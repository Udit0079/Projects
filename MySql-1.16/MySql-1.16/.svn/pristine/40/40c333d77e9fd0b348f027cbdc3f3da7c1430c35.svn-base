/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.common;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.DlAcctOpenReg;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.LoanAcDetailStatementPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author root
 */
@Stateless(mappedName = "FtsPostingMgmtFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class FtsPostingMgmtFacade implements FtsPostingMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnAuth;
    NumberFormat formatter = new DecimalFormat("#.##");
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private SbIntCalcFacadeRemote intRemote;
    @EJB
    private LoanInterestCalculationFacadeRemote intLoanRemote;
    @EJB
    InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    ShareTransferFacadeRemote shareTranFacade;
    @EJB
    AccountOpeningFacadeRemote accOpenFacade;
    @EJB
    AutoTermDepositRenewalRemote autoRenewRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    AccountAuthorizationManagementFacadeRemote accountAuthFacade;
    @EJB
    TransactionManagementFacadeRemote txnRemote;

    public FtsPostingMgmtFacade() {
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yyyy");

    public String ftsPosting43CBS(String acNo, Integer tranType, Integer ty, Double amt, String dt, String valueDt, String enterBy,
            String orgnBrCode, String destBrCode, Integer tranDesc, String details, Float trsNo, Float recNo,
            Integer tranId, String termId, String auth, String authBy, String subTokenNo, Integer payBy,
            String instNo, String instDt, String tdacNo, Float vchNo, String intFlag, String closeFlag,
            String screenFlag, String txnStatus, Float tokenNoDr, String cxSxFlag, String adviceNo, String adviceBrnCode,
            String entryType) throws ApplicationException {
        try {
            // String cashMod;
            //String acctNature;
            Double crAmt = 0.0d;
            Double drAmt = 0.0d;
            // String tokenPaid;
            String tokenPaidBy;
            //float lTokenNo = 0.0f;
            // int iy;
            // String custName;
            // String version;
            Float tokenNo = 0.0f;
            String msg;
            // String acNo1 = "";
            System.out.println("Incomming Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty + " and batch No is = " + trsNo);
//            if ((acNo == null) || (acNo.equalsIgnoreCase(""))) {
//                return "ACCOUNT NO IS BLANK !";
//            }
            // iy = 1;
//            if (trsNo == null) {
//                trsNo = 0.0f;
//            }
//            if (tranDesc == null) {
//                tranDesc = 0;
//            }
//            if (tokenNo == null) {
//                tokenNo = 0.0f;
//            }
//            if (instDt == null) {
//                instDt = "";
//            }
            if (instDt == null || instDt.equals("")) {
                instDt = "19000101";
            }
            int iy = 1;
            if ((getAccountNature(acNo)).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List chkList = em.createNativeQuery("SELECT ifnull(POSTFLAG,1) FROM gltable WHERE ACNO='" + acNo + "' AND POSTFLAG IN (11,12,13)").getResultList();
                if (!chkList.isEmpty()) {
                    Vector Lst = (Vector) chkList.get(0);
                    iy = Integer.parseInt(Lst.get(0).toString());
                }
            }

            if (!enterBy.equalsIgnoreCase("System")) {
                msg = ftsDateValidate(dt, orgnBrCode);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return "" + msg;
                }
            }

            //VAR_ABB = TRANCOUNT;
            List chkList2 = em.createNativeQuery("SELECT ifnull(CODE,41) FROM parameterinfo_report WHERE REPORTNAME='VERSION'").getResultList();
            Vector verLst = (Vector) chkList2.get(0);
            String version = verLst.get(0).toString();

            if ((version.equalsIgnoreCase("")) || (!(version.equalsIgnoreCase("53")))) {
                version = "41";
            }
//            if ((acNo == null) || (acNo.equalsIgnoreCase(""))) {
//                msg = "ACCOUNT NO IS BLANK !";
//                return "" + msg;
//            }
//            if (ty == null) {
//                msg = "INVALID VALUE FOR TY ( NULL ) !";
//                return "" + msg;
//            }
//            if (!((ty == 0) || (ty == 1))) {
//                msg = "INVALID VALUE FOR TY !  " + ty;
//                return "" + msg;
//            }
            msg = ftsAcnoValidate(acNo, ty, entryType);
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return "" + msg;
            }
            if (valueDt == null) {
                valueDt = dt;
            }
            if ((amt == null) || (amt <= 0)) {
                msg = "AMOUNT CAN NOT BE ZERO OR LESS THAN ZERO !";
                return "" + msg;
            }
            if (tranType == null) {
                msg = "INVALID VALUE FOR TRANTYPE ( NULL ) !";
                return "" + msg;
            }
            if (!((tranType == 0) || (tranType == 1) || (tranType == 2) || (tranType == 5) || (tranType == 6) || (tranType == 8) || (tranType == 27))) {
                msg = "INVALID VALUE FOR TRANTYPE !   " + tranType;
                return "" + msg;
            }
            if (enterBy.equalsIgnoreCase("")) {
                msg = "ENTER BY FIELD CAN NOT BE BLANK !";
                return "" + msg;
            }
            if (((screenFlag == null) || (screenFlag.equalsIgnoreCase(""))) && (version.equalsIgnoreCase("53")) && (tranType == 1)) {
                msg = "SCREEN FLAG CAN NOT BE BLANK FOR CLEARING ENTRY !";
                return "" + msg;
            }
            if (((tranType == 2) || (tranType == 8) || (tranType == 27)) && (trsNo == null)) {
                msg = "PLEASE ENTER BATCH NO FOR TRANSFER ENTRY !";
                return "" + msg;
            }
            msg = ftsUserValidate(enterBy, orgnBrCode);

            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return "" + msg;
            }
            if (((authBy == null) || (authBy.equalsIgnoreCase(""))) && (auth != null) && (auth.equalsIgnoreCase("Y"))) {
                msg = "AUTHBY FIELD CAN NOT BLANK FOR AUTHORISED ENTRY !";
                return "" + msg;
            }
            if (payBy == null) {
                msg = "PLEASE ENTER VALID MODE OF PAYMENT/RECEIPT !";
                return "" + msg;
            }
            if (!((payBy == 1) || (payBy == 2) || (payBy == 3) || (payBy == 4) || (payBy == 5) || (payBy == 6))) {
                msg = "PLEASE ENTER VALID MODE OF PAYMENT/RECEIPT !";
                return "" + msg;
            }
            if (((payBy == 1) || (payBy == 4)) && (instNo == null)) {
                msg = "PLEASE ENTER INSTRUMENT NUMBER !";
                return "" + msg;
            }
            if (((payBy == 5) || (payBy == 6)) && (tranId == null)) {
                msg = "TRANSACTION ID IS NOT SUPPLIED !";
                return "" + msg;
            }
            if (((payBy == 5) || (payBy == 6)) && (termId == null)) {
                msg = "TERMINAL ID IS NOT SUPPLIED !";
                return "" + msg;
            }
            if ((auth == null) || (auth.equalsIgnoreCase(""))) {
                auth = "N";
            }
            if (!((auth.equalsIgnoreCase("N")) || (auth.equalsIgnoreCase("Y")))) {
                msg = "ENTER VALID MODE OF AUTH FLAG";
                return "" + msg;
            }
            List chkList3 = em.createNativeQuery("SELECT COALESCE(CASHMOD,'Y') FROM parameterinfo WHERE brncode = CAST('" + orgnBrCode + "' AS unsigned)").getResultList();
            Vector verLst1 = (Vector) chkList3.get(0);
            String cashMod = verLst1.get(0).toString();

            if ((ty == 1) && (tranType == 0) && (!((payBy == 5) || (payBy == 6))) && (cashMod.equalsIgnoreCase("Y"))) {
                tokenNo = tokenNoDr;
                if (tokenNo == null) {
                    msg = "TOKEN NO CAN NOT BE BLANK";
                    return "" + msg;
                }
                if (subTokenNo == null) {
                    msg = "SUBTOKEN NO CAN NOT BE BLANK ";
                    return "" + msg;
                }
            }

            String acctNature = getAccountNature(acNo);
            if ((acctNature == null) || (acctNature.equalsIgnoreCase(""))) {
                msg = "ACCOUNT NATURE DOES NOT EXISTS !";
                return "" + msg;
            }
            if ((acctNature.equalsIgnoreCase("OF")) && (tdacNo == null)) {
                msg = "TD ACNO HAS NOT BEEN PROVIDED !";
                return "" + msg;
            }
            if (ty == 0) {
                crAmt = amt;
                drAmt = 0d;
            } else if (ty == 1) {
                crAmt = 0d;
                drAmt = amt;
            } else {
                crAmt = 0d;
                drAmt = 0d;
            }
            if (details == null) {
                details = "";
            }
            if ((closeFlag == null) || (closeFlag.equalsIgnoreCase(""))) {
                closeFlag = null;
            }
            if ((intFlag == null) || (intFlag.equalsIgnoreCase(""))) {
                intFlag = null;
            }

            //Added by Dinesh on 21/02/2011 For CTS
            if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("Y"))) {
                msg = updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }

                msg = ftsInstDateValidate(instDt);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }
//                msg = ftsStockStatementExipryValidate(acNo,dt);
//                if (!(msg.equalsIgnoreCase("TRUE"))) {
//                    return msg;
//                }
            }

            String MSG1 = chkBal(acNo, ty, tranDesc, acctNature);
            if (MSG1.equalsIgnoreCase("CHECKBALANCE")) {
                msg = checkBalance(acNo, amt, enterBy);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return "" + msg + " for A/C No. " + acNo;
                }
            }

            //Code by Dhirendra Singh for over limit identification
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                iy = Integer.parseInt(checkBalForOdLimit(acNo, amt, enterBy));
            }
            /**
             * **************Code add by Alok*********************** regarding
             * Share Checking in Army/Postal*************
             */
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                List schemeList = em.createNativeQuery("select a.SCHEME_CODE, b.CustId from cbs_loan_acc_mast_sec a, customerid  b where a.acno = b.Acno and a.acno = '" + acNo + "' ").getResultList();
                if (!schemeList.isEmpty()) {
                    Vector schemeListVect = (Vector) schemeList.get(0);
                    String schemeCode = schemeListVect.get(0).toString();
                    String custId = schemeListVect.get(1).toString();
                    List schList = accOpenFacade.chkSchDetails(schemeCode);
                    if (!schList.isEmpty()) {
                        Vector schLst = (Vector) schList.get(0);
                        String schFlg = schLst.get(0).toString();
                        // String stOpt = schLst.get(1).toString();
                        String memFlg = schLst.get(2).toString();
                        String folioNo = "";
                        if (schFlg.equalsIgnoreCase("Y")) {
                            if (memFlg.equalsIgnoreCase("Y")) {
                                List memChk = em.createNativeQuery("select * from share_holder where custid ='" + custId + "'").getResultList();
                                if (memChk.isEmpty()) {
                                    return "SCHEME IS ONLY FOR THE MEMBER CUSTOMER !!!";
                                }
                            }

                            List shareChk = em.createNativeQuery("select reg_pass_sheet_print_event from cbs_scheme_currency_details where scheme_Code ='" + schemeCode + "'").getResultList();
                            if (!shareChk.isEmpty()) {
                                Vector shareLst = (Vector) shareChk.get(0);
                                String shareFlg = shareLst.get(0).toString();
                                if (shareFlg.equalsIgnoreCase("Y")) {
                                    String cmpSH = accOpenFacade.shareCompare(custId, Float.parseFloat(amt.toString()), schemeCode, acNo);
                                    if (!cmpSH.equalsIgnoreCase("true")) {
                                        //                                ut.rollback();
                                        return "SHARE IS NOT SUFFICIENT FOR THE LOAN !!!";
                                    }
                                }
                            }

                            String cCmp = accOpenFacade.loanAmtCompare(custId, Float.parseFloat(amt.toString()), schemeCode, folioNo.equalsIgnoreCase("") ? "msg" : "", "");
                            /**
                             * **IF folioNo is blank THEN method return in the
                             * form of Message ELSE method return in the OD
                             * value as per calculation ****
                             */
//                            if (!folioNo.equalsIgnoreCase("")) {
//                                amt = Double.parseDouble(cCmp);
//                            } else {
                            if (!cCmp.equalsIgnoreCase("true")) {
                                //                        ut.rollback();
                                return cCmp;
                            }
//                            }
                        }
                    } else {
                        //                ut.rollback();
                        return "SCHEME CODE NOT DEFINED IN MASTER TABLE !!!";
                    }
                }
            }
            /**
             * **************Code add by Alok**********************
             */
            String custName = ftsGetCustName(acNo);

            System.out.println("After Validation Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty);
            if (tranType == 0) {
                if (cashMod.equalsIgnoreCase("N")) {
                    //tokenPaid = "Y";
                    tokenPaidBy = "SYSTEM";
                } else {
                    // tokenPaid = "N";
                    tokenPaidBy = "";
                }
                if (cxSxFlag.equalsIgnoreCase("C")) {
                    iy = 8888;
                } else if (cxSxFlag.equalsIgnoreCase("S")) {
                    iy = 9999;
                }
                if (ty == 0) {
                    if ((recNo == null) || (recNo == 0)) {
                        recNo = getRecNo();
                    }
                    if (!auth.equalsIgnoreCase("Y")) {
                        //changed by Prakash Starts
                        if ((payBy == 1) && (ty == 0) && (auth.equalsIgnoreCase("N"))) {
                            msg = updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                return "CASH CREDIT BY CHEQUE IS NOT ALLOWED";
                            }
                        }
                        System.out.println("Before inserting data in tokentable_credit Account number is = " + acNo + " and amount is = " + amt
                                + " and transaction type is = " + tranType + " and TY is = " + ty);
                        //changed by Prakash Ends
                        Query insertQuery = em.createNativeQuery("INSERT INTO tokentable_credit(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,TY,TRANTYPE,RECNO,INSTNO,PAYBY,"
                                + "IY,AUTH,ENTERBY,TRANDESC,DETAILS,TOKENPAIDBY,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                                + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + ty + "," + tranType + ","
                                + recNo + ",'" + instNo + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + ",'" + details + "','"
                                + tokenPaidBy + "','" + termId + "','" + orgnBrCode + "','" + destBrCode + "'," + tranId + ")");
                        insertQuery.executeUpdate();

                        System.out.println("After inserting data in tokentable_credit Account number is = " + acNo + " and amount is = " + amt
                                + " and transaction type is = " + tranType + " and TY is = " + ty);
                    }

                    //changed by Dinesh
                    if (!auth.equalsIgnoreCase("Y")) {
                        List TOKENNOList = em.createNativeQuery("SELECT TOKENNO FROM tokentable_credit WHERE RECNO=" + recNo + " AND DT='" + dt + "' AND ORG_BRNID = '" + orgnBrCode + "'").getResultList();
                        Vector TOKENNOLst = (Vector) TOKENNOList.get(0);
                        tokenNo = Float.parseFloat(TOKENNOLst.get(0).toString());
                    }

                    if (auth.equalsIgnoreCase("Y")) {
                        Query insertQuery1 = em.createNativeQuery("INSERT INTO recon_cash_d(ACNO ,CUSTNAME, TY, DT,VALUEDT, DRAMT, CRAMT,TRANTYPE, DETAILS, "
                                + "IY, INSTNO,INSTDT,ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,TERM_ID,"
                                + "ORG_BRNID,DEST_BRNID,TRAN_ID)"
                                + "values ('" + acNo + "','" + custName + "'," + ty + ",'" + dt + "','" + valueDt + "'," + drAmt + "," + crAmt + "," + tranType + ",'"
                                + details + "'," + iy + ",'" + instNo + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recNo + "," + payBy + ",'" + authBy + "',"
                                + trsNo + ",CURRENT_TIMESTAMP," + tranDesc + "," + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','"
                                + termId + "','" + orgnBrCode + "','" + destBrCode + "'," + tranId + ")");

                        insertQuery1.executeUpdate();
                    }
                    if (auth.equalsIgnoreCase("Y")) {
//                      Query deleteQuery = em.createNativeQuery("DELETE FROM tokentable_credit WHERE RECNO=" + recNo + " AND DT='" + dt + "' AND ORG_BRNID = '" + orgnBrCode + "'");
                        Query deleteQuery = em.createNativeQuery("update tokentable_credit set auth='Y', authby='" + authBy + "' WHERE RECNO=" + recNo + " AND DT='" + dt + "' AND ORG_BRNID = '" + orgnBrCode + "'");
                        deleteQuery.executeUpdate();
                    }

                } else if (ty == 1) {
                    if ((cashMod.equalsIgnoreCase("Y")) && (!(payBy == 5 || payBy == 6))) {
                        float lTokenNo = tokenNo;
                        if (!(tokenNo != 9999999)) {
                            lTokenNo = ftsTokenTable(ty, subTokenNo, version);
                        }
                        if (lTokenNo == 0) {
                            msg = "TOKEN NO :- " + tokenNo + " HAS BEEN ISSUED !";
                            return "" + msg;
                        }
                    } else {
                        // tokenPaid = "Y";
                        tokenPaidBy = "SYSTEM";
                    }
                    if ((recNo == null) || (recNo == 0)) {
                        recNo = getRecNo();
                    }
                    if (!auth.equalsIgnoreCase("Y")) {
                        //changed by Prakash
                        if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("N"))) {
                            msg = updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                return msg;
                            }
                        }
                        //changed by Prakash Ends
                        // try {
                        Query insertQuery = em.createNativeQuery("INSERT INTO tokentable_debit(ACNO,CUSTNAME,DT,VALUEDT,DRAMT,TY,TRANTYPE,RECNO,INSTNO,INSTDT,PAYBY,"
                                + "IY,AUTH,AUTHBY,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,TOKENPAIDBY,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID,voucherno)"
                                + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + drAmt + "," + ty + ","
                                + tranType + "," + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','"
                                + authBy + "','" + enterBy + "'," + tranDesc + "," + tokenNo + ",'" + subTokenNo + "','"
                                + details + "','" + tokenPaidBy + "','" + termId + "','" + orgnBrCode + "','" + destBrCode + "'," + tranId + ","+ vchNo+")");
                        insertQuery.executeUpdate();
                        // } catch (Exception e) {
                        //      e.getMessage();
                        //  }
                    }

                    if (auth.equalsIgnoreCase("Y")) {
                        Query insertQuery1 = em.createNativeQuery("INSERT INTO recon_cash_d(ACNO ,CUSTNAME, TY, DT,VALUEDT, DRAMT, CRAMT,TRANTYPE, DETAILS, "
                                + "IY, INSTNO,INSTDT,ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,"
                                + "TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                                + "values ('" + acNo + "','" + custName + "'," + ty + ",'" + dt + "','" + valueDt + "'," + drAmt + ","
                                + crAmt + "," + tranType + ",'" + details + "'," + iy + ",'" + instNo + "','" + instDt + "','" + enterBy + "','" + auth + "',"
                                + recNo + "," + payBy + ",'" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP," + tranDesc + "," + tokenNo + ",'"
                                + tokenPaidBy + "','" + subTokenNo + "','" + termId + "','" + orgnBrCode + "','" + destBrCode + "'" + "," + tranId + ")");

                        insertQuery1.executeUpdate();
                    }
                }
            }
            if ((version.equalsIgnoreCase("53")) && (tranType == 1)) {
                if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("N"))) {
                    msg = updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        return msg;
                    }
                }
                if ((recNo == null) || (recNo == 0)) {
                    recNo = getRecNo();
                }
                Query insertQuery2 = em.createNativeQuery("INSERT INTO recon_clg_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,INSTNO,INSTDT,"
                        + "PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,SCREENFLAG,TXNSTATUS,AUTHBY,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                        + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + drAmt + "," + ty + ","
                        + tranType + "," + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + ","
                        + tokenNo + ",'" + subTokenNo + "','" + details + "','" + screenFlag + "','" + txnStatus + "','" + authBy + "','" + termId + "','"
                        + orgnBrCode + "','" + destBrCode + "'," + tranId + ")");
                insertQuery2.executeUpdate();
            }

            if ((version.equalsIgnoreCase("41")) || ((version.equalsIgnoreCase("53")) && ((tranType == 2) || (tranType == 6) || (tranType == 8)
                    || (tranType == 27))) || ((version.equalsIgnoreCase("53")) && ((tranType == 0) || (tranType == 1)) && (auth.equalsIgnoreCase("Y")))) {
                if ((recNo == null) || (recNo == 0)) {
                    recNo = getRecNo();
                }
                if ((version.equalsIgnoreCase("53")) && ((tranType == 2) || (tranType == 6) || (tranType == 8) || (tranType == 27))) {
                    if (!auth.equalsIgnoreCase("Y")) {
                        if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("N"))) {
                            msg = updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                return msg;
                            }
                        }
                        Query insertQuery3 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                                + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                                + "DEST_BRNID,TRAN_ID,ADVICENO,ADVICEBRNCODE,tdacno)"
                                + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + drAmt + ","
                                + ty + "," + tranType + "," + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "',"
                                + tranDesc + "," + tokenNo + ",'" + subTokenNo + "','" + details + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'"
                                + termId + "','" + orgnBrCode + "','" + destBrCode + "'," + tranId + ",'" + adviceNo + "','" + adviceBrnCode + "','" + tdacNo + "')");
                        insertQuery3.executeUpdate();
                        System.out.println("After inserting data in RECON_TRF_D Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty + " and batch No =" + trsNo);
                    }

                    if (auth.equalsIgnoreCase("Y")) {
                        Query updateQuery = em.createNativeQuery("UPDATE recon_trf_d SET AUTH='Y',AUTHBY='" + authBy + "' WHERE TRSNO=" + trsNo + "");
                        updateQuery.executeUpdate();

                        //Added by dinesh on 20110224 for CTS and online DDS transacyion
                        if (tranDesc == 65 || tranDesc == 121) {
                            Query insertQuery3 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,INSTNO,INSTDT,"
                                    + "PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID,ADVICENO,ADVICEBRNCODE)"
                                    + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + drAmt + "," + ty + "," + tranType + ","
                                    + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + "," + tokenNo + ",'"
                                    + subTokenNo + "','" + details + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'" + termId + "','"
                                    + orgnBrCode + "','" + destBrCode + "'," + tranId + ",'" + adviceNo + "','" + adviceBrnCode + "')");
                            insertQuery3.executeUpdate();
                        }
                        //Addition ends here
                    }
                }

                if ((auth.equalsIgnoreCase("Y")) || (version.equalsIgnoreCase("41"))) {
                    if (cxSxFlag.equalsIgnoreCase("C")) {
                        iy = 8888;
                    } else if (cxSxFlag.equalsIgnoreCase("S")) {
                        iy = 9999;
                    }
                    msg = insertRecons(acctNature, acNo, ty, amt, dt, valueDt, tranType, details, enterBy, trsNo, null, recNo, auth, authBy, tranDesc, payBy,
                            instNo, instDt, tokenNo, null, subTokenNo, iy, tdacNo, vchNo, intFlag, closeFlag, orgnBrCode, destBrCode, tranId, termId, adviceNo, adviceBrnCode);
                }
            }

            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return msg;
            }

            if ((ty == 0) && (auth.equalsIgnoreCase("Y"))) {
                msg = updateBalance(acctNature, acNo, crAmt, drAmt, "Y", "Y");
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }
            }

            if (ty == 1) {
                msg = updateBalance(acctNature, acNo, crAmt, drAmt, "Y", "Y");
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }
            }
            if ((auth.equalsIgnoreCase("Y"))) {
                if ((acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) || (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) || (acctNature.equalsIgnoreCase(CbsConstant.SS_AC))) {
                    if ((tranDesc == 0) || (tranDesc == 1) || (tranDesc == 2) || (tranDesc == 3) || (tranDesc == 4) || (tranDesc == 5) || (tranDesc == 6) || (tranDesc == 7) || (tranDesc == 8)) {
                        msg = loanDisbursementInstallment(acNo, amt, ty, authBy, dt, recNo, tranDesc, details);
                    }
                } else if ((acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                    if (((tranDesc == 0) || (tranDesc == 1) || (tranDesc == 6))) {
                        msg = loanDisbursementInstallment(acNo, amt, ty, authBy, dt, recNo, tranDesc, details);
                    }
                }
                if (!msg.equals("TRUE")) {
                    return msg;
                }
            }

            //acNo1 = acNo.substring(2, 10);
            if ((auth.equalsIgnoreCase("Y")) && (payBy == 1) && acctNature.equals(CbsConstant.PAY_ORDER) && isPoDdGlhead(acNo.substring(2, 10))) {
                msg = processBill(acNo, instNo, instDt, amt, ty, tranType, authBy);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }
            }
            return "" + msg + tokenNo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public String ftsDateValidate(String dtTransaction, String orgnBrCode) throws ApplicationException {
        try {
            if (dtTransaction == null) {
                return "Transaction Date Can Not Be Null!";
            }
            List dateList = em.createNativeQuery("select min(date) from bankdays where dayendflag1='N' and daybeginflag <>'H' and brncode = '" + orgnBrCode + "'").getResultList();
            Vector dateVect = (Vector) dateList.get(0);
            String dtWorking = dateVect.get(0).toString();
            int dateDiff = dateDiff(dtWorking, dtTransaction);
            if (dateDiff != 0) {
                return "Date Denied. Current Working Day Is :-   " + dtWorking.substring(6, 8) + "/" + dtWorking.substring(4, 6) + "/" + dtWorking.substring(0, 4);
            } else {
                return "TRUE";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public int dateDiff(String frmDate, String toDate) throws ApplicationException {
        try {
            List newDif = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY, '" + frmDate + "', '" + toDate + "')").getResultList();
            Vector vect1 = (Vector) newDif.get(0);
            String NewDiff1 = vect1.get(0).toString();
            int NewDiff = Integer.parseInt(NewDiff1);
            return NewDiff;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String ftsAcnoValidate(String acctNo, int tyFlag, String entryType) throws ApplicationException {
        try {
            int acctStatus = 0;
            int optStatus = 0;
            int postflag = 0;

            List acNatureList = em.createNativeQuery("select Acctnature from accounttypemaster where acctcode='" + getAccountCode(acctNo) + "'").getResultList();
            if (acNatureList.size() <= 0) {
                return "Invalid Account Nature";
            }
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = (String) acNatureVect.get(0);

            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                if (acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    List acNoltList = em.createNativeQuery("SELECT acno FROM td_vouchmst WHERE ofACNO='" + acctNo + "'").getResultList();
                    if (acNoltList.size() <= 0) {
                        return "No Number Exist";
                    } else {
                        Vector acno = (Vector) acNoltList.get(0);
                        acctNo = acno.get(0).toString();
                    }
                }

                String chkFlag = txnAuth.fdFidilityChk(acctNo);
                if (chkFlag.equalsIgnoreCase("true")) {
                    List statustList = em.createNativeQuery("SELECT ACCSTATUS FROM fidility_accountmaster WHERE ACNO='" + acctNo + "' and auth = 'Y'").getResultList();
                    if (statustList.size() <= 0) {
                        return "Account number does not exist or not authorized!";
                    } else {
                        Vector status = (Vector) statustList.get(0);
                        acctStatus = Integer.parseInt(status.get(0).toString());
                    }
                } else {
                    List statustList = em.createNativeQuery("SELECT ACCSTATUS FROM td_accountmaster WHERE ACNO='" + acctNo + "' and (authby is not null or authby <>'')").getResultList();
                    if (statustList.size() <= 0) {
                        return "Account number does not exist or not authorized!";
                    } else {
                        Vector status = (Vector) statustList.get(0);
                        acctStatus = Integer.parseInt(status.get(0).toString());
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List postFlagList = em.createNativeQuery("SELECT postflag FROM gltable WHERE ACNO='" + acctNo + "'").getResultList();
                if (postFlagList.size() <= 0) {
                    return "Account number does not exist !";
                } else {
                    Vector postFlag = (Vector) postFlagList.get(0);
                    postflag = Integer.parseInt(postFlag.get(0).toString());
                }
            } else {
                List opStatusList = em.createNativeQuery("SELECT ACCSTATUS,OPTSTATUS FROM accountmaster WHERE ACNO='" + acctNo + "' and (authby is not null or authby <>'')").getResultList();
                if (opStatusList.size() <= 0) {
                    return "Account number does not exist or not authorized!";
                } else {
                    Vector ele = (Vector) opStatusList.get(0);
                    acctStatus = Integer.parseInt(ele.get(0).toString());
                    optStatus = Integer.parseInt(ele.get(1).toString());
                }
            }

            if (!(acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {

                if (acctStatus == 9) {
                    return "Account is Closed";
                } else if (acctStatus == 8) {
                    return "Operation Stopped For this Account";
                } else if (acctStatus == 7 && tyFlag == 1) {
                    return "Withdrawal Stopped for this Account";
                } else if (acctStatus == 4 && tyFlag == 1) {
                    return "Account has been Frozen";
//                } else if (acctStatus == 2 && tyFlag == 1) {
//                    if (!entryType.equalsIgnoreCase("S")) { //S- For System
//                        return "Account is Marked as Inoperative";
//                    }
//                }
                } else if (acctStatus == 2) {
                    if (!entryType.equalsIgnoreCase("S")) { //S- For System
                        return "Account is Marked as Inoperative";
                    }
                }

                if (!acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || !acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                    if (optStatus == 2 && tyFlag == 1) {
//                        if (!entryType.equalsIgnoreCase("S")) { //S- For System
//                            return "Account is marked as Inoperative";
//                        }
//                    }
                    if (optStatus == 2) {
                        if (!entryType.equalsIgnoreCase("S")) { //S- For System
                            return "Account is marked as Inoperative";
                        }
                    }
                }

                if (acctStatus == 1 || acctStatus == 3 || acctStatus == 5 || acctStatus == 6 || acctStatus == 10 || acctStatus == 11 || acctStatus == 12 || acctStatus == 13 || acctStatus == 14) {
                    return "true";
                }

                if (acctStatus != 1 && acctStatus != 2 && acctStatus != 3 && acctStatus != 4 && acctStatus != 5 && acctStatus != 6 && acctStatus != 7 && acctStatus != 8 && acctStatus != 9 && acctStatus != 10 && acctStatus != 11 && acctStatus != 12 && acctStatus != 13 && acctStatus != 14) {
                    return "Sorry,Invalid Account Status";
                }
            }
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (postflag == 99) {
                    return "GL Head Not in Use";
                } else {
                    return "true";
                }
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String ftsUserValidate(String user, String orgnBrCode) throws ApplicationException {
        String message = "";
        try {
            if (user.equalsIgnoreCase("System")) {
                return "true";
            } else {
                List checkUserId = em.createNativeQuery("select userid from securityinfo "
                        + "where userid= '" + user + "' and levelid not in (5,6)").getResultList();
                if (checkUserId.size() > 0) {
                    message = "True";
                } else {
                    message = "User ID :- " + user + " Does Not Exists !";
                }
            }
//            List checkUserId = em.createNativeQuery("select userid from securityinfo "
//                    + "where userid= '" + user + "' and levelid not in (5,6)").getResultList();
//            if (checkUserId.size() > 0) {
//                message = "True";
//            } else {
//                message = "User ID :- " + user + " Does Not Exists !";
//            }
            return message;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String ftsGetBal(String acctNo) throws ApplicationException {
        try {
            String acNature = getAccountNature(acctNo);
            String tableName = CbsUtil.getReconBalanTableName(acNature);
            List balList = em.createNativeQuery("select cast(balance as decimal(25,2)) from " + tableName + " where acno='" + acctNo + "'").getResultList();
            if (balList.isEmpty()) {
                throw new ApplicationException("Account balance does not exist for " + acctNo);
            }
            Vector bal = (Vector) balList.get(0);
            return bal.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String checkBalance(String acctNo, double amount, String Userid) throws ApplicationException {
        try {
            String brCode = getCurrentBrnCode(acctNo);
            double balance = Double.parseDouble(ftsGetBal(acctNo));
            String balExceed = "";
            double lienAmount = 0;
            double limitAmount = 0;
            int acFlag = 0;
            int levelId = 0;

            String curDt = ymd.format(new Date());

            String acNature = getAccountNature(acctNo);

//            String rs = ftsDateValidate(curDt, brCode);
//            if (!rs.equalsIgnoreCase("True")) {
//                throw new ApplicationException(rs);
//            }
            List balexceedList = em.createNativeQuery("select ifnull(Balexceed,'') from parameterinfo WHERE brncode = " + Integer.parseInt(brCode)).getResultList();
            if (balexceedList.size() > 0) {
                Vector balEx = (Vector) balexceedList.get(0);
                balExceed = balEx.get(0).toString();
            }
            List levelIdList = em.createNativeQuery("select ifnull(levelid,'') from securityinfo WHERE userid = '" + Userid + "'").getResultList();
            if (levelIdList.size() > 0) {
                Vector levelIdVec = (Vector) levelIdList.get(0);
                levelId = Integer.parseInt(levelIdVec.get(0).toString());
            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                List limiamtListQuery = em.createNativeQuery("select ifnull(maxlimit,0) from loan_appparameter where acno='" + acctNo + "'").getResultList();
                if (limiamtListQuery.size() > 0) {
                    Vector amt = (Vector) limiamtListQuery.get(0);
                    limitAmount = Double.parseDouble(amt.get(0).toString());
                }
                if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List adHocLimitListQuery = em.createNativeQuery("select ifnull(adhoclimit,0) from loan_appparameter where acno='" + acctNo
                            + "' and '" + curDt + "' between Adhocapplicabledt and adhocexpiry").getResultList();
                    double adhocLimit = 0;
                    if (adHocLimitListQuery.size() > 0) {
                        Vector amt = (Vector) adHocLimitListQuery.get(0);
                        adhocLimit = Double.parseDouble(amt.get(0).toString());
                    }
                    limitAmount = limitAmount + adhocLimit;
                }
                List acflagListQuery = em.createNativeQuery("select ifnull(accstatus,1) from accountmaster where acno='" + acctNo + "'").getResultList();
                if (acflagListQuery.size() > 0) {
                    Vector flag = (Vector) acflagListQuery.get(0);
                    acFlag = Integer.parseInt(flag.get(0).toString());
                }

                if (acFlag != 10) {
                    if (Double.compare(amount, (balance + limitAmount)) == 0 || Double.compare(amount, (balance + limitAmount)) < 0) {
                        return "True";
                    } else if (balExceed.equals("M") && (levelId == 1 || levelId == 2)) {
                        return "True";
                    } else if (balExceed.equals("C") && (levelId == 1 || levelId == 2 || levelId == 3 || levelId == 4)) {
                        return "True";
                    } else {
                        return "Balance Exceeds";
                    }
                } else {
                    List lienamtListQuery = em.createNativeQuery("select ifnull(amount,0) from accountstatus where acno = " + "'" + acctNo
                            + "' and spno = (select max(spno) from accountstatus a where a.acno = '" + acctNo + "' and auth = 'Y')").getResultList();
                    if (lienamtListQuery.size() > 0) {
                        Vector lAmount = (Vector) lienamtListQuery.get(0);
                        lienAmount = Double.parseDouble(lAmount.get(0).toString());
                    }
                    if (Double.compare((amount + lienAmount), balance) > 0) {
                        return "Balance Exceeds the Lien Value = " + lienAmount;
                    } else {
                        return "True";
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                    || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {

                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {

                    List acflagQuery = em.createNativeQuery("select ifnull(accstatus,0) from accountmaster where acno='" + acctNo + "'").getResultList();
                    if (acflagQuery.size() > 0) {
                        Vector acflag1 = (Vector) acflagQuery.get(0);
                        acFlag = Integer.parseInt(acflag1.get(0).toString());
                    }
                } else {
                    List acflag1Query = em.createNativeQuery("select ifnull(accstatus,0) from td_accountmaster where acno='" + acctNo + "'").getResultList();
                    if (acflag1Query.size() > 0) {
                        Vector acflag2 = (Vector) acflag1Query.get(0);
                        acFlag = Integer.parseInt(acflag2.get(0).toString());
                    }
                }

                if ((acFlag) != 10) {
                    if (Double.compare(amount, balance) > 0) {
                        return "Balance Exceeds";
                    } else {
                        return "True";
                    }
                } else {
                    List lienamut1Query = em.createNativeQuery("select ifnull(amount,0) from accountstatus where acno = '" + acctNo + "'"
                            + " and spno = (select max(spno) from accountstatus a where a.acno = '" + acctNo + "' and auth = 'Y')").getResultList();
                    if (lienamut1Query.size() > 0) {
                        Vector acflag2 = (Vector) lienamut1Query.get(0);
                        lienAmount = Double.parseDouble(acflag2.get(0).toString());
                    }
                    if (Double.compare((amount + lienAmount), balance) > 0) {
                        return "Balance Exceeds the Lien Value = " + lienAmount;
                    } else {
                        return "True";
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String checkBalForOdLimit(String acctNo, double amount, String Userid) throws ApplicationException {
        try {
            String brCode = getCurrentBrnCode(acctNo);
            double balance = Double.parseDouble(ftsGetBal(acctNo));
            String balExceed = "";
            double limitAmount = 0;
            int acFlag = 0;
            int levelId = 0;
            String curDt = ymd.format(new Date());

            String acNature = getAccountNature(acctNo);

//            String rs = ftsDateValidate(curDt, getCurrentBrnCode(acctNo));
//            if (!rs.equalsIgnoreCase("True")) {
//                throw new ApplicationException(rs);
//            }
            List balexceedList = em.createNativeQuery("select ifnull(Balexceed,'') from parameterinfo WHERE brncode = " + Integer.parseInt(brCode)).getResultList();
            if (balexceedList.size() > 0) {
                Vector balEx = (Vector) balexceedList.get(0);
                balExceed = balEx.get(0).toString();
            }
            List levelIdList = em.createNativeQuery("select ifnull(levelid,'') from securityinfo WHERE userid = '" + Userid + "'").getResultList();
            if (levelIdList.size() > 0) {
                Vector levelIdVec = (Vector) levelIdList.get(0);
                levelId = Integer.parseInt(levelIdVec.get(0).toString());
            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List limiamtListQuery = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter where acno='" + acctNo + "'").getResultList();
                if (limiamtListQuery.size() > 0) {
                    Vector amt = (Vector) limiamtListQuery.get(0);
                    limitAmount = Double.parseDouble(amt.get(0).toString());
                }
                List adHocLimitListQuery = em.createNativeQuery("select ifnull(adhoclimit,0) from loan_appparameter where acno='" + acctNo
                        + "' and '" + curDt + "' between Adhocapplicabledt and adhocexpiry").getResultList();
                double adhocLimit = 0;
                if (adHocLimitListQuery.size() > 0) {
                    Vector amt = (Vector) adHocLimitListQuery.get(0);
                    adhocLimit = Double.parseDouble(amt.get(0).toString());
                }
                limitAmount = limitAmount + adhocLimit;
                List acflagListQuery = em.createNativeQuery("select ifnull(accstatus,1) from accountmaster where acno='" + acctNo + "'").getResultList();
                if (acflagListQuery.size() > 0) {
                    Vector flag = (Vector) acflagListQuery.get(0);
                    acFlag = Integer.parseInt(flag.get(0).toString());
                }

                if (acFlag != 10) {
                    if (Double.compare(amount, (balance + limitAmount)) == 0 || Double.compare(amount, (balance + limitAmount)) < 0) {
                        return "1";
                    } else if (balExceed.equals("M") && (levelId == 1 || levelId == 2)) {
                        return "99";
                    } else if (balExceed.equals("C") && (levelId == 1 || levelId == 2 || levelId == 3 || levelId == 4)) {
                        return "99";
                    } else {
                        return "0";
                    }
                }
            }
            return "1";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String ftsGetCustName(String acno) throws ApplicationException {
        List custNameList = new ArrayList();
        String acNat = "";
        String custName = "";
        try {
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + getAccountCode(acno) + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC) || acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                custNameList = em.createNativeQuery("select custname from td_accountmaster where acno='" + acno + "'").getResultList();
                if (!custNameList.isEmpty()) {
                    Vector recLst1 = (Vector) custNameList.get(0);
                    custName = recLst1.get(0).toString();
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                custNameList = em.createNativeQuery("select acname from gltable where acno='" + acno + "'").getResultList();
                if (!custNameList.isEmpty()) {
                    Vector recLst2 = (Vector) custNameList.get(0);
                    custName = recLst2.get(0).toString();
                }
            } else {
                custNameList = em.createNativeQuery("select custname from accountmaster where acno='" + acno + "'").getResultList();
                if (!custNameList.isEmpty()) {
                    Vector recLst3 = (Vector) custNameList.get(0);
                    custName = recLst3.get(0).toString();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return custName;
    }

    public String ftsgetPanNo(String destAcNo) throws ApplicationException {
        String PrimaryPanno = "";
        String CustID = "";
        List Pannolist = new ArrayList();
        List CustIdlist = new ArrayList();
        try {
            CustIdlist = em.createNativeQuery("select CustId from customerid where customerid.Acno='" + destAcNo + "'").getResultList();
            if (!CustIdlist.isEmpty()) {
                Vector cstid = (Vector) CustIdlist.get(0);
                CustID = cstid.get(0).toString();
            }

            Pannolist = em.createNativeQuery("select ifnull(PAN_GIRNumber,'')as PrimaryPanno from cbs_customer_master_detail where customerid ='" + CustID + "' ").getResultList();
            if (!Pannolist.isEmpty()) {
                Vector pangir = (Vector) Pannolist.get(0);
                PrimaryPanno = pangir.get(0).toString();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return PrimaryPanno;
    }

    public String ftsgetjointDetail(String destAcNo) throws ApplicationException {
        String SecondaryPanno = "";
        String jointCustId = "";
        List spannolist = new ArrayList();
        List custidlist = new ArrayList();
        try {
            custidlist = em.createNativeQuery("select ifnull(custid1, '') as jointCustId from accountmaster where accountmaster.ACNo='" + destAcNo + "'").getResultList();
            if (!custidlist.isEmpty()) {
                Vector jtdetail = (Vector) custidlist.get(0);
                jointCustId = jtdetail.get(0).toString();
            }
            spannolist = em.createNativeQuery("select ifnull(PAN_GIRNumber,'')as PrimaryPanno from cbs_customer_master_detail where customerid ='" + jointCustId + "'").getResultList();
            if (!spannolist.isEmpty()) {
                Vector pangir1 = (Vector) spannolist.get(0);
                SecondaryPanno = pangir1.get(0).toString();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return SecondaryPanno;
    }

    public String ftsgetJointname(String destAcNo) throws ApplicationException {
        String jointName = "";
        List jointholderlist = new ArrayList();
        try {
            jointholderlist = em.createNativeQuery("select ifnull(JtName1,'')as jointName from accountmaster where accountmaster.ACNo='" + destAcNo + "'").getResultList();
            Vector jointName1 = (Vector) jointholderlist.get(0);
            jointName = jointName1.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return jointName;
    }

    public Float ftsTokenTable(Integer ty, String subTokenNo, String version) throws ApplicationException {
        Float tokenNo = null;
        try {
            if (subTokenNo == null) {
                subTokenNo = "A";
            }
            List tokenList = new ArrayList();
            if (version.equalsIgnoreCase("53")) {
                if (ty == 0) {
                    tokenNo = 1.0F;
                } else if (ty == 1) {
                    tokenList = em.createNativeQuery("select TokenNo from tokentable_debit where tokenno = " + tokenNo + " and subtokenno = '" + subTokenNo + "' and Auth='N'").getResultList();
                    if (tokenList.isEmpty()) {
                        tokenNo = 1.0F;
                    } else {
                        tokenNo = 0.0F;
                    }
                } else {
                    tokenNo = 0.0F;
                }
            } else {
                //FOR VERSION 41
                if (ty == 0) {
                    tokenList = em.createNativeQuery("select ifnull(max(tokenno),0)+1  from tokentable_credit").getResultList();
                    if (!tokenList.isEmpty()) {
                        Vector recLst = (Vector) tokenList.get(0);
                        tokenNo = Float.parseFloat(recLst.get(0).toString());
                    }
                } else if (ty == 1) {
                    List tokenList1 = em.createNativeQuery("select TokenNo from tokentable where tokenno = " + tokenNo + " and subtokenno = '" + subTokenNo + "' and tokenpaid = 'N'").getResultList();
                    if (tokenList1.isEmpty()) {
                        tokenNo = 1.0F;
                    } else {
                        tokenNo = 0.0F;
                    }
                } else {
                    tokenNo = 0.0F;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tokenNo;
    }

    public String insertRecons(String actNature, String acno, Integer ty, double amt, String dt, String valueDt, Integer tranType, String details, String enterBy, Float trsno,
            String tranTime, Float recno, String auth, String authBy, Integer tranDesc, Integer payBy, String instno, String instDt, Float tokenNo, String tokenPaidBy, String subTokenNo,
            Integer iy, String tdAcno, Float voucherNo, String intFlag, String closeFlag, String orgBrnId, String destBrnId, Integer tranId, String termId, String adviceNo, String adviceBrnCode) throws ApplicationException {
        double balance = 0;
        double cramt = 0;
        double dramt = 0;
        if (valueDt == null) {
            valueDt = dt;
        }
        if (trsno == null) {
            trsno = (float) 0.0;
        }
        if (tranTime == null) {
            tranTime = "";
        }
        if (recno == null) {
            recno = (float) 0;
        }
        if (auth == null) {
            auth = "N";
        }
        if (authBy == null) {
            authBy = "";
        }
        if (tranDesc == null) {
            tranDesc = 0;
        }
        if (payBy == null) {
            payBy = 3;
        }
        if (instno == null) {
            instno = "";
        }
//        if (instDt == null) {
//            instDt = "";
//        }
        if (instDt == null || instDt.equals("")) {
            instDt = "19000101";
        }
        if (tokenNo == null) {
            tokenNo = (float) 0;
        }
        if (tokenPaidBy == null) {
            tokenPaidBy = "";
        }
        if (subTokenNo == null) {
            subTokenNo = "A";
        }
        if (iy == null) {
            iy = 1;
        }
        if (tdAcno == null) {
            tdAcno = "";
        }
        if (voucherNo == null) {
            voucherNo = (float) 0;
        }
        if (intFlag == null) {
            intFlag = "";
        }
        if (orgBrnId == null) {
            orgBrnId = "";
        }
        if (destBrnId == null) {
            destBrnId = "";
        }
        if (tranId == null) {
            tranId = 0;
        }
        if (termId == null) {
            termId = "";
        }

        try {
            if (tranTime.equalsIgnoreCase("")) {
                List checkDate = em.createNativeQuery("select CURRENT_TIMESTAMP").getResultList();
                Vector checkDateVect = (Vector) checkDate.get(0);
                tranTime = checkDateVect.get(0).toString();
            }
            if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || actNature.equalsIgnoreCase(CbsConstant.OF_AC) || actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || actNature.equalsIgnoreCase(CbsConstant.SS_AC) || actNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                List checkActNature = em.createNativeQuery("select clearedbalance from reconbalan where acno='" + acno + "'").getResultList();
                if (checkActNature.size() <= 0) {
                    return "Invalid A/c No " + acno;
                }
                Vector actNatureVect = (Vector) checkActNature.get(0);
                balance = Float.parseFloat(actNatureVect.get(0).toString());
            } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List checkActNature = em.createNativeQuery("select clearedbalance from ca_reconbalan where acno='" + acno + "'").getResultList();
                if (checkActNature.size() <= 0) {
                    return "Invalid A/c No " + acno;
                }
                Vector actNatureVect = (Vector) checkActNature.get(0);
                balance = Float.parseFloat(actNatureVect.get(0).toString());
            } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List checkActNature = em.createNativeQuery("select clearedbalance from td_reconbalan where acno='" + acno + "'").getResultList();
                if (checkActNature.size() <= 0) {
                    return "Invalid A/c No " + acno;
                }
                Vector actNatureVect = (Vector) checkActNature.get(0);
                balance = Float.parseFloat(actNatureVect.get(0).toString());
            } else {
                return "Invalid AccountNature of A/c No :-" + acno;
            }

            if (ty == 0) {
                cramt = amt;
                dramt = 0;
            } else if (ty == 1) {
                cramt = 0;
                dramt = amt;
            } else {
                return "Invalid Transaction Mode for Account No :- " + acno;
            }

            if (recno == 0) {
                recno = getRecNo();
            }
            if (recno == 0) {
                return "Problem in Recno generation for Account No :- " + acno;
            }
            if (ty == 1) {
                balance = balance;
            } else {
                balance = balance - dramt + cramt;
            }
            /**
             * *end here**
             */
            if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                Integer varinsertReconList = em.createNativeQuery("insert into recon( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ","
                        + "'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recno + "," + payBy + ",'" + authBy + "'," + trsno + "," + tranDesc + ","
                        + "" + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + ","
                        + "'" + termId + "' )").executeUpdate();
                if (varinsertReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                Integer varinsertDdstransactionList = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, InstNo, instDt, EnterBy,Auth, recno, payby, Authby, trsno, TokenNo,SubTokenNo,trantime,org_brnid,dest_brnid,tran_id,"
                        + "term_id,trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO,TOKENPAIDBY)values('" + acno + "', " + ty + ",'" + dt + "','" + valueDt + "'," + dramt + ","
                        + "" + cramt + "," + balance + "," + tranType + ",'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "', "
                        + "" + recno + ", " + payBy + ",'" + authBy + "'," + trsno + "," + tokenNo + ",'" + subTokenNo + "','" + tranTime + "',"
                        + "'" + orgBrnId + "','" + destBrnId + "'," + tranId + ",'" + termId + "'," + tranDesc + ",'','','','" + instno + "',"
                        + "'" + tokenPaidBy + "')").executeUpdate();
                if (varinsertDdstransactionList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                Integer varinsertOfrecnoList = em.createNativeQuery("insert into of_recon( acno,tdacno,ty,dt, valueDt,Dramt, CrAmt, Balance, TranType,"
                        + "details,EnterBy, Auth, recno,Authby, trsno,TranDesc, TokenNo,SubTokenNo,instno,payby,iy,Tokenpaidby,voucherno,intflag,"
                        + "closeflag,trantime,org_brnid,dest_brnid,tran_id,term_id )values('" + acno + "','" + tdAcno + "', " + ty + ",'" + dt + "'"
                        + ",'" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ",'" + details + "', '" + enterBy + "','" + auth + "',"
                        + "" + recno + ",'" + authBy + "'," + trsno + ", " + tranDesc + ", " + tokenNo + ", '" + subTokenNo + "','" + instno + "',"
                        + "" + payBy + "," + iy + ",'" + tokenPaidBy + "','" + voucherNo + "','" + intFlag + "','" + closeFlag + "','" + tranTime + "',"
                        + "'" + orgBrnId + "','" + destBrnId + "'," + tranId + ",'" + termId + "' )").executeUpdate();
                if (varinsertOfrecnoList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actNature.equalsIgnoreCase(CbsConstant.SS_AC) || actNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                Integer varinsertLoanReconList = em.createNativeQuery("insert into loan_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,TokenpaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id ) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ","
                        + "" + balance + "," + tranType + " , '" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "',"
                        + "" + recno + "," + payBy + ",'" + authBy + "'," + trsno + ",  " + tranDesc + "," + tokenNo + ",'" + tokenPaidBy + "', "
                        + "'" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + "," + tranId + " )").executeUpdate();
                if (varinsertLoanReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                Integer varinsertGlReconList = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id,AdviceNo,AdviceBrnCode) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ","
                        + "'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recno + "," + payBy + ",'" + authBy + "'," + trsno + "," + tranDesc + ","
                        + "" + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + ","
                        + "'" + termId + "','" + adviceNo + "','" + adviceBrnCode + "' )").executeUpdate();
                if (varinsertGlReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                Integer varinsertRdReconList = em.createNativeQuery("insert into rdrecon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ","
                        + "'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recno + "," + payBy + ",'" + authBy + "'," + trsno + "," + tranDesc + ","
                        + "" + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + ","
                        + "'" + termId + "' )").executeUpdate();
                if (varinsertRdReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Integer varinsertCaReconList = em.createNativeQuery("insert into ca_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ","
                        + "'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recno + "," + payBy + ",'" + authBy + "'," + trsno + "," + tranDesc + ","
                        + "" + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + ","
                        + "'" + termId + "' )").executeUpdate();
                if (varinsertCaReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                if (closeFlag == null) {
                    Integer varinsertTdReconList = em.createNativeQuery("insert into td_recon(acno , ty, dt, valueDt,Dramt, CrAmt, Balance, TranType, "
                            + "details,EnterBy, Auth, recno,Authby, trsno,TranDesc, TokenNo,SubTokenNo,instno,instDt,payby,iy,tokenPaidBy,VoucherNo,IntFlag,"
                            + "trantime,org_brnid,dest_brnid,tran_id,term_id )values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + ","
                            + "" + cramt + ", " + balance + "," + tranType + ", '" + details + "','" + enterBy + "','" + auth + "', " + recno + ","
                            + "'" + authBy + "', " + trsno + "," + tranDesc + "," + tokenNo + ",'" + subTokenNo + "','" + instno + "','" + instDt + "'," + payBy + ", "
                            + "" + iy + ",'" + tokenPaidBy + "'," + voucherNo + ",'" + intFlag + "','" + tranTime + "','" + orgBrnId + "',"
                            + "'" + destBrnId + "'," + tranId + ",'" + termId + "')").executeUpdate();
                    if (varinsertTdReconList <= 0) {
                        return "Insertion Problem in Recons for A/c No :- " + acno;
                    }
                } else {
                    Integer varinsertTdReconList = em.createNativeQuery("insert into td_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType, "
                            + "details,EnterBy, Auth, recno,Authby, trsno,TranDesc, TokenNo,SubTokenNo,instno, instDt,payby,iy,tokenPaidBy,VoucherNo,IntFlag,"
                            + "CloseFlag,trantime,org_brnid,dest_brnid,tran_id,term_id )values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + ","
                            + "" + cramt + ", " + balance + "," + tranType + ", '" + details + "','" + enterBy + "','" + auth + "', " + recno + ","
                            + "'" + authBy + "', " + trsno + "," + tranDesc + "," + tokenNo + ",'" + subTokenNo + "','" + instno + "','" + instDt + "'," + payBy + ", "
                            + "" + iy + ",'" + tokenPaidBy + "'," + voucherNo + ",'" + intFlag + "','" + closeFlag + "','" + tranTime + "','" + orgBrnId + "',"
                            + "'" + destBrnId + "'," + tranId + ",'" + termId + "')").executeUpdate();
                    if (varinsertTdReconList <= 0) {
                        return "Insertion Problem in Recons for A/c No :- " + acno;
                    }
                }

            } else {
                return "Invalid Accountnature for A/c No :- " + acno;
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public Float getRecNo() throws ApplicationException {
        try {
            //New One
            float recno = 0f;
            int n = em.createNativeQuery("UPDATE reconvmast SET recno = LAST_INSERT_ID(recno + 1)").executeUpdate();
            if (n > 0) {
                List list = em.createNativeQuery("SELECT LAST_INSERT_ID()").getResultList();
                Vector ele = (Vector) list.get(0);
                recno = Float.parseFloat(ele.get(0).toString());
            }
            return recno;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void updateRecNo(float recNo) throws ApplicationException {
        try {
            int n = em.createNativeQuery("UPDATE reconvmast SET recno = " + recNo).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in RecNo updation");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String updateBalance(String acctNature, String acno, double cramt, double dramt,
            String balanceflag, String clearedbalanceflag) throws ApplicationException {
        if (balanceflag == null || balanceflag.equalsIgnoreCase("")) {
            balanceflag = "Y";
        }
        if (clearedbalanceflag == null || clearedbalanceflag.equalsIgnoreCase("")) {
            clearedbalanceflag = "Y";
        }
        double balance = 0;
        double clearedbalance = 0;
        try {
            if (balanceflag.equalsIgnoreCase("Y")) {
                balance = cramt - dramt;
            }
            if (clearedbalanceflag.equalsIgnoreCase("Y")) {
                clearedbalance = cramt - dramt;
            }
            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.SS_AC) || acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                List checkList = em.createNativeQuery("select * from reconbalan where acno='" + acno + "'").getResultList();
                if (checkList.size() > 0) {
                    Integer varupdateReconBalanList = em.createNativeQuery("Update reconbalan set balance=ifnull(balance,0) + " + balance + " ,clearedbalance = ifnull(clearedbalance,0) + " + clearedbalance + ",dt=CURRENT_TIMESTAMP"
                            + " where acno='" + acno + "'").executeUpdate();
                    if (varupdateReconBalanList <= 0) {
                        return "ReconBalan is not updated";
                    }
                } else {
                    Integer varinsertReconBalanList = em.createNativeQuery("Insert into reconbalan(acno,balance,ClearedBalance,dt)"
                            + " values('" + acno + "'," + balance + ", " + clearedbalance + ",CURRENT_TIMESTAMP)").executeUpdate();
                    if (varinsertReconBalanList <= 0) {
                        return "Data is not inserted in ReconBalan";
                    }
                }
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List checkList = em.createNativeQuery("select * from ca_reconbalan where acno='" + acno + "'").getResultList();
                if (checkList.size() > 0) {
                    Integer varupdateCaReconbalanList = em.createNativeQuery("Update ca_reconbalan set balance="
                            + "ifnull(balance,0) + " + balance + " ,clearedbalance = ifnull(clearedbalance,0) +"
                            + " " + clearedbalance + ",dt=CURRENT_TIMESTAMP where acno='" + acno + "'").executeUpdate();
                    if (varupdateCaReconbalanList <= 0) {
                        //ut.rollback();
                        return "CA_Reconbalan is not updated";
                    }
                } else {
                    Integer varinsertCaReconbalanList = em.createNativeQuery("Insert into ca_reconbalan(acno,balance,"
                            + "ClearedBalance,dt) values('" + acno + "'," + balance + ", " + clearedbalance + " ,CURRENT_TIMESTAMP)").executeUpdate();

                    if (varinsertCaReconbalanList <= 0) {
                        return "Data is not inserted in CA_Reconbalan";
                    }
                }
            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                List checkList = em.createNativeQuery("select * from td_reconbalan where acno='" + acno + "'").getResultList();
                if (checkList.size() > 0) {
                    Integer varupdateTdreconbalanList = em.createNativeQuery("Update td_reconbalan set "
                            + "balance=ifnull(balance,0) + " + balance + " ,clearedbalance = ifnull(clearedbalance,0) + " + clearedbalance + ","
                            + "dt=CURRENT_TIMESTAMP where acno='" + acno + "'").executeUpdate();
                    if (varupdateTdreconbalanList <= 0) {
                        return "TdReconbalan is not updated";
                    }
                } else {
                    Integer varinsertTdreconbalanList = em.createNativeQuery("Insert into td_reconbalan(acno,balance,"
                            + "ClearedBalance,dt) values('" + acno + "'," + balance + ", " + clearedbalance + ",CURRENT_TIMESTAMP)").executeUpdate();
                    if (varinsertTdreconbalanList <= 0) {
                        return "Data is not inserted in TdReconbalan";
                    }
                }
            } else {
                return "Invalid AccountNature of A/c No :- " + acno;
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String updateCheque(String acno, Integer payBy, Integer ty, Float chqNo1, String authBy) throws ApplicationException {
        try {
            if (acno == null) {
                return "Account No Can Not Be Blank !";
            } else if (payBy == null) {
                return "Invalid Mode of Receipt (PAYBY) :- ( '" + payBy + "' )";
            } else if (ty == null) {
                return "Invalid Type of Transaction(TY) :- ( '" + ty + "' )";
            } else if (chqNo1 == null || chqNo1 == 0) {
                return "Invalid Cheque No :- ( '" + chqNo1 + "' )";
            } else if (authBy == null) {
                return "Enter By Field Can Not Be Blank !";
            }
            List chq = em.createNativeQuery("SELECT CAST(" + chqNo1 + " AS unsigned)").getResultList();
            Vector chq1 = (Vector) chq.get(0);
            int chqNo = Integer.parseInt(chq1.get(0).toString());

            String acNat = getAccountNature(acno);
            if ((payBy == 1) && (ty == 1) && (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {

                if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    return "TRUE";
                }
                String status = "";
                String tableName = "chbook_sb";
                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    tableName = "chbook_ca";
                }
                List chk2 = em.createNativeQuery("select statusflag from " + tableName + " where acno='" + acno + "' and chqno=" + chqNo + "").getResultList();
                if (!chk2.isEmpty()) {
                    Vector recLst = (Vector) chk2.get(0);
                    status = recLst.get(0).toString();
                }
                if (status.equalsIgnoreCase("F")) {
                    Query updateQuery = em.createNativeQuery("update " + tableName + " set statusflag='U', lastupdateby='" + authBy + "',"
                            + "lastupdatedt=DATE_FORMAT(curdate(),'%Y%m%d') where acno='" + acno + "' and chqno=" + chqNo + "");
                    int var = updateQuery.executeUpdate();

                    if (var <= 0) {
                        return "Problem in data updation in chbook table";
                    } else {
                        return "TRUE";
                    }
                } else if (status.equalsIgnoreCase("S")) {
                    return "Cheque:- " + chqNo + "  Has Been Marked For STOP PAYEMENT.";

                } else if (status.equalsIgnoreCase("U")) {
                    return "Cheque:- " + chqNo + "  Has Already Been Used.";
                } else {
                    return "Instrument No:- " + chqNo + "  Does Not Exists For Ac No :- " + acno;
                }
            } else {
                return "Cheque Facility is Not Available for Account :- " + acno;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String chequeStatus(String acno, String chqNo) throws ApplicationException {
        try {
            if (acno == null || acno.trim().equals("")) {
                return "Account No Can Not Be Blank !";
            } else if (chqNo == null || chqNo.trim().equals("")) {
                return "Invalid Cheque No :- ( '" + chqNo + "' )";
            }
            String acNat = getAccountNature(acno);
            if (!(acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                return "Cheque Facility is Not Available for Account :- " + acno;
            }
            if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                return "TRUE";
            }
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                String status = "";
                String tableName = "chbook_sb";
                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    tableName = "chbook_ca";
                }
                List chqList = em.createNativeQuery("select statusflag from " + tableName + " where "
                        + "acno='" + acno + "' and cast(chqno as char(10))=" + chqNo + "").getResultList();
                if (chqList.isEmpty()) {
                    return "Instrument No:- " + chqNo + "  Does Not Exists For Ac No :- " + acno;
                }
                Vector ele = (Vector) chqList.get(0);
                status = ele.get(0).toString().trim();

                if (status.equalsIgnoreCase("S")) {
                    return "Cheque:- " + chqNo + "  Has Been Marked For STOP PAYEMENT.";
                } else if (status.equalsIgnoreCase("U")) {
                    return "Cheque:- " + chqNo + "  Has Already Been Used.";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "TRUE";
    }

    public String processBill(String acno, String instNo, String instDt, double amount, Integer ty, Integer tranType, String authBy) throws ApplicationException {
        String message = "Start of Proc Process Bill";
        int var = 0;
        List rsList = em.createNativeQuery("select instnature, instcode from billtypemaster where glhead = '" + acno.substring(2, 10) + "'").getResultList();
        if (rsList.isEmpty()) {
            throw new ApplicationException("GL Head does not exist in Bill Type Master");
        }
        Vector ele1 = (Vector) rsList.get(0);
        if (ele1.get(0) == null) {
            throw new ApplicationException("GL Head does not exist in Bill Type Master");
        }
        if (rsList.size() > 1) {
            return "Same GL head define in bill type master for different Bill Type";
        }
        String bNature = ele1.get(0).toString();
        String billType = ele1.get(1).toString();

        if (bNature.equalsIgnoreCase("PO")) {
            List brList = em.createNativeQuery("select alphaCode from branchmaster where  brncode= '" + acno.substring(0, 2) + "'").getResultList();
            if (brList.isEmpty()) {
                return "Data does not exist in branch master";
            }
            String issueBr = "";
            Vector ele = (Vector) brList.get(0);
            if (ele.get(0) != null) {
                issueBr = ele.get(0).toString();
            }

            Float poInstNo = Float.parseFloat(instNo);

            if (!em.createNativeQuery("select instno from  bill_lost a  WHERE a.INSTNO=" + poInstNo + " and a.billtype='" + billType + "' and a.issueBr = '" + issueBr + "' and a.origindt='" + instDt + "' and auth='Y' and status='L'").getResultList().isEmpty()) {
                message = "542_Pay Order No " + instNo + " Is Marked Lost!";
            } else if (!em.createNativeQuery("select * from bill_po where instno =" + poInstNo + " and amount=" + amount + "  and status='Cancelled' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                message = "543_Pay Order No " + instNo + " Is Marked Cancelled !";
            } else if (em.createNativeQuery("select * from bill_po where instno =" + poInstNo + " and amount=" + amount + " and validationdt='" + instDt + "' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                message = "544_Pay Order No " + instNo + " Does Not Exists!";
            } else if (!em.createNativeQuery("select * from bill_po where instno =" + poInstNo + " and amount=" + amount + " and validationdt='" + instDt + "' and status='PAID' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                message = "545_Pay Order No " + instNo + " Already Used!";
            } else if (!em.createNativeQuery("select * from bill_po where instno =" + poInstNo + " and amount=" + amount + "  and status='Issued' and validationdt='" + instDt + "' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                Query updateQuery = em.createNativeQuery("update bill_po set status='Paid',lastupdateby='" + authBy + "',"
                        + " dt=DATE_FORMAT(curdate(),'%Y%m%d'),ty=" + ty + " where instno =" + poInstNo + " and amount=" + amount
                        + " and status='Issued' and validationdt='" + instDt + "' AND acno = '" + acno + "'");
                var = updateQuery.executeUpdate();
                message = "TRUE";
            }
        } else if (bNature.equalsIgnoreCase("DD")) {
            Float poInstNo = Float.parseFloat(instNo);
            if (!em.createNativeQuery("select * from bill_lost where billtype = 'DD' and instno =" + poInstNo + " and status='Lost'").getResultList().isEmpty()) {
                message = "546_DD  No " + instNo + " Is Marked Lost!";
            } else if (!em.createNativeQuery("select * from bill_dd where instno =" + poInstNo + " and amount=" + amount + "  and status='Cancelled' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                message = "547_DD  No " + instNo + " Is Marked Cancelled !";
            } else if (em.createNativeQuery("select * from bill_dd where instno =" + poInstNo + " and amount=" + amount + " and origindt='" + instDt + "' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                message = "548_DD No " + instNo + " Does Not Exists!";
            } else if (!em.createNativeQuery("select * from bill_dd where instno =" + poInstNo + " and amount=" + amount + " and origindt='" + instDt + "' and status='PAID' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                message = "549_DD No " + instNo + " Already Used!";
            } else if (!em.createNativeQuery("select * from bill_dd where instno =" + poInstNo + " and amount=" + amount + " and origindt='" + instDt + "' and status='Issued' AND acno = '" + acno + "'").getResultList().isEmpty()) {
                Query updateQuery = em.createNativeQuery("update bill_dd set status='Paid',lastupdateby='" + authBy + "',"
                        + " dt=DATE_FORMAT(curdate(),'%Y%m%d'),ty=" + ty + " where instno =" + poInstNo + " and amount=" + amount + " and origindt='" + instDt
                        + "' and status='Issued' AND acno = '" + acno + "'");
                var = updateQuery.executeUpdate();
                message = "TRUE";
            }
        }
        return message;
    }

    /*
     * 0 : Others 1 : Installment 2 : Principle 3 : Interest[It is running for
     * interest posting] 4 : Interest Posting 5 : Prcessing Charges 6 :
     * Disbursement 7 : Cheque Return Charges 8 : Charges 9 : Contra 21:
     * Passbook / Statement Chg. 22: Ledger Folio Chg. 23: Cheque Book Chg. 24:
     * Stop Paymnet Chg. 26: Cheque Return Chg. 27: Standing Instruction Chg.
     * 29: Processing & Inspection Chg. 30: Late Installment Chg. 31: Insurance
     * Chg. 101:Pre-Payment
     */
    public String loanDisbursementInstallment(String acno, double amt, int ty, String authby, String dt, float recno, int transdesc, String remarks) throws ApplicationException {
        String message = "", acNature = "", eiFlowId = "", prinDemFlowId = "", disbFlowId = "", colFlowId = "", intDemFlowId = "", penalIntDemFlowId = "", overdueIntFlowId = "", pastDueColNPAFlowId = "", chgDemFlowId = "";
        int var = 0, var1 = 0, var3 = 0, var4 = 0;
        Integer sno, sno_paid = 0, sno_unpaid = 0, sno_unpaid_max = 0;
        double excessamt = 0.0d, p_excessamt = 0.0d, installmentamt = 0.0d, excessamt_unpaid = 0.0d, totalamt = 0.0d, overFlowAmt = 0.0d;
        if (remarks == null || remarks.equalsIgnoreCase("")) {
            remarks = "NOT REQUIRED";
        }
        try {
            List acNatureList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + getAccountCode(acno) + "'").getResultList();
            if (!acNatureList.isEmpty()) {
                Vector acNatureVector = (Vector) acNatureList.get(0);
                acNature = acNatureVector.get(0).toString();
            } else {
                message = "There is no A/c Nature corressponding this A/c No.";
                throw new ApplicationException(message);
            }

            if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                String schemeCode = "";
                List schemeCodeList = em.createNativeQuery("select a.scheme_code from cbs_loan_acc_mast_sec a, loan_appparameter b where a.acno = b.acno and a.acno ='" + acno + "'").getResultList();
                if (schemeCodeList.isEmpty()) {
                    throw new ApplicationException("Schemecode does not exist in CBS_LOAN_ACC_MAST_SEC table for A/c No. :" + acno);
                } else {
                    for (int i = 0; i < schemeCodeList.size(); i++) {
                        Vector schemeCodeVector = (Vector) schemeCodeList.get(i);
                        schemeCode = schemeCodeVector.get(0).toString();
                    }
                }

                List demandFlowIdList = em.createNativeQuery("select ei_flow_id, principal_flow_id, disbursement_flow_id,collection_flow_id, int_demand_flow_id, penal_int_demand_flow_id, overdue_int_demand_flow_id,past_due_collection_flow_id, "
                        + "charge_demand_flow_id from cbs_scheme_loan_prepayment_details where scheme_code =  '" + schemeCode + "'").getResultList();

                if (demandFlowIdList.isEmpty()) {
                    throw new ApplicationException("Demand Flow Id does not exist in CBS_SCHEME_LOAN_PREPAYMENT_DETAILS table for Schemecode :" + schemeCode);
                } else {
                    for (int i = 0; i < demandFlowIdList.size(); i++) {
                        Vector demandFlowIdVector = (Vector) demandFlowIdList.get(i);
                        eiFlowId = demandFlowIdVector.get(0).toString();
                        prinDemFlowId = demandFlowIdVector.get(1).toString();
                        disbFlowId = demandFlowIdVector.get(2).toString();

                        colFlowId = demandFlowIdVector.get(3).toString();
                        intDemFlowId = demandFlowIdVector.get(4).toString();
                        penalIntDemFlowId = demandFlowIdVector.get(5).toString();
                        overdueIntFlowId = demandFlowIdVector.get(6).toString();
                        pastDueColNPAFlowId = demandFlowIdVector.get(7).toString();
                        chgDemFlowId = demandFlowIdVector.get(8).toString();
                    }
                }

                if (ty == 1) {
                    int dmdSchNo = 0;
                    Integer dmdSrl = null, ShdlNo = null;
                    Date dmdEffDt = null;
                    double dmdAmt = 0, emiAmt = 0, dmdAdjAmt = 0;

                    List serialNumList = em.createNativeQuery("select dmd_srl_num from cbs_loan_dmd_table  where dmd_date = '" + dt + "'").getResultList();
                    if (serialNumList.size() > 0) {
                        Vector serialNumVector = (Vector) serialNumList.get(0);
                        dmdSrl = Integer.parseInt(serialNumVector.get(0).toString());
                    } else {
                        List dmdSerialNumList = em.createNativeQuery("select ifnull(max(dmd_srl_num),0) from cbs_loan_dmd_table").getResultList();
                        if (dmdSerialNumList.size() > 0) {
                            Vector dmdSerialNumVector = (Vector) dmdSerialNumList.get(0);
                            dmdSrl = Integer.parseInt(dmdSerialNumVector.get(0).toString()) + 1;
                        }
                    }

                    List shdlNumList = em.createNativeQuery("select ifnull(max(shdl_num),0) from cbs_loan_dmd_table").getResultList();
                    if (shdlNumList.size() > 0) {
                        Vector shdlNumVector = (Vector) shdlNumList.get(0);
                        ShdlNo = Integer.parseInt(shdlNumVector.get(0).toString());
                        if (ShdlNo == 0) {
                            ShdlNo = 1;
                        } else {
                            ShdlNo = ShdlNo + 1;
                        }
                    }
                    if (transdesc == 6) {
                        shdlNumList = em.createNativeQuery("select ifnull(max(sno),0)+1 from loandisbursement where acno='" + acno + "'").getResultList();
                        Vector shdlNumVector = (Vector) shdlNumList.get(0);
                        sno = Integer.parseInt(shdlNumVector.get(0).toString());

                        Query insertQuery = em.createNativeQuery("insert into loandisbursement(acno,sno,amtdisbursed,disbursementdt,remarks,recno)"
                                + " values('" + acno + "'," + sno + "," + amt + ",'" + dt + "','" + remarks + "','" + recno + "')");
                        var = insertQuery.executeUpdate();

                        if (var <= 0) {
                            throw new ApplicationException("Insertion problem in LoanDisbursement table");
                        } else {
                            message = "TRUE";
                        }
                    } else if (transdesc == 1) {
                        message = "Current Related To is not allowed for debit";
                    } else {
                        message = "TRUE";
                    }
                } else {
                    if (ty == 0) {
                        if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            message = "TRUE";
                            /*} else if (transdesc == 101) {
                             message = "TRUE";
                             */
                        } else if (transdesc == 0 || transdesc == 1 || transdesc == 2 || transdesc == 3 || transdesc == 67 || transdesc == 66) {

                            /**
                             * *Getting the collection amount if previous
                             * demand overflowed**
                             */
                            String ofLowAdjFlg = "";
//                            List getLoanAdditionalTrList = em.createNativeQuery("select ifnull(sum(overflow_amt),0) from cbs_la_additional_tr_details  where acno = '" + acno + "' and dmd_flow_id in('" + colFlowId + "','" + pastDueColNPAFlowId + "') and int_routing_flag = 'L' and post_due_flag = 'N' and tr_reversal_flag = 'N'").getResultList();
//
//                            if (getLoanAdditionalTrList.size() > 0) {
//                                Vector getLoanAdditionalTrVector = (Vector) getLoanAdditionalTrList.get(0);
//                                overFlowAmt = Double.parseDouble(getLoanAdditionalTrVector.get(0).toString());
//                                if (overFlowAmt > 0) {
//                                    ofLowAdjFlg = "Y";
//                                } else {
//                                    ofLowAdjFlg = "N";
//                                }
//                            }
                            double totalAdjAmt = amt + overFlowAmt;
                            int dmdSchNo = 0;
                            Date dmdEffDt = null, dmdDt = null;
                            String dmdFlowId = "";
                            double dmdAmt = 0, penalAmt = 0, intAmt = 0, chgAmt = 0;

                            List getDmdList = em.createNativeQuery("select acno,shdl_num,dmd_flow_id,dmd_srl_num,dmd_eff_date,dmd_ovdu_date,dmd_amt,last_adj_date,ifnull(tot_adj_amt,0) as tot_adj_amt, ifnull(ei_amt,0) as ei_amt,dmd_date from cbs_loan_dmd_table  where acno = '" + acno + "' and dmd_date <= '" + dt + "' and del_flg = 'N' and latefee_status_flg in ('N','L','U') and (dmd_amt-ifnull(tot_adj_amt,0) ) > 0  order by acno, dmd_date, shdl_num, dmd_srl_num").getResultList();
                            if (getDmdList.size() > 0) {

                                /**
                                 * *Determining the collection flow according
                                 * to the collection flow id as per sequence**
                                 */
                                List collectionSeqList = em.createNativeQuery("select demand_id from cbs_loan_collection_flow_sequence where collection_flow_id='" + colFlowId + "' order by sequence_no").getResultList();
                                if (collectionSeqList.isEmpty()) {
                                    throw new ApplicationException("Collection flow sequence is not found");
                                } else {
                                    for (int k = 0; k < collectionSeqList.size(); k++) {
                                        Vector collectionSeqVector = (Vector) collectionSeqList.get(k);
                                        String demandId = collectionSeqVector.get(0).toString();
                                        for (int i = 0; i < getDmdList.size(); i++) {
                                            Vector getDmdVect = (Vector) getDmdList.get(i);

                                            String dmdAcNo = getDmdVect.get(0).toString();
                                            dmdSchNo = Integer.parseInt(getDmdVect.get(1).toString());
                                            dmdFlowId = getDmdVect.get(2).toString();
                                            int dmdSrNo = Integer.parseInt(getDmdVect.get(3).toString());

                                            dmdEffDt = ymmd.parse(getDmdVect.get(4).toString());
                                            // System.out.println("dmdEffDt:" + dmdEffDt);
                                            Date dmdOvrDt = ymmd.parse(getDmdVect.get(5).toString());
                                            // System.out.println("dmdOvrDt:" + dmdOvrDt);
                                            dmdAmt = Double.parseDouble(getDmdVect.get(6).toString());

                                            double dmdAdjAmt = Double.parseDouble(getDmdVect.get(8).toString());
                                            double emiAmt = Double.parseDouble(getDmdVect.get(9).toString());
                                            dmdDt = ymmd.parse(getDmdVect.get(10).toString());
                                            if (totalAdjAmt > 0) {
                                                dmdAmt = dmdAmt - dmdAdjAmt;
                                            }

                                            /**
                                             * *Checking the matching of demand
                                             * id**
                                             */
                                            if (demandId.equalsIgnoreCase(dmdFlowId)) {
                                                if (totalAdjAmt > 0) {
                                                    List tsCntList = em.createNativeQuery("select ifnull(ts_cnt,0)+1 from cbs_loan_dmd_table where acno = '" + acno + "' and shdl_num = " + dmdSchNo + " and dmd_flow_id = '" + dmdFlowId + "' and del_flg = 'N' and dmd_srl_num = " + dmdSrNo).getResultList();
                                                    Vector tsCntVector = (Vector) tsCntList.get(0);
                                                    int tsCnt = Integer.parseInt(tsCntVector.get(0).toString());

                                                    List srNoList = em.createNativeQuery("select ifnull(max(cast(srl_num  as unsigned)),0)+1, ifnull(max(cast(part_tran_srl_num as unsigned)),0)+1 from cbs_loan_dmd_adj_table").getResultList();
                                                    Vector srNoVector = (Vector) srNoList.get(0);
                                                    int srNo = Integer.parseInt(srNoVector.get(0).toString());
                                                    int partTrSrNo = Integer.parseInt(srNoVector.get(1).toString());

                                                    /**
                                                     * **Check what should be
                                                     * the adjust first***
                                                     */
                                                    if (dmdFlowId.toUpperCase().equals(chgDemFlowId.toUpperCase())) {
                                                        /**
                                                         * *** Charge
                                                         * Adjustment****
                                                         */
                                                        Query updateQuery = null;
                                                        Query insertQuery = null;
                                                        if (dmdAmt > totalAdjAmt) {

                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'U', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            chgAmt = totalAdjAmt;
                                                            totalAdjAmt = 0;
                                                        } else if (dmdAmt <= totalAdjAmt) {
                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'S', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            totalAdjAmt = totalAdjAmt - dmdAmt;
                                                            chgAmt = dmdAmt;
                                                        }
                                                    } else if (dmdFlowId.toUpperCase().equals(penalIntDemFlowId.toUpperCase())) {
                                                        /**
                                                         * *** Penal
                                                         * Adjustment****
                                                         */
                                                        Query updateQuery = null;
                                                        Query insertQuery = null;
                                                        if (dmdAmt > totalAdjAmt) {

                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {

                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'U', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            penalAmt = totalAdjAmt;
                                                            totalAdjAmt = 0;
                                                        } else if (dmdAmt <= totalAdjAmt) {
                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'S', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            totalAdjAmt = totalAdjAmt - dmdAmt;
                                                            penalAmt = dmdAmt;
                                                        }
                                                    } else if (dmdFlowId.toUpperCase().equals(intDemFlowId.toUpperCase())) {
                                                        /**
                                                         * *** Interest
                                                         * Adjustment****
                                                         */
                                                        Query updateQuery = null;
                                                        Query insertQuery = null;
                                                        if (dmdAmt > totalAdjAmt) {

                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {

                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'U', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            intAmt = totalAdjAmt;
                                                            totalAdjAmt = 0;
                                                        } else if (dmdAmt <= totalAdjAmt) {
                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'S', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            totalAdjAmt = totalAdjAmt - dmdAmt;
                                                            intAmt = dmdAmt;
                                                        }

                                                    } else if (dmdFlowId.toUpperCase().equals(prinDemFlowId.toUpperCase())) {
                                                        /**
                                                         * *** Principal
                                                         * Adjustment****
                                                         */
                                                        Query updateQuery = null;
                                                        Query insertQuery = null;
                                                        if (dmdAmt > totalAdjAmt) {

                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'U', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            totalAdjAmt = 0;
                                                        } else if (dmdAmt <= totalAdjAmt) {
                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {

                                                                updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT = ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'S', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table(ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            totalAdjAmt = totalAdjAmt - dmdAmt;
                                                        }
                                                    } else if (dmdFlowId.toUpperCase().equals(eiFlowId.toUpperCase())) {
                                                        /**
                                                         * *** Equated
                                                         * Installment
                                                         * Adjustment****
                                                         */
                                                        Query updateQuery = null;
                                                        Query insertQuery = null;
                                                        if (dmdAmt > totalAdjAmt) {
                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table (ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + totalAdjAmt + ", LATEFEE_STATUS_FLG = 'U', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                    insertQuery = em.createNativeQuery("insert into cbs_loan_dmd_adj_table(ACNO, SHDL_NUM, DMD_FLOW_ID, DMD_DATE, DMD_SRL_NUM, SRL_NUM, DEL_FLG, ADJ_DATE, ADJ_AMT, LCHG_USER_ID, LCHG_TIME, OFLOW_ADJ_FLG, TRAN_DATE, TRAN_ID, PART_TRAN_SRL_NUM, TS_CNT)"
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + totalAdjAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            }
                                                            totalAdjAmt = 0;
                                                        } else if (dmdAmt <= totalAdjAmt) {
                                                            if (ymd.parse(dt).after(dmdDt)) {
                                                                updateQuery = em.createNativeQuery("UPDATE cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'L', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
                                                                    Integer insertQry = insertQuery.executeUpdate();
                                                                    if (insertQry <= 0) {
                                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                                    }
//                                                                    }
                                                                }
                                                            } else if (ymd.parse(dt).before(dmdDt) || (ymd.parse(dt).equals(dmdDt))) {
                                                                updateQuery = em.createNativeQuery("UPDATE  cbs_loan_dmd_table SET TOT_ADJ_AMT =  ifnull(TOT_ADJ_AMT,0)+" + dmdAmt + ", LATEFEE_STATUS_FLG = 'S', LAST_ADJ_DATE = '" + dt + "', LCHG_USER_ID = '" + authby + "', LCHG_TIME= CURRENT_TIMESTAMP, TS_CNT = " + tsCnt + "  where acno = '" + acno + "' and SHDL_NUM = " + dmdSchNo + " and DMD_FLOW_ID = '" + dmdFlowId + "' and DEL_FLG = 'N' and DMD_SRL_NUM = " + dmdSrNo/*
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
                                                                            + " values('" + acno + "'," + dmdSchNo + ",'" + dmdFlowId + "','" + ymd.format(dmdDt) + "'," + dmdSrNo + ",'" + srNo + "','N', '" + dt + "'," + dmdAmt + ", '" + authby + "',CURRENT_TIMESTAMP, '" + ofLowAdjFlg + "',CURRENT_TIMESTAMP, " + recno + ",'" + partTrSrNo + "',0)");
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
                                    }

//                                    if (ofLowAdjFlg.equals("Y")) {
//                                        Query updateLaAddTrQuery = em.createNativeQuery("UPDATE cbs_la_additional_tr_details set POST_DUE_FLAG = 'Y', TR_REVERSAL_FLAG  = 'Y', REVERSAL_TR_ID = " + recno + ",	REVERSAL_TR_DT = '" + dt + "', RCRE_USER_ID = '" + authby + "',RCRE_TIME = CURRENT_TIMESTAMP where ACNO = '" + acno + "' and DMD_FLOW_ID = '" + colFlowId + "' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'");
//                                        Integer updtQuery = updateLaAddTrQuery.executeUpdate();
//                                        if (updtQuery <= 0) {
//                                            throw new ApplicationException("538_Updation Problem of Loan Additional Transaction" + dmdFlowId);
//                                        }
//                                    }
//
//                                    if ((totalAdjAmt) > 0) {
//                                        if (dmdSchNo == 0) {
//                                            List repSchNoList = em.createNativeQuery("select ifnull(MAX(REPAY_SCH_NO),0)+1 from cbs_la_additional_tr_details where acno = '" + acno + "' and DMD_FLOW_ID = '" + dmdFlowId + "'").getResultList();
//                                            Vector repSchNoVect = (Vector) repSchNoList.get(0);
//                                            dmdSchNo = Integer.parseInt(repSchNoVect.get(0).toString());
//                                        }
//                                        Query insertLaAddTrQuery = em.createNativeQuery("insert into cbs_la_additional_tr_details(ACNO, REPAY_SCH_NO, DMD_FLOW_ID, OVERFLOW_AMT, POST_DUE_FLAG , TR_REVERSAL_FLAG, INT_ROUTING_FLAG, DMD_DT, LAST_MANUAL_TR_FLAG, DMD_GEN_SAT_DETAIL, LCHG_USER_ID, LCHG_TIME)"
//                                                + " values('" + acno + "'," + dmdSchNo + ",'" + colFlowId + "'," + totalAdjAmt + ",'N','N','L','" + ymd.format(dmdDt) + "', 'N','Y', '" + authby + "',CURRENT_TIMESTAMP)");
//                                        Integer insertQry = insertLaAddTrQuery.executeUpdate();
//                                        if (insertQry <= 0) {
//                                            throw new ApplicationException("538_Insertion Problem in Loan Additional Transaction Table");
//                                        }
//                                    }
                                }
                            }
//                            else {
//                                if (ofLowAdjFlg.equals("Y")) {
//                                    Query updateLaAddTrQuery = em.createNativeQuery("UPDATE  cbs_la_additional_tr_details set POST_DUE_FLAG = 'Y', TR_REVERSAL_FLAG  = 'Y', REVERSAL_TR_ID = " + recno + ",	REVERSAL_TR_DT = '" + ymd.format(ymd.parse(dt)) + "', RCRE_USER_ID = '" + authby + "',RCRE_TIME = CURRENT_TIMESTAMP where ACNO = '" + acno + "' and DMD_FLOW_ID = '" + colFlowId + "' and POST_DUE_FLAG = 'N' and TR_REVERSAL_FLAG = 'N'");
//                                    Integer updtQuery = updateLaAddTrQuery.executeUpdate();
//                                    if (updtQuery <= 0) {
//                                        throw new ApplicationException("538_Updation Problem of Loan Additional Transaction" + colFlowId);
//                                    }
//                                }
//
//                                if ((totalAdjAmt) > 0) {
//                                    if (dmdSchNo == 0) {
//                                        List repSchNoList = em.createNativeQuery("select ifnull(MAX(REPAY_SCH_NO),0)+1 from cbs_la_additional_tr_details where acno = '" + acno + "'").getResultList();
//                                        Vector repSchNoVect = (Vector) repSchNoList.get(0);
//                                        dmdSchNo = Integer.parseInt(repSchNoVect.get(0).toString());
//                                    }
//
//                                    Query insertLaAddTrQuery = em.createNativeQuery("insert into cbs_la_additional_tr_details(ACNO, REPAY_SCH_NO, DMD_FLOW_ID, OVERFLOW_AMT, POST_DUE_FLAG , TR_REVERSAL_FLAG, INT_ROUTING_FLAG, DMD_DT, LAST_MANUAL_TR_FLAG, DMD_GEN_SAT_DETAIL, LCHG_USER_ID, LCHG_TIME)"
//                                            + " values('" + acno + "'," + dmdSchNo + ",'" + colFlowId + "'," + totalAdjAmt + ",'N','N','L','" + dt + "', 'N','Y', '" + authby + "',CURRENT_TIMESTAMP)");
//                                    Integer insertQry = insertLaAddTrQuery.executeUpdate();
//                                    if (insertQry <= 0) {
//                                        throw new ApplicationException("538_Insertion Problem in Loan Additional Transaction Table");
//                                    }
//                                }
//                            }

                            /*EMI Handling*/
                            List emiDetailsList = em.createNativeQuery("select * from emidetails where acno='" + acno + "'").getResultList();
                            if (!emiDetailsList.isEmpty()) {
                                List chkList1 = em.createNativeQuery("select sno,excessamt,p_excessamt from emidetails "
                                        + " where sno=(select max(sno) from emidetails where "
                                        + " acno='" + acno + "' and upper(status)='PAID') and acno='" + acno + "'").getResultList();
                                if (!chkList1.isEmpty()) {
                                    Vector recLst1 = (Vector) chkList1.get(0);
                                    int authLen = recLst1.get(0).toString().length();
                                    if (authLen == 12) {
                                        String loanAuth = recLst1.get(0).toString();
                                        char[] charArr = loanAuth.toCharArray();
                                        String emi = String.valueOf(charArr[2]);
                                    }
                                }

                                List unPaidEmiDetailsList = em.createNativeQuery("select sno,INSTALLAMT,ifnull(excessamt,0) from emidetails "
                                        + " where sno=(select min(sno) from emidetails where "
                                        + " acno='" + acno + "' and upper(status)='UNPAID')  and acno='" + acno + "'").getResultList();

                                if (!unPaidEmiDetailsList.isEmpty()) {
                                    for (int i = 0; i < unPaidEmiDetailsList.size(); i++) {
                                        Vector unPaidEmiDetailsVector = (Vector) unPaidEmiDetailsList.get(i);
                                        sno_unpaid = Integer.parseInt(unPaidEmiDetailsVector.get(0).toString());
                                        installmentamt = Float.parseFloat(unPaidEmiDetailsVector.get(1).toString());
                                        excessamt_unpaid = Float.parseFloat(unPaidEmiDetailsVector.get(2).toString());
                                    }
                                }
                                //                            if (sno_paid == null || sno_paid == 0) {
                                excessamt = excessamt_unpaid;
                                //                            }

                                if (common.getAccseq().equalsIgnoreCase("M") || common.getAccseq().equalsIgnoreCase("P")) {
                                    /*M=Army; P=Postal */
                                    totalamt = excessamt + amt - intAmt - penalAmt - chgAmt;
                                } else {
                                    totalamt = excessamt + amt;
                                }

                                if (totalamt < installmentamt) {
                                    Query updateQuery = em.createNativeQuery("update emidetails set p_excessamt=" + excessamt + ",excessamt=" + totalamt + ","
                                            + " lastUPDate='" + dt + "',recno=" + recno + ",PaymentDt='" + dt + "'"
                                            + " where sno=" + sno_unpaid + " and acno='" + acno + "'");
                                    var1 = updateQuery.executeUpdate();
                                    if (var1 <= 0) {
                                        throw new ApplicationException("538_Updation Problem of EmiDetails for account " + acno);
                                    } else {
                                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt)"
                                                + " values('" + acno + "','" + dt + "'," + totalamt + ")");
                                        Integer insertQry = insertQuery.executeUpdate();
                                        if (insertQry <= 0) {
                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                        } else {
                                            message = "TRUE";
                                        }
                                    }
                                } else {
                                    message = "539_No EMI Installment Updation For " + acno;
                                    Integer oldUnPaidSNo = sno_unpaid;
//                                    List unPaidList = em.createNativeQuery("select max(sno) from emidetails "
//                                            + " group by acno,status having acno='" + acno + "'"
//                                            + " and upper(status)='UNPAID'").getResultList();
                                    List unPaidList = em.createNativeQuery("select max(sno) from emidetails  where acno='" + acno + "'and upper(status)='UNPAID' group by acno").getResultList();
                                    if (!unPaidList.isEmpty()) {
                                        Vector unPaidVector = (Vector) unPaidList.get(0);
                                        for (int i = 0; i < unPaidVector.size(); i++) {
                                            sno_unpaid_max = Integer.parseInt(unPaidVector.get(0).toString());

                                            while (totalamt >= installmentamt && sno_unpaid <= sno_unpaid_max) {
                                                Query updateQuery = em.createNativeQuery("update emidetails set status='PAID',PaymentDt='" + dt + "',lastUPDate='" + dt + "',"
                                                        + " recno=" + recno + ",EnterBy='" + authby + "' where sno=" + sno_unpaid + " and acno='" + acno + "'");

                                                var3 = updateQuery.executeUpdate();
                                                totalamt = totalamt - installmentamt;
                                                sno_unpaid = sno_unpaid + 1;

                                                List emiList = em.createNativeQuery("select INSTALLAMT from emidetails "
                                                        + " where sno=" + sno_unpaid + " and acno='" + acno + "'").getResultList();
                                                if (!emiList.isEmpty()) {
                                                    Vector emiVector = (Vector) emiList.get(0);
                                                    installmentamt = Float.parseFloat(emiVector.get(0).toString());
                                                }
                                                if (var3 <= 0) {
                                                    throw new ApplicationException("538_Updation Problem of EmiDetails for account " + acno);
                                                }
                                            }
                                            if (totalamt > 0) {
                                                if (totalamt < installmentamt && sno_unpaid <= sno_unpaid_max) {
//                                                    if ((sno_unpaid - 1) == oldUnPaidSNo) {
//                                                        Query updateQuery1 = em.createNativeQuery("update emidetails set excessamt=" + totalamt + " where sno=" + sno_unpaid + " and acno='" + acno + "'");
//                                                        var4 = updateQuery1.executeUpdate();
//                                                        if (var4 <= 0) {
//                                                            throw new ApplicationException("538_Updation Problem of EmiDetails for account " + acno);
//                                                        }
//                                                        Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt)"
//                                                                + " values('" + acno + "','" + dt + "'," + totalamt + ")");
//                                                        Integer insertQry = insertQuery.executeUpdate();
//                                                        if (insertQry <= 0) {
//                                                            throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
//                                                        } 
//                                                    } else {
                                                    Query updateQuery1 = em.createNativeQuery("update emidetails set excessamt=" + totalamt + " where sno=" + sno_unpaid + " and acno='" + acno + "'");
                                                    var4 = updateQuery1.executeUpdate();
                                                    if (var4 <= 0) {
                                                        throw new ApplicationException("538_Updation Problem of EmiDetails for account " + acno);
                                                    }
                                                    Query insertQuery = em.createNativeQuery("insert into cbs_loan_emi_excess_details (acno, dt, excessamt)"
                                                            + " values('" + acno + "','" + dt + "'," + totalamt + ")");
                                                    Integer insertQry = insertQuery.executeUpdate();
                                                    if (insertQry <= 0) {
                                                        throw new ApplicationException("538_Insertion Problem in Loan Demand Adjustment Table");
                                                    }
//                                                    }
                                                }
                                            }
                                            message = "TRUE";
                                        }
                                    } else {
                                        List paidList = em.createNativeQuery("select max(sno) from emidetails  where acno='" + acno + "'and upper(status)='PAID' group by acno").getResultList();
                                        if (!paidList.isEmpty()) {
                                            double outStanding = Double.parseDouble(intLoanRemote.outStandingAsOnDate(acno, dt));
                                            if (outStanding != 0) {
                                                message = "TRUE";
                                            }
                                        }

                                    }
                                }
                            } else {
                                message = "TRUE";
                            }
                        } else {
                            message = "TRUE";
                        }
                    } else {
                        if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            throw new ApplicationException("502_Invalid Ty value or TranDesc Value");
                        } else {
                            return "TRUE";
                        }
                    }
                }
            } else {
                if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    if (ty == 0 && (transdesc == 0 || transdesc == 1)) {
                        List rdInstallmentUnPaidList = em.createNativeQuery("select sno,excessamt,p_excessamt from rd_installment "
                                + " where sno=(select max(sno) from rd_installment where "
                                + " acno='" + acno + "' and upper(status)='PAID') and acno='" + acno + "'").getResultList();

                        if (!rdInstallmentUnPaidList.isEmpty()) {
                            for (int i = 0; i < rdInstallmentUnPaidList.size(); i++) {
                                Vector rdInstallmentUnPaidVector = (Vector) rdInstallmentUnPaidList.get(i);
                                sno_paid = Integer.parseInt(rdInstallmentUnPaidVector.get(0).toString());
                                excessamt = Float.parseFloat(rdInstallmentUnPaidVector.get(1).toString());
                                p_excessamt = Float.parseFloat(rdInstallmentUnPaidVector.get(2).toString());
                            }
                        }

                        List rdInstallmentPaidList = em.createNativeQuery("select sno,INSTALLAMT,ifnull(excessamt,0) from rd_installment "
                                + " where sno=(select min(sno) from rd_installment where "
                                + " acno='" + acno + "' and upper(status)='UNPAID')  and acno='" + acno + "'").getResultList();

                        if (!rdInstallmentPaidList.isEmpty()) {
                            for (int i = 0; i < rdInstallmentPaidList.size(); i++) {
                                Vector rdInstallmentPaidVector = (Vector) rdInstallmentPaidList.get(i);
                                sno_unpaid = Integer.parseInt(rdInstallmentPaidVector.get(0).toString());
                                installmentamt = Float.parseFloat(rdInstallmentPaidVector.get(1).toString());
                                excessamt_unpaid = Float.parseFloat(rdInstallmentPaidVector.get(2).toString());
                            }
                        }
                        if (sno_paid == null || sno_paid == 0) {
                            excessamt = excessamt_unpaid;
                        }
                        totalamt = excessamt + amt;
                        if (totalamt < installmentamt) {
                            Query updateQuery = em.createNativeQuery("update rd_installment set p_excessamt=" + excessamt + ",excessamt=" + totalamt + ","
                                    + " lastUPDate='" + dt + "',recno=" + recno + ",PaymentDt='" + dt + "'"
                                    + " where sno=ifnull(" + sno_paid + "," + sno_unpaid + ") and acno='" + acno + "'");
                            var1 = updateQuery.executeUpdate();
                            if (var1 <= 0) {
                                throw new ApplicationException("540_Updation Problem of rd_installment");
                            }
                            message = "TRUE";
                        } else {
                            message = "541_No RD Installment For Updation";
                            List installmentList = em.createNativeQuery("select max(sno) from rd_installment "
                                    + " group by acno,status having acno='" + acno + "'"
                                    + " and upper(status)='UNPAID'").getResultList();
                            if (!installmentList.isEmpty()) {
                                Vector installmentVector = (Vector) installmentList.get(0);
                                for (int i = 0; i < installmentVector.size(); i++) {
                                    sno_unpaid_max = Integer.parseInt(installmentVector.get(0).toString());

                                    while (totalamt >= installmentamt && sno_unpaid <= sno_unpaid_max) {
                                        Query updateQuery = em.createNativeQuery("update rd_installment set status='PAID',PaymentDt='" + dt + "',lastUPDate='" + dt + "',"
                                                + " recno=" + recno + ",EnterBy='" + authby + "' where sno=" + sno_unpaid + " and acno='" + acno + "'");
                                        var3 = updateQuery.executeUpdate();
                                        totalamt = totalamt - installmentamt;
                                        sno_unpaid = sno_unpaid + 1;

                                        List rdList = em.createNativeQuery("select INSTALLAMT from rd_installment "
                                                + " where sno=" + sno_unpaid + " and acno='" + acno + "'").getResultList();
                                        if (!rdList.isEmpty()) {
                                            Vector rdVector = (Vector) rdList.get(0);
                                            installmentamt = Float.parseFloat(rdVector.get(0).toString());
                                        }
                                        if (var3 <= 0) {
                                            throw new ApplicationException("540_Updation Problem of rd_installment");
                                        }
                                        if (totalamt < installmentamt && sno_unpaid <= sno_unpaid_max) {
                                            Query updateQuery1 = em.createNativeQuery("update rd_installment set excessamt=" + totalamt + " where sno=" + sno_unpaid + " - 1 and acno='" + acno + "'");
                                            var4 = updateQuery1.executeUpdate();
                                            if (var4 <= 0) {
                                                throw new ApplicationException("540_Updation Problem of rd_installment");
                                            }
                                        }
                                    }
                                    message = "TRUE";
                                }
                            }
                        }
                    } else if (ty == 1 && transdesc == 9) {
                        double installmentAmount = 0;
                        int maxSerialNo = 0;
                        List installmentAmtList = em.createNativeQuery("Select ifnull(rdinstaL,0) from accountmaster where acno='" + acno + "' and rdinstal is not null and rdinstal<>'' and rdinstal<>0").getResultList();
                        if (!installmentAmtList.isEmpty()) {
                            Vector installmentAmtVector = (Vector) installmentAmtList.get(0);
                            installmentAmount = Double.parseDouble(installmentAmtVector.get(0).toString());
                        }

                        int noOfPaidInstallment = (int) (amt / installmentAmount);
                        List maxSerialNoList = em.createNativeQuery("SELECT MAX(SNO) FROM rd_installment WHERE ACNO='" + acno + "' AND STATUS='PAID'").getResultList();
                        if (!maxSerialNoList.isEmpty()) {
                            Vector maxSerialNoVector = (Vector) maxSerialNoList.get(0);
                            maxSerialNo = Integer.parseInt(maxSerialNoVector.get(0).toString());
                        }

                        for (int f = maxSerialNo; f > (maxSerialNo - noOfPaidInstallment); f--) {
                            Query updateRdInstallment = em.createNativeQuery("UPDATE rd_installment SET STATUS='Unpaid',PAYMENTDT=null,LASTUPDATE=null,RECNO=0.0 WHERE ACNO='" + acno + "' AND SNO=" + f + "");
                            int updateNo = updateRdInstallment.executeUpdate();
                            if (updateNo <= 0) {
                                throw new ApplicationException("Updatetion Problem in RD INSTALLMENT table");
                            }
                        }
                        message = "TRUE";
                    }
                } else {
                    throw new ApplicationException("516_Account Nature Is Invalid");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public String loanInstallmentReversal(String acNo, String date, String recNo, String updateBy) throws ApplicationException {
        String message = "FALSE";
        try {
            List installmentPaidList = em.createNativeQuery("Select sno from emidetail where acno = '" + acNo + "' and paymentdt = '" + date + "' and recno = '" + recNo + "'").getResultList();
            if (!installmentPaidList.isEmpty()) {
                for (int i = 0; i < installmentPaidList.size(); i++) {
                    Vector element = (Vector) installmentPaidList.get(i);
                    int sNo = Integer.parseInt(element.get(0).toString());
                    Query updateRdInstallment = em.createNativeQuery("UPDATE emidetail SET STATUS='Unpaid',PAYMENTDT='',RECNO=0.0, LASTUPDATE=now(),"
                            + "ENTERBY = '" + updateBy + "', REMARKS = 'TRANSACTION REVERSAL' "
                            + "WHERE ACNO='" + acNo + "' AND SNO=" + sNo + " and RECNO = " + recNo);
                    int updateNo = updateRdInstallment.executeUpdate();
                    if (updateNo <= 0) {
                        throw new ApplicationException("Updatetion Problem in INSTALLMENT");
                    }

                    Query deleteExcess = em.createNativeQuery("delete from cbs_loan_emi_excess_details "
                            + "WHERE ACNO='" + acNo + "' AND dt=" + date + " and RECNO = " + recNo);
                    updateNo = deleteExcess.executeUpdate();
                    if (updateNo <= 0) {
                        throw new ApplicationException("delete Problem in ExcessAmt");
                    } else {
                        message = "TRUE";
                    }
                }
                Query deleteExcess = em.createNativeQuery("delete from cbs_loan_emi_excess_details "
                        + "WHERE ACNO='" + acNo + "' AND dt=" + date + " and RECNO = " + recNo);
                int updateNo = deleteExcess.executeUpdate();
                if (updateNo <= 0) {
                    throw new ApplicationException("delete Problem in ExcessAmt");
                } else {
                    message = "TRUE";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public Float getTrsNo() throws ApplicationException {
        try {
            //New One
            float trsNo = 0f;
            int n = em.createNativeQuery("UPDATE reconvmast_trans SET trsno = LAST_INSERT_ID(trsno + 1)").executeUpdate();
            if (n > 0) {
                List list = em.createNativeQuery("SELECT LAST_INSERT_ID()").getResultList();
                Vector ele = (Vector) list.get(0);
                trsNo = Float.parseFloat(ele.get(0).toString());
            }
            return trsNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String cbsAuthCashCrMakeEntry(int msgNo, int msgBillStart, String status, int msgBillPo,
            int msgBillEnd, String authBy, String date, Float seqNo, String instNo, String billType, String acno,
            String custName, String payableAt, double amount, String dt1, String originDt, int timeLimit, double comm,
            int tranType, int ty, String inFavourOf, String enterBy, String lastUpdateBy, Float recNo,
            String orgnBrCode) throws ApplicationException {
        String msg = "FALSE";
        try {
            System.out.println("lastUpdateBy:=======" + lastUpdateBy);

            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            date = ymd.format(dmy.parse(date));
            dt1 = ymd.format(dmy.parse(dt1));
            originDt = ymd.format(dmy.parse(originDt));

            if ((msgNo >= msgBillStart) && (msgNo <= msgBillEnd)) {
                if (status.equalsIgnoreCase("ISSUED")) {
                    if (msgNo == msgBillPo) {
                        if (msgNo == 110) {
                            Query q1 = em.createNativeQuery("insert into bill_po(FYEAR,SEQNO,INSTNO,BILLTYPE,ACNO,CUSTNAME,PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,TIMELIMIT,COMM,TRANTYPE,TY,INFAVOUROF,PLACE,ENTERBY,LASTUPDATEBY,AUTH,AUTHBY,TRANTIME,RECNO,printflag,validationdt) "
                                    + "values(DATE_FORMAT('" + date + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + acno + "','" + custName + "','" + payableAt + "'," + amount + ",'" + dt1 + "','" + originDt + "','" + status + "'," + timeLimit + "," + comm + "," + tranType + "," + ty + ",'" + inFavourOf + "','','" + enterBy + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recNo + ",0,'" + originDt + "')");
                            q1.executeUpdate();
                            msg = "TRUE";
                        } else if (msgNo == 120) {
                            Query q2 = em.createNativeQuery("insert into bill_tpo (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag) "
                                    + "values(DATE_FORMAT('" + date + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + acno + "','" + custName + "','" + payableAt + "'," + amount + ",'" + dt1 + "','" + originDt + "','" + status + "'," + timeLimit + "," + comm + "," + tranType + "," + ty + ",'" + inFavourOf + "','','" + enterBy + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recNo + ",0,'') ");
                            q2.executeUpdate();
                            msg = "TRUE";
                        } else if (msgNo == 130) {
                            Query q2 = em.createNativeQuery("insert into bill_ad (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag) "
                                    + "values(DATE_FORMAT('" + date + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + acno + "','" + custName + "','" + payableAt + "'," + amount + ",'" + dt1 + "','" + originDt + "','" + status + "'," + timeLimit + "," + comm + "," + tranType + "," + ty + ",'" + inFavourOf + "','','" + enterBy + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recNo + ",0) ");
                            q2.executeUpdate();
                            msg = "TRUE";
                        } else if (msgNo == 140) {
                            Query q2 = em.createNativeQuery("insert into bill_dd (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag, org_brnid) "
                                    + "values(DATE_FORMAT('" + date + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + acno + "','" + custName + "','" + payableAt + "'," + amount + ",'" + dt1 + "','" + originDt + "','" + status + "'," + timeLimit + "," + comm + "," + tranType + "," + ty + ",'" + inFavourOf + "','','" + enterBy + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recNo + ",0,'" + orgnBrCode + "') ");
                            q2.executeUpdate();
                            msg = "TRUE";
                        }
                    }
                } else {
                    if (status.equals("PAID") || status.equals("CANCELLED")) {
                        if (msgNo == msgBillPo) {
                            List list = em.createNativeQuery("select ifnull(InstNature,'') from billtypemaster where instcode = '" + billType + "'").getResultList();
                            Vector vBillNat = (Vector) list.get(0);
                            String billNature = vBillNat.get(0).toString();
                            if (billNature.equals("PO")) {
                                Query q3 = em.createNativeQuery("Update bill_po Set dt = '" + dt1 + "',Status = '" + status + "',recNo = " + recNo + " ,ty=1,AcNo = '" + acno + "',LastUpdateBy = '" + authBy + "' where seqno = " + seqNo + " and InstNo = '" + instNo + "' and SUBSTRING(acno,1,2)= '" + orgnBrCode + "' and validationdt='" + originDt + "'");
                                int rs = q3.executeUpdate();
                                if (rs > 0) {
                                    msg = "TRUE";
                                }
                            } else if (billNature.equals("TPO")) {
                                Query q2 = em.createNativeQuery("insert into bill_tpo values(DATE_FORMAT('" + date + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + acno + "','" + custName + "','" + payableAt + "'," + amount + ",'" + dt1 + "','" + originDt + "','" + status + "'," + timeLimit + "," + comm + "," + tranType + "," + ty + ",'" + inFavourOf + "','','" + enterBy + "','" + lastUpdateBy + "','Y','" + authBy + "',CURRENT_TIMESTAMP," + recNo + ",0) ");
                                q2.executeUpdate();
                                msg = "TRUE";
                            } else if (billNature.equals("AD")) {
                                Query q2 = em.createNativeQuery("insert into bill_ad (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag)"
                                        + " values(DATE_FORMAT('" + date + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + acno + "','" + custName + "','" + payableAt + "'," + amount + ",'" + dt1 + "','" + originDt + "','" + status + "'," + timeLimit + "," + comm + "," + tranType + "," + ty + ",'" + inFavourOf + "','','" + enterBy + "','" + lastUpdateBy + "','Y','" + authBy + "',CURRENT_TIMESTAMP," + recNo + ",0) ");
                                q2.executeUpdate();
                                msg = "TRUE";
                            } else if (billNature.equals("DD")) {
                                Query q3 = em.createNativeQuery("Update bill_dd Set dt = '" + dt1 + "',Status = '" + status + "',recNo = " + recNo + " ,ty=1,AcNo = '" + acno + "',LastUpdateBy = '" + authBy + "' where seqno = " + seqNo + " and InstNo = '" + instNo + "' and SUBSTRING(acno,1,2)= '" + orgnBrCode + "' and origindt='" + originDt + "'");
                                int rs = q3.executeUpdate();
                                if (rs > 0) {
                                    msg = "TRUE";
                                }
                            }
                        }
                    }
                }
            } else {
                if (msgNo == 150) {
                    msg = cbsAuthCashCrSrfentry(recNo, authBy, originDt, orgnBrCode);
                    if (!msg.substring(0, 4).equals("TRUE")) {
                        msg = "Problem occured in Cr Make Entry";

                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String cbsAuthCashCrSrfentry(float recNo, String authBy, String originDt, String orgnBrCode) throws ApplicationException {
        String msg = null, acno = null, details = null, instNo = null, enterBy = null, tranTime = null, dt = null;
        double crAmt = 0.0d, drAmt = 0.0d, balance = 0.0d;
        float payBy = 0.0f;
        int ty = 0, tranType = 0, iy = 0, trsNo = 0, tranDesc = 0, msgNo = 0;
        try {
            List data = em.createNativeQuery("select acno,dt,cramt,dramt,balance,trantype,instno,iy,ty,payby,trsno,trantime,trandesc,enterby"
                    + " from gl_recon where recno = " + recNo + "  and auth='Y' and dt='" + originDt + "' AND ORG_BRNID = '" + orgnBrCode + "'").getResultList();
            if (data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    Vector element = (Vector) data.get(i);
                    acno = element.get(0).toString();
                    dt = element.get(1).toString();
                    crAmt = Double.parseDouble(element.get(2).toString());
                    drAmt = Double.parseDouble(element.get(3).toString());
                    balance = Double.parseDouble(element.get(4).toString());
                    tranType = Integer.parseInt(element.get(5).toString());
                    instNo = element.get(6).toString();
                    iy = Integer.parseInt(element.get(7).toString());
                    ty = Integer.parseInt(element.get(8).toString());
                    payBy = Float.parseFloat(element.get(9).toString());
                    trsNo = Integer.parseInt(element.get(10).toString());
                    tranTime = element.get(11).toString();
                    tranDesc = Integer.parseInt(element.get(12).toString());
                    enterBy = element.get(13).toString();

                    if (msgNo == 150) {
                        details = "Subsidy amount";
                    } else {
                        details = " ";
                    }

                    Query q1 = em.createNativeQuery("INSERT INTO srf_recon(ACNO,TY,DT,DRAMT,CRAMT,BALANCE,TRANTYPE,DETAILS,IY,INSTNO,ENTERBY,AUTH,PAYBY,AUTHBY,TRSNO,TRANTIME,TRANDESC,RECNO) "
                            + "values ('" + acno + "'," + ty + ",'" + dt + "'," + drAmt + "," + crAmt + ", " + balance + " ," + tranType + ",'" + details + "'," + iy + ",'" + instNo + "','" + enterBy + "','Y'," + payBy + ",'" + authBy + "'," + trsNo + ",'" + tranTime + "'," + tranDesc + "," + recNo + ")");
                    q1.executeUpdate();
                    msg = "TRUE";
                }
            } else {
                //ut.rollback();
                msg = "Problem occured in srf entry";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String lPading(String padVar, int padLength, String padChar) {
        int padvarLength = padVar.length();
        for (int i = 1; i <= padLength - padvarLength; i++) {
            padVar = padChar.concat(padVar);
        }
        return padVar;
    }

    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        String businessDate = "";
        try {
            Date dt = new Date();
            List cbsBankDays = em.createNativeQuery("SELECT DAYBEGINFLAG,DAYENDFLAG FROM cbs_bankdays WHERE DATE = '" + ymd.format(dt) + "'").getResultList();
            if (cbsBankDays.isEmpty()) {
                throw new ApplicationException("Data is not present in Cbs Bankdays.");
            } else {
                Vector element = (Vector) cbsBankDays.get(0);
                String cbsDaybeginFlag = element.get(0).toString();
                String cbsDayendFlag = element.get(1).toString();
                if (cbsDaybeginFlag.equalsIgnoreCase("H") && cbsDayendFlag.equalsIgnoreCase("Y")) {
                    throw new ApplicationException("Today is bank holiday.");
                } else if (cbsDaybeginFlag.equalsIgnoreCase("N") && cbsDayendFlag.equalsIgnoreCase("N")) {
                    throw new ApplicationException("Daybegin of bank is pending on current date.");
                } else if (cbsDaybeginFlag.equalsIgnoreCase("Y") && cbsDayendFlag.equalsIgnoreCase("Y")) {
                    throw new ApplicationException("Daybegin of bank is pending for next business date.");
                } else if (cbsDaybeginFlag.equalsIgnoreCase("Y") && cbsDayendFlag.equalsIgnoreCase("N")) {
                    List date = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DATE = '" + ymd.format(dt) + "' AND BRNCODE='" + orgnBrCode + "'").getResultList();
                    Vector dateVector = (Vector) date.get(0);
                    businessDate = dateVector.get(0).toString();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return businessDate;
    }

    public String ftsStockStatementExipryValidate(String acno, String todayDate) throws ApplicationException {
        SimpleDateFormat ymdd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            String parameterCode = getValueFromCbsparameterInfo("STOCK-STMT-ACCTCODE");
            if (!(parameterCode.equalsIgnoreCase(""))) {
                if (parameterCode.contains(acno.substring(2, 4).toString())) {
                    List doccumentExList = txnRemote.getDocumentExpiryDate(acno);
                    if (!doccumentExList.isEmpty()) {
                        Vector docD = (Vector) doccumentExList.get(0);
                        String docExpDate = docD.get(0).toString();
                        if (!docExpDate.equalsIgnoreCase("1900-01-01")) {
                            if (ymd.parse(todayDate).compareTo(ymdd.parse(docExpDate)) > 0) {
                                return "Stock statement has been expired of this account. you can not debited to this account.";
                            }
                        }
                    }
                }
            }
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String ftsInstDateValidate(String instDate) throws ApplicationException {
        try {
            if (instDate == null || instDate.equalsIgnoreCase("")) {
                return "Instrument date can not be blank";
            }

            String sixMonthChqDt = "20120331";
            String toDt = "";
            int instDtCompare = ymd.parse(instDate).compareTo(ymd.parse(sixMonthChqDt));
            if (instDtCompare <= 0) {
                toDt = CbsUtil.monthAdd(instDate, 6);
            } else {
                toDt = CbsUtil.monthAdd(instDate, 3);
            }
            long outCompareValue = CbsUtil.dayDiff(ymd.parse(toDt), ymd.parse(ymd.format(new Date())));
            if (outCompareValue > 0) {
                return "This is outdated cheque";
            }
            long postCompareValue = CbsUtil.dayDiff(ymd.parse(instDate), ymd.parse(ymd.format(new Date())));
            if (postCompareValue < 0) {
                return "This is postdated cheque";
            }
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String chkBal(String acNo, Integer ty, Integer tranDesc, String acctNature) throws ApplicationException {
        try {
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                if ((tranDesc != 3) && (tranDesc != 4) && (tranDesc != 5) && (tranDesc != 7) && (tranDesc != 8) && (tranDesc != 9)
                        && (tranDesc != 21) && (tranDesc != 22) && (tranDesc != 23) && (tranDesc != 24) && (tranDesc != 25)
                        && (tranDesc != 26) && (tranDesc != 27) && (tranDesc != 28) && (tranDesc != 29) && (tranDesc != 30)
                        && (tranDesc != 31) && (tranDesc != 32) && (tranDesc != 102)) {
                    return "CHECKBALANCE";
                }
            }

            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                return "CHECKBALANCE";
            }
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                return "CHECKBALANCE";
            }
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC))) {
                return "CHECKBALANCE";
            }
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC))) {
                return "CHECKBALANCE";
            }
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN))) {
                if ((tranDesc != 3) && (tranDesc != 4) && (tranDesc != 5)
                        && (tranDesc != 7) && (tranDesc != 8) && (tranDesc != 9) && (tranDesc != 21) && (tranDesc != 22) && (tranDesc != 23) && (tranDesc != 24)
                        && (tranDesc != 25) && (tranDesc != 26) && (tranDesc != 27) && (tranDesc != 28) && (tranDesc != 29) && (tranDesc != 30)
                        && (tranDesc != 31) && (tranDesc != 32) && (tranDesc != 102)) {
                    return "CHECKBALANCE";
                }
            }
            if ((ty == 1) && (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                if ((tranDesc != 3) && (tranDesc != 4) && (tranDesc != 5)
                        && (tranDesc != 7) && (tranDesc != 8) && (tranDesc != 9) && (tranDesc != 21) && (tranDesc != 22) && (tranDesc != 23) && (tranDesc != 24)
                        && (tranDesc != 25) && (tranDesc != 26) && (tranDesc != 27) && (tranDesc != 28) && (tranDesc != 29) && (tranDesc != 30)
                        && (tranDesc != 31) && (tranDesc != 32) && (tranDesc != 102)) {
                    return "CHECKBALANCE";
                }
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getIntTrfAcNo(String userAccountNumber) throws ApplicationException {
        try {
            String INT_TRF_ACNO = "";
            String acType = userAccountNumber.substring(2, 4);
            List ac_Type_StaffList = em.createNativeQuery("SELECT Int_Ac_Open_Enable_In_Staff FROM accounttypemaster WHERE ACCTCODE = '" + acType + "'").getResultList();
            Vector acStaffVect = (Vector) ac_Type_StaffList.get(0);
            String Int_Ac_Open_Enable_In_Staff = acStaffVect.get(0).toString();
            if (Int_Ac_Open_Enable_In_Staff.equalsIgnoreCase("Y")) {
                List int_Trf_AcnoList = em.createNativeQuery("Select ifnull(INT_TRF_ACNO,0) from cbs_loan_acc_mast_sec where acno='" + userAccountNumber + "'").getResultList();
                Vector intVec = (Vector) int_Trf_AcnoList.get(0);
                INT_TRF_ACNO = intVec.get(0).toString();
            } else {
                INT_TRF_ACNO = "";
            }
            return INT_TRF_ACNO;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getNewAccountNumber(String userAccountNumber) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("SELECT NEW_AC_NO FROM cbs_acno_mapping WHERE OLD_AC_NO='" + userAccountNumber + "' OR NEW_AC_NO='" + userAccountNumber + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account number does not exist");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getOldAccountNumber(String userAccountNumber) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("SELECT OLD_AC_NO FROM cbs_acno_mapping WHERE NEW_AC_NO='" + userAccountNumber + "' OR OLD_AC_NO='" + userAccountNumber + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account number does not exist");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getNewAccountNumberForHo(String userAccountNumber) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("SELECT NEW_AC_NO FROM cbs_acno_mapping WHERE substring(OLD_AC_NO,3,8)='" + userAccountNumber + "' OR substring(NEW_AC_NO,3,8)='" + userAccountNumber + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account Number does not exist");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAccountNature(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acctnature from accounttypemaster where acctCode = '" + getAccountCode(accNo) + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                return (String) element.get(0);
            } else {
                throw new ApplicationException("Account Nature for " + accNo + " does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getActNatureFor8DigitGLCode(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acctnature from accounttypemaster where acctCode = '" + accNo.substring(0, 2) + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                return element.get(0).toString();
            } else {
                throw new ApplicationException("Account Nature for " + accNo + " does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCurrentBrnCode(String accNo) throws ApplicationException {
        try {
            List listCurBrnCode;
            String nature = getAcNatureByCode(accNo.substring(2, 4));
            if (nature.equals(CbsConstant.FIXED_AC) || nature.equals(CbsConstant.MS_AC)) {
                String chkMsg = txnAuth.fdFidilityChk(accNo);
                if (chkMsg.equalsIgnoreCase("true")) {
                    listCurBrnCode = em.createNativeQuery("select brncode from fidility_accountmaster where acno = '" + accNo + "'").getResultList();
                    if (!listCurBrnCode.isEmpty()) {
                        Vector element = (Vector) listCurBrnCode.get(0);
                        return element.get(0).toString();
                    }
                    throw new ApplicationException("Branch Code does not exist for " + accNo);
                } else {
                    listCurBrnCode = em.createNativeQuery("select CurBrCode from td_accountmaster where acno = '" + accNo + "'").getResultList();
                    if (!listCurBrnCode.isEmpty()) {
                        Vector element = (Vector) listCurBrnCode.get(0);
                        return element.get(0).toString();
                    }
                    throw new ApplicationException("Branch Code does not exist for " + accNo);
                }
            } else if (nature.equals(CbsConstant.PAY_ORDER)) {
                return accNo.substring(0, 2);
            } else {
                listCurBrnCode = em.createNativeQuery("select CurBrCode from accountmaster where acno = '" + accNo + "'").getResultList();
                if (!listCurBrnCode.isEmpty()) {
                    Vector element = (Vector) listCurBrnCode.get(0);
                    return element.get(0).toString();
                }
                throw new ApplicationException("Branch Code does not exist for " + accNo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAccountCode(String accNo) throws ApplicationException {
        try {
            List listCurBrnCode = em.createNativeQuery("select accttype from fidility_accountmaster where acno = '" + accNo + "'").getResultList();
            if (!listCurBrnCode.isEmpty()) {
                Vector element = (Vector) listCurBrnCode.get(0);
                return element.get(0).toString();
            } else {
                listCurBrnCode = em.createNativeQuery("select accttype from accountmaster where acno = '" + accNo + "'").getResultList();
                if (!listCurBrnCode.isEmpty()) {
                    Vector element = (Vector) listCurBrnCode.get(0);
                    return element.get(0).toString();
                } else {
                    listCurBrnCode = em.createNativeQuery("select accttype from td_accountmaster where acno='" + accNo + "'").getResultList();
                    if (!listCurBrnCode.isEmpty()) {
                        Vector element = (Vector) listCurBrnCode.get(0);
                        return element.get(0).toString();
                    } else {
                        listCurBrnCode = em.createNativeQuery("select acno from gltable where acno = '" + accNo + "'").getResultList();
                        if (!listCurBrnCode.isEmpty()) {
                            return accNo.substring(2, 4);
                        } else {
                            throw new ApplicationException("Account Code does not exist for " + accNo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcctTypeDesc() throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select AcctCode,AcctType,acctNature,AcctDesc from accounttypemaster order by acctcode").getResultList();
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getUnAuthorizedTranList(String acctcode, String brCode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select acno from tokentable_debit where auth='N' and substring(acno,3,2)='" + acctcode + "' and org_brnid='" + brCode + "'"
                    + "union all select acno from tokentable_credit where auth='N' and substring(acno,3,2)='" + acctcode + "' and org_brnid='" + brCode + "'"
                    + "union all select acno from recon_clg_d where auth='N' and substring(acno,3,2)='" + acctcode + "' and org_brnid='" + brCode + "'"
                    + "union all select acno from recon_trf_d where auth='N' and substring(acno,3,2)='" + acctcode + "'and org_brnid='" + brCode + "'").getResultList();
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getUnAuthorizedTranListForAcno(String acno) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select acno from tokentable_debit where auth='N' and acno='" + acno + "' union all "
                    + "select acno from tokentable_credit where auth='N' and acno='" + acno + "' union all "
                    + "select acno from recon_clg_d where auth='N' and acno='" + acno + "' union all "
                    + "select acno from recon_trf_d where auth='N' and acno='" + acno + "' union all "
                    + "select acno from recon_cash_d r where r.acno ='" + acno + "' and auth = 'N' ").getResultList();
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getOriginBranch() throws ApplicationException {
        String originBranch = "";
        try {
            List list = em.createNativeQuery("SELECT BRNCODE FROM branchmaster WHERE ALPHACODE=(SELECT ALPHACODE FROM bnkadd)").getResultList();
            if (!list.isEmpty()) {
                Vector vect = (Vector) list.get(0);
                int brncode = Integer.parseInt(vect.get(0).toString());
                originBranch = String.format("%02d", brncode);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return originBranch;
    }

    @Override
    public String getMiniStatement(String acno, String bankName) throws ApplicationException {
        String msg = "", acctNature = "", balance = "", partialMsg = "";
        List dataList = new ArrayList();
        try {
            acctNature = getAccountNature(acno);
            if (acctNature.equalsIgnoreCase("CA")) {
                dataList = em.createNativeQuery("select cramt,dramt,trantype,date_format(dt,'%d/%m/%Y') from ca_recon where acno='" + acno + "' and trantime in(select trantime from ca_recon a where 3 > (select count(trantime) from ca_recon where trantime >a.trantime and acno='" + acno + "') and acno='" + acno + "') order by trantime").getResultList();
            } else if (acctNature.equalsIgnoreCase("SB")) {
                dataList = em.createNativeQuery("select cramt,dramt,trantype,date_format(dt,'%d/%m/%Y') from recon where acno='" + acno + "' and trantime in(select trantime from recon a where 3 > (select count(trantime) from recon where trantime >a.trantime and acno='" + acno + "') and acno='" + acno + "') order by trantime").getResultList();
            } else if (acctNature.equalsIgnoreCase("TL") || acctNature.equalsIgnoreCase("DL")) {
                dataList = em.createNativeQuery("select cramt,dramt,trantype,date_format(dt,'%d/%m/%Y') from loan_recon where acno='" + acno + "' and trantime in(select trantime from loan_recon a where 3 > (select count(trantime) from loan_recon where trantime >a.trantime and acno='" + acno + "') and acno='" + acno + "') order by trantime").getResultList();
            } else if (acctNature.equalsIgnoreCase("RD")) {
                dataList = em.createNativeQuery("select cramt,dramt,trantype,date_format(dt,'%d/%m/%Y') from rdrecon where acno='" + acno + "' and trantime in(select trantime from rdrecon a where 3 > (select count(trantime) from rdrecon where trantime >a.trantime and acno='" + acno + "') and acno='" + acno + "') order by trantime").getResultList();
            } else if (acctNature.equalsIgnoreCase("FD") || acctNature.equalsIgnoreCase("MS") || acctNature.equalsIgnoreCase("OF")) {
                dataList = em.createNativeQuery("select cramt,dramt,trantype,date_format(dt,'%d/%m/%Y') from td_recon where acno='" + acno + "' and trantime in(select trantime from td_recon a where 3 > (select count(trantime) from td_recon where trantime >a.trantime and acno='" + acno + "') and acno='" + acno + "') order by trantime").getResultList();
            }
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);

                    String cramt = formatter.format(Double.parseDouble(element.get(0).toString()));
                    String dramt = formatter.format(Double.parseDouble(element.get(1).toString()));
                    String tranDt = element.get(3).toString();

                    String tranMsg = tranDt + ":Cr=" + cramt + ":Dr=" + dramt + ":";
                    partialMsg = partialMsg + tranMsg;
                }
                balance = ftsGetBal(acno);
                msg = "Your last three transactions to A/c XXXX" + acno.substring(4, 10) + "XX are " + partialMsg + " Avl balance is " + balance + " .Thanks, " + bankName + ".";
            }
        } catch (Exception ex) {
            System.out.println("Problem In getMiniStatement() Method " + ex.getMessage());
        }
        return msg;
    }

    public String checkDuplicateToken(String tokenNo, String subTokenNo, String brCode, String dt) throws ApplicationException {
        try {
            List dataList = new ArrayList();
            dataList = em.createNativeQuery("select tokenno from tokentable_debit where tokenno='" + tokenNo + "' and "
                    + "subtokenno='" + subTokenNo + "' and dt='" + dt + "' and org_brnid='" + brCode + "' union all "
                    + "select tokenno from recon_cash_d where tokenno='" + tokenNo + "' and subtokenno='" + subTokenNo + "' and "
                    + "dt='" + dt + "' and org_brnid='" + brCode + "'").getResultList();
            if (!dataList.isEmpty()) {
                return "This Token already issue. Please issue another.";
            } else {
                return "True";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCashMode(String brCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(cashmod,'Y') from parameterinfo where brncode=cast('" + brCode + "' as unsigned)").getResultList();
            String cashMod = "N";
            if (!list.isEmpty()) {
                Vector v7 = (Vector) list.get(0);
                cashMod = v7.get(0).toString();
            }
            return cashMod;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAcNatureByCode(String acCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acctnature from accounttypemaster where acctCode = '" + acCode + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                return (String) element.get(0);
            } else {
                throw new ApplicationException("Account Nature for " + acCode + " does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getBankCode() throws ApplicationException {
        String bankCode = "";
        try {
            List bankCodeList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
            if (bankCodeList.isEmpty()) {
                throw new ApplicationException("Please Fill Bank Details In MB SMS SENDER BANK DETAIL !");
            } else {
                Vector element = (Vector) bankCodeList.get(0);
                bankCode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return bankCode;
    }

    public String getNpciBankCode() throws ApplicationException {
        String bankCode = "";
        try {
            List bankCodeList = em.createNativeQuery("select NPCI_BANK_CODE from mb_sms_sender_bank_detail").getResultList();
            if (bankCodeList.isEmpty()) {
                throw new ApplicationException("Please Fill Bank Details In MB SMS SENDER BANK DETAIL !");
            } else {
                Vector element = (Vector) bankCodeList.get(0);
                bankCode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return bankCode;
    }

    public String getOfficeAccountNo(String acno) throws ApplicationException {
        String officeAccount = "false";
        try {
            String acNature = getAccountNature(acno);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List glDescList = em.createNativeQuery("Select fromno,tono from gl_desc_range where glname='OFFICE ACCOUNT'").getResultList();
                if (!glDescList.isEmpty()) {
                    Vector element = (Vector) glDescList.get(0);
                    Integer fromno = Integer.parseInt(element.get(0).toString());
                    Integer tono = Integer.parseInt(element.get(1).toString());

                    Integer head = Integer.parseInt(acno.substring(4, 10));
                    List headList = em.createNativeQuery("Select glname from gl_desc_range where " + head + ">=" + fromno + " and " + head + "<=" + tono + "").getResultList();
                    if (!headList.isEmpty()) {
                        officeAccount = "true";
                    } else {
                        officeAccount = "false";
                    }
                } else {
                    officeAccount = "false";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return officeAccount;
    }

    public List getOfficeHeadDetails(String acno) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select acname,postflag,msgflag from gltable where acno='" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List checkOfficeAccount() throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("Select fromno,tono from gl_desc_range where glname='OFFICE ACCOUNT'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return checkList;
    }

    public String getAcnoByPurpose(String purpose) throws ApplicationException {
        String acno = "";
        try {
            List dataList = em.createNativeQuery("select acno from abb_parameter_info where purpose='" + purpose + "'").getResultList();
            if (!dataList.isEmpty()) {
                Vector element = (Vector) dataList.get(0);
                acno = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acno;
    }

    public List getAdviceList(int groupcode) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(description) from codebook where groupcode=" + groupcode + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAccountStatusMessage(int accStatus) throws ApplicationException {
        if (accStatus == 1) {
            return "Operative";
        } else if (accStatus == 2) {
            return "Account Has been marked Inoperative";
        } else if (accStatus == 3) {
            return "Account Has been marked Suit Filed";
        } else if (accStatus == 4) {
            return "Account Has been marked Frozen";
        } else if (accStatus == 5) {
            return "Account Has been marked Recalled";
        } else if (accStatus == 6) {
            return "Account Has been marked Decreed";
        } else if (accStatus == 7) {
            return "Withdrawal is not Allowed in this Account";
        } else if (accStatus == 8) {
            return "Account Has been marked Operation Stopped";
        } else if (accStatus == 9) {
            return "Account Has been Closed";
        } else if (accStatus == 10) {
            return "10";
        } else if (accStatus == 11) {
            return "This Account is SUB STANDARD Account";
        } else if (accStatus == 12) {
            return "This Account is DOUBT FUL Account";
        } else if (accStatus == 13) {
            return "This Account is LOSS Account";
        } else if (accStatus == 14) {
            return "This Account is PROTESTED BILL Account";
        } else {
            return "";
        }
    }

    public boolean isUserAuthorized(String userId, String brCode) throws ApplicationException {
        try {
            List levelIdList = em.createNativeQuery("select ifnull(levelid,'') from securityinfo WHERE userid = '" + userId + "' and brncode='" + brCode + "'").getResultList();
            if (levelIdList.isEmpty()) {
                throw new ApplicationException("User does not exist");
            }
            Vector levelIdVec = (Vector) levelIdList.get(0);
            int levelId = Integer.parseInt(levelIdVec.get(0).toString());
            if (levelId == 1 || levelId == 2) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public boolean existInParameterInfoReport(String reportName) throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT code from parameterinfo_report where reportname='" + reportName + "'").getResultList();
            if (list.isEmpty()) {
                return false;
            } else {
                Vector element = (Vector) list.get(0);
                if (element.get(0) != null) {
                    if (element.get(0).toString().equals("1")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean isInstrumentLost(float seqNo, String instNo, String issueDt, String issueBrCode, String billType) throws ApplicationException {
        try {
            List brList = em.createNativeQuery("select alphaCode from branchmaster where  brncode= '" + issueBrCode + "'").getResultList();
            if (brList.isEmpty()) {
                throw new ApplicationException("Data does not exist in branch master");
            }
            String issueBr = "";
            Vector ele = (Vector) brList.get(0);
            if (ele.get(0) != null) {
                issueBr = ele.get(0).toString();
            }
            List list = em.createNativeQuery("select instno from  bill_lost a  WHERE a.INSTNO='" + instNo + "' AND a.seqno=" + seqNo + " and "
                    + "a.billtype='" + billType + "' and a.issueBr = '" + issueBr + "' and a.origindt='" + issueDt + "' and auth='Y' and status='L'").getResultList();
            if (list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getBaseParameter(String accode) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from cbs_base_parameter where account_code='" + accode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List isModuleActiveBasedOnAcCode(String accode) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from cbs_module_based_on_accode where account_code='" + accode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCurrentFinYear(String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select mindate,maxdate from yearend where brncode='" + brCode + "' and yearendflag='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public boolean isPoDdGlhead(String glHead) throws ApplicationException {
        try {
            List rsList = em.createNativeQuery("select acno from gltable where substring(acno,3,8)='" + glHead + "' and msgflag = '4'").getResultList();
            if (rsList.isEmpty()) {
                return false;
            } else {
                Vector ele = (Vector) rsList.get(0);
                if (ele.get(0) != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getPoDdGlhead(String billNature) throws ApplicationException {
        try {
            List rsList = em.createNativeQuery("select glhead from billtypemaster where instcode = '" + billNature + "'").getResultList();
            if (rsList.isEmpty()) {
                throw new ApplicationException("GL Head does not exist in Bill Type Master");
            }
            Vector ele = (Vector) rsList.get(0);
            if (ele.get(0) == null) {
                throw new ApplicationException("GL Head does not exist in Bill Type Master");
            }
            return ele.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getGlHeadFromParam(String brCode, String headName) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acno from abb_parameter_info where purpose = '" + headName + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("GL Head does not exist in abb_parameter_info");
            }
            Vector vect = (Vector) list.get(0);
            return brCode.concat(vect.get(0).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getSmsDetails() throws ApplicationException {
        try {
            return em.createNativeQuery("select bank_code,sms_short_code,sms_sender,sender_id,template_bank_name,"
                    + "sms_vendor from mb_sms_sender_bank_detail").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAcctTypeByCode(String acCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select accttype from accounttypemaster where acctCode = '" + acCode + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                return (String) element.get(0);
            } else {
                throw new ApplicationException("Acct Type for " + acCode + " does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCustName(String acno) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String actNature = getAccountNature(acno);
            if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                dataList = em.createNativeQuery("select custname from td_accountmaster where acno='" + acno + "'").getResultList();
            } else if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                dataList = em.createNativeQuery("select acname from gltable where acno='" + acno + "'").getResultList();
            } else {
                dataList = em.createNativeQuery("select custname from accountmaster where acno='" + acno + "'").getResultList();
            }
            if (dataList.isEmpty()) {
                throw new ApplicationException("Custname is not present for A/c :" + acno);
            }
            Vector element = (Vector) dataList.get(0);
            return (String) element.get(0);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getThriftAccountNumber(String userAccountNumber) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select acno from td_accountmaster where introaccno = '" + userAccountNumber + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Account number does not exist");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getCodeFromCbsParameterInfo(String name) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='" + name + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define values in cbs_parameterinfo for: " + name);
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getValueFromCbsparameterInfo(String name) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='" + name + "'").getResultList();
            if (list.isEmpty()) {
                return "";
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getglheadDD(String acno) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select GLHEAD from billtypemaster where InstNature = '" + CbsConstant.DD_AC + "' and GLHEAD = '" + acno + " '").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Demand Draft GL Head does not exist !");
                //return "Demand Draft GL Head does not exist !";
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//    public String insertSms(String acno, String message) throws ApplicationException {
//        try {
//            MbSubscriberTabDAO mbSubscriberTabDAO = new MbSubscriberTabDAO(em);
//            MbSubscriberTab oldObj = mbSubscriberTabDAO.getEntityByAcno(acno);
//            String templateBankName = "";
//            
//            List<MbSmsSenderBankDetailTO> resultList = smsFacade.getBankAndSenderDetail();
//            for (MbSmsSenderBankDetailTO to : resultList) {
//                templateBankName = to.getTemplateBankName();
//            }
//            message = message + ". Thanks, " + templateBankName + ".";
//            String query = "insert into mb_push_msg_tab (mobile, acno,message,message_status,message_type,dt,enter_by)"
//                    + "values ('" + oldObj.getMobileNo() + "','" + acno + "','" + message + "', 1 ,'TRANSACTIONAL', now(),'System')";
//
//            int rs = em.createNativeQuery(query).executeUpdate();
//            if (rs <= 0) {
//                return "Problem in data insertion";
//            }
//            return "true";
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
    public boolean isBranchExists(String brncode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select branchname from branchmaster where "
                    + "brncode = cast('" + brncode + "' as unsigned)").getResultList();
            if (list.isEmpty()) {
                return false;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    public boolean isActiveAgent(String agentCode, String brnCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select name from ddsagent where status='A' and "
                    + "agcode='" + agentCode + "' and brncode='" + brnCode + "'").getResultList();
            if (list.isEmpty()) {
                return false;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    public boolean isValidDddAccount(String acno) throws ApplicationException {
        try {
            String acNature = getAccountNature(acno);
            if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                //List list = em.createNativeQuery("select custname from accountmaster "
                  //      + "where acno='" + acno + "' and accstatus in(1,2,7,10)").getResultList();
                
                 List list = em.createNativeQuery("select custname from accountmaster "
                        + "where acno='" + acno + "'").getResultList();
                if (list.isEmpty()) {
                    return false;
                }
            } else {
                throw new ApplicationException("Only DDS Account is allowed.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    public String getAccountTable(String nature) throws ApplicationException {
        String table = "";
        try {
            table = "accountmaster";
            if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                table = "td_accountmaster";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return table;
    }

    public String markUnClaimed(String reportBranchCode, String acNature, List<UnclaimedAccountStatementPojo> unClaimedList,
            String today, String userName, String flag, String acType, String savingRoi) throws ApplicationException {
        String msg = "";
        try {
            double totalUnClaimedCrAmount = 0;
            String accountTable = getAccountTable(acNature);
            Float unClaimTrsno = getTrsNo();
            String todayDt = ymd.format(dmyy.parse(today));

            List list = em.createNativeQuery("select ifnull(glheadint,'') as int_head,ifnull(unclaimed_head,'') as "
                    + "unclaimed_head from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Please define interest head for A/c code-->" + acType);
            }
            Vector element = (Vector) list.get(0);
            String intGlHead = reportBranchCode + element.get(0).toString() + "01";
            String unClaimedHead = reportBranchCode + element.get(1).toString();
            if (intGlHead.equals("") || intGlHead.length() != 12 || unClaimedHead.equals("") || unClaimedHead.length() != 12) {
                throw new ApplicationException("Please define proper Int and Unclaimed Gl head.");
            }

            for (UnclaimedAccountStatementPojo pojo : unClaimedList) {
                String detailDt = "";
                double totalInterest = 0;
                //Interest Posting If Any.
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    list = em.createNativeQuery("select date_format(next_int_calc_dt,'%d/%m/%Y') as fromDt from "
                            + "cbs_loan_acc_mast_sec where acno='" + pojo.getAcNo() + "'").getResultList();
                    if (list == null || list.isEmpty()) {
                        throw new ApplicationException("Please define next interest calculation "
                                + "date for A/c-->" + pojo.getAcNo());
                    }
                    element = (Vector) list.get(0);
                    String fromDt = element.get(0).toString();
                    detailDt = fromDt;

                    List<LoanIntCalcList> resultList = intRemote.cbsSbIntCalc("I", "", acType, pojo.getAcNo(),
                            fromDt, today, reportBranchCode);
                    if (!resultList.isEmpty()) {
                        for (LoanIntCalcList lict : resultList) {
                            totalInterest = lict.getTotalInt();
                        }
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    list = em.createNativeQuery("select cast(roi as decimal(14,2)) as roi,date_format(fddt,'%Y%m%d') as fddt "
                            + "from td_vouchmst where acno='" + pojo.getAcNo() + "' and "
                            + "voucherno=" + pojo.getReceiptNo() + "").getResultList();
                    if (list == null || list.isEmpty()) {
                        throw new ApplicationException("There is no entry in td vouchmst for a/c: " + pojo.getAcNo() + " and "
                                + "voucher no: " + pojo.getReceiptNo());
                    }
                    element = (Vector) list.get(0);
                    float roi = Float.parseFloat(element.get(0).toString());
                    String fddt = element.get(1).toString();
                    detailDt = dmyy.format(ymd.parse(fddt));

                    long fullDt = CbsUtil.dayDiff(ymd.parse(fddt), dmyy.parse(pojo.getLastTrnDt()));
                    String year = String.valueOf(fullDt / 365);
                    fullDt = fullDt % 365;
                    String mon = String.valueOf(fullDt / 30);
                    String day = String.valueOf(fullDt % 30);

                    List<Double> tdGlobalValue = autoRenewRemote.tdGlobal(pojo.getAcNo(), Float.parseFloat(pojo.getReceiptNo()), "True", roi, 0, year, mon, day, "");
                    //String[] values = tdGlobalValue.split(": ");
                    double fdInterest = Double.parseDouble(formatter.format(tdGlobalValue.get(3)));

                    double outSt = Double.parseDouble(formatter.format(pojo.getAmount() + fdInterest));
                    String savingFrDt = CbsUtil.dateAdd(ymd.format(dmyy.parse(pojo.getLastTrnDt())), 1);
                    Long savingDiff = CbsUtil.dayDiff(ymd.parse(savingFrDt), dmyy.parse(today));
                    double savingInterest = 0;
                    if (savingDiff > 0) {
                        savingInterest = Double.parseDouble(savingRoi) * savingDiff.doubleValue() * outSt / 36500;
                    }
                    totalInterest = Double.parseDouble(formatter.format(fdInterest + savingInterest));
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    if (acType.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode()) || acType.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                        list = em.createNativeQuery("select date_format(next_int_calc_dt,'%d/%m/%Y') as fromDt from "
                                + "cbs_loan_acc_mast_sec where acno='" + pojo.getAcNo() + "'").getResultList();
                        if (list == null || list.isEmpty()) {
                            throw new ApplicationException("Please define next interest calculation "
                                    + "date for A/c-->" + pojo.getAcNo());
                        }
                        element = (Vector) list.get(0);
                        String fromDt = element.get(0).toString();
                        detailDt = fromDt;
                        String glHead = intLoanRemote.getGlHeads(acType);
                        List<LoanIntCalcList> resultList = intLoanRemote.cbsLoanIntCalc("I", acType, pojo.getAcNo(), fromDt, today, glHead, "", reportBranchCode);
                        if (!resultList.isEmpty()) {
                            for (LoanIntCalcList lict : resultList) {
                                totalInterest = lict.getTotalInt();
                            }
                        }
                    }
                }
                //Interest Posting If any.
                if (totalInterest > 0) {
                    Float trsno = getTrsNo();
                    msg = insertRecons(acNature, pojo.getAcNo(), 0, totalInterest, todayDt, todayDt, 2,
                            "Int from " + detailDt + " to" + today + " in unclaimed marking", userName, trsno,
                            "", getRecNo(), "Y", userName, 8, 3, "", todayDt, 0f, "", "", 0, "",
                            0f, "", null, reportBranchCode, reportBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = updateBalance(acNature, pojo.getAcNo(), totalInterest, 0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = insertRecons(getAccountNature(intGlHead), intGlHead, 1, totalInterest,
                            todayDt, todayDt, 2, "Int from " + detailDt + " to" + today + " in unclaimed marking",
                            userName, trsno, "", getRecNo(), "Y", userName, 8, 3, "", todayDt,
                            0f, "", "", 0, "", 0f, "", null, reportBranchCode, reportBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = updateBalance(getAccountNature(intGlHead), intGlHead, 0,
                            totalInterest, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
                //Un-Claimed Marking.
                double partyBal = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    list = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from td_recon "
                            + "where acno = '" + pojo.getAcNo() + "'").getResultList();
                    element = (Vector) list.get(0);
                    partyBal = Double.parseDouble(element.get(0).toString());
                } else {
                    partyBal = Double.parseDouble(formatter.format(Double.parseDouble(ftsGetBal(pojo.getAcNo()))));
                }
                if (partyBal > 0) {
                    totalUnClaimedCrAmount += partyBal;

                    msg = insertRecons(acNature, pojo.getAcNo(), 1, partyBal, todayDt, todayDt, 2,
                            "Unclaimed Marking Dr Amount", userName, unClaimTrsno,
                            "", getRecNo(), "Y", userName, 0, 3, "", todayDt, 0f, "", "", 0, "",
                            0f, "", null, reportBranchCode, reportBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    double updateBal = partyBal;
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                            || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        updateBal = totalInterest;
                    }
                    msg = updateBalance(acNature, pojo.getAcNo(), 0, updateBal, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    if (flag.equalsIgnoreCase("R")) {   //For Bulk Marking.
                        int result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,"
                                + "enterby,auth,effdt,baseacno,voucher_no,authby,trantime) values ('" + pojo.getAcNo() + "','DEAF',"
                                + "'15','" + todayDt + "'," + partyBal + ",'" + userName + "','Y','" + todayDt + "',"
                                + "'" + pojo.getAcNo() + "'," + Double.parseDouble(pojo.getReceiptNo()) + ",'System',now())").executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getAcNo());
                        }
                        result = em.createNativeQuery("update " + accountTable + " set accstatus=15 where "
                                + "acno='" + pojo.getAcNo() + "'").executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In A/c Table Updation. " + pojo.getAcNo());
                        }
                    } else {    //For Individual Marking.
                        int result = em.createNativeQuery("update accountstatus a inner join(select max(spno) as spno from "
                                + "accountstatus where acno='" + pojo.getAcNo() + "' and spflag=15 and "
                                + "effdt='" + todayDt + "') b on a.spno=b.spno set amount = " + partyBal + " where "
                                + "acno='" + pojo.getAcNo() + "' and spflag=15 and effdt='" + todayDt + "'").executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In Account Status Table Updation. " + pojo.getAcNo());
                        }
                    }
                }
            }
            //Unclaimed Gl Cr amount.
            if (totalUnClaimedCrAmount > 0) {
                msg = insertRecons(getAccountNature(unClaimedHead), unClaimedHead, 0, Double.parseDouble(formatter.format(totalUnClaimedCrAmount)),
                        todayDt, todayDt, 2, "Unclaimed Marking Cr Amount", userName, unClaimTrsno, "", getRecNo(),
                        "Y", userName, 0, 3, "", todayDt, 0f, "", "", 0, "", 0f, "", null, reportBranchCode, reportBranchCode,
                        0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = updateBalance(getAccountNature(unClaimedHead), unClaimedHead,
                        totalUnClaimedCrAmount, 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                //Insertion of posting details.
                list = em.createNativeQuery("select ifnull(max(sno),0)+1 as sno from cbs_loan_acctype_interest_parameter").getResultList();
                element = (Vector) list.get(0);
                int sno = Integer.parseInt(element.get(0).toString());

                int result = em.createNativeQuery("INSERT INTO cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,"
                        + "POST_FLAG,DT,POST_DT,BRNCODE,ENTER_BY,FLAG) VALUES(" + sno + ",'" + acType + "','" + todayDt + "',"
                        + "'" + todayDt + "','Y','" + todayDt + "','" + todayDt + "','" + reportBranchCode + "','System',"
                        + "'UC')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Cbs Loan Acctype Interest Parameter "
                            + "Table Insertion. ");
                }
            }
            return msg = "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCodeByReportName(String reportName) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select code from parameterinfo_report "
                    + "where reportname='" + reportName + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define values in parameterinfo report for: " + reportName);
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<String> getAccountPresentStatus(String acno) throws ApplicationException {
        List<String> dataList = new ArrayList<String>();
        String status = "", statusDesc = "";
        try {
            String accountTable = getAccountTable(getAccountNature(acno));
            String accStatusQuery = "select accstatus from " + accountTable + " where acno='" + acno + "'";

            List list = em.createNativeQuery("" + accStatusQuery + "").getResultList();
            Vector ele = (Vector) list.get(0);
            status = ele.get(0).toString();

            list = em.createNativeQuery("select description from codebook where groupcode=3 and "
                    + "code in(" + accStatusQuery + ")").getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
                statusDesc = ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        dataList.add(status);
        dataList.add(statusDesc);
        return dataList;
    }

//    public String isPrimaryAc(String custId, String acno) throws ApplicationException {
//        try {
//            custId = custId.trim();
//            acno = acno.trim();
//            String nature = getAccountNature(acno);
//            if (!(nature.equalsIgnoreCase(CbsConstant.SAVING_AC)
//                    || nature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
//                throw new ApplicationException("Invalid A/c Natue For Primary A/c.");
//            }
//            List list = em.createNativeQuery("select acno from customerid where custid='" + custId + "' and "
//                    + "acno='" + acno + "'").getResultList();
//            if (list == null || list.isEmpty()) {
//                list = em.createNativeQuery("select acno from accountmaster where custid1='" + custId + "' and acno='" + acno + "' "
//                        + "union select acno from accountmaster where custid2='" + custId + "' and acno='" + acno + "' "
//                        + "union select acno from accountmaster where custid3='" + custId + "' and acno='" + acno + "' "
//                        + "union select acno from accountmaster where custid4='" + custId + "' and acno='" + acno + "'").getResultList();
//                if (list == null || list.isEmpty()) {
//                    throw new ApplicationException("Invalid A/c Number For CustId. " + custId);
//                }
//            }
//            list = em.createNativeQuery("select accstatus from accountmaster "
//                    + "where acno = '" + acno + "'").getResultList();
//            if (list == null || list.isEmpty()) {
//                throw new ApplicationException("A/c no does not exists for Primary A/c.");
//            }
//            Vector ele = (Vector) list.get(0);
//            if (Integer.parseInt(ele.get(0).toString()) != 1) {
//                throw new ApplicationException("Invalid A/c No for Primary A/c.");
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return "true";
//    }
    public String isPrimaryAc(String custId, String acno) throws ApplicationException {
        try {
            custId = custId.trim();
            acno = acno.trim();
            String nature = getAccountNature(acno);
            String acctcode = getAccountCode(acno);
            if (!(nature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || (nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    && getAcctTypeByCode(acctcode).equalsIgnoreCase(CbsConstant.CURRENT_AC)))) {
                throw new ApplicationException("Invalid A/c Natue For Primary A/c.");
            }
            List list = em.createNativeQuery("select acno from customerid where custid='" + custId + "' and "
                    + "acno='" + acno + "'").getResultList();
            if (list == null || list.isEmpty()) {
                list = em.createNativeQuery("select acno from accountmaster where custid1='" + custId + "' and acno='" + acno + "' "
                        + "union select acno from accountmaster where custid2='" + custId + "' and acno='" + acno + "' "
                        + "union select acno from accountmaster where custid3='" + custId + "' and acno='" + acno + "' "
                        + "union select acno from accountmaster where custid4='" + custId + "' and acno='" + acno + "'").getResultList();
                if (list == null || list.isEmpty()) {
                    throw new ApplicationException("Invalid A/c Number For CustId. " + custId);
                }
            }
            list = em.createNativeQuery("select accstatus from accountmaster "
                    + "where acno = '" + acno + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("A/c no does not exists for Primary A/c.");
            }
            Vector ele = (Vector) list.get(0);
            if (Integer.parseInt(ele.get(0).toString()) != 1) {
                throw new ApplicationException("Invalid A/c No for Primary A/c.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String isAadharExists(String custId, String aadharNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select customerid from cbs_customer_master_detail "
                    + "where aadhaar_no = '" + aadharNo.trim() + "' and "
                    + "customerid <> '" + custId.trim() + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String mappedCustId = ele.get(0).toString();
                throw new ApplicationException("This Aadhar is already mapped with Customer Id: " + mappedCustId);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String isLpgIdExists(String custId, String lpgId) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select customerid from cbs_customer_master_detail "
                    + "where lpg_id = '" + lpgId.trim() + "' and "
                    + "customerid <> '" + custId.trim() + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String mappedCustId = ele.get(0).toString();
                throw new ApplicationException("This Lpg is already mapped with Customer Id: " + mappedCustId);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String isIdentityNoExists(String identityNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select CustomerId from cbs_cust_identity_details where IdentityNo = '" + identityNo + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String mappedCustId = ele.get(0).toString();
                throw new ApplicationException("This Identity no is already mapped with Customer Id:" + mappedCustId);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String isCustomerIdentityNoExists(String identityNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select customerid from cbs_customer_master_detail where IdentityNo = '" + identityNo + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String mappedCustId = ele.get(0).toString();
                throw new ApplicationException("This Identity no is already mapped with Customer Id:" + mappedCustId);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public boolean isAccountNPA(String acno) throws ApplicationException {
        try {
            String nature = getAccountNature(acno);
            if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                    || nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List opStatusList = em.createNativeQuery("SELECT accstatus FROM accountmaster WHERE ACNO ='" + acno + "' and accstatus in (3,5,6,11,12,13,14)").getResultList();
                if (opStatusList.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String lastTxnDateUpdation(String acno) throws ApplicationException {
        try {
            String nature = getAccountNature(acno);
            if (!(nature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || nature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || nature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                int n = em.createNativeQuery("update accountmaster set "
                        + "last_txn_date=now() where acno='" + acno + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in last txn date updation.");
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String fdPaymentRenewalTxn(String accNature, String acNo, Integer tranType, Integer ty, Double amt, String dt, String valueDt,
            String enterBy, String orgnBrCode, String destBrCode, Integer tranDesc, String details, Float trsNo, Float recNo, Integer tranId,
            String termId, String auth, String authBy, String subTokenNo, Integer payBy, String instNo, String instDt, String tdacNo, Float vchNo,
            String intFlag, String closeFlag, String screenFlag, String txnStatus, Float tokenNoDr) throws ApplicationException {
        try {
            String msg = insertRecons(accNature, acNo, ty, amt, dt, valueDt, tranType, details, enterBy, trsNo, null, recNo, auth, authBy,
                    tranDesc, payBy, instNo, instDt, tokenNoDr, null, subTokenNo, 1, tdacNo, vchNo, intFlag, closeFlag, orgnBrCode, destBrCode,
                    null, null, null, null);
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public Integer getCodeForReportName(String reportName) throws ApplicationException {
        Integer code = 0;
        try {
            List list = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where "
                    + "reportname in('" + reportName + "')").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                return Integer.parseInt(ele.get(0).toString().trim());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return code;
    }

    public String getCentralFinYear() throws ApplicationException {
        String fYear;
        String tmpDate = ymd.format(new Date());
        try {
            List tmpseries = em.createNativeQuery("SELECT date FROM cbs_bankdays WHERE DAYENDFLAG = 'N' and DAYBEGINFLAG = 'Y'").getResultList();
            if (tmpseries.size() > 0) {
                Vector elebal = (Vector) tmpseries.get(0);
                tmpDate = elebal.get(0).toString();
            }
            List selectQuery = em.createNativeQuery("SELECT f_year FROM cbs_yearend where mindate<= '" + ymd.format(ymmd.parse(tmpDate)) + "' and maxdate >= '" + ymd.format(ymmd.parse(tmpDate)) + "'").getResultList();
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

    public List getThreshLmtParam() throws ApplicationException {
        List chkList2 = new ArrayList();
        try {
            chkList2 = em.createNativeQuery("SELECT ifnull(CODE,0) FROM parameterinfo_report WHERE REPORTNAME='THRESHOLD_LIMIT_ON'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return chkList2;
    }

    public String isThreshLmtExceed(String acNo, double amt, String toDt) throws ApplicationException {
        try {
            double thrAmt = 0;
            //String toDt = ymd.format(ymmd.parse(dt));
            List listCust = em.createNativeQuery("select ifnull(a.ThresoldTransactionLimit,0) from customerid c,cbs_customer_master_detail a "
                    + " where c.custid = a.customerid and c.acno = '" + acNo + "'").getResultList();
            if (listCust == null || listCust.isEmpty()) {
                return "This Acno does not have Customer Id ";
            } else {
                Vector ele = (Vector) listCust.get(0);
                thrAmt = Double.parseDouble(ele.get(0).toString());
            }

            List list = em.createNativeQuery("select c.acno,a.acctnature from customerid c, accounttypemaster a where c.custid in ("
                    + " select custid from customerid where acno = '" + acNo + "') and substring(c.acno,3,2) = a.acctcode "
                    + " and a.acctcode in (select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.SAVING_AC + "','" + CbsConstant.CURRENT_AC + "'))").getResultList();
            if (list == null || list.isEmpty()) {
                return "This Acno does not have Customer Id ";
            } else {
                double crTotAmt = 0;
                String finYr = getCentralFinYear();
                String frmDT = finYr + "0401";
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    String accno = element.get(0).toString();
                    String acNat = element.get(1).toString();
                    if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        List crTotList = em.createNativeQuery("SELECT ifnull(sum(cramt),0) FROM recon where acno = '" + accno + "' and "
                                + " trantype <>8 and dt between '" + frmDT + "' and '" + toDt + "' ").getResultList();
                        Vector crTotVect = (Vector) crTotList.get(0);
                        crTotAmt = crTotAmt + Double.parseDouble(crTotVect.get(0).toString());

                    } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List crTotList = em.createNativeQuery("SELECT ifnull(sum(cramt),0) FROM ca_recon where acno = '" + accno + "' and "
                                + " trantype <>8 and dt between '" + frmDT + "' and '" + toDt + "' ").getResultList();
                        Vector crTotVect = (Vector) crTotList.get(0);
                        crTotAmt = crTotAmt + Double.parseDouble(crTotVect.get(0).toString());
                    }
                    List unAuthCrTotList = em.createNativeQuery("select ifnull(sum(a.cramt),0) from ((SELECT CrAmt from "
                            + "tokentable_credit where Acno='" + accno + "' and dt='" + toDt + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                            + "union all "
                            + "(SELECT CrAmt from "
                            + "tokentable_debit where Acno='" + accno + "' and dt='" + toDt + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                            + "union all "
                            + "(SELECT CrAmt from "
                            + "recon_cash_d where Acno='" + accno + "' and dt='" + toDt + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                            + "union all "
                            + "(SELECT CrAmt from "
                            + "recon_clg_d where Acno='" + accno + "' and dt='" + toDt + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                            + "union all "
                            + "(SELECT CrAmt from "
                            + "recon_trf_d where Acno='" + accno + "' and dt='" + toDt + "' and (auth='N' OR auth IS NULL) order by dt,recno)) a ").getResultList();
                    Vector unAuthCrVect = (Vector) unAuthCrTotList.get(0);
                    crTotAmt = crTotAmt + Double.parseDouble(unAuthCrVect.get(0).toString());
                }
                crTotAmt = crTotAmt + amt;
                if (crTotAmt > thrAmt) {
                    return "ThreshHold Limit Exceed For this Customer";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String saveTLAcOpenDetailWithOutTranMgmt(String cust_id, String tmpANat, String appTp, String appSeq,
            String FYear, String actype, String orgncode, String agcode, String acOpDt, String title,
            String custname, String fathername, String praddress, String craddress, String phoneno,
            String panno, String dob, Integer occupation, String grdname, String grd_relation,
            Integer operatingMode, String JtName1, String JtName2, String JtName3, String JtName4,
            String nominee, String nominee_relatioship, String nomineeAdd, String nomineeDob,
            String acnoIntro, Float Odlimit, Float roi, String sancDtV, String IntOpt, String subSidyAmt,
            Integer docuno, String docudetails, String spInst, String UserText, String schemeCode,
            Integer moritoriumPeriod, Float acnoPreDr, Float acnoPreCr, String rateCode, String calMethod, String calOn,
            String intAppFrequency, String calLevel, String compoundFrequency, String disbursmentType, String intCode,
            String paggingFrequency, Integer LoanPeriod1, Integer LoanPeriod2, String jtCustId1, String jtCustId2,
            String jtCustId3, String jtCustId4, String actCateg, String folioNo, String hufFamily, int chqOpt) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
        String message = "";
        try {
//            ut.begin();
            int var1 = 0, var2 = 0, var3 = 0, var4 = 0, var5 = 0, var6 = 0, var7 = 0, var8 = 0, var9 = 0, var10 = 0, var11 = 0, var12 = 0, var13 = 0, var14 = 0, var15 = 0, vari = 0;
            String tempbd = null;
            Float tmpPenalty = 0.0f;
            String TempDate = null;
            String tempAge = null;
            String cust_no = null;
            String cust_no_new1 = null;
            String Staffacno = null;
            String Tempacno = null;
            String tmpLastDate = null;
            String tmpLastDt = null;
            Integer tmpCKBook = 0;
            String rsKC = null;
            String alphacode = null;
            String sancDt = null;
            String status = null;
            String schFlg = null;
            String stOpt = null;
            String memFlg = null;
            String shareFlg = null;
            String intAcOpenEnableInStaff = null;
            String acTypeOpenInStaff = null;

            Pattern p = Pattern.compile("[a-zA-z]+([ '-][a-zA-Z]+)*");
            Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");

            if (tmpANat == null || tmpANat.equalsIgnoreCase("") || tmpANat.length() == 0) {
//                ut.rollback();
                message = "ACCOUNT NATURE IS BLANK !!!";
                return message;
            }
            if (actype == null || actype.equalsIgnoreCase("") || actype.length() == 0) {
//                ut.rollback();
                message = "ACCOUNT TYPE IS BLANK !!!";
                return message;
            }
            if (orgncode == null || orgncode.equalsIgnoreCase("") || orgncode.length() == 0) {
//                ut.rollback();
                message = "BRANCH CODE IS BLANK !!!";
                return message;
            }
            if (agcode == null || agcode.equalsIgnoreCase("") || agcode.length() == 0) {
//                ut.rollback();
                message = "AGENT CODE IS BLANK !!!";
                return message;
            }
            if (custname == null || custname.equalsIgnoreCase("") || custname.length() == 0) {
//                ut.rollback();
                message = "CUSTOMER NAME IS BLANK !!!";
                return message;
            }
//            if (custname.length() < 3) {
////                ut.rollback();
//                message = "CUSTOMER NAME LENGTH SHOULD BE MINIMUM THREE !!!";
//                return message;
//            }
//            if (fathername == null || fathername.equalsIgnoreCase("") || fathername.length() == 0) {
//            } else {
//                if (fathername.length() < 3) {
////                    ut.rollback();
//                    message = "FATHER / HUSBAND NAME LENGTH SHOULD BE MINIMUM THREE !!!";
//                    return message;
//                }
//                Matcher fathernameCheck = p.matcher(fathername);
//                if (!fathernameCheck.matches()) {
////                    ut.rollback();
//                    message = "FATHER / HUSBAND NAME SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
//                    return message;
//                }
//            }
            if (praddress == null || praddress.equalsIgnoreCase("") || praddress.length() == 0) {
//                ut.rollback();
                message = "PERMANENT ADDRESS IS BLANK !!!";
                return message;
            }
//            if (praddress.length() < 10) {
//                ut.rollback();
//                message = "PERMANENT ADDRESS LENGTH SHOULD BE MINIMUM TEN !!!";
//                return message;
//            }
            if (craddress == null || craddress.equalsIgnoreCase("") || craddress.length() == 0) {
//                ut.rollback();
                message = "CORRESPONDANCE ADDRESS IS BLANK !!!";
                return message;
            }
//            if (craddress.length() < 10) {
//                ut.rollback();
//                message = "CORRESPONDANCE ADDRESS LENGTH SHOULD BE MINIMUM TEN !!!";
//                return message;
//            }
            if (dob == null) {
//                ut.rollback();
                message = "DATE OF BIRTH IS BLANK !!!";
                return message;
            }
            // comment by manish kumar
//            if (occupation == null) {
////                ut.rollback();
//                message = "INVALID VALUE FOR OCCUPATION ( NULL ) !!!";
//                return message;
//            }
            if (operatingMode == null) {
//                ut.rollback();
                message = "INVALID VALUE FOR OPERATION MODE ( NULL ) !!!";
                return message;
            }
            if ((nominee == null) || (nominee.equalsIgnoreCase("")) || (nominee.length() == 0)) {
            } else {
                if (nominee_relatioship == null) {
//                    ut.rollback();
                    message = "NOMINEE RELATIONSHIP CANNOT BE BLANK !!!";
                    return message;
                }
                if (nomineeAdd == null) {
//                    ut.rollback();
                    message = "NOMINEE ADDRESS CANNOT BE BLANK !!!";
                    return message;
                }
                if (nomineeDob == null) {
//                    ut.rollback();
                    message = "NOMINEE DATE OF BIRTH CANNOT BE BLANK !!!";
                    return message;
                }
            }
            if (Odlimit == null || Odlimit < 0) {
//                ut.rollback();
                message = "SANCTION AMOUNT CAN NOT BE ZERO OR LESS THAN ZERO !!!";
                return message;
            }
            if (roi == null) {
//                ut.rollback();
                message = "ROI IS BLANK !!!";
                return message;
            }
            if (roi < 0) {
//                ut.rollback();
                message = "ROI CANNOT BE LESS THAN ZERO !!!";
                return message;
            }
            if (IntOpt == null || IntOpt.equalsIgnoreCase("") || IntOpt.length() == 0) {
//                ut.rollback();
                message = "INTEREST OPTION IS BLANK !!!";
                return message;
            }

            if (acnoIntro == null || acnoIntro.equalsIgnoreCase("") || acnoIntro.length() == 0) {
                acnoIntro = "";
            }
            if (moritoriumPeriod == null) {
//                ut.rollback();
                message = "MORATORIUM PERIOD IS BLANK !!!";
                return message;
            }
            if (moritoriumPeriod < 0) {
//                ut.rollback();
                message = "MORATORIUM PERIOD CANNOT BE LESS THAN ZERO !!!";
                return message;
            }
            if (acnoPreDr == null) {
//                ut.rollback();
                message = "ACCOUNT PREFERABLE DR. IS BLANK !!!";
                return message;
            }
            if (acnoPreDr < 0) {
//                ut.rollback();
                message = "ACCOUNT PREFERABLE DR. CANNOT BE LESS THAN ZERO !!!";
                return message;
            }
            if (acnoPreCr == null) {
//                ut.rollback();
                message = "ACCOUNT PREFERABLE CR. IS BLANK !!!";
                return message;
            }
            if (acnoPreCr < 0) {
//                ut.rollback();
                message = "ACCOUNT PREFERABLE CR. CANNOT BE LESS THAN ZERO !!!";
                return message;
            }
            if (LoanPeriod1 == null) {
//                ut.rollback();
                message = "LOAN PERIOD MM IS BLANK !!!";
                return message;
            }
            if (LoanPeriod1 < 0) {
//                ut.rollback();
                message = "LOAN PERIOD MM CANNOT BE LESS THAN ZERO !!!";
                return message;
            }
            if (LoanPeriod2 == null) {
//                ut.rollback();
                message = "LOAN PERIOD DD IS BLANK !!!";
                return message;
            }
            if (LoanPeriod2 < 0) {
//                ut.rollback();
                message = "LOAN PERIOD DD CANNOT BE LESS THAN ZERO !!!";
                return message;
            }

            List custList = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + cust_id + "'").getResultList();
            Vector cuLst = (Vector) custList.get(0);
            String cu_id = cuLst.get(0).toString() != null ? cuLst.get(0).toString() : "";
            if ((cu_id.equalsIgnoreCase("N")) || (cu_id == null) || (cu_id.equalsIgnoreCase(""))) {
//                ut.rollback();
                return "Customer Verification is not completed. !";
            }

            String cFlag = accOpenFacade.custMergedFlag(cust_id);
            if (cFlag.equalsIgnoreCase("false")) {
//                ut.rollback();
                return "Customer Id Is Merged.";
            }

            if (!((jtCustId1 == null) || (jtCustId1.trim().equalsIgnoreCase("")))) {
                String cFlag1 = accOpenFacade.custMergedFlag(jtCustId1);
                if (cFlag1.equalsIgnoreCase("false")) {
//                    ut.rollback();
                    return "Joint Holder1 Customer Id Is Merged.";
                }

                List custList1 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtCustId1 + "'").getResultList();
                Vector cuLst1 = (Vector) custList1.get(0);
                String cu_id1 = cuLst1.get(0).toString() != null ? cuLst1.get(0).toString() : "";
                if ((cu_id1.equalsIgnoreCase("N")) || (cu_id1 == null) || (cu_id1.equalsIgnoreCase(""))) {
//                    ut.rollback();
                    return "Joint Holder1 Customer id " + jtCustId1 + " Verification is not completed.";
                }
            }

            if (!((jtCustId2 == null) || (jtCustId2.trim().equalsIgnoreCase("")))) {
                String cFlag2 = accOpenFacade.custMergedFlag(jtCustId2);
                if (cFlag2.equalsIgnoreCase("false")) {
//                    ut.rollback();
                    return "Joint Holder2 Customer Id Is Merged.";
                }

                List custList2 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtCustId2 + "'").getResultList();
                Vector cuLst2 = (Vector) custList2.get(0);
                String cu_id2 = cuLst2.get(0).toString() != null ? cuLst2.get(0).toString() : "";
                if ((cu_id2.equalsIgnoreCase("N")) || (cu_id2 == null) || (cu_id2.equalsIgnoreCase(""))) {
//                    ut.rollback();
                    return "Joint Holder2 Customer id " + jtCustId2 + " Verification is not completed.";
                }
            }

            if (!((jtCustId3 == null) || (jtCustId3.equalsIgnoreCase("")))) {
                String cFlag3 = accOpenFacade.custMergedFlag(jtCustId3);
                if (cFlag3.equalsIgnoreCase("false")) {
//                    ut.rollback();
                    return "Joint Holder3 Customer Id Is Merged.";
                }

                List custList3 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtCustId3 + "'").getResultList();
                Vector cuLst3 = (Vector) custList3.get(0);
                String cu_id3 = cuLst3.get(0).toString() != null ? cuLst3.get(0).toString() : "";
                if ((cu_id3.equalsIgnoreCase("N")) || (cu_id3 == null) || (cu_id3.equalsIgnoreCase(""))) {
//                    ut.rollback();
                    return "Joint Holder3 Customer id " + jtCustId3 + " Verification is not completed.";
                }
            }

            if (!((jtCustId4 == null) || (jtCustId4.equalsIgnoreCase("")))) {
                String cFlag4 = accOpenFacade.custMergedFlag(jtCustId4);
                if (cFlag4.equalsIgnoreCase("false")) {
//                    ut.rollback();
                    return "Joint Holder4 Customer Id Is Merged.";
                }

                List custList4 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtCustId4 + "'").getResultList();
                Vector cuLst4 = (Vector) custList4.get(0);
                String cu_id4 = cuLst4.get(0).toString() != null ? cuLst4.get(0).toString() : "";
                if ((cu_id4.equalsIgnoreCase("N")) || (cu_id4 == null) || (cu_id4.equalsIgnoreCase(""))) {
//                    ut.rollback();
                    return "Joint Holder4 Customer id " + jtCustId4 + " Verification is not completed.";
                }
            }

            if (!actype.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                List sancDtdiffLt = em.createNativeQuery("SELECT TIMESTAMPDIFF(day,'" + sancDtV + "',curdate())").getResultList();
                if (!sancDtdiffLt.isEmpty()) {
                    Vector ele = (Vector) sancDtdiffLt.get(0);
                    if (Integer.parseInt(ele.get(0).toString()) < 0) {
//                        ut.rollback();
                        message = "SANCTION DATE CANNOT BE GREATOR THAN CURRENT DATE !!!";
                        return message;
                    }
                }
            }

            if (tmpANat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || tmpANat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || tmpANat.equalsIgnoreCase(CbsConstant.SS_AC)) {
                if (subSidyAmt == null || subSidyAmt.equalsIgnoreCase("") || subSidyAmt.length() == 0) {
//                    ut.rollback();
                    message = "SUBSIDY AMOUNT IS BLANK !!!";
                    return message;
                }
                if (Float.parseFloat(subSidyAmt) < 0) {
//                    ut.rollback();
                    message = "SUBSIDY AMOUNT CANNOT BE LESS THAN ZERO !!!";
                    return message;
                }
            }

            if (appTp.equalsIgnoreCase("true")) {
                if (appSeq == null) {
//                    ut.rollback();
                    message = "APPLICATION SEQUENCE IS BLANK !!!";
                    return message;
                }
            }
            if (acnoIntro == null || acnoIntro.equalsIgnoreCase("") || acnoIntro.length() == 0) {
            } else {
                String acNature = getAccountNature(acnoIntro);
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List chk1 = em.createNativeQuery("SELECT accstatus from td_accountmaster where acno='" + acnoIntro + "'").getResultList();
                    if (chk1.isEmpty()) {
//                        ut.rollback();
                        message = "INTRODUCER ACCOUNT NO. DOES NOT EXIST !!!";
                        return message;
                    } else {
                        Vector ele = (Vector) chk1.get(0);
                        if (ele.get(0).toString().equalsIgnoreCase("9")) {
//                            ut.rollback();
                            message = "INTRODUCER ACCOUNT NO. HAS BEEN CLOSED !!!";
                            return message;
                        }
                    }
                } else {
                    List chk2 = em.createNativeQuery("SELECT accstatus from accountmaster where acno='" + acnoIntro + "'").getResultList();
                    if (chk2.isEmpty()) {
//                        ut.rollback();
                        message = "INTRODUCER ACCOUNT NO. DOES NOT EXIST !!!";
                        return message;
                    } else {
                        Vector ele = (Vector) chk2.get(0);
                        if (ele.get(0).toString().equalsIgnoreCase("9")) {
//                            ut.rollback();
                            message = "INTRODUCER ACCOUNT NO. HAS BEEN CLOSED !!!";
                            return message;
                        }
                    }
                }
            }

            if (acnoIntro == null || acnoIntro.equalsIgnoreCase("") || acnoIntro.length() == 0) {
            } else {
                List lt1 = em.createNativeQuery("SELECT CUSTID from customerid WHERE ACNO='" + acnoIntro + "'").getResultList();
                Vector ltv = (Vector) lt1.get(0);
                if (ltv.get(0).toString().equalsIgnoreCase(cust_id)) {
//                    ut.rollback();
                    message = "SORRY, INTRODUCER A/C. HOLDER CANNOT INTRODUCE FOR SELF i.e. CUST ID FOR WHICH YOU ARE OPENING ACCOUNT AND INTRODUCER CUST ID ARE SAME !!!";
                    return message;
                }
            }

            List chk3 = em.createNativeQuery("SELECT TIMESTAMPDIFF(day,curdate(),DATE_FORMAT('" + dob + "', '%Y-%m-%d'))").getResultList();
            if (!chk3.isEmpty()) {
                Vector ele = (Vector) chk3.get(0);
                if (Integer.parseInt(ele.get(0).toString()) > 0) {
//                    ut.rollback();
                    message = "DATE OF BIRTH CAN NOT BE GREATER THAN CURRENT DATE !!!";
                    return message;
                }
            }

            Integer custAge;
            List custAgeLt = em.createNativeQuery("SELECT TIMESTAMPDIFF(year,DATE_FORMAT('" + dob + "', '%Y-%m-%d'),curdate())").getResultList();
            Vector custAgeLtVec = (Vector) custAgeLt.get(0);
            custAge = Integer.parseInt(custAgeLtVec.get(0).toString());
            if (custAge >= 18) {
                status = "MJ";
            } else {
                status = "MN";
            }

            if ((custAge < 18) && (grdname == null || grdname.length() == 0 || grdname.equalsIgnoreCase(""))) {
                if (!title.equalsIgnoreCase("M/S")) {
//                    ut.rollback();
                    message = "CUSTOMER IS MINOR ACCORDING TO DOB , SO PLEASE ENTER GUARDIAN NAME AND RELATIONSHIP BOTH !!!";
                    return message;
                }
            }

            if (grdname == null || grdname.length() == 0 || grdname.equalsIgnoreCase("")) {
            } else {
                Matcher grdnameCheck = p.matcher(grdname);
                if (!grdnameCheck.matches()) {
//                    ut.rollback();
                    message = "GUARDIAN NAME SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                    return message;
                }
            }

            if ((custAge < 18) && (grd_relation == null || grd_relation.length() == 0 || grd_relation.equalsIgnoreCase(""))) {
                if (!title.equalsIgnoreCase("M/S")) {
//                    ut.rollback();
                    message = "PLEASE ENTER GUARDIAN RELATIONSHIP !!!";
                    return message;
                }
            }

            if (grd_relation == null || grd_relation.length() == 0 || grd_relation.equalsIgnoreCase("")) {
            } else {
                Matcher grdrelationCheck = p.matcher(grd_relation);
                if (!grdrelationCheck.matches()) {
//                    ut.rollback();
                    message = "GUARDIAN RELATIONSHIP SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                    return message;
                }
            }
            if (docudetails == null || docudetails.equalsIgnoreCase("") || docudetails.length() == 0) {
            } else if (!validateString(docudetails)) {
//                ut.rollback();
                message = "DOCUMENT DETAIL SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                return message;
            }
            if (spInst == null || spInst.equalsIgnoreCase("") || spInst.length() == 0) {
            } else if (!validateString(spInst)) {
//                ut.rollback();
                message = "SPECIAL INSTRUCTIONS SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                return message;
            }

            List schList = new ArrayList();
            schList = accOpenFacade.chkSchDetails(schemeCode);
            if (!schList.isEmpty()) {
                Vector schLst = (Vector) schList.get(0);
                schFlg = schLst.get(0).toString();
                stOpt = schLst.get(1).toString();
                memFlg = schLst.get(2).toString();
                if (schFlg.equalsIgnoreCase("Y")) {
                    if (memFlg.equalsIgnoreCase("Y")) {
                        List memChk = em.createNativeQuery("select * from share_holder where custid ='" + cust_id + "'").getResultList();
                        if (memChk.isEmpty()) {
//                            ut.rollback();
                            message = "SCHEME IS ONLY FOR THE MEMBER CUSTOMER !!!";
                            return message;
                        }
                    }
                    /*Commented due to Army don't require at the time of account opening********/
                    /*List shareChk = em.createNativeQuery("select reg_pass_sheet_print_event from cbs_scheme_currency_details where scheme_Code ='" + schemeCode + "'").getResultList();
                     if (!shareChk.isEmpty()) {
                     Vector shareLst = (Vector) shareChk.get(0);
                     shareFlg = shareLst.get(0).toString();
                     if (shareFlg.equalsIgnoreCase("Y")) {
                     String cmpSH = accOpenFacade.shareCompare(cust_id, Odlimit, schemeCode);
                     if (!cmpSH.equalsIgnoreCase("true")) {
                     //                                ut.rollback();
                     message = "SHARE IS NOT SUFFICIENT FOR THE LOAN !!!";
                     return message;
                     }
                     }
                     }*/

                    String cCmp = accOpenFacade.loanAmtCompare(cust_id, Odlimit, schemeCode, folioNo.equalsIgnoreCase("") ? "msg" : "", "");
                    /**
                     * **IF folioNo is blank THEN method return in the form of
                     * Message ELSE method return in the OD value as per
                     * calculation ****
                     */
                    if (!folioNo.equalsIgnoreCase("")) {
                        Odlimit = Float.parseFloat(cCmp);
                    } else {
                        if (!cCmp.equalsIgnoreCase("true")) {
                            //                        ut.rollback();
                            return cCmp;
                        }
                    }
                }
            } else {
//                ut.rollback();
                message = "SCHEME CODE NOT DEFINED IN MASTER TABLE !!!";
                return message;
            }

            List date = em.createNativeQuery("select DATE_FORMAT(date,'%Y-%m-%d') from bankdays where dayendflag='N' and brncode='" + orgncode + "'").getResultList();
            Vector recLst = (Vector) date.get(0);
            tempbd = recLst.get(0).toString();

            List chk4 = em.createNativeQuery("select DATE_FORMAT('" + acOpDt + "', '%Y%m%d')").getResultList();
            Vector recLst1 = (Vector) chk4.get(0);
            TempDate = recLst1.get(0).toString();

            List chk5 = em.createNativeQuery("select DATE_FORMAT('" + dob + "','%Y%m%d')").getResultList();
            Vector recLst2 = (Vector) chk5.get(0);
            tempAge = recLst2.get(0).toString();

            List chk6 = em.createNativeQuery("select DATE_FORMAT('" + sancDtV + "','%Y%m%d')").getResultList();
            Vector recLst3 = (Vector) chk6.get(0);
            sancDt = recLst3.get(0).toString();

            List chk7 = em.createNativeQuery("SELECT alphacode from branchmaster where brncode='" + orgncode + "'").getResultList();
            Vector recLst4 = (Vector) chk7.get(0);
            alphacode = recLst4.get(0).toString();

            List chk8 = em.createNativeQuery("SELECT Penalty FROM accounttypemaster WHERE acctcode = '" + actype + "'").getResultList();
            Vector recLst5 = (Vector) chk8.get(0);
            tmpPenalty = Float.parseFloat(recLst5.get(0).toString());

            if (tmpPenalty == null) {
                tmpPenalty = roi / 12;
            } else {
                tmpPenalty = (roi + tmpPenalty) / 12;
            }
            if (folioNo.equalsIgnoreCase("")) {
                cust_no = accOpenFacade.cbsCustId(actype, orgncode);
            } else {
                cust_no = folioNo;
            }

            cust_no_new1 = cust_no.toString();

            int length = cust_no_new1.length();
            int addedZero = 6 - length;
            for (int i = 1; i <= addedZero; i++) {
                cust_no_new1 = "0" + cust_no_new1;
            }

            Tempacno = orgncode + actype + cust_no_new1 + agcode;

            List acNoExist = em.createNativeQuery("select NEW_AC_NO from cbs_acno_mapping where new_AC_NO = '" + Tempacno + "' ").getResultList();
            if (acNoExist.isEmpty()) {
                Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                        + "values ('" + Tempacno + "','" + Tempacno + "')");
                insertMapping.executeUpdate();
            } else {
                message = "This account is already exist for AcType !!!" + actype;
                return message;
            }

            Query insertQuery = em.createNativeQuery("INSERT INTO customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,occupation,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
                    + " values('" + cust_no_new1 + "',upper('" + actype + "'),'" + title + "','" + custname + "','" + craddress + "','" + praddress + "','" + phoneno + "','" + tempAge + "'," + occupation + ",'" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "','" + TempDate + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')");
//            Query insertQuery = em.createNativeQuery("INSERT INTO customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
//                    + " values('" + cust_no_new1 + "',upper('" + actype + "'),'" + title + "','" + custname + "','" + craddress + "','" + praddress + "','" + phoneno + "','" + tempAge + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "','" + TempDate + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')");
            var1 = insertQuery.executeUpdate();

            List chk9 = em.createNativeQuery("SELECT DATE_FORMAT('" + TempDate + "', '%Y-%m-%d')").getResultList();
            Vector recLst6 = (Vector) chk9.get(0);
            tmpLastDt = recLst6.get(0).toString();

            //  List chk10 = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '"+tmpLastDt+"', INTERVAL 1 YEAR ), '%Y-%m-%d')").getResultList();
            // Vector recLst7 = (Vector) chk10.get(0);
            // nextdate = recLst7.get(0).toString();
            tmpLastDate = TempDate;
            
            Query insertQuery1 = em.createNativeQuery("INSERT INTO accountmaster(acno,openingdt,LastOpDate,introaccno,intdeposit,opermode,JtName1,JtName2,accstatus,orgncode,nomination,ODLimit,minbal,penalty,enteredby,"
                    + " lastupdatedt,accttype,relatioship,JtName3,JtName4,ClosingBal,CustName,chequebook,adhocLimit,adhocInterest,closingdate,optstatus,instruction,custid1,custid2,custid3,custid4,CurBrCode,acctCategory,huf_family)"
                    + " values('" + Tempacno + "','" + ymd.format(new Date()) + "','" + ymd.format(new Date()) + "','" + acnoIntro + "'," + roi + "," + operatingMode + ",'" + JtName1 + "','" + JtName2 + "',1," + occupation + ",'" + nominee + "'," + Odlimit + ",1," + tmpPenalty + ",'" + UserText + "','" + ymd.format(new Date()) + "','" + actype + "'"
                    + " ,'" + nominee_relatioship + "','" + JtName3 + "','" + JtName4 + "',0,'" + custname + "'," + chqOpt + ",0,0,NULL,1,'" + spInst + "','" + jtCustId1 + "','" + jtCustId2 + "','" + jtCustId3 + "','" + jtCustId4 + "','" + orgncode + "','" + actCateg + "','" + hufFamily + "')");
//            Query insertQuery1 = em.createNativeQuery("INSERT INTO accountmaster(acno,openingdt,LastOpDate,introaccno,intdeposit,opermode,JtName1,JtName2,accstatus,nomination,ODLimit,minbal,penalty,enteredby,"
//                    + " lastupdatedt,accttype,relatioship,JtName3,JtName4,ClosingBal,CustName,chequebook,adhocLimit,adhocInterest,closingdate,optstatus,instruction,custid1,custid2,custid3,custid4,CurBrCode,acctCategory,huf_family)"
//                    + " values('" + Tempacno + "','" + TempDate + "','" + tmpLastDate + "','" + acnoIntro + "'," + roi + "," + operatingMode + ",'" + JtName1 + "','" + JtName2 + "',1,'" + nominee  + nominee + "'," + Odlimit + ",1," + tmpPenalty + ",'" + UserText + "','" + TempDate + "','" + actype + "'"
//                    + " ,'" + grd_relation + "','" + JtName3 + "','" + JtName4 + "',0,'" + custname + "'," + tmpCKBook + ",0,0,NULL,1,'" + spInst + "','" + jtCustId1 + "','" + jtCustId2 + "','" + jtCustId3 + "','" + jtCustId4 + "','" + orgncode + "','" + actCateg + "','" + hufFamily + "')");
            var2 = insertQuery1.executeUpdate();

            Query insertQuery2 = em.createNativeQuery("INSERT INTO cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                    + " VALUES ('" + Tempacno + "','" + schemeCode + "','" + intCode + "'," + moritoriumPeriod + "," + acnoPreDr + "," + acnoPreCr + ",'" + rateCode + "','" + disbursmentType + "','" + Tempacno + "','" + calMethod + "','" + calOn + "','" + intAppFrequency + "','" + calLevel + "','" + compoundFrequency + "','" + paggingFrequency + "'," + LoanPeriod1 + "," + LoanPeriod2 + ",'" + acOpDt + "','" + acOpDt + "','" + acOpDt + "')");
            var3 = insertQuery2.executeUpdate();

            List chk10 = em.createNativeQuery(" select Int_Ac_Open_Enable_In_Staff,ifnull(Ac_Type_Open_In_Staff,'') as actypeOpenInstaff from accounttypemaster where acctcode ='" + actype + "'").getResultList();
            Vector recLst10 = (Vector) chk10.get(0);
            intAcOpenEnableInStaff = recLst10.get(0).toString();
            if (!recLst10.get(1).toString().equalsIgnoreCase("")) {
                acTypeOpenInStaff = recLst10.get(1).toString();
            } else {
                acTypeOpenInStaff = "";
            }

            if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                if (!acTypeOpenInStaff.equalsIgnoreCase("")) {
                    if (folioNo.equalsIgnoreCase("")) {
                        cust_no = accOpenFacade.cbsCustId(acTypeOpenInStaff, orgncode);
                    } else {
                        cust_no = folioNo;
                    }

                    String cust_no_new2 = cust_no.toString();

                    length = cust_no_new2.length();
                    addedZero = 6 - length;
                    for (int i = 1; i <= addedZero; i++) {
                        cust_no_new2 = "0" + cust_no_new2;
                    }

                    if (!cust_no_new1.equalsIgnoreCase(cust_no_new2)) {
                        message = "Please verify the account series of !!!" + acTypeOpenInStaff;
                        return message;
                    }

                    Staffacno = orgncode + acTypeOpenInStaff + cust_no_new2 + agcode;

                    acNoExist = em.createNativeQuery("select NEW_AC_NO from cbs_acno_mapping where new_AC_NO = '" + Staffacno + "' ").getResultList();
                    if (acNoExist.isEmpty()) {
                        Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                                + "values ('" + Staffacno + "','" + Staffacno + "')");
                        insertMapping.executeUpdate();
                    } else {
                        message = "This account is already exist for AcType !!!" + actype + " and " + acTypeOpenInStaff;
                        return message;
                    }

                    insertQuery = em.createNativeQuery("INSERT INTO customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,occupation,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
                            + " values('" + cust_no_new2 + "',upper('" + acTypeOpenInStaff + "'),'" + title + "','" + custname + "','" + craddress + "','" + praddress + "','" + phoneno + "','" + tempAge + "'," + occupation + ",'" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "','" + TempDate + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')");
//            Query insertQuery = em.createNativeQuery("INSERT INTO customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
//                    + " values('" + cust_no_new1 + "',upper('" + actype + "'),'" + title + "','" + custname + "','" + craddress + "','" + praddress + "','" + phoneno + "','" + tempAge + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "','" + TempDate + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')");
                    var1 = insertQuery.executeUpdate();

                    chk9 = em.createNativeQuery("SELECT DATE_FORMAT('" + TempDate + "', '%Y-%m-%d')").getResultList();
                    recLst6 = (Vector) chk9.get(0);
                    tmpLastDt = recLst6.get(0).toString();

                    //  List chk10 = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '"+tmpLastDt+"', INTERVAL 1 YEAR ), '%Y-%m-%d')").getResultList();
                    // Vector recLst7 = (Vector) chk10.get(0);
                    // nextdate = recLst7.get(0).toString();
                    tmpLastDate = TempDate;
                    String recoverType = common.getRecoverType(schemeCode);
                    insertQuery1 = em.createNativeQuery("INSERT INTO accountmaster(acno,openingdt,LastOpDate,introaccno,intdeposit,opermode,JtName1,JtName2,accstatus,orgncode,nomination,ODLimit,minbal,penalty,enteredby,"
                            + " lastupdatedt,accttype,relatioship,JtName3,JtName4,ClosingBal,CustName,chequebook,adhocLimit,adhocInterest,closingdate,optstatus,instruction,custid1,custid2,custid3,custid4,CurBrCode,acctCategory,huf_family)"
                            + " values('" + Staffacno + "','" + TempDate + "','" + tmpLastDate + "','" + acnoIntro + "','0'," + operatingMode + ",'" + JtName1 + "','" + JtName2 + "',1," + occupation + ",'" + nominee + "',0,1," + tmpPenalty + ",'" + UserText + "','" + TempDate + "','" + acTypeOpenInStaff + "'"
                            + " ,'" + nominee_relatioship + "','" + JtName3 + "','" + JtName4 + "',0,'" + custname + "'," + chqOpt + ",0,0,NULL,1,'" + spInst + "','" + jtCustId1 + "','" + jtCustId2 + "','" + jtCustId3 + "','" + jtCustId4 + "','" + orgncode + "','" + actCateg + "','" + hufFamily + "')");
//            Query insertQuery1 = em.createNativeQuery("INSERT INTO accountmaster(acno,openingdt,LastOpDate,introaccno,intdeposit,opermode,JtName1,JtName2,accstatus,nomination,ODLimit,minbal,penalty,enteredby,"
//                    + " lastupdatedt,accttype,relatioship,JtName3,JtName4,ClosingBal,CustName,chequebook,adhocLimit,adhocInterest,closingdate,optstatus,instruction,custid1,custid2,custid3,custid4,CurBrCode,acctCategory,huf_family)"
//                    + " values('" + Tempacno + "','" + TempDate + "','" + tmpLastDate + "','" + acnoIntro + "'," + roi + "," + operatingMode + ",'" + JtName1 + "','" + JtName2 + "',1,'" + nominee  + nominee + "'," + Odlimit + ",1," + tmpPenalty + ",'" + UserText + "','" + TempDate + "','" + acTypeOpenInStaff + "'"
//                    + " ,'" + grd_relation + "','" + JtName3 + "','" + JtName4 + "',0,'" + custname + "'," + tmpCKBook + ",0,0,NULL,1,'" + spInst + "','" + jtCustId1 + "','" + jtCustId2 + "','" + jtCustId3 + "','" + jtCustId4 + "','" + orgncode + "','" + actCateg + "','" + hufFamily + "')");
                    var2 = insertQuery1.executeUpdate();

                    Query insertQueryapp = em.createNativeQuery("INSERT INTO loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt, ODLimit,MaxLimit,PresentStatus,EnterBy,ClosingDt,recover) "
                            + " values('" + orgncode + "','" + Staffacno + "','" + custname + "','" + tmpANat + "'," + roi + "," + tmpPenalty + "," + Odlimit + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d')," + Odlimit + "," + Odlimit + ",'OPERATIVE','" + UserText + "',null,'" + recoverType + "')");
                    vari = insertQueryapp.executeUpdate();

                    insertQuery2 = em.createNativeQuery("INSERT INTO cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                            + " VALUES ('" + Staffacno + "','" + schemeCode + "','" + intCode + "'," + moritoriumPeriod + "," + acnoPreDr + "," + acnoPreCr + ",'" + rateCode + "','" + disbursmentType + "','" + Staffacno + "','" + calMethod + "','" + calOn + "','" + intAppFrequency + "','" + calLevel + "','" + compoundFrequency + "','" + paggingFrequency + "'," + LoanPeriod1 + "," + LoanPeriod2 + ",'" + acOpDt + "','" + acOpDt + "','" + acOpDt + "')");
                    var3 = insertQuery2.executeUpdate();

                    Query insertQuerySec = em.createNativeQuery("Update cbs_loan_acc_mast_sec set INT_TRF_ACNO='" + Staffacno + "' where acno='" + Tempacno + "'");
                    var14 = insertQuerySec.executeUpdate();
                }
            }
            /**
             * ADDED BY ROHIT ON 18/03/2011*
             */
            String intPegflag = "";
            if (rateCode.equalsIgnoreCase("Fl")) {
                intPegflag = "N";
            } else {
                intPegflag = "Y";
            }
            List monAddList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + acOpDt + "', INTERVAL " + LoanPeriod1 + " MONTH ), '%Y%m%d')").getResultList();
            Vector monAddListVec = (Vector) monAddList.get(0);
            String tempDt1 = monAddListVec.get(0).toString();

            List dayAddList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + tempDt1 + "', INTERVAL " + LoanPeriod2 + " DAY ), '%Y%m%d')").getResultList();
            Vector dayAddListVec = (Vector) dayAddList.get(0);
            String tempDt2 = dayAddListVec.get(0).toString();

            List lastDayLt = em.createNativeQuery("SELECT DATE_FORMAT(LAST_DAY('" + tempDt2 + "'), '%Y%m%d')").getResultList();
            Vector lastDayLtVec = (Vector) lastDayLt.get(0);
            String toDate = lastDayLtVec.get(0).toString();

            List dateDiffLt = em.createNativeQuery("SELECT TIMESTAMPDIFF (DAY,'" + acOpDt + "','" + toDate + "')").getResultList();
            Vector dateDiffLtVec = (Vector) dateDiffLt.get(0);
            int effNoOfDays = Integer.parseInt(dateDiffLtVec.get(0).toString());

            Query intRateQuery = em.createNativeQuery("insert into cbs_acc_int_rate_details(ACNO,AC_INT_VER_NO,INT_TABLE_CODE,ACC_PREF_CR,MIN_INT_RATE_CR,MAX_INT_RATE_CR,AC_PREF_DR,MIN_INT_RATE_DR, MAX_INT_RATE_DR,INT_PEG_FLG,PEG_FREQ_MON,PEG_FREQ_DAYS,EFF_FRM_DT,EFF_TO_DT,EFF_NO_OF_DAYS,CREATED_BY,CREATION_DT,MOD_CNT)"
                    + " values('" + Tempacno + "',1,'" + intCode + "'," + acnoPreCr + ",0,0," + acnoPreDr + ",0,0,'" + intPegflag + "','" + paggingFrequency + "',0,'" + acOpDt + "','" + toDate + "'," + effNoOfDays + ",'" + UserText + "','" + acOpDt + "',0)");
            intRateQuery.executeUpdate();
            if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                if (!acTypeOpenInStaff.equalsIgnoreCase("")) {
                    intRateQuery = em.createNativeQuery("insert into cbs_acc_int_rate_details(ACNO,AC_INT_VER_NO,INT_TABLE_CODE,ACC_PREF_CR,MIN_INT_RATE_CR,MAX_INT_RATE_CR,AC_PREF_DR,MIN_INT_RATE_DR, MAX_INT_RATE_DR,INT_PEG_FLG,PEG_FREQ_MON,PEG_FREQ_DAYS,EFF_FRM_DT,EFF_TO_DT,EFF_NO_OF_DAYS,CREATED_BY,CREATION_DT,MOD_CNT)"
                            + " values('" + Staffacno + "',1,'" + intCode + "'," + acnoPreCr + ",0,0," + acnoPreDr + ",0,0,'" + intPegflag + "','" + paggingFrequency + "',0,'" + acOpDt + "','" + toDate + "'," + effNoOfDays + ",'" + UserText + "','" + acOpDt + "',0)");
                    intRateQuery.executeUpdate();
                }
            }
            /**
             * END*
             */
//            if (tmpANat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                Query insertQuery3 = em.createNativeQuery("INSERT INTO int_dldetails(acno,closingbalance,cumuactualamt,closingactualamt,todate) values('" + Tempacno + "',0,0,0,'" + tempbd + "')");
//                var4 = insertQuery3.executeUpdate();
//            } else if (tmpANat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
//                Query insertQuery3 = em.createNativeQuery("INSERT INTO int_tldetails(acno,closingbalance,cumuactualamt,closingactualamt,todate) values('" + Tempacno + "',0,0,0,'" + tempbd + "')");
//                var4 = insertQuery3.executeUpdate();
//            }
            if (tmpANat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || tmpANat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                Query insertQuery4 = em.createNativeQuery("INSERT INTO applicantgeneral(Acno,FATHERSNAME,AcOpeningDt,Classification, Sector,ApplicantCategory,LoanDuration,LastUpdate,EnterBy,yojyna,DateOfDocument,"
//                        + " LastBalConfirmLet,DocumentExpDt) "
//                        + " values('" + Tempacno + "','" + fathername + "','" + tmpLastDt + "','','','',0,'" + tempbd + "','" + UserText + "',0,'" + tmpLastDt + "','" + tmpLastDt + "',DATE_ADD('"+tmpLastDt+"', INTERVAL 36 MONTH))");
//                var5 = insertQuery4.executeUpdate();

                Query insertQuery5 = em.createNativeQuery("INSERT INTO apploandetails(Acno,AmtSanctioned,intrate,SanctionDt,LoanDuration,LastUpdate)"
                        + " values('" + Tempacno + "'," + Odlimit + "," + roi + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d'),0,'" + tempbd + "')");
                var6 = insertQuery5.executeUpdate();
                if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                    if (!acTypeOpenInStaff.equalsIgnoreCase("")) {
                        insertQuery5 = em.createNativeQuery("INSERT INTO apploandetails(Acno,AmtSanctioned,intrate,SanctionDt,LoanDuration,LastUpdate)"
                                + " values('" + Staffacno + "'," + Odlimit + "," + roi + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d'),0,'" + tempbd + "')");
                        var6 = insertQuery5.executeUpdate();

                    }
                }
            }

            String recoverType = common.getRecoverType(schemeCode);

            if (tmpANat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                Query insertQuery6 = em.createNativeQuery("INSERT INTO int_caodccdetails values('" + Tempacno + "',0,0,0,0,0,0,0,0,'" + tempbd + "')");
//                var7 = insertQuery6.executeUpdate();
                Query insertQuery7 = em.createNativeQuery("INSERT INTO ca_reconbalan(Acno,Balance,dt) values('" + Tempacno + "',0,'" + tmpLastDt + "')");
                var8 = insertQuery7.executeUpdate();

                List chk12 = em.createNativeQuery("SELECT productcode from accounttypemaster where acctcode='" + actype + "'").getResultList();
                Vector recLst9 = (Vector) chk12.get(0);
                rsKC = recLst9.get(0).toString();
                if (rsKC == null || rsKC.equalsIgnoreCase("")) {
                    Query insertQuery8 = em.createNativeQuery("INSERT INTO loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt,ODLimit,MaxLimit,PresentStatus,EnterBy,ClosingDt,recover) "
                            + " values('" + orgncode + "','" + Tempacno + "','" + custname + "','" + tmpANat + "'," + roi + "," + tmpPenalty + "," + Odlimit + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d')," + Odlimit + "," + Odlimit + ",'OPERATIVE','" + UserText + "',null,'" + recoverType + "')");
                    var9 = insertQuery8.executeUpdate();
                } else {
                    if (rsKC.equalsIgnoreCase("KC") || rsKC.equalsIgnoreCase("DP")) {
                        Query insertQuery9 = em.createNativeQuery("INSERT INTO loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt, ODLimit,MaxLimit,PresentStatus,EnterBy,ClosingDt,SubsidyAmt,recover) "
                                + " values('" + orgncode + "','" + Tempacno + "','" + custname + "','" + tmpANat + "'," + roi + "," + tmpPenalty + "," + Odlimit + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d')," + Odlimit + "," + Odlimit + ",'OPERATIVE','" + UserText + "',null," + subSidyAmt + ",'" + recoverType + "')");
                        var10 = insertQuery9.executeUpdate();
                    } else {
                        Query insertQuery9 = em.createNativeQuery("INSERT INTO loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt, ODLimit,MaxLimit,PresentStatus,EnterBy,ClosingDt,recover) "
                                + " values('" + orgncode + "','" + Tempacno + "','" + custname + "','" + tmpANat + "'," + roi + "," + tmpPenalty + "," + Odlimit + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d')," + Odlimit + "," + Odlimit + ",'OPERATIVE','" + UserText + "',null,'" + recoverType + "')");
                        var10 = insertQuery9.executeUpdate();
                    }
                }
                if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                    if (!acTypeOpenInStaff.equalsIgnoreCase("")) {
                        insertQuery7 = em.createNativeQuery("INSERT INTO ca_reconbalan(Acno,Balance,dt) values('" + Staffacno + "',0,'" + tmpLastDt + "')");
                        var8 = insertQuery7.executeUpdate();
                    }
                }
                if (!actype.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
//                    Query insertQuery10 = em.createNativeQuery("INSERT INTO applicantgeneral(Acno,AcOpeningDt,Classification,Sector,ApplicantCategory,LoanDuration,LastUpdate,EnterBy,yojyna,DateOfDocument,"
//                            + " LastBalConfirmLet,DocumentExpDt)"
//                            + " values('" + Tempacno + "','" + tmpLastDt + "','','','',0,'" + tempbd + "','" + UserText + "',0,'" + tmpLastDt + "','" + tmpLastDt + "', DATE_ADD('"+tmpLastDt+"', INTERVAL 36 MONTH))");
//                    var11 = insertQuery10.executeUpdate();
                }
            } else {
                Query insertQuery6 = em.createNativeQuery("INSERT INTO reconbalan(acno,Balance,dt) values('" + Tempacno + "',0,'" + tmpLastDt + "')");
                var7 = insertQuery6.executeUpdate();
                if (tmpANat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    Query insertQuery7 = em.createNativeQuery("INSERT INTO loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt,ODLimit,MaxLimit,SubsidyAmt,"
                            + " PresentStatus,EnterBy,ClosingDt,product,recover)"
                            + " values('" + orgncode + "','" + Tempacno + "','" + custname + "','" + tmpANat + "'," + roi + "," + tmpPenalty + "," + Odlimit + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d')," + Odlimit + "," + Odlimit + "," + subSidyAmt + ",'OPERATIVE','" + UserText + "',null,'" + IntOpt + "','" + recoverType + "')");
                    var8 = insertQuery7.executeUpdate();
                } else {
                    Query insertQuery7 = em.createNativeQuery("INSERT INTO loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt,ODLimit,MaxLimit,PresentStatus,EnterBy,closingDt,product,recover)"
                            + " values('" + orgncode + "','" + Tempacno + "','" + custname + "','" + tmpANat + "'," + roi + "," + tmpPenalty + "," + Odlimit + ",DATE_FORMAT('" + sancDt + "', '%Y-%m-%d')," + Odlimit + "," + Odlimit + ",'OPERATIVE','" + UserText + "',null,'" + IntOpt + "','" + recoverType + "')");
                    var8 = insertQuery7.executeUpdate();
                }
                if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                    if (!acTypeOpenInStaff.equalsIgnoreCase("")) {
                        insertQuery6 = em.createNativeQuery("INSERT INTO reconbalan(acno,Balance,dt) values('" + Staffacno + "',0,'" + tmpLastDt + "')");
                        var7 = insertQuery6.executeUpdate();
                    }
                }
            }
            Query insertQuery11 = em.createNativeQuery("INSERT into documentsreceived (acno,groupdocu,docuno,docudetails,receivedDate)"
                    + " values('" + Tempacno + "',14," + docuno + ",'" + docudetails + "','" + TempDate + "')");
            var12 = insertQuery11.executeUpdate();
            /**
             * *** changed By Shipra *****
             */
            Query insertQuery12 = em.createNativeQuery("INSERT INTO customerid(custid,acno,enterby,txnbrn) values('" + cust_id + "','" + Tempacno + "','" + UserText + "','" + alphacode + "')");
            var13 = insertQuery12.executeUpdate();
            if (appTp.equalsIgnoreCase("true")) {
                Query insertQuery13 = em.createNativeQuery("Update loan_receipt set AcnoAssigned='" + Tempacno + "' where loantype='" + actype + "' and appno= '" + appSeq + "' and fyear = '" + FYear + "'");
                var14 = insertQuery13.executeUpdate();
            }
            if (intAcOpenEnableInStaff.equalsIgnoreCase("Y")) {
                if (!acTypeOpenInStaff.equalsIgnoreCase("")) {
                    insertQuery11 = em.createNativeQuery("INSERT into documentsreceived (acno,groupdocu,docuno,docudetails,receivedDate)"
                            + " values('" + Staffacno + "',14," + docuno + ",'" + docudetails + "','" + TempDate + "')");
                    var12 = insertQuery11.executeUpdate();

                    insertQuery12 = em.createNativeQuery("INSERT INTO customerid(custid,acno,enterby,txnbrn) values('" + cust_id + "','" + Staffacno + "','" + UserText + "','" + alphacode + "')");
                    var13 = insertQuery12.executeUpdate();
                    if (appTp.equalsIgnoreCase("true")) {
                        Query insertQuery13 = em.createNativeQuery("Update loan_receipt set AcnoAssigned='" + Staffacno + "' where loantype='" + acTypeOpenInStaff + "' and appno= '" + appSeq + "' and fyear = '" + FYear + "'");
                        var14 = insertQuery13.executeUpdate();
                    }
                }
            }
            /**
             * **** Ended *****
             */
            //Todo************************************************************?
//            message = saveLoanMISDetail(Tempacno);
//            if (!message.equalsIgnoreCase("true")) {
//                ut.rollback();
//                return message;
//            }
            if ((nominee == null) || (nominee.equalsIgnoreCase("")) || (nominee.length() == 0)) {
            } else {
                Matcher nomineeCheck = p.matcher(nominee);
                if (!nomineeCheck.matches()) {
//                    ut.rollback();
                    message = "NOMINEE NAME SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                    return message;
                }
                Matcher nomineeRelatioshipCheck = p.matcher(nominee_relatioship);
                if (!nomineeRelatioshipCheck.matches()) {
//                    ut.rollback();
                    message = "NOMINEE RELATIONSHIP SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                    return message;
                }
                if (nomineeAdd != null && !validateString(nomineeAdd)) {
//                    ut.rollback();
                    message = "NOMINEE ADDRESS SHOULD NOT CONTAIN SPECIAL CHARACTERS !!!";
                    return message;
                }
                List chk15 = em.createNativeQuery("SELECT TIMESTAMPDIFF (DAY,curdate(), DATE_FORMAT('" + nomineeDob + "', '%Y-%m-%d'))").getResultList();
                Vector recLst11 = (Vector) chk15.get(0);
                if (Integer.parseInt(recLst11.get(0).toString()) > 0) {
//                    ut.rollback();
                    message = "NOMINEE DATE OF BIRTH CANNOT BE GREATE THAN CURRENT DATE !!!";
                    return message;
                }
                String minorFlag = null;
                Integer nomAge;
                List chk16 = em.createNativeQuery("SELECT TIMESTAMPDIFF(year,DATE_FORMAT('" + nomineeDob + "', '%Y-%m-%d'),curdate())").getResultList();
                Vector recLst12 = (Vector) chk16.get(0);
                nomAge = Integer.parseInt(recLst12.get(0).toString());
                if (nomAge >= 18) {
                    minorFlag = "N";
                } else {
                    minorFlag = "Y";
                }
                Query insertQuery14 = em.createNativeQuery("INSERT INTO nom_details (acno,nomname,nomadd,relation,minior,nomdob,nomage,enterby,authby,"
                        + "trantime,nom_reg_no) VALUES('" + Tempacno + "','" + nominee + "','" + nomineeAdd + "','" + nominee_relatioship + "','" 
                        + minorFlag + "','" + nomineeDob + "'," + nomAge + ",'" + UserText + "','" + UserText + "',now(),0)");
                var15 = insertQuery14.executeUpdate();
            }

            String acno = "";
            String acSeq = common.getAccseq();
            if (!acSeq.equalsIgnoreCase("M")) {
                if (schFlg.equalsIgnoreCase("Y")) {

                    List branchmasterList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + orgncode + "'").getResultList();
                    if (branchmasterList.size() > 0) {
                        Vector descVect = (Vector) branchmasterList.get(0);
                        alphacode = descVect.get(0).toString();
                    }

                    String custId = accOpenFacade.cbsCustId(stOpt, orgncode);
                    if (custId.length() == 1) {
                        custId = "00000" + custId;
                    }
                    if (custId.length() == 2) {
                        custId = "0000" + custId;
                    }
                    if (custId.length() == 3) {
                        custId = "000" + custId;
                    }
                    if (custId.length() == 4) {
                        custId = "00" + custId;
                    }
                    if (custId.length() == 5) {
                        custId = "0" + custId;
                    }

                    acno = orgncode + stOpt + custId + agcode;

                    Query insertMapping1 = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                            + "values ('" + acno + "','" + acno + "')");
                    insertMapping1.executeUpdate();

                    Integer customerMaster = em.createNativeQuery("insert into td_customermaster(custno,actype,title,custname,"
                            + "craddress,praddress,phoneno,dob,occupation,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,"
                            + "fathername,remarks,brncode)values('" + custId + "','" + stOpt + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
                            + "'" + phoneno + "','" + dob + "','" + occupation + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
                            + "'" + TempDate + "','" + agcode + "','" + UserText + "','" + fathername + "','',"
                            + "'" + orgncode + "')").executeUpdate();
//                    Integer customerMaster = em.createNativeQuery("insert into td_customermaster(custno,actype,title,custname,"
//                            + "craddress,praddress,phoneno,dob,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,"
//                            + "fathername,remarks,brncode)values('" + custId + "','" + stOpt + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
//                            + "'" + phoneno + "','" + dob + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
//                            + "'" + TempDate + "','" + agcode + "','" + UserText + "','" + fathername + "','',"
//                            + "'" + orgncode + "')").executeUpdate();
                    if (customerMaster < 0) {
                        //                    ut.rollback();
                        return "Data is not inserted into Customer Master";
                    }

                    Integer accountMaster = em.createNativeQuery("insert td_accountmaster(acno,openingdt,introaccno,opermode,JtName1,"
                            + "JtName2,Relationship,remarks,accstatus,orgncode,nomination,enteredby,lastupdatedt,accttype,JtName3,JtName4,"
                            + "tdsDetails,tdsFlag,cust_Type,custName,closingdate,custid1,custid2,custid3,custid4,CurBrCode,acctCategory)values('" + acno + "','" + TempDate + "','" + Tempacno + "',"
                            + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "','" + nominee_relatioship + "','',1,"
                            + "'" + occupation + "','" + nominee + "','" + UserText + "','" + TempDate + "','" + stOpt + "' ,'" + JtName3 + "',"
                            + "'" + JtName4 + "','','N','OT','" + custname + "',null,"
                            + "'" + jtCustId1 + "','" + jtCustId2 + "','" + jtCustId3 + "','" + jtCustId4 + "','" + orgncode + "','" + actCateg + "') ").executeUpdate();
//                    Integer accountMaster = em.createNativeQuery("insert td_accountmaster(acno,openingdt,introaccno,opermode,JtName1,"
//                            + "JtName2,Relationship,remarks,accstatus,,nomination,enteredby,lastupdatedt,accttype,JtName3,JtName4,"
//                            + "tdsDetails,tdsFlag,cust_Type,custName,closingdate,custid1,custid2,custid3,custid4,CurBrCode,acctCategory)values('" + acno + "','" + TempDate + "','" + Tempacno + "',"
//                            + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "','" + nominee_relatioship + "','',1"
//                            + "','" + nominee + "','" + UserText + "','" + TempDate + "','" + stOpt + "' ,'" + JtName3 + "',"
//                            + "'" + JtName4 + "','','N','OT','" + custname + "',null,"
//                            + "'" + jtCustId1 + "','" + jtCustId2 + "','" + jtCustId3 + "','" + jtCustId4 + "','" + orgncode + "','" + actCateg + "') ").executeUpdate();
                    if (accountMaster < 0) {
                        //                    ut.rollback();
                        return "Data is not inserted into Account Master";
                    }

                    Integer reconbalan = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',0,'" + TempDate + "')").executeUpdate();
                    if (reconbalan < 0) {
                        //                    ut.rollback();
                        return "Data is not inserted into Reconbalan";
                    }

                    Integer documentsReceived = em.createNativeQuery("insert documentsreceived (acno,groupdocu,docuno,docudetails,receiveddate) values('" + acno + "',14," + docuno + ",'" + docudetails + "','" + TempDate + "')").executeUpdate();
                    if (documentsReceived < 0) {
                        //                    ut.rollback();
                        return "Data is not inserted into Documents Received";
                    }

                    Integer customer = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn) values"
                            + "(" + cust_id + ",'" + acno + "','" + UserText + "','" + alphacode + "')").executeUpdate();
                    if (customer < 0) {
                        //                    ut.rollback();
                        return "Data is not inserted into Customer Id ";
                    }
                }
            }

            if ((var1 > 0) && (var2 > 0) && (var3 > 0) && (var13 > 0) && (var12 > 0)) {
//                ut.commit();
                if (schFlg.equalsIgnoreCase("Y")) {
                    if (!(Staffacno == null || Staffacno.equalsIgnoreCase(""))) {
                        message = "Loan Account No." + Tempacno + "," + Staffacno + " AND Thrift Account No." + acno + " FOR CUST ID " + cust_id;
                    } else {
                        message = "Loan Account No." + Tempacno + " AND Thrift Account No." + acno + " FOR CUST ID " + cust_id;
                    }
                } else {
                    if (!(Staffacno == null || Staffacno.equalsIgnoreCase(""))) {
                        message = Tempacno + "," + Staffacno + " FOR CUST ID " + cust_id;
                    } else {
                        message = Tempacno + " FOR CUST ID " + cust_id;
                    }
                }
                return message;
            } else {
//                ut.rollback();
                message = "DATA COULD NOT BE SAVED !!!";
                return message;
            }
        } catch (Exception ex) {
//            try {
////                ut.rollback();
//                return ex.getMessage();
//            } catch (SystemException syex) {
//                throw new ApplicationException(ex);
//            }
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    private boolean validateString(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '#' || value.charAt(i) == '~') {
                return false;
            }
        }
        return true;
    }

    public String saveDlAcctOpenRegisterWithOutTranMgmt(String actype, String Occupation, String OpMode, String orgncode, String agcode,
            Float Sanclimit, Float ROI, String Title, String custname, String CrAddress, String PrAddress, String phone,
            String enterby, String pan, String FrName, String Age, String JtName1, String JtName2, String IntAcct, String catvalue,
            String CustIDExist, String schemeCode, String intTableCode, Integer moratoriunPd, Float accPrefDr,
            Float accPrefCr, String rateCode, String disbType, String calcMethod, String calcOnInt, String calLevel,
            String compFreq, Integer peggFreq, String intAppFreq, Integer loanPdMonth,
            Integer loanPdDay, List table, String dob, String custId1, String custId2, String actCateg, String acOpenFromFlag, String folioNo, String hufFamily) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
        List resultList = null;
        Float tmppenalty = null;
        Float penalty;
        Integer code1;
        String custn;
        String custno = null;
        // Integer opercode;
        String Tempacno;
        Integer AutoSno = null;
        Integer max;
        String tempbd = "";
        String TempIntAc;
        String BrAlphaCode = null;
        String TmpAcNat;
        Integer custid;
        Integer OccupCode = null;
        String bd = null;
        String message = "";
        if (JtName1 == null) {
            JtName1 = "";
        }
        if (JtName2 == null) {
            JtName2 = "";
        }
        try {
//            ut.begin();

            List custList = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + CustIDExist + "'").getResultList();
            Vector cuLst = (Vector) custList.get(0);
            String cu_id = cuLst.get(0).toString() != null ? cuLst.get(0).toString() : "";
            if ((cu_id.equalsIgnoreCase("N")) || (cu_id == null) || (cu_id.equalsIgnoreCase(""))) {
//                ut.rollback();
                return "Customer Verification is not completed.";
            }

            String cFlag = accOpenFacade.custMergedFlag(CustIDExist);
            if (cFlag.equalsIgnoreCase("false")) {
//                ut.rollback();
                return "Customer Id Is Merged.";
            }

            if (!((custId1 == null) || (custId1.equalsIgnoreCase("")))) {
                String cFlag1 = accOpenFacade.custMergedFlag(custId1);
                if (cFlag1.equalsIgnoreCase("false")) {
//                    ut.rollback();
                    return "Joint Holder1 Customer Id Is Merged.";
                }

                List custList1 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId1 + "'").getResultList();
                Vector cuLst1 = (Vector) custList1.get(0);
                String cu_id1 = cuLst1.get(0).toString() != null ? cuLst1.get(0).toString() : "";
                if ((cu_id1.equalsIgnoreCase("N")) || (cu_id1 == null) || (cu_id1.equalsIgnoreCase(""))) {
//                    ut.rollback();
                    return "Joint Holder1 Customer id " + custId1 + " Verification is not completed.";
                }
            }

            if (!((custId2 == null) || (custId2.equalsIgnoreCase("")))) {
                String cFlag2 = accOpenFacade.custMergedFlag(custId2);
                if (cFlag2.equalsIgnoreCase("false")) {
//                    ut.rollback();
                    return "Joint Holder2 Customer Id Is Merged.";
                }

                List custList2 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId2 + "'").getResultList();
                Vector cuLst2 = (Vector) custList2.get(0);
                String cu_id2 = cuLst2.get(0).toString() != null ? cuLst2.get(0).toString() : "";
                if ((cu_id2.equalsIgnoreCase("N")) || (cu_id2 == null) || (cu_id2.equalsIgnoreCase(""))) {
//                    ut.rollback();
                    return "Joint Holder2 Customer id " + custId2 + " Verification is not completed.";
                }
            }

            List tempbdList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + orgncode + "'").getResultList();
            if (!tempbdList.isEmpty()) {
                Vector ele = (Vector) tempbdList.get(0);
                tempbd = ele.get(0).toString();
            }
            List bdList = em.createNativeQuery("SELECT DATE_FORMAT('" + tempbd + "', '%Y-%m-%d')").getResultList();
            if (!bdList.isEmpty()) {
                Vector ele = (Vector) bdList.get(0);
                bd = ele.get(0).toString();
            }

            List accountTypeMasterList = em.createNativeQuery("SELECT Penalty FROM accounttypemaster WHERE acctcode='" + actype + "'").getResultList();
            if (!accountTypeMasterList.isEmpty()) {
                Vector ele = (Vector) accountTypeMasterList.get(0);
                penalty = Float.parseFloat(ele.get(0).toString());
                if (penalty == 0) {
                    tmppenalty = (float) 0;
                } else {
                    tmppenalty = penalty;
                }
                tmppenalty = ROI / 12;
            }

            OccupCode = Integer.parseInt(Occupation);
            if (folioNo.equalsIgnoreCase("")) {
                custn = accOpenFacade.cbsCustId(actype, orgncode);
            } else {
                custn = folioNo;
            }

            if (custn.toString().length() == 1) {
                custno = "00000" + custn;
            }
            if (custn.toString().length() == 2) {
                custno = "0000" + custn;
            }
            if (custn.toString().length() == 3) {
                custno = "000" + custn;
            }
            if (custn.toString().length() == 4) {
                custno = "00" + custn;
            }
            if (custn.toString().length() == 5) {
                custno = "0" + custn;
            }
            if (custn.toString().length() == 6) {
                custno = custn;
            }
            Integer customerMaster = em.createNativeQuery("Insert into customermaster(custno,actype,title,custname,craddress,"
                    + "praddress,phoneno,Occupation,panNo,lastupdatedt,agcode,EnteredBy,fatherName,DOB,brncode) Values('" + custno + "',"
                    + "'" + actype + "','" + Title + "','" + custname + "','" + CrAddress + "','" + PrAddress + "','" + phone + "'," + OccupCode + ","
                    + "'" + pan + "','" + tempbd + "','01','" + enterby + "','" + FrName + "','" + dob + "','" + orgncode + "')").executeUpdate();
//            System.out.println("Insert into customermaster(custno,actype,title,custname,craddress,"
//                    + "praddress,phoneno,panNo,lastupdatedt,agcode,EnteredBy,fatherName,DOB,brncode) Values('" + custno + "',"
//                    + "'" + actype + "','" + Title + "','" + custname + "','" + CrAddress + "','" + PrAddress + "','" + phone + "',"
//                    + "'" + pan + "','" + tempbd + "','01','" + enterby + "','" + FrName + "','" + dob + "','" + orgncode + "')");
//            Integer customerMaster = em.createNativeQuery("Insert into customermaster(custno,actype,title,custname,craddress,"
//                    + "praddress,phoneno,panNo,lastupdatedt,agcode,EnteredBy,fatherName,DOB,brncode) Values('" + custno + "',"
//                    + "'" + actype + "','" + Title + "','" + custname + "','" + CrAddress + "','" + PrAddress + "','" + phone + "',"
//                    + "'" + pan + "','" + tempbd + "','01','" + enterby + "','" + FrName + "','" + dob + "','" + orgncode + "')").executeUpdate();

            if (customerMaster < 0) {
//                ut.rollback();
                message = "Data is not inserted into Customer Master";
                resultList.add(message);
                return "false" + ":" + message;
            }
            Tempacno = orgncode + actype.toUpperCase() + custno + agcode;

            if (IntAcct != null) {
                TempIntAc = IntAcct.substring(0, 12);
            } else {
                TempIntAc = "";
            }

            List acNoExist = em.createNativeQuery("select NEW_AC_NO from cbs_acno_mapping where new_AC_NO = '" + Tempacno + "' ").getResultList();
            if (acNoExist.isEmpty()) {
                Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                        + "values ('" + Tempacno + "','" + Tempacno + "')");
                insertMapping.executeUpdate();
            } else {
                message = "This account is already exist for AcType !!!" + actype;
                return message;
            }

            Integer loanAccMst = em.createNativeQuery("insert into cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,"
                    + "INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,"
                    + "CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                    + "values('" + Tempacno + "','" + schemeCode + "','" + intTableCode + "'," + moratoriunPd + "," + accPrefDr + ","
                    + "" + accPrefCr + ",'" + rateCode + "','" + disbType + "','" + Tempacno + "','" + calcMethod + "','" + calcOnInt + "',"
                    + "'" + intAppFreq + "','" + calLevel + "','" + compFreq + "'," + peggFreq + "," + loanPdMonth + "," + loanPdDay + ",'" + tempbd + "','" + tempbd + "','" + tempbd + "')").executeUpdate();
            if (loanAccMst <= 0) {
//                ut.rollback();
                message = "Data is not inserted into cbs_loan_acc_mast_sec";
                resultList.add(message);
                return "false" + ":" + message;
            }

            /**
             * ADDED BY ROHIT ON 18/03/2011*
             */
            String intPegflag = "";

            if (rateCode.equalsIgnoreCase("Fl")) {
                intPegflag = "N";
            } else {
                intPegflag = "Y";
            }

            List monAddList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + tempbd + "', INTERVAL " + loanPdMonth + " MONTH ), '%Y%m%d')").getResultList();
            Vector monAddListVec = (Vector) monAddList.get(0);
            String tempDt1 = monAddListVec.get(0).toString();

            List dayAddList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + tempDt1 + "', INTERVAL " + loanPdDay + " DAY ), '%Y%m%d')").getResultList();
            Vector dayAddListVec = (Vector) dayAddList.get(0);
            String tempDt2 = dayAddListVec.get(0).toString();

            List lastDayLt = em.createNativeQuery("SELECT DATE_FORMAT(LAST_DAY('" + tempDt2 + "'), '%Y%m%d')").getResultList();
            Vector lastDayLtVec = (Vector) lastDayLt.get(0);
            String toDate = lastDayLtVec.get(0).toString();

            List dateDiffLt = em.createNativeQuery("SELECT TIMESTAMPDIFF(day,'" + tempbd + "','" + toDate + "')").getResultList();
            Vector dateDiffLtVec = (Vector) dateDiffLt.get(0);
            int effNoOfDays = Integer.parseInt(dateDiffLtVec.get(0).toString());

            Query intRateQuery = em.createNativeQuery("insert into cbs_acc_int_rate_details(ACNO,AC_INT_VER_NO,INT_TABLE_CODE,ACC_PREF_CR,MIN_INT_RATE_CR,MAX_INT_RATE_CR,AC_PREF_DR,MIN_INT_RATE_DR, MAX_INT_RATE_DR,INT_PEG_FLG,PEG_FREQ_MON,PEG_FREQ_DAYS,EFF_FRM_DT,EFF_TO_DT,EFF_NO_OF_DAYS,CREATED_BY,CREATION_DT,MOD_CNT)"
                    + " values('" + Tempacno + "',1,'" + intTableCode + "'," + accPrefCr + ",0,0," + accPrefDr + ",0,0,'" + intPegflag + "'," + peggFreq + ",0,'" + tempbd + "','" + toDate + "'," + effNoOfDays + ",'" + enterby + "','" + tempbd + "',0)");
            intRateQuery.executeUpdate();

            /**
             * END*
             */
            Integer account = em.createNativeQuery("Insert into accountmaster(acno,openingdt,LastOpDate,introAccno,"
                    + "intDeposit,operMode,JtName1,JtName2,accStatus,orgnCode,ODLimit,minBal,Penalty,enteredby,lastupdatedt,"
                    + "accttype,ClosingBal,CustName,closingdate,optstatus,custid1,custid2,CurBrCode,acctCategory,huf_family)Values('" + Tempacno + "','" + tempbd + "','" + tempbd + "',"
                    + "'" + TempIntAc + "'," + ROI + "," + OpMode + ",'" + JtName1 + "','" + JtName2 + "', 1," + OccupCode + "," + Sanclimit + ""
                    + ",1," + tmppenalty + ",'" + enterby + "','" + tempbd + "','" + actype + "',0,'" + custname + "',NULL,1,'" + custId1 + "','" + custId2 + "','" + orgncode + "','" + actCateg + "','" + hufFamily + "')").executeUpdate();
//            Integer account = em.createNativeQuery("Insert into accountmaster(acno,openingdt,LastOpDate,introAccno,"
//                    + "intDeposit,operMode,JtName1,JtName2,accStatus,ODLimit,minBal,Penalty,enteredby,lastupdatedt,"
//                    + "accttype,ClosingBal,CustName,closingdate,optstatus,custid1,custid2,CurBrCode,acctCategory,huf_family)Values('" + Tempacno + "','" + tempbd + "','" + tempbd + "',"
//                    + "'" + TempIntAc + "'," + ROI + "," + OpMode + ",'" + JtName1 + "','" + JtName2 + "', 1," + Sanclimit + ""
//                    + ",1," + tmppenalty + ",'" + enterby + "','" + tempbd + "','" + actype + "',0,'" + custname + "',NULL,1,'" + custId1 + "','" + custId2 + "','" + orgncode + "','" + actCateg + "','" + hufFamily + "')").executeUpdate();

            if (account <= 0) {
//                ut.rollback();
                message = "Data is not inserted into accountmaster";
                resultList.add(message);
                return "false" + ":" + message;
            }

            Integer appLoanDetails = em.createNativeQuery("Insert apploandetails(acno,AmtSanctioned,intrate,SanctionDt,"
                    + "LoanDuration,LastUpdate) values('" + Tempacno + "'," + Sanclimit + "," + ROI + ",'" + bd + "',0,'" + bd + "')").executeUpdate();
            if (appLoanDetails <= 0) {
//                ut.rollback();
                message = "Data is not inserted into apploandetails";
                return "false" + ":" + message;
            }
            Integer ReconBalan = em.createNativeQuery("Insert reconbalan(acno,Balance,dt) values('" + Tempacno + "',0,'" + bd + "')").executeUpdate();
            if (ReconBalan <= 0) {
//                ut.rollback();
                message = "Data is not inserted into reconbalan";
                return "false" + ":" + message;
            }

//            List acctNatureList = fnAcnat(actype);
//            Vector acctNature = (Vector) acctNatureList.get(0);
            TmpAcNat = getAcNatureByCode(actype);//acctNature.get(0).toString();
            String recoverType = common.getRecoverType(schemeCode);
            Integer LoanAppParameter = em.createNativeQuery("Insert loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,"
                    + "PenalROI,Sanctionlimit, ODLimit,MaxLimit,PresentStatus,EnterBy,closingDt,sanctionLimitDt,recover)Values"
                    + "('" + orgncode + "','" + Tempacno + "','" + custname + "','" + TmpAcNat + "'," + ROI + ","
                    + "" + tmppenalty + "," + Sanclimit + "," + Sanclimit + "," + Sanclimit + ",'OPERATIVE','" + enterby + "',null,'" + bd + "','" + recoverType + "')").executeUpdate();
            if (LoanAppParameter <= 0) {
//                ut.rollback();
                message = "Data is not inserted into Loan AppParameter";
                return "false" + ":" + message;
            }

            List loanSecurityList = em.createNativeQuery("Select ifnull(MAX(SNO),0) From loansecurity where acno='" + Tempacno + "'").getResultList();
            if (!loanSecurityList.isEmpty()) {
                Vector ele = (Vector) loanSecurityList.get(0);
                max = Integer.parseInt(ele.get(0).toString());
                if (max == 0) {
                    AutoSno = 0;
                } else {
                    AutoSno = max;
                }
            }

            List alphaCodeList = em.createNativeQuery("select  alphaCode from branchmaster where BrnCode = '" + orgncode + "'").getResultList();
            if (!alphaCodeList.isEmpty()) {
                Vector ele = (Vector) alphaCodeList.get(0);
                BrAlphaCode = ele.get(0).toString();
            }
            Integer customerid = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn) "
                    + "values( '" + CustIDExist + "', '" + Tempacno + "', '" + enterby + "', '" + BrAlphaCode + "')").executeUpdate();
            if (customerid <= 0) {
//                ut.rollback();
                message = "Data is not inserted into CustomerId";
                return "false" + ":" + message;
            }
            custid = Integer.parseInt(CustIDExist);
            String odAllowedWithSlab = "N";
            List odAllowedWithSlabList = em.createNativeQuery("select  ifnull(TURN_OVER_DETAIL_FLAG,'N') "
                    + " from cbs_scheme_general_scheme_parameter_master "
                    + " where SCHEME_CODE='" + schemeCode + "'").getResultList();
            if (!odAllowedWithSlabList.isEmpty()) {
                Vector ele = (Vector) odAllowedWithSlabList.get(0);
                odAllowedWithSlab = ele.get(0).toString();
            }
            if (!(acOpenFromFlag.equalsIgnoreCase("MEMBERSHIP")) && (odAllowedWithSlab.equalsIgnoreCase("N"))) { //This change is due to ARMY/RBI Account Opening at the time of Membership accunt opeing
                message = saveDlLoanSecurity(table, Tempacno, orgncode, agcode);
                if (!message.equalsIgnoreCase("true")) {
//                    ut.rollback();
                    return message;
                }
            } else {
                if (message.equalsIgnoreCase("")) {
                    message = "true";
                }
            }
            //ToDo
//            message = saveLoanMISDetail(Tempacno);
//            if (!message.equalsIgnoreCase("true")) {
//                ut.rollback();
//                return message;
//            }
//            ut.commit();
            message = message + ":" + Tempacno + ":" + custid;
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveDlLoanSecurity(List table, String Tempacno, String orgncode, String agcode) throws ApplicationException {
        // String tmpTDAcno;
        Integer max = null;
        String Tempbd = null;
        String Acnat22 = null;
        Float spno;
        Float tmpTdVouch = null;
        String tmpRemarks = null;
        String bd = null;
        String tablematdt = null;
        Integer AutoSno;
        String msg = null;
        String acnoBrn = "";
        List<DlAcctOpenReg> dlTable = table;
        try {
            for (int i = 0; i < dlTable.size(); i++) {
                Float Tablelien = Float.parseFloat(dlTable.get(i).getLien());
                String tableEnterBy = dlTable.get(i).getEnterBy();
                String tableacno = dlTable.get(i).getAcno();
                String tablesecNature = dlTable.get(i).getSecNature();
                String tableDetail = dlTable.get(i).getDetails();
                String tablesno = dlTable.get(i).getSno();
                String tableSecDesc1 = dlTable.get(i).getSecDes1();
                String tableSecDesc2 = dlTable.get(i).getSecDesc2();
                String tableSecDesc3 = dlTable.get(i).getSecDesc3();
                String tableIssueDate = dlTable.get(i).getIssueDate();
                String tableParticiular = dlTable.get(i).getParticular();
                String tableMatVal = dlTable.get(i).getMatValue();
                String tablematdate = dlTable.get(i).getMatDate();
                String tableMargin = dlTable.get(i).getMargin();
                String tableVchNo = dlTable.get(i).getVoucherNo();
                if (tableSecDesc3 == null) {
                    tableSecDesc3 = "";
                }

                String tableSecType = dlTable.get(i).getTypeOfSec();
                acnoBrn = Tempacno.substring(0, 2);//facadeRemote.getCurrentBrnCode(Tempacno);
                List bankDayList = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG='N' AND brncode = '" + acnoBrn + "'").getResultList();
                if (bankDayList.size() > 0) {
                    Vector bankDayVect = (Vector) bankDayList.get(0);
                    Tempbd = bankDayVect.get(0).toString();

                    List convertList = em.createNativeQuery("SELECT DATE_FORMAT('" + Tempbd + "', '%Y-%m-%d')").getResultList();
                    if (convertList.size() > 0) {
                        Vector DayVect = (Vector) convertList.get(0);
                        bd = DayVect.get(0).toString();
                    }
                    List convertDateList = em.createNativeQuery("SELECT DATE_FORMAT(date_format(STR_TO_DATE('" + tablematdate + "', '%d/%m/%Y'),'%Y%m%d'), '%Y-%m-%d')").getResultList();
                    if (convertDateList.size() > 0) {
                        Vector convertDateVect = (Vector) convertDateList.get(0);
                        tablematdt = convertDateVect.get(0).toString();
                    }
                    List tableIssueDtList = em.createNativeQuery("SELECT DATE_FORMAT(date_format(STR_TO_DATE('" + tableIssueDate + "', '%d/%m/%Y'),'%Y%m%d'), '%Y-%m-%d')").getResultList();
                    if (tableIssueDtList.size() > 0) {
                        Vector tableIssueDtVect = (Vector) tableIssueDtList.get(0);
                        tableIssueDate = tableIssueDtVect.get(0).toString();
                    }
                    acnoBrn = Tempacno.substring(0, 2);//facadeRemote.getCurrentBrnCode(Tempacno);
                    List loanSecurityList = em.createNativeQuery("Select ifnull(MAX(SNO),0) From loansecurity where acno='" + Tempacno + "'").getResultList();
                    if (loanSecurityList.size() > 0) {
                        Vector loanSecurityVect = (Vector) loanSecurityList.get(0);
                        max = Integer.parseInt(loanSecurityVect.get(0).toString());
                    }
                    if (max == 0) {
                        AutoSno = 0;
                    } else {
                        AutoSno = max;
                    }

                    if (tablesno != null || !tablesno.equalsIgnoreCase("") || tablesno.length() != 0) {
                        AutoSno = AutoSno + 1;
                        Integer LoanSecurity = em.createNativeQuery("Insert into loansecurity(acno,sno,particulars,MatValue,"
                                + "lienValue,matdate,Status,IssueDate,Remarks,Enteredby,EntryDate,security,SecurityOption,"
                                + "SecurityChg,LIENACNO,Margin,securitysub,securitytype,auth)values('" + Tempacno + "'," + AutoSno + ",'" + tableParticiular + "',"
                                + "" + tableMatVal + "," + Tablelien + ",'" + tablematdt + "','ACTIVE','" + tableIssueDate + "','" + tableDetail + "',"
                                + "'" + tableEnterBy + "','" + bd + "',substring('" + tablesecNature + "', 1, 1),'" + tableSecDesc2 + "','" + tableSecDesc1 + "',"
                                + "'" + tableacno + "'," + tableMargin + ",'" + tableSecDesc3 + "','" + tableSecType + "','N')").executeUpdate();
                        if (LoanSecurity < 0) {
                            return "false:Data is not inserted into loansecurity";
                        }

                        List acnatureList = accOpenFacade.fnAcnat(tableParticiular);
                        if (acnatureList.size() > 0) {
                            Vector acnatureVect = (Vector) acnatureList.get(0);
                            Acnat22 = acnatureVect.get(0).toString();
                        } else {
                            Acnat22 = "";
                        }
                        if (Acnat22.equalsIgnoreCase(CbsConstant.RECURRING_AC) || Acnat22.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            //String tmpRdAcno = tableacno.substring(0, 12);
                            tmpRemarks = "Lien Marked For: " + Tempacno;
                            List AccountStatusList = em.createNativeQuery("Select ifnull(max(spNo),0) + 1 From accountstatus").getResultList();
                            if (AccountStatusList.size() > 0) {
                                Vector AccountStatusVect = (Vector) AccountStatusList.get(0);
                                spno = Float.parseFloat(AccountStatusVect.get(0).toString());
                                if (spno == 0) {
                                    tmpTdVouch = (float) 1;
                                } else {
                                    tmpTdVouch = spno;
                                }
                            } else {
                                tmpTdVouch = (float) 1;
                            }
                            Integer accountStatus = em.createNativeQuery("Insert Into accountstatus(Acno,Remark,spFlag,dt,Amount,EnterBy,Auth)"
                                    + "Values('" + tableacno + "','" + tmpRemarks + "',10,'" + bd + "'," + Tablelien + ",'" + tableEnterBy + "','Y')").executeUpdate();
                            if (accountStatus < 0) {
                                return "false:Data is not inserted into accountstatus";
                            }
                            Integer accountStatusUpdate = em.createNativeQuery("update accountmaster Set accstatus = 10 Where acno = '" + tableacno + "'").executeUpdate();
                            if (accountStatusUpdate < 0) {
                                return "false:Data is not Updated into accountstatus";
                            }
                            tmpRemarks = tablesecNature + " Acno: " + tableacno;

                        } else if (tableSecDesc1.equalsIgnoreCase("LIEN")) {
                            // tmpTDAcno = orgncode + tableDetail.substring(59, 67) + "01";
                            if (!tableVchNo.equals("")) {
                                tmpTdVouch = Float.parseFloat(tableVchNo);
                            }

                            tmpRemarks = "Lien Marked For: " + Tempacno;

                            List VouchmstList = em.createNativeQuery("Select voucherNo From td_vouchmst Where Acno='" + tableacno + "' and voucherno=" + tmpTdVouch + "").getResultList();

                            if (VouchmstList.size() > 0) {
                                Integer TDlienupdate = em.createNativeQuery("Insert td_lien_update (Acno,Voucherno,receiptNo,Enterby,txndate,"
                                        + "lienStatus,remarks,marked_branch,Actype)Select acno,voucherNo,receiptNo,'" + tableEnterBy + "',now(),'Y','" + tmpRemarks + "','" + orgncode + "',"
                                        + "substring(acno,3,2) From td_vouchmst Where Acno='" + tableacno + "' and voucherno='" + tmpTdVouch + "'").executeUpdate();
                                if (TDlienupdate < 0) {
                                    return "false:Data is not inserted into td_lien_update";
                                }
                            } else {
                                msg = "FALSE";
                                return msg;
                            }

                            Integer TDVouchmst = em.createNativeQuery("Update td_vouchmst Set Lien='Y' Where acno='" + tableacno + "' and voucherno=" + tmpTdVouch + "").executeUpdate();
                            if (TDVouchmst < 0) {
                                return "false:Data is not inserted into td_vouchmst";
                            }
                            tmpRemarks = tableSecDesc1 + " Acno: " + tableacno + "/" + tmpTdVouch;

                        } else {
                            if (tablesecNature.equalsIgnoreCase("p")) {
                                tmpRemarks = "PRIMARY";
                            } else if (tablesecNature.equalsIgnoreCase("c")) {
                                tmpRemarks = "COLLATERAL";
                            }
                        }
                        Integer documentsReceived = em.createNativeQuery("insert documentsreceived (Acno,docuNo,docuDetails,receivedDate) "
                                + "values('" + Tempacno + "'," + AutoSno + ",'" + tmpRemarks + "','" + Tempbd + "')").executeUpdate();
                        if (documentsReceived < 0) {
                            return "false:Data is not inserted into Documents Received";
                        }
                        msg = "true";
                    }
                } else {
                    msg = "false";
                }
            }
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveAccountOpenSbRdWithOutTranMgmt(String cust_type, String cust_id, String actype, String title, String custname, String craddress, String praddress,
            String phoneno, String dob, int occupation, int operatingMode, String panno, String grdname, String grd_relation, String agcode, String DateText,
            String UserText, String fathername, String acnoIntro, String JtName1, String JtName2, String orgncode, String nominee, String nominee_relatioship,
            String JtName3, String JtName4, int rdperiod, float rdinstall, float rdroi, int docuno, String docudetails, String nomineeAdd, String nomineeDate,
            String custid1, String custid2, String custid3, String custid4, String schemeCode, String intCode, String actCateg, String folioNo, String hufFamily, int chqOpt) throws ApplicationException {
        try {
            String newCustNo = "", tdsFlag = "", alertMsg = "";
            String status = "";
            
            List custList = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + cust_id + "'").getResultList();
            Vector cuLst = (Vector) custList.get(0);
            String cu_id = cuLst.get(0).toString() != null ? cuLst.get(0).toString() : "";
            if ((cu_id.equalsIgnoreCase("N")) || (cu_id == null) || (cu_id.equalsIgnoreCase(""))) {
                return "Customer Verification is not completed.";
            }

            String cFlag = accOpenFacade.custMergedFlag(cust_id);
            if (cFlag.equalsIgnoreCase("false")) {
                return "Customer Id Is Merged.";
            }

            if (!((custid1 == null) || (custid1.trim().equalsIgnoreCase("")))) {
                String cFlag1 = accOpenFacade.custMergedFlag(custid1);
                if (cFlag1.equalsIgnoreCase("false")) {
                    return "Joint Holder1 Customer Id Is Merged.";
                }

                List custList1 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custid1 + "'").getResultList();
                Vector cuLst1 = (Vector) custList1.get(0);
                String cu_id1 = cuLst1.get(0).toString() != null ? cuLst1.get(0).toString() : "";
                if ((cu_id1.equalsIgnoreCase("N")) || (cu_id1 == null) || (cu_id1.equalsIgnoreCase(""))) {
                    return "Joint Holder1 Customer id " + custid1 + " Verification is not completed.";
                }
            }

            if (!((custid2 == null) || (custid2.trim().equalsIgnoreCase("")))) {
                String cFlag2 = accOpenFacade.custMergedFlag(custid2);
                if (cFlag2.equalsIgnoreCase("false")) {
                    return "Joint Holder2 Customer Id Is Merged.";
                }

                List custList2 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custid2 + "'").getResultList();
                Vector cuLst2 = (Vector) custList2.get(0);
                String cu_id2 = cuLst2.get(0).toString() != null ? cuLst2.get(0).toString() : "";
                if ((cu_id2.equalsIgnoreCase("N")) || (cu_id2 == null) || (cu_id2.equalsIgnoreCase(""))) {
                    return "Joint Holder2 Customer id " + custid2 + " Verification is not completed.";
                }
            }

            if (!((custid3 == null) || (custid3.trim().equalsIgnoreCase("")))) {
                String cFlag3 = accOpenFacade.custMergedFlag(custid3);
                if (cFlag3.equalsIgnoreCase("false")) {
                    return "Joint Holder3 Customer Id Is Merged.";
                }

                List custList3 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custid3 + "'").getResultList();
                Vector cuLst3 = (Vector) custList3.get(0);
                String cu_id3 = cuLst3.get(0).toString() != null ? cuLst3.get(0).toString() : "";
                if ((cu_id3.equalsIgnoreCase("N")) || (cu_id3 == null) || (cu_id3.equalsIgnoreCase(""))) {
                    return "Joint Holder3 Customer id " + custid3 + " Verification is not completed.";
                }
            }

            if (!((custid4 == null) || (custid4.trim().equalsIgnoreCase("")))) {
                String cFlag4 = accOpenFacade.custMergedFlag(custid4);
                if (cFlag4.equalsIgnoreCase("false")) {
                    return "Joint Holder4 Customer Id Is Merged.";
                }

                List custList4 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custid4 + "'").getResultList();
                Vector cuLst4 = (Vector) custList4.get(0);
                String cu_id4 = cuLst4.get(0).toString() != null ? cuLst4.get(0).toString() : "";
                if ((cu_id4.equalsIgnoreCase("N")) || (cu_id4 == null) || (cu_id4.equalsIgnoreCase(""))) {
                    return "Joint Holder4 Customer id " + custid4 + " Verification is not completed.";
                }
            }

            List dateDiff = em.createNativeQuery("select TIMESTAMPDIFF(day, '" + dob + "','" + DateText + "')").getResultList();
            Vector dateDiffs = (Vector) dateDiff.get(0);
            String dateDifference = dateDiffs.get(0).toString();
            int stats = Integer.parseInt(dateDifference);
            int sts = stats / 365;
            if (sts >= 18) {
                status = "MJ";
            } else if (sts < 18) {
                status = "MN";
            }
            List secList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + actype + "'").getResultList();
            Vector secLst = (Vector) secList.get(0);
            String acct_nat = secLst.get(0).toString();
            List rdDtList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + DateText + "', INTERVAL " + rdperiod + " MONTH ), '%Y%m%d')").getResultList();
            Vector rdDtLst = (Vector) rdDtList.get(0);
            String rdmatDate = rdDtLst.get(0).toString();

            List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + orgncode + "").getResultList();
            Vector alphaLst = (Vector) alphaList.get(0);
            String alphacode = alphaLst.get(0).toString();
            String custNo = "";
            if (folioNo.equalsIgnoreCase("")) {
                custNo = accOpenFacade.cbsCustId(actype, orgncode);
            } else {
                custNo = folioNo;
            }
            newCustNo = CbsUtil.lPadding(6, Integer.parseInt(custNo));
            String acno = orgncode + actype + newCustNo + "01";
            List acNoExist = em.createNativeQuery("select NEW_AC_NO from cbs_acno_mapping where new_AC_NO = '" + acno + "' ").getResultList();
            if (acNoExist.isEmpty()) {
                Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                        + "values ('" + acno + "','" + acno + "')");
                insertMapping.executeUpdate();
            } else {
                alertMsg = "This account is already exist for AcType !!!" + actype;
                return alertMsg;
            }

            Query insertQuery = em.createNativeQuery("insert into customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,occupation,status,panno, "
                    + "grdname,relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
                    + "values (" + "'" + newCustNo + "'" + "," + "'" + actype + "'" + "," + "'" + title + "'" + "," + "'" + custname + "'" + "," + "'" + craddress
                    + "'" + "," + "'" + praddress + "'" + "," + "'" + phoneno + "'" + "," + "'" + dob + "'" + "," + occupation + "," + "'" + status + "'" + ","
                    + "'" + panno + "'" + "," + "'" + grdname + "'" + "," + "'" + grd_relation + "'" + "," + "'" + DateText + "'" + "," + "'" + agcode + "'"
                    + "," + "'" + UserText + "'" + "," + "'" + fathername + "'" + "," + "'" + orgncode + "'" + ")");
//            Query insertQuery = em.createNativeQuery("insert into customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,status,panno, "
//                    + "grdname,relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
//                    + "values (" + "'" + newCustNo + "'" + "," + "'" + actype + "'" + "," + "'" + title + "'" + "," + "'" + custname + "'" + "," + "'" + craddress
//                    + "'" + "," + "'" + praddress + "'" + "," + "'" + phoneno + "'" + "," + "'" + dob + "'" + "," + "'" + status + "'" + ","
//                    + "'" + panno + "'" + "," + "'" + grdname + "'" + "," + "'" + grd_relation + "'" + "," + "'" + DateText + "'" + "," + "'" + agcode + "'"
//                    + "," + "'" + UserText + "'" + "," + "'" + fathername + "'" + "," + "'" + orgncode + "'" + ")");
            insertQuery.executeUpdate();
            if (acct_nat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                int sno = 1;
                int period = 1;
                for (int i = 0; i < rdperiod; i++) {
                    List rdDtList1 = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + DateText + "', INTERVAL " + period + " MONTH ), '%Y%m%d')").getResultList();
                    Vector rdDtLst1 = (Vector) rdDtList1.get(0);
                    String rdmatDate1 = rdDtLst1.get(0).toString();
                    Query insertQuery1 = em.createNativeQuery("insert into rd_installment(acno,sno,duedt,installamt,status,enterby)"
                            + "values (" + "'" + acno + "'" + "," + sno + "," + "'" + rdmatDate1 + "'" + "," + rdinstall + "," + "'Unpaid'" + "," + "'" + UserText + "'" + ")");
                    insertQuery1.executeUpdate();
                    sno++;
                    period++;
                }

                String docMsg = accOpenFacade.getCustAcTdsDocDtl(cust_id, "", "C");
                if (docMsg.equalsIgnoreCase("true")) {
                    tdsFlag = "N";
                    alertMsg = "Please Collect TDS Doc From This Customer";

                    List cList = em.createNativeQuery("select distinct doc_details,fyear,uniqueIdentificationNo,customerid from tds_docdetail where customerid = '" + cust_id + " ' ").getResultList();
                    if (cList.isEmpty()) {
                        cList = em.createNativeQuery("select distinct doc_details,fyear,uniqueIdentificationNo,customerid from tds_docdetail where customerid in "
                                + "(select ifnull(guardiancode,'') from cbs_cust_minorinfo where customerid = '" + cust_id + "' and ifnull(guardiancode,'') <> '')").getResultList();
                    }
                    Vector custLst = (Vector) cList.get(0);
                    String docDtl = custLst.get(0).toString();
                    String fYear = custLst.get(1).toString();
                    String uin = custLst.get(2).toString();
                    String custMjrId = custLst.get(3).toString();

                    List sList = em.createNativeQuery("select coalesce(max(seqno),0)+1 from tds_docdetail where customerid = '" + custMjrId + " '").getResultList();
                    Vector seqLst = (Vector) sList.get(0);
                    String secListed = seqLst.get(0).toString();
                    int secnum = Integer.parseInt(secListed);

                    Query insertDocQuery = em.createNativeQuery("insert into tds_docdetail(customerid,acno,seqNo,submission_date,"
                            + "fyear,receiptNo,doc_details,docFlag,orgBrnid,tranTime,enterBy,auth,uniqueIdentificationNo)"
                            + "values ('" + custMjrId + "','" + acno + "'," + secnum + ",now()," + fYear + ",'','" + docDtl + "','Y','" + orgncode + "',now(),'" + UserText + "','N','" + uin + "')");
                    int var = insertDocQuery.executeUpdate();
                    if (var <= 0) {
                        throw new ApplicationException("Data could not be Inserted");
                    }
                } else {
                    tdsFlag = "Y";
                }
            }
            /**
             * **** ARMY Thrift Account Opening Required Checking
             * EEFC_SCHEME_FLAG = THRIFT ACCOUNT required,
             * MIN_COMMIT_UTILISATION = Amount Required for Thrift Account *****
             */
            List tfAcOpeninList = em.createNativeQuery("select EEFC_SCHEME_FLAG, MIN_COMMIT_UTILISATION from cbs_scheme_general_scheme_parameter_master where scheme_type = '" + actype + "'").getResultList();
            if (tfAcOpeninList.size() > 0) {
                Vector tfAcOpeingVect = (Vector) tfAcOpeninList.get(0);
                if (tfAcOpeingVect.get(0).toString().equalsIgnoreCase("Y")) {
                    rdinstall = Float.parseFloat(tfAcOpeingVect.get(1).toString());
                    if (rdinstall <= 0) {
                        return "Please define Thrift Account value.";
                    }
                }
            }
            /**
             * **** ARMY*****
             */
            Query insertQuery2 = em.createNativeQuery("insert into accountmaster(acno,openingdt,LastOpDate,introaccno,RDInstal,RDmatDate,closingbal,opermode,JtName1,JtName2,accstatus,orgncode,nomination,ODLimit,minbal,penalty,enteredby,lastupdatedt,accttype,relatioship,JtName3,JtName4,Custname,intDeposit,ClosingDate,optstatus,custid1,custid2,custid3,custid4,CurBrCode,tdsFlag,acctCategory,huf_family,chequebook)"
                    + "values (" + "'" + acno + "'" + "," + "'" + DateText + "'" + "," + "'" + DateText + "'" + "," + "'" + acnoIntro + "'" + "," + rdinstall + "," + "'" + rdmatDate + "'"
                    + "," + 0 + "," + "'" + operatingMode + "'" + "," + "'" + JtName1 + "'" + "," + "'" + JtName2 + "'" + "," + 1 + "," + occupation + "," + "'" + nominee + "'" + "," + 0
                    + "," + 1 + "," + 0 + "," + "'" + UserText + "'" + "," + "'" + DateText + "'" + "," + "'" + actype + "'" + "," + "'" + nominee_relatioship + "'" + "," + "'" + JtName3
                    + "'" + "," + "'" + JtName4 + "'" + "," + "'" + custname + "'" + "," + rdroi + ",NULL," + 1 + "," + "'" + custid1 + "'" + "," + "'" + custid2 + "'" + "," + "'" + custid3
                    + "'" + "," + "'" + custid4 + "'" + ",'" + orgncode + "','" + tdsFlag + "','" + actCateg + "','" + hufFamily + "'," + chqOpt + ")");
//            Query insertQuery2 = em.createNativeQuery("insert into accountmaster(acno,openingdt,LastOpDate,introaccno,RDInstal,RDmatDate,closingbal,opermode,JtName1,JtName2,accstatus,nomination,ODLimit,minbal,penalty,enteredby,lastupdatedt,accttype,relatioship,JtName3,JtName4,Custname,intDeposit,ClosingDate,optstatus,custid1,custid2,custid3,custid4,CurBrCode,tdsFlag,acctCategory,huf_family)"
//                    + "values (" + "'" + acno + "'" + "," + "'" + DateText + "'" + "," + "'" + DateText + "'" + "," + "'" + acnoIntro + "'" + "," + rdinstall + "," + "'" + rdmatDate + "'"
//                    + "," + 0 + "," + "'" + operatingMode + "'" + "," + "'" + JtName1 + "'" + "," + "'" + JtName2 + "'" + "," + 1 + "," + "'" + nominee + "'" + "," + 0
//                    + "," + 1 + "," + 0 + "," + "'" + UserText + "'" + "," + "'" + DateText + "'" + "," + "'" + actype + "'" + "," + "'" + nominee_relatioship + "'" + "," + "'" + JtName3
//                    + "'" + "," + "'" + JtName4 + "'" + "," + "'" + custname + "'" + "," + rdroi + ",NULL," + 1 + "," + "'" + custid1 + "'" + "," + "'" + custid2 + "'" + "," + "'" + custid3
//                    + "'" + "," + "'" + custid4 + "'" + ",'" + orgncode + "','" + tdsFlag + "','" + actCateg + "','" + hufFamily + "')");
            insertQuery2.executeUpdate();
            if (acct_nat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Query insertQuery3 = em.createNativeQuery("insert into ca_reconbalan(acno,balance,dt)"
                        + "values (" + "'" + acno + "'" + "," + 0 + "," + "'" + DateText + "'" + ")");
                insertQuery3.executeUpdate();
            } else {
                Query insertQuery4 = em.createNativeQuery("insert into reconbalan(acno,balance,dt)"
                        + "values (" + "'" + acno + "'" + "," + 0 + "," + "'" + DateText + "'" + ")");
                insertQuery4.executeUpdate();
            }

            Query insertQuery5 = em.createNativeQuery("insert into documentsreceived(acno,groupdocu,docuno,docudetails,receiveddate)"
                    + "values (" + "'" + acno + "'" + "," + 14 + "," + docuno + "," + "'" + docudetails + "'" + "," + "'" + DateText + "'" + ")");
            insertQuery5.executeUpdate();

            Query insertQuery6 = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn)"
                    + "values (" + "'" + cust_id + "'" + "," + "'" + acno + "'" + "," + "'" + UserText + "'" + "," + "'" + alphacode + "'" + ")");
            int var6 = insertQuery6.executeUpdate();

            if (!(nominee == null || nominee.equalsIgnoreCase(""))) {
                Integer nomAge;
                List chk16 = em.createNativeQuery("SELECT TIMESTAMPDIFF(year,date_format('" + nomineeDate + "', '%Y-%m-%d'),curdate())").getResultList();
                Vector recLst12 = (Vector) chk16.get(0);
                nomAge = Integer.parseInt(recLst12.get(0).toString());
                if (nomAge >= 18) {
                    status = "N";
                } else {
                    status = "Y";
                }
                List nomNoList = em.createNativeQuery("SELECT ifnull(max(nom_reg_no),0)+1 from nom_details").getResultList();
                Vector nomVect = (Vector) nomNoList.get(0);
                long nomRegNo = Long.parseLong(nomVect.get(0).toString());
                
                Query insertQuery7 = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomdob,nomage,enterby,authby,"
                        + "trantime,nom_reg_no) values ('" + acno + "','" + nominee + "','" + nomineeAdd + "','" + nominee_relatioship + "','" + status 
                        + "','" + nomineeDate + "'," + nomAge + ",'" + UserText + "','" + UserText + "','" + DateText + "'," + nomRegNo +")");
                insertQuery7.executeUpdate();
            }

            //Addition to save in the table on the basis of Scheme Code.
            int var9 = 0;

            if (!acct_nat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                String rateCode = "";
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(dt);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                String oneBackDay = sdf.format(cal.getTime());
                rateCode = "Fl";

                List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='N' and brncode='" + orgncode + "'").getResultList();
                Vector yearEnd = (Vector) yearEndList.get(0);
                String minDate = yearEnd.get(0).toString();
                String maxDate = yearEnd.get(1).toString();

                List countingList = em.createNativeQuery("select count(*) from cbs_loan_acctype_interest_parameter where ac_type='" + CbsAcCodeConstant.SAVING_AC.getAcctCode() + "' and brncode='" + orgncode + "' and DATE_FORMAT(from_dt, '%Y%m%d') between '" + minDate + "' and '" + maxDate + "'"
                        + "and DATE_FORMAT(to_dt, '%Y%m%d') between '" + minDate + "' and '" + maxDate + "'").getResultList();
                Vector counting = (Vector) countingList.get(0);
                int no = Integer.parseInt(counting.get(0).toString());
                String calMethod = "";
                if (no == 1) {
                    calMethod = "Y";
                } else if (no == 2) {
                    calMethod = "H";
                } else if (no == 4) {
                    calMethod = "Q";
                } else if (no == 12) {
                    calMethod = "M";
                }

                /**
                 * *Addition to save the data into cbs_acc_int_rate_details
                 * table.**
                 */
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String effToDt = "31/12/2099";  //According to Mr. Alok
                int effNoOfDays = (int) CbsUtil.dayDiff(sdf.parse(sdf.format(dt)), dateFormat.parse(effToDt));

                Query insertQuery7 = em.createNativeQuery("insert into cbs_acc_int_rate_details(ACNO,AC_INT_VER_NO,INT_TABLE_CODE,ACC_PREF_CR,MIN_INT_RATE_CR,MAX_INT_RATE_CR,AC_PREF_DR,MIN_INT_RATE_DR, MAX_INT_RATE_DR,INT_PEG_FLG,PEG_FREQ_MON,PEG_FREQ_DAYS,EFF_FRM_DT,EFF_TO_DT,EFF_NO_OF_DAYS,CREATED_BY,CREATION_DT,MOD_CNT,UPDATED_BY,UPDATED_DT)"
                        + " values('" + acno + "',1,'" + intCode + "',0.00,0.00,0.00,0.00,0.00,0.00,'N',0,0,'" + DateText + "','20991231'," + effNoOfDays + ",'" + UserText + "','" + DateText + "',0,'','19000101')");

                int insertNo = insertQuery7.executeUpdate();
                if (insertNo <= 0) {
//                    ut.rollback();
                    return "Insertion problem in cbs_acc_int_rate_details table.";
                }

                /**
                 * *Addition end here***
                 */
                Query insertQuery8 = em.createNativeQuery("INSERT INTO cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                        + " VALUES ('" + acno + "','" + schemeCode + "','" + intCode + "'," + 0 + ",0.00,0.00 ,'" + rateCode + "','S','','" + calMethod + "','END','C','L','" + calMethod + "','0',0,0,'" + oneBackDay + "','" + oneBackDay + "','" + date + "')");
                insertQuery8.executeUpdate();

                //List sNoList = em.createNativeQuery("SELECT max(ifnull(sNo,0))+1 FROM  cbs_loan_interest_post_ac_wise").getResultList();
                List sNoList = em.createNativeQuery("SELECT IFNULL(MAX(SNO)+1,1) FROM  cbs_loan_interest_post_ac_wise").getResultList();
                Vector sNoVect = (Vector) sNoList.get(0);
                int sNo = Integer.parseInt(sNoVect.get(0).toString());

                Query insertQuery9 = em.createNativeQuery("INSERT INTO cbs_loan_interest_post_ac_wise(SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG)"
                        + "VALUES(" + sNo + ",'" + acno + "','Y','" + date + "','" + oneBackDay + "','" + oneBackDay + "','" + orgncode + "','I')");
                var9 = insertQuery9.executeUpdate();
            }

            // Addition ends
            if (!acct_nat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                if (var9 > 0) {
                    return "Account Open Successfully, Account No Is  " + acno + "  And Id Is " + cust_id;
                } else {
                    return "Account does not Open";
                }
            } else {
                if (var6 > 0) {
                    return "Account Open Successfully, Account No Is  " + acno + "  And Id Is " + cust_id + " " + alertMsg;
                } else {
                    return "Account does not Open";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcctCodeDetails(String acctcode) throws ApplicationException {
        try {
            return em.createNativeQuery("select acctdesc,accttype,acctnature from accounttypemaster "
                    + "where acctcode='" + acctcode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getDdAccountNo(String acno) throws ApplicationException {
        String ddAccount = "false";
        try {
            List glDescList = em.createNativeQuery("select glhead from billtypemaster where instNature = 'DD' "
                    + "glhead = '" + acno.substring(2, 10) + "'").getResultList();
            if (!glDescList.isEmpty()) {
                ddAccount = "true";
            } else {
                ddAccount = "false";
            }
            return ddAccount;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List getIssuedDdList(String opt) throws ApplicationException {
        List ddLst = new ArrayList();
        try {
            if (opt.equalsIgnoreCase("P") || opt.equalsIgnoreCase("C")) {
                ddLst = em.createNativeQuery("select instno from bill_dd where status='ISSUED' and auth ='Y'").getResultList();
            } else if (opt.equalsIgnoreCase("V")) {
                ddLst = em.createNativeQuery("select instno from bill_dd_pymtauth where auth ='N'").getResultList();
            }
            return ddLst;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getDdInstNoDetails(String opt, String instNo) throws ApplicationException {
        List instDtlLst = new ArrayList();
        try {
            if (opt.equalsIgnoreCase("P") || opt.equalsIgnoreCase("C")) {
                instDtlLst = em.createNativeQuery("select b.custname,b.seqno,b.fyear,date_format(b.origindt,'%d/%m/%Y'),c.ref_desc,b.infavourof,b.amount,"
                        + "b.org_brnid,bm.branchname,b.acno from bill_dd b, cbs_ref_rec_type c, branchmaster bm where b.status='ISSUED' "
                        + "and b.auth ='Y' and b.instno = '" + instNo + "' and b.payableat = c.ref_code and b.org_brnid = bm.brncode").getResultList();
            } else if (opt.equalsIgnoreCase("V")) {
                instDtlLst = em.createNativeQuery("select fyear,seqno,instno,custname,date_format(dt,'%d/%m/%Y'),payableat,infavourof,amount,inst_brnid,orgBrnName,"
                        + "status,dracno,cracno,enterby,flag from bill_dd_pymtauth where instno = '" + instNo + "' and flag<>'D' and auth ='N'").getResultList();
            }
            return instDtlLst;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllDdGlhead(String billNature) throws ApplicationException {
        List rsList;
        try {
            rsList = em.createNativeQuery("select glhead from billtypemaster where instnature = '" + billNature + "'").getResultList();
            if (rsList.isEmpty()) {
                throw new ApplicationException("GL Head does not exist in Bill Type Master");
            }
            return rsList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getGlTranInfo(String acno, Integer ty, double amt) throws ApplicationException {
        String glTran = "true";
        try {
            String acNature = getAccountNature(acno);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List glTpList = em.createNativeQuery("Select CrDrFlag from gltable where acno ='" + acno + "'").getResultList();
                //List glBalList = em.createNativeQuery("Select isnull(sum(cramt - dramt),0) from gl_recon where acno ='"+ acno +"'").getResultList();
                List glBalList = em.createNativeQuery("select sum(a.amt) from (Select ifnull(sum(cramt - dramt),0) as amt from gl_recon where acno ='" + acno + "' "
                        + " union select ifnull(sum(cramt - dramt),0) as amt from tokentable_credit where acno ='" + acno + "' and (auth='N' OR auth IS NULL) "
                        + " union select ifnull(sum(cramt - dramt),0) as amt from tokentable_debit where acno ='" + acno + "' and (auth='N' OR auth IS NULL) "
                        + " union select ifnull(sum(cramt - dramt),0) as amt from recon_cash_d where acno ='" + acno + "' and (auth='N' OR auth IS NULL) "
                        + " union select ifnull(sum(cramt - dramt),0) as amt from recon_trf_d where acno ='" + acno + "' and (auth='N' OR auth IS NULL) "
                        + " union select ifnull(sum(cramt - dramt),0) as amt from recon_clg_d where acno ='" + acno + "' and (auth='N' OR auth IS NULL)) a").getResultList();

                Vector element = (Vector) glTpList.get(0);
                Vector ele = (Vector) glBalList.get(0);
                double balAmt = Double.parseDouble(formatter.format(Double.parseDouble(ele.get(0).toString())));

                if (element.get(0).toString().equalsIgnoreCase("C") && (ty == 1)) {
                    if ((balAmt - amt) < 0) {
                        glTran = "Credit GL Can't go Less than Zero";
                    }
                }

                if (element.get(0).toString().equalsIgnoreCase("D") && (ty == 0)) {
                    if ((balAmt + amt) > 0) {
                        glTran = "Debit GL Can't go Greater than Zero";
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return glTran;
    }

    public String ftsAcnoValidateAuto(String acctNo, int tyFlag) throws ApplicationException {
        try {
            int acctStatus = 0;
            int optStatus = 0;
            int postflag = 0;

            List acNatureList = em.createNativeQuery("select Acctnature from accounttypemaster where acctcode='" + getAccountCode(acctNo) + "'").getResultList();
            if (acNatureList.size() <= 0) {
                return "Invalid Account Nature";
            }
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = (String) acNatureVect.get(0);

            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                if (acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    List acNoltList = em.createNativeQuery("SELECT acno FROM td_vouchmst WHERE ofACNO='" + acctNo + "'").getResultList();
                    if (acNoltList.size() <= 0) {
                        return "No Number Exist";
                    } else {
                        Vector acno = (Vector) acNoltList.get(0);
                        acctNo = acno.get(0).toString();
                    }
                }

                String chkFlag = txnAuth.fdFidilityChk(acctNo);
                if (chkFlag.equalsIgnoreCase("true")) {
                    List statustList = em.createNativeQuery("SELECT ACCSTATUS FROM fidility_accountmaster WHERE ACNO='" + acctNo + "' and auth='Y'").getResultList();
                    if (statustList.size() <= 0) {
                        return "Account number does not exist or not authorized!";
                    } else {
                        Vector status = (Vector) statustList.get(0);
                        acctStatus = Integer.parseInt(status.get(0).toString());
                    }
                } else {
                    List statustList = em.createNativeQuery("SELECT ACCSTATUS FROM td_accountmaster WHERE ACNO='" + acctNo + "' and (authby is not null or authby <>'')").getResultList();
                    if (statustList.size() <= 0) {
                        return "Account number does not exist or not authorized!";
                    } else {
                        Vector status = (Vector) statustList.get(0);
                        acctStatus = Integer.parseInt(status.get(0).toString());
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List postFlagList = em.createNativeQuery("SELECT postflag FROM gltable WHERE ACNO='" + acctNo + "'").getResultList();
                if (postFlagList.size() <= 0) {
                    return "Account number does not exist !";
                } else {
                    Vector postFlag = (Vector) postFlagList.get(0);
                    postflag = Integer.parseInt(postFlag.get(0).toString());
                }
            } else {
                List opStatusList = em.createNativeQuery("SELECT ACCSTATUS,OPTSTATUS FROM accountmaster WHERE ACNO='" + acctNo + "' and (authby is not null or authby <>'')").getResultList();
                if (opStatusList.size() <= 0) {
                    return "Account number does not exist or not authorized!";
                } else {
                    Vector ele = (Vector) opStatusList.get(0);
                    acctStatus = Integer.parseInt(ele.get(0).toString());
                    optStatus = Integer.parseInt(ele.get(1).toString());
                }
            }

            if (!(acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                if (acctStatus == 9) {
                    return "Account is Closed";
                } else if (acctStatus == 8) {
                    return "Operation Stopped For this Account";
                }

                if (acctStatus == 1 || acctStatus == 3 || acctStatus == 5 || acctStatus == 6 || acctStatus == 10 || acctStatus == 11 || acctStatus == 12 || acctStatus == 13 || acctStatus == 14) {
                    return "true";
                }

                if (acctStatus != 1 && acctStatus != 2 && acctStatus != 3 && acctStatus != 4 && acctStatus != 5 && acctStatus != 6 && acctStatus != 7 && acctStatus != 8 && acctStatus != 9 && acctStatus != 10 && acctStatus != 11 && acctStatus != 12 && acctStatus != 13 && acctStatus != 14) {
                    return "Sorry,Invalid Account Status";
                }
            }
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (postflag == 99) {
                    return "GL Head Not in Use";
                } else {
                    return "true";
                }
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getCodeFromCbsParameterinfo(String name) throws ApplicationException {
        String code = "";
        try {
            List list = em.createNativeQuery("select * from cbs_parameterinfo where name='" + name + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define cbs param-->" + name);
            }
            Vector ele = (Vector) list.get(0);
            code = ele.get(0).toString().trim();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return code;
    }

    public List chkDdflag() throws ApplicationException {
        List chkList2 = new ArrayList();
        try {
            chkList2 = em.createNativeQuery("SELECT ifnull(CODE,0) FROM parameterinfo_report WHERE REPORTNAME='DD_HEADOFFICE_ON'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return chkList2;
    }

    public List chkBadPerson(String title, String name, String dob, String pob, String designation,
            String address, String good, String low, String passportNo, String nationality) throws ApplicationException {
        List chkList = new ArrayList();
        try {
//            chkList = em.createNativeQuery("select a.title,a.name,a.dob,a.pob,a.designation,a.address,a.good_quality,a.low_quality,a.passport_no,a.nationality from"
//                    + "(select title,name,dob,pob,designation,address,good_quality,low_quality,passport_no,nationality from bad_person_info "
//                    + "union "
//                    + "select title,name,dob,pob,designation,address,good_quality,low_quality,passport_no,nationality from bad_person_info_his)a "
//                    + "where a.title like '%" + title + "%' and a.name like '%" + name + "%' and dob like '%" + dob + "%' and pob like '%" + pob + "%' "
//                    + "and address like '%" + address + "%' and passport_no like '%" + passportNo + "%' and nationality like '%" + nationality + "%' "
//                    + "and good_quality like '%" + good + "%' and low_quality like '%" + low + "%'").getResultList();

            chkList = em.createNativeQuery("select ifnull(a.title,''), ifnull(a.name,''), ifnull(a.dob,''), ifnull(a.pob,''), ifnull(a.designation,'') ,ifnull(a.address,''), ifnull(a.good_quality,''), ifnull(a.low_quality,''), ifnull(a.passport_no,''), ifnull(a.nationality,'') from "
                    + "(select title,name,dob,pob,designation,address,good_quality,low_quality,passport_no,nationality from bad_person_info "
                    + "union "
                    + "select title,name,dob,pob,designation,address,good_quality,low_quality,passport_no,nationality from bad_person_info_his)a "
                    + "where ((a.name like '%" + name + "%' and  dob like '%" + dob + "%')"
                    + "or (a.name like '%" + name + "%' and  nationality like '%" + nationality + "%')"
                    + "or (a.name like '%" + name + "%' and  address like '%" + address + "%')"
                    + "or (a.name like '%" + name + "%' and if(LENGTH('" + passportNo + "') > 0, passport_no like '%" + passportNo + "%',0)) "
                    + "or (a.name like '%" + name + "%' and if(LENGTH('" + pob + "') > 0, pob like '%" + pob + "%',0)))").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return chkList;
    }

    public List autoPayLienStatus(String acNo, double recptNo) throws ApplicationException {
        List chkList = new ArrayList();
        try {
            chkList = em.createNativeQuery("select ifnull(lien,'N'),ifnull(auto_pay,'N') from td_vouchmst where acno = '" + acNo + "' "
                    + "and ReceiptNo = " + recptNo + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return chkList;
    }

    /**
     * *******VERY VERY IMPORTANT MSG If any changes will be occurred in
     * npaPosting method Then same will be modify in
     * AccountAuthorizationManagementFacade method name is same *******
     */
    public String npaPosting(List<NpaAccountDetailPojo> npaList, String dt, String enterBy, String orgnCode) throws ApplicationException {
        try {
            if (npaList.isEmpty()) {
                throw new ApplicationException("There is no data to mark as npa.");
            }
            int pCodeNo = 0;
            double glBalGoingNpa = 0;
            float trsNo = 0f;
            float recNo = 0f;
            String toDt = ymd.format(new Date()), msg = "", brnCode = "", markAcno = "";
            List pOpt = em.createNativeQuery("Select ifnull(code,0) From parameterinfo_report Where reportname ='PIC_OPT'").getResultList();
            if (!pOpt.isEmpty()) {
                Vector pNo = (Vector) pOpt.get(0);
                pCodeNo = Integer.parseInt(pNo.get(0).toString());
            }
            String autoNpaEnable = "N";
            List autoNpaList = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name = 'AUTO-NPA'").getResultList();
            if (!autoNpaList.isEmpty()) {
                Vector autoNpaVect = (Vector) autoNpaList.get(0);
                autoNpaEnable = autoNpaVect.get(0).toString().equalsIgnoreCase("Y") ? autoNpaVect.get(0).toString() : autoNpaEnable;
            }
            if (autoNpaEnable.equalsIgnoreCase("Y")) {
                Map<String, String> map = new HashMap<String, String>();
                for (NpaAccountDetailPojo pojo : npaList) {
                    if (!map.containsKey(pojo.getAcNo().substring(2, 4))) { //not Present                    
                        map.put(pojo.getAcNo().substring(2, 4), pojo.getAcNo().substring(2, 4));
                    }
                }
                for (Map.Entry entry : map.entrySet()) {
                    trsNo = getTrsNo();
                    glBalGoingNpa = 0;
                    for (NpaAccountDetailPojo pojo : npaList) {
                        if (!pojo.getAcNo().substring(0, 2).equalsIgnoreCase(orgnCode)) {
                            throw new ApplicationException("Auto Npa Marking is not allowed by other branch.");
                        }
                        String markStatus = pojo.getMarkFlag();
                        if (markStatus.equalsIgnoreCase("M") && pojo.getAcNo().substring(2, 4).equalsIgnoreCase(entry.getValue().toString())) {
                            String listAcno = pojo.getAcNo();
                            String accStatus = pojo.getStatus();
//                    String npaMarkDt = ymd.format(dmyy.parse(pojo.getDueDt()));
                            String npaMarkDt = dt;
                            String spFlag = "";

                            String frDt = ymd.format(dmyy.parse(pojo.getLastCrDate()));
                            if (accStatus.trim().toUpperCase().contains("SUB")) {
                                accStatus = "SUB STANDARD";
                                spFlag = "11";
                            } else if (accStatus.trim().toUpperCase().contains("DOU")) {
                                accStatus = "DOUBTFUL";
                                spFlag = "12";
                            }

                            String presentStatus = pojo.getCurrentStatus().toUpperCase();
                            if (presentStatus.equalsIgnoreCase("OPE") || presentStatus.equalsIgnoreCase("STANDARD")) {
                                presentStatus = "1";
                            } else if (presentStatus.contains("SUB")) {
                                presentStatus = "11";
                            } else if (presentStatus.equalsIgnoreCase("DOU")) {
                                presentStatus = "12";
                            }

                            List list = em.createNativeQuery("select code from codebook where groupcode='3' and code<> 0 and description = '" + accStatus + "'").getResultList();
                            if (list.isEmpty()) {
                                throw new ApplicationException("Please fill data in codebook for groupcode 3.");
                            }
                            Vector element = (Vector) list.get(0);
                            String code = element.get(0).toString();

                            markAcno = listAcno;
                            brnCode = getCurrentBrnCode(markAcno);
                            String acctNature = getAccountNature(markAcno);

                            String updateDt = "";
                            if (code.equalsIgnoreCase("11")) {
                                updateDt = "NPADT";
                            } else if (code.equalsIgnoreCase("12")) {
                                updateDt = "DBTDT";
                            }

                            /*
                             * Interest going in NPA 
                             */
                            if (autoNpaEnable.equalsIgnoreCase("Y")) {
                                if ((presentStatus.equalsIgnoreCase("1")) && (spFlag.equalsIgnoreCase("11") || spFlag.equalsIgnoreCase("12"))) {
                                    double balGoingNpa = 0;
                                    String npaDuration = "";
                                    List<LoanAcDetailStatementPojo> resultList = accountAuthFacade.getLoanGoingToNpa(markAcno, frDt, dt, brnCode);
                                    if (resultList.size() > 0) {
                                        balGoingNpa = resultList.get(0).getBalance();
                                        npaDuration = resultList.get(0).getDate();
                                    }

                                    if (balGoingNpa > 0) {
                                        /* Credit the Party Account */
                                        recNo = getRecNo();

                                        if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                            Query q4 = em.createNativeQuery("INSERT INTO loan_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                                    + "('" + markAcno + "',0.0,'" + toDt + "','" + toDt + "',0," + balGoingNpa + ",0,2," + recNo + "," + trsNo + ","
                                                    + "3,0,3,'INTT. TRF FOR MEM " + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + brnCode + "','" + brnCode + "')");
                                            q4.executeUpdate();

                                            msg = updateBalance(acctNature, markAcno, balGoingNpa, 0, "Y", "N");
                                            if (!msg.equalsIgnoreCase("TRUE")) {
                                                throw new ApplicationException(msg);
                                            }

                                        } else {
                                            Query q4 = em.createNativeQuery("INSERT INTO ca_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                                    + "('" + markAcno + "',0.0,'" + toDt + "','" + toDt + "',0," + balGoingNpa + ",0,2," + recNo + "," + trsNo + ","
                                                    + "3,0,3,'INTT. TRF FOR MEM " + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + brnCode + "','" + brnCode + "')");
                                            q4.executeUpdate();

                                            msg = updateBalance(acctNature, markAcno, balGoingNpa, 0, "Y", "N");
                                            if (!msg.equalsIgnoreCase("TRUE")) {
                                                throw new ApplicationException(msg);
                                            }
                                        }

                                        recNo = getRecNo();
                                        Query q5 = em.createNativeQuery("INSERT INTO npa_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY,INTAMT, ORG_BRNID, DEST_BRNID) VALUES "
                                                + "('" + markAcno + "',0.0,'" + toDt + "','" + toDt + "'," + balGoingNpa + ",0,1,8," + recNo + "," + trsNo + ","
                                                + "3,0,3,'INTT. TRF FOR MEM " + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "'," + balGoingNpa + ",'" + brnCode + "','" + brnCode + "')");
                                        q5.executeUpdate();
                                        msg = updateBalance(acctNature, markAcno, 0, balGoingNpa, "Y", "N");
                                        if (!msg.equalsIgnoreCase("TRUE")) {
                                            throw new ApplicationException(msg);
                                        }
                                        glBalGoingNpa = glBalGoingNpa + balGoingNpa;
                                    }

                                    if (spFlag.equalsIgnoreCase("11") || spFlag.equalsIgnoreCase("12")) {
                                        Query q = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,effdt,amount,enterby,auth,authby,baseacno, TRANTIME) values ('" + markAcno + "','" + accStatus + "','" + code + "','" + dt + "','" + npaMarkDt + "',0,'" + enterBy + "','Y','SYSTEM','" + markAcno + "', now())");
                                        int insertResult = q.executeUpdate();
                                        if (insertResult < 0) {
                                            throw new ApplicationException("Problem In Account Status Table Inertion.");
                                        }

                                        insertResult = em.createNativeQuery("update accountmaster set accstatus = '" + code + "' where acno = '" + markAcno + "'").executeUpdate();
                                        if (insertResult <= 0) {
                                            throw new ApplicationException("Problem In Accountmaster Table Updation.");
                                        }
                                        if (pCodeNo == 1) {
                                            insertResult = em.createNativeQuery("update loan_appparameter set recover = 'PIC', presentstatus = '" + accStatus + "', npaAcNo='"
                                                    + markAcno + "', " + updateDt + " ='" + dt + "' where acno = '" + markAcno + "'").executeUpdate();
                                            if (insertResult <= 0) {
                                                throw new ApplicationException("Updation Problem In Loan Appparameter Table.");
                                            }
                                        } else {
                                            insertResult = em.createNativeQuery("update loan_appparameter set presentstatus = '" + accStatus + "', npaAcNo='"
                                                    + markAcno + "', " + updateDt + " ='" + dt + "' where acno = '" + markAcno + "'").executeUpdate();
                                            if (insertResult <= 0) {
                                                throw new ApplicationException("Updation Problem In Loan Appparameter Table.");
                                            }
                                        }
                                    }
                                }
                            }
                            if ((presentStatus.equalsIgnoreCase("11")) && (spFlag.equalsIgnoreCase("12"))) {
                                Query q = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,effdt,amount,enterby,auth,authby,baseacno, TRANTIME) values ('" + markAcno + "','" + accStatus + "','" + code + "','" + dt + "','" + npaMarkDt + "',0,'" + enterBy + "','Y','SYSTEM','" + markAcno + "', now())");
                                int insertResult = q.executeUpdate();
                                if (insertResult < 0) {
                                    throw new ApplicationException("Problem In Account Status Table Inertion.");
                                }

                                insertResult = em.createNativeQuery("update accountmaster set accstatus = '" + code + "' where acno = '" + markAcno + "'").executeUpdate();
                                if (insertResult <= 0) {
                                    throw new ApplicationException("Problem In Accountmaster Table Updation.");
                                }
                                if (pCodeNo == 1) {
                                    insertResult = em.createNativeQuery("update loan_appparameter set recover = 'PIC', presentstatus = '" + accStatus + "', npaAcNo='"
                                            + markAcno + "', " + updateDt + " ='" + dt + "' where acno = '" + markAcno + "'").executeUpdate();
                                    if (insertResult <= 0) {
                                        throw new ApplicationException("Updation Problem In Loan Appparameter Table.");
                                    }
                                } else {
                                    insertResult = em.createNativeQuery("update loan_appparameter set presentstatus = '" + accStatus + "', npaAcNo='"
                                            + markAcno + "', " + updateDt + " ='" + dt + "' where acno = '" + markAcno + "'").executeUpdate();
                                    if (insertResult <= 0) {
                                        throw new ApplicationException("Updation Problem In Loan Appparameter Table.");
                                    }
                                }
                            }
                        }
                    }
                    if (glBalGoingNpa > 0 && autoNpaEnable.equalsIgnoreCase("Y")) {
                        List listGlhead = em.createNativeQuery("SELECT GLHEADINT,ifnull(GLHEADURI,''),ifnull(glheadname,'') FROM accounttypemaster WHERE ACCTCODE='" + entry.getValue().toString() + "'").getResultList();
                        if (listGlhead.isEmpty()) {
                            msg = "Please define the Overdue Interest Reserve and Interest receivable Gl head.";
                            throw new ApplicationException(msg);
                        }
                        Vector elementGlHead = (Vector) listGlhead.get(0);
                        String intGl = elementGlHead.get(0).toString();
                        String uriGl = elementGlHead.get(1).toString();
                        String oirHead = elementGlHead.get(2).toString();
                        recNo = getRecNo();
                        String interestAcno = brnCode + intGl + "01";
                        Query q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                + "('" + interestAcno + "',0.0,'" + toDt + "','" + toDt + "'," + glBalGoingNpa + ",0,1,2," + recNo + "," + trsNo + ","
                                + "3,0,3,'INTT. TRF FOR MEM : Batch " + trsNo + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + brnCode + "','" + brnCode + "','','')");
                        q4.executeUpdate();
                        msg = updateBalance("PO", brnCode + intGl + "01", 0, glBalGoingNpa, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }

                        if (!oirHead.equals("") && !uriGl.equals("")) {
                            recNo = getRecNo();
                            String oHead = brnCode + oirHead + "01";
                            q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                    + "('" + oHead + "',0.0,'" + toDt + "','" + toDt + "',0,ABS(" + glBalGoingNpa + "),0,2," + recNo + "," + trsNo + ","
                                    + "3,0,3,'Int. Trf to Memo : Batch " + trsNo + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + brnCode + "','" + brnCode + "','','')");
                            q4.executeUpdate();
                            msg = updateBalance("PO", brnCode + oirHead + "01", Math.abs(glBalGoingNpa), 0, "Y", "N");
                            if (!msg.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(msg);
                            }

                            recNo = getRecNo();
                            oHead = brnCode + uriGl + "01";
                            q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                    + "('" + oHead + "',0.0,'" + toDt + "','" + toDt + "',ABS(" + glBalGoingNpa + "),0,1,2," + recNo + "," + trsNo + ","
                                    + "3,0,3,'Int. Trf to Memo : Batch " + trsNo + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + brnCode + "','" + brnCode + "','','')");
                            q4.executeUpdate();

                            msg = updateBalance("PO", brnCode + uriGl + "01", 0, Math.abs(glBalGoingNpa), "Y", "N");
                            if (!msg.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            msg = "Please define the Overdue Interest Reserve and Interest receivable Gl head.";
                            throw new ApplicationException(msg);
                        }
                    }
                }
                return "Npa marking is successfull.";
            } else {
                return "Please enable the NPA Marking parameter.";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String insertionAsPerPrincipalInt(String acNo, String toDt, double intAmt) throws ApplicationException {
        String msg = "TRUE", flag = "";
        try {
            List npaList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME IN ('NPAINT')").getResultList();
            if (npaList.isEmpty()) {
                flag = "calc";
            }
            String acctNature = common.getAcNatureByAcNo(acNo);
            double drIntAmt = 0, drPrinAmt = 0;
            List entryExistInTableQuery = em.createNativeQuery("select date_format(dt,'%Y-%m-%d'), prin_amt, int_amt, chg_amt, date_format(trantime,'%Y-%m-%d') "
                    + " from cbs_loan_ac_wise_prin_int "
                    + " where acno = '" + acNo + "' and sno = (select ifnull(max(sno),0) from cbs_loan_ac_wise_prin_int "
                    + " where acno = '" + acNo + "' and dt<='" + toDt + "' )").getResultList();
            if (entryExistInTableQuery.isEmpty()) {
                List prinAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
                        + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from " + common.getTableName(acctNature) + " where acno =  '" + acNo + "' and VALUEDT<='" + toDt + "'  and auth = 'Y' and trandesc not in (3,4,8) "
                        + " union all "
                        + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + acNo + "' and VALUEDT<='" + toDt + "'  and auth = 'Y' and trandesc not in (3,4,8) ) a").getResultList();
//                List prinAmtList = em.createNativeQuery("select  ifnull(sum(ifnull(dramt,0)),0) from loan_recon where ACNO = '" + acNo + "' and date_format(dt,'%Y-%m-%d') <= '" + toDt + "'  and trandesc not in (3,4)").getResultList();
                if (!prinAmtList.isEmpty()) {
                    Vector prinAmtVect = (Vector) prinAmtList.get(0);
                    drPrinAmt = Double.parseDouble(prinAmtVect.get(0).toString());
                    List intAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
                            + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from " + common.getTableName(acctNature) + " where acno =  '" + acNo + "' and VALUEDT<='" + toDt + "'  and auth = 'Y'  and trandesc in (3,4,8)  "
                            + " union all "
                            + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + acNo + "' and VALUEDT<='" + toDt + "'  and auth = 'Y'  and trandesc in (3,4,8) ) a").getResultList();
//                        List intAmtList = em.createNativeQuery("select  ifnull(sum(ifnull(dramt,0)),0) from loan_recon where ACNO = '" + acNo + "' and date_format(dt,'%Y-%m-%d') <= '" + toDt + "'  and trandesc in (3,4)").getResultList();
                    if (!intAmtList.isEmpty()) {
                        Vector intAmtVect = (Vector) intAmtList.get(0);
                        drIntAmt = Double.parseDouble(intAmtVect.get(0).toString());
                    }
                    List crAmtBetweenDtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  from "
                            + "(select ifnull(sum(ifnull(cramt,0)),0)  as cramt from " + common.getTableName(acctNature) + " where acno =  '" + acNo + "' and VALUEDT<='" + toDt + "' and auth = 'Y'  "
                            + "union all "
                            + "select ifnull(sum(ifnull(cramt,0)),0)  as cramt  from npa_recon where acno =  '" + acNo + "' and VALUEDT<='" + toDt + "'  and auth = 'Y' ) a").getResultList();
//                        List crAmtBetweenDtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0) from loan_recon where ACNO = '" + acNo + "' and dt <='" + toDt + "' AND AUTH = 'Y' ").getResultList();
                    if (!crAmtBetweenDtList.isEmpty()) {
                        Vector crAmtBetweenDtVect = (Vector) crAmtBetweenDtList.get(0);
                        double crAmtBetweenDt = Double.parseDouble(crAmtBetweenDtVect.get(0).toString());
                        if (crAmtBetweenDt >= drIntAmt) {
                            drPrinAmt = drPrinAmt - intAmt - (crAmtBetweenDt - drIntAmt);
                            drIntAmt = 0;
                        } else {
                            drIntAmt = drIntAmt - crAmtBetweenDt;
                            drPrinAmt = drPrinAmt;
                        }
                    } else {
                        drIntAmt = drIntAmt;
                        drPrinAmt = drPrinAmt;
                    }
                } else {
                    drIntAmt = drIntAmt;
                    drPrinAmt = drPrinAmt;
                }
            } else {
                Vector prinIntVect = (Vector) entryExistInTableQuery.get(0);
                String lastIntDt = CbsUtil.dateAdd(ymd.format(ymmd.parse(prinIntVect.get(0).toString())), 1);
                drPrinAmt = Double.parseDouble(prinIntVect.get(1).toString());
                drIntAmt = Double.parseDouble(prinIntVect.get(2).toString());
                if (ymmd.parse(prinIntVect.get(4).toString()).before(ymmd.parse(prinIntVect.get(0).toString()))) {
                    lastIntDt = CbsUtil.dateAdd(ymd.format(ymmd.parse(prinIntVect.get(4).toString())), 1);
                }
                String npaDrWithoutIntBetweenDtQuery = "  union All SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + toDt + "'  AND AUTH = 'Y' and tranDesc not in (3,4,8) group by acno ) a";
                String npaDrIntBetweenDtQuery = "  union All SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + toDt + "'  AND AUTH = 'Y' and tranDesc in (3,4,8) group by acno ) a";
                String npaCrBetweenDtQuery = "  union All SELECT IFNULL(SUM(IFNULL(CRAMT,0)),0) as amt FROM npa_recon WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + toDt + "'  AND AUTH = 'Y'  group by acno ) a";

                double drWithoutIntBetDt = 0, dtIntBetDt = 0;
                List outStDrWithoutIntAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM "
                        + CbsUtil.getReconTableName(acctNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + toDt + "' AND AUTH = 'Y' and tranDesc not in (3,4,8) group by  acno "
                        + (flag.equalsIgnoreCase("calc") ? npaDrWithoutIntBetweenDtQuery : ") a")).getResultList();
                if (!outStDrWithoutIntAmtList.isEmpty()) {
                    Vector outStDrWithoutIntAmtVect = (Vector) outStDrWithoutIntAmtList.get(0);
                    drWithoutIntBetDt = Double.parseDouble(outStDrWithoutIntAmtVect.get(0).toString());
                }
                List outStDrIntAmtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(DRAMT,0)),0) as amt FROM "
                        + CbsUtil.getReconTableName(acctNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + toDt + "' AND AUTH = 'Y' and tranDesc in (3,4,8) group by  acno "
                        + (flag.equalsIgnoreCase("calc") ? npaDrIntBetweenDtQuery : ") a")).getResultList();
                if (!outStDrIntAmtList.isEmpty()) {
                    Vector outStDrIntAmtListVect = (Vector) outStDrIntAmtList.get(0);
                    dtIntBetDt = Double.parseDouble(outStDrIntAmtListVect.get(0).toString()) - intAmt;
                }
                List crAmtBetweenDtList = em.createNativeQuery("select ifnull(sum(a.amt),0) from (SELECT IFNULL(SUM(IFNULL(CRAMT,0)),0) as amt FROM "
                        + CbsUtil.getReconTableName(acctNature) + " WHERE ACNO = '" + acNo + "' AND VALUEDT between  '" + lastIntDt + "' AND  '" + toDt + "' AND AUTH = 'Y' group by  acno "
                        + (flag.equalsIgnoreCase("calc") ? npaCrBetweenDtQuery : ") a")).getResultList();
                if (!crAmtBetweenDtList.isEmpty()) {
                    Vector crAmtBetweenDtVect = (Vector) crAmtBetweenDtList.get(0);
                    double crAmtBetweenDt = Double.parseDouble(crAmtBetweenDtVect.get(0).toString());
                    if (crAmtBetweenDt >= (drIntAmt + dtIntBetDt)) {
                        drPrinAmt = drPrinAmt + drWithoutIntBetDt - (crAmtBetweenDt - drIntAmt - dtIntBetDt);
                        drIntAmt = 0;
                    } else {
                        drPrinAmt = drPrinAmt + drWithoutIntBetDt;
                        drIntAmt = drIntAmt + dtIntBetDt - crAmtBetweenDt;
                    }
                } else {
                    drPrinAmt = drPrinAmt + drWithoutIntBetDt;
                    drIntAmt = drIntAmt + dtIntBetDt;
                }
            }
//            Query insertPrinIntQuery = em.createNativeQuery("INSERT INTO cbs_loan_ac_wise_prin_int (acno, dt, prin_amt, int_amt, chg_amt) "
//                    + " VALUES ('" + acNo + "', '" + toDt + "', " + (drPrinAmt < 0 ? 0 : drPrinAmt) + ", " + (drIntAmt + Math.abs(intAmt)) + ", 0)");
//            Integer insertReconBalan = insertPrinIntQuery.executeUpdate();
//            if (insertReconBalan == 0) {
//                msg = "false";
//                throw new ApplicationException("Value doesn't inserted in cbs_loan_ac_wise_prin_int");
//            }
        } catch (Exception e) {
            msg = "false ";
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public String getKeyPwd() throws ApplicationException {
        try {
            if (getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                List list = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where "
                        + "name in('DSG-KEY-PWD')").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    return ele.get(0).toString().trim();
                }
            }
            return "";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String isReceiptExist(String acno) throws ApplicationException {
        String msg = "true";
        try {
            acno = acno.trim();
            List list = em.createNativeQuery("select acno from td_vouchmst where acno ='" + acno + "'").getResultList();
            if (list == null || list.isEmpty()) {
                msg = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String receiptNotCreated(String tempBd, String brnCode) throws ApplicationException {
        String msg = "true";
        try {
            List list = em.createNativeQuery("select distinct t.acno from td_recon t, td_accountmaster a where substring(t.acno,1,2)='" + brnCode + "' "
                    + " and dt = '" + tempBd + "' and t.acno = a.acno and a.accstatus <> 9 ").getResultList();
            if (list == null || list.isEmpty()) {
            } else {
                for (int i = 0; i < list.size(); i++) {
                    Vector acLst = (Vector) list.get(0);
                    String acno = acLst.get(0).toString();
                    String acMsg = isReceiptExist(acno);
                    if (acMsg.equalsIgnoreCase("false")) {
                        return "Receipt Not Created For Account " + acno;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String npaRecoveryUpdation(float trsNumber, String nature, String acNo, String curDt, double amount, String brCode, String toBrCode, String authBy) throws ApplicationException {
        try {
            if (nature.equals(CbsConstant.TERM_LOAN) || nature.equals(CbsConstant.DEMAND_LOAN) || nature.equals(CbsConstant.CURRENT_AC)) {
                /* Dont remove this code and remove comment after confirmation from basti*/
                String status = interBranchFacade.fnGetLoanStatus(acNo, curDt);
                if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                    BigDecimal currentBal = new BigDecimal(ftsGetBal(acNo));
                    String result = "";
                    if (interBranchFacade.npaPOrdChk(acNo).equalsIgnoreCase("true")) {
                        result = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, amount, authBy, brCode, toBrCode, curDt, trsNumber);
                        if (!result.equalsIgnoreCase("True")) {
                            throw new ApplicationException(result);
                        }
                    } else if (interBranchFacade.npaPOrdChk(acNo).equalsIgnoreCase("false")) {
                        int code = getCodeForReportName("PIC-OS-RECOVERY");
                        if (code == 1) {
                            if ((currentBal.doubleValue()) > 0) {
                                String msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, curDt, trsNumber);
                                if (!msg.equalsIgnoreCase("True")) {
                                    throw new ApplicationException(msg);
                                }
                            }
                        } else {
                            double totInstallAmt = 0;
                            if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                /**/
                                List crAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  as cramt from " + common.getTableName(nature) + " where acno =  '" + acNo + "' and dt<='" + curDt + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' and details not like '%Clg Return%' ").getResultList();
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
                                        String msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, curDt, trsNumber);
                                        if (!msg.equalsIgnoreCase("True")) {
                                            throw new ApplicationException(msg);
                                        }
                                    }
                                } else {
                                    installAmtList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno = '" + acNo + "' and duedt<='" + curDt + "'").getResultList();
                                    Vector instllAmtVect = (Vector) installAmtList.get(0);
                                    totInstallAmt = Double.parseDouble(instllAmtVect.get(0).toString());
                                    double remainPriAmt = totInstallAmt - totIntDrAmt;
                                    if ((remainPriAmt > 0) && (remainPriAmt <= (totCrAmt - totIntDrAmt)) && (totCrAmt - totInstallAmt) >= 0) {
                                        amount = totCrAmt - totInstallAmt;
                                        String msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, amount, authBy, brCode, toBrCode, curDt, trsNumber);
                                        if (!msg.equalsIgnoreCase("True")) {
                                            throw new ApplicationException(msg);
                                        }

                                    }
                                }
                            } else {
                                if ((currentBal.doubleValue()) > 0) {
                                    String msg = interBranchFacade.npaRecoveryAmtUpdation(acNo, curDt, currentBal.doubleValue(), authBy, brCode, toBrCode, curDt, trsNumber);
                                    if (!msg.equalsIgnoreCase("True")) {
                                        throw new ApplicationException(msg);
                                    }
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

    public String strDetailInsertion(String alertSubCode, String acNo, Float trSno, Float recNo, String orginBrCode, String destBrCode, String enterBy) throws ApplicationException {
        try {
            if (!(alertSubCode == null || alertSubCode.equalsIgnoreCase(""))) {
                if (alertSubCode.contains("!")) {
                    if ((alertSubCode.split("!")[2]).equalsIgnoreCase("N")) {
                        Query insert = em.createNativeQuery("INSERT INTO cbs_str_detail (acno, batch_no, recno, dt, orgnbrncode, destbrncode,"
                                + "alert_code, enter_by, trantime, auth_by, auth_status, update_date, flag) VALUES "
                                + "('" + acNo + "', " + trSno + ", " + recNo + ", curdate(), '" + orginBrCode + "', '" + destBrCode + "',"
                                + " '" + alertSubCode.split("!")[0] + "', '" + enterBy + "',now(), '', 'N', now(),'EXP')");
                        int insertResult = insert.executeUpdate();
                        if (insertResult <= 0) {
                            throw new ApplicationException("Problem in data insertion for cbs_str_detail");
                        }
                    } else {
                        Query insert = em.createNativeQuery("INSERT INTO cbs_str_detail (acno, batch_no, recno, dt, orgnbrncode, destbrncode,"
                                + "alert_code, enter_by, trantime, auth_by, auth_status, update_date, flag) VALUES "
                                + "('" + acNo + "', " + trSno + ", " + recNo + ", curdate(), '" + orginBrCode + "', '" + destBrCode + "',"
                                + " '" + alertSubCode.split("!")[0] + "', '" + enterBy + "',now(), '', 'N', now(),'STR')");
                        int insertResult = insert.executeUpdate();
                        if (insertResult <= 0) {
                            throw new ApplicationException("Problem in data insertion for cbs_str_detail");
                        }
                    }
                } else {
                    Query insert = em.createNativeQuery("INSERT INTO cbs_str_detail (acno, batch_no, recno, dt, orgnbrncode, destbrncode,"
                            + "alert_code, enter_by, trantime, auth_by, auth_status, update_date, flag) VALUES "
                            + "('" + acNo + "', " + trSno + ", " + recNo + ", curdate(), '" + orginBrCode + "', '" + destBrCode + "',"
                            + " '" + alertSubCode + "', '" + enterBy + "',now(), '', 'N', now(),'STR')");

                    int insertResult = insert.executeUpdate();
                    if (insertResult <= 0) {
                        throw new ApplicationException("Problem in data insertion for cbs_str_detail");
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);

        }
        return "true";
    }

    public String strDetailDeletion(String acNo, Float trSno, Float recNo, String dt, String enterBy) throws ApplicationException {
        try {
            Query updateQuery = em.createNativeQuery("update cbs_str_detail set batch_no = " + trSno + ",auth_status = 'D',auth_by = '" + enterBy + "',update_date = now() where acno = '" + acNo + "' and dt= '" + dt + "' and recno = " + recNo + " and auth_status = 'N'");
            int updatetResult = updateQuery.executeUpdate();
            if (updatetResult <= 0) {
                throw new ApplicationException("Problem in data updation for cbs_str_detail");
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "true";
    }

    public double getDebitAmtByAcno(String acNo, String frdt, String toDt) throws ApplicationException {
        double drAmt = 0d;
        try {
            String actNature = getAccountNature(acNo);
            String tableName = common.getTableName(actNature);

//            List amtList = em.createNativeQuery("select ifnull(sum(DrAmt),0) from " + tableName + " where acno = '" + acNo + "' and TranType = 0 and Ty = 1 "
//                    + "and dt between '" + frdt + "' and '" + toDt + "'").getResultList();
            List amtList = em.createNativeQuery("select sum(drAmt) from "
                    + "(select ifnull(sum(DrAmt),0) as drAmt from " + tableName + " where acno = '" + acNo + "' and TranType = 0 and Ty = 1 and dt between '" + frdt + "' and '" + toDt + "' "
                    + "union all "
                    + "select ifnull(sum(DrAmt),0)as drAmt from tokentable_debit  where acno = '" + acNo + "' and TranType = 0 and Ty = 1 and (auth = 'N' or (tokenpaidby is null or tokenpaidby ='')) and dt between '" + frdt + "' and '" + toDt + "'"
                    + " union all "
                    + "select (ifnull(sum(DrAmt),0) - ifnull(sum(CrAmt),0)) as drAmt from " + tableName + "  where acno = '" + acNo + "' and TranType = 2 and Trandesc = 70 and "
                    + "(details not like 'ATM POS TXN AT%' and details not like 'ATM MERCHANT SALE AT%' and details not like 'ATM PURCHASE WITH CASH BACK AT%' and details not like 'ATM E-COMM TXN AT%') "
                    + "and (details not like 'ATM POS TXN FEE AT%' and details not like 'ATM MERCHANT SALE FEE AT%' and details not like 'ATM PURCHASE WITH CASH BACK FEE AT%' and details not like 'ATM E-COMM TXN FEE AT%') and dt between '" + frdt + "' and '" + toDt + "')a").getResultList();

            if (!amtList.isEmpty()) {
                Vector amtVector = (Vector) amtList.get(0);
                drAmt = Double.parseDouble(amtVector.get(0).toString());
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return drAmt;
    }

    public List getTotDebitAndCreditTran(String acno, String dt) throws ApplicationException {
        List tranList = new ArrayList();
        try {
//            tranList = em.createNativeQuery("select ifnull(count(*),0) from ca_recon where acno = '"+ acno +"' and dt >= '"+ dt +"' and trantype <>8 and trandesc not in(3,4) and ty = 0 "
//                    + " union all "
//                    + "select ifnull(count(*),0) from ca_recon where acno = '"+ acno +"' and dt >= '"+ dt +"' and trantype <>8 and trandesc not in(3,4) and ty = 1").getResultList();
//            
            tranList = em.createNativeQuery("select a.cramt,b.dramt from ( select ifnull(count(*),0) as cramt from ca_recon "
                    + " where acno = '" + acno + "' and dt >= '" + dt + "' and trantype <>8 and trandesc not in(3,4,5,7,25,33,35,65,71) and ty = 0) a, "
                    + " (select ifnull(count(*),0) as dramt from ca_recon where acno = '" + acno + "' and dt >= '" + dt + "' and "
                    + " trantype <>8 and trandesc not in(3,4,5,7,25,33,35,65,71) and ty = 1) b").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tranList;
    }

    public boolean xchangeDenoModule(String brnCode) throws ApplicationException {
        try {
            List cashModeList = em.createNativeQuery("select ifnull(cashmod,'N') from parameterinfo where brncode='" + brnCode + "'").getResultList();
            if (cashModeList.isEmpty()) {
                return false;
            }
            Vector amtVector = (Vector) cashModeList.get(0);
            String cashMode = amtVector.get(0).toString();
            String code = "0";
            if (cashMode.equalsIgnoreCase("N")) {
                code = getCodeByReportName("TELLER_DENOMINITION");
            } else {
                code = getCodeByReportName("CASH DENOMINITION MODULE");
            }
            if (code.equals("1")) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    //If return is true then data will load on cash received form.
    public Boolean[] ddsCashReceivedFlag(String brnCode) throws ApplicationException {
        try {
            Boolean arr[] = new Boolean[2];

            List cashModeList = em.createNativeQuery("select ifnull(cashmod,'N') from parameterinfo "
                    + "where brncode='" + brnCode + "'").getResultList();
            if (cashModeList.isEmpty()) {
                arr[0] = false; //For data load
                arr[1] = false; //For denomination grid show
                return arr;
            }
            Vector amtVector = (Vector) cashModeList.get(0);
            String cashMode = amtVector.get(0).toString();
            int code = 0;

            if (cashMode.equalsIgnoreCase("N")) {
                code = getCodeForReportName("TELLER_DENOMINITION");
                if (code == 1) {
                    arr[0] = true; //For data load
                    arr[1] = true; //For denomination grid show
                    return arr;
                } else {
                    arr[0] = false; //For data load
                    arr[1] = false; //For denomination grid show
                    return arr;
                }
            } else {
                code = getCodeForReportName("CASH DENOMINITION MODULE");
                if (code == 1) {
                    arr[0] = true; //For data load
                    arr[1] = true; //For denomination grid show
                    return arr;
                } else {
                    arr[0] = true; //For data load
                    arr[1] = false; //For denomination grid show
                    return arr;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getTotDebitAndCreditAfterCir(String acno, String dt) throws ApplicationException {
        List tranList = new ArrayList();
        try {
            tranList = em.createNativeQuery("select sum(a.denoCr1 + c.denoCr2),b.denoDr from "
                    + " (select ifnull(sum(denomination * denomination_value),0) as denoCr1 from denomination_detail where acno ='" + acno + "' "
                    + " and ty = 0 and denomination not in (500.0,1000.0) and dt >= '" + dt + "')a,"
                    + " (select ifnull(sum(denomination * denomination_value),0) as denoDr from denomination_detail where acno ='" + acno + "' "
                    + " and ty = 1 and dt >='" + dt + "') b,(select ifnull(sum(denomination * denomination_value),0) as denoCr2 "
                    + " from denomination_detail where acno ='" + acno + "' and ty = 0 and dt >='" + dt + "' and denomination in (500.0,1000.0) "
                    + " and new_old_flag ='N') c").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tranList;
    }

    public String getCircularPassInfo(String acno, String dt, double amt) throws ApplicationException {
        String msg = "true";
        try {
            acno = acno.trim();
            List list = em.createNativeQuery("select acno from sbn_pass_details where acno ='" + acno + "' and dt ='" + dt + "'"
                    + "and auth ='Y' and amount=" + amt + " and recno = 0.0").getResultList();
            if (list == null || list.isEmpty()) {
                msg = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public List getTotCreditAfterCir(String acno, String dt) throws ApplicationException {
        List tranList = new ArrayList();
        try {
            tranList = em.createNativeQuery("select ifnull(sum(denomination * denomination_value),0) as denoCr from "
                    + " denomination_detail where acno ='" + acno + "' and ty = 0 and dt >='" + dt + "' "
                    + " and denomination in (500.0,1000.0) and new_old_flag ='O'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tranList;
    }

    public List getNameForcts(String acName, String acNo) throws ApplicationException {
        try {
            String actNature = getAcNatureByCode(acNo.substring(2, 4));

            if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                return em.createNativeQuery("select custname,ifnull(JtName1,''),ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,'') from td_accountmaster where acno = '" + acNo + "'").getResultList();
            } else if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                return em.createNativeQuery("select AcName,''JtName1,''JtName2,''JtName3,''JtName4 from gltable where acno = '" + acNo + "'").getResultList();
            } else {
                return em.createNativeQuery("select custname,ifnull(JtName1,''),ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,'') from accountmaster where acno = '" + acNo + "'").getResultList();
            }

//            return em.createNativeQuery("select * from accountmaster where custname like '%" + acName + "%' and acno = '" + acNo + "' "
//                    + "union "
//                    + "select * from accountmaster where JtName1 like '%" + acName + "%' and acno = '" + acNo + "' "
//                    + "union "
//                    + "select * from accountmaster where JtName2 like '%" + acName + "%' and acno = '" + acNo + "' "
//                    + "union "
//                    + "select * from accountmaster where JtName3 like '%" + acName + "%' and acno = '" + acNo + "' "
//                    + "union "
//                    + "select * from accountmaster where JtName4 like '%" + acName + "%' and acno = '" + acNo + "' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List getBranchCodeList(String orgnCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            List list1 = em.createNativeQuery("select alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
            Vector v1 = (Vector) list1.get(0);
            String aCode = v1.get(0).toString();
            if (aCode.equalsIgnoreCase("HO")) {
                result = em.createNativeQuery("SELECT 'A','ALL' union select cast(brncode as char(2)),alphacode from branchmaster").getResultList();
            } else {
                result = em.createNativeQuery("select brncode ,alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List getAccountDetails(String branchCode, String acType, String accountNo) throws ApplicationException {
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select PrdAcctId,b.acno,d.custname,d.fathername,date_format(d.DateOfBirth,'%d/%m/%Y') from rdmast a,accountmaster b,customerid c,cbs_customer_master_detail d "
                    + "where brncode = '" + branchCode + "' and substring(PrdAcctId,1,2) = '" + acType + "' and a.acno = '" + accountNo + "' "
                    + "and a.acno=b.acno and a.acno = c.acno and c.custid = cast(d.customerid as unsigned)").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return result;

    }

    public List getReceiptNo(String branchCode, String acType, String accountNo, String receiptNo) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select PrdAcctId,b.acno,d.custname,d.fathername,date_format(d.DateOfBirth,'%d/%m/%Y') "
                    + "from rdmast a,accountmaster b,customerid c,cbs_customer_master_detail d where brncode = '" + branchCode + "' "
                    + "and substring(PrdAcctId,1,2) = '" + acType + "' "
                    + "and substring(PrdAcctId,19,6) = '" + accountNo + "' and  cast(substring(PrdAcctId,25,8) as unsigned) = " + Integer.parseInt(receiptNo) + " "
                    + "and a.acno=b.acno and a.acno = c.acno and c.custid = cast(d.customerid as unsigned)").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getCheqRetChgAndHead(String purpose, String acType) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select glheadMisc,charges from parameterinfo_miscincome "
                    + "where purpose = '" + purpose + "' and acctcode = '" + acType + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getChequeReturnCharge(String chargeType, String chargeName, double amount, String accountType) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String curDate = ymd.format(new Date());
            dataList = em.createNativeQuery("select CR_GL_HEAD,amt from cbs_charge_detail where CHARGE_TYPE = '" + chargeType + "' and charge_name='" + chargeName + "' and ac_type='" + accountType + "' "
                    + "and " + amount + " between from_range and to_range and eff_date=(select max(eff_date) from cbs_charge_detail where CHARGE_TYPE = '" + chargeType + "' and "
                    + "charge_name='" + chargeName + "' and ac_type='" + accountType + "' and " + amount + " between from_range and to_range and eff_date<= '" + curDate + "') ").getResultList();
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getBusinessDate() throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT DATE_FORMAT(DATE,'%Y%m%d') FROM cbs_bankdays WHERE DAYBEGINFLAG='Y' AND DAYENDFLAG='N'").getResultList();
            if (list.isEmpty()) {
                list = em.createNativeQuery("SELECT DATE_FORMAT(MIN(DATE),'%Y%m%d') FROM cbs_bankdays WHERE DAYBEGINFLAG='N' AND DAYENDFLAG='N'").getResultList();
            }
            Vector vector = (Vector) list.get(0);
            return vector.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public Map<Integer, String> clearingReturnReasonInMap() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        try {
            List list = em.createNativeQuery("select code,ifnull(description,'') from codebook where groupcode=13").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define clearing return data.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                map.put(Integer.parseInt(ele.get(0).toString()), ele.get(1).toString());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return map;
    }

    @Override
    public String isATMHead(String acno, String orgBrCode) throws ApplicationException {
        String result = "false";
        try {
            if (acno == null || acno.trim().equals("") || acno.trim().length() != 12) {
                throw new Exception("ATM Head can not be blank.");
            }
            if (!getAccountNature(acno).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                throw new Exception("This is not a proper ATM Head.");
            }
            List list = em.createNativeQuery("select atm_id from atm_master where atm_branch='" + orgBrCode + "' and "
                    + "atm_cash_general_head='" + acno + "'").getResultList();
            if (list.isEmpty()) {
                result = "false";
            } else {
                result = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    @Override
    public List getAccountDetailFromAccountMaster(String acno) throws ApplicationException {
        return em.createNativeQuery("select date_format(ifnull(rdmatdate,'19000101'),'%Y%m%d') from "
                + "accountmaster where acno='" + acno + "'").getResultList();
    }

    @Override
    public List getBranchNameEmail(String brcode) throws ApplicationException {
        try {
            return em.createNativeQuery("select BranchName,email from branchmaster where BrnCode = " + Integer.parseInt(brcode)).getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getGlHeadbalance(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select cast(Balance as decimal(25,2)) as balance from reconbalan where acno = '" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getMaxyearEndDate(String year) throws ApplicationException {
        try {
            return em.createNativeQuery("select MAXDATE from cbs_yearend where substring(MAXDATE,1,4) = '" + year + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List isVerifyPending(String acNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno from td_payment_auth where auth='N' and acno='" + acNo + "' "
                    + "and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.RECURRING_AC + "'))").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String isMinorCustomerId(String customerId) throws ApplicationException {

        String isMinir = "";
        try {
            List list = em.createNativeQuery("select ifnull(minorflag,'') from cbs_customer_master_detail where customerid = '" + customerId + "'").getResultList();
            Vector vtr = (Vector) list.get(0);
            isMinir = vtr.get(0).toString();
            return isMinir;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String autoInterestSaveLoanInterestParameter(String financialYear, String acctType, String interestType, String intOption, String userName, String todayDate, String brnCode, String frDt, String chgParameter, String acctNature) throws ApplicationException {
        try {

            int noOfMonths = 0;
            int valueOfMonths = 0;
            String mode = "", mode1 = "", brCodeQuery = "", brnCodeQuery = "";

//            YearEndDatePojo yDate = new YearEndDatePojo();
//            yDate = (YearEndDatePojo) rbiReportFacade.getYearEndDataAccordingFinYear(brnCode.equalsIgnoreCase("0A") ? "90" : brnCode, financialYear);
//            String finStartDt = yDate.getMinDate();
//            String finEndDt = yDate.getMaxDate();
            String finStartDt = financialYear + "0401";
            String finEndDt = (Integer.parseInt(financialYear) + 1) + "0331";

            String toDt = finEndDt;
            String frStDt = frDt;
            String toStDt = toDt;

//            String fromDt = financialYear + "04" + "01";
//            String toDt = "";
            if (intOption.equalsIgnoreCase("M")) {
                noOfMonths = 12;
                valueOfMonths = 1;
            }
            if (intOption.equalsIgnoreCase("Q")) {
                noOfMonths = 4;
                valueOfMonths = 3;
            }
            if (intOption.equalsIgnoreCase("H")) {
                noOfMonths = 2;
                valueOfMonths = 6;
            }
            if (intOption.equalsIgnoreCase("Y")) {
                noOfMonths = 1;
                valueOfMonths = 12;
            }
            if (intOption.equalsIgnoreCase("M")) {
                mode = "Monthly";
            }
            if (intOption.equalsIgnoreCase("Q")) {
                mode = "Quarterly";
            }
            if (intOption.equalsIgnoreCase("H")) {
                mode = "Half Yearly";
            }
            if (intOption.equalsIgnoreCase("Y")) {
                mode = "Yearly";
            }
            if (interestType.equalsIgnoreCase("I")) {
                mode1 = "Interest";
            }
            if (interestType.equalsIgnoreCase("D")) {
                mode1 = "Deposit";
            }
            if (interestType.equalsIgnoreCase("P")) {
                mode1 = "Penal";
            }
            if (interestType.equalsIgnoreCase("NA")) {
                mode1 = "Not Applicable";
            }
            if (interestType.equalsIgnoreCase("MB")) {
                mode1 = "Msg Charge";
            }
            //if (brnCode.equalsIgnoreCase("0A") || brnCode.equalsIgnoreCase("90")) {
            if (brnCode.equalsIgnoreCase("0A")) {
                brCodeQuery = "";
                brnCodeQuery = "";
            } else {
                brCodeQuery = "  where brncode = '" + brnCode + "' ";
                brnCodeQuery = " and brncode = '" + brnCode + "' ";
            }
//            int finYear = Integer.parseInt(financialYear) + 1;
//            String finYearCheck = Integer.toString(finYear);
//            String toDateCheck = finYearCheck + "03" + "31";

            if (chgParameter.equalsIgnoreCase("Charge")) {
                if (ymd.parse(frDt).before(ymd.parse(finStartDt))) {
                    throw new ApplicationException("Start Date should not be less than Financial year Start Date");
                }
                if (ymd.parse(frDt).after(ymd.parse(finEndDt))) {
                    throw new ApplicationException("Start Date should not be greater than Financial year End Date");
                }
                if (!acctNature.equalsIgnoreCase("A") && acctType.equalsIgnoreCase("A")) {
                    acctType = acctNature;
                }
                List chkListCheck = em.createNativeQuery("SELECT DISTINCT(COUNT(*)) FROM cbs_loan_acctype_interest_parameter WHERE AC_TYPE = '" + acctType + "' " + brnCodeQuery
                        + " and Flag = '" + interestType + "' and FROM_DT BETWEEN '" + frDt + "' AND '" + toDt
                        + "' GROUP BY BRNCODE HAVING COUNT(*)>1").getResultList();
                if (!chkListCheck.isEmpty()) {
                    Vector Lst = (Vector) chkListCheck.get(0);
                    int count = Integer.parseInt(Lst.get(0).toString());
                    //if (count == noOfMonths) {
                    throw new ApplicationException("Data is already posted for the A/C type " + acctType
                            + " as Interest Option " + mode + " and Interest Type " + mode1);
                    // }
                }
            } else {
                String acNature = getAcNatureByCode(acctType);
                if (acNature.equals(CbsConstant.SAVING_AC)) {
                    List chkListCheck = em.createNativeQuery("SELECT DISTINCT(COUNT(*)) FROM cbs_loan_acctype_interest_parameter WHERE AC_TYPE = '" + acctType + "' " + brnCodeQuery
                            + " and FROM_DT BETWEEN '" + frDt + "' AND '" + toDt + "' GROUP BY BRNCODE HAVING COUNT(*)>1").getResultList();
                    if (!chkListCheck.isEmpty()) {
                        Vector Lst = (Vector) chkListCheck.get(0);
                        int count = Integer.parseInt(Lst.get(0).toString());
                        //  if (count == noOfMonths * 2) {
                        throw new ApplicationException("Data is already posted for the A/C type " + acctType
                                + " as Interest Option " + mode + " and Interest Type " + mode1);
                        // }
                    }
                } else {
                    if (ymd.parse(frDt).before(ymd.parse(finStartDt))) {
                        throw new ApplicationException("Start Date should not be less than Financial year Start Date");
                    }
                    if (ymd.parse(frDt).after(ymd.parse(finEndDt))) {
                        throw new ApplicationException("Start Date should not be greater than Financial year End Date");
                    }
                    List chkListCheck = em.createNativeQuery("SELECT DISTINCT(COUNT(*)) FROM cbs_loan_acctype_interest_parameter WHERE AC_TYPE = '" + acctType + "' " + brnCodeQuery
                            + " and Flag = '" + interestType + "' and FROM_DT BETWEEN '" + frDt + "' AND '" + toDt
                            + "' GROUP BY BRNCODE HAVING COUNT(*)>1").getResultList();
                    if (!chkListCheck.isEmpty()) {
                        Vector Lst = (Vector) chkListCheck.get(0);
                        int count = Integer.parseInt(Lst.get(0).toString());
                        //if (count == noOfMonths) {
                        throw new ApplicationException("Data is already posted for the A/C type " + acctType
                                + " as Interest Option " + mode + " and Interest Type " + mode1);
                        // }
                    }
                }
            }
            int recNo = 0;
            List chkList = em.createNativeQuery(" select brncode from branchmaster " + brCodeQuery).getResultList();
            if (!chkList.isEmpty()) {
                for (int j = 0; j < chkList.size(); j++) {
//                    fromDt = financialYear + "04" + "01";
                    frDt = frStDt;
                    toDt = toStDt;
                    for (int i = 0; i < noOfMonths; i++) {
                        Vector Lst = (Vector) chkList.get(j);

                        brnCode = CbsUtil.lPadding(2, Integer.parseInt(Lst.get(0).toString()));
                        toDt = CbsUtil.dateAdd(CbsUtil.monthAdd(frDt, valueOfMonths), -1);

                        List codeCheck = em.createNativeQuery("select ifnull(max(SNO),0)+1 from cbs_loan_acctype_interest_parameter").getResultList();
                        if (codeCheck.size() <= 0) {
                            recNo = 1;
                        } else {
                            Vector elem = (Vector) codeCheck.get(0);
                            recNo = Integer.parseInt(elem.get(0).toString());
                        }
                        if (chgParameter.equalsIgnoreCase("Charge")) {
                            if (ymd.parse(toDt).after(ymd.parse(finEndDt))) {
                                break;
                            }
                            if (!acctNature.equalsIgnoreCase("A") && acctType.equalsIgnoreCase("A")) {
                                acctType = acctNature;
                            }
                            Integer minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                    + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + interestType + "')").executeUpdate();
                            if (minChargeStatusList <= 0) {
                                throw new ApplicationException("Data Not Saved");
                            }
                            frDt = CbsUtil.dateAdd(toDt, 1);
                            if (ymd.parse(frDt).after(ymd.parse(finEndDt))) {
                                break;
                            }
                        } else {
                            String acNature = getAcNatureByCode(acctType);
                            if (acNature.equals(CbsConstant.SAVING_AC)) {
                                Integer minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                        + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + 1 + "')").executeUpdate();
                                if (minChargeStatusList <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                recNo = recNo + 1;
                                minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                        + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + 2 + "')").executeUpdate();
                                if (minChargeStatusList <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                frDt = CbsUtil.dateAdd(toDt, 1);
                            } else {
                                if (ymd.parse(toDt).after(ymd.parse(finEndDt))) {
                                    break;
                                }
                                Integer minChargeStatusList = em.createNativeQuery("insert into cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,POST_FLAG,DT,BRNCODE,ENTER_BY,FLAG)"
                                        + "VALUES(" + recNo + ",'" + acctType + "','" + frDt + "','" + toDt + "','N','" + todayDate + "','" + brnCode + "','" + userName + "','" + interestType + "')").executeUpdate();
                                if (minChargeStatusList <= 0) {
                                    throw new ApplicationException("Data Not Saved");
                                }
                                frDt = CbsUtil.dateAdd(toDt, 1);
                                if (ymd.parse(frDt).after(ymd.parse(finEndDt))) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
