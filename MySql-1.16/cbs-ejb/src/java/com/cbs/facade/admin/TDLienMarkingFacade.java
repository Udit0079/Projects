/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  01 DECEMBER 2010
 */
package com.cbs.facade.admin;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.AcTransferAuthPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import java.io.File;
import java.io.FileInputStream;
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

@Stateless(mappedName = "TDLienMarkingFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TDLienMarkingFacade implements TDLienMarkingFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    TransactionManagementFacadeRemote trnsFacadeRemote;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    TxnAuthorizationManagementFacadeRemote trfAuthRemote;

    public List acctTypeCombo() throws ApplicationException {
        List actype = null;
        try {
            actype = em.createNativeQuery("Select AcctCode From accounttypemaster where acctnature in ('FD','MS','OF')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public String dlAcOpen() throws ApplicationException {
        String desc = null;
        try {
            List lt = em.createNativeQuery("Select Description From codebook Where GroupCode=51 and code=61").getResultList();
            if (!lt.isEmpty()) {
                Vector ele = (Vector) lt.get(0);
                desc = ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return desc;
    }

    public String auth(String userName, String brCode) throws ApplicationException {
        String msg = null;
        try {
            List lt = em.createNativeQuery("select levelId from securityinfo where userid='" + userName + "' and brncode='" + brCode + "' and levelId in (1,2)").getResultList();
            if (lt.isEmpty()) {
                msg = "False";
            } else {
                msg = "True";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public List customerDetail(String acno) throws ApplicationException {
        List lt = null;
        try {
            lt = em.createNativeQuery("select am.custname,cb.description,am.jtname1 from td_accountmaster am, codebook cb where am.acno='" + acno + "' and am.opermode=cb.code and cb.groupcode=4 ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return lt;
    }

    public List gridDetailLoad(String acno, String acNat) throws ApplicationException {
        List lt = null;
        try {
            if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                lt = em.createNativeQuery("Select Acno, voucherno, receiptNo, prinamt, date_format(fddt,'%d/%m/%Y'), date_format(matDt,'%d/%m/%Y'), date_format(TD_MadeDT,'%d/%m/%Y'), IntOpt, roi, status, seqno, coalesce(lien,'') From td_vouchmst "
                        + " where OFacno='" + acno + "' and status='c' AND LIEN='Y' order by status, voucherno").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                lt = em.createNativeQuery("Select Acno, voucherno, receiptNo, prinamt, date_format(fddt,'%d/%m/%Y'), date_format(matDt,'%d/%m/%Y'), date_format(TD_MadeDT,'%d/%m/%Y'), IntOpt, roi, status, seqno, coalesce(lien,'') From td_vouchmst "
                        + " where acno='" + acno + "' and status<>'c' order by status, voucherno").getResultList();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return lt;
    }

    public String tdLienPresentAmount(String acno, Float voucherno, Float prinamount) throws ApplicationException {
        Float pretds = 0.0f;
        Float preint = 0.0f;
        String remarks = "";
        Float presentamt = null;
        try {

            List chk1 = em.createNativeQuery("select VOUCHERNO,REMARKS from td_lien_update WHERE VOUCHERNO=" + voucherno + " AND TXNDATE IN (select MAX(TXNDATE) from td_lien_update  WHERE VOUCHERNO=" + voucherno + " and acno = '" + acno + "') and acno = '" + acno + "'").getResultList();
            if (!chk1.isEmpty()) {
                List chk2 = em.createNativeQuery("select REMARKS from td_lien_update WHERE VOUCHERNO=" + voucherno + " AND TXNDATE IN (select MAX(TXNDATE) from td_lien_update  WHERE VOUCHERNO=" + voucherno + " and acno = '" + acno + "') and acno = '" + acno + "'").getResultList();
                if (!chk2.isEmpty()) {
                    Vector ele = (Vector) chk2.get(0);
                    remarks = ele.get(0).toString();
                }
            }
            List chk3 = em.createNativeQuery("select sum(ifnull(Interest,0)) From td_interesthistory where acno='" + acno + "' and voucherno=" + voucherno + "").getResultList();
            if (!chk3.isEmpty()) {
                List chk4 = em.createNativeQuery("select coalesce(sum(ifnull(Interest,0)),0) From td_interesthistory where acno='" + acno + "' and voucherno=" + voucherno + " ").getResultList();
                if (!chk4.isEmpty()) {
                    Vector ele = (Vector) chk4.get(0);
                    preint = Float.parseFloat(ele.get(0).toString());
                }
            }
            List chk5 = em.createNativeQuery("select sum(ifnull(TDS,0)) From tdshistory where acno='" + acno + "' and voucherno=" + voucherno + "").getResultList();
            if (!chk5.isEmpty()) {
                List chk6 = em.createNativeQuery("select coalesce(sum(ifnull(TDS,0)),0) From tdshistory where acno='" + acno + "' and voucherno=" + voucherno + " ").getResultList();
                if (!chk6.isEmpty()) {
                    Vector ele = (Vector) chk6.get(0);
                    pretds = Float.parseFloat(ele.get(0).toString());
                }
            }
            presentamt = prinamount + preint - pretds;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return remarks + "*" + presentamt.toString();
    }

    public String saveLienMarkingDetail(Float ReceiptNo, Float VchNo, String Actype, String AcNO, String LAcNO, String chkLien, String AUTH, String enteredby, String remark, String Loan_Lien_Call, String tmpSecType, String DLAccOpen_Lien, String BillLcBg_Lien, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
            ut.begin();
            int var1 = 0, var2 = 0, var3 = 0, var4 = 0;
            String LienD = null;
            //Float prinAmtD = 0.0f;
            // String FdDtD = null;
            // String MatDtD = null;
            // Float RoiD = 0.0f;
            //  String IntOptD = null;
            String Tempbd = null;
            String acctNo = null;
            Float VoucherNo = 0.0f;
            Float Vreceiptno = 0.0f;
            Float prinAmt = 0.0f;
            String FDDt = null;
            String TDCodeDesc = null;
            String MatDt = null;
            Integer AutoSno = null;
            Float ROI = 0.0f;
            String TmpMsg = null;

            if (ReceiptNo == null) {
                ut.rollback();
                msg = "PLEASE SELECT RECEIPT NO FROM GRID !!!";
                return msg;
            }
            if (VchNo == null) {
                ut.rollback();
                msg = "VOUCHER NO PASSED BLANK !!!";
                return msg;
            }

            if (Actype == null) {
                ut.rollback();
                msg = "ACCOUNT TYPE PASSED BLANK !!!";
                return msg;
            }

            if (AcNO == null) {
                ut.rollback();
                msg = "ACCOUNT NO. PASSED BLANK !!!";
                return msg;
            }
            String brcode = fts.getCurrentBrnCode(AcNO);
            List dtList = em.createNativeQuery("SELECT date_format(DATE,'%Y-%m-%d') FROM bankdays WHERE DAYENDFLAG = 'N' and brncode = '" + brcode + "'").getResultList();
            if (!dtList.isEmpty()) {
                Vector dtv = (Vector) dtList.get(0);
                Tempbd = dtv.get(0).toString();
            } else {
                ut.rollback();
                msg = "SERVER DATE NOT FOUND !!!";
                return msg;
            }

            if (Actype.equalsIgnoreCase(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                List chk1 = em.createNativeQuery("Select coalesce(Lien,''),prinAmt,FdDt,MatDt,Roi,IntOpt From td_vouchmst Where Voucherno=" + VchNo + " and OFacno='" + AcNO + "'").getResultList();
                if (!chk1.isEmpty()) {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector ele = (Vector) chk1.get(i);
                        LienD = ele.get(0).toString();
                        //  prinAmtD = Float.parseFloat(ele.get(1).toString());
                        //  FdDtD = ele.get(2).toString();
                        //  MatDtD = ele.get(3).toString();
                        // RoiD = Float.parseFloat(ele.get(4).toString());
                        // IntOptD = ele.get(5).toString();
                    }
                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("Yes")) {
                        ut.rollback();
                        msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                        return msg;
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("No")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("No"))) {
                        ut.rollback();
                        msg = "Lien Is Already Removed Against- " + AcNO + " / " + VchNo;
                        return msg;
                    }

                    if (DLAccOpen_Lien.equalsIgnoreCase("True")) {
                        if (LienD.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            msg = "Sorry, You Cannot Remove Lien From Here !!!";
                            return msg;
                        } else {
                            ut.rollback();
                            msg = "";
                            return msg;
                        }
                    } else if (BillLcBg_Lien.equalsIgnoreCase("True")) {
                        if (LienD.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                            return msg;
                        }
                    }

                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("No")) {
                        if (AUTH.equalsIgnoreCase("True")) {
                            if (Actype.equalsIgnoreCase(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and OFacno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE OFacno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            } else {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            }
                            msg = "Lien Mark Is Removed Against- " + AcNO + " / " + VchNo;
                        } else {
                            ut.rollback();
                            msg = "Lien Mark Can Be Removed By Manager Login !!!";
                            return msg;
                        }
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("Yes")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("Yes"))) {
                        List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                        for (int i = 0; i < chk2.size(); i++) {
                            Vector ele = (Vector) chk2.get(i);
                            acctNo = ele.get(0).toString();
                            VoucherNo = Float.parseFloat(ele.get(1).toString());
                            Vreceiptno = Float.parseFloat(ele.get(2).toString());
                        }
                        Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                        var1 = insertQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien =SUBSTRING('" + chkLien + "',1,1) WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                        var2 = updateQuery.executeUpdate();
                        msg = "Lien Is Marked Against- " + AcNO + " / " + VchNo;
                    }

                    if (chkLien.equalsIgnoreCase("Yes")) {
                        if (Loan_Lien_Call.equalsIgnoreCase("True")) {
                            List chk2 = em.createNativeQuery("Select a.prinAmt,a.FDDt,a.MatDt,a.ROI From td_vouchmst a,td_accountmaster b Where a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "'and a.acno=b.acno").getResultList();
                            if (chk2.isEmpty()) {
                                ut.rollback();
                                msg = "";
                                return msg;
                            } else {
                                List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a,td_accountmaster b"
                                        + " WHERE a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();
                                if (!chk3.isEmpty()) {
                                    for (int i = 0; i < chk3.size(); i++) {
                                        Vector ele = (Vector) chk3.get(i);
                                        prinAmt = Float.parseFloat(ele.get(0).toString());
                                        FDDt = ele.get(1).toString();
                                        MatDt = ele.get(2).toString();
                                        ROI = Float.parseFloat(ele.get(3).toString());
                                    }
                                }
                                List chk4 = em.createNativeQuery("SELECT ifnull(Description,'') FROM codebook WHERE GroupCode=51 and code=61").getResultList();
                                if (!chk4.isEmpty()) {
                                    Vector ele = (Vector) chk4.get(0);
                                    TDCodeDesc = ele.get(0).toString();
                                }
                                List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + LAcNO + "'").getResultList();
                                if (!chk5.isEmpty()) {
                                    Vector ele = (Vector) chk5.get(0);
                                    AutoSno = Integer.parseInt(ele.get(0).toString());
                                    AutoSno = AutoSno + 1;
                                }
                                TmpMsg = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY): ";
                                Query insertQuery = em.createNativeQuery("INSERT INTO loansecurity (acno,sno,security,particulars,matdate,lienvalue,matValue,issuedate,"
                                        + " status,Remarks,enteredby,entrydate,SecurityOption,SecurityChg,lienacno)"
                                        + " VALUES('" + LAcNO + "'," + AutoSno + ",'" + tmpSecType + "',SUBSTRING('" + AcNO + "',3,2),'" + MatDt + "'," + prinAmt + "," + prinAmt + ",'" + FDDt + "',"
                                        + " 'Active','" + TmpMsg + "','" + enteredby + "','" + Tempbd + "','" + TDCodeDesc + "','Lien','" + AcNO + "')");
                                var3 = insertQuery.executeUpdate();

                            }
                        }
                    }
                }
            } else {
                List chk1 = em.createNativeQuery("Select coalesce(Lien,''),prinAmt,FdDt,MatDt,Roi,IntOpt"
                        + " From td_vouchmst Where Voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                if (!chk1.isEmpty()) {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector ele = (Vector) chk1.get(i);
                        LienD = ele.get(0).toString();
                        prinAmt = Float.parseFloat(ele.get(1).toString());
                        //  FdDtD = ele.get(2).toString();
                        //  MatDtD = ele.get(3).toString();
                        ROI = Float.parseFloat(ele.get(4).toString());
                        //  IntOptD = ele.get(5).toString();
                    }
                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("Yes")) {
                        ut.rollback();
                        msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                        return msg;
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("No")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("No"))) {
                        ut.rollback();
                        msg = "Lien Is Already Removed Against- " + AcNO + " / " + VchNo;
                        return msg;
                    }
                    if (DLAccOpen_Lien.equalsIgnoreCase("True")) {
                        if (LienD.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            msg = "Sorry, You Cannot Remove Lien From Here !!!";
                            return msg;
                        } else {
                            ut.rollback();
                            msg = "";
                            return msg;
                        }
                    } else if (BillLcBg_Lien.equalsIgnoreCase("True")) {
                        if (LienD.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            msg = "Lien Is Already Marked Against- " + AcNO + " / " + VchNo;
                            return msg;
                        }
                    }
                    if (LienD.equalsIgnoreCase("Y") && chkLien.equalsIgnoreCase("No")) {
                        if (AUTH.equalsIgnoreCase("True")) {
                            if (Actype.equalsIgnoreCase(CbsAcCodeConstant.OF_AC.getAcctCode())) {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and OFacno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE OFacno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            } else {
                                List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                                if (!chk2.isEmpty()) {
                                    for (int i = 0; i < chk2.size(); i++) {
                                        Vector ele = (Vector) chk2.get(i);
                                        acctNo = ele.get(0).toString();
                                        VoucherNo = Float.parseFloat(ele.get(1).toString());
                                        Vreceiptno = Float.parseFloat(ele.get(2).toString());
                                    }

                                    List chLienBrn = em.createNativeQuery("select lienstatus,ifnull(marked_branch,'') from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + " "
                                            + "and txndate = (select max(txndate) from td_lien_update where acno = '" + AcNO + "' and voucherno = " + VchNo + " and receiptno = " + Vreceiptno + ")").getResultList();

                                    String lStat = "", mBrn = "";
                                    if (!chLienBrn.isEmpty()) {
                                        Vector ele = (Vector) chLienBrn.get(0);
                                        lStat = ele.get(0).toString();
                                        mBrn = ele.get(1).toString();
                                    }

                                    if (lStat.equalsIgnoreCase("Y")) {
                                        if (mBrn.equalsIgnoreCase(brnCode)) {
                                            Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                                    + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                                            var1 = insertQuery.executeUpdate();
                                            Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien = '' WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                                            var2 = updateQuery.executeUpdate();
                                        } else {
                                            ut.rollback();
                                            msg = "Lien Can Be Removed Only From Marked Branch";
                                            return msg;
                                        }
                                    } else {
                                        ut.rollback();
                                        msg = "Lien Status Is Removed In Td_lien_update";
                                        return msg;
                                    }
                                }
                            }
                            msg = "Lien Mark Is Removed Against- " + AcNO + " / " + VchNo;

                        } else {
                            ut.rollback();
                            msg = "Lien Mark Can Be Removed By Manager Login !!!";
                            return msg;
                        }
                    } else if ((LienD.equalsIgnoreCase("N") && chkLien.equalsIgnoreCase("Yes")) || (LienD.equalsIgnoreCase("") && chkLien.equalsIgnoreCase("Yes"))) {
                        List chk2 = em.createNativeQuery("SELECT acno,VoucherNo,receiptno from td_vouchmst where voucherno=" + VchNo + " and acno='" + AcNO + "'").getResultList();
                        for (int i = 0; i < chk2.size(); i++) {
                            Vector ele = (Vector) chk2.get(i);
                            acctNo = ele.get(0).toString();
                            VoucherNo = Float.parseFloat(ele.get(1).toString());
                            Vreceiptno = Float.parseFloat(ele.get(2).toString());
                        }
                        Query insertQuery = em.createNativeQuery("INSERT td_lien_update(acno,voucherno,receiptno,enterby,txndate,lienstatus,actype,marked_branch,remarks) "
                                + " VALUES('" + acctNo + "'," + VoucherNo + "," + Vreceiptno + ",'" + enteredby + "',NOW(),SUBSTRING('" + chkLien + "',1,1),SUBSTRING('" + acctNo + "',3,2),'" + brnCode + "','" + remark + "')");
                        var1 = insertQuery.executeUpdate();
                        Query updateQuery = em.createNativeQuery("UPDATE td_vouchmst SET Lien =SUBSTRING('" + chkLien + "',1,1) WHERE acno='" + AcNO + "' and voucherno=" + VchNo + "");
                        var2 = updateQuery.executeUpdate();
                        msg = "Lien Is Marked Against- " + AcNO + " / " + VchNo;
                    }
                    if (chkLien.equalsIgnoreCase("Yes")) {
                        if (Loan_Lien_Call.equalsIgnoreCase("True")) {
                            List chk2 = em.createNativeQuery("Select a.prinAmt,a.FDDt,a.MatDt,a.ROI From td_vouchmst a,td_accountmaster b Where a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();

                            if (chk2.isEmpty()) {
                                ut.rollback();
                                msg = "";
                                return msg;
                            } else {
                                List chk3 = em.createNativeQuery("SELECT a.prinAmt,a.FDDt,a.MatDt,a.ROI FROM td_vouchmst a,td_accountmaster b"
                                        + " WHERE a.voucherNo = " + VchNo + " and b.accttype = '" + Actype + "' and a.acno = '" + AcNO + "' and a.acno=b.acno").getResultList();
                                if (!chk3.isEmpty()) {
                                    for (int i = 0; i < chk3.size(); i++) {
                                        Vector ele = (Vector) chk3.get(i);
                                        prinAmt = Float.parseFloat(ele.get(0).toString());
                                        FDDt = ele.get(1).toString();
                                        MatDt = ele.get(2).toString();
                                        // ROI = Float.parseFloat(ele.get(3).toString());
                                    }
                                }
                                List chk4 = em.createNativeQuery("SELECT ifnull(Description,'') FROM codebook WHERE GroupCode=51 and code=61").getResultList();
                                if (!chk4.isEmpty()) {
                                    Vector ele = (Vector) chk4.get(0);
                                    TDCodeDesc = ele.get(0).toString();
                                }
                                List chk5 = em.createNativeQuery("SELECT ifnull(max(Sno),0) From loansecurity Where Acno= '" + LAcNO + "'").getResultList();
                                if (!chk5.isEmpty()) {
                                    Vector ele = (Vector) chk5.get(0);
                                    AutoSno = Integer.parseInt(ele.get(0).toString());
                                    AutoSno = AutoSno + 1;
                                }
                                TmpMsg = "DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY): ";
                                Query insertQuery = em.createNativeQuery("INSERT INTO loansecurity (acno,sno,security,particulars,matdate,lienvalue,matValue,issuedate,"
                                        + " status,Remarks,enteredby,entrydate,SecurityOption,SecurityChg,lienacno)"
                                        + " VALUES('" + LAcNO + "'," + AutoSno + ",'" + tmpSecType + "',SUBSTRING('" + AcNO + "',3,2),'" + MatDt + "'," + prinAmt + "," + prinAmt + ",'" + FDDt + "',"
                                        + " 'Active',CONCAT('DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):', '" + AcNO + "','; VchNo:" + VchNo + "', '; ROI:' "
                                        + ", cast(" + ROI + " as char(10)) , '; Present Amt:' , cast(" + prinAmt + " as char(20))),'" + enteredby + "','" + Tempbd + "','" + TDCodeDesc + "','Lien','" + AcNO + "')");
                                var3 = insertQuery.executeUpdate();
                            }
                        }

                    }
                }
            }
//            ut.commit();
//            return msg;
            if (var1 > 0 && var2 > 0) {
                ut.commit();
                return msg;
            } else {
                ut.rollback();
                return "RECORD NOT SAVED !!!";
            }

        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(ex);
            }
            throw new ApplicationException(ex);
        }
        //return msg;
    }

    /**
     * *****************Methods for
     * ImageSaveBean********************************
     */
    public String saveImages(String[] children, String dirName, String Enterby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (children != null) {
                int count = children.length;
                int insCnt = 0;
                for (int i = 0; i < children.length; i++) {
                    // Get filename of file or directory
                    String filename = children[i];
                    String Name = filename.substring(0, 12);
                    String OldAcNo = Name;
                    if (!(new File(dirName + File.separatorChar + filename)).isDirectory()) {
                        if (Name.length() != 12) {
                            ut.rollback();
                            return "File is Corrupted";
                        } else {
                            FileInputStream fin = null;
                            try {
                                File file = new File(dirName + File.separatorChar + filename);
                                fin = new FileInputStream(file);

                                byte[] bytes = new byte[(int) file.length()];
                                fin.read(bytes);
                                String encBytes = Base64.encodeBytes(bytes);

                                int SrN = Integer.parseInt(Name.substring(11));
                                Query insertRecon = em.createNativeQuery("insert into cbs_cust_image_detail (NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date) "
                                        + "values (" + "'" + Name + "'" + "," + "'" + OldAcNo + "'" + "," + SrN + "," + "'" + encBytes + "'" + "," + "'" + Enterby + "'" + "," + "'N'," + "''" + ",NOW())");

                                Integer countRow = insertRecon.executeUpdate();
                                if (countRow < 1) {
                                    ut.rollback();
                                    return "Data could not be inserted in recon.";
                                } else {
                                    insCnt = insCnt + 1;
                                }

                            } catch (Exception ex) {
                                throw new ApplicationException(ex);
                            } finally {
                                fin.close();
                            }
                        }
                    }
                }
                if (count == insCnt) {
                    ut.commit();
                    return "true";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "Data Not Inserted";
    }

    public String saveSingleImg(File fileImg, String Enterby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        FileInputStream fin = null;
        String encBytes = "";
        try {
            ut.begin();
            try {
                fin = new FileInputStream(fileImg);
                byte[] bytes = new byte[(int) fileImg.length()];
                fin.read(bytes);
                encBytes = Base64.encodeBytes(bytes);
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            } finally {
                fin.close();
            }

            String filename = fileImg.getName();
            String Name = filename.substring(0, 12);

            int SrN = Integer.parseInt(Name.substring(11));
            String newAcno = fts.getNewAccountNumber(Name.trim());
            if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                throw new ApplicationException("Either Account number does not exist or Image name is not like account number");
            }
            String bankCode = fts.getBankCode();

            boolean result = CbsUtil.generateSig(newAcno, encBytes, bankCode);
            if (result) {
                Query selectQuery = em.createNativeQuery("select NewAcNo from cbs_cust_image_detail where NewAcNo = '" + newAcno + "'");
                List acList = selectQuery.getResultList();
                if (acList.size() > 0) {
                    Query insertRecon = em.createNativeQuery("insert into cbs_cust_image_detail_his (NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date) select NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date from cbs_cust_image_detail where NewAcNo = '" + newAcno + "'");
                    Integer countRow = insertRecon.executeUpdate();
                    if (countRow < 1) {
                        throw new ApplicationException("Data could not be inserted in cbs_cust_image_detail_his.");
                    } else {
                        Query delQuery = em.createNativeQuery("delete from cbs_cust_image_detail where  NewAcNo = '" + newAcno + "'");
                        Integer row = delQuery.executeUpdate();
                        if (row < 1) {
                            throw new ApplicationException("Data could not be inserted in cbs_cust_image_detail.");
                        }
                    }
                }
                String image = bankCode + newAcno;

                Query insertRecon = em.createNativeQuery("insert into cbs_cust_image_detail (NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date) "
                        + "values (" + "'" + newAcno + "'" + "," + "'" + newAcno + "'" + "," + SrN + "," + "'" + image + "'" + "," + "'" + Enterby + "'" + "," + "'N'," + "''" + ",NOW())");
                Integer countRow = insertRecon.executeUpdate();
                if (countRow < 1) {
                    throw new ApplicationException("Data could not be inserted in cbs_cust_image_detail.");
                }
                ut.commit();
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "true";
    }

    public String saveCkycImage(String custId, String name, String Enterby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            List acList = em.createNativeQuery("select NewAcNo from cbs_cust_image_detail where NewAcNo = '" + custId + "' and image='client_" + name + "'").getResultList();
            if (acList.size() > 0) {
                Query insertRecon = em.createNativeQuery("insert into cbs_cust_image_detail_his (NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date) "
                        + "select NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date from cbs_cust_image_detail where NewAcNo = '" + custId + "' "
                        + "and image='client_" + name + "'");
                Integer countRow = insertRecon.executeUpdate();
                if (countRow < 1) {
                    throw new ApplicationException("Data could not be inserted in cbs_cust_image_detail_his.");
                }
                Query delQuery = em.createNativeQuery("delete from cbs_cust_image_detail where  NewAcNo = '" + custId + "' and image='client_" + name + "'");
                Integer row = delQuery.executeUpdate();
                if (row < 1) {
                    throw new ApplicationException("Data could not be inserted in cbs_cust_image_detail.");
                }
            }
            List srNoList = em.createNativeQuery("select ifnull(max(SrNo),0)+1 from cbs_cust_image_detail where NewAcNo = '" + custId + "'").getResultList();
            Vector vec = (Vector) srNoList.get(0);
            int srNo = Integer.parseInt(vec.get(0).toString());

            Query insertRecon = em.createNativeQuery("insert into cbs_cust_image_detail (NewAcNo,OldAcNo,SrNo,Image,enterby,Auth,AuthBy,scanned_date) "
                    + "values ('" + custId + "','" + custId + "'," + srNo + "," + "'client_" + name + "','" + Enterby + "','N','',NOW())");
            Integer countRow = insertRecon.executeUpdate();
            if (countRow < 1) {
                throw new ApplicationException("Data could not be inserted in cbs_cust_image_detail.");
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return "true";
    }

    public String checkPrimaryBrCode(String custId, String brCode) throws ApplicationException {
        try {
            List rsList = em.createNativeQuery("select primarybrCode from cbs_customer_master_detail where customerid='" + custId
                    + "' and primarybrCode = '" + brCode + "'").getResultList();
            if (rsList.isEmpty()) {
                return "false";
            } else {
                return "true";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    /**
     * *******************************Methods of
     * AccountTransfer***************************** Created by Ankit
     */
    public List getAlphacodes() throws ApplicationException {
        try {
            return em.createNativeQuery("select alphacode from branchmaster where brncode not in ('90','99')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private String getBranchCodeByAlphaCode(String alphaCode) throws ApplicationException {
        String brncode = "";
        try {
            List brncodeList = em.createNativeQuery("select brncode from branchmaster where alphacode like '" + alphaCode + "' ").getResultList();
            if (brncodeList.size() > 0) {
                Vector vec = (Vector) brncodeList.get(0);
                String brCode = vec.get(0).toString();
                brncode = brCode.length() < 2 ? "0" + brCode : brCode;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return brncode;
    }

    public String getAlphacodeByBranchCode(String brncode) throws ApplicationException {
        String alphaCode = "";
        int brcd = Integer.parseInt(brncode);
        try {
            List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + brcd).getResultList();
            if (alphaList.size() > 0) {
                Vector vec = (Vector) alphaList.get(0);
                alphaCode = vec.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return alphaCode;
    }

    public String checkAcNoForTransfer(String acno) throws ApplicationException {
        String msg = "";
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            List selectList = em.createNativeQuery("select * from cbs_account_transfer_history where acno='" + acno + "' and DATE_FORMAT(transfer_time,'%Y%m%d')='" + ymd.format(new Date()) + "' ").getResultList();
            if (selectList.size() > 0) {
                msg = "true";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }

    public String transferAccount(String acno, String originBranch, String respondingBranch, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        String msg = "";
        try {
            String resBrnCode = getBranchCodeByAlphaCode(respondingBranch);
            List bankDaysList = em.createNativeQuery("select daybeginflag,dayendflag from bankdays where brncode='" + resBrnCode + "' and date='" + ymd.format(new Date()) + "'").getResultList();
            if (bankDaysList.size() > 0) {
                Vector vec = (Vector) bankDaysList.get(0);
                if (vec.get(0).toString().equalsIgnoreCase("H")) {
                    return msg = "There is holiday of responding branch,so account can not be transfer !";
                } else if (vec.get(1).toString().equalsIgnoreCase("Y")) {
                    return msg = "Responding branch has done Day End,so account can not be transfer !";
                }
            }
            BigDecimal custId = null;
            List custIdList = em.createNativeQuery("select custid from customerid where acno like '" + acno + "'").getResultList();
            if (custIdList.size() > 0) {
                Vector vc = (Vector) custIdList.get(0);
                custId = new BigDecimal(vc.get(0).toString());
            } else {
                return msg = "Customer ID does not exist for this account no.";
            }
            List schemeList = em.createNativeQuery("select a.scheme_code,b.scheme_type from cbs_loan_acc_mast_sec a,cbs_scheme_general_scheme_parameter_master b"
                    + "  where a.scheme_code=b.scheme_code and a.acno='" + acno + "'").getResultList();
            String schemeCode = "";
            String schemeType = "";
            if (schemeList.size() > 0) {
                Vector schemeVec = (Vector) schemeList.get(0);
                schemeCode = schemeVec.get(0).toString();
                schemeType = schemeVec.get(1).toString();
            }
            ut.begin();
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMdd hh:MM:ss");
            Query insertQuery = em.createNativeQuery("insert into cbs_account_transfer_history values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            insertQuery.setParameter(1, originBranch);
            insertQuery.setParameter(2, resBrnCode);
            insertQuery.setParameter(3, acno);
            insertQuery.setParameter(4, "T");
            insertQuery.setParameter(5, "N");
            insertQuery.setParameter(6, "N");
            insertQuery.setParameter(7, "INR");
            insertQuery.setParameter(8, custId);
            insertQuery.setParameter(9, schemeCode);
            insertQuery.setParameter(10, schemeType);
            insertQuery.setParameter(11, enterBy);
            insertQuery.setParameter(12, ymdhms.format(new Date()));
            insertQuery.setParameter(13, "");
            insertQuery.setParameter(14, "");
            int count = insertQuery.executeUpdate();
            int count1 = em.createNativeQuery("update accountmaster set AccStatus=11, LastUpdateDt=DATE_FORMAT(CURDATE(),'%Y%m%d')  where acno='" + acno + "'").executeUpdate();
            if (count > 0 && count1 > 0) {
                ut.commit();
                msg = "Account has been successfully transferd";
            } else {
                ut.rollback();
                msg = "Account did'nt transfer !!";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }

    public List<AcTransferAuthPojo> getUnAuthTrfList(String brncode) throws ApplicationException {
        List<AcTransferAuthPojo> unAuthList = new ArrayList<AcTransferAuthPojo>();
        try {
            List selectList = em.createNativeQuery("SELECT A.ACNO,A.FROM_BRANCH,A.TO_BRANCH,A.ENTER_BY FROM cbs_account_transfer_history A,accountmaster B "
                    + " WHERE A.ACNO=B.ACNO AND B.LASTUPDATEDT=DATE_FORMAT(A.TRANSFER_TIME,'%Y%m%d') AND B.ACCSTATUS=11 AND B.CURBRCODE ='" + brncode + "' "
                    + " AND B.LASTUPDATEDT=DATE_FORMAT(CURDATE(),'%Y%m%d')  AND TO_AUTH='N'").getResultList();
            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    Vector vec = (Vector) selectList.get(i);
                    AcTransferAuthPojo authPojo = new AcTransferAuthPojo();
                    if (vec.get(0) != null) {
                        authPojo.setAcno(vec.get(0).toString());
                    }
                    if (vec.get(1) != null) {
                        String fromBr = getAlphacodeByBranchCode(vec.get(1).toString());
                        authPojo.setFromBranch(fromBr);
                    }
                    if (vec.get(2) != null) {
                        String toBr = getAlphacodeByBranchCode(vec.get(2).toString());
                        authPojo.setToBranch(toBr);
                    }
                    if (vec.get(3) != null) {
                        authPojo.setEnterBy(vec.get(3).toString());
                    }
                    authPojo.setSno(i + 1);
                    authPojo.setAuth("N");
                    unAuthList.add(authPojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return unAuthList;
    }

    public String authorizeAcTransfer(AcTransferAuthPojo acTrfList, String format, String enterBy, String orgnBrCode) throws ApplicationException {
        String msg = "";
        String agentCode = "01";
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
            String acNature = fts.getAccountNature(acTrfList.getAcno());
            String acCode = fts.getAccountCode(acTrfList.getAcno());
            String glHead = "";
            if (!acCode.equalsIgnoreCase("A/C No. does not exist")) {
                List selectList = em.createNativeQuery("select glhead from accounttypemaster where acctcode='" + acCode + "'").getResultList();
                if (selectList.size() > 0) {
                    Vector vec = (Vector) selectList.get(0);
                    glHead = vec.get(0).toString();
                }
            } else {
                return "Entry could not be authorize";
            }
            List selectReconTdReconCaRecon = trnsFacadeRemote.selectFromReconTdReconCaRecon(acTrfList.getAcno(), acNature);
            if (!selectReconTdReconCaRecon.isEmpty()) {
                Vector vecReconTdReconCaRecon = (Vector) selectReconTdReconCaRecon.get(0);
                BigDecimal balance = new BigDecimal(vecReconTdReconCaRecon.get(0).toString());
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                String accountNo = orgnBrCode + glHead + agentCode;
                String hoGlHead = "";
                List hoAcccountList = em.createNativeQuery("select acno from gltable where acname like 'HEAD OFFICE'").getResultList();
                if (hoAcccountList.size() > 0) {
                    Vector vect = (Vector) hoAcccountList.get(0);
                    hoGlHead = orgnBrCode + vect.get(0).toString().substring(2, 10) + "01";
                }
                int trsNo = fts.getTrsNo().intValue();
                if (Double.parseDouble(balance.toString()) > 0.0) {
                    for (int i = 0; i < 2; i++) {
                        Query insertQuery = em.createNativeQuery("insert into recon_trf_d (Acno,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,Tran_id,Term_id,adviceNo,adviceBrnCode) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        if (i == 0) {
                            insertQuery.setParameter(1, accountNo);
                            insertQuery.setParameter(3, 0.0);
                            insertQuery.setParameter(4, balance);
                            insertQuery.setParameter(5, 0);
                            insertQuery.setParameter(7, fts.getRecNo());
                        } else {
                            insertQuery.setParameter(1, hoGlHead);
                            insertQuery.setParameter(3, balance);
                            insertQuery.setParameter(4, 0.0);
                            insertQuery.setParameter(5, 1);
                            insertQuery.setParameter(7, fts.getRecNo());
                        }

                        insertQuery.setParameter(2, ymd.format(new Date()));
                        insertQuery.setParameter(6, 2);
                        insertQuery.setParameter(8, trsNo);
                        insertQuery.setParameter(9, "");
                        insertQuery.setParameter(10, 3);
                        insertQuery.setParameter(11, 0);
                        insertQuery.setParameter(12, 0);
                        insertQuery.setParameter(13, "FOR ACCOUNT TRANSFER");
                        insertQuery.setParameter(14, orgnBrCode);
                        insertQuery.setParameter(15, orgnBrCode);
                        insertQuery.setParameter(16, ymdhms.format(new Date()));
                        insertQuery.setParameter(17, "N");
                        insertQuery.setParameter(18, "SYSTEM");
                        insertQuery.setParameter(19, "");
                        insertQuery.setParameter(20, ymd.format(new Date()));
                        insertQuery.setParameter(21, "");
                        insertQuery.setParameter(22, 0);
                        insertQuery.setParameter(23, "");
                        insertQuery.setParameter(24, "");
                        insertQuery.setParameter(25, "");
                        insertQuery.executeUpdate();
                    }
                } else {
                    for (int i = 0; i < 2; i++) {
                        Query insertQuery = em.createNativeQuery("insert into recon_trf_d (Acno,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,Tran_id,Term_id,adviceNo,adviceBrnCode) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        if (i == 0) {
                            insertQuery.setParameter(1, accountNo);
                            insertQuery.setParameter(3, balance);
                            insertQuery.setParameter(4, 0.0);
                            insertQuery.setParameter(5, 1);
                            insertQuery.setParameter(7, fts.getRecNo());
                        } else {
                            insertQuery.setParameter(1, hoGlHead);
                            insertQuery.setParameter(3, 0.0);
                            insertQuery.setParameter(4, balance);
                            insertQuery.setParameter(5, 0);
                            insertQuery.setParameter(7, fts.getRecNo());
                        }
                        insertQuery.setParameter(2, ymd.format(new Date()));
                        insertQuery.setParameter(6, 2);
                        trsNo = fts.getTrsNo().intValue();
                        insertQuery.setParameter(8, trsNo);
                        insertQuery.setParameter(9, "");
                        insertQuery.setParameter(10, 3);
                        insertQuery.setParameter(11, 0);
                        insertQuery.setParameter(12, 0);
                        insertQuery.setParameter(13, "FOR ACCOUNT TRANSFER");
                        insertQuery.setParameter(14, orgnBrCode);
                        insertQuery.setParameter(15, orgnBrCode);
                        insertQuery.setParameter(16, ymdhms.format(new Date()));
                        insertQuery.setParameter(17, "N");
                        insertQuery.setParameter(18, "SYSTEM");
                        insertQuery.setParameter(19, "");
                        insertQuery.setParameter(20, ymd.format(new Date()));
                        insertQuery.setParameter(21, "");
                        insertQuery.setParameter(22, 0);
                        insertQuery.setParameter(23, "");
                        insertQuery.setParameter(24, "");
                        insertQuery.setParameter(25, "");
                        insertQuery.executeUpdate();
                    }
                }
                ut.commit();
                msg = trfAuthRemote.authLocalTransaction(trsNo, enterBy, ymd.format(new Date()), orgnBrCode);
                if (!msg.equalsIgnoreCase("TRUE")) {
                    ut.begin();
                    int x = em.createNativeQuery("delete from recon_trf_d where trsno=" + trsNo).executeUpdate();
                    System.out.println("delted entry " + x);
                    ut.commit();
                    return msg;
                } else {
                    ut.begin();
                    String toBrCode = getBranchCodeByAlphaCode(acTrfList.getToBranch());
                    String fromBrCode = getBranchCodeByAlphaCode(acTrfList.getFromBranch());
                    String curBrnCode = fts.getCurrentBrnCode(acTrfList.getAcno());
                    if (curBrnCode.equalsIgnoreCase(fromBrCode)) {
                        em.createNativeQuery("update cbs_account_transfer_history set from_auth='Y', From_auth_by='" + enterBy + "' where acno='" + acTrfList.getAcno() + "' and from_branch='" + orgnBrCode + "'").executeUpdate();
                        int updateAc = em.createNativeQuery("update accountmaster set AccStatus=11, LastUpdateDt=DATE_FORMAT(CURDATE(),'%Y%m%d') ,CurBrCode='" + toBrCode + "' where acno='" + acTrfList.getAcno() + "'").executeUpdate();
                        if (updateAc <= 0) {
                            ut.rollback();
                            return "Entry could not be authorize !!";
                        }
                    } else if (curBrnCode.equalsIgnoreCase(toBrCode)) {
                        em.createNativeQuery("update cbs_account_transfer_history set to_auth='Y', to_auth_by='" + enterBy + "' where acno='" + acTrfList.getAcno() + "' and to_branch='" + orgnBrCode + "'").executeUpdate();
                        int updateAc = em.createNativeQuery("update accountmaster set AccStatus=1, LastUpdateDt=DATE_FORMAT(CURDATE(),'%Y%m%d')  where acno='" + acTrfList.getAcno() + "'").executeUpdate();
                        if (updateAc <= 0) {
                            ut.rollback();
                            return "Entry could not be authorize !!";
                        }
                    }
                    ut.commit();
                    return msg;
                }
            } else {
                return "Entry could not be authorize";
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        //  return msg;
    }

    public List getDocumentType(String custId, String classification) throws ApplicationException {
        List select = new ArrayList();
        try {
//                if(classification.equalsIgnoreCase("POI")){
//                    select = em.createNativeQuery("select crt.REF_CODE, crt.REF_DESC from cbs_ref_rec_type crt where REF_REC_NO='311' and crt.REF_CODE = (select c.legal_document from cbs_customer_master_detail c where customerid='"+custId+"')").getResultList();
//                    if(select.isEmpty()){
//                        throw new ApplicationException("Please submit POI.");
//                    }
//                }
//                if(classification.equalsIgnoreCase("POA")){
//                    select = em.createNativeQuery("select crt.REF_CODE, crt.REF_DESC from  cbs_customer_master_detail c, cbs_ref_rec_type crt  where customerid='"+custId+"' and REF_REC_NO='313' and c.poa = crt.REF_CODE\n" +
//                                                  "union\n" +
//                                                  "select crt.REF_CODE, crt.REF_DESC from  cbs_customer_master_detail c, cbs_ref_rec_type crt  where customerid='"+custId+"' and REF_REC_NO='313' and c.MailPoa = crt.REF_CODE\n" +
//                                                  "union\n" +
//                                                  "select crt.REF_CODE, crt.REF_DESC from  cbs_customer_master_detail c, cbs_ref_rec_type crt  where customerid='"+custId+"' and REF_REC_NO='313' and c.JuriPoa = crt.REF_CODE").getResultList();
//                    if(select.isEmpty()){
//                        throw new ApplicationException("POA not found corresponding Customer ID.");
//                    }
//                }
            if (classification.equalsIgnoreCase("CKYCRIMAGE")) {
                Vector vec = (Vector) em.createNativeQuery("select Replace(CustImage,'|',',') from cbs_customer_master_detail where customerid ='" + custId + "'").getSingleResult();
                String imageCode = vec.get(0).toString();
                if (imageCode.isEmpty()) {
                    throw new ApplicationException("Image code does not exist in Customer Master Detail !");
                }
                select = em.createNativeQuery("select REF_CODE, REF_DESC from cbs_ref_rec_type where cbs_ref_rec_type.REF_REC_NO ='362' and REF_CODE in(" + imageCode + ")").getResultList();
                if (select.isEmpty()) {
                    throw new ApplicationException("Image Code does not exist in CBS REF REC !");
                }
            }
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
