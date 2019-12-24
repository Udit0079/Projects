/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.common;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.DividendTable;
import com.cbs.dto.ckycr.CKYCRDownloadDetail30;
import com.cbs.dto.ckycr.CKYCRDownloadDetail60;
import com.cbs.dto.other.DenominationDetailTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.pojo.CKYCRDownloadPojo;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.Validator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author Administrator
 */
@Stateless(mappedName = "InterBranchTxnFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class InterBranchTxnFacade implements InterBranchTxnFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnAuth;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private BankProcessManagementFacadeRemote bankProcessManagement;
    @EJB
    private InterBranchTxnFacadeRemote ibtRemote;
    @EJB
    private CommonReportMethodsRemote reportRemote;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    SimpleDateFormat ymdhh = new SimpleDateFormat("dd-MMM-yy-hh:mm:ss");
    NumberFormat formatter = new DecimalFormat("#.##");
    Date curDt = new Date();
//    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymdSql = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public InterBranchTxnFacade() {
    }

    /**
     *
     * @param acNo
     * @param tranType
     * @param ty
     * @param amt
     * @param dt
     * @param valueDt
     * @param enterBy
     * @param orgBrCode
     * @param destBrCode
     * @param tranDesc
     * @param details
     * @param trsNo
     * @param recNo
     * @param tranId
     * @param termId
     * @param auth
     * @param authBy
     * @param subTokenNo
     * @param payBy
     * @param instNo
     * @param instDt
     * @param tdacNo
     * @param vchNo
     * @param intFlag
     * @param closeFlag
     * @param screenFlag
     * @param txnStatus
     * @param tokenNoDr
     * @param cxSxFlag
     * @return
     */
    public String ftsPosting43CBS(String acNo, Integer tranType, Integer ty, Double amt, String dt, String valueDt,
            String enterBy, String orgBrCode, String destBrCode, Integer tranDesc, String details, Float trsNo,
            Float recNo, Integer tranId, String termId, String auth, String authBy, String subTokenNo, Integer payBy,
            String instNo, String instDt, String tdacNo, Float vchNo, String intFlag, String closeFlag,
            String screenFlag, String txnStatus, Float tokenNoDr, String cxSxFlag, String adviceNo, String adviceBrnCode) throws ApplicationException {
        try {
            String cashMod = "";
            String acctNature;
            Double crAmt = 0.0d;
            Double drAmt = 0.0d;
            String tokenPaid;
            String tokenPaidBy;
            float lTokenNo = 0.0f;
            int iy;
            String custName;
            String version;

            Float tokenNo = 0.0f;
            String msg;

            // String acNo1 = "";
            System.out.println("Incomming Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty);
            if ((acNo == null) || (acNo.equalsIgnoreCase(""))) {
                return "ACCOUNT NO IS BLANK !";
            }
            iy = 1;
            if (trsNo == null) {
                trsNo = 0.0f;
            }
            if (tranDesc == null) {
                tranDesc = 0;
            }
            if (tokenNo == null) {
                tokenNo = 0.0f;
            }

            if ((ftsPosting.getAccountCode(acNo)).equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode()) == true) {
                List chkList = em.createNativeQuery("SELECT ifnull(POSTFLAG,1) FROM gltable WHERE ACNO='" + acNo + "' AND POSTFLAG IN (11,12,13)").getResultList();
                if (chkList.isEmpty()) {
                    iy = 1;
                } else {
                    Vector Lst = (Vector) chkList.get(0);
                    iy = Integer.parseInt(Lst.get(0).toString());
                }
            }

            /**
             * *Commented by dinesh**
             */
//            msg = ftsPosting.ftsDateValidate(dt, orgBrCode);
//
//            if (!(msg.equalsIgnoreCase("TRUE"))) {
//                return "" + msg;
//            }
            if (valueDt == null) {
                valueDt = dt;
            }
            List chkList2 = em.createNativeQuery("SELECT ifnull(CODE,41) FROM parameterinfo_report WHERE REPORTNAME='VERSION'").getResultList();
            Vector verLst = (Vector) chkList2.get(0);
            version = verLst.get(0).toString();

            if ((version.equalsIgnoreCase("")) || (!(version.equalsIgnoreCase("53")))) {
                version = "41";
            }
            if ((acNo == null) || (acNo.equalsIgnoreCase(""))) {
                msg = "ACCOUNT NO IS BLANK !";
                return "" + msg;
            }
            if (ty == null) {
                msg = "INVALID VALUE FOR TY ( NULL ) !";
                return "" + msg;
            }
            if (!((ty == 0) || (ty == 1))) {
                msg = "INVALID VALUE FOR TY !  " + ty;
                return "" + msg;
            }

            if (tranDesc == 35) {
                msg = ftsPosting.ftsAcnoValidateAuto(acNo, ty);
            } else {
                msg = ftsPosting.ftsAcnoValidate(acNo, ty, "");
            }

            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return "" + msg;
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
            if ((enterBy == null) || (enterBy.equalsIgnoreCase(""))) {
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
            /**
             * Added by Dinesh Pratap Singh For Interbranch Transaction Where
             * Enter By is SYSTEM.
             */
            if (!enterBy.equalsIgnoreCase("SYSTEM")) {
                msg = ftsPosting.ftsUserValidate(enterBy, orgBrCode);
            }
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
            List chkList3 = em.createNativeQuery("SELECT COALESCE(CASHMOD,'Y') FROM parameterinfo WHERE brncode = CAST('" + orgBrCode + "' AS unsigned)").getResultList();
            /**
             * Modified by dinesh, At above cashMod was initialized.
             */
            if (!chkList3.isEmpty()) {
                Vector verLst1 = (Vector) chkList3.get(0);
                cashMod = verLst1.get(0).toString();
            } else {
                return "Cash mode not found !";
            }

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

            List chkList4 = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE='" + ftsPosting.getAccountCode(acNo) + "'").getResultList();
            Vector verLst2 = (Vector) chkList4.get(0);
            acctNature = verLst2.get(0).toString();

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
//            if (instDt == null) {
//                instDt = "";
//            }
            if (instDt == null || instDt.equals("")) {
                instDt = "19000101";
            }

            String msg1 = ftsPosting.chkBal(acNo, ty, tranDesc, acctNature);
            if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                msg = checkBalance(acNo, amt, enterBy);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return "" + msg;
                }
            }

            custName = ftsPosting.ftsGetCustName(acNo);
            System.out.println("After Validation Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty);
            if (tranType == 0) {
                if (cashMod.equalsIgnoreCase("N")) {
                    tokenPaid = "Y";
                    tokenPaidBy = "SYSTEM";
                } else {
                    tokenPaid = "N";
                    tokenPaidBy = "";
                }
                if (cxSxFlag.equalsIgnoreCase("C")) {
                    iy = 8888;
                } else if (cxSxFlag.equalsIgnoreCase("S")) {
                    iy = 9999;
                }
                if (ty == 0) {
                    if ((recNo == null) || (recNo == 0)) {
                        recNo = ftsPosting.getRecNo();
                    }
                    if (!auth.equalsIgnoreCase("Y")) {
                        //changed by Prakash Starts
                        if ((payBy == 1) && (ty == 0) && (auth.equalsIgnoreCase("N"))) {
                            msg = ftsPosting.updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                return "CASH CREDIT BY CHEQUE IS NOT ALLOWED";
                            }
                        }
                        System.out.println("Before inserting data in tokentable_credit Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty);
                        //changed by Prakash Ends
                        Query insertQuery = em.createNativeQuery("INSERT INTO tokentable_credit(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,TY,TRANTYPE,RECNO,INSTNO,PAYBY,"
                                + "IY,AUTH,ENTERBY,TRANDESC,DETAILS,TOKENPAIDBY,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                                + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + ty + "," + tranType + ","
                                + recNo + ",'" + instNo + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + ",'" + details + "','"
                                + tokenPaidBy + "','" + termId + "','" + orgBrCode + "','" + destBrCode + "'," + tranId + ")");
                        insertQuery.executeUpdate();
                        System.out.println("After inserting data in tokentable_credit Account number is = " + acNo + " and amount is = " + amt + " and transaction type is = " + tranType + " and TY is = " + ty);
                    }

                    //changed by Dinesh
                    if (!auth.equalsIgnoreCase("Y")) {
                        List TOKENNOList = em.createNativeQuery("SELECT TOKENNO FROM tokentable_credit WHERE RECNO=" + recNo + " AND DT='" + dt + "' AND ORG_BRNID = '" + orgBrCode + "'").getResultList();
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
                                + termId + "','" + orgBrCode + "','" + destBrCode + "'," + tranId + ")");
                        insertQuery1.executeUpdate();
                    }

                    if (auth.equalsIgnoreCase("Y")) {
//                      Query deleteQuery = em.createNativeQuery("DELETE FROM tokentable_credit WHERE RECNO=" + recNo + " AND DT='" + dt + "' AND ORG_BRNID = '" + orgBrCode + "'");
                        Query deleteQuery = em.createNativeQuery("update tokentable_credit set auth='Y', authby='" + authBy + "' WHERE RECNO=" + recNo + " AND DT='" + dt + "' AND ORG_BRNID = '" + orgBrCode + "'");
                        deleteQuery.executeUpdate();
                    }

                } else if (ty == 1) {

                    if ((cashMod.equalsIgnoreCase("Y")) && (!(payBy == 5 || payBy == 6))) {
                        lTokenNo = tokenNo;
                        if (!(tokenNo != 9999999)) {
                            lTokenNo = ftsPosting.ftsTokenTable(ty, subTokenNo, version);
                        }
                        if (lTokenNo == 0) {
                            msg = "TOKEN NO :- " + tokenNo + " HAS BEEN ISSUED !";
                            return "" + msg;
                        }
                    } else {
                        tokenPaid = "Y";
                        tokenPaidBy = "SYSTEM";
                    }
                    if ((recNo == null) || (recNo == 0)) {
                        recNo = ftsPosting.getRecNo();
                    }

                    if (!auth.equalsIgnoreCase("Y")) {
                        //changed by Prakash
                        if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("N"))) {
                            msg = ftsPosting.updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                return msg;
                            }
                        }
                        //changed by Prakash Ends
                        try {
                            Query insertQuery = em.createNativeQuery("INSERT INTO tokentable_debit(ACNO,CUSTNAME,DT,VALUEDT,DRAMT,TY,TRANTYPE,RECNO,INSTNO,INSTDT,PAYBY,"
                                    + "IY,AUTH,AUTHBY,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,TOKENPAIDBY,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                                    + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + drAmt + "," + ty + ","
                                    + tranType + "," + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','"
                                    + authBy + "','" + enterBy + "'," + tranDesc + "," + tokenNo + ",'" + subTokenNo + "','"
                                    + details + "','" + tokenPaidBy + "','" + termId + "','" + orgBrCode + "','" + destBrCode + "'," + tranId + ")");
                            insertQuery.executeUpdate();
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                    if (auth.equalsIgnoreCase("Y")) {
                        if ((iy == 9999) && cashMod.equalsIgnoreCase("Y")) {
                            tokenPaidBy = "SYSYEM";
                        }
                        Query insertQuery1 = em.createNativeQuery("INSERT INTO recon_cash_d(ACNO ,CUSTNAME, TY, DT,VALUEDT, DRAMT, CRAMT,TRANTYPE, DETAILS, "
                                + "IY, INSTNO,INSTDT,ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,"
                                + "TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                                + "values ('" + acNo + "','" + custName + "'," + ty + ",'" + dt + "','" + valueDt + "'," + drAmt + ","
                                + crAmt + "," + tranType + ",'" + details + "'," + iy + ",'" + instNo + "','" + instDt + "','" + enterBy + "','" + auth + "',"
                                + recNo + "," + payBy + ",'" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP," + tranDesc + "," + tokenNo + ",'"
                                + tokenPaidBy + "','" + subTokenNo + "','" + termId + "','" + orgBrCode + "','" + destBrCode + "'" + "," + tranId + ")");
                        insertQuery1.executeUpdate();
                    }
                }
            }
            if ((version.equalsIgnoreCase("53")) && (tranType == 1)) {
                if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("N"))) {
                    msg = ftsPosting.updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        return msg;
                    }
                }
                //Added by Dinesh on 21/02/2011 For CTS
                if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("Y"))) {
                    msg = ftsPosting.updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        return msg;
                    }

                    msg = ftsPosting.ftsInstDateValidate(instDt);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        return msg;
                    }
                }
                if ((recNo == null) || (recNo == 0)) {
                    recNo = ftsPosting.getRecNo();
                }
                Query insertQuery2 = em.createNativeQuery("INSERT INTO recon_clg_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,INSTNO,INSTDT"
                        + "PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,SCREENFLAG,TXNSTATUS,AUTHBY,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                        + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + drAmt + "," + ty + ","
                        + tranType + "," + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + ","
                        + tokenNo + ",'" + subTokenNo + "','" + details + "','" + screenFlag + "','" + txnStatus + "','" + authBy + "','" + termId + "','"
                        + orgBrCode + "','" + destBrCode + "'," + tranId + ")");
                insertQuery2.executeUpdate();
            }

            if ((version.equalsIgnoreCase("41")) || ((version.equalsIgnoreCase("53")) && ((tranType == 2) || (tranType == 6) || (tranType == 8) || (tranType == 27))) || ((version.equalsIgnoreCase("53"))
                    && ((tranType == 0) || (tranType == 1)) && (auth.equalsIgnoreCase("Y")))) {
                if ((recNo == null) || (recNo == 0)) {
                    recNo = ftsPosting.getRecNo();
                }
                if ((version.equalsIgnoreCase("53")) && ((tranType == 2) || (tranType == 6) || (tranType == 8) || (tranType == 27))) {
                    if (!auth.equalsIgnoreCase("Y")) {
                        if ((payBy == 1) && (ty == 1) && (auth.equalsIgnoreCase("N"))) {
                            msg = ftsPosting.updateCheque(acNo, payBy, ty, Float.parseFloat(instNo), enterBy);
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                return msg;
                            }
                        }
                        Query insertQuery3 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                                + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                                + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)"
                                + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + drAmt + ","
                                + ty + "," + tranType + "," + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "',"
                                + tranDesc + "," + tokenNo + ",'" + subTokenNo + "','" + details + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'"
                                + termId + "','" + orgBrCode + "','" + destBrCode + "'," + tranId + ",'','')");
                        insertQuery3.executeUpdate();
                    }

                    if (auth.equalsIgnoreCase("Y")) {
                        Query updateQuery = em.createNativeQuery("UPDATE recon_trf_d SET AUTH='Y',AUTHBY='" + authBy + "' WHERE TRSNO=" + trsNo + " AND ACNO='" + acNo + "' and recno = " + recNo + "");
                        updateQuery.executeUpdate();

                        if (tranDesc == 65 || tranDesc == 66 || tranDesc == 67) {
                            Query insertQuery3 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,INSTNO,INSTDT,"
                                    + "PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)"
                                    + "values ('" + acNo + "','" + custName + "','" + dt + "','" + valueDt + "'," + crAmt + "," + drAmt + "," + ty + "," + tranType + ","
                                    + recNo + ",'" + instNo + "','" + instDt + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + "," + tokenNo + ",'"
                                    + subTokenNo + "','" + details + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'" + termId + "','"
                                    + orgBrCode + "','" + destBrCode + "'," + tranId + ",'','')");
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
                    msg = ftsPosting.insertRecons(acctNature, acNo, ty, amt, dt, valueDt, tranType, details, enterBy, trsNo, null, recNo, auth,
                            authBy, tranDesc, payBy, instNo, instDt, tokenNo, null, subTokenNo, iy, tdacNo, vchNo, intFlag, closeFlag, orgBrCode, destBrCode, tranId, termId, adviceNo, adviceBrnCode);
                }
            }

            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return msg;
            }

            if ((ty == 0) && (auth.equalsIgnoreCase("Y"))) {
                msg = ftsPosting.updateBalance(acctNature, acNo, crAmt, drAmt, "Y", "Y");
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }
            }

            /*
             * ======================@@@@@@@@@@@@@==========================
             * New Addition by Alok Yadav on 30-May-2011
             */
            if ((auth.equalsIgnoreCase("Y"))) {
                if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    /* Dont remove this code and remove comment after confirmation from basti*/
                    msg = ftsPosting.npaRecoveryUpdation(trsNo, acctNature, acNo, valueDt, crAmt, orgBrCode, destBrCode, enterBy);
                    if (!msg.equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                    if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        if ((tranDesc == 0) || (tranDesc == 1) || (tranDesc == 2) || (tranDesc == 3) || (tranDesc == 4) || (tranDesc == 5) || (tranDesc == 6) || (tranDesc == 7) || (tranDesc == 8) || (tranDesc == 67) || (tranDesc == 66)) {
                            msg = ftsPosting.loanDisbursementInstallment(acNo, amt, ty, authBy, dt, recNo, tranDesc, details);
                            if (!msg.equals("TRUE")) {
                                return msg;
                            }
                        }
                    }
                } else if ((acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC))) {
                    if (((tranDesc == 0) || (tranDesc == 1) || (tranDesc == 6))) {
                        msg = ftsPosting.loanDisbursementInstallment(acNo, amt, ty, authBy, dt, recNo, tranDesc, details);
                        if (!msg.equals("TRUE")) {
                            return msg;
                        }
                    }
                }
            }
            //acNo1 = acNo.substring(2, 10);
            if ((auth.equalsIgnoreCase("Y")) && (payBy == 1) && acctNature.equals(CbsConstant.PAY_ORDER) && ftsPosting.isPoDdGlhead(acNo.substring(2, 10))) {
                msg = ftsPosting.processBill(acNo, instNo, instDt, amt, ty, tranType, authBy);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return msg;
                }
            }
            return "" + msg + tokenNo;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param acctNo
     * @return
     */
    public String ftsGetBalRemote(String acctNo) throws ApplicationException {
        try {
            double balance = 0;

            List acNatureList = em.createNativeQuery("select Acctnature from accounttypemaster where acctcode='" + ftsPosting.getAccountCode(acctNo) + "'").getResultList();
            if (acNatureList.size() <= 0) {
                return "A/c Type Does Not Exist";
            }
            Vector acNatureVect = (Vector) acNatureList.get(0);
            String acNature = (String) acNatureVect.get(0);

            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List balList = em.createNativeQuery("SELECT ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) FROM ca_recon WHERE acno='" + acctNo + "'").getResultList();
                if (balList.size() > 0) {
                    Vector bal = (Vector) balList.get(0);
                    balance = Double.valueOf(bal.get(0).toString());
                }

            } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                List balListQuery = em.createNativeQuery("select ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) from td_recon where acno = '" + acctNo + "' and closeflag is NULL and trantype !=27").getResultList();

                if (balListQuery.size() > 0) {
                    Vector balVector = (Vector) balListQuery.get(0);

                    balance = Double.valueOf(balVector.get(0).toString());

                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                List balanceListQuery = em.createNativeQuery("select ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) from loan_recon where acno='" + acctNo + "'").getResultList();
                if (balanceListQuery.size() > 0) {
                    Vector balanceVector = (Vector) balanceListQuery.get(0);
                    balance = Double.valueOf(balanceVector.get(0).toString());
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                List balanceListQuery = em.createNativeQuery("select ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) from rdrecon where acno='" + acctNo + "'").getResultList();
                if (balanceListQuery.size() > 0) {
                    Vector balanceVector = (Vector) balanceListQuery.get(0);
                    balance = Double.valueOf(balanceVector.get(0).toString());
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                List balanceListQuery = em.createNativeQuery("select ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) from recon where acno='" + acctNo + "'").getResultList();
                if (balanceListQuery.size() > 0) {
                    Vector balanceVector = (Vector) balanceListQuery.get(0);
                    balance = Double.valueOf(balanceVector.get(0).toString());
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                List balanceListQuery = em.createNativeQuery("select ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) from ddstransaction where acno='" + acctNo + "'").getResultList();
                if (balanceListQuery.size() > 0) {
                    Vector balanceVector = (Vector) balanceListQuery.get(0);
                    balance = Double.valueOf(balanceVector.get(0).toString());
                }
            } else {
                List balanceListQuery = em.createNativeQuery("select ROUND(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) from gl_recon where acno='" + acctNo + "'").getResultList();
                if (balanceListQuery.size() > 0) {
                    Vector balanceVector = (Vector) balanceListQuery.get(0);
                    balance = Double.valueOf(balanceVector.get(0).toString());
                }
            }
            String finalBalance = Double.toString(balance);
            return finalBalance;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     * @param acctNo
     * @param amount
     * @param Userid
     * @return
     */
    public String checkBalance(String acctNo, double amount, String Userid) throws ApplicationException {

        try {
            double balance = 0;
            String brCode = ftsPosting.getCurrentBrnCode(acctNo);

            String tmpMsg = ftsGetBalRemote(acctNo);
            if (tmpMsg.equalsIgnoreCase("A/c Type Does Not Exist")) {
                return tmpMsg;
            } else {
                balance = Double.parseDouble(tmpMsg);
            }

            double lienAmount = 0;
            double limitAmount = 0;
            int acFlag = 0;

            String curDt = ymd.format(new Date());

            String acNature = ftsPosting.getAccountNature(acctNo);

//            String rs = ftsPosting.ftsDateValidate(curDt, brCode);
//            if (!rs.equalsIgnoreCase("True")) {
//                throw new ApplicationException(rs);
//            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {

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
                    } else {
                        return "Balance Exceeds";
                    }
                } else {
                    List lienamtListQuery = em.createNativeQuery("select ifnull(amount,0) from accountstatus where acno = '" + acctNo + "' and spno = "
                            + "(select max(spno) from accountstatus a where a.acno = '" + acctNo + "' and auth = 'Y')").getResultList();
                    if (lienamtListQuery.size() > 0) {
                        Vector lAmount = (Vector) lienamtListQuery.get(0);
                        lienAmount = Double.parseDouble(lAmount.get(0).toString());
                    }

                    if (Double.compare((amount + lienAmount), balance) > 0) {
                        return "Balance Exceeds The Lien Value " + lienAmount;
                    } else {
                        return "True";
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                    || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {

                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                        || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {

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
                        return "Balance Exceeds The Lien Value " + lienAmount;
                    } else {
                        return "true";
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *
     * @param acNo
     * @param ty
     * @param amt
     * @param bal
     * @param tranType
     * @param details
     * @param tokenNo
     * @param subTokenNo
     * @param instNo
     * @param instDt
     * @param payBy
     * @param vchNo
     * @param recNo
     * @param tranDesc
     * @param destBrCode
     * @param orgnBrCode
     * @param enterBy
     * @param authBy
     * @param trSNo
     * @return
     */
    public String cbsPostingSx(String acNo, Integer ty, String valueDt, double amt, double bal, Integer tranType, String details,
            Float tokenNo, String subTokenNo, String instNo, String instDt, Integer payBy,
            Float vchNo, Float recNo, Integer tranDesc, String destBrCode, String orgnBrCode,
            String enterBy, String authBy, Float trSNo, String adviceNo, String adviceBrnCode) throws ApplicationException {

        Date dt = null;
        String crGlHead = "";
        String drGlHead = "";
        String consoleGlHead = "";
        String instrDt = "";
        String orgnAlphaCode = "";
        String msg = "";
        String tdAcNo;
        String orgnIsoAcNo = "";
        int isoTy = 0;
        try {
            instNo = ftsPosting.lPading(instNo, 10, "0");
            dt = ymd.parse(ftsPosting.daybeginDate(orgnBrCode));

            if (amt == 0) {
                return "ERROR OCCURED: - AMOUNT CAN NOT BE ZERO";
            }

            if (payBy == 1 && (instNo.equalsIgnoreCase("0") || instNo.equalsIgnoreCase(null) || instNo.equalsIgnoreCase(""))) {
                return "ERROR OCCURED: - INST. NO. CAN NOT BE ZERO IN CASE OF CHEQUE";
            }

            if (payBy == 1 && (instDt.equalsIgnoreCase(null) || instDt.equalsIgnoreCase("") || instDt.length() < 8)) {
                return "ERROR OCCURED: - INST. DATE CAN NOT BE BLANK IN CASE OF CHEQUEOR SHOULD BE IN PROPER STATE";
            }

            if (acNo.equalsIgnoreCase(null) || acNo.equalsIgnoreCase("") || acNo.equalsIgnoreCase("0") || acNo.length() != 12) {
                return "ERROR OCCURED: - ACCOUNT NO. CAN NOT BE BLANK OR SHOULD BE IN PROPER STATE";
            }

            if (destBrCode.equalsIgnoreCase(null) || destBrCode.equalsIgnoreCase("") || destBrCode.trim().length() != 2) {
                return "ERROR OCCURED: - DESTINATION BRANCH CODE CAN NOT BE BLANK OR SHOULD BE IN PROPER STATE";
            }

            if (orgnBrCode.equalsIgnoreCase(null) || orgnBrCode.equalsIgnoreCase("") || orgnBrCode.trim().length() != 2) {
                return "ERROR OCCURED: - ORIGIN BRANCH CODE CAN NOT BE BLANK OR SHOULD BE IN PROPER STATE";
            }

            if (ty < 0 || ty > 1) {
                return "ERROR OCCURED: - TY SHOULD BE IN 0 OR 1 !";
            }

            if (tranType < 0 || tranType > 2) {
                return "ERROR OCCURED: - INVALID VALUE FOR TRANTYPE (" + tranType + ") !";
            }

            if (enterBy.equalsIgnoreCase(null) || enterBy.equalsIgnoreCase("")) {
                return "ERROR OCCURED: - ENTER BY FIELD CAN NOT BE BLANK !";
            }

            List paramList = ftsPosting.isModuleActiveBasedOnAcCode(ftsPosting.getAccountCode(acNo));
            if (!paramList.isEmpty()) {
                Vector paramVector = (Vector) paramList.get(0);
                String modFlag = paramVector.get(1).toString();
                if (modFlag.equalsIgnoreCase("N") && tranDesc == 66) {
                    return "Transaction is not allowed for this type of account.";
                }
            }

            if ((instDt == null) || instDt.equalsIgnoreCase("")) {
                instrDt = null;
            } else {
                try {
                    instrDt = ymmd.format(ymmd.parse(instDt));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            List consoleGlHeadList = em.createNativeQuery("SELECT ACNO FROM abb_parameter_info WHERE PURPOSE = 'INTERSOLE ACCOUNT'").getResultList();
            if (consoleGlHeadList.size() > 0) {
                Vector consoleGlHeadVect = (Vector) consoleGlHeadList.get(0);
                consoleGlHead = ftsPosting.getCurrentBrnCode(acNo).concat(consoleGlHeadVect.get(0).toString());
                orgnIsoAcNo = orgnBrCode.concat(consoleGlHeadVect.get(0).toString());
            } else {
                return "ERROR OCCURED: - Please enter CASH IN HAND GL Head!";
            }
            float crRecNo = 0;
            float drRecNo = 0;

            if (ty == 0) {
                crGlHead = acNo;
                drGlHead = consoleGlHead;
                crRecNo = recNo;
                drRecNo = ftsPosting.getRecNo();
                isoTy = 0;
            } else if (ty == 1) {
                crGlHead = consoleGlHead;
                drGlHead = acNo;
                drRecNo = recNo;
                crRecNo = ftsPosting.getRecNo();
                isoTy = 1;
            }

            List orgnAlphaCodeList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '" + Integer.parseInt(orgnBrCode) + "'").getResultList();
            if (orgnAlphaCodeList.size() > 0) {
                Vector orgnAlphaCodeVect = (Vector) orgnAlphaCodeList.get(0);
                orgnAlphaCode = orgnAlphaCodeVect.get(0).toString();
            } else {
                return "ERROR OCCURED: - Please enter ALPHA CODE for Orign branch!";
            }

            tdAcNo = "0";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(dt);
            details = "AT " + orgnAlphaCode + ": " + details;
            /**
             * **Added by Dinesh for A/c Statement Charges***
             */
            Integer tempTrandesc = tranDesc;
            if (tranDesc == 65) {
                tranDesc = 0;
            }

            msg = ftsPosting43CBS(crGlHead, tranType, 0, amt, date, valueDt, enterBy, orgnBrCode, destBrCode, tranDesc, details, trSNo, crRecNo, 0, "", "Y", authBy, "", payBy,
                    instNo, instrDt, tdAcNo, 0f, "", "", "", "", tokenNo, "S", adviceNo, adviceBrnCode);
            System.out.println("msg 1111:=======" + msg);
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                return msg;
            }

            /**
             * **Added by Dinesh for A/c Statement Charges***
             */
            if (tempTrandesc == 65) {
                tranDesc = tempTrandesc;
            }

            /**
             * ***** end here***
             */
            msg = ftsPosting43CBS(drGlHead, tranType, 1, amt, date, valueDt, enterBy, orgnBrCode, destBrCode, tranDesc, details,
                    Float.parseFloat(trSNo.toString()), drRecNo, 0, "", "Y", authBy, "", payBy, instNo, instrDt, tdAcNo, 0f, "", "", "", "", tokenNo, "S", adviceNo, adviceBrnCode);
            System.out.println("msg 2222:=======" + msg);
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                return msg;
            }

            if (tranType == 2) {
                recNo = ftsPosting.getRecNo();
                /**
                 * **Added by Dinesh for A/c Statement Charges***
                 */
                if (tranDesc == 65) {
                    tranDesc = 0;
                }

                /**
                 * ***** end here***
                 */
                msg = ftsPosting43CBS(orgnIsoAcNo, tranType, isoTy, amt, date, valueDt, enterBy, orgnBrCode, destBrCode, tranDesc, details,
                        Float.parseFloat(trSNo.toString()), recNo, 0, "", "Y", authBy, "", payBy, instNo, instrDt, tdAcNo, 0f, "", "", "", "", tokenNo, "C", adviceNo, adviceBrnCode);
                System.out.println("msg 333:=======" + msg);
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    return msg;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    /**
     *
     * @param acNo
     * @param ty
     * @param amt
     * @param bal
     * @param tranType
     * @param details
     * @param tokenNo
     * @param subTokenNo
     * @param instNo
     * @param instDt
     * @param payBy
     * @param vchNo
     * @param recNo
     * @param tranDesc
     * @param destBrCode
     * @param orgnBrCode
     * @param enterBy
     * @param authBy
     * @param trSNo
     * @return
     */
    public String cbsPostingCx(String acNo, Integer ty, String valueDt, double amt, double bal, Integer tranType, String details,
            Float tokenNo, String subTokenNo, String instNo, String instDt, Integer payBy,
            Float vchNo, Float recNo, Integer tranDesc, String destBrCode, String orgnBrCode,
            String enterBy, String authBy, Float trSNo, String adviceNo, String adviceBrnCode) throws ApplicationException {

        Date dt = null;
        String instrDt = "";
        String msg = "";
        try {
            instNo = ftsPosting.lPading(instNo, 10, "0");
            dt = ymd.parse(ftsPosting.daybeginDate(orgnBrCode));

            if (amt == 0) {
                return "ERROR OCCURED: - AMOUNT CAN NOT BE ZERO";
            }

            if (payBy == 1 && (instNo.equalsIgnoreCase("0") || instNo.equalsIgnoreCase(null) || instNo.equalsIgnoreCase(""))) {
                return "ERROR OCCURED: - INST. NO. CAN NOT BE ZERO IN CASE OF CHEQUE";
            }

            if (payBy == 1 && (instDt.equalsIgnoreCase(null) || instDt.equalsIgnoreCase("") || instDt.length() < 8)) {
                return "ERROR OCCURED: - INST. DATE CAN NOT BE BLANK IN CASE OF CHEQUEOR SHOULD BE IN PROPER STATE";
            }

            if (acNo.equalsIgnoreCase(null) || acNo.equalsIgnoreCase("") || acNo.equalsIgnoreCase("0") || acNo.length() != 12) {
                return "ERROR OCCURED: - ACCOUNT NO. CAN NOT BE BLANK OR SHOULD BE IN PROPER STATE";
            }

            if (destBrCode.equalsIgnoreCase(null) || destBrCode.equalsIgnoreCase("") || destBrCode.trim().length() != 2) {
                return "ERROR OCCURED: - DESTINATION BRANCH CODE CAN NOT BE BLANK OR SHOULD BE IN PROPER STATE";
            }

            if (orgnBrCode.equalsIgnoreCase(null) || orgnBrCode.equalsIgnoreCase("") || orgnBrCode.trim().length() != 2) {
                return "ERROR OCCURED: - ORIGIN BRANCH CODE CAN NOT BE BLANK OR SHOULD BE IN PROPER STATE";
            }

            if (ty < 0 || ty > 1) {
                return "ERROR OCCURED: - TY SHOULD BE IN 0 OR 1 !";
            }

            if (tranType < 0 || tranType > 2) {
                return "ERROR OCCURED: - INVALID VALUE FOR TRANTYPE (" + tranType + ") !";
            }

            if (enterBy.equalsIgnoreCase(null) || enterBy.equalsIgnoreCase("")) {
                return "ERROR OCCURED: - ENTER BY FIELD CAN NOT BE BLANK !";
            }

            if ((instDt == null) || instDt.equalsIgnoreCase("")) {
                instrDt = null;
            } else {
                try {
                    instrDt = ymmd.format(ymmd.parse(instDt));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            String consoleGlHead;
            List consoleGlHeadList = em.createNativeQuery("SELECT ACNO FROM abb_parameter_info WHERE PURPOSE = 'INTERSOLE ACCOUNT'").getResultList();
            if (consoleGlHeadList.size() > 0) {
                Vector consoleGlHeadVect = (Vector) consoleGlHeadList.get(0);
                consoleGlHead = orgnBrCode.concat(consoleGlHeadVect.get(0).toString());
            } else {
                return "ERROR OCCURED: - Please enter CASH IN HAND GL Head!";
            }
            String tdAcNo = "0";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(dt);
            if (tranType == 0) {
                recNo = ftsPosting.getRecNo();
                msg = ftsPosting43CBS(consoleGlHead, tranType, ty, amt, date, valueDt, enterBy, orgnBrCode, destBrCode, tranDesc, details, Float.parseFloat(trSNo.toString()),
                        recNo, 0, "", "Y", authBy, "", payBy, instNo, instrDt, tdAcNo, 0f, "", "", "", "", tokenNo, "C", adviceNo, adviceBrnCode);

                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    return msg;
                }

            } else {
                if (recNo == null || recNo == 0) {
                    recNo = ftsPosting.getRecNo();
                }
                msg = ftsPosting43CBS(acNo, tranType, ty, amt, date, valueDt, enterBy, orgnBrCode, destBrCode, tranDesc, details, trSNo, recNo,
                        0, "", "Y", authBy, "", payBy, instNo, instrDt, tdAcNo, 0f, "", "", "", "", tokenNo, "C", adviceNo, adviceBrnCode);

                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    return msg;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String fnGetLoanStatus(String acno, String dt) throws ApplicationException {
        String status = null;
        try {
            List list = em.createNativeQuery("Select acno from loan_appparameter where acno='" + acno + "'").getResultList();
            if (list.size() <= 0) {
                status = "Record not found";
            }

            List list1 = em.createNativeQuery("Select c.description from accountstatus a,codebook c where acno='" + acno + "' "
                    + "and effdt=(Select max(Effdt) from accountstatus where EffDt<='" + dt + "' and acno='" + acno + "'"
                    + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and EffDt<='" + dt + "' "
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and EffDt<='" + dt + "' and (spflag in (11,12,13,14,3,6,7,8,2) or "
                    + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))"
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ").getResultList();

            if (list1.isEmpty()) {
                status = "STANDARD";
            } else {
                Vector element = (Vector) list1.get(0);
                status = element.get(0).toString();
                status = status.substring(0, 3).toUpperCase();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return status;
    }

    public String fnGetLoanStatusTillDate(String acno, String dt) throws ApplicationException {
        String status = null;
        try {
            List list = em.createNativeQuery("Select acno from loan_appparameter where acno='" + acno + "'").getResultList();
            if (list.size() <= 0) {
                status = "Record not found";
            }

            List list1 = em.createNativeQuery("Select c.description from accountstatus a,codebook c where acno='" + acno + "' "
                    + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + dt + "' and acno='" + acno + "'"
                    + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<='" + dt + "' "
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<='" + dt + "' and (spflag in (11,12,13,14,3,6,7,8,2) or "
                    + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))"
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ").getResultList();

            if (list1.isEmpty()) {
                status = "STANDARD";
            } else {
                Vector element = (Vector) list1.get(0);
                status = element.get(0).toString();
                status = status.substring(0, 3).toUpperCase();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return status;
    }

    public String fnGetLoanStatusOfAccountTillDate(String acno, String dt) throws ApplicationException {
        String status = null;
        try {
            List list = em.createNativeQuery("Select acno from loan_appparameter where acno='" + acno + "'").getResultList();
            if (list.size() <= 0) {
                status = "Record not found";
            }

            List list1 = em.createNativeQuery("Select c.description from accountstatus a,codebook c where acno='" + acno + "' "
                    + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + dt + "' and acno='" + acno + "'"
                    + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<='" + dt + "' "
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d')<='" + dt + "' and (spflag in (11,12,13,14,3,6,7,8,2) or "
                    + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))"
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ").getResultList();

            if (list1.isEmpty()) {
                status = "STANDARD";
            } else {
                Vector element = (Vector) list1.get(0);
                status = element.get(0).toString().toUpperCase();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return status;
    }

    public String fnGetLoanStatusBetweenDate(String acno, String fromDt, String toDt) throws ApplicationException {
        String status = null;
        try {
            List list = em.createNativeQuery("Select acno from loan_appparameter where acno='" + acno + "'").getResultList();
            if (list.size() <= 0) {
                status = "Record not found";
            }

            List list1 = em.createNativeQuery("Select c.description from accountstatus a,codebook c where acno='" + acno + "' "
                    + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "' and acno='" + acno + "'"
                    + " and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "' "
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) "
                    + "and spno=(Select max(Spno) from accountstatus where acno='" + acno + "' and date_format(EffDt,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "' and (spflag in (11,12,13,14,3,6,7,8,2) or "
                    + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))"
                    + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) "
                    + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 ").getResultList();

            if (list1.isEmpty()) {
                status = fnGetLoanStatusTillDate(acno, toDt);
            } else {
                Vector element = (Vector) list1.get(0);
                status = element.get(0).toString();
                status = status.substring(0, 3).toUpperCase();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return status;
    }

    public String npaRecoveryAmtUpdation(String acno, String dt, double crAmt, String enterBy, String orgnBrCode, String destBrCode, String valueDate, float trsNo) throws ApplicationException {
        try {
            /**
             * ***********************************************************************************
             * urihead(GLHEADURI) means Interest Accrued Due On NPA
             * oirHead(glheadname) means Overdue Interest Reserve
             * ************************************************************************************
             */
            String accStatus = fnGetLoanStatus(acno, dt);
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
                        String interestAcno = brncode + intGl + "01";
                        Query q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                + "('" + interestAcno + "',0.0,'" + dt + "','" + valueDate + "',0,ABS(" + mem + "),0,2," + recNo1 + "," + trsNo + ","
                                + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "','','')");
                        q4.executeUpdate();
                        msg = ftsPosting.updateBalance("PO", brncode + intGl + "01", Math.abs(mem), 0, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }

                        Query q5 = em.createNativeQuery("INSERT INTO npa_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY,INTAMT, ORG_BRNID, DEST_BRNID) VALUES "
                                + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "',0,ABS(" + mem + "),0,8," + recNo1 + "," + trsNo + ","
                                + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "'," + crAmt + ",'" + orgnBrCode + "','" + destBrCode + "')");
                        q5.executeUpdate();
//                    msg = ftsPosting.updateBalance(accNature, acno, Math.abs(mem), 0, "Y", "N");
//                    if (!msg.equalsIgnoreCase("TRUE")) {
//                        throw new ApplicationException(msg);
//                    }

                        /**
                         * **********************************************************************************************************************************
                         * Code for contra Entry As per Document given by Mr.
                         * Manab Jordar. Work done by Dhirendra
                         * Singh************************************
                         * *********************************************************************************************************************************
                         */
                        if (!oirHead.equals("") && !uriGl.equals("")) {
                            recNo = ftsPosting.getRecNo();
                            String oHead = brncode + oirHead + "01";
                            q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                    + "('" + oHead + "',0.0,'" + dt + "','" + valueDate + "',ABS(" + mem + "),0,1,2," + recNo + "," + trsNo + ","
                                    + "3,0,3,'Int. Recovered from " + acno + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "','','')");
                            q4.executeUpdate();
                            msg = ftsPosting.updateBalance("PO", brncode + oirHead + "01", 0, Math.abs(mem), "Y", "N");
                            if (!msg.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(msg);
                            }

                            recNo = ftsPosting.getRecNo();
                            oHead = brncode + uriGl + "01";
                            q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                    + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                    + "('" + oHead + "',0.0,'" + dt + "','" + valueDate + "',0,ABS(" + mem + "),0,2," + recNo + "," + trsNo + ","
                                    + "3,0,3,'Int. Recovered from " + acno + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "','','')");
                            q4.executeUpdate();

                            msg = ftsPosting.updateBalance("PO", brncode + uriGl + "01", Math.abs(mem), 0, "Y", "N");
                            if (!msg.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(msg);
                            }
                        }
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
                                Double outstanding = commonReport.getBalanceOnDate(acno, dt);//Ouststanding includes credit amount
                                if (npaPOrdChk(acno).equalsIgnoreCase("true")) {
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
                                                if (ymd.parse(maxDueDT).compareTo(dt.length() == 8 ? ymd.parse(dt) : ymmd.parse(dt)) <= 0) {
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
                                            installAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  as dramt from " + commonReport.getTableName(accNature) + " where acno =  '" + acno + "' and dt<='" + dt + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ").getResultList();
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
                                                if (ymd.parse(maxDueDT).compareTo(dt.length() == 8 ? ymd.parse(dt) : ymmd.parse(dt)) <= 0) {  // It was previos on date 21/10/2016--compareTo(ymmd.parse(dt))
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
                                if (npaPOrdChk(acno).equalsIgnoreCase("true")) {
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
                                } else if (npaPOrdChk(acno).equalsIgnoreCase("false")) {
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

                    String interestAcno = brncode + intGl + "01";
                    Query q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                            + "('" + interestAcno + "',0.0,'" + dt + "','" + valueDate + "',0," + crAmt + ",0,2," + recNo + "," + trsNo + ","
                            + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "','','')");
                    q4.executeUpdate();
                    msg = ftsPosting.updateBalance("PO", brncode + intGl + "01", crAmt, 0, "Y", "N");
                    if (!msg.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                    Query q5 = em.createNativeQuery("INSERT INTO npa_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY,INTAMT, ORG_BRNID, DEST_BRNID) VALUES "
                            + "('" + acno + "',0.0,'" + dt + "','" + valueDate + "',0," + crAmt + ",0,8," + recNo + "," + trsNo + ","
                            + "3,0,3,'INTT. REC FOR MEM',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "'," + crAmt + ",'" + orgnBrCode + "','" + destBrCode + "')");
                    q5.executeUpdate();
//                    msg = ftsPosting.updateBalance(accNature, acno, crAmt, 0, "Y", "N");
//                    if (!msg.equalsIgnoreCase("TRUE")) {
//                        throw new ApplicationException(msg);
//                    }
                    /**
                     * **********************************************************************************************************************************
                     * Code for contra Entry As per Document given by Mr. Manab
                     * Jordar. Work done by Dhirendra
                     * Singh************************************
                     * *********************************************************************************************************************************
                     */
                    if (!oirHead.equals("") && !uriGl.equals("")) {
                        recNo = ftsPosting.getRecNo();
                        String oHead = brncode + oirHead + "01";
                        q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                + "('" + oHead + "',0.0,'" + dt + "','" + valueDate + "'," + crAmt + ",0,1,2," + recNo + "," + trsNo + ","
                                + "3,0,3,'Int. Recovered from " + acno + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "','','')");
                        q4.executeUpdate();
                        msg = ftsPosting.updateBalance("PO", brncode + oirHead + "01", 0, crAmt, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }
                        recNo = ftsPosting.getRecNo();
                        oHead = brncode + uriGl + "01";
                        q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                + "('" + oHead + "',0.0,'" + dt + "','" + valueDate + "',0," + crAmt + ",0,2," + recNo + "," + trsNo + ","
                                + "3,0,3,'Int. Recovered from " + acno + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + enterBy + "','" + orgnBrCode + "','" + destBrCode + "','','')");
                        q4.executeUpdate();

                        msg = ftsPosting.updateBalance("PO", brncode + uriGl + "01", crAmt, 0, "Y", "N");
                        if (!msg.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(msg);
                        }
                    }
                    /**
                     * *************************************************************************************************************************************
                     */
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());

        }
        return "True";
    }

    public String npaPOrdChk(String acNo) throws com.cbs.exception.ApplicationException {
        try {
            String msg = "true";
            List sChkList = em.createNativeQuery("select ifnull(recover,0) from loan_appparameter where acno = '" + acNo + "'").getResultList();
            if (sChkList.isEmpty()) {
                msg = "true";
            } else {
                Vector vChk = (Vector) sChkList.get(0);
                if (vChk.get(0).toString().equalsIgnoreCase("PIC")) {
                    msg = "false";
                } else {
                    msg = "true";
                }
            }
            return msg;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String insertIntoNeftStatusTable(String alertMsg, String process, String neftBankName,
            Float trsNo, String user, ExcelReaderPojo pojo) throws ApplicationException {
        String status = "", details = "", reason = "";
        try {
            if (alertMsg.contains(":")) {
                status = "Mismatch";
                details = alertMsg.substring(alertMsg.indexOf(":") + 1);
                reason = alertMsg.substring(0, 4).equalsIgnoreCase("ifsc") ? "IFSC CODE NOT CORRECT."
                        : "CUSTOMER NAME IS INCORRECT.";
            } else if (alertMsg.equalsIgnoreCase("duplicate")) {
                status = "Unsuccess";
                reason = "THIS UTR ALREADY PROCESSED.";
            } else if (alertMsg.equalsIgnoreCase("success")) {
                status = "Success";
            } else if (alertMsg.equalsIgnoreCase("no mapped account")) {
                status = "Unsuccess";
                reason = "ACNO IS NOT IN PROPER STATE.";
            } else {
                status = "Unsuccess";
                reason = alertMsg;
            }

            String txnType = stripLeadingAndTrailingQuotes(pojo.getTxnType());
            String utr = stripLeadingAndTrailingQuotes(pojo.getUtr());
            String senderName = stripLeadingAndTrailingQuotes(pojo.getSenderName());
            String narration = txnType + "-" + utr + "-" + senderName;

            String headerUtr = (pojo.getHeaderUtr() == null || pojo.getHeaderUtr().equals("")) ? "" : pojo.getHeaderUtr();
            String batchTime = (pojo.getBatchTime() == null || pojo.getBatchTime().equals("")) ? "" : pojo.getBatchTime();
            String relatedRefNo = (pojo.getRelatedRefNo() == null || pojo.getRelatedRefNo().equals("")) ? "" : pojo.getRelatedRefNo();
            String senderAcType = (pojo.getSenderAcype() == null || pojo.getSenderAcype().equals("")) ? "" : pojo.getSenderAcype();
            String beneficiaryAcType = (pojo.getBeneficiaryAcType() == null || pojo.getBeneficiaryAcType().equals("")) ? "" : pojo.getBeneficiaryAcType();
            String rejectReason = (pojo.getRejectReason() == null || pojo.getRejectReason().equals("")) ? "" : pojo.getRejectReason();
            String remittanceDt = (pojo.getRemittanceDate() == null || pojo.getRemittanceDate().equals("")) ? "19000101" : pojo.getRemittanceDate();
            String senderAddress = (pojo.getSenderAddOne() == null || pojo.getSenderAddOne().equals("")) ? "" : pojo.getSenderAddOne();
            String returnTranRefNo = (pojo.getReturnTranRefNo() == null || pojo.getReturnTranRefNo().equals("")) ? "" : pojo.getReturnTranRefNo();
            String iwFileName = (pojo.getIwFileName() == null || pojo.getIwFileName().trim().equals("")) ? "" : pojo.getIwFileName();

            int n = em.createNativeQuery("insert into neft_rtgs_status(utr,bene_account,bene_name,"
                    + "receiver_ifsc,amount,sender_account,sender_name,sender_ifsc,txn_type,narration,"
                    + "status,reason,details,dt,tran_time,enter_by,auth_by,value_dt,receiver_bank_name,"
                    + "receiver_bank_code,receiver_bank_address,sponsor_bank_name,sponsor_bank_code,"
                    + "sponsor_ifsc,sponsor_ref_no,sponsor_address,sender_bank_name,sender_bank_code,"
                    + "reason_code,bene_add,remittance_info,remittance_originator,process,neft_bank_name,"
                    + "trs_no,header_utr,batch_time,related_ref_no,sender_actype,bene_actype,reject_reason,"
                    + "remittance_date,sender_address,return_tran_ref_no,iw_file_name) values('" + utr + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getBeneAccount()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getBeneName()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getReceiverIfsc()) + "',"
                    + "" + pojo.getAmount() + ",'" + stripLeadingAndTrailingQuotes(pojo.getSenderAcc()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSenderName()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSenderIfsc()) + "','" + txnType + "',"
                    + "'" + narration + "','" + status + "','" + reason + "','" + details + "',"
                    + "'" + ymd.format(new Date()) + "',now(),'" + user + "','" + user + "',"
                    + "'" + ymd.format(ymdhh.parse(stripLeadingAndTrailingQuotes(pojo.getTimestamp()))) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getReceiverBankName()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getReceiverBankCode()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getReceiverBankAddress()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSponsorBankName()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSponsorBankCode()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSponsorIfsc()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSponsorRefNo()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSponsorAddress()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSenderBankName()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getSenderBankCode()) + "','',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getBeneAdd()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getRemitInfo()) + "',"
                    + "'" + stripLeadingAndTrailingQuotes(pojo.getRemittanceOriginator()) + "',"
                    + "'" + process + "','" + neftBankName + "'," + trsNo + ",'" + headerUtr + "',"
                    + "'" + batchTime + "','" + relatedRefNo + "','" + senderAcType + "',"
                    + "'" + beneficiaryAcType + "','" + rejectReason + "','" + remittanceDt + "',"
                    + "'" + senderAddress + "','" + returnTranRefNo + "','" + iwFileName + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Neft Status Table Insertion->>>>>"
                        + stripLeadingAndTrailingQuotes(pojo.getBeneAccount()));
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    public String insertNeftBulkTransaction(String process, String neftBankName, Float trsNo,
            String user, String orgnBrCode, ExcelReaderPojo pojo, String isoAccount,
            String orgnAlphaCode) throws ApplicationException {
        String consoleGlHead = "", orgnIsoAcNo = "", partyAc = "";
        Float recNo = 0f;
        try {
            partyAc = stripLeadingAndTrailingQuotes(pojo.getBeneAccount());

            consoleGlHead = partyAc.substring(0, 2).concat(isoAccount);
            orgnIsoAcNo = orgnBrCode.concat(isoAccount);

            String txnType = stripLeadingAndTrailingQuotes(pojo.getTxnType());
            String utr = stripLeadingAndTrailingQuotes(pojo.getUtr());
            String senderName = stripLeadingAndTrailingQuotes(pojo.getSenderName());
            String narration = txnType + "-" + utr + "-" + senderName;
            String valueDt = ymd.format(ymdhh.parse(stripLeadingAndTrailingQuotes(pojo.getTimestamp())));

            narration = "AT " + orgnAlphaCode + ": " + narration;

            List dataList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '"
                    + partyAc.substring(2, 4) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }
            Vector dataVec = (Vector) dataList.get(0);
            String partyNature = dataVec.get(0).toString();

            String partyBrCode = partyAc.substring(0, 2);
            //Party A/c
            recNo = ftsPosting.getRecNo();
            //String businessDt = (pojo.getTranDate() != null && !pojo.getTranDate().equals("")) ? pojo.getTranDate(): ymd.format(new Date());
            String businessDt = commonReport.getBusinessDate();
            String result = ftsPosting.insertRecons(partyNature, partyAc, 0, pojo.getAmount().doubleValue(),
                    businessDt, valueDt, 2, narration, user, trsNo, "",
                    recNo, "Y", user, 66, 3, "", "", 0f, "", "A", 0,
                    "", 0f, "", null, orgnBrCode, partyBrCode, 0, "", "", "");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + partyAc);
            }

            result = ftsPosting.updateBalance(partyNature, partyAc, pojo.getAmount().doubleValue(), 0, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Balance Updation Problem For A/c No-->" + partyAc);
            }

            //Party Branch ISO.
            recNo = ftsPosting.getRecNo();
            int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + consoleGlHead + "',1,'" + businessDt + "',"
                    + "'" + valueDt + "'," + pojo.getAmount().doubleValue() + ",0, 0,2,'" + narration + "',"
                    + "0,'','19000101','" + user + "','Y'," + recNo + ",3,'" + user + "',"
                    + "" + trsNo + ",66,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }

            //Origin Branch ISO.
            n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + orgnIsoAcNo + "',0,'" + businessDt + "',"
                    + "'" + valueDt + "',0," + pojo.getAmount().doubleValue() + ", 0,2,'" + narration + "',"
                    + "0,'','19000101','" + user + "','Y'," + ftsPosting.getRecNo() + ",3,'" + user + "',"
                    + "" + trsNo + ",66,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }
            //Emi updation if exists for that a/c
            if (partyNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    || partyNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || partyNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                int intPostOnDeposit = ftsPosting.getCodeForReportName("INT_POST_DEPOSIT");
                if (intPostOnDeposit == 0) {
                    String msg = ftsPosting.npaRecoveryUpdation(trsNo, partyNature, partyAc, businessDt, pojo.getAmount().doubleValue(), orgnBrCode, partyBrCode, user);
                    if (!msg.equalsIgnoreCase("True")) {
                        throw new ApplicationException(msg);
                    }
                }
                dataList = em.createNativeQuery("select acno from emidetails where "
                        + "acno='" + partyAc + "' and status='Unpaid'").getResultList();
                if (!dataList.isEmpty()) {
                    result = ftsPosting.loanDisbursementInstallment(partyAc, pojo.getAmount().doubleValue(), 0,
                            "System", businessDt, ftsPosting.getRecNo(), 1, "Through Neft");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Emi details updation issue-->" + result);
                    }
                }
            }
            //Emd Here
            //Success Neft Status Insertion.
            result = insertIntoNeftStatusTable("Success", process, neftBankName, trsNo, user, pojo);
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Insertion Problem In Inward Neft Status-->" + pojo.getBeneAccount());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    public String stripLeadingAndTrailingQuotes(String str) {
        str = str.trim();
        if (str.startsWith("'")) {
            str = str.substring(1, str.length());
        }
        if (str.endsWith("'")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String insertDividendTxn(Float trsNo, float recNo, String user, String orgnBrCode, DividendTable pojo, String isoAccount,
            String orgnAlphaCode, String details) throws ApplicationException {
        String consoleGlHead = "", orgnIsoAcNo = "", partyAc = "";
        // Float recNo = 0f;
        try {
            partyAc = pojo.getBeneAcno();

            consoleGlHead = partyAc.substring(0, 2).concat(isoAccount);
            orgnIsoAcNo = orgnBrCode.concat(isoAccount);

            String narration = "AT " + orgnAlphaCode + ": " + details;

            List dataList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '"
                    + partyAc.substring(2, 4) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }
            Vector dataVec = (Vector) dataList.get(0);
            String partyNature = dataVec.get(0).toString();

            String partyBrCode = partyAc.substring(0, 2);
            //Party A/c
            //recNo = ftsPosting.getRecNo();
            String table = "accountmaster";

            if (partyNature.equalsIgnoreCase(CbsConstant.MS_AC) || partyNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                table = "td_accountmaster";
            }

            List statusList = em.createNativeQuery("select accstatus from " + table + " where acno = '" + partyAc + "'").getResultList();
            if (statusList.isEmpty()) {
                throw new ApplicationException("Account does not exist");
            }
            Vector dataVect = (Vector) statusList.get(0);
            String accstatus = dataVect.get(0).toString();
            if (accstatus.equals("9")) {
                return "Account has been closed";
            }

            String result = ftsPosting.insertRecons(partyNature, partyAc, 0, pojo.getDivamt(),
                    ymd.format(new Date()), ymd.format(new Date()), 2, narration, user, trsNo, "",
                    recNo, "Y", user, 110, 3, "", "", 0f, "", "A", 0,
                    "", 0f, "", null, orgnBrCode, partyBrCode, 0, "", "", "");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + partyAc);
            }
            
            result = ftsPosting.updateBalance(partyNature, partyAc, pojo.getDivamt(), 0, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Balance Updation Problem For A/c No-->" + partyAc);
            }
            if (partyNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || partyNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || partyNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                if (accstatus.equals("11") || accstatus.equals("12") || accstatus.equals("13")) {
                    result = ftsPosting.npaRecoveryUpdation(trsNo, partyNature, partyAc, ymd.format(new Date()), pojo.getDivamt(), orgnBrCode, partyBrCode, user);
                    if (!result.equalsIgnoreCase("True")) {
                        throw new ApplicationException(result);
                    }
                }
                if (partyNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    result = ftsPosting.loanDisbursementInstallment(partyAc, pojo.getDivamt(), 0, user, ymd.format(new Date()), recNo, 1, "");
                    if (!result.equalsIgnoreCase("TRUE")) {
                        return result;
                    }
                }
            }
            
            ftsPosting.lastTxnDateUpdation(partyAc);
            //Party Branch ISO.
            recNo = recNo + 1;
            int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + consoleGlHead + "',1,'" + ymd.format(new Date()) + "',"
                    + "'" + ymd.format(new Date()) + "'," + pojo.getDivamt() + ",0, 0,2,'" + narration + "',"
                    + "0,'','19000101','" + user + "','Y'," + recNo + ",3,'" + user + "',"
                    + "" + trsNo + ",110,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }

            //Origin Branch ISO.
            recNo = recNo + 1;
            n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + orgnIsoAcNo + "',0,'" + ymd.format(new Date()) + "',"
                    + "'" + ymd.format(new Date()) + "',0," + pojo.getDivamt().doubleValue() + ", 0,2,'" + narration + "',"
                    + "0,'','19000101','" + user + "','Y'," + recNo + ",3,'" + user + "',"
                    + "" + trsNo + ",110,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    public Map<String, Double> getTaxComponent(Double chargeAmount, String applicableDt) throws ApplicationException {
//        NumberFormat formattter = new DecimalFormat("#.##");
        Map<String, Double> map = new HashMap<String, Double>();

        List list = em.createNativeQuery("select component_base,component_type,des,rot,glhead,roundupto "
                + "from taxmaster where applicabledt=(select max(applicabledt) from taxmaster where "
                + "applicableflag='Y' and applicabledt<='" + applicableDt + "')").getResultList();
        if (list.isEmpty()) {
            throw new ApplicationException("Please define tax in tax master.");
        }
        Vector vec = (Vector) list.get(0);
        if (vec.get(0) == null || vec.get(0).toString().trim().equals("")) {
            throw new ApplicationException("Please define component base in tax master.");
        }
        String componentBase = vec.get(0).toString().trim();
        String componentType = vec.get(1).toString().trim();

        int roundUpTO = Integer.parseInt(vec.get(5).toString().trim());
        String taxHead = vec.get(4).toString().trim();

        if (componentBase.equalsIgnoreCase("BC")) { //Both(dr and cr) Club Entry
            if (vec.get(2) == null || vec.get(2).toString().trim().equals("")) {
                throw new ApplicationException("Please define description in tax master.");
            }
            String description = vec.get(2).toString().trim();
            double rot = Double.parseDouble(vec.get(3).toString().trim());
            if (vec.get(4) == null || vec.get(4).toString().trim().equals("")
                    || vec.get(4).toString().trim().length() != 8) {
                throw new ApplicationException("Please fill proper gl head in tax master.");
            }

            //Tax Calculation
            double tax = (chargeAmount * rot) / 100;
            tax = CbsUtil.round(tax, roundUpTO);
            //Setting In Map
            map.put(description + ":" + taxHead + "01" + ":" + componentBase + ":R", tax);
            return map;
        } else {
            if (componentType == null || componentType.equals("") || !componentType.contains(":")) { //At least there will be two component.
                throw new ApplicationException("Please define proper component type in tax master.");
            }
            List componentTypeList = em.createNativeQuery("select charge_name,amt,cr_gl_head from "
                    + "cbs_charge_detail where charge_type='SERVICE-TAX'").getResultList();
            if (componentTypeList.isEmpty()) {
                throw new ApplicationException("Please define tax component in cbs charge detail.");
            }
            //Break the component
            String[] arr = componentType.split(":");    //Component type will be separated by ":"
            if (arr.length != componentTypeList.size()) {
                throw new ApplicationException("Please define equal no of component type "
                        + "in tax master and cbs charge detail.");
            }
            //Checking of all component and tax calculation
            for (int i = 0; i < arr.length; i++) {
                int index = -1;
                for (int j = 0; j < componentTypeList.size(); j++) {
                    Vector ele = (Vector) componentTypeList.get(j);
                    if (ele.get(0).toString().trim().equalsIgnoreCase(arr[i])) {
                        index = 0;
                        //Validation
                        if (ele.get(2) == null || ele.get(2).toString().trim().equals("")
                                || ele.get(2).toString().trim().length() != 10) {
                            throw new ApplicationException("Please define proper glhead of length 10 "
                                    + "in cbs charge detail for " + ele.get(0).toString().trim());
                        }
                        //Tax Calculation
                        double tax = (chargeAmount * Double.parseDouble(ele.get(1).toString().trim())) / 100;
                        tax = CbsUtil.round(tax, roundUpTO);
                        if (ele.get(2).toString().trim().equals(taxHead + "01")) {
                            map.put(ele.get(0).toString().trim() + ":" + ele.get(2).toString().trim() + ":" + componentBase + ":R", tax);
                            break;
                        } else {
                            map.put(ele.get(0).toString().trim() + ":" + ele.get(2).toString().trim() + ":" + componentBase + ":S", tax);
                            break;
                        }
                    }
                }
                if (index == -1) {
                    throw new ApplicationException("Please define equal no of component type "
                            + "in tax master and cbs charge detail. Component type and charge name should be same.");
                }
            }
            return map;
        }
    }

    public Map<String, Double> getIgstTaxComponent(Double chargeAmount, String applicableDt) throws ApplicationException {
//        NumberFormat formattter = new DecimalFormat("#.##");
        Map<String, Double> map = new HashMap<String, Double>();

        List list = em.createNativeQuery("select component_base,component_type,des,rot,glhead,roundupto "
                + "from taxmaster where applicabledt=(select max(applicabledt) from taxmaster where "
                + "applicableflag='Y' and applicabledt<='" + applicableDt + "')").getResultList();
        if (list.isEmpty()) {
            throw new ApplicationException("Please define tax in tax master.");
        }
        Vector vec = (Vector) list.get(0);
        if (vec.get(0) == null || vec.get(0).toString().trim().equals("")) {
            throw new ApplicationException("Please define component base in tax master.");
        }
        String componentBase = vec.get(0).toString().trim();
        String componentType = vec.get(1).toString().trim();

        int roundUpTO = Integer.parseInt(vec.get(5).toString().trim());
        String taxHead = vec.get(4).toString().trim();

        if (!componentBase.equalsIgnoreCase("BC")) {
            if (componentType == null || componentType.equals("") || !componentType.contains(":")) { //At least there will be two component.
                throw new ApplicationException("Please define proper component type in tax master.");
            }
            List componentTypeList = em.createNativeQuery("select charge_name, amt, cr_gl_head from "
                    + "cbs_charge_detail where charge_type='SERVICE-TAX-IGST'").getResultList();
            if (componentTypeList.isEmpty()) {
                throw new ApplicationException("Please define tax component in cbs charge detail.");
            }
            //Break the component
//            String[] arr = componentType.split(":");    //Component type will be separated by ":"
//            if (arr.length != componentTypeList.size()) {
//                throw new ApplicationException("Please define equal no of component type "
//                        + "in tax master and cbs charge detail.");
//            }
            //Checking of all component and tax calculation
//            for (int i = 0; i < arr.length; i++) {
//                int index = -1;
            for (int j = 0; j < componentTypeList.size(); j++) {
                Vector ele = (Vector) componentTypeList.get(j);
//                    if (ele.get(0).toString().trim().equalsIgnoreCase(arr[i])) {
//                        index = 0;
                //Validation
                if (ele.get(2) == null || ele.get(2).toString().trim().equals("")
                        || ele.get(2).toString().trim().length() != 10) {
                    throw new ApplicationException("Please define proper glhead of length 10 "
                            + "in cbs charge detail for " + ele.get(0).toString().trim());
                }
                //Tax Calculation
                double tax = (chargeAmount * Double.parseDouble(ele.get(1).toString().trim())) / 100;
                tax = CbsUtil.round(tax, roundUpTO);
                if (ele.get(2).toString().trim().equals(taxHead + "01")) {
                    map.put(ele.get(0).toString().trim() + ":" + ele.get(2).toString().trim() + ":" + componentBase + ":R", tax);
                    break;
                } else {
                    map.put(ele.get(0).toString().trim() + ":" + ele.get(2).toString().trim() + ":" + componentBase + ":S", tax);
                    break;
                }
//                    }
            }
//                if (index == -1) {
//                    throw new ApplicationException("Please define equal no of component type "
//                            + "in tax master and cbs charge detail. Component type and charge name should be same.");
//                }
//            }            
        }
        return map;
    }

    public Map<String, Double> getTaxComponentSlab(String applicableDt) throws ApplicationException {
        try {
            Map<String, Double> map = new HashMap<String, Double>();

            List list = em.createNativeQuery("select component_base,component_type,des,rot,glhead,roundupto "
                    + "from taxmaster where applicabledt=(select max(applicabledt) from taxmaster where "
                    + "applicableflag='Y' and applicabledt<='" + applicableDt + "')").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define tax in tax master.");
            }
            Vector vec = (Vector) list.get(0);
            if (vec.get(0) == null || vec.get(0).toString().trim().equals("")) {
                throw new ApplicationException("Please define component base in tax master.");
            }
            String componentBase = vec.get(0).toString().trim();
            String componentType = vec.get(1).toString().trim();
            String roundUpTO = vec.get(5).toString().trim();

            if (componentBase.equalsIgnoreCase("BC")) { //Both(dr and cr) Club Entry
                if (vec.get(2) == null || vec.get(2).toString().trim().equals("")) {
                    throw new ApplicationException("Please define description in tax master.");
                }
                String description = vec.get(2).toString().trim();
                double rot = Double.parseDouble(vec.get(3).toString().trim());
                if (vec.get(4) == null || vec.get(4).toString().trim().equals("")
                        || vec.get(4).toString().trim().length() != 8) {
                    throw new ApplicationException("Please fill proper gl head in tax master.");
                }
                String taxHead = vec.get(4).toString().trim() + "01";
                //Setting In Map
                map.put(description + ":" + taxHead + ":" + componentBase + ":" + roundUpTO, rot);
                return map;
            } else {
                if (componentType == null || componentType.equals("") || !componentType.contains(":")) { //At least there will be two component.
                    throw new ApplicationException("Please define proper component type in tax master.");
                }
                List componentTypeList = em.createNativeQuery("select charge_name,amt,cr_gl_head from "
                        + "cbs_charge_detail where charge_type='SERVICE-TAX'").getResultList();
                if (componentTypeList.isEmpty()) {
                    throw new ApplicationException("Please define tax component in cbs charge detail.");
                }
                //Break the component
                String[] arr = componentType.split(":");    //Component type will be separated by ":"
                if (arr.length != componentTypeList.size()) {
                    throw new ApplicationException("Please define equal no of component type "
                            + "in tax master and cbs charge detail.");
                }
                //Checking of all component and tax calculation
                for (int i = 0; i < arr.length; i++) {
                    int index = -1;
                    for (int j = 0; j < componentTypeList.size(); j++) {
                        Vector ele = (Vector) componentTypeList.get(j);
                        if (ele.get(0).toString().trim().equalsIgnoreCase(arr[i])) {
                            index = 0;
                            //Validation
                            if (ele.get(2) == null || ele.get(2).toString().trim().equals("")
                                    || ele.get(2).toString().trim().length() != 10) {
                                throw new ApplicationException("Please define proper glhead of length 10 "
                                        + "in cbs charge detail for " + ele.get(0).toString().trim());
                            }
                            //Setting In Map
                            map.put(ele.get(0).toString().trim() + ":" + ele.get(2).toString().trim() + ":"
                                    + componentBase + ":" + roundUpTO, Double.parseDouble(ele.get(1).toString().trim()));
                            break;
                        }
                    }
                    if (index == -1) {
                        throw new ApplicationException("Please define equal no of component type "
                                + "in tax master and cbs charge detail. Component type and charge name should be same.");
                    }
                }
                return map;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public Map<String, Double> getIgstTaxComponentSlab(String applicableDt) throws ApplicationException {
        try {
            Map<String, Double> map = new HashMap<String, Double>();

            List list = em.createNativeQuery("select component_base,component_type,des,rot,glhead,roundupto "
                    + "from taxmaster where applicabledt=(select max(applicabledt) from taxmaster where "
                    + "applicableflag='Y' and applicabledt<='" + applicableDt + "')").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define tax in tax master.");
            }
            Vector vec = (Vector) list.get(0);
            if (vec.get(0) == null || vec.get(0).toString().trim().equals("")) {
                throw new ApplicationException("Please define component base in tax master.");
            }
            String componentBase = vec.get(0).toString().trim();
            String componentType = vec.get(1).toString().trim();
            String roundUpTO = vec.get(5).toString().trim();

            if (!componentBase.equalsIgnoreCase("BC")) { //Both(dr and cr) Club Entry

                if (componentType == null || componentType.equals("") || !componentType.contains(":")) { //At least there will be two component.
                    throw new ApplicationException("Please define proper component type in tax for IGST master.");
                }
                List componentTypeList = em.createNativeQuery("select charge_name,amt,cr_gl_head from "
                        + "cbs_charge_detail where charge_type='SERVICE-TAX-IGST'").getResultList();
                if (componentTypeList.isEmpty()) {
                    throw new ApplicationException("Please define tax component in cbs charge detail.");
                }
                //Break the component
//                String[] arr = componentType.split(":");    //Component type will be separated by ":"
//                if (arr.length != componentTypeList.size()) {
//                    throw new ApplicationException("Please define equal no of component type "
//                            + "in tax master and cbs charge detail.");
//                }
                //Checking of all component and tax calculation
//                for (int i = 0; i < componentTypeList.size(); i++) {
//                    int index = -1;
                for (int j = 0; j < componentTypeList.size(); j++) {
                    Vector ele = (Vector) componentTypeList.get(j);
//                        if (ele.get(0).toString().trim().equalsIgnoreCase(arr[i])) {
//                            index = 0;
                    //Validation
                    if (ele.get(2) == null || ele.get(2).toString().trim().equals("")
                            || ele.get(2).toString().trim().length() != 10) {
                        throw new ApplicationException("Please define proper glhead of length 10 "
                                + "in cbs charge detail for " + ele.get(0).toString().trim());
                    }
                    //Setting In Map
                    map.put(ele.get(0).toString().trim() + ":" + ele.get(2).toString().trim() + ":"
                            + componentBase + ":" + roundUpTO, Double.parseDouble(ele.get(1).toString().trim()));
                    break;
//                        }
                }
//                    if (index == -1) {
//                        throw new ApplicationException("Please define equal no of component type "
//                                + "in tax master and cbs charge detail. Component type and charge name should be same.");
//                    }
//                }                
            }
            return map;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String emiPaidMarkingDt(String acNo, String dt) throws Exception {
        try {
            List rset, rs1, rs2, rs3, rs4 = null;
            String dueDt = "19000101";
            double tot = 0;
            tot = 0;
            rs1 = em.createNativeQuery("select cramt,dt from loan_recon where cramt <> '0' and acno='" + acNo + "'").getResultList();
            for (int i = 1; i < rs1.size(); i++) {
                Vector rs1Vect = (Vector) rs1.get(i);
                double cramt = Double.parseDouble(rs1Vect.get(0).toString());
                String tranDt = rs1Vect.get(1).toString();

                rs2 = em.createNativeQuery("select ifnull(count(*),0) from loan_recon where dt='" + tranDt + "' and acno ='" + acNo + "' and dramt=" + cramt).getResultList();
                if (!rs2.isEmpty()) {
                    Vector rs2Vect = (Vector) rs2.get(0);
                    int count = Integer.parseInt(rs2Vect.get(0).toString());
                    if (count == 0) {
                        tot = tot + cramt;
                    } else {
                        rs2 = em.createNativeQuery("select ifnull(count(*),0) from npa_recon where dt='" + tranDt + "' and acno ='" + acNo + "' and cramt=" + cramt).getResultList();
                        if (!rs2.isEmpty()) {
                            rs2Vect = (Vector) rs2.get(0);
                            if (count == 1) {
                                tot = tot + cramt;
                            }
                        }
                    }
                }
            }
            rs2 = em.createNativeQuery("select installamt from emidetails where acno='" + acNo + "' and sno = (select min(sno)from emidetails "
                    + "where acno='" + acNo + "' and duedt<='" + dt + "')").getResultList();
            double installment = 0;
            if (!rs2.isEmpty()) {
                Vector rs2Vect = (Vector) rs2.get(0);
                installment = Double.parseDouble(rs2Vect.get(0).toString());
            }

            rs3 = em.createNativeQuery("select count(1) from emidetails where acno='" + acNo + "' and status='PAID'").getResultList();
            int paidCnt = 0;
            if (!rs3.isEmpty()) {
                Vector rs3Vect = (Vector) rs3.get(0);
                paidCnt = Integer.parseInt(rs3Vect.get(0).toString());
            }

            int inst = new Double(tot / installment).intValue();
            double paid = installment * inst;
            double excamt = tot - paid;

            rs2 = em.createNativeQuery("select ifnull(max(sno),0) from emidetails where acno='" + acNo + "' and status='PAID'").getResultList();
            int sno = 0;
            if (!rs2.isEmpty()) {
                Vector rs2Vect = (Vector) rs2.get(0);
                sno = Integer.parseInt(rs2Vect.get(0).toString());
            }

//                rs2 = stmt2.executeQuery("select max(dt) from loan_recon where acno='" + acno + "' and cramt <> '0'");
//                String paydt = "";
//                if (rs2.next()) {
//                    paydt = rs2.getString(1);
//                }
//                rs2 = stmt2.executeQuery("select ifnull(max(recno),0) from loan_recon where acno='" + acno + "' and dt = (select max(dt) from loan_recon where acno='" + acno + "' and cramt <> '0') and cramt <> '0'");
            float recno = 0;
//                if (rs2.next()) {
//                    recno = rs2.getFloat(1);
//                }

            int toSno = sno + inst - paidCnt;

            rs2 = em.createNativeQuery("select date_format(duedt,'%Y%m%d') from emidetails where acno='" + acNo + "' and sno=" + toSno).getResultList();
            if (!rs2.isEmpty()) {
                Vector rs2Vect = (Vector) rs2.get(0);
                dueDt = rs2Vect.get(0).toString();
            }
//            stmt2.executeUpdate("update emidetails set status='PAID', /*recno=" + recno + ",*/excessamt=0 where acno = '" + acNo + "' and sno <= " + toSno + " and status='UNPAID'");
////                if (res <= 0) {
////                    throw new Exception("Problem in data updation");
////                }
////                stmt2.executeUpdate("update emidetails set excessamt=" + excamt + " where acno=" + acno + " and sno =" + (toSno - 1));
////                if (res <= 0) {
////                    throw new Exception("Problem in data updation");
////                }
//            rs2 = stmt2.executeQuery("select min(sno) from emidetails where acno='" + acno + "' and status ='PAID' group by acno");
//            if (rs2.next()) {
//                sno = rs2.getInt(1);
//            }
//
//            excamt = 0;
//            int cnt1 = 0;
//            tot=0;
//
//            rs3 = stmt3.executeQuery("select dt,sum(cramt), recno from loan_recon where acno='" + acno + "' and cramt <> 0 group by dt");
//            while (rs3.next()) {
//                String dt = rs3.getString(1);
//                //tot = rs3.getDouble(2);
//                tot = 0;
//                double cramt = rs3.getDouble(2);
//                recno = rs3.getFloat(3);
//                rs2 = stmt2.executeQuery("select ifnull(count(*),0) from loan_recon where dt='" + dt + "' and acno ='" + acno + "' and dramt=" + cramt);
//                if (rs2.next()) {
//                    int count = rs2.getInt(1);
//                    if (count == 0) {
//                        tot = tot + cramt;
//                    } else {
//                        rs2 = stmt2.executeQuery("select ifnull(count(*),0) from npa_recon where dt='" + dt + "' and acno ='" + acno + "' and cramt=" + cramt);
//                        if (rs2.next()) {
//                            count = rs2.getInt(1);
//                            if (count == 1) {
//                                tot = tot + cramt;
//                            }
//                        }
//                    }
//                }/*
//                rs4 = stmt4.executeQuery("select * from loan_recon where acno='" + acno + "' and dramt = " + cramt + " and dt = '" + dt + "'");
//                if (rs4.next()) {
//                    //tot = 0;
//                    rs2 = stmt2.executeQuery("select ifnull(count(*),0) from npa_recon where dt='" + dt + "' and acno ='" + acno + "' and cramt=" + cramt);
//                    if (rs2.next()) {
//                        int cnt21 = rs2.getInt(1);
//                        if (cnt21 == 1) {
//                            tot = tot + cramt;
//                        }else{
//                            tot = 0;
//                        }
//                    }
//                }*/
//
//
//
//                if (cnt1 == 0) {
//                    rs4 = stmt4.executeQuery("select installamt,min(sno) from emidetails where acno='" + acno + "' and status ='PAID' group by acno,installamt");
//                    if (rs4.next()) {
//                        installment = rs4.getDouble(1);
//                        sno = rs4.getInt(2);
//                    }
//                } else if (cnt1 > 0) {
//                    rs4 = stmt4.executeQuery("select installamt from emidetails where acno='" + acno + "' and status ='PAID' group by acno,installamt");
//                    if (rs4.next()) {
//                        installment = rs4.getDouble(1);
//                    }
//                }
//                if (tot + excamt > installment) {
//                    inst = new Double((tot + excamt) / installment).intValue();
//                    paid = installment * inst;
//                    toSno = sno + inst;
//
//                    stmt4.executeUpdate("update emidetails set paymentdt='" + dt + "', recno = "+recno+" where acno = '" + acno + "' and sno >= " + sno
//                            + " and sno < " + toSno + " and status='PAID'");
////                        if (res <= 0) {
////                            throw new Exception("Problem in data updation");
////                        }
//                    sno = toSno;
//                    cnt1 = cnt1 + 1;
//                    excamt = tot + excamt - paid;
//                    stmt2.executeUpdate("update emidetails set excessamt=" + excamt + " where acno=" + acno + " and sno =" + (toSno - 1));
//                } else if (tot + excamt < installment) {
//                    excamt = tot + excamt;
//                    if (excamt > installment) {
//                        inst = new Double((tot + excamt) / installment).intValue();
//                        paid = installment * inst;
//                        toSno = sno + inst;
//
//                        stmt4.executeUpdate("update emidetails set paymentdt='" + dt + "', recno = "+recno+" where acno = '" + acno + "' and sno >= " + sno
//                                + " and sno < " + toSno + " and status='PAID'");
////                            if (res <= 0) {
////                                throw new Exception("Problem in data updation");
////                            }
//
//                        sno = toSno;
//                        cnt1 = cnt1 + 1;
//                        excamt = excamt - paid;
//                        stmt2.executeUpdate("update emidetails set excessamt=" + excamt + " where acno=" + acno + " and sno =" + (toSno - 1));
//                    }
//                } else if (tot + excamt == installment) {
//                    inst = new Double((tot + excamt) / installment).intValue();
//                    paid = installment * inst;
//                    toSno = sno + inst;
//
//                    stmt4.executeUpdate("update emidetails set paymentdt='" + dt + "', recno = "+recno+" where acno = '" + acno + "' and sno >= " + sno
//                            + " and sno < " + toSno + " and status='PAID'");
////                        if (res <= 0) {
////                            throw new Exception("Problem in data updation");
////                        }
//                    sno = toSno;
//                    cnt1 = cnt1 + 1;
//                    excamt = tot + excamt - paid;
//                    stmt2.executeUpdate("update emidetails set excessamt=" + excamt + " where acno=" + acno + " and sno =" + (toSno - 1));
//                }
//            }

            return dueDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String insertDenominationDetail(String acno, float recNo, String dt, BigDecimal denomination,
            int denoValue, int ty, String brnCode, String enteryBy) throws ApplicationException {
        try {
            if (acno == null || acno.equals("")) {
                throw new ApplicationException("A/c no can not be null or blank.");
            }
            if (recNo == 0f || recNo == 0.0f) {
                throw new ApplicationException("Rec number can not be zero.");
            }
            if (dt == null || dt.equals("") || !new Validator().validateDateYYYYMMDD(dt)) {
                throw new ApplicationException("Date should be in yyyyMMdd format.");
            }
            if (denomination == null || denomination.equals("")) {
                throw new ApplicationException("Please provide proper denomination.");
            }
            if (denoValue == 0) {
                throw new ApplicationException("Denomination value should not be zero.");
            }
            if (!(ty == 0 || ty == 1)) {
                throw new ApplicationException("Please provide proper ty value.");
            }
            if (brnCode == null || brnCode.equals("") || brnCode.trim().length() != 2) {
                throw new ApplicationException("Please provide proper branch code value.");
            }
            if (enteryBy == null || enteryBy.equals("")) {
                throw new ApplicationException("Please provide proper enter by value.");
            }

            int n = em.createNativeQuery("insert into denomination_detail (acno, recno, dt, denomination, "
                    + "denomination_value, ty, brncode, enterby, entrydate) values ('" + acno + "', " + recNo + ", "
                    + "'" + dt + "', '" + denomination + "', " + denoValue + ", " + ty + ", '" + brnCode + "', "
                    + "'" + enteryBy + "', now())").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in denomination detail insertion.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

//    public String insertDenominationDetail(String acno, float recNo, String dt, String denomination,
//            int denoValue, int ty, String brnCode, String enteryBy) throws ApplicationException {
//        try {
//            if (acno == null || acno.equals("")) {
//                throw new ApplicationException("A/c no can not be null or blank.");
//            }
//            if (recNo == 0f || recNo == 0.0f) {
//                throw new ApplicationException("Rec number can not be zero.");
//            }
//            if (dt == null || dt.equals("") || !new Validator().validateDateYYYYMMDD(dt)) {
//                throw new ApplicationException("Date should be in yyyyMMdd format.");
//            }
//            if (denomination == null || denomination.equals("")) {
//                throw new ApplicationException("Please provide proper denomination.");
//            }
//            if (denoValue == 0) {
//                throw new ApplicationException("Denomination value should not be zero.");
//            }
//            if (!(ty == 0 || ty == 1)) {
//                throw new ApplicationException("Please provide proper ty value.");
//            }
//            if (brnCode == null || brnCode.equals("") || brnCode.trim().length() != 2) {
//                throw new ApplicationException("Please provide proper branch code value.");
//            }
//            if (enteryBy == null || enteryBy.equals("")) {
//                throw new ApplicationException("Please provide proper enter by value.");
//            }
//
//            int n = em.createNativeQuery("insert into denomination_detail (acno, recno, dt, denomination, "
//                    + "denomination_value, ty, brncode, enterby, entrydate) values ('" + acno + "', " + recNo + ", "
//                    + "'" + dt + "', '" + denomination + "', " + denoValue + ", " + ty + ", '" + brnCode + "', "
//                    + "'" + enteryBy + "', now()").executeUpdate();
//            if (n <= 0) {
//                throw new ApplicationException("Problem in denomination detail insertion.");
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return "true";
//    }
    public String insertDenominationDetail(String acno, float recNo, String dt, BigDecimal denomination,
            int denoValue, int ty, String brnCode, String enteryBy, String newOldFlag) throws ApplicationException {
        try {
            if (acno == null || acno.equals("")) {
                throw new ApplicationException("A/c no can not be null or blank.");
            }
            if (recNo == 0f || recNo == 0.0f) {
                throw new ApplicationException("Rec number can not be zero.");
            }
            if (dt == null || dt.equals("") || !new Validator().validateDateYYYYMMDD(dt)) {
                throw new ApplicationException("Date should be in yyyyMMdd format.");
            }
            if (denomination == null) {
                throw new ApplicationException("Please provide proper denomination.");
            }
            if (denoValue == 0) {
                throw new ApplicationException("Denomination value should not be zero.");
            }
            if (!(ty == 0 || ty == 1 || ty == 3 || ty == 4)) {
                throw new ApplicationException("Please provide proper ty value.");
            }
            if (brnCode == null || brnCode.equals("") || brnCode.trim().length() != 2) {
                throw new ApplicationException("Please provide proper branch code value.");
            }
            if (enteryBy == null || enteryBy.equals("")) {
                throw new ApplicationException("Please provide proper enter by value.");
            }
            if (newOldFlag == null || newOldFlag.equals("")) {
                throw new ApplicationException("Please provide proper flag value.");
            }

            int n = em.createNativeQuery("insert into denomination_detail (acno, recno, dt, denomination, "
                    + "denomination_value, ty, brncode, enterby, entrydate, new_old_flag) values ('" + acno + "', " + recNo + ", "
                    + "'" + dt + "', " + denomination + ", " + denoValue + ", " + ty + ", '" + brnCode + "', "
                    + "'" + enteryBy + "', now(),'" + newOldFlag + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in denomination detail insertion.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    //A single argument should not be null and dt should be in yyyyMMdd format.
    public String updateOpeningDenomination(String brnCode, BigDecimal denomination, int denoValue,
            String dt, String newOldFlag) throws ApplicationException {
        try {
            if (brnCode == null || brnCode.equals("") || brnCode.trim().length() != 2) {
                throw new ApplicationException("Please provide proper branch code value.");
            }
            if (denomination == null) {
                throw new ApplicationException("Please provide proper denomination.");
            }
            if (denoValue == 0) {
                throw new ApplicationException("Denomination value should not be zero.");
            }
            if (dt == null || dt.equals("") || !new Validator().validateDateYYYYMMDD(dt)) {
                throw new ApplicationException("Date should be in yyyyMMdd format.");
            }
            if (newOldFlag == null || newOldFlag.equals("")) {
                throw new ApplicationException("Please provide proper flag.");
            }

            List list = em.createNativeQuery("select denomination_value from denomination_opening where "
                    + "brncode='" + brnCode + "' and denomination='" + denomination + "' and "
                    + "dt='" + dt + "' and new_old_flag='" + newOldFlag + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no such denomination to update.");
            }

            List listChk = em.createNativeQuery("select denomination_value+" + denoValue + " from denomination_opening where "
                    + "brncode='" + brnCode + "' and denomination='" + denomination + "' and "
                    + "dt='" + dt + "' and new_old_flag='" + newOldFlag + "'").getResultList();
            if (!listChk.isEmpty()) {
                Vector denoVecChk = (Vector) listChk.get(0);
                int chkDenoVal = Integer.parseInt(denoVecChk.get(0).toString());
                if (chkDenoVal < 0) {
                    throw new ApplicationException("You can't pay more than your stock For. Rs. " + denomination);
                }
            }

            int n = em.createNativeQuery("update denomination_opening set denomination_value="
                    + "denomination_value+" + denoValue + ", trantime=now() where brncode='" + brnCode + "' and "
                    + "denomination='" + denomination + "' and dt='" + dt + "' and new_old_flag='" + newOldFlag + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in opening denomination updation.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String insertBulkDenominationDetail(List<DenominationDetailTo> denominationList) throws ApplicationException {
        try {
            String denominationInsertQuery = "insert into denomination_detail (acno, recno, dt, denomination, "
                    + "denomination_value, ty, brncode, enterby, entrydate, new_old_flag) values";
            String denominationDataQuery = "";
            for (DenominationDetailTo to : denominationList) {
                if (to.getAcno() == null || to.getAcno().equals("")) {
                    throw new ApplicationException("A/c no can not be null or blank.");
                }
                if (to.getRecNo() == 0f || to.getRecNo() == 0.0f) {
                    throw new ApplicationException("Rec number can not be zero.");
                }
                if (to.getDt() == null || to.getDt().equals("") || !new Validator().validateDateYYYYMMDD(to.getDt())) {
                    throw new ApplicationException("Date should be in yyyyMMdd format.");
                }
                if (to.getDenomination() == null) {
                    throw new ApplicationException("Please provide proper denomination.");
                }
                if (to.getDenoValue() == 0) {
                    throw new ApplicationException("Denomination value should not be zero.");
                }
                if (!(to.getTy() == 0 || to.getTy() == 1 || to.getTy() == 3 || to.getTy() == 4)) {
                    throw new ApplicationException("Please provide proper ty value.");
                }
                if (to.getBrnCode() == null || to.getBrnCode().equals("") || to.getBrnCode().trim().length() != 2) {
                    throw new ApplicationException("Please provide proper branch code value.");
                }
                if (to.getEnteryBy() == null || to.getEnteryBy().equals("")) {
                    throw new ApplicationException("Please provide proper enter by value.");
                }
                if (to.getNewOldFlag() == null || to.getNewOldFlag().equals("")) {
                    throw new ApplicationException("Please provide proper flag value.");
                }

                if (denominationDataQuery.equals("")) {
                    denominationDataQuery = "('" + to.getAcno() + "', " + to.getRecNo() + ", '" + to.getDt() + "', "
                            + "'" + to.getDenomination() + "', " + to.getDenoValue() + ", " + to.getTy() + ", "
                            + "'" + to.getBrnCode() + "', '" + to.getEnteryBy() + "', now(),'" + to.getNewOldFlag() + "')";
                } else {
                    denominationDataQuery = denominationDataQuery + "," + "('" + to.getAcno() + "', " + to.getRecNo() + ", "
                            + "'" + to.getDt() + "', '" + to.getDenomination() + "', " + to.getDenoValue() + ", "
                            + "" + to.getTy() + ", '" + to.getBrnCode() + "', '" + to.getEnteryBy() + "', now(),"
                            + "'" + to.getNewOldFlag() + "')";
                }
            }

            denominationInsertQuery = denominationInsertQuery + denominationDataQuery;
            int n = em.createNativeQuery(denominationInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in denomination detail insertion.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String deleteDenomination(float recNo, String dt) throws ApplicationException {
        try {
            if (recNo == 0f || recNo == 0.0f) {
                return "Rec no can not be zero.";
            }
            if (dt == null || dt.equals("") || !new Validator().validateDateYYYYMMDD(dt)) {
                return "Please provide date in YYYYMMDD format.";
            }
            List list = em.createNativeQuery("select brncode,denomination,denomination_value,"
                    + "date_format(dt,'%Y%m%d'),new_old_flag,ty from denomination_detail where "
                    + "recno=" + recNo + " and dt='" + dt + "'").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector denoVec = (Vector) list.get(i);
                    String denoBrnCode = denoVec.get(0).toString();
                    BigDecimal denomination = new BigDecimal(denoVec.get(1).toString());
                    int denoValue = Integer.parseInt(denoVec.get(2).toString());
                    String denoDt = denoVec.get(3).toString();
                    String denoFlag = denoVec.get(4).toString();
                    int ty = Integer.parseInt(denoVec.get(5).toString());

                    if (ty == 0 || ty == 4) {
                        denoValue = -denoValue;
                    }
                    int n = em.createNativeQuery("update denomination_opening set "
                            + "denomination_value = denomination_value+" + denoValue + ","
                            + "trantime=now() where brncode='" + denoBrnCode + "' and "
                            + "denomination=" + denomination + " and dt='" + denoDt + "' and "
                            + "new_old_flag='" + denoFlag + "'").executeUpdate();
                    if (n <= 0) {
                        return "Problem in denomination opening updation.";
                    }
                }
                int n = em.createNativeQuery("delete from denomination_detail "
                        + "where recno=" + recNo + " and dt='" + dt + "'").executeUpdate();
                if (n <= 0) {
                    return "Problem in denomination deletion.";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public void insertCKYCRDownload(CKYCRDownloadPojo pojo) throws Exception {
        try {
            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {
//                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where cast(Batch_No as unsigned)='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where Batch_No='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no such request to download it.");
                } else {
                    Vector vec = (Vector) selectList.get(0);
                    pojo.setRequestDate(vec.get(0).toString());
                }
            }
            String insertquery = "INSERT INTO ckycr_download (RecordType, LineNo, ApplicationType,"
                    + " BranchCode, ApplicantNameUpdateFlag, PersonalEntityDetailsUpdateFlag, AddressDetailsUpdateFlag,"
                    + " ContactDetailsUpdateFlag, RemarksUpdateFlag, KYCVerificationUpdateFlag, IdentityDetailsUpdateFlag,"
                    + " RelatedPersonDetailsFlag, ControllingPersonDetailsFlag, ImageDetailsUpdateFlag, ConstitutionType,"
                    + " AccHolderTypeFlag, AccHolderType, AccType, CKYCno, CustNamePrefix, CustFirstName, CustMiddleName,"
                    + " CustLastName, CustFullName, MaidenNamePrefix, MaidenFirstName, MaidenMiddleName, MaidenLastName,"
                    + " MaidenFullName, father_spouse_flag, FatherSpouseNamePrefix, FatherSpouseFirstName,"
                    + " FatherSpouseMiddleName, FatherSpouseLastName, FatherSpouseFullName, MotherNamePrefix,"
                    + " MotherFirstName, MotherMiddleName, MotherLastName, MotherFullName, Gender, MaritalStatus, "
                    + "Nationality, OccupationType, DateOfBirth, PlaceOfIncorporation, DateOfCommBusiness, "
                    + "CountryOfIncorporation, ResidenceCountryTaxLaw, IdentificationType, TINNo, TINIssuingCountry, "
                    + "PAN, ResidentialStatus, FlagCustResiForTaxJuriOutsideIN, JuriOfResidence, JuriTaxIdentificationNo,"
                    + " CountryOfBirth, PlaceOfBirth, PerAddType, PerAddressLine1, peraddressline2, peraddressline3, "
                    + "PerCityVillage, PerDistrict, PerState, PerCountryCode, PerPostalCode, PerPOA, PerOtherPOA, "
                    + "PerMailAddSameFlagIndicate, MailAddType, MailAddressLine1, MailAddressLine2, MailAddressLine3,"
                    + " MailCityVillage, MailDistrict, MailState, MailCountry, MailPostalCode, MailPOA,"
                    + " JuriAddBasedOnFlag, JuriAddType, JuriAddressLine1, JuriAddressLine2, JuriAddressLine3, "
                    + "JuriCityVillage, JuriState, JuriCountry, JuriPostCode, JuriPOA, ResidenceTelSTDCode,"
                    + " ResidenceTelNo, OfficeTeleSTDCode, OfficeTelNo, ISDCode, MobileNo, FaxSTDCode, FaxNo,"
                    + " EmailID, Remarks, DateOfDeclaration, PlaceOfDeclaration, KYCVerificationDate, "
                    + "TypeOfDocSubmitted, KYCVerificationName, KYCVerificDesignation, KYCVerificBranch,"
                    + " KYCVerificEMPCode, OrganisationName, OrganisationCode, NoOfIdentityDetails, "
                    + "NoOfrelatedpeople, NoOfControllingPersonResiOutsideIN, NoOfLocalAddDetails, NoOfImages, "
                    + "ErrorCode, Filler1, Filler2, Filler3, Filler4,ZipFileName,RequestType,Mode,RequestId,RequestDate) VALUES ('"
                    + pojo.getRecordType() + "','" + pojo.getLineNo() + "','"
                    + pojo.getApplicationType() + "','" + pojo.getBranchCode() + "','"
                    + pojo.getApplicantNameUpdateFlag() + "','" + pojo.getPersonalEntityDetailsUpdateFlag() + "','"
                    + pojo.getAddressDetailsUpdateFlag() + "','" + pojo.getContactDetailsUpdateFlag() + "','"
                    + pojo.getRemarksUpdateFlag() + "','" + pojo.getkYCVerificationUpdateFlag() + "','"
                    + pojo.getIdentityDetailsUpdateFlag() + "','" + pojo.getRelatedPersonDetailsFlag() + "','"
                    + pojo.getControllingPersonDetailsFlag() + "','" + pojo.getImageDetailsUpdateFlag() + "','"
                    + ParseFileUtil.addTrailingZeros(pojo.getConstitutionType(), 2) + "','" + pojo.getAccHolderTypeFlag() + "','"
                    + pojo.getAccHolderType() + "','" + pojo.getAccType() + "','"
                    + pojo.getcKYCno() + "','" + pojo.getCustNamePrefix() + "','"
                    + pojo.getCustFirstName() + "','" + pojo.getCustMiddleName() + "','"
                    + pojo.getCustLastName() + "','" + pojo.getCustFullName() + "','"
                    + pojo.getMaidenNamePrefix() + "','" + pojo.getMaidenFirstName() + "','"
                    + pojo.getMaidenMiddleName() + "','" + pojo.getMaidenLastName() + "','"
                    + pojo.getMaidenFullName() + "','" + pojo.getFather_spouse_flag() + "','"
                    + pojo.getFatherSpouseNamePrefix() + "','" + pojo.getFatherSpouseFirstName() + "','"
                    + pojo.getFatherSpouseMiddleName() + "','" + pojo.getFatherSpouseLastName() + "','"
                    + pojo.getFatherSpouseFullName() + "','" + pojo.getMotherNamePrefix() + "','"
                    + pojo.getMotherFirstName() + "','" + pojo.getMotherMiddleName() + "','"
                    + pojo.getMotherLastName() + "','" + pojo.getMotherFullName() + "','"
                    + pojo.getGender() + "','" + pojo.getMaritalStatus() + "','"
                    + pojo.getNationality() + "','" + pojo.getOccupationType() + "','"
                    + pojo.getDateOfBirth() + "','" + pojo.getPlaceOfIncorporation() + "','"
                    + pojo.getDateOfCommBusiness() + "','" + pojo.getCountryOfIncorporation() + "','"
                    + pojo.getResidenceCountryTaxLaw() + "','" + pojo.getIdentificationType() + "','"
                    + pojo.gettINNo() + "','" + pojo.gettINIssuingCountry() + "','"
                    + pojo.getpAN() + "','" + pojo.getResidentialStatus() + "','"
                    + pojo.getFlagCustResiForTaxJuriOutsideIN() + "','" + pojo.getJuriOfResidence() + "','"
                    + pojo.getJuriTaxIdentificationNo() + "','" + pojo.getCountryOfBirth() + "','"
                    + pojo.getPlaceOfBirth() + "','" + pojo.getPerAddType() + "','"
                    + pojo.getPerAddressLine1() + "','" + pojo.getPeraddressline2() + "','"
                    + pojo.getPeraddressline3() + "','" + pojo.getPerCityVillage() + "','"
                    + pojo.getPerDistrict() + "','" + pojo.getPerState() + "','"
                    + pojo.getPerCountryCode() + "','" + pojo.getPerPostalCode() + "','"
                    + pojo.getPerPOA() + "','" + pojo.getPerOtherPOA() + "','"
                    + pojo.getPerMailAddSameFlagIndicate() + "','" + pojo.getMailAddType() + "','"
                    + pojo.getMailAddressLine1() + "','" + pojo.getMailAddressLine2() + "','"
                    + pojo.getMailAddressLine3() + "','" + pojo.getMailCityVillage() + "','"
                    + pojo.getMailDistrict() + "','" + pojo.getMailState() + "','"
                    + pojo.getMailCountry() + "','" + pojo.getMailPostalCode() + "','"
                    + pojo.getMailPOA() + "','" + pojo.getJuriAddBasedOnFlag() + "','"
                    + pojo.getJuriAddType() + "','" + pojo.getJuriAddressLine1() + "','"
                    + pojo.getJuriAddressLine2() + "','" + pojo.getJuriAddressLine3() + "','"
                    + pojo.getJuriCityVillage() + "','" + pojo.getJuriState() + "','"
                    + pojo.getJuriCountry() + "','" + pojo.getJuriPostCode() + "','"
                    + pojo.getJuriPOA() + "','" + pojo.getResidenceTelSTDCode() + "','"
                    + pojo.getResidenceTelNo() + "','" + pojo.getOfficeTeleSTDCode() + "','"
                    + pojo.getOfficeTelNo() + "','" + pojo.getiSDCode() + "','"
                    + pojo.getMobileNo() + "','" + pojo.getFaxSTDCode() + "','"
                    + pojo.getFaxNo() + "','" + pojo.getEmailID() + "','"
                    + pojo.getRemarks() + "','" + pojo.getDateOfDeclaration() + "','"
                    + pojo.getPlaceOfDeclaration() + "','" + pojo.getkYCVerificationDate() + "','"
                    + pojo.getTypeOfDocSubmitted() + "','" + pojo.getkYCVerificationName() + "','"
                    + pojo.getkYCVerificDesignation() + "','" + pojo.getkYCVerificBranch() + "','"
                    + pojo.getkYCVerificEMPCode() + "','" + pojo.getOrganisationName() + "','"
                    + pojo.getOrganisationCode() + "','" + pojo.getNoOfIdentityDetails() + "','"
                    + pojo.getNoOfrelatedpeople() + "','" + pojo.getNoOfControllingPersonResiOutsideIN() + "','"
                    + pojo.getNoOfLocalAddDetails() + "','" + pojo.getNoOfImages() + "','"
                    + pojo.getErrorCode() + "','" + pojo.getFiller1() + "','"
                    + pojo.getFiller2() + "','" + pojo.getFiller3() + "','"
                    + pojo.getFiller4() + "','" + pojo.getZipFileName() + "','"
                    + pojo.getRequestType() + "','" + pojo.getMode() + "','"
                    + pojo.getRequestId() + "','" + pojo.getRequestDate() + "');";

            int n = em.createNativeQuery(insertquery).executeUpdate();

            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {

                if (n <= 0) {
                    throw new Exception("Problem in download response detail-20 dumping.");
                }
                List chqList = em.createNativeQuery("select status from ckycr_request_detail where "
                        + "customerid_ckycr_no='" + pojo.getcKYCno().trim() + "' and mode='DOWNLOAD' "
                        + "and status='UPLOADED'").getResultList();
                if (!chqList.isEmpty()) {
                    n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
                            + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                            + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
                            + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
                            + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                            + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
                            + "from ckycr_request_detail where customerid_ckycr_no='" + pojo.getcKYCno().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail_his maintainance.");
                    }

                    n = em.createNativeQuery("update ckycr_request_detail set status='DOWNLOADED',reason='',"
                            + "reason_code='',response_update_time=now(),seq_no=0 where "
                            + "customerid_ckycr_no='" + pojo.getcKYCno().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in download response updation.");
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void insertCKYCRDownloadDetail30(CKYCRDownloadDetail30 pojo) throws Exception {
        try {
            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {
//                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where cast(Batch_No as unsigned)='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where Batch_No='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no such request to download it.");
                } else {
                    Vector vec = (Vector) selectList.get(0);
                    pojo.setRequestDate(vec.get(0).toString());
                }
            }

            List grouTxnId = em.createNativeQuery("select coalesce(max(TxnNo),'') from ckycr_download where CKYCno='" + pojo.getcKYCno() + "'").getResultList();
            if (!grouTxnId.isEmpty()) {
                pojo.setGroupTxnId(((Vector) grouTxnId.get(0)).get(0).toString());
            }
            String insertquery = "INSERT INTO ckycr_download_detail_30 (CKYCno,RecordType,LineNumber,Identificationtype,"
                    + "IdentityNumber,ExpiryDate,IdProofSubmitted,IdVerificationStatus,Filler1,"
                    + "Filler2,Filler3,Filler4,ZipFileName,RequestType,Mode,RequestId,RequestDate,groupTxnId) value ('"
                    + pojo.getcKYCno() + "','" + pojo.getRecordType() + "','"
                    + pojo.getLineNumber() + "','" + pojo.getIdentificationtype() + "','"
                    + pojo.getIdentityNumber() + "','" + pojo.getExpiryDate() + "','"
                    + pojo.getIdProofSubmitted() + "','" + pojo.getIdVerificationStatus() + "','"
                    + pojo.getFiller1() + "','" + pojo.getFiller2() + "','"
                    + pojo.getFiller3() + "','" + pojo.getFiller4() + "','"
                    + pojo.getZipFileName() + "','" + pojo.getRequestType() + "','"
                    + pojo.getMode() + "','" + pojo.getRequestId() + "','" + pojo.getRequestDate() + "'," + pojo.getGroupTxnId() + ");";
            int n = em.createNativeQuery(insertquery).executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in download response detail-30 dumping.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void insertCKYCRDownloadDetail60(CKYCRDownloadDetail60 pojo) throws Exception {
        try {
            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {
//                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where cast(Batch_No as unsigned)='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where Batch_No='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no such request to download it.");
                } else {
                    Vector vec = (Vector) selectList.get(0);
                    pojo.setRequestDate(vec.get(0).toString());
                }
            }
            List grouTxnId = em.createNativeQuery("select coalesce(max(TxnNo),'') from ckycr_download where CKYCno='" + pojo.getcKYCno() + "'").getResultList();
            if (!grouTxnId.isEmpty()) {
                pojo.setGroupTxnId(((Vector) grouTxnId.get(0)).get(0).toString());
            }
            String insertquery = "insert into ckycr_download_detail_60(CKYCno,RecordType,LineNumber,"
                    + "BranchCode,AddressType,LocalAddressLine1,LocalAddressLine2,LocalAddressLine3,"
                    + "LocalAddressCityVillage,LocalAddressDistrict,LocalAddressPINCode,LocalAddressState,"
                    + "LocalAddressCountry,ProofofAdd,ResiTelSTDCode,ResiTelNo,OfficeTelSTDCode,OfficeTelNo,"
                    + "MobISDCode,MobileNo,FaxSTDCode,FaxNo,EmailID,DateofDeclaration,PlaceofDeclaration,"
                    + "Filler1,Filler2,Filler3,Filler4,ZipFileName,RequestType,Mode,RequestId,RequestDate,SeqNo,groupTxnId)"
                    + " value('" + pojo.getcKYCno() + "','" + pojo.getRecordType() + "','"
                    + pojo.getLineNumber() + "','" + pojo.getBranchCode() + "','"
                    + pojo.getAddressType() + "','" + pojo.getLocalAddressLine1() + "','"
                    + pojo.getLocalAddressLine2() + "','" + pojo.getLocalAddressLine3() + "','"
                    + pojo.getLocalAddressCityVillage() + "','" + pojo.getLocalAddressDistrict() + "','"
                    + pojo.getLocalAddressPINCode() + "','" + pojo.getLocalAddressState() + "','"
                    + pojo.getLocalAddressCountry() + "','" + pojo.getProofofAdd() + "','"
                    + pojo.getResiTelSTDCode() + "','" + pojo.getResiTelNo() + "','"
                    + pojo.getOfficeTelSTDCode() + "','" + pojo.getOfficeTelNo() + "','"
                    + pojo.getMobISDCode() + "','" + pojo.getMobileNo() + "','"
                    + pojo.getFaxSTDCode() + "','" + pojo.getFaxNo() + "','"
                    + pojo.getEmailID() + "','" + pojo.getDateofDeclaration() + "','"
                    + pojo.getPlaceofDeclaration() + "','"
                    + pojo.getFiller1() + "','" + pojo.getFiller2() + "','"
                    + pojo.getFiller3() + "','" + pojo.getFiller4() + "','"
                    + pojo.getZipFileName() + "','" + pojo.getRequestType() + "','"
                    + pojo.getMode() + "','" + pojo.getRequestId() + "','" + pojo.getRequestDate() + "','"
                    + pojo.getSeqNo() + "'," + pojo.getGroupTxnId() + ");";
            int n = em.createNativeQuery(insertquery).executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in download response detail-60 dumping.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public String postECSChargesAndTax(String cbsUmrn, String accNo, String username, float trsno,
            String loginBrCode, String dt) throws ApplicationException {
        try {
            int ecsDRChargeType = reportRemote.getCodeByReportName("ECS-DR-CHG-TYPE");
            int gstapply = reportRemote.getCodeByReportName("GST-APPLY");
            int taxApply = reportRemote.getCodeByReportName("STAXMODULE_ACTIVE");
            String accBrnCode = accNo.substring(0, 2);
            String accNature = ftsPosting.getAccountNature(accNo);
            float recno = ftsPosting.getRecNo().floatValue();
            Map<String, Double> map = new HashMap<String, Double>();

            String glHeadForChargeCR = "";
            double returnCharge = 0.0, totalChgToBeDed = 0.0, serviceCharge = 0.0;
            if (ecsDRChargeType == 1) {
                List list = em.createNativeQuery("select ifnull(charges,0),glheadmisc from parameterinfo_miscincome "
                        + "where acctcode='AL' and purpose='ECS-DR-CHG'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define charge head for Ecs Return Charges.");
                }
                Vector ele = (Vector) list.get(0);
                returnCharge = Float.parseFloat(ele.get(0).toString().trim());
                glHeadForChargeCR = accBrnCode + ele.get(1).toString().trim() + "01";
            } else if (ecsDRChargeType == 0) {
                List list = em.createNativeQuery("select ifnull(charges,0),glheadmisc from parameterinfo_miscincome "
                        + "where acctcode='" + ftsPosting.getAccountCode(accNo) + "' and purpose='ECS-DR-CHG'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define charge head for Ecs Return Charges. ");
                }
                Vector ele = (Vector) list.get(0);
                returnCharge = Float.parseFloat(ele.get(0).toString().trim());
                glHeadForChargeCR = accBrnCode + ele.get(1).toString().trim() + "01";
            }
            //Charge head transaction. 
            if (returnCharge == 0.0) {
                throw new ApplicationException("Total charge amount is zero so it can not be post.");
            }
            String[] arr = getCustomerStateDetail(accNo, loginBrCode); //Check here
            if (arr == null) {
                throw new ApplicationException("State detail not found for account:" + accNo);
            }

            totalChgToBeDed = returnCharge;
            //Balance Checking of Party Account
            if (taxApply == 1 && gstapply == 1) {
                if (arr[0].equalsIgnoreCase(arr[1])) {
                    map = ibRemote.getTaxComponent(returnCharge, dt);
                } else {
                    map = ibRemote.getIgstTaxComponent(returnCharge, dt);
                }
                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    serviceCharge = serviceCharge + Double.parseDouble(entry.getValue().toString());
                }
                totalChgToBeDed = totalChgToBeDed + serviceCharge;
            }

            String msg = ftsPosting.checkBalance(accNo, totalChgToBeDed, username);
            if (!msg.equalsIgnoreCase("TRUE")) {
                int n = em.createNativeQuery("insert into pendingcharges(acno,dt,amount,details,"
                        + "instno,ty,trantype,recno,trsno,enterby,auth, authby,updatedt,updateby,charges,"
                        + "recover,trandesc) values('" + accNo + "','" + dt + "',"
                        + "" + returnCharge + ",'Pending NPCI-ECS-DR Charge','',1,2,0," + trsno + ",'" + username + "',"
                        + "'Y','" + username + "',current_timestamp,'" + username + "',Null,'N',121)").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in pending charges updation.");
                }
                return "pending";
            }
            msg = ftsPosting.insertRecons(accNature, accNo, 1, returnCharge, dt,
                    dt, 2, "NPCI-ECS-DR-RetChg", username, trsno, ymdh.format(new Date()),
                    recno, "Y", username, 121, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                    accBrnCode, accBrnCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            int trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                    + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                    + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                    + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + dt + "','" + dt + "',0,"
                    + "" + returnCharge + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',121,0,'','NPCI-ECS-DR-RetChg',"
                    + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                    + "'" + accBrnCode + "',0,'','')").executeUpdate();
            if (trfSeq <= 0) {
                throw new ApplicationException("Problem In Trf Scroll Insertion.");
            }
            recno = recno + 1;
            msg = ftsPosting.insertRecons("PO", glHeadForChargeCR, 0, returnCharge, dt, dt,
                    2, "NPCI-ECS-DR-RetChg", username, trsno, ymdh.format(new Date()), recno,
                    "Y", username, 121, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", accBrnCode, accBrnCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            msg = ftsPosting.updateBalance("PO", glHeadForChargeCR, returnCharge, 0.0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                    + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                    + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                    + "values('" + glHeadForChargeCR + "','" + ftsPosting.getCustName(glHeadForChargeCR) + "','" + dt + "','" + dt + "'," + Double.parseDouble(formatter.format(returnCharge)) + ","
                    + "0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',121,0,'','NPCI-ECS-DR-RetChg',"
                    + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                    + "'" + accBrnCode + "',0,'','')").executeUpdate();
            if (trfSeq <= 0) {
                throw new ApplicationException("Problem In Trf Scroll Insertion.");
            }
            if (taxApply == 1 && gstapply == 1 && serviceCharge != 0) {
                recno = recno + 1;
                msg = ftsPosting.insertRecons(accNature, accNo, 1, serviceCharge, dt,
                        dt, 2, "NPCI ECS-DR-Service-Tax", username, trsno, ymdh.format(new Date()),
                        recno, "Y", username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "",
                        "", accBrnCode, accBrnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                        + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                        + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                        + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + dt + "','" + dt + "',0,"
                        + "" + serviceCharge + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',71,0,'','NPCI ECS-DR-Service-Tax',"
                        + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                        + "'" + accBrnCode + "',0,'','')").executeUpdate();
                if (trfSeq <= 0) {
                    throw new ApplicationException("Problem In Trf Scroll Insertion.");
                }

                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();

                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    String description = keyArray[0];
                    String taxHead = accBrnCode + keyArray[1];
                    double taxAmount = Double.parseDouble(entry.getValue().toString());

                    recno = recno + 1;

                    description = description + " for ECS DR Return Charge";
                    msg = ftsPosting.insertRecons("PO", taxHead, 0, taxAmount, dt, dt, 2,
                            description, username, trsno, ymdh.format(new Date()), recno, "Y",
                            username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", accBrnCode, accBrnCode, 0,
                            "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                            + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                            + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                            + "values('" + taxHead + "','" + ftsPosting.getCustName(taxHead) + "','" + dt + "','" + dt + "'," + taxAmount + ","
                            + "0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',71,0,'','NPCI ECS-DR-Service-Tax',"
                            + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                            + "'" + accBrnCode + "',0,'','')").executeUpdate();
                    if (trfSeq <= 0) {
                        throw new ApplicationException("Problem In Trf Scroll Insertion.");
                    }

                    msg = ftsPosting.updateBalance("PO", taxHead, taxAmount, 0.0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                }
            }
            //Account balance updation
            msg = ftsPosting.updateBalance(accNature, accNo, 0.0, totalChgToBeDed, "Y", "Y");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            ftsPosting.updateRecNo(recno);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public String postECSDRSuccessEntry(String CBS_Umrn, String accNo, double creditAmount, String username, float trsno) throws ApplicationException {
        if (creditAmount == 0.0) {
            throw new ApplicationException("Total ECS-DR-CLR-AMT is zero so it can not be post.");
        }

        //Account Credit
        String accBrnCode = accNo.substring(0, 2);
        String accNature = ftsPosting.getAccountNature(accNo);
//        String accCode = ftsPosting.getAccountCode(accNo);
        String currentDt = ftsPosting.daybeginDate(accBrnCode);
//        float trsno = ftsPosting.getTrsNo().floatValue();
        float recno = ftsPosting.getRecNo().floatValue();
//        recno = recno + 1;
        String msg = ftsPosting.insertRecons(accNature, accNo, 0, creditAmount, currentDt,
                currentDt, 2, "ECS-DR-CLR-AMT", username, trsno, ymdh.format(new Date()),
                recno, "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                accBrnCode, accBrnCode, 0, "", "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        msg = ftsPosting.updateBalance(accNature, accNo, creditAmount, 0.0, "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        int trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + currentDt + "','" + currentDt + "',"
                + "" + creditAmount + ",0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ECS-DR-CLR-AMT',"
                + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                + "'" + accBrnCode + "',0,'','')").executeUpdate();
        if (trfSeq <= 0) {
            throw new ApplicationException("Problem In Trf Scroll Insertion.");
        }

        //Emi updation if exists for that a/c
        if (accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                || accNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                || accNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
            int intPostOnDeposit = ftsPosting.getCodeForReportName("INT_POST_DEPOSIT");
            if (intPostOnDeposit == 0) {
                msg = ftsPosting.npaRecoveryUpdation(trsno, accNature, accNo, ymd.format(new Date()), creditAmount, accBrnCode, accBrnCode, username);
                if (!msg.equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
            }
            List chkList = em.createNativeQuery("select acno from emidetails where "
                    + "acno='" + accNo + "' and status='Unpaid'").getResultList();
            if (!chkList.isEmpty()) {
                msg = ftsPosting.loanDisbursementInstallment(accNo, creditAmount, 0,
                        "System", ymd.format(new Date()), ftsPosting.getRecNo(), 1, "Through Neft");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Emi details updation issue-->" + msg);
                }
            }
        }
        //Emd Here
        //ECS-DR-CLG-HEAD head transaction. 
        String glHeadEcsDrClg = ftsPosting.getCodeFromCbsParameterInfo("ECS-DR-CLG-HEAD");
        glHeadEcsDrClg = accBrnCode + glHeadEcsDrClg + "01";
        recno = recno + 1;
        msg = ftsPosting.insertRecons("PO", glHeadEcsDrClg, 1, Double.parseDouble(formatter.format(creditAmount)), currentDt, currentDt,
                2, "ECS-DR-CLR-AMT", username, trsno, ymdh.format(new Date()), recno,
                "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", accBrnCode, accBrnCode, 0, "", "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        msg = ftsPosting.updateBalance("PO", glHeadEcsDrClg, 0.0, Double.parseDouble(formatter.format(creditAmount)), "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                + "values('" + glHeadEcsDrClg + "','" + ftsPosting.getCustName(glHeadEcsDrClg) + "','" + currentDt + "','" + currentDt + "',"
                + "0," + Double.parseDouble(formatter.format(creditAmount)) + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ECS-DR-CLR-AMT',"
                + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                + "'" + accBrnCode + "',0,'','')").executeUpdate();
        if (trfSeq <= 0) {
            throw new ApplicationException("Problem In Trf Scroll Insertion.");
        }
        //Last Recno Updation
        ftsPosting.updateRecNo(recno);
        return "true";
    }

    @Override
    public String postACHDRreturnChargesAndTax(String accNo, String username, float trsno,
            String loginBrCode, String dt) throws ApplicationException {
        try {
            int achDRChargeType = reportRemote.getCodeByReportName("ACH-DR-CHG-TYPE");
            int gstapply = reportRemote.getCodeByReportName("GST-APPLY");
            int taxApply = reportRemote.getCodeByReportName("STAXMODULE_ACTIVE");
            String accBrnCode = accNo.substring(0, 2);
            String accNature = ftsPosting.getAccountNature(accNo);
            float recno = ftsPosting.getRecNo().floatValue();
            Map<String, Double> map = new HashMap<String, Double>();

            String glHeadForChargeCR = "";
            double returnCharge = 0.0, totalChgToBeDed = 0.0, serviceCharge = 0.0;
            if (achDRChargeType == 1) {
                List list = em.createNativeQuery("select ifnull(charges,0),glheadmisc from parameterinfo_miscincome "
                        + "where acctcode='AL' and purpose='ACH-DR-CHG'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define charge head for ACH Return Charges.");
                }
                Vector ele = (Vector) list.get(0);
                returnCharge = Float.parseFloat(ele.get(0).toString().trim());
                glHeadForChargeCR = accBrnCode + ele.get(1).toString().trim() + "01";
            } else if (achDRChargeType == 0) {
                List list = em.createNativeQuery("select ifnull(charges,0),glheadmisc from parameterinfo_miscincome "
                        + "where acctcode='" + ftsPosting.getAccountCode(accNo) + "' and purpose='ACH-DR-CHG'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Please define charge head for ACH Return Charges. ");
                }
                Vector ele = (Vector) list.get(0);
                returnCharge = Float.parseFloat(ele.get(0).toString().trim());
                glHeadForChargeCR = accBrnCode + ele.get(1).toString().trim() + "01";
            }
            if (returnCharge == 0.0) {
                throw new ApplicationException("Total charge amount is zero so it can not be post.");
            }
            String[] arr = getCustomerStateDetail(accNo, loginBrCode);
            if (arr == null) {
                throw new ApplicationException("State detail not found for account:" + accNo);
            }
            totalChgToBeDed = returnCharge;
            //Balance Checking of Party Account
            if (taxApply == 1 && gstapply == 1) {
                if (arr[0].equalsIgnoreCase(arr[1])) {
                    map = ibRemote.getTaxComponent(returnCharge, dt);
                } else {
                    map = ibRemote.getIgstTaxComponent(returnCharge, dt);
                }
                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    serviceCharge = serviceCharge + Double.parseDouble(entry.getValue().toString());
                }
                totalChgToBeDed = totalChgToBeDed + serviceCharge;
            }

            String msg = ftsPosting.checkBalance(accNo, totalChgToBeDed, username);
            if (!msg.equalsIgnoreCase("TRUE")) {
                int n = em.createNativeQuery("insert into pendingcharges(acno,dt,amount,details,"
                        + "instno,ty,trantype,recno,trsno,enterby,auth, authby,updatedt,updateby,charges,"
                        + "recover,trandesc) values('" + accNo + "','" + dt + "',"
                        + "" + returnCharge + ",'Pending NPCI-ACH-DR Charge','',1,2,0," + trsno + ",'" + username + "',"
                        + "'Y','" + username + "',current_timestamp,'" + username + "',Null,'N',122)").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in pending charges updation.");
                }
                return "pending";
            }
            msg = ftsPosting.insertRecons(accNature, accNo, 1, returnCharge, dt,
                    dt, 2, "NPCI-ACH-DR-RetChg", username, trsno, ymdh.format(new Date()),
                    recno, "Y", username, 122, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                    accBrnCode, accBrnCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            int trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                    + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                    + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                    + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + dt + "','" + dt + "',0,"
                    + "" + returnCharge + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',122,0,'','NPCI-ACH-DR-RetChg',"
                    + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                    + "'" + accBrnCode + "',0,'','')").executeUpdate();
            if (trfSeq <= 0) {
                throw new ApplicationException("Problem In Trf Scroll Insertion.");
            }

            recno = recno + 1;
            msg = ftsPosting.insertRecons("PO", glHeadForChargeCR, 0, returnCharge, dt, dt,
                    2, "NPCI-ACH-DR-RetChg", username, trsno, ymdh.format(new Date()), recno,
                    "Y", username, 122, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", accBrnCode, accBrnCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            msg = ftsPosting.updateBalance("PO", glHeadForChargeCR, returnCharge, 0.0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                    + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                    + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                    + "values('" + glHeadForChargeCR + "','" + ftsPosting.getCustName(glHeadForChargeCR) + "','" + dt + "','" + dt + "'," + returnCharge + ","
                    + "0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',122,0,'','NPCI-ACH-DR-RetChg',"
                    + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                    + "'" + accBrnCode + "',0,'','')").executeUpdate();
            if (trfSeq <= 0) {
                throw new ApplicationException("Problem In Trf Scroll Insertion.");
            }

            if (taxApply == 1 && gstapply == 1 && serviceCharge != 0) {
                recno = recno + 1;

                msg = ftsPosting.insertRecons(accNature, accNo, 1, serviceCharge, dt,
                        dt, 2, "NPCI-ACH-DR-Service-Tax", username, trsno, ymdh.format(new Date()),
                        recno, "Y", username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "",
                        "", accBrnCode, accBrnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                        + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                        + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                        + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + dt + "','" + dt + "',0,"
                        + "" + serviceCharge + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',71,0,'','NPCI-ACH-DR-Service-Tax',"
                        + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                        + "'" + accBrnCode + "',0,'','')").executeUpdate();
                if (trfSeq <= 0) {
                    throw new ApplicationException("Problem In Trf Scroll Insertion.");
                }

                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    String description = keyArray[0];
                    String taxHead = accBrnCode + keyArray[1];
                    double taxAmount = Double.parseDouble(entry.getValue().toString());

                    recno = recno + 1;
                    description = description + " for ACH DR Return Charge";
                    msg = ftsPosting.insertRecons("PO", taxHead, 0, taxAmount, dt, dt, 2,
                            description, username, trsno, ymdh.format(new Date()), recno, "Y",
                            username, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", accBrnCode, accBrnCode, 0,
                            "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                            + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                            + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                            + "values('" + taxHead + "','" + ftsPosting.getCustName(taxHead) + "','" + dt + "','" + dt + "'," + taxAmount + ","
                            + "0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',71,0,'','NPCI-ACH-DR-Service-Tax',"
                            + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
                            + "'" + accBrnCode + "',0,'','')").executeUpdate();
                    if (trfSeq <= 0) {
                        throw new ApplicationException("Problem In Trf Scroll Insertion.");
                    }

                    msg = ftsPosting.updateBalance("PO", taxHead, taxAmount, 0.0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }

            }
            //Account balance updation
            msg = ftsPosting.updateBalance(accNature, accNo, 0.0, totalChgToBeDed, "Y", "Y");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            ftsPosting.updateRecNo(recno);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

//    @Override
//    public String postACHDRSuccessEntry(String accNo, double creditAmount, String username, float trsno) throws ApplicationException {
//        if (creditAmount == 0.0) {
//            throw new ApplicationException("Total ACH-DR-CLR-AMT is zero so it can not be post.");
//        }
//        //Account Credit
//        String accBrnCode = accNo.substring(0, 2);
//        String accNature = ftsPosting.getAccountNature(accNo);
//        String currentDt = ftsPosting.daybeginDate(accBrnCode);
//        float recno = ftsPosting.getRecNo().floatValue();
//        String msg = ftsPosting.insertRecons(accNature, accNo, 0, creditAmount, currentDt,
//                currentDt, 2, "ACH-DR-CLR-AMT", username, trsno, ymdh.format(new Date()),
//                recno, "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
//                accBrnCode, accBrnCode, 0, "", "", "");
//        if (!msg.equalsIgnoreCase("true")) {
//            throw new ApplicationException(msg);
//        }
//
//        msg = ftsPosting.updateBalance(accNature, accNo, creditAmount, 0.0, "", "");
//        if (!msg.equalsIgnoreCase("true")) {
//            throw new ApplicationException(msg);
//        }
//
//        int trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
//                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
//                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
//                + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + currentDt + "','" + currentDt + "',"
//                + "" + creditAmount + ",0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ACH-DR-CLR-AMT',"
//                + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
//                + "'" + accBrnCode + "',0,'','')").executeUpdate();
//        if (trfSeq <= 0) {
//            throw new ApplicationException("Problem In Trf Scroll Insertion.");
//        }
//        //Emi updation if exists for that a/c
//        if (accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
//                || accNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
//                || accNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//            int intPostOnDeposit = ftsPosting.getCodeForReportName("INT_POST_DEPOSIT");
//            if (intPostOnDeposit == 0) {
//                msg = ftsPosting.npaRecoveryUpdation(trsno, accNature, accNo, ymd.format(new Date()), creditAmount, accBrnCode, accBrnCode, username);
//                if (!msg.equalsIgnoreCase("True")) {
//                    throw new ApplicationException(msg);
//                }
//            }
//            List chkList = em.createNativeQuery("select acno from emidetails where "
//                    + "acno='" + accNo + "' and status='Unpaid'").getResultList();
//            if (!chkList.isEmpty()) {
//                msg = ftsPosting.loanDisbursementInstallment(accNo, creditAmount, 0,
//                        "System", ymd.format(new Date()), ftsPosting.getRecNo(), 1, "Through Neft");
//                if (!msg.equalsIgnoreCase("true")) {
//                    throw new ApplicationException("Emi details updation issue-->" + msg);
//                }
//            }
//        }
//        //Emd Here
//        //ACH-DR-CLG-HEAD head transaction. 
//        String glHeadAchDrClg = ftsPosting.getCodeFromCbsParameterInfo("ACH-DR-CLG-HEAD");
//        glHeadAchDrClg = accBrnCode + glHeadAchDrClg + "01";
//        recno = recno + 1;
//        msg = ftsPosting.insertRecons("PO", glHeadAchDrClg, 1, Double.parseDouble(formatter.format(creditAmount)), currentDt, currentDt,
//                2, "ACH-DR-CLR-AMT", username, trsno, ymdh.format(new Date()), recno,
//                "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", accBrnCode, accBrnCode, 0, "", "", "");
//        if (!msg.equalsIgnoreCase("true")) {
//            throw new ApplicationException(msg);
//        }
//
//        msg = ftsPosting.updateBalance("PO", glHeadAchDrClg, 0.0, Double.parseDouble(formatter.format(creditAmount)), "", "");
//        if (!msg.equalsIgnoreCase("true")) {
//            throw new ApplicationException(msg);
//        }
//
//        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
//                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
//                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
//                + "values('" + glHeadAchDrClg + "','" + ftsPosting.getCustName(glHeadAchDrClg) + "','" + currentDt + "','" + currentDt + "',"
//                + "0," + Double.parseDouble(formatter.format(creditAmount)) + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ACH-DR-CLR-AMT',"
//                + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + accBrnCode + "',"
//                + "'" + accBrnCode + "',0,'','')").executeUpdate();
//        if (trfSeq <= 0) {
//            throw new ApplicationException("Problem In Trf Scroll Insertion.");
//        }
//        //Last Recno Updation
//        ftsPosting.updateRecNo(recno);
//        return "true";
//    }
    @Override
    public String postACHDRSuccessEntry(String accNo, double creditAmount, String username, float trsno) throws ApplicationException {
        if (creditAmount == 0.0) {
            throw new ApplicationException("Total ACH-DR-CLR-AMT is zero so it can not be post.");
        }
        //Account Credit
        String accBrnCode = accNo.substring(0, 2);
        String hoBrnCode = commonReport.getBrncodeByAlphacode("HO");
        String accNature = ftsPosting.getAccountNature(accNo);
        String currentDt = ftsPosting.daybeginDate(accBrnCode);
        float recno = ftsPosting.getRecNo().floatValue();
        int entryMode = ftsPosting.getCodeForReportName("ACH-DR-ENTRY-MODE");

        String orgnBrCode = "", destBrCode = "";
        if (entryMode == 1) { //HO Level
            orgnBrCode = hoBrnCode;
            destBrCode = accBrnCode;
        } else if (entryMode == 0) { //Branch Level
            orgnBrCode = accBrnCode;
            destBrCode = accBrnCode;
        }
        String msg = ftsPosting.insertRecons(accNature, accNo, 0, creditAmount, currentDt,
                currentDt, 2, "ACH-DR-CLR-AMT", username, trsno, ymdh.format(new Date()),
                recno, "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                orgnBrCode, destBrCode, 0, "", "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        msg = ftsPosting.updateBalance(accNature, accNo, creditAmount, 0.0, "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        int trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                + "values('" + accNo + "','" + ftsPosting.getCustName(accNo) + "','" + currentDt + "','" + currentDt + "',"
                + "" + creditAmount + ",0,0,2," + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ACH-DR-CLR-AMT',"
                + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + orgnBrCode + "',"
                + "'" + destBrCode + "',0,'','')").executeUpdate();
        if (trfSeq <= 0) {
            throw new ApplicationException("Problem In Trf Scroll Insertion.");
        }
        //Emi updation if exists for that a/c
        if (accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                || accNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                || accNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
            int intPostOnDeposit = ftsPosting.getCodeForReportName("INT_POST_DEPOSIT");
            if (intPostOnDeposit == 0) {
                msg = ftsPosting.npaRecoveryUpdation(trsno, accNature, accNo, ymd.format(new Date()), creditAmount, orgnBrCode, destBrCode, username);
                if (!msg.equalsIgnoreCase("True")) {
                    throw new ApplicationException(msg);
                }
            }
            List chkList = em.createNativeQuery("select acno from emidetails where "
                    + "acno='" + accNo + "' and status='Unpaid'").getResultList();
            if (!chkList.isEmpty()) {
                msg = ftsPosting.loanDisbursementInstallment(accNo, creditAmount, 0,
                        "System", ymd.format(new Date()), ftsPosting.getRecNo(), 1, "Through Neft");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Emi details updation issue-->" + msg);
                }
            }
        }
        //Emd Here
        //ACH-DR-CLG-HEAD head transaction. 
        String glHeadAchDrClg = ftsPosting.getCodeFromCbsParameterInfo("ACH-DR-CLG-HEAD");
        glHeadAchDrClg = orgnBrCode + glHeadAchDrClg + "01";
        recno = recno + 1;
        msg = ftsPosting.insertRecons("PO", glHeadAchDrClg, 1, Double.parseDouble(formatter.format(creditAmount)), currentDt, currentDt,
                2, "ACH-DR-CLR-AMT", username, trsno, ymdh.format(new Date()), recno,
                "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, orgnBrCode, 0, "", "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        msg = ftsPosting.updateBalance("PO", glHeadAchDrClg, 0.0, Double.parseDouble(formatter.format(creditAmount)), "", "");
        if (!msg.equalsIgnoreCase("true")) {
            throw new ApplicationException(msg);
        }

        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                + "values('" + glHeadAchDrClg + "','" + ftsPosting.getCustName(glHeadAchDrClg) + "','" + currentDt + "','" + currentDt + "',"
                + "0," + Double.parseDouble(formatter.format(creditAmount)) + ",1,2," + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ACH-DR-CLR-AMT',"
                + "'" + username + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + orgnBrCode + "',"
                + "'" + orgnBrCode + "',0,'','')").executeUpdate();
        if (trfSeq <= 0) {
            throw new ApplicationException("Problem In Trf Scroll Insertion.");
        }
        //ISO entry in case of HO Entry Mode
        if (entryMode == 1) {
            List isoList = em.createNativeQuery("select acno from abb_parameter_info where purpose='INTERSOLE ACCOUNT'").getResultList();
            if (isoList.isEmpty()) {
                throw new ApplicationException("Please define intersole account.");
            }
            Vector isoVec = (Vector) isoList.get(0);
            String isoHead = isoVec.get(0).toString();
//            String isoHeadAcNo = hoBrnCode + isoHead + "01";
            String isoHeadAcNo = hoBrnCode + isoHead;

            recno = recno + 1;
            msg = ftsPosting.insertRecons("PO", isoHeadAcNo, 0, Double.parseDouble(formatter.format(creditAmount)),
                    currentDt, currentDt, 2, "ACH-DR-CLR-AMT", username, trsno, ymdh.format(new Date()), recno,
                    "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, destBrCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                    + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                    + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                    + "values('" + isoHeadAcNo + "','" + ftsPosting.getCustName(isoHeadAcNo) + "',"
                    + "'" + currentDt + "','" + currentDt + "'," + Double.parseDouble(formatter.format(creditAmount)) + ",0,0,2,"
                    + "" + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ACH-DR-CLR-AMT','" + username + "',"
                    + "" + trsno + ",CURRENT_TIMESTAMP,'','" + orgnBrCode + "','" + destBrCode + "',0,'','')").executeUpdate();
            if (trfSeq <= 0) {
                throw new ApplicationException("Problem In Trf Scroll Insertion.");
            }

            recno = recno + 1;
            //isoHeadAcNo=accBrnCode + isoHead + "01";
            isoHeadAcNo = accBrnCode + isoHead;
            msg = ftsPosting.insertRecons("PO", isoHeadAcNo, 1, Double.parseDouble(formatter.format(creditAmount)),
                    currentDt, currentDt, 2, "ACH-DR-CLR-AMT", username, trsno, ymdh.format(new Date()), recno,
                    "Y", username, 0, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", orgnBrCode, destBrCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                    + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                    + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                    + "values('" + isoHeadAcNo + "','" + ftsPosting.getCustName(isoHeadAcNo) + "',"
                    + "'" + currentDt + "','" + currentDt + "',0," + Double.parseDouble(formatter.format(creditAmount)) + ",1,2,"
                    + "" + recno + ",'','19000101',3,0,'Y','" + username + "',0,0,'','ACH-DR-CLR-AMT','" + username + "',"
                    + "" + trsno + ",CURRENT_TIMESTAMP,'','" + orgnBrCode + "','" + destBrCode + "',0,'','')").executeUpdate();
            if (trfSeq <= 0) {
                throw new ApplicationException("Problem In Trf Scroll Insertion.");
            }
        }
        //Last Recno Updation
        ftsPosting.updateRecNo(recno);
        return "true";
    }

    public String[] getCustomerStateDetail(String acno, String operatingBranchCode) throws Exception {
        String[] arr = new String[2];
        List list = em.createNativeQuery("select ifnull(cm.mailstatecode,'') as statecode,ifnull(br.state,'') as "
                + "brstate from cbs_customer_master_detail cm,customerid ci,branchmaster br where "
                + "ci.custid = cast(cm.customerid as unsigned) and ci.acno='" + acno + "' and "
                + "br.brncode='" + operatingBranchCode + "'").getResultList();
        if (list.isEmpty()) {
            throw new Exception("Please check mail state code of account or state code of branch.");
        }
        Vector ele = (Vector) list.get(0);
        String custStateCode = ele.get(0).toString().trim();
        String brStateCode = ele.get(1).toString().trim();
        if (custStateCode.equals("") || brStateCode.equals("")) {
            throw new Exception("Please check mail state code of account or state code of branch.");
        }
        arr[0] = custStateCode;
        arr[1] = brStateCode;
        return arr;
    }

    @Override
    public BigDecimal getCustomerCashWithdrawal(String custIds, String frDt, String toDt) throws ApplicationException {
        try {
            BigDecimal totalCashWithdrawal = new BigDecimal(0);
            List list = em.createNativeQuery("select ifnull(sum(g.amt),0) from "
                    + "(select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('SB') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1 OR  t.trandesc = 70) and c.custid in (" + custIds + ") "
                    + "union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  t.trandesc = 70) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  t.trandesc = 70) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  t.trandesc = 70) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  t.trandesc = 70) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  t.trandesc = 70) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from td_accountmaster a, customerid c, tokentable_debit t where a.acno = c.acno and (a.closingdate is null or a.closingdate "
                    + "between '" + frDt + "' and '" + toDt + "') and t.acno = a.acno and auth = 'N' and dt between '" + frDt + "' and '" + toDt + "' and t.trantype = 0 and t.ty = 1 "
                    + "and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, tokentable_debit t where a.acno = c.acno and (a.closingdate is null or a.closingdate "
                    + "between '" + frDt + "' and '" + toDt + "') and t.acno = a.acno and auth = 'N' and dt between '" + frDt + "' and '" + toDt + "' and t.trantype = 0 and t.ty = 1 "
                    + "and c.custid in (" + custIds + ")) g").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                totalCashWithdrawal = new BigDecimal(ele.get(0).toString().trim());
            }
            return totalCashWithdrawal;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public BigDecimal getCustomerTdsOnCashWithdrawal(String custIds, String frDt, String toDt, BigDecimal curAmt, String auth) throws ApplicationException {
        try {
            String preFrDt = "20190401", preToDt = "20190831";

            String query = "select (select ifnull(sum(g.amt),0) from "
                    + "(select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctnature in ('SB') and am.acctcode = a.accttype "
                    + "and a.acno = c.acno and (a.closingdate is null or a.closingdate between '" + preFrDt + "' and '" + preToDt + "')  and t.acno = a.acno and dt between "
                    + "'" + preFrDt + "' and '" + preToDt + "' and (t.trantype = 0 and t.ty = 1 OR  (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR  t.details like 'NFS ATM WITHDRAWAL%' OR "
                    + "t.details like 'ATM NORMAL TRANSACTION%' OR  t.details like 'NFS ATM REVERSAL%' OR "
                    + "t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR  t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NORMAL REVERSAL TRANSACTION' OR  t.details like 'NFS REVERSAL TRANSACTION%'))) and c.custid in (" + custIds + ") "
                    + "union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + preFrDt + "' and '" + preToDt + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between "
                    + "'" + preFrDt + "' and '" + preToDt + "' and (t.trantype = 0 and t.ty = 1 OR  (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR  t.details like 'NFS ATM WITHDRAWAL%' OR "
                    + "t.details like 'ATM NORMAL TRANSACTION%' OR  t.details like 'NFS ATM REVERSAL%' OR "
                    + "t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR  t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NORMAL REVERSAL TRANSACTION' OR  t.details like 'NFS REVERSAL TRANSACTION%'))) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + preFrDt + "' and '" + preToDt + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between "
                    + "'" + preFrDt + "' and '" + preToDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + preFrDt + "' and '" + preToDt + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between "
                    + "'" + preFrDt + "' and '" + preToDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + preFrDt + "' and '" + preToDt + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between "
                    + "'" + preFrDt + "' and '" + preToDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + preFrDt + "' and '" + preToDt + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between "
                    + "'" + preFrDt + "' and '" + preToDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + ") g) as augAmt,"
                    + "(select ifnull(sum(g.amt),0) from "
                    + "(select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('SB') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1 OR  (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR  t.details like 'NFS ATM WITHDRAWAL%' OR "
                    + "t.details like 'ATM NORMAL TRANSACTION%' OR  t.details like 'NFS ATM REVERSAL%' OR "
                    + "t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR  t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NORMAL REVERSAL TRANSACTION' OR  t.details like 'NFS REVERSAL TRANSACTION%'))) and c.custid in (" + custIds + ") "
                    + "union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1 OR  (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR  t.details like 'NFS ATM WITHDRAWAL%' OR "
                    + "t.details like 'ATM NORMAL TRANSACTION%' OR  t.details like 'NFS ATM REVERSAL%' OR "
                    + "t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR  t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NORMAL REVERSAL TRANSACTION' OR  t.details like 'NFS REVERSAL TRANSACTION%'))) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt-cramt) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and (t.trantype = 0 and t.ty = 1  OR  (t.ty=0 AND t.trandesc = 9)) and c.custid in (" + custIds + ") ";
            if (auth.equals("N")) {
                query = query + " union "
                        + "select sum(dramt) as amt from td_accountmaster a, customerid c, tokentable_debit t where a.acno = c.acno and (a.closingdate is null or a.closingdate "
                        + "between '" + frDt + "' and '" + toDt + "') and t.acno = a.acno and auth = 'N' and dt between '" + frDt + "' and '" + toDt + "' and t.trantype = 0 and t.ty = 1 "
                        + "and c.custid in (" + custIds + ") "
                        + " union "
                        + "select sum(dramt) as amt from accountmaster a, customerid c, tokentable_debit t where a.acno = c.acno and (a.closingdate is null or a.closingdate "
                        + "between '" + frDt + "' and '" + toDt + "') and t.acno = a.acno and auth = 'N' and dt between '" + frDt + "' and '" + toDt + "' and t.trantype = 0 and t.ty = 1 "
                        + "and c.custid in (" + custIds + ")";
            }
            query = query + ") g) as curAmt,"
                    + "(select ifnull(sum(g.amt),0) from "
                    + "(select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('SB') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and t.ty = 1 and  t.trandesc = 136 and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and t.ty = 1 and  t.trandesc = 136 and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and t.ty = 1 and  t.trandesc = 136 and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and t.ty = 1 and  t.trandesc = 136 and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and t.ty = 1 and  t.trandesc = 136 and c.custid in (" + custIds + ") "
                    + " union "
                    + "select sum(dramt) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate between '" + frDt + "' and '" + toDt + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between "
                    + "'" + frDt + "' and '" + toDt + "' and t.ty = 1 and  t.trandesc = 136 and c.custid in (" + custIds + ") ";
            if (auth.equals("N")) {
                query = query + " union "
                        + "select voucherno as amt from td_accountmaster a, customerid c, tokentable_debit t where a.acno = c.acno and (a.closingdate is null or a.closingdate "
                        + "between '" + frDt + "' and '" + toDt + "') and t.acno = a.acno and auth = 'N' and dt between '" + frDt + "' and '" + toDt + "' and t.trantype = 0 and t.ty = 1 "
                        + "and c.custid in (" + custIds + ") "
                        + " union "
                        + "select voucherno as amt from accountmaster a, customerid c, tokentable_debit t where a.acno = c.acno and (a.closingdate is null or a.closingdate "
                        + "between '" + frDt + "' and '" + toDt + "') and t.acno = a.acno and auth = 'N' and dt between '" + frDt + "' and '" + toDt + "' and t.trantype = 0 and t.ty = 1 "
                        + "and c.custid in (" + custIds + ")";
            }
            query = query + ") g) as tdsAmt";
            System.out.println(query);
            List list = em.createNativeQuery(query).getResultList();

            //BigDecimal tdsAmt = new BigDecimal(0);
            BigDecimal augCashWithdrawal = new BigDecimal(0);
            BigDecimal curCashWithdrawal = new BigDecimal(0);
            BigDecimal tdsDeducted = new BigDecimal(0);

            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                augCashWithdrawal = new BigDecimal(ele.get(0).toString().trim());
                curCashWithdrawal = new BigDecimal(ele.get(1).toString().trim());
                tdsDeducted = new BigDecimal(ele.get(2).toString().trim());
            }
            if (augCashWithdrawal.compareTo(new BigDecimal(10000000)) >= 0) {
                augCashWithdrawal = new BigDecimal(10000000);
            }

            BigDecimal totalCashWithdrawal = augCashWithdrawal.add(curCashWithdrawal).add(curAmt);
            BigDecimal tdsToBeDeducted = new BigDecimal(0);
            if (totalCashWithdrawal.compareTo(new BigDecimal(10000000)) >= 0) {
                tdsToBeDeducted = ((totalCashWithdrawal.subtract(new BigDecimal(10000000))).multiply(new BigDecimal(2))).divide(new BigDecimal(100));
            }
            if (tdsToBeDeducted.compareTo(tdsDeducted) > 0) {
                tdsToBeDeducted = tdsToBeDeducted.subtract(tdsDeducted);
            } else {
                tdsToBeDeducted = new BigDecimal(0);
            }
            return tdsToBeDeducted;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getTdsToBeDeductedForCustomer(String panNo, String custId, String fromDt, String toDt, BigDecimal curAmt, String auth) throws ApplicationException {
        try {
            if (panNo.length() == 10) {
                List custIdList = em.createNativeQuery("select ifnull(customerid,'') from cbs_customer_master_detail where customerid<>'" + custId + "' "
                        + "and pan_girnumber = '" + panNo + "' and SuspensionFlg <> 'Y'").getResultList();
                for (int i = 0; i < custIdList.size(); i++) {
                    Vector element = (Vector) custIdList.get(i);
                    custId = custId + "," + Long.parseLong(element.get(0).toString());
                }
            }
            return getCustomerTdsOnCashWithdrawal(custId, fromDt, toDt, curAmt, auth);
        } catch (ApplicationException | NumberFormatException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getTdsToBeDeductedForCustomer(String acNo, String fromDt, String toDt, BigDecimal curAmt, String auth) throws ApplicationException {
        try {
            List custIdList = em.createNativeQuery("select cd1.customerid from cbs_customer_master_detail cd1,(select cd.PAN_GIRNumber as pan "
                    + "from customerid c, cbs_customer_master_detail cd where acno = '" + acNo + "' "
                    + "and c.custid = cast(cd.customerid as unsigned)) a where cd1.pan_girNumber = a.pan and cd1.SuspensionFlg <> 'Y' and length(cd1.PAN_GIRNumber)=10 "
                    + "union "
                    + "select custid from customerid where acno = '" + acNo + "'").getResultList();
            String custId = "";
            for (int i = 0; i < custIdList.size(); i++) {
                Vector element = (Vector) custIdList.get(i);
                if (custId.equals("")) {
                    custId = element.get(0).toString();
                } else {
                    custId = custId + "," + element.get(0).toString();
                }
            }
            if (custId.equals("")) {
                throw new ApplicationException("Customer Id does not exist for " + acNo);
            }
            return getCustomerTdsOnCashWithdrawal(custId, fromDt, toDt, curAmt, auth);
        } catch (ApplicationException | NumberFormatException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String tdsDeductionForCashWithdrawal(String partyNature, String partyAc, double tdsAmt, String enterBy, String orgnBrCode) throws ApplicationException {
        String consoleGlHead = "", orgnIsoAcNo = "";
        // Float recNo = 0f;
        try {
            List dataList = em.createNativeQuery("select acno from abb_parameter_info "
                    + "where purpose in( 'INTERSOLE ACCOUNT')").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }
            Vector dataVec = (Vector) dataList.get(0);
            String isoAccount = dataVec.get(0).toString();

            String tdsPaybleHead = ftsPosting.getCodeFromCbsParameterInfo("TDS-HEAD-CASH-WITHDRAWAL");
            tdsPaybleHead = orgnBrCode.concat(tdsPaybleHead);

            dataList = em.createNativeQuery("select alphacode from branchmaster where "
                    + "brncode = '" + Integer.parseInt(orgnBrCode) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define alphacode for-->" + orgnBrCode);
            }
            dataVec = (Vector) dataList.get(0);

            String orgnAlphaCode = dataVec.get(0).toString();

            String partyBrCode = partyAc.substring(0, 2);
            consoleGlHead = partyBrCode.concat(isoAccount);
            orgnIsoAcNo = orgnBrCode.concat(isoAccount);

            String narration = " 2% TDS deduction on Cash withdrawal";
            if (!orgnBrCode.equals(partyBrCode)) {
                narration = "AT " + orgnAlphaCode + ":" + narration;
            }

            //Party A/c
            float recNo = ftsPosting.getRecNo();
            float trsNo = ftsPosting.getTrsNo();

            String result = ftsPosting.insertRecons(partyNature, partyAc, 1, tdsAmt,
                    ymd.format(new Date()), ymd.format(new Date()), 2, narration, enterBy, trsNo, "",
                    recNo, "Y", "System", 136, 3, "", "", 0f, "", "A", 0,
                    "", 0f, "", null, orgnBrCode, partyBrCode, 0, "", "", "");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Insertion Problem In Recons For A/c No-->" + partyAc);
            }

            result = ftsPosting.updateBalance(partyNature, partyAc, 0, tdsAmt, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Balance Updation Problem For A/c No-->" + partyAc);
            }

            if (!orgnBrCode.equals(partyBrCode)) {

                recNo = recNo + 1;
                int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, cramt, dramt, balance, "
                        + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                        + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                        + "advicebrncode) values('" + consoleGlHead + "',0,'" + ymd.format(new Date()) + "',"
                        + "'" + ymd.format(new Date()) + "'," + tdsAmt + ",0, 0,2,'" + narration + "',"
                        + "0,'','19000101','" + enterBy + "','Y'," + recNo + ",3,'System',"
                        + "" + trsNo + ",136,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
                }

                //Origin Branch ISO.
                recNo = recNo + 1;
                n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, cramt, dramt, balance, "
                        + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                        + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                        + "advicebrncode) values('" + orgnIsoAcNo + "',1,'" + ymd.format(new Date()) + "',"
                        + "'" + ymd.format(new Date()) + "',0," + tdsAmt + ", 0,2,'" + narration + "',"
                        + "0,'','19000101','" + enterBy + "','Y'," + recNo + ",3,'System',"
                        + "" + trsNo + ",136,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
                }
            }
            narration = narration + " for acno " + partyAc;
            recNo = recNo + 1;
            int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, cramt, dramt, balance, "
                    + "trantype,details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, "
                    + "tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,"
                    + "advicebrncode) values('" + tdsPaybleHead + "',0,'" + ymd.format(new Date()) + "',"
                    + "'" + ymd.format(new Date()) + "'," + tdsAmt + ",0, 0,2,'" + narration + "',"
                    + "0,'','19000101','" + enterBy + "','Y'," + recNo + ",3,'System',"
                    + "" + trsNo + ",136,0,'','',now(),'" + orgnBrCode + "','" + partyBrCode + "',0,'','','' )").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + consoleGlHead);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }
}
