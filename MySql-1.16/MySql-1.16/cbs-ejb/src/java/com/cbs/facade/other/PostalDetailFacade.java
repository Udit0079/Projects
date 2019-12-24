/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.ChargesObject;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.DemandReportFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.pojo.BulkRecoveryPojo;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.pojo.PostalDemandPojo;
import com.cbs.pojo.PremiumDetailsPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author root
 */
@Stateless(mappedName = "PostalDetailFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PostalDetailFacade implements PostalDetailFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private CtsManagementFacadeRemote ctsRemote;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnRemote;
    @EJB
    private LoanInterestCalculationFacadeRemote loanIntCalcRemote;
    @EJB
    private LoanReportFacadeRemote loanReportRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private DemandReportFacadeRemote demandRemote;
    @EJB
    DDSReportFacadeRemote ddsRemote;
    @EJB
    CommonReportMethodsRemote common;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat yMMd = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();

    public int getCodeByReportName(String reportName) throws ApplicationException {
        int code = 0;
        try {
            List dataList = em.createNativeQuery("select code from parameterinfo_report where reportname='" + reportName + "'").getResultList();
            if (!dataList.isEmpty()) {
                Vector element = (Vector) dataList.get(0);
                code = Integer.parseInt(element.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return code;
    }

//    public String getMaxFinYearDate(String alphacode) throws ApplicationException {
//        String finYearMaxDt = "";
//        try {
//            List dataList = em.createNativeQuery("select maxdate from yearend where cast(brncode as unsigned) in(select brncode from branchmaster where alphacode='" + alphacode + "') and yearendflag='N'").getResultList();
//            if (!dataList.isEmpty()) {
//                Vector element = (Vector) dataList.get(0);
//                finYearMaxDt = element.get(0).toString();
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return finYearMaxDt;
//    }
    public List getPrimiumAndWellFareDetails(String txnType, String acno) throws ApplicationException {
        try {
//            List chkList = em.createNativeQuery("select freq from cbs_slab_range where txn_type='" + txnType + "' and status='A' and freq = 'O'").getResultList();
//            if (chkList.isEmpty()) {
//                Vector chkVect = (Vector) chkList.get(0);
//                String freq = chkVect.get(0).toString();
//                return em.createNativeQuery("select freq from cbs_slab_range where txn_type='' and status='A' and freq = 'O'").getResultList();
//            } else {
            List chkList = em.createNativeQuery("SELECT acno,sno,date_format(due_dt,'%d/%m/%Y') as due_dt,insured_amount,premium_amount,"
                    + "status,ifnull(date_format(payment_dt,'%d/%m/%Y'),'') as payment_dt,enter_by,periodicity,excess_amt,policy_no "
                    + "FROM insurance_details where txn_type='" + txnType + "' and acno='" + acno + "' order by sno").getResultList();
//            }
            return chkList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getPrimiumAmtDetails(String txnType, String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT ifnull(sum(ifnull(premium_amount,0)),0), IFNULL(date_format(max(payment_dt),'%Y%m%d'),'19000101') "
                    + " FROM insurance_details where txn_type='" + txnType + "' and acno='" + acno + "'").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getMemberRegistrationDt(String memberNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select date_format(regdate,'%d/%m/%Y') as regdate,date_format(dob,'%d/%m/%Y') as dob from share_holder where regfoliono='" + memberNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

//    public double getWellFareFund(String txnType) throws ApplicationException {
//        double wellFareFund = 0.0;
//        try {
//            List dataList = em.createNativeQuery("select ifnull(amount,0) from cbs_slab_range where txn_type='" + txnType + "' and status='A'").getResultList();
//            if (!dataList.isEmpty()) {
//                Vector element = (Vector) dataList.get(0);
//                wellFareFund = Double.parseDouble(element.get(0).toString());
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return wellFareFund;
//    }
    public List getWellFareFund(String txnType) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(amount,0),freq from cbs_slab_range where txn_type='" + txnType + "' and status='A'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String generateInsuranceAndWellFare(List<PremiumDetailsPojo> dataList, String acNo, String odLimit, String txnType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "true";
        try {
            ut.begin();
            double amt = 0d, totalAmtPaid = 0d;
            String freq = "", glHead = "";
            // (!ftsPosting.getAcctTypeByCode(acNo.substring(2, 4)).equalsIgnoreCase("TF") && dataList.isEmpty())

            if (!dataList.isEmpty()) {
                if (txnType.equalsIgnoreCase("WL")) {
                    List chkList = em.createNativeQuery("select amount, gl_head from cbs_slab_range where txn_type='" + txnType + "' and status='A' and freq = 'O'").getResultList();
                    if (chkList.size() > 0) {
                        Vector chkVect = (Vector) chkList.get(0);
//                        amt = Double.parseDouble(chkVect.get(0).toString());
                        amt = Double.parseDouble(odLimit);
                        glHead = chkVect.get(1).toString();
                        int gNo = 1;
//                        List checkAmtPaid = em.createNativeQuery("select IFNULL(SUM(IFNULL(premium_amount,0)),0) from insurance_details where acno = '" + dataList.get(0).getAcno() + "' and status = 'PAID' and txn_type = 'WL' ").getResultList();
//                        if (checkAmtPaid.size() > 0) {
//                            Vector checkAmtVect = (Vector) checkAmtPaid.get(0);
//                            totalAmtPaid = Double.parseDouble(checkAmtVect.get(0).toString());
//                            if (totalAmtPaid < amt) {
//                                amt = amt - totalAmtPaid;
//                            }
//                        }
                        if (amt > 0) {
                            for (PremiumDetailsPojo pojo : dataList) {
                                List gNoList = em.createNativeQuery("select IFNULL(max(sno),0) from insurance_details where acno='" + pojo.getAcno() + "'").getResultList();
                                if (gNoList.size() > 0) {
                                    Vector gNoVect = (Vector) gNoList.get(0);
                                    gNo = Integer.parseInt(gNoVect.get(0).toString()) + 1;
                                }
                                int insertResult = em.createNativeQuery("INSERT INTO insurance_details(txn_type,acno,sno,due_dt,insured_amount,"
                                        + "premium_amount,status,payment_dt,enter_by,periodicity,excess_amt,policy_no) "
                                        + "VALUES('" + pojo.getTxnType() + "','" + pojo.getAcno() + "'," + gNo + ","
                                        + "'" + ymd.format(dmy.parse(pojo.getDueDt())) + "'," + pojo.getAmount() + "," + amt + ","
                                        + "'PAID',curdate(),'" + pojo.getEnteryBy() + "',"
                                        + "'" + pojo.getPeriodicity() + "'," + pojo.getExcessAmount() + ",'" + pojo.getPolicyNo() + "')").executeUpdate();
                                if (insertResult <= 0) {
                                    throw new ApplicationException("Problem in premium / wellfare generation");
                                }
                                //List existThreftAcCheckList = em.createNativeQuery("select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + pojo.getAcno() + "') and substring(acno,3,2) = (select acctcode from accounttypemaster where  acctnature = 'SB' and AcctType = 'TF')").getResultList();
                                List existThreftAcCheckList = em.createNativeQuery("select b.acno from customerid a,accountmaster b where CustId = (select custId from share_holder where Regfoliono = '" + pojo.getAcno() + "') "
                                        + "and substring(b.acno,3,2) = (select acctcode from accounttypemaster where  acctnature = 'SB' and AcctType = 'TF') "
                                        + "and b.accstatus<> 9 and a.acno = b.acno").getResultList();
                                if (existThreftAcCheckList.size() > 0) {
                                    Vector existThreftAcCheckVect = (Vector) existThreftAcCheckList.get(0);
                                    String tfAcNo = existThreftAcCheckVect.get(0).toString();
                                    List balChkList = em.createNativeQuery("select IFNULL(SUM(IFNULL(CRAMT,0)),0)-IFNULL(SUM(IFNULL(DRAMT,0)),0)  from recon where acno = '" + tfAcNo + "' and dt<=DATE_FORMAT(CURDATE(),'%Y%m%d')").getResultList();
                                    if (balChkList.size() > 0) {
                                        Vector balChkVect = (Vector) balChkList.get(0);
                                        double balChk = Double.parseDouble(balChkVect.get(0).toString());
                                        if (balChk < amt) {
                                            throw new ApplicationException("Please check the Thrift Account " + tfAcNo + " balance");
                                        } else {
                                            float trSNo = ftsPosting.getTrsNo();
                                            float recNo = ftsPosting.getRecNo();

                                            Query insertReconQuery = em.createNativeQuery("INSERT into recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                                    + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
                                                    + " values('" + tfAcNo + "',1, DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                                                    + amt + ",0,2,0,3,'WELLFARE FUND','system','Y','" + pojo.getEnteryBy() + "',"
                                                    + trSNo + "," + recNo + ",'" + tfAcNo.substring(0, 2) + "','" + tfAcNo.substring(0, 2) + "')");
                                            Integer insertRecon = insertReconQuery.executeUpdate();
                                            if (insertRecon == 0) {
                                                throw new ApplicationException("Value not inserted in Recon");
                                            }
                                            List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + tfAcNo + "'").getResultList();

                                            if (chkBalan.size() > 0) {
                                                Query updateCaReconQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE = BALANCE - " + amt
                                                        + " WHERE Acno = '" + tfAcNo + "'");

                                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                                if (updateCaRecon == 0) {
                                                    throw new ApplicationException("Value not updated in  reconbalan");
                                                }
                                            }
                                            recNo = ftsPosting.getRecNo();
                                            Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + amt + " WHERE ACNO= '" + tfAcNo.substring(0, 2).concat(glHead).concat("01") + "'");
                                            Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                            if (updateReconBalan == 0) {
                                                throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                            }
                                            Query insertGlReconBalanQuery = em.createNativeQuery("INSERT into gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                    + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                    + " VALUES('" + tfAcNo.substring(0, 2).concat(glHead).concat("01") + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),DATE_FORMAT(CURDATE(),'%Y%m%d'),"
                                                    + amt + ",0,2,0,3,'WELLFARE FUND FROM ACNO " + tfAcNo + "','system','Y','" + pojo.getEnteryBy()
                                                    + "'," + trSNo + "," + recNo + ",'" + tfAcNo.substring(0, 2) + "','" + tfAcNo.substring(0, 2) + "')");
                                            Integer insertGlReconBalan = insertGlReconBalanQuery.executeUpdate();
                                            if (insertGlReconBalan == 0) {
                                                throw new ApplicationException("Value doesn't inserted in gl_recon");
                                            }
                                        }
                                    }
                                } else {
                                    throw new ApplicationException("Please Open the Thrift Account.");
                                }

                            }
                        }
                    } else {
                        for (PremiumDetailsPojo pojo : dataList) {
                            int insertResult = em.createNativeQuery("INSERT INTO insurance_details(txn_type,acno,sno,due_dt,insured_amount,"
                                    + "premium_amount,status,payment_dt,enter_by,periodicity,excess_amt,policy_no) "
                                    + "VALUES('" + pojo.getTxnType() + "','" + pojo.getAcno() + "'," + pojo.getSno() + ","
                                    + "'" + ymd.format(dmy.parse(pojo.getDueDt())) + "'," + pojo.getAmount() + "," + pojo.getPremiumAmount() + ","
                                    + "'" + pojo.getStatus() + "','" + ymd.format(dmy.parse(pojo.getPaymentDt())) + "','" + pojo.getEnteryBy() + "',"
                                    + "'" + pojo.getPeriodicity() + "'," + pojo.getExcessAmount() + ",'" + pojo.getPolicyNo() + "')").executeUpdate();
                            if (insertResult <= 0) {
                                throw new ApplicationException("Problem in premium / wellfare generation");
                            }
                        }
                    }
                } else {
                    for (PremiumDetailsPojo pojo : dataList) {
                        int insertResult = em.createNativeQuery("INSERT INTO insurance_details(txn_type,acno,sno,due_dt,insured_amount,"
                                + "premium_amount,status,payment_dt,enter_by,periodicity,excess_amt,policy_no) "
                                + "VALUES('" + pojo.getTxnType() + "','" + pojo.getAcno() + "'," + pojo.getSno() + ","
                                + "'" + ymd.format(dmy.parse(pojo.getDueDt())) + "'," + pojo.getAmount() + "," + pojo.getPremiumAmount() + ","
                                + "'" + pojo.getStatus() + "','" + ymd.format(dmy.parse(pojo.getPaymentDt())) + "','" + pojo.getEnteryBy() + "',"
                                + "'" + pojo.getPeriodicity() + "'," + pojo.getExcessAmount() + ",'" + pojo.getPolicyNo() + "')").executeUpdate();
                        if (insertResult <= 0) {
                            throw new ApplicationException("Problem in premium / wellfare generation");
                        }
                    }
                }

            } else {
                Query updateacMasterQuery = em.createNativeQuery("update accountmaster set RdInstal = '" + odLimit + "',Rdmatdate = now() where acno  = '" + acNo + "'");
                Integer updateAcMaster = updateacMasterQuery.executeUpdate();
                if (updateAcMaster == 0) {
                    throw new ApplicationException("Value doesn't updated in ACCOUNTMASTER");
                }
            }

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    public TreeSet<String> getBranchPlaceOfWorking(String brncode) throws ApplicationException {
        TreeSet<String> ts = new TreeSet<String>();
        try {
//            List dataList = em.createNativeQuery("select acno from accountmaster where accstatus in(1,11,12) and "
//                    + "curbrcode='" + brncode + "' and accttype in (select acctcode from accounttypemaster where "
//                    + "acctnature in('" + CbsConstant.TERM_LOAN + "'))").getResultList();
//            if (!dataList.isEmpty()) {
//                for (int i = 0; i < dataList.size(); i++) {
//                    Vector element = (Vector) dataList.get(i);
//                    String acno = element.get(0).toString();

            // List firmList = em.createNativeQuery("select distinct area from share_holder ").getResultList();
            List returnList = new ArrayList();
            if (brncode.equalsIgnoreCase("A")) {
                returnList = em.createNativeQuery("select distinct(area) from share_holder where area is not null order by area").getResultList();
            } else {
                returnList = em.createNativeQuery("select distinct(s.area) from share_holder s, branchmaster b where s.alphacode = b.alphacode and b.brncode = " + Integer.parseInt(brncode) + " "
                        + "and s.area is not null order by area").getResultList();
            }

            if (!returnList.isEmpty()) {
                for (int i = 0; i < returnList.size(); i++) {
                    Vector element = (Vector) returnList.get(i);
                    if (element.get(0) != null && !element.get(0).toString().equals("")) {
                        ts.add(element.get(0).toString());
                    }
                }

            }
//                }
//            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return ts;
    }

    public String generateExcelFileBasedOnPlaceOfWorking(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String fileName = "";
        try {
            ut.begin();
            //Generation of folder where we write the file.
            String writeFolderPath = "";
            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/POSTAL/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/POSTAL/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }

            String bnkIdentity = ftsPosting.getCodeFromCbsParameterInfo("BNK_IDENTIFIER");
            String bnkCode = ftsPosting.getBankCode();
            String acctCode = armyInterestPostParameter();
            if (acctCode.equalsIgnoreCase("0")) {
                throw new ApplicationException("Please fill Acct Code in cbs_parameterinfo!");
            }
            //Get max DMD_SRL_NUM no.

            List maxList = em.createNativeQuery("select ifnull(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
            Vector srlVector = (Vector) maxList.get(0);
            Integer srlNo = (Integer.parseInt(srlVector.get(0).toString()));
            //Get data for placeofworking.
            List dataList = em.createNativeQuery("select custid from share_holder where "
                    + "area='" + placeOfWorking + "'").getResultList();
            if (!dataList.isEmpty()) {
                //Generating Excel File.
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("DEMAND");

                //Header Line
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("SL NO.");
                cell = row.createCell(1);
                cell.setCellValue("ID NO.");
                cell = row.createCell(2);
                cell.setCellValue("ACNO");
                cell = row.createCell(3);
                cell.setCellValue("LOAN-EMI");
                cell = row.createCell(4);
                cell.setCellValue("RD-INSTALLMENT");
                cell = row.createCell(5);
                cell.setCellValue("THEFT-FUND");
                cell = row.createCell(6);
                cell.setCellValue("AMOUNT");
                cell = row.createCell(7);
                cell.setCellValue("CHEQUE-NO");
                cell = row.createCell(8);
                cell.setCellValue("CHEQUE-DATE");
                int rownum = 1;
                for (int i = 0; i < dataList.size(); i++) {                     //CustomerId Loop
                    Vector element = (Vector) dataList.get(i);
                    String customerId = element.get(0).toString();

                    //Member No on the basis of custid.
                    // List memList = em.createNativeQuery("select regfoliono from share_holder where custid='" + customerId + "'").getResultList();
                    List memList = em.createNativeQuery("select a.regfoliono,date_format(b.DateOfBirth,'%Y%m%d') as dob from share_holder a,cbs_customer_master_detail b where custid='" + customerId + "' and a.custid = b.customerid").getResultList();
                    if (memList.isEmpty()) {
                        throw new ApplicationException("There is no Member No for CustomerId: " + customerId);
                    }
                    element = (Vector) memList.get(0);
                    String memberNo = element.get(0).toString();
                    String dob = element.get(1).toString();
                    //  System.out.println(customerId+" == "+dob+" == "+ymd.format(new Date())+" == "+CbsUtil.yearDiff(ymd.parse(dob),new Date()));
                    String srzDob = CbsUtil.yearAdd(dob, 60);
                    List schNoList = em.createNativeQuery("select ifnull(max(shdl_num),0)+1 as sno from cbs_loan_dmd_table where DMD_SRL_NUM='" + srlNo + "' ").getResultList();
                    Vector schNoVect = (Vector) schNoList.get(0);
                    int schNo = Integer.parseInt(schNoVect.get(0).toString());
                    //All Active Loan Account For A CustomerId of a particular barnch.
                    if ((bnkCode.equalsIgnoreCase("krbi") && ymd.parse(srzDob).after(new Date())) || bnkCode.equalsIgnoreCase("army")) {

                        List activeList = new ArrayList();
                        if (bnkIdentity.equalsIgnoreCase("RP")) {
                            activeList = em.createNativeQuery("select acno from accountmaster a,accounttypemaster b where accstatus <>9 and "
                                    + "curbrcode='" + brncode + "' and a.accttype in (" + acctCode + ") and "
                                    + "acno in(select acno from customerid where custid=" + customerId + ")  and acctnature in('" + CbsConstant.TERM_LOAN + "') and a.accttype=AcctCode").getResultList();
                        } else {
                            activeList = em.createNativeQuery("select acno from accountmaster where accstatus <>9 and "
                                    + "curbrcode='" + brncode + "' and accttype in (select acctcode from accounttypemaster "
                                    + "where acctnature in('" + CbsConstant.TERM_LOAN + "') and acctcode <> '31') and "
                                    + "acno in(select acno from customerid where custid=" + customerId + ")").getResultList();
                        }


                        if (!activeList.isEmpty()) {
                            for (int j = 0; j < activeList.size(); j++) {           //Loan Account Loop
                                //Data Line Creation
                                // row = sheet.createRow(rownum++);

                                element = (Vector) activeList.get(j);
                                String loanAccount = element.get(0).toString();
//                            cell = row.createCell(0);
//                            cell.setCellValue(rownum - 1);
//
//                            cell = row.createCell(1);
//                            cell.setCellValue(customerId);
//
//                            cell = row.createCell(2);
//                            cell.setCellValue(loanAccount);
//                            cell = row.createCell(3);
                                //Get Demand flow Id
                                List flowIdList = getDemandFlowId(loanAccount);
                                Vector vtr = (Vector) flowIdList.get(0);
                                String prinFlowId = vtr.get(0).toString();
                                String intFlowId = vtr.get(1).toString();
                                String dmdDueDt = "", dmdAmount = "";
                                //For Loan EMI
                                List emiList;
                                double overDueAmt = 0d, intAmt = 0d;
                                double outSt = Double.parseDouble(loanIntCalcRemote.outStandingAsOnDate(loanAccount, generationDt));

                                if (loanAccount.substring(2, 4).equalsIgnoreCase("11") && bnkCode.equalsIgnoreCase("army")) {
                                    emiList = em.createNativeQuery("select ifnull(INSTALLAMT,0) as overDueAmt,ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from emidetails where acno='" + loanAccount + "' and status='Unpaid' and duedt<='" + ymd.format(dmy.parse(generationDt)) + "'").getResultList();
                                    if (!emiList.isEmpty()) {
                                        element = (Vector) emiList.get(0);
                                        dmdAmount = element.get(0).toString();
                                    }
                                    if (Double.parseDouble(dmdAmount) == 0) {
                                        String fromDt = loanIntCalcRemote.allFromDt(loanAccount.substring(2, 4), brncode, "f");
                                        String toDt = loanIntCalcRemote.allFromDt(loanAccount.substring(2, 4), brncode, "t");
                                        LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(fromDt, toDt, loanAccount, brncode);
                                        intAmt = it.getTotalInt() * -1;
                                    }
                                } else {
                                    emiList = em.createNativeQuery("select ifnull(installamt,0) as overDueAmt,ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from emidetails where acno='" + loanAccount + "' and status='Unpaid' and duedt<='" + ymd.format(dmy.parse(generationDt)) + "'").getResultList();
                                    if (emiList.isEmpty()) {
                                        emiList = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + loanAccount + "' and status='Unpaid' /*and duedt<='" + ymd.format(dmy.parse(generationDt)) + "'*/").getResultList();
                                    }
                                    element = (Vector) emiList.get(0);
                                    dmdAmount = element.get(0).toString();
                                    if (Double.parseDouble(dmdAmount) == 0) {
                                        if (Math.abs(outSt) > 0) {
                                            emiList = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                                    + "ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from emidetails "
                                                    + "where acno='" + loanAccount + "' and status='Unpaid' and "
                                                    + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                                    + "where acno='" + loanAccount + "' and status='Unpaid') ").getResultList();
                                        }
                                        element = (Vector) emiList.get(0);
                                        dmdAmount = element.get(0).toString();
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
                                        if (Double.parseDouble(dmdAmount) > Math.abs(outSt)) {
                                            dmdAmount = String.valueOf(Math.abs(outSt));
                                        } else {
                                            dmdAmount = dmdAmount;
                                        }
                                    }

                                    //For Over Due Amount new code
                                    if (bnkCode.equalsIgnoreCase("army")) {
                                        if (Math.abs(outSt) > 0) {
                                            List<OverDueListPojo> list = ddsRemote.getOverDueListData(loanAccount.substring(0, 2), loanAccount.substring(2, 4), ymd.format(dmy.parse(generationDt)), loanAccount);
                                            if (!list.isEmpty()) {
                                                overDueAmt = list.get(0).getOveDue().doubleValue();
                                            }
                                        }
                                    }
                                    //END For Over Due Amount new code
                                    String fromDt = loanIntCalcRemote.allFromDt(loanAccount.substring(2, 4), brncode, "f");
                                    String toDt = loanIntCalcRemote.allFromDt(loanAccount.substring(2, 4), brncode, "t");
                                    LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(fromDt, toDt, loanAccount, brncode);
                                    intAmt = it.getTotalInt() * -1;
                                }
                                double emiAmt = Double.parseDouble(dmdAmount) + intAmt + overDueAmt;

                                if (!String.valueOf(emiAmt).equals("0.0")) {
                                    dmdDueDt = element.get(1).toString();
                                    if (dmdDueDt.equalsIgnoreCase("")) {
                                        dmdDueDt = ymd.format(dmy.parse(generationDt));
                                    } else {
                                        if (ymd.parse(dmdDueDt).compareTo(dmy.parse(generationDt)) >= 0) {
                                            dmdDueDt = ymd.format(dmy.parse(dmdDueDt.substring(6, 8).concat(generationDt.substring(2, 10))));
                                        }
                                    }
                                    if (!createDemandHistory(loanAccount, prinFlowId, intFlowId, dmdDueDt, "M", new BigDecimal(Double.parseDouble(dmdAmount)), new BigDecimal(intAmt), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                        throw new ApplicationException("Problem In EMI Demand Generation.");
                                    }
                                }
                                if (emiAmt != 0) {
                                    row = sheet.createRow(rownum++);
                                    cell = row.createCell(0);
                                    cell.setCellValue(rownum - 1);

                                    cell = row.createCell(1);
                                    cell.setCellValue(customerId);

                                    cell = row.createCell(2);
                                    cell.setCellValue(loanAccount);
                                    cell = row.createCell(3);
                                    cell.setCellValue(String.valueOf(emiAmt));
                                    cell = row.createCell(4);
                                    cell.setCellValue("0.0");
                                    cell = row.createCell(5);
                                    cell.setCellValue("0.0");
                                    cell = row.createCell(6);
                                    cell.setCellValue(String.valueOf(emiAmt));
                                    cell = row.createCell(7);
                                    cell.setCellValue("");
                                    cell = row.createCell(8);
                                    cell.setCellValue("");
                                }
                            }
                        }

                        //All Active RD Account For A CustomerId of a particular barnch.
                        // if (!bnkIdentity.equalsIgnoreCase("RP")) {

                        List activeRdLst = em.createNativeQuery("select acno from accountmaster where accstatus <>9 and "
                                + "curbrcode='" + brncode + "' and accttype in (select acctcode from accounttypemaster "
                                + "where acctnature in('" + CbsConstant.RECURRING_AC + "')) and "
                                + "acno in(select acno from customerid where custid=" + customerId + ")").getResultList();
                        if (!activeRdLst.isEmpty()) {
                            for (int j = 0; j < activeRdLst.size(); j++) {           //Loan Account Loop
                                //Data Line Creation
                                //row = sheet.createRow(rownum++);

                                element = (Vector) activeRdLst.get(j);
                                String rdAccount = element.get(0).toString();
//                                cell = row.createCell(0);
//                                cell.setCellValue(rownum - 1);
//
//                                cell = row.createCell(1);
//                                cell.setCellValue(customerId);
//
//                                cell = row.createCell(2);
//                                cell.setCellValue(rdAccount);
//
//                                cell = row.createCell(3);
//                                cell.setCellValue("0.0");
//
//                                cell = row.createCell(4);

//                            List flowIdList = em.createNativeQuery("select principal_flow_id,int_demand_flow_id,disbursement_flow_id, collection_flow_id,penal_int_demand_flow_id, overdue_int_demand_flow_id, past_due_collection_flow_id,charge_demand_flow_id from cbs_scheme_loan_prepayment_details where scheme_code =  'RD001' ").getResultList();
//                            Vector vtr = (Vector) flowIdList.get(0);
//                            String rdFlowId = vtr.get(0).toString();
//                            String intFlowId = vtr.get(1).toString();

                                String rdDueDt = "", rdAmount = "";
                                //For RD Installment

                                List emiList = em.createNativeQuery("select ifnull((sum(installamt)),0) as overDueAmt,"
                                        + "date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment "
                                        + "where acno='" + rdAccount + "' and status='Unpaid' and "
                                        + "duedt<='" + ymd.format(dmy.parse(generationDt)) + "'").getResultList();

                                element = (Vector) emiList.get(0);
                                rdAmount = element.get(0).toString();
                                if (Double.parseDouble(rdAmount) == 0) {
                                    emiList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0),date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment "
                                            + "where acno='" + rdAccount + "' and sno=(select min(SNO) from rd_installment where acno = '" + rdAccount + "' and STATUS = 'Unpaid')").getResultList();
                                    element = (Vector) emiList.get(0);
                                    rdAmount = element.get(0).toString();
                                }

                                //cell.setCellValue(rdAmount);
                                if (!rdAmount.equals("0.0")) {
                                    rdDueDt = element.get(1).toString();
                                    if (rdDueDt.equalsIgnoreCase("")) {
                                        rdDueDt = ymd.format(dmy.parse(generationDt));
                                    } else {
                                        if (ymd.parse(rdDueDt).compareTo(dmy.parse(generationDt)) >= 0) {
                                            rdDueDt = ymd.format(dmy.parse(rdDueDt.substring(6, 8).concat(generationDt.substring(2, 10))));
                                        }
                                    }
                                    rdDueDt = element.get(1).toString();
                                    if (!createDemandHistory(rdAccount, "RDDEM", "", rdDueDt, "M", new BigDecimal(rdAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                        throw new ApplicationException("Problem In EMI Demand Generation.");
                                    }
                                    row = sheet.createRow(rownum++);
                                    cell = row.createCell(0);
                                    cell.setCellValue(rownum - 1);

                                    cell = row.createCell(1);
                                    cell.setCellValue(customerId);

                                    cell = row.createCell(2);
                                    cell.setCellValue(rdAccount);

                                    cell = row.createCell(3);
                                    cell.setCellValue("0.0");

                                    cell = row.createCell(4);
                                    cell.setCellValue(rdAmount);
                                    cell = row.createCell(5);
                                    cell.setCellValue("0.0");
                                    cell = row.createCell(6);
                                    cell.setCellValue(rdAmount);
                                    cell = row.createCell(7);
                                    cell.setCellValue("");
                                    cell = row.createCell(8);
                                    cell.setCellValue("");

                                }
                            }
                        }
                        if (bnkCode.equalsIgnoreCase("army")) {
                            //New Code All Active Theft Account For A CustomerId of a particular barnch.
                            List activeTFList = em.createNativeQuery("select acno from accountmaster where accstatus <>9 and curbrcode='" + brncode + "' "
                                    + "and accttype in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "') and accttype ='TF')"
                                    + "and acno in(select acno from customerid where custid=" + customerId + ")").getResultList();

                            if (!activeTFList.isEmpty()) {
                                for (int j = 0; j < activeTFList.size(); j++) {           //Theft Account Loop
                                    //Data Line Creation
                                    row = sheet.createRow(rownum++);

                                    element = (Vector) activeTFList.get(j);
                                    String thAccount = element.get(0).toString();
                                    cell = row.createCell(0);
                                    cell.setCellValue(rownum - 1);

                                    cell = row.createCell(1);
                                    cell.setCellValue(customerId);

                                    cell = row.createCell(2);
                                    cell.setCellValue(thAccount);

                                    cell = row.createCell(3);
                                    cell.setCellValue("0.0");

                                    cell = row.createCell(4);
                                    cell.setCellValue("0.0");
                                    cell = row.createCell(5);

//                            List flowIdList = getDemandFlowId(thAccount);
//                            Vector vtr = (Vector) flowIdList.get(0);
//                            String threftFlowId = vtr.get(0).toString();
//                            String intFlowId = vtr.get(1).toString();

                                    String thDueDt = "", thAmount = "0.0";
                                    //For Theft EMI
                                    List emiList = em.createNativeQuery("select ifnull(RdInstal,0) as overDueAmt from accountmaster where acno  = '" + thAccount + "'").getResultList();
                                    if (!emiList.isEmpty()) {
                                        element = (Vector) emiList.get(0);
                                        thAmount = element.get(0).toString();
                                        cell.setCellValue(thAmount);

                                        if (!thAmount.equals("0.0")) {
                                            if (thDueDt.equalsIgnoreCase("")) {
                                                thDueDt = ymd.format(dmy.parse(generationDt));
                                            }
                                            if (!createDemandHistory(thAccount, "THDEM", "", thDueDt, "M", new BigDecimal(thAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                                throw new ApplicationException("Problem In EMI Demand Generation.");
                                            }
                                        }
                                    }

                                    cell = row.createCell(6);
                                    cell.setCellValue(thAmount);
                                    cell = row.createCell(7);
                                    cell.setCellValue("");
                                    cell = row.createCell(8);
                                    cell.setCellValue("");
                                }
                            }
                            //End New Code All Active Theft Account For A CustomerId of a particular barnch.
                        } else {
                            /*New code for Thrift fund & Welfare Fund*/
                            List activeTFList = em.createNativeQuery("select acno,substring(acno,3,2) acType from accountmaster where accstatus <>9 and curbrcode='" + brncode + "' "
                                    + "and accttype in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "') and accttype ='TF')"
                                    + "and acno in(select acno from customerid where custid=" + customerId + ") "
                                    + "union "
                                    + "select Regfoliono acno,substring(Regfoliono,3,2) acType from share_holder where custid = '" + customerId + "'").getResultList();

                            if (!activeTFList.isEmpty()) {
                                for (int j = 0; j < activeTFList.size(); j++) {           //Theft Account Loop
                                    //Data Line Creation
                                    row = sheet.createRow(rownum++);

                                    element = (Vector) activeTFList.get(j);
                                    String thAccount = element.get(0).toString();
                                    String acType = element.get(1).toString();

                                    cell = row.createCell(0);
                                    cell.setCellValue(rownum - 1);

                                    cell = row.createCell(1);
                                    cell.setCellValue(customerId);

                                    cell = row.createCell(2);
                                    cell.setCellValue(thAccount);

                                    cell = row.createCell(3);
                                    cell.setCellValue("0.0");

                                    cell = row.createCell(4);
                                    cell.setCellValue("0.0");
                                    cell = row.createCell(5);

//                            List flowIdList = getDemandFlowId(thAccount);
//                            Vector vtr = (Vector) flowIdList.get(0);
//                            String threftFlowId = vtr.get(0).toString();
//                            String intFlowId = vtr.get(1).toString();

                                    String thDueDt = "", thAmount = "0.0";
                                    if (acType.equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                                        memberNo = thAccount;
                                        //For Theft EMI
                                        List emiList = em.createNativeQuery("select charges from parameterinfo_miscincome where Purpose = 'WELFARE_FUND' and Acctcode ='" + CbsAcCodeConstant.SF_AC.getAcctCode() + "'").getResultList();
                                        if (!emiList.isEmpty()) {
                                            element = (Vector) emiList.get(0);
                                            thAmount = element.get(0).toString();
                                            cell.setCellValue(thAmount);

                                            if (!thAmount.equals("0.0")) {
                                                if (thDueDt.equalsIgnoreCase("")) {
                                                    thDueDt = ymd.format(dmy.parse(generationDt));
                                                }
                                                if (!createDemandHistory(memberNo, "PWELL", "", thDueDt, "M", new BigDecimal(thAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                                    throw new ApplicationException("Problem In EMI Demand Generation.");
                                                }
                                            }
                                        }
                                    } else {
                                        //For Theft EMI
                                        List emiList = em.createNativeQuery("select ifnull(RdInstal,0) as overDueAmt from accountmaster where acno  = '" + thAccount + "'").getResultList();
                                        if (!emiList.isEmpty()) {
                                            element = (Vector) emiList.get(0);
                                            thAmount = element.get(0).toString();
                                            cell.setCellValue(thAmount);

                                            if (!thAmount.equals("0.0")) {
                                                if (thDueDt.equalsIgnoreCase("")) {
                                                    thDueDt = ymd.format(dmy.parse(generationDt));
                                                }
                                                if (!createDemandHistory(thAccount, "THDEM", "", thDueDt, "M", new BigDecimal(thAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                                    throw new ApplicationException("Problem In EMI Demand Generation.");
                                                }
                                            }
                                        }
                                    }
                                    cell = row.createCell(6);
                                    cell.setCellValue(thAmount);
                                    cell = row.createCell(7);
                                    cell.setCellValue("");
                                    cell = row.createCell(8);
                                    cell.setCellValue("");
                                }
                            }
                            /*END New code for Thrift fund & Welfare Fund*/
                        }
                        /*For Member Well Fare Fund. It will be only one time in a year 
                         based on member.*/
//                        row = sheet.createRow(rownum++);
//                        cell = row.createCell(0);
//                        cell.setCellValue(rownum - 1);
//                        cell = row.createCell(1);
//                        cell.setCellValue(customerId);
//                        cell = row.createCell(2);
//                        cell.setCellValue(memberNo);
//                        cell = row.createCell(3);
//                        cell.setCellValue("0.0");
//                        cell = row.createCell(4);
//                        cell.setCellValue("0.0");

//                        String[] arr = getFYearRangeForGenerationDate(generationDt);
//                        List paidList = em.createNativeQuery("select * from insurance_details where acno='" + memberNo + "' and due_dt "
//                                + "between '" + arr[0] + "' and '" + arr[1] + "' and status='PAID'").getResultList();
//                        //cell = row.createCell(5);
//                        if (!paidList.isEmpty()) {
//                            //cell.setCellValue("0.0");
//                        } else {
//                            //For Member Well Fare Fund.
//                            List list = em.createNativeQuery("select ifnull((sum(insured_amount)-sum(excess_amt)),0) as overDueAmt,"
//                                    + "date_format(max(due_dt),'%Y%m%d') as maxDueDt from insurance_details "
//                                    + "where acno='" + memberNo + "' and status='UNPAID' "
//                                    + "and due_dt<='" + arr[1] + "'").getResultList();
//
//                            element = (Vector) list.get(0);
//                            String dmdAmount = element.get(0).toString();
//                            //cell.setCellValue(dmdAmount);
//                            if (!dmdAmount.equals("0.0")) {
//                                String dmdDueDt = element.get(1).toString();
//                                if (!createDemandHistory(memberNo, "PWELL", "", dmdDueDt, "Y", new BigDecimal(dmdAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
//                                    throw new ApplicationException("Problem In Well Fare Demand Generation.");
//                                }
//                                row = sheet.createRow(rownum++);
//                                cell = row.createCell(0);
//                                cell.setCellValue(rownum - 1);
//                                cell = row.createCell(1);
//                                cell.setCellValue(customerId);
//                                cell = row.createCell(2);
//                                cell.setCellValue(memberNo);
//                                cell = row.createCell(3);
//                                cell.setCellValue("0.0");
//                                cell = row.createCell(4);
//                                cell.setCellValue("0.0");
//                                cell = row.createCell(5);
//                                cell.setCellValue(dmdAmount);
//
//                                cell = row.createCell(6);
//                                cell.setCellValue(dmdAmount);
//                                cell = row.createCell(7);
//                                cell.setCellValue("");
//                                cell = row.createCell(8);
//                                cell.setCellValue("");
//
//                            }
//                        }
                        //End of well fare fund.
                        // }
                    }
                }
                //Write the workbook in file system
                String absFilePath = writeFolderPath + placeOfWorking + ".xls";
                FileOutputStream out = new FileOutputStream(new File(absFilePath));
                wb.write(out);
                out.close();
                fileName = absFilePath.substring(absFilePath.lastIndexOf("/") + 1);
            }
            //New code Insert in table cbs_loan_dmd_info
            int srlInsert = em.createNativeQuery("INSERT INTO cbs_loan_dmd_info (SNO, ACNO, FLAG, BRNCODE, POSTINGDT, FROMDT, TODT,OFFICEID) "
                    + "VALUES "
                    + "(" + srlNo + ", '', 'A', '" + brncode + "', curdate(), '" + firstDt + "', '" + ymd.format(dmy.parse(generationDt)) + "','" + placeOfWorking + "')").executeUpdate();
            if (srlInsert <= 0) {
                throw new ApplicationException("Problem In Insertion.");
            }
            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public String generateExcelFileBasedOnPlaceOfWorkingPostal(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String fileName = "";
        try {
            ut.begin();
            //Generation of folder where we write the file.
            String writeFolderPath = "";
            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/POSTAL/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/POSTAL/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }
            //Get max DMD_SRL_NUM no.

            List maxList = em.createNativeQuery("select ifnull(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
            Vector srlVector = (Vector) maxList.get(0);
            Integer srlNo = (Integer.parseInt(srlVector.get(0).toString()));
            // Get Premium Code
            String premiumCode = "";
            List premiumList = em.createNativeQuery("select Acctcode from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM'").getResultList();
            if (!premiumList.isEmpty()) {
                Vector vtr = (Vector) premiumList.get(0);
                premiumCode = vtr.get(0).toString();
            }
            String premiumDt = ftsPosting.getCodeFromCbsParameterInfo("LIP_PREMIUM_DT");
            //Get data for placeofworking.
            List dataList = em.createNativeQuery("select cast(custid as unsigned ),regfoliono from share_holder where "
                    + "area='" + placeOfWorking + "' order by cast(custid as unsigned)").getResultList();
            if (!dataList.isEmpty()) {
                //Generating Excel File.
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("DEMAND");

                //Header Line
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue("SL NO.");
                cell = row.createCell(1);
                cell.setCellValue("ID NO.");
                cell = row.createCell(2);
                cell.setCellValue("ACNO");
                cell = row.createCell(3);
                cell.setCellValue("LOAN-EMI");
                cell = row.createCell(4);
                cell.setCellValue("LIP-AMOUNT");
                cell = row.createCell(5);
                cell.setCellValue("WELFARE-FUND");
                cell = row.createCell(6);
                cell.setCellValue("AMOUNT");
                cell = row.createCell(7);
                cell.setCellValue("CHEQUE-NO");
                cell = row.createCell(8);
                cell.setCellValue("CHEQUE-DATE");
                int rownum = 1;
                List checkList = new ArrayList();
                for (int i = 0; i < dataList.size(); i++) {                     //CustomerId Loop
                    Vector element = (Vector) dataList.get(i);
                    String customerId = element.get(0).toString();
                    String memberNo = element.get(1).toString();
                    List schNoList = em.createNativeQuery("select ifnull(max(shdl_num),0)+1 as sno from cbs_loan_dmd_table where DMD_SRL_NUM='" + srlNo + "' ").getResultList();
                    Vector schNoVect = (Vector) schNoList.get(0);
                    int schNo = Integer.parseInt(schNoVect.get(0).toString());
                    //All Active Loan Account For A CustomerId of a particular barnch.
                    List activeList = em.createNativeQuery("select a.acno,sum(r.cramt-r.dramt) from accountmaster a,loan_recon r where a.accstatus <>9 "
                            + "and a.curbrcode='" + brncode + "' and a.accttype in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "')) "
                            + "and a.acno in(select acno from customerid where custid= " + customerId + ")and a.acno=r.acno and dt<='" + ymd.format(dmy.parse(generationDt)) + "' "
                            + "group by r.acno having sum(r.cramt-r.dramt)<> 0 order by a.acno").getResultList();
                    if (!activeList.isEmpty()) {
                        for (int j = 0; j < activeList.size(); j++) {           //Loan Account Loop
                            //Data Line Creation
                            row = sheet.createRow(rownum++);

                            element = (Vector) activeList.get(j);
                            String loanAccount = element.get(0).toString();
                            checkList.add(loanAccount);
                            double outSt = Double.parseDouble(element.get(1).toString());
                            cell = row.createCell(0);
                            cell.setCellValue(rownum - 1);

                            cell = row.createCell(1);
                            cell.setCellValue(customerId);

                            cell = row.createCell(2);
                            cell.setCellValue(loanAccount);
                            cell = row.createCell(3);
                            //Get Demand flow Id
                            List flowIdList = getDemandFlowId(loanAccount);
                            Vector vtr = (Vector) flowIdList.get(0);
                            String prinFlowId = vtr.get(0).toString();
                            String intFlowId = vtr.get(1).toString();
                            String dmdDueDt = "", dmdAmount = "";
                            //For Loan EMI
                            List emiList = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                    + "ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from emidetails "
                                    + "where acno='" + loanAccount + "' and status='Unpaid' and "
                                    + "duedt<='" + ymd.format(dmy.parse(generationDt)) + "'").getResultList();

                            element = (Vector) emiList.get(0);
                            dmdAmount = element.get(0).toString();
                            if (Double.parseDouble(dmdAmount) == 0) {
                                if (Math.abs(outSt) > 0) {
                                    emiList = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                            + "ifnull(date_format(max(duedt),'%Y%m%d'),'') as maxDueDt from emidetails "
                                            + "where acno='" + loanAccount + "' and status='Unpaid' and "
                                            + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                            + "where acno='" + loanAccount + "' and status='Unpaid') ").getResultList();
                                }
                                element = (Vector) emiList.get(0);
                                dmdAmount = element.get(0).toString();
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
                                if (Double.parseDouble(dmdAmount) > Math.abs(outSt)) {
                                    dmdAmount = String.valueOf(Math.abs(outSt));
                                } else {
                                    dmdAmount = dmdAmount;
                                }
                            }

                            String fromDt = loanIntCalcRemote.allFromDt(loanAccount.substring(2, 4), brncode, "f");
                            String toDt = loanIntCalcRemote.allFromDt(loanAccount.substring(2, 4), brncode, "t");

                            LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(fromDt, toDt, loanAccount, brncode);
                            double intAmt = it.getTotalInt() * -1;
                            double emiAmt = Double.parseDouble(dmdAmount) + intAmt;

                            cell.setCellValue(String.valueOf(emiAmt));
                            if (!String.valueOf(emiAmt).equals("0.0")) {
                                dmdDueDt = element.get(1).toString();
                                if (dmdDueDt.equalsIgnoreCase("")) {
                                    dmdDueDt = ymd.format(dmy.parse(generationDt));
                                } else {
                                    if (ymd.parse(dmdDueDt).compareTo(dmy.parse(generationDt)) >= 0) {
                                        dmdDueDt = ymd.format(dmy.parse(dmdDueDt.substring(6, 8).concat(generationDt.substring(2, 10))));
                                    }
                                }
                                if (!createDemandHistory(loanAccount, prinFlowId, intFlowId, dmdDueDt, "M", new BigDecimal(Double.parseDouble(dmdAmount)), new BigDecimal(intAmt), enterBy, placeOfWorking, toDt, srlNo, schNo)) {
                                    throw new ApplicationException("Problem In EMI Demand Generation.");
                                }
                            }

                            cell = row.createCell(4);
                            cell.setCellValue("0.0");
                            cell = row.createCell(5);
                            cell.setCellValue("0.0");
                            cell = row.createCell(6);
                            cell.setCellValue("0.0");
                            cell = row.createCell(7);
                            cell.setCellValue("");
                            cell = row.createCell(8);
                            cell.setCellValue("");
                        }
                    }

                    //All Insurance Premium
                    double custBal = loanReportRemote.getCustIdWiseBal(brncode, customerId, ymd.format(dmy.parse(generationDt)));
                    double custBal1 = loanReportRemote.getCustIdWiseBal(brncode, customerId, premiumDt);
                    if (custBal != 0) {
                        List premiumLst = em.createNativeQuery("select a.acno from accountmaster a,customerid b where custid = '" + customerId + "' and a.acno=b.acno and substring(a.acno,3,2) in('" + premiumCode + "','31') and (a.closingdate='' or ifnull(a.closingdate,'')='' or a.closingdate >'" + ymd.format(dmy.parse(generationDt)) + "') and curbrcode = '" + brncode + "'").getResultList();
                        if (!premiumLst.isEmpty()) {
                            for (int j = 0; j < premiumLst.size(); j++) {           //Loan Account Loop
                                //Data Line Creation
                                row = sheet.createRow(rownum++);

                                element = (Vector) premiumLst.get(j);
                                String premiumAccount = element.get(0).toString();
                                checkList.add(premiumAccount);
                                cell = row.createCell(0);
                                cell.setCellValue(rownum - 1);

                                cell = row.createCell(1);
                                cell.setCellValue(customerId);

                                cell = row.createCell(2);
                                cell.setCellValue(premiumAccount);

                                cell = row.createCell(3);
                                cell.setCellValue("0.0");

                                cell = row.createCell(4);

                                String rdDueDt = "", premiumAmount = "0.0";
                                //For Premium insurance 
                                if (premiumAccount.substring(2, 4).equalsIgnoreCase(premiumCode)) {
                                    if (custBal1 != 0) {
                                        List emiList = em.createNativeQuery("select ifnull(charges,0) as premiumAmt from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM' and Acctcode ='" + premiumCode + "'").getResultList();
                                        element = (Vector) emiList.get(0);
                                        premiumAmount = element.get(0).toString();
                                    }
                                    cell.setCellValue(premiumAmount);
                                    if (!premiumAmount.equals("0.0")) {
                                        if (!createDemandHistory(premiumAccount, "PINSP", "", ymd.format(dmy.parse(generationDt)), "M", new BigDecimal(premiumAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                            throw new ApplicationException("Problem In EMI Demand Generation.");
                                        }
                                    }
                                } else {
                                    cell.setCellValue(premiumAmount);
                                    if (!createDemandHistory(premiumAccount, "PINSP", "", ymd.format(dmy.parse(generationDt)), "M", new BigDecimal(premiumAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                        throw new ApplicationException("Problem In EMI Demand Generation.");
                                    }
                                }


                                cell = row.createCell(5);
                                cell.setCellValue("0.0");
                                cell = row.createCell(6);
                                cell.setCellValue("0.0");
                                cell = row.createCell(7);
                                cell.setCellValue("");
                                cell = row.createCell(8);
                                cell.setCellValue("");
                            }
                        }
                    }
                    if (custBal != 0) {
                        /*For Member Well Fare Fund. It will be only one time in a year 
                         based on member.*/
                        row = sheet.createRow(rownum++);
                        cell = row.createCell(0);
                        cell.setCellValue(rownum - 1);
                        cell = row.createCell(1);
                        cell.setCellValue(customerId);
                        cell = row.createCell(2);
                        cell.setCellValue(memberNo);
                        cell = row.createCell(3);
                        cell.setCellValue("0.0");
                        cell = row.createCell(4);
                        cell.setCellValue("0.0");
                        checkList.add(memberNo);
                        String[] arr = getFYearRangeForGenerationDate(generationDt);
                        List paidList = em.createNativeQuery("select * from insurance_details where acno='" + memberNo + "' and due_dt "
                                + "between '" + arr[0] + "' and '" + arr[1] + "' and status='PAID'").getResultList();
                        cell = row.createCell(5);
                        if (!paidList.isEmpty()) {
                            cell.setCellValue("0.0");
                        } else {
                            //For Member Well Fare Fund.
                            List list = em.createNativeQuery("select charges from parameterinfo_miscincome where Purpose = 'WELFARE_FUND' and Acctcode ='" + CbsAcCodeConstant.SF_AC.getAcctCode() + "'").getResultList();
                            element = (Vector) list.get(0);
                            String dmdAmount = element.get(0).toString();
                            cell.setCellValue(dmdAmount);
                            if (!dmdAmount.equals("0.0")) {
                                // String dmdDueDt = element.get(1).toString();
                                if (!createDemandHistory(memberNo, "PWELL", "", ymd.format(dmy.parse(generationDt)), "Y", new BigDecimal(dmdAmount), new BigDecimal(0), enterBy, placeOfWorking, ymd.format(dmy.parse(generationDt)), srlNo, schNo)) {
                                    throw new ApplicationException("Problem In Well Fare Demand Generation.");
                                }
                            }
                        }

                        cell = row.createCell(6);
                        cell.setCellValue("0.0");
                        cell = row.createCell(7);
                        cell.setCellValue("");
                        cell = row.createCell(8);
                        cell.setCellValue("");
                        //End of well fare fund.
                    }
                }
                //Write the workbook in file system
                if (checkList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                String absFilePath = writeFolderPath + placeOfWorking + ".xls";
                FileOutputStream out = new FileOutputStream(new File(absFilePath));
                wb.write(out);
                out.close();
                fileName = absFilePath.substring(absFilePath.lastIndexOf("/") + 1);
            }
            //New code Insert in table cbs_loan_dmd_info
            int srlInsert = em.createNativeQuery("INSERT INTO cbs_loan_dmd_info (SNO, ACNO, FLAG, BRNCODE, POSTINGDT, FROMDT, TODT,OFFICEID) "
                    + "VALUES "
                    + "(" + srlNo + ", '', 'A', '" + brncode + "', curdate(), '" + firstDt + "', '" + ymd.format(dmy.parse(generationDt)) + "','" + placeOfWorking + "')").executeUpdate();
            if (srlInsert <= 0) {
                throw new ApplicationException("Problem In Insertion.");
            }
            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public boolean createDemandHistory(String loanAccount, String dmdFlowId, String intdmdFlowId, String dmdDueDt, String frequency, BigDecimal dmdAmount, BigDecimal idmdAmount, String enterBy, String checkRollArea, String tillDate, Integer srlNo, Integer schNo) {
        boolean result = true;
//        List emiList = em.createNativeQuery("select ifnull(max(shdl_num),0)+1 as sno from cbs_loan_dmd_table where DMD_SRL_NUM='" + srlNo + "' ").getResultList();
//        Vector element = (Vector) emiList.get(0);
//        int sno = Integer.parseInt(element.get(0).toString());
        BigDecimal emiAmt = dmdAmount.add(idmdAmount);
        try {
            if (loanAccount.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                int insertSno = em.createNativeQuery("INSERT INTO cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,"
                        + "dmd_date,dmd_srl_num,del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,"
                        + "rcre_user_id,rcre_time,ei_amt,"
                        + "ts_cnt,latefee_status_flg,check_roll_area) VALUES('" + loanAccount + "'," + schNo + ",'" + dmdFlowId + "',"
                        + "'" + ymd.format(date) + "'," + srlNo + ",'N','" + frequency + "','" + dmdDueDt + "','" + CbsUtil.dateAdd(dmdDueDt, 1) + "',"
                        + dmdAmount + ",'" + enterBy + "',now(),"
                        + dmdAmount + ",0,'N','" + checkRollArea + "')").executeUpdate();
                if (insertSno <= 0) {
                    result = false;
                }
            } else {
                if (!(ftsPosting.getAccountNature(loanAccount).equalsIgnoreCase(CbsConstant.TERM_LOAN))) {

                    int insertSno = em.createNativeQuery("INSERT INTO cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,"
                            + "dmd_date,dmd_srl_num,del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,"
                            + "rcre_user_id,rcre_time,ei_amt,"
                            + "ts_cnt,latefee_status_flg,check_roll_area) VALUES('" + loanAccount + "'," + schNo + ",'" + dmdFlowId + "',"
                            + "'" + ymd.format(date) + "'," + srlNo + ",'N','" + frequency + "','" + dmdDueDt + "','" + CbsUtil.dateAdd(dmdDueDt, 1) + "',"
                            + dmdAmount + ",'" + enterBy + "',now(),"
                            + dmdAmount + ",0,'N','" + checkRollArea + "')").executeUpdate();
                    if (insertSno <= 0) {
                        result = false;
                    }

                } else {
                    int insertSno1 = em.createNativeQuery("INSERT INTO cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, "
                            + "LD_FREQ_TYPE,DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, RCRE_USER_ID, RCRE_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG, "
                            + "CHECK_ROLL_AREA) VALUES ('" + loanAccount + "', " + schNo + ", '" + dmdFlowId + "', '" + ymd.format(date) + "', " + srlNo + ", 'N', '" + frequency + "', '" + dmdDueDt + "', '" + CbsUtil.dateAdd(dmdDueDt, 1) + "'," + dmdAmount + ", '" + enterBy + "', "
                            + " now(), " + emiAmt + ", 0, 'N', '" + checkRollArea + "')").executeUpdate();

                    if (insertSno1 <= 0) {
                        result = false;
                    }

                    int insertSno2 = em.createNativeQuery("INSERT INTO cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, "
                            + "LD_FREQ_TYPE,DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, RCRE_USER_ID, RCRE_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG, "
                            + "CHECK_ROLL_AREA) VALUES ('" + loanAccount + "', " + schNo + ", '" + intdmdFlowId + "', '" + ymd.format(date) + "', " + srlNo + ", 'N', '" + frequency + "', '" + tillDate + "', '" + CbsUtil.dateAdd(tillDate, 1) + "'," + idmdAmount + ", '" + enterBy + "', "
                            + " now(), " + emiAmt + ", 0, 'N', '" + checkRollArea + "')").executeUpdate();
                    if (insertSno2 <= 0) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }

//    public boolean post(String acctType, String acno, String user, String date, String frDt, String toDt, String orgnCode) throws ApplicationException {
//        boolean result = true;
//        String message = "";
//        Integer sno = null;
//        Integer dmdSrl = null;
//        String accountNo = null;
//        double installamt;
//        double prinamt;
//        double interestamt;
//        String dmdPFlag = null;
//        String dmdIFlag;
//        String lgFreq = "M";
//        String ovduDate = null;
//        Integer ShdlNo = null;
//        double elAmt;
//        String dueDate;
//
//        List emiList = new ArrayList();
//        try {
//            List loanList = em.createNativeQuery("select ifnull(max(Sno),0) from cbs_loan_dmd_info").getResultList();
//            if (loanList.size() > 0) {
//                Vector ele = (Vector) loanList.get(0);
//                sno = Integer.parseInt(ele.get(0).toString());
//                if (sno == 0) {
//                    sno = 1;
//                } else {
//                    sno = sno + 1;
//                }
//            }
//            List loanDList = em.createNativeQuery("select dmd_srl_num from cbs_loan_dmd_table  where DMD_DATE = '" + date + "'").getResultList();
//            if (loanDList.size() > 0) {
//                Vector ele = (Vector) loanDList.get(0);
//                dmdSrl = Integer.parseInt(ele.get(0).toString());
//            } else {
//                List loanDMList = em.createNativeQuery("select ifnull(max(dmd_srl_num),0) from cbs_loan_dmd_table ").getResultList();
//                if (loanDMList.size() > 0) {
//                    Vector ele = (Vector) loanDMList.get(0);
//                    dmdSrl = Integer.parseInt(ele.get(0).toString()) + 1;
//                }
//            }
////            if (acctType.equalsIgnoreCase("A")) {
////                emiList = em.createNativeQuery("select a.acno,ifnull(a.installamt,0),"
////                        + "ifnull(a.prinamt,0),ifnull(a.interestamt,0),a.duedt, b.accstatus from "
////                        + "emidetails a,accountmaster b where a.acno = b.acno and b.accstatus <>9 "
////                        + "and a.status = 'unpaid' and a.duedt <= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
////                        + "where acno=a.acno and status='Unpaid')  "
////                        + "and a.acno=b.acno and b.curBrcode = '" + orgnCode + "' "
////                        + "order by a.acno").getResultList();
////                if (emiList.isEmpty()) {
////                    resultList.add("EMI Schedule does not exists Between " + sdf.format(ymd.parse(frDt)) + " to " + sdf.format(ymd.parse(toDt)));
////                    return resultList;
////                }
////
////                List loanDemandList = em.createNativeQuery("select * from cbs_loan_dmd_info where FromDT = '" + frDt + "' and ToDT = "
////                        + "'" + toDt + "' and flag = 'A'").getResultList();
////                if (!loanDemandList.isEmpty()) {
////                    resultList.add("Demand for this period already Posted");
////                    return resultList;
////                }
////                Integer demandInfoList = em.createNativeQuery("Insert into cbs_loan_dmd_info(sno,acno,flag,brnCode,postingDt,FromDT,ToDT) Values"
////                        + "(" + sno + ",'','" + acctType + "','" + orgnCode + "',now(),'" + frDt + "','" + toDt + "')").executeUpdate();
////                if (demandInfoList < 0) {
////                    resultList.add("Data is not inserted into cbs_loan_demand_info");
////                    return resultList;
////                }
////            } else 
//                if (acctType.equalsIgnoreCase("S")) {
//                emiList = em.createNativeQuery("select a.acno,ifnull(a.installamt,0),"
//                        + "ifnull(a.prinamt,0),ifnull(a.interestamt,0),a.duedt, b.accstatus "
//                        + "from emidetails a,accountmaster b where a.acno = b.acno and b.accstatus"
//                        + " <>9 and a.status = 'unpaid' and a.duedt a.duedt <= (select date_format(min(duedt),'%Y%m%d')  from emidetails " 
//                        + " where acno=a.acno and status='Unpaid')"
//                        + "and a.acno = b.acno and b.curBrcode = '" + orgnCode + "' and a.acno = '" + acno + "'  order "
//                        + "by a.acno").getResultList();
//                if (emiList.isEmpty()) {
//                    throw new ApplicationException("EMI Schedule does not exists Between " + dmy.format(ymd.parse(frDt)) + " to " + dmy.format(ymd.parse(toDt)));                    
//                }
//
//                List loanDemandList = em.createNativeQuery("select * from cbs_loan_dmd_info where FromDT = '" + frDt + "' and ToDT = "
//                        + "'" + toDt + "' and flag = 'A'").getResultList();
//                if (!loanDemandList.isEmpty()) {
//                    throw new ApplicationException("Demand for this period already Posted");
//                }
//                List loanDemanList = em.createNativeQuery("select * from cbs_loan_dmd_info where postingDt = '" + date + "'  and acno = '" + acno + "' and flag = 'S'").getResultList();
//                if (!loanDemanList.isEmpty()) {
//                    throw new ApplicationException("Demand for this period already Posted againt this Account No.");
//                }
//
//                Integer demandInfoList = em.createNativeQuery("Insert into cbs_loan_dmd_info(sno,acno,flag,brnCode,postingDt,FromDT,ToDT) Values"
//                        + "(" + sno + ",'" + acno + "','" + acctType + "','" + orgnCode + "','" + date + "','" + frDt + "','" + toDt + "')").executeUpdate();
//                if (demandInfoList < 0) {
//                    throw new ApplicationException("Data is not inserted into cbs_loan_demand_info");
//                }
//            }
//            String schemeCode = null;
//            for (int i = 0; i < emiList.size(); i++) {
//                Vector ele = (Vector) emiList.get(i);
////                if (acctType.equalsIgnoreCase("A")) {
////                    accountNo = "";
////                } else if (acctType.equalsIgnoreCase("S")) {
////                    accountNo = ele.get(0).toString();
////                }
//
//                accountNo = ele.get(0).toString();
//                List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from "
//                        + "cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND "
//                        + "A.ACNO ='" + accountNo + "'").getResultList();
//                if (SecDetailsList.isEmpty()) {
//                    throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
//                } else {
//                    for (int j = 0; j < SecDetailsList.size(); j++) {
//                        Vector SecDetailsVect = (Vector) SecDetailsList.get(j);
//                        schemeCode = (String) SecDetailsVect.get(1);
//                    }
//                }
//                List flowDetailList = em.createNativeQuery("select ei_flow_id, principal_flow_id,"
//                        + " disbursement_flow_id, collection_flow_id, int_demand_flow_id, "
//                        + "penal_int_demand_flow_id, overdue_int_demand_flow_id, past_due_collection_flow_id,"
//                        + " charge_demand_flow_id from cbs_scheme_loan_prepayment_details where "
//                        + "scheme_code =  '" + schemeCode + "'").getResultList();
//                String eiFlowId = null;
//                String prinDemFlowId = null;
//                String disbFlowId;
//                String colFlowId = null;
//                String intDemFlowId = null;
//                String penalIntDemFlowId = null;
//                String overdueIntFlowId;
//                String pastDueColNPAFlowId = null;
//                String chgDemFlowId = null;
//                double overFlowAmt = 0;
//
//                if (flowDetailList.isEmpty()) {
//                    throw new ApplicationException("Flow Id Does Not Exists in Scheme Master.");                    
//                } else {
//                    for (int k = 0; k < flowDetailList.size(); k++) {
//                        Vector flowDetailVect = (Vector) flowDetailList.get(k);
//                        eiFlowId = flowDetailVect.get(0).toString();
//                        prinDemFlowId = flowDetailVect.get(1).toString();
//                        disbFlowId = flowDetailVect.get(2).toString();
//                        colFlowId = flowDetailVect.get(3).toString();
//                        intDemFlowId = flowDetailVect.get(4).toString();
//                        penalIntDemFlowId = flowDetailVect.get(5).toString();
//                        overdueIntFlowId = flowDetailVect.get(6).toString();
//                        pastDueColNPAFlowId = flowDetailVect.get(7).toString();
//                        chgDemFlowId = flowDetailVect.get(8).toString();
//
//                    }
//                }
//                String fromDt = loanIntCalcRemote.allFromDt(accountNo.substring(2, 4), orgnCode, "f");
//                String tooDt = loanIntCalcRemote.allFromDt(accountNo.substring(2, 4), orgnCode, "t");
//
//                LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(frDt, toDt, accountNo, orgnCode);
//                double intAmt = it.getTotalInt() * -1;
//
//                installamt = Double.parseDouble(ele.get(1).toString());
//                prinamt = Double.parseDouble(ele.get(2).toString());
//                interestamt = Double.parseDouble(ele.get(3).toString()) != 0 ? Double.parseDouble(ele.get(3).toString()) : intAmt;
//                dueDate = ele.get(4).toString();
//                if ((prinamt + interestamt) == prinamt) {
//                    elAmt = (float) 0;
//                } else if ((prinamt + interestamt) == interestamt) {
//                    elAmt = (float) 0;
//                } else {
//                    elAmt = prinamt + interestamt;
//                }
//
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(yMMd.parse(dueDate));
//                cal.add(cal.DATE, 1);
//                String overdueDt = ymd.format(cal.getTime());
//                //  System.out.println("Acno: "+accountNo+",dueDate: "+dueDate+"overdueDt:"+overdueDt+";");
////                List convertList = em.createNativeQuery("select convert(datetime,DateAdd(d, 1,'" + date + "'),103)").getResultList();
////                if (convertList.size() > 0) {
////                    Vector secVec = (Vector) convertList.get(0);
////                    ovduDate = secVec.get(0).toString();
////                }
//
//                List snoList = em.createNativeQuery("select ifnull(max(SHDL_NUM),0) from cbs_loan_dmd_table where DMD_SRL_NUM = "+sno).getResultList();
//
//                if (snoList.size() > 0) {
//                    Vector secVec = (Vector) snoList.get(0);
//                    ShdlNo = Integer.parseInt(secVec.get(0).toString());
//                    if (ShdlNo == 0) {
//                        ShdlNo = 1;
//                    } else {
//                        ShdlNo = ShdlNo + 1;
//                    }
//                }
//
////                List mastSecList = em.createNativeQuery("select calc_method from  cbs_loan_acc_mast_sec where acno = '" + accountNo + "'").getResultList();
////                if (mastSecList.size() > 0) {
////                    Vector secVec = (Vector) mastSecList.get(0);
////                    lgFreq = secVec.get(0).toString();
////                }
//
////                List loanDmdTableList = em.createNativeQuery("select * from cbs_loan_dmd_table where acno = '" + accountNo + "' and DMD_DATE = '" + date + "'").getResultList();
////                if (loanDmdTableList.isEmpty()) {
//                if (acctType.equalsIgnoreCase("A")) {
//                    if (prinamt != 0) {
//                        Integer loanDmdList = em.createNativeQuery("insert into cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,dmd_date,"
//                                + "del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,rcre_user_id,"
//                                + "rcre_time,ei_amt,ts_cnt,latefee_status_flg,dmd_srl_num) Values"
//                                + "('" + accountNo + "'," + ShdlNo + ",'" + prinDemFlowId + "','" + date + "','N','" + lgFreq + "','" + dueDate + "','" + overdueDt + "'," + prinamt + ","
//                                + "'" + user + "',now()," + elAmt + ",0,'N'," + sno + ")").executeUpdate();
//                        if (loanDmdList < 0) {
//                            throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");
//                        }
//                    }
//                    if (interestamt != 0) {
//
//                        Integer loanDmdList = em.createNativeQuery("insert into cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,dmd_date,"
//                                + "del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,rcre_user_id,"
//                                + "rcre_time,ei_amt,ts_cnt,latefee_status_flg,dmd_srl_num) values"
//                                + "('" + accountNo + "'," + ShdlNo + ",'" + intDemFlowId + "','" + date + "','N','" + lgFreq + "','" + dueDate + "','" + overdueDt + "'," + interestamt + ","
//                                + "'" + user + "',now()," + elAmt + ",0,'N'," + sno + ")").executeUpdate();
//                        if (loanDmdList < 0) {
//                            throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");
//                        }
//                    }
//                }
////                } else {
//                    if (acctType.equalsIgnoreCase("S")) {
//                        throw new ApplicationException("Demand for this period already Posted againt this Account No.");
//                    }
////                }
//
//            }            
//            
//        } catch (Exception e) {
//            try {
//                e.printStackTrace();
//                throw new ApplicationException(e);
//            } catch (IllegalStateException ex) {
//                throw new ApplicationException(ex);
//            } catch (SecurityException ex) {
//                throw new ApplicationException(ex);
//            } catch (Exception ea) {
//                throw new ApplicationException(ea);
//            }
//        }
//        return result;
//    }
    public String[] getFYearRangeForGenerationDate(String generationDt) throws ApplicationException {
        String[] arr = new String[2];
        try {
            String diffYear = "", firstDt = "", lastDt = "";
            String genMonth = generationDt.substring(3, 5);
            if (Integer.parseInt(genMonth) >= 4 && Integer.parseInt(genMonth) <= 12) {
                firstDt = generationDt.substring(6) + "0401";
                diffYear = CbsUtil.yearAdd(ymd.format(dmy.parse(generationDt)), 1);
                lastDt = diffYear.substring(0, 4) + "0331";
            } else {
                diffYear = CbsUtil.yearAdd(ymd.format(dmy.parse(generationDt)), -1);
                firstDt = diffYear.substring(0, 4) + "0401";
                lastDt = generationDt.substring(6) + "0331";
            }
            arr[0] = firstDt;
            arr[1] = lastDt;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }

    public String getShdlNo(String acNo) throws ApplicationException {
        String shNo = "0";
        try {
            List shdleList = em.createNativeQuery("select max(SHDL_NUM) from cbs_loan_dmd_table  where ACNO = '" + acNo + "' and SHDL_NUM =(select max(SHDL_NUM) from cbs_loan_dmd_table where acno = '" + acNo + "')").getResultList();
            Vector ele = (Vector) shdleList.get(0);
            shNo = ele.get(0).toString();
        } catch (Exception e) {
            e.getMessage();
        }
        return shNo;
    }

    public String processBulkRecovery(List<BulkRecoveryPojo> dataList, String orgnBrCode, String useName, String todayDate, String place) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", branchPremiumHead = "", branchWellFareHead = "";
        Integer payBy = 0;
        boolean remoteChq = false;
        List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
        try {
            ut.begin();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to process recovery.");
            }
            String[] arr = checkExcelBatch(dataList);
            if (arr[0].equals("false")) {
                throw new ApplicationException("In batch dr and cr is not equal in excel file.");
            }
            todayDate = ymd.format(dmy.parse(todayDate));
            String drAccount = arr[1].trim();
            String drAmount = arr[2].trim();
            String chqNo = arr[3].trim();
            String chqDt = arr[4].trim();
            //Chque Related Manipulation.
            if (chqNo == null || chqNo.equals("")) {
                payBy = 3;
                chqDt = "19000101";
            } else {
                if (chqDt == null || chqDt.equals("")) {
                    throw new ApplicationException("Please fill cheque date for dr amount in DD/MM/YYYY format in excel file.");
                }
                payBy = 1;
                chqDt = ymd.format(dmy.parse(chqDt));
            }
            String srNo = "", flag = "", toDt = "", valueDt = "", prinFlowId = "";
            int gracePdDays = 0;
            // new Code
            List dmdInfoList = getFileGenDt(place);
            if (!dmdInfoList.isEmpty()) {
                Vector dmdVector = (Vector) dmdInfoList.get(0);
                srNo = dmdVector.get(0).toString();
                flag = dmdVector.get(1).toString();
                toDt = dmdVector.get(2).toString();
            }
            String acctCode = armyInterestPostParameter();
            String bnkCode = ftsPosting.getBankCode();
            if (Double.parseDouble(drAmount) != 0 && !drAccount.equals("")) {              //Normal Transfer Case
                //Dr A/c validation
                result = validateDrAccount(drAccount);
                if (!result.equals("true")) {
                    throw new ApplicationException(result);
                }
                //Making flag to true if it is inter branch batch. Otherwise it will be false always.
                if (!drAccount.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
                    remoteChq = true;
                }
                //Generating common batch no.
                Float trsno = ftsPosting.getTrsNo();
                //Getting branch level Premium and WellFare Head.
                if (!ftsPosting.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("RP")) {
                    branchPremiumHead = orgnBrCode + getAccountByPurpose("PREMIUM-HEAD");
                    branchWellFareHead = orgnBrCode + getAccountByPurpose("WELFARE-HEAD");
                }
                if (bnkCode.equalsIgnoreCase("krbi")) {
                    branchWellFareHead = orgnBrCode + getAccountByPurpose("WELFARE-HEAD");
                }

                //Dr Account

                if (remoteChq == true) {
                    //Calling of SX
                    result = interBranchFacade.cbsPostingSx(drAccount, 1, ymd.format(new Date()), Double.parseDouble(drAmount), 0.0, 2, "Bulk Recovery Dr:", 0f, "", chqNo, chqDt, payBy, 0f, ftsPosting.getRecNo(), 67, drAccount.substring(0, 2), orgnBrCode, useName, useName, trsno, "", "");
                } else {
//                    valueDt = getValueDt(srNo, toDt);
                    result = performGeneralTransaction(drAccount, 1, 2, chqNo, Double.parseDouble(drAmount), trsno, useName, orgnBrCode, "", "", chqDt, payBy, useName, ymd.format(new Date()));
                }
                if (!result.toLowerCase().contains("true")) {
                    throw new ApplicationException(result);
                }
                //For Cr Accounts. 
                double totalNpaAmt = 0d;
                for (BulkRecoveryPojo obj : dataList) {
                    if (!obj.getAcno().equalsIgnoreCase(drAccount)) {
                        result = validateCrAccount(obj.getAcno(), orgnBrCode);
                        if (!result.equals("true")) {
                            throw new ApplicationException(result);
                        }
                        //Get Demand flow Id
                        System.out.println(obj.getAcno());
                        if (!obj.getAcno().substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                            if (ftsPosting.getAccountNature(obj.getAcno()).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                List flowIdList = getDemandFlowId(obj.getAcno());
                                Vector vtr = (Vector) flowIdList.get(0);
                                prinFlowId = vtr.get(0).toString();
                            }
                        }
                        //Different coming amounts-emi,premium,wellfare transactions
                        if (obj.getLoanEmi() != null && Double.parseDouble(obj.getLoanEmi()) != 0) {
                            double intAmt = 0d, prinAmt = 0d;
                            if (remoteChq == true) {
                                //Calling of CX for Loan A/c
                                result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), 0.0, 2, "", 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                            } else {
                                String paidSno = "";
                                result = ftsPosting.loanDisbursementInstallment(obj.getAcno(), Double.parseDouble(obj.getLoanEmi()), 0, useName, ymd.format(new Date()), trsno, 1, "Demand Recovery");
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                System.out.println("PLOAN Result is->" + result);
                                paidSno = result.substring(result.indexOf(":") + 1);
                                valueDt = getValueDt(obj.getAcno(), toDt);
                                result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getLoanEmi()), trsno, useName, orgnBrCode, paidSno, "", "19000101", 3, useName, valueDt);
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                            /*New Code Npa Recovery*/
                            String status = interBranchFacade.fnGetLoanStatus(obj.getAcno(), ymd.format(new Date()));
                            if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                //npaRecoveryUpdation
                                totalNpaAmt = totalNpaAmt + getNpaRecoveryAmt(obj.getAcno(), ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), status);
                                String msgRecAmt = bulkNpaRecoveryUpdation(trsno, ftsPosting.getAccountNature(obj.getAcno()), obj.getAcno(), ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), orgnBrCode, orgnBrCode, useName, valueDt, bnkCode);
                                if (!msgRecAmt.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(msgRecAmt);
                                }
                            }

                            /*End Of New Code Npa Recovery*/
                        }
                        if (obj.getInsPremium() != null && Double.parseDouble(obj.getInsPremium()) != 0) {
                            String paidSno = "";
                            result = satisfyRecovery("RDDEM", obj.getAcno(), Double.parseDouble(obj.getInsPremium()), useName, bnkCode);
                            if (!result.contains("true")) {
                                throw new ApplicationException(result);
                            }
                            System.out.println("PINSP Result is->" + result);
                            paidSno = result.substring(result.indexOf(":") + 1);
                            if (remoteChq == true) {
                                //Calling of CX for BranchPremiumHead A/c
                                result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getInsPremium()), 0.0, 2, paidSno + "::" + obj.getAcno(), 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                            } else {
                                valueDt = ymd.format(new Date());
                                result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getInsPremium()), trsno, useName, orgnBrCode, paidSno, obj.getAcno(), "19000101", 3, useName, valueDt);
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }

                            List getDmdList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_srl_num,dmd_eff_date,dmd_ovdu_date,dmd_amt,last_adj_date,ifnull(tot_adj_amt,0) as tot_adj_amt, ifnull(ei_amt,0) as ei_amt from cbs_loan_dmd_table  where acno = '" + obj.getAcno() + "' and dmd_flow_id = 'RDDEM' and DMD_SRL_NUM =" + srNo + "  order by acno, dmd_eff_date, shdl_num, dmd_srl_num").getResultList();
                            if (getDmdList.size() > 0) {
                                for (int i = 0; i < getDmdList.size(); i++) {
                                    Vector getDmdVect = (Vector) getDmdList.get(i);
                                    String dmdAcNo = getDmdVect.get(0).toString();
                                    int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                    String dmdFlowId = getDmdVect.get(2).toString();
                                    int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                                    Date dmdEffDt = yMMd.parse(getDmdVect.get(4).toString());
                                    double dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());

                                    double dmdAdjAmt = Double.parseDouble(getDmdVect.get(8).toString());
                                    List tsCntList = em.createNativeQuery("select ifnull(ts_cnt,0)+1 from cbs_loan_dmd_table where acno = '" + obj.getAcno() + "' and shdl_num = " + dmdSchNo + " and dmd_flow_id = 'RDDEM' and del_flg = 'N' and dmd_srl_num = " + dmdSrNo).getResultList();
                                    Vector tsCntVector = (Vector) tsCntList.get(0);
                                    int tsCnt = Integer.parseInt(tsCntVector.get(0).toString());

                                    List srNoList = em.createNativeQuery("select ifnull(max(cast(srl_num  as unsigned)),0)+1, ifnull(max(cast(part_tran_srl_num as unsigned)),0)+1 from cbs_loan_dmd_adj_table").getResultList();
                                    Vector srNoVector = (Vector) srNoList.get(0);
                                    int srlNo = Integer.parseInt(srNoVector.get(0).toString());
                                    int partTrSrNo = Integer.parseInt(srNoVector.get(1).toString());

                                    int updateDmdTable = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET  LAST_ADJ_DATE = curdate(), TOT_ADJ_AMT = " + Double.parseDouble(obj.getInsPremium()) + ", RCRE_USER_ID = '" + useName + "' WHERE ACNO = '" + obj.getAcno() + "' and DMD_SRL_NUM =" + srNo + " ").executeUpdate();
                                    if (updateDmdTable <= 0) {
                                        throw new ApplicationException("Problem in updation");
                                    }
                                    Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                            + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'RDDEM','" + ymd.format(dmdEffDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + todayDate + "'," + dmdAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                    Integer insertQry = insertQuery.executeUpdate();
                                    if (insertQry <= 0) {
                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                    }
                                }
                            }
                        }

                        if (obj.getAcno().substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                            if (obj.getWellFare() != null && Double.parseDouble(obj.getWellFare()) != 0) {
                                String paidSno = "";
                                result = satisfyRecovery("PWELL", obj.getAcno(), Double.parseDouble(obj.getWellFare()), useName, bnkCode);
                                if (!result.contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                System.out.println("PWELL Result is->" + result);
                                paidSno = result.substring(result.indexOf(":") + 1);
                                if (remoteChq == true) {
                                    //Calling of CX for BranchWellFareHead A/c
                                    result = interBranchFacade.cbsPostingCx(branchWellFareHead, 0, ymd.format(new Date()), Double.parseDouble(obj.getWellFare()), 0.0, 2, paidSno + "::" + obj.getAcno(), 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                                } else {
                                    valueDt = ymd.format(new Date());
                                    result = performGeneralTransaction(branchWellFareHead, 0, 2, "", Double.parseDouble(obj.getWellFare()), trsno, useName, orgnBrCode, paidSno, obj.getAcno(), "19000101", 3, useName, valueDt);
                                }
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }

                                int updateDmdTable = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET  LAST_ADJ_DATE = curdate(), TOT_ADJ_AMT = " + Double.parseDouble(obj.getWellFare()) + ", RCRE_USER_ID = '" + useName + "' WHERE ACNO = '" + obj.getAcno() + "' and DMD_SRL_NUM =" + srNo + " ").executeUpdate();
                                if (updateDmdTable <= 0) {
                                    throw new ApplicationException("Problem in updation");
                                }
                            }
                        } else {
                            /*THREFT ACCOUNT RECOVERY HANDLING*/
                            if (obj.getWellFare() != null && Double.parseDouble(obj.getWellFare()) != 0 && ftsPosting.getAcctTypeByCode(obj.getAcno().substring(2, 4)).equalsIgnoreCase("Tf")) {
                                String paidSno = "";
                                double totalAdjAmt = Double.parseDouble(obj.getWellFare());
                                if (remoteChq == true) {
                                    //Calling of CX for BranchWellFareHead A/c
                                    result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getWellFare()), 0.0, 2, paidSno + "::" + obj.getAcno(), 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                                } else {
                                    valueDt = ymd.format(new Date());
                                    result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getWellFare()), trsno, useName, orgnBrCode, paidSno, obj.getAcno(), "19000101", 3, useName, valueDt);
                                }
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                if (totalAdjAmt > 0) {
                                    List getDmdList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_srl_num,dmd_eff_date,dmd_ovdu_date,dmd_amt,last_adj_date,ifnull(tot_adj_amt,0) as tot_adj_amt, ifnull(ei_amt,0) as ei_amt, dmd_date from cbs_loan_dmd_table  where acno = '" + obj.getAcno() + "' and dmd_flow_id = 'THDEM' and dmd_eff_date <= '" + todayDate + "' and del_flg = 'N' and latefee_status_flg in ('N','L','U') and (dmd_amt-ifnull(tot_adj_amt,0) ) > 0  order by acno, dmd_eff_date, shdl_num, dmd_srl_num").getResultList();
                                    if (getDmdList.size() > 0) {
                                        for (int i = 0; i < getDmdList.size(); i++) {
                                            Vector getDmdVect = (Vector) getDmdList.get(i);
                                            String dmdAcNo = getDmdVect.get(0).toString();
                                            int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                            String dmdFlowId = getDmdVect.get(2).toString();
                                            int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                                            Date dmdEffDt = yMMd.parse(getDmdVect.get(4).toString());
                                            double dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());

                                            double dmdAdjAmt = Double.parseDouble(getDmdVect.get(8).toString());
                                            Date dmdDt = yMMd.parse(getDmdVect.get(10).toString());
                                            List tsCntList = em.createNativeQuery("select ifnull(ts_cnt,0)+1 from cbs_loan_dmd_table where acno = '" + obj.getAcno() + "' and shdl_num = " + dmdSchNo + " and dmd_flow_id = 'THDEM' and del_flg = 'N' and dmd_srl_num = " + dmdSrNo).getResultList();
                                            Vector tsCntVector = (Vector) tsCntList.get(0);
                                            int tsCnt = Integer.parseInt(tsCntVector.get(0).toString());

                                            List srNoList = em.createNativeQuery("select ifnull(max(cast(srl_num  as unsigned)),0)+1, ifnull(max(cast(part_tran_srl_num as unsigned)),0)+1 from cbs_loan_dmd_adj_table").getResultList();
                                            Vector srNoVector = (Vector) srNoList.get(0);
                                            int srlNo = Integer.parseInt(srNoVector.get(0).toString());
                                            int partTrSrNo = Integer.parseInt(srNoVector.get(1).toString());

                                            Query updateQuery = null;
                                            Query insertQuery = null;
                                            if (dmdAmt > totalAdjAmt) {
                                                if (ymd.parse(todayDate).after(dmdDt)) {
                                                    updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + todayDate + "', LCHG_USER_ID = '" + useName + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + obj.getAcno() + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
                                                             * +"
                                                             * and
                                                             * LATEFEE_STATUS_FLG
                                                             * IN
                                                             * ('N','L')"
                                                             */);
                                                    Integer updtQuery = updateQuery.executeUpdate();
                                                    if (updtQuery <= 0) {
                                                        throw new ApplicationException("538_Updation problem in CBS_LOAN_DMD_TABLE" + dmdFlowId);
                                                    } else {
//                                                                    if (i == getDmdList.size() - 1) {
                                                        insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME,  TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srlNo + "','N', '" + todayDate + "'," + totalAdjAmt + ", '" + useName + "',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                                        Integer insertQry = insertQuery.executeUpdate();
                                                        if (insertQry <= 0) {
                                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                        }
//                                                                    }
                                                    }
                                                } else if (ymd.parse(todayDate).before(dmdDt) || (ymd.parse(todayDate).equals(dmdDt))) {
                                                    updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'U', LAST_ADJ_DATE = '" + todayDate + "', LCHG_USER_ID = '" + useName + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + obj.getAcno() + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
                                                             * +"
                                                             * and
                                                             * LATEFEE_STATUS_FLG
                                                             * IN
                                                             * ('N','L')"
                                                             */);
                                                    Integer updtQuery = updateQuery.executeUpdate();
                                                    if (updtQuery <= 0) {
                                                        throw new ApplicationException("538_Updation Problem of CBS_LOAN_DMD_TABLE" + dmdFlowId);
                                                    } else {
//                                                                    if (i == getDmdList.size() - 1) {
                                                        insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srlNo + "','N', '" + todayDate + "'," + totalAdjAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                                        Integer insertQry = insertQuery.executeUpdate();
                                                        if (insertQry <= 0) {
                                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                        }
//                                                                    }
                                                    }
                                                }
                                                totalAdjAmt = totalAdjAmt - dmdAmt;
                                            } else if (dmdAmt <= totalAdjAmt) {
                                                if (ymd.parse(todayDate).after(dmdDt)) {
                                                    updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + todayDate + "', LCHG_USER_ID = '" + useName + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + obj.getAcno() + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
                                                             * +"
                                                             * and
                                                             * LATEFEE_STATUS_FLG
                                                             * IN
                                                             * ('N','L')"
                                                             */);
                                                    Integer updtQuery = updateQuery.executeUpdate();
                                                    if (updtQuery <= 0) {
                                                        throw new ApplicationException("538_Updation Problem of CBS_LOAN_DMD_TABLE" + dmdFlowId);
                                                    } else {
//                                                                    if (i == getDmdList.size() - 1) {
                                                        insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdEffDt) + "'," + dmdSrNo + ",'" + srlNo + "','N', '" + todayDate + "'," + dmdAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                                        Integer insertQry = insertQuery.executeUpdate();
                                                        if (insertQry <= 0) {
                                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                        }
//                                                                    }
                                                    }
                                                } else if (ymd.parse(todayDate).before(dmdDt) || (ymd.parse(todayDate).equals(dmdDt))) {
                                                    updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'S', LAST_ADJ_DATE = '" + todayDate + "', LCHG_USER_ID = '" + useName + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + obj.getAcno() + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
                                                             * +"
                                                             * and
                                                             * LATEFEE_STATUS_FLG
                                                             * IN
                                                             * ('N','L')"
                                                             */);
                                                    Integer updtQuery = updateQuery.executeUpdate();
                                                    if (updtQuery <= 0) {
                                                        throw new ApplicationException("538_Updation Problem of CBS_LOAN_DMD_TABLE" + dmdFlowId);
                                                    } else {
//                                                                    if (i == getDmdList.size() - 1) {
                                                        insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srlNo + "','N', '" + todayDate + "'," + dmdAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                                        Integer insertQry = insertQuery.executeUpdate();
                                                        if (insertQry <= 0) {
                                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                        }
//                                                                    }
                                                    }
                                                }
                                                totalAdjAmt = totalAdjAmt - dmdAmt;
                                            }
                                        }
                                    }
                                }

                            }
                            //End New Code 
                        }
                    }
                    //Creating SMS List.
                    if (Double.parseDouble(obj.getAmount()) != 0) {
                        SmsToBatchTo to = new SmsToBatchTo();
                        to.setAcNo(obj.getAcno());
                        to.setCrAmt(Double.parseDouble(obj.getAmount()));
                        to.setDrAmt(0.0);
                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                        to.setTranType(2);
                        to.setTxnDt(dmy.format(ymd.parse(todayDate)));
                        to.setTy(0);
                        smsList.add(to);
                    }
                }

                /* GlHead Entry of Npa Recoery*/
                if (totalNpaAmt != 0) {
                    List list = em.createNativeQuery("SELECT distinct GLHEADINT,ifnull(GLHEADURI,''),ifnull(glheadname,'') FROM accounttypemaster WHERE acctnature = '" + CbsConstant.TERM_LOAN + "' and ACCTCODE in(" + acctCode + ")").getResultList();
                    Vector element = (Vector) list.get(0);
                    String intGl = element.get(0).toString();
                    String uriGl = element.get(1).toString();
                    String oirHead = element.get(2).toString();
                    // String accNature = ftsPosting.getAccountNature(acno);
                    String msg = "";
                    if (intGl.equals("")) {
                        return "THE INTEREST GL HEAD NOT EXIST";
                    }

                    float recNo = ftsPosting.getRecNo();
                    String valueDate = toDt;
                    String interestAcno = orgnBrCode + intGl + "01";
                    Query q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                            + "('" + interestAcno + "',0.0,'" + ymd.format(new Date()) + "','" + valueDate + "',0,ABS(" + totalNpaAmt + "),0,2," + recNo + "," + trsno + ","
                            + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + useName + "','" + orgnBrCode + "','" + orgnBrCode + "','','')");
                    q4.executeUpdate();
                    msg = ftsPosting.updateBalance("PO", orgnBrCode + intGl + "01", Math.abs(totalNpaAmt), 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }

                    if (!oirHead.equals("") && !uriGl.equals("")) {
                        recNo = ftsPosting.getRecNo();
                        String oHead = orgnBrCode + oirHead + "01";
                        q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                + "('" + oHead + "',0.0,'" + ymd.format(new Date()) + "','" + valueDate + "',ABS(" + totalNpaAmt + "),0,1,2," + recNo + "," + trsno + ","
                                + "3,0,3,'Int. Recovered from Npa Acno',CURRENT_TIMESTAMP,'Y','SYSTEM','" + useName + "','" + orgnBrCode + "','" + orgnBrCode + "','','')");
                        q4.executeUpdate();
                        msg = ftsPosting.updateBalance("PO", orgnBrCode + oirHead + "01", 0, Math.abs(totalNpaAmt), "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }
                        recNo = ftsPosting.getRecNo();
                        oHead = orgnBrCode + uriGl + "01";
                        q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                + "('" + oHead + "',0.0,'" + ymd.format(new Date()) + "','" + valueDate + "',0,ABS(" + totalNpaAmt + "),0,2," + recNo + "," + trsno + ","
                                + "3,0,3,'Int. Recovered from Npa Acno',CURRENT_TIMESTAMP,'Y','SYSTEM','" + useName + "','" + orgnBrCode + "','" + orgnBrCode + "','','')");
                        q4.executeUpdate();

                        msg = ftsPosting.updateBalance("PO", orgnBrCode + uriGl + "01", Math.abs(totalNpaAmt), 0, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }
                    }
                }
                /* End Of GlHead Entry of Npa Recoery*/


            } else if (Double.parseDouble(drAmount) != 0 && drAccount.equals("")) {        //Sundry Case
                //Check whether register is open on excel uploading date.
                List list = em.createNativeQuery("select emflag,date_format(postingdate,'%Y%m%d') as postDt,"
                        + "date_format(clearingdate,'%Y%m%d') as clgDt from clg_ow_register where "
                        + "entrydate='" + ymd.format(dmy.parse(todayDate)) + "' and brncode='" + orgnBrCode + "' "
                        + "and status='OPEN'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Register is not open on date: " + todayDate);
                }
                Vector element = (Vector) list.get(0);
                String emFlag = element.get(0).toString();
//                String postDt = element.get(1).toString();
//                String clgDt = element.get(2).toString();

                //Get current financial year for login branch.
                Integer fyear = Integer.parseInt(getFinYearForBranch(orgnBrCode));

                //Get max seq no for above coming financial year.
                list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 as maxseq from bill_sundry "
                        + "where fyear = " + fyear + "").getResultList();
                element = (Vector) list.get(0);
                Double maxSeqNo = Double.parseDouble(element.get(0).toString());
                //Generating the branch sundry a/c.
                String sundryHead = orgnBrCode + getAccountByPurpose("SUNDRY-CREDITORS");
                //Inserting the entry into branch sundry a/c for total dr amount.
                Integer insertResult = em.createNativeQuery("INSERT INTO bill_sundry(fyear,seqno,instno,acno,details,amount,dt,"
                        + "origindt,status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,recno,lastupdateby) "
                        + "VALUES(" + fyear + "," + maxSeqNo + ",'','" + sundryHead + "',''," + drAmount + ","
                        + "'" + ymd.format(dmy.parse(todayDate)) + "','" + ymd.format(dmy.parse(todayDate)) + "',"
                        + "'ISSUED',0,1,0,'" + useName + "','','Y','" + useName + "',CURRENT_TIMESTAMP,"
                        + "" + ftsPosting.getRecNo() + ",'')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Problem in sundry head account insertion: " + sundryHead);
                }
                //Insert sundry a/c clg_ow_entry table.
                insertResult = em.createNativeQuery("INSERT INTO clg_ow_entry(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,"
                        + "Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,AreaCode,BnkCode,BranchCode,remarks,ReasonForCancel,"
                        + "vtot,EMFlag,EnterBy,AuthBy,BillType,AlphaCode,BcBpNo,Fyear,IY,OBCFLAG,TranDesc,dt,DrwAcno,DrwName,"
                        + "TerminalId,VerifiedBy,Sbal,orgnbrcode,destbrcode) "
                        + "VALUES('" + sundryHead + "',3,0,'" + ymd.format(dmy.parse(todayDate)) + "','" + chqNo + "',"
                        + "" + drAmount + ",'E','" + chqDt + "','" + getBankCode().toUpperCase() + "','" + getBankCode().toUpperCase() + "',"
                        + "'000','000','000','',0,1,'" + emFlag + "','" + useName + "','','','','" + maxSeqNo.toString() + "',"
                        + "'" + fyear.toString() + "',11,'',0,'" + ymd.format(dmy.parse(todayDate)) + "','N.A.','POSTAL','','',"
                        + "'','" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Problem in sundry head account insertion in clg ow entry: " + sundryHead);
                }
                //Insert all loan a/c in bill_sundry_dt table.
                for (BulkRecoveryPojo obj : dataList) {
                    if (obj.getAmount() == null || obj.getAmount().equals("") || Double.parseDouble(obj.getAmount()) == 0.0) {
                        if (obj.getLoanEmi() != null && Double.parseDouble(obj.getLoanEmi()) != 0) {
                            insertResult = em.createNativeQuery("INSERT INTO bill_sundry_dt(fyear,seqno,instno,acno,details,"
                                    + "amount,dt,origindt,status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,"
                                    + "recno,lastupdateby) VALUES(" + fyear + "," + maxSeqNo + ",'','" + obj.getAcno() + "','PLOAN',"
                                    + "" + Double.parseDouble(obj.getLoanEmi()) + ",'" + ymd.format(dmy.parse(todayDate)) + "',"
                                    + "'" + ymd.format(dmy.parse(todayDate)) + "','ISSUED',0,1,1,'" + useName + "','','Y',"
                                    + "'" + useName + "',CURRENT_TIMESTAMP," + ftsPosting.getRecNo() + ",'')").executeUpdate();
                        }
                        if (obj.getInsPremium() != null && Double.parseDouble(obj.getInsPremium()) != 0) {
                            insertResult = em.createNativeQuery("INSERT INTO bill_sundry_dt(fyear,seqno,instno,acno,details,"
                                    + "amount,dt,origindt,status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,"
                                    + "recno,lastupdateby) VALUES(" + fyear + "," + maxSeqNo + ",'','" + obj.getAcno() + "','P-RD',"
                                    + "" + Double.parseDouble(obj.getInsPremium()) + ",'" + ymd.format(dmy.parse(todayDate)) + "',"
                                    + "'" + ymd.format(dmy.parse(todayDate)) + "','ISSUED',0,1,1,'" + useName + "','','Y',"
                                    + "'" + useName + "',CURRENT_TIMESTAMP," + ftsPosting.getRecNo() + ",'')").executeUpdate();
                        }
                        if (obj.getWellFare() != null && Double.parseDouble(obj.getWellFare()) != 0) {
                            insertResult = em.createNativeQuery("INSERT INTO bill_sundry_dt(fyear,seqno,instno,acno,details,"
                                    + "amount,dt,origindt,status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,"
                                    + "recno,lastupdateby) VALUES(" + fyear + "," + maxSeqNo + ",'','" + obj.getAcno() + "','PWELL',"
                                    + "" + Double.parseDouble(obj.getWellFare()) + ",'" + ymd.format(dmy.parse(todayDate)) + "',"
                                    + "'" + ymd.format(dmy.parse(todayDate)) + "','ISSUED',0,1,1,'" + useName + "','','Y',"
                                    + "'" + useName + "',CURRENT_TIMESTAMP," + ftsPosting.getRecNo() + ",'')").executeUpdate();
                        }
                        if (insertResult <= 0) {
                            throw new ApplicationException("Problem in loan against sundry head account insertion: " + sundryHead);
                        }
                    }
                }
            }

            int updateDmdInfo = em.createNativeQuery("update cbs_loan_dmd_info set FLAG = 'R',RECOVERYDT = curdate() where SNO = " + srNo).executeUpdate();
            if (updateDmdInfo <= 0) {
                throw new ApplicationException("Problem in updation");
            }
            ut.commit();
            try {
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception ex) {
                System.out.println("Problem in sending the sms in loan demand recovery.");
            }
            result = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    public double getNpaRecoveryAmt(String acno, String dt, double crAmt, String accStatus) throws ApplicationException {
        try {
            double glAmt = 0d;
            if (accStatus.equals("SUB") || accStatus.equals("DOU") || accStatus.equals("LOS")) {
                List list2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)),0)-ifnull(SUM(ifnull(DRAMT,0)),0) FROM npa_recon WHERE ACNO='" + acno + "' AND DT<='" + dt + "'").getResultList();
                Vector element2 = (Vector) list2.get(0);
                double mem = Double.parseDouble(element2.get(0).toString());
                if (Math.abs(mem) <= (crAmt) && mem <= 0) {
                    if (mem < 0) {
                        glAmt = mem;
                    }
                } else if (Math.abs(mem) > (crAmt) && mem < 0) {
                    glAmt = crAmt;
                }
            }
            return glAmt;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String bulkNpaRecoveryUpdation(float trsNumber, String nature, String acNo, String curDt, double amount, String brCode, String toBrCode, String authBy, String valueDt, String bnkCode) throws ApplicationException {
        try {
            if (nature.equals(CbsConstant.TERM_LOAN) || nature.equals(CbsConstant.DEMAND_LOAN) || nature.equals(CbsConstant.CURRENT_AC)) {
                /* Dont remove this code and remove comment after confirmation from basti*/
                String status = interBranchFacade.fnGetLoanStatus(acNo, curDt);
                if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                    BigDecimal currentBal = new BigDecimal(ftsPosting.ftsGetBal(acNo));
                    String result = "";
                    if (interBranchFacade.npaPOrdChk(acNo).equalsIgnoreCase("true")) {
                        if (bnkCode.equalsIgnoreCase("army")) {
                            result = bulknpaRecoveryAmtUpdation(acNo, curDt, amount, authBy, brCode, toBrCode, valueDt, trsNumber);
                        } else {
                            result = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, amount, authBy, brCode, toBrCode, valueDt, trsNumber);
                        }
                        if (!result.equalsIgnoreCase("True")) {
                            throw new ApplicationException(result);
                        }
                    } else if (interBranchFacade.npaPOrdChk(acNo).equalsIgnoreCase("false")) {
                        double totInstallAmt = 0;
                        if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            /**/
                            List crAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  as cramt from " + common.getTableName(nature) + " where acno =  '" + acNo + "' and dt<='" + curDt + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
                            Vector crAmtVect = (Vector) crAmtList.get(0);
                            double totCrAmt = Double.parseDouble(crAmtVect.get(0).toString());

                            List drAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  as dramt from " + common.getTableName(nature) + " where acno =  '" + acNo + "' and dt<='" + curDt + "'  and auth = 'Y' and trandesc in (3,4,8) and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
                            Vector drAmtVect = (Vector) drAmtList.get(0);
                            double totIntDrAmt = Double.parseDouble(drAmtVect.get(0).toString());

                            List installAmtList = em.createNativeQuery("select * from emidetails where acno = '" + acNo + "' and duedt>'" + curDt + "'").getResultList();
                            if (installAmtList.isEmpty()) {
//                                installAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  as dramt from " + common.getTableName(nature) + " where acno =  '" + acNo + "' and dt<='" + curDt + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
//                                Vector instllAmtVect = (Vector) installAmtList.get(0);
//                                totInstallAmt = Double.parseDouble(instllAmtVect.get(0).toString());
                                if ((currentBal.doubleValue()) > 0) {
                                    String msg = "";
                                    if (bnkCode.equalsIgnoreCase("army")) {
                                        msg = bulknpaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, valueDt, trsNumber);
                                    } else {
                                        msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, valueDt, trsNumber);
                                    }
                                    if (!msg.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(msg);
                                    }
                                }
                            } else {
                                installAmtList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno = '" + acNo + "' and duedt<='" + curDt + "'").getResultList();
                                Vector instllAmtVect = (Vector) installAmtList.get(0);
                                totInstallAmt = Double.parseDouble(instllAmtVect.get(0).toString());

                                double remainPriAmt = totInstallAmt - totIntDrAmt;
                                if ((remainPriAmt > 0) && (remainPriAmt <= totCrAmt)) {
                                    String msg = "";
                                    if (bnkCode.equalsIgnoreCase("army")) {
                                        msg = bulknpaRecoveryAmtUpdation(acNo, curDt, amount, authBy, brCode, toBrCode, valueDt, trsNumber);
                                    } else {
                                        msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, amount, authBy, brCode, toBrCode, valueDt, trsNumber);
                                    }
                                    if (!msg.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(msg);
                                    }

                                }
                            }
                        } else {
                            if ((currentBal.doubleValue()) > 0) {
                                String msg = "";
                                if (bnkCode.equalsIgnoreCase("army")) {
                                    msg = bulknpaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, valueDt, trsNumber);
                                } else {
                                    msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, valueDt, trsNumber);
                                }
                                if (!msg.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(msg);
                                }
                            }
                        }
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String bulknpaRecoveryAmtUpdation(String acno, String dt, double crAmt, String enterBy, String orgnBrCode, String destBrCode, String valueDate, float trsNo) throws ApplicationException {
        try {
            /**
             * ***********************************************************************************
             * urihead(GLHEADURI) means Interest Accrued Due On NPA
             * oirHead(glheadname) means Overdue Interest Reserve
             * ************************************************************************************
             */
            String accStatus = interBranchFacade.fnGetLoanStatus(acno, dt);
            String accode = ftsPosting.getAccountCode(acno);
            List list = em.createNativeQuery("SELECT GLHEADINT,ifnull(GLHEADURI,''),ifnull(glheadname,'') FROM accounttypemaster WHERE ACCTCODE='" + accode + "'").getResultList();
            Vector element = (Vector) list.get(0);
            String intGl = element.get(0).toString();
            String uriGl = element.get(1).toString();
            String oirHead = element.get(2).toString();
            String accNature = ftsPosting.getAccountNature(acno);
            String msg = "";
            if (intGl.equals("")) {
                return "THE INTEREST GL HEAD NOT EXIST";
            }

            String brncode = ftsPosting.getCurrentBrnCode(acno);
            if (accStatus.equals("SUB") || accStatus.equals("DOU") || accStatus.equals("LOS")) {
                List list2 = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)),0)-ifnull(SUM(ifnull(DRAMT,0)),0) FROM npa_recon WHERE ACNO='" + acno + "' AND DT<='" + dt + "'").getResultList();
                Vector element2 = (Vector) list2.get(0);
                double mem = Double.parseDouble(element2.get(0).toString());

                if (Math.abs(mem) <= (crAmt) && mem <= 0) {
//                    float trsNo = ftsPosting.getTrsNo();
                    if (mem < 0) {
                        float recNo = ftsPosting.getRecNo();

                        if (accNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            Query q4 = em.createNativeQuery("INSERT INTO loan_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                    + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "',ABS(" + mem + "),0,1,2," + recNo + "," + trsNo + ","
                                    + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "')");
                            q4.executeUpdate();
                            msg = ftsPosting.updateBalance(accNature, acno, 0, Math.abs(mem), "Y", "N");
                            if (!msg.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            Query q4 = em.createNativeQuery("INSERT INTO ca_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                    + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "',ABS(" + mem + "),0,1,2," + recNo + "," + trsNo + ","
                                    + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "')");
                            q4.executeUpdate();
                            msg = ftsPosting.updateBalance(accNature, acno, 0, Math.abs(mem), "Y", "N");
                            if (!msg.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(msg);
                            }
                        }
                        float recNo1 = ftsPosting.getRecNo();

                        Query q5 = em.createNativeQuery("INSERT INTO npa_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY,INTAMT, ORG_BRNID, DEST_BRNID) VALUES "
                                + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "',0,ABS(" + mem + "),0,8," + recNo1 + "," + trsNo + ","
                                + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "'," + crAmt + ",'" + orgnBrCode + "','" + destBrCode + "')");
                        q5.executeUpdate();
                    }
                    /**
                     * *************************************************************************************************************************************
                     */
                    //Addition On 21/01/2015
                    int autoMarking = 0, flag = 0;
                    List list5 = em.createNativeQuery("SELECT code FROM parameterinfo_report WHERE reportname = 'AUTO-NPA-TO-STANDARD'").getResultList();
                    if (!list5.isEmpty()) {
                        Vector element5 = (Vector) list5.get(0);
                        autoMarking = Integer.parseInt(element5.get(0).toString());
                    }
                    if (autoMarking == 1) {
                        String acStatusDetails = "", npaDueDt = "";
                        List sanctionLimitDtList = null;
                        if (accStatus.equals("SUB") || accStatus.equals("DOU") || accStatus.equals("LOS")) {
                            BigDecimal currentBal = new BigDecimal(BigInteger.ZERO);
                            List chkBalList = em.createNativeQuery("select sum(a.cramt) from ("
                                    + "select ifnull(sum(cramt-dramt),0) as cramt from loan_recon where acno = '" + acno + "' and dt<='" + dt + "'"
                                    + "union all "
                                    + "select ifnull(sum(cramt-dramt),0) as cramt from ca_recon where acno = '" + acno + "' and dt<='" + dt + "'"
                                    + "union all "
                                    + "select ifnull(sum(cramt-dramt),0) as cramt from npa_recon where acno = '" + acno + "' and dt<='" + dt + "') a").getResultList();
                            if (!chkBalList.isEmpty()) {
                                Vector element5 = (Vector) chkBalList.get(0);
                                currentBal = new BigDecimal(element5.get(0).toString());
                            }
//                            BigDecimal currentBal = new BigDecimal(ftsPosting.ftsGetBal(acno));
                            if (accNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
//                                npaDueDt = emiPaidMarkingDt(acno, dt);//                            
//                                List emiPaidChk = em.createNativeQuery("SELECT acno FROM emidetails WHERE acno = '" + acno + "' and status='unpaid' and duedt<='" + dt + "'").getResultList();
                                Double outstanding = common.getBalanceOnDate(acno, dt);//Ouststanding includes credit amount
                                if (interBranchFacade.npaPOrdChk(acno).equalsIgnoreCase("true")) {
                                    /**
                                     * *CIP Case checking**
                                     */
                                    List checklistEmi = em.createNativeQuery("select * from emidetails where acno = '" + acno + "' and status = 'unpaid'  ").getResultList();
                                    if (!checklistEmi.isEmpty()) {
                                        List installAmtList = em.createNativeQuery("select * from emidetails where acno = '" + acno + "' and duedt>'" + dt + "'").getResultList();
                                        if (installAmtList.isEmpty()) {
                                            installAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
                                                    + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from loan_recon where acno =  '" + acno + "'  and auth = 'Y' and dt<='" + dt + "' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
                                                    + " union all "
                                                    + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + acno + "'  and auth = 'Y' and dt<='" + dt + "' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ) a").getResultList();
                                        } else {
                                            installAmtList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno = '" + acno + "' and duedt<='" + dt + "'").getResultList();
                                        }
                                        Vector instllAmtVect = (Vector) installAmtList.get(0);
                                        double totInstallAmt = Double.parseDouble(instllAmtVect.get(0).toString());

                                        List crAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  from "
                                                + "(select ifnull(sum(ifnull(cramt,0)),0)  as cramt from loan_recon where acno =  '" + acno + "' and auth = 'Y' and dt<='" + dt + "'  and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
                                                + "union all "
                                                + "select ifnull(sum(ifnull(cramt,0)),0)  as cramt  from npa_recon where acno =  '" + acno + "' and auth = 'Y' and dt<='" + dt + "'   and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%') a").getResultList();
                                        Vector crAmtVect = (Vector) crAmtList.get(0);
                                        double totCrAmt = Double.parseDouble(crAmtVect.get(0).toString());

                                        List drAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
                                                + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from loan_recon where acno =  '" + acno + "'  and auth = 'Y' and dt<='" + dt + "' and trandesc in (3,4,8) and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
                                                + " union all "
                                                + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + acno + "'  and auth = 'Y' and dt<='" + dt + "' and trandesc in (3,4,8)  and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ) a").getResultList();
                                        Vector drAmtVect = (Vector) drAmtList.get(0);
                                        double totIntChgDrAmt = Double.parseDouble(drAmtVect.get(0).toString());
                                        double remainOverDuePriAmt = totInstallAmt - totIntChgDrAmt;
                                        if ((remainOverDuePriAmt > 0) && (totIntChgDrAmt <= totCrAmt)) {
                                            installAmtList = em.createNativeQuery("select ifnull(count(INSTALLAMT),0), ifnull(INSTALLAMT,0), date_format(ifnull(max(duedt),'19000101'),'%Y%m%d')   from emidetails where acno = '" + acno + "' ").getResultList();
                                            instllAmtVect = (Vector) installAmtList.get(0);
                                            double totInstallment = Double.parseDouble(instllAmtVect.get(0).toString());
                                            double installmentAmt = Double.parseDouble(instllAmtVect.get(1).toString());
                                            String maxDueDT = instllAmtVect.get(2).toString();
                                            if (!maxDueDT.equalsIgnoreCase("19000101")) {
                                                if (ymd.parse(maxDueDT).compareTo(dt.length() == 8 ? ymd.parse(dt) : yMMd.parse(dt)) <= 0) {
                                                    /*All the EMI due of that account*/
                                                    double paidInstallment = totCrAmt / installmentAmt;
                                                    if (paidInstallment >= totInstallment) {
                                                        flag = 1;
                                                    }
                                                } else {
                                                    if (remainOverDuePriAmt <= (totCrAmt - totIntChgDrAmt)) {
                                                        flag = 1;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (outstanding >= 0 && currentBal.doubleValue() >= 0) {
                                            flag = 1;
                                        }
                                    }
                                } else {
                                    /**
                                     * *PIC Case checking**
                                     */
//                                if (emiPaidChk.isEmpty()) {
                                    List checklistEmi = em.createNativeQuery("select * from emidetails where acno = '" + acno + "' and status = 'unpaid'  ").getResultList();
                                    if (!checklistEmi.isEmpty()) {
                                        List installAmtList = em.createNativeQuery("select * from emidetails where acno = '" + acno + "' and duedt>'" + dt + "'").getResultList();
                                        if (installAmtList.isEmpty()) {
                                            installAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  as dramt from " + common.getTableName(accNature) + " where acno =  '" + acno + "' and dt<='" + dt + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
                                        } else {
                                            installAmtList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno = '" + acno + "' and duedt<='" + dt + "'").getResultList();
                                        }
                                        Vector instllAmtVect = (Vector) installAmtList.get(0);
                                        double totInstallAmt = Double.parseDouble(instllAmtVect.get(0).toString());

                                        List crAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  as cramt from loan_recon where acno =  '" + acno + "' and auth = 'Y' and dt<='" + dt + "'  and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
                                        Vector crAmtVect = (Vector) crAmtList.get(0);
                                        double totCrAmt = Double.parseDouble(crAmtVect.get(0).toString());

                                        List drAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  as dramt from loan_recon where acno =  '" + acno + "'  and auth = 'Y' and dt<='" + dt + "' and trandesc in (3,4,8) and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
                                        Vector drAmtVect = (Vector) drAmtList.get(0);
                                        double totIntDrAmt = Double.parseDouble(drAmtVect.get(0).toString());
                                        double remainOverDuePriAmt = totInstallAmt - totIntDrAmt;
                                        if ((remainOverDuePriAmt > 0) && (remainOverDuePriAmt <= totCrAmt)) {
                                            installAmtList = em.createNativeQuery("select ifnull(count(INSTALLAMT),0), ifnull(INSTALLAMT,0), date_format(ifnull(max(duedt),'19000101'),'%Y%m%d')   from emidetails where acno = '" + acno + "' ").getResultList();
                                            instllAmtVect = (Vector) installAmtList.get(0);
                                            double totInstallment = Double.parseDouble(instllAmtVect.get(0).toString());
                                            double installmentAmt = Double.parseDouble(instllAmtVect.get(1).toString());
                                            String maxDueDT = instllAmtVect.get(2).toString();
                                            if (!maxDueDT.equalsIgnoreCase("19000101")) {
                                                if (ymd.parse(maxDueDT).compareTo(dt.length() == 8 ? ymd.parse(dt) : yMMd.parse(dt)) <= 0) {  // It was previos on date 21/10/2016--compareTo(ymmd.parse(dt))
                                                    double paidInstallment = totCrAmt / installmentAmt;
                                                    if (paidInstallment >= totInstallment) {
                                                        flag = 1;
                                                    }
                                                } else {
                                                    flag = 1;
                                                }
                                            }
                                        }
                                    } else {
                                        if (outstanding >= 0 && currentBal.doubleValue() >= 0) {
                                            flag = 1;
                                        }
                                    }
                                }
                            } else if (accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                if (interBranchFacade.npaPOrdChk(acno).equalsIgnoreCase("true")) {
                                    List borDtl = em.createNativeQuery("select date_format(ifnull(DOCUMENT_EXP_DATE,'19000101'),'%Y%m%d') from cbs_loan_borrower_details where acc_no ='" + acno + "'").getResultList();
                                    if (!borDtl.isEmpty()) {
                                        Vector vect1 = (Vector) borDtl.get(0);
                                        String docDt = vect1.get(0).toString();
                                        SimpleDateFormat yymmdd = new SimpleDateFormat("yyyyMMdd");
                                        if (yymmdd.parse(docDt).after(yymmdd.parse(dt))) {
                                            List subStdDtList = em.createNativeQuery("select a.acno,max(a.effdt) from accountstatus a, accountmaster ac "
                                                    + "where a.acno = ac.acno and a.spflag in (11) and a.effdt <='" + dt + "' and a.acno ='" + acno + "'"
                                                    + " and (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + dt + "') group by a.acno").getResultList();
                                            if (!subStdDtList.isEmpty()) {
                                                Vector vect = (Vector) subStdDtList.get(0);
                                                String subStdDt = vect.get(1).toString();
                                                List crAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  from "
                                                        + "(select ifnull(sum(ifnull(cramt,0)),0)  as cramt from ca_recon where acno =  '" + acno + "' and auth = 'Y' and dt between '" + subStdDt + "' and '" + dt + "'  and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
                                                        + "union all "
                                                        + "select ifnull(sum(ifnull(cramt,0)),0)  as cramt  from npa_recon where acno =  '" + acno + "' and auth = 'Y' and dt between '" + subStdDt + "' and '" + dt + "'    and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%') a").getResultList();
                                                Vector crAmtVect = (Vector) crAmtList.get(0);
                                                double totCrAmt = Double.parseDouble(crAmtVect.get(0).toString());
                                                List drAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
                                                        + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from ca_recon where acno =  '" + acno + "'  and auth = 'Y' and dt between '" + subStdDt + "' and '" + dt + "'   and trandesc in (3,4,8) and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
                                                        + " union all "
                                                        + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + acno + "'  and auth = 'Y' and dt between '" + subStdDt + "' and '" + dt + "'   and trandesc in (3,4,8)  and details not like '%INTT. REC FOR MEM%' ) a").getResultList();
                                                Vector drAmtVect = (Vector) drAmtList.get(0);
                                                double totIntChgDrAmt = Double.parseDouble(drAmtVect.get(0).toString());
                                                List sanctionDtList = em.createNativeQuery("select ifnull(ODLimit,0) from loan_appparameter where acno =  '" + acno + "' ").getResultList();
                                                BigDecimal sancAmt = new BigDecimal("0");
                                                if (!sanctionDtList.isEmpty()) {
                                                    Vector vist = (Vector) sanctionDtList.get(0);
                                                    sancAmt = new BigDecimal(vist.get(0).toString());
                                                }
                                                if (totIntChgDrAmt <= totCrAmt) {
                                                    double balance = currentBal.doubleValue();
                                                    if (sancAmt.doubleValue() >= Math.abs(balance)) {
                                                        flag = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (interBranchFacade.npaPOrdChk(acno).equalsIgnoreCase("false")) {
                                    if (currentBal.doubleValue() >= 0) {
                                        flag = 1;
                                    }
                                }
                            }
                            if (flag == 1) {
                                acStatusDetails = "OPERATIVE, AUTO MARKING SUB STANDARD TO STANDARD";
                                int n = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,enterby,auth,effdt,"
                                        + "baseacno,authby,trantime) values('" + acno + "','" + acStatusDetails + "','1','" + dt + "',"
                                        + "0,'" + enterBy + "','Y','" + dt + "','" + acno + "','System',now())").executeUpdate();
                                if (n <= 0) {
                                    throw new ApplicationException("Problem In Account Status Insertion.");
                                }
                                Integer entryList = em.createNativeQuery("update loan_appparameter set presentstatus = 'OPERATIVE', npaAcNo='', recover='CIP', STADT ='" + dt + "' where acno = '"
                                        + acno + "'").executeUpdate();
                                if (entryList <= 0) {
                                    throw new ApplicationException("Problem in data updation");
                                }
                                n = em.createNativeQuery("update accountmaster set accstatus=1 where acno='" + acno + "'").executeUpdate();
                                if (n <= 0) {
                                    throw new ApplicationException("Problem In Account Master Updation.");
                                }
                            }
                        }
                    }
                } else if (Math.abs(mem) > (crAmt) && mem < 0) {
//                    float trsNo = ftsPosting.getTrsNo();
                    float recNo = ftsPosting.getRecNo();

                    if (accNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        Query q4 = em.createNativeQuery("INSERT INTO loan_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "'," + crAmt + ",0,1,2," + recNo + "," + trsNo + ","
                                + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "')");
                        q4.executeUpdate();

                        msg = ftsPosting.updateBalance(accNature, acno, 0, crAmt, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }

                    } else {
                        Query q4 = em.createNativeQuery("INSERT INTO ca_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "'," + crAmt + ",0,1,2," + recNo + "," + trsNo + ","
                                + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "')");
                        q4.executeUpdate();

                        msg = ftsPosting.updateBalance(accNature, acno, 0, crAmt, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }
                    }

                    recNo = ftsPosting.getRecNo();

                    Query q5 = em.createNativeQuery("INSERT INTO npa_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY,INTAMT, ORG_BRNID, DEST_BRNID) VALUES "
                            + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "',0," + crAmt + ",0,8," + recNo + "," + trsNo + ","
                            + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "'," + crAmt + ",'" + orgnBrCode + "','" + destBrCode + "')");
                    q5.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());

        }
        return "True";
    }

    public String processBulkRecoveryPostal(List<BulkRecoveryPojo> dataList, String orgnBrCode, String useName, String todayDate, String place) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", branchPremiumHead = "", branchWellFareHead = "", suspenseHead;
        Integer payBy = 0;
        boolean remoteChq = false;
        try {
            ut.begin();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to process recovery.");
            }
            String[] arr = checkExcelBatch(dataList);
            if (arr[0].equals("false")) {
                throw new ApplicationException("In batch dr and cr is not equal in excel file.");
            }
            todayDate = ymd.format(dmy.parse(todayDate));
            String drAccount = arr[1].trim();
            String drAmount = arr[2].trim();
            String chqNo = arr[3].trim();
            String chqDt = arr[4].trim();
            //Chque Related Manipulation.
            if (chqNo == null || chqNo.equals("")) {
                payBy = 3;
                chqDt = "19000101";
            } else {
                if (chqDt == null || chqDt.equals("")) {
                    throw new ApplicationException("Please fill cheque date for dr amount in DD/MM/YYYY format in excel file.");
                }
                payBy = 1;
                chqDt = ymd.format(dmy.parse(chqDt));
            }
            String srNo = "", flag = "", toDt = "", valueDt = "", prinFlowId = "";
            int gracePdDays = 0;
            // new Code
            List dmdInfoList = getFileGenDt(place);
            if (!dmdInfoList.isEmpty()) {
                Vector dmdVector = (Vector) dmdInfoList.get(0);
                srNo = dmdVector.get(0).toString();
                flag = dmdVector.get(1).toString();
                toDt = dmdVector.get(2).toString();
            }

            //Scheme Code
            Map<String, String> schemeCodeMap = new HashMap<String, String>();
            List schemeCodeList = em.createNativeQuery("select SCHEME_TYPE,grace_period_for_late_fee_days from cbs_scheme_loan_interest_details a ,"
                    + "(select acctcode from accounttypemaster where acctnature = '" + CbsConstant.TERM_LOAN + "'"
                    + "union all "
                    + "select Acctcode from parameterinfo_miscincome where Purpose = 'LIP_PREMIUM')b where a.SCHEME_TYPE = b.acctcode").getResultList();
            if (schemeCodeList.isEmpty()) {
                throw new ApplicationException("Scheme Code does not exist 1");
            }
            for (int i = 0; i < schemeCodeList.size(); i++) {
                Vector ele = (Vector) schemeCodeList.get(i);
                schemeCodeMap.put(ele.get(0).toString(), ele.get(1).toString());
            }
            //END Scheme Code

            if (Double.parseDouble(drAmount) != 0 && !drAccount.equals("")) {              //Normal Transfer Case
                //Dr A/c validation
                result = validateDrAccount(drAccount);
                if (!result.equals("true")) {
                    throw new ApplicationException(result);
                }
                //Making flag to true if it is inter branch batch. Otherwise it will be false always.
                if (!drAccount.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
                    remoteChq = true;
                }
                //Generating common batch no.
                Float trsno = ftsPosting.getTrsNo();
                //Getting branch level Premium and WellFare Head.
                branchPremiumHead = orgnBrCode + getAccountByPurpose("PREMIUM-HEAD");
                branchWellFareHead = orgnBrCode + getAccountByPurpose("WELFARE-HEAD");
                suspenseHead = orgnBrCode + getAccountByPurpose("SUSPENCE-HEAD");
                String bnkCode = ftsPosting.getBankCode();
                //Dr Account
                if (remoteChq == true) {
                    //Calling of SX
                    result = interBranchFacade.cbsPostingSx(drAccount, 1, ymd.format(new Date()), Double.parseDouble(drAmount), 0.0, 2, "Bulk Recovery Dr:", 0f, "", chqNo, chqDt, payBy, 0f, ftsPosting.getRecNo(), 67, drAccount.substring(0, 2), orgnBrCode, useName, useName, trsno, "", "");
                } else {
//                    valueDt = getValueDt(srNo, toDt);
                    result = performGeneralTransaction(drAccount, 1, 2, chqNo, Double.parseDouble(drAmount), trsno, useName, orgnBrCode, "", "", chqDt, payBy, useName, ymd.format(new Date()));
                }
                if (!result.toLowerCase().contains("true")) {
                    throw new ApplicationException(result);
                }

                //Get current financial year for login branch.
                Integer fyear = Integer.parseInt(getFinYearForBranch(orgnBrCode));
                //Get max seq no for above coming financial year.
                List list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 as maxseq from bill_suspense "
                        + "where fyear = " + fyear + "").getResultList();
                Vector element = (Vector) list.get(0);
                Double maxSeqNo = Double.parseDouble(element.get(0).toString());

                //For Cr Accounts.  
                double totalSuspenseDmdAmt = 0d;
                double totalNpaAmt = 0d;
                for (BulkRecoveryPojo obj : dataList) {
                    if (!obj.getAcno().equalsIgnoreCase(drAccount)) {
//                        result = validateCrAccount(obj.getAcno(), orgnBrCode);
//                        if (!result.equals("true")) {
//                            throw new ApplicationException(result);
//                        }
                        //Get Demand flow Id
                        //Different coming amounts-emi,premium,wellfare transactions
                        if (obj.getLoanEmi() != null && Double.parseDouble(obj.getLoanEmi()) != 0) {
                            result = validateCrAccount(obj.getAcno(), orgnBrCode);
                            if (!result.equals("true")) {
                                throw new ApplicationException(result);
                            }
                            double acDmdAmt = 0d;
                            if (remoteChq == true) {
                                //Calling of CX for Loan A/c
                                result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), 0.0, 2, "", 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                            } else {
                                String paidSno = "";
                                // New Code add bill suspense entry
//                                List dmdList = em.createNativeQuery("select cast(sum(DMD_AMT)as decimal(14,2)) from cbs_loan_dmd_table where acno = '" + obj.getAcno() + "' and DMD_SRL_NUM = " + srNo + "").getResultList();
//                                Vector intVector = (Vector) dmdList.get(0);
//                                acDmdAmt = Double.parseDouble(intVector.get(0).toString());
//                                if (Double.parseDouble(obj.getLoanEmi()) > acDmdAmt) {
//                                    Float recNo = ftsPosting.getRecNo();
//                                    double suspenAmt = Double.parseDouble(obj.getLoanEmi()) - acDmdAmt;
//                                    totalSuspenseDmdAmt = totalSuspenseDmdAmt + suspenAmt;
//                                    Integer insertResult = em.createNativeQuery("INSERT INTO bill_suspense_dt(fyear,seqno,instno,acno,details,"
//                                            + "amount,dt,origindt,status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,"
//                                            + "recno,lastupdateby) VALUES(" + fyear + "," + maxSeqNo + ",'','" + obj.getAcno() + "','Excess amount for loan a/c',"
//                                            + "" + suspenAmt + ",'" + todayDate + "',"
//                                            + "'" + todayDate + "','ISSUED',0,1,1,'" + useName + "','','Y',"
//                                            + "'" + useName + "',CURRENT_TIMESTAMP," + recNo + ",'')").executeUpdate();
//                                    if (insertResult <= 0) {
//                                        throw new ApplicationException("Problem in sundry head account insertion: " + suspenseHead);
//                                    }
//
//                                }
                                // ENd New Code add bill suspense entry
//                                if (Double.parseDouble(obj.getLoanEmi()) > acDmdAmt) {
//                                    result = ftsPosting.loanDisbursementInstallment(obj.getAcno(), acDmdAmt, 0, useName, ymd.format(new Date()), trsno, 1, "Demand Recovery");
//                                } else {
//                                    result = ftsPosting.loanDisbursementInstallment(obj.getAcno(), Double.parseDouble(obj.getLoanEmi()), 0, useName, ymd.format(new Date()), trsno, 1, "Demand Recovery");
//                                }

                                result = ftsPosting.loanDisbursementInstallment(obj.getAcno(), Double.parseDouble(obj.getLoanEmi()), 0, useName, ymd.format(new Date()), trsno, 1, "Demand Recovery");
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                paidSno = result.substring(result.indexOf(":") + 1);
                                valueDt = getValueDtPostal(schemeCodeMap.get(obj.getAcno().substring(2, 4)), toDt);
                                result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getLoanEmi()), trsno, useName, orgnBrCode, paidSno, "", "19000101", 3, useName, valueDt);
//                                if (Double.parseDouble(obj.getLoanEmi()) > acDmdAmt) {
//                                    result = performGeneralTransaction(obj.getAcno(), 0, 2, "", acDmdAmt, trsno, useName, orgnBrCode, paidSno, "", "19000101", 3, useName, valueDt);
//                                } else {
//                                    result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getLoanEmi()), trsno, useName, orgnBrCode, paidSno, "", "19000101", 3, useName, valueDt);
//                                }

                                /*New Code Npa Recovery*/
                                String status = interBranchFacade.fnGetLoanStatus(obj.getAcno(), ymd.format(new Date()));
                                if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                    //npaRecoveryUpdation
                                    totalNpaAmt = totalNpaAmt + getNpaRecoveryAmt(obj.getAcno(), ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), status);
                                    String msgRecAmt = bulkNpaRecoveryUpdation(trsno, ftsPosting.getAccountNature(obj.getAcno()), obj.getAcno(), ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), orgnBrCode, orgnBrCode, useName, valueDt, bnkCode);
                                    if (!msgRecAmt.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(msgRecAmt);
                                    }
                                }

                                /*End Of New Code Npa Recovery*/
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        }
                        if (obj.getInsPremium() != null && Double.parseDouble(obj.getInsPremium()) != 0) {
                            result = validateCrAccount(obj.getAcno(), orgnBrCode);
                            if (!result.equals("true")) {
                                throw new ApplicationException(result);
                            }
                            String paidSno = "";
                            paidSno = result.substring(result.indexOf(":") + 1);
                            if (remoteChq == true) {
                                //Calling of CX for BranchPremiumHead A/c
                                result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getInsPremium()), 0.0, 2, paidSno + "::" + obj.getAcno(), 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                            } else {
                                valueDt = ymd.format(new Date());
                                result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getInsPremium()), trsno, useName, orgnBrCode, paidSno, obj.getAcno(), "19000101", 3, useName, valueDt);
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }

                            List getDmdList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_srl_num,dmd_eff_date,dmd_ovdu_date,dmd_amt,last_adj_date,ifnull(tot_adj_amt,0) as tot_adj_amt, ifnull(ei_amt,0) as ei_amt from cbs_loan_dmd_table  where acno = '" + obj.getAcno() + "' and dmd_flow_id = 'PINSP' and DMD_SRL_NUM =" + srNo + "  order by acno, dmd_eff_date, shdl_num, dmd_srl_num").getResultList();
                            if (getDmdList.size() > 0) {
                                for (int i = 0; i < getDmdList.size(); i++) {
                                    Vector getDmdVect = (Vector) getDmdList.get(i);
                                    String dmdAcNo = getDmdVect.get(0).toString();
                                    int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                    String dmdFlowId = getDmdVect.get(2).toString();
                                    int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                                    Date dmdEffDt = yMMd.parse(getDmdVect.get(4).toString());
                                    double dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());
                                    double dmdAdjAmt = Double.parseDouble(getDmdVect.get(8).toString());
//                                    List tsCntList = em.createNativeQuery("select ifnull(ts_cnt,0)+1 from cbs_loan_dmd_table where acno = '" + obj.getAcno() + "' and shdl_num = " + dmdSchNo + " and dmd_flow_id = 'PINSP' and del_flg = 'N' and dmd_srl_num = " + dmdSrNo).getResultList();
//                                    Vector tsCntVector = (Vector) tsCntList.get(0);
//                                    int tsCnt = Integer.parseInt(tsCntVector.get(0).toString());
                                    List srNoList = em.createNativeQuery("select ifnull(max(cast(srl_num  as unsigned)),0)+1, ifnull(max(cast(part_tran_srl_num as unsigned)),0)+1 from cbs_loan_dmd_adj_table").getResultList();
                                    Vector srNoVector = (Vector) srNoList.get(0);
                                    int srlNo = Integer.parseInt(srNoVector.get(0).toString());
                                    int partTrSrNo = Integer.parseInt(srNoVector.get(1).toString());

                                    int updateDmdTable = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET  LAST_ADJ_DATE = curdate(), TOT_ADJ_AMT = " + Double.parseDouble(obj.getInsPremium()) + ", RCRE_USER_ID = '" + useName + "' WHERE ACNO = '" + obj.getAcno() + "' and DMD_SRL_NUM =" + srNo + " ").executeUpdate();
                                    if (updateDmdTable <= 0) {
                                        throw new ApplicationException("Problem in updation");
                                    }
                                    Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                            + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'PINSP','" + ymd.format(dmdEffDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + todayDate + "'," + dmdAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                    Integer insertQry = insertQuery.executeUpdate();
                                    if (insertQry <= 0) {
                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                    }
                                }
                            }
                        }
                        //if (obj.getAcno().substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                        if (obj.getWellFare() != null && Double.parseDouble(obj.getWellFare()) != 0) {
                            result = validateCrAccount(obj.getAcno(), orgnBrCode);
                            if (!result.equals("true")) {
                                throw new ApplicationException(result);
                            }
                            String paidSno = "";
                            result = satisfyRecovery("PWELL", obj.getAcno(), Double.parseDouble(obj.getWellFare()), useName, bnkCode);
                            if (!result.contains("true")) {
                                throw new ApplicationException(result);
                            }
                            paidSno = result.substring(result.indexOf(":") + 1);
                            if (remoteChq == true) {
                                //Calling of CX for BranchWellFareHead A/c
                                result = interBranchFacade.cbsPostingCx(branchWellFareHead, 0, ymd.format(new Date()), Double.parseDouble(obj.getWellFare()), 0.0, 2, paidSno + "::" + obj.getAcno(), 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                            } else {
                                valueDt = ymd.format(new Date());
                                result = performGeneralTransaction(branchWellFareHead, 0, 2, "", Double.parseDouble(obj.getWellFare()), trsno, useName, orgnBrCode, paidSno, obj.getAcno(), "19000101", 3, useName, valueDt);
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                            List getDmdList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_srl_num,dmd_eff_date,dmd_ovdu_date,dmd_amt,last_adj_date,ifnull(tot_adj_amt,0) as tot_adj_amt, ifnull(ei_amt,0) as ei_amt from cbs_loan_dmd_table  where acno = '" + obj.getAcno() + "' and dmd_flow_id = 'PWELL' and DMD_SRL_NUM =" + srNo + "  order by acno, dmd_eff_date, shdl_num, dmd_srl_num").getResultList();
                            if (getDmdList.size() > 0) {
                                for (int i = 0; i < getDmdList.size(); i++) {
                                    Vector getDmdVect = (Vector) getDmdList.get(i);
                                    String dmdAcNo = getDmdVect.get(0).toString();
                                    int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                    String dmdFlowId = getDmdVect.get(2).toString();
                                    int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                                    Date dmdEffDt = yMMd.parse(getDmdVect.get(4).toString());
                                    double dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());

                                    double dmdAdjAmt = Double.parseDouble(getDmdVect.get(8).toString());
                                    List tsCntList = em.createNativeQuery("select ifnull(ts_cnt,0)+1 from cbs_loan_dmd_table where acno = '" + obj.getAcno() + "' and shdl_num = " + dmdSchNo + " and dmd_flow_id = 'PWELL' and del_flg = 'N' and dmd_srl_num = " + dmdSrNo).getResultList();
                                    Vector tsCntVector = (Vector) tsCntList.get(0);
                                    int tsCnt = Integer.parseInt(tsCntVector.get(0).toString());

                                    List srNoList = em.createNativeQuery("select ifnull(max(cast(srl_num  as unsigned)),0)+1, ifnull(max(cast(part_tran_srl_num as unsigned)),0)+1 from cbs_loan_dmd_adj_table").getResultList();
                                    Vector srNoVector = (Vector) srNoList.get(0);
                                    int srlNo = Integer.parseInt(srNoVector.get(0).toString());
                                    int partTrSrNo = Integer.parseInt(srNoVector.get(1).toString());

                                    int updateDmdTable = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET  LAST_ADJ_DATE = curdate(), TOT_ADJ_AMT = " + Double.parseDouble(obj.getWellFare()) + ", RCRE_USER_ID = '" + useName + "' WHERE ACNO = '" + obj.getAcno() + "' and DMD_SRL_NUM =" + srNo + " ").executeUpdate();
                                    if (updateDmdTable <= 0) {
                                        throw new ApplicationException("Problem in updation");
                                    }
                                    Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                            + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'PWELL','" + ymd.format(dmdEffDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + todayDate + "'," + dmdAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                    Integer insertQry = insertQuery.executeUpdate();
                                    if (insertQry <= 0) {
                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                    }
                                }
                            }
                        }
                    }
                }

//                if (totalSuspenseDmdAmt > 0) {
//                    Integer insertResult = em.createNativeQuery("INSERT INTO bill_suspense(fyear,seqno,instno,acno,details,amount,dt,"
//                            + "origindt,status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,recno,lastupdateby) "
//                            + "VALUES(" + fyear + "," + maxSeqNo + ",'','" + suspenseHead + "','Excess amount for loan a/c'," + totalSuspenseDmdAmt + ","
//                            + "'" + todayDate + "','" + todayDate + "',"
//                            + "'ISSUED',0,1,0,'" + useName + "','','Y','" + useName + "',CURRENT_TIMESTAMP,"
//                            + "" + ftsPosting.getRecNo() + ",'')").executeUpdate();
//                    if (insertResult <= 0) {
//                        throw new ApplicationException("Problem in sundry head account insertion: " + suspenseHead);
//                    }
//                    valueDt = ymd.format(new Date());
//                    result = performGeneralTransaction(suspenseHead, 0, 2, "", totalSuspenseDmdAmt, trsno, useName, orgnBrCode, "", "", "19000101", 3, useName, valueDt);
//                    if (!result.toLowerCase().contains("true")) {
//                        throw new ApplicationException(result);
//                    }
//                }


            }

            int updateDmdInfo = em.createNativeQuery("update cbs_loan_dmd_info set FLAG = 'R',RECOVERYDT = curdate() where SNO = " + srNo).executeUpdate();
            if (updateDmdInfo <= 0) {
                throw new ApplicationException("Problem in updation");
            }
            ut.commit();
            result = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    public String satisfyRecovery(String mode, String acno, Double receivedAmount, String authBy, String bnkCode) throws ApplicationException {
        String result = "true", paidSno = "";
        int minSno = 0;
        Double amount = 0.0, excessAmt = 0.0;
        try {

            if ((bnkCode.equalsIgnoreCase("pccb") || bnkCode.equalsIgnoreCase("krbi"))
                    && acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                int gNo = 0;
                List gNoList = em.createNativeQuery("select IFNULL(max(sno),0) from insurance_details where acno='" + acno + "'").getResultList();
                if (gNoList.size() > 0) {
                    Vector gNoVect = (Vector) gNoList.get(0);
                    gNo = Integer.parseInt(gNoVect.get(0).toString()) + 1;
                }
                int insertResult = em.createNativeQuery("INSERT INTO insurance_details(txn_type,acno,sno,due_dt,insured_amount,"
                        + "premium_amount,status,payment_dt,enter_by,periodicity,excess_amt,policy_no) "
                        + "VALUES('" + mode + "','" + acno + "'," + gNo + ","
                        + " curdate()," + receivedAmount + "," + receivedAmount + ","
                        + "'PAID',curdate(),'" + authBy + "',"
                        + "'Y',0,'')").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Problem in premium / wellfare generation");
                }
            } else {
                List list = new ArrayList();
                //if (mode.equalsIgnoreCase("PLOAN")) {
                if (mode.equalsIgnoreCase("PRDEM")) {
                    list = em.createNativeQuery("select sno,ifnull(installamt,0),ifnull(excessamt,0) from emidetails where "
                            + "acno='" + acno + "' and status='UNPAID' and duedt in(select min(duedt) from emidetails "
                            + "where acno='" + acno + "' and status='UNPAID')").getResultList();
                    if (list.isEmpty()) {
                        return result = "There is no unpaid emi for A/c: " + acno;
                    }
                } else if (mode.equalsIgnoreCase("RDDEM")) {
                    list = em.createNativeQuery("select sno,ifnull(installamt,0),ifnull(EXCESSAMT,0) from rd_installment "
                            + "where acno='" + acno + "' and status='UNPAID' and DUEDT in (select min(DUEDT) from "
                            + "rd_installment where acno='" + acno + "' and status='UNPAID')").getResultList();
                    if (list.isEmpty()) {
                        return result = "There is no unpaid premium for A/c: " + acno;
                    }
                } else if (mode.equalsIgnoreCase("PWELL")) {
                    list = em.createNativeQuery("select sno,ifnull(insured_amount,0),ifnull(excess_amt,0) from insurance_details "
                            + "where acno='" + acno + "' and status='UNPAID' and due_dt in (select min(due_dt) from insurance_details "
                            + "where acno='" + acno + "' and status='UNPAID')").getResultList();
                    if (list.isEmpty()) {
                        return result = "There is no unpaid wellfare for A/c: " + acno;
                    }
                }
                Vector element = (Vector) list.get(0);
                minSno = Integer.parseInt(element.get(0).toString());
                amount = Double.parseDouble(element.get(1).toString());
                excessAmt = Double.parseDouble(element.get(2).toString());
                int updateQuery = 0;
                if (excessAmt != 0) {
                    if ((receivedAmount + excessAmt) - amount >= 0) {
                        //Only Paid mark minsno and generate again received amount.
                        if (mode.equalsIgnoreCase("PRDEM")) {
                            updateQuery = em.createNativeQuery("update emidetails set status='PAID',excessamt=0.0,"
                                    + "enterby='" + authBy + "',paymentdt='" + ymd.format(new Date()) + "',"
                                    + "lastupdate='" + ymd.format(new Date()) + "' where acno='" + acno + "' "
                                    + "and sno=" + minSno + "").executeUpdate();
                        } else if (mode.equalsIgnoreCase("RDDEM")) {
                            updateQuery = em.createNativeQuery("update rd_installment set status='PAID',excessamt=0.0,"
                                    + "enterby='" + authBy + "',paymentdt='" + ymd.format(new Date()) + "',"
                                    + "lastupdate='" + ymd.format(new Date()) + "' where acno='" + acno + "' "
                                    + "and sno=" + minSno + "").executeUpdate();
                        } else if (mode.equalsIgnoreCase("PINSP") || mode.equalsIgnoreCase("PWELL")) {
                            updateQuery = em.createNativeQuery("update insurance_details set status='PAID',excess_amt=0.0,"
                                    + "payment_dt='" + ymd.format(new Date()) + "' where acno='" + acno + "' and sno=" + minSno + "").executeUpdate();
                        }
                        if (updateQuery <= 0) {
                            return result = "Problem in status updation for: " + acno;
                        }
                        paidSno = ":" + String.valueOf(minSno);
                        receivedAmount = receivedAmount - (amount - excessAmt);
                        minSno += 1;
                    }
                }
                //Payment of others sno status.
                BigDecimal bi[] = new BigDecimal(receivedAmount).divideAndRemainder(new BigDecimal(amount));
                BigDecimal quotient = bi[0];
                BigDecimal remainder = bi[1];

                for (int i = minSno; i < (minSno + quotient.intValue()); i++) {
                    if (mode.equalsIgnoreCase("PRDEM")) {
                        updateQuery = em.createNativeQuery("update emidetails set status='PAID',enterby='" + authBy + "',"
                                + "paymentdt='" + ymd.format(new Date()) + "',lastupdate='" + ymd.format(new Date()) + "' "
                                + "where acno='" + acno + "' and sno=" + i + "").executeUpdate();
                    } else if (mode.equalsIgnoreCase("RDDEM")) {
                        updateQuery = em.createNativeQuery("update rd_installment set status='PAID',enterby='" + authBy + "',"
                                + "paymentdt='" + ymd.format(new Date()) + "',lastupdate='" + ymd.format(new Date()) + "' "
                                + "where acno='" + acno + "' and sno=" + i + "").executeUpdate();
                    } else if (mode.equalsIgnoreCase("PINSP") || mode.equalsIgnoreCase("PWELL")) {
                        updateQuery = em.createNativeQuery("update insurance_details set status='PAID',"
                                + "payment_dt='" + ymd.format(new Date()) + "' where acno='" + acno + "' and sno=" + i + "").executeUpdate();
                    }
                    if (updateQuery <= 0) {
                        return result = "Problem in status updation for: " + acno;
                    }
                    paidSno = paidSno + ":" + String.valueOf(i);
                }
                if (remainder.doubleValue() != 0) {
                    if (mode.equalsIgnoreCase("PRDEM")) {
                        updateQuery = em.createNativeQuery("update emidetails set excessamt = excessamt + " + remainder.doubleValue() + " "
                                + "where acno='" + acno + "' and sno=" + (minSno + quotient.intValue()) + "").executeUpdate();
                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt)"
                                + " values('" + acno + "','" + ymd.format(new Date()) + "'," + remainder.doubleValue() + ")");
                        Integer insertQry = insertQuery.executeUpdate();
                    } else if (mode.equalsIgnoreCase("RDDEM")) {
                        updateQuery = em.createNativeQuery("update rd_installment set excessamt = excessamt + " + remainder.doubleValue() + " "
                                + "where acno='" + acno + "' and sno=" + (minSno + quotient.intValue()) + "").executeUpdate();
                    } else if (mode.equalsIgnoreCase("PINSP") || mode.equalsIgnoreCase("PWELL")) {
                        updateQuery = em.createNativeQuery("update insurance_details set excessamt = excess_amt + " + remainder.doubleValue() + " "
                                + "where acno='" + acno + "' and sno=" + (minSno + quotient.intValue()) + "").executeUpdate();
                    }
                    if (updateQuery <= 0) {
                        return result = "Problem in status updation for: " + acno;
                    }
                }

            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result + firstAndLastPaidSno(paidSno);
    }

    public String performGeneralTransaction(String acno, Integer ty, Integer tranType, String chqNo,
            Double amount, Float trsNo, String enterBy, String orgBrCode, String paidSno, String acAgainstPremiumAndWellGl, String chqDt, Integer payBy, String authBy, String valueDt) throws ApplicationException {
        String result = "true";
        enterBy = "system";
        try {
            Double crAmt = 0.0, drAmt = 0.0;
            String details = "";
            if (chqNo != null && !chqNo.equals("")) {
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(chqNo);
                if (m.matches() != true) {
                    return result = "Please fill proper Inst.No. in Excel File.";
                }
                result = ctsRemote.chequeValidate(acno, chqNo);
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
                result = ftsPosting.updateCheque(acno, 1, 1, Float.parseFloat(chqNo), enterBy);
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
            }

            if (ty == 1) {
                details = "Bulk Recovery Dr:" + paidSno + "::" + acAgainstPremiumAndWellGl;
                drAmt = amount;
            } else {
                details = "Bulk Recovery Cr: " + paidSno + "::" + acAgainstPremiumAndWellGl;
                crAmt = amount;
            }
            Float recNo = ftsPosting.getRecNo();
            String custName = "";
            // custName= ftsPosting.getCustName(acno);

            int insertResult = 0;
            if (tranType == 2) {
                insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                        + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                        + "dest_brnid,tran_id,adviceno,advicebrncode)"
                        + "values ('" + acno + "','" + custName + "',date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d')," + crAmt + "," + drAmt + ","
                        + ty + "," + tranType + "," + recNo + ",'" + chqNo + "'," + chqDt + "," + payBy + ",1,"
                        + "'Y','" + enterBy + "',0,0,'','" + details + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'',"
                        + "'" + orgBrCode + "','" + acno.substring(0, 2) + "',0,'','')").executeUpdate();
            } else if (tranType == 1) {
                insertResult = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,cramt,dramt,ty,trantype,recno,"
                        + "trsno,auth,enterby,authby,details,org_brnid,dest_brnid,valueDt)"
                        + "values ('" + acno + "','" + custName + "',date_format(curdate(),'%Y%m%d'),"
                        + "" + crAmt + "," + drAmt + "," + ty + "," + tranType + "," + recNo + "," + trsNo + ",'Y',"
                        + "'" + enterBy + "','" + authBy + "','" + details + "','" + orgBrCode + "',"
                        + "'" + orgBrCode + "',date_format(curdate(),'%Y%m%d'))").executeUpdate();
            } else if (tranType == 0) {
                insertResult = em.createNativeQuery("INSERT INTO recon_cash_d(acno,custname,dt,dramt,cramt,ty,trantype,recno,"
                        + "trsno,instno,payby,iy,opermode,auth,enterby,authby,tokenpaidby,trandesc,tokenno,subtokenno,trantime,"
                        + "details,voucherno,intflag,closeflag,tdacno,tran_id,term_id,org_brnid,dest_brnid,valuedt,instdt)"
                        + " VALUES('" + acno + "','" + custName + "',date_format(curdate(),'%Y%m%d'),"
                        + "" + drAmt + "," + crAmt + "," + ty + "," + tranType + "," + recNo + "," + trsNo + ",'" + chqNo + "',"
                        + "" + payBy + ",0,'','Y','" + enterBy + "','" + authBy + "','',0,0,'',CURRENT_TIMESTAMP,"
                        + "'" + details + "',0,'','','',0,'','" + orgBrCode + "','" + acno.substring(0, 2) + "',"
                        + "date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'))").executeUpdate();
            }

            if (insertResult <= 0) {
                return result = "Problem In Recon Trf D Insertion For A/c :" + acno;
            }
            result = ftsPosting.insertRecons(ftsPosting.getAccountNature(acno), acno, ty, amount, ymd.format(new Date()), valueDt, tranType, details, enterBy,
                    trsNo, ymdhms.format(new Date()), recNo, "Y", authBy, 0, payBy, chqNo, chqDt, 0f, "",
                    "", 1, "", 0f, "", "", orgBrCode, acno.substring(0, 2), 0, "", "", "");
            if (!result.equalsIgnoreCase("true")) {
                return result;
            }
            result = ftsPosting.updateBalance(ftsPosting.getAccountNature(acno), acno, crAmt, drAmt, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                return result;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String[] checkExcelBatch(List<BulkRecoveryPojo> dataList) {
        String[] arr = new String[5];
        Double totalEmi = 0.0, totalPremium = 0.0, totalWellFare = 0.0, totalAmount = 0.0;
        String drAccount = "", chqNo = "", chqDt = "", equalbatch = "true";

        try {
            String bnkCode = ftsPosting.getBankCode();
            for (BulkRecoveryPojo pojo : dataList) {
                if (bnkCode.equalsIgnoreCase("oefb")) {
                    if (!pojo.getLoanEmi().equals("")) {
                        totalEmi += Double.parseDouble(pojo.getLoanEmi());
                    }
                    if (!pojo.getAmount().equals("")) {
                        totalAmount += Double.parseDouble(pojo.getAmount());
                    }
                    if (!pojo.getAmount().equals("")) {
                        drAccount = pojo.getAcno();
                        chqNo = "";
                        chqDt = "";
                    }

                } else {
                    if (!pojo.getLoanEmi().equals("")) {
                        totalEmi += Double.parseDouble(pojo.getLoanEmi());
                    }
                    if (!pojo.getInsPremium().equals("")) {
                        totalPremium += Double.parseDouble(pojo.getInsPremium());
                    }
                    if (!pojo.getWellFare().equals("")) {
                        totalWellFare += Double.parseDouble(pojo.getWellFare());
                    }
                    if (!pojo.getAmount().equals("")) {
                        totalAmount += Double.parseDouble(pojo.getAmount());
                    }
                    if (!pojo.getAmount().equals("")) {
                        drAccount = pojo.getAcno();
                        chqNo = pojo.getChqNo();
                        chqDt = pojo.getChqDt();
                    }
                }
            }
            if ((totalEmi + totalPremium + totalWellFare) != totalAmount) {
                equalbatch = "false";
            }
            arr[0] = equalbatch;
            arr[1] = drAccount;
            arr[2] = totalAmount.toString();    //For Total dr amount.
            arr[3] = chqNo;
            arr[4] = chqDt;

        } catch (Exception ex) {
            ex.getMessage();
        }
        return arr;
    }

    public String validateDrAccount(String drAccount) throws ApplicationException {
        String result = "true";
        try {
            if (drAccount == null || drAccount.equals("") || drAccount.length() != 12) {
                return result = "Please fill 12 digit Dr A/c no in excel file";
            }
//            else if (!ftsPosting.getAccountNature(drAccount).equals(CbsConstant.CURRENT_AC)) {
//                return "Please fill Dr a/c of current nature.";
//            }
            result = ftsPosting.ftsAcnoValidate(drAccount, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                return result;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String validateCrAccount(String crAccount, String orgBrCode) throws ApplicationException {
        String result = "true";
        try {
            if (crAccount == null || crAccount.equals("") || crAccount.length() != 12) {
                return result = "Please fill 12 digit Cr A/c no in excel file for A/c :" + crAccount;
            }
            if (crAccount.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                List list = em.createNativeQuery("select regfoliono from share_holder where "
                        + "regfoliono='" + crAccount + "'").getResultList();
                if (list.isEmpty()) {
                    return result = "There is no member : " + crAccount;
                }
            } else if (ftsPosting.getAcctTypeByCode(crAccount.substring(2, 4)).equalsIgnoreCase("Tf")) {

                if (!orgBrCode.equals(ftsPosting.getCurrentBrnCode(crAccount))) {
                    return result = "Please fill self branch for A/c No:" + crAccount;
                }
                if (!ftsPosting.getAccountNature(crAccount).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    return "Please fill A/c of saving nature: " + crAccount;
                }
                result = ftsPosting.ftsAcnoValidate(crAccount, 0, "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

            } else if (ftsPosting.getAcNatureByCode(crAccount.substring(2, 4)).equalsIgnoreCase("RD")) {

                if (!orgBrCode.equals(ftsPosting.getCurrentBrnCode(crAccount))) {
                    return result = "Please fill self branch for A/c No:" + crAccount;
                }
                if (!ftsPosting.getAccountNature(crAccount).equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    return "Please fill A/c of recuring nature: " + crAccount;
                }
                result = ftsPosting.ftsAcnoValidate(crAccount, 0, "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

            } else if (ftsPosting.getAcctTypeByCode(crAccount.substring(2, 4)).equalsIgnoreCase("LP")) {

                if (!orgBrCode.equals(crAccount.substring(0, 2))) {
                    return result = "Please fill self branch for A/c No:" + crAccount;
                }
                if (!ftsPosting.getAcNatureByCode(crAccount.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    return "Please fill A/c of current nature: " + crAccount;
                }
                // result = ftsPosting.ftsAcnoValidate(crAccount, 0, "");
                result = getAccStatusValidate(crAccount, 0);
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

            } else {
                if (!orgBrCode.equals(crAccount.substring(0, 2))) {
                    return result = "Please fill self branch for A/c No:" + crAccount;
                }
                if (!(ftsPosting.getAcNatureByCode(crAccount.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN) || ftsPosting.getAcNatureByCode(crAccount.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                    return "Please fill A/c of loan nature: " + crAccount;
                }
                //result = ftsPosting.ftsAcnoValidate(crAccount, 0, "");
                result = getAccStatusValidate(crAccount, 0);
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String getAccStatusValidate(String acNo, int tyFlag) throws ApplicationException {
        try {
            int acctStatus = 0;
            int optStatus = 0;
            List opStatusList = em.createNativeQuery("SELECT ACCSTATUS,OPTSTATUS FROM accountmaster WHERE ACNO='" + acNo + "' and (authby is not null or authby <>'')").getResultList();
            if (opStatusList.size() <= 0) {
                return "Account number does not exist or not authorized!";
            } else {
                Vector ele = (Vector) opStatusList.get(0);
                acctStatus = Integer.parseInt(ele.get(0).toString());
                optStatus = Integer.parseInt(ele.get(1).toString());
            }

            if (acctStatus == 9) {
                return "Account is Closed and Account is : " + acNo;
            } else if (acctStatus == 8) {
                return "Operation Stopped For this Account";
            } else if (acctStatus == 7 && tyFlag == 1) {
                return "Withdrawal Stopped for this Account";
            } else if (acctStatus == 4 && tyFlag == 1) {
                return "Account has been Frozen";
            } else if (acctStatus == 2 && tyFlag == 1) {
                return "Account is Marked as Inoperative";
            } else if (optStatus == 2 && tyFlag == 1) {
                return "Account is marked as Inoperative";
            }
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAccountByPurpose(String purpose) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acno from abb_parameter_info where purpose='" + purpose + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please fill proper 10 digit A/c no. For: " + purpose);
            }
            Vector element = (Vector) list.get(0);
            if (element.get(0) != null && !element.get(0).toString().equalsIgnoreCase("")
                    && element.get(0).toString().trim().length() == 10) {
                String accountNo = element.get(0).toString();
                return accountNo;
            } else {
                throw new ApplicationException("Please fill proper 10 digit A/c no. For: " + purpose);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getFinYearForBranch(String orgnBrCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select f_year from yearend where yearendflag='N' and brncode='" + orgnBrCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define yearend entry for branch: ");
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getBankCode() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define bank code.");
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String clearingOperationOnPosting(Float trsNo, Integer fyear, Double seqNo, String originDt, String sundryHead,
            Double sundryDrAmt, String authBy, String orgBrCode) throws ApplicationException {
        String result = "true";
        try {
            //Checking of bill_sundry_dt table.
            List list = em.createNativeQuery("select acno,details,amount from bill_sundry_dt where fyear = " + fyear + " and seqno=" + seqNo + "").getResultList();
            if (list.isEmpty()) {
                return result = "There is no data in bill sundry dt for:- fyear: " + fyear.toString() + " And SeqNo: " + seqNo.toString();
            }
            //Performing the dr transaction for sundry head.
//            Float trsNo = ftsPosting.getTrsNo();
            result = performGeneralTransaction(sundryHead, 1, 1, "", sundryDrAmt, trsNo, authBy, orgBrCode, "", "", "19000101", 3, authBy, "");
            if (!result.equalsIgnoreCase("true")) {
                return result;
            }
            //Updating the status in bill_sundry
            int insertResult = em.createNativeQuery("update bill_sundry set status='PAID',dt='" + ymd.format(new Date()) + "',"
                    + "lastupdateby='" + authBy + "' where fyear=" + fyear + " and seqno=" + seqNo + "").executeUpdate();
            if (insertResult <= 0) {
                return result = "Problem in bill sundry updation.";
            }
            //Now Cr all loan a/c and satisfy all the emis agains a/c and membe no.
            String branchPremiumHead = orgBrCode + getAccountByPurpose("PREMIUM-HEAD");
            String branchWellFareHead = orgBrCode + getAccountByPurpose("WELFARE-HEAD");

            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                String acno = element.get(0).toString();
                String details = element.get(1).toString();
                Double amount = Double.parseDouble(element.get(2).toString());

                String paidSno = "";
                result = satisfyRecovery(details, acno, amount, authBy, "");
                if (!result.contains("true")) {
                    return result;
                }
                paidSno = result.substring(result.indexOf(":") + 1);
                String creditAccount = "";
                if (details.equalsIgnoreCase("PINSP")) {
                    creditAccount = branchPremiumHead;
                } else if (details.equalsIgnoreCase("PWELL")) {
                    creditAccount = branchWellFareHead;
                } else {
                    creditAccount = acno;
                }

                result = performGeneralTransaction(creditAccount, 0, 1, "", amount, trsNo, authBy, orgBrCode, paidSno, acno, "19000101", 3, authBy, "");
                if (!result.toLowerCase().contains("true")) {
                    throw new ApplicationException(result);
                }
            }

            //Updating the status in bill sundry dt.
            insertResult = em.createNativeQuery("update bill_sundry_dt set status='PAID',dt='" + ymd.format(new Date()) + "',"
                    + "lastupdateby='" + authBy + "' where fyear=" + fyear + " and seqno=" + seqNo + "").executeUpdate();
            if (insertResult <= 0) {
                return result = "Problem in bill sundry dt updation.";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String isAccountofPostal(String acno) throws ApplicationException {
        String result = "N";
        try {
//            List list = em.createNativeQuery("select nre_scheme_flag from cbs_scheme_general_scheme_parameter_master where "
//                    + "scheme_code in(select scheme_code from cbs_loan_acc_mast_sec where acno='" + acno + "')").getResultList();
            List list = em.createNativeQuery("select * from share_holder where custid = "
                    + " (select custid from customerid where acno='" + acno + "')").getResultList();
            if (list.isEmpty()) {
                result = "N";
            } else {
                Vector element = (Vector) list.get(0);
                if (element.get(0) == null || element.get(0).toString().equals("")) {
                    result = "N";
                } else {
                    result = "Y";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List<PostalDemandPojo> getAllDemandOfAccount(String acno, String todayDt) throws ApplicationException {
        List<PostalDemandPojo> list = new ArrayList<PostalDemandPojo>();
        try {
            String dmdDueDt = "", dmdAmount = "";
            //Loan emi demand
            List emiList = em.createNativeQuery("select ifnull((sum(installamt)-sum(excessamt)),0) as overDueAmt,"
                    + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
                    + "where acno='" + acno + "' and status='Unpaid' and "
                    + "duedt<='" + todayDt + "'").getResultList();

            Vector element = (Vector) emiList.get(0);
            dmdAmount = element.get(0).toString();
            dmdDueDt = element.get(1) == null ? "" : element.get(1).toString();   //yyyyMMdd format
            list.add(createDemandTable(acno, "PLOAN", new BigDecimal(dmdAmount), dmdDueDt));

            //Loan a/c premium demand
//            emiList = em.createNativeQuery("select ifnull((sum(premium_amount)-sum(excess_amt)),0) as overDueAmt,"
//                    + "date_format(max(due_dt),'%Y%m%d') as maxDueDt from insurance_details where "
//                    + "acno='" + acno + "' and status='UNPAID' and "
//                    + "due_dt<='" + todayDt + "'").getResultList();

            emiList = em.createNativeQuery("select ifnull((sum(installamt)),0) as overDueAmt,"
                    + "date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment "
                    + "where acno='" + acno + "' and status='Unpaid' and "
                    + "duedt<='" + todayDt + "'").getResultList();

            element = (Vector) emiList.get(0);
            dmdAmount = element.get(0).toString();
            dmdDueDt = element.get(1) == null ? "" : element.get(1).toString();   //yyyyMMdd format
            //list.add(createDemandTable(acno, "PINSP", new BigDecimal(dmdAmount), dmdDueDt));    
            list.add(createDemandTable(acno, "P-RD", new BigDecimal(dmdAmount), dmdDueDt));

            //Loan a/c well fare demand
            emiList = em.createNativeQuery("select regfoliono from share_holder where custid in(select custid from customerid where acno='" + acno + "')").getResultList();
//            if (emiList.isEmpty()) {
//                throw new ApplicationException("There should be a member no for a/c: " + acno);
//            }
            if (!emiList.isEmpty()) {
                element = (Vector) emiList.get(0);
                String memberNo = element.get(0).toString();

                emiList = em.createNativeQuery("select ifnull((sum(insured_amount)-sum(excess_amt)),0) as overDueAmt,"
                        + "date_format(max(due_dt),'%Y%m%d') as maxDueDt from insurance_details "
                        + "where acno='" + memberNo + "' and status='UNPAID' "
                        + "and due_dt<='" + todayDt + "'").getResultList();

                element = (Vector) emiList.get(0);
                dmdAmount = element.get(0).toString();
                dmdDueDt = element.get(1) == null ? "" : element.get(1).toString();   //yyyyMMdd format
                list.add(createDemandTable(memberNo, "PWELL", new BigDecimal(dmdAmount), dmdDueDt));
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public PostalDemandPojo createDemandTable(String acno, String dmdType, BigDecimal overDueAmt, String dueDt) throws ApplicationException {
        PostalDemandPojo pojo = new PostalDemandPojo();
        try {
            pojo.setAcno(acno);
            pojo.setDmdType(dmdType);
            pojo.setOverDueAmt(overDueAmt);
            pojo.setDueDt(dueDt.equals("") ? "" : dmy.format(ymd.parse(dueDt)));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    public String processSingleRecovery(Float trsno, Float recno, String orgBrCode, String todayDt, Integer tranType,
            String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = null;
            Vector element = null;
            String result = "", loanAc = "", enterBy = "", tranOrgCode = "", tranDestCode = "", branchPremiumHead = "", branchWellFareHead = "";
            Double cramt = 0.0, dramt = 0.0, dmdAmount = 0.0;
            Integer tranDesc = 0;
            BigDecimal totDmdAmount = new BigDecimal("0");
            boolean remoteCheck = false;
            if (tranType == 0) {
                trsno = ftsPosting.getTrsNo();

                //Get all data regarding recno
                list = em.createNativeQuery("select acno,ifnull(cramt,0) as cramt,enterby,trandesc,org_brnid,dest_brnid "
                        + "from tokentable_credit where recno=" + recno + " and org_brnid = '" + orgBrCode + "'").getResultList();
                element = (Vector) list.get(0);
                loanAc = element.get(0).toString();
                cramt = Double.parseDouble(element.get(1).toString());
                enterBy = element.get(2).toString();
                tranDesc = Integer.parseInt(element.get(3).toString());
                tranOrgCode = element.get(4).toString();
                tranDestCode = element.get(5).toString();

                //Checking of total cr with total demand for an account.
                List<PostalDemandPojo> dmdList = getAllDemandOfAccount(loanAc, ymd.format(dmy.parse(todayDt)));
                for (PostalDemandPojo pojo : dmdList) {
                    totDmdAmount = totDmdAmount.add(pojo.getOverDueAmt());
                }
                if (new BigDecimal(cramt).compareTo(totDmdAmount) == 1) {
                    throw new ApplicationException("Cr amount should be less than or equal to total demand amount :" + totDmdAmount.toString());
                }
                //Preparing premium and well fare head.
                branchPremiumHead = loanAc.substring(0, 2) + getAccountByPurpose("PREMIUM-HEAD");
                branchWellFareHead = loanAc.substring(0, 2) + getAccountByPurpose("WELFARE-HEAD");
                //Remote Checking
                if (!tranOrgCode.equalsIgnoreCase(tranDestCode)) {
                    remoteCheck = true;
                }
                //Satisfy and insert related transactions.
                for (PostalDemandPojo obj : dmdList) {
                    if (obj.getDmdType().equals("PLOAN") && obj.getOverDueAmt().doubleValue() != 0) {
                        if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                            dmdAmount = obj.getOverDueAmt().doubleValue();
                            cramt = cramt - (obj.getOverDueAmt().doubleValue());
                        } else {
                            dmdAmount = cramt;
                            cramt = 0.0;
                        }

                        String paidSno = "";
                        result = satisfyRecovery("PLOAN", obj.getAcno(), dmdAmount, authBy, "");
                        if (!result.contains("true")) {
                            throw new ApplicationException(result);
                        }
                        paidSno = result.substring(result.indexOf(":") + 1);
                        if (remoteCheck == true) {
                            //Calling of CX for Loan A/c
                            result = interBranchFacade.cbsPostingSx(obj.getAcno(), 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
                                    paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                            result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
                                    paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                        } else {
                            result = performGeneralTransaction(obj.getAcno(), 0, 0, "", dmdAmount, trsno, enterBy, tranOrgCode,
                                    paidSno, "", "19000101", 3, authBy, "");
                        }
                        if (!result.toLowerCase().contains("true")) {
                            throw new ApplicationException(result);
                        }
                    }
//                    if (obj.getDmdType().equals("PINSP") && obj.getOverDueAmt().doubleValue() != 0) {
//                        if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
//                            dmdAmount = obj.getOverDueAmt().doubleValue();
//                            cramt = cramt - (obj.getOverDueAmt().doubleValue());
//                        } else {
//                            dmdAmount = cramt;
//                            cramt = 0.0;
//                        }
//                        if (dmdAmount != 0) {
//                            String paidSno = "";
//                            result = satisfyRecovery("PINSP", obj.getAcno(), dmdAmount, authBy);
//                            if (!result.contains("true")) {
//                                throw new ApplicationException(result);
//                            }
//                            paidSno = result.substring(result.indexOf(":") + 1);
//                            if (remoteCheck == true) {
//                                //Calling of CX for Loan A/c
//                                result = interBranchFacade.cbsPostingSx(branchPremiumHead, 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
//                                        paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
//                                        tranOrgCode, enterBy, authBy, trsno, "", "");
//                                if (!result.toLowerCase().contains("true")) {
//                                    throw new ApplicationException(result);
//                                }
//                                result = interBranchFacade.cbsPostingCx(branchPremiumHead, 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
//                                        paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
//                                        tranOrgCode, enterBy, authBy, trsno, "", "");
//                            } else {
//                                result = performGeneralTransaction(branchPremiumHead, 0, 0, "", dmdAmount, trsno, enterBy, tranOrgCode,
//                                        paidSno, "", "19000101", 3, authBy);
//                            }
//                            if (!result.toLowerCase().contains("true")) {
//                                throw new ApplicationException(result);
//                            }
//                        }
//                    }
                    if (obj.getDmdType().equals("P-RD") && obj.getOverDueAmt().doubleValue() != 0) {
                        if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                            dmdAmount = obj.getOverDueAmt().doubleValue();
                            cramt = cramt - (obj.getOverDueAmt().doubleValue());
                        } else {
                            dmdAmount = cramt;
                            cramt = 0.0;
                        }
                        if (dmdAmount != 0) {
                            String paidSno = "";
                            result = satisfyRecovery("P-RD", obj.getAcno(), dmdAmount, authBy, "");
                            if (!result.contains("true")) {
                                throw new ApplicationException(result);
                            }
                            paidSno = result.substring(result.indexOf(":") + 1);
                            if (remoteCheck == true) {
                                //Calling of CX for Loan A/c
                                result = interBranchFacade.cbsPostingSx(obj.getAcno(), 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
                                        paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                        tranOrgCode, enterBy, authBy, trsno, "", "");
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
                                        paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                        tranOrgCode, enterBy, authBy, trsno, "", "");
                            } else {
                                result = performGeneralTransaction(obj.getAcno(), 0, 0, "", dmdAmount, trsno, enterBy, tranOrgCode,
                                        paidSno, "", "19000101", 3, authBy, "");
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        }
                    }
                    if (obj.getDmdType().equals("PWELL") && obj.getOverDueAmt().doubleValue() != 0) {
                        if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                            dmdAmount = obj.getOverDueAmt().doubleValue();
                            cramt = cramt - (obj.getOverDueAmt().doubleValue());
                        } else {
                            dmdAmount = cramt;
                            cramt = 0.0;
                        }
                        if (dmdAmount != 0) {
                            String paidSno = "";
                            result = satisfyRecovery("PWELL", obj.getAcno(), dmdAmount, authBy, "");
                            if (!result.contains("true")) {
                                throw new ApplicationException(result);
                            }
                            paidSno = result.substring(result.indexOf(":") + 1);
                            if (remoteCheck == true) {
                                //Calling of CX for Loan A/c
                                result = interBranchFacade.cbsPostingSx(branchWellFareHead, 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
                                        paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                        tranOrgCode, enterBy, authBy, trsno, "", "");
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                result = interBranchFacade.cbsPostingCx(branchWellFareHead, 0, ymd.format(new Date()), dmdAmount, 0.0, 0,
                                        paidSno, 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                        tranOrgCode, enterBy, authBy, trsno, "", "");
                            } else {
                                result = performGeneralTransaction(branchWellFareHead, 0, 0, "", dmdAmount, trsno, enterBy, tranOrgCode,
                                        paidSno, "", "19000101", 3, authBy, "");
                            }
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        }
                    }
                }
//                int delQuery = em.createNativeQuery("delete from tokentable_credit where recno=" + recno + " and "
//                        + "org_brnid = '" + orgBrCode + "'").executeUpdate();
                int delQuery = em.createNativeQuery("update tokentable_credit set auth='Y', authby='" + authBy + "' WHERE RECNO=" + recno + " AND DT='" + ymd.format(new Date()) + "' AND ORG_BRNID = '" + orgBrCode + "'").executeUpdate();
                if (delQuery <= 0) {
                    throw new ApplicationException("There is problem in deletion for tokentable credit: " + recno.toString());
                }
            }
            ut.commit();
            return result = "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String processTrfRecovery(Float trsno, Float recno, String orgBrCode, String todayDt,
            Integer tranType, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            List list = null;
            Vector element = null;
            String result = "", loanAc = "", enterBy = "", tranOrgCode = "", tranDestCode = "", branchPremiumHead = "", branchWellFareHead = "";
            String instno = "", instDt = "";
            Double cramt = 0.0, dramt = 0.0, dmdAmount = 0.0, payBy = 0.0;
            Integer tranDesc = 0, ty = 0, iy = 0;

            //All cr a/c should be of postal in this batch.
            if (isNotPostalBatch(trsno).equalsIgnoreCase("true")) {
                throw new ApplicationException("All cr a/c should be of Member.");
            }
            //getting all batch data.
            list = em.createNativeQuery("select acno,ifnull(dramt,0) as dramt,ifnull(cramt,0) as cramt,ty,recno,org_brnid,"
                    + "dest_brnid,enterby,trandesc,payby,instno,date_format(instdt,'%Y%m%d') as instdt,iy from recon_trf_d where "
                    + "trsno=" + trsno + "").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no batch no: " + trsno);
            }
            //Identification of either it is remote or local batch.
            result = txnRemote.cbsDestEntryIdentification(trsno);
            if (result.equalsIgnoreCase("true")) {     //Remote Batch
                for (int i = 0; i < list.size(); i++) {     //Loop on batch data list.
                    element = (Vector) list.get(i);
                    loanAc = element.get(0).toString();
                    dramt = Double.parseDouble(element.get(1).toString());
                    cramt = Double.parseDouble(element.get(2).toString());
                    ty = Integer.parseInt(element.get(3).toString());
                    recno = Float.parseFloat(element.get(4).toString());
                    tranOrgCode = element.get(5).toString();
                    tranDestCode = element.get(6).toString();
                    enterBy = element.get(7).toString();
                    tranDesc = Integer.parseInt(element.get(8).toString());
                    payBy = Double.parseDouble(element.get(9).toString());
                    instno = element.get(10).toString();
                    if (element.get(11) == null || element.get(11).toString().equals("")) {
                        instDt = "19000101";
                    } else {
                        instDt = element.get(11).toString();
                    }
//                    iy = Integer.parseInt(element.get(12).toString());

                    if (tranOrgCode.equals(tranDestCode)) {
                        if (ty == 1) {
                            result = interBranchFacade.cbsPostingCx(loanAc, ty, ymd.format(new Date()), dramt, 0.0, 2,
                                    "", 0f, "", instno, instDt, payBy.intValue(), 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        } else if (ty == 0) {
                            result = trfRemoteSatisFaction(loanAc, getAllDemandOfAccount(loanAc, todayDt), cramt, trsno, recno,
                                    ty, instno, instDt, payBy.intValue(), tranDesc, tranDestCode, tranOrgCode, enterBy, authBy);
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        }
                    } else if (!tranOrgCode.equals(tranDestCode)) {
                        if (ty == 1) {
                            result = interBranchFacade.cbsPostingSx(loanAc, ty, ymd.format(new Date()), dramt, 0.0, 2,
                                    "", 0f, "", instno, instDt, payBy.intValue(), 0f, ftsPosting.getRecNo(), tranDesc, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        } else if (ty == 0) {
                            result = trfRemoteSatisFaction(loanAc, getAllDemandOfAccount(loanAc, todayDt), cramt, trsno, recno,
                                    ty, instno, instDt, payBy.intValue(), tranDesc, tranDestCode, tranOrgCode, enterBy, authBy);
                            if (!result.toLowerCase().contains("true")) {
                                throw new ApplicationException(result);
                            }
                        }
                    }
                }
            } else {    //Local Batch
                //Preparing premium and well fare head.
                branchPremiumHead = orgBrCode + getAccountByPurpose("PREMIUM-HEAD");
                branchWellFareHead = orgBrCode + getAccountByPurpose("WELFARE-HEAD");
                for (int i = 0; i < list.size(); i++) {     //Loop on batch data list.
                    element = (Vector) list.get(i);
                    loanAc = element.get(0).toString();
                    dramt = Double.parseDouble(element.get(1).toString());
                    cramt = Double.parseDouble(element.get(2).toString());
                    ty = Integer.parseInt(element.get(3).toString());
                    recno = Float.parseFloat(element.get(4).toString());
                    tranOrgCode = element.get(5).toString();
                    tranDestCode = element.get(6).toString();
                    enterBy = element.get(7).toString();
                    tranDesc = Integer.parseInt(element.get(8).toString());
                    payBy = Double.parseDouble(element.get(9).toString());
                    instno = element.get(10).toString();
                    if (element.get(11) == null || element.get(11).toString().equals("")) {
                        instDt = "19000101";
                    } else {
                        instDt = element.get(11).toString();
                    }
                    iy = Integer.parseInt(element.get(12).toString());

                    if (ty == 1) {
                        result = ftsPosting.insertRecons(ftsPosting.getAccountNature(loanAc), loanAc, ty, dramt, ymd.format(new Date()),
                                ymd.format(new Date()), 2, "", enterBy, trsno, ymd.format(new Date()), ftsPosting.getRecNo(), "Y", authBy, tranDesc,
                                payBy.intValue(), instno, instDt, 0f, "", "", iy, "", 0f, "", "", tranOrgCode,
                                tranDestCode, 0, "", "", "");
                        if (!result.equalsIgnoreCase("true")) {
                            throw new ApplicationException(result);
                        }
                        int updateResult = em.createNativeQuery("update recon_trf_d set auth='Y',authby='" + authBy + "' where trsno=" + trsno + " and recno=" + recno + "").executeUpdate();
                        if (updateResult <= 0) {
                            throw new ApplicationException("Updation problem in recon_trf_d for: Trsno- " + trsno + " ,Recno- " + recno);
                        }
                    } else if (ty == 0) {
                        List<PostalDemandPojo> dmdList = getAllDemandOfAccount(loanAc, todayDt);
                        //Satisfy and insert related transactions.
                        for (PostalDemandPojo obj : dmdList) {
                            if (obj.getDmdType().equals("PLOAN") && obj.getOverDueAmt().doubleValue() != 0) {
                                if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                                    dmdAmount = obj.getOverDueAmt().doubleValue();
                                    cramt = cramt - (obj.getOverDueAmt().doubleValue());
                                } else {
                                    dmdAmount = cramt;
                                    cramt = 0.0;
                                }
                                String paidSno = "";
                                result = satisfyRecovery("PLOAN", obj.getAcno(), dmdAmount, authBy, "");
                                if (!result.contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                paidSno = result.substring(result.indexOf(":") + 1);
                                result = performGeneralTransaction(obj.getAcno(), 0, 2, "", dmdAmount, trsno, enterBy, tranOrgCode,
                                        paidSno, "", "19000101", 3, authBy, "");
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                            }
//                            if (obj.getDmdType().equals("PINSP") && obj.getOverDueAmt().doubleValue() != 0) {
//                                if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
//                                    dmdAmount = obj.getOverDueAmt().doubleValue();
//                                    cramt = cramt - (obj.getOverDueAmt().doubleValue());
//                                } else {
//                                    dmdAmount = cramt;
//                                    cramt = 0.0;
//                                }
//                                if (dmdAmount != 0) {
//                                    String paidSno = "";
//                                    result = satisfyRecovery("PINSP", obj.getAcno(), dmdAmount, authBy);
//                                    if (!result.contains("true")) {
//                                        throw new ApplicationException(result);
//                                    }
//                                    paidSno = result.substring(result.indexOf(":") + 1);
//                                    result = performGeneralTransaction(branchPremiumHead, 0, 2, "", dmdAmount, trsno, enterBy, tranOrgCode,
//                                            paidSno, obj.getAcno(), "19000101", 3, authBy);
//                                    if (!result.toLowerCase().contains("true")) {
//                                        throw new ApplicationException(result);
//                                    }
//                                }
//                            }
                            if (obj.getDmdType().equals("P-RD") && obj.getOverDueAmt().doubleValue() != 0) {
                                if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                                    dmdAmount = obj.getOverDueAmt().doubleValue();
                                    cramt = cramt - (obj.getOverDueAmt().doubleValue());
                                } else {
                                    dmdAmount = cramt;
                                    cramt = 0.0;
                                }
                                if (dmdAmount != 0) {
                                    String paidSno = "";
                                    result = satisfyRecovery("P-RD", obj.getAcno(), dmdAmount, authBy, "");
                                    if (!result.contains("true")) {
                                        throw new ApplicationException(result);
                                    }
                                    paidSno = result.substring(result.indexOf(":") + 1);
                                    result = performGeneralTransaction(obj.getAcno(), 0, 2, "", dmdAmount, trsno, enterBy, tranOrgCode,
                                            paidSno, obj.getAcno(), "19000101", 3, authBy, "");
                                    if (!result.toLowerCase().contains("true")) {
                                        throw new ApplicationException(result);
                                    }
                                }
                            }
                            if (obj.getDmdType().equals("PWELL") && obj.getOverDueAmt().doubleValue() != 0) {
                                if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                                    dmdAmount = obj.getOverDueAmt().doubleValue();
                                    cramt = cramt - (obj.getOverDueAmt().doubleValue());
                                } else {
                                    dmdAmount = cramt;
                                    cramt = 0.0;
                                }
                                if (dmdAmount != 0) {
                                    String paidSno = "";
                                    result = satisfyRecovery("PWELL", obj.getAcno(), dmdAmount, authBy, "");
                                    if (!result.contains("true")) {
                                        throw new ApplicationException(result);
                                    }
                                    paidSno = result.substring(result.indexOf(":") + 1);
                                    result = performGeneralTransaction(branchWellFareHead, 0, 2, "", dmdAmount, trsno, enterBy, tranOrgCode,
                                            paidSno, obj.getAcno(), "19000101", 3, authBy, "");
                                    if (!result.toLowerCase().contains("true")) {
                                        throw new ApplicationException(result);
                                    }
                                }
                            }
                        }
                        int delQuery = em.createNativeQuery("delete from recon_trf_d where trsno=" + trsno + " and recno=" + recno + "").executeUpdate();
                        if (delQuery <= 0) {
                            throw new ApplicationException("There is problem in deletion from recon_trf_d: trsno: " + recno.toString() + "recno: " + recno);
                        }
                    }
                }
            }
            //End Process
            ut.commit();
            return result = "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String trfRemoteSatisFaction(String loanAc, List<PostalDemandPojo> dmdList, Double cramt,
            Float trsno, Float recno, Integer ty, String instno, String instDt, Integer payBy,
            Integer tranDesc, String tranDestCode, String tranOrgCode, String enterBy, String authBy) throws ApplicationException {

        String result = "true", branchPremiumHead = "", branchWellFareHead = "";
        try {
            Double dmdAmount = 0.0;
            branchPremiumHead = loanAc.substring(0, 2) + getAccountByPurpose("PREMIUM-HEAD");
            branchWellFareHead = loanAc.substring(0, 2) + getAccountByPurpose("WELFARE-HEAD");
            //Satisfy and insert related transactions.
            for (PostalDemandPojo obj : dmdList) {
                if (obj.getDmdType().equals("PLOAN") && obj.getOverDueAmt().doubleValue() != 0) {
                    if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                        dmdAmount = obj.getOverDueAmt().doubleValue();
                        cramt = cramt - (obj.getOverDueAmt().doubleValue());
                    } else {
                        dmdAmount = cramt;
                        cramt = 0.0;
                    }
                    String paidSno = "";
//                    result = satisfyRecovery("PLOAN", obj.getAcno(), dmdAmount, authBy);
//                    if (!result.contains("true")) {
//                        throw new ApplicationException(result);
//                    }
                    paidSno = result.substring(result.indexOf(":") + 1);
                    if (tranOrgCode.equals(tranDestCode)) {
                        result = interBranchFacade.cbsPostingCx(obj.getAcno(), ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
                                "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
                                tranOrgCode, enterBy, authBy, trsno, "", "");
                    } else {
                        result = interBranchFacade.cbsPostingSx(obj.getAcno(), ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
                                "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
                                tranOrgCode, enterBy, authBy, trsno, "", "");
                    }
                    if (!result.toLowerCase().contains("true")) {
                        throw new ApplicationException(result);
                    }
                }
                if (obj.getDmdType().equals("P-RD") && obj.getOverDueAmt().doubleValue() != 0) {
                    if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                        dmdAmount = obj.getOverDueAmt().doubleValue();
                        cramt = cramt - (obj.getOverDueAmt().doubleValue());
                    } else {
                        dmdAmount = cramt;
                        cramt = 0.0;
                    }
                    if (dmdAmount != 0) {
                        String paidSno = "";
                        result = satisfyRecovery("P-RD", obj.getAcno(), dmdAmount, authBy, "");
                        if (!result.contains("true")) {
                            throw new ApplicationException(result);
                        }
                        paidSno = result.substring(result.indexOf(":") + 1);
                        if (tranOrgCode.equals(tranDestCode)) {
                            result = interBranchFacade.cbsPostingCx(obj.getAcno(), ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
                                    "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                        } else {
                            result = interBranchFacade.cbsPostingSx(obj.getAcno(), ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
                                    "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                        }
                        if (!result.toLowerCase().contains("true")) {
                            throw new ApplicationException(result);
                        }
                    }
                }
//                if (obj.getDmdType().equals("PINSP") && obj.getOverDueAmt().doubleValue() != 0) {
//                    if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
//                        dmdAmount = obj.getOverDueAmt().doubleValue();
//                        cramt = cramt - (obj.getOverDueAmt().doubleValue());
//                    } else {
//                        dmdAmount = cramt;
//                        cramt = 0.0;
//                    }
//                    if (dmdAmount != 0) {
//                        String paidSno = "";
//                        result = satisfyRecovery("PINSP", obj.getAcno(), dmdAmount, authBy);
//                        if (!result.contains("true")) {
//                            throw new ApplicationException(result);
//                        }
//                        paidSno = result.substring(result.indexOf(":") + 1);
//                        if (tranOrgCode.equals(tranDestCode)) {
//                            result = interBranchFacade.cbsPostingCx(branchPremiumHead, ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
//                                    "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
//                                    tranOrgCode, enterBy, authBy, trsno, "", "");
//                        } else {
//                            result = interBranchFacade.cbsPostingSx(branchPremiumHead, ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
//                                    "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
//                                    tranOrgCode, enterBy, authBy, trsno, "", "");
//                        }
//                        if (!result.toLowerCase().contains("true")) {
//                            throw new ApplicationException(result);
//                        }
//                    }
//                }
                if (obj.getDmdType().equals("PWELL") && obj.getOverDueAmt().doubleValue() != 0) {
                    if ((cramt - (obj.getOverDueAmt().doubleValue())) >= 0) {
                        dmdAmount = obj.getOverDueAmt().doubleValue();
                        cramt = cramt - (obj.getOverDueAmt().doubleValue());
                    } else {
                        dmdAmount = cramt;
                        cramt = 0.0;
                    }
                    if (dmdAmount != 0) {
                        String paidSno = "";
                        result = satisfyRecovery("PWELL", obj.getAcno(), dmdAmount, authBy, "");
                        if (!result.contains("true")) {
                            throw new ApplicationException(result);
                        }
                        paidSno = result.substring(result.indexOf(":") + 1);
                        if (tranOrgCode.equals(tranDestCode)) {
                            result = interBranchFacade.cbsPostingCx(branchWellFareHead, ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
                                    "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                        } else {
                            result = interBranchFacade.cbsPostingSx(branchWellFareHead, ty, ymd.format(new Date()), dmdAmount, 0.0, 2,
                                    "", 0f, "", instno, instDt, payBy, 0f, ftsPosting.getRecNo(), 67, tranDestCode,
                                    tranOrgCode, enterBy, authBy, trsno, "", "");
                        }
                        if (!result.toLowerCase().contains("true")) {
                            throw new ApplicationException(result);
                        }
                    }
                }
            }
            int delQuery = em.createNativeQuery("delete from recon_trf_d where trsno=" + trsno + " and recno=" + recno + "").executeUpdate();
            if (delQuery <= 0) {
                throw new ApplicationException("There is problem in deletion from recon_trf_d: trsno: " + recno.toString() + "recno: " + recno);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String checkNreFlagInBatch(Float trsno) throws ApplicationException {
        String msg = null;
        //List getDataList = em.createNativeQuery("select acno from recon_trf_d where trsno = cast(" + trsno + " as unsigned) and ty=0").getResultList();
        List getDataList = em.createNativeQuery("select t.acno from recon_trf_d t, accountmaster a where t.trsno = cast(" + trsno + " as unsigned) and t.ty=0 and t.acno = a.acno "
                + " and a.accttype in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "'))").getResultList();
        if (getDataList.size() > 0) {
            for (int i = 0; i < getDataList.size(); i++) {
                Vector getDateVect = (Vector) getDataList.get(i);
                String acno = getDateVect.get(0).toString();
                if (isAccountofPostal(acno).equalsIgnoreCase("Y")) {
                    return msg = "true";
                } else {
                    msg = "false";
                }
            }
        } else {
            return "Please check the Bach No, You have passed.";
        }
        return msg;
    }

    public String isNotPostalBatch(Float trsno) throws ApplicationException {
        String msg = null;
        List getDataList = em.createNativeQuery("select acno from recon_trf_d where trsno = cast(" + trsno + " as unsigned) and ty=0").getResultList();
        if (getDataList.size() > 0) {
            for (int i = 0; i < getDataList.size(); i++) {
                Vector getDateVect = (Vector) getDataList.get(i);
                String acno = getDateVect.get(0).toString();
                if (!isAccountofPostal(acno).equalsIgnoreCase("Y")) {
                    return msg = "true";
                } else {
                    msg = "false";
                }
            }
        } else {
            return "Please check the Bach No, You have passed.";
        }
        return msg;
    }

    public String firstAndLastPaidSno(String allPaidSno) throws ApplicationException {
        String firstAndLastSno = "";
        try {
            allPaidSno = allPaidSno.substring(allPaidSno.indexOf(":") + 1);
            String[] arr = allPaidSno.split(":");
            if (arr.length > 1) {
                firstAndLastSno = ":" + arr[0] + ":" + arr[arr.length - 1];
            } else {
                firstAndLastSno = ":" + arr[0];
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return firstAndLastSno;
    }

    public List getDesgType(String dt, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct a.desig_code from fidility_accountmaster a , fidility_schedule b where a.acno = b.acno "
                    + " and b.dt<='" + dt + "' and b.auth ='Y' and b.status ='UNPAID' and a.brncode = '" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getHeadFidility(String desg) throws ApplicationException {
        try {
            return em.createNativeQuery("select crGlhead,drGlhead from fidility_premium_slab where desig_code = '" + desg + "' and effDt = "
                    + " (select max(effdt) from fidility_premium_slab where desig_code = '" + desg + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getSumFidility(String dt, String desg, String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(sum(b.amt),0) from fidility_accountmaster a,fidility_schedule b where a.acno = b.acno and "
                    + " b.dt<='" + dt + "' and b.auth ='Y' and b.status ='UNPAID' and a.desig_code ='" + desg + "' and a.brncode ='" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<LoanIntCalcList> fidilityListRep(String toDt, String desg, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        try {
            List fidilityMethodList = em.createNativeQuery("select a.desig_code,a.custname,a.bondamt,a.premiumamt,b.acno,b.amt,date_format(b.dt,'%d/%m/%Y') from "
                    + " fidility_accountmaster a,fidility_schedule b where a.acno = b.acno and b.dt<='" + toDt + "' and b.auth ='Y' and b.status ='UNPAID' "
                    + " and a.desig_code ='" + desg + "' and a.brncode ='" + brnCode + "' order by a.desig_code,acno,dt,amt").getResultList();
            if (!fidilityMethodList.isEmpty()) {
                for (int i = 0; i < fidilityMethodList.size(); i++) {
                    Vector getAllVect = (Vector) fidilityMethodList.get(i);
                    LoanIntCalcList it = new LoanIntCalcList();
                    it.setDetails(getAllVect.get(0).toString());
                    it.setCustName(getAllVect.get(1).toString());
                    it.setTotalInt(Double.parseDouble(getAllVect.get(2).toString()));
                    it.setProduct(Double.parseDouble(getAllVect.get(3).toString()));
                    it.setAcNo(getAllVect.get(4).toString());
                    it.setClosingBal(Double.parseDouble(getAllVect.get(5).toString()));
                    it.setLastDt(getAllVect.get(6).toString());
                    intDetails.add(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intDetails;
    }

    public String fidilityPost(String dt, String desg, String brncode, String crhead, String drhead, double amt, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String result = "";
            float dTrSNo = 0;
            float dRecNo = 0;
            String details = "";
            String crGlAcNo = brncode + crhead + "01";
            String drGlAcNo = brncode + drhead + "01";
            ut.begin();
            List tempBdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brncode + "'").getResultList();
            if (tempBdList.isEmpty()) {
                throw new ApplicationException("Dayend has been already done for branch :" + brncode);
            }
            Vector tempBdVect = (Vector) tempBdList.get(0);
            String tempBd = tempBdVect.get(0).toString();

            dTrSNo = ftsPosting.getTrsNo();
            dRecNo = ftsPosting.getRecNo();

            details = "Fidility Adjustment on " + dt;

            Query insertQuery5 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,ENTERBY,"
                    + "AUTHBY,AUTH,recno,trsno,trandesc,org_brnid,dest_brnid)"
                    + "values ('" + drGlAcNo + "'," + 1 + ",'" + dt + "','" + dt + "'," + amt + "," + 2 + ",'" + details + "','"
                    + authBy + "','SYSTEM','Y'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brncode + "','" + brncode + "')");
            int var7 = insertQuery5.executeUpdate();
            if (var7 <= 0) {
                throw new ApplicationException("Error! Inserting transaction for GL account.");
            }

            result = ftsPosting.updateBalance(CbsConstant.PAY_ORDER, drGlAcNo, amt, 0, "", "");
            if (!result.equalsIgnoreCase("True")) {
                throw new ApplicationException("Error in Updating Balance.");
            }

            dRecNo = ftsPosting.getRecNo();
            Query insert1 = em.createNativeQuery("insert into gl_recon(acno,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,auth,enterby,"
                    + "authby,trandesc,details,payby,iy,org_brnid,dest_brnid,adviceNo,adviceBrnCode) values('" + crGlAcNo + "','"
                    + dt + "','" + dt + "',0," + amt + ",0,2 ," + dRecNo + ","
                    + dTrSNo + ",'Y','" + authBy + "','SYSTEM',13,'" + details + "',3,1,'" + brncode + "','" + brncode + "','','')");
            int var1 = insert1.executeUpdate();

            if (var1 <= 0) {
                throw new ApplicationException("Error! Inserting transaction for GL account.");
            }

            result = ftsPosting.updateBalance(CbsConstant.PAY_ORDER, crGlAcNo, 0, amt, "", "");
            if (!result.equalsIgnoreCase("True")) {
                throw new ApplicationException("Error in Updating Balance.");
            }

            Query updateFidilityQuery = em.createNativeQuery("UPDATE fidility_schedule fs INNER JOIN fidility_accountmaster fa ON fs.acno = fa.acno "
                    + " set fs.status='PAID',trsno=" + dTrSNo + ",adjDate = '" + dt + "'  where fs.dt<='" + dt + "' and fs.auth ='Y' and fs.status ='UNPAID' and fa.desig_code ='" + desg + "' and fa.brncode ='" + brncode + "'");
            Integer updateCaRecon = updateFidilityQuery.executeUpdate();
            if (updateCaRecon == 0) {
                throw new ApplicationException("Error! In Data Updation");
            } else {
                ut.commit();
                result = "Fidility Adjusted successfully. Generated batch no is " + dTrSNo;
            }
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String getTheftAmt(String acNo) throws ApplicationException {
        String theftAmt = "";
        try {
            List theftAmtList = em.createNativeQuery("select RdInstal as overDueAmt from accountmaster where acno  = '" + acNo + "'").getResultList();
            if (!theftAmtList.isEmpty()) {
                Vector element = (Vector) theftAmtList.get(0);
                theftAmt = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return theftAmt;
    }

    public List fileGenChecking(String brnCode, String placeOfWorking, String fromDt, String toDt) throws ApplicationException {
        try {
//            return em.createNativeQuery("select * from cbs_loan_dmd_info where SNO = " + Integer.parseInt(srlNo) + " and POSTINGDT between '" + dt1 + "' and '" + dt2 + "'").getResultList();
            return em.createNativeQuery("select * from cbs_loan_dmd_info where brncode = '" + brnCode + "' and OFFICEID = '" + placeOfWorking + "' and fromdt = '" + fromDt + "' and todt='" + toDt + "' ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }


    }

    public String getflag(String brnCode, String placeOfWorking, String toDt) throws ApplicationException {
        String flag = "";
        try {
            List flagList = em.createNativeQuery("select FLAG from cbs_loan_dmd_info where brncode = '" + brnCode + "' and OFFICEID = '" + placeOfWorking + "' and todt<='" + toDt + "' and flag = 'A'").getResultList();
            if (!flagList.isEmpty()) {
                Vector element = (Vector) flagList.get(0);
                flag = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return flag;
    }

    public List getFileGenDt(String place) throws ApplicationException {
        try {
            return em.createNativeQuery("select SNO,flag,date_format(TODT,'%Y%m%d') from cbs_loan_dmd_info where SNO = (select max(DMD_SRL_NUM) from cbs_loan_dmd_table where CHECK_ROLL_AREA = '" + place + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getValueDt(String srNo, String toDt) throws ApplicationException {
        String valueDt = "";
        int gracePdDays = 0;
        try {
            // loanReportRemote.getSchemeCodeAcNoWise(srNo);
            List emiLateApplicableList = em.createNativeQuery("select apply_late_fee_for_delayed_payment, grace_period_for_late_fee_months, "
                    + " grace_period_for_late_fee_days from cbs_scheme_loan_interest_details where scheme_code='" + loanReportRemote.getSchemeCodeAcNoWise(srNo) + "'").getResultList();
            if (!emiLateApplicableList.isEmpty()) {
                Vector emiLateApplicableVect = (Vector) emiLateApplicableList.get(0);
                String lateFeeApp = emiLateApplicableVect.get(0).toString();
                Integer gracePdMonth = Integer.parseInt(emiLateApplicableVect.get(1).toString());
                gracePdDays = Integer.parseInt(emiLateApplicableVect.get(2).toString());
            }
            String graceDt = CbsUtil.dateAdd(toDt, gracePdDays);
            if (ymd.parse(toDt).after(date) || ymd.parse(toDt).equals(date)) {
                valueDt = ymd.format(date);
            } else {
                if (ymd.parse(graceDt).after(date) || ymd.parse(graceDt).equals(date)) {
                    valueDt = toDt;
                } else {
                    //long graceDiff = CbsUtil.dayDiff(ymd.parse(graceDt), date);
                    //valueDt = CbsUtil.dateAdd(toDt, (int) graceDiff);
                    valueDt = ymd.format(date);
                }
            }
            return valueDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getValueDtPostal(String srNo, String toDt) throws ApplicationException {
        String valueDt = "";
        int gracePdDays = 0;
        try {
            gracePdDays = Integer.parseInt(srNo);
            String graceDt = CbsUtil.dateAdd(toDt, gracePdDays);
            if (ymd.parse(toDt).after(date) || ymd.parse(toDt).equals(date)) {
                valueDt = ymd.format(date);
            } else {
                if (ymd.parse(graceDt).after(date) || ymd.parse(graceDt).equals(date)) {
                    valueDt = toDt;
                } else {
                    valueDt = ymd.format(date);
                }
            }
            return valueDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getDemandFlowId(String acNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select principal_flow_id,int_demand_flow_id,disbursement_flow_id, collection_flow_id,penal_int_demand_flow_id, overdue_int_demand_flow_id, past_due_collection_flow_id,charge_demand_flow_id from cbs_scheme_loan_prepayment_details where scheme_code =  '" + loanReportRemote.getSchemeCodeAcNoWise(acNo) + "' ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getsrNoByArea(String place) throws ApplicationException {
        String srlNo = "0";
        try {
            List srlList = em.createNativeQuery("select ifnull(max(DMD_SRL_NUM),0) from cbs_loan_dmd_table where  CHECK_ROLL_AREA = '" + place + "'").getResultList();
            if (!srlList.isEmpty()) {
                Vector element = (Vector) srlList.get(0);
                srlNo = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return srlNo;
    }

    public List getFrdtTodtByArea(String place) throws ApplicationException {
        try {
            return em.createNativeQuery("select date_format(FROMDT,'%Y%m%d'),date_format(TODT,'%Y%m%d') from cbs_loan_dmd_info where sno= (select max(SNO)from cbs_loan_dmd_info where OFFICEID = '" + place + "' and flag = 'A')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String armyInterestPostParameter() throws ApplicationException {
        String code = "0";
        try {
            List srlList = em.createNativeQuery("select CODE from cbs_parameterinfo where name = 'INTEREST_POST_CHECK_ARMY'").getResultList();
            if (!srlList.isEmpty()) {
                Vector element = (Vector) srlList.get(0);
                code = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return code;
    }

    public List interestPostChecking(String brnCode, String code, String frDt, String toDt) throws ApplicationException {
        try {
            if (ftsPosting.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                return em.createNativeQuery("select * from cbs_loan_acctype_interest_parameter where ac_type in(select distinct substring(acno,3,2) from loan_recon where substring(acno,1,2) ='" + brnCode + "' and substring(acno,3,2) not in(select AcctCode from accounttypemaster where acctnature = '" + CbsConstant.DEMAND_LOAN + "') and dt<='" + toDt + "'group by acno having(cast(sum(cramt-dramt) as decimal(25,2)) <>0)) and FROM_DT = '" + frDt + "' and to_dt = '" + toDt + "' and brncode = '" + brnCode + "' and POST_FLAG = 'N'").getResultList();
            } else {
                return em.createNativeQuery("select * from cbs_loan_acctype_interest_parameter where ac_type in(" + code + ") and FROM_DT = '" + frDt + "' and to_dt = '" + toDt + "' and brncode = '" + brnCode + "' and POST_FLAG = 'N'").getResultList();
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<ChargesObject> calculateInsPremium(String brnCode, String acType, String dt) throws ApplicationException {
        List<ChargesObject> tempTable = new ArrayList<ChargesObject>();
        try {
            List result = em.createNativeQuery("select a.acno,a.custname, sum(c.cramt-c.dramt) from accountmaster a, ca_recon c "
                    + "where substring(c.acno,3,2)='" + acType + "' and c.valuedt <='" + dt + "' and a.acno = c.acno and a.CurBrCode = '" + brnCode + "' "
                    + "group by c.acno having sum(c.cramt-c.dramt) >0").getResultList();

            if (result.isEmpty()) {
                ChargesObject temp = new ChargesObject();
                temp.setMsg("Please Check The Date Entered !!!");
                tempTable.add(temp);
                return tempTable;
            } else {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    ChargesObject temp = new ChargesObject();
                    temp.setMsg("True");
                    temp.setAcNo(ele.get(0).toString());
                    temp.setCustName(ele.get(1).toString());
                    temp.setLimit(Float.parseFloat(ele.get(2).toString()));
                    tempTable.add(temp);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tempTable;
    }

    public String postPremium(List<ChargesObject> lipLst, String crHead, double totAmt, String dt, String enterBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "True";
        try {
            ut.begin();
            float dTrSNo = 0;
            float dRecNo = 0;
            dTrSNo = ftsPosting.getTrsNo();
            String details = "Insurance Premium Till " + dt;
            for (ChargesObject obj : lipLst) {
                dRecNo = ftsPosting.getRecNo();
                String acno = obj.getAcNo();
                float amt = obj.getLimit();
                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                        + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                        + "dest_brnid,tran_id,adviceno,advicebrncode)"
                        + "values ('" + acno + "','" + ftsPosting.getCustName(acno) + "',date_format(curdate(),'%Y%m%d')," + dt + ",0," + amt + ","
                        + 1 + "," + 2 + "," + dRecNo + ",'',null," + 3 + ",1,"
                        + "'Y','SYSTEM',0,0,'','" + details + "','" + enterBy + "'," + dTrSNo + ",CURRENT_TIMESTAMP,'',"
                        + "'" + brnCode + "','" + acno.substring(0, 2) + "',0,'','')").executeUpdate();

                if (insertResult <= 0) {
                    throw new ApplicationException("Problem in premium Insertion in recon_trf_d");
                } else {
                    String result = ftsPosting.insertRecons(ftsPosting.getAccountNature(acno), acno, 1, amt, ymd.format(new Date()), dt, 2, details, "SYSTEM",
                            dTrSNo, ymdhms.format(new Date()), dRecNo, "Y", enterBy, 0, 3, "", null, 0f, "",
                            "", 1, "", 0f, "", "", brnCode, acno.substring(0, 2), 0, "", "", "");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException(result);
                    } else {
                        result = ftsPosting.updateBalance(ftsPosting.getAccountNature(acno), acno, 0, amt, "Y", "Y");
                        if (!result.equalsIgnoreCase("true")) {
                            throw new ApplicationException(result);
                        }
                    }
                }
            }
            dRecNo = ftsPosting.getRecNo();
            int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                    + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                    + "dest_brnid,tran_id,adviceno,advicebrncode)"
                    + "values ('" + crHead + "','" + ftsPosting.getCustName(crHead) + "',date_format(curdate(),'%Y%m%d')," + dt + "," + totAmt + ",0,"
                    + 0 + "," + 2 + "," + dRecNo + ",'',null," + 3 + ",1,"
                    + "'Y','SYSTEM',0,0,'','" + details + "','" + enterBy + "'," + dTrSNo + ",CURRENT_TIMESTAMP,'',"
                    + "'" + brnCode + "','" + crHead.substring(0, 2) + "',0,'','')").executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Problem in premium Insertion in recon_trf_d");
            } else {
                String result = ftsPosting.insertRecons(ftsPosting.getAccountNature(crHead), crHead, 0, totAmt, ymd.format(new Date()), dt, 2, details, "SYSTEM",
                        dTrSNo, ymdhms.format(new Date()), dRecNo, "Y", enterBy, 0, 3, "", null, 0f, "",
                        "", 1, "", 0f, "", "", brnCode, crHead.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    throw new ApplicationException(result);
                } else {
                    result = ftsPosting.updateBalance(ftsPosting.getAccountNature(crHead), crHead, totAmt, 0, "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException(result);
                    }
                }
            }
            ut.commit();
            return msg + dTrSNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String getCustNameByFolioNo(String folioNo) throws ApplicationException {
        String custName = "";
        try {
            List custList = em.createNativeQuery("select custname from cbs_customer_master_detail where customerid = (select custid from share_holder where regfoliono = '" + folioNo + "')").getResultList();
            if (!custList.isEmpty()) {
                Vector element = (Vector) custList.get(0);
                custName = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return custName;
    }

    public String isReoover(String brnCode, String place) throws ApplicationException {
        String flag = "";
        try {
            List flagList = em.createNativeQuery("select FLAG from cbs_loan_dmd_info where brncode = '" + brnCode + "' and OFFICEID = '" + place + "' and SNO in (select ifnull(max(DMD_SRL_NUM),0) from cbs_loan_dmd_table where  CHECK_ROLL_AREA = '" + place + "')").getResultList();
            if (!flagList.isEmpty()) {
                Vector element = (Vector) flagList.get(0);
                flag = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return flag;
    }

    public List getBranchPlaceOfWorking1(String brncode) throws ApplicationException {
        try {
            // List tableResultList = em.createNativeQuery("select distinct(s.area) from share_holder s, branchmaster b,cbs_ref_rec_type c where s.alphacode = b.alphacode and b.brncode = " + Integer.parseInt(brncode) + " and s.area = c.ref_code order by ORDER_BY").getResultList();
            List tableResultList = em.createNativeQuery("select distinct(s.area) from share_holder s, branchmaster b,cbs_ref_rec_type c where s.alphacode = b.alphacode and b.brncode = " + Integer.parseInt(brncode) + " and c.ref_rec_no = '019' and s.area = c.ref_code order by ORDER_BY").getResultList();
            return tableResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getDemandByNature(String branch, String actNature, String tillDt) throws ApplicationException {
        double demandAmt = 0d;
        List result = em.createNativeQuery("select cast(sum(installamt) as decimal(25,2)) DemandAmt from emidetails em,"
                + "(select acno,cast(sum(cramt-dramt) as decimal(25,2)) outbal from loan_recon a,accounttypemaster b "
                + "where substring(a.acno,3,2)= b.acctcode and acctnature = '" + actNature + "'and dt<='" + tillDt + "' "
                + "group by acno having(outbal <>0)) lo where em.acno= lo.acno and substring(lo.acno,1,2)='" + branch + "' "
                + "and em.status='Unpaid' and em.duedt<='" + tillDt + "' ").getResultList();

        if (!result.isEmpty()) {
            Vector vtr = (Vector) result.get(0);
            demandAmt = Double.parseDouble(vtr.get(0).toString());
        }
        return demandAmt;
    }

    public String generateExcelFileBasedOnPlaceOfWorkingOef(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy, String range) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String fileName = "", prinFlowId = "", intFlowId = "", dmdDueDt = "";

        double balance = 0d, installmentAmt = 0d, intAmt = 0d, emiAmt = 0d;
        try {
            ut.begin();
            //Generation of folder where we write the file.
            String writeFolderPath = "";
            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/POSTAL/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/POSTAL/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }
            //Get max DMD_SRL_NUM no.
            int dmdSrlNo = getDmdSrlNo();
            List schNoList = em.createNativeQuery("select ifnull(max(shdl_num),0)+1 as sno from cbs_loan_dmd_table where DMD_SRL_NUM='" + dmdSrlNo + "' ").getResultList();
            Vector schNoVect = (Vector) schNoList.get(0);
            int schNo = Integer.parseInt(schNoVect.get(0).toString());
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
            cell.setCellValue("ACCOUNT NO");
            cell = row.createCell(6);
            cell.setCellValue("CODE_DED1");
            cell = row.createCell(7);
            cell.setCellValue("AMT_DED1");
            cell = row.createCell(8);
            cell.setCellValue("AMOUNT");
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

            List result = em.createNativeQuery("select employeeNo,a.custid,c.acno,concat(ifnull(b.custname,''),' ',ifnull(b.middle_name,''),' ',ifnull(b.last_name,'')) as custname,cast(sum(cramt-dramt) as decimal(25,2)) debitBal,e.acctnature,s.buss_desig  "
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
                    + "group by c.acno having(creditBal> 0) order by 2,3").getResultList();

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    String employeeNo = vtr.get(0).toString();
                    String custId = vtr.get(1).toString();
                    String acNo = vtr.get(2).toString();
                    String custName = vtr.get(3).toString();
                    balance = Double.parseDouble(vtr.get(4).toString());
                    String actNature = vtr.get(5).toString();
                    String secNio = vtr.get(6).toString();
                    if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        //installmentAmt = demandRemote.getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                        String dmdValue[] = demandRemote.getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                        installmentAmt = Double.parseDouble(dmdValue[0]);
                        dmdDueDt = dmdValue[1];
//                        String fromDt = intCalFrDtByAcodeMap.get(acNo.substring(2, 4));
//                        String toDt = intCalToDtByAcodeMap.get(acNo.substring(2, 4));
//                        LoanIntCalcList it = loanIntCalcRemote.accWiseLoanIntCalc(fromDt, toDt, acNo, brncode);
//                        intAmt = it.getTotalInt() * -1;
                        intAmt = Double.parseDouble(dmdValue[2]);
                        emiAmt = installmentAmt + intAmt;
                        //Get Demand flow Id
//                        List flowIdList = getDemandFlowId(acNo);
//                        Vector dmdVector = (Vector) flowIdList.get(0);
//                        prinFlowId = dmdVector.get(0).toString();
//                        intFlowId = dmdVector.get(1).toString();
                        String dmdId = dmdIdByAcodeMap.get(acNo.substring(2, 4));
                        String id[] = dmdId.split(":");
                        prinFlowId = id[0];
                        intFlowId = id[1];
                    } else {
                        // installmentAmt = demandRemote.getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                        String dmdValue[] = demandRemote.getInstallmentAmt(acNo, ymd.format(dmy.parse(generationDt)), balance, actNature);
                        installmentAmt = Double.parseDouble(dmdValue[0]);
                        dmdDueDt = dmdValue[1];
                    }

                    if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        if (dmdDueDt.equalsIgnoreCase("")) {
                            dmdDueDt = ymd.format(dmy.parse(generationDt));
                        } else {
                            if (ymd.parse(dmdDueDt).compareTo(dmy.parse(generationDt)) >= 0) {
                                dmdDueDt = ymd.format(dmy.parse(dmdDueDt.substring(6, 8).concat(generationDt.substring(2, 10))));
                            }
                        }
                        if (!createDemandHistory(acNo, prinFlowId, intFlowId, dmdDueDt, "M", new BigDecimal(installmentAmt), new BigDecimal(intAmt), enterBy, placeOfWorking + "_" + range, ymd.format(dmy.parse(generationDt)), dmdSrlNo, schNo)) {
                            throw new ApplicationException("Problem In EMI Demand Generation.");
                        }
                    } else {
                        if (dmdDueDt.equalsIgnoreCase("")) {
                            dmdDueDt = ymd.format(dmy.parse(generationDt));
                        } else {
                            if (ymd.parse(dmdDueDt).compareTo(dmy.parse(generationDt)) >= 0) {
                                dmdDueDt = ymd.format(dmy.parse(dmdDueDt.substring(6, 8).concat(generationDt.substring(2, 10))));
                            }
                        }
                        if (!createDemandHistory(acNo, "RDDEM", "", dmdDueDt, "M", new BigDecimal(installmentAmt), new BigDecimal(intAmt), enterBy, placeOfWorking + "_" + range, ymd.format(dmy.parse(generationDt)), dmdSrlNo, schNo)) {
                            throw new ApplicationException("Problem In EMI Demand Generation.");
                        }
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
                    cell.setCellValue(employeeNo);
                    cell = row.createCell(4);
                    cell.setCellValue(secNio);
                    cell = row.createCell(5);
                    cell.setCellValue(acNo);
                    cell = row.createCell(6);
                    cell.setCellValue("36");
                    cell = row.createCell(7);
                    if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        //cell.setCellValue(String.valueOf(emiAmt));
                        cell.setCellValue(emiAmt);
                    } else {
                        //cell.setCellValue(String.valueOf(installmentAmt));
                        cell.setCellValue(installmentAmt);
                    }
                    cell = row.createCell(8);
                    cell.setCellValue("0.0");
                    String absFilePath = writeFolderPath + placeOfWorking + "_" + range + ".xls";
                    FileOutputStream out = new FileOutputStream(new File(absFilePath));
                    wb.write(out);
                    out.close();
                    fileName = absFilePath.substring(absFilePath.lastIndexOf("/") + 1);
                }
            }
            //New code Insert in table cbs_loan_dmd_info
            int srlInsert = em.createNativeQuery("INSERT INTO cbs_loan_dmd_info (SNO, ACNO, FLAG, BRNCODE, POSTINGDT, FROMDT, TODT,OFFICEID) "
                    + "VALUES "
                    + "(" + dmdSrlNo + ", '', 'A', '" + brncode + "', curdate(), '" + firstDt + "', '" + ymd.format(dmy.parse(generationDt)) + "','" + placeOfWorking + "_" + range + "')").executeUpdate();
            if (srlInsert <= 0) {
                throw new ApplicationException("Problem In Insertion.");
            }
            ut.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public String processBulkRecoveryOef(List<BulkRecoveryPojo> dataList, String orgnBrCode, String useName, String todayDate, String place) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String result = "";
        Integer payBy = 0;
        boolean remoteChq = false;
        List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
        try {
            ut.begin();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to process recovery.");
            }
            String[] arr = checkExcelBatch(dataList);
            if (arr[0].equals("false")) {
                throw new ApplicationException("In batch dr and cr is not equal in excel file.");
            }
            todayDate = ymd.format(dmy.parse(todayDate));
            String drAccount = arr[1].trim();
            String drAmount = arr[2].trim();
            String chqNo = arr[3].trim();
            String chqDt = arr[4].trim();
            //Chque Related Manipulation.
            if (chqNo == null || chqNo.equals("")) {
                payBy = 3;
                chqDt = "19000101";
            } else {
                if (chqDt == null || chqDt.equals("")) {
                    throw new ApplicationException("Please fill cheque date for dr amount in DD/MM/YYYY format in excel file.");
                }
                payBy = 1;
                chqDt = ymd.format(dmy.parse(chqDt));
            }
            String srNo = "", flag = "", toDt = "", valueDt = "", prinFlowId = "";
            int gracePdDays = 0;
            // new Code
            List dmdInfoList = getFileGenDt(place);
            if (!dmdInfoList.isEmpty()) {
                Vector dmdVector = (Vector) dmdInfoList.get(0);
                srNo = dmdVector.get(0).toString();
                flag = dmdVector.get(1).toString();
                toDt = dmdVector.get(2).toString();
            }
            if (Double.parseDouble(drAmount) != 0 && !drAccount.equals("")) {              //Normal Transfer Case
                //Dr A/c validation
                result = validateDrAccount(drAccount);
                if (!result.equals("true")) {
                    throw new ApplicationException(result);
                }
                //Making flag to true if it is inter branch batch. Otherwise it will be false always.
                if (!drAccount.substring(0, 2).equalsIgnoreCase(orgnBrCode)) {
                    remoteChq = true;
                }
                //Generating common batch no.
                Float trsno = ftsPosting.getTrsNo();
                //Dr Account
                if (remoteChq == true) {
                    //Calling of SX
                    result = interBranchFacade.cbsPostingSx(drAccount, 1, ymd.format(new Date()), Double.parseDouble(drAmount), 0.0, 2, "Bulk Recovery Dr:", 0f, "", chqNo, chqDt, payBy, 0f, ftsPosting.getRecNo(), 67, drAccount.substring(0, 2), orgnBrCode, useName, useName, trsno, "", "");
                } else {
//                    valueDt = getValueDt(srNo, toDt);
                    result = performGeneralTransaction(drAccount, 1, 2, chqNo, Double.parseDouble(drAmount), trsno, useName, orgnBrCode, "", "", chqDt, payBy, useName, ymd.format(new Date()));
                }
                if (!result.toLowerCase().contains("true")) {
                    throw new ApplicationException(result);
                }
                //For Cr Accounts.                
                for (BulkRecoveryPojo obj : dataList) {
                    if (!obj.getAcno().equalsIgnoreCase(drAccount)) {
                        result = validateCrAccount(obj.getAcno(), orgnBrCode);
                        if (!result.equals("true")) {
                            throw new ApplicationException(result);
                        }
                        //Get Demand flow Id
                        System.out.println(obj.getAcno());
//                        if (ftsPosting.getAccountNature(obj.getAcno()).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
//                            List flowIdList = getDemandFlowId(obj.getAcno());
//                            Vector vtr = (Vector) flowIdList.get(0);
//                            prinFlowId = vtr.get(0).toString();
//                        }
                        //Different coming amounts-emi transactions
                        if (ftsPosting.getAcNatureByCode(obj.getAcno().substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            if (obj.getLoanEmi() != null && Double.parseDouble(obj.getLoanEmi()) != 0) {
                                if (remoteChq == true) {
                                    //Calling of CX for Loan A/c
                                    result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), 0.0, 2, "", 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                                } else {
                                    String paidSno = "";
                                    result = ftsPosting.loanDisbursementInstallment(obj.getAcno(), Double.parseDouble(obj.getLoanEmi()), 0, useName, ymd.format(new Date()), trsno, 1, "Demand Recovery");
                                    if (!result.toLowerCase().contains("true")) {
                                        throw new ApplicationException(result);
                                    }
                                    System.out.println("PLOAN Result is->" + result);
                                    paidSno = result.substring(result.indexOf(":") + 1);
                                    valueDt = getValueDt(obj.getAcno(), toDt);
                                    result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getLoanEmi()), trsno, useName, orgnBrCode, paidSno, "", "19000101", 3, useName, valueDt);
                                }
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }
                            }
                        } else {
                            if (obj.getLoanEmi() != null && Double.parseDouble(obj.getLoanEmi()) != 0) {
                                String paidSno = "";
                                //P-RD
                                result = satisfyRecovery("RDDEM", obj.getAcno(), Double.parseDouble(obj.getLoanEmi()), useName, "");
                                if (!result.contains("true")) {
                                    throw new ApplicationException(result);
                                }
                                System.out.println("PINSP Result is->" + result);
                                paidSno = result.substring(result.indexOf(":") + 1);
                                if (remoteChq == true) {
                                    //Calling of CX for BranchPremiumHead A/c
                                    result = interBranchFacade.cbsPostingCx(obj.getAcno(), 0, ymd.format(new Date()), Double.parseDouble(obj.getLoanEmi()), 0.0, 2, paidSno + "::" + obj.getAcno(), 0f, "", "", "19000101", 3, 0f, ftsPosting.getRecNo(), 67, orgnBrCode, orgnBrCode, useName, useName, trsno, "", "");
                                } else {
                                    valueDt = ymd.format(new Date());
                                    result = performGeneralTransaction(obj.getAcno(), 0, 2, "", Double.parseDouble(obj.getLoanEmi()), trsno, useName, orgnBrCode, paidSno, obj.getAcno(), "19000101", 3, useName, valueDt);
                                }
                                if (!result.toLowerCase().contains("true")) {
                                    throw new ApplicationException(result);
                                }

                                List getDmdList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_srl_num,dmd_eff_date,dmd_ovdu_date,dmd_amt,last_adj_date,ifnull(tot_adj_amt,0) as tot_adj_amt, ifnull(ei_amt,0) as ei_amt from cbs_loan_dmd_table  where acno = '" + obj.getAcno() + "' and dmd_flow_id = 'RDDEM' and DMD_SRL_NUM =" + srNo + "  order by acno, dmd_eff_date, shdl_num, dmd_srl_num").getResultList();
                                if (getDmdList.size() > 0) {
                                    for (int i = 0; i < getDmdList.size(); i++) {
                                        Vector getDmdVect = (Vector) getDmdList.get(i);
                                        String dmdAcNo = getDmdVect.get(0).toString();
                                        int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                        String dmdFlowId = getDmdVect.get(2).toString();
                                        int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                                        Date dmdEffDt = yMMd.parse(getDmdVect.get(4).toString());
                                        double dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());

                                        double dmdAdjAmt = Double.parseDouble(getDmdVect.get(8).toString());
                                        List tsCntList = em.createNativeQuery("select ifnull(ts_cnt,0)+1 from cbs_loan_dmd_table where acno = '" + obj.getAcno() + "' and shdl_num = " + dmdSchNo + " and dmd_flow_id = 'RDDEM' and del_flg = 'N' and dmd_srl_num = " + dmdSrNo).getResultList();
                                        Vector tsCntVector = (Vector) tsCntList.get(0);
                                        int tsCnt = Integer.parseInt(tsCntVector.get(0).toString());

                                        List srNoList = em.createNativeQuery("select ifnull(max(cast(srl_num  as unsigned)),0)+1, ifnull(max(cast(part_tran_srl_num as unsigned)),0)+1 from cbs_loan_dmd_adj_table").getResultList();
                                        Vector srNoVector = (Vector) srNoList.get(0);
                                        int srlNo = Integer.parseInt(srNoVector.get(0).toString());
                                        int partTrSrNo = Integer.parseInt(srNoVector.get(1).toString());

                                        int updateDmdTable = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET  LAST_ADJ_DATE = curdate(), TOT_ADJ_AMT = " + Double.parseDouble(obj.getLoanEmi()) + ", RCRE_USER_ID = '" + useName + "' WHERE ACNO = '" + obj.getAcno() + "' and DMD_SRL_NUM =" + srNo + " ").executeUpdate();
                                        if (updateDmdTable <= 0) {
                                            throw new ApplicationException("Problem in updation");
                                        }
                                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                + " values('" + obj.getAcno() + "'," + dmdSchNo + ",'RDDEM','" + ymd.format(dmdEffDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + todayDate + "'," + dmdAmt + ", '" + useName + "',CURRENT_TIMESTAMP, '',CURRENT_TIMESTAMP, " + trsno + ",'" + partTrSrNo + "',0)");
                                        Integer insertQry = insertQuery.executeUpdate();
                                        if (insertQry <= 0) {
                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //Creating SMS List.
//                    if (Double.parseDouble(obj.getAmount()) != 0) {
//                        SmsToBatchTo to = new SmsToBatchTo();
//                        to.setAcNo(obj.getAcno());
//                        to.setCrAmt(Double.parseDouble(obj.getAmount()));
//                        to.setDrAmt(0.0);
//                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);
//                        to.setTranType(2);
//                        to.setTxnDt(dmy.format(ymd.parse(todayDate)));
//                        to.setTy(0);
//                        smsList.add(to);
//                    }
                }
            }
            int updateDmdInfo = em.createNativeQuery("update cbs_loan_dmd_info set FLAG = 'R',RECOVERYDT = curdate() where SNO = " + srNo).executeUpdate();
            if (updateDmdInfo <= 0) {
                throw new ApplicationException("Problem in updation");
            }
            ut.commit();
            try {
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception ex) {
                System.out.println("Problem in sending the sms in loan demand recovery.");
            }
            result = "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
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
}