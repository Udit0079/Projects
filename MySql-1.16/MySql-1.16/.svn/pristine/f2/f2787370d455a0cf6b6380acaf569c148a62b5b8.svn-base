/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LockerRentDetail;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsBulkPostingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless(mappedName = "AutoTermDepositRenewal")
public class AutoTermDepositRenewal implements AutoTermDepositRenewalRemote {

    Date date = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    TermDepositeCalculationManagementFacadeRemote tdCalRemote;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPostMgmtRepote;
    @EJB
    CtsManagementFacadeRemote ctsRemote;
    @EJB
    TdReceiptManagementFacadeRemote tdRcptMgmtRemote;
    @EJB
    InterBranchTxnFacadeRemote ftsRemoteMethods;
    @EJB
    PostalDetailFacadeRemote postalRemoteMethods;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    FtsBulkPostingFacadeRemote ftsDrCr;
    @EJB
    private SbIntCalcFacadeRemote sbIntFacade;
    @EJB
    AccountOpeningFacadeRemote openingFacadeRemote;
    @EJB
    LoanGenralFacadeRemote loanFacade;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public String autoRenewTermDeposit() throws ApplicationException {
        String message = "true", period = "", series = "", renewedMatDate = "";
        double renewedMatAmt;
        try {
            int renewD = postalRemoteMethods.getCodeByReportName("RENEWED_DT_FLAG");
            int tDays = postalRemoteMethods.getCodeByReportName("TD_RENEWAL_DAYS");
            List accountList = getAllRenewalAccount(ymd.format(date));
            for (int j = 0; j < accountList.size(); j++) {
                Vector accountVector = (Vector) accountList.get(j);
                String receiptNo = accountVector.get(1).toString();
                String accountNo = accountVector.get(2).toString();
                String acNat = accountVector.get(18).toString();
                System.out.println("accountNo" + accountNo);
                //Checking of auto pay flag. because both auto renew and auto pay can not be applicable together.
                String autoPayFlag = accountVector.get(19).toString();
                if (autoPayFlag.equalsIgnoreCase("Y")) {
                    return "Both auto renew and payment can not be applied together for a/c-->" + accountNo;
                }

                String renewedTdDate = "";
                if (renewD == 0) {
                    renewedTdDate = accountVector.get(5).toString();
                } else {
                    //long dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(accountVector.get(5).toString())), 1)), ymd.parse(CbsUtil.dateAdd(ymd.format(new Date()), -1)));
                    long dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(accountVector.get(5).toString())), 1)), new Date());
                    if (dtDiff > tDays) {
                        renewedTdDate = dmy.format(date);
                    } else {
                        renewedTdDate = accountVector.get(5).toString();
                    }
                }

//                String renewedTdDate = "";
//                if (renewD == 0) {
//                    renewedTdDate = accountVector.get(5).toString();
//                } else if (renewD == 1) {
//                    renewedTdDate = dmy.format(date);
//                }
                //String provision = tdRcptMgmtRemote.getProvApplyFlag(accountNo);
                int years, month, days, tdDayMth, tdDayCum;

                if (!(accountVector.get(15) == null || accountVector.get(15).toString().equalsIgnoreCase(""))
                        || !(accountVector.get(16) == null || accountVector.get(16).toString().equalsIgnoreCase(""))
                        || !(accountVector.get(17) == null || accountVector.get(17).toString().equalsIgnoreCase(""))) {

                    if (accountVector.get(15) == null || accountVector.get(15).toString().equalsIgnoreCase("")) {
                        years = 0;
                    } else {
                        years = Integer.parseInt(accountVector.get(15).toString());
                    }
                    if (accountVector.get(16) == null || accountVector.get(16).toString().equalsIgnoreCase("")) {
                        month = 0;
                    } else {
                        month = Integer.parseInt(accountVector.get(16).toString());
                    }
                    if (accountVector.get(17) == null || accountVector.get(17).toString().equalsIgnoreCase("")) {
                        days = 0;
                    } else {
                        days = Integer.parseInt(accountVector.get(17).toString());
                    }
                    period = years + "Years" + month + "Months" + days + "Days";
                } else {
                    years = Integer.parseInt(accountVector.get(8).toString());
                    month = Integer.parseInt(accountVector.get(9).toString());
                    days = Integer.parseInt(accountVector.get(10).toString());
                    period = years + "Years" + month + "Months" + days + "Days";
                }
                String addYear = ymd.format(date).substring(0, 4);
                String stYear = "01" + "/" + "01" + "/" + addYear;
                String endYear = "31" + "/" + "12" + "/" + addYear;

                String matYears = CbsUtil.yearAdd(ymd.format(dmy.parse(renewedTdDate)), years);
                String tmpRs = CbsUtil.monthAdd(matYears, month);
                renewedMatDate = CbsUtil.dateAdd(tmpRs, days);

                if (renewedMatDate == null || renewedMatDate.equalsIgnoreCase("")) {
                    return "Renewed maturity date not found";
                }
                float rtno = Float.parseFloat(accountVector.get(13).toString());
                String intOpt = accountVector.get(6).toString();
                float roi = Float.parseFloat(accountVector.get(7).toString());

                double prinAmt = Double.parseDouble(accountVector.get(0).toString());
                double[] tdsDetails = globalRenewal(accountNo, rtno, "True", roi, 0, years, month, days, intOpt, prinAmt);

                double tdsDeducted = tdsDetails[0];
                double tdsDeductedForLastFinyear = tdsDetails[1];
                double tdsToBeDeducted = tdsDetails[2];

                double renewalAmount = tdsDetails[3];
                double balint = tdsDetails[4];

                double totalIntRateCal = tdsDetails[5];
                double clActTdsToBeDeducted = tdsDetails[6];

                String renewAmt = tdRcptMgmtRemote.orgFDInterest(intOpt, roi, ymd.format(dmy.parse(renewedTdDate)), renewedMatDate, renewalAmount, period, accountNo.substring(0, 2));
                if (renewAmt == null || renewAmt.equalsIgnoreCase("")) {
                    return "Renewed maturity amount not found";
                } else {
                    renewedMatAmt = Double.parseDouble(renewAmt) + renewalAmount;
                }

                List custCatList = em.createNativeQuery("select ifnull(cust_type,'OT') from td_accountmaster where acno ='" + accountNo + "'").getResultList();
                Vector cuVec = (Vector) custCatList.get(0);
                String cType = cuVec.get(0).toString();

                roi = tdRcptMgmtRemote.tdApplicableROI(accountNo, cType, renewedMatAmt, renewedMatDate, ymd.format(dmy.parse(renewedTdDate)), ymd.format(date), acNat);

                List globalFDList = tdCalRemote.getGlobalFdCondition();
                if (!globalFDList.isEmpty()) {
                    Vector globalFDVector = (Vector) globalFDList.get(0);
                    tdDayMth = Integer.parseInt(globalFDVector.get(1).toString());
                    tdDayCum = Integer.parseInt(globalFDVector.get(2).toString());
                } else {
                    return "Data does not exist in TDCondition table";
                }

                if (renewalAmount >= prinAmt) {

                    try {
                        String msg = tdRenewalSave(accountNo, ymd.format(dmy.parse(renewedTdDate)), renewedMatDate, tdsToBeDeducted, years, month, days, rtno, intOpt, tdDayMth,
                                tdDayCum, "Existing", "Existing", "A", Float.parseFloat(receiptNo), "System", Float.toString(rtno), renewalAmount,
                                accountNo.substring(0, 2), balint, renewalAmount, renewalAmount, stYear, endYear, tdsDeductedForLastFinyear,
                                totalIntRateCal, tdsDeducted, roi, renewedMatAmt, clActTdsToBeDeducted, "A");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            return msg;
                        }

//                        int updateNo = em.createNativeQuery("update td_vouchmst set autorenew='N' where acno='" + accountNo + "' and voucherno=" + rtno + " and receiptno=" + receiptNo).executeUpdate();
//                        if (updateNo <= 0) {
//                            return "Updation problem in TD Vouchmst table";
//                        }
                        String[] resultArr = msg.split(":");
                        float newVchNo = Float.parseFloat(resultArr[4]);

                        int updateNo = em.createNativeQuery("update td_vouchmst set autorenew='Y' where acno='" + accountNo + "' and voucherno=" + newVchNo + " and receiptno=" + receiptNo).executeUpdate();
                        if (updateNo <= 0) {
                            return "Updation problem in TD Vouchmst table";
                        }

                        updateNo = em.createNativeQuery("update td_renewal_auth set auth='Y', authby='System' where acno='" + accountNo + "' and rtNoHide=" + rtno).executeUpdate();
                        if (updateNo <= 0) {
                            return "Updation problem in TD Vouchmst table";
                        }

//                        updateNo = em.createNativeQuery("update td_renewal_auth set auth='Y', authby='System' where acno='" + accountNo + "' and rtNoHide=" + newVchNo).executeUpdate();
//                        if (updateNo <= 0) {
//                            return "Updation problem in TD Vouchmst table";
//                        }
                    } catch (Exception e) {
                        if (e.getMessage().equalsIgnoreCase("This receipt is Lien Marked")) {
                            String reason = "This Receipt is Lien Marked.";
                            int updateNo = em.createNativeQuery("INSERT INTO td_pending_auto_renewal (ACNO, ReceiptNo, voucherno, Reason,dt,TranTime) "
                                    + "VALUES ('" + accountNo + "'," + receiptNo + ", " + rtno + ", '" + reason + "','" + ymd.format(date) + "', now())").executeUpdate();
                            if (updateNo <= 0) {
                                return "Problem in data insertion in td_pending_auto_renewal";
                            }
                        } else {
                            return e.getMessage();
                        }
                    }
                } else {
                    String reason = "This Receipt could not be renewed beacuse of some TDS related issue. So please make a payment and then create new one.";
                    int updateNo = em.createNativeQuery("INSERT INTO td_pending_auto_renewal (ACNO, ReceiptNo, voucherno, Reason,dt,TranTime) "
                            + "VALUES ('" + accountNo + "'," + receiptNo + ", " + rtno + ", '" + reason + "','" + ymd.format(date) + "', now())").executeUpdate();
                    if (updateNo <= 0) {
                        return "Problem in data insertion in td_pending_auto_renewal";
                    }
                }
            }
            //}
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public List getAllRenewalAccount(String dt) throws ApplicationException {
        List accountList = new ArrayList();
        try {
            int code = 0;
            List codeList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='FD_SAME_DATE_RENEWAL'").getResultList();
            if (!codeList.isEmpty()) {
                Vector v1 = (Vector) codeList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }
            if (code == 0) {
                accountList = em.createNativeQuery("Select distinct tdv.prinamt,tdv.receiptno,tdv.acno,tdv.fddt,tdv.status,DATE_FORMAT(matdt,'%d/%m/%Y'),tdv.IntOpt,tdv.roi,"
                        + "tdv.years,tdv.months,tdv.days,tdv.period,tdv.seqno,tdv.voucherno,tdv.nextintpaydt,tdv.renewyears,tdv.renewmonths,tdv.renewdays,a.acctnature,ifnull(tdv.auto_pay,'') "
                        + "from td_vouchmst tdv,td_accountmaster tda, accounttypemaster a where tdv.matdt<'" + dt + "' and tdv.autorenew ='Y' and tdv.status='A' and "
                        + "tdv.acno = tda.acno and tda.accstatus not in (9,15) and a.acctcode = tda.accttype and tda.CurBrCode in (select bd.brncode from bankdays bd, "
                        + "branchmaster bm where Date='" + dt + "' and DayBeginFlag <> 'H' and cast(bd.brncode as unsigned) = bm.brncode)").getResultList();
            } else {
                accountList = em.createNativeQuery("Select distinct tdv.prinamt,tdv.receiptno,tdv.acno,tdv.fddt,tdv.status,DATE_FORMAT(matdt,'%d/%m/%Y'),tdv.IntOpt,tdv.roi,"
                        + "tdv.years,tdv.months,tdv.days,tdv.period,tdv.seqno,tdv.voucherno,tdv.nextintpaydt,tdv.renewyears,tdv.renewmonths,tdv.renewdays,a.acctnature,ifnull(tdv.auto_pay,'') "
                        + "from td_vouchmst tdv,td_accountmaster tda, accounttypemaster a where tdv.matdt<='" + dt + "' and tdv.autorenew ='Y' and tdv.status='A' and "
                        + "tdv.acno = tda.acno and tda.accstatus not in (9,15) and a.acctcode = tda.accttype and tda.CurBrCode in (select bd.brncode from bankdays bd, "
                        + "branchmaster bm where Date='" + dt + "' and DayBeginFlag <> 'H' and cast(bd.brncode as unsigned) = bm.brncode)").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return accountList;
    }

    public double[] globalRenewal(String accountNo, float rtno, String maturePayment, float roi, int tdcumPrematureFlag, int years, int month, int days, String intOpt, double prinAmount) throws ApplicationException {

        try {
            List<Double> resultList = tdGlobal(accountNo, rtno, maturePayment, roi, tdcumPrematureFlag,
                    Integer.toString(years), Integer.toString(month), Integer.toString(days), "");

            double tdsDeducted = resultList.get(0);
            double tdsDeductedForLastFinyear = resultList.get(1);

            double tdsToBeDeducted = Math.round(resultList.get(2));

            double totalIntRateCal = resultList.get(3);
            double intPaidCal = resultList.get(4);
            double clActTdsToBeDeducted = resultList.get(5);
            if (clActTdsToBeDeducted < 0) {           // Software Bug #37389 as per Dhiru Sir
                clActTdsToBeDeducted = 0;
            }

            double balint = totalIntRateCal - intPaidCal;
            double amt = 0;
            double renewalAmount = 0.0f;
            if (intOpt.equalsIgnoreCase("M") || intOpt.equalsIgnoreCase("Q") || intOpt.equalsIgnoreCase("Y")) {
                amt = prinAmount + balint - tdsToBeDeducted - clActTdsToBeDeducted;
                renewalAmount = Math.round(amt);
                if (amt >= prinAmount) {
                    renewalAmount = Math.round(prinAmount);
                }
            } else if (intOpt.equalsIgnoreCase("C") || intOpt.equalsIgnoreCase("S")) {//|| intOpt.equalsIgnoreCase("Y")) {
                amt = prinAmount + intPaidCal + balint - tdsToBeDeducted;
                amt = amt - tdsDeducted;
                amt = amt - tdsDeductedForLastFinyear - clActTdsToBeDeducted;
                renewalAmount = Math.round(amt);
            }
            double[] tdsDetails = new double[7];
            tdsDetails[0] = tdsDeducted;
            tdsDetails[1] = tdsDeductedForLastFinyear;
            tdsDetails[2] = tdsToBeDeducted;
            tdsDetails[3] = renewalAmount;
            tdsDetails[4] = balint;
            tdsDetails[5] = totalIntRateCal;
            tdsDetails[6] = clActTdsToBeDeducted;
            return tdsDetails;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String tdRenewalSave(String acctNo, String renewFdDate, String renewMatDate, double tdsToBeDeducted, int periodYY, int periodMM, int periodDD,
            float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries, float recieptNo,
            String enteredBy, String rVoucherNo, double rAmt, String orgnBrCode, double tmpBalInt, double txtAmt, double matPre, String stYear, String endYear,
            double lastFinYearTds, double tmpIntTds, double tdsDeducted, float roiNew, double renewMatAmt, double clActTdsToBeDeducted, String autoManualFlag) throws ApplicationException {

        try {
            // ut.begin();
            float oldRecieptNo = recieptNo;
            String destBrnCode = acctNo.substring(0, 2);
            String tempbd = ymd.format(date);
            if (autoManualFlag.equalsIgnoreCase("M")) {

                List authChkList = em.createNativeQuery("Select acno from td_renewal_auth where rtNoHide=" + tmpVchNo + " "
                        + " and acno='" + acctNo + "' and ReceiptNo = " + oldRecieptNo + " and Auth='Y'").getResultList();
                if (!authChkList.isEmpty()) {
                    throw new ApplicationException("Receipt Already Authorized.");
                }

                List tempBdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG='N' and brncode = '" + orgnBrCode + "'").getResultList();
                if (tempBdList.size() <= 0) {
                    throw new ApplicationException("Data does not exist.");
                }
                Vector tempBdVec = (Vector) tempBdList.get(0);
                tempbd = tempBdVec.get(0).toString();
            }

            String acType = ftsPostMgmtRepote.getAccountCode(acctNo);
            String acctNature = ftsPostMgmtRepote.getAccountNature(acctNo);

            if (autoManualFlag.equalsIgnoreCase("A")) {
                List acNoList = em.createNativeQuery("SELECT * FROM td_vouchmst where acno='" + acctNo + "' and voucherno=" + tmpVchNo + " and status='A'").getResultList();
                if (acNoList.size() <= 0) {
                    throw new ApplicationException("Receipt Already Marked As Close");
                }
            } else if (autoManualFlag.equalsIgnoreCase("M")) {
                List authVal = em.createNativeQuery("select * from td_renewal_auth where acno ='" + acctNo + "' and rtNoHide =" + tmpVchNo + " "
                        + " and Receiptno= " + recieptNo + " and auth ='Y'").getResultList();
                if (!authVal.isEmpty()) {
                    throw new ApplicationException("Receipt Already Marked Authorize");
                }
            }

            int postFlag = 0;
            if (optInterest.equalsIgnoreCase("S")) {
                List tmppostList = em.createNativeQuery("SELECT SimplePostFlag From td_parameterinfo").getResultList();
                if (tmppostList.size() > 0) {
                    Vector tmppostVec = (Vector) tmppostList.get(0);
                    postFlag = Integer.parseInt(tmppostVec.get(0).toString());
                }
            }
            long diff = CbsUtil.dayDiff(ymd.parse(renewFdDate), ymd.parse(renewMatDate));
            if (optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("S")) {
                if (diff < gFDDayMnth) {
                    throw new ApplicationException("Please note FD duration cannot be less than defined duration");
                }
            }
            if (optInterest.equalsIgnoreCase("C")) {
                if (diff < gFDDayYrs) {
                    throw new ApplicationException("Please note duration cannot be less than");
                }
            }

            List gltdsList = em.createNativeQuery("SELECT  DISTINCT  TDS_GLhead FROM tdsslab where LastUpdateDt = (select max(LastUpdateDt) from tdsslab)").getResultList();
            String tdsGLHead = "";
            if (gltdsList.size() > 0) {
                Vector gltdsVec = (Vector) gltdsList.get(0);
                tdsGLHead = orgnBrCode + gltdsVec.get(0).toString();
            }
            double tdBalance = 0;
            double provInt = 0;
            double provTds = 0;
            String newAcNo = "";
            if (renewAc.equalsIgnoreCase("New")) {
                newAcNo = tdCalRemote.cbsTdRenewalNewAcct(acctNo, enteredBy, orgnBrCode, acType);
            }
            if (recptCmb.equalsIgnoreCase("New")) {
                String tdReceiptSq = "";
                List sqList = em.createNativeQuery("SELECT COALESCE(receiptno_seq,'A') FROM td_parameterinfo").getResultList();
                if (sqList.size() > 0) {
                    Vector sqdsVec = (Vector) sqList.get(0);
                    tdReceiptSq = sqdsVec.get(0).toString();
                }

                if (tdReceiptSq.equalsIgnoreCase("C")) {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue"
                            + " Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' and brncode = '" + orgnBrCode + "') And "
                            + "Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' And brncode = '" + orgnBrCode + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    recieptNo = Float.parseFloat(rcnoVec.get(0).toString());

                    Integer entryList = em.createNativeQuery("UPDATE td_receiptissue SET status='U',LastUpdateBy='" + enteredBy + "', LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + recieptNo
                            + " and status = 'F' and scheme ='FD' and brncode = '" + orgnBrCode + "') as temp) and scheme ='FD' and status = 'F' and "
                            + "series='" + cmbSeries + "' And brncode = '" + orgnBrCode + "'").executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                } else if (tdReceiptSq.equalsIgnoreCase("N")) {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue "
                            + "Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' and brncode = '" + orgnBrCode + "') And "
                            + "Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' and brncode = '" + orgnBrCode + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    recieptNo = Float.parseFloat(rcnoVec.get(0).toString());

                    Integer entryList = em.createNativeQuery("UPDATE td_receiptissue set status='U',LastUpdateBy='" + enteredBy + "', LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + recieptNo + " and status = 'F' "
                            + "and scheme ='" + acctNature + "' and brncode = '" + orgnBrCode + "') as temp) and scheme ='" + acctNature + "' and status = 'F' "
                            + "and series='" + cmbSeries + "' And brncode = '" + orgnBrCode + "'").executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                } else if (tdReceiptSq.equalsIgnoreCase("T")) {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue "
                            + "Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' And brncode = '" + orgnBrCode + "') And "
                            + "Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' And brncode = '" + orgnBrCode + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    recieptNo = Float.parseFloat(rcnoVec.get(0).toString());

                    Integer entryList = em.createNativeQuery("UPDATE td_receiptissue set status='U',LastUpdateBy='" + enteredBy + "',LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + recieptNo + " and status = 'F' "
                            + "and scheme = '" + acctNature + "' And brncode = '" + orgnBrCode + "') as temp) and scheme ='" + acctNature + "' and status = 'F' and "
                            + "series='" + cmbSeries + "' And brncode = '" + orgnBrCode + "'").executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                } else {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue "
                            + "Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' And brncode = '" + orgnBrCode
                            + "') And Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' And brncode = '" + orgnBrCode
                            + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    recieptNo = Float.parseFloat(rcnoVec.get(0).toString());

                    Query updatelist = em.createNativeQuery("UPDATE td_receiptissue set status='U',LastUpdateBy='" + enteredBy + "',LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + recieptNo + " and status = 'F' "
                            + "and scheme ='" + acctNature + "' And brncode = '" + orgnBrCode + "') as temp) and scheme ='" + acctNature + "' and status = 'F' "
                            + "and series='" + cmbSeries + "' And brncode = '" + orgnBrCode + "'");
                    Integer stockInfo = updatelist.executeUpdate();
                    if (stockInfo <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                }
            }

            List finlamtList = new ArrayList();
            if (autoManualFlag.equalsIgnoreCase("A")) {
                finlamtList = em.createNativeQuery("SELECT DISTINCT acno,prinamt,voucherno,FDDt,status,matdt,IntOpt,roi,cldt,finalAmt,period,"
                        + "IntToAcNo,OFFlag,OFAcno,lien from td_vouchmst where status='A' and voucherNo='" + rVoucherNo + "' and acno='" + acctNo
                        + "' UNION SELECT DISTINCT vm.acno,vm.prinamt,vm.voucherno,vm.FDDt,vm.status,vm.matdt,vm.IntOpt,vm.roi,vm.cldt,"
                        + "vm.finalAmt,vm.period,vm.IntToAcNo,vm.OFFlag,vm.OFAcno,lien from td_vouchmst vm where OFFlag='Y' and vm.status='C' "
                        + "and vm.voucherNo='" + rVoucherNo + "' and vm.acno='" + acctNo + "'").getResultList();
            } else if (autoManualFlag.equalsIgnoreCase("M")) {
                finlamtList = em.createNativeQuery("SELECT DISTINCT acno,prinamt,voucherno,FDDt,status,matdt,IntOpt,roi,cldt,finalAmt,period,"
                        + "IntToAcNo,OFFlag,OFAcno,lien from td_vouchmst where status='C' and voucherNo='" + rVoucherNo + "' and acno='" + acctNo
                        + "' UNION SELECT DISTINCT vm.acno,vm.prinamt,vm.voucherno,vm.FDDt,vm.status,vm.matdt,vm.IntOpt,vm.roi,vm.cldt,"
                        + "vm.finalAmt,vm.period,vm.IntToAcNo,vm.OFFlag,vm.OFAcno,lien from td_vouchmst vm where OFFlag='Y' and vm.status='C' "
                        + "and vm.voucherNo='" + rVoucherNo + "' and vm.acno='" + acctNo + "'").getResultList();
            }

            if (finlamtList.size() <= 0) {
                throw new ApplicationException("Data Not exist in Td vouch master");
            }
            Vector tdVchList = (Vector) finlamtList.get(0);
            double prinamt = 0;
            if (tdVchList.get(1) != null) {
                prinamt = Double.parseDouble(tdVchList.get(1).toString());
            }
            float voucherno = 0;
            if (tdVchList.get(2) != null) {
                voucherno = Float.parseFloat(tdVchList.get(2).toString());
            }

            String matdt = "";
            if (tdVchList.get(5) != null) {
                matdt = tdVchList.get(5).toString();
            }
            double finalAmt = 0;
            if (tdVchList.get(9) != null) {
                finalAmt = Double.parseDouble(tdVchList.get(9).toString());
            }
            String intToAcNo = "";
            if (tdVchList.get(11) != null) {
                intToAcNo = tdVchList.get(11).toString();
            }
            String ofFlag = "";
            if (tdVchList.get(12) != null) {
                ofFlag = tdVchList.get(12).toString();
            }
            String ofAcno = "";
            if (tdVchList.get(13) != null) {
                ofAcno = tdVchList.get(13).toString();
            }
            String lien = "";
            if (tdVchList.get(14) != null) {
                lien = tdVchList.get(14).toString();
            }

            if (intToAcNo.length() < 12 && (optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("Y"))) {
                intToAcNo = acctNo;
            }
            String destnBrnCode = "";
            if (!intToAcNo.equals("")) {
                destnBrnCode = ftsPostMgmtRepote.getCurrentBrnCode(intToAcNo);
            }
            //Todo Check this condition
            if (lien.equalsIgnoreCase("Y") && ftsPostMgmtRepote.getCodeForReportName("LIEN-FD-RENEW") != 1) {
                throw new ApplicationException("This receipt is Lien Marked");
            }
            float maxVchNo = 0;
            float trsNo = 0;
            float recNo = 0;
            double balInt = 0;
            if (ofFlag.equalsIgnoreCase("Y")) {
                trsNo = ftsPostMgmtRepote.getTrsNo();
                recNo = ftsPostMgmtRepote.getRecNo();
                int ofReconInsert = em.createNativeQuery("INSERT INTO of_recon(acno,dramt,dt,ValueDt,ty,trantype,enterby,tdacno,voucherno,auth,trsno,recno,authby,org_brnid,dest_brnid)"
                        + "VALUES('" + ofAcno + "'," + finalAmt + ",'" + tempbd + "','" + renewFdDate + "',1,2,'" + enteredBy + "','" + acctNo + "'," + voucherno + ",'Y'," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                if (ofReconInsert <= 0) {
                    throw new ApplicationException("Data Not Saved");
                }
                Integer tdReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                        + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + rAmt + ",'" + enteredBy + "','Y','Transfer to',0,2," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                if (tdReconInsert <= 0) {
                    throw new ApplicationException("Data Not Saved");
                }
            } else {
                Query entryList = em.createNativeQuery("UPDATE td_vouchmst SET OFFlag='N',status='C',ClDt='" + tempbd + "',penalty=0 where voucherno=" + voucherno + " and acno='" + acctNo + "'");
                Integer var = entryList.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data Not Saved");
                }

                List mstList = em.createNativeQuery("SELECT ifnull(max(voucherNo),0) FROM td_vouchmst WHERE substring(acno,3,2)='" + acType + "'").getResultList();

                Vector rcnoVec = (Vector) mstList.get(0);
                maxVchNo = Float.parseFloat(rcnoVec.get(0).toString()) + 1;

                List glheadList = em.createNativeQuery("SELECT glheadprov,glheadInt FROM accounttypemaster WHERE acctcode= '" + acType + "'").getResultList();
                if (glheadList.isEmpty()) {
                    throw new ApplicationException("Problem in getting data from account type master.");
                }

                Vector glVec = (Vector) glheadList.get(0);
                if (glVec.get(1) == null || glVec.get(1).toString().equals("")) {
                    throw new ApplicationException("Please enter GL Head");
                }
                String glheadInt = orgnBrCode + glVec.get(1).toString() + "01";

                String glheadprov = "";
                if (optInterest.equalsIgnoreCase("S") && (postFlag == 2 || postFlag == 0)) {
                    if (glVec.get(0) == null || glVec.get(0).toString().equals("")) {
                        throw new ApplicationException("Please enter GL Provision Head");
                    }
                    glheadprov = orgnBrCode + glVec.get(0).toString() + "01";
                }

                trsNo = ftsPostMgmtRepote.getTrsNo();
                if (tdsToBeDeducted > 0) {
                    Integer tdsHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt) values('" + acctNo + "',"
                            + tmpVchNo + "," + tdsToBeDeducted + ",'" + tempbd + "','" + tempbd + "','" + tempbd + "','" + optInterest + "')").executeUpdate();
                    if (tdsHistory <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }

                    Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                            + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                            + "VALUES('" + acctNo + "'," + tmpVchNo + "," + tdsToBeDeducted + ",'" + tempbd + "','" + matdt + "','" + tempbd + "','"
                            + optInterest + "','R'," + trsNo + "," + recNo + "," + tmpVchNo + ",date_format(now(),'%Y%m%d'))");
                    int result = insertTdsQuery.executeUpdate();
                    if (result < 0) {
                        throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                    }

                    double tdsAmt = tdsToBeDeducted;
                    recNo = ftsPostMgmtRepote.getRecNo();
                    if (clActTdsToBeDeducted > 0) {
                        String finYr = getFinYear(orgnBrCode);
                        String frmDT = finYr + "0401";
                        String toDT = (Integer.parseInt(finYr) + 1) + "0331";

                        //String strResult = getTdsRateAndApplicableAmt(acctNo, tempbd);
                        // String[] strRsArr = strResult.split(":");
                        //float tdsRate = Float.parseFloat(strRsArr[1]);
//                        List intCloseList = em.createNativeQuery("select sum(a.interest), b.voucherno,b.acno,b.intopt from  td_interesthistory a, td_vouchmst b "
//                                + "where a.acno = b.acno and b.status ='C' and b.acno = '" + acctNo + "' and a.voucherno = b.voucherno and cldt >='"
//                                + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' and a.voucherno <>" + tmpVchNo + " group by a.voucherno").getResultList();
//                        List intCloseList = em.createNativeQuery("select sum(a.interest), b.voucherno,b.acno,b.intopt from  td_interesthistory a, td_vouchmst b, customerid c "
//                                + "where a.acno = b.acno and b.acno = c.acno and b.status ='C' and c.custid in (select custid from customerid where acno = '" + acctNo + "') and a.voucherno = b.voucherno and cldt >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "and a.voucherno <>" + tmpVchNo + " group by a.voucherno,a.acno").getResultList();
//                        tdsAmt = tdsAmt + closeActTdsPosting(intCloseList, tdsRate, frmDT, toDT, trsNo, recNo, tempbd, tmpVchNo, clActTdsToBeDeducted);
//                        List rdIntCloseList = em.createNativeQuery("select sum(a.interest), 0 ,b.acno,'C' from  rd_interesthistory a, accountmaster b "
//                                + "where a.acno = b.acno and b.acno = '" + acctNo + "' and closingdate >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "group by a.acno").getResultList();
//                        List rdIntCloseList = em.createNativeQuery("select sum(a.interest), 0 ,b.acno,'Q' from  rd_interesthistory a, accountmaster b, customerid c  "
//                                + "where a.acno = b.acno and b.acno = c.acno and c.custid in (select custid from customerid where acno = '" + acctNo + "') and closingdate >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "group by a.acno").getResultList();
//
//                        tdsAmt = tdsAmt + closeActTdsPosting(rdIntCloseList, tdsRate, frmDT, toDT, trsNo, recNo, tempbd, tmpVchNo, clActTdsToBeDeducted);
//                        List dsIntCloseList = em.createNativeQuery("select sum(a.interest), 0 ,b.acno,'C' from  dds_interesthistory a, accountmaster b "
//                                + "where a.acno = b.acno and b.acno = '" + acctNo + "' and closingdate >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
////                                + "group by a.acno").getResultList();
//                        List dsIntCloseList = em.createNativeQuery("select sum(a.interest), 0 ,b.acno,'Q' from  dds_interesthistory a, accountmaster b, customerid c  "
//                                + "where a.acno = b.acno and b.acno = c.acno and c.custid in (select custid from customerid where acno = '" + acctNo + "') and closingdate >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "group by a.acno").getResultList();
//
//                        tdsAmt = tdsAmt + closeActTdsPosting(dsIntCloseList, tdsRate, frmDT, toDT, trsNo, recNo, tempbd, tmpVchNo, clActTdsToBeDeducted);
//
//                        if (Math.round(tdsAmt) != (tdsToBeDeducted + clActTdsToBeDeducted)) {
//                            throw new ApplicationException("Tds Amount Problem");
//                        }
                        List nrCloseList = getUnRecoverdTdsAccounts(acctNo, frmDT, toDT, "NR", "Y");

                        closeActTdsPosting(nrCloseList, frmDT, toDT, trsNo, recNo, tempbd, tmpVchNo, clActTdsToBeDeducted, acctNo, optInterest);
                        tdsAmt = tdsAmt + clActTdsToBeDeducted;
                    }

                    Integer TD_Recon = em.createNativeQuery("INSERT INTO td_recon (acno,voucherno,drAmt,fdDt,dt,ValueDt,enterBy,TranType,Ty,details,intflag,trsno,recno,auth,authby,org_brnid,dest_brnid)"
                            + "VALUES('" + acctNo + "'," + tmpVchNo + "," + Math.round(tdsAmt) + ",'" + tempbd + "','" + tempbd + "','" + renewFdDate + "','" + enteredBy + "',2,1,'TDS Deducted For Acno','T'," + trsNo + "," + recNo + ",'Y','System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                    if (TD_Recon <= 0) {
                        throw new ApplicationException("Data Not Saved,Some Problem in Saving in the Database");
                    }
                    Integer GL_Recon = em.createNativeQuery("INSERT INTO gl_recon (acno,cramt,enterBy,TranType,Ty,dt,ValueDt,details,trsno,recno,auth,authby,org_brnid,dest_brnid,adviceNo,adviceBrnCode)"
                            + "VALUES('" + tdsGLHead + "'," + Math.round(tdsAmt) + ",'" + enteredBy + "',2,0,'" + tempbd + "','" + renewFdDate + "','TDS Deducted For Acno'," + trsNo + "," + recNo + ",'Y','System','" + orgnBrCode + "','" + destBrnCode + "','','')").executeUpdate();
                    if (GL_Recon <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                    String rs = ftsPostMgmtRepote.updateBalance("PO", tdsGLHead, Math.round(tdsAmt), 0, "Y", "Y");
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Problem in GL balance updation");
                    }

                    if (optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Y")) {
                        String rs1 = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, 0, Math.round(tdsAmt), "Y", "Y");
                        if (!rs1.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in balance updation");
                        }
                    }
                }

                if (optInterest.equalsIgnoreCase("S") || optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Y")) {
                    float RecNo = ftsPostMgmtRepote.getRecNo();
                    Integer TD_Recon = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,closeflag,authby,org_brnid,dest_brnid,voucherno)"
                            + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + prinamt + ",'" + enteredBy + "','Y','VCH: PrinAmt to',0,2," + trsNo + "," + RecNo + ",'C','System','" + orgnBrCode + "','" + destBrnCode + "'," + tmpVchNo + ")").executeUpdate();
                    if (TD_Recon <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }

                } else if (optInterest.equalsIgnoreCase("C")) {
                    List Td_InterestHistoryList = em.createNativeQuery("SELECT ifnull(SUM(Interest),0) FROM td_interesthistory where voucherno=" + voucherno + " and acno='" + acctNo + "'").getResultList();
                    double IntPaid = 0;
                    if (Td_InterestHistoryList.size() > 0) {
                        Vector balVec = (Vector) Td_InterestHistoryList.get(0);
                        IntPaid = Double.parseDouble(balVec.get(0).toString());
                    }
                    double tmpMatAmt = IntPaid + prinamt;
                    balInt = CbsUtil.round(tmpBalInt, 0);
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer TD_Recon = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,closeflag,authby,org_brnid,dest_brnid,VoucherNo)"
                            + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + tmpMatAmt + ",'" + enteredBy + "','Y','VCH: Trf.',0,2," + trsNo + "," + recNo + ",'C','System','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                    if (TD_Recon <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                    if (balInt > 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer TD_Reconmid = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + balInt + ",'" + enteredBy + "','VCH: Trf Bal Int',0,8," + trsNo + "," + recNo + ",'Y','System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                        if (TD_Reconmid <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        Integer TD_InterestHistory = em.createNativeQuery("INSERT INTO td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                                + "VALUES('" + acctNo + "','" + tempbd + "'," + balInt + ",'" + rVoucherNo + "','" + tempbd + "','" + tempbd + "','" + optInterest + "')").executeUpdate();
                        if (TD_InterestHistory <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer Gl_Recon = em.createNativeQuery("INSERT INTO gl_recon(acno,Dt,ValueDt,dramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,authby,org_brnid,dest_brnid,adviceNo,adviceBrnCode)"
                                + "VALUES('" + glheadInt + "','" + tempbd + "','" + renewFdDate + "'," + balInt + ",'" + enteredBy + "','VCH: Trf. Bal Int to vch',1,8," + trsNo + "," + recNo + ",'Y','System','" + orgnBrCode + "','" + destBrnCode + "','','')").executeUpdate();
                        if (Gl_Recon <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance("PO", glheadInt, 0, balInt, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }
                    }

                    if (balInt < 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer TD_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + Math.abs(balInt) + ",'" + enteredBy + "','VCH: Trf Bal Int. to vch',1,8," + trsNo + "," + recNo + ",'Y','System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                        if (TD_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        Integer TD_InterestHistory = em.createNativeQuery("INSERT INTO td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                                + "VALUES('" + acctNo + "','" + tempbd + "'," + balInt + ",'" + rVoucherNo + "','" + tempbd + "','" + tempbd + "','" + optInterest + "')").executeUpdate();
                        if (TD_InterestHistory <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,cramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,authby,org_brnid,dest_brnid)"
                                + "VALUES('" + glheadInt + "','" + tempbd + "','" + renewFdDate + "'," + Math.abs(balInt) + ",'" + enteredBy + "','VCH: Trf. Bal Int to vch',0,8," + trsNo + "," + recNo + ",'Y','System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                        if (Gl_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance("PO", glheadInt, Math.abs(balInt), 0, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }
                    }
                }

                if (optInterest.equalsIgnoreCase("S")) {
                    List balanList = em.createNativeQuery("SELECT ifnull(sum(Interest),0) FROM td_interesthistory WHERE VOUCHERNO=" + voucherno + " and acno='" + acctNo + "'").getResultList();
                    if (balanList.size() > 0) {
                        Vector balVec = (Vector) balanList.get(0);
                        provInt = Double.parseDouble(balVec.get(0).toString());
                    }

                    List tdsIntList = em.createNativeQuery("SELECT ifnull(sum(tds),0) FROM tdshistory WHERE VOUCHERNO=" + voucherno + " "
                            + "and acno='" + acctNo + "' and dt < '" + tempbd + "'").getResultList();
                    if (tdsIntList.size() > 0) {
                        Vector tdIntVec = (Vector) tdsIntList.get(0);
                        provTds = Double.parseDouble(tdIntVec.get(0).toString());
                    }

                    balInt = (matPre + tdsToBeDeducted + lastFinYearTds + tdsDeducted + clActTdsToBeDeducted) - provInt - prinamt;
                    balInt = CbsUtil.round(balInt, 0);
                    if ((postFlag == 2 || postFlag == 0) && optInterest.equalsIgnoreCase("S")) {
                        if (provInt > 0) {
                            recNo = ftsPostMgmtRepote.getRecNo();
                            Integer ReconbalanInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                    + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + provInt + ",'" + enteredBy + "','Y','VCH: Transfer to',0,8," + trsNo + "," + recNo + ",'System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                            if (ReconbalanInsert <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            recNo = ftsPostMgmtRepote.getRecNo();
                            Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                    + "VALUES('" + glheadprov + "','" + tempbd + "','" + renewFdDate + "'," + provInt + ",'" + enteredBy + "','Y','VCH: Transfer to',1,8," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                            if (Gl_ReconInsert <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            String rs = ftsPostMgmtRepote.updateBalance("PO", glheadprov, 0, provInt, "Y", "Y");
                            if (!rs.equalsIgnoreCase("True")) {
                                throw new ApplicationException("Problem in GL balance updation");
                            }
                        }

                        if (provTds > 0) {
                            recNo = ftsPostMgmtRepote.getRecNo();
                            Integer ReconbalanInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                    + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + provTds + ",'" + enteredBy + "','Y','VCH: Tds to',1,8," + trsNo + "," + recNo + ",'System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                            if (ReconbalanInsert <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            recNo = ftsPostMgmtRepote.getRecNo();
                            Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                    + "VALUES('" + glheadprov + "','" + tempbd + "','" + renewFdDate + "'," + provTds + ",'" + enteredBy + "','Y','VCH: Tds to',0,8," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                            if (Gl_ReconInsert <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            String rs = ftsPostMgmtRepote.updateBalance("PO", glheadprov, provTds, 0, "Y", "Y");
                            if (!rs.equalsIgnoreCase("True")) {
                                throw new ApplicationException("Problem in GL balance updation");
                            }
                        }
                    }
                    if (balInt > 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer TD_Reconmid = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + balInt + ",'" + enteredBy + "','Y','VCH: Trf BalInt. to vch',0,8," + trsNo + "," + recNo + ",'System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                        if (TD_Reconmid <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        Integer TD_InterestHistory = em.createNativeQuery("INSERT INTO td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                                + "VALUES('" + acctNo + "','" + tempbd + "'," + balInt + ",'" + rVoucherNo + "','" + tempbd + "','" + tempbd + "','" + optInterest + "')").executeUpdate();
                        if (TD_InterestHistory <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        Integer Gl_Recon = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                + "VALUES('" + glheadInt + "','" + tempbd + "','" + renewFdDate + "'," + balInt + ",'" + enteredBy + "','Y','VCH: Bal Int. for vch',1,8," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                        if (Gl_Recon <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance("PO", glheadInt, 0, balInt, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }
                    }
                    if (balInt < 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer TD_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + Math.abs(balInt) + ",'" + enteredBy + "','Y','VCH: Trf BalInt. to vch',1,8," + trsNo + "," + recNo + ",'System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                        if (TD_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                + "VALUES('" + glheadInt + "','" + tempbd + "','" + renewFdDate + "'," + Math.abs(balInt) + ",'" + enteredBy + "','Y','VCH: Bal Int. for vch',0,8," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                        if (Gl_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance("PO", glheadInt, Math.abs(balInt), 0, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }
                    }
                }

                if (optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Y")) {
                    List balanList = em.createNativeQuery("SELECT ifnull(sum(Interest),0) FROM td_interesthistory WHERE VOUCHERNO='" + rVoucherNo + "' and acno='" + acctNo + "'").getResultList();
                    if (balanList.size() > 0) {
                        Vector balVec = (Vector) balanList.get(0);
                        provInt = Double.parseDouble(balVec.get(0).toString());
                    }
                    balInt = tmpIntTds - provInt;
                    balInt = CbsUtil.round(balInt, 0);
                    if (balInt > 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer TD_Reconmid = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo)"
                                + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + balInt + ",'" + enteredBy + "','Y','Trf BalInt. to vch',0,8," + trsNo + "," + recNo + ",'System','I','" + orgnBrCode + "','" + destBrnCode + "'," + rVoucherNo + ")").executeUpdate();
                        if (TD_Reconmid <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }

                        Integer TD_InterestHistory = em.createNativeQuery("INSERT INTO td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                                + "VALUES('" + acctNo + "','" + tempbd + "'," + balInt + ",'" + rVoucherNo + "','" + tempbd + "','" + tempbd + "','" + optInterest + "')").executeUpdate();
                        if (TD_InterestHistory <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer Gl_Recon = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                + "VALUES('" + glheadInt + "','" + tempbd + "','" + renewFdDate + "'," + balInt + ",'" + enteredBy + "','Y','Bal Int. for vch',1,8," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                        if (Gl_Recon <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, balInt, 0, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in balance updation");
                        }

                        rs = ftsPostMgmtRepote.updateBalance("PO", glheadInt, 0, balInt, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }

                        if (!acctNo.equals(intToAcNo) && (balInt - tdsToBeDeducted > 0)) {

                            if (!ftsPostMgmtRepote.getCurrentBrnCode(intToAcNo).equalsIgnoreCase(orgnBrCode)) {
                                /**
                                 * Interbranch Transaction
                                 */
                                double tempBalInt = balInt - tdsToBeDeducted - clActTdsToBeDeducted;
                                recNo = ftsPostMgmtRepote.getRecNo();
                                String crDetails = "Trf BalInt. From AcNo " + acctNo;
                                rs = ftsRemoteMethods.cbsPostingSx(intToAcNo, 0, renewFdDate, tempBalInt, 0, 2, crDetails, 0.0f, "", "", "", 3, 0.0f,
                                        recNo, 4, ftsPostMgmtRepote.getCurrentBrnCode(intToAcNo), orgnBrCode, enteredBy, "System", trsNo, "", "");
                                if (rs.substring(0, 4).equalsIgnoreCase("true")) {
                                    recNo = ftsPostMgmtRepote.getRecNo();
                                    crDetails = "Trf BalInt. to acno " + intToAcNo;
                                    rs = ftsRemoteMethods.cbsPostingCx(acctNo, 1, renewFdDate, tempBalInt, 0, 2, crDetails, 0.0f, "", "", "", 3,
                                            0.0f, recNo, 4, orgnBrCode, orgnBrCode, enteredBy, "System", trsNo, "", "");
                                    if (!rs.substring(0, 4).equalsIgnoreCase("true")) {
                                        throw new ApplicationException(rs);
                                    }
                                    ftsPostMgmtRepote.updateBalance(acctNature, acctNo, 0, tempBalInt, "Y", "Y");
                                } else {
                                    throw new ApplicationException(rs);
                                }
                            } else if (ftsPostMgmtRepote.getCurrentBrnCode(intToAcNo).equalsIgnoreCase(orgnBrCode)) {
                                String intAccNature = ftsPostMgmtRepote.getAccountNature(intToAcNo);

                                recNo = ftsPostMgmtRepote.getRecNo();

                                double tempBalInt = balInt - tdsToBeDeducted - clActTdsToBeDeducted;
                                Integer tdReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,"
                                        + "Recno,authby,INTFLAG,org_brnid,dest_brnid,VoucherNo) values('" + acctNo + "','" + tempbd + "','" + renewFdDate + "',"
                                        + tempBalInt + ",'" + enteredBy + "','Y','Trf BalInt. to acno',1,2," + trsNo + "," + recNo + ",'System','','"
                                        + orgnBrCode + "','" + destBrnCode + "'," + rVoucherNo + ")").executeUpdate();
                                if (tdReconInsert <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                rs = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, 0, tempBalInt, "Y", "Y");
                                if (!rs.equalsIgnoreCase("True")) {
                                    throw new ApplicationException("Problem in account balance updation");
                                }
                                recNo = ftsPostMgmtRepote.getRecNo();
                                String details = "Trf BalInt. From AcNo " + acctNo;
                                String reconRs = ftsPostMgmtRepote.insertRecons(intAccNature, intToAcNo, 0, tempBalInt, tempbd, renewFdDate, 2, details,
                                        enteredBy, trsNo, "", recNo, "Y", "System", 0, 3, "", "", 0f, enteredBy, "A", 1, "", 0f, "", "",
                                        orgnBrCode, destnBrnCode, 0, "", "", "");
                                if (!reconRs.equalsIgnoreCase("True")) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                rs = ftsPostMgmtRepote.updateBalance(intAccNature, intToAcNo, tempBalInt, 0, "Y", "Y");
                                if (!rs.equalsIgnoreCase("True")) {
                                    throw new ApplicationException("Problem in balance updation");
                                }
                            }
                            ftsPostMgmtRepote.lastTxnDateUpdation(intToAcNo);
                        }
                    }
                    if (balInt < 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer TD_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid,Voucherno)"
                                + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + Math.abs(balInt) + ",'" + enteredBy + "','Y','Trf BalInt. to vch',1,8," + trsNo + "," + recNo + ",'System','I','" + orgnBrCode + "','" + destBrnCode + "'," + voucherno + ")").executeUpdate();
                        if (TD_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                + "VALUES('" + glheadInt + "','" + tempbd + "','" + renewFdDate + "'," + Math.abs(balInt) + ",'" + enteredBy + "','Y','Bal Int. for vch',0,8," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                        if (Gl_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, 0, balInt, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in balance updation");
                        }

                        rs = ftsPostMgmtRepote.updateBalance("PO", glheadInt, balInt, 0, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }
                    }
                }
                if (renewAc.equalsIgnoreCase("Existing")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer reconInsert = em.createNativeQuery("INSERT td_recon(acno,FDDt,Dt,ValueDt,Cramt,Voucherno,EnterBy,details,Ty,tranType,Recno,trsNo,authby,auth,org_brnid,dest_brnid)"
                            + "VALUES('" + acctNo + "','" + matdt + "','" + tempbd + "','" + renewFdDate + "'," + txtAmt + "," + maxVchNo + ",'" + enteredBy + "','VCH New Receipt',0,2," + recNo + "," + trsNo + ",'System','Y','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();
                    if (reconInsert <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                } else if (renewAc.equalsIgnoreCase("New")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer reconInsert = em.createNativeQuery("INSERT td_recon(acno,FDDt,Dt,ValueDt,Cramt,Voucherno,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                            + "VALUES('" + newAcNo + "','" + matdt + "','" + tempbd + "','" + renewFdDate + "'," + txtAmt + "," + maxVchNo + ",'" + enteredBy + "','Y','New Fd',0,2," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "')").executeUpdate();

                    if (reconInsert <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                }
                if (renewAc.equalsIgnoreCase("Existing")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer reconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,details,Ty,tranType,Recno,trsNo,auth,authby,org_brnid,dest_brnid,VoucherNo)"
                            + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + txtAmt + ",'" + enteredBy + "','VCH New Receipt Renewal Entry',1,2," + recNo + "," + trsNo + ",'Y','System','" + orgnBrCode + "','" + destBrnCode + "'," + maxVchNo + ")").executeUpdate();
                    if (reconInsert <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }

                } else if (renewAc.equalsIgnoreCase("New")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer reconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid,VoucherNo)"
                            + "VALUES('" + acctNo + "','" + tempbd + "','" + renewFdDate + "'," + txtAmt + ",'" + enteredBy + "','Y','Transfer to',1,2," + trsNo + "," + recNo + ",'System','" + orgnBrCode + "','" + destBrnCode + "'," + maxVchNo + ")").executeUpdate();
                    if (reconInsert <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                }

                tdBalance = (tdBalance + rAmt) - txtAmt;

                String startYear = ymd.format(dmy.parse(stYear));
                String endYr = ymd.format(dmy.parse(endYear));
                List controlList = em.createNativeQuery("SELECT ControlType FROM td_parameterinfo").getResultList();
                float tmpVSeqNo = 0f;
                if (controlList.size() > 0) {
                    Vector balVec1 = (Vector) controlList.get(0);
                    String controlType = balVec1.get(0).toString();

                    if (controlType.equalsIgnoreCase("T")) {
                        List seqlList = em.createNativeQuery("SELECT ifnull(Max(SeqNo),0) FROM td_vouchmst WHERE TD_MadeDt between '" + startYear
                                + "'  and '" + endYr + "' and substring(acno,3,2) = '" + acType + "'").getResultList();
                        if (seqlList.size() > 0) {
                            Vector balVec = (Vector) seqlList.get(0);
                            tmpVSeqNo = Float.parseFloat(balVec.get(0).toString());
                        }

                    } else if (controlType.equalsIgnoreCase("N")) {
                        List seqlvList = em.createNativeQuery("SELECT ifnull(Max(SeqNo),0) FROM td_vouchmst WHERE TD_MadeDt between '" + startYear
                                + "'  and '" + endYr + "' and substring(acno,3,2) in (Select acctCode from accounttypemaster where acctNature = '"
                                + acctNature + "')").getResultList();
                        if (seqlvList.size() > 0) {
                            Vector balVec = (Vector) seqlvList.get(0);
                            tmpVSeqNo = Float.parseFloat(balVec.get(0).toString());
                        }
                    }
                }

                String strperiod = "";
                if (renewAc.equalsIgnoreCase("Existing")) {
                    strperiod = periodYY + "Years" + periodMM + "Months" + periodDD + "Days";
                    Integer reconInsert = em.createNativeQuery("INSERT td_vouchmst(acno,Voucherno,Prinamt,fddt,matDt,roi,intopt,inttoacno,status,"
                            + "auth,Enterby,period,prevVoucherNo,ReceiptNo,years,months,days,td_madeDt,nextintpaydt,cumuprinamt,trantime,seqno,"
                            + "OFFlag,AutoRenew,authby) values('" + acctNo + "'," + maxVchNo + "," + txtAmt + ",'" + renewFdDate + "','" + renewMatDate
                            + "'," + roiNew + ",'" + optInterest + "','" + intToAcNo + "','A','Y','" + enteredBy + "','" + strperiod + "'," + voucherno
                            + "," + recieptNo + ",'" + periodYY + "','" + periodMM + "' ,'" + periodDD + "' ,'" + tempbd + "','" + renewFdDate + "',"
                            + txtAmt + ",now()," + tmpVSeqNo + ",'N','N','System')").executeUpdate();
                    if (reconInsert <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                    String rs = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, tdBalance, 0, "Y", "Y");
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Problem in TD balance updation");
                    }
                } else if (renewAc.equalsIgnoreCase("New")) {
                    Integer reconInsert = em.createNativeQuery("INSERT td_vouchmst(acno,Voucherno,Prinamt,fddt,matDt,roi,intopt,inttoacno,status,"
                            + "auth,Enterby,period,prevVoucherNo,ReceiptNo,years,months,days,td_madeDt,nextintpaydt,cumuprinamt,trantime,seqno,OFFlag,"
                            + "AutoRenew,authby) values('" + newAcNo + "'," + maxVchNo + "," + txtAmt + ",'" + renewFdDate + "','" + renewMatDate
                            + "'," + roiNew + ",'" + optInterest + "','" + intToAcNo + "','A','Y','" + enteredBy + "','" + strperiod + "'," + voucherno
                            + "," + recieptNo + ",'" + periodYY + "','" + periodMM + "' ,'" + periodDD + "' ,'" + tempbd + "','" + renewFdDate + "',"
                            + txtAmt + ",now()," + tmpVSeqNo + ",'N','N','System')").executeUpdate();

                    if (reconInsert <= 0) {
                        throw new ApplicationException("Data Not Saved");
                    }
                    //please check it balance will update from tdBalance ot txtAmt 
                    String rs = ftsPostMgmtRepote.updateBalance(acctNature, newAcNo, tdBalance, 0, "Y", "Y");
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Problem in TD balance updation");
                    }
                }
            }
            if (!renewAc.equalsIgnoreCase("New")) {
                newAcNo = acctNo;
            }
            List cuIdList = em.createNativeQuery("select custid from customerid where acno = '" + acctNo + " '").getResultList();
            Vector cuIdLst = (Vector) cuIdList.get(0);
            String custId = cuIdLst.get(0).toString();

            String docMsg = openingFacadeRemote.getCustAcTdsDocDtl(custId, "", "C");
            if (docMsg.equalsIgnoreCase("true")) {
                List minorList = em.createNativeQuery("select guardiancode from  cbs_cust_minorinfo where (guardiancode is not null and guardiancode<> '') "
                        + " and CustomerId = '" + custId + "'").getResultList();

                String docDetailCustId = custId;
                if (!minorList.isEmpty()) {
                    Vector mnrVect = (Vector) minorList.get(0);
                    docDetailCustId = mnrVect.get(0).toString();
                }
                List cuList = em.createNativeQuery("select distinct doc_details,fyear,uniqueIdentificationNo from tds_docdetail where customerid = '" + docDetailCustId + " ' ").getResultList();
                Vector cuLst = (Vector) cuList.get(0);
                String docDtl = cuLst.get(0).toString();
                String fYear = cuLst.get(1).toString();
                String uin = cuLst.get(2).toString();

                List secList = em.createNativeQuery("select coalesce(max(seqno),0)+1 from tds_docdetail where customerid = '" + docDetailCustId + " '").getResultList();
                Vector secLst = (Vector) secList.get(0);
                String secListed = secLst.get(0).toString();
                int secnum = Integer.parseInt(secListed);

                Query insertQuery = em.createNativeQuery("insert into tds_docdetail(customerid,acno,seqNo,submission_date,"
                        + "fyear,receiptNo,doc_details,docFlag,orgBrnid,tranTime,enterBy,auth,uniqueIdentificationNo,authby)"
                        + "values ('" + docDetailCustId + "','" + newAcNo + "'," + secnum + ",now()," + fYear + ",'" + maxVchNo + "','" + docDtl + "','Y','"
                        + orgnBrCode + "',now(),'" + enteredBy + "','Y','" + uin + "','System')");
                int var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Inserted");
                }
                int resultUp = em.createNativeQuery("update td_vouchmst set tdsflag = 'N' where acno = '" + newAcNo + "' and voucherno = " + maxVchNo + " and ReceiptNo = " + recieptNo).executeUpdate();
                if (resultUp <= 0) {
                    throw new ApplicationException("Problem in Receipt TDS Details updation");
                }
            }

//            /*   Replace acno with rAcno by Nishant Kansal on Date 08/11/2011  */
//            Integer tdRenewalAuthNew = em.createNativeQuery("insert into td_renewal_auth(acno,voucherno,auth,batchno,trantime,enterby, balInterest, matAmt)"
//                    + "VALUES('" + newAcNo + "'," + maxVchNo + ",'N'," + trsNo + ",now(),'" + enteredBy + "',0," + renewMatAmt + " )").executeUpdate();
//            if (tdRenewalAuthNew <= 0) {
//                throw new ApplicationException("Problem in inserting Data.");
//            }
//
//            Integer tdRenewalAuthOld = em.createNativeQuery("insert into td_renewal_auth(acno,voucherno,auth,batchno,trantime,enterby, balInterest, matAmt)"
//                    + "VALUES('" + acctNo + "'," + tmpVchNo + ",'N'," + trsNo + ",now(),'" + enteredBy + "'," + balInt + ", 0)").executeUpdate();
//            if (tdRenewalAuthOld <= 0) {
//                throw new ApplicationException("Problem in inserting Data.");
//            }
            if (autoManualFlag.equalsIgnoreCase("M")) {
                Query entryList = em.createNativeQuery("UPDATE td_renewal_auth SET Auth='Y',AuthBy='" + enteredBy + "',batchno=" + trsNo + ",auto_manual='M' where "
                        + " rtNoHide=" + tmpVchNo + " and acno='" + acctNo + "' and ReceiptNo = " + oldRecieptNo + "");
                Integer var = entryList.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in updation");
                }
            } else if (autoManualFlag.equalsIgnoreCase("A")) {
                String query = "INSERT INTO td_renewal_auth(ACNO,renewedTdDt,renewedMatDt,TdsToBeDed,year,month,days,rtNoHide,IntOpt,tdDayMth,tdDayCum,"
                        + "renewalAmount,ReceiptNo,series,receiptnew,EnterBy,rAmt,balint,renewalAcc,renewedMatAmt,matpre,stYear,endYear,ROI,TDSDeductedLast,"
                        + "totalIntRateCal,TdsDed,ClActTdsToBeDeducted,ClActTdsDeducted,ClActInt,appRoiFrom,Auth,TranTime,auto_manual) "
                        + " VALUES('" + acctNo + "','" + renewFdDate + "','" + renewMatDate + "'," + tdsToBeDeducted + "," + periodYY + "," + periodMM + "," + periodDD + "," + tmpVchNo + ","
                        + "'" + optInterest + "'," + gFDDayMnth + "," + gFDDayYrs + "," + txtAmt + "," + oldRecieptNo + ",'" + cmbSeries + "','" + recptCmb + "',"
                        + "'" + enteredBy + "'," + rAmt + "," + balInt + ",'" + renewAc + "'," + renewMatAmt + "," + matPre + ",'" + stYear + "','" + endYear + "',"
                        + "" + roiNew + "," + lastFinYearTds + "," + tmpIntTds + "," + tdsDeducted + "," + clActTdsToBeDeducted + ",0.0,0.0,"
                        + "'','Y',now(),'A')";
                Query insertQuery = em.createNativeQuery(query);
                int varQ1 = insertQuery.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
            }
            String result = "";
            if (lien.equalsIgnoreCase("Y") && ftsPostMgmtRepote.getCodeForReportName("LIEN-FD-RENEW") == 1) {
                result = fdLienSecurityRenewal(newAcNo, voucherno, oldRecieptNo, newAcNo, maxVchNo, recieptNo, enteredBy, tempbd, orgnBrCode, intToAcNo);
                if (!result.equals("True")) {
                    throw new ApplicationException("Problem in security renewal");
                }
            }
            if (renewAc.equalsIgnoreCase("New")) {
                result = "True:" + trsNo + ":" + balInt + ":" + recieptNo + ":" + maxVchNo + ":" + newAcNo;
            } else {
                result = "True:" + trsNo + ":" + balInt + ":" + recieptNo + ":" + maxVchNo;
            }
            //ut.commit();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String tdProvisionRenewal(String acno, String renewFDDt, String renewMatDT, double tdsDeduct, int periodYY, int periodMM,
            int periodDD, float tmpVchNo, String optInterest, int gFDDayMnth, int gFDDayYrs, String renewAc, String recptCmb, String cmbSeries,
            float tmpReciept, String enteredBy, String rVoucherNo, String rAcno, double rAmt, String orgBrCode, double tmpBalInt,
            double txtAmt, double matPre, String stYear, String endYear, float roiNew, double renewMatAmt, String autoManualFlag) throws ApplicationException {
        // UserTransaction ut = context.getUserTransaction();
        try {
            // ut.begin();

            String destBrnCode = ftsPostMgmtRepote.getCurrentBrnCode(acno);
            String accType = ftsPostMgmtRepote.getAccountCode(acno);
            String acctNature = ftsPostMgmtRepote.getAccountNature(acno);
            float oldRecieptNo = tmpReciept;

            if (autoManualFlag.equalsIgnoreCase("M")) {
                List authChkList = em.createNativeQuery("Select acno from td_renewal_auth where rtNoHide=" + tmpVchNo + " "
                        + " and acno='" + acno + "' and ReceiptNo = " + oldRecieptNo + " and Auth='Y'").getResultList();
                if (!authChkList.isEmpty()) {
                    throw new ApplicationException("Receipt Already Authorized.");
                }
            }

            List tempBdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG='N' and brncode = '" + orgBrCode + "'").getResultList();
            if (tempBdList.isEmpty()) {
                throw new ApplicationException("Data does not exist in bank days");
            }
            Vector tempBdVec = (Vector) tempBdList.get(0);
            String tempbd = tempBdVec.get(0).toString();

//            List acNoList = em.createNativeQuery("SELECT * FROM td_vouchmst where acno='" + acno + "' and voucherno=" + tmpVchNo + " and status='A'").getResultList();
//            if (acNoList.size() <= 0) {
//                throw new ApplicationException("Receipt already closed");
//            }
            long diff = CbsUtil.dayDiff(ymd.parse(renewFDDt), ymd.parse(renewMatDT));

            if (optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("S")) {
                if (diff < gFDDayMnth) {
                    throw new ApplicationException("Please note duration can not be less than");
                }
            }
            if (optInterest.equalsIgnoreCase("C")) {
                if (diff < gFDDayYrs) {
                    throw new ApplicationException("Please note duration cannot be less than");
                }
            }
            String tdsGlHead = "";
            List gltdsList = em.createNativeQuery("SELECT  DISTINCT TDS_GLhead FROM tdsslab where LastUpdateDt = (select max(LastUpdateDt) from tdsslab)").getResultList();
            if (gltdsList.size() > 0) {
                Vector gltdsVec = (Vector) gltdsList.get(0);
                tdsGlHead = gltdsVec.get(0).toString();
                tdsGlHead = orgBrCode + tdsGlHead;
            }
            String newAcNo = "";
            if (renewAc.equalsIgnoreCase("New")) {
                newAcNo = tdCalRemote.cbsTdRenewalNewAcct(acno, enteredBy, orgBrCode, accType);
            }
            String tdReceiptSeq = "";
            if (recptCmb.equalsIgnoreCase("New")) {
                List sqList = em.createNativeQuery("SELECT COALESCE(receiptno_seq,'A') FROM td_parameterinfo").getResultList();
                if (sqList.size() > 0) {
                    Vector sqdsVec = (Vector) sqList.get(0);
                    tdReceiptSeq = sqdsVec.get(0).toString();
                }

                if (tdReceiptSeq.equalsIgnoreCase("C")) {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue "
                            + "Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' and brncode = '" + orgBrCode + "') And "
                            + "Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' And brncode = '" + orgBrCode + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. does not exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    tmpReciept = Float.parseFloat(rcnoVec.get(0).toString());

                    Integer entryList = em.createNativeQuery("UPDATE td_receiptissue SET status='U',LastUpdateBy='" + enteredBy + "', LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + tmpReciept + " and status = 'F' and "
                            + "scheme ='" + acctNature + "' and brncode = '" + orgBrCode + "') as temp) and scheme ='" + acctNature + "' and status = 'F' and series='"
                            + cmbSeries + "' And brncode = '" + orgBrCode + "'").executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Data Not Saved, Please fill proper series");
                    }
                } else if (tdReceiptSeq.equalsIgnoreCase("N")) {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue "
                            + "Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' and brncode = '" + orgBrCode + "') "
                            + "And Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' and brncode = '" + orgBrCode
                            + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    tmpReciept = Float.parseFloat(rcnoVec.get(0).toString());

                    Integer entryList = em.createNativeQuery("UPDATE td_receiptissue set status='U',LastUpdateBy='" + enteredBy + "', LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + tmpReciept + " and status = 'F' "
                            + "and scheme ='" + acctNature + "' and brncode = '" + orgBrCode + "') as temp) and scheme ='" + acctNature + "' and status = 'F' "
                            + "and series='" + cmbSeries + "' And brncode = '" + orgBrCode + "'").executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                } else if (tdReceiptSeq.equalsIgnoreCase("T")) {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue"
                            + " Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + accType + "' And brncode = '" + orgBrCode + "') "
                            + "And Scheme ='" + accType + "' And Status = 'F' and series='" + cmbSeries + "' And brncode = '" + orgBrCode
                            + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    tmpReciept = Float.parseFloat(rcnoVec.get(0).toString());

                    Integer entryList = em.createNativeQuery("UPDATE td_receiptissue set status='U',LastUpdateBy='" + enteredBy + "',LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + tmpReciept + " and status = 'F' and"
                            + " scheme = '" + accType + "' And brncode = '" + orgBrCode + "') as temp) and scheme ='" + accType + "' and status = 'F' and series='"
                            + cmbSeries + "' And brncode = '" + orgBrCode + "'").executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                } else {
                    List rcnoList = em.createNativeQuery("Select Min(ReceiptNo) From td_receiptissue Where Sno In (Select min(sno) From td_receiptissue "
                            + "Where Status = 'F' and series='" + cmbSeries + "' And Scheme = '" + acctNature + "' And brncode = '" + orgBrCode + "') "
                            + "And Scheme ='" + acctNature + "' And Status = 'F' and series='" + cmbSeries + "' And brncode = '" + orgBrCode
                            + "'").getResultList();
                    if (rcnoList.isEmpty()) {
                        throw new ApplicationException("Receipt no. Does Not Exist");
                    }
                    Vector rcnoVec = (Vector) rcnoList.get(0);
                    tmpReciept = Float.parseFloat(rcnoVec.get(0).toString());

                    Query updatelist = em.createNativeQuery("UPDATE td_receiptissue set status='U',LastUpdateBy='" + enteredBy + "',LastUpdateDt='"
                            + tempbd + "' where sno in (SELECT id from (select min(sno) as id from td_receiptissue where receiptno= " + tmpReciept + " and status = 'F' "
                            + "and scheme ='" + acctNature + "' And brncode = '" + orgBrCode + "') as temp) and scheme ='" + acctNature + "' and status = 'F' "
                            + "and series='" + cmbSeries + "' And brncode = '" + orgBrCode + "'");
                    Integer stockInfo = updatelist.executeUpdate();
                    if (stockInfo <= 0) {
                        throw new ApplicationException("Data Not Saved,Please fill proper series");
                    }
                }
            }

            List finlamtList = new ArrayList();
            if (autoManualFlag.equalsIgnoreCase("A")) {
                finlamtList = em.createNativeQuery("SELECT DISTINCT acno,prinamt,voucherno,FDDt,status,matdt,IntOpt,roi,cldt,finalAmt,period,"
                        + "IntToAcNo,OFFlag,OFAcno,lien from td_vouchmst where status='A' and voucherNo='" + rVoucherNo + "' and acno='" + acno
                        + "' UNION SELECT DISTINCT vm.acno,vm.prinamt,vm.voucherno,vm.FDDt,vm.status,vm.matdt,vm.IntOpt,vm.roi,vm.cldt,"
                        + "vm.finalAmt,vm.period,vm.IntToAcNo,vm.OFFlag,vm.OFAcno,lien from td_vouchmst vm where OFFlag='Y' and vm.status='C' "
                        + "and vm.voucherNo='" + rVoucherNo + "' and vm.acno='" + acno + "'").getResultList();
            } else if (autoManualFlag.equalsIgnoreCase("M")) {
                finlamtList = em.createNativeQuery("SELECT DISTINCT acno,prinamt,voucherno,FDDt,status,matdt,IntOpt,roi,cldt,finalAmt,period,"
                        + "IntToAcNo,OFFlag,OFAcno,lien from td_vouchmst where status='C' and voucherNo='" + rVoucherNo + "' and acno='" + acno
                        + "' UNION SELECT DISTINCT vm.acno,vm.prinamt,vm.voucherno,vm.FDDt,vm.status,vm.matdt,vm.IntOpt,vm.roi,vm.cldt,"
                        + "vm.finalAmt,vm.period,vm.IntToAcNo,vm.OFFlag,vm.OFAcno,lien from td_vouchmst vm where OFFlag='Y' and vm.status='C' "
                        + "and vm.voucherNo='" + rVoucherNo + "' and vm.acno='" + acno + "'").getResultList();
            }

            if (finlamtList.isEmpty()) {
                throw new ApplicationException("Data does not exists");
            }
            Vector custvoucherNo = (Vector) finlamtList.get(0);
            String acctNo = "";
            if (custvoucherNo.get(0) != null) {
                acctNo = custvoucherNo.get(0).toString();
            }
            String acType = ftsPostMgmtRepote.getAccountCode(acctNo);
            double prinamt = 0;
            if (custvoucherNo.get(1) != null) {
                prinamt = Double.parseDouble(custvoucherNo.get(1).toString());
            }
            float vchNo = 0;
            if (custvoucherNo.get(2) != null) {
                vchNo = Float.parseFloat(custvoucherNo.get(2).toString());
            }
            String fddt = "";
            if (custvoucherNo.get(3) != null) {
                fddt = custvoucherNo.get(3).toString();
            }
            String matdt = "";
            if (custvoucherNo.get(5) != null) {
                matdt = custvoucherNo.get(5).toString();
            }
            double finalAmt = 0;
            if (custvoucherNo.get(9) != null) {
                finalAmt = Double.parseDouble(custvoucherNo.get(9).toString());
            }
            String intToAcNo = "";
            if (custvoucherNo.get(11) != null) {
                intToAcNo = custvoucherNo.get(11).toString();
            }
            String ofFlag = "";
            if (custvoucherNo.get(12) != null) {
                ofFlag = custvoucherNo.get(12).toString();
            }
            String ofAcNo = "";
            if (custvoucherNo.get(13) != null) {
                ofAcNo = custvoucherNo.get(13).toString();
            }
            String lien = "";
            if (custvoucherNo.get(14) != null) {
                lien = custvoucherNo.get(14).toString();
            }
            if (intToAcNo.length() < 12 && (optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Q"))) {
                intToAcNo = rAcno;
            }

            if (lien.equalsIgnoreCase("Y")) {
                throw new ApplicationException("This Receipt is Lien Marked");
            }
            float trsNo = 0;
            float recNo = 0;
            float maxVchNo = 0;
            double balInt = 0;
            if (ofFlag.equalsIgnoreCase("Y")) {
                trsNo = ftsPostMgmtRepote.getTrsNo();
                recNo = ftsPostMgmtRepote.getRecNo();
                Integer ofReconInsert = em.createNativeQuery("INSERT INTO of_recon(acno,dramt,dt,ValueDt,ty,trantype,enterby,tdacno,voucherno,auth,trsno,"
                        + "recno,authby,org_brnid,dest_brnid) VALUES('" + ofAcNo + "'," + finalAmt + ",'" + tempbd + "','" + renewFDDt + "',1,2,'"
                        + enteredBy + "','" + acctNo + "'," + vchNo + ",'Y'," + trsNo + "," + recNo + ",'System','" + orgBrCode + "','"
                        + destBrnCode + "')").executeUpdate();
                if (ofReconInsert <= 0) {
                    throw new ApplicationException("Problem in inserting Data.");
                }
                Integer tdReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,"
                        + "org_brnid,dest_brnid) VALUES('" + acctNo + "','" + tempbd + "','" + renewFDDt + "'," + rAmt + ",'" + enteredBy
                        + "','Y','Transfer to',0,2," + trsNo + "," + recNo + ",'System','" + orgBrCode + "','" + destBrnCode + "')").executeUpdate();
                if (tdReconInsert <= 0) {
                    throw new ApplicationException("Problem in inserting Data.");
                }
            } else {
                Query entryList = em.createNativeQuery("UPDATE td_vouchmst SET OFFlag='N',status='C',ClDt='" + tempbd + "',penalty=0 where voucherno="
                        + vchNo + " and acno='" + acctNo + "'");
                Integer var = entryList.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Problem in updating Data.");
                }

                List mstList = em.createNativeQuery("SELECT ifnull(max(voucherNo),0) FROM td_vouchmst WHERE substring(acno,3,2)='" + acType + "'").getResultList();
                if (mstList.isEmpty()) {
                    throw new ApplicationException("Problem in getting voucher number.");
                }
                Vector rcnoVec = (Vector) mstList.get(0);
                maxVchNo = Float.parseFloat(rcnoVec.get(0).toString()) + 1;

                List glheadList = em.createNativeQuery("SELECT glheadprov,glheadInt FROM accounttypemaster WHERE acctcode= '" + acType + "'").getResultList();
                if (glheadList.isEmpty()) {
                    throw new ApplicationException("Problem in getting data from account type master.");
                }

                Vector glVec = (Vector) glheadList.get(0);
                if (glVec.get(1) == null) {
                    throw new ApplicationException("Please enter GL Head");
                }
                String glheadInt = orgBrCode + glVec.get(1).toString() + "01";
                String glHeadProv = "";
                if (glVec.get(0) == null) {
                    throw new ApplicationException("Please enter GL Provision Head");
                }
                glHeadProv = orgBrCode + glVec.get(0).toString() + "01";

                trsNo = ftsPostMgmtRepote.getTrsNo();
                if (tdsDeduct > 0) {
                    Integer tdsHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt) VALUES('" + acno
                            + "'," + tmpVchNo + "," + tdsDeduct + ",'" + tempbd + "','" + tempbd + "','" + tempbd + "','" + optInterest + "')").executeUpdate();
                    if (tdsHistory <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    recNo = ftsPostMgmtRepote.getRecNo();

                    Integer tdRecon = em.createNativeQuery("INSERT INTO td_recon (acno,voucherno,drAmt,fdDt,dt,ValueDt,enterBy,TranType,Ty,details,"
                            + "intflag,trsno,recno,auth,authby,org_brnid,dest_brnid) VALUES('" + acno + "'," + tmpVchNo + "," + tdsDeduct + ",'"
                            + tempbd + "','" + tempbd + "','" + renewFDDt + "','" + enteredBy + "',2,1,'TDS Deducted For Acno','T',"
                            + trsNo + "," + recNo + ",'Y','System','" + orgBrCode + "','" + destBrnCode + "')").executeUpdate();
                    if (tdRecon <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    Integer glRecon = em.createNativeQuery("INSERT INTO gl_recon (acno,cramt,enterBy,TranType,Ty,dt,ValueDt,details,trsno,recno,auth,"
                            + "authby,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + tdsGlHead + "'," + tdsDeduct + ",'" + enteredBy + "',2,0,'"
                            + tempbd + "','" + renewFDDt + "','TDS Deducted For Acno'," + trsNo + "," + recNo + ",'Y','System','" + orgBrCode
                            + "','" + destBrnCode + "','','')").executeUpdate();
                    if (glRecon <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    String msg = ftsPostMgmtRepote.updateBalance("PO", tdsGlHead, tdsDeduct, 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                }

                recNo = ftsPostMgmtRepote.getRecNo();
                balInt = CbsUtil.round(tmpBalInt, 0);
                if (balInt != 0) {
                    Integer TD_InterestHistory = em.createNativeQuery("INSERT INTO td_interesthistory(acno,dt,interest,voucherno,ToDt,FromDt,intOPt)"
                            + "VALUES('" + acctNo + "','" + tempbd + "'," + balInt + ",'" + rVoucherNo + "','" + matdt + "','" + fddt + "','" + optInterest + "')").executeUpdate();
                    if (TD_InterestHistory <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                }
                List TdIntHistList = em.createNativeQuery("SELECT ifnull(SUM(Interest),0) FROM td_interesthistory where voucherno=" + vchNo
                        + " and acno='" + acctNo + "'").getResultList();
                double intPaid = 0;
                if (TdIntHistList.size() > 0) {
                    Vector balVec = (Vector) TdIntHistList.get(0);
                    intPaid = Double.parseDouble(balVec.get(0).toString());
                }
                List tdsHistList = em.createNativeQuery("SELECT ifnull(SUM(tds),0) FROM tdshistory where voucherno=" + vchNo + " and acno='" + acctNo
                        + "' and intopt='" + optInterest + "'").getResultList();
                double totTdsAmt = 0;
                if (tdsHistList.size() > 0) {
                    Vector balVec = (Vector) tdsHistList.get(0);
                    totTdsAmt = Double.parseDouble(balVec.get(0).toString());
                }
                intPaid = CbsUtil.round((intPaid - totTdsAmt), 0);
                double tmpMatAmt = CbsUtil.round((intPaid + prinamt), 0);

                Integer TD_Recon = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,closeflag,"
                        + "authby,org_brnid,dest_brnid,voucherno) VALUES('" + acctNo + "','" + tempbd + "','" + renewFDDt + "'," + tmpMatAmt + ",'" + enteredBy
                        + "','Y','VCH: Trf. PrinAmt and IntPaid to vch',0,2," + trsNo + "," + recNo + ",'C','System','" + orgBrCode + "','" + destBrnCode + "'," + rVoucherNo + ")").executeUpdate();
                if (TD_Recon <= 0) {
                    throw new ApplicationException("Problem in inserting Data.");
                }
                recNo = ftsPostMgmtRepote.getRecNo();
                String details = "Prov Int Paid on Acno: " + acctNo + " / " + vchNo + " / " + optInterest;
                TD_Recon = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,intflag,"
                        + "authby,org_brnid,dest_brnid,voucherno) VALUES('" + acctNo + "','" + tempbd + "','" + renewFDDt + "'," + Math.abs(balInt) + ",'" + enteredBy
                        + "','Y','" + details + "',0,8," + trsNo + "," + recNo + ",'I','System','" + orgBrCode + "','" + destBrnCode + "'," + rVoucherNo + ")").executeUpdate();
                if (TD_Recon <= 0) {
                    throw new ApplicationException("Problem in inserting Data.");
                }
                recNo = ftsPostMgmtRepote.getRecNo();
                details = "Bal Prov Int Paid for Acno: " + acctNo + " / " + vchNo + " / " + optInterest;
                String checkListA = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 1, Math.abs(balInt), tempbd, renewFDDt, enteredBy, orgBrCode, destBrnCode, 0,
                        details, trsNo, recNo, 0, "FROMFD", "Y", "System", "A", 3, "", "", "", vchNo, "", "", "", "", 0f, "N", "", "", "");
                if (!(checkListA.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(checkListA);
                }
                if (balInt > 0) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer gl_recon = em.createNativeQuery("INSERT INTO gl_recon(acno,Dt,ValueDt,cramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,"
                            + "authby,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + glHeadProv + "','" + tempbd + "','" + renewFDDt + "',"
                            + Math.abs(balInt) + ",'" + enteredBy + "','VCH: Trf. Bal Int to vch" + maxVchNo + "',0,8," + trsNo + "," + recNo
                            + ",'Y','System','" + orgBrCode + "','" + destBrnCode + "','','')").executeUpdate();
                    if (gl_recon <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    String msg = ftsPostMgmtRepote.updateBalance("PO", glHeadProv, Math.abs(balInt), 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }

                    recNo = ftsPostMgmtRepote.getRecNo();
                    gl_recon = em.createNativeQuery("INSERT INTO gl_recon(acno,Dt,ValueDt,dramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,authby,"
                            + "org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + glheadInt + "','" + tempbd + "','" + renewFDDt + "',"
                            + Math.abs(balInt) + ",'" + enteredBy + "','VCH: Trf. Bal Int to vch" + maxVchNo + "',1,8," + trsNo + "," + recNo
                            + ",'Y','System','" + orgBrCode + "','" + destBrnCode + "','','')").executeUpdate();
                    if (gl_recon <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    msg = ftsPostMgmtRepote.updateBalance("PO", glheadInt, 0, Math.abs(balInt), "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                }
                if (balInt < 0) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer gl_recon = em.createNativeQuery("INSERT INTO gl_recon(acno,Dt,ValueDt,dramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,"
                            + "authby,org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + glHeadProv + "','" + tempbd + "','" + renewFDDt + "',"
                            + Math.abs(balInt) + ",'" + enteredBy + "','VCH: Trf. Bal Int to vch" + maxVchNo + "',1,8," + trsNo + "," + recNo
                            + ",'Y','System','" + orgBrCode + "','" + destBrnCode + "','','')").executeUpdate();
                    if (gl_recon <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    String msg = ftsPostMgmtRepote.updateBalance("PO", glHeadProv, 0, Math.abs(balInt), "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }

                    recNo = ftsPostMgmtRepote.getRecNo();
                    gl_recon = em.createNativeQuery("INSERT INTO gl_recon(acno,Dt,ValueDt,cramt,EnterBy,details,Ty,tranType,trsNo,Recno,auth,authby,"
                            + "org_brnid,dest_brnid,adviceNo,adviceBrnCode) VALUES('" + glheadInt + "','" + tempbd + "','" + renewFDDt + "',"
                            + Math.abs(balInt) + ",'" + enteredBy + "','VCH: Trf. Bal Int to vch" + maxVchNo + "',0,8," + trsNo + "," + recNo
                            + ",'Y','System','" + orgBrCode + "','" + destBrnCode + "','','')").executeUpdate();
                    if (gl_recon <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    msg = ftsPostMgmtRepote.updateBalance("PO", glheadInt, Math.abs(balInt), 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                }

                if (renewAc.equalsIgnoreCase("Existing")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer Gl_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,details,Ty,tranType,Recno,trsNo,auth,"
                            + "authby,org_brnid,dest_brnid,VoucherNo) VALUES('" + acctNo + "','" + tempbd + "','" + renewFDDt + "'," + txtAmt + ",'" + enteredBy
                            + "','VCH New Receipt Renewal Entry',1,2," + recNo + "," + trsNo + ",'Y','System','" + orgBrCode + "','" + destBrnCode + "'," + maxVchNo + ")").executeUpdate();
                    if (Gl_ReconInsert <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }

                } else if (renewAc.equalsIgnoreCase("New")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer Gl_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,"
                            + "authby,org_brnid,dest_brnid,VoucherNo) VALUES('" + acctNo + "','" + tempbd + "','" + renewFDDt + "'," + txtAmt + ",'" + enteredBy
                            + "','Y','Transfer to',1,2," + trsNo + "," + recNo + ",'System','" + orgBrCode + "','" + destBrnCode + "'," + maxVchNo + ")").executeUpdate();
                    if (Gl_ReconInsert <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                }
                double tdBal = rAmt - txtAmt;

                if (renewAc.equalsIgnoreCase("Existing")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer Gl_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,FDDt,Dt,ValueDt,Cramt,Voucherno,EnterBy,details,Ty,tranType,Recno,"
                            + "trsNo,authby,auth,org_brnid,dest_brnid) VALUES('" + acctNo + "','" + matdt + "','" + tempbd + "','" + renewFDDt + "',"
                            + txtAmt + "," + maxVchNo + ",'" + enteredBy + "','VCH New Receipt',0,2," + recNo + "," + trsNo + ",'System','Y','"
                            + orgBrCode + "','" + destBrnCode + "')").executeUpdate();
                    if (Gl_ReconInsert <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                } else if (renewAc.equalsIgnoreCase("New")) {
                    recNo = ftsPostMgmtRepote.getRecNo();
                    Integer Gl_ReconInsert = em.createNativeQuery("INSERT td_recon(acno,FDDt,Dt,ValueDt,Cramt,Voucherno,EnterBy,auth,details,Ty,tranType,"
                            + "trsNo,Recno,authby,org_brnid,dest_brnid) VALUES('" + newAcNo + "','" + matdt + "','" + tempbd + "','" + renewFDDt + "',"
                            + txtAmt + "," + maxVchNo + ",'" + enteredBy + "','Y','New Fd',0,2," + trsNo + "," + recNo + ",'System','" + orgBrCode + "','"
                            + destBrnCode + "')").executeUpdate();
                    if (Gl_ReconInsert <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                }
                String startYear = ymd.format(dmy.parse(stYear));
                String endYr = ymd.format(dmy.parse(endYear));

                float tmpVSeqNo = 0;
                List controlList = em.createNativeQuery("SELECT ControlType FROM td_parameterinfo").getResultList();
                if (controlList.size() > 0) {
                    Vector balVec = (Vector) controlList.get(0);
                    String controlType = balVec.get(0).toString();
                    if (controlType.equalsIgnoreCase("T")) {
                        List seqlList = em.createNativeQuery("SELECT ifnull(Max(SeqNo),0) FROM td_vouchmst WHERE TD_MadeDt between '" + startYear
                                + "'  and '" + endYr + "' and substring(acno,3,2) = '" + accType + "'").getResultList();
                        if (seqlList.size() > 0) {
                            Vector seqVec = (Vector) seqlList.get(0);
                            tmpVSeqNo = Float.parseFloat(seqVec.get(0).toString());
                        }
                    } else if (controlType.equalsIgnoreCase("N")) {
                        List seqlvList = em.createNativeQuery("SELECT ifnull(Max(SeqNo),0) FROM td_vouchmst WHERE TD_MadeDt between '" + startYear
                                + "'  and '" + endYr + "' and substring(acno,3,2) in (Select acctCode from accounttypemaster where acctNature = '"
                                + acctNature + "')").getResultList();
                        if (seqlvList.size() > 0) {
                            Vector seqVec = (Vector) seqlvList.get(0);
                            tmpVSeqNo = Float.parseFloat(seqVec.get(0).toString());
                        }
                    }
                }
                String strPd = periodYY + "Years" + periodMM + "Months" + periodDD + "Days";
                if (renewAc.equalsIgnoreCase("Existing")) {
                    Integer Gl_ReconInsert = em.createNativeQuery("INSERT td_vouchmst(acno,Voucherno,Prinamt,fddt,matDt,roi,intopt,inttoacno,status,auth,Enterby,period,prevVoucherNo,ReceiptNo,years,months,days,td_madeDt,nextintpaydt,cumuprinamt,trantime,seqno,OFFlag,AutoRenew,authby)"
                            + "VALUES('" + acctNo + "'," + maxVchNo + "," + txtAmt + ",'" + renewFDDt + "','" + renewMatDT + "'," + roiNew + ",'" + optInterest + "','" + intToAcNo + "','A','Y','" + enteredBy + "','" + strPd + "'," + vchNo + "," + tmpReciept + ",'" + periodYY + "','" + periodMM + "' ,'" + periodDD + "' ,'" + tempbd + "','" + renewFDDt + "'," + txtAmt + ",now()," + tmpVSeqNo + ",'N','N','System')").executeUpdate();
                    if (Gl_ReconInsert <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    String msg = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, tdBal, 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                } else if (renewAc.equalsIgnoreCase("New")) {
                    Integer Gl_ReconInsert = em.createNativeQuery("INSERT td_vouchmst(acno,Voucherno,Prinamt,fddt,matDt,roi,intopt,inttoacno,status,auth,Enterby,period,prevVoucherNo,ReceiptNo,years,months,days,td_madeDt,nextintpaydt,cumuprinamt,trantime,seqno,OFFlag,AutoRenew,authby)"
                            + "VALUES('" + newAcNo + "'," + maxVchNo + "," + txtAmt + ",'" + renewFDDt + "','" + renewMatDT + "'," + roiNew + ",'" + optInterest + "','" + intToAcNo + "','A','Y','" + enteredBy + "','" + strPd + "'," + vchNo + "," + tmpReciept + ",'" + periodYY + "','" + periodMM + "' ,'" + periodDD + "' ,'" + tempbd + "','" + renewFDDt + "'," + txtAmt + ",now()," + tmpVSeqNo + ",'N','N','System')").executeUpdate();
                    if (Gl_ReconInsert <= 0) {
                        throw new ApplicationException("Problem in inserting Data.");
                    }
                    String msg = ftsPostMgmtRepote.updateBalance(acctNature, acctNo, tdBal, 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                }
            }
            if (renewAc.equalsIgnoreCase("New")) {
                rAcno = newAcNo;
            }
            Query entryList = em.createNativeQuery("UPDATE td_renewal_auth SET Auth='Y',AuthBy='" + enteredBy + "',batchno=" + trsNo + ",auto_manual='M' where "
                    + " rtNoHide=" + tmpVchNo + " and acno='" + acctNo + "' and ReceiptNo = " + oldRecieptNo + "");
            Integer var = entryList.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Problem in updation");
            }
            String result = "";
            if (renewAc.equalsIgnoreCase("New")) {
                result = "True:" + trsNo + ":" + balInt + ":" + tmpReciept + ":" + maxVchNo + ":" + newAcNo;
            } else {
                result = "True:" + trsNo + ":" + balInt + ":" + tmpReciept + ":" + maxVchNo;
            }
            // ut.commit();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void closeActTdsPosting(List intCloseList, String frmDT, String toDT, float trsNumber, float recNo,
            String tempBd, float rtNumber, double calculatedTds, String acno, String intOpt) throws ApplicationException {
        try {
            double tdsAmt = 0;
            for (int cnt = 0; cnt < intCloseList.size(); cnt++) {
                Vector intCloseVect = (Vector) intCloseList.get(cnt);

                //double clFinYearInt = Double.parseDouble(intCloseVect.get(0).toString());
                float closeVchNo = Float.parseFloat(intCloseVect.get(1).toString());
                String closeAcNo = intCloseVect.get(0).toString();
                String clActIntOpt = intCloseVect.get(2).toString();

                //double vchCalTds = clFinYearInt * tdsRate / 100;
                // double recoveredTds = getClActFinYearTds(closeAcNo, frmDT, toDT, "R", closeVchNo);
                double unRecoveredTds = getClActFinYearTds(closeAcNo, frmDT, toDT, "NR", closeVchNo);
                // double insertedTds = 0;
//                if (vchCalTds < 0) {
//                    tdsAmt = tdsAmt + vchCalTds;
//                }
//                if (Math.round(vchCalTds) > recoveredTds + unRecoveredTds) {
//                    insertedTds = vchCalTds - recoveredTds + unRecoveredTds;
//
//                    tdsAmt = tdsAmt + insertedTds;
//                    Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
//                            + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
//                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + Math.round(insertedTds) + ",'" + tempBd + "','" + toDT + "','" + frmDT + "','"
//                            + clActIntOpt + "','R'," + trsNumber + "," + recNo + "," + rtNumber + ",date_format(now(),'%Y%m%d'))");
//                    int result = insertTdsQuery.executeUpdate();
//                    if (result < 0) {
//                        tdsAmt = tdsAmt - insertedTds;
//                        throw new ApplicationException("Error in updating tdshistory for tds ");
//                    }
//                }
                if (unRecoveredTds > 0) {
                    //insertedTds = unRecoveredTds;
                    tdsAmt = tdsAmt + unRecoveredTds;
                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recno = " + recNo + ", "
                            + "recoveredVch = " + trsNumber + ", tdsRecoveredDt = " + tempBd + " where acno = '" + closeAcNo + "' and "
                            + "VoucherNo = " + closeVchNo + " and recovered ='NR' and intOpt = '" + clActIntOpt + "'");
                    int result = updateTdsQuery.executeUpdate();
                    if (result < 0) {
                        throw new ApplicationException("Error in updating tdshistory for tds ");
                    }
                    Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                            + "VALUES('" + closeAcNo + "'," + closeVchNo + "," + unRecoveredTds + ",'" + tempBd + "','" + tempBd + "','"
                            + frmDT + "','" + clActIntOpt + "')").executeUpdate();
                    if (TDSHistory <= 0) {
                        throw new ApplicationException("Data Not Saved For " + closeAcNo);
                    }
                }
            }
            double remainTds = calculatedTds - tdsAmt;
            if (remainTds > 0) {
                Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                        + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                        + "VALUES('" + acno + "'," + rtNumber + "," + remainTds + ",'" + tempBd + "','" + tempBd + "','" + frmDT + "','"
                        + intOpt + "','R'," + trsNumber + "," + recNo + ",0,'" + tempBd + "')");
                int result = insertTdsQuery.executeUpdate();
                if (result < 0) {
                    throw new ApplicationException("Error in updating tdshistory for tds ");
                }
                Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                        + "VALUES('" + acno + "'," + rtNumber + "," + remainTds + ",'" + tempBd + "','" + tempBd + "','"
                        + frmDT + "','" + intOpt + "')").executeUpdate();
                if (TDSHistory <= 0) {
                    throw new ApplicationException("Data Not Saved For " + acno);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getClActFinYearTds(String acno, String fromDt, String toDt, String recover, float vchNo) throws ApplicationException {
        try {
            String acNature = ftsPostMgmtRepote.getAccountNature(acno);

            String query = "";
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                query = "select ifnull(sum(tds),0) as tds from td_vouchmst tv,tds_reserve_history ti where tv.acno=ti.acno and (tv.cldt >='" + fromDt + "') "
                        + "and tv.acno='" + acno + "' and tv.VoucherNo = ti.voucherno and ti.dt between '" + fromDt + "' and '" + toDt
                        + "' and ti.recovered='" + recover + "' and ti.voucherno = " + vchNo;
            } else {
                query = "select ifnull(sum(tds),0) as tds from accountmaster ac,tds_reserve_history ri where ac.acno=ri.acno and "
                        + "(ac.closingdate >='" + fromDt + "') and ac.acno='" + acno + "' and ri.dt between '" + fromDt + "' and '" + toDt
                        + "' and ri.recovered='" + recover + "'";
            }
            List dataList = em.createNativeQuery(query).getResultList();
            Vector tdsPaidVector = (Vector) dataList.get(0);
            return Double.parseDouble(tdsPaidVector.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String autoMonthlyIntPosting() throws ApplicationException {
        try {
            List brCodeList = em.createNativeQuery("select bd.brncode from bankdays bd, branchmaster bm where Date='" + ymd.format(date) + "' and DayBeginFlag <> 'H' "
                    + "and cast(bd.brncode as unsigned) = bm.brncode").getResultList();
            for (int k = 0; k < brCodeList.size(); k++) {
                Vector brCodeV = (Vector) brCodeList.get(k);

                String orgnBrCode = brCodeV.get(0).toString();

                List acCodeList = em.createNativeQuery("select acctcode,glheadint from accounttypemaster WHERE productCode like '%M%'").getResultList();
                for (int j = 0; j < acCodeList.size(); j++) {

                    Vector acctV = (Vector) acCodeList.get(j);

                    String acctCode = acctV.get(0).toString();

                    String glHeadInt = orgnBrCode + acctV.get(1).toString() + "01";

                    List cursorLt = em.createNativeQuery("select tm.seqno,tm.acno, substring(am.custName,1,28),tm.voucherno,tm.roi,date_format(tm.nextintpaydt,'%Y%m%d'),"
                            + "date_format(tm.matdt,'%Y%m%d'),tm.prinamt,ifnull(tm.years,0), ifnull(tm.months,0),ifnull(tm.days,0),tm.cumuprinamt,tm.inttoAcno,"
                            + "tm.TDSDeducted, date_format(tm.FDDT,'%Y%m%d'),tm.period From td_vouchmst tm, td_accountmaster am Where am.acno=tm.acno and "
                            + "tm.intopt='M' and tm.status<>'C'  and tm.nextIntPayDt< tm.matdt and tm.roi<>0 AND tm.nextintpaydt<='"
                            + CbsUtil.monthAdd(ymd.format(date), -1) + "' and am.accttype='" + acctCode + "' and am.curbrcode = " + orgnBrCode).getResultList();
                    if (!cursorLt.isEmpty()) {
                        float trsno = ftsPostMgmtRepote.getTrsNo();

                        float trsno2 = ftsPostMgmtRepote.getTrsNo();

                        double totalInt = 0.0d;

                        for (int i = 0; i < cursorLt.size(); i++) {

                            Vector curV = (Vector) cursorLt.get(i);
                            String accno = curV.get(1).toString();
                            float voucherno = Float.parseFloat(curV.get(3).toString());
                            float roi = Float.parseFloat(curV.get(4).toString());

                            String nextintpaydt = curV.get(5).toString();
                            String matdt = curV.get(6).toString();
                            double pamt = Double.parseDouble(curV.get(7).toString());

                            String intToAcno = curV.get(12).toString();
                            String period = (curV.get(15).toString());
                            if (intToAcno.equals("")) {
                                intToAcno = accno;
                            }

                            long dateDiff = CbsUtil.dayDiff(ymd.parse(ymd.format(date)), ymd.parse(matdt));

                            String toDt = "";

                            if (dateDiff < 0) {
                                toDt = matdt;
                            } else {
                                toDt = CbsUtil.monthAdd(nextintpaydt, 1);
                            }
                            //String orgnBrCode = accno.substring(0, 2);

                            double interest = Double.parseDouble(tdRcptMgmtRemote.orgFDInterest("M", roi, nextintpaydt, toDt, pamt, period, orgnBrCode));

                            float recno = ftsPostMgmtRepote.getRecNo();

                            Integer tdrecon = em.createNativeQuery("insert into td_recon (acno,dt,ValueDt,cramt,voucherno,intflag,ty,trantype,"
                                    + "details,auth,enterby,authby,recno,trsno,PAYBY,TRANDESC,IY,org_brnid,dest_brnid)values('" + accno + "',"
                                    + "date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d')," + interest + "," + voucherno + ",'I',0,8,'Int on Vch: "
                                    + voucherno + "','Y','System','System'," + recno + "," + trsno + ",3,4,1,'" + orgnBrCode + "','"
                                    + orgnBrCode + "')").executeUpdate();
                            if (tdrecon <= 0) {
                                throw new ApplicationException("Error in TD Credit Entry !!!");
                            }

                            if (!intToAcno.equalsIgnoreCase(accno)) {
                                String crDate = ymd.format(date);
                                double crAmount = interest;

                                String crDetails = "Int.on Vch : " + String.valueOf(voucherno) + " A/c No. ";

                                if (!ftsPostMgmtRepote.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
                                    /**
                                     * Interbranch Transaction
                                     */
                                    recno = ftsPostMgmtRepote.getRecNo();

                                    String resultMessage = ftsRemoteMethods.cbsPostingSx(intToAcno, 0, crDate, crAmount, 0, 2, crDetails + accno, 0.0f, "", "", "",
                                            3, 0.0f, recno, 4, ftsPostMgmtRepote.getCurrentBrnCode(intToAcno), orgnBrCode, "System", "System", trsno2, "", "");

                                    if (!resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
                                        throw new ApplicationException(resultMessage);
                                    }
                                    recno = ftsPostMgmtRepote.getRecNo();

                                    resultMessage = ftsRemoteMethods.cbsPostingCx(accno, 1, crDate, crAmount, 0, 2, crDetails + intToAcno, 0.0f, "", "", "",
                                            3, 0.0f, recno, 4, orgnBrCode, orgnBrCode, "System", "System", trsno2, "", "");
                                    if (!resultMessage.substring(0, 4).equalsIgnoreCase("true")) {
                                        throw new ApplicationException(resultMessage);
                                    }

                                } else if (ftsPostMgmtRepote.getCurrentBrnCode(intToAcno).equalsIgnoreCase(orgnBrCode)) {
                                    /**
                                     * Local Transaction
                                     */
                                    recno = ftsPostMgmtRepote.getRecNo();

                                    Integer recon = em.createNativeQuery("insert into td_recon(acno,dt,ValueDt,Dramt,voucherno,ty,trantype,details,"
                                            + "auth,enterBy,authby,recno,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) values ('" + accno + "',"
                                            + "date_format(now(),'%Y%m%d'),date_format(now(),'%Y%m%d')," + crAmount + "," + voucherno + ",1,2,'Int.on Vch : " + voucherno + " A/c No. " + intToAcno + "',"
                                            + "'Y','System','System'," + recno + "," + trsno2 + ",3,4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                                    if (recon <= 0) {
                                        throw new ApplicationException("Error in TD Debit Entry !!!");
                                    }
                                    //   ftsMethods.updateBalance(acNat, acno, 0, interest-tds, "Y", "Y");

                                    String acctNature = ftsPostMgmtRepote.getAccountNature(intToAcno);
                                    recno = ftsPostMgmtRepote.getRecNo();

                                    String resultMessage = ftsPostMgmtRepote.insertRecons(acctNature, intToAcno, 0, crAmount, crDate, crDate, 2, crDetails + accno,
                                            "System", trsno2, crDate, recno, "Y", "System", 4, 3, "", "", 0.0f, "", "", 1, "", 0.0f, "", "", orgnBrCode, orgnBrCode,
                                            0, "", "", "");

                                    if (!resultMessage.equalsIgnoreCase("true")) {
                                        throw new ApplicationException(resultMessage);
                                    }

                                    resultMessage = ftsPostMgmtRepote.updateBalance(acctNature, intToAcno, crAmount, 0, "Y", "Y");

                                    if (!resultMessage.equalsIgnoreCase("true")) {
                                        throw new ApplicationException(resultMessage);
                                    }
                                }
                                ftsPostMgmtRepote.lastTxnDateUpdation(intToAcno);
                            } else {
                                double crAmount = interest;
                                String acctNature = ftsPostMgmtRepote.getAccountNature(accno);
                                String resultMessage = ftsPostMgmtRepote.updateBalance(acctNature, accno, crAmount, 0, "Y", "Y");
                                if (!resultMessage.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(resultMessage);
                                }
                            }
                            Integer interesthistory = em.createNativeQuery("insert into td_interesthistory(acno,voucherno,interest,fromdt,todt,"
                                    + "intopt,dt) values ('" + accno + "'," + voucherno + "," + interest + ",'" + nextintpaydt + "','" + toDt + "',"
                                    + "'M','" + ymd.format(date) + "')").executeUpdate();
                            if (interesthistory <= 0) {
                                return "Error in TD Interest History Entry !!!";
                            }
                            Integer vouchmst = em.createNativeQuery("update td_vouchmst set nextintpaydt='" + CbsUtil.dateAdd(toDt, 1) + "'"
                                    + " where acno='" + accno + "' and intopt='M' and status='A' AND VOUCHERNO = " + voucherno).executeUpdate();
                            if (vouchmst <= 0) {
                                return "Error in TD Voucher Updation !!!";
                            }
                            totalInt = totalInt + interest;
                        }
                        if (totalInt > 0) {
                            float recno = ftsPostMgmtRepote.getRecNo();
                            Integer rdrecon = em.createNativeQuery("insert into gl_recon (acno,dt,valueDt,dramt,ty,trantype,details,auth,enterBy,"
                                    + "authby,trsno,payby,recno,TRANDESC,IY,org_brnid,dest_brnid) values('" + glHeadInt + "',date_format(now(),'%Y%m%d')"
                                    + ",date_format(now(),'%Y%m%d')," + totalInt + ",1,8, 'Interest Amount','Y','System','System'," + trsno + ",3,"
                                    + "" + recno + ",4,1,'" + orgnBrCode + "','" + orgnBrCode + "')").executeUpdate();
                            if (rdrecon <= 0) {
                                return "Error in GL_Entry !!!";
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

    public String autoPendingChargesPosting(String todayDt, String enterBy) throws ApplicationException {
        try {
            String msg = "", desc = "";;
            int tyFlag = 1;
            Float trSNo = null;
            boolean isNpaChargesPost = false;
            List npaChargesPostingList = em.createNativeQuery("SELECT ifnull(code,0) FROM parameterinfo_report WHERE reportname='NPA CHARGES POSTING'").getResultList();
            if (!npaChargesPostingList.isEmpty()) {
                Vector npaChargesPostVect = (Vector) npaChargesPostingList.get(0);
                if (npaChargesPostVect.get(0).toString().equals("1")) {
                    isNpaChargesPost = true;
                } else {
                    isNpaChargesPost = false;
                }
            }
            List list = em.createNativeQuery("Select ifnull(code,0),reportname from parameterinfo_report where "
                    + "reportname in ('STAXMODULE_ACTIVE')").getResultList();
            Vector v8 = (Vector) list.get(0);
            String staxModuleActive = "N";
            if (Integer.parseInt(v8.get(0).toString()) == 0) {
                staxModuleActive = "N";
            } else {
                staxModuleActive = "Y";
            }
            Map<String, Double> map = new HashMap<>();
            double sPerc = 0;
            int rUpTo = 0;
            double sPercIgst = 0;
            int rUpToIgst = 0;
            if (staxModuleActive.equalsIgnoreCase("Y")) {
                map = ftsRemoteMethods.getTaxComponentSlab(ymd.format(date));
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    sPerc = sPerc + Double.parseDouble(entry.getValue().toString());
                    rUpTo = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
                map = ftsRemoteMethods.getIgstTaxComponentSlab(ymd.format(date));
                Set<Entry<String, Double>> set1 = map.entrySet();
                Iterator<Entry<String, Double>> it1 = set1.iterator();
                while (it1.hasNext()) {
                    Entry entry = it1.next();
                    sPercIgst = sPercIgst + Double.parseDouble(entry.getValue().toString());
                    rUpToIgst = Integer.parseInt(entry.getKey().toString().split(":")[3]);
                }
            }
            List brCodeList = em.createNativeQuery("select distinct a.trandesc as code, substring(a.acno,3,2) as acType, \n"
                    + " substring(a.acno,1,2) as brnCode, concat(substring(a.acno,1,2),b.GLHeadMisc,'01') as glhead , rrt.ref_desc \n"
                    + " from pendingcharges a, parameterinfo_miscincome b,gltable c,cbs_ref_rec_type rrt \n"
                    + " where a.recover = 'N' and a.TRANDESC = b.trandesc and rrt.ref_code=a.trandesc and rrt.ref_rec_no='454'  \n"
                    + " and concat(substring(a.acno,1,2),b.GLHeadMisc,'01') = c.acno \n"
                    + " group by a.trandesc, substring(a.acno,1,2), substring(a.acno,3,2) \n"
                    + " order by a.trandesc, substring(a.acno,1,2), substring(a.acno,3,2) ").getResultList();
            for (int k = 0; k < brCodeList.size(); k++) {
                Vector brCodeV = (Vector) brCodeList.get(k);

                int tranDesc = Integer.parseInt(brCodeV.get(0).toString());
                String acType = brCodeV.get(1).toString();
                String orgnBrCode = brCodeV.get(2).toString();
                String glAcc = brCodeV.get(3).toString();
                desc = "Auto recover of " + brCodeV.get(4).toString();

                String mainDetails = "";
                double totalTaxAmt = 0;
                trSNo = ftsPostMgmtRepote.getTrsNo();
                double penalty1 = 0d;
                double amt = 0d;
                double ttlTaxAmt = 0d;
                double ttlTaxAmtIgst = 0d;

                double penalty = 0d;
                String taxName = "";
                int ty = 0;
                int iy = 1;
                ty = 0;
                List chargesList = em.createNativeQuery("select p.Acno, concat(ifnull(cm.custname,''), if(ifnull(cm.middle_name,'')= '', ifnull(cm.middle_name,''), concat(' ', ifnull(cm.middle_name,''))), if(ifnull(cm.last_name,'')= '', ifnull(cm.last_name,''), concat(' ', ifnull(cm.last_name,'')))) as CustName, "
                        + " p.amount, DATE_FORMAT(p.dt,'%Y%m%d'), p.enterby, p.TXNID, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState  "
                        + " from pendingcharges p, customerid ci, cbs_customer_master_detail cm, branchmaster br, accountmaster ac  "
                        + " where p.Acno = ci.Acno and p.acno = ac.acno and ci.CustId = cast(cm.customerid as unsigned) and ac.AccStatus <>9 "
                        + " and p.recover = 'N' and p.TRANDESC = " + tranDesc
                        + " and substring(p.Acno,3,2) = '" + acType + "' and substring(p.Acno,1,2) = '" + orgnBrCode + "' "
                        + " and br.BrnCode=cast(substring(p.Acno,1,2) as unsigned)").getResultList();
                for (int z = 0; z < chargesList.size(); z++) { // for
                    Vector chargeVect = (Vector) chargesList.get(z);
                    String acNo = chargeVect.get(0).toString();

                    String custName = chargeVect.get(1).toString();
                    penalty = Double.parseDouble(chargeVect.get(2).toString());
                    String dt = chargeVect.get(3).toString();
                    int txnId = Integer.parseInt(chargeVect.get(5).toString());
                    String custState = chargeVect.get(6).toString();
                    String branchState = chargeVect.get(7).toString();

                    int optStatus = 1;//chargesObj.getOptStatus();
                    int stat = Integer.parseInt(ftsPostMgmtRepote.getAccountPresentStatus(acNo).get(0));
                    String acNature = ftsDrCr.acNature(ftsPostMgmtRepote.getAccountCode(acNo));

                    double tPenalty = penalty;
                    float recNo = 0f;
                    double taxAmt = 0d;
                    double taxAmtIgst = 0d;
                    if (penalty > 0) {
                        if (stat != 11 && stat != 12 && stat != 13) {
                            if (staxModuleActive.equalsIgnoreCase("Y")) {
                                if (custState.equalsIgnoreCase(branchState)) {
                                    taxAmt = CbsUtil.round(((penalty * sPerc) / 100), rUpTo);
                                    ttlTaxAmt = ttlTaxAmt + taxAmt;
                                    tPenalty = penalty + taxAmt;
                                } else {
                                    taxAmtIgst = CbsUtil.round(((penalty * sPercIgst) / 100), rUpToIgst);
                                    ttlTaxAmtIgst = ttlTaxAmtIgst + taxAmtIgst;
                                    tPenalty = penalty + taxAmtIgst;
                                    System.out.println("acNo:" + acNo + "; Igst:" + taxAmtIgst + "; Total IGST" + ttlTaxAmtIgst);
                                }
                            }
                            if (tyFlag == 1) {
                                msg = ftsPostMgmtRepote.checkBalance(acNo, tPenalty, enterBy);
                            }

                            if (msg.equalsIgnoreCase("True")) { // In case of sufficient fund.
                                if (staxModuleActive.equalsIgnoreCase("Y")) {
                                    recNo = ftsPostMgmtRepote.getRecNo();
                                    String taxDetail = (custState.equalsIgnoreCase(branchState) ? "CGST:SGST" : "IGST") + " for " + desc + ":" + dt;
                                    msg = ftsPostMgmtRepote.insertRecons(acNature, acNo, tyFlag, (custState.equalsIgnoreCase(branchState) ? taxAmt : taxAmtIgst), ymd.format(date), ymd.format(date), 2, taxDetail, enterBy, trSNo, null,
                                            recNo, "Y", enterBy, 71, 3, null, null, (float) 0, null, "A", iy, null, null, null, null,
                                            orgnBrCode, orgnBrCode, 0, null, "", "");
                                    if (!msg.equalsIgnoreCase("True")) {
                                        return "False";
                                    }
                                }
                                recNo = ftsPostMgmtRepote.getRecNo();

                                msg = ftsPostMgmtRepote.insertRecons(acNature, acNo, tyFlag, penalty, ymd.format(date), ymd.format(date), 2, desc + ":" + dt, enterBy, trSNo, null, recNo, "Y", enterBy, tranDesc,
                                        3, null, null, (float) 0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                                if (!msg.equalsIgnoreCase("True")) {
                                    return "False";
                                }
                                penalty1 = penalty1 + penalty;
                                double crAmt = 0d, drAmt = 0d;
                                if (tyFlag == 0) {
                                    crAmt = tPenalty;
                                    drAmt = (float) 0;
                                } else {
                                    crAmt = (float) 0;
                                    drAmt = tPenalty;
                                }
                                msg = ftsPostMgmtRepote.updateBalance(acNature, acNo, crAmt, drAmt, "Y", "Y");
                                if (!msg.equalsIgnoreCase("True")) {
                                    return "False";
                                }
                                Integer updatePending = em.createNativeQuery("Update pendingcharges set recover = 'Y',Updatedt=CURRENT_TIMESTAMP,	UpdateBy='" + enterBy + "' "
                                        + " where acno='" + acNo + "' and TXNID = " + txnId).executeUpdate();
                                if (updatePending <= 0) {
                                    return "pendingcharges is not updated";
                                }
                            } else { // In case of insufficient fund.

                                if (staxModuleActive.equalsIgnoreCase("Y")) {
                                    if (custState.equalsIgnoreCase(branchState)) {
                                        if (ttlTaxAmt != 0) {
                                            ttlTaxAmt = ttlTaxAmt - taxAmt;
                                        }
                                    } else {
                                        if (ttlTaxAmtIgst != 0) {
                                            ttlTaxAmtIgst = ttlTaxAmtIgst - taxAmtIgst;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (isNpaChargesPost) {
                                recNo = ftsPostMgmtRepote.getRecNo();

                                msg = ftsDrCr.ftsNpaEntry(acNo, tyFlag, penalty, desc + ":" + dt, enterBy, enterBy, optStatus, trSNo, recNo,
                                        tranDesc, 1, custName, orgnBrCode);
                                if (!msg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Date Couldn't be inserted in NPA_ENTRY");
                                }
                                Integer updatePending = em.createNativeQuery("Update pendingcharges set recover = 'Y',Updatedt=CURRENT_TIMESTAMP,	UpdateBy='" + enterBy + "' "
                                        + " where acno='" + acNo + "' and TXNID = " + txnId).executeUpdate();
                                if (updatePending <= 0) {
                                    return "pendingcharges is not updated";
                                }
                                totalTaxAmt = totalTaxAmt + penalty;
                            }
                        }
                    }// End pently >= 0 condition
                    amt = penalty1;
                } // End for loop
                if (totalTaxAmt > 0) {
                    ftsDrCr.contraEntryUriOirHead(acType, totalTaxAmt, orgnBrCode, desc, ymd.format(date), tranDesc, trSNo, enterBy);
                }
                if (penalty1 > 0) {
                    float recNo = 0f;
                    String acNature = "";
                    String taxDesc = taxName + desc;
                    double crAmt = 0d, drAmt = 0d;
                    double totalGstAmt = 0;

                    if (staxModuleActive.equalsIgnoreCase("Y")) {
                        double sTaxAmt = CbsUtil.round(((ttlTaxAmt * 100) / sPerc), rUpTo);
                        map = interBranchFacade.getTaxComponent(sTaxAmt, ymd.format(date));
                        Set<Entry<String, Double>> set1 = map.entrySet();

                        Iterator<Entry<String, Double>> it1 = set1.iterator();
                        String taxHead = "";
                        while (it1.hasNext()) {
                            Entry entry = it1.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            taxHead = orgnBrCode + keyArray[1];
                            mainDetails = description.toUpperCase() + " for " + desc;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            totalGstAmt = totalGstAmt + taxAmount;
                            acNature = ftsDrCr.acNature(ftsPostMgmtRepote.getAccountCode(taxHead));
                            recNo = ftsPostMgmtRepote.getRecNo();

                            msg = ftsPostMgmtRepote.insertRecons(acNature, taxHead, 0, taxAmount, ymd.format(date), ymd.format(date), 2, mainDetails, enterBy, trSNo, null, recNo, "Y", enterBy,
                                    71, 3, null, null, (float) 0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }

                            crAmt = taxAmount;
                            drAmt = (float) 0;
                            msg = ftsPostMgmtRepote.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                            if (!msg.equalsIgnoreCase("True")) {
                                return "False";
                            }
                        }
                        ttlTaxAmt = CbsUtil.round(ttlTaxAmt, rUpTo);
                        if (ttlTaxAmt != totalGstAmt) {
                            double serAmount = ttlTaxAmt - totalGstAmt;
                            serAmount = CbsUtil.round(serAmount, rUpTo);
                            int drCr = 0;
                            if (serAmount < 0) {
                                drCr = 1;
                                serAmount = Math.abs(serAmount);
                            }
                            recNo = recNo + 1;
                            msg = ftsPostMgmtRepote.insertRecons("PO", taxHead, drCr, serAmount, ymd.format(date), ymd.format(date), 2, "Difference amount for Charge batch", enterBy, trSNo, null, recNo, "Y",
                                    enterBy, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0,
                                    "", "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }

                            msg = ftsPostMgmtRepote.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                        }
                        if (ttlTaxAmtIgst != 0) {
                            double sTaxAmtIgst = CbsUtil.round(((ttlTaxAmtIgst * 100) / sPercIgst), rUpToIgst);
                            map = interBranchFacade.getIgstTaxComponent(sTaxAmtIgst, ymd.format(date));
                            Set<Entry<String, Double>> setIgst = map.entrySet();
                            Iterator<Entry<String, Double>> itIgst = setIgst.iterator();
                            while (itIgst.hasNext()) {
                                Entry entry = itIgst.next();
                                String[] keyArray = entry.getKey().toString().split(":");
                                String description = keyArray[0];
                                taxHead = orgnBrCode + keyArray[1];

                                mainDetails = description.toUpperCase() + " for " + desc;
                                double taxAmount = Double.parseDouble(entry.getValue().toString());

                                acNature = ftsDrCr.acNature(ftsPostMgmtRepote.getAccountCode(taxHead));
                                recNo = ftsPostMgmtRepote.getRecNo();

                                msg = ftsPostMgmtRepote.insertRecons(acNature, taxHead, 0, taxAmount, ymd.format(date), ymd.format(date), 2, mainDetails, enterBy, trSNo, null, recNo, "Y", enterBy,
                                        71, 3, null, null, (float) 0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                                if (!msg.equalsIgnoreCase("True")) {
                                    return "False";
                                }

                                crAmt = taxAmount;
                                drAmt = (float) 0;
                                msg = ftsPostMgmtRepote.updateBalance(acNature, taxHead, crAmt, drAmt, "Y", "Y");
                                if (!msg.equalsIgnoreCase("True")) {
                                    return "False";
                                }
                            }
                        }
                    }
                    acNature = ftsDrCr.acNature(ftsPostMgmtRepote.getAccountCode(glAcc));
                    msg = ftsPostMgmtRepote.insertRecons(acNature, glAcc, ty, amt, ymd.format(date), ymd.format(date), 2, taxDesc, enterBy, trSNo, null, recNo, "Y", enterBy,
                            tranDesc, 3, null, null, (float) 0.0, null, "A", iy, null, null, null, null, orgnBrCode, orgnBrCode, 0, null, "", "");
                    if (!msg.equalsIgnoreCase("True")) {
                        return "False";
                    }
                    if (ty == 0) {
                        crAmt = amt;
                        drAmt = (float) 0;
                    } else {
                        crAmt = (float) 0;
                        drAmt = amt;
                    }
                    msg = ftsPostMgmtRepote.updateBalance(acNature, glAcc, crAmt, drAmt, "Y", "Y");
                    if (!msg.equalsIgnoreCase("True")) {
                        return "False";
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    //Auto Term Deposit Payment
    /**
     *
     * @param todayDt
     * @param userName
     * @return
     * @throws ApplicationException
     */
    public String autoPaymentTermDeposit(String todayDt, String userName) throws ApplicationException {
        String message = "true";
        try {
            List accountList = getAllPaymentAccount(ymd.format(new Date()));
            for (int i = 0; i < accountList.size(); i++) {
                Vector ele = (Vector) accountList.get(i);
                BigDecimal prinAmt = new BigDecimal(ele.get(0).toString());
                Double receiptNo = Double.parseDouble(ele.get(1).toString());
                String tdAc = ele.get(2).toString().trim();
                String fdDt = ele.get(3).toString().trim();
                String matDt = ele.get(5).toString().trim();
                String intOpt = ele.get(6).toString().trim();
                Double roi = Double.parseDouble(ele.get(7).toString().trim());
                Double voucherNo = Double.parseDouble(ele.get(13).toString());
                String autoRenew = ele.get(15).toString().trim();
                String paymentAc = ele.get(17).toString().trim();
                String intToAcno = ele.get(19).toString().trim();

                if (paymentAc.equals("") || paymentAc.length() != 12) {
                    return "There is no proper auto payment a/c number "
                            + "for A/c-->" + tdAc + "::Receipt No-->" + receiptNo;
                }
                if (autoRenew.equals("Y")) {
                    return "Auto renew and auto payment can not be applied "
                            + "together for A/c-->" + tdAc + "::Receipt No-->" + receiptNo;
                }
                //Retrieving receipt details
                String[] arr = getReceiptDetails(tdAc, voucherNo.floatValue(), roi.floatValue(), fdDt, matDt,
                        prinAmt.doubleValue(), getCustType(tdAc), tdAc.substring(0, 2), "Y", todayDt);
                //Calling td payment
                String result = completeButton(tdAc, voucherNo.floatValue(), getIntOptDescription(intOpt), "MATURE",
                        prinAmt.doubleValue(), Double.parseDouble(arr[0]), Double.parseDouble(arr[1]),
                        Double.parseDouble(arr[2]), Double.parseDouble(arr[3]), fdDt, matDt,
                        Double.parseDouble(arr[4]), 0f, roi.floatValue(), "System",
                        tdAc.substring(0, 2), Double.parseDouble(arr[5]), "Y", "System",
                        Double.parseDouble(arr[6]), "Y", todayDt); //Check for passed brcode
                if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                    return result;
                }
                //Auto payment transaction
                result = autoPaymentTransaction(tdAc, prinAmt.doubleValue(), voucherNo, paymentAc, Float.parseFloat(result.substring(result.indexOf(":") + 1)),
                        userName, todayDt, Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), intOpt, intToAcno);
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
                //Other table updation
                int n = em.createNativeQuery("update td_vouchmst set status='C',cldt='" + todayDt + "' where "
                        + "acno='" + tdAc + "' and voucherno=" + voucherNo).executeUpdate();
                if (n <= 0) {
                    return "Problem in data updation in td vouchmst table for a/c-->" + tdAc;
                }

                n = em.createNativeQuery("insert into td_payment_auth(acno,voucherno,receiptno,intopt,status,"
                        + "prinamt,remainingint,tdstobeded,tdsdeducted,intpaid,fddt,matdt,finalamt,penalty,"
                        + "roi,actualtotint,enterby,auth,authby,trantime,actualroi,netconroi,clacttdstobededucted,"
                        + "clacttdsdeducted,clacttdsintfinyear) values('" + tdAc + "'," + voucherNo + ","
                        + "" + receiptNo + ",'" + intOpt.substring(0, 1) + "','M'," + prinAmt + "," + arr[0] + ","
                        + "" + arr[1] + "," + arr[2] + "," + arr[3] + ",'" + fdDt + "','" + matDt + "',"
                        + "" + arr[4] + ",0," + roi + "," + arr[5] + ",'" + userName + "','Y','System',"
                        + "now()," + roi + ",0," + arr[6] + "," + arr[7] + "," + arr[8] + ")").executeUpdate();
                if (n <= 0) {
                    return "Problem in data insertin in td_payment_auth table for a/c-->" + tdAc;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public List getAllPaymentAccount(String dt) throws ApplicationException {
        List accountList = new ArrayList();
        try {
            Integer code = ftsPostMgmtRepote.getCodeForReportName("FD-SAME-DATE-PAYMENT");
            if (code == 0) {
                accountList = em.createNativeQuery("select distinct tdv.prinamt,tdv.receiptno,tdv.acno,DATE_FORMAT(tdv.fddt,'%Y%m%d') as fddt,"
                        + "tdv.status,DATE_FORMAT(matdt,'%Y%m%d') as matdt,tdv.IntOpt,tdv.roi,tdv.years,tdv.months,tdv.days,tdv.period,tdv.seqno,"
                        + "tdv.voucherno,DATE_FORMAT(tdv.nextintpaydt,'%Y%m%d') as nextintpaydt,tdv.autorenew,ifnull(tdv.auto_pay,'') as auto_pay,"
                        + "ifnull(tdv.auto_paid_acno,'') as auto_paid_acno,a.acctnature,tdv.IntToAcno from td_vouchmst tdv,td_accountmaster tda, accounttypemaster a "
                        + "where tdv.matdt<'" + dt + "' and tdv.auto_pay ='Y' and tdv.status='A' and tdv.acno = tda.acno and tda.accstatus not in (9,15) "
                        + "and a.acctcode = tda.accttype and tda.CurBrCode in (select bd.brncode from bankdays bd, branchmaster bm where Date='" + dt + "' "
                        + "and DayBeginFlag <> 'H' and cast(bd.brncode as unsigned) = bm.brncode)").getResultList();
            } else if (code == 1) {
                accountList = em.createNativeQuery("select distinct tdv.prinamt,tdv.receiptno,tdv.acno,DATE_FORMAT(tdv.fddt,'%Y%m%d') as fddt,"
                        + "tdv.status,DATE_FORMAT(matdt,'%Y%m%d') as matdt,tdv.IntOpt,tdv.roi,tdv.years,tdv.months,tdv.days,tdv.period,tdv.seqno,"
                        + "tdv.voucherno,DATE_FORMAT(tdv.nextintpaydt,'%Y%m%d') as nextintpaydt,tdv.autorenew,ifnull(tdv.auto_pay,'') as auto_pay,"
                        + "ifnull(tdv.auto_paid_acno,'') as auto_paid_acno,a.acctnature,tdv.IntToAcno from td_vouchmst tdv,td_accountmaster tda, accounttypemaster a "
                        + "where tdv.matdt<='" + dt + "' and tdv.auto_pay ='Y' and tdv.status='A' and tdv.acno = tda.acno and tda.accstatus not in (9,15) "
                        + "and a.acctcode = tda.accttype and tda.CurBrCode in (select bd.brncode from bankdays bd, branchmaster bm where Date='" + dt + "' "
                        + "and DayBeginFlag <> 'H' and cast(bd.brncode as unsigned) = bm.brncode)").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return accountList;
    }

    public String getCustType(String accountNo) throws ApplicationException {
        String custType = "OT";
        try {
            List list = em.createNativeQuery("select ifnull(cust_type,'OT') from "
                    + "td_accountmaster where acno ='" + accountNo + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                custType = ele.get(0).toString().trim();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return custType;
    }

    public String[] getReceiptDetails(String accountNo, float rtNo, float roi, String fdDt, String matDt,
            double prinAmt, String custCategory, String orgnBrCode, String autoPayFlag, String centralDayBeginDt) throws ApplicationException {
        String[] arr = new String[13];
        try {
            String rtNoInfo = tdCalRemote.cbsTdPaymentRtno(accountNo, rtNo, roi, fdDt, matDt, "", prinAmt,
                    custCategory, orgnBrCode, autoPayFlag, centralDayBeginDt);

            if (rtNoInfo.equals("This Receipt No. Does Not Exist")
                    || rtNoInfo.equals("This Receipt is closed and Amount is Paid")
                    || rtNoInfo.equals("The Receipt has been marked closed and amount is transferred to")) {
                throw new ApplicationException(rtNoInfo);
            } else if (rtNoInfo.equals("Lien Marked against Deposit . To close the Deposit remove Lien Marking against Deposit")) {
                String acnoLien = tdCalRemote.LienAcno(accountNo);
                throw new ApplicationException("Lien Marked against " + acnoLien + ". To close the Deposit remove Lien Marking against Deposit-->" + accountNo);
            } else {
                String[] values = null;
                String spliter = ": ";
                values = rtNoInfo.split(spliter);
                //Currently Required
                arr[0] = formatter.format(Math.round(Double.parseDouble(values[2]))); //RemainingInterest
                arr[1] = formatter.format(Double.parseDouble(values[5])); //TdsToBeDeducted
                arr[2] = formatter.format(Double.parseDouble(values[4])); //TdsDeducted
                arr[3] = formatter.format(Double.parseDouble(values[0])); //InterestPaid
                arr[4] = formatter.format(Double.parseDouble(values[6])); //NetAmount
                arr[5] = formatter.format(Double.parseDouble(values[1])); //ActualTotalInterest
                arr[6] = formatter.format(Double.parseDouble(values[11])); //CloseActTdsToBeDeducted
                arr[7] = formatter.format(Double.parseDouble(values[12])); //CloseActTdsDeducted
                arr[8] = formatter.format(Double.parseDouble(values[13])); //CloseActIntFinYear
                //Currently not in use filed                
                arr[9] = formatter.format(Double.parseDouble(values[3])); //DeductForLastFinalFear
                arr[10] = formatter.format(Double.parseDouble(values[8])); //ApplicableRate
                arr[11] = values[9]; //CurrentStatus
                arr[12] = values[10]; //MatPreFlag
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }

    public String getIntOptDescription(String intOpt) {
        String intOptDesc = "";
        if (intOpt.equals("C")) {
            intOptDesc = "Cumulative";
        } else if (intOpt.equals("S")) {
            intOptDesc = "Simple";
        } else if (intOpt.equals("Q")) {
            intOptDesc = "Quarterly";
        } else if (intOpt.equals("M")) {
            intOptDesc = "Monthly";
        }
        return intOptDesc;
    }

    public String autoPaymentTransaction(String tdAc, double prinAmt, Double voucherNo,
            String autoPaidAc, float trsno, String userName, String todayDt, double remainingInt, double tdsToBeDeducted, String intOpt, String intToAcno) throws ApplicationException {
        try {
            //Total interest for a/c and voucher
            List list = em.createNativeQuery("select ifnull(sum(interest),0) as interest from "
                    + "td_interesthistory where acno='" + tdAc + "' and voucherno=" + voucherNo + "").getResultList();
            Vector vec = (Vector) list.get(0);
            double totalInt = Double.parseDouble(vec.get(0).toString());
            //Total tds for a/c and voucher
            list = em.createNativeQuery("select ifnull(sum(tds),0) as tds from tdshistory "
                    + "where acno='" + tdAc + "' and voucherno=" + voucherNo + "").getResultList();
            vec = (Vector) list.get(0);
            double totalTds = Double.parseDouble(vec.get(0).toString());
            //Total net amount
            double netAmount = 0;
            if (intOpt.equalsIgnoreCase("C")) {
                netAmount = Double.parseDouble(formatter.format(prinAmt + totalInt - totalTds));
            } else {
                if (tdAc.equalsIgnoreCase(intToAcno)) {
                    netAmount = Double.parseDouble(formatter.format(prinAmt + totalInt - totalTds));
                } else {
                    netAmount = Double.parseDouble(formatter.format(prinAmt + remainingInt - tdsToBeDeducted));
                }
            }
            //Iso a/c
            list = em.createNativeQuery("select acno from abb_parameter_info where purpose='INTERSOLE ACCOUNT'").getResultList();
            if (list.isEmpty()) {
                return "Please define INTERSOLE ACCOUNT in abb parameter info";
            }
            vec = (Vector) list.get(0);
            String intersoleAc = vec.get(0).toString();
            if (intersoleAc.trim().length() != 10) {
                return "Please define proper INTERSOLE ACCOUNT in abb parameter info";
            }

            String details = "Trf amount from Td A/c-->" + tdAc + " to Auto Pay A/c-->" + autoPaidAc;

            String result = "";
            if (tdAc.substring(0, 2).equalsIgnoreCase(autoPaidAc.substring(0, 2))) { //Loacal Branch
                //Td a/c transaction
                result = ftsPostMgmtRepote.insertRecons(ftsPostMgmtRepote.getAccountNature(tdAc), tdAc, 1, netAmount,
                        todayDt, todayDt, 2, details, userName, trsno, null, ftsPostMgmtRepote.getRecNo(),
                        "Y", "System", 0, 3, "", null, 0f, "", "", 1, "", voucherNo.floatValue(), "", null,
                        tdAc.substring(0, 2), tdAc.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                result = ftsPostMgmtRepote.updateBalance(ftsPostMgmtRepote.getAccountNature(tdAc),
                        tdAc, 0, netAmount, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
                //Auto payment a/c transaction
                result = ftsPostMgmtRepote.insertRecons(ftsPostMgmtRepote.getAccountNature(autoPaidAc), autoPaidAc, 0, netAmount,
                        todayDt, todayDt, 2, details, userName, trsno, null, ftsPostMgmtRepote.getRecNo(),
                        "Y", "System", 0, 3, "", null, 0f, "", "", 1, "", voucherNo.floatValue(), "", null,
                        tdAc.substring(0, 2), tdAc.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                result = ftsPostMgmtRepote.updateBalance(ftsPostMgmtRepote.getAccountNature(autoPaidAc),
                        autoPaidAc, netAmount, 0, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
            } else { //Remote Branch
                //Td a/c transaction
                result = ftsPostMgmtRepote.insertRecons(ftsPostMgmtRepote.getAccountNature(tdAc), tdAc, 1, netAmount,
                        todayDt, todayDt, 2, details, userName, trsno, null, ftsPostMgmtRepote.getRecNo(),
                        "Y", "System", 0, 3, "", null, 0f, "", "", 1, "", voucherNo.floatValue(), "", null,
                        tdAc.substring(0, 2), tdAc.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                result = ftsPostMgmtRepote.updateBalance(ftsPostMgmtRepote.getAccountNature(tdAc),
                        tdAc, 0, netAmount, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
                //ISO a/c transaction
                String isoAc = tdAc.substring(0, 2) + intersoleAc;

                result = ftsPostMgmtRepote.insertRecons(ftsPostMgmtRepote.getAccountNature(isoAc), isoAc, 0, netAmount,
                        todayDt, todayDt, 2, details, userName, trsno, null, ftsPostMgmtRepote.getRecNo(),
                        "Y", "System", 0, 3, "", null, 0f, "", "", 1, "", voucherNo.floatValue(), "", null,
                        tdAc.substring(0, 2), autoPaidAc.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                result = ftsPostMgmtRepote.updateBalance(ftsPostMgmtRepote.getAccountNature(isoAc),
                        isoAc, netAmount, 0, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                //Auto payment a/c transaction
                result = ftsPostMgmtRepote.insertRecons(ftsPostMgmtRepote.getAccountNature(autoPaidAc), autoPaidAc, 0, netAmount,
                        todayDt, todayDt, 2, details, userName, trsno, null, ftsPostMgmtRepote.getRecNo(),
                        "Y", "System", 0, 3, "", null, 0f, "", "", 1, "", voucherNo.floatValue(), "", null,
                        tdAc.substring(0, 2), autoPaidAc.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                result = ftsPostMgmtRepote.updateBalance(ftsPostMgmtRepote.getAccountNature(autoPaidAc),
                        autoPaidAc, netAmount, 0, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                //ISO a/c transaction
                isoAc = autoPaidAc.substring(0, 2) + intersoleAc;

                result = ftsPostMgmtRepote.insertRecons(ftsPostMgmtRepote.getAccountNature(isoAc), isoAc, 1, netAmount,
                        todayDt, todayDt, 2, details, userName, trsno, null, ftsPostMgmtRepote.getRecNo(),
                        "Y", "System", 0, 3, "", null, 0f, "", "", 1, "", voucherNo.floatValue(), "", null,
                        tdAc.substring(0, 2), autoPaidAc.substring(0, 2), 0, "", "", "");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }

                result = ftsPostMgmtRepote.updateBalance(ftsPostMgmtRepote.getAccountNature(isoAc),
                        isoAc, 0, netAmount, "Y", "Y");
                if (!result.equalsIgnoreCase("true")) {
                    return result;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String completeButton(String acno, float rtNumber, String intOption, String status,
            double prinAmt, double remainingInterest, double tdsToBeDeducted, double tdsDeducted,
            double interestPaid, String fdDate, String matDate, double netAmount, float penalty,
            float roi, String user, String brCode, double actualInterest, String auth,
            String authBy, double clActTdsToBeDeducted, String autoPayFlag,
            String centralDayBeginDt) throws ApplicationException {
        try {
            double tmpCrAmt = 0d;
            String tmpPreDetails;
            double tmpDrAmt = 0;
            String glAcNo = "";
            long fullDt = CbsUtil.dayDiff(ymd.parse(fdDate), ymd.parse(matDate));
            fullDt = fullDt % 365;
            double provTds = 0;

            String Tempbd = "";
            if (autoPayFlag.equalsIgnoreCase("Y")) {
                Tempbd = centralDayBeginDt;
            } else {
                List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + brCode + "'").getResultList();
                Vector tempCurrent = (Vector) tempBd.get(0);
                Tempbd = tempCurrent.get(0).toString();
            }
            List simple = em.createNativeQuery("Select SimplePostFlag,TDBalFlag From td_parameterinfo").getResultList();
            if (simple.isEmpty()) {
                throw new ApplicationException("Data does not exist in td_parameterinfo");
            }
            Vector simples = (Vector) simple.get(0);
            int paramPostFlag = 0;
            if (simples.get(0) != null) {
                paramPostFlag = Integer.parseInt(simples.get(0).toString());
            }
            String paramBalFlag = "N";
            if (simples.get(1) != null) {
                paramBalFlag = simples.get(1).toString();
            }
            String accNature = ftsPostMgmtRepote.getAccountNature(acno);
            String acCode = ftsPostMgmtRepote.getAccountCode(acno);
            List accountList = em.createNativeQuery("select GLHeadInt,glheadprov from accounttypemaster where acctcode='" + acCode + "'").getResultList();
            if (accountList.isEmpty()) {
                throw new ApplicationException("Interest Head and Provision Head does not exist");
            }
            Vector accountLists = (Vector) accountList.get(0);
            if ((accountLists.get(0) == null) || (accountLists.get(0).equals(""))) {
                throw new ApplicationException("Interest Head does not exist");
            }
            String glHeadInt = brCode + accountLists.get(0).toString() + "01";
            String glHeadProv = "";
            if ((intOption.equals("Simple")) && (paramPostFlag == 2 || paramPostFlag == 0)) {
                if ((accountLists.get(1) == null) || (accountLists.get(1).equals(""))) {
                    throw new ApplicationException("Provision Head does not exist");
                }
                glHeadProv = brCode + accountLists.get(1).toString() + "01";
            }
            String glTds = "";
            List tdsGlHead = em.createNativeQuery("select tds_glhead from tdsslab where tds_applicabledate=(select max(tds_applicabledate) from tdsslab)").getResultList();
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
            if (status.equals("MATURE")) {
                if ((intOption.equals("Monthly")) || (intOption.equals("Quarterly"))) {
                    //  netAmount = netAmount - interestPaid;
                    if ((prinAmt) + (remainingInterest) < (tdsToBeDeducted)) {
                        throw new ApplicationException("TDS Amount Exceeds the Balance amount");
                    }
                } else if ((intOption.equals("Cumulative")) || (intOption.equals("Simple"))) {
                    if ((prinAmt + interestPaid + remainingInterest) < (tdsToBeDeducted)) {
                        throw new ApplicationException("TDS Amount Exceeds the Balance amount");
                    }
                }
            }
            if (paramBalFlag.equals("Y")) {
                ftsPostMgmtRepote.updateBalance(accNature, acno, netAmount, 0, "Y", "Y");
            } else {
                ftsPostMgmtRepote.updateBalance(accNature, acno, netAmount, 0, "Y", "Y");
            }
            float recno = 0;
            float tokenNo = 0;

            if (intOption.equals("Cumulative")) {
                tmpCrAmt = prinAmt + interestPaid;
            } else {
                tmpCrAmt = prinAmt;
            }
            if (status.equals("PREMATURE")) {
                double abc = actualInterest - interestPaid;
                tmpPreDetails = "Deposit Closed(Premature), Prin Amt: " + prinAmt + "and Int. for remaining days is " + abc;
            } else {
                tmpPreDetails = "Deposit Closed(Mature), Prin Amt: " + prinAmt + "and Int. for remaining days is " + remainingInterest;
            }
            Query insertQuery = em.createNativeQuery("insert into td_recon(acno,fddt,dt,valuedt,cramt,enterby,trantype,ty,closeflag,details,recno, trsno,"
                    + "trantime,auth,authby,org_brnid,dest_brnid,voucherNo) values ('" + acno + "','" + Tempbd + "','" + Tempbd + "','" + Tempbd + "'," + tmpCrAmt
                    + ",'" + user + "', 2 , 0 ,'C'" + ",'" + tmpPreDetails + "'," + recNo + "," + trsNumber + ",'" + Tempbd + "','" + auth + "','" + authBy
                    + "','" + brCode + "','" + brCode + "','" + rtNumber + "')");
            int varA = insertQuery.executeUpdate();
            if (varA <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }

            List tdsIntList = em.createNativeQuery("SELECT ifnull(sum(tds),0) FROM tdshistory WHERE VOUCHERNO=" + rtNumber + " and acno='" + acno + "'").getResultList();
            if (tdsIntList.size() > 0) {
                Vector tdIntVec = (Vector) tdsIntList.get(0);
                provTds = Float.parseFloat(tdIntVec.get(0).toString());
            }

            if (status.equals("MATURE")) {
                if ((intOption.equals("Simple")) && (paramPostFlag == 2 || paramPostFlag == 0)) {
                    if (interestPaid > 0) {
                        String detail = "Provision Interest Paid to TD A/c";
                        String checkListA = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 0, interestPaid, Tempbd, Tempbd, user, brCode, brCode, 3, detail,
                                trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                        if (!(checkListA.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListA);
                        }
                        detail = ":Vch Trf To  A/c: " + acno + "VoucherNo: " + rtNumber + "IntOpt: " + intOption;
                        String checkListB = ftsPostMgmtRepote.ftsPosting43CBS(glHeadProv, 2, 1, interestPaid, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                                trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                        if (!(checkListB.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListB);
                        }
                    }

                    if (provTds > 0) {
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer ReconbalanInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid)"
                                + "VALUES('" + acno + "','" + Tempbd + "','" + Tempbd + "'," + provTds + ",'" + authBy + "','Y','VCH: Tds to',1,8," + trsNumber + "," + recNo + ",'System','I','" + brCode + "','" + brCode + "')").executeUpdate();
                        if (ReconbalanInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        recNo = ftsPostMgmtRepote.getRecNo();
                        Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                + "VALUES('" + glHeadProv + "','" + Tempbd + "','" + Tempbd + "'," + provTds + ",'" + authBy + "','Y','VCH: Tds to',0,8," + trsNumber + "," + recNo + ",'System','" + brCode + "','" + brCode + "')").executeUpdate();
                        if (Gl_ReconInsert <= 0) {
                            throw new ApplicationException("Data Not Saved");
                        }
                        String rs = ftsPostMgmtRepote.updateBalance("PO", glHeadProv, provTds, 0, "Y", "Y");
                        if (!rs.equalsIgnoreCase("True")) {
                            throw new ApplicationException("Problem in GL balance updation");
                        }
                    }
                }
                if (remainingInterest > 0) {
                    String detail = "Bal. Int. Paid on Acno: " + acno + "  VoucherNo.:  " + rtNumber + " IntOpt: " + intOption;
                    String checkListC = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 0, remainingInterest, Tempbd, Tempbd, user, brCode, brCode, 3, detail,
                            trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                    if (!(checkListC.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                        throw new ApplicationException(checkListC);
                    }
                } else if (remainingInterest < 0) {
                    double balint = Math.abs(remainingInterest);
                    String detail = "Bal. Int. Paid on Acno: " + acno + "VoucherNo.: " + rtNumber + "IntOpt: " + intOption;
                    String checkListD = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 1, balint, Tempbd, Tempbd, user, brCode, brCode, 3, detail, trsNumber,
                            recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                    if (!(checkListD.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                        throw new ApplicationException(checkListD);
                    }
                }
                if (remainingInterest > 0) {
                    double balint = Math.abs(remainingInterest);
                    String detail = "Trf To GLAcno For : " + acno + "/" + rtNumber + "/" + intOption;
                    String checkListE = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 1, balint, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                            trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                    if (!(checkListE.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                        throw new ApplicationException(checkListE);
                    }
                } else if (remainingInterest < 0) {
                    double balint = Math.abs(remainingInterest);
                    String detail = "Trf To  GLAcno  For : " + acno + " / " + rtNumber + " / " + intOption;
                    String checkListF = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 0, balint, Tempbd, Tempbd, user, brCode, brCode, 0, detail,
                            trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                    if (!(checkListF.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                        throw new ApplicationException(checkListF);
                    }
                }
            } else if (status.equals("PREMATURE")) {
                if (actualInterest == interestPaid) {
                    if (intOption.equals("Simple")) {
                        if (actualInterest != 0) {
                            String detail = "Int. Provision on TD : " + acno + " / " + rtNumber + " / " + intOption;
                            String checkListG = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 0, Math.abs(actualInterest), Tempbd, Tempbd, user, brCode,
                                    brCode, 3, detail, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null,
                                    tokenNo);
                            if (!(checkListG.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                                throw new ApplicationException(checkListG);
                            }
                            if (paramPostFlag == 2 || paramPostFlag == 0) {
                                glAcNo = glHeadProv;
                            } else {
                                glAcNo = glHeadInt;
                            }
                            String details = "Trf to GLAcno2 For Acno: " + acno + " / " + rtNumber + " / " + intOption;
                            String checkListH = ftsPostMgmtRepote.ftsPosting43CBS(glAcNo, 2, 1, Math.abs(actualInterest), Tempbd, Tempbd, user, brCode,
                                    brCode, 0, details, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null,
                                    tokenNo, "N", "", "", "");
                            if (!(checkListH.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                                throw new ApplicationException(checkListH);
                            }
                        }
                    }
                } else if (actualInterest > interestPaid) {
                    if ((intOption.equals("Simple")) && (paramPostFlag != 1)) {
                        if (interestPaid > 0) {
                            String details = "Provision Int Paid to TD:" + acno + " / " + rtNumber + " / " + intOption;
                            String checkListI = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 0, Math.abs(interestPaid), Tempbd, Tempbd, user, brCode,
                                    brCode, 3, details, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null,
                                    tokenNo);
                            if (!(checkListI.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                                throw new ApplicationException(checkListI);
                            }
                            glAcNo = glHeadProv;
                            String detail = "Trf To: " + acno + " / " + rtNumber + " / " + intOption;
                            String checkListJ = ftsPostMgmtRepote.ftsPosting43CBS(glAcNo, 2, 1, Math.abs(interestPaid), Tempbd, Tempbd, user, brCode,
                                    brCode, 0, detail, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null,
                                    tokenNo, "N", "", "", "");
                            if (!(checkListJ.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                                throw new ApplicationException(checkListJ);
                            }
                        }

                        if (provTds > 0) {
                            recNo = ftsPostMgmtRepote.getRecNo();
                            Integer ReconbalanInsert = em.createNativeQuery("INSERT td_recon(acno,Dt,ValueDt,dramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,INTFLAG,org_brnid,dest_brnid)"
                                    + "VALUES('" + acno + "','" + Tempbd + "','" + Tempbd + "'," + provTds + ",'" + authBy + "','Y','VCH: Tds to',1,8," + trsNumber + "," + recNo + ",'System','I','" + brCode + "','" + brCode + "')").executeUpdate();
                            if (ReconbalanInsert <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            recNo = ftsPostMgmtRepote.getRecNo();
                            Integer Gl_ReconInsert = em.createNativeQuery("INSERT gl_recon(acno,Dt,ValueDt,cramt,EnterBy,auth,details,Ty,tranType,trsNo,Recno,authby,org_brnid,dest_brnid)"
                                    + "VALUES('" + glAcNo + "','" + Tempbd + "','" + Tempbd + "'," + provTds + ",'" + authBy + "','Y','VCH: Tds to',0,8," + trsNumber + "," + recNo + ",'System','" + brCode + "','" + brCode + "')").executeUpdate();
                            if (Gl_ReconInsert <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            String rs = ftsPostMgmtRepote.updateBalance("PO", glAcNo, provTds, 0, "Y", "Y");
                            if (!rs.equalsIgnoreCase("True")) {
                                throw new ApplicationException("Problem in GL balance updation");
                            }
                        }
                    }
                    tmpCrAmt = actualInterest - interestPaid;
                    if (tmpCrAmt < 0) {
                        String detail = "Bal. Int. Paid on Acno: " + acno + " / " + rtNumber + " / " + intOption;
                        String checkListK = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 1, Math.abs(tmpCrAmt), Tempbd, Tempbd, user, brCode, brCode, 3, detail,
                                trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                        if (!(checkListK.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListK);
                        }
                    } else if (tmpCrAmt > 0) {
                        String detail = "Bal. Int. Paid on Acno: " + acno + " / " + rtNumber + " / " + intOption;
                        String checkListL = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 0, tmpCrAmt, Tempbd, Tempbd, user, brCode, brCode, 3, detail,
                                trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                        if (!(checkListL.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListL);
                        }
                    }
                    tmpDrAmt = actualInterest - interestPaid;
                    if (tmpDrAmt < 0) {
                        String detail = "Trf to GLAcno  For:" + acno + " / " + rtNumber + " / " + intOption;
                        String checkListfg = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 0, Math.abs(tmpDrAmt), Tempbd, Tempbd, user, brCode, brCode,
                                0, detail, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                        if (!(checkListfg.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListfg);
                        }
                    } else if (tmpDrAmt > 0) {
                        String detail = "Trf to GLAcno  For:" + acno + " / " + rtNumber + " / " + intOption;
                        String checkListM = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 1, Math.abs(tmpDrAmt), Tempbd, Tempbd, user, brCode, brCode,
                                0, detail, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                        if (!(checkListM.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListM);
                        }
                    }
                } else if (actualInterest < interestPaid) {
                    //if (intOption.equals("Simple")) {
                    if ((intOption.equals("Simple")) && (paramPostFlag != 1)) {
                        if (actualInterest > 0) {
                            String detail = "Provision Int Paid to TD:" + acno + " / " + rtNumber + " / " + intOption;
                            String checkListN = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 0, actualInterest, Tempbd, Tempbd, user, brCode, brCode, 3,
                                    detail, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                            if (!(checkListN.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                                throw new ApplicationException(checkListN);
                            }
                        }
                        if (paramPostFlag == 2 || paramPostFlag == 0) {
                            glAcNo = glHeadProv;
                        } else {
                            glAcNo = glHeadInt;
                        }
                        String detail = "Trf To :" + glAcNo + " For: " + acno + " / " + rtNumber + " / " + intOption;
                        String checkListQ = ftsPostMgmtRepote.ftsPosting43CBS(glAcNo, 2, 1, interestPaid, Tempbd, Tempbd, user, brCode, brCode, 0,
                                detail, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                        if (!(checkListQ.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListQ);
                        }

                        tmpCrAmt = interestPaid - actualInterest;
                        String details = "Trf To " + glHeadInt + " For: " + acno + " / " + rtNumber + " / " + intOption;
                        String checkListR = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 0, tmpCrAmt, Tempbd, Tempbd, user, brCode, brCode, 0,
                                details, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                        if (!(checkListR.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListR);
                        }
                    } else {
                        tmpDrAmt = interestPaid - actualInterest;
                        //tdBalance = tdBalance - interestPaid - actualInterest;
                        String detail = "Balance Int Paid to TD:" + acno + " / " + rtNumber + " / " + intOption;
                        String checkListO = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 8, 1, tmpDrAmt, Tempbd, Tempbd, user, brCode, brCode, 3, detail,
                                trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "I", null, null, null, tokenNo);
                        if (!(checkListO.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListO);
                        }
                        String details = "Rev.Int Paid By TD: " + acno + " / " + rtNumber + " / " + intOption;
                        String checkListP = ftsPostMgmtRepote.ftsPosting43CBS(glHeadInt, 2, 0, tmpDrAmt, Tempbd, Tempbd, user, brCode, brCode, 0,
                                details, trsNumber, recno, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                        if (!(checkListP.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(checkListP);
                        }
                    }
                }
            }
            if (intOption.equals("Cumulative")) {
                Query updateQ2 = em.createNativeQuery("UPDATE td_vouchmst SET CumuPrinAmt=" + netAmount + ",OFFlag='N',Status='C',cldt='"
                        + Tempbd + "',finalamt=" + netAmount + ",penalty=" + penalty + ",netroi=" + roi + "  WHERE ACNO='"
                        + acno + "' and voucherno=" + rtNumber + " ");
                int varQ2 = updateQ2.executeUpdate();
                if (varQ2 <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            } else {
                Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET OFFlag='N',Status='C',cldt='" + Tempbd + "',finalamt=" + netAmount
                        + ",penalty=" + penalty + ",netroi=" + roi + "  WHERE ACNO='" + acno + "' and voucherno=" + rtNumber + " ");
                int varQ1 = updateQ1.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            }

            if (status.equals("MATURE")) {
                if (remainingInterest > 0) {
                    Query insertQueryA = em.createNativeQuery("insert into td_interesthistory(acno,dt,interest,voucherno,todt,fromdt,intopt)"
                            + "values ('" + acno + "','" + Tempbd + "'," + remainingInterest + "," + rtNumber + ",'" + Tempbd + "','" + Tempbd + "','"
                            + intOption.substring(0, 1) + "')");
                    int varAZ = insertQueryA.executeUpdate();
                    if (varAZ <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }
                }
            } else {
                double balInterest = actualInterest - interestPaid;
                if (balInterest > 0 || balInterest < 0) {
                    Query insertQueryB = em.createNativeQuery("insert into td_interesthistory(acno,dt,interest,voucherno,todt,fromdt,intopt)"
                            + "values ('" + acno + "','" + Tempbd + "'," + balInterest + "," + rtNumber + ",'" + Tempbd + "','" + Tempbd + "','"
                            + intOption.substring(0, 1) + "')");
                    int varB = insertQueryB.executeUpdate();
                    if (varB <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }
                }
            }
            List selectQuery = em.createNativeQuery("Select TDSFlag From td_accountmaster where acno='" + acno + "' ").getResultList();
            Vector tdsFlagCurrent = (Vector) selectQuery.get(0);
            String tdsFlag = tdsFlagCurrent.get(0).toString();
            if (tdsFlag.equals("Y") || tdsFlag.equals("C")) {
                if (tdsToBeDeducted > 0 || tdsToBeDeducted != 0 || clActTdsToBeDeducted > 0) {
                    if (tdsToBeDeducted > 0 || tdsToBeDeducted != 0) {
                        Query insertQueryC = em.createNativeQuery("insert into tdshistory(acno,voucherno,tds,dt,todt,fromdt,intopt) values ('" + acno
                                + "'," + rtNumber + "," + tdsToBeDeducted + ",'" + Tempbd + "','" + matDate + "','" + Tempbd + "','" + intOption.substring(0, 1) + "')");
                        int varC = insertQueryC.executeUpdate();
                        if (varC <= 0) {
                            throw new ApplicationException("Problem in data insertion");
                        }
                        Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                                + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                                + "VALUES('" + acno + "'," + rtNumber + "," + tdsToBeDeducted + ",'" + Tempbd + "','" + matDate + "','" + Tempbd + "','"
                                + intOption.substring(0, 1) + "','R'," + trsNumber + "," + recNo + "," + rtNumber + ",date_format(now(),'%Y%m%d'))");
                        int result = insertTdsQuery.executeUpdate();
                        if (result < 0) {
                            throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                        }
                    }

                    double tdsAmt = tdsToBeDeducted;
                    recNo = ftsPostMgmtRepote.getRecNo();
                    if (clActTdsToBeDeducted > 0) {
                        String finYr = getFinYear(brCode);
                        String frmDT = finYr + "0401";
                        String toDT = (Integer.parseInt(finYr) + 1) + "0331";

//                        String strResult = getTdsRateAndApplicableAmt(acno, Tempbd);
//                        String[] strRsArr = strResult.split(":");
//
//                        float tdsRate = Float.parseFloat(strRsArr[1]);
//                        List intCloseList = em.createNativeQuery("select sum(a.interest), b.voucherno,b.acno,b.intopt from  td_interesthistory a, td_vouchmst b "
//                                + "where a.acno = b.acno and b.status ='C' and b.acno = '" + acno + "' and a.voucherno = b.voucherno and cldt >='"
//                                + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' and a.voucherno <>" + rtNumber + " group by a.voucherno").getResultList();
//                        
//                        List intCloseList = em.createNativeQuery("select sum(a.interest), b.voucherno,b.acno,b.intopt from  td_interesthistory a, td_vouchmst b, customerid c "
//                                + "where a.acno = b.acno and b.acno = c.acno and b.status ='C' and c.custid in (select custid from customerid where acno = '" + acno + "') and a.voucherno = b.voucherno and cldt >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "and a.voucherno <>" + rtNumber + " group by a.voucherno").getResultList();
//
//                        tdsAmt = tdsAmt + closeActTdsPosting(intCloseList, tdsRate, frmDT, toDT, trsNumber, recNo, Tempbd, rtNumber,clActTdsToBeDeducted);
//
//                        List rdIntCloseList = em.createNativeQuery("select sum(a.interest), 0 ,b.acno,'C' from  rd_interesthistory a, accountmaster b, customerid c "
//                                + "where a.acno = b.acno and b.acno = c.acno and c.custid in (select custid from customerid where acno = '" + acno + "') and closingdate >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "group by a.acno").getResultList();
//
//                        tdsAmt = tdsAmt + closeActTdsPosting(rdIntCloseList, tdsRate, frmDT, toDT, trsNumber, recNo, Tempbd, rtNumber,clActTdsToBeDeducted);
//
//                        List dsIntCloseList = em.createNativeQuery("select sum(a.interest), 0 ,b.acno,'C' from  dds_interesthistory a, accountmaster b, customerid c "
//                                + "where a.acno = b.acno and b.acno = c.acno and c.custid in (select custid from customerid where acno = '" + acno + "') and closingdate >='" + frmDT + "' and a.dt between'" + frmDT + "' and '" + toDT + "' "
//                                + "group by a.acno").getResultList();
//
//                        tdsAmt = tdsAmt + closeActTdsPosting(dsIntCloseList, tdsRate, frmDT, toDT, trsNumber, recNo, Tempbd, rtNumber,clActTdsToBeDeducted);
//                        if (Math.round(tdsAmt) != (tdsToBeDeducted + clActTdsToBeDeducted)) {
//                            throw new ApplicationException("Tds Amount Problem");
//                        }
                        List nrCloseList = getUnRecoverdTdsAccounts(acno, frmDT, toDT, "NR", "Y");

                        closeActTdsPosting(nrCloseList, frmDT, toDT, trsNumber, recNo, Tempbd, rtNumber, clActTdsToBeDeducted, acno, intOption.substring(0, 1));
                        tdsAmt = tdsAmt + clActTdsToBeDeducted;
                    }

                    String TDS = "TDS Deducted For Acno : " + acno + "/" + rtNumber + "/ " + intOption;

                    String checkListO = ftsPostMgmtRepote.fdPaymentRenewalTxn(accNature, acno, 2, 1, Double.parseDouble(String.valueOf(Math.round(tdsAmt))), Tempbd, Tempbd, user, brCode, brCode, 0, TDS,
                            trsNumber, recNo, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, rtNumber, "T", null, null, null, tokenNo);
                    if (!(checkListO.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                        throw new ApplicationException(checkListO);
                    }
                    float recNo1 = ftsPostMgmtRepote.getRecNo();
                    String TDS1 = "TDS Deducted For Acno " + acno + "/" + rtNumber + "/ " + intOption;
                    String checkListP = ftsPostMgmtRepote.ftsPosting43CBS(glTds, 2, 0, Double.parseDouble(String.valueOf(Math.round(tdsAmt))), Tempbd, Tempbd, user, brCode, brCode, 0,
                            TDS1, trsNumber, recNo1, 0, "FROMFD", auth, authBy, "20", 3, "", null, null, null, null, null, null, null, tokenNo, "N", "", "", "");
                    if (!(checkListP.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                        throw new ApplicationException(checkListP);
                    }
                }
            }

            List selectQuery1 = em.createNativeQuery("select TDSDeducted From td_vouchmst Where Acno='" + acno + "' and voucherno=" + rtNumber + " ").getResultList();
            if ((selectQuery1.isEmpty())) {
                Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET TDSDeducted=" + tdsToBeDeducted + " WHERE ACNO='" + acno + "' and voucherno=" + rtNumber + " ");
                int varQ1 = updateQ1.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
            } else {
                Vector deduct = (Vector) selectQuery1.get(0);
                String dt = deduct.get(0).toString();
                double srt1 = Double.parseDouble(dt);
                double txtTd1 = srt1 + tdsToBeDeducted;
                Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET TDSDeducted=" + txtTd1 + " WHERE ACNO='" + acno + "' and voucherno=" + rtNumber + " ");
                int varQ1 = updateQ1.executeUpdate();
                if (varQ1 <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
            }

            if (!autoPayFlag.equalsIgnoreCase("Y")) {
                String query = "update td_payment_auth set auth='" + auth + "',authBy='" + authBy + "' where acno = '" + acno + "' and voucherNo = " + rtNumber;
                int rs = em.createNativeQuery(query).executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            }
            if (autoPayFlag.equalsIgnoreCase("Y")) {
                return "true:" + trsNumber;
            }
            if (remainingInterest != 0) {
                return "Deposit has been marked as closed And Transfer Batch No." + trsNumber;
            } else {
                return "Deposit has been marked as closed";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<Double> tdGlobal(String acNo, float rtNo, String maturePayment,
            float nROI, int tdcumuPrematureFlag, String year, String mon, String day, String tdType) throws ApplicationException {

        try {
            String brCode = ftsPostMgmtRepote.getCurrentBrnCode(acNo);
            String bussnesDt = ymd.format(new Date());
            List tmpseries = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode ='" + brCode + "'").getResultList();

            if (!tmpseries.isEmpty()) {
                Vector elebal = (Vector) tmpseries.get(0);
                bussnesDt = elebal.get(0).toString();
            }

            String finYr = getFinYear(brCode);
            String frmDT = finYr + "0401";
            String toDT = (Integer.parseInt(finYr) + 1) + "0331";

            List selectQueryInttPaid = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where acno='" + acNo
                    + "' AND voucherno=" + rtNo + " ").getResultList();
            double vchTotalIntPaid = 0;

            if (!selectQueryInttPaid.isEmpty()) {
                Vector Intpaid = (Vector) selectQueryInttPaid.get(0);
                vchTotalIntPaid = Double.parseDouble(Intpaid.get(0).toString());
            }

            List selectQueryintopt = em.createNativeQuery("SELECT intopt,date_format(fddt,'%Y%m%d'),date_format(matdt,'%Y%m%d'),roi,prinamt,"
                    + "date_format(NextIntPayDt,'%Y%m%d'), CumuPrinAmt,period FROM td_vouchmst WHERE acno='" + acNo + "' and voucherno=" + rtNo).getResultList();
            Vector intoptVec = (Vector) selectQueryintopt.get(0);
            String intopt = intoptVec.get(0).toString();
            String fddt = intoptVec.get(1).toString();
            String matdt = intoptVec.get(2).toString();

            Float roi = Float.parseFloat(intoptVec.get(3).toString());
            double prinAmt = Double.parseDouble(intoptVec.get(4).toString());
            String nextIntPayDT = intoptVec.get(5).toString();

            double cumuPrinAmt = Double.parseDouble(intoptVec.get(6).toString());
            String pr = intoptVec.get(7).toString();

            long fullDt1 = CbsUtil.dayDiff(ymd.parse(fddt), ymd.parse(bussnesDt));
            long fullDtM1 = fullDt1 % 365;
            String period1 = String.valueOf(fullDt1 / 365) + "Years" + String.valueOf(fullDtM1 / 30) + "Months" + String.valueOf(fullDtM1 % 30) + "Days";

            List selectQueryFinYear = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from td_interesthistory where acno='" + acNo + "' AND voucherno=" + rtNo + " AND dt between '" + frmDT + "' and '" + toDT + "'").getResultList();
            double vchFinYearIntPaid = 0;

            if (!selectQueryFinYear.isEmpty()) {
                Vector finYear = (Vector) selectQueryFinYear.get(0);
                vchFinYearIntPaid = Double.parseDouble(finYear.get(0).toString());
            }

//            List selectQuerytdAmt = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tdshistory where acno='" + acNo + "' and voucherno=" 
//                    + rtNo + " AND dt between '" + frmDT + "' and '" + toDT + "'").getResultList();
//            double vchFinYearTdsAmt = 0;
//
//            if (!selectQuerytdAmt.isEmpty()) {
//                Vector tdAmount = (Vector) selectQuerytdAmt.get(0);
//                vchFinYearTdsAmt = Double.parseDouble(tdAmount.get(0).toString());
//            }
//            
            List selectQuerytdsLast = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory where acno='" + acNo + "' AND "
                    + "voucherno=" + rtNo + " AND dt < '" + frmDT + "'").getResultList();
            double lastYearTdsDeducted = 0;
            if (selectQuerytdsLast.size() > 0) {
                Vector last = (Vector) selectQuerytdsLast.get(0);
                lastYearTdsDeducted = Double.parseDouble(last.get(0).toString());
            }

            List selectQuerytdsDeducted = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory WHERE acno='" + acNo + "' AND "
                    + "voucherno=" + rtNo + " and dt >= '" + frmDT + "'").getResultList();
            double finYearTdsDeducted = 0;
            if (selectQuerytdsDeducted.size() > 0) {
                Vector tdsDeduct = (Vector) selectQuerytdsDeducted.get(0);
                finYearTdsDeducted = Double.parseDouble(tdsDeduct.get(0).toString());
            }
            double balIntt = 0;
            double vchTotalIntt = 0;

            List selectQueryAnytdsLast = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory where acno='" + acNo + "' AND "
                    + "voucherno=" + rtNo + "").getResultList();
            double lastTdsDeducted = 0;
            if (selectQueryAnytdsLast.size() > 0) {
                Vector lastAnyTds = (Vector) selectQueryAnytdsLast.get(0);
                lastTdsDeducted = Double.parseDouble(lastAnyTds.get(0).toString());
            }

            //if (intopt.equalsIgnoreCase("C") && maturePayment.equalsIgnoreCase("True") && finYearTdsDeducted > 0) {
            if (intopt.equalsIgnoreCase("C") && maturePayment.equalsIgnoreCase("True") && lastTdsDeducted > 0) {
                long dateDiff = CbsUtil.dayDiff(ymd.parse(nextIntPayDT), ymd.parse(matdt));
                if (dateDiff <= 0) {
                    balIntt = 0;
                } else {
                    //String bAInt = tdRcptMgmtRemote.orgFDInterest(intopt, roi, nextIntPayDT, matdt, cumuPrinAmt, pr, acNo.substring(0, 2));
                    String bAInt = tdRcptMgmtRemote.orgFDInterestNew(intopt, roi, nextIntPayDT, matdt, cumuPrinAmt, pr, acNo.substring(0, 2),fddt);
                    balIntt = Double.parseDouble(bAInt);
                }
                double vchTotalCalInt = Double.parseDouble(tdRcptMgmtRemote.orgFDInterest(intopt, roi, fddt, matdt, prinAmt, pr, acNo.substring(0, 2)));
                
                vchFinYearIntPaid = vchFinYearIntPaid + balIntt;
                vchTotalIntt = vchTotalIntPaid + balIntt;
                
                if((vchTotalIntt) > (vchTotalCalInt)){
                    vchTotalIntt = vchTotalCalInt;
                }

            } else if (!maturePayment.equalsIgnoreCase("False")) {
                String orgFd = tdRcptMgmtRemote.orgFDInterest(intopt, roi, fddt, matdt, prinAmt, pr, acNo.substring(0, 2));
                balIntt = Double.parseDouble(orgFd);

                vchFinYearIntPaid = vchFinYearIntPaid + (balIntt - vchTotalIntPaid);
                vchTotalIntt = balIntt;
            }
            if (maturePayment.equalsIgnoreCase("False")) {
                if (intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("S") || intopt.equalsIgnoreCase("Q") || intopt.equalsIgnoreCase("Y")) {
                    long diff = CbsUtil.dayDiff(ymd.parse(fddt), ymd.parse(bussnesDt));
                    vchTotalIntt = prinAmt * nROI * diff / 36500;
                    vchFinYearIntPaid = vchFinYearIntPaid + (vchTotalIntt - vchTotalIntPaid);
                } else if (intopt.equalsIgnoreCase("C")) {
                    List selectQuerydiff = em.createNativeQuery("select tddaycum from tdcondition WHERE applicable_date=(SELECT MAX(applicable_date) "
                            + "FROM  tdcondition WHERE applicable_date <= '" + fddt + "')").getResultList();
                    if (selectQuerydiff.size() > 0) {
                        Vector cum = (Vector) selectQuerydiff.get(0);
                        int tdDayCum = Integer.parseInt(cum.get(0).toString());

                        long diff = CbsUtil.dayDiff(ymd.parse(fddt), ymd.parse(bussnesDt));
                        if (diff > tdDayCum) {
                            List selQueryDays = em.createNativeQuery("SELECT Code FROM parameterinfo_report where reportname ='TDCUMU_PREMATURE_FLAG'").getResultList();
                            if (selQueryDays.size() > 0) {
                                Vector last = (Vector) selQueryDays.get(0);
                                tdcumuPrematureFlag = Integer.parseInt(last.get(0).toString());
                            }

                            int tdCumuPreQDays = 365;
                            if (tdcumuPrematureFlag == 1) {
                                tdCumuPreQDays = 91;
                            }
                            if (diff < tdCumuPreQDays) {
                                vchTotalIntt = prinAmt * nROI * ((float) diff / 36500);
                            } else {
                                String orgFd = tdRcptMgmtRemote.orgFDInterest(intopt, nROI, fddt, bussnesDt, prinAmt, period1, acNo.substring(0, 2));
                                vchTotalIntt = Double.parseDouble(orgFd);
                            }
                        } else {
                            vchTotalIntt = prinAmt * nROI * ((float) diff / 36500);
                        }
                    } else {
                        String orgFd = tdRcptMgmtRemote.orgFDInterest(intopt, nROI, fddt, bussnesDt, prinAmt, period1, acNo.substring(0, 2));
                        vchTotalIntt = Double.parseDouble(orgFd);
                    }
                    vchFinYearIntPaid = vchFinYearIntPaid + (vchTotalIntt - vchTotalIntPaid);
                }
            }

            double TmpTdSIntTot = 0;
            int tDays = 0;
            long dtDiff = 0;
            int tdPaymentFlag = ftsPostMgmtRepote.getCodeForReportName("TD-PAYMENT-SBINT");
            if (tdType.equalsIgnoreCase("TDPAYMENT") && tdPaymentFlag == 1) {
                dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(matdt, 0)), new Date());
                //int tdPaymentFlag = ftsPostMgmtRepote.getCodeForReportName("TD-PAYMENT-SBINT");
                if (dtDiff > tDays) {
                    String sbIntTabCode = "";
                    List setIntTableCode = em.createNativeQuery("SELECT ifnull(Code,'') FROM cbs_parameterinfo where name ='SAVING_INTTABLE_CODE'").getResultList();
                    if (setIntTableCode.size() > 0) {
                        Vector intTabCodeVec = (Vector) setIntTableCode.get(0);
                        sbIntTabCode = intTabCodeVec.get(0).toString();
                    }
                    List<SavingIntRateChangePojo> dataList = sbIntFacade.getSavingRoiChangeDetail(sbIntTabCode, matdt, ymd.format(new Date()));
                    if (dataList.isEmpty()) {
                        throw new ApplicationException("There is no slab for saving interest calculation.");
                    }
                    double fnlTmpInt = 0;
                    if (intopt.equalsIgnoreCase("Y") || intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) {
                        fnlTmpInt = (prinAmt + (vchTotalIntt - finYearTdsDeducted - lastYearTdsDeducted - vchTotalIntPaid));
                    } else {
                        fnlTmpInt = (prinAmt + vchTotalIntt - finYearTdsDeducted - lastYearTdsDeducted);
                    }
                    for (int k = 0; k < dataList.size(); k++) {
                        double rateOfInt = 0;
                        Long dDiff = null;
                        SavingIntRateChangePojo obj = dataList.get(k);
                        String slabFrDt = obj.getFrDt();
                        String slabToDt = obj.getToDt();
                        rateOfInt = obj.getRoi();
                        dDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
                        double sInt = (fnlTmpInt * rateOfInt * dDiff / 36500);
                        //fnlTmpInt = fnlTmpInt + sInt;
                        TmpTdSIntTot = TmpTdSIntTot + sInt;
                    }
//                
//                float tRoi = 0;
//                List selectQueryTRoi = em.createNativeQuery("SELECT Code FROM cbs_parameterinfo where name ='TD_SB_INT'").getResultList();
//                if (selectQueryTRoi.size() > 0) {
//                    Vector last = (Vector) selectQueryTRoi.get(0);
//                    tRoi = Float.parseFloat(last.get(0).toString());
//                }

//                TmpTdSIntTot = ((prinAmt + balIntt) * tRoi * dtDiff / 36500);
                    vchFinYearIntPaid = vchFinYearIntPaid + TmpTdSIntTot;
                    vchTotalIntt = vchTotalIntt + TmpTdSIntTot;
                }
            } else {
                List selQueryDays = em.createNativeQuery("SELECT Code FROM parameterinfo_report where reportname ='TD_RENEWAL_DAYS'").getResultList();
                if (selQueryDays.size() > 0) {
                    Vector last = (Vector) selQueryDays.get(0);
                    tDays = Integer.parseInt(last.get(0).toString());
                }
                //long dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(matdt, 1)), ymd.parse(CbsUtil.dateAdd(ymd.format(new Date()), -1)));
                dtDiff = CbsUtil.dayDiff(ymd.parse(CbsUtil.dateAdd(matdt, 1)), new Date());
                if (dtDiff > tDays) {
                    String sbIntTabCode = "";
                    List setIntTableCode = em.createNativeQuery("SELECT ifnull(Code,'') FROM cbs_parameterinfo where name ='SAVING_INTTABLE_CODE'").getResultList();
                    if (setIntTableCode.size() > 0) {
                        Vector intTabCodeVec = (Vector) setIntTableCode.get(0);
                        sbIntTabCode = intTabCodeVec.get(0).toString();
                    }

                    List<SavingIntRateChangePojo> dataList = sbIntFacade.getSavingRoiChangeDetail(sbIntTabCode, matdt, ymd.format(new Date()));
                    if (dataList.isEmpty()) {
                        throw new ApplicationException("There is no slab for saving interest calculation.");
                    }

                    double fnlTmpInt = 0;
                    if (intopt.equalsIgnoreCase("Y") || intopt.equalsIgnoreCase("M") || intopt.equalsIgnoreCase("Q")) {
                        fnlTmpInt = (prinAmt + (vchTotalIntt - finYearTdsDeducted - lastYearTdsDeducted - vchTotalIntPaid));
                    } else {
                        fnlTmpInt = (prinAmt + vchTotalIntt - finYearTdsDeducted - lastYearTdsDeducted);
                    }

                    for (int k = 0; k < dataList.size(); k++) {
                        double rateOfInt = 0;
                        Long dDiff = null;
                        SavingIntRateChangePojo obj = dataList.get(k);
                        String slabFrDt = obj.getFrDt();
                        String slabToDt = obj.getToDt();
                        rateOfInt = obj.getRoi();
                        dDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
                        double sInt = (fnlTmpInt * rateOfInt * dDiff / 36500);
                        //fnlTmpInt = fnlTmpInt + sInt;
                        TmpTdSIntTot = TmpTdSIntTot + sInt;
                    }
//                
//                float tRoi = 0;
//                List selectQueryTRoi = em.createNativeQuery("SELECT Code FROM cbs_parameterinfo where name ='TD_SB_INT'").getResultList();
//                if (selectQueryTRoi.size() > 0) {
//                    Vector last = (Vector) selectQueryTRoi.get(0);
//                    tRoi = Float.parseFloat(last.get(0).toString());
//                }

//                TmpTdSIntTot = ((prinAmt + balIntt) * tRoi * dtDiff / 36500);
                    vchFinYearIntPaid = vchFinYearIntPaid + TmpTdSIntTot;
                    vchTotalIntt = vchTotalIntt + TmpTdSIntTot;
                }
            }

            String strResult = getTdsRateAndApplicableAmt(acNo, bussnesDt);
            String[] strRsArr = strResult.split(":");
            String tdsFlag = strRsArr[0];

            float tdsRate = Float.parseFloat(strRsArr[1]);

            float tdsApplicableAmt = Float.parseFloat(strRsArr[2]);

            /*List selectQuerytdsLast = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory where acno='" + acNo + "' AND "
             + "voucherno=" + rtNo + " AND dt < '" + frmDT + "'").getResultList();
             double lastYearTdsDeducted = 0;
             if (selectQuerytdsLast.size() > 0) {
             Vector last = (Vector) selectQuerytdsLast.get(0);
             lastYearTdsDeducted = Double.parseDouble(last.get(0).toString());
             }

             List selectQuerytdsDeducted = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory WHERE acno='" + acNo + "' AND "
             + "voucherno=" + rtNo + " and dt > '" + frmDT + "'").getResultList();
             double finYearTdsDeducted = 0;
             if (selectQuerytdsDeducted.size() > 0) {
             Vector tdsDeduct = (Vector) selectQuerytdsDeducted.get(0);
             finYearTdsDeducted = Double.parseDouble(tdsDeduct.get(0).toString());
             }*/
            double tdsToBeDeducted = 0;
            //float closeFinYearTdsUnRecovered = 0;
            double closeFinYearIntOfCustomer = 0;

            double closeAcctTdsToBeDeduted = 0;
            double closeFinYearTdsRecovered = 0;
            //case when tds did not deducted on the particular customer
            if (getCustomerFinYearTds(acNo, frmDT, toDT, "R", "") == 0) {
                //float customerPostedFinYearInt = getFinYearIntOfCustomer(acNo, frmDT, toDT,"N");
                double customerFinYearInt = getFinYearIntOfCustomer(acNo, frmDT, toDT, "") + (vchTotalIntt - vchTotalIntPaid);
                double closeFinYearCalculatedTds = 0;

                if ((tdsFlag.equalsIgnoreCase("Y") && (customerFinYearInt > tdsApplicableAmt))
                        || (tdsFlag.equalsIgnoreCase("Y") && ((customerFinYearInt + getMajorOrMinorInt(acNo, frmDT, toDT) + getPropInterest(acNo, frmDT, toDT)) > tdsApplicableAmt))) {
                    double tdsCal = Math.round(vchFinYearIntPaid * tdsRate / 100);
                    tdsToBeDeducted = tdsCal - finYearTdsDeducted;

                    closeFinYearIntOfCustomer = getFinYearIntOfCustomer(acNo, frmDT, toDT, "Y");
                    closeFinYearCalculatedTds = Math.round(closeFinYearIntOfCustomer * tdsRate / 100);

                    closeFinYearTdsRecovered = getCustomerFinYearTds(acNo, frmDT, toDT, "R", "Y");

                    closeAcctTdsToBeDeduted = closeFinYearCalculatedTds - closeFinYearTdsRecovered;
                }
            } else {
                if (tdsFlag.equalsIgnoreCase("Y")) {
                    if (finYearTdsDeducted == 0) {
                        tdsToBeDeducted = Math.round(vchFinYearIntPaid * tdsRate / 100);
                    } else {
                        /*After discussion with Dhiru sir */
                        String tdsPostEnable = "";
                        List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
                        if (!tdsPostEnableList.isEmpty()) {
                            Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                            tdsPostEnable = tdsPostEnableVect.get(0).toString();
                        }
                        /*After discussion with Dhiru sir */
                        if (intopt.equalsIgnoreCase("M") && tdsPostEnable.equalsIgnoreCase("Y")) {
                            double mIntPaid = 0;
//                            List monIntPaid = em.createNativeQuery("select ifnull(sum(td.interest),0) from tdshistory tv, "
//                                    + " td_interesthistory td  where tv.acno = '" + acNo + "' and tv.voucherno = " + rtNo + " "
//                                    + " and td.acno = tv.acno and td.voucherno = tv.voucherno and td.dt > (select max(dt) from tdshistory "
//                                    + " where acno = '" + acNo + "' and voucherno = " + rtNo + ")").getResultList();
                            List monIntPaid = em.createNativeQuery("select ifnull(sum(interest),0) from "
                                    + " td_interesthistory where acno = '" + acNo + "' and voucherno = " + rtNo + " "
                                    + " and dt > (select max(dt) from tdshistory "
                                    + " where acno = '" + acNo + "' and voucherno = " + rtNo + ")").getResultList();
                            if (!monIntPaid.isEmpty()) {
                                Vector mIntVec = (Vector) monIntPaid.get(0);
                                mIntPaid = Double.parseDouble(mIntVec.get(0).toString());
                                tdsToBeDeducted = Math.round((mIntPaid + (vchTotalIntt - vchTotalIntPaid)) * tdsRate / 100);
                            }
                        } else {
                            if (vchTotalIntt - vchTotalIntPaid > 0) {
                                tdsToBeDeducted = Math.round((vchTotalIntt - vchTotalIntPaid) * tdsRate / 100);
                            }
                        }
                    }
                    int activeVouchNr = 0;
                    List activeVouchUnRecoverList = em.createNativeQuery("SELECT count(*) FROM tds_reserve_history a,td_vouchmst b where  recovered = 'NR' and dt between '" + frmDT + "' and '" + toDT + "'"
                            + "and a.acno = b.acno and a.VoucherNo = b.VoucherNo and status = 'A'"
                            + "and a.acno in(select acno from customerid where custid in(select custid from customerid where acno = '" + acNo + "'))").getResultList();
                    if (!activeVouchUnRecoverList.isEmpty()) {
                        Vector vtr = (Vector) activeVouchUnRecoverList.get(0);
                        activeVouchNr = Integer.parseInt(vtr.get(0).toString());
                    }
                    if (activeVouchNr > 1) {
                        List tdsList = em.createNativeQuery("SELECT TDS FROM tds_reserve_history a,td_vouchmst b where  recovered = 'NR' and dt between '" + frmDT + "' and '" + toDT + "' "
                                + "and a.acno = b.acno and a.VoucherNo = b.VoucherNo and status = 'A'and a.acno ='" + acNo + "' and a.VoucherNo =" + rtNo).getResultList();
                        if (!tdsList.isEmpty()) {
                            Vector vtr1 = (Vector) tdsList.get(0);
                            closeAcctTdsToBeDeduted = Double.parseDouble(vtr1.get(0).toString());
                        }
                        closeAcctTdsToBeDeduted = closeAcctTdsToBeDeduted + getCustomerFinYearTds(acNo, frmDT, toDT, "NR", "Y");
                    } else {
                        List tdsList = em.createNativeQuery("SELECT TDS FROM tds_reserve_history a,td_vouchmst b where  recovered = 'NR' and dt between '" + frmDT + "' and '" + toDT + "' "
                                + "and a.acno = b.acno and a.VoucherNo = b.VoucherNo and status = 'A'and a.acno ='" + acNo + "' and a.VoucherNo =" + rtNo).getResultList();
                        if (!tdsList.isEmpty()) {
                            Vector vtr1 = (Vector) tdsList.get(0);
                            closeAcctTdsToBeDeduted = Double.parseDouble(vtr1.get(0).toString());
                        }

                        closeAcctTdsToBeDeduted = closeAcctTdsToBeDeduted + getCustomerFinYearTds(acNo, frmDT, toDT, "NR", "Y");
                    }
                }
            }
            if (tdsToBeDeducted < 0) {
                tdsToBeDeducted = 0;
            }
            List<Double> result = new ArrayList<Double>();
            result.add(finYearTdsDeducted);
            result.add(lastYearTdsDeducted);
            result.add(tdsToBeDeducted);

            result.add(vchTotalIntt);
            result.add(vchTotalIntPaid);

            result.add(closeAcctTdsToBeDeduted);
            result.add(closeFinYearTdsRecovered);
            result.add(closeFinYearIntOfCustomer);
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getFinYear(String brCode) throws ApplicationException {
        String fYear;
        String tmpDate = ymd.format(new Date());
        try {
            List tmpseries = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' and brncode = '" + brCode + "'").getResultList();
            if (tmpseries.size() > 0) {
                Vector elebal = (Vector) tmpseries.get(0);
                tmpDate = elebal.get(0).toString();
            }
            List selectQuery = em.createNativeQuery("SELECT f_year FROM yearend where mindate<= '" + tmpDate + "' and maxdate >= '" + tmpDate + "' "
                    + "and brncode='" + brCode + "'").getResultList();
            if (selectQuery.size() > 0) {
                Vector dateVect = (Vector) selectQuery.get(0);
                fYear = dateVect.get(0).toString();
            } else {
                fYear = "0";
            }
            return fYear;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getTdsRateAndApplicableAmt(String acNo, String bussnesDt) throws ApplicationException {
        try {
            int tmpOrgCode = 3;
            String tdsFlag = "Y";
            int panLen = 0;
            String custType = "OT";
            String accCat = "";
            String acNat = ftsPostMgmtRepote.getAccountNature(acNo);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List selectQuerytdsFlag = em.createNativeQuery("SELECT ifnull(TDSFlag,'Y'),ifnull(orgncode,3),ifnull(cust_type,'OT'),acctCategory FROM td_accountmaster where Acno='" + acNo + "'").getResultList();
                if (!selectQuerytdsFlag.isEmpty()) {
                    Vector tdsflg = (Vector) selectQuerytdsFlag.get(0);
                    tdsFlag = tdsflg.get(0).toString();
                    tmpOrgCode = Integer.parseInt(tdsflg.get(1).toString());
                    custType = tdsflg.get(2).toString();
                    accCat = tdsflg.get(3).toString();
                    if (tmpOrgCode > 3) {
                        tmpOrgCode = 3;
                    }
                }
            } else {
                List selectQuerytdsFlag = em.createNativeQuery("SELECT ifnull(TDSFlag,'Y'),ifnull(orgncode,3),ifnull(cust_type,'OT'),acctCategory FROM accountmaster where Acno='" + acNo + "'").getResultList();
                if (!selectQuerytdsFlag.isEmpty()) {
                    Vector tdsflg = (Vector) selectQuerytdsFlag.get(0);
                    tdsFlag = tdsflg.get(0).toString();
                    tmpOrgCode = Integer.parseInt(tdsflg.get(1).toString());
                    custType = tdsflg.get(2).toString();
                    accCat = tdsflg.get(3).toString();
                    if (tmpOrgCode > 3) {
                        tmpOrgCode = 3;
                    }
                }
            }

            List cIdLst = em.createNativeQuery("select custid from customerid where acno ='" + acNo + "'").getResultList();
            if (cIdLst.isEmpty()) {
                throw new ApplicationException("Customer Id is not Created For " + acNo);
            }
            Vector cIdListVec = (Vector) cIdLst.get(0);
            String cId = cIdListVec.get(0).toString();

            List mjOMinFlagLst = em.createNativeQuery("select minorflag,ifnull(CustEntityType,'03') from cbs_customer_master_detail where customerid ='" + cId + "'").getResultList();
            if (mjOMinFlagLst.isEmpty()) {
                throw new ApplicationException("minorFlag is not Set For " + cId);
            }
            Vector mjOMinVec = (Vector) mjOMinFlagLst.get(0);
            String mFlag = mjOMinVec.get(0).toString();
            String cEntType = mjOMinVec.get(1).toString();

            List selectQuerPan;
            if (mFlag.equalsIgnoreCase("Y")) {
                selectQuerPan = em.createNativeQuery("select length(ifnull(ci.PAN_GIRNumber,0)) from cbs_customer_master_detail ci,"
                        + " cbs_cust_minorinfo cm where ci.customerid = cm.guardiancode "
                        + " and cm.customerid = '" + cId + "'").getResultList();
            } else {
                selectQuerPan = em.createNativeQuery("select length(ifnull(PAN_GIRNumber,0)) from cbs_customer_master_detail "
                        + " where customerid = '" + cId + "'").getResultList();
            }

            if (!selectQuerPan.isEmpty()) {
                Vector vPan = (Vector) selectQuerPan.get(0);
                panLen = Integer.parseInt(vPan.get(0).toString());
            }

            String query = "select date_format(cm.DateOfBirth,'%Y%m%d') from customerid ci, cbs_customer_master_detail cm where "
                    + "ci.acno='" + acNo + "' and ci.CustId = cm.customerid";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Data does not exist in Customer Master");
            }
            Vector vect = (Vector) dataList.get(0);
            String dob = vect.get(0).toString();
            String srCiznFlag = "N";
            int dblBenifitAge = 0;
            if (custType.equalsIgnoreCase("SC")) {
                if (cEntType.equalsIgnoreCase("01")) {
                    srCiznFlag = "Y";
                }
            } else {
                if (cEntType.equalsIgnoreCase("01")) {
                    dblBenifitAge = ftsPostMgmtRepote.getCodeForReportName("DOUBLE-BENIFIT-AGE");
                    if (CbsUtil.yearDiff(ymd.parse(dob), ymd.parse(bussnesDt)) >= dblBenifitAge) {
                        srCiznFlag = "Y";
                    } else {
                        srCiznFlag = "N";
                    }
                }
            }

            List selectQuer65;
            if (srCiznFlag.equalsIgnoreCase("Y")) {
                selectQuer65 = em.createNativeQuery("select ifnull(Srctzn_Tds_Amount,-1),ifnull(tds_rate *(1 + tds_surcharge /100.0),-1),tdsrate_pan from "
                        + "tdsslab where type=" + tmpOrgCode + " and tds_applicabledate in(select max(tds_applicabledate) from tdsslab where "
                        + "tds_applicabledate<='" + bussnesDt + "' and type=" + tmpOrgCode + ")").getResultList();
            } else {
                selectQuer65 = em.createNativeQuery("select ifnull(tds_amount,-1),ifnull(tds_rate *(1 + tds_surcharge /100.0),-1),tdsrate_pan from "
                        + "tdsslab where type=" + tmpOrgCode + " and tds_applicabledate in(select max(tds_applicabledate) from tdsslab where "
                        + "tds_applicabledate<='" + bussnesDt + "' and type=" + tmpOrgCode + ")").getResultList();
            }
            Vector v65 = (Vector) selectQuer65.get(0);

            float tdsApplicableAmt = Float.parseFloat(v65.get(0).toString());
            if (tdsApplicableAmt == -1) {
                throw new ApplicationException("Tds applicable amount does not exist in tds slab. So please fill tds slab.");
            }

            float tdsRate = 0;

            if (panLen == 10) {
                tdsRate = Float.parseFloat(v65.get(1).toString());
            } else {
                tdsRate = Float.parseFloat(v65.get(2).toString());
            }

            if (tdsRate == -1) {
                throw new ApplicationException("Tds applicable rate does not exist in tds slab. So please fill tds slab.");
            }
            return tdsFlag + ":" + tdsRate + ":" + tdsApplicableAmt;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

//    public String getTdsRateAndApplicableAmt(String acNo, String bussnesDt) throws ApplicationException {
//        try {
//            int tmpOrgCode = 3;
//            String tdsFlag = "Y";
//            int panLen = 0;
//            String acNat = ftsPostMgmtRepote.getAccountNature(acNo);
//            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                List selectQuerytdsFlag = em.createNativeQuery("SELECT ifnull(TDSFlag,'Y'),ifnull(orgncode,3) FROM td_accountmaster where Acno='" + acNo + "'").getResultList();
//                if (!selectQuerytdsFlag.isEmpty()) {
//                    Vector tdsflg = (Vector) selectQuerytdsFlag.get(0);
//                    tdsFlag = tdsflg.get(0).toString();
//                    tmpOrgCode = Integer.parseInt(tdsflg.get(1).toString());
//                    if (tmpOrgCode > 3) {
//                        tmpOrgCode = 3;
//                    }
//                }
//
//                List selectQuerPan = em.createNativeQuery("SELECT length(ifnull(tc.panno,0)) FROM td_customermaster tc WHERE tc.custno =SUBSTRING('"
//                        + acNo + "',5,6) AND tc.actype = SUBSTRING('" + acNo + "',3,2) AND tc.brncode = SUBSTRING('" + acNo + "',1,2)").getResultList();
//                Vector vPan = (Vector) selectQuerPan.get(0);
//                panLen = Integer.parseInt(vPan.get(0).toString());
//            } else {
//                List selectQuerytdsFlag = em.createNativeQuery("SELECT ifnull(TDSFlag,'Y'),ifnull(orgncode,3) FROM accountmaster where Acno='" + acNo + "'").getResultList();
//                if (!selectQuerytdsFlag.isEmpty()) {
//                    Vector tdsflg = (Vector) selectQuerytdsFlag.get(0);
//                    tdsFlag = tdsflg.get(0).toString();
//                    tmpOrgCode = Integer.parseInt(tdsflg.get(1).toString());
//                    if (tmpOrgCode > 3) {
//                        tmpOrgCode = 3;
//                    }
//                }
//
//                List selectQuerPan = em.createNativeQuery("SELECT length(ifnull(tc.panno,0)) FROM customermaster tc WHERE tc.custno =SUBSTRING('"
//                        + acNo + "',5,6) AND tc.actype = SUBSTRING('" + acNo + "',3,2) AND tc.brncode = SUBSTRING('" + acNo + "',1,2) and tc.agcode = SUBSTRING('" + acNo + "',11,2)").getResultList();
//                Vector vPan = (Vector) selectQuerPan.get(0);
//                panLen = Integer.parseInt(vPan.get(0).toString());
//            }
//
//            List selectQuer65 = em.createNativeQuery("select ifnull(tds_amount,-1),ifnull(tds_rate *(1 + tds_surcharge /100.0),-1),tdsrate_pan from "
//                    + "tdsslab where type=" + tmpOrgCode + " and tds_applicabledate in(select max(tds_applicabledate) from tdsslab where "
//                    + "tds_applicabledate<='" + bussnesDt + "' and type=" + tmpOrgCode + ")").getResultList();
//            Vector v65 = (Vector) selectQuer65.get(0);
//
//            float tdsApplicableAmt = Float.parseFloat(v65.get(0).toString());
//            if (tdsApplicableAmt == -1) {
//                throw new ApplicationException("Tds applicable amount does not exist in tds slab. So please fill tds slab.");
//            }
//
//            float tdsRate = 0;
//
//            if (panLen == 10) {
//                tdsRate = Float.parseFloat(v65.get(1).toString());
//            } else {
//                tdsRate = Float.parseFloat(v65.get(2).toString());
//            }
//
//            if (tdsRate == -1) {
//                throw new ApplicationException("Tds applicable rate does not exist in tds slab. So please fill tds slab.");
//            }
//            return tdsFlag + ":" + tdsRate + ":" + tdsApplicableAmt;
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
    public double getCustomerFinYearTds(String acno, String fromDt, String toDt, String recover, String closeFlag) throws ApplicationException {
        try {
            String closeTdQuery = "";;
            String closeQuery = "";
            //Branch checking for only close account
            if (closeFlag.equals("Y")) {
                closeTdQuery = "and (tv.cldt >='" + fromDt + "') ";
                closeQuery = "and (ac.closingdate >='" + fromDt + "') ";
            }
            if (closeFlag.equals("N")) {
                closeTdQuery = "and (tv.cldt is null or tv.cldt ='') ";
                closeQuery = "and (ac.closingdate is null or ac.closingdate ='') ";
            }
            String query = "select sum(a.tds) from "
                    + "(select ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeTdQuery
                    + "and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + fromDt + "' and '" + toDt + "' and ti.recovered='" + recover + "' "
                    + "union all "
                    + "select ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeQuery
                    + "and ac.acno=ri.acno and ri.dt between '" + fromDt + "' and '" + toDt + "' and ri.recovered='" + recover + "') a";

            List dataList = em.createNativeQuery(query).getResultList();
            Vector tdsPaidVector = (Vector) dataList.get(0);
            return Float.parseFloat(tdsPaidVector.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getFinYearIntOfCustomer(String acno, String fromDt, String toDt, String closeFlag) throws ApplicationException {
        try {
            String closeTdQuery = "";;
            String closeQuery = "";
            //Branch checking for only close account
            if (closeFlag.equals("Y")) {
                closeTdQuery = "and (tv.cldt >='" + fromDt + "') ";
                closeQuery = "and (ac.closingdate >='" + fromDt + "') ";
            }
            if (closeFlag.equals("N")) {
                closeTdQuery = "and (tv.cldt is null or tv.cldt ='') ";
                closeQuery = "and (ac.closingdate is null or ac.closingdate ='') ";
            }
            String query = "select sum(a.interest) from "
                    + "(select ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeTdQuery
                    + "and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + fromDt + "' and '" + toDt + "' "
                    + "union all "
                    + "select ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeQuery
                    + "and ac.acno=ri.acno and ri.dt between '" + fromDt + "' and '" + toDt + "'"
                    + "union all "
                    + "select ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeQuery
                    + "and ac.acno=ri.acno and ri.dt between '" + fromDt + "' and '" + toDt + "' ) a";

            List dataList = em.createNativeQuery(query).getResultList();
            Vector intPaidVector = (Vector) dataList.get(0);
            return Double.parseDouble(intPaidVector.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnRecoverdTdsAccounts(String acno, String fromDt, String toDt, String recover, String closeFlag) throws ApplicationException {
        try {
            String closeTdQuery = "";;
            String closeQuery = "";
            //Branch checking for only close account
            if (closeFlag.equals("Y")) {
                closeTdQuery = "and (tv.cldt >='" + fromDt + "') ";
                closeQuery = "and (ac.closingdate >='" + fromDt + "') ";
            }
            if (closeFlag.equals("N")) {
                closeTdQuery = "and (tv.cldt is null or tv.cldt ='') ";
                closeQuery = "and (ac.closingdate is null or ac.closingdate ='') ";
            }
            String query = "select ti.acno,ti.voucherno,ti.intopt, DATE_FORMAT(ti.FromDt,'%Y%m%d') as fromDt, "
                    + "DATE_FORMAT(ti.ToDt,'%Y%m%d') as toDt, ifnull(ti.tds,0) as tds from customerid ci,td_vouchmst tv,"
                    + "tds_reserve_history ti where tv.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeTdQuery
                    + "and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between '" + fromDt + "' and '" + toDt + "' and ti.recovered='" + recover + "' "
                    + "union all "
                    + "select ti.acno,ti.voucherno,ti.intopt, DATE_FORMAT(ti.FromDt,'%Y%m%d') as fromDt, "
                    + "DATE_FORMAT(ti.ToDt,'%Y%m%d') as toDt, ifnull(ti.tds,0) as tds from customerid ci,accountmaster ac,"
                    + "tds_reserve_history ti where ac.acno=ci.acno "
                    + "and custid in (select custid from customerid where acno='" + acno + "') " + closeQuery
                    + "and ac.acno=ti.acno and ti.dt between '" + fromDt + "' and '" + toDt + "' and ti.recovered='" + recover + "'";

            List dataList = em.createNativeQuery(query).getResultList();

            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<SmsToBatchTo> autoLockerRentPosting(String tempBd) throws ApplicationException {

        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        try {

            List brnList = em.createNativeQuery("select bd.brncode from bankdays bd, branchmaster bm where Date='" + tempBd + "' and DayBeginFlag <> 'H' "
                    + "and cast(bd.brncode as unsigned) = bm.brncode").getResultList();
            if (brnList.isEmpty()) {
                throw new ApplicationException("Branch code is not defined");
            }
            //ut.begin();
            int staxModuleActive = 0;
            String taxFlag = "";
            List stTaxList = em.createNativeQuery("SELECT ifnull(code,0) FROM parameterinfo_report WHERE reportname='STAXMODULE_ACTIVE'").getResultList();
            if (!stTaxList.isEmpty()) {
                Vector stTaxListV = (Vector) stTaxList.get(0);
                staxModuleActive = Integer.parseInt(stTaxListV.get(0).toString());
                if (staxModuleActive == 1) {
                    taxFlag = "Y";
                } else {
                    taxFlag = "N";
                }
            } else {
                taxFlag = "N";
            }
            String tmpDetails = "VCH. Of Locker Rent";
            String glAcNo = "";
            String glAccNo = "";
            List glLst = em.createNativeQuery("select glheadmisc from parameterinfo_miscincome where purpose='Locker Rent'").getResultList();
            if (glLst.isEmpty()) {
                throw new ApplicationException("GL Head is not defined");
            }
            Vector glListVec = (Vector) glLst.get(0);
            glAccNo = glListVec.get(0).toString();

            for (int a = 0; a < brnList.size(); a++) {
                Vector brnListVector = (Vector) brnList.get(a);
                String brncode = brnListVector.get(0).toString();
//                if (brncode.length() < 2) {
//                    brncode = "0" + brncode;
//                }
                System.out.println("For Branch Code-->" + brncode + "\n");
                //List<LockerRentDetail> rentList = gridLoadForAllAccounts(brncode);

                List returnList = gridAutoLoadForAllAccounts(brncode);
                List<LockerRentDetail> rentList = new ArrayList<LockerRentDetail>();
                glAcNo = brncode + glAccNo + "01";

                for (int i = 0; i < returnList.size(); i++) {
                    Vector ele = (Vector) returnList.get(i);
                    LockerRentDetail detail = new LockerRentDetail();
                    detail.setCabno(ele.get(0) == null ? 0 : Float.parseFloat(ele.get(0).toString()));
                    detail.setLockertype(ele.get(1).toString());
                    detail.setLockerno(ele.get(2) == null ? 0 : Float.parseFloat(ele.get(2).toString()));

                    detail.setAcno(ele.get(3).toString());
                    detail.setCustname(ele.get(4).toString());
                    detail.setPenalty(ele.get(5) == null ? 0 : Double.parseDouble(ele.get(5).toString()));

                    detail.setRentduedt(ele.get(6).toString());
                    detail.setStatus(ele.get(7).toString());
                    detail.setBrCode(ele.get(8).toString());
                    detail.setCustState(ele.get(9).toString());
                    detail.setBrnchState(ele.get(10).toString());
                    detail.setAdvPayYr(ele.get(11).toString());
                    rentList.add(detail);
                }

                List<List> returnDataList = ftsDrCr.postLockerRent(rentList, glAcNo, "SYSTEM", tmpDetails, 35, taxFlag, "N", brncode);

                List<String> strList = (List<String>) returnDataList.get(0);

                if (!strList.get(0).substring(0, 4).equalsIgnoreCase("true")) {
                    if (strList.get(0).substring(0, 4).equalsIgnoreCase("flase")) {
                        throw new ApplicationException("Problem in Auto Locker Rent Posting");
                    } else {
                        throw new ApplicationException(strList.get(0));
                    }
                }
                smsBatchList = new ArrayList<SmsToBatchTo>();
                List<SmsToBatchTo> tempSmsList = (List<SmsToBatchTo>) returnDataList.get(1);
                for (SmsToBatchTo to : tempSmsList) {
                    smsBatchList.add(to);
                }
            }
            return smsBatchList;
        } catch (Exception e) {
            try {
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List gridAutoLoadForAllAccounts(String brCode) throws ApplicationException {
        List gridList = new ArrayList();
        try {
            gridList = em.createNativeQuery("Select aa.CabNo,aa.LockerType,aa.LockerNo,aa.Acno,aa.CustName,aa.Rent,"
                    + "aa.RentDueDt,aa.status,aa.curbrcode, bb.stateCode, bb.brState,aa.freq_year from"
                    + "(Select m.CabNo,m.LockerType,m.LockerNo,l.Acno,a.CustName,l.Rent,"
                    + "date_format(l.RentDueDt,'%d/%m/%Y') as RentDueDt,l.status,a.curbrcode,m.freq_year From lockeracmaster m,lockerrent l, accountmaster a "
                    + " Where rentDueDt<='" + ymd.format(new Date()) + "' And "
                    + "l.acno=a.acno and l.acno = m.acno And m.cabno=l.cabno and m.lockerType=l.lockerType and m.lockerno = l.lockerno and "
                    + "l.rent <> 0 and l.status='U' and l.BrnCode = '" + brCode + "' and a.accstatus=1 order by m.cabno,m.lockerno) aa,"
                    + " (select ci.Acno as acno, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState   "
                    + " from customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                    + " where ci.CustId = cast(cm.customerid as unsigned) and br.brncode=cast('" + brCode + "' as unsigned))  bb "
                    + " where aa.acno = bb.acno order by aa.cabno,aa.lockerno ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return gridList;
    }

    public double getMajorOrMinorInt(String acno, String fromDt, String toDt) throws ApplicationException {
        try {
            List cIdLst = em.createNativeQuery("select custid from customerid where acno ='" + acno + "'").getResultList();
            if (cIdLst.isEmpty()) {
                throw new ApplicationException("Customer Id is not Created For " + acno);
            }
            Vector cIdListVec = (Vector) cIdLst.get(0);
            String cId = cIdListVec.get(0).toString();

            List mjOMinFlagLst = em.createNativeQuery("select minorflag from cbs_customer_master_detail where customerid ='" + cId + "'").getResultList();
            if (mjOMinFlagLst.isEmpty()) {
                throw new ApplicationException("minorFlag is not Set For " + cId);
            }
            Vector mjOMinVec = (Vector) mjOMinFlagLst.get(0);
            String mFlag = mjOMinVec.get(0).toString();

            String query = "", queryRd = "", queryDs = "";
            if (mFlag.equalsIgnoreCase("Y")) {
                query = "select ifnull(sum(interest),0) from td_interesthistory where acno in "
                        + " (select ci.acno from customerid ci,cbs_cust_minorinfo cm where ci.custid = cast(cm.guardiancode as unsigned) "
                        + " and cm.customerid = '" + cId + "' and substring(ci.acno,3,2) in "
                        + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'";

                queryRd = "select ifnull(sum(interest),0) from rd_interesthistory where acno in "
                        + " (select ci.acno from customerid ci,cbs_cust_minorinfo cm where ci.custid = cast(cm.guardiancode as unsigned) "
                        + " and cm.customerid = '" + cId + "' and substring(ci.acno,3,2) in "
                        + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'";

                queryDs = "select ifnull(sum(interest),0) from dds_interesthistory where acno in "
                        + " (select ci.acno from customerid ci,cbs_cust_minorinfo cm where ci.custid = cast(cm.guardiancode as unsigned)"
                        + " and cm.customerid = '" + cId + "' and substring(ci.acno,3,2) in "
                        + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'";
            } else {
                query = "select ifnull(sum(interest),0) from td_interesthistory where acno in "
                        + " (select ci.acno from customerid ci,cbs_cust_minorinfo cm where ci.custid = cast(cm.customerid as unsigned)"
                        + " and cm.guardiancode = '" + cId + "' and substring(ci.acno,3,2) in "
                        + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'";

                queryRd = "select ifnull(sum(interest),0) from rd_interesthistory where acno in "
                        + " (select ci.acno from customerid ci,cbs_cust_minorinfo cm where ci.custid = cast(cm.customerid as unsigned) "
                        + " and cm.guardiancode = '" + cId + "' and substring(ci.acno,3,2) in "
                        + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'";

                queryDs = "select ifnull(sum(interest),0) from dds_interesthistory where acno in "
                        + " (select ci.acno from customerid ci,cbs_cust_minorinfo cm where ci.custid = cast(cm.customerid as unsigned) "
                        + " and cm.guardiancode = '" + cId + "' and substring(ci.acno,3,2) in "
                        + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'";
            }

            List dataList = em.createNativeQuery(query).getResultList();
            Vector intPaidVector = (Vector) dataList.get(0);

            List dataListRd = em.createNativeQuery(queryRd).getResultList();
            Vector intRdPaidVector = (Vector) dataListRd.get(0);

            List dataListDs = em.createNativeQuery(queryDs).getResultList();
            Vector intDsPaidVector = (Vector) dataListDs.get(0);

            return Double.parseDouble(intPaidVector.get(0).toString()) + Double.parseDouble(intRdPaidVector.get(0).toString())
                    + Double.parseDouble(intDsPaidVector.get(0).toString());
        } catch (Exception e) {
            try {
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public double getPropInterest(String acno, String fromDt, String toDt) throws ApplicationException {
        try {
            List cIdLst = em.createNativeQuery("select custid from customerid where acno ='" + acno + "'").getResultList();
            if (cIdLst.isEmpty()) {
                throw new ApplicationException("Customer Id is not Created For " + acno);
            }
            Vector cIdListVec = (Vector) cIdLst.get(0);
            String cId = cIdListVec.get(0).toString();

            String query = "select ifnull(sum(interest),0) from td_interesthistory where acno in "
                    + " (select ci.acno from customerid ci,td_accountmaster tm where ci.custid = tm.custid1 "
                    + " and tm.custid1 = '" + cId + "' and opermode = 8) and dt between '" + fromDt + "' and '" + toDt + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            Vector intPaidVector = (Vector) dataList.get(0);

            String queryRd = "select ifnull(sum(interest),0) from rd_interesthistory where acno in "
                    + " (select ci.acno from customerid ci, accountmaster tm where ci.custid = tm.custid1 "
                    + " and tm.custid1 = '" + cId + "' and opermode = 8 and substring(ci.acno,3,2) in "
                    + " (select acctcode from accounttypemaster where acctnature in ('RD'))) and dt between '" + fromDt + "' and '" + toDt + "'";
            List dataListRd = em.createNativeQuery(queryRd).getResultList();
            Vector intRdPaidVector = (Vector) dataListRd.get(0);

            String queryDs = "select ifnull(sum(interest),0) from dds_interesthistory where acno in "
                    + " (select ci.acno from customerid ci,accountmaster tm where ci.custid = tm.custid1 "
                    + " and tm.custid1 = '" + cId + "' and opermode = 8 and substring(ci.acno,3,2) in "
                    + " (select acctcode from accounttypemaster where acctnature in ('DS'))) and dt between '" + fromDt + "' and '" + toDt + "'";
            List dataListDs = em.createNativeQuery(queryDs).getResultList();
            Vector intDsPaidVector = (Vector) dataListDs.get(0);

            return Double.parseDouble(intPaidVector.get(0).toString()) + Double.parseDouble(intRdPaidVector.get(0).toString())
                    + Double.parseDouble(intDsPaidVector.get(0).toString());
        } catch (Exception e) {
            try {
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String tdRenewalAuthBalInt(String acctNo, double tdsToBeDeducted, float tmpVchNo, String optInterest, String rVoucherNo, double tmpBalInt,
            double matPre, double lastFinYearTds, double tmpIntTds, double tdsDeducted, double clActTdsToBeDeducted, String rStatus) throws ApplicationException {
        try {
            double provInt = 0;
            List finlamtList = em.createNativeQuery("SELECT DISTINCT acno,prinamt,voucherno,FDDt,status,matdt,IntOpt,roi,cldt,finalAmt,period,"
                    + "IntToAcNo,OFFlag,OFAcno,lien from td_vouchmst where status='" + rStatus + "' and voucherNo='" + rVoucherNo + "' and acno='" + acctNo
                    + "' UNION SELECT DISTINCT vm.acno,vm.prinamt,vm.voucherno,vm.FDDt,vm.status,vm.matdt,vm.IntOpt,vm.roi,vm.cldt,"
                    + "vm.finalAmt,vm.period,vm.IntToAcNo,vm.OFFlag,vm.OFAcno,lien from td_vouchmst vm where OFFlag='Y' and vm.status='C' "
                    + "and vm.voucherNo='" + rVoucherNo + "' and vm.acno='" + acctNo + "'").getResultList();
            if (finlamtList.size() <= 0) {
                throw new ApplicationException("Data Not exist in Td vouch master");
            }
            Vector tdVchList = (Vector) finlamtList.get(0);
            double prinamt = 0;
            if (tdVchList.get(1) != null) {
                prinamt = Double.parseDouble(tdVchList.get(1).toString());
            }

            double balInt = 0;
            if (optInterest.equalsIgnoreCase("C")) {
                balInt = CbsUtil.round(tmpBalInt, 0);
            }

            if (optInterest.equalsIgnoreCase("S")) {
                List balanList = em.createNativeQuery("SELECT ifnull(sum(Interest),0) FROM td_interesthistory WHERE VOUCHERNO=" + tmpVchNo + " and acno='" + acctNo + "'").getResultList();
                if (balanList.size() > 0) {
                    Vector balVec = (Vector) balanList.get(0);
                    provInt = Double.parseDouble(balVec.get(0).toString());
                }
                balInt = (matPre + tdsToBeDeducted + lastFinYearTds + tdsDeducted + clActTdsToBeDeducted) - provInt - prinamt;
                balInt = CbsUtil.round(balInt, 0);
            }

            if (optInterest.equalsIgnoreCase("Q") || optInterest.equalsIgnoreCase("M") || optInterest.equalsIgnoreCase("Y")) {
                List balanList = em.createNativeQuery("SELECT ifnull(sum(Interest),0) FROM td_interesthistory WHERE VOUCHERNO='" + rVoucherNo + "' and acno='" + acctNo + "'").getResultList();
                if (balanList.size() > 0) {
                    Vector balVec = (Vector) balanList.get(0);
                    provInt = Double.parseDouble(balVec.get(0).toString());
                }
                balInt = tmpIntTds - provInt;
                balInt = CbsUtil.round(balInt, 0);
            }

            String result = "";
            result = "" + balInt;
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String autoSeniorCitizenMarking(String dt, String userName) throws ApplicationException {
        try {
            double dblBenifitAge = ftsPostMgmtRepote.getCodeForReportName("SR-CITIZEN-AGE").doubleValue();
            List acNoList = em.createNativeQuery("select t.Acno,t.CurBrCode,t.Accttype,date_format(cd.DateOfBirth,'%Y%m%d') as DateOfBirth from td_accountmaster t, cbs_customer_master_detail cd, "
                    + "customerid c where t.cust_type <> 'SC' and t.acno = c.acno and c.custid = cast(cd.customerid as unsigned) and cd.custEntityType = '01' "
                    + "and t.accstatus <>9 and date_format(cd.DateOfBirth,'%Y%m%d') <> '19000101'"
                    + "union "
                    + "select t.Acno,t.CurBrCode,t.Accttype,date_format(cd.DateOfBirth,'%Y%m%d') as DateOfBirth from accountmaster t, cbs_customer_master_detail cd, customerid c where t.cust_type <> 'SC' "
                    + "and t.acno = c.acno and c.custid = cast(cd.customerid as unsigned) and cd.custEntityType = '01' "
                    + "and t.accstatus <>9 and date_format(cd.DateOfBirth,'%Y%m%d') <> '19000101' and t.accttype in "
                    + "(select acctcode from accounttypemaster where acctnature in ('RD','DS'))").getResultList();
            for (int k = 0; k < acNoList.size(); k++) {
                Vector acNoV = (Vector) acNoList.get(k);
                String acNo = acNoV.get(0).toString();
                String orgnBrCode = acNoV.get(1).toString();
                String acctType = acNoV.get(2).toString();
                String dob = acNoV.get(3).toString();

                if ((CbsUtil.yearDiff(ymd.parse(dob), ymd.parse(dt)) >= dblBenifitAge)) {
                    String acctNature = ftsPostMgmtRepote.getAccountNature(acNo);
                    if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        Query insertIntoAccEditHistory = em.createNativeQuery("insert into acedithistory (Acno,Name,OperMode,OrgnCode,EnteredBy,UpdateDt,Auth,AuthBy,"
                                + "introacno,FName,MAddress, PAddress,PhNo,PanNo,chBook,Nominee,Relationship,MinBalCharge,JtName1,JtName2,GName,AcInst, AppTDS,TDSDocu,"
                                + "IntOpt,JTNAME3,JtName4,IntToAcno,cust_type,acctCategory)select a.acno, c.custname , a.opermode, a.orgncode, " + "'" + userName + "',now() ,'Y', 'SYSTEM', a.introaccno, "
                                + "c.fathername, c.craddress, c.praddress,c.phoneno,c.panno,0,a.nomination,a.relationship,'2',a.jtname1, a.jtname2, c.grdname, "
                                + "a.instruction,tdsflag,tdsdetails,'', a.jtname3, a.jtname4,'',a.cust_type,a.acctCategory from td_accountmaster a , td_customermaster c "
                                + "where a.acno='" + acNo + "'and c.custno='" + acNo.substring(4, 10) + "'and c.brncode = '" + orgnBrCode + "' and substring(a.acno,5,6) = c.custno and a.accttype = c.actype");

                        int insertIntoAccEditHistoryResult = insertIntoAccEditHistory.executeUpdate();
                        if (insertIntoAccEditHistoryResult <= 0) {
                            return "Insertion Problem into acedithistory";
                        } else {
                            Integer updateMst = em.createNativeQuery("update td_accountmaster set cust_type='SC' where acno='" + acNo + "'").executeUpdate();
                            if (updateMst <= 0) {
                                return "Error in TD Account Master Updation !!!";
                            }
                        }
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        int var1 = em.createNativeQuery("insert into acedithistory (Acno,Name,OperMode,OrgnCode,EnteredBy,UpdateDt,Auth,AuthBy,introacno,FName,MAddress,"
                                + " PAddress,PhNo,PanNo,chBook,Nominee,Relationship,MinBalCharge,JtName1,JtName2,GName,AcInst,AppTDS,TDSDocu,IntOpt,JTNAME3,JtName4,"
                                + " IntToAcno, custid1, custid2, custid3, custid4,acctCategory,huf_family) select a.acno, c.custname , a.opermode, a.orgncode," + "'" + userName + "',now(),'Y','SYSTEM',a.introaccno,c.fathername, "
                                + " c.craddress,c.praddress,c.phoneno,c.panno,a.chequebook,a.nomination,a.relatioship,a.minbal,a.JtName1,a.JtName2"
                                + " ,c.grdname,a.instruction,a.tdsflag,'','',a.JtName3,a.JtName4,'', custid1, custid2, custid3, custid4,a.acctCategory,a.huf_family from accountmaster a , "
                                + " customermaster c where a.acno='" + acNo + "' and c.custno=substring('" + acNo + "',5,6) and c.brncode = '" + orgnBrCode
                                + "' and c.actype = '" + acctType + "' and c.agcode=substring('" + acNo + "',11,2)").executeUpdate();
                        if (var1 <= 0) {
                            return "Updation problem in Account details";
                        } else {
                            Integer updateMst = em.createNativeQuery("update accountmaster set cust_type='SC' where acno='" + acNo + "'").executeUpdate();
                            if (updateMst <= 0) {
                                return "Error in Account Master Updation !!!";
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

    public String fdLienSecurityRenewal(String acNo, float vchNo, float receiptNo, String newAcNo, float newVchNo, float newReceiptNo, String userName, String dt, String brnCode, String intToAcNo) throws ApplicationException {
        try {
            String loanAcNo = "";
            List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + acNo + "' and voucherno = " + vchNo + " and receiptno = " + receiptNo + " "
                    + "and txndate = (select max(txndate) from td_lien_update where acno = '" + acNo + "' and voucherno = " + vchNo + " and receiptno = " + receiptNo + ")").getResultList();
            if (chLienBrn.isEmpty()) {
                throw new ApplicationException("This voucher does not lien marked");
            }
            Vector ele = (Vector) chLienBrn.get(0);
            String lStat = ele.get(0).toString();
            if (lStat.equalsIgnoreCase("N")) {
                throw new ApplicationException("Lien Status is removed in td_lien_update");
            }

            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                    + " VALUES('" + acNo + "'," + vchNo + "," + receiptNo + ",'" + userName + "',now(),'N','" + acNo.substring(2, 4) + "','" + brnCode + "','Lien Marked For:" + loanAcNo + "')");
            int var1 = insertQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data insertion in td_lien_update");
            }

            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = 'N' WHERE acno='" + acNo + "' and voucherno=" + vchNo + "");
            int var2 = updateQuery.executeUpdate();
            if (var2 <= 0) {
                throw new ApplicationException("Problem in data update in td_vouchmst");
            }

            //New voucher Lien Marked process
            insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                    + " VALUES('" + newAcNo + "'," + newVchNo + "," + newReceiptNo + ",'" + userName + "',now(),'Y','" + newAcNo.substring(2, 4) + "','"
                    + brnCode + "','Lien Marked For:" + loanAcNo + "')");
            var1 = insertQuery.executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Problem in data insertion in td_lien_update");
            }

            updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = 'Y' WHERE acno='" + newAcNo + "' and voucherno=" + newVchNo + "");
            var2 = updateQuery.executeUpdate();
            if (var2 <= 0) {
                throw new ApplicationException("Problem in data update in td_vouchmst");
            }

            List dataList = em.createNativeQuery("select acno,sNo, ifnull(IntTableCode,''),ifnull(security,''),ifnull(SecurityRoi,0),ifnull(MargineROI,0),ifnull(AppRoi,0),ifnull(Margin,0), ifnull(AddRoi,0) from loansecurity where "
                    + "status='active' and lienacno='" + acNo + "' and voucherno=" + vchNo).getResultList();
            /*+ "and (securitysub<>'BANK GUARANTEE ISSUE' or securitysub is null or securitysub='')"*/
            if (!dataList.isEmpty()) {
                Vector loanAcNoVect = (Vector) dataList.get(0);
                loanAcNo = loanAcNoVect.get(0).toString();
                int sNo = Integer.parseInt(loanAcNoVect.get(1).toString());
                String intTableCode = loanAcNoVect.get(2).toString();

                String security = loanAcNoVect.get(3).toString();
                float securityROI = Float.parseFloat(loanAcNoVect.get(4).toString());
                float margineROI = Float.parseFloat(loanAcNoVect.get(5).toString());
                float appRoi = Float.parseFloat(loanAcNoVect.get(6).toString());

                float margin = Float.parseFloat(loanAcNoVect.get(7).toString());
                float addRoi = Float.parseFloat(loanAcNoVect.get(8).toString());

                Integer upadteMasterList = em.createNativeQuery("Update loansecurity Set Status = 'EXPIRED',"
                        + "ExpiredBy='" + userName + "',ExpiryDate='" + dt + "' Where Acno= '" + loanAcNo + "' and  sno =" + sNo + "").executeUpdate();
                if (upadteMasterList <= 0) {
                    throw new ApplicationException("Status Has Not Been Changed As EXPIRED");
                }

                List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a  WHERE a.voucherNo = " + newVchNo + " and a.acno = '"
                        + newAcNo + "'").getResultList();
                Vector newVchVect = (Vector) chk3.get(0);
                float prinAmt = Float.parseFloat(newVchVect.get(0).toString());

                if (intToAcNo.equalsIgnoreCase("")) {
                    String result = loanFacade.tdLienPresentAmount(newAcNo, newVchNo, prinAmt);
                    if (result == null) {
                        throw new ApplicationException("PROBLEM IN GETTING PRESENT AMOUNT !!!");
                    }
                    int n = result.indexOf("*");
                    prinAmt = Float.parseFloat(result.substring(n + 1));
                }

                double prinAmtD = prinAmt - ((prinAmt * margin) / 100);
                String fdDt = newVchVect.get(1).toString();
                String matDt = newVchVect.get(2).toString();
                float roi = Float.parseFloat(newVchVect.get(3).toString());

                List chk4 = em.createNativeQuery("SELECT ifnull(Description,'') FROM codebook WHERE GroupCode=51 and code=61").getResultList();
                if (chk4.isEmpty()) {
                    throw new ApplicationException("Data does not exit");
                }
                Vector ele1 = (Vector) chk4.get(0);
                String tdCodeDesc = ele1.get(0).toString();

                List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + loanAcNo + "'").getResultList();
                if (chk5.isEmpty()) {
                    throw new ApplicationException("Data does not exit");
                }
                Vector ele2 = (Vector) chk5.get(0);
                int autoSno = Integer.parseInt(ele2.get(0).toString());
                autoSno = autoSno + 1;

                Query instQuery = em.createNativeQuery("INSERT INTO loansecurity (acno,sno,security,particulars,matdate,lienvalue,matValue,issuedate,"
                        + " status,Remarks,enteredby,entrydate,SecurityOption,SecurityChg,lienacno, SecurityRoi,MargineROI,AppRoi,IntTableCode,Margin, AddRoi,voucherNo)"
                        + " VALUES('" + loanAcNo + "'," + autoSno + ",'" + security + "','" + newAcNo.substring(2, 4) + "','" + matDt + "'," + prinAmtD + ","
                        + prinAmt + ",'" + fdDt + "','Active',CONCAT('DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):', '" + newAcNo + "','; VchNo:"
                        + newVchNo + "', '; ROI:' , cast(" + roi + " as char(10)) , '; Present Amt:' , cast(" + prinAmt + " as char(20))), '" + userName + "','"
                        + dt + "','" + tdCodeDesc + "','Lien','" + newAcNo + "'," + Double.valueOf(roi) + "," + margineROI + "," + appRoi + ",'" + intTableCode
                        + "','" + margin + "','" + addRoi + "'," + newVchNo + ")");
                int var3 = instQuery.executeUpdate();
                if (var3 <= 0) {
                    throw new ApplicationException("Problem in data insertion in loansecurity");
                }

            }
            return "True";
        } catch (ApplicationException | NumberFormatException e) {
            throw e;
        }
    }
}
