/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.LoanMultiList;
import com.cbs.dto.LoanPrequistitsList;
import com.cbs.dto.ProvisionalCertificatePojo;
import com.cbs.dto.YearEndDatePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
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
import java.util.Map.Entry;
import java.util.Set;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author Sudhir
 */
@Stateless(mappedName = "LoanIntProductTestFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanIntProductTestFacade implements LoanIntProductTestFacadeRemote {

    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private AccountOpeningFacadeRemote acOpenFacadeRemote;
    @EJB
    RbiReportFacadeRemote rbiReportFacade;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat mdy = new SimpleDateFormat("MMM dd yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public List getAcctType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select acctcode,GlHeadInt from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public String allFromDt(String acType, String brnCode, String want) throws ApplicationException {
        try {
            String toDt = null;
            List getMaxToDtList = em.createNativeQuery("select MIN(SNO) from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = 'I'").getResultList();
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
                if (want.equalsIgnoreCase("f")) {
                    List getFrDtList = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in cbs_loan_acctype_interest_parameter Table for the current financial year.";
                    }
                } else if (want.equalsIgnoreCase("t")) {
                    List getFrDtList = em.createNativeQuery("select date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in cbs_loan_acctype_interest_parameter Table for the current financial year.";
                    }
                }
            } else {
                toDt = "Please check the existance of this account type in cbs_loan_acctype_interest_parameter Table for the current financial year.";
            }
            return toDt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    //*********************Get the From Date(Individual Account Wise)**********************//
    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException {
        try {
            List getMaxToDtList = em.createNativeQuery("select IFNULL(date_format(max(TODT), '%Y%m%d'),'') from  cbs_loan_interest_post_ac_wise where acno = '" + acNo + "' and BRNCODE = '" + brnCode + "' and POST_FLAG = 'Y' and FLAG = 'I'").getResultList();
            String toDt = "";
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                toDt = getMaxToDtVect.get(0).toString();
                if (toDt.equals("")) {
                    String acNature = ftsPostRemote.getAccountNature(acNo);
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from ca_recon where acno = '" + acNo + "' and ty = 1 and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                throw new ApplicationException("From date is not found");
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = fromDt;
                            }
                        } else {
                            throw new ApplicationException("From date is not found");
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from loan_recon where acno = '" + acNo + "' and ty = 1 and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                throw new ApplicationException("From date is not found");
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = fromDt;
                            }
                        } else {
                            throw new ApplicationException("From date is not found");
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                        List getMindtAcWiseList = em.createNativeQuery("select date_format(min(dt), '%Y%m%d') from recon where acno = '" + acNo + "' and org_brnid = '" + brnCode + "'").getResultList();
                        if (getMindtAcWiseList.size() > 0) {
                            Vector getMinDtAcWiseVect = (Vector) getMindtAcWiseList.get(0);
                            if (getMinDtAcWiseVect.get(0) == null || getMinDtAcWiseVect.get(0).toString().equalsIgnoreCase("")) {
                                throw new ApplicationException("From date is not found");
                            } else {
                                String fromDt = getMinDtAcWiseVect.get(0).toString();
                                toDt = fromDt;
                            }
                        } else {
                            throw new ApplicationException("From date is not found");
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

    public List<LoanMultiList> accWiseLoanIntProductCalcProvisional(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        List<ProvisionalCertificatePojo> intDetails = new ArrayList<ProvisionalCertificatePojo>();
        ProvisionalCertificatePojo it = new ProvisionalCertificatePojo();
        List<ProvisionalCertificatePojo> intProductDetail = new ArrayList<ProvisionalCertificatePojo>();
        String roi;
        try {
            String schemeCode = null;
            String intTableCode = null;
            int moratoriumPd;
            double accPrefDr = 0;
            double accPrefCr = 0;
            String rateCode = null;
            String disbType, acctDesc, custName, jointName1, currAdd, jointName2, jointName3, jointName4;
            String calcMethod = null;
            String calcOn;
            String intAppFreq = null;
            String calcLevel = null;
            String compoundFreq;
            int peggFreq = 0;
            int loanPdMonth;
            int loanPdDay;
            double rateOfInt = 0;
            double penalRoi;
            double sanctionLimit = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            String acNature = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            double subsidyAmt = 0;
            String subsidyExpDt = "";
            String sanctionDt = null;
            Date sancDt = null;
            List tempList = null;

            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank calculation method doesn't exist in Parameterinfo");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }
            tempList = em.createNativeQuery("select a.acno,a.custname,b.acctdesc,ifnull(a.jtname1,''),a.nomination,c.craddress,c.praddress,ifnull(a.jtname2,''),"
                    + "ifnull(a.jtname3,''),ifnull(a.jtname4,'') from accountmaster a,accounttypemaster b ,customermaster c where a.acno='" + acNo
                    + "' and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) "
                    + "and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2) = c.agcode").getResultList();
            Vector tempVector = (Vector) tempList.get(0);
            acctDesc = tempVector.get(2).toString();
            custName = tempVector.get(1).toString();
            jointName1 = tempVector.get(3).toString();
            currAdd = tempVector.get(5).toString();
            jointName2 = tempVector.get(7).toString();
            jointName3 = tempVector.get(8).toString();
            jointName4 = tempVector.get(9).toString();


            acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,''),B.Sanctionlimitdt  from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
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
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(19).toString();
                    intCompTillDt = SecDetailsVect.get(20).toString();
                    nextIntCalcDt = SecDetailsVect.get(21).toString();
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                    sancDt = (Date) SecDetailsVect.get(24);
                }
            }
            sanctionDt = dmy.format(sancDt);
            List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
            String eiFlowId = null;
            String prinDemFlowId = null;
            String disbFlowId;
            String colFlowId = null;
            String intDemFlowId = null;
            String penalIntDemFlowId = null;
            String overdueIntFlowId;
            String pastDueColNPAFlowId = null;
            String chgDemFlowId = null;
            double overFlowAmt = 0;

            if (flowDetailList.isEmpty()) {
                throw new ApplicationException("Flow Id does not exist in SchemeMaster..");
            } else {
                for (int j = 0; j < flowDetailList.size(); j++) {
                    Vector flowDetailVect = (Vector) flowDetailList.get(j);
                    eiFlowId = flowDetailVect.get(0).toString();
                    prinDemFlowId = flowDetailVect.get(1).toString();
                    disbFlowId = flowDetailVect.get(2).toString();
                    colFlowId = flowDetailVect.get(3).toString();
                    intDemFlowId = flowDetailVect.get(4).toString();
                    penalIntDemFlowId = flowDetailVect.get(5).toString();
                    overdueIntFlowId = flowDetailVect.get(6).toString();
                    pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                    chgDemFlowId = flowDetailVect.get(8).toString();
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
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";
            ArrayList datesFrom = new ArrayList();
            if (intAppFreq.equalsIgnoreCase("S")) {
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
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + //                            " union All " +
                            //                            " select DMD_DT as fromDt from CBS_LA_ADDITIONAL_TR_DETAILS where  acno = '" + acNo + "' and DMD_DT  BETWEEN '" + fromDt + "' and '" + toDt + "'  and DMD_FLOW_ID in('" + colFlowId + "','" + pastDueColNPAFlowId + "') and INT_ROUTING_FLAG = 'L' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'  group by DMD_DT " +
                            " order by  fromDt").getResultList();
                }
            } else if (intAppFreq.equalsIgnoreCase("C")) {
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
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' and tranDec not in ('3','4') GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' and tranDec not in ('3','4') group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' and tranDesc in (" + tranDescComp + ") and tranDesc not in ('1','3','4') group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + " union All "
                            + " select duedt as fromDt from emidetails where  acno = '" + acNo + "' and duedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  "
                            + " order by  fromDt").getResultList();

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

            for (Iterator i = datesFrom.iterator(); i.hasNext();) {
                String currentDate = i.next().toString();
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
            double emiAmt = 0;
            for (int i = 0; i < b.length - 1; i++) {
                ProvisionalCertificatePojo itProduct = new ProvisionalCertificatePojo();
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();

                /* Making the outstading according to the Calculation Level
                 IF Calculation Level is Sanction or Limit==================*/
                /*1. Oustanding */
                if (calcLevel.equals("L")) {

                    outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate));
                    List emiDueList = em.createNativeQuery("Select INSTALLAMT from emidetails where acno =" + acNo + " and duedt = '" + fDate + "'  and status ='UNPAID'").getResultList();
                    if (!emiDueList.isEmpty()) {
                        Vector emiDueVect = (Vector) emiDueList.get(0);
                        emiAmt = emiAmt + Double.parseDouble(emiDueVect.get(0).toString());
                    }
                    outSt = outSt + emiAmt;
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
                    String roiGet = loanInterestCalculationBean.getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                    if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                        throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + outSt);
                    } else {
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                }
                /*3. No. of days between From Date and To Date*/

                double dayDiff = (double) CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));

                /* In each slab, No. of days is increasing by 1  */
                dayDiff = dayDiff + 1;

                /*4. Interest Calculation*/
                double interest = rateOfInt * dayDiff * outSt / 36500;

                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                double wrongIntAmt = 0;
                List wrongPostedIntList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
                        + " union All "
                        + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by acno").getResultList();
                if (wrongPostedIntList.isEmpty()) {
                    wrongIntAmt = 0;
                } else {
                    for (int j = 0; j < wrongPostedIntList.size(); j++) {
                        Vector wrongPostedIntVect = (Vector) wrongPostedIntList.get(j);
                        wrongIntAmt = Double.parseDouble(wrongPostedIntVect.get(0).toString()) + wrongIntAmt;
                    }
                }
                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                b[i][2] = formatter.format(outSt);
                b[i][3] = Double.toString(rateOfInt);
                b[i][4] = Double.toString(dayDiff);
                b[i][5] = formatter.format(interest);
                b[i][6] = intTableCode;
                totalInt = totalInt + interest - wrongIntAmt;
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
                itProduct.setRoi(new BigDecimal(rateOfInt));
                itProduct.setProduct(new BigDecimal(outSt));
                itProduct.setClosingBal(new BigDecimal(dayDiff));
                itProduct.setAcNat(acctDesc);
                itProduct.setCurAdd(currAdd);
                itProduct.setJointName1(jointName1);
                itProduct.setJointName2(jointName2);
                itProduct.setJointName3(jointName3);
                itProduct.setJointName4(jointName4);
                itProduct.setSanctionAmt(new BigDecimal(sanctionLimit));
                itProduct.setSanctionDt(sanctionDt);
                itProduct.setTotalInt(new BigDecimal(Math.round(interest)));
                itProduct.setIntTableCode(intTableCode);

                intProductDetail.add(itProduct);
                itProduct = new ProvisionalCertificatePojo();
                /* 
                 * AdHoc Interest ROI 
                 */
                double adHocRoi = 0;
                double adHocInt = 0;
                double odLimit = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
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
                        if (adHocInt != 0) {
                            itProduct.setAcNo(acNo);
                            itProduct.setCustName(custName);
                            itProduct.setFirstDt(fDate);
                            itProduct.setLastDt(tDate);
                            itProduct.setRoi(new BigDecimal(adHocRoi));
                            itProduct.setProduct(new BigDecimal((outSt - odLimit * -1) * dayDiff));
                            itProduct.setClosingBal(new BigDecimal(dayDiff));
                            itProduct.setTotalInt(new BigDecimal(Math.round(adHocInt)));
                            itProduct.setIntTableCode("Adhoc Interest");
                            itProduct.setAcNat(acctDesc);
                            itProduct.setCurAdd(currAdd);
                            itProduct.setJointName1(jointName1);
                            itProduct.setJointName2(jointName2);
                            itProduct.setJointName3(jointName3);
                            itProduct.setJointName4(jointName4);
                            itProduct.setSanctionAmt(new BigDecimal(sanctionLimit));
                            itProduct.setSanctionDt(sanctionDt);
                            intProductDetail.add(itProduct);
                            product = product + outSt;
                        }
                    }
                }
            }

            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setAcNat(acctDesc);
            it.setSanctionAmt(new BigDecimal(sanctionLimit));
            it.setClosingBal(new BigDecimal(closingBal));
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setProduct(new BigDecimal(product));
            it.setTotalInt(new BigDecimal(totalInt));
            it.setRoi(new BigDecimal(rateOfInt));
            it.setCurAdd(currAdd);
            it.setIntTableCode(intTableCode);
            intDetails.add(it);
            la.setLoanIntDetails(intDetails);
            la.setLoanProductIntDetails(intProductDetail);
            lml.add(la);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lml;
    }
    /*
     * Loan Interest Product Calculation 
     */

    public List<LoanMultiList> accWiseLoanIntProductCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        String roi;
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
            double penalRoi;
            double sanctionLimit = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            String acNature = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            double subsidyAmt = 0, roiGlobal = 0;
            String subsidyExpDt = "", intCalcBasedOnSecurity = "N", orderByParameter = "E";

            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank calculation method doesn't exist in Parameterinfo");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }

            acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,''), ifnull(sg.TURN_OVER_DETAIL_FLAG,'N'), ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E')  from cbs_loan_acc_mast_sec A, loan_appparameter B, cbs_scheme_general_scheme_parameter_master sg where A.ACNO = B.ACNO AND A.SCHEME_CODE = sg.scheme_code AND A.ACNO ='" + acNo + "'").getResultList();
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
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(19).toString();
                    intCompTillDt = SecDetailsVect.get(20).toString();
                    nextIntCalcDt = SecDetailsVect.get(21).toString();
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                    intCalcBasedOnSecurity = SecDetailsVect.get(24).toString();
                    orderByParameter = SecDetailsVect.get(25).toString();
                }
            }
            List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
            String eiFlowId = null;
            String prinDemFlowId = null;
            String disbFlowId;
            String colFlowId = null;
            String intDemFlowId = null;
            String penalIntDemFlowId = null;
            String overdueIntFlowId;
            String pastDueColNPAFlowId = null;
            String chgDemFlowId = null;
            double overFlowAmt = 0;

            if (flowDetailList.isEmpty()) {
                throw new ApplicationException("Flow Id does not exist in SchemeMaster..");
            } else {
                for (int j = 0; j < flowDetailList.size(); j++) {
                    Vector flowDetailVect = (Vector) flowDetailList.get(j);
                    eiFlowId = flowDetailVect.get(0).toString();
                    prinDemFlowId = flowDetailVect.get(1).toString();
                    disbFlowId = flowDetailVect.get(2).toString();
                    colFlowId = flowDetailVect.get(3).toString();
                    intDemFlowId = flowDetailVect.get(4).toString();
                    penalIntDemFlowId = flowDetailVect.get(5).toString();
                    overdueIntFlowId = flowDetailVect.get(6).toString();
                    pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                    chgDemFlowId = flowDetailVect.get(8).toString();
                }
            }

            String custName = "";
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
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
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";
            ArrayList datesFrom = new ArrayList();
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
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + //                            " union All " +
                            //                            " select DMD_DT as fromDt from CBS_LA_ADDITIONAL_TR_DETAILS where  acno = '" + acNo + "' and DMD_DT  BETWEEN '" + fromDt + "' and '" + toDt + "'  and DMD_FLOW_ID in('" + colFlowId + "','" + pastDueColNPAFlowId + "') and INT_ROUTING_FLAG = 'L' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'  group by DMD_DT " +
                            " order by  fromDt").getResultList();
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
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + //                            " union All " +
                            //                            " select DMD_DT as fromDt from CBS_LA_ADDITIONAL_TR_DETAILS where  acno = '" + acNo + "' and DMD_DT  BETWEEN '" + fromDt + "' and '" + toDt + "'  and DMD_FLOW_ID in('" + colFlowId + "','" + pastDueColNPAFlowId + "') and INT_ROUTING_FLAG = 'L' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'  group by DMD_DT" +
                            " order by  fromDt").getResultList();

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
                                datesFrom.add(a[i][1]);
                                if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                                    datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                }
                            }
                        } else {
                            getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            datesFrom.add(a[i][0]);
                            getAdhocDateChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                                datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            }
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                datesFrom.add(a[i][1]);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                                    datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                                }
                            }
                        }
                    } else if (i > 0 && i < unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                            datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        }
                    } else if (i == unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        a[i][1] = ymd.format(ymmd.parse(toDt));
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        getAdhocDateChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                            datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, a[i - 1][0], a[i - 1][1], datesFrom);
                        }
                        if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                            /*=======Getting the ROI Change Date in Current i Position=======================*/
                            getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                            datesFrom.add(a[i][1]);
                            getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
                            if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                                datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, ymd.format(ymd.parse(a[i][0])), ymd.format(ymd.parse(a[i][1])), datesFrom);
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
                if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                    datesFrom = loanInterestCalculationBean.getSecurityAddExpDtChangeSlab(acNo, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                }
                Collections.sort(datesFrom);
            }

            for (Iterator i = datesFrom.iterator(); i.hasNext();) {
                String currentDate = i.next().toString();
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
            for (int i = 0; i < b.length - 1; i++) {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();

                /* Making the outstading according to the Calculation Level
                 IF Calculation Level is Sanction or Limit==================*/
                /*1. Oustanding */
                if (calcLevel.equals("L")) {
                    outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate));
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
                    String roiGet = loanInterestCalculationBean.getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                    if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                        throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + outSt);
                    } else {
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                }
                /*3. No. of days between From Date and To Date*/

                double dayDiff = (double) CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));

                /* In each slab, No. of days is increasing by 1  */
                dayDiff = dayDiff + 1;

                /*4. Interest Calculation*/
                double interest = 0d;
                if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                    double intAmt = 0d, matValue = 0d, lienValue = 0d, lienValue1 = 0d, securityRoi = 0d, margineROI = 0d, appRoi = 0d, margin = 0d, roi1 = 0d, additionalRoi = 0d;
                    int stmFreq = 0;
                    if (outSt < 0) {
                        outSt = outSt * -1;
//                        List paramList = em.createNativeQuery("select code from cbs_parameterinfo where NAME = 'SEC_ROI_CALC'").getResultList();
                        String orderBy = " aa.Entrydate ";
//                        if (!paramList.isEmpty()) {
//                            Vector orderVect = (Vector) paramList.get(0);
//                            if (orderVect.get(0).toString().equalsIgnoreCase("R")) { //AppRoi
//                                orderBy = " AppRoi, Entrydate";
//                            } else if (orderVect.get(0).toString().equalsIgnoreCase("DR")) { //AppRoi desc
//                                orderBy = " AppRoi desc, Entrydate ";
//                            } else if (orderVect.get(0).toString().equalsIgnoreCase("DT")) { //Entrydate
//                                orderBy = " Entrydate ";
//                            }
//                        }
                        if (orderByParameter.equalsIgnoreCase("A")) { //AppRoi
                            orderBy = " aa.AppRoi, aa.Entrydate";
                        } else if (orderByParameter.equalsIgnoreCase("R")) { //AppRoi desc
                            orderBy = " aa.AppRoi desc, aa.Entrydate ";
                        } else if (orderByParameter.equalsIgnoreCase("E")) { //Entrydate
                            orderBy = " aa.Entrydate ";
                        } 
                        List securityList = em.createNativeQuery("select aa.matvalue, aa.matdate, aa.lienvalue, aa.Issuedate, aa.SecurityRoi, aa.MargineROI, aa.AppRoi, aa.IntTableCode, aa.Margin,aa.STMFrequency, aa.Entrydate, aa.addRoi, aa.sno from "
                                + "(select sno, matvalue, matdate, lienvalue, Issuedate, ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(Margin,100) as Margin, ifnull(cast(STMFrequency as unsigned),0) as STMFrequency, Entrydate, ifnull(addRoi,0) as addRoi from loansecurity where acno = '" + acNo + "' and status = 'Active'  and IntTableCode is not null and security = 'P' and entrydate <= '" + tDate + "' and IntTableCode <>''"
                                + " union all "
                                + " select sno, matvalue, matdate, lienvalue, Issuedate, ifnull(SecurityRoi,0) as SecurityRoi, ifnull(MargineROI,0) as MargineROI, ifnull(AppRoi,0) as AppRoi, IntTableCode, ifnull(Margin,100) as Margin, ifnull(cast(STMFrequency as unsigned),0) as STMFrequency, Entrydate, ifnull(addRoi,0) as addRoi from loansecurity where acno = '" + acNo + "' and status = 'EXPIRED' and IntTableCode is not null and security = 'P' and entrydate <= '" + tDate + "' and ExpiryDate > '" + tDate + "' and IntTableCode <>'') aa order by " + orderBy).getResultList();
                        if (!securityList.isEmpty()) {
                            for (int z = 0; z < securityList.size(); z++) {
                                Vector secVect = (Vector) securityList.get(z);
                                roi1 = 0;
                                matValue = Double.parseDouble(secVect.get(0).toString());
                                String matDate = secVect.get(1).toString();
                                lienValue = Double.parseDouble(secVect.get(2).toString());
                                lienValue1 = Double.parseDouble(secVect.get(2).toString());
                                String issueDate = secVect.get(3).toString();
                                securityRoi = Double.parseDouble(secVect.get(4).toString());
                                margineROI = Double.parseDouble(secVect.get(5).toString());
                                appRoi = Double.parseDouble(secVect.get(6).toString());
                                String intTableCode1 = secVect.get(7).toString();
                                margin = Double.parseDouble(secVect.get(8).toString());
                                stmFreq = Integer.parseInt(secVect.get(9).toString());
                                additionalRoi = Double.parseDouble(secVect.get(11).toString());

//                                lienValue = lienValue - ((lienValue * margin)/100);
                                if (stmFreq == 7) {
                                    lienValue = lienValue;
                                    outSt = outSt;
                                } else {
                                    if ((outSt <= lienValue) && (outSt != 0)) {
                                        lienValue = outSt;
                                        outSt = 0;
                                        z = securityList.size() - 1;
                                    } else {
                                        lienValue = lienValue;
                                        outSt = outSt - lienValue;
                                    }
                                }
                                roi1 = securityRoi + additionalRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode1, (stmFreq == 7 ? lienValue1 : lienValue), ymd.format(ymd.parse(fDate))));
                                roiGlobal = roi1;
                                if (stmFreq == 7) {
                                    interest = ((outSt * roi1 * dayDiff) / 36500);
                                    if ((outSt <= lienValue) && (outSt != 0)) {
                                        lienValue = outSt;
                                        outSt = 0;
                                        z = securityList.size() - 1;
                                    } else {
                                        lienValue = lienValue;
                                        outSt = outSt - lienValue;
                                    }
                                } else {
                                    interest = ((lienValue * roi1 * dayDiff) / 36500);
                                }

                                /**
                                 * *********************Add on for if wrong
                                 * interest posted *******************
                                 */
                                /*double wrongIntAmt = 0;
                                 List wrongPostedIntList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
                                 + " union All "
                                 + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by acno").getResultList();
                                 if (wrongPostedIntList.isEmpty()) {
                                 //                    throw new ApplicationException("No tranction done in this account");
                                 wrongIntAmt = 0;
                                 } else {
                                 for (int j = 0; j < wrongPostedIntList.size(); j++) {
                                 Vector wrongPostedIntVect = (Vector) wrongPostedIntList.get(j);
                                 wrongIntAmt = Double.parseDouble(wrongPostedIntVect.get(0).toString()) + wrongIntAmt;
                                 //                        if (wrongIntAmt > 0) {
                                 //                            wrongIntAmt = 0;
                                 //                        }
                                 }
                                 }*/
                                /**
                                 * *********************Add on for if wrong
                                 * interest posted *******************
                                 */
                                b[i][2] = Double.toString(lienValue);
                                b[i][3] = Double.toString(roi1);
                                b[i][4] = Double.toString(dayDiff);
                                b[i][5] = Double.toString(interest);
                                b[i][6] = intTableCode;
                                totalInt = totalInt + interest; //- wrongIntAmt;
                                if (i == 0) {
                                    firstDt = fDate;
                                } else if (i == b.length - 2) {
                                    lastDt = tDate;
                                }
                                closingBal = Double.parseDouble(b[i][2]);
                                roi = b[i][3];
                                product = Double.parseDouble(b[i][2]);

                                itProduct.setAcNo(acNo);
                                itProduct.setCustName(custName);
                                itProduct.setFirstDt(fDate);
                                itProduct.setLastDt(tDate);
                                itProduct.setRoi(roi1);
                                itProduct.setFdProduct(lienValue);
                                itProduct.setFdInt(i + 1);
                                itProduct.setProduct(lienValue * dayDiff);
                                itProduct.setClosingBal(dayDiff);
                                itProduct.setTotalInt(interest);
                                itProduct.setIntTableCode(intTableCode);

                                intProductDetail.add(itProduct);
                                itProduct = new LoanIntCalcList();
                                /* 
                                 * AdHoc Interest ROI 
                                 */
                                double adHocRoi = 0;
                                double adHocInt = 0;
                                double odLimit = 0;
                                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
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
                                        if (adHocInt != 0) {
                                            itProduct.setAcNo(acNo);
                                            itProduct.setCustName(custName);
                                            itProduct.setFirstDt(fDate);
                                            itProduct.setLastDt(tDate);
                                            itProduct.setRoi(adHocRoi);
                                            itProduct.setFdProduct(outSt);
                                            itProduct.setFdInt(i + 1);
                                            itProduct.setProduct((outSt - odLimit * -1) * dayDiff);
                                            itProduct.setClosingBal(dayDiff);
                                            itProduct.setTotalInt((adHocInt));
                                            itProduct.setIntTableCode("Adhoc Interest");

                                            intProductDetail.add(itProduct);
                                            product = product + outSt;
                                        }
                                    }
                                }
                            }
                        } else {                            
                            interest = ((outSt * roiGlobal * dayDiff) / 36500);                            
                            
                            b[i][2] = Double.toString(lienValue);
                            b[i][3] = Double.toString(roi1);
                            b[i][4] = Double.toString(dayDiff);
                            b[i][5] = Double.toString(interest);
                            b[i][6] = intTableCode;
                            totalInt = totalInt + interest; //- wrongIntAmt;
                            if (i == 0) {
                                firstDt = fDate;
                            } else if (i == b.length - 2) {
                                lastDt = tDate;
                            }
                            closingBal = Double.parseDouble(b[i][2]);
                            roi = b[i][3];
                            product = Double.parseDouble(b[i][2]);

                            itProduct.setAcNo(acNo);
                            itProduct.setCustName(custName);
                            itProduct.setFirstDt(fDate);
                            itProduct.setLastDt(tDate);
                            itProduct.setRoi(roiGlobal);
                            itProduct.setFdProduct(lienValue);
                            itProduct.setFdInt(i + 1);
                            itProduct.setProduct(outSt * dayDiff);
                            itProduct.setClosingBal(dayDiff);
                            itProduct.setTotalInt(interest);
                            itProduct.setIntTableCode(intTableCode);

                            intProductDetail.add(itProduct);
                            itProduct = new LoanIntCalcList();
                            /* 
                             * AdHoc Interest ROI 
                             */
                            double adHocRoi = 0;
                            double adHocInt = 0;
                            double odLimit = 0;
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
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
                                    if (adHocInt != 0) {
                                        itProduct.setAcNo(acNo);
                                        itProduct.setCustName(custName);
                                        itProduct.setFirstDt(fDate);
                                        itProduct.setLastDt(tDate);
                                        itProduct.setRoi(adHocRoi);
                                        itProduct.setFdProduct(outSt);
                                        itProduct.setFdInt(i + 1);
                                        itProduct.setProduct((outSt - odLimit * -1) * dayDiff);
                                        itProduct.setClosingBal(dayDiff);
                                        itProduct.setTotalInt((adHocInt));
                                        itProduct.setIntTableCode("Adhoc Interest");

                                        intProductDetail.add(itProduct);
                                        product = product + outSt;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    interest = rateOfInt * dayDiff * outSt / 36500;

                    /**
                     * *********************Add on for if wrong interest posted
                     * *******************
                     */
                    double wrongIntAmt = 0;
                    List wrongPostedIntList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
                            + " union All "
                            + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by acno").getResultList();
                    if (wrongPostedIntList.isEmpty()) {
                        //                    throw new ApplicationException("No tranction done in this account");
                        wrongIntAmt = 0;
                    } else {
                        for (int j = 0; j < wrongPostedIntList.size(); j++) {
                            Vector wrongPostedIntVect = (Vector) wrongPostedIntList.get(j);
                            wrongIntAmt = Double.parseDouble(wrongPostedIntVect.get(0).toString()) + wrongIntAmt;
                            //                        if (wrongIntAmt > 0) {
                            //                            wrongIntAmt = 0;
                            //                        }
                        }
                    }
                    /**
                     * *********************Add on for if wrong interest posted
                     * *******************
                     */
                    b[i][2] = formatter.format(outSt);
                    b[i][3] = Double.toString(rateOfInt);
                    b[i][4] = Double.toString(dayDiff);
                    b[i][5] = formatter.format(interest);
                    b[i][6] = intTableCode;
                    totalInt = totalInt + interest - wrongIntAmt;
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
                    itProduct.setFdProduct(outSt);
                    itProduct.setFdInt(i + 1);
                    itProduct.setProduct(outSt * dayDiff);
                    itProduct.setClosingBal(dayDiff);
                    itProduct.setTotalInt((interest));
                    itProduct.setIntTableCode(intTableCode);

                    intProductDetail.add(itProduct);
                    itProduct = new LoanIntCalcList();
                    /* 
                     * AdHoc Interest ROI 
                     */
                    double adHocRoi = 0;
                    double adHocInt = 0;
                    double odLimit = 0;
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
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
                            if (adHocInt != 0) {
                                itProduct.setAcNo(acNo);
                                itProduct.setCustName(custName);
                                itProduct.setFirstDt(fDate);
                                itProduct.setLastDt(tDate);
                                itProduct.setRoi(adHocRoi);
                                itProduct.setFdProduct(outSt);
                                itProduct.setFdInt(i + 1);
                                itProduct.setProduct((outSt - odLimit * -1) * dayDiff);
                                itProduct.setClosingBal(dayDiff);
                                itProduct.setTotalInt((adHocInt));
                                itProduct.setIntTableCode("Adhoc Interest");

                                intProductDetail.add(itProduct);
                                product = product + outSt;
                            }
                        }
                    }
                }
            }

            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double((totalInt)))));
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);
            intDetails.add(it);
            la.setLoanIntDetails(intDetails);
            la.setLoanProductIntDetails(intProductDetail);
            lml.add(la);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return lml;
    }

    public List<LoanMultiList> accWiseLoanIntProductCalcAsPerEmiRecovery(String fromDt, String toDt, String acNo, String brnCode, String roiFromUserFlag, double roiFromUser) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        String roi;
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
            double penalRoi;
            double sanctionLimit = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            String acNature = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            double subsidyAmt = 0;
            String subsidyExpDt = "", emiStartFlag = "N", openingDt = fromDt;

            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank calculation method doesn't exist in Parameterinfo");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }

            acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,''), c.OpeningDt  from cbs_loan_acc_mast_sec A, loan_appparameter B, accountmaster c where A.acno = c.acno and A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
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
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(19).toString();
                    intCompTillDt = SecDetailsVect.get(20).toString();
                    nextIntCalcDt = SecDetailsVect.get(21).toString();
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                    openingDt = SecDetailsVect.get(24).toString();
                }
            }
            List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
            String eiFlowId = null;
            String prinDemFlowId = null;
            String disbFlowId;
            String colFlowId = null;
            String intDemFlowId = null;
            String penalIntDemFlowId = null;
            String overdueIntFlowId;
            String pastDueColNPAFlowId = null;
            String chgDemFlowId = null;
            double overFlowAmt = 0;

            if (flowDetailList.isEmpty()) {
                throw new ApplicationException("Flow Id does not exist in SchemeMaster..");
            } else {
                for (int j = 0; j < flowDetailList.size(); j++) {
                    Vector flowDetailVect = (Vector) flowDetailList.get(j);
                    eiFlowId = flowDetailVect.get(0).toString();
                    prinDemFlowId = flowDetailVect.get(1).toString();
                    disbFlowId = flowDetailVect.get(2).toString();
                    colFlowId = flowDetailVect.get(3).toString();
                    intDemFlowId = flowDetailVect.get(4).toString();
                    penalIntDemFlowId = flowDetailVect.get(5).toString();
                    overdueIntFlowId = flowDetailVect.get(6).toString();
                    pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                    chgDemFlowId = flowDetailVect.get(8).toString();
                }
            }

            String custName = "";
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            }

            Calendar calendar = Calendar.getInstance();
            Date frDate;
            if (ymmd.parse(fromDt).before(ymmd.parse(openingDt))) {
                fromDt = openingDt;
            }
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
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";
            ArrayList datesFrom = new ArrayList();
            //if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
            unionAllTableList = em.createNativeQuery("select duedt  as fromDt from emidetails where  acno = '" + acNo + "' "
                    + " and duedt BETWEEN '" + fromDt + "' and '" + toDt + "' "
                    + " order by  fromDt").getResultList();
            //}

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
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
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
            ArrayList dateadd = new ArrayList();;
            String preDt = "";
            for (int i = 0; i < datesFrom.size(); i++) {
                if (i != datesFrom.size() - 1) {
                    int monthDiff = CbsUtil.monthDiff(ymd.parse(datesFrom.get(i).toString()), ymd.parse(datesFrom.get(i + 1).toString()));
                    if (monthDiff > 0) {
                        for (int j = 0; j < monthDiff; j++) {
                            if (j == 0) {
                                Date dt1 = CbsUtil.getLastDateOfMonth(ymd.parse(datesFrom.get(i).toString()));
                                if (dt1.after(ymd.parse(datesFrom.get(i).toString()))) {
                                    String dt2 = CbsUtil.dateAdd(ymmd.format(dt1), 1);
                                    dateadd.add(dt2);
                                    preDt = dt2;
                                }
                            } else {
//                                Date dt1 = CbsUtil.getLastDateOfMonth(ymd.parse(datesFrom.get(i).toString()));
                                if (ymmd.parse(preDt).after(ymd.parse(datesFrom.get(i).toString()))) {
                                    String dt2 = CbsUtil.monthAdd(preDt, 1);
                                    dateadd.add(dt2);
                                    preDt = dt2;
                                } else {
                                    String dt2 = CbsUtil.monthAdd(preDt, 1);
//                                    dateadd.add(dt2);
                                    preDt = dt2;
                                }
                            }
                        }
                    } else {
                        Date dt1 = CbsUtil.getLastDateOfMonth(ymd.parse(datesFrom.get(i).toString()));
                        if (dt1.after(ymd.parse(datesFrom.get(i).toString()))) {
                            String dt2 = CbsUtil.dateAdd(ymmd.format(dt1), 1);
                            if (!ymmd.parse(dt2).after(dt1)) {
                                dateadd.add(dt2);
                                preDt = dt2;
                            }
                        }
                    }
                }
            }
            if (!dateadd.isEmpty()) {
                for (int i = 0; i < dateadd.size(); i++) {
//                    String dt11 = ymd.format(ymmd.parse(dateadd.get(i).toString()));
                    datesFrom.add(ymd.format(ymmd.parse(dateadd.get(i).toString())));
                }
            }

            Map<Date, Date> map = new HashMap<Date, Date>();
            for (int i = 0; i < datesFrom.size(); i++) {
                Date object = ymd.parse(datesFrom.get(i).toString());
                if (!map.containsKey(object)) { //Present
//                    System.out.println("Hi"+object);
//                } else { //Not Present
                    map.put(object, object);
                }
            }

            datesFrom = new ArrayList();
            Set<Entry<Date, Date>> set = map.entrySet();
            Iterator<Entry<Date, Date>> it1 = set.iterator();
            while (it1.hasNext()) {
                Entry<Date, Date> entry = it1.next();
                datesFrom.add(ymd.format(entry.getKey()));
//                System.out.println("CustomerId-->" + entry.getKey() + "::Amount-->" + entry.getValue());
            }

            Collections.sort(datesFrom);
            for (Iterator i = datesFrom.iterator(); i.hasNext();) {
                String currentDate = i.next().toString();
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
            double totalInt = 0.0f, preInt = 0d;
            for (int i = 0; i < b.length - 1; i++) {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();
                double emiAmt = 0d;
                /* Making the outstading according to the Calculation Level
                 IF Calculation Level is Sanction or Limit==================*/
                /*1. Oustanding */
                if (i == 0) {
                    if (calcLevel.equals("L")) {
                        outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate));
                    } else if (calcLevel.equals("S")) {
                        outSt = sanctionLimit * -1;
                    }
                } else {
//                    if(outSt==0){
//                        if (calcLevel.equals("L")) {
//                            outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate));
//                        } else if (calcLevel.equals("S")) {
//                            outSt = sanctionLimit * -1;
//                        }
//                    }
                    List emiList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where  acno = '" + acNo + "' "
                            + " and duedt ='" + fDate + "' ").getResultList();
//                    System.out.println("select ifnull(installamt,0) from emidetails where  acno = '" + acNo + "' "
//                            + " and duedt ='" + fDate + "' ");
                    if (!emiList.isEmpty()) {
                        emiAmt = Double.parseDouble(((Vector) emiList.get(0)).get(0).toString());
                    }

                    Date firstDtOfMonth = ymmd.parse(CbsUtil.getFirstDateOfGivenDate(ymd.parse(fDate)));
//                    System.out.println("Emi Amt:"+emiAmt+"FirstDate:"+firstDtOfMonth+";"+fDate);
                    if (firstDtOfMonth.equals(ymd.parse(fDate))) {
                        outSt = outSt + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0) + (outSt < 0 ? emiAmt : 0);
                        preInt = 0;
                    } else {
                        outSt = outSt + (outSt < 0 ? emiAmt : 0);
                    }
//                    if (!emiList.isEmpty()) {
//                        emiStartFlag = "Y";
//                        emiAmt = Double.parseDouble(((Vector) emiList.get(0)).get(0).toString());
//                        Date firstDtOfMonth = ymmd.parse(CbsUtil.getFirstDateOfGivenDate(ymd.parse(fDate)));
////                    System.out.println("Emi Amt:"+emiAmt+"FirstDate:"+firstDtOfMonth+";"+fDate);
//                        if (firstDtOfMonth.equals(ymd.parse(fDate))) {
//                            outSt = outSt + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0) + (outSt < 0 ? emiAmt : 0);
//                            preInt = 0;
//                        } else {
//                            outSt = outSt + (outSt < 0 ? emiAmt : 0);
//                        }
//                    } else {
//                        if (emiStartFlag.equalsIgnoreCase("Y")) {
//                            outSt = outSt + (outSt < 0 ? emiAmt : 0);
//                        } else {
//                            if (calcLevel.equals("L")) {
//                                outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate)) + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0);
//                            } else if (calcLevel.equals("S")) {
//                                outSt = sanctionLimit * -1;
//                            }
//                        }
//                    }
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
                    String roiGet = loanInterestCalculationBean.getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                    if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                        throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + outSt);
                    } else {
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                }
                /*3. No. of days between From Date and To Date*/

                double dayDiff = (double) CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));

                /* In each slab, No. of days is increasing by 1  */
                dayDiff = dayDiff + 1;

                /*4. Interest Calculation*/
                double interest = (roiFromUserFlag.equalsIgnoreCase("Y") ? roiFromUser : rateOfInt) * dayDiff * outSt / 36500;
                preInt = preInt + interest;

                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                double wrongIntAmt = 0;
//                List wrongPostedIntList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
//                        + " union All "
//                        + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by acno").getResultList();
//                if (wrongPostedIntList.isEmpty()) {
////                    throw new ApplicationException("No tranction done in this account");
//                    wrongIntAmt = 0;
//                } else {
//                    for (int j = 0; j < wrongPostedIntList.size(); j++) {
//                        Vector wrongPostedIntVect = (Vector) wrongPostedIntList.get(j);
//                        wrongIntAmt = Double.parseDouble(wrongPostedIntVect.get(0).toString()) + wrongIntAmt;
////                        if (wrongIntAmt > 0) {
////                            wrongIntAmt = 0;
////                        }
//                    }
//                }
                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                b[i][2] = formatter.format(outSt);
                b[i][3] = Double.toString(rateOfInt);
                b[i][4] = Double.toString(dayDiff);
                b[i][5] = formatter.format(interest);
                b[i][6] = intTableCode;
                totalInt = totalInt + interest - wrongIntAmt;
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
                itProduct.setRoi((roiFromUserFlag.equalsIgnoreCase("Y") ? roiFromUser : rateOfInt));
                itProduct.setProduct(outSt);
                itProduct.setClosingBal(dayDiff);
                itProduct.setTotalInt(interest);
                itProduct.setIntTableCode(intTableCode);
                itProduct.setPriAmt(emiAmt);

                intProductDetail.add(itProduct);
                itProduct = new LoanIntCalcList();
            }

            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalInt))));
            it.setRoi((roiFromUserFlag.equalsIgnoreCase("Y") ? roiFromUser : rateOfInt));
            it.setIntTableCode(intTableCode);
            intDetails.add(it);
            la.setLoanIntDetails(intDetails);
            la.setLoanProductIntDetails(intProductDetail);
            lml.add(la);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return lml;
    }

    public List<LoanMultiList> accWiseLoanProjectedIntCalc(String fromDt, String toDt, String acNo, String brnCode, String roiFromUserFlag, double roiFromUser) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        String roi;
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
            double penalRoi;
            double sanctionLimit = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            String acNature = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            double subsidyAmt = 0;
            String subsidyExpDt = "", emiStartFlag = "N", openingDt = fromDt;

            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank calculation method doesn't exist in Parameterinfo");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }

            acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,''),c.OpeningDt  from cbs_loan_acc_mast_sec A, loan_appparameter B, accountmaster c where A.acno = c.acno and A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
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
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(19).toString();
                    intCompTillDt = SecDetailsVect.get(20).toString();
                    nextIntCalcDt = SecDetailsVect.get(21).toString();
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                    openingDt = SecDetailsVect.get(24).toString();
                }
            }
            List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
            String eiFlowId = null;
            String prinDemFlowId = null;
            String disbFlowId;
            String colFlowId = null;
            String intDemFlowId = null;
            String penalIntDemFlowId = null;
            String overdueIntFlowId;
            String pastDueColNPAFlowId = null;
            String chgDemFlowId = null;
            double overFlowAmt = 0;

            if (flowDetailList.isEmpty()) {
                throw new ApplicationException("Flow Id does not exist in SchemeMaster..");
            } else {
                for (int j = 0; j < flowDetailList.size(); j++) {
                    Vector flowDetailVect = (Vector) flowDetailList.get(j);
                    eiFlowId = flowDetailVect.get(0).toString();
                    prinDemFlowId = flowDetailVect.get(1).toString();
                    disbFlowId = flowDetailVect.get(2).toString();
                    colFlowId = flowDetailVect.get(3).toString();
                    intDemFlowId = flowDetailVect.get(4).toString();
                    penalIntDemFlowId = flowDetailVect.get(5).toString();
                    overdueIntFlowId = flowDetailVect.get(6).toString();
                    pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                    chgDemFlowId = flowDetailVect.get(8).toString();
                }
            }

            String custName = "";
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            }

            Calendar calendar = Calendar.getInstance();
            Date frDate;
            if (ymmd.parse(fromDt).before(ymmd.parse(openingDt))) {
                fromDt = openingDt;
            }
            if (ymmd.format(ymd.parse(intCalcUptoDt)).equalsIgnoreCase("19000101")) {
                frDate = ymmd.parse(fromDt);
                calendar.setTime(frDate);
            } else {
                frDate = ymmd.parse(fromDt);
                calendar.setTime(frDate);
            }

            fromDt = ymmd.format(calendar.getTime());
            Date nxtCalDt;
            String nextIntCalculationDt = ymd.format(ymd.parse(nextIntCalcDt));
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
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";
            ArrayList datesFrom = new ArrayList();
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
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();

                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescSimple + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + " union "
                            + " select duedt  as fromDt from emidetails where  acno = '" + acNo + "' "
                            + " and duedt BETWEEN '" + nextIntCalculationDt + "' and '" + toDt + "' "
                            + //                            " union All " +
                            //                            " select DMD_DT as fromDt from CBS_LA_ADDITIONAL_TR_DETAILS where  acno = '" + acNo + "' and DMD_DT  BETWEEN '" + fromDt + "' and '" + toDt + "'  and DMD_FLOW_ID in('" + colFlowId + "','" + pastDueColNPAFlowId + "') and INT_ROUTING_FLAG = 'L' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'  group by DMD_DT " +
                            " order by  fromDt").getResultList();
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
                        npaQuery = " UNION SELECT VALUEDT FROM npa_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT ";
                    } else {
                        npaQuery = " UNION select VALUEDT as fromDt from npa_recon where  acno = '" + acNo + "' and valuedt BETWEEN '" + fromDt + "' and '" + toDt + "' and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT ";
                    }
                } else {
                    npaQuery = "";
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM ca_recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT from loan_recon where  acno =  '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    unionAllTableList = em.createNativeQuery("select VALUEDT as fromDt from loan_recon where  acno = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "'  and auth = 'Y' and tranDesc in (" + tranDescComp + ") group by VALUEDT "
                            + npaQuery
                            + " UNION "
                            + " select EFF_FRM_DT as fromDt from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT "
                            + " union "
                            + " select duedt  as fromDt from emidetails where  acno = '" + acNo + "' "
                            + " and duedt BETWEEN '" + nextIntCalculationDt + "' and '" + toDt + "' "
                            + //                            " union All " +
                            //                            " select DMD_DT as fromDt from CBS_LA_ADDITIONAL_TR_DETAILS where  acno = '" + acNo + "' and DMD_DT  BETWEEN '" + fromDt + "' and '" + toDt + "'  and DMD_FLOW_ID in('" + colFlowId + "','" + pastDueColNPAFlowId + "') and INT_ROUTING_FLAG = 'L' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'  group by DMD_DT" +
                            " order by  fromDt").getResultList();

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
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                getAdhocDateChangeSlab(acNo, a[i][0], a[i][1], datesFrom);
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

            for (Iterator i = datesFrom.iterator(); i.hasNext();) {
                String currentDate = i.next().toString();
            }
            Collections.sort(datesFrom);
            ArrayList dateadd = new ArrayList();
            String preDt = null;
            for (int i = 0; i < datesFrom.size(); i++) {
                if (i != datesFrom.size() - 1) {
                    if (ymd.parse(datesFrom.get(i).toString()).after(ymd.parse(nextIntCalculationDt))) {
                        int monthDiff = CbsUtil.monthDiff(ymd.parse(datesFrom.get(i).toString()), ymd.parse(datesFrom.get(i + 1).toString()));
                        if (monthDiff > 0) {
                            for (int j = 0; j < monthDiff; j++) {
                                if (j == 0) {
                                    Date dt1 = CbsUtil.getLastDateOfMonth(ymd.parse(datesFrom.get(i).toString()));
                                    if (dt1.after(ymd.parse(datesFrom.get(i).toString()))) {
                                        String dt2 = CbsUtil.dateAdd(ymmd.format(dt1), 1);
                                        dateadd.add(dt2);
                                        preDt = dt2;
                                    }
                                } else {
//                                Date dt1 = CbsUtil.getLastDateOfMonth(ymd.parse(datesFrom.get(i).toString()));
                                    if (ymmd.parse(preDt).after(ymd.parse(datesFrom.get(i).toString()))) {
                                        String dt2 = CbsUtil.monthAdd(preDt, 1);
                                        dateadd.add(dt2);
                                        preDt = dt2;
                                    } else {
                                        String dt2 = CbsUtil.monthAdd(preDt, 1);
//                                    dateadd.add(dt2);
                                        preDt = dt2;
                                    }
                                }
                            }
                        } else {
                            Date dt1 = CbsUtil.getLastDateOfMonth(ymd.parse(datesFrom.get(i).toString()));
                            if (dt1.after(ymd.parse(datesFrom.get(i).toString()))) {
                                String dt2 = CbsUtil.dateAdd(ymmd.format(dt1), 1);
                                if (!ymmd.parse(dt2).after(dt1)) {
                                    dateadd.add(dt2);
                                    preDt = dt2;
                                }
                            }
                        }
                    }
                }
            }
            if (!dateadd.isEmpty()) {
                for (int i = 0; i < dateadd.size(); i++) {
//                    String dt11 = ymd.format(ymmd.parse(dateadd.get(i).toString()));
                    datesFrom.add(ymd.format(ymmd.parse(dateadd.get(i).toString())));
                }
            }
            Map<Date, Date> map = new HashMap<Date, Date>();
            for (int i = 0; i < datesFrom.size(); i++) {
                Date object = ymd.parse(datesFrom.get(i).toString());
                if (!map.containsKey(object)) { //Present
//                    System.out.println("Hi"+object);
//                } else { //Not Present
                    map.put(object, object);
                }
            }

            datesFrom = new ArrayList();
            Set<Entry<Date, Date>> set = map.entrySet();
            Iterator<Entry<Date, Date>> it1 = set.iterator();
            while (it1.hasNext()) {
                Entry<Date, Date> entry = it1.next();
                datesFrom.add(ymd.format(entry.getKey()));
//                System.out.println("CustomerId-->" + entry.getKey() + "::Amount-->" + entry.getValue());
            }
            Collections.sort(datesFrom);
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
            double totalInt = 0.0f, preInt = 0d;
            for (int i = 0; i < b.length - 1; i++) {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();
                double emiAmt = 0d;
                /* Making the outstading according to the Calculation Level
                 IF Calculation Level is Sanction or Limit==================*/
                /*1. Oustanding */
                if (i == 0) {
                    if (calcLevel.equals("L")) {
                        outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate)) + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0);
                    } else if (calcLevel.equals("S")) {
                        outSt = sanctionLimit * -1;
                    }
                } else {
//                    if(outSt==0){
//                        if (calcLevel.equals("L")) {
//                            outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate)) + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0);
//                        } else if (calcLevel.equals("S")) {
//                            outSt = sanctionLimit * -1;
//                        } 
//                    }
                    List emiList = em.createNativeQuery("select ifnull(installamt,0) from emidetails where  acno = '" + acNo + "' "
                            + " and duedt ='" + fDate + "' ").getResultList();
//                    System.out.println("select ifnull(installamt,0) from emidetails where  acno = '" + acNo + "' "
//                            + " and duedt ='" + fDate + "' ");
                    if (!emiList.isEmpty()) {
                        emiStartFlag = "Y";
                        emiAmt = Double.parseDouble(((Vector) emiList.get(0)).get(0).toString());
                        Date firstDtOfMonth = ymmd.parse(CbsUtil.getFirstDateOfGivenDate(ymd.parse(fDate)));
//                    System.out.println("Emi Amt:"+emiAmt+"FirstDate:"+firstDtOfMonth+";"+fDate);
                        if (firstDtOfMonth.equals(ymd.parse(fDate))) {
                            outSt = outSt + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0) + (outSt < 0 ? emiAmt : 0);
                            preInt = 0;
                        } else {
                            outSt = outSt + (outSt < 0 ? emiAmt : 0);
                        }
                    } else {
                        if (emiStartFlag.equalsIgnoreCase("Y")) {
                            outSt = outSt + (outSt < 0 ? emiAmt : 0);
                        } else {
                            if (calcLevel.equals("L")) {
                                outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate)) + (intAppFreq.equalsIgnoreCase("C") ? preInt : 0);
                            } else if (calcLevel.equals("S")) {
                                outSt = sanctionLimit * -1;
                            }
                        }
                    }
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
                    String roiGet = loanInterestCalculationBean.getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                    if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                        throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + outSt);
                    } else {
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                }
                /*3. No. of days between From Date and To Date*/

                double dayDiff = (double) CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));

                /* In each slab, No. of days is increasing by 1  */
                dayDiff = dayDiff + 1;

                /*4. Interest Calculation*/
                double interest = (roiFromUserFlag.equalsIgnoreCase("Y") ? roiFromUser : rateOfInt) * dayDiff * outSt / 36500;
                preInt = preInt + interest;
                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                double wrongIntAmt = 0;
                List wrongPostedIntList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by  acno "
                        + " union All "
                        + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND valuedt BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3,4) group by acno").getResultList();
                if (wrongPostedIntList.isEmpty()) {
//                    throw new ApplicationException("No tranction done in this account");
                    wrongIntAmt = 0;
                } else {
                    for (int j = 0; j < wrongPostedIntList.size(); j++) {
                        Vector wrongPostedIntVect = (Vector) wrongPostedIntList.get(j);
                        wrongIntAmt = Double.parseDouble(wrongPostedIntVect.get(0).toString()) + wrongIntAmt;
//                        if (wrongIntAmt > 0) {
//                            wrongIntAmt = 0;
//                        }
                    }
                }
                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                b[i][2] = formatter.format(outSt);
                b[i][3] = Double.toString(rateOfInt);
                b[i][4] = Double.toString(dayDiff);
                b[i][5] = formatter.format(interest);
                b[i][6] = intTableCode;
                totalInt = totalInt + interest - wrongIntAmt;
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
                itProduct.setRoi((roiFromUserFlag.equalsIgnoreCase("Y") ? roiFromUser : rateOfInt));
                itProduct.setProduct(outSt);
                itProduct.setClosingBal(dayDiff);
                itProduct.setTotalInt(Double.parseDouble(formatter.format(interest)));
                itProduct.setIntTableCode(intTableCode);
                itProduct.setPriAmt(emiAmt);
                intProductDetail.add(itProduct);
                itProduct = new LoanIntCalcList();
                /* 
                 * AdHoc Interest ROI 
                 */
                double adHocRoi = 0;
                double adHocInt = 0;
                double odLimit = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
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
                        if (adHocInt != 0) {
                            itProduct.setAcNo(acNo);
                            itProduct.setCustName(custName);
                            itProduct.setFirstDt(fDate);
                            itProduct.setLastDt(tDate);
                            itProduct.setRoi(adHocRoi);
                            itProduct.setProduct((outSt - odLimit * -1) * dayDiff);
                            itProduct.setClosingBal(dayDiff);
                            itProduct.setTotalInt(Double.parseDouble(formatter.format(adHocInt)));
                            itProduct.setIntTableCode("Adhoc Interest");

                            intProductDetail.add(itProduct);
                            product = product + outSt;
                        }
                    }
                }
            }

            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double((totalInt)))));
            it.setRoi((roiFromUserFlag.equalsIgnoreCase("Y") ? roiFromUser : rateOfInt));
            it.setIntTableCode(intTableCode);
            intDetails.add(it);
            la.setLoanIntDetails(intDetails);
            la.setLoanProductIntDetails(intProductDetail);
            lml.add(la);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return lml;
    }

    public List<LoanMultiList> accWiseTheftIntProductCalc(String flag, String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        List<LoanMultiList> lml = new ArrayList<LoanMultiList>();
        LoanMultiList la = new LoanMultiList();
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
        String roi;
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
            double penalRoi;
            double sanctionLimit = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            String acNature = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            double subsidyAmt = 0;
            String subsidyExpDt = "";

            /*Getting the Calculation Method*/
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/
            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank calculation method doesn't exist in Parameterinfo");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }

            acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.intdeposit, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT from cbs_loan_acc_mast_sec A, accountmaster B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
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
//                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
//                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(17).toString();
                    intCompTillDt = SecDetailsVect.get(18).toString();
                    nextIntCalcDt = SecDetailsVect.get(19).toString();
//                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
//                    subsidyExpDt = SecDetailsVect.get(23).toString(); 
                }
            }
            List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
            String eiFlowId = null;
            String prinDemFlowId = null;
            String disbFlowId;
            String colFlowId = null;
            String intDemFlowId = null;
            String penalIntDemFlowId = null;
            String overdueIntFlowId;
            String pastDueColNPAFlowId = null;
            String chgDemFlowId = null;
            double overFlowAmt = 0;

            if (flowDetailList.isEmpty()) {
                throw new ApplicationException("Flow Id does not exist in SchemeMaster..");
            } else {
                for (int j = 0; j < flowDetailList.size(); j++) {
                    Vector flowDetailVect = (Vector) flowDetailList.get(j);
                    eiFlowId = flowDetailVect.get(0).toString();
                    prinDemFlowId = flowDetailVect.get(1).toString();
                    disbFlowId = flowDetailVect.get(2).toString();
                    colFlowId = flowDetailVect.get(3).toString();
                    intDemFlowId = flowDetailVect.get(4).toString();
                    penalIntDemFlowId = flowDetailVect.get(5).toString();
                    overdueIntFlowId = flowDetailVect.get(6).toString();
                    pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                    chgDemFlowId = flowDetailVect.get(8).toString();
                }
            }

            String custName = "";
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
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
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
                }

            } else if (intAppFreq.equalsIgnoreCase("C")) {
                /* COMPOUNDING ====================================================*/
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    unionAllTableList = em.createNativeQuery("SELECT VALUEDT FROM recon WHERE  ACNO = '" + acNo + "' and valuedt  BETWEEN '" + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                            + " UNION "
                            + " select EFF_FRM_DT from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '" + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();
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

            for (Iterator i = datesFrom.iterator(); i.hasNext();) {
                String currentDate = i.next().toString();
            }

            List bl = new ArrayList();
            for (int j = 0; j < datesFrom.size(); j++) {
                String dt = datesFrom.get(j).toString();
                double bal = common.getBalanceOnDate(acNo, ymmd.format(ymd.parse(dt)));
                bl.add(bal);
            }
            Collections.sort(bl);
//            System.out.print(bl);
            double minFdAmt = Double.parseDouble(bl.get(0).toString());

            List fdroiList = em.createNativeQuery("select Interest_rate,Applicable_Date,ST,SC From td_slab where fromDays <= 365.0 and toDays >=365.0 and  fromAmount <= " + minFdAmt + " and toAmount >= " + minFdAmt + " and Applicable_Date in(select max(applicable_Date) from td_slab where applicable_date<='" + fromDt + "')").getResultList();
            Vector vtr = (Vector) fdroiList.get(0);
            fdRoiGet = Double.parseDouble(vtr.get(0).toString());

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
            for (int i = 0; i < b.length - 1; i++) {
                LoanIntCalcList itProduct = new LoanIntCalcList();
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();
                     
                /* Making the outstading according to the Calculation Level
                 IF Calculation Level is Sanction or Limit==================*/
                /*1. Oustanding */

                //outSt = Double.parseDouble(outStandingAsOnDate(acNo, fDate));
                outSt = common.getBalanceOnDate(acNo, fDate);
                if (outSt > 0) {

                    String roiGet = acOpenFacadeRemote.getROI(schemeCode, outSt, fDate);

                    if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                        throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + outSt);
                    } else {
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                }
                /*3. No. of days between From Date and To Date*/

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
                intProductDetail.add(itProduct);
                //  itProduct = new LoanIntCalcList();
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
            it.setProduct(product);
            it.setTotalInt(totalInt);
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);
            intDetails.add(it);
            la.setLoanIntDetails(intDetails);
            la.setLoanProductIntDetails(intProductDetail);
            lml.add(la);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return lml;
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
            List subsidyExpDateList = em.createNativeQuery("select ifnull(SUBSIDYEXPDT,'') from loan_appparameter where acno = '" + acNo + "' and SUBSIDYEXPDT between '" + fromDt + "' and  '" + toDate + "'").getResultList();
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

    /**
     * ***********========GETTING THE OUTSTANDING AMOUNT TILL
     * DATE========************
     */
    public String outStandingAsOnDate(String acNo, String tillDate) throws ApplicationException {
        String result = "";
        String tranDescSimple = "", tranDescComp = "", npaQuery = "";
        try {
            String acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT ACNO, SCHEME_CODE, INTEREST_TABLE_CODE, MORATORIUM_PD, ACC_PREF_DR, ACC_PREF_CR, RATE_CODE, DISB_TYPE, CALC_METHOD, CALC_ON, INT_APP_FREQ, CALC_LEVEL, COMPOUND_FREQ, PEGG_FREQ, LOAN_PD_MONTH, LOAN_PD_DAY  from cbs_loan_acc_mast_sec where acno ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String acno = (String) SecDetailsVect.get(0);
                    String schemeCode = (String) SecDetailsVect.get(1);
                    String intTableCode = (String) SecDetailsVect.get(2);
                    int moratoriumPd = Integer.parseInt(SecDetailsVect.get(3).toString());
                    double accPrefDr = Double.parseDouble(SecDetailsVect.get(4).toString());
                    double accPrefCr = Double.parseDouble(SecDetailsVect.get(5).toString());
                    String rateCode = (String) SecDetailsVect.get(6);
                    String disbType = (String) SecDetailsVect.get(7);
                    String calcMethod = (String) SecDetailsVect.get(8);
                    String calcOn = (String) SecDetailsVect.get(9);
                    String intAppFreq = (String) SecDetailsVect.get(10);
                    String calcLevel = (String) SecDetailsVect.get(11);
                    String compoundFreq = (String) SecDetailsVect.get(12);
                    //int peggFreq = Integer.parseInt(SecDetailsVect.get(13).toString());
                    int peggFreq = (SecDetailsVect.get(13) == null ? 0 : Integer.parseInt(SecDetailsVect.get(13).toString()));

                    int loanPdMonth = Integer.parseInt(SecDetailsVect.get(14).toString());
                    int loanPdDay = Integer.parseInt(SecDetailsVect.get(15).toString());

                    List outStDrAmtList = null;
                    List outStCrAmtList;
                    Vector outStDrAmtVect;
                    Vector outStCrAmtVect;
                    double drAmt = 0;
                    double crAmt = 0;
                    double outSt = 0;
                    List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
                    String eiFlowId = null;
                    String prinDemFlowId = null;
                    String disbFlowId;
                    String colFlowId = null;
                    String intDemFlowId = null;
                    String penalIntDemFlowId = null;
                    String overdueIntFlowId;
                    String pastDueColNPAFlowId = null;
                    String chgDemFlowId = null;

                    if (flowDetailList.isEmpty()) {
                        throw new ApplicationException("Flow Id does not exist in SchemeMaster");
                    } else {
                        for (int j = 0; j < flowDetailList.size(); j++) {
                            Vector flowDetailVect = (Vector) flowDetailList.get(j);
                            eiFlowId = flowDetailVect.get(0).toString();
                            prinDemFlowId = flowDetailVect.get(1).toString();
                            disbFlowId = flowDetailVect.get(2).toString();
                            colFlowId = flowDetailVect.get(3).toString();
                            intDemFlowId = flowDetailVect.get(4).toString();
                            penalIntDemFlowId = flowDetailVect.get(5).toString();
                            overdueIntFlowId = flowDetailVect.get(6).toString();
                            pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                            chgDemFlowId = flowDetailVect.get(8).toString();

                        }
                    }
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
                        List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
                        if (npaList.isEmpty()) {
                            if ((acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) || (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                                npaQuery = "  union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' group by acno ";
                            } else {
                                npaQuery = "  union All SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT <= '" + tillDate + "' AND AUTH = 'Y' and tranDesc in (" + tranDescSimple + ") group by acno ";
                            }
                        } else {
                            npaQuery = "";
                        }
                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                    + " ca_recon WHERE ACNO = '" + acNo + "' AND valuedt <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                    + npaQuery).getResultList();

                            if (outStDrAmtList.isEmpty()) {
//                                throw new ApplicationException("No tranction done in this account");
                                drAmt = 0;
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
                                    + " loan_recon WHERE ACNO = '" + acNo + "' AND valuedt <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                    + npaQuery).getResultList();

                            if (outStDrAmtList.isEmpty()) {
//                                throw new ApplicationException("No tranction done in this account");
                                drAmt = 0;
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
                            outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                    + " loan_recon WHERE ACNO = '" + acNo + "' AND DT <= '" + tillDate + "' AND AUTH = 'Y' and "
                                    + " tranDesc in (" + tranDescSimple + ") group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
//                                throw new ApplicationException("No tranction done in this account");
                                drAmt = 0;
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
                        List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
                        if (npaList.isEmpty()) {
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
                                    + " ca_recon WHERE ACNO = '" + acNo + "' AND valuedt <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                    + npaQuery).getResultList();

                            if (outStDrAmtList.isEmpty()) {
//                                throw new ApplicationException("No tranction done in this account");
                                drAmt = 0;
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
                                    + " loan_recon WHERE ACNO = '" + acNo + "' AND valuedt <= '" + tillDate + "' AND AUTH = 'Y' group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
//                                throw new ApplicationException("No tranction done in this account");
                                drAmt = 0;
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
                            outStDrAmtList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM "
                                    + " loan_recon WHERE ACNO = '" + acNo + "' AND valuedt <= '" + tillDate + "' AND AUTH = 'Y'  group by  acno "
                                    + npaQuery).getResultList();
                            if (outStDrAmtList.isEmpty()) {
//                                throw new ApplicationException("No tranction done in this account");
                                drAmt = 0;
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
                    outSt = outSt;
                    result = Double.toString(outSt);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return result;
    }

    public ArrayList getRoiChangeSlab(String intTableCode, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException {
        try {
            /*=======Getting the ROI Change Date in Previous i Position=======================*/
            List laIntCodeMastList = em.createNativeQuery("select START_DATE,INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
            List laIntCodeMastHistList = em.createNativeQuery("select START_DATE,INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                String stDt = laIntCodeMastVect.get(0).toString();
                String intMastTblCod = laIntCodeMastVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select start_date from cbs_loan_interest_master where code = '" + intMastTblCod + "' and  START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select start_date from cbs_loan_interest_master_history where code = '" + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
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

                List laIntMastList = em.createNativeQuery("select start_date from cbs_loan_interest_master where code = '" + intMastTblCod + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select start_date from cbs_loan_interest_master_history where code = '" + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
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

    /*  C   = Calender Year (Jan-Dec)
     *  CF  = Calender Year Financial Year (Apr-Mar)
     *  A   = Anniversary
     */
    public String getNextToDt(String brnCode, String acNo, String fromDt, String freq, int moratoriumPd) throws ApplicationException {
        String result = null;
        String bankCalcMethod;
        String frDt = fromDt;
        String toDt = "";
        try {
            /* C=Calender Method     =  (Jan-Dec/Apr-Mar)
             *                          15-Jan To 31-Jan, 01-Feb To 28/29-Feb (Monthly)
             * A= Anniversary Method =  (15-Jan to 15-Feb(Monthly)) "*/

            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                return result = "Bank Calculation Method doesn't Exists in parameterinfo.";
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
                    //  System.out.println("HalfYear:" + halfyear);
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

    public LoanIntCalcList accWiseLoanIntCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException {
        double product = 0;
        double closingBal = 0;
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        String roi = "";
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
            double penalRoi;
            double sanctionLimit = 0;
            double outSt = 0;
            String firstDisbDt = null;
            String bankCalcMethod;
            String intCalcUptoDt = null;
            String intCompTillDt;
            String nextIntCalcDt = null;
            String acNature = null;
            List unionAllTableList = null;
            String firstDt = null;
            String lastDt = null;
            double subsidyAmt = 0;
            String subsidyExpDt = "", intCalcBasedOnSecurity = "N", orderByParameter = "E";


            List bankCalcMethodList = em.createNativeQuery("select IFNULL(CALC_MTHD,'') from parameterinfo where BrnCode = '" + brnCode + "'").getResultList();
            if (bankCalcMethodList.isEmpty()) {
                throw new ApplicationException("Bank calculation method does not exist in ParameterInfo");
            } else {
                Vector bankCalcMethodVect = (Vector) bankCalcMethodList.get(0);
                bankCalcMethod = bankCalcMethodVect.get(0).toString();
                if (bankCalcMethod.equals("")) {
                    bankCalcMethod = "CF";
                }
            }
            double minAdHocIntValue = 0;
            List minAdHocIntValueList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'ADHOC_LOAN_MIN_INT_VALUE'").getResultList();
            if (!minAdHocIntValueList.isEmpty()) {
                Vector minAdHocIntValueVect = (Vector) minAdHocIntValueList.get(0);
                minAdHocIntValue = Double.parseDouble(minAdHocIntValueVect.get(0).toString());
            }

//            List paramList = em.createNativeQuery("select code from cbs_parameterinfo where NAME = 'SEC_ROI_CALC'").getResultList();
//            String orderBy = " Entrydate ";
//            if (!paramList.isEmpty()) {
//                Vector orderVect = (Vector) paramList.get(0);
//                if (orderVect.get(0).toString().equalsIgnoreCase("R")) { //AppRoi
//                    orderBy = " AppRoi ";
//                } else if (orderVect.get(0).toString().equalsIgnoreCase("DR")) { //AppRoi desc
//                    orderBy = " AppRoi desc ";
//                } else if (orderVect.get(0).toString().equalsIgnoreCase("DT")) { //Entrydate
//                    orderBy = " Entrydate ";
//                }
//            }

            acNature = ftsPostRemote.getAccountNature(acNo);
            List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE, A.INTEREST_TABLE_CODE, A.MORATORIUM_PD, A.ACC_PREF_DR, A.ACC_PREF_CR, A.RATE_CODE, A.DISB_TYPE, A.CALC_METHOD, A.CALC_ON, A.INT_APP_FREQ, A.CALC_LEVEL, A.COMPOUND_FREQ, A.PEGG_FREQ, A.LOAN_PD_MONTH, A.LOAN_PD_DAY, B.ROI, B.PENALROI, B.SANCTIONLIMIT, IFNULL(A.INT_CALC_UPTO_DT,0) as INT_CALC_UPTO_DT, IFNULL(A.INT_COMP_TILL_DT,0) as INT_COMP_TILL_DT, IFNULL(A.NEXT_INT_CALC_DT,0) as NEXT_INT_CALC_DT, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,''), ifnull(sg.TURN_OVER_DETAIL_FLAG,'N'), ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E')  from cbs_loan_acc_mast_sec A, loan_appparameter B, cbs_scheme_general_scheme_parameter_master sg  where A.ACNO = B.ACNO AND A.SCHEME_CODE = sg.scheme_code AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in Secondary Details table of loan");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    String acno = (String) SecDetailsVect.get(0);
//                    System.out.println("acno:" + acno);
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
                    penalRoi = Double.parseDouble(SecDetailsVect.get(17).toString());
                    sanctionLimit = Double.parseDouble(SecDetailsVect.get(18).toString());
                    intCalcUptoDt = SecDetailsVect.get(19).toString();
                    intCompTillDt = SecDetailsVect.get(20).toString();
                    nextIntCalcDt = SecDetailsVect.get(21).toString();
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(22).toString());
                    subsidyExpDt = SecDetailsVect.get(23).toString();
                    intCalcBasedOnSecurity = SecDetailsVect.get(24).toString();
                    orderByParameter = SecDetailsVect.get(25).toString();
                }
            }

            List flowDetailList = em.createNativeQuery("select EI_FLOW_ID, PRINCIPAL_FLOW_ID, DISBURSEMENT_FLOW_ID, COLLECTION_FLOW_ID, INT_DEMAND_FLOW_ID, PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();
            String eiFlowId = null;
            String prinDemFlowId = null;
            String disbFlowId;
            String colFlowId = null;
            String intDemFlowId = null;
            String penalIntDemFlowId = null;
            String overdueIntFlowId;
            String pastDueColNPAFlowId = null;
            String chgDemFlowId = null;
            //double overFlowAmt = 0;

            if (flowDetailList.isEmpty()) {
                throw new ApplicationException("Flow Id does not exist in SchemeMaster");
            } else {
                for (int j = 0; j < flowDetailList.size(); j++) {
                    Vector flowDetailVect = (Vector) flowDetailList.get(j);
                    eiFlowId = flowDetailVect.get(0).toString();
                    prinDemFlowId = flowDetailVect.get(1).toString();
                    disbFlowId = flowDetailVect.get(2).toString();
                    colFlowId = flowDetailVect.get(3).toString();
                    intDemFlowId = flowDetailVect.get(4).toString();
                    penalIntDemFlowId = flowDetailVect.get(5).toString();
                    overdueIntFlowId = flowDetailVect.get(6).toString();
                    pastDueColNPAFlowId = flowDetailVect.get(7).toString();
                    chgDemFlowId = flowDetailVect.get(8).toString();
                }
            }

            String custName = null;
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does exist for account number " + acNo);
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
            //** it will discuss with Sogendra Sir in future
            //toDt = ymmd.format(ymmd.parse(nextIntCalcDt));
            String tranDescSimple = "", tranDescComp = "", npaQuery = "";
            ArrayList datesFrom = new ArrayList();
            String[][] b = loanInterestCalculationBean.createFromDtArray(acNo, fromDt, toDt, intAppFreq, acNature, intTableCode, intCalcBasedOnSecurity, "N");
            double totalInt = 0.0f;
            for (int i = 0; i < b.length - 1; i++) {
                String fDate = b[i][0].toString();
                String tDate = b[i][1].toString();

                /* Making the outstading according to the Calculation Level
                 IF Calculation Level is Sanction or Limit==================*/
                /*1. Oustanding */
                if (calcLevel.equals("L")) {
                    //outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate));
                    outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, fDate));
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
                    String roiGet = loanInterestCalculationBean.getRoiLoanAccount(outSt, ymd.format(ymd.parse(fDate)), acNo);
                    if (roiGet.substring(0, 2).equalsIgnoreCase("fa")) {
                        throw new ApplicationException(roiGet.substring(6) + " for account " + acNo + "." + "outstanding:" + outSt);
                    } else {
                        rateOfInt = Double.parseDouble(roiGet);
                    }
                }
                /*3. No. of days between From Date and To Date*/

                double dayDiff = (double) CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                /* In each slab, No. of days is increasing by 1  */
                dayDiff = dayDiff + 1;

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
                    double[] interestArr = loanInterestCalculationBean.loanIntAsPerSecurity(acNo, ymd.format(ymd.parse(fDate)), ymd.format(ymd.parse(tDate)), dayDiff, outSt, ymd.format(ymd.parse(fDate)), orderBy);
                    interest = interestArr[0];
                    rateOfInt = interestArr[1];
                } else {
                    interest = Math.round(rateOfInt * dayDiff * outSt / 36500);
                }

                /* 
                 * AdHoc Interest ROI 
                 */
                double adHocRoi = 0;
                double adHocInt = 0;
                double odLimit = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List adHocDateList = em.createNativeQuery("select Adhocapplicabledt, AdhocExpiry, AdhocROI, ODLimit, Adhoclimit, PenalROI from loan_appparameter where acno = '" + acNo + "' and Adhocapplicabledt <= '" + fDate + "'").getResultList();
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
                interest = interest + adHocInt;
                rateOfInt = rateOfInt + adHocRoi;

                /**
                 * *********************Add on for if wrong interest posted
                 * *******************
                 */
                double wrongIntAmt = 0;
                String intUpTo = "";
                String tablename = common.getTableName(acNature);
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    intUpTo = "INT UPTO " + mdy.format(ymmd.parse(toDt));
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    intUpTo = "INT.UP TO " + mdy.format(ymmd.parse(toDt));
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    intUpTo = "INT.UP TO " + mdy.format(ymmd.parse(toDt));
                }
                List wrongPostedIntList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM " + tablename + " WHERE ACNO = '" + acNo + "' AND VALUEDT BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3) and details like '%" + intUpTo + "%'  group by  acno "
                        + " union All "
                        + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM " + tablename + " WHERE ACNO = '" + acNo + "' AND VALUEDT BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3) and details like '%DIFF INT UPTO " + mdy.format(ymmd.parse(toDt)) + "%'  group by  acno "
                        + " union All "
                        + " SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT BETWEEN '" + ymmd.format(ymd.parse(fDate)) + "' AND  '" + ymmd.format(ymd.parse(tDate)) + "' AND AUTH = 'Y' and tranDesc in (3) and details like '%INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + "%' group by acno").getResultList();
                if (wrongPostedIntList.isEmpty()) {
//                    throw new ApplicationException("No tranction done in this account");
                    wrongIntAmt = 0;
                } else {
                    for (int j = 0; j < wrongPostedIntList.size(); j++) {
                        Vector wrongPostedIntVect = (Vector) wrongPostedIntList.get(j);
                        wrongIntAmt = Double.parseDouble(wrongPostedIntVect.get(0).toString()) + wrongIntAmt;
//                        if (wrongIntAmt > 0) {
//                            wrongIntAmt = 0;
//                        }
                    }
                }
                /**
                 * ********************* Add on for if wrong interest posted
                 * *******************
                 */
                b[i][2] = formatter.format(outSt);
                b[i][3] = Double.toString(rateOfInt);
                b[i][4] = Double.toString(dayDiff);
                b[i][5] = formatter.format(interest);
                b[i][6] = intTableCode;
                totalInt = totalInt + interest - wrongIntAmt;
                if (i == 0) {
                    firstDt = fDate;
                } else if (i == b.length - 2) {
                    lastDt = tDate;
                }
                closingBal = Double.parseDouble(b[i][2]);
                roi = b[i][3];
                product = product + Double.parseDouble(b[i][2]) * dayDiff;
            }
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(fromDt);
            it.setLastDt(toDt);
            it.setProduct(product);
            it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.abs(totalInt) > minAdHocIntValue ? totalInt : 0))));
            it.setRoi(rateOfInt);
            it.setIntTableCode(intTableCode);
            intDetails.add(it);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return it;
    }

    public List<LoanIntCalcList> cbsIntCalLoan(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {

        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        String acNature = "";
        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));

            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Account nature does not exist");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            List getAllAccList = null, checkInSecOfLoan = null;
            if (intOpt.equalsIgnoreCase("I")) {
                if (acNature.equals(CbsConstant.CURRENT_AC)) {
                    getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                            + "  (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                } else if (acNature.equals(CbsConstant.DEMAND_LOAN)) {
                    getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                            + "  (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno  as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                } else if (acNature.equals(CbsConstant.TERM_LOAN)) {
                    getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                            + " (select distinct acno  as acno1  from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno  as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.acno = '" + acNo + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and "
                            + " a.acno = '" + acNo + "' and a.CurBrCode = '" + brnCode + "'  order by a.acno").getResultList();

                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist");
                }
                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                        double intAmt = it.getTotalInt();
                        if (intAmt != 0) {
                            intCalc.add(it);
                        }
//                        else {
//                            throw new ApplicationException("Interest is calculated Zero for " + acNo);
//                        }
                        return intCalc;
                    }
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                }
            } else {
                if (acNature.equals(CbsConstant.CURRENT_AC)) {
                    getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                            + " (select distinct acno  as acno1 from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno  as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                } else if (acNature.equals(CbsConstant.DEMAND_LOAN)) {
                    getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                            + " (select distinct acno  as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno  as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                } else if (acNature.equals(CbsConstant.TERM_LOAN)) {
                    getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                            + " (select distinct acno  as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                            + " UNION  "
                            + " select distinct acno  as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                            + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                    checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c where a.acno = b.ACNO AND a.acno = c.ACNO "
                            + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();
                }
                if (getAllAccList.size() <= 0) {
                    throw new ApplicationException("Account number does not exist.");
                }

                if (getAllAccList.size() == checkInSecOfLoan.size()) {
                    for (int i = 0; i < getAllAccList.size(); i++) {
                        Vector getAllAccVect = (Vector) getAllAccList.get(i);
                        acNo = (String) getAllAccVect.get(0);
                        it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                        double intAmt = it.getTotalInt();
                        if (intAmt != 0) {
//                            List getAccExistInNpaList = em.createNativeQuery(" select distinct a.acno from npa_recon a, accountmaster b where  a.acno = b.acno and b.accstatus <> 1 and  a.acno = '" + acNo + "'").getResultList();
//                            if (getAccExistInNpaList.size() > 0) {
                            intCalc.add(it);
//                            }
                        }
//                        else {
//                            throw new ApplicationException("Interest is calculated zero for " + acNo);
//                        }
                    }
                    return intCalc;
                } else {
                    throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intCalc;
    }

    public String reportShow(String brCode, String enterBy, List resultList) throws ApplicationException {
        String message = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            String acno;
            String custName;
            String fromDate;
            String toDate;
            double roi;
            double closingBalance;
            double product;
            double intAmt;
            String intTableCode;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            List<LoanIntCalcList> tempTable = new ArrayList<LoanIntCalcList>();
            Query deleteQuery = em.createNativeQuery("DELETE FROM cbs_temp_loan_rep_table WHERE BRNCODE='" + brCode + "'");
            var = deleteQuery.executeUpdate();
            if (resultList.size() == 1) {
                //tempTable = resultList;
                for (int i = 0; i < resultList.size(); i++) {
                    List temp = (List) resultList.get(i);
                    tempTable = temp;
                    if (tempTable.get(i).getFlag() != null) {

                        if ((resultList.size() == 1 && tempTable.get(0).getFlag().substring(0, 5).equalsIgnoreCase("false"))) {
                            message = tempTable.get(0).getFlag().substring(6);
                            ut.rollback();
                            return message;
                        }
                    } else {
                        acno = tempTable.get(0).getAcNo();
                        custName = tempTable.get(0).getCustName();
                        fromDate = sdf.format(ymmd.parse(tempTable.get(0).getFirstDt()));
                        toDate = sdf.format(ymmd.parse(tempTable.get(0).getLastDt()));
                        roi = tempTable.get(0).getRoi();
                        closingBalance = (-1 * tempTable.get(0).getClosingBal());
                        double acctProduct = -1 * tempTable.get(0).getProduct();
                        double acctInt = -1 * tempTable.get(0).getTotalInt();
                        product = acctProduct;
                        intAmt = acctInt;
                        intTableCode = tempTable.get(0).getIntTableCode();
                        String tmpIntTableCode;
                        List codeDefLt = em.createNativeQuery("SELECT IFNULL(INTEREST_CODE_DESCRIPTION,'') FROM cbs_loan_interest_code_master WHERE INTEREST_CODE='" + intTableCode + "'").getResultList();
                        if (codeDefLt.isEmpty()) {
                            tmpIntTableCode = intTableCode;
                        } else {
                            Vector codeDefLtVec = (Vector) codeDefLt.get(0);
                            tmpIntTableCode = intTableCode + " : " + codeDefLtVec.get(0).toString();
                        }
                        Query insertQuery = em.createNativeQuery("INSERT INTO cbs_temp_loan_rep_table VALUES('" + acno + "','" + custName + "','" + fromDate + "','" + toDate + "'," + roi + "," + closingBalance + "," + product + "," + intAmt + ",'" + tmpIntTableCode + "','" + brCode + "','" + enterBy + "')");
                        var1 = insertQuery.executeUpdate();
                    }
                }
            } else if (resultList.size() > 1) {
                for (int j = 0; j < resultList.size(); j++) {
                    List temp = (List) resultList.get(j);
                    tempTable = temp;
                    //   System.out.println("tempTable.get(i).getFlag():" + tempTable.get(0).getFlag());
                    if (tempTable.get(0).getFlag() != null) {
                        if (tempTable.get(0).getFlag().substring(0, 5).equalsIgnoreCase("false")) {
                            message = tempTable.get(0).getFlag().substring(6);
                            ut.rollback();
                            return message;
                        }
                    } else {
                        acno = tempTable.get(0).getAcNo();
                        custName = tempTable.get(0).getCustName();
                        fromDate = sdf.format(ymmd.parse(tempTable.get(0).getFirstDt()));
                        toDate = sdf.format(ymmd.parse(tempTable.get(0).getLastDt()));
                        double closingBal = tempTable.get(0).getClosingBal();
                        double acctProduct = tempTable.get(0).getProduct();
                        double acctInt = tempTable.get(0).getTotalInt();
                        if (closingBal < 0.0) {
                            closingBal = -1 * closingBal;
                        }
                        if (acctProduct < 0.0) {
                            acctProduct = -1 * acctProduct;
                        }
                        if (acctInt < 0.0) {
                            acctInt = -1 * acctInt;
                        }
                        roi = tempTable.get(0).getRoi();
                        closingBalance = closingBal;
                        product = acctProduct;
                        intAmt = acctInt;
                        intTableCode = tempTable.get(0).getIntTableCode();
                        String tmpIntTableCode;
                        List codeDefLt = em.createNativeQuery("SELECT IFNULL(INTEREST_CODE_DESCRIPTION,'') FROM cbs_loan_interest_code_master WHERE INTEREST_CODE='" + intTableCode + "'").getResultList();
                        if (codeDefLt.isEmpty()) {
                            tmpIntTableCode = intTableCode;
                        } else {
                            Vector codeDefLtVec = (Vector) codeDefLt.get(0);
                            tmpIntTableCode = intTableCode + " : " + codeDefLtVec.get(0).toString();
                        }
                        Query insertQuery = em.createNativeQuery("INSERT INTO cbs_temp_loan_rep_table VALUES('" + acno + "','" + custName + "','" + fromDate + "','" + toDate + "'," + roi + "," + closingBalance + "," + product + "," + intAmt + ",'" + tmpIntTableCode + "','" + brCode + "','" + enterBy + "')");
                        var1 = insertQuery.executeUpdate();
                    }
                }
            }
            ut.commit();
            message = "true";
            return message;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        // return message; commented by sudhir
    }

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException {
        Vector ele = null;
        try {
            List list = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and br.brncode=cast('" + orgnbrcode + "' AS UNSIGNED)").getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return ele;
    }

    public String loanInterestPosting(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException {
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
            String msg = null;
            String uriGl = "", oirHead = "";
            double totalNpaIntAmt = 0;

            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for " + acType);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                glAcNo = brnCode + glHeadVect.get(0).toString() + "01";
                uriGl = glHeadVect.get(1).toString();
                oirHead = glHeadVect.get(2).toString();
            }

            /*
             * Individual Account Wise Posting
             */
            if (intOpt.equalsIgnoreCase("I")) {

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
                msg = "Individual Account updation is not permitted.";
            } else {
                /**
                 * All Account Wise Posting
                 */
                ut.begin();
                List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'Loan Interest' and "
                        + "actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or "
                        + "(todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                if (parameterInfo.isEmpty()) {
                    throw new ApplicationException("Please post it from Main Form 'LOAN INTEREST CALCULATION'");
                } else {
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, (select distinct acno as acno1 "
                                + "from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                                + " UNION  "
                                + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                                + "  where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                        List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c "
                                + "where a.acno = b.ACNO AND a.acno = c.ACNO  and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 "
                                + "and a.CurBrCode = '" + brnCode + "'").getResultList();
                        String oldTrsNo = null;
                        float trSNo = ftsPostRemote.getTrsNo();
                        if (getAllAccList.size() == checkInSecOfLoan.size()) {
                            double glSumAmt = 0f;
                            for (int i = 0; i < getAllAccList.size(); i++) {
                                Vector getAllAccVect = (Vector) getAllAccList.get(i);
                                acNo = (String) getAllAccVect.get(0);
                                it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                                double intAmt = -1 * it.getTotalInt();
                                double closingbal = -1 * it.getClosingBal();
                                double roi = it.getRoi();
                                double crIntAmt = 0, drIntAmt = 0;
                                int ty = 1;
                                if (intAmt != 0) {
                                    //                                List getAccExistInNpaList = em.createNativeQuery(" select distinct a.acno from npa_recon a, accountmaster b where  a.acno = b.acno and b.accstatus <> 1 and  a.acno = '" + acNo + "'").getResultList();
                                    //                                if (getAccExistInNpaList.size() > 0) {
                                    List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "' and accstatus <> 9").getResultList();
                                    Vector acStatusVect = (Vector) acStatusList.get(0);
                                    int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                    if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {

                                        //                                        List getTrSNoList = em.createNativeQuery("select dramt, recno, trsno  from npa_recon where acno = '" + acNo + "' and dt = '" + toDt + "' and details = 'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y'").getResultList();
                                        //                                        Vector getTrSNoVect = (Vector) getTrSNoList.get(0);
                                        //                                        double drAmt = (double) Double.parseDouble(getTrSNoVect.get(0).toString());
                                        //                                        String oldRecNo = (String) getTrSNoVect.get(1).toString();
                                        //                                        oldTrsNo = (String) getTrSNoVect.get(2).toString();
                                        //                                        drAmt = drAmt + intAmt;
                                        if (intAmt < 0) {
                                            crIntAmt = Math.abs(intAmt);
                                            ty = 0;
                                        } else {
                                            drIntAmt = intAmt;
                                            ty = 1;
                                        }
                                        float recNo = ftsPostRemote.getRecNo();
                                        Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,"
                                                + "DETAILS,IY,ENTERBY,AUTHBY,AUTH,PAYBY,trsno,RECNO,balance,trandesc,intamt,status,trantime,org_brnid, dest_brnid)"
                                                + " values('" + acNo + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + drIntAmt + "," + crIntAmt + ",8,'DIFF INT UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + intAmt + "," + acStatus + ",NOW(),'" + brnCode + "','" + brnCode + "')");

                                        //                                        Query insertNpaReconQuery = em.createNativeQuery("UPDATE npa_recon SET dramt = " + drAmt + ", intamt = " + drAmt + "  Where acno = '" + acNo + "' and dt = '" + toDt + "' and details = 'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y' AND recno = '" + recNo + "' AND trsNo = '" + trsNo + "'");

                                        Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                                        if (insertNpaRecon == 0) {
                                            throw new ApplicationException("Value not inserted in  npa_recon");
                                        }
                                        totalNpaIntAmt = totalNpaIntAmt + intAmt;
                                    } else {
                                        //                                        List getTrSNoList = em.createNativeQuery("select dramt, recno, trsno  from ca_recon where acno = '" + acNo + "' and  details = 'INT UPTO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and org_brnid = '" + brnCode + "' and auth = 'Y'").getResultList();
                                        //                                        Vector getTrSNoVect = (Vector) getTrSNoList.get(0);
                                        //                                        double drAmt = (double) Double.parseDouble(getTrSNoVect.get(0).toString());
                                        //                                        String oldRecNo = (String) getTrSNoVect.get(1).toString();
                                        //                                        oldTrsNo = (String) getTrSNoVect.get(2).toString();

                                        //                                        drAmt = drAmt + intAmt;
                                        if (intAmt < 0) {
                                            crIntAmt = Math.abs(intAmt);
                                            ty = 0;
                                        } else {
                                            drIntAmt = intAmt;
                                            ty = 1;
                                        }
                                        //                                        Query insertNpaReconQuery = em.createNativeQuery("UPDATE ca_recon SET dramt = " + drAmt + " Where acno = '" + acNo + "' and details = 'INT UPTO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and org_brnid = '" + brnCode + "' and auth = 'Y' AND recno = '" + oldRecNo + "' AND trsNo = '" + oldTrsNo + "'");

                                        float recNo = ftsPostRemote.getRecNo();
                                        Query insertCaReconQuery = em.createNativeQuery("INSERT ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
                                                + " values('" + acNo + "'," + ty + ", DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + drIntAmt + "," + crIntAmt + ",8,3,3,'DIFF INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
                                                + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                        Integer insertNpaRecon = insertCaReconQuery.executeUpdate();
                                        if (insertNpaRecon == 0) {
                                            throw new ApplicationException("Value is not updated in recon");
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

                                        glSumAmt = glSumAmt + intAmt;
                                    }
                                    //                                }
                                }
                                if (i == getAllAccList.size() - 1) {
                                    if (glSumAmt != 0) {
                                        crIntAmt = 0;
                                        drIntAmt = 0;
                                        Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNo + "'");
                                        Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                        if (updateReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }

                                        //                                    List getTrSNoList = em.createNativeQuery("select cramt, recno  from gl_recon where acno = '" + glAcNo + "' and details = 'VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                            + " and org_brnid = '" + brnCode + "' and auth = 'Y' and trsno = '" + trsNo + "'").getResultList();
                                        //                                    Vector getTrSNoVect = (Vector) getTrSNoList.get(0);
                                        //                                    double crAmt = (double) Double.parseDouble(getTrSNoVect.get(0).toString());
                                        //                                    String recNo = (String) getTrSNoVect.get(1).toString();
                                        //
                                        //                                    crAmt = crAmt + glSumAmt;
                                        //
                                        //                                    Query insertReconBalanQuery = em.createNativeQuery("UPDATE gl_recon SET cramt = " + crAmt + " Where acno = '" + glAcNo + "' and details = 'VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                            + " and org_brnid = '" + brnCode + "' and auth = 'Y' AND recno = '" + recNo + "' AND trsNo = '" + trsNo + "'");
                                        if (glSumAmt < 0) {
                                            drIntAmt = Math.abs(glSumAmt);
                                            ty = 1;
                                        } else {
                                            crIntAmt = glSumAmt;
                                            ty = 0;
                                        }
                                        float recNo = ftsPostRemote.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + glAcNo + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + crIntAmt + "," + drIntAmt + ",8,3,3,'DIFF VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in gl_recon");
                                        }
                                        if (!oirHead.equals("") && !uriGl.equals("")) {
                                            uriGl = brnCode + uriGl + "01";
                                            oirHead = brnCode + oirHead + "01";
                                            if (totalNpaIntAmt != 0) {
                                                /*
                                                 * * ***********************************************************************************
                                                 * urihead(GLHEADURI) means Interest Accrued Due On NPA
                                                 * oirHead(glheadname) means Overdue Interest Reserve
                                                 * ************************************************************************************
                                                 *
                                                 * IN NPA Interest Posting in main Interest Posting 
                                                 *  IF  totalNpaIntAmt >0
                                                 * ------------------------------------------------------
                                                 *                  DrAmt               CrAmt
                                                 * ------------------------------------------------------
                                                 *  OirGl           0                   totalNpaIntAmt
                                                 *  UriGL           totalNpaIntAmt      0
                                                 */
                                                crIntAmt = 0;
                                                drIntAmt = 0;
                                                if (totalNpaIntAmt < 0) {
                                                    crIntAmt = Math.abs(totalNpaIntAmt);
                                                    ty = 0;
                                                } else {
                                                    drIntAmt = totalNpaIntAmt;
                                                    ty = 1;
                                                }
                                                recNo = ftsPostRemote.getRecNo();
                                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                        + " VALUES('" + oirHead + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                        + crIntAmt + "," + drIntAmt + ",8,3,3,'Diff Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                                insertReconBalan = insertReconBalanQuery.executeUpdate();
                                                if (insertReconBalan == 0) {
                                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                                }

                                                crIntAmt = 0;
                                                drIntAmt = 0;
                                                if (totalNpaIntAmt < 0) {
                                                    drIntAmt = Math.abs(totalNpaIntAmt);
                                                    ty = 1;
                                                } else {
                                                    crIntAmt = totalNpaIntAmt;
                                                    ty = 0;
                                                }
                                                recNo = ftsPostRemote.getRecNo();
                                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                        + " VALUES('" + uriGl + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                        + crIntAmt + "," + drIntAmt + ",8,3,3,'Diff Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                                insertReconBalan = insertReconBalanQuery.executeUpdate();
                                                if (insertReconBalan == 0) {
                                                    throw new ApplicationException("Value doesn't inserted in gl_recon");
                                                }

                                                updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + totalNpaIntAmt + " WHERE ACNO= '" + oirHead + "'");
                                                updateReconBalan = updateReconBalanQuery.executeUpdate();
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
                                    } else {
                                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                                    }
                                }
                            }
                            ut.commit();
                            msg = "Interest Posted Successfully. The Batch No. :" + trSNo;
                        } else {
                            throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        String oldTrsNo = null;
                        float trSNo = ftsPostRemote.getTrsNo();

                        List getAllAccList = em.createNativeQuery("select distinct acno from accountmaster a, "
                                + " (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                                + " UNION  "
                                + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) b "
                                + " where a.acno = b.acno1 and  subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();


                        List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c"
                                + " where a.acno = b.ACNO AND a.acno = c.ACNO"
                                + " and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                        if (getAllAccList.size() == checkInSecOfLoan.size()) {
                            double glSumAmt = 0f;
                            for (int i = 0; i < getAllAccList.size(); i++) {
                                double crIntAmt = 0, drIntAmt = 0;
                                Vector getAllAccVect = (Vector) getAllAccList.get(i);
                                acNo = (String) getAllAccVect.get(0);
                                it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                                double intAmt = -1 * it.getTotalInt();
                                double closingBal = -1 * it.getClosingBal();
                                double roi = it.getRoi();
                                int ty = 1;
                                if (intAmt != 0) {
                                    //                                List getAccExistInNpaList = em.createNativeQuery(" select distinct a.acno from npa_recon a, accountmaster b where  a.acno = b.acno and b.accstatus <> 1 and  a.acno = '" + acNo + "'").getResultList();
                                    //                                if (getAccExistInNpaList.size() > 0) {
                                    List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo
                                            + "' and accstatus <> 9").getResultList();
                                    Vector acStatusVect = (Vector) acStatusList.get(0);
                                    int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                    if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {

                                        //                                        List getTrSNoList = em.createNativeQuery("select dramt, recno, trsno  from npa_recon where acno = '" + acNo + "' and details = 'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y'").getResultList();
                                        //                                        Vector getTrSNoVect = (Vector) getTrSNoList.get(0);
                                        //                                        double drAmt = (double) Double.parseDouble(getTrSNoVect.get(0).toString());
                                        //                                        String recNo = (String) getTrSNoVect.get(1).toString();
                                        //                                        trsNo = (String) getTrSNoVect.get(2).toString();
                                        //                                        drAmt = drAmt + intAmt;
                                        //
                                        //                                        Query insertNpaReconQuery = em.createNativeQuery("UPDATE npa_recon SET dramt = " + drAmt + ", intamt = " + drAmt + "  Where acno = '" + acNo + "' and details = 'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y' AND recno = '" + recNo + "' AND trsNo = '" + trsNo + "'");
                                        if (intAmt < 0) {
                                            crIntAmt = Math.abs(intAmt);
                                            ty = 0;
                                        } else {
                                            drIntAmt = intAmt;
                                            ty = 1;
                                        }
                                        float recNo = ftsPostRemote.getRecNo();
                                        Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,"
                                                + "DETAILS,IY,ENTERBY,AUTHBY,AUTH,PAYBY,trsno,RECNO,balance,trandesc,intamt,status,trantime,org_brnid, dest_brnid)"
                                                + " values('" + acNo + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + drIntAmt + "," + crIntAmt + ",8,'DIFF INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + intAmt + "," + acStatus + ",NOW(),'" + brnCode + "','" + brnCode + "')");

                                        totalNpaIntAmt = totalNpaIntAmt + intAmt;
                                        Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
                                        if (insertNpaRecon == 0) {
                                            throw new ApplicationException("Value not inserted in  npa_recon");
                                        }

                                    } else {
                                        //                                        List getTrSNoList = em.createNativeQuery("select dramt, recno, trsno  from loan_recon where acno = '" + acNo + "' and details = 'INT.UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y'").getResultList();
                                        //                                        Vector getTrSNoVect = (Vector) getTrSNoList.get(0);
                                        //                                        double drAmt = (double) Double.parseDouble(getTrSNoVect.get(0).toString());
                                        //                                        String recNo = (String) getTrSNoVect.get(1).toString();
                                        //                                        trsNo = (String) getTrSNoVect.get(2).toString();
                                        //
                                        //                                        drAmt = drAmt + intAmt;
                                        //
                                        //                                        Query insertNpaReconQuery = em.createNativeQuery("UPDATE loan_recon SET dramt = " + drAmt + " Where acno = '" + acNo + "' and details = 'INT.UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                                + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y' AND recno = '" + recNo + "' AND trsNo = '" + trsNo + "'");

                                        if (intAmt < 0) {
                                            crIntAmt = Math.abs(intAmt);
                                            ty = 0;
                                        } else {
                                            drIntAmt = intAmt;
                                            ty = 1;
                                        }

                                        float recNo = ftsPostRemote.getRecNo();
                                        Query insertLoanReconQuery = em.createNativeQuery("INSERT loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
                                                + " values('" + acNo + "'," + ty + ", DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + drIntAmt + "," + crIntAmt + ",8,3,3,'DIFF INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
                                                + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");


                                        Integer insertNpaRecon = insertLoanReconQuery.executeUpdate();
                                        if (insertNpaRecon == 0) {
                                            throw new ApplicationException("Value not inserted in recon table ");
                                        }

                                        List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + acNo + "'").getResultList();
                                        if (chkBalan.size() > 0) {
                                            Query updateReconQuery = em.createNativeQuery(" UPDATE reconbalan SET BALANCE = BALANCE - " + intAmt
                                                    + " WHERE Acno = '" + acNo + "'");
                                            Integer updateRecon = updateReconQuery.executeUpdate();
                                            if (updateRecon == 0) {
                                                throw new ApplicationException("Value not updated in  reconbalan");
                                            }
                                        }

                                        //                                        List existInTable = em.createNativeQuery("SELECT * FROM int_tldetails where acno = '" + acNo + "' ").getResultList();
                                        //                                        if (existInTable.size() > 0) {
                                        //                                            if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                        //                                                Query updateTlDetailsQuery = em.createNativeQuery("UPDATE int_tldetails set cumuactualamt=0, "
                                        //                                                        + "closingactualamt= " + closingBal + "+" + drAmt + ", todate= '" + it.getLastDt()
                                        //                                                        + "' WHERE Acno = '" + acNo + "'");
                                        //                                                Integer updateTlDetails = updateTlDetailsQuery.executeUpdate();
                                        //                                                if (updateTlDetails == 0) {
                                        //                                                    throw new ApplicationException("Value not updated in  int_tldetails");
                                        //                                                }
                                        //                                            }
                                        //                                            if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                        //                                                Query updateDlDetailsQuery = em.createNativeQuery(" UPDATE int_tldetails set closingbalance= "
                                        //                                                        + closingBal + "-" + intAmt + ", closingactualamt= " + closingBal + ",CumuActualAmt =0,"
                                        //                                                        + "CumuPenalAmt = 0,CumuAdhocAmt = 0, todate= '" + it.getLastDt()
                                        //                                                        + "' WHERE Acno = '" + acNo + "'");
                                        //
                                        //                                                Integer updateDlDetails = updateDlDetailsQuery.executeUpdate();
                                        //                                                if (updateDlDetails == 0) {
                                        //                                                    throw new ApplicationException("Value not updated in int_tldetails");
                                        //                                                }
                                        //                                            }
                                        //                                        } else {
                                        //                                            Query updateDlDetailsQuery = em.createNativeQuery(" INSERT INTO int_tldetails(acno, closingbalance, "
                                        //                                                    + "closingactualamt,CumuActualAmt, CumuPenalAmt, CumuAdhocAmt, todate) values('" + acNo + "',"
                                        //                                                    + closingBal + "-(" + intAmt + "), " + closingBal + ",0,0,0,'" + it.getLastDt() + "')");
                                        //                                            Integer updateDlDetails = updateDlDetailsQuery.executeUpdate();
                                        //                                            if (updateDlDetails == 0) {
                                        //                                                throw new ApplicationException("Value not inserted in  int_tldetails");
                                        //                                            }
                                        //                                        }

                                        glSumAmt = glSumAmt + intAmt;
                                    }
                                    String schemeCode = null;
                                    List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from cbs_loan_acc_mast_sec A,"
                                            + " loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
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
                                                + " LCHG_USER_ID = '" + authBy + "', LCHG_TIME = now(), TS_CNT = " + tsCnt + " where ACNO = '" + acNo + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "'").executeUpdate();
                                        if (loanDmdList < 0) {
                                            throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");

                                        }
                                    } else {
                                        Integer loanDmdList = em.createNativeQuery("Insert into cbs_loan_dmd_table(ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_DATE,"
                                                + "DEL_FLG,LD_FREQ_TYPE,DMD_EFF_DATE,DMD_OVDU_DATE,DMD_AMT,RCRE_USER_ID,"
                                                + "RCRE_TIME,EI_AMT,TS_CNT,LATEFEE_STATUS_FLG,dmd_srl_num) Values"
                                                + "('" + acNo + "'," + ShdlNo + ",'" + intDemFlowId + "','" + toDt + "','N','M','" + toDt + "','" + dateAdd(toDt, 1) + "'," + intAmt + ","
                                                + "'" + authBy + "',now()," + intAmt + ",0,'N'," + dmdSrl + ")").executeUpdate();
                                        if (loanDmdList < 0) {
                                            throw new ApplicationException("Data is not inserted into cbs_loan_dmd_table");
                                        }
                                    }
                                    //                                }
                                }
                                if (i == getAllAccList.size() - 1) {
                                    if (glSumAmt != 0) {
                                        crIntAmt = 0;
                                        drIntAmt = 0;
                                        Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNo + "'");
                                        Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                        if (updateReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't updated in reconbalan");
                                        }

                                        //                                    List getTrSNoList = em.createNativeQuery("select cramt, recno  from gl_recon where acno = '" + glAcNo + "' and details = 'VCH INT. UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                            + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y' and trsno = '" + trsNo + "'").getResultList();
                                        //                                    Vector getTrSNoVect = (Vector) getTrSNoList.get(0);
                                        //                                    double crAmt = (double) Double.parseDouble(getTrSNoVect.get(0).toString());
                                        //                                    String recNo = (String) getTrSNoVect.get(1).toString();
                                        //
                                        //                                    crAmt = crAmt + glSumAmt;
                                        //
                                        //                                    Query insertReconBalanQuery = em.createNativeQuery("UPDATE gl_recon SET cramt = " + crAmt + " Where acno = '" + glAcNo + "' and details = 'VCH INT. UP TO " + mdy.format(ymmd.parse(toDt)) + "' "
                                        //                                            + " and valueDt = '" + toDt + "' and org_brnid = '" + brnCode + "' and auth = 'Y' AND recno = '" + recNo + "' AND trsNo = '" + trsNo + "'");

                                        if (glSumAmt < 0) {
                                            drIntAmt = Math.abs(glSumAmt);
                                            ty = 1;
                                        } else {
                                            crIntAmt = glSumAmt;
                                            ty = 1;
                                        }
                                        float recNo = ftsPostRemote.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + glAcNo + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + crIntAmt + "," + drIntAmt + ",8,3,3,'DIFF VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");

                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in gl_recon");
                                        }
                                        if (!oirHead.equals("") && !uriGl.equals("")) {
                                            uriGl = brnCode + uriGl + "01";
                                            oirHead = brnCode + oirHead + "01";
                                            if (totalNpaIntAmt != 0) {
                                                /*
                                                 * /**
                                                 * ***********************************************************************************
                                                 * urihead(GLHEADURI) means Interest Accrued Due On NPA
                                                 * oirHead(glheadname) means Overdue Interest Reserve
                                                 * ************************************************************************************
                                                 *
                                                 * IN NPA Interest Posting in main Interest Posting 
                                                 *  IF  totalNpaIntAmt >0
                                                 * ------------------------------------------------------
                                                 *                  DrAmt               CrAmt
                                                 * ------------------------------------------------------
                                                 *  OirGl           0                   totalNpaIntAmt
                                                 *  UriGL           totalNpaIntAmt      0
                                                 */
                                                crIntAmt = 0;
                                                drIntAmt = 0;
                                                if (totalNpaIntAmt < 0) {
                                                    crIntAmt = Math.abs(totalNpaIntAmt);
                                                    ty = 0;
                                                } else {
                                                    drIntAmt = totalNpaIntAmt;
                                                    ty = 1;
                                                }
                                                recNo = ftsPostRemote.getRecNo();
                                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                        + " VALUES('" + oirHead + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                        + crIntAmt + "," + drIntAmt + ",8,3,3,'Diff Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                                insertReconBalan = insertReconBalanQuery.executeUpdate();
                                                if (insertReconBalan == 0) {
                                                    throw new ApplicationException("Value doesn't inserted in gl_recon");
                                                }
                                                crIntAmt = 0;
                                                drIntAmt = 0;
                                                if (totalNpaIntAmt < 0) {
                                                    drIntAmt = Math.abs(totalNpaIntAmt);
                                                    ty = 1;
                                                } else {
                                                    crIntAmt = totalNpaIntAmt;
                                                    ty = 0;
                                                }
                                                recNo = ftsPostRemote.getRecNo();
                                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                        + " VALUES('" + uriGl + "'," + ty + ",DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                        + crIntAmt + "," + drIntAmt + ",8,3,3,'Diff Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                                insertReconBalan = insertReconBalanQuery.executeUpdate();
                                                if (insertReconBalan == 0) {
                                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                                }

                                                updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + totalNpaIntAmt + " WHERE ACNO= '" + oirHead + "'");
                                                updateReconBalan = updateReconBalanQuery.executeUpdate();
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
                            }
                            ut.commit();
                            msg = "Interest Posted Successfully. The Batch No. :" + trSNo;
                        } else {
                            throw new ApplicationException("Please Check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                        }
                    }
                }
            }
            return msg;
        } catch (Exception e) {
            try {
                e.printStackTrace();
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

    public List acWiseFromDtToDt(String year, String brnCode) throws ApplicationException {
        List dtList;
        try {
            dtList = em.createNativeQuery("select mindate,maxdate from yearend where f_year = '" + year + "' and brncode = '" + brnCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dtList;
    }

    public List<LoanPrequistitsList> staffLoanPerquisits(String dt, String schemeCode, String brnCode, double appRoi) throws ApplicationException {
        List<LoanPrequistitsList> intCalc = new ArrayList<>();
        LoanPrequistitsList it;
        try {
            String brnQuery = "";
            String finStartDt = rbiReportFacade.getMinFinYear(dt);
            YearEndDatePojo yDate = new YearEndDatePojo();
            yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingToDate(brnCode.equalsIgnoreCase("0A") ? "90" : brnCode, dt);
            if (!(brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90"))) {
                brnQuery = " and substring(a.acno,1,2) in ('" + brnCode + "')";
            }
            List acNoList = em.createNativeQuery("select aa.acno, aa.custname, aa.OpeningDt, aa.ClosingDate, aa.acctNature, "
                    + "date_format(aa.NEXT_INT_CALC_DT,'%Y%m%d') as nextIntPayDt, aa.CustId, "
                    + "if(aa.acctNature='CA',"
                    + "(select sum(dramt) from ca_recon where acno = aa.acno and dt between '" + finStartDt + "' and '" + dt + "' and trandesc in (3,4)), "
                    + "(select sum(dramt) from loan_recon where acno = aa.acno and dt between '" + finStartDt + "' and '" + dt + "' and trandesc in (3,4))) as dtIntAmt "
                    + "from "
                    + "(select a.acno, a.custname, a.OpeningDt, a.ClosingDate, e.acctNature, b.NEXT_INT_CALC_DT, f.CustId  from accountmaster a, cbs_loan_acc_mast_sec b, hr_emp_loan_detail c, "
                    + "cbs_scheme_general_scheme_parameter_master d, accounttypemaster e, customerid f where a.ACNo = f.Acno and b.SCHEME_CODE = d.SCHEME_CODE and a.ACNo = b.ACNO and a.ACNo = c.loan_ac and b.SCHEME_CODE = '" + schemeCode + "' "
                    + "and  (a.ClosingDate is null or  a.ClosingDate <>'' or  a.ClosingDate >'" + dt + "') and substring(a.acno,3,2) = e.AcctCode " + brnQuery
                    + "union "
                    + "select a.acno, a.custname, a.OpeningDt, a.ClosingDate, e.acctNature, b.NEXT_INT_CALC_DT, f.CustId  from accountmaster a, cbs_loan_acc_mast_sec b, hr_emp_loan_detail_his c, "
                    + "cbs_scheme_general_scheme_parameter_master d, accounttypemaster e, customerid f where a.ACNo = f.Acno and b.SCHEME_CODE = d.SCHEME_CODE and a.ACNo = b.ACNO and a.ACNo = c.loan_ac and b.SCHEME_CODE = '" + schemeCode + "' "
                    + "and  (a.ClosingDate is null or  a.ClosingDate <>'' or  a.ClosingDate >'" + dt + "') and substring(a.acno,3,2) = e.AcctCode " + brnQuery + ") aa order by aa.CustId, aa.acno").getResultList();

            if (!acNoList.isEmpty()) {
                for (int i = 0; i < acNoList.size(); i++) {
                    it = new LoanPrequistitsList();
                    Vector acNoVect = (Vector) acNoList.get(i);
                    String acNo = acNoVect.get(0).toString();
                    String custName = acNoVect.get(1).toString();
                    String openingDt = acNoVect.get(2).toString();
                    String nextIntPayDt = acNoVect.get(5).toString();
//                    double drIntAmt = Double.parseDouble(acNoVect.get(7).toString());
                    double drIntAmt = 0;
                    Integer custId = Integer.parseInt(acNoVect.get(6).toString());
                    double totalIntApplied = 0, intProjeced = 0, totalNewIntApp = 0, intProjectedNew = 0, runningRoi = 0;
                    List<LoanIntCalcList> intProductDetail = new ArrayList<LoanIntCalcList>();
                    List<LoanMultiList> resultList = accWiseLoanProjectedIntCalc(ymmd.parse(yDate.getMinDate()).before(ymmd.parse(openingDt)) ? openingDt : yDate.getMinDate(),
                            ymmd.parse(nextIntPayDt).before(ymmd.parse(dt)) ? CbsUtil.dateAdd(nextIntPayDt, -1) : dt, acNo, brnCode, "N", 0);
                    if (!resultList.isEmpty()) {
                        intProductDetail = resultList.get(0).getLoanProductIntDetails();
                        //intDetails = resultList.get(0).getLoanIntDetails();
                        for (int k = 0; k < intProductDetail.size(); k++) {
                            double acctInt = intProductDetail.get(k).getTotalInt();
                            if (acctInt < 0.0) {
                                acctInt = -1 * acctInt;
                            }
                            drIntAmt = drIntAmt + acctInt;
                        }
                    }
                    resultList = accWiseLoanIntProductCalcAsPerEmiRecovery(ymmd.parse(nextIntPayDt).before(ymmd.parse(dt)) ? nextIntPayDt : CbsUtil.dateAdd(dt, 1), yDate.getMaxDate(), acNo, brnCode, "N", 0);
                    if (resultList.size() <= 0) {
                        return intCalc;//"Data does not exist";
                    } else {
                        intProductDetail = resultList.get(0).getLoanProductIntDetails();
                        for (int j = 0; j < intProductDetail.size(); j++) {
                            double acctInt = intProductDetail.get(j).getTotalInt();
                            if (acctInt < 0.0) {
                                acctInt = -1 * acctInt;
                            }
                            intProjeced = intProjeced + acctInt;
                            runningRoi = intProductDetail.get(j).getRoi();
                        }
                    }

                    resultList = accWiseLoanProjectedIntCalc(ymmd.parse(yDate.getMinDate()).before(ymmd.parse(openingDt)) ? openingDt : yDate.getMinDate(), ymmd.parse(nextIntPayDt).before(ymmd.parse(dt)) ? CbsUtil.dateAdd(nextIntPayDt, -1) : dt, acNo, brnCode, "Y", appRoi);
                    if (!resultList.isEmpty()) {
                        intProductDetail = resultList.get(0).getLoanProductIntDetails();
                        //intDetails = resultList.get(0).getLoanIntDetails();
                        for (int k = 0; k < intProductDetail.size(); k++) {
                            double acctInt = intProductDetail.get(k).getTotalInt();
                            if (acctInt < 0.0) {
                                acctInt = -1 * acctInt;
                            }
                            totalNewIntApp = totalNewIntApp + acctInt;
                        }
                    }

                    resultList = accWiseLoanProjectedIntCalc(ymmd.parse(nextIntPayDt).before(ymmd.parse(dt)) ? nextIntPayDt : CbsUtil.dateAdd(dt, 1), yDate.getMaxDate(), acNo, brnCode, "Y", appRoi);
                    if (!resultList.isEmpty()) {
                        intProductDetail = resultList.get(0).getLoanProductIntDetails();
                        //intDetails = resultList.get(0).getLoanIntDetails();
                        for (int k = 0; k < intProductDetail.size(); k++) {
                            double acctInt = intProductDetail.get(k).getTotalInt();
                            if (acctInt < 0.0) {
                                acctInt = -1 * acctInt;
                            }
                            intProjectedNew = intProjectedNew + acctInt;
                        }
                    }
                    it.setCustId(custId);
                    it.setAcNo(acNo);
                    it.setCustName(custName);
                    it.setIntApplied(drIntAmt);
                    it.setIntProjected(intProjeced);
                    it.setIntTotal(drIntAmt + intProjeced);
                    it.setNewIntApplied(totalNewIntApp);
                    it.setNewIntProjected(intProjectedNew);
                    it.setNewIntTotal(totalNewIntApp + intProjectedNew);
                    it.setDiffInt((totalNewIntApp + intProjectedNew) - (drIntAmt + intProjeced));
                    intCalc.add(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intCalc;
    }

    public List getStaffScheme() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct b.SCHEME_CODE, d.SCHEME_DESCRIPTION from accountmaster a, cbs_loan_acc_mast_sec b, hr_emp_loan_detail c, \n"
                    + "cbs_scheme_general_scheme_parameter_master d where b.SCHEME_CODE = d.SCHEME_CODE and a.ACNo = b.ACNO and a.ACNo = c.loan_ac \n"
                    + "union \n"
                    + "select distinct b.SCHEME_CODE, d.SCHEME_DESCRIPTION from accountmaster a, cbs_loan_acc_mast_sec b, hr_emp_loan_detail_his c, \n"
                    + "cbs_scheme_general_scheme_parameter_master d where b.SCHEME_CODE = d.SCHEME_CODE and a.ACNo = b.ACNO and a.ACNo = c.loan_ac").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
