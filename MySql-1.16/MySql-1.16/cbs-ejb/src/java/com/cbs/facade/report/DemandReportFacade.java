/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.DemandOefPojo;
import com.cbs.utils.CbsUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Admin
 */
@Stateless(mappedName = "DemandReportFacade")
public class DemandReportFacade implements DemandReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private LoanInterestCalculationFacadeRemote loanIntCalcRemote;
    @EJB
    private LoanReportFacadeRemote loanReportRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#");

    public List<DemandOefPojo> getOefDemandRecoveriesData(String area, String tillDate, String brCode, String range) throws ApplicationException {
        List<DemandOefPojo> dataList = new ArrayList<DemandOefPojo>();
        double balance = 0d, installmentAmt = 0d, intAmt = 0d, emiAmt = 0d;
        try {
            String subCondition = "";
            if (area.equalsIgnoreCase("IECP")) {
                if (range.equalsIgnoreCase("100001 to 104444")) {
                    subCondition = "and trim(employeeNo) between '100001' and '104444'";

                } else if (range.equalsIgnoreCase("104445 to 106666")) {
                    subCondition = "and trim(employeeNo) between '104445' and '106666'";
                } else if (range.equalsIgnoreCase("106667 to 109999")) {
                    subCondition = "and trim(employeeNo) between '106667' and '109999'";
                }
            } else {
                subCondition = "and substring(employeeNo,1,2) <> '10'";
                // subCondition = "and trim(employeeNo) between '805928' and '805928'";
            }
            Map<String, String> intCalFrDtByAcodeMap = new HashMap<String, String>();
            Map<String, String> intCalToDtByAcodeMap = new HashMap<String, String>();

            List list = em.createNativeQuery("select acctcode from accounttypemaster  where acctnature in('TL','DL')").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector actVector = (Vector) list.get(i);
                List list1 = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d'),date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                        + "AC_TYPE ='" + actVector.get(0).toString() + "' and POST_FLAG = 'N'  and BRNCODE = '" + brCode + "' and SNO in "
                        + "(select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + actVector.get(0).toString() + "' and POST_FLAG = 'N' and BRNCODE = '" + brCode + "' and flag = 'I')").getResultList();
                if (!list1.isEmpty()) {
                    Vector dtVector = (Vector) list1.get(0);
                    intCalFrDtByAcodeMap.put(actVector.get(0).toString(), dtVector.get(0).toString());
                    intCalToDtByAcodeMap.put(actVector.get(0).toString(), dtVector.get(1).toString());
                }
            }
            List result = em.createNativeQuery("select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) debitBal,e.acctnature  "
                    + "from customerid a, cbs_customer_master_detail b,accountmaster c,loan_recon d,(select AcctCode,acctnature from accounttypemaster where acctnature='" + CbsConstant.TERM_LOAN + "') e,share_holder s where a.custid = cast(b.customerid as unsigned) and s.custid = b.customerid "
                    + "and b.employeeNo is not null and b.employeeNo <> '' "
                    + "and substring(a.acno,3,2) = e.AcctCode " + subCondition + " and a.acno=c.acno and c.acno = d.acno "
                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "') and d.dt <= '" + tillDate + "' "
                    + "group by c.acno having(debitBal<0) "
                    + "union all "
                    + "select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) creditBal,e.acctnature  "
                    + "from customerid a, cbs_customer_master_detail b,accountmaster c,rdrecon d,(select AcctCode,acctnature from accounttypemaster where acctnature='" + CbsConstant.RECURRING_AC + "') e,share_holder s where a.custid = cast(b.customerid as unsigned) and s.custid = b.customerid "
                    + "and b.employeeNo is not null and b.employeeNo <> '' "
                    + "and substring(a.acno,3,2) = e.AcctCode " + subCondition + " and a.acno=c.acno and c.acno = d.acno "
                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "') and d.dt <= '" + tillDate + "' "
                    + "group by c.acno having(creditBal> 0) order by 2,3").getResultList();

            String empId = "";
            int cnt = 0;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DemandOefPojo pojo = new DemandOefPojo();
                    String employeeNo = vtr.get(0).toString();
                    String custId = vtr.get(1).toString();
                    String acNo = vtr.get(2).toString();
                    String custName = vtr.get(3).toString();
                    balance = Double.parseDouble(vtr.get(4).toString());
                    String actNature = vtr.get(5).toString();

                    if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        String dmdValue[] = getInstallmentAmt(acNo, tillDate, balance, actNature);
//                        String fromDt = loanIntCalcRemote.allFromDt(acNo.substring(2, 4), brCode, "f");
//                        String toDt = loanIntCalcRemote.allFromDt(acNo.substring(2, 4), brCode, "t");
//                        String fromDt = intCalFrDtByAcodeMap.get(acNo.substring(2, 4));
//                        String toDt = intCalToDtByAcodeMap.get(acNo.substring(2, 4));
//                        LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(fromDt, toDt, acNo, brCode);
//                        intAmt = it.getTotalInt() * -1;
                        installmentAmt = Double.parseDouble(dmdValue[0]);
                        intAmt = Double.parseDouble(dmdValue[2]);
                        emiAmt = installmentAmt + intAmt;
                    } else {
                        String dmdValue[] = getInstallmentAmt(acNo, tillDate, balance, actNature);
                        installmentAmt = Double.parseDouble(dmdValue[0]);
                        emiAmt = installmentAmt;
                    }

                    pojo.setsNo(i + 1);
                    pojo.setPersonalNo(employeeNo);
                    pojo.setCustId(custId);
                    pojo.setAcNo(acNo);
                    pojo.setCustName(custName);
                    if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        pojo.setLtAmt(installmentAmt);
                        pojo.setLtInt(intAmt);
                        pojo.setLoanEmi(emiAmt);
                    } else if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        pojo.setRdAmt(installmentAmt);
                    }
                    pojo.setLtdmsAmt(0);
                    pojo.setBalance(Math.abs(balance));
                    pojo.setTotalDemand(emiAmt);
                    dataList.add(pojo);



                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return dataList;
    }

    public String[] getInstallmentAmt(String acNo, String tillDt, double outSt, String acNature) throws ApplicationException {
        double installAmt = 0d;
        String dmdAmount = "0", demandDueDt = "",dmdIntAmount="0";
        List emiList = new ArrayList();
        String arr[] = new String[3];

        try {

            if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                emiList = em.createNativeQuery("select ifnull(sum(PRINAMT),0) as overDueAmt,"
                        + "ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt,ifnull(sum(INTERESTAMT),0) from emidetails "
                        + "where acno='" + acNo + "' and status='Unpaid' and "
                        + "duedt<='" + tillDt + "'").getResultList();
                Vector element = (Vector) emiList.get(0);
                dmdAmount = element.get(0).toString();
                dmdIntAmount = element.get(2).toString();
                if (Double.parseDouble(dmdAmount) == 0) {
                    if (Math.abs(outSt) > 0) {
                        emiList = em.createNativeQuery("select ifnull(sum(PRINAMT),0) as overDueAmt,"
                                + "ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt,ifnull(sum(INTERESTAMT),0) from emidetails "
                                + "where acno='" + acNo + "' and status='Unpaid' and "
                                + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                + "where acno='" + acNo + "' and status='Unpaid') ").getResultList();
                    }
                    element = (Vector) emiList.get(0);
                    dmdAmount = element.get(0).toString();
                    dmdIntAmount = element.get(2).toString();
                    if (Double.parseDouble(dmdAmount) == 0) {
                        dmdAmount = String.valueOf(Math.abs(outSt));
                    } else {
                        if (Double.parseDouble(dmdAmount) > Math.abs(outSt)) {
                            dmdAmount = String.valueOf(Math.abs(outSt));
                        } else {
                            dmdAmount = dmdAmount;
                        }
                    }
                } else {
                    if ((Double.parseDouble(dmdAmount)+Double.parseDouble(dmdIntAmount)) > Math.abs(outSt)) {
                        dmdAmount = String.valueOf(Math.abs(outSt));
                    } else {
                        dmdAmount = dmdAmount;
                    }
                }
                demandDueDt = element.get(1).toString();
            } else {
                emiList = em.createNativeQuery("select ifnull((sum(installamt)),0) as overDueAmt,ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from rd_installment where acno='" + acNo + "' and status='Unpaid' and duedt<='" + tillDt + "'").getResultList();
                Vector rdvtr = (Vector) emiList.get(0);
                dmdAmount = rdvtr.get(0).toString();
                if (Double.parseDouble(dmdAmount) == 0) {
                    emiList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0),ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from rd_installment "
                            + "where acno='" + acNo + "' and sno=(select min(SNO) from rd_installment where acno = '" + acNo + "' and STATUS = 'Unpaid')").getResultList();
                }
                rdvtr = (Vector) emiList.get(0);
                dmdAmount = rdvtr.get(0).toString();
                demandDueDt = rdvtr.get(1).toString();
            }

            arr[0] = dmdAmount;
            arr[1] = demandDueDt;
            arr[2] = dmdIntAmount;


            //installAmt = Double.parseDouble(dmdAmount);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }

    public String generateExcelPersonalNoWise(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy, String range) throws ApplicationException {
        String fileName = "";

        try {
            //Generation of folder where we write the file.
            String writeFolderPath = "";

            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/PersonalNoWise/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/PersonalNoWise/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }
            //Generating Excel File.
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("DEMAND");
            //Header Line
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("REC_CODE");
            cell = row.createCell(1);
            cell.setCellValue("YEAR");
            cell = row.createCell(2);
            cell.setCellValue("MONTH");
            cell = row.createCell(3);
            cell.setCellValue("PER_NO");
            cell = row.createCell(4);
            cell.setCellValue("SEC_NO");
            cell = row.createCell(5);
            cell.setCellValue("TKT_NO");
            cell = row.createCell(6);
            cell.setCellValue("CODE_DED1");
            cell = row.createCell(7);
            cell.setCellValue("AMT_DED1");
            int rownum = 1;


            String subCondition = "";
            if (placeOfWorking.equalsIgnoreCase("IECP")) {
                if (range.equalsIgnoreCase("100001 to 104444")) {
                    subCondition = "and trim(employeeNo) between '100001' and '104444'";
                } else if (range.equalsIgnoreCase("104445 to 106666")) {
                    subCondition = "and trim(employeeNo) between '104445' and '106666'";
                } else if (range.equalsIgnoreCase("106667 to 109999")) {
                    subCondition = "and trim(employeeNo) between '106667' and '109999'";
                }
            } else {
                subCondition = "and substring(employeeNo,1,2) <> '10'";
            }
            Map<String, String> intCalFrDtByAcodeMap = new HashMap<String, String>();
            Map<String, String> intCalToDtByAcodeMap = new HashMap<String, String>();
            Map<String, String> dmdIdByAcodeMap = new HashMap<String, String>();

            List list = em.createNativeQuery("select acctcode from accounttypemaster  where acctnature in('TL','DL')").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector actVector = (Vector) list.get(i);
                List list1 = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d'),date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                        + "AC_TYPE ='" + actVector.get(0).toString() + "' and POST_FLAG = 'N'  and BRNCODE = '" + brncode + "' and SNO in "
                        + "(select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + actVector.get(0).toString() + "' and POST_FLAG = 'N' and BRNCODE = '" + brncode + "' and flag = 'I')").getResultList();
                if (!list1.isEmpty()) {
                    Vector dtVector = (Vector) list1.get(0);
                    intCalFrDtByAcodeMap.put(actVector.get(0).toString(), dtVector.get(0).toString());
                    intCalToDtByAcodeMap.put(actVector.get(0).toString(), dtVector.get(1).toString());
                }
            }

            List dmdIdList = em.createNativeQuery("select SCHEME_TYPE,concat(principal_flow_id,':',int_demand_flow_id) from cbs_scheme_loan_prepayment_details where SCHEME_TYPE in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.TERM_LOAN + "')").getResultList();
            for (int i = 0; i < dmdIdList.size(); i++) {
                Vector dmIdVector = (Vector) dmdIdList.get(i);
                dmdIdByAcodeMap.put(dmIdVector.get(0).toString(), dmIdVector.get(1).toString());
            }

            List list1 = em.createNativeQuery("select employeeNo,custid,acno,custname,debitBal,acctnature,buss_desig from("
                    + "select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) debitBal,e.acctnature,s.buss_desig  "
                    + "from customerid a, cbs_customer_master_detail b,accountmaster c,loan_recon d,(select AcctCode,acctnature from accounttypemaster where acctnature='" + CbsConstant.TERM_LOAN + "') e,share_holder s where a.custid = cast(b.customerid as unsigned) and s.custid = b.customerid "
                    + "and b.employeeNo is not null and b.employeeNo <> '' "
                    + "and substring(a.acno,3,2) = e.AcctCode " + subCondition + " and a.acno=c.acno and c.acno = d.acno "
                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + generationDt + "') and d.dt <= '" + generationDt + "' "
                    + "group by c.acno having(debitBal<0) "
                    + "union all "
                    + "select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) creditBal,e.acctnature,s.buss_desig  "
                    + "from customerid a, cbs_customer_master_detail b,accountmaster c,rdrecon d,(select AcctCode,acctnature from accounttypemaster where acctnature='" + CbsConstant.RECURRING_AC + "') e,share_holder s where a.custid = cast(b.customerid as unsigned) and s.custid = b.customerid "
                    + "and b.employeeNo is not null and b.employeeNo <> '' "
                    + "and substring(a.acno,3,2) = e.AcctCode " + subCondition + " and a.acno=c.acno and c.acno = d.acno "
                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + generationDt + "') and d.dt <= '" + generationDt + "' "
                    + "group by c.acno having(creditBal> 0) order by 2,3)a group by employeeNo").getResultList();
            if (!list1.isEmpty()) {
                for (int i = 0; i < list1.size(); i++) {
                    Vector ele = (Vector) list1.get(i);
                    String personalNo = ele.get(0).toString();
                    String secNio = ele.get(6).toString();
                    // String actNature = ele.get(5).toString();
                    //  double balance = 0d, installmentAmt = 0d, intAmt = 0d, emiAmt = 0d;
                    double emiAmtPid = 0d, emiAmt = 0d;

                    List result = em.createNativeQuery("select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) debitBal,e.acctnature,s.buss_desig  "
                            + "from customerid a, cbs_customer_master_detail b,accountmaster c,loan_recon d,(select AcctCode,acctnature from accounttypemaster where acctnature='" + CbsConstant.TERM_LOAN + "') e,share_holder s where a.custid = cast(b.customerid as unsigned) and s.custid = b.customerid "
                            + "and b.employeeNo is not null and b.employeeNo <> '' "
                            + "and substring(a.acno,3,2) = e.AcctCode and trim(employeeNo) ='" + personalNo + "' and a.acno=c.acno and c.acno = d.acno "
                            + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + generationDt + "') and d.dt <= '" + generationDt + "' "
                            + "group by c.acno having(debitBal<0) "
                            + "union all "
                            + "select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) creditBal,e.acctnature,s.buss_desig  "
                            + "from customerid a, cbs_customer_master_detail b,accountmaster c,rdrecon d,(select AcctCode,acctnature from accounttypemaster where acctnature='" + CbsConstant.RECURRING_AC + "') e,share_holder s where a.custid = cast(b.customerid as unsigned) and s.custid = b.customerid "
                            + "and b.employeeNo is not null and b.employeeNo <> '' "
                            + "and substring(a.acno,3,2) = e.AcctCode and trim(employeeNo) ='" + personalNo + "' and a.acno=c.acno and c.acno = d.acno "
                            + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + generationDt + "') and d.dt <= '" + generationDt + "' "
                            + "group by c.acno having(creditBal> 0) order by 2,3").getResultList();

                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector vtr = (Vector) result.get(j);
                            String employeeNo = vtr.get(0).toString();
                            String custId = vtr.get(1).toString();
                            String acNo = vtr.get(2).toString();
                            String custName = vtr.get(3).toString();
                            double balance = 0d, installmentAmt = 0d, intAmt = 0d, rdinstallmentAmt = 0d;
                            balance = Double.parseDouble(vtr.get(4).toString());
                            String actNature = vtr.get(5).toString();
                            //String secNio = vtr.get(6).toString();

                            if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                //installmentAmt = demandRemote.getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                                String dmdValue[] = getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                                installmentAmt = Double.parseDouble(dmdValue[0]);
                                //dmdDueDt = dmdValue[1];
//                                String fromDt = intCalFrDtByAcodeMap.get(acNo.substring(2, 4));
//                                String toDt = intCalToDtByAcodeMap.get(acNo.substring(2, 4));
//                                LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(fromDt, toDt, acNo, brncode);
//                                intAmt = it.getTotalInt() * -1;
                                
                                intAmt = Double.parseDouble(dmdValue[2]);
                                emiAmt = installmentAmt + intAmt;
                                //Get Demand flow Id
                            } else {
                                // installmentAmt = demandRemote.getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                                String dmdValue[] = getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                                installmentAmt = Double.parseDouble(dmdValue[0]);
                                rdinstallmentAmt = installmentAmt;

                            }
                            emiAmtPid = emiAmtPid + emiAmt + rdinstallmentAmt;

                        }
                        //Data Line Creation
                        row = sheet.createRow(rownum++);
                        cell = row.createCell(0);
                        cell.setCellValue("2D");
                        cell = row.createCell(1);
                        cell.setCellValue(ymd.format(dmy.parse(generationDt)).substring(0, 4));
                        cell = row.createCell(2);
                        cell.setCellValue(ymd.format(dmy.parse(generationDt)).substring(4, 6));
                        cell = row.createCell(3);
                        cell.setCellValue(personalNo);
                        cell = row.createCell(4);
                        cell.setCellValue(secNio);
                        cell = row.createCell(5);
                        cell.setCellValue(0.0);
                        cell = row.createCell(6);
                        cell.setCellValue("36");
                        cell = row.createCell(7);
                        // cell.setCellValue(String.valueOf(emiAmtPid));
                        cell.setCellValue(emiAmtPid);

                        System.out.println(String.valueOf(emiAmtPid) + " = = = " + formatter.format(emiAmtPid));

                        String absFilePath = writeFolderPath + placeOfWorking + "_" + range + ".DBF";
                        FileOutputStream out = new FileOutputStream(new File(absFilePath));
                        wb.write(out);
                        out.close();
                        fileName = absFilePath.substring(absFilePath.lastIndexOf("/") + 1);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public int getDmdSrlNo() {
        int srlNo = 0;
        try {
            List maxList = em.createNativeQuery("select ifnull(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
            Vector srlVector = (Vector) maxList.get(0);
            srlNo = (Integer.parseInt(srlVector.get(0).toString()));
            return srlNo;
        } catch (Exception ex) {
            ex.getMessage();
        }
        return srlNo;
    }

    @Override
    public List<OverdueEmiReportPojo> getDueLoanRecoveryData(String branch, String frDt, String days) throws ApplicationException {
        List<OverdueEmiReportPojo> dataList = new ArrayList<>();
        List result = new ArrayList();
        try {
            String asOnDate = CbsUtil.dateAdd(frDt, Integer.parseInt(days));
            int isExceessEmi = common.isExceessEmi(asOnDate);
            if (branch.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select a.acno,b.custname,a.INSTALLAMT,date_format(duedt,'%d/%m/%Y'),mobilenumber from emidetails a,accountmaster b,customerid c,cbs_customer_master_detail d "
                        + "where a.acno=b.acno and a.acno = c.acno "
                        + "and c.custid = cast(d.customerid as unsigned) and duedt between '" + frDt + "' and '" + asOnDate + "' "
                        + "and status = 'unpaid' group by acno").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,b.custname,a.INSTALLAMT,date_format(duedt,'%d/%m/%Y'),mobilenumber from emidetails a,accountmaster b,customerid c,cbs_customer_master_detail d "
                        + "where a.acno=b.acno and a.acno = c.acno and curbrcode = '" + branch + "' "
                        + "and c.custid = cast(d.customerid as unsigned) and duedt between '" + frDt + "' and '" + asOnDate + "' "
                        + "and status = 'unpaid' group by acno").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
                    pojo.setAccountNumber(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setInstallmentamt(new BigDecimal(vtr.get(2).toString()));
                    pojo.setDueDate(vtr.get(3).toString());
                    pojo.setCustId(vtr.get(4).toString());
                    List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("A/Cs Whose Plan Has Finished", vtr.get(0).toString(), 1, 200, asOnDate, vtr.get(0).toString().substring(0, 2), "", 0, isExceessEmi, null, 0);
                    if (!overdueEmiList.isEmpty()) {
                        pojo.setAmountOverdue(overdueEmiList.get(0).getAmountOverdue());
                    }
                    pojo.setBalance(new BigDecimal(Math.abs(common.getBalanceOnDate(vtr.get(0).toString(), asOnDate))));
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }
}
