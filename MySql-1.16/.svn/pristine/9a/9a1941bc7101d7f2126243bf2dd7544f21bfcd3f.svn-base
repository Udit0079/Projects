/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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

@Stateless(mappedName = "ReconcilationManagementFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class ReconcilationManagementFacade implements ReconcilationManagementFacadRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    @EJB
    CommonReportMethodsRemote commonReport;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    NumberFormat formatter = new DecimalFormat("#.##");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    Date currDt = new Date();

//    public String autoReconcilation(String currentDate) throws ApplicationException {
//        String message = "System Error Occured During Reconsilation Process.";
//        try {
//            Float recnosilationAmount = 0f, Org_Instrument_Amount = 0f, Res_Instrument_Amount = 0f, batchno = 0f;
//            String txnid1 = "";
//            String Org_Originating_Branch = "", Org_Origin_Dt = "", Org_Instrument_Type = "", Org_Instrument_No = "", Org_Instrument_Date = "", datetime = "", Org_Responding_Branch = "", Org_Entry_Type = "", Org_Ty = "";
//            String Res_Responding_Branch = "", Res_Responded_Dt = "", Res_Instrument_Type = "", Res_Instrument_No = "", Res_Instrument_Date = "", Res_Originating_Branch = "", Res_Entry_Type, Res_Ty = "";
//            List l1 = em.createNativeQuery("select * from gl_recon where dt='" + currentDate + "' and trandesc=999 and substring(ACNO,3,10) in (select acno from abb_parameter_info where purpose='HEAD OFFICE')").getResultList();
//
//            for (int i = 0; i < l1.size(); i++) {
//                Vector v1 = (Vector) l1.get(i);
//                if (v1.get(0).toString() == null || v1.get(0).toString().equals("")) {
//                    txnid1 = "";
//                } else {
//                    txnid1 = v1.get(0).toString();
//                }
//
//                if (Float.parseFloat(v1.get(9).toString()) == 0.0 || Float.parseFloat(v1.get(9).toString()) == 0) {
//                    batchno = 0f;
//                } else {
//                    batchno = Float.parseFloat(v1.get(9).toString());
//                }
//
//                if (v1.get(27).toString() == null || v1.get(27).toString().equals("")) {
//                    Res_Responding_Branch = "";
//                } else {
//                    Res_Responding_Branch = v1.get(27).toString();
//                }
//
//                Res_Responded_Dt = currentDate;
//                Res_Instrument_Type = "";
//                if (v1.get(10).toString() == null || v1.get(10).toString().equals("")) {
//                    Res_Instrument_No = "";
//                } else {
//                    Res_Instrument_No = v1.get(10).toString();
//                }
//
//                if (Float.parseFloat(v1.get(4).toString()) == 0.0 || Float.parseFloat(v1.get(4).toString()) == 0) {
//                    Res_Instrument_Amount = 0f;
//                } else {
//                    Res_Instrument_Amount = Float.parseFloat(v1.get(4).toString());
//                }
//
//                Res_Instrument_Date = currentDate;
//
//                if (v1.get(26).toString() == null || v1.get(26).toString().equals("")) {
//                    Res_Originating_Branch = "";
//                } else {
//                    Res_Originating_Branch = v1.get(26).toString();
//                }
//
//                if (v1.get(7).toString() == null || v1.get(7).toString().equals("")) {
//                    Res_Entry_Type = "";
//                } else {
//                    Res_Entry_Type = v1.get(7).toString();
//                }
//
//                if (v1.get(6).toString() == null || v1.get(6).toString().equals("")) {
//                    Res_Ty = "";
//                } else {
//                    Res_Ty = v1.get(6).toString();
//                }
//
//                ///////////////////// CHECKING
//                if (Res_Instrument_Amount != 0.0 || Res_Instrument_Amount != 0) {
//                    recnosilationAmount = Res_Instrument_Amount;
//                    List l2 = em.createNativeQuery("select * from gl_recon where dt='" + currentDate + "' and trandesc=999 and substring(ACNO,3,10) in (select acno from abb_parameter_info where purpose='HEAD OFFICE') and txnid not in ('" + txnid1 + "') and trsno=" + batchno + " and dest_brnid='" + Res_Responding_Branch + "'").getResultList();
//                    {
//                        for (int j = 0; j < l2.size(); j++) {
//                            Vector v2 = (Vector) l2.get(j);
//                            if (v2.get(26).toString() == null || v2.get(26).toString().equals("")) {
//                                Org_Originating_Branch = "";
//                            } else {
//                                Org_Originating_Branch = v2.get(26).toString();
//                            }
//
//                            Org_Origin_Dt = currentDate;
//                            Org_Instrument_Type = "";
//
//                            if (v2.get(10).toString() == null || v2.get(10).toString().equals("")) {
//                                Org_Instrument_No = "";
//                            } else {
//                                Org_Instrument_No = v2.get(10).toString();
//                            }
//
//                            if (Float.parseFloat(v2.get(5).toString()) == 0.0 || Float.parseFloat(v2.get(5).toString()) == 0) {
//                                Org_Instrument_Amount = 0f;
//                            } else {
//                                Org_Instrument_Amount = Float.parseFloat(v2.get(5).toString());
//                            }
//
//                            Org_Instrument_Date = currentDate;
//                            datetime = currentDate;
//
//                            if (v2.get(27).toString() == null || v2.get(27).toString().equals("")) {
//                                Org_Responding_Branch = "";
//                            } else {
//                                Org_Responding_Branch = v2.get(27).toString();
//                            }
//
//                            if (v2.get(7).toString() == null || v2.get(7).toString().equals("")) {
//                                Org_Entry_Type = "";
//                            } else {
//                                Org_Entry_Type = v2.get(7).toString();
//                            }
//
//                            if (v2.get(6).toString() == null || v2.get(6).toString().equals("")) {
//                                Org_Ty = "";
//                            } else {
//                                Org_Ty = v2.get(6).toString();
//                            }
//
//
//                            if (String.valueOf(Org_Instrument_Amount).equals(String.valueOf(recnosilationAmount))) {
//                                Query horecnosileEntryList = em.createNativeQuery("insert into ho_reconsile_entry(Org_Originating_Branch,Org_Origin_Dt,Org_Instrument_Type,Org_Instrument_No,Org_Instrument_Amount,Org_Instrument_Date,Org_Responding_Branch,Org_Entry_Type,Org_Ty,Res_Responding_Branch,Res_Responded_Dt,Res_Instrument_Type,Res_Instrument_No,Res_Instrument_Amount,Res_Instrument_Date,Res_Originating_Branch,Res_Entry_Type,Res_Ty)  values ('" + Org_Originating_Branch + "','" + Org_Origin_Dt + "','" + Org_Instrument_Type + "','" + Org_Instrument_No + "'," + Org_Instrument_Amount + ",'" + Org_Instrument_Date + "','" + Org_Responding_Branch + "','" + Org_Entry_Type + "','" + Org_Ty + "','" + Res_Responding_Branch + "','" + Res_Responded_Dt + "','" + Res_Instrument_Type + "','" + Res_Instrument_No + "'," + Res_Instrument_Amount + ",'" + Res_Instrument_Date + "','" + Res_Originating_Branch + "','" + Res_Entry_Type + "','" + Res_Ty + "')");
//                                int k = horecnosileEntryList.executeUpdate();
//
//                                if (k == 1) {
//                                    break;
//                                }
//
//                                if (k <= 0) {
//                                    //ut.rollback();
//                                    return "Problem in recnociling  Ho Advice";
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            //ut.commit();
//            message = "Reconcilation process Completed";
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return message;
//    }
    public String checkForIntersoleAccounts(String currentDate) throws ApplicationException {
        String originIntersole = "";
        String message = "System Error Occured During Intersole Process.";
        float recno;
        float trsno;
        try {
            List brnList = em.createNativeQuery("select BrnCode from branchmaster").getResultList();
            if (brnList.isEmpty()) {
                return "Branch code is not defined";
            }
            for (int i = 0; i < brnList.size(); i++) {
                Vector brnListVector = (Vector) brnList.get(i);
                String brncode = brnListVector.get(0).toString();
                if (brncode.length() < 2) {
                    brncode = "0" + brncode;
                }

                List intersoleList = em.createNativeQuery("select acno from  abb_parameter_info where purpose='intersole account' ").getResultList();
                if (intersoleList.isEmpty() || intersoleList == null) {
                    continue;
                }
                Vector intersoleVector = (Vector) intersoleList.get(0);
                originIntersole = brncode + intersoleVector.get(0).toString();
                List headOfficeListOrigin = em.createNativeQuery("select acno from abb_parameter_info where purpose='HEAD OFFICE'").getResultList();
                if (headOfficeListOrigin.isEmpty() || headOfficeListOrigin == null) {
                    continue;
                }

                Vector hoCodeOrigin = (Vector) headOfficeListOrigin.get(0);
                String originHoAccount = brncode + hoCodeOrigin.get(0).toString();

                List intersoleBranchList = em.createNativeQuery("select * from gl_recon where acno='" + originIntersole + "' "
                        + "and dt='" + currentDate + "' and trandesc<>999 and trantype in (0,1) and org_brnid<>dest_brnid").getResultList();
                if (!intersoleBranchList.isEmpty()) {
                    for (int b = 0; b < intersoleBranchList.size(); b++) {
                        Vector v1 = (Vector) intersoleBranchList.get(b);
                        double drAmt1 = Double.parseDouble(v1.get(4).toString());
                        double crAmt1 = Double.parseDouble(v1.get(5).toString());

                        int tranType = Integer.parseInt(v1.get(7).toString());
                        String orgBrnId = v1.get(26).toString();
                        String destBrnId = v1.get(27).toString();
                        trsno = Float.parseFloat(v1.get(9).toString());
                        List branchnameList = em.createNativeQuery("select alphacode from branchmaster where brncode='"
                                + orgBrnId + "'").getResultList();
                        if (branchnameList.isEmpty() || branchnameList == null) {
                            return "Alphacode do not exist";
                        }
                        Vector branchnameVec = (Vector) branchnameList.get(0);
                        String orgBranchName = branchnameVec.get(0).toString();

                        List branchnameList1 = em.createNativeQuery("select alphacode from branchmaster where brncode='"
                                + destBrnId + "'").getResultList();
                        if (branchnameList1.isEmpty() || branchnameList1 == null) {
                            return "Alphacode do not exist";
                        }
                        Vector branchnameVec1 = (Vector) branchnameList1.get(0);
                        String destBranchName = branchnameVec1.get(0).toString();

                        if (drAmt1 != 0.0 || drAmt1 != 0) {
                            List l4 = em.createNativeQuery("select substring('" + originIntersole + "',1,2)").getResultList();
                            Vector v11 = (Vector) l4.get(0);
                            String originIntersoleCode = v11.get(0).toString();
                            String details = "";
                            if (originIntersoleCode.equals(orgBrnId)) {
                                if (tranType == 1) {
                                    details = "By Clg, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                } else {
                                    details = "By cash, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                }
                            } else {
                                if (tranType == 1) {
                                    details = "To Clg, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                } else {
                                    details = "To cash, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                }
                            }
                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();
                            Query q1 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,"
                                    + "trsno,instno,payby,iy,auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,"
                                    + "trantime,details,checkby,tran_id,term_id,org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values "
                                    + "('" + originIntersole + "',0,'" + currentDate + "','" + currentDate + "',0.0," + drAmt1 + ",0," + tranType + "," + recno + "," + trsno
                                    + ",'',3,'0','Y','SYSTEM','SYSTEM','','',999,0,'',now(),'" + details + "','',0,0,'"
                                    + orgBrnId + "','" + destBrnId + "',0,'','','')");
                            int a = q1.executeUpdate();
                            if (a <= 0) {
                                //ut.rollback();
                                return "Transaction problem in intersole account";
                            }
                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();
                            Query q2 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,"
                                    + "instno,payby,iy,auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,"
                                    + "details,checkby,tran_id,term_id,org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originHoAccount
                                    + "',0,'" + currentDate + "','" + currentDate + "'," + drAmt1 + ",0.0,1," + tranType + "," + recno + "," + trsno + ",'',3,'0','Y','SYSTEM',"
                                    + "'SYSTEM','','',999,0,'',now(),'" + details + "','',0,0,'" + orgBrnId + "','"
                                    + destBrnId + "',0,'','','')");
                            int c = q2.executeUpdate();
                            if (c <= 0) {
                                return "Transaction problem in intersole account";
                            }
                        }
                        if (crAmt1 != 0.0 || crAmt1 != 0) {
                            List l4 = em.createNativeQuery("select substring('" + originIntersole + "',1,2)").getResultList();
                            Vector v11 = (Vector) l4.get(0);
                            String originIntersoleCode = v11.get(0).toString();
                            String details = "";

                            if (originIntersoleCode.equals(orgBrnId)) {
                                if (tranType == 1) {
                                    details = "By Clg, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                } else {
                                    details = "By cash, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                }
                            } else {
                                if (tranType == 1) {
                                    details = "To Clg, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                } else {
                                    details = "To cash, at" + " " + orgBranchName + " " + "for" + " " + destBranchName;
                                }
                            }
                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();
                            Query q1 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,"
                                    + "instno,payby,iy,auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,"
                                    + "details,checkby,tran_id,term_id,org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originIntersole
                                    + "',0,'" + currentDate + "','" + currentDate + "'," + crAmt1 + ",'0.0',1," + tranType + "," + recno + "," + trsno + ",'',3,'0','Y','SYSTEM',"
                                    + "'SYSTEM','','',999,0,'','" + currentDate + "','" + details + "','',0,0,'" + orgBrnId + "','"
                                    + destBrnId + "',0,'','','')");
                            int a = q1.executeUpdate();
                            if (a <= 0) {
                                return "Transaction problem in intersole account";
                            }
                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();
                            Query q2 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,"
                                    + "instno,payby,iy,auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,details,"
                                    + "checkby,tran_id,term_id,org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originHoAccount + "',0,'"
                                    + currentDate + "','" + currentDate + "',0.0," + crAmt1 + ",0," + tranType + "," + recno + "," + trsno + ",'',3,'0','Y','SYSTEM','SYSTEM','','',"
                                    + "999,0,'','" + currentDate + "','" + details + "','',0,0,'" + orgBrnId + "','" + destBrnId + "',0,'','','')");
                            int d = q2.executeUpdate();
                            if (d <= 0) {
                                return "Transaction problem in intersole account";
                            }
                        }

                    }
                }
                /**
                 * *************************************************************************************************************************
                 * For handling transfer transactions
                 * **************************************************************************************************************************
                 */
                List transferList = em.createNativeQuery("select * from gl_recon where acno='" + originIntersole + "' and dt='"
                        + currentDate + "' and trandesc<>999 and trantype=2 and org_brnid<>dest_brnid").getResultList();

                if (!transferList.isEmpty()) {
                    for (int K = 0; K < transferList.size(); K++) {
                        Vector v1 = (Vector) transferList.get(K);
                        double drAmt1 = Double.parseDouble(v1.get(4).toString());
                        double crAmt1 = Double.parseDouble(v1.get(5).toString());

                        String orgBrnId = v1.get(26).toString();
                        String destBrnId = v1.get(27).toString();
                        String batchTrsno = v1.get(9).toString();

                        if (orgBrnId.equals(destBrnId)) {
                            List distinctidList = em.createNativeQuery("select org_brnid,dest_brnid from gl_recon where dt='" + currentDate
                                    + "' and trandesc<>999 and trsno='" + batchTrsno + "' and org_brnid<>dest_brnid").getResultList();
                            Vector distinctV1 = (Vector) distinctidList.get(0);
                            orgBrnId = distinctV1.get(0).toString();
                            destBrnId = distinctV1.get(1).toString();
                        }

                        if (drAmt1 != 0.0 || drAmt1 != 0) {
                            List l4 = em.createNativeQuery("select substring('" + originIntersole + "',1,2)").getResultList();
                            Vector v11 = (Vector) l4.get(0);
                            String creditCode = v11.get(0).toString();
                            String debitCode = "";

                            if (creditCode.equals(orgBrnId)) {
                                debitCode = destBrnId;
                            } else {
                                debitCode = orgBrnId;
                            }

                            List creditCodeList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + creditCode + "'").getResultList();
                            if (creditCodeList.isEmpty() || creditCodeList == null) {
                                return "Alphacode do not exist";
                            }
                            Vector creditcodeVec = (Vector) creditCodeList.get(0);
                            String creditCodeName = creditcodeVec.get(0).toString();

                            List debitCodeList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + debitCode + "'").getResultList();
                            if (debitCodeList.isEmpty() || debitCodeList == null) {
                                return "Alphacode do not exist";
                            }
                            Vector debitCodeVec = (Vector) debitCodeList.get(0);
                            String debitCodeName = debitCodeVec.get(0).toString();

                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();

                            String details = "By trf," + " " + "Cr A/c at" + creditCodeName + " " + "Dr A/c for" + debitCodeName;
                            Query q1 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,instno,payby,iy,"
                                    + "auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,details,checkby,tran_id,term_id,"
                                    + "org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originIntersole + "',0,'" + currentDate + "','" + currentDate + "',0.0," + drAmt1
                                    + ",0,2," + recno + "," + batchTrsno + ",'',3,'0','Y','SYSTEM','SYSTEM','','',999,0,'',now(),'" + details
                                    + "','',0,0,'" + orgBrnId + "','" + destBrnId + "',0,'','','')");
                            int a = q1.executeUpdate();
                            if (a <= 0) {
                                return "Transaction problem in intersole account";
                            }
                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();

                            Query q2 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,instno,payby,iy,"
                                    + "auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,details,checkby,tran_id,term_id,"
                                    + "org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originHoAccount + "',0,'" + currentDate + "','" + currentDate + "'," + drAmt1
                                    + ",0.0,1,2," + recno + "," + batchTrsno + ",'',3,'0','Y','SYSTEM','SYSTEM','','',999,0,'',now(),'"
                                    + details + "','',0,0,'" + orgBrnId + "','" + destBrnId + "',0,'','','')");
                            int b = q2.executeUpdate();
                            if (b <= 0) {
                                return "Transaction problem in intersole account";
                            }
                        }

                        if (crAmt1 != 0.0 || crAmt1 != 0) {

                            List l4 = em.createNativeQuery("select substring('" + originIntersole + "',1,2)").getResultList();
                            Vector v11 = (Vector) l4.get(0);
                            String debitCode = v11.get(0).toString();
                            String creditCode = "";
                            if (debitCode.equals(orgBrnId)) {
                                creditCode = destBrnId;
                            } else {
                                creditCode = orgBrnId;
                            }

                            List creditCodeList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + creditCode + "'").getResultList();
                            if (creditCodeList.isEmpty() || creditCodeList == null) {
                                return "Alphacode do not exist";
                            }
                            Vector creditcodeVec = (Vector) creditCodeList.get(0);
                            String creditCodeName = creditcodeVec.get(0).toString();

                            List debitCodeList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + debitCode + "'").getResultList();
                            if (debitCodeList.isEmpty() || debitCodeList == null) {
                                return "Alphacode do not exist";
                            }
                            Vector debitCodeVec = (Vector) debitCodeList.get(0);
                            String debitCodeName = debitCodeVec.get(0).toString();

                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();

                            String details = "By trf," + "for Dr A/c" + debitCodeName + " " + "Cr A/c at" + " " + creditCodeName;
                            Query q1 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,instno,payby,iy,"
                                    + "auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,details,checkby,tran_id,term_id,"
                                    + "org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originIntersole + "',0,'" + currentDate + "','" + currentDate + "'," + crAmt1
                                    + ",'0.0',1,2," + recno + "," + batchTrsno + ",'',3,'0','Y','SYSTEM','SYSTEM','','',999,0,'',now(),'"
                                    + details + "','',0,0,'" + orgBrnId + "','" + destBrnId + "',0,'','','')");
                            int a = q1.executeUpdate();
                            if (a <= 0) {
                                return "Transaction problem in intersole account";
                            }

                            //recno = ftsPostRemote.getRecNo(brncode);
                            recno = ftsPostRemote.getRecNo();
                            Query q2 = em.createNativeQuery("insert into gl_recon (acno,balance,dt,valuedt,dramt,cramt,ty,trantype,recno,trsno,instno,payby,iy,"
                                    + "auth,EnterBy,authby,tokenpaidby,tokenpaid,trandesc,tokenno,subtokenno,trantime,details,checkby,tran_id,term_id,"
                                    + "org_brnid,dest_brnid,clgreason,favorof,adviceNo,adviceBrnCode) values ('" + originHoAccount + "',0,'" + currentDate + "','" + currentDate + "',0.0," + crAmt1
                                    + ",0,2," + recno + "," + batchTrsno + ",'',3,'0','Y','SYSTEM','SYSTEM','','',999,0,'',now(),'"
                                    + details + "','',0,0,'" + orgBrnId + "','" + destBrnId + "',0,'','','')");
                            int b = q2.executeUpdate();
                            if (b <= 0) {
                                return "Transaction problem in intersole account";
                            }
                        }

                    }
                }
            }
            message = "Intersole process Completed";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return message;
    }

//    public String hoConsolidateEntry(String enterBy) throws ApplicationException {
//        //UserTransaction ut = context.getUserTransaction();
//        Integer seqno = 0;
//        Date d1 = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String date = sdf.format(d1);
//        try {
//            //ut.begin();
//            List branchList = em.createNativeQuery("select brncode from branchmaster  where alphacode not in ('HO','CELL')").getResultList();
//            if (branchList.isEmpty() || branchList == null) {
//                //ut.rollback();
//                return "Branch code is not defined..";
//            }
//            for (int i = 0; i < branchList.size(); i++) {
//                Vector v1 = (Vector) branchList.get(i);
//                String brnCode = v1.get(0).toString();
//                if (brnCode.length() < 2) {
//                    brnCode = "0" + brnCode;
//                }
//                /**
//                 * ******************************************************************************************************************
//                 * Check for existing branch
//                 * *****************************************************************************************************************
//                 */
//                List selectForAllBranches = em.createNativeQuery("SELECT 1 FROM ca_recon WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "' UNION "
//                        + "SELECT 1 FROM loan_recon  WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "' UNION "
//                        + "SELECT 1 FROM ddstransaction WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "' UNION "
//                        + "SELECT 1 FROM td_recon WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "'  UNION "
//                        + "SELECT 1 FROM gl_recon  WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "' UNION "
//                        + "SELECT 1 FROM rdrecon WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "' UNION  "
//                        + "SELECT 1 FROM recon WHERE SUBSTRING(ACNO,1,2)='" + brnCode + "'").getResultList();
//                if (selectForAllBranches.isEmpty()) {
//                    continue;
//                }
//                List glHeadList = em.createNativeQuery("select distinct(glhead) from accounttypemaster where acctnature<>'" + CbsConstant.PAY_ORDER + "'  and glhead<>'' ").getResultList();
//                if (glHeadList.isEmpty() || glHeadList == null) {
//                    //ut.rollback();
//                    return "Account nature is not defined..";
//                }
//                for (int j = 0; j < glHeadList.size(); j++) {
//                    Vector v2 = (Vector) glHeadList.get(j);
//                    String glHead = v2.get(0).toString();
//                    String table = "";
//                    String table1 = null;
//
//                    List acctNtreCodeList = em.createNativeQuery("select acctnature,acctcode from accounttypemaster WHERE glhead='" + glHead + "'").getResultList();
//                    if (acctNtreCodeList.isEmpty() || acctNtreCodeList == null) {
//                        //ut.rollback();
//                        return "No account nature exist for " + glHead;
//                    }
//
//                    Vector acctVector = (Vector) acctNtreCodeList.get(0);
//                    String accountNature = acctVector.get(0).toString();
//                    String acctCode = acctVector.get(1).toString();
//                    // String brnacct = brnCode + acctCode;
//
//                    if (accountNature.equals(CbsConstant.CURRENT_AC)) {
//                        table = "ca_recon";
//                        table1 = "accountmaster";
//                    }
//                    if (accountNature.equals(CbsConstant.DEMAND_LOAN)) {
//                        table = "loan_recon";
//                        table1 = "accountmaster";
//                    }
//                    if (accountNature.equals(CbsConstant.DEPOSIT_SC)) {
//                        table = "ddstransaction";
//                        table1 = "accountmaster";
//                    }
//                    if (accountNature.equals(CbsConstant.FIXED_AC) || accountNature.equals(CbsConstant.MS_AC)) {
//                        table = "td_recon";
//                        table1 = "td_accountmaster";
//                    }
//                    /* if (accountNature.equals(CbsConstant.GL_AC)) {
//                     table = "gl_recon";
//                     table1 = "gltable";
//                     }*/
//                    if (accountNature.equals(CbsConstant.RECURRING_AC)) {
//                        table = "rdrecon";
//                        table1 = "accountmaster";
//                    }
//                    if (accountNature.equals(CbsConstant.SAVING_AC)) {
//                        table = "recon";
//                        table1 = "accountmaster";
//                    }
//                    if (accountNature.equals(CbsConstant.TERM_LOAN)) {
//                        table = "loan_recon";
//                        table1 = "accountmaster";
//                    }
//                    List l1 = new ArrayList();
//                    if (accountNature.equalsIgnoreCase(CbsConstant.MS_AC) || accountNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
//
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "' and trantype=0 and ty=0 and dt='" + date + "' ").getResultList();
//                        Vector v3 = (Vector) l1.get(0);
//                        Double cashCredit = Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "' and trantype=0 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double cashDebit = Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "' and trantype=1 and ty=0  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double ClearingCredit = Double.parseDouble(v3.get(0).toString());
//
//
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "' and trantype=1 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double ClearingDebit = Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "'  and trantype=2 and ty=0  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double Transfercredit = Double.parseDouble(v3.get(0).toString());
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "'  and trantype=8  and ty=0  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Transfercredit = Transfercredit + Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "'  and trantype=2 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double TransferDebit = Double.parseDouble(v3.get(0).toString());
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " tr, " + table1 + " td where td.acno=tr.acno and td.accttype='" + acctCode + "' and td.curbrcode='" + brnCode + "' and trantype=8 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        TransferDebit = TransferDebit + Double.parseDouble(v3.get(0).toString());
//                        glHead = brnCode + glHead + "01";
//                        List sno = em.createNativeQuery("select ifnull(max(sno),0)+1 from ho_consolidate_entry").getResultList();
//                        Vector v4 = (Vector) sno.get(0);
//                        seqno = Integer.parseInt(v4.get(0).toString());
//
//                        Query q1 = em.createNativeQuery("insert into ho_consolidate_entry (SNO,ACNO,DT,CASH_CR_AMT,CASH_DR_AMT,TRF_CR_AMT,TRF_DR_AMT,CLG_CR_AMT,CLG_DR_AMT,ACCTCODE,ENTERBY) values (" + seqno + ",'" + glHead + "','" + date + "'," + cashCredit + "," + cashDebit + "," + Transfercredit + "," + TransferDebit + "," + ClearingCredit + "," + ClearingDebit + ",'" + acctCode + "','" + enterBy + "')");
//                        int int1 = q1.executeUpdate();
//                        if (int1 <= 0) {
//                            //ut.rollback();
//                            return "problem in inserting data to ho_consolidate_entry";
//                        }
//
//                    } else {
//
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "' and trantype=0 and ty=0 and dt='" + date + "' ").getResultList();
//                        Vector v3 = (Vector) l1.get(0);
//                        Double cashCredit = Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "' and trantype=0 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double cashDebit = Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "' and trantype=1 and ty=0  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double ClearingCredit = Double.parseDouble(v3.get(0).toString());
//
//
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "' and trantype=1 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double ClearingDebit = Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "' and trantype=2 and ty=0  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double Transfercredit = Double.parseDouble(v3.get(0).toString());
//                        l1 = em.createNativeQuery("select ifnull(sum(cramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "'  and trantype=8  and ty=0  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Transfercredit = Transfercredit + Double.parseDouble(v3.get(0).toString());
//
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "'  and trantype=2 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        Double TransferDebit = Double.parseDouble(v3.get(0).toString());
//                        l1 = em.createNativeQuery("select ifnull(sum(dramt),0) from " + table + " re, " + table1 + " ac where ac.acno=re.acno and ac.accttype='" + acctCode + "' and ac.curbrcode='" + brnCode + "' and trantype=8 and ty=1  and dt='" + date + "'").getResultList();
//                        v3 = (Vector) l1.get(0);
//                        TransferDebit = TransferDebit + Double.parseDouble(v3.get(0).toString());
//                        glHead = brnCode + glHead + "01";
//                        List sno = em.createNativeQuery("select ifnull(max(sno),0)+1 from ho_consolidate_entry").getResultList();
//                        Vector v4 = (Vector) sno.get(0);
//                        seqno = Integer.parseInt(v4.get(0).toString());
//
//                        Query q1 = em.createNativeQuery("insert into ho_consolidate_entry (SNO,ACNO,DT,CASH_CR_AMT,CASH_DR_AMT,TRF_CR_AMT,TRF_DR_AMT,CLG_CR_AMT,CLG_DR_AMT,ACCTCODE,ENTERBY) values (" + seqno + ",'" + glHead + "','" + date + "'," + cashCredit + "," + cashDebit + "," + Transfercredit + "," + TransferDebit + "," + ClearingCredit + "," + ClearingDebit + ",'" + acctCode + "','" + enterBy + "')");
//                        int int1 = q1.executeUpdate();
//                        if (int1 <= 0) {
//                            //ut.rollback();
//                            return "problem in inserting data to ho_consolidate_entry";
//                        }
//                    }
//
//
//
//
//                }
//                //////////////////////////////////    CASE GL ////////////////////
//                List l8 = em.createNativeQuery("select distinct(acno) from  gl_recon  where substring(acno,1,2)='" + brnCode + "' and dt='" + date + "' ").getResultList();
//                if (l8.isEmpty() || l8 == null) {
//                    continue;
//                }
//                for (int k = 0; k < l8.size(); k++) {
//                    Vector v8 = (Vector) l8.get(k);
//                    String glhead = v8.get(0).toString();
//
//                    List l9 = em.createNativeQuery("select ifnull(sum(cramt),0) from gl_recon where acno='" + glhead + "' and  trantype=0 and ty=0  and dt='" + date + "'").getResultList();
//                    List l10 = em.createNativeQuery("select ifnull(sum(dramt),0) from gl_recon where acno='" + glhead + "' and  trantype=0 and ty=1 and dt='" + date + "'").getResultList();
//
//                    List l11 = em.createNativeQuery("select ifnull(sum(cramt),0) from gl_recon where acno='" + glhead + "' and  trantype=1 and ty=0  and dt='" + date + "'").getResultList();
//                    List l12 = em.createNativeQuery("select ifnull(sum(dramt),0) from gl_recon where acno='" + glhead + "' and  trantype=1 and ty=1  and dt='" + date + "'").getResultList();
//
//                    List l13 = em.createNativeQuery("select ifnull(sum(cramt),0) from gl_recon where acno='" + glhead + "' and  trantype=2 and ty=0  and dt='" + date + "'").getResultList();
//                    List l13interest = em.createNativeQuery("select ifnull(sum(cramt),0) from gl_recon where acno='" + glhead + "' and  trantype=8 and ty=0  and dt='" + date + "'").getResultList();
//
//                    List l14 = em.createNativeQuery("select ifnull(sum(dramt),0) from gl_recon where acno='" + glhead + "' and  trantype=2 and ty=1  and dt='" + date + "'").getResultList();
//                    List l14interest = em.createNativeQuery("select ifnull(sum(dramt),0) from gl_recon where acno='" + glhead + "' and  trantype=8 and ty=1  and dt='" + date + "'").getResultList();
//
//                    Vector v3 = (Vector) l9.get(0);
//                    Double cashCredit = Double.parseDouble(v3.get(0).toString());
//
//                    v3 = (Vector) l10.get(0);
//                    Double cashDebit = Double.parseDouble(v3.get(0).toString());
//
//                    v3 = (Vector) l11.get(0);
//                    Double ClearingCredit = Double.parseDouble(v3.get(0).toString());
//
//                    v3 = (Vector) l12.get(0);
//                    Double ClearingDebit = Double.parseDouble(v3.get(0).toString());
//
//                    v3 = (Vector) l13.get(0);
//                    Double Transfercredit = Double.parseDouble(v3.get(0).toString());
//                    v3 = (Vector) l13interest.get(0);
//                    Transfercredit = Transfercredit + Double.parseDouble(v3.get(0).toString());
//
//                    v3 = (Vector) l14.get(0);
//                    Double TransferDebit = Double.parseDouble(v3.get(0).toString());
//                    v3 = (Vector) l14interest.get(0);
//                    TransferDebit = TransferDebit + Double.parseDouble(v3.get(0).toString());
//
//                    List sno = em.createNativeQuery("select ifnull(max(sno),0)+1 from ho_consolidate_entry").getResultList();
//                    Vector v4 = (Vector) sno.get(0);
//                    seqno = Integer.parseInt(v4.get(0).toString());
//
//                    Query q1 = em.createNativeQuery("insert into ho_consolidate_entry (SNO,ACNO,DT,CASH_CR_AMT,CASH_DR_AMT,TRF_CR_AMT,TRF_DR_AMT,CLG_CR_AMT,CLG_DR_AMT,ACCTCODE,ENTERBY) values (" + seqno + ",'" + glhead + "','" + date + "'," + cashCredit + "," + cashDebit + "," + Transfercredit + "," + TransferDebit + "," + ClearingCredit + "," + ClearingDebit + ",'" + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "','" + enterBy + "')");
//                    int int1 = q1.executeUpdate();
//                    if (int1 <= 0) {
//                        //ut.rollback();
//                        return "problem in inserting data to ho_consolidate_entry";
//                    }
//                }
//            }
//            //ut.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException(e);
//        }
//        return "Successful";
//    }
    public String adviceReconsilation(String currentDate) throws ApplicationException {
        String message = "true";
        try {
            List branchList = commonReport.getAlphacodeExcludingHo();
            if (!branchList.isEmpty()) {
                List adviceList = ftsPostRemote.getAdviceList(Integer.parseInt(SiplConstant.CLG_AD_HEAD.getValue()));
                if (!adviceList.isEmpty()) {
                    for (int i = 0; i < branchList.size(); i++) {
                        Vector brVector = (Vector) branchList.get(i);
                        String brncode = brVector.get(0).toString();
                        if (brncode.length() == 1) {
                            brncode = "0" + brncode;
                        }
                        for (int j = 0; j < adviceList.size(); j++) {
                            Vector adviceVector = (Vector) adviceList.get(j);
                            String adviceDesc = adviceVector.get(0).toString();
                            String branchAdviceHead = ftsPostRemote.getAcnoByPurpose(adviceDesc);
                            if (branchAdviceHead.equalsIgnoreCase("")) {
                                continue; /* Now again for next adviceDesc*/

                            } else {
                                List serviceHeadList = em.createNativeQuery("select service_branch_acno from cbs_relative_account where branch_acno = '" + branchAdviceHead + "'").getResultList();
                                if (!serviceHeadList.isEmpty()) {
                                    Vector serviceVector = (Vector) serviceHeadList.get(0);
                                    String serviceHead = serviceVector.get(0).toString().trim();

                                    branchAdviceHead = brncode + branchAdviceHead;  /*12 digit branch head*/

                                    String serviceBranchCode = commonReport.getBrncodeByAlphacode(SiplConstant.SERVICE_BR_AL_CODE.getValue());
                                    serviceHead = serviceBranchCode + serviceHead;  /*12 digit banker head*/

                                    BigDecimal balance = new BigDecimal("0");
                                    List outStandAmountList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon where dt ='" + currentDate + "' and auth='y' and acno='" + branchAdviceHead + "'").getResultList();
                                    if (!outStandAmountList.isEmpty()) {
                                        Vector balElement = (Vector) outStandAmountList.get(0);
                                        balance = new BigDecimal(formatter.format(balElement.get(0)));
                                    }

                                    if (balance.compareTo(new BigDecimal("0")) != 0) {  /*Only allowed if some amount in that head*/

                                        int cxTy = 0, sxTy = 0;
                                        String detailCx = "", detailSx = "";
                                        if (balance.compareTo(new BigDecimal("0")) == -1) {
                                            cxTy = 0;
                                            detailCx = "Amt trf w.r.t " + commonReport.getAcNameByAcno(branchAdviceHead) + " contra entry on " + dmy.format(currDt) + " to " + commonReport.getBranchNameByBrncode(serviceBranchCode) + " ,Acno " + serviceHead;
                                            sxTy = 1;
                                            detailSx = "Amt trf w.r.t " + commonReport.getAcNameByAcno(serviceHead) + " contra entry on " + dmy.format(currDt) + " by " + commonReport.getBranchNameByBrncode(brncode) + " ,Acno " + branchAdviceHead;
                                        } else {
                                            cxTy = 1;
                                            detailCx = "Amt trf w.r.t " + commonReport.getAcNameByAcno(branchAdviceHead) + " contra entry on " + dmy.format(currDt) + " by " + commonReport.getBranchNameByBrncode(serviceBranchCode) + " ,Acno " + serviceHead;
                                            sxTy = 0;
                                            detailSx = "Amt trf w.r.t " + commonReport.getAcNameByAcno(serviceHead) + " contra entry on " + dmy.format(currDt) + " to " + commonReport.getBranchNameByBrncode(brncode) + " ,Acno " + branchAdviceHead;
                                        }
                                        /*Transaction Handling Now*/
                                        Float trsno = ftsPostRemote.getTrsNo();
                                        Float recno = ftsPostRemote.getRecNo();
                                        message = interBranchFacade.cbsPostingSx(serviceHead, sxTy, ymd.format(new Date()), balance.abs().doubleValue(), 0f, 2, detailSx, 0f, "A", "", "",
                                                3, 0f, recno, 66, serviceBranchCode, brncode, "System", "System", trsno, "", "");

                                        if (message.substring(0, 4).equalsIgnoreCase("true")) {
                                            recno = ftsPostRemote.getRecNo();
                                            message = interBranchFacade.cbsPostingCx(branchAdviceHead, cxTy, ymd.format(new Date()), balance.abs().doubleValue(), 0f, 2, detailCx, 0f, "A", "", "",
                                                    3, 0f, recno, 66, brncode, brncode, "System", "System", trsno, "", "");

                                            if (message.substring(0, 4).equalsIgnoreCase("true")) {
                                                message = "true";
                                            } else {
                                                return message;
                                            }
                                        } else {
                                            return message;
                                        }
                                    }
                                } else {
                                    continue;   /* Now again for next adviceDesc*/

                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public String neftInwardOutwardReconsilation(String currentDate) throws ApplicationException {
        String message = "true";
        try {
            String bankerHead = ftsPostRemote.getAcnoByPurpose("SPONSOR-CA-BANKER-HEAD");
            if (!bankerHead.equals("")) {
                List brnCodeList = em.createNativeQuery("select brncode from branchmaster where alphacode='HO'").getResultList();
                if (!brnCodeList.isEmpty()) {
                    Vector element = (Vector) brnCodeList.get(0);
                    String brnCode = element.get(0).toString();

                    bankerHead = brnCode + bankerHead;
                    if (bankerHead.length() != 12) {
                        return message = "Please define proper SPONSOR-CA-BANKER-HEAD.";
                    }

                    String inwardHead = ftsPostRemote.getAcnoByPurpose("NEFT-RTGS");
                    String outwardHead = ftsPostRemote.getAcnoByPurpose("NEFT-RTGS-PAYMENT");

                    if (inwardHead.equals("") && outwardHead.equals("")) {
                        message = "Both inward and outward process can not be ignore.";
                    } else {
                        inwardHead = brnCode + inwardHead;
                        outwardHead = brnCode + outwardHead;

                        List totalAmountList = null;
                        Vector vectorAmount = null;
                        double amount = 0;
                        String details = "";
                        Integer bankHeadTy, commonTy;

                        Float trsno = ftsPostRemote.getTrsNo();
                        if (!inwardHead.equals("") && inwardHead.trim().length() == 12) {
                            totalAmountList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)),0) as decimal(16,2)) from gl_recon where dt ='" + currentDate + "' and auth='y' and trandesc = 66 and acno='" + inwardHead + "'").getResultList();
                            vectorAmount = (Vector) totalAmountList.get(0);
                            amount = Double.parseDouble(vectorAmount.get(0).toString());
                            if (amount != 0) {
                                if (amount < 0) {
                                    commonTy = 0;
                                    bankHeadTy = 1;
                                } else {
                                    commonTy = 1;
                                    bankHeadTy = 0;
                                }
                                details = "Neft-Rtgs Inward Banker Head Reconsilation";
                                message = ftsPostRemote.insertRecons(ftsPostRemote.getAccountNature(inwardHead), inwardHead, commonTy, Math.abs(amount), currentDate, currentDate, 2, details, "System", trsno, currentDate, ftsPostRemote.getRecNo(), "Y", "System", 66, 3, "", "", 0f, "", "", 0, "", 0f, "", "", brnCode, brnCode, 0, "", "", "");
                                if (message.equalsIgnoreCase("true")) {
                                    message = updateBalanceBasedOnTy(inwardHead, Math.abs(amount), commonTy);
                                    if (message.equalsIgnoreCase("true")) {
                                        message = ftsPostRemote.insertRecons(ftsPostRemote.getAccountNature(bankerHead), bankerHead, bankHeadTy, Math.abs(amount), currentDate, currentDate, 2, details, "System", trsno, currentDate, ftsPostRemote.getRecNo(), "Y", "System", 66, 3, "", "", 0f, "", "", 0, "", 0f, "", "", brnCode, brnCode, 0, "", "", "");
                                        if (message.equalsIgnoreCase("true")) {
                                            message = updateBalanceBasedOnTy(bankerHead, Math.abs(amount), bankHeadTy);
                                        } else {
                                            return message;
                                        }
                                    } else {
                                        return message;
                                    }
                                } else {
                                    return message;
                                }
                            }
                        }
                        if (message.equalsIgnoreCase("true") && !outwardHead.equals("") && outwardHead.trim().length() == 12) {
                            totalAmountList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)),0) as decimal(16,2)) from gl_recon where dt ='" + currentDate + "' and auth='y' and trandesc = 66 and acno='" + outwardHead + "'").getResultList();
                            vectorAmount = (Vector) totalAmountList.get(0);
                            amount = Double.parseDouble(vectorAmount.get(0).toString());
                            if (amount != 0) {
                                if (amount < 0) {
                                    commonTy = 0;
                                    bankHeadTy = 1;
                                } else {
                                    commonTy = 1;
                                    bankHeadTy = 0;
                                }
                                details = "Neft-Rtgs Outward Banker Head Reconsilation";
                                message = ftsPostRemote.insertRecons(ftsPostRemote.getAccountNature(outwardHead), outwardHead, commonTy, Math.abs(amount), currentDate, currentDate, 2, details, "System", trsno, currentDate, ftsPostRemote.getRecNo(), "Y", "System", 66, 3, "", "", 0f, "", "", 0, "", 0f, "", "", brnCode, brnCode, 0, "", "", "");
                                if (message.equalsIgnoreCase("true")) {
                                    message = updateBalanceBasedOnTy(outwardHead, Math.abs(amount), commonTy);
                                    if (message.equalsIgnoreCase("true")) {
                                        message = ftsPostRemote.insertRecons(ftsPostRemote.getAccountNature(bankerHead), bankerHead, bankHeadTy, Math.abs(amount), currentDate, currentDate, 2, details, "System", trsno, currentDate, ftsPostRemote.getRecNo(), "Y", "System", 66, 3, "", "", 0f, "", "", 0, "", 0f, "", "", brnCode, brnCode, 0, "", "", "");
                                        if (message.equalsIgnoreCase("true")) {
                                            message = updateBalanceBasedOnTy(bankerHead, Math.abs(amount), bankHeadTy);
                                        } else {
                                            return message;
                                        }
                                    } else {
                                        return message;
                                    }
                                } else {
                                    return message;
                                }
                            }
                        }
                    }
                } else {
                    message = "Please define Ho in branchmaster.";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public String neftInwardReturnReconsilation(String sponsorBankName, String currentDate) throws ApplicationException {
        String message = "true";
        try {
            List glHeadList = em.createNativeQuery("select iw_head,ow_head from cbs_auto_neft_details where process='AUTO' and NEFT_BANK_NAME = '"
                    + sponsorBankName + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("Please define the GL head in cbs_auto_neft_details for inward and outward NEFT");
            }

            Vector element = (Vector) glHeadList.get(0);
            String inwardHead = element.get(0).toString();
            String outwardHead = element.get(1).toString();

            if (inwardHead.equals("") && outwardHead.equals("")) {
                throw new ApplicationException("Both inward and outward process can not be ignore.");
            }

            List brnCodeList = em.createNativeQuery("select brncode from branchmaster where alphacode='HO'").getResultList();
            if (brnCodeList.isEmpty()) {
                throw new ApplicationException("Both inward and outward process can not be ignore.");
            }
            Vector vect = (Vector) brnCodeList.get(0);
            String brnCode = vect.get(0).toString();

            Float trsno = ftsPostRemote.getTrsNo();
            if (!inwardHead.equals("") && inwardHead.trim().length() == 12 && !outwardHead.equals("") && outwardHead.trim().length() == 12) {

                List totalAmountList = em.createNativeQuery("select ifnull(sum(amount),0) from neft_rtgs_status where status='Sponsor' and dt='" + currentDate + "' "
                        + "and reason <> 'THIS UTR ALREADY PROCESSED.'").getResultList();
                if (!totalAmountList.isEmpty()) {
                    Vector vectorAmount = (Vector) totalAmountList.get(0);

                    double amount = Double.parseDouble(vectorAmount.get(0).toString());
                    if (amount != 0) {
                        String details = "Neft-Rtgs Inward Return of dated" + currentDate;

                        message = ftsPostRemote.insertRecons(CbsConstant.PAY_ORDER, inwardHead, 1, Math.abs(amount), currentDate, currentDate, 2,
                                details, "System", trsno, currentDate, ftsPostRemote.getRecNo(), "Y", "System", 66, 3, "", "", 0f, "", "", 0, "", 0f, "", "", brnCode,
                                brnCode, 0, "", "", "");
                        if (!message.equalsIgnoreCase("true")) {
                            throw new ApplicationException(message);
                        }
                        message = updateBalanceBasedOnTy(inwardHead, Math.abs(amount), 1);
                        if (!message.equalsIgnoreCase("true")) {
                            throw new ApplicationException(message);
                        }

                        message = ftsPostRemote.insertRecons(CbsConstant.PAY_ORDER, outwardHead, 0, Math.abs(amount), currentDate, currentDate,
                                2, details, "System", trsno, currentDate, ftsPostRemote.getRecNo(), "Y", "System", 66, 3, "", "", 0f, "", "", 0, "", 0f, "", "",
                                brnCode, brnCode, 0, "", "", "");
                        if (!message.equalsIgnoreCase("true")) {
                            throw new ApplicationException(message);
                        }
                        message = updateBalanceBasedOnTy(outwardHead, Math.abs(amount), 0);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public String updateBalanceBasedOnTy(String head, double amount, Integer ty) throws ApplicationException {
        String message = "";
        try {
            if (ty == 0) {
                message = ftsPostRemote.updateBalance(ftsPostRemote.getAccountNature(head), head, amount, 0, "Y", "Y");
            } else if (ty == 1) {
                message = ftsPostRemote.updateBalance(ftsPostRemote.getAccountNature(head), head, 0, amount, "Y", "Y");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public String isoTxnReconsilationExceptIntersole(String todayDt) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acno from abb_parameter_info where purpose='HEAD OFFICE'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(0).toString().trim().equals("") || ele.get(0).toString().trim().length() != 10) {
                return "Please fill proper HO a/c in abb parameter info table";
            }
            String hoAccount = ele.get(0).toString().trim();

            Float recNo = ftsPostRemote.getRecNo();

            list = em.createNativeQuery("select acno,dramt,cramt,ty,trantype,trandesc,ifnull(details,'') as detail,trsno,"
                    + "payby,org_brnid,dest_brnid,ifnull(enterby,'') as enterby,ifnull(authby,'') as authby from "
                    + "recon where dt='" + todayDt + "' and iy=51 and org_brnid<>dest_brnid "
                    + "union all "
                    + "select acno,dramt,cramt,ty,trantype,trandesc,ifnull(details,'') as detail,trsno,payby,"
                    + "org_brnid,dest_brnid,ifnull(enterby,'') as enterby,ifnull(authby,'') as authby from "
                    + "ddstransaction where dt='" + todayDt + "' and iy=51 and org_brnid<>dest_brnid "
                    + "union all "
                    + "select acno,dramt,cramt,ty,trantype,trandesc,ifnull(details,'') as detail,trsno,payby,"
                    + "org_brnid,dest_brnid,ifnull(enterby,'') as enterby,ifnull(authby,'') as authby from "
                    + "loan_recon where dt='" + todayDt + "' and iy=51 and org_brnid<>dest_brnid "
                    + "union all "
                    + "select acno,dramt,cramt,ty,trantype,trandesc,ifnull(details,'') as detail,trsno,payby,"
                    + "org_brnid,dest_brnid,ifnull(enterby,'') as enterby,ifnull(authby,'') as authby from "
                    + "rdrecon where dt='" + todayDt + "' and iy=51 and org_brnid<>dest_brnid "
                    + "union all "
                    + "select acno,dramt,cramt,ty,trantype,trandesc,ifnull(details,'') as detail,trsno,payby,"
                    + "org_brnid,dest_brnid,ifnull(enterby,'') as enterby,ifnull(authby,'') as authby from "
                    + "ca_recon where dt='" + todayDt + "' and iy=51 and org_brnid<>dest_brnid "
                    + "union all "
                    + "select acno,dramt,cramt,ty,trantype,trandesc,ifnull(details,'') as detail,trsno,payby,"
                    + "org_brnid,dest_brnid,ifnull(enterby,'') as enterby,ifnull(authby,'') as authby from "
                    + "td_recon where dt='" + todayDt + "' and iy=51 and org_brnid<>dest_brnid ").getResultList();
            for (int i = 0; i < list.size(); i++) {
                ele = (Vector) list.get(i);
//                String acno = ele.get(0).toString().trim();
                BigDecimal dramt = new BigDecimal(ele.get(1).toString().trim());
                BigDecimal cramt = new BigDecimal(ele.get(2).toString().trim());
                int ty = Integer.parseInt(ele.get(3).toString().trim());
                int tranType = Integer.parseInt(ele.get(4).toString().trim());
                int tranDesc = Integer.parseInt(ele.get(5).toString().trim());
                String details = ele.get(6).toString().trim();
                int trsNo = Integer.parseInt(ele.get(7).toString().trim());
                double payBy = Double.parseDouble(ele.get(8).toString().trim());
                String orgnCode = ele.get(9).toString().trim();
                String destCode = ele.get(10).toString().trim();
                String enterBy = ele.get(11).toString().trim();
                String authBy = ele.get(12).toString().trim();

                if (ty == 0) {
                    int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,"
                            + "details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,"
                            + "tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,advicebrncode) "
                            + "values('" + destCode + hoAccount + "',1,'" + todayDt + "','" + todayDt + "',"
                            + "" + cramt.doubleValue() + ",0, 0," + tranType + ",'" + details + "',999,'',null,"
                            + "'" + enterBy + "','Y'," + recNo + "," + payBy + ",'" + authBy + "'," + trsNo + ","
                            + "" + tranDesc + ",0,'','',now(),'" + orgnCode + "','" + destCode + "',0,'','','' )").executeUpdate();
                    if (n <= 0) {
                        return "Insertion Problem in Gl Recon for A/c No :- " + destCode + hoAccount;
                    }
                    recNo = recNo + 1;
                    n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,"
                            + "details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,"
                            + "tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,advicebrncode) "
                            + "values('" + orgnCode + hoAccount + "',0,'" + todayDt + "','" + todayDt + "',"
                            + "0," + cramt.doubleValue() + ", 0," + tranType + ",'" + details + "',999,'',null,"
                            + "'" + enterBy + "','Y'," + recNo + "," + payBy + ",'" + authBy + "'," + trsNo + ","
                            + "" + tranDesc + ",0,'','',now(),'" + orgnCode + "','" + destCode + "',0,'','','' )").executeUpdate();
                    if (n <= 0) {
                        return "Insertion Problem in Gl Recon for A/c No :- " + orgnCode + hoAccount;
                    }
                    recNo = recNo + 1;
                } else if (ty == 1) {
                    int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,"
                            + "details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,"
                            + "tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,advicebrncode) "
                            + "values('" + destCode + hoAccount + "',0,'" + todayDt + "','" + todayDt + "',"
                            + "0," + dramt.doubleValue() + ", 0," + tranType + ",'" + details + "',999,'',null,"
                            + "'" + enterBy + "','Y'," + recNo + "," + payBy + ",'" + authBy + "'," + trsNo + ","
                            + "" + tranDesc + ",0,'','',now(),'" + orgnCode + "','" + destCode + "',0,'','','' )").executeUpdate();
                    if (n <= 0) {
                        return "Insertion Problem in Gl Recon for A/c No :- " + destCode + hoAccount;
                    }
                    recNo = recNo + 1;
                    n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,"
                            + "details, iy, instno, instdt, enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,"
                            + "tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,term_id,adviceno,advicebrncode) "
                            + "values('" + orgnCode + hoAccount + "',1,'" + todayDt + "','" + todayDt + "',"
                            + "" + dramt.doubleValue() + ",0, 0," + tranType + ",'" + details + "',999,'',null,"
                            + "'" + enterBy + "','Y'," + recNo + "," + payBy + ",'" + authBy + "'," + trsNo + ","
                            + "" + tranDesc + ",0,'','',now(),'" + orgnCode + "','" + destCode + "',0,'','','' )").executeUpdate();
                    if (n <= 0) {
                        return "Insertion Problem in Gl Recon for A/c No :- " + orgnCode + hoAccount;
                    }
                    recNo = recNo + 1;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }
}
