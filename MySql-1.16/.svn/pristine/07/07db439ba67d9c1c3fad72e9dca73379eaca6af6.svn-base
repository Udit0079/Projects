/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cdci;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.cdci.CustInfo;
import com.cbs.dto.cdci.CustRegReturn;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class FIWSFacade implements FIWSFacadeRemote {

    @PersistenceContext
    protected EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    TermDepositeCalculationManagementFacadeRemote termDepositeCalculationManagementFacadeRemote;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingMgmtFacadeRemote;
    @EJB
    private AccountOpeningFacadeRemote accOpeningFacade;
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//    public List<MiniStatementPojo> getMiniStatement(String acNo) throws ApplicationException {
//         List<MiniStatementPojo> miniStatement = new ArrayList<MiniStatementPojo>();
//        try {
//            double bal = 0.0;
//            int trantype = 0, ty = 0;
//            String particulars = "";
//            double cramt = 0, dramt = 0;
//            
//            SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//           
//            String accountNature = ftsPostingMgmtFacadeRemote.getAccountNature(acNo);
//
//            String reconTable = getTableName(accountNature);
//            List resultList = entityManager.createNativeQuery("select date_format(min(tmp.dt),'%Y%m%d') from (select acno,dt from " + reconTable
//                    + " where acno='" + acNo + "' order by dt desc limit 10) tmp").getResultList();
//            if (resultList.isEmpty()) {
//                throw new ApplicationException("Data does not exist.");
//            }
//            Vector vect = (Vector) resultList.get(0);
//            String minDt = vect.get(0).toString() == null ? ymmd.format(new Date()) : vect.get(0).toString();
//            
//            String preDt = CbsUtil.dateAdd(minDt, -1);
//            double balance = fnBalosForAccountAsOn(acNo, preDt);
//            List dataList = entityManager.createNativeQuery("select date_format(dt,'%d/%m/%Y') ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,"
//                    + "recno from " + reconTable + " where acno='" + acNo + "' and auth='y' order by trantime DESC limit 10").getResultList();
//
//            if (dataList.isEmpty()) {
//                throw new ApplicationException("Transaction does not exist.");
//            }
//            int l = 1;
//            for (int i = dataList.size() - 1; i >= 0; i--) {
//                String p1 = "", p2 = "";
//                Vector vec = (Vector) dataList.get(i);
//                ty = Integer.parseInt(vec.get(7).toString());
//                if (ty == 0) {
//                    p1 = "By ";
//                } else {
//                    p1 = "To ";
//                }
//                trantype = Integer.parseInt(vec.get(6).toString());
//                if (trantype == 0) {
//                    p2 = "CSH ";
//                } else if (trantype == 1) {
//                    p2 = "CLR ";
//                } else if (trantype == 8) {
//                    p2 = "INT ";
//                } else {
//                    p2 = "TRF ";
//                }
//                cramt = Double.parseDouble(vec.get(4).toString());
//                dramt = Double.parseDouble(vec.get(3).toString());
//                bal = balance + cramt - dramt;
//                if (l == 1) {
//                    MiniStatementPojo pojo = new MiniStatementPojo();
//                    pojo.setDate1(sdf.format(ymmd.parse(preDt)));
//                    pojo.setParticulars("As ");
//                    pojo.setChequeNo("999");
//                    pojo.setWithdrawl(0.0);
//                    pojo.setDeposit(0.0);
//                    pojo.setBalance(balance);
//                    miniStatement.add(pojo);
//                }
//                l = l + 1;
//                balance = bal;
//                particulars = p1 + p2 + (vec.get(1) == null ? "" : vec.get(1).toString());
//                MiniStatementPojo pojo = new MiniStatementPojo();
//                pojo.setDate1(vec.get(0) == null ? sdf.format(new Date()) : vec.get(0).toString());
//                pojo.setParticulars(particulars);
//                pojo.setChequeNo(vec.get(2) == null ? "" : vec.get(2).toString());
//                pojo.setWithdrawl(dramt);
//                pojo.setDeposit(cramt);
//                pojo.setBalance(bal);
//                miniStatement.add(pojo);
//            }
//            
//        } catch (Exception e) {
//            throw  new ApplicationException(e.getMessage());
//        }
//        return miniStatement;
//    }
//    private float fnBalosForAccountAsOn(String acno, String dt) throws ApplicationException {
//        float balance = 0;
//        List list2 = new ArrayList();
//        try {
//            List list1 = entityManager.createNativeQuery("Select AcctNature from accounttypemaster where AcctCode = SubString('" + acno + "',3,2)").getResultList();
//            Vector element1 = (Vector) list1.get(0);
//            String acctNature = element1.get(0).toString();
//            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM ca_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM loan_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM rdrecon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM gl_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM of_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM nparecon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND DT <='" + dt + "'	").getResultList();
//            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//                list2 = entityManager.createNativeQuery("SELECT IFNULL(SUM(IFNULL(CRAMT,0))-SUM(IFNULL(DRAMT,0)),0) FROM recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND DT <='" + dt + "'	").getResultList();
//            }
//            Vector element2 = (Vector) list2.get(0);
//            balance = Float.parseFloat(element2.get(0).toString());
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return balance;
//    }
//
//    private String getTableName(String acctNature) throws ApplicationException {
//        if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//            return "recon";
//        } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//            return "ca_recon";
//        } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.SS_AC) || acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
//            return "loan_recon";
//        } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//            return "td_recon";
//        } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
//            return "ddstransaction";
//        } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
//            return "rdrecon";
//        }
//        return null;
//    }
    public String fiPostingTxnProcess(String acno, String txnDt, String amt, int ty, String macId, String bcId, String remark) throws ApplicationException {
        String msg = "";
        int payBy = 0;
        try {
            String currentBrnCode = ftsPostingMgmtFacadeRemote.getCurrentBrnCode(acno);
            if (amt.equalsIgnoreCase("") || amt.equalsIgnoreCase("0")) {
                throw new ApplicationException("Amount can not be 0.");
            } else if (acno.equalsIgnoreCase("") || acno.length() < 12) {
                throw new ApplicationException("Please insert valid Account no.");
            } else if (ty != 0) {
                if (ty != 1) {
                    throw new ApplicationException("TY should be 0 or 1.");
                }
            } else if (bcId == null || bcId.equalsIgnoreCase("")) {
                throw new ApplicationException("Enter by field can not be blank.");
            } else if (macId == null || macId.equalsIgnoreCase("")) {
                throw new ApplicationException("Mac Id can not be blank.");
            }
            if (ty == 1) {
                payBy = 2;
            } else {
                payBy = 3;
            }
            UserTransaction ut = context.getUserTransaction();
            Float trsno = ftsPostingMgmtFacadeRemote.getTrsNo();
            ut.begin();
            entityManager.createNativeQuery("INSERT INTO fi_txn(ACNO,TXN_DT,TXN_AMT,CR_DR_STATUS,MACHINE_ID,BC_ID,TXN_ID,IN_TIME)"
                    + " VALUES ('" + acno + "','" + txnDt + "'," + amt + "," + ty + ",'" + macId + "','" + bcId + "'," + trsno + ",now())").executeUpdate();
            String ftsPosting43CBSMsg = ftsPostingMgmtFacadeRemote.ftsPosting43CBS(acno, 0, ty, Double.parseDouble(amt), txnDt, txnDt, bcId, currentBrnCode, currentBrnCode, 0, remark, trsno, ftsPostingMgmtFacadeRemote.getRecNo(), 0, "", "Y", "SYSTEM", "", payBy, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
            if (ftsPosting43CBSMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                String acnoG = currentBrnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + "00550401";
                int tyg = 0;
                if (ty == 0) {
                    tyg = 1;
                } else {
                    tyg = 0;
                }
                String ftsPosting43CBSMsgG = ftsPostingMgmtFacadeRemote.ftsPosting43CBS(acnoG, 0, tyg, Double.parseDouble(amt), txnDt, txnDt, bcId, currentBrnCode, currentBrnCode, 0, remark, trsno, ftsPostingMgmtFacadeRemote.getRecNo(), 0, "", "Y", "SYSTEM", "", payBy, "", "", "", 0f, "", "", "T", "", 0f, "N", "", "", "");
                if (!ftsPosting43CBSMsgG.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return ftsPosting43CBSMsgG;
                }
                msg = ftsPosting43CBSMsgG;
            } else {
                ut.rollback();
                return ftsPosting43CBSMsg;
            }
            entityManager.createNativeQuery("UPDATE fi_txn SET MSG ='" + msg + "', OUT_TIME = now() WHERE ACNO = '" + acno + "' AND TXN_DT = '" + txnDt + "' AND TXN_ID =" + trsno).executeUpdate();
            ut.commit();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return msg;
    }

    public Double balanceeEnquiry(String acNo) throws ApplicationException {
        Object balanceList = null;
        String acNat = "";
        try {
            List acNatList = entityManager.createNativeQuery("select acctnature from accounttypemaster where acctcode=(select accttype from accountmaster where acNo='" + acNo + "')").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            }
            if (acNat.equalsIgnoreCase("SB")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("CA")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM ca_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("TD")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("TL")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM loan_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("DL")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM loan_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("RD")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM rdrecon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("PO")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM gl_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("OF")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM of_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("NP")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM nparecon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("MS")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("FD")) {
                balanceList = entityManager.createNativeQuery("SELECT ROUND((SUM(IFNULL(CRAMT,0)) - SUM(IFNULL(DRAMT,0))),2) FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acNo + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND DT <=CONVERT(VARCHAR(8),getDate(),112)").getSingleResult();
            } else if (acNat.equalsIgnoreCase("DS")) {
                balanceList = entityManager.createNativeQuery("select balance from reconbalan where acno='" + acNo + "'").getSingleResult();
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

    public CustRegReturn customerRegistration(CustInfo custInfo) throws ApplicationException {
        CustRegReturn regResult = new CustRegReturn();
        List selectMaxCustId = entityManager.createNativeQuery("select IFNULL(max(cast(customerid as int)),'0')as customerid "
                + "from cbs_customer_master_detail").getResultList();
        Vector vecToSelectMaxCustid = (Vector) selectMaxCustId.get(0);

        String strForMaxCustid = vecToSelectMaxCustid.get(0).toString();
        int custId = Integer.parseInt(strForMaxCustid) + 1;
        UserTransaction ut = context.getUserTransaction();
        try {
            Query insertIntoCustMasterDtl = entityManager.createNativeQuery("insert into cbs_customer_master_detail "
                    + "(customerid,title,custname,gender,DateOfBirth,AddressLine1,staffflag,minorflag,nriflag,PrimaryBrCode,SuspensionFlg,"
                    + "Auth,RecordCreaterID,CreationTime,TsCnt) "
                    + "values(" + custId + ",'Mr.','" + custInfo.getCustName() + "','" + custInfo.getGender() + "','" + custInfo.getDateOfBirth() + "','"
                    + custInfo.getLocation() + "','N','N','N','" + custInfo.getPrimaryBrCode() + "','N','N','" + custInfo.getUserId() + "','" + ymmd.format(new Date()) + "','0')");

            int result = insertIntoCustMasterDtl.executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Problem in Customer Registration.");
            }
            String status = "";
            long dateDiff = CbsUtil.dayDiff(ymmd.parse(custInfo.getDateOfBirth()), new Date());
            long sts = dateDiff / 365;
            if (sts >= 18) {
                status = "MJ";
            } else if (sts < 18) {
                status = "MN";
            }
            String actype = CbsAcCodeConstant.FI_AC.getAcctCode();
            List secList = entityManager.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + actype + "'").getResultList();
            if (secList.isEmpty()) {
                throw new ApplicationException("Account Nature does not exist.");
            }

            String custNoNew = accOpeningFacade.cbsCustId(actype, custInfo.getPrimaryBrCode());

            for (int i = custNoNew.length(); i < 6; i++) {
                custNoNew = "0" + custNoNew;
            }
            String acno = custInfo.getPrimaryBrCode() + actype + custNoNew + "01";

            String query = "insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO) values ('" + acno + "','" + acno + "')";
            Query insertMapping = entityManager.createNativeQuery(query);
            int mapping = insertMapping.executeUpdate();
            if (mapping <= 0) {
                throw new ApplicationException("Problem In Mapping Table Insertion !");
            }

            query = "insert into customermaster(custno,actype,title,custname,craddress,praddress,dob,status,lastupdatedt,"
                    + "AgCode,EnteredBy,brncode) values ('" + custNoNew + "','" + actype + "','MR.','" + custInfo.getCustName() + "','"
                    + custInfo.getLocation() + "','" + custInfo.getLocation() + "','" + custInfo.getDateOfBirth() + "','"
                    + status + "','" + ymmd.format(new Date()) + "','01','" + custInfo.getUserId() + "','" + custInfo.getPrimaryBrCode() + "')";

            Query insertQuery = entityManager.createNativeQuery(query);
            mapping = insertQuery.executeUpdate();
            if (mapping <= 0) {
                throw new ApplicationException("Problem In CustomerMaster Table Insertion !");
            }

            query = "insert into accountmaster(acno,openingdt,LastOpDate,closingbal,opermode,accstatus,enteredby,lastupdatedt,"
                    + "accttype,Custname,CurBrCode,intdeposit) values ('" + acno + "','" + ymmd.format(new Date()) + "','"
                    + ymmd.format(new Date()) + "',0 , 1 , 1 ,'" + custInfo.getUserId() + "','" + ymmd.format(new Date()) + "','"
                    + actype + "','" + custInfo.getCustName() + "','" + custInfo.getPrimaryBrCode() + "',4.0)";
            Query insertQuery2 = entityManager.createNativeQuery(query);
            mapping = insertQuery2.executeUpdate();
            if (mapping <= 0) {
                throw new ApplicationException("Problem In AccountMaster Table Insertion !");
            }

            query = "insert into reconbalan(acno,balance,dt) values ('" + acno + "'," + 0 + ",'" + ymmd.format(new Date()) + "')";
            Query insertQuery4 = entityManager.createNativeQuery(query);
            mapping = insertQuery4.executeUpdate();
            if (mapping <= 0) {
                throw new ApplicationException("Problem In ReconBalan Table Insertion !");
            }


            //Addition to save in the table on the basis of Scheme Code.


            String date = ymmd.format(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String oneBackDay = ymmd.format(cal.getTime());

            String rateCode = "Fl";
            query = "select mindate,maxdate from yearend where yearendflag='N' and brncode='" + custInfo.getPrimaryBrCode() + "'";
            List yearEndList = entityManager.createNativeQuery(query).getResultList();
            Vector yearEnd = (Vector) yearEndList.get(0);
            String minDate = yearEnd.get(0).toString();
            String maxDate = yearEnd.get(1).toString();

            query = "select count(*) from cbs_loan_acctype_interest_parameter where ac_type='" + CbsAcCodeConstant.FI_AC.getAcctCode()
                    + "' and brncode='" + custInfo.getPrimaryBrCode() + "' and convert(varchar(8),from_dt,112) between '" + minDate
                    + "' and '" + maxDate + "'" + "and convert(varchar(8),to_dt,112) between '" + minDate + "' and '" + maxDate + "'";
            List countingList = entityManager.createNativeQuery(query).getResultList();
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
            query = "select a.interest_table_code, b.scheme_code from cbs_scheme_tod_reference_details a, "
                    + "cbs_scheme_general_scheme_parameter_master b where a.Scheme_code =b.scheme_code and b.scheme_type='10'";
            List schemeCodelist = entityManager.createNativeQuery(query).getResultList();
            Vector schemeCodeVect = (Vector) schemeCodelist.get(0);
            String schemeCode = schemeCodeVect.get(1).toString();
            String intCode = schemeCodeVect.get(0).toString();

            /**
             * *Addition to save the data into CBS_ACC_INT_RATE_DETAILS table.**
             */
            String effToDt = "31/12/2099";  //According to Mr. Alok
            int effNoOfDays = (int) CbsUtil.dayDiff(new Date(), sdf.parse(effToDt));

            Query insertQuery7 = entityManager.createNativeQuery("insert into cbs_acc_int_rate_details(ACNO,AC_INT_VER_NO,INT_TABLE_CODE,ACC_PREF_CR,MIN_INT_RATE_CR,MAX_INT_RATE_CR,AC_PREF_DR,MIN_INT_RATE_DR, MAX_INT_RATE_DR,INT_PEG_FLG,PEG_FREQ_MON,PEG_FREQ_DAYS,EFF_FRM_DT,EFF_TO_DT,EFF_NO_OF_DAYS,CREATED_BY,CREATION_DT,MOD_CNT,UPDATED_BY,UPDATED_DT)"
                    + " values('" + acno + "',1,'" + intCode + "',0.00,0.00,0.00,0.00,0.00,0.00,'N',0,0,'" + ymmd.format(new Date()) + "','20991231'," + effNoOfDays + ",'" + custInfo.getUserId() + "','" + ymmd.format(new Date()) + "',0,'','')");

            int insertNo = insertQuery7.executeUpdate();
            if (insertNo <= 0) {
                throw new ApplicationException("Insertion problem in CBS_ACC_INT_RATE_DETAILS table.");
            }

            /**
             * *Addition end here***
             */
            Query insertQuery8 = entityManager.createNativeQuery("INSERT INTO cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                    + " VALUES ('" + acno + "','" + schemeCode + "','" + intCode + "'," + 0 + ",0.00,0.00 ,'" + rateCode + "','S','','" + calMethod + "','END','C','L','" + calMethod + "','0',0,0,'" + oneBackDay + "','" + oneBackDay + "','" + date + "')");
            mapping = insertQuery8.executeUpdate();
            if (mapping <= 0) {
                throw new ApplicationException("Problem In CBS Loan Account Mast Table Insertion !");
            }

            List sNoList = entityManager.createNativeQuery("SELECT IFNULL(MAX(SNO)+1,1) FROM  cbs_loan_interest_post_ac_wise").getResultList();
            Vector sNoVect = (Vector) sNoList.get(0);
            int sNo = Integer.parseInt(sNoVect.get(0).toString());

            Query insertQuery9 = entityManager.createNativeQuery("INSERT INTO cbs_loan_interest_post_ac_wise(SNO,ACNO,POST_FLAG,POSTINGDT,FROMDT,TODT,BRNCODE,FLAG)"
                    + "VALUES(" + sNo + ",'" + acno + "','Y','" + date + "','" + oneBackDay + "','" + oneBackDay + "','" + custInfo.getPrimaryBrCode() + "','I')");
            mapping = insertQuery9.executeUpdate();
            if (mapping <= 0) {
                throw new ApplicationException("Problem In CBS Loan Post Table Insertion !");
            }
            regResult.setMsg("True");
            regResult.setAcNo(acno);
            regResult.setCustId(String.valueOf(custId));
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return regResult;
    }

    public CustRegReturn customerIdAndAccountOpen(String agentBrnId, String name, String fatherName, String gender, String address, String phone, String dob, String occupation, String panno, String userId) throws ApplicationException {
        CustRegReturn regResult = new CustRegReturn();
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            CustInfo custInfo = new CustInfo();
            custInfo.setCustName(name);
            if (gender.equalsIgnoreCase("Male")) {
                gender = "M";
            } else {
                gender = "F";
            }
            custInfo.setGender(gender);
            custInfo.setLocation(address);
            custInfo.setDateOfBirth(dob);
            custInfo.setPrimaryBrCode(agentBrnId);
            custInfo.setUserId(userId);

            regResult = customerRegistration(custInfo);
            if (regResult != null) {
                List alphaList = entityManager.createNativeQuery("select alphacode from branchmaster where brncode=cast('" + agentBrnId + "' as int)").getResultList();
                Vector element = (Vector) alphaList.get(0);
                String alphacode = element.get(0).toString();
                if (!alphaList.isEmpty()) {
                    Query query = entityManager.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn)"
                            + "values (" + "'" + regResult.getCustId() + "'" + "," + "'" + regResult.getAcNo() + "'" + "," + "'" + userId + "'" + "," + "'" + alphacode + "'" + ")");
                    int result = query.executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("There is no branch for this agent !");
                    }
                } else {
                    throw new ApplicationException("There is no branch for this agent !");
                }
            }
            ut.commit();
            System.out.println("Message is: " + regResult.getMsg() + "\n");
            System.out.println("Message is: " + regResult.getAcNo() + "\n");
            System.out.println("Message is: " + regResult.getCustId() + "\n");
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return regResult;
    }
}
