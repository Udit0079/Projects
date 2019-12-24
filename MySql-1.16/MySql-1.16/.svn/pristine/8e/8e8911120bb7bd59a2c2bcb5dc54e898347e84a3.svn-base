/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.FdRoiDetail;
import com.cbs.pojo.IntCalTable;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author Alok Yadav
 * @Modifier Dhirendra Singh
 */
@Stateless(mappedName = "LoanInterestCalculationFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanInterestCalculationFacade implements LoanInterestCalculationFacadeRemote {

    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private LoanIntProductTestFacadeRemote loanIntFacade;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private AccountOpeningFacadeRemote acOpenFacadeRemote;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private SbIntCalcFacadeRemote sbIntCal;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    Date date = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat mdy = new SimpleDateFormat("MMM dd yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public List getAcctType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Acctcode From accounttypemaster "
                    + "where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') and CrDbFlag in('B','D')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getCaAcctType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Acctcode From accounttypemaster "
                    + "where acctNature in('" + CbsConstant.CURRENT_AC + "') ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getTheftAcType() throws ApplicationException {
        try {
            List theftList;
            theftList = em.createNativeQuery("select AcctCode from accounttypemaster where accttype = 'TF'").getResultList();
            return theftList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGlHeads(String acctCode) throws ApplicationException {
        String message;
        try {
            List resultlist = em.createNativeQuery("select GLHeadInt from accounttypemaster"
                    + " where AcctCode = '" + acctCode + "'").getResultList();
            if (resultlist.size() <= 0) {
                message = "GL Head not found for this account type.";
            } else {
                Vector element = (Vector) resultlist.get(0);
                message = (String) element.get(0);
            }
            return message;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getCaAccountCreditGlHeads(String acctCode) throws ApplicationException {
        String message;
        try {
            List resultlist = em.createNativeQuery("select GLHeadInt from accounttypemaster"
                    + " where AcctCode = '" + acctCode + "'").getResultList();
            List glHeadList = em.createNativeQuery("select ifnull(a.INTEREST_PAID_FLAG,''), ifnull(a.INTEREST_PAYABLE_ACCOUNT_PLACEHOLDER,'' )  from cbs_scheme_interest_or_service_charges_details a, accounttypemaster b where a.scheme_type = b.acctcode and a.scheme_type = '" + acctCode + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("Scheme is not defined for " + acctCode);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                if (glHeadVect.get(0).toString().equalsIgnoreCase("N") || glHeadVect.get(0).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("Interest is not allowed in " + acctCode);
                } else {
                    if (glHeadVect.get(1).toString().equalsIgnoreCase("")) {
                        throw new ApplicationException("GL Head Doesn't Exists for " + acctCode + " in Scheme");
                    } else {
                        message = glHeadVect.get(1).toString();
                    }
                }
            }
            return message;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String dateAdd(String dt, int noOfDays) throws ApplicationException {
        try {
            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(dt);
            calendar.setTime(frDate);
            calendar.add(Calendar.DATE, noOfDays);
            dt = ymmd.format(calendar.getTime());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return dt;
    }

    //*********************Get the From Date(Individual Account Wise)**********************//
    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException {
        try {
            List getMaxToDtList = em.createNativeQuery("select IFNULL(date_format(max(TODT), '%Y%m%d'),'') from  cbs_loan_interest_post_ac_wise where "
                    + "acno = '" + acNo + "' and BRNCODE = '" + brnCode + "' and POST_FLAG = 'Y' and FLAG = 'I'").getResultList();
            String toDt = "";
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                toDt = getMaxToDtVect.get(0).toString();
                if (toDt.equals("")) {
                    String acNature = fTSPosting43CBSBean.getAccountNature(acNo);
                    List selectQuery = em.createNativeQuery("select acno,custname,date_format(openingdt,'%d/%m/%Y'),accstatus "
                            + "from accountmaster where acno='" + acNo + "'").getResultList();
                    if (selectQuery.isEmpty()) {
                        throw new ApplicationException("Account number does not exist");
                    }

                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List getMindtAcWiseList = em.createNativeQuery("select date_format(max(dt), '%Y%m%d') from ca_recon where acno = '" + acNo
                                + "' and ty = 1 and trandesc in (3,4) and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);

                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                toDt = getMinTxnDt(acNature, acNo, brnCode);
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = dateAdd(fromDt, 1);
                            }
                        } else {
                            toDt = getMinTxnDt(acNature, acNo, brnCode);
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        List getMindtAcWiseList = em.createNativeQuery("select date_format(max(dt), '%Y%m%d') from loan_recon where acno = '" + acNo
                                + "' and ty = 1 and trandesc in (3,4) and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                toDt = getMinTxnDt(acNature, acNo, brnCode);
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = dateAdd(fromDt, 1);
                            }
                        } else {
                            toDt = getMinTxnDt(acNature, acNo, brnCode);
                        }
                    }
                } else {
                    toDt = dateAdd(toDt, 1);
                }
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private String getMinTxnDt(String acNature, String acNo, String brCode) throws ApplicationException {
        try {
            String tableName = "";
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                tableName = "ca_recon";
            } else {
                tableName = "loan_recon";
            }
            List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from " + tableName + " where acno = '" + acNo
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

    public String caAccountWiseFromDt(String acNo, String brnCode) throws ApplicationException {
        try {
            List getMaxToDtList = em.createNativeQuery("select IFNULL(date_format(max(TODT), '%Y%m%d'),'') from  cbs_loan_interest_post_ac_wise where "
                    + "acno = '" + acNo + "' and BRNCODE = '" + brnCode + "' and POST_FLAG = 'Y' and FLAG = 'D'").getResultList();
            String toDt = "";
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                toDt = getMaxToDtVect.get(0).toString();
                if (toDt.equals("")) {
                    String acNature = fTSPosting43CBSBean.getAccountNature(acNo);
                    List selectQuery = em.createNativeQuery("select acno,custname,date_format(openingdt,'%d/%m/%Y'),accstatus "
                            + "from accountmaster where acno='" + acNo + "'").getResultList();
                    if (selectQuery.isEmpty()) {
                        throw new ApplicationException("Account number does not exist");
                    }

                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                        List getMindtAcWiseList = em.createNativeQuery("select date_format(max(dt), '%Y%m%d') from ca_recon where acno = '" + acNo
//                                + "' and ty = 0 and trandesc in (3,4) and org_brnid = '" + brnCode + "'").getResultList();
//                        if (getMindtAcWiseList.size() > 0) {
//                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
//                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
//                                toDt = allDepositAccountFromDt(acNo.substring(2,4), brnCode, "f");
//                            } else {
//                                String fromDt = getMinDtAcWiseVect.get(0).toString();
//                                toDt = dateAdd(fromDt, 1);
//                            }
//                        } else {
                        toDt = allDepositAccountFromDt(acNo.substring(2, 4), brnCode, "f");
//                        }
                    }
                } else {
                    toDt = dateAdd(toDt, 1);
                }
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    //*********************Get the From Date & To Date(All Account)**********************//
    public String allFromDt(String acType, String brnCode, String want) throws ApplicationException {
        try {
            String toDt = null;
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType
                    + "' and POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = 'I'").getResultList();
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
                if (want.equalsIgnoreCase("f")) {
                    List getFrDtList = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
                    }
                } else if (want.equalsIgnoreCase("t")) {
                    List getFrDtList = em.createNativeQuery("select date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
                    }
                }
            } else {
                toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String allDepositAccountFromDt(String acType, String brnCode, String want) throws ApplicationException {
        try {
            String toDt = null;
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType
                    + "' and POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = 'D'").getResultList();
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
                if (want.equalsIgnoreCase("f")) {
                    List getFrDtList = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
                    }
                } else if (want.equalsIgnoreCase("t")) {
                    List getFrDtList = em.createNativeQuery("select date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
                    }
                }
            } else {
                toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getRoiLoanAccount(double amt, String date, String acno) throws ApplicationException {

        double acPrefCr = 0;
        double acPrefDr = 0;
        double roi = 0;
        double rateOfInt = 0;
        int peggFreq = 0;
        String rateCode = "";
        String result = "";
        String intTableCode = "";
        String intCalcBasedOnSecurity = "N", acNature = "", orderByParameter = "E";
        String openingDt = "";
        int acStat = 1;
        try {
            if (date.length() == 10 && date.contains("-")) {
                date = ymmd.format(ymd.parse(date));
            }
            List tempList = em.createNativeQuery("select accstatus,intdeposit, odlimit FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
            if (!tempList.isEmpty()) {
                Vector tempVector = (Vector) tempList.get(0);
                acStat = Integer.parseInt(tempVector.get(0).toString());
                roi = Double.parseDouble(tempVector.get(1).toString());
                amt = Double.parseDouble(tempVector.get(2).toString());
            }

            if (acStat != 9) {
                List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, IFNULL(B.PEGG_FREQ,0), "
                        + "IFNULL(E.intdeposit,0), E.OPENINGDT, ifnull(sg.TURN_OVER_DETAIL_FLAG,'N'), ac.acctNature, ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E')  "
                        + " FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B, loan_appparameter D, accountmaster E, "
                        + " cbs_scheme_general_scheme_parameter_master sg, accounttypemaster ac   "
                        + " WHERE  A.ACNO = B.ACNO AND A.ACNO = D.ACNO AND A.ACNO = E.ACNO  and B.SCHEME_CODE = sg.scheme_code "
                        + " AND A.ACNO = '" + acno + "' AND A.EFF_FRM_DT <= '" + date + "'  and substring(A.ACNO,3,2) = ac.AcctCode AND  "
                        + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acno + "' "
                        + " AND C.EFF_FRM_DT <= '" + date + "')").getResultList();

                if (!intTableCodeEditList.isEmpty()) {
                    Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
                    intTableCode = intTableCodeVect.get(0).toString();
                    acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());
                    acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
                    rateCode = intTableCodeVect.get(3).toString();
                    peggFreq = Integer.parseInt(intTableCodeVect.get(4).toString());
                    rateOfInt = Double.parseDouble(intTableCodeVect.get(5).toString());
                    openingDt = intTableCodeVect.get(6).toString();
                    intCalcBasedOnSecurity = intTableCodeVect.get(7).toString();
                    acNature = intTableCodeVect.get(8).toString();
                    orderByParameter = intTableCodeVect.get(9).toString();
                }

                /* IF RATE CODE is "Absolute Fixed/Fixed/Floating" =========*/
                /*2. ROI*/
                if (rateCode.equalsIgnoreCase("Ab")) {                     //=====Absolute Fixed
                    roi = rateOfInt;
                } else if (rateCode.equalsIgnoreCase("Fi")) {              //=====Fixed
                    /*Adding the Pegging Freq in to the Sanction Date*/
                    Calendar now = Calendar.getInstance();
                    //                now.setTime(ymmd.parse(date));
                    now.setTime(ymmd.parse(openingDt));
                    now.add(Calendar.MONTH, peggFreq);
                    Date peggDt = now.getTime();
                    //if (peggDt.after(ymmd.parse(date)) || peggDt.equals(ymmd.parse(date))) {
                    if (peggDt.before(ymmd.parse(date))) {
                        result = getROI(intTableCode, amt, date);
                        roi = Double.parseDouble(result) + acPrefDr - acPrefCr;
                    } else {
                        roi = rateOfInt;
                    }
                } else if (rateCode.equalsIgnoreCase("Fl")) {              //=====Floating
                    result = getROI(intTableCode, amt, date);
                    roi = Double.parseDouble(result) + acPrefDr - acPrefCr;
                }
                if (intCalcBasedOnSecurity.equalsIgnoreCase("Y") ) {
//                    List paramList = em.createNativeQuery("select code from cbs_parameterinfo where NAME = 'SEC_ROI_CALC'").getResultList();
                    String orderBy = " aa.Entrydate ";
                    if (orderByParameter.equalsIgnoreCase("A")) { //AppRoi
                        orderBy = " aa.AppRoi, aa.Entrydate";
                    } else if (orderByParameter.equalsIgnoreCase("R")) { //AppRoi desc
                        orderBy = " aa.AppRoi desc, aa.Entrydate ";
                    } else if (orderByParameter.equalsIgnoreCase("E")) { //Entrydate
                        orderBy = " aa.Entrydate ";
                    }
                    double[] interestArr = loanIntAsPerSecurity(acno, ymd.format(ymmd.parse(date)), ymd.format(ymmd.parse(date)), 1, amt * -1, ymd.format(ymmd.parse(date)), orderBy);
                    roi = interestArr[1];
                }
                if ((acStat == 11) || (acStat == 12) || (acStat == 13)) {
                    /*Code for MORENA (As per Dhiru Sir after varification)
                     Penal applicable only for NPA accounts*/
                    double penalRoiForNpaAc = 0;
                    List penalForNpaAcAppList = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name = 'NPA-PENAL-ROI-APPLY'").getResultList();
                    if (penalForNpaAcAppList.size() > 0) {
                        Vector penalForNpaAcAppVect = (Vector) penalForNpaAcAppList.get(0);
                        String penalRoiForNpaApply = penalForNpaAcAppVect.get(0).toString();
                        if (penalRoiForNpaApply.equalsIgnoreCase("Y")) {
                            List penalForNpaAcList = em.createNativeQuery("select ifnull(code,0) from cbs_parameterinfo where name = 'NPA-PENAL-ROI'").getResultList();
                            if (penalForNpaAcList.size() > 0) {
                                Vector penalForNpaAcVect = (Vector) penalForNpaAcList.get(0);
                                penalRoiForNpaAc = Double.parseDouble(penalForNpaAcVect.get(0).toString());
                                roi = roi + penalRoiForNpaAc;
                            }
                        }
                    }
                    /**
                     * ***************MORENA********************
                     */
                }
            }
//            /* 
//             * AdHoc Interest ROI 
//             */
//            String acNature = fTSPosting43CBSBean.getAccountNature(acno);
//            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acno + "' and Adhocapplicabledt <= '" + date + "'").getResultList();
//                if (!adHocDateList.isEmpty()) {
//                    Vector adHocDateVect = (Vector) adHocDateList.get(0);
//                    String adhocAppDt = adHocDateVect.get(0).toString();
//                    String adhocExpDt = adHocDateVect.get(1).toString();
//                    double adHocRoi = Double.parseDouble(adHocDateVect.get(2).toString());
//                    double odLimit = Double.parseDouble(adHocDateVect.get(3).toString());
//                    double adHocLimit = Double.parseDouble(adHocDateVect.get(4).toString());
//                    double penalRoi = Double.parseDouble(adHocDateVect.get(5).toString());
//                    if ((ymd.parse(adhocAppDt).before(ymd.parse(date))) || (ymd.parse(adhocAppDt).equals(ymd.parse(date)))) {
//                        if (!ymd.parse(date).after(ymd.parse(adhocExpDt))) {
//                            if (amt < 0) {
//                                if (Math.abs(amt) > odLimit) {
//                                    if (Math.abs(amt) > (odLimit + adHocLimit)) {
//                                        roi = roi + adHocRoi + penalRoi;
//                                    } else if ((Math.abs(amt) <= (odLimit + adHocLimit)) && (Math.abs(amt) > odLimit)) {
//                                        roi = roi + adHocRoi;
//                                    }
//                                }
//                            }
//                        }
//                    }
////                    else if(!ymd.parse(adhocExpDt).before(ymd.parse(date))){
////                        if(!ymd.parse(adhocExpDt).after(ymd.parse(date))){
////                           if(amt <0){
////                                if((amt*-1)>odLimit){
////                                   if((amt*-1)>(odLimit+adHocLimit)){
////                                       roi = roi + adHocRoi +penalRoi;
////                                   } else if(((amt*-1)<=(odLimit+adHocLimit)) && ((amt*-1)>odLimit)){
////                                       roi = roi + adHocRoi ;
////                                   } 
////                                }   
////                            } 
////                        }
////                    }
//                }
//            }
            result = new Double(roi).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return result;

    }

    //*********************GET LATEST ROI ************************//
    public String getROI(String intTableCode, double amt, String date) throws ApplicationException {
        String result = null;
        int intVerNo;
        String intMastTblCod;
        double roi;
        if (amt < 0) {
            amt = amt * -1;
        }
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,"
                    + "INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master where INTEREST_CODE='"
                    + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();

            List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,"
                    + "INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master_history where INTEREST_CODE='"
                    + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(3);
            } else if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(3);
            } else {
                throw new ApplicationException("Data does not exists in CBS_LOAN_INTEREST_CODE_MASTER " + intTableCode);
            }

            double intPerDr;
            double intPerCr;
            List laIntMastList = em.createNativeQuery("select cast(interest_percentage_debit as decimal(6,2)),cast(interest_percentage_credit as decimal(6,2)),start_date,end_date from "
                    + "cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date  and "
                    + "Record_Status = 'A'").getResultList();
            List laIntMastHistList = em.createNativeQuery("select cast(interest_percentage_debit as decimal(6,2)),cast(interest_percentage_credit as decimal(6,2)),start_date,end_date from "
                    + "cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date "
                    + " and Record_Status = 'A'").getResultList();

            if (!laIntMastList.isEmpty()) {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());
            } else if (!laIntMastHistList.isEmpty()) {
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Double.parseDouble(laIntMastHistVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastHistVect.get(1).toString());
            } else {
                throw new ApplicationException("false:Data does not exists in CBS_LOAN_INTEREST_MASTER " + intMastTblCod);
            }

            String nrIntIndi;
            double nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, cast(NORMAL_INTEREST_PERCENTAGE  as decimal(6,2)) from"
                    + " cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and "
                    + "END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, cast(NORMAL_INTEREST_PERCENTAGE  as decimal(6,2)) from "
                    + "cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT "
                    + "and END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (!laIntSlabMastList.isEmpty()) {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
            } else if (!laIntSlabMastHistList.isEmpty()) {
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
            } else {
                throw new ApplicationException("false:Data does not exists in CBS_LOAN_INTEREST_SLAB_MASTER " + intTableCode);
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                roi = nrIntPer + intPerDr - intPerCr;
                result = Double.toString(roi);
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = 0d;
                result = Double.toString(roi);
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
                roi = nrIntPer;
                result = Double.toString(roi);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return result;
    }

    public LoanIntCalcList accWiseLoanIntCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException {
        //1. Select the Scheme Code, Interest Code according to ACNO.
        //2. Make the slab according to Interest Code
        //3.
        //UserTransaction ut = context.getUserTransaction();


        LoanIntCalcList it = new LoanIntCalcList();
        try {
            String intTableCode = "";
            String calcMethod = "";
            String intAppFreq = "";
            String calcLevel = "";
            double rateOfInt = 0, preRoi = 0;
            double sanctionLimit = 0;
            double outSt = 0;
            String bankCalcMethod;
            String intCalcUptoDt = "";
            String nextIntCalcDt = "";
            String acNature = "";
            double subsidyAmt = 0;
            String subsidyExpDt = "", accStatus = "1", intCalcBasedOnSecurity = "N", orderByParameter = "E";
            //List unionAllTableList = null;
            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            /*
             List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
             if (bankCalcMethodList.isEmpty()) {
             throw new ApplicationException("Bank Calculation Method doesn't Exists in ParameterInfo.");
             } else {
             Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
             bankCalcMethod = bankCalcMethodVect.get(0).toString();
             if (bankCalcMethod.equals("")) {
             bankCalcMethod = "CF";
             }
             }*/

            /*Getting AcNature*/
//            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + fTSPosting43CBSBean.getAccountCode(acNo) + "'").getResultList();
//            if (acNatureList.isEmpty()) {
//                throw new ApplicationException("Account Nature doesn't exists.");
//            } else {
//                Vector acNatureVect = (Vector) acNatureList.get(0);
//                acNature = acNatureVect.get(0).toString();
//            }
//            List paramList = em.createNativeQuery("select code from cbs_parameterinfo where NAME = 'SEC_ROI_CALC'").getResultList();

            String custName = "";
            /**
             * ---Account is exists in Loan Secondary Table---*
             */
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, "
                    + "A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, "
                    + "IFNULL(A.PEGG_FREQ,0), A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, "
                    + "IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,''), "
                    + "c.AccStatus, c.custname, d.acctNature, ifnull(sg.TURN_OVER_DETAIL_FLAG,'N'), ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E')  from cbs_loan_acc_mast_sec A, "
                    + "loan_appparameter B, accountmaster c, accounttypemaster d, cbs_scheme_general_scheme_parameter_master sg "
                    + "where substring(A.acno,3,2) = d.AcctCode  and A.ACNO = c.ACNO and A.ACNO = B.ACNO and A.SCHEME_CODE = sg.scheme_code "
                    + "AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = (String) SecDetailsVect.get(2);
                    calcMethod = (String) SecDetailsVect.get(8);
                    intAppFreq = (String) SecDetailsVect.get(10);
                    calcLevel = (String) SecDetailsVect.get(11);
                    //rateOfInt = Double.parseDouble(SecDetailsVect.get(16).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(19).toString();
                    nextIntCalcDt = SecDetailsVect.get(21).toString();
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                    accStatus = SecDetailsVect.get(24).toString();
                    custName = SecDetailsVect.get(25).toString();
                    acNature = SecDetailsVect.get(26).toString();
                    intCalcBasedOnSecurity = SecDetailsVect.get(27).toString();
                    orderByParameter = SecDetailsVect.get(28).toString();
                }
            }

//            System.out.println(">>>>>>>>>>>>>>>>>>>AcNo;" + acNo);
//            System.out.println("1.;" + new Date());

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

//            System.out.println("2.;" + new Date());

            fromDt = ymmd.format(calendar.getTime());
            Date nxtCalDt;
            if (ymmd.format(ymd.parse(nextIntCalcDt)).equalsIgnoreCase("19000101")) {
                nxtCalDt = ymmd.parse(fromDt);
                calendar.setTime(frDate);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(brnCode, acNo, fromDt, calcMethod, 0);
            } else {
                nxtCalDt = ymd.parse(nextIntCalcDt);
                calendar.setTime(nxtCalDt);
                calendar.add(Calendar.DATE, 0);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(brnCode, acNo, fromDt, calcMethod, 0);
            }
            //** it will discuss with Sogendra Sir in future
            //toDt = ymmd.format(ymmd.parse(nextIntCalcDt));

//            System.out.println("3.;" + new Date());

            String[][] b = createFromDtArray(acNo, fromDt, toDt, intAppFreq, acNature, intTableCode, intCalcBasedOnSecurity, "N");


//            System.out.println("4.;" + new Date());

            long fromTimeStamp = ymd.parse(b[0][0]).getTime();
            long intCalcUpToDtStamp = ymd.parse(intCalcUptoDt).getTime();
            double totalInt = 0d;
            double product = 0d;
            double closingBal = 0d;
            /*Check that there is any value date transaction or not. Interest calculation in case of Value Date transaction*/
            if (fromTimeStamp < intCalcUpToDtStamp) {

//                System.out.println("5.;" + new Date());

                /* For getting from date before the value date*/
                String preIntPostingFromDt = getFromDtBeforeValueDt(b[0][0], acNo, acNature);

                /**
                 * For getting previous interest posting dates
                 */
                List<String> preIntPostingDtList = getIntPostingDtlist(b[0][0], acNo, acNature);
                /* Create double dim array of from date and to date*/
                String[][] b1 = createFromDtArray(acNo, preIntPostingFromDt, toDt, intAppFreq, acNature, intTableCode, intCalcBasedOnSecurity, "N");

//                System.out.println("6.;" + new Date());

                for (int i = 0; i < b1.length - 1; i++) {
                    String fDate = b1[i][0].toString();
                    String tDate = b1[i][1].toString();

                    /* Making the outstading according to the Calculation Level
                     IF Calculation Level is Sanction or Limit==================*/
                    /*1. Oustanding */
                    if (calcLevel.equals("L")) {
                        outSt = Double.parseDouble(outStandingAsOnDate(acNo, fDate));
                    } else if (calcLevel.equals("S")) {
                        outSt = sanctionLimit * -1;
                    }
                    /*
                     * Did the implementation of Subsidy Amount 
                     */
                    if ((subsidyAmt != 0)) {
                        if (ymd.parse(subsidyExpDt).after(ymd.parse(fDate)) || ymd.parse(subsidyExpDt).equals(ymd.parse(fDate))) {
                            outSt = outSt + subsidyAmt;
                            if (outSt >= 0) {
                                outSt = 0;
                            }
                        }
                    }
                    if (outSt <= 0) {
                        /* IF RATE CODE is "Absolute Fixed/Fixed/Floating" =========*/
                        /*2. ROI*/
                        String roiGet = getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                    /*3. No. of days between From Date and To Date*/
                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                    /* In each slab, No. of days is increasing by 1  */
                    double dayDiff = dDiff + 1;

                    /*4. Interest Calculation*/
                    double interest = 0d;
                    if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                        String orderBy = " aa.Entrydate ";
                        if (orderByParameter.equalsIgnoreCase("A")) { //AppRoi
                            orderBy = " aa.AppRoi, aa.Entrydate";
                        } else if (orderByParameter.equalsIgnoreCase("R")) { //AppRoi desc
                            orderBy = " aa.AppRoi desc, aa.Entrydate ";
                        } else if (orderByParameter.equalsIgnoreCase("E")) { //Entrydate
                            orderBy = " aa.Entrydate ";
                        }
                        double[] interestArr = loanIntAsPerSecurity(acNo, ymd.format(ymd.parse(fDate)), ymd.format(ymd.parse(tDate)), dayDiff, outSt, ymd.format(ymd.parse(fDate)), orderBy);
                        interest = interestArr[0];
                        rateOfInt = interestArr[1];
                    } else {
                        interest = rateOfInt * dayDiff * outSt / 36500;
                    }
                    /* 
                     * AdHoc Interest ROI 
                     */
                    double adHocRoi = 0;
                    double adHocInt = 0;
                    double odLimit = 0;
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, ifnull(AdhocROI,0), ifnull(ODLimit,0), ifnull(Adhoclimit,0), PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
                        if (!adHocDateList.isEmpty()) {
                            Vector adHocDateVect = (Vector) adHocDateList.get(0);
                            String adhocAppDt = adHocDateVect.get(0).toString();
                            String adhocExpDt = adHocDateVect.get(1).toString();
                            adHocRoi = Double.parseDouble(adHocDateVect.get(2).toString());
                            odLimit = Double.parseDouble(adHocDateVect.get(3).toString());
                            double adHocLimit = Double.parseDouble(adHocDateVect.get(4).toString());
                            //double loanPenalRoi = Double.parseDouble(adHocDateVect.get(5).toString());
                            if ((ymd.parse(adhocAppDt).before(ymd.parse(fDate))) || (ymd.parse(adhocAppDt).equals(ymd.parse(fDate)))) {
                                if (!ymd.parse(fDate).after(ymd.parse(adhocExpDt))) {
                                    if (outSt < 0) {
                                        if (Math.abs(outSt) > odLimit) {
                                            if (Math.abs(outSt) > (odLimit + adHocLimit)) {
                                                adHocRoi = adHocRoi;
                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
                                            } else if ((Math.abs(outSt) <= (odLimit + adHocLimit)) && (Math.abs(outSt) > odLimit)) {
                                                adHocRoi = adHocRoi;
                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
                                            }
                                        }
                                    }
                                }
                            }
                            //                    else if(!ymd.parse(adhocExpDt).before(ymd.parse(date))){
                            //                        if(!ymd.parse(adhocExpDt).after(ymd.parse(date))){
                            //                           if(amt <0){
                            //                                if((amt*-1)>odLimit){
                            //                                   if((amt*-1)>(odLimit+adHocLimit)){
                            //                                       roi = roi + adHocRoi +penalRoi;
                            //                                   } else if(((amt*-1)<=(odLimit+adHocLimit)) && ((amt*-1)>odLimit)){
                            //                                       roi = roi + adHocRoi ;
                            //                                   } 
                            //                                }   
                            //                            } 
                            //                        }
                            //                    }
                        }
                    }
                    b1[i][2] = formatter.format(outSt);
                    b1[i][3] = Double.toString(rateOfInt + adHocRoi);
                    b1[i][4] = Double.toString(dayDiff);
                    b1[i][5] = formatter.format(interest + adHocInt);
                    b1[i][6] = intTableCode;
                    totalInt = totalInt + interest + adHocInt;
                    closingBal = Double.parseDouble(b1[i][2]);
                    double postedInt = 0d;
                    if (isPostingDt(fDate, preIntPostingDtList)) {
                        postedInt = getPostedInterest(acNo, fDate, acNature);
                        if (postedInt - totalInt * -1 > 0) {
                            totalInt = totalInt * -1 - postedInt;
                        } else {
                            totalInt = postedInt - totalInt * -1;
                        }
                    }
                    product = product + Double.parseDouble(b1[i][2]) * dayDiff;
                }

//                System.out.println("7.;" + new Date());

            } else {

//                System.out.println("8.;" + new Date());

                /*Interest calculation in case of Value Date transaction*/
                for (int i = 0; i < b.length - 1; i++) {
                    String fDate = b[i][0].toString();
                    String tDate = b[i][1].toString();

                    /* Making the outstading according to the Calculation Level
                     IF Calculation Level is Sanction or Limit==================*/
                    /*1. Oustanding */
                    if (calcLevel.equals("L")) {
                        outSt = Double.parseDouble(outStandingAsOnDate(acNo, fDate));
                    } else if (calcLevel.equals("S")) {
                        outSt = sanctionLimit * -1;
                    }
                    /*
                     * Did the implementation of Subsidy Amount 
                     */
                    if ((subsidyAmt != 0)) {
                        if (ymd.parse(subsidyExpDt).after(ymd.parse(fDate)) || ymd.parse(subsidyExpDt).equals(ymd.parse(fDate))) {
                            outSt = outSt + subsidyAmt;
                            if (outSt >= 0) {
                                outSt = 0;
                            }
                        }
                    }
                    if (outSt <= 0) {
                        /* IF RATE CODE is "Absolute Fixed/Fixed/Floating" =========*/
                        /*2. ROI*/
                        String roiGet = getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                    /*3. No. of days between From Date and To Date*/
                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                    /* In each slab, No. of days is increasing by 1  */
                    double dayDiff = dDiff + 1;

                    /*4. Interest Calculation*/
                    double interest = 0d;
                    if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                        String orderBy = " aa.Entrydate ";
                        if (orderByParameter.equalsIgnoreCase("A")) { //AppRoi
                            orderBy = " aa.AppRoi, aa.Entrydate";
                        } else if (orderByParameter.equalsIgnoreCase("R")) { //AppRoi desc
                            orderBy = " aa.AppRoi desc, aa.Entrydate ";
                        } else if (orderByParameter.equalsIgnoreCase("E")) { //Entrydate
                            orderBy = " aa.Entrydate ";
                        }
                        double[] interestArr = loanIntAsPerSecurity(acNo, ymd.format(ymd.parse(fDate)), ymd.format(ymd.parse(tDate)), dayDiff, outSt, ymd.format(ymd.parse(fDate)), orderBy);
                        interest = interestArr[0];
                        rateOfInt = interestArr[1];
                        if (rateOfInt != 0) {
                            preRoi = rateOfInt;
                        }
                    } else {
                        interest = rateOfInt * dayDiff * outSt / 36500;
                    }
                    /* 
                     * AdHoc Interest ROI 
                     */
                    double adHocRoi = 0;
                    double adHocInt = 0;
                    double odLimit = 0;
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, ifnull(AdhocROI,0), ifnull(ODLimit,0), ifnull(Adhoclimit,0), PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
                        if (!adHocDateList.isEmpty()) {
                            Vector adHocDateVect = (Vector) adHocDateList.get(0);
                            String adhocAppDt = adHocDateVect.get(0).toString();
                            String adhocExpDt = adHocDateVect.get(1).toString();
                            adHocRoi = Double.parseDouble(adHocDateVect.get(2).toString());
                            odLimit = Double.parseDouble(adHocDateVect.get(3).toString());
                            double adHocLimit = Double.parseDouble(adHocDateVect.get(4).toString());
                            //double loanPenalRoi = Double.parseDouble(adHocDateVect.get(5).toString());
                            if ((ymd.parse(adhocAppDt).before(ymd.parse(fDate))) || (ymd.parse(adhocAppDt).equals(ymd.parse(fDate)))) {
                                if (!ymd.parse(fDate).after(ymd.parse(adhocExpDt))) {
                                    if (outSt < 0) {
                                        if (Math.abs(outSt) > odLimit) {
                                            if (Math.abs(outSt) > (odLimit + adHocLimit)) {
                                                adHocRoi = adHocRoi;
                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
                                            } else if ((Math.abs(outSt) <= (odLimit + adHocLimit)) && (Math.abs(outSt) > odLimit)) {
                                                adHocRoi = adHocRoi;
                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
                                            }
                                        }
                                    }
                                }
                            }
                            //                    else if(!ymd.parse(adhocExpDt).before(ymd.parse(date))){
                            //                        if(!ymd.parse(adhocExpDt).after(ymd.parse(date))){
                            //                           if(amt <0){
                            //                                if((amt*-1)>odLimit){
                            //                                   if((amt*-1)>(odLimit+adHocLimit)){
                            //                                       roi = roi + adHocRoi +penalRoi;
                            //                                   } else if(((amt*-1)<=(odLimit+adHocLimit)) && ((amt*-1)>odLimit)){
                            //                                       roi = roi + adHocRoi ;
                            //                                   } 
                            //                                }   
                            //                            } 
                            //                        }
                            //                    }
                        }
                    }

                    b[i][2] = formatter.format(outSt);
                    b[i][3] = Double.toString(rateOfInt + adHocRoi);
                    b[i][4] = Double.toString(dayDiff);
                    b[i][5] = formatter.format(interest + adHocInt);
                    b[i][6] = intTableCode;
                    totalInt = totalInt + interest + adHocInt;
                    closingBal = Double.parseDouble(b[i][2]);
                    product = product + Double.parseDouble(b[i][2]) * dayDiff;
                }

//                System.out.println("9.;" + new Date());

            }
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(CbsUtil.round(totalInt, 0))));
            it.setRoi(rateOfInt == 0.0 ? preRoi : rateOfInt);
            it.setIntTableCode(intTableCode);
            it.setPriAmt(outSt);
            it.setAccStatus(accStatus);


            return it;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public double[] loanIntAsPerSecurity(String acNo, String fromDt, String toDt, double noOfDays, double outstanding, String calDt, String orderBy) throws ApplicationException {
        try {
            double interest = 0d, matValue = 0d, lienValue = 0d, lienValue1 = 0d, securityRoi = 0d, margineROI = 0d, appRoi = 0d, margin = 0d, roi = 0d, addRoi = 0;
            int stmFreq = 0;
            double[] intAmt = new double[2];
            if (outstanding < 0) {
                outstanding = outstanding * -1;
                           
                List securityList = em.createNativeQuery("select ifnull(aa.matvalue,0), ifnull(aa.matdate,''), ifnull(aa.lienvalue,0), ifnull(aa.Issuedate,''), aa.SecurityRoi, aa.MargineROI, aa.AppRoi, aa.IntTableCode, aa.Margin, aa.STMFrequency, aa.Entrydate, aa.addRoi, aa.sno from "
                        + " (select sno, matvalue, matdate, lienvalue, Issuedate, ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(Margin,100) as Margin,ifnull(cast(STMFrequency as unsigned),0) as STMFrequency, Entrydate, ifnull(addRoi,0) as addRoi from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and IntTableCode is not null and entrydate <= '" + toDt + "' and ExpiryDate > '" + toDt + "' and security = 'P' and IntTableCode <>'' "
                        + "  union all "
                        + "  select sno, matvalue, matdate, lienvalue, Issuedate, ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(Margin,100) as Margin,ifnull(cast(STMFrequency as unsigned),0) as STMFrequency, Entrydate, ifnull(addRoi,0) as addRoi from loansecurity where acno = '" + acNo + "' and status = 'Active'  and IntTableCode is not null and entrydate <= '" + toDt + "' and security = 'P' and IntTableCode <>'') aa  order by " + orderBy).getResultList();
                if (!securityList.isEmpty()) {
                    for (int i = 0; i < securityList.size(); i++) {
                        Vector secVect = (Vector) securityList.get(i);
                        roi = 0;
                        matValue = Double.parseDouble(secVect.get(0).toString());
                        String matDate = secVect.get(1).toString();
                        lienValue = Double.parseDouble(secVect.get(2).toString());
                        lienValue1 = Double.parseDouble(secVect.get(2).toString());
                        String issueDate = secVect.get(3).toString();
                        securityRoi = Double.parseDouble(secVect.get(4).toString());
                        margineROI = Double.parseDouble(secVect.get(5).toString());
                        appRoi = Double.parseDouble(secVect.get(6).toString());
                        String intTableCode = secVect.get(7).toString();
                        margin = Double.parseDouble(secVect.get(8).toString());
                        stmFreq = Integer.parseInt(secVect.get(9).toString());
                        addRoi = Double.parseDouble(secVect.get(11).toString());
//                        lienValue = lienValue - ((lienValue * margin) / 100);
                        if (stmFreq == 7) {
                            lienValue = lienValue;
                            outstanding = outstanding;
                        } else {
                            if ((outstanding <= lienValue) && (outstanding != 0)) {
                                lienValue = outstanding;
                                outstanding = 0;
                                i = securityList.size() - 1;
                            } else {
                                lienValue = lienValue;
                                outstanding = outstanding - lienValue;
                            }
                        }
                        roi = securityRoi + addRoi + Double.parseDouble(getROI(intTableCode, (stmFreq == 7 ? lienValue1 : lienValue), calDt));
                        if (stmFreq == 7) {
                            interest = interest + ((outstanding * roi * noOfDays) / 36500);
                            if ((outstanding <= lienValue) && (outstanding != 0)) {
                                lienValue = outstanding;
                                outstanding = 0;
                                i = securityList.size() - 1;
                            } else {
                                lienValue = lienValue;
                                outstanding = outstanding - lienValue;
                            }
                        } else {
                            interest = interest + ((lienValue * roi * noOfDays) / 36500);
                        }
                    }
                }
                if ((outstanding > 0) && (stmFreq != 7)) {
                    if (securityList.isEmpty()) {
                        securityList = em.createNativeQuery(" select ifnull(matvalue,0), ifnull(matdate,''), ifnull(lienvalue,0), ifnull(Issuedate,''), ifnull(SecurityRoi,0) as SecurityRoi, "
                                + "ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(Margin,100) as Margin,"
                                + "ifnull(cast(STMFrequency as unsigned),0) as STMFrequency, Entrydate, ifnull(addRoi,0) as addRoi "
                                + "from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and IntTableCode is not null and "
                                + "entrydate <= '" + toDt + "' and IntTableCode <>'' "
                                + " and sno in (select max(sno) from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' "
                                + "and IntTableCode is not null and entrydate <= '" + toDt + "' and IntTableCode <>'')").getResultList();
                        if (!securityList.isEmpty()) {
                            Vector secVect = (Vector) securityList.get(0);
                            roi = 0;
                            matValue = Double.parseDouble(secVect.get(0).toString());
                            String matDate = secVect.get(1).toString();
                            lienValue = Double.parseDouble(secVect.get(2).toString());
                            lienValue1 = Double.parseDouble(secVect.get(2).toString());
                            String issueDate = secVect.get(3).toString();
                            securityRoi = Double.parseDouble(secVect.get(4).toString());
                            margineROI = Double.parseDouble(secVect.get(5).toString());
                            appRoi = Double.parseDouble(secVect.get(6).toString());
                            String intTableCode = secVect.get(7).toString();
                            margin = Double.parseDouble(secVect.get(8).toString());
                            stmFreq = Integer.parseInt(secVect.get(9).toString());
                            addRoi = Double.parseDouble(secVect.get(11).toString());
                            lienValue = outstanding;
                            roi = securityRoi + addRoi + Double.parseDouble(getROI(intTableCode, (stmFreq == 7 ? lienValue1 : lienValue), calDt));
                        }
                    }
                    interest = interest + ((outstanding * roi * noOfDays) / 36500);
                }
            }
            intAmt[0] = interest * -1;
            intAmt[1] = roi;
            return intAmt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public LoanIntCalcList accWiseCaAccountIntCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException {
        //1. Select the Scheme Code, Interest Code according to ACNO.
        //2. Make the slab according to Interest Code
        //3.
        //UserTransaction ut = context.getUserTransaction();


        LoanIntCalcList it = new LoanIntCalcList();
        try {
            String schemeCode = "";
            String intTableCode = "";
            String calcMethod = "";
            String intAppFreq = "";
            String calcLevel = "";
            double rateOfInt = 0;
            double sanctionLimit = 0;
            double outSt = 0;
            String bankCalcMethod;
            String intCalcUptoDt = "";
            String nextIntCalcDt = "";
            String acNature = "";
            double subsidyAmt = 0;
            String subsidyExpDt = "";
            //List unionAllTableList = null;
            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank Calculation Method doesn't Exists in ParameterInfo.");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }

            /*Getting AcNature*/
            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + fTSPosting43CBSBean.getAccountCode(acNo) + "'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Account Nature doesn't exists.");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }
            String custName = "";
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            }
            /**
             * ---Account is exists in Loan Secondary Table---*
             */
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, c.CR_INT_RATE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, "
                    + " A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, "
                    + " IFNULL(A.PEGG_FREQ,0), A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, "
                    + " IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,'')  from cbs_loan_acc_mast_sec A, "
                    + " loan_appparameter B, cbs_scheme_currency_details c where A.ACNO = B.ACNO and c.SCHEME_TYPE = substring(A.ACNO,3,2) AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    schemeCode = (String) SecDetailsVect.get(1);
                    intTableCode = (String) SecDetailsVect.get(2);
                    calcMethod = (String) SecDetailsVect.get(8);
                    intAppFreq = (String) SecDetailsVect.get(10);
                    calcLevel = (String) SecDetailsVect.get(11);
                    //rateOfInt = Double.parseDouble(SecDetailsVect.get(16).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = ymd.format(ymmd.parse(dateAdd(caAccountWiseFromDt(acNo, brnCode), -1)));
                    nextIntCalcDt = ymd.format(ymmd.parse(caAccountWiseFromDt(acNo, brnCode)));
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                }
            }
            if (intTableCode.equalsIgnoreCase("")) {
                throw new ApplicationException("Please define the Credit Interest Rate Code in this account " + acNo + "'s Scheme " + schemeCode);
            }

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
                nextIntCalcDt = getNextToDt(brnCode, acNo, fromDt, calcMethod, 0);
            } else {
                nxtCalDt = ymd.parse(nextIntCalcDt);
                calendar.setTime(nxtCalDt);
                calendar.add(Calendar.DATE, 0);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(brnCode, acNo, fromDt, calcMethod, 0);
            }
            //** it will discuss with Sogendra Sir in future
            //toDt = ymmd.format(ymmd.parse(nextIntCalcDt));

            String[][] b = createFromDtArray(acNo, fromDt, toDt, intAppFreq, acNature, intTableCode, "N", "N");

            long fromTimeStamp = ymd.parse(b[0][0]).getTime();
            long intCalcUpToDtStamp = ymd.parse(intCalcUptoDt).getTime();
            double totalInt = 0d;
            double product = 0d;
            double closingBal = 0d;
            /*Check that there is any value date transaction or not. Interest calculation in case of Value Date transaction*/
            if (fromTimeStamp < intCalcUpToDtStamp) {
                /* For getting from date before the value date*/
                String preIntPostingFromDt = getFromDtBeforeValueDt(b[0][0], acNo, acNature);

                /**
                 * For getting previous interest posting dates
                 */
                List<String> preIntPostingDtList = getIntPostingDtlist(b[0][0], acNo, acNature);
                /* Create double dim array of from date and to date*/
                String[][] b1 = createFromDtArray(acNo, preIntPostingFromDt, toDt, intAppFreq, acNature, intTableCode, "N", "N");
                for (int i = 0; i < b1.length - 1; i++) {
                    String fDate = b1[i][0].toString();
                    String tDate = b1[i][1].toString();

                    /* Making the outstading according to the Calculation Level
                     IF Calculation Level is Sanction or Limit==================*/
                    /*1. Oustanding */
                    if (calcLevel.equals("L")) {
                        outSt = Double.parseDouble(outStandingCaAccountAsOnDate(acNo, fDate));
                    } else if (calcLevel.equals("S")) {
                        outSt = sanctionLimit * -1;
                    }
                    /*
                     * Did the implementation of Subsidy Amount 
                     */
                    if ((subsidyAmt != 0)) {
                        if (ymd.parse(subsidyExpDt).after(ymd.parse(fDate)) || ymd.parse(subsidyExpDt).equals(ymd.parse(fDate))) {
                            outSt = outSt + subsidyAmt;
                            if (outSt >= 0) {
                                outSt = 0;
                            }
                        }
                    }
                    if (outSt >= 0) {
                        /* IF RATE CODE is "Absolute Fixed/Fixed/Floating" =========*/
                        /*2. ROI*/
                        String roiGet = getROI(intTableCode, outSt, fDate);
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                    /*3. No. of days between From Date and To Date*/
                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                    /* In each slab, No. of days is increasing by 1  */
                    double dayDiff = dDiff + 1;

                    /*4. Interest Calculation*/
                    double interest = rateOfInt * dayDiff * outSt / 36500;
                    //System.out.println("AcNo:" + acNo + "; Roi:" + rateOfInt + "; int:" + interest + "; outSt:" + outSt);
                    /* 
                     * AdHoc Interest ROI 
                     */
                    double adHocRoi = 0;
                    double adHocInt = 0;
                    double odLimit = 0;
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, ifnull(AdhocROI,0), ifnull(ODLimit,0), ifnull(Adhoclimit,0), PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
                        if (!adHocDateList.isEmpty()) {
                            Vector adHocDateVect = (Vector) adHocDateList.get(0);
                            String adhocAppDt = adHocDateVect.get(0).toString();
                            String adhocExpDt = adHocDateVect.get(1).toString();
                            adHocRoi = Double.parseDouble(adHocDateVect.get(2).toString());
                            odLimit = Double.parseDouble(adHocDateVect.get(3).toString());
                            double adHocLimit = Double.parseDouble(adHocDateVect.get(4).toString());
                            //double loanPenalRoi = Double.parseDouble(adHocDateVect.get(5).toString());
//                            if ((ymd.parse(adhocAppDt).before(ymd.parse(fDate))) || (ymd.parse(adhocAppDt).equals(ymd.parse(fDate)))) {
//                                if (!ymd.parse(fDate).after(ymd.parse(adhocExpDt))) {
//                                    if (outSt < 0) {
//                                        if (Math.abs(outSt) > odLimit) {
//                                            if (Math.abs(outSt) > (odLimit + adHocLimit)) {
//                                                adHocRoi = adHocRoi;
//                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
//                                            } else if ((Math.abs(outSt) <= (odLimit + adHocLimit)) && (Math.abs(outSt) > odLimit)) {
//                                                adHocRoi = adHocRoi;
//                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
                            //                    else if(!ymd.parse(adhocExpDt).before(ymd.parse(date))){
                            //                        if(!ymd.parse(adhocExpDt).after(ymd.parse(date))){
                            //                           if(amt <0){
                            //                                if((amt*-1)>odLimit){
                            //                                   if((amt*-1)>(odLimit+adHocLimit)){
                            //                                       roi = roi + adHocRoi +penalRoi;
                            //                                   } else if(((amt*-1)<=(odLimit+adHocLimit)) && ((amt*-1)>odLimit)){
                            //                                       roi = roi + adHocRoi ;
                            //                                   } 
                            //                                }   
                            //                            } 
                            //                        }
                            //                    }
                        }
                    }
                    b1[i][2] = formatter.format(outSt);
                    b1[i][3] = Double.toString(rateOfInt + adHocRoi);
                    b1[i][4] = Double.toString(dayDiff);
                    b1[i][5] = formatter.format(interest + adHocInt);
                    b1[i][6] = intTableCode;
                    totalInt = totalInt + interest + adHocInt;
                    closingBal = Double.parseDouble(b1[i][2]);
                    double postedInt = 0d;
                    if (isPostingDt(fDate, preIntPostingDtList)) {
                        postedInt = getPostedInterest(acNo, fDate, acNature);
                        if (postedInt - totalInt * -1 > 0) {
                            totalInt = totalInt * -1 - postedInt;
                        } else {
                            totalInt = postedInt - totalInt * -1;
                        }
                    }
                    product = product + Double.parseDouble(b1[i][2]) * dayDiff;
                }
            } else {
                /*Interest calculation in case of Value Date transaction*/
                for (int i = 0; i < b.length - 1; i++) {
                    String fDate = b[i][0].toString();
                    String tDate = b[i][1].toString();

                    /* Making the outstading according to the Calculation Level
                     IF Calculation Level is Sanction or Limit==================*/
                    /*1. Oustanding */
                    if (calcLevel.equals("L")) {
                        outSt = Double.parseDouble(outStandingCaAccountAsOnDate(acNo, fDate));
                    } else if (calcLevel.equals("S")) {
                        outSt = sanctionLimit * -1;
                    }
                    /*
                     * Did the implementation of Subsidy Amount 
                     */
                    if ((subsidyAmt != 0)) {
                        if (ymd.parse(subsidyExpDt).after(ymd.parse(fDate)) || ymd.parse(subsidyExpDt).equals(ymd.parse(fDate))) {
                            outSt = outSt + subsidyAmt;
                            if (outSt <= 0) {
                                outSt = 0;
                            }
                        }
                    }
                    if (outSt >= 0) {
                        /* IF RATE CODE is "Absolute Fixed/Fixed/Floating" =========*/
                        /*2. ROI*/
                        String roiGet = getROI(intTableCode, outSt, fDate);
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                    /*3. No. of days between From Date and To Date*/
                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                    /* In each slab, No. of days is increasing by 1  */
                    double dayDiff = dDiff + 1;

                    /*4. Interest Calculation*/
                    double interest = rateOfInt * dayDiff * outSt / 36500;
                    /* 
                     * AdHoc Interest ROI 
                     */
                    double adHocRoi = 0;
                    double adHocInt = 0;
                    double odLimit = 0;
//                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                        List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, ifnull(AdhocROI,0), ifnull(ODLimit,0), ifnull(Adhoclimit,0), PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
//                        if (!adHocDateList.isEmpty()) {
//                            Vector adHocDateVect = (Vector) adHocDateList.get(0);
//                            String adhocAppDt = adHocDateVect.get(0).toString();
//                            String adhocExpDt = adHocDateVect.get(1).toString();
//                            adHocRoi = Double.parseDouble(adHocDateVect.get(2).toString());
//                            odLimit = Double.parseDouble(adHocDateVect.get(3).toString());
//                            double adHocLimit = Double.parseDouble(adHocDateVect.get(4).toString());
//                            //double loanPenalRoi = Double.parseDouble(adHocDateVect.get(5).toString());
////                            if ((ymd.parse(adhocAppDt).before(ymd.parse(fDate))) || (ymd.parse(adhocAppDt).equals(ymd.parse(fDate)))) {
////                                if (!ymd.parse(fDate).after(ymd.parse(adhocExpDt))) {
////                                    if (outSt > 0) {
////                                        if (Math.abs(outSt) > odLimit) {
////                                            if (Math.abs(outSt) > (odLimit + adHocLimit)) {
////                                                adHocRoi = adHocRoi;
////                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
////                                            } else if ((Math.abs(outSt) <= (odLimit + adHocLimit)) && (Math.abs(outSt) > odLimit)) {
////                                                adHocRoi = adHocRoi;
////                                                adHocInt = adHocRoi * dayDiff * (outSt - odLimit * -1) / 36500;
////                                            }
////                                        }
////                                    }
////                                }
////                            }
//                            //                    else if(!ymd.parse(adhocExpDt).before(ymd.parse(date))){
//                            //                        if(!ymd.parse(adhocExpDt).after(ymd.parse(date))){
//                            //                           if(amt <0){
//                            //                                if((amt*-1)>odLimit){
//                            //                                   if((amt*-1)>(odLimit+adHocLimit)){
//                            //                                       roi = roi + adHocRoi +penalRoi;
//                            //                                   } else if(((amt*-1)<=(odLimit+adHocLimit)) && ((amt*-1)>odLimit)){
//                            //                                       roi = roi + adHocRoi ;
//                            //                                   } 
//                            //                                }   
//                            //                            } 
//                            //                        }
//                            //                    }
//                        }
//                    }

                    b[i][2] = formatter.format(outSt);
                    b[i][3] = Double.toString(rateOfInt + adHocRoi);
                    b[i][4] = Double.toString(dayDiff);
                    b[i][5] = formatter.format(interest + adHocInt);
                    b[i][6] = intTableCode;
                    totalInt = totalInt + interest + adHocInt;
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


            return it;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public LoanIntCalcList accWiseLoanIntCalcTheftNew(List<SavingIntRateChangePojo> roiList, List<FdRoiDetail> FdRoiList, String fromDt, String toDt, String acNo, String brnCode, String acNature) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        String roi;
        LoanIntCalcList it = new LoanIntCalcList();

        try {

            String schemeCode = null;
            String intTableCode = null;
            int moratoriumPd;
            double accPrefDr = 0;
            double accPrefCr = 0;
            String rateCode = null;
            String disbType;
            String calcMethod = null;
            String calcOn;
            String intAppFreq = null;
            String calcLevel = null;
            String compoundFreq;
            int peggFreq = 0;
            int loanPdMonth;
            int loanPdDay;
            double rateOfInt = 0;
            double fdRoiGet = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            // acNature = fTSPosting43CBSBean.getAccountNature(acNo);
            String custName = "";
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.intdeposit, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT,B.CustName from cbs_loan_acc_mast_sec A, accountmaster B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String acno = (String) SecDetailsVect.get(0);
                    schemeCode = (String) SecDetailsVect.get(1);
                    intTableCode = (String) SecDetailsVect.get(2);
                    moratoriumPd = Integer.parseInt(SecDetailsVect.get(3).toString());
                    accPrefDr = Double.parseDouble(SecDetailsVect.get(4).toString());
                    accPrefCr = Double.parseDouble(SecDetailsVect.get(5).toString());
                    rateCode = (String) SecDetailsVect.get(6);
                    disbType = (String) SecDetailsVect.get(7);
                    calcMethod = (String) SecDetailsVect.get(8);
                    calcOn = (String) SecDetailsVect.get(9);
                    intAppFreq = (String) SecDetailsVect.get(10);
                    calcLevel = (String) SecDetailsVect.get(11);
                    compoundFreq = (String) SecDetailsVect.get(12);
                    peggFreq = (SecDetailsVect.get(13) == null ? 0 : Integer.parseInt(SecDetailsVect.get(13).toString()));
                    loanPdMonth = Integer.parseInt(SecDetailsVect.get(14).toString());
                    loanPdDay = Integer.parseInt(SecDetailsVect.get(15).toString());
                    rateOfInt = Double.parseDouble(SecDetailsVect.get(16).toString());
                    intCalcUptoDt = SecDetailsVect.get(17).toString();
                    intCompTillDt = SecDetailsVect.get(18).toString();
                    nextIntCalcDt = SecDetailsVect.get(19).toString();
                    custName = SecDetailsVect.get(20).toString();
                }
            }

            Calendar calendar = Calendar.getInstance();
            Date frDate;
            if (ymmd.format(ymd.parse(intCalcUptoDt)).equalsIgnoreCase("19000101")) {
                frDate = ymmd.parse(fromDt);
                calendar.setTime(frDate);
            } else {
                frDate = ymmd.parse(fromDt);
                calendar.setTime(frDate);
            }

            fromDt = ymmd.format(calendar.getTime());
            Date nxtCalDt;
            if (ymmd.format(ymd.parse(nextIntCalcDt)).equalsIgnoreCase("19000101")) {
                nxtCalDt = ymmd.parse(fromDt);
                calendar.setTime(frDate);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(brnCode, acNo, fromDt, calcMethod, 0);
            } else {
                nxtCalDt = ymd.parse(nextIntCalcDt);
                calendar.setTime(nxtCalDt);
                calendar.add(Calendar.DATE, 0);
                nextIntCalcDt = ymmd.format(calendar.getTime());
                nextIntCalcDt = getNextToDt(brnCode, acNo, fromDt, calcMethod, 0);
            }
            //** it will discuss with Sogendra Sir in future
            //toDt = ymmd.format(ymmd.parse(nextIntCalcDt));
            ArrayList datesFrom = new ArrayList();
            if (intAppFreq.equalsIgnoreCase("S")) {
                /* SIMPLE ==========================================================*/
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ").getResultList();
                }

            } else if (intAppFreq.equalsIgnoreCase("C")) {
                /* COMPOUNDING ====================================================*/
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ").getResultList();
                }
            }
            String a[][] = new String[unionAllTableList.size()][7];

            datesFrom.add(ymd.format(ymmd.parse(fromDt)));

            if (!unionAllTableList.isEmpty()) {
                for (int i = 0; i < unionAllTableList.size(); i++) {
                    Vector unionAllTableVect = (Vector) unionAllTableList.get(i);
                    a[i][0] = unionAllTableVect.get(0).toString();
                    if (i == 0) {
                        /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
                        if (ymmd.parse(fromDt).equals(ymd.parse(unionAllTableVect.get(0).toString()))) {
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                datesFrom.add(a[i][1]);
                            }
                        } else {
                            getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            datesFrom.add(a[i][0]);
                            getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                datesFrom.add(a[i][1]);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                            }
                        }
                    } else if (i > 0 && i < unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                    } else if (i == unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        a[i][1] = ymd.format(ymmd.parse(toDt));
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                            /*=======Getting the ROI Change Date in Current i Position=======================*/
                            getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                            datesFrom.add(a[i][1]);
                            getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                        } else {
                            datesFrom.add(a[i][1]);
                        }
                    }
                }
                Collections.sort(datesFrom);
            } else {
                getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                datesFrom.add(ymd.format(ymmd.parse(toDt)));
                getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                Collections.sort(datesFrom);
            }
            double minFdAmt = getMinBal(acNo, fromDt, toDt);
            //String roiGet = acOpenFacadeRemote.getROI(schemeCode, outSt, fromDt);
//            rateOfInt = roiList.get(0).getRoi();
//            for (int k = 0; k < roiList.size(); k++) {
//                SavingIntRateChangePojo obj = roiList.get(k);
//                String slabFrDt = obj.getFrDt();
//                String slabToDt = obj.getToDt();
//                double sbRoi = obj.getRoi();
//                System.out.println("slabFrDt: "+slabFrDt+"  slabToDt : " +slabToDt+" sbRoi: "+sbRoi);
//            }
//            List fdroiList = em.createNativeQuery("select Interest_rate,Applicable_Date,ST,SC From td_slab where fromDays <= 365.0 and toDays >=365.0 and  fromAmount <= " + minFdAmt + " and toAmount >= " + minFdAmt + " and Applicable_Date in(select max(applicable_Date) from td_slab where applicable_date<='" + toDt + "')").getResultList();
//            Vector vtr = (Vector) fdroiList.get(0);
//            fdRoiGet = Double.parseDouble(vtr.get(0).toString());

            for (int i = 0; i < FdRoiList.size(); i++) {
                FdRoiDetail obj = FdRoiList.get(i);
                //fromAmount <= 1400 and toAmount >= 1400
                if (obj.getFromAmt() == minFdAmt || obj.getFromAmt() < minFdAmt && obj.getToAmt() == minFdAmt || obj.getToAmt() > minFdAmt) {
                    fdRoiGet = obj.getFdRoi();
                }
            }
            String b[][] = new String[datesFrom.size()][7];
            if (!datesFrom.isEmpty()) {
                for (int i = 0; i < datesFrom.size(); i++) {
                    if (i == 0) {
                        b[i][0] = datesFrom.get(i).toString();
                        firstDisbDt = datesFrom.get(i).toString();
                    } else if (i > 0 && i < datesFrom.size() - 1) {
                        b[i][0] = datesFrom.get(i).toString();
                        b[i - 1][1] = ymd.format(ymmd.parse(dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
                    } else if (i == datesFrom.size() - 1) {
                        b[i - 1][1] = datesFrom.get(i).toString();
                    }
                }
            }
            double totalInt = 0.0f;
            double totalSbProduct = 0d, totalFdProduct = 0d, totalSbInt = 0d, totalFdInt = 0d;
//            List dtWiseBalList = getBalByDate(acNo, fromDt, toDt);
//            Map<String, Double> balMap = new HashMap<String, Double>();
//            for (int j = 0; j < dtWiseBalList.size(); j++) {
//                Vector balVector = (Vector) dtWiseBalList.get(j);
//                balMap.put(balVector.get(1).toString(), Double.parseDouble(balVector.get(0).toString()));
//            }
            System.out.println(acNo);
            for (int i = 0; i < b.length - 1; i++) {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();
                outSt = common.getBalanceOnDate(acNo, fDate);
                String roiGet = acOpenFacadeRemote.getROI(schemeCode, outSt, fDate);  //Get Roi Modify on dt 0407
                rateOfInt = Double.parseDouble(roiGet);
//                if (fDate.length() > 10) {
//                    outSt = balMap.get(ymmd.format(ymdhms.parse(fDate)));
//                } else {
//                    outSt = balMap.get(ymmd.format(ymd.parse(fDate)))==null? 0:balMap.get(ymmd.format(ymd.parse(fDate)));
//                }
                double dayDiff = (double) CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                /* In each slab, No. of days is increasing by 1  */
                dayDiff = dayDiff + 1;
                /*4. Interest Calculation*/
                double interest = rateOfInt * dayDiff * (outSt - minFdAmt) / 36500;
                double fdInterest = fdRoiGet * dayDiff * minFdAmt / 36500;

                b[i][2] = formatter.format(outSt);
                b[i][3] = Double.toString(rateOfInt);
                b[i][4] = Double.toString(dayDiff);
                b[i][5] = formatter.format(interest);
                b[i][6] = intTableCode;
                totalInt = totalInt + interest;
                if (i == 0) {
                    firstDt = fDate;
                } else if (i == b.length - 2) {
                    lastDt = tDate;
                }
                closingBal = Double.parseDouble(b[i][2]);
                roi = b[i][3];
                product = product + Double.parseDouble(b[i][2]);

                itProduct.setAcNo(acNo);
                itProduct.setCustName(custName);
                itProduct.setFirstDt(fDate);
                itProduct.setLastDt(tDate);
                itProduct.setRoi(rateOfInt);

                itProduct.setProduct((outSt - minFdAmt) * dayDiff);
                itProduct.setFdProduct(minFdAmt * dayDiff);
                itProduct.setClosingBal(dayDiff);
                itProduct.setTotalInt(interest);
                itProduct.setIntTableCode(intTableCode);
                itProduct.setSbInt(Double.parseDouble(formatter.format(interest)));
                itProduct.setFdInt(Double.parseDouble(formatter.format(fdInterest)));
                itProduct.setFdRoi(fdRoiGet);
                itProduct.setSbFdTotalIntAmt(interest + fdInterest);
                itProduct = new LoanIntCalcList();
                totalSbProduct = totalSbProduct + ((outSt - minFdAmt) * dayDiff);
                totalFdProduct = minFdAmt * dayDiff;
                totalSbInt = totalSbInt + Double.parseDouble(formatter.format(interest));
                totalFdInt = totalFdInt + Double.parseDouble(formatter.format(fdInterest));

            }
            totalInt = totalSbInt + totalFdInt;
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setFdProduct(totalFdProduct);
            it.setProduct(totalSbProduct);
            it.setSbInt(totalSbInt);
            it.setFdInt(totalFdInt);
            it.setTotalInt(totalInt);
            it.setRoi(rateOfInt);
            it.setFdRoi(fdRoiGet);
            it.setIntTableCode(intTableCode);

            return it;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<FdRoiDetail> getFdRoiData(String dt) throws ApplicationException {
        List<FdRoiDetail> dataList = new ArrayList<FdRoiDetail>();
        try {
            List list = em.createNativeQuery("select interest_rate,FromAmount,cast(ToAmount as decimal(25,2)) From td_slab where fromDays <= 365.0 and toDays >=365.0 and Applicable_Date in(select max(applicable_Date) from td_slab where applicable_date<='" + dt + "')").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                FdRoiDetail obj = new FdRoiDetail();
                obj.setFdRoi(Double.parseDouble(vtr.get(0).toString()));
                obj.setFromAmt(Double.parseDouble(vtr.get(1).toString()));
                obj.setToAmt(Double.parseDouble(vtr.get(2).toString()));
                dataList.add(obj);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List getBalByDate(String acNo, String frDt, String toDt) throws ApplicationException {

        try {
            return em.createNativeQuery("select ifnull(sum(ifnull(a.cramt,0)) - sum(ifnull(a.dramt,0)),0) as bal,'" + frDt + "' from recon a where a.auth= 'Y' and a.acno = '" + acNo + "' and a.dt <= '" + frDt + "' "
                    + "union "
                    + "select (select sum(ifnull(b.cramt,0)) - sum(ifnull(b.dramt,0)) as bal from recon b where b.auth= 'Y' and b.acno = a.ACNo and b.dt<=a.Dt) as bal,date_format(a.dt,'%Y%m%d') from recon a where a.auth= 'Y' and a.acno = '" + acNo + "' and a.dt between '" + frDt + "' and '" + toDt + "' group by a.acno, a.dt "
                    + "union "
                    + "select ifnull(sum(ifnull(a.cramt,0)) - sum(ifnull(a.dramt,0)),0) as bal,date_format(max(a.dt),'%Y%m%d') from recon a where a.auth= 'Y' and a.acno = '" + acNo + "' and a.dt <= '" + toDt + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getMinBal(String acNo, String frDt, String toDt) throws ApplicationException {
        double minBal = 0d;
        try {
            List minBalList = em.createNativeQuery("select  min(bal) from(select  ifnull(sum(ifnull(a.cramt,0)) - sum(ifnull(a.dramt,0)),0)as bal "
                    + "from recon a where a.auth= 'Y' and a.acno = '" + acNo + "' and a.dt <= '" + frDt + "' "
                    + "union "
                    + "select min(bal)as bal from(select (select sum(ifnull(b.cramt,0)) - sum(ifnull(b.dramt,0)) as bal from recon b where b.auth= 'Y' "
                    + "and b.acno = a.ACNo and b.dt<=a.Dt) as bal from recon a where a.auth= 'Y' and a.acno = '" + acNo + "' and a.dt between '" + frDt + "' "
                    + "and '" + toDt + "' group by a.acno, a.dt)m "
                    + "union "
                    + "select  ifnull(sum(ifnull(a.cramt,0)) - sum(ifnull(a.dramt,0)),0) as bal from recon a where a.auth= 'Y' and a.acno = '" + acNo + "' "
                    + "and a.dt <= '" + toDt + "')minBal").getResultList();

            if (!minBalList.isEmpty()) {
                Vector balVector = (Vector) minBalList.get(0);
                minBal = Double.parseDouble(balVector.get(0).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return minBal;

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
    public String[][] createFromDtArray(String acNo, String fromDt, String toDt, String intAppFreq, String acNature,
            String intTableCode, String SecurityAddExpFlag, String odPenalFlag) throws ApplicationException {
        try {
            ArrayList datesFrom = new ArrayList();
            List unionAllTableList = getFromDtList(acNo, fromDt, toDt, intAppFreq, acNature);
            /*This code is used for Khattri regarding OD Limit modification date addition in Penal int calc*/
            if (acNature.equalsIgnoreCase("CA") && odPenalFlag.equalsIgnoreCase("Y")) {
                List limitModList = em.createNativeQuery("select DATE_FORMAT(aa.enterdate,'%Y-%m-%d') from "
                        + "(select aclimit, enterdate, txnid from loan_oldinterest where acno =  '" + acNo + "' and enterdate between '" + fromDt + "' and '" + toDt + "') aa , "
                        + "(select max(TXNID) as txnid, enterdate from loan_oldinterest where acno =  '" + acNo + "' and enterdate between '" + fromDt + "' and '" + toDt + "' group by enterdate) bb where "
                        + "aa.enterdate = bb.enterdate and aa.txnid = bb.txnid order by aa.enterdate, aa.txnid").getResultList();
//                unionAllTableList.add(limitModList);
                if (!limitModList.isEmpty()) {
                    for (int i = 0; i < limitModList.size(); i++) {
                        Vector unionAllVect = (Vector) limitModList.get(i);
                        if (!(ymd.parse(unionAllVect.get(0).toString()).equals(ymmd.parse(fromDt)) || ymd.parse(unionAllVect.get(0).toString()).equals(ymmd.parse(toDt)))) {
                            datesFrom.add(ymd.format(ymd.parse(unionAllVect.get(0).toString())));
                        }
                    }
                }
            }

            String a[][] = new String[unionAllTableList.size()][7];
            datesFrom.add(ymd.format(ymmd.parse(fromDt)));
            if (!unionAllTableList.isEmpty()) {
                for (int i = 0; i < unionAllTableList.size(); i++) {
                    Vector unionAllTableVect = (Vector) unionAllTableList.get(i);
                    a[i][0] = ymd.format(ymd.parse(unionAllTableVect.get(0).toString()));
                    if (i == 0) {
                        /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
                        if (ymmd.parse(fromDt).equals(ymd.parse(unionAllTableVect.get(0).toString()))) {
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                                    datesFrom = getSecurityAddExpDtChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                }
                                datesFrom.add(a[i][1]);
                            }
                        } else {
                            getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            datesFrom.add(a[i][0]);
                            getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                                datesFrom = getSecurityAddExpDtChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            }
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                datesFrom.add(a[i][1]);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                                    datesFrom = getSecurityAddExpDtChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                }
                            }
                        }
                    } else if (i > 0 && i < unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                            datesFrom = getSecurityAddExpDtChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        }
                    } else if (i == unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        a[i][1] = ymd.format(ymmd.parse(toDt));
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                            datesFrom = getSecurityAddExpDtChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        }
                        if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                            /*=======Getting the ROI Change Date in Current i Position=======================*/
                            getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                            datesFrom.add(a[i][1]);
                            getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                            if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                                datesFrom = getSecurityAddExpDtChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                            }
                        } else {
                            datesFrom.add(a[i][1]);
                        }
                    }
                }
                Collections.sort(datesFrom);
            } else {
                getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                datesFrom.add(ymd.format(ymmd.parse(toDt)));
                getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                if (SecurityAddExpFlag.equalsIgnoreCase("Y")) {
                    datesFrom = getSecurityAddExpDtChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                }
                Collections.sort(datesFrom);
            }
            String b[][] = new String[datesFrom.size()][7];
            if (!datesFrom.isEmpty()) {
                for (int i = 0; i < datesFrom.size(); i++) {
                    if (i == 0) {
                        b[i][0] = datesFrom.get(i).toString();
                    } else if (i > 0 && i < datesFrom.size() - 1) {
                        b[i][0] = datesFrom.get(i).toString();
                        b[i - 1][1] = ymd.format(ymmd.parse(dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
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

    public String getMinBalFromDtToToDt(String fromDt, String toDt, String acNo, String intTableCode) throws ApplicationException {
        String minbalAmt = "";
        try {
            ArrayList datesFrom = new ArrayList();

            List result = em.createNativeQuery("select INT_APP_FREQ from cbs_loan_acc_mast_sec where acno = '" + acNo + "'").getResultList();
            Vector vtr = (Vector) result.get(0);
            String intAppFreq = vtr.get(0).toString();

            List unionAllTableList = getFromDtList(acNo, fromDt, toDt, intAppFreq, common.getAcNatureByAcNo(acNo));

            // List unionAllTableList = loanIntCalRemotr.

            String a[][] = new String[unionAllTableList.size()][7];
            datesFrom.add(ymd.format(ymmd.parse(fromDt)));
            if (!unionAllTableList.isEmpty()) {
                for (int i = 0; i < unionAllTableList.size(); i++) {
                    Vector unionAllTableVect = (Vector) unionAllTableList.get(i);
                    a[i][0] = unionAllTableVect.get(0).toString();
                    if (i == 0) {
                        /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
                        if (ymmd.parse(fromDt).equals(ymd.parse(unionAllTableVect.get(0).toString()))) {
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                datesFrom.add(a[i][1]);
                            }
                        } else {
                            getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            datesFrom.add(a[i][0]);
                            getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                datesFrom.add(a[i][1]);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                            }
                        }
                    } else if (i > 0 && i < unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                    } else if (i == unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        a[i][1] = ymd.format(ymmd.parse(toDt));
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                            /*=======Getting the ROI Change Date in Current i Position=======================*/
                            getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                            datesFrom.add(a[i][1]);
                            getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                        } else {
                            datesFrom.add(a[i][1]);
                        }
                    }
                }
                Collections.sort(datesFrom);
            } else {
                getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                datesFrom.add(ymd.format(ymmd.parse(toDt)));
                getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                Collections.sort(datesFrom);
            }

            List bl = new ArrayList();
            for (int j = 0; j < datesFrom.size(); j++) {
                String dt = datesFrom.get(j).toString();
                double bal = common.getBalanceOnDate(acNo, ymmd.format(ymd.parse(dt)));
                bl.add(bal);
            }

            Collections.sort(bl);
            System.out.print(bl);
            minbalAmt = bl.get(0).toString();


        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return minbalAmt;
    }

    /**
     *
     * @param acNo
     * @param dt
     * @return
     */
    private double getPostedInterest(String acNo, String dt, String acNature) throws ApplicationException {
        try {
            String query = "";
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                query = "select dramt from ca_recon where acno  = '" + acNo + "' and valuedt = '" + dt + "' and trandesc in (3,4) and ty =1";
            } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                query = "select dramt from loan_recon where acno  = '" + acNo + "' and valuedt = '" + dt + "' and trandesc in (3,4) and ty =1";
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                query = "select dramt from loan_recon where acno  = '" + acNo + "' and valuedt = '" + dt + "' and trandesc in (3,4) and ty =1";
            }
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
     * @param acNature
     * @return
     */
    private String getFromDtBeforeValueDt(String valueDt, String acNo, String acNature) throws ApplicationException {
        try {
            String query = "";
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                query = "select date_format(valuedt,'%Y%m%d') from ca_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt = "
                        + "(select max(valuedt) from ca_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt < '" + valueDt + "')";
            } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                query = "select date_format(valuedt,'%Y%m%d') from loan_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt = "
                        + "(select max(valuedt) from loan_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt < '" + valueDt + "')";
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                query = "select date_format(valuedt,'%Y%m%d') from loan_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt = "
                        + "(select max(valuedt) from loan_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt < '" + valueDt + "')";
            }
            List fromDtList = em.createNativeQuery(query).getResultList();
            String preIntPostingFromDt = "";
            if (!fromDtList.isEmpty()) {
                Vector fromDtVect = (Vector) fromDtList.get(0);
                preIntPostingFromDt = dateAdd(fromDtVect.elementAt(0).toString(), 1);
            } else {
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    query = "select date_format(valuedt,'%Y%m%d') from ca_recon where acno='" + acNo + "' and valuedt = (select min(valuedt) from ca_recon where acno='" + acNo + "')";
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    query = "select date_format(valuedt,'%Y%m%d') from loan_recon where acno='" + acNo + "' and valuedt = (select min(valuedt) from loan_recon where acno='" + acNo + "')";
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    query = "select date_format(valuedt,'%Y%m%d') from loan_recon where acno='" + acNo + "' and valuedt = (select min(valuedt) from loan_recon where acno='" + acNo + "')";
                }
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
     * @param acNature
     * @return
     */
    private List<String> getIntPostingDtlist(String valueDt, String acNo, String acNature) throws ApplicationException {
        try {
            String query = "";
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                query = "select valuedt from ca_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt >= '" + valueDt + "'";
            } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                query = "select valuedt from loan_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt >= '" + valueDt + "'";
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                query = "select valuedt from loan_recon where trandesc in (3,4) and acno ='" + acNo + "' and valuedt >= '" + valueDt + "'";
            }
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
     *
     * @param acNo
     * @param fromDt
     * @param toDt
     * @param intAppFreq
     * @param acNature
     * @return
     */
    private List getFromDtList(String acNo, String fromDt, String toDt, String intAppFreq, String acNature) throws ApplicationException {
        try {
            List unionAllTableList = null;
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";
            if (intAppFreq.equalsIgnoreCase("S")) {
                /* SIMPLE ==========================================================*/
                /**
                 * *SIMPLE: Trandesc in (0,1,2,5,6,7,8,9,66) **
                 */
                List tranDescSimpleList = em.createNativeQuery("SELECT CODE FROM cbs_parameterinfo WHERE NAME IN ('INTCALSIMPLE')").getResultList();
                if (tranDescSimpleList.isEmpty()) {
                    throw new ApplicationException("Please Check data  is exists for INTCALSIMPLE in CBS_PARAMETERINFO Table");
                } else {
                    Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                    tranDescSimple = tranDescSimpleVect.get(0).toString();
                }
                List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
                if (npaList.isEmpty()) {
                    if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and VALUEDT  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and VALUEDT BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and VALUEDT  BETWEEN '" + fromDt
                            + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt
                            + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and VALUEDT  BETWEEN '" + fromDt
                            + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt
                            + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and VALUEDT BETWEEN '"
                            + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '"
                            + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + " order by  fromDt").getResultList();
                }
            } else if (intAppFreq.equalsIgnoreCase("C")) {
                /* COMPOUNDING ====================================================*/
                /**
                 * *COMPOUNDING: tranDesc in (0,1,2,3,4,5,6,7,8,9,66)**
                 */
                List tranDescCompList = em.createNativeQuery("SELECT CODE FROM cbs_parameterinfo WHERE NAME IN ('INTCALCOMP')").getResultList();
                if (tranDescCompList.isEmpty()) {
                    throw new ApplicationException("Please Check data  is exists for INTCALCOMP in CBS_PARAMETERINFO Table");
                } else {
                    Vector tranDescCompVect = (Vector) tranDescCompList.get(0);
                    tranDescComp = tranDescCompVect.get(0).toString();
                }
                List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
                if (npaList.isEmpty()) {
                    if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and VALUEDT  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and VALUEDT BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and VALUEDT  BETWEEN '" + fromDt
                            + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt
                            + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();


                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and VALUEDT  BETWEEN '" + fromDt
                            + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt
                            + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and VALUEDT  BETWEEN '"
                            + fromDt + "' and '" + toDt + "'  and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '"
                            + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + " order by  fromDt").getResultList();
                }
            }
            return unionAllTableList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    /**
     * ***********========GETTING THE OUTSTANDING AMOUNT TILL
     * DATE========************ Flag = CALC : This is used for NPA_RECON
     * involvement in OutStanding REP OR "": is used for only recon entry
     * involvement
     */
    public String outStandingAsOnDate(String acNo, String tillDate) throws ApplicationException {
        String result = "";
        try {
            /**
             * ---Account is exists in Loan Secondary Table---*
             */
            String acNature, flag = "";
            String tranDescSimple = "", tranDescComp = "", npaQuery = "", npaDrWithIntQuery = "", npaDrWithoutIntQuery = "", npaDrWithIntBetweenDtQuery = "", npaCrQuery = "",
                    npaDrWithoutIntBetweenDtQuery = "", npaCrBetweenDtQuery = "";

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + fTSPosting43CBSBean.getAccountCode(acNo) + "'").getResultList();
            if (acNatureList.isEmpty()) {
                return result = "Account Nature doesn't exists.";
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
            if (npaList.isEmpty()) {
                flag = "calc";
            }
            List SecDetailsList = em.createNativeQuery("SELECT a.ACNO, a.SCHEME_CODE, a.INTEREST_TABLE_CODE, a.MORATORIUM_PD, a.ACC_PREF_DR, "
                    + "a.ACC_PREF_CR, a.RATE_CODE, a.DISB_TYPE, a.CALC_METHOD, a.CALC_ON, a.INT_APP_FREQ, a.CALC_LEVEL, a.COMPOUND_FREQ, IFNULL(a.PEGG_FREQ,0), "
                    + "a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.recover  from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                return result = "Account No Does Not Exists in Secondary Details table of Loan.";
            } else {
                Vector SecDetailsVect = (Vector) SecDetailsList.get(0);
                String intAppFreq = (String) SecDetailsVect.get(10);
                String recoveryOpt = (String) SecDetailsVect.get(16);
                List outStDrAmtList = null, outStDrIntAmtList = null, outStDrWithoutIntAmtList = null, outStCrAmtList = null;

                Vector outStDrAmtVect, outStDrIntAmtVect, outStDrWithoutIntAmtVect, outStDrWithIntAmtVect, outStCrVect;
                double drAmt = 0, drInt = 0, drWithoutInt = 0, crAmt = 0;
                double outSt = 0;
                if (intAppFreq.equalsIgnoreCase("S")) {
                    /* SIMPLE */
                    /*==Getting the DR Amount till Date==*/
                    /**
                     * *SIMPLE: Trandesc in (0,1,2,5,6,7,8,9,66) **
                     */
                    List tranDescSimpleList = em.createNativeQuery("SELECT CODE FROM cbs_parameterinfo WHERE NAME IN ('INTCALSIMPLE')").getResultList();
                    if (tranDescSimpleList.isEmpty()) {
                        throw new ApplicationException("Please Check data  is exists for INTCALSIMPLE in CBS_PARAMETERINFO Table");
                    } else {
                        Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                        tranDescSimple = tranDescSimpleVect.get(0).toString();
                    }

                    npaDrWithIntQuery = "  union All SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (3,4) group by acno ) a";
                    npaDrWithoutIntQuery = "  union All SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc not in (3,4) group by acno ) a";
                    npaCrQuery = "  union All SELECT IFNULL(SUM(IFNULL(CRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y'  group by acno ) a";

                    outStDrIntAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM "
                            + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
                            + (flag.equalsIgnoreCase("calc") ? npaDrWithIntQuery : ") a")).getResultList();
                    outStDrWithoutIntAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM "
                            + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc not in (3,4) group by  acno "
                            + (flag.equalsIgnoreCase("calc") ? npaDrWithoutIntQuery : ") a")).getResultList();
                    outStCrAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(CRAMT,0)),0) as amt FROM "
                            + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                            + (flag.equalsIgnoreCase("calc") ? npaCrQuery : ") a")).getResultList();

                    if (!outStDrIntAmtList.isEmpty()) {
                        outStDrIntAmtVect = (Vector) outStDrIntAmtList.get(0);
                        drInt = Double.parseDouble(outStDrIntAmtVect.get(0).toString());
                    }
                    if (!outStDrWithoutIntAmtList.isEmpty()) {
                        outStDrWithoutIntAmtVect = (Vector) outStDrWithoutIntAmtList.get(0);
                        drWithoutInt = Double.parseDouble(outStDrWithoutIntAmtVect.get(0).toString());
                    }
                    if (!outStCrAmtList.isEmpty()) {
                        outStCrVect = (Vector) outStCrAmtList.get(0);
                        crAmt = Double.parseDouble(outStCrVect.get(0).toString());
                    }
                    if (recoveryOpt.equalsIgnoreCase("PIC")) {
                        drAmt = crAmt - drWithoutInt;
                    } else {
                        /*List entryExistInTableQuery = em.createNativeQuery("select date_format(dt,'%Y-%m-%d'), prin_amt, int_amt, chg_amt, date_format(trantime,'%Y-%m-%d') from cbs_loan_ac_wise_prin_int "
                                + " where acno = '" + acNo + "' and sno = (select ifnull(max(sno),0) from cbs_loan_ac_wise_prin_int "
                                + " where acno = '" + acNo + "' and date_format(dt,'%Y-%m-%d')<='" + tillDate + "' )").getResultList();
                        if (entryExistInTableQuery.isEmpty()) {*/
                            if (drInt != 0) {
                                if (crAmt >= drInt) {
                                    drAmt = (crAmt - drInt) - drWithoutInt;
                                } else {
                                    drAmt = -drWithoutInt;
                                }
                            } else {
                                drAmt = crAmt - drWithoutInt;
                            }
                        /*} else {
                            Vector prinIntVect = (Vector) entryExistInTableQuery.get(0);
                            String lastIntDt = ymd.format(ymmd.parse(CbsUtil.dateAdd(ymmd.format(ymd.parse(prinIntVect.get(0).toString())), 1)));
                            double prinAmt = Double.parseDouble(prinIntVect.get(1).toString());
                            double intAmt = Double.parseDouble(prinIntVect.get(2).toString());
                            if (ymd.parse(prinIntVect.get(4).toString()).before(ymd.parse(prinIntVect.get(0).toString()))) {
                                lastIntDt = ymd.format(ymmd.parse(CbsUtil.dateAdd(ymmd.format(ymd.parse(prinIntVect.get(4).toString())), 1)));
                            }
                            npaDrWithIntBetweenDtQuery = "  union All SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + tillDate + "'  AND AUTH = 'Y' and tranDesc in (3,4) group by acno ) a";
                            npaDrWithoutIntBetweenDtQuery = "  union All SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + tillDate + "'  AND AUTH = 'Y' and tranDesc not in (3,4) group by acno ) a";
                            npaCrBetweenDtQuery = "  union All SELECT IFNULL(SUM(IFNULL(CRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + tillDate + "'  AND AUTH = 'Y'  group by acno ) a";

                            double drWithoutIntBetDt = 0, drIntBetDt = 0;
                            outStDrWithoutIntAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM "
                                    + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + tillDate + "' AND AUTH = 'Y' and tranDesc not in (3,4) group by  acno "
                                    + (flag.equalsIgnoreCase("calc") ? npaDrWithoutIntBetweenDtQuery : ") a")).getResultList();
                            if (!outStDrWithoutIntAmtList.isEmpty()) {
                                outStDrWithoutIntAmtVect = (Vector) outStDrWithoutIntAmtList.get(0);
                                drWithoutIntBetDt = Double.parseDouble(outStDrWithoutIntAmtVect.get(0).toString());
                            }

                            List outStDrWithIntAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM "
                                    + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
                                    + (flag.equalsIgnoreCase("calc") ? npaDrWithIntBetweenDtQuery : ") a")).getResultList();
                            if (!outStDrWithoutIntAmtList.isEmpty()) {
                                outStDrWithIntAmtVect = (Vector) outStDrWithIntAmtList.get(0);
                                drIntBetDt = Double.parseDouble(outStDrWithIntAmtVect.get(0).toString());
                            }

                            List crAmtBetweenDtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(CRAMT,0)),0) as amt FROM "
                                    + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                    + (flag.equalsIgnoreCase("calc") ? npaCrBetweenDtQuery : ") a")).getResultList();
                            if (!crAmtBetweenDtList.isEmpty()) {
                                Vector crAmtBetweenDtVect = (Vector) crAmtBetweenDtList.get(0);
                                double crAmtBetweenDt = Double.parseDouble(crAmtBetweenDtVect.get(0).toString());
                                if (crAmtBetweenDt >= (intAmt + drIntBetDt)) {
                                    drAmt = (crAmtBetweenDt - intAmt - drIntBetDt) - prinAmt - drWithoutIntBetDt;
                                } else {
                                    drAmt = -prinAmt - drWithoutIntBetDt;
                                }
                            } else {
                                drAmt = -prinAmt - drWithoutIntBetDt;
                            }
                        }*/
//                        if (crAmt >= drInt) {
//                            drAmt = (crAmt - drInt) - drWithoutInt;
//                        } else {
//                            drAmt = -drWithoutInt;
//                        }
                    }
                    if (drAmt > 0) {
                        drAmt = 0;
                    }
                    outSt = drAmt;
//                    System.out.println(">>>Acno:"+acNo+"; drInt:"+drInt+"; drWithoutInt:"+drWithoutInt+"; crAmt:"+crAmt+"; drAmt:"+drAmt);
                } else if (intAppFreq.equalsIgnoreCase("C")) {
                    /* COMPOUNDING */
                    /*==Getting the Outstanding Amount till Date==*/
                    /**
                     * *COMPOUNDING: tranDesc in (0,1,2,3,4,5,6,7,8,9,66)**
                     */
                    List tranDescCompList = em.createNativeQuery("SELECT CODE FROM cbs_parameterinfo WHERE NAME IN ('INTCALCOMP')").getResultList();
                    if (tranDescCompList.isEmpty()) {
                        throw new ApplicationException("Please Check data  is exists for INTCALCOMP in CBS_PARAMETERINFO Table");
                    } else {
                        Vector tranDescCompVect = (Vector) tranDescCompList.get(0);
                        tranDescComp = tranDescCompVect.get(0).toString();
                    }
//                    List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
//                    if (npaList.isEmpty()) {                    
                    if (flag.equalsIgnoreCase("calc")) {
                        if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                            npaQuery = " union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by acno ";
                        } else {
                            npaQuery = " union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (" + tranDescComp + ") group by acno ";
                        }
                    } else {
                        npaQuery = "";
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                + "ca_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                + npaQuery).getResultList();

                        if (outStDrAmtList.isEmpty()) {
                            result = "No tranction done in this Account.";
                        } else {
                            for (int j = 0; j < outStDrAmtList.size(); j++) {
                                outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                            }
                            if (drAmt > 0) {
                                drAmt = 0;
                            }
                        }
                        outSt = drAmt;
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                + "loan_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                + npaQuery).getResultList();
                        if (outStDrAmtList.isEmpty()) {
                            result = "No tranction done in this Account.";
                        } else {
                            for (int j = 0; j < outStDrAmtList.size(); j++) {
                                outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                            }
                            if (drAmt > 0) {
                                drAmt = 0;
                            }
                        }
                        outSt = drAmt;
                    } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        /*==Getting the DR Amount till Date==*/
                        /**
                         * Getting the collection amount if previous demand
                         * overflowed
                         */
                        outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                + "loan_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y'  group by  acno "
                                + npaQuery).getResultList();
                        if (outStDrAmtList.isEmpty()) {
                            result = "No tranction done in this Account.";
                        } else {
                            for (int j = 0; j < outStDrAmtList.size(); j++) {
                                outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                            }
                            if (drAmt > 0) {
                                drAmt = 0;
                            }

                        }
                        outSt = drAmt;
                    }
                }
                result = Double.toString(outSt);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    /*For Khatri UCB-Delhi, interest Calculation based on Security Deposit Slab*/
    public ArrayList getSecurityAddExpDtChangeSlab(String acNo, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException {
        try {
            /*=======Getting the Security Change Date between From Date and To Date=======================*/
            List securityDateList = em.createNativeQuery("select distinct DATE_FORMAT(entrydate,'%Y-%m-%d') as dt from loansecurity where acno = '" + acNo + "' and "
                    + " entrydate between '" + fromDt + "' and '" + toDate + "' and auth = 'Y' and entrydate is not null "
                    + " union "
                    + " select distinct DATE_FORMAT(ExpiryDate,'%Y-%m-%d') as dt from loansecurity where acno = '" + acNo + "' and "
                    + " ExpiryDate between '" + fromDt + "' and '" + toDate + "' and auth = 'Y' and ExpiryDate is not null order by dt").getResultList();
            if (!securityDateList.isEmpty()) {
                for (int i = 0; i < securityDateList.size(); i++) {
                    Vector securityDateVect = (Vector) securityDateList.get(i);
                    String securityExpDt = securityDateVect.get(0).toString();
                    if (!ymd.parse(securityExpDt).before(ymd.parse(fromDt)) && !ymd.parse(securityExpDt).equals(ymd.parse(fromDt))) {
                        if (!ymd.parse(securityExpDt).after(ymd.parse(toDate)) && !ymd.parse(securityExpDt).equals(ymd.parse(toDate))) {
                            datesFrom.add(securityExpDt);
                        }
                    }
                }
            }

            securityDateList = em.createNativeQuery("select distinct ifnull(IntTableCode,'') as IntTableCode from loansecurity where acno = '" + acNo + "' "
                    + " and entrydate between '" + fromDt + "' and '" + toDate + "' and auth = 'Y' and entrydate is not null "
                    + " union  "
                    + " select distinct ifnull(IntTableCode,'') as IntTableCode  from loansecurity where acno = '" + acNo + "' "
                    + "and ExpiryDate between '" + fromDt + "' and '" + toDate + "' and auth = 'Y' and ExpiryDate is not null "
                    + "  union  "
                    + " select distinct ifnull(IntTableCode,'') as IntTableCode  from loansecurity where acno = '" + acNo + "' "
                    + " and auth = 'Y' and entrydate <= '" + toDate + "' and ExpiryDate is null and status = 'ACTIVE' order by IntTableCode ").getResultList();
            if (!securityDateList.isEmpty()) {
                for (int i = 0; i < securityDateList.size(); i++) {
                    Vector securityDateVect = (Vector) securityDateList.get(i);
                    String intTableCode = securityDateVect.get(0).toString();
                    if (!intTableCode.equalsIgnoreCase("")) {
                        getRoiChangeSlab(intTableCode, fromDt, toDate, datesFrom);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return datesFrom;
    }

    public ArrayList getRoiChangeSlab(String intTableCode, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException {
        try {
            /*=======Getting the ROI Change Date in Previous i Position=======================*/
            List laIntCodeMastList = em.createNativeQuery("select date_format(START_DATE,'%Y-%m-%d'),INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master "
                    + "where INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

            List laIntCodeMastHistList = em.createNativeQuery("select date_format(START_DATE,'%Y-%m-%d'),INTEREST_MASTER_TABLE_CODE from "
                    + "cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '"
                    + fromDt + "' and '" + toDate + "'").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                String stDt = laIntCodeMastVect.get(0).toString();
                String intMastTblCod = laIntCodeMastVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select date_format(start_date,'%Y-%m-%d') from cbs_loan_interest_master where code = '" + intMastTblCod + "' and  START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select date_format(start_date,'%Y-%m-%d') from cbs_loan_interest_master_history where code = '" + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                if (!laIntMastList.isEmpty()) {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    String stDtIntMast = laIntMastVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }
                if (!laIntMastHistList.isEmpty()) {
                    Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                    String stDtIntMast = laIntMastHistVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }

            }
            if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                String stDt = laIntCodeMastHistVect.get(0).toString();
                String intMastTblCod = laIntCodeMastHistVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select date_format(start_date,'%Y-%m-%d') from cbs_loan_interest_master where code = '" + intMastTblCod
                        + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

                List laIntMastHistList = em.createNativeQuery("select date_format(start_date,'%Y-%m-%d') from cbs_loan_interest_master_history where code = '"
                        + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                if (!laIntMastList.isEmpty()) {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    String stDtIntMast = laIntMastVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }
                if (!laIntMastHistList.isEmpty()) {
                    Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                    String stDtIntMast = laIntMastHistVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return datesFrom;
    }

    public ArrayList getAdhocDateChangeSlab(String acNo, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException {
        try {
            /*=======Getting the ROI Change Date in Previous i Position=======================*/
            List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry  from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + toDate + "'").getResultList();
            if (!adHocDateList.isEmpty()) {
                Vector adHocDateVect = (Vector) adHocDateList.get(0);
                String adhocAppDt = adHocDateVect.get(0).toString();
                String adhocExpDt = adHocDateVect.get(1).toString();
                if (!ymd.parse(adhocAppDt).before(ymd.parse(fromDt))) {
                    if (!ymd.parse(adhocAppDt).after(ymd.parse(toDate))) {
                        datesFrom.add(adhocAppDt);
                    }
                } else if (!ymd.parse(adhocExpDt).before(ymd.parse(toDate))) {
                    if (!ymd.parse(adhocExpDt).after(ymd.parse(fromDt))) {
                        datesFrom.add(adhocExpDt);
                    }
                }
            }
            List subsidyExpDateList = em.createNativeQuery("select IFNULL(SUBSIDYEXPDT,'') from loan_appparameter where acno = '" + acNo + "' and SUBSIDYEXPDT between '" + fromDt + "' and  '" + toDate + "'").getResultList();
            if (!subsidyExpDateList.isEmpty()) {
                Vector subsidyExpDateVect = (Vector) subsidyExpDateList.get(0);
                String subsidyExpDt = subsidyExpDateVect.get(0).toString();
                if (!ymd.format(ymd.parse(subsidyExpDt)).equalsIgnoreCase("1900-01-01")) {
                    if (!ymd.parse(subsidyExpDt).before(ymd.parse(fromDt))) {
                        if (!ymd.parse(subsidyExpDt).after(ymd.parse(toDate))) {
                            datesFrom.add(ymd.format(ymmd.parse(CbsUtil.dateAdd(ymmd.format(ymd.parse(subsidyExpDt)), 1))));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return datesFrom;
    }

    /*  C   = Calender Year (Jan-Dec)
     *  CF  = Calender Year Financial Year (Apr-Mar)
     *  A   = Anniversary
     */
    public String getNextToDt(String brnCode, String acNo, String fromDt, String freq, int moratoriumPd) throws ApplicationException {
        String result = null;
        String bankCalcMethod;
        //  String frDt = fromDt;
        String toDt = "";

        try {
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/

            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                return result = "Bank Calculation Method doesn't Exists in ParameterInfo.";
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equalsIgnoreCase("")) {
                    bankCalcMethod = "CF";
                }
            }


            Calendar calendar = Calendar.getInstance();
            Date frDate = ymmd.parse(fromDt);
            calendar.setTime(frDate);
            calendar.add(Calendar.MONTH, moratoriumPd);

            if (bankCalcMethod.equalsIgnoreCase("C")) {
                /*Calender Method*/
                if (freq.equalsIgnoreCase("M")) {

                    /*Monthly*/
                    int lastDate = calendar.getActualMaximum(Calendar.DATE);
                    calendar.set(Calendar.DATE, lastDate);

                } else if (freq.equalsIgnoreCase("Q")) {

                    /*Quarterly*/
                    int month = calendar.get(Calendar.MONTH); /* 0 through 11 */
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
                    /*Halfyearly*/
                    int month = calendar.get(Calendar.MONTH); /* 0 through 11 */
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
                    /*Yearly*/
                    int month = calendar.get(Calendar.MONTH); /* 0 through 11 */
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
                result = toDt;
            } else if (bankCalcMethod.equalsIgnoreCase("CF")) {
                /*Calender Method But Financial Year*/
                if (freq.equalsIgnoreCase("M")) {

                    /*Monthly*/
                    int lastDate = calendar.getActualMaximum(Calendar.DATE);
                    calendar.set(Calendar.DATE, lastDate);

                } else if (freq.equalsIgnoreCase("Q")) {

                    /*Quarterly*/
                    int month = calendar.get(Calendar.MONTH); /* 0 through 11 */
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
                    /*Halfyearly*/
                    int month = calendar.get(Calendar.MONTH); /* 0 through 11 */
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
                    /*Yearly*/
                    int month = calendar.get(Calendar.MONTH); /* 0 through 11 */
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
                result = toDt;
            } else if (bankCalcMethod.equalsIgnoreCase("A")) {
                /*Anniversary Method*/

                if (freq.equalsIgnoreCase("M")) {
                    /*Monthly*/
                    calendar.add(Calendar.MONTH, 1);
                    toDt = ymmd.format(calendar.getTime());

                } else if (freq.equalsIgnoreCase("Q")) {
                    /*Quarterly*/
                    calendar.add(Calendar.MONTH, 3);
                    toDt = ymmd.format(calendar.getTime());

                } else if (freq.equalsIgnoreCase("H")) {
                    /*Halfyearly*/
                    calendar.add(Calendar.MONTH, 6);
                    toDt = ymmd.format(calendar.getTime());

                } else if (freq.equalsIgnoreCase("Y")) {
                    /*Yearly*/
                    calendar.add(Calendar.MONTH, 12);
                    toDt = ymmd.format(calendar.getTime());

                }
                result = toDt;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List<LoanIntCalcList> cbsLoanIntCalc(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {

        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();

        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = acNatureVect.get(0).toString();
            List getAllAccList = null;
            List checkInSecOfLoan = null;
            if (intOpt.equalsIgnoreCase("I")) {
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType
                            + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1  from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();


                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and  "
                            + "subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1   and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }
                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                        intCalc.add(it);
                    }
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
                }
            } else {
                /*
                 ALL ACCOUNT CALCULATION
                 */
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    String acNoIn = " select distinct ee.acno from "
                            + " (select distinct cc.acno from "
                            + " (select aa.acno, max(aa.dt), sum(aa.amt),'" + fromDt + "' as fromDt  from "
                            + " (select a.acno as acno, max(a.dt) as dt, cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) as amt "
                            + " from ca_recon a where substring(a.acno,3,2) in ('" + acType + "') and substring(a.acno,1,2) in ('" + brnCode + "') and a.dt <='" + fromDt + "' group by a.acno "
                            + " union all "
                            + " select a.acno as acno, max(a.dt) as dt, cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) as amt "
                            + " from npa_recon a where substring(a.acno,3,2) in ('" + acType + "') and substring(a.acno,1,2) in ('" + brnCode + "') and a.dt <='" + fromDt + "' group by a.acno ) aa group by aa.acno having sum(aa.amt) <0 "
                            + " union all "
                            + " select aa.acno, max(aa.dt), sum(aa.amt),'" + toDt + "' as fromDt  from "
                            + " (select a.acno as acno, max(a.dt) as dt, cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) as amt "
                            + " from ca_recon a where substring(a.acno,3,2) in ('" + acType + "') and substring(a.acno,1,2) in ('" + brnCode + "') "
                            + "and a.dt <='" + toDt + "' group by a.acno "
                            + " union all "
                            + " select a.acno as acno, max(a.dt) as dt, cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) as amt "
                            + " from npa_recon a where substring(a.acno,3,2) in ('" + acType + "') and substring(a.acno,1,2) in ('" + brnCode + "') "
                            + "and a.dt <='" + toDt + "' group by a.acno) aa group by aa.acno having sum(aa.amt) <0 ) cc "
                            + " union all "
                            + " select Distinct dd.acno from "
                            + " (select aa.acno, aa.dt, aa.amt from "
                            + " (select a.acno as acno, a.dt as dt, "
                            + " (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from ca_recon where acno = a.acno and dt<=a.dt ) +"
                            + " (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from npa_recon where acno = a.acno and dt<=a.dt ) as amt, 'MAIN' as tableName "
                            + " from ca_recon a where substring(a.acno,3,2) in ('" + acType + "') and substring(a.acno,1,2) in ('" + brnCode + "') "
                            + "and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " group by a.acno, a.dt "
                            + " union all "
                            + " select a.acno as acno, a.dt as dt, "
                            + " (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from ca_recon where acno = a.acno and dt<=a.dt ) + "
                            + " (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) as npaamt from npa_recon where acno = a.acno and dt<=a.dt ) as amt, 'NPA' as tableName "
                            + " from npa_recon a where substring(a.acno,3,2) in ('" + acType + "') and substring(a.acno,1,2) in ('" + brnCode + "') "
                            + "and a.dt between  '" + fromDt + "' and '" + toDt + "' "
                            + " group by a.acno, a.dt ) aa group by aa.acno, aa.dt having aa.amt < 0) dd) ee ";

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                            + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' and a.acno in (" + acNoIn + ") "
                            + " order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' and a.acno in (" + acNoIn + ") order by acno").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                            + "  (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and  "
                            + "subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and  "
                            + "subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }

                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
//                        System.out.println("Acno:" + acNo + "; END Time: " + new Date());
                        if (it.getTotalInt() < 0) {
                            intCalc.add(it);
                        }
                    }
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanIntCalcList> cbsCaAccountIntCalc(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {

        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();

        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = acNatureVect.get(0).toString();
            List getAllAccList = null;
            List checkInSecOfLoan = null;
            if (intOpt.equalsIgnoreCase("I")) {
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType
                            + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1  from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();


                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }
                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseCaAccountIntCalc(fromDt, toDt, acNo, brnCode);
                        if (it.getTotalInt() > 0) {
                            intCalc.add(it);
                        }
                    }
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
                }
            } else {
                /*
                 ALL ACCOUNT CALCULATION
                 */
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                            + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'order by acno").getResultList();

                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }

                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseCaAccountIntCalc(fromDt, toDt, acNo, brnCode);
                        if (it.getTotalInt() > 0) {
                            intCalc.add(it);
                        }
                    }
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ***********========GETTING THE OUTSTANDING AMOUNT TILL
     * DATE========************ Flag = CALC : This is used for NPA_RECON
     * involvement in OutStanding REP OR "": is used for only recon entry
     * involvement
     */
    public String outStandingCaAccountAsOnDate(String acNo, String tillDate) throws ApplicationException {
        String result = "";
        try {
            /**
             * ---Account is exists in Loan Secondary Table---*
             */
            String acNature, flag = "";
            String tranDescSimple = "", tranDescComp = "", npaQuery = "", npaDrWithIntQuery = "", npaDrWithoutIntQuery = "", npaCrQuery = "";

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + fTSPosting43CBSBean.getAccountCode(acNo) + "'").getResultList();
            if (acNatureList.isEmpty()) {
                return result = "Account Nature doesn't exists.";
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
            if (npaList.isEmpty()) {
                flag = "calc";
            }
            List SecDetailsList = em.createNativeQuery("SELECT a.ACNO, a.SCHEME_CODE, a.INTEREST_TABLE_CODE, a.MORATORIUM_PD, a.ACC_PREF_DR, "
                    + "a.ACC_PREF_CR, a.RATE_CODE, a.DISB_TYPE, a.CALC_METHOD, a.CALC_ON, a.INT_APP_FREQ, a.CALC_LEVEL, a.COMPOUND_FREQ, IFNULL(a.PEGG_FREQ,0), "
                    + "a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.recover  from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                return result = "Account No Does Not Exists in Secondary Details table of Loan.";
            } else {
                Vector SecDetailsVect = (Vector) SecDetailsList.get(0);
                String intAppFreq = (String) SecDetailsVect.get(10);
                String recoveryOpt = (String) SecDetailsVect.get(16);
                List outStDrAmtList = null, outStDrIntAmtList = null, outStDrWithoutIntAmtList = null, outStCrAmtList = null;

                Vector outStDrAmtVect, outStDrIntAmtVect, outStDrWithoutIntAmtVect, outStCrVect;
                double drAmt = 0, drInt = 0, drWithoutInt = 0, crAmt = 0;
                double outSt = 0;
                if (intAppFreq.equalsIgnoreCase("S")) {
                    /* SIMPLE */
                    /*==Getting the DR Amount till Date==*/
                    /**
                     * *SIMPLE: Trandesc in (0,1,2,5,6,7,8,9,66) **
                     */
                    List tranDescSimpleList = em.createNativeQuery("SELECT CODE FROM cbs_parameterinfo WHERE NAME IN ('INTCALSIMPLE')").getResultList();
                    if (tranDescSimpleList.isEmpty()) {
                        throw new ApplicationException("Please Check data  is exists for INTCALSIMPLE in CBS_PARAMETERINFO Table");
                    } else {
                        Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                        tranDescSimple = tranDescSimpleVect.get(0).toString();
                    }

                    if (flag.equalsIgnoreCase("calc")) {
                        if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                            npaQuery = " union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc not in (3,4)group by acno ";
                        } else {
                            npaQuery = " union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc not in (3,4) group by acno ";
                        }
                    } else {
                        npaQuery = "";
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc not in (3,4) group by  acno "
                                + npaQuery).getResultList();

                        if (outStDrAmtList.isEmpty()) {
                            result = "No tranction done in this Account.";
                        } else {
                            for (int j = 0; j < outStDrAmtList.size(); j++) {
                                outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                            }
                            if (drAmt < 0) {
                                drAmt = 0;
                            }
                        }
                        outSt = drAmt;
                    }
//                    System.out.println(">>>Acno:"+acNo+"; drInt:"+drInt+"; drWithoutInt:"+drWithoutInt+"; crAmt:"+crAmt+"; drAmt:"+drAmt);
                } else if (intAppFreq.equalsIgnoreCase("C")) {
                    /* COMPOUNDING */
                    /*==Getting the Outstanding Amount till Date==*/
                    /**
                     * *COMPOUNDING: tranDesc in (0,1,2,3,4,5,6,7,8,9,66)**
                     */
                    List tranDescCompList = em.createNativeQuery("SELECT CODE FROM cbs_parameterinfo WHERE NAME IN ('INTCALCOMP')").getResultList();
                    if (tranDescCompList.isEmpty()) {
                        throw new ApplicationException("Please Check data  is exists for INTCALCOMP in CBS_PARAMETERINFO Table");
                    } else {
                        Vector tranDescCompVect = (Vector) tranDescCompList.get(0);
                        tranDescComp = tranDescCompVect.get(0).toString();
                    }
//                    List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
//                    if (npaList.isEmpty()) {                    
                    if (flag.equalsIgnoreCase("calc")) {
                        if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                            npaQuery = " union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by acno ";
                        } else {
                            npaQuery = " union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (" + tranDescComp + ") group by acno ";
                        }
                    } else {
                        npaQuery = "";
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                + CbsUtil.getReconTableName(acNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                + npaQuery).getResultList();

                        if (outStDrAmtList.isEmpty()) {
                            result = "No tranction done in this Account.";
                        } else {
                            for (int j = 0; j < outStDrAmtList.size(); j++) {
                                outStDrAmtVect = (Vector) outStDrAmtList.get(j);
                                drAmt = Double.parseDouble(outStDrAmtVect.get(0).toString()) + drAmt;
                            }
                            if (drAmt < 0) {
                                drAmt = 0;
                            }
                        }
                        outSt = drAmt;
//                        System.out.println("acNo:"+acNo+"; OutStanding:"+outSt+"; Dt:"+tillDate);
                    }
                }
                result = Double.toString(outSt);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List<LoanIntCalcList> cbsTheftIntCalcNew(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException {

        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        try {

            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            // Sb Roi
            List schemeList = em.createNativeQuery("select distinct INTEREST_TABLE_CODE from cbs_loan_acc_mast_sec where subString(acno,3,2)  = '" + acType + "'").getResultList();
            Vector vtr = (Vector) schemeList.get(0);
            String intTableCode = vtr.get(0).toString();
            List<SavingIntRateChangePojo> dataList = sbIntCal.getSavingRoiChangeDetail(intTableCode, fromDt, toDt);
            //End
            //Fd Roi
            List<FdRoiDetail> FddataList = getFdRoiData(fromDt);
            //End
            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = acNatureVect.get(0).toString();
            List getAllAccList = null;
            List checkInSecOfLoan = null;
            if (intOpt.equalsIgnoreCase("I")) {
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from recon where  substring(acno,3,2) = '" + acType
                            + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                }

                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }
                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseLoanIntCalcTheftNew(dataList, FddataList, fromDt, toDt, acNo, brnCode, acNature);
                        intCalc.add(it);
//                        System.out.print(intCalc.size());
                    }

//                    System.out.print(intCalc.size());
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
                }
            } else {
                /*
                 ALL ACCOUNT CALCULATION
                 */
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                            + " (select distinct acno as acno1 from recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno) b "
                            + " where a.acno = b.acno1  and "
                            + " subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'order by acno").getResultList();

                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }

                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseLoanIntCalcTheftNew(dataList, FddataList, fromDt, toDt, acNo, brnCode, acNature);
                        intCalc.add(it);
                    }
//                    System.out.print(intCalc.size());
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//    public List<LoanMultiList> cbsTheftIntCalc(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {
//
//        List<LoanMultiList> intCalc = new ArrayList<LoanMultiList>();
//        LoanMultiList it = new LoanMultiList();
//
//        try {
//            fromDt = ymmd.format(dmy.parse(fromDt));
//            toDt = ymmd.format(dmy.parse(toDt));
//
//            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
//            Vector acNatureVect = (Vector) acNatureList.get(0);
//            String acNature = acNatureVect.get(0).toString();
//            List getAllAccList = null;
//            List checkInSecOfLoan = null;
//            if (intOpt.equalsIgnoreCase("I")) {
//                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//
//                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
//                            + " (select distinct acno as acno1 from recon where  substring(acno,3,2) = '" + acType
//                            + "'  and auth = 'Y' group by acno ) b "
//                            + " where a.acno = b.acno1 and "
//                            + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();
//
//
//                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
//                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
//                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();
//
//                }
//
//                if (getAllAccList.size() <= 0) {
//                    throw new ApplicationException("Account number does not exist.");
//                }
//                if (getAllAccList.size() == checkInSecOfLoan.size()) {
//                    for (int i = 0; i < getAllAccList.size(); i++) {
//                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
//                        acNo = (String) getAllAccVect.get(0);
//                        intCalc = loanIntFacade.accWiseTheftIntProductCalc(intOpt, fromDt, toDt, acNo, brnCode);
//                        // it.setLoanProductIntDetails(intCalc);
//                        System.out.print(intCalc.size());
//                    }
//                    // intCalc.add(it);
//                    System.out.print(intCalc.size());
//                    return intCalc;
//                } else {
//                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
//                }
//            } else {
//                /*
//                 ALL ACCOUNT CALCULATION
//                 */
//                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//
//                    getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
//                            + " (select distinct acno as acno1 from recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno) b "
//                            + " where a.acno = b.acno1  and "
//                            + " subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' and a.custid1 = '28' order by a.acno").getResultList();
//
//                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
//                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'order by acno").getResultList();
//
//                }
//                if (getAllAccList.size() <= 0) {
//                    throw new ApplicationException("Account number does not exist.");
//                }
//
//                if (getAllAccList.size() == checkInSecOfLoan.size()) {
//                    for (int i = 0; i < getAllAccList.size(); i++) {
//                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
//                        acNo = (String) getAllAccVect.get(0);
//                        intCalc = loanIntFacade.accWiseTheftIntProductCalc(intOpt, fromDt, toDt, acNo, brnCode);
//                        it.setLoanProductIntDetails(intCalc);
//                        intCalc.add(it);
//                    }
//                    System.out.print(intCalc.size());
//                    return intCalc;
//                } else {
//                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
//                }
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//    }
    public String loanInterestPosting(List<IntCalTable> intCalc, String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {
//        LoanIntCalcList it = new LoanIntCalcList();
        UserTransaction ut = context.getUserTransaction();
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
//            List tempBdList = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
//            Vector tempBdVect = (Vector) tempBdList.get(0);
//            String tempBd = tempBdVect.get(0).toString();
            String tempBd = ymmd.format(date);

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE ,Int_Ac_Open_Enable_In_Staff FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = acNatureVect.get(0).toString();
            String Int_Ac_Open_Enable_In_Staff = acNatureVect.get(1).toString();
//            String Int_Ac_open_Enable_In_Staff = acNatureVect.get(2).toString()
           
            /*
             * Flag, to pass the Authrize entry in Individual Account
             */
            String indAcAuthEntryFlag = "N";
            List indAcAuthEntryFlagList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PASS_IND_AC_AUTH_ENTRY'").getResultList();
            if (!indAcAuthEntryFlagList.isEmpty()) {
                Vector indAcAuthEntryFlagVect = (Vector) indAcAuthEntryFlagList.get(0);
                indAcAuthEntryFlag = indAcAuthEntryFlagVect.get(0).toString();
            }

            ut.begin();
            String uriGl = "";
            String oirHead = "";
            double totalNpaIntAmt = 0, prinAmt = 0;
            int acStatus = 1;
            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for " + acType);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                glAcNo = brnCode + glHeadVect.get(0).toString() + "01";
                uriGl = glHeadVect.get(1).toString();
                oirHead = glHeadVect.get(2).toString();
            }
            /**
             * Individual Account Wise Posting
             */
            if (intOpt.equalsIgnoreCase("I") && indAcAuthEntryFlag.equalsIgnoreCase("N")) {

                /*Getting TempTrSNo*/
                float dTrSNo = fTSPosting43CBSBean.getTrsNo();

                /*Getting TmpRecNo*/
                float dRecNo = fTSPosting43CBSBean.getRecNo();

                List int_Trf_AcnoList = em.createNativeQuery("Select INT_TRF_ACNO from cbs_loan_acc_mast_sec where acno='" + acNo + "'").getResultList();
                Vector intVec = (Vector) int_Trf_AcnoList.get(0);
                String INT_TRF_ACNO = intVec.get(0).toString();
                /*Getting Ac Status*/
                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'").getResultList();
                Vector acStatusVect = (Vector) acStatusList.get(0);
                acStatus = Integer.parseInt(acStatusVect.get(0).toString());


                String schemeCode = null;
                List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from cbs_loan_acc_mast_sec A, loan_appparameter B "
                        + "where A.ACNO = B.ACNO AND A.ACNO ='" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'").getResultList();
                if (SecDetailsList.isEmpty()) {
                    throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
                } else {
                    for (int i = 0; i < SecDetailsList.size(); i++) {
                        Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                        schemeCode = (String) SecDetailsVect.get(1);
                    }
                }

                List flowDetailList = em.createNativeQuery("select INT_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
                String intDemFlowId = null;
                if (flowDetailList.isEmpty()) {
                    throw new ApplicationException("false;Flow Id Does Not Exists in Scheme Master.");
                } else {
                    for (int i = 0; i < flowDetailList.size(); i++) {
                        Vector flowDetailVect = (Vector) flowDetailList.get(i);
                        intDemFlowId = flowDetailVect.get(0).toString();
                    }
                }
                /*
                 Getting the List as FromDT, ToDT, ActualProduct, Roi, IntAmt, IntTableCode
                 */
                LoanIntCalcList it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);

                double intAmt = it.getTotalInt() * -1;
                double roi = it.getRoi();
                fromDt = it.getFirstDt();
                toDt = it.getLastDt();
                prinAmt = it.getPriAmt();
                String strDetails = "INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%";
                if (intAmt != 0) {
                    if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {

                        Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNo,Ty,Dt,valuedt,DrAmt,CrAmt,TranType,Details,iy,"
                                + "EnterBy,auth,Payby,Authby,Trantime,TranDesc,IntAmt,Status,recno, trsno,org_brnid, dest_brnid,balance)"
                                + " VALUES ('" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "',1,'" + tempBd + "','" + toDt + "'," + intAmt + ",0,8,'" + strDetails + "',1,'" + authBy
                                + "','Y',3,'SYSTEM',NOW(),3," + intAmt + ",'" + acStatus + "'," + dRecNo + "," + dTrSNo + ",'" + brnCode + "','"
                                + brnCode + "',0.0)");
                        Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                        if (insertNpaRecon == 0) {
                            throw new ApplicationException("false;Value not inserted in  Npa_recon");
                        } else {
                            float recNo = fTSPosting43CBSBean.getRecNo();
                            if (!oirHead.equals("") && !uriGl.equals("")) {
                                uriGl = brnCode + uriGl + "01";
                                oirHead = brnCode + oirHead + "01";
                                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                        + intAmt + ",0,8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "','system','Y','" + authBy
                                        + "'," + dTrSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                if (insertReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                }
                                recNo = fTSPosting43CBSBean.getRecNo();
                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                        + intAmt + ",8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "','system','Y','" + authBy
                                        + "'," + dTrSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                insertReconBalan = insertReconBalanQuery.executeUpdate();
                                if (insertReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                }

                                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + intAmt + " WHERE ACNO= '" + oirHead + "'");
                                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                if (updateReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't updated in reconbalan");
                                }

                                Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + intAmt + " WHERE ACNO= '" + uriGl + "'");
                                Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                                if (updateReconBalanUri == 0) {
                                    throw new ApplicationException("Value doesn't updated in reconbalan");
                                }
                                List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                Vector sNoVect = (Vector) sNoList.get(0);
                                int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,"
                                        + "POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "','Y',NOW(),'" + fromDt
                                        + "','" + toDt + "','" + brnCode + "','I')");
                                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                if (updateIntPostAcWise == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                }
                                String nextCalcDt = dateAdd(toDt, 1);
                                Query updateCaReconQuery = em.createNativeQuery(" UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_acc_mast_sec table");
                                }
                                ut.commit();
                                return "Interest posted successfully.";
                            } else {
                                ut.commit();
                                return "Interest posted successfully.";
                            }
                        }
                    } else {
                        List acExistList = em.createNativeQuery("SELECT custname,jtname1,jtname2,jtname3,JTNAME4,IFNULL(odlimit,0) 'ODLIMIT' ,"
                                + "IFNULL(adhoclimit,0) 'ADHOCLIMIT',  opermode,accstatus,optstatus,DATE_FORMAT(openingdt,'%Y-%m-%d') 'OPENINGDT', "
                                + "rdinstal,rdmatdate,intdeposit,Instruction FROM accountmaster WHERE acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'").getResultList();
                        if (acExistList.size() > 0) {
                            Vector acExistVect = (Vector) acExistList.get(0);
                            String custName = acExistVect.get(0).toString();
                            Query insertReconTrfDQuery = em.createNativeQuery("INSERT INTO recon_trf_d (acno,custName,Dramt,enterBy,TranType,Ty,dt,"
                                    + "valuedt,details,trsno,recno,AUTH,Trandesc,PAYBY, org_brnid, dest_brnid,adviceNo,adviceBrnCode) "
                                    + "values ('" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "','" + custName + "'," + intAmt + ",'" + authBy + "',8,1,'" + tempBd + "','" + toDt
                                    + "','" + strDetails + "'," + dTrSNo + "," + dRecNo + ",'N',3,3,'" + brnCode + "','" + brnCode + "','','')");

                            Integer insertReconTrfD = insertReconTrfDQuery.executeUpdate();
                            if (insertReconTrfD == 0) {
                                throw new ApplicationException("false;Value not inserted in Recon_Trf_D");
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                Query updateCaReconQuery = em.createNativeQuery("UPDATE ca_reconbalan SET Balance = Balance - " + intAmt + " "
                                        + " WHERE Acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Value not updated in  ca_reconbalan");
                                }
                                List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                Vector sNoVect = (Vector) sNoList.get(0);
                                int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,"
                                        + "POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "','Y',NOW(),'" + fromDt
                                        + "','" + toDt + "','" + brnCode + "','I')");
                                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                if (updateIntPostAcWise == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                }
                            } else {
                                Query updateCaReconQuery = em.createNativeQuery(" UPDATE reconbalan SET Balance = Balance - " + intAmt + " "
                                        + "WHERE Acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Value not updated in  reconbalan");
                                }
                                List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                Vector sNoVect = (Vector) sNoList.get(0);
                                int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,"
                                        + "POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "','Y',NOW(),'" + fromDt
                                        + "','" + toDt + "','" + brnCode + "','I')");
                                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                if (updateIntPostAcWise == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                } else {
                                    int dmdSchNo = 0;
                                    Integer dmdSrl = null;
                                    List loanDList = em.createNativeQuery("select dmd_srl_num from cbs_loan_dmd_table  where DMD_DATE = '" + toDt + "'").getResultList();
                                    if (loanDList.size() > 0) {
                                        Vector ele = (Vector) loanDList.get(0);
                                        dmdSrl = Integer.parseInt(ele.get(0).toString());
                                    } else {
                                        List loanDMList = em.createNativeQuery("select IFNULL(max(dmd_srl_num),0) from cbs_loan_dmd_table ").getResultList();
                                        if (loanDMList.size() > 0) {
                                            Vector ele = (Vector) loanDMList.get(0);
                                            dmdSrl = Integer.parseInt(ele.get(0).toString()) + 1;
                                        }
                                    }
                                    Integer ShdlNo = null;
                                    List snoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0) from cbs_loan_dmd_table").getResultList();
                                    if (snoList.size() > 0) {
                                        Vector secVec = (Vector) snoList.get(0);
                                        ShdlNo = Integer.parseInt(secVec.get(0).toString());
                                        if (ShdlNo == 0) {
                                            ShdlNo = 1;
                                        } else {
                                            ShdlNo = ShdlNo + 1;
                                        }
                                    }
                                    Integer tsCnt = 0;
                                    String dmdFlowId = null;
                                    List getDmdList = em.createNativeQuery("select ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_SRL_NUM,DMD_EFF_DATE,DMD_OVDU_DATE,"
                                            + "DMD_AMT,LAST_ADJ_DATE,IFNULL(TOT_ADJ_AMT,0) as TOT_ADJ_AMT, IFNULL(EI_AMT,0) as EI_AMT,TS_CNT from "
                                            + "cbs_loan_dmd_table  where acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "' and DMD_EFF_DATE between '" + fromDt + "' and '" + toDt
                                            + "' and DEL_FLG = 'N' and LATEFEE_STATUS_FLG IN ('N','L','U')  order by acno, DMD_EFF_DATE, SHDL_NUM, "
                                            + "DMD_SRL_NUM").getResultList();
                                    if (getDmdList.size() > 0) {
                                        for (int i = 0; i < getDmdList.size(); i++) {
                                            Vector getDmdVect = (Vector) getDmdList.get(i);
                                            dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                            dmdFlowId = getDmdVect.get(2).toString();
                                            tsCnt = Integer.parseInt(getDmdVect.get(10).toString());
                                        }
                                        Integer loanDmdList = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET DMD_AMT = " + intAmt
                                                + ", DMD_EFF_DATE = '" + toDt + "', DMD_OVDU_DATE = '" + dateAdd(toDt, 1) + "',"
                                                + " LCHG_USER_ID = '" + authBy + "', LCHG_TIME = NOW(), TS_CNT = " + tsCnt + " where ACNO = '"
                                                + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "'").executeUpdate();
                                        if (loanDmdList < 0) {
                                            throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");
                                        }
                                    } else {
                                        Integer loanDmdList = em.createNativeQuery("Insert into cbs_loan_dmd_table(ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_DATE,"
                                                + "DEL_FLG,LD_FREQ_TYPE,DMD_EFF_DATE,DMD_OVDU_DATE,DMD_AMT,RCRE_USER_ID,"
                                                + "RCRE_TIME,EI_AMT,TS_CNT,LATEFEE_STATUS_FLG,dmd_srl_num) Values"
                                                + "('" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'," + ShdlNo + ",'" + intDemFlowId + "','" + toDt + "','N','M','" + toDt + "','" + dateAdd(toDt, 1) + "'," + intAmt + ","
                                                + "'" + authBy + "',NOW()," + intAmt + ",0,'N'," + dmdSrl + ")").executeUpdate();
                                        if (loanDmdList < 0) {
                                            throw new ApplicationException("Data is not inserted into CBS_LOAN_DMD_TABLE");
                                        }
                                    }
                                }
                            }
                            String details = "VCH$ As Int Amount From " + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo));
                            String ACNO = glAcNo;
                            Integer tranType = 8;
                            Integer ty = 0;
                            Integer tranDesc = 3;
                            Float trSNo1 = dTrSNo;
                            Float recNo1 = 0f;
                            Integer tranId = 0;
                            String termId = "";
                            String auth = "";
                            String authBy1 = "";
                            String subtokenNo = "A";
                            Integer payBy = 3;
                            String instNo = "";
                            String instDt = "";
                            String tdAcNo = "";
                            Float voucherNo = null;
                            String intFlag = "";
                            String closeFlag = "";
                            String screenFlag = "";
                            String txnStatus = "";
                            Float tokenNoDr = 0f;
                            String cxSxFlag = "N";

                            String msg = fTSPosting43CBSBean.ftsPosting43CBS(ACNO, tranType, ty, intAmt, tempBd, tempBd, authBy, brnCode, brnCode,
                                    tranDesc, details, trSNo1, recNo1, tranId, termId, auth, authBy1, subtokenNo, payBy, instNo, instDt, tdAcNo,
                                    voucherNo, intFlag, closeFlag, screenFlag, txnStatus, tokenNoDr, cxSxFlag, "", "", "S");

                            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                String nextCalcDt = dateAdd(toDt, 1);
                                Query updateCaReconQuery = em.createNativeQuery(" UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_acc_mast_sec table");
                                } else {
                                    ut.commit();
                                    //Sending Sms
//                                    try {
//                                        smsFacade.sendTransactionalSms(SmsType.INTEREST_WITHDRAWAL, acNo, 8, 1,
//                                                intAmt, dmy.format(ymmd.parse(tempBd)));
//                                    } catch (Exception ex) {
//                                        System.out.println("Error SMS Sending-->A/c is::" + acNo + " And "
//                                                + "Amount is::" + intAmt);
//                                    }
                                    //End here
                                    return "Interest Posted Successfully!! Generated Batch No is " + dTrSNo;
                                }
                            } else {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            throw new ApplicationException("Account No does Not Exist");
                        }
                    }
                } else {
                    throw new ApplicationException("Interest not Posted successfully, because Interest is calculated Zero.");
                }
            } else {
                /**
                 * All Account Wise Posting
                 */
                String indAcQuery = "";
                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();    //Sms List

                if (indAcAuthEntryFlag.equalsIgnoreCase("N")) {
                    List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'Loan Interest' and "
                            + "actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or "
                            + "(todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                    if (parameterInfo.size() > 0) {
                        throw new ApplicationException("Interest Already Posted");
                    }
                } else {
                    indAcQuery = " and a.acno = '" + acNo + "' ";
                }
                float trSNo = fTSPosting43CBSBean.getTrsNo();
                List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                Vector sNoVect = (Vector) sNoList.get(0);
                int sNo = Integer.parseInt(sNoVect.get(0).toString());

                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                            + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and "
                            + " subString(a.acno,3,2)  = '" + acType + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                    List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c "
                            + "where a.acno = b.ACNO AND a.acno = c.ACNO  and subString(a.acno,3,2)  = '" + acType + "' " + indAcQuery + " and a.accStatus <> 9 "
                            + "and a.CurBrCode = '" + brnCode + "'").getResultList();

                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        double glSumAmt = 0f;
                        for (int i = 0; i < intCalc.size(); i++) {
//                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = intCalc.get(i).getAcNo();
//                            it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                            double intAmt = intCalc.get(i).getTotalInt().doubleValue();
                            double closingBal = intCalc.get(i).getClosingBal().doubleValue();
                            double roi = intCalc.get(i).getRoi().doubleValue();
//                            prinAmt = it.getPriAmt();
                            //Sms Object Creation
                            if (intAmt > 0) {
                                SmsToBatchTo to = new SmsToBatchTo();

                                to.setAcNo(acNo);
                                to.setCrAmt(0d);
                                to.setDrAmt(intAmt);
                                to.setTranType(8);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(ymmd.parse(tempBd)));
                                to.setTemplate(SmsType.INTEREST_WITHDRAWAL);

                                smsList.add(to);
                            }
                            //End Here
                            if (intAmt != 0) {
//                                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "' and accstatus <> 9").getResultList();
//                                Vector acStatusVect = (Vector) acStatusList.get(0);
//                                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                acStatus = Integer.parseInt(intCalc.get(i).getAccStatus());
                                if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {
                                    float recNo = fTSPosting43CBSBean.getRecNo();

                                    Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                            + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                            + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + intAmt + ",0,8,'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                            + trSNo + "," + recNo + ",0,3," + intAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                            + brnCode + "')");

                                    Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                                    if (insertNpaRecon == 0) {
                                        throw new ApplicationException("Value not inserted in  npa_recon");
                                    }
                                    /**
                                     * * Added For NPA Accounts**
                                     */
                                    totalNpaIntAmt = totalNpaIntAmt + intAmt;
                                    String nextCalcDt = dateAdd(toDt, 1);
                                    Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                    if (updateCaRecon == 0) {
                                        throw new ApplicationException("Interest not Posted successfully");
                                    }

                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    sNo = sNo + 1;
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    /**
                                     * * Addition End Here **
                                     */
                                } else {
                                    float recNo = fTSPosting43CBSBean.getRecNo();

                                    Query insertCaReconQuery = em.createNativeQuery("INSERT ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
                                            + " values('" + acNo + "',1, DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + intAmt + ",0,8,3,3,'INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
                                            + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                    Integer insertCaRecon = insertCaReconQuery.executeUpdate();
                                    if (insertCaRecon == 0) {
                                        throw new ApplicationException("Value not inserted in Ca_Recon");
                                    }
                                    List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  ca_reconbalan WHERE ACNO='" + acNo + "'").getResultList();

                                    if (chkBalan.size() > 0) {
                                        Query updateCaReconQuery = em.createNativeQuery("UPDATE ca_reconbalan SET BALANCE = BALANCE - " + intAmt
                                                + " WHERE Acno = '" + acNo + "'");

                                        Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                        if (updateCaRecon == 0) {
                                            throw new ApplicationException("Value not updated in  ca_reconbalan");
                                        }
                                    }
                                    String nextCalcDt = dateAdd(toDt, 1);
                                    Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                    if (updateCaRecon == 0) {
                                        throw new ApplicationException("Interest not Posted successfully");
                                    }
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    sNo = sNo + 1;
                                    glSumAmt = glSumAmt + intAmt;
                                }
//                                List SecDetailsList = em.createNativeQuery("SELECT a.ACNO, a.SCHEME_CODE, a.INTEREST_TABLE_CODE, a.MORATORIUM_PD, a.ACC_PREF_DR, "
//                                        + "a.ACC_PREF_CR, a.RATE_CODE, a.DISB_TYPE, a.CALC_METHOD, a.CALC_ON, a.INT_APP_FREQ, a.CALC_LEVEL, a.COMPOUND_FREQ, IFNULL(a.PEGG_FREQ,0), "
//                                        + "a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.recover  from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + acNo + "'").getResultList();
//                                if (!SecDetailsList.isEmpty()) {
//                                    Vector SecDetailsVect = (Vector) SecDetailsList.get(0);
//                                    String intAppFreq = (String) SecDetailsVect.get(10);
//                                    String recoveryOpt = (String) SecDetailsVect.get(16);
//                                    if (intAppFreq.equalsIgnoreCase("S") && recoveryOpt.equalsIgnoreCase("CIP")) {
//                                        String msg = fTSPosting43CBSBean.insertionAsPerPrincipalInt(acNo, toDt, intAmt);
//                                        if (!msg.equalsIgnoreCase("TRUE")) {
//                                            throw new ApplicationException(msg);
//                                        }
//                                    }
//                                }
                            }
                            if (i == intCalc.size() - 1) {
                                if (glSumAmt > 0) {
                                    float recNo = fTSPosting43CBSBean.getRecNo();
                                    Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNo + "'");
                                    Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                    if (updateReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                    }
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glAcNo + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + glSumAmt + ",0,8,3,3,'VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                            + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                                    }
                                }
                                if (!oirHead.equals("") && !uriGl.equals("")) {
                                    uriGl = brnCode + uriGl + "01";
                                    oirHead = brnCode + oirHead + "01";
                                    if (totalNpaIntAmt > 0) {
                                        float recNo = fTSPosting43CBSBean.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + totalNpaIntAmt + ",0,8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                        }
                                        recNo = fTSPosting43CBSBean.getRecNo();
                                        insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                + totalNpaIntAmt + ",8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in gl_recon");
                                        }

                                        Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + totalNpaIntAmt + " WHERE ACNO= '" + oirHead + "'");
                                        Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                        if (updateReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }

                                        Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + totalNpaIntAmt + " WHERE ACNO= '" + uriGl + "'");
                                        Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                                        if (updateReconBalanUri == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                            + " (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and  "
                            + "subString(a.acno,3,2)  = '" + acType + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                    List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c"
                            + " where a.acno = b.ACNO AND a.acno = c.ACNO"
                            + " and subString(a.acno,3,2)  = '" + acType + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        double glSumAmt = 0f;
                        for (int i = 0; i < intCalc.size(); i++) {
//                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = intCalc.get(i).getAcNo();
                            List int_Trf_AcnoList = em.createNativeQuery("Select INT_TRF_ACNO from cbs_loan_acc_mast_sec where acno='" + acNo + "'").getResultList();
                            Vector intVec = (Vector) int_Trf_AcnoList.get(0);
                            String INT_TRF_ACNO = intVec.get(0).toString();
//                            it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                            double intAmt = intCalc.get(i).getTotalInt().doubleValue();
                            double closingBal = intCalc.get(i).getClosingBal().doubleValue();
                            double roi = intCalc.get(i).getRoi().doubleValue();
//                            prinAmt = it.getPriAmt();
                            //Sms Object Creation
                            if (intAmt > 0) {
                                SmsToBatchTo to = new SmsToBatchTo();

                                to.setAcNo(acNo);
                                to.setCrAmt(0d);
                                to.setDrAmt(intAmt);
                                to.setTranType(8);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(ymmd.parse(tempBd)));
                                to.setTemplate(SmsType.INTEREST_WITHDRAWAL);

                                smsList.add(to);
                            }
                            //End Here
                            if (intAmt != 0) {
//                                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo
//                                        + "' and accstatus <> 9").getResultList();
//                                Vector acStatusVect = (Vector) acStatusList.get(0);
//                                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                acStatus = Integer.parseInt(intCalc.get(i).getAccStatus());
                                if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {

                                    float recNo = fTSPosting43CBSBean.getRecNo();
                                    Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,"
                                            + "DETAILS,IY,ENTERBY,AUTHBY,AUTH,PAYBY,trsno,RECNO,balance,trandesc,intamt,status,trantime,org_brnid, dest_brnid)"
                                            + " values('" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + intAmt + ",0,8,'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                            + trSNo + "," + recNo + ",0,3," + intAmt + "," + acStatus + ",NOW(),'" + brnCode + "','" + brnCode + "')");

                                    Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                                    totalNpaIntAmt = totalNpaIntAmt + intAmt;
                                    if (insertNpaRecon == 0) {
                                        throw new ApplicationException("Value not inserted in  npa_recon");
                                    }
                                    /**
                                     * * Added For NPA Accounts**
                                     */
                                    String nextCalcDt = dateAdd(toDt, 1);
                                    Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '"
                                            + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                    if (updateCaRecon == 0) {
                                        throw new ApplicationException("Interest not Posted successfully");
                                    }


                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise "
                                            + "(SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo
                                            + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    sNo = sNo + 1;
                                    /**
                                     * * Addition End Here **
                                     */
                                } else {
                                    float recNo = fTSPosting43CBSBean.getRecNo();

                                    Query insertCaReconQuery = em.createNativeQuery("INSERT loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " values ('" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + intAmt + ",0,8,3,3,'INT.UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
                                            + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                    Integer insertCaRecon = insertCaReconQuery.executeUpdate();
                                    if (insertCaRecon == 0) {
                                        throw new ApplicationException("Value not inserted in Loan_Recon");
                                    }
                                    List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + acNo
                                            + "'").getResultList();
                                    if (chkBalan.size() > 0) {
                                        Query updateReconQuery = em.createNativeQuery(" UPDATE reconbalan SET BALANCE = BALANCE - " + intAmt
                                                + " WHERE Acno = '" + acNo + "'");
                                        Integer updateRecon = updateReconQuery.executeUpdate();
                                        if (updateRecon == 0) {
                                            throw new ApplicationException("Value not updated in  ca_reconbalan");
                                        }
                                    }
                                    List existInTable = em.createNativeQuery("SELECT * FROM int_tldetails where acno = '" + acNo + "' ").getResultList();
                                    if (existInTable.size() > 0) {
                                        if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {

                                            Query updateTlDetailsQuery = em.createNativeQuery("UPDATE int_tldetails set cumuactualamt=0, "
                                                    + "closingactualamt= " + closingBal + ", todate= '" + toDt
                                                    + "' WHERE Acno = '" + acNo + "'");
                                            Integer updateTlDetails = updateTlDetailsQuery.executeUpdate();
                                            if (updateTlDetails == 0) {
                                                throw new ApplicationException("Value not updated in  ca_reconbalan");
                                            }
                                        }
                                        if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                            Query updateDlDetailsQuery = em.createNativeQuery(" UPDATE int_tldetails set closingbalance= "
                                                    + closingBal + "-" + intAmt + ", closingactualamt= " + closingBal + ",CumuActualAmt =0,"
                                                    + "CumuPenalAmt = 0,CumuAdhocAmt = 0, todate= '" + toDt
                                                    + "' WHERE Acno = '" + acNo + "'");

                                            Integer updateDlDetails = updateDlDetailsQuery.executeUpdate();
                                            if (updateDlDetails == 0) {
                                                throw new ApplicationException("Value not updated in int_tldetails");
                                            }
                                        }
                                    } else {
                                        Query updateDlDetailsQuery = em.createNativeQuery(" INSERT INTO int_tldetails(acno, closingbalance, "
                                                + "closingactualamt,CumuActualAmt, CumuPenalAmt, CumuAdhocAmt, todate) values('" + acNo + "',"
                                                + closingBal + "-(" + intAmt + "), " + closingBal + ",0,0,0,'" + toDt + "')");
                                        Integer updateDlDetails = updateDlDetailsQuery.executeUpdate();
                                        if (updateDlDetails == 0) {
                                            throw new ApplicationException("Value not inserted in  int_tldetails");
                                        }
                                    }

                                    String nextCalcDt = dateAdd(toDt, 1);
                                    Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '"
                                            + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                    if (updateCaRecon == 0) {
                                        throw new ApplicationException("Interest not Posted successfully");
                                    }

                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise "
                                            + "(SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo
                                            + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    sNo = sNo + 1;
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    glSumAmt = glSumAmt + intAmt;
                                }
//                                List SecDetailsList = em.createNativeQuery("SELECT a.ACNO, a.SCHEME_CODE, a.INTEREST_TABLE_CODE, a.MORATORIUM_PD, a.ACC_PREF_DR, "
//                                        + "a.ACC_PREF_CR, a.RATE_CODE, a.DISB_TYPE, a.CALC_METHOD, a.CALC_ON, a.INT_APP_FREQ, a.CALC_LEVEL, a.COMPOUND_FREQ, IFNULL(a.PEGG_FREQ,0), "
//                                        + "a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.recover  from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'").getResultList();
//                                if (!SecDetailsList.isEmpty()) {
//                                    Vector SecDetailsVect = (Vector) SecDetailsList.get(0);
//                                    String intAppFreq = (String) SecDetailsVect.get(10);
//                                    String recoveryOpt = (String) SecDetailsVect.get(16);
//                                    if (intAppFreq.equalsIgnoreCase("S") && recoveryOpt.equalsIgnoreCase("CIP")) {
//                                        String msg = fTSPosting43CBSBean.insertionAsPerPrincipalInt(acNo, toDt, intAmt);
//                                        if (!msg.equalsIgnoreCase("TRUE")) {
//                                            throw new ApplicationException(msg);
//                                        }
//                                    }
//                                }
                            }
                            if (i == intCalc.size() - 1) {
                                if (glSumAmt > 0) {
                                    float recNo = fTSPosting43CBSBean.getRecNo();
                                    Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ "
                                            + glSumAmt + " WHERE ACNO= '" + glAcNo + "'");
                                    Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                    if (updateReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't updated in reconbalan");
                                    }
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glAcNo + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + glSumAmt + ",0,8,3,3,'VCH INT. UP TO " + mdy.format(ymmd.parse(toDt)) + "','system','Y','"
                                            + authBy + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                                    }
                                }
                                if (!oirHead.equals("") && !uriGl.equals("")) {
                                    uriGl = brnCode + uriGl + "01";
                                    oirHead = brnCode + oirHead + "01";
                                    if (totalNpaIntAmt > 0) {
                                        float recNo = fTSPosting43CBSBean.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + totalNpaIntAmt + ",0,8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in gl_recon");
                                        }
                                        recNo = fTSPosting43CBSBean.getRecNo();
                                        insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                + totalNpaIntAmt + ",8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                        }

                                        Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + totalNpaIntAmt + " WHERE ACNO= '" + oirHead + "'");
                                        Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                        if (updateReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }

                                        Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + totalNpaIntAmt + " WHERE ACNO= '" + uriGl + "'");
                                        Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                                        if (updateReconBalanUri == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }
                                    }
                                }
                            }


                            String schemeCode = null;
                            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from cbs_loan_acc_mast_sec A,"
                                    + " loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'").getResultList();
                            if (SecDetailsList.isEmpty()) {
                                throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
                            } else {
                                for (int j = 0; j < SecDetailsList.size(); j++) {
                                    Vector SecDetailsVect = (Vector) SecDetailsList.get(j);
                                    schemeCode = (String) SecDetailsVect.get(1);
                                }
                            }

                            List flowDetailList = em.createNativeQuery("select INT_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where "
                                    + "SCHEME_CODE =  '" + schemeCode + "'").getResultList();
                            String intDemFlowId = null;
                            if (flowDetailList.isEmpty()) {
                                throw new ApplicationException("Flow Id Does Not Exists in Scheme Master.");
                            } else {
                                for (int k = 0; k < flowDetailList.size(); k++) {
                                    Vector flowDetailVect = (Vector) flowDetailList.get(k);
                                    intDemFlowId = flowDetailVect.get(0).toString();
                                }
                            }
                            Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,"
                                    + "POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt
                                    + "','" + toDt + "','" + brnCode + "','I')");
                            Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                            sNo = sNo + 1;
                            if (updateIntPostAcWise == 0) {
                                throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                            }
                            /*else {
                             if (!common.getAccseq().equalsIgnoreCase("M")) {
                             int dmdSchNo = 0;
                             Integer dmdSrl = null;
                             List loanDList = em.createNativeQuery("select dmd_srl_num from cbs_loan_dmd_table  where DMD_DATE = '" + toDt + "'").getResultList();
                             if (loanDList.size() > 0) {
                             Vector ele = (Vector) loanDList.get(0);
                             dmdSrl = Integer.parseInt(ele.get(0).toString());
                             } else {
                             List loanDMList = em.createNativeQuery("select IFNULL(max(dmd_srl_num),0) from cbs_loan_dmd_table ").getResultList();
                             if (loanDMList.size() > 0) {
                             Vector ele = (Vector) loanDMList.get(0);
                             dmdSrl = Integer.parseInt(ele.get(0).toString()) + 1;
                             }
                             }
                             Integer ShdlNo = null;
                             List snoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0) from cbs_loan_dmd_table").getResultList();
                             if (snoList.size() > 0) {
                             Vector secVec = (Vector) snoList.get(0);
                             ShdlNo = Integer.parseInt(secVec.get(0).toString());
                             if (ShdlNo == 0) {
                             ShdlNo = 1;
                             } else {
                             ShdlNo = ShdlNo + 1;
                             }
                             }
                             Integer tsCnt = 0;
                             String dmdFlowId = null;
                             List getDmdList = em.createNativeQuery("select ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_SRL_NUM,DMD_EFF_DATE,DMD_OVDU_DATE,"
                             + "DMD_AMT,LAST_ADJ_DATE,IFNULL(TOT_ADJ_AMT,0) as TOT_ADJ_AMT, IFNULL(EI_AMT,0) as EI_AMT,TS_CNT "
                             + "from cbs_loan_dmd_table  where acno = '" + acNo + "' and DMD_EFF_DATE between '" + fromDt + "' and '"
                             + toDt + "' and DEL_FLG = 'N' and LATEFEE_STATUS_FLG IN ('N','L','U')  order by acno, DMD_EFF_DATE, "
                             + "SHDL_NUM, DMD_SRL_NUM").getResultList();
                             if (getDmdList.size() > 0) {
                             for (int l = 0; l < getDmdList.size(); l++) {
                             Vector getDmdVect = (Vector) getDmdList.get(l);
                             dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                             dmdFlowId = getDmdVect.get(2).toString();
                             tsCnt = Integer.parseInt(getDmdVect.get(10).toString());
                             }
                             Integer loanDmdList = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET DMD_AMT = " + intAmt + ", DMD_EFF_DATE = '" + toDt + "', DMD_OVDU_DATE = '" + dateAdd(toDt, 1) + "',"
                             + " LCHG_USER_ID = '" + authBy + "', LCHG_TIME = NOW(), TS_CNT = " + tsCnt + " where ACNO = '" + acNo + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "'").executeUpdate();
                             if (loanDmdList < 0) {
                             throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");

                             }
                             } else {
                             Integer loanDmdList = em.createNativeQuery("Insert into cbs_loan_dmd_table(ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_DATE,"
                             + "DEL_FLG,LD_FREQ_TYPE,DMD_EFF_DATE,DMD_OVDU_DATE,DMD_AMT,RCRE_USER_ID,"
                             + "RCRE_TIME,EI_AMT,TS_CNT,LATEFEE_STATUS_FLG,dmd_srl_num) Values"
                             + "('" + acNo + "'," + ShdlNo + ",'" + intDemFlowId + "','" + toDt + "','N','M','" + toDt + "','" + dateAdd(toDt, 1) + "'," + intAmt + ","
                             + "'" + authBy + "',NOW()," + intAmt + ",0,'N'," + dmdSrl + ")").executeUpdate();
                             if (loanDmdList < 0) {
                             throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");
                             }
                             }
                             }
                             }*/
                        }
                    } else {
                        throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                    }
                }
                if (indAcAuthEntryFlag.equalsIgnoreCase("N")) {
                    List getParameterInfoIntList = em.createNativeQuery("select * from parameterinfo_int where ACTYPE='" + acType + "' AND MFLAG='N'  and brncode = '" + brnCode + "'").getResultList();
                    if (getParameterInfoIntList.size() > 0) {
                        Query updateParaInfoIntQuery = em.createNativeQuery("UPDATE parameterinfo_int SET MFLAG='Y',DT='" + toDt + "' WHERE ACTYPE='" + acType + "' AND MFLAG='N'  and brncode = '" + brnCode + "'");
                        Integer updateParaInfoInt = updateParaInfoIntQuery.executeUpdate();
                        if (updateParaInfoInt == 0) {
                            throw new ApplicationException("Value doesn't updated in  parameterinfo_int");
                        }
                        String minDt;
                        List getMinDtList = em.createNativeQuery("SELECT MIN(DT) FROM parameterinfo_int WHERE ACTYPE='" + acType + "' and brncode = '" + brnCode + "'").getResultList();
                        if (getMinDtList.size() > 0) {
                            Vector getMinDtVect = (Vector) getMinDtList.get(0);
                            minDt = getMinDtVect.get(0).toString();
                            updateParaInfoIntQuery = em.createNativeQuery("UPDATE parameterinfo_int SET MFLAG = 'N',DT = '" + dateAdd(toDt, 1) + "' WHERE ACTYPE = '" + acType + "' AND DT = '" + minDt + "'");
                            updateParaInfoInt = updateParaInfoIntQuery.executeUpdate();
                            if (updateParaInfoInt == 0) {
                                throw new ApplicationException("Value doesn't updated in  parameterinfo_int");
                            }
                        }
                    }

                    Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                            + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','Loan Interest',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                    if (insertReconBalan == 0) {
                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                    } else {
                        Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  "
                                + "WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'I' and brncode = '" + brnCode + "'");
                        Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                        if (updateAccTypeIntPara == 0) {
                            throw new ApplicationException("Value doesn't updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                        } else {
                            ut.commit();
                            //Sending Sms
                            try {
                                smsFacade.sendSmsToBatch(smsList);
                            } catch (Exception e) {
                                System.out.println("Problem In SMS Sending To Batch In "
                                        + "Transfer Authorization." + e.getMessage());
                            }
                            //End here
                            return "Interest posted successfully. Generated batch no is " + trSNo;
                        }
                    }
                } else {
                    ut.commit();
                    //Sending Sms
                    try {
                        smsFacade.sendSmsToBatch(smsList);
                    } catch (Exception e) {
                        System.out.println("Problem In SMS Sending To Batch In "
                                + "Transfer Authorization." + e.getMessage());
                    }
                    //End here
                    return "Interest posted successfully. Generated batch no is " + trSNo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
                 e.printStackTrace();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String caAccountInterestPosting(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {
        LoanIntCalcList it = new LoanIntCalcList();
        UserTransaction ut = context.getUserTransaction();
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            List tempBdList = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
            Vector tempBdVect = (Vector) tempBdList.get(0);
            String tempBd = tempBdVect.get(0).toString();

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = acNatureVect.get(0).toString();

            /*
             * Flag, to pass the Authrize entry in Individual Account
             */
            String indAcAuthEntryFlag = "N";
            List indAcAuthEntryFlagList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PASS_IND_AC_AUTH_ENTRY'").getResultList();
            if (!indAcAuthEntryFlagList.isEmpty()) {
                Vector indAcAuthEntryFlagVect = (Vector) indAcAuthEntryFlagList.get(0);
                indAcAuthEntryFlag = indAcAuthEntryFlagVect.get(0).toString();
            }

            ut.begin();
            String uriGl = "";
            String oirHead = "";
            double totalNpaIntAmt = 0;
//            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            List glHeadList = em.createNativeQuery("select ifnull(a.INTEREST_PAID_FLAG,''), ifnull(a.INTEREST_PAYABLE_ACCOUNT_PLACEHOLDER,'' ), IFNULL(b.GLHEADURI,''),IFNULL(b.glheadname,'')  from cbs_scheme_interest_or_service_charges_details a, accounttypemaster b where a.scheme_type = b.acctcode and a.scheme_type = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("Scheme is not defined for " + acType);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                if (glHeadVect.get(0).toString().equalsIgnoreCase("N") || glHeadVect.get(0).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("Interest is not allowed in " + acType);
                } else {
                    if (glHeadVect.get(1).toString().equalsIgnoreCase("")) {
                        throw new ApplicationException("GL Head Doesn't Exists for " + acType + " in Scheme");
                    } else {
                        glAcNo = brnCode + glHeadVect.get(1).toString() + "01";
                        uriGl = glHeadVect.get(2).toString();
                        oirHead = glHeadVect.get(3).toString();
                    }
                }
            }
            /**
             * Individual Account Wise Posting
             */
            if (intOpt.equalsIgnoreCase("I") && indAcAuthEntryFlag.equalsIgnoreCase("N")) {

                /*Getting TempTrSNo*/
                float dTrSNo = fTSPosting43CBSBean.getTrsNo();

                /*Getting TmpRecNo*/
                float dRecNo = fTSPosting43CBSBean.getRecNo();

                /*Getting Ac Status*/
                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "'").getResultList();
                Vector acStatusVect = (Vector) acStatusList.get(0);
                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());


                String schemeCode = null;
                List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from cbs_loan_acc_mast_sec A, loan_appparameter B "
                        + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
                if (SecDetailsList.isEmpty()) {
                    throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
                } else {
                    for (int i = 0; i < SecDetailsList.size(); i++) {
                        Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                        schemeCode = (String) SecDetailsVect.get(1);
                    }
                }

                List flowDetailList = em.createNativeQuery("select INT_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
                String intDemFlowId = null;
                if (flowDetailList.isEmpty()) {
                    throw new ApplicationException("false;Flow Id Does Not Exists in Scheme Master.");
                } else {
                    for (int i = 0; i < flowDetailList.size(); i++) {
                        Vector flowDetailVect = (Vector) flowDetailList.get(i);
                        intDemFlowId = flowDetailVect.get(0).toString();
                    }
                }
                /*
                 Getting the List as FromDT, ToDT, ActualProduct, Roi, IntAmt, IntTableCode
                 */
                it = accWiseCaAccountIntCalc(fromDt, toDt, acNo, brnCode);

                double intAmt = it.getTotalInt() * -1;
                double roi = it.getRoi();
                fromDt = it.getFirstDt();
                toDt = it.getLastDt();
                String strDetails = "DEPOSIT INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%";
                if (intAmt != 0) {
                    if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {

                        Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNo,Ty,Dt,valuedt,DrAmt,CrAmt,TranType,Details,iy,"
                                + "EnterBy,auth,Payby,Authby,Trantime,TranDesc,IntAmt,Status,recno, trsno,org_brnid, dest_brnid,balance)"
                                + " VALUES ('" + acNo + "',0,'" + tempBd + "','" + toDt + "',0," + intAmt + ",8,'" + strDetails + "',1,'" + authBy
                                + "','Y',3,'SYSTEM',NOW(),3," + intAmt + ",'" + acStatus + "'," + dRecNo + "," + dTrSNo + ",'" + brnCode + "','"
                                + brnCode + "',0.0)");
                        Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                        if (insertNpaRecon == 0) {
                            throw new ApplicationException("false;Value not inserted in  Npa_recon");
                        } else {
                            float recNo = fTSPosting43CBSBean.getRecNo();
                            if (!oirHead.equals("") && !uriGl.equals("")) {
                                uriGl = brnCode + uriGl + "01";
                                oirHead = brnCode + oirHead + "01";
                                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + oirHead + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                        + intAmt + ",8,3,3,'Deposit Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acNo + "','system','Y','" + authBy
                                        + "'," + dTrSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                if (insertReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                }
                                recNo = fTSPosting43CBSBean.getRecNo();
                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + uriGl + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                        + intAmt + ",0,8,3,3,'Deposit Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acNo + "','system','Y','" + authBy
                                        + "'," + dTrSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                insertReconBalan = insertReconBalanQuery.executeUpdate();
                                if (insertReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                }

                                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + intAmt + " WHERE ACNO= '" + oirHead + "'");
                                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                if (updateReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't updated in reconbalan");
                                }

                                Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + intAmt + " WHERE ACNO= '" + uriGl + "'");
                                Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                                if (updateReconBalanUri == 0) {
                                    throw new ApplicationException("Value doesn't updated in reconbalan");
                                }
                                List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                Vector sNoVect = (Vector) sNoList.get(0);
                                int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,"
                                        + "POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt
                                        + "','" + toDt + "','" + brnCode + "','I')");
                                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                if (updateIntPostAcWise == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                }
                                String nextCalcDt = dateAdd(toDt, 1);
//                                Query updateCaReconQuery = em.createNativeQuery(" UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
//                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
//                                if (updateCaRecon == 0) {
//                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_acc_mast_sec table");
//                                }
                                ut.commit();
                                return "Interest posted successfully.";
                            } else {
                                ut.commit();
                                return "Interest posted successfully.";
                            }
                        }
                    } else {
                        List acExistList = em.createNativeQuery("SELECT custname,jtname1,jtname2,jtname3,JTNAME4,IFNULL(odlimit,0) 'ODLIMIT' ,"
                                + "IFNULL(adhoclimit,0) 'ADHOCLIMIT',  opermode,accstatus,optstatus,DATE_FORMAT(openingdt,'%Y-%m-%d') 'OPENINGDT', "
                                + "rdinstal,rdmatdate,intdeposit,Instruction FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
                        if (acExistList.size() > 0) {
                            Vector acExistVect = (Vector) acExistList.get(0);
                            String custName = acExistVect.get(0).toString();
                            Query insertReconTrfDQuery = em.createNativeQuery("INSERT INTO recon_trf_d (acno,custName,cramt,enterBy,TranType,Ty,dt,"
                                    + "valuedt,details,trsno,recno,AUTH,Trandesc,PAYBY, org_brnid, dest_brnid,adviceNo,adviceBrnCode) "
                                    + "values ('" + acNo + "','" + custName + "'," + intAmt + ",'" + authBy + "',8,0,'" + tempBd + "','" + toDt
                                    + "','" + strDetails + "'," + dTrSNo + "," + dRecNo + ",'N',3,3,'" + brnCode + "','" + brnCode + "','','')");

                            Integer insertReconTrfD = insertReconTrfDQuery.executeUpdate();
                            if (insertReconTrfD == 0) {
                                throw new ApplicationException("false;Value not inserted in Recon_Trf_D");
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                Query updateCaReconQuery = em.createNativeQuery("UPDATE ca_reconbalan SET Balance = Balance + " + intAmt + " "
                                        + " WHERE Acno = '" + acNo + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Value not updated in  ca_reconbalan");
                                }
                            } else {
                                Query updateCaReconQuery = em.createNativeQuery(" UPDATE reconbalan SET Balance = Balance - " + intAmt + " "
                                        + "WHERE Acno = '" + acNo + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Value not updated in  reconbalan");
                                }
                                List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                Vector sNoVect = (Vector) sNoList.get(0);
                                int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                Query updateIntPostAcWiseQuery = em.createNativeQuery(" insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,"
                                        + "POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt
                                        + "','" + toDt + "','" + brnCode + "','D')");
                                Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                if (updateIntPostAcWise == 0) {
                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                }
//                                else {
//                                    int dmdSchNo = 0;
//                                    Integer dmdSrl = null;
//                                    List loanDList = em.createNativeQuery("select dmd_srl_num from cbs_loan_dmd_table  where DMD_DATE = '" + toDt + "'").getResultList();
//                                    if (loanDList.size() > 0) {
//                                        Vector ele = (Vector) loanDList.get(0);
//                                        dmdSrl = Integer.parseInt(ele.get(0).toString());
//                                    } else {
//                                        List loanDMList = em.createNativeQuery("select IFNULL(max(dmd_srl_num),0) from cbs_loan_dmd_table ").getResultList();
//                                        if (loanDMList.size() > 0) {
//                                            Vector ele = (Vector) loanDMList.get(0);
//                                            dmdSrl = Integer.parseInt(ele.get(0).toString()) + 1;
//                                        }
//                                    }
//                                    Integer ShdlNo = null;
//                                    List snoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0) from cbs_loan_dmd_table").getResultList();
//                                    if (snoList.size() > 0) {
//                                        Vector secVec = (Vector) snoList.get(0);
//                                        ShdlNo = Integer.parseInt(secVec.get(0).toString());
//                                        if (ShdlNo == 0) {
//                                            ShdlNo = 1;
//                                        } else {
//                                            ShdlNo = ShdlNo + 1;
//                                        }
//                                    }
//                                    Integer tsCnt = 0;
//                                    String dmdFlowId = null;
//                                    List getDmdList = em.createNativeQuery("select ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_SRL_NUM,DMD_EFF_DATE,DMD_OVDU_DATE,"
//                                            + "DMD_AMT,LAST_ADJ_DATE,IFNULL(TOT_ADJ_AMT,0) as TOT_ADJ_AMT, IFNULL(EI_AMT,0) as EI_AMT,TS_CNT from "
//                                            + "cbs_loan_dmd_table  where acno = '" + acNo + "' and DMD_EFF_DATE between '" + fromDt + "' and '" + toDt
//                                            + "' and DEL_FLG = 'N' and LATEFEE_STATUS_FLG IN ('N','L','U')  order by acno, DMD_EFF_DATE, SHDL_NUM, "
//                                            + "DMD_SRL_NUM").getResultList();
//                                    if (getDmdList.size() > 0) {
//                                        for (int i = 0; i < getDmdList.size(); i++) {
//                                            Vector getDmdVect = (Vector) getDmdList.get(i);
//                                            dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
//                                            dmdFlowId = getDmdVect.get(2).toString();
//                                            tsCnt = Integer.parseInt(getDmdVect.get(10).toString());
//                                        }
//                                        Integer loanDmdList = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET DMD_AMT = " + intAmt
//                                                + ", DMD_EFF_DATE = '" + toDt + "', DMD_OVDU_DATE = '" + dateAdd(toDt, 1) + "',"
//                                                + " LCHG_USER_ID = '" + authBy + "', LCHG_TIME = NOW(), TS_CNT = " + tsCnt + " where ACNO = '"
//                                                + acNo + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "'").executeUpdate();
//                                        if (loanDmdList < 0) {
//                                            throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");
//                                        }
//                                    } else {
//                                        Integer loanDmdList = em.createNativeQuery("Insert into cbs_loan_dmd_table(ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_DATE,"
//                                                + "DEL_FLG,LD_FREQ_TYPE,DMD_EFF_DATE,DMD_OVDU_DATE,DMD_AMT,RCRE_USER_ID,"
//                                                + "RCRE_TIME,EI_AMT,TS_CNT,LATEFEE_STATUS_FLG,dmd_srl_num) Values"
//                                                + "('" + acNo + "'," + ShdlNo + ",'" + intDemFlowId + "','" + toDt + "','N','M','" + toDt + "','" + dateAdd(toDt, 1) + "'," + intAmt + ","
//                                                + "'" + authBy + "',NOW()," + intAmt + ",0,'N'," + dmdSrl + ")").executeUpdate();
//                                        if (loanDmdList < 0) {
//                                            throw new ApplicationException("Data is not inserted into CBS_LOAN_DMD_TABLE");
//                                        }
//                                    }
//                                }
                            }
                            String details = "VCH$ As Deposit Int Amount From " + acNo;
                            String ACNO = glAcNo;
                            Integer tranType = 8;
                            Integer ty = 1;
                            Integer tranDesc = 3;
                            Float trSNo1 = dTrSNo;
                            Float recNo1 = 0f;
                            Integer tranId = 0;
                            String termId = "";
                            String auth = "";
                            String authBy1 = "";
                            String subtokenNo = "A";
                            Integer payBy = 3;
                            String instNo = "";
                            String instDt = "";
                            String tdAcNo = "";
                            Float voucherNo = null;
                            String intFlag = "";
                            String closeFlag = "";
                            String screenFlag = "";
                            String txnStatus = "";
                            Float tokenNoDr = 0f;
                            String cxSxFlag = "N";

                            String msg = fTSPosting43CBSBean.ftsPosting43CBS(ACNO, tranType, ty, intAmt, tempBd, tempBd, authBy, brnCode, brnCode,
                                    tranDesc, details, trSNo1, recNo1, tranId, termId, auth, authBy1, subtokenNo, payBy, instNo, instDt, tdAcNo,
                                    voucherNo, intFlag, closeFlag, screenFlag, txnStatus, tokenNoDr, cxSxFlag, "", "", "S");

                            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
//                                String nextCalcDt = dateAdd(toDt, 1);
//                                Query updateCaReconQuery = em.createNativeQuery(" UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
//                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
//                                if (updateCaRecon == 0) {
//                                    throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_acc_mast_sec table");
//                                } else {
                                ut.commit();
                                //Sending Sms
//                                    try {
//                                        smsFacade.sendTransactionalSms(SmsType.INTEREST_WITHDRAWAL, acNo, 8, 1,
//                                                intAmt, dmy.format(ymmd.parse(tempBd)));
//                                    } catch (Exception ex) {
//                                        System.out.println("Error SMS Sending-->A/c is::" + acNo + " And "
//                                                + "Amount is::" + intAmt);
//                                    }
                                //End here
                                return "Interest Posted Successfully!! Generated Batch No is " + dTrSNo;
//                                }
                            } else {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            throw new ApplicationException("Account No does Not Exist");
                        }
                    }
                } else {
                    throw new ApplicationException("Interest not Posted successfully, because Interest is calculated Zero.");
                }
            } else {
                /**
                 * All Account Wise Posting
                 */
                String indAcQuery = "";
                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();    //Sms List

                if (indAcAuthEntryFlag.equalsIgnoreCase("N")) {
                    List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'Deposit Interest' and "
                            + "actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or "
                            + "(todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                    if (parameterInfo.size() > 0) {
                        throw new ApplicationException("Interest Already Posted");
                    }
                } else {
                    indAcQuery = " and a.acno = '" + acNo + "' ";
                }
                float trSNo = fTSPosting43CBSBean.getTrsNo();
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                            + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1  and "
                            + " subString(a.acno,3,2)  = '" + acType + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                    List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c "
                            + "where a.acno = b.ACNO AND a.acno = c.ACNO  and subString(a.acno,3,2)  = '" + acType + "' " + indAcQuery + " and a.accStatus <> 9 "
                            + "and a.CurBrCode = '" + brnCode + "'").getResultList();

                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        double glSumAmt = 0f;
                        for (int i = 0; i < getAllAccList.size(); i++) {
                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = (String) getAllAccVect.get(0);
                            it = accWiseCaAccountIntCalc(fromDt, toDt, acNo, brnCode);
                            double intAmt = it.getTotalInt();
                            double closingbal = it.getClosingBal();
                            double roi = it.getRoi();
                            //Sms Object Creation
                            if (intAmt > 0) {
                                SmsToBatchTo to = new SmsToBatchTo();

                                to.setAcNo(acNo);
                                to.setCrAmt(0d);
                                to.setDrAmt(intAmt);
                                to.setTranType(8);
                                to.setTy(1);
                                to.setTxnDt(dmy.format(ymmd.parse(tempBd)));
                                to.setTemplate(SmsType.INTEREST_WITHDRAWAL);

                                smsList.add(to);
                            }
                            //End Here
                            if (intAmt != 0) {
                                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "' and accstatus <> 9").getResultList();
                                Vector acStatusVect = (Vector) acStatusList.get(0);
                                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {
                                    float recNo = fTSPosting43CBSBean.getRecNo();

                                    Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                            + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                            + " values('" + acNo + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                            + intAmt + ",8,'DEPOSIT INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                            + trSNo + "," + recNo + ",0,3," + intAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                            + brnCode + "')");

                                    Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                                    if (insertNpaRecon == 0) {
                                        throw new ApplicationException("Value not inserted in  npa_recon");
                                    }
                                    /**
                                     * * Added For NPA Accounts**
                                     */
                                    totalNpaIntAmt = totalNpaIntAmt + intAmt;
                                    String nextCalcDt = dateAdd(toDt, 1);
//                                    Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
//                                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
//                                    if (updateCaRecon == 0) {
//                                        throw new ApplicationException("Interest not Posted successfully");
//                                    }
                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','D')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    /**
                                     * * Addition End Here **
                                     */
                                } else {
                                    float recNo = fTSPosting43CBSBean.getRecNo();

                                    Query insertCaReconQuery = em.createNativeQuery("INSERT ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
                                            + " values('" + acNo + "',0, DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                            + intAmt + ",8,3,3,'DEPOSIT INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
                                            + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                    Integer insertCaRecon = insertCaReconQuery.executeUpdate();
                                    if (insertCaRecon == 0) {
                                        throw new ApplicationException("Value not inserted in Ca_Recon");
                                    }
                                    List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  ca_reconbalan WHERE ACNO='" + acNo + "'").getResultList();

                                    if (chkBalan.size() > 0) {
                                        Query updateCaReconQuery = em.createNativeQuery("UPDATE ca_reconbalan SET BALANCE = BALANCE - " + intAmt
                                                + " WHERE Acno = '" + acNo + "'");

                                        Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                        if (updateCaRecon == 0) {
                                            throw new ApplicationException("Value not updated in  ca_reconbalan");
                                        }
                                    }
                                    String nextCalcDt = dateAdd(toDt, 1);
//                                    Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
//                                    Integer updateCaRecon = updateCaReconQuery.executeUpdate();
//                                    if (updateCaRecon == 0) {
//                                        throw new ApplicationException("Interest not Posted successfully");
//                                    }
                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','D')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    glSumAmt = glSumAmt + intAmt;
                                }
                            }
                            if (i == getAllAccList.size() - 1) {
                                if (glSumAmt > 0) {
                                    float recNo = fTSPosting43CBSBean.getRecNo();
                                    Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNo + "'");
                                    Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                    if (updateReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                    }
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glAcNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                            + glSumAmt + ",8,3,3,'DEPOSIT VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                            + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                                    }
                                }
                                if (!oirHead.equals("") && !uriGl.equals("")) {
                                    uriGl = brnCode + uriGl + "01";
                                    oirHead = brnCode + oirHead + "01";
                                    if (totalNpaIntAmt > 0) {
                                        float recNo = fTSPosting43CBSBean.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + oirHead + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                + totalNpaIntAmt + ",8,3,3,'Deposit Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                        }
                                        recNo = fTSPosting43CBSBean.getRecNo();
                                        insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + uriGl + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + totalNpaIntAmt + ",0,8,3,3,'Deposit Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in gl_recon");
                                        }

                                        Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + totalNpaIntAmt + " WHERE ACNO= '" + oirHead + "'");
                                        Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                        if (updateReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }

                                        Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + totalNpaIntAmt + " WHERE ACNO= '" + uriGl + "'");
                                        Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                                        if (updateReconBalanUri == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                    }
                }
                if (indAcAuthEntryFlag.equalsIgnoreCase("N")) {
                    List getParameterInfoIntList = em.createNativeQuery("select * from parameterinfo_int where ACTYPE='" + acType + "' AND MFLAG='N'  and brncode = '" + brnCode + "'").getResultList();
                    if (getParameterInfoIntList.size() > 0) {
                        Query updateParaInfoIntQuery = em.createNativeQuery("UPDATE parameterinfo_int SET MFLAG='Y',DT='" + toDt + "' WHERE ACTYPE='" + acType + "' AND MFLAG='N'  and brncode = '" + brnCode + "'");
                        Integer updateParaInfoInt = updateParaInfoIntQuery.executeUpdate();
                        if (updateParaInfoInt == 0) {
                            throw new ApplicationException("Value doesn't updated in  parameterinfo_int");
                        }
                        String minDt;
                        List getMinDtList = em.createNativeQuery("SELECT MIN(DT) FROM parameterinfo_int WHERE ACTYPE='" + acType + "' and brncode = '" + brnCode + "'").getResultList();
                        if (getMinDtList.size() > 0) {
                            Vector getMinDtVect = (Vector) getMinDtList.get(0);
                            minDt = getMinDtVect.get(0).toString();
                            updateParaInfoIntQuery = em.createNativeQuery("UPDATE parameterinfo_int SET MFLAG = 'N',DT = '" + dateAdd(toDt, 1) + "' WHERE ACTYPE = '" + acType + "' AND DT = '" + minDt + "'");
                            updateParaInfoInt = updateParaInfoIntQuery.executeUpdate();
                            if (updateParaInfoInt == 0) {
                                throw new ApplicationException("Value doesn't updated in  parameterinfo_int");
                            }
                        }
                    }

                    Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                            + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','Deposit Interest',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                    if (insertReconBalan == 0) {
                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                    } else {
                        Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  "
                                + "WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'D' and brncode = '" + brnCode + "'");
                        Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                        if (updateAccTypeIntPara == 0) {
                            throw new ApplicationException("Value doesn't updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                        } else {
                            ut.commit();
                            //Sending Sms
                            try {
                                smsFacade.sendSmsToBatch(smsList);
                            } catch (Exception e) {
                                System.out.println("Problem In SMS Sending To Batch In "
                                        + "Transfer Authorization." + e.getMessage());
                            }
                            //End here
                            return "Interest posted successfully. Generated batch no is " + trSNo;
                        }
                    }
                } else {
                    ut.commit();
                    //Sending Sms
                    try {
                        smsFacade.sendSmsToBatch(smsList);
                    } catch (Exception e) {
                        System.out.println("Problem In SMS Sending To Batch In "
                                + "Transfer Authorization." + e.getMessage());
                    }
                    //End here
                    return "Interest posted successfully. Generated batch no is " + trSNo;
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String theftInterestPosting(List<LoanIntCalcList> intProductDetail, String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            List tempBdList = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
            Vector tempBdVect = (Vector) tempBdList.get(0);
            String tempBd = tempBdVect.get(0).toString();

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = acNatureVect.get(0).toString();

            ut.begin();

            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for " + acType);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                glAcNo = brnCode + glHeadVect.get(0).toString() + "01";
            }
            List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'Loan Interest' and "
                    + "actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or "
                    + "(todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
            if (parameterInfo.size() > 0) {
                throw new ApplicationException("Interest Already Posted");
            }
            float trSNo = fTSPosting43CBSBean.getTrsNo();
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                if (!intProductDetail.isEmpty()) {
                    double glSumAmt = 0f;
                    for (int i = 0; i < intProductDetail.size(); i++) {
                        acNo = intProductDetail.get(i).getAcNo();
                        double intAmt = new BigDecimal(intProductDetail.get(i).getTotalInt()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                        double closingbal = intProductDetail.get(i).getClosingBal();
                        double roi = intProductDetail.get(i).getRoi();

                        if (intAmt != 0) {
                            float recNo = fTSPosting43CBSBean.getRecNo();

                            Query insertCaReconQuery = em.createNativeQuery("INSERT recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                    + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
                                    + " values('" + acNo + "',0, DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                    + intAmt + ",8,3,3,'INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
                                    + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                            Integer insertCaRecon = insertCaReconQuery.executeUpdate();
                            if (insertCaRecon == 0) {
                                throw new ApplicationException("Value not inserted in Recon");
                            }
                            List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + acNo + "'").getResultList();

                            if (chkBalan.size() > 0) {
                                Query updateCaReconQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE = BALANCE - " + intAmt
                                        + " WHERE Acno = '" + acNo + "'");

                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Value not updated in  reconbalan");
                                }
                            }
                            String nextCalcDt = dateAdd(toDt, 1);
                            Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                            Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                            if (updateCaRecon == 0) {
                                throw new ApplicationException("Interest not Posted successfully");
                            }
                            glSumAmt = glSumAmt + intAmt;
                        }

                        if (i == intProductDetail.size() - 1) {
                            if (glSumAmt > 0) {
                                float recNo = fTSPosting43CBSBean.getRecNo();
                                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNo + "'");
                                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                if (updateReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                }
                                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + glAcNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                        + glSumAmt + ",8,3,3,'VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                if (insertReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't inserted in gl_recon");
                                }
                            }
                        }
                    }
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                }
            }
            Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                    + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','Loan Interest',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
            Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
            if (insertReconBalan == 0) {
                throw new ApplicationException("Value doesn't inserted in gl_recon");
            } else {
                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  "
                        + "WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'I' and brncode = '" + brnCode + "'");
                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                if (updateAccTypeIntPara == 0) {
                    throw new ApplicationException("Value doesn't updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                } else {
                    ut.commit();
                    return "Interest posted successfully. Generated batch no is " + trSNo;
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List getOnlyLoanAcctType() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') and "
                    + "accttype not in('" + CbsConstant.CURRENT_AC + "') ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public LoanIntCalcList txnLoanIntCalc(String toDt, String acNo, String brnCode) throws ApplicationException {
        LoanIntCalcList it = new LoanIntCalcList();
        try {
            double intTot = 0;
            double crTot = 0;
            double outStAmt = 0;
            double rateOfInt = 0;
            double interest = 0;
//            List frCrDtList = em.createNativeQuery("SELECT ifnull(date_format(max(dt),'%Y-%m-%d'),'') FROM loan_recon where acno = '"+ acNo +"' and dramt <>0 and trantype =8 and dt<='"+ toDt +"'").getResultList();
//            Vector frCrDtVect = (Vector) frCrDtList.get(0);
//            frDate = frCrDtVect.get(0).toString();
//            if(frDate.equalsIgnoreCase("")){
//                List frDrDtList = em.createNativeQuery("SELECT date_format(max(dt),'%Y-%m-%d') FROM loan_recon where acno = '"+ acNo +"' and dramt <>0 and trantype <>8 and dt<='"+ toDt +"'").getResultList();
//                Vector frDrDtVect = (Vector) frDrDtList.get(0);
//                frDate = frDrDtVect.get(0).toString();
//            }            

            List resultList = em.createNativeQuery("select DATE_FORMAT(next_int_calc_dt,'%Y-%m-%d') from cbs_loan_acc_mast_sec where acno='" + acNo + "'").getResultList();
            Vector frCrDtVect = (Vector) resultList.get(0);
            String frDate = frCrDtVect.get(0).toString();

            List intTotList = em.createNativeQuery("SELECT ifnull(sum(dramt),0) FROM loan_recon where acno = '" + acNo + "' and trantype =8 and dt<'" + toDt + "'").getResultList();
            Vector intTotVect = (Vector) intTotList.get(0);
            intTot = Double.parseDouble(intTotVect.get(0).toString());

            List crTotList = em.createNativeQuery("SELECT ifnull(sum(cramt),0) FROM loan_recon where acno = '" + acNo + "' and dt<'" + toDt + "'").getResultList();
            Vector crTotVect = (Vector) crTotList.get(0);
            crTot = Double.parseDouble(crTotVect.get(0).toString());

            List osTotList = em.createNativeQuery("SELECT ifnull(sum(cramt - dramt),0) FROM loan_recon where acno = '" + acNo + "' and dt<'" + toDt + "'").getResultList();
            Vector osTotVect = (Vector) osTotList.get(0);
            outStAmt = Double.parseDouble(osTotVect.get(0).toString());

            String roiGet = getRoiLoanAccount(outStAmt, frDate, acNo);
            rateOfInt = Double.parseDouble(roiGet);

            if (ymd.parse(frDate).compareTo(ymd.parse(toDt)) < 0) {
                Long dDiff = CbsUtil.dayDiff(ymd.parse(frDate), ymd.parse(toDt));
                double dayDiff = dDiff;

                if (crTot >= intTot) {
                    interest = rateOfInt * dayDiff * outStAmt / 36500;
                } else {
                    double balInt = intTot - crTot;
                    interest = rateOfInt * dayDiff * outStAmt / 36500;
                    interest = interest + balInt;
                }
            } else {
                interest = 0;
            }

            it.setAcNo(acNo);
            it.setFirstDt(frDate);
            it.setLastDt(toDt);
            it.setTotalInt(Double.parseDouble(formatter.format(CbsUtil.round(interest, 0))));
            it.setRoi(rateOfInt);
            return it;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List acWiseFromDtToDt(String year, String brnCode) throws ApplicationException {
        List dtList;
        try {
            dtList = em.createNativeQuery("select mindate,maxdate from yearend where f_year = '" + year + "' and brncode = '" + brnCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dtList;
    }

    public List<LoanIntCalcList> threftIntCalcPostal(String intOpt, String acType, String acNo, float roi, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();

        try {

            double dayDiff = (double) CbsUtil.dayDiff(dmy.parse(fromDt), dmy.parse(toDt));
            dayDiff = dayDiff + 1;
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            String calDt = fromDt.substring(0, 4) + "0930";
            String calFDt = fromDt.substring(0, 4) + "1001";

            List getAllAccList = null;
            List checkInSecOfLoan = null;

            String relatedAcct = "";
            List schCode = em.createNativeQuery("select status_option from cbs_scheme_general_scheme_parameter_master where "
                    + "scheme_type = '" + acType + "'").getResultList();
            for (int i = 0; i < schCode.size(); i++) {
                Vector schVect = (Vector) schCode.get(i);
                relatedAcct = (String) schVect.get(0);
            }

            if (intOpt.equalsIgnoreCase("I")) {
//                getAllAccList = em.createNativeQuery("select distinct a.acno,c.custid, a.custname,a.accstatus,ifnull(a.closingdate,''),ifnull(s.area,''),d.fName from accountmaster a, "
//                        + "(select distinct acno as racno from recon where acno = '" + acNo + "' and auth = 'Y' group by acno) b, customerid c,share_holder s,(select cast(customerid as unsigned) as cId,fathername as fName from cbs_customer_master_detail)d   "
//                        + " where a.acno = b.racno and "
//                        + " a.acno = '" + acNo + "' and a.acno = c.acno  and c.CustId = cast(s.custId as unsigned) and c.CustId = cId and a.openingdt< '" + toDt + "' and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();
                getAllAccList = em.createNativeQuery("select distinct a.acno,c.custid, a.custname,a.accstatus,ifnull(a.closingdate,''),'',d.fName from accountmaster a, "
                        + "(select distinct acno as racno from recon where acno = '" + acNo + "' and auth = 'Y' group by acno) b, customerid c,(select cast(customerid as unsigned) as cId,fathername as fName from cbs_customer_master_detail)d   "
                        + " where a.acno = b.racno and "
                        + " a.acno = '" + acNo + "' and a.acno = c.acno and c.CustId = cId and a.openingdt< '" + toDt + "' and a.CurBrCode = '" + brnCode + "' and a.accstatus <> 9 order by a.acno").getResultList();

                checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b, recon c "
                        + " where a.acno = b.ACNO AND a.acno = c.ACNO "
                        + " and a.acno = '" + acNo + "' and a.openingdt < '" + toDt + "' and a.CurBrCode = '" + brnCode + "' and a.accstatus <> 9 order by a.acno").getResultList();

            } else {
                /*
                 ALL ACCOUNT CALCULATION
                 */
//                getAllAccList = em.createNativeQuery("select distinct a.acno,c.custid, a.custname,a.accstatus,ifnull(a.closingdate,''),ifnull(s.area,''),d.fName from accountmaster a,  "
//                        + " (select distinct acno as racno from recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno) b, customerid c,share_holder s,(select cast(customerid as unsigned) as cId,fathername as fName from cbs_customer_master_detail)d "
//                        + " where a.acno = b.racno and "
//                        + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = c.acno and c.CustId = cast(s.custId as unsigned) and c.CustId = cId and a.openingdt< '" + toDt + "' and a.CurBrCode = '" + brnCode + "' order by s.area,a.acno").getResultList();
                getAllAccList = em.createNativeQuery("select distinct a.acno,c.custid, a.custname,a.accstatus,ifnull(a.closingdate,''),'',d.fName from accountmaster a,  "
                        + " (select distinct acno as racno from recon where substring(acno,3,2) = '" + acType + "' and auth = 'Y' group by acno) b, customerid c,(select cast(customerid as unsigned) as cId,fathername as fName from cbs_customer_master_detail)d "
                        + " where a.acno = b.racno and "
                        + " subString(a.acno,3,2)  = '" + acType + "' and a.acno = c.acno and c.CustId = cId and a.openingdt< '" + toDt + "' and a.CurBrCode = '" + brnCode + "' and a.accstatus <> 9 order by a.acno").getResultList();

                checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                        + " and subString(a.acno,3,2)  = '" + acType + "' and a.openingdt < '" + toDt + "' and a.CurBrCode = '" + brnCode + "' and a.accstatus <> 9 order by acno").getResultList();
            }

            if (getAllAccList.size() <= 0) {
                throw new ApplicationException("Account number does not exist.");
            }

//            if (getAllAccList.size() == checkInSecOfLoan.size()) {
            for (int i = 0; i < getAllAccList.size(); i++) {
                Vector getAllAccVect = (Vector) getAllAccList.get(i);
                acNo = (String) getAllAccVect.get(0);
                String custId = getAllAccVect.get(1).toString();
                String custName = getAllAccVect.get(2).toString();
                String area = getAllAccVect.get(5).toString();
                String fatherName = getAllAccVect.get(6).toString();
                double outSt = common.getBalanceOnDateWithoutInt(acNo, CbsUtil.dateAdd(fromDt, -1));
                double crAmt = 0, drAmt = 0, interest = 0, fInt = 0, interest1 = 0, drAmt1 = 0, totAmt = 0;

                List dtLst = em.createNativeQuery("select ifnull(date_format(max(dt),'%Y%m%d'),'19000101') from recon "
                        + "where acno = '" + acNo + "' and dramt<>0 and trantype<>8 and TranDesc <> 3 and dt <='" + calDt + "'").getResultList();
                Vector getDtVect = (Vector) dtLst.get(0);
                String closeDt = getDtVect.get(0).toString();

                List amtLst = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from recon where acno = '" + acNo + "' "
                        + "and dt between '" + fromDt + "' and '" + calDt + "' and trantype<>8 and TranDesc <> 3").getResultList();
                for (int j = 0; j < amtLst.size(); j++) {
                    Vector amtVect = (Vector) amtLst.get(j);
                    crAmt = Double.parseDouble(amtVect.get(0).toString());
                    drAmt = Double.parseDouble(amtVect.get(1).toString());
                }

                List amtDLst = em.createNativeQuery("select ifnull(sum(dramt),0) from recon where acno = '" + acNo + "' "
                        + "and dt between '" + calFDt + "' and '" + toDt + "' and trantype<>8 and TranDesc <> 3").getResultList();
                for (int j = 0; j < amtDLst.size(); j++) {
                    Vector amtDVect = (Vector) amtDLst.get(j);
                    //crAmt = Double.parseDouble(amtVect.get(0).toString());
                    drAmt1 = Double.parseDouble(amtDVect.get(0).toString());
                }
                if (drAmt > 0) {
                    double outSt1 = common.getBalanceOnDateWithoutInt(acNo, calDt);
                    if (outSt1 > 0) {
                        if (outSt1 > 0) {
                            interest = roi * dayDiff * outSt1 / 36500;
                        }

                        totAmt = (outSt + crAmt);
                        if (crAmt > 0) {
                            interest1 = (roi * dayDiff * crAmt / 36500) / 2;
                        }

                        if (drAmt1 > 0) {
                            fInt = (interest + interest1) / 2;
                        } else {
                            fInt = interest + interest1;
                        }
                    } else {
                        fInt = 0;
                    }
                } else {
                    if (outSt > 0) {
                        interest = roi * dayDiff * outSt / 36500;
                    }

                    totAmt = (outSt + crAmt);
                    if (crAmt > 0) {
                        interest1 = (roi * dayDiff * crAmt / 36500) / 2;
                    }

                    if (drAmt > 0) {
                        fInt = 0;
                    } else if (drAmt1 > 0) {
                        fInt = (interest + interest1) / 2;
                    } else {
                        fInt = interest + interest1;
                    }
                }
//                    if (ymmd.parse(closeDt).after(ymmd.parse(calDt))) {
//                        fInt = (interest + interest1) / 2;
//                    } else if (ymmd.parse(closeDt).before(ymmd.parse(calDt)) && (ymmd.parse(closeDt).after(ymmd.parse(fromDt)))) {
//                        fInt = 0;
//                    } else {
//                        fInt = interest + interest1;
//                    }

                String relAcno = "";
                List relAcLst = em.createNativeQuery("select a.acno from accountmaster a, customerid c where c.custid = '" + custId + "' "
                        + " and a.accttype = '" + relatedAcct + "' and a.acno = c.acno and a.accstatus<>9").getResultList();
                if (relAcLst.isEmpty()) {
                    relAcno = acNo;
                } else {
                    Vector relAcVect = (Vector) relAcLst.get(0);
                    relAcno = relAcVect.get(0).toString();
                }

                if (fInt > 0) {
                    LoanIntCalcList it = new LoanIntCalcList();
                    it.setAcNo(acNo);
                    it.setCustName(custName);
                    it.setIntTableCode(area);
                    it.setRoi(roi);
                    it.setProduct(Double.parseDouble(formatter.format(totAmt)));
                    it.setTotalInt(Double.parseDouble(formatter.format(fInt)));
                    it.setRelatedAc(relAcno);
                    it.setFirstDt(fromDt);
                    it.setLastDt(toDt);
                    it.setFatherName(fatherName);

                    intCalc.add(it);
                }
            }
            return intCalc;
//            } else {
//                throw new ApplicationException("Please Check all the '" + acType + "' account is exists in CBS_LOAN_ACC_MAST_SEC Table");
//            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String theftInterestPostalPosting(List<LoanIntCalcList> intProductDetail, String acType, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            float trSNo = fTSPosting43CBSBean.getTrsNo();
            String msg = "";
            if (!intProductDetail.isEmpty()) {
                double glSumAmt = 0f;
                for (int i = 0; i < intProductDetail.size(); i++) {
                    String acno = intProductDetail.get(i).getAcNo();
                    String roi = String.valueOf(intProductDetail.get(i).getRoi());
                    double intAmt = Double.parseDouble(formatter.format(Math.round(intProductDetail.get(i).getTotalInt())));
                    String details = "Threft Interest From " + fromDt + " To " + toDt + "@ " + roi;
                    if (intAmt != 0) {
                        float recNo = fTSPosting43CBSBean.getRecNo();
                        String lResult = fTSPosting43CBSBean.insertRecons(fTSPosting43CBSBean.getAccountNature(acno), acno, 0,
                                intAmt, ymd.format(new Date()), ymd.format(new Date()), 8, details, "SYSTEM",
                                trSNo, ymdhms.format(new Date()), recNo, "Y", authBy, 82, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                brnCode, acno.substring(0, 2), 0, null, "", "");
                        if (lResult.equalsIgnoreCase("True")) {
                            msg = fTSPosting43CBSBean.updateBalance(fTSPosting43CBSBean.getAccountNature(acno), acno, intAmt, 0, "Y", "Y");
                            if (msg.equalsIgnoreCase("True")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                        + "tran_id,adviceno,advicebrncode) values('" + acno + "',"
                                        + "'" + fTSPosting43CBSBean.ftsGetCustName(acno) + "','" + ymd.format(new Date()) + "',"
                                        + "'" + ymd.format(new Date()) + "'," + intAmt + ",0,0,8,"
                                        + "" + recNo + ",'',null,3,0,'Y','SYSTEM',"
                                        + "82 ,0,'A','" + details + "','" + authBy + "'," + trSNo + ",CURRENT_TIMESTAMP,"
                                        + "'','" + brnCode + "',"
                                        + "'" + acno.substring(0, 2) + "',0,'','')").executeUpdate();

                                if (insertResult <= 0) {
                                    throw new ApplicationException("Data insertion problem in transfer scroll for account number: " + acno);
                                }
                            } else {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            throw new ApplicationException(lResult);
                        }

                        glSumAmt = glSumAmt + intAmt;
                        String nextCalcDt = dateAdd(toDt, 1);
                        Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '" + toDt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acno + "'");
                        Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                        if (updateCaRecon == 0) {
                            throw new ApplicationException("Interest not Posted successfully");
                        }
                    }
                }

                float recNo = fTSPosting43CBSBean.getRecNo();
                String lResult = fTSPosting43CBSBean.insertRecons(fTSPosting43CBSBean.getAccountNature(glAcNo), glAcNo, 1,
                        Double.parseDouble(formatter.format(glSumAmt)), ymd.format(new Date()), ymd.format(new Date()), 8, "Threft Interest From " + fromDt + "To " + toDt, "SYSTEM",
                        trSNo, ymdhms.format(new Date()), recNo, "Y", authBy, 82, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                        brnCode, glAcNo.substring(0, 2), 0, null, "", "");
                if (lResult.equalsIgnoreCase("True")) {
                    msg = fTSPosting43CBSBean.updateBalance(fTSPosting43CBSBean.getAccountNature(glAcNo), glAcNo, 0, Double.parseDouble(formatter.format(glSumAmt)), "Y", "Y");
                    if (msg.equalsIgnoreCase("True")) {
                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                + "tran_id,adviceno,advicebrncode) values('" + glAcNo + "',"
                                + "'" + fTSPosting43CBSBean.ftsGetCustName(glAcNo) + "','" + ymd.format(new Date()) + "',"
                                + "'" + ymd.format(new Date()) + "',0," + Double.parseDouble(formatter.format(glSumAmt)) + ",1,8,"
                                + "" + recNo + ",'',null,3,0,'Y','SYSTEM',"
                                + "82 ,0,'A','','" + authBy + "'," + trSNo + ",CURRENT_TIMESTAMP,"
                                + "'','" + brnCode + "',"
                                + "'" + glAcNo.substring(0, 2) + "',0,'','')").executeUpdate();
                        if (insertResult <= 0) {
                            throw new ApplicationException("Data insertion problem in transfer scroll for account number: " + glAcNo);
                        }
                    } else {
                        throw new ApplicationException(msg);
                    }
                } else {
                    throw new ApplicationException(lResult);
                }

                float trSNo1 = fTSPosting43CBSBean.getTrsNo();

                String remTran = remoteBranchIdentification(intProductDetail);
                if (remTran.equalsIgnoreCase("TRUE")) {
                    for (int i = 0; i < intProductDetail.size(); i++) {
                        String acno = intProductDetail.get(i).getAcNo();
                        String relAcNo = intProductDetail.get(i).getRelatedAc();
                        if (!acno.equalsIgnoreCase(relAcNo)) {
                            double intAmt = Double.parseDouble(formatter.format(Math.round(intProductDetail.get(i).getTotalInt())));
                            String details = "Threft Interest Transfer From " + acno + " To " + relAcNo;

                            recNo = fTSPosting43CBSBean.getRecNo();
                            if (!brnCode.equalsIgnoreCase(acno.substring(0, 2))) {
                                msg = interBranchFacade.cbsPostingSx(acno, 1, ymd.format(new Date()), intAmt, 0f, 2, details, 0f, "", "", null,
                                        3, 0f, recNo, 3, acno.substring(0, 2), brnCode, "SYSTEM", authBy, trSNo1, "", "");
                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    msg = "TRUE";
                                } else {
                                    throw new ApplicationException("Data insertion problem from cbsPostingSx: " + acno);
                                }
                            } else {
                                msg = interBranchFacade.cbsPostingCx(acno, 1, ymd.format(new Date()), intAmt, 0f, 2, details, 0f, "", "", null,
                                        3, 0f, recNo, 3, acno.substring(0, 2), brnCode, "SYSTEM", authBy, trSNo1, "", "");
                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    msg = "TRUE";
                                } else {
                                    throw new ApplicationException("Data insertion problem from cbsPostingCx: " + acno);
                                }
                            }

                            if (msg.equalsIgnoreCase("True")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                        + "tran_id,adviceno,advicebrncode) values('" + acno + "',"
                                        + "'" + fTSPosting43CBSBean.ftsGetCustName(acno) + "','" + ymd.format(new Date()) + "',"
                                        + "'" + ymd.format(new Date()) + "',0," + intAmt + ",1,8,"
                                        + "" + recNo + ",'',null,3,0,'Y','SYSTEM',"
                                        + "82 ,0,'A','" + details + "','" + authBy + "'," + trSNo1 + ",CURRENT_TIMESTAMP,"
                                        + "'','" + brnCode + "',"
                                        + "'" + acno.substring(0, 2) + "',0,'','')").executeUpdate();

                                if (insertResult <= 0) {
                                    throw new ApplicationException("Data insertion problem in transfer scroll for account number: " + acno);
                                }
                            }

                            recNo = fTSPosting43CBSBean.getRecNo();
                            if (!brnCode.equalsIgnoreCase(relAcNo.substring(0, 2))) {
                                msg = interBranchFacade.cbsPostingSx(relAcNo, 0, ymd.format(new Date()), intAmt, 0f, 2, details, 0f, "", "", null,
                                        3, 0f, recNo, 3, relAcNo.substring(0, 2), brnCode, "SYSTEM", authBy, trSNo1, "", "");
                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    msg = "TRUE";
                                } else {
                                    throw new ApplicationException("Data insertion problem from cbsPostingSx: " + relAcNo);
                                }
                            } else {
                                msg = interBranchFacade.cbsPostingCx(relAcNo, 0, ymd.format(new Date()), intAmt, 0f, 2, details, 0f, "", "", null,
                                        3, 0f, recNo, 3, relAcNo.substring(0, 2), brnCode, "SYSTEM", authBy, trSNo1, "", "");
                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    msg = "TRUE";
                                } else {
                                    throw new ApplicationException("Data insertion problem from cbsPostingCx: " + relAcNo);
                                }
                            }

                            if (msg.equalsIgnoreCase("True")) {
                                int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                        + "tran_id,adviceno,advicebrncode) values('" + relAcNo + "',"
                                        + "'" + fTSPosting43CBSBean.ftsGetCustName(relAcNo) + "','" + ymd.format(new Date()) + "',"
                                        + "'" + ymd.format(new Date()) + "'," + intAmt + ",0,0,8,"
                                        + "" + recNo + ",'',null,3,0,'Y','SYSTEM',"
                                        + "82 ,0,'A','" + details + "','" + authBy + "'," + trSNo1 + ",CURRENT_TIMESTAMP,"
                                        + "'','" + brnCode + "',"
                                        + "'" + relAcNo.substring(0, 2) + "',0,'','')").executeUpdate();

                                if (insertResult <= 0) {
                                    throw new ApplicationException("Data insertion problem in transfer scroll for account number: " + acno);
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < intProductDetail.size(); i++) {
                        String acno = intProductDetail.get(i).getAcNo();
                        String relAcNo = intProductDetail.get(i).getRelatedAc();
                        if (!acno.equalsIgnoreCase(relAcNo)) {
                            double intAmt = Double.parseDouble(formatter.format(Math.round(intProductDetail.get(i).getTotalInt())));
                            String details = "Threft Interest Transfer From " + acno + " To " + relAcNo;

                            recNo = fTSPosting43CBSBean.getRecNo();
                            lResult = fTSPosting43CBSBean.insertRecons(fTSPosting43CBSBean.getAccountNature(acno), acno, 1,
                                    intAmt, ymd.format(new Date()), ymd.format(new Date()), 8, details, "SYSTEM",
                                    trSNo1, ymdhms.format(new Date()), recNo, "Y", authBy, 82, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                    brnCode, acno.substring(0, 2), 0, null, "", "");
                            if (lResult.equalsIgnoreCase("True")) {
                                msg = fTSPosting43CBSBean.updateBalance(fTSPosting43CBSBean.getAccountNature(acno), acno, intAmt, 0, "Y", "Y");
                                if (msg.equalsIgnoreCase("True")) {
                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                            + "tran_id,adviceno,advicebrncode) values('" + acno + "',"
                                            + "'" + fTSPosting43CBSBean.ftsGetCustName(acno) + "','" + ymd.format(new Date()) + "',"
                                            + "'" + ymd.format(new Date()) + "',0," + intAmt + ",1,8,"
                                            + "" + recNo + ",'',null,3,0,'Y','SYSTEM',"
                                            + "82 ,0,'A','" + details + "','" + authBy + "'," + trSNo1 + ",CURRENT_TIMESTAMP,"
                                            + "'','" + brnCode + "',"
                                            + "'" + acno.substring(0, 2) + "',0,'','')").executeUpdate();

                                    if (insertResult <= 0) {
                                        throw new ApplicationException("Data insertion problem in transfer scroll for account number: " + acno);
                                    }
                                } else {
                                    throw new ApplicationException(msg);
                                }
                            } else {
                                throw new ApplicationException(lResult);
                            }

                            recNo = fTSPosting43CBSBean.getRecNo();
                            lResult = fTSPosting43CBSBean.insertRecons(fTSPosting43CBSBean.getAccountNature(relAcNo), relAcNo, 0,
                                    intAmt, ymd.format(new Date()), ymd.format(new Date()), 8, details, "SYSTEM",
                                    trSNo1, ymdhms.format(new Date()), recNo, "Y", authBy, 82, 3, "", null, 0f, null, "A", 1, null, 0f, null, null,
                                    brnCode, acno.substring(0, 2), 0, null, "", "");
                            if (lResult.equalsIgnoreCase("True")) {
                                msg = fTSPosting43CBSBean.updateBalance(fTSPosting43CBSBean.getAccountNature(acno), acno, intAmt, 0, "Y", "Y");
                                if (msg.equalsIgnoreCase("True")) {
                                    int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                            + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                            + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                            + "tran_id,adviceno,advicebrncode) values('" + relAcNo + "',"
                                            + "'" + fTSPosting43CBSBean.ftsGetCustName(relAcNo) + "','" + ymd.format(new Date()) + "',"
                                            + "'" + ymd.format(new Date()) + "'," + intAmt + ",0,0,8,"
                                            + "" + recNo + ",'',null,3,0,'Y','SYSTEM',"
                                            + "82 ,0,'A','" + details + "','" + authBy + "'," + trSNo1 + ",CURRENT_TIMESTAMP,"
                                            + "'','" + brnCode + "',"
                                            + "'" + acno.substring(0, 2) + "',0,'','')").executeUpdate();

                                    if (insertResult <= 0) {
                                        throw new ApplicationException("Data insertion problem in transfer scroll for account number: " + acno);
                                    }
                                } else {
                                    throw new ApplicationException(msg);
                                }
                            } else {
                                throw new ApplicationException(lResult);
                            }
                        }
                    }
                }

                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  "
                        + "WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'I' and brncode = '" + brnCode + "'");
                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                if (updateAccTypeIntPara == 0) {
                    throw new ApplicationException("Value doesn't updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                } else {
                    ut.commit();
                    return "Interest posted successfully. Generated batch no is " + trSNo;
                }
            } else {
                throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String remoteBranchIdentification(List<LoanIntCalcList> intProductDetail) {
        String msg = null;
        for (LoanIntCalcList dtlLst : intProductDetail) {
            if (!(dtlLst.getAcNo().equalsIgnoreCase(dtlLst.getRelatedAc()))) {
                if (!dtlLst.getAcNo().substring(0, 2).equalsIgnoreCase(dtlLst.getRelatedAc().substring(0, 2))) {
                    msg = "TRUE";
                    return msg;
                } else {
                    msg = "FALSE";
                }
            } else {
                msg = "FALSE";
            }
        }
        return msg;
    }

    public String allFromDtForCharge(String acType, String brnCode, String want, String flag) throws ApplicationException {
        try {
            String toDt = null;
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType
                    + "' and POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = '" + flag + "'").getResultList();
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
                if (want.equalsIgnoreCase("f")) {
                    List getFrDtList = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
                    }
                } else if (want.equalsIgnoreCase("t")) {
                    List getFrDtList = em.createNativeQuery("select date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
                    }
                }
            } else {
                toDt = "Please check the existance of this account type in CBS_LOAN_ACCTYPE_INTEREST_PARAMETER Table for the current financial year.";
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
