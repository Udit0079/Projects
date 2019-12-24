/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.other.CashHandlingDTO;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.sms.service.PropertyContainer;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "TxnAuthorizationManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TxnAuthorizationManagementFacade implements TxnAuthorizationManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    CertIssueFacadeRemote certIssueFacadeRemote;
    @EJB
    TdReceiptManagementFacadeRemote tdRcptMgmtRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private LoanInterestCalculationFacadeRemote loanFacade;
    @EJB
    MessageDetailBeanRemote messageDetailBeanRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private BankProcessManagementFacadeRemote bankProcessManagement;
//    @EJB
//    private CommonReportMethodsRemote commonReport;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat nf = new DecimalFormat("#.##");
    Date curDt = new Date();

    /*                         Start of Cash Credit Authorization         */
    public List getCashCrDataToAuthorize(String todayDate, String orgBranchCode) throws ApplicationException {
        List dataToAuthorize = null;
        try {
            List list = em.createNativeQuery("select ifnull(cashmod,'Y') from parameterinfo where brncode=cast('" + orgBranchCode + "' as unsigned)").getResultList();
            String cashMod = "N";
            if (!list.isEmpty()) {
                Vector v7 = (Vector) list.get(0);
                cashMod = v7.get(0).toString();
            }
            if (cashMod.equalsIgnoreCase("Y")) {
                dataToAuthorize = em.createNativeQuery("select t.tokenno,t.acno,t.custname,t.cramt,ifnull(ifnull(ifnull(r1.balance,r2.balance),r3.balance),0)as balance, "
                        + "ifnull(t.instno,'') as instno,t.enterby,t.auth,t.recno,t.details,t.org_brnid,t.dest_brnid,date_format(t.valuedt,'%d/%m/%Y'),t.instdt,t.trandesc "
                        + "from tokentable_credit t left join reconbalan r1 on t.acno=r1.acno left join ca_reconbalan r2 on t.acno=r2.acno left join td_reconbalan r3 on t.acno=r3.acno "
                        + "where DATE_FORMAT(t.dt,'%Y%m%d')='" + todayDate + "' and t.org_brnid='" + orgBranchCode + "' and t.tokenpaid = 'Y' "
                        + "and t.authby is null and (t.auth is null or t.auth='N') order by tokenno").getResultList();
            } else {
                dataToAuthorize = em.createNativeQuery("select t.tokenno,t.acno,t.custname,t.cramt,ifnull(ifnull(ifnull(r1.balance,r2.balance),r3.balance),0)as balance, "
                        + "ifnull(t.instno,'') as instno,t.enterby,t.auth,t.recno,t.details,t.org_brnid,t.dest_brnid,date_format(t.valuedt,'%d/%m/%Y'),t.instdt,t.trandesc "
                        + "from tokentable_credit t left join reconbalan r1 on t.acno=r1.acno left join ca_reconbalan r2 on t.acno=r2.acno left join td_reconbalan r3 on t.acno=r3.acno "
                        + "where DATE_FORMAT(t.dt,'%Y%m%d')='" + todayDate + "' and t.org_brnid='" + orgBranchCode + "' and (t.tokenpaidby is not null or t.tokenpaidby <> '') "
                        + "and t.authby is null and (t.auth is null or t.auth='N') order by tokenno").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataToAuthorize;
    }

    public String cbsAuthCashCr(float recNo, String authBy, int msgNo, int msgBillStart, String status, int msgBillPo,
            int msgBillEnd, String date, float seqNo, String instNo, String billType, String acno, String custName,
            String payableAt, double amount, String dt1, String originDt, int timeLimit, double comm, int tranType, int ty,
            String inFavourOf, String enterBy, String lastUpdateBy, String orgnBrCode, String destBrCode, String valueDate)
            throws ApplicationException {

        Float trsNo;
        String msgAuth = null, msgMakeEntry = null, msg = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                    == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
            trsNo = fTSPosting43CBSBean.getTrsNo();
            if (!String.valueOf(recNo).equals("") && !orgnBrCode.equals("")) {
                Query q1 = em.createNativeQuery("insert into recon_cash_d(acno ,custname, dt, DrAmt, Cramt, Ty, Trantype, recno, trsno, instno, payby, iy, opermode, auth, EnterBy, authby, TokenPaidBy, TranDesc, TokenNo, SubTokenNo, Trantime, Details, voucherno, intflag, closeflag, org_brnid, dest_brnid,valueDt,instDt) "
                        + "select Acno, CustName, Dt, DrAmt, Cramt, Ty, Trantype, recno, " + trsNo + ", instno, payby, iy, opermode, 'Y', EnterBy, '" + authBy + "', TokenPaidBy, TranDesc, TokenNo, SubTokenNo, Trantime, Details, voucherno, intflag, closeflag, org_brnid, dest_brnid,valueDt,instDt FROM tokentable_credit "
                        + "where recno='" + recNo + "' and org_brnid='" + destBrCode + "'");
                q1.executeUpdate();
                if (msgNo > 0) {
                    msgMakeEntry = fTSPosting43CBSBean.cbsAuthCashCrMakeEntry(msgNo, msgBillStart, status, msgBillPo, msgBillEnd, authBy, date, seqNo, instNo, billType, acno, custName, payableAt, amount, dt1, originDt, timeLimit, comm, tranType, ty, inFavourOf, enterBy, lastUpdateBy, recNo, orgnBrCode);
                    if (!msgMakeEntry.substring(0, 4).equals("TRUE")) {
                        ut.rollback();
                        msg = "Problem in authorization";
                        return msg;
                    }
                }

                msgAuth = authCashCredit(trsNo, orgnBrCode);
                if (msgAuth.equals("Data does not exist")) {
                    msg = msgAuth;
                } else {
                    if (msgAuth.substring(0, 3).equals("YES")) {
                        if (msgAuth.toLowerCase().contains("cash-handling")) {
                            if (msgAuth.substring(16).equalsIgnoreCase("pending")) {
                                msg = "Authorization is over: Cash handling charge has been entered into pending charges";
                            } else {
                                msg = "Authorization is over: Batch no of cash handling charge is:" + msgAuth.substring(16);
                            }
                            fTSPosting43CBSBean.lastTxnDateUpdation(acno);
                            ut.commit();
                            try {
                                //Sms Sending.    
                                if (templateType.equalsIgnoreCase("indr")) {
                                    List detailsQuery = em.createNativeQuery("select Details from tokentable_credit where recno='" + recNo + "' and org_brnid='" + destBrCode + "'").getResultList();
                                    Vector det = (Vector) detailsQuery.get(0);
                                    String details = det.get(0).toString();
                                    String details1 = "";
                                    if (details.length() <= 30) {
                                        details1 = details;
                                    } else if (details.length() > 30) {
                                        details1 = details.substring(1, 30);
                                    }
                                    smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT_INDR, acno, 0, 0, amount, date, details1);
                                } else {
                                    smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT, acno, 0, 0, amount, date, "");
                                }
                            } catch (Exception ex) {
                                System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::" + amount);
                            }
                        } else {
                            if (Float.parseFloat(msgAuth.substring(7)) != 0.0) {
                                msg = "Authorization is over. " + " seqNo :- " + msgAuth.substring(7) + " Fyear :- " + msgAuth.substring(3, 7);
                                ut.commit();
                            } else {
                                //Deaf updation
                                fTSPosting43CBSBean.lastTxnDateUpdation(acno);
                                //Deaf updation end here
                                msg = "Authorization is over ";
                                ut.commit();
                                try {
                                    //Sms Sending.
                                    if (templateType.equalsIgnoreCase("indr")) {
                                        List detailsQuery = em.createNativeQuery("select Details from tokentable_credit where recno='" + recNo + "' and org_brnid='" + destBrCode + "'").getResultList();
                                        Vector det = (Vector) detailsQuery.get(0);
                                        String details = det.get(0).toString();
                                        String details1 = "";
                                        if (details.length() <= 30) {
                                            details1 = details;
                                        } else if (details.length() > 30) {
                                            details1 = details.substring(1, 30);
                                        }
                                        smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT_INDR, acno, 0, 0, amount, date, details1);
                                    } else {
                                        smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT, acno, 0, 0, amount, date, "");
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::" + amount);
                                }
                            }
                        }
                    } else {
                        msg = msgAuth;
                    }
                }
            } else {
                msg = "Problem in authorization";
                ut.rollback();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String authCashCredit(Float trsNo, String orgnBrCode) throws ApplicationException {
        String msg = null, acno = null, authBy = null, dt = null, remarks = null, enterBy = null, details = null, orgBrnId = null,
                destBrnId = null, accStatus = null, successMsg = null, valueDate = null;
        double crAmt = 0.0d, balance = 0.0d;
        float recno = 0.0f;
        int tranDesc = 0, iy = 0;
        float seqNo = 0;
        int fyear = 0;
        String cashHandlingMsg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            List data = em.createNativeQuery("select r.acno,r.cramt,r.recno,r.authby,r.dt,r.trandesc,r.details,r.iy,r.Enterby,r.org_brnid, r.dest_brnid,r.valueDt "
                    + "from recon_cash_d r where auth='Y' and ty=0 and trsno=" + trsNo + "").getResultList();
            if (data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    Vector element = (Vector) data.get(i);
                    acno = element.get(0).toString();
                    crAmt = Double.parseDouble(element.get(1).toString());
                    recno = Float.parseFloat(element.get(2).toString());
                    authBy = element.get(3).toString();
                    dt = element.get(4).toString();
                    tranDesc = Integer.parseInt(element.get(5).toString());
                    details = element.get(6).toString();
                    iy = Integer.parseInt(element.get(7).toString());
                    enterBy = element.get(8).toString();
                    orgBrnId = element.get(9).toString();
                    destBrnId = element.get(10).toString();
                    valueDate = element.get(11).toString();

                    String nature = fTSPosting43CBSBean.getAccountNature(acno);
                    if (acno.equals("") || nature.equals("")) {
                        ut.rollback();
                        return msg = "Please check for Recno,TokenNo,SubTokenNO";
                    }

                    /*Block Code Start For Fidility Functionality*/
                    String chkMsg = fdFidilityChk(acno);

                    if (((nature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (nature.equalsIgnoreCase(CbsConstant.MS_AC))) && chkMsg.equalsIgnoreCase("true")) {
                        List dataFd = em.createNativeQuery("select r.Ty,r.Trantype,r.Trantime,r.payby,r.instno,ifnull(r.InstDt,''),r.TokenNo,r.TokenPaidBy,ifnull(r.SubTokenNo,''),ifnull(r.tdacno,''),ifnull(r.voucherno,'0'),ifnull(r.intflag,''),ifnull(r.closeflag,''),ifnull(r.Tran_id,'0'),"
                                + " ifnull(r.Term_id,'0'),date_format(r.dt,'%Y%m%d') from recon_cash_d r where auth='Y' and ty=0 and trsno=" + trsNo + " and r.recno = '" + recno + "' "
                                + " and r.org_brnid='" + orgnBrCode + "'").getResultList();
                        if (!dataFd.isEmpty()) {
                            Vector dF = (Vector) dataFd.get(0);
                            Double pBy = Double.parseDouble(dF.get(3).toString());
                            String msg1 = insertFidilityTran(nature, acno, Integer.parseInt(dF.get(0).toString()), crAmt, dF.get(15).toString(), valueDate, Integer.parseInt(dF.get(1).toString()), details, enterBy, trsNo,
                                    dF.get(2).toString(), recno, "Y", authBy, tranDesc, pBy.intValue(), dF.get(4).toString(), dF.get(5).toString(), Float.parseFloat(dF.get(6).toString()), dF.get(7).toString(), dF.get(8).toString(), iy,
                                    dF.get(9).toString(), Float.parseFloat(dF.get(10).toString()), dF.get(11).toString(), dF.get(12).toString(), orgBrnId, destBrnId, Integer.parseInt(dF.get(13).toString()), dF.get(14).toString(), "", "");
                            if (!msg1.equalsIgnoreCase("true")) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                            List listfyear = em.createNativeQuery("select f_year from yearend where yearendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
                            Vector elementFyear = (Vector) listfyear.get(0);
                            fyear = Integer.parseInt(elementFyear.get(0).toString());
                        }
                    } /*Block Code End For Fidility Functionality*/ else {

                        int intPostOnDeposit = 0;
                        intPostOnDeposit = Integer.parseInt(fTSPosting43CBSBean.getCodeByReportName("INT_POST_DEPOSIT"));

                        if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && tranDesc == 1) {
                            List checkList = em.createNativeQuery("select *from rd_installment where acno='" + acno + "' and STATUS = 'UNPAID'").getResultList();
                            if (checkList.isEmpty()) {
                                ut.rollback();
                                msg = "All installments for this A/C has been paid.You can not paid further installments.";
                                return msg;
                            }
                            List checkListAmt = em.createNativeQuery("select sum(INSTALLAMT) from rd_installment where acno='" + acno + "' and STATUS = 'UNPAID'").getResultList();
                            if (!checkListAmt.isEmpty()) {
                                Vector vecAmt = (Vector) checkListAmt.get(0);
                                if (crAmt > Double.parseDouble(vecAmt.get(0).toString())) {
                                    ut.rollback();
                                    msg = "You can not pay more then total installment amount for this A/C.";
                                    return msg;
                                }

                            }
                        }

                        if (nature.equalsIgnoreCase(CbsConstant.SAVING_AC) || nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)
                                || nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || nature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || nature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                                || nature.equalsIgnoreCase(CbsConstant.SS_AC) || nature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                            List list1 = em.createNativeQuery("select acno from reconbalan where acno='" + acno + "'").getResultList();
                            if (list1.size() > 0) {
                                List list2 = em.createNativeQuery("select clearedbalance from reconbalan where acno='" + acno + "'").getResultList();
                                Vector clearbal = (Vector) list2.get(0);
                                balance = Double.parseDouble(clearbal.get(0).toString());
                                Query q1 = em.createNativeQuery("Update reconbalan set clearedbalance = ifnull(clearedbalance,0) + ifnull(" + crAmt + ",0),balance =ifnull(balance,0) +ifnull(" + crAmt + ",0) where acno='" + acno + "'");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Problem in updation";
                                }
                            } else {
                                Query q1 = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + crAmt + ",0),ifnull(" + crAmt + ",0))");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Problem in insertion";
                                } else {
                                    balance = crAmt;
                                }
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            List list1 = em.createNativeQuery("select acno from ca_reconbalan where acno='" + acno + "'").getResultList();
                            if (list1.size() > 0) {
                                List list2 = em.createNativeQuery("select clearedbalance from ca_reconbalan where acno='" + acno + "'").getResultList();
                                Vector clearbal = (Vector) list2.get(0);
                                balance = Double.parseDouble(clearbal.get(0).toString());
                                Query q1 = em.createNativeQuery("Update ca_reconbalan set clearedbalance = ifnull(clearedbalance,0) + ifnull(" + crAmt + ",0),balance =ifnull(balance,0) +ifnull(" + crAmt + ",0) where acno='" + acno + "'");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Problem in updation";
                                }
                            } else {
                                Query q1 = em.createNativeQuery("insert into ca_reconbalan(acno,dt,balance,clearedbalance) values('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + crAmt + ",0),ifnull(" + crAmt + ",0))");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Problem in insertion";
                                } else {
                                    balance = crAmt;
                                }
                            }
                        } //else if (nature.equalsIgnoreCase("FD") || nature.equalsIgnoreCase("MS") || nature.equalsIgnoreCase("OF")) {
                        else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)
                                || nature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                            List list1 = em.createNativeQuery("select acno from td_reconbalan where acno='" + acno + "'").getResultList();
                            if (list1.size() > 0) {
                                List list2 = em.createNativeQuery("select clearedbalance from td_reconbalan where acno='" + acno + "'").getResultList();
                                Vector clearbal = (Vector) list2.get(0);
                                balance = Double.parseDouble(clearbal.get(0).toString());
                                Query q1 = em.createNativeQuery("Update td_reconbalan set clearedbalance = ifnull(clearedbalance,0) + ifnull(" + crAmt + ",0),balance =ifnull(balance,0) +ifnull(" + crAmt + ",0) where acno='" + acno + "'");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Problem in updation";
                                }
                            } else {
                                Query q1 = em.createNativeQuery("insert into td_reconbalan(acno,dt,balance,clearedbalance) values('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + crAmt + ",0),ifnull(" + crAmt + ",0))");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Problem in insertion";
                                } else {
                                    balance = crAmt;
                                }
                            }
                        }
                        balance = balance + crAmt;

                        if (nature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            Query q1 = em.createNativeQuery("insert into recon(acno,ty,dt,ValueDt, Dramt, CrAmt, Balance, TranType, details, iy, instno,instDt,"
                                    + "EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                    + "tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valueDt, r.Dramt, r.CrAmt, " + balance + ",r.TranType"
                                    + ", r.details, r.iy, r.instno,r.instDt, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno, "
                                    + "r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            Query q1 = em.createNativeQuery("insert into ddstransaction(acno,ty,dt,ValueDt, Dramt, CrAmt, Balance, TranType, details, iy, ReceiptNo,"
                                    + "InstDt,EnterBy, Auth, recno, payby, Authby, trsno, Trantime, TokenNo,"
                                    + "tokenPaidBy, SubTokenNo,InstNo,org_brnid,dest_brnid,tran_id,term_id,tokenpaid,favorof,CheckBy,trandesc) select r.acno , r.ty, r.dt,r.valueDt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + ", r.details, r.iy, r.instno,r.instdt,r.EnterBy, r.Auth, r.recno, r.payby, r.Authby, r.trsno, "
                                    + "r.Trantime, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.INSTNO,r.ORG_BRNID,r.DEST_BRNID,r.TRAN_ID,r.TERM_ID,'','','',0 from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                            Query q1 = em.createNativeQuery("insert into of_recon ( acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details,"
                                    + "EnterBy, Auth, recno,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                    + "SubTokenNo,tokenPaidBy,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + ", r.details,r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno, "
                                    + "r.Trantime, r.TranDesc, r.TokenNo, r.SubTokenNo,r.tokenPaidBy,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || nature.equalsIgnoreCase(CbsConstant.SS_AC)) {

                            if (intPostOnDeposit == 0) {
                                String status = interBranchFacade.fnGetLoanStatus(acno, dt);
                                //  float recNo1 = dRecno(orgnBrCode);
                                Query q1 = em.createNativeQuery("insert into loan_recon ( acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,instdt,"
                                        + "EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                        + "SubTokenNo,tokenPaidBy,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                        + ", r.details, r.iy, r.instno,r.instdt, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno, "
                                        + "r.Trantime, r.TranDesc, r.TokenNo, r.SubTokenNo,r.tokenPaidBy,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                        + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Insertion problem in recon";
                                }
                                if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                    successMsg = fTSPosting43CBSBean.npaRecoveryUpdation(trsNo, nature, acno, dt, crAmt, orgnBrCode, destBrnId, enterBy);
                                    if (!successMsg.equalsIgnoreCase("True")) {
                                        ut.rollback();
                                        throw new ApplicationException(msg);
                                    }
                                }
                            } else {
                                LoanIntCalcList it = loanFacade.txnLoanIntCalc(dt, acno, orgBrnId);
                                double intAmt = it.getTotalInt() * -1;
                                double roi = it.getRoi();

                                String glInt = loanFacade.getGlHeads(acno.substring(2, 4));
                                glInt = orgBrnId + glInt + "01";

                                Query q1 = em.createNativeQuery("insert into loan_recon ( acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,instdt,"
                                        + "EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                        + "SubTokenNo,tokenPaidBy,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                        + ", r.details, r.iy, r.instno,r.instdt, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno, "
                                        + "r.Trantime, r.TranDesc, r.TokenNo, r.SubTokenNo,r.tokenPaidBy,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                        + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return msg = "Insertion problem in recon";
                                }

                                if (intAmt != 0) {
                                    float trsInt = fTSPosting43CBSBean.getTrsNo();
                                    float recnoInt = fTSPosting43CBSBean.getRecNo();
                                    Query insertQuery = em.createNativeQuery("INSERT loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " values ('" + acno + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + dt + "',"
                                            + intAmt + ",0,8,3,3,'INT.UP TO " + dt + " @" + roi + "%" + "','" + enterBy + "','Y','" + authBy + "',"
                                            + trsInt + "," + recnoInt + ",'" + orgBrnId + "','" + orgBrnId + "')");

                                    Integer insertRecon = insertQuery.executeUpdate();
                                    if (insertRecon == 0) {
                                        ut.rollback();
                                        return msg = "Value not inserted in Loan_Recon";
                                    }

                                    recnoInt = fTSPosting43CBSBean.getRecNo();
                                    Query insertGlReconQuery = em.createNativeQuery("INSERT gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glInt + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + dt + "'," + intAmt + ",0,"
                                            + "8,3,3,'Int Up To " + dt + " of " + acno + "','" + enterBy + "','Y','" + authBy
                                            + "'," + trsInt + "," + recnoInt + ",'" + orgBrnId + "','" + orgBrnId + "')");
                                    Integer insertGlRecon = insertGlReconQuery.executeUpdate();
                                    if (insertGlRecon == 0) {
                                        throw new ApplicationException("Value doesn't inserted in GL_RECON");
                                    }
                                }

                                String nextCalcDt = CbsUtil.dateAdd(ymd.format(ymmd.parse(dt)), 1);
                                Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '"
                                        + dt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acno + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    throw new ApplicationException("Interest not Posted successfully");
                                }
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                            Query q1 = em.createNativeQuery("insert into gl_recon (acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno, instdt,"
                                    + "EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                    + "tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,adviceNo,adviceBrnCode) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + ", r.details, r.iy, r.instno,r.instdt, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno, "
                                    + "r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid,'','' from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            Query q1 = em.createNativeQuery("insert into rdrecon ( acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,instdt,"
                                    + "EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo, "
                                    + "tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + ", r.details, r.iy, r.instno,r.instdt, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno,  "
                                    + "r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            Query q1 = em.createNativeQuery("insert into ca_recon ( acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details, iy, instno,instdt,"
                                    + "EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                    + "tokenPaidBy, SubTokenNo,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + ", r.details, r.iy, r.instno,r.instdt, r.EnterBy, r.Auth, r.recno, r.payby,r.Authby,r.trsno, "
                                    + "r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                            String status = interBranchFacade.fnGetLoanStatus(acno, dt);
                            if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                successMsg = fTSPosting43CBSBean.npaRecoveryUpdation(trsNo, nature, acno, dt, crAmt, orgnBrCode, destBrnId, enterBy);
                                if (!successMsg.equalsIgnoreCase("True")) {
                                    ut.rollback();
                                    throw new ApplicationException(msg);
                                }
                            }
                        } else if (nature.equalsIgnoreCase(CbsConstant.FIXED_AC) || nature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            Query q1 = em.createNativeQuery("insert into td_recon ( acno , ty, dt,valuedt, Dramt, CrAmt, Balance, TranType, details,"
                                    + "EnterBy, Auth, recno,Authby, trsno, Trantime, TranDesc, TokenNo,"
                                    + "SubTokenNo,tokenPaidBy,org_brnid,dest_brnid) select r.acno , r.ty, r.dt,r.valuedt, r.Dramt, r.CrAmt, " + balance + ",r.TranType "
                                    + ", r.details, r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno, "
                                    + "r.Trantime, r.TranDesc, r.TokenNo, r.SubTokenNo,r.tokenPaidBy,r.org_brnid,r.dest_brnid from recon_cash_d r "
                                    + "where r.recno=" + recno + " and r.org_brnid='" + orgnBrCode + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }
                        }

                        if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || nature.equalsIgnoreCase(CbsConstant.SS_AC) || nature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            if (intPostOnDeposit == 0) {
                                String msgLoan = fTSPosting43CBSBean.loanDisbursementInstallment(acno, crAmt, 0, authBy, dt, recno, tranDesc, remarks);
                                if (!msgLoan.equalsIgnoreCase("TRUE")) {
                                    ut.rollback();
                                    return msg = msgLoan;
                                }
                            }
                        }

                        List listfyear = em.createNativeQuery("select f_year from yearend where yearendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
                        Vector elementFyear = (Vector) listfyear.get(0);
                        fyear = Integer.parseInt(elementFyear.get(0).toString());

                        if (iy == 11 && tranDesc != 9) {
                            List list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear=extract(year from '" + dt + "')").getResultList();
                            Vector element1 = (Vector) list.get(0);
                            seqNo = Float.parseFloat(element1.get(0).toString());
                            Query q1 = em.createNativeQuery("Insert into bill_sundry(Fyear,AcNo,amount,enterby,dt,status,auth,"
                                    + "trantype,seqno,ty,recno,origindt,authby) values(Year('" + dt + "'),'" + acno + "',ifnull(" + crAmt + ",0),'" + enterBy + "','" + dt + "',"
                                    + "'ISSUED','Y',0," + seqNo + ",0," + recno + ",'" + dt + "','" + authBy + "')");
                            q1.executeUpdate();
                        }

                        if (iy == 12 && tranDesc != 9) {
                            List list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_suspense where Fyear=extract(year from '" + dt + "')").getResultList();
                            Vector element1 = (Vector) list.get(0);
                            seqNo = Float.parseFloat(element1.get(0).toString());
                            Query q1 = em.createNativeQuery("Insert into bill_suspense(Fyear,AcNo,amount,enterby,dt,status,auth,"
                                    + "trantype,seqno,ty,recno,origindt,authby) values(Year('" + dt + "'),'" + acno + "',ifnull(" + crAmt + ",0),'" + enterBy + "','" + dt + "',"
                                    + "'ISSUED','Y',0," + seqNo + ",0," + recno + ",'" + dt + "','" + authBy + "')");
                            q1.executeUpdate();
                        }

                        if (iy == 13 && tranDesc != 9) {
                            List list = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_clgadjustment where acno='" + acno + "' and Fyear=extract(year from '" + dt + "')").getResultList();
                            Vector element1 = (Vector) list.get(0);
                            seqNo = Float.parseFloat(element1.get(0).toString());
                            Query q1 = em.createNativeQuery("Insert into bill_clgadjustment(Fyear,AcNo,amount,enterby,dt,status,auth,"
                                    + "trantype,seqno,ty,recno,origindt,authby) values(Year('" + dt + "'),'" + acno + "',ifnull(" + crAmt + ",0),'" + enterBy + "','" + dt + "',"
                                    + "'ISSUED','Y',0," + seqNo + ",0," + recno + ",'" + dt + "','" + authBy + "')");
                            q1.executeUpdate();
                        }

                        //if (acno.substring(2, 4).equals("NP")) {
                        if (fTSPosting43CBSBean.getAccountCode(acno).equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                            Query q1 = em.createNativeQuery("Insert into npa_recon(AcNo,ty,dt,valuedt,Dramt,Cramt,TranType,EnterBy,Auth,"
                                    + "recno,Payby,AuthBy,TranDesc,Iy,IntAmt,Status,org_brnid,dest_brnid) values('" + acno + "',1,"
                                    + "CURRENT_TIMESTAMP," + valueDate + ",0," + crAmt + ",0,'" + enterBy + "','Y'," + recno + ",3,'" + authBy + "'," + tranDesc + ","
                                    + "" + iy + ",0,''," + orgBrnId + "," + destBrnId + ")");
                            q1.executeUpdate();
                        }
                        //For Cash Handling Charges
//                        List cshHandlingList = em.createNativeQuery("select fix_perc_flag,amt,cr_gl_head from "
//                                + "cbs_charge_detail where charge_type='TXN' and charge_name='CASH-HANDLING-CHG' and "
//                                + "from_range<=" + crAmt + " and to_range>=" + crAmt + " and "
//                                + "ac_type='" + fTSPosting43CBSBean.getAccountCode(acno) + "'").getResultList();

                        List cshHandlingList = em.createNativeQuery("select fix_perc_flag,amt,cr_gl_head,from_range,"
                                + "to_range from cbs_charge_detail where charge_type='TXN' and "
                                + "charge_name='CASH-HANDLING-CHG' and from_range<=" + crAmt + " and "
                                + "ac_type='" + fTSPosting43CBSBean.getAccountCode(acno) + "' order "
                                + "by from_range").getResultList();
                        if (!cshHandlingList.isEmpty()) {
                            cashHandlingMsg = processCashHandlingCharges(cshHandlingList, acno, crAmt, orgnBrCode, authBy);
                        }
                    }
                }

                //Query q1 = em.createNativeQuery("DELETE FROM tokentable_credit WHERE RECNO IN (SELECT RECNO FROM recon_cash_d WHERE TRSNO=" + trsNo + ") and org_brnid='" + orgnBrCode + "'");
                Query q1 = em.createNativeQuery("update tokentable_credit set auth='Y', authby='" + authBy + "' WHERE RECNO IN "
                        + "(SELECT RECNO FROM recon_cash_d WHERE TRSNO=" + trsNo + ") and org_brnid='" + orgnBrCode + "'");
                int no = q1.executeUpdate();
                if (no <= 0) {
                    ut.rollback();
                    return msg = "Problem in tokentable_credit updation";
                }
                if (cashHandlingMsg.equals("")) {
                    msg = "YES" + fyear + seqNo; //Old Code
                } else {
                    msg = "YES" + "Cash-Handling" + cashHandlingMsg;
                }
            } else {
                msg = "Data does not exist";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String processCashHandlingCharges(List cshHandlingList, String acno, double amt,
            String orgnCode, String authBy) throws ApplicationException {
        String cashHandlingMsg = "";
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            double partySerCharge = 0.0;
            String currentDt = fTSPosting43CBSBean.daybeginDate(orgnCode);
            int taxApplyCode = fTSPosting43CBSBean.getCodeForReportName("STAXMODULE_ACTIVE");
            float trsno = fTSPosting43CBSBean.getTrsNo().floatValue();

//            Vector element = (Vector) cshHandlingList.get(0);
//            String chargeFlag = element.get(0).toString();
//            Double amtOrPerc = Double.parseDouble(element.get(1).toString());
//            String chgCrGlHead = element.get(2).toString();
//            if (chgCrGlHead == null || chgCrGlHead.trim().equals("") || chgCrGlHead.trim().length() != 10) {
//                throw new ApplicationException("Please define proper charge gl head for cash handling.");
//            }
//            chgCrGlHead = orgnCode + chgCrGlHead;
//            
//            if (chargeFlag.equalsIgnoreCase("P")) {
//                amtOrPerc = (amt * amtOrPerc) / 100;
//            }
//            amtOrPerc = Double.parseDouble(nf.format(amtOrPerc));
            List<String> chargeList = getCashHandlingCharge(cshHandlingList, amt);
            Double amtOrPerc = Double.parseDouble(nf.format(Double.parseDouble(chargeList.get(0))));
            System.out.println("Total Charge Amount Is-->" + amtOrPerc);
            String chgCrGlHead = orgnCode + chargeList.get(1);

            if (taxApplyCode == 1) {
                Map<String, Double> map = interBranchFacade.getTaxComponent(amtOrPerc, ymd.format(new Date()));
                Set<Map.Entry<String, Double>> set = map.entrySet();
                Iterator<Map.Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = it.next();
                    partySerCharge = partySerCharge + Double.parseDouble(entry.getValue().toString());
                }
            }

            double totalDramt = amtOrPerc + partySerCharge;
            String msg = fTSPosting43CBSBean.checkBalance(acno, totalDramt, authBy);
            if (!msg.equalsIgnoreCase("TRUE")) {
                int n = em.createNativeQuery("insert into pendingcharges(acno,dt,amount,details,"
                        + "instno,ty,trantype,recno,trsno,enterby,auth, authby,updatedt,updateby,charges,"
                        + "recover,trandesc) values('" + acno + "','" + currentDt + "',"
                        + "" + nf.format(totalDramt) + ",'Pending Cash Handling Charges','',1,2,0,0,'" + authBy + "',"
                        + "'Y','" + authBy + "','" + currentDt + "','" + authBy + "',Null,'N',7)").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in pending charges maintenance.");
                }
                return "Pending";
            }
            //Charge deduction
            String acNature = fTSPosting43CBSBean.getAccountNature(acno);
            msg = fTSPosting43CBSBean.insertRecons(acNature, acno, 1, amtOrPerc, currentDt, currentDt, 2,
                    "Cash Handling Charges", authBy, trsno, ymdh.format(new Date()),
                    fTSPosting43CBSBean.getRecNo().floatValue(), "Y", authBy, 35, 3, "", "", 0.0f, "",
                    "", 0, "", 0.0f, "", "", orgnCode, orgnCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            if (partySerCharge != 0 && taxApplyCode == 1) {
                msg = fTSPosting43CBSBean.insertRecons(acNature, acno, 1, partySerCharge, currentDt,
                        currentDt, 2, "GST for Cash Handling Charges", authBy, trsno, ymdh.format(new Date()),
                        fTSPosting43CBSBean.getRecNo().floatValue(), "Y", authBy, 71, 3, "", "", 0.0f, "",
                        "", 0, "", 0.0f, "", "", orgnCode, orgnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            msg = fTSPosting43CBSBean.updateBalance(acNature, acno, 0.0, Double.parseDouble(nf.format(totalDramt)), "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            acNature = fTSPosting43CBSBean.getAccountNature(chgCrGlHead);
            msg = fTSPosting43CBSBean.insertRecons(acNature, chgCrGlHead, 0, amtOrPerc, currentDt, currentDt, 2,
                    "Cash Handling Charges", authBy, trsno, ymdh.format(new Date()),
                    fTSPosting43CBSBean.getRecNo().floatValue(), "Y", authBy, 35, 3, "", "", 0.0f, "", "",
                    0, "", 0.0f, "", "", orgnCode, orgnCode, 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            msg = fTSPosting43CBSBean.updateBalance(acNature, chgCrGlHead, amtOrPerc, 0.0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }

            if (taxApplyCode == 1) {
                Map<String, Double> map = interBranchFacade.getTaxComponent(amtOrPerc, ymd.format(new Date()));
                Set<Entry<String, Double>> staxSet = map.entrySet();
                Iterator<Entry<String, Double>> staxIt = staxSet.iterator();
                while (staxIt.hasNext()) {
                    Entry entry = staxIt.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    String description = keyArray[0];
                    String taxHead = orgnCode + keyArray[1];
                    double serAmount = Double.parseDouble(entry.getValue().toString());

                    if (serAmount != 0) {
                        description = description + " Cash Handling Charges";
                        msg = fTSPosting43CBSBean.insertRecons("PO", taxHead, 0, serAmount, currentDt, currentDt, 2,
                                description, authBy, trsno, ymdh.format(new Date()),
                                fTSPosting43CBSBean.getRecNo().floatValue(), "Y", authBy, 71, 3, "", "", 0.0f, "",
                                "", 0, "", 0.0f, "", "", orgnCode, orgnCode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }

                        msg = fTSPosting43CBSBean.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }
                }
            }
            cashHandlingMsg = String.valueOf(trsno);
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return cashHandlingMsg;
    }

    private List<String> getCashHandlingCharge(List cshHandlingList, double cramt) throws Exception {
        List<String> resultList = new ArrayList<String>(); //0- chargeAmount, 1- GL Head
        List<CashHandlingDTO> list = new ArrayList<CashHandlingDTO>();
        try {
            Double tempCreditAmount = cramt;
            for (int i = 0; i < cshHandlingList.size(); i++) {
                CashHandlingDTO dto = new CashHandlingDTO();
                Vector ele = (Vector) cshHandlingList.get(i);

                System.out.println("tempCreditAmount-->" + tempCreditAmount + ":Flag-->" + ele.get(0).toString() + ":PercAmt-->" + ele.get(1).toString() + "\n");
                if (tempCreditAmount <= (Double.parseDouble(ele.get(4).toString()) - Double.parseDouble(ele.get(3).toString())) + 1) {
                    dto.setBreakedAmount(new BigDecimal(tempCreditAmount));
                    dto.setFixedOrPercFlag(ele.get(0).toString());
                    dto.setFixedAmtOrPerc(new BigDecimal(ele.get(1).toString()));
                    dto.setChargeGlHead(ele.get(2).toString());

                    list.add(dto);
                } else if (tempCreditAmount > (Double.parseDouble(ele.get(4).toString()) - Double.parseDouble(ele.get(3).toString())) + 1) {
                    dto.setBreakedAmount(new BigDecimal((Double.parseDouble(ele.get(4).toString()) - Double.parseDouble(ele.get(3).toString())) + 1));
                    dto.setFixedOrPercFlag(ele.get(0).toString());
                    dto.setFixedAmtOrPerc(new BigDecimal(ele.get(1).toString()));
                    dto.setChargeGlHead(ele.get(2).toString());
                    tempCreditAmount = tempCreditAmount - ((Double.parseDouble(ele.get(4).toString()) - Double.parseDouble(ele.get(3).toString())) + 1);

                    list.add(dto);
                }
            }
            BigDecimal totalChargeAmount = BigDecimal.ZERO;
            String chargeGlHead = "";
            for (int z = 0; z < list.size(); z++) {
                CashHandlingDTO dto = list.get(z);
                System.out.println("breakedAmount-->" + dto.getBreakedAmount() + ":fixedOrPercFlag->"
                        + dto.getFixedOrPercFlag() + ":fixedAmtOrPerc->" + dto.getFixedAmtOrPerc() + ":chargeGlHead->"
                        + dto.getChargeGlHead() + "\n");

                if (dto.getFixedOrPercFlag().equalsIgnoreCase("P")) {
                    totalChargeAmount = totalChargeAmount.add((dto.getBreakedAmount().multiply(dto.getFixedAmtOrPerc())).divide(new BigDecimal("100")));
                } else if (dto.getFixedOrPercFlag().equalsIgnoreCase("F")) {
                    totalChargeAmount = totalChargeAmount.add(dto.getFixedAmtOrPerc());
                }
                chargeGlHead = dto.getChargeGlHead(); //Since all glhead will be same.
            }
            if (chargeGlHead == null || chargeGlHead.trim().equals("") || chargeGlHead.trim().length() != 10) {
                throw new ApplicationException("Please define proper charge gl head for cash handling.");
            }
            resultList.add(totalChargeAmount.toString());
            resultList.add(chargeGlHead);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return resultList;
    }

    public String cbsAuthCashCrDeletion(float recno, String instno, String acno, double amount,
            String dt, String custName, String subtokenno, float tokenno, String origindt, String enterby,
            String authby, String orgCode) throws ApplicationException {
        String msg = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String poflag, instNature;
            int postFlag, cashDenoModule = 0, a;
            Query q1 = em.createNativeQuery(" DELETE FROM tokentable_credit WHERE ACNO='" + acno + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND RECNO=" + recno + " and org_brnid='" + orgCode + "'");
            a = q1.executeUpdate();
            if (a <= 0) {
                ut.rollback();
                msg = "Problem Occured In Cr Deletion";
                return msg;
            }
            //acNature = fTSPosting43CBSBean.getAccountNature(acno);
            Query q2 = em.createNativeQuery("Insert into deletetrans(acno,cramt,dramt,enterby,deletedby,deletedt,authby,custname,subtokenno,tokenno,"
                    + "trantype,trantime,org_brnid,dest_brnid)values ('" + acno + "'," + amount + ", 0,'" + enterby + "','" + authby
                    + "', date_format(STR_TO_DATE('" + origindt + "', '%d/%m/%Y'),'%Y%m%d'),'','" + custName + "','" + subtokenno + "',"
                    + tokenno + ",0,CURRENT_TIMESTAMP(),'" + orgCode + "','" + acno.substring(0, 2) + "')");
            a = q2.executeUpdate();
            if (a <= 0) {
                ut.rollback();
                msg = "Problem Occured In Cr Deletion";
                return msg;
            }
            //  if (acno.substring(2, 4).equals("GL")) {
            if (fTSPosting43CBSBean.getAccountCode(acno).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                List l4 = em.createNativeQuery("select glhead,instnature from billtypemaster where glhead = SUBSTRING('" + acno + "', 3, 8)").getResultList();
                if (!l4.isEmpty()) {
                    poflag = "true";
                    List l5 = em.createNativeQuery("SELECT instnature from billtypemaster where glhead = SUBSTRING('" + acno + "', 3, 8)").getResultList();
                    Vector v2 = (Vector) l5.get(0);
                    instNature = v2.get(0).toString();
                } else {
                    poflag = "false";
                    instNature = "";
                }
                List l5 = em.createNativeQuery("select postflag from gltable where acno ='" + acno + "'").getResultList();
                if (!l5.isEmpty()) {
                    List l6 = em.createNativeQuery("select postflag from gltable where acno ='" + acno + "'").getResultList();
                    Vector v3 = (Vector) l6.get(0);
                    postFlag = Integer.parseInt(v3.get(0).toString());
                    if (postFlag == 12) {
                        Query q5 = em.createNativeQuery("update bill_suspense set status = 'Issued',dt=date_format(STR_TO_DATE('" + origindt + "', '%d/%m/%Y'),'%Y%m%d'),lastupdateby=null where dt =date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q5.executeUpdate();
                        if (a <= 0) {
                            ut.rollback();
                            msg = "Problem Occured In Cr Deletion";
                            return msg;
                        }
                    }
                    if (postFlag == 13) {
                        Query q5 = em.createNativeQuery("update bill_clgadjustment set status = 'ISSUED',dt=date_format(STR_TO_DATE('" + origindt + "', '%d/%m/%Y'),'%Y%m%d'),lastupdateby=null where dt ='" + dt + "' and recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q5.executeUpdate();

                        if (a <= 0) {
                            ut.rollback();
                            msg = "Problem Occured In Cr Deletion";
                            return msg;
                        }

                    }
                }
                if (poflag.equals("true")) {
                    if (instNature.equals("DD")) {
                        Query q5 = em.createNativeQuery("delete from bill_dd where dt = date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q5.executeUpdate();

                    } else if (instNature.equals("PO")) {
                        Query q5 = em.createNativeQuery("delete from bill_po where dt = date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q5.executeUpdate();
                    } else if (instNature.equals("TPO")) {
                        Query q5 = em.createNativeQuery("delete from bill_tpo where dt = date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and  recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q5.executeUpdate();
                    } else {
                        Query q5 = em.createNativeQuery("delete from bill_ad where dt = date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and  recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q5.executeUpdate();

                        Query q6 = em.createNativeQuery("delete from bill_hoothers where dt = date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and  recno =" + recno + " and SUBSTRING(acno,1,2)='" + orgCode + "'");
                        a = q6.executeUpdate();

                    }
                }
                if (cashDenoModule == 1) {
                    Query q5 = em.createNativeQuery(" Delete from denominition where acno='" + acno + "' and dt= date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') and recno=" + recno + "");
                    a = q5.executeUpdate();
                    if (a <= 0) {
                        ut.rollback();
                        msg = "Problem Occured In Cr Deletion";
                        return msg;
                    }
                }

            }
            //STR
            List isAlertList = em.createNativeQuery("select * from cbs_str_detail where acno = '" + acno + "' and dt= '" + ymd.format(dmyOne.parse(dt)) + "' and recno = " + recno + " and auth_status = 'N'").getResultList();
            if (!isAlertList.isEmpty()) {
                String strResult = fTSPosting43CBSBean.strDetailDeletion(acno, new Float(0), recno, ymd.format(dmyOne.parse(dt)), enterby);
                if (!strResult.equalsIgnoreCase("true")) {
                    ut.rollback();
                    throw new ApplicationException("Deletion problem in cbs_str_detail");
                }
            }
            //Denomination deletion 16/11/2015
            String denoMessage = interBranchFacade.deleteDenomination(recno, ymd.format(dmyOne.parse(dt)));
            if (!denoMessage.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(denoMessage);
            }
            //End
            msg = "Entry Deleted Successfully.";
            ut.commit();
            msg = "TRUE" + msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }

    public String cbsCashCrDestAuth(float recno, String authBy, String enterBy, String orgCode) throws ApplicationException {
        String msg = null, acno = null, custName = null, dt = null, instNo = null, operMode = null, auth = null, tokenPaidBy = null,
                subTokenNo = null, tranTime = null, details = null, intFlag = null, closeFlag = null, tdAcno = null, tokenPaid = null,
                termId = null, orgBrnid = null, destBrnid = null, valueDate = null;
        double drAmt = 0.0d, crAmt = 0.0d;
        float recno1 = 0.0f, voucherNo = 0.0f, tokenNo = 0.0f;
        Integer ty = 0, tranType = 0, trsno = 0, iy = 0, tranDesc = 0, tranId = 0, payBy = 0;
        String cashHandlingMsg = "";
        UserTransaction ut = context.getUserTransaction();
        try {

            if (!String.valueOf(recno).equals("") && !orgCode.equals("")) {
                List list = em.createNativeQuery("SELECT ACNO, CUSTNAME,DATE_FORMAT(DT,'%Y%m%d'),DRAMT,CRAMT,TY,TRANTYPE,RECNO, TRSNO, INSTNO, CAST(PAYBY AS unsigned), IY, COALESCE(OPERMODE,''),AUTH,TOKENPAIDBY,TRANDESC,TOKENNO, COALESCE(SUBTOKENNO,''),DATE_FORMAT(TRANTIME,'%Y%m%d'),DETAILS,COALESCE(VOUCHERNO,''),COALESCE(INTFLAG,''),COALESCE(CLOSEFLAG,''),COALESCE(TDACNO,''),COALESCE(TOKENPAID,''),TRAN_ID,TERM_ID,ORG_BRNID,DEST_BRNID,DATE_FORMAT(VALUEDT,'%Y%m%d') FROM tokentable_credit WHERE RECNO IN(" + recno + ") AND ORG_BRNID = '" + orgCode + "'").getResultList();
                Vector element = (Vector) list.get(0);
                acno = element.get(0).toString();
                custName = element.get(1).toString();
                dt = element.get(2).toString();
                drAmt = Double.parseDouble(element.get(3).toString());
                crAmt = Double.parseDouble(element.get(4).toString());
                ty = Integer.parseInt(element.get(5).toString());
                tranType = Integer.parseInt(element.get(6).toString());
                recno1 = Float.parseFloat(element.get(7).toString());
                trsno = Integer.parseInt(element.get(8).toString());
                instNo = element.get(9).toString();
                payBy = Integer.parseInt(element.get(10).toString());
                iy = Integer.parseInt(element.get(11).toString());
                operMode = element.get(12).toString();
                auth = element.get(13).toString();
                tokenPaidBy = element.get(14).toString();
                tranDesc = Integer.parseInt(element.get(15).toString());
                tokenNo = Float.parseFloat(element.get(16).toString());
                subTokenNo = element.get(17).toString();
                tranTime = element.get(18).toString();
                details = element.get(19).toString();
                voucherNo = element.get(20).toString().equals("") ? 0f : Float.parseFloat(element.get(20).toString());
                intFlag = element.get(21).toString();
                closeFlag = element.get(22).toString();
                tdAcno = element.get(23).toString();
                tokenPaid = element.get(24).toString();
                tranId = Integer.parseInt(element.get(25).toString());
                termId = element.get(26).toString();
                orgBrnid = element.get(27).toString();
                destBrnid = element.get(28).toString();
                valueDate = element.get(29).toString();
                //  String accNature = fnAcNature(acno.substring(2, 4));
                String accNature = fTSPosting43CBSBean.getAccountNature(acno);
                if (accNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && tranDesc == 1) {
                    List checkList = em.createNativeQuery("select *from rd_installment where acno='" + acno + "' and STATUS = 'UNPAID'").getResultList();
                    if (checkList.isEmpty()) {
                        msg = "All installments for this A/C has been paid.You can not paid further installments.";
                        return msg;
                    }
                    List checkListAmt = em.createNativeQuery("select sum(installamt) from rd_installment where acno='" + acno + "' and STATUS = 'UNPAID'").getResultList();
                    if (!checkListAmt.isEmpty()) {
                        Vector vecAmt = (Vector) checkListAmt.get(0);
                        if (crAmt > Double.parseDouble(vecAmt.get(0).toString())) {
                            msg = "You can not pay more then total installment amount for this A/C.";
                            return msg;
                        }

                    }
                }

                ut.begin();
                Float trsno1 = fTSPosting43CBSBean.getTrsNo();

                if (details.indexOf("~`") > -1) {
                    String status = null, date = null, billType = null, accNo = null, cuName = null,
                            payableAt = null, dt1 = null, originDt = null, inFavourOf = null, lastUpdateBy = null, enterByDtl = null;
                    float seqNo = 0.0f;
                    double amount = 0.0d, comm = 0.0d;
                    int msgNo = 0, msgBillStart = 0, msgBillPo = 0, msgBillEnd = 0, timeLimit = 0, trTp = 0, ty1 = 0;
                    String[] elements = details.split("~`");
                    msgNo = Integer.parseInt(elements[1]);
                    msgBillStart = 110;
                    status = elements[11];

                    msgBillPo = Integer.parseInt(elements[1]);
                    msgBillEnd = 140;
                    date = elements[9];
                    seqNo = Float.parseFloat(elements[2]);
                    instNo = elements[3];

                    billType = elements[4];
                    accNo = elements[5];
                    cuName = elements[6];
                    payableAt = elements[7];

                    amount = Double.parseDouble(elements[8]);
                    dt1 = elements[9];
                    originDt = elements[9];
                    timeLimit = Integer.parseInt(elements[12]);

                    comm = Double.parseDouble(elements[13]);
                    trTp = Integer.parseInt(elements[14]);
                    ty1 = Integer.parseInt(elements[15]);

                    inFavourOf = elements[16];
                    enterByDtl = elements[17];
                    lastUpdateBy = elements[18];

                    if ((msgNo >= msgBillStart) && (msgNo <= msgBillEnd)) {
                        if (status.equalsIgnoreCase("ISSUED")) {
                            if (msgNo == msgBillPo) {
                                if (msgNo == 110) {
                                    Query q1 = em.createNativeQuery("insert into bill_po(FYEAR,SEQNO,INSTNO,BILLTYPE,ACNO,CUSTNAME,PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,TIMELIMIT,COMM,TRANTYPE,TY,INFAVOUROF,PLACE,ENTERBY,LASTUPDATEBY,AUTH,AUTHBY,TRANTIME,RECNO,printflag,validationdt) "
                                            + "values(DATE_FORMAT('" + ymd.format(dmyOne.parse(date)) + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + accNo + "','" + cuName + "','" + payableAt + "'," + amount + ",'" + ymd.format(dmyOne.parse(dt1)) + "','" + ymd.format(dmyOne.parse(originDt)) + "','" + status + "'," + timeLimit + "," + comm + "," + trTp + "," + ty1 + ",'" + inFavourOf + "','','" + enterByDtl + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recno1 + ",0,'" + originDt + "')");
                                    q1.executeUpdate();
                                    msg = "TRUE";
                                } else if (msgNo == 120) {
                                    Query q2 = em.createNativeQuery("insert into bill_tpo (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag) "
                                            + "values(DATE_FORMAT('" + ymd.format(dmyOne.parse(date)) + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + accNo + "','" + cuName + "','" + payableAt + "'," + amount + ",'" + ymd.format(dmyOne.parse(dt1)) + "','" + ymd.format(dmyOne.parse(originDt)) + "','" + status + "'," + timeLimit + "," + comm + "," + trTp + "," + ty1 + ",'" + inFavourOf + "','','" + enterByDtl + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recno1 + ",0,'') ");
                                    q2.executeUpdate();
                                    msg = "TRUE";
                                } else if (msgNo == 130) {
                                    Query q2 = em.createNativeQuery("insert into bill_ad (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag) "
                                            + "values(DATE_FORMAT('" + ymd.format(dmyOne.parse(date)) + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + accNo + "','" + cuName + "','" + payableAt + "'," + amount + ",'" + ymd.format(dmyOne.parse(dt1)) + "','" + ymd.format(dmyOne.parse(originDt)) + "','" + status + "'," + timeLimit + "," + comm + "," + trTp + "," + ty1 + ",'" + inFavourOf + "','','" + enterByDtl + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recno1 + ",0) ");
                                    q2.executeUpdate();
                                    msg = "TRUE";
                                } else if (msgNo == 140) {
                                    Query q2 = em.createNativeQuery("insert into bill_dd (FYEAR, SEQNO, INSTNO, BILLTYPE, ACNO, CUSTNAME, PAYABLEAT, AMOUNT, DT, ORIGINDT, STATUS, TIMELIMIT, COMM, TRANTYPE, TY, INFAVOUROF, PLACE, ENTERBY, LASTUPDATEBY, AUTH, AUTHBY, TRANTIME, RECNO, printflag, org_brnid) "
                                            + "values(DATE_FORMAT('" + ymd.format(dmyOne.parse(date)) + "','%Y')," + seqNo + ",'" + instNo + "','" + billType + "','" + accNo + "','" + cuName + "','" + payableAt + "'," + amount + ",'" + ymd.format(dmyOne.parse(dt1)) + "','" + ymd.format(dmyOne.parse(originDt)) + "','" + status + "'," + timeLimit + "," + comm + "," + trTp + "," + ty1 + ",'" + inFavourOf + "','','" + enterByDtl + "','" + lastUpdateBy + "','N','',CURRENT_TIMESTAMP," + recno1 + ",0,'" + orgBrnid + "') ");
                                    q2.executeUpdate();
                                    msg = "TRUE";
                                }
                            }
                        }
                    }
                }

                msg = interBranchFacade.cbsPostingSx(acno, ty, valueDate, crAmt, 0f, tranType, details, tokenNo, subTokenNo, instNo, "", payBy, voucherNo, recno1, tranDesc, destBrnid, orgBrnid, enterBy, authBy, trsno1, "", "");
                //System.out.println("msg1 is::::" + msg);
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    ut.rollback();
                    return msg;
                }

                msg = interBranchFacade.cbsPostingCx(acno, ty, valueDate, crAmt, 0f, tranType, details, tokenNo, subTokenNo, instNo, "", payBy, voucherNo, 0f, tranDesc, destBrnid, orgBrnid, enterBy, authBy, trsno1, "", "");
                //System.out.println("msg2 is::::" + msg);
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    ut.rollback();
                    return msg;
                }
                //Deaf updation
                fTSPosting43CBSBean.lastTxnDateUpdation(acno);
                //Deaf updation end here
                //For Cash Handling Charges
//                List cshHandlingList = em.createNativeQuery("select fix_perc_flag,amt,cr_gl_head from "
//                        + "cbs_charge_detail where charge_type='TXN' and charge_name='CASH-HANDLING-CHG' and "
//                        + "from_range<=" + crAmt + " and to_range>=" + crAmt + " and "
//                        + "ac_type='" + fTSPosting43CBSBean.getAccountCode(acno) + "'").getResultList();
                List cshHandlingList = em.createNativeQuery("select fix_perc_flag,amt,cr_gl_head,from_range,"
                        + "to_range from cbs_charge_detail where charge_type='TXN' and "
                        + "charge_name='CASH-HANDLING-CHG' and from_range<=" + crAmt + " and "
                        + "ac_type='" + fTSPosting43CBSBean.getAccountCode(acno) + "' order "
                        + "by from_range").getResultList();
                if (!cshHandlingList.isEmpty()) {
                    cashHandlingMsg = processCashHandlingCharges(cshHandlingList, acno, crAmt, orgBrnid, authBy);
                }

                ut.commit();
                msg = "Authorization is over";
                if (!cashHandlingMsg.trim().equals("")) {
                    msg = "Cash-Handling" + cashHandlingMsg;
                }
                try {
                    String details1 = "";
                    if (details.length() <= 30) {
                        details1 = details;
                    } else if (details.length() > 30) {
                        details1 = details.substring(1, 30);
                    }
                    //Sms Sending.
                    String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                            == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                    if (templateType.equalsIgnoreCase("indr")) {
                        smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT_INDR, acno, 0, 0, crAmt,
                                dmyOne.format(ymd.parse(valueDate)), details1);
                    } else {
                        smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT, acno, 0, 0, crAmt,
                                dmyOne.format(ymd.parse(valueDate)), "");
                    }
//                    smsFacade.sendTransactionalSms(SmsType.CASH_DEPOSIT, acno, 0, 0, crAmt,
//                            dmyOne.format(ymd.parse(valueDate)),"");
                } catch (Exception ex) {
                    System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::" + crAmt);
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }
    /*                       End of Cash Credit Authorization         */

    /*                       Start of Cash Debit Authorization         */
    public List getUnAuthCashdr(String orgnBrCode) throws ApplicationException {
        try {
            String cashMod = "";
            List cashModLt = em.createNativeQuery("SELECT COALESCE(CASHMOD,'Y') FROM parameterinfo WHERE brncode = CAST('" + orgnBrCode + "' AS unsigned)").getResultList();
            Vector cashModVerLst = (Vector) cashModLt.get(0);
            cashMod = cashModVerLst.get(0).toString();
            List resultList = new ArrayList();
            if (cashMod.equalsIgnoreCase("N")) {
                String query = "select t.Tokenno,t.Subtokenno,t.acno,t.custname,t.dramt,ifnull(ifnull(ifnull(r1.balance,r2.balance), "
                        + "r3.balance),0) as Balance,ifnull(t.INSTNO,'') as instno,t.enterby,t.auth,t.recno,t.details,t.IY,t.org_brnid, "
                        + "t.dest_brnid,date_format(t.instdt,'%d/%m/%Y'),date_format(t.valuedt,'%d/%m/%Y'),trandesc,atm.acctnature,t.voucherno from tokentable_debit t "
                        + "left join reconbalan r1 on t.acno=r1.acno left join ca_reconbalan r2 on "
                        + "t.acno=r2.acno left join td_reconbalan r3 on t.acno=r3.acno left join accounttypemaster atm on substring(t.acno,3,2) = atm.acctcode "
                        + "where t.tokenpaidby='SYSTEM' "
                        + "and (t.auth='N' OR t.auth=null) and t.trantype=0 and t.ty=1 and t.org_brnid='" + orgnBrCode + "' order by t.RECNO";
                Query selectQuery = em.createNativeQuery(query);
                resultList = selectQuery.getResultList();
            } else {
                String query = "select t.Tokenno,t.Subtokenno,t.acno,t.custname,t.dramt,ifnull(ifnull(ifnull(r1.balance,r2.balance), "
                        + "r3.balance),0) as Balance,ifnull(t.INSTNO,'') as instno,t.enterby,t.auth,t.recno,t.details,t.IY,t.org_brnid, "
                        + "t.dest_brnid,date_format(t.instdt,'%d/%m/%Y'),date_format(t.valuedt,'%d/%m/%Y'),trandesc,atm.acctnature,t.voucherno  from tokentable_debit t "
                        + "left join reconbalan r1 on t.acno=r1.acno left join ca_reconbalan r2 on "
                        + "t.acno=r2.acno left join td_reconbalan r3 on t.acno=r3.acno left join accounttypemaster atm on substring(t.acno,3,2) = atm.acctcode "
                        + "where (t.tokenpaidby='' OR t.tokenpaidby=null) "
                        + "and (t.auth='N' OR t.auth=null) and t.trantype=0 and t.ty=1 and t.iy<>3333 and t.org_brnid='" + orgnBrCode + "' order by t.RECNO";
                Query selectQuery = em.createNativeQuery(query);
                resultList = selectQuery.getResultList();
            }
            return resultList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
            //return null;
        }
    }

    public String cbsAuthCashDr(float recno, String auth1, int msgNo, int msgBillStart, String status, int msgBillPO,
            int msgBillEnd, String date, float seqNo, String instno, String billType, String acno, String custname,
            String payableAt, double amount, String dt, String originDt, int timeLimit, double comm, int tranType, int ty,
            String inFavourOf, String enterBy, String lastUpdateBy, String orgnBrCode, String valueDate, String InstrDt,
            BigDecimal tdsToBeDeducted) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            String sFlag = "", cashMod = "", msgAuth = "", msgMakeEntry = "", flag = "";
            float trsno = 0f;
            trsno = fTSPosting43CBSBean.getTrsNo();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (billType != null && billType.equalsIgnoreCase("PO")) {
                String format = ymd.format(sdf.parse(originDt));
                List checkList = em.createNativeQuery("select status from bill_po where seqno=" + seqNo + " and "
                        + "instno ='" + instno + "' and billtype ='" + billType + "' and acno = '" + acno + "' and "
                        + "origindt = '" + format + "'").getResultList();
                if (!checkList.isEmpty()) {
                    Vector element = (Vector) checkList.get(0);
                    String checkStatus = (String) element.get(0);
                    if (checkStatus.equalsIgnoreCase("PAID")) {
                        throw new ApplicationException("This instrument no. has been already paid.");
                    }
                }
            }
            List l1 = em.createNativeQuery("select cashMod from parameterinfo where "
                    + "brncode=convert('" + orgnBrCode + "',unsigned)").getResultList();
            Vector v1 = (Vector) l1.get(0);
            cashMod = v1.get(0).toString();

            if (String.valueOf(recno).equalsIgnoreCase("")) {
                throw new ApplicationException("You have not selected any record for authorization");
            }
            if (cashMod.equalsIgnoreCase("N")) {
                Query q2 = em.createNativeQuery("update tokentable_debit set auth='Y', authby='" + auth1 + "', "
                        + "trsno=" + trsno + "where recno in (" + recno + ") and org_brnid ='" + orgnBrCode + "'");
                q2.executeUpdate();

                msgAuth = authCashDebitWithoutCashier(auth1, trsno, orgnBrCode, tdsToBeDeducted);
                sFlag = msgAuth;
                if (msgNo > 0) {
                    msgMakeEntry = fTSPosting43CBSBean.cbsAuthCashCrMakeEntry(msgNo, msgBillStart, status, msgBillPO,
                            msgBillEnd, auth1, date, seqNo, instno, billType, acno, custname, payableAt, amount, dt,
                            originDt, timeLimit, comm, tranType, ty, inFavourOf, enterBy, lastUpdateBy, recno, orgnBrCode);
                    flag = msgMakeEntry;
                    if (!flag.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException("Problem Occured In Dr Authorization " + flag);
                    }
                }
                if (!sFlag.substring(0, 3).equalsIgnoreCase("YES")) {
                    throw new ApplicationException("Problem Occured In Dr Authorization " + sFlag);
                }
                msg = "true";
                if (Float.parseFloat(sFlag.substring(7)) != 0.0) {
                    msg = "Authorization over. " + " seqNo :- " + sFlag.substring(7) + " Fyear :- " + sFlag.substring(3, 7);
                }
            } else {
                Query q2 = em.createNativeQuery("update tokentable_debit set auth='Y',authby ='" + auth1 + "' where "
                        + "recno in (" + recno + ")  AND ORG_BRNID ='" + orgnBrCode + "'");
                q2.executeUpdate();
                Query q3 = em.createNativeQuery("insert into recon_cash_d(acno ,custname, ty, dt, Dramt, CrAmt, TranType, "
                        + "details, iy, instno, EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo, "
                        + "tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt) select acno ,custname, ty, dt, "
                        + "Dramt, CrAmt, TranType, details, iy, instno, EnterBy, 'Y', recno,payby,authby, trsno, Trantime, "
                        + "TranDesc, TokenNo, tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt from "
                        + "tokentable_debit where recno in (" + recno + ") AND ORG_BRNID ='" + orgnBrCode + "'");
                q3.executeUpdate();
                List l2 = em.createNativeQuery("select r.acno,a.acctNature,r.trandesc from tokentable_debit r join accounttypemaster a "
                        + "on substring(r.acno,3,2)=a.AcctCode  where r.authby='" + auth1 + "' and r.auth='Y' and r.recno=" + recno + "").getResultList();
                if (l2.isEmpty()) {
                    throw new ApplicationException("Data does not exist for authorizayion");
                }
                Vector v3 = (Vector) l2.get(0);
                String acNo = v3.get(0).toString();
                String actNature = v3.get(1).toString();
                int trandesc = Integer.parseInt(v3.get(2).toString());
                
                if (tdsToBeDeducted.compareTo(new BigDecimal(0)) > 0) {
                    String msg1 = fTSPosting43CBSBean.chkBal(acNo, 1, trandesc, actNature);
                    if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                        msg1 = fTSPosting43CBSBean.checkBalance(acNo, tdsToBeDeducted.doubleValue(), enterBy);
                        if (!(msg1.equalsIgnoreCase("TRUE"))) {
                            throw new ApplicationException(msg + " for A/C No. " + acNo);
                        }
                    }
                }
                if (msgNo > 0) {
                    msgMakeEntry = fTSPosting43CBSBean.cbsAuthCashCrMakeEntry(msgNo, msgBillStart, status, msgBillPO,
                            msgBillEnd, auth1, date, seqNo, instno, billType, acno, custname, payableAt, amount, dt,
                            originDt, timeLimit, comm, tranType, ty, inFavourOf, enterBy, lastUpdateBy, recno, orgnBrCode);
                    flag = msgMakeEntry;
                    if (!flag.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException("Problem Occured In Dr Authorization " + flag);
                    }
                }
                msg = "true";
            }
            //Deaf updation
            fTSPosting43CBSBean.lastTxnDateUpdation(acno);
            //Deaf updation end here
            ut.commit();
            try {
                //Sms Sending.
                if (cashMod.equalsIgnoreCase("N")) {
                    String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                            == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                    if (templateType.equalsIgnoreCase("indr")) {
                        List detailsQuery = em.createNativeQuery("select Details from tokentable_debit where recno='" + recno + "' and org_brnid='" + orgnBrCode + "'").getResultList();
                        Vector det = (Vector) detailsQuery.get(0);
                        String details = det.get(0).toString();
                        String details1 = "";
                        if (details.length() <= 30) {
                            details1 = details;
                        } else if (details.length() > 30) {
                            details1 = details.substring(1, 30);
                        }
                        smsFacade.sendTransactionalSms(SmsType.CASH_WITHDRAWAL_INDR, acno, 0, 1, amount,
                                dmyOne.format(ymd.parse(valueDate)), details1);
                    } else {
                        smsFacade.sendTransactionalSms(SmsType.CASH_WITHDRAWAL, acno, 0, 1, amount,
                                dmyOne.format(ymd.parse(valueDate)), "");
                    }
                    List list = em.createNativeQuery("select iy from tokentable_debit where recno=" + recno + " and "
                            + "org_brnid ='" + orgnBrCode + "'").getResultList();
                    Vector ele = (Vector) list.get(0);
                    int exceedIy = Integer.parseInt(ele.get(0).toString());
                    if (exceedIy == 99) {
                        sendCCODExceedSms(acno, amount);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::"
                        + amount + "::Error is-->" + ex.getMessage());
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return msg;
    }

    public void sendCCODExceedSms(String acno, double amount) throws ApplicationException {
        try {
            Integer code = fTSPosting43CBSBean.getCodeForReportName("CC-OD-LIMIT-EXCEED");
            if (code == 1) {
                List list = em.createNativeQuery("select ifnull(odlimit,0) from "
                        + "loan_appparameter where acno='" + acno + "'").getResultList();
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(0);
                    BigDecimal odLimit = new BigDecimal(nf.format(Double.parseDouble(ele.get(0).toString())));
                    BigDecimal balance = new BigDecimal(nf.format(Double.parseDouble(fTSPosting43CBSBean.ftsGetBal(acno))));

                    List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                    String mobileNo = "";
                    List mobileList = em.createNativeQuery("select mobile_no from mb_subscriber_tab "
                            + "where acno='" + acno + "'").getResultList();
                    if (!mobileList.isEmpty()) {
                        Vector mEle = (Vector) mobileList.get(0);
                        mobileNo = mEle.get(0).toString().trim().substring(3);
                    } else {
                        mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(acno);
                    }
                    if (mobileNo.length() == 10) {
                        TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                        tSmsRequestTo.setMsgType("PAT");
                        tSmsRequestTo.setTemplate(SmsType.CC_OD_LIMIT_EXCEED);

                        tSmsRequestTo.setAcno(acno);
                        tSmsRequestTo.setDate(dmyOne.format(new Date()));
                        tSmsRequestTo.setAmount(odLimit.doubleValue());
                        tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                        tSmsRequestTo.setBankName(templateBankName);
                        tSmsRequestTo.setFirstCheque(balance.toString());

                        tSmsRequestTo.setModuleName("NO-CHG");
                        smsFacade.sendSms(tSmsRequestTo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String authCashDebitWithoutCashier(String authby, float trsno, String orgnBrCode, BigDecimal tdsToBeDeducted) throws ApplicationException {

        String acNo, actNature, O, remarks, enterBy, dt, successFlag = "";
        double dramt, balance = 0d;
        float recno;
        float seqNo = 0;
        int fyear = 0;

        try {
            int trandesc, iy;
            Query q4 = em.createNativeQuery("insert into recon_cash_d(acno,custname,ty,dt,Dramt,CrAmt,TranType,details,iy,instno,EnterBy,Auth,recno,payby,"
                    + "Authby,trsno,Trantime,TranDesc,TokenNo,tokenPaidBy,SubTokenNo,org_brnid,dest_brnid,valuedt,instdt) select acno,custname,ty,dt,Dramt,"
                    + "CrAmt,TranType,details,iy,instno,EnterBy,auth,recno,payby,authby,trsno,Trantime,TranDesc,TokenNo,tokenPaidBy,SubTokenNo,org_brnid,"
                    + "dest_brnid,valuedt,instdt from tokentable_debit where trsno=" + trsno + " and  authby='" + authby + "' and auth='Y'");
            int int1 = q4.executeUpdate();
            if (int1 <= 0) {
                successFlag = "Insertion Problem of recon_cash_D";
                return successFlag;
            }

            List l2 = em.createNativeQuery("select r.acno,r.dramt,r.recno,a.acctNature,r.dt,r.trandesc,r.details, r.iy,r.enterby, r.org_brnid, r.dest_brnid,"
                    + "r.valuedt,r.instdt from tokentable_debit r join accounttypemaster a on substring(r.acno,3,2)=a.AcctCode  where r.authby='" + authby
                    + "' and r.auth='Y' and r.trsno=" + trsno + "").getResultList();
            if (!l2.isEmpty()) {
                for (int i = 0; i < l2.size(); i++) {
                    Vector v3 = (Vector) l2.get(i);
                    acNo = v3.get(0).toString();
                    dramt = Double.parseDouble(v3.get(1).toString());
                    recno = Float.parseFloat(v3.get(2).toString());
                    actNature = v3.get(3).toString();
                    dt = v3.get(4).toString();
                    trandesc = Integer.parseInt(v3.get(5).toString());
                    remarks = v3.get(6).toString();
                    iy = Integer.parseInt(v3.get(7).toString());
                    enterBy = v3.get(8).toString();
//                    orgnBrcode = v3.get(9).toString();
//                    destBrCode = v3.get(10).toString();
//                    valueDt = v3.get(11).toString();
//
//                    if ((v3.get(12) == null) || (v3.get(12).toString().equals(""))) {
//                        instrdt = null;
//                    } else {
//                        instrdt = v3.get(12).toString();
//                    }

                    if (acNo.equalsIgnoreCase("") || dramt == 0 || actNature.equalsIgnoreCase("")) {
                        successFlag = "Please Check for Recno,TokenNo,SubTokenNo";
                        return successFlag;
                    }
                    if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || actNature.equalsIgnoreCase(CbsConstant.OF_AC)
                            || actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)
                            || actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || actNature.equalsIgnoreCase(CbsConstant.SS_AC) || actNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        List l5 = em.createNativeQuery("select acno from reconbalan where acno='" + acNo + "'").getResultList();
                        if (!l5.isEmpty()) {
                            List l6 = em.createNativeQuery("select clearedbalance from reconbalan where acno='" + acNo + "'").getResultList();
                            if (!l6.isEmpty()) {
                                Vector v6 = (Vector) l6.get(0);
                                balance = Double.parseDouble(v6.get(0).toString());
                            }
                        }
                    } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List l5 = em.createNativeQuery("select acno from ca_reconbalan where acno='" + acNo + "'").getResultList();
                        if (!l5.isEmpty()) {
                            List l6 = em.createNativeQuery("select clearedbalance from ca_reconbalan where acno='" + acNo + "'").getResultList();
                            if (!l6.isEmpty()) {
                                Vector v6 = (Vector) l6.get(0);
                                balance = Double.parseDouble(v6.get(0).toString());
                            }
                        }
                    } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        List l5 = em.createNativeQuery("select acno from td_reconbalan where acno='" + acNo + "'").getResultList();
                        if (!l5.isEmpty()) {
                            List l6 = em.createNativeQuery("select clearedbalance from td_reconbalan where acno='" + acNo + "'").getResultList();
                            if (!l6.isEmpty()) {
                                Vector v6 = (Vector) l6.get(0);
                                balance = Double.parseDouble(v6.get(0).toString());
                            }
                        }
                    }

                    List l3 = em.createNativeQuery("select ifnull(" + balance + ",0)").getResultList();
                    /**
                     * *end here**
                     */
                    if (!l3.isEmpty()) {
                        Vector v4 = (Vector) l3.get(0);
                        balance = Double.parseDouble(v4.get(0).toString());
                    }

                    int b = 0;
                    if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        Query q5 = em.createNativeQuery("insert into recon(acno,ty,dt,Dramt,CrAmt,Balance,TranType,details,iy,instno,EnterBy,Auth,recno,payby,"
                                + "Authby,trsno,Trantime,TranDesc,TokenNo,tokenPaidBy,SubTokenNo,org_brnid,dest_brnid,valuedt,instdt) select r.acno,r.ty,r.dt,"
                                + "r.Dramt,r.CrAmt," + balance + ",r.TranType,r.details,r.iy,r.instno,r.EnterBy,r.Auth,r.recno,r.payby,r.Authby,r.trsno,"
                                + "r.Trantime,r.TranDesc,r.TokenNo,r.tokenPaidBy,r.SubTokenNo,r.org_brnid,r.dest_brnid,r.valuedt,r.instdt from recon_cash_d r "
                                + "where r.recno=" + recno + " AND ORG_BRNID ='" + orgnBrCode + "'");
                        b = q5.executeUpdate();
                    } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        Query q5 = em.createNativeQuery("insert into ddstransaction (acno,ty,dt,Dramt,CrAmt,Balance,TranType,details,iy,InstNo,EnterBy,Auth,"
                                + "recno,payby,Authby,trsno,Trantime,TokenNo,SubTokenNo,org_brnid,dest_brnid,tran_id,term_id,trandesc,tokenpaid,favorof,"
                                + "CheckBy,RECEIPTNO,TOKENPAIDBY,valuedt,instdt) select r.acno,r.ty,r.dt,r.Dramt,r.CrAmt," + balance + ",r.TranType,r.details,"
                                + "r.iy,r.instno,r.EnterBy,r.Auth,r.recno,r.payby,r.Authby,r.trsno,r.Trantime,r.TokenNo,r.SubTokenNo,r.org_brnid,r.dest_brnid,"
                                + "r.tran_id,r.term_id,r.trandesc,'','','',r.INSTNO,r.TOKENPAIDBY,r.valuedt,r.instdt from recon_cash_d r where r.recno="
                                + recno + " AND ORG_BRNID ='" + orgnBrCode + "'");
                        b = q5.executeUpdate();
                    } else if (actNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        Query q5 = em.createNativeQuery(" insert into of_recon(acno,tdacno,ty,dt,Dramt,CrAmt,Balance,TranType,details,EnterBy, Auth, recno,"
                                + "Authby, trsno, Trantime, TranDesc, TokenNo,SubTokenNo,instno,payby,iy,Tokenpaidby,voucherno,intflag,closeflag,org_brnid,"
                                + "dest_brnid,valuedt,instdt) select r.acno,r.tdacno, r.ty, r.dt, r.Dramt, r.CrAmt," + balance + ",r.TranType , r.details,"
                                + "r.EnterBy, r.Auth, r.recno,r.Authby, r.trsno, r.Trantime, r.TranDesc, r.TokenNo, r.SubTokenNo,r.instno,r.payby,r.iy,"
                                + "r.Tokenpaidby,r.voucherno,r.intflag,r.closeflag,r.org_brnid,r.dest_brnid,r.valuedt,r.instdt from recon_cash_d r where "
                                + "r.recno=" + recno + " AND ORG_BRNID ='" + orgnBrCode + "'");
                        b = q5.executeUpdate();
                    } else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || actNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        Query q5 = em.createNativeQuery("insert into loan_recon(acno , ty, dt, Dramt, CrAmt, Balance, TranType, details, iy, instno,EnterBy, "
                                + "Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,TokenpaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt) "
                                + "select r.acno , r.ty, r.dt, r.Dramt, r.CrAmt," + balance + ",r.TranType , r.details, r.iy, r.instno, r.EnterBy, r.Auth, "
                                + "r.recno, r.payby,r.Authby, r.trsno, r.Trantime, r.TranDesc, r.TokenNo,r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid,"
                                + "r.valuedt,r.instdt from recon_cash_d r where r.recno=" + recno + " AND ORG_BRNID ='" + orgnBrCode + "'");
                        b = q5.executeUpdate();
                    } else if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        Query q5 = em.createNativeQuery("insert into gl_recon(acno , ty, dt, Dramt, CrAmt, Balance, TranType, details, iy, instno,EnterBy, "
                                + "Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt,"
                                + "adviceNo,adviceBrnCode) select r.acno , r.ty, r.dt, r.Dramt, r.CrAmt," + balance + ",r.TranType , r.details, r.iy, r.instno,"
                                + " r.EnterBy, r.Auth, r.recno, r.payby,r.Authby, r.trsno, r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,"
                                + "r.org_brnid,r.dest_brnid,r.valuedt,r.instdt,'','' from recon_cash_d r where r.recno=" + recno + " AND ORG_BRNID ='"
                                + orgnBrCode + "'");
                        b = q5.executeUpdate();
                    } else if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        Query q5 = em.createNativeQuery("insert into rdrecon(acno , ty, dt, Dramt, CrAmt, Balance, TranType, details, iy, instno,EnterBy, Auth,"
                                + " recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt) "
                                + "select r.acno , r.ty, r.dt, r.Dramt, r.CrAmt," + balance + ",r.TranType , r.details, r.iy, r.instno, r.EnterBy, r.Auth, "
                                + "r.recno, r.payby,r.Authby, r.trsno, r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid,"
                                + "r.valuedt,r.instdt from recon_cash_d r where r.recno=" + recno + " AND ORG_BRNID ='" + orgnBrCode + "'");
                        b = q5.executeUpdate();

                        /**
                         * **Addedby dinesh for contra***
                         */
                        /**
                         * ********Execute
                         * Loan_Disbursement_Installment******************
                         */
                        if (trandesc == 0 || trandesc == 9) {
                            O = fTSPosting43CBSBean.loanDisbursementInstallment(acNo, dramt, 1, authby, dt, recno, trandesc, remarks);
                            if (!O.equalsIgnoreCase("true")) {
                                return O;
                            }
                        }
                    } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        Query q5 = em.createNativeQuery("insert into ca_recon(acno,ty,dt,Dramt,CrAmt,Balance,TranType,details,iy,instno,EnterBy,Auth, recno,"
                                + "payby,Authby, trsno, Trantime, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt) select r.acno, "
                                + "r.ty, r.dt, r.Dramt, r.CrAmt, " + balance + ",r.TranType , r.details, r.iy, r.instno, r.EnterBy, r.Auth, r.recno, r.payby,"
                                + "r.Authby, r.trsno, r.Trantime, r.TranDesc, r.TokenNo, r.tokenPaidBy, r.SubTokenNo,r.org_brnid,r.dest_brnid,r.valuedt,r.instdt "
                                + "from recon_cash_d r where r.recno=" + recno + " AND ORG_BRNID ='" + orgnBrCode + "' ");
                        b = q5.executeUpdate();
                    } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        Query q5 = em.createNativeQuery("insert into td_recon(acno,ty,dt,Dramt,CrAmt,Balance,TranType,details,EnterBy, Auth, recno,Authby, trsno,"
                                + " Trantime, TranDesc, TokenNo, SubTokenNo,instno,payby,iy,tokenPaidBy,VoucherNo,IntFlag,CloseFlag,org_brnid,dest_brnid,valuedt,"
                                + "instdt) select r.acno , r.ty, r.dt, r.Dramt, r.CrAmt," + balance + ",r.TranType , r.details, r.EnterBy, r.Auth, r.recno,"
                                + "r.Authby, r.trsno, r.Trantime, r.TranDesc, r.TokenNo, r.SubTokenNo,r.instno,r.payby,r.iy ,r.tokenPaidBy,r.VoucherNo,r.IntFlag,"
                                + "r.CloseFlag,r.org_brnid,r.dest_brnid,r.valuedt,r.instdt from recon_cash_d r where r.recno=" + recno + " AND ORG_BRNID ='"
                                + orgnBrCode + "'");
                        b = q5.executeUpdate();
                    } /**
                     * **actNature for RD is added by dinesh**
                     */
                    else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        /**
                         * ********Execute
                         * Loan_Disbursement_Installment******************
                         */
                        O = fTSPosting43CBSBean.loanDisbursementInstallment(acNo, dramt, 1, authby, dt, recno, trandesc, remarks);
                        if (!O.equalsIgnoreCase("true")) {
                            return O;
                        }
                    }

                    if (b <= 0) {
                        successFlag = "Insertion Problem In Recon";
                        return successFlag;
                    }
                    List listfyear = em.createNativeQuery("select f_year from yearend where yearendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
                    Vector elementFyear = (Vector) listfyear.get(0);
                    fyear = Integer.parseInt(elementFyear.get(0).toString());

                    if (iy == 11 && trandesc != 9) {
                        List l7 = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear=extract(year from '" + dt + "')").getResultList();
                        Vector v5 = (Vector) l7.get(0);
                        seqNo = Float.parseFloat(v5.get(0).toString());

                        Query q7 = em.createNativeQuery("Insert into bill_sundry(Fyear,AcNo,amount,enterby,dt,status,auth,trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + dt + "'),'" + acNo + "',ifnull(" + dramt + ",0),'" + enterBy + "','" + dt + "','ISSUED','Y',0," + seqNo + ",1," + recno + ",'" + dt + "','" + authby + "')");
                        q7.executeUpdate();
                    }

                    if (iy == 12 && trandesc != 9) {
                        List l7 = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_suspense where Fyear=extract(year from '" + dt + "')").getResultList();
                        Vector v5 = (Vector) l7.get(0);
                        seqNo = Float.parseFloat(v5.get(0).toString());

                        Query q7 = em.createNativeQuery("Insert into bill_suspense(Fyear,AcNo,amount,enterby,dt,status,auth,trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + dt + "'),'" + acNo + "',ifnull(" + dramt + ",0),'" + enterBy + "','" + dt + "','ISSUED','Y',0," + seqNo + ",1," + recno + ",'" + dt + "','" + authby + "')");
                        q7.executeUpdate();

                    }

                    if (iy == 13 && trandesc != 9) {
                        List l7 = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_clgadjustment where acno='" + acNo + "' and Fyear=extract(year from '" + dt + "')").getResultList();
                        Vector v5 = (Vector) l7.get(0);
                        seqNo = Float.parseFloat(v5.get(0).toString());

                        Query q7 = em.createNativeQuery("Insert into bill_clgadjustment(Fyear,AcNo,amount,enterby,dt,status,auth,trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + dt + "'),'" + acNo + "',ifnull(" + dramt + ",0),'" + enterBy + "','" + dt + "','ISSUED','Y',0," + seqNo + ",1," + recno + ",'" + dt + "','" + authby + "')");
                        q7.executeUpdate();

                    }
                    /* Code by Dhirendra Singh for TDS Deduction of cash withdrawal above 1 cr*/
                    if (!actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        //BigDecimal tdsToBeDeducted = interBranchFacade.getTdsToBeDeductedForCustomer(acNo, "20190901", dt, new BigDecimal(0),"Y");
                        if (tdsToBeDeducted.compareTo(new BigDecimal(0)) > 0) {
                            String msg1 = fTSPosting43CBSBean.chkBal(acNo, 1, trandesc, actNature);
                            if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                                String msg = fTSPosting43CBSBean.checkBalance(acNo, tdsToBeDeducted.doubleValue(), enterBy);
                                if (!(msg.equalsIgnoreCase("TRUE"))) {
                                    throw new ApplicationException(msg + " for A/C No. " + acNo);
                                }
                            }
                            //To do Transfer Transaction for TDS Deduction
                            interBranchFacade.tdsDeductionForCashWithdrawal(actNature, acNo, tdsToBeDeducted.doubleValue(), authby, orgnBrCode);
                        }
                    }
                    successFlag = "YES" + fyear + seqNo;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return successFlag;

    }

    public String CbsAuthCashDrDeletion(float recNo, String instNo, String acNo, double amount, String dt,
            String custName, String subTokenNo, float tokenNo, String originDt, String enterBy,
            String authBy, String orgCode) throws ApplicationException {
        String acNature, result, msg = "";
        int postFlag;
        double crAmt;
        double drAmt;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int a;
            List rsList = em.createNativeQuery("select acno from tokentable_debit WHERE ACNO='" + acNo + "'  AND DT='" + dt + "' "
                    + "AND RECNO=" + recNo + " AND ORG_BRNID='" + orgCode + "' and auth = 'Y'").getResultList();
            if (rsList.size() > 0) {
                ut.rollback();
                msg = "This transaction is already authorized";
                return msg;
            }
            Query q1 = em.createNativeQuery("DELETE FROM tokentable_debit WHERE ACNO='" + acNo + "'  AND DT='" + dt + "' AND RECNO=" + recNo + " AND ORG_BRNID='" + orgCode + "'");
            a = q1.executeUpdate();
            if (a <= 0) {
                ut.rollback();
                msg = "Problem Occured in Dr Deletion";
                return msg;
            }
            acNature = fTSPosting43CBSBean.getAccountNature(acNo);
            Query q2 = em.createNativeQuery("Insert into deletetrans(acno,cramt,dramt,enterby,deletedby,deletedt,authby,custname,subtokenno,tokenno,"
                    + "trantype,trantime,org_brnid,dest_brnId) values ('" + acNo + "', 0," + amount + ",'" + enterBy + "','" + authBy + "', '" + originDt + "','','"
                    + custName + "','" + subTokenNo + "'," + tokenNo + ",0,CURRENT_TIMESTAMP(),'" + orgCode + "','" + acNo.substring(0, 2) + "')");
            a = q2.executeUpdate();
            if (a <= 0) {
                ut.rollback();
                msg = "Problem Occured In Dr Deletion";
                return msg;
            }
            crAmt = 0;
            drAmt = (-1) * amount;
            result = fTSPosting43CBSBean.updateBalance(acNature, acNo, crAmt, drAmt, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                ut.rollback();
                return result;
            }

            if (!instNo.equalsIgnoreCase("")) {
                int chequeNo = Integer.parseInt(instNo);
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    Query q3 = em.createNativeQuery(" update chbook_sb set statusflag='F',lastupdateby='" + authBy + "',lastupdatedt='" + dt + "' where acno='" + acNo + "' and chqno='" + instNo + "' ");
                    a = q3.executeUpdate();
                    if (a <= 0) {
                        ut.rollback();
                        msg = "Problem Occured In SB Account Dr Deletion";
                        return msg;
                    }
//                    Query q4 = em.createNativeQuery("Delete from  reconusedcheque where acno='" + acNo + "' and instno='" + chequeNo + "'");
//                    a = q4.executeUpdate();
//                    if (a <= 0) {
//                        ut.rollback();
//                        msg = "Problem Occured In SB Account Dr Deletion";
//                        return msg;
//                    }

                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    Query q3 = em.createNativeQuery(" update chbook_ca set statusflag='F',lastupdateby='" + authBy + "',lastupdatedt='" + dt + "' where acno='" + acNo + "' and chqno='" + instNo + "' ");
                    a = q3.executeUpdate();
                    if (a <= 0) {
                        ut.rollback();
                        msg = "Problem Occured In CA Account Dr Deletion";
                        return msg;
                    }
//                    Query q4 = em.createNativeQuery("Delete from  reconusedcheque where acno='" + acNo + "' and instno='" + chequeNo + "'");
//                    a = q4.executeUpdate();
//                    if (a <= 0) {
//                        ut.rollback();
//                        msg = "Problem Occured In CA Account Dr Deletion";
//                        return msg;
//                    }

                }
//                else if (acNature.equalsIgnoreCase("CC")) {
//                    Query q3 = em.createNativeQuery(" update chbook_cc set statusflag='F',lastupdateby='" + authBy + "',lastupdatedt='" + dt + "' where acno='" + acNo + "' and chqno='" + instNo + "' ");
//                    a = q3.executeUpdate();
//                    if (a <= 0) {
//                        ut.rollback();
//                        msg = "Problem Occured In CC Account Dr Deletion";
//                        return msg;
//                    }
//                    Query q4 = em.createNativeQuery("Delete from  reconusedcheque where acno='" + acNo + "' and instno='" + chequeNo + "'");
//                    a = q4.executeUpdate();
//                    if (a <= 0) {
//                        ut.rollback();
//                        msg = "Problem Occured In CC Account Dr Deletion";
//                        return msg;
//                    }
//
//                }
            }
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List l5 = em.createNativeQuery("select postflag from gltable where acno ='" + acNo + "'").getResultList();
                Vector v3 = (Vector) l5.get(0);
                postFlag = Integer.parseInt(v3.get(0).toString());
                if (postFlag == 11) {
                    Query q3 = em.createNativeQuery("update bill_sundry set status = 'Issued',dt='" + originDt + "' ,lastupdateby=null where dt ='" + dt + "' and SUBSTRING(acno,1,2) = '" + orgCode + "' and recno =" + recNo + "");
                    a = q3.executeUpdate();
                    if (a <= 0) {
                        ut.rollback();
                        msg = "Problem Occured In GL Account Dr Deletion";
                        return msg;
                    }
                }
            }
            //}
            List isAlertList = em.createNativeQuery("select * from cbs_str_detail where acno = '" + acNo + "' and dt= '" + dt + "' and recno = " + recNo + " and auth_status = 'N'").getResultList();
            if (!isAlertList.isEmpty()) {
                String strResult = fTSPosting43CBSBean.strDetailDeletion(acNo, new Float(0), recNo, dt, enterBy);
                if (!strResult.equalsIgnoreCase("true")) {
                    ut.rollback();
                    throw new ApplicationException("Deletion problem in cbs_str_detail");
                }
            }
            //Denomination deletion 16/11/2015
            String denoMessage = interBranchFacade.deleteDenomination(recNo, dt);
            if (!denoMessage.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(denoMessage);
            }
            //End
            msg = "Entry Deleted Successfully.";
            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }

    public String cbsCashDrDestAuth(float recno, String authBy, String enterBy, String orgCode, BigDecimal tdsToBeDeducted)
            throws ApplicationException {
        String msg = null, acno = null, custName = null, dt = null, instNo = null, operMode = null, auth = null, tokenPaidBy = null,
                subTokenNo = null, tranTime = null, details = null, intFlag = null, closeFlag = null, tdAcno = null, tokenPaid = null,
                termId = null, orgBrnid = null, destBrnid = null, valuedt = null, instrdt = null;
        double drAmt = 0.0d, crAmt = 0.0d;
        float recno1 = 0.0f, voucherNo = 0.0f, tokenNo = 0.0f;
        Integer ty = 0, tranType = 0, trsno = 0, iy = 0, tranDesc = 0, tranId = 0, payBy = 0;
        String cashmod = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            if (!String.valueOf(recno).equalsIgnoreCase("")) {
                List list = em.createNativeQuery("SELECT ACNO,CUSTNAME,DATE_FORMAT(DT,'%Y%m%d'),DRAMT,CRAMT,TY,TRANTYPE,RECNO, "
                        + "TRSNO, INSTNO, CAST(PAYBY AS unsigned), IY,COALESCE( OPERMODE,''),AUTH,TOKENPAIDBY,TRANDESC,TOKENNO, "
                        + "SUBTOKENNO,DATE_FORMAT(TRANTIME,'%Y%m%d'),details,COALESCE(VOUCHERNO,''),COALESCE(INTFLAG,''),"
                        + "COALESCE(CLOSEFLAG,''),COALESCE(TDACNO,''),TOKENPAID,TRAN_ID,TERM_ID,ORG_BRNID,DEST_BRNID,"
                        + "DATE_FORMAT(VALUEDT,'%Y%m%d'),DATE_FORMAT(INSTDT,'%Y%m%d') FROM tokentable_debit WHERE "
                        + "RECNO IN(" + recno + ") AND ORG_BRNID = '" + orgCode + "'").getResultList();
                Vector element = (Vector) list.get(0);
                acno = element.get(0).toString();
                custName = element.get(1).toString();
                dt = element.get(2).toString();
                drAmt = Double.parseDouble(element.get(3).toString());
                crAmt = Double.parseDouble(element.get(4).toString());

                ty = Integer.parseInt(element.get(5).toString());
                tranType = Integer.parseInt(element.get(6).toString());
                recno1 = Float.parseFloat(element.get(7).toString());
                trsno = Integer.parseInt(element.get(8).toString());
                instNo = element.get(9).toString();

                payBy = Integer.parseInt(element.get(10).toString());
                iy = Integer.parseInt(element.get(11).toString());
                operMode = element.get(12).toString();
                auth = element.get(13).toString();
                tokenPaidBy = element.get(14).toString();

                tranDesc = Integer.parseInt(element.get(15).toString());
                tokenNo = Float.parseFloat(element.get(16).toString());
                subTokenNo = element.get(17).toString();
                tranTime = element.get(18).toString();
                details = element.get(19).toString();
                //voucherNo = element.get(20).toString().equals("") ? 0f : Float.parseFloat(element.get(20).toString());
                intFlag = element.get(21).toString();

                closeFlag = element.get(22).toString();
                tdAcno = element.get(23).toString();
                tokenPaid = element.get(24).toString();

                tranId = Integer.parseInt(element.get(25).toString());
                termId = element.get(26).toString();
                orgBrnid = element.get(27).toString();
                destBrnid = element.get(28).toString();
                valuedt = element.get(29).toString();
                instrdt = element.get(30).toString();

                List l1 = em.createNativeQuery("SELECT CASHMOD FROM parameterinfo WHERE "
                        + "BRNCODE = " + Integer.parseInt(orgCode) + "").getResultList();
                Vector v1 = (Vector) l1.get(0);
                cashmod = v1.get(0).toString();

                ut.begin();
                float trsno1 = fTSPosting43CBSBean.getTrsNo();
                String actNature = fTSPosting43CBSBean.getAccountNature(acno);
                if (cashmod.equalsIgnoreCase("N")) {
                    Query q1 = em.createNativeQuery("UPDATE tokentable_debit SET AUTH='Y', AUTHBY='" + authBy + "', TRSNO=" + trsno + " WHERE RECNO IN (" + recno + ") AND ORG_BRNID = '" + orgCode + "'");
                    int a = q1.executeUpdate();
                    if (a <= 0) {
                        throw new ApplicationException("Problem in updating tokentable_debit");
                    }
                    msg = interBranchFacade.cbsPostingSx(acno, ty, valuedt, drAmt, 0f, tranType, details, tokenNo, subTokenNo, instNo, instrdt, payBy, voucherNo, recno1, tranDesc, destBrnid, orgBrnid, enterBy, authBy, trsno1, "", "");
                    System.out.println("msg cash withdrawl:=======" + msg);
                    if (!msg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                    msg = interBranchFacade.cbsPostingCx(acno, ty, valuedt, drAmt, 0f, tranType, details, tokenNo, subTokenNo, instNo, instrdt, payBy, voucherNo, recno1, tranDesc, destBrnid, orgBrnid, enterBy, authBy, trsno1, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(msg);
                    }
                    if (!actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                        BigDecimal tdsToBeDeducted = interBranchFacade.getTdsToBeDeductedForCustomer(acno, "20190901", dt, new BigDecimal(0),"Y");
                        if (tdsToBeDeducted.compareTo(new BigDecimal(0)) > 0) {
                            String msg1 = fTSPosting43CBSBean.chkBal(acno, 1, tranDesc, actNature);
                            if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                                msg = fTSPosting43CBSBean.checkBalance(acno, tdsToBeDeducted.doubleValue(), enterBy);
                                if (!(msg.equalsIgnoreCase("TRUE"))) {
                                    throw new ApplicationException(msg + " for A/C No. " + acno);
                                }
                            }
                            //To do Transfer Transaction for TDS Deduction
                            interBranchFacade.tdsDeductionForCashWithdrawal(actNature, acno, tdsToBeDeducted.doubleValue(), authBy, orgBrnid);
                        }
                    }
                } else {
                    Query q2 = em.createNativeQuery("UPDATE tokentable_debit SET AUTH='Y', AUTHBY='" + authBy + "' WHERE RECNO IN (" + recno + ") AND ORG_BRNID = '" + orgCode + "'");
                    int a = q2.executeUpdate();
                    if (a <= 0) {
                        throw new ApplicationException("Problem in updating tokentable_debit");
                    }
                    Query q3 = em.createNativeQuery("insert into recon_cash_d(acno ,custname, ty, dt, Dramt, CrAmt, TranType, details, iy, instno, EnterBy, Auth, recno,payby,Authby, trsno, Trantime, TranDesc, TokenNo, tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt)"
                            + " select acno ,custname, ty, dt, Dramt, CrAmt, TranType, details, iy, instno, EnterBy, 'Y', recno,payby,authby, trsno, Trantime, TranDesc, TokenNo, tokenPaidBy, SubTokenNo,org_brnid,dest_brnid,valuedt,instdt from tokentable_debit where recno in (" + recno + ") AND ORG_BRNID ='" + orgCode + "'");
                    int var = q3.executeUpdate();
                    System.out.println("var:=======" + var);
                    if (var <= 0) {
                        throw new ApplicationException("Insertion failed in Recon Cash D !!!");
                    }
                    msg = "TRUE";
                }
                //Deaf updation
                fTSPosting43CBSBean.lastTxnDateUpdation(acno);
                //Deaf updation end here
                ut.commit();
                try {
                    //Sms Sending.
                    if (cashmod.equalsIgnoreCase("N")) {
                        String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                                == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                        if (templateType.equalsIgnoreCase("indr")) {
                            List detailsQuery = em.createNativeQuery("select Details from tokentable_debit where recno='" + recno + "' and org_brnid='" + orgCode + "'").getResultList();
                            Vector det = (Vector) detailsQuery.get(0);
                            String detail = det.get(0).toString();
                            String details1 = "";
                            if (details.length() <= 30) {
                                details1 = details;
                            } else if (details.length() > 30) {
                                details1 = details.substring(1, 30);
                            }
                            smsFacade.sendTransactionalSms(SmsType.CASH_WITHDRAWAL_INDR, acno, 0, 1, drAmt,
                                    dmyOne.format(ymd.parse(dt)), details1);
                        } else {
                            smsFacade.sendTransactionalSms(SmsType.CASH_WITHDRAWAL, acno, 0, 1, drAmt,
                                    dmyOne.format(ymd.parse(dt)), "");
                        }
//              smsFacade.sendTransactionalSms(SmsType.CASH_WITHDRAWAL, acno, 0, 1,
//                     drAmt, dmyOne.format(ymd.parse(dt)));
                        List checklist = em.createNativeQuery("select iy,dramt from tokentable_debit "
                                + "where recno=" + recno + " and "
                                + "org_brnid ='" + orgCode + "'").getResultList();
                        Vector checkEle = (Vector) checklist.get(0);
                        int exceedIy = Integer.parseInt(checkEle.get(0).toString());
                        double amt = Double.parseDouble(checkEle.get(1).toString());
                        if (exceedIy == 99) {
                            sendCCODExceedSms(acno, amt);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error SMS Sending-->A/c is::" + acno + " And Amount is::" + drAmt);
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }

    public String getSignatureDetails(String acno) {
        List signature = em.createNativeQuery("select image from cbs_cust_image_detail where newacno='" + acno + "' and auth='Y'").getResultList();
        if (!signature.isEmpty()) {
            Vector img = (Vector) signature.get(0);
            String image = img.get(0).toString();
            return image;
        } else {
            return "Signature not found";
        }
    }

    public List getDataForSignature(String accNo) throws ApplicationException {
        List result = new ArrayList();
        try {
            String acctNature;
            acctNature = fTSPosting43CBSBean.getAccountNature(accNo);
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                result = em.createNativeQuery("Select ifnull(jtname1,''),ifnull(jtname2,''),ifnull(jtname3,''),ifnull(jtname4,''),ifnull(instruction,''),"
                        + "ifnull(opermode,''),DATE_FORMAT(openingdt,'%Y%m%d') 'openingdt',ifnull(nomination,''), 0, custname from td_accountmaster"
                        + " where AcNo='" + accNo + "'").getResultList();
            } else {
                result = em.createNativeQuery("Select ifnull(jtname1,''),ifnull(jtname2,''),ifnull(jtname3,''),ifnull(jtname4,''),ifnull(instruction,''),"
                        + " ifnull(opermode,''),DATE_FORMAT(openingdt,'%Y%m%d') 'openingdt',ifnull(nomination,''), ifnull(odlimit,0), custname"
                        + " from accountmaster where AcNo='" + accNo + "'").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List selectForOperationMode(String opmode) {
        List list = em.createNativeQuery("select ifnull(description,'') from codebook where groupcode=4 and code=" + opmode + " ").getResultList();
        return list;
    }

    public List getUserAuthorizationLimitCash(String userName, String orgBrnCode) {
        List userAuthLimit = em.createNativeQuery("SELECT COALESCE(TOCASHLIMIT,0.0) FROM securityinfo WHERE USERID='" + userName + "' AND BRNCODE='" + orgBrnCode + "'").getResultList();
        return userAuthLimit;
    }

    public List getUserAuthorizationLimitClearing(String userName, String orgBrnCode) {
        List userAuthLimit = em.createNativeQuery("SELECT COALESCE(CLGDEBIT,0.0) FROM securityinfo WHERE USERID='" + userName + "' AND BRNCODE='" + orgBrnCode + "'").getResultList();
        return userAuthLimit;
    }
    /*                       End of Cash Debit Authorization         */

    /*                       Start of Clearing Authorization         */
    public List tableAuthorize(String date, int ty, String orgnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select r.acno,r.custname,r.cramt,r.dramt,ifnull(ifnull(b1.balance,b2.balance),b3.balance) as balance, ifnull(r.instno,'') as instno,"
                    + "r.enterby,r.auth,r.tokenno as tokenno, ifnull(r.subtokenno,'') as subtokenno,r.recno,r.details as details,date_format(r.dt,'%d/%m/%Y'),date_format(r.valuedt,'%d/%m/%Y'),"
                    + "date_format(r.instdt,'%d/%m/%Y'),iy,trandesc from recon_clg_d r left join reconbalan b1 on r.acno=b1.acno "
                    + "left join ca_reconbalan b2 on r.acno=b2.acno left join td_reconbalan b3 on r.acno=b3.acno where r.dt='" + date
                    + "' and r.trantype=1 and r.ty= " + ty + " AND r.Trandesc!=64 and AUTH='N' and r.screenflag='T' and org_brnid='" + orgnCode + "' order by r.recno");

            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public String cbsAuthDeletion(float recNo, String instNo, String acNo, double CrAmt, double DrAmt, double Amount, String dt, String custName, String subtokenno,
            float tokenno, String originDt, String enterby, String deleteby, int tranType, String brCode, String option)
            throws ApplicationException {
        try {
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            String acctNat;
            String poFlag = "";
            String dateVal;
            String tableName;
            double balance = 0;
            double newBalance = 0;
            String dtVal = "";

            Query delQuery = em.createNativeQuery("DELETE FROM recon_clg_d WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND RECNO=" + recNo + " AND org_brnid ='" + brCode + "'");
            int var1 = delQuery.executeUpdate();
            if (var1 > 0) {
                acctNat = fTSPosting43CBSBean.getAccountNature(acNo);

                List acctNat1 = em.createNativeQuery("select date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d')").getResultList();
                Vector acctNatVec1 = (Vector) acctNat1.get(0);
                dateVal = acctNatVec1.get(0).toString();

                // this code added By Zeeshan //
                if (option.equalsIgnoreCase("INWARD")) {
                    Amount = (-DrAmt);
                } else {
                    Amount = (-CrAmt);

                }
                if (option.equalsIgnoreCase("INWARD")) {
                    if (acctNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List bal = em.createNativeQuery("select acno,ifnull(balance,0),dt from ca_reconbalan where acno = '" + acNo
                                + "'").getResultList();
                        if (bal.size() > 0) {
                            Vector balVec = (Vector) bal.get(0);
                            balance = Double.parseDouble(balVec.get(1).toString());
                            dtVal = balVec.get(2).toString();
                            newBalance = balance - Amount;
                        } else {
                            newBalance = -(Amount);
                        }

                    } else if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNat.equalsIgnoreCase(CbsConstant.MS_AC)
                            || acctNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        List bal = em.createNativeQuery("select acno,ifnull(balance,0),dt from td_reconbalan where acno = '" + acNo
                                + "'").getResultList();
                        if (bal.size() > 0) {
                            Vector balVec = (Vector) bal.get(0);
                            balance = Double.parseDouble(balVec.get(1).toString());
                            dtVal = balVec.get(2).toString();
                            newBalance = balance - Amount;
                        } else {
                            newBalance = -(Amount);

                        }
                    } else {
                        List bal = em.createNativeQuery("select acno,ifnull(balance,0),dt from reconbalan where acno = '" + acNo
                                + "'").getResultList();
                        if (bal.size() > 0) {
                            Vector balVec = (Vector) bal.get(0);
                            balance = Double.parseDouble(balVec.get(1).toString());
                            dtVal = balVec.get(2).toString();
                            newBalance = balance - Amount;

                        } else {
                            newBalance = -(Amount);
                        }
                    }

                    balance = newBalance;
                    dtVal = dt;
                    if (acctNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        Query updatechbook = em.createNativeQuery("update ca_reconbalan set Balance=" + balance + ", "
                                + "dt=date_format(STR_TO_DATE('" + dtVal + "', '%d/%m/%Y'),'%Y%m%d') where acno='" + acNo + "'");
                        int var3 = updatechbook.executeUpdate();
                        if (!(var3 > 0)) {
                            ut.rollback();
                            return "Updation problem in ca_reconbalan";
                        }

                    } else if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNat.equalsIgnoreCase(CbsConstant.MS_AC) || acctNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        Query updatechbook = em.createNativeQuery("update td_reconbalan  set Balance=" + balance + ", "
                                + "dt=date_format(STR_TO_DATE('" + dtVal + "', '%d/%m/%Y'),'%Y%m%d') where acno='" + acNo + "'");
                        int var3 = updatechbook.executeUpdate();
                        if (!(var3 > 0)) {
                            ut.rollback();
                            return "Updation problem in td_reconbalan";
                        }
                    } else {
                        Query updatechbook = em.createNativeQuery("update reconbalan set Balance=" + balance + ", "
                                + "dt=date_format(STR_TO_DATE('" + dtVal + "', '%d/%m/%Y'),'%Y%m%d') where acno='" + acNo + "'");
                        int var3 = updatechbook.executeUpdate();
                        if (!(var3 > 0)) {
                            ut.rollback();
                            return "Updation problem in reconbalan";
                        }
                    }
                }
                if (!instNo.equalsIgnoreCase("") && option.equalsIgnoreCase("INWARD")) {
                    if (acctNat.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                            || acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        tableName = "chbook_" + acctNat.toLowerCase();
                        Query updatechbook = em.createNativeQuery("update " + tableName + " set statusflag='F',lastupdateby='" + deleteby
                                + "',lastupdatedt=date_format(STR_TO_DATE('" + originDt + "', '%d/%m/%Y'),'%Y%m%d') where acno='" + acNo + "' and chqno='" + instNo + "'");
                        updatechbook.executeUpdate();

//                        Query ReconUsedCheque = em.createNativeQuery("Delete from  reconusedcheque where acno='" + acNo + "' and instno='" + instNo + "'");
//                        ReconUsedCheque.executeUpdate();
                    }
                }
                Query insertQuery1 = em.createNativeQuery("Insert into deletetrans(acno,cramt,dramt,enterby,deletedby,deletedt,authby,custname,"
                        + "subtokenno,tokenno,trantype,trantime,org_brnId,dest_brnId)"
                        + "values ('" + acNo + "'," + CrAmt + "," + DrAmt + ",'" + enterby + "','" + deleteby + "','" + dateVal + "','','"
                        + custName + "','" + subtokenno + "'," + tokenno + "," + tranType + ",CURRENT_TIMESTAMP(),'" + brCode + "','"
                        + acNo.substring(0, 2) + "')");
                int var2 = insertQuery1.executeUpdate();
                if (var2 > 0) {
                    //   if (acNo.substring(2, 4).equalsIgnoreCase("GL")) {
                    if (fTSPosting43CBSBean.getAccountCode(acNo).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                        List billData = em.createNativeQuery("select glhead from billtypemaster where glhead = SUBSTRING('" + acNo + "',3,8)").getResultList();
                        if (!(billData.isEmpty())) {
                            poFlag = "True";
                        } else {
                            poFlag = "False";
                        }
                    }
                    if (poFlag.equalsIgnoreCase("True")) {
                        if (option.equalsIgnoreCase("INWARD")) {
                            if (acctNat.equalsIgnoreCase("DD")) {
                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_dd WHERE ACNO='" + acNo
                                        + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode
                                        + "' AND RECNO=" + recNo + "");
                                delBillQuery.executeUpdate();
                            } else if (acctNat.equalsIgnoreCase("PO")) {
                                Query updatechbook = em.createNativeQuery("update bill_po set status = 'Issued' ,dt = date_format(STR_TO_DATE('" + originDt + "', '%d/%m/%Y'),'%Y%m%d') where dt = date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' and  recno = " + recNo + "");
                                updatechbook.executeUpdate();
                            } else if (acctNat.equalsIgnoreCase("TPO")) {
                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_tpo WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                delBillQuery.executeUpdate();
                            } else {
                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_ad WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                int var4 = delBillQuery.executeUpdate();
                                if (var4 > 0) {
                                    Query delBillOthQuery = em.createNativeQuery("DELETE FROM bill_hoothers WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                    delBillOthQuery.executeUpdate();
                                } else {
                                    ut.rollback();
                                    return "Deletion problem in bill_dd";
                                }
                            }
                        } else {
                            if (acctNat.equalsIgnoreCase("DD")) {
                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_dd WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                delBillQuery.executeUpdate();
                            } else if (acctNat.equalsIgnoreCase("PO")) {

                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_po WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                delBillQuery.executeUpdate();

                            } else if (acctNat.equalsIgnoreCase("TPO")) {
                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_tpo WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                delBillQuery.executeUpdate();

                            } else {
                                Query delBillQuery = em.createNativeQuery("DELETE FROM bill_ad WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                int var4 = delBillQuery.executeUpdate();
                                if (var4 > 0) {
                                    Query delBillOthQuery = em.createNativeQuery("DELETE FROM bill_hoothers WHERE ACNO='" + acNo + "' AND DT=date_format(STR_TO_DATE('" + dt + "', '%d/%m/%Y'),'%Y%m%d') AND SUBSTRING(ACNO,1,2) = '" + brCode + "' AND RECNO=" + recNo + "");
                                    delBillOthQuery.executeUpdate();
                                } else {
                                    ut.rollback();
                                    return "Deletion problem in bill_dd";
                                }
                            }
                        }
                    }
                } else {
                    ut.rollback();
                    return "Deletion problem from recon_trf_d";
                }
            }
            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "True";
    }

    public String cbsAuthCleringDrCr(float recno, String Auth1, int MsgNo, int Msg_Bill_Start, String Status,
            int Msg_Bill_PO, int Msg_Bill_End, String date, float SeqNo, String instno, String BillType,
            String Acno, String custname, String PAYABLEAT, double Amount, String dt, String ORIGINDT,
            int TimeLimit, double comm, int TranType, int ty, String InFavourOf, String enterby, String LastUpdateBy,
            String option, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (Float.toString(recno).equals("")) {
                throw new ApplicationException("You have not selected any Record for Authorization");
            }
            float trsNo = fTSPosting43CBSBean.getTrsNo();
            Query clgQuery = em.createNativeQuery("Update recon_clg_d set auth = 'Y',authby = '" + Auth1 + "',trsNo= " + trsNo
                    + " where recno = " + recno + " And auth = 'N' and org_brnid ='" + brCode + "'");
            int var3 = clgQuery.executeUpdate();
            if (var3 <= 0) {
                throw new ApplicationException("Data Has been Authorised Already By Other Person");
            }
            if (MsgNo > 0) {
                /*Start of code added by Dhirendra Singh for duplicate po authorization*/
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String format = "";
                if (!ORIGINDT.isEmpty()) {
                    format = ymd.format(sdf.parse(ORIGINDT));
                }
                List checkList = em.createNativeQuery("select status from bill_po where seqno=" + SeqNo + " and INSTNO ='" + instno + "' and BILLTYPE ='" + BillType + "' and acno = '" + Acno + "' and ORIGINDT = '" + format + "'").getResultList();
                if (!checkList.isEmpty()) {
                    Vector element = (Vector) checkList.get(0);
                    String checkStatus = (String) element.get(0);
                    if (checkStatus.equalsIgnoreCase("PAID")) {
                        throw new ApplicationException("This instrument no. has been already paid.");
                    }
                }
                /*End of code added by Dhirendra Singh for duplicate po authorization*/
                String msgMakeEntry = fTSPosting43CBSBean.cbsAuthCashCrMakeEntry(MsgNo, Msg_Bill_Start, Status, Msg_Bill_PO, Msg_Bill_End, Auth1,
                        date, SeqNo, instno, BillType, Acno, custname, PAYABLEAT, Amount, dt, ORIGINDT, TimeLimit, comm, TranType, ty,
                        InFavourOf, enterby, LastUpdateBy, recno, brCode);

                String flag = msgMakeEntry.substring(0, 4);
                if (!(flag.equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException("Error In Data Insertion ");
                }
            }
            String sflag = authClg(trsNo, option, brCode);
            if (!sflag.equalsIgnoreCase("YES")) {
                throw new ApplicationException(sflag);
            }
            ut.commit();
            //Sending Sms
            if (!fTSPosting43CBSBean.getAccountNature(Acno).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                try {
                    List drList = em.createNativeQuery("select ifnull(instno,'') as instno,ty,iy from recon_clg_d "
                            + "where recno = " + recno + " and org_brnid ='" + brCode + "'").getResultList();
                    Vector drVec = (Vector) drList.get(0);
                    String drInstNo = drVec.get(0).toString();
                    Integer checkTy = Integer.parseInt(drVec.get(1).toString());
                    Integer checkIy = Integer.parseInt(drVec.get(2).toString());
                    if (drInstNo.equals("")) {
                        smsFacade.sendTransactionalSms(SmsType.MANUAL_CLEARING_WITHDRAWAL, Acno, 1, 1, Amount, dt, "");
                    } else {
                        TransferSmsRequestTo trfSmsRequestTo = new TransferSmsRequestTo();
                        trfSmsRequestTo.setMsgType("T");
                        trfSmsRequestTo.setTemplate(SmsType.CLEARING_WITHDRAWAL);
                        trfSmsRequestTo.setAcno(Acno);
                        trfSmsRequestTo.setTranType(1);
                        trfSmsRequestTo.setTy(1);
                        trfSmsRequestTo.setAmount(Amount);
                        trfSmsRequestTo.setDate(dt);
                        trfSmsRequestTo.setFirstCheque(drInstNo);
                        smsFacade.sendSms(trfSmsRequestTo);
                    }
                    //Sending Exceed SMS
                    if (checkTy == 1 && checkIy == 99) {
                        sendCCODExceedSms(Acno, Amount);
                    }
                    //End Here
                } catch (Exception ex) {
                    System.out.println("Error SMS Sending-->A/c is::" + Acno + " And Amount is::" + Amount);
                }
            }
            //End here.
            return "Authorization over";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String authClg(float trsNo, String option, String brCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select r.acno,ifnull(r.dramt,0),ifnull(r.cramt,0),r.recno,a.acctNature,r.ty,r.authby ,"
                    + "DATE_FORMAT(r.dt,'%Y%m%d'),r.trandesc,r.details,r.iy,r.enterby,r.trantype,r.trantime,cast(r.payby as unsigned),r.instno,"
                    + "r.voucherno,r.intflag,r.closeflag,DATE_FORMAT(r.valuedt,'%Y%m%d'),DATE_FORMAT(r.instdt,'%Y%m%d') from recon_clg_d r "
                    + "join accounttypemaster a on substring(r.acno,3,2)=a.AcctCode  and r.screenflag='T' "
                    + "where r.trsno=" + trsNo + "").getResultList();

            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }

            Vector element = (Vector) list.get(0);
            String acno = element.get(0).toString();
            double drAmt = Double.parseDouble(element.get(1).toString());
            double crAmt = Double.parseDouble(element.get(2).toString());
            float recno = Float.parseFloat(element.get(3).toString());

            String actNature = element.get(4).toString();
            int ty = Integer.parseInt(element.get(5).toString());
            String authBy = element.get(6).toString();
            String dt = element.get(7).toString();
            int tranDesc = Integer.parseInt(element.get(8).toString());

            String remarks = element.get(9).toString();
            int iy = Integer.parseInt(element.get(10).toString());
            String enterBy = element.get(11).toString();
            int tranType = Integer.parseInt(element.get(12).toString());
            String tranTime = element.get(13).toString();
            int payBy = Integer.parseInt(element.get(14).toString());
            String instNo = "";
            String instrDt = "";
            if (element.get(15) != null) {
                instNo = element.get(15).toString();
                instrDt = element.get(20).toString();
            }
            float voucherNo = 0;
            if (element.get(16) != null) {
                voucherNo = Float.parseFloat(element.get(16).toString());
            }
            String intFlag = "";
            if (element.get(17) != null) {
                intFlag = element.get(17).toString();
            }
            String closeFlag = "";
            if (element.get(18) != null) {
                closeFlag = element.get(18).toString();
            }

            double amt = drAmt + crAmt;
            String destBrCode = fTSPosting43CBSBean.getCurrentBrnCode(acno);
            String valuedt = element.get(19).toString();

            if (brCode.equals(destBrCode)) {
                String lResult = fTSPosting43CBSBean.insertRecons(actNature, acno, ty, amt, dt, valuedt, tranType, remarks, enterBy, trsNo, tranTime, recno,
                        "Y", authBy, tranDesc, payBy, instNo, instrDt, 0f, null, "A", iy, null, voucherNo, intFlag, closeFlag, brCode, destBrCode, 0, null, "", "");
                if (!lResult.endsWith("TRUE")) {
                    throw new ApplicationException("Insertion Problem In Recon " + lResult);
                }
                //Insert into SMS Table in case of clearing dr authorization.
//                if (!acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
//                    if (smsFacade.isValidAcnoForSms("T", acno, 1, 1, amt)) {
//                        String balance = commonReport.getBalanceOnDate(acno, dt).toString();
//
//                        String message = "Your a/c XXXX" + acno.substring(4, 10) + "XX is debited by Rs. " + nf.format(amt) + " on " + dmy.format(ymd.parse(dt)) + " by clearing. Your available balance is Rs. " + nf.format(Double.parseDouble(balance));
//                        lResult = fTSPosting43CBSBean.insertSms(acno, message);
//                        if (!lResult.equalsIgnoreCase("TRUE")) {
//                            throw new ApplicationException(lResult);
//                        }
//                    }
//                }
                /*Block Code Start For Fidility Functionality*/

//                if ((actNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (actNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
//                    if (ty == 0) {
//                        String chkMsg = fdFidilityChk(acno);
//                        if (chkMsg.equalsIgnoreCase("true")) {
//                            String msg = insertFidilityTran(actNature, acno, ty, amt, dt, valuedt, tranType, remarks, enterBy, trsNo, tranTime, recno,
//                                    "Y", authBy, tranDesc, payBy, instNo, instrDt, 0f, null, "A", iy, null, voucherNo, intFlag, closeFlag, brCode, destBrCode, 0, null, "", "");
//                            if (!msg.equalsIgnoreCase("true")) {
//                                throw new ApplicationException("Insertion problem in Fidility Account");
//                            }
//                        }
//                    }
//                }
                /*Block Code End For Fidility Functionality*/
                if (option.equals("OUTWARD") && ty == 0) {
                    lResult = fTSPosting43CBSBean.updateBalance(actNature, acno, crAmt, 0, "Y", "Y");
                    if (!lResult.equals("TRUE")) {
                        throw new ApplicationException("Updation Problem of ReconBalance2 :" + lResult);
                    }
                }
                if (iy == 11 && tranDesc != 9) {
                    List list1 = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_sundry where Fyear=extract(year from '" + dt + "')").getResultList();
                    Vector element1 = (Vector) list1.get(0);
                    float seqNo = Float.parseFloat(element1.get(0).toString());
                    Query q1 = em.createNativeQuery("Insert into bill_sundry(Fyear,AcNo,amount,enterby,dt,status,auth,"
                            + "trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + dt + "'),'" + acno + "',ifnull(" + crAmt + ",0)+ifnull(" + drAmt + ",0),'" + enterBy + "','" + dt + "',"
                            + "'ISSUED','Y',1," + seqNo + "," + ty + "," + recno + ",'" + dt + "','" + authBy + "')");
                    int rs = q1.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Insertion problem in sundry");
                    }
                }

                if (iy == 12 && tranDesc != 9) {
                    List list1 = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_suspense where Fyear=extract(year from '" + dt + "')").getResultList();
                    Vector element1 = (Vector) list1.get(0);
                    float seqNo = Float.parseFloat(element1.get(0).toString());
                    Query q1 = em.createNativeQuery("Insert into bill_suspense(Fyear,AcNo,amount,enterby,dt,status,auth,"
                            + "trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + dt + "'),'" + acno + "',ifnull(" + crAmt + ",0)+ifnull(" + drAmt + ",0),'" + enterBy + "','" + dt + "',"
                            + "'ISSUED','Y',1," + seqNo + "," + ty + "," + recno + ",'" + dt + "','" + authBy + "')");
                    int rs = q1.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Insertion problem in suspense");
                    }
                }

                if (iy == 13 && tranDesc != 9) {
                    List list1 = em.createNativeQuery("select ifnull(max(seqno),0) + 1 from bill_clgadjustment where acno='" + acno + "' AND SUBSTRING(ACNO,1,2) = '" + brCode + "' and Fyear=extract(year from '" + dt + "')").getResultList();
                    Vector element1 = (Vector) list1.get(0);
                    float seqNo = Float.parseFloat(element1.get(0).toString());
                    Query q1 = em.createNativeQuery("Insert into bill_clgadjustment(Fyear,AcNo,amount,enterby,dt,status,auth,"
                            + "trantype,seqno,ty,recno,origindt,authby) values(extract(year from '" + dt + "'),'" + acno + "',ifnull(" + crAmt + ",0)+ifnull(" + drAmt + ",0),'" + enterBy + "','" + dt + "',"
                            + "'ISSUED','Y',1," + seqNo + "," + ty + "," + recno + ",'" + dt + "','" + authBy + "')");
                    int rs = q1.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Insertion problem in clg adjustment");
                    }
                }
            } else {
                /* Code for handling inter branch transaction Added By Dhirendra Singh*/
                List chkList3 = em.createNativeQuery("select acno from abb_parameter_info where purpose='INTERSOLE ACCOUNT'").getResultList();
                if (chkList3.isEmpty()) {
                    throw new ApplicationException("Intersole account does not defined");
                }
                Vector recLst3 = (Vector) chkList3.get(0);
                String isoHead = recLst3.get(0).toString();
                String orgnIntersoleAcNo = brCode + isoHead;
                String destIntersoleAcNo = destBrCode + isoHead;

                List orgnAlphaCodeList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '" + Integer.parseInt(brCode) + "'").getResultList();

                if (orgnAlphaCodeList.isEmpty()) {
                    throw new ApplicationException("Please enter ALPHA CODE for Orign branch");
                }
                Vector orgnAlphaCodeVect = (Vector) orgnAlphaCodeList.get(0);
                String orgnAlphaCode = orgnAlphaCodeVect.get(0).toString();

                remarks = "AT " + orgnAlphaCode + ": " + remarks;

                String recon = fTSPosting43CBSBean.insertRecons(actNature, acno, ty, amt, dt, valuedt, tranType, remarks, enterBy, trsNo, tranTime, recno, "Y",
                        authBy, tranDesc, payBy, instNo, instrDt, 0f, null, "A", 9999, null, voucherNo, intFlag, closeFlag, brCode, destBrCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    throw new ApplicationException("Problem in Insertion in recons ");
                }
                //Insert into SMS Table in case of clearing dr authorization.
//                if (!acno.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
//                    if (smsFacade.isValidAcnoForSms("T", acno, 1, 1, amt)) {
//                        String balance = commonReport.getBalanceOnDate(acno, dt).toString();
//
//                        String message = "Your a/c XXXX" + acno.substring(4, 10) + "XX is debited by Rs. " + nf.format(amt) + " on " + dmy.format(ymd.parse(dt)) + " by clearing. Your available balance is Rs. " + nf.format(Double.parseDouble(balance));
//                        String lResult = fTSPosting43CBSBean.insertSms(acno, message);
//                        if (!lResult.equalsIgnoreCase("TRUE")) {
//                            throw new ApplicationException(lResult);
//                        }
//                    }
//                }

                int rvTy = 0;
                if (ty == 0) {
                    rvTy = 1;
                }
                recon = fTSPosting43CBSBean.insertRecons("PO", destIntersoleAcNo, rvTy, amt, dt, valuedt, tranType, remarks, enterBy, trsNo, null, recno, "Y", authBy,
                        0, 3, null, null, 0f, null, "A", 9999, null, null, null, null, brCode, destBrCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    throw new ApplicationException("Problem in Insertion in recons ");
                }

                recon = fTSPosting43CBSBean.insertRecons("PO", orgnIntersoleAcNo, ty, amt, dt, valuedt, tranType, remarks, enterBy, trsNo, null, recno, "Y", authBy,
                        0, 3, null, null, 0f, null, "A", 8888, null, null, null, null, brCode, destBrCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    throw new ApplicationException("Problem in Insertion in recons");
                }
            }
            return "YES";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /*                       End of Clearing Authorization         */

    /*                       Start of Xfer Authorization         */
    public List getUnAuthXferDetails(String orgnBrCode) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            cbsAuthGetTransfer(new SimpleDateFormat("yyyyMMdd").format(new Date()), orgnBrCode);
            String query = "select acno,custname,trantype,instno,cramt,auth,enterby,recno,ty,DRAMT,TRSNO,balance,payby, details,clgreason,iy,"
                    + "subtokenno,checkby,CASE date_format(instdt,'%d/%m/%Y') WHEN '01/01/1900' THEN '' ELSE date_format(instdt,'%d/%m/%Y') END,date_format(valuedt,'%d/%m/%Y'), AdviceNo, AdviceBrnCode from tran_temp "
                    + "where org_brnid= '" + orgnBrCode + "' ORDER BY TRSNO,recno";
            Query selectQuery = em.createNativeQuery(query);
            resultList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public String getTranDescription(int tranDesc) throws ApplicationException {
        try {
            String query = "select description from codebook where groupcode= 42 and code = " + tranDesc;
            List dataList = em.createNativeQuery(query).getResultList();
            String txnDesc = "Others";
            if (!dataList.isEmpty()) {
                Vector recLst3 = (Vector) dataList.get(0);
                txnDesc = recLst3.get(0).toString();
            }
            return txnDesc;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String cbsAuthTransfer(String dt, Float lblBatch, String enterByPage, String orgnBrnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String auth = null, msg = "", msgTr = "", sunSusMsg = "";
        Float trSNo;
        double totalDrAmt = 0.0;
        try {
            ut.begin();
            List getDataList = em.createNativeQuery("select acno,custname,trantype,instno,cramt,auth,enterby,recno,ty,dramt,"
                    + "trsno,balance,payby,details,clgreason,iy,subtokenno,checkby,org_brnid,dest_brnid from tran_temp "
                    + "where trsno = cast(" + lblBatch + " as unsigned)").getResultList();
            if (getDataList.size() <= 0) {
                throw new ApplicationException("Please check Batch No, you have passed.");
            }
            List totalDrAmtList = em.createNativeQuery("select sum(dramt) from tran_temp where "
                    + "trsno = cast(" + lblBatch + " as unsigned)").getResultList();
            if (totalDrAmtList.size() > 0) {
                Vector element = (Vector) totalDrAmtList.get(0);
                totalDrAmt = Double.parseDouble(element.get(0).toString());
            }
            List userAuthLimit = em.createNativeQuery("select coalesce(trandebit,0.0) from securityinfo where "
                    + "userid='" + enterByPage + "' and brncode='" + orgnBrnCode + "'").getResultList();
            if (userAuthLimit.size() <= 0) {
                throw new ApplicationException("UserId does not exists ::" + enterByPage);
            }
            Vector element = (Vector) userAuthLimit.get(0);
            double userLimit = Double.parseDouble(element.get(0).toString());
            if (userLimit == 0.0 || totalDrAmt > userLimit) {
                throw new ApplicationException("Your passing limit is less than withdrawal amount. You can not "
                        + "authorize this transaction");
            }
            //Cheking of transaction type.
            String msg1 = cbsDestEntryIdentification(lblBatch);
            if (msg1.equalsIgnoreCase("true")) {
                List enterByList = em.createNativeQuery("select enterby from tran_temp where "
                        + "trsno =cast(" + lblBatch + " as unsigned)").getResultList();
                if (enterByList.size() > 0) {
                    Vector enterByVect = (Vector) enterByList.get(0);
                    String enterBy = enterByVect.get(0).toString();
                    if (enterBy.toUpperCase().equals(enterByPage.toUpperCase())) {
                        throw new ApplicationException("You can not Pass Your Own Entry.");
                    }
                    String msg2 = cbsCoreTrfAuth(lblBatch, enterByPage);
                    if (!msg2.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg2);
                    }
                    if (msg2.equalsIgnoreCase("true")) {
                        if (!shareAuth(enterByPage, orgnBrnCode, lblBatch, dt)) {
                            throw new ApplicationException("Data does not save in share module.");
                        }
                        if (!dividendPaymentProcess(enterByPage, orgnBrnCode, lblBatch, dt)) {
                            throw new ApplicationException("Data does not Dividend Payment.");
                        }
                        //Code for handling PO /DD /AD / TPO Code added by Dhirendra Singh
                        for (int i = 0; i < getDataList.size(); i++) {
                            String acNo = (((Vector) getDataList.get(i)).get(0)).toString();
                            String detail = (((Vector) getDataList.get(i)).get(13)).toString();
                            int clgReason = Integer.parseInt((((Vector) getDataList.get(i)).get(14)).toString());
                            int iy = Integer.parseInt((((Vector) getDataList.get(i)).get(15)).toString());
                            double crAmt = Double.parseDouble((((Vector) getDataList.get(i)).get(4)).toString());
                            double drAmt = Double.parseDouble((((Vector) getDataList.get(i)).get(9)).toString());
                            float recNo = Float.parseFloat((((Vector) getDataList.get(i)).get(7)).toString());
                            if (detail.indexOf("~`") > -1) {
                                //Change By Dhirendra Singh
                                String flag = insertIntoBillPO(detail, auth, orgnBrnCode);
                                if (!flag.equalsIgnoreCase("true")) {
                                    throw new ApplicationException(flag);
                                }
                            }
                            int ty = 0;
                            if ((crAmt != 0)) {
                                ty = 0;
                                drAmt = 0d;
                            } else {
                                ty = 1;
                                crAmt = 0d;
                            }
                            if (iy == 11 || iy == 12 || iy == 13) {
                                float seqNo = 0f;
                                if (clgReason != 9) {
                                    if (iy == 11) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_sundry WHERE FYEAR = extract(year from '" + dt + "')").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (iy == 12) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_suspense WHERE FYEAR = extract(year from '" + dt + "')").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (iy == 13) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_clgadjustment WHERE ACNO='" + acNo + "' AND FYEAR = extract(year from '" + dt + "')").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (seqNo == 0f) {
                                        seqNo = 1f;
                                    } else {
                                        seqNo = seqNo + 1;
                                    }
                                    double stmAmt = 0d;
                                    if (crAmt != 0) {
                                        stmAmt = crAmt;
                                    }
                                    if (drAmt != 0) {
                                        stmAmt = drAmt;
                                    }
                                    if (stmAmt > 0) {
                                        if (iy == 11) {
                                            em.createNativeQuery("INSERT bill_sundry (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,"
                                                    + "SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + enterByPage
                                                    + "','" + dt + "', 'ISSUED','Y',2," + seqNo + ","
                                                    + ty + "," + recNo + ",'" + dt + "','" + enterByPage
                                                    + "','" + detail + "')").executeUpdate();
                                        }
                                        if (iy == 12) {
                                            em.createNativeQuery("INSERT bill_suspense (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,TY,"
                                                    + "RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + enterByPage
                                                    + "','" + dt + "', 'ISSUED','Y',2," + seqNo + "," + ty
                                                    + "," + recNo + ",'" + dt + "','" + enterByPage
                                                    + "','" + detail + "')").executeUpdate();
                                        }
                                        if (iy == 13) {
                                            em.createNativeQuery("INSERT bill_clgadjustment (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,"
                                                    + "TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + enterByPage + "','" + dt + "', 'ISSUED','Y',2," + seqNo + "," + ty + "," + recNo + ",'" + dt + "','" + enterByPage + "','" + detail + "')").executeUpdate();
                                        }
                                        //msg = "SEQUENCE NO. IS " + seqNo + " FOR " + acNo + " ACCOUNT";
                                        sunSusMsg = "Sequence No. is " + seqNo + " for " + acNo + " A/C";
                                    }
                                }
                            }
                        }

                        for (int s = 0; s < getDataList.size(); s++) {
                            Vector getDateVect = (Vector) getDataList.get(s);
                            //Deaf updation
                            fTSPosting43CBSBean.lastTxnDateUpdation(getDateVect.get(0).toString());
                        }
                        ut.commit();
                        if (sunSusMsg.equals("")) {
                            msg2 = "TRUE" + msg2;
                        } else {
                            msg2 = "TRUE" + "->" + sunSusMsg;
                        }

                        //msg2 = "TRUE" + msg;
                        //Sending Sms
                        try {
                            List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                            List<SmsToBatchTo> smsExceedList = new ArrayList<SmsToBatchTo>();
                            for (int s = 0; s < getDataList.size(); s++) {
                                SmsToBatchTo to = new SmsToBatchTo();

                                Vector getDateVect = (Vector) getDataList.get(s);
                                to.setAcNo(getDateVect.get(0).toString());
                                to.setCrAmt(Double.parseDouble(getDateVect.get(4).toString()));
                                to.setDrAmt(Double.parseDouble(getDateVect.get(9).toString()));
                                to.setTranType(2);
                                to.setTy(Integer.parseInt(getDateVect.get(8).toString()));
                                to.setTxnDt(dmyOne.format(ymd.parse(dt)));
                                String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                                        == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                                if (templateType.equalsIgnoreCase("indr")) {
                                    to.setTemplate(to.getTy() == 0 ? SmsType.TRANSFER_DEPOSIT_INDR : SmsType.TRANSFER_WITHDRAWAL_INDR);
                                    String details = getDateVect.get(13).toString();
                                    String details1 = "";
                                    if (details.length() <= 30) {
                                        details1 = details;
                                    } else if (details.length() > 30) {
                                        details1 = details.substring(1, 30);
                                    }
                                    to.setDetails(details1);
                                } else {
                                    to.setTemplate(to.getTy() == 0 ? SmsType.TRANSFER_DEPOSIT : SmsType.TRANSFER_WITHDRAWAL);
                                    to.setDetails("");
                                }
                                smsList.add(to);

                                //Making List For CC-OD Limit Excced
                                if (Integer.parseInt(getDateVect.get(8).toString()) == 1
                                        && Integer.parseInt(getDateVect.get(15).toString()) == 99) {
                                    SmsToBatchTo exccedTo = new SmsToBatchTo();
                                    exccedTo.setAcNo(getDateVect.get(0).toString());
                                    exccedTo.setDrAmt(Double.parseDouble(getDateVect.get(9).toString()));
                                    smsExceedList.add(exccedTo);
                                }
                                //End Here
                            }
                            smsFacade.sendSmsToBatch(smsList);

                            //Limit Exceed SMS
                            for (int c = 0; c < smsExceedList.size(); c++) {
                                SmsToBatchTo exccedTo = smsExceedList.get(c);
                                sendCCODExceedSms(exccedTo.getAcNo(), exccedTo.getDrAmt());
                            }
                            //End Here
                        } catch (Exception e) {
                            System.out.println("Problem In SMS Sending To Batch In "
                                    + "Transfer Authorization." + e.getMessage());
                        }
                        //End here.
                        return msg2;
                    }
                }
            } else {
                /*When the Orgn Branch Code & Destination Branh both are same*/
                List enterByList = em.createNativeQuery("select enterby,valuedt,instdt from tran_temp "
                        + "where trsno =" + lblBatch).getResultList();
                if (enterByList.size() > 0) {
                    Vector enterByVect = (Vector) enterByList.get(0);
                    String enterBy = enterByVect.get(0).toString();
                    String valDt = enterByVect.get(1).toString();
                    if (enterBy.toUpperCase().equals(enterByPage.toUpperCase())) {
                        throw new ApplicationException("You can not Pass Your Own Entry.");
                    }
                    if (!shareAuth(enterByPage, orgnBrnCode, lblBatch, dt)) {
                        throw new ApplicationException("Data does not save in share module.");
                    }
                    if (!dividendPaymentProcess(enterByPage, orgnBrnCode, lblBatch, dt)) {
                        throw new ApplicationException("Data does not Dividend Payment.");
                    }
                    int intPostOnDeposit = 0;
                    intPostOnDeposit = Integer.parseInt(fTSPosting43CBSBean.getCodeByReportName("INT_POST_DEPOSIT"));
                    for (int i = 0; i < getDataList.size(); i++) {
                        Vector getDateVect = (Vector) getDataList.get(i);
                        String acNo = getDateVect.get(0).toString();
                        String instNo = getDateVect.get(3).toString();
                        double crAmt = Double.parseDouble(getDateVect.get(4).toString());
                        auth = getDateVect.get(5).toString();

                        enterBy = getDateVect.get(6).toString();
                        float recNo = Float.parseFloat(getDateVect.get(7).toString());
                        double drAmt = Double.parseDouble(getDateVect.get(9).toString());
                        trSNo = Float.parseFloat(getDateVect.get(10).toString());

                        String details = getDateVect.get(13).toString();
                        int clgReason = Integer.parseInt(getDateVect.get(14).toString());
                        int iy = Integer.parseInt(getDateVect.get(15).toString());
                        String checkBy = getDateVect.get(17).toString();
                        String destBrnid = getDateVect.get(19).toString();
                        String acNature = fTSPosting43CBSBean.getAccountNature(acNo);

                        int ty = 0;
                        double total;
                        String dt1 = "";
                        if (!trSNo.equals(lblBatch)) {
                            throw new ApplicationException("Please check your batch no.");
                        }
                        if ((crAmt != 0)) {
                            ty = 0;
                            drAmt = 0d;
                        } else {
                            ty = 1;
                            crAmt = 0d;
                        }
                        /*Block Code Start For Fidility Functionality*/
                        String chkMsg = fdFidilityChk(acNo);
                        if (((acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (acNature.equalsIgnoreCase(CbsConstant.MS_AC))) && ((chkMsg.equalsIgnoreCase("true")) && (ty == 0))) {
                            List dataFd = em.createNativeQuery("SELECT R.valueDt,R.trantype,R.trantime,R.trandesc,R.payby,R.instDt,R.TokenNo,"
                                    + "R.TokenPaidBy,R.SubTokenNo,R.tdacno,R.voucherno,R.intflag,R.closeflag,R.Tran_id,R.Term_id,R.AdviceNo,"
                                    + "R.AdviceBrnCode FROM recon_trf_d R WHERE AUTH='Y' AND TRSNO=cast(" + trSNo + " as unsigned) AND TY = " + ty + " AND RECNO = " + recNo + " and org_brnid = '" + orgnBrnCode + "'").getResultList();
                            if (!dataFd.isEmpty()) {
                                Vector dF = (Vector) dataFd.get(0);
                                msg = insertFidilityTran(acNature, acNo, ty, crAmt, dt, dF.get(0).toString(), Integer.parseInt(dF.get(1).toString()), details,
                                        enterBy, trSNo, dF.get(2).toString(), recNo, "Y", enterByPage, Integer.parseInt(dF.get(3).toString()),
                                        Integer.parseInt(dF.get(4).toString()), instNo, dF.get(5).toString(), Float.parseFloat(dF.get(6).toString()),
                                        dF.get(7).toString(), dF.get(8).toString(), iy, dF.get(9).toString(), Float.parseFloat(dF.get(10).toString()),
                                        dF.get(11).toString(), dF.get(12).toString(), orgnBrnCode, destBrnid, Integer.parseInt(dF.get(13).toString()),
                                        dF.get(14).toString(), dF.get(15).toString(), dF.get(16).toString());
                                if (!msg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Insertion problem in Fidility Account.");
                                }
                            }
                        } /*Block Code End For Fidility Functionality*/ else {
                            List authList = em.createNativeQuery("select auth from recon_trf_d where acno = '" + acNo
                                    + "' and recno = '" + recNo + "' and trsno = cast(" + trSNo + " as unsigned) and "
                                    + "(auth is null or auth = 'N') and ty = '" + ty + "' and dt = '" + dt + "'").getResultList();
                            Vector authvect = (Vector) authList.get(0);
                            auth = authvect.get(0).toString();
                            if (!auth.equalsIgnoreCase("N")) {
                                throw new ApplicationException("Batch has been already authorized.");
                            }
                            if (iy == 21) {
                                String billNo = null;
                                msg = cbsAuthTransferObcProcess(billNo, dt, instNo, enterByPage, trSNo, recNo, crAmt,
                                        drAmt, orgnBrnCode);
                                if (!msg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException(msg);
                                }
                            }
                            auth = "Y";
                            Integer updateQry = em.createNativeQuery("UPDATE recon_trf_d SET AUTH = '" + auth + "', AUTHBY = '"
                                    + enterByPage + "' WHERE ACNO = '" + acNo + "' AND RECNO = " + recNo + " "
                                    + "AND TRSNO = cast(" + trSNo + " as unsigned) AND (AUTH IS NULL OR AUTH = 'N') AND TY = '" + ty + "' "
                                    + "AND DT = '" + dt + "'").executeUpdate();
                            if (updateQry > 0) {
                                msgTr = authTransferTrans(trSNo, ty, recNo, orgnBrnCode);
                                if (!msgTr.equalsIgnoreCase("yes")) {
                                    throw new ApplicationException(msgTr);
                                }
                                msg = "TRUE";
                            }
                            //Code for handling PO /DD /AD / TPO Code added by Dhirendra Singh
                            if (details.indexOf("~`") > -1) {
                                String flag = insertIntoBillPO(details, auth, orgnBrnCode);
                                if (!flag.equalsIgnoreCase("true")) {
                                    throw new ApplicationException(flag);
                                }
                            }
                            //Code for Litigation charges for  Ramgarhia
                            if (acNature.equals(CbsConstant.PAY_ORDER) && clgReason == 103 && ty == 1) {
                                saveNPALitigationCharges(checkBy, dt, recNo, trSNo, orgnBrnCode, enterByPage, drAmt);
                            }
                            //For TDS Entry in RD AND DDS 
                            if ((acNature.equals(CbsConstant.RECURRING_AC) || acNature.equals(CbsConstant.DEPOSIT_SC))) {
                                if ((instNo.equals("VOUCHER")) && (clgReason == 10 || clgReason == 33) && (drAmt != 0)) {
                                    int tdsRs = em.createNativeQuery("Insert into tdshistory(acno,voucherno,tds,dt,fromdt,todt,intopt) "
                                            + "VALUES('" + acNo + "',0," + drAmt + "," + "'" + dt + "','" + dt + "','"
                                            + dt + "','Q')").executeUpdate();
                                    if (tdsRs <= 0) {
                                        throw new ApplicationException("Data does not inserted in tds history.");
                                    }

                                    List unRecTdsList = em.createNativeQuery("select TXNID from tds_reserve_history where acno = '" + acNo + "' "
                                            + " and tds = " + drAmt + " and recovered ='NR'  order by TXNID").getResultList();
                                    if (!unRecTdsList.isEmpty()) {
                                        if (unRecTdsList.size() > 1) {
                                            Vector unRecTdsVect = (Vector) unRecTdsList.get(0);
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trSNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + acNo + "' and recovered ='NR' and tds = " + drAmt + " and TXNID = " + unRecTdsVect.get(0).toString());
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                throw new ApplicationException("Error in updating tdshistory for tds ");
                                            }
                                        } else if (unRecTdsList.size() == 1) {
                                            Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trSNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                    + " where acno = '" + acNo + "'  and recovered ='NR' and tds = " + drAmt + " ");
                                            int result = updateTdsQuery.executeUpdate();
                                            if (result < 0) {
                                                throw new ApplicationException("Error in updating tdshistory for tds ");
                                            }
                                        }
                                    } else {
                                        Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                                                + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                                                + "VALUES('" + acNo + "',0," + drAmt + ",'" + dt + "','" + dt + "','" + dt + "','Q',"
                                                + "'R'," + trSNo + "," + recNo + ",0,date_format(now(),'%Y%m%d'))");
                                        int result = insertTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                                        }
                                    }
                                }
                            }

                            //For TDS Entry 
                            if ((!instNo.equals("VOUCHER")) && (clgReason == 10 || clgReason == 33) && (drAmt != 0)) {
                                List rtNoList = em.createNativeQuery("SELECT voucherno,ifnull(tdacno,'') from recon_trf_d where acno='"
                                        + acNo + "' and dt='" + dt + "' and trsno = " + trSNo + " and recno=" + recNo).getResultList();
                                if (rtNoList.isEmpty()) {
                                    throw new ApplicationException("Proper data does not exist in recon_trf_d table "
                                            + "for this batch");
                                }
                                Vector rtNoVect = (Vector) rtNoList.get(0);
                                float vchNo = Float.parseFloat(rtNoVect.get(0).toString());
                                String tdAcno = "";
                                if (acNature.equals(CbsConstant.PAY_ORDER)) {
                                    tdAcno = rtNoVect.get(1).toString();
                                } else {
                                    tdAcno = acNo;
                                }
                                List intOptList = em.createNativeQuery("select upper(intopt) from td_vouchmst where acno='"
                                        + tdAcno + "' and voucherno = " + vchNo).getResultList();
                                String tdsIntOpt = "";
                                if (intOptList.size() > 0) {
                                    Vector intOptVect = (Vector) intOptList.get(0);
                                    tdsIntOpt = intOptVect.get(0).toString();
                                }
                                if (tdsIntOpt.equals("C")) {
                                    int rs = em.createNativeQuery("update td_vouchmst set cumuprinamt=ifnull(cumuprinamt,0)-" + drAmt
                                            + ", tdsdeducted=ifnull(tdsdeducted,0)+" + drAmt + " where acno='" + tdAcno
                                            + "' and voucherno = " + vchNo).executeUpdate();
                                    if (rs <= 0) {
                                        throw new ApplicationException("Data does not updated");
                                    }
                                } else {
                                    int rs = em.createNativeQuery("update td_vouchmst set tdsdeducted=ifnull(tdsdeducted,0)+" + drAmt
                                            + " where acno='" + tdAcno + "' and voucherno = " + vchNo).executeUpdate();
                                    if (rs <= 0) {
                                        throw new ApplicationException("Data does not updated");
                                    }
                                }
                                int tdsRs = em.createNativeQuery("Insert into tdshistory(acno,voucherno,tds,dt,fromdt,todt,intopt) "
                                        + "VALUES('" + tdAcno + "'," + vchNo + "," + drAmt + "," + "'" + dt + "','" + dt + "','"
                                        + dt + "','" + tdsIntOpt + "')").executeUpdate();
                                if (tdsRs <= 0) {
                                    throw new ApplicationException("Data does not inserted in tds history.");
                                }

                                List unRecTdsList = em.createNativeQuery("select TXNID from tds_reserve_history where acno = '" + acNo + "' and voucherno = " + vchNo + " and tds = " + drAmt + " and recovered ='NR' order by TXNID").getResultList();
                                if (!unRecTdsList.isEmpty()) {
                                    if (unRecTdsList.size() > 1) {
                                        Vector unRecTdsVect = (Vector) unRecTdsList.get(0);
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trSNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + acNo + "' and VoucherNo = " + vchNo + " and recovered ='NR' and tds = " + drAmt + " and TXNID = " + unRecTdsVect.get(0).toString());
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                    } else if (unRecTdsList.size() == 1) {
                                        Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', recoveredVch = " + trSNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                                + " where acno = '" + acNo + "' and VoucherNo = " + vchNo + " and recovered ='NR' and tds = " + drAmt);
                                        int result = updateTdsQuery.executeUpdate();
                                        if (result < 0) {
                                            throw new ApplicationException("Error in updating tdshistory for tds ");
                                        }
                                    }
                                } else {
                                    Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                                            + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                                            + "VALUES('" + tdAcno + "'," + vchNo + "," + drAmt + ",'" + dt + "','" + dt + "','" + dt + "','"
                                            + tdsIntOpt + "','R'," + trSNo + "," + recNo + "," + vchNo + ",date_format(now(),'%Y%m%d'))");
                                    int result = insertTdsQuery.executeUpdate();
                                    if (result < 0) {
                                        throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                                    }
                                }

                            }
                            //MIR CODE MEMBER TRANSACTION HANDLE
                            String acChk = fTSPosting43CBSBean.getAcnoByPurpose("MIR-HEAD");
                            if (!acChk.equalsIgnoreCase("") && acChk.equalsIgnoreCase(acNo.substring(2))) {
                                recNo = fTSPosting43CBSBean.getRecNo();
                                int memMir = em.createNativeQuery("Insert into mem_mir_recon(acno,cramt,dramt,dt,recno,trsno,ty,trantype,instno,instdt,auth,enterby,"
                                        + " authby,trandesc,details,org_brnid,dest_brnid) "
                                        + "VALUES('" + details.substring(0, 12) + "'," + crAmt + "," + drAmt + "," + "'" + dt + "'," + recNo + ","
                                        + trSNo + "," + ty + ",2,'" + instNo + "',NULL,'Y','" + enterBy + "','" + enterByPage + "',0,'" + details + "','" + orgnBrnCode + "','" + destBrnid + "')").executeUpdate();
                                if (memMir <= 0) {
                                    throw new ApplicationException("Data does not inserted in mem_mir_recon.");
                                }
                            }
                            if (iy == 11 || iy == 12 || iy == 13) {
                                float seqNo = 0f;
                                if (clgReason != 9) {
                                    if (iy == 11) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_sundry WHERE FYEAR = extract(year from '" + dt + "')").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (iy == 12) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_suspense WHERE FYEAR = extract(year from '" + dt + "')").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (iy == 13) {
                                        List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0) FROM bill_clgadjustment WHERE ACNO='" + acNo + "' AND FYEAR = extract(year from '" + dt + "')").getResultList();
                                        Vector seqNoVect = (Vector) seqNoList.get(0);
                                        seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                    }
                                    if (seqNo == 0f) {
                                        seqNo = 1f;
                                    } else {
                                        seqNo = seqNo + 1;
                                    }
                                    double stmAmt = 0d;
                                    if (crAmt != 0) {
                                        stmAmt = crAmt;
                                    }
                                    if (drAmt != 0) {
                                        stmAmt = drAmt;
                                    }
                                    if (stmAmt > 0) {
                                        if (iy == 11) {
                                            em.createNativeQuery("INSERT bill_sundry (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,"
                                                    + "SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + enterByPage
                                                    + "','" + dt + "', 'ISSUED','Y',2," + seqNo + ","
                                                    + ty + "," + recNo + ",'" + dt + "','" + enterByPage
                                                    + "','" + details + "')").executeUpdate();
                                        }
                                        if (iy == 12) {
                                            em.createNativeQuery("INSERT bill_suspense (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,TY,"
                                                    + "RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + enterByPage
                                                    + "','" + dt + "', 'ISSUED','Y',2," + seqNo + "," + ty
                                                    + "," + recNo + ",'" + dt + "','" + enterByPage
                                                    + "','" + details + "')").executeUpdate();
                                        }
                                        if (iy == 13) {
                                            em.createNativeQuery("INSERT bill_clgadjustment (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,"
                                                    + "TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES( extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + enterByPage + "','" + dt + "', 'ISSUED','Y',2," + seqNo + "," + ty + "," + recNo + ",'" + dt + "','" + enterByPage + "','" + details + "')").executeUpdate();
                                        }
                                        //msg = "SEQUENCE NO. IS " + seqNo + " FOR " + acNo + " ACCOUNT";
                                        sunSusMsg = "Sequence No. is " + seqNo + " for " + acNo + " A/C";
                                    }
                                }
                            }
                            if ((acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                    || acNature.equalsIgnoreCase(CbsConstant.SS_AC) || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))
                                    && (crAmt != 0)) {

                                if (intPostOnDeposit == 0) {
                                    String status = interBranchFacade.fnGetLoanStatus(acNo, dt);
                                    if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                        String msgRecAmt = fTSPosting43CBSBean.npaRecoveryUpdation(trSNo, acNature, acNo, dt, crAmt, orgnBrnCode, destBrnid, enterByPage);
                                        if (!msgRecAmt.equalsIgnoreCase("True")) {
                                            throw new ApplicationException(msg);
                                        }
                                    }
                                }
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                    || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                                if (intPostOnDeposit == 0) {
                                    total = drAmt + crAmt;
                                    try {
                                        dt1 = ymmd.format(ymd.parse(dt));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    msg = fTSPosting43CBSBean.loanDisbursementInstallment(acNo, total, ty, enterByPage, dt1, recNo, clgReason, details);
                                    if (!msg.equalsIgnoreCase("true")) {
                                        throw new ApplicationException(msg);
                                    }
                                }
                            } else if ((acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) && (clgReason == 1 || clgReason == 6 || clgReason == 9)) {
                                total = drAmt + crAmt;
                                try {
                                    dt1 = ymmd.format(ymd.parse(dt));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                msg = fTSPosting43CBSBean.loanDisbursementInstallment(acNo, total, ty, enterByPage, dt1, recNo, clgReason, details);
                                if (!msg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException(msg);
                                }
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                                em.createNativeQuery("update of_recon set tdacno=(select acno from td_vouchmst "
                                        + "where ofacno='" + acNo + "') where acno='" + acNo + "' and tdacno "
                                        + "is null").executeUpdate();
                            }
                            //Postal Code
                            String chFd = cbsFdAcTranChk(acNo);
                            if (chFd.equalsIgnoreCase("true") && (ty == 0)) {
                                String vStartDate = "", vEndDate = "", detail = "", matDate = "", seqNo = null, tmpIntToAcno = "", msg4;
                                float maxVouchNo = 0, tmpVSeqNo = 0;
                                double vBalance = 0;
                                int memWorkPrd = 0;

                                List memWorkLst = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                                        + "where reportname ='MEMBER-WORKING-PERIOD'").getResultList();
                                if (memWorkLst.size() > 0) {
                                    Vector memWorkVec = (Vector) memWorkLst.get(0);
                                    memWorkPrd = Integer.parseInt(memWorkVec.get(0).toString());
                                }

                                List matDateLst = em.createNativeQuery("select date_format(date_add(DATE_FORMAT(str_to_date(tc.dob,'%Y%m%d'),'%Y-%m-%d'), interval '" + memWorkPrd + "' year),'%Y%m%d') from td_accountmaster ta,"
                                        + " td_customermaster tc  where ta.acno ='" + acNo + "' and tc.custno = substring('" + acNo + "',5,6) and "
                                        + " tc.brncode = substring('" + acNo + "',1,2) and tc.actype = substring('" + acNo + "',3,2)").getResultList();
                                if (matDateLst.size() > 0) {
                                    for (int j = 0; j < matDateLst.size(); j++) {
                                        Vector matDateVec = (Vector) matDateLst.get(j);
                                        matDate = matDateVec.get(0).toString();
                                    }
                                }

                                List aRes = CbsUtil.getYrMonDayDiff(dt, matDate);
                                int prdY = Integer.parseInt(aRes.get(0).toString());
                                int prdM = Integer.parseInt(aRes.get(1).toString());
                                int prdD = Integer.parseInt(aRes.get(2).toString());

                                String period = prdY + "Years" + prdM + "Months" + prdD + "Days";
                                vStartDate = dt.substring(0, 4) + "0101";
                                vEndDate = dt.substring(0, 4) + "1231";

                                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    List tdVouchList = em.createNativeQuery("select ifnull(max(voucherno),0) from td_vouchmst where "
                                            + "substring(acno,3,2)=substring('" + acNo + "',3,2)").getResultList();
                                    if (tdVouchList.size() > 0) {
                                        Vector tdVouchVec = (Vector) tdVouchList.get(0);
                                        maxVouchNo = Float.parseFloat(tdVouchVec.get(0).toString());
                                        if (maxVouchNo == 0) {
                                            maxVouchNo = 1;
                                        } else {
                                            maxVouchNo = maxVouchNo + 1;
                                        }
                                    } else {
                                        maxVouchNo = 1;
                                    }

                                    detail = " Trf To " + acNo + " " + maxVouchNo;
                                    recNo = fTSPosting43CBSBean.getRecNo();
                                    String currBrnCode = fTSPosting43CBSBean.getCurrentBrnCode(acNo);
                                    Integer tdReconInsert = em.createNativeQuery("insert into td_recon (acno,ty,dt,ValueDt,VOUCHERNO,fddt,trantype,dramt,cramt,iy,"
                                            + "instno,recno,DETAILS,payby,trsno,trandesc,tokenno,subtokenno,tokenpaid,enterby,auth,authby,org_brnid,dest_brnid)"
                                            + " VALUES ('" + acNo + "',0,'" + dt + "','" + dt + "'," + maxVouchNo + ",'" + dt + "',27,0," + crAmt + ",1,NULL," + recNo + ",'" + detail + "',3,"
                                            + "" + trSNo + ",2,0,0,'N','" + enterBy + "','Y','" + enterByPage + "','" + orgnBrnCode + "','" + currBrnCode + "')").executeUpdate();
                                    if (tdReconInsert <= 0) {
                                        throw new ApplicationException("Data not saved on td_recon.");
                                    }
                                    recNo = fTSPosting43CBSBean.getRecNo();
                                    Integer tdReconInsert1 = em.createNativeQuery("insert into td_recon (acno,ty,dt,ValueDt,VOUCHERNO,fddt,trantype,"
                                            + "dramt,cramt,iy,instno,recno,DETAILS,payby,trsno,trandesc,tokenno,subtokenno,tokenpaid,enterby,auth,authby,org_brnid,dest_brnid)"
                                            + "VALUES ('" + acNo + "',1,'" + dt + "','" + dt + "'," + maxVouchNo + ",'" + dt + "',27," + crAmt + ",0,1,NULL," + recNo + ",'" + detail + "',3," + trSNo + ",2,0,0,'N',"
                                            + "'" + enterBy + "','Y','" + enterByPage + "','" + orgnBrnCode + "','" + currBrnCode + "')").executeUpdate();
                                    if (tdReconInsert1 <= 0) {
                                        throw new ApplicationException("Data not saved on td_recon.");
                                    }
                                    List balanceList = em.createNativeQuery("select ifnull(balance,0) from td_reconbalan where acno = '" + acNo + "'").getResultList();
                                    if (balanceList.size() > 0) {
                                        Vector balanceVec = (Vector) balanceList.get(0);
                                        vBalance = Float.parseFloat(balanceVec.get(0).toString());
                                    }
                                    vBalance = vBalance - crAmt;
                                    Integer tdReconBalUpdate = em.createNativeQuery("update td_reconbalan set balance = " + vBalance + " where acno = '" + acNo + "' ").executeUpdate();
                                    if (tdReconBalUpdate <= 0) {
                                        throw new ApplicationException("Data not updated on td_reconbalan.");
                                    }

                                    List controlTypeList = em.createNativeQuery("select ifnull(ControlType,'') from td_parameterinfo").getResultList();
                                    if (controlTypeList.size() > 0) {
                                        Vector controlTypeVec = (Vector) controlTypeList.get(0);
                                        String controlType = controlTypeVec.get(0).toString();
                                        if (controlType.equalsIgnoreCase("T")) {
                                            List seqNoList = em.createNativeQuery("select ifnull(max(seqno),0) from td_vouchmst where td_madedt "
                                                    + "between '" + vStartDate + "' and '" + vEndDate + "' and substring(acno,3,2) = substring('" + acNo + "',3,2)").getResultList();
                                            if (seqNoList.size() > 0) {
                                                Vector seqNoVec = (Vector) seqNoList.get(0);
                                                seqNo = seqNoVec.get(0).toString();
                                            }
                                        } else if (controlType.equalsIgnoreCase("N")) {
                                            List seqNoList = em.createNativeQuery("select ifnull(MAX(SeqNo),0) from td_vouchmst Where TD_MadeDt"
                                                    + " between '" + vStartDate + "' AND '" + vEndDate + "' AND substring(acno,3,2) IN "
                                                    + "(select acctCode from accounttypemaster WHERE acctNature = '" + acNature + "')").getResultList();
                                            if (seqNoList.size() > 0) {
                                                Vector seqNoVec = (Vector) seqNoList.get(0);
                                                seqNo = seqNoVec.get(0).toString();
                                            }
                                        }
                                    } else {
                                        tmpVSeqNo = 1;
                                    }
                                    if (seqNo == null || seqNo.equalsIgnoreCase("") || seqNo.equalsIgnoreCase("0.0")) {
                                        tmpVSeqNo = 1;
                                    } else {
                                        tmpVSeqNo = Float.parseFloat(seqNo) + 1;
                                    }
                                    tmpIntToAcno = acNo;
                                    Float roi = tdRcptMgmtRemote.tdApplicableROI(acNo, "OT", (float) crAmt, matDate, dt, dt, acNature);
                                    Integer tdInsert = em.createNativeQuery("insert into td_vouchmst (Acno,Voucherno,ReceiptNo,Prinamt,Fddt,matDt,roi,intOpt,"
                                            + "autoRenew,SeqNo,Auth,Authby,enterBy,Status,period,years,months,days,NextIntPayDt,cumuprinamt,TD_MadeDt,"
                                            + "intToAcno,trantime,offlag) VALUES('" + acNo + "'," + maxVouchNo + "," + maxVouchNo + "," + crAmt + ",'" + dt + "','" + matDate + "'," + roi + ","
                                            + "'S','N'," + tmpVSeqNo + ",'Y','" + enterByPage + "','" + enterBy + "','A','" + period + "'," + prdY + "," + prdM + "," + prdD + ",'" + dt + "',"
                                            + "" + crAmt + ",'" + dt + "','',now(),'N')").executeUpdate();
                                    if (tdInsert <= 0) {
                                        throw new ApplicationException("Data not saved on td_vouchmst.");
                                    }
                                }
                            }
                        }
                    }
                    //Addition of interest history insertion.
                    List dataList = em.createNativeQuery("SELECT ACNO, CUSTNAME, DT, DRAMT, CRAMT, TY, TRANTYPE, RECNO, TRSNO,"
                            + "ifnull(INSTNO,''), CAST(PAYBY as unsigned) as PAYBY, IY, COALESCE(OPERMODE,'') as OPERMODE, AUTH, ENTERBY, COALESCE(TOKENPAIDBY,'') as "
                            + "TOKENPAIDBY, TRANDESC, TOKENNO, ifnull(SUBTOKENNO,''), TRANTIME, DETAILS,"
                            + "COALESCE(VOUCHERNO,'') as VOUCHERNO, COALESCE(INTFLAG,'') as INTFLAG, COALESCE(CLOSEFLAG,'') as CLOSEFLAG, "
                            + "COALESCE(TDACNO,'') as TDACNO, TRAN_ID, TERM_ID, ORG_BRNID, DEST_BRNID,VALUEDT,INSTDT,ADVICENO,ADVICEBRNCODE "
                            + "FROM recon_trf_d WHERE TRSNO = cast(" + lblBatch + " as unsigned)").getResultList();
                    for (int i = 0; i < dataList.size(); i++) {
                        Vector dataVec = (Vector) dataList.get(i);
                        String acNo = dataVec.get(0).toString();
                        String date = dataVec.get(2).toString();
                        double drAmt = Double.parseDouble(dataVec.get(3).toString());
                        double crAmt = Double.parseDouble(dataVec.get(4).toString());
                        int ty = Integer.parseInt(dataVec.get(5).toString());
                        int tranDesc = Integer.parseInt(dataVec.get(16).toString());

                        String accountNature = fTSPosting43CBSBean.getAccountNature(acNo);
                        if ((accountNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                                || accountNature.equalsIgnoreCase(CbsConstant.MS_AC)
                                || accountNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                                || accountNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC))
                                && (tranDesc == 3 || tranDesc == 4)) {
                            insertInterestHistory(accountNature, acNo, date, ty, crAmt, drAmt, enterByPage);
                        }
                    }
                    //End Here
                    if (msgTr.equalsIgnoreCase("yes")) {
                        //Deaf updation
                        for (int s = 0; s < getDataList.size(); s++) {
                            Vector getDateVect = (Vector) getDataList.get(s);
                            fTSPosting43CBSBean.lastTxnDateUpdation(getDateVect.get(0).toString());
                        }
                        //Deaf updation end here
                        ut.commit();
                        if (sunSusMsg.equals("")) {
                            msg = "TRUE" + msg;
                        } else {
                            msg = "TRUE" + "->" + sunSusMsg;
                        }
                        //Sending Sms To Batch.
                        try {
                            List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                            List<SmsToBatchTo> smsExceedList = new ArrayList<SmsToBatchTo>();
                            for (int s = 0; s < getDataList.size(); s++) {
                                SmsToBatchTo to = new SmsToBatchTo();

                                Vector getDateVect = (Vector) getDataList.get(s);
                                to.setAcNo(getDateVect.get(0).toString());
                                to.setCrAmt(Double.parseDouble(getDateVect.get(4).toString()));
                                to.setDrAmt(Double.parseDouble(getDateVect.get(9).toString()));
                                to.setTranType(2);
                                to.setTy(Integer.parseInt(getDateVect.get(8).toString()));
                                to.setTxnDt(dmyOne.format(ymd.parse(dt)));
                                String templateType = PropertyContainer.getProperties().getProperty("sms.template.path")
                                        == null ? "" : PropertyContainer.getProperties().getProperty("sms.template.path");
                                if (templateType.equalsIgnoreCase("indr")) {
                                    to.setTemplate(to.getTy() == 0 ? SmsType.TRANSFER_DEPOSIT_INDR : SmsType.TRANSFER_WITHDRAWAL_INDR);
                                    String details = getDateVect.get(13).toString();
                                    String details1 = "";
                                    if (details.length() <= 30) {
                                        details1 = details;
                                    } else if (details.length() > 30) {
                                        details1 = details.substring(1, 30);
                                    }
                                    to.setDetails(details1);
                                } else {
                                    to.setTemplate(to.getTy() == 0 ? SmsType.TRANSFER_DEPOSIT : SmsType.TRANSFER_WITHDRAWAL);
                                }

                                smsList.add(to);
                                //Making List For CC-OD Limit Excced
                                if (Integer.parseInt(getDateVect.get(8).toString()) == 1
                                        && Integer.parseInt(getDateVect.get(15).toString()) == 99) {
                                    SmsToBatchTo exccedTo = new SmsToBatchTo();
                                    exccedTo.setAcNo(getDateVect.get(0).toString());
                                    exccedTo.setDrAmt(Double.parseDouble(getDateVect.get(9).toString()));
                                    smsExceedList.add(exccedTo);
                                }
                                //End Here
                            }
                            smsFacade.sendSmsToBatch(smsList);
                            //Limit Exceed SMS
                            for (int c = 0; c < smsExceedList.size(); c++) {
                                SmsToBatchTo exccedTo = smsExceedList.get(c);
                                sendCCODExceedSms(exccedTo.getAcNo(), exccedTo.getDrAmt());
                            }
                            //End Here
                        } catch (Exception e) {
                            System.out.println("Problem In SMS Sending To Batch In "
                                    + "Transfer Authorization." + e.getMessage());
                        }
                        //End here.
                        return msg;
                    }
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }

    private boolean shareAuth(String user, String brncode, float batchno, String dt) throws ApplicationException {
        List result;
        Vector vtr;
        boolean resultdata = false;
        String foliono, remarks, pono, adviseno, issuedt, status;
        int noofshare, certno;
        try {
            result = em.createNativeQuery("select details,adviceno,advicebrncode from recon_trf_d where dt='" + dt + "' and trsno=" + batchno
                    + " and org_brnid='" + brncode + "' and details like '%~!%' and details like '%~@%' ").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    vtr = (Vector) result.get(i);
                    String detail = vtr.get(0) != null ? vtr.get(0).toString() : "error";
                    String adviceNo = vtr.get(1) != null ? vtr.get(1).toString() : "";
                    if (!detail.contains("error")) {
                        String s[] = detail.split("~!");
                        if (s[1].contains("true")) {
                            String s2[] = s[1].split("~@");
                            foliono = s2[1].toString();
                            pono = s2[2].toString();
                            adviseno = s2[3].toString();
                            remarks = s2[4].toString();
                            noofshare = Integer.parseInt(s2[5].toString());
                            issuedt = s2[6].toString();
                            resultdata = insertdata(foliono, user, pono, adviceNo, remarks, noofshare, brncode, dt, issuedt);
                        } else {
                            String s2[] = s[1].split("~@");
                            foliono = s2[1].toString();
                            certno = Integer.parseInt(s2[2].toString());
                            issuedt = s2[3].toString();
                            status = s2[4].toString();
                            noofshare = Integer.parseInt(s2[5].toString());
                            resultdata = inactiveCertificate(foliono, user, certno, issuedt, status, noofshare, brncode, dt);
                        }
                    }
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultdata;
    }

    private boolean dividendPaymentProcess(String user, String brncode, float batchno, String dt) throws ApplicationException {
        List result;

        try {
            result = em.createNativeQuery("SELECT ACNO, CUSTNAME, DT, DRAMT, CRAMT, TY, TRANTYPE, RECNO, TRSNO,"
                    + "ifnull(INSTNO,''), CAST(PAYBY as unsigned) as PAYBY, IY, COALESCE(OPERMODE,'') as OPERMODE, AUTH, ENTERBY, COALESCE(TOKENPAIDBY,'') as "
                    + "TOKENPAIDBY, TRANDESC, TOKENNO, ifnull(SUBTOKENNO,''), TRANTIME, DETAILS,"
                    + "COALESCE(VOUCHERNO,'') as VOUCHERNO, COALESCE(INTFLAG,'') as INTFLAG, COALESCE(CLOSEFLAG,'') as CLOSEFLAG, "
                    + "COALESCE(TDACNO,'') as TDACNO, TRAN_ID, TERM_ID, ORG_BRNID, DEST_BRNID,VALUEDT,INSTDT,ADVICENO,ADVICEBRNCODE "
                    + "FROM recon_trf_d WHERE TRSNO = cast(" + batchno + " as unsigned) and dt='" + dt + "' and org_brnid='" + brncode + "' and TDACNO <> '' and details like 'Dividend Payment of:%'").getResultList();
//            result = em.createNativeQuery("select details,adviceno,advicebrncode,tdacno,voucherno from recon_trf_d where dt='" + dt + "' and trsno= " + batchno + " "
//                    + "and org_brnid='" + brncode + "' and details like '%Dividend@#%'").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector getDateVect = (Vector) result.get(i);
                    String acNo = getDateVect.get(0).toString();
                    //String custname = getDateVect.get(1).toString();
                    String date = getDateVect.get(2).toString();
                    double drAmt = Double.parseDouble(getDateVect.get(3).toString());
                    double CrAmt = Double.parseDouble(getDateVect.get(4).toString());
                    int ty = Integer.parseInt(getDateVect.get(5).toString());

                    int tranType = Integer.parseInt(getDateVect.get(6).toString());
                    float recNo = Float.parseFloat(getDateVect.get(7).toString());
                    float trSNo = Float.parseFloat(getDateVect.get(8).toString());
                    String instNo = getDateVect.get(9).toString();
                    int payBy = Integer.parseInt(getDateVect.get(10).toString());

                    String enterBy = getDateVect.get(14).toString();
                    //String tokenPaidBy = getDateVect.get(15).toString();
                    int tranDesc = Integer.parseInt(getDateVect.get(16).toString());
                    float tokenNo = Float.parseFloat(getDateVect.get(17).toString());
                    String subTokenNo = getDateVect.get(18).toString();

                    String Details = getDateVect.get(20).toString();
                    float voucherNo = getDateVect.get(21).toString().equals("") ? 0f : Float.parseFloat(getDateVect.get(21).toString());
                    String tdAcNo = getDateVect.get(24).toString();
                    String orgnBrnId = getDateVect.get(27).toString();
                    String destBrnId = getDateVect.get(28).toString();

                    String valuedt = getDateVect.get(29).toString();
                    String adviceNo = getDateVect.get(31).toString();
                    String adviceBrnCode = getDateVect.get(32).toString();

                    //select distinct F_YEAR from yearend where YEARENDFLAG = 'N'
                    String fyear = "";
                    List list = em.createNativeQuery("select distinct F_YEAR from yearend where YEARENDFLAG = 'N'").getResultList();
                    Vector fyVector = (Vector) list.get(0);
                    fyear = fyVector.get(0).toString();

                    Query insertDivRecon = em.createNativeQuery("Insert Into dividend_recon (acno,dramt,cramt,ty,trantype,iy,enterby,authby,details,bcode,dest_brnid, "
                            + "org_brnid,auth,disdate,trandesc,fyear,payby,trantime,recno,trsno) Values ('" + tdAcNo + "'," + drAmt + ",0,1,2,0,'" + enterBy + "','"
                            + user + "','Dividend Payment for acno  " + tdAcNo + "','HO','" + brncode + "','" + brncode + "','Y','" + date + "',110,'"
                            + fyear + "',3,now()," + recNo + "," + trSNo + ")");
                    int insertI = insertDivRecon.executeUpdate();
                    if (insertI <= 0) {
                        throw new ApplicationException("Dividend Payment Failed");
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String cbsDestEntryIdentification(Float trSNo) {
        String msg = null;
        List getDataList = em.createNativeQuery("SELECT ACNO,ORG_BRNID,DEST_BRNID FROM recon_trf_d WHERE TRSNO = cast(" + trSNo + " as unsigned)").getResultList();
        if (getDataList.size() > 0) {
            for (int i = 0; i < getDataList.size(); i++) {
                Vector getDateVect = (Vector) getDataList.get(i);
                String orgnBrnId = getDateVect.get(1).toString();
                String destBrnId = getDateVect.get(2).toString();
                if (!orgnBrnId.equalsIgnoreCase(destBrnId)) {
                    msg = "TRUE";
                    return msg;
                } else {
                    msg = "FALSE";
                }
            }
        } else {
            return "Please check the Bach No, You have passed.";
        }
        return msg;
    }

    public String cbsCoreTrfAuth(Float batchNo, String authBy) throws ApplicationException {
        //Date tranTime;
        String msg = null, instrDt = null;
        if (batchNo == null) {
            return "Please check the Batch No, you have passed.";
        } else {
            List getDataList = em.createNativeQuery("SELECT ACNO, CUSTNAME, DT, DRAMT, CRAMT, TY, TRANTYPE, RECNO, TRSNO,"
                    + "ifnull(INSTNO,''), CAST(PAYBY as unsigned) as PAYBY, IY, COALESCE(OPERMODE,'') as OPERMODE, AUTH, ENTERBY, COALESCE(TOKENPAIDBY,'') as "
                    + "TOKENPAIDBY, TRANDESC, TOKENNO, ifnull(SUBTOKENNO,''), TRANTIME, DETAILS,"
                    + "COALESCE(VOUCHERNO,'') as VOUCHERNO, COALESCE(INTFLAG,'') as INTFLAG, COALESCE(CLOSEFLAG,'') as CLOSEFLAG, "
                    + "COALESCE(TDACNO,'') as TDACNO, TRAN_ID, TERM_ID, ORG_BRNID, DEST_BRNID,VALUEDT,INSTDT,ADVICENO,ADVICEBRNCODE "
                    + "FROM recon_trf_d WHERE TRSNO = cast(" + batchNo + " as unsigned)").getResultList();

            if (getDataList.size() > 0) {
                for (int i = 0; i < getDataList.size(); i++) {
                    Vector getDateVect = (Vector) getDataList.get(i);
                    String acNo = getDateVect.get(0).toString();
                    //String custname = getDateVect.get(1).toString();

                    String dt = getDateVect.get(2).toString();
                    double drAmt = Double.parseDouble(getDateVect.get(3).toString());
                    double CrAmt = Double.parseDouble(getDateVect.get(4).toString());
                    int ty = Integer.parseInt(getDateVect.get(5).toString());

                    int tranType = Integer.parseInt(getDateVect.get(6).toString());
                    float recNo = Float.parseFloat(getDateVect.get(7).toString());
                    float trSNo = Float.parseFloat(getDateVect.get(8).toString());
                    String instNo = getDateVect.get(9).toString();
                    int payBy = Integer.parseInt(getDateVect.get(10).toString());

                    String enterBy = getDateVect.get(14).toString();
                    //String tokenPaidBy = getDateVect.get(15).toString();
                    int tranDesc = Integer.parseInt(getDateVect.get(16).toString());
                    float tokenNo = Float.parseFloat(getDateVect.get(17).toString());
                    String subTokenNo = getDateVect.get(18).toString();

                    String Details = getDateVect.get(20).toString();
                    float voucherNo = getDateVect.get(21).toString().equals("") ? 0f : Float.parseFloat(getDateVect.get(21).toString());
                    String tdAcNo = getDateVect.get(24).toString();
                    String orgnBrnId = getDateVect.get(27).toString();
                    String destBrnId = getDateVect.get(28).toString();

                    String valuedt = getDateVect.get(29).toString();
                    String adviceNo = getDateVect.get(31).toString();
                    String adviceBrnCode = getDateVect.get(32).toString();

                    if ((getDateVect.get(30) == null) || (getDateVect.get(30).toString().equals(""))) {
                        instrDt = null;
                    } else {
                        instrDt = getDateVect.get(30).toString();
                    }

                    double amt = 0d;
                    if (ty == 0) {
                        amt = CrAmt;
                    } else if (ty == 1) {
                        amt = drAmt;
                    }
                    try {
                        String accountNature = fTSPosting43CBSBean.getAccountNature(acNo);
                        if (!orgnBrnId.equalsIgnoreCase(destBrnId)) {
                            msg = interBranchFacade.cbsPostingSx(acNo, ty, valuedt, amt, 0f, tranType, Details, tokenNo, subTokenNo, instNo, instrDt,
                                    payBy, voucherNo, recNo, tranDesc, destBrnId, orgnBrnId, enterBy, authBy, trSNo, adviceNo, adviceBrnCode);
                            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                msg = "TRUE";
                            } else {
                                return msg;
                            }
                        } else {
                            msg = interBranchFacade.cbsPostingCx(acNo, ty, valuedt, amt, 0f, tranType, Details, tokenNo, subTokenNo, instNo, instrDt,
                                    payBy, voucherNo, recNo, tranDesc, destBrnId, orgnBrnId, enterBy, authBy, trSNo, adviceNo, adviceBrnCode);
                            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                msg = "TRUE";
                            } else {
                                return msg;
                            }
                            //Code for Litigation charges for  Ramgarhia
                            if (accountNature.equals(CbsConstant.PAY_ORDER) && tranDesc == 103 && ty == 1) {
                                saveNPALitigationCharges(tdAcNo, dt, recNo, trSNo, orgnBrnId, enterBy, drAmt);
                            }
                        }

                        //Addition for interest history table.
                        if ((accountNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                                || accountNature.equalsIgnoreCase(CbsConstant.MS_AC)
                                || accountNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                                || accountNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC))
                                && (tranDesc == 3 || tranDesc == 4)) {
                            insertInterestHistory(accountNature, acNo, dt, ty, CrAmt, drAmt, authBy);
                        }
                    } catch (Exception ex) {
                        throw new ApplicationException(ex);
                    }
                }
            }
        }
        return msg;
    }

    private String saveNPALitigationCharges(String npaAcNo, String dt, float recNo, float trsNo, String orgnBrnCode, String enterByPage, double drAmt) throws ApplicationException {
        try {
            Query insertNpaReconQuery = em.createNativeQuery("INSERT INTO  npa_recon (ACNo,Ty,Dt,valuedt,DrAmt,CrAmt,TranType,Details,iy,"
                    + "EnterBy,auth,Payby,Authby,Trantime,TranDesc,IntAmt,Status,recno, trsno,org_brnid, dest_brnid,balance)"
                    + " VALUES ('" + npaAcNo + "',1,'" + dt + "','" + dt + "'," + drAmt + ",0,8,'Litigation Charges',1,'" + enterByPage
                    + "','Y',3,'SYSTEM',NOW(),103,0,''," + recNo + "," + trsNo + ",'" + orgnBrnCode + "','" + orgnBrnCode + "',0.0)");
            Integer insertNpaRecon = insertNpaReconQuery.executeUpdate();
            if (insertNpaRecon == 0) {
                throw new ApplicationException("false;Value not inserted in  Npa_recon");
            }
            recNo = fTSPosting43CBSBean.getRecNo();
            String acType = fTSPosting43CBSBean.getAccountCode(npaAcNo);
            List glHeadList = em.createNativeQuery("SELECT IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where "
                    + "acctcode = '" + acType + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for " + acType);
            }
            Vector glHeadVect = (Vector) glHeadList.get(0);
            String uriGl = glHeadVect.get(0).toString();
            String oirHead = glHeadVect.get(1).toString();
            int oirParam = fTSPosting43CBSBean.getCodeForReportName("NPA-CHARGES-IN-OIR");
            if (oirParam == 1 && (!oirHead.equals("") && !uriGl.equals(""))) {
                uriGl = orgnBrnCode + uriGl + "01";
                oirHead = orgnBrnCode + oirHead + "01";
                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                        + " VALUES('" + oirHead + "',0,'" + dt + "','" + dt + "'," + drAmt + ",0,8,3,3,'Litigation Charges on NPA of " + npaAcNo + "','system','Y','" + enterByPage
                        + "'," + trsNo + "," + recNo + ",'" + orgnBrnCode + "','" + orgnBrnCode + "')");
                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                if (insertReconBalan == 0) {
                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                }
                recNo = fTSPosting43CBSBean.getRecNo();
                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                        + " VALUES('" + uriGl + "',1,'" + dt + "','" + dt + "',0," + drAmt + ",8,3,3,'Litigation Charges on NPA of " + npaAcNo + "','system','Y','" + enterByPage
                        + "'," + trsNo + "," + recNo + ",'" + orgnBrnCode + "','" + orgnBrnCode + "')");
                insertReconBalan = insertReconBalanQuery.executeUpdate();
                if (insertReconBalan == 0) {
                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                }

                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + drAmt + " WHERE ACNO= '" + oirHead + "'");
                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                if (updateReconBalan == 0) {
                    throw new ApplicationException("Value doesn't updated in reconbalan");
                }

                Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + drAmt + " WHERE ACNO= '" + uriGl + "'");
                Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                if (updateReconBalanUri == 0) {
                    throw new ApplicationException("Value doesn't updated in reconbalan");
                }
            }
            return "true";
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String insertInterestHistory(String actNature, String acno, String dt, int ty, double crAmt,
            double drAmt, String userName) throws ApplicationException {
        try {
            double amt = 0;
            int n = 0;
            if (ty == 0) {
                amt = crAmt;
            } else if (ty == 1) {
                amt = -drAmt;
            }
            if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                n = em.createNativeQuery("insert into td_interesthistory (acno,voucherno,interest,dt,fromdt,todt,intopt) "
                        + "values('" + acno + "', 0, " + amt + ", '" + dt + "', '" + dt + "', '" + dt + "', 'T')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in td_interesthistory insertion.");
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                n = em.createNativeQuery("insert into rd_interesthistory (acno,interest,todt,dt,remarks,enterby,vch_no,"
                        + "from_dt,int_opt) values('" + acno + "', " + amt + ", '" + dt + "', '" + dt + "', "
                        + "'Manual Interest Posting', '" + userName + "', 0, '" + dt + "', '')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in rd_interesthistory insertion.");
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                n = em.createNativeQuery("insert into dds_interesthistory(acno,interest,dt,fromdate,todt,trantime,"
                        + "enterby) values('" + acno + "'," + amt + ",'" + dt + "','" + dt + "''" + dt + "',"
                        + "now(),'" + userName + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in dds_interesthistoryy insertion.");
                }
            }
        } catch (Exception ex) {
            if (ex.getMessage().toLowerCase().contains("mysqlintegrityconstraintviolationexception")) {
                throw new ApplicationException("You can not post manual duplicate entry for A/c: " + acno);
            } else {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "TRUE";
    }

    public String cbsSecAuthVal(String sAuthFlag, double sTranAmt, Integer sty, String sCheckBy, String sMsg, String orgnCode)
            throws ApplicationException {
        double sAuthValue = 0.0d;
        String msg = "";
        try {
            List sauthValueList = em.createNativeQuery("SELECT SAUTHVALUE FROM parameterinfo WHERE BRNCODE=cast(" + orgnCode + " as unsigned)").getResultList();

            if (sauthValueList.size() > 0) {
                Vector sauthValueVect = (Vector) sauthValueList.get(0);
                sAuthValue = Double.parseDouble(sauthValueVect.get(0).toString());
            }
            if (sAuthFlag.equalsIgnoreCase("Y") && sTranAmt > sAuthValue && sty == 1 && sCheckBy.equalsIgnoreCase("")) {
                msg = "TRUE";
                if (sMsg.equalsIgnoreCase("TRUE")) {
                    msg = "PLEASE VERIFY THE FIRST AUTHORIZATION";
                }

            } else {
                msg = "FALSE";
            }
            return msg;

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

    }

    public String cbsAuthTransferObcProcess(String billNo, String dt, String instNo, String enterBy, Float trsno, Float recno,
            double cramt, double dramt, String orgnBrCode) throws ApplicationException {

        String CUSTNAME = "";
        String acno = "";
        try {
            List acnoList = em.createNativeQuery("select ifnull(acno,'') from bill_obcbooking Where Billno=" + billNo + " AND Fyear=extract(year from '" + dt + "')  and billType='BC' and instno = '" + instNo + "' and substring(acno,3,2) = '" + orgnBrCode + "'").getResultList();
            if (acnoList.size() > 0) {
                Vector acnoVect = (Vector) acnoList.get(0);
                acno = acnoVect.get(0).toString();
            }
            List dateList = em.createNativeQuery("select DATE_FORMAT(DATE_FORMAT('" + dt + "','%Y-%m-%d'),'%Y-%m-%d')").getResultList();
            if (dateList.size() > 0) {
                Vector dateVect = (Vector) dateList.get(0);
                dt = dateVect.get(0).toString();
            }
            Integer billObcProcessed = em.createNativeQuery("Insert bill_obcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,"
                    + "BankAddr,InstNo,InstAmount,Comm,Postage,Coll_BnkCharge,CreditedAmount, InFavourOf,InstDt,Dt,instEntryDt,Status,EnterBy,"
                    + "TrsNo,RecNo,Coll_AlphaCode,Coll_BankName,Coll_BankAddr,billdetaindt) Select billNo,Acno,FYear,BillType,Alphacode,BankName,"
                    + "BankAddr,InstNo,InstAmount,Comm,Postage,coll_BankCharge," + cramt + ",InFavourOf,InstDt,'" + dt + "','" + dt + "','R',"
                    + "UPPER('" + enterBy + "')," + trsno + "," + recno + ",Coll_AlphaCode,Coll_BankName,Coll_BankAddr,BCRealizationDT From bill_obcbooking "
                    + "Where Billno=" + billNo + " and Fyear=extract(year from '" + dt + "') and billType='BC' and instno = '" + instNo + "'").executeUpdate();

            if (billObcProcessed <= 0) {
                return "Data is not inserted into Bill_ObcProcessed";
            }
            Integer billObcBooking = em.createNativeQuery("Delete From bill_obcbooking Where Billno=" + billNo + " and Fyear=extract(year from '" + dt + "') and "
                    + "billType='BC' and instno = '" + instNo + "'").executeUpdate();

            if (billObcBooking <= 0) {
                return "Data is not Deleted From Bill_ObcBooking";

            }
            if (acno.equalsIgnoreCase("GLHO")) {
                Integer billHOOthers = em.createNativeQuery("insert bill_hoothers (FYear,InstNo,Acno,CustName,InfavourOf,PayableAt,"
                        + "Amount,Enterby,Auth,AuthBy,Dt,Status,Trantype, Seqno,Comm,Billtype,Ty,Recno,OriginDt)Select FYear,instno,Acno,"
                        + "'" + CUSTNAME + "',InFavourOf,Coll_Alphacode," + dramt + ",'" + enterBy + "','Y','" + enterBy + "','" + dt + "','Paid', 2, billno,Comm + Postage, "
                        + "billtype,1," + recno + ",billdetaindt From bill_obcprocessed Where Billno=" + billNo + " and Fyear=extract(year from '" + dt + "') and "
                        + "billType='BC' and instno = '" + instNo + "'").executeUpdate();

                if (billHOOthers < 0) {
                    return "Data is not inserted into Bill_HOOthers";
                }
            }
            return "True";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String cbsAuthTransferCreditSBCACashGrd(String dt, String orgnCode) throws ApplicationException {
        String custName;
        String instNo;
        String details;
        Float voucherNo;
        try {
            List reconTrfdList = em.createNativeQuery("SELECT ACNO,ifnull(CUSTNAME,'NOT AVAIL') AS CUSTNAME,TRANTYPE,ifnull(INSTNO,''), ifnull(INSTDT,'19000101'),CRAMT,DRAMT,"
                    + "AUTH,ifnull(ENTERBY,''),ifnull(RECNO,0),ifnull(PAYBY,3.0),TY,ifnull(TRSNO,0),ifnull(DETAILS,''),ifnull(TRANDESC,0),ifnull(IY,3),ifnull(tdacno,''),"
                    + "ifnull(VOUCHERNO,0),ORG_BRNID,DEST_BRNID, ifnull(VALUEDT,''),ADVICENO,ADVICEBRNCODE FROM recon_trf_d "
                    + "WHERE (AUTH IS NULL OR AUTH = 'N') AND DT = '" + dt + "' AND TRANTYPE IN (2,8) AND "
                    + "ORG_BRNID='" + orgnCode + "'").getResultList();

            if (reconTrfdList.size() > 0) {
                for (int i = 0; i < reconTrfdList.size(); i++) {
                    Vector reconTrfdVect = (Vector) reconTrfdList.get(i);
                    String acno = reconTrfdVect.get(0).toString();
                    if (reconTrfdVect.get(1) == null) {
                        custName = "";
                    } else {
                        custName = reconTrfdVect.get(1).toString();
                    }
                    int tranType = Integer.parseInt(reconTrfdVect.get(2).toString());
                    if (reconTrfdVect.get(3) == null) {
                        instNo = "";
                    } else {
                        instNo = reconTrfdVect.get(3).toString();
                    }
                    String instDt = reconTrfdVect.get(4).toString();
                    double cramt = Double.parseDouble(reconTrfdVect.get(5).toString());
                    double dramt = Double.parseDouble(reconTrfdVect.get(6).toString());
                    String enterBy = reconTrfdVect.get(8).toString();

                    float recno = Float.parseFloat(reconTrfdVect.get(9).toString());
                    float payBy = Float.parseFloat(reconTrfdVect.get(10).toString());
                    int ty = Integer.parseInt(reconTrfdVect.get(11).toString());
                    int trsno = Integer.parseInt(reconTrfdVect.get(12).toString());

                    if (reconTrfdVect.get(13) == null) {
                        details = "";
                    } else {
                        details = reconTrfdVect.get(13).toString();
                    }
                    int tranDesc = Integer.parseInt(reconTrfdVect.get(14).toString());
                    int iy = Integer.parseInt(reconTrfdVect.get(15).toString());
                    String checkBY = reconTrfdVect.get(16).toString();

                    if (reconTrfdVect.get(17) == null || reconTrfdVect.get(17).equals("")) {
                        voucherNo = 0.0f;
                    } else {
                        voucherNo = Float.parseFloat(reconTrfdVect.get(17).toString());
                    }

                    String orgBrId = reconTrfdVect.get(18).toString();
                    String destBrId = reconTrfdVect.get(19).toString();
                    String valueDt = reconTrfdVect.get(20).toString();
                    String adviceNo = reconTrfdVect.get(21).toString();
                    String adviceBrnCode = reconTrfdVect.get(22).toString();

                    String acNature = fTSPosting43CBSBean.getAccountNature(acno);
                    double balance = 0d;
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List balanceList = em.createNativeQuery("SELECT round(ifnull(BALANCE,0),2) FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (balanceList.size() > 0) {
                            Vector balanceVect = (Vector) balanceList.get(0);
                            balance = Double.parseDouble(balanceVect.get(0).toString());
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)
                            || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        List balanceList = em.createNativeQuery("SELECT round(ifnull(BALANCE,0),2) FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();

                        if (balanceList.size() > 0) {
                            Vector balanceVect = (Vector) balanceList.get(0);
                            balance = Double.parseDouble(balanceVect.get(0).toString());
                        }
                    } else {
                        List balanceList = em.createNativeQuery("SELECT round(ifnull(BALANCE,0),2) FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();

                        if (balanceList.size() > 0) {
                            Vector balanceVect = (Vector) balanceList.get(0);
                            balance = Double.parseDouble(balanceVect.get(0).toString());
                        }
                    }
                    if (!instNo.equalsIgnoreCase("") || voucherNo != 0) {
                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            instNo = voucherNo.toString();
                        }
                    } else {
                        if (payBy == 1.0) {
                            instNo = "CHEQUE";
                        } else if (payBy == 2.0) {
                            instNo = "W/S";
                        } else if (payBy == 3.0) {
                            instNo = "VOUCHER";
                        }
                    }
                    if (ty == 0) {
                        dramt = 0.0f;
                    } else {
                        cramt = 0.0f;
                    }

                    Integer tranTemp = em.createNativeQuery("INSERT INTO tran_temp (TRSNO,ACNO,CUSTNAME,CRAMT,DRAMT,BALANCE,INSTNO, INSTDT, AUTH,"
                            + "ENTERBY,RECNO,DETAILS,PAYBY,CLGREASON,IY,CHECKBY,ORG_BRNID,DEST_BRNID,ty,TranType,valueDt,adviceno,advicebrncode) VALUES(" + trsno + ",'" + acno + "','" + custName + "',"
                            + "" + cramt + "," + dramt + "," + balance + ",'" + instNo + "','" + instDt + "','N','" + enterBy + "'," + recno + ",'" + details + "'," + payBy + "," + tranDesc + ","
                            + "" + iy + ",'" + checkBY + "','" + orgBrId + "','" + destBrId + "'," + ty + "," + tranType + ",'" + valueDt + "','" + adviceNo + "','" + adviceBrnCode + "')").executeUpdate();

                    if (tranTemp < 0) {
                        return "Data is not inserted into TRAN_TEMP";
                    }
                }
            }
            return "TRUE111";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

    }

    public String authTransferTrans(Float trsNo, Integer ty, Float recno, String orgnBrCode) throws ApplicationException {
        String successFlag = "";
        String acNature = "";
        double balance = 0f;
        List balancList;
        List balanceList;
        try {
            List reconTrfList = em.createNativeQuery("SELECT ifnull(R.ACNO,''),R.CRAMT,R.DRAMT,R.RECNO,R.AUTHBY,R.DT,R.TRANDESC,R.DETAILS,R.IY,"
                    + "R.ENTERBY, R.VALUEDT FROM recon_trf_d R  WHERE AUTH='Y' AND TRSNO=cast(" + trsNo + " as unsigned) AND TY = " + ty + " AND RECNO = " + recno + " and org_brnid = '" + orgnBrCode + "'").getResultList();
            successFlag = "YES";
            if (reconTrfList.isEmpty()) {
                successFlag = "FALSE";
            } else if (reconTrfList.size() > 0) {
                Vector reconTrfVect = (Vector) reconTrfList.get(0);
                String acno = reconTrfVect.get(0).toString();
                double cramt = Double.parseDouble(reconTrfVect.get(1).toString());
                double dramt = Double.parseDouble(reconTrfVect.get(2).toString());

                recno = Float.parseFloat(reconTrfVect.get(3).toString());
                String authBy = reconTrfVect.get(4).toString();

                int tranDesc = Integer.parseInt(reconTrfVect.get(6).toString());
                int iy = Integer.parseInt(reconTrfVect.get(8).toString());
                String enterBy = reconTrfVect.get(9).toString();
                String valueDt = reconTrfVect.get(10).toString();

                acNature = fTSPosting43CBSBean.getAccountNature(acno);
                if (acno.equalsIgnoreCase("") || acNature.equalsIgnoreCase("")) {
                    successFlag = "PLEASE CHECK FOR RECNO,TOKENNO,SUBTOKENNO";
                    return "PLEASE CHECK FOR RECNO,TOKENNO,SUBTOKENNO";
                }

                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)
                        || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNature.equalsIgnoreCase(CbsConstant.SS_AC) || acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {

                    balanceList = em.createNativeQuery("SELECT BALANCE FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                    if (!balanceList.isEmpty()) {
                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    if (ty == 0) {
                        List acnoList = em.createNativeQuery("SELECT ACNO FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            List cbalanceList = em.createNativeQuery("SELECT CLEAREDBALANCE FROM reconbalan WHERE ACNO='" + acno + "'").getResultList();
                            if (!cbalanceList.isEmpty()) {
                                Vector balanceVect = (Vector) cbalanceList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET CLEAREDBALANCE = ifnull(CLEAREDBALANCE,0) + "
                                    + "ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0),BALANCE =ifnull(BALANCE,0) + ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0)"
                                    + " WHERE ACNO='" + acno + "'").executeUpdate();
                            if (reconBalan < 0) {
                                //ut.rollback();
                                return "Data is not updated into RECONBALAN";
                            }
                        } else {
                            Integer reconBalan = em.createNativeQuery("INSERT INTO reconbalan(ACNO,dt,BALANCE,CLEAREDBALANCE)VALUES"
                                    + "('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0),ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0))").executeUpdate();
                            if (reconBalan < 0) {
                                //ut.rollback();
                                return "Data is not inserted into RECONBALAN";
                            }
                            balancList = em.createNativeQuery("SELECT ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0)").getResultList();
                            if (!balancList.isEmpty()) {
                                Vector balanceVect = (Vector) balancList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    balanceList = em.createNativeQuery("SELECT BALANCE FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                    if (!balanceList.isEmpty()) {

                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    if (ty == 0) {
                        List acnoList = em.createNativeQuery("SELECT ACNO FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            List cbalanceList = em.createNativeQuery("SELECT CLEAREDBALANCE FROM ca_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                            if (!cbalanceList.isEmpty()) {
                                Vector balanceVect = (Vector) cbalanceList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            Integer reconBalan = em.createNativeQuery("UPDATE ca_reconbalan SET CLEAREDBALANCE = ifnull(CLEAREDBALANCE,0) + "
                                    + "ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0),BALANCE =ifnull(BALANCE,0) + ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0)"
                                    + " WHERE ACNO='" + acno + "'").executeUpdate();
                            if (reconBalan < 0) {
                                //ut.rollback();
                                return "Data is not updated into CA_RECONBALAN";
                            }
                        } else {
                            Integer reconBalan = em.createNativeQuery("INSERT INTO ca_reconbalan(ACNO,dt,BALANCE,CLEAREDBALANCE)VALUES"
                                    + "('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0),ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0))").executeUpdate();
                            if (reconBalan < 0) {
                                //ut.rollback();
                                return "Data is not inserted into CA_RECONBALAN";
                            }
                            balancList = em.createNativeQuery("SELECT ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0)").getResultList();
                            if (!balancList.isEmpty()) {
                                Vector balanceVect = (Vector) balancList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    balanceList = em.createNativeQuery("SELECT BALANCE FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                    if (!balanceList.isEmpty()) {

                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    if (ty == 0) {
                        List acnoList = em.createNativeQuery("SELECT ACNO FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            List cbalanceList = em.createNativeQuery("SELECT CLEAREDBALANCE FROM td_reconbalan WHERE ACNO='" + acno + "'").getResultList();
                            if (!cbalanceList.isEmpty()) {
                                Vector balanceVect = (Vector) cbalanceList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            Integer reconBalan = em.createNativeQuery("UPDATE td_reconbalan SET CLEAREDBALANCE = ifnull(CLEAREDBALANCE,0) + "
                                    + "ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0),BALANCE =ifnull(BALANCE,0) + ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0)"
                                    + " WHERE ACNO='" + acno + "'").executeUpdate();
                            if (reconBalan < 0) {
                                //ut.rollback();
                                return "Data is not updated into TD_RECONBALAN";
                            }
                        } else {
                            Integer reconBalan = em.createNativeQuery("INSERT INTO td_reconbalan(ACNO,dt,BALANCE,CLEAREDBALANCE) VALUES('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0),ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0))").executeUpdate();
                            if (reconBalan < 0) {
                                //ut.rollback();
                                return "Data is not inserted into TD_RECONBALAN";
                            }
                            balancList = em.createNativeQuery("SELECT ifnull(" + cramt + ",0) - ifnull(" + dramt + ",0)").getResultList();
                            if (!balancList.isEmpty()) {
                                Vector balanceVect = (Vector) balancList.get(0);
                                balance = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                    }
                }

                if (ty == 0) {
                    balancList = em.createNativeQuery("SELECT ifnull(" + balance + ",0) +ifnull(" + cramt + ",0) -ifnull(" + dramt + ",0)").getResultList();
                    if (!balancList.isEmpty()) {
                        Vector balanceVect = (Vector) balancList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                }
                //}
                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    Integer sbRecon = em.createNativeQuery("INSERT INTO recon(ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, IY, "
                            + "INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID,instdt,valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY, "
                            + "R.AUTH, R.RECNO,R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO,"
                            + "R.ORG_BRNID,R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (sbRecon <= 0) {
                        return "Data is not inserted into RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    Integer ddsTran = em.createNativeQuery("INSERT INTO ddstransaction ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE,"
                            + " DETAILS, IY, RECEIPTNO, ENTERBY, AUTH, RECNO,PAYBY, AUTHBY, TRSNO, TRANTIME, TOKENNO,TOKENPAIDBY,"
                            + " SUBTOKENNO,tran_id,term_id,org_brnid,dest_brnid,trandesc,tokenpaid,favorof,INSTNO,CheckBy,instdt,valuedt) SELECT "
                            + "R.ACNO,R.TY,R.DT,R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY, "
                            + "R.AUTH, R.RECNO,R.PAYBY, R.AUTHBY, R.TRSNO, R.TRANTIME, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO,"
                            + "R.tran_id,R.TERM_ID,R.ORG_BRNID,R.dest_brnid,R.trandesc,'','',R.INSTNO,'',R.instdt,R.valuedt FROM recon_trf_d R WHERE "
                            + "R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (ddsTran <= 0) {
                        return "Data is not inserted into DDSTRANSACTION";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    Integer ofRecon = em.createNativeQuery("INSERT INTO of_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, "
                            + "DETAILS, ENTERBY, AUTH, RECNO,AUTHBY, TRSNO,TRANTIME, TRANDESC, TOKENNO, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID, valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS,R.ENTERBY, "
                            + "R.AUTH, R.RECNO,R.AUTHBY, R.TRSNO,R.TRANTIME, R.TRANDESC, R.TOKENNO, R.SUBTOKENNO, R.ORG_BRNID, "
                            + "R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY =" + ty + "").executeUpdate();
                    if (ofRecon <= 0) {
                        return "Data is not inserted into OF_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.SS_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {

                    if (ty == 0) {
                        int intPostOnDeposit = 0;
                        intPostOnDeposit = Integer.parseInt(fTSPosting43CBSBean.getCodeByReportName("INT_POST_DEPOSIT"));
                        if (intPostOnDeposit != 0) {
                            LoanIntCalcList it = loanFacade.txnLoanIntCalc(reconTrfVect.get(5).toString(), acno, orgnBrCode);
                            double intAmt = it.getTotalInt() * -1;
                            double roi = it.getRoi();

                            if (intAmt != 0) {
                                String glInt = loanFacade.getGlHeads(acno.substring(2, 4));
                                glInt = orgnBrCode + glInt + "01";

                                float trsInt = fTSPosting43CBSBean.getTrsNo();
                                float recnoInt = fTSPosting43CBSBean.getRecNo();
                                Query insertQuery = em.createNativeQuery("INSERT loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " values ('" + acno + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + reconTrfVect.get(5).toString() + "',"
                                        + intAmt + ",0,8,3,3,'INT.UP TO " + reconTrfVect.get(5).toString() + " @" + roi + "%" + "','" + enterBy + "','Y','" + authBy + "',"
                                        + trsInt + "," + recnoInt + ",'" + orgnBrCode + "','" + orgnBrCode + "')");

                                Integer insertRecon = insertQuery.executeUpdate();
                                if (insertRecon == 0) {
                                    return "Value not inserted in Loan_Recon";
                                }

                                recnoInt = fTSPosting43CBSBean.getRecNo();
                                Query insertGlReconQuery = em.createNativeQuery("INSERT gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + glInt + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + reconTrfVect.get(5).toString() + "'," + intAmt + ",0,"
                                        + "8,3,3,'Int Up To " + reconTrfVect.get(5).toString() + " of " + acno + "','" + enterBy + "','Y','" + authBy
                                        + "'," + trsInt + "," + recnoInt + ",'" + orgnBrCode + "','" + orgnBrCode + "')");
                                Integer insertGlRecon = insertGlReconQuery.executeUpdate();
                                if (insertGlRecon == 0) {
                                    return "Value doesn't inserted in GL_RECON";
                                }
                            }

                            String nextCalcDt = CbsUtil.dateAdd(ymd.format(ymmd.parse(reconTrfVect.get(5).toString())), 1);
                            Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '"
                                    + reconTrfVect.get(5).toString() + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acno + "'");
                            Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                            if (updateCaRecon == 0) {
                                throw new ApplicationException("Interest not Posted successfully");
                            }
                        }
                    }

                    Integer loanRecon = em.createNativeQuery("INSERT INTO loan_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE,"
                            + " DETAILS, IY, INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO, SUBTOKENNO,"
                            + "ORG_BRNID,DEST_BRNID,instdt,valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS, "
                            + "R.IY, R.INSTNO, R.ENTERBY, R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC,"
                            + " R.TOKENNO, R.SUBTOKENNO, R.ORG_BRNID,R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND "
                            + "AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (loanRecon <= 0) {
                        return "Data is not inserted into LOAN_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    Integer glRecon = em.createNativeQuery("INSERT INTO gl_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, IY, "
                            + "INSTNO, ENTERBY, AUTH, RECNO, PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID,instdt,valuedt,adviceno,advicebrncode)SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY,"
                            + " R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO , "
                            + "R.ORG_BRNID, R.DEST_BRNID,R.instdt,R.valuedt,R.adviceno,R.advicebrncode FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (glRecon <= 0) {
                        return "Data is not inserted into GL_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    Integer rdRecon = em.createNativeQuery("INSERT INTO rdrecon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, IY, "
                            + "INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,ORG_BRNID,"
                            + "DEST_BRNID,instdt,valuedt)SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO, R.ENTERBY,"
                            + " R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY, R.SUBTOKENNO ,R.ORG_BRNID,"
                            + " R.DEST_BRNID,R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (rdRecon <= 0) {
                        return "Data is not inserted into RDRECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    Integer caRecon = em.createNativeQuery("INSERT INTO ca_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE, TRANTYPE, DETAILS, IY,"
                            + " INSTNO, ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO, ORG_BRNID,"
                            + " DEST_BRNID,instdt,valuedt) SELECT R.ACNO , R.TY, R.DT, R.DRAMT, R.CRAMT, " + balance + ",R.TRANTYPE , R.DETAILS, R.IY, R.INSTNO,"
                            + " R.ENTERBY, R.AUTH, R.RECNO, R.PAYBY,R.AUTHBY, R.TRSNO, R.TRANTIME, R.TRANDESC, R.TOKENNO,R.TOKENPAIDBY,"
                            + " R.SUBTOKENNO ,R.ORG_BRNID, R.DEST_BRNID, R.instdt,R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND AUTH = 'Y'"
                            + " AND TY = " + ty + "").executeUpdate();
                    if (caRecon <= 0) {
                        return "Data is not inserted into CA_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    Integer tdRecon = em.createNativeQuery("INSERT INTO td_recon ( ACNO , TY, DT, DRAMT, CRAMT, BALANCE,TRANTYPE, DETAILS, ENTERBY,"
                            + " AUTH, RECNO,AUTHBY,TRSNO, TRANTIME, TRANDESC, TOKENNO, SUBTOKENNO,intflag,voucherno,ORG_BRNID,DEST_BRNID,valuedt) SELECT R.ACNO , R.TY,"
                            + " R.DT, R.DRAMT, R.CRAMT," + balance + ",R.TRANTYPE , R.DETAILS, R.ENTERBY, R.AUTH, R.RECNO,R.AUTHBY,R.TRSNO, R.TRANTIME,"
                            + " R.TRANDESC, R.TOKENNO, R.SUBTOKENNO,R.intflag,R.voucherno ,R.ORG_BRNID,R.DEST_BRNID, R.valuedt FROM recon_trf_d R WHERE R.RECNO=" + recno + " AND R.TRSNO = " + trsNo + " AND "
                            + "AUTH = 'Y' AND TY = " + ty + "").executeUpdate();
                    if (tdRecon <= 0) {
                        return "Data is not inserted into TD_RECON";
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    Integer npaRecon = em.createNativeQuery("INSERT INTO npa_recon(ACNO,TY,DT,DRAMT,CRAMT,TRANTYPE,ENTERBY,AUTH,RECNO,PAYBY,AUTHBY,"
                            + "TRANDESC,IY,INTAMT,STATUS,ORG_BRNID,DEST_BRNID,VALUEDT) VALUES('" + acno + "',1,CURRENT_TIMESTAMP,0," + cramt + ",0,'" + enterBy
                            + "','Y'," + recno + ",3,'" + authBy + "','" + tranDesc + "'," + iy + ",0,'','" + orgnBrCode + "','" + orgnBrCode
                            + "','" + valueDt + "')").executeUpdate();
                    if (npaRecon <= 0) {
                        return "Data is not inserted into NPA_RECON";
                    }
                }
            }
            return successFlag;

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

    }

    public String cbsAuthGetTransfer(String date, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List checkList = null;
            String CustName = null;
            checkList = em.createNativeQuery("SELECT DISTINCT ACNO FROM recon_trf_d WHERE (CUSTNAME IS NULL OR CUSTNAME='') AND "
                    + "DT='" + date + "' AND ORG_BRNID='" + orgnBrCode + "'").getResultList();
            if (checkList.size() > 0) {
                for (int i = 0; i < checkList.size(); i++) {
                    Vector secLst = (Vector) checkList.get(i);
                    String TMPACNO = secLst.get(0).toString();
                    String ACNATURE = fTSPosting43CBSBean.getAccountNature(TMPACNO);
                    if (ACNATURE.equalsIgnoreCase("FALSE")) {
                        ut.rollback();
                        return "Account nature does not exists !!!.";
                    }
                    if (ACNATURE.equalsIgnoreCase(CbsConstant.FIXED_AC) || (ACNATURE.equalsIgnoreCase(CbsConstant.MS_AC))) {
                        List checkList2 = em.createNativeQuery("Select custname from td_accountmaster where acno='" + TMPACNO + "'").getResultList();
                        if (checkList2.isEmpty()) {
                            ut.rollback();
                            return "Customer Name does not exists !!!.";
                        }
                        Vector secLst2 = (Vector) checkList2.get(0);
                        CustName = secLst2.get(0).toString();
                        Query updRecon = em.createNativeQuery("update recon_trf_d set custname = '" + CustName + "'  WHERE ACNO='" + TMPACNO + "' AND ORG_BRNID='" + orgnBrCode + "'");
                        int var = updRecon.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Custname doesn't updated to RECON_TRF_D.";
                        }
                    } else if (ACNATURE.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        List checkList2 = em.createNativeQuery("select custname from td_accountmaster where acno in (select acno from td_vouchmst "
                                + "WHERE OFACNO='" + TMPACNO + "')").getResultList();
                        if (checkList2.isEmpty()) {
                            ut.rollback();
                            return "Customer Name does not exists !!!.";
                        }
                        Vector secLst2 = (Vector) checkList2.get(0);
                        CustName = secLst2.get(0).toString();
                        Query updRecon = em.createNativeQuery("UPDATE recon_trf_d SET CUSTNAME = '" + CustName + "'  WHERE ACNO='" + TMPACNO
                                + "' AND ORG_BRNID='" + orgnBrCode + "'");
                        int var = updRecon.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Custname doesn't updated to RECON_TRF_D.";
                        }
                    } else if (ACNATURE.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        List checkList2 = em.createNativeQuery("SELECT ACNAME FROM gltable WHERE ACNO='" + TMPACNO + "'").getResultList();
                        if (checkList2.isEmpty()) {
                            ut.rollback();
                            return "Customer Name does not exists !!!.";
                        }
                        Vector secLst2 = (Vector) checkList2.get(0);
                        CustName = secLst2.get(0).toString();
                        Query updRecon = em.createNativeQuery("UPDATE recon_trf_d SET CUSTNAME = '" + CustName + "'  WHERE ACNO='" + TMPACNO
                                + "' AND ORG_BRNID='" + orgnBrCode + "'");
                        int var = updRecon.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Custname doesn't updated to RECON_TRF_D.";
                        }
                    } else {
                        List checkList2 = em.createNativeQuery("SELECT CUSTNAME FROM accountmaster WHERE ACNO='" + TMPACNO + "'").getResultList();
                        if (checkList2.isEmpty()) {
                            ut.rollback();
                            return "Customer Name does not exists !!!.";
                        }
                        Vector secLst2 = (Vector) checkList2.get(0);
                        CustName = secLst2.get(0).toString();
                        Query updRecon = em.createNativeQuery("UPDATE recon_trf_d SET CUSTNAME = '" + CustName + "'  WHERE ACNO='" + TMPACNO + "' AND ORG_BRNID='" + orgnBrCode + "'");
                        int var = updRecon.executeUpdate();
                        if (var <= 0) {
                            ut.rollback();
                            return "Custname doesn't updated to RECON_TRF_D.";
                        }
                    }
                }
            }
            Query deltrtmp = em.createNativeQuery("delete from tran_temp where org_brnid='" + orgnBrCode + "'");
            deltrtmp.executeUpdate();
            String msg = cbsAuthTransferCreditSBCACashGrd(date, orgnBrCode);
            if (msg.equalsIgnoreCase("TRUE111")) {
                msg = "TRUE1";
            } else {
                ut.rollback();
                msg = "ERROR OCCURED";
            }
            ut.commit();
            return msg;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String cbsAcStatus(String acNo) throws ApplicationException {
        String acNature = null;
        String accStatus = null;

        try {
            acNature = fTSPosting43CBSBean.getAccountNature(acNo);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk1 = em.createNativeQuery("select ACCSTATUS FROM td_accountmaster  WHERE ACNO='" + acNo + "'").getResultList();
                if (!chk1.isEmpty()) {
                    Vector ele = (Vector) chk1.get(0);
                    accStatus = ele.get(0).toString();
                }
            } else {
                List chk1 = em.createNativeQuery("select ACCSTATUS FROM accountmaster  WHERE ACNO='" + acNo + "'").getResultList();
                if (!chk1.isEmpty()) {
                    Vector ele = (Vector) chk1.get(0);
                    accStatus = ele.get(0).toString();
                }
            }
            return accStatus;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private String insertIntoBillPO(String detail, String auth, String orgnBrnCode) throws ApplicationException {
        String flag = "";
        try {
            String[] elements = detail.split("~`");

            int msgNoPo = Integer.parseInt(elements[1]);
            int msgBillStart = 110;
            String status = elements[11];
            int msgBillPo = Integer.parseInt(elements[1]);

            int msgBillEnd = 140;
            String datePo = elements[9];
            float seqNoPo = Float.parseFloat(elements[2]);
            String instNoPo = elements[3];

            String billTypePo = elements[4];
            String acNoPo = elements[5];
            String custNamePo = elements[6];

            String payableAtPo = elements[7];
            double amountPo = Double.parseDouble(elements[8]);
            String dtPo = elements[9];
            //originDtPo = elements[9];
            String originDtPo = elements[10];

            int timeLimitPo = Integer.parseInt(elements[12]);
            double commPo = Double.parseDouble(elements[13]);
            int tranTypePo = Integer.parseInt(elements[14]);
            int tyPo = Integer.parseInt(elements[15]);

            String infavourOfPo = elements[16];
            String enterByPo = elements[17];
            String lastUpdateByPo = elements[18];
            float recNoPo = Float.parseFloat(elements[20]);
            flag = fTSPosting43CBSBean.cbsAuthCashCrMakeEntry(msgNoPo, msgBillStart, status, msgBillPo, msgBillEnd, auth, datePo, seqNoPo,
                    instNoPo, billTypePo, acNoPo, custNamePo, payableAtPo, amountPo, dtPo, originDtPo, timeLimitPo, commPo,
                    tranTypePo, tyPo, infavourOfPo, enterByPo, lastUpdateByPo, recNoPo, orgnBrnCode);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

        return flag;
    }
    /*                       End of Xfer Authorization         */

    /*                       Start of Pay Order Authorization         */
    public List getAcType() throws ApplicationException {
        List resultList = new ArrayList();
        try {
            String query = "SELECT DISTINCT INSTNATURE FROM billtypemaster";
            Query selectQuery = em.createNativeQuery(query);
            resultList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public List getUnAuthPoDetails(String date, String orgnBrCode, String acType) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            String query = "";
            if (acType.toUpperCase().equalsIgnoreCase("AD")) {
                query = "Select instno, seqno, billtype, acno, custname, payableat, amount, infavourof, status, enterby, auth, recno "
                        + "from bill_ad where auth = 'N' and dt = '" + date + "' and substring(acno,1,2)= '" + orgnBrCode + "' order by recno";
            } else if (acType.toUpperCase().equalsIgnoreCase("DD")) {
//                query = "Select instno, seqno, billtype, acno, custname, payableat, amount, infavourof, status, enterby, auth, recno "
//                        + "from bill_dd where auth = 'N' and dt = '" + date + "' and org_brnid= '" + orgnBrCode + "' order by recno";
                query = "Select b.instno, b.seqno, b.billtype, b.acno, b.custname, c.ref_desc, b.amount, b.infavourof, b.status, b.enterby, b.auth, b.recno "
                        + "from bill_dd b, cbs_ref_rec_type c where auth = 'N' and dt = '" + date + "' and org_brnid= '" + orgnBrCode + "' "
                        + "and b.payableat = c.ref_code order by recno";
            } else if (acType.toUpperCase().equalsIgnoreCase("PO")) {
                query = "Select instno, seqno, billtype, acno, custname, payableat, amount, infavourof, status, enterby, auth, recno "
                        + "from bill_po where auth = 'N' and dt = '" + date + "' and substring(acno,1,2)= '" + orgnBrCode + "' order by recno";
            }
            Query selectQuery = em.createNativeQuery(query);
            resultList = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public String poAuthorization(Float recNo, String acNo, Float seqNo, String billType, double amt, String status, String dt, String auth1, String brCode)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String message = "";
            int year = Integer.parseInt(dt.substring(0, 4));
            List glReconLt = em.createNativeQuery("select ifnull(auth,'N') from gl_recon where recno = " + recNo + " and dt  = '" + dt + "' AND org_brnid = '" + brCode + "'").getResultList();
            if (glReconLt.isEmpty()) {
                throw new ApplicationException("Entry does not Exist");
            }
            Vector glReconLtVec = (Vector) glReconLt.get(0);
            String auth = glReconLtVec.get(0).toString();
            if (auth.equalsIgnoreCase("N")) {
                if (acNo.equalsIgnoreCase("CASH")) {
                    throw new ApplicationException("Cash Entry has not been Authorized");
                } else {
                    throw new ApplicationException("Transfer Entry has not been Authorized");
                }
            }
            String addEntryFlag = "FALSE";
            List reportLt = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where reportname='AD ENTRY'").getResultList();
            Vector reportLtVec = (Vector) reportLt.get(0);
            if (Integer.parseInt(reportLtVec.get(0).toString()) == 1) {
                addEntryFlag = "TRUE";
            }

            List billTyMasterLt = em.createNativeQuery("select InstNature from billtypemaster where instcode = '" + billType + "'").getResultList();
            if (billTyMasterLt.isEmpty()) {
                throw new ApplicationException("Data does not exist in BillTypeMaster");
            }
            Vector billTyMasterLtVec = (Vector) billTyMasterLt.get(0);
            String billNatureMsg = billTyMasterLtVec.get(0).toString();

            String tableName = "";
            if (billType.equalsIgnoreCase("PO")) {
                tableName = "bill_po";
            } else if (billNatureMsg.equalsIgnoreCase("DD")) {
                tableName = "bill_dd";
            } else if (billNatureMsg.equalsIgnoreCase("TPO")) {
                tableName = "bill_tpo";
            } else if (billNatureMsg.equalsIgnoreCase("AD")) {
                tableName = "bill_ad";
            }
            if (billNatureMsg.equalsIgnoreCase("PO")) {
                List billPoList = em.createNativeQuery("SELECT AUTH FROM bill_po where dt = '" + dt + "' and recno = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND STATUS='ISSUED' AND AUTH='Y'").getResultList();
                if (!billPoList.isEmpty()) {
                    throw new ApplicationException("PO is already Authorized.");
                }
            }
            long instNo = 0;
            if (addEntryFlag.equalsIgnoreCase("TRUE")) {
                instNo = (long) getBillNo(billType, amt, brCode);
            } else {
                if (!billNatureMsg.equalsIgnoreCase("AD")) {
                    instNo = (long) getBillNo(billType, amt, brCode);
                }
            }
            seqNo = 1.0f;
            if (tableName.equalsIgnoreCase("bill_dd")) {
                List billPoLt = em.createNativeQuery("select max(seqno)+1 from " + tableName + " where fyear = " + year
                        + " AND org_brnid='" + brCode + "' and billtype = '" + billType + "'").getResultList();
                if (billPoLt.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Bill Table");
                }
                Vector billPoLtVec = (Vector) billPoLt.get(0);
                seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
            } else {
                List billPoLt = em.createNativeQuery("select max(seqno)+1 from " + tableName + " where fyear = " + year
                        + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
                if (billPoLt.isEmpty()) {
                    throw new ApplicationException("Data does not exist in Bill Table");
                }
                Vector billPoLtVec = (Vector) billPoLt.get(0);
                seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
            }

            System.out.println("Pay Order number is >>>>>>>" + instNo + " and Generated Sequence number is >>>>>>" + seqNo);
            System.out.println("Logged in user is >>>>>>>" + auth1 + " and logged in branch is >>>>>>" + brCode);

            String ddNoVarchar = String.valueOf(instNo);
            String seqNoVarchar = seqNo.toString();

            if (ddNoVarchar.contains(".")) {
                ddNoVarchar = ddNoVarchar.substring(0, ddNoVarchar.indexOf("."));
            }
            if (seqNoVarchar.contains(".")) {
                seqNoVarchar = seqNoVarchar.substring(0, seqNoVarchar.indexOf("."));
            }

            if (addEntryFlag.equalsIgnoreCase("FALSE") && billNatureMsg.equalsIgnoreCase("AD")) {
                Query updateQuery = em.createNativeQuery("update bill_ad set auth = 'Y',authby = '" + auth1 + "' where  dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='"
                        + brCode + "' and recno = " + recNo + "");
                if (updateQuery.executeUpdate() <= 0) {
                    throw new ApplicationException("Problem in PO authorization");
                }
                message = billType + " Authorized";
            } else {
                if (tableName.equalsIgnoreCase("bill_dd")) {
                    Query updateQuery = em.createNativeQuery("update " + tableName + " set auth = 'Y',seqno = " + seqNo + " ,instno = " + instNo + ", authby = '"
                            + auth1 + "' where dt = '" + dt + "' AND org_brnid='" + brCode + "' and recno = " + recNo + "");
                    if (updateQuery.executeUpdate() <= 0) {
                        throw new ApplicationException("Problem in PO authorization");
                    }
                } else {
                    Query updateQuery = em.createNativeQuery("update " + tableName + " set auth = 'Y',seqno = " + seqNo + " ,instno = " + instNo + ", authby = '"
                            + auth1 + "' where dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode + "' and recno = " + recNo + "");
                    if (updateQuery.executeUpdate() <= 0) {
                        throw new ApplicationException("Problem in PO authorization");
                    }
                }
                message = "Issued, Seq No = " + seqNoVarchar + ", Instrument No = " + ddNoVarchar;
            }
            ut.commit();
            return message;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public float getBillNo(String instCode, double amt, String brCode) throws ApplicationException {
        try {
            String query = "select ifnull(series,'N/A'),CAST(ifnull(min(ddno),0) AS unsigned) from chbook_bill where statusFlag='F' and brncode='" + brCode
                    + "' and Inst_type='" + instCode + "' and slabcode in (select slabcode from chbookmaster_amtslab where  amtfrom <= " + amt + " and amtto >= " + amt
                    + " and instcode ='" + instCode + "') limit 1";
            List cbBookBillLt = em.createNativeQuery(query).getResultList();
            if (cbBookBillLt.isEmpty()) {
                throw new ApplicationException("Book Not Issued for " + instCode);
            }
            Vector cbBookBillLtVec = (Vector) cbBookBillLt.get(0);
            float ddNo = Float.parseFloat(cbBookBillLtVec.get(1).toString());
            String ddNoChar = cbBookBillLtVec.get(1).toString();
            String series = cbBookBillLtVec.get(0).toString();

            System.out.println("Pay Order number is >>>>>>>" + ddNoChar + " and series number is >>>>>>" + series);
            System.out.println("Logged in branch is >>>>>>" + brCode);

            if (series.equalsIgnoreCase("N/A")) {
                throw new ApplicationException("Book Not Issued for " + instCode);
            }
            Query updateQuery = em.createNativeQuery("update chbook_bill set statusflag = 'U' where ddno = " + ddNo
                    + " and series = '" + series + "' and statusflag = 'F' AND inst_type='" + instCode + "' AND BRNCODE='" + brCode + "'");
            int rs = updateQuery.executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in Chbook_Bill updation.");
            }
            return ddNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//    public String poAuthorization(Float recNo, String acNo, Float seqNo, String billType, double amt, String status, String dt, String auth1, String brCode)
//            throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            String message = "", billNatureMsg = "", msgRemNat = "", series = "", ddNoChar = "";
//            Float ddNo = 0f;
//
//            int year = Integer.parseInt(dt.substring(0, 4));
//            List glReconLt = em.createNativeQuery("select ifnull(auth,'N') from gl_recon where recno = " + recNo
//                    + " and dt  = '" + dt + "' AND org_brnid = '"
//                    + brCode + "'").getResultList();
//            if (!glReconLt.isEmpty()) {
//                Vector glReconLtVec = (Vector) glReconLt.get(0);
//                String auth = glReconLtVec.get(0).toString();
//                if (auth.equalsIgnoreCase("N")) {
//                    if (acNo.equalsIgnoreCase("CASH")) {
//                        ut.rollback();
//                        message = "Cash Entry Has Not been Authorized";
//                        return message;
//                    } else {
//                        ut.rollback();
//                        message = "Transfer Entry Has Not been Authorized";
//                        return message;
//                    }
//                }
//                List reportLt = em.createNativeQuery("select code from parameterinfo_report where reportname='AD ENTRY'").getResultList();
//                String addEntryFlag = "";
//                if (!reportLt.isEmpty()) {
//                    Vector reportLtVec = (Vector) reportLt.get(0);
//                    int adEntry = Integer.parseInt(reportLtVec.get(0).toString());
//                    if (adEntry == 1) {
//                        addEntryFlag = "TRUE";
//                    } else {
//                        addEntryFlag = "FALSE";
//                    }
//                } else {
//                    addEntryFlag = "FALSE";
//                }
//                List billTyMasterLt = em.createNativeQuery("select InstNature from billtypemaster where instcode = '" + billType + "'").getResultList();
//                if (!billTyMasterLt.isEmpty()) {
//                    Vector billTyMasterLtVec = (Vector) billTyMasterLt.get(0);
//                    billNatureMsg = billTyMasterLtVec.get(0).toString();
//                    msgRemNat = billNatureMsg;
//                } else {
//                    billNatureMsg = "";
//                }
//                if (addEntryFlag.equalsIgnoreCase("TRUE")) {
//                    if (status.equalsIgnoreCase("PAID") || status.equalsIgnoreCase("CANCELLED")) {
//                        if (billNatureMsg.equalsIgnoreCase("DD")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_dd SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        } else if (billNatureMsg.equalsIgnoreCase("PO")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_po SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        } else if (billNatureMsg.equalsIgnoreCase("TPO")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_tpo SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        } else if (billNatureMsg.equalsIgnoreCase("AD")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_ad SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        }
//                    }
//                    if (status.equalsIgnoreCase("ISSUED") && billNatureMsg.equalsIgnoreCase("PO")) {
//                        if (billNatureMsg.equalsIgnoreCase("PO")) {
//                            List billPoList = em.createNativeQuery("SELECT AUTH FROM bill_po where dt = '" + dt + "' and recno = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND STATUS='ISSUED' AND AUTH='Y'").getResultList();
//                            if (!billPoList.isEmpty()) {
//                                ut.rollback();
//                                message = "PO is already Authorized.";
//                                return message;
//                            }
//                        }
//                    }
//                    List cbBookBillLt = chBookBill(billType, amt, "AAAAA", brCode);
//                    if (!cbBookBillLt.isEmpty()) {
//                        Vector cbBookBillLtVec = (Vector) cbBookBillLt.get(0);
//                        ddNo = Float.parseFloat(cbBookBillLtVec.get(1).toString());
//
//                        ddNoChar = cbBookBillLtVec.get(1).toString();
//                        series = cbBookBillLtVec.get(0).toString();
//                        System.out.println("Pay Order number is >>>>>>>" + ddNoChar + " and series number is >>>>>>" + series);
//                        System.out.println("Logged in user is >>>>>>>" + auth1 + " and logged in branch is >>>>>>" + brCode);
//                        if (series.equalsIgnoreCase("N/A")) {
//                            ut.rollback();
//                            message = billType + " Book Not Issued!";
//                            return message;
//                        } else if (ddNo == 0 && series.equalsIgnoreCase("ERROR")) {
//                            ut.rollback();
//                            message = billType + " Book Not Issued!";
//                            return message;
//                        }
//                    } else {
//                        ut.rollback();
//                        message = billType + " Book Not Issued!";
//                        return message;
//                    }
//                    Query updateQuery = em.createNativeQuery("update chbook_bill set statusflag = 'U' where ddno = " + ddNo
//                            + " and series = '" + series + "' and statusflag = 'F' AND inst_type='" + billType + "' AND BRNCODE='" + brCode + "'");
//                    updateQuery.executeUpdate();
//                    if (billType.equalsIgnoreCase("PO")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_po where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                        System.out.println("Pay Order number is >>>>>>>" + ddNoChar + " and Generated Sequence number is >>>>>>" + seqNo);
//                        System.out.println("Logged in user is >>>>>>>" + auth1 + " and logged in branch is >>>>>>" + brCode);
//                    } else if (billNatureMsg.equalsIgnoreCase("DD")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_dd where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                    } else if (billNatureMsg.equalsIgnoreCase("TPO")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_tpo where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                    } else if (billNatureMsg.equalsIgnoreCase("AD")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_ad where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                    }
//                } else {
//                    if (status.equalsIgnoreCase("PAID") || status.equalsIgnoreCase("CANCELLED")) {
//                        if (billNatureMsg.equalsIgnoreCase("DD")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_dd SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        } else if (billNatureMsg.equalsIgnoreCase("PO")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_po SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        } else if (billNatureMsg.equalsIgnoreCase("TPO")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_tpo SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        } else if (billNatureMsg.equalsIgnoreCase("AD")) {
//                            Query updateQuery = em.createNativeQuery("UPDATE bill_ad SET AUTH = 'Y',AUTHBY = '" + auth1
//                                    + "' WHERE RECNO = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND SEQNO = " + seqNo + "");
//                            updateQuery.executeUpdate();
//                        }
//                    }
//                    if (status.equalsIgnoreCase("ISSUED") && billNatureMsg.equalsIgnoreCase("PO")) {
//                        if (billNatureMsg.equalsIgnoreCase("PO")) {
//                            List billPoList = em.createNativeQuery("SELECT AUTH FROM bill_po where dt = '" + dt + "' and recno = " + recNo + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' AND STATUS='ISSUED' AND AUTH='Y'").getResultList();
//                            if (!billPoList.isEmpty()) {
//                                ut.rollback();
//                                message = "PO is already Authorized.";
//                                return message;
//                            }
//                        }
//                    }
//                    if (!msgRemNat.equalsIgnoreCase("AD")) {
//                        List cbBookBillLt = chBookBill(billType, amt, "AAAAA", brCode);
//                        if (!cbBookBillLt.isEmpty()) {
//                            Vector cbBookBillLtVec = (Vector) cbBookBillLt.get(0);
//                            ddNo = Float.parseFloat(cbBookBillLtVec.get(1).toString());
//                            ddNoChar = cbBookBillLtVec.get(1).toString();
//                            series = cbBookBillLtVec.get(0).toString();
//                            System.out.println("Pay Order number is >>>>>>>" + ddNoChar + " and series number is >>>>>>" + series);
//                            System.out.println("Logged in user is >>>>>>>" + auth1 + " and logged in branch is >>>>>>" + brCode);
//                            if (series.equalsIgnoreCase("N/A")) {
//                                ut.rollback();
//                                message = billType + " Book Not Issued!";
//                                return message;
//                            } else if (ddNo == 0 && series.equalsIgnoreCase("ERROR")) {
//                                ut.rollback();
//                                message = billType + " Book Not Issued!";
//                                return message;
//                            }
//                        } else {
//                            ut.rollback();
//                            message = billType + " Book Not Issued!";
//                            return message;
//                        }
//                        Query updateQuery = em.createNativeQuery("update chbook_bill set statusflag = 'U' where ddno = " + ddNo
//                                + " and series = '" + series + "' and statusflag = 'F' AND inst_type='" + billType + "' AND BRNCODE='" + brCode + "'");
//                        updateQuery.executeUpdate();
//                    }
//                    if (billType.equalsIgnoreCase("PO")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_po where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                        System.out.println("Pay Order number is >>>>>>>" + ddNoChar + " and Generated Sequence number is >>>>>>" + seqNo);
//                        System.out.println("Logged in user is >>>>>>>" + auth1 + " and logged in branch is >>>>>>" + brCode);
//                    } else if (billNatureMsg.equalsIgnoreCase("DD")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_dd where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                    } else if (billNatureMsg.equalsIgnoreCase("TPO")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_tpo where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                    } else if (billNatureMsg.equalsIgnoreCase("AD")) {
//                        List billPoLt = em.createNativeQuery("select max(seqno)+1 from bill_ad where fyear = " + year
//                                + " AND SUBSTRING(ACNO,1,2)='" + brCode + "' and billtype = '" + billType + "'").getResultList();
//                        if (!billPoLt.isEmpty()) {
//                            Vector billPoLtVec = (Vector) billPoLt.get(0);
//                            seqNo = Float.parseFloat(billPoLtVec.get(0).toString());
//                        } else {
//                            seqNo = 1.0f;
//                        }
//                    }
//                }
//                String ddNoVarchar = ddNo.toString();
//                String seqNoVarchar = seqNo.toString();
//
//                if (ddNoVarchar.contains(".")) {
//                    ddNoVarchar = ddNoVarchar.substring(0, ddNoVarchar.indexOf("."));
//                }
//                if (seqNoVarchar.contains(".")) {
//                    seqNoVarchar = seqNoVarchar.substring(0, seqNoVarchar.indexOf("."));
//                }
//                if (billNatureMsg.equalsIgnoreCase("DD")) {
//                    Query updateQuery = em.createNativeQuery("update bill_dd set auth = 'Y',seqno = " + seqNo
//                            + " ,instno = " + ddNoChar + ", authby = '" + auth1 + "' where dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode + "' and recno = " + recNo + "");
//                    updateQuery.executeUpdate();
//                    message = "Issued, Seq No = " + seqNoVarchar + ", Instrument No = " + ddNoVarchar;
//                } else if (billNatureMsg.equalsIgnoreCase("PO")) {
//                    Query updateQuery = em.createNativeQuery("update bill_po set auth = 'Y',seqno = " + seqNo + " ,instno = " + ddNoChar
//                            + ", authby = '" + auth1 + "' where origindt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode + "' and recno = " + recNo + "");
//                    updateQuery.executeUpdate();
//                    message = "Issued, Seq No = " + seqNoVarchar + ", Instrument No = " + ddNoVarchar;
//                } else if (billNatureMsg.equalsIgnoreCase("TPO")) {
//                    Query updateQuery = em.createNativeQuery("update bill_tpo set auth = 'Y',seqno = " + seqNo + " ,instno = " + ddNoChar
//                            + ", authby = '" + auth1 + "' where dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode + "' and recno = " + recNo + "");
//                    updateQuery.executeUpdate();
//                    message = "Issued, Seq No = " + seqNoVarchar + ", Instrument No = " + ddNoVarchar;
//                } else if (billNatureMsg.equalsIgnoreCase("AD")) {
//                    if (addEntryFlag.equalsIgnoreCase("FALSE")) {
//                        Query updateQuery = em.createNativeQuery("update bill_ad set auth = 'Y',authby = '" + auth1
//                                + "' where  dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode
//                                + "' and recno = " + recNo + "");
//                        updateQuery.executeUpdate();
//                        message = billType + " Authorized";
//                    } else {
//                        Query updateQuery = em.createNativeQuery("update bill_ad set auth = 'Y',seqno = " + seqNo
//                                + " ,instno = " + ddNoChar + ", authby = '" + auth1 + "' where dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode + "' and recno = " + recNo + "");
//                        updateQuery.executeUpdate();
//                        message = "Issued, Seq No = " + seqNoVarchar + ", Instrument No = " + ddNoVarchar;
//                    }
//                } else {
//                    Query updateQuery = em.createNativeQuery("update bill_hoothers set auth = 'Y',authby = '" + auth1
//                            + "' where dt = '" + dt + "' AND SUBSTRING(ACNO,1,2)='" + brCode
//                            + "' and recno = " + recNo + "");
//                    updateQuery.executeUpdate();
//                    message = billType + " Authorized";
//                }
//            } else {
//                ut.rollback();
//                message = "Entry Does Not Exist";
//                return message;
//            }
//            ut.commit();
//            return "TRUE" + message;
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//
//    }
//
//    public List chBookBill(String instCode, double amt, String serNum, String brCode) throws ApplicationException {
//        List resultList = null;
//        try {
//            if (serNum.equals("") || serNum == null) {
//                serNum = "AAAAA";
//            }
//
//            if (serNum.equalsIgnoreCase("AAAAA")) {
//                String query = "select ifnull(series,'N/A'),CAST(ddno AS unsigned) from chbook_bill where ddno in ("
//                        + " select min(ddno) from chbook_bill where statusFlag='F' and brncode='" + brCode + "' and Inst_type='" + instCode + "'"
//                        + " and slabcode in (select slabcode from chbookmaster_amtslab where  amtfrom <= " + amt + " and amtto >= " + amt + " and instcode ='" + instCode + "')"
//                        + " and entryDt in (select min(entryDt) from chbook_bill where statusFlag='F' and brncode='" + brCode + "' and  Inst_type='" + instCode + "' and slabcode in "
//                        + " (select slabcode from chbookmaster_amtslab where "
//                        + " amtfrom <= " + amt + " and amtto >= " + amt + " and instcode ='" + instCode + "'))) and Inst_type='" + instCode + "' AND statusFlag='F' and brncode='" + brCode + "' limit 1";
//                resultList = em.createNativeQuery(query).getResultList();
//            } else {
//                String query = "select ifnull(series,'N/A'),CAST(ddno AS unsigned) from "
//                        + " chbook_bill where ddno in ("
//                        + " select min(ddno) from chbook_bill"
//                        + " where statusFlag='F' and brncode='" + brCode + "' and Inst_type='" + instCode + "'"
//                        + " and slabcode in "
//                        + " (select slabcode from chbookmaster_amtslab where "
//                        + " amtfrom <= " + amt + " and amtto >= " + amt + " and instcode ='" + instCode + "') and series='" + serNum + "'"
//                        + " and entryDt in "
//                        + " (select min(entryDt) from chbook_bill "
//                        + " where statusFlag='F' and"
//                        + " Inst_type='" + instCode + "' and brncode='" + brCode + "' and slabcode in "
//                        + " (select slabcode from chbookmaster_amtslab where "
//                        + " amtfrom <= " + amt + " and amtto >= " + amt + " and instcode ='" + instCode + "' and series='" + serNum + "')))"
//                        + " and Inst_type='" + instCode + "' AND statusFlag='F' and brncode='" + brCode + "' limit 1";
//                resultList = em.createNativeQuery(query).getResultList();
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return resultList;
//    }
    public String authLocalTransaction(int trSNo, String AuthBy, String dt, String orgnBrnCode) throws ApplicationException {
        float trsno = (float) trSNo;
        String auth = null;
        String msgTr = "";
        int trsNumber = 0;
        //String enterBy = "";
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            /*When the Orgn Branch Code & Destination Branch both are same*/
            List enterByList1 = em.createNativeQuery("SELECT ENTERBY,VALUEDT,INSTDT FROM recon_trf_d WHERE TRSNO =" + trSNo).getResultList();
            if (enterByList1.size() > 0) {
                Vector enterByVect = (Vector) enterByList1.get(0);
                String enterBy1 = enterByVect.get(0).toString();
                String valDt = enterByVect.get(1).toString();
                String instrDt = null;
                if ((enterByVect.get(2) == null) || (enterByVect.get(2).toString().equals(""))) {
                    instrDt = null;
                } else {
                    instrDt = enterByVect.get(2).toString();
                }
                if (enterBy1.toUpperCase().equals(AuthBy.toUpperCase())) {
                    ut.rollback();
                    return "You can not Pass Your Own Entry.";
                }
                List getDataList = em.createNativeQuery("SELECT ACNO,CUSTNAME,TRANTYPE,INSTNO,CRAMT,AUTH,ENTERBY,RECNO,TY,DRAMT,TRSNO,"
                        + "0 as BALANCE,PAYBY,DETAILS,0 AS CLGREASON,IY,SUBTOKENNO,'' AS CHECKBY,ORG_BRNID,DEST_BRNID FROM recon_trf_d WHERE TRSNO = " + trSNo).getResultList();
                for (int i = 0; i < getDataList.size(); i++) {
                    Vector getDateVect = (Vector) getDataList.get(i);
                    String acNo = getDateVect.get(0).toString();
                    String instNo = getDateVect.get(3).toString();
                    double crAmt = Double.parseDouble(getDateVect.get(4).toString());
                    auth = getDateVect.get(5).toString();
                    // enterBy = getDateVect.get(6).toString();
                    float recNo = Float.parseFloat(getDateVect.get(7).toString());
                    double drAmt = Double.parseDouble(getDateVect.get(9).toString());
                    trsNumber = Integer.parseInt(getDateVect.get(10).toString());
                    String details = getDateVect.get(13).toString();
                    int clgReason = Integer.parseInt(getDateVect.get(14).toString());
                    int iy = Integer.parseInt(getDateVect.get(15).toString());
                    String destBrnid = getDateVect.get(19).toString();
                    String acNature = "";
                    String acCode = fTSPosting43CBSBean.getAccountCode(acNo);
                    List acNatureList = em.createNativeQuery("SELECT ACCTNATURE FROM accounttypemaster WHERE ACCTCODE = '" + acCode + "'").getResultList();
                    if (acNatureList.isEmpty()) {
                        ut.rollback();
                        return "ACCOUNT TYPE DOES NOT EXIST";
                    } else {
                        Vector acNatureVect = (Vector) acNatureList.get(0);
                        acNature = acNatureVect.get(0).toString();
                    }
                    int ty = 0;
                    double total;
                    String dt1 = "";
                    if (trsNumber == trSNo) {
                        if ((crAmt != 0)) {
                            ty = 0;
                            drAmt = 0d;
                        } else {
                            ty = 1;
                            crAmt = 0d;
                        }
                        List authList = em.createNativeQuery("SELECT AUTH FROM recon_trf_d WHERE ACNO = '" + acNo
                                + "' AND RECNO = '" + recNo + "' AND TRSNO =" + trsNumber + " AND "
                                + "(AUTH IS NULL OR AUTH = 'N') AND TY = '" + ty + "' AND DT = '" + dt + "'").getResultList();

                        Vector authvect = (Vector) authList.get(0);
                        auth = authvect.get(0).toString();
                        if (auth.equalsIgnoreCase("n")) {
                            if (iy == 99) {
                                ut.rollback();
                                msg = "LIMIT EXCEEDS" + msg;
                                return msg;
                            } else if (iy == 21) {
                                String billNo = null;
                                msg = cbsAuthTransferObcProcess(billNo, dt, instNo, AuthBy, trsno, recNo, crAmt, drAmt, orgnBrnCode);
                                if (!msg.equalsIgnoreCase("true")) {
                                    ut.rollback();
                                    return msg;
                                }
                            }
                            auth = "Y";
                            Integer updateQry = em.createNativeQuery("UPDATE recon_trf_d SET AUTH = '" + auth + "', AUTHBY = '"
                                    + AuthBy + "' WHERE ACNO = '" + acNo + "' AND RECNO = " + recNo + " "
                                    + "AND TRSNO =" + trsNumber + " AND (AUTH IS NULL OR AUTH = 'N') AND TY = '" + ty + "' "
                                    + "AND DT = '" + dt + "'").executeUpdate();
                            if (updateQry > 0) {
                                msgTr = authTransferTrans(trsno, ty, recNo, orgnBrnCode);
                                if (!msgTr.equalsIgnoreCase("yes")) {
                                    ut.rollback();
                                    return msgTr;
                                } else {
                                    msg = "TRUE";
                                }
                            }

                            if ((!instNo.equalsIgnoreCase("VOUCHER")) && (drAmt != 0)) {
                                if (clgReason == 10 || clgReason == 33) {
                                    List intOptList = em.createNativeQuery("SELECT UPPER(INTOPT) FROM td_vouchmst WHERE ACNO='"
                                            + acNo + "' AND VOUCHERNO = cast(" + instNo + " as decimal)").getResultList();
                                    String tdsIntOpt = "";
                                    if (intOptList.size() > 0) {
                                        Vector intOptVect = (Vector) intOptList.get(0);
                                        tdsIntOpt = intOptVect.get(0).toString();
                                    }
                                    if (tdsIntOpt.equals("C")) {
                                        em.createNativeQuery("UPDATE td_vouchmst SET CUMUPRINAMT=ifnull(CUMUPRINAMT,0)-" + drAmt
                                                + ", TDSDEDUCTED=ifnull(TDSDEDUCTED,0)+" + drAmt + " WHERE ACNO='" + acNo
                                                + "' AND VOUCHERNO = cast(" + instNo + " as decimal)").executeUpdate();
                                    } else {
                                        em.createNativeQuery("UPDATE td_vouchmst SET TDSDEDUCTED=ifnull(TDSDEDUCTED,0)+" + drAmt
                                                + " WHERE ACNO='" + acNo + "' AND VOUCHERNO = cast(" + instNo + " as decimal)").executeUpdate();
                                    }
                                    em.createNativeQuery("INSERT INTO tdshistory(ACNO,VOUCHERNO,TDS,DT,FROMDT,TODT,INTOPT) "
                                            + "VALUES('" + acNo + "',cast(" + instNo + " as decimal)," + drAmt + ","
                                            + "'" + dt + "',"
                                            + "'" + dt + "',"
                                            + "'" + dt + "','" + tdsIntOpt + "')").executeUpdate();
                                }
                            }
                        } else {
                            ut.rollback();
                            msg = "BATCH HAS ALREADY BEEN AUTHORIZED.";
                            return msg;
                        }
                        if (iy == 11 || iy == 12 || iy == 13) {
                            float seqNo = 0f;
                            if (clgReason != 9) {
                                if (iy == 11) {
                                    List seqNoList = em.createNativeQuery("SELECT ifnull(MAX(SEQNO),0) FROM bill_sundry WHERE FYEAR = extract(year from '" + dt + "')").getResultList();
                                    Vector seqNoVect = (Vector) seqNoList.get(0);
                                    seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                }
                                if (iy == 12) {
                                    List seqNoList = em.createNativeQuery("SELECT ifnull(MAX(SEQNO),0) FROM bill_suspense WHERE FYEAR = extract(year from '" + dt + "')").getResultList();
                                    Vector seqNoVect = (Vector) seqNoList.get(0);
                                    seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                }
                                if (iy == 13) {
                                    List seqNoList = em.createNativeQuery("SELECT ifnull(MAX(SEQNO),0) FROM bill_clgadjustment WHERE ACNO='" + acNo + "' AND SUBSTRING(ACNO,1,2) = '" + orgnBrnCode + "' AND FYEAR = extract(year from '" + dt + "')").getResultList();
                                    Vector seqNoVect = (Vector) seqNoList.get(0);
                                    seqNo = Float.parseFloat(seqNoVect.get(0).toString());
                                }
                                if (seqNo == 0f) {
                                    seqNo = 1f;
                                } else {
                                    seqNo = seqNo + 1;
                                }
                                double stmAmt = 0d;
                                if (crAmt != 0) {
                                    stmAmt = crAmt;
                                }
                                if (drAmt != 0) {
                                    stmAmt = drAmt;
                                }
                                if (stmAmt > 0) {
                                    if (iy == 11) {
                                        em.createNativeQuery("INSERT bill_sundry (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES(extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + AuthBy + "','" + dt + "', 'ISSUED','Y',2," + seqNo + "," + ty + "," + recNo + ",'" + dt + "','" + AuthBy + "','" + details + "')").executeUpdate();
                                    }
                                    if (iy == 12) {
                                        em.createNativeQuery("INSERT bill_suspense (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES(extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + AuthBy + "','" + dt + "','ISSUED','Y',2," + seqNo + "," + ty + "," + recNo + ",'" + dt + "','" + AuthBy + "','" + details + "')").executeUpdate();
                                    }
                                    if (iy == 13) {
                                        em.createNativeQuery("INSERT bill_clgadjustment (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE,SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) VALUES(extract(year from '" + dt + "'),'" + acNo + "','" + stmAmt + "','" + AuthBy + "','" + dt + "', 'ISSUED','Y',2," + seqNo + "," + ty + "," + recNo + ",'" + dt + "','" + AuthBy + "','" + details + "')").executeUpdate();
                                    }
                                    msg = "SEQUENCE NO. IS " + seqNo + " FOR " + acNo + " ACCOUNT";
                                }
                            }
                        }
                        if ((acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || acNature.equalsIgnoreCase(CbsConstant.SS_AC) || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))
                                && (crAmt != 0)) {
                            String msgRecAmt = fTSPosting43CBSBean.npaRecoveryUpdation(trsno, acNature, acNo, dt, crAmt, orgnBrnCode, destBrnid, AuthBy);
                            if (!msgRecAmt.equalsIgnoreCase("True")) {
                                ut.rollback();
                                throw new ApplicationException(msg);
                            }
                        }
                        if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            total = drAmt + crAmt;
                            try {
                                dt1 = ymmd.format(ymd.parse(dt));
                            } catch (Exception e) {
                                throw new ApplicationException(e);
                            }
                            msg = fTSPosting43CBSBean.loanDisbursementInstallment(acNo, total, ty, AuthBy, dt1, recNo, clgReason, details);
                            if (!msg.equalsIgnoreCase("true")) {
                                ut.rollback();
                                return msg;
                            }
                        } else if ((acNature.equalsIgnoreCase("rd")) && (clgReason == 1 || clgReason == 6)) {
                            total = drAmt + crAmt;
                            try {
                                dt1 = ymmd.format(ymd.parse(dt));
                            } catch (Exception e) {
                                throw new ApplicationException(e);
                            }
                            msg = fTSPosting43CBSBean.loanDisbursementInstallment(acNo, total, ty, AuthBy, dt1, recNo, clgReason, details);
                            if (!msg.equalsIgnoreCase("true")) {
                                ut.rollback();
                                return msg;
                            }
                        }
                        if (acNature.equalsIgnoreCase("of")) {
                            em.createNativeQuery("UPDATE of_recon SET TDACNO=(SELECT ACNO FROM td_vouchmst WHERE OFACNO='" + acNo + "') WHERE ACNO='" + acNo + "' AND TDACNO IS NULL").executeUpdate();
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException();
        }
        System.out.println("messgae from transfer auth " + msg);
        return msg;
    }

    public boolean inactiveCertificate(String customerFolioNo, String user, int certNo, String issuedt, String status, int shareno, String orgnBrCode, String dt) throws ApplicationException {
        try {
            List data = em.createNativeQuery("select auth,status from  certificate_share where certificateno= '" + certNo + "' and IssueDt='" + issuedt + "'").getResultList();
            Vector vtr = (Vector) data.get(0);
            String curStatus = vtr.get(1).toString();
            if (vtr.get(0).toString().equalsIgnoreCase("N")) {
                throw new ApplicationException("Authorise The Entry Before Mark Inactive");
            }
            if (curStatus.equals("C")) {
                throw new ApplicationException("This certificate already inactive.");
            }

            String paidShareNoStr = "";
            List sharelist = em.createNativeQuery("select shareno from share_capital_issue where foliono='" + customerFolioNo
                    + "' and sharecertno='" + certNo + "' order by shareno").getResultList();
            if (!sharelist.isEmpty()) {
                for (int l = 0; l < shareno; l++) {
                    Vector vtr2 = (Vector) sharelist.get(l);
                    if (vtr2.get(0) != null) {
                        paidShareNoStr = paidShareNoStr + vtr2.get(0).toString() + ",";
                    }
                }
                paidShareNoStr = paidShareNoStr.substring(0, paidShareNoStr.length() - 1);
            }
            if (status.equalsIgnoreCase("Inactive")) {
                int i = em.createNativeQuery("update certificate_share set status='C',lastupdatedt='" + dt + "', lastupdateby='" + user + "', auth='Y',paymentDt='" + dt
                        + "' Where certificateNo=" + certNo + "").executeUpdate();

                int j = em.createNativeQuery("insert into share_capital_issue_his(ShareNo,FolioNo,ShareCertNo,IssueDt,IssuedBy,fromdt,todt,LastUpdateTime,"
                        + "LastUpdateBy,Auth,AuthBy) select shareNo,FolioNo,ShareCertNo, IssueDt,IssuedBy,dt,'" + issuedt + "',CURRENT_TIMESTAMP,'" + user
                        + "', auth, authby from share_capital_issue where  sharecertno=" + certNo + " and shareno in (" + paidShareNoStr + ")").executeUpdate();

                int k = em.createNativeQuery("update share_capital_issue set LastUpdateTime='" + ymd.format(new java.util.Date()) + "', LastUpdateBy='"
                        + user + "' where ShareCertNo=" + certNo + "").executeUpdate();
                if (i > 0 && j > 0 && k > 0) {
                    return true;
                } else {
                    throw new ApplicationException("Data does not save for Share Certificate");
                }
            } else if (status.equalsIgnoreCase("Partial")) {
                String remainingShareNoStr = "";
                if (!sharelist.isEmpty()) {
                    for (int l = shareno; l < sharelist.size(); l++) {
                        Vector vtr2 = (Vector) sharelist.get(l);
                        if (vtr2.get(0) != null) {
                            remainingShareNoStr = remainingShareNoStr + vtr2.get(0).toString() + ",";
                        }
                    }
                    remainingShareNoStr = remainingShareNoStr.substring(0, remainingShareNoStr.length() - 1);
                }
                List certnoList = em.createNativeQuery("select ifnull(max(certificateno),0)+1 from certificate_share").getResultList();
                Double newCertNo = 0d;
                if (!certnoList.isEmpty()) {
                    Vector vtr1 = (Vector) certnoList.get(0);
                    newCertNo = Double.parseDouble(vtr1.get(0).toString());
                }
                int i = em.createNativeQuery("update certificate_share set status='C',lastupdatedt='" + dt + "', lastupdateby='" + user + "', auth='Y',paymentDt='"
                        + ymd.format(new java.util.Date()) + "' Where certificateNo=" + certNo + "").executeUpdate();

                int j = em.createNativeQuery("Insert Into certificate_share(issuedt,issuedby,adviceno,pono,certissuedt,remark,certificateNo,status,"
                        + "auth,lastupdateby,lastupdatedt,paymentdt) select issuedt,issuedby,adviceno,pono,certissuedt,remark," + newCertNo + ",'A','N','"
                        + user + "',now(),NULL " + "from certificate_share Where certificateNo = " + certNo + " and status = 'C'").executeUpdate();

                j = em.createNativeQuery("insert into share_capital_issue_his(ShareNo,FolioNo,ShareCertNo,IssueDt,IssuedBy,fromdt,todt,LastUpdateTime,"
                        + "LastUpdateBy,Auth,AuthBy) select shareNo,FolioNo,ShareCertNo, IssueDt,IssuedBy,dt,'" + issuedt + "',CURRENT_TIMESTAMP,'" + user
                        + "', auth, authby from share_capital_issue where  sharecertno=" + certNo + " and shareno in (" + paidShareNoStr + ")").executeUpdate();

                int k = em.createNativeQuery("Update share_capital_issue Set lastupdatetime='" + ymd.format(new java.util.Date()) + "', Lastupdateby='" + user
                        + "' Where shareNo in (" + paidShareNoStr + ") and shareCertno=" + certNo + "").executeUpdate();

                k = em.createNativeQuery("Update share_capital_issue Set shareCertno=" + newCertNo + ",lastupdatetime='" + ymd.format(new java.util.Date())
                        + "', Lastupdateby='" + user + "',auth='N',authby=null Where shareNo in (" + remainingShareNoStr + ") and shareCertno=" + certNo).executeUpdate();

                if (i > 0 && j > 0 && k > 0) {
                    return true;
                } else {
                    throw new ApplicationException("Data does not save for Share Certificate");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return false;
    }

    public boolean insertdata(String folioNo, String user, String pono, String advise, String remark, int noOfShare, String orgnBrCode, String dt, String issueDt) throws ApplicationException {
        try {
            Double newCertNo = 0d;
            List certno = em.createNativeQuery("select ifnull(max(certificateno),0)from certificate_share").getResultList();
            if (!certno.isEmpty()) {
                Vector vtr1 = (Vector) certno.get(0);
                newCertNo = Double.parseDouble(vtr1.get(0).toString()) + 1;
            }

            String acno = orgnBrCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001";
            List shareList = em.createNativeQuery("select shareno from share_capital_issue where foliono='" + acno
                    + "' order by shareno limit " + noOfShare).getResultList();
            String shareNoStr = "";
            if (shareList.isEmpty()) {
                throw new ApplicationException("Share does not exist for Issue");
            }
            if (shareList.size() < noOfShare) {
                throw new ApplicationException("Issing shares are greater than available Shares.");
            }
            int size = shareList.size();
            for (int l = 0; l < size; l++) {
                Vector vtr2 = (Vector) shareList.get(l);
                if (vtr2.get(0) != null) {
                    shareNoStr = shareNoStr + vtr2.get(0).toString() + ",";
                }
            }
            shareNoStr = shareNoStr.substring(0, shareNoStr.length() - 1);
            int i = em.createNativeQuery("Insert Into certificate_share(CertIssueDt,certificateNo,status,issueDt,issuedBy,Auth,AdviceNo,PONo,"
                    + "paymentDt,remark,lastupdateby,lastupdatedt) Values('" + dt + "'," + newCertNo + ",'A','" + issueDt + "','" + user + "',"
                    + "'N','" + advise + "','" + pono + "',null,'" + remark + "','" + user + "','" + dt + "')").executeUpdate();

            int j = em.createNativeQuery("Update share_capital_issue Set Foliono='" + folioNo + "',shareCertno=" + newCertNo + ",lastupdatetime='"
                    + dt + "',Lastupdateby='" + user + "',dt='" + dt + "', auth='N' Where shareNo in (" + shareNoStr + ")").executeUpdate();
            if (i == 0 && j == 0) {
                throw new ApplicationException("Data is not save in Certificate_Share");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    public String cbsFdAcTranChk(String acNo) throws com.cbs.exception.ApplicationException {
        try {
            String msg = "false";
            String acNature = fTSPosting43CBSBean.getAccountNature(acNo);
            String acCode = fTSPosting43CBSBean.getAccountCode(acNo);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List sChkList = em.createNativeQuery("select * from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag ='Y' and "
                        + " status_option = '" + acCode + "'").getResultList();
                if (sChkList.isEmpty()) {
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

    public String insertFidilityTran(String actNature, String acno, Integer ty, double amt, String dt, String valueDt, Integer tranType, String details,
            String enterBy, Float trsno, String tranTime, Float recno, String auth, String authBy, Integer tranDesc, Integer payBy,
            String instno, String instDt, Float tokenNo, String tokenPaidBy, String subTokenNo, Integer iy, String tdAcno, Float voucherNo,
            String intFlag, String closeFlag, String orgBrnId, String destBrnId, Integer tranId, String termId, String adviceNo, String adviceBrnCode) throws ApplicationException {
        try {
            String msg = "true";
            List dsgGetList = em.createNativeQuery("select desig_code,custid,accstatus from fidility_accountmaster where acno = '" + acno + "'").getResultList();
            if (!dsgGetList.isEmpty()) {
                Vector dsgVect = (Vector) dsgGetList.get(0);
                String dsgCd = dsgVect.get(0).toString();
                String cuId = dsgVect.get(1).toString();
                String accSt = dsgVect.get(2).toString();
                if (accSt.equalsIgnoreCase("9")) {
                    msg = "Account is Closed";
                } else {
                    List exitList = em.createNativeQuery("select ForYear,CrGlHead from fidility_premium_slab where Desig_Code='" + dsgCd + "' and EffDt = (select max(EffDt) from fidility_premium_slab where Desig_Code='" + dsgCd + "')").getResultList();
                    if (!exitList.isEmpty()) {
                        Vector exitVect = (Vector) exitList.get(0);
                        int fYr = Integer.parseInt(exitVect.get(0).toString());
                        String crHead = exitVect.get(1).toString();
                        if (crHead == null || crHead.equalsIgnoreCase("")) {
                            msg = "GL Head Not Defined For This Account";
                        } else {
                            String nAcNo = orgBrnId + crHead + "01";
                            msg = fTSPosting43CBSBean.insertRecons("PO", nAcNo, ty, amt, dt, valueDt, tranType, details, enterBy, trsno, tranTime,
                                    recno, auth, authBy, tranDesc, payBy, instno, instDt, tokenNo, tokenPaidBy, subTokenNo, iy, tdAcno,
                                    voucherNo, intFlag, closeFlag, orgBrnId, destBrnId, tranId, termId, adviceNo, adviceBrnCode);

                            double pAmt = amt / fYr;
                            if (msg.equalsIgnoreCase("true")) {
                                String matdt = CbsUtil.yearAdd(dt, fYr);
                                while ((ymd.parse(dt).compareTo(ymd.parse(matdt))) < 0) {
                                    Integer insertQuery2 = em.createNativeQuery("insert into fidility_schedule(CustId,ACNO,Amt,Status,AdjDate,DT,MatDt,EnterBy,auth,AdjBy,trsno,AuthBy)"
                                            + "values (" + "'" + cuId + "'" + "," + "'" + acno + "'" + "," + pAmt + "," + "'UNPAID','" + dt + "','" + dt + "','" + matdt + "'," + "'" + enterBy + "'" + ",'Y','',0," + "'" + authBy + "')").executeUpdate();
                                    if (insertQuery2 <= 0) {
                                        msg = "Insertion Problem In Fidility Slab";
                                        break;
                                    }
                                    dt = CbsUtil.yearAdd(dt, 1);
                                }
                            } else {
                                msg = "Insertion Problem In GL_recon";
                            }
                        }
                    } else {
                        msg = "No Detail For Designation Code Related With This Account";
                    }
                }
            } else {
                msg = "Account No. Not Exist In Fidility Account Master";
            }
            return msg;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String fdFidilityChk(String acNo) throws com.cbs.exception.ApplicationException {
        try {
            String msg = "false";
            List sChkList = em.createNativeQuery("select * from cbs_loan_acc_mast_sec where acno = '" + acNo + "'").getResultList();
            if (sChkList.isEmpty()) {
                msg = "false";
            } else {
                msg = "true";
            }
            return msg;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
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

    public String idMergeSave(String orgCode, String mergCode, String enterBy, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List brList = em.createNativeQuery("select alphacode from branchmaster where brncode =" + Integer.parseInt(brCode)).getResultList();
            if (brList.isEmpty()) {
                throw new ApplicationException("Branch Doe not exist");
            }
            Vector brVect = (Vector) brList.get(0);
            String orgnAlphaCode = brVect.get(0).toString();

            List result = em.createNativeQuery("select primarybrcode,ifnull(DateOfBirth,''),ifnull(PAN_GIRNumber,''),ifnull(VoterIDNo,''),ifnull(DrivingLicenseNo,''),"
                    + "fathername,auth from cbs_customer_master_detail where customerid ='" + orgCode + "'").getResultList();

            List result1 = em.createNativeQuery("select primarybrcode,ifnull(DateOfBirth,''),ifnull(PAN_GIRNumber,''),ifnull(VoterIDNo,''),ifnull(DrivingLicenseNo,''),"
                    + "fathername,auth from cbs_customer_master_detail where customerid ='" + mergCode + "'").getResultList();

            if (result.isEmpty()) {
                throw new ApplicationException("Primary Branch Code Not Exist For " + orgCode);
            }

            if (result1.isEmpty()) {
                throw new ApplicationException("Primary Branch Code Not Exist For " + mergCode);
            }

            String oExist = "N";
            String mExist = "N";

            List oResult = em.createNativeQuery("select customerid from tds_docdetail where customerid ='" + orgCode + "'").getResultList();
            if (!oResult.isEmpty()) {
                oExist = "Y";
            }

            List mResult = em.createNativeQuery("select customerid from tds_docdetail where customerid ='" + mergCode + "'").getResultList();
            if (!mResult.isEmpty()) {
                mExist = "Y";
            }

            if (oExist.equalsIgnoreCase("Y") || mExist.equalsIgnoreCase("Y")) {
//                if (oExist.equalsIgnoreCase("Y") && mExist.equalsIgnoreCase("Y")) {
//                    throw new ApplicationException("You Can't Merge, Because TDS Doc Detail For Both Customerid is Generated");
//                }
//                if (oExist.equalsIgnoreCase("N") && mExist.equalsIgnoreCase("Y")) {
//                    throw new ApplicationException("Doc Detail For " + mergCode + " is Genetared So Merge " + orgCode + " into " + mergCode);
//                }
                throw new ApplicationException("You Can't Merge, Because TDS Doc Detail For Both Customerid is Generated");
            }

            Vector record = (Vector) result.get(0);
            String prBr = record.get(0).toString();
            String pDob = record.get(1).toString();

            String pPan = record.get(2).toString();
            String pVoter = record.get(3).toString();

            String pDl = record.get(4).toString();
            String pFName = record.get(5).toString();
            String pAuth = record.get(6).toString();

            Vector record1 = (Vector) result1.get(0);
            String mrgBr = record1.get(0).toString();
            String mDob = record1.get(1).toString();

            String mPan = record1.get(2).toString();
            String mVoter = record1.get(3).toString();
            String mDl = record1.get(4).toString();

            String mFName = record1.get(5).toString();
            String mAuth = record1.get(6).toString();

            if (!orgnAlphaCode.equalsIgnoreCase("HO")) {
                if (!prBr.equalsIgnoreCase(mrgBr)) {
                    throw new ApplicationException("Both Customer Id is not from same Branch");
                }

                if (!prBr.equalsIgnoreCase(brCode)) {
                    throw new ApplicationException("You can not merge Customer Id for Other Branch");
                }
            }

            if (pAuth.equalsIgnoreCase("N")) {
                throw new ApplicationException("Authorization Pending For Customer Id " + orgCode);
            }

            if (mAuth.equalsIgnoreCase("N")) {
                throw new ApplicationException("Authorization Pending For Customer Id " + mergCode);
            }

            if (!pFName.trim().equalsIgnoreCase(mFName.trim())) {
                throw new ApplicationException("Father Name is not same for both Customer Id");
            }

            if (!pPan.trim().equalsIgnoreCase(mPan.trim())) {
                throw new ApplicationException("Pan No is not same for both Customer Id");
            }

//            if (!pVoter.trim().equalsIgnoreCase(mVoter.trim())) {
//                return "Voter Id Is Not Same For Both Customer Id";
//            }
//
//            if (!pDl.trim().equalsIgnoreCase(mDl.trim())) {
//                return "Driving License Is Not Same For Both Customer Id";
//            }
            long dayDiff = CbsUtil.dayDiff(ymmd.parse(pDob), ymmd.parse(mDob));
            if (dayDiff != 0) {
                throw new ApplicationException("Date Of Birth is not same for both Customer Id");
            }

            Query insertChg = em.createNativeQuery("insert into cbs_id_merge_auth(orgId,mergId,orgId_br,mergId_br,auth,mergedby,mergeddate,authby,deleteby,orgn_br_code)"
                    + " values('" + orgCode + "','" + mergCode + "','" + prBr + "','" + mrgBr + "','N','" + enterBy + "',DATE_FORMAT(curdate(),'%Y%m%d'),'','','" + brCode + "')");

            Integer insertChgVarient = insertChg.executeUpdate();
            if (insertChgVarient <= 0) {
                throw new ApplicationException("Data Not Saved In Table");
            }
            ut.commit();
            return "true";
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

    public List getUnAuthMergId(String orgCode) throws ApplicationException {
        try {
            List result = em.createNativeQuery("select orgId,mergId,auth,mergedby from cbs_id_merge_auth where auth= 'N' and orgn_br_code ='"
                    + orgCode + "'").getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String mergeIdDeletion(String orgCode, String mergCode, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            int j = em.createNativeQuery("Update cbs_id_merge_auth Set auth='D',deleteby='" + authBy + "'"
                    + " Where orgId = '" + orgCode + "' and mergId = '" + mergCode + "' and auth = 'N'").executeUpdate();

            if (j <= 0) {
                throw new ApplicationException("Data Not Deleted");
            }
            ut.commit();
            return "Data Deleted Successfully";
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

    public String mergeIdAuth(String orgCode, String mergCode, String enterBy, String authBy, String orgBrCode) throws ApplicationException {
        int i = 1;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            List result = em.createNativeQuery("select ifnull(auth,'N') from cbs_customer_master_detail where customerid ='" + orgCode + "'").getResultList();
            List result1 = em.createNativeQuery("select ifnull(auth,'N') from cbs_customer_master_detail where customerid ='" + mergCode + "'").getResultList();

            String pAuth, mAuth;
            Vector record = (Vector) result.get(0);
            pAuth = record.get(0).toString();

            Vector record1 = (Vector) result1.get(0);
            mAuth = record1.get(0).toString();

            if (pAuth.equalsIgnoreCase("N")) {
                throw new ApplicationException("Authorization Pending For Customer Id " + orgCode);
            }

            if (mAuth.equalsIgnoreCase("N")) {
                throw new ApplicationException("Authorization Pending For Customer Id " + mergCode);
            }

            List getsNoList = em.createNativeQuery("SELECT ifnull(max(sNo),0) FROM cbs_id_merge_history").getResultList();
            if (!getsNoList.isEmpty()) {
                Vector vSNo = (Vector) getsNoList.get(0);
                int j = Integer.parseInt(vSNo.get(0).toString());
                i = i + j;
            }

            /**
             * **** Maintain History For Account On Merged Customer Id *****
             */
            List getDataList = em.createNativeQuery("SELECT ACNO FROM customerid WHERE custid = '" + mergCode + "'").getResultList();
            for (int k = 0; k < getDataList.size(); k++) {
                Vector getDateVect = (Vector) getDataList.get(k);
                String acno = getDateVect.get(0).toString();

                Query insertChg = em.createNativeQuery("insert into cbs_id_merge_history(oldCustId,sNo,acno,jtId1,jtId2,jtId3,"
                        + "jtId4,newCustId,enterby,authby,orgn_br_code)"
                        + " values('" + mergCode + "'," + i + ",'" + acno + "','','','','','" + orgCode + "','" + enterBy + "','" + authBy
                        + "','" + orgBrCode + "')");

                Integer insertChgVarient = insertChg.executeUpdate();
                if (insertChgVarient <= 0) {
                    throw new ApplicationException("Data Not Saved In Table");
                }
                i = i + 1;
            }

            /**
             * **** Maintain History For Share Holder On Merged Customer Id
             * *****
             */
            List getShareList = em.createNativeQuery("SELECT regfoliono FROM share_holder WHERE custid = '" + mergCode + "'").getResultList();
            for (int m = 0; m < getShareList.size(); m++) {
                Vector getShareVect = (Vector) getShareList.get(m);
                String acno = getShareVect.get(0).toString();

                Query insertChg = em.createNativeQuery("insert into cbs_id_merge_history(oldCustId,sNo,acno,jtId1,jtId2,jtId3,"
                        + "jtId4,newCustId,enterby,authby,orgn_br_code) values('" + mergCode + "'," + i + ",'" + acno + "','','','','','" + orgCode
                        + "','" + enterBy + "','" + authBy + "','" + orgBrCode + "')");

                Integer insertChgVarient = insertChg.executeUpdate();
                if (insertChgVarient <= 0) {
                    throw new ApplicationException("Data Not Saved In Table");
                }
                i = i + 1;
            }

            /**
             * **** Maintain History For Joint Detail From AccountMaster And
             * Td_Accountmaster On Merged Customer Id *****
             */
            List getJtList = em.createNativeQuery("SELECT ACNO,case custid1 when " + mergCode + " then custid1 else '' end as custid1,"
                    + " case custid2 when " + mergCode + " then custid2 else '' end as custid2,case custid3 when " + mergCode + " then custid3 else '' end as custid3,"
                    + " case custid4 when " + mergCode + " then custid4 else '' end as custid4 FROM accountmaster WHERE (custid1 = '" + mergCode + "' or custid2 = '" + mergCode + "' "
                    + " or custid3 ='" + mergCode + "' or custid4 ='" + mergCode + "') union SELECT ACNO,case custid1 when " + mergCode + " then custid1 else '' end as custid1,"
                    + " case custid2 when " + mergCode + " then custid2 else '' end as custid2,case custid3 when " + mergCode + " then custid3 else '' end as custid3,"
                    + " case custid4 when " + mergCode + " then custid4 else '' end as custid4 FROM td_accountmaster WHERE (custid1 = '" + mergCode + "' or custid2 = '" + mergCode + "' "
                    + " or custid3 ='" + mergCode + "' or custid4 ='" + mergCode + "') ").getResultList();
            for (int l = 0; l < getJtList.size(); l++) {
                Vector getJtVect = (Vector) getJtList.get(l);
                String acno = getJtVect.get(0).toString();

                String cuId1 = getJtVect.get(1) == null ? getJtVect.get(1).toString() : "";
                String cuId2 = getJtVect.get(2) == null ? getJtVect.get(2).toString() : "";
                String cuId3 = getJtVect.get(3) == null ? getJtVect.get(3).toString() : "";
                String cuId4 = getJtVect.get(4) == null ? getJtVect.get(4).toString() : "";

                String cuId = "";
                List acCustList = em.createNativeQuery("SELECT custid FROM customerid WHERE acno = '" + acno + "'").getResultList();
                if (!acCustList.isEmpty()) {
                    Vector getCustVect = (Vector) acCustList.get(0);
                    cuId = getCustVect.get(0).toString();
                } else {
                    throw new ApplicationException("Cust Id Not Available For Acno " + acno);
                }

                Query insertChg = em.createNativeQuery("insert into cbs_id_merge_history(oldCustId,sNo,acno,jtId1,jtId2,jtId3,"
                        + "jtId4,newCustId,enterby,authby,orgn_br_code)"
                        + " values('" + cuId + "'," + i + ",'" + acno + "','" + cuId1 + "','" + cuId2 + "','" + cuId3 + "','" + cuId4 + "','"
                        + orgCode + "','" + enterBy + "','" + authBy + "','" + orgBrCode + "')");

                Integer insertChgVarient = insertChg.executeUpdate();
                if (insertChgVarient <= 0) {
                    throw new ApplicationException("Data Not Saved In Table");
                }
                i = i + 1;
            }

            /**
             * **** Delete From Other Tables For Merged Customer Id *****
             */
            int a;
            Query q4 = em.createNativeQuery("Delete from cbs_buyer_seller_limit_details where customerid ='" + mergCode + "'");
            a = q4.executeUpdate();

            Query q5 = em.createNativeQuery("Delete from cbs_cust_currencyinfo where customerid ='" + mergCode + "'");
            a = q5.executeUpdate();

            Query q6 = em.createNativeQuery("Delete from cbs_cust_delivery_channel_details where customerid ='" + mergCode + "'");
            a = q6.executeUpdate();

            Query q7 = em.createNativeQuery("Delete from cbs_cust_minorinfo where customerid ='" + mergCode + "'");
            a = q7.executeUpdate();

            Query q8 = em.createNativeQuery("Delete from cbs_cust_misinfo where customerid ='" + mergCode + "'");
            a = q8.executeUpdate();

            Query q9 = em.createNativeQuery("Delete from cbs_cust_nreinfo where customerid ='" + mergCode + "'");
            a = q9.executeUpdate();

            Query q10 = em.createNativeQuery("Delete from cbs_kyc_assets where customerid ='" + mergCode + "'");
            a = q10.executeUpdate();

            Query q11 = em.createNativeQuery("Delete from cbs_kyc_details where customerid ='" + mergCode + "'");
            a = q11.executeUpdate();

            Query q12 = em.createNativeQuery("Delete from cbs_kyc_loan where customerid ='" + mergCode + "'");
            a = q12.executeUpdate();

            Query q13 = em.createNativeQuery("Delete from cbs_related_persons_details where customerid ='" + mergCode + "'");
            a = q13.executeUpdate();

            Query q14 = em.createNativeQuery("Delete from cbs_trade_finance_information where customerid ='" + mergCode + "'");
            a = q14.executeUpdate();

            /**
             * **** Update Tables For Merged Customer Id *****
             */
            String cName = "";
            List getNameList = em.createNativeQuery("SELECT custname FROM cbs_customer_master_detail where customerid = '" + orgCode + "'").getResultList();
            if (!getNameList.isEmpty()) {
                Vector vCN = (Vector) getNameList.get(0);
                cName = vCN.get(0).toString();
            }

            Query updateQuery = em.createNativeQuery("update customerid set custid = '" + orgCode + "' "
                    + " where  custid = '" + mergCode + "'");
            updateQuery.executeUpdate();

            Query updateQuery1 = em.createNativeQuery("update fidility_accountmaster set custid = '" + orgCode + "' "
                    + " where  custid = '" + mergCode + "'");
            updateQuery1.executeUpdate();

            Query updateQuery2 = em.createNativeQuery("update fidility_schedule set custid = '" + orgCode + "' "
                    + " where  custid = '" + mergCode + "'");
            updateQuery2.executeUpdate();

            Query updateQuery3 = em.createNativeQuery("update loan_guarantordetails set GAR_CUSTID = '" + orgCode + "' "
                    + " where  GAR_CUSTID = '" + mergCode + "'");
            updateQuery3.executeUpdate();

            Query updateQuery4 = em.createNativeQuery("update accountmaster set JtName1 = '" + cName + "' , custid1 = '" + orgCode + "' "
                    + " where custid1 = '" + mergCode + "'");
            updateQuery4.executeUpdate();

            Query updateQuery5 = em.createNativeQuery("update accountmaster set JtName2 = '" + cName + "' , custid2 = '" + orgCode + "' "
                    + " where custid2 = '" + mergCode + "'");
            updateQuery5.executeUpdate();

            Query updateQuery6 = em.createNativeQuery("update accountmaster set JtName3 = '" + cName + "' , custid3 = '" + orgCode + "' "
                    + " where custid3 = '" + mergCode + "'");
            updateQuery6.executeUpdate();

            Query updateQuery7 = em.createNativeQuery("update accountmaster set JtName4 = '" + cName + "' , custid4 = '" + orgCode + "' "
                    + " where custid4 = '" + mergCode + "'");
            updateQuery7.executeUpdate();

            Query updateQuery8 = em.createNativeQuery("update td_accountmaster set JtName1 = '" + cName + "' , custid1 = '" + orgCode + "' "
                    + " where custid1 = '" + mergCode + "'");
            updateQuery8.executeUpdate();

            Query updateQuery9 = em.createNativeQuery("update td_accountmaster set JtName2 = '" + cName + "' , custid2 = '" + orgCode + "' "
                    + " where custid2 = '" + mergCode + "'");
            updateQuery9.executeUpdate();

            Query updateQuery10 = em.createNativeQuery("update td_accountmaster set JtName3 = '" + cName + "' , custid3 = '" + orgCode + "' "
                    + " where custid3 = '" + mergCode + "'");
            updateQuery10.executeUpdate();

            Query updateQuery11 = em.createNativeQuery("update td_accountmaster set JtName4 = '" + cName + "' , custid4 = '" + orgCode + "' "
                    + " where custid4 = '" + mergCode + "'");
            updateQuery11.executeUpdate();

            Query updateQuery12 = em.createNativeQuery("update share_holder set custId = '" + orgCode + "' "
                    + " where custId = '" + mergCode + "'");
            updateQuery12.executeUpdate();

            Query updateQuery13 = em.createNativeQuery("update share_holder set JtName1 = '" + cName + "' , JtId1 = '" + orgCode + "' "
                    + " where JtId1 = '" + mergCode + "'");
            updateQuery13.executeUpdate();

            Query updateQuery14 = em.createNativeQuery("update share_holder set JtName2 = '" + cName + "' , JtId2 = '" + orgCode + "' "
                    + " where JtId2 = '" + mergCode + "'");
            updateQuery14.executeUpdate();

            Query updateQuery15 = em.createNativeQuery("update cbs_customer_master_detail set SuspensionFlg = 'S' "
                    + " where customerid = '" + mergCode + "'");
            updateQuery15.executeUpdate();

            Query updateQuery16 = em.createNativeQuery("update cbs_loan_borrower_details set cust_id = '" + orgCode + "' "
                    + " where cust_id = '" + mergCode + "'");
            updateQuery16.executeUpdate();

            Query updateQuery17 = em.createNativeQuery("update cbs_id_merge_auth set auth = 'Y', authby = '" + authBy + "' "
                    + " where mergId = '" + mergCode + "' and orgId = '" + orgCode + "'");
            updateQuery17.executeUpdate();

            ut.commit();
            return "True";
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

    public List getDenoDateToShow(String acno, double recNo, String dt) throws ApplicationException {
        List denoDataToShowe = null;
        try {
            denoDataToShowe = em.createNativeQuery("select denomination_value,denomination,new_old_flag,ty from "
                    + " denomination_detail where acno = '" + acno + "' and recno = " + recNo + " and dt ='" + dt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return denoDataToShowe;
    }
}
