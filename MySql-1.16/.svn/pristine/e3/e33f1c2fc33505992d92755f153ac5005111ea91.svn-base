/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.TxnDetailBean;
import com.cbs.dto.other.BackDateEntryPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.TimerServiceFacadeRemote;
import com.cbs.pojo.SuspiciousVerifyPojo;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "TransactionManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TransactionManagementFacade implements TransactionManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingRemote;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnAuth;
    @EJB
    TimerServiceFacadeRemote facade;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private LoanInterestCalculationFacadeRemote loanFacade;
    @EJB
    private BankProcessManagementFacadeRemote bankProcessManagement;
    SimpleDateFormat ymd_Format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat yMMd = new SimpleDateFormat("dd/MM/yyyy");

    /*Start of Transaction Management*/
    public List getDataForF6(String query) throws ApplicationException {
        //public List getDataForF6(String orgBrnCode) throws ApplicationException { //old prameter    
        //List listForF6 = em.createNativeQuery("select AcName,acno from gltable where substring(acno,1,2)='" + orgBrnCode + "' order by AcName").getResultList();// old code
        // added by manish kumar
        List listForF6 = em.createNativeQuery(query).getResultList();

        return listForF6;
    }

    public List getDropdownDataOnLoadTranType(String groupCode) throws ApplicationException {
        List list = em.createNativeQuery("SELECT CODE,DESCRIPTION FROM codebook WHERE GROUPCODE='" + groupCode + "' and Code in('0','1','2') ORDER BY CODE").getResultList();
        return list;
    }

    public List getDropdownDataOnLoad(String groupCode) throws ApplicationException {
        List list = em.createNativeQuery("SELECT CODE,DESCRIPTION FROM codebook WHERE GROUPCODE='" + groupCode + "' ORDER BY CODE").getResultList();
        return list;
    }

    public List getBy(String groupCode) throws ApplicationException {
        List list = em.createNativeQuery("SELECT CODE,DESCRIPTION FROM codebook WHERE GROUPCODE='" + groupCode + "' AND CODE!=0 ORDER BY CODE").getResultList();
        return list;
    }

    public List selectFromBankDays(String orgBrnCode) throws ApplicationException {
        List list = em.createNativeQuery("SELECT CONVERT(date,datetime) FROM bankdays WHERE DayEndFlag='N' AND brncode = '" + orgBrnCode + "'").getResultList();
        return list;
    }

    public List selectFromLoanDisbursement(String accNo) throws ApplicationException {
        List list = em.createNativeQuery("select * from loandisbursement where acno = '" + accNo + "'").getResultList();
        return list;
    }

    public List selectFromGLTable(String accNo) throws ApplicationException {
        List list = em.createNativeQuery("Select acname,acno,postflag,MSGFLAG from gltable where acno='" + accNo + "'").getResultList();
        return list;
    }

    public List selectFromGLTablecrDRFlag(String accNo) throws ApplicationException {
        List list = em.createNativeQuery("Select CrDrFlag from gltable where acno='" + accNo + "'").getResultList();
        return list;
    }

    public List selectForDS(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select acno,custname,jtname1,jtname2,jtname3,jtname4,ifnull(instruction,''),opermode,odlimit,openingdt,"
                    + "accstatus,adhoclimit,ifnull(optstatus,1)as optstatus,ifnull(chequebook,0) as chequebook from accountmaster where AcNo='" + accNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Account Details does not exist.");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectForFDMS(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select acno,custname,jtname1,jtname2,jtname3,jtname4,ifnull(instruction,''),opermode,"
                    + "DATE_FORMAT(openingdt,'%Y%m%d') 'openingdt',accstatus from td_accountmaster where AcNo='" + accNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Account Details does not exist.");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectForOF(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acno,LIEN from td_vouchmst where ofacno = '" + accNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Account Details does not exist.");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromAcctMaster(String accNo) throws ApplicationException {
        try {
            String curDt = ymdFormat.format(new Date());
            List list = em.createNativeQuery("select am.acno,am.name,am.jtname1,am.jtname2,am.jtname3,am.jtname4,am.instr,am.opermode,am.odlimit,"
                    + "am.openingdt,am.status,ifnull(la.adhoclimit,0),am.optstatus,am.chequebook from "
                    + "(Select acno as acno,custname as name, ifnull(jtname1,'') as jtname1 ,ifnull(jtname2,'') as jtname2,ifnull(jtname3,'') "
                    + "as jtname3, ifnull(jtname4,'') as jtname4, ifnull(instruction,'') as instr, opermode as opermode ,odlimit as odlimit, "
                    + "openingdt as openingdt,accstatus as status ,ifnull(optstatus,1)as optstatus,ifnull(chequebook,0) as chequebook from "
                    + "accountmaster where AcNo='" + accNo + "') am left join "
                    + "(select acno as acno, ifnull(adhoclimit,0) as adhoclimit from loan_appparameter where acno='" + accNo + "' and '" + curDt
                    + "' between Adhocapplicabledt and adhocexpiry) la on am.acno = la.acno").getResultList();

            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getDocumentExpiryDate(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select CAST(ifnull(a.DOCUMENT_EXP_DATE,'19000101') AS DATE)  from cbs_loan_borrower_details a where a.ACC_NO = '" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public int acctStatus(String accNo) throws ApplicationException {
        try {
            String nature = ftsPostingRemote.getAccountNature(accNo);
            List list = new ArrayList();
            if (nature.equals(CbsConstant.FIXED_AC) || nature.equals(CbsConstant.MS_AC)) {
                list = em.createNativeQuery("select accstatus from td_accountmaster where AcNo='" + accNo + "'").getResultList();
            } else if (nature.equals(CbsConstant.OF_AC)) {
                list = em.createNativeQuery("select status from td_vouchmst where AcNo='" + accNo + "'").getResultList();
            } else {
                list = em.createNativeQuery("select accstatus from accountmaster where AcNo='" + accNo + "'").getResultList();
            }
            return Integer.parseInt(list.get(0).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List selectFromAccountStatus(String accNo) {
        List list = em.createNativeQuery("select ifnull(amount,0)as amount from accountstatus where acno = '" + accNo + "' and spno = (select max(spno) from accountstatus a where a.acno = '" + accNo + "' and auth = 'Y')").getResultList();
        return list;
    }

    public List selectFromLoanMisDetails(String accNo, String Tempbd) {
        List list = em.createNativeQuery("select DOCUMENT_EXP_DATE from cbs_loan_borrower_details WHERE ACC_NO='" + accNo + "' and DOCUMENT_EXP_DATE<'" + Tempbd + "'").getResultList();
        return list;
    }

    public List selectFromLoanAppParameter(String accNo) {
        List list = em.createNativeQuery("select ifnull(maxlimit,0)as maxlimit from loan_appparameter where acno = '" + accNo + "'").getResultList();
        return list;
    }

    public List selectFromReconTdReconCaRecon(String accNo, String TmpAcctNature) {
        List list;
        if (TmpAcctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || TmpAcctNature.equalsIgnoreCase(CbsConstant.MS_AC)
                || TmpAcctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
            list = em.createNativeQuery("SELECT ROUND(ifnull(BALANCE,0),2) FROM td_reconbalan WHERE ACNO ='" + accNo + "'").getResultList();
        } else if (TmpAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            list = em.createNativeQuery("SELECT ROUND(ifnull(BALANCE,0),2) FROM ca_reconbalan WHERE ACNO ='" + accNo + "'").getResultList();
        } else {
            list = em.createNativeQuery("SELECT ROUND(ifnull(BALANCE,0),2) FROM reconbalan WHERE ACNO ='" + accNo + "'").getResultList();
        }
        return list;
    }

    public List selectPendingBalance(String accNo) {
        List list = em.createNativeQuery("Select ifnull(sum(txninstamt),0) from clg_ow_shadowbal where acno='" + accNo + "'").getResultList();
        return list;
    }

    public List selectForStopBalance(String accNo, String TmpAcctNature) {
        List list = null;
        if (TmpAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
            list = em.createNativeQuery("select acno from chbook_sb where acno='" + accNo + "' and statusflag='S'").getResultList();
        } else if (TmpAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            list = em.createNativeQuery("select acno from chbook_ca where acno='" + accNo + "' and statusflag='S'").getResultList();
        }
        return list;
    }

    public List addToChqDate(String chqDate) {
        List list = null;
        try {
            String sixMonthChqDt = "20120331"; //According to Mayank sir, It will change after discussion.
            int compareChqDt = ymdFormat.parse(chqDate).compareTo(ymdFormat.parse(sixMonthChqDt));
            if (compareChqDt <= 0) {
                list = em.createNativeQuery("select DATE_FORMAT(DATE_ADD('" + chqDate + "', INTERVAL 6 MONTH),'%Y-%m-%d')").getResultList();
            } else {
                list = em.createNativeQuery("select DATE_FORMAT(DATE_ADD('" + chqDate + "', INTERVAL 3 MONTH),'%Y-%m-%d')").getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List msgModuleFlag(String msgModuleName) {
        List list = em.createNativeQuery("select code from parameterinfo_report where reportname='" + msgModuleName + "'").getResultList();
        return list;
    }

//    public List selectMsgFlag(String acno) {
//        List list = em.createNativeQuery("Select ifnull(MsgFlag,0) msgflag from gltable where acno = '" + acno + "'").getResultList();
//        return list;
//    }
    public List selectForTDOFDRAct(String acno) {
        List list = em.createNativeQuery("select ACNO,ReceiptNo from td_vouchmst where acno='" + acno + "' and status='C' and (ofacno is not null) AND (ofacno<>'') order by ofacno").getResultList();
        return list;
    }

    public List selectGrdName(String accNo, String accNat) {
        List list;
        if (accNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || accNat.equalsIgnoreCase(CbsConstant.OF_AC) || accNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
            list = em.createNativeQuery("select ifnull(grdname,'') from td_customermaster where actype= '" + accNo.substring(2, 4) + "' and custno='" + accNo.substring(4, 10) + "'  and agcode='" + accNo.substring(10) + "'").getResultList();
        } else {
            list = em.createNativeQuery("select ifnull(grdname,'') from customermaster where actype= '" + accNo.substring(2, 4) + "' and custno='" + accNo.substring(4, 10) + "'  and agcode='" + accNo.substring(10) + "'").getResultList();
        }
        return list;
    }

    public List selectForOperationMode(String opmode) {
        List list = em.createNativeQuery("select description from codebook where groupcode=4 and code='" + opmode + "' ").getResultList();
        return list;
    }

    public String selectProductCode(String socAcct) throws ApplicationException {
        try {
            String productCode = "";
            List list = em.createNativeQuery("select ifnull(productcode,'') from accounttypemaster where acctcode='" + socAcct + "'").getResultList();
            if (!list.isEmpty()) {
                Vector v1 = (Vector) list.get(0);
                productCode = v1.get(0).toString();
            }
            return productCode;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectFromToknCrReconCash(String accNo, String Tempbd) {
        List list = em.createNativeQuery("SELECT ifnull(SUM(CRAMT),0) FROM tokentable_credit WHERE DT='" + Tempbd + "' AND ACNO='" + accNo + "' UNION ALL SELECT ifnull(SUM(CRAMT),0) FROM recon_cash_d WHERE DT='" + Tempbd + "' AND ACNO='" + accNo + "'").getResultList();
        return list;
    }

    public List selectPanNo(String tableName, String accNo) throws ApplicationException {
        List list = new ArrayList();
        try {
            String curBrnCode = ftsPostingRemote.getCurrentBrnCode(accNo);
            list = em.createNativeQuery("SELECT ifnull(panno,'') FROM  " + tableName + " Where CUSTNO= '" + accNo.substring(4, 10) + "'  AND ACTYPE='" + accNo.substring(2, 4) + "' AND AGCODE='" + accNo.substring(10) + "' and brncode='" + curBrnCode + "' ").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public List selectFromBillLost(String chqno, String orgBrnCode) {
        String format = "%06d";
        chqno = String.format(format, chqno);
        List list = em.createNativeQuery("select instno from bill_lost where billtype='PO' AND INSTNO='" + chqno + "' and BranchCode=(select AlphaCode from branchmaster where BrnCode=cast('" + orgBrnCode + "' as unsigned))").getResultList();
        return list;
    }

    public List selectRDInstall(String accNo) {
        List list = em.createNativeQuery("Select ifnull(rdinstaL,0) from accountmaster where acno='" + accNo + "' and rdinstal is not null and rdinstal<>'' and rdinstal<>0").getResultList();
        return list;
    }

    public List checkForUnpaidRdInstallment(String accNo) {
        List list = em.createNativeQuery("select *from rd_installment where acno='" + accNo + "' and STATUS = 'UNPAID'").getResultList();
        return list;
    }

    public List checkForUnpaidRdInstallmentAmt(String accNo) {
        List list = em.createNativeQuery("select sum(INSTALLAMT) from rd_installment where acno='" + accNo + "' and STATUS = 'UNPAID'").getResultList();
        return list;
    }

    /**
     * **Dinesh modification***
     */
    public List getLastTransAmtForAccount(String accNo) {
        List list = em.createNativeQuery("select ifnull(cramt,0) from rdrecon where acno='" + accNo + "' and dt=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
        return list;
    }

    public void checkStockStatementExpiry(String accNo, String dt) throws ApplicationException {
        try {
            String productCode = selectProductCode(accNo.substring(2, 4));
            if (productCode.equalsIgnoreCase("CC") || productCode.equalsIgnoreCase("DP")) {
                List list = em.createNativeQuery("select date_format(ifnull(DATE_ADD(max(nextstmdt), INTERVAL STMGRACEPD DAY),CURDATE()),'%Y%m%d') "
                        + "from loan_stockstm WHERE ACNO='" + accNo + "'  and nextstmdt in (select max(nextstmdt) as nextstmdt "
                        + "from loan_stockstm WHERE ACNO='" + accNo + "') GROUP BY STMGRACEPD").getResultList();
                if (!list.isEmpty()) {
                    Vector v5 = (Vector) list.get(0);
                    String stkExpDt = v5.get(0).toString();
                    if (ymdFormat.parse(stkExpDt).getTime() < ymdFormat.parse(dt).getTime()) {
                        throw new ApplicationException("Stock Statement has been expired on " + yMMd.format(ymdFormat.parse(stkExpDt)) + ". Please contact to authorize person.");
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List selectFromTDRecon(String accNo, String tempBd) {
        List list = em.createNativeQuery("select round(sum(ifnull(cramt,0))-sum(ifnull(Dramt,0)),2) FROM td_recon WHERE ACNO='" + accNo + "' AND TRANTYPE<>27 AND CLOSEFLAG IS NULL AND DT<='" + tempBd + "'").getResultList();
        return list;
    }

    public List selectFromLoanDisb(String accNo) {
        List list = em.createNativeQuery("SELECT ifnull(SUM(AMTDISBURSED),0) FROM loandisbursement WHERE ACNO='" + accNo + "'").getResultList();
        return list;
    }

    public List selectForGlobalVariables(String orgBrnCode) {
        List list = em.createNativeQuery("select brncode,ifnull(balexceed,'M'),ifnull(cashmod,'Y'),ifnull(bankname,''),ifnull(address,''),ifnull(sauthvalue,0) from parameterinfo where brncode=cast('" + orgBrnCode + "' as UNSIGNED)").getResultList();
        return list;
    }

    public void checkCashClose(String orgBrnCode, String tempBd) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select cashclose from bankdays where date='" + tempBd + "' and dayendflag='N' and cashclose='N' and Brncode='" + orgBrnCode + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("Sorry, Cash Transaction is not allowed after cash closing");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String selectFromTDParameterInfo() {
        List list = em.createNativeQuery("SELECT ifnull(OFDRFLAG,'N') FROM td_parameterinfo").getResultList();
        Vector v = (Vector) list.get(0);
        return v.get(0).toString();
    }

    public List selectBulkReportsFromParameterInfoReport() {
        List list = em.createNativeQuery("Select ifnull(code,0),reportname from parameterinfo_report where reportname in "
                + "('CCODEXPIRY','STAXMODULE_ACTIVE','PAN AMOUNT','VALUE DATE ACTIVE','MSGMODULE_ACTIVE','PO-MULTIPLE-ENTRY','AADHAR-ALERT')").getResultList();
        return list;
    }

    public List selectForCtrlIKey(String orgBrnCode) {
        List list = em.createNativeQuery("select FYEAR,SEQNO,INSTNO,AMOUNT,ORIGINDT,INFAVOUROF,ENTERBY from bill_po  where status='ISSUED'and  substring(acno,1,2)='" + orgBrnCode + "'order by dt,seqno").getResultList();
        return list;
    }

    public List<TxnDetailBean> accViewAuth(String acNo, String curDate, String brCode) throws ApplicationException {
        curDate = curDate.substring(6) + curDate.substring(3, 5) + curDate.substring(0, 2);
        try {
            String openingBalDt = null;
            double openingBal = 0;
            List<TxnDetailBean> txnDetailList = new ArrayList<TxnDetailBean>();
            List checkList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = substring('" + acNo + "',3,2)").getResultList();
            if (checkList.isEmpty()) {
                throw new ApplicationException("NO Account Nature For This Account !!!.");
            }
            Vector AcctNatureLst = (Vector) checkList.get(0);
            String acctNat = AcctNatureLst.get(0).toString();

            Calendar cal = Calendar.getInstance();
            String minDate = "";
            if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List checkList1 = em.createNativeQuery("SELECT MINDATE  FROM yearend WHERE MINDATE<='" + curDate + "' AND MAXDATE>='" + curDate + "' AND brncode = '" + brCode + "'").getResultList();
                if (checkList1.size() > 0) {
                    Vector minDateLst = (Vector) checkList1.get(0);
                    minDate = minDateLst.get(0).toString();
                }
                cal.setTime(ymdFormat.parse(curDate));
                int curMonth = cal.get(Calendar.MONTH);
                if ((Long.parseLong(acNo.substring(4, 10)) >= Long.parseLong(SiplConstant.GL_PL_ST.getValue()))
                        && (Long.parseLong(acNo.substring(4, 10)) <= Long.parseLong(SiplConstant.GL_PL_END.getValue()))) {
                    if (curMonth != 3) {
                        cal.setTime(ymdFormat.parse(CbsUtil.monthAdd(curDate, -1)));
                    } else {
                        cal.setTime(ymdFormat.parse(minDate));
                    }
                } else {
                    cal.setTime(ymdFormat.parse(CbsUtil.monthAdd(curDate, -1)));
                }
            } else if ((acctNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) || (acctNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                cal.setTime(ymdFormat.parse(CbsUtil.monthAdd(curDate, -6)));
            } else {
                cal.setTime(ymdFormat.parse(CbsUtil.monthAdd(curDate, -1)));
            }
            int code = 0;
            List rDescList = em.createNativeQuery("select code from parameterinfo_report where reportname='ROI-IN-DESC'").getResultList();
            if (!rDescList.isEmpty()) {
                Vector autoVector = (Vector) rDescList.get(0);
                code = Integer.parseInt(autoVector.get(0).toString());
            }
            //cal.set(Calendar.DATE, 1);
            openingBalDt = CbsUtil.dateAdd(ymdFormat.format(cal.getTime()), 1);
            String tableName = commonReport.getTableName(acctNat);
            if (acctNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (facade.isNewGl()) {
                    if ((Long.parseLong(acNo.substring(4, 10)) >= Long.parseLong(SiplConstant.GL_PL_ST.getValue()))
                            && (Long.parseLong(acNo.substring(4, 10)) <= Long.parseLong(SiplConstant.GL_PL_END.getValue()))) {
                        List checkList2 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0) - ifnull(SUM(dramt),0) FROM  gl_recon  WHERE Acno='" + acNo
                                + "' AND dt<'" + openingBalDt + "' AND DT>='" + minDate + "' and auth='Y'").getResultList();
                        Vector vect = (Vector) checkList2.get(0);
                        openingBal = Double.parseDouble(vect.get(0).toString());
                    } else {
                        List checkList2 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0) - ifnull(SUM(dramt),0) FROM  gl_recon  WHERE Acno='" + acNo
                                + "' AND dt<'" + openingBalDt + "' and auth='Y'").getResultList();
                        Vector vect = (Vector) checkList2.get(0);
                        openingBal = Double.parseDouble(vect.get(0).toString());
                    }
                } else {
                    if ((Long.parseLong(acNo.substring(4, 10)) >= Long.parseLong(SiplConstant.GL_PL_ST.getValue()))
                            && (Long.parseLong(acNo.substring(4, 10)) <= Long.parseLong(SiplConstant.GL_PL_END.getValue()))) {
                        List checkList2 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0) - ifnull(SUM(dramt),0) FROM  gl_recon  WHERE Acno='" + acNo
                                + "' AND dt<'" + openingBalDt + "' AND DT>='" + minDate + "' and auth='Y'").getResultList();
                        Vector vect = (Vector) checkList2.get(0);
                        openingBal = Double.parseDouble(vect.get(0).toString());
                    } else {
                        List checkList2 = em.createNativeQuery("SELECT ifnull(SUM(cramt),0) - ifnull(SUM(dramt),0) FROM  gl_recon  WHERE Acno='" + acNo
                                + "' AND dt<'" + openingBalDt + "' and auth='Y'").getResultList();
                        Vector vect = (Vector) checkList2.get(0);
                        openingBal = Double.parseDouble(vect.get(0).toString());
                    }
                }
            } else if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List checkList2 = em.createNativeQuery("select ifnull(sum(cramt),0) - ifnull(sum(dramt),0) from  td_recon  where Acno='" + acNo + "' AND dt<'" + openingBalDt
                        + "' and auth='Y' and closeflag is null and trantype <> 27").getResultList();
                Vector CASTLst = (Vector) checkList2.get(0);
                openingBal = Double.parseDouble(CASTLst.get(0).toString());
            } else {
                List checkList2 = em.createNativeQuery("select ifnull(sum(cramt),0) - ifnull(sum(dramt),0) from " + tableName + " where acno='" + acNo + "' AND dt<'" + openingBalDt + "' and auth='Y' AND TRANTYPE <>27").getResultList();
                Vector CASTLst = (Vector) checkList2.get(0);
                openingBal = Double.parseDouble(CASTLst.get(0).toString());
            }

            List checkList3;
            if (acctNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                checkList3 = em.createNativeQuery("SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,coalesce(authby,''),"
                        + "ty,coalesce(Details,'') from td_recon where Acno='" + acNo + "' and dt>='" + openingBalDt + "' and auth='Y' and trantype<>27 "
                        + "and closeflag is null order by Dt").getResultList();
            } else {
                checkList3 = em.createNativeQuery("SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,authby,ty,"
                        + "coalesce(Details,'') from " + tableName + " where Acno='" + acNo + "' and dt>='" + openingBalDt + "' and auth='Y' order by Dt").getResultList();
            }
            TxnDetailBean txnDetailObj;
            for (int i = 0; i < checkList3.size(); i++) {
                Vector vect = (Vector) checkList3.get(i);

                txnDetailObj = new TxnDetailBean();
                txnDetailObj.setAcNo(acNo);

                txnDetailObj.setDt(vect.get(0).toString());
                txnDetailObj.setTranType(vect.get(1).toString());
                txnDetailObj.setInstNo(vect.get(2).toString());

                txnDetailObj.setDrAmt(Double.parseDouble(vect.get(3).toString()));
                txnDetailObj.setCrAmt(Double.parseDouble(vect.get(4).toString()));

                txnDetailObj.setEnterBy(vect.get(5).toString());
                txnDetailObj.setAuthBy(vect.get(6).toString());

                int tranType = Integer.parseInt(vect.get(1).toString());
                int ty = Integer.parseInt(vect.get(7).toString());
                String details = vect.get(8).toString().toUpperCase();
                if (code == 0 && ((details.contains("@")) && (details.contains("%")))) {
                    details = details.substring(0, details.indexOf("@"));
                }
                String particular = "";
                if (acctNat.equals(CbsConstant.PAY_ORDER)) {
                    String poDetails = "";
                    String array[];
                    if (!details.equalsIgnoreCase("")) {
                        if (details.contains("#")) {
                            if (details.contains("#F`") && details.contains("#G`")) {
                                array = vect.get(8).toString().split("#");
                                poDetails = array[0] + " " + details.substring(details.indexOf("#F`") + 3, details.indexOf("#G`"));
                            } else {
                                poDetails = details;
                            }

                        } else if (vect.get(8).toString().contains("~`")) {
                            array = vect.get(8).toString().split("~`");
                            poDetails = array[0] + " " + array[16];
                        } else {
                            poDetails = vect.get(8).toString();
                        }
                    } else {
                        poDetails = vect.get(8).toString();
                    }
                    details = poDetails;
                }
                if (ty == 0) {
                    openingBal = openingBal + txnDetailObj.getCrAmt();
                    if (tranType == 0) {
                        particular = "BY CASH " + details;
                    } else if (tranType == 1) {
                        particular = "BY CLEARING " + details;
                    } else if (tranType == 2) {
                        particular = "BY TRANSFER " + details;
                    } else if (tranType == 5) {
                        particular = "BY EXTENSION " + details;
                    } else if (tranType == 8) {
                        particular = "BY TRANSFER " + details;
                    }
                } else {
                    openingBal = openingBal - txnDetailObj.getDrAmt();
                    if (tranType == 0) {
                        particular = "TO CASH " + details;
                    } else if (tranType == 1) {
                        particular = "TO CLEARING " + details;
                    } else if (tranType == 2) {
                        particular = "TO TRANSFER " + details;
                    } else if (tranType == 5) {
                        particular = "TO EXTENSION " + details;
                    } else if (tranType == 8) {
                        particular = "TO TRANSFER " + details;
                    }
                }
                txnDetailObj.setAmount(openingBal);
                txnDetailObj.setDetails(particular);
                txnDetailList.add(txnDetailObj);
            }
            return txnDetailList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getOpeningBalF4(String accNo, String currentDt) throws ApplicationException {
        try {
            currentDt = currentDt.substring(6) + currentDt.substring(3, 5) + currentDt.substring(0, 2);
            return String.valueOf(commonReport.getBalanceOnDate(accNo, currentDt));
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List selectForCtrlS(String accNo, String orgBrnCode) {
        List list = null;
        List list1 = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accNo.substring(2, 4) + "'").getResultList();
        Vector v = (Vector) list1.get(0);
        if (v.get(0).toString().equalsIgnoreCase(CbsConstant.SAVING_AC)) {
            list = em.createNativeQuery("select acno,Chqno,IssueDt from chbook_sb where acno='" + accNo + "' and statusflag='S'").getResultList();
            return list;
        } else if (v.get(0).toString().equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            list = em.createNativeQuery("select acno,Chqno,IssueDt from chbook_ca where acno='" + accNo + "' and statusflag='S'").getResultList();
            return list;
        }
        return list;
    }

    public List selectBillType(String accNo) throws ApplicationException {

        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select instcode,InstNature from billtypemaster where GLHEAD='" + accNo.substring(2, 10)
                    + "' order by instcode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }

    public List selectAlphaCode(String brCode) {
        List list = em.createNativeQuery("select distinct alphacode from branchmaster where brncode = " + Integer.parseInt(brCode)).getResultList();
        return list;
    }

    public List checkCommFlag(String billType) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select commflag,glhead,glheadint from billtypemaster where instcode='" + billType + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("No Information for GL Heads Exits in the database.");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List strBillNature(String billType) {
        List list = em.createNativeQuery("select ifnull(InstNature,'') from billtypemaster where instcode = '" + billType + "'").getResultList();
        return list;
    }

    public List getParentACode(String billType, String orgBrnCode) {
        List list = em.createNativeQuery("select alphacode,branchname,address,PINCODE from branchmaster where BrnCode=cast('"
                + orgBrnCode + "' as unsigned)").getResultList();
        return list;
    }

    private String getInstNo(String seqNo, String validateDate, String orgBrnCode, String billTable) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (billTable.equalsIgnoreCase("bill_po")) {
                list = em.createNativeQuery("select instno from bill_po where seqno =" + Float.parseFloat(seqNo)
                        + " and status='Issued' and VALIDATIONDT = '" + validateDate + "' and substring(acno,1,2)='"
                        + orgBrnCode + "'").getResultList();
            } else {
                list = em.createNativeQuery("select instno from bill_dd where seqno =" + Float.parseFloat(seqNo)
                        + " and status='Issued' and ORIGINDT = '" + validateDate + "' and substring(acno,1,2)='"
                        + orgBrnCode + "'").getResultList();
            }

            if (list.isEmpty()) {
                throw new ApplicationException("Instrument does not exist.");
            }
            Vector vBillPO = (Vector) list.get(0);
            return vBillPO.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getPoDetails(String billTable, String seqNo, String validateDate, String orgBrnCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            String instNo = getInstNo(seqNo, validateDate, orgBrnCode, billTable);
            if (ftsPostingRemote.isInstrumentLost(Float.parseFloat(seqNo), instNo, validateDate, orgBrnCode, "PO")) {
                throw new ApplicationException("This instrument mark as lost.");
            }
            if (billTable.equalsIgnoreCase("bill_po")) {
                list = em.createNativeQuery("select custname,acno,amount,infavourof,payableat,dt,instno from " + billTable + " where seqno ="
                        + Float.parseFloat(seqNo) + " and status<>'Cancelled' and VALIDATIONDT = '" + validateDate + "' and substring(acno,1,2)='"
                        + orgBrnCode + "'").getResultList();
            } else {
                list = em.createNativeQuery("select custname,acno,amount,infavourof,payableat,dt,instno from " + billTable + " where seqno ="
                        + Float.parseFloat(seqNo) + " and status<>'Cancelled' and origindt = '" + validateDate + "' and substring(acno,1,2)='"
                        + orgBrnCode + "'").getResultList();
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getDataFromBillPO(String billTable, String seqNo, String validateDate, String orgBrnCode) throws ApplicationException {
        try {
            String instNo = getInstNo(seqNo, validateDate, orgBrnCode, billTable);
            if (ftsPostingRemote.isInstrumentLost(Float.parseFloat(seqNo), instNo, validateDate, orgBrnCode, "PO")) {
                throw new ApplicationException("This instrument mark as lost.");
            }
            List list = em.createNativeQuery("select custname,acno,amount,infavourof,payableat,dt,instno from " + billTable + " where seqno ="
                    + Float.parseFloat(seqNo) + " and status='Issued' and VALIDATIONDT = '" + validateDate + "' and substring(acno,1,2)='"
                    + orgBrnCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public Double getCommission(String Tempbd, String bill, int payby, double amt) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select commcharge,ifnull(surcharge,0),ifnull(mincharge,0),commflag from bill_commission where "
                    + "slabdate = (select max(slabdate) from bill_commission where slabdate <= '" + Tempbd + "' and collecttype='" + bill
                    + "' and amountfrom <= " + amt + " and amountto >= " + amt + " and payby=" + payby + ") and collecttype='" + bill
                    + "' and payby=" + payby + " and amountfrom <= " + amt + " and amountto >= " + amt).getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Slab does not defined");
            }
            Vector v = (Vector) list.get(0);
//            double commAmt = Math.round(Double.parseDouble(v.get(0).toString()));
            double commAmt = Double.parseDouble(v.get(0).toString());
            if (v.get(3).toString().equals("Y")) {
                double bg = commAmt * amt / 1000;
                commAmt = Math.round(bg);
                if (commAmt < Double.parseDouble(v.get(2).toString())) {
                    commAmt = Math.round(Double.parseDouble(v.get(2).toString()));
                }
                double scharge = Double.parseDouble(v.get(1).toString());
                commAmt = commAmt + scharge;
            }
            return commAmt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getDetailsPOCustName(String accNo, String orgBrnCode) throws ApplicationException {
        try {
            String nature = ftsPostingRemote.getAccountNature(accNo);
            String query = "";
            if (nature.equals(CbsConstant.PAY_ORDER)) {
                query = "select acno, 1, ifnull(br.State,'') as stateCode, ifnull(br.State,'') as brState "
                        + "from gltable am, branchmaster br where am.acno = '" + accNo + "'and br.brncode=cast('" + orgBrnCode + "' as unsigned)";
            } else if (nature.equals(CbsConstant.FIXED_AC) || nature.equals(CbsConstant.MS_AC)) {
                query = "select CustFullName, am.accstatus, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                        + " from td_accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where am.acno = '" + accNo + "' and am.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + " and br.brncode=cast('" + orgBrnCode + "' as unsigned)";
            } else {
                query = "select CustFullName, am.accstatus, ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                        + " from accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where am.acno = '" + accNo + "' and am.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + " and br.brncode=cast('" + orgBrnCode + "' as unsigned)";
            }

            List accountList = em.createNativeQuery(query).getResultList();
            if (accountList.isEmpty()) {
                throw new ApplicationException("Account does not exist");
            }
            return accountList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String saveTransferDetails(String orgBrnCode, List<TxnDetailBean> cashPOTxnList, String tempBd, String authBy) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {

            double sumCrAmt = 0d;
            double sumDrAmt = 0d;
            ut.begin();
            float tranNo = ftsPostingRemote.getTrsNo();
            for (int j = 0; j < cashPOTxnList.size(); j++) {
                sumCrAmt = sumCrAmt + cashPOTxnList.get(j).getCrAmt();
                sumDrAmt = sumDrAmt + cashPOTxnList.get(j).getDrAmt();
            }
            int postFlag = 0;
            for (TxnDetailBean obj : cashPOTxnList) {
                String instDt = "";
                if (obj.getInstDt() != null) {
                    instDt = obj.getInstDt();
                }
                postFlag = getPostFlag(obj.getAcNo());
                if (postFlag == 11 && contra(postFlag, obj.getTy()) == true) {
                    List list1 = em.createNativeQuery("Select * from bill_sundry_dt "
                            + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='PAID'").getResultList();
                    if (list1.isEmpty()) {
                        List list2 = em.createNativeQuery("select amount from bill_sundry "
                                + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'").getResultList();
                        Vector vector2 = (Vector) list2.get(0);
                        if (Double.parseDouble(vector2.get(0).toString()) == sumDrAmt) {
                            for (int loop1 = 0; loop1 < cashPOTxnList.size(); loop1++) {
                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop1).getTy());
                                if (iyValue == 0) {
                                    Query insert1 = em.createNativeQuery("insert into bill_sundry_dt values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop1).getAcNo() + "','" + cashPOTxnList.get(loop1).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop1).getCrAmt() + ",'" + cashPOTxnList.get(loop1).getDt() + "','" + cashPOTxnList.get(loop1).getDt() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop1).getTranType() + "," + cashPOTxnList.get(loop1).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop1).getEnterBy() + "',null,'Y','" + cashPOTxnList.get(loop1).getEnterBy() + "',CURRENT_TIMESTAMP," + cashPOTxnList.get(loop1).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Sundry Updation");
                                    }
                                }
                            }
                            Query insert2 = em.createNativeQuery("update bill_sundry set status='PAID',recno=" + obj.getRecNo() + " ,dt = CURDATE() where seqno="
                                    + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'");
                            int result2 = insert2.executeUpdate();
                            if (result2 <= 0) {
                                throw new ApplicationException("Problem Occured In Sundry Updation");
                            }
                        } else {
                            for (int loop2 = 0; loop2 < cashPOTxnList.size(); loop2++) {

                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop2).getTy());
                                if (iyValue == 0) {
                                    List selectOriginDate = em.createNativeQuery("select DATE_FORMAT(origindt,'%Y%m%d') "
                                            + "from bill_sundry where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                            + "and status='ISSUED'").getResultList();
                                    Vector vectorOriginDate = (Vector) selectOriginDate.get(0);
                                    Query insert1 = em.createNativeQuery("insert into bill_sundry_dt values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop2).getAcNo() + "','" + cashPOTxnList.get(loop2).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop2).getCrAmt() + ",'" + cashPOTxnList.get(loop2).getDt() + "','" + vectorOriginDate.get(0).toString() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop2).getTranType() + "," + cashPOTxnList.get(loop2).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop2).getEnterBy() + "',null,'Y',null,CURRENT_TIMESTAMP," + cashPOTxnList.get(loop2).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Sundry Updation");
                                    }
                                }
                            }

                        }
                    } else {
                        List list2 = em.createNativeQuery("select amount from bill_sundry "
                                + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'").getResultList();
                        Vector vector2 = (Vector) list2.get(0);
                        List list3 = em.createNativeQuery("select sum(amount)+" + sumDrAmt + " from bill_sundry_dt "
                                + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                + "and status='PAID'").getResultList();
                        Vector vector3 = (Vector) list3.get(0);
                        if (Double.parseDouble(vector2.get(0).toString()) == Double.parseDouble(vector3.get(0).toString())) {
                            for (int loop3 = 0; loop3 < cashPOTxnList.size(); loop3++) {

                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop3).getTy());
                                if (iyValue == 0) {
                                    List selectOriginDate = em.createNativeQuery("select DATE_FORMAT(origindt,'%Y%m%d') "
                                            + "from bill_sundry where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                            + "and status='ISSUED'").getResultList();
                                    Vector vectorOriginDate = (Vector) selectOriginDate.get(0);
                                    Query insert1 = em.createNativeQuery("insert into bill_sundry_dt values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop3).getAcNo() + "','" + cashPOTxnList.get(loop3).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop3).getCrAmt() + ",'" + cashPOTxnList.get(loop3).getDt() + "','" + vectorOriginDate.get(0).toString() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop3).getTranType() + "," + cashPOTxnList.get(loop3).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop3).getEnterBy() + "',null,'Y',null,CURRENT_TIMESTAMP," + cashPOTxnList.get(loop3).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Sundry Updation");
                                    }
                                }
                            }
                            Query insert2 = em.createNativeQuery("update bill_sundry set status='PAID',recno=" + obj.getRecNo() + ",dt = CURDATE()"
                                    + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'");
                            int result2 = insert2.executeUpdate();
                            if (result2 <= 0) {
                                throw new ApplicationException("Problem Occured In Sundry Updation");
                            }
                        } else if (Double.parseDouble(vector2.get(0).toString()) < Double.parseDouble(vector3.get(0).toString())) {
                            throw new ApplicationException("Amount Debit From Sundry Head For This Sequence is Greater Than Credit");
                        } else {
                            for (int loop4 = 0; loop4 < cashPOTxnList.size(); loop4++) {
                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop4).getTy());
                                if (iyValue == 0) {
                                    List selectOriginDate = em.createNativeQuery("select DATE_FORMAT(origindt,'%Y%m%d') "
                                            + "from bill_sundry where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                            + "and status='ISSUED'").getResultList();
                                    Vector vectorOriginDate = (Vector) selectOriginDate.get(0);
                                    Query insert1 = em.createNativeQuery("insert into bill_sundry_dt values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop4).getAcNo() + "','" + cashPOTxnList.get(loop4).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop4).getCrAmt() + ",'" + cashPOTxnList.get(loop4).getDt() + "','" + vectorOriginDate.get(0).toString() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop4).getTranType() + "," + cashPOTxnList.get(loop4).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop4).getEnterBy() + "',null,'N',null,CURRENT_TIMESTAMP," + cashPOTxnList.get(loop4).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Sundry Updation");
                                    }
                                }
                            }
                        }
                    }
                } else if (postFlag == 12 && contra(postFlag, obj.getTy()) == true) {

                    List list1 = em.createNativeQuery("Select * from bill_suspense_dt "
                            + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='PAID'").getResultList();
                    if (list1.isEmpty()) {
                        List list2 = em.createNativeQuery("select amount from bill_suspense "
                                + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'").getResultList();
                        Vector vector2 = (Vector) list2.get(0);
                        if (Double.parseDouble(vector2.get(0).toString()) == sumCrAmt) {
                            for (int loop1 = 0; loop1 < cashPOTxnList.size(); loop1++) {
                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop1).getTy());
                                if (iyValue == 1) {
                                    Query insert1 = em.createNativeQuery("insert into bill_suspense_dt (fyear,seqno,instno,acno,details,amount,dt,origindt,"
                                            + "status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,recno,LASTUPDATEBY) values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop1).getAcNo() + "','" + cashPOTxnList.get(loop1).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop1).getDrAmt() + ",'" + cashPOTxnList.get(loop1).getDt() + "','" + cashPOTxnList.get(loop1).getDt() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop1).getTranType() + "," + cashPOTxnList.get(loop1).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop1).getEnterBy() + "',null,'Y','" + cashPOTxnList.get(loop1).getEnterBy() + "',CURRENT_TIMESTAMP," + cashPOTxnList.get(loop1).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Suspense Updation");
                                    }
                                }
                            }
                            Query insert2 = em.createNativeQuery("update bill_suspense set status='PAID',recno=" + obj.getRecNo() + ",dt= curdate()"
                                    + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'");
                            int result2 = insert2.executeUpdate();
                            if (result2 <= 0) {
                                throw new ApplicationException("Problem Occured In Suspense Updation");
                            }
                        } else {
                            for (int loop2 = 0; loop2 < cashPOTxnList.size(); loop2++) {

                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop2).getTy());
                                if (iyValue == 1) {
                                    List selectOriginDate = em.createNativeQuery("select DATE_FORMAT(origindt,'%Y%m%d') "
                                            + "from bill_suspense where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                            + "and status='ISSUED'").getResultList();
                                    Vector vectorOriginDate = (Vector) selectOriginDate.get(0);
                                    Query insert1 = em.createNativeQuery("insert into bill_suspense_dt (fyear,seqno,instno,acno,details,amount,dt,origindt,"
                                            + "status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,recno,LASTUPDATEBY) values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop2).getAcNo() + "','" + cashPOTxnList.get(loop2).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop2).getDrAmt() + ",'" + cashPOTxnList.get(loop2).getDt() + "','" + vectorOriginDate.get(0).toString() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop2).getTranType() + "," + cashPOTxnList.get(loop2).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop2).getEnterBy() + "',null,'N',null,CURRENT_TIMESTAMP," + cashPOTxnList.get(loop2).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Suspense Updation");
                                    }
                                }
                            }
                        }
                    } else {
                        List list2 = em.createNativeQuery("select amount from bill_suspense "
                                + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'").getResultList();
                        Vector vector2 = (Vector) list2.get(0);
                        List list3 = em.createNativeQuery("select sum(amount)+" + sumCrAmt + " from bill_suspense_dt "
                                + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                + "and status='PAID'").getResultList();
                        Vector vector3 = (Vector) list3.get(0);
                        if (Double.parseDouble(vector2.get(0).toString()) == Double.parseDouble(vector3.get(0).toString())) {
                            for (int loop3 = 0; loop3 < cashPOTxnList.size(); loop3++) {

                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop3).getTy());
                                if (iyValue == 1) {
                                    List selectOriginDate = em.createNativeQuery("select DATE_FORMAT(origindt,'%Y%m%d') "
                                            + "from bill_suspense where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                            + "and status='ISSUED'").getResultList();
                                    Vector vectorOriginDate = (Vector) selectOriginDate.get(0);
                                    Query insert1 = em.createNativeQuery("insert into bill_suspense_dt (fyear,seqno,instno,acno,details,amount,dt,origindt,"
                                            + "status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,recno,LASTUPDATEBY) values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop3).getAcNo() + "','" + cashPOTxnList.get(loop3).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop3).getDrAmt() + ",'" + cashPOTxnList.get(loop3).getDt() + "','" + vectorOriginDate.get(0).toString() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop3).getTranType() + "," + cashPOTxnList.get(loop3).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop3).getEnterBy() + "',null,'N',null,CURRENT_TIMESTAMP," + cashPOTxnList.get(loop3).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Suspense Updation");
                                    }
                                }
                            }
                            Query insert2 = em.createNativeQuery("update bill_suspense set status='PAID',recno=" + obj.getRecNo() + ", dt= curdate()"
                                    + "where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " and status='ISSUED'");
                            int result2 = insert2.executeUpdate();
                            if (result2 <= 0) {
                                throw new ApplicationException("Problem Occured In Suspense Updation");
                            }
                        } else {
                            for (int loop4 = 0; loop4 < cashPOTxnList.size(); loop4++) {

                                int iyValue = Integer.parseInt(cashPOTxnList.get(loop4).getTy());
                                if (iyValue == 1) {
                                    List selectOriginDate = em.createNativeQuery("select DATE_FORMAT(origindt,'%Y%m%d') "
                                            + "from bill_suspense where seqno=" + obj.getSqNo() + " and fyear=" + obj.getFinYear() + " "
                                            + "and status='ISSUED'").getResultList();
                                    Vector vectorOriginDate = (Vector) selectOriginDate.get(0);
                                    Query insert1 = em.createNativeQuery("insert into bill_suspense_dt (fyear,seqno,instno,acno,details,amount,dt,origindt,"
                                            + "status,comm,trantype,ty,enterby,updateby,auth,authby,trantime,recno,LASTUPDATEBY) values(" + obj.getFinYear() + "," + obj.getSqNo() + ","
                                            + "'','" + cashPOTxnList.get(loop4).getAcNo() + "','" + cashPOTxnList.get(loop4).getDetails() + "',"
                                            + "" + cashPOTxnList.get(loop4).getDrAmt() + ",'" + cashPOTxnList.get(loop4).getDt() + "','" + vectorOriginDate.get(0).toString() + "','PAID',null"
                                            + "," + cashPOTxnList.get(loop4).getTranType() + "," + cashPOTxnList.get(loop4).getTy() + ""
                                            + ",'" + cashPOTxnList.get(loop4).getEnterBy() + "',null,'N',null,CURRENT_TIMESTAMP," + cashPOTxnList.get(loop4).getRecNo() + ",null)");
                                    int result1 = insert1.executeUpdate();
                                    if (result1 <= 0) {
                                        throw new ApplicationException("Problem Occured In Suspense Updation");
                                    }
                                }
                            }
                        }
                    }
                }
                double tdTmpFBal = 0;
                String acctNature = ftsPostingRemote.getAccountNature(obj.getAcNo());
                if ((acctNature.equals(CbsConstant.FIXED_AC) || acctNature.equals(CbsConstant.MS_AC)) && obj.getTy().equals("1")) {
                    List list2 = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0))-sum(ifnull(Dramt,0)),0) as decimal(14,2)) FROM td_recon WHERE ACNO='"
                            + obj.getAcNo() + "' AND TRANTYPE<>27 AND CLOSEFLAG IS NULL AND DT<=CONVERT('" + tempBd + "',DATETIME)").getResultList();
                    if (!list2.isEmpty()) {
                        Vector v2 = (Vector) list2.get(0);
                        tdTmpFBal = Double.parseDouble(v2.get(0).toString());
                    }
                    if ((tdTmpFBal - obj.getAmount()) < 0) {
                        throw new ApplicationException("Please Check the Statement Balance for A/c No. " + obj.getAcNo() + " Batch Cancelled");
                    }
                }
                String ftsProcResult = ftsPostingRemote.ftsPosting43CBS(obj.getAcNo(), Integer.parseInt(obj.getTranType()), Integer.parseInt(obj.getTy()), obj.getAmount(),
                        obj.getDt(), obj.getValueDate(), obj.getEnterBy(), obj.getOrgnBrId(), obj.getDestBrId(), Integer.parseInt(obj.getTranDesc()),
                        obj.getDetails(), tranNo, obj.getRecNo(), Integer.parseInt(String.valueOf(obj.getTranId())), obj.getTermId(),
                        obj.getAuth(), obj.getAuthBy(), obj.getSubTokenNo(), Integer.parseInt(obj.getPayBy()), obj.getInstNo(), instDt, obj.getTdAcNo(),
                        obj.getVoucherNo(), obj.getIntFlag(), obj.getCloseFlag(), obj.getScreenFlag(), obj.getTxnStatus(), obj.getTokenNoDr(),
                        obj.getCxsxFlag(), "", "", "");

                System.out.println("After executing FTS Posting in case of transfer. Batch No = " + tranNo + " and acno = " + obj.getAcNo() + " and amount is = " + obj.getAmount());
                if (!ftsProcResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    throw new ApplicationException(ftsProcResult);
                }

                //STR 
                if (!(obj.getAlertSubCode() == null || obj.getAlertSubCode().equalsIgnoreCase(""))) {
                    String str = ftsPostingRemote.strDetailInsertion(obj.getAlertSubCode(), obj.getAcNo(), tranNo, obj.getRecNo(), obj.getOrgnBrId(),
                            obj.getDestBrId(), obj.getEnterBy());
                    if (!str.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Insertion Problem in cbs_str_details !");
                    }
                }
            }
            ut.commit();
            System.out.println("After comminting transaction. Batch No = " + tranNo);
            return String.valueOf(tranNo);
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

    public String chkInstNoTrf(String accNo, String instNo, String tableName) throws ApplicationException {
        List list1 = em.createNativeQuery("select statusflag from " + tableName + " where acno='" + accNo + "' and chqno=" + Float.parseFloat(instNo) + "").getResultList();
        if (list1.isEmpty()) {
            throw new ApplicationException("The Instrument No. " + instNo + " Does Not Exist");
        }
        if (list1.get(0).toString().equals("U")) {
            throw new ApplicationException("Instrument no. " + instNo + " has Already been Used");
        } else if (list1.get(0).toString().equals("S")) {
            throw new ApplicationException("Stop Payment");
        }
        return "True";
    }

    public List checkForNPA(String tempAcctNature, String accNo) {
        List list = em.createNativeQuery("select ACNO from loan_appparameter where ACNO='" + accNo + "' AND (presentstatus not in ('OPERATIVE','IRREGULAR','NULL'))").getResultList();
        return list;
    }

    public String updateSundryAccount(String accNo, String authBy, String seqNo, String year,
            String orgBrnCode, String sundryTable, String date, float recno) throws ApplicationException {
        try {
            Query update = em.createNativeQuery("Update " + sundryTable + " SET status='PAID',dt='" + date + "',lastupdateby='"
                    + authBy + "' ,RECNO=" + recno + " WHERE acno='" + accNo + "' and seqno=" + Float.parseFloat(seqNo) + " and fyear="
                    + Integer.parseInt(year) + " and status='ISSUED'");
            int updateResult = update.executeUpdate();
            if (updateResult <= 0) {
                return "False";
            } else {
                return "True";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String checkForAccountNo(String accNo) throws ApplicationException {
        List list = new ArrayList();
        String nature = ftsPostingRemote.getAccountNature(accNo);
        try {
            if (nature.equals(CbsConstant.FIXED_AC) || nature.equals(CbsConstant.MS_AC)) {
                String chkMsg = txnAuth.fdFidilityChk(accNo);
                if (chkMsg.equalsIgnoreCase("true")) {
                    list = em.createNativeQuery("select * from fidility_accountmaster where acno='" + accNo + "'").getResultList();
                } else {
                    list = em.createNativeQuery("select * from td_accountmaster where acno='" + accNo + "'").getResultList();
                }
            } else if (nature.equals(CbsConstant.PAY_ORDER)) {
                list = em.createNativeQuery("select * from gltable where acno='" + accNo + "'").getResultList();
            } else {
                list = em.createNativeQuery("select * from accountmaster where acno='" + accNo + "'").getResultList();
            }
            if (list.isEmpty()) {
                return "Account no. does not exist";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "TRUE";
    }

    public int ccodStockExp(String accNo) throws ApplicationException {
        try {
//            Calendar cal1 = Calendar.getInstance();
//            Calendar cal2 = Calendar.getInstance();
            List list1 = em.createNativeQuery("select * from loansecurity where acno='" + accNo + "'").getResultList();
            if (list1.isEmpty()) {
                return 95;
            }
            List list2 = em.createNativeQuery("select DATE_FORMAT(max(nextstmdt),'%Y%m%d') from loansecurity where acno='" + accNo + "'").getResultList();
            Vector v2 = (Vector) list2.get(0);
            String maxNextDt = v2.get(0).toString();

            String currDate = ymdFormat.format(new Date());
            if (CbsUtil.dayDiff(ymdFormat.parse(maxNextDt), ymdFormat.parse(currDate)) > 0) {
                return 95;
            }
//            cal1.set(Integer.parseInt(maxNextDt.substring(0, 4)), Integer.parseInt(maxNextDt.substring(4, 6)), Integer.parseInt(maxNextDt.substring(6)));
//            cal2.set(Integer.parseInt(currDate.substring(0, 4)), Integer.parseInt(currDate.substring(4, 6)), Integer.parseInt(currDate.substring(6)));
//            
//            if (cal1.before(cal2)) {
//                return 95;
//            }
            List list4 = em.createNativeQuery("select * from loan_insurance where acno='" + accNo + "'").getResultList();
            if (list4.isEmpty()) {
                return 96;
            }
            List list5 = em.createNativeQuery("select DATE_FORMAT(max(todt),'%Y%m%d') from loan_insurance where acno='" + accNo + "'").getResultList();
            Vector v5 = (Vector) list5.get(0);
            maxNextDt = v5.get(0).toString();
            if (CbsUtil.dayDiff(ymdFormat.parse(maxNextDt), ymdFormat.parse(currDate)) > 0) {
                return 96;
            }
//            cal1.set(Integer.parseInt(maxNextDt.substring(0, 4)), Integer.parseInt(maxNextDt.substring(4, 6)), Integer.parseInt(maxNextDt.substring(6)));
//            if (cal1.before(cal2)) {
//                return 96;
//            }
            List list6 = em.createNativeQuery("select DATE_FORMAT(max(sanctionlimitdt),'%Y%m%d') from loan_appparameter where acno='" + accNo + "'").getResultList();
            Vector v6 = (Vector) list6.get(0);
            maxNextDt = v6.get(0).toString();
            if (CbsUtil.dayDiff(ymdFormat.parse(maxNextDt), ymdFormat.parse(currDate)) > 0) {
                return 97;
            }
//            cal1.set(Integer.parseInt(maxNextDt.substring(0, 4)), Integer.parseInt(maxNextDt.substring(4, 6)), Integer.parseInt(maxNextDt.substring(6)));
//            if (cal1.before(cal2)) {
//                return 97;
//            }
            return 0;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List checkLoanAuthorization(String accNo) {
        List list = em.createNativeQuery("select acno from accountmaster where (authby is null or authby ='') and acno = '" + accNo + "'").getResultList();
        return list;
    }
    /*                Start Of Repayment Schedule Code         */

    public List getRepaymentScheduleInfo(String accNo) throws ApplicationException {
        List list = new ArrayList();
        try {
            list = em.createNativeQuery("select ifnull(max(sno),'') from emidetails where acno='" + accNo + "' and upper(status)='UNPAID'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }
    /*                End Of Repayment Schedule Code         */

    public List checkForSundryTableAmount(String accNo, String seqNo, String year, String sundryTable) {
        List list = em.createNativeQuery("select amount,ty from " + sundryTable + " where acno='" + accNo + "' "
                + "and seqno=" + Float.parseFloat(seqNo) + " and fyear=" + Integer.parseInt(year) + " "
                + "AND status='ISSUED'").getResultList();
        return list;
    }

    public boolean contra(int a1, String a2) {
        if (a1 == 11 && a2.equalsIgnoreCase("1")) {
            return true;
        }
        if (a1 == 12 && a2.equalsIgnoreCase("0")) {
            return true;
        }
        return false;
    }

    public List getSumOfAmountFromIntermediateTable(String interTableName, String seqNo, String year) {
        List list = em.createNativeQuery("select coalesce(sum(amount),0) from " + interTableName + " "
                + "where seqno=" + Double.parseDouble(seqNo) + " and fyear=" + Integer.parseInt(year) + " "
                + "/*and status='ISSUED'*/").getResultList();
        return list;
    }

    public List getDetailsOfUnAuthAccounts(String accNo) throws ApplicationException {
        try {
            String accNature = ftsPostingRemote.getAccountNature(accNo);
            List list = new ArrayList();
            if (accNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || accNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                list = em.createNativeQuery("select * from td_accountmaster where (authby is null or authby ='') "
                        + "and acno = '" + accNo + "'").getResultList();
            } else {
                list = em.createNativeQuery("select * from accountmaster where (authby is null or authby ='') "
                        + "and acno = '" + accNo + "'").getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }
    /*End of Transaction Management*/

    /*Start of Extension Counter*/
    public List getDropdownList(String brCode) throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select CONCAT(acname,' --> ',acno) from gltable where substring(acno,1,2)='" + brCode + "'");
            acctNo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acctNo;
    }

    public List extensionCounterDropDown() throws ApplicationException {
        List extensionCon = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select description from codebook where groupcode=38 and code<>0");
            extensionCon = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return extensionCon;
    }

    public List accountDetail(String acctNo) throws ApplicationException {
        List accountInfo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select acname,acno,ifnull(postflag,0) as postflag from gltable where acno='" + acctNo + "'");
            accountInfo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accountInfo;
    }

    public List getBalance(String acctNo) throws ApplicationException {

        List balanceInfo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select balance from reconbalan where acno='" + acctNo + "'");
            balanceInfo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return balanceInfo;
    }

    public String saveExtTxn(List list, String dt, String enterBy, String details, String brcode)
            throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            float recnum = 0.0f;
            if ((dt == null) || (enterBy == null) || (details == null) || (list.isEmpty())) {
                ut.rollback();
                return "Data could not be null.";
            }
            Integer var = 0;
            Integer var1 = 0;
            int si = list.size();
            for (int a = 0, b = 1, c = 2, d = 3, e = 4; a < list.size(); a = a + 5, b = b + 5, c = c + 5, d = d + 5, e = e + 5) {
                recnum = ftsPostingRemote.getRecNo();
                Query insertQuery = em.createNativeQuery("insert into recon_ext_d(ACNo,custname,Dt,Valuedt,DrAmt,CrAmt,Ty,TranType,recno,trsno,payby,iy,auth,EnterBy,TranDesc,Trantime,details)"
                        + "values (" + "'" + list.get(a) + "'" + "," + "'" + list.get(d) + "'" + "," + "CONVERT(varchar,CONVERT(datetime," + "'" + dt + "'" + ",103),121)" + "," + "CONVERT(varchar,CONVERT(datetime," + "'" + dt + "'" + ",103),121)" + "," + list.get(c) + "," + list.get(b) + "," + list.get(e) + "," + 5 + "," + recnum + "," + 0 + "," + 3 + "," + 1 + "," + "'N'" + "," + "'" + enterBy + "'" + "," + 0 + ",CURRENT_TIMESTAMP," + "'" + details + "'" + ")");
                var = insertQuery.executeUpdate();
            }
            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "Data could not be saved";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    /*End of Extension Counter*/

    /*Start of Account View UnAuthorization*/
    public List<TxnDetailBean> accViewUnauth(String acNo, String curDate, String brCode) throws ApplicationException {
        try {
            List<TxnDetailBean> txnDetailList = new ArrayList<TxnDetailBean>();
            curDate = curDate.substring(6) + curDate.substring(3, 5) + curDate.substring(0, 2);

            String acctNat = ftsPostingRemote.getAccountNature(acNo);
            double openingBal = commonReport.getBalanceOnDate(acNo, curDate);

            String query = "(SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,auth,ty, coalesce(Details,''),org_brnid from "
                    + "tokentable_credit where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                    + "union all "
                    + "(SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,auth,ty,coalesce(Details,''),org_brnid from "
                    + "tokentable_debit where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                    + "union all "
                    + "(SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,auth,ty,coalesce(Details,''),org_brnid from "
                    + "recon_cash_d where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                    + "union all "
                    + "(SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,auth,ty,coalesce(Details,''),org_brnid from "
                    + "recon_clg_d where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                    + "union all "
                    + "(SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,auth,ty,coalesce(Details,''),org_brnid from "
                    + "recon_trf_d where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                    //                    + "union all "
                    //                    + "(SELECT date_format(dt,'%d/%m/%Y'),Trantype,coalesce(instno,''),DrAmt,CrAmt,EnterBy,auth,ty,coalesce(Details,'') from "
                    //                    + "recon_ext_d where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='N' OR auth IS NULL) order by dt,recno) "
                    + "union all "
                    + "(SELECT date_format(dt,'%d/%m/%Y'),1,coalesce(inst_no,''),inst_amt,0,Enter_By,auth,1,coalesce(userdetails,''),orgn_branch from "
                    + "cts_clg_in_entry where Acno='" + acNo + "' and dt='" + curDate + "' and (auth='' or userdetails='hold') order by dt,tran_time)";

            List checkList3 = em.createNativeQuery(query).getResultList();
            TxnDetailBean txnDetailObj;
            for (int i = 0; i < checkList3.size(); i++) {
                Vector vect = (Vector) checkList3.get(i);

                txnDetailObj = new TxnDetailBean();
                txnDetailObj.setAcNo(acNo);

                txnDetailObj.setDt(vect.get(0).toString());
                txnDetailObj.setTranType(vect.get(1).toString());
                txnDetailObj.setInstNo(vect.get(2).toString());

                txnDetailObj.setDrAmt(Double.parseDouble(vect.get(3).toString()));
                txnDetailObj.setCrAmt(Double.parseDouble(vect.get(4).toString()));

                txnDetailObj.setEnterBy(vect.get(5).toString());
                if (vect.get(6).toString().equals("")) {
                    txnDetailObj.setAuthBy("N");
                } else {
                    txnDetailObj.setAuthBy(vect.get(6).toString());
                }

                int tranType = Integer.parseInt(vect.get(1).toString());
                int ty = Integer.parseInt(vect.get(7).toString());
                String details = vect.get(8).toString().toUpperCase();
                String particular = "";
                if (acctNat.equals(CbsConstant.PAY_ORDER)) {
                    String poDetails = "";
                    String array[];
                    if (!details.equalsIgnoreCase("")) {
                        if (details.contains("#")) {
                            if (details.contains("#F`") && details.contains("#G`")) {
                                array = vect.get(8).toString().split("#");
                                poDetails = array[0] + " " + details.substring(details.indexOf("#F`") + 3, details.indexOf("#G`"));
                            } else {
                                poDetails = details;
                            }

                        } else if (vect.get(8).toString().contains("~`")) {
                            array = vect.get(8).toString().split("~`");
                            poDetails = array[0] + " " + array[16];
                        } else {
                            poDetails = vect.get(8).toString();
                        }
                    } else {
                        poDetails = vect.get(8).toString();
                    }
                    details = poDetails;
                }
                if (ty == 0) {
                    openingBal = openingBal + txnDetailObj.getCrAmt();
                    if (tranType == 0) {
                        particular = "BY CASH " + details;
                    } else if (tranType == 1) {
                        particular = "BY CLEARING " + details;
                    } else if (tranType == 2) {
                        particular = "BY TRANSFER " + details;
                    } else if (tranType == 5) {
                        particular = "BY EXTENSION " + details;
                    } else if (tranType == 8) {
                        particular = "BY TRANSFER " + details;
                    }
                } else {
                    openingBal = openingBal - txnDetailObj.getDrAmt();
                    if (tranType == 0) {
                        particular = "TO CASH " + details;
                    } else if (tranType == 1) {
                        particular = "TO CLEARING " + details;
                    } else if (tranType == 2) {
                        particular = "TO TRANSFER " + details;
                    } else if (tranType == 5) {
                        particular = "TO EXTENSION " + details;
                    } else if (tranType == 8) {
                        particular = "TO TRANSFER " + details;
                    }
                }
                txnDetailObj.setOrgnBrId(vect.get(9).toString());
                txnDetailObj.setDetails(particular);
                txnDetailObj.setAmount(openingBal);
                txnDetailList.add(txnDetailObj);
            }
            return txnDetailList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    /*End of Account View UnAuthorization*/

    public int getRemoteCashLimitFlag() throws ApplicationException {
        int code = 0;
        try {
            List codeList = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME='Remote Cash Limit'").getResultList();
            if (!codeList.isEmpty()) {
                Vector element = (Vector) codeList.get(0);
                code = Integer.parseInt(element.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return code;
    }

    public List getRemoteTranLimits(String accode) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            resultList = em.createNativeQuery("select * from cbs_remote_txn_limit where account_code='" + accode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public BigDecimal getTotalCashTranAmtOnCurrentDt(String acno, String orgBrCode, String option) throws ApplicationException {
        List tranAmtList = null;
        BigDecimal totalAmount = new BigDecimal("0");
        try {
            if (option.equalsIgnoreCase("C")) {
                tranAmtList = em.createNativeQuery("select ifnull(sum(cramt),0) from tokentable_credit where acno='" + acno + "' and dt='" + ymdFormat.format(new Date()) + "' and org_brnid<>'" + orgBrCode + "' and dest_brnid='" + orgBrCode + "' and auth ='N'").getResultList();
                Vector amtElement = (Vector) tranAmtList.get(0);
                totalAmount = new BigDecimal(amtElement.get(0).toString());

                tranAmtList = em.createNativeQuery("select ifnull(sum(cramt),0) from recon_cash_d where acno='" + acno + "' and dt='" + ymdFormat.format(new Date()) + "' and org_brnid<>'" + orgBrCode + "' and dest_brnid='" + orgBrCode + "'").getResultList();
                amtElement = (Vector) tranAmtList.get(0);
                totalAmount = totalAmount.add(new BigDecimal(amtElement.get(0).toString()));
            } else if (option.equalsIgnoreCase("D")) {
                tranAmtList = em.createNativeQuery("select ifnull(sum(dramt),0) from tokentable_debit where acno='" + acno + "' and dt='" + ymdFormat.format(new Date()) + "' and org_brnid<>'" + orgBrCode + "' and dest_brnid='" + orgBrCode + "'").getResultList();
                Vector amtElement = (Vector) tranAmtList.get(0);
                totalAmount = new BigDecimal(amtElement.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return totalAmount;
    }

    public List getUnAuthPayOrderPayment(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select details from tokentable_debit where acno='" + acno + "' and auth='n' and ty=1 union "
                    + "select details from recon_trf_d where acno='" + acno + "' and auth='n' and ty=1 union "
                    + "select details from recon_clg_d where acno='" + acno + "' and auth='n' and ty=1 ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<String> getCustIdByAccount(String accountNo) throws ApplicationException {
        List<String> dataList = new ArrayList<String>();
        NumberFormat formatter = new DecimalFormat("#.##");
        try {
            List custIdList = em.createNativeQuery("select custid from customerid where acno='" + accountNo + "'").getResultList();
            if (!custIdList.isEmpty()) {
                String entityType = "";
                Vector element = (Vector) custIdList.get(0);
                String custId = element.get(0).toString();
                dataList.add(custId);
                custIdList = em.createNativeQuery("select ifnull(networth,'0'),ifnull(operationalriskrating,''),custentitytype from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
                if (!custIdList.isEmpty()) {
                    element = (Vector) custIdList.get(0);
                    dataList.add(formatter.format(Double.parseDouble(element.get(0).toString())));
                    String riskCode = element.get(1).toString();
                    entityType = element.get(2).toString();
                    custIdList = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no='024' and ref_code='" + riskCode + "'").getResultList();
                    if (!custIdList.isEmpty()) {
                        element = (Vector) custIdList.get(0);
                        dataList.add(element.get(0).toString());
                    } else {
                        dataList.add("");
                    }
                } else {
                    dataList.add("");
                    dataList.add("");
                }

                custIdList = em.createNativeQuery("select ifnull(pan_girnumber,'') from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
                if (!custIdList.isEmpty()) {
                    element = (Vector) custIdList.get(0);
                    dataList.add(element.get(0).toString());
                } else {
                    dataList.add("");
                }
                String occupationCode = "";
                custIdList = em.createNativeQuery("select ifnull(occupation,'') from customermaster where custno='" + accountNo.substring(4, 10) + "' and actype='" + accountNo.substring(2, 4) + "' and agcode='" + accountNo.substring(10) + "' and brncode='" + accountNo.substring(0, 2) + "'").getResultList();
                if (!custIdList.isEmpty()) {
                    element = (Vector) custIdList.get(0);
                    occupationCode = element.get(0).toString();
                    custIdList = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no='021' and ref_code='" + occupationCode + "'").getResultList();
                    if (!custIdList.isEmpty()) {
                        element = (Vector) custIdList.get(0);
                        dataList.add(element.get(0).toString());
                    } else {
                        dataList.add("");
                    }
                } else {
                    dataList.add("");
                }

                custIdList = em.createNativeQuery("select ifnull(IdentityNo,'') from cbs_customer_master_detail where legal_document ='E' and customerid = '" + custId + "' "
                        + " union "
                        + " select ifnull(IdentityNo,'') from cbs_cust_identity_details where IdentificationType ='E' and customerid = '" + custId + "'").getResultList();
                if (!custIdList.isEmpty()) {
                    element = (Vector) custIdList.get(0);
                    dataList.add(element.get(0).toString());
                } else {
                    dataList.add("");
                }
                dataList.add(entityType);
                
                 custIdList = em.createNativeQuery("Select ifnull(acctCategory,'') from accountmaster where AcNo='" + accountNo + "'"
                                    + "union all Select ifnull(acctCategory,'') from td_accountmaster where AcNo='" + accountNo + "'").getResultList();
                if (!custIdList.isEmpty()) {
                    element = (Vector) custIdList.get(0);
                    dataList.add(element.get(0).toString());
                } else {
                    dataList.add("");
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public BigDecimal getFinYearDepositAsWellAsMonthWithdrawalTillDate(String accountNo, String finYearMinDate) throws ApplicationException {
        BigDecimal totalFinYearDepOrMonthWithdrawal = new BigDecimal("0");
        try {
            String acNature = ftsPostingRemote.getAccountNature(accountNo);
            String reconTable = commonReport.getTableName(acNature);
            String curDate = ymdFormat.format(new Date());
            List amountList = new ArrayList();
            if (!finYearMinDate.equals("")) {
                amountList = em.createNativeQuery("select cast(ifnull(sum(cramt),0) as decimal(25,2)) from " + reconTable + " "
                        + "where acno='" + accountNo + "' and dt>='" + finYearMinDate + "' and dt<='" + curDate + "' and "
                        + "trantype in(0,2) and ty=0").getResultList();
            } else {
                String monthMinDt = curDate.substring(0, 6) + "01";
                amountList = em.createNativeQuery("select cast(ifnull(sum(dramt),0) as decimal(25,2)) from " + reconTable + " "
                        + "where acno='" + accountNo + "' and dt>='" + monthMinDt + "' and dt<='" + curDate + "' and "
                        + "trantype in(0,2) and ty=1").getResultList();
            }
            Vector element = (Vector) amountList.get(0);
            totalFinYearDepOrMonthWithdrawal = new BigDecimal(element.get(0).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return totalFinYearDepOrMonthWithdrawal;
    }

    public List getCustomerDetailByAcno(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("Select acno,custname,jtname1,openingdt,adhoclimit,AccStatus from accountmaster where AcNo='" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcnoCheckByAcno(String acno) throws ApplicationException {
        List acNoList = null;
        try {
            acNoList = em.createNativeQuery("select distinct(ACNo) from npa_recon where ACNo = '" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acNoList;
    }

    public List getNpaAcnoChechByAcno(String acno) throws ApplicationException {
        List acNoList = null;
        try {
            acNoList = em.createNativeQuery("select acno from accountmaster where AccStatus in (11,12,13) and acno = '" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acNoList;
    }

    public Float getNpaRecNo() throws ApplicationException {
        try {
            Float recno;
            List recnoList = em.createNativeQuery("select ifnull(max(ifnull(recno,0)),0) from npa_recon").getResultList();
            Vector recnoVect = (Vector) recnoList.get(0);
            recno = Float.parseFloat(recnoVect.get(0).toString()) + 1;
            return recno;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveNpaTransactionData(String acNo, String transType, String type, String by, String relatedTo, String date, double amt, String detail, String enterBy, String brcode, String status) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String uriGl = "";
        String oirHead = "";
        try {

            ut.begin();
            Integer uu;
            Float recno = getNpaRecNo();
            String detail1 = "Memorandam Manual: " + detail;
            List glHeadList = em.createNativeQuery("SELECT glheadint,IFNULL(GLHEADURI,''),IFNULL(glheadname,'') from accounttypemaster where acctcode = '" + acNo.substring(2, 4) + "'").getResultList();
            if (glHeadList.isEmpty()) {
                throw new ApplicationException("GL Head Doesn't Exists for ");
            } else {
                Vector glHeadVect = (Vector) glHeadList.get(0);
                uriGl = glHeadVect.get(1).toString();
                oirHead = glHeadVect.get(2).toString();
            }
            double npaBal = getNpaBalCheck(acNo, date);
            if (npaBal > 0) {
                throw new ApplicationException("Interest Entry already exist in Memorandam. Please choose another Account.");
            }

            if (type.equalsIgnoreCase("0")) {
                Query q = em.createNativeQuery("insert into npa_recon (acno,TranType,ty,crAmt,dramt,dt,Details,recno,intamt,valuedt,trandesc,"
                        + "enterby,auth,org_brnid,dest_brnid,Balance,iy,Payby,Authby,Trsno,Trantime,Status)" + " values"
                        + "('" + acNo + "'," + transType + "," + type + "," + amt + ",0,'" + date + "','" + detail1 + "'," + recno + ",0,'" + date + "'," + relatedTo + ","
                        + "'" + enterBy + "','N','" + brcode + "','" + brcode + "',0,0,3,'" + enterBy + "',0,now(),'" + status + "')");
                uu = q.executeUpdate();
            } else {
                Query q = em.createNativeQuery("insert into npa_recon (acno,TranType,ty,drAmt,crAmt,dt,Details,recno,intamt,valuedt,trandesc,"
                        + "enterby,auth,org_brnid,dest_brnid,Balance,iy,Payby,Authby,Trsno,Trantime,Status)" + " values"
                        + "('" + acNo + "'," + transType + "," + type + "," + amt + ",0,'" + date + "','" + detail1 + "'," + recno + ",0,'" + date + "'," + relatedTo + ","
                        + "'" + enterBy + "','N','" + brcode + "','" + brcode + "',0,0,3,'" + enterBy + "',0,now(),'" + status + "')");
                uu = q.executeUpdate();
            }

            if (!oirHead.equals("") && !uriGl.equals("")) {

                uriGl = acNo.substring(0, 2) + uriGl + "01";
                oirHead = acNo.substring(0, 2) + oirHead + "01";
                float recNo = ftsPostingRemote.getRecNo();
                float dTrSNo = ftsPostingRemote.getTrsNo();

                Query insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                        + " VALUES('" + oirHead + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + date + "',"
                        + amt + ",0,2," + relatedTo + ",3,'" + detail1 + "','system','Y','" + enterBy
                        + "'," + dTrSNo + "," + recno + ",'" + acNo.substring(0, 2) + "','" + acNo.substring(0, 2) + "')");

                Integer insertReconBalan = insertReconBalanQuery.executeUpdate();
                if (insertReconBalan == 0) {
                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                }
                recNo = ftsPostingRemote.getRecNo();
                insertReconBalanQuery = em.createNativeQuery("INSERT  gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                        + " VALUES('" + uriGl + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + date + "',0,"
                        + amt + ",2," + relatedTo + ",3,'" + detail1 + "','system','Y','" + enterBy
                        + "'," + dTrSNo + "," + recNo + ",'" + acNo.substring(0, 2) + "','" + acNo.substring(0, 2) + "')");

                insertReconBalan = insertReconBalanQuery.executeUpdate();
                if (insertReconBalan == 0) {
                    throw new ApplicationException("Value doesn't inserted in GL_RECON");
                }

                Query updateReconBalanQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)+ " + amt + " WHERE ACNO= '" + oirHead + "'");
                Integer updateReconBalan = updateReconBalanQuery.executeUpdate();
                if (updateReconBalan == 0) {
                    throw new ApplicationException("Value doesn't updated in reconbalan");
                }

                Query updateReconBalanUriQuery = em.createNativeQuery("UPDATE reconbalan SET BALANCE=IFNULL(BALANCE,0)- " + amt + " WHERE ACNO= '" + uriGl + "'");
                Integer updateReconBalanUri = updateReconBalanUriQuery.executeUpdate();
                if (updateReconBalanUri == 0) {
                    throw new ApplicationException("Value doesn't updated in reconbalan");
                }
            }

            if (uu > 0) {
                ut.commit();
                return "Data Save successfully !!!";
            } else {
                ut.rollback();
                return "Some Problem in Data Save !!!";
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

    public List getNpaUnauthData(String brcode, String txnDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno,TranType,ty,crAmt,dramt,dt,Details,recno,enterby,auth from npa_recon where substring(acno,1,2)='" + brcode + "' and auth ='N'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteNpaDetail(String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            String query = "delete from npa_recon where acno = '" + acno + "' and auth = 'N'";
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            ut.commit();
            return "Npa Detail successfully deleted";
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

    public String UpdateNpaAuthStatus(String acno, String authby) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String query = "update npa_recon set auth = 'Y',Authby = '" + authby + "' where acno = '" + acno + "'";
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation");
            }

            ut.commit();
            return "Verify successfully";
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

    public List selectFidiltyDtl(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select acno,custname,'','','','','','Self',DATE_FORMAT(openingdt,'%Y%m%d') 'openingdt',accstatus from "
                    + "fidility_accountmaster where AcNo='" + accNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Account Details does not exist");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String loanExpCheck(String acno, String Tempbd, String acctOpenDate) throws ApplicationException {
        String msg = "true";
        try {
            List result = em.createNativeQuery("select ifnull(loan_pd_month,0),ifnull(loan_pd_day,0) from cbs_loan_acc_mast_sec where acno = '" + acno + "'").getResultList();
            if (!result.isEmpty()) {
                Vector vtrmain = (Vector) result.get(0);
                int loanPdM = Integer.parseInt(vtrmain.get(0).toString());
                int loanPdD = Integer.parseInt(vtrmain.get(1).toString());

                List monAddList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + acctOpenDate + "', INTERVAL " + loanPdM + " MONTH ), '%Y%m%d')").getResultList();
                Vector monAddListVec = (Vector) monAddList.get(0);
                String tempDt1 = monAddListVec.get(0).toString();

                List dayAddList = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD( '" + tempDt1 + "', INTERVAL " + loanPdD + " DAY ), '%Y%m%d')").getResultList();
                Vector dayAddListVec = (Vector) dayAddList.get(0);
                String tempDt2 = dayAddListVec.get(0).toString();

                List dateDiffLt = em.createNativeQuery("SELECT TIMESTAMPDIFF (DAY,'" + Tempbd + "','" + tempDt2 + "')").getResultList();
                Vector dateDiffLtVec = (Vector) dateDiffLt.get(0);
                int effNoOfDays = Integer.parseInt(dateDiffLtVec.get(0).toString());

                List noMaster = em.createNativeQuery("SELECT ifnull(code,999) from parameterinfo_report where reportName ='LOAN-EXP-DAYS'").getResultList();
                if (!noMaster.isEmpty()) {
                    Vector masDiffVec = (Vector) noMaster.get(0);
                    int masDays = Integer.parseInt(masDiffVec.get(0).toString());

                    if ((effNoOfDays > 0) && (effNoOfDays <= masDays)) {
                        msg = "Account Will Expire in " + effNoOfDays + " Days";
                    } else if ((effNoOfDays <= 0) && (effNoOfDays <= masDays)) {
                        msg = "Account is Already Expired";
                    } else {
                        msg = "true";
                    }
                } else {
                    msg = "true";
                }
            } else {
                msg = "true";
            }
        } catch (Exception e) {
            msg = "true";
        }
        return msg;
    }

    public String kycExpCheck(String acno, String curDate) {
        try {
            String kycUpdationDt = "19000101";
            String riskCategory = "03";

            List cuUpdateList = em.createNativeQuery("SELECT ifnull(date_format(CustUpdationDate,'%Y%m%d'),'19000101'),ifnull(operationalRiskrating,'03') "
                    + "from cbs_customer_master_detail cc,customerid c where cc.customerid = c.custId and c.acno='" + acno + "'").getResultList();
            if (!cuUpdateList.isEmpty()) {
                Vector cuUpdateListVec = (Vector) cuUpdateList.get(0);
                kycUpdationDt = cuUpdateListVec.get(0).toString();
                riskCategory = cuUpdateListVec.get(1).toString();
            }
            if (kycUpdationDt.equals("19000101")) {
                return "Please update the KYC Detail of this Customer";
            }
            long effNoOfDays = CbsUtil.dayDiff(ymdFormat.parse(kycUpdationDt), ymdFormat.parse(curDate));
            long year = effNoOfDays / 365;
            if (riskCategory.equals("03") && year >= 10) {
                return "Please update the KYC Detail of this Customer";
            }
            if (riskCategory.equals("02") && year >= 8) {
                return "Please update the KYC Detail of this Customer";
            }
            if (riskCategory.equals("01") && year >= 2) {
                return "Please update the KYC Detail of this Customer";
            }
            return "true";
        } catch (Exception e) {
            return "true";
        }
    }

    public long getPollInterval() throws ApplicationException {
        try {
            List dataList = em.createNativeQuery("select Min(due_dt),grace_pd from cbs_pymt_details where pymt_flag = 'N'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Payment related data does not exist");
            }
            Vector dataVect = (Vector) dataList.get(0);
            String dueDt = dataVect.get(0).toString();

            long timePd = CbsUtil.dayDiff(ymdFormat.parse(dueDt), ymdFormat.parse(ymdFormat.format(new Date())));

            List timeInterValList = em.createNativeQuery("select alert_time from cbs_pymt_alert_slab where days = (select max(days) from "
                    + "cbs_pymt_alert_slab where days < " + timePd + ")").getResultList();
            if (timeInterValList.isEmpty()) {
                return 0;
            }
            Vector timeInterValVect = (Vector) timeInterValList.get(0);
            int interval = Integer.parseInt(timeInterValVect.get(0).toString());

            return interval * 60 * 1000;
        } catch (Exception e) {
            throw new ApplicationException("From CBS Payment Made, The Error is ---------->" + e.getMessage());
        }
    }

    public String markDemand(List<VillageWiseEMIDetailPojo> reportList, String user, String date, String orgnCode, String frDate, String toDate, String offId, String deptId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Integer dmdSrl = null;
        String accountNo = null;
        double prinamt;
        double interestamt;
        String lgFreq = "M";
        Integer ShdlNo = null;
        double elAmt;
        String dueDate;
        // Integer sno = null;
        double installamt;

        try {
            ut.begin();

            List loanDMList = em.createNativeQuery("select ifnull(max(dmd_srl_num),0) from cbs_loan_dmd_table ").getResultList();
            if (loanDMList.size() > 0) {
                Vector ele = (Vector) loanDMList.get(0);
                dmdSrl = Integer.parseInt(ele.get(0).toString()) + 1;
            }

            List loanDemandList = em.createNativeQuery("select * from cbs_loan_dmd_info where FromDT = '" + frDate + "' and ToDT = "
                    + "'" + toDate + "' and officeId = '" + offId + "' and depetId = '" + deptId + "'").getResultList();
            if (!loanDemandList.isEmpty()) {
                ut.rollback();
                return "Demand for this period already Posted";
            }

            Integer demandInfoList = em.createNativeQuery("Insert into cbs_loan_dmd_info(sno,acno,flag,brnCode,postingDt,FromDT,ToDT,recoverydt,officeid,depetid) Values"
                    + "(" + dmdSrl + ",'','A','" + orgnCode + "','" + ymdFormat.format(yMMd.parse(date)) + "','" + frDate + "','" + toDate + "',NULL,'" + offId + "','" + deptId + "')").executeUpdate();
            if (demandInfoList < 0) {
                ut.rollback();
                return "Data is not inserted into cbs_loan_demand_info";
            }

            for (VillageWiseEMIDetailPojo it : reportList) {
                String schemeCode = null;
                accountNo = it.getAcNo();
                List SecDetailsList = em.createNativeQuery("SELECT A.ACNO, A.SCHEME_CODE from "
                        + "cbs_loan_acc_mast_sec A, loan_appparameter B where A.ACNO = B.ACNO AND "
                        + "A.ACNO ='" + accountNo + "'").getResultList();
                if (SecDetailsList.isEmpty()) {
                    ut.rollback();
                    return "Account No Does Not Exists in Secondary Details table of Loan.";
                } else {
                    for (int j = 0; j < SecDetailsList.size(); j++) {
                        Vector SecDetailsVect = (Vector) SecDetailsList.get(j);
                        schemeCode = (String) SecDetailsVect.get(1);
                    }
                }

                List flowDetailList = em.createNativeQuery("select ei_flow_id, principal_flow_id,"
                        + " disbursement_flow_id, collection_flow_id, int_demand_flow_id, "
                        + "penal_int_demand_flow_id, overdue_int_demand_flow_id, past_due_collection_flow_id,"
                        + " charge_demand_flow_id from cbs_scheme_loan_prepayment_details where "
                        + "scheme_code =  '" + schemeCode + "'").getResultList();
                String prinDemFlowId = null;
                String intDemFlowId = null;

                if (flowDetailList.isEmpty()) {
                    ut.rollback();
                    return "Flow Id Does Not Exists in Scheme Master.";
                } else {
                    for (int k = 0; k < flowDetailList.size(); k++) {
                        Vector flowDetailVect = (Vector) flowDetailList.get(k);
                        prinDemFlowId = flowDetailVect.get(1).toString();
                        intDemFlowId = flowDetailVect.get(4).toString();
                    }
                }

                installamt = it.getInstallment();
                prinamt = it.getPrinAmt();
                interestamt = it.getIntAmt();
                dueDate = it.getDate();
                if ((prinamt + interestamt) == prinamt) {
                    elAmt = (double) 0;
                } else if ((prinamt + interestamt) == interestamt) {
                    elAmt = (double) 0;
                } else {
                    elAmt = prinamt + interestamt;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(yMMd.parse(dueDate));
                cal.add(cal.DATE, 1);
                String overdueDt = ymdFormat.format(cal.getTime());

                List snoList = em.createNativeQuery("select ifnull(max(SHDL_NUM),0) from cbs_loan_dmd_table").getResultList();
                if (snoList.size() > 0) {
                    Vector secVec = (Vector) snoList.get(0);
                    ShdlNo = Integer.parseInt(secVec.get(0).toString());
                    if (ShdlNo == 0) {
                        ShdlNo = 1;
                    } else {
                        ShdlNo = ShdlNo + 1;
                    }
                }

                List loanDmdTableList = em.createNativeQuery("select * from cbs_loan_dmd_table where acno = '" + accountNo + "' and DMD_DATE = '" + ymdFormat.format(yMMd.parse(date)) + "'").getResultList();
                if (loanDmdTableList.isEmpty()) {
                    if (prinamt != 0) {
                        Integer loanDmdList = em.createNativeQuery("insert into cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,dmd_date,"
                                + "del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,rcre_user_id,"
                                + "rcre_time,ei_amt,ts_cnt,latefee_status_flg,dmd_srl_num) Values"
                                + "('" + accountNo + "'," + ShdlNo + ",'" + prinDemFlowId + "','" + ymdFormat.format(yMMd.parse(date)) + "','N','" + lgFreq + "','" + ymdFormat.format(yMMd.parse(dueDate)) + "','" + overdueDt + "'," + prinamt + ","
                                + "'" + user + "','" + ymdFormat.format(yMMd.parse(date)) + "'," + elAmt + ",0,'N'," + dmdSrl + ")").executeUpdate();
                        if (loanDmdList < 0) {
                            ut.rollback();
                            return "Data is not inserted into cbs_loan_dmd_table";
                        }
                    }
                    if (interestamt != 0) {

                        Integer loanDmdList = em.createNativeQuery("insert into cbs_loan_dmd_table(acno,shdl_num,dmd_flow_id,dmd_date,"
                                + "del_flg,ld_freq_type,dmd_eff_date,dmd_ovdu_date,dmd_amt,rcre_user_id,"
                                + "rcre_time,ei_amt,ts_cnt,latefee_status_flg,dmd_srl_num) values"
                                + "('" + accountNo + "'," + ShdlNo + ",'" + intDemFlowId + "','" + ymdFormat.format(yMMd.parse(date)) + "','N','" + lgFreq + "','" + ymdFormat.format(yMMd.parse(dueDate)) + "','" + overdueDt + "'," + interestamt + ","
                                + "'" + user + "','" + ymdFormat.format(yMMd.parse(date)) + "'," + elAmt + ",0,'N'," + dmdSrl + ")").executeUpdate();
                        if (loanDmdList < 0) {
                            ut.rollback();
                            return "Data is not inserted into cbs_loan_dmd_table";
                        }
                    }
                }
            }
            ut.commit();
            return "True" + "Generated Demand No. is " + dmdSrl;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String recoverDemand(List<VillageWiseEMIDetailPojo> reportList, String recType, String accNo, String chqNo, String chqDt, String offId, String orgnCode, String user, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = null, successMsg = null, flag = "";
            float trSNo = 0;

            List flagList = em.createNativeQuery("select FLAG from cbs_loan_dmd_info where SNO = " + Integer.parseInt(offId) + "").getResultList();
            if (!flagList.isEmpty()) {
                Vector element = (Vector) flagList.get(0);
                flag = element.get(0).toString();
            }

            if (!flag.equalsIgnoreCase("A")) {
                ut.rollback();
                return "Demand Already Recovered";
            }

            if (recType.equalsIgnoreCase("C")) {
                for (int i = 0; i < reportList.size(); i++) {
                    String acno = reportList.get(i).getAcNo().toString();
                    double crAmt = Double.parseDouble(reportList.get(i).getDemandAmt().toString());
                    float recno = ftsPostingRemote.getRecNo();
                    String authBy = user;
                    String dt = date;
                    int tranDesc = 1;
                    String details = "Demand Recovery For " + acno;
                    int iy = 0;
                    String enterBy = user;
                    String orgBrnId = orgnCode;
                    String destBrnId = acno.substring(0, 2);
                    String valueDate = date;
                    double balance = 0.0d;

                    String nature = ftsPostingRemote.getAccountNature(acno);
                    if (acno.equals("") || nature.equals("")) {
                        ut.rollback();
                        return "Acno Blank";
                    }

                    int intPostOnDeposit = 0;
                    intPostOnDeposit = Integer.parseInt(ftsPostingRemote.getCodeByReportName("INT_POST_DEPOSIT"));

                    if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        List list1 = em.createNativeQuery("select acno from reconbalan where acno='" + acno + "'").getResultList();
                        if (list1.size() > 0) {
                            List list2 = em.createNativeQuery("select clearedbalance from reconbalan where acno='" + acno + "'").getResultList();
                            Vector clearbal = (Vector) list2.get(0);
                            balance = Double.parseDouble(clearbal.get(0).toString());
                            Query q1 = em.createNativeQuery("Update reconbalan set clearedbalance = ifnull(clearedbalance,0) + ifnull(" + crAmt + ",0),balance =ifnull(balance,0) +ifnull(" + crAmt + ",0) where acno='" + acno + "'");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return "Problem in updation";
                            }
                        } else {
                            Query q1 = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values('" + acno + "',CURRENT_TIMESTAMP,ifnull(" + crAmt + ",0),ifnull(" + crAmt + ",0))");
                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return "Problem in insertion";
                            } else {
                                balance = crAmt;
                            }
                        }
                    }
                    balance = balance + crAmt;

                    if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                            || nature.equalsIgnoreCase(CbsConstant.SS_AC)) {

                        if (intPostOnDeposit == 0) {
                            Query q1 = em.createNativeQuery("insert into loan_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                    + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,TokenpaidBy, SubTokenNo,trantime,"
                                    + "org_brnid,dest_brnid) values('" + acno + "',0,'" + dt + "','" + valueDate + "',0," + crAmt + ","
                                    + "" + balance + ",0 , '" + details + "'," + iy + ",'',null,'" + enterBy + "','Y',"
                                    + "" + recno + ",3,'" + authBy + "',0,  " + tranDesc + ",0,'', "
                                    + "'0',CURRENT_TIMESTAMP,'" + orgBrnId + "','" + destBrnId + "')");

                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return "Insertion problem in recon";
                            }
                            Query insertCashD = em.createNativeQuery("INSERT INTO recon_cash_d(acno,custname,dt,dramt,cramt,ty,trantype,recno,"
                                    + "trsno,instno,payby,iy,opermode,auth,enterby,authby,tokenpaidby,trandesc,tokenno,subtokenno,trantime,"
                                    + "details,voucherno,intflag,closeflag,tdacno,tran_id,term_id,org_brnid,dest_brnid,valuedt,instdt)"
                                    + " VALUES('" + acno + "','" + ftsPostingRemote.getCustName(acno) + "',date_format(curdate(),'%Y%m%d'),"
                                    + "0," + crAmt + ",0,0," + recno + ",0,'" + chqNo + "',"
                                    + "3,0,'','Y','" + enterBy + "','" + authBy + "','',0,0,'',CURRENT_TIMESTAMP,"
                                    + "'" + details + "',0,'','','',0,'','" + orgBrnId + "','" + destBrnId + "',"
                                    + "date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'))");

                            int num = insertCashD.executeUpdate();
                            if (num <= 0) {
                                ut.rollback();
                                return "Insertion problem in recon_cash_d";
                            }

                            String status = interBranchFacade.fnGetLoanStatus(acno, dt);
                            if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                successMsg = ftsPostingRemote.npaRecoveryUpdation(trSNo, nature, acno, dt, crAmt, orgBrnId, destBrnId, authBy);
                                if (!msg.equalsIgnoreCase("True")) {
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

                            Query q1 = em.createNativeQuery("insert into loan_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                    + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,TokenpaidBy, SubTokenNo,trantime,"
                                    + "org_brnid,dest_brnid) values('" + acno + "',0,'" + dt + "','" + valueDate + "',0," + crAmt + ","
                                    + "" + balance + ",0 , '" + details + "'," + iy + ",'',null,'" + enterBy + "','Y',"
                                    + "" + recno + ",3,'" + authBy + "',0,  " + tranDesc + ",0,'', "
                                    + "'0',CURRENT_TIMESTAMP,'" + orgBrnId + "','" + destBrnId + "')");

                            int no = q1.executeUpdate();
                            if (no <= 0) {
                                ut.rollback();
                                return msg = "Insertion problem in recon";
                            }

                            Query insertCashD = em.createNativeQuery("INSERT INTO recon_cash_d(acno,custname,dt,dramt,cramt,ty,trantype,recno,"
                                    + "trsno,instno,payby,iy,opermode,auth,enterby,authby,tokenpaidby,trandesc,tokenno,subtokenno,trantime,"
                                    + "details,voucherno,intflag,closeflag,tdacno,tran_id,term_id,org_brnid,dest_brnid,valuedt,instdt)"
                                    + " VALUES('" + acno + "','" + ftsPostingRemote.getCustName(acno) + "',date_format(curdate(),'%Y%m%d'),"
                                    + "0," + crAmt + ",0,0," + recno + ",0,'" + chqNo + "',"
                                    + "3,0,'','Y','" + enterBy + "','" + authBy + "','',0,0,'',CURRENT_TIMESTAMP,"
                                    + "'" + details + "',0,'','','',0,'','" + orgBrnId + "','" + destBrnId + "',"
                                    + "date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'))");

                            int num = insertCashD.executeUpdate();
                            if (num <= 0) {
                                ut.rollback();
                                return "Insertion problem in recon_cash_d";
                            }

                            if (intAmt != 0) {
                                float trsInt = ftsPostingRemote.getTrsNo();
                                float recnoInt = ftsPostingRemote.getRecNo();
                                Query insertQuery = em.createNativeQuery("INSERT loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " values ('" + acno + "',1,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + dt + "',"
                                        + intAmt + ",0,8,3,3,'INT.UP TO " + dt + " @" + roi + "%" + "','" + enterBy + "','Y','" + authBy + "',"
                                        + trsInt + "," + recnoInt + ",'" + orgBrnId + "','" + orgBrnId + "')");

                                Integer insertRecon = insertQuery.executeUpdate();
                                if (insertRecon == 0) {
                                    ut.rollback();
                                    return "Value not inserted in Loan_Recon";
                                }

                                Query insertCashD1 = em.createNativeQuery("INSERT INTO recon_cash_d(acno,custname,dt,dramt,cramt,ty,trantype,recno,"
                                        + "trsno,instno,payby,iy,opermode,auth,enterby,authby,tokenpaidby,trandesc,tokenno,subtokenno,trantime,"
                                        + "details,voucherno,intflag,closeflag,tdacno,tran_id,term_id,org_brnid,dest_brnid,valuedt,instdt)"
                                        + " VALUES('" + acno + "','" + ftsPostingRemote.getCustName(acno) + "',date_format(curdate(),'%Y%m%d'),"
                                        + " " + intAmt + " ,0,0,0," + recno + ",0,'" + chqNo + "',"
                                        + "3,0,'','Y','" + enterBy + "','" + authBy + "','',0,0,'',CURRENT_TIMESTAMP,"
                                        + "'" + details + "',0,'','','',0,'','" + orgBrnId + "','" + destBrnId + "',"
                                        + "date_format(curdate(),'%Y%m%d'),date_format(curdate(),'%Y%m%d'))");

                                int num1 = insertCashD1.executeUpdate();
                                if (num1 <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in recon_cash_d";
                                }

                                recnoInt = ftsPostingRemote.getRecNo();
                                Query insertGlReconQuery = em.createNativeQuery("INSERT gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                        + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                        + " VALUES('" + glInt + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + dt + "'," + intAmt + ",0,"
                                        + "8,3,3,'Int Up To " + dt + " of " + acno + "','" + enterBy + "','Y','" + authBy
                                        + "'," + trsInt + "," + recnoInt + ",'" + orgBrnId + "','" + orgBrnId + "')");
                                Integer insertGlRecon = insertGlReconQuery.executeUpdate();
                                if (insertGlRecon == 0) {
                                    ut.rollback();
                                    return "Value doesn't inserted in GL_RECON";
                                }
                            }

                            String nextCalcDt = CbsUtil.dateAdd(ymdFormat.format(ymd_Format.parse(dt)), 1);
                            Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '"
                                    + dt + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acno + "'");
                            Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                            if (updateCaRecon == 0) {
                                ut.rollback();
                                return "Interest not Posted successfully";
                            }
                        }
                    }

                    if (nature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || nature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        if (intPostOnDeposit == 0) {
                            String msgLoan = ftsPostingRemote.loanDisbursementInstallment(acno, crAmt, 0, authBy, dt, recno, tranDesc, details);
                            if (!msgLoan.equalsIgnoreCase("TRUE")) {
                                ut.rollback();
                                return msgLoan;
                            }
                        }
                    }

                    if (ftsPostingRemote.getAccountCode(acno).equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                        Query q1 = em.createNativeQuery("Insert into npa_recon(AcNo,ty,dt,valuedt,Dramt,Cramt,TranType,EnterBy,Auth,"
                                + "recno,Payby,AuthBy,TranDesc,Iy,IntAmt,Status,org_brnid,dest_brnid) values('" + acno + "',1,"
                                + "CURRENT_TIMESTAMP," + valueDate + ",0," + crAmt + ",0,'" + enterBy + "','Y'," + recno + ",3,'" + authBy + "'," + tranDesc + ","
                                + "" + iy + ",0,''," + orgBrnId + "," + destBrnId + ")");
                        q1.executeUpdate();
                    }

                    ftsPostingRemote.lastTxnDateUpdation(acno);
                }

                int updateDmdInfo = em.createNativeQuery("update cbs_loan_dmd_info set FLAG = 'R',RECOVERYDT = curdate() where SNO = " + offId).executeUpdate();
                if (updateDmdInfo <= 0) {
                    throw new ApplicationException("Problem in updation");
                }
            } else {
                trSNo = ftsPostingRemote.getTrsNo();
                if (accNo.substring(0, 2).equalsIgnoreCase(orgnCode)) {
                    double drAmt = 0;
                    /*When the Orgn Branch Code & Destination Branh both are same*/
                    int intPostOnDeposit = 0;
                    intPostOnDeposit = Integer.parseInt(ftsPostingRemote.getCodeByReportName("INT_POST_DEPOSIT"));
                    for (int i = 0; i < reportList.size(); i++) {
                        String acNo = reportList.get(i).getAcNo().toString();
                        double crAmt = Double.parseDouble(reportList.get(i).getDemandAmt());
                        float recNo = ftsPostingRemote.getRecNo();
                        double balance = 0.0d;
                        String details = "Demand Recovery For " + acNo;
                        drAmt = drAmt + crAmt;
                        int iy = 1;
                        String destBrnid = acNo.substring(0, 2);
                        String acNature = ftsPostingRemote.getAccountNature(acNo);

                        int ty = 0;

                        if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            List list1 = em.createNativeQuery("select acno from reconbalan where acno='" + acNo + "'").getResultList();
                            if (list1.size() > 0) {
                                List list2 = em.createNativeQuery("select clearedbalance from reconbalan where acno='" + acNo + "'").getResultList();
                                Vector clearbal = (Vector) list2.get(0);
                                balance = Double.parseDouble(clearbal.get(0).toString());
                                Query q1 = em.createNativeQuery("Update reconbalan set clearedbalance = ifnull(clearedbalance,0) + ifnull(" + crAmt + ",0),balance =ifnull(balance,0) +ifnull(" + crAmt + ",0) where acno='" + acNo + "'");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return "Problem in updation";
                                }
                            } else {
                                Query q1 = em.createNativeQuery("insert into reconbalan(acno,dt,balance,clearedbalance) values('" + acNo + "',CURRENT_TIMESTAMP,ifnull(" + crAmt + ",0),ifnull(" + crAmt + ",0))");
                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return "Problem in insertion";
                                } else {
                                    balance = crAmt;
                                }
                            }
                        }
                        balance = balance + crAmt;

                        if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {

                            if (intPostOnDeposit == 0) {
                                Query q1 = em.createNativeQuery("insert into loan_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,TokenpaidBy, SubTokenNo,trantime,"
                                        + "org_brnid,dest_brnid) values('" + acNo + "','" + ty + "','" + date + "','" + date + "',0," + crAmt + ","
                                        + "" + balance + ",2, '" + details + "'," + iy + ",'',null,'" + user + "','Y',"
                                        + "" + recNo + ",3,'" + user + "','" + trSNo + "',0,0,'', "
                                        + "'0',CURRENT_TIMESTAMP,'" + orgnCode + "','" + destBrnid + "')");

                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in recon";
                                }
                                String status = interBranchFacade.fnGetLoanStatus(acNo, date);
                                if (status.equals("SUB") || status.equals("DOU") || status.equals("LOS")) {
                                    successMsg = ftsPostingRemote.npaRecoveryUpdation(trSNo, acNature, acNo, date, crAmt, orgnCode, destBrnid, user);
                                    if (!successMsg.equalsIgnoreCase("True")) {
                                        ut.rollback();
                                        throw new ApplicationException(msg);
                                    }
                                }
                            } else {
                                LoanIntCalcList it = loanFacade.txnLoanIntCalc(date, acNo, orgnCode);
                                double intAmt = it.getTotalInt() * -1;
                                double roi = it.getRoi();

                                String glInt = loanFacade.getGlHeads(acNo.substring(2, 4));
                                glInt = orgnCode + glInt + "01";

                                Query q1 = em.createNativeQuery("insert into loan_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                        + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,TokenpaidBy, SubTokenNo,trantime,"
                                        + "org_brnid,dest_brnid) values('" + acNo + "','" + ty + "','" + date + "','" + date + "',0," + crAmt + ","
                                        + "" + balance + ",2,'" + details + "'," + iy + ",'',null,'" + user + "','Y',"
                                        + "" + recNo + ",3,'" + user + "','" + trSNo + "',0,0,'', "
                                        + "'0',CURRENT_TIMESTAMP,'" + orgnCode + "','" + destBrnid + "')");

                                int no = q1.executeUpdate();
                                if (no <= 0) {
                                    ut.rollback();
                                    return "Insertion problem in recon";
                                }

                                if (intAmt != 0) {
                                    float recnoInt = ftsPostingRemote.getRecNo();
                                    Query insertQuery = em.createNativeQuery("INSERT loan_recon (ACNO,TY,DT,VALUEDT,DRAMT,cramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " values ('" + acNo + "','" + ty + "',DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + date + "',"
                                            + intAmt + ",0,8,3,3,'INT.UP TO " + date + " @" + roi + "%" + "','" + user + "','Y','" + user + "',"
                                            + trSNo + "," + recnoInt + ",'" + orgnCode + "','" + destBrnid + "')");

                                    Integer insertRecon = insertQuery.executeUpdate();
                                    if (insertRecon == 0) {
                                        ut.rollback();
                                        return "Value not inserted in Loan_Recon";
                                    }

                                    recnoInt = ftsPostingRemote.getRecNo();
                                    Query insertGlReconQuery = em.createNativeQuery("INSERT gl_recon(ACNO,TY,DT,VALUEDT,CRAMT,dramt,TRANTYPE,"
                                            + "trandesc,payby,DETAILS,ENTERBY,AUTH,authby,trsno,RECNO,org_brnid, dest_brnid) "
                                            + " VALUES('" + glInt + "',0,DATE_FORMAT(CURDATE(),'%Y%m%d'),'" + date + "'," + intAmt + ",0,"
                                            + "8,3,3,'Int Up To " + date + " of " + acNo + "','" + user + "','Y','" + user
                                            + "'," + trSNo + "," + recnoInt + ",'" + orgnCode + "','" + destBrnid + "')");
                                    Integer insertGlRecon = insertGlReconQuery.executeUpdate();
                                    if (insertGlRecon == 0) {
                                        ut.rollback();
                                        return "Value doesn't inserted in GL_RECON";
                                    }
                                }

                                String nextCalcDt = CbsUtil.dateAdd(ymdFormat.format(ymd_Format.parse(date)), 1);
                                Query updateCaReconQuery = em.createNativeQuery("UPDATE cbs_loan_acc_mast_sec SET INT_CALC_UPTO_DT = '"
                                        + date + "', NEXT_INT_CALC_DT = '" + nextCalcDt + "' WHERE Acno = '" + acNo + "'");
                                Integer updateCaRecon = updateCaReconQuery.executeUpdate();
                                if (updateCaRecon == 0) {
                                    ut.rollback();
                                    return "Interest not Posted successfully";
                                }
                            }
                        }

                        recNo = ftsPostingRemote.getRecNo();
                        if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                                || acNature.equalsIgnoreCase(CbsConstant.SS_AC)) {
                            if (intPostOnDeposit == 0) {
                                String msgLoan = ftsPostingRemote.loanDisbursementInstallment(acNo, crAmt, 0, user, date, recNo, 1, details);
                                if (!msgLoan.equalsIgnoreCase("TRUE")) {
                                    ut.rollback();
                                    return msg = msgLoan;
                                }
                            }
                        }

                        recNo = ftsPostingRemote.getRecNo();
                        if (ftsPostingRemote.getAccountCode(acNo).equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                            Query q1 = em.createNativeQuery("Insert into npa_recon(AcNo,ty,dt,valuedt,Dramt,Cramt,TranType,EnterBy,Auth,"
                                    + "recno,Payby,AuthBy,TranDesc,Iy,IntAmt,Status,org_brnid,dest_brnid) values('" + acNo + "','" + ty + "',"
                                    + "CURRENT_TIMESTAMP," + date + ",0," + crAmt + ",2,'" + user + "','Y'," + recNo + ",3,'" + user + "',1,"
                                    + "" + iy + ",0,''," + orgnCode + "," + destBrnid + ")");
                            q1.executeUpdate();
                        }

                        ftsPostingRemote.lastTxnDateUpdation(acNo);
                    }

                    String details = "Demand Recovery For " + offId;
                    float recNo = ftsPostingRemote.getRecNo();
                    String acNature = ftsPostingRemote.getAccountNature(accNo);
                    if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        Integer varinsertReconList = em.createNativeQuery("insert into recon( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                                + "org_brnid,dest_brnid,tran_id,term_id) values('" + accNo + "',1,'" + date + "','" + date + "'," + drAmt + ",0, 0,2,"
                                + "'" + details + "',1,'" + chqNo + "','" + chqDt + "','" + user + "','Y'," + recNo + ",3,'" + user + "'," + trSNo + ",0,"
                                + "0,'','',CURRENT_TIMESTAMP,'" + orgnCode + "','" + accNo.substring(0, 2) + "',0,"
                                + "'0' )").executeUpdate();
                        if (varinsertReconList <= 0) {
                            ut.rollback();
                            return "Insertion Problem in Recons for A/c No :- " + accNo;
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        Integer varinsertCaReconList = em.createNativeQuery("insert into ca_recon(acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                                + "org_brnid,dest_brnid,tran_id,term_id) values('" + accNo + "',1,'" + date + "','" + date + "'," + drAmt + ",0, 0,2,"
                                + "'" + details + "',1,'" + chqNo + "','" + chqDt + "','" + user + "','Y'," + recNo + ",3,'" + user + "'," + trSNo + ",0,"
                                + "0,'','',CURRENT_TIMESTAMP,'" + orgnCode + "','" + accNo.substring(0, 2) + "',0,"
                                + "'0' )").executeUpdate();
                        if (varinsertCaReconList <= 0) {
                            ut.rollback();
                            return "Insertion Problem in Recons for A/c No :- " + accNo;
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        Integer varinsertGlReconList = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                + " details, iy, instno, instDt, EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                                + "org_brnid,dest_brnid,tran_id,term_id,AdviceNo,AdviceBrnCode) values('" + accNo + "',1,'" + date + "','" + date + "'," + drAmt + ",0, 0,2,"
                                + "'" + details + "',1,'',null,'" + user + "','Y'," + recNo + ",3,'" + user + "'," + trSNo + ",0,"
                                + "0,'','',CURRENT_TIMESTAMP,'" + orgnCode + "','" + accNo.substring(0, 2) + "',0,"
                                + "'','','' )").executeUpdate();
                        if (varinsertGlReconList <= 0) {
                            return "Insertion Problem in Recons for A/c No :- " + accNo;
                        }
                    }

                    if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        msg = ftsPostingRemote.updateCheque(accNo, 1, 1, Float.parseFloat(chqNo), user);
                        if (!(msg.equalsIgnoreCase("TRUE"))) {
                            ut.rollback();
                            return msg;
                        }
                    }

                    msg = ftsPostingRemote.updateBalance(acNature, accNo, 0, drAmt, "Y", "Y");
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        ut.rollback();
                        return msg;
                    }
                    ftsPostingRemote.lastTxnDateUpdation(accNo);
                } else {
                    double drAmt = 0;
                    /*When the Orgn Branch Code & Destination Branh both are Not same*/
                    for (int i = 0; i < reportList.size(); i++) {
                        String acNo = reportList.get(i).getAcNo().toString();
                        double crAmt = Double.parseDouble(reportList.get(i).getDemandAmt());
                        String enterBy = user;
                        String details = "Demand Recovery For " + acNo;
                        float recNo = ftsPostingRemote.getRecNo();
                        drAmt = drAmt + crAmt;
                        int ty = 0;

                        msg = interBranchFacade.cbsPostingCx(acNo, ty, date, crAmt, 0f, 2, details, 0f, "", "", null,
                                3, 0f, recNo, 1, orgnCode, acNo.substring(0, 2), enterBy, enterBy, trSNo, "", "");
                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                            msg = "TRUE";
                        } else {
                            ut.rollback();
                            return msg;
                        }

                        ftsPostingRemote.lastTxnDateUpdation(acNo);
                    }

                    String instrDt = null;
                    if ((chqDt == null) || (chqDt.toString().equals(""))) {
                        instrDt = null;
                    } else {
                        instrDt = chqDt;
                    }

                    float recNo = ftsPostingRemote.getRecNo();
                    String details = "Demand Recovery For " + offId;
                    msg = interBranchFacade.cbsPostingSx(accNo, 1, date, drAmt, 0f, 2, details, 0f, "", chqNo, instrDt,
                            3, 0f, recNo, 3, orgnCode, accNo.substring(0, 2), user, user, trSNo, "", "");
                    if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
                        return msg;
                    }

                    msg = ftsPostingRemote.updateCheque(accNo, 1, 1, Float.parseFloat(chqNo), user);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        ut.rollback();
                        return msg;
                    }
                    String acNature = ftsPostingRemote.getAccountNature(accNo);
                    msg = ftsPostingRemote.updateBalance(acNature, accNo, 0, drAmt, "Y", "Y");
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        ut.rollback();
                        return msg;
                    }
                    ftsPostingRemote.lastTxnDateUpdation(accNo);
                }

                int updateDmdInfo = em.createNativeQuery("update cbs_loan_dmd_info set FLAG = 'R',RECOVERYDT = curdate() where SNO = " + offId).executeUpdate();
                if (updateDmdInfo <= 0) {
                    throw new ApplicationException("Problem in updation");
                }
            }
            ut.commit();
            if (recType.equalsIgnoreCase("C")) {
                return "True";
            } else {
                return "True" + trSNo;
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
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String saveDdPaymentCancelDetails(int fyear, double seqNo, String instNo, String cuName, String date, String payableAt,
            String fvrOf, double amt, String instBrnId, String brName, String status, String drAcNo, String crAcNo, String user, String flag,
            String orgBrnid) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List acNoList = em.createNativeQuery("select * from bill_dd_pymtauth where instNo = '" + instNo + "'").getResultList();
            if (!acNoList.isEmpty()) {
                throw new ApplicationException("This Instrument No. Already Processed");
            }

            String query = "INSERT INTO bill_dd_pymtauth(FYEAR,SEQNO,INSTNO,CUSTNAME,DT,PAYABLEAT,INFAVOUROF,AMOUNT,inst_brnid,orgBrnName,STATUS,DrAcNo,"
                    + "CrAcNo,ENTERBY,Flag,AUTH, AUTHBY,TRANTIME,org_brnid,dest_brnid) "
                    + "VALUES(" + fyear + "," + seqNo + ",'" + instNo + "','" + cuName + "','" + ymd_Format.format(yMMd.parse(date)) + "','" + payableAt + "','"
                    + fvrOf + "'," + amt + ",'" + instBrnId + "','" + brName + "','" + status + "','" + drAcNo + "','" + crAcNo + "','" + user
                    + "','" + flag + "','N','',now(),'" + orgBrnid + "', '" + crAcNo.substring(0, 2) + "')";
            Query insertQuery = em.createNativeQuery(query);
            int varQ1 = insertQuery.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }

            ut.commit();
            return "Data successfully saved";
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

    public String verifyDdPaymentCancelDetails(String instNo, double seqNo, int fyear, String trantype, String orgnBrnCode, String date, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            double Amt = 0.0;
            List getDataList = em.createNativeQuery("select CUSTNAME,date_format(DT,'%d/%m/%Y'),PAYABLEAT,INFAVOUROF,AMOUNT,inst_brnid,STATUS,"
                    + "DrAcNo,CrAcNo,ENTERBY,org_brnid,dest_brnid from bill_dd_pymtauth where fyear = " + fyear + " "
                    + " and seqno = " + seqNo + " and instNo = '" + instNo + "'").getResultList();
            if (getDataList.size() <= 0) {
                throw new ApplicationException("Please check instNo No, you have passed.");
            }
            String msg = "";
            String stat = "";
            String issDt = "";
            float dRecNo = ftsPostingRemote.getRecNo();
            for (int i = 0; i < getDataList.size(); i++) {
                Vector getDateVect = (Vector) getDataList.get(i);
                issDt = getDateVect.get(1).toString();
                String payAt = getDateVect.get(2).toString();
                Amt = Double.parseDouble(getDateVect.get(4).toString());
                stat = getDateVect.get(6).toString();
                String drAc = getDateVect.get(7).toString();
                String crAc = getDateVect.get(8).toString();
                String enterBy = getDateVect.get(9).toString();
                String orgId = getDateVect.get(10).toString();
                String destId = getDateVect.get(11).toString();

                List userAuthLimit = em.createNativeQuery("select coalesce(trandebit,0.0) from securityinfo where "
                        + "userid='" + user + "' and brncode='" + orgnBrnCode + "'").getResultList();
                if (userAuthLimit.size() <= 0) {
                    throw new ApplicationException("UserId does not exists ::" + user);
                }

                Vector element = (Vector) userAuthLimit.get(0);
                double userLimit = Double.parseDouble(element.get(0).toString());
                if (userLimit == 0.0 || Amt > userLimit) {
                    throw new ApplicationException("Your passing limit is less than amount. You can not "
                            + "authorize this transaction");
                }
                Float trsno = ftsPostingRemote.getTrsNo();
                if (orgId.equalsIgnoreCase(destId)) {
                    String acNat = "";
                    acNat = ftsPostingRemote.getAccountNature(drAc);

                    msg = ftsPostingRemote.insertRecons(acNat, drAc, 1, Amt, ymd_Format.format(yMMd.parse(date)), ymd_Format.format(yMMd.parse(date)), 2,
                            "DD " + stat + " For Inst No. " + instNo + "", enterBy, trsno, "", dRecNo, "Y",
                            user, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgId, destId, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = ftsPostingRemote.updateBalance(acNat, drAc, 0, Amt, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    acNat = ftsPostingRemote.getAccountNature(crAc);

                    msg = ftsPostingRemote.insertRecons(acNat, crAc, 0, Amt, ymd_Format.format(yMMd.parse(date)), ymd_Format.format(yMMd.parse(date)), 2,
                            "DD " + stat + " For Inst No. " + instNo + "", enterBy, trsno, "", ftsPostingRemote.getRecNo(), "Y",
                            user, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgId, destId, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = ftsPostingRemote.updateBalance(acNat, crAc, Amt, 0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                } else {
                    String acNat = ftsPostingRemote.getAccountNature(drAc);
                    msg = interBranchFacade.cbsPostingCx(drAc, 1, ymd_Format.format(yMMd.parse(date)), Amt, 0f, 2, "DD " + stat + " For Inst No. " + instNo + "", 0f, "0", "", null, 3, null, dRecNo, 0, destId, orgId, enterBy, user, trsno, "", "");
                    if (msg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                        msg = interBranchFacade.cbsPostingSx(crAc, 0, ymd_Format.format(yMMd.parse(date)), Amt, 0f, 2, "DD " + stat + " For Inst No. " + instNo + "", 0f, "0", "", null, 3, null, ftsPostingRemote.getRecNo(), 0, destId, orgId, enterBy, user, trsno, "", "");
                        if (msg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            msg = ftsPostingRemote.updateBalance(acNat, drAc, 0, Amt, "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                        } else {
                            throw new ApplicationException(msg);
                        }
                    } else {
                        throw new ApplicationException(msg);
                    }
                }
            }

            String query = ("Update bill_dd_pymtauth set auth ='Y', authby = '" + user + "' where instno = '" + instNo + "' and fyear = " + fyear + " and seqno = " + seqNo + "");
            Query insertQuery = em.createNativeQuery(query);
            int varQ1 = insertQuery.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in Data Updation in bill_dd_pymtauth");
            }

            Query q3 = em.createNativeQuery("Update bill_dd Set dt = '" + ymd_Format.format(yMMd.parse(date)) + "',Status = '" + stat + "',recNo = " + dRecNo + " ,ty=1,"
                    + "LastUpdateBy = '" + user + "' where seqno = " + seqNo + " "
                    + "and InstNo = '" + instNo + "' and origindt='" + ymd_Format.format(yMMd.parse(issDt)) + "'");
            int rs = q3.executeUpdate();
            if (rs > 0) {
                msg = "TRUE";
            }

            ut.commit();
            return "Data Verified Successfully";
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

    public String deleteDdPaymentCancelDetails(int fyear, double seqNo, String instNo, String user) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            List acNoList = em.createNativeQuery("select * from bill_dd_pymtauth where instNo = '" + instNo + "' and flag = 'D'").getResultList();
            if (!acNoList.isEmpty()) {
                throw new ApplicationException("This Instrument No. Already Deleted");
            }

            String query = ("Update bill_dd_pymtauth set flag = 'D', auth ='Y', authby = '" + user + "' where instno = '" + instNo + "' and fyear = " + fyear + " and seqno = " + seqNo + "");
            Query insertQuery = em.createNativeQuery(query);
            int varQ1 = insertQuery.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in Data Deletion");
            }

            ut.commit();
            return "Data successfully Deleted";
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

    public List<TdReceiptIntDetailPojo> getOtherActDetails(String acNo) throws ApplicationException {
        try {
            List acNoList = em.createNativeQuery("select ci.custid, ac.acno,cb.description from customerid ci,accountmaster ac,codebook cb "
                    + "where ac.acno=ci.acno and cb.groupcode=3 and custid in (select custid from customerid where acno='" + acNo + "') and "
                    + "ac.accstatus = cb.code order by ac.accstatus").getResultList();
            return acNoList;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getNpaBalCheck(String acNo, String date) throws ApplicationException {
        double npaBal = 0d;
        List balList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from npa_recon where acno = '" + acNo + "' and dt <= '" + date + "'").getResultList();
        Vector balVector = (Vector) balList.get(0);
        npaBal = Double.parseDouble(balVector.get(0).toString());
        return npaBal;
    }

    public String saveTxnDetails(List<TxnDetailBean> txnList, String sundryTable, List<DDSDenominationGrid> denominationTable, String acNature, 
            String custId, String panNo, String occupation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String finalMsg = "";
            String msg = "";
            int listSize = txnList.size();
            for (TxnDetailBean obj : txnList) {
                String instDt = "";
                if (obj.getInstDt() != null) {
                    instDt = obj.getInstDt();
                }
                int postFlag = getPostFlag(obj.getAcNo());
                if (contra(postFlag, obj.getTy()) == true) {
                    Query update = em.createNativeQuery("Update " + sundryTable + " SET status='PAID',dt='" + obj.getDt() + "',lastupdateby='"
                            + obj.getEnterBy() + "' ,RECNO=" + obj.getRecNo() + " WHERE acno='" + obj.getAcNo() + "' and seqno=" + obj.getSqNo()
                            + " and fyear=" + obj.getFinYear() + " and status='ISSUED'");
                    int updateResult = update.executeUpdate();
                    if (updateResult <= 0) {
                        throw new ApplicationException("Problem in data updation for Sundry or Suspense");
                    }
                }
                BigDecimal tdsToBeDeducted = new BigDecimal(0);
                if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) && !occupation.equals("50") && Integer.parseInt(obj.getTranType()) == 0 && Integer.parseInt(obj.getTy()) == 1) {
                    tdsToBeDeducted = interBranchFacade.getTdsToBeDeductedForCustomer(panNo, custId, "20190901", obj.getDt(),new BigDecimal(obj.getAmount()),"N");
                    if (tdsToBeDeducted.compareTo(new BigDecimal(0)) > 0) {
                        String msg1 = ftsPostingRemote.chkBal(obj.getAcNo(), Integer.parseInt(obj.getTy()), Integer.parseInt(obj.getTranDesc()), acNature);
                        if (msg1.equalsIgnoreCase("CHECKBALANCE")) {
                            msg = ftsPostingRemote.checkBalance(obj.getAcNo(), obj.getAmount() + tdsToBeDeducted.doubleValue(), obj.getEnterBy());
                            if (!(msg.equalsIgnoreCase("TRUE"))) {
                                throw new ApplicationException(msg + " for A/C No. " + obj.getAcNo());
                            }
                        }
                    }
                }
                msg = ftsPostingRemote.ftsPosting43CBS(obj.getAcNo(), Integer.parseInt(obj.getTranType()), Integer.parseInt(obj.getTy()), obj.getAmount(),
                        obj.getDt(), obj.getValueDate(), obj.getEnterBy(), obj.getOrgnBrId(), obj.getDestBrId(), Integer.parseInt(obj.getTranDesc()),
                        obj.getDetails(), obj.getTrsNo(), obj.getRecNo(), obj.getTranId(), obj.getTermId(), obj.getAuth(), obj.getAuthBy(),
                        obj.getSubTokenNo(), Integer.parseInt(obj.getPayBy()), obj.getInstNo(), instDt, obj.getTdAcNo(), tdsToBeDeducted.floatValue(),
                        obj.getIntFlag(), obj.getCloseFlag(), obj.getScreenFlag(), obj.getTxnStatus(), obj.getTokenNoDr(), obj.getCxsxFlag(), "", "", "");

                if (!msg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    throw new ApplicationException(msg);
                }

                if (!(obj.getAlertSubCode() == null || obj.getAlertSubCode().equalsIgnoreCase(""))) {
                    String str = ftsPostingRemote.strDetailInsertion(obj.getAlertSubCode(), obj.getAcNo(), obj.getTrsNo(), obj.getRecNo(), obj.getOrgnBrId(),
                            obj.getDestBrId(), obj.getEnterBy());
                    if (!str.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Insertion Problem in cbs_str_details !");
                    }
                }

                // Code Added For Denomination 
                if ((!denominationTable.isEmpty()) && (obj.getTranType().equalsIgnoreCase("0"))) {
                    if (listSize == 1 || (listSize > 1 && obj.getDetails().contains("~`"))) {
                        for (DDSDenominationGrid olObj : denominationTable) {
                            double denVal = olObj.getDenoValue();
                            int denNoCnt = olObj.getDenoNo();
                            String oNFlg = olObj.getFlag();
                            String denoMsg = interBranchFacade.insertDenominationDetail(obj.getAcNo(), obj.getRecNo(), obj.getDt(), new BigDecimal(denVal),
                                    denNoCnt, Integer.parseInt(olObj.getTy()), obj.getOrgnBrId(), obj.getEnterBy(), oNFlg);
                            if (!denoMsg.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Insertion Problem in denomination_details !");
                            } else {
                                int cnVal = 0;
                                if (olObj.getTy().equalsIgnoreCase("0") || olObj.getTy().equalsIgnoreCase("4")) {
                                    cnVal = denNoCnt;
                                } else if (olObj.getTy().equalsIgnoreCase("1") || olObj.getTy().equalsIgnoreCase("3")) {
                                    cnVal = denNoCnt * -1;
                                }

                                String denUpdateMsg = interBranchFacade.updateOpeningDenomination(obj.getOrgnBrId(), new BigDecimal(denVal), cnVal, obj.getDt(), oNFlg);
                                if (!denUpdateMsg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Insertion Problem in denomination_details !");
                                }
                            }
                        }
                    }

                    /*                    
                     String sbnParam = ftsPostingRemote.getCodeFromCbsParameterInfo("SBN_DEPOSIT_PARAM");
                     if (sbnParam.equalsIgnoreCase("1")) {
                     String acNature = ftsPostingRemote.getAccountNature(obj.getAcNo());
                     if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                     String sPassVal = ftsPostingRemote.getCircularPassInfo(obj.getAcNo(), obj.getDt(), obj.getAmount());
                     if (sPassVal.equalsIgnoreCase("true")) {
                     Query updateSbnDtl = em.createNativeQuery("update sbn_pass_details set recno='" + obj.getRecNo() + "' where acno ='" + obj.getAcNo() + "' "
                     + "and dt ='" + obj.getDt() + "' and recno = 0.0 and amount = " +obj.getAmount() + "");
                     int var = updateSbnDtl.executeUpdate();
                     if (var <= 0) {
                     ut.rollback();
                     return "Data not updated in sbn_pass_details!!!";
                     }
                     }
                     }
                     } */
                }

                if (finalMsg.equals("")) {
                    finalMsg = "Generate Voucher No :-" + (long) Float.parseFloat(msg.substring(4));
                } else {
                    finalMsg = finalMsg + "," + (long) Float.parseFloat(msg.substring(4));
                }
            }
            ut.commit();
            if (txnList.size() == 1) {
                return msg;
            } else {
                return finalMsg;
            }
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

    public int getPostFlag(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select ifnull(postflag,0) from gltable where acno='" + accNo + "'").getResultList();
            if (list.isEmpty()) {
                return 0;
            } else {
                Vector vecGLTable = (Vector) list.get(0);
                return Integer.parseInt(vecGLTable.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public int getMsgFlag(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select ifnull(MsgFlag,0) from gltable where acno='" + accNo + "'").getResultList();
            if (list.isEmpty()) {
                return 0;
            } else {
                Vector vecGLTable = (Vector) list.get(0);
                return Integer.parseInt(vecGLTable.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getCashTxnDetals(String acno, String orgnBrCode) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            ftsPostingRemote.getNewAccountNumber(acno);
            List list = em.createNativeQuery("select acno,custname,date_format(dt,'%d/%m/%Y'),dramt,cramt,ty,recno,enterby,authby,Details "
                    + "from recon_cash_d where acno='" + acno + "' and dt='" + date + "' and auth='Y' and  org_brnid='" + orgnBrCode
                    + "' order by recno").getResultList();
            return list;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getDenominationDetails(String acno, float recno, String dt, String orgnBrCode, int ty) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(dmy.parse(dt));
            List list = em.createNativeQuery("select denomination,denomination_value,new_old_flag from denomination_detail where acno='" + acno + "' "
                    + "and brncode='" + orgnBrCode + "' and dt='" + date + "' and recno=" + recno + " and ty=" + ty).getResultList();
            return list;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String updateDenominationDetails(List<DDSDenominationGrid> denoList, String acno, float recno, String dt, String orgnBrCode, int ty, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            ut.begin();
            String date = sdf.format(dmy.parse(dt));
            int rs = em.createNativeQuery("insert into denomination_detail_his(acno,recno,dt,denomination,denomination_value,ty,brncode,enterby,"
                    + "entrydate,new_old_flag,updatedby,updatedtime) select acno,recno,dt,denomination,denomination_value,ty,brncode,enterby,"
                    + "entrydate,new_old_flag,'" + userName + "',now() from denomination_detail where dt='" + date + "' and recno=" + recno).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data insertion in history table");
            }

            String msg = interBranchFacade.deleteDenomination(recno, date);
            if (!msg.equals("true")) {
                throw new ApplicationException(msg);
            }
            int denoNo = 0;
            for (DDSDenominationGrid ddg : denoList) {

                denoNo = ddg.getDenoNo();
                if (ty == 1) {
                    denoNo = -ddg.getDenoNo();
                }
                msg = interBranchFacade.insertDenominationDetail(acno, recno, date, new BigDecimal(ddg.getDenoValue()), ddg.getDenoNo(), ty,
                        orgnBrCode, userName, ddg.getFlag());
                if (!msg.equals("true")) {
                    throw new ApplicationException(msg);
                }

                msg = interBranchFacade.updateOpeningDenomination(orgnBrCode, new BigDecimal(ddg.getDenoValue()), denoNo, date, ddg.getFlag());
                if (!msg.equals("true")) {
                    throw new ApplicationException(msg);
                }
            }
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

    public boolean isWSAllow(String acno) throws ApplicationException {
        try {
            int remoteTxnVch = ftsPostingRemote.getCodeForReportName("REMOTE_TXN_VOUCHER");
            if (remoteTxnVch == 0) {
                return false;
            }

            List list = em.createNativeQuery("select acno from acedithistory where acno='" + acno + "' and (authby is null or authby ='')").getResultList();

            if (!list.isEmpty()) {
                throw new ApplicationException("Account Edit Authorization is pending for this Account");
            }

            list = em.createNativeQuery("select acno from accountmaster where acno='" + acno + "' and orgncode=16").getResultList();
            if (list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAccountDetails(String accNo, String acNature) throws ApplicationException {
        try {
            List list;
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                list = em.createNativeQuery("Select ta.acno,custname,jtname1,jtname2,jtname3,jtname4,cb.description,DATE_FORMAT(openingdt,'%Y%m%d') "
                        + "'openingdt',accstatus,ci.custid from td_accountmaster ta,customerid ci,codebook cb where ci.acno=ta.acno and ta.AcNo='" + accNo
                        + "' and groupcode=4 and cb.code=opermode").getResultList();
            } else {
                list = em.createNativeQuery("Select ta.acno,custname,jtname1,jtname2,jtname3,jtname4,cb.description,DATE_FORMAT(openingdt,'%Y%m%d') "
                        + "'openingdt',accstatus,ci.custid from accountmaster ta,customerid ci,codebook cb where ci.acno=ta.acno and ta.AcNo='" + accNo
                        + "' and groupcode=4 and cb.code=opermode").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("Account Details does not exist.");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnauthDetails(String orgBrCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select txnId,sbn.acno,ac.custname,amount,details,enterBy,org_brnid,dest_brnid from "
                    + " sbn_pass_details sbn, accountmaster ac where ac.acno=sbn.acno and auth='N' and org_brnid='" + orgBrCode + "' "
                    + " and dt='" + ymdFormat.format(new Date()) + "' "
                    + " union all "
                    + " Select txnId,sbn.acno,ac.custname,amount,details,enterBy,org_brnid,dest_brnid from sbn_pass_details sbn,"
                    + "td_accountmaster ac where ac.acno=sbn.acno and auth='N' and org_brnid='" + orgBrCode + "' and dt='"
                    + ymdFormat.format(new Date()) + "'").getResultList();

            if (list.isEmpty()) {
                throw new ApplicationException("Unauthorized details does not exist");
            }

            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String processSBNCashDepostDecl(String func, String acno, String details, String userName, double amt, String orgbrCode, String authBy, long txnId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (func.equals("A")) {
                int code = ftsPostingRemote.getCodeForReportName("MULTIPLE-DEPOSIT");
                if (code == 0) {
                    List list = em.createNativeQuery(" Select acno from sbn_pass_details where acno= '" + acno + "'").getResultList();
                    if (!list.isEmpty()) {
                        throw new ApplicationException("You can deposit the cash only one time beyond the limit");
                    }
                }
                int n = em.createNativeQuery("INSERT INTO sbn_pass_details (acno, dt, amount, enterby, authby, auth, trantime, details, recno, "
                        + "org_brnid, dest_brnid) VALUES ('" + acno + "', '" + ymdFormat.format(new Date()) + "', " + amt + ", '" + userName + "', '', 'N', "
                        + "now(), '" + details + "', 0, '" + orgbrCode + "', '" + acno.substring(0, 2) + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }
            } else if (func.equals("D")) {
                List list = em.createNativeQuery(" Select acno from sbn_pass_details where txnid= " + txnId).getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Transaction already deleted");
                }
                int n = em.createNativeQuery("delete from sbn_pass_details where txnid= " + txnId).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in data insertion.");
                }
            } else if (func.equals("V")) {
                if (userName.equalsIgnoreCase(authBy)) {
                    throw new ApplicationException("You can not verify your own transaction");
                }
                List list = em.createNativeQuery(" Select levelid from securityinfo where userid='" + authBy + "' and brncode='" + orgbrCode + "'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("User does not exist");
                }
                Vector vect = (Vector) list.get(0);
                int levelId = Integer.parseInt(vect.get(0).toString());
                if (levelId != 1 && levelId != 2) {
                    throw new ApplicationException("Verification does not allow for this user");
                }
                int n = em.createNativeQuery("update sbn_pass_details set auth='Y', authBy = '" + userName + "' where txnid= " + txnId).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in data updation.");
                }
            }
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
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public List<SuspiciousVerifyPojo> getStrVerifyDetails(String dt, String brnCode) throws ApplicationException {
        List<SuspiciousVerifyPojo> suspiciousVerPojoList = new ArrayList<SuspiciousVerifyPojo>();
        try {
            List gridList = new ArrayList();
            gridList = em.createNativeQuery("select c.acno,a.custname,c.batch_no,c.recno,date_format(c.dt,'%d/%m/%Y'),"
                    + "c.alert_code,c.enter_by,c.txnId from cbs_str_detail c, accountmaster a where c.acno = a.acno and c.flag = 'STR'"
                    + " and c.auth_status = 'N' and dt ='" + dt + "' and destbrncode ='" + brnCode + "'").getResultList();
            if (gridList.size() > 0) {
                for (int j = 0; j < gridList.size(); j++) {
                    Vector record = (Vector) gridList.get(j);
                    SuspiciousVerifyPojo pDtl = new SuspiciousVerifyPojo();
                    pDtl.setAcNo(record.get(0).toString());
                    pDtl.setCustName(record.get(1).toString());
                    pDtl.setTrsNo(Float.parseFloat(record.get(2).toString()));
                    pDtl.setRecNo(Float.parseFloat(record.get(3).toString()));
                    pDtl.setDt(record.get(4).toString());
                    pDtl.setAlertCode(record.get(5).toString());
                    pDtl.setEnterBy(record.get(6).toString());
                    pDtl.setsNo(Integer.parseInt(record.get(7).toString()));
                    suspiciousVerPojoList.add(pDtl);
                }
            }

            return suspiciousVerPojoList;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String markSuspiciousTran(List<SuspiciousVerifyPojo> gridList, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            for (SuspiciousVerifyPojo it : gridList) {
                String authFlag = "";
                if (it.isCheckBox() == true) {
                    authFlag = "Y";
                } else {
                    authFlag = "D";
                }
                int n = 0;
                if (authFlag.equalsIgnoreCase("D")) {
                    n = em.createNativeQuery("update cbs_str_detail set auth_status ='" + authFlag + "', auth_By = '" + user + "',flag='EXP' where txnid= " + it.getsNo()).executeUpdate();
                } else {
                    n = em.createNativeQuery("update cbs_str_detail set auth_status ='" + authFlag + "', auth_By = '" + user + "' where txnid= " + it.getsNo()).executeUpdate();
                }
                if (n <= 0) {
                    throw new ApplicationException("Problem in data updation.");
                }
            }
            ut.commit();
            return "Record Verified Successfully";
        } catch (Exception e) {
            try {
                e.printStackTrace();
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public List getAccDetailForChange(String accNo) throws ApplicationException {
        List list;
        try {
            String AcNature = ftsPostingRemote.getAccountNature(accNo);
            if (AcNature.equals(CbsConstant.FIXED_AC) || AcNature.equals(CbsConstant.MS_AC)) {
                list = em.createNativeQuery("select a.acno,a.custname,a.custid1,c.CustFullName,a.OperMode,cb.description "
                        + " from td_accountmaster a,cbs_customer_master_detail c,codebook cb where a.acno = '" + accNo + "' "
                        + " and a.custid1 = c.customerid and cb.code = a.opermode and cb.groupcode = 4").getResultList();
            } else {
                list = em.createNativeQuery("select a.acno,a.custname,a.custid1,c.CustFullName,a.OperMode,cb.description "
                        + " from accountmaster a, cbs_customer_master_detail c, codebook cb where a.acno = '" + accNo + "' "
                        + " and a.custid1 = c.customerid and cb.code = a.opermode and cb.groupcode = 4").getResultList();
            }

            if (list.isEmpty()) {
                throw new ApplicationException("Account Details / Joint Id does not exist.");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String changeCustomerIdData(String accNo, String jtId, String remark, String orgBrCode, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            String jtCustName = "", jtTitle = "", jtCrAddress = "", jtPrAddress = "", jtPhoneNo = "", jtPanNo = "", jtDob = "",
                    jtOccupation = "26", jtFatherName = "";

            List custDetailValuesLst = em.createNativeQuery("select CustFullName,ifnull(title,''),ifnull(MailAddressLine1,''),"
                    + "ifnull(MailAddressLine2,''),ifnull(MailBlock,''),ifnull(MailVillage,''),ifnull(MailDistrict,''),"
                    + "ifnull(MailStateCode,''),ifnull(MailPostalCode,''),ifnull(MailCountryCode,''),ifnull(PerAddressLine1,''),"
                    + "ifnull(peraddressline2,''),ifnull(PerBlock,''),ifnull(PerVillage,''),ifnull(PerDistrict,''),ifnull(PerStateCode,''),"
                    + "ifnull(PerPostalCode,''),ifnull(PerCountryCode,''),ifnull(pan_girnumber,'')as pan_girnumber,ifnull(mobilenumber,'') as mobilenumber,"
                    + "date_format(DateOfBirth,'%Y%m%d'),ifnull(fathername,'')as fathername,"
                    + "ifnull(FatherMiddleName,'') as father_middle_name,"
                    + "ifnull(FatherLastName,'') as father_last_name from cbs_customer_master_detail where customerid='" + jtId + "'").getResultList();

            if (!custDetailValuesLst.isEmpty()) {
                Vector custDetailVec = (Vector) custDetailValuesLst.get(0);
                jtCustName = custDetailVec.get(0).toString();
                jtTitle = custDetailVec.get(1).toString();

                jtCrAddress = custDetailVec.get(2).toString() + "," + custDetailVec.get(3).toString(); // corresAdd
                if (!custDetailVec.get(4).toString().equalsIgnoreCase("") || custDetailVec.get(4).toString().length() != 0) {
                    if (jtCrAddress.substring(jtCrAddress.lastIndexOf(",")).equals(",")) {
                        jtCrAddress = jtCrAddress + custDetailVec.get(4).toString();
                    } else {
                        jtCrAddress = jtCrAddress + "," + custDetailVec.get(4).toString();
                    }
                }

                if (!custDetailVec.get(5).toString().equalsIgnoreCase("") || custDetailVec.get(5).toString().length() != 0) {
                    if (jtCrAddress.substring(jtCrAddress.lastIndexOf(",")).equals(",")) {
                        jtCrAddress = jtCrAddress + custDetailVec.get(5).toString();
                    } else {
                        jtCrAddress = jtCrAddress + "," + custDetailVec.get(5).toString();
                    }
                }

                if (!custDetailVec.get(6).toString().equalsIgnoreCase("") || custDetailVec.get(6).toString().length() != 0) {
                    if (jtCrAddress.substring(jtCrAddress.lastIndexOf(",")).equals(",")) {
                        jtCrAddress = jtCrAddress + custDetailVec.get(6).toString();
                    } else {
                        jtCrAddress = jtCrAddress + "," + custDetailVec.get(6).toString();
                    }
                }

                String mailState = "0";
                if (!custDetailVec.get(7).toString().equalsIgnoreCase("0") || custDetailVec.get(7).toString().length() != 0) {
                    mailState = custDetailVec.get(7).toString();
                    if (!mailState.equalsIgnoreCase("0")) {
                        if (jtCrAddress.substring(jtCrAddress.lastIndexOf(",")).equals(",")) {
                            jtCrAddress = jtCrAddress + getCodeDesc("002", mailState);
                        } else {
                            jtCrAddress = jtCrAddress + "," + getCodeDesc("002", mailState);
                        }
                    }
                }

                if (!custDetailVec.get(8).toString().equalsIgnoreCase("") || custDetailVec.get(8).toString().length() != 0) {
                    if (jtCrAddress.substring(jtCrAddress.lastIndexOf(",")).equals(",")) {
                        jtCrAddress = jtCrAddress + custDetailVec.get(8).toString();
                    } else {
                        jtCrAddress = jtCrAddress + "," + custDetailVec.get(8).toString();
                    }
                }

                String mailCountry = "0";
                if (!custDetailVec.get(9).toString().equalsIgnoreCase("0") || custDetailVec.get(9).toString().length() != 0) {
                    mailCountry = custDetailVec.get(9).toString();
                    if (!mailCountry.equalsIgnoreCase("0")) {
                        if (jtCrAddress.substring(jtCrAddress.lastIndexOf(",")).equals(",")) {
                            jtCrAddress = jtCrAddress + getCodeDesc("302", mailCountry);
                        } else {
                            jtCrAddress = jtCrAddress + "," + getCodeDesc("302", mailCountry);
                        }
                    }
                }

                // Per Address                
                jtPrAddress = custDetailVec.get(10).toString() + "," + custDetailVec.get(11).toString();
                if (!custDetailVec.get(12).toString().equalsIgnoreCase("") || custDetailVec.get(12).toString().length() != 0) {
                    if (jtPrAddress.substring(jtPrAddress.lastIndexOf(",")).equals(",")) {
                        jtPrAddress = jtPrAddress + custDetailVec.get(12).toString();
                    } else {
                        jtPrAddress = jtPrAddress + "," + custDetailVec.get(12).toString();
                    }
                }

                if (!custDetailVec.get(13).toString().equalsIgnoreCase("") || custDetailVec.get(13).toString().length() != 0) {
                    if (jtPrAddress.substring(jtPrAddress.lastIndexOf(",")).equals(",")) {
                        jtPrAddress = jtPrAddress + custDetailVec.get(13).toString();
                    } else {
                        jtPrAddress = jtPrAddress + "," + custDetailVec.get(13).toString();
                    }
                }

                if (!custDetailVec.get(14).toString().equalsIgnoreCase("") || custDetailVec.get(14).toString().length() != 0) {
                    if (jtPrAddress.substring(jtPrAddress.lastIndexOf(",")).equals(",")) {
                        jtPrAddress = jtPrAddress + custDetailVec.get(14).toString();
                    } else {
                        jtPrAddress = jtPrAddress + "," + custDetailVec.get(14).toString();
                    }
                }

                String perMailState = "0";
                if (!custDetailVec.get(15).toString().equalsIgnoreCase("0") || custDetailVec.get(15).toString().length() != 0) {
                    perMailState = custDetailVec.get(15).toString();
                    if (!perMailState.equalsIgnoreCase("0")) {
                        if (jtPrAddress.substring(jtPrAddress.lastIndexOf(",")).equals(",")) {
                            jtPrAddress = jtPrAddress + getCodeDesc("002", perMailState);
                        } else {
                            jtPrAddress = jtPrAddress + "," + getCodeDesc("002", perMailState);
                        }
                    }
                }

                if (!custDetailVec.get(16).toString().equalsIgnoreCase("") || custDetailVec.get(16).toString().length() != 0) {
                    if (jtPrAddress.substring(jtPrAddress.lastIndexOf(",")).equals(",")) {
                        jtPrAddress = jtPrAddress + custDetailVec.get(16).toString();
                    } else {
                        jtPrAddress = jtPrAddress + "," + custDetailVec.get(16).toString();
                    }
                }

                String perMailCountry = "0";
                if (!custDetailVec.get(17).toString().equalsIgnoreCase("0") || custDetailVec.get(17).toString().length() != 0) {
                    perMailCountry = custDetailVec.get(17).toString();
                    if (!perMailCountry.equalsIgnoreCase("0")) {
                        if (jtPrAddress.substring(jtPrAddress.lastIndexOf(",")).equals(",")) {
                            jtPrAddress = jtPrAddress + getCodeDesc("302", perMailCountry);
                        } else {
                            jtPrAddress = jtPrAddress + "," + getCodeDesc("302", perMailCountry);
                        }
                    }
                }

                jtPanNo = custDetailVec.get(18).toString();
                jtPhoneNo = custDetailVec.get(19).toString();
                jtDob = custDetailVec.get(20).toString();

                jtFatherName = custDetailVec.get(21).toString();

                String fMiddleName = custDetailVec.get(22).toString();
                String fLastName = custDetailVec.get(23).toString();

                jtFatherName = jtFatherName.trim() + " " + fMiddleName.trim();
                jtFatherName = jtFatherName.trim() + " " + fLastName.trim();
            }

            List occupationCodelist = em.createNativeQuery("select ifnull(nullif(occupationCode,''),'26') from cbs_cust_misinfo where customerid= '" + jtId + "'").getResultList();
            if (!occupationCodelist.isEmpty()) {
                Vector occupationCodelistVec = (Vector) occupationCodelist.get(0);
                jtOccupation = occupationCodelistVec.get(0).toString();
            }

            String AcNature = ftsPostingRemote.getAccountNature(accNo);
            if (AcNature.equals(CbsConstant.FIXED_AC) || AcNature.equals(CbsConstant.MS_AC)) {
                Query insertIntoAccEditHistory = em.createNativeQuery("insert into acedithistory (Acno,Name,OperMode,OrgnCode,EnteredBy,UpdateDt,Auth,AuthBy,"
                        + "introacno,FName,MAddress, PAddress,PhNo,PanNo,chBook,Nominee,Relationship,MinBalCharge,JtName1,JtName2,GName,AcInst, AppTDS,TDSDocu,"
                        + "IntOpt,JTNAME3,JtName4,IntToAcno,cust_type,acctCategory)select a.acno, c.custname , a.opermode, a.orgncode, '" + userName + "',now() ,'Y', '', a.introaccno, "
                        + "c.fathername, c.craddress, c.praddress,c.phoneno,c.panno,0,a.nomination,a.relationship,'2',a.jtname1, a.jtname2, c.grdname, "
                        + "a.instruction,tdsflag,tdsdetails,'', a.jtname3, a.jtname4,'',a.cust_type,a.acctCategory from td_accountmaster a , td_customermaster c "
                        + "where a.acno='" + accNo + "'and c.custno='" + accNo.substring(4, 10) + "'and c.brncode = '" + orgBrCode + "' and substring(a.acno,5,6) = c.custno and a.accttype = c.actype");

                int insertIntoAccEditHistoryResult = insertIntoAccEditHistory.executeUpdate();
                if (insertIntoAccEditHistoryResult <= 0) {
                    throw new ApplicationException("Insertion Problem into acedithistory");
                }

                Query updateTDAccMast = em.createNativeQuery("update td_accountmaster set custname='" + jtCustName + "',opermode='1',jtname1='',lastupdatedt=now(),custid1='' where acno='" + accNo + "'");

                int updateTDAccMastResult = updateTDAccMast.executeUpdate();
                if (updateTDAccMastResult <= 0) {
                    throw new ApplicationException("Updation Problem into Td Account Master.");
                }

                Query insertIntoCustomerHistory = em.createNativeQuery("insert into customermaster_his (custno,title,custname,craddress,"
                        + "praddress,phoneno,panno,status,grdname,relation,dob,occupation,enteredby,lastupdatedt,remarks,cust_lines,actype,agcode,"
                        + "fathername,brncode) select custno,title,custname,craddress,praddress,phoneno,panno,status,grdname,relation,dob,occupation,"
                        + "enteredby,lastupdatedt,remarks,td_lines,actype,agcode,fathername,brncode from td_customermaster "
                        + " where custno = '" + accNo.substring(4, 10) + "' and brncode = '" + orgBrCode + "' "
                        + " and actype = '" + accNo.substring(2, 4) + "' and agcode = '" + accNo.substring(10, 12) + "'");

                int insertIntoCustomerHistoryResult = insertIntoCustomerHistory.executeUpdate();
                if (insertIntoCustomerHistoryResult <= 0) {
                    throw new ApplicationException("Insertion Problem into customerHistory");
                }

                Query updateTDCustMast = em.createNativeQuery("update td_customermaster set title = '" + jtTitle + "', custname = '" + jtCustName + "', "
                        + "craddress = '" + jtCrAddress + "', praddress='" + jtPrAddress + "', phoneno ='" + jtPhoneNo + "',panno ='" + jtPanNo + "', "
                        + "dob = '" + jtDob + "' ,occupation = '" + jtOccupation + "',fathername='" + jtFatherName + "',enteredby = '" + userName + "',"
                        + "lastupdatedt = date_format(now(),'%Y%m%d') where custno = '" + accNo.substring(4, 10) + "' and brncode = '" + orgBrCode + "'"
                        + "and actype = '" + accNo.substring(2, 4) + "' and agcode = '" + accNo.substring(10, 12) + "'");
                int updateTDCustMastResult = updateTDCustMast.executeUpdate();
                if (updateTDCustMastResult <= 0) {
                    throw new ApplicationException("Updation Problem in td customermaster.");
                }
            } else {
                String int_option_old = "";
                if (AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || AcNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || AcNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List intoptions = em.createNativeQuery("Select coalesce(Product,'') From loan_appparameter where acno='" + accNo + "' and BrCode='"
                            + orgBrCode + "' ").getResultList();
                    if (intoptions.isEmpty()) {
                        throw new ApplicationException("Data does not exist for this account in Loan AppParameter.");
                    }
                    Vector res = (Vector) intoptions.get(0);
                    int_option_old = res.get(0).toString();
                }

                int var1 = 0;
                var1 = em.createNativeQuery("insert into acedithistory (Acno,Name,OperMode,OrgnCode,EnteredBy,UpdateDt,Auth,AuthBy,introacno,FName,MAddress,"
                        + " PAddress,PhNo,PanNo,chBook,Nominee,Relationship,MinBalCharge,JtName1,JtName2,GName,AcInst,AppTDS,TDSDocu,IntOpt,JTNAME3,JtName4,"
                        + " IntToAcno, custid1, custid2, custid3, custid4,acctCategory,huf_family) select a.acno, c.custname , a.opermode, a.orgncode," + "'" + userName + "',now(),'Y','',a.introaccno,c.fathername, "
                        + " c.craddress,c.praddress,c.phoneno,c.panno,a.chequebook,a.nomination,a.relatioship,a.minbal,a.JtName1,a.JtName2"
                        + " ,c.grdname,a.instruction,a.tdsflag,''," + "'" + int_option_old + "',a.JtName3,a.JtName4,'', custid1, custid2, custid3, custid4,a.acctCategory,a.huf_family from accountmaster a , "
                        + " customermaster c where a.acno='" + accNo + "' and c.custno=substring('" + accNo + "',5,6) and c.brncode = '" + orgBrCode
                        + "' and c. actype = substring('" + accNo + "',3,2) and c.agcode=substring('" + accNo + "',11,2)").executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Updation problem in Account details");
                }

                var1 = em.createNativeQuery("update accountmaster set custname='" + jtCustName + "', opermode=1,jtname1='',"
                        + "lastupdatedt=date_format(now(),'%Y%m%d'),custid1='' where acno='" + accNo + "' ").executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Updation problem in accountmaster");
                }

                Query insertIntoCustomerHistory = em.createNativeQuery("insert into customermaster_his (custno,title,custname,craddress,"
                        + " praddress,phoneno,panno,status,grdname,relation,dob,occupation,enteredby,lastupdatedt,remarks,cust_lines,"
                        + "actype,agcode1,agcode,fathername,brncode) select custno,title,custname,craddress,"
                        + " praddress,phoneno,panno,status,grdname,relation,dob,occupation,enteredby,lastupdatedt,remarks,cust_lines,"
                        + "actype,agcode1,agcode,fathername,brncode from customermaster "
                        + "where custno=substring('" + accNo + "',5,6) and brncode = '" + orgBrCode
                        + "' and actype = substring('" + accNo + "',3,2) and agcode=substring('" + accNo + "',11,2)");

                int insertIntoCustomerHistoryResult = insertIntoCustomerHistory.executeUpdate();
                if (insertIntoCustomerHistoryResult <= 0) {
                    throw new ApplicationException("Insertion Problem into customerHistory");
                }

                var1 = em.createNativeQuery("update customermaster set title = '" + jtTitle + "', custname = '" + jtCustName + "', "
                        + "craddress = '" + jtCrAddress + "', praddress='" + jtPrAddress + "', phoneno ='" + jtPhoneNo + "',panno ='" + jtPanNo + "', "
                        + "dob = '" + jtDob + "' ,occupation = '" + jtOccupation + "',fathername='" + jtFatherName + "',enteredby = '" + userName + "',"
                        + "lastupdatedt = date_format(now(),'%Y%m%d') where custno = '" + accNo.substring(4, 10) + "' and brncode = '" + orgBrCode + "'"
                        + "and actype = '" + accNo.substring(2, 4) + "' and agcode = '" + accNo.substring(10, 12) + "'").executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Updation problem in customermaster");
                }
            }

            Query insertCustomerIdHistory = em.createNativeQuery("insert into customerid_his (CustId,Acno,EnterBy,TranTime,taxcat,TxnBrn) "
                    + " select CustId,Acno,EnterBy,TranTime,taxcat,TxnBrn from customerid "
                    + " where Acno = '" + accNo + "'");

            int insertCustomerIdHistoryResult = insertCustomerIdHistory.executeUpdate();
            if (insertCustomerIdHistoryResult <= 0) {
                throw new ApplicationException("Insertion Problem into customer ID History");
            }

            Query updateCustomerid = em.createNativeQuery("update customerid set custid = '" + jtId + "' "
                    + " where acno = '" + accNo + "'");
            int updateCustomeridResult = updateCustomerid.executeUpdate();
            if (updateCustomeridResult <= 0) {
                throw new ApplicationException("Updation Problem in td customermaster.");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                e.printStackTrace();
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            } catch (Exception ea) {
                throw new ApplicationException(ea);
            }
        }
    }

    public String getCodeDesc(String recCode, String cityCode) throws ApplicationException {
        List listForIntroCity = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='" + recCode + "'  and ref_code = '" + cityCode + "'").getResultList();
        if (!listForIntroCity.isEmpty()) {
            Vector v5 = (Vector) listForIntroCity.get(0);
            return v5.get(0).toString();
        }
        return "";
    }

    @Override
    public String saveBackDateEntry(List<BackDateEntryPojo> itemList, String brncode, String username, String mode, String remarks, String valueDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            // SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

            ut.begin();
            long trsno = getDateWiseTrsNo(ymd_Format.format(yMMd.parse(valueDate)));
            long recno = 0;
            for (BackDateEntryPojo itemData : itemList) {
                if (recno == 0) {
                    recno = getReconByAcnoDt(ymd_Format.format(yMMd.parse(itemData.getDate())));
                } else {
                    recno = recno + 1;
                }

                if (itemData.getTxnType().equalsIgnoreCase("CR")) {
                    int n = em.createNativeQuery("insert into recon_trf_d (Acno,custname ,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,"
                            + " Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,"
                            + " Tran_id,Term_id,adviceNo,adviceBrnCode) values ('" + itemData.getAcno() + "','" + itemData.getGlname() + "','" + ymd_Format.format(yMMd.parse(itemData.getDate())) + "',"
                            + " 0.0,'" + itemData.getAmount() + "' , 0 ,2, '" + recno + "','" + trsno + "','',3.0, 0,77,'" + remarks + "','" + brncode + "','" + brncode + "',"
                            + " '" + ymd_Format.format(yMMd.parse(itemData.getDate())) + "' ,'N','" + username + "','','" + ymd_Format.format(yMMd.parse(itemData.getDate())) + "','A',0,'','','')").executeUpdate();

                    if (n <= 0) {
                        throw new ApplicationException("Problem in batch insertion.");
                    }
                } else {
                    int n = em.createNativeQuery("insert into recon_trf_d (Acno,custname,Dt,Dramt,Cramt,Ty,Trantype,Recno,Trsno,Instno,"
                            + " Payby,iy,TranDesc,Details,org_brnid,dest_brnid, Trantime,Auth,EnterBy,AuthBy,valuedt,SubTokenNo,"
                            + " Tran_id,Term_id,adviceNo,adviceBrnCode) values ('" + itemData.getAcno() + "','" + itemData.getGlname() + "','" + ymd_Format.format(yMMd.parse(itemData.getDate())) + "',"
                            + " '" + itemData.getAmount() + "',0.0, 1 ,2, '" + recno + "','" + trsno + "','',3.0, 0,77,'" + remarks + "','" + brncode + "','" + brncode + "',"
                            + " '" + ymd_Format.format(yMMd.parse(itemData.getDate())) + "' ,'N','" + username + "','','" + ymd_Format.format(yMMd.parse(itemData.getDate())) + "','A',0,'','','')").executeUpdate();

                    if (n <= 0) {
                        throw new ApplicationException("Problrm in batch insertion.");
                    }
                }
                if (itemData.getTxnType().equalsIgnoreCase("DR")) {
                    String result = ftsPostingRemote.updateBalance("PO", itemData.getAcno(), 0.0, itemData.getAmount().doubleValue(), "Y", "Y");
                    if (!result.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(result);
                    }
                }
            }
            ut.commit();
            return "true" + trsno;
        } catch (NotSupportedException | SystemException | ParseException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                throw new ApplicationException(ex1);
            }
            throw new ApplicationException(ex);
        }
    }

    public long getReconByAcnoDt(String dt) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select cast(ifnull(max(recno),0) as unsigned) from gl_recon where dt = '" + dt + "' "
                    + "union all select cast(ifnull(max(recno),0) as unsigned) from recon_trf_d where dt = '" + dt + "'").getResultList();
            Vector vtr = (Vector) list.get(0);
            return Long.parseLong(vtr.get(0).toString()) + 1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<BackDateEntryPojo> getUnauthorizedData() throws ApplicationException {
        List<BackDateEntryPojo> dataList = new ArrayList<>();
        try {
            List result = em.createNativeQuery("select acno,ifnull(custname,''),date_format(dt,'%d/%m/%Y'),dramt,cramt,trsno,recno,details from recon_trf_d where auth = 'N' and authby='' and trandesc = '77' order by trsno ").getResultList();
            if (result.isEmpty()) {
                throw new ApplicationException("There is no list");
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vec = (Vector) result.get(i);
                String acno = vec.get(0).toString();
                String glName = vec.get(1).toString();
                String date = vec.get(2).toString();
                double dramt = Double.parseDouble(vec.get(3).toString());
                double cramt = Double.parseDouble(vec.get(4).toString());
                int batch = Integer.parseInt(vec.get(5).toString());
                double recno = Double.parseDouble(vec.get(6).toString());
                String detail = vec.get(7).toString();

                BackDateEntryPojo pojo = new BackDateEntryPojo();
                pojo.setAcno(acno);
                pojo.setGlname(glName);
                pojo.setDate(date);
                pojo.setDramount(new BigDecimal(dramt));
                pojo.setCramount(new BigDecimal(cramt));
                pojo.setBatchno(batch);
                pojo.setRecno(recno);
                pojo.setDetail(detail);

                dataList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public List<BackDateEntryPojo> getBatchList(String function, int batchno) throws ApplicationException {
        List<BackDateEntryPojo> batchList = new ArrayList<>();
        try {
            List result = em.createNativeQuery("select acno,custname,date_format(dt,'%d/%m/%Y'),dramt,cramt,trsno,recno,details from recon_trf_d where auth = 'N' and authby='' and trandesc = '77' and trsno = '" + batchno + "'").getResultList();
            if (result.isEmpty()) {
                throw new ApplicationException("There is no list");
            }
            for (int i = 0; i < result.size(); i++) {
                Vector vec = (Vector) result.get(i);
                String acno = vec.get(0).toString();
                String glName = vec.get(1).toString();
                String date = vec.get(2).toString();
                double dramt = Double.parseDouble(vec.get(3).toString());
                double cramt = Double.parseDouble(vec.get(4).toString());
                int batch = Integer.parseInt(vec.get(5).toString());
                double recno = Double.parseDouble(vec.get(6).toString());
                String detail = vec.get(7).toString();

                BackDateEntryPojo pojo = new BackDateEntryPojo();
                pojo.setAcno(acno);
                pojo.setGlname(glName);
                pojo.setDate(date);
                pojo.setDramount(new BigDecimal(dramt));
                pojo.setCramount(new BigDecimal(cramt));
                pojo.setBatchno(batch);
                pojo.setRecno(recno);
                pojo.setDetail(detail);

                batchList.add(pojo);
            }

        } catch (ApplicationException | NumberFormatException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return batchList;
    }

    @Override
    public String authorizeBackDateEntry(List<BackDateEntryPojo> itemList, String username, String Date, String brnCode, float trsNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
            ut.begin();
            List enterByList2 = em.createNativeQuery("SELECT LevelId FROM securityinfo WHERE UserId ='" + username + "'").getResultList();
            if (enterByList2.isEmpty()) {
                throw new ApplicationException("User does not exist");
            }
            Vector enterByVect = (Vector) enterByList2.get(0);
            String levelid = enterByVect.get(0).toString();
            if (!(levelid.equalsIgnoreCase("1") || levelid.equalsIgnoreCase("2"))) {
                throw new ApplicationException("You are not the authorize person to authorize this Entry.");
            }

            List enterByList1 = em.createNativeQuery("SELECT ENTERBY FROM recon_trf_d WHERE TRSNO =" + trsNo).getResultList();
            Vector vect = (Vector) enterByList1.get(0);
            String enterBy1 = vect.get(0).toString();
            if (enterBy1.toUpperCase().equals(username.toUpperCase())) {
                throw new ApplicationException("You can not be Authorize Your Own Entry.");
            }

            String auth = "Y";
            for (BackDateEntryPojo item : itemList) {
                int updateQry = em.createNativeQuery("UPDATE recon_trf_d SET AUTH = '" + auth + "', AUTHBY = '" + username + "' WHERE  "
                        + "ACNO = '" + item.getAcno() + "' AND RECNO ='" + item.getRecno() + "' AND TRSNO ='" + item.getBatchno() + "'").executeUpdate();
                if (updateQry < 0) {
                    throw new ApplicationException("Problem in updation of recon_trf_d Authorization");
                }
                int ty = 0;
                if (item.getDramount().compareTo(BigDecimal.ZERO) != 0) {
                    ty = 1;
                }
                Integer varinsertGlReconList = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, instno,EnterBy, Auth, recno,payby,Authby, trsno, TranDesc, TokenNo,tokenPaidBy, SubTokenNo,trantime,"
                        + "org_brnid,dest_brnid,tran_id,term_id,AdviceNo,AdviceBrnCode) values('" + item.getAcno() + "','" + ty + "','" + ymdFormat.format(yMMd.parse(item.getDate())) + "', "
                        + " '" + ymdFormat.format(yMMd.parse(item.getDate())) + "'," + item.getDramount() + "," + item.getCramount() + ",0.0 , 2,"
                        + "'" + item.getDetail() + "', 0 ,'','" + username + "','" + auth + "'," + item.getRecno() + ", 3.0 ,'" + username + "'," + item.getBatchno() + ", 77 ,"
                        + "  0,'','',now(),'" + brnCode + "','" + brnCode + "', 0 ,"
                        + " '','','' )").executeUpdate();
                if (varinsertGlReconList <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No :- " + item.getAcno());
                }
                if (!(item.getCramount().compareTo(BigDecimal.ZERO) == 0)) {
                    String result = ftsPostingRemote.updateBalance("PO", item.getAcno(), item.getCramount().doubleValue(), item.getDramount().doubleValue(), "Y", "Y");
                    if (!result.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(result);
                    }
                }
            }
            ut.commit();
        } catch (ParseException | NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                throw new ApplicationException(ex1.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String deleteOperationBackDateEnry(List<BackDateEntryPojo> itemList, String username, String Date, String brnCode, float trsNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List authByList1 = em.createNativeQuery("SELECT AUTH FROM recon_trf_d WHERE TRSNO =" + trsNo + "and auth = 'Y'").getResultList();
            if (authByList1.size() > 0) {
                throw new ApplicationException("You can not be delete this batch.");
            }
            int n = em.createNativeQuery(" DELETE FROM recon_trf_d  WHERE TRSNO =" + trsNo).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in Batch deletion ");
            }
            for (BackDateEntryPojo item : itemList) {
                if (!(item.getDramount().compareTo(BigDecimal.ZERO) != 0)) {
                    String result = ftsPostingRemote.updateBalance("PO", item.getAcno(), item.getDramount().doubleValue(), item.getCramount().doubleValue(), "Y", "Y");
                    if (!result.equalsIgnoreCase("TRUE")) {
                        throw new ApplicationException(result);
                    }
                }
            }
            ut.commit();
        } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex1) {
                throw new ApplicationException(ex1.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    @Override
    public long getDateWiseTrsNo(String dt) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select cast(ifnull(max(trsno),0) as unsigned)+1 from gl_recon where dt = '" + dt + "' "
                    + "union all select cast(ifnull(max(recno),0) as unsigned) from recon_trf_d where dt = '" + dt + "'").getResultList();
            Vector vtr = (Vector) list.get(0);
            return Long.parseLong(vtr.get(0).toString()) + 1;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
