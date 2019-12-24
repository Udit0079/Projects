package com.cbs.facade.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.CashTokenBookGridFile;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DenominitionTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "CashTokenBookFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CashTokenBookFacade implements CashTokenBookFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsMethods;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnAuth;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List gridLoad(String brCode) throws ApplicationException {
        List grid = new ArrayList();
        try {
            List tempBdLt = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG='N' AND BRNCODE='" + brCode + "'").getResultList();
            String tempBd = ((Vector) tempBdLt.get(0)).get(0).toString();
//            Query selectQuery = em.createNativeQuery("select cast(tokenno as unsigned),subtokenno,acno,custname,dramt,enterby,Authby,cast(recno as unsigned),org_brnid,dest_brnid from recon_cash_d "
//                    + " where (tokenpaidby is null OR tokenpaidby='') and auth = 'Y' and ty=1 and trantype=0"
//                    + " AND DT='" + tempBd + "' AND org_brnid='" + brCode + "'"
//                    + " AND ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE " + " PURPOSE LIKE '%CASH IN HAND%') AND IY NOT IN (9999)"
//                    + " order by tokenno");
            Query selectQuery = em.createNativeQuery("select tokenNo,subtokenno,acno,custname,dramt,enterby,Authby,recNo,org_brnid,dest_brnid,JointName from ("
                    + " select cast(a.tokenno as unsigned) tokenNo,a.subtokenno,a.acno,a.custname,a.dramt,a.enterby,a.Authby, "
                    + " cast(a.recno as unsigned) recNo,a.org_brnid,a.dest_brnid,ifnull(concat(b.JtName1,' / ',b.JtName2,' / ',b.JtName3,' / ',b.JtName4),'') as JointName  "
                    + " from recon_cash_d a,accountmaster b where (a.tokenpaidby is null OR a.tokenpaidby='') and a.auth = 'Y' and a.ty=1 "
                    + " and a.trantype=0 and a.acno=b.acno AND a.DT='" + tempBd + "' AND a.org_brnid='" + brCode + "' "
                    + " AND a.ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE  PURPOSE LIKE '%CASH IN HAND%') "
                    + " AND IY NOT IN (9999) "
                    + " union all "
                    + " select cast(a.tokenno as unsigned) tokenNo,a.subtokenno,a.acno,a.custname,a.dramt,a.enterby,a.Authby, "
                    + " cast(a.recno as unsigned),a.org_brnid,a.dest_brnid,ifnull(concat(b.JtName1,' / ',b.JtName2,' / ',b.JtName3,' / ',b.JtName4),'') as JointName  "
                    + " from recon_cash_d a,td_accountmaster b where (a.tokenpaidby is null OR a.tokenpaidby='') and a.auth = 'Y' and a.ty=1 "
                    + " and a.trantype=0 and a.acno=b.acno AND a.DT='" + tempBd + "' AND a.org_brnid='" + brCode + "' "
                    + " AND a.ACNO NOT IN (SELECT DISTINCT ACNO FROM abb_parameter_info WHERE  PURPOSE LIKE '%CASH IN HAND%') "
                    + " AND IY NOT IN (9999) "
                    + " union all "
                    + " select cast(a.tokenno as unsigned),a.subtokenno,a.acno,a.custname,a.dramt,a.enterby,a.Authby,cast(a.recno as unsigned),a.org_brnid,a.dest_brnid, "
                    + " '' as JointName from recon_cash_d a,gltable b  "
                    + " where (a.tokenpaidby is null OR a.tokenpaidby='') and a.auth = 'Y' and a.ty=1 and a.trantype=0 and a.acno=b.acno AND a.DT='" + tempBd + "'  "
                    + " AND a.org_brnid='" + brCode + "' AND a.ACNO NOT IN(SELECT DISTINCT ACNO FROM abb_parameter_info WHERE  PURPOSE LIKE '%CASH IN HAND%')  "
                    + " AND IY NOT IN (9999) "
                    + " )f order by f.tokenno");

            grid = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return grid;
    }

    public String tokenPaidDebitWithCashier(List<CashTokenBookGridFile> gridDetail, String tokenpaidby, String enterDt, List<DDSDenominationGrid> denominationTable, boolean denominationRender, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0, var2 = 0, var3 = 0, var4 = 0, var5 = 0, var6 = 0;
            String actnature = "";
            if (gridDetail.isEmpty() || gridDetail == null) {
                ut.rollback();
                return "Grid is empty !!!";
            }
            if (tokenpaidby == null || tokenpaidby.equalsIgnoreCase("")) {
                ut.rollback();
                return "Token paid by could not be blank !!!";
            }
            double balance = 0.0d;
            int iy = 0;
            float SeqNo;
            for (int j = 0; j < gridDetail.size(); j++) {
                if (gridDetail.get(j).getTokenPaid().equalsIgnoreCase("Y")) {
                    if (gridDetail.get(j).getOrgBrnId() == null || gridDetail.get(j).getOrgBrnId().equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Origin branch ID should not be blank !!!";
                    }
                    if (gridDetail.get(j).getDestBrnId() == null || gridDetail.get(j).getDestBrnId().equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Destination branch ID should not be blank !!!";
                    }
                    balance = 0.0d;
                    //System.out.println("acno:=======" + gridDetail.get(j).getAcno());
                    if (gridDetail.get(j).getOrgBrnId().equalsIgnoreCase(gridDetail.get(j).getDestBrnId())) {
                        if (gridDetail.get(j).getSubTokenNo() == null || gridDetail.get(j).getSubTokenNo().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty sub token no !!!";
                        }
                        if (gridDetail.get(j).getTokenNo() == null || gridDetail.get(j).getTokenNo().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty token no !!!";
                        }
                        if (gridDetail.get(j).getRecNo() == null || gridDetail.get(j).getRecNo().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty rec no !!!";
                        }
                        if (gridDetail.get(j).getAcno() == null || gridDetail.get(j).getAcno().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty account number !!!";
                        }

                        actnature = ftsMethods.getAccountNature(gridDetail.get(j).getAcno());

                        Query updateReconCashDQ = em.createNativeQuery("update recon_cash_d set tokenpaidby='" + tokenpaidby + "' where recno=" + gridDetail.get(j).getRecNo() + " and tokenno=" + gridDetail.get(j).getTokenNo() + " and subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                        var = updateReconCashDQ.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Updation Problem of Current Recon !!!";
                        }
                        Query updateTokenTableDr = em.createNativeQuery("update tokentable_debit set tokenpaidby='" + tokenpaidby + "',TOKENPAID='Y' where recno=" + gridDetail.get(j).getRecNo() + " and tokenno=" + gridDetail.get(j).getTokenNo() + " and subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                        var1 = updateTokenTableDr.executeUpdate();
                        if (var1 <= 0) {
                            ut.rollback();
                            return "Updation Problem of TOKENTABLE DEBIT !!!";
                        }
                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC) || actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || actnature.equalsIgnoreCase(CbsConstant.OF_AC) || actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.SS_AC) || actnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            List chkLt = em.createNativeQuery("select acno from reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                                balance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update reconbalan set clearedbalance = " + balance + " - " + gridDetail.get(j).getAmount() + " where acno='" + gridDetail.get(j).getAcno() + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values ('" + gridDetail.get(j).getAcno() + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in Reconbalan !!!";
                                }
                                balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            List chkLt = em.createNativeQuery("select acno from ca_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from ca_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                                balance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update ca_reconbalan set clearedbalance = " + balance + " - " + gridDetail.get(j).getAmount() + " where acno='" + gridDetail.get(j).getAcno() + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in CA_Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into ca_reconbalan(acno,dt,balance,clearedbalance) values ('" + gridDetail.get(j).getAcno() + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in CA_Reconbalan !!!";
                                }
                                balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            List chkLt = em.createNativeQuery("select acno from td_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from td_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                                balance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update td_reconbalan set clearedbalance = " + balance + " - " + gridDetail.get(j).getAmount() + " where acno='" + gridDetail.get(j).getAcno() + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in CA_Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into td_reconbalan(acno,dt,balance,clearedbalance) values ('" + gridDetail.get(j).getAcno() + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in CA_Reconbalan !!!";
                                }
                                balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        }
                        balance = balance - Double.parseDouble(gridDetail.get(j).getAmount());
                        /*  ************** Insertion into Corresponding Recons ******************/
                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into recon ( acno , ty, dt, Valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', '" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            Query insertReconQ = em.createNativeQuery("insert into ddstransaction ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, InstNo,  "
                                    + " EnterBy, Auth, recno, payby, Authby, trsno, Trantime, TokenNo,  "
                                    + " SubTokenNo,org_brnid,dest_brnid,tran_id,term_id,trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO,TOKENPAIDBY) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby, r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TokenNo, r.SubTokenNo,org_brnid,dest_brnid,0,'',r.TRANDESC,'Y','','',r.instno,'" + tokenpaidby + "' from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "' ");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in DDS Transaction !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into of_recon ( acno,tdacno,ty,dt,valuedt, Dramt, CrAmt, Balance, TranType, details,  "
                                    + " EnterBy, Auth, recno,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " SubTokenNo,instno,payby,iy,Tokenpaidby,voucherno,intflag,closeflag,org_brnid,dest_brnid) select r.acno,r.tdacno, r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details,r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', r.SubTokenNo,r.instno,r.payby,r.iy,'" + tokenpaidby + "',  "
                                    + " r.voucherno,r.intflag,r.closeflag,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in OF_Recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || actnature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into loan_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,TokenpaidBy,  "
                                    + " SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,"
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y','" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Loan_recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            Query insertReconQ = em.createNativeQuery("insert into gl_recon (acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,adviceNo,adviceBrnCode) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + " , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,"
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y','" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid,'','' from recon_cash_d r "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Gl_recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into rdrecon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', '" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in RDRecon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into ca_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', '" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in CA_Recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into td_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details,  "
                                    + " EnterBy, Auth, recno,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " SubTokenNo,instno,payby,iy,tokenPaidBy,VoucherNo,IntFlag,CloseFlag,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', r.SubTokenNo,r.instno,r.payby,r.iy  "
                                    + " ,'" + tokenpaidby + "',r.VoucherNo,r.IntFlag,r.CloseFlag,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in TD_Recon !!!";
                            }
                        }
                        /**
                         * **************In case of Disbursement Updation For
                         * Loan Accounts ****
                         */
                        List tempDtLt = em.createNativeQuery("select date_format(DT,'%Y%m%d'),trandesc,details,authby,EnterBy  "
                                + " from recon_cash_d where recno=" + gridDetail.get(j).getRecNo() + " and tokenno=" + gridDetail.get(j).getTokenNo() + " "
                                + " and subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'").getResultList();
                        Vector tempDtLtVec = (Vector) tempDtLt.get(0);
                        String tempDt = tempDtLtVec.get(0).toString();
                        String tempTranDesc = tempDtLtVec.get(1).toString();
                        String tempRemarks = tempDtLtVec.get(2).toString();
                        String tempAuthBy = tempDtLtVec.get(3).toString();
                        String tempEnterBy = tempDtLtVec.get(4).toString();
                        if (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            String resultDisbursment = ftsMethods.loanDisbursementInstallment(gridDetail.get(j).getAcno(), Double.parseDouble(gridDetail.get(j).getAmount()), 1, tempAuthBy, tempDt, Float.parseFloat(gridDetail.get(j).getRecNo()), Integer.parseInt(tempTranDesc), tempRemarks);
                            if (resultDisbursment == null || resultDisbursment.equalsIgnoreCase("")) {
                                ut.rollback();
                                return "System error occured in Loan Disbursement Installment !!!";
                            } else if (!resultDisbursment.equalsIgnoreCase("TRUE")) {
                                ut.rollback();
                                return "System error occured in Loan Disbursement Installment !!!";
                            }
                        }
                        if (actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            List tyLt = em.createNativeQuery("SELECT ifnull(IY,1) from recon_cash_d r where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'").getResultList();
                            iy = Integer.parseInt(((Vector) tyLt.get(0)).get(0).toString());
                            if (iy == 11 && !tempTranDesc.equalsIgnoreCase("9")) {
                                List seqLt = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear=extract(year from '" + tempDt + "')").getResultList();
                                SeqNo = Float.parseFloat(((Vector) seqLt.get(0)).get(0).toString());
                                Query insertBillSundryQ = em.createNativeQuery("Insert into bill_sundry(Fyear,AcNo,amount,enterby,dt,status,auth,  "
                                        + " trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + tempDt + "'),'" + gridDetail.get(j).getAcno() + "',ifnull(" + gridDetail.get(j).getAmount() + ",0),'" + tempEnterBy + "','" + tempDt + "',"
                                        + " 'ISSUED','Y',0," + SeqNo + ",1," + gridDetail.get(j).getRecNo() + ",date_format(curdate(),'%Y%m%d'),'" + tempAuthBy + "')");
                                var5 = insertBillSundryQ.executeUpdate();
                                if (var5 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in Bill_Sundry !!!";
                                }
                            }
                            if (iy == 12 && !tempTranDesc.equalsIgnoreCase("9")) {
                                List seqLt = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_suspense where Fyear=extract(year from '" + tempDt + "')").getResultList();
                                SeqNo = Float.parseFloat(((Vector) seqLt.get(0)).get(0).toString());
                                Query insertBillSusQ = em.createNativeQuery("Insert into bill_suspense(Fyear,AcNo,amount,enterby,dt,status,auth,  "
                                        + " trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + tempDt + "'),'" + gridDetail.get(j).getAcno() + "',ifnull(" + gridDetail.get(j).getAmount() + ",0),'" + tempEnterBy + "','" + tempDt + "',"
                                        + " 'ISSUED','Y',0," + SeqNo + ",1," + gridDetail.get(j).getRecNo() + ",date_format(curdate(),'%Y%m%d'),'" + tempAuthBy + "')");
                                var5 = insertBillSusQ.executeUpdate();
                                if (var5 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in Bill_Suspense !!!";
                                }
                            }
                            if (iy == 13 && !tempTranDesc.equalsIgnoreCase("9")) {
                                List seqLt = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_clgadjustment where acno='" + gridDetail.get(j).getAcno() + "' and Fyear=extract(year from '" + tempDt + "')").getResultList();
                                SeqNo = Float.parseFloat(((Vector) seqLt.get(0)).get(0).toString());
                                Query insertBillAdjQ = em.createNativeQuery("Insert into bill_clgadjustment(Fyear,AcNo,amount,enterby,dt,status,auth,  "
                                        + " trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + tempDt + "'),'" + gridDetail.get(j).getAcno() + "',ifnull(" + gridDetail.get(j).getAmount() + ",0),'" + tempEnterBy + "','" + tempDt + "',"
                                        + " 'ISSUED','Y',0," + SeqNo + ",1," + gridDetail.get(j).getRecNo() + ",date_format(curdate(),'%Y%m%d'),'" + tempAuthBy + "')");
                                var5 = insertBillAdjQ.executeUpdate();
                                if (var5 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in Bill_ClgAdjustment !!!";
                                }
                            }
                        }
                        /* Code by Dhirendra Singh for TDS Deduction of cash withdrawal above 1 cr*/
                        if (!actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            List occuCodeList = em.createNativeQuery("Select ifnull(acctCategory,'') from accountmaster where AcNo='" + gridDetail.get(j).getAcno() + "'"
                                    + "union all Select ifnull(acctCategory,'') from td_accountmaster where AcNo='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (occuCodeList.isEmpty()) {
                                throw new ApplicationException("Data does not exist in accountmaster");
                            }
                            String occupationCode = ((Vector) occuCodeList.get(0)).get(0).toString();
                            if (!occupationCode.equals("50")) {
                                BigDecimal tdsToBeDeducted = interBranchFacade.getTdsToBeDeductedForCustomer(gridDetail.get(j).getAcno(), "20190901", tempDt, new BigDecimal(0), "Y");
                                if (tdsToBeDeducted.compareTo(new BigDecimal(0)) > 0) {
                                    String msg1 = ftsMethods.chkBal(gridDetail.get(j).getAcno(), 1, Integer.parseInt(tempTranDesc), actnature);
                                    if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                                        String msg = ftsMethods.checkBalance(gridDetail.get(j).getAcno(), tdsToBeDeducted.doubleValue(), tempAuthBy);
                                        if (!(msg.equalsIgnoreCase("TRUE"))) {
                                            throw new ApplicationException(msg + " for A/C No. " + gridDetail.get(j).getAcno());
                                        }
                                    }
                                    //To do Transfer Transaction for TDS Deduction
                                    interBranchFacade.tdsDeductionForCashWithdrawal(actnature, gridDetail.get(j).getAcno(), tdsToBeDeducted.doubleValue(), tempAuthBy, gridDetail.get(j).getOrgBrnId());
                                }
                            }
                        }
                    } else {
                        /**
                         * This block of code(else part) is for inter branch
                         * transactions*
                         */
                        /**
                         * Intersole account pick up*
                         */
                        String orgnIsoAcNo = "";
                        String destIsoAcNo = "";
                        List orgnIsoAcNoList = em.createNativeQuery("SELECT ACNO FROM abb_parameter_info WHERE PURPOSE = 'INTERSOLE ACCOUNT'").getResultList();
                        if (orgnIsoAcNoList.size() > 0) {
                            Vector consoleGlHeadVect = (Vector) orgnIsoAcNoList.get(0);
                            String curBrMsg = ftsMethods.getCurrentBrnCode(gridDetail.get(j).getAcno());
                            if (curBrMsg.trim().substring(0, 1).equalsIgnoreCase("B")) {
                                return curBrMsg;
                            }
                            destIsoAcNo = curBrMsg.concat(consoleGlHeadVect.get(0).toString());
                            orgnIsoAcNo = brCode.concat(consoleGlHeadVect.get(0).toString());
                        } else {
                            return "ERROR OCCURED: - Please enter INTERSOLE GL Head!";
                        }

                        /**
                         * End of intersole account process*
                         */
                        if (gridDetail.get(j).getSubTokenNo() == null || gridDetail.get(j).getSubTokenNo().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty sub token no !!!";
                        }
                        if (gridDetail.get(j).getTokenNo() == null || gridDetail.get(j).getTokenNo().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty token no !!!";
                        }
                        if (gridDetail.get(j).getRecNo() == null || gridDetail.get(j).getRecNo().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty rec no !!!";
                        }
                        if (gridDetail.get(j).getAcno() == null || gridDetail.get(j).getAcno().equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Please check for empty account number !!!";
                        }
                        actnature = ftsMethods.getAccountNature(gridDetail.get(j).getAcno());
                        Query updateReconCashDQ = em.createNativeQuery("update recon_cash_d set tokenpaidby='" + tokenpaidby + "' where recno=" + gridDetail.get(j).getRecNo() + " and tokenno=" + gridDetail.get(j).getTokenNo() + " and subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                        var = updateReconCashDQ.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Updation Problem of Current Recon !!!";
                        }
                        Query updateTokenTableDr = em.createNativeQuery("update tokentable_debit set tokenpaidby='" + tokenpaidby + "',TOKENPAID='Y' where recno=" + gridDetail.get(j).getRecNo() + " and tokenno=" + gridDetail.get(j).getTokenNo() + " and subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                        var1 = updateTokenTableDr.executeUpdate();
                        if (var1 <= 0) {
                            ut.rollback();
                            return "Updation Problem of TOKENTABLE DEBIT !!!";
                        }
                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC) || actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || actnature.equalsIgnoreCase(CbsConstant.OF_AC) || actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.SS_AC) || actnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            List chkLt = em.createNativeQuery("select acno from reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                                balance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update reconbalan set clearedbalance = " + balance + " - " + gridDetail.get(j).getAmount() + " where acno='" + gridDetail.get(j).getAcno() + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values ('" + gridDetail.get(j).getAcno() + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in Reconbalan !!!";
                                }
                                balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            List chkLt = em.createNativeQuery("select acno from ca_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from ca_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                                balance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update ca_reconbalan set clearedbalance = " + balance + " - " + gridDetail.get(j).getAmount() + " where acno='" + gridDetail.get(j).getAcno() + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in CA_Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into ca_reconbalan(acno,dt,balance,clearedbalance) values ('" + gridDetail.get(j).getAcno() + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in CA_Reconbalan !!!";
                                }
                                balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            List chkLt = em.createNativeQuery("select acno from td_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from td_reconbalan where acno='" + gridDetail.get(j).getAcno() + "'").getResultList();
                                balance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update td_reconbalan set clearedbalance = " + balance + " - " + gridDetail.get(j).getAmount() + " where acno='" + gridDetail.get(j).getAcno() + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in CA_Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into td_reconbalan(acno,dt,balance,clearedbalance) values ('" + gridDetail.get(j).getAcno() + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in CA_Reconbalan !!!";
                                }
                                balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        }
                        if (destIsoAcNo == null || destIsoAcNo.equalsIgnoreCase("") || destIsoAcNo.length() != 12) {
                            ut.rollback();
                            return "Destination intersole account number is not present !!!";
                        } else {
                            List chkLt = em.createNativeQuery("select acno from reconbalan where acno='" + destIsoAcNo + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from reconbalan where acno='" + destIsoAcNo + "'").getResultList();
                                double tempBalance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update reconbalan set clearedbalance = " + tempBalance + " + " + gridDetail.get(j).getAmount() + " where acno='" + destIsoAcNo + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values ('" + destIsoAcNo + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in Reconbalan !!!";
                                }
                                //balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        }
                        if (orgnIsoAcNo == null || orgnIsoAcNo.equalsIgnoreCase("") || orgnIsoAcNo.length() != 12) {
                            ut.rollback();
                            return "Destination intersole account number is not present !!!";
                        } else {
                            List chkLt = em.createNativeQuery("select acno from reconbalan where acno='" + orgnIsoAcNo + "'").getResultList();
                            if (!chkLt.isEmpty()) {
                                List balLt = em.createNativeQuery("select ifnull(clearedbalance,0) from reconbalan where acno='" + orgnIsoAcNo + "'").getResultList();
                                double tempBalance = Double.parseDouble(((Vector) balLt.get(0)).get(0).toString());
                                Query updateReconBal = em.createNativeQuery("Update reconbalan set clearedbalance = " + tempBalance + " - " + gridDetail.get(j).getAmount() + " where acno='" + orgnIsoAcNo + "'");
                                var2 = updateReconBal.executeUpdate();
                                if (var2 <= 0) {
                                    ut.rollback();
                                    return "Updation failed in Reconbalan !!!";
                                }
                            } else {
                                Query inserReconBalan = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values ('" + orgnIsoAcNo + "',CURRENT_TIMESTAMP,0,- " + gridDetail.get(j).getAmount() + ")");
                                var3 = inserReconBalan.executeUpdate();
                                if (var3 <= 0) {
                                    ut.rollback();
                                    return "Insertion failed in Reconbalan !!!";
                                }
                                //balance = -Double.parseDouble(gridDetail.get(j).getAmount());
                            }
                        }
                        balance = balance - Double.parseDouble(gridDetail.get(j).getAmount());
                        /*  ************** Insertion into Corresponding Recons ******************/
                        if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', '" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            Query insertReconQ = em.createNativeQuery("insert into ddstransaction ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, InstNo,  "
                                    + " EnterBy, Auth, recno, payby, Authby, trsno, Trantime, TokenNo,  "
                                    + " SubTokenNo,org_brnid,dest_brnid,tran_id,term_id,trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO,TOKENPAIDBY) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby, r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TokenNo, r.SubTokenNo,org_brnid,dest_brnid,'','',r.TRANDESC,'Y','','',r.instno,'" + tokenpaidby + "' from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "' ");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in DDS Transaction !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into of_recon ( acno,tdacno,ty,dt,valuedt, Dramt, CrAmt, Balance, TranType, details,  "
                                    + " EnterBy, Auth, recno,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " SubTokenNo,instno,payby,iy,Tokenpaidby,voucherno,intflag,closeflag,org_brnid,dest_brnid) select r.acno,r.tdacno, r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details,r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', r.SubTokenNo,r.instno,r.payby,9999,'" + tokenpaidby + "',  "
                                    + " r.voucherno,r.intflag,r.closeflag,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in OF_Recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || actnature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into loan_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,TokenpaidBy,  "
                                    + " SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,"
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y','" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Loan_recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            Query insertReconQ = em.createNativeQuery("insert into gl_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,adviceNo,adviceBrnCode) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,"
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y','" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid,'','' from recon_cash_d r "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Gl_recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into rdrecon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', '" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in RDRecon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into ca_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', '" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in CA_Recon !!!";
                            }
                        } else if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            Query insertReconQ = em.createNativeQuery("insert into td_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details,  "
                                    + " EnterBy, Auth, recno,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " SubTokenNo,instno,payby,iy,tokenPaidBy,VoucherNo,IntFlag,CloseFlag,org_brnid,dest_brnid) select r.acno , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType   "
                                    + " , r.details, r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno,   "
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y', r.SubTokenNo,r.instno,r.payby,9999  "
                                    + " ,'" + tokenpaidby + "',r.VoucherNo,r.IntFlag,r.CloseFlag,r.org_brnid,r.dest_brnid from recon_cash_d r  "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in TD_Recon !!!";
                            }
                        }
                        if (destIsoAcNo == null || destIsoAcNo.equalsIgnoreCase("") || destIsoAcNo.length() != 12) {
                            ut.rollback();
                            return "Destination intersole account number is not present !!!";
                        } else {
                            Query insertReconQ = em.createNativeQuery("insert into gl_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,adviceNo,adviceBrnCode) select '" + destIsoAcNo + "' , 0, r.dt, r.valuedt, r.CrAmt, r.Dramt, " + balance + ",r.TranType "
                                    + " , r.details, 9999, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,"
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y','" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid,'','' from recon_cash_d r "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Gl_recon !!!";
                            }
                        }
                        if (orgnIsoAcNo == null || orgnIsoAcNo.equalsIgnoreCase("") || orgnIsoAcNo.length() != 12) {
                            ut.rollback();
                            return "Origin intersole account number is not present !!!";
                        } else {
                            Query insertReconQ = em.createNativeQuery("insert into gl_recon ( acno , ty, dt, valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,  "
                                    + " EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenpaid,  "
                                    + " tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,adviceNo,adviceBrnCode) select '" + orgnIsoAcNo + "' , r.ty, r.dt, r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + " , r.details, 8888, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,"
                                    + " r.Trantime, r.TranDesc, r.TokenNo,'Y','" + tokenpaidby + "', r.SubTokenNo,r.org_brnid,r.dest_brnid,'','' from recon_cash_d r "
                                    + " where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'");
                            var4 = insertReconQ.executeUpdate();
                            if (var4 <= 0) {
                                ut.rollback();
                                return "Insertion failed in Gl_recon !!!";
                            }
                        }
                        /**
                         * **************In case of Disbursement Updation For
                         * Loan Accounts ****
                         */
                        List tempDtLt = em.createNativeQuery("select date_format(DT,'%Y%m%d'),trandesc,details,authby,EnterBy  "
                                + " from recon_cash_d where recno=" + gridDetail.get(j).getRecNo() + " and tokenno=" + gridDetail.get(j).getTokenNo() + " "
                                + " and subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'").getResultList();
                        Vector tempDtLtVec = (Vector) tempDtLt.get(0);
                        String tempDt = tempDtLtVec.get(0).toString();
                        String tempTranDesc = tempDtLtVec.get(1).toString();
                        String tempRemarks = tempDtLtVec.get(2).toString();
                        String tempAuthBy = tempDtLtVec.get(3).toString();
                        String tempEnterBy = tempDtLtVec.get(4).toString();
                        if (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            String resultDisbursment = ftsMethods.loanDisbursementInstallment(gridDetail.get(j).getAcno(), Double.parseDouble(gridDetail.get(j).getAmount()), 1, tempAuthBy, tempDt, Float.parseFloat(gridDetail.get(j).getRecNo()), Integer.parseInt(tempTranDesc), tempRemarks);
                            if (resultDisbursment == null || resultDisbursment.equalsIgnoreCase("")) {
                                ut.rollback();
                                return "System error occured in Loan Disbursement Installment !!!";
                            } else if (!resultDisbursment.equalsIgnoreCase("TRUE")) {
                                ut.rollback();
                                return "System error occured in Loan Disbursement Installment !!!";
                            }
                        }
                        if (actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            List tyLt = em.createNativeQuery("SELECT ifnull(IY,1) from recon_cash_d r where r.recno=" + gridDetail.get(j).getRecNo() + " and r.tokenno=" + gridDetail.get(j).getTokenNo() + " and r.subtokenno='" + gridDetail.get(j).getSubTokenNo() + "'").getResultList();
                            iy = Integer.parseInt(((Vector) tyLt.get(0)).get(0).toString());
                            if (iy == 11 && !tempTranDesc.equalsIgnoreCase("9")) {
                                List seqLt = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear=extract(year from '" + tempDt + "')").getResultList();
                                SeqNo = Float.parseFloat(((Vector) seqLt.get(0)).get(0).toString());
                                Query insertBillSundryQ = em.createNativeQuery("Insert into Bill_Sundry(Fyear,AcNo,amount,enterby,dt,status,auth,  "
                                        + " trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + tempDt + "'),'" + gridDetail.get(j).getAcno() + "',ifnull(" + gridDetail.get(j).getAmount() + ",0),'" + tempEnterBy + "','" + tempDt + "',"
                                        + " 'ISSUED','Y',0," + SeqNo + ",1," + gridDetail.get(j).getRecNo() + ",date_format(curdate(),'%Y%m%d'),'" + tempAuthBy + "')");
                                var5 = insertBillSundryQ.executeUpdate();
                                if (var5 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in Bill_Sundry !!!";
                                }
                            }
                            if (iy == 12 && !tempTranDesc.equalsIgnoreCase("9")) {
                                List seqLt = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_suspense where Fyear=extract(year from '" + tempDt + "')").getResultList();
                                SeqNo = Float.parseFloat(((Vector) seqLt.get(0)).get(0).toString());
                                Query insertBillSusQ = em.createNativeQuery("Insert into bill_suspense(Fyear,AcNo,amount,enterby,dt,status,auth,  "
                                        + " trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + tempDt + "'),'" + gridDetail.get(j).getAcno() + "',ifnull(" + gridDetail.get(j).getAmount() + ",0),'" + tempEnterBy + "','" + tempDt + "',"
                                        + " 'ISSUED','Y',0," + SeqNo + ",1," + gridDetail.get(j).getRecNo() + ",date_format(curdate(),'%Y%m%d'),'" + tempAuthBy + "')");
                                var5 = insertBillSusQ.executeUpdate();
                                if (var5 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in Bill_Suspense !!!";
                                }
                            }
                            if (iy == 13 && !tempTranDesc.equalsIgnoreCase("9")) {
                                List seqLt = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_clgadjustment where acno='" + gridDetail.get(j).getAcno() + "' and Fyear=extract(year from '" + tempDt + "')").getResultList();
                                SeqNo = Float.parseFloat(((Vector) seqLt.get(0)).get(0).toString());
                                Query insertBillAdjQ = em.createNativeQuery("Insert into Bill_ClgAdjustment(Fyear,AcNo,amount,enterby,dt,status,auth,  "
                                        + " trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + tempDt + "'),'" + gridDetail.get(j).getAcno() + "',ifnull(" + gridDetail.get(j).getAmount() + ",0),'" + tempEnterBy + "','" + tempDt + "',"
                                        + " 'ISSUED','Y',0," + SeqNo + ",1," + gridDetail.get(j).getRecNo() + ",date_format(curdate(),'%Y%m%d'),'" + tempAuthBy + "')");
                                var5 = insertBillAdjQ.executeUpdate();
                                if (var5 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in Bill_ClgAdjustment !!!";
                                }
                            }
                        }

                        /* Code by Dhirendra Singh for TDS Deduction of cash withdrawal above 1 cr*/
                        if (!actnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            List occuCodeList = em.createNativeQuery("Select ifnull(acctCategory,'') from accountmaster where AcNo='" + gridDetail.get(j).getAcno() + "'").getResultList();

                            if (occuCodeList.isEmpty()) {
                                throw new ApplicationException("Data does not exist in Account master");
                            }
                            String occupationCode = ((Vector) occuCodeList.get(0)).get(0).toString();
                            if (!occupationCode.equals("50")) {
                                BigDecimal tdsToBeDeducted = interBranchFacade.getTdsToBeDeductedForCustomer(gridDetail.get(j).getAcno(), "20190901", tempDt, new BigDecimal(0), "Y");
                                if (tdsToBeDeducted.compareTo(new BigDecimal(0)) > 0) {
                                    String msg1 = ftsMethods.chkBal(gridDetail.get(j).getAcno(), 1, Integer.parseInt(tempTranDesc), actnature);
                                    if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                                        String msg = ftsMethods.checkBalance(gridDetail.get(j).getAcno(), tdsToBeDeducted.doubleValue(), tempAuthBy);
                                        if (!(msg.equalsIgnoreCase("TRUE"))) {
                                            throw new ApplicationException(msg + " for A/C No. " + gridDetail.get(j).getAcno());
                                        }
                                    }
                                    //To do Transfer Transaction for TDS Deduction
                                    interBranchFacade.tdsDeductionForCashWithdrawal(actnature, gridDetail.get(j).getAcno(), tdsToBeDeducted.doubleValue(), tempAuthBy, gridDetail.get(j).getOrgBrnId());
                                }
                            }
                        }
                    }
                }

                /**
                 * Code for denomination*
                 */
                if (denominationRender == true) {
                    if (denominationTable.isEmpty()) {
                        ut.rollback();
                        return "Please enter denomination correctly !!!";
                    }
                    if (gridDetail.get(j).getTokenPaid().equalsIgnoreCase("Y")) {
                        for (DDSDenominationGrid olObj : denominationTable) {
                            double denVal = olObj.getDenoValue();
                            int denNoCnt = olObj.getDenoNo();
                            String oNFlg = olObj.getFlag();
                            String denoMsg = interBranchFacade.insertDenominationDetail(gridDetail.get(j).getAcno(), Float.parseFloat(gridDetail.get(j).getRecNo()), enterDt, new BigDecimal(denVal),
                                    denNoCnt, Integer.parseInt(olObj.getTy()), brCode, gridDetail.get(j).getEnterBy(), oNFlg);
                            if (!denoMsg.equalsIgnoreCase("true")) {
                                ut.rollback();
                                return "Insertion Problem in denomination_details !";
                            } else {
                                int cnVal = 0;
                                if (olObj.getTy().equalsIgnoreCase("0") || olObj.getTy().equalsIgnoreCase("4")) {
                                    cnVal = denNoCnt;
                                } else if (olObj.getTy().equalsIgnoreCase("1") || olObj.getTy().equalsIgnoreCase("3")) {
                                    cnVal = denNoCnt * -1;
                                }

                                String denUpdateMsg = interBranchFacade.updateOpeningDenomination(brCode, new BigDecimal(denVal), cnVal, enterDt, oNFlg);
                                if (!denUpdateMsg.equalsIgnoreCase("true")) {
                                    ut.rollback();
                                    return "Updation Problem in denomination_details !";
                                }
                            }
                        }
                    }
                }
            }

            ut.commit();
            try {
                //Sms Sending.
                for (CashTokenBookGridFile obj : gridDetail) {
                    if (obj.getTokenPaid().equalsIgnoreCase("Y")) {
                        smsFacade.sendTransactionalSms(SmsType.CASH_WITHDRAWAL, obj.getAcno(), 0, 1,
                                Double.parseDouble(obj.getAmount()), dmy.format(new Date()), "");

                        List list = em.createNativeQuery("select iy from tokentable_debit where "
                                + "recno=" + Double.parseDouble(obj.getRecNo()) + " and "
                                + "org_brnid ='" + obj.getOrgBrnId() + "'").getResultList();
                        Vector ele = (Vector) list.get(0);
                        int exceedIy = Integer.parseInt(ele.get(0).toString());
                        if (exceedIy == 99) {
                            txnAuth.sendCCODExceedSms(obj.getAcno(), Double.parseDouble(obj.getAmount()));
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error SMS Sending In Cash Withdrawal");
            }
            return "Cash paid succesfully.";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public boolean cashAndDenominitionModChk() throws ApplicationException {
        try {
            List paramInfoRepDenoLt = em.createNativeQuery("SELECT ReportName FROM parameterinfo_report WHERE CODE=1 AND ReportName='CASH DENOMINITION MODULE'").getResultList();
            if (paramInfoRepDenoLt.isEmpty() || paramInfoRepDenoLt.size() <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * **********************************CashAllotmentRegisterBean
     * *************************
     */
    public List activeCashierCombo(String brCode) throws ApplicationException {
        List actCashier = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select UserId,name from counterdetails where statusofcounter='Y' AND BRNCODE='" + brCode + "'");
            actCashier = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return actCashier;
    }

    public String cashRecievedByCashier(DenominitionTable denominitionObj, String userId, double openingBal, String enterBy, String enterDt, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (denominitionObj == null) {
                ut.rollback();
                return "Denomination grid is empty !!!";
            }
            int var = 0, var1 = 0;
            String tempCashierId = "";
            double tempAmount = 0.0d;
            int tempRs1000 = 0;
            int tempRs500 = 0;
            int tempRs100 = 0;
            int tempRs50 = 0;
            int tempRs20 = 0;
            int tempRs10 = 0;
            int tempRs5 = 0;
            int tempRs2 = 0;
            int tempRs1 = 0;
            int tempRs05 = 0;
            String headCshr = "";
            List chkLt1 = em.createNativeQuery("select cashierst,HEADCASHIER from securityinfo where userid='" + userId + "' and levelid not in (5,6) and brncode='" + brCode + "'").getResultList();
            if (chkLt1.isEmpty() || chkLt1.size() <= 0) {
                ut.rollback();
                return "User ID does not exists !!!";
            } else {
                String cashierst = ((Vector) chkLt1.get(0)).get(0).toString();
                headCshr = ((Vector) chkLt1.get(0)).get(1).toString();
                if (cashierst.equalsIgnoreCase("N")) {
                    ut.rollback();
                    return "This user ID is not a valid cashier !!!";
                }
            }
            if (headCshr.equalsIgnoreCase("Y")) {
                List chkHeadCashierDenominationLt = em.createNativeQuery("SELECT CASHIER,AMOUNT,Rs1000,Rs500,Rs100,Rs50,Rs20,Rs10,Rs5,Rs2,Rs1,Rs05 FROM denominition_opening "
                        + " WHERE HEADCASHIER='Y' AND DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'").getResultList();
                if (chkHeadCashierDenominationLt.isEmpty()) {
                    Query insertDenominationQuery = em.createNativeQuery("insert into denominition_opening(Cashier, Amount, "
                            + "Ty, Dt, Rs1000, Rs500, Rs100, Rs50, Rs20, Rs10, Rs5, Rs2, Rs1, Rs05, "
                            + "EnterBy, recno, Trantime,CLOSINGFLAG,BRNCODE,HEADCASHIER,AUTH) values ('" + denominitionObj.getCashierId() + "', "
                            + denominitionObj.getAmount() + ", " + denominitionObj.getTy() + ", '"
                            + denominitionObj.getDt() + "', " + denominitionObj.getRs1000() + ", "
                            + denominitionObj.getRs500() + ", " + denominitionObj.getRs100() + ", "
                            + denominitionObj.getRs50() + ", " + denominitionObj.getRs20() + ", "
                            + denominitionObj.getRs10() + ", " + denominitionObj.getRs5() + ", "
                            + denominitionObj.getRs2() + ", " + denominitionObj.getRs1() + ", "
                            + denominitionObj.getRs05() + ", '" + denominitionObj.getEnterBy() + "', "
                            + ftsMethods.getRecNo() + ", CURRENT_TIMESTAMP,'N','" + brCode + "','" + headCshr + "','Y')");
                    var = insertDenominationQuery.executeUpdate();
                    if (var <= 0) {
                        ut.rollback();
                        return "Insertion failed in cash demonination !!!";
                    } else {
                        ut.commit();
                        return "Data saved succesfully.";
                    }
                } else {
                    Vector ele = (Vector) chkHeadCashierDenominationLt.get(0);
                    tempCashierId = ele.get(0).toString();
                    tempAmount = Double.parseDouble(ele.get(1).toString());
                    tempRs1000 = Integer.parseInt(ele.get(2).toString());
                    tempRs500 = Integer.parseInt(ele.get(3).toString());
                    tempRs100 = Integer.parseInt(ele.get(4).toString());
                    tempRs50 = Integer.parseInt(ele.get(5).toString());
                    tempRs20 = Integer.parseInt(ele.get(6).toString());
                    tempRs10 = Integer.parseInt(ele.get(7).toString());
                    tempRs5 = Integer.parseInt(ele.get(8).toString());
                    tempRs2 = Integer.parseInt(ele.get(9).toString());
                    tempRs1 = Integer.parseInt(ele.get(10).toString());
                    tempRs05 = Integer.parseInt(ele.get(11).toString());

                    Query insertHistory = em.createNativeQuery("INSERT INTO denominition_opening_history SELECT * FROM denominition_opening WHERE HEADCASHIER='Y' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'");
                    var = insertHistory.executeUpdate();
                    if (var <= 0) {
                        ut.rollback();
                        return "Insertion failed in denomination history !!!";
                    }
                    Query updateHeadCashDen = em.createNativeQuery("UPDATE denominition_opening SET AMOUNT=" + (openingBal + tempAmount) + ", Rs1000=" + (denominitionObj.getRs1000() + tempRs1000) + ",Rs500=" + (denominitionObj.getRs500() + tempRs500) + ","
                            + " Rs100=" + (denominitionObj.getRs100() + tempRs100) + ",Rs50=" + (denominitionObj.getRs50() + tempRs50) + ",Rs20=" + (denominitionObj.getRs20() + tempRs20) + ",Rs10=" + (denominitionObj.getRs10() + tempRs10) + ","
                            + " Rs5=" + (denominitionObj.getRs5() + tempRs5) + ",Rs2=" + (denominitionObj.getRs2() + tempRs2) + ",Rs1=" + (denominitionObj.getRs1() + tempRs1) + ",Rs05=" + (denominitionObj.getRs05() + tempRs05) + " "
                            + " WHERE HEADCASHIER='Y' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y' AND DT='" + enterDt + "'");
                    var1 = updateHeadCashDen.executeUpdate();
                    if (var1 <= 0) {
                        ut.rollback();
                        return "Insertion failed in cash demonination !!!";
                    } else {
                        ut.commit();
                        return "Data saved succesfully.";
                    }
                }
            } else {
                List chkHeadCashierDenominationLt = em.createNativeQuery("SELECT CASHIER,AMOUNT,Rs1000,Rs500,Rs100,Rs50,Rs20,Rs10,Rs5,Rs2,Rs1,Rs05 FROM denominition_opening "
                        + " WHERE HEADCASHIER='Y' AND DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'").getResultList();
                if (chkHeadCashierDenominationLt.isEmpty()) {
                    ut.rollback();
                    return "Head Cashier does not have cash , head cashier can only allot the cash to other cashiers !!!";
                } else {
                    Vector ele = (Vector) chkHeadCashierDenominationLt.get(0);
                    tempCashierId = ele.get(0).toString();
                    tempAmount = Double.parseDouble(ele.get(1).toString());
                    tempRs1000 = Integer.parseInt(ele.get(2).toString());
                    tempRs500 = Integer.parseInt(ele.get(3).toString());
                    tempRs100 = Integer.parseInt(ele.get(4).toString());
                    tempRs50 = Integer.parseInt(ele.get(5).toString());
                    tempRs20 = Integer.parseInt(ele.get(6).toString());
                    tempRs10 = Integer.parseInt(ele.get(7).toString());
                    tempRs5 = Integer.parseInt(ele.get(8).toString());
                    tempRs2 = Integer.parseInt(ele.get(9).toString());
                    tempRs1 = Integer.parseInt(ele.get(10).toString());
                    tempRs05 = Integer.parseInt(ele.get(11).toString());
                }
                if (denominitionObj.getAmount() > tempAmount) {
                    ut.rollback();
                    return "Balance Exceeds !!!";
                }
                if (tempRs1000 < denominitionObj.getRs1000()) {
                    ut.rollback();
                    return "Rs. 1000 denomination exceeds from denomination stock !!!";
                }
                if (tempRs500 < denominitionObj.getRs500()) {
                    ut.rollback();
                    return "Rs. 500 denomination exceeds from denomination stock !!!";
                }
                if (tempRs100 < denominitionObj.getRs100()) {
                    ut.rollback();
                    return "Rs. 100 denomination exceeds from denomination stock !!!";
                }
                if (tempRs50 < denominitionObj.getRs50()) {
                    ut.rollback();
                    return "Rs. 50 denomination exceeds from denomination stock !!!";
                }
                if (tempRs20 < denominitionObj.getRs20()) {
                    ut.rollback();
                    return "Rs. 20 denomination exceeds from denomination stock !!!";
                }
                if (tempRs10 < denominitionObj.getRs10()) {
                    ut.rollback();
                    return "Rs. 10 denomination exceeds from denomination stock !!!";
                }
                if (tempRs5 < denominitionObj.getRs5()) {
                    ut.rollback();
                    return "Rs. 5 denomination exceeds from denomination stock !!!";
                }
                if (tempRs2 < denominitionObj.getRs2()) {
                    ut.rollback();
                    return "Rs. 2 denomination exceeds from denomination stock !!!";
                }
                if (tempRs1 < denominitionObj.getRs1()) {
                    ut.rollback();
                    return "Rs. 1 denomination exceeds from denomination stock !!!";
                }
                if (tempRs05 < denominitionObj.getRs05()) {
                    ut.rollback();
                    return "50 Paisa denomination exceeds from denomination stock !!!";
                }
                List jrCashierChklt = em.createNativeQuery("SELECT Cashier FROM denominition_opening WHERE CASHIER = '" + userId + "' and HEADCASHIER<>'Y' AND DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y' ").getResultList();
                if (jrCashierChklt.isEmpty()) {
                    Query updateHeadCashDen = em.createNativeQuery("UPDATE denominition_opening SET AMOUNT=" + (tempAmount - openingBal) + ", Rs1000=" + (tempRs1000 - denominitionObj.getRs1000()) + ",Rs500=" + (tempRs500 - denominitionObj.getRs500()) + ","
                            + " Rs100=" + (tempRs100 - denominitionObj.getRs100()) + ",Rs50=" + (tempRs50 - denominitionObj.getRs50()) + ",Rs20=" + (tempRs20 - denominitionObj.getRs20()) + ",Rs10=" + (tempRs10 - denominitionObj.getRs10()) + ","
                            + " Rs5=" + (tempRs5 - denominitionObj.getRs5()) + ",Rs2=" + (tempRs2 - denominitionObj.getRs2()) + ",Rs1=" + (tempRs1 - denominitionObj.getRs1()) + ",Rs05=" + (tempRs05 - denominitionObj.getRs05()) + " "
                            + " WHERE HEADCASHIER='Y' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'");
                    var1 = updateHeadCashDen.executeUpdate();
                    if (var1 <= 0) {
                        ut.rollback();
                        return "Head cashier demonination updation failed !!!";
                    }
                    Query insertDenominationQuery = em.createNativeQuery("insert into denominition_opening(Cashier, Amount, "
                            + "Ty, Dt, Rs1000, Rs500, Rs100, Rs50, Rs20, Rs10, Rs5, Rs2, Rs1, Rs05, "
                            + "EnterBy, recno, Trantime,CLOSINGFLAG,BRNCODE,HEADCASHIER,AUTH) values ('" + denominitionObj.getCashierId() + "', "
                            + denominitionObj.getAmount() + ", " + denominitionObj.getTy() + ", '"
                            + denominitionObj.getDt() + "', " + denominitionObj.getRs1000() + ", "
                            + denominitionObj.getRs500() + ", " + denominitionObj.getRs100() + ", "
                            + denominitionObj.getRs50() + ", " + denominitionObj.getRs20() + ", "
                            + denominitionObj.getRs10() + ", " + denominitionObj.getRs5() + ", "
                            + denominitionObj.getRs2() + ", " + denominitionObj.getRs1() + ", "
                            + denominitionObj.getRs05() + ", '" + denominitionObj.getEnterBy() + "', "
                            + ftsMethods.getRecNo() + ",CURRENT_TIMESTAMP,'N','" + brCode + "','" + headCshr + "','Y')");
                    var = insertDenominationQuery.executeUpdate();
                    if (var <= 0) {
                        ut.rollback();
                        return "Insertion failed in cash demonination !!!";
                    } else {
                        ut.commit();
                        return "Data saved succesfully.";
                    }
                } else {
                    String temp1CashierId = "";
                    double temp1Amount = 0.0d;
                    int temp1Rs1000 = 0;
                    int temp1Rs500 = 0;
                    int temp1Rs100 = 0;
                    int temp1Rs50 = 0;
                    int temp1Rs20 = 0;
                    int temp1Rs10 = 0;
                    int temp1Rs5 = 0;
                    int temp1Rs2 = 0;
                    int temp1Rs1 = 0;
                    int temp1Rs05 = 0;
                    List chkCashierDenominationLt = em.createNativeQuery("SELECT CASHIER,AMOUNT,Rs1000,Rs500,Rs100,Rs50,Rs20,Rs10,Rs5,Rs2,Rs1,Rs05 FROM denominition_opening "
                            + " WHERE CASHIER = '" + userId + "' and HEADCASHIER<>'Y' AND DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'").getResultList();
                    if (chkCashierDenominationLt.isEmpty()) {
                        ut.rollback();
                        return "Head Cashier does not have cash , head cashier can only allot the cash to other cashiers !!!";
                    } else {
                        Vector ele = (Vector) chkCashierDenominationLt.get(0);
                        temp1CashierId = ele.get(0).toString();
                        temp1Amount = Double.parseDouble(ele.get(1).toString());
                        temp1Rs1000 = Integer.parseInt(ele.get(2).toString());
                        temp1Rs500 = Integer.parseInt(ele.get(3).toString());
                        temp1Rs100 = Integer.parseInt(ele.get(4).toString());
                        temp1Rs50 = Integer.parseInt(ele.get(5).toString());
                        temp1Rs20 = Integer.parseInt(ele.get(6).toString());
                        temp1Rs10 = Integer.parseInt(ele.get(7).toString());
                        temp1Rs5 = Integer.parseInt(ele.get(8).toString());
                        temp1Rs2 = Integer.parseInt(ele.get(9).toString());
                        temp1Rs1 = Integer.parseInt(ele.get(10).toString());
                        temp1Rs05 = Integer.parseInt(ele.get(11).toString());
                    }
                    Query insertHistory = em.createNativeQuery("INSERT INTO denominition_opening_history SELECT * FROM denominition_opening WHERE CASHIER = '" + userId + "' and HEADCASHIER<>'Y' AND DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'");
                    var = insertHistory.executeUpdate();
                    if (var <= 0) {
                        ut.rollback();
                        return "Insertion failed in denomination history !!!";
                    }
                    Query updateHeadCashDen = em.createNativeQuery("UPDATE denominition_opening SET AMOUNT=" + (tempAmount - openingBal) + ", Rs1000=" + (tempRs1000 - denominitionObj.getRs1000()) + ",Rs500=" + (tempRs500 - denominitionObj.getRs500()) + ","
                            + " Rs100=" + (tempRs100 - denominitionObj.getRs100()) + ",Rs50=" + (tempRs50 - denominitionObj.getRs50()) + ",Rs20=" + (tempRs20 - denominitionObj.getRs20()) + ",Rs10=" + (tempRs10 - denominitionObj.getRs10()) + ","
                            + " Rs5=" + (tempRs5 - denominitionObj.getRs5()) + ",Rs2=" + (tempRs2 - denominitionObj.getRs2()) + ",Rs1=" + (tempRs1 - denominitionObj.getRs1()) + ",Rs05=" + (tempRs05 - denominitionObj.getRs05()) + " "
                            + " WHERE HEADCASHIER='Y' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'");
                    var1 = updateHeadCashDen.executeUpdate();
                    if (var1 <= 0) {
                        ut.rollback();
                        return "Head cashier demonination updation failed !!!";
                    }
                    Query updateCashDen = em.createNativeQuery("UPDATE denominition_opening SET AMOUNT=" + (openingBal + temp1Amount) + ", Rs1000=" + (denominitionObj.getRs1000() + temp1Rs1000) + ",Rs500=" + (denominitionObj.getRs500() + temp1Rs500) + ","
                            + " Rs100=" + (denominitionObj.getRs100() + temp1Rs100) + ",Rs50=" + (denominitionObj.getRs50() + temp1Rs50) + ",Rs20=" + (denominitionObj.getRs20() + temp1Rs20) + ",Rs10=" + (denominitionObj.getRs10() + temp1Rs10) + ","
                            + " Rs5=" + (denominitionObj.getRs5() + temp1Rs5) + ",Rs2=" + (denominitionObj.getRs2() + temp1Rs2) + ",Rs1=" + (denominitionObj.getRs1() + temp1Rs1) + ",Rs05=" + (denominitionObj.getRs05() + temp1Rs05) + " "
                            + " WHERE CASHIER = '" + userId + "' and HEADCASHIER<>'Y' AND DT='" + enterDt + "' AND BRNCODE='" + brCode + "' AND CLOSINGFLAG<>'Y'");
                    var1 = updateCashDen.executeUpdate();
                    if (var1 <= 0) {
                        ut.rollback();
                        return "Insertion failed in cash demonination !!!";
                    } else {
                        ut.commit();
                        return "Data saved succesfully.";
                    }
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }
}
