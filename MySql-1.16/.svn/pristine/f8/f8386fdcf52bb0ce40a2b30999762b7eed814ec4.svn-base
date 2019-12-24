/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.dds;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "PigMyFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PigMyFacade implements PigMyFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fts;
    @EJB
    private DDSManagementFacadeRemote ddsMgmtRemote;
    @EJB
    private SmsManagementFacadeRemote smsRemote;
    SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yy");
    SimpleDateFormat hmss = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    /**
     *
     * @param custId
     * @param payLoad
     * @return
     */
    @Override
    public String pigMeProcess(String custId, String payLoad) {
        UserTransaction ut = context.getUserTransaction();
        String message = "0000:SUCCESS";  //It is for success.
        BigInteger txnId = null;
        try {
            ut.begin();
            System.out.println("Pig-Me Request On CBS::::CustId Is-->" + custId + ":::Payload Is-->" + payLoad);
            if (custId == null || payLoad == null || custId.equals("") || payLoad.equals("") || custId.length() != 8) {
                throw new Exception("Problem In CustId And Payload Request Parameter Null/Blank "
                        + "As Well As CustId Length Checking. Request CustID is-->" + custId + " ::PayLoad is-->" + payLoad);
            }
            String[] pArr = payLoad.split(",");
            if (pArr.length != 8) {
                throw new Exception("Problem In PayLoad Length Validation That Should be 8. Request Payload is-->" + payLoad);
            }
            /**
             * Fetching and checking of values. pArr[0] ---> Branch Code pArr[1]
             * ---> Agent Code pArr[2] ---> A/c No.
             */
            if (pArr[0] == null || pArr[0].equals("") || pArr[0].trim().length() == 0) {
                throw new Exception("Invalid BrCode In Request Parameter Payload. Maximum Length Should Be Of 2.--->" + pArr[0]);
            }
            if (pArr[1] == null || pArr[1].equals("") || pArr[1].trim().length() == 0) {
                throw new Exception("Invalid AgCode In Request Parameter Payload. Maximum Length Should Be Of 2.--->" + pArr[1]);
            }
            if (pArr[2] == null || pArr[2].equals("") || pArr[2].trim().length() == 0) {
                throw new Exception("Invalid AcNo In Request Parameter Payload. Maximum Length Should Be Of 6.--->" + pArr[2]);
            }
            if (pArr[3] == null || pArr[3].equals("") || pArr[3].trim().length() == 0 || Double.parseDouble(pArr[3].trim()) == 0) {
                throw new Exception("Invalid RcptAmt In Request Parameter Payload--->" + pArr[3]);
            }
            if (pArr[5] == null || pArr[5].equals("") || pArr[5].trim().length() == 0 || pArr[5].trim().length() != 8) {
                throw new Exception("Invalid RcptDt In Request Parameter Payload--->" + pArr[5]);
            }
            if (pArr[7] == null || pArr[7].equals("") || pArr[7].trim().length() == 0) {
                throw new Exception("Invalid Rcpt No In Request Parameter Payload--->" + pArr[7]);
            }
            String brCode = ParseFileUtil.addTrailingZeros(String.valueOf(Integer.parseInt(pArr[0].trim())), 2);
            if (!fts.isBranchExists(brCode)) {
                throw new Exception("Problem In Branch Existence Checking.");
            }
            String agCode = ParseFileUtil.addTrailingZeros(String.valueOf(Integer.parseInt(pArr[1].trim())), 2);
            if (!fts.isActiveAgent(agCode, brCode)) {
                throw new Exception("Problem In Active Agent Checking.");
            }
            String acno = ParseFileUtil.addTrailingZeros(String.valueOf(Integer.parseInt(pArr[2].trim())), 6);
            acno = brCode + CbsAcCodeConstant.DS_AC.getAcctCode() + acno + agCode;
            if (!fts.isValidDddAccount(acno)) {
                throw new Exception("Account does not exist");
            }
            double amount = Double.parseDouble(pArr[3].trim());
            String receiptDt = ymd.format(dmyy.parse(pArr[5].trim()));
            //Logging Into CBS
            List checkList = em.createNativeQuery("select txn_id,cbs_return_code,cbs_return_reason from "
                    + "online_pigme_request where client_id='" + custId + "' and branch_code='" + pArr[0].trim() + "' and "
                    + "agent_code='" + pArr[1].trim() + "' and acno='" + pArr[2].trim() + "' and "
                    + "receipt_amt=" + new BigDecimal(pArr[3].trim()) + " and close_bal=" + new BigDecimal(pArr[4].trim()) + " and "
                    + "receipt_dt='" + pArr[5].trim() + "' and receipt_time='" + pArr[6].trim() + "' and receipt_no ='" + pArr[7].trim() + "' order by txn_id").getResultList();
            if (checkList.isEmpty()) {
                txnId = getTxnId();
                int n = em.createNativeQuery("insert into online_pigme_request(txn_id,client_id,branch_code,agent_code,"
                        + "acno,receipt_amt,close_bal,receipt_dt,receipt_time,cbs_received_time,"
                        + "cbs_return_code,cbs_return_reason,receipt_no) "
                        + "values(" + txnId + ",'" + custId + "','" + pArr[0].trim() + "','" + pArr[1].trim() + "',"
                        + "'" + pArr[2].trim() + "'," + new BigDecimal(pArr[3].trim()) + ","
                        + "" + new BigDecimal(pArr[4].trim()) + ",'" + pArr[5].trim() + "',"
                        + "'" + pArr[6].trim() + "',now(),'','','" + pArr[7].trim() + "')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In Request Logging In CBS At Start Phase.");
                }
            } else {
                Vector ele = (Vector) checkList.get(checkList.size() - 1);
                String returnCode = ele.get(1).toString().trim();
                if (returnCode.equals("0000")) {
                    ut.commit();
                    return "0000:Duplicate at CBS";
                } else if (returnCode.equals("0001") || returnCode.equals("")) {
                    txnId = getTxnId();
                    int n = em.createNativeQuery("insert into online_pigme_request(txn_id,client_id,branch_code,agent_code,"
                            + "acno,receipt_amt,close_bal,receipt_dt,receipt_time,cbs_received_time,"
                            + "cbs_return_code,cbs_return_reason,receipt_no) "
                            + "values(" + txnId + ",'" + custId + "','" + pArr[0].trim() + "','" + pArr[1].trim() + "',"
                            + "'" + pArr[2].trim() + "'," + new BigDecimal(pArr[3].trim()) + ","
                            + "" + new BigDecimal(pArr[4].trim()) + ",'" + pArr[5].trim() + "',"
                            + "'" + pArr[6].trim() + "',now(),'','','" + pArr[7].trim() + "')").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Request Logging In CBS At Start Phase On Failure.");
                    }
                }
            }

            //Transaction Processing
            String curDt = fts.getBusinessDate();
            String custName = fts.getCustName(acno);
            int txnMode = fts.getCodeForReportName("DDS-ONLINE-TXN-MODE"); //Either cash or transfer, 1 for transfer or 0 for cash, 2 for GLhead transaction
            int authMode = fts.getCodeForReportName("ONLINE-DDS-MODE"); // 1 for authorized or 0 for unauthorized

            Float idNo = 0f;
            if (txnMode == 0) {
                Float recno = fts.getRecNo();
                onlineCashTxn(brCode, curDt, acno, agCode, receiptDt, custName, amount, authMode, recno);
                idNo = recno;
            } else {
                Float trsNo = fts.getTrsNo();
                onlineXferTxn(brCode, curDt, acno, agCode, receiptDt, amount, txnMode, authMode, trsNo, pArr[7].trim());
                idNo = trsNo;
            }

            //Updation of request
            int n = em.createNativeQuery("update online_pigme_request set cbs_return_code='0000',"
                    + "cbs_return_reason='Success',batch_no=" + idNo + ",business_date='" + curDt + "' where "
                    + "txn_id=" + txnId + "").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem In Request Logging Status Updation.");
            }

            ut.commit();
            //Sending SMS In Case of Authorized Entry
            try {
                if ((txnMode == 1 || txnMode == 2) && authMode == 1) { //Trf Authorized
//                    smsRemote.sendTransactionalSms(SmsType.TRANSFER_DEPOSIT, acno, 2, 0, amount, dmy.format(new Date()), "");
                    smsRemote.sendTransactionalSms(SmsType.TRANSFER_DEPOSIT, acno, 2, 0, amount, dmy.format(ymd.parse(curDt)), "");
                } else if (txnMode == 0 && authMode == 1) { //Cash Authorized
//                    smsRemote.sendTransactionalSms(SmsType.CASH_DEPOSIT, acno, 0, 0, amount, dmy.format(new Date()), "");
                    smsRemote.sendTransactionalSms(SmsType.CASH_DEPOSIT, acno, 0, 0, amount, dmy.format(ymd.parse(curDt)), "");
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending SMS In Online DDS Mode To Account No:- " + acno);
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                String reason = "Un-Success";
                try {
                    ut.begin();
                    if (ex.getMessage() != null && !ex.getMessage().equals("")) {
                        reason = ex.getMessage().length() > 100 ? ex.getMessage().substring(0, 100) : ex.getMessage();
                    }
                    txnId = getTxnId();
                    String[] pArr = payLoad.split(",");
                    int n = em.createNativeQuery("insert into online_pigme_request(txn_id,client_id,branch_code,agent_code,"
                            + "acno,receipt_amt,close_bal,receipt_dt,receipt_time,cbs_received_time,"
                            + "cbs_return_code,cbs_return_reason,receipt_no) "
                            + "values(" + txnId + ",'" + custId + "','" + pArr[0].trim() + "','" + pArr[1].trim() + "',"
                            + "'" + pArr[2].trim() + "'," + new BigDecimal(pArr[3].trim()) + ","
                            + "" + new BigDecimal(pArr[4].trim()) + ",'" + pArr[5].trim() + "',"
                            + "'" + pArr[6].trim() + "',now(),'0001','" + reason + "','" + pArr[7].trim() + "')").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Request Logging In CBS At Start Phase On Failure.");
                    }
                    ut.commit();
                    message = "0001:" + reason; //It is for error.
                } catch (Exception sx) {
                    ut.commit();
                    message = "0001:" + sx.getMessage(); //It is for error.
                }
            } catch (Exception e) {
                System.out.println("Problem In pigMeProcess() Method--->" + e.getMessage());
                message = "0001:" + e.getMessage(); //It is for error.
            }
        }
        return message;
    }

    private String onlineXferTxn(String brCode, String curDt, String acno, String agCode, String receiptDt, double amount, int txnMode, int authMode, Float trsNo, String receiptNo) throws ApplicationException {
        String agentAcNo = "";
        String agentName = "";
        if (txnMode == 1) {
            List list = em.createNativeQuery("select da.agent_acno,am.custname,am.chequebook, atm.minbal,atm.minbal_chq,"
                    + "atm.acctnature,atm.accttype,da.name from ddsagent da,accountmaster am,accounttypemaster atm where "
                    + "da.brncode ='" + brCode + "' and da.agcode='" + agCode + "' and da.agent_acno = am.acno and "
                    + "am.accttype = atm.acctcode").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            Vector ele = (Vector) list.get(0);
            agentAcNo = ele.get(0).toString();
            agentName = ele.get(7).toString();
            int chBookFlag = Integer.parseInt(ele.get(2).toString());
            double minBal = 0;
            if (chBookFlag == 1) {
                minBal = Double.parseDouble(ele.get(4).toString());
            } else {
                minBal = Double.parseDouble(ele.get(3).toString());
            }

            int odAllowed = fts.getCodeForReportName("PIG-ME-OD-ALLOWED");
            if (ele.get(5).toString().trim().equalsIgnoreCase(CbsConstant.CURRENT_AC)
                    && ele.get(6).toString().trim().equalsIgnoreCase("OD") && odAllowed == 1) {
                String balResult = fts.checkBalForOdLimit(agentAcNo, amount, "System");
                if (!balResult.equalsIgnoreCase("1")) {
                    throw new ApplicationException("Insufficient Fund");
                }
            } else {
                double debitAcNoBal = Double.parseDouble(fts.ftsGetBal(agentAcNo));
                double availBal = debitAcNoBal - minBal;
                if (availBal < amount) {
                    throw new ApplicationException("Insufficient Fund");
                }
            }
        } else {
            List list = em.createNativeQuery("select da.agent_acno,am.acname from ddsagent da, gltable am where da.brncode ='" + brCode
                    + "' and da.agcode='" + agCode + "' and da.agent_acno = am.acno ").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            Vector ele = (Vector) list.get(0);
            agentAcNo = ele.get(0).toString();
            agentName = ele.get(1).toString();
        }
        Float recNo = fts.getRecNo();
//        Float trsNo = fts.getTrsNo();
        //Cash Mode-Unauthorize Handling.
        String auth = "N";
        String authBy = "";
        if (authMode == 1) {
            auth = "Y";
            authBy = "System";
        }

        String result = fts.ftsPosting43CBS(agentAcNo, 2, 1, amount, curDt, receiptDt, "System", brCode, brCode, 78, "DDS Collection for " + acno
                + " using receipt no " + receiptNo, trsNo, recNo, 0, "", auth, authBy, "", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "S");
        if (!result.substring(0, 4).equalsIgnoreCase("True")) {
            throw new ApplicationException(result);
        }
        recNo = fts.getRecNo();
        int stat = Integer.parseInt(fts.getAccountPresentStatus(acno).get(0));
        if (stat == 1 || stat == 2 || stat == 7 || stat == 10) {
            result = fts.ftsPosting43CBS(acno, 2, 0, amount, curDt, receiptDt, "System", brCode, brCode, 78, "DDS Collection by Agent " + agentName + " ("
                    + agentAcNo + ") and receipt no is" + receiptNo, trsNo, recNo, 0, "", auth, authBy, "", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "S");
            if (!result.substring(0, 4).equalsIgnoreCase("True")) {
                throw new ApplicationException(result);
            }
        } else {
            List consoleGlHeadList = em.createNativeQuery("SELECT ACNO FROM abb_parameter_info WHERE PURPOSE = 'DDS-SUNDRY-GLHEAD'").getResultList();
            if (consoleGlHeadList.isEmpty()) {
                throw new ApplicationException("Please Define DDS Sundry GLHead");
            }
            Vector consoleGlHeadVect = (Vector) consoleGlHeadList.get(0);
            String ddsSundryGlHead = fts.getCurrentBrnCode(acno).concat(consoleGlHeadVect.get(0).toString());
            
            List seqNoList = em.createNativeQuery("SELECT IFNULL(MAX(SEQNO),0)+1 FROM bill_sundry WHERE FYEAR = extract(year from '" + curDt + "')").getResultList();
            Vector seqNoVect = (Vector) seqNoList.get(0);
            float seqNo = Float.parseFloat(seqNoVect.get(0).toString());

            int rs = em.createNativeQuery("INSERT bill_sundry (FYEAR,ACNO,AMOUNT,ENTERBY,DT,STATUS,AUTH,TRANTYPE, SEQNO,TY,RECNO,ORIGINDT,AUTHBY,DETAILS) "
                    + "VALUES( extract(year from '" + curDt + "'),'" + ddsSundryGlHead + "','" + amount + "','System','" + curDt + "', 'ISSUED','Y',2," 
                    + seqNo + ",0," + recNo + ",'" + curDt + "','System','DDS Collection by Agent " + agentName + " for "+ acno +" using receipt no is" 
                    + receiptNo +"')").executeUpdate();
            if(rs <= 0) throw new ApplicationException("Problem in data insertion in bill_sundry");
            
            result = fts.ftsPosting43CBS(ddsSundryGlHead, 2, 0, amount, curDt, receiptDt, "System", brCode, brCode, 78, "DDS Collection by Agent " + agentName 
                    + " for "+ acno +" using receipt no is" + receiptNo, trsNo, recNo, 0, "", auth, authBy, "", 3, "", "", "", 0f, "", "", "", "", 0f, "", "", "", "S");
            if (!result.substring(0, 4).equalsIgnoreCase("True")) {
                throw new ApplicationException(result);
            }
        }

        return "true";
    }

    private String onlineCashTxn(String brCode, String curDt, String acno, String agCode, String receiptDt,
            String custName, double amount, int authMode, Float recno) throws ApplicationException {
        try {
//            Float recno = fts.getRecNo();
            //Cash Mode-Unauthorize Handling.
            if (authMode == 0) {
                String tokenPaid = "";
                String cashMode = ddsMgmtRemote.getCashMode(brCode);
                if (cashMode.equals("")) {
                    throw new ApplicationException("Cash mode status has not been set for your branch. " + brCode);
                }
                if (cashMode.equalsIgnoreCase("Y")) {
                    tokenPaid = "N";
                } else {
                    tokenPaid = "Y";
                }
                List list = em.createNativeQuery("select ifnull(max(cast(receiptno as unsigned)),0) as receipt_no from "
                        + "dds_auth_d where substring(acno,1,2)='" + brCode + "' and substring(acno,11,2)='" + agCode + "' "
                        + "and dt='" + curDt + "'").getResultList();
                Vector ele = (Vector) list.get(0);
                String receiptNo = String.valueOf(Integer.parseInt(ele.get(0).toString()) + 1);

                int n = em.createNativeQuery("insert into dds_auth_d(acno,ty,dt,dramt,cramt,trantype,receiptno,"
                        + "enterby,recno,tokenpaid) values('" + acno + "',0,'" + curDt + "',0," + amount + ",0,"
                        + "'" + receiptNo + "','System'," + recno + ",'" + tokenPaid + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Proble In DDS_AUTH_D Insertion.");
                }
            }
            //Cash Mode-Authorize Handling.
            if (authMode == 1) {
                int queryResult = em.createNativeQuery("insert into tokentable_credit(acno,custname,dt,trantime,cramt,"
                        + "dramt,payby,enterby,auth,trandesc,tokenpaid,tokenpaidby,authby,recno,trantype,ty,org_brnid,"
                        + "dest_brnid,valuedt,details) values ('" + acno + "','" + custName + "',"
                        + "'" + curDt + "',now()," + amount + ",0,3,'SYSTEM','Y',78,'Y','SYSTEM',"
                        + "'SYSTEM'," + recno + ",0,0,'" + brCode + "','" + brCode + "', '" + receiptDt + "',"
                        + "'Online Cash deposit from PIGME Machine')").executeUpdate();
                if (queryResult <= 0) {
                    throw new Exception("Problem In Tokentable Credit Insertion.");
                }

                queryResult = em.createNativeQuery("insert into recon_cash_d(acno ,custname, ty, dt,valuedt, dramt, cramt,"
                        + "trantype, details, iy, instno,enterby, auth, recno,payby,authby,trantime, trandesc, tokenno,"
                        + "tokenpaidby, subtokenno,term_id,org_brnid,dest_brnid,tran_id) values ('" + acno + "','" + custName + "',"
                        + "0,'" + curDt + "','" + receiptDt + "',0," + amount + ",0,'Online Cash deposit from PIGME Machine',1,'',"
                        + "'SYSTEM','Y'," + recno + ",3,'SYSTEM',now(),78,0,'SYSTEM','','','" + brCode + "',"
                        + "'" + brCode + "'" + ",0)").executeUpdate();
                if (queryResult <= 0) {
                    throw new Exception("Problem In Recon Cash D Insertion.");
                }
                queryResult = em.createNativeQuery("insert into ddstransaction(acno,ty,dt,valuedt,dramt,cramt,trantype,recno,"
                        + "details,iy,enterby,auth,payby,authby,trandesc,tokenno,subtokenno,tokenpaid,balance,favorof,"
                        + "trantime,instno,checkby,org_brnid,dest_brnid,tran_id,term_id,tokenpaidby) values('" + acno + "',"
                        + "0,'" + curDt + "','" + receiptDt + "',0," + amount + ",0," + recno + ","
                        + "'Online Cash deposit from PIGME Machine',1,'SYSTEM','Y',3,'SYSTEM',78,0,'',"
                        + "'Y',0,'',now(),'','','" + brCode + "','" + brCode + "',0,'','SYSTEM')").executeUpdate();
                if (queryResult <= 0) {
                    throw new Exception("Problem In DSS Transaction Insertion.");
                }
                String msg = fts.updateBalance(fts.getAccountNature(acno), acno, amount, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception("Problem In Balance Updation.");
                }
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigInteger getTxnId() throws ApplicationException {
        BigInteger txnId = null;
        try {
            List list = em.createNativeQuery("select ifnull(max(txn_id),0)+1 from online_pigme_request").getResultList();
            Vector ele = (Vector) list.get(0);
            txnId = new BigInteger(ele.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return txnId;
    }
}
