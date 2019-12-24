/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.pojo.IntCalTable;
import com.cbs.exception.ApplicationException;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.LoanIntCalcListSort;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
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
import javax.transaction.UserTransaction;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless(mappedName = "CentralizedLoanInterestCalFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CentralizedLoanInterestCalFacade implements CentralizedLoanInterestCalFacadeRemote {

    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanIntCalc;
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

    public String centralizedLoanInterestPosting(List<IntCalTable> intCalc, String acType, String acNature, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String acNo = "";
            String TotalTrsNo = "";
            String glAcNo = "";
            String acType1 = "";
            String indAcAuthEntryFlag = "N";
            List indAcAuthEntryFlagList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'PASS_IND_AC_AUTH_ENTRY'").getResultList();
            if (!indAcAuthEntryFlagList.isEmpty()) {
                Vector indAcAuthEntryFlagVect = (Vector) indAcAuthEntryFlagList.get(0);
                indAcAuthEntryFlag = indAcAuthEntryFlagVect.get(0).toString();
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
            List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();    //Sms List            
            String trSNo = "";
            Set<Map.Entry<String, String>> setBranch = mapBranch.entrySet();
            Iterator<Map.Entry<String, String>> itBranch = setBranch.iterator();
            while (itBranch.hasNext()) {
                Map.Entry<String, String> entryBranch = itBranch.next();
                brnCode = entryBranch.getValue();

                Set<Map.Entry<String, String>> setActype = acTypeMap.entrySet();
                Iterator<Map.Entry<String, String>> itAcType = setActype.iterator();
                while (itAcType.hasNext()) {
                    Map.Entry<String, String> entryAcType = itAcType.next();
                    acType1 = entryAcType.getValue();
                    List<IntCalTable> IntCalListAcTypeWise = new ArrayList<IntCalTable>();
                    for (int m = 0; m < intCalc.size(); m++) {
                        if (intCalc.get(m).getAcNo().substring(0, 2).equalsIgnoreCase(brnCode) && intCalc.get(m).getAcNo().substring(2, 4).equalsIgnoreCase(acType1)) {
                            IntCalListAcTypeWise.add(intCalc.get(m));
                        }
                    }

//                    if (!IntCalListAcTypeWise.isEmpty()) {
//                        List tempBdList = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG = 'N' AND BRNCODE = '" + brnCode + "'").getResultList();
//                        Vector tempBdVect = (Vector) tempBdList.get(0);
//                        String tempBd = tempBdVect.get(0).toString();
                    if (!IntCalListAcTypeWise.isEmpty()) {
                        String tempBd = ymmd.format(date);

                        /*
                         * Flag, to pass the Authrize entry in Individual Account
                         */
                        List ac_Type_StaffList = em.createNativeQuery("SELECT Int_Ac_Open_Enable_In_Staff FROM accounttypemaster WHERE ACCTCODE = '" + acType1 + "'").getResultList();
                        Vector acStaffVect = (Vector) ac_Type_StaffList.get(0);
                        String Int_Ac_Open_Enable_In_Staff = acStaffVect.get(0).toString();

                        String uriGl = "";
                        String oirHead = "";
                        double totalNpaIntAmt = 0, prinAmt = 0;
                        int acStatus = 1;
                        acNature = IntCalListAcTypeWise.get(0).getAcNature();
                        List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acType1 + "'").getResultList();
                        if (glHeadList.isEmpty()) {
                            throw new ApplicationException("GL Head Doesn't Exists for " + acType1);
                        } else {
                            Vector glHeadVect = (Vector) glHeadList.get(0);
                            glAcNo = brnCode + glHeadVect.get(0).toString() + "01";
                            uriGl = glHeadVect.get(1).toString();
                            oirHead = glHeadVect.get(2).toString();
                        }
                        /**
                         * All Account Wise Posting
                         */
                        String indAcQuery = "";


                        if (indAcAuthEntryFlag.equalsIgnoreCase("N")) {
                            List parameterInfo = em.createNativeQuery("select * from parameterinfo_posthistory where purpose = 'Loan Interest' and "
                                    + "actype = '" + acType1 + "' and brnCode = '" + brnCode + "' and ((fromdt between '" + fromDt + "' and '" + toDt + "') or "
                                    + "(todt between '" + fromDt + "' and '" + toDt + "'))").getResultList();
                            if (parameterInfo.size() > 0) {
                                throw new ApplicationException("Interest Already Posted");
                            }
                        } else {
                            indAcQuery = " and a.acno = '" + acNo + "' ";
                        }
                        trSNo = (fTSPosting43CBSBean.getTrsNo()).toString();
                        if (trSNo.equalsIgnoreCase(" ")) {
                            TotalTrsNo = trSNo;
                        } else {
                            TotalTrsNo = TotalTrsNo.concat(",".concat(trSNo));
                        }
                        List sNoList = em.createNativeQuery("SELECT max(IFNULL(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                        Vector sNoVect = (Vector) sNoList.get(0);
                        int sNo = Integer.parseInt(sNoVect.get(0).toString());

                        if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            List getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a,  "
                                    + " (select distinct acno as acno1 from ca_recon where  substring(acno,3,2) = '" + acType1 + "'  and auth = 'Y' group by acno "
                                    + " UNION  "
                                    + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType1 + "'  and auth = 'Y' group by acno ) b "
                                    + " where a.acno = b.acno1  and "
                                    + " subString(a.acno,3,2)  = '" + acType1 + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();

                            List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,ca_recon c "
                                    + "where a.acno = b.ACNO AND a.acno = c.ACNO  and subString(a.acno,3,2)  = '" + acType1 + "' " + indAcQuery + " and a.accStatus <> 9 "
                                    + "and a.CurBrCode = '" + brnCode + "'").getResultList();

                            if (getAllAccList.size() == checkInSecOfLoan.size()) {
                                double glSumAmt = 0f;
                                for (int i = 0; i < IntCalListAcTypeWise.size(); i++) {
                                    acNo = IntCalListAcTypeWise.get(i).getAcNo();
                                    double intAmt = IntCalListAcTypeWise.get(i).getTotalInt().doubleValue();
                                    double closingBal = IntCalListAcTypeWise.get(i).getClosingBal().doubleValue();
                                    double roi = IntCalListAcTypeWise.get(i).getRoi().doubleValue();
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
                                        acStatus = Integer.parseInt(IntCalListAcTypeWise.get(i).getAccStatus());
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
//                                        List SecDetailsList = em.createNativeQuery("SELECT a.ACNO, a.SCHEME_CODE, a.INTEREST_TABLE_CODE, a.MORATORIUM_PD, a.ACC_PREF_DR, "
//                                                + "a.ACC_PREF_CR, a.RATE_CODE, a.DISB_TYPE, a.CALC_METHOD, a.CALC_ON, a.INT_APP_FREQ, a.CALC_LEVEL, a.COMPOUND_FREQ, IFNULL(a.PEGG_FREQ,0), "
//                                                + "a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.recover  from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + acNo + "'").getResultList();
//                                        if (!SecDetailsList.isEmpty()) {
//                                            Vector SecDetailsVect = (Vector) SecDetailsList.get(0);
//                                            String intAppFreq = (String) SecDetailsVect.get(10);
//                                            String recoveryOpt = (String) SecDetailsVect.get(16);
//                                            if (intAppFreq.equalsIgnoreCase("S") && recoveryOpt.equalsIgnoreCase("CIP")) {
//                                                String msg = fTSPosting43CBSBean.insertionAsPerPrincipalInt(acNo, toDt, intAmt);
//                                                if (!msg.equalsIgnoreCase("TRUE")) {
//                                                    throw new ApplicationException(msg);
//                                                }
//                                            }
//                                        }
                                    }
                                    if (i == IntCalListAcTypeWise.size() - 1) {
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
                                                        + totalNpaIntAmt + ",0,8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType1 + "','system','Y','" + authBy
                                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                                if (insertReconBalan == 0) {
                                                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                                }
                                                recNo = fTSPosting43CBSBean.getRecNo();
                                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                        + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                        + totalNpaIntAmt + ",8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType1 + "','system','Y','" + authBy
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
                                throw new ApplicationException("Please Check all the '" + acType1 + "' account is exists in cbs_loan_acc_mast_sec Table");
                            }
                        } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            List getAllAccList = em.createNativeQuery("select distinct a.acno from accountmaster a, "
                                    + " (select distinct acno as acno1 from loan_recon where  substring(acno,3,2) = '" + acType1 + "'  and auth = 'Y' group by acno "
                                    + " UNION  "
                                    + " select distinct acno as acno1 from npa_recon where  substring(acno,3,2) = '" + acType1 + "'  and auth = 'Y' group by acno ) b "
                                    + " where a.acno = b.acno1  and  "
                                    + "subString(a.acno,3,2)  = '" + acType1 + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "' order by a.acno").getResultList();


                            List checkInSecOfLoan = em.createNativeQuery("select distinct a.acno from accountmaster a, cbs_loan_acc_mast_sec b,loan_recon c"
                                    + " where a.acno = b.ACNO AND a.acno = c.ACNO"
                                    + " and subString(a.acno,3,2)  = '" + acType1 + "' " + indAcQuery + " and a.accStatus <> 9 and a.CurBrCode = '" + brnCode + "'").getResultList();

                            if (getAllAccList.size() == checkInSecOfLoan.size()) {
                                double glSumAmt = 0f;
                                for (int i = 0; i < IntCalListAcTypeWise.size(); i++) {
//                            Vector getAllAccVect = (Vector) getAllAccList.get(i);
                                    acNo = IntCalListAcTypeWise.get(i).getAcNo();
                                    List int_Trf_AcnoList = em.createNativeQuery("Select INT_TRF_ACNO from cbs_loan_acc_mast_sec where acno='" + acNo + "'").getResultList();
                                    Vector intVec = (Vector) int_Trf_AcnoList.get(0);
                                    String INT_TRF_ACNO = intVec.get(0).toString();
//                            it = accWiseLoanIntCalc(fromDt, toDt, acNo, brnCode);
                                    double intAmt = IntCalListAcTypeWise.get(i).getTotalInt().doubleValue();
                                    double closingBal = IntCalListAcTypeWise.get(i).getClosingBal().doubleValue();
                                    double roi = IntCalListAcTypeWise.get(i).getRoi().doubleValue();
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

                                        acStatus = Integer.parseInt(IntCalListAcTypeWise.get(i).getAccStatus());
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
                                        List SecDetailsList = em.createNativeQuery("SELECT a.ACNO, a.SCHEME_CODE, a.INTEREST_TABLE_CODE, a.MORATORIUM_PD, a.ACC_PREF_DR, "
                                                + "a.ACC_PREF_CR, a.RATE_CODE, a.DISB_TYPE, a.CALC_METHOD, a.CALC_ON, a.INT_APP_FREQ, a.CALC_LEVEL, a.COMPOUND_FREQ, IFNULL(a.PEGG_FREQ,0), "
                                                + "a.LOAN_PD_MONTH, a.LOAN_PD_DAY, b.recover  from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y") ? (INT_TRF_ACNO) : (acNo)) + "'").getResultList();
                                        if (!SecDetailsList.isEmpty()) {
                                            Vector SecDetailsVect = (Vector) SecDetailsList.get(0);
                                            String intAppFreq = (String) SecDetailsVect.get(10);
                                            String recoveryOpt = (String) SecDetailsVect.get(16);
                                            if (intAppFreq.equalsIgnoreCase("S") && recoveryOpt.equalsIgnoreCase("CIP")) {
                                                String msg = fTSPosting43CBSBean.insertionAsPerPrincipalInt(acNo, toDt, intAmt);
                                                if (!msg.equalsIgnoreCase("TRUE")) {
                                                    throw new ApplicationException(msg);
                                                }
                                            }
                                        }
                                    }
                                    if (i == IntCalListAcTypeWise.size() - 1) {
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
                                                        + totalNpaIntAmt + ",0,8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType1 + "','system','Y','" + authBy
                                                        + "'," + trSNo + "," + recNo + ",'" + brnCode + "','" + brnCode + "')");
                                                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                                                if (insertReconBalan == 0) {
                                                    throw new ApplicationException("Value doesn't inserted in gl_recon");
                                                }
                                                recNo = fTSPosting43CBSBean.getRecNo();
                                                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                                        + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + toDt + "',0,"
                                                        + totalNpaIntAmt + ",8,3,3,'Int From " + mdy.format(ymmd.parse(fromDt)) + " to " + mdy.format(ymmd.parse(toDt)) + " on NPA of " + acType1 + "','system','Y','" + authBy
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
                                }
                            } else {
                                throw new ApplicationException("Please Check all the '" + acType1 + "' account is exists in cbs_loan_acc_mast_sec Table");
                            }
                        }
                        if (indAcAuthEntryFlag.equalsIgnoreCase("N")) {
                            List getParameterInfoIntList = em.createNativeQuery("select * from parameterinfo_int where ACTYPE='" + acType1 + "' AND MFLAG='N'  and brncode = '" + brnCode + "'").getResultList();
                            if (getParameterInfoIntList.size() > 0) {
                                Query updateParaInfoIntQuery = em.createNativeQuery("UPDATE parameterinfo_int SET MFLAG='Y',DT='" + toDt + "' WHERE ACTYPE='" + acType1 + "' AND MFLAG='N'  and brncode = '" + brnCode + "'");
                                Integer updateParaInfoInt = updateParaInfoIntQuery.executeUpdate();
                                if (updateParaInfoInt == 0) {
                                    throw new ApplicationException("Value doesn't updated in  parameterinfo_int");
                                }
                                String minDt;
                                List getMinDtList = em.createNativeQuery("SELECT MIN(DT) FROM parameterinfo_int WHERE ACTYPE='" + acType1 + "' and brncode = '" + brnCode + "'").getResultList();
                                if (getMinDtList.size() > 0) {
                                    Vector getMinDtVect = (Vector) getMinDtList.get(0);
                                    minDt = getMinDtVect.get(0).toString();
                                    updateParaInfoIntQuery = em.createNativeQuery("UPDATE parameterinfo_int SET MFLAG = 'N',DT = '" + dateAdd(toDt, 1) + "' WHERE ACTYPE = '" + acType1 + "' AND DT = '" + minDt + "'");
                                    updateParaInfoInt = updateParaInfoIntQuery.executeUpdate();
                                    if (updateParaInfoInt == 0) {
                                        throw new ApplicationException("Value doesn't updated in  parameterinfo_int");
                                    }
                                }
                            }

                            Query insertReconBalanQuery = em.createNativeQuery("INSERT INTO parameterinfo_posthistory(actype,FromDt,todt,purpose,trandesc,dt,trantime,enterby,status,brncode)"
                                    + " VALUES('" + acType1 + "','" + fromDt + "','" + toDt + "','Loan Interest',3,'" + toDt + "',NOW(),'" + authBy + "',1,'" + brnCode + "')");
                            Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                            if (insertReconBalan == 0) {
                                throw new ApplicationException("Value doesn't inserted in gl_recon");
                            } else {
                                Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + authBy + "'  "
                                        + "WHERE AC_TYPE = '" + acType1 + "' AND FROM_DT = '" + fromDt + "' and TO_DT = '" + toDt + "' and flag = 'I' and brncode = '" + brnCode + "'");
                                Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
                                if (updateAccTypeIntPara == 0) {
                                    throw new ApplicationException("Value doesn't updated in  LOAN_ACCTYPE_INTEREST_PARAMETER");
                                }
                            }
                        }
                    }
                }
            }

            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            //End here
            return "Interest posted successfully. Generated batch no is " + TotalTrsNo;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List<LoanIntCalcList> centralizedLoanInterestCalculationCover(String acType, String acNature, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException {
        List<LoanIntCalcList> intCalc = new ArrayList<LoanIntCalcList>();
        try {
            double closingBal = 0d;
            String acno = "";
            double npaBal = 0d;
            double odLimit = 0;
            double interest = 0d;
            double rateOfInt = 0d;
            double Inter = 0d;
            String PreviousAccNo = "";
            String preToDt = "";
            String intTableCode = "";
            double product = 0d;
            double intDeposi = 0d;
            String turnOverFlag = "";
            String calcLevel = "";
            int pegg_Freq = 0;
            String calc_On = "";
            String rate_Code = "";
            long version = 0l;
            double ACC_PREF_CR = 0d;
            double AC_PREF_DR = 0d, simpleBal = 0d;
            int PEG_FREQ_MON = 0;
            String INT_PEG_FLG = "";
            String OpeningDt = "";
            String loanFinistDt = "", accStatus = "", orderByParameter = "E";
            double actRoi = 0d;
            String branch = "";
            String accType = "", intAppFreq = "";

//            List paramList = em.createNativeQuery("select code from cbs_parameterinfo where NAME = 'SEC_ROI_CALC'").getResultList();
//            String orderBy = " Entrydate ";
//            if (!paramList.isEmpty()) {
//                Vector orderVect = (Vector) paramList.get(0);
//                if (orderVect.get(0).toString().equalsIgnoreCase("R")) { //AppRoi
//                    orderBy = " AppRoi, Entrydate ";
//                } else if (orderVect.get(0).toString().equalsIgnoreCase("DR")) { //AppRoi desc
//                    orderBy = " AppRoi desc, Entrydate ";
//                } else if (orderVect.get(0).toString().equalsIgnoreCase("DT")) { //Entrydate
//                    orderBy = " Entrydate ";
//                }
//            }
            String acNatureQuery = "";
            String branchQuery = "";
            if (!(brnCode.equalsIgnoreCase("0A")) || (brnCode.equalsIgnoreCase("All"))) {
                branchQuery = "and substring(am.acno,1,2) ='" + brnCode + "' ";
            }
            if (acNature.equalsIgnoreCase("All")) {
                acNatureQuery = " and atm.acctNature in ('CA','DL','TL') ";
            } else {
                if (acType.equalsIgnoreCase("All")) {
                    acNatureQuery = " and atm.acctNature in ('" + acNature + "') ";
                } else {
                    acNatureQuery = " and atm.acctNature in ('" + acNature + "') and substring(am.acno,3,2) ='" + acType + "' ";
                }
            }

            List intList = null;
            String query = "select aa.brnCode,aa.acctNature, aa.AcctCode, aa.acno, DATE_FORMAT(aa.date, '%Y-%m-%d') as date, aa.INTEREST_TABLE_CODE, aa.bal, aa.npaBal, aa.odlimit, cc.ROI,((aa.bal+aa.npaBal)*cc.ROI), "
                    + "aa.custName, aa.intdeposit, aa.TURN_OVER_DETAIL_FLAG, aa.CALC_LEVEL, aa.PEGG_FREQ, aa.CALC_ON, aa.RATE_CODE , "
                    + "aa.version, air.INT_TABLE_CODE, air.ACC_PREF_CR, air.AC_PREF_DR, air.INT_PEG_FLG, air.PEG_FREQ_MON, aa.OpeningDt,"
                    + "DATE_ADD(DATE_FORMAT(aa.OpeningDt, '%Y-%m-%d'), INTERVAL air.PEG_FREQ_MON MONTH) as loanFinistDt,"
                    + "ifnull(if(aa.RATE_CODE='Fl',(cc.ROI-air.ACC_PREF_CR+air.AC_PREF_DR), "
                    + "if(DATE_ADD(DATE_FORMAT(aa.OpeningDt, '%Y-%m-%d'), INTERVAL air.PEG_FREQ_MON MONTH)<=aa.date,"
                    + "(cc.ROI-air.ACC_PREF_CR+air.AC_PREF_DR), aa.intdeposit )),0) as actRoi, aa.AccStatus, aa.LINK_TRAN_TO_PURCHASE_SALE,"
                    + "ifnull(DATE_FORMAT(lp.Adhocapplicabledt, '%Y-%m-%d'),'1900-01-01'), ifnull(DATE_FORMAT(lp.AdhocExpiry, '%Y-%m-%d'),'1900-01-01'), "
                    + "ifnull(lp.AdhocROI,0) as AdhocROI, ifnull(lp.ODLimit,0) as ODLimit, "
                    + "ifnull(lp.Adhoclimit,0) as Adhoclimit, lp.PenalROI, aa.INT_APP_FREQ, aa.simpleBal  "
                    + "from (select substring(am.acno,1,2) as brnCode,atm.acctNature, atm.AcctCode, am.acno, bk.date, s.INTEREST_TABLE_CODE, "
                    + "(if((atm.acctnature = 'CA'),(select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ca_recon where acno = am.acno and dt<=bk.date), "
                    + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from loan_recon where acno = am.acno and dt<=bk.date) )) as bal,"
                    + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from npa_recon where acno = am.acno and dt<=bk.date) as npaBal, "
                    + "(if((atm.acctnature = 'CA'),(select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from ca_recon where acno = am.acno and trandesc not in (3,4) and dt<=bk.date), "
                    + "(select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from loan_recon where acno = am.acno  and trandesc not in (3,4) and dt<=bk.date) )) as simpleBal, "
                    + " s.INT_APP_FREQ, am.odlimit, "
                    + "am.custName, am.intdeposit, ifnull(sg.TURN_OVER_DETAIL_FLAG,'N') as TURN_OVER_DETAIL_FLAG, s.CALC_LEVEL, s.PEGG_FREQ, s.CALC_ON, s.RATE_CODE,"
                    + "(select MAX(ai.AC_INT_VER_NO) as AC_INT_VER_NO FROM cbs_acc_int_rate_details ai where ai.acno = am.acno and ai.EFF_FRM_DT<=bk.date) as version,"
                    + "am.OpeningDt, am.AccStatus, ifnull(sg.LINK_TRAN_TO_PURCHASE_SALE, 'E') as LINK_TRAN_TO_PURCHASE_SALE from cbs_bankdays bk, accounttypemaster atm, accountmaster am "
                    + "left join  cbs_loan_acc_mast_sec s on am.acno = s.acno  "
                    + "left join cbs_scheme_general_scheme_parameter_master sg  on s.SCHEME_CODE = sg.SCHEME_CODE  "
                    + "where substring(am.acno,3,2) = atm.AcctCode " + acNatureQuery + "" + branchQuery + " and atm.crdbflag in ('D','B') and (am.ClosingDate = '' or am.ClosingDate is null or am.ClosingDate>'" + toDt + "') and  "
                    + "bk.date between '" + fromDt + "' and '" + toDt + "' and bk.date >= s.NEXT_INT_CALC_DT  /*  and am.acno in( '020800045601' )*/ and am.acno and s.INTEREST_TABLE_CODE is not null group by am.acno, bk.date) aa  "
                    + "left join  "
                    + "(select b.INTEREST_CODE,c.NORMAL_INTEREST_INDICATOR,c.INTEREST_SLAB_DEBIT_CREDIT_FLAG,c.NORMAL_INTEREST_PERCENTAGE, a.INTEREST_PERCENTAGE_DEBIT, "
                    + "c.INTEREST_VERSION_NO,   c.INTEREST_SLAB_VERSION_NO,   c.LOAN_PERIOD_MONTHS,   c.LOAN_PERIOD_DAYS,   c.RECORD_STATUS,   c.BEGIN_SLAB_AMOUNT,   c.END_SLAB_AMOUNT, "
                    + "b.INTEREST_CODE_DESCRIPTION,   b.INTEREST_VERSION_NO as INTEREST_VERSION_NO_b,   b.INTEREST_VERSION_DESCRIPTION, b.RECORD_STATUS as RECORD_STATUS_B,   b.START_DATE,   b.END_DATE,   b.INDICATOR_FLAG,   b.BASE_PERCENTAGE_DEBIT,   b.BASE_PERCENTAGE_CREDIT, "
                    + "a.CODE,   a.CODE_DESCRIPTION,   a.INDICATOR_FLAG as INDICATOR_FLAG_A,   a.VERSION_NO,   a.RECORD_STATUS as RECORD_STATUS_A,   a.START_DATE as START_DATE_A,   a.END_DATE as END_DATE_A, "
                    + "a.INTEREST_PERCENTAGE_DEBIT as INTEREST_PERCENTAGE_DEBIT_A,   a.INTEREST_PERCENTAGE_CREDIT as INTEREST_PERCENTAGE_CREDIT_A,cast(if(c.NORMAL_INTEREST_INDICATOR='N', c.NORMAL_INTEREST_PERCENTAGE, "
                    + "if(c.INTEREST_SLAB_DEBIT_CREDIT_FLAG = 'Dr',(c.NORMAL_INTEREST_PERCENTAGE+a.INTEREST_PERCENTAGE_DEBIT),(c.NORMAL_INTEREST_PERCENTAGE+a.INTEREST_PERCENTAGE_CREDIT ))) as decimal(25,2)) as ROI from "
                    + "(select   INTEREST_CODE,   INTEREST_VERSION_NO,   INTEREST_SLAB_DEBIT_CREDIT_FLAG,   INTEREST_SLAB_VERSION_NO,   LOAN_PERIOD_MONTHS,   LOAN_PERIOD_DAYS,   RECORD_STATUS,   BEGIN_SLAB_AMOUNT,   END_SLAB_AMOUNT,   NORMAL_INTEREST_INDICATOR,   NORMAL_INTEREST_PERCENTAGE,   PEENAL_INTEREST_INDICATOR,   PEENAL_INTEREST_PERCENTAGE,  "
                    + "CLEAN_PORTION_INDICATOR,   CLEAN_INTEREST_PERCENTAGE,   QIS_PORTION_INDICATOR,   QIS_INTEREST_PERCENTAGE,   ADDITIONAL_PORTION_INDICATOR,   ADDITIONAL_INTEREST_PERCENTAGES from cbs_loan_interest_slab_master  "
                    + "union all "
                    + " select   INTEREST_CODE,   INTEREST_VERSION_NO,   INTEREST_SLAB_DEBIT_CREDIT_FLAG,   INTEREST_SLAB_VERSION_NO,   LOAN_PERIOD_MONTHS,   LOAN_PERIOD_DAYS,   RECORD_STATUS,  "
                    + " BEGIN_SLAB_AMOUNT,   END_SLAB_AMOUNT,   NORMAL_INTEREST_INDICATOR,   NORMAL_INTEREST_PERCENTAGE,   PEENAL_INTEREST_INDICATOR,   PEENAL_INTEREST_PERCENTAGE,   CLEAN_PORTION_INDICATOR,   CLEAN_INTEREST_PERCENTAGE,   QIS_PORTION_INDICATOR,   QIS_INTEREST_PERCENTAGE,   ADDITIONAL_PORTION_INDICATOR,   ADDITIONAL_INTEREST_PERCENTAGES from cbs_loan_interest_slab_master_history ) c "
                    + "left join (select   INTEREST_CODE,   INTEREST_CODE_DESCRIPTION,   INTEREST_VERSION_NO,   INTEREST_VERSION_DESCRIPTION,   RECORD_STATUS,   START_DATE,   END_DATE,   INDICATOR_FLAG,   BASE_PERCENTAGE_DEBIT,   BASE_PERCENTAGE_CREDIT from cbs_loan_interest_code_master "
                    + "union all select   INTEREST_CODE,   INTEREST_CODE_DESCRIPTION,   INTEREST_VERSION_NO,   INTEREST_VERSION_DESCRIPTION,   RECORD_STATUS,   START_DATE,   END_DATE,   INDICATOR_FLAG,   BASE_PERCENTAGE_DEBIT,   BASE_PERCENTAGE_CREDIT from cbs_loan_interest_code_master_history  ) b "
                    + "on c.INTEREST_CODE = b.INTEREST_CODE and c.INTEREST_VERSION_NO = b.INTEREST_VERSION_NO and c.RECORD_STATUS = b.RECORD_STATUS and c.Record_Status = 'A' "
                    + "left join(select  CODE,   CODE_DESCRIPTION,   INDICATOR_FLAG,   VERSION_NO,   RECORD_STATUS,   START_DATE,   END_DATE,   INTEREST_PERCENTAGE_DEBIT,   INTEREST_PERCENTAGE_CREDIT from cbs_loan_interest_master "
                    + "union all select   CODE,   CODE_DESCRIPTION,   INDICATOR_FLAG,   VERSION_NO,   RECORD_STATUS,   START_DATE,   END_DATE,   INTEREST_PERCENTAGE_DEBIT,   INTEREST_PERCENTAGE_CREDIT from cbs_loan_interest_master_history ) a "
                    + "on a.code =  b.INDICATOR_FLAG  ) cc on aa.INTEREST_TABLE_CODE = cc.INTEREST_CODE and aa.date between  cc.START_DATE and cc.END_DATE  and aa.date between  cc.START_DATE and cc.END_DATE  "
                    + "and aa.odlimit between cc.BEGIN_SLAB_AMOUNT and cc.END_SLAB_AMOUNT "
                    + "left join  "
                    + "cbs_acc_int_rate_details air on air.acno= aa.acno and air.AC_INT_VER_NO  = aa.version "
                    + "left join  "
                    + "loan_appparameter lp "
                    + "on lp.acno = aa.acno  "
                    + "order by aa.brnCode, aa.acctNature, aa.AcctCode, aa.acno, aa.date ";
//            System.out.println("Query: " + query);
            intList = em.createNativeQuery(query).getResultList();
            if (intList.isEmpty()) {
                throw new ApplicationException("Data Not Exist");
            } else {
                Map<String, LoanIntCalcList> mapCustIdWiseCloseInt = new HashMap<String, LoanIntCalcList>();
                LoanIntCalcList pojo;
                int m = 0;
                if (!intList.isEmpty()) {
                    for (int e = 0; e < intList.size(); e++) {
                        Vector intVect = (Vector) intList.get(e);
                        pojo = new LoanIntCalcList();
                        branch = intVect.get(0).toString();
                        acNature = intVect.get(1).toString();
                        accType = intVect.get(2).toString();
                        acno = intVect.get(3).toString();
                        preToDt = intVect.get(4).toString();
                        intTableCode = intVect.get(5).toString();
                        intAppFreq = intVect.get(35).toString();
                        simpleBal = Double.parseDouble(intVect.get(36).toString());
                        closingBal = Double.parseDouble(intVect.get(6).toString());
                        npaBal = Double.parseDouble(intVect.get(7).toString());
                        if (intAppFreq.equalsIgnoreCase("C")) {
                            closingBal = closingBal + npaBal;
                        } else {
                            closingBal = simpleBal;
                        }

                        odLimit = Double.parseDouble(intVect.get(8).toString());
//                        rateOfInt = intVect.get(9) == null ? Double.parseDouble(intVect.get(12).toString()) : Double.parseDouble(intVect.get(9).toString());
//                        Inter = Double.parseDouble(intVect.get(10).toString());
//                        turnOverFlag = intVect.get(13).toString();
//                        calcLevel = intVect.get(14).toString();
//                        pegg_Freq = Integer.parseInt(intVect.get(15).toString());
//                        calc_On = intVect.get(16).toString();
//                        rate_Code = intVect.get(17).toString();
//                        version = Long.parseLong(intVect.get(18).toString());
//                        ACC_PREF_CR = Double.parseDouble(intVect.get(20).toString());
//                        AC_PREF_DR = Double.parseDouble(intVect.get(21).toString());
//                        INT_PEG_FLG = intVect.get(22).toString();
//                        PEG_FREQ_MON = Integer.parseInt(intVect.get(23).toString());
                        OpeningDt = intVect.get(24).toString();
                        loanFinistDt = intVect.get(25).toString();
                        rateOfInt = Double.parseDouble(intVect.get(26).toString());
                        accStatus = intVect.get(27).toString();
                        orderByParameter = intVect.get(28).toString();
                        double adHocRoi = 0, adHocInt = 0;

                        String intCalcBasedOnSecurity = (intVect.get(13) == null ? "N" : intVect.get(13).toString());
                        if (intCalcBasedOnSecurity.equalsIgnoreCase("Y")) {
                            String orderBy = " aa.Entrydate ";
                            if (orderByParameter.equalsIgnoreCase("A")) { //AppRoi
                                orderBy = " aa.AppRoi, aa.Entrydate";
                            } else if (orderByParameter.equalsIgnoreCase("R")) { //AppRoi desc
                                orderBy = " aa.AppRoi desc, aa.Entrydate ";
                            } else if (orderByParameter.equalsIgnoreCase("E")) { //Entrydate
                                orderBy = " aa.Entrydate ";
                            }
                            double[] interestArr = loanIntCalc.loanIntAsPerSecurity(acno, ymd.format(ymd.parse(fromDt)), ymd.format(ymd.parse(fromDt)), 1, (closingBal), ymd.format(ymd.parse(fromDt)), orderBy);
                            double interest1 = interestArr[0];
                            rateOfInt = interestArr[1];
                            if (acno.equalsIgnoreCase(PreviousAccNo)) {
                                interest = (interest + interest1);
                                product = product + closingBal;
                            } else {
                                m = m + 1;
                                interest = 0;
                                product = 0;
                                interest = (interest + interest1);
                                product = product + closingBal;
                                fromDt = intVect.get(4).toString();
                            }
                        } else {
                            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                String adhocAppDt = intVect.get(29).toString();
                                String adhocExpDt = intVect.get(30).toString();
                                adHocRoi = Double.parseDouble(intVect.get(31).toString());
                                double adHocLimit = Double.parseDouble(intVect.get(33).toString());

                                if ((!adhocAppDt.equalsIgnoreCase("1900-01-01")) && (ymd.parse(adhocAppDt).before(ymd.parse(preToDt))) || (ymd.parse(adhocAppDt).equals(ymd.parse(preToDt)))) {
                                    if (!ymd.parse(preToDt).after(ymd.parse(adhocExpDt))) {
                                        if (closingBal < 0) {
                                            if (Math.abs(closingBal) > odLimit) {
                                                if (Math.abs(closingBal) > (odLimit + adHocLimit)) {
                                                    adHocRoi = adHocRoi;
                                                    adHocInt = adHocRoi * (closingBal - odLimit * -1) / 36500;
                                                } else if ((Math.abs(closingBal) <= (odLimit + adHocLimit)) && (Math.abs(closingBal) > odLimit)) {
                                                    adHocRoi = adHocRoi;
                                                    adHocInt = adHocRoi * (closingBal - odLimit * -1) / 36500;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (acno.equalsIgnoreCase(PreviousAccNo)) {
                                if (closingBal < 0) {
                                    interest = (interest + adHocInt + (((closingBal) * rateOfInt) / 36500));
                                    product = product + closingBal;
                                }
                            } else {
                                m = m + 1;
                                interest = 0;
                                product = 0;
                                if (closingBal < 0) {
                                    interest = (interest + adHocInt + (((closingBal) * rateOfInt) / 36500));
                                    product = product + closingBal;
                                }
                                fromDt = intVect.get(4).toString();
                            }
                        }

                        pojo.setAcNo(acno);
                        pojo.setRoi(rateOfInt);
                        pojo.setSrNo(m);
                        pojo.setBranch(branch);
                        pojo.setAcType(accType);
                        pojo.setIntTableCode(intTableCode);
                        pojo.setTotalInt(interest);
                        pojo.setFirstDt(dmy.format(ymd.parse(fromDt)));
                        pojo.setClosingBal(closingBal);
                        pojo.setProduct(product);
                        pojo.setAcNature(acNature);
                        pojo.setAccStatus(accStatus);
                        pojo.setLastDt(dmy.format(ymd.parse(preToDt)));
                        pojo.setCustName(intVect.get(11).toString());
                        if (interest < 0) {
                            mapCustIdWiseCloseInt.put(acno, pojo);
                        }
//                        System.out.println("i:" + e);
                        PreviousAccNo = acno;
                    }
                    Set<Entry<String, LoanIntCalcList>> set = mapCustIdWiseCloseInt.entrySet();
                    Iterator<Entry<String, LoanIntCalcList>> it = set.iterator();
                    while (it.hasNext()) {
                        Entry<String, LoanIntCalcList> entry = it.next();
                        intCalc.add(entry.getValue());
                    }
                    Collections.sort(intCalc, new LoanIntCalcListSort());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return intCalc;
    }
}
