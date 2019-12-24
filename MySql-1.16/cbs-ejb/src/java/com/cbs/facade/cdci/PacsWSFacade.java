/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cdci;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.cdci.FiMiniStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PacsWSFacade implements PacsWSFacadeRemote {

    @PersistenceContext
    protected EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingMgmtFacadeRemote;
    @EJB
    CtsManagementFacadeRemote CtsRemote;
    /*@EJB
     private AccountOpeningFacadeRemote accOpeningFacade;*/
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<FiMiniStatementPojo> getMiniStatement(String acno, String opt) throws ApplicationException {
        double bal = 0.0;
        String dt = "18800101", fromdt = "18800101";
        String todt = null;
        int trantype = 0, ty = 0;
        String particulars = "";
        double cramt = 0, dramt = 0;
        Date date = new Date();
        double balance1 = 0;
        String minDt = "";
        String preDt = "";

        List<FiMiniStatementPojo> miniStatement = new ArrayList<FiMiniStatementPojo>();
        try {
            if (opt.equalsIgnoreCase("glb")) {
                List resultList = em.createNativeQuery("select glhead, description,type,dramt ,cramt,groupcode,subgroupcode from glbtempactypeentry").getResultList();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector vec = (Vector) resultList.get(i);
                        FiMiniStatementPojo pojo = new FiMiniStatementPojo();
                        pojo.setDate1(vec.get(0) == null ? "" : vec.get(0).toString());
                        pojo.setParticulars(vec.get(1) == null ? "" : vec.get(1).toString());
                        pojo.setChequeno(vec.get(2) == null ? "" : vec.get(2).toString());
                        pojo.setWithdrawl(Double.parseDouble(vec.get(3) == null ? "0" : vec.get(3).toString()));
                        pojo.setDeposit(Double.parseDouble(vec.get(4) == null ? "0" : vec.get(4).toString()));
                        pojo.setTrantype(Integer.parseInt(vec.get(5) == null ? "0" : vec.get(5).toString()));
                        pojo.setTy(Integer.parseInt(vec.get(6) == null ? "0" : vec.get(6).toString()));
                        miniStatement.add(pojo);
                    }
                }
            } else if (opt.equalsIgnoreCase("plr")) {
                List resultList = em.createNativeQuery("select glhead, description,amount4,groupcode,subgroupcode from PLTempEntry").getResultList();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector vec = (Vector) resultList.get(i);
                        FiMiniStatementPojo pojo = new FiMiniStatementPojo();
                        pojo.setDate1(vec.get(0) == null ? "" : vec.get(0).toString());
                        pojo.setParticulars(vec.get(1) == null ? "" : vec.get(1).toString());
                        pojo.setDeposit(Double.parseDouble(vec.get(2) == null ? "0" : vec.get(2).toString()));
                        pojo.setTrantype(Integer.parseInt(vec.get(3) == null ? "0" : vec.get(3).toString()));
                        pojo.setTy(Integer.parseInt(vec.get(4) == null ? "0" : vec.get(4).toString()));
                        miniStatement.add(pojo);
                    }
                }
            } else if (opt.equalsIgnoreCase("dbr")) {
                List resultList = em.createNativeQuery(" select acnum, acname,crbal1,crbal0,crbal2,crdittotal,drbal1,drbal0,drbal2,debittotal,groupcode,subgroupcode from daybook").getResultList();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector vec = (Vector) resultList.get(i);
                        FiMiniStatementPojo pojo = new FiMiniStatementPojo();
                        pojo.setDate1(vec.get(0) == null ? "" : vec.get(0).toString());
                        pojo.setParticulars(vec.get(1) == null ? "" : vec.get(1).toString());
                        pojo.setClgcr(Float.parseFloat(vec.get(2) == null ? "0" : vec.get(2).toString()));
                        pojo.setCashcr(Float.parseFloat(vec.get(3) == null ? "0" : vec.get(3).toString()));
                        pojo.setTrncr(Float.parseFloat(vec.get(4) == null ? "0" : vec.get(4).toString()));
                        pojo.setDeposit(Double.parseDouble(vec.get(5) == null ? "0" : vec.get(5).toString()));
                        pojo.setClgdr(Float.parseFloat(vec.get(6) == null ? "0" : vec.get(6).toString()));
                        pojo.setCashdr(Float.parseFloat(vec.get(7) == null ? "0" : vec.get(7).toString()));
                        pojo.setTrndr(Float.parseFloat(vec.get(8) == null ? "0" : vec.get(8).toString()));
                        pojo.setWithdrawl(Double.parseDouble(vec.get(9) == null ? "0" : vec.get(9).toString()));
                        pojo.setTrantype(Integer.parseInt(vec.get(10) == null ? "0" : vec.get(10).toString()));
                        pojo.setTy(Integer.parseInt(vec.get(11) == null ? "0" : vec.get(11).toString()));
                        miniStatement.add(pojo);
                    }
                }
            } else {
                List resultList1 = new ArrayList();
                String accountNature = ftsPostingMgmtFacadeRemote.getAccountNature(acno);
                if (accountNature == null) {
                    if (opt.equalsIgnoreCase("gls")) {
                        resultList1 = em.createNativeQuery("select  dt ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,recno from TEMP_GLSTAT"
                                + " where (dt<='" + todt + "' and dt>='" + fromdt + "' ) order by dt,trantime,recno").getResultList();//fromdt and toDt did not initialize yet.
                    } else if (opt.equalsIgnoreCase("glr")) {
                        resultList1 = em.createNativeQuery("select dt,'','',sum(dramt) ,sum(cramt),sum(isnull(balance,0)),'','','','' from temp_glstat group by dt").getResultList();
                    } else if (opt.equalsIgnoreCase("glb")) {
                        resultList1 = em.createNativeQuery("select '',description,'',dramt ,cramt,'',groupcode,subgroupcode,'','' from glbtempactypeentry").getResultList();
                    }
                } else {
//                    String reconTable = getTableName(accountNature);
                    List resultList = em.createNativeQuery("select min(dt), max(dt) from (select top 10 acno,dt from SE_RECON where acno='" + acno + "' order by dt desc) tmp(acno,dt)").getResultList();
                    if (!resultList.isEmpty()) {
                        for (int i = 0; i < resultList.size(); i++) {
                            Vector vec = (Vector) resultList.get(i);
                            minDt = ymmd.format((Date) (vec.get(0) == null ? date : vec.get(0)));
                            todt = ymmd.format((Date) (vec.get(1) == null ? "19000101" : vec.get(1)));
                        }
                    }
                    preDt = CbsUtil.dateAdd(minDt, -1);
                    balance1 = fnBalosForAccountAsOn(acno, preDt);
                    resultList1 = em.createNativeQuery("select TOP 10 dt ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,recno from se_recon where acno='" + acno + "' and auth='y' order by trantime DESC").getResultList();
                }
                if (!resultList1.isEmpty()) {
                    int l = 1;
                    for (int i = resultList1.size() - 1; i >= 0; i--) {
                        String p1 = "", p2 = "";
                        Vector vec = (Vector) resultList1.get(i);
                        ty = Integer.parseInt(vec.get(7).toString());
                        if (ty == 0) {
                            p1 = "By ";
                        } else {
                            p1 = "To ";
                        }
                        trantype = Integer.parseInt(vec.get(6).toString());
                        if (trantype == 0) {
                            p2 = "CSH ";
                        } else if (trantype == 1) {
                            p2 = "CLR ";
                        } else if (trantype == 8) {
                            p2 = "INT ";
                        } else {
                            p2 = "TRF ";
                        }
                        cramt = Double.parseDouble(vec.get(4).toString());
                        dramt = Double.parseDouble(vec.get(3).toString());
                        bal = balance1 + cramt - dramt;
                        if (l == 1) {
                            FiMiniStatementPojo pojo = new FiMiniStatementPojo();
                            pojo.setDate1(sdf.format(ymmd.parse(preDt)));
                            pojo.setDt(sdf.format(ymmd.parse(preDt)));
                            pojo.setParticulars("As ");
                            pojo.setChequeno("999");
                            pojo.setWithdrawl(0.0);
                            pojo.setDeposit(0.0);
                            pojo.setBalance(balance1);
                            pojo.setTrantype(6);
                            pojo.setTy(9);
                            pojo.setTrantime(sdf.format(ymmd.parse(preDt)));
                            pojo.setRecno(8989);
                            miniStatement.add(pojo);
                        }
                        l = l + 1;
                        balance1 = bal;
                        particulars = p1 + p2 + (vec.get(1) == null ? "" : vec.get(1).toString());
                        FiMiniStatementPojo pojo = new FiMiniStatementPojo();
                        pojo.setDate1(sdf.format((Date) (vec.get(0) == null ? date : vec.get(0))));
                        pojo.setDt(sdf.format((vec.get(0) == null ? date : vec.get(0))));
                        pojo.setParticulars(particulars);
                        pojo.setChequeno(vec.get(2) == null ? "" : vec.get(2).toString());
                        pojo.setWithdrawl(dramt);
                        pojo.setDeposit(cramt);
                        pojo.setBalance(bal);
                        pojo.setTrantype(Integer.parseInt(vec.get(6).toString()));
                        pojo.setTy(Integer.parseInt(vec.get(7).toString()));
                        pojo.setTrantime(sdf.format((Date) vec.get(8)));
                        pojo.setRecno(Float.parseFloat(vec.get(9).toString()));
                        miniStatement.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return miniStatement;
    }

    private float fnBalosForAccountAsOn(String acno, String dt) throws ApplicationException {
        float balance = 0;
        List list2 = new ArrayList();
        try {
            List list1 = em.createNativeQuery("Select AcctNature from accounttypemaster where AcctCode = SubString('" + acno + "',3,2)").getResultList();
            Vector element1 = (Vector) list1.get(0);
            String acctNature = element1.get(0).toString();
            if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                list2 = em.createNativeQuery("SELECT ISNULL(SUM(ISNULL(CRAMT,0))-SUM(ISNULL(DRAMT,0)),0) FROM SE_RECON WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
            }
            Vector element2 = (Vector) list2.get(0);
            balance = Float.parseFloat(element2.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return balance;
    }

    public Double balanceeEnquiry(String acNo) throws ApplicationException {
        Object balanceList = null;
        String acNat = "";
        try {
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode=(select accttype from accountmaster where acNo='" + acNo + "')").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            }
            if (acNat.equalsIgnoreCase("TL")) {
                balanceList = em.createNativeQuery("SELECT ROUND((SUM(ISNULL(CRAMT,0)) - SUM(ISNULL(DRAMT,0))),2) FROM SE_RECON WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            }
            if (balanceList != null) {
                Vector val = (Vector) balanceList;
                if (val.get(0) != null) {
                    return new Double(val.get(0).toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String fiPostingTxnProcess(String acno, String txnDt, String amt, int ty, String macId, String bcId, String remark, String mAcNo, String chqNo, String chqDt) throws ApplicationException {
        String msg = "false";
        int payBy = 1;
        int tranDesc = 0;
        Float tokenNo = 0.0f;
        Float recNo = 0.0f;
        int tranType = 0, pacsTy = 0;
        Double crAmt = 0.0d;
        Double drAmt = 0.0d;
        String custName = "";
        String custMemName = "";
        String details = "Pacs Transaction";
        String tokenPaid;
        String tokenPaidBy;
        UserTransaction ut = context.getUserTransaction();
        try {
            String currentBrnCode = ftsPostingMgmtFacadeRemote.getCurrentBrnCode(acno);
            if (amt.equalsIgnoreCase("") || amt.equalsIgnoreCase("0")) {
                throw new ApplicationException("Amount can not be 0.");
            } else if (acno.equalsIgnoreCase("") || acno.length() < 12) {
                throw new ApplicationException("Please insert valid Account no.");
            } else if (mAcNo.equalsIgnoreCase("") || mAcNo.length() < 12) {
                throw new ApplicationException("Please insert valid Member Account no.");
            } else if (ty != 0) {
                if (ty != 1) {
                    throw new ApplicationException("TY should be 0 or 1.");
                }
            } else if (ty != 0) {
                if (chqNo == null || chqNo.equalsIgnoreCase("")) {
                    throw new ApplicationException("Cheque No. can not be blank");
                }
                if (chqDt == null || chqDt.equalsIgnoreCase("")) {
                    throw new ApplicationException("Cheque Date can not be blank");
                }
            } else if (bcId == null || bcId.equalsIgnoreCase("")) {
                throw new ApplicationException("Enter by field can not be blank.");
            } else if (macId == null || macId.equalsIgnoreCase("")) {
                throw new ApplicationException("Mac Id can not be blank.");
            }


            if (ty == 1) {
                payBy = 1;
                pacsTy = 0;
            } else {
                payBy = 3;
                pacsTy = 1;
            }

            Float trsNo = ftsPostingMgmtFacadeRemote.getTrsNo();
            ut.begin();

            em.createNativeQuery("INSERT INTO FI_TXN(ACNO,TXN_DT,TXN_AMT,CR_DR_STATUS,MACHINE_ID,BC_ID,TXN_ID,IN_TIME,MACNO,INSTNO,INSTDT)"
                    + " VALUES ('" + acno + "','" + txnDt + "'," + amt + "," + ty + ",'" + macId + "','" + bcId + "'," + trsNo + ",GETDATE(),'" + mAcNo + "','" + chqNo + "','" + chqDt + "')").executeUpdate();


            msg = ftsPostingMgmtFacadeRemote.ftsDateValidate(txnDt, currentBrnCode);
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return "" + msg;
            }

            msg = ftsPostingMgmtFacadeRemote.ftsAcnoValidate(acno, ty, "");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return "" + msg;
            }

            msg = ftsPostingMgmtFacadeRemote.ftsAcnoValidate(mAcNo, ty, "");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                return "" + msg;
            }

            if (ty == 0) {
                crAmt = Double.parseDouble(amt);
                drAmt = 0d;
            } else if (ty == 1) {
                crAmt = 0d;
                drAmt = Double.parseDouble(amt);
            }

            if (ty == 1) {
                String MSG1 = ftsPostingMgmtFacadeRemote.chkBal(acno, ty, tranDesc, "CA");
                if (MSG1.equalsIgnoreCase("CHECKBALANCE")) {
                    msg = ftsPostingMgmtFacadeRemote.checkBalance(acno, Double.parseDouble(amt), bcId);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        return "" + msg;
                    }
                }

                MSG1 = ftsPostingMgmtFacadeRemote.chkBal(mAcNo, ty, tranDesc, "TL");
                if (MSG1.equalsIgnoreCase("CHECKBALANCE")) {
                    msg = ftsPostingMgmtFacadeRemote.checkBalance(mAcNo, Double.parseDouble(amt), bcId);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        return "" + msg;
                    }
                }

                msg = CtsRemote.chequeValidate(acno, chqNo);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return "" + msg;
                }

                msg = ftsPostingMgmtFacadeRemote.ftsInstDateValidate(chqDt);
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    return "" + msg;
                }
            }

            custName = ftsPostingMgmtFacadeRemote.ftsGetCustName(acno);
            custMemName = ftsPostingMgmtFacadeRemote.ftsGetCustName(mAcNo);

            if (tranType == 0) {
                tokenPaid = "Y";
                tokenPaidBy = "SYSTEM";
                if (ty == 0) {
                    if ((recNo == null) || (recNo == 0)) {
                        recNo = ftsPostingMgmtFacadeRemote.getRecNo();
                    }

                    Query insertQuery1 = em.createNativeQuery("INSERT INTO RECON_CASH_D(ACNO ,CUSTNAME, TY, DT,VALUEDT, DRAMT, CRAMT,TRANTYPE, DETAILS, "
                            + "IY, INSTNO,INSTDT,ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,TERM_ID,"
                            + "ORG_BRNID,DEST_BRNID,TRAN_ID)"
                            + "values ('" + acno + "','" + custName + "'," + ty + ",'" + txnDt + "','" + txnDt + "'," + drAmt + "," + crAmt + "," + tranType + ",'"
                            + details + "',0,'" + chqNo + "','" + chqDt + "','" + bcId + "','Y'," + recNo + "," + payBy + ",'SYSTEM',"
                            + trsNo + "," + "GETDATE()" + "," + tranDesc + "," + tokenNo + ",'" + tokenPaidBy + "','A','','" + currentBrnCode + "','" + currentBrnCode + "',0)");

                    insertQuery1.executeUpdate();

                } else if (ty == 1) {
                    tokenPaid = "Y";
                    tokenPaidBy = "SYSTEM";

                    if ((recNo == null) || (recNo == 0)) {
                        recNo = ftsPostingMgmtFacadeRemote.getRecNo();
                    }
                    Query insertQuery1 = em.createNativeQuery("INSERT INTO RECON_CASH_D(ACNO ,CUSTNAME, TY, DT,VALUEDT, DRAMT, CRAMT,TRANTYPE, DETAILS, "
                            + "IY, INSTNO,INSTDT,ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, SUBTOKENNO,"
                            + "TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID)"
                            + "values ('" + acno + "','" + custName + "'," + ty + ",'" + txnDt + "','" + txnDt + "'," + drAmt + ","
                            + crAmt + "," + tranType + ",'" + details + "',0,'" + chqNo + "','" + chqDt + "','" + bcId + "','Y',"
                            + recNo + "," + payBy + ",'SYSTEM'," + trsNo + "," + "GETDATE()" + "," + tranDesc + "," + tokenNo + ",'"
                            + tokenPaidBy + "','A','','" + currentBrnCode + "','" + currentBrnCode + "'" + ",0)");

                    insertQuery1.executeUpdate();

                }
            }

            /*Pacs Account Entry*/

            msg = insertRecons("CA", acno, ty, Double.parseDouble(amt), txnDt, txnDt, tranType, details, bcId, trsNo, ymmd.format(new Date()), recNo, "Y", "SYSTEM", tranDesc, payBy,
                    chqNo, chqDt, tokenNo, "", "A", 0, "", 0f, "", "", currentBrnCode, currentBrnCode, 0, "", "", "", "");
            if (msg.equalsIgnoreCase("true")) {
                msg = ftsPostingMgmtFacadeRemote.updateBalance("CA", acno, crAmt, drAmt, "Y", "Y");
                if (msg.equalsIgnoreCase("true")) {
                    /*Member Account Entry*/
                    msg = insertRecons("TL", mAcNo, ty, Double.parseDouble(amt), txnDt, txnDt, tranType, details, bcId, trsNo, ymmd.format(new Date()), recNo, "Y", "SYSTEM", tranDesc, payBy,
                            chqNo, chqDt, tokenNo, "", "A", 0, "", 0f, "", "", currentBrnCode, currentBrnCode, 0, "", "", "", acno);
                    if (msg.equalsIgnoreCase("true")) {
                        msg = ftsPostingMgmtFacadeRemote.updateBalance("TL", mAcNo, crAmt, drAmt, "Y", "Y");
                        if (msg.equalsIgnoreCase("true")) {
                            /*GL Head Entry*/
                            String glHead = currentBrnCode + "0698989801";
                            msg = insertRecons("PO", glHead, pacsTy, Double.parseDouble(amt), txnDt, txnDt, tranType, details, bcId, trsNo, ymmd.format(new Date()), recNo, "Y", "SYSTEM", tranDesc, payBy,
                                    chqNo, chqDt, tokenNo, "", "A", 0, "", 0f, "", "", currentBrnCode, currentBrnCode, 0, "", "", "", "");
                            if (msg.equalsIgnoreCase("true")) {
                                msg = ftsPostingMgmtFacadeRemote.updateBalance("PO", glHead, crAmt, drAmt, "Y", "Y");
                                if (!msg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException(msg);
                                }
                            } else {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            throw new ApplicationException(msg);
                        }
                    } else {
                        throw new ApplicationException(msg);
                    }
                } else {
                    throw new ApplicationException(msg);
                }
            } else {
                throw new ApplicationException(msg);
            }

            em.createNativeQuery("UPDATE FI_TXN SET MSG ='" + msg + "', OUT_TIME = GETDATE() WHERE ACNO = '" + acno + "' AND TXN_DT = '" + txnDt + "' AND TXN_ID =" + trsNo).executeUpdate();
            if ((ty == 1) && (acno.substring(2, 4)).equalsIgnoreCase("02")) {
                msg = ftsPostingMgmtFacadeRemote.updateCheque(acno, payBy, ty, Float.parseFloat(chqNo), "SYSTEM");
                if (!(msg.equalsIgnoreCase("TRUE"))) {
                    throw new ApplicationException(msg);
                }
            }

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return msg;
    }

    public String insertRecons(String actNature, String acno, Integer ty, double amt, String dt, String valueDt, Integer tranType, String details, String enterBy, Float trsno,
            String tranTime, Float recno, String auth, String authBy, Integer tranDesc, Integer payBy, String instno, String instDt, Float tokenNo, String tokenPaidBy, String subTokenNo,
            Integer iy, String tdAcno, Float voucherNo, String intFlag, String closeFlag, String orgBrnId, String destBrnId, Integer tranId, String termId, String adviceNo, String adviceBrnCode, String societyAcno) throws ApplicationException {
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
        if (instDt == null) {
            instDt = "";
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
                List checkDate = em.createNativeQuery("select getdate()").getResultList();
                Vector checkDateVect = (Vector) checkDate.get(0);
                tranTime = checkDateVect.get(0).toString();
            }
            if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
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
            } else {
                return "Invalid AccountNature of of A/c No :-" + acno;
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
                recno = ftsPostingMgmtFacadeRemote.getRecNo();
            }
            if (recno == 0) {
                return "Problem in Recno generation for Account No :- " + acno;
            }

            if (ty == 1) {
                balance = balance;
            } else {
                balance = balance - dramt + cramt;
            }

            if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                Integer varinsertReconList = em.createNativeQuery("insert into se_recon (Acno,SocAcno,Balance,Dt,CrAmt,DrAmt,PRN,INST,CHG,Ty,TranType,recno,trsno,instno,payby,"
                        + "iy,auth,EnterBy,authby,tokenpaid,tokenpaidBy,TranDesc,tokenno,SubTokenNo,Trantime,Details,TxnBrn,ValueDt,InstDt) "
                        + "values('" + acno + "','" + societyAcno + "'," + balance + ",'" + dt + "'," + cramt + "," + dramt + ",0,0,0," + ty + "," + tranType + ","
                        + "" + recno + "," + trsno + ",'" + instno + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "','" + authBy + "','',''," + tranDesc + ","
                        + "" + tokenNo + ",'" + subTokenNo + "','" + tranTime + "','" + details + "','" + orgBrnId + "','" + dt + "','" + instDt + "')").executeUpdate();
                if (varinsertReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                Integer varinsertGlReconList = em.createNativeQuery("insert into gl_recon (acno,ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id,AdviceNo,AdviceBrnCode) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ","
                        + "'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recno + "," + payBy + ",'" + authBy + "'," + trsno + "," + tranDesc + ","
                        + "" + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + ","
                        + "'" + termId + "','" + adviceNo + "','" + adviceBrnCode + "' )").executeUpdate();
                if (varinsertGlReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Integer varinsertCaReconList = em.createNativeQuery("insert into ca_recon ( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id) values('" + acno + "'," + ty + ",'" + dt + "','" + valueDt + "'," + dramt + "," + cramt + ", " + balance + "," + tranType + ","
                        + "'" + details + "'," + iy + ",'" + instno + "','" + instDt + "','" + enterBy + "','" + auth + "'," + recno + "," + payBy + ",'" + authBy + "'," + trsno + "," + tranDesc + ","
                        + "" + tokenNo + ",'" + tokenPaidBy + "','" + subTokenNo + "','" + tranTime + "','" + orgBrnId + "','" + destBrnId + "'," + tranId + ","
                        + "'" + termId + "' )").executeUpdate();
                if (varinsertCaReconList <= 0) {
                    return "Insertion Problem in Recons for A/c No :- " + acno;
                }
            } else {
                return "Invalid Accountnature for A/c No :- " + acno;
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
