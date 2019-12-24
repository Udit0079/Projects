/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import javax.transaction.UserTransaction;
import javax.persistence.Query;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import javax.transaction.SystemException;

/**
 *
 * @author Administrator
 */
@Stateless(mappedName = "SbIntCalcFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class SbIntCalcFacade implements SbIntCalcFacadeRemote {

//    @EJB
//    private LoanInterestCalculationFacadeRemote loanIntCalcBean;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmmy = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public List chkGLHead(String acType) throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("select distinct glheadint from accounttypemaster where acctcode = '" + acType + "'");
            return selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List checkkGLHead() throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("select distinct glheadint from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "'");
            return selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAcctType() throws ApplicationException {
        try {
            return em.createNativeQuery("select Acctcode From accounttypemaster where acctNature in('" + CbsConstant.SAVING_AC + "') and productcode <> 'T'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public double getRoiLoanAccount(double amt, String date, String acno) throws ApplicationException {
        double roi = 0;
        double rateOfInt = 0;
        try {
            List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, "
                    + "ifnull(B.PEGG_FREQ,0) FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B "
                    + " WHERE  A.ACNO = B.ACNO AND A.ACNO = '" + acno + "' AND A.EFF_FRM_DT <= '" + date + "' AND  "
                    + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acno + "' "
                    + " AND C.EFF_FRM_DT <= '" + date + "')").getResultList();

            if (intTableCodeEditList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_acc_int_rate_details for account " + acno);
            }
            Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
            String intTableCode = intTableCodeVect.get(0).toString();
            double acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());

            double acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
            String rateCode = intTableCodeVect.get(3).toString();
            int peggFreq = Integer.parseInt(intTableCodeVect.get(4).toString());

            if (rateCode.equalsIgnoreCase("Ab")) {                     //=====Absolute Fixed
                roi = rateOfInt;
            } else if (rateCode.equalsIgnoreCase("Fi")) {              //=====Fixed
                Calendar now = Calendar.getInstance();
                now.setTime(ymmd.parse(date));
                now.add(Calendar.MONTH, peggFreq);
                Date peggDt = now.getTime();

                if (peggDt.after(ymmd.parse(date)) || peggDt.equals(ymmd.parse(date))) {
                    roi = getROI(intTableCode, amt, date);
                } else {
                    roi = rateOfInt;
                }
            } else if (rateCode.equalsIgnoreCase("Fl")) {              //=====Floating
                roi = getROI(intTableCode, amt, date);
            }
            return roi + acPrefCr - acPrefDr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    //*********************GET LATEST ROI ************************//
    public double getROI(String intTableCode, double amt, String date) throws ApplicationException {
        double roi;
        if (amt < 0) {
            amt = amt * -1;
        }
        try {

            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, INTEREST_MASTER_TABLE_CODE"
                    + " from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + date + "' "
                    + "BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();
            int intVerNo;
            String intMastTblCod;
            if (laIntCodeMastList.isEmpty()) {
                List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, INTEREST_MASTER_TABLE_CODE"
                        + " from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode
                        + "' and '" + date + "' BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();
                if (laIntCodeMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in cbs_loan_interest_code_master for interest Table Code " + intTableCode);
                }
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(1);

            } else {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(1);
            }
            double intPerDr;
            double intPerCr;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit from "
                    + "cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date  "
                    + "and Record_Status = 'A'").getResultList();

            if (laIntMastList.isEmpty()) {
                List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit "
                        + "from cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and "
                        + "end_date  and Record_Status = 'A'").getResultList();
                if (laIntMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in cbs_loan_interest_master for interest Table Code " + intTableCode);
                }
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Double.parseDouble(laIntMastHistVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastHistVect.get(1).toString());
            } else {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());
            }
            String nrIntIndi;
            double nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                    + "cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt
                    + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (laIntSlabMastList.isEmpty()) {
                List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                        + "cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  "
                        + "BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();
                if (laIntSlabMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in cbs_loan_interest_slab_master interest Table Code " + intTableCode);
                }
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
            } else {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                //roi = nrIntPer + intPerDr - intPerCr;
                roi = nrIntPer + intPerCr - intPerDr;
                return roi;
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = 0d;
                return roi;
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
                roi = nrIntPer;
                return roi;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public LoanIntCalcList accWiseLoanIntCalc(String fromDt, String toDt, Vector acctVector,
            String brnCode, String bankCalcMethod, double deafAmount, double deafSavingRoi) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        LoanIntCalcList it = new LoanIntCalcList();
        try {
            double outSt = 0;
            String acNo = acctVector.get(0).toString();
            String custName = acctVector.get(1).toString();
            double rateOfInt = Double.parseDouble(acctVector.get(2).toString());
            double sanctionLimit = Double.parseDouble(acctVector.get(3).toString());

            String intTableCode = acctVector.get(4).toString();
            String calcMethod = acctVector.get(5).toString();
            String intAppFreq = acctVector.get(6).toString();
            String calcLevel = acctVector.get(7).toString();

            String intCalcUptoDt = acctVector.get(8).toString();
            String nextIntCalcDt = acctVector.get(9).toString();
            int accStatus = Integer.parseInt(acctVector.get(10).toString());

            Calendar calendar = Calendar.getInstance();
            Date frDate;
            if (ymmd.format(ymd.parse(intCalcUptoDt)).equalsIgnoreCase("19000101")) {
                frDate = ymmd.parse(fromDt);
                calendar.setTime(frDate);
            } else {
                if (ymd.parse(intCalcUptoDt).equals(ymd.parse(nextIntCalcDt))) {
                    frDate = ymd.parse(intCalcUptoDt);
                    calendar.setTime(frDate);
                } else {
                    frDate = ymd.parse(intCalcUptoDt);
                    calendar.setTime(frDate);
                    calendar.add(Calendar.DATE, 1);
                }
            }

            fromDt = ymmd.format(calendar.getTime());

            Date nxtCalDt;
            if (ymmd.format(ymd.parse(nextIntCalcDt)).equalsIgnoreCase("19000101")) {
                nxtCalDt = ymmd.parse(fromDt);
                calendar.setTime(frDate);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(bankCalcMethod, acNo, fromDt, calcMethod, 0);
            } else {
                nxtCalDt = ymd.parse(nextIntCalcDt);
                calendar.setTime(nxtCalDt);
                calendar.add(Calendar.DATE, 0);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(bankCalcMethod, acNo, fromDt, calcMethod, 0);
            }
            //** it will discuss with Sogendra Sir in future
            //toDt = ymmd.format(ymmd.parse(nextIntCalcDt));

            double totalInt = 0.0f;
            String[][] b = createFromDtArray(acNo, fromDt, toDt, intTableCode);
            long fromTimeStamp = ymd.parse(b[0][0]).getTime();
            long intCalcUpToDtStamp = ymd.parse(intCalcUptoDt).getTime();
            /*
             * Check that there is any value date transaction or not. Interest
             * calculation in case of Value Date transaction
             */
            if (fromTimeStamp < intCalcUpToDtStamp) {
                /*
                 * For getting from date before the value date
                 */
                String preIntPostingFromDt = getFromDtBeforeValueDt(b[0][0], acNo);

                /**
                 * For getting previous interest posting dates
                 */
                List<String> preIntPostingDtList = getIntPostingDtlist(b[0][0], acNo);
                /*
                 * Create double dim array of from date and to date
                 */
                String[][] b1 = createFromDtArray(acNo, preIntPostingFromDt, toDt, intTableCode);
                for (int i = 0; i < b1.length - 1; i++) {
                    String fDate = b1[i][0].toString();
                    String tDate = b1[i][1].toString();

                    /*
                     * Making the outstading according to the Calculation Level.
                     * IF Calculation Level is Sanction or Limit
                     */
                    /*
                     * 1. Oustanding
                     */
                    if (calcLevel.equals("L") && accStatus != 15) {
                        outSt = outStandingAsOnDate(acNo, fDate, intAppFreq);
                    } else if (calcLevel.equals("S") && accStatus != 15) {
                        outSt = sanctionLimit * -1;
                    } else if (accStatus == 15) {
                        outSt = deafAmount;
                    }
                    if (accStatus == 15) {
                        rateOfInt = deafSavingRoi;
                    } else {
                        rateOfInt = getRoiLoanAccount(outSt, fDate, acNo);
                    }

                    /*
                     * 3. No. of days between From Date and To Date
                     */
                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                    /*
                     * In each slab, No. of days is increasing by 1
                     */
                    double dayDiff = dDiff.doubleValue() + 1;

                    /*
                     * 4. Interest Calculation
                     */
                    double interest = rateOfInt * dayDiff * outSt / 36500;
                    b1[i][2] = formatter.format(outSt);
                    b1[i][3] = Double.toString(rateOfInt);
                    b1[i][4] = Double.toString(dayDiff);
                    b1[i][5] = formatter.format(interest);
                    b1[i][6] = intTableCode;
                    totalInt = totalInt + interest;
                    double postedInt = 0d;
                    if (isPostingDt(fDate, preIntPostingDtList)) {
                        postedInt = getPostedInterest(acNo, fDate);
                    }
                    if (postedInt - totalInt > 0) {
                        totalInt = postedInt - totalInt;
                    } else {
                        totalInt = totalInt - postedInt;
                    }
                    closingBal = Double.parseDouble(b1[i][2]);
                    product = product + Double.parseDouble(b1[i][2]) * dayDiff;
                }
            } else {
                for (int i = 0; i < b.length - 1; i++) {
                    String fDate = b[i][0].toString();
                    String tDate = b[i][1].toString();

                    /*
                     * Making the outstading according to the Calculation Level.
                     * IF Calculation Level is Sanction or Limit
                     */
                    /*
                     * 1. Oustanding
                     */
                    if (calcLevel.equals("L") && accStatus != 15) {
                        outSt = outStandingAsOnDate(acNo, fDate, intAppFreq);
                    } else if (calcLevel.equals("S") && accStatus != 15) {
                        outSt = sanctionLimit * -1;
                    } else if (accStatus == 15) {
                        outSt = deafAmount;
                    }

                    if (accStatus == 15) {
                        rateOfInt = deafSavingRoi;
                    } else {
                        rateOfInt = getRoiLoanAccount(outSt, fDate, acNo);
                    }

                    /*
                     * 3. No. of days between From Date and To Date
                     */
                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                    /*
                     * In each slab, No. of days is increasing by 1
                     */
                    double dayDiff = dDiff.doubleValue() + 1;

                    /*
                     * 4. Interest Calculation
                     */
                    double interest = rateOfInt * dayDiff * outSt / 36500;
                    b[i][2] = formatter.format(outSt);
                    b[i][3] = Double.toString(rateOfInt);
                    b[i][4] = Double.toString(dayDiff);
                    b[i][5] = formatter.format(interest);
                    b[i][6] = intTableCode;
                    totalInt = totalInt + interest;
                    closingBal = Double.parseDouble(b[i][2]);
                    product = product + Double.parseDouble(b[i][2]) * dayDiff;
                }
            }
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);

            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(CbsUtil.round(totalInt, 0))));
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return it;
    }

    /**
     *
     * @param dtStr
     * @param list
     * @return
     * @throws Exception
     */
    private boolean isPostingDt(String dtStr, List<String> list) throws ApplicationException {
        try {
            Date dt = ymd.parse(dtStr);
            for (String obj : list) {
                if (dt.getTime() == ymd.parse(obj).getTime()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param acNo
     * @param fromDt
     * @param toDt
     * @param intAppFreq
     * @param acNature
     * @param intTableCode
     * @return
     * @throws Exception
     */
    private String[][] createFromDtArray(String acNo, String fromDt, String toDt, String intTableCode) throws ApplicationException {
        try {
            ArrayList datesFrom = new ArrayList();
            List unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM recon WHERE  ACNO = '" + acNo + "' and Dt  BETWEEN '"
                    + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                    + " UNION ALL "
                    + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '"
                    + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();

            String a[][] = new String[unionAllTableList.size()][7];
            datesFrom.add(ymd.format(ymmd.parse(fromDt)));
            if (!unionAllTableList.isEmpty()) {
                for (int i = 0; i < unionAllTableList.size(); i++) {
                    Vector unionAllTableVect = (Vector) unionAllTableList.get(i);
                    a[i][0] = unionAllTableVect.get(0).toString();
                    if (i == 0) {
                        /*
                         * =======Getting the ROI Change Date Between FromDt and
                         * i Position Date=======================
                         */
                        if (ymmd.parse(fromDt).equals(ymd.parse(unionAllTableVect.get(0).toString()))) {
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                datesFrom.add(a[i][1]);
                            }
                        } else {
                            getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            datesFrom.add(a[i][0]);
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*
                                 * =======Getting the ROI Change Date in
                                 * Previous i Position=======================
                                 */
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                datesFrom.add(a[i][1]);
                            }
                        }
                    } else if (i > 0 && i < unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        /*
                         * =======Getting the ROI Change Date in Previous i
                         * Position=======================
                         */
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                    } else if (i == unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        a[i][1] = ymd.format(ymmd.parse(toDt));
                        /*
                         * =======Getting the ROI Change Date in Previous i
                         * Position=======================
                         */
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                            /*
                             * =======Getting the ROI Change Date in Current i
                             * Position=======================
                             */
                            getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                            datesFrom.add(a[i][1]);
                        } else {
                            datesFrom.add(a[i][1]);
                        }
                    }
                }
                Collections.sort(datesFrom);
            } else {
                getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                datesFrom.add(ymd.format(ymmd.parse(toDt)));
                Collections.sort(datesFrom);
            }
            String b[][] = new String[datesFrom.size()][7];
            if (!datesFrom.isEmpty()) {
                for (int i = 0; i < datesFrom.size(); i++) {
                    if (i == 0) {
                        b[i][0] = datesFrom.get(i).toString();
                        // firstDisbDt = datesFrom.get(i).toString();
                    } else if (i > 0 && i < datesFrom.size() - 1) {
                        b[i][0] = datesFrom.get(i).toString();
                        b[i - 1][1] = ymd.format(ymmd.parse(CbsUtil.dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
                    } else if (i == datesFrom.size() - 1) {
                        b[i - 1][1] = datesFrom.get(i).toString();
                    }
                }
            }
            return b;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param acNo
     * @param dt
     * @return
     */
    private double getPostedInterest(String acNo, String dt) throws ApplicationException {
        try {
            String query = "select cramt from recon where acno  = '" + acNo + "' and dt = '" + dt + "' and trandesc in (3,4) and ty =0";
            List intList = em.createNativeQuery(query).getResultList();
            double postedInt = 0d;
            if (!intList.isEmpty()) {
                Vector intVect = (Vector) intList.get(0);
                postedInt = Double.parseDouble(intVect.elementAt(0).toString());
            } else {
                postedInt = 0;
            }
            return postedInt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param valueDt
     * @param acNo
     * @return
     */
    private String getFromDtBeforeValueDt(String valueDt, String acNo) throws ApplicationException {
        try {
            String query = "select date_format(valuedt,'%Y%m%d') from recon where trandesc in (3,4) and acno ='" + acNo + "' and dt = "
                    + "(select max(dt) from recon where trandesc in (3,4) and acno ='" + acNo + "' and dt < '" + valueDt + "')";
            List fromDtList = em.createNativeQuery(query).getResultList();

            String preIntPostingFromDt = "";
            if (!fromDtList.isEmpty()) {
                Vector fromDtVect = (Vector) fromDtList.get(0);
                preIntPostingFromDt = CbsUtil.dateAdd(fromDtVect.elementAt(0).toString(), 1);
            } else {
                query = "select date_format(valuedt,'%Y%m%d') from recon where acno='" + acNo + "' and dt = (select min(dt) from recon where acno='" + acNo + "')";
                List acctOpenDt = em.createNativeQuery(query).getResultList();
                if (!acctOpenDt.isEmpty()) {
                    Vector acctOpenDtVect = (Vector) acctOpenDt.get(0);
                    preIntPostingFromDt = acctOpenDtVect.elementAt(0).toString();
                }
            }
            return preIntPostingFromDt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param valueDt
     * @param acNo
     * @return
     */
    private List<String> getIntPostingDtlist(String valueDt, String acNo) throws ApplicationException {
        try {
            String query = "select valuedt from recon where trandesc in (3,4) and acno ='" + acNo + "' and dt >= '" + valueDt + "'";
            List preIntPostingDts = em.createNativeQuery(query).getResultList();
            List<String> preIntPostingDtList = new ArrayList<String>();
            if (!preIntPostingDts.isEmpty()) {
                for (int i = 0; i < preIntPostingDts.size(); i++) {
                    Vector preIntPostingDtVect = (Vector) preIntPostingDts.get(i);
                    preIntPostingDtList.add(preIntPostingDtVect.elementAt(0).toString());
                }
            }
            return preIntPostingDtList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ***********========GETTING THE OUTSTANDING AMOUNT TILL
     * DATE========************
     */
    public double outStandingAsOnDate(String acNo, String tillDate, String intAppFreq) throws ApplicationException {
        try {
            List outStDrAmtList = null;
            if (intAppFreq.equalsIgnoreCase("S")) {
                outStDrAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM recon WHERE "
                        + "ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (0,1,2,5,6,7,8,9) "
                        + "group by acno ").getResultList();

            } else if (intAppFreq.equalsIgnoreCase("C")) {
                outStDrAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0)),0) FROM "
                        + "recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by  acno ").getResultList();
            }

            if (outStDrAmtList.isEmpty()) {
                return 0;
                //throw new ApplicationException("No tranction done in this Account "+ acNo);
            }
            Vector outStDrAmtVect = (Vector) outStDrAmtList.get(0);
            double drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString());
            if (drAmt < 0) {
                drAmt = 0;
            }
            return drAmt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param intTableCode
     * @param fromDt
     * @param toDate
     * @param datesFrom
     * @return
     */
    public ArrayList getRoiChangeSlab(String intTableCode, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException {
        try {
            /*
             * =======Getting the ROI Change Date in Previous i
             * Position=======================
             */
            List laIntCodeMastList = em.createNativeQuery("select START_DATE,INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master where "
                    + "INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

            List laIntCodeMastHistList = em.createNativeQuery("select START_DATE,INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master_history "
                    + "where INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                String stDt = laIntCodeMastVect.get(0).toString();
                String intMastTblCod = laIntCodeMastVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select start_date from cbs_loan_interest_master where code = '" + intMastTblCod
                        + "' and  START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select start_date from cbs_loan_interest_master_history where code = '"
                        + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                if (!laIntMastList.isEmpty()) {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    String stDtIntMast = laIntMastVect.get(0).toString();
                    datesFrom.add(stDtIntMast);
                }
                if (!laIntMastHistList.isEmpty()) {
                    Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                    String stDtIntMast = laIntMastHistVect.get(0).toString();
                    datesFrom.add(stDtIntMast);
                }

            }
            if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                String stDt = laIntCodeMastHistVect.get(0).toString();
                String intMastTblCod = laIntCodeMastHistVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select start_date from cbs_loan_interest_master where code = '" + intMastTblCod
                        + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select start_date from cbs_loan_interest_master_history where code = '"
                        + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                if (!laIntMastList.isEmpty()) {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    String stDtIntMast = laIntMastVect.get(0).toString();
                    datesFrom.add(stDtIntMast);
                }
                if (!laIntMastHistList.isEmpty()) {
                    Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                    String stDtIntMast = laIntMastHistVect.get(0).toString();
                    datesFrom.add(stDtIntMast);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return datesFrom;
    }

    /*
     * C = Calender Year (Jan-Dec) CF = Calender Year Financial Year (Apr-Mar) A
     * = Anniversary
     */
    public String getNextToDt(String bankCalcMethod, String acNo, String fromDt, String freq, int moratoriumPd) throws ApplicationException {
        //String result = null;
        String toDt = "";
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(fromDt);
            calendar.setTime(frDate);
            calendar.add(Calendar.MONTH, moratoriumPd);

            if (bankCalcMethod.equalsIgnoreCase("C")) {
                /*
                 * Calender Method
                 */
                if (freq.equalsIgnoreCase("M")) {
                    /*
                     * Monthly
                     */
                    int lastDate = calendar.getActualMaximum(Calendar.DATE);
                    calendar.set(Calendar.DATE, lastDate);

                } else if (freq.equalsIgnoreCase("Q")) {

                    /*
                     * Quarterly
                     */
                    int month = calendar.get(Calendar.MONTH); /*
                     * 0 through 11
                     */

                    int quarter = (month / 3) + 1;
                    String currentQuarterBeginDate = "";

                    if (quarter == 1) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "0101";
                    } else if (quarter == 2) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "0401";
                    } else if (quarter == 3) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "0701";
                    } else if (quarter == 4) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "1001";
                    }
                    calendar.setTime(ymmd.parse(currentQuarterBeginDate));
                    String formmatedQuarterBeginDate = ymmd.format(calendar.getTime());
                    calendar.setTime(ymmd.parse(formmatedQuarterBeginDate));
                    calendar.add(Calendar.MONTH, 3);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                } else if (freq.equalsIgnoreCase("H")) {
                    /*
                     * Halfyearly
                     */
                    int month = calendar.get(Calendar.MONTH); /*
                     * 0 through 11
                     */

                    int halfyear = (month / 6) + 1;
                    String currentHalfYearBeginDate = "";
                    if (halfyear == 1) {
                        currentHalfYearBeginDate = calendar.get(Calendar.YEAR) + "0101";
                    } else if (halfyear == 2) {
                        currentHalfYearBeginDate = calendar.get(Calendar.YEAR) + "0701";
                    }
                    calendar.setTime(ymmd.parse(currentHalfYearBeginDate));
                    String formmatedHalfYearBeginDate = ymmd.format(calendar.getTime());
                    calendar.setTime(ymmd.parse(formmatedHalfYearBeginDate));
                    calendar.add(Calendar.MONTH, 6);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                } else if (freq.equalsIgnoreCase("Y")) {
                    /*
                     * Yearly
                     */
                    int month = calendar.get(Calendar.MONTH); /*
                     * 0 through 11
                     */

                    int Yearly = (month / 12) + 1;
                    String currentYearBeginDate = "";
                    if (Yearly == 1) {
                        currentYearBeginDate = calendar.get(Calendar.YEAR) + "0101";
                    }
                    calendar.setTime(ymmd.parse(currentYearBeginDate));
                    String formmatedYearBeginDate = ymmd.format(calendar.getTime());
                    calendar.setTime(ymmd.parse(formmatedYearBeginDate));
                    calendar.add(Calendar.MONTH, 12);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }
                toDt = ymmd.format(calendar.getTime());
                //result = toDt;
            } else if (bankCalcMethod.equalsIgnoreCase("CF")) {
                /*
                 * Calender Method But Financial Year
                 */
                if (freq.equalsIgnoreCase("M")) {
                    /*
                     * Monthly
                     */
                    int lastDate = calendar.getActualMaximum(Calendar.DATE);
                    calendar.set(Calendar.DATE, lastDate);
                } else if (freq.equalsIgnoreCase("Q")) {
                    /*
                     * Quarterly
                     */
                    int month = calendar.get(Calendar.MONTH); /*
                     * 0 through 11
                     */

                    int quarter = (month / 3) + 1;
                    String currentQuarterBeginDate = "";

                    if (quarter == 1) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "0101";
                    } else if (quarter == 2) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "0401";
                    } else if (quarter == 3) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "0701";
                    } else if (quarter == 4) {
                        currentQuarterBeginDate = calendar.get(Calendar.YEAR) + "1001";
                    }
                    calendar.setTime(ymmd.parse(currentQuarterBeginDate));
                    String formmatedQuarterBeginDate = ymmd.format(calendar.getTime());
                    calendar.setTime(ymmd.parse(formmatedQuarterBeginDate));
                    calendar.add(Calendar.MONTH, 3);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                } else if (freq.equalsIgnoreCase("H")) {
                    /*
                     * Halfyearly
                     */
                    int month = calendar.get(Calendar.MONTH); /*
                     * 0 through 11
                     */

                    int halfyear = (month / 6) + 1;
                    String currentHalfYearBeginDate = "";
                    if (halfyear == 1) {
                        currentHalfYearBeginDate = calendar.get(Calendar.YEAR) + "0401";
                    } else if (halfyear == 2) {
                        currentHalfYearBeginDate = calendar.get(Calendar.YEAR) + "1001";
                    }
                    calendar.setTime(ymmd.parse(currentHalfYearBeginDate));
                    String formmatedHalfYearBeginDate = ymmd.format(calendar.getTime());
                    calendar.setTime(ymmd.parse(formmatedHalfYearBeginDate));
                    calendar.add(Calendar.MONTH, 6);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                } else if (freq.equalsIgnoreCase("Y")) {
                    /*
                     * Yearly
                     */
                    int month = calendar.get(Calendar.MONTH); /*
                     * 0 through 11
                     */

                    int Yearly = (month / 12) + 1;
                    String currentYearBeginDate = "";
                    if (Yearly == 1) {
                        currentYearBeginDate = calendar.get(Calendar.YEAR) + "0401";
                    }
                    calendar.setTime(ymmd.parse(currentYearBeginDate));
                    String formmatedYearBeginDate = ymmd.format(calendar.getTime());
                    calendar.setTime(ymmd.parse(formmatedYearBeginDate));
                    calendar.add(Calendar.MONTH, 12);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }

                toDt = ymmd.format(calendar.getTime());
                // result = toDt;
            } else if (bankCalcMethod.equalsIgnoreCase("A")) {
                /*
                 * Anniversary Method
                 */

                if (freq.equalsIgnoreCase("M")) {
                    /*
                     * Monthly
                     */
                    calendar.add(Calendar.MONTH, 1);
                    toDt = ymmd.format(calendar.getTime());

                } else if (freq.equalsIgnoreCase("Q")) {
                    /*
                     * Quarterly
                     */
                    calendar.add(Calendar.MONTH, 3);
                    toDt = ymmd.format(calendar.getTime());

                } else if (freq.equalsIgnoreCase("H")) {
                    /*
                     * Halfyearly
                     */
                    calendar.add(Calendar.MONTH, 6);
                    toDt = ymmd.format(calendar.getTime());

                } else if (freq.equalsIgnoreCase("Y")) {
                    /*
                     * Yearly
                     */
                    calendar.add(Calendar.MONTH, 12);
                    toDt = ymmd.format(calendar.getTime());
                }
                //result = toDt;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return toDt;
    }

    /**
     * @param intOpt ( A -- ALL , I -- INDIVIDUAL ACCOUNT)
     * @param acType (ACCOUNT TYPE)
     * @param acNo (ACCOUNT NUMBER)
     * @param fromDt (FROM DATE)
     * @param toDt (TO DATE)
     * @param glAcNo (GL ACCOUNT NO FOR INTEREST POSTING)
     * @param authBy (NAME OF OPERATING USER)
     * @param mode ( CAL -- INTEREST CALCULATION , POST -- INTEREST POSTING)
     * @param brnCode
     * @return
     * @throws ApplicationException
     */
    public List<LoanIntCalcList> cbsSbIntCalc(String intOpt, String acctStatus, String acType, String acNo,
            String fromDt, String toDt, String brnCode) throws ApplicationException {

        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it;
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));

            List bankCalcMethodList = em.createNativeQuery("select ifnull(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank Calculation method doesn't exists in ParameterInfo.");
            }
            Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
            String bankCalcMethod = bankCalcMethodVect.get(0).toString();
            if (bankCalcMethod.equals("")) {
                bankCalcMethod = "CF";
            }
            List getAllAccList = null;
            if (intOpt.equalsIgnoreCase("I")) {
                getAllAccList = em.createNativeQuery("SELECT B.ACNO, B.CUSTNAME, B.intDeposit, B.odlimit, "
                        + "A.INTEREST_TABLE_CODE, A.CALC_METHOD,A.INT_APP_FREQ, A.CALC_LEVEL, "
                        + "ifnull(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, ifnull(A.NEXT_INT_CALC_DT,0) "
                        + "as NEXT_INT_CALC_DT,B.ACCSTATUS from cbs_loan_acc_mast_sec A, accountmaster B "
                        + "WHERE A.ACNO = B.ACNO AND B.ACCSTATUS NOT IN (9,15) AND  B.acno = '" + acNo + "' and "
                        + "B.OPENINGDT <= '" + toDt + "' AND B.curBrCode = '" + brnCode + "' ORDER BY "
                        + "B.ACNO").getResultList();
                if (getAllAccList.isEmpty()) {
                    throw new ApplicationException("Account doesn't Exist.");
                }
                for (int i = 0; i < getAllAccList.size(); i++) {
                    Vector getAllAccVect = (Vector) getAllAccList.get(i);
                    it = accWiseLoanIntCalc(fromDt, toDt, getAllAccVect, brnCode, bankCalcMethod, 0d, 0);
                    if (it == null) {
                        throw new ApplicationException("Interest not calculated due to system error occurred");
                    }
                    if (it.getTotalInt() > 0) {
                        intDetails.add(it);
                    }
                }
            } else {
                if (acctStatus.equals("1")) {
                    getAllAccList = em.createNativeQuery("SELECT B.ACNO, B.CUSTNAME, B.intDeposit, B.odlimit, A.INTEREST_TABLE_CODE, A.CALC_METHOD, "
                            + "A.INT_APP_FREQ, A.CALC_LEVEL, ifnull(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, ifnull(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, B.ORGNCODE "
                            + " from cbs_loan_acc_mast_sec A, accountmaster B WHERE A.ACNO = B.ACNO AND B.ACCSTATUS NOT IN (9,2,15) AND  B.accttype = '" + acType
                            + "' and B.OPENINGDT <= '" + toDt + "' AND B.curBrCode = '" + brnCode + "' ORDER BY B.ACNO").getResultList();
                } else {
                    getAllAccList = em.createNativeQuery("SELECT B.ACNO, B.CUSTNAME, B.intDeposit, B.odlimit, A.INTEREST_TABLE_CODE, A.CALC_METHOD, "
                            + "A.INT_APP_FREQ, A.CALC_LEVEL, ifnull(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, ifnull(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, B.ORGNCODE  "
                            + " from cbs_loan_acc_mast_sec A, accountmaster B WHERE A.ACNO = B.ACNO AND B.ACCSTATUS IN (2) AND  B.accttype = '" + acType
                            + "' and B.OPENINGDT <= '" + toDt + "' AND B.curBrCode = '" + brnCode + "' ORDER BY B.ACNO").getResultList();
                }
                if (getAllAccList.isEmpty()) {
                    throw new ApplicationException("Account doesn't Exist.");
                }
                long mills = System.currentTimeMillis();

                int slabCode = 0;
                List slabList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='SB-SLAB-WISE'").getResultList();
                if (!slabList.isEmpty()) {
                    Vector v1 = (Vector) slabList.get(0);
                    slabCode = Integer.parseInt(v1.get(0).toString());
                }

                if (slabCode == 0) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        /**
                         * **Product based interest calculation**
                         */
                        /**
                         * *Old based on outstanding**
                         */
                        //                    it = allAcctIntCal(fromDt, toDt, getAllAccVect, brnCode, bankCalcMethod);
                        /**
                         * *New based on slab**
                         */
                        //System.out.println("calculating interest of acno ->" + getAllAccVect.get(0).toString() + "------------> " + new Date());
                        it = allAcctIntCalSaving(fromDt, toDt, getAllAccVect, brnCode, bankCalcMethod);
                        System.out.println(it.getAcNo() + ":" + it.getTotalInt());
                        if (it == null) {
                            throw new ApplicationException("Interest not calculated due to system error occurred");
                        }
                        if (it.getTotalInt() > 0) {
                            intDetails.add(it);
                        }
                    }
                } else {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        /**
                         * *Product based interest calculation**
                         */
                        /**
                         * *Old based on outstanding**
                         */
                        //                    it = allAcctIntCal(fromDt, toDt, getAllAccVect, brnCode, bankCalcMethod);
                        /**
                         * *New based on slab**
                         */
                        //System.out.println("calculating interest of acno ->" + getAllAccVect.get(0).toString() + "------------> " + new Date());
                        it = allAcctIntCalSavingSlab(fromDt, toDt, getAllAccVect, brnCode, bankCalcMethod);
                        System.out.println(it.getAcNo() + ":" + it.getTotalInt());
                        if (it == null) {
                            throw new ApplicationException("Interest not calculated due to system error occurred");
                        }
                        if (it.getTotalInt() > 0) {
                            intDetails.add(it);
                        }
                    }
                }
                long diff = System.currentTimeMillis() - mills;
                System.out.println("Total time in calulation is = " + diff);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intDetails;
    }

    public LoanIntCalcList allAcctIntCal(String fromDt, String toDt, Vector acctVector, String brnCode, String bankCalcMethod) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        LoanIntCalcList it = new LoanIntCalcList();
        try {
            double outSt = 0;
            String acNo = acctVector.get(0).toString();
            String custName = acctVector.get(1).toString();
            double rateOfInt = Double.parseDouble(acctVector.get(2).toString());

            String intTableCode = acctVector.get(4).toString();
            System.out.println("Before Getting product of acno ->" + acNo);
            List productAndDtList = em.createNativeQuery("SELECT PRODUCT, FROM_DATE , TO_DATE, INT_CALC_START_DATE FROM "
                    + "cbs_saving_account_product WHERE ACNO='" + acNo + "'ORDER BY FROM_DATE").getResultList();
            if (productAndDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in Product Table for " + acNo);
            }
            System.out.println("After getting product of acno ->" + acNo);
            double totalInt = 0.0f;
            for (int i = 0; i < productAndDtList.size(); i++) {
                Vector productAndDtVect1 = (Vector) productAndDtList.get(i);
                outSt = Double.parseDouble(productAndDtVect1.elementAt(0).toString());
                String fDate = productAndDtVect1.elementAt(1).toString();
                String tDate = productAndDtVect1.elementAt(2).toString();

                rateOfInt = getRoiLoanAccount(outSt, fDate, acNo);
                /*
                 * 3. No. of days between From Date and To Date
                 */
                Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                /*
                 * In each slab, No. of days is increasing by 1
                 */
                double dayDiff = dDiff.doubleValue() + 1;
                /*
                 * 4. Interest Calculation
                 */
                double interest = rateOfInt * outSt * dayDiff / 36500;
                totalInt = totalInt + interest;
                closingBal = outSt;
                product = product + outSt * dayDiff;
            }
            System.out.println("after calculating interest of acno ->" + acNo + "------------> " + new Date());
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);

            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(totalInt)))));
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return it;
    }

    public LoanIntCalcList allAcctIntCalSaving(String fromDt, String toDt, Vector acctVector, String brnCode, String bankCalcMethod) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        LoanIntCalcList it = new LoanIntCalcList();
        try {
            double outSt = 0;
            String acNo = acctVector.get(0).toString();
            String custName = acctVector.get(1).toString();
            double rateOfInt = Double.parseDouble(acctVector.get(2).toString());
            String intTableCode = acctVector.get(4).toString();
            int orgnCode = Integer.parseInt(acctVector.get(10).toString());
            double acPrefDr = 0.0, acPrefCr = 0.0;

            int code = 0;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='SB-PRODUCT'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }

            double totalInt = 0.0f;
            List<SavingIntRateChangePojo> dataList = getSavingRoiChangeDetail(intTableCode, fromDt, toDt);
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no slab for saving interest calculation.");
            }
            for (int k = 0; k < dataList.size(); k++) {
                SavingIntRateChangePojo obj = dataList.get(k);
                String slabFrDt = obj.getFrDt();
                String slabToDt = obj.getToDt();
                rateOfInt = obj.getRoi();

                /**
                 * ***New Addition***********
                 */
                if (orgnCode == 16) {
                    List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, "
                            + "ifnull(B.PEGG_FREQ,0) FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B "
                            + " WHERE  A.ACNO = B.ACNO AND A.ACNO = '" + acNo + "' AND A.EFF_FRM_DT <= '" + slabToDt + "' AND  "
                            + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acNo + "' "
                            + " AND C.EFF_FRM_DT <= '" + slabToDt + "')").getResultList();

                    if (intTableCodeEditList.isEmpty()) {
                        throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acNo);
                    }
                    Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
                    acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());
                    acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
                    rateOfInt = rateOfInt + acPrefCr - acPrefDr;
                }

                /**
                 * *********End here**********
                 */
                //for monthly product
                if (code == 1) {
                    List productAndDtList = em.createNativeQuery("select sum(product) from cbs_saving_account_product "
                            + "where acno='" + acNo + "' and from_date between '" + slabFrDt + "' and '" + slabToDt + "'").getResultList();
                    //convert(varchar(8),from_date,112)

                    if (productAndDtList.isEmpty()) {
                        continue;
                    }
                    Vector productAndDtVect1 = (Vector) productAndDtList.get(0);
                    outSt = Double.parseDouble(productAndDtVect1.elementAt(0).toString());
                    double interest = rateOfInt * outSt / 1200;
                    totalInt = totalInt + interest;
                    closingBal = outSt;
                    product = product + outSt;

                } else {
                    List productAndDtList = em.createNativeQuery("select product,date_format(from_date,'%Y%m%d'),date_format(to_date,'%Y%m%d') from cbs_saving_account_product "
                            + "where acno='" + acNo + "' and from_date between '" + slabFrDt + "' and '" + slabToDt + "'").getResultList();
                    //convert(varchar(8),from_date,112)

                    if (productAndDtList.isEmpty()) {
                        continue;
                    }
//                System.out.println("After getting product of acno ->" + acNo + "   --->Time was--->" + new Date());

                    for (int i = 0; i < productAndDtList.size(); i++) {
                        Vector productAndDtVect1 = (Vector) productAndDtList.get(i);
                        outSt = Double.parseDouble(productAndDtVect1.elementAt(0).toString());
                        String fDate = productAndDtVect1.elementAt(1).toString();
                        String tDate = productAndDtVect1.elementAt(2).toString();

                        double tempInterest = 0;
                        if (i == 0) {
                            if (!(ymmd.parse(fDate).compareTo(ymmd.parse(slabFrDt)) == 0)) {
                                List tempOutStantList = em.createNativeQuery("select ifnull(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)),0) from recon r "
                                        + "where r.acno='" + acNo + "' and date_format(r.valuedt,'%Y%m%d')<='" + slabFrDt + "' and auth='y'").getResultList();
                                Vector element = (Vector) tempOutStantList.get(0);
                                double tempOutStant = Double.parseDouble(element.get(0).toString());

                                long tempDayDiff = CbsUtil.dayDiff(ymmd.parse(slabFrDt), ymmd.parse(fDate));
                                tempInterest = rateOfInt * tempOutStant * tempDayDiff / 36500;
                                totalInt = totalInt + tempInterest;
                                product = product + tempOutStant * tempDayDiff;
                            }
                        }

                        Long dDiff = null;
                        if (ymmd.parse(tDate).compareTo(ymmd.parse(slabToDt)) > 0) {
                            dDiff = CbsUtil.dayDiff(ymmd.parse(fDate), ymmd.parse(slabToDt));

                            double dayDiff = dDiff.doubleValue() + 1;
                            /*
                             * 4. Interest Calculation
                             */
                            double interest = rateOfInt * outSt * dayDiff / 36500;
                            totalInt = totalInt + interest;
                            closingBal = outSt;
                            product = product + outSt * dayDiff;

                            if (ymmd.parse(tDate).compareTo(ymmd.parse(toDt)) == 0) {
                                List<SavingIntRateChangePojo> changeList = getSavingRoiChangeDetail(intTableCode, CbsUtil.dateAdd(slabToDt, 1), toDt);
                                if (dataList.isEmpty()) {
                                    throw new ApplicationException("There is no slab for saving interest calculation.");
                                }
                                SavingIntRateChangePojo chgObj = changeList.get(k);
                                rateOfInt = chgObj.getRoi();

                                dDiff = CbsUtil.dayDiff(ymmd.parse(CbsUtil.dateAdd(slabToDt, 1)), ymmd.parse(toDt));
                                dayDiff = dDiff.doubleValue() + 1;

                                /*
                                 * 4. Interest Calculation
                                 */
                                interest = rateOfInt * outSt * dayDiff / 36500;
                                totalInt = totalInt + interest;
                                closingBal = outSt;
                                product = product + outSt * dayDiff;
                            }
                        } else {
                            dDiff = CbsUtil.dayDiff(ymmd.parse(fDate), ymmd.parse(tDate));

                            double dayDiff = dDiff.doubleValue() + 1;
                            /*
                             * 4. Interest Calculation
                             */
                            double interest = rateOfInt * outSt * dayDiff / 36500;
                            totalInt = totalInt + interest;
                            closingBal = outSt;
                            product = product + outSt * dayDiff;
                        }
                    }
                }
            }
//            System.out.println("after calculating interest of acno ->" + acNo + "------------> " + new Date());
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(dmy.format(ymmd.parse(fromDt)));
            it.setLastDt(dmy.format(ymmd.parse(toDt)));

            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(totalInt)))));
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return it;
    }

    public LoanIntCalcList allAcctIntCalSavingSlab(String fromDt, String toDt, Vector acctVector, String brnCode, String bankCalcMethod) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        LoanIntCalcList it = new LoanIntCalcList();
        try {
            double outSt = 0;
            String acNo = acctVector.get(0).toString();
            String custName = acctVector.get(1).toString();
            double rateOfInt = Double.parseDouble(acctVector.get(2).toString());
            String intTableCode = acctVector.get(4).toString();
            int orgnCode = Integer.parseInt(acctVector.get(10).toString());
            double acPrefDr = 0.0, acPrefCr = 0.0;

            int code = 0;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='SB-PRODUCT'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }

            double totalInt = 0.0f;

            /*
             * Code Comment For Slab Wise Roi In Utility
             */
//            List<SavingIntRateChangePojo> dataList = getSavingRoiChangeDetail(intTableCode, fromDt, toDt);
//            if (dataList.isEmpty()) {
//                throw new ApplicationException("There is no slab for saving interest calculation.");
//            }
//            for (int k = 0; k < dataList.size(); k++) {
//                SavingIntRateChangePojo obj = dataList.get(k);
//                String slabFrDt = obj.getFrDt();
//                String slabToDt = obj.getToDt();
//                rateOfInt = obj.getRoi();
            /**
             * ***New Addition***********
             */
//                if (orgnCode == 16) {
//                    List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, "
//                            + "ifnull(B.PEGG_FREQ,0) FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B "
//                            + " WHERE  A.ACNO = B.ACNO AND A.ACNO = '" + acNo + "' AND A.EFF_FRM_DT <= '" + slabToDt + "' AND  "
//                            + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acNo + "' "
//                            + " AND C.EFF_FRM_DT <= '" + slabToDt + "')").getResultList();
//
//                    if (intTableCodeEditList.isEmpty()) {
//                        throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acNo);
//                    }
//                    Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
//                    acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());
//                    acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
//                    rateOfInt = rateOfInt + acPrefCr - acPrefDr;
//                }
            /**
             * *********End here**********
             */
            //for monthly product
            if (code == 1) {
                List<SavingIntRateChangePojo> dataList = getSavingRoiChangeDetail(intTableCode, fromDt, toDt);
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no slab for saving interest calculation.");
                }

                for (int k = 0; k < dataList.size(); k++) {
                    SavingIntRateChangePojo obj = dataList.get(k);
                    String slabFrDt = obj.getFrDt();
                    String slabToDt = obj.getToDt();
                    rateOfInt = obj.getRoi();

                    if (orgnCode == 16) {
                        List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, "
                                + "ifnull(B.PEGG_FREQ,0) FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B "
                                + " WHERE  A.ACNO = B.ACNO AND A.ACNO = '" + acNo + "' AND A.EFF_FRM_DT <= '" + slabToDt + "' AND  "
                                + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acNo + "' "
                                + " AND C.EFF_FRM_DT <= '" + slabToDt + "')").getResultList();

                        if (intTableCodeEditList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acNo);
                        }
                        Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
                        acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());
                        acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
                        rateOfInt = rateOfInt + acPrefCr - acPrefDr;
                    }
                    List productAndDtList = em.createNativeQuery("select sum(product) from cbs_saving_account_product "
                            + "where acno='" + acNo + "' and from_date between '" + slabFrDt + "' and '" + slabToDt + "'").getResultList();
                    //convert(varchar(8),from_date,112)

                    if (productAndDtList.isEmpty()) {
                        continue;
                    }
                    Vector productAndDtVect1 = (Vector) productAndDtList.get(0);
                    outSt = Double.parseDouble(productAndDtVect1.elementAt(0).toString());
                    double interest = rateOfInt * outSt / 1200;
                    totalInt = totalInt + interest;
                    closingBal = outSt;
                    product = product + outSt;
                }
            } else {
                List productAndDtList = em.createNativeQuery("select product,date_format(from_date,'%Y%m%d'),date_format(to_date,'%Y%m%d'),roi from cbs_saving_account_product "
                        + "where acno='" + acNo + "' and from_date between '" + fromDt + "' and '" + toDt + "'").getResultList();
                //convert(varchar(8),from_date,112)

//                    if (productAndDtList.isEmpty()) {
//                        continue;
//                    }
//                System.out.println("After getting product of acno ->" + acNo + "   --->Time was--->" + new Date());
                for (int i = 0; i < productAndDtList.size(); i++) {
                    Vector productAndDtVect1 = (Vector) productAndDtList.get(i);
                    outSt = Double.parseDouble(productAndDtVect1.elementAt(0).toString());
                    String fDate = productAndDtVect1.elementAt(1).toString();
                    String tDate = productAndDtVect1.elementAt(2).toString();
                    rateOfInt = Double.parseDouble(productAndDtVect1.elementAt(3).toString());
                    if (orgnCode == 16) {
                        List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, "
                                + "ifnull(B.PEGG_FREQ,0) FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B "
                                + " WHERE  A.ACNO = B.ACNO AND A.ACNO = '" + acNo + "' AND A.EFF_FRM_DT <= '" + tDate + "' AND  "
                                + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acNo + "' "
                                + " AND C.EFF_FRM_DT <= '" + tDate + "')").getResultList();

                        if (intTableCodeEditList.isEmpty()) {
                            throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acNo);
                        }
                        Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
                        acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());
                        acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
                        rateOfInt = rateOfInt + acPrefCr - acPrefDr;
                    }

                    double tempInterest = 0;
//                        if (i == 0) {
//                            if (!(ymmd.parse(fDate).compareTo(ymmd.parse(fDate)) == 0)) {
//                                List tempOutStantList = em.createNativeQuery("select ifnull(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)),0) from recon r "
//                                        + "where r.acno='" + acNo + "' and date_format(r.valuedt,'%Y%m%d')<='" + fDate + "' and auth='y'").getResultList();
//                                Vector element = (Vector) tempOutStantList.get(0);
//                                double tempOutStant = Double.parseDouble(element.get(0).toString());
//
//                                long tempDayDiff = CbsUtil.dayDiff(ymmd.parse(fDate), ymmd.parse(fDate));
//                                tempInterest = rateOfInt * tempOutStant * tempDayDiff / 36500;
//                                totalInt = totalInt + tempInterest;
//                                product = product + tempOutStant * tempDayDiff;
//                            }
//                        }

                    Long dDiff = null;
//                        if (ymmd.parse(tDate).compareTo(ymmd.parse(tDate)) > 0) {
//                            dDiff = CbsUtil.dayDiff(ymmd.parse(fDate), ymmd.parse(tDate));
//
//                            double dayDiff = dDiff.doubleValue() + 1;
//                            /*
//                             * 4. Interest Calculation
//                             */
//                            double interest = rateOfInt * outSt * dayDiff / 36500;
//                            totalInt = totalInt + interest;
//                            closingBal = outSt;
//                            product = product + outSt * dayDiff;
//
//                            if (ymmd.parse(tDate).compareTo(ymmd.parse(toDt)) == 0) {
////                                List<SavingIntRateChangePojo> changeList = getSavingRoiChangeDetail(intTableCode, CbsUtil.dateAdd(slabToDt, 1), toDt);
////                                if (dataList.isEmpty()) {
////                                    throw new ApplicationException("There is no slab for saving interest calculation.");
////                                }
////                                SavingIntRateChangePojo chgObj = changeList.get(k);
////                                rateOfInt = chgObj.getRoi();
//
//                                dDiff = CbsUtil.dayDiff(ymmd.parse(CbsUtil.dateAdd(tDate, 1)), ymmd.parse(toDt));
//                                dayDiff = dDiff.doubleValue() + 1;
//
//                                /*
//                                 * 4. Interest Calculation
//                                 */
//                                interest = rateOfInt * outSt * dayDiff / 36500;
//                                totalInt = totalInt + interest;
//                                closingBal = outSt;
//                                product = product + outSt * dayDiff;
//                            }
//                        } else {
                    dDiff = CbsUtil.dayDiff(ymmd.parse(fDate), ymmd.parse(tDate));

                    double dayDiff = dDiff.doubleValue() + 1;
                    /*
                     * 4. Interest Calculation
                     */
                    double interest = rateOfInt * outSt * dayDiff / 36500;
                    totalInt = totalInt + interest;
                    closingBal = outSt;
                    product = product + outSt * dayDiff;
//                        }
                }
                //}
            }
//            System.out.println("after calculating interest of acno ->" + acNo + "------------> " + new Date());
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(dmy.format(ymmd.parse(fromDt)));
            it.setLastDt(dmy.format(ymmd.parse(toDt)));

            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(totalInt)))));
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return it;
    }

    public List<SavingIntRateChangePojo> getSavingRoiChangeDetail(String intTableCode, String fromDt, String toDt) throws ApplicationException {
        List<SavingIntRateChangePojo> dataList = new ArrayList<SavingIntRateChangePojo>();
        List<SavingIntRateChangePojo> resultList = new ArrayList<SavingIntRateChangePojo>();
        double roi = 0.0;
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, INTEREST_MASTER_TABLE_CODE"
                    + " from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + fromDt + "' "
                    + "BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();
            int intVerNo;
            String intMastTblCod = "", tmpFrDt = "", tmpToDt = "", tmpNFrDt = "", tmpNToDt = "";
            if (laIntCodeMastList.isEmpty()) {
                List laIntCodeMastHistList = em.createNativeQuery("select date_format(end_date,'%Y%m%d'),interest_version_no, "
                        + "interest_master_table_code from cbs_loan_interest_code_master_history where "
                        + "interest_code='" + intTableCode + "' and record_status = 'A'  and '" + fromDt + "' "
                        + " BETWEEN  start_date and end_date "
                        + "order by interest_version_no").getResultList();
                if (laIntCodeMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in cbs_loan_interest_code_master for interest Table Code " + intTableCode);
                }

                tmpFrDt = fromDt;
                for (int i = 0; i < laIntCodeMastHistList.size(); i++) {
                    SavingIntRateChangePojo pojo = new SavingIntRateChangePojo();

                    Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(i);
                    tmpToDt = laIntCodeMastHistVect.get(0).toString();
                    intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(1).toString());
                    intMastTblCod = (String) laIntCodeMastHistVect.get(2);

                    pojo.setFrDt(tmpFrDt);
                    pojo.setToDt(tmpToDt);
                    pojo.setMasterTblCode(intMastTblCod);
                    pojo.setVersionNo(intVerNo);
                    dataList.add(pojo);

                    tmpFrDt = CbsUtil.dateAdd(tmpToDt, 1);
                }

                laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, INTEREST_MASTER_TABLE_CODE"
                        + " from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + tmpFrDt + "' "
                        + "BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();

                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(1);

                SavingIntRateChangePojo pojo = new SavingIntRateChangePojo();
                pojo.setFrDt(tmpFrDt);
                pojo.setToDt(toDt);
                pojo.setMasterTblCode(intMastTblCod);
                pojo.setVersionNo(intVerNo);
                dataList.add(pojo);
            } else {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(1);

                SavingIntRateChangePojo pojo = new SavingIntRateChangePojo();
                pojo.setFrDt(fromDt);
                pojo.setToDt(toDt);
                pojo.setMasterTblCode(intMastTblCod);
                pojo.setVersionNo(intVerNo);
                dataList.add(pojo);
            }

//            System.out.println("First Printing \n");
//            for (int m = 0; m < dataList.size(); m++) {
//                SavingIntRateChangePojo obj = dataList.get(m);
//                System.out.println("" + obj.getFrDt() + ":" + obj.getToDt() + ":" + obj.getIntPerCr() + ":" + obj.getIntPerDr() + ":" + obj.getRoi() + "\n");
//            }
            for (int i = 0; i < dataList.size(); i++) {
                SavingIntRateChangePojo pojo = dataList.get(i);

                double intPerDr = 0, intPerCr = 0;
                List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit from "
                        + "cbs_loan_interest_master where code = '" + pojo.getMasterTblCode() + "' and '" + pojo.getFrDt() + "' BETWEEN  start_date and end_date  "
                        + "and Record_Status = 'A'").getResultList();

                if (laIntMastList.isEmpty()) {
                    List laIntMastHistList = em.createNativeQuery("select date_format(end_date,'%Y%m%d'),interest_percentage_debit,interest_percentage_credit from "
                            + "cbs_loan_interest_master_history where code = '" + pojo.getMasterTblCode() + "' and end_date "
                            + "BETWEEN  '" + pojo.getFrDt() + "' and '" + pojo.getToDt() + "'  and Record_Status = 'A'").getResultList();
                    if (laIntMastHistList.isEmpty()) {
                        throw new ApplicationException("Data does not exists in cbs_loan_interest_master for interest Table Code " + intTableCode);
                    }

                    tmpNFrDt = pojo.getFrDt();
                    for (int l = 0; l < laIntMastHistList.size(); l++) {
                        Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                        tmpNToDt = laIntMastHistVect.get(0).toString();
                        intPerDr = Double.parseDouble(laIntMastHistVect.get(1).toString());
                        intPerCr = Double.parseDouble(laIntMastHistVect.get(2).toString());

                        SavingIntRateChangePojo obj = new SavingIntRateChangePojo();
                        obj.setFrDt(tmpNFrDt);
                        obj.setToDt(tmpNToDt);
                        obj.setMasterTblCode(pojo.getMasterTblCode());
                        obj.setVersionNo(pojo.getVersionNo());
                        obj.setIntPerDr(intPerDr);
                        obj.setIntPerCr(intPerCr);
                        resultList.add(obj);

                        tmpNFrDt = CbsUtil.dateAdd(tmpNToDt, 1);
                    }

//                    System.out.println("Second Printing \n");
//                    for (int m = 0; m < resultList.size(); m++) {
//                        SavingIntRateChangePojo obj = resultList.get(m);
//                        System.out.println("" + obj.getFrDt() + ":" + obj.getToDt() + ":" + obj.getIntPerCr() + ":" + obj.getIntPerDr() + ":" + obj.getRoi() + "\n");
//                    }
                    laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit from "
                            + "cbs_loan_interest_master where code = '" + pojo.getMasterTblCode() + "' and '" + tmpNFrDt + "' BETWEEN  start_date and end_date  "
                            + "and Record_Status = 'A'").getResultList();

                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                    intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());

                    SavingIntRateChangePojo obj = new SavingIntRateChangePojo();
                    obj.setFrDt(tmpNFrDt);
                    obj.setToDt(pojo.getToDt());
                    obj.setMasterTblCode(pojo.getMasterTblCode());
                    obj.setVersionNo(pojo.getVersionNo());
                    obj.setIntPerDr(intPerDr);
                    obj.setIntPerCr(intPerCr);
                    resultList.add(obj);

//                    System.out.println("Second Printing \n");
//                    for (int m = 0; m < resultList.size(); m++) {
//                        SavingIntRateChangePojo obj1 = resultList.get(m);
//                        System.out.println("" + obj1.getFrDt() + ":" + obj1.getToDt() + ":" + obj1.getIntPerCr() + ":" + obj1.getIntPerDr() + ":" + obj1.getRoi() + "\n");
//                    }
                } else {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                    intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());

//                    SavingIntRateChangePojo obj = new SavingIntRateChangePojo();
//                    obj.setFrDt(pojo.getFrDt());
//                    obj.setToDt(pojo.getToDt());
//                    obj.setMasterTblCode(pojo.getMasterTblCode());
//                    obj.setVersionNo(pojo.getVersionNo());
                    pojo.setIntPerDr(intPerDr);
                    pojo.setIntPerCr(intPerCr);
                    resultList.add(pojo);
                }
            }

//            for (int m = 0; m < resultList.size(); m++) {
//                SavingIntRateChangePojo obj = resultList.get(m);
//                System.out.println("" + obj.getFrDt() + ":" + obj.getToDt() + ":" + obj.getIntPerCr() + ":" + obj.getIntPerDr() + ":" + obj.getRoi());
//            }
            for (int m = 0; m < resultList.size(); m++) {
                SavingIntRateChangePojo obj = resultList.get(m);
                String nrIntIndi;
                double nrIntPer;
                List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                        + "cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and Record_Status = 'A' AND "
                        + "INTEREST_VERSION_NO =" + obj.getVersionNo() + "").getResultList();

                if (laIntSlabMastList.isEmpty()) {
                    List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                            + "cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and Record_Status = 'A' "
                            + "AND INTEREST_VERSION_NO =" + obj.getVersionNo() + "").getResultList();
                    if (laIntSlabMastHistList.isEmpty()) {
                        throw new ApplicationException("Data does not exists in cbs_loan_interest_slab_master interest Table Code " + intTableCode);
                    }
                    Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                    nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                    nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
                } else {
                    Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                    nrIntIndi = (String) laIntSlabMastVect.get(0);
                    nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
                }

                if (nrIntIndi.equalsIgnoreCase("F")) {
                    roi = nrIntPer + obj.getIntPerCr() - obj.getIntPerDr();
                } else if (nrIntIndi.equalsIgnoreCase("D")) {
                    roi = 0d;
                } else if (nrIntIndi.equalsIgnoreCase("N")) {
                    roi = nrIntPer;
                }
                obj.setRoi(roi);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    /**
     *
     * @param intDetailList
     * @param intOpt
     * @param status
     * @param acType
     * @param fromDt
     * @param toDt
     * @param glAcNo
     * @param authBy
     * @param brnCode
     * @return
     * @throws ApplicationException
     */
    public String sbInterestPosting(List<LoanIntCalcList> intDetailList, String intOpt, String status, String acType, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            String details = "";
            float dTrSNo = 0;
            float dRecNo = 0;
            String result = "";
            glAcNo = brnCode + glAcNo + "01";
            ut.begin();
            List tempBdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
            if (tempBdList.isEmpty()) {
                throw new ApplicationException("Dayend has been already done for branch :" + brnCode);
            }
            Vector tempBdVect = (Vector) tempBdList.get(0);
            String tempBd = tempBdVect.get(0).toString();

            if (intDetailList.isEmpty()) {
                throw new ApplicationException("Data does not exist for interest posting;");
            }
            if (intOpt.equalsIgnoreCase("I")) {
                dTrSNo = fTSPosting43CBSBean.getTrsNo();
                dRecNo = fTSPosting43CBSBean.getRecNo();

                LoanIntCalcList it = intDetailList.get(0);
                details = "INT FROM " + dmmy.format(dmy.parse(it.getFirstDt())) + " TO " + dmmy.format(dmy.parse(it.getLastDt()));

                List checkRep = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME ='SBINT_POST'").getResultList();
                Vector checkRep1 = (Vector) checkRep.get(0);
                if (checkRep1.get(0).toString().equals("0")) {

                    Query insertQuery = em.createNativeQuery("INSERT INTO recon (ACNO,TY,DT,VALUEDT,CRAMT,TRANTYPE,DETAILS,ENTERBY,"
                            + "AUTHBY,AUTH,recno,trsno,trandesc,org_brnid,dest_brnid)"
                            + "values ('" + it.getAcNo() + "'," + 0 + ",'" + tempBd + "','" + tempBd + "'," + it.getTotalInt() + "," + 8 + ",'" + details
                            + "','" + authBy + "','SYSTEM','Y'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brnCode + "','" + brnCode + "')");
                    int var3 = insertQuery.executeUpdate();
                    if (var3 <= 0) {
                        throw new ApplicationException("Error! Inserting data into recon.");
                    }
                    result = fTSPosting43CBSBean.updateBalance(CbsConstant.SAVING_AC, it.getAcNo(), it.getTotalInt(), 0, "", "");
                    if (!result.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Error in Updating Balance.");
                    }

                    result = fTSPosting43CBSBean.updateBalance(CbsConstant.PAY_ORDER, glAcNo, 0, it.getTotalInt(), "", "");
                    if (!result.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Error in Updating Balance.");
                    }

                    dRecNo = fTSPosting43CBSBean.getRecNo();
                    Query insertQuery5 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,ENTERBY,"
                            + "AUTHBY,AUTH,recno,trsno,trandesc,org_brnid,dest_brnid)"
                            + "values ('" + glAcNo + "'," + 1 + ",'" + tempBd + "','" + tempBd + "'," + it.getTotalInt() + "," + 8 + ",'" + details + "','"
                            + authBy + "','SYSTEM','Y'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brnCode + "','" + glAcNo.substring(0, 2) + "')");
                    int var7 = insertQuery5.executeUpdate();
                    if (var7 <= 0) {
                        throw new ApplicationException("Error! Inserting transaction for GL account.");
                    }
                } else if (checkRep1.get(0).toString().equals("1")) {
                    dRecNo = fTSPosting43CBSBean.getRecNo();
                    Query insertQuery = em.createNativeQuery("INSERT INTO recon_trf_d (ACNO,TY,DT,VALUEDT,CRAMT,TRANTYPE,DETAILS,ENTERBY,"
                            + "AUTHBY,AUTH,recno,trsno,trandesc,org_brnid,dest_brnid,adviceNo,adviceBrnCode)"
                            + "values ('" + it.getAcNo() + "'," + 0 + ",'" + tempBd + "','" + tempBd + "'," + it.getTotalInt() + "," + 8 + ",'" + details
                            + "','" + authBy + "','','N'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brnCode + "','" + brnCode + "','','')");
                    int var3 = insertQuery.executeUpdate();
                    if (var3 <= 0) {
                        throw new ApplicationException("Error! Inserting data in RECON_TRF_D.");
                    }
                    result = fTSPosting43CBSBean.updateBalance(CbsConstant.PAY_ORDER, glAcNo, 0, it.getTotalInt(), "", "");
                    if (!result.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Error in Updating Balance.");
                    }

                    dRecNo = fTSPosting43CBSBean.getRecNo();
                    Query insertQuery5 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,ENTERBY,"
                            + "AUTHBY,AUTH,recno,trsno,trandesc,org_brnid,dest_brnid,adviceNo,adviceBrnCode)"
                            + "values ('" + glAcNo + "'," + 1 + ",'" + tempBd + "','" + tempBd + "'," + it.getTotalInt() + "," + 8 + ",'" + details
                            + "','" + authBy + "','','N'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brnCode + "','" + glAcNo.substring(0, 2) + "','','')");
                    int var7 = insertQuery5.executeUpdate();
                    if (var7 <= 0) {
                        throw new ApplicationException("Error! Inserting data in RECON_TRF_D.");
                    }
                }
                List sNoList = em.createNativeQuery("SELECT max(ifnull(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                Vector sNoVect = (Vector) sNoList.get(0);
                int sNo = Integer.parseInt(sNoVect.get(0).toString());

                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) "
                        + "values(" + sNo + ", '" + it.getAcNo() + "','Y',now(),'" + ymd.format(dmy.parse(it.getFirstDt())) + "','" + ymd.format(dmy.parse(it.getLastDt())) + "','" + brnCode + "','I')");
                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                if (updateIntPostAcWise == 0) {
                    throw new ApplicationException("Error! Data inserting into cbs_loan_interest_post_ac_wise");
                } else {
                    String nextCalcDt = CbsUtil.dateAdd(ymmd.format(dmy.parse(it.getLastDt())), 1);
                    Query updateCaReconQuery = em.createNativeQuery(" UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + ymd.format(dmy.parse(it.getLastDt())) + "', NEXT_INT_CALC_DT = '"
                            + nextCalcDt + "' WHERE Acno = '" + it.getAcNo() + "'");
                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                    if (updateCaRecon == 0) {
                        throw new ApplicationException("Error! Data inserting into cbs_loan_acc_mast_sec");
                    } else {
                        ut.commit();
                        //Sending SMS
//                        try {
//                            if (it.getTotalInt() > 0) {
//                                smsFacade.sendTransactionalSms(SmsType.INTEREST_DEPOSIT, it.getAcNo(), 8, 0,
//                                        it.getTotalInt(), dmy.format(ymmd.parse(tempBd)));
//                            }
//                        } catch (Exception ex) {
//                            System.out.println("Error SMS Sending-->A/c is::" + it.getAcNo() + " And "
//                                    + "Amount is::" + it.getTotalInt());
//                        }
                        //End here
                        result = "Interest posted successfully. Generated batch no is " + dTrSNo;
                    }
                }
            } else {
                String trsNo = sbIntPosting(intDetailList, status, acType, fromDt, toDt, glAcNo, authBy, brnCode, tempBd);
                ut.commit();
                //Sending Sms
                try {
                    List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                    for (LoanIntCalcList it : intDetailList) {
                        if (it.getTotalInt() > 0) {
                            SmsToBatchTo to = new SmsToBatchTo();

                            to.setAcNo(it.getAcNo());
                            to.setCrAmt(it.getTotalInt());
                            to.setDrAmt(0d);
                            to.setTranType(8);
                            to.setTy(0);
                            to.setTxnDt(dmy.format(ymmd.parse(tempBd)));
                            to.setTemplate(SmsType.INTEREST_DEPOSIT);

                            smsList.add(to);
                        }
                    }
                    smsFacade.sendSmsToBatch(smsList);
                } catch (Exception e) {
                    System.out.println("Problem In SMS Sending To Batch In "
                            + "Transfer Authorization." + e.getMessage());
                }
                //End here
                result = "Interest Posted Successfully. Generated Batch No is :" + trsNo;
            }
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException {
        try {
            List getMaxToDtList = em.createNativeQuery("select ifnull(date_format(max(TODT), '%Y%m%d'),'') from  cbs_loan_interest_post_ac_wise where "
                    + "acno = '" + acNo + "' and BRNCODE = '" + brnCode + "' and POST_FLAG = 'Y' and FLAG = 'I'").getResultList();
            String dt = "";
            if (getMaxToDtList.isEmpty()) {
                throw new ApplicationException("Dayend has been already done for branch :" + brnCode);
            }
            Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
            dt = getMaxToDtVect.get(0).toString();
            if (dt.equals("")) {
                List selectQuery = em.createNativeQuery("select acno,custname,date_format(openingdt,'%d/%m/%Y'),accstatus "
                        + "from accountmaster where acno='" + acNo + "'").getResultList();
                if (selectQuery.isEmpty()) {
                    throw new ApplicationException("Account number does not exist");
                }

                List getMindtAcWiseList = em.createNativeQuery("select ifnull(date_format(max(dt), '%Y%m%d'),'') from recon where acno = '" + acNo
                        + "' and ty = 0 and trandesc in (3,4) and org_brnid = '" + brnCode + "'").getResultList();
                Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                if (getMinDtAcWiseVect.get(0).toString().equals("")) {
                    dt = getMinTxnDt(acNo, brnCode);
                }
//                Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                getMinDtAcWiseVect.get(0).toString();
                if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                    dt = getMinTxnDt(acNo, brnCode);
                } else {
                    String fromDt = getMinDtAcWiseVect.get(0).toString();
                    dt = CbsUtil.dateAdd(fromDt, 1);
                }
            } else {
                dt = CbsUtil.dateAdd(dt, 1);
            }
            return dt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private String getMinTxnDt(String acNo, String brCode) throws ApplicationException {
        try {
            List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from recon where acno = '" + acNo
                    + "' and trandesc not in (3,4) and org_brnid = '" + brCode + "'").getResultList();
            if (getMindtAcWiseList.isEmpty()) {
                throw new ApplicationException("From date is not found");
            }
            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                throw new ApplicationException("From date is not found");
            } else {
                return getMinDtAcWiseVect.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    //*********************Get the From Date & To Date(All Account)**********************//

    public String allFromDt(String acType, String brnCode, String want, String acctStatus) throws ApplicationException {
        try {
            String dt = "";
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType
                    + "' and POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = '" + acctStatus + "'").getResultList();
            if (getMaxToDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
            if (getMaxToDtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
            if (want.equalsIgnoreCase("f")) {
                List getFrDtList = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                        + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            } else if (want.equalsIgnoreCase("t")) {
                List getFrDtList = em.createNativeQuery("select date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                        + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            }
            return dt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private String sbIntPosting(List<LoanIntCalcList> intDetailList, String status, String acType, String fromDt, String toDt, String glAcNo, String authBy, String brnCode, String tempBd) throws ApplicationException {
        try {
            long mills = System.currentTimeMillis();
            String result;
            float dTrSNo = fTSPosting43CBSBean.getTrsNo();
            List checkList8 = em.createNativeQuery("SELECT DISTINCT DT FROM parameterinfo_posthistory WHERE actype='" + acType
                    + "' AND dt='" + toDt + "' AND brncode = '" + brnCode + "' and status = " + status + " AND purpose = 'Interest Posting'").getResultList();
            if (checkList8.size() > 0) {
                throw new ApplicationException("Interest is already posted.");
            }

            double glSumAmt = 0f;
            float dRecNo = 0;
            String details = "";
            String nextCalcDt = CbsUtil.dateAdd(toDt, 1);
            List sNoList = em.createNativeQuery("SELECT max(ifnull(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
            Vector sNoVect = (Vector) sNoList.get(0);
            int sNo = Integer.parseInt(sNoVect.get(0).toString());

            for (LoanIntCalcList it : intDetailList) {
                dRecNo = fTSPosting43CBSBean.getRecNo();

                details = "INT FROM " + dmmy.format(dmy.parse(it.getFirstDt())) + " TO " + dmmy.format(dmy.parse(it.getLastDt()));

                Query insertQuery = em.createNativeQuery("INSERT INTO recon (ACNO,TY,DT,VALUEDT,CRAMT,TRANTYPE,DETAILS,ENTERBY,AUTHBY,"
                        + "AUTH,recno,trsno,trandesc,org_brnid,dest_brnid)"
                        + "values ('" + it.getAcNo() + "'," + 0 + ",'" + tempBd + "','" + tempBd + "'," + it.getTotalInt() + "," + 8 + ",'" + details + "','"
                        + authBy + "','SYSTEM','Y'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brnCode + "','" + brnCode + "')");
                int var3 = insertQuery.executeUpdate();
                if (var3 <= 0) {
                    throw new ApplicationException("Error! Insering data into recon.");
                }
                result = fTSPosting43CBSBean.updateBalance(CbsConstant.SAVING_AC, it.getAcNo(), it.getTotalInt(), 0, "", "");
                if (!result.equalsIgnoreCase("True")) {
                    throw new ApplicationException("Error in Updating Balance.");
                }
                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,"
                        + "POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + it.getAcNo() + "','Y',now(),'" + fromDt + "','"
                        + toDt + "','" + brnCode + "','I')");
                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                if (updateIntPostAcWise == 0) {
                    throw new ApplicationException("Error! Inserting data into cbs_loan_interest_post_ac_wise");
                }
                Query updateCaReconQuery = em.createNativeQuery(" UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "',"
                        + " NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE acno = '" + it.getAcNo() + "'");
                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                if (updateCaRecon == 0) {
                    throw new ApplicationException("Error! Updating data into cbs_loan_acc_mast_sec");
                }
                sNo = sNo + 1;
                glSumAmt = glSumAmt + it.getTotalInt();
            }

            result = fTSPosting43CBSBean.updateBalance(CbsConstant.PAY_ORDER, glAcNo, 0, glSumAmt, "", "");
            if (!result.equalsIgnoreCase("True")) {
                throw new ApplicationException("Error in Updating Balance.");
            }
            dRecNo = fTSPosting43CBSBean.getRecNo();
            Query insertQuery5 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,TY,DT,VALUEDT,DRAMT,TRANTYPE,DETAILS,ENTERBY,AUTHBY,"
                    + "AUTH,recno,trsno,trandesc,org_brnid,dest_brnid)"
                    + "values ('" + glAcNo + "'," + 1 + ",'" + tempBd + "','" + tempBd + "'," + glSumAmt + "," + 8 + ",'" + details + "','"
                    + authBy + "','SYSTEM','Y'," + dRecNo + "," + dTrSNo + "," + 3 + ",'" + brnCode + "','" + glAcNo.substring(0, 2) + "')");
            int var7 = insertQuery5.executeUpdate();
            if (var7 <= 0) {
                throw new ApplicationException("Error! insering data into gl_recon");
            }

            Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,"
                    + "dt,trantime,enterby,status,brncode)"
                    + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','Interest Posting',3,'" + toDt + "',now(),'" + authBy
                    + "'," + status + ",'" + brnCode + "')");
            Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
            if (insertReconBalan == 0) {
                throw new ApplicationException("Error! Inserting data into parameterinfo_posthistory");
            } else {
                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', "
                        + "POST_DT = now(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt
                        + "' and TO_DT = '" + toDt + "' and flag = '" + status + "' and brncode = '" + brnCode + "'");
                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                if (updateAccTypeIntPara == 0) {
                    throw new ApplicationException("Error! Updating data into loan_acctype_interest_parameter");
                } else {
                    result = String.valueOf(dTrSNo);
                }
            }
            long diff = System.currentTimeMillis() - mills;
            System.out.println("Total time in calulation is = " + diff);
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String sbAllIntPosting(String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String result = "";
            ut.begin();
            List tempBdList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + brnCode + "'").getResultList();
            if (tempBdList.isEmpty()) {
                throw new ApplicationException("");
            }
            Vector tempBdVect = (Vector) tempBdList.get(0);
            String tempBd = tempBdVect.get(0).toString();
            List acTypeList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature = '" + CbsConstant.SAVING_AC + "' and productcode <> 'T'").getResultList();
            if (acTypeList.isEmpty()) {
                throw new ApplicationException("Account type does not exist in Saving Nature");
            }
            List<LoanIntCalcList> rsList = new ArrayList<LoanIntCalcList>();
            String acType = "";
            String glAcNo = "";
            String trsNo = "";
            for (int i = 0; i < acTypeList.size(); i++) {
                Vector acTypeVect = (Vector) acTypeList.get(i);
                acType = acTypeVect.get(0).toString();
                List list = chkGLHead(acType);
                if (list.isEmpty()) {
                    throw new ApplicationException("Interest GL Head does not defined for " + acType);
                }
                Vector ele = (Vector) list.get(0);
                glAcNo = ele.get(0).toString();
                glAcNo = brnCode + glAcNo + "01";
                List accList = em.createNativeQuery("select acno from  accountmaster where accstatus not in (9,2) and accttype = '" + acType
                        + "' and openingdt <= '" + toDt + "' and curBrCode = '" + brnCode + "' order by acno").getResultList();
                if (!accList.isEmpty()) {
                    rsList = cbsSbIntCalc("A", "1", acType, "", fromDt, toDt, brnCode);
                    if (!rsList.isEmpty()) {
                        trsNo = sbIntPosting(rsList, "1", acType, ymmd.format(dmy.parse(fromDt)), ymmd.format(dmy.parse(toDt)), glAcNo, authBy, brnCode, tempBd);
                        result = result.equals("") ? trsNo : result + "," + trsNo;
                    }
                }

                accList = em.createNativeQuery("select acno from  accountmaster where accstatus in (2) and  accttype = '" + acType
                        + "' and openingdt <= '" + toDt + "' and curBrCode = '" + brnCode + "' order by acno").getResultList();
                if (!accList.isEmpty()) {
                    rsList = cbsSbIntCalc("A", "2", acType, "", fromDt, toDt, brnCode);
                    if (!rsList.isEmpty()) {
                        trsNo = sbIntPosting(rsList, "2", acType, ymmd.format(dmy.parse(fromDt)), ymmd.format(dmy.parse(toDt)), glAcNo, authBy, brnCode, tempBd);
                        result = result.equals("") ? trsNo : result + "," + trsNo;
                    }
                }
            }
            ut.commit();
            //Sending Sms
            try {
                if (!rsList.isEmpty()) {
                    List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                    for (LoanIntCalcList it : rsList) {
                        if (it.getTotalInt() > 0) {
                            SmsToBatchTo to = new SmsToBatchTo();

                            to.setAcNo(it.getAcNo());
                            to.setCrAmt(it.getTotalInt());
                            to.setDrAmt(0d);
                            to.setTranType(8);
                            to.setTy(0);
                            to.setTxnDt(dmy.format(ymmd.parse(tempBd)));
                            to.setTemplate(SmsType.INTEREST_DEPOSIT);

                            smsList.add(to);
                        }
                    }
                    smsFacade.sendSmsToBatch(smsList);
                }
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            //End here    
            return "Interest successfully posted. Generated Batch Numbers are " + result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String getAllFromDt(String want) throws ApplicationException {
        try {
            String dt = "";
            List getMaxToDtList = em.createNativeQuery("select max(sno) from  cbs_loan_acctype_interest_parameter where ac_type ='10' and post_flag = 'N' "
                    + "and post_dt is null ").getResultList();
            if (getMaxToDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
            if (getMaxToDtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
            if (want.equalsIgnoreCase("f")) {
                List getFrDtList = em.createNativeQuery("select date_format(from_dt,'%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                        + "ac_type ='10' and post_flag = 'N' and sno = " + sNo + " and post_dt is null ").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            } else if (want.equalsIgnoreCase("t")) {
                List getFrDtList = em.createNativeQuery("select date_format(to_dt,'%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                        + "ac_type ='10' and POST_FLAG = 'N' and SNO = " + sNo + " and post_dt is null").getResultList();
                if (getFrDtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
                }
                Vector getFrDtVect = (Vector) getFrDtList.get(0);
                dt = getFrDtVect.get(0).toString();
            }
            return dt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String savingProductCalculation(String fromDt, String toDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int code = 0;
            System.out.println("Product posting end --->" + new Date());
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='SB-PRODUCT'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }
            int slabCode = 0;
            List slabList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='SB-SLAB-WISE'").getResultList();
            if (!slabList.isEmpty()) {
                Vector v1 = (Vector) slabList.get(0);
                slabCode = Integer.parseInt(v1.get(0).toString());
            }
            String rs = "";
            if (code == 1) {
                rs = insertMonthlyProduct(fromDt, toDt);
            } else {
                if (slabCode == 1) {
                    rs = slabWiseSavingProduct(fromDt, toDt);
                } else {
                    rs = normalSavingProduct(fromDt, toDt);
                }
            }

            if (!rs.equalsIgnoreCase("true")) {
                throw new ApplicationException(rs);
            }
            System.out.println("Product posting end --->" + new Date());
            ut.commit();
            return rs;
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

    private String normalSavingProduct(String fromDt, String toDt) throws ApplicationException {
        try {
            String fromDate = "";
            String frDate = "";
            BigDecimal firstDayProduct = new BigDecimal(0);
            String query = "select a.acno,a.accstatus,date_format(a.openingdt,'%Y%m%d'),c.int_table_code from accountmaster a, cbs_acc_int_rate_details c where "
                    + "a.accstatus<>9 and a.acno = c.acno and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "') and crdbflag ='C')";
            List accountList = em.createNativeQuery(query).getResultList();
            for (int k = 0; k < accountList.size(); k++) {
                Vector accountElement = (Vector) accountList.get(k);
                String acno = accountElement.get(0).toString();

                String openingDt = accountElement.get(2).toString();
                String acnoStatus = accountElement.get(1).toString();

                fromDate = fromDt;
                frDate = fromDt;
                if (ymmd.parse(openingDt).getTime() > ymmd.parse(fromDate).getTime()) {
                    fromDate = openingDt;
                    frDate = openingDt;
                }
                firstDayProduct = getOutStandingAmount(acno, frDate);
                Query insertProductTable = em.createNativeQuery("Insert into cbs_saving_account_product values('" + acno + "',"
                        + firstDayProduct + ",'" + fromDate + "','" + toDt + "','" + frDate + "'," + acnoStatus + ")");
                int insertValue = insertProductTable.executeUpdate();
                if (insertValue <= 0) {
                    throw new ApplicationException("Insertion problem in cbs saving account product for " + acno);
                }
                List valueDtList = em.createNativeQuery("select date_format(r.valuedt,'%Y%m%d') from recon r where acno='" + acno
                        + "' AND DT between '" + fromDate + "' and '" + toDt + "'").getResultList();

                for (int l = 0; l < valueDtList.size(); l++) {
                    Vector valueDtElement = (Vector) valueDtList.get(l);

                    String valueDt = valueDtElement.get(0).toString();
                    firstDayProduct = getOutStandingAmount(acno, valueDt);
                    List valueDtTranList = em.createNativeQuery("Select acno from cbs_saving_account_product where acno='" + acno
                            + "' and date_format(from_date,'%Y%m%d')='" + valueDt + "'").getResultList();
                    if (!valueDtTranList.isEmpty()) {
                        Query updateProductTable = em.createNativeQuery("update cbs_saving_account_product set "
                                + "product = " + firstDayProduct + ", from_date='" + valueDt + "', int_calc_start_date ='" + frDate
                                + "' where acno='" + acno + "' and date_format(from_date,'%Y%m%d')='" + valueDt + "'");
                        int updateValue = updateProductTable.executeUpdate();
                        if (updateValue <= 0) {
                            throw new ApplicationException("Updation problem in cbs saving account product for " + acno);
                        }
                    } else {
                        Query updateProductTable = em.createNativeQuery("update cbs_saving_account_product set to_date = '"
                                + CbsUtil.dateAdd(valueDt, -1) + "' where acno = '" + acno + "' and from_date = '" + fromDate + "'");
                        int updateValue = updateProductTable.executeUpdate();
                        if (updateValue <= 0) {
                            throw new ApplicationException("Updation problem in cbs saving account product for " + acno);
                        }
                        Query insertProductTable1 = em.createNativeQuery("Insert into cbs_saving_account_product values('" + acno + "',"
                                + firstDayProduct + ",'" + valueDt + "','" + toDt + "','" + frDate + "'," + acnoStatus + ")");
                        int insertValue1 = insertProductTable1.executeUpdate();
                        if (insertValue1 <= 0) {
                            throw new ApplicationException("Insertion problem in cbs saving account product for " + acno);
                        }
                    }
                    fromDate = valueDt;
                }
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private String slabWiseSavingProduct(String frDt, String toDate) throws ApplicationException {
        try {
            String frDate;
            String toDt;
            String fromDate;

            String query = "select a.acno,a.accstatus,date_format(a.openingdt,'%Y%m%d'),c.int_table_code from accountmaster a, cbs_acc_int_rate_details c where "
                    + "a.accstatus<>9 and a.acno = c.acno and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "') and crdbflag ='C')";
            List resultList = em.createNativeQuery(query).getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }

            for (int i = 0; i < resultList.size(); i++) {
                Vector vect = (Vector) resultList.get(i);
                String acno = vect.get(0).toString();

                String accstatus = vect.get(1).toString();
                String opDt = vect.get(2).toString();

                String intTableCode = vect.get(3).toString();
                System.out.println("Account number = " + acno);

                frDate = frDt;
                fromDate = frDt;
                toDt = toDate;

                if (ymmd.parse(opDt).getTime() > ymmd.parse(frDt).getTime() && ymmd.parse(opDt).getTime() <= ymmd.parse(toDate).getTime()) {
                    fromDate = opDt;
                    frDate = opDt;
                }

                ArrayList datesFrom = new ArrayList();
                datesFrom.add(fromDate);

                getRoiChangeSlab(intTableCode, fromDate, toDt, datesFrom);

                double firstDayProduct = 0;
                List balList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from recon where acno = '" + acno + "' and valuedt <='" + fromDate + "'").getResultList();

                if (balList.isEmpty()) {
                    throw new ApplicationException("Transaction does not exist for " + acno);
                }
                Vector balVect = (Vector) balList.get(0);
                firstDayProduct = Double.parseDouble(balVect.get(0).toString());

                double roi = getROI(intTableCode, firstDayProduct, fromDate);
                Query insertProductTable = em.createNativeQuery("insert into cbs_saving_account_product(ACNO,PRODUCT,FROM_DATE,TO_DATE,INT_CALC_START_DATE,ACNO_STATUS,ROI) "
                        + "values('" + acno + "'," + firstDayProduct + ",'" + fromDate + "','" + toDt + "','" + frDate + "','" + accstatus + "'," + roi + ")");
                int insertValue = insertProductTable.executeUpdate();
                if (insertValue <= 0) {
                    throw new ApplicationException("Insertion problem in cbs saving account product for " + acno);
                }

                List valueDtList = em.createNativeQuery("select date_format(valuedt,'%Y%m%d') from recon where acno='" + acno
                        + "' AND DT between '" + fromDate + "' and '" + toDt + "' and auth = 'Y' group by valuedt").getResultList();
                for (int j = 0; j < valueDtList.size(); j++) {
                    Vector dtVect = (Vector) valueDtList.get(j);
                    datesFrom.add(dtVect.get(0).toString());
                }

                Collections.sort(datesFrom);
                for (int k = 0; k < datesFrom.size(); k++) {

                    String valueDt = datesFrom.get(k).toString();
                    balList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from recon where acno = '" + acno + "' and valuedt <='" + valueDt + "'").getResultList();

                    if (balList.isEmpty()) {
                        firstDayProduct = 0;
                    } else {
                        balVect = (Vector) balList.get(0);
                        firstDayProduct = Double.parseDouble(balVect.get(0).toString());
                    }

                    roi = getROI(intTableCode, firstDayProduct, valueDt);

                    List valueDtTranList = em.createNativeQuery("Select acno from cbs_saving_account_product where acno='" + acno
                            + "' and from_date='" + valueDt + "'").getResultList();
                    if (!valueDtTranList.isEmpty()) {
                        Query updateProductTable = em.createNativeQuery("update cbs_saving_account_product set "
                                + "product = " + firstDayProduct + ", from_date='" + valueDt + "', int_calc_start_date ='" + frDate
                                + "' where acno='" + acno + "' and date_format(from_date,'%Y%m%d')='" + valueDt + "'");
                        int updateValue = updateProductTable.executeUpdate();
                        if (updateValue <= 0) {
                            throw new ApplicationException("Updation problem in cbs saving account product for " + acno);
                        }
                    } else {
                        Query updateProductTable = em.createNativeQuery("update cbs_saving_account_product set to_date = '"
                                + CbsUtil.dateAdd(valueDt, -1) + "' where acno = '" + acno + "' and from_date = '" + fromDate + "'");
                        int updateValue = updateProductTable.executeUpdate();
                        if (updateValue <= 0) {
                            throw new ApplicationException("Updation problem in cbs saving account product for " + acno);
                        }
                        Query insertProductTable1 = em.createNativeQuery("Insert into cbs_saving_account_product values('" + acno + "',"
                                + firstDayProduct + ",'" + valueDt + "','" + toDt + "','" + frDate + "'," + accstatus + "," + roi + ")");
                        int insertValue1 = insertProductTable1.executeUpdate();
                        if (insertValue1 <= 0) {
                            throw new ApplicationException("Insertion problem in cbs saving account product for " + acno);
                        }
                    }
                    fromDate = valueDt;
                }
            }
            return "True";
        } catch (ApplicationException | ParseException | NumberFormatException e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private String insertMonthlyProduct(String frDt, String toDate) throws ApplicationException {
        try {
            String frDate;
            String toDt;
            String fromDate;

            String query = "select a.acno,a.accstatus,date_format(a.openingdt,'%Y%m%d'),c.int_table_code from accountmaster a, cbs_acc_int_rate_details c where "
                    + "a.accstatus<>9 and a.acno = c.acno and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "'))";
            List resultList = em.createNativeQuery(query).getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }

            for (int i = 0; i < resultList.size(); i++) {
                Vector vect = (Vector) resultList.get(i);
                String acno = vect.get(0).toString();

                String accstatus = vect.get(1).toString();
                String opDt = vect.get(2).toString();

                System.out.println("Account number = " + acno);
                frDate = frDt;
                fromDate = frDt;
                toDt = toDate;

                if (ymmd.parse(opDt).getTime() > ymmd.parse(frDt).getTime() && ymmd.parse(opDt).getTime() <= ymmd.parse(toDate).getTime()) {
                    fromDate = opDt;
                    frDate = opDt;
                }

                int fromDay = CbsUtil.datePart("D", frDate);
                if (fromDay <= 10) {
                    frDate = frDate.substring(0, 6) + "10";
                    fromDate = frDate.substring(0, 6) + "10";
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ymmd.parse(frDate));

                String mEndDt = frDate.substring(0, 6) + calendar.getActualMaximum(Calendar.DATE);

                while (ymmd.parse(mEndDt).getTime() <= ymmd.parse(toDate).getTime()) {
                    double monthProduct = 0;
                    List balList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from recon where acno = '" + acno + "' and valuedt <'" + frDate + "'").getResultList();
                    if (balList.isEmpty()) {
                        throw new ApplicationException("Transaction does not exist for " + acno);
                    }
                    Vector balVect = (Vector) balList.get(0);
                    monthProduct = Double.parseDouble(balVect.get(0).toString());

                    List valueDtList = em.createNativeQuery("select date_format(valuedt,'%Y%m%d') from recon where  acno = '" + acno + "' and dt between '"
                            + frDate + "' and '" + mEndDt + "' and auth = 'Y' group by valuedt").getResultList();

                    for (int l = 0; l < valueDtList.size(); l++) {
                        Vector valueDtElement = (Vector) valueDtList.get(l);

                        String valueDt = valueDtElement.get(0).toString();
                        double product = 0;
                        List productList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from recon where acno = '" + acno + "' and valuedt <='" + valueDt + "'").getResultList();
                        if (!productList.isEmpty()) {
                            Vector productVect = (Vector) productList.get(0);
                            product = Double.parseDouble(productVect.get(0).toString());
                        }
                        if (product < monthProduct) {
                            monthProduct = product;
                        }
                    }
                    Query insertProductTable1 = em.createNativeQuery("insert into cbs_saving_account_product values('" + acno + "'," + monthProduct + ",'" + mEndDt + "','"
                            + toDt + "','" + fromDate + "','" + accstatus + "')");
                    int insertValue1 = insertProductTable1.executeUpdate();
                    if (insertValue1 <= 0) {
                        throw new ApplicationException("Insertion problem in cbs saving account product for " + acno);
                    }
                    mEndDt = CbsUtil.monthAdd(mEndDt, 1);
                    frDate = CbsUtil.monthAdd(frDate, 1);
                }
            }

            return "True";
        } catch (ApplicationException | ParseException | NumberFormatException e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getOutStandingAmount(String acno, String tillDt) {
        BigDecimal outStandAmount = new BigDecimal("0.00");

        List outStandList = em.createNativeQuery("Select ifnull(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)),0) from recon r where r.acno='" + acno
                + "' and date_format(r.valuedt,'%Y%m%d') <='" + tillDt + "' and auth='Y'").getResultList();
        Vector outStandElement = (Vector) outStandList.get(0);
        outStandAmount = new BigDecimal(String.valueOf(outStandElement.get(0)));

        return outStandAmount;
    }

    public String getIntPostingDt(String brCode, String acType, String acStatus) throws ApplicationException {
        try {
            List getMaxPostDtList = em.createNativeQuery("select max(date_format(post_dt,'%Y%m%d')) from cbs_loan_acctype_interest_parameter where "
                    + "ac_type ='" + acType + "' and post_flag = 'Y' and brncode='" + brCode + "' and flag='" + acStatus + "'").getResultList();
            if (getMaxPostDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            Vector getMaxPostDtVect = (Vector) getMaxPostDtList.get(0);
            if (getMaxPostDtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            return getMaxPostDtVect.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getIntPostingPd(String acType, String brnCode, String postingDt, String acctStatus) throws ApplicationException {
        try {
            List dtList = em.createNativeQuery("select date_format(from_dt,'%Y%m%d'), date_format(to_dt,'%Y%m%d') from "
                    + "cbs_loan_acctype_interest_parameter where ac_type ='" + acType + "' and post_flag = 'Y' and brncode = '"
                    + brnCode + "' and flag = '" + acctStatus + "' and date_format(post_dt,'%Y%m%d') = '" + postingDt + "'").getResultList();
            if (dtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            return dtList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanIntCalcList> cbsSbIntCalcForDeafClaim(String intOpt, String acctStatus, String acType,
            String acNo, String fromDt, String toDt, String brnCode, double deafAmount, double deafSavingRoi) throws ApplicationException {
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it;
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));

            List bankCalcMethodList = em.createNativeQuery("select ifnull(CALC_MTHD,'') from parameterinfo "
                    + "where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank Calculation method doesn't exists in ParameterInfo.");
            }
            Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
            String bankCalcMethod = bankCalcMethodVect.get(0).toString();
            if (bankCalcMethod.equals("")) {
                bankCalcMethod = "CF";
            }
            List getAllAccList = null;
            if (intOpt.equalsIgnoreCase("I")) {
                getAllAccList = em.createNativeQuery("SELECT B.ACNO, B.CUSTNAME, B.intDeposit, B.odlimit, "
                        + "A.INTEREST_TABLE_CODE, A.CALC_METHOD,A.INT_APP_FREQ, A.CALC_LEVEL, "
                        + "ifnull(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, ifnull(A.NEXT_INT_CALC_DT,0) "
                        + "as NEXT_INT_CALC_DT,B.ACCSTATUS from cbs_loan_acc_mast_sec A, accountmaster B "
                        + "WHERE A.ACNO = B.ACNO AND B.ACCSTATUS NOT IN (9) AND  B.acno = '" + acNo + "' and "
                        + "B.OPENINGDT <= '" + toDt + "' AND B.curBrCode = '" + brnCode + "' ORDER BY "
                        + "B.ACNO").getResultList();
                if (getAllAccList.isEmpty()) {
                    throw new ApplicationException("Account doesn't Exist.");
                }
                for (int i = 0; i < getAllAccList.size(); i++) {
                    Vector getAllAccVect = (Vector) getAllAccList.get(i);
                    it = accWiseLoanIntCalc(fromDt, toDt, getAllAccVect, brnCode, bankCalcMethod, deafAmount, deafSavingRoi);
                    if (it == null) {
                        throw new ApplicationException("Interest not calculated due to system error occurred");
                    }
                    if (it.getTotalInt() > 0) {
                        intDetails.add(it);
                    }
                }
            }
        } catch (ParseException | ApplicationException e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intDetails;
    }

    public String getProductFromDt() throws ApplicationException {
        try {
            List maxDtList = em.createNativeQuery("select ifnull(date_format(max(to_date),'%Y%m%d'),'') from cbs_saving_account_product").getResultList();
            if (maxDtList.isEmpty()) {
                return "";
            }
            Vector dateVect = (Vector) maxDtList.get(0);
            return dateVect.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
