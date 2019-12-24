/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.IntCalTable;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Administrator
 */
@Stateless(mappedName = "LoanPenalCalculationBean")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LoanPenalCalculationFacade implements LoanPenalCalculationFacadeRemote {

    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    private CommonReportMethodsRemote common;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPosting;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat mdy = new SimpleDateFormat("MMM dd yyyy");
    NumberFormat formatter = new DecimalFormat("#0.00");

    public List getAcctType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Acctcode From accounttypemaster "
                    + "where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    //*********************Get the From Date(Individual Account Wise)**********************//
    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException {
        try {
            List getMaxToDtList = em.createNativeQuery("select IFNULL(date_format(max(TODT), '%Y%m%d'),'') from  cbs_loan_interest_post_ac_wise where acno = '" + acNo + "' and BRNCODE = '" + brnCode + "' and POST_FLAG = 'Y' and FLAG = 'P'").getResultList();
            String toDt = "";
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                toDt = getMaxToDtVect.get(0).toString();
                if (toDt.equals("")) {
                    String acNature = ftsPosting.getAccountNature(acNo);

                    List selectQuery = em.createNativeQuery("select acno,custname,date_format(openingdt,'%d/%m/%Y'),accstatus from accountmaster where acno='" + acNo + "'").getResultList();
                    if (selectQuery.isEmpty()) {
                        throw new ApplicationException("Account number does not exist.");
                    }

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
                    + "' and POST_FLAG = 'N' and BRNCODE = '" + brnCode + "' and flag = 'P'").getResultList();
            if (getMaxToDtList.size() > 0) {
                Vector getMaxToDtVect = (Vector) getMaxToDtList.get(0);
                int sNo = Integer.parseInt(getMaxToDtVect.get(0).toString());
                if (want.equalsIgnoreCase("f")) {
                    List getFrDtList = em.createNativeQuery("select date_format(FROM_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter "
                            + "where AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
                    if (getFrDtList.size() > 0) {
                        Vector getFrDtVect = (Vector) getFrDtList.get(0);
                        toDt = getFrDtVect.get(0).toString();
                    } else {
                        toDt = "Please check the existance of this account type in cbs_loan_acctype_interest_parameter Table for the current financial year.";
                    }
                } else if (want.equalsIgnoreCase("t")) {
                    List getFrDtList = em.createNativeQuery("select date_format(TO_DT, '%Y%m%d') from  cbs_loan_acctype_interest_parameter where "
                            + "AC_TYPE ='" + acType + "' and POST_FLAG = 'N' and SNO = " + sNo + " and BRNCODE = '" + brnCode + "'").getResultList();
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

    public List<List<LoanIntCalcList>> cbsLoanPenalCalculation(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException {
        List<List<LoanIntCalcList>> intCalc = new ArrayList<List<LoanIntCalcList>>();
//        List intCalcAll = new ArrayList();
        //List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        //LoanIntCalcList it = new LoanIntCalcList();
        String acNature = "";
        List<LoanIntCalcList> repaymentPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> securityPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> stockPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> odPenalList = new ArrayList<LoanIntCalcList>();
        //glAcNO = brnCode + glAcNO + "01";

        try {
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            Date toDate = null;
            toDate = ymmd.parse(toDt);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate);
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            calendar.set(Calendar.DATE, lastDate);
            //Date lastDt = ymd.parse(ymd.format(calendar.getTime()));
            String securityPenalExist = "N";
            String odPenalExist = "N";
            String stockPenalExist = "N";
            String emiPenalExist = "N";
            String penalOnOnlyNpa = "N";
            String penalExcludeNpa = "N", npaAcExcludeQuery = "", npaExcludeQuery = "";
            List penalOnOnlyNpaList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ONLY_NPA' ").getResultList();
            if (penalOnOnlyNpaList.size() > 0) {
                Vector penalOnOnlyNpaVect = (Vector) penalOnOnlyNpaList.get(0);
                penalOnOnlyNpa = penalOnOnlyNpaVect.get(0).toString();
            }

            List penalExcludeNpaList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_EXCLUDE_NPA' ").getResultList();
            if (penalExcludeNpaList.size() > 0) {
                Vector penalExcludeNpaVect = (Vector) penalExcludeNpaList.get(0);
                penalExcludeNpa = penalExcludeNpaVect.get(0).toString();
            }

            List securityPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_SECURITY' ").getResultList();
            if (securityPenalExistList.size() > 0) {
                Vector securityPenalExistVect = (Vector) securityPenalExistList.get(0);
                securityPenalExist = securityPenalExistVect.get(0).toString();
            }

            List odPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ODLIMIT' ").getResultList();
            if (odPenalExistList.size() > 0) {
                Vector odPenalExistVect = (Vector) odPenalExistList.get(0);
                odPenalExist = odPenalExistVect.get(0).toString();
            }

            List stockPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_STOCK' ").getResultList();
            if (stockPenalExistList.size() > 0) {
                Vector stockPenalExistVect = (Vector) stockPenalExistList.get(0);
                stockPenalExist = stockPenalExistVect.get(0).toString();
            }
            List emiPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_EMI' ").getResultList();
            if (emiPenalExistList.size() > 0) {
                Vector emiPenalExistVect = (Vector) emiPenalExistList.get(0);
                emiPenalExist = emiPenalExistVect.get(0).toString();
            }

            List checkEmiPenalPosted = em.createNativeQuery("select actype from parameterinfo_posthistory where purpose = 'EMI PENAL INT' "
                    + "and actype = '" + acType + "'  and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt
                    + "' and '" + toDt + "'))").getResultList();
            if (checkEmiPenalPosted.size() > 0) {
                throw new ApplicationException("Penal Interest Already Posted.");
            }
            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Account nature does not exist");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            /**
             * *Calculation Part**
             */
            if (penalExcludeNpa.equalsIgnoreCase("Y")) {
                npaAcExcludeQuery = " and accStatus not in ('11','12','13') ";
                npaExcludeQuery = " and a.accStatus not in ('11','12','13')";
            }
            if (intOpt.equalsIgnoreCase("I")) {
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    if (securityPenalExist.equalsIgnoreCase("Y")) {
                        securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        if (securityPenalList.size() > 0) {
                            if (securityPenalList.get(0).getTotalInt() > 0) {
                                intCalc.add(securityPenalList);
                            }
                        }
                    }
                    if (stockPenalExist.equalsIgnoreCase("Y")) {
                        stockPenalList = stockPenalCalculation(acNo, fromDt, toDt, brnCode);
                        if (stockPenalList.size() > 0) {
                            if (stockPenalList.get(0).getTotalInt() > 0) {
                                intCalc.add(stockPenalList);
                            }
                        }
                    }
                    if (odPenalExist.equalsIgnoreCase("Y")) {
                        odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                        if (odPenalList.size() > 0) {
                            if (odPenalList.get(0).getTotalInt() > 0) {
                                intCalc.add(odPenalList);
                            }
                        }
                    }

                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        if (emiPenalExist.equalsIgnoreCase("Y")) {
                            repaymentPenalList = repaymentPenalCalculationOnEMI(acNo, fromDt, toDt, brnCode);
                            if (repaymentPenalList.size() > 0) {
                                if (repaymentPenalList.get(0).getTotalInt() > 0) {
                                    intCalc.add(repaymentPenalList);
                                }
                            }
                        }
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        if (odPenalExist.equalsIgnoreCase("Y")) {
                            odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                            if (odPenalList.size() > 0) {
                                if (odPenalList.get(0).getTotalInt() > 0) {
                                    intCalc.add(odPenalList);
                                }
                            }
                        }
                    }
                    if (securityPenalExist.equalsIgnoreCase("Y")) {
                        securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        if (securityPenalList.size() > 0) {
                            if (securityPenalList.get(0).getTotalInt() > 0) {
                                intCalc.add(securityPenalList);
                            }
                        }
                    }

                }
                return intCalc;
            } else if (intOpt.equalsIgnoreCase("A")) {
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List getAllAccList, checkInSecOfLoan;
                    if (penalOnOnlyNpa.equalsIgnoreCase("Y")) {
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + "  subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "'").getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "'").getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

                    } else {

//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + " acno in (select distinct acno from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
//                                + " Union All  "
//                                + " select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) "
//                                + " and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "'" + npaAcExcludeQuery).getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where "
                                + "acno in (select distinct acno from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                                + "Union All "
                                + "select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno) "
                                + "and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "' " + npaAcExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO and a.acno in (select distinct c.acno from ca_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
//                                + " Union All  "
//                                + " select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'" + npaExcludeQuery).getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and a.acno in (select distinct c.acno from ca_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
                                + "Union All "
                                + "select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' " + npaExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

                    }
                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        for (int i = 0; i < getAllAccList.size(); i++) {
                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = (String) getAllAccVect.get(0);

                            if (securityPenalExist.equalsIgnoreCase("Y")) {
                                securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                                if (securityPenalList.size() > 0) {
                                    if (securityPenalList.get(0).getTotalInt() > 0) {
                                        intCalc.add(securityPenalList);
                                    }
                                }
                            }
                            if (stockPenalExist.equalsIgnoreCase("Y")) {
                                stockPenalList = stockPenalCalculation(acNo, fromDt, toDt, brnCode);
                                if (stockPenalList.size() > 0) {
                                    if (stockPenalList.get(0).getTotalInt() > 0) {
                                        intCalc.add(stockPenalList);
                                    }
                                }
                            }
                            if (odPenalExist.equalsIgnoreCase("Y")) {
                                odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                                if (odPenalList.size() > 0) {
                                    if (odPenalList.get(0).getTotalInt() > 0) {
                                        intCalc.add(odPenalList);
                                    }
                                }
                            }
                        }
                        return intCalc;
                    } else {
                        throw new ApplicationException("Please check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List getAllAccList, checkInSecOfLoan;
                    if (penalOnOnlyNpa.equalsIgnoreCase("Y")) {
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + "  subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "'").getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "'").getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

                    } else {
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + " acno in (select distinct acno from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
//                                + " Union All  "
//                                + " select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) "
//                                + " and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "'" + npaAcExcludeQuery).getResultList();


                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where "
                                + "acno in (select distinct acno from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                                + "Union All "
                                + "select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) "
                                + "and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "'" + npaAcExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO and a.acno in (select distinct c.acno from loan_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
//                                + " Union All  "
//                                + " select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'" + npaExcludeQuery).getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and a.acno in (select distinct c.acno from loan_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
                                + "Union All "
                                + "select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' " + npaExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();
                    }
                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        for (int i = 0; i < getAllAccList.size(); i++) {
                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = (String) getAllAccVect.get(0);
                            if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                if (emiPenalExist.equalsIgnoreCase("Y")) {
                                    repaymentPenalList = repaymentPenalCalculationOnEMI(acNo, fromDt, toDt, brnCode);
                                    if (repaymentPenalList.size() > 0) {
                                        if (repaymentPenalList.get(0).getTotalInt() > 0) {
                                            intCalc.add(repaymentPenalList);
                                        }
                                    }
                                }
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                if (odPenalExist.equalsIgnoreCase("Y")) {
                                    odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                                    if (odPenalList.size() > 0) {
                                        if (odPenalList.get(0).getTotalInt() > 0) {
                                            intCalc.add(odPenalList);
                                        }
                                    }
                                }
                            }

                            if (securityPenalExist.equalsIgnoreCase("Y")) {
                                securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                                if (securityPenalList.size() > 0) {
                                    if (securityPenalList.get(0).getTotalInt() > 0) {
                                        intCalc.add(securityPenalList);
                                    }
                                }
                            }
                        }
                        return intCalc;
                    } else {
                        throw new ApplicationException("Please check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                    }
                }
            }
            /**
             * *Calculation Part End**
             */
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intCalc;
    }

    public String cbsLoanPenalPosting(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String acNature = "", schemeCode = "", freq = "";
        double repaymentPenalAmt = 0d, securityPenalAmt = 0d, stockPenalAmt = 0d, odPenalAmt = 0, totalPenal = 0, totalGlPenal = 0, roi = 0, totalNpaIntAmt = 0;
        SimpleDateFormat mmmdy = new SimpleDateFormat("MMM dd yyyy");
        List<LoanIntCalcList> repaymentPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> securityPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> stockPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> odPenalList = new ArrayList<LoanIntCalcList>();
        try {
            ut.begin();
            fromDt = ymmd.format(dmy.parse(fromDt));
            toDt = ymmd.format(dmy.parse(toDt));
            Date toDate = null;
            toDate = ymmd.parse(toDt);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate);
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            calendar.set(Calendar.DATE, lastDate);
            //Date lastDt = ymd.parse(ymd.format(calendar.getTime()));

            String securityPenalExist = "N";
            String odPenalExist = "N";
            String stockPenalExist = "N";
            String emiPenalExist = "N";
            String penalOnOnlyNpa = "N";
            String penalExcludeNpa = "N", npaAcExcludeQuery = "", npaExcludeQuery = "";
            List penalOnOnlyNpaList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ONLY_NPA' ").getResultList();
            if (penalOnOnlyNpaList.size() > 0) {
                Vector penalOnOnlyNpaVect = (Vector) penalOnOnlyNpaList.get(0);
                penalOnOnlyNpa = penalOnOnlyNpaVect.get(0).toString();
            }
            List penalExcludeNpaList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_EXCLUDE_NPA' ").getResultList();
            if (penalExcludeNpaList.size() > 0) {
                Vector penalExcludeNpaVect = (Vector) penalExcludeNpaList.get(0);
                penalExcludeNpa = penalExcludeNpaVect.get(0).toString();
            }

            if (intOpt.equalsIgnoreCase("I")) {
                throw new ApplicationException("Individual account penal posting is not allowed by system.");
            }
            String uriGl = "";
            String oirHead = "";
            glAcNO = brnCode + glAcNO + "01";
            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for " + acType);
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
//                glAcNo = brnCode + glHeadVect.get(0).toString() + "01";
                uriGl = glHeadVect.get(1).toString();
                oirHead = glHeadVect.get(2).toString();
            }
            List securityPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_SECURITY' ").getResultList();
            if (securityPenalExistList.size() > 0) {
                Vector securityPenalExistVect = (Vector) securityPenalExistList.get(0);
                securityPenalExist = securityPenalExistVect.get(0).toString();
            }

            List odPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ODLIMIT' ").getResultList();
            if (odPenalExistList.size() > 0) {
                Vector odPenalExistVect = (Vector) odPenalExistList.get(0);
                odPenalExist = odPenalExistVect.get(0).toString();
            }

            List stockPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_STOCK' ").getResultList();
            if (stockPenalExistList.size() > 0) {
                Vector stockPenalExistVect = (Vector) stockPenalExistList.get(0);
                stockPenalExist = stockPenalExistVect.get(0).toString();
            }
            List emiPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_EMI' ").getResultList();
            if (emiPenalExistList.size() > 0) {
                Vector emiPenalExistVect = (Vector) emiPenalExistList.get(0);
                emiPenalExist = emiPenalExistVect.get(0).toString();
            }

            List checkEmiPenalPosted = em.createNativeQuery("select actype from parameterinfo_posthistory where purpose = 'EMI PENAL INT' "
                    + "and actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt
                    + "' and '" + toDt + "'))").getResultList();
            if (checkEmiPenalPosted.size() > 0) {
                throw new ApplicationException("Penal interest already posted.");
            }
            List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            if (acNatureList.isEmpty()) {
                throw new ApplicationException("Account nature does not exist");
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = acNatureVect.get(0).toString();
            }

            /**
             * **Posting Part**
             */
            List tempBdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
            Vector tempBdVect = (Vector) tempBdList.get(0);
            String tempBd = tempBdVect.get(0).toString();

            if (intOpt.equalsIgnoreCase("I")) {
                List SecDetailsList = em.createNativeQuery("SELECT A.SCHEME_CODE, A.CALC_METHOD  from cbs_loan_acc_mast_sec A, "
                        + "loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();

                if (SecDetailsList.isEmpty()) {
                    throw new ApplicationException("Account number does not exist in secondary details table of loan.");
                } else {
                    for (int i = 0; i < SecDetailsList.size(); i++) {
                        Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                        schemeCode = (String) SecDetailsVect.get(0);
                        freq = (String) SecDetailsVect.get(1);
                    }
                }
                List flowDetailList = em.createNativeQuery("select PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, "
                        + "PAST_DUE_COLLECTION_FLOW_ID, CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '"
                        + schemeCode + "'").getResultList();

                String penalIntDemFlowId = null;
                if (flowDetailList.isEmpty()) {
                    throw new ApplicationException("Flow Id Does Not Exists in Scheme Master.");
                } else {
                    for (int i = 0; i < flowDetailList.size(); i++) {
                        Vector flowDetailVect = (Vector) flowDetailList.get(i);
                        penalIntDemFlowId = flowDetailVect.get(0).toString();
                    }
                }
                float trSNo = ftsPosting.getTrsNo();
                String till = mmmdy.format(toDate);
                repaymentPenalAmt = 0d;
                securityPenalAmt = 0d;
                stockPenalAmt = 0d;
                odPenalAmt = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        if (emiPenalExist.equalsIgnoreCase("Y")) {
                            repaymentPenalList = repaymentPenalCalculationOnEMI(acNo, fromDt, toDt, brnCode);
                            repaymentPenalAmt = repaymentPenalList.get(0).getTotalInt();
                            roi = repaymentPenalList.get(0).getRoi();
                        }
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        if (odPenalExist.equalsIgnoreCase("Y")) {
                            odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                            odPenalAmt = odPenalList.get(0).getTotalInt();
                            roi = odPenalList.get(0).getRoi();
                        }
                    }
                    if (securityPenalExist.equalsIgnoreCase("Y")) {
                        securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        securityPenalAmt = securityPenalList.get(0).getTotalInt();
                        roi = securityPenalList.get(0).getRoi();
                    }

                    totalPenal = repaymentPenalAmt + securityPenalAmt + odPenalAmt;
                    float recNo;
                    Integer insertLaRecon = null;
                    if (repaymentPenalAmt > 0) {
                        recNo = ftsPosting.getRecNo();
                        insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,"
                                + "payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "'," + repaymentPenalAmt
                                + ",0,8,3,3,'EMI PENAL INT UP TO " + till + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'"
                                + brnCode + "','" + brnCode + "')").executeUpdate();

                        if (insertLaRecon <= 0) {
                            throw new ApplicationException("Insertion failed in LOAN RECON table");
                        }
                    }

                    if (odPenalAmt > 0) {
                        recNo = ftsPosting.getRecNo();
                        insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,"
                                + "payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "'," + odPenalAmt
                                + ",0,8,3,3,'OVERLIMIT PENAL INT UP TO " + till + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'"
                                + brnCode + "','" + brnCode + "')").executeUpdate();

                        if (insertLaRecon <= 0) {
                            throw new ApplicationException("Insertion failed in LOAN RECON table");
                        }
                    }

                    if (securityPenalAmt > 0) {
                        recNo = ftsPosting.getRecNo();
                        insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,"
                                + "DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + securityPenalAmt
                                + "',0,8,3,3,'SECURITY PENAL INT UP TO " + till + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo
                                + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                        if (insertLaRecon <= 0) {
                            throw new ApplicationException("Insertion failed in LOAN RECON table");
                        }
                    }

                    if (insertLaRecon > 0) {
                        List chkBalList = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + acNo + "'").getResultList();
                        if (chkBalList.size() > 0) {
                            Vector chkBalVect = (Vector) chkBalList.get(0);
                            double balance = Double.parseDouble(chkBalVect.get(0).toString()) - totalPenal;
                            Integer updateBalanQry = em.createNativeQuery("UPDATE  reconbalan SET BALANCE=" + balance
                                    + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + acNo + "'").executeUpdate();
                            if (updateBalanQry > 0) {
                                if (intOpt.equalsIgnoreCase("I")) {
                                    glAcNO = brnCode + glAcNO + "01";
                                    List chkGlBalList = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + glAcNO + "'").getResultList();
                                    if (chkBalList.size() > 0) {
                                        recNo = ftsPosting.getRecNo();

                                        Vector chkGlBalVect = (Vector) chkGlBalList.get(0);
                                        double glBalance = Double.parseDouble(chkGlBalVect.get(0).toString()) + totalPenal;
                                        updateBalanQry = em.createNativeQuery("UPDATE  reconbalan SET BALANCE=" + glBalance
                                                + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + glAcNO + "'").executeUpdate();

                                        if (updateBalanQry <= 0) {
                                            throw new ApplicationException("Balance is not updated in reconbalan table.");
                                        }

                                        Integer insertGLRecon = em.createNativeQuery("INSERT into gl_recon (ACNO,TY,DT,VALUEDT,CRAMT,dramt,"
                                                + "TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + totalPenal + ",0,8,3,3,'VCH$ As Penal Int. Amt UP TO " + till + "','" + authBy
                                                + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
                                        if (insertGLRecon <= 0) {
                                            throw new ApplicationException("Balance does not inserted in GL RECON table");
                                        } else {
                                            List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                            Vector sNoVect = (Vector) sNoList.get(0);
                                            int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                            Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','P')");
                                            Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                            if (updateIntPostAcWise <= 0) {
                                                throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                            } else {
                                                List dmdSchNoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                                Vector dmdSchNoVect = (Vector) dmdSchNoList.get(0);
                                                int dmdSchNo = Integer.parseInt(dmdSchNoVect.get(0).toString());

                                                List srNoList = em.createNativeQuery("select IFNULL(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                                Vector srNoVect = (Vector) srNoList.get(0);
                                                int srNo = Integer.parseInt(srNoVect.get(0).toString());
                                                String overDudt = dateAdd(toDt, 1);
                                                Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, LD_FREQ_TYPE, DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, LCHG_USER_ID, LCHG_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG)"
                                                        + " values('" + acNo + "'," + dmdSchNo + ",'" + penalIntDemFlowId + "','" + ymd.format(ymmd.parse(toDt)) + "'," + srNo + ",'N', '" + freq + "','" + ymmd.format(ymmd.parse(toDt)) + "', '" + overDudt + "'," + totalPenal + ", '" + authBy + "',NOW(), " + totalPenal + ",0,'N')");
                                                Integer insertQry = insertQuery.executeUpdate();
                                                if (insertQry <= 0) {
                                                    throw new ApplicationException("Insertion problem in Loan Demand table");
                                                } else {
                                                    ut.commit();
                                                    return "Penal Interest posted successfully. Batch No. is " + trSNo;
                                                }
                                            }
                                        }
                                    } else {
                                        throw new ApplicationException("No information about account balance");
                                    }
                                }
                            } else {
                                throw new ApplicationException("Balance does not update.");
                            }
                        } else {
                            throw new ApplicationException("Account does not update.");
                        }
                    } else {
                        throw new ApplicationException("Value is not inserted in Loan Recon Table");
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    if (securityPenalExist.equalsIgnoreCase("Y")) {
                        securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        securityPenalAmt = securityPenalList.get(0).getTotalInt();
                        roi = securityPenalList.get(0).getRoi();
                    }
                    if (stockPenalExist.equalsIgnoreCase("Y")) {
                        stockPenalList = stockPenalCalculation(acNo, fromDt, toDt, brnCode);
                        stockPenalAmt = stockPenalList.get(0).getTotalInt();
                        roi = stockPenalList.get(0).getRoi();
                    }
                    if (odPenalExist.equalsIgnoreCase("Y")) {
                        odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                        odPenalAmt = odPenalList.get(0).getTotalInt();
                        roi = odPenalList.get(0).getRoi();
                    }

                    totalPenal = securityPenalAmt + stockPenalAmt + odPenalAmt;
                    float recNo;
                    Integer insertLaRecon = null;
                    if (securityPenalAmt > 0) {
                        recNo = ftsPosting.getRecNo();

                        insertLaRecon = em.createNativeQuery("INSERT into ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + securityPenalAmt + "',0,8,3,3,'SECURITY PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                        if (insertLaRecon <= 0) {
                            throw new ApplicationException("Insertion problem in Ca Recon table");
                        }
                    }

                    if (stockPenalAmt > 0) {
                        recNo = ftsPosting.getRecNo();

                        insertLaRecon = em.createNativeQuery("INSERT into ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + stockPenalAmt + "',0,8,3,3,'STOCK PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                        if (insertLaRecon <= 0) {
                            throw new ApplicationException("Insertion problem in Ca Recon table");
                        }
                    }
                    if (odPenalAmt > 0) {
                        recNo = ftsPosting.getRecNo();

                        insertLaRecon = em.createNativeQuery("INSERT into ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + odPenalAmt + "',0,8,3,3,'OVERLIMIT PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                        if (insertLaRecon <= 0) {
                            throw new ApplicationException("Insertion problem in Ca Recon table");
                        }
                    }
                    if (insertLaRecon > 0) {
                        List chkBalList = em.createNativeQuery("SELECT BALANCE FROM  ca_reconbalan WHERE ACNO='" + acNo + "'").getResultList();
                        if (chkBalList.size() > 0) {
                            Vector chkBalVect = (Vector) chkBalList.get(0);
                            double balance = Double.parseDouble(chkBalVect.get(0).toString()) - totalPenal;
                            Integer updateBalanQry = em.createNativeQuery("UPDATE  ca_reconbalan SET BALANCE=" + balance + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + acNo + "'").executeUpdate();
                            if (updateBalanQry > 0) {
                                if (intOpt.equalsIgnoreCase("I")) {
                                    glAcNO = brnCode + glAcNO + "01";
                                    List chkGlBalList = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + glAcNO + "'").getResultList();
                                    if (chkGlBalList.size() > 0) {
                                        recNo = ftsPosting.getRecNo();

                                        Vector chkGlBalVect = (Vector) chkGlBalList.get(0);
                                        double glBalance = Double.parseDouble(chkGlBalVect.get(0).toString()) + totalPenal;
                                        updateBalanQry = em.createNativeQuery("UPDATE  reconbalan SET BALANCE=" + glBalance + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + glAcNO + "'").executeUpdate();
                                        if (updateBalanQry <= 0) {
                                            throw new ApplicationException("updation problem in balance");
                                        }
                                        Integer insertGLRecon = em.createNativeQuery("INSERT into gl_recon (ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + totalPenal + "',0,8,3,3,'VCH$ As EMI Penal Int. Amt UP TO " + till + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
                                        if (insertGLRecon <= 0) {
                                            throw new ApplicationException("Balance does not inserted in Gl Head.");
                                        } else {
                                            List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                            Vector sNoVect = (Vector) sNoList.get(0);
                                            int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                            Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','P')");
                                            Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                            if (updateIntPostAcWise <= 0) {
                                                throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                            } else {
                                                List dmdSchNoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                                Vector dmdSchNoVect = (Vector) dmdSchNoList.get(0);
                                                int dmdSchNo = Integer.parseInt(dmdSchNoVect.get(0).toString());

                                                List srNoList = em.createNativeQuery("select IFNULL(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                                Vector srNoVect = (Vector) srNoList.get(0);
                                                int srNo = Integer.parseInt(srNoVect.get(0).toString());
                                                String overDudt = dateAdd(toDt, 1);
                                                Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, LD_FREQ_TYPE, DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, LCHG_USER_ID, LCHG_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG)"
                                                        + " values('" + acNo + "'," + dmdSchNo + ",'" + penalIntDemFlowId + "','" + toDt + "'," + srNo + ",'N', '" + freq + "','" + toDt + "', '" + overDudt + "'," + totalPenal + ", '" + authBy + "',NOW(), " + totalPenal + ",0,'N')");
                                                Integer insertQry = insertQuery.executeUpdate();
                                                if (insertQry <= 0) {
                                                    throw new ApplicationException("Insertion problem in Loan Demand Table");
                                                } else {
                                                    ut.commit();
                                                    return "Penal Interest posted successfully. Batch No. is " + trSNo;
                                                }
                                            }
                                        }
                                    } else {
                                        throw new ApplicationException("No information about account balance");
                                    }
                                }
                            } else {
                                throw new ApplicationException("Balance does not update.");
                            }
                        } else {
                            throw new ApplicationException("Account does not update.");
                        }
                    } else {
                        throw new ApplicationException("Sorry,value is not inserted in loan_recon.");
                    }
                }
            } else if (intOpt.equalsIgnoreCase("A")) {
//                authBy = "SYSTEM";
                /* Checking toDate is the last date of the month*/
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'PENAL INT' and actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                    if (parameterInfo.size() > 0) {
                        throw new ApplicationException("Interest already posted");
                    }

                    if (penalExcludeNpa.equalsIgnoreCase("Y")) {
                        npaAcExcludeQuery = " and accStatus not in ('11','12','13') ";
                        npaExcludeQuery = " and a.accStatus not in ('11','12','13')";
                    }

                    List getAllAccList, checkInSecOfLoan;
                    if (penalOnOnlyNpa.equalsIgnoreCase("Y")) {
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + " subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and curBrCode = '" + brnCode + "'").getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13')  and a.CurBrCode = '" + brnCode + "'").getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

                    } else {
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + " acno in (select distinct acno from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
//                                + " Union All  "
//                                + " select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) "
//                                + " and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and curBrCode = '" + brnCode + "'" + npaAcExcludeQuery).getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where "
                                + "acno in (select distinct acno from ca_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                                + "Union All "
                                + "select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno) "
                                + "and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "' " + npaAcExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO and a.acno in (select distinct c.acno from ca_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
//                                + " Union All  "
//                                + " select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'" + npaExcludeQuery).getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and a.acno in (select distinct c.acno from ca_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
                                + "Union All "
                                + "select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' " + npaExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();
                    }

                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        double glSumAmt = 0f;
                        float recNo;
                        float trSNo = ftsPosting.getTrsNo();
                        String till = mmmdy.format(toDate);
                        for (int i = 0; i < getAllAccList.size(); i++) {
                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = (String) getAllAccVect.get(0);
                            repaymentPenalAmt = 0d;
                            securityPenalAmt = 0d;
                            stockPenalAmt = 0d;
                            odPenalAmt = 0;
                            List SecDetailsList = em.createNativeQuery("SELECT A.SCHEME_CODE, A.CALC_METHOD   from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
                            if (SecDetailsList.isEmpty()) {
                                throw new ApplicationException("Account number does not exists in secondary details table of loan.");
                            } else {
                                for (int k = 0; k < SecDetailsList.size(); k++) {
                                    Vector SecDetailsVect = (Vector) SecDetailsList.get(k);
                                    schemeCode = (String) SecDetailsVect.get(0);
                                    freq = (String) SecDetailsVect.get(1);
                                }
                            }
                            List flowDetailList = em.createNativeQuery("select PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID,CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();

                            String penalIntDemFlowId = null;
                            if (flowDetailList.isEmpty()) {
                                throw new ApplicationException("Flow Id does not exist in Scheme Master.");
                            } else {
                                for (int l = 0; l < flowDetailList.size(); l++) {
                                    Vector flowDetailVect = (Vector) flowDetailList.get(l);
                                    penalIntDemFlowId = flowDetailVect.get(0).toString();
                                }
                            }

                            if (securityPenalExist.equalsIgnoreCase("Y")) {
                                securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                                securityPenalAmt = securityPenalList.get(0).getTotalInt();
                                roi = securityPenalList.get(0).getRoi();
                            }
                            if (stockPenalExist.equalsIgnoreCase("Y")) {
                                stockPenalList = stockPenalCalculation(acNo, fromDt, toDt, brnCode);
                                stockPenalAmt = stockPenalList.get(0).getTotalInt();
                                roi = stockPenalList.get(0).getRoi();
                            }
                            if (odPenalExist.equalsIgnoreCase("Y")) {
                                odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                                odPenalAmt = odPenalList.get(0).getTotalInt();
                                roi = odPenalList.get(0).getRoi();
                            }

                            totalPenal = securityPenalAmt + stockPenalAmt + odPenalAmt;
//                            if (securityPenalAmt > 0 || stockPenalAmt > 0 || odPenalAmt >0) {
                            if (totalPenal > 0) {
                                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "' and accstatus <> 9").getResultList();
                                Vector acStatusVect = (Vector) acStatusList.get(0);
                                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {
                                    recNo = ftsPosting.getRecNo();
                                    Integer insertLaRecon = null;
                                    if (securityPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                                + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                                + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + securityPenalAmt + ",0,8,'SECURITY PENAL INT UP TO " + till + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + securityPenalAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                                + brnCode + "')").executeUpdate();
                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }

                                    if (stockPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                                + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                                + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + stockPenalAmt + ",0,8,''STOCK PENAL INT UP TO " + till + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + stockPenalAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                                + brnCode + "')").executeUpdate();
                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }

                                    if (odPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                                + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                                + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + odPenalAmt + ",0,8,'OVERLIMIT PENAL INT UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + odPenalAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                                + brnCode + "')").executeUpdate();
                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }
//                                    Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
//                                            + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
//                                            + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
//                                            + totalPenal + ",0,8,'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
//                                            + trSNo + "," + recNo + ",0,3," + totalPenal + "," + acStatus + ",NOW(),'" + brnCode + "','"
//                                            + brnCode + "')");
//
//                                    Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
//                                    if (insertNpaRecon == 0) {
//                                        throw new ApplicationException("Value not inserted in  npa_recon");
//                                    }
                                    /**
                                     * * Added For NPA Accounts**
                                     */
                                    totalNpaIntAmt = totalNpaIntAmt + totalPenal;

                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','P')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    /**
                                     * * Addition End Here **
                                     */
                                } else {
                                    recNo = ftsPosting.getRecNo();
                                    Integer insertLaRecon = null;
                                    if (securityPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT into ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + securityPenalAmt + "',0,8,3,3,'SECURITY PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }

                                    if (stockPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT into ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + stockPenalAmt + "',0,8,3,3,'STOCK PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }

                                    if (odPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT into ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + odPenalAmt + "',0,8,3,3,'OVERLIMIT PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }
//                                    Query insertCaReconQuery = em.createNativeQuery("INSERT ca_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
//                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO, org_brnid, dest_brnid) "
//                                            + " values('" + acNo + "',1, DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
//                                            + totalPenal + ",0,8,3,3,'INT UPTO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "','system','Y','" + authBy + "',"
//                                            + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
//
//                                    Integer insertCaRecon = insertCaReconQuery.executeUpdate();
//                                    if (insertCaRecon == 0) {
//                                        throw new ApplicationException("Value not inserted in Ca_Recon");
//                                    }
                                    List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  ca_reconbalan WHERE ACNO='" + acNo + "'").getResultList();

                                    if (chkBalan.size() > 0) {
                                        Query updateCaReconQuery = em.createNativeQuery("UPDATE ca_reconbalan SET BALANCE = BALANCE - " + totalPenal
                                                + " WHERE Acno = '" + acNo + "'");

                                        Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                        if (updateCaRecon == 0) {
                                            throw new ApplicationException("Value not updated in  ca_reconbalan");
                                        }
                                    }
                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    } else {
                                        List dmdSchNoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                        Vector dmdSchNoVect = (Vector) dmdSchNoList.get(0);
                                        int dmdSchNo = Integer.parseInt(dmdSchNoVect.get(0).toString());

                                        List srNoList = em.createNativeQuery("select IFNULL(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                        Vector srNoVect = (Vector) srNoList.get(0);
                                        int srNo = Integer.parseInt(srNoVect.get(0).toString());
                                        String overDudt = dateAdd(toDt, 1);
                                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, LD_FREQ_TYPE, DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, LCHG_USER_ID, LCHG_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG)"
                                                + " values('" + acNo + "'," + dmdSchNo + ",'" + penalIntDemFlowId + "','" + ymd.format(ymmd.parse(toDt)) + "'," + srNo + ",'N', '" + freq + "','" + ymmd.format(ymmd.parse(toDt)) + "', '" + overDudt + "'," + totalPenal + ", '" + authBy + "',NOW(), " + totalPenal + ",0,'N')");
                                        Integer insertQry = insertQuery.executeUpdate();
                                        if (insertQry <= 0) {
                                            throw new ApplicationException("Insertion problem in Loan Demand table");
                                        }
                                    }

                                    glSumAmt = glSumAmt + totalPenal;
                                }
                            }

                            if (i == getAllAccList.size() - 1) {
                                if (glSumAmt > 0) {
                                    recNo = ftsPosting.getRecNo();
                                    Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNO + "'");
                                    Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                    if (updateReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                    }
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + glSumAmt + ",0,8,3,3,'VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                            + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                                    }
                                }
                                if (totalNpaIntAmt > 0) {
                                    if (!oirHead.equals("") && !uriGl.equals("")) {
                                        uriGl = brnCode + uriGl + "01";
                                        oirHead = brnCode + oirHead + "01";

                                        recNo = ftsPosting.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + totalNpaIntAmt + ",0,8,3,3,'Penal Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                        }
                                        recNo = ftsPosting.getRecNo();
                                        insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                + totalNpaIntAmt + ",8,3,3,'Penal Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
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
                                        } else {
                                            insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                                                    + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                                            insertReconBalan = insertReconBalanQuery.executeUpdate();
                                            if (insertReconBalan <= 0) {
                                                throw new ApplicationException("Value does not inserted in gl_recon");
                                            } else {
                                                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '" + brnCode + "'");
                                                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                                                if (updateAccTypeIntPara <= 0) {
                                                    throw new ApplicationException("Value does not updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                                                } else {
                                                    ut.commit();
                                                    return "Penal Interest posted successfully. Batch No. is " + trSNo;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                                            + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan <= 0) {
                                        throw new ApplicationException("Value does not inserted in gl_recon");
                                    } else {
                                        Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '" + brnCode + "'");
                                        Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                                        if (updateAccTypeIntPara <= 0) {
                                            throw new ApplicationException("Value does not updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                                        } else {
                                            ut.commit();
                                            return "Penal Interest posted successfully. Batch No. is " + trSNo;
                                        }
                                    }
                                }
                            }
//                            }
                        }
//                        glAcNO = brnCode + glAcNO + "01";
//                        List chkGlBalList = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + glAcNO + "'").getResultList();
//                        if (chkGlBalList.size() > 0) {
//                            recNo = ftsPosting.getRecNo();
//
//                            Vector chkGlBalVect = (Vector) chkGlBalList.get(0);
//                            double glBalance = Double.parseDouble(chkGlBalVect.get(0).toString()) + totalGlPenal;
//                            Integer updateGlBalanQry = em.createNativeQuery("UPDATE  reconbalan SET BALANCE=" + glBalance + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + glAcNO + "'").executeUpdate();
//                            if (updateGlBalanQry <= 0) {
//                                throw new ApplicationException("Balance updation problem in reconbalan table");
//                            }
//                            Integer insertGLRecon = em.createNativeQuery("INSERT into gl_recon (ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
//                                    + "('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'"+toDt+"','" + totalGlPenal + "',0,8,3,3,'VCH$ As Penal Int. Amt UP TO " + till + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
//                            if (insertGLRecon <= 0) {
//                                throw new ApplicationException("Balance does not inserted in Gl Head.");
//                            } else {
//                                Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
//                                        + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
//                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
//                                if (insertReconBalan <= 0) {
//                                    throw new ApplicationException("Value does not inserted in gl_recon");
//                                } else {
//                                    Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '" + brnCode + "'");
//                                    Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
//                                    if (updateAccTypeIntPara <= 0) {
//                                        throw new ApplicationException("Value does not updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
//                                    } else {
//                                        ut.commit();
//                                        return "Penal Interest posted successfully. Batch No. is " + trSNo;
//                                    }
//                                }
//                            }
//                        } else {
//                            throw new ApplicationException("No information about balance for account number " + glAcNO);
//                        }
                    } else {
                        throw new ApplicationException("Please check all the '" + acType + "' account is exist in cbs_loan_acc_mast_sec Table");
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'EMI PENAL INT' and actype = '" + acType + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                    if (parameterInfo.size() > 0) {
                        throw new ApplicationException("Interest already posted");
                    }
                    if (penalExcludeNpa.equalsIgnoreCase("Y")) {
                        npaAcExcludeQuery = " and accStatus not in ('11','12','13') ";
                        npaExcludeQuery = "and a.accStatus not in ('11','12','13') ";
                    }

                    List getAllAccList, checkInSecOfLoan;
                    if (penalOnOnlyNpa.equalsIgnoreCase("Y")) {
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + " subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "'").getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where subString(acno,3,2)  = '" + acType + "' and accStatus in ('11','12','13') and CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO  and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "'").getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus in ('11','12','13') and a.CurBrCode = '" + brnCode + "')b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

                    } else {                       
//                        getAllAccList = em.createNativeQuery("select distinct acno from accountmaster where "
//                                + " acno in (select distinct acno from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
//                                + " Union All  "
//                                + " select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) "
//                                + " and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "'" + npaAcExcludeQuery).getResultList();

                        getAllAccList = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct acno from accountmaster where "
                                + "acno in (select distinct acno from loan_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno "
                                + "Union All "
                                + "select distinct acno from npa_recon where  substring(acno,3,2) = '" + acType + "'  and auth = 'Y' group by acno ) "
                                + "and  subString(acno,3,2)  = '" + acType + "' and accStatus <> 9 and CurBrCode = '" + brnCode + "'" + npaAcExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                        checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                                + " where a.acno = b.ACNO  and a.acno in (select distinct c.acno from loan_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
//                                + " Union All  "
//                                + " select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno )  and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'" + npaExcludeQuery).getResultList();

                        checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                                + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                                + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                                + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                                + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                                + "where a.acno = b.ACNO and a.acno in (select distinct c.acno from loan_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno "
                                + "Union All "
                                + "select distinct c.acno from npa_recon c where  substring(c.acno,3,2) = '" + acType + "'  and c.auth = 'Y' group by c.acno ) and subString(a.acno,3,2)  = '" + acType + "' and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' " + npaExcludeQuery + ")b "
                                + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();
                    }

                    if (getAllAccList.size() == checkInSecOfLoan.size()) {
                        double glSumAmt = 0f;
                        float recNo;
                        float trSNo = ftsPosting.getTrsNo();
                        String till = mmmdy.format(toDate);
                        for (int i = 0; i < getAllAccList.size(); i++) {
                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                            acNo = (String) getAllAccVect.get(0);
                            repaymentPenalAmt = 0d;
                            securityPenalAmt = 0d;
                            stockPenalAmt = 0d;
                            odPenalAmt = 0;
                            List SecDetailsList = em.createNativeQuery("SELECT A.SCHEME_CODE  from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
                            if (SecDetailsList.isEmpty()) {
                                throw new ApplicationException("Account no does not exists in secondary details table of loan.");
                            } else {
                                for (int k = 0; k < SecDetailsList.size(); k++) {
                                    Vector SecDetailsVect = (Vector) SecDetailsList.get(k);
                                    schemeCode = (String) SecDetailsVect.get(0);
                                }
                            }
                            List flowDetailList = em.createNativeQuery("select PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();

                            String penalIntDemFlowId = null;
                            if (flowDetailList.isEmpty()) {
                                throw new ApplicationException("Flow Id Does Not Exists in Scheme Master.");
                            } else {
                                for (int l = 0; l < flowDetailList.size(); l++) {
                                    Vector flowDetailVect = (Vector) flowDetailList.get(l);
                                    penalIntDemFlowId = flowDetailVect.get(0).toString();
                                }
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                if (emiPenalExist.equalsIgnoreCase("Y")) {
                                    repaymentPenalList = repaymentPenalCalculationOnEMI(acNo, fromDt, toDt, brnCode);
                                    repaymentPenalAmt = repaymentPenalList.get(0).getTotalInt();
                                    roi = repaymentPenalList.get(0).getRoi();
                                }
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                if (odPenalExist.equalsIgnoreCase("Y")) {
                                    odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                                    odPenalAmt = odPenalList.get(0).getTotalInt();
                                    roi = odPenalList.get(0).getRoi();
                                }
                            }
                            if (securityPenalExist.equalsIgnoreCase("Y")) {
                                securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                                securityPenalAmt = securityPenalList.get(0).getTotalInt();
                                roi = securityPenalList.get(0).getRoi();
                            }

                            totalPenal = repaymentPenalAmt + securityPenalAmt + odPenalAmt;
                            Integer insertLaRecon = null;
                            if (totalPenal > 0) {
                                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "' and accstatus <> 9").getResultList();
                                Vector acStatusVect = (Vector) acStatusList.get(0);
                                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {
                                    recNo = ftsPosting.getRecNo();
                                    if (repaymentPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                                + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                                + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + repaymentPenalAmt + ",0,8,'EMI PENAL INT UP TO " + till + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + repaymentPenalAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                                + brnCode + "')").executeUpdate();
                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }

                                    if (securityPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                                + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                                + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + securityPenalAmt + ",0,8,''SECURITY PENAL INT UP TO " + till + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + securityPenalAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                                + brnCode + "')").executeUpdate();
                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }

                                    if (odPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                                + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                                + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + odPenalAmt + ",0,8,'OVERLIMIT PENAL INT UP TO " + till + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
                                                + trSNo + "," + recNo + ",0,3," + odPenalAmt + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                                + brnCode + "')").executeUpdate();
                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("insertion problem in Ca recon table");
                                        }
                                    }
//                                    Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
//                                            + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
//                                            + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
//                                            + totalPenal + ",0,8,'INTEREST UP TO " + mdy.format(ymmd.parse(toDt)) + " @" + roi + "%" + "',1,'" + authBy + "','system','Y',3,"
//                                            + trSNo + "," + recNo + ",0,3," + totalPenal + "," + acStatus + ",NOW(),'" + brnCode + "','"
//                                            + brnCode + "')");
//
//                                    Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
//                                    if (insertNpaRecon == 0) {
//                                        throw new ApplicationException("Value not inserted in  npa_recon");
//                                    }
                                    /**
                                     * * Added For NPA Accounts**
                                     */
                                    totalNpaIntAmt = totalNpaIntAmt + totalPenal;

                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','P')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                    /**
                                     * * Addition End Here **
                                     */
                                } else {
                                    recNo = ftsPosting.getRecNo();
                                    if (repaymentPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "'," + repaymentPenalAmt + ",0,8,3,3,'EMI PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("Insertion problem in Loan Recon table");
                                        }
                                    }
                                    if (securityPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + securityPenalAmt + "',0,8,3,3,'SECURITY PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("Insertion problem in Loan Recon table");
                                        }
                                    }
                                    if (odPenalAmt > 0) {
                                        recNo = ftsPosting.getRecNo();
                                        insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                                + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + odPenalAmt + "',0,8,3,3,'OVERLIMIT PENAL INT UP TO " + till + " @" + roi + "%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                        if (insertLaRecon <= 0) {
                                            throw new ApplicationException("Insertion problem in Loan Recon table");
                                        }
                                    }
                                    List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + acNo + "'").getResultList();

                                    if (chkBalan.size() > 0) {
                                        Query updateCaReconQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE = BALANCE - " + totalPenal
                                                + " WHERE Acno = '" + acNo + "'");

                                        Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                        if (updateCaRecon == 0) {
                                            throw new ApplicationException("Value not updated in  reconbalan");
                                        }
                                    }
                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    } else {
                                        List dmdSchNoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                        Vector dmdSchNoVect = (Vector) dmdSchNoList.get(0);
                                        int dmdSchNo = Integer.parseInt(dmdSchNoVect.get(0).toString());

                                        List srNoList = em.createNativeQuery("select IFNULL(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                        Vector srNoVect = (Vector) srNoList.get(0);
                                        int srNo = Integer.parseInt(srNoVect.get(0).toString());
                                        String overDudt = dateAdd(toDt, 1);
                                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, LD_FREQ_TYPE, DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, LCHG_USER_ID, LCHG_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG)"
                                                + " values('" + acNo + "'," + dmdSchNo + ",'" + penalIntDemFlowId + "','" + toDt + "'," + srNo + ",'N', '" + freq + "','" + toDt + "', '" + overDudt + "'," + totalPenal + ", '" + authBy + "',NOW(), " + totalPenal + ",0,'N')");
                                        Integer insertQry = insertQuery.executeUpdate();
                                        if (insertQry <= 0) {
                                            throw new ApplicationException("Insertion problem in Loan Demand Table");
                                        }
                                    }
                                    glSumAmt = glSumAmt + totalPenal;
                                }
                            }

                            if (i == getAllAccList.size() - 1) {
                                if (glSumAmt > 0) {
                                    recNo = ftsPosting.getRecNo();
                                    Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNO + "'");
                                    Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                    if (updateReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                    }
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + glSumAmt + ",0,8,3,3,'PENAL INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                            + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't inserted in gl_recon");
                                    }
                                }
                                if (totalNpaIntAmt > 0) {
                                    if (!oirHead.equals("") && !uriGl.equals("")) {
                                        uriGl = brnCode + uriGl + "01";
                                        oirHead = brnCode + oirHead + "01";

                                        recNo = ftsPosting.getRecNo();
                                        Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                                + totalNpaIntAmt + ",0,8,3,3,'Penal Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
                                                + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                        Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                        if (insertReconBalan == 0) {
                                            throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                        }
                                        recNo = ftsPosting.getRecNo();
                                        insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                + totalNpaIntAmt + ",8,3,3,'Penal Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType + "','system','Y','" + authBy
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
                                        } else {
                                            insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                                                    + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','EMI PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                                            insertReconBalan = insertReconBalanQuery.executeUpdate();
                                            if (insertReconBalan <= 0) {
                                                throw new ApplicationException("Value does not inserted in gl_recon");
                                            } else {
                                                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '" + brnCode + "'");
                                                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                                                if (updateAccTypeIntPara <= 0) {
                                                    throw new ApplicationException("Value does not updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                                                } else {
                                                    ut.commit();
                                                    return "Penal Interest posted successfully. Batch No. is " + trSNo;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                                            + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','EMI PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan <= 0) {
                                        throw new ApplicationException("Value does not inserted in gl_recon");
                                    } else {
                                        Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '" + brnCode + "'");
                                        Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                                        if (updateAccTypeIntPara <= 0) {
                                            throw new ApplicationException("Value does not updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                                        } else {
                                            ut.commit();
                                            return "Penal Interest posted successfully. Batch No. is " + trSNo;
                                        }
                                    }
                                }
                            }
//                            if (repaymentPenalAmt > 0 || securityPenalAmt > 0 || odPenalAmt > 0) {
//                                if (repaymentPenalAmt > 0) {
//                                    recNo = ftsPosting.getRecNo();
//                                    insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
//                                            + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'"+toDt+"'," + repaymentPenalAmt + ",0,8,3,3,'EMI PENAL INT UP TO " + till + " @"+roi+"%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
//
//                                    if (insertLaRecon <= 0) {
//                                        throw new ApplicationException("Insertion problem in Loan Recon table");
//                                    }
//                                }
//                                if (securityPenalAmt > 0) {
//                                    recNo = ftsPosting.getRecNo();
//                                    insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
//                                            + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'"+toDt+"','" + securityPenalAmt + "',0,8,3,3,'SECURITY PENAL INT UP TO " + till + " @"+roi+"%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
//
//                                    if (insertLaRecon <= 0) {
//                                        throw new ApplicationException("Insertion problem in Loan Recon table");
//                                    }
//                                }
//                                if (odPenalAmt > 0) {
//                                    recNo = ftsPosting.getRecNo();
//                                    insertLaRecon = em.createNativeQuery("INSERT into loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
//                                            + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'"+toDt+"','" + odPenalAmt + "',0,8,3,3,'OVERLIMIT PENAL INT UP TO " + till + " @"+roi+"%','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
//
//                                    if (insertLaRecon <= 0) {
//                                        throw new ApplicationException("Insertion problem in Loan Recon table");
//                                    }
//                                }
//
//                                if (insertLaRecon > 0) {
//                                    List chkBalList = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + acNo + "'").getResultList();
//                                    if (chkBalList.size() > 0) {
//                                        Vector chkBalVect = (Vector) chkBalList.get(0);
//                                        double balance = Double.parseDouble(chkBalVect.get(0).toString()) - totalPenal;
//                                        Integer updateBalanQry = em.createNativeQuery("UPDATE  reconbalan SET BALANCE=" + balance + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + acNo + "'").executeUpdate();
//                                        if (updateBalanQry > 0) {
//                                            List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
//                                            Vector sNoVect = (Vector) sNoList.get(0);
//                                            int sNo = Integer.parseInt(sNoVect.get(0).toString());
//                                            Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','P')");
//                                            Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
//                                            if (updateIntPostAcWise <= 0) {
//                                                throw new ApplicationException("Penal not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
//                                            } else {
//                                                List dmdSchNoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
//                                                Vector dmdSchNoVect = (Vector) dmdSchNoList.get(0);
//                                                int dmdSchNo = Integer.parseInt(dmdSchNoVect.get(0).toString());
//
//                                                List srNoList = em.createNativeQuery("select IFNULL(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
//                                                Vector srNoVect = (Vector) srNoList.get(0);
//                                                int srNo = Integer.parseInt(srNoVect.get(0).toString());
//                                                String overDudt = dateAdd(toDt, 1);
//                                                Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, LD_FREQ_TYPE, DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, LCHG_USER_ID, LCHG_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG)"
//                                                        + " values('" + acNo + "'," + dmdSchNo + ",'" + penalIntDemFlowId + "','" + toDt + "'," + srNo + ",'N', '" + freq + "','" + toDt + "', '" + overDudt + "'," + totalPenal + ", '" + authBy + "',NOW(), " + totalPenal + ",0,'N')");
//                                                Integer insertQry = insertQuery.executeUpdate();
//                                                if (insertQry <= 0) {
//                                                    throw new ApplicationException("Insertion problem in Loan Demand Table");
//                                                }
////                                                else {
////                                                    ut.commit();
////                                                    return "Penal Interest posted successfully. Batch No. is " + trSNo;
////                                                }
//                                                totalGlPenal = totalGlPenal + totalPenal;
//                                            }
//
//                                        } else {
//                                            throw new ApplicationException("Balance does not update.");
//                                        }
//                                    } else {
//                                        throw new ApplicationException("Account does not update.");
//                                    }
//                                } else {
//                                    throw new ApplicationException("Value is not inserted in loan_Recon.");
//                                }
//                            }
                        }
//                        glAcNO = brnCode + glAcNO + "01";
//                        List chkGlBalList = em.createNativeQuery("SELECT BALANCE FROM  reconbalan WHERE ACNO='" + glAcNO + "'").getResultList();
//                        if (chkGlBalList.size() > 0) {
//                            recNo = ftsPosting.getRecNo();
//
//                            Vector chkGlBalVect = (Vector) chkGlBalList.get(0);
//                            double glBalance = Double.parseDouble(chkGlBalVect.get(0).toString()) + totalGlPenal;
//                            Integer updateGlBalanQry = em.createNativeQuery("UPDATE  reconbalan SET BALANCE=" + glBalance + ",DT=DATE_FORMAT(CURDATE(),'%Y%m%d') WHERE ACNO='" + glAcNO + "'").executeUpdate();
//                            if (updateGlBalanQry <= 0) {
//                                throw new ApplicationException("Balance updation problem in reconbalan table");
//                            }
//                            Integer insertGLRecon = em.createNativeQuery("INSERT into gl_recon (ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
//                                    + "('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'"+toDt+"','" + totalGlPenal + "',0,8,3,3,'VCH$ As Penal Int. Amt UP TO " + till + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();
//                            if (insertGLRecon <= 0) {
//                                throw new ApplicationException("Balance does not inserted in Gl Head.");
//                            } else {
//                                Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
//                                        + " VALUES('" + acType + "','" + fromDt + "','" + toDt + "','EMI PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
//                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
//                                if (insertReconBalan <= 0) {
//                                    throw new ApplicationException("Value doesn't inserted in gl_recon");
//                                } else {
//                                    Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '"+brnCode+"'");
//                                    Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
//                                    if (updateAccTypeIntPara <= 0) {
//                                        throw new ApplicationException("Value does not updated in cbs_loan_acctype_interest_parameter");
//                                    } else {
//                                        ut.commit();
//                                        return "Penal Interest posted successfully. Batch No. is " + trSNo;
//                                    }
//                                }
//                            }
//                        } else {
//                            throw new ApplicationException("No information about account balance");
//                        }
                    } else {
                        throw new ApplicationException("Please check all the '" + acType + "' account is exists in cbs_loan_acc_mast_sec Table");
                    }
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                e.printStackTrace();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "Penal Interest posted successfully";
    }

    public List<LoanIntCalcList> repaymentPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        try {
            List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            String intTableCode = "";
            String firstDt = "";
            String lastDt = "";
            Date dmdAdjDt = null;
            Date dmdEffDt = null;
            double dmdAdjAmt = 0;
            double totalAmt = 0;
            double penalAmt = 0;
            double dmdAmt = 0;
            double overFlowAmt = 0;
            double closingBal = 0;
            double roi = 0;
            double product = 0;
            double penal = 0;

            List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE from cbs_loan_acc_mast_sec A, loan_appparameter B "
                    + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = SecDetailsVect.get(0).toString();
                }
            }
            String custName = null;
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }

            /**
             * Getting the Actual Demand Posted in Table
             */
            List getDmdList = em.createNativeQuery("select ACNO,SHDL_NUM,DMD_FLOW_ID,DMD_SRL_NUM,DMD_EFF_DATE,DMD_OVDU_DATE,DMD_AMT from "
                    + "cbs_loan_dmd_table  where acno = '" + acNo + "' and DMD_EFF_DATE between '" + fromDt + "' and '" + toDt
                    + "' and DEL_FLG = 'N' order by acno, DMD_EFF_DATE").getResultList();

            if (getDmdList.size() > 0) {
                for (int i = 0; i < getDmdList.size(); i++) {
                    try {
                        Vector getDmdVect = (Vector) getDmdList.get(i);
                        String dmdAcNo = getDmdVect.get(0).toString();
                        int dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                        String dmdFlowId = getDmdVect.get(2).toString();
                        int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());
                        dmdEffDt = ymd.parse(getDmdVect.get(4).toString());
                        dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());

                        List getLoanAdditionalTrList = em.createNativeQuery("select OVERFLOW_AMT from cbs_la_additional_tr_details  where acno = '"
                                + dmdAcNo + "' and REPAY_SCH_NO = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and INT_ROUTING_FLAG = 'L' and "
                                + "TR_REVERSAL_FLAG = 'N'").getResultList();

                        if (getLoanAdditionalTrList.size() > 0) {
                            Vector getLoanAdditionalTrVect = (Vector) getLoanAdditionalTrList.get(i);
                            overFlowAmt = Double.parseDouble(getLoanAdditionalTrVect.get(1).toString());
                        } else {
                            overFlowAmt = 0f;
                        }
                        /* Getting the Demand Received data from Demand Satisfication table*/
                        List getDmdAdjList = em.createNativeQuery("select ADJ_DATE,ADJ_AMT from cbs_loan_dmd_adj_table  where acno = '" + dmdAcNo
                                + "' and SHDL_NUM = " + dmdSchNo + " and  DMD_FLOW_ID = '" + dmdFlowId + "' and DMD_SRL_NUM = " + dmdSrNo
                                + " and DEL_FLG = 'N'").getResultList();

                        if (getDmdAdjList.size() > 0) {
                            for (int j = 0; j <= getDmdAdjList.size(); j++) {
                                if (j == getDmdAdjList.size() - 1) {

                                    Vector getDmdAdjVect = (Vector) getDmdAdjList.get(j);
                                    dmdAdjDt = ymd.parse(getDmdAdjVect.get(0).toString());
                                    dmdAdjAmt = Double.parseDouble(getDmdAdjVect.get(1).toString());
                                    totalAmt = dmdAmt - dmdAdjAmt - overFlowAmt;
                                    if (dmdAdjDt.before(dmdEffDt) && totalAmt > 0) {
                                        firstDt = dmy.format(dmdEffDt);
                                        dmdAdjDt = ymd.parse(toDt);
                                        String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(dmdAdjDt));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            it.setFlag("false;" + intRoi);
                                            intDetails.add(it);
                                            return intDetails;
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }

                                        Long day = CbsUtil.dayDiff(dmdAdjDt, dmdEffDt);//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                        int noOfDays = day.intValue() + 1;

                                        penalAmt = roi * noOfDays * totalAmt / 36500;
                                        product = product + dmdAmt;
                                        penal = penal + penalAmt;
                                        j++;
                                    } else if (dmdAdjDt.equals(dmdEffDt) && totalAmt > 0) {
                                        firstDt = dmy.format(dmdEffDt);
                                        dmdAdjDt = ymd.parse(toDt);
                                        String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(dmdAdjDt));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            it.setFlag("false;" + intRoi);
                                            intDetails.add(it);
                                            //System.out.println("intDeatils Size in EJB::>>" + intDetails.size());
                                            return intDetails;
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }

                                        Long day = CbsUtil.dayDiff(dmdAdjDt, dmdEffDt);//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                        int noOfDays = day.intValue() + 1;
                                        penalAmt = roi * noOfDays * totalAmt / 36500;
                                        product = product + dmdAmt;
                                        penal = penal + penalAmt;
                                        j++;
                                    } else if (dmdAdjDt.after(dmdEffDt) && totalAmt > 0) {
                                        firstDt = dmy.format(dmdEffDt);
                                        String intRoi = getPenalROI(intTableCode, (dmdAmt - overFlowAmt), ymd.format(dmdAdjDt));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            it.setFlag("false;" + intRoi);
                                            intDetails.add(it);
                                            //System.out.println("intDeatils Size in EJB::>>" + intDetails.size());
                                            return intDetails;
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }
                                        Long day = CbsUtil.dayDiff(dmdAdjDt, dmdEffDt);//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                        int noOfDays = day.intValue() + 1;
                                        penalAmt = roi * noOfDays * (dmdAmt - overFlowAmt) / 36500;
                                        product = product + dmdAmt;
                                        penal = penal + penalAmt;
                                    }
                                } else {
                                    if (dmdAdjDt.before(ymd.parse(toDt))) {
                                        lastDt = dmy.format(ymd.parse(toDt));
                                        String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymd.parse(toDt)));
                                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                            it.setFlag("false;" + intRoi);
                                            intDetails.add(it);
                                            return intDetails;
                                        } else {
                                            roi = Double.parseDouble(intRoi);
                                        }
                                        Long day = CbsUtil.dayDiff(ymd.parse(toDt), dmdAdjDt);//(ymd.parse(toDt).getTime() - dmdAdjDt.getTime()) / (24 * 60 * 60 * 1000);
                                        int noOfDays = day.intValue() + 1;
                                        penalAmt = roi * noOfDays * totalAmt / 36500;
                                        product = product + totalAmt;
                                        penal = penal + penalAmt;
                                    }
                                }
                            }
                        } else {
                            firstDt = dmy.format(dmdEffDt);
                            lastDt = dmy.format(ymd.parse(toDt));
                            dmdAdjDt = ymd.parse(toDt);
                            dmdAdjAmt = 0f;
                            totalAmt = dmdAmt - dmdAdjAmt - overFlowAmt;
                            String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymd.parse(toDt)));
                            if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                throw new ApplicationException("Roi does not exist");
                            } else {
                                roi = Double.parseDouble(intRoi);
                            }

                            Long day = CbsUtil.dayDiff(ymd.parse(toDt), dmdAdjDt);// / (24 * 60 * 60 * 1000);
                            int noOfDays = day.intValue() + 1;

                            penalAmt = roi * noOfDays * totalAmt / 36500;
                            product = product + totalAmt;
                            penal = penal + penalAmt;
                        }
                    } catch (ParseException ex) {
                        throw new ApplicationException(ex.getMessage());
                    }
                }
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penal)))));
                //it.setTotalInt(totalInt);
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);

            } else {
                //NumberFormat formatter = new DecimalFormat("#0.00");
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penal)))));
                //it.setTotalInt(totalInt);
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);
            }
            return intDetails;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanIntCalcList> repaymentPenalCalculationOnEMI(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        try {
            List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            String intTableCode = "", schemeCode = "", lateFeeApp = "Y";
            Integer gracePdMonth = 0, gracePdDays = 0;
            String firstDt = dmy.format(ymmd.parse(fromDt));
            String lastDt = dmy.format(ymmd.parse(toDt));
            Date dmdAdjDt = null;
            Date dmdEffDt = null;
            double dmdAdjAmt = 0;
            double totalAmt = 0;
            double penalAmt = 0;
            double dmdAmt = 0;
            double overFlowAmt = 0;
            double closingBal = 0;
            double roi = 0;
            double product = 0;
            double penal = 0;

            List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE, A.SCHEME_CODE  from cbs_loan_acc_mast_sec A, loan_appparameter B "
                    + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = SecDetailsVect.get(0).toString();
                    schemeCode = SecDetailsVect.get(1).toString();
                }
            }
            System.out.println("scheme_code:" + schemeCode + "; Acno:" + acNo);
            List emiLateApplicableList = em.createNativeQuery("select * from cbs_scheme_loan_interest_details where scheme_code='" + schemeCode + "'").getResultList();
            if (emiLateApplicableList.isEmpty()) {
                throw new ApplicationException("Scheme Code: " + schemeCode + " does  not exist in cbs_scheme_loan_interest_details ");
            } else {
                emiLateApplicableList = em.createNativeQuery("select apply_late_fee_for_delayed_payment, grace_period_for_late_fee_months, "
                        + " grace_period_for_late_fee_days from cbs_scheme_loan_interest_details where scheme_code='" + schemeCode + "'").getResultList();
                if (SecDetailsList.isEmpty()) {
                    throw new ApplicationException("Scheme Code: " + schemeCode + " does  not exist in cbs_scheme_loan_interest_details ");
                } else {
                    for (int i = 0; i < SecDetailsList.size(); i++) {
                        Vector emiLateApplicableVect = (Vector) emiLateApplicableList.get(i);
                        lateFeeApp = emiLateApplicableVect.get(0).toString();
                        gracePdMonth = Integer.parseInt(emiLateApplicableVect.get(1).toString());
                        gracePdDays = Integer.parseInt(emiLateApplicableVect.get(2).toString());
                    }
                }
            }

            String custName = null;
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }

            /**
             * Getting the Actual Demand Posted in Table
             */
            List getNonPaidList = em.createNativeQuery("select DATE_FORMAT(ifnull(duedt,'" + ymmd.format(ymmd.parse(toDt)) + "'), '%Y%m%d') as duedt, DATE_FORMAT(ifnull(paymentdt,'" + ymmd.format(ymmd.parse(toDt)) + "'), '%Y%m%d') as paymentdt, installamt, status, excessamt from emidetails where "
                    + " acno='" + acNo + "' and status = 'unpaid' and DATE_FORMAT(duedt, '%Y%m%d')<='" + toDt + "' "
                    //                    + " and paymentdt is null "
                    //                    + " union all "
                    //                    + " select duedt, paymentdt, installamt, status, excessamt from emidetails where acno='" + acNo + "' "
                    //                    + " and status = 'unpaid' and duedt<='" + toDt+"' ' and paymentdt is not null "
                    + " union all "
                    + " select DATE_FORMAT(ifnull(duedt,'" + ymmd.format(ymmd.parse(toDt)) + "'), '%Y%m%d') as duedt, DATE_FORMAT(ifnull(paymentdt,'" + ymmd.format(ymmd.parse(toDt)) + "'), '%Y%m%d') as paymentdt, installamt, status, excessamt from emidetails where acno='" + acNo + "' "
                    + " and status = 'paid' and DATE_FORMAT(duedt, '%Y%m%d')<='" + toDt + "'  and DATE_FORMAT(paymentdt, '%Y%m%d') between '" + fromDt + "' and '" + toDt + "' "
                    + " order by duedt ").getResultList();

            if (getNonPaidList.size() > 0) {
                for (int i = 0; i < getNonPaidList.size(); i++) {
                    try {
                        Vector getDmdVect = (Vector) getNonPaidList.get(i);
                        String dueDt = getDmdVect.get(0).toString();
                        String pymtDt = getDmdVect.get(1).toString();
                        double installmentAmt = Double.parseDouble(getDmdVect.get(2).toString());
                        String status = getDmdVect.get(3).toString();
                        double excessAmt = Double.parseDouble(getDmdVect.get(4).toString());
                        System.out.println("acno:" + acNo + "; dueDt:" + dueDt + "; pymtDt:" + pymtDt);
                        if (status.equalsIgnoreCase("paid")) {
                            if (ymmd.parse(pymtDt).after(ymmd.parse(dueDt))) {
                                totalAmt = installmentAmt;
                                String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymmd.parse(pymtDt)));
                                if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                    it.setFlag("false;" + intRoi);
                                    intDetails.add(it);
                                    return intDetails;
                                } else {
                                    roi = Double.parseDouble(intRoi);
                                }
                                if (lateFeeApp.equalsIgnoreCase("Y")) {
                                    dueDt = CbsUtil.monthAdd(ymmd.format(ymmd.parse(dueDt)), gracePdMonth);
                                    dueDt = CbsUtil.dateAdd(dueDt, gracePdDays);

                                } else {
                                    dueDt = ymmd.format(ymmd.parse(dueDt));
                                }
//                                Long day = CbsUtil.dayDiff(ymd.parse(dueDt), ymd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                Long day = CbsUtil.dayDiff(ymmd.parse(dueDt).before(ymmd.parse(fromDt)) ? ymmd.parse(fromDt) : ymmd.parse(dueDt), ymmd.parse(pymtDt).after(ymmd.parse(toDt)) ? ymmd.parse(toDt) : ymmd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                                if (day > 0) {
                                    int noOfDays = day.intValue() + 1;

                                    penalAmt = roi * noOfDays * totalAmt / 36500;
                                    product = product + noOfDays * totalAmt;
                                    penal = penal + penalAmt;
                                }
                            }
                        } else {
                            totalAmt = installmentAmt;
                            String intRoi = getPenalROI(intTableCode, totalAmt, ymd.format(ymmd.parse(pymtDt)));
                            if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                it.setFlag("false;" + intRoi);
                                intDetails.add(it);
                                return intDetails;
                            } else {
                                roi = Double.parseDouble(intRoi);
                            }

                            if (lateFeeApp.equalsIgnoreCase("Y")) {
                                dueDt = CbsUtil.monthAdd(ymmd.format(ymmd.parse(dueDt)), gracePdMonth);
                                dueDt = CbsUtil.dateAdd(dueDt, gracePdDays);

                            } else {
                                dueDt = ymmd.format(ymmd.parse(dueDt));
                            }
//                                Long day = CbsUtil.dayDiff(ymd.parse(dueDt), ymd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);
                            Long day = CbsUtil.dayDiff(ymmd.parse(dueDt).before(ymmd.parse(fromDt)) ? ymmd.parse(fromDt) : ymmd.parse(dueDt), ymmd.parse(pymtDt).after(ymmd.parse(toDt)) ? ymmd.parse(toDt) : ymmd.parse(pymtDt));//(dmdAdjDt.getTime() - dmdEffDt.getTime()) / (24 * 60 * 60 * 1000);

                            if (day > 0) {
                                int noOfDays = day.intValue() + 1;

                                penalAmt = roi * noOfDays * totalAmt / 36500;
                                product = product + noOfDays * totalAmt;
                                penal = penal + penalAmt;
                            }
                        }
                    } catch (ParseException ex) {
                        throw new ApplicationException(ex.getMessage());
                    }
                }
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penal)))));
                //it.setTotalInt(totalInt);
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);

            } else {
                //NumberFormat formatter = new DecimalFormat("#0.00");
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(Math.round(penal)))));
                //it.setTotalInt(totalInt);
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Repayment penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);
            }
            return intDetails;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    //********************* GET LATEST PENAL ROI ************************//
    public String getPenalROI(String intTableCode, double amt, String date) throws ApplicationException {
        String result = "";
        int intVerNo;
        String intMastTblCod;
        double roi;
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,"
                    + "INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode
                    + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,"
                    + "INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode
                    + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(3);
            } else if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(3);
            } else {
                result = "Data does not exists";
                return result;
            }

            double intPerDr;
            double intPerCr;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from "
                    + "cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from "
                    + "cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntMastList.isEmpty()) {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());
            } else if (!laIntMastHistList.isEmpty()) {
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Double.parseDouble(laIntMastHistVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastHistVect.get(1).toString());
            } else {
                result = "Data does not exists";
                return result;
            }

            String nrIntIndi;
            double nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT PEENAL_INTEREST_INDICATOR, PEENAL_INTEREST_PERCENTAGE from "
                    + "cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between "
                    + " BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            List laIntSlabMastHistList = em.createNativeQuery("SELECT PEENAL_INTEREST_INDICATOR, PEENAL_INTEREST_PERCENTAGE from "
                    + "cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  "
                    + "BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (!laIntSlabMastList.isEmpty()) {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
            } else if (!laIntSlabMastHistList.isEmpty()) {
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
            } else {
                result = "Data does not exists";
                return result;
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                roi = nrIntPer + intPerDr - intPerCr;
                result = new Double(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = (double) 0.0;
                result = new Double(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
                roi = nrIntPer;
                result = new Double(roi).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List<LoanIntCalcList> odPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        /*Applicatble on all A/c, which nature belongs to TL/DL as well as CA Nature.*/
        try {
            List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            String intTableCode = "";
            String acNature = "";
            String firstDt = "";
            String lastDt = "";
            String intAppFreq = "";
            double totalPenalAmt = 0f;
            double subsidyAmt = 0;
            String subsidyExpDt = "";

            String custName = null;
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for account number " + acNo);
            }
            List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE, A.INT_APP_FREQ, IFNULL(B.SUBSIDYAMT,0), IFNULL(B.SUBSIDYEXPDT,'') from cbs_loan_acc_mast_sec A, loan_appparameter B "
                    + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();

            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account No Does Not Exists in Secondary Details table of Loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = (String) SecDetailsVect.get(0);
                    intAppFreq = (String) SecDetailsVect.get(1);
                    subsidyAmt = Double.parseDouble(SecDetailsVect.get(2).toString());
                    subsidyExpDt = SecDetailsVect.get(3).toString();
                }
            }
            acNature = ftsPosting.getAccountNature(acNo);
            String[][] b = loanInterestCalculationBean.createFromDtArray(acNo, fromDt, toDt, intAppFreq, acNature, intTableCode, "N", "Y");
            double matValue = 0;
            double margine;
            double dpLimit = 0;
            double totalDpLimit = 0;
            double closingBal = 0;
            double roi = 0;
            double outstanding = 0, adhocLimit = 0;
            int noOfDays = 0;

            for (int j = 0; j < b.length - 1; j++) {
                matValue = 0;
                margine = 0;
                dpLimit = 0;
                totalDpLimit = 0;
                closingBal = 0;
                roi = 0;
                outstanding = 0;
                adhocLimit = 0;
                noOfDays = 0;
                String fDate = b[j][0].toString();
                String tDate = b[j][1].toString();
                List margineList = em.createNativeQuery("select ifnull(acLimit,0), IFNULL(Adhocapplicabledt,'1900-01-01'), IFNULL(adhoctilldt,'1900-01-01'), ifnull(Adhoclimit,0) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + fDate + "' and txnid = (select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + fDate + "')").getResultList();
                if (margineList.isEmpty()) {
                    margineList = em.createNativeQuery("Select IFNULL(odlimit,0), IFNULL(Adhocapplicabledt,'1900-01-01'), IFNULL(AdhocExpiry,'1900-01-01'), ifnull(Adhoclimit,0) From loan_appparameter  Where acno='" + acNo + "' ").getResultList();
                }
//            List margineList = em.createNativeQuery("Select IFNULL(odlimit,0) From loan_appparameter  Where acno='" + acNo + "' ").getResultList();
                if (margineList.size() > 0) {
                    for (int i = 0; i < margineList.size(); i++) {
                        Vector margineVect = (Vector) margineList.get(0);
                        matValue = Double.parseDouble(margineVect.get(0).toString());
                        //margine = Double.parseDouble(margineVect.get(1).toString());
                        if (Double.parseDouble(margineVect.get(3).toString()) > 0) {
                            if ((ymd.parse(fDate).after(ymd.parse(margineVect.get(1).toString()))) && (!ymd.parse(fDate).after(ymd.parse(margineVect.get(2).toString())))) {
                                adhocLimit = Double.parseDouble(margineVect.get(3).toString());
                            }
                        }
                        dpLimit = matValue + adhocLimit;//- (matValue * margine / 100);
                        totalDpLimit = totalDpLimit + dpLimit;
                    }
                } else {
                    dpLimit = 0f;
                    totalDpLimit = totalDpLimit + dpLimit;
                }
                outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, tDate));
                /*
                 * Did the implementation of Subsidy Amount 
                 */
                if ((subsidyAmt != 0)) {
                    if (ymd.parse(subsidyExpDt).after(ymd.parse(fDate)) || ymd.parse(subsidyExpDt).equals(ymd.parse(fDate))) {
                        if (outstanding < 0) {
                            outstanding = outstanding + subsidyAmt;
                        }
                    }
                }
                if (outstanding < 0) {
                    outstanding = outstanding * -1;
                } else {
                    outstanding = outstanding * 1;
                }
                acNature = ftsPosting.getAccountNature(acNo);

//                System.out.println("acno:"+acNo+"; outstanding:"+outstanding+"; ODLimit:"+totalDpLimit);

                if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

//                    System.out.println("acno:"+acNo+";From Date:"+fDate+"; ToDt:"+tDate+"; outstatnding:"+formatter.format(outstanding)+"; dpLimit:"+formatter.format(totalDpLimit)+"; Diff:"+formatter.format((outstanding-totalDpLimit))+"; TotalPenal:"+totalPenalAmt);
                    if (outstanding > totalDpLimit) {
                        try {
                            outstanding = outstanding - totalDpLimit;

                            if (outstanding > 0) {
                                String intRoi = getPenalROI(intTableCode, outstanding, toDt);
                                if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                    throw new ApplicationException("Roi does not exist");
                                } else {
                                    roi = Double.parseDouble(intRoi);
                                }
                            }
                            Date frDt = ymd.parse(fDate);
                            Date tillDt = ymd.parse(tDate);
                            System.out.println("frDt:" + frDt + ";tillDt:" + tillDt);
                            Long day = CbsUtil.dayDiff(frDt, tillDt);//(tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
                            noOfDays = day.intValue() + 1;
                            double penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                            System.out.println(">>>>acno:" + acNo + ";roi" + roi + "; noOfDays:" + noOfDays + "; outstanding:" + outstanding + "; penalAmt:" + penalAmt);
                            totalPenalAmt = totalPenalAmt + penalAmt;


                        } catch (ParseException ex) {
                            Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        outstanding = outstanding;
                        totalPenalAmt = totalPenalAmt;
                    }
//                    System.out.println(">>>>>>>>>>acno:"+acNo+";From Date:"+fDate+"; ToDt:"+tDate+"; outstatnding:"+formatter.format(outstanding)+"; dpLimit:"+formatter.format(totalDpLimit)+"; Diff:"+formatter.format((outstanding-totalDpLimit))+"; TotalPenal:"+totalPenalAmt+"; No of Days:"+noOfDays);
                }
            }
            it.setAcNo(acNo);
            it.setCustName(custName);
            it.setClosingBal(closingBal);
            it.setFirstDt(dmy.format(ymmd.parse(fromDt)));
            it.setLastDt(dmy.format(ymmd.parse(toDt)));
            it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding * noOfDays))));
            it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
            it.setRoi(roi);
            it.setIntTableCode(intTableCode);
            it.setDetails("Over limit penal between " + dmy.format(ymmd.parse(fromDt)) + " to " + dmy.format(ymmd.parse(toDt)));
            intDetails.add(it);


            //        else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            //            List entryDtList = em.createNativeQuery("Select entryDate From loansecurity Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' and EntryDate between '" + fromDt + "' and '" + toDt + "' group by entrydate").getResultList();
            //            try {
            //                String a[][] = new String[entryDtList.size()][2];
            //                ArrayList datesFrom = new ArrayList();
            //                datesFrom.add(ymd.format(ymd.parse(fromDt)));
            //
            //                if (!entryDtList.isEmpty()) {
            //                    for (int i = 0; i < entryDtList.size(); i++) {
            //                        Vector entryDtVect = (Vector) entryDtList.get(i);
            //                        a[i][0] = entryDtVect.get(0).toString();
            //                        if (i == 0) {
            //                            /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
            //                            if (ymmd.parse(fromDt).equals(ymd.parse(entryDtVect.get(0).toString()))) {
            //                                if (i == entryDtList.size() - 1) {
            //                                    a[i][1] = ymd.format(ymmd.parse(toDt));
            //                                    /*=======Getting the ROI Change Date in Previous i Position=======================*/
            //                                    datesFrom.add(a[i][1]);
            //                                }
            //                            } else {
            //                                datesFrom.add(a[i][0]);
            //                                if (i == entryDtList.size() - 1) {
            //                                    a[i][1] = ymd.format(ymmd.parse(toDt));
            //                                    /*=======Getting the ROI Change Date in Previous i Position=======================*/
            //                                    datesFrom.add(a[i][1]);
            //                                }
            //                            }
            //                        } else if (i > 0 && i < entryDtList.size() - 1) {
            //                            a[i - 1][1] = a[i][0];
            //                            /*=======Getting the ROI Change Date in Previous i Position=======================*/
            //                            datesFrom.add(a[i][0]);
            //                        } else if (i == entryDtList.size() - 1) {
            //                            a[i - 1][1] = a[i][0];
            //                            a[i][1] = ymd.format(ymmd.parse(toDt));
            //                            /*=======Getting the ROI Change Date in Previous i Position=======================*/
            //                            datesFrom.add(a[i][0]);
            //                            if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
            //                                /*=======Getting the ROI Change Date in Current i Position=======================*/
            //                                datesFrom.add(a[i][1]);
            //                            }
            //                        }
            //                    }
            //                    Collections.sort(datesFrom);
            //                } else {
            //                    datesFrom.add(ymd.format(ymd.parse(toDt)));
            //                    Collections.sort(datesFrom);
            //                }
            //
            //                String b[][] = new String[datesFrom.size()][2];
            //                if (!datesFrom.isEmpty()) {
            //                    for (int i = 0; i < datesFrom.size(); i++) {
            //                        if (i == 0) {
            //                            b[i][0] = datesFrom.get(i).toString();
            //                            //firstDisbDt = datesFrom.get(i).toString();
            //                        } else if (i > 0 && i < datesFrom.size() - 1) {
            //                            b[i][0] = datesFrom.get(i).toString();
            //                            b[i - 1][1] = ymd.format(ymmd.parse(dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
            //                        } else if (i == datesFrom.size() - 1) {
            //                            b[i - 1][1] = datesFrom.get(i).toString();
            //                        }
            //                    }
            //                }
            //                double product = 0;
            //                for (int i = 0; i < b.length - 1; i++) {
            //                    String fDate = b[i][0].toString();
            //                    String tDate = b[i][1].toString();
            //                    List margineList = em.createNativeQuery("Select ifnull(MatValue,0),ifnull(Margin,0) From loansecurity Where acno='" + acNo + "' and UPPER(status) = 'ACTIVE' and EntryDate between '" + fDate + "' and '" + tDate + "' Order By acno,Sno").getResultList();
            //                    if (margineList.size() > 0) {
            //                        for (int j = 0; j < margineList.size(); j++) {
            //                            Vector margineVect = (Vector) margineList.get(0);
            //                            matValue = Double.parseDouble(margineVect.get(0).toString());
            //                            margine = Double.parseDouble(margineVect.get(1).toString());
            //                            dpLimit = matValue - (matValue * margine / 100);
            //                            totalDpLimit = totalDpLimit + dpLimit;
            //                        }
            //                    } else {
            //                        dpLimit = 0f;
            //                        totalDpLimit = totalDpLimit + dpLimit;
            //                    }
            //                    outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, tDate));
            //                    if (outstanding < 0) {
            //                        outstanding = outstanding * -1;
            //                    } else {
            //                        outstanding = outstanding * 1;
            //                    }
            //                    if (outstanding > totalDpLimit) {
            //                        try {
            //                            outstanding = outstanding - dpLimit;
            //                            if (outstanding > 0) {
            //                                String intRoi = getPenalROI(intTableCode, outstanding, toDt);
            //                                if (intRoi.equalsIgnoreCase("Data does not exists")) {
            //                                    throw new ApplicationException("Roi does not exist");
            //                                } else {
            //                                    roi = Double.parseDouble(intRoi);
            //                                }
            //                            }
            //                            Date frDt = ymd.parse(fDate);
            //                            Date tillDt = ymd.parse(tDate);
            //                            Long day = CbsUtil.dayDiff(frDt,tillDt);//(tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
            //                            int noOfDays = day.intValue() + 1;
            //                            double penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
            //                            totalPenalAmt = totalPenalAmt + penalAmt;
            //                            product = product + outstanding * noOfDays;
            //                            if (i == 0) {
            //                                firstDt = dmy.format(frDt);
            //                            }
            //                            if (i == b.length - 2) {
            //                                lastDt = dmy.format(tillDt);
            //                                closingBal = outstanding;
            //                            }
            //                        } catch (ParseException ex) {
            //                            Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
            //                        }
            //                    }
            //                }
            //
            //                it.setAcNo(acNo);
            //                it.setCustName(custName);
            //                it.setClosingBal(closingBal);
            //                it.setFirstDt(firstDt);
            //                it.setLastDt(lastDt);
            //                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
            //                it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
            //
            //                it.setRoi(roi);
            //                it.setIntTableCode(intTableCode);
            //                it.setDetails("Security penal between " + firstDt + " to " + lastDt);
            //                intDetails.add(it);
            //            } catch (Exception e) {
            //                throw new ApplicationException(e.getMessage());
            //            }
            //        }
            return intDetails;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List<LoanIntCalcList> securityPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        /*Applicatble on all A/c, which nature belongs to TL/DL as well as CA Nature.*/
        List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
        LoanIntCalcList it = new LoanIntCalcList();
        String intTableCode = "";
        String acNature = "";
        String firstDt = "";
        String lastDt = "";
        double totalPenalAmt = 0f;

        String custName = null;
        List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
        if (acExistList.size() > 0) {
            Vector acExistVect = (Vector) acExistList.get(0);
            custName = acExistVect.get(0).toString();
        } else {
            throw new ApplicationException("Custname does not exist for account number " + acNo);
        }
        List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE from cbs_loan_acc_mast_sec A, loan_appparameter B "
                + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();

        if (SecDetailsList.isEmpty()) {
            throw new ApplicationException("Account No Does Not Exists in Secondary Details(cbs_loan_acc_mast_sec) table of Loan for account " + acNo + ".");
        } else {
            for (int i = 0; i < SecDetailsList.size(); i++) {
                Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                intTableCode = (String) SecDetailsVect.get(0);
            }
        }

        double outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, toDt));
        if (outstanding < 0) {
            outstanding = outstanding * -1;
        } else {
            outstanding = outstanding * 1;
        }
        acNature = ftsPosting.getAccountNature(acNo);

        double matValue = 0;
        double margine;
        double dpLimit = 0;
        double totalDpLimit = 0;
        double closingBal = 0;
        double roi = 0;
        if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
            List margineList = em.createNativeQuery("Select IFNULL(MatValue,0),IFNULL(Margin,0) From loansecurity  Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' Order By acno,Sno").getResultList();
            if (margineList.size() > 0) {
                for (int i = 0; i < margineList.size(); i++) {
                    Vector margineVect = (Vector) margineList.get(0);
                    matValue = Double.parseDouble(margineVect.get(0).toString());
                    margine = Double.parseDouble(margineVect.get(1).toString());
                    dpLimit = matValue - (matValue * margine / 100);
                    totalDpLimit = totalDpLimit + dpLimit;
                }
            } else {
                dpLimit = 0f;
                totalDpLimit = totalDpLimit + dpLimit;
            }

            if (outstanding > totalDpLimit) {
                try {
                    outstanding = outstanding - dpLimit;

                    if (outstanding > 0) {
                        String intRoi = getPenalROI(intTableCode, outstanding, toDt);
                        if (intRoi.equalsIgnoreCase("Data does not exists")) {
                            throw new ApplicationException("Roi does not exist");
                        } else {
                            roi = Double.parseDouble(intRoi);
                        }
                    }
                    Date frDt = ymd.parse(fromDt);
                    Date tillDt = ymd.parse(toDt);
                    Long day = CbsUtil.dayDiff(tillDt, frDt);//(tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
                    int noOfDays = day.intValue() + 1;
                    double penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                    totalPenalAmt = totalPenalAmt + penalAmt;

                    it.setAcNo(acNo);
                    it.setCustName(custName);
                    it.setClosingBal(closingBal);
                    it.setFirstDt(dmy.format(frDt));
                    it.setLastDt(dmy.format(tillDt));
                    it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding * noOfDays))));
                    it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                    it.setRoi(roi);
                    it.setIntTableCode(intTableCode);
                    it.setDetails("Security penal between " + dmy.format(frDt) + " to " + dmy.format(tillDt));
                    intDetails.add(it);
                } catch (ParseException ex) {
                    Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(fromDt);
                it.setLastDt(toDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(outstanding))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Security penal between " + fromDt + " to " + toDt);
                intDetails.add(it);
            }
        } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            List entryDtList = em.createNativeQuery("Select entryDate From loansecurity Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' and EntryDate between '" + fromDt + "' and '" + toDt + "' group by entrydate").getResultList();
            try {
                String a[][] = new String[entryDtList.size()][2];
                ArrayList datesFrom = new ArrayList();
                datesFrom.add(ymd.format(ymd.parse(fromDt)));

                if (!entryDtList.isEmpty()) {
                    for (int i = 0; i < entryDtList.size(); i++) {
                        Vector entryDtVect = (Vector) entryDtList.get(i);
                        a[i][0] = entryDtVect.get(0).toString();
                        if (i == 0) {
                            /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
                            if (ymmd.parse(fromDt).equals(ymd.parse(entryDtVect.get(0).toString()))) {
                                if (i == entryDtList.size() - 1) {
                                    a[i][1] = ymd.format(ymmd.parse(toDt));
                                    /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                    datesFrom.add(a[i][1]);
                                }
                            } else {
                                datesFrom.add(a[i][0]);
                                if (i == entryDtList.size() - 1) {
                                    a[i][1] = ymd.format(ymmd.parse(toDt));
                                    /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                    datesFrom.add(a[i][1]);
                                }
                            }
                        } else if (i > 0 && i < entryDtList.size() - 1) {
                            a[i - 1][1] = a[i][0];
                            /*=======Getting the ROI Change Date in Previous i Position=======================*/
                            datesFrom.add(a[i][0]);
                        } else if (i == entryDtList.size() - 1) {
                            a[i - 1][1] = a[i][0];
                            a[i][1] = ymd.format(ymmd.parse(toDt));
                            /*=======Getting the ROI Change Date in Previous i Position=======================*/
                            datesFrom.add(a[i][0]);
                            if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                                /*=======Getting the ROI Change Date in Current i Position=======================*/
                                datesFrom.add(a[i][1]);
                            }
                        }
                    }
                    Collections.sort(datesFrom);
                } else {
                    datesFrom.add(ymd.format(ymd.parse(toDt)));
                    Collections.sort(datesFrom);
                }

                String b[][] = new String[datesFrom.size()][2];
                if (!datesFrom.isEmpty()) {
                    for (int i = 0; i < datesFrom.size(); i++) {
                        if (i == 0) {
                            b[i][0] = datesFrom.get(i).toString();
                            //firstDisbDt = datesFrom.get(i).toString();
                        } else if (i > 0 && i < datesFrom.size() - 1) {
                            b[i][0] = datesFrom.get(i).toString();
                            b[i - 1][1] = ymd.format(ymmd.parse(dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
                        } else if (i == datesFrom.size() - 1) {
                            b[i - 1][1] = datesFrom.get(i).toString();
                        }
                    }
                }
                double product = 0;
                for (int i = 0; i < b.length - 1; i++) {
                    String fDate = b[i][0].toString();
                    String tDate = b[i][1].toString();
                    List margineList = em.createNativeQuery("Select IFNULL(MatValue,0),IFNULL(Margin,0) From loansecurity Where acno='" + acNo + "' and UPPER(status) = 'ACTIVE' and EntryDate between '" + fDate + "' and '" + tDate + "' Order By acno,Sno").getResultList();
                    if (margineList.size() > 0) {
                        for (int j = 0; j < margineList.size(); j++) {
                            Vector margineVect = (Vector) margineList.get(0);
                            matValue = Double.parseDouble(margineVect.get(0).toString());
                            margine = Double.parseDouble(margineVect.get(1).toString());
                            dpLimit = matValue - (matValue * margine / 100);
                            totalDpLimit = totalDpLimit + dpLimit;
                        }
                    } else {
                        dpLimit = 0f;
                        totalDpLimit = totalDpLimit + dpLimit;
                    }
                    outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, tDate));
                    if (outstanding < 0) {
                        outstanding = outstanding * -1;
                    } else {
                        outstanding = outstanding * 1;
                    }
                    if (outstanding > totalDpLimit) {
                        try {
                            outstanding = outstanding - dpLimit;
                            if (outstanding > 0) {
                                String intRoi = getPenalROI(intTableCode, outstanding, toDt);
                                if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                    throw new ApplicationException("Roi does not exist");
                                } else {
                                    roi = Double.parseDouble(intRoi);
                                }
                            }
                            Date frDt = ymd.parse(fDate);
                            Date tillDt = ymd.parse(tDate);
                            Long day = CbsUtil.dayDiff(tillDt, frDt);//(tillDt.getTime() - frDt.getTime()) / (24 * 60 * 60 * 1000);
                            int noOfDays = day.intValue() + 1;
                            double penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                            totalPenalAmt = totalPenalAmt + penalAmt;
                            product = product + outstanding * noOfDays;
                            if (i == 0) {
                                firstDt = dmy.format(frDt);
                            }
                            if (i == b.length - 2) {
                                lastDt = dmy.format(tillDt);
                                closingBal = outstanding;
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));

                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails("Security penal between " + firstDt + " to " + lastDt);
                intDetails.add(it);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApplicationException(e.getMessage());
            }
        }
        return intDetails;
    }

    public List<LoanIntCalcList> stockPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException {
        try {
            List<LoanIntCalcList> intDetails = new ArrayList<LoanIntCalcList>();
            LoanIntCalcList it = new LoanIntCalcList();
            String intTableCode = "";
            String acNature = "";
            String firstDt = "";
            String lastDt = "";
            double totalPenalAmt = 0d;
            double closingBal = 0d;
            String details = "";
            String custName = "";
            double product = 0;
            double roi = 0;
            double outstanding = 0;
            double noOfDays = 0;
            double penalAmt = 0;

            List SecDetailsList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE from cbs_loan_acc_mast_sec A, loan_appparameter B "
                    + "where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();

            if (SecDetailsList.isEmpty()) {
                throw new ApplicationException("Account number does not exist in secondary details table of loan.");
            } else {
                for (int i = 0; i < SecDetailsList.size(); i++) {
                    Vector SecDetailsVect = (Vector) SecDetailsList.get(i);
                    intTableCode = (String) SecDetailsVect.get(0);
                }
            }
            acNature = ftsPosting.getAccountNature(acNo);
            List acExistList = em.createNativeQuery("SELECT custname FROM accountmaster WHERE acno = '" + acNo + "'").getResultList();
            if (acExistList.size() > 0) {
                Vector acExistVect = (Vector) acExistList.get(0);
                custName = acExistVect.get(0).toString();
            } else {
                throw new ApplicationException("Custname does not exist for acount number " + acNo);
            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List entryDtList = em.createNativeQuery("Select entryDate, receivedSTMDt From loan_stockstm Where acno='" + acNo + "'  and UPPER(status) = 'ACTIVE' and EntryDate between '" + fromDt + "' and '" + toDt + "' Order By acno,entryDate").getResultList();
                try {
                    ArrayList datesFrom = new ArrayList();
                    datesFrom.add(ymd.format(ymmd.parse(fromDt)));
                    if (entryDtList.size() > 0) {
                        for (int i = 0; i < entryDtList.size(); i++) {
                            Vector entryDtVect = (Vector) entryDtList.get(i);
                            Date entryDt = ymd.parse(entryDtVect.get(0).toString());
                            Date receivedDt = ymd.parse(entryDtVect.get(1).toString());
                            if (receivedDt.after(entryDt)) {
                                outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, entryDtVect.get(1).toString()));
                                if (outstanding < 0) {
                                    outstanding = outstanding * -1;
                                } else {
                                    outstanding = outstanding * 1;
                                }
                                product = product + outstanding;
                                if (outstanding > 0) {
                                    String intRoi = getPenalROI(intTableCode, outstanding, entryDtVect.get(1).toString());
                                    if (intRoi.equalsIgnoreCase("Data does not exists")) {
                                        it.setFlag("false;" + intRoi);
                                        intDetails.add(it);
                                        return intDetails;
                                    } else {
                                        roi = Double.parseDouble(intRoi);
                                    }
                                }
                                //String endofline = System.getProperty("line.separator");
                                details = details.concat(" Stock Penal between " + dmy.format(entryDt) + " and " + dmy.format(receivedDt));
                                if (i == 0) {
                                    firstDt = dmy.format(entryDt);
                                }
                                if (i == entryDtList.size() - 1) {
                                    lastDt = dmy.format(receivedDt);
                                    closingBal = outstanding;
                                }

                                Long day = CbsUtil.dayDiff(receivedDt, entryDt);//(receivedDt.getTime() - entryDt.getTime()) / (24 * 60 * 60 * 1000);
                                noOfDays = day.doubleValue();
                                penalAmt = Math.round(roi * noOfDays * outstanding / 36500);
                                totalPenalAmt = totalPenalAmt + penalAmt;
                            }
                        }
                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(firstDt);
                        it.setLastDt(lastDt);
                        it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));

                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails(details);
                        intDetails.add(it);
                    } else {
                        it.setAcNo(acNo);
                        it.setCustName(custName);
                        it.setClosingBal(closingBal);
                        it.setFirstDt(firstDt);
                        it.setLastDt(lastDt);
                        it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                        it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                        //it.setTotalInt(totalInt);
                        it.setRoi(roi);
                        it.setIntTableCode(intTableCode);
                        it.setDetails(details);
                        intDetails.add(it);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(LoanPenalCalculationFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //  NumberFormat formatter = new DecimalFormat("#0.00");
                it.setAcNo(acNo);
                it.setCustName(custName);
                it.setClosingBal(closingBal);
                it.setFirstDt(firstDt);
                it.setLastDt(lastDt);
                it.setProduct(Double.parseDouble(formatter.format(new Double(product))));
                it.setTotalInt(Double.parseDouble(formatter.format(new Double(totalPenalAmt))));
                //it.setTotalInt(totalInt);
                it.setRoi(roi);
                it.setIntTableCode(intTableCode);
                it.setDetails(details);
                intDetails.add(it);
            }
            return intDetails;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<List<LoanIntCalcList>> cbsCentralizedPenalCalculation(String brnCode, String acNature, String acType, String fromDt, String toDt) throws ApplicationException {
        List<List<LoanIntCalcList>> intCalc = new ArrayList<List<LoanIntCalcList>>();
        List<LoanIntCalcList> repaymentPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> securityPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> stockPenalList = new ArrayList<LoanIntCalcList>();
        List<LoanIntCalcList> odPenalList = new ArrayList<LoanIntCalcList>();
        try {
            Date toDate = null;
            toDate = ymmd.parse(toDt);
            String acNo = "";

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate);
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            calendar.set(Calendar.DATE, lastDate);
            //Date lastDt = ymd.parse(ymd.format(calendar.getTime()));
            String securityPenalExist = "N";
            String odPenalExist = "N";
            String stockPenalExist = "N";
            String emiPenalExist = "N";
            String penalOnOnlyNpa = "N";
            String penalExcludeNpa = "N", npaAcExcludeQuery = "", npaExcludeQuery = "";
            List penalOnOnlyNpaList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ONLY_NPA' ").getResultList();
            if (penalOnOnlyNpaList.size() > 0) {
                Vector penalOnOnlyNpaVect = (Vector) penalOnOnlyNpaList.get(0);
                penalOnOnlyNpa = penalOnOnlyNpaVect.get(0).toString();
            }

            List penalExcludeNpaList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_EXCLUDE_NPA' ").getResultList();
            if (penalExcludeNpaList.size() > 0) {
                Vector penalExcludeNpaVect = (Vector) penalExcludeNpaList.get(0);
                penalExcludeNpa = penalExcludeNpaVect.get(0).toString();
            }

            List securityPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_SECURITY' ").getResultList();
            if (securityPenalExistList.size() > 0) {
                Vector securityPenalExistVect = (Vector) securityPenalExistList.get(0);
                securityPenalExist = securityPenalExistVect.get(0).toString();
            }

            List odPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_ODLIMIT' ").getResultList();
            if (odPenalExistList.size() > 0) {
                Vector odPenalExistVect = (Vector) odPenalExistList.get(0);
                odPenalExist = odPenalExistVect.get(0).toString();
            }

            List stockPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_STOCK' ").getResultList();
            if (stockPenalExistList.size() > 0) {
                Vector stockPenalExistVect = (Vector) stockPenalExistList.get(0);
                stockPenalExist = stockPenalExistVect.get(0).toString();
            }
            List emiPenalExistList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PENAL_ON_EMI' ").getResultList();
            if (emiPenalExistList.size() > 0) {
                Vector emiPenalExistVect = (Vector) emiPenalExistList.get(0);
                emiPenalExist = emiPenalExistVect.get(0).toString();
            }
            String brnString = "";
            if (!(brnCode.equalsIgnoreCase("ALL") || (brnCode.equalsIgnoreCase("0A")))) {
                brnString = " and brnCode = '" + brnCode + "' ";
            }
            String acTypeString = "";
            if (acNature.equalsIgnoreCase("ALL")) {
                acTypeString = "and actype in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "'))";
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeString = "and actype in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in ('B','D')) ";
                } else {
                    acTypeString = "and actype in (select acctcode from accounttypemaster where acctcode ='" + acType + "' )";
                }
            }
            List checkEmiPenalPosted = em.createNativeQuery("select actype from parameterinfo_posthistory where purpose = 'EMI PENAL INT' "
                    + "" + acTypeString + " " + brnString + " and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt
                    + "' and '" + toDt + "'))").getResultList();
            if (checkEmiPenalPosted.size() > 0) {
                throw new ApplicationException("Penal Interest Already Posted for account type " + checkEmiPenalPosted.get(0).toString() + "");
            }

            /**
             * *Calculation Part**
             */
            String brnCodeQuery = "";
            if (!(brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("ALL"))) {
                brnCodeQuery = "and CurBrCode = '" + brnCode + "' ";
            }
            String acNatQuery = "", secAcNatureQuery = " ";
            if (acNature.equalsIgnoreCase("All")) {
                acNatQuery = " and substring(c.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crdbflag in ('B','D'))";
                secAcNatureQuery = " and subString(a.acno,3,2)  in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crdbflag in ('B','D'))";
            } else {
                if (acType.equalsIgnoreCase("All")) {
                    acNatQuery = " and substring(c.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in ('B','D'))";
                    secAcNatureQuery = " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in ('B','D'))";
                } else {
                    acNatQuery = " and substring(c.acno,3,2) in (select acctcode from accounttypemaster where acctcode ='" + acType + "')";
                    secAcNatureQuery = " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctcode ='" + acType + "')";
                }
            }

            if (penalExcludeNpa.equalsIgnoreCase("Y")) {
                npaAcExcludeQuery = " and c.accStatus not in ('11','12','13') ";
                npaExcludeQuery = " and a.accStatus not in ('11','12','13')";
            }
            List getAllAccList, checkInSecOfLoan;
            if (penalOnOnlyNpa.equalsIgnoreCase("Y")) {
//                getAllAccList = em.createNativeQuery("select distinct acno from accountmaster c  where "
//                        + "  accStatus in ('11','12','13') " + acNatQuery + " " + brnCodeQuery + " ").getResultList();

                getAllAccList = em.createNativeQuery("select b.acno from "
                        + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                        + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                        + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                        + "(select distinct acno from accountmaster c  where accStatus in ('11','12','13') " + acNatQuery + " " + brnCodeQuery + ")b "
                        + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                        + " where a.acno = b.ACNO " + acNatQuery + " and a.accStatus in ('11','12','13') " + brnCodeQuery + " ").getResultList();

                checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                        + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a, "
                        + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                        + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                        + "(select distinct c.acno from accountmaster c, cbs_loan_acc_mast_sec b"
                        + " where c.acno = b.ACNO " + acNatQuery + " and c.accStatus in ('11','12','13') " + brnCodeQuery + ")b "
                        + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

            } else {
//                getAllAccList = em.createNativeQuery("select distinct acno from accountmaster c where "
//                        + " acno in (select distinct acno from ca_recon c where  auth = 'Y' " + acNatQuery + " group by acno "
//                        + " union all "
//                        + " select distinct acno from loan_recon c where  auth = 'Y' " + acNatQuery + " group by acno "
//                        + " Union All  "
//                        + " select distinct acno from npa_recon c where  auth = 'Y' " + acNatQuery + " group by acno ) "
//                        + " " + acNatQuery + " " + brnCodeQuery + " and accStatus <> 9 " + npaAcExcludeQuery).getResultList();

                getAllAccList = em.createNativeQuery("select b.acno from "
                        + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a,"
                        + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                        + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a,"
                        + "(select distinct acno from accountmaster c where  acno in "
                        + "(select distinct acno from ca_recon c where  auth = 'Y' " + acNatQuery + " group by acno "
                        + "union all "
                        + "select distinct acno from loan_recon c where  auth = 'Y'  " + acNatQuery + " group by acno "
                        + "Union All "
                        + "select distinct acno from npa_recon c where  auth = 'Y' " + acNatQuery + " group by acno ) "
                        + "" + acNatQuery + " " + brnCodeQuery + "  and accStatus <> 9 " + npaAcExcludeQuery + " "
                        + ")b where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

//                checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b"
//                        + " where a.acno = b.ACNO and a.acno in (select distinct c.acno from ca_recon c where  c.auth = 'Y' " + acNatQuery + " group by c.acno "
//                        + " union all "
//                        + " select distinct acno from loan_recon c where  auth = 'Y' " + acNatQuery + " group by acno"
//                        + " Union All  "
//                        + " select distinct c.acno from npa_recon c where  c.auth = 'Y' " + acNatQuery + " group by c.acno )  " + secAcNatureQuery + " " + brnCodeQuery + " and a.accStatus <> 9 " + npaExcludeQuery).getResultList();

                checkInSecOfLoan = em.createNativeQuery("select b.acno from "
                        + "(select a.ACNO,a.AC_INT_VER_NO,a.penal_apply from cbs_acc_int_rate_details a,"
                        + "(select acno,max(AC_INT_VER_NO) maxAcIntVerNo from cbs_acc_int_rate_details where substring(acno,3,2) in(select AcctCode from accounttypemaster where CrDbFlag in('B','D')) group by acno)b "
                        + "where a.acno = b.acno and a.AC_INT_VER_NO = b.maxAcIntVerNo and a.penal_apply = 'Y' group by acno)a, "
                        + "(select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b "
                        + "where a.acno = b.ACNO and a.acno in (select distinct c.acno from ca_recon c where  c.auth = 'Y'  " + acNatQuery + " group by c.acno "
                        + "union all "
                        + "select distinct acno from loan_recon c where  auth = 'Y'  " + acNatQuery + " group by acno "
                        + "Union All "
                        + "select distinct c.acno from npa_recon c where  c.auth = 'Y'  " + acNatQuery + " group by c.acno ) "
                        + "" + secAcNatureQuery + " " + brnCodeQuery + " and a.accStatus <> 9 " + npaExcludeQuery + ")b "
                        + "where a.acno = b.acno and a.penal_apply = 'Y'").getResultList();

            }
            if (getAllAccList.size() == checkInSecOfLoan.size()) {
                for (int i = 0; i < getAllAccList.size(); i++) {
                    Vector getAllAccVect = (Vector) getAllAccList.get(i);
                    acNo = (String) getAllAccVect.get(0);

                    if (securityPenalExist.equalsIgnoreCase("Y")) {
                        securityPenalList = securityPenalCalculation(acNo, fromDt, toDt, brnCode);
                        if (securityPenalList.size() > 0) {
                            if (securityPenalList.get(0).getTotalInt() > 0) {
                                intCalc.add(securityPenalList);
                            }
                        }
                    }
                    if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        if (stockPenalExist.equalsIgnoreCase("Y")) {
                            stockPenalList = stockPenalCalculation(acNo, fromDt, toDt, brnCode);
                            if (stockPenalList.size() > 0) {
                                if (stockPenalList.get(0).getTotalInt() > 0) {
                                    intCalc.add(stockPenalList);
                                }
                            }
                        }
                        if (odPenalExist.equalsIgnoreCase("Y")) {
                            odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                            if (odPenalList.size() > 0) {
                                if (odPenalList.get(0).getTotalInt() > 0) {
                                    intCalc.add(odPenalList);
                                }
                            }
                        }
                    }
                    if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        if (odPenalExist.equalsIgnoreCase("Y")) {
                            odPenalList = odPenalCalculation(acNo, fromDt, toDt, brnCode);
                            if (odPenalList.size() > 0) {
                                if (odPenalList.get(0).getTotalInt() > 0) {
                                    intCalc.add(odPenalList);
                                }
                            }
                        }
                    }
                    if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        if (emiPenalExist.equalsIgnoreCase("Y")) {
                            repaymentPenalList = repaymentPenalCalculationOnEMI(acNo, fromDt, toDt, brnCode);
                            if (repaymentPenalList.size() > 0) {
                                if (repaymentPenalList.get(0).getTotalInt() > 0) {
                                    intCalc.add(repaymentPenalList);
                                }
                            }
                        }
                    }
                }
            }
            return intCalc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String cbsCentralizedPenalPosting(List<IntCalTable> intCalc, String brnCode, String acNature, String acType, String fromDt, String toDt, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String schemeCode = "", freq = "";
        double totalPenal = 0, totalNpaIntAmt = 0;
        SimpleDateFormat mmmdy = new SimpleDateFormat("MMM dd yyyy");
        try {
            ut.begin();
            Date toDate = null;
            String acType1 = "";
            toDate = ymmd.parse(toDt);
            String acNo = "";
            String TotalTrsNo = "";
            float trSNo = 0;
            String glAcNO = "", uriGl = "", oirHead = "";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate);
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            calendar.set(Calendar.DATE, lastDate);

            String acTypeString = "";
            String acCodeString = "";
            if (acNature.equalsIgnoreCase("ALL")) {
                acTypeString = "and actype in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "'))";
                acCodeString = " acctcode in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "'))";
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeString = "and actype in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in ('B','D')) ";
                    acCodeString = " acctcode in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in ('B','D'))";
                } else {
                    acTypeString = "and actype in (select acctcode from accounttypemaster where acctcode ='" + acType + "' )";
                    acCodeString = " acctcode in (select acctcode from accounttypemaster where acctcode = ('" + acType + "')";
                }
            }
            String brnString = "";
            if (!(brnCode.equalsIgnoreCase("ALL") || brnCode.equalsIgnoreCase("0A"))) {
                brnString = "and brnCode ='" + brnCode + "'";
            }


            List checkEmiPenalPosted = em.createNativeQuery("select actype from parameterinfo_posthistory where purpose = 'EMI PENAL INT' "
                    + "" + acTypeString + " " + brnString + " and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt
                    + "' and '" + toDt + "'))").getResultList();
            if (checkEmiPenalPosted.size() > 0) {
                throw new ApplicationException("Penal interest already posted.");
            }

            Map<String, String> mapBranch = new HashMap<String, String>();
            for (IntCalTable intCalTable : intCalc) {
                if (!mapBranch.containsKey(intCalTable.getAcNo().substring(0, 2))) { //Present Branch                       
                    mapBranch.put(intCalTable.getAcNo().substring(0, 2), intCalTable.getAcNo().substring(0, 2));
                }
            }
            Map<String, String> acTypeMap = new HashMap<String, String>();
            for (IntCalTable intCalTable : intCalc) {
                if (!acTypeMap.containsKey(intCalTable.getAcNo().substring(2, 4))) { //Present Branch                       
                    acTypeMap.put(intCalTable.getAcNo().substring(2, 4), intCalTable.getAcNo().substring(2, 4));
                }
            }
            Set<Map.Entry<String, String>> setBranch = mapBranch.entrySet();
            Iterator<Map.Entry<String, String>> itBranch = setBranch.iterator();
            while (itBranch.hasNext()) {
                Map.Entry<String, String> entryBranch = itBranch.next();
                brnCode = entryBranch.getValue();
                totalNpaIntAmt = 0d;

                Set<Map.Entry<String, String>> setActype = acTypeMap.entrySet();
                Iterator<Map.Entry<String, String>> itAcType = setActype.iterator();
                while (itAcType.hasNext()) {
                    Map.Entry<String, String> entryAcType = itAcType.next();
                    acType1 = entryAcType.getValue();
                    List<IntCalTable> IntCalListAcTypeWise = new ArrayList<IntCalTable>();
                    for (int m = 0; m < intCalc.size(); m++) {
                        intCalc.get(m).getTotalInt();
                        if (intCalc.get(m).getAcNo().substring(0, 2).equalsIgnoreCase(brnCode) && intCalc.get(m).getAcNo().substring(2, 4).equalsIgnoreCase(acType1)) {
                            IntCalListAcTypeWise.add(intCalc.get(m));
                        }
                    }
                    List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType1 + "' ").getResultList();
                    if (glHeadList.isEmpty()) {
                        throw new ApplicationException("GL Head Doesn't Exists");
                    } else {
                        Vector glHeadVect = (Vector) glHeadList.get(0);
                        glAcNO = "";
                        glAcNO = brnCode + glHeadVect.get(0).toString() + "01";
                        uriGl = "";
                        oirHead = "";
                        uriGl = glHeadVect.get(1).toString();
                        oirHead = glHeadVect.get(2).toString();

                        //List tempBdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
                        //Vector tempBdVect = (Vector) tempBdList.get(0);
                        //String tempBd = tempBdVect.get(0).toString();
                        List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'PENAL INT' and actype = '" + acType1 + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or (todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                        if (parameterInfo.size() > 0) {
                            throw new ApplicationException("Interest already posted");
                        }
                        double glSumAmt = 0f;
                        float recNo;
                        String details = "";
                        String till = mmmdy.format(toDate);
                        for (int s = 0; s < IntCalListAcTypeWise.size(); s++) {
                            details = "";
                            totalPenal = 0d;
                            glSumAmt = 0f;
                            totalNpaIntAmt = 0d;
                            IntCalTable vect = IntCalListAcTypeWise.get(s);
                            totalPenal = Double.parseDouble(vect.getTotalInt().toString());
                            details = vect.getDetails();
                            acNo = vect.getAcNo();
                            String acNat = common.getAcNatureByAcNo(acNo);
                            List SecDetailsList = em.createNativeQuery("SELECT A.SCHEME_CODE, A.CALC_METHOD   from cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND A.ACNO ='" + acNo + "'").getResultList();
                            if (SecDetailsList.isEmpty()) {
                                throw new ApplicationException("Account number does not exists in secondary details table of loan.");
                            } else {
                                for (int k = 0; k < SecDetailsList.size(); k++) {
                                    Vector SecDetailsVect = (Vector) SecDetailsList.get(k);
                                    schemeCode = (String) SecDetailsVect.get(0);
                                    freq = (String) SecDetailsVect.get(1);
                                }
                            }
                            List flowDetailList = em.createNativeQuery("select PENAL_INT_DEMAND_FLOW_ID, OVERDUE_INT_DEMAND_FLOW_ID, PAST_DUE_COLLECTION_FLOW_ID,CHARGE_DEMAND_FLOW_ID from cbs_scheme_loan_prepayment_details where SCHEME_CODE =  '" + schemeCode + "'").getResultList();

                            String penalIntDemFlowId = null;
                            if (flowDetailList.isEmpty()) {
                                throw new ApplicationException("Flow Id does not exist in Scheme Master.");
                            } else {
                                for (int l = 0; l < flowDetailList.size(); l++) {
                                    Vector flowDetailVect = (Vector) flowDetailList.get(l);
                                    penalIntDemFlowId = flowDetailVect.get(0).toString();
                                }
                            }
                            if (totalPenal > 0) {
                                if (s == 0) {
                                    trSNo = ftsPosting.getTrsNo();
                                } else if (glSumAmt > 0) {
                                    trSNo = ftsPosting.getTrsNo();
                                }
                                System.out.println("trsno" + trSNo);
                                String trSno = String.valueOf(trSNo);
                                if (trSno.equalsIgnoreCase(" ")) {
                                    TotalTrsNo = trSno;
                                } else {
//                                    if (s == 0) {
//                                        TotalTrsNo = trSno;
//                                    } else if (glSumAmt > 0) {
//                                        TotalTrsNo = TotalTrsNo.concat(",".concat(trSno));
//                                    }
                                    if (TotalTrsNo.contains(",")) {
                                        if (!TotalTrsNo.contains(trSno)) {
                                            TotalTrsNo = TotalTrsNo.concat(",".concat(trSno));
                                        }
                                    } else {
                                        if (TotalTrsNo.equalsIgnoreCase("")) {
                                            TotalTrsNo = trSno;
                                        } else {
                                            if (!TotalTrsNo.contains(trSno)) {
                                                TotalTrsNo = TotalTrsNo.concat(",".concat(trSno));
                                            }
                                        }

                                    }
                                }
                                List acStatusList = em.createNativeQuery("select accstatus from accountmaster where acno = '" + acNo + "' and accstatus <> 9").getResultList();
                                Vector acStatusVect = (Vector) acStatusList.get(0);
                                int acStatus = Integer.parseInt(acStatusVect.get(0).toString());
                                if ((acStatus == 11) || (acStatus == 12) || (acStatus == 13) || (acStatus == 14) || (acStatus == 3) || (acStatus == 6)) {
                                    recNo = ftsPosting.getRecNo();
                                    totalNpaIntAmt = 0d;
                                    Integer insertLaRecon = null;
                                    recNo = ftsPosting.getRecNo();
                                    insertLaRecon = em.createNativeQuery("INSERT INTO  npa_recon (ACNO,TY,DT,VALUEDT,DRAMT,CRAMT,TRANTYPE,DETAILS,"
                                            + "IY,ENTERBY,authby,AUTH,payby,trsno,RECNO,balance,trandesc,intamt,status,trantime, org_brnid, dest_brnid)"
                                            + " values('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + totalPenal + ",0,8,'" + details + "',1,'" + authBy + "','system','Y',3,"
                                            + trSNo + "," + recNo + ",0,3," + totalPenal + "," + acStatus + ",NOW(),'" + brnCode + "','"
                                            + brnCode + "')").executeUpdate();
                                    if (insertLaRecon <= 0) {
                                        throw new ApplicationException("insertion problem in Ca recon table");
                                    }

                                    totalNpaIntAmt = totalNpaIntAmt + totalPenal;

                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','P')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    }
                                } else {
                                    recNo = ftsPosting.getRecNo();
                                    Integer insertLaRecon = null;
                                    recNo = ftsPosting.getRecNo();
                                    insertLaRecon = em.createNativeQuery("INSERT into " + common.getTableName(acNat) + " (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid,dest_brnid) values "
                                            + "('" + acNo + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "','" + totalPenal + "',0,8,3,3,'" + details + "','" + authBy + "','Y','SYSTEM'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')").executeUpdate();

                                    if (insertLaRecon <= 0) {
                                        throw new ApplicationException("insertion problem in Ca recon table");
                                    }
                                    String reconBalanTable = "reconbalan";
                                    if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        reconBalanTable = "ca_reconbalan";
                                    }
                                    List chkBalan = em.createNativeQuery("SELECT BALANCE FROM  " + reconBalanTable + " WHERE ACNO='" + acNo + "'").getResultList();

                                    if (chkBalan.size() > 0) {
                                        Query updateCaReconQuery = em.createNativeQuery("UPDATE " + reconBalanTable + " SET BALANCE = BALANCE - " + totalPenal
                                                + " WHERE Acno = '" + acNo + "'");

                                        Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                        if (updateCaRecon == 0) {
                                            throw new ApplicationException("Value not updated in  reconbalan table");
                                        }
                                    }
                                    List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                                    Vector sNoVect = (Vector) sNoList.get(0);
                                    int sNo = Integer.parseInt(sNoVect.get(0).toString());
                                    Query updateIntPostAcWiseQuery = em.createNativeQuery("insert into cbs_loan_interest_post_ac_wise (SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG) values(" + sNo + ", '" + acNo + "','Y',NOW(),'" + fromDt + "','" + toDt + "','" + brnCode + "','I')");
                                    Integer updateIntPostAcWise = updateIntPostAcWiseQuery.executeUpdate();
                                    if (updateIntPostAcWise == 0) {
                                        throw new ApplicationException("Interest not Posted successfully, because value is not inserted into cbs_loan_interest_post_ac_wise table");
                                    } else {
                                        List dmdSchNoList = em.createNativeQuery("select IFNULL(max(SHDL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                        Vector dmdSchNoVect = (Vector) dmdSchNoList.get(0);
                                        int dmdSchNo = Integer.parseInt(dmdSchNoVect.get(0).toString());

                                        List srNoList = em.createNativeQuery("select IFNULL(max(DMD_SRL_NUM),0)+1 from cbs_loan_dmd_table").getResultList();
                                        Vector srNoVect = (Vector) srNoList.get(0);
                                        int srNo = Integer.parseInt(srNoVect.get(0).toString());
                                        String overDudt = dateAdd(toDt, 1);
                                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, DEL_FLG, LD_FREQ_TYPE, DMD_EFF_DATE, DMD_OVDU_DATE, DMD_AMT, LCHG_USER_ID, LCHG_TIME, EI_AMT, TS_CNT, LATEFEE_STATUS_FLG)"
                                                + " values('" + acNo + "'," + dmdSchNo + ",'" + penalIntDemFlowId + "','" + ymd.format(ymmd.parse(toDt)) + "'," + srNo + ",'N', '" + freq + "','" + ymmd.format(ymmd.parse(toDt)) + "', '" + overDudt + "'," + totalPenal + ", '" + authBy + "',NOW(), " + totalPenal + ",0,'N')");
                                        Integer insertQry = insertQuery.executeUpdate();
                                        if (insertQry <= 0) {
                                            throw new ApplicationException("Insertion problem in Loan Demand table");
                                        }
                                    }
                                    glSumAmt = glSumAmt + totalPenal;
                                }
                            }
                            if (glSumAmt > 0) {
                                recNo = ftsPosting.getRecNo();
                                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + glSumAmt + " WHERE ACNO= '" + glAcNO + "'");
                                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                                if (updateReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't updated in RECONBALAN");
                                }
                                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + glAcNO + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                        + glSumAmt + ",0,8,3,3,'VCH INT. UP TO  " + mdy.format(ymmd.parse(toDt)) + "','system','Y','" + authBy
                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                if (insertReconBalan == 0) {
                                    throw new ApplicationException("Value doesn't inserted in gl_recon");
                                }
                            }
                            if (totalNpaIntAmt > 0) {
                                if (!oirHead.equals("") && !uriGl.equals("")) {
                                    uriGl = glHeadVect.get(1).toString();
                                    oirHead = glHeadVect.get(2).toString();
                                    uriGl = brnCode + uriGl + "01";
                                    oirHead = brnCode + oirHead + "01";

                                    recNo = ftsPosting.getRecNo();
                                    Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',"
                                            + totalNpaIntAmt + ",0,8,3,3,'Penal Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType1 + "','system','Y','" + authBy
                                            + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                    Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                    if (insertReconBalan == 0) {
                                        throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                    }
                                    recNo = ftsPosting.getRecNo();
                                    insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                            + totalNpaIntAmt + ",8,3,3,'Penal Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType1 + "','system','Y','" + authBy
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
                        if (!IntCalListAcTypeWise.isEmpty()) {
                            Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                                    + " VALUES('" + acType1 + "','" + fromDt + "','" + toDt + "','PENAL INT',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                            Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                            if (insertReconBalan <= 0) {
                                throw new ApplicationException("Value does not inserted in gl_recon");
                            } else {
                                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  WHERE AC_TYPE = '" + acType1 + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'P' and brncode = '" + brnCode + "'");
                                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                                if (updateAccTypeIntPara <= 0) {
                                    throw new ApplicationException("Value does not updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                                }
                            }
                        }
                    }
                }
            }
            ut.commit();
            return "Interest Posted Successfully." + TotalTrsNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }
}
