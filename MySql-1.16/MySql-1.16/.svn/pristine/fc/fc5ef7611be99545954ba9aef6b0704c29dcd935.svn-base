/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.td;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.AcNoByComparator;
import com.cbs.dto.AcTypeProvPojo;
import com.cbs.dto.TdIntDetail;
import com.cbs.dto.TdsDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.intcal.TdInterestCalFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.pojo.GridData;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "TermDepositeCalculationManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TermDepositeCalculationManagementFacade implements TermDepositeCalculationManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPostMgmtRepote;
    @EJB
    TdReceiptManagementFacadeRemote orgFdInt;
    @EJB
    TdInterestCalFacadeRemote tdIntCal;
    @EJB
    private RDIntCalFacadeRemote rdIntCalFacade;
    @EJB
    AutoTermDepositRenewalRemote autoRenewRemote;
    @EJB
    DDSManagementFacadeRemote ddsFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List getTableDetail(String acno, String brCode) throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("select voucherno,receiptno,seqno,prinamt,date_format(TD_MadeDT, '%d/%m/%Y') AS TD_MadeDT,"
                    + "date_format(fddt,'%d/%m/%Y') AS fddt,date_format(matDt,'%d/%m/%Y') AS matDt,IntOpt,roi,status From td_vouchmst "
                    + "where acno='" + acno + "' and voucherno not in (select voucherno from td_payment_auth where acno ='" + acno + "' and auth='N') "
                    + "and voucherno not in (select rtNoHide from td_renewal_auth where acno ='" + acno + "' and auth='N') "
                    + "order by status, voucherno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return checkList;
    }

    public String cbsTdPaymentAccInfo(String acno, String brCode) throws ApplicationException {
        try {
            List statusList = em.createNativeQuery("Select ifnull(am.CustName,''),ifnull(am.Jtname1,''),ifnull(am.jtname2,''),ifnull(cb.Description,''),ifnull(am.cust_type,''),ifnull(am.Nomination,''),ifnull(am.opermode,'') from td_accountmaster am,codebook cb where acno='" + acno + "' and am.OperMode=cb.code and cb.groupcode = 4").getResultList();
            if (statusList.isEmpty()) {
                throw new ApplicationException("Account does not exist");
            }
            Vector statusL = (Vector) statusList.get(0);
            String custName = statusL.get(0).toString();
            String jtName1 = statusL.get(1).toString();
            String jtName2 = statusL.get(2).toString();
            String description = statusL.get(3).toString();
            String custType = statusL.get(4).toString();
            String nomination = statusL.get(5).toString();
            int operMode = Integer.parseInt(statusL.get(6).toString());
            String guarName = "";
            if (operMode == 11) {
                List guarNameList = em.createNativeQuery("SELECT ifnull(substring(grdname,1,20),'') from td_customermaster where  custno=substring('" + acno + "',5,6) and actype=substring('" + acno + "',3,2) and agcode=substring('" + acno + "',11,2) and brncode = (select curBrCode from td_accountmaster where acno='" + acno + "')").getResultList();
                if (guarNameList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in td customer master");
                }
                Vector guarNameLists = (Vector) guarNameList.get(0);
                guarName = guarNameLists.get(0).toString();
            }
            return custName + ": " + jtName1 + ": " + jtName2 + ": " + description + ": " + custType + ": " + nomination + ": " + operMode + ": " + guarName + ": " + "hi";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String fieldDisplay(String acno, float receiptNo, double lblPAmt, double tdGlobalInttPaid,
            String tmpCustCat, String brCode, String autoPayFlag, String centralDayBeginDt) throws ApplicationException {
        try {
            String tmpFlag = "TRUE";
            List acList = em.createNativeQuery("select acno,FDDt,MatDt,ROI,PrinAmt,IntOpt,ifnull(Lien,''),period,status,offlag,ofacno,"
                    + "COALESCE(cumuprinamt,'0') as cumuprinamt,nextintpaydt From td_vouchmst Where acno='" + acno + "'  and voucherno = "
                    + receiptNo + " ").getResultList();
            if (acList.isEmpty()) {
                throw new ApplicationException("This Receipt No. Does Not Exist");
            }
            Vector statusL = (Vector) acList.get(0);
            String fdDate = statusL.get(1).toString();
            String fdDates = fdDate.substring(0, 4) + fdDate.substring(5, 7) + fdDate.substring(8, 10);
            String correctFdDt = fdDate.substring(8, 10) + "/" + fdDate.substring(5, 7) + "/" + fdDate.substring(0, 4);

            String matDates = statusL.get(2).toString();
            String matDate = matDates.substring(0, 4) + matDates.substring(5, 7) + matDates.substring(8, 10);
            String correctMatDt = matDates.substring(8, 10) + "/" + matDates.substring(5, 7) + "/" + matDates.substring(0, 4);
            float roi = Float.parseFloat(statusL.get(3).toString());
            double prinamt = Double.parseDouble(statusL.get(4).toString());
            String intOpt = statusL.get(5).toString();
            String Lien = statusL.get(6).toString();
            String status = statusL.get(8).toString();
            String offlag = statusL.get(9).toString();
            if ((status.equals("C")) && (offlag.equals("N"))) {
                throw new ApplicationException("This Receipt is closed and Amount is Paid");
            }
            if ((status.equals("C")) && (offlag.equals("Y"))) {
                throw new ApplicationException("The Receipt has been marked closed and amount is transferred to");
            }
            if (Lien.equals("Y")) {
                throw new ApplicationException("Lien Marked against Deposit . To close the Deposit remove Lien Marking against Deposit");
            }
            List acnoList = em.createNativeQuery("Select TDSFlag From td_accountmaster Where Acno='" + acno + "'").getResultList();
            if (!acnoList.isEmpty()) {
                Vector acnoLists = (Vector) acnoList.get(0);
                String TDSFlag = acnoLists.get(0).toString();
                if (TDSFlag.equals("N")) {
                    TDSFlag = "False";
                } else {
                    TDSFlag = "True";
                }
            }
            if (intOpt.equals("C")) {
                intOpt = "Cumulative";
            } else if (intOpt.equals("S")) {
                intOpt = "Simple";
            } else if (intOpt.equals("Q")) {
                intOpt = "Quarterly";
            } else if (intOpt.equals("M")) {
                intOpt = "Monthly";
            } else if (intOpt.equals("Y")) {
                intOpt = "Yearly";
            }

            String tempbd = "";
            if (autoPayFlag.equalsIgnoreCase("Y")) {
                tempbd = centralDayBeginDt;
            } else {
                List currentDtList = em.createNativeQuery("SELECT date_format(date,'%Y%m%d') FROM bankdays WHERE DAYENDFLAG = 'N' and Brncode ='" + brCode + "'").getResultList();
                Vector currentDtLists = (Vector) currentDtList.get(0);
                tempbd = currentDtLists.get(0).toString();
            }

            long dateDiff = CbsUtil.dayDiff(ymd.parse(matDate), ymd.parse(tempbd));
            String MatPreFlag = "True";
            String LblStatus = "Mature Withdrawal";
            String mintStatus = "MATURE";
            long lblactivedays = 0;
            float lblPrevROI = 0;
            String acNat = ftsPostMgmtRepote.getAccountNature(acno);
            if (dateDiff < 0) {
                MatPreFlag = "False";
                LblStatus = "Pre-Mature Withdrawal";
                mintStatus = "PREMATURE";
                double tmpNum;
                lblactivedays = CbsUtil.dayDiff(ymd.parse(fdDates), ymd.parse(tempbd));
                if (intOpt.equals("Cumulative")) {
                    tmpNum = lblPAmt + tdGlobalInttPaid;
                } else {
                    tmpNum = lblPAmt;
                }
                lblPrevROI = orgFdInt.tdApplicableROI(acno, tmpCustCat, tmpNum, tempbd, fdDates, tempbd, acNat);
            }
            return correctFdDt + ": " + correctMatDt + ": " + roi + ": " + prinamt + ": " + intOpt + ": " + lblactivedays + ": " + tmpFlag + ": " + MatPreFlag + ": " + LblStatus + ": " + mintStatus + ": " + lblPrevROI;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String cbsTdPaymentRtno(String accountNo, float txtReceiptNo, float netROI, String fdDate, String matDate, String user, double prinAmt,
            String tmpCustCat, String brCode, String autoPayFlag, String centralDayBeginDt) throws ApplicationException {
        try {
            long fullDt = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
            String Year = String.valueOf(fullDt / 365);
            fullDt = fullDt % 365;
            String Mon = String.valueOf(fullDt / 30);
            String Day = String.valueOf(fullDt % 30);

            String fieldD = fieldDisplay(accountNo, txtReceiptNo, prinAmt, 0, tmpCustCat, brCode, autoPayFlag, centralDayBeginDt);
            String[] values = fieldD.split(": ");
            String intOpt = values[4];
            int activeDays = Integer.parseInt(values[5]);
            if (activeDays <= 0) {
                activeDays = 0;
            }
            String matPreFlag = values[7];
            String status = values[8];
            //String mintStatus = values[9];
            float prevRoi = Float.parseFloat(values[10]);

            List<Double> resultList = autoRenewRemote.tdGlobal(accountNo, txtReceiptNo, matPreFlag, netROI, 0, Year, Mon, Day,"TDPAYMENT");
            double intPaid = Math.round(resultList.get(4));
            double totalInt = Math.round(resultList.get(3));

            double balInt = Math.round(resultList.get(3) - resultList.get(4));
            double tdsDeductedLastYear = Math.round(resultList.get(1));

            double tdsDeductedFinYear = Math.round(resultList.get(0));
            double tdsToBeDeducted = Math.round(resultList.get(2));

            double closeAcctTdsToBeDeducted = Math.round(resultList.get(5));
            double closeAcctTdsDeducted = Math.round(resultList.get(6));
            double closeAcctIntFinYear = Math.round(resultList.get(7));

            double netAmt = 0;
            if (intOpt.equals("Quarterly") || intOpt.equals("Monthly") || intOpt.equals("Yearly")) {
                netAmt = prinAmt + balInt - tdsToBeDeducted - closeAcctTdsToBeDeducted;
            } else {
                netAmt = prinAmt + intPaid + balInt - tdsToBeDeducted - tdsDeductedFinYear - tdsDeductedLastYear - closeAcctTdsToBeDeducted;
            }
            return intPaid + ": " + totalInt + ": " + balInt + ": " + tdsDeductedLastYear + ": " + tdsDeductedFinYear + ": " + tdsToBeDeducted
                    + ": " + netAmt + ": " + activeDays + ": " + prevRoi + ": " + status + ": " + matPreFlag + ": " + closeAcctTdsToBeDeducted
                    + ": " + closeAcctTdsDeducted + ": " + closeAcctIntFinYear;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String cbsPenalityApply(String accountNo, float txtReceiptNo, String fdDate, String matDate, String user, double lblPAmt,
            String applicableRate, String penalityApplicable, String intOpt, String brCode,
            String tmpCustCat, String autoPayFlag, String centralDayBeginDt) throws ApplicationException {
        try {
            if (Float.parseFloat(penalityApplicable) < 0) {
                throw new ApplicationException("SORRY! Please Fill Correct Penality");
            }
            if (Float.parseFloat(applicableRate) - Float.parseFloat(penalityApplicable) < 0) {
                throw new ApplicationException("SORRY! Penalty Can Not Be Greater Than Applicable ROI");
            }
            float netRoi = Float.parseFloat(applicableRate) - Float.parseFloat(penalityApplicable);

            long fullDt = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
            String Year = String.valueOf(fullDt / 365);
            fullDt = fullDt % 365;

            String Mon = String.valueOf(fullDt / 30);
            String Day = String.valueOf(fullDt % 30);

            String fieldD = fieldDisplay(accountNo, txtReceiptNo, lblPAmt, 0, tmpCustCat, brCode, autoPayFlag, centralDayBeginDt);
            String[] values = fieldD.split(":");
            intOpt = values[4].trim();

            int activeDays = Integer.parseInt(values[5].trim());
            if (activeDays <= 0) {
                activeDays = 0;
            }

            float prevRoi = Float.parseFloat(values[10]);

            List<Double> resultList = autoRenewRemote.tdGlobal(accountNo, txtReceiptNo, "False", netRoi, 0, Year, Mon, Day,"");

            double intPaid = Math.round(resultList.get(4));
            double totalInt = Math.round(resultList.get(3));

            double balInt = Math.round(resultList.get(3) - resultList.get(4));
            double tdsDeductedLastYear = Math.round(resultList.get(1));

            double tdsDeductedFinYear = Math.round(resultList.get(0));
            double tdsToBeDeducted = Math.round(resultList.get(2));

            double closeAcctTdsToBeDeducted = Math.round(resultList.get(5));
            double closeAcctTdsDeducted = Math.round(resultList.get(6));
            double closeAcctIntFinYear = Math.round(resultList.get(7));

            double netAmt = 0;
            double netTds = tdsDeductedFinYear;

            if (intOpt.equals("Quarterly") || intOpt.equals("Monthly") || intOpt.equals("Cumulative")) {
                String prApp = orgFdInt.getProvApplyFlag(accountNo);
                if (prApp.equals("")) {
                    netTds = 0;
                }
            } else {
                List simple = em.createNativeQuery("Select ifnull(SimplePostFlag,0) From td_parameterinfo").getResultList();
                if (simple.isEmpty()) {
                    throw new ApplicationException("Data does not exist in td_parameterinfo");
                }
                Vector simples = (Vector) simple.get(0);
                int postFlag = Integer.parseInt(simples.get(0).toString());
                if (postFlag == 1) {
                    netTds = 0;
                }
            }

            if (intOpt.equals("Quarterly") || intOpt.equals("Monthly") || intOpt.equals("Yearly")) {
                netAmt = lblPAmt + balInt - tdsToBeDeducted - closeAcctTdsToBeDeducted;
            } else {
                netAmt = lblPAmt + intPaid + balInt - tdsToBeDeducted - tdsDeductedFinYear - tdsDeductedLastYear - closeAcctTdsToBeDeducted;
            }
            netAmt = Math.round(netAmt);

            double balance = 0;
            List balanceList = em.createNativeQuery("select COALESCE(Balance,'0') as Balance from td_reconbalan where acno='" + accountNo + "' ").getResultList();
            if (!balanceList.isEmpty()) {
                Vector balanceLists = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceLists.get(0).toString());
            }
            return intPaid + ": " + totalInt + ": " + balInt + ": " + tdsDeductedLastYear + ": " + tdsDeductedFinYear + ": " + tdsToBeDeducted
                    + ": " + netAmt + ": " + balance + ": " + prevRoi + ": " + closeAcctTdsToBeDeducted + ": " + closeAcctTdsDeducted + ": "
                    + closeAcctIntFinYear;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String completeButton(String acno, float rtNumber, String intOption, String status, double prinAmt, double remainingInterest,
            double tdsToBeDeducted, double tdsDeducted, double interestPaid, String fdDate, String matDate, double netAmount, float penalty,
            float roi, String user, String brCode, double actualInterest, String auth, String authBy, double clActTdsToBeDeducted) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            synchronized (TermDepositeCalculationManagementFacade.class) {
                String query = "select authby from  td_payment_auth where auth='Y' and acno = '" + acno + "' and voucherNo = " + rtNumber;
                List list = em.createNativeQuery(query).getResultList();
                if (!list.isEmpty()) {
                    Vector vect = (Vector) list.get(0);
                    throw new ApplicationException("This receipt is already authorized by " + vect.get(0).toString());
                }
                result = autoRenewRemote.completeButton(acno, rtNumber, intOption, status, prinAmt, remainingInterest, tdsToBeDeducted, tdsDeducted,
                        interestPaid, fdDate, matDate, netAmount, penalty, roi, user, brCode, actualInterest, "Y", authBy, clActTdsToBeDeducted, "", "");
            }
            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }


    /*Start of TDS Calculation*/
    public String getFirstDate(int months, int years) {
//        if (months <= 3) {
//            years = years - 1;
//        }
        String mon = "";
        if (Integer.toString(months).length() < 2) {
            mon = "0" + months;
        } else {
            mon = Integer.toString(months);
        }
        String firstDate = "01/" + mon + "/" + years;
        return firstDate;
    }

    public String getLastdate(String firstDate) {
        String dd = firstDate.substring(0, 2);
        String mm = firstDate.substring(3, 5);
        String yy = firstDate.substring(6);
        switch (Integer.parseInt(mm)) {
            case 1:
                dd = String.valueOf(31);
                break;
            case 3:
                dd = String.valueOf(31);
                break;
            case 5:
                dd = String.valueOf(31);
                break;
            case 7:
                dd = String.valueOf(31);
                break;
            case 8:
                dd = String.valueOf(31);
                break;
            case 10:
                dd = String.valueOf(31);
                break;
            case 12:
                dd = String.valueOf(31);
                break;
            case 4:
                dd = String.valueOf(30);
                break;
            case 6:
                dd = String.valueOf(30);
                break;
            case 9:
                dd = String.valueOf(30);
                break;
            case 11:
                dd = String.valueOf(30);
                break;
            case 2:
                if (Integer.parseInt(yy) % 400 == 0) {
                    dd = String.valueOf(29);
                } else if (Integer.parseInt(yy) % 4 == 0) {
                    dd = String.valueOf(29);
                } else {
                    dd = String.valueOf(28);
                }
                break;
            default:
        }
        return dd + "/" + mm + "/" + yy;
    }

    public String getGlHead(String lastDate, String branchCode) {
        Query selectQuery = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab"
                + " where TDS_Applicabledate<='" + lastDate + "')");
        List l = selectQuery.getResultList();
        Vector v = (Vector) l.get(0);
        String s = branchCode + v.get(0).toString();
        return s;
    }

    public List<TdIntDetail> interestCalculation(String fromdt, String todt, String acctype,
            String brCode, String optPost) throws ApplicationException {
        List<TdIntDetail> tdIntDetailList = new ArrayList<TdIntDetail>();
        try {
            double interest = 0.0f;
            float totmon = 0.0f;
            //int totquarter = 0;
            String mtdt1 = "";

            Float totdays = 0.0f;
            String nextintpaydt1 = "";
            Float matDays = 0.0f;
            int lastQuarter = 0;

            String totdays1;
            double intAmt = 0.0f;
            Long quarterDays = 0l;
            long mnthdays = 0l;

            int mm = 0;
            int tmp = CbsUtil.datePart("M", todt);
            if (tmp > 2) {
                mm = tmp - 2;
            } else {
                mm = tmp;
            }
            String ddfrom = "";
            if (mm < 10) {
                ddfrom = String.valueOf(CbsUtil.datePart("Y", todt)) + "0" + String.valueOf(mm) + "01";
            } else {
                ddfrom = String.valueOf(CbsUtil.datePart("Y", todt)) + String.valueOf(mm) + "01";
            }
            Long dd = CbsUtil.dayDiff(ymd.parse(ddfrom), ymd.parse(todt));
            dd = dd + 1;
            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brCode)).getSingleResult();

            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            List cursorLt;
            if (optPost.equalsIgnoreCase("F")) {
                cursorLt = em.createNativeQuery("select tm.seqno,tm.acno, substring(am.custname,1,28), tm.intopt,"
                        + "tm.voucherno,tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),date_format(tm.matdt,'%Y%m%d'), "
                        + "tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoacno, "
                        + "tm.tdsdeducted, tm.status,ci.custid,date_format(tm.FDDT,'%Y%m%d') from td_vouchmst tm, td_accountmaster am ,"
                        + "customerid ci where am.acno=ci.acno and am.acno = tm.acno and am.tdsflag in ('Y','C') "
                        + "and tm.status<>'C' and substring(tm.acno,3,2)='" + acctype + "' and tm.nextintpaydt < tm.matdt and "
                        + "tm.roi<>0 and am.accstatus<>9 and am.CurBrCode='" + brCode + "' ").getResultList();
            } else {
                cursorLt = em.createNativeQuery("select tm.seqno,tm.acno, substring(am.custname,1,28), tm.intopt,"
                        + "tm.voucherno,tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),date_format(tm.matdt,'%Y%m%d'), "
                        + "tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoacno, "
                        + "tm.tdsdeducted, tm.status,ci.custid,date_format(tm.FDDT,'%Y%m%d') from td_vouchmst tm, td_accountmaster am ,"
                        + "customerid ci where am.acno=ci.acno and am.acno = tm.acno and am.tdsflag in ('Y','C') "
                        + "and tm.status<>'C' and substring(tm.acno,3,2)='" + acctype
                        + "' and tm.nextintpaydt < tm.matdt and tm.nextintpaydt<='" + todt + "' and "
                        + "tm.roi<>0 and am.accstatus<>9 and am.CurBrCode='" + brCode + "'").getResultList();
            }

            if (cursorLt.size() > 0) {
                for (int i = 0; i < cursorLt.size(); i++) {
                    Vector curV = (Vector) cursorLt.get(i);

                    Float seqno = Float.parseFloat(curV.get(0).toString());
                    String accno = curV.get(1).toString();
                    String custName = curV.get(2).toString();

                    String intOpt = curV.get(3).toString();
                    Float voucherno = Float.parseFloat(curV.get(4).toString());
                    Float roi = Float.parseFloat(curV.get(5).toString());

                    String nextintpaydt = curV.get(6).toString();
                    String matdt = curV.get(7).toString();
                    double pamt = Double.parseDouble(curV.get(8).toString());
                    double pamt1 = pamt;
                    int year = Integer.parseInt(curV.get(9).toString());
                    int months = Integer.parseInt(curV.get(10).toString());
                    //days = Integer.parseInt(curV.get(11).toString());

                    double cumuprinamt = Double.parseDouble(curV.get(12).toString());
                    String inttoacno = curV.get(13).toString();
                    String status = curV.get(15).toString();
                    String fdDt = (curV.get(17).toString());
                    if (intOpt.equalsIgnoreCase("M")) {
                        mnthdays = CbsUtil.dayDiff(ymd.parse(fromdt), ymd.parse(todt)) + 1;

                        long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                        int dtPart = CbsUtil.datePart("D", nextintpaydt);

                        if (dtPart != 1 && dateDiff > 0) {
                            String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                            nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);

                            /* calculate the difference between nextintpaydt and lastday of the month  */
                            Long totaldays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                            totmon = (totaldays.floatValue() / mnthdays) + CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                            totdays = totaldays.floatValue() + CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                        }

                        /*B-- IF FROM DT IS FIRST DATE OF THE MONTH  AND MATURITY PERIOD LIES AFTER TODATE  */
                        if (dtPart == 1 && dateDiff > 0) {
                            totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                            Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                            totdays = tmpTotDay.floatValue();
                        }
                        /*C--- IF FROM DT IS NOT THE FIRST DT OF THE MONTH AND MATURITY DATE LIES BEFoRE TO DATE */
                        if (dtPart != 1 && dateDiff < 0) {
                            String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                            nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);
                            mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";

                            /*calculate  the no of days between first of maturity month and maturity date */
                            Long tempMatDays = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                            matDays = tempMatDays.floatValue();

                            totdays = matDays + CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                            totmon = totdays / mnthdays + CbsUtil.monthDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, 1)), ymd.parse(matdt));

                            Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totdays = tmpTotDay.floatValue();
                        }
                        if (dtPart == 1 && dateDiff < 0) {
                            mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";
                            totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(mtdt1));

                            Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                            matDays = tmpTotDay.floatValue();
                            totdays = totdays + matDays;
                            totmon = matDays / mnthdays + totmon;

                            tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totdays = tmpTotDay.floatValue();
                        }
                        interest = 0.0f;
                        interest = (pamt - (pamt * (1 / (1 + roi / 1200)))) * totmon;
                    }
                    if (intOpt.equalsIgnoreCase("Q")) {
                        long dtDiff = CbsUtil.dayDiff(ymd.parse(ddfrom), ymd.parse(fdDt));
                        long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                        if (dateDiff > 0) {
                            Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                            totdays = tmpDays.floatValue();
                        } else if (dateDiff <= 0) {
                            Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totdays = tmpDays.floatValue();
                        }
                        if (dtDiff > 0) {
                            interest = 0.0f;
                            interest = (pamt * (roi / 36500)) * totdays;
                        } else {
                            interest = 0.0f;
                            Float totquarter = totdays / dd;
                            interest = (pamt * (roi / 400)) * totquarter;
                        }
                    }
                    if (intOpt.equalsIgnoreCase("S")) {
                        long dateDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                        int dtPart = CbsUtil.datePart("D", nextintpaydt);
                        if (dtPart != 1 && dateDiff >= 0) {
                            String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                            nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);

                            Long totaldays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;
                            totdays = totaldays.floatValue();
                            totmon = (totdays / 30) + CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));
                            totdays = totdays + CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(todt));

                            if (dateDiff == 0) {
                                totdays = totdays - 1;
                            }
                        }
                        if (dtPart == 1 && dateDiff >= 0) {
                            totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                            Long tmpDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) + 1;
                            totdays = tmpDay.floatValue();
                            if (dateDiff == 0) {
                                totdays = totdays - 1;
                            }
                        }
                        if (dtPart != 1 && dateDiff < 0) {
                            String tempDt = CbsUtil.monthAdd(nextintpaydt, 1);
                            nextintpaydt1 = CbsUtil.dateAdd(tempDt.substring(0, 4) + tempDt.substring(4, 6) + "01", -1);
                            mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";

                            Long tmpMatDays = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                            matDays = tmpMatDays.floatValue();
                            totdays = matDays + CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1)) + 1;

                            totmon = (totdays / 30) + CbsUtil.monthDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, 1)), ymd.parse(matdt));
                            Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totdays = tmpDays.floatValue();
                        }

                        if (dtPart == 1 && dateDiff < 0) {
                            mtdt1 = matdt.substring(0, 4) + matdt.substring(4, 6) + "01";
                            totmon = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(mtdt1));

                            Long tmpTotDay = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                            matDays = tmpTotDay.floatValue();
                            totdays = totdays + matDays;
                            totmon = (matDays / 30) + totmon;

                            tmpTotDay = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totdays = tmpTotDay.floatValue();
                        }
                        interest = 0.0f;
                        if (year == 0 && Math.floor(months) == 0) {
                            interest = (pamt * roi * totdays) / 36500;
                        }
                        if (year != 0 || months != 0) {
                            interest = (pamt * roi * totmon / 1200);
                        }
                    }
                    if (intOpt.equalsIgnoreCase("C")) {
                        pamt = cumuprinamt;

                        long dtPart = CbsUtil.datePart("D", nextintpaydt);
                        long mPart = CbsUtil.datePart("M", nextintpaydt);
                        long dtDiff = CbsUtil.dayDiff(ymd.parse(todt), ymd.parse(matdt));
                        int totquarter = 0;
                        if ((dtPart != 1 && dtDiff >= 0) || ((dtPart == 1) && (mPart != 4 && mPart != 7 && mPart != 10 && mPart != 1) && dtDiff >= 0)) {
                            interest = 0.0f;
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(todt)) / 3;
                            lastQuarter = totquarter * 3;

                            totdays1 = CbsUtil.monthAdd(CbsUtil.dateAdd(todt, 1), -(lastQuarter));
                            Long tmpD = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(totdays1));
                            totdays = tmpD.floatValue();

                            if (totquarter == 0) {
                                interest = pamt * (1 + roi / 400) / (1 + roi / 36500 * (91 - totdays)) - pamt;
                            }
                            if (totquarter != 0) {
                                quarterDays = CbsUtil.dayDiff(ymd.parse(CbsUtil.monthAdd(totdays1, -3)), ymd.parse(totdays1));
                                interest = (pamt * (1 + roi / 400) / (1 + roi / 36500 * (91 - totdays))) - pamt;
                                intAmt = interest;
                                pamt = pamt + interest;

                                Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                                interest = tmpInt.floatValue();
                                interest = interest + intAmt;
                            }
                        }
                        if (dtPart == 1 && (mPart == 1 || mPart == 4 || mPart == 7 || mPart == 10) && dtDiff >= 0) {
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(CbsUtil.monthAdd(todt, 1)));
                            totquarter = totquarter / 3;
                            Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                            interest = tmpInt.floatValue();
                        }
                        if (dtPart == 1 && (mPart == 1 || mPart == 4 || mPart == 7 || mPart == 10) && dtDiff < 0) {
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                            totquarter = totquarter / 3;
                            if (totquarter == 0) {
                                Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(matdt));
                                totdays = tmpDays.floatValue();
                                interest = pamt * roi * totdays / 36500;
                            } else {
                                Double tmpInt = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;
                                interest = tmpInt.floatValue();
                                intAmt = interest;
                                pamt = pamt + interest;
                                totquarter = totquarter * 3;
                                mtdt1 = CbsUtil.monthAdd(nextintpaydt, totquarter);

                                Long tmpDt = CbsUtil.dayDiff(ymd.parse(mtdt1), ymd.parse(matdt));
                                matDays = tmpDt.floatValue();
                                interest = pamt * roi * matDays / 36500;
                                interest = interest + intAmt;
                            }
                        }
                        if ((dtPart != 1 && dtDiff < 0) || ((dtPart == 1) && (mPart != 1 && mPart != 4 && mPart != 7 && mPart != 10) && dtDiff < 0)) {
                            nextintpaydt1 = nextintpaydt;
                            long monPart = CbsUtil.datePart("M", nextintpaydt);
                            if (monPart >= 1 && monPart <= 3) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "0401";
                            } else if (monPart >= 4 && monPart <= 6) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "0701";
                            } else if (monPart >= 7 && monPart <= 9) {
                                nextintpaydt1 = CbsUtil.datePart("Y", nextintpaydt) + "1001";
                            } else if (monPart >= 10 && monPart <= 12) {
                                nextintpaydt1 = CbsUtil.datePart("Y", CbsUtil.yearAdd(nextintpaydt, 1)) + "0101";
                            }
                            Long tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt), ymd.parse(nextintpaydt1));
                            totdays = tmpDays.floatValue();
                            quarterDays = CbsUtil.dayDiff(ymd.parse(CbsUtil.monthAdd(nextintpaydt1, -3)), ymd.parse(nextintpaydt1));
                            totquarter = CbsUtil.monthDiff(ymd.parse(nextintpaydt1), ymd.parse(matdt)) / 3;

                            lastQuarter = totquarter * 3;
                            nextintpaydt1 = CbsUtil.monthAdd(nextintpaydt1, lastQuarter);

                            tmpDays = CbsUtil.dayDiff(ymd.parse(nextintpaydt1), ymd.parse(matdt));
                            matDays = tmpDays.floatValue();
                            if ((totquarter == 0) || ((quarterDays - totdays - matDays) > 0)) {
                                interest = pamt * (1 + roi * (totdays + matDays) / 36500) / (1 + roi * matDays / 36500) - pamt;
                            } else {
                                interest = (pamt * (1 + roi / 400) / (1 + roi / 36500 * (quarterDays - totdays))) - pamt;
                            }
                            intAmt = interest;
                            pamt = pamt + interest;
                            interest = (Math.pow(1 + roi / 400, totquarter) - 1) * pamt;

                            intAmt = intAmt + interest;
                            pamt = pamt + interest;
                            interest = pamt * roi * matDays / 36500;
                            interest = intAmt + interest;
                        }
                    }
                    if (interest > 0) {
                        TdIntDetail tdIntDetail = new TdIntDetail();
                        tdIntDetail.setMsg("TRUE");
                        tdIntDetail.setBnkName(bnkName);
                        tdIntDetail.setBnkAdd(bnkAddress);
                        tdIntDetail.setCustId(curV.get(16).toString());
                        tdIntDetail.setAcno(accno);
                        tdIntDetail.setCustName(custName);
                        tdIntDetail.setVoucherNo(Math.round(voucherno));

                        tdIntDetail.setpAmt(pamt1);
                        tdIntDetail.setRoi(roi);
                        tdIntDetail.setFromDt(nextintpaydt);

                        tdIntDetail.setMatDt(matdt);

                        //if(status.equalsIgnoreCase("C")) tdIntDetail.setInterest(0);
                        //else tdIntDetail.setInterest(CbsUtil.round(interest, 0));
                        tdIntDetail.setInterest(CbsUtil.round(interest, 0));
                        tdIntDetail.setIntToAcno(inttoacno);

                        tdIntDetail.setToDt(todt);
                        tdIntDetail.setSeqno(seqno);
                        tdIntDetail.setIntOpt(intOpt);
                        tdIntDetail.setStatus(curV.get(15).toString());

                        tdIntDetailList.add(tdIntDetail);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tdIntDetailList;
    }

    public Double minorAccountIntForGuardian(String custId, String firstdate, String lastdate) throws ApplicationException {
        double minorInterest = 0d;
        try {
            List getCustIdWiseInterstOfMinorList = em.createNativeQuery("select aa.custid, ifnull(sum(interest),0) from "
                    + " (select b.acno, b.voucherno, sum(ifnull(b.interest,0)) as interest, b.intopt, substring(am.custName,1,28) as custName, "
                    + " c.status,ci.custid,'' as from_dt,'' as ToDt from td_interesthistory b, td_accountmaster am, td_vouchmst c, customerid ci where am.acno = ci.acno and "
                    + " b.acno = am.acno and b.acno = c.acno and b.voucherno = c.voucherno and b.fromdt "
                    + " between '" + firstdate + "' and '" + lastdate + "' and am.accstatus<>9 and (c.status <>'C' or c.ClDt between '" + firstdate + "' and '" + lastdate + "') "
                    + " and am.tdsflag in ('Y','C') "
                    + " group by b.voucherno, b.acno,b.intopt,am.custname, c.status,ci.custid "
                    + " union all "
                    + " select b.acno, b.vch_no as voucherno, sum(ifnull(b.interest,0))  as interest, ifnull(b.int_opt,'Q') as intopt, "
                    + " substring(am.custName,1,28)  as custName,'' as status,"
                    + " ci.custid, b.from_dt, b.ToDt from rd_interesthistory b, accountmaster am, customerid ci "
                    + " where am.acno = ci.acno and b.acno = am.acno and b.from_dt between '" + firstdate + "' and '" + lastdate + "' "
                    + " and (am.closingDate is null or am.closingDate = '' or am.closingDate >'" + lastdate + "') and "
                    + " am.tdsflag in ('Y','C') group by ci.custid, b.acno,b.int_opt,am.custname "
                    + " union all "
                    + " select b.acno, '0' as voucherno, sum(ifnull(b.interest,0)) as interest, 'Q' as intopt, substring(am.custName,1,28) as custName, '' as status,"
                    + " ci.custid, b.fromdate, b.ToDt from dds_interesthistory b, accountmaster am, customerid ci "
                    + " where am.acno = ci.acno and b.acno = am.acno and b.fromdate between '" + firstdate + "' and '" + lastdate + "'  "
                    + " and (am.closingDate is null or am.closingDate = '' or am.closingDate >'" + lastdate + "') and "
                    + " am.tdsflag in ('Y','C') group by ci.custid, b.acno,am.custname )aa where aa.custid in "
                    + " (select CustomerId from cbs_cust_minorinfo where guardiancode = '" + custId + "') "
                    + " group by aa.custid order by aa.custid ").getResultList();
            if (!getCustIdWiseInterstOfMinorList.isEmpty()) {
                Vector custIdIntVect = (Vector) getCustIdWiseInterstOfMinorList.get(0);
                minorInterest = Double.parseDouble(custIdIntVect.get(0).toString());

            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return minorInterest;
    }

    public List<TdsDetail> tdsCalculationBeforeIntCalc(String firstdate, String lastdate, String brnCode)
            throws ApplicationException {
        try {
            System.out.println("Start time is =" + new Date());
            String finYr = autoRenewRemote.getFinYear(brnCode);
            String frmDT = finYr + "0401";
            List<TdIntDetail> intCalcDetailList = new ArrayList<TdIntDetail>();
            List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and fromdt = '" + firstdate + "' and todt = '" + lastdate + "' and brncode = '" + brnCode + "' and actype in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "')").getResultList();
            if (!tdsPostList.isEmpty()) {
                throw new ApplicationException("TDS calculation is already posted for this duration.");
            }

            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brnCode)).getSingleResult();
            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            List selectQuer65 = em.createNativeQuery("SELECT ifnull(TDS_AMOUNT,-1),ifnull(TDS_RATE *(1 + TDS_SURCHARGE /100.0),-1),tdsRate_pan FROM tdsslab "
                    + "WHERE TYPE=1 AND TDS_APPLICABLEDATE IN(SELECT MAX(TDS_APPLICABLEDATE) FROM tdsslab WHERE TDS_APPLICABLEDATE<='" + lastdate
                    + "' AND TYPE=1)").getResultList();
            Vector v65 = (Vector) selectQuer65.get(0);

            float tdsApplicableAmt = Float.parseFloat(v65.get(0).toString());

            if (tdsApplicableAmt == -1) {
                return getErrorMsgList("Tds applicable amount does not exist in tds slab. So please fill tds slab.");
            }

            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') and productcode is not null");
            List resultList = selectQuery.getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                Vector v = (Vector) resultList.get(i);
                List<TdIntDetail> subList = interestCalculation(firstdate, lastdate, v.get(0).toString(), brnCode, "Q");
                intCalcDetailList.addAll(subList);
            }

            Query selectRdQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "') and productcode is not null");
            List resultRdList = selectRdQuery.getResultList();
            for (int i = 0; i < resultRdList.size(); i++) {
                Vector v = (Vector) resultRdList.get(i);
                List<TdIntDetail> rdIntListsubList = rdIntCalFacade.intCalcForTds(v.get(0).toString(), firstdate, lastdate, brnCode);
                intCalcDetailList.addAll(rdIntListsubList);
            }

            List resultDsList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.DEPOSIT_SC + "') and productcode is not null").getResultList();

            for (int i = 0; i < resultDsList.size(); i++) {
                Vector v = (Vector) resultDsList.get(i);
                List<TdIntDetail> dsIntSbuList = ddsFacade.intCalcForTds(firstdate, lastdate, v.get(0).toString(), brnCode);
                intCalcDetailList.addAll(dsIntSbuList);
            }

            List vectList = em.createNativeQuery("select b.acno, b.voucherno, sum(ifnull(b.interest,0)), b.intopt, substring(am.custName,1,28), "
                    + "c.status,ci.custid from td_interesthistory b, td_accountmaster am, td_vouchmst c, customerid ci where am.acno = ci.acno and "
                    + "b.acno = am.acno and b.acno = c.acno and b.voucherno = c.voucherno and am.curbrcode='" + brnCode + "' and b.fromdt between '"
                    + frmDT + "' and  '" + lastdate + "' and am.accstatus<>9 and (c.status <>'C' or c.ClDt between '" + frmDT + "' and '"
                    + lastdate + "') and am.tdsflag in ('Y','C') "
                    + " group by b.voucherno, b.acno,b.intopt,am.custname, c.status,ci.custid").getResultList();

            // if (vectList.size() > 0) {
            for (int i = 0; i < vectList.size(); i++) {
                Vector curV = (Vector) vectList.get(i);
                int index = getIndex(intCalcDetailList, curV.get(0).toString(), Float.parseFloat(curV.get(1).toString()));
                if (index > -1) {
                    TdIntDetail tempObj = intCalcDetailList.get(index);
                    intCalcDetailList.remove(index);
                    tempObj.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    intCalcDetailList.add(tempObj);

                } else {
                    TdIntDetail tdIntDetail = new TdIntDetail();
                    tdIntDetail.setMsg("TRUE");
                    tdIntDetail.setAcno(curV.get(0).toString());

                    tdIntDetail.setCustName(curV.get(4).toString());
                    tdIntDetail.setIntOpt(curV.get(3).toString());
                    tdIntDetail.setStatus(curV.get(5).toString());

                    tdIntDetail.setVoucherNo(Math.round(Float.parseFloat(curV.get(1).toString())));
                    tdIntDetail.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    tdIntDetail.setCustId(curV.get(6).toString());
                    intCalcDetailList.add(tdIntDetail);
                }
            }
            //  }
            vectList = em.createNativeQuery("select b.acno, b.vch_no, sum(ifnull(b.interest,0)), ifnull(b.int_opt,'Q'), substring(am.custName,1,28), "
                    + " ci.custid, b.from_dt, b.ToDt from rd_interesthistory b, accountmaster am, customerid ci "
                    + "where am.acno = ci.acno and b.acno = am.acno and am.curbrcode='" + brnCode + "' and b.from_dt between '" + frmDT + "' and  "
                    + "'" + lastdate + "' and (am.closingDate is null or am.closingDate = '' or am.closingDate >'" + lastdate + "') and "
                    + "am.tdsflag in ('Y','C') group by ci.custid, b.acno,b.int_opt,am.custname ").getResultList();

            //    if (vectList.size() > 0) {
            for (int i = 0; i < vectList.size(); i++) {
                Vector curV = (Vector) vectList.get(i);
                int index = getIndex(intCalcDetailList, curV.get(0).toString(), Float.parseFloat(curV.get(1).toString()));
                if (index > -1) {
                    TdIntDetail tempObj = intCalcDetailList.get(index);
                    intCalcDetailList.remove(index);
                    tempObj.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    intCalcDetailList.add(tempObj);

                } else {
                    TdIntDetail tdIntDetail = new TdIntDetail();
                    tdIntDetail.setMsg("TRUE");
                    tdIntDetail.setAcno(curV.get(0).toString());

                    tdIntDetail.setCustName(curV.get(4).toString());
                    tdIntDetail.setIntOpt(curV.get(3).toString());
                    tdIntDetail.setStatus("");

                    tdIntDetail.setVoucherNo(Math.round(Float.parseFloat(curV.get(1).toString())));
                    tdIntDetail.setIntPaid(Double.parseDouble(curV.get(2).toString()));

                    tdIntDetail.setCustId(curV.get(5).toString());
                    intCalcDetailList.add(tdIntDetail);
                }
            }
            //  }

            vectList = em.createNativeQuery("select b.acno, '0', sum(ifnull(b.interest,0)), 'Q', substring(am.custName,1,28), "
                    + " ci.custid, b.fromdate, b.ToDt from dds_interesthistory b, accountmaster am, customerid ci "
                    + "where am.acno = ci.acno and b.acno = am.acno and am.curbrcode='" + brnCode + "' and b.fromdate between '" + frmDT + "' and  "
                    + "'" + lastdate + "' and (am.closingDate is null or am.closingDate = '' or am.closingDate >'" + lastdate + "') and "
                    + "am.tdsflag in ('Y','C') group by ci.custid, b.acno,am.custname ").getResultList();

            //  if (vectList.size() > 0) {
            for (int i = 0; i < vectList.size(); i++) {
                Vector curV = (Vector) vectList.get(i);
                int index = getIndex(intCalcDetailList, curV.get(0).toString(), Float.parseFloat(curV.get(1).toString()));
                if (index > -1) {
                    TdIntDetail tempObj = intCalcDetailList.get(index);
                    intCalcDetailList.remove(index);
                    tempObj.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    intCalcDetailList.add(tempObj);

                } else {
                    TdIntDetail tdIntDetail = new TdIntDetail();
                    tdIntDetail.setMsg("TRUE");
                    tdIntDetail.setAcno(curV.get(0).toString());

                    tdIntDetail.setCustName(curV.get(4).toString());
                    tdIntDetail.setIntOpt(curV.get(3).toString());
                    tdIntDetail.setStatus("");

                    tdIntDetail.setVoucherNo(Math.round(Float.parseFloat(curV.get(1).toString())));
                    tdIntDetail.setIntPaid(Double.parseDouble(curV.get(2).toString()));

                    tdIntDetail.setCustId(curV.get(5).toString());
                    intCalcDetailList.add(tdIntDetail);
                }
            }
            //  }

            Collections.sort(intCalcDetailList, new AcNoByComparator());
            System.out.println("Loop Start time is =" + new Date());
            List<TdsDetail> tdsDetailList = new ArrayList<TdsDetail>();
            if (intCalcDetailList.size() > 0 && intCalcDetailList.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                for (TdIntDetail tdIntDetail : intCalcDetailList) {
                    String acNo = tdIntDetail.getAcno();
                    String custId = tdIntDetail.getCustId();
                    String minorCustId = "", propCustId = "";
//                    /*Is Customer ID major for any minor */
//                    List isMinorList = em.createNativeQuery("select CustomerId from cbs_cust_minorinfo where guardiancode = '"+custId+"'").getResultList();
//                    if(!isMinorList.isEmpty()) {
//                        Vector minorCustIdVect = (Vector) isMinorList.get(0);
//                        minorCustId = minorCustIdVect.get(0).toString();
//                    }

                    String voucherNo = tdIntDetail.getVoucherNo().toString();
                    double interest = tdIntDetail.getInterest();
                    double intPaid = tdIntDetail.getIntPaid();
                    String panNo = "";
                    String acNature = ftsPostMgmtRepote.getAccountNature(acNo);
                    double totalIntPaid = 0;
                    double totalInt = 0;
                    //                    double totalTdsDeducted = getCustomerFinYearTds(acNo, frmDT, lastdate, "R", "");
                    //                    if (totalTdsDeducted == 0) {
                    //                        totalIntPaid = getFinYearIntOfCustomer(acNo, frmDT, lastdate, "");
                    //                        totalInt = getInterestSum(intCalcDetailList, custId);
                    //                    }else{
                    //                        totalIntPaid = getTotalIntPaid(intCalcDetailList, custId);
                    //                        totalInt = getInterestSum(intCalcDetailList, custId);
                    //                    }

                    //                    int taxCat = 0;
                    //                    //     double acctTds = 0d;
                    //                    List vectObj = em.createNativeQuery("select ifnull(a.taxcat,1) from customerid a where a.acno='" + acNo + "'").getResultList();
                    //                    if (vectObj.size() > 0) {
                    //                        Vector vect = (Vector) vectObj.get(0);
                    //                        taxCat = Integer.parseInt(vect.get(0).toString());
                    //                    }
                    //    List vectObj1 = null;
                    //                    System.out.println("acno:"+acNo);
                    //                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    //                        vectObj1 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b,accountmaster tdam "
                    //                                + "where tdam.CurBrCode='" + brnCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO "
                    //                                + "and b.FROMDT >='" + firstdate + "' AND  b.TODT <='" + lastdate + "'").getResultList();
                    //
                    //                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    //                        vectObj1 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b,td_accountmaster tdam "
                    //                                + "where tdam.CurBrCode='" + brnCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO "
                    //                                + "and b.FROMDT >='" + firstdate + "' AND  b.TODT <='" + lastdate + "'").getResultList();
                    //                    }
                    //
                    //                    if (vectObj1.size() > 0) {
                    //                        Vector vect = (Vector) vectObj1.get(0);
                    //                        acctTds = Double.parseDouble(vect.get(0).toString());
                    //                    }
                    double vchTds = 0d;
                    List vectObj2 = null;
                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        vectObj2 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b ,accountmaster tdam where "
                                + "tdam.CurBrCode='" + brnCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO and b.fromdt between '" + frmDT
                                + "' AND  '" + lastdate + "' and b.VoucherNo=" + voucherNo + "").getResultList();

                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        vectObj2 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b ,td_accountmaster tdam where "
                                + "tdam.CurBrCode='" + brnCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO and b.fromdt between '" + frmDT
                                + "' AND  '" + lastdate + "' and b.VoucherNo=" + voucherNo + "").getResultList();
                    }
                    if (vectObj2.size() > 0) {
                        Vector vect = (Vector) vectObj2.get(0);
                        vchTds = Double.parseDouble(vect.get(0).toString());
                    }

                    int panLen = 6;
                    List selectQuerPan = null;
//                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                        selectQuerPan = em.createNativeQuery("SELECT length(ifnull(tc.panno,0)),ifnull(tc.panno,'') FROM customermaster tc WHERE tc.custno =SUBSTRING('" + acNo + "',5,6) AND "
//                                + " tc.actype = SUBSTRING('" + acNo + "',3,2) AND tc.brncode = SUBSTRING('" + acNo + "',1,2)").getResultList();
//
//                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                        selectQuerPan = em.createNativeQuery("SELECT length(ifnull(tc.panno,0)),ifnull(tc.panno,'') FROM td_customermaster tc WHERE tc.custno =SUBSTRING('" + acNo + "',5,6) AND "
//                                + " tc.actype = SUBSTRING('" + acNo + "',3,2) AND tc.brncode = SUBSTRING('" + acNo + "',1,2)").getResultList();
//                    }                    
                    List mjOMinFlagLst = em.createNativeQuery("select minorflag from cbs_customer_master_detail where customerid ='" + custId + "'").getResultList();
                    if (mjOMinFlagLst.isEmpty()) {
                        throw new ApplicationException("minorFlag is not Set For " + custId);
                    }
                    Vector mjOMinVec = (Vector) mjOMinFlagLst.get(0);
                    String mFlag = mjOMinVec.get(0).toString();
                    if (mFlag.equalsIgnoreCase("Y")) {
                        selectQuerPan = em.createNativeQuery("select length(ifnull(ci.PAN_GIRNumber,0)),ifnull(ci.PAN_GIRNumber,'') from cbs_customer_master_detail ci,"
                                + " cbs_cust_minorinfo cm where ci.customerid = cm.guardiancode "
                                + " and cm.customerid = '" + custId + "'").getResultList();
                    } else {
                        selectQuerPan = em.createNativeQuery("select length(ifnull(PAN_GIRNumber,0)),ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail "
                                + " where customerid = '" + custId + "'").getResultList();
                    }

                    if (!selectQuerPan.isEmpty()) {
                        Vector vPan = (Vector) selectQuerPan.get(0);
                        panLen = Integer.parseInt(vPan.get(0).toString());
                        panNo = vPan.get(1).toString();
                    }

                    float trsRate = 0;

                    if (panLen == 10) {
                        trsRate = Float.parseFloat(v65.get(1).toString());
                    } else {
                        trsRate = Float.parseFloat(v65.get(2).toString());
                    }

                    if (trsRate == -1) {
                        return getErrorMsgList("Tds applicable rate does not exist in tds slab. So please fill tds slab.");
                    }

                    //To Do look into the matter
                    //if (totalInt + totalIntPaid - acctTds >= tdsApplicableAmt) {
                    double tdsCal = 0;
                    double tdsToBeDucteded = 0;
                    double minorAccInt = 0;
                    /*Is Customer ID is minor Then don't calculate the TDS, because minor interst is attached with Major*/
                    List inMinorList = em.createNativeQuery("select a.CustomerId from cbs_cust_minorinfo a, cbs_customer_master_detail b where a.CustomerId = b.customerid and b.minorflag = 'Y' and a.CustomerId = '" + custId + "'").getResultList();
                    if (!inMinorList.isEmpty()) {
                        /*Is Customer ID major for any minor Then calculate the Total Minor Interest*/
                        List isMinorList = em.createNativeQuery("select CustomerId from cbs_cust_minorinfo where guardiancode = '" + custId + "'").getResultList();
                        if (!isMinorList.isEmpty()) {
                            for (int z = 0; z < isMinorList.size(); z++) {
                                Vector minorCustIdVect = (Vector) isMinorList.get(z);
                                minorCustId = minorCustIdVect.get(0).toString();
                                minorAccInt = minorAccInt + getInterestSum(intCalcDetailList, minorCustId);
                                if (minorAccInt != 0) {
                                    System.out.println("Major CustID:" + custId + "; Minor CustID:" + minorCustId + "; Int:" + minorAccInt);
                                }
                            }
                        }
                    } else {
                        /*Is Customer ID major for any minor Then calculate the Total Minor Interest*/
                        List isMinorList = em.createNativeQuery("select guardiancode from cbs_cust_minorinfo where CustomerId = '" + custId + "'").getResultList();
                        if (!isMinorList.isEmpty()) {
                            for (int z = 0; z < isMinorList.size(); z++) {
                                Vector minorCustIdVect = (Vector) isMinorList.get(z);
                                minorCustId = minorCustIdVect.get(0).toString();
                                minorAccInt = minorAccInt + getInterestSum(intCalcDetailList, minorCustId);
                                if (minorAccInt != 0) {
                                    System.out.println("Minor CustID:" + custId + "; Major CustID:" + minorCustId + "; Int:" + minorAccInt);
                                }
                            }
                        }
                    }

                    double propInt = 0;
                    /*Proprietorship handling*/
//                    List proCustIdList = em.createNativeQuery("select ci.CustId from customerid ci,td_accountmaster tm where opermode = 8 and "
//                            + " ci.custid = tm.custid1 "
//                            + " union all "
//                            + " select ci.CustId from customerid ci,accountmaster tm where tm.opermode = 8 and "
//                            + " ci.custid = tm.custid1 and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))").getResultList();
//                    if (!proCustIdList.isEmpty()) {
//                        for (int y = 0; y < proCustIdList.size(); y++) {
//                            Vector minorCustIdVect = (Vector) proCustIdList.get(y);
//                            propCustId = minorCustIdVect.get(0).toString();
//                            propInt = propInt + getInterestSum(intCalcDetailList, propCustId);
//                            if (propInt != 0) {
//                                System.out.println("==>>>>CustID:" + custId + "; Prop CustID:" + propCustId + "; Int:" + propInt);
//                            }
//                        }
//                    }
                    //tds deduct nahi hua hai
                    double totalTdsDeducted = autoRenewRemote.getCustomerFinYearTds(acNo, frmDT, lastdate, "R", "");
                    if (totalTdsDeducted == 0) {
                        //total int paid for the fin year
                        //total int calculation for this quarter
                        //totalIntPaid = getFinYearIntOfCustomer(acNo, frmDT, lastdate, "");
                        totalIntPaid = getTotalIntPaid(intCalcDetailList, custId);
                        totalInt = getInterestSum(intCalcDetailList, custId);
                        // if greate then applicable amt
                        if (totalInt + minorAccInt + propInt + totalIntPaid >= tdsApplicableAmt) {
                            tdsCal = ((interest + intPaid) * trsRate / 100);
                            tdsCal = CbsUtil.round(tdsCal, 0);
                            tdsToBeDucteded = tdsCal - vchTds;
                        }
                        //tds deduct hua hai   
                    } else {
                        // totalIntPaid = getTotalIntPaid(intCalcDetailList, custId);
                        // totalInt = getInterestSum(intCalcDetailList, custId);
                        //                        tdsCal = (interest * trsRate / 100);
                        //                        tdsCal = CbsUtil.round(tdsCal, 0);
                        //                        tdsToBeDucteded = tdsCal;
                        if (tdIntDetail.getStatus().equalsIgnoreCase("A")) {
                            if (vchTds > 0) {
                                double mIntPaid = 0;
                                if (tdIntDetail.getIntOpt().equalsIgnoreCase("M")) {
                                    List monIntPaid = em.createNativeQuery("select ifnull(sum(td.interest),0) from td_vouchmst tv, "
                                            + "td_interesthistory td  where tv.acno = '" + acNo + "' and tv.voucherno = '" + voucherNo + "' "
                                            + "and td.acno = tv.acno and td.voucherno = tv.voucherno and td.fromdt > '" + CbsUtil.dateAdd(firstdate, -1) + "'").getResultList();
                                    if (!monIntPaid.isEmpty()) {
                                        Vector mIntVec = (Vector) monIntPaid.get(0);
                                        mIntPaid = Double.parseDouble(mIntVec.get(0).toString());
                                    }
                                }
                                tdsCal = ((interest + minorAccInt + propInt + mIntPaid) * trsRate / 100);
                                tdsCal = CbsUtil.round(tdsCal, 0);
                                tdsToBeDucteded = tdsCal;
                            } else {
                                tdsCal = ((interest + minorAccInt + propInt + intPaid) * trsRate / 100);
                                tdsCal = CbsUtil.round(tdsCal, 0);
                                tdsToBeDucteded = tdsCal - vchTds;
                            }
                        }
                    }
                    //                    if (totalInt + totalIntPaid >= tdsApplicableAmt) {
                    //                        double tdsCal = 0;
                    //                        double tdsToBeDucteded = 0;
                    //                        if(totalTdsDeducted ==0){
                    //                            tdsCal = ((interest + intPaid) * trsRate / 100);
                    //                            tdsCal = CbsUtil.round(tdsCal, 0);
                    //                            tdsToBeDucteded = tdsCal - vchTds;
                    //                        }else{
                    //                            if(tdIntDetail.getStatus().equalsIgnoreCase("A")){
                    //                                tdsCal = ((interest + intPaid) * trsRate / 100);
                    //                                tdsCal = CbsUtil.round(tdsCal, 0);
                    //                                tdsToBeDucteded = tdsCal - vchTds;
                    //                            }
                    //                        }

                    if (tdsToBeDucteded > 0 || tdsCal > 0 || vchTds > 0) {
                        TdsDetail tdsDetail = new TdsDetail();
                        tdsDetail.setMsg("TRUE");
                        tdsDetail.setCustId(custId);
                        tdsDetail.setAccNo(acNo);

                        tdsDetail.setName(tdIntDetail.getCustName());
                        tdsDetail.setVoucherNo(voucherNo);
                        tdsDetail.setOption(tdIntDetail.getIntOpt());
                        tdsDetail.setPanNo(panNo);
                        tdsDetail.setStatus("");
                        tdsDetail.setTdsCalculated(tdsCal);
                        tdsDetail.setTdsDeducted(vchTds);
                        tdsDetail.setIntPaid(intPaid);
                        tdsDetail.setTdsToBeDed(tdsToBeDucteded);
                        tdsDetail.setIntCalculated(interest);
                        tdsDetail.setBnkName(bnkName);
                        tdsDetail.setBnkaddress(bnkAddress);
                        tdsDetailList.add(tdsDetail);
                    }
//                    }
                }
                Collections.sort(tdsDetailList, new CustIdByComparator());
                System.out.println("End time is =" + new Date());
                if (tdsDetailList.isEmpty()) {
                    UserTransaction ut = context.getUserTransaction();
                    ut.begin();
                    try {
                        List acNatureList = em.createNativeQuery("Select distinct acctnature from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "') and (glheadprov is not NULL and glheadprov!='')").getResultList();
                        if (acNatureList.size() > 0) {
                            for (int i = 0; i < acNatureList.size(); i++) {
                                Vector acNatureVect = (Vector) acNatureList.get(i);
                                Integer tdsEnteredList = em.createNativeQuery("insert into parameterinfo_posthistory (actype, FromDt, ToDt, purpose, trandesc, dt, trantime, enterby, status, brncode) "
                                        + "values ('" + acNatureVect.get(0).toString() + "', '" + firstdate + "', '" + lastdate + "', 'TDS POSTED', 3, date_format(now(),'%Y%m%d'), now(), 'System', 1, '" + brnCode + "');").executeUpdate();
                                if (tdsEnteredList <= 0) {
                                    throw new ApplicationException("Data is not saving for account nature " + acNatureVect.get(0).toString());
                                }
                            }
                        }
                        ut.commit();
                    } catch (ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException e) {
                        try {
                            ut.rollback();
                            throw new ApplicationException(e.getMessage());
                        } catch (IllegalStateException | SecurityException | SystemException ex) {
                            throw new ApplicationException(ex.getMessage());
                        }
                    }
                }
                return tdsDetailList;
            } else {
                return getErrorMsgList("Data does not exist.");
            }
        } catch (ApplicationException | NumberFormatException | IllegalStateException | NotSupportedException | SystemException e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public String tdsPostBeforeIntCalc(List<TdsDetail> tdsCalculation, String glHead, String brnCode, String frDt, String toDt, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Date dt = new Date();
        String date = ymd.format(dt);
        try {
            ut.begin();
            List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
            if (!tdsPostEnableList.isEmpty()) {
                Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                String tdsPostEnable = tdsPostEnableVect.get(0).toString();
                if (tdsPostEnable.equalsIgnoreCase("N")) {
                    throw new ApplicationException("TDS reserve functionality is disable. Please contact to system administrator");
                }
            } else {
                throw new ApplicationException("TDS reserve functionality is disable. Please contact to system administrator");
            }
            List tdsPostList = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'TDS POSTED' and fromdt = '" + frDt + "' and todt = '" + toDt + "' and brncode = '" + brnCode + "' and actype in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "')").getResultList();
            if (!tdsPostList.isEmpty()) {
                throw new ApplicationException("TDS is already posted for this duration.");
            }
            for (TdsDetail tdsDetail : tdsCalculation) {
                String acNo = tdsDetail.getAccNo();
                String vchNo = tdsDetail.getVoucherNo();
                String opt = tdsDetail.getOption();
                double tdsDedTo = tdsDetail.getTdsToBeDed();

                if (tdsDedTo > 0) {
                    //Code done by Dhirendra Singh
                    Integer tdsHisResult = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,recovered)"
                            + "VALUES('" + acNo + "'," + vchNo + "," + tdsDedTo + ",'" + date + "','" + toDt + "','" + frDt + "','" + opt + "','NR')").executeUpdate();
                    if (tdsHisResult <= 0) {
                        throw new ApplicationException("Data Not Saved For " + acNo);
                    }
                }
            }
            List acNatureList = em.createNativeQuery("Select distinct acctnature from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "') and (glheadprov is not NULL and glheadprov!='')").getResultList();
            if (acNatureList.size() > 0) {
                for (int i = 0; i < acNatureList.size(); i++) {
                    Vector acNatureVect = (Vector) acNatureList.get(i);
                    Integer tdsEnteredList = em.createNativeQuery("insert into parameterinfo_posthistory (actype, FromDt, ToDt, purpose, trandesc, dt, trantime, enterby, status, brncode) "
                            + "values ('" + acNatureVect.get(0).toString() + "', '" + frDt + "', '" + toDt + "', 'TDS POSTED', 3, date_format(now(),'%Y%m%d'), now(), '" + authBy + "', 1, '" + brnCode + "');").executeUpdate();
                    if (tdsEnteredList <= 0) {
                        throw new ApplicationException("Data is not saving for account nature " + acNatureVect.get(0).toString());
                    }
                }
            }
            ut.commit();
            return "true";
        } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    class CustIdByComparator implements Comparator<TdsDetail> {

        public int compare(TdsDetail tdIntDetailObj1, TdsDetail tdIntDetailObj2) {
            Long custId1 = Long.parseLong(tdIntDetailObj1.getCustId());
            Long custId2 = Long.parseLong(tdIntDetailObj2.getCustId());
            return custId1.compareTo(custId2);
        }
    }

    public int getIndex(List<TdIntDetail> list, String acno, float voucherNo) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAcno().equals(acno) && list.get(i).getVoucherNo() == voucherNo) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getInterestSum(List<TdIntDetail> txnList, String custId) {
        double sum = 0d;
        for (TdIntDetail minTxn : txnList) {
            if (minTxn.getCustId().equals(custId)) {
                sum = sum + minTxn.getInterest();
            }
        }
        return sum;
    }

    public double getTotalIntPaid(List<TdIntDetail> txnList, String custId) {
        double sum = 0d;
        for (TdIntDetail minTxn : txnList) {
            if (minTxn.getCustId().equals(custId)) {
                sum = sum + minTxn.getIntPaid();
            }
        }
        return sum;
    }

    private List<TdsDetail> getErrorMsgList(String msg) {
        List<TdsDetail> tdsDetailList = new ArrayList<TdsDetail>();
        TdsDetail tdsDetail = new TdsDetail();
        tdsDetail.setMsg(msg);
        tdsDetailList.add(tdsDetail);
        return tdsDetailList;
    }

    public List getAcctDetail(String tmpAcc, String brCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            String acNat = ftsPostMgmtRepote.getAccountNature(tmpAcc);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Query selectQuery = em.createNativeQuery("Select am.CustName,am.Jtname1,am.jtname2,cb.Description from td_accountmaster am, codebook cb "
                        + "where acno='" + tmpAcc + "' and accstatus<>9 and am.OperMode=cb.code and cb.groupcode = 4");
                result = selectQuery.getResultList();
            } else {
                Query selectQuery = em.createNativeQuery("Select am.CustName,am.Jtname1,am.jtname2,cb.Description from accountmaster am, codebook cb "
                        + "where acno='" + tmpAcc + "' and accstatus<>9 and am.OperMode=cb.code and cb.groupcode = 4");
                result = selectQuery.getResultList();
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List getTableDetailForTdSingleEntry(String tmpAcc, String brCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Acno,voucherno,receiptNo,prinamt,date_format(fddt,'%d/%m/%Y') AS fddt,"
                    + "date_format(matDt,'%d/%m/%Y') AS matDt,date_format(TD_MadeDT,'%d/%m/%Y') AS TD_MadeDT,IntOpt,roi,status,seqno,"
                    + "lien From td_vouchmst where acno='" + tmpAcc + "' order by status,voucherno");
            result = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List getReceiptNo(String ac, String rt, String brCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select * From td_vouchmst Where acno='" + ac + "'  and voucherno ='" + rt + "'");
            result = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String save(List<GridData> generalGrid, float lblTotAmt, String brCode, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (lblTotAmt <= 0) {
                throw new ApplicationException("Please fill total amount");
            }
            String curBsDt = "";
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            if (!tempBd.isEmpty()) {
                Vector tempCurrent = (Vector) tempBd.get(0);
                curBsDt = tempCurrent.get(0).toString();
            }

            List postFlagResults = em.createNativeQuery("Select SimplePostFlag From td_parameterinfo").getResultList();
            String postFlag = "";
            if (!postFlagResults.isEmpty()) {
                Vector postFlagVec = (Vector) postFlagResults.get(0);
                postFlag = postFlagVec.get(0).toString();
            }

            float trsNumber = ftsPostMgmtRepote.getTrsNo();
            String tmpDetails;
            for (GridData gd : generalGrid) {
                String nature = ftsPostMgmtRepote.getAccountNature(gd.getAcNo());
                if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    String provision = orgFdInt.getProvApplyFlag(gd.getAcNo());
                    float recNo = ftsPostMgmtRepote.getRecNo();
                    String intOpts = gd.getIntOpt();
                    if (provision.toUpperCase().contains(intOpts.toUpperCase())) {
                        String acctCode = ftsPostMgmtRepote.getAccountCode(gd.getAcNo());
                        List provHeadList = em.createNativeQuery("Select glheadprov from accounttypemaster where acctcode='" + acctCode + "'").getResultList();
                        if (provHeadList.isEmpty()) {
                            throw new ApplicationException("Provision GL head does not exit.");
                        }
                        Vector provHeadVec = (Vector) provHeadList.get(0);
                        String provHead = brCode + provHeadVec.get(0).toString() + "01";
                        if (intOpts.equals("C")) {
                            tmpDetails = "TDS deduction from Provision Int Head. For Cumu Account ::" + gd.getAcNo() + "/" + gd.getRecptNo() + "/" + gd.getRtNo();
                        } else {
                            tmpDetails = "TDS deduction from Provision Int Head. For Account ::" + gd.getAcNo() + "/" + gd.getRecptNo() + "/" + gd.getRtNo();
                        }

                        Query insertQueryA = em.createNativeQuery("insert into recon_trf_d(acno,voucherno,drAmt,dt,ValueDt,enterBy,TranType,Ty,details,intflag,"
                                + "trsno,recno,TranDesc,Payby,iy,org_brnid,dest_brnid,Term_id,adviceNo,adviceBrnCode,tdacno) "
                                + "values ('" + provHead + "'," + gd.getRtNo() + "," + gd.getTdsAmt() + ",'" + curBsDt + "','" + curBsDt + "','" + user + "'," + 2 + ","
                                + 1 + ",'" + tmpDetails + "','T' ," + trsNumber + "," + recNo + "," + 33 + "," + 3 + "," + 1 + ",'" + brCode + "','" + brCode
                                + "','FROMFD','','','" + gd.getAcNo() + "')");
                        int varA = insertQueryA.executeUpdate();
                        if (varA <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    } else {
                        if ((intOpts.equals("M")) || (intOpts.equals("Q"))) {
                            String chkBalance = ftsPostMgmtRepote.checkBalance(gd.getAcNo(), Double.parseDouble(gd.getTdsAmt()), user);
                            if (!(chkBalance.equalsIgnoreCase("True"))) {
                                throw new ApplicationException("For " + chkBalance + " " + gd.getRecptNo());
                            }
                            String rs = ftsPostMgmtRepote.updateBalance(nature, gd.getAcNo(), 0, Double.parseDouble(gd.getTdsAmt()), "", "");
                            if (!rs.equalsIgnoreCase("True")) {
                                throw new ApplicationException("Problem in data updation");
                            }
                        } else if ((intOpts.equals("S")) && (!postFlag.equals("1"))) {
                            String chkBalance = ftsPostMgmtRepote.checkBalance(gd.getAcNo(), Double.parseDouble(gd.getTdsAmt()), user);
                            if (!(chkBalance.equalsIgnoreCase("True"))) {
                                throw new ApplicationException("For " + chkBalance + " " + gd.getRecptNo());
                            }
                            String rs = ftsPostMgmtRepote.updateBalance(nature, gd.getAcNo(), 0, Double.parseDouble(gd.getTdsAmt()), "", "");
                            if (!rs.equalsIgnoreCase("True")) {
                                throw new ApplicationException("Problem in data updation");
                            }
                        }
                        float netAmt = 0;
                        List netAmtList = em.createNativeQuery("Select sum(ifnull(crAmt,0))-sum(ifnull(drAmt,0)) From td_recon where Acno='" + gd.getAcNo()
                                + "' and CloseFlag is null and auth='Y'").getResultList();
                        if (!netAmtList.isEmpty()) {
                            Vector netAmtLists = (Vector) netAmtList.get(0);
                            netAmt = Float.parseFloat(netAmtLists.get(0).toString());
                        }
                        if (netAmt < Float.parseFloat(gd.getTdsAmt())) {
                            throw new ApplicationException("Insufficient Fund");
                        }
                        if (intOpts.equals("C")) {
                            tmpDetails = "TDS deduction. For Cumu Account ::" + gd.getAcNo() + "/" + gd.getRecptNo() + "/" + gd.getRtNo();
                        } else {
                            tmpDetails = "TDS deduction. For Account ::" + gd.getAcNo() + "/" + gd.getRecptNo() + "/" + gd.getRtNo();
                        }

                        Query insertQueryA = em.createNativeQuery("insert into recon_trf_d(acno,voucherno,drAmt,dt,ValueDt,enterBy,TranType,Ty,details,intflag,"
                                + "trsno,recno,TranDesc,Payby,iy,org_brnid,dest_brnid,Term_id,adviceNo,adviceBrnCode) "
                                + "values ('" + gd.getAcNo() + "'," + gd.getRtNo() + "," + gd.getTdsAmt() + ",'" + curBsDt + "','" + curBsDt + "','" + user + "'," + 2 + ","
                                + 1 + ",'" + tmpDetails + "','T' ," + trsNumber + "," + recNo + "," + 33 + "," + 3 + "," + 1 + ",'" + brCode + "','" + brCode
                                + "','FROMFD','','')");
                        int varA = insertQueryA.executeUpdate();
                        if (varA <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                    }
                } else if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    String table = "rdrecon";
                    if (nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        table = "ddstransaction";
                    }
                    float recNo = ftsPostMgmtRepote.getRecNo();
                    String chkBalance = ftsPostMgmtRepote.checkBalance(gd.getAcNo(), Double.parseDouble(gd.getTdsAmt()), user);
                    if (!(chkBalance.equalsIgnoreCase("True"))) {
                        throw new ApplicationException("For " + chkBalance + " " + gd.getRecptNo());
                    }
                    String rs = ftsPostMgmtRepote.updateBalance(nature, gd.getAcNo(), 0, Double.parseDouble(gd.getTdsAmt()), "", "");
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Problem in data updation");
                    }
                    float netAmt = 0;

                    List netAmtList = em.createNativeQuery("Select sum(ifnull(crAmt,0))-sum(ifnull(drAmt,0)) From " + table + " where Acno='" + gd.getAcNo() + "' and auth='Y'").getResultList();

                    if (!netAmtList.isEmpty()) {
                        Vector netAmtLists = (Vector) netAmtList.get(0);
                        netAmt = Float.parseFloat(netAmtLists.get(0).toString());
                    }
                    if (netAmt < Float.parseFloat(gd.getTdsAmt())) {
                        throw new ApplicationException("Insufficient Fund");
                    }

                    tmpDetails = "TDS deduction. For Account ::" + gd.getAcNo();

                    Query insertQueryA = em.createNativeQuery("insert into recon_trf_d(acno,drAmt,dt,ValueDt,enterBy,TranType,Ty,details,intflag,"
                            + "trsno,recno,TranDesc,Payby,iy,org_brnid,dest_brnid,Term_id,adviceNo,adviceBrnCode) "
                            + "values ('" + gd.getAcNo() + "'," + gd.getTdsAmt() + ",'" + curBsDt + "','" + curBsDt + "','" + user + "'," + 2 + ","
                            + 1 + ",'" + tmpDetails + "','' ," + trsNumber + "," + recNo + "," + 33 + "," + 3 + "," + 1 + ",'" + brCode + "','" + brCode
                            + "','','','')");
                    int varA = insertQueryA.executeUpdate();
                    if (varA <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }
                }
            }
            String tdsGLHead = "";
            List tmpGLHeadList = em.createNativeQuery("Select TDS_GLHead From tdsslab").getResultList();
            if (!tmpGLHeadList.isEmpty()) {
                Vector tmpGLHeadv3 = (Vector) tmpGLHeadList.get(0);
                tdsGLHead = brCode + tmpGLHeadv3.get(0).toString();
            }

            tmpDetails = "TDS Deduction";
            float recNo = ftsPostMgmtRepote.getRecNo();
            Query insertQueryB = em.createNativeQuery("insert into recon_trf_d(acno,crAmt,enterBy,TranType,Ty,Dt,ValueDt,Details,trsno,recno,TranDesc,"
                    + "Payby,iy,org_brnid,dest_brnid,Term_id,adviceNo,adviceBrnCode) values ('" + tdsGLHead + "'," + lblTotAmt + ",'" + user + "',"
                    + 2 + "," + 0 + ",'" + curBsDt + "','" + curBsDt + "','" + tmpDetails + "'," + trsNumber + "," + recNo + "," + 33 + "," + 3 + ","
                    + 1 + ",'" + brCode + "','" + brCode + "','FROMFD','','')");
            int varB = insertQueryB.executeUpdate();
            if (varB > 0) {
                ut.commit();
                return "Record Posted Successfully  And  Batch No is " + trsNumber;
            } else {
                ut.rollback();
                return "Problem in Posting";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String displayField(String tmpAcno, String txtRTNo, String brcode) throws ApplicationException {
        try {
            float lblreceiptNo;
            float CumuPrinAmt;
            List tempList = em.createNativeQuery("Select ReceiptNo,CumuPrinAmt,Status,OFFlag,IntOpt From td_vouchmst Where acno='" + tmpAcno
                    + "'  and voucherno =" + txtRTNo + "").getResultList();
            if (tempList.isEmpty()) {
                throw new ApplicationException("This Receipt No. does not exist");
            }
            Vector tempLists = (Vector) tempList.get(0);
            lblreceiptNo = Float.parseFloat(tempLists.get(0).toString());
            CumuPrinAmt = Float.parseFloat(tempLists.get(1).toString());
            String Status = tempLists.get(2).toString();
            String OFFlag = tempLists.get(3).toString();
            String intOpt = tempLists.get(4).toString();
            if ((Status.equals("C")) && (OFFlag.equals("N"))) {
                throw new ApplicationException("This Receipt is closed and amount is paid");
            }
            return lblreceiptNo + ": " + CumuPrinAmt + ": " + intOpt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String cmdLoadClick(String tmpAcc, String intOpt, float tdsAmount, String user, String brcode)
            throws ApplicationException {
        try {
            String provFlag = orgFdInt.getProvApplyFlag(tmpAcc);
            if (!provFlag.equals("") && provFlag.toUpperCase().contains(intOpt.toUpperCase())) {
                List postFlagResults = em.createNativeQuery("select ifnull(sum(interest),0) from td_interesthistory where acno='" + tmpAcc + "'").getResultList();
                float totalInt = 0;
                if (postFlagResults.size() > 0) {
                    Vector postFlagVec = (Vector) postFlagResults.get(0);
                    totalInt = Float.parseFloat(postFlagVec.get(0).toString());
                }
                if (totalInt < tdsAmount) {
                    return " For Balance Exceeds";
                }
            } else {
                List postFlagResults = em.createNativeQuery("Select SimplePostFlag From td_parameterinfo").getResultList();
                Vector postFlagVec = (Vector) postFlagResults.get(0);
                String postFlag = postFlagVec.get(0).toString();
                if ((intOpt.equals("M")) || (intOpt.equals("Q"))) {
                    String chkBalance = ftsPostMgmtRepote.checkBalance(tmpAcc, tdsAmount, user);
                    if (!(chkBalance.equalsIgnoreCase("True"))) {
                        return "For " + chkBalance;
                    }
                } else if ((intOpt.equals("S")) && (!postFlag.equals("1"))) {
                    String chkBalance = ftsPostMgmtRepote.checkBalance(tmpAcc, tdsAmount, user);
                    if (!(chkBalance.equalsIgnoreCase("True"))) {
                        return "For " + chkBalance;
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List tableAccountWise(String accountNo, String date, String orgnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            if (accountNo.equals("") || accountNo == null) {
                Query selectQuery = em.createNativeQuery("Select distinct tdv.prinamt,tdv.receiptno,tdv.acno,tdv.fddt,tdv.status,tdv.matDt,tdv.IntOpt,"
                        + "tdv.roi,tdv.years,tdv.months,tdv.days,tdv.period,tdv.seqno,tdv.voucherno,tdv.nextintpaydt from td_vouchmst tdv,td_accountmaster "
                        + "tda where tdv.matdt<='" + date + "'and tdv.status='A' AND tdv.ACNO = tda.ACNO and tda.accstatus not in (9,15) and tda.CurBrCode = '" + orgnCode + "' "
                        + "and tdv.VoucherNo not in (select rtNoHide from td_renewal_auth where auth ='N')");
                tableResult = selectQuery.getResultList();
            } else {
                Query selectQuery = em.createNativeQuery("Select distinct prinamt,receiptno,acno,fddt,status,matDt,IntOpt,roi,years,months,days,period,"
                        + "seqno,voucherno,nextintpaydt from td_vouchmst where matdt <='" + date + "' and status='A' and acno='" + accountNo + "' "
                        + "and VoucherNo not in (select rtNoHide from td_renewal_auth where auth ='N' and acno='" + accountNo + "')");
                tableResult = selectQuery.getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tableResult;
    }

    public List getBookSeries(String acctNature, String receipt, String brCode) throws ApplicationException {

        List tdreceiptissue = new ArrayList();
        if (receipt == null) {
            receipt = "";
        }
        try {
            if (receipt.equalsIgnoreCase("C")) {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='FD' and status='F' And brncode = '"
                        + brCode + "'").getResultList();
            } else if (receipt.equalsIgnoreCase("T")) {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='" + acctNature + "' and status='F' "
                        + "And brncode = '" + brCode + "'").getResultList();
            } else if (receipt.equalsIgnoreCase("N")) {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='" + acctNature + "' and status='F'"
                        + " And brncode = '" + brCode + "'").getResultList();
            } else {
                tdreceiptissue = em.createNativeQuery("select distinct series from td_receiptissue where scheme='" + acctNature + "' and status='F' "
                        + "And brncode = '" + brCode + "'").getResultList();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tdreceiptissue;
    }

    public List getTdRecieptSeq() throws ApplicationException {
        List tdparameterinfo = new ArrayList();
        try {
            tdparameterinfo = em.createNativeQuery("select coalesce(receiptno_seq,0) as receiptno_seq from td_parameterinfo").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tdparameterinfo;
    }

    public List getGlobalFdCondition() throws ApplicationException {
        List resultlist = new ArrayList();
        List listValue = new ArrayList();
        String applicableDate;
        String maxDate = "";
        try {
            resultlist = em.createNativeQuery("SELECT MAX(Applicable_Date) FROM tdcondition WHERE status = 'N'").getResultList();
            if (resultlist.size() > 0) {
                Vector resultlistVect = (Vector) resultlist.get(0);
                applicableDate = resultlistVect.get(0).toString();
                if (resultlist.isEmpty()) {
                    List getDate = em.createNativeQuery("select now()").getResultList();
                    if (getDate.size() > 0) {
                        Vector maxDateVect = (Vector) getDate.get(0);
                        maxDate = maxDateVect.get(0).toString();
                    }
                } else {
                    maxDate = applicableDate;
                }
            }
            listValue = em.createNativeQuery("SELECT TdAmount,TDDayMth,TDDayCum FROM tdcondition WHERE Applicable_Date = '" + maxDate + "'").getResultList();
            return listValue;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String cbsCustId(String acctType, String brCode) throws ApplicationException {
        List customerNoList = new ArrayList();
        String customerNo = "";
        try {
            String acctNature = ftsPostMgmtRepote.getAcNatureByCode(acctType);
            List parameterList = em.createNativeQuery("Select * From parameterinfo where UPPER(Acc_Seq)='I' ").getResultList();
            if (parameterList.size() > 0) {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    customerNoList = em.createNativeQuery(" select coalesce(max(custno),'000000') From td_customermaster where actype='" + acctType + "' AND brncode ='" + brCode + "'").getResultList();
                    if (customerNoList.size() > 0) {
                        Vector customerNoVec = (Vector) customerNoList.get(0);
                        customerNo = customerNoVec.get(0).toString();
                    }
                } else {
                    customerNoList = em.createNativeQuery(" select coalesce(max(custno),'000000') From customermaster where actype='" + acctType + "' AND brncode = '" + brCode + "'").getResultList();
                    if (customerNoList.size() > 0) {
                        Vector customerNoVec = (Vector) customerNoList.get(0);
                        customerNo = customerNoVec.get(0).toString();
                    }
                }
            } else {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    customerNoList = em.createNativeQuery("select coalesce(max(custno),'000000') From td_customermaster WHERE brncode='" + brCode + "'").getResultList();
                    if (customerNoList.size() > 0) {
                        Vector customerNoVec = (Vector) customerNoList.get(0);
                        customerNo = customerNoVec.get(0).toString();
                    }
                } else {
                    customerNoList = em.createNativeQuery("select coalesce(max(custno),'000000') From customermaster WHERE brncode ='" + brCode + "'").getResultList();
                    if (customerNoList.size() > 0) {
                        Vector customerNoVec = (Vector) customerNoList.get(0);
                        customerNo = customerNoVec.get(0).toString();
                    }
                }
            }
            return customerNo;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String cbsTdRenewalNewAcct(String oldAcNo, String enteredBy, String orgnCode, String acType) throws ApplicationException {
        try {
            String tempDb = "";
            List tempDbList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' and brncode = '" + orgnCode + "'").getResultList();
            if (tempDbList.size() > 0) {
                Vector tempDbVec = (Vector) tempDbList.get(0);
                tempDb = tempDbVec.get(0).toString();
            }
            String custNo = cbsCustId(acType, orgnCode);
            int intCustNo = Integer.parseInt(custNo) + 1;
            String newCustNoNew = CbsUtil.lPadding(6, intCustNo);
            String oldCustNo = oldAcNo.substring(4, 10);
            String acNo = orgnCode + acType.toUpperCase() + newCustNoNew + "01";

            Integer tdCustInsert = em.createNativeQuery("INSERT INTO td_customermaster(custno,actype,title, custname,fathername,craddress,praddress,"
                    + "phoneno,dob,remarks,occupation,status,panno,grdname,relation,lastupdatedt,AgCode,EnteredBy,brncode) "
                    + "select  '" + newCustNoNew + "','" + acType + "',title, custname,fathername,craddress,praddress,phoneno,dob,remarks,"
                    + "occupation,status,panno,grdname,relation,lastupdatedt,AgCode,'" + enteredBy + "','" + orgnCode + "' FROM td_customermaster WHERE "
                    + "custno='" + oldCustNo + "'  and actype='" + acType + "' and brncode ='" + orgnCode + "'").executeUpdate();
            if (tdCustInsert <= 0) {
                throw new ApplicationException("Data not saved in td_customermaster");
            }
            Integer tdMasterInsert = em.createNativeQuery("INSERT td_accountmaster(acno,openingdt,introaccno,opermode,JtName1,JtName2,Relationship,"
                    + "remarks,accstatus,orgncode,nomination,enteredby,accttype,JtName3,JtName4,tdsDetails,tdsFlag,cust_Type,custName,closingdate,authby,"
                    + "lastupdatedt,CurBrCode)"
                    + "SELECT '" + acNo + "','" + tempDb + "',introaccno,opermode,JtName1,JtName2,Relationship,remarks,accstatus,orgncode,nomination,"
                    + "'" + enteredBy + "', AcctType,JtName3,JtName4,tdsDetails,TDSFlag,cust_Type,custname,closingdate, '" + enteredBy + "','" + tempDb
                    + "','" + orgnCode + "' FROM td_accountmaster WHERE acno='" + oldAcNo + "'").executeUpdate();
            if (tdMasterInsert <= 0) {
                throw new ApplicationException("data not saved on td_accountmaster");
            }

            Integer tdReconBalanInsert = em.createNativeQuery("INSERT INTO td_reconbalan(acno,balance,dt) VALUES('" + acNo + "',0,'" + tempDb + "')").executeUpdate();
            if (tdReconBalanInsert <= 0) {
                throw new ApplicationException("data not saved on td_reconbalan");
            }

            Integer documentsReceivedInsert = em.createNativeQuery(" Insert into documentsreceived(acno,groupdocu,docuno,docudetails,"
                    + "receiveddate) select '" + acNo + "',groupDocu,docuno,docudetails,'" + tempDb + "' from documentsreceived where acno='"
                    + oldAcNo + "'").executeUpdate();
            if (documentsReceivedInsert <= 0) {
                throw new ApplicationException("DATA NOT SAVED OF DOCUMENTRECEIVED");
            }

            Integer acnoMapping = em.createNativeQuery("insert into cbs_acno_mapping(old_ac_no,new_ac_no) VALUES('" + acNo + "','" + acNo + "')").executeUpdate();
            if (acnoMapping <= 0) {
                throw new ApplicationException("Data was not saved in mapping !");
            }
            String custIdNew = "";
            List custIdNewList = em.createNativeQuery("SELECT custid FROM customerid WHERE acno = '" + oldAcNo + "'").getResultList();
            if (custIdNewList.size() > 0) {
                Vector custIdNewVec = (Vector) custIdNewList.get(0);
                custIdNew = custIdNewVec.get(0).toString();
            }

            String alphaCode = "";
            List alphaCodeList = em.createNativeQuery("SELECT alphacode FROM branchmaster WHERE brncode=cast('" + orgnCode + "' as int)").getResultList();
            if (alphaCodeList.size() > 0) {
                Vector alphaCodeVec = (Vector) alphaCodeList.get(0);
                alphaCode = alphaCodeVec.get(0).toString();
            }
            Integer customerIdInsert = em.createNativeQuery("INSERT INTO customerid(custid,acno,enterby,txnbrn) VALUES('" + custIdNew + "','" + acNo + "','" + enteredBy + "','" + alphaCode + "')").executeUpdate();
            if (customerIdInsert <= 0) {
                throw new ApplicationException("Data not saved in customerid");
            }
            return acNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String tdRenewalSave(String acctNo, String renewFdDate, String renewMatDate, double tdsDeduct, int periodYY, int periodMM, int periodDD,
            float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries, float recieptNo,
            String enteredBy, String rVoucherNo, double rAmt, String orgnBrCode, double tmpBalInt, double txtAmt, double matPre,
            String stYear, String endYear, double lblLastFtds, double tmpIntTds, double tdsAmt, float roiNew, double renewMatAmt, double clActTdsToBeDeducted) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            String result = autoRenewRemote.tdRenewalSave(acctNo, renewFdDate, renewMatDate, tdsDeduct, periodYY, periodMM, periodDD, tmpVchNo,
                    optInterest, gFDDayMnth, gFDDayYrs, renewAc, recptCmb, cmbSeries, recieptNo, enteredBy, rVoucherNo, rAmt, orgnBrCode, tmpBalInt,
                    txtAmt, matPre, stYear, endYear, lblLastFtds, tmpIntTds, tdsAmt, roiNew, renewMatAmt, clActTdsToBeDeducted, "M");

            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public Float tdApplicableROIFdRenewal(String acno, String applicableRoi, double totAmt, String matDt, String wefDt, String presentDt) throws ApplicationException {
        List resultList = new ArrayList();
        BigDecimal interestRate = BigDecimal.ZERO, st = BigDecimal.ZERO, sc = BigDecimal.ZERO, mg = BigDecimal.ZERO;
        String custType = "";
        try {
            String acNat = ftsPostMgmtRepote.getAccountNature(acno);
            long dayDiff = CbsUtil.dayDiff(ymd.parse(wefDt), ymd.parse(matDt));
            if (applicableRoi.equalsIgnoreCase("W.e.f Of ROI")) {
                resultList = getTdSlabData(Integer.parseInt(String.valueOf(dayDiff)), totAmt, wefDt, acNat);
            } else if (applicableRoi.equalsIgnoreCase("Less ROI")) {
                resultList = getTdSlabData(Integer.parseInt(String.valueOf(dayDiff)), totAmt, presentDt, acNat);
            }

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(0);
                    interestRate = new BigDecimal(element.get(0).toString());
                    st = new BigDecimal(element.get(2).toString());
                    sc = new BigDecimal(element.get(3).toString());
                    mg = new BigDecimal(element.get(4).toString());
                }
                List acnoCustList = em.createNativeQuery("select cust_Type From td_accountmaster where acno = '" + acno + "'").getResultList();
                if (!acnoCustList.isEmpty()) {
                    Vector element = (Vector) acnoCustList.get(0);
                    custType = element.get(0).toString();
                }

                int dblBenifitAge = 0;
                boolean staffSc = false;
                if (!acno.equals("") && custType.equalsIgnoreCase("ST")) {
                    dblBenifitAge = ftsPostMgmtRepote.getCodeForReportName("DOUBLE-BENIFIT-AGE");
                    if (dblBenifitAge > 50) {
                        staffSc = orgFdInt.isStaffSeniorCitizen(acno, dblBenifitAge, wefDt);
                    }
                }
                if (staffSc && custType.equalsIgnoreCase("ST")) {
                    interestRate = interestRate.add(sc).add(st);
                } else if (custType.equalsIgnoreCase("SC")) {
                    interestRate = interestRate.add(sc);
                } else if (custType.equalsIgnoreCase("ST")) {
                    interestRate = interestRate.add(st);
                } else if (custType.equalsIgnoreCase("MG")) {
                    interestRate = interestRate.add(mg);
                } else {
                    return interestRate.floatValue();
                }
            } else {
                throw new ApplicationException("There is no slab corresponding to this period !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return interestRate.floatValue();
    }

    public List getTdSlabData(int noOfDays, double totAmt, String compareDt, String acNat) throws ApplicationException {
        List tdSlabList = new ArrayList();
        try {
            tdSlabList = em.createNativeQuery("select Interest_rate,Applicable_Date,ST,SC,MG From td_slab where fromDays <=" + noOfDays + " "
                    + "and toDays >=" + noOfDays + " and fromAmount <= " + totAmt + " and toAmount >= " + totAmt + " "
                    + "and acctNature = '" + acNat + "' and Applicable_Date in (select max(applicable_Date) from td_slab where applicable_date<='" + compareDt + "' and acctNature = '" + acNat + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tdSlabList;
    }

    public String LienAcno(String acno) throws ApplicationException {
        String lienacno = null;
        try {
            List tdList = em.createNativeQuery("select acno from loansecurity where lienacno = '" + acno + "'").getResultList();
            if (!tdList.isEmpty()) {
                Vector tdvec = (Vector) tdList.get(0);
                lienacno = tdvec.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return lienacno;
    }

    public String provisionCompleteAction(String acno, float rtNumber, String intOpt, String status, double prinAmt, double remainingInterest,
            double tdsToBeDeducted, double interestPaid, String fdDate, String matDate, double netAmt, float penalty, float roi, String user,
            String brCode, double actualInterest, String auth, String authBy) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            double tmpCrAmt = 0d;
            String tmpPreDetails;
            ut.begin();

            List list = em.createNativeQuery("select authby from  td_payment_auth where auth='Y' and acno = '" + acno + "' and voucherNo = " + rtNumber).getResultList();
            if (!list.isEmpty()) {
                Vector vect = (Vector) list.get(0);
                throw new ApplicationException("This receipt is already authorized by " + vect.get(0).toString());
            }

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            String accNature = ftsPostMgmtRepote.getAccountNature(acno);
            String acCode = ftsPostMgmtRepote.getAccountCode(acno);
            List accountList = em.createNativeQuery("select GLHeadInt,glheadprov from accounttypemaster where acctcode='" + acCode + "'").getResultList();
            if (accountList.isEmpty()) {
                throw new ApplicationException("Interest Head and Provision Head does not exist");
            }
            Vector accountLists = (Vector) accountList.get(0);
            if ((accountLists.get(0).equals("")) || (accountLists.get(0) == null)) {
                throw new ApplicationException("Interest Head does not exist");
            }
            String glHeadInt = brCode + accountLists.get(0).toString() + "01";

            if ((accountLists.get(1).equals("")) || (accountLists.get(1) == null)) {
                throw new ApplicationException("Provision Head does not exist");
            }
            String glHeadProv = brCode + accountLists.get(1).toString() + "01";

            String glTds = "";
            List tdsGlHead = em.createNativeQuery("select TDS_Glhead from tdsslab where TDS_Applicabledate=(select max(TDS_Applicabledate) from tdsslab)").getResultList();
            if (tdsGlHead.isEmpty()) {
                throw new ApplicationException("GL Head is not present in the TDS Slab");
            }
            Vector tGlhead = (Vector) tdsGlHead.get(0);
            String tdsGlHeads = tGlhead.get(0).toString();
            if (tGlhead.get(0) == null) {
                throw new ApplicationException("GL Head is not present in the TDS Slab");
            }
            glTds = brCode + tdsGlHeads;
            float trsNumber = ftsPostMgmtRepote.getTrsNo();
            float recNo = ftsPostMgmtRepote.getRecNo();
            tmpCrAmt = prinAmt;
            //tdBalance = tdBalance + prinAmt;
            if (status.equals("PREMATURE")) {
                double abc = actualInterest - interestPaid;
                tmpPreDetails = "Deposit Closed(Premature), Prin Amt: " + prinAmt + "and Int. for remaining days is " + abc;
            } else {
                tmpPreDetails = "Deposit Closed(Mature), Prin Amt: " + prinAmt + "and Int. for remaining days is " + remainingInterest;
            }
            Query insertQuery = em.createNativeQuery("insert into td_recon(acno,FDDt,Dt,ValueDt,Cramt,enterBy,TranType,Ty,closeFlag,details,recno, "
                    + "trsno,trantime,auth,authby,org_brnid,dest_brnid) values ('" + acno + "','" + Tempbd + "','" + Tempbd + "','" + Tempbd + "',"
                    + tmpCrAmt + ",'" + user + "', 2 ,0 ,'C','" + tmpPreDetails + "'," + recNo + "," + trsNumber + ",'" + Tempbd + "','" + auth + "','"
                    + authBy + "','" + brCode + "','" + brCode + "')");
            int varA = insertQuery.executeUpdate();
            if (varA <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }

            if (remainingInterest > 0) {
                String detail = "Bal. Int. Paid For Acno: " + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                String msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 0, Math.abs(remainingInterest), Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
                detail = "Trf To GL AcNo For :" + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 1, Math.abs(remainingInterest), Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
            } else if (remainingInterest < 0) {
                String detail = "Rev Prov. Int. Paid For Acno: " + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                String msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 1, Math.abs(remainingInterest), Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
                detail = "Trf To GL AcNo For :" + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 0, Math.abs(remainingInterest), Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
            }

            if (intOpt.equals("Cumulative")) {
                Query updateQ2 = em.createNativeQuery("UPDATE td_vouchmst SET CumuPrinAmt=" + netAmt + ",OFFlag='N',Status='C',cldt='"
                        + Tempbd + "',finalamt=" + netAmt + ",penalty=" + penalty + ",netroi=" + roi + "  WHERE ACNO='"
                        + acno + "' and voucherno=" + rtNumber + " ");
                int varQ2 = updateQ2.executeUpdate();
                if (varQ2 <= 0) {
                    throw new ApplicationException("Problem in data updation.");
                }
            } else {
                Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET OFFlag='N',Status='C',cldt='" + Tempbd + "',finalamt=" + netAmt
                        + ",penalty=" + penalty + ",netroi=" + roi + "  WHERE ACNO='" + acno + "' and voucherno=" + rtNumber + " ");
                int varQ1 = updateQ1.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data updation.");
                }
            }

            if (status.equals("MATURE")) {
                if (remainingInterest != 0) {
                    Query insertQueryA = em.createNativeQuery("Insert Into td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                            + "values ('" + acno + "','" + Tempbd + "'," + remainingInterest + "," + rtNumber + ",'" + Tempbd + "','" + Tempbd + "','"
                            + intOpt.substring(0, 1) + "')");
                    int varAZ = insertQueryA.executeUpdate();
                    if (varAZ <= 0) {
                        throw new ApplicationException("Problem in data insertion.");
                    }
                }
            } else {
                double balInterest = actualInterest - interestPaid;
                if (balInterest != 0) {
                    Query insertQueryB = em.createNativeQuery("insert into td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                            + "values ('" + acno + "','" + Tempbd + "'," + balInterest + "," + rtNumber + ",'" + Tempbd + "','" + Tempbd + "','"
                            + intOpt.substring(0, 1) + "')");
                    int varB = insertQueryB.executeUpdate();
                    if (varB <= 0) {
                        throw new ApplicationException("Problem in data insertion.");
                    }
                }
            }
            List selectQuery = em.createNativeQuery("Select TDSFlag From td_accountmaster where acno='" + acno + "' ").getResultList();
            Vector TDSFlagCurrent = (Vector) selectQuery.get(0);
            String TDSFlag = TDSFlagCurrent.get(0).toString();
            if ((TDSFlag.equals("Y") || TDSFlag.equals("C")) && (tdsToBeDeducted > 0 || tdsToBeDeducted != 0)) {
                Query insertQueryC = em.createNativeQuery("insert into tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                        + "values ('" + acno + "'," + rtNumber + "," + tdsToBeDeducted + ",'" + Tempbd + "','" + matDate + "','" + Tempbd + "','"
                        + intOpt.substring(0, 1) + "')");
                int varC = insertQueryC.executeUpdate();
                if (varC <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }

                String detail = "TDS Deducted For Acno: " + acno + "VoucherNo: " + rtNumber + "IntOpt: " + intOpt;
                double dblTds = tdsToBeDeducted;
                String msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 1, Math.abs(dblTds), Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
                String tdsDetails = "TDS Deducted For Acno" + acno + "VoucherNo: " + rtNumber + "IntOpt: " + intOpt;
                recNo = ftsPostMgmtRepote.getRecNo();
                String checkListP = ftsPostMgmtRepote.ftsPosting43CBS(glTds, 2, 0, Math.abs(dblTds), Tempbd, Tempbd, user, brCode, brCode, 0, tdsDetails,
                        trsNumber, recNo, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(checkListP.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(checkListP);
                }
            }

            List selectQuery1 = em.createNativeQuery("select TDSDeducted From td_vouchmst Where Acno='" + acno + "' and voucherno=" + rtNumber + " ").getResultList();
            if ((selectQuery1.isEmpty())) {
                Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET TDSDeducted=" + tdsToBeDeducted + " WHERE ACNO='" + acno + "' and voucherno=" + rtNumber + " ");
                int varQ1 = updateQ1.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }
            } else {
                Vector deduct = (Vector) selectQuery1.get(0);
                String dt = deduct.get(0).toString();
                double txtTd1 = Double.parseDouble(dt) + tdsToBeDeducted;
                Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET TDSDeducted=" + txtTd1 + " WHERE ACNO='" + acno + "' and voucherno=" + rtNumber + " ");
                int varQ1 = updateQ1.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }
            }
            ftsPostMgmtRepote.updateBalance(accNature, acno, prinAmt, 0, "Y", "Y");
            double totProvAmt = 0d;
            // if (intOpt.equals("Cumulative") || intOpt.equals("Simple")) {
            List intList = em.createNativeQuery("select sum(ifnull(interest,0)) from td_interesthistory where acno='" + acno
                    + "' and voucherno=" + rtNumber + " and intopt='" + intOpt.substring(0, 1) + "'").getResultList();
            Vector intVect = (Vector) intList.get(0);
            if (intVect.get(0) != null) {
                totProvAmt = Double.parseDouble(intVect.get(0).toString());
            }
            List tdsList = em.createNativeQuery("select sum(ifnull(tds,0)) from tdshistory where acno='" + acno
                    + "' and voucherno=" + rtNumber + " and intopt='" + intOpt.substring(0, 1) + "'").getResultList();
            Vector tdsVect = (Vector) tdsList.get(0);
            double totTdsAmt = 0d;
            if (tdsVect.get(0) != null) {
                totTdsAmt = Double.parseDouble(tdsVect.get(0).toString());
            }
            double totPayAmt = totProvAmt - totTdsAmt;
            double totPayAmt2 = totPayAmt + prinAmt;

            if (totPayAmt2 > netAmt) {
                double totPayAmt3 = totPayAmt2 - netAmt;
                String detail = "Prov. Int Rev. for AcNo: " + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                String msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 1, totPayAmt3, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }

                msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 0, totPayAmt3, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
            }
            if (totPayAmt > 0) {
                String detail = "Prov Int Paid on AcNo: " + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                String msg = ftsPostMgmtRepote.ftsPosting43CBS(acno, 8, 0, totPayAmt, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, "I", null, null, null, 0f, "N", "", "", "S");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
                detail = "Bal Prov. Int Paid For AcNo: " + acno + ", VoucherNo: " + rtNumber + " and IntOpt: " + intOpt;
                msg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 1, totPayAmt, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                        trsNumber, 0f, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, 0f, "N", "", "", "");
                if (!(msg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
            }

            String query = "update td_payment_auth set auth='" + auth + "',authBy='" + authBy + "' where acno = '" + acno + "' and voucherNo = " + rtNumber;
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            if (remainingInterest != 0) {
                ut.commit();
                return "Deposit has been marked as closed And Transfer Batch No." + trsNumber;
            } else {
                ut.commit();
                return "Deposit has been marked as closed";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }

        }
    }

    public String tdProvisionRenewal(String acno, String renewFDDt, String renewMatDT, double tdsDeduct, int periodYY, int periodMM,
            int periodDD, float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries,
            float tmpReciept, String enteredBy, String rVoucherNo, String rAcno, double rAmt, String orgBrCode, double tmpBalInt,
            double txtAmt, double matPre, String stYear, String endYear, float roiNew, double renewMatAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = autoRenewRemote.tdProvisionRenewal(acno, renewFDDt, renewMatDT, tdsDeduct, periodYY, periodMM, periodDD, tmpVchNo,
                    optInterest, gFDDayMnth, gFDDayYrs, renewAc, recptCmb, cmbSeries, tmpReciept, enteredBy, rVoucherNo, rAcno, rAmt, orgBrCode,
                    tmpBalInt, txtAmt, matPre, stYear, endYear, roiNew, renewMatAmt, "M");
            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String saveTdPaymentDetails(String acno, float rtNo, float receiptNo, String intOption, String status, double prinAmt, double remInt,
            double tdsToBeDeducted, double tdsDeducted, double interestPaid, String fdDate, String matDate, double netAmount, float penalty,
            float roi, String user, String brCode, double actualInterest, float acROI, float netConRoi, double clActTdsToBeDeducted,
            double clActTdsDeducted, double clActIntFinYear) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List acNoList = em.createNativeQuery("select t.acno from td_payment_auth t, customerid c where t.acno= c.acno and t.auth='N' and "
                    + "c.custid = (select custid from customerid where acno='" + acno + "')").getResultList();
            if (!acNoList.isEmpty()) {
                throw new ApplicationException("Some receipts are pending for authorization of this customer. Please "
                        + "authorize these receipts before making another payment for the same customer.");
            }
            List dtList = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) dtList.get(0);
            String curDt = tempCurrent.get(0).toString();

            Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET Status='C',cldt='" + curDt + "' WHERE ACNO='" + acno + "' and voucherno=" + rtNo);
            int varQ1 = updateQ1.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            String query = "INSERT INTO td_payment_auth(ACNO,VoucherNo,ReceiptNo,IntOpt,Status,PrinAmt,RemainingInt,TdsToBeDed,TDSDeducted,IntPaid,FDDT,MatDt,"
                    + "FinalAmt,Penalty,ROI,ActualTotInt,EnterBy,Auth,AuthBy,TranTime,actualROI,NetConROI,ClActTdsToBeDeducted,ClActTdsDeducted,ClActTdsIntfinYear) "
                    + "VALUES('" + acno + "'," + rtNo + "," + receiptNo + ",'" + intOption.substring(0, 1) + "','" + status.substring(0, 1) + "'," + prinAmt + ","
                    + remInt + "," + tdsToBeDeducted + "," + tdsDeducted + "," + interestPaid + ",'" + fdDate + "','" + matDate + "'," + netAmount + "," + penalty
                    + "," + roi + "," + actualInterest + ",'" + user + "','N','',now()," + acROI + "," + netConRoi + "," + clActTdsToBeDeducted + "," + clActTdsDeducted
                    + "," + clActIntFinYear + ")";
            Query insertQuery = em.createNativeQuery(query);
            varQ1 = insertQuery.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            ut.commit();
            return "Data successfully saved";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List getUnAuthAcNo(String orgnBrCode) throws ApplicationException {
        try {
            String query = "select distinct acno from td_payment_auth where auth='N' and substring(acno,1,2) = '" + orgnBrCode + "' "
                    + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "'))";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnAuthRtNo(String acNo) throws ApplicationException {
        try {
            String query = "select voucherNo from td_payment_auth where auth='N' and acno = '" + acNo + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getRtDetails(String acNo, float rtNo) throws ApplicationException {
        try {

            String query = "select acno,voucherno,receiptno,intopt,status,prinamt,remainingint,tdstobeded,tdsdeducted,intpaid,date_format(fddt,'%d/%m/%Y'),"
                    + "date_format(matdt,'%d/%m/%Y'),finalamt,penalty,roi,actualtotint,enterby,auth,authby,actualROI,NetConROI,ClActTdsToBeDeducted,"
                    + "ClActTdsDeducted,ClActTdsIntfinYear from td_payment_auth where auth='N' and acno = '" + acNo + "' and voucherNo = " + rtNo;
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String deleteTdPaymentDetails(String acNo, float rtNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String query = "UPDATE td_vouchmst SET Status='A',cldt=null where acno = '" + acNo + "' and voucherNo = " + rtNo;
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            query = "delete from td_payment_auth where auth='N' and acno = '" + acNo + "' and voucherNo = " + rtNo;
            rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            ut.commit();
            return "Receipt Detail successfully deleted";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String getContractedRoi(String acNo, float rtNo) throws ApplicationException {
        try {
            String query = "select roi from td_vouchmst where acno = '" + acNo + "' and voucherNo = " + rtNo;
            List rs = em.createNativeQuery(query).getResultList();;
            if (rs.isEmpty()) {
                throw new ApplicationException("Problem in data deletion");
            }
            Vector seqVec = (Vector) rs.get(0);
            return seqVec.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public int getActypePojo(List<AcTypeProvPojo> list, String acTp) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAcType().equalsIgnoreCase(acTp)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<TdsDetail> projectedTdsCalculation(String firstdate, String lastdate, String brCode) throws ApplicationException {
        try {
            System.out.println("Calculation Start + " + new Date());
            String finYr = autoRenewRemote.getFinYear(brCode);
            String frmDT = finYr + "0401";
            List<TdIntDetail> intCalcDetailList = new ArrayList<TdIntDetail>();
            List<TdIntDetail> subList;

            Object bnkNameObj = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where "
                    + "b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(brCode)).getSingleResult();
            String bnkName = ((Vector) bnkNameObj).elementAt(0).toString();
            String bnkAddress = ((Vector) bnkNameObj).elementAt(1).toString();

            //To Do customer Category now it is 1
            List selectQuer65 = em.createNativeQuery("SELECT ifnull(TDS_AMOUNT,-1),ifnull(TDS_RATE *(1 + TDS_SURCHARGE /100.0),-1),tdsRate_pan FROM tdsslab "
                    + "WHERE TYPE=1 AND TDS_APPLICABLEDATE IN(SELECT MAX(TDS_APPLICABLEDATE) FROM tdsslab WHERE TDS_APPLICABLEDATE<='" + lastdate
                    + "' AND TYPE=1)").getResultList();
            Vector v65 = (Vector) selectQuer65.get(0);

            float tdsApplicableAmt = Float.parseFloat(v65.get(0).toString());

            if (tdsApplicableAmt == -1) {
                return getErrorMsgList("Tds applicable amount does not exist in tds slab. So please fill tds slab.");
            }

            Query selectQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') and productcode is not null");
            List resultList = selectQuery.getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                Vector v = (Vector) resultList.get(i);
                subList = interestCalculation(firstdate, lastdate, v.get(0).toString(), brCode, "Q");
                intCalcDetailList.addAll(subList);

            }
            Query selectRdQuery = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.RECURRING_AC + "') and productcode is not null");
            List resultRdList = selectRdQuery.getResultList();
            List<TdIntDetail> rdSubList;
            for (int i = 0; i < resultRdList.size(); i++) {
                Vector v = (Vector) resultRdList.get(i);
                rdSubList = rdIntCalFacade.rdProjectedIntCalc(v.get(0).toString(), firstdate, lastdate, brCode);
                intCalcDetailList.addAll(rdSubList);

            }
            List resultDsList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.DEPOSIT_SC + "') and productcode is not null").getResultList();

            for (int i = 0; i < resultDsList.size(); i++) {
                Vector v = (Vector) resultDsList.get(i);
                List<TdIntDetail> dsIntSbuList = ddsFacade.intCalcForTds(firstdate, lastdate, v.get(0).toString(), brCode);
                intCalcDetailList.addAll(dsIntSbuList);
            }
            List vectList = em.createNativeQuery("select b.acno, b.voucherno, sum(ifnull(b.interest,0)), b.intopt, substring(am.custName,1,28), "
                    + "c.status,ci.custid from td_interesthistory b, td_accountmaster am, td_vouchmst c, customerid ci where am.acno = ci.acno and "
                    + "b.acno = am.acno and b.acno = c.acno and b.voucherno = c.voucherno and am.curbrcode='" + brCode + "' and b.dt between '"
                    + frmDT + "' and  '" + lastdate + "' and am.accstatus<>9 and (c.status <>'C' or c.ClDt between '" + frmDT + "' and '"
                    + lastdate + "') and am.tdsflag in ('Y','C') "
                    + " group by b.voucherno, b.acno,b.intopt,am.custname, c.status,ci.custid").getResultList();

            for (int i = 0; i < vectList.size(); i++) {
                Vector curV = (Vector) vectList.get(i);
                int index = getIndex(intCalcDetailList, curV.get(0).toString(), Float.parseFloat(curV.get(1).toString()));
                if (index > -1) {
                    TdIntDetail tempObj = intCalcDetailList.get(index);
                    intCalcDetailList.remove(index);
                    tempObj.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    intCalcDetailList.add(tempObj);

                } else {
                    TdIntDetail tdIntDetail = new TdIntDetail();
                    tdIntDetail.setMsg("TRUE");
                    tdIntDetail.setAcno(curV.get(0).toString());

                    tdIntDetail.setCustName(curV.get(4).toString());
                    tdIntDetail.setIntOpt(curV.get(3).toString());
                    tdIntDetail.setStatus(curV.get(5).toString());

                    tdIntDetail.setVoucherNo(Math.round(Float.parseFloat(curV.get(1).toString())));
                    tdIntDetail.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    tdIntDetail.setCustId(curV.get(6).toString());
                    intCalcDetailList.add(tdIntDetail);
                }
            }
            vectList = em.createNativeQuery("select b.acno, '0', sum(ifnull(b.interest,0)), 'Q', substring(am.custName,1,28), "
                    + " ci.custid, b.fromdate, b.ToDt from dds_interesthistory b, accountmaster am, customerid ci "
                    + "where am.acno = ci.acno and b.acno = am.acno and am.curbrcode='" + brCode + "' and b.dt between '" + frmDT + "' "
                    + "and  '" + lastdate + "' and (am.closingDate is null or am.closingDate = '' or am.closingDate >'" + lastdate + "') and "
                    + "am.tdsflag in ('Y','C') group by ci.custid, b.acno,am.custname ").getResultList();

            for (int i = 0; i < vectList.size(); i++) {
                Vector curV = (Vector) vectList.get(i);
                int index = getIndex(intCalcDetailList, curV.get(0).toString(), Float.parseFloat(curV.get(1).toString()));
                if (index > -1) {
                    TdIntDetail tempObj = intCalcDetailList.get(index);
                    intCalcDetailList.remove(index);
                    tempObj.setIntPaid(Double.parseDouble(curV.get(2).toString()));
                    intCalcDetailList.add(tempObj);

                } else {
                    TdIntDetail tdIntDetail = new TdIntDetail();
                    tdIntDetail.setMsg("TRUE");
                    tdIntDetail.setAcno(curV.get(0).toString());

                    tdIntDetail.setCustName(curV.get(4).toString());
                    tdIntDetail.setIntOpt(curV.get(3).toString());
                    tdIntDetail.setStatus("");

                    tdIntDetail.setVoucherNo(Math.round(Float.parseFloat(curV.get(1).toString())));
                    tdIntDetail.setIntPaid(Double.parseDouble(curV.get(2).toString()));

                    tdIntDetail.setCustId(curV.get(5).toString());
                    intCalcDetailList.add(tdIntDetail);
                }
            }
            Collections.sort(intCalcDetailList, new AcNoByComparator());
            System.out.println("Loop Stat time = " + new Date());
            List<TdsDetail> tdsDetailList = new ArrayList<TdsDetail>();
            if (intCalcDetailList.size() > 0 && intCalcDetailList.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                for (TdIntDetail tdIntDetail : intCalcDetailList) {
                    String acNo = tdIntDetail.getAcno();
                    String custId = tdIntDetail.getCustId();
                    String voucherNo = tdIntDetail.getVoucherNo().toString();
                    double interest = tdIntDetail.getInterest();
                    double intPaid = tdIntDetail.getIntPaid();
                    String panNo = "";
                    String acNature = ftsPostMgmtRepote.getAccountNature(acNo);
                    double totalIntPaid = 0;
                    double totalInt = 0;

                    //totalIntPaid = getTotalIntPaid(intCalcDetailList, custId);
                    // totalInt = getInterestSum(intCalcDetailList, custId);
                    // int taxCat = 0;
                    // double acctTds = 0d;
//                    List vectObj = em.createNativeQuery("select ifnull(a.taxcat,1) from customerid a where a.acno='" + acNo + "'").getResultList();
//                    if (vectObj.size() > 0) {
//                        Vector vect = (Vector) vectObj.get(0);
//                        taxCat = Integer.parseInt(vect.get(0).toString());
//                    }
                    double vchTds = 0d;
                    List vectObj2 = null;
                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        vectObj2 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b ,accountmaster tdam where "
                                + "tdam.CurBrCode='" + brCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO and b.DT between '" + frmDT
                                + "' AND  '" + lastdate + "' and b.VoucherNo=" + voucherNo + "").getResultList();

                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        vectObj2 = em.createNativeQuery("select ifnull(sum(TDS),0) from tdshistory b ,td_accountmaster tdam where "
                                + "tdam.CurBrCode='" + brCode + "' and b.acno='" + acNo + "' and b.Acno = tdam.ACNO and b.DT between '" + frmDT
                                + "' AND  '" + lastdate + "' and b.VoucherNo=" + voucherNo + "").getResultList();
                    }
                    if (vectObj2.size() > 0) {
                        Vector vect = (Vector) vectObj2.get(0);
                        vchTds = Double.parseDouble(vect.get(0).toString());
                    }

                    int panLen = 6;
                    List selectQuerPan = null;
//                    if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                        selectQuerPan = em.createNativeQuery("SELECT length(ifnull(tc.panno,0)),ifnull(tc.panno,'') FROM customermaster tc WHERE tc.custno =SUBSTRING('" + acNo + "',5,6) AND "
//                                + " tc.actype = SUBSTRING('" + acNo + "',3,2) AND tc.brncode = SUBSTRING('" + acNo + "',1,2)").getResultList();
//
//                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                        selectQuerPan = em.createNativeQuery("SELECT length(ifnull(tc.panno,0)),ifnull(tc.panno,'') FROM td_customermaster tc WHERE tc.custno =SUBSTRING('" + acNo + "',5,6) AND "
//                                + " tc.actype = SUBSTRING('" + acNo + "',3,2) AND tc.brncode = SUBSTRING('" + acNo + "',1,2)").getResultList();
//                    }
                    List mjOMinFlagLst = em.createNativeQuery("select minorflag from cbs_customer_master_detail where customerid ='" + custId + "'").getResultList();
                    if (mjOMinFlagLst.isEmpty()) {
                        throw new ApplicationException("minorFlag is not Set For " + custId);
                    }
                    Vector mjOMinVec = (Vector) mjOMinFlagLst.get(0);
                    String mFlag = mjOMinVec.get(0).toString();
                    if (mFlag.equalsIgnoreCase("Y")) {
                        selectQuerPan = em.createNativeQuery("select length(ifnull(ci.PAN_GIRNumber,0)),ifnull(ci.PAN_GIRNumber,'') from cbs_customer_master_detail ci,"
                                + " cbs_cust_minorinfo cm where ci.customerid = cm.guardiancode "
                                + " and cm.customerid = '" + custId + "'").getResultList();
                    } else {
                        selectQuerPan = em.createNativeQuery("select length(ifnull(PAN_GIRNumber,0)),ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail "
                                + " where customerid = '" + custId + "'").getResultList();
                    }

                    if (!selectQuerPan.isEmpty()) {
                        Vector vPan = (Vector) selectQuerPan.get(0);
                        panLen = Integer.parseInt(vPan.get(0).toString());
                        panNo = vPan.get(1).toString();
                    }

                    float trsRate = 0;

                    if (panLen == 10) {
                        trsRate = Float.parseFloat(v65.get(1).toString());
                    } else {
                        trsRate = Float.parseFloat(v65.get(2).toString());
                    }

                    if (trsRate == -1) {
                        return getErrorMsgList("Tds applicable rate does not exist in tds slab. So please fill tds slab.");
                    }

                    double tdsCal = 0;
                    double tdsToBeDucteded = 0;

                    //tds deduct nahi hua hai
                    double totalTdsDeducted = autoRenewRemote.getCustomerFinYearTds(acNo, frmDT, lastdate, "R", "");
                    if (totalTdsDeducted == 0) {
                        //total int paid for the fin year
                        //total int calculation for this quarter
                        // totalIntPaid = getFinYearIntOfCustomer(acNo, frmDT, lastdate, "");
                        totalIntPaid = getTotalIntPaid(intCalcDetailList, custId);
                        totalInt = getInterestSum(intCalcDetailList, custId);
                        // if greate then applicable amt
                        if (totalInt + totalIntPaid >= tdsApplicableAmt) {
                            tdsCal = ((interest + intPaid) * trsRate / 100);
                            tdsCal = CbsUtil.round(tdsCal, 0);
                            tdsToBeDucteded = tdsCal - vchTds;
                        }
                        //tds deduct hua hai   
                    } else {
                        if (tdIntDetail.getStatus().equalsIgnoreCase("A")) {
                            if (vchTds > 0) {
                                tdsCal = (interest * trsRate / 100);
                                tdsCal = CbsUtil.round(tdsCal, 0);
                                tdsToBeDucteded = tdsCal;
                            } else {
                                tdsCal = ((interest + intPaid) * trsRate / 100);
                                tdsCal = CbsUtil.round(tdsCal, 0);
                                tdsToBeDucteded = tdsCal - vchTds;
                            }
                        }
                    }
                    if (tdsToBeDucteded > 0 || tdsCal > 0 || vchTds > 0) {
                        TdsDetail tdsDetail = new TdsDetail();
                        tdsDetail.setMsg("TRUE");
                        tdsDetail.setCustId(custId);
                        tdsDetail.setAccNo(acNo);

                        tdsDetail.setName(tdIntDetail.getCustName());
                        tdsDetail.setVoucherNo(voucherNo);
                        tdsDetail.setOption(tdIntDetail.getIntOpt());

                        tdsDetail.setPanNo(panNo);
                        tdsDetail.setStatus("");

                        tdsDetail.setTdsCalculated(tdsCal);
                        tdsDetail.setTdsDeducted(vchTds);
                        tdsDetail.setIntPaid(intPaid);

                        tdsDetail.setTdsToBeDed(tdsToBeDucteded);
                        tdsDetail.setIntCalculated(interest);

                        tdsDetail.setBnkName(bnkName);
                        tdsDetail.setBnkaddress(bnkAddress);
                        tdsDetailList.add(tdsDetail);
                    }
                }
                Collections.sort(tdsDetailList, new CustIdByComparator());
                System.out.println("Calculation End + " + new Date());
                return tdsDetailList;
            } else {
                return getErrorMsgList("Data does not exist.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
}
