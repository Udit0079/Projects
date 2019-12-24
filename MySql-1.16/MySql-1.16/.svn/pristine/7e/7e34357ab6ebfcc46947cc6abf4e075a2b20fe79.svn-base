package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.RdReschedulePojo;
import com.cbs.dto.report.AbbStatementPojo;
import com.cbs.dto.report.AccountEditHistoryPojo;
import com.cbs.dto.report.AccountOpenRegisterPojo;
import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.report.AccountStatusReportPojo;
import com.cbs.dto.report.AccountTransactionPojo;
import com.cbs.dto.report.ArfBrcPojo;
import com.cbs.dto.report.BillSundryDetailsPojo;
import com.cbs.dto.report.CADebitBalancePojo;
import com.cbs.dto.report.CrDrBalancePojo;
import com.cbs.dto.report.CreditBalancePojo;
import com.cbs.dto.report.CustIdWiseAcStmt;
import com.cbs.dto.report.FdAccountDetailPojo;
import com.cbs.dto.report.Form60ReportPojo;
import com.cbs.dto.report.FormNo15gPart1Pojo;
import com.cbs.dto.report.HoIntCalPojo;
import com.cbs.dto.report.IMPSTxnParameterPojo;
import com.cbs.dto.report.InoperativeMarkingPojo;
import com.cbs.dto.report.InterestFdReportPojo;
import com.cbs.dto.report.KYCcategorisationPojo;
import com.cbs.dto.report.KycAccountDetailPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueNonEmiResultList;
import com.cbs.dto.report.PendingChargesReportPojo;
import com.cbs.dto.report.SlabDetailPojo;
import com.cbs.dto.report.SmsPostingReportPojo;
import com.cbs.dto.report.TdsOnCashWithdrawalPojo;
import com.cbs.dto.report.TopDepositorBorrowerPojo;
import com.cbs.dto.report.TransactionDetailsPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.dto.report.ZeroBalReportPojo;
import com.cbs.dto.report.ho.AtmDisputePojo;
import com.cbs.dto.report.ho.AtmStatusPojo;
import com.cbs.dto.report.ho.UserDepuTrfDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.master.TermDepositMasterFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.pojo.CurrencyExchangePojo;
import com.cbs.pojo.CustAccountInfoPojo;
import com.cbs.pojo.DeletedTransactionPojo;
import com.cbs.pojo.DenominationDetailsPojo;
import com.cbs.pojo.DepositLoanPojo;
import com.cbs.pojo.FiuDormantToOperative;
import com.cbs.pojo.InoperativeToOperativePoJo;
import com.cbs.pojo.JanDhanAcnoInfoPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.PfmsRegistrationDetailPojo;
import com.cbs.pojo.SalarySheetPojo;
import com.cbs.pojo.SmsAcnoRegistrationPojo;
import com.cbs.pojo.SortedByNpaBal;
import com.cbs.pojo.ThresoldTransactionInfoPojo;
import com.cbs.pojo.ctrMoreThan1Crore;
import com.cbs.pojo.exchangeDetailPojo;
import com.cbs.pojo.jdHavingDepositPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;

@Stateless(mappedName = "MiscReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class MiscReportFacade implements MiscReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsBean;
    @EJB
    InterBranchTxnFacadeRemote sxcxBean;
    @EJB
    LoanReportFacadeRemote loanReport;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private SbIntCalcFacadeRemote intRemote;
    @EJB
    private AdvancesInformationTrackingRemote advInfoRemote;
    @EJB
    private NpaReportFacadeRemote npaRemote;
    @EJB
    private TermDepositMasterFacadeRemote tdRemote;
    @EJB
    private OtherReportFacadeRemote otherRemote;
    @EJB
    private CtrStrAirReportFacadeRemote ctrStrRemote;
    @EJB
    TransactionManagementFacadeRemote txnRemote;
    @EJB
    TdReceiptManagementFacadeRemote orgFdInt;
    @EJB
    AccountOpeningFacadeRemote accountRemote;
    @EJB
    OverDueReportFacadeRemote overDueRemote;
    @Resource
    EJBContext context;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat nft = new DecimalFormat("0.00");
    NumberFormat formatter = new DecimalFormat("#");

    @Override
    public List<AccountStatusReportPojo> getAccountStatusReport(String date, String brnCode) throws ApplicationException {
        List<AccountStatusReportPojo> returnList = new ArrayList<AccountStatusReportPojo>();
        try {
            List resultList1 = em.createNativeQuery("SELECT acctCode,acctDesc,acctNature,acctType FROM accounttypemaster WHERE acctNature <>'PO'").getResultList();
            for (int i = 0; i < resultList1.size(); i++) {
                Vector ele1 = (Vector) resultList1.get(i);
                String acctCode = ele1.get(0).toString();
                String acctDesc = ele1.get(1).toString();
                String acctNature = ele1.get(2).toString();
                String acctType = ele1.get(3).toString();
                int acnoActiveCount = 0, acnoClosedCount = 0;
                String gType = "";
                if (acctNature.equalsIgnoreCase("FD") || (acctNature.equalsIgnoreCase("MS")) || acctNature.equalsIgnoreCase("RD") || acctNature.equalsIgnoreCase("DS") || acctNature.equalsIgnoreCase("SB") || acctType.equalsIgnoreCase("CA")) {
                    if (acctNature.equalsIgnoreCase("FD") || acctNature.equalsIgnoreCase("MS")) {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM td_accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype='" + acctCode + "'").getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                gType = ele2.get(0).toString();
                                acnoActiveCount = Integer.parseInt(ele2.get(1).toString());
                            }
                            Vector ele3 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM td_accountmaster where OpeningDt <= '" + date + "' and (ifnull(closingdate,'')<>'' and closingdate<='" + date + "') and accttype='" + acctCode + "'").getSingleResult();
                            if (ele3 != null && ele3.get(0) != null) {
                                gType = ele3.get(0).toString();
                                acnoClosedCount = Integer.parseInt(ele3.get(1).toString());
                            }
                        } else {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM td_accountmaster WHERE curbrcode= '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype='" + acctCode + "'").getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                gType = ele2.get(0).toString();
                                acnoActiveCount = Integer.parseInt(ele2.get(1).toString());
                            }
                            Vector ele3 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM td_accountmaster where curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' and (ifnull(closingdate,'')<>'' and closingdate<='" + date + "') and accttype='" + acctCode + "'").getSingleResult();
                            if (ele3 != null && ele3.get(0) != null) {
                                gType = ele3.get(0).toString();
                                acnoClosedCount = Integer.parseInt(ele3.get(1).toString());
                            }
                        }
                    } else {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype='" + acctCode + "'").getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                gType = ele2.get(0).toString();
                                acnoActiveCount = Integer.parseInt(ele2.get(1).toString());
                            }
                            Vector ele3 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM accountmaster where OpeningDt <= '" + date + "' and (ifnull(closingdate,'')<>'' and closingdate<='" + date + "') and accttype='" + acctCode + "'").getSingleResult();
                            if (ele3 != null && ele3.get(0) != null) {
                                gType = ele3.get(0).toString();
                                acnoClosedCount = Integer.parseInt(ele3.get(1).toString());
                            }
                        } else {
                            Vector ele2 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM accountmaster WHERE curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype='" + acctCode + "'").getSingleResult();
                            if (ele2 != null && ele2.get(0) != null) {
                                gType = ele2.get(0).toString();
                                acnoActiveCount = Integer.parseInt(ele2.get(1).toString());
                            }
                            Vector ele3 = (Vector) em.createNativeQuery("SELECT 'Deposit' as depositLoan,COUNT(acno) as acNo FROM accountmaster where curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' and (ifnull(closingdate,'')<>'' and closingdate<='" + date + "') and accttype='" + acctCode + "'").getSingleResult();
                            if (ele3 != null && ele3.get(0) != null) {
                                gType = ele3.get(0).toString();
                                acnoClosedCount = Integer.parseInt(ele3.get(1).toString());
                            }
                        }
                    }

                } else {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT 'Loan' as depositLoan,COUNT(acno) as acNo FROM accountmaster WHERE OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype='" + acctCode + "'").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            gType = ele2.get(0).toString();
                            acnoActiveCount = Integer.parseInt(ele2.get(1).toString());
                        }
                        Vector ele3 = (Vector) em.createNativeQuery("SELECT 'Loan' as depositLoan,COUNT(acno) as acNo FROM accountmaster where OpeningDt <= '" + date + "' and (ifnull(closingdate,'')<>'' and closingdate<='" + date + "') and accttype='" + acctCode + "'").getSingleResult();
                        if (ele3 != null && ele3.get(0) != null) {
                            gType = ele3.get(0).toString();
                            acnoClosedCount = Integer.parseInt(ele3.get(1).toString());
                        }
                    } else {
                        Vector ele2 = (Vector) em.createNativeQuery("SELECT 'Loan' as depositLoan,COUNT(acno) as acNo FROM accountmaster WHERE curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' AND (ifnull(closingdate,'')='' or closingdate > '" + date + "' OR closingdate='') AND accttype='" + acctCode + "'").getSingleResult();
                        if (ele2 != null && ele2.get(0) != null) {
                            gType = ele2.get(0).toString();
                            acnoActiveCount = Integer.parseInt(ele2.get(1).toString());
                        }
                        Vector ele3 = (Vector) em.createNativeQuery("SELECT 'Loan' as depositLoan,COUNT(acno) as acNo FROM accountmaster where curbrcode = '" + brnCode + "' AND OpeningDt <= '" + date + "' and (ifnull(closingdate,'')<>'' and closingdate<='" + date + "') and accttype='" + acctCode + "'").getSingleResult();
                        if (ele3 != null && ele3.get(0) != null) {
                            gType = ele3.get(0).toString();
                            acnoClosedCount = Integer.parseInt(ele3.get(1).toString());
                        }
                    }
                }
                AccountStatusReportPojo pojo = new AccountStatusReportPojo();
                pojo.setAcName(acctDesc);
                pojo.setActiveAcc(acnoActiveCount);
                pojo.setClosedAcc(acnoClosedCount);
                pojo.setDepositLoan(gType);
                pojo.setAcNo(date);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List<AccountStatementReportPojo> getAccountStatementReport(String acNo, String fromDate, String toDate) throws ApplicationException {
        List<AccountStatementReportPojo> returnList = new ArrayList<AccountStatementReportPojo>();
        double openBal;
        try {
            List tempList = null;
            String date = "", particulars = "", chequeno = "", acNat, valdt = "", custName = "", acctDesc = "", jtName1 = null, jtName2 = null, crAdd = null, prAdd = null, jtName3 = null, jtName4 = null, nomName = null, tableName = "", gstn = "", opDate = "", mobileNo = "";
            double withdrawl = 0, deposit = 0, balance = 0, pendBal;
            Calendar calFromDt = Calendar.getInstance();
            calFromDt.setTime(ymdFormat.parse(fromDate));
            calFromDt.add(Calendar.DATE, -1);
            String dtB = ymdFormat.format(calFromDt.getTime());
            acNat = ftsBean.getAccountNature(acNo);
            if (acNat != null) {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList = em.createNativeQuery("Select a.custname, b.acctdesc, a.jtname1, a.jtname2, c.craddress, c.praddress,a.jtname3,a.jtname4,a.nomination,date_format(a.OpeningDt,'%d/%m/%Y')"
                            + " from td_accountmaster a, accounttypemaster b,td_customermaster c where a.acno='" + acNo + "' and substring(a.acno,3,2)=b.acctcode and "
                            + "substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype").getResultList();
                } else {
                    tempList = em.createNativeQuery("Select a.custname, b.acctdesc, a.jtname1, a.jtname2, c.craddress, c.praddress,a.jtname3,a.jtname4,a.nomination,date_format(a.OpeningDt,'%d/%m/%Y')"
                            + " from accountmaster a, accounttypemaster b ,customermaster c where a.acno='" + acNo + "' and substring(a.acno,3,2)=b.acctcode and "
                            + "substring(a.acno,5,6)=c.custno and c.brncode = a.curbrcode and substring(a.acno,3,2)=c.actype and "
                            + "substring(a.acno,11,2)=c.agcode").getResultList();
                }

                String acStatus = "", PerStatus = "", acStatusDesc = "";
                List acStatusList = em.createNativeQuery("select code from cbs_parameterinfo where name in('AC_STATUS')").getResultList();
                if (!acStatusList.isEmpty()) {
                    Vector acStatusVect = (Vector) acStatusList.get(0);
                    acStatus = acStatusVect.get(0).toString();
                }

                if (acStatus.equalsIgnoreCase("Y")) {
                    List acPerStatusList = em.createNativeQuery("select accstatus,Description from accountmaster a, codebook c where acno = '" + acNo + "' "
                            + "and c.groupcode = '3' and a.accstatus = c.code"
                            + " union "
                            + "select accstatus,Description from td_accountmaster a, codebook c where acno = '" + acNo + "' "
                            + "and c.groupcode = '3' and a.accstatus = c.code").getResultList();
                    Vector acStatusVect1 = (Vector) acPerStatusList.get(0);
                    PerStatus = acStatusVect1.get(0).toString();
                    acStatusDesc = acStatusVect1.get(1).toString();
                }

                if (tempList.isEmpty()) {
                    return null;
                } else {
                    Vector ele = (Vector) tempList.get(0);
                    custName = ele.get(0).toString();
                    acctDesc = ele.get(1).toString();
                    opDate = ele.get(9).toString();
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        tableName = " td_accountmaster ";
                    } else {
                        tableName = " accountmaster ";
                    }

//                    List custList = em.createNativeQuery(" select ifnull(name,'')  from acedithistory where acno = '" + acNo + "' "
//                            + " and txnid=(select min(txnid) from acedithistory where acno = '" + acNo + "'  and date_format(UpdateDt,'%Y%m%d') >'" + toDate + "')").getResultList();
//                    if (!custList.isEmpty()) {
//                        Vector custVect = (Vector) custList.get(0);
//                        custName = !custVect.get(0).toString().equalsIgnoreCase("") ? custVect.get(0).toString() : "";
//                        if (jtName1.equalsIgnoreCase("")) {
//                            joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
//                            if (!joint1List.isEmpty()) {
//                                joint1Vect = (Vector) joint1List.get(0);
//                                jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? joint1Vect.get(1).toString() : "";
//                            } else {
//                                jtName1 = "";
//                            }
//                        }
                    //  }
                    List joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'')  from acedithistory where acno = '" + acNo + "' "
                            + " and txnid=(select min(txnid) from acedithistory where acno = '" + acNo + "'  and date_format(UpdateDt,'%Y%m%d') >'" + toDate + "')").getResultList();
                    if (!joint1List.isEmpty()) {
                        Vector joint1Vect = (Vector) joint1List.get(0);
                        jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? joint1Vect.get(1).toString() : "";
//                        if (jtName1.equalsIgnoreCase("")) {
//                            joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
//                            if (!joint1List.isEmpty()) {
//                                joint1Vect = (Vector) joint1List.get(0);
//                                jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? joint1Vect.get(1).toString() : "";
//                            } else {
//                                jtName1 = "";
//                            }
//                        }
                    } else {
                        joint1List = em.createNativeQuery(" select ifnull(custid1,''),ifnull(JtName1,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
                        if (!joint1List.isEmpty()) {
                            Vector joint1Vect = (Vector) joint1List.get(0);
                            jtName1 = !joint1Vect.get(1).toString().equalsIgnoreCase("") ? joint1Vect.get(1).toString() : "";
                        } else {
                            jtName1 = "";
                        }
                    }

                    List joint2List = em.createNativeQuery(" select ifnull(custid2,''),ifnull(JtName2,'')  from acedithistory where acno = '" + acNo + "' "
                            + " and txnid=(select min(txnid) from acedithistory where acno = '" + acNo + "'  and date_format(UpdateDt,'%Y%m%d') >'" + toDate + "')").getResultList();
                    if (!joint2List.isEmpty()) {
                        Vector joint2Vect = (Vector) joint2List.get(0);
                        jtName2 = !joint2Vect.get(1).toString().equalsIgnoreCase("") ? joint2Vect.get(1).toString() : "";
//                        if (jtName2.equalsIgnoreCase("")) {
//                            joint2List = em.createNativeQuery(" select ifnull(custid2,''),ifnull(JtName2,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
//                            if (!joint2List.isEmpty()) {
//                                joint2Vect = (Vector) joint2List.get(0);
//                                jtName2 = !joint2Vect.get(1).toString().equalsIgnoreCase("") ? joint2Vect.get(1).toString() : "";
//                            } else {
//                                jtName2 = "";
//                            }
//                        }
                    } else {
                        joint2List = em.createNativeQuery(" select ifnull(custid2,''),ifnull(JtName2,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
                        if (!joint2List.isEmpty()) {
                            Vector joint2Vect = (Vector) joint2List.get(0);
                            jtName2 = !joint2Vect.get(1).toString().equalsIgnoreCase("") ? joint2Vect.get(1).toString() : "";
                        } else {
                            jtName2 = "";
                        }
                    }

                    List joint3List = em.createNativeQuery(" select ifnull(custid3,''),ifnull(JtName3,'')  from acedithistory where acno = '" + acNo + "' "
                            + " and txnid=(select min(txnid) from acedithistory where acno = '" + acNo + "'  and date_format(UpdateDt,'%Y%m%d') >'" + toDate + "')").getResultList();
                    if (!joint3List.isEmpty()) {
                        Vector joint3Vect = (Vector) joint3List.get(0);
                        jtName3 = !joint3Vect.get(1).toString().equalsIgnoreCase("") ? joint3Vect.get(1).toString() : "";
//                        if (jtName3.equalsIgnoreCase("")) {
//                            joint3List = em.createNativeQuery(" select ifnull(custid3,''),ifnull(JtName3,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
//                            if (!joint3List.isEmpty()) {
//                                joint3Vect = (Vector) joint3List.get(0);
//                                jtName3 = !joint3Vect.get(1).toString().equalsIgnoreCase("") ? joint3Vect.get(1).toString() : "";
//                            } else {
//                                jtName3 = "";
//                            }
//                        }
                    } else {
                        joint3List = em.createNativeQuery(" select ifnull(custid3,''),ifnull(JtName3,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
                        if (!joint3List.isEmpty()) {
                            Vector joint3Vect = (Vector) joint3List.get(0);
                            jtName3 = !joint3Vect.get(1).toString().equalsIgnoreCase("") ? joint3Vect.get(1).toString() : "";
                        } else {
                            jtName3 = "";
                        }
                    }

                    List joint4List = em.createNativeQuery(" select ifnull(custid4,''),ifnull(JtName4,'')  from acedithistory where acno = '" + acNo + "' "
                            + " and txnid=(select min(txnid) from acedithistory where acno = '" + acNo + "'  and date_format(UpdateDt,'%Y%m%d') >'" + toDate + "')").getResultList();
                    if (!joint4List.isEmpty()) {
                        Vector joint4Vect = (Vector) joint4List.get(0);
                        jtName4 = !joint4Vect.get(1).toString().equalsIgnoreCase("") ? joint4Vect.get(1).toString() : "";
//                        if (jtName4.equalsIgnoreCase("")) {
//                            joint4List = em.createNativeQuery(" select ifnull(custid4,''),ifnull(JtName4,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
//                            if (!joint4List.isEmpty()) {
//                                joint4Vect = (Vector) joint4List.get(0);
//                                jtName4 = !joint4Vect.get(1).toString().equalsIgnoreCase("") ? joint4Vect.get(1).toString() : "";
//                            } else {
//                                jtName4 = "";
//                            }
//                        }
                    } else {
                        joint4List = em.createNativeQuery(" select ifnull(custid4,''),ifnull(JtName4,'') from " + tableName + " where acno = '" + acNo + "' ").getResultList();
                        if (!joint4List.isEmpty()) {
                            Vector joint4Vect = (Vector) joint4List.get(0);
                            jtName4 = !joint4Vect.get(1).toString().equalsIgnoreCase("") ? joint4Vect.get(1).toString() : "";
                        } else {
                            jtName4 = "";
                        }
                    }

                    crAdd = ele.get(4).toString();
                    prAdd = ele.get(5).toString();

                    List addList = em.createNativeQuery("select ifnull(mailAdd,''), ifnull(MailCityCode,''),ifnull(mailstatecode,''),  ifnull(MailPostalCode,''), ifnull(perAdd,''), ifnull(PerCityCode,''), ifnull(Perstatecode,''), ifnull(PerPostalCode,''), LastChangeTime,gstno,mobilenumber,custname from "
                            + " (select ifnull(a.custfullname,'') as custname ,CONCAT(ifnull(a.MailAddressLine1,''),ifnull(a.MailAddressLine2,''), ifnull(a.MailVillage,''), ifnull(a.MailBlock,'')) as mailAdd, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.mailCityCode) as MailCityCode, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.mailstatecode) as mailstatecode,a.MailPostalCode, "
                            + " CONCAT(ifnull(a.PerAddressLine1,''), ifnull(a.PerAddressLine2,''), ifnull(a.PerVillage,''), ifnull(a.PerBlock,'')) as perAdd, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.PerCityCode) as PerCityCode, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.Perstatecode) as Perstatecode,a.PerPostalCode, "
                            + " date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T') AS LastChangeTime, a.TXNID,ifnull(a.gstIdentificationNumber,'') gstno,mobilenumber from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acNo + "' "
                            + " and a.TXNID =(select max(a.TXNID) from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acNo + "' "
                            + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d')<='" + fromDate + "') "
                            + " union all"
                            + " select ifnull(a.custfullname,'') as custname ,CONCAT(ifnull(a.MailAddressLine1,''),ifnull(a.MailAddressLine2,''), ifnull(a.MailVillage,''), ifnull(a.MailBlock,'')) as mailAdd, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.mailCityCode) as MailCityCode, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.mailstatecode) as mailstatecode,a.MailPostalCode, "
                            + " CONCAT(ifnull(a.PerAddressLine1,''), ifnull(a.PerAddressLine2,''), ifnull(a.PerVillage,''), ifnull(a.PerBlock,'')) as perAdd, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = a.PerCityCode) as PerCityCode, "
                            + " (select ref_desc from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = a.Perstatecode) as Perstatecode,a.PerPostalCode, "
                            + " date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T') AS LastChangeTime, 0 as TXNID,ifnull(a.gstIdentificationNumber,'') gstno,mobilenumber from cbs_customer_master_detail a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acNo + "' "
                            + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d') <= '" + fromDate + "' ) a where a.LastChangeTime= "
                            + " (select max(LastChangeTime) from "
                            + " (select date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T')  as LastChangeTime from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acNo + "' "
                            + " and a.TXNID =(select max(a.TXNID) from cbs_customer_master_detail_his a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acNo + "' "
                            + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d')<='" + fromDate + "') "
                            + " union all "
                            + " select date_format(ifnull(LastChangeTime,'1900-01-01'),'%Y-%m-%d %T')  as LastChangeTime from cbs_customer_master_detail a, customerid b   where a.customerid = b.CustId and b.Acno = '" + acNo + "' "
                            + " and date_format(ifnull(a.LastChangeTime,'19000101'),'%Y%m%d')<='" + fromDate + "' ) a) ").getResultList();

                    if (!addList.isEmpty()) {
                        Vector addVect = (Vector) addList.get(0);
                        if (addVect.get(0).toString().trim().contains(addVect.get(1).toString().trim())) {
                            crAdd = addVect.get(0).toString().trim();
                        } else {
                            crAdd = addVect.get(0).toString().trim().concat(", ").concat(addVect.get(1).toString().trim());
                        }
                        if (!addVect.get(2).toString().trim().equalsIgnoreCase("")) {
                            crAdd = crAdd.concat(", ").concat(addVect.get(2).toString().trim());
                        }
                        if (!addVect.get(3).toString().trim().equalsIgnoreCase("")) {
                            crAdd = crAdd.concat(", ").concat(addVect.get(3).toString().trim());
                        }

                        if (addVect.get(4).toString().trim().contains(addVect.get(5).toString().trim())) {
                            prAdd = addVect.get(4).toString().trim();
                        } else {
                            prAdd = addVect.get(4).toString().trim().concat(",").concat(addVect.get(5).toString().trim());
                        }
                        if (!addVect.get(6).toString().trim().equalsIgnoreCase("")) {
                            prAdd = prAdd.concat(", ").concat(addVect.get(6).toString().trim());
                        }
                        if (!addVect.get(7).toString().trim().equalsIgnoreCase("")) {
                            prAdd = prAdd.concat(", ").concat(addVect.get(7).toString().trim());
                        }
                        gstn = addVect.get(9).toString();

                        if (addVect.get(10) == null) {
                            mobileNo = "";
                        } else {
                            mobileNo = addVect.get(10).toString();
                        }
                        custName = !addVect.get(11).toString().equalsIgnoreCase("") ? addVect.get(11).toString() : "";
                    }
                    if (ele.get(8) == null) {
                        nomName = "";
                    } else {
                        nomName = ele.get(8).toString() != null ? ele.get(8).toString() : "";
                    }
                }
                pendBal = common.getDoubleValueFromVector((Vector) em.createNativeQuery("SELECT ifnull(SUM(txninstamt) ,0) from clg_ow_shadowbal "
                        + "where acno = '" + acNo + "' AND DT <= '" + toDate + "'").getSingleResult());
                openBal = common.getBalanceOnDate(acNo, dtB);
                List<AbbStatementPojo> abbStatement = common.getAbbStatement(acNo, fromDate, toDate, "");
                for (AbbStatementPojo asp : abbStatement) {
                    date = asp.getDate1();
                    particulars = asp.getParticulars();
                    chequeno = asp.getChequeNo();
                    withdrawl = asp.getWithdrawl();
                    deposit = asp.getDeposit();
                    balance = asp.getBalance();
                    valdt = asp.getValueDate();
                    AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                    pojo.setAcNo(acNo);
                    pojo.setPerStatus(PerStatus);
                    pojo.setAcStatusDesc(acStatusDesc);
                    pojo.setAcType(acctDesc);
                    pojo.setAcStatus(acStatus);
                    pojo.setBalance(balance);
                    pojo.setChequeNo(chequeno);
                    pojo.setCrAdd(crAdd);
                    pojo.setCustName(custName);
                    pojo.setDate(date);
                    pojo.setDeposit(deposit);
                    pojo.setJtName(jtName1);
                    pojo.setNomination(nomName);
                    pojo.setOpeningBal(openBal);
                    pojo.setParticulars(particulars);
                    pojo.setPendingBal(pendBal);
                    pojo.setPrAdd(prAdd);
                    pojo.setValueDate(valdt);
                    pojo.setWithdrawl(withdrawl);
                    pojo.setAcNature(acNat);
                    pojo.setJtName2(jtName2);
                    pojo.setJtName3(jtName3);
                    pojo.setJtName4(jtName4);
                    pojo.setGstrn(gstn);
                    pojo.setOpeningDt(opDate);
                    pojo.setMobileNo(mobileNo);
                    returnList.add(pojo);
                }
                if (abbStatement.isEmpty()) {
                    AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                    pojo.setAcNo(acNo);
                    pojo.setPerStatus(PerStatus);
                    pojo.setAcStatusDesc(acStatusDesc);
                    pojo.setAcStatus(acStatus);
                    pojo.setAcType(acctDesc);
                    pojo.setCrAdd(crAdd);
                    pojo.setCustName(custName);
                    pojo.setJtName(jtName1);
                    pojo.setNomination(nomName);
                    pojo.setOpeningBal(openBal);
                    pojo.setPendingBal(pendBal);
                    pojo.setBalance(openBal);
                    pojo.setPrAdd(prAdd);
                    pojo.setAcNature(acNat);
                    pojo.setJtName2(jtName2);
                    pojo.setJtName3(jtName3);
                    pojo.setJtName4(jtName4);
                    pojo.setGstrn(gstn);
                    pojo.setOpeningDt(opDate);
                    pojo.setMobileNo(mobileNo);
                    returnList.add(pojo);
                }
                return returnList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    @Override
    public List<CustIdWiseAcStmt> getCustIdAccountStatementReport(String acNo, String fromDate, String toDate) throws ApplicationException {
        List<CustIdWiseAcStmt> returnList = new ArrayList<CustIdWiseAcStmt>();
        double openBal;
        try {
            List tempList = null;
            String query = "select bb.* from (select UPPER(cc.title) as title,aa.CustId as custId, atm.acctNature, atm.AcctCode as acctCode, UPPER(atm.acctdesc) as acctDesc,  "
                    + "LPAD(ifnull(bm.brncode,''), 2, '0') as brnCode, aa.Acno as acNo, ifnull(aa.Nomination,'') as nomination,  "
                    + "if(ifnull((select ifnull(code,'0') from parameterinfo_report where reportname='ROI-IN-DESC'),'0')='0', "
                    + "SUBSTRING_INDEX(if(ifnull(trim(LEADING '0' from  aa.instno),'')<>'',  "
                    + "concat(if(aa.ty='0', if(aa.details<>'',concat(CAST('By ' AS CHAR CHARACTER SET utf8),CAST(aa.details AS CHAR CHARACTER SET utf8)),CAST(aa.details AS CHAR CHARACTER SET utf8)), "
                    + "if(aa.details<>'',concat(CAST('To ' AS CHAR CHARACTER SET utf8),CAST(aa.details AS CHAR CHARACTER SET utf8)),aa.details)), "
                    + "concat(CAST(' CHQ DT:' AS CHAR CHARACTER SET utf8),CAST(aa.InstDt AS CHAR CHARACTER SET utf8), CAST(' CHQ No:' AS CHAR CHARACTER SET utf8),CAST(trim(LEADING '0' from  aa.instno) AS CHAR CHARACTER SET utf8)))   "
                    + ",if(aa.ty='0', if(aa.details<>'',concat('By ',aa.details),aa.details),if(aa.details<>'',concat('To ',aa.details),aa.details))),'@',1)  "
                    + ",if(ifnull(trim(LEADING '0' from  aa.instno),'')<>'',  "
                    + "concat(if(aa.ty='0', if(aa.details<>'',concat(CAST('By ' AS CHAR CHARACTER SET utf8), CAST(aa.details AS CHAR CHARACTER SET utf8)), "
                    + "CAST(aa.details AS CHAR CHARACTER SET utf8)),if(aa.details<>'',concat(CAST('To ' AS CHAR CHARACTER SET utf8), CAST(aa.details AS CHAR CHARACTER SET utf8)), "
                    + "CAST(aa.details AS CHAR CHARACTER SET utf8))), "
                    + "concat(CAST(' CHQ DT:' AS CHAR CHARACTER SET utf8),CAST(aa.InstDt AS CHAR CHARACTER SET utf8),  "
                    + "CAST(' CHQ No:' AS CHAR CHARACTER SET utf8),CAST(trim(LEADING '0' from  aa.instno) AS CHAR CHARACTER SET utf8)))   "
                    + ",if(aa.ty='0', if(aa.details<>'',concat(CAST('By ' AS CHAR CHARACTER SET utf8), CAST(aa.details AS CHAR CHARACTER SET utf8)), "
                    + "CAST(aa.details AS CHAR CHARACTER SET utf8)),if(aa.details<>'',concat(CAST('To ' AS CHAR CHARACTER SET utf8), CAST(aa.details AS CHAR CHARACTER SET utf8)),aa.details))))as details,   "
                    + "date_format(ifnull(aa.dt,'01/01/1900'),'%d/%m/%Y') as date ,date_format(ifnull(aa.valuedt,'01/01/1900'),'%d/%m/%Y') as valueDate, ifnull(trim(LEADING '0' from  aa.instno),'') as chequeNo, if(aa.InstDt='01/01/1900','',aa.InstDt) as instDt,  "
                    + "aa.crAmt, aa.drAmt,ifnull(aa.openingBal,0) as openingBal, ifnull(aa.closingBal,0) as closingBal,   "
                    + "(ifnull(aa.closingBal,0) - ifnull(aa.pendingBal,0)) as availableBalance, aa.recno,aa.ty,  "
                    + "aa.pendingBal, aa.balance, UPPER(ifnull(cc.CustFullName,'')) as custFullName,   "
                    + "UPPER(CONCAT(CAST(ifnull(cc.PerAddressLine1,'')  AS CHAR CHARACTER SET utf8) ,CAST(' ' AS CHAR CHARACTER SET utf8), CAST(ifnull(cc.peraddressline2,'') AS CHAR CHARACTER SET utf8) , "
                    + "CAST(' ' AS CHAR CHARACTER SET utf8), CAST(ifnull(cc.PerVillage,'') AS CHAR CHARACTER SET utf8) ,CAST(' ' AS CHAR CHARACTER SET utf8),  CAST(ifnull(cc.PerBlock,'') AS CHAR CHARACTER SET utf8) , CAST(' ' AS CHAR CHARACTER SET utf8),   "
                    + "CAST(ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001'  and REF_CODE = cc.PerCityCode),'') AS CHAR CHARACTER SET utf8) ,CAST(' ' AS CHAR CHARACTER SET utf8),   "
                    + "CAST(ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '002'  and REF_CODE = cc.PerStateCode),'') AS CHAR CHARACTER SET utf8),CAST(' ' AS CHAR CHARACTER SET utf8),   "
                    + "CAST(ifnull(cc.PerPostalCode,'')AS CHAR CHARACTER SET utf8)))  as address,  "
                    + "UPPER(ba.bankname) as bankname, UPPER(ba.bankaddress) as bankAddress, ifnull(bm.Pincode ,'') as brPin, ifnull(BrPhone,'') as brPhone,   "
                    + "ifnull(BrFax,'') as brFax,   "
                    + "concat(CAST(LPAD(ifnull(ba.micr,''), 3, '0') AS CHAR CHARACTER SET utf8),CAST(LPAD(ifnull(ba.micrcode,''), 3, '0') AS CHAR CHARACTER SET utf8),CAST(LPAD(ifnull(ba.branchcode,''), 3, '0')AS CHAR CHARACTER SET utf8)) as micrCode,   "
                    + " ifnull(bm.email, '') as email, ifnull(UPPER(bm.IFSC_CODE),'') as ifsc,   "
                    + "(select Concat(CAST(ifnull(address,'') AS CHAR CHARACTER SET utf8),if(ifnull(brPhone,'')='',CAST('' AS CHAR CHARACTER SET utf8),Concat(CAST(', Phone:' AS CHAR CHARACTER SET utf8),CAST(ifnull(brPhone,'') AS CHAR CHARACTER SET utf8)) )) as hoAdd from parameterinfo where brncode = 90) as hoAdd, "
                    + "aa.VoucherNo,ifnull(aa.PrinAmt,0) as PrinAmt,ifnull(aa.interest,0) as interest,ifnull(aa.tds,0) as tds,aa.Years,aa.Months,aa.Days,aa.Period, "
                    + "aa.roi, date_format(aa.matDt,'%d/%m/%Y') as matDt, date_format(aa.openingDt,'%d/%m/%Y') as openingDt  "
                    + "from cbs_customer_master_detail cc,   "
                    + "( "
                    + "select ci.CustId, ci.Acno, am.Nomination,r.details, "
                    + "date_format(ifnull(r.InstDt,'1900-01-01'),'%d/%m/%Y') as InstDt, r.instno, r.dt,r.valuedt, r.cramt, r.dramt, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ca_recon where auth= 'Y' and acno = r.acno and dt < '" + fromDate + "' and auth = 'Y') as openingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ca_recon where auth= 'Y' and acno = r.acno and dt <= '" + toDate + "' and auth = 'Y') as closingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ca_recon where auth= 'Y' and acno = r.acno and dt <= r.dt and auth = 'Y') as balance, "
                    + "(SELECT ifnull(SUM(txninstamt) ,0) from clg_ow_shadowbal where acno = r.acno AND DT <= '" + toDate + "') as pendingBal, r.recno, r.ty,  "
                    + "0 as VoucherNo,0 as PrinAmt,0 as interest,0 as tds,0 as Years,0 as Months,0 as Days,'' as Period, "
                    + "am.intDeposit as roi, date_format('19000101','%Y%m%d') as matDt, am.openingDt     "
                    + "from customerid ci, accountmaster am,  ca_recon r  "
                    + "where ci.acno = am.acno and r.acno = am.acno  "
                    + "and (am.ClosingDate is null or am.ClosingDate = '' or am.ClosingDate>'" + fromDate + "')  "
                    + "and ci.custid =  '" + acNo + "' and r.dt between '" + fromDate + "' and '" + toDate + "'  "
                    + "union all  "
                    + "select ci.CustId, ci.Acno, am.Nomination,r.details,  "
                    + " date_format(ifnull(r.InstDt,'1900-01-01'),'%d/%m/%Y') as InstDt,r.instno, r.dt, r.valuedt, r.cramt, r.dramt,  "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from recon where auth= 'Y' and acno = r.acno and dt < '" + fromDate + "' and auth = 'Y') as openingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from recon where auth= 'Y' and acno = r.acno and dt <= '" + toDate + "' and auth = 'Y') as closingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from recon where auth= 'Y' and acno = r.acno and dt <= r.dt and auth = 'Y') as balance, "
                    + "(SELECT ifnull(SUM(txninstamt) ,0) from clg_ow_shadowbal where acno = r.acno AND DT <= '" + toDate + "') as pendingBal, r.recno, r.ty,  "
                    + "0 as VoucherNo,0 as PrinAmt,0 as interest,0 as tds,0 as Years,0 as Months,0 as Days,'' as Period, "
                    + "am.intDeposit as roi, date_format('19000101','%Y%m%d') as matDt, am.openingDt          "
                    + "from customerid ci, accountmaster am,  recon r  "
                    + "where ci.acno = am.acno and r.acno = am.acno  "
                    + "and (am.ClosingDate is null or am.ClosingDate = '' or am.ClosingDate>'" + fromDate + "')  "
                    + "and ci.custid =  '" + acNo + "' and r.dt between '" + fromDate + "' and '" + toDate + "'  "
                    + "union all  "
                    + " "
                    + "select ci.CustId, ci.Acno, am.Nomination,'' as details,  "
                    + "date_format(ifnull('1900-01-01','1900-01-01'),'%d/%m/%Y') as InstDt, '' as instno, '19000101' as dt, '19000101' as valuedt, 0 as cramt, 0 as dramt,  "
                    + " 0 as openingBal,  "
                    + "(select cast((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))) as decimal(25,2)) from rdrecon where auth= 'Y' and acno = am.acno and dt <= '" + toDate + "' ) as closingBal,  "
                    + "0 as balance, 0 as pendingBal, 0 as recno, 0 ty, "
                    + "0 as VoucherNo, am.rdinstal as prinamt,  "
                    + "(select sum(interest) from rd_interesthistory where acno = ci.acno and dt <='20170630') as interest, "
                    + "(select sum(tds) from tdshistory where acno = ci.acno and dt <='20170630') as tds, "
                    + "0 as Years,0 as Months, 0 as Days, 0 as tenure, am.intdeposit as roi, date_format(am.rdmatdate,'%Y%m%d') as matDt, am.openingdt "
                    + "from customerid ci, accountmaster am  "
                    + "where ci.acno = am.acno and substring(am.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('RD')) "
                    + "and (am.ClosingDate is null or am.ClosingDate = '' or am.ClosingDate>'" + fromDate + "')  "
                    + "and ci.custid =  '" + acNo + "' group by am.acno  "
                    + "union all  "
                    + "select ci.CustId, ci.Acno, am.Nomination,r.details, "
                    + "date_format(ifnull(r.InstDt,'1900-01-01'),'%d/%m/%Y') as InstDt, r.instno, r.dt,r.valuedt, r.cramt, r.dramt, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = r.acno and dt < '" + fromDate + "') as openingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = r.acno and dt <= '" + toDate + "') as closingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = r.acno and dt <= r.dt) as balance, "
                    + "(SELECT ifnull(SUM(txninstamt) ,0) from clg_ow_shadowbal where acno = r.acno AND DT <= '" + toDate + "') as pendingBal, r.recno, r.ty,  "
                    + "0 as VoucherNo,0 as PrinAmt,0 as interest,0 as tds,0 as Years,0 as Months,0 as Days,'' as Period, "
                    + "am.intDeposit as roi, date_format('19000101','%Y%m%d') as matDt, am.openingDt           "
                    + "from customerid ci, accountmaster am,  loan_recon r  "
                    + "where ci.acno = am.acno and r.acno = am.acno  "
                    + "and (am.ClosingDate is null or am.ClosingDate = '' or am.ClosingDate>'" + fromDate + "')  "
                    + "and ci.custid =  '" + acNo + "' and r.dt between '" + fromDate + "' and '" + toDate + "'  "
                    + "union all  "
                    + "select ci.CustId, ci.Acno, am.Nomination,r.details, "
                    + "date_format(ifnull(r.InstDt,'1900-01-01'),'%d/%m/%Y') as InstDt, r.instno, r.dt,r.valuedt, r.cramt, r.dramt, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ddstransaction where auth= 'Y' and acno = r.acno and dt < '" + fromDate + "' and auth = 'Y') as openingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ddstransaction where auth= 'Y' and acno = r.acno and dt <= '" + toDate + "' and auth = 'Y') as closingBal, "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ddstransaction where auth= 'Y' and acno = r.acno and dt <= r.dt and auth = 'Y') as balance, "
                    + "(SELECT ifnull(SUM(txninstamt) ,0) from clg_ow_shadowbal where acno = r.acno AND DT <= '" + toDate + "') as pendingBal, r.recno, r.ty,  "
                    + "0 as VoucherNo,0 as PrinAmt,0 as interest,0 as tds,0 as Years,0 as Months,0 as Days,'' as Period, "
                    + "am.intDeposit as roi, date_format('19000101','%Y%m%d') as matDt, am.openingDt        "
                    + "from customerid ci,  accountmaster am,  ddstransaction r  "
                    + "where ci.acno = am.acno and r.acno = am.acno  "
                    + "and (am.ClosingDate is null or am.ClosingDate = '' or am.ClosingDate>'" + fromDate + "')  "
                    + "and ci.custid =  '" + acNo + "' and r.dt between '" + fromDate + "' and '" + toDate + "'  "
                    + "union all  "
                    + "select ci.CustId, ci.Acno, am.Nomination,'' as details,  "
                    + "date_format(ifnull('1900-01-01','1900-01-01'),'%d/%m/%Y') as InstDt, '' as instno, '19000101' as dt, '19000101' as valuedt, 0 as cramt, 0 as dramt,  "
                    + " 0 as openingBal,  "
                    + "(select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = am.acno and closeflag is null  "
                    + "and Trantype<> 27 and auth='y' and dt <= '20170717') as closingBal,  "
                    + "0 as balance,  "
                    + "0 as pendingBal, 0 as recno, 0 ty,v.VoucherNo, v.prinamt,  "
                    + "(select sum(interest) from td_interesthistory where acno = ci.acno and VoucherNo = v.VoucherNo and dt <='20170630') as interest, "
                    + "(select sum(tds) from tdshistory where acno = ci.acno and VoucherNo = v.VoucherNo and dt <='20170630') as tds, "
                    + "Years,Months,Days, Period as tenure,v.roi as roi, date_format(v.matDt,'%Y%m%d') as matDt, date_format(v.fddt,'%Y%m%d') as openingDt "
                    + " "
                    + "from customerid ci, td_accountmaster am, td_vouchmst v   "
                    + "where  ci.acno = am.acno and am.acno = v.acno and substring(am.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('FD','MS')) "
                    + "and (am.ClosingDate is null or am.ClosingDate = '' or am.ClosingDate>'" + fromDate + "')  "
                    + "and (date_format(v.ClDt,'%Y%m%d') is null or date_format(v.ClDt,'%Y%m%d')  = '' or date_format(v.ClDt,'%Y%m%d') >'" + fromDate + "')  "
                    + "and ci.custid =  '" + acNo + "' group by ci.CustId, ci.Acno,v.VoucherNo ) aa, accounttypemaster atm, branchmaster bm, bnkadd ba, parameterinfo pi  "
                    + "where aa.CustId = cc.customerid  "
                    + " and substring(aa.Acno,3,2) = atm.AcctCode and substring(aa.Acno,1,2) = LPAD(ifnull(bm.brncode,''), 2, '0') and   "
                    + "bm.AlphaCode = ba.alphacode   "
                    + "and pi.brncode = bm.brncode  "
                    + "order by  bm.brnCode, atm.acctNature, atm.AcctCode, aa.Acno, aa.dt, aa.recno) bb, cbs_service_detail cs where  "
                    + "bb.CustId = cs.customer_id and bb.acno = cs.acno ";
            List CustIdList = em.createNativeQuery(query).getResultList();
//            System.out.println("Query>>>" + query);
            if (!CustIdList.isEmpty()) {
                for (int i = 0; i < CustIdList.size(); i++) {
//                    System.out.println("i:"+i);
                    Vector custIdWiseVect = (Vector) CustIdList.get(i);
                    CustIdWiseAcStmt pojo = new CustIdWiseAcStmt();
                    pojo.setTitle(custIdWiseVect.get(0).toString());
                    pojo.setCustId(Integer.parseInt(custIdWiseVect.get(1).toString()));
                    pojo.setAcctNature(custIdWiseVect.get(2).toString());
                    pojo.setAcctCode(custIdWiseVect.get(3).toString());
                    pojo.setAcctDesc(custIdWiseVect.get(4).toString());
                    pojo.setBrnCode(custIdWiseVect.get(5).toString());
                    pojo.setAcNo(custIdWiseVect.get(6).toString());
                    pojo.setNomination(custIdWiseVect.get(7).toString());
                    pojo.setDetails(custIdWiseVect.get(8).toString());
                    pojo.setDate(custIdWiseVect.get(9).toString());
                    pojo.setValueDate(custIdWiseVect.get(10).toString());
                    pojo.setChequeNo(custIdWiseVect.get(11).toString());
                    pojo.setInstDt(custIdWiseVect.get(12).toString());
                    pojo.setCrAmt(Double.parseDouble(custIdWiseVect.get(13).toString()));
                    pojo.setDrAmt(Double.parseDouble(custIdWiseVect.get(14).toString()));
                    pojo.setOpeningBal(Double.parseDouble(custIdWiseVect.get(15).toString()));
                    pojo.setClosingBal(Double.parseDouble(custIdWiseVect.get(16).toString()));
                    pojo.setAvailableBalance(Double.parseDouble(custIdWiseVect.get(17).toString()));
                    pojo.setRecno(Double.parseDouble(custIdWiseVect.get(18).toString()));
                    pojo.setTy(Integer.parseInt(custIdWiseVect.get(19).toString()));
                    pojo.setPendingBal(Double.parseDouble(custIdWiseVect.get(20).toString()));
                    pojo.setBalance(Double.parseDouble(custIdWiseVect.get(21).toString()));
                    pojo.setCustFullName(custIdWiseVect.get(22).toString());
                    pojo.setAddress(custIdWiseVect.get(23).toString());
                    pojo.setBankName(custIdWiseVect.get(24).toString());
                    pojo.setBankAddress(custIdWiseVect.get(25).toString());
                    pojo.setBrPin(custIdWiseVect.get(26).toString());
                    pojo.setBrPhone(custIdWiseVect.get(27).toString());
                    pojo.setBrFax(custIdWiseVect.get(28).toString());
                    pojo.setMicrCode(custIdWiseVect.get(29).toString());
                    pojo.setEmail(custIdWiseVect.get(30).toString());
                    pojo.setIfsc(custIdWiseVect.get(31).toString());
                    pojo.setHoAdd(custIdWiseVect.get(32).toString());
                    pojo.setVoucherNo(Double.parseDouble(custIdWiseVect.get(33).toString()) == 0.0 ? 0 : Double.parseDouble(custIdWiseVect.get(33).toString()));
                    pojo.setPrinAmt(Double.parseDouble(custIdWiseVect.get(34).toString()));
                    pojo.setInterest(Double.parseDouble(custIdWiseVect.get(35).toString()));
                    pojo.setTds(Double.parseDouble(custIdWiseVect.get(36).toString()));
                    pojo.setYears(Integer.parseInt(custIdWiseVect.get(37).toString()));
                    pojo.setMonths(Integer.parseInt(custIdWiseVect.get(38).toString()));
                    pojo.setDays(Integer.parseInt(custIdWiseVect.get(39).toString()));
                    if (custIdWiseVect.get(2).toString().equalsIgnoreCase("RD")) {
                        List arr = CbsUtil.getYrMonDayDiff(ymd.format(dmyy.parse(custIdWiseVect.get(43).toString())), ymd.format(dmyy.parse(custIdWiseVect.get(42).toString())));
                        if (!arr.isEmpty()) {
                            pojo.setPeriod(arr.get(0).toString().concat("Year").concat((arr.get(1).toString().equalsIgnoreCase("0") ? "" : (" " + arr.get(1).toString().concat("Month"))).concat((arr.get(2).toString().equalsIgnoreCase("0") ? "" : (" " + arr.get(1).toString().concat("Days"))))));
                        }
                    } else {
                        pojo.setPeriod(custIdWiseVect.get(40).toString());
                    }

                    pojo.setRoi(Double.parseDouble(custIdWiseVect.get(41).toString()));
                    pojo.setMatDt(custIdWiseVect.get(42).toString());
                    pojo.setOpeningDt(custIdWiseVect.get(43).toString());

                    returnList.add(pojo);
                }
                if (CustIdList.isEmpty()) {
                    CustIdWiseAcStmt pojo = new CustIdWiseAcStmt();
                    pojo.setTitle("");
                    pojo.setCustId(0);
                    pojo.setAcctNature("");
                    pojo.setAcctCode("");
                    pojo.setAcctDesc("");
                    pojo.setBrnCode("");
                    pojo.setAcNo("");
                    pojo.setNomination("");
                    pojo.setDetails("");
                    pojo.setDate("");
                    pojo.setValueDate("");
                    pojo.setChequeNo("");
                    pojo.setInstDt("");
                    pojo.setCrAmt(0d);
                    pojo.setDrAmt(0d);
                    pojo.setOpeningBal(0d);
                    pojo.setClosingBal(0d);
                    pojo.setAvailableBalance(0d);
                    pojo.setRecno(0d);
                    pojo.setTy(0);
                    pojo.setPendingBal(0d);
                    pojo.setBalance(0d);
                    pojo.setCustFullName("");
                    pojo.setAddress("");
                    pojo.setBankName("");
                    pojo.setBankAddress("");
                    pojo.setBrPin("");
                    pojo.setBrPhone("");
                    pojo.setBrFax("");
                    pojo.setMicrCode("");
                    pojo.setEmail("");
                    pojo.setIfsc("");
                    pojo.setHoAdd("");
                    pojo.setVoucherNo(0d);
                    pojo.setPrinAmt(0d);
                    pojo.setInterest(0d);
                    pojo.setTds(0d);
                    pojo.setYears(0);
                    pojo.setMonths(0);
                    pojo.setDays(0);
                    pojo.setPeriod("");
                    pojo.setRoi(0d);
                    pojo.setMatDt("");
                    pojo.setOpeningDt("");
                    returnList.add(pojo);
                }
                return returnList;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return null;
    }

    /**
     * ***************** Account Open Register ***********************
     */
    /**
     *
     * @param acCode
     * @param fromDt
     * @param toDt
     * @param orgnBrId
     * @return
     */
    @Override
    public List<AccountOpenRegisterPojo> accountOpenRegister(String acCode, String fromDt, String toDt, String orgnBrId, String acNature) throws ApplicationException {
        try {
            String intNo = "";
            List acctopen = new ArrayList();
            String introducerName = "";
            List objBan = common.getBranchNameandAddress(orgnBrId.equalsIgnoreCase("0A") ? "90" : orgnBrId);
            String bnkName = null, bnkAddress = null;
            double amount = 0;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acNatQuery = " and substring(am.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "')) ";
//            String acNat = common.getAcNatureByAcType(acCode);
            List<AccountOpenRegisterPojo> ListOpen = new ArrayList<AccountOpenRegisterPojo>();
            if (acCode.equalsIgnoreCase("ALL")) {
                List acopen;
                if (orgnBrId.equalsIgnoreCase("0A")) {

//                    acopen = em.createNativeQuery("SELECT date_format(am.openingdt,'%Y%m%d'),am.acno,am.custname,cm.FatherName, am.JtName1,cm.prAddress,cb.description,"
//                            + "am.introaccno,am.curbrcode,am.accttype FROM td_accountmaster am,codebook cb,td_customermaster cm WHERE am.openingDt "
//                            + "between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype and "
//                            + "substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode  and am.curbrcode = cm.brncode"
//                            + " union "
//                            + "SELECT am.openingdt,am.acno,am.custname,cm.FatherName,am.JtName1,cm.prAddress,cb.description,am.introaccno,am.curbrcode,"
//                            + "am.accttype FROM accountmaster am, codebook cb,customermaster cm WHERE am.openingDt between '" + fromDt + "' and '"
//                            + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno "
//                            + "and substring(am.acno,11,2)=cm.agcode  and am.curbrcode = cm.brncode order by curbrcode,accttype").getResultList();
                    acopen = em.createNativeQuery("select a.opdt,a.acno,a.custname,a.FatherName, a.JtName1,a.prAddress,a.description,a.introaccno,a.curbrcode,"
                            + "a.accttype,a.EnteredBy,a.AuthBy from(SELECT date_format(am.openingdt,'%Y%m%d') as opdt,am.acno as acno,am.custname as custname,cm.FatherName as FatherName, "
                            + "am.JtName1 as JtName1,cm.prAddress as prAddress,cb.description as description,am.introaccno as introaccno,am.curbrcode as curbrcode,"
                            + "am.accttype as accttype,am.EnteredBy,am.AuthBy FROM td_accountmaster am,codebook cb,td_customermaster cm WHERE am.openingDt between '" + fromDt + "' and '" + toDt + "' "
                            + "and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno "
                            + "and substring(am.acno,11,2)=cm.agcode  and am.curbrcode = cm.brncode " + acNatQuery + " "
                            + "union "
                            + "SELECT am.openingdt as openingdt,am.acno as acno,"
                            + "am.custname as custname,cm.FatherName as FatherName,am.JtName1 as JtName1,cm.prAddress as prAddress,cb.description as description,"
                            + "am.introaccno as introaccno,am.curbrcode as curbrcode,am.accttype as accttype,am.EnteredBy,am.AuthBy FROM accountmaster am, codebook cb,customermaster cm "
                            + "WHERE am.openingDt between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype "
                            + "and substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode  and am.curbrcode = cm.brncode " + acNatQuery + " )a "
                            + "order by a.acno,a.opdt").getResultList();
                } else {
//                    acopen = em.createNativeQuery("SELECT date_format(am.openingdt,'%Y%m%d'),am.acno,am.custname,cm.FatherName, am.JtName1,cm.prAddress,cb.description,"
//                            + "am.introaccno,am.curbrcode,am.accttype FROM td_accountmaster am,codebook cb,td_customermaster cm WHERE am.openingDt "
//                            + "between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype and "
//                            + "substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode and am.curbrcode ='" + orgnBrId + "' and "
//                            + "am.curbrcode = cm.brncode "
//                            + "union "
//                            + "SELECT am.openingdt,am.acno,am.custname,cm.FatherName,am.JtName1,cm.prAddress, cb.description,am.introaccno,am.curbrcode,"
//                            + "am.accttype FROM accountmaster am, codebook cb,customermaster cm WHERE am.openingDt between '" + fromDt + "' and '"
//                            + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno "
//                            + "and substring(am.acno,11,2)=cm.agcode and am.curbrcode = '" + orgnBrId + "' and am.curbrcode = cm.brncode "
//                            + "order by curbrcode,accttype").getResultList();

                    acopen = em.createNativeQuery("select a.opdt,a.acno,a.custname,a.FatherName, a.JtName1,a.prAddress,a.description,a.introaccno,"
                            + "a.curbrcode,a.accttype,a.EnteredBy,a.AuthBy from"
                            + "(SELECT date_format(am.openingdt,'%Y%m%d') as opdt,am.acno as acno,am.custname as custname,cm.FatherName as FatherName, "
                            + "am.JtName1 as JtName1,cm.prAddress as prAddress,cb.description as description,am.introaccno as introaccno,am.curbrcode as curbrcode ,"
                            + "am.accttype as accttype,am.EnteredBy,am.AuthBy FROM td_accountmaster am,codebook cb,td_customermaster cm WHERE am.openingDt between '" + fromDt + "' and '" + toDt + "' "
                            + "and cb.code=am.OPERMODE and cb.groupcode=4 and am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno "
                            + "and substring(am.acno,11,2)=cm.agcode and am.curbrcode ='" + orgnBrId + "' and am.curbrcode = cm.brncode " + acNatQuery + " "
                            + "union "
                            + "SELECT am.openingdt as opdt,am.acno as acno,am.custname as custname,cm.FatherName as FatherName,am.JtName1 as JtName1,"
                            + "cm.prAddress as prAddress, cb.description as description,am.introaccno as introaccno,am.curbrcode as curbrcode,am.accttype as accttype,am.EnteredBy,am.AuthBy "
                            + "FROM accountmaster am, codebook cb,customermaster cm WHERE am.openingDt between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE "
                            + "and cb.groupcode=4 and am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode "
                            + "and am.curbrcode = '" + orgnBrId + "' and am.curbrcode = cm.brncode  " + acNatQuery + " )a order by a.acno,a.opdt").getResultList();
                }
                if (acopen.size() > 0) {
                    for (int i = 0; i < acopen.size(); i++) {
                        Vector record = (Vector) acopen.get(i);
                        AccountOpenRegisterPojo pojo = new AccountOpenRegisterPojo();
                        pojo.setBankname(bnkName);
                        pojo.setBankaddress(bnkAddress);
                        pojo.setOpDt(record.get(0).toString().substring(6, 8) + "/" + record.get(0).toString().substring(4, 6) + "/" + record.get(0).toString().substring(0, 4));
                        pojo.setACNO(record.get(1).toString());
                        pojo.setAcDesc(common.getAcctDecription(record.get(1).toString().substring(2, 4)));
                        pojo.setCUSTNAME(record.get(2).toString());
                        pojo.setFHNAME(record.get(3).toString());
                        pojo.setJTNAME(record.get(4).toString());
                        pojo.setADDRESS(record.get(5).toString());
                        pojo.setOPMODE(record.get(6).toString());
                        pojo.setEnterBy(record.get(10).toString());
                        pojo.setAuthBy(record.get(11).toString());
                        amount = loanReport.openingBalance(record.get(1).toString(), toDt);
                        pojo.setBalance(new BigDecimal(amount));
                        if (record.get(7) != null && record.get(7).toString().length() == 12) {
                            intNo = record.get(7).toString();
                            pojo.setINTRONO(record.get(7).toString());
                            if (intNo != null && !intNo.equalsIgnoreCase("")) {
                                List intList = em.createNativeQuery("select custname from td_accountmaster where acno = '" + intNo + "'").getResultList();
                                if (intList.size() > 0) {
                                    Vector vect = (Vector) intList.get(0);
                                    if (vect.get(0) != null) {
                                        introducerName = vect.get(0).toString();
                                    }
                                } else {
                                    List intList1 = em.createNativeQuery("select custname from accountmaster where acno = '" + intNo + "'").getResultList();
                                    if (intList1.size() > 0) {
                                        Vector vect = (Vector) intList1.get(0);
                                        if (vect.get(0) != null) {
                                            introducerName = vect.get(0).toString();
                                        }
                                    }
                                }
                                pojo.setINTRONAME(introducerName);
                            }
                        } else {
                            pojo.setINTRONO("");
                            pojo.setINTRONAME("");
                        }
                        ListOpen.add(pojo);
                    }
                }
            } else {
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (orgnBrId.equalsIgnoreCase("0A")) {
                        acctopen = em.createNativeQuery("SELECT am.openingdt,am.acno,am.custname,cm.FatherName,am.JtName1,cm.prAddress, cb.description,"
                                + "am.introaccno,am.EnteredBy,am.AuthBy From td_accountmaster am, codebook cb, td_customermaster cm where am.accttype='" + acCode
                                + "' and am.openingDt between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and "
                                + "am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode and am.curbrcode = "
                                + "cm.brncode order by am.curbrcode,am.accttype").getResultList();
                    } else {
                        acctopen = em.createNativeQuery("SELECT am.openingdt,am.acno,am.custname,cm.FatherName,am.JtName1,cm.prAddress,cb.description,"
                                + "am.introaccno,am.EnteredBy,am.AuthBy From td_accountmaster am, codebook cb, td_customermaster cm where am.accttype='" + acCode
                                + "' and am.openingDt between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and "
                                + "am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode and am.curbrcode = '"
                                + orgnBrId + "' and am.curbrcode = cm.brncode order by am.curbrcode,am.accttype").getResultList();
                    }
                } else {
                    if (orgnBrId.equalsIgnoreCase("0A")) {
                        acctopen = em.createNativeQuery("SELECT am.openingdt,am.acno,am.custname,cm.FatherName,am.JtName1,cm.prAddress,cb.description,"
                                + "am.introaccno,am.EnteredBy,am.AuthBy From accountmaster am, codebook cb, customermaster cm where am.accttype='" + acCode
                                + "' and am.openingDt between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and "
                                + "am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode and am.curbrcode = "
                                + "cm.brncode order by am.curbrcode,am.accttype").getResultList();
                    } else {
                        acctopen = em.createNativeQuery("SELECT am.openingdt,am.acno,am.custname,cm.FatherName,am.JtName1,cm.prAddress,cb.description,"
                                + "am.introaccno,am.EnteredBy,am.AuthBy From accountmaster am, codebook cb, customermaster cm where am.accttype='" + acCode
                                + "' and am.openingDt between '" + fromDt + "' and '" + toDt + "' and cb.code=am.OPERMODE and cb.groupcode=4 and "
                                + "am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and substring(am.acno,11,2)=cm.agcode and am.curbrcode = '"
                                + orgnBrId + "' and am.curbrcode = cm.brncode order by am.curbrcode,am.accttype").getResultList();
                    }
                }
            }
            if (acctopen.size() > 0) {
                for (int i = 0; i < acctopen.size(); i++) {
                    Vector record = (Vector) acctopen.get(i);
                    AccountOpenRegisterPojo pojo = new AccountOpenRegisterPojo();
                    pojo.setBankname(bnkName);
                    pojo.setBankaddress(bnkAddress);
                    //  pojo.setDT(ymdFormat.parse(record.get(0).toString()));
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        pojo.setOpDt(record.get(0).toString().substring(8, 10) + "/" + record.get(0).toString().substring(5, 7) + "/" + record.get(0).toString().substring(0, 4));
                    } else {
                        pojo.setOpDt(record.get(0).toString().substring(6, 8) + "/" + record.get(0).toString().substring(4, 6) + "/" + record.get(0).toString().substring(0, 4));
                    }
                    pojo.setACNO(record.get(1).toString());
                    pojo.setAcDesc(common.getAcctDecription(record.get(1).toString().substring(2, 4)));
                    pojo.setCUSTNAME(record.get(2).toString());
                    pojo.setFHNAME(record.get(3).toString());
                    pojo.setJTNAME(record.get(4).toString());
                    pojo.setADDRESS(record.get(5).toString());
                    pojo.setOPMODE(record.get(6).toString());
                    pojo.setEnterBy(record.get(8).toString());
                    pojo.setAuthBy(record.get(9).toString());
                    amount = loanReport.openingBalance(record.get(1).toString(), toDt);
                    pojo.setBalance(new BigDecimal(amount));
                    if (record.get(7) != null && record.get(7).toString().length() == 12) {
                        intNo = record.get(7).toString();
                        pojo.setINTRONO(record.get(7).toString());
                        if (intNo != null && !intNo.equalsIgnoreCase("")) {
                            List intList = em.createNativeQuery("SELECT CUSTNAME FROM td_accountmaster WHERE ACNO = '" + intNo + "'").getResultList();
                            if (intList.size() > 0) {
                                Vector vect = (Vector) intList.get(0);
                                if (vect.get(0) != null) {
                                    introducerName = vect.get(0).toString();
                                }
                            } else {
                                List intList1 = em.createNativeQuery("SELECT CUSTNAME FROM accountmaster WHERE ACNO = '" + intNo + "'").getResultList();
                                if (intList1.size() > 0) {
                                    Vector vect = (Vector) intList1.get(0);
                                    if (vect.get(0) != null) {
                                        introducerName = vect.get(0).toString();
                                    }
                                }
                            }
                            pojo.setINTRONAME(introducerName);
                        }
                    } else {
                        pojo.setINTRONO("");
                        pojo.setINTRONAME("");
                    }

                    ListOpen.add(pojo);
                }
            }
            return ListOpen;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * *************** CA DEBIT BALANCE REPORT ***********************
     */
    @Override
    public List<CADebitBalancePojo> CADebitBalance(String dt, String accountType, String brnCode) throws ApplicationException {

        List<CADebitBalancePojo> currentDebitList = new ArrayList<CADebitBalancePojo>();
        List objBan = new ArrayList();
        try {
            List result = new ArrayList();
            if (brnCode.equalsIgnoreCase("0A")) {
                objBan = common.getBranchNameandAddress("90");
            } else {
                objBan = common.getBranchNameandAddress(brnCode);
            }
            //     List objBan = common.getBranchNameandAddress(brnCode);
            String bnkName = null, bnkAddress = null;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acctNature = common.getAcNatureByAcType(accountType);
            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select c.acno,custname,round(ifnull(sum(cramt),0)-sum(ifnull(dramt,0)),2) from ca_recon c,"
                            + "accountmaster a where a.acno = c.acno and dt <='" + dt + "' and a.accttype ='" + accountType + "' group by c.acno,"
                            + "a.custname having ROUND(ifnull(sum(cramt),0),2)< ROUND(ifnull(sum(dramt),0),2) order by c.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select c.acno,custname,round(ifnull(sum(cramt),0)-sum(ifnull(dramt,0)),2) from ca_recon c ,"
                            + "accountmaster a where a.acno = c.acno and dt <='" + dt + "' and a.accttype ='" + accountType + "' AND a.curbrcode ='"
                            + brnCode + "' group by c.acno,a.custname having ROUND(ifnull(sum(cramt),0),2)< ROUND(ifnull(sum(dramt),0),2) "
                            + "order by c.acno").getResultList();
                }

                if (result.size() > 0) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector record = (Vector) result.get(i);
                        CADebitBalancePojo balCert = new CADebitBalancePojo();
                        balCert.setAccountNo(record.get(0).toString());
                        balCert.setCustName(record.get(1).toString());
                        balCert.setBalance(new BigDecimal(nft.format(Double.parseDouble(record.get(2).toString()))));
                        balCert.setBnkName(bnkName);
                        balCert.setBnkAddress(bnkAddress);
                        currentDebitList.add(balCert);
                    }
                }
            }
            return currentDebitList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getAccDescriptionByAcctCode(String code) throws ApplicationException {
        String desc = "";
        try {
            List resultList = em.createNativeQuery("select AcctDesc from accounttypemaster where acctcode='" + code + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector vec = (Vector) resultList.get(0);
                desc = vec.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return desc;
    }

    /**
     * **************************************** INOPERATIVE MARKING
     * ******************************
     */
    @Override
    public List<InoperativeMarkingPojo> inoperativeMarking(String acType, String date, int days, String brCode) throws ApplicationException {
        List<InoperativeMarkingPojo> inoperativeMarkingPojos = new ArrayList<InoperativeMarkingPojo>();
        try {
            List result = new ArrayList();
            List objBan = common.getBranchNameandAddress(brCode);
            String bnkName = null, bnkAddress = null, tablename = "";
            double amount = 0d;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acctNature = common.getAcNatureByAcType(acType);
            if (acctNature.equalsIgnoreCase("CA")) {
                result = em.createNativeQuery("SELECT distinct r.acno,date_format(max(dt),'%d/%m/%Y'),custname,timestampdiff(day,max(dt),'" + date + "') "
                        + "from ca_recon r,accountmaster a where SUBSTRING(A.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8  "
                        + "AND trandesc<>33 and a.accstatus <> 9 and optstatus <> 2 and dt <= '" + date + "' and a.acno in( select acno from ca_recon "
                        + "where trantype <> 8 group by acno having (timestampdiff(day,max(dt),'" + date + "' ) > '" + days + "')) AND SUBSTRING (a.acno,1,2) ='" + brCode + "' "
                        + "group by r.acno,a.custname order by r.acno").getResultList();
            } else {
                result = em.createNativeQuery("SELECT distinct r.acno,date_format(max(dt),'%d/%m/%Y'),custname,timestampdiff(day,max(dt),'" + date + "') from "
                        + "recon r,accountmaster a  where SUBSTRING(A.ACNO,3,2)='" + acType + "' AND r.acno = a.acno and trantype <> 8 AND "
                        + "trandesc<>33 and a.accstatus <> 9 and optstatus <> 2 and dt <= '" + date + "' and a.acno in( select acno "
                        + "from recon where trantype <> 8 group by acno having (timestampdiff(day,max(dt),'" + date + "' ) > '" + days + "')) "
                        + "AND SUBSTRING (a.acno,1,2) ='" + brCode + "' group by r.acno,a.custname order by r.acno").getResultList();
            }
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    InoperativeMarkingPojo balCert = new InoperativeMarkingPojo();
                    balCert.setACNO(record.get(0).toString());
                    balCert.setDT(record.get(1).toString());
                    balCert.setCUSTNAME(record.get(2).toString());
                    balCert.setDAYDIFF(record.get(3).toString());
                    if (acctNature.equalsIgnoreCase("CA")) {
                        tablename = "ca_recon";
                    } else {
                        tablename = "RECON";
                    }
                    List amountList = em.createNativeQuery("SELECT ROUND(SUM(CRAMT-DRAMT),2) FROM " + tablename + " WHERE ACNO ='" + record.get(0).toString() + "' AND DT <='" + date + "'").getResultList();
                    if (!amountList.isEmpty()) {
                        Vector balanceVect = (Vector) amountList.get(0);
                        amount = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    balCert.setBALANCE(amount);
                    balCert.setBNKNAME(bnkName);
                    balCert.setBNKADD(bnkAddress);
                    inoperativeMarkingPojos.add(balCert);
                }
            }
            return inoperativeMarkingPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getAccountDetails(String acno) throws ApplicationException {
        List returnList = null;
        try {
            List resultList = em.createNativeQuery("SELECT date_format(From_dt ,'%d/%m/%Y'),date_format(To_dt,'%d/%m/%Y'),Printedby,"
                    + "date_format(Trantime,'%b %d %Y %T:%p') FROM accstatement_hist where Acno='" + acno + "'").getResultList();
            if (!resultList.isEmpty()) {
                returnList = new ArrayList();
                Vector ele = (Vector) resultList.get(0);
                for (int i = 0; i < ele.size(); i++) {
                    returnList.add(ele.get(i));
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public String checkAcStatementChargeOpt(String checkOrigin) throws ApplicationException {
        try {
            List acChargeCode = new ArrayList();
            if (checkOrigin.equalsIgnoreCase("true")) {
                acChargeCode = em.createNativeQuery("select code from parameterinfo_report where reportname='O.B A/C STATEMENT CHARGES'").getResultList();
            } else {
                acChargeCode = em.createNativeQuery("select code from parameterinfo_report where reportname='ACCOUNT STATEMENT CHARGES'").getResultList();
            }
            if (acChargeCode.isEmpty()) {
                return "false";
            } else {
                Vector v1 = (Vector) acChargeCode.get(0);
                int code = Integer.parseInt(v1.get(0).toString());
                if (code == 1) {
                    return "true";
                } else {
                    return "false";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String originBranchAccountCharges(String acno, int rows, String orgBrnCode, String username, String fromdt, String toDt, int pages) throws ApplicationException {
        double accountStatementCharges, taxPercentage, taxPercentageAmount, totalAccountStatementCharges;
        String errorMessage = "Failed!!! Problem in deducting charge process for origin branch";
        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            ut.begin();
            List acChargeCode = em.createNativeQuery("select code from parameterinfo_report where reportname='O.B A/C STATEMENT CHARGES'").getResultList();
            if (acChargeCode.isEmpty()) {
                ut.rollback();
                return "Account statement charges is not defined for origin Branch account";
            } else {
                Vector v1 = (Vector) acChargeCode.get(0);
                int code = Integer.parseInt(v1.get(0).toString());
                if (code == 0) {
                    ut.rollback();
                    return "Account statement charges for origin branch is off";
                } else {
                    List returnList = getParameters(acno, rows, orgBrnCode, fromdt, toDt, pages);
                    if (returnList.get(0).toString().equalsIgnoreCase("0")) {
                        ut.rollback();
                        return "Account statement charges is not defined,either for account code or for your branch";
                    }
                    if (returnList.get(0).toString().equalsIgnoreCase("1")) {
                        ut.rollback();
                        return "Duplicate statement charges is applicable but applied type is not defined";
                    }
                    if (returnList.get(0).toString().equalsIgnoreCase("2")) {
                        ut.rollback();
                        return "Duplicate statement charges is applicable , but not defined for either account code or for your branch";

                    }
                    accountStatementCharges = Double.parseDouble(returnList.get(0).toString());
//                    taxPercentage = Double.parseDouble(returnList.get(1).toString());
//                    taxPercentageAmount = Double.parseDouble(returnList.get(2).toString());
//                    totalAccountStatementCharges = Double.parseDouble(returnList.get(3).toString());
                    String serviceChargeAccount = returnList.get(1).toString();
//                    String serviceTaxEducationCess = returnList.get(5).toString();
//                    Double duplicateCharges = Double.parseDouble(returnList.get(6).toString());

                    String dt = ymdFormat.format(new Date());
                    /*
                     * For Service Tax
                     */
                    List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname like 'STAXMODULE_ACTIVE'").getResultList();
                    Vector element4 = (Vector) listCode.get(0);
                    Integer sCode = Integer.parseInt(element4.get(0).toString());
                    double sTax = 0d;
                    if (sCode == 1) {
                        map = sxcxBean.getTaxComponent(accountStatementCharges, dt);
                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            sTax = sTax + Double.parseDouble(entry.getValue().toString());
                        }
                    }

                    float recno = ftsBean.getRecNo();
                    float trsno = ftsBean.getTrsNo();

                    /**
                     * ********************TRANSFER BATCH
                     * ENTRY***************************************
                     */
                    Query Q = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                            + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                            + "dest_brnid,tran_id,adviceno,advicebrncode)"
                            + "values ('" + serviceChargeAccount + "','ACCOUNT STATEMENT CHARGE','" + dt + "','" + dt + "'," + accountStatementCharges + ",0.0,0,2," + recno + ",'','" + dt + "',3,0,'Y','" + username + "',0,0,'A',' ACCOUNT STATEMENT CHARGE','SYSTEM'," + trsno + ",now(),'','" + orgBrnCode + "','" + orgBrnCode + "',0,'','')");
                    int rowAffected = Q.executeUpdate();
                    if (rowAffected <= 0) {
                        ut.rollback();
                        return errorMessage;
                    }

                    String glAcNature = common.getAcNatureByAcNo(serviceChargeAccount);
                    String result = ftsBean.insertRecons(glAcNature, serviceChargeAccount, 0, accountStatementCharges, dt, dt, 2, " GST FOR A/C STATEMENT CHARGES", username, trsno, null, recno, "Y", "SYSTEM", null, 3, null, null, null, null, null, 1, null, null, null, null, orgBrnCode, orgBrnCode, null, null, "", "");
                    if (!result.equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return errorMessage;
                    }

                    recno = ftsBean.getRecNo();
                    List custNameList = em.createNativeQuery("select custname from accountmaster where acno='" + acno + "'").getResultList();
                    if (custNameList.isEmpty()) {
                        ut.rollback();
                        return "Customer data does not exist of account no" + acno;
                    }
                    Vector custNameVec = (Vector) custNameList.get(0);
                    String custName = custNameVec.get(0).toString();
                    Q = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                            + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                            + "dest_brnid,tran_id,adviceno,advicebrncode)"
                            + "values ('" + acno + "','" + custName + "','" + dt + "','" + dt + "',0.0," + accountStatementCharges + ",1,2," + recno + ",'','" + dt + "',3,0,'Y','" + username + "',0,0,'A','ACCOUNT STATREMENT CHARGE','SYSTEM'," + trsno + ",now(),'','" + orgBrnCode + "','" + orgBrnCode + "',0,'','')");
                    rowAffected = Q.executeUpdate();
                    if (rowAffected <= 0) {
                        ut.rollback();
                        return errorMessage;
                    }

                    String custAcNature = common.getAcNatureByAcNo(acno);
                    result = ftsBean.insertRecons(custAcNature, acno, 1, accountStatementCharges, dt, dt, 2, "ACCOUNT STATREMENT CHARGE", username, trsno, null, recno, "Y", "SYSTEM", 71, 3, null, null, null, null, null, 1, null, null, null, null, orgBrnCode, orgBrnCode, null, null, "", "");
                    if (!result.equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return errorMessage;
                    }

                    if (code == 1) {
                        recno = ftsBean.getRecNo();

                        Q = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                                + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                                + "dest_brnid,tran_id,adviceno,advicebrncode)"
                                + "values ('" + acno + "','" + custName + "','" + dt + "','" + dt + "',0.0," + sTax + ",1,2," + recno + ",'','" + dt + "',3,0,'Y','" + username + "',0,0,'A','ACCOUNT STATREMENT CHARGE','SYSTEM'," + trsno + ",now(),'','" + orgBrnCode + "','" + orgBrnCode + "',0,'','')");
                        rowAffected = Q.executeUpdate();
                        if (rowAffected <= 0) {
                            ut.rollback();
                            return errorMessage;
                        }

                        result = ftsBean.insertRecons(custAcNature, acno, 1, sTax, dt, dt, 2, "GST FOR ACCOUNT STATREMENT CHARGE", username, trsno, null, recno, "Y", "SYSTEM", null, 3, null, null, null, null, null, 1, null, null, null, null, orgBrnCode, orgBrnCode, null, null, "", "");
                        if (!result.equalsIgnoreCase("TRUE")) {
                            ut.rollback();
                            return errorMessage;
                        }

                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = orgBrnCode + keyArray[1];
                            String mainDetails = description.toUpperCase() + " for A/c Statement Chg. for. " + acno;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            recno = ftsBean.getRecNo();
                            Q = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                                    + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                                    + "dest_brnid,tran_id,adviceno,advicebrncode)"
                                    + "values ('" + taxHead + "','" + mainDetails + "','" + dt + "','" + dt + "'," + taxAmount + ",0.0,0,2," + recno + ",'','" + dt + "',3,0,'Y','" + username + "',0,0,'A',' " + mainDetails + "','SYSTEM'," + trsno + ",now(),'','" + orgBrnCode + "','" + orgBrnCode + "',0,'','')");
                            rowAffected = Q.executeUpdate();
                            if (rowAffected <= 0) {
                                ut.rollback();
                                return errorMessage;
                            }

                            glAcNature = common.getAcNatureByAcNo(taxHead);

                            result = ftsBean.insertRecons(glAcNature, taxHead, 0, taxAmount, dt, dt, 2, mainDetails, username, trsno, null, recno, "Y", "SYSTEM", null, 3, null, null, null, null, null, 1, null, null, null, null, orgBrnCode, orgBrnCode, null, null, "", "");
                            if (!result.equalsIgnoreCase("TRUE")) {
                                ut.rollback();
                                return errorMessage;
                            }
                        }
                    }

                    result = ftsBean.updateBalance(custAcNature, acno, 0.0d, (accountStatementCharges + sTax), null, null);
                    if (result.equalsIgnoreCase("TRUE")) {
                        ut.commit();
                        return "Account statement charges deducted successfully";
                    } else {
                        ut.rollback();
                        return "Failed !!! Unable to update customer balance";
                    }
                    /**
                     * ************ CORRESSPONDING RECONS ENTRY END
                     * ****************
                     */
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

    @Override
    public String destinationBranchAccountCharges(String acno, int rows, String orgBrnCode, String username, String fromdt, String toDt, int pages) throws ApplicationException {
        double accountStatementCharges, taxPercentage, taxPercentageAmount, totalAccountStatementCharges;
        String dt = ymdFormat.format(new Date());
        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            ut.begin();
            List acChargeCode = em.createNativeQuery("select code from parameterinfo_report where reportname='ACCOUNT STATEMENT CHARGES'").getResultList();
            if (acChargeCode.isEmpty()) {
                ut.rollback();
                return "Account statement charges is not defined for Destination account";
            } else {
                Vector v1 = (Vector) acChargeCode.get(0);
                int code = Integer.parseInt(v1.get(0).toString());
                if (code == 0) {
                    ut.rollback();
                    return "Account statement charges for Destination branch is off";
                } else {
                    List returnList = getParameters(acno, rows, orgBrnCode, fromdt, toDt, pages);
                    if (returnList.get(0).toString().equalsIgnoreCase("0")) {
                        ut.rollback();
                        return "Account statement charges is not defined,either for account code or for your branch";
                    }
                    if (returnList.get(0).toString().equalsIgnoreCase("1")) {
                        ut.rollback();
                        return "Duplicate statement charges is aplicable but applied type is not defined";

                    }
                    if (returnList.get(0).toString().equalsIgnoreCase("2")) {
                        ut.rollback();
                        return "Duplicate statement charges is applicable , but not defined for either account code or for your branch";

                    }
                    accountStatementCharges = Double.parseDouble(returnList.get(0).toString());
//                    taxPercentage = Double.parseDouble(returnList.get(1).toString());
//                    taxPercentageAmount = Double.parseDouble(returnList.get(2).toString());
//                    totalAccountStatementCharges = Double.parseDouble(returnList.get(3).toString());
                    String serviceChargeAccount = returnList.get(1).toString();
//                    String serviceTaxEducationCess = returnList.get(5).toString();
//                    Double duplicateCharges = Double.parseDouble(returnList.get(6).toString());

                    /*
                     * For Service Tax
                     */
                    List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname like 'STAXMODULE_ACTIVE'").getResultList();
                    Vector element4 = (Vector) listCode.get(0);
                    Integer sCode = Integer.parseInt(element4.get(0).toString());
                    double sTax = 0d;
                    if (sCode == 1) {
                        map = sxcxBean.getTaxComponent(accountStatementCharges, dt);
                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            sTax = sTax + Double.parseDouble(entry.getValue().toString());
                        }
                    }

                    float trsno = ftsBean.getTrsNo();
                    float recno = ftsBean.getRecNo();
                    String result = sxcxBean.cbsPostingCx(serviceChargeAccount, 0, dt, accountStatementCharges, 0, 2, "ACCOUNT STATEMENT CHARGE", 0f, "", "", "19000101", 3, 0f, recno, 65, orgBrnCode, orgBrnCode, username, "SYSTEM", trsno, "", "");
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
                        return "Failed!!! Problem in deducting account statement charges for destination account";
                    }

                    recno = ftsBean.getRecNo();
                    result = sxcxBean.cbsPostingSx(acno, 1, dt, accountStatementCharges, 0, 2, " ACCOUNT STATEMENT CHARGE", 0f, "", "", "19000101", 3, 0f, recno, 65, acno.substring(0, 2), orgBrnCode, username, "SYSTEM", trsno, "", "");
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        ut.rollback();
                        return "Falied!!! Problem in deducting account statement charges for destination account";
                    }

                    if (code == 1) {
                        recno = ftsBean.getRecNo();
                        result = sxcxBean.cbsPostingSx(acno, 1, dt, sTax, 0, 2, " GST FOR ACCOUNT STATEMENT CHARGE", 0f, "", "", "19000101", 3, 0f, recno, 71, acno.substring(0, 2), orgBrnCode, username, "SYSTEM", trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
                            return "Falied!!! Problem in deducting account statement charges for destination account";
                        }

                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = orgBrnCode + keyArray[1];
                            String mainDetails = description.toUpperCase() + " for A/c Statement Chg. for. " + acno;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            recno = ftsBean.getRecNo();
                            result = sxcxBean.cbsPostingCx(taxHead, 0, dt, taxAmount, 0, 2, mainDetails, 0f, "", "", "19000101", 3, 0f, recno, 71, orgBrnCode, orgBrnCode, username, "SYSTEM", trsno, "", "");
                            if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                                ut.rollback();
                                return "Problem in deducting account statement charges for destination account";
                            }
                        }
                    }

                    String custAcNature = common.getAcNatureByAcNo(acno);
                    result = ftsBean.updateBalance(custAcNature, acno, 0.0d, (accountStatementCharges + sTax), null, null);
                    if (result.equalsIgnoreCase("TRUE")) {
                        ut.commit();
                        return "Account statement charges deducted successfully";
                    } else {
                        ut.rollback();
                        return "Problem in customer balance updation";
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

    @Override
    public List getParameters(String acno, int rows, String orgBrnCode, String fromdt, String todt, int pages) throws ApplicationException {
        try {
            List returnList = new ArrayList();
            double accountStatementCharges = 0d;
//                    , taxPercentage = 0d, taxPercentageAmount = 0d, totalAccountStatementCharges = 0d, duplicateCharges = 0d;
            String acctcode = acno.substring(2, 4);
            List glheadAndChargesAccount = em.createNativeQuery("SELECT glheadmisc,charges FROM parameterinfo_miscincome WHERE purpose="
                    + "'abb statement charges' and acctcode='" + acctcode + "'").getResultList();
            if (glheadAndChargesAccount.isEmpty()) {
                returnList.add("0");
                return returnList;
            }
            Vector v = (Vector) glheadAndChargesAccount.get(0);
            accountStatementCharges = Double.parseDouble(v.get(1).toString());
            accountStatementCharges = accountStatementCharges * pages;
            String serviceChargeAccount = orgBrnCode + v.get(0).toString() + "01";

//            List taxmaster = em.createNativeQuery("select rot from taxmaster where applicableflag='Y'").getResultList();
//            Vector v1 = (Vector) taxmaster.get(0);
//            taxPercentage = Double.parseDouble(v1.get(0).toString());
//            taxPercentageAmount = (taxPercentage * (accountStatementCharges)) / 100;
//            totalAccountStatementCharges = accountStatementCharges + taxPercentageAmount;
//            List serviceChargeList = em.createNativeQuery("select acno from gltable where acname ='SERVICE CHARGES' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
//            Vector serviceChargeVector = (Vector) serviceChargeList.get(0);
//            String serviceChargeAccount = serviceChargeVector.get(0).toString();
//            List serviceTaxList = em.createNativeQuery("select acno from gltable where acname ='SERVICE TAX AND EDUCATION CESS' and substring(acno,1,2)='" + orgBrnCode + "'").getResultList();
//            Vector serviceTaxVec = (Vector) serviceTaxList.get(0);
//            String serviceTaxEducationCess = serviceTaxVec.get(0).toString();
            returnList.add(accountStatementCharges);
//            returnList.add(taxPercentage);
//            returnList.add(taxPercentageAmount);
//            returnList.add(totalAccountStatementCharges);
            returnList.add(serviceChargeAccount);
//            returnList.add(serviceTaxEducationCess);
//            returnList.add(duplicateCharges);
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String insertReasons(String acno, String dt, String remarks, String enterBy, String statementFromDt, String statementToDt, String orgBrnId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("insert into cbs_statement_charge_waiver(ACNO,DT,REMARKS,ENTRY_BY,STATEMENT_FROM_DT,STATEMENT_TO_DT,ORIGIN_BRANCH) values('" + acno + "','" + dt + "','" + remarks + "','" + enterBy + "','" + statementFromDt + "','" + statementToDt + "','" + orgBrnId + "')");
            int rowAffected = q.executeUpdate();
            if (rowAffected > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "false";
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

    @Override
    public String saveAccountDetails(String acno, String fromDt, String toDt, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List secList = em.createNativeQuery("select ACNO from accstatement_hist where acno='" + acno + "'").getResultList();
            if (secList.isEmpty()) {
                Query insertQuery1 = em.createNativeQuery("insert into accstatement_hist(acno,from_dt,to_dt,printedby,trantime)"
                        + "values (" + "'" + acno + "'" + "," + "'" + fromDt + "'" + "," + "'" + toDt + "'" + "," + "'" + user + "'" + "," + "now()" + ")");
                insertQuery1.executeUpdate();

            } else {
                Query updateQuery = em.createNativeQuery("update accstatement_hist set from_dt='" + fromDt + "',to_dt='" + toDt
                        + "',printedby='" + user + "',trantime=now() where acno='" + acno + "'");
                updateQuery.executeUpdate();
            }

            ut.commit();
            return "";
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

    @Override
    public List<ZeroBalReportPojo> getZeroBalReportDetails(String DATE, String ACTYPE, String BRCODE, String acNature, String txnType) throws ApplicationException {
        long begin = System.nanoTime();
        List<ZeroBalReportPojo> resultList = new ArrayList<ZeroBalReportPojo>();
        String bankName = "", bankAddress = "", tempAcctNature = "";
        try {
            List bankDetailsList;
            if (BRCODE.equalsIgnoreCase("0A")) {
                bankDetailsList = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode='90'").getResultList();
            } else {
                bankDetailsList = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode='" + BRCODE + "'").getResultList();
            }
            if (bankDetailsList.size() > 0) {
                for (int i = 0; i < bankDetailsList.size(); i++) {
                    Vector element = (Vector) bankDetailsList.get(i);
                    bankName = element.get(0).toString();
                    bankAddress = element.get(1).toString();
                }
            }
            // New Code
            String acTypeQuery = "", branchQuery = "", fdQuery = "";
            if (ACTYPE.equalsIgnoreCase("ALL")) {
                acTypeQuery = "and a.accttype in(select acctcode from accounttypemaster where acctnature = '" + acNature + "') ";
            } else {
                acTypeQuery = "and a.accttype in('" + ACTYPE + "') ";
            }

            if (BRCODE.equalsIgnoreCase("0A")) {
                branchQuery = "";
            } else {
                branchQuery = "and a.curbrcode='" + BRCODE + "'";
            }

            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                fdQuery = "and closeflag is null and trantype<>27 ";
            } else {
                fdQuery = "";
            }

            List natureDetailList = new ArrayList();
            ZeroBalReportPojo tableObj;
            if (txnType.equalsIgnoreCase("0")) {
                natureDetailList = em.createNativeQuery("select r.acno,a.custname ,cast(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)) as decimal(25,2)),a.AccStatus  "
                        + "from " + common.getTableName(acNature) + " r,"
                        + "" + ftsBean.getAccountTable(acNature) + " a where r.acno=a.acno and a.acno not in"
                        + "(select ast.acno as npaAcno  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(effdt) as dt from accountstatus where effdt <='" + DATE + "' "
                        + "and SPFLAG IN (15)  group by acno) b  where aa.acno = b.ano and aa.effdt = b.dt "
                        + "and aa.SPFLAG IN (15)  group by aa.acno,aa.effdt) c "
                        + "where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno)" + acTypeQuery + " "
                        + "And (closingdate is null or closingdate = '' or closingdate > '" + DATE + "') " + fdQuery + " and dt <= '" + DATE
                        + "' " + branchQuery + " group by r.acno having(cast(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)) as decimal(25,2)) =0) "
                        + "order by r.acno").getResultList();
            } else if (txnType.equalsIgnoreCase("1")) {
                if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    if (ACTYPE.equalsIgnoreCase("ALL")) {
                        natureDetailList = em.createNativeQuery("select distinct acno,custname,0,a.AccStatus from accountmaster a "
                                + " where Accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "') " + branchQuery + " and OpeningDt <='" + DATE + "' "
                                + " And (closingdate is null or closingdate = '' or closingdate > '" + DATE + "') and acno not in (select distinct acno from " + common.getTableName(acNature) + ")  order by acno ").getResultList();
                    } else {
                        natureDetailList = em.createNativeQuery(" select distinct acno,custname,0,a.AccStatus from accountmaster a "
                                + " where Accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "') " + branchQuery + " and accttype='" + ACTYPE + "' and OpeningDt <='" + DATE + "' "
                                + " And (closingdate is null or closingdate = '' or closingdate > '" + DATE + "') and acno not in (select distinct acno from " + common.getTableName(acNature) + ")  order by acno  ").getResultList();
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (ACTYPE.equalsIgnoreCase("ALL")) {
                        natureDetailList = em.createNativeQuery("select distinct acno,custname,0,a.AccStatus from td_accountmaster a "
                                + " where Accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "') " + branchQuery + " and OpeningDt <='" + DATE + "' "
                                + " And (closingdate is null or closingdate = '' or closingdate > '" + DATE + "') and acno not in (select distinct acno from " + common.getTableName(acNature) + ")  order by acno ").getResultList();
                    } else {
                        natureDetailList = em.createNativeQuery("select distinct acno,custname,0,a.AccStatus from td_accountmaster a "
                                + " where Accttype in (select acctcode from accounttypemaster where acctnature = '" + acNature + "') " + branchQuery + " and accttype='" + ACTYPE + "' and OpeningDt <='" + DATE + "' "
                                + " And (closingdate is null or closingdate = '' or closingdate > '" + DATE + "') and acno not in (select distinct acno from " + common.getTableName(acNature) + ")  order by acno").getResultList();
                    }
                }
            } else if (txnType.equalsIgnoreCase("2")) {
                natureDetailList = em.createNativeQuery("select r.acno,a.custname ,cast(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)) as decimal(25,2)),a.AccStatus  "
                        + "from " + common.getTableName(acNature) + " r,"
                        + "" + ftsBean.getAccountTable(acNature) + " a where r.acno=a.acno and a.acno in"
                        + "(select ast.acno as npaAcno  from accountstatus ast, "
                        + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + "(select acno as ano, max(effdt) as dt from accountstatus where effdt <='" + DATE + "' "
                        + "and SPFLAG IN (15)  group by acno) b  where aa.acno = b.ano and aa.effdt = b.dt "
                        + "and aa.SPFLAG IN (15)  group by aa.acno,aa.effdt) c "
                        + "where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno)" + acTypeQuery + " "
                        + "And (closingdate is null or closingdate = '' or closingdate > '" + DATE + "') " + fdQuery + " and dt <= '" + DATE
                        + "' " + branchQuery + " group by r.acno having(cast(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)) as decimal(25,2)) =0) "
                        + "order by r.acno").getResultList();
            }

            if (natureDetailList.size() > 0) {
                for (int i = 0; i < natureDetailList.size(); i++) {
                    Vector element = (Vector) natureDetailList.get(i);
                    tableObj = new ZeroBalReportPojo();
                    tableObj.setBankName(bankName);
                    tableObj.setBankAdd(bankAddress);
                    tableObj.setAcno(element.get(0).toString());
                    tableObj.setCustName(element.get(1).toString());
                    tableObj.setBal(Float.parseFloat(element.get(2).toString()));
                    resultList.add(tableObj);
                }
            }
//            System.out.println("Returned List Size---->" + resultList.size());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
//        System.out.println("Execution time for getZeroBalReportDetails() method is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        return resultList;
    }

    public List getAllAccounType() throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select distinct (acctcode) from accounttypemaster").getResultList();
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * **************************Methods for HO Inrerest Cal
     * Report*********************
     */
    public String checkAcno(String acno) throws ApplicationException {
        String acName = null;
        try {
            List resultList = em.createNativeQuery("SELECT ACNAME FROM gltable WHERE ACNO='" + acno + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector vec = (Vector) resultList.get(0);
                acName = vec.get(0).toString();
            } else {
                throw new ApplicationException("No detail exists for this account no. !");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return acName;
    }

    public List<HoIntCalPojo> getHoInterst(String acno, String fromDate, String toDate, double roi) throws ApplicationException {
        double opCloseBal = 0.0;
        double totCloseBal = 0.0;
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        List<HoIntCalPojo> resultList = new ArrayList<HoIntCalPojo>();
        try {
            List amountList = em.createNativeQuery("Select ifnull(sum(cramt)-sum(dramt),0) From  gl_recon  Where acno = '" + acno + "'  and dt < '" + fromDate + "'  and AUTH='Y' ").getResultList();
            Vector vec = (Vector) amountList.get(0);
            opCloseBal = Double.parseDouble(vec.get(0).toString());
            totCloseBal = opCloseBal;
            double prodBalCr = 0.0;
            double prodBaldr = 0.0;
            while (ymd.parse(fromDate).compareTo(ymd.parse(toDate)) <= 0) {
                List amtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) From  gl_recon Where acno = '" + acno + "'  and dt = '" + fromDate + "'  and AUTH='Y'").getResultList();
                Vector vect = (Vector) amtList.get(0);
                double closeBal = Double.parseDouble(vect.get(0).toString());
                totCloseBal += closeBal;
                if (totCloseBal > 1) {
                    prodBalCr += totCloseBal;
                } else {
                    prodBaldr += totCloseBal;
                }
                fromDate = CbsUtil.dateAdd(fromDate, 1);
            }
            DecimalFormat formatter = new DecimalFormat("#");
            DecimalFormat formatr = new DecimalFormat("#.##");
            if (prodBalCr > 0) {
                double interest = Double.parseDouble(formatter.format((prodBalCr * roi) / 36500));
                HoIntCalPojo pojo = new HoIntCalPojo();
                pojo.setAcno(acno);
                pojo.setProduct(Double.parseDouble(formatter.format(prodBalCr)));
                pojo.setInterest(interest);
                pojo.setClosingBal(Double.parseDouble(formatr.format(totCloseBal)));
                pojo.setProductType("Credit Product");
                pojo.setRoi(roi);
                resultList.add(pojo);
            }
            if (prodBaldr < 0) {
                double intDr = Double.parseDouble(formatter.format((prodBaldr * roi) / 36500));
                HoIntCalPojo pojo = new HoIntCalPojo();
                pojo.setAcno(acno);
                pojo.setProduct(Double.parseDouble(formatter.format(prodBaldr)));
                pojo.setInterest(intDr);
                pojo.setClosingBal(Double.parseDouble(formatr.format(totCloseBal)));
                pojo.setProductType("Debit Product");
                pojo.setRoi(roi);
                resultList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    public List<AccountEditHistoryPojo> getAccountEditHistoryReport(String acno, String frmDt, String toDt) throws ApplicationException {
        List<AccountEditHistoryPojo> editHisList = new ArrayList<AccountEditHistoryPojo>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String acNature = ftsBean.getAccountNature(acno);
            List resultList = new ArrayList();
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                resultList = em.createNativeQuery("select acno,custname,openingdt from td_accountmaster where acno='" + acno + "'").getResultList();
            } else {
                resultList = em.createNativeQuery("select acno,custname,openingdt from accountmaster where acno='" + acno + "'").getResultList();
            }
            if (resultList.isEmpty()) {
                throw new ApplicationException("Account no. does not exits.");
            }
            Vector vect = (Vector) resultList.get(0);

            String sqlQuery = "select ifnull(name, '') 'name',ifnull(opermode,0) 'opermode', " + "orgncode,enteredby,updatedt,"
                    + "ifnull(authby,'') 'authby',ifnull(introacno,'') 'introacno', ifnull(fname,'') 'fname',ifnull(maddress,'') 'maddress', "
                    + "ifnull(paddress,'') 'paddress',ifnull(phno,'') 'phno', ifnull(panno,'') 'panno',ifnull(chbook,0) 'chbook', "
                    + "ifnull(nominee,'') 'nominee',ifnull(relationship,'') 'relationship', ifnull(jtname1,'') 'jtname1' ,ifnull(jtname2,'') 'jtname2', "
                    + "ifnull(gname,'') 'gname',ifnull(acinst,'') 'acinst',ifnull(apptds,'') 'apptds',ifnull(tdsdocu,'') 'tdsdocu', "
                    + "ifnull(intopt,'') 'intopt',ifnull(minbalcharge,'') 'minbalcharge', ifnull(jtname3,'') 'jtname3',ifnull(jtname4,'') 'jtname4',"
                    + "ifnull(inttoacno,'') 'inttoacno'  from acedithistory where acno='" + acno + "' and date_format(updatedt,'%Y%m%d') between '" + frmDt + "' and '" + toDt + "' order by updatedt desc";
            List resultList1 = em.createNativeQuery(sqlQuery.toString()).getResultList();
            if (!resultList1.isEmpty()) {
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector vec = (Vector) resultList1.get(i);
                    AccountEditHistoryPojo pojo = new AccountEditHistoryPojo();
                    pojo.setName(vec.get(0).toString());
                    List resultList2 = em.createNativeQuery("select description from codebook where groupcode=4 and code=" + Integer.parseInt(vec.get(1).toString())).getResultList();
                    if (!resultList2.isEmpty()) {
                        pojo.setOperationMode(((Vector) resultList2.get(0)).get(0).toString());
                    } else {
                        pojo.setOperationMode("");
                    }
                    List resultList3 = em.createNativeQuery("select description from codebook where groupcode=6 and code=" + Integer.parseInt(vec.get(2).toString())).getResultList();
                    if (!resultList3.isEmpty()) {
                        pojo.setOrgnCode(((Vector) resultList3.get(0)).get(0).toString());
                    } else {
                        pojo.setOrgnCode("");
                    }
                    pojo.setAcno(acno);
                    pojo.setCustName(vect.get(1).toString());
                    pojo.setOpeningDt(sdf.format(ymdFormat.parse(vect.get(2).toString())));
                    pojo.setEnterBy(vec.get(3).toString());
                    pojo.setUpdatedDt(sdf.format((Date) vec.get(4)));
                    pojo.setAuthBy(vec.get(5).toString());
                    pojo.setIntroAcno(vec.get(6).toString());
                    pojo.setFatherName(vec.get(7).toString());
                    pojo.setMailAdd(vec.get(8).toString());
                    pojo.setPermAdd(vec.get(9).toString());
                    pojo.setPhoneNo(vec.get(10).toString());
                    pojo.setPanNo(vec.get(11).toString());
                    pojo.setCheckBook(vec.get(12) == null || vec.get(12).toString().equalsIgnoreCase("0") || vec.get(12).toString().equalsIgnoreCase("") ? "No" : "Yes");
                    pojo.setNominee(vec.get(13).toString());
                    pojo.setRelationShip(vec.get(14).toString());
                    pojo.setJtName1(vec.get(15).toString());
                    pojo.setJtName2(vec.get(16).toString());
                    pojo.setGuardianName(vec.get(17).toString());
                    pojo.setAcInst(vec.get(18).toString());
                    pojo.setAppTds(vec.get(19).toString());
                    pojo.setTdsDocu(vec.get(20).toString());
                    pojo.setIntOpt(vec.get(21).toString());
                    pojo.setMinBalCharge(vec.get(22) == null || vec.get(22).toString().equalsIgnoreCase("0") || vec.get(22).toString().equalsIgnoreCase("") ? "" : "Yes");
                    pojo.setJtName3(vec.get(23).toString());
                    pojo.setJtName4(vec.get(24).toString());
                    pojo.setAllJtName(vec.get(15).toString().trim() + "/" + vec.get(16).toString().trim() + "/" + vec.get(23).toString().trim() + "/" + vec.get(24).toString().trim());
                    pojo.setIntToAcno(vec.get(25).toString());
                    pojo.setAcNature(acNature);
                    editHisList.add(pojo);
                }

            }
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                String query = "select ifnull(roi,0) roi,ifnull(penalroi,0) penalroi,ifnull(aclimit,0) aclimit, ifnull(enterby,'') enterby,date_format(enterdate,'%d/%m/%Y')"
                        + "enterdate, ifnull(adhoclimit,0) adhoclimit,adhoctilldt, ifnull(adhocinterest,0) adhocinterest,ifnull(authby,'') "
                        + "authby, adhocapplicabledt,ifnull(sanctionlimit,0) sanctionlimit, sanctionlimitdt,ifnull(maxlimit,0) maxlimit, "
                        + "ifnull(subsidyamt,0) subsidyamt,subsidyexpdt from loan_oldinterest where acno='" + acno + "' and enterdate between '"
                        + frmDt + "' and '" + toDt + "' order by enterdate desc";
                List resultList2 = em.createNativeQuery(query.toString()).getResultList();
                if (!resultList2.isEmpty()) {
                    for (int i = 0; i < resultList2.size(); i++) {
                        Vector vec = (Vector) resultList2.get(i);
                        AccountEditHistoryPojo pojo = new AccountEditHistoryPojo();
                        pojo.setAcno(acno);
                        pojo.setCustName(vect.get(1).toString());
                        pojo.setOpeningDt(sdf.format(ymdFormat.parse(vect.get(2).toString())));
                        pojo.setRoi(vec.get(0).toString());
                        pojo.setPanelRoi(vec.get(1).toString());
                        pojo.setLimit(vec.get(2).toString());
                        pojo.setLimitEnterBy(vec.get(3).toString());
                        String roiUpdateDt = vec.get(4).toString();
                        pojo.setRoiUpdatedDt(roiUpdateDt.equalsIgnoreCase("01/01/1900") ? "" : roiUpdateDt);
                        pojo.setAdHocLimit(vec.get(5).toString());
                        String adHocDt = vec.get(6) == null ? "" : sdf.format(yymmdd.parse(vec.get(6).toString()));
                        pojo.setAdHocDt(adHocDt.equalsIgnoreCase("01/01/1900") ? "" : adHocDt);
                        pojo.setAdHocInt(vec.get(7).toString());
                        pojo.setLimitAuthBy(vec.get(8).toString());
                        String adHocApplDt = vec.get(9) == null ? "" : sdf.format(yymmdd.parse(vec.get(9).toString()));
                        pojo.setAdHocApplicableDt(adHocApplDt.equalsIgnoreCase("01/01/1900") ? "" : adHocApplDt);
                        pojo.setSanctionLimit(vec.get(10).toString());
                        String sancLimitDt = vec.get(11) == null ? "" : sdf.format(yymmdd.parse(vec.get(11).toString()));
                        pojo.setSanctionLimitDt(sancLimitDt.equalsIgnoreCase("01/01/1900") ? "" : sancLimitDt);
                        pojo.setMaxLimit(vec.get(12).toString());
                        pojo.setSubsidyAmt(vec.get(13).toString());
                        String subExpDt = vec.get(14) == null ? "" : sdf.format(yymmdd.parse(vec.get(14).toString()));
                        pojo.setSubsidyExpDt(subExpDt.equalsIgnoreCase("01/01/1900") ? "" : subExpDt);
                        editHisList.add(pojo);
                    }
                }

            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return editHisList;
    }

    public List<AccountStatementReportPojo> getAccountStatementReportWise(String acNo, String fromDate, String toDate) throws ApplicationException {
        List<AccountStatementReportPojo> returnList = new ArrayList<AccountStatementReportPojo>();
        double openBal;
        try {
            List tempList = null;
            String date = "", particulars = "", chequeno = "", acNat, valdt = "", custName = "", acctDesc = "", jtName1 = null, jtName2 = null, crAdd = null, prAdd = null;
            double withdrawl = 0, deposit = 0, balance = 0;
            Calendar calFromDt = Calendar.getInstance();
            calFromDt.setTime(ymdFormat.parse(fromDate));
            calFromDt.add(Calendar.DATE, -1);
            String dtB = ymdFormat.format(calFromDt.getTime());
            acNat = ftsBean.getAccountNature(acNo);
            if (acNat != null) {
                tempList = em.createNativeQuery("SELECT a.CUSTNAME, b.ACCTDESC, a.JTName1, a.JTName2, c.CrAddress, c.PrAddress FROM accountmaster a,"
                        + "accounttypemaster b ,customermaster c WHERE a.ACNO='" + acNo + "' AND SUBSTRING(a.acno,3,2)=b.ACCTCODE AND "
                        + "SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype AND SUBSTRING(a.acno,11,2)="
                        + "c.agcode").getResultList();
                if (tempList.isEmpty()) {
                    return null;
                } else {
                    Vector ele = (Vector) tempList.get(0);
                    custName = ele.get(0).toString();
                    acctDesc = ele.get(1).toString();
                    jtName1 = ele.get(2).toString();
                    jtName2 = ele.get(3).toString();
                    crAdd = ele.get(4).toString();
                    prAdd = ele.get(5).toString();
                }
                openBal = common.getBalanceOnDateWise(acNo, dtB);
                List<AbbStatementPojo> abbStatement = common.getAbbStatementWies(acNo, fromDate, toDate, "");
                for (AbbStatementPojo asp : abbStatement) {
                    date = asp.getDate1();
                    particulars = asp.getParticulars();
                    chequeno = asp.getChequeNo();
                    withdrawl = asp.getWithdrawl();
                    deposit = asp.getDeposit();
                    balance = asp.getBalance();
                    valdt = asp.getValueDate();
                    AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                    pojo.setAcNo(acNo);
                    pojo.setAcType(acctDesc);
                    pojo.setBalance(balance);
                    pojo.setChequeNo(chequeno);
                    pojo.setCrAdd(crAdd);
                    pojo.setCustName(custName);
                    pojo.setDate(date);
                    pojo.setDeposit(deposit);
                    pojo.setJtName(jtName1);
                    pojo.setNomination(jtName2);
                    pojo.setOpeningBal(openBal);
                    pojo.setParticulars(particulars);
                    pojo.setPrAdd(prAdd);
                    pojo.setValueDate(valdt);
                    pojo.setWithdrawl(withdrawl);
                    pojo.setAcNature(acNat);
                    returnList.add(pojo);
                }
                if (abbStatement.isEmpty()) {
                    AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                    pojo.setAcNo(acNo);
                    pojo.setAcType(acctDesc);
                    pojo.setCrAdd(crAdd);
                    pojo.setCustName(custName);
                    pojo.setJtName(jtName1);
                    pojo.setNomination(jtName2);
                    pojo.setOpeningBal(openBal);
                    pojo.setBalance(openBal);
                    pojo.setPrAdd(prAdd);
                    pojo.setAcNature(acNat);
                    returnList.add(pojo);
                }
                return returnList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    public List<RdReschedulePojo> getAccountDetailsStatement(String acno) throws ApplicationException {
        List<RdReschedulePojo> returnList = new ArrayList<RdReschedulePojo>();
        String custName, openingDate, matdt, installment, roi, period;
        try {
            List resultList = em.createNativeQuery("SELECT custname,date_format(OPENINGDT,'%Y%m%d') AS OPENING_DT,date_format(RDMATDATE,'%Y%m%d') "
                    + "AS MAT_DT,RDINSTAL,IntDeposit FROM accountmaster WHERE ACCSTATUS<>9 AND ACNO ='" + acno + "' ").getResultList();
            if (resultList.isEmpty()) {
                return null;
            } else {
                Vector ele = (Vector) resultList.get(0);
                custName = ele.get(0).toString();
                openingDate = ele.get(1).toString();
                if (ele.get(2) != null) {
                    matdt = ele.get(2).toString();
                } else {
                    matdt = "";
                }
                if (ele.get(3) != null) {
                    installment = ele.get(3).toString();
                } else {
                    installment = "";
                }
                roi = ele.get(4).toString();
                period = String.valueOf(CbsUtil.monthDiff(ymd.parse(openingDate), ymd.parse(matdt)));
                RdReschedulePojo pojo = new RdReschedulePojo();
                pojo.setCustname(custName);
                pojo.setOpeningDate(openingDate);
                pojo.setMatdt(matdt);
                pojo.setInstallment(installment);
                pojo.setRoi(roi);
                pojo.setPeriod(period);
                returnList.add(pojo);
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List<RdReschedulePojo> gridDetailAccountDetails(String acno) throws ApplicationException {
        List<RdReschedulePojo> detailsList = new ArrayList<RdReschedulePojo>();
        try {
            List checkList;
            checkList = em.createNativeQuery("select sno,date_format(duedt,'%Y%m%d'),installamt,status,date_format(paymentdt,'%Y%m%d'),enterby "
                    + "from rd_installment where acno ='" + acno + "'").getResultList();

            for (int i = 0; i < checkList.size(); i++) {
                Vector vec = (Vector) checkList.get(i);
                RdReschedulePojo pojo = new RdReschedulePojo();
                pojo.setSno(vec.get(0).toString());
                pojo.setDueDt(vec.get(1).toString().substring(6, 8) + "/" + vec.get(1).toString().substring(4, 6) + "/" + vec.get(1).toString().substring(0, 4));
                pojo.setInstallment(vec.get(2).toString());
                pojo.setStatus(vec.get(3).toString());
                pojo.setPaymentDt(vec.get(4) != null ? vec.get(4).toString().substring(6, 8) + "/" + vec.get(4).toString().substring(4, 6) + "/" + vec.get(4).toString().substring(0, 4) : "");
                pojo.setEnterBy(vec.get(5) != null ? vec.get(5).toString() : "");
                detailsList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return detailsList;
    }

    public String SaveRecordRD(String acno, String openingDate, String installamt, int period, String EnterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        Date date = new Date();
        try {
            ut.begin();
            String dueDt = openingDate;
            int sno = 1;
            for (int i = 0; i < period; i++) {
                dueDt = CbsUtil.monthAdd(dueDt, 1);
                Query q1 = em.createNativeQuery("insert into rd_installment (acno,sno,duedt,installamt,status,enterby,recno,excessamt,p_excessamt) "
                        + "values('" + acno + "'," + sno + ",'" + dueDt + "','" + Float.parseFloat(installamt) + "','UNPAID','" + EnterBy + "',0,0.0,0.0)");
                int insertResult = q1.executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Insertion problem in RD_Installment");
                }
                sno = sno + 1;
            }

            List resultChkList = em.createNativeQuery("select * from rd_installment where acno = '" + acno + "' and duedt < '" + ymdFormat.format(date) + "'").getResultList();
            if (resultChkList.size() > 0) {
                Query q2 = em.createNativeQuery("update rd_installment set status = 'PAID' where acno = '" + acno + "' and duedt < '" + ymdFormat.format(date) + "'");
                int insertResult1 = q2.executeUpdate();
                if (insertResult1 <= 0) {
                    throw new ApplicationException("Insertion problem in RD_Installment");
                }
            }
            result = "true";
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return result;
    }

    public List gettransDetailsReport(String frDt, String toDt, String brnCode,
            String fromTime, String tottime, int timeAllow, String reportType,
            String statusType, String bnkName) throws ApplicationException {
        List<TransactionDetailsPojo> repList = new ArrayList<TransactionDetailsPojo>();
        String condition = null;
        try {
            if (timeAllow == 0) {
                String statusCond = "";
                /*
                 * A ALL
                 * S Paid 
                 * U Un-Paid 
                 * I Response Awaiting 
                 * P Pending (Based On Auth, Y- Verified, N-Pending) 
                 * A ALL
                 */
                if (statusType.equalsIgnoreCase("A")) {
                    statusCond = "";
                } else {
                    statusCond = " and b.Status='" + statusType + "' ";
                }
                if (brnCode.equalsIgnoreCase("A")) {
                    if (reportType.equalsIgnoreCase("GO")) {// For general outward report data for all branch
                        condition = "Initiated_Bank_Name='" + bnkName + "' and (CPSMS_MessageId is null or CPSMS_MessageId='') and dt between '" + frDt + "' and '" + toDt + "'" + statusCond + " order by PaymentDate";
                    } else if (reportType.equalsIgnoreCase("CO")) {// For CPSMS outward report data for all branch
                        condition = "(CPSMS_MessageId is not null AND CPSMS_MessageId<>'') and dt between '" + frDt + "' and '" + toDt + "'" + statusCond + " order by PaymentDate";
                    }
                } else {
                    if (reportType.equalsIgnoreCase("GO")) {// For general outward report data for particular branch
                        condition = "Initiated_Bank_Name='" + bnkName + "' and (CPSMS_MessageId is null or CPSMS_MessageId='') and OrgBrnid ='" + brnCode + "' and dt between '" + frDt + "' and '" + toDt + "'" + statusCond + " order by PaymentDate";
                    } else if (reportType.equalsIgnoreCase("CO")) {// For CPSMS outward report data for particular branch
                        condition = "(CPSMS_MessageId is not null AND CPSMS_MessageId<>'') and OrgBrnid ='" + brnCode + "' and dt between '" + frDt + "' and '" + toDt + "'" + statusCond + " order by PaymentDate";
                    }
                }
            }

            List list = em.createNativeQuery("select a.ref_desc,b.beneficiaryname,b.txnamt,b.paymentdate,b.debitaccountno,"
                    + " b.creditaccountno,b.details, b.cmsbankrefno,b.utrno,b.reason,b.OutsidebankIfscCode,b.Status,"
                    + " b.UniqueCustomerRefNo from cbs_ref_rec_type a, neft_ow_details b where  a.ref_rec_no='009' "
                    + " and a.ref_code =  b.paymentType  and " + condition).getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    TransactionDetailsPojo pojo = new TransactionDetailsPojo();
                    Vector vector = (Vector) list.get(i);
                    pojo.setBeneficiaryName(vector.get(1).toString());
                    pojo.setTxnAmt(new BigDecimal(vector.get(2).toString()));
                    pojo.setPaymentDate(vector.get(3).toString());
                    pojo.setDebitAccountNo(vector.get(4).toString());
                    pojo.setCreditAccountNo(vector.get(5).toString());
                    pojo.setDetails(vector.get(6) != null ? vector.get(6).toString() : "");
                    pojo.setCmsBankRefNo(vector.get(7) != null ? vector.get(7).toString() : "");
                    pojo.setUtrNo(vector.get(8) != null ? vector.get(8).toString() : "");
                    pojo.setIfscCode(vector.get(10) != null ? vector.get(10).toString() : "");
                    if (reportType.equalsIgnoreCase("GO")) {
                        pojo.setUniqueCustRefNo(vector.get(0).toString());
                    } else {
                        pojo.setUniqueCustRefNo(vector.get(12) != null ? vector.get(12).toString() : "");
                    }
                    String status = vector.get(11).toString();
                    if (status.equalsIgnoreCase("S")) {
                        pojo.setReason("");
                    } else {
                        pojo.setReason(vector.get(9) != null ? vector.get(9).toString() : "");
                    }
                    repList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return repList;
    }

    public String getRefDesc(String paymentType) throws ApplicationException {
        String refDesc = "";
        try {
            List refDescList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='211' and ref_code='" + paymentType + "'").getResultList();
            if (!refDescList.isEmpty()) {
                Vector element = (Vector) refDescList.get(0);
                refDesc = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return refDesc;
    }

    public List<AccountTransactionPojo> getAccountTransactionDetails(String acno, String repType, double frAmt, double toAmt, String frDt, String toDt) throws ApplicationException {
        List<AccountTransactionPojo> resultList = new ArrayList<AccountTransactionPojo>();
        List acnoDetailList = new ArrayList();
        String tableName1 = "";
        try {
            String acctCode = ftsPosting.getAccountCode(acno);
            String acNat = ftsPosting.getAcNatureByCode(acctCode);
            String tableName2 = common.getTableName(acNat);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                tableName1 = "td_accountmaster";
            } else {
                tableName1 = "accountmaster";
            }

            if (repType.equalsIgnoreCase("3")) {
                acnoDetailList = em.createNativeQuery("SELECT a.CUSTNAME,cu.panno,r.cramt as cramt,r.dramt as dramt ,date_format( r.dt,'%Y%m%d') FROM " + tableName1 + " a," + tableName2 + " r,customermaster cu  "
                        + "WHERE a.ACNO ='" + acno + "' and a.ACNO = r.acno and cu.brncode=a.curbrcode and substring(r.acno,1,2)= a.curbrcode and a.acctType=cu.actype "
                        + "and substring(a.acno,5,6)=cu.custno and (r.cramt between '" + frAmt + "' and '" + toAmt + "' or r.dramt between '" + frAmt + "' and '" + toAmt + "')and r.dt between '" + frDt + "' and '" + toDt + "' "
                        + "group by a.CUSTNAME,cu.panno,r.dt,r.cramt,r.dramt order by r.dt").getResultList();
            } else {
                acnoDetailList = em.createNativeQuery("SELECT a.CUSTNAME,cu.panno,r.cramt as cramt,r.dramt as dramt ,date_formatdate(r.dt,'%Y%m%d') FROM " + tableName1 + " a," + tableName2 + " r,customermaster cu  "
                        + "WHERE a.ACNO ='" + acno + "' and a.ACNO = r.acno and cu.brncode=a.curbrcode and substring(r.acno,1,2)= a.curbrcode and a.acctType=cu.actype "
                        + "and substring(a.acno,5,6)=cu.custno and (r.cramt between '" + frAmt + "' and '" + toAmt + "' or r.dramt between '" + frAmt + "' and '" + toAmt + "')and r.dt between '" + frDt + "' and '" + toDt + "' "
                        + "AND  r.TRANTYPE='" + repType + "'group by a.CUSTNAME,cu.panno,r.dt,r.cramt,r.dramt order by r.dt").getResultList();
            }

            if (acnoDetailList.isEmpty()) {
                throw new ApplicationException("Data does not exit.");
            }
            for (int i = 0; i < acnoDetailList.size(); i++) {
                Vector vtr = (Vector) acnoDetailList.get(i);
                AccountTransactionPojo pojo = new AccountTransactionPojo();
                pojo.setAccNo(acno);
                pojo.setCustName(vtr.get(0).toString());
                pojo.setPanNo(vtr.get(1).toString() != null ? vtr.get(1).toString() : "");
                pojo.setCrAmt(Double.parseDouble(vtr.get(2).toString()));
                pojo.setDrAmt(Double.parseDouble(vtr.get(3).toString()));
                pojo.setDate(vtr.get(4).toString().substring(6, 8) + "/" + vtr.get(4).toString().substring(4, 6) + "/" + vtr.get(4).toString().substring(0, 4));
                resultList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    public List<KYCcategorisationPojo> getKYCcategDetails(String brnCode, String acctNature, String acctType, String toDt) throws ApplicationException {
        List<KYCcategorisationPojo> kycAcNoList = new ArrayList<KYCcategorisationPojo>();
        String tableName1 = "";
        List result = new ArrayList();

        try {
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                tableName1 = "td_accountmaster";
            } else {
                tableName1 = "accountmaster";
            }
            if (acctType.equalsIgnoreCase("All")) {
                result = em.createNativeQuery("select a.acno ,a.custname ,ifnull(b.Networth,0),d.ref_desc,ifnull(date_format(b.RatingAsOn,'%d/%m/%Y'),'') "
                        + "from " + tableName1 + " a,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type d, accounttypemaster act "
                        + "where  a.accttype = act.acctcode and act.acctnature ='" + acctNature + "' and  a.acno = c.acno and b.customerid = c.CustId and a.curbrcode = '" + brnCode + "' "
                        + "and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code and OpeningDt <= '" + toDt + "' "
                        + "and a.AccStatus <> 9 order by a.acno").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno ,a.custname ,ifnull(b.Networth,0),d.ref_desc,ifnull(date_format(b.RatingAsOn,'%d/%m/%Y'),'')"
                        + "from " + tableName1 + " a,cbs_customer_master_detail b,customerid c,cbs_ref_rec_type d "
                        + "where a.accttype ='" + acctType + "' and a.acno = c.acno and b.customerid = c.CustId and a.curbrcode = '" + brnCode + "' "
                        + "and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code and OpeningDt <= '" + toDt + "' "
                        + "and a.AccStatus <> 9 order by a.acno").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector datav = (Vector) result.get(i);
                    KYCcategorisationPojo pojo = new KYCcategorisationPojo();
                    String acno = datav.get(0).toString();
                    String custName = datav.get(1).toString();
                    pojo.setAcNo(acno);
                    pojo.setCustName(custName);
                    pojo.setAnnualIncome(new BigDecimal(datav.get(2).toString()));
                    pojo.setKycCateg(datav.get(3).toString());
                    pojo.setOrrRatingDt(datav.get(4).toString());

                    kycAcNoList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return kycAcNoList;
    }

    public BigDecimal getAverageBalanceRbi(String acno, String formDt, String toDt) throws ApplicationException {
        BigDecimal balance = new BigDecimal("0.00");
        int nodays;
        try {
            String acNat = ftsPosting.getAcNatureByCode(acno.substring(2, 4));
            String tableName2 = common.getTableName(acNat);
            nodays = (int) CbsUtil.dayDiff(ymd.parse(formDt), ymd.parse(toDt));

            for (int j = 0; j <= nodays; j++) {
                formDt = CbsUtil.dateAdd(formDt, j);
                List avgList = em.createNativeQuery("select ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2),0) from " + tableName2 + " where dt <= '" + formDt + "' and acno = '" + acno + "'").getResultList();
                if (!avgList.isEmpty()) {
                    Vector vtr1 = (Vector) avgList.get(0);
                    BigDecimal amt = new BigDecimal(vtr1.get(0).toString());
                    balance = amt.add(balance);
                }
            }
            return new BigDecimal(balance.doubleValue() / (nodays == 0 ? nodays + 1 : nodays));
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<FdAccountDetailPojo> getFdAccountDetail(String acCode, String fAmt, String tAmt, String fDt, String tDt, String brCode) throws ApplicationException {
        List<FdAccountDetailPojo> fdAcNoDetailList = new ArrayList<FdAccountDetailPojo>();
        List result = new ArrayList();
        List resultl = new ArrayList();
        String acctNature = "";

        try {
            acctNature = common.getAcNatureByAcType(acCode);
            if (brCode.equalsIgnoreCase("0A")) {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),c.praddress,ifnull(c.panno,'') "
                            + "from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno "
                            + "and  a.acno = r.acno and r.acno = th.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno "
                            + "and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.voucherno = th.voucherno "
                            + "and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername "
                            + "having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' order by d.custid,acno").getResultList();
                }

            } else {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),c.praddress,ifnull(c.panno,'') "
                            + "from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno "
                            + "and  a.acno = r.acno and r.acno = th.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno "
                            + "and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and a.curbrcode ='" + brCode + "' "
                            + "and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername "
                            + "having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' order by d.custid,acno").getResultList();
                }
            }

            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Vector resultSet = (Vector) result.get(i);
                    String acno = resultSet.get(0).toString();
                    String custN = resultSet.get(1).toString();
                    String fName = resultSet.get(2).toString();
                    String addr = resultSet.get(3).toString();
                    String panNo = resultSet.get(4).toString();

                    resultl = em.createNativeQuery("select cast(t.prinamt as decimal (25,2)),ifnull(cast(t.FinalAmt as decimal (25,2)),0),t.roi,th.interest  from td_vouchmst t,td_interesthistory th "
                            + "where t.acno = '" + acno + "' and t.acno = th.acno and t.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "'").getResultList();

                    if (resultl.size() > 0) {
                        for (int j = 0; j < resultl.size(); j++) {
                            Vector record = (Vector) resultl.get(j);
                            FdAccountDetailPojo pojo = new FdAccountDetailPojo();

                            pojo.setAcNo(acno);
                            pojo.setCustName(custN);
                            pojo.setFatherName(fName);
                            pojo.setPerAddr(addr);
                            pojo.setPanNo(panNo);
                            pojo.setPrnAmt(new BigDecimal(record.get(0).toString()));
                            pojo.setMatAmt(new BigDecimal(record.get(1).toString()));
                            pojo.setRoiAmt(new BigDecimal(record.get(2).toString()));
                            pojo.setIntAmt(new BigDecimal(record.get(3).toString()));
                            fdAcNoDetailList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return fdAcNoDetailList;
    }

    public List<CrDrBalancePojo> getMonthWiseCrDrBal(String brnCode, String acctType, String balType, String frDt, String toDt, String orderBy) throws ApplicationException {
        List<CrDrBalancePojo> crdrList = new ArrayList<CrDrBalancePojo>();
        List result = new ArrayList();
        String orBy;
        try {
            if (orderBy.equalsIgnoreCase("0")) {
                orBy = "c.acno";
            } else {
                orBy = "c.dt";
            }

            if (brnCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select c.acno,custname,c.dt,cast(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0)as decimal(25,2)) from ca_recon c ,accountmaster a where  "
                        + "substring(c.acno,1,2) = a.CurBrcode and a.acno = c.acno and dt between '" + frDt + "' and '" + toDt + "' and substring(c.acno,3,2) ='" + acctType + "' "
                        + "group by c.acno,a.custname,c.dt,c.cramt having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = '" + balType + "' order by " + orBy + " ").getResultList();
            } else {
                result = em.createNativeQuery("select c.acno,custname,c.dt,cast(ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0)as decimal(25,2)) from ca_recon c ,accountmaster a where substring(c.acno,1,2)='" + brnCode + "' "
                        + "and substring(c.acno,1,2) = a.CurBrcode and a.acno = c.acno and dt between '" + frDt + "' and '" + toDt + "' and substring(c.acno,3,2) ='" + acctType + "' "
                        + "group by c.acno,a.custname,c.dt,c.cramt having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = '" + balType + "' order by " + orBy + " ").getResultList();
            }
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Vector resultSet = (Vector) result.get(i);
                    CrDrBalancePojo pojo = new CrDrBalancePojo();
                    pojo.setAcNo(resultSet.get(0).toString());
                    pojo.setCustName(resultSet.get(1).toString());
                    pojo.setDate(resultSet.get(2).toString().substring(8, 10) + "/" + resultSet.get(2).toString().substring(5, 7) + "/" + resultSet.get(2).toString().substring(0, 4));
                    pojo.setBal(new BigDecimal(resultSet.get(3).toString()));
                    crdrList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return crdrList;
    }

    public List<TopDepositorBorrowerPojo> getTopDepositorBorrower(String brnCode, String opt, String frdate, String todate, int nos, String repType, String bnkBranch, String borrowerType, double frmAmt, double toAmt) throws ApplicationException {
        List<TopDepositorBorrowerPojo> topDepBowList = new ArrayList<TopDepositorBorrowerPojo>();
        List dataList = new ArrayList();
        List acList = new ArrayList();
        List list = new ArrayList();
        List<TopDepositorBorrowerPojo> sortList = new ArrayList<TopDepositorBorrowerPojo>();
        List<NpaStatusReportPojo> npaList = new ArrayList<NpaStatusReportPojo>();
        List custIdList = new ArrayList();
        List acnoList = new ArrayList();
        String table1 = "", table2 = "";
        try {
//            System.out.println("Top Start:"+new Date());
            if (repType.equalsIgnoreCase("Customer Id")) {
                List<TopDepositorBorrowerPojo> reportList = getTopDepositorBorrowerN(brnCode, opt, frdate, todate, nos, repType, bnkBranch, borrowerType, frmAmt, toAmt);
                for (int i = 0; i < reportList.size(); i++) {
                    TopDepositorBorrowerPojo val = reportList.get(i);
                    TopDepositorBorrowerPojo pojo = new TopDepositorBorrowerPojo();

                    pojo.setCustId(val.getCustId());
                    pojo.setAcNo(val.getAcNo());
//                    List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from  loan_oldinterest "
//                            + "where acno =  '" + val.getAcNo() + "' and txnid = "
//                            + "(select min(TXNID) from loan_oldinterest where acno =  '" + val.getAcNo() + "' and enterdate>'" + todate + "' ) ").getResultList();
//                    if (!sanctionLimitDtList.isEmpty()) {
//                        Vector vist = (Vector) sanctionLimitDtList.get(0);
////                        System.out.println("a" + vist.get(0).toString());
//                        pojo.setDepositAmt(new BigDecimal(vist.get(1).toString()));
//                    } else {
//                        pojo.setDepositAmt(val.getDepositAmt());
//                    }
                    if (opt.equalsIgnoreCase("Sanction Wise")) {
                        pojo.setDepositAmt(val.getOutStandingBal()); //Setting the outsatnding inwhich credit bal account have 0
                        pojo.setCreditAmt(val.getCreditAmt());      // If A/c have Credit bal then value settring
                        if (val.getSanctionLimitDt().equalsIgnoreCase("19000101")) {
                            pojo.setOutStandingBal(val.getDepositAmt()); //Setting Senction Amount
                        } else {
                            pojo.setOutStandingBal(val.getSanctionLimit());
                        }
                    } else if (opt.equalsIgnoreCase("Borrower") || opt.equalsIgnoreCase("Borrower Wise Balance")) {
                        pojo.setDepositAmt(val.getOutStandingBal());
                        pojo.setCreditAmt(val.getCreditAmt());
                        if (val.getSanctionLimitDt().equalsIgnoreCase("19000101")) {
                            pojo.setOutStandingBal(val.getDepositAmt());
                        } else {
                            pojo.setOutStandingBal(val.getSanctionLimit());
                        }
                    } else {
                        pojo.setCreditAmt(new BigDecimal(0)); // handling of null  
                        pojo.setOutStandingBal(val.getOutStandingBal());
                        pojo.setDepositAmt(val.getDepositAmt());

                    }
                    // pojo.setDepositAmt(new BigDecimal(vtr.get(1).toString()));
                    pojo.setDeositorName(val.getDeositorName());
                    pojo.setFatherName(val.getFatherName());
                    pojo.setPan(val.getPan());
                    pojo.setAddress(val.getAddress());
                    pojo.setsNo(val.getsNo());
                    pojo.setPrint(val.getPrint());
                    pojo.setGroupId(val.getGroupId());
                    pojo.setGroupName(val.getGroupName());
                    if (val.getOverDueAmt() == null) {
                        pojo.setOverDueAmt(new BigDecimal("0"));
                    } else {
                        pojo.setOverDueAmt(val.getOverDueAmt());
                    }
                    topDepBowList.add(pojo);

                }
            } else if (repType.equalsIgnoreCase("Account No")) {

                if (opt.equalsIgnoreCase("Borrower")) {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.dramt) as decimal(25,2)) from (select acno,sum(dramt) as dramt "
                                + "from ca_recon where substring(acno,3,2) not in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C') and dt between '" + frdate + "'and '" + todate + "'group by acno union "
                                + "select acno,sum(dramt) as dramt from loan_recon where dt between '" + frdate + "'and '" + todate + "' group by acno) a "
                                + "group by a.acno order by sum(a.dramt) desc limit " + nos).getResultList();
                    } else {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.dramt) as decimal(25,2)) from (select acno,sum(dramt) as dramt "
                                + "from ca_recon where substring(acno,1,2)= '" + brnCode + "' and substring(acno,3,2) not in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C') "
                                + "and dt between '" + frdate + "'and '" + todate + "'group by acno union select acno,sum(dramt) as dramt from loan_recon where substring(acno,1,2)= '" + brnCode + "' "
                                + "and dt between '" + frdate + "'and '" + todate + "' group by acno) a group by a.acno order by sum(a.dramt) desc limit " + nos).getResultList();

                    }
                } else if (opt.equalsIgnoreCase("Depositor")) {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.cramt) as decimal(25,2)) from (select acno,sum(cramt) as cramt from recon  "
                                + "where  dt between '" + frdate + "'and '" + todate + "' group by acno union select acno,sum(cramt) as cramt from ca_recon  where substring(acno,3,2) in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C') and dt between '" + frdate + "'and '" + todate + "' group by acno "
                                + "union select acno,sum(cramt) as cramt from rdrecon  where  dt between '" + frdate + "'and '" + todate + "' group by acno union select acno,sum(cramt) as cramt "
                                + "from td_recon  where  dt between '" + frdate + "'and '" + todate + "' group by acno) a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();

                    } else {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.cramt) as decimal(25,2)) from (select acno,sum(cramt) as cramt from recon  "
                                + "where substring(acno,1,2)='" + brnCode + "' and dt between '" + frdate + "'and '" + todate + "' group by acno union select acno,sum(cramt) as cramt from "
                                + "ca_recon  where substring(acno,1,2)='" + brnCode + "' and substring(acno,3,2) in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C')and dt between '" + frdate + "'and '" + todate + "' group by acno union select acno,sum(cramt) as cramt "
                                + "from rdrecon  where substring(acno,1,2)='" + brnCode + "' and dt between '" + frdate + "'and '" + todate + "' group by acno union select acno,sum(cramt) as cramt "
                                + "from td_recon  where substring(acno,1,2)='" + brnCode + "' and dt between '" + frdate + "'and '" + todate + "' group by acno) "
                                + "a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    }
                } else if (opt.equalsIgnoreCase("Deposit Wise Balance")) {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.cramt) as decimal(25,2)) from (select acno,sum(cramt-dramt) as cramt from recon where dt <= '" + todate + "' group by acno "
                                + "union select acno,sum(cramt-dramt) as cramt from ca_recon where substring(acno,3,2) in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C') and dt <= '" + todate + "' group by acno "
                                + "union select acno,sum(cramt-dramt) as cramt from rdrecon  where dt <= '" + todate + "' group by acno "
                                + "union select acno,sum(cramt-dramt) as cramt from td_recon  where closeflag is null and trantype<>27 and dt <= '" + todate + "' group by acno order by cramt) "
                                + "a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    } else {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.cramt) as decimal(25,2)) from (select acno,sum(cramt-dramt) as cramt from recon where substring(acno,1,2)='" + brnCode + "' and dt <='" + todate + "' group by acno "
                                + "union select acno,sum(cramt-dramt) as cramt from ca_recon  where substring(acno,1,2)='" + brnCode + "' and substring(acno,3,2) in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C')and dt <= '" + todate + "' group by acno "
                                + "union select acno,sum(cramt-dramt) as cramt from rdrecon  where substring(acno,1,2)='" + brnCode + "' and dt <= '" + todate + "' group by acno "
                                + "union select acno,sum(cramt-dramt) as cramt from td_recon  where substring(acno,1,2)='" + brnCode + "' and closeflag is null and trantype<>27 and dt <= '" + todate + "' group by acno order by cramt) "
                                + "a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    }
                } else if (opt.equalsIgnoreCase("Borrower Wise Balance")) {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.cramt) as decimal(25,2)) from (select acno,sum(dramt-cramt) as cramt "
                                + "from loan_recon  where dt <= '" + todate + "' group by acno "
                                + "union "
                                + "select acno,sum(dramt-cramt) as cramt from ca_recon  where substring(acno,3,2) not in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C')  and dt <= '" + todate + "' group by acno"
                                + ") a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    } else {
                        dataList = em.createNativeQuery("select a.acno,cast(sum(a.cramt) as decimal(25,2)) from (select acno,sum(dramt-cramt) as cramt "
                                + "from loan_recon  where substring(acno,1,2)='" + brnCode + "' and dt <= '" + todate + "' group by acno "
                                + " union "
                                + "select acno,sum(dramt-cramt) as cramt from ca_recon  where substring(acno,1,2)='" + brnCode + "' and substring(acno,3,2) not in(select acctCode from accounttypemaster where acctnature = '" + CbsConstant.CURRENT_AC + "' and CrDbFlag = 'C')  and dt <= '" + todate + "' group by acno) "
                                + "a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    }
                } else if (opt.equalsIgnoreCase("Sanction Wise")) {
                    if (brnCode.equalsIgnoreCase("0A")) {
                        dataList = em.createNativeQuery("select a.acno,cast(a.cramt as decimal(25,2)) from (select acno,ifnull(Sanctionlimit,0) as cramt from loan_appparameter  where acno in(select acno from accountmaster where openingdt <='" + todate + "' and (closingdate is null or closingdate='' or closingdate > '" + todate + "'))  group by acno ) a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    } else {
                        dataList = em.createNativeQuery("select a.acno,cast(a.cramt as decimal(25,2)) from (select acno,ifnull(Sanctionlimit,0) as cramt from loan_appparameter  where acno in(select acno from accountmaster where openingdt <='" + todate + "' and (closingdate is null or closingdate='' or closingdate > '" + todate + "') and curbrcode = '" + brnCode + "')  group by acno ) a group by a.acno order by sum(a.cramt) desc limit " + nos).getResultList();
                    }
                }
                int sNo = 0;
                if (dataList.size() > 0) {
                    for (int i = 0; i < dataList.size(); i++) {
                        sNo = sNo + 1;
                        Vector record = (Vector) dataList.get(i);
                        TopDepositorBorrowerPojo pojo = new TopDepositorBorrowerPojo();
                        String acNo = record.get(0).toString();
//                        List custidList = em.createNativeQuery("select distinct CustId from customerid where acno = '" + acNo + "'").getResultList();
//                        Vector ele = (Vector) custidList.get(0);
//                        String custId = ele.get(0).toString();
                        String acNat = ftsPosting.getAcNatureByCode(acNo.substring(2, 4));
                        if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            table1 = "td_accountmaster";
                            table2 = "td_customermaster";
                        } else {
                            table1 = "accountmaster";
                            table2 = "customermaster";
                        }
//                        list = em.createNativeQuery("Select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,'') from " + table1 + "  td, "
//                                + table2 + " tc where td.acno = '" + acNo + "' and concat(brncode,actype,custno,agcode)=td.acno").getResultList();
                        list = em.createNativeQuery("select ''acno, custfullname,concat(PerAddressLine1,' ',PerAddressLine2) address,PAN_GIRNumber,concat(fathername,' ',FatherMiddleName,' ',FatherLastName)fatherName "
                                + "from cbs_customer_master_detail where customerid = (select custid from customerid where acno = '" + acNo + "')").getResultList();
                        Vector data = (Vector) list.get(0);
                        String custName = data.get(1).toString();
                        String address = data.get(2).toString();
                        String pan = data.get(3).toString();
                        String fatherName = data.get(4).toString();

                        pojo.setAcNo(acNo);
                        pojo.setDeositorName(custName);
                        pojo.setFatherName(fatherName);
                        pojo.setAddress(address);
                        pojo.setPan(pan);
                        pojo.setCustId("- -");
                        pojo.setsNo(sNo);
                        pojo.setDepositAmt(new BigDecimal(record.get(1).toString()));
                        if (opt.equalsIgnoreCase("Sanction Wise")) {
                            List sanctionLimitDtList = em.createNativeQuery("select  b.acno, b.actuallimt, b.enterdate, b.TXNID from "
                                    + " (select ifnull(a.acno,'') as acno, (select aclimit from  loan_oldinterest where acno = a.acno and txnid = a.TXNID) as actuallimt, "
                                    + " min(a.enterdate) as enterdate, a.TXNID as TXNID from (select acno, aclimit, date_format(enterdate,'%Y%m%d') as enterdate, max(TXNID) as TXNID  "
                                    + " from loan_oldinterest where acno = '" + acNo + "' group by   acno, date_format(enterdate,'%Y%m%d')) a where a.acno = '" + acNo + "' "
                                    + " and a.enterdate>'" + todate + "' order by a.enterdate ) b where b.acno <>''  ").getResultList();
                            if (!sanctionLimitDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                pojo.setDepositAmt(new BigDecimal(vist.get(1).toString()));
                            } else {
                                pojo.setDepositAmt(new BigDecimal(record.get(1).toString()));
                            }
                            pojo.setOutStandingBal(new BigDecimal(common.getBalanceOnDate(acNo, todate)));
                            pojo.setCreditAmt(new BigDecimal(0.0));
                        }
                        topDepBowList.add(pojo);
                    }
                }
            } else if (repType.equalsIgnoreCase("Npa")) {
                // New Code Top Npa Balance Wise
                List<NpaStatusReportPojo> result = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", frdate, todate, "", brnCode, "Y");
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        NpaStatusReportPojo obj = result.get(i);
                        NpaStatusReportPojo pojo = new NpaStatusReportPojo();
                        pojo.setAcno(obj.getAcno());
                        pojo.setCustName(obj.getCustName());
                        pojo.setBalance(obj.getBalance());
                        pojo.setPresentStatus(obj.getPresentStatus());
                        pojo.setStandardDt(obj.getStandardDt());
                        pojo.setSubStandardDt(obj.getSubStandardDt());
                        pojo.setDoubtFullDt(obj.getDoubtFullDt());
                        pojo.setLossDt(obj.getLossDt());

                        npaList.add(obj);
                    }
                    ComparatorChain chObj = new ComparatorChain();
                    chObj.addComparator(new SortedByNpaBal());
                    Collections.sort(npaList, chObj);
                    for (int m = 0; m < (nos < npaList.size() ? nos : npaList.size()); m++) {
                        NpaStatusReportPojo obj1 = npaList.get(m);
                        TopDepositorBorrowerPojo pojo = new TopDepositorBorrowerPojo();

                        pojo.setAcNo(obj1.getAcno());
                        pojo.setDeositorName(obj1.getCustName());
                        List sanctionLimitDtList = em.createNativeQuery("select  b.acno, b.actuallimt, b.enterdate, b.TXNID from "
                                + " (select ifnull(a.acno,'') as acno, (select aclimit from  loan_oldinterest where acno = a.acno and txnid = a.TXNID) as actuallimt, "
                                + " min(a.enterdate) as enterdate, a.TXNID as TXNID from (select acno, aclimit, date_format(enterdate,'%Y%m%d') as enterdate, max(TXNID) as TXNID  "
                                + " from loan_oldinterest where acno = '" + obj1.getAcno() + "' group by   acno, date_format(enterdate,'%Y%m%d')) a where a.acno = '" + obj1.getAcno() + "' "
                                + " and a.enterdate>'" + todate + "' order by a.enterdate ) b where b.acno <>''   ").getResultList();
                        if (!sanctionLimitDtList.isEmpty()) {
                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                            pojo.setDepositAmt(new BigDecimal(vist.get(0).toString()));
                        } else {
                            pojo.setDepositAmt(obj1.getBalance());
                        }
                        pojo.setAddress(obj1.getPresentStatus());
                        if (obj1.getPresentStatus().substring(0, 3).equalsIgnoreCase("STA")) {
                            pojo.setPan(obj1.getStandardDt());
                        } else if (obj1.getPresentStatus().substring(0, 3).equalsIgnoreCase("SUB")) {
                            pojo.setPan(obj1.getSubStandardDt());
                        } else if (obj1.getPresentStatus().substring(0, 3).equalsIgnoreCase("DOU")) {
                            pojo.setPan(obj1.getDoubtFullDt());
                        } else if (obj1.getPresentStatus().substring(0, 3).equalsIgnoreCase("LOS")) {
                            pojo.setPan(obj1.getLossDt());
                        }
                        pojo.setRepType("Npa");
                        pojo.setCustId("");
                        topDepBowList.add(pojo);
                        //System.out.print("Acno " + obj1.getAcno() + " CustName " + obj1.getCustName() + " Balance " + obj1.getBalance() + " Persent Status " + obj1.getPresentStatus());
                    }
                }
            }
//            System.out.println("Top End:"+new Date());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return topDepBowList;
    }

    public List<TopDepositorBorrowerPojo> getTopDepositorBorrowerN(String brnCode, String opt, String frdate, String todate, int nos, String repType, String bnkBranch, String borrowerType, double frmAmt, double toAmt) throws ApplicationException {
        List<TopDepositorBorrowerPojo> topDepBowList = new ArrayList<TopDepositorBorrowerPojo>();
        List dataList = new ArrayList();
        try {
            List<OverdueEmiReportPojo> overDueDetails = new ArrayList<OverdueEmiReportPojo>();
            List<OverdueNonEmiResultList> resultList1 = new ArrayList<OverdueNonEmiResultList>();
            List<OverdueNonEmiResultList> resultList2 = new ArrayList<OverdueNonEmiResultList>();
            String borroerTypeQuery = "", borroerTypeHisQuery = "";
            if (borrowerType.equalsIgnoreCase("1")) {
                borroerTypeQuery = " and ifnull(a.groupid,'0') in ('0') ";
                borroerTypeHisQuery = " and ifnull(a.groupid,'0') in ('0') ";
            } else if (borrowerType.equalsIgnoreCase("2")) {
                borroerTypeQuery = " and ifnull(a.groupid,'0') not in ('0') ";
                borroerTypeHisQuery = " and ifnull(a.groupid,'0') not in ('0') ";
            }
            if (!(opt.equalsIgnoreCase("Depositor") || opt.equalsIgnoreCase("Deposit Wise Balance"))) {
                overDueDetails = overDueRemote.getOverdueEmiReport(1, 0, "", "ALL", 1, 5000, todate, brnCode, "", false, "", "N");
                resultList1 = overDueRemote.getOverDueNonEmi("CA", "ALL", todate, brnCode, "N");
                resultList2 = overDueRemote.getOverDueNonEmi("DL", "ALL", todate, brnCode, "N");
            }
            String brnQuery = "", brnQueryDeposit = "";
            if (!brnCode.equalsIgnoreCase("0A")) {
                brnQueryDeposit = " where substring(a.aacno,1,2)='" + brnCode + "' ";
                brnQuery = " and substring(cc.acno,1,2)='" + brnCode + "' ";
            }
//            if (repType.equalsIgnoreCase("Customer Id")) {
////                if (brnCode.equalsIgnoreCase("0A")) {
            if (opt.equalsIgnoreCase("Depositor") || opt.equalsIgnoreCase("Deposit Wise Balance")) {
                String crAmt = (opt.equalsIgnoreCase("Depositor") ? "r.cramt" : "r.cramt-r.dramt");
                String dtQuery = (opt.equalsIgnoreCase("Depositor") ? " between '" + frdate + "' and '" + todate + "' " : " <= '" + todate + "' ");
                String query = "select a.cuId,sum(a.crrAmt),b.custfullname,"
                        + "concat(b.fathername,' ',b.FatherMiddleName,' ',b.FatherLastName)fatherName ,"
                        + "b.PAN_GIRNumber,b.PerAddressLine1,0,'','','' from "
                        + "(select a.cuId, aacno, sum(a.crrAmt)as crrAmt from"
                        + "(select f2.cId as cuId, f1.aacno, sum(f1.cramt)as crrAmt from"
                        + "(select r.acno as aacno, cast(sum(" + crAmt + ")as decimal(25,2)) as cramt  from recon r,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)c "
                        + "where r.acno=c.acn and r.dt " + dtQuery + " group by r.acno )f1,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)f2 "
                        + "where f1.aacno = f2.acn group by f2.cId, f1.aacno "
                        + "union "
                        + "select f2.cId as cuId, f1.aacno, sum(f1.cramt)as crrAmt from"
                        + "(select r.acno as aacno,cast(sum(" + crAmt + ")as decimal(25,2)) as cramt from ca_recon r,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)c "
                        + "where r.acno=c.acn and r.dt " + dtQuery + " group by r.acno )f1,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)f2 "
                        + "where f1.aacno = f2.acn group by f2.cId, f1.aacno "
                        + "union "
                        + "select f2.cId as cuId, f1.aacno, sum(f1.cramt)as crrAmt from"
                        + "(select r.acno as aacno,cast(sum(" + crAmt + ")as decimal(25,2)) as cramt  from rdrecon r,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)c "
                        + "where r.acno=c.acn and r.dt " + dtQuery + " group by r.acno )f1,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)f2 "
                        + "where f1.aacno = f2.acn group by f2.cId, f1.aacno "
                        + "union "
                        + "select f2.cId as cuId, f1.aacno, sum(f1.cramt)as crrAmt from"
                        + "(select r.acno as aacno,cast(sum(" + crAmt + ")as decimal(25,2)) as cramt  from td_recon r,"
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)c "
                        + "where r.acno=c.acn and r.dt " + dtQuery + " and closeflag is null and trantype<>27 group by r.acno )f1, "
                        + "(select acno as acn,custid as cId from customerid c,"
                        + "(select AcctCode as acType from accounttypemaster where CrDbFlag in('C','B'))t where substring(c.acno,3,2)= t.acType)f2 "
                        + "where f1.aacno = f2.acn group by f2.cId, f1.aacno)a " + brnQueryDeposit + " group by a.cuId)a, "
                        + "cbs_customer_master_detail b where  a.cuId = b.customerid group by a.cuId order by sum(a.crrAmt) desc limit " + nos;
                dataList = em.createNativeQuery(query).getResultList();
//                System.out.println("Query:"+query);
            } else {
                String query = "select aa.* from (select a.custId, a.bal, b.custfullname, "
                        + " concat(b.fathername,' ',b.FatherMiddleName,' ',b.FatherLastName)fatherName, b.PAN_GIRNumber, \n"
                        + " b.PerAddressLine1, ifnull(count(a.acno),0), \n"
                        + " ifnull(cc.groupid,1), ifnull(cc.groupCustID,'0') as groupCustID, \n"
                        + " b.custfullname as custName, \n"
                        + " a.SanctionLimitDt, cc.odlimit, cc.bal as outstand from \n"
                        + " (select bor.*, if(bor.bal<0,0,bor.bal) as outstand, SanctionLimitDt, \n"
                        + " if(sf.acTypeForStaff = substring( bor.acNo,3,2),0,cast(ifnull(ol.acLimit, lp.odlimit) as decimal(25,2)))as odLimit, sf.acTypeForStaff  from  \n /*For Staff Interest Loan ODLImit is set ZERO*/ "
                        + " (select bor.custId, bor.acNo, bor.custName, bor.groupID,  \n"
                        + " (if(ifnull(bor.groupID,'0') = '0' , bor.custId, \n"
                        + " if((bor.groupID = '1' and (bor.groupCustID is null OR bor.groupCustID ='0')), bor.custId, bor.groupCustID))) as groupCustID, bor.acctnature,\n"
                        + (opt.equalsIgnoreCase("Borrower") ? " "
                        + " ifnull(if(bor.acctnature = 'CA', \n"
                        + " (select cast((sum(ifnull(dramt,0))) as decimal(25,2)) from ca_recon   where auth= 'Y' and trandesc in (6) and acno = bor.acNo and dt between  '" + frdate + "' and  '" + todate + "'), \n"
                        + " (select cast((sum(ifnull(dramt,0))) as decimal(25,2)) from loan_recon where auth= 'Y' and trandesc in (6) and acno = bor.acNo and dt between  '" + frdate + "' and  '" + todate + "')),0) as bal\n "
                        : " ifnull(if(bor.acctnature = 'CA', \n"
                        + " (select cast((sum(ifnull(dramt,0)) - sum(ifnull(cramt,0))) as decimal(25,2)) from ca_recon   where auth= 'Y' and acno = bor.acNo and dt <= '" + todate + "'), \n"
                        + " (select cast((sum(ifnull(dramt,0)) - sum(ifnull(cramt,0))) as decimal(25,2)) from loan_recon where auth= 'Y' and acno = bor.acNo and dt <= '" + todate + "')),0) as bal\n")
                        + " from  \n"
                        + " (select r.custId,r.acNo, r.custName, r.openingdt, r.updateDt, r.version,r.groupID,r.groupCustID,r.acctnature  from  \n"
                        + " (select a.cust_ID as custId,am.acno  as acNo, am.custName, ifnull(a.loan_duration,0) as loanDuration,  \n"
                        + " ci.custid as cid, am.openingdt as openingdt, ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt,  \n"
                        + " a.TOTAL_MODIFICATIONS as version, a.groupID, a.groupCustID, atm.acctnature from cbs_loan_borrower_details a, accountmaster am, \n"
                        + " accounttypemaster atm, customerid ci \n"
                        + " where  substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') )  \n"
                        + " and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno  and ci.Acno = am.ACNo   \n" + borroerTypeQuery
                        + " and am.openingdt <= '" + todate + "' and (ifnull(am.closingdate,'')='' or am.closingdate > '" + todate + "')   \n"
                        + " union all   \n"
                        + " select a.cust_ID as custId,am.acno as acNo, am.custName, ifnull(a.loan_duration,0) as loanDuration,  ci.custid as cid, am.openingdt as openingdt, \n"
                        + " ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt,  a.TOTAL_MODIFICATIONS as version, a.groupID, a.groupCustID, atm.acctnature  \n"
                        + " from cbs_loan_borrower_details_history a , accountmaster am, accounttypemaster atm , customerid ci \n"
                        + " where  substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') )  \n"
                        + " and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and ci.Acno = am.ACNo   \n" + borroerTypeQuery
                        + " and am.openingdt <= '" + todate + "' and (ifnull(am.closingdate,'')='' or am.closingdate > '" + todate + "') ) r  \n"
                        + " left join  \n"
                        + " loandisbursement ld on r.acNo = ld.ACNO,  cbs_customer_master_detail cd where  r.cid = cd.customerid  group by r.acno, r.updateDt, r.version ) bor,   \n"
                        + " (select mo.acno, max(mo.modi) as modifi from   (select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g  \n"
                        + " where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + todate + "' group by g.ACC_NO   \n"
                        + " union all   \n"
                        + " select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g  \n"
                        + " where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + todate + "' group by g.ACC_NO) mo group by mo.acno) ver   \n"
                        + " where bor.acno = ver.acno and bor.version = ver.modifi order by  groupCustID ) bor \n"
                        + " left join    \n"
                        + " (select acno as oldAcno, SanctionLimitDt, acLimit from  loan_oldinterest lo,  \n"
                        + " (select acno as oldAcno1, min(TXNID) as txnId from loan_oldinterest where enterdate>'" + todate + "' group by acno) ll \n"
                        + " where  ll.oldAcno1 = lo.acno and lo.TXNID= ll.txnId) ol on bor.acno = ol.oldAcno \n"
                        + " left join  \n"
                        + " (select acno, cast(ifnull(odlimit,0) as decimal(25,2)) as odlimit from loan_appparameter group by acno) lp on lp.acno = bor.acno  \n"
                        + " left join \n"
                        + " (select AcctCode, Ac_Type_Open_In_Staff as acTypeForStaff from accounttypemaster where Int_Ac_Open_Enable_In_Staff = 'Y' ) sf on substring(bor.acno,3,2) = sf.acTypeForStaff \n"
                        + " /*where bor.groupCustID = '9297' */ \n"
                        + " )a, cbs_customer_master_detail b, \n"
                        + " (select bor.groupCustID, bor.custId, bor.acctnature, bor.acNo, bor.custName, bor.groupID, bor.SanctionLimitDt, sum(bor.bal) as bal, sum(bor.odLimit) as odLimit from  \n"
                        + " (select bor.*, if(bor.bal<0,0,bor.bal) as outstand, SanctionLimitDt, "
                        + " if(sf.acTypeForStaff = substring( bor.acNo,3,2),0,cast(ifnull(ol.acLimit, lp.odlimit) as decimal(25,2)))as odLimit, sf.acTypeForStaff  from  \n /*For Staff Interest Loan ODLImit is set ZERO*/ "
                        + " (select bor.custId, bor.acNo, bor.custName, bor.groupID,   "
                        + " (if(ifnull(bor.groupID,'0') = '0' , bor.custId, \n"
                        + " if((bor.groupID = '1' and (bor.groupCustID is null OR bor.groupCustID ='0')), bor.custId, bor.groupCustID))) as groupCustID, bor.acctnature, \n"
                        + (opt.equalsIgnoreCase("Borrower") ? " "
                        + " ifnull(if(bor.acctnature = 'CA', \n"
                        + " (select cast((sum(ifnull(dramt,0))) as decimal(25,2)) from ca_recon   where auth= 'Y' and acno = bor.acNo and trandesc in (6) and dt between  '" + frdate + "' and  '" + todate + "'), \n"
                        + " (select cast((sum(ifnull(dramt,0))) as decimal(25,2)) from loan_recon where auth= 'Y' and acno = bor.acNo and trandesc in (6) and dt between  '" + frdate + "' and  '" + todate + "')),0) as bal\n "
                        : " ifnull(if(bor.acctnature = 'CA', \n"
                        + " (select cast((sum(ifnull(dramt,0)) - sum(ifnull(cramt,0))) as decimal(25,2)) from ca_recon   where auth= 'Y' and acno = bor.acNo and dt <= '" + todate + "'), \n"
                        + " (select cast((sum(ifnull(dramt,0)) - sum(ifnull(cramt,0))) as decimal(25,2)) from loan_recon where auth= 'Y' and acno = bor.acNo and dt <= '" + todate + "')),0) as bal\n")
                        + " from   "
                        + " (select r.custId,r.acNo, r.custName, r.openingdt, r.updateDt, r.version,r.groupID,r.groupCustID,r.acctnature  from   \n"
                        + " (select a.cust_ID as custId,am.acno  as acNo, am.custName, ifnull(a.loan_duration,0) as loanDuration,   \n"
                        + " ci.custid as cid, am.openingdt as openingdt, ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt,   \n"
                        + " a.TOTAL_MODIFICATIONS as version, a.groupID, a.groupCustID, atm.acctnature from cbs_loan_borrower_details a, accountmaster am,  \n"
                        + " accounttypemaster atm, customerid ci  "
                        + " where  substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') )   \n"
                        + " and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno  and ci.Acno = am.ACNo   \n"
                        + borroerTypeQuery
                        + " and am.openingdt <= '" + todate + "' and (ifnull(am.closingdate,'')='' or am.closingdate > '" + todate + "')    \n"
                        + " union all    "
                        + " select a.cust_ID as custId,am.acno as acNo, am.custName, ifnull(a.loan_duration,0) as loanDuration,  ci.custid as cid, am.openingdt as openingdt,  \n"
                        + " ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt,  a.TOTAL_MODIFICATIONS as version, a.groupID, a.groupCustID, atm.acctnature   \n"
                        + " from cbs_loan_borrower_details_history a , accountmaster am, accounttypemaster atm , customerid ci  \n"
                        + " where  substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') )   \n"
                        + " and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and ci.Acno = am.ACNo   \n"
                        + borroerTypeHisQuery
                        + " and am.openingdt <= '" + todate + "' and (ifnull(am.closingdate,'')='' or am.closingdate > '" + todate + "') ) r   \n"
                        + " left join   \n"
                        + " loandisbursement ld on r.acNo = ld.ACNO,  cbs_customer_master_detail cd where  r.cid = cd.customerid  group by r.acno, r.updateDt, r.version ) bor,    \n"
                        + " (select mo.acno, max(mo.modi) as modifi from   (select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g   \n"
                        + " where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + todate + "' group by g.ACC_NO    \n"
                        + " union all    \n"
                        + " select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g   \n"
                        + " where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + todate + "' group by g.ACC_NO) mo group by mo.acno) ver    \n"
                        + " where bor.acno = ver.acno and bor.version = ver.modifi order by  groupCustID ) bor  "
                        + " left join     \n"
                        + " (select acno as oldAcno, SanctionLimitDt, acLimit from  loan_oldinterest lo,   \n"
                        + " (select acno as oldAcno1, min(TXNID) as txnId from loan_oldinterest where enterdate>'" + todate + "' group by acno) ll  \n"
                        + " where  ll.oldAcno1 = lo.acno and lo.TXNID= ll.txnId) ol on bor.acno = ol.oldAcno  \n"
                        + " left join   \n"
                        + " (select acno, cast(ifnull(odlimit,0) as decimal(25,2)) as odlimit from loan_appparameter group by acno) lp on lp.acno = bor.acno   \n"
                        + " left join \n"
                        + " (select AcctCode, Ac_Type_Open_In_Staff as acTypeForStaff from accounttypemaster where Int_Ac_Open_Enable_In_Staff = 'Y' ) sf on substring(bor.acno,3,2) = sf.acTypeForStaff \n"
                        + " /*where bor.groupCustID = '9297' or bor.custId = '9691'*/ "
                        + " ) bor group by \n"
                        + (borrowerType.equalsIgnoreCase("1") ? " bor.custId having " : " bor.groupCustID having ")
                        + (opt.equalsIgnoreCase("Sanction Wise") ? " sum(bor.odLimit) " : " sum(bor.outstand) ")
                        + " between " + frmAmt + " and " + toAmt + " ) cc where \n"
                        + (borrowerType.equalsIgnoreCase("1") ? " cc.custId=a.custId " : " cc.groupCustID= a.groupCustID ")
                        + " and a.custId = b.customerid "
                        + brnQuery
                        + " group by " + (borrowerType.equalsIgnoreCase("1") ? " a.custId " : " cc.groupCustID ")
                        + " order by cast(" + (borrowerType.equalsIgnoreCase("1") ? " a.custId " : " cc.groupCustID ") + " as decimal))aa order by "
                        + (opt.equalsIgnoreCase("Sanction Wise") ? " aa.odLimit " : " aa.outstand ") + " desc limit " + nos;
//                System.out.println("query>>" + query);

                dataList = em.createNativeQuery(query).getResultList();
            }
            List acnoList;
            if (!dataList.isEmpty()) {
                String acNo = "", print = "";
                int sNo = 0;
                for (int i = 0; i < dataList.size(); i++) {
                    Vector vtr = (Vector) dataList.get(i);
                    // TopDepositorBorrowerPojo pojo = new TopDepositorBorrowerPojo();
                    String cId = vtr.get(0).toString();
                    int cnt = Integer.parseInt(vtr.get(6).toString());
                    String brnQuery1 = "";
                    if (!brnCode.equalsIgnoreCase("0A")) {
                        brnQuery1 = " and an1.curbrcode = '" + brnCode + "' ";
                    }
                    if (opt.equalsIgnoreCase("Depositor")) {
                        if (i == 0) {
                            sNo = 1;
                        } else {
                            sNo = sNo + 1;
                        }
                        double odLimit = 0;
                        double outStanding = 0;

                        if (sNo > 1) {
                            print = "Y";
                        } else {
                            print = "N";
                        }
                        Vector acVector = (Vector) dataList.get(i);
                        TopDepositorBorrowerPojo pojo = new TopDepositorBorrowerPojo();
//                        acNo = acVector.get(0).toString();

//                        outStanding = common.getBalanceOnDate(acNo, todate);
                        odLimit = outStanding;
                        pojo.setSanctionLimit(new BigDecimal("0"));
                        pojo.setSanctionLimitDt("19000101");
//                        pojo.setDepositAmt(new BigDecimal(vtr.get(1).toString()));

                        pojo.setCustId(cId);
                        pojo.setAcNo(acNo);
                        //pojo.setDepositAmt(new BigDecimal((odLimit)));

                        pojo.setOutStandingBal(new BigDecimal((outStanding)));
                        if (outStanding >= 0) {
                            pojo.setCreditAmt(new BigDecimal((outStanding)));
                            pojo.setOutStandingBal(new BigDecimal((outStanding)));
                        } else {
                            pojo.setCreditAmt(new BigDecimal(0.0));
                            pojo.setOutStandingBal(new BigDecimal((outStanding)));
                        }
                        pojo.setDepositAmt(new BigDecimal(vtr.get(1).toString()));
                        pojo.setDeositorName(vtr.get(2).toString());
                        pojo.setFatherName(vtr.get(3).toString());
                        pojo.setPan(vtr.get(4).toString());
                        pojo.setAddress(vtr.get(5).toString());
                        pojo.setsNo(sNo);
                        pojo.setPrint(print);
                        pojo.setGroupId(vtr.get(8).toString());
                        pojo.setGroupName(vtr.get(9).toString());
                        pojo.setOverDueAmt(new BigDecimal("0"));
                        topDepBowList.add(pojo);
                    } else {

                        if (opt.equalsIgnoreCase("Deposit Wise Balance")) {
                            acnoList = em.createNativeQuery("select an1.acno from accountmaster an1,(select ac.acno from customerid ac,(select acctcode "
                                    + "as actcode from accounttypemaster where CrDbFlag in('C','B')) aa where ac.custid = '" + cId + "'and substring(ac.acno,3,2)= aa.actcode)an2 "
                                    + "where an1.acno = an2.acno and (an1.closingdate is null or an1.closingdate='' or an1.closingdate > '" + todate + "') "
                                    + "and an1.OpeningDt <= '" + todate + "' "
                                    + "union "
                                    + "select an1.acno from td_accountmaster an1,(select ac.acno from customerid ac,(select acctcode as actcode from "
                                    + "accounttypemaster where CrDbFlag in('C','B')) aa where ac.custid = '" + cId + "'and substring(ac.acno,3,2)= aa.actcode)an2"
                                    + " where an1.acno = an2.acno " + brnQuery1 + " and (an1.closingdate is null or an1.closingdate='' or an1.closingdate > '" + todate + "') "
                                    + "and an1.OpeningDt <= '" + todate + "'").getResultList();
                        } else {
                            String acQuery = "select an1.acno, "
                                    + " if(sf.acTypeForStaff = substring( an2.acno,3,2), 0, cast(ifnull(lo.acLimit,lp.odlimit) as decimal(25,2)))as odLimit, "
                                    + " atm.acctnature, "
                                    + " if(atm.acctnature = 'CA', "
                                    + " (select cast(ifnull((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),0) as decimal(25,2)) from ca_recon where auth= 'Y' and acno = an1.acno and dt <= '" + todate + "' ), "
                                    + " (select cast(ifnull((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),0) as decimal(25,2)) from loan_recon where auth= 'Y' and acno = an1.acno and dt <= '" + todate + "' )) as bal,"
                                    + " ifnull(date_format(lo.SanctionLimitDt,'%Y%m%d'),'19000101') as SanctionLimitDt, an1.custname, bor.custId "
                                    + " from accountmaster an1,"
                                    + " (select bor.custId, bor.acNo,  bor.groupID,  \n"
                                    + " (if(ifnull(bor.groupID,'0') = '0' , bor.custId, \n"
                                    + " if((bor.groupID = '1' and (bor.groupCustID is null OR bor.groupCustID ='0')), bor.custId, bor.groupCustID))) as groupCustID, \n"
                                    + " bor.acctnature from  \n"
                                    + " (select r.custId,r.acNo,  r.openingdt, r.updateDt, r.version,r.groupID,r.groupCustID,r.acctnature  from  \n"
                                    + " (select a.cust_ID as custId,am.acno  as acNo, ci.custid as cid, am.openingdt as openingdt, ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt,  \n"
                                    + " a.TOTAL_MODIFICATIONS as version, a.groupID, a.groupCustID, atm.acctnature from cbs_loan_borrower_details a, accountmaster am, \n"
                                    + " accounttypemaster atm, customerid ci \n"
                                    + " where  substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') )  \n"
                                    + " and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno  and ci.Acno = am.ACNo   \n"
                                    + borroerTypeQuery + "   and am.openingdt <= '" + todate + "' and (ifnull(am.closingdate,'')='' or am.closingdate > '" + todate + "')   \n"
                                    + " union all   \n"
                                    + " select a.cust_ID as custId,am.acno as acNo, ci.custid as cid, am.openingdt as openingdt, ifnull(a.LAST_UPDATED_DATE,a.CREATION_DATE) as updateDt,  \n"
                                    + " a.TOTAL_MODIFICATIONS as version, a.groupID, a.groupCustID, atm.acctnature  from cbs_loan_borrower_details_history a , accountmaster am, \n"
                                    + " accounttypemaster atm , customerid ci \n"
                                    + " where  substring(a.acc_no,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','SS','DL') and CrDbFlag in('B','D') )  \n"
                                    + " and substring(am.acno,3,2) = atm.AcctCode and a.acc_no = am.acno and ci.Acno = am.ACNo   \n"
                                    + borroerTypeQuery + " and am.openingdt <= '" + todate + "' and (ifnull(am.closingdate,'')='' or am.closingdate > '" + todate + "') ) r  \n"
                                    + " left join  \n"
                                    + " loandisbursement ld on r.acNo = ld.ACNO,  cbs_customer_master_detail cd where  r.cid = cd.customerid  group by r.acno, r.updateDt, r.version ) bor,   \n"
                                    + " (select mo.acno, max(mo.modi) as modifi from   (select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g  \n"
                                    + " where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + todate + "' group by g.ACC_NO   \n"
                                    + " union all   \n"
                                    + " select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g  \n"
                                    + " where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + todate + "' group by g.ACC_NO) mo group by mo.acno) ver   \n"
                                    + " where bor.acno = ver.acno and bor.version = ver.modifi order by  groupCustID ) bor, "
                                    + " (select ac.custid, ac.acno from customerid ac,(select acctcode as actcode "
                                    + " from accounttypemaster where CrDbFlag in('D','B')) aa "
                                    + " where substring(ac.acno,3,2)= aa.actcode)an2 "
                                    + " left join   "
                                    + " (select acno as oldAcno, SanctionLimitDt, acLimit from  loan_oldinterest lo, "
                                    + " (select acno as oldAcno1, min(TXNID) as txnId from loan_oldinterest where enterdate>'" + todate + "' group by acno) ll "
                                    + " where ll.oldAcno1 = lo.acno and lo.TXNID= ll.txnId) lo on lo.oldAcno = an2.acno  "
                                    + " left join "
                                    + " (select acno, cast(ifnull(odlimit,0) as decimal(25,2)) as odlimit from loan_appparameter group by acno) lp on lp.acno = an2.acno "
                                    + " left join "
                                    + " accounttypemaster atm on  substring(an2.acno,3,2)= atm.acctcode   "
                                    + "  left join "
                                    + " (select AcctCode, Ac_Type_Open_In_Staff as acTypeForStaff from accounttypemaster where Int_Ac_Open_Enable_In_Staff = 'Y' ) sf on substring(an2.acno,3,2) = sf.acTypeForStaff "
                                    + " where an1.acno = bor.acno and an2.custid = bor.custId and an1.acno = an2.acno " + brnQuery1 + " "
                                    + " and (an1.closingdate is null or an1.closingdate='' or an1.closingdate > '" + todate + "') "
                                    + " and " + (borrowerType.equalsIgnoreCase("1") ? " bor.custId =" + cId : " bor.groupCustID = " + vtr.get(8).toString())
                                    + " and an1.OpeningDt <= '" + todate + "' order by substring(an1.acno,5,8),substring(an1.acno,3,2)";
//                        System.out.println("acQuery:"+acQuery);
                            acnoList = em.createNativeQuery(acQuery).getResultList();
                        }

                        acNo = "";
                        print = "";
                        sNo = 0;
                        if (!acnoList.isEmpty()) {
                            for (int j = 0; j < acnoList.size(); j++) {
                                double odLimit = 0;
                                double outStanding = 0;
                                sNo = sNo + 1;

                                if (sNo > 1) {
                                    print = "Y";
                                } else {
                                    print = "N";
                                }
                                Vector acVector = (Vector) acnoList.get(j);
                                TopDepositorBorrowerPojo pojo = new TopDepositorBorrowerPojo();
                                acNo = acVector.get(0).toString();
                                if (opt.equalsIgnoreCase("Sanction Wise") || opt.equalsIgnoreCase("Borrower") || opt.equalsIgnoreCase("Borrower Wise Balance")) {
//                                System.out.println("acNo:"+acNo+"; "+acVector);
                                    outStanding = Double.parseDouble(acVector.get(3).toString()); // Oustanding 
                                    odLimit = Double.parseDouble(acVector.get(1).toString()); // Limit 
//                                if (cnt == 0 || cnt == 1) {
//                                    balance = balance;
//                                } else {
//                                    if (Math.abs(outStanding) > balance) {
//                                        balance = balance;
////                                        balance = outStanding; Changes made as per the mail from CCBL Bank (If sanction is less than outstanding then only sanction will be shown.
//                                    } else if (Math.abs(outStanding) <= balance) {
//                                        balance = balance;
//                                    }
//                                }
                                    pojo.setSanctionLimit(new BigDecimal(acVector.get(1).toString()));
                                    pojo.setSanctionLimitDt(acVector.get(4).toString());
                                    pojo.setDepositAmt(new BigDecimal((odLimit)));
                                    pojo.setCustId(acVector.get(6).toString());
                                    pojo.setDeositorName(acVector.get(5).toString());
                                } else {
                                    outStanding = common.getBalanceOnDate(acNo, todate);
                                    odLimit = outStanding;
                                    pojo.setSanctionLimit(new BigDecimal("0"));
                                    pojo.setSanctionLimitDt("19000101");
                                    pojo.setDepositAmt(new BigDecimal((outStanding)));
                                    pojo.setCustId(cId);
                                    pojo.setDeositorName(vtr.get(2).toString());
                                }
//                                pojo.setCustId(acVector.get(6).toString());
                                pojo.setAcNo(acNo);
                                //pojo.setDepositAmt(new BigDecimal((odLimit)));

                                pojo.setOutStandingBal(new BigDecimal((outStanding)));
                                if (outStanding >= 0) {
                                    pojo.setCreditAmt(new BigDecimal((outStanding)));
                                    pojo.setOutStandingBal(new BigDecimal(0.0));
                                } else {
                                    pojo.setCreditAmt(new BigDecimal(0.0));
                                    pojo.setOutStandingBal(new BigDecimal((outStanding)));
                                }
                                // pojo.setDepositAmt(new BigDecimal(vtr.get(1).toString()));
//                                pojo.setDeositorName(acVector.get(5).toString());
                                pojo.setFatherName(vtr.get(3).toString());
                                pojo.setPan(vtr.get(4).toString());
                                pojo.setAddress(vtr.get(5).toString());
                                pojo.setsNo(sNo);
                                pojo.setPrint(print);
                                pojo.setGroupId(vtr.get(8).toString());
                                pojo.setGroupName(vtr.get(9).toString());
                                if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    for (int y = 0; y < overDueDetails.size(); y++) {
                                        if (acNo.equalsIgnoreCase(overDueDetails.get(y).getAccountNumber())) {
                                            pojo.setOverDueAmt(overDueDetails.get(y).getAmountOverdue());
                                            break;
                                        }
                                    }
                                } else if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                    for (int y = 0; y < resultList2.size(); y++) {
                                        if (acNo.equalsIgnoreCase(resultList2.get(y).getAccountNo())) {
                                            pojo.setOverDueAmt(new BigDecimal(resultList2.get(y).getOverDue()));
                                            break;
                                        }
                                    }
                                } else if (common.getAcNatureByAcNo(acNo).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                    for (int y = 0; y < resultList1.size(); y++) {
                                        if (acNo.equalsIgnoreCase(resultList1.get(y).getAccountNo())) {
                                            pojo.setOverDueAmt(new BigDecimal(resultList1.get(y).getOverDue()));
                                            break;
                                        }
                                    }
                                }
                                topDepBowList.add(pojo);
                            }
                        }
                    }
                    //topDepBowList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return topDepBowList;
    }

    public String getCustId(String acNo) throws ApplicationException {
        String custId = "";
        try {
            List custIdList = em.createNativeQuery("select CustId from customerid where acno = '" + acNo + "'").getResultList();
            if (!custIdList.isEmpty()) {
                Vector vtr = (Vector) custIdList.get(0);
                custId = vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return custId;
    }

    public List<UnclaimedAccountStatementPojo> getUnclaimedAccountDetails(String brnCode, String acType, String asOnDt, String frYear, String toYear, String acNature) throws ApplicationException {
        List<UnclaimedAccountStatementPojo> dataList = new ArrayList<UnclaimedAccountStatementPojo>();
        List result = new ArrayList();
        List result1 = new ArrayList();

        try {
            String fyr = "-" + frYear;
            String tyr = "-" + toYear;

            List frDtList = em.createNativeQuery("select DATE_FORMAT(date_add('" + asOnDt + "', INTERVAL " + tyr + " YEAR),'%Y%m%d')").getResultList();
            Vector dtvtr = (Vector) frDtList.get(0);
            String frDt = dtvtr.get(0).toString();

            List toDtList = em.createNativeQuery("select DATE_FORMAT(date_add('" + asOnDt + "', INTERVAL " + fyr + " YEAR),'%Y%m%d')").getResultList();
            Vector dtvtr1 = (Vector) toDtList.get(0);
            String toDt = dtvtr1.get(0).toString();

            String acNat = "";

            if (!acType.equalsIgnoreCase("ALL")) {
                acNat = ftsPosting.getAcNatureByCode(acType);
            }
            String table2 = "";
            String table1 = "";

            if (acType.equalsIgnoreCase("ALL")) {
                table2 = common.getTableName(acNature);
                acNat = acNature;
            } else {
                table2 = common.getTableName(acNat);
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                table1 = "td_accountmaster";
            } else {
                table1 = "accountmaster";
            }
            String tranDescUnd = "";
            List tranDescSimpleList = em.createNativeQuery("select code from cbs_parameterinfo where name in ('und')").getResultList();
            if (tranDescSimpleList.isEmpty()) {
                throw new ApplicationException("Please Check data  is exists for UND in CBS_PARAMETERINFO Table");
            } else {
                Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                tranDescUnd = tranDescSimpleVect.get(0).toString();
            }
            List list;
            if (acType.equalsIgnoreCase("ALL")) {
                list = em.createNativeQuery("select acctCode from accounttypemaster where crdbflag in('C','B') and acctNature = '" + acNature + "' order by acctCode").getResultList();
            } else {
                list = em.createNativeQuery("select acctCode from accounttypemaster where acctCode = '" + acType + "'").getResultList();
            }

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    String acctCode = ele.get(0).toString();

                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            result = em.createNativeQuery("select count(a.acno) from td_vouchmst v,td_accountmaster a where  matDt between '" + frDt + "' and '" + toDt + "' and status = 'A' and v.acno = a.acno and a.acctType = '" + acctCode + "'").getResultList();
                        } else {
                            result = em.createNativeQuery("select count(a.acno) from td_vouchmst v,td_accountmaster a where a.curbrcode = '" + brnCode + "' and  matDt between '" + frDt + "' and '" + toDt + "' and status = 'A' and v.acno = a.acno and a.acctType = '" + acctCode + "'").getResultList();
                        }
                    } else {
                        if (brnCode.equalsIgnoreCase("0A")) {
//                            result = em.createNativeQuery("select count(acno) from " + table1 + " where acno in(select acno from " + table2 + " where substring(acno,3,2) = '" + acctCode + "'"
//                                    + "and trandesc not in(" + tranDescUnd + ") group by acno having max(dt) between '" + frDt + "' and '" + toDt + "' and sum(cramt-dramt) <> 0) and (closingdate is null or closingdate='' or closingdate > '" + asOnDt + "')and accstatus<>15").getResultList();
                            result = em.createNativeQuery("select count(acno) from " + table1 + " where acno in(SELECT a.ACNo FROM " + table1 + " a, "
                                    + table2 + " r WHERE a.ACNo = r.ACNo and a.accstatus not in(15,9) AND a.Accttype ='" + acctCode + "' "
                                    + "AND r.TranDesc NOT IN (" + tranDescUnd + ") and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + asOnDt + "') group by a.ACNo,a.custname having max(r.dt) between '" + frDt + "' "
                                    + "and '" + toDt + "')").getResultList();
                        } else {
                            result = em.createNativeQuery("select count(acno) from " + table1 + " where acno in(SELECT a.ACNo FROM " + table1 + " a, recon r WHERE a.ACNo = r.ACNo and a.accstatus not in(15,9)  AND a.Accttype ='" + acctCode + "' AND a.CURBRCODE ='" + brnCode + "' AND r.TranDesc NOT IN (" + tranDescUnd + ")  and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + asOnDt + "') group by a.ACNo,a.custname  having max(r.dt) between '" + frDt + "' and '" + toDt + "' )").getResultList();
                        }
                    }

                    Vector vtr = (Vector) result.get(0);
                    String noOfAcno = vtr.get(0).toString();
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            result1 = em.createNativeQuery("select cast((ifnull(sum(cramt),0)-ifnull(sum(dramt),0)) as decimal(25,2)) from td_recon where acno in(select a.acno from td_vouchmst v,td_accountmaster a  where  matDt between '" + frDt + "' and '" + toDt + "' and status = 'A' and v.acno = a.acno and a.acctType = '" + acctCode + "')").getResultList();
                        } else {
                            result1 = em.createNativeQuery("select cast((ifnull(sum(cramt),0)-ifnull(sum(dramt),0)) as decimal(25,2)) from td_recon where acno in(select a.acno from td_vouchmst v,td_accountmaster a  where  a.curbrcode = '" + brnCode + "' and matDt between '" + frDt + "' and '" + toDt + "' and status = 'A' and v.acno = a.acno and a.acctType = '" + acctCode + "')").getResultList();
                        }
                    } else {
                        if (brnCode.equalsIgnoreCase("0A")) {
                            //result1 = em.createNativeQuery("select cast((ifnull(sum(cramt),0)-ifnull(sum(dramt),0)) as decimal(25,2)) from " + table2 + " where acno in(select acno from " + table1 + " where acno in(select acno from " + table2 + " where  substring(acno,3,2) = '" + acctCode + "' and trandesc not in(" + tranDescUnd + ") group by acno having max(dt) between '" + frDt + "' and '" + toDt + "' and sum(cramt-dramt)<> 0) and (closingdate is null or closingdate='' or closingdate > '" + asOnDt + "')) and dt <= '" + asOnDt + "'").getResultList();
                            result1 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from " + table2 + " where acno in(SELECT a.ACNo FROM " + table1 + " a, " + table2 + " r WHERE a.ACNo = r.ACNo and a.accstatus not in(15,9) AND a.Accttype ='" + acctCode + "' AND r.TranDesc NOT IN (" + tranDescUnd + ") group by a.ACNo,a.custname having max(r.dt) between '" + frDt + "' and '" + toDt + "') and dt <= '" + asOnDt + "'").getResultList();
                        } else {
                            result1 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from " + table2 + " where acno in(SELECT a.ACNo FROM " + table1 + " a, " + table2 + " r WHERE a.ACNo = r.ACNo and a.accstatus not in(15,9)  AND a.Accttype ='" + acctCode + "' AND a.CURBRCODE ='" + brnCode + "' AND r.TranDesc NOT IN (" + tranDescUnd + ")  and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + asOnDt + "') group by a.ACNo,a.custname  having max(r.dt) between '" + frDt + "' and '" + toDt + "') and dt <= '" + asOnDt + "'").getResultList();
                        }
                    }

                    Vector vtr1 = (Vector) result1.get(0);
                    double bal = Double.parseDouble(vtr1.get(0).toString());
                    String acDesc = common.getAcctDecription(acctCode);

                    UnclaimedAccountStatementPojo pojo = new UnclaimedAccountStatementPojo();
                    pojo.setNoOfAcno(Integer.parseInt(noOfAcno));
                    pojo.setBalance(bal);
                    pojo.setAcType(acctCode);
                    pojo.setActDesc(acDesc);
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<UnclaimedAccountStatementPojo> getUnclaimedAccountStatement(String brnCode, String acType, String asOnDt, String frYear, String toYear, String acNature) throws ApplicationException {
        List<UnclaimedAccountStatementPojo> dataList = new ArrayList<UnclaimedAccountStatementPojo>();
        List result = new ArrayList();
        try {
            String fyr = "-" + frYear;
            String tyr = "-" + toYear;
            List frDtList = em.createNativeQuery("select DATE_FORMAT(date_add('" + asOnDt + "', "
                    + "INTERVAL " + tyr + " YEAR),'%Y%m%d')").getResultList();
            Vector dtvtr = (Vector) frDtList.get(0);
            String frDt = dtvtr.get(0).toString();

            List toDtList = em.createNativeQuery("select DATE_FORMAT(date_add('" + asOnDt + "', "
                    + "INTERVAL " + fyr + " YEAR),'%Y%m%d')").getResultList();
            Vector dtvtr1 = (Vector) toDtList.get(0);
            String toDt = dtvtr1.get(0).toString();
            String acNat = "";
            if (!acType.equalsIgnoreCase("ALL")) {
                acNat = ftsPosting.getAcNatureByCode(acType);
            }
            String table2 = "";
            String table1 = "";
            if (acType.equalsIgnoreCase("ALL")) {
                table2 = common.getTableName(acNature);
                acNat = acNature;
            } else {
                table2 = common.getTableName(acNat);
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                table1 = "td_accountmaster";
            } else {
                table1 = "accountmaster";
            }

            String tranDescUnd = "";
            List tranDescSimpleList = em.createNativeQuery("select code from cbs_parameterinfo where "
                    + "name in ('UND')").getResultList();
            if (tranDescSimpleList.isEmpty()) {
                throw new ApplicationException("Please Check data  is exists for UND in CBS_PARAMETERINFO Table");
            } else {
                Vector tranDescSimpleVect = (Vector) tranDescSimpleList.get(0);
                tranDescUnd = tranDescSimpleVect.get(0).toString();
            }

            if (acNature.equalsIgnoreCase("FD") || acNature.equalsIgnoreCase("MS")) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    if (acType.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select a.acno,a.custname,voucherno from td_vouchmst v,td_accountmaster "
                                + "a  where  matDt between '" + frDt + "' and '" + toDt + "' and status = 'A' and "
                                + "v.acno = a.acno and a.acctType in(select acctcode from accounttypemaster "
                                + "a1 where a1.crdbflag='C' and a1.acctnature='" + acNature + "') and "
                                + "a.accstatus<>15").getResultList();
                    } else {
                        result = em.createNativeQuery("select a.acno,a.custname,voucherno from td_vouchmst v,td_accountmaster "
                                + "a  where  matDt between '" + frDt + "' and '" + toDt + "' and status = 'A' and "
                                + "v.acno = a.acno and a.acctType = '" + acType + "' and a.accstatus<>15").getResultList();
                    }
                } else {
                    if (acType.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("select a.acno,a.custname,ReceiptNo from td_vouchmst v,td_accountmaster "
//                                + "a  where a.curbrcode = '" + brnCode + "' and  matDt between '" + frDt + "' "
//                                + "and '" + toDt + "' and status = 'A' and v.acno = a.acno and a.acctType in(select "
//                                + "acctcode from accounttypemaster where acctnature = '" + acNature + "') and a.accstatus<>15").getResultList();

                        result = em.createNativeQuery("select a.acno,a.custname,voucherno from td_vouchmst v,td_accountmaster a "
                                + "where a.curbrcode = '" + brnCode + "' and  matDt between '" + frDt + "' and '" + toDt + "' "
                                + "and status = 'A' and v.acno = a.acno and a.acctType in(select acctcode from accounttypemaster "
                                + "a1 where a1.crdbflag='C' and a1.acctnature='" + acNature + "') and "
                                + "a.accstatus<>15").getResultList();
                    } else {
                        result = em.createNativeQuery("select a.acno,a.custname,voucherno from td_vouchmst v,td_accountmaster "
                                + "a  where a.curbrcode = '" + brnCode + "' and  matDt between '" + frDt + "' "
                                + "and '" + toDt + "' and status = 'A' and v.acno = a.acno and "
                                + "a.acctType = '" + acType + "' and a.accstatus<>15").getResultList();
                    }
                }
            } else {
                if (brnCode.equalsIgnoreCase("0A")) {
                    if (acType.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("SELECT a.ACNo,a.custname FROM " + table1 + " a, " + table2 + " r WHERE "
                                + "a.ACNo = r.ACNo and a.accstatus not in(15,9) AND SUBSTRING(r.Acno,3,2) IN (select acctcode from "
                                + "accounttypemaster a1 where a1.crdbflag in('C','B') and a1.acctnature='" + acNature + "') AND "
                                + "r.TranDesc NOT IN (" + tranDescUnd + ") and (a.closingdate is null or a.closingdate='' or "
                                + "a.closingdate > '" + asOnDt + "') group by a.ACNo,a.custname having max(r.dt) "
                                + "between '" + frDt + "' and '" + toDt + "' /*and cast(sum(r.cramt-r.dramt)as decimal(25,2))> 0*/").getResultList();
                    } else {
                        result = em.createNativeQuery("SELECT a.ACNo,a.custname FROM " + table1 + " a, " + table2 + " r WHERE "
                                + "a.ACNo = r.ACNo and a.accstatus not in(15,9) AND a.Accttype ='" + acType + "' AND r.TranDesc "
                                + "NOT IN (" + tranDescUnd + ") and (a.closingdate is null or a.closingdate='' or "
                                + "a.closingdate > '" + asOnDt + "') group by a.ACNo,a.custname having max(r.dt) "
                                + "between '" + frDt + "' and '" + toDt + "' /*and cast(sum(r.cramt-r.dramt)as decimal(25,2))> 0*/").getResultList();
                    }
                } else {
                    if (acType.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("SELECT a.ACNo,a.custname FROM " + table1 + " a, " + table2 + " r WHERE a.ACNo = r.ACNo and a.accstatus<>15 "
//                                + " AND SUBSTRING(r.Acno,3,2) IN (SELECT acctcode FROM accounttypemaster a1 WHERE a1.acctNature ='" + acNature + "') AND a.CURBRCODE ='" + brnCode + "' AND r.TranDesc NOT IN (" + tranDescUnd + ") "
//                                + " and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + asOnDt + "') group by r.acno "
//                                + " having max(r.dt) between '" + frDt + "' and '" + toDt + "'").getResultList();

                        result = em.createNativeQuery("SELECT a.ACNo,a.custname FROM " + table1 + " a, " + table2 + " r WHERE "
                                + "a.ACNo = r.ACNo and a.accstatus not in(15,9) AND SUBSTRING(r.Acno,3,2) IN (select acctcode from "
                                + "accounttypemaster a1 where a1.crdbflag in('C','B') and a1.acctnature='" + acNature + "') AND "
                                + "a.CURBRCODE ='" + brnCode + "' AND r.TranDesc NOT IN (" + tranDescUnd + ") "
                                + "and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + asOnDt + "') group "
                                + "by a.ACNo,a.custname having max(r.dt) between '" + frDt + "' and '" + toDt + "' /*and cast(sum(r.cramt-r.dramt)as decimal(25,2))> 0*/ ").getResultList();
                    } else {
                        result = em.createNativeQuery("SELECT a.ACNo,a.custname FROM " + table1 + " a, " + table2 + " r WHERE a.ACNo = r.ACNo and a.accstatus not in(15,9) "
                                + " AND a.Accttype ='" + acType + "' AND a.CURBRCODE ='" + brnCode + "' AND r.TranDesc NOT IN (" + tranDescUnd + ") "
                                + " and (a.closingdate is null or a.closingdate='' or a.closingdate > '" + asOnDt + "') group by a.ACNo,a.custname "
                                + " having max(r.dt) between '" + frDt + "' and '" + toDt + "' /*and cast(sum(r.cramt-r.dramt)as decimal(25,2))> 0*/").getResultList();
                    }
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector element = (Vector) result.get(i);
                    UnclaimedAccountStatementPojo pojo = new UnclaimedAccountStatementPojo();
                    String acNo = element.get(0).toString();
                    String custName = element.get(1).toString();
                    String receiptNo = "0";
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        receiptNo = element.get(2).toString();
                    }
                    List result1 = new ArrayList();
                    String lastTrnDt = "";
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        result1 = em.createNativeQuery("select MatDt from td_vouchmst where acno = '" + acNo + "' and "
                                + "Status = 'A' and voucherno = " + Double.parseDouble(receiptNo) + "").getResultList();
                    } else {
                        //result1 = em.createNativeQuery("select ifnull(max(dt),'') from " + table2 + " where acno = '" + acNo + "' and trandesc not in(" + tranDescUnd + ")").getResultList();
                        result1 = em.createNativeQuery("select ifnull(Last_Txn_Date,'') from accountmaster where acno = '" + acNo + "'").getResultList();
                    }
                    if (!result1.isEmpty()) {
                        Vector dtvtr2 = (Vector) result1.get(0);
                        lastTrnDt = dtvtr2.get(0).toString();
                        if (!lastTrnDt.equalsIgnoreCase("")) {
                            lastTrnDt = lastTrnDt.substring(8, 10) + "/" + lastTrnDt.substring(5, 7) + "/" + lastTrnDt.substring(0, 4);
                        }
                    }

                    List result2 = null;
                    if (acType.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode()) || acType.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                        result2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from " + table2 + " where acno ='" + acNo + "' and dt < '" + asOnDt + "' group by acno having sum(cramt-dramt) > 0").getResultList();
                    } else {
                        result2 = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from " + table2 + " where acno = '" + acNo + "'and dt <= '" + asOnDt + "'").getResultList();
                    }
                    double Amt = 0d;
                    if (!result2.isEmpty()) {
                        Vector blvtr = (Vector) result2.get(0);
                        Amt = Double.parseDouble(blvtr.get(0).toString());
                    }

                    // if (Amt != 0.00) {
                    pojo.setAcNo(acNo);
                    pojo.setCustName(custName);
                    pojo.setLastTrnDt(lastTrnDt);
                    pojo.setAmount(Amt);
                    pojo.setReceiptNo(receiptNo);
                    pojo.setfYear(frDt.substring(6, 8) + "/" + frDt.substring(4, 6) + "/" + frDt.substring(0, 4));
                    pojo.setTyear(asOnDt.substring(6, 8) + "/" + asOnDt.substring(4, 6) + "/" + asOnDt.substring(0, 4));
                    dataList.add(pojo);
                    //}
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<BillSundryDetailsPojo> getBillSundryDetail(String brnCode, String tillDt) throws ApplicationException {
        List<BillSundryDetailsPojo> dataList = new ArrayList<BillSundryDetailsPojo>();
        List result = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("SELECT fyear, seqno, acno, amount, origindt FROM bill_sundry where status = 'issued' and origindt < '" + tillDt + "' order by fyear,seqno").getResultList();
            } else {
                result = em.createNativeQuery("SELECT fyear, seqno, acno, amount, origindt FROM bill_sundry where status = 'issued' and substring(acno,1,2) = '" + brnCode + "' and origindt < '" + tillDt + "' order by fyear,seqno").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    BillSundryDetailsPojo pojo = new BillSundryDetailsPojo();
                    pojo.setfYear(ele.get(0).toString());
                    pojo.setSqNo(ele.get(1).toString());
                    pojo.setAcNo(ele.get(2).toString());
                    pojo.setAmount(Double.parseDouble(ele.get(3).toString()));
                    pojo.setOrginDt(ele.get(4).toString().substring(8, 10) + "/" + ele.get(4).toString().substring(5, 7) + "/" + ele.get(4).toString().substring(0, 4));
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<SlabDetailPojo> getSlabDetal(String acctNature, String frDt, String toDt) throws ApplicationException {
        List<SlabDetailPojo> dataList = new ArrayList<SlabDetailPojo>();
        double intRateSc = 0d, intRateSt = 0d, intRateOt = 0d, intRateMg = 0d;
        try {
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List result = em.createNativeQuery("select Applicable_Date,interest_rate,fromdays,todays,fromamount,cast(toamount as decimal(25,2)),sc,st,ot,mg  from "
                        + "td_slab where Applicable_Date between '" + frDt + "' and '" + toDt + "' and acctNature = '" + acctNature + "' order by Applicable_Date").getResultList();
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        SlabDetailPojo pojo = new SlabDetailPojo();
                        double intRate = Double.parseDouble(vtr.get(1).toString());
                        double sc = Double.parseDouble(vtr.get(6).toString());
                        double st = Double.parseDouble(vtr.get(7).toString());
                        double ot = Double.parseDouble(vtr.get(8).toString());
                        double mg = Double.parseDouble(vtr.get(9).toString());
                        intRateSc = intRate + sc;
                        intRateSt = intRate + st;
                        intRateOt = intRate + ot;
                        intRateMg = intRate + ot;
                        pojo.setAppDt(vtr.get(0).toString().substring(8, 10) + "/" + vtr.get(0).toString().substring(5, 7) + "/" + vtr.get(0).toString().substring(0, 4));
                        pojo.setFormDays(vtr.get(2).toString());
                        pojo.setToDays(vtr.get(3).toString());
                        pojo.setFromAmt(Double.parseDouble(vtr.get(4).toString()));
                        pojo.setToAmt(Double.parseDouble(vtr.get(5).toString()));
                        pojo.setIntRateSc(intRateSc);
                        pojo.setIntRateSt(intRateSt);
                        pojo.setIntRateOt(intRateOt);
                        pojo.setIntRateMg(intRateMg);
                        dataList.add(pojo);
                    }
                }
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                List result = em.createNativeQuery("SELECT APPLICABLE_DATE,FROM_MON,TO_MON,FROM_AMT,cast(to_amt as decimal(25,2)),ROI,COMM_PERC,SURCHARGE FROM dds_slab").getResultList();
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        SlabDetailPojo pojo = new SlabDetailPojo();
                        pojo.setAppDt(vtr.get(0).toString().substring(8, 10) + "/" + vtr.get(0).toString().substring(5, 7) + "/" + vtr.get(0).toString().substring(0, 4));
                        pojo.setFormDays(vtr.get(1).toString());
                        pojo.setToDays(vtr.get(2).toString());
                        pojo.setFromAmt(Double.parseDouble(vtr.get(3).toString()));
                        pojo.setToAmt(Double.parseDouble(vtr.get(4).toString()));
                        pojo.setRoi(Double.parseDouble(vtr.get(5).toString()));
                        pojo.setCommPerc(Double.parseDouble(vtr.get(6).toString()));
                        pojo.setSurCharge(Double.parseDouble(vtr.get(7).toString()));
                        dataList.add(pojo);
                    }
                }
            } else {
                List result = em.createNativeQuery("select cl.interest_code,cl.begin_slab_amount,cl.end_slab_amount,cl.normal_interest_percentage,cd.start_date,cd.end_date "
                        + "from cbs_loan_interest_slab_master cl, cbs_loan_interest_code_master cd where cl.interest_code = cd.interest_code  "
                        + "and cl.interest_code in(SELECT distinct(SCHEME_CODE) FROM cbs_loan_acc_mast_sec  where substring(acno,3,2) in(select acctcode from accounttypemaster "
                        + "where acctnature = '" + acctNature + "'))and  cd.start_date < '" + toDt + "' union select cl.interest_code,cl.begin_slab_amount,cl.end_slab_amount,cl.normal_interest_percentage,cd.start_date,cd.end_date "
                        + "from cbs_loan_interest_slab_master_history cl,cbs_loan_interest_code_master_history cd where cl.interest_code = cd.interest_code and cl.interest_code in"
                        + "(SELECT distinct(SCHEME_CODE) FROM cbs_loan_acc_mast_sec  where substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature = '" + acctNature + "'))"
                        + "and cd.start_date < '" + toDt + "' order by 1").getResultList();
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        SlabDetailPojo pojo = new SlabDetailPojo();
                        String intCode = vtr.get(0).toString();
                        List list = em.createNativeQuery("select scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_code = '" + intCode + "'").getResultList();
                        Vector ele = (Vector) list.get(0);
                        pojo.setIntCode(intCode);
                        pojo.setFromAmt(Double.parseDouble(vtr.get(1).toString()));
                        pojo.setToAmt(Double.parseDouble(vtr.get(2).toString()));
                        pojo.setRoi(Double.parseDouble(vtr.get(3).toString()));
                        pojo.setFormDays(vtr.get(4).toString().substring(8, 10) + "/" + vtr.get(4).toString().substring(5, 7) + "/" + vtr.get(4).toString().substring(0, 4));
                        pojo.setToDays(vtr.get(5).toString().substring(8, 10) + "/" + vtr.get(5).toString().substring(5, 7) + "/" + vtr.get(5).toString().substring(0, 4));
                        pojo.setIntCodeDesc(ele.get(0).toString());
                        dataList.add(pojo);
                    }
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<SmsPostingReportPojo> getSmsPostingDetail(String brnCode, String acType, String frDt, String toDt) throws ApplicationException {
        List<SmsPostingReportPojo> dataList = new ArrayList<SmsPostingReportPojo>();
        List list = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    //list = em.createNativeQuery("select acno,dr_amount,from_date,to_date,DATE_FORMAT(tran_Dt,'%d/%m/%Y') from  mb_charge_posting_indivisual_history WHERE DATE_FORMAT(tran_Dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' order by acno ").getResultList();
                    list = em.createNativeQuery("select a.acno,a.custname,a.accttype,c.AcctDesc,b.drrAmt,b.frDt,b.toDt,b.txnDt from accountmaster a,"
                            + "(select acno as acNo,dr_amount as drrAmt,from_date as frDt,to_date as toDt,DATE_FORMAT(tran_Dt,'%d/%m/%Y') as txnDt "
                            + "from  mb_charge_posting_indivisual_history WHERE DATE_FORMAT(tran_Dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "')b,"
                            + "accounttypemaster c where a.acno = b.acNo and a.accttype = c.AcctCode order by a.acno,b.txnDt").getResultList();
                } else {
                    //list = em.createNativeQuery("select acno,dr_amount,from_date,to_date,DATE_FORMAT(tran_Dt,'%d/%m/%Y') from  mb_charge_posting_indivisual_history WHERE SUBSTRING(acno,3,2) = '" + acType + "' and DATE_FORMAT(tran_Dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' order by acno ").getResultList();
                    list = em.createNativeQuery("select a.acno,a.custname,a.accttype,c.AcctDesc,b.drrAmt,b.frDt,b.toDt,b.txnDt from accountmaster a,"
                            + "(select acno as acNo,dr_amount as drrAmt,from_date as frDt,to_date as toDt,DATE_FORMAT(tran_Dt,'%d/%m/%Y') as txnDt "
                            + "from  mb_charge_posting_indivisual_history WHERE SUBSTRING(acno,3,2) = '" + acType + "' and DATE_FORMAT(tran_Dt,'%Y%m%d') "
                            + "between '" + frDt + "' and '" + toDt + "')b,accounttypemaster c where a.acno = b.acNo and a.accttype = c.AcctCode "
                            + "order by a.acno,b.txnDt").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    // list = em.createNativeQuery("select acno,dr_amount,from_date,to_date,DATE_FORMAT(tran_Dt,'%d/%m/%Y') from  mb_charge_posting_indivisual_history WHERE SUBSTRING(acno,1,2) = '" + brnCode + "'  and DATE_FORMAT(tran_Dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' order by acno ").getResultList();
                    list = em.createNativeQuery("select a.acno,a.custname,a.accttype,c.AcctDesc,b.drrAmt,b.frDt,b.toDt,b.txnDt from accountmaster a,"
                            + "(select acno as acNo,dr_amount as drrAmt,from_date as frDt,to_date as toDt,DATE_FORMAT(tran_Dt,'%d/%m/%Y') as txnDt "
                            + "from  mb_charge_posting_indivisual_history WHERE SUBSTRING(acno,1,2) = '" + brnCode + "' and DATE_FORMAT(tran_Dt,'%Y%m%d') "
                            + "between '" + frDt + "' and '" + toDt + "')b,accounttypemaster c where a.acno = b.acNo and a.accttype = c.AcctCode "
                            + "order by a.acno,b.txnDt").getResultList();
                } else {
                    // list = em.createNativeQuery("select acno,dr_amount,from_date,to_date,DATE_FORMAT(tran_Dt,'%d/%m/%Y') from  mb_charge_posting_indivisual_history WHERE SUBSTRING(acno,1,2) = '" + brnCode + "' and SUBSTRING(acno,3,2) = '" + acType + "' and DATE_FORMAT(tran_Dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' order by acno ").getResultList();
                    list = em.createNativeQuery("select a.acno,a.custname,a.accttype,c.AcctDesc,b.drrAmt,b.frDt,b.toDt,b.txnDt from accountmaster a,"
                            + "(select acno as acNo,dr_amount as drrAmt,from_date as frDt,to_date as toDt,DATE_FORMAT(tran_Dt,'%d/%m/%Y') as txnDt from  "
                            + "mb_charge_posting_indivisual_history WHERE SUBSTRING(acno,1,2) = '" + brnCode + "' and SUBSTRING(acno,3,2) = '" + acType
                            + "' and DATE_FORMAT(tran_Dt,'%Y%m%d') between '" + frDt + "' and '" + toDt + "')b,accounttypemaster c where a.acno = b.acNo "
                            + "and a.accttype = c.AcctCode order by a.acno,b.txnDt").getResultList();
                }
            }

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    SmsPostingReportPojo pojo = new SmsPostingReportPojo();
//                    String acDesc = common.getAcctDecription(vtr.get(0).toString().substring(2, 4));
//                    String acNat = ftsPosting.getAcNatureByCode(vtr.get(0).toString().substring(2, 4));
//                    String table1 = "";
//                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                        table1 = "td_accountmaster";
//                    } else {
//                        table1 = "accountmaster";
//                    }
//
//                    List custNameList = em.createNativeQuery("select custname from " + table1 + " where acno = '" + vtr.get(0).toString() + "'").getResultList();
//                    Vector cvr = (Vector) custNameList.get(0);

                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setAcType(vtr.get(2).toString());
                    pojo.setAcDesc(vtr.get(3).toString());
                    pojo.setDrAmt(Double.parseDouble(vtr.get(4).toString()));
                    pojo.setFrom_date(vtr.get(5).toString());
                    pojo.setTo_date(vtr.get(6).toString());
                    pojo.setTxnDate(vtr.get(7).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List getFdrdtltdAcctType() throws ApplicationException {
        try {
            List select = em.createNativeQuery("select distinct(acctNature) from accounttypemaster where acctNature not in ('" + CbsConstant.PAY_ORDER + "')").getResultList();
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<KycAccountDetailPojo> getKycData(String brnCode, String asOnDt, String toDt, String repType, String repOption, String nature, String acType) throws ApplicationException {
        List<KycAccountDetailPojo> dataList = new ArrayList<KycAccountDetailPojo>();
        List list = new ArrayList();

        try {

            String branch = "", groupBy = "", orderBy = "", natureCondition = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                branch = "";
            } else {
                branch = "and PrimaryBrCode = '" + brnCode + "'";
            }

            if (repOption.equalsIgnoreCase("Id")) {
                groupBy = "group by custid";
                orderBy = "order by 1";
            } else {
                groupBy = "group by custid,c.acno";
                orderBy = "order by 1,10";

                if (!nature.equalsIgnoreCase("All")) {
                    if (acType.equalsIgnoreCase("All")) {
                        natureCondition = "and accttype in(select AcctCode from accounttypemaster where acctnature='" + nature + "')";
                    } else {
                        natureCondition = "and accttype = '" + acType + "'";
                    }

                }
            }

            Map<String, String> riskMap = new HashMap<>();
            List riskCateList = em.createNativeQuery("select REF_CODE,REF_DESC from cbs_ref_rec_type where REF_REC_NO = '024'").getResultList();
            for (int i = 0; i < riskCateList.size(); i++) {
                Vector vtr = (Vector) riskCateList.get(i);
                riskMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }

            Map<String, String> addIdProofMap = new HashMap<>();
            List addIdProofList = em.createNativeQuery("select REF_CODE,REF_DESC from cbs_ref_rec_type where REF_REC_NO = '312'").getResultList();
            for (int i = 0; i < addIdProofList.size(); i++) {
                Vector vtr = (Vector) addIdProofList.get(i);
                addIdProofMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }

            Map<String, String> acstatusDescMap = new HashMap<>();
            List acstatusDescMapList = em.createNativeQuery("select Code,Description from codebook where  groupcode = 3 ").getResultList();
            for (int i = 0; i < acstatusDescMapList.size(); i++) {
                Vector vtr = (Vector) acstatusDescMapList.get(i);
                acstatusDescMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }

            if (repType.equalsIgnoreCase("Comp") || repType.equalsIgnoreCase("Exp")) {
//                list = em.createNativeQuery("select customerid,CustFullName,concat(PerAddressLine1,'',ifnull(PerAddressLine2,'')),dateofbirth ,"
//                        + "date_format(CustUpdationDate,'%Y%m%d'),b.REF_DESC,operationalRiskrating,ifnull(mobilenumber,''),ifnull(PAN_GIRNumber,'') "
//                        + "from cbs_customer_master_detail a,cbs_ref_rec_type b where  PrimaryBrCode = '" + brnCode + "'  "
//                        + "and SuspensionFlg not in('Y','S') and date_format(CustUpdationDate,'%Y%m%d') < '" + asOnDt + "' "
//                        + "and date_format(CustUpdationDate,'%d/%m/%Y') <> '01/01/1900' and b.REF_REC_NO = '312' "
//                        + "and a.poa = b.REF_CODE order by customerid").getResultList();
                if (repOption.equalsIgnoreCase("Id")) {
                    list = em.createNativeQuery("select a.custid,b.CustFullName,concat(b.PerAddressLine1,'',ifnull(b.PerAddressLine2,'')),b.dateofbirth,date_format(ifnull(b.CustUpdationDate,'19000101'),'%Y%m%d'), "
                            + "b.poa,b.operationalRiskrating,ifnull(b.mobilenumber,''),ifnull(b.PAN_GIRNumber,''),c.acno,c.accstatus from customerid a,cbs_customer_master_detail b, "
                            + "(select ACNO,accstatus from accountmaster where OpeningDt<'" + asOnDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + asOnDt + "') "
                            + "union all "
                            + "select ACNO,accstatus from td_accountmaster where OpeningDt<'" + asOnDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + asOnDt + "'))c "
                            + "where a.acno = c.acno and a.custid = cast(b.customerid as unsigned) " + branch + " " + groupBy + " " + orderBy + "").getResultList();
                } else {

                    if (nature.equalsIgnoreCase("All")) {
                        list = em.createNativeQuery("select a.custid,b.CustFullName,concat(b.PerAddressLine1,'',ifnull(b.PerAddressLine2,'')),b.dateofbirth,date_format(ifnull(b.CustUpdationDate,'19000101'),'%Y%m%d'), "
                                + "b.poa,b.operationalRiskrating,ifnull(b.mobilenumber,''),ifnull(b.PAN_GIRNumber,''),c.acno,c.accstatus from customerid a,cbs_customer_master_detail b, "
                                + "(select ACNO,accstatus from accountmaster where OpeningDt<'" + asOnDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + asOnDt + "') "
                                + "union all "
                                + "select ACNO,accstatus from td_accountmaster where OpeningDt<'" + asOnDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + asOnDt + "'))c "
                                + "where a.acno = c.acno and a.custid = cast(b.customerid as unsigned) " + branch + " " + groupBy + " " + orderBy + "").getResultList();
                    } else {
                        list = em.createNativeQuery("select a.custid,b.CustFullName,concat(b.PerAddressLine1,'',ifnull(b.PerAddressLine2,'')),b.dateofbirth,date_format(ifnull(b.CustUpdationDate,'19000101'),'%Y%m%d'), "
                                + "b.poa,b.operationalRiskrating,ifnull(b.mobilenumber,''),ifnull(b.PAN_GIRNumber,''),c.acno,c.accstatus from customerid a,cbs_customer_master_detail b, "
                                + "(select ACNO,accstatus from accountmaster where OpeningDt<'" + asOnDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + asOnDt + "') " + natureCondition + " "
                                + "union all "
                                + "select ACNO,accstatus from td_accountmaster where OpeningDt<'" + asOnDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + asOnDt + "') " + natureCondition + ")c "
                                + "where a.acno = c.acno and a.custid = cast(b.customerid as unsigned) " + branch + " " + groupBy + " " + orderBy + "").getResultList();
                    }
                }
            } else if (repType.equalsIgnoreCase("From")) {

//                list = em.createNativeQuery("select customerid,CustFullName,concat(PerAddressLine1,'',ifnull(PerAddressLine2,'')),dateofbirth ,"
//                        + "date_format(CustUpdationDate,'%Y%m%d'),b.REF_DESC,operationalRiskrating,ifnull(mobilenumber,''),ifnull(PAN_GIRNumber,'') "
//                        + "from cbs_customer_master_detail a,cbs_ref_rec_type b where  PrimaryBrCode = '" + brnCode + "'  "
//                        + "and SuspensionFlg not in('Y','S') and date_format(CustUpdationDate,'%Y%m%d') between '" + asOnDt + "' and '" + toDt + "' "
//                        + "and b.REF_REC_NO = '312' and a.poa = b.REF_CODE order by customerid").getResultList();
                if (repOption.equalsIgnoreCase("Id")) {
                    list = em.createNativeQuery("select a.custid,b.CustFullName,concat(b.PerAddressLine1,'',ifnull(b.PerAddressLine2,'')),b.dateofbirth,date_format(ifnull(b.CustUpdationDate,'19000101'),'%Y%m%d'), "
                            + "b.poa,b.operationalRiskrating,ifnull(b.mobilenumber,''),ifnull(b.PAN_GIRNumber,''),c.acno,c.accstatus from customerid a,cbs_customer_master_detail b, "
                            + "(select ACNO,accstatus from accountmaster where OpeningDt<'" + toDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + toDt + "') "
                            + "union all "
                            + "select ACNO,accstatus from td_accountmaster where OpeningDt<'" + toDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + toDt + "'))c "
                            + "where a.acno = c.acno and a.custid = cast(b.customerid as unsigned) "
                            + "and date_format(b.CustUpdationDate,'%Y%m%d') between '" + asOnDt + "' and '" + toDt + "' "
                            + "" + branch + " " + groupBy + " " + orderBy + " ").getResultList();
                } else {
                    if (nature.equalsIgnoreCase("All")) {
                        list = em.createNativeQuery("select a.custid,b.CustFullName,concat(b.PerAddressLine1,'',ifnull(b.PerAddressLine2,'')),b.dateofbirth,date_format(ifnull(b.CustUpdationDate,'19000101'),'%Y%m%d'), "
                                + "b.poa,b.operationalRiskrating,ifnull(b.mobilenumber,''),ifnull(b.PAN_GIRNumber,''),c.acno,c.accstatus from customerid a,cbs_customer_master_detail b, "
                                + "(select ACNO,accstatus from accountmaster where OpeningDt<'" + toDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + toDt + "') "
                                + "union all "
                                + "select ACNO,accstatus from td_accountmaster where OpeningDt<'" + toDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + toDt + "'))c "
                                + "where a.acno = c.acno and a.custid = cast(b.customerid as unsigned) "
                                + "and date_format(b.CustUpdationDate,'%Y%m%d') between '" + asOnDt + "' and '" + toDt + "' "
                                + "" + branch + " " + groupBy + " " + orderBy + " ").getResultList();
                    } else {
                        list = em.createNativeQuery("select a.custid,b.CustFullName,concat(b.PerAddressLine1,'',ifnull(b.PerAddressLine2,'')),b.dateofbirth,date_format(ifnull(b.CustUpdationDate,'19000101'),'%Y%m%d'), "
                                + "b.poa,b.operationalRiskrating,ifnull(b.mobilenumber,''),ifnull(b.PAN_GIRNumber,''),c.acno,c.accstatus from customerid a,cbs_customer_master_detail b, "
                                + "(select ACNO,accstatus from accountmaster where OpeningDt<'" + toDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + toDt + "') " + natureCondition + " "
                                + "union all "
                                + "select ACNO,accstatus from td_accountmaster where OpeningDt<'" + toDt + "' and (ifnull(closingdate,'')='' or closingdate>'" + toDt + "') " + natureCondition + ")c "
                                + "where a.acno = c.acno and a.custid = cast(b.customerid as unsigned) "
                                + "and date_format(b.CustUpdationDate,'%Y%m%d') between '" + asOnDt + "' and '" + toDt + "' "
                                + "" + branch + " " + groupBy + " " + orderBy + " ").getResultList();
                    }
                }
            } else if (repType.equalsIgnoreCase("Uncomp")) {
                list = em.createNativeQuery("select customerid,CustFullName,concat(PerAddressLine1,'',ifnull(PerAddressLine2,'')),dateofbirth,"
                        + "date_format(ifnull(CustUpdationDate,'19000101'),'%Y%m%d'),'' as poa,operationalRiskrating,ifnull(mobilenumber,''),"
                        + "ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail a "
                        + "where (CustUpdationDate is null or date_format(CustUpdationDate,'%Y%m%d') ='19000101') "
                        + "and  PrimaryBrCode = '" + brnCode + "' and SuspensionFlg not in('Y','S') "
                        + "and (CreationTime < '" + asOnDt + "' or CreationTime is null)").getResultList();
            }
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    KycAccountDetailPojo pojo = new KycAccountDetailPojo();
                    String custId = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();
                    String add = vtr.get(2).toString();
                    String dob = vtr.get(3).toString();
                    String idProof = "", addProof = "";
                    String riskCategory = "03";
                    addProof = vtr.get(5).toString();
                    riskCategory = vtr.get(6).toString();
                    String mobileNo = vtr.get(7).toString();
                    String panNo = vtr.get(8).toString();
                    if (riskCategory.equalsIgnoreCase("")) {
                        riskCategory = "03";
                    }
                    String dateOfBierh = dmyy.format(yymmdd.parse(dob));
                    String custUpdationDt = "";
                    if (!repType.equalsIgnoreCase("Uncomp")) {
                        custUpdationDt = vtr.get(4).toString();
                    }
                    String acno = vtr.get(9).toString();
                    String acStatus = vtr.get(10).toString();
                    String renewaldate = "";
                    if (repType.equalsIgnoreCase("Comp") || repType.equalsIgnoreCase("Exp")) {

                        if (riskCategory.equals("01")) { // High
                            // renewaldate = getRenewalDtForKyc(custUpdationDt, "2");
                            renewaldate = dmyy.format(ymdFormat.parse(CbsUtil.yearAdd(custUpdationDt, 2)));
                        } else if (riskCategory.equals("02")) {  // Medium
                            //renewaldate = getRenewalDtForKyc(custUpdationDt, "8");
                            renewaldate = dmyy.format(ymdFormat.parse(CbsUtil.yearAdd(custUpdationDt, 8)));
                        } else if ((riskCategory.equals("03") || riskCategory.equals("04"))) { // Low
                            // renewaldate = getRenewalDtForKyc(custUpdationDt, "10");
                            renewaldate = dmyy.format(ymdFormat.parse(CbsUtil.yearAdd(custUpdationDt, 10)));

                        }

                        if (repType.equalsIgnoreCase("Exp")) {
                            if (dmyy.parse(renewaldate).before(ymdFormat.parse(asOnDt))) {
                                if (dateOfBierh == null || dateOfBierh.equalsIgnoreCase("") || dateOfBierh.equalsIgnoreCase("01/01/1900")) {
                                    pojo.setDob("");
                                } else {
                                    pojo.setDob(dateOfBierh);
                                }
                                String upDt = dmyy.format(ymd.parse(custUpdationDt));
                                pojo.setAdd(add.trim());
                                pojo.setCustId(custId);
                                pojo.setCustName(custName);
                                pojo.setUpDationDt(upDt);
                                pojo.setIdProof(getIdProofForKyc(custId));
                                pojo.setAddressProof(addIdProofMap.get(addProof) == null ? "" : addIdProofMap.get(addProof));
                                long effNoOfDays = CbsUtil.dayDiff(ymd.parse(custUpdationDt), ymdFormat.parse(asOnDt));
                                long year = effNoOfDays / 365;

                                pojo.setRenewalDt(renewaldate);
                                pojo.setMobileNo(mobileNo);
                                pojo.setPanNo(panNo);
                                pojo.setRiskCategory(riskMap.get(riskCategory) == null ? "" : riskMap.get(riskCategory));
                                if (repOption.equalsIgnoreCase("Id")) {
                                    pojo.setAcNo("");
                                    pojo.setAccstatus("");
                                } else {
                                    pojo.setAcNo(acno);
                                    pojo.setAccstatus(acstatusDescMap.get(acStatus) == null ? "" : acstatusDescMap.get(acStatus));
                                }
                                dataList.add(pojo);
                            }
                        } else {
                            if (dmyy.parse(renewaldate).after(ymdFormat.parse(asOnDt))) {
                                if (dateOfBierh == null || dateOfBierh.equalsIgnoreCase("") || dateOfBierh.equalsIgnoreCase("01/01/1900")) {
                                    pojo.setDob("");
                                } else {
                                    pojo.setDob(dateOfBierh);
                                }
                                String upDt = dmyy.format(ymd.parse(custUpdationDt));
                                pojo.setAdd(add.trim());
                                pojo.setCustId(custId);
                                pojo.setCustName(custName);
                                pojo.setUpDationDt(upDt);
                                pojo.setIdProof(getIdProofForKyc(custId));
                                pojo.setAddressProof(addIdProofMap.get(addProof) == null ? "" : addIdProofMap.get(addProof));
                                long effNoOfDays = CbsUtil.dayDiff(ymd.parse(custUpdationDt), ymdFormat.parse(asOnDt));
                                long year = effNoOfDays / 365;

                                pojo.setRenewalDt(renewaldate);
                                pojo.setMobileNo(mobileNo);
                                pojo.setPanNo(panNo);
                                pojo.setRiskCategory(riskMap.get(riskCategory) == null ? "" : riskMap.get(riskCategory));
                                if (repOption.equalsIgnoreCase("Id")) {
                                    pojo.setAcNo("");
                                    pojo.setAccstatus("");
                                } else {
                                    pojo.setAcNo(acno);
                                    pojo.setAccstatus(acstatusDescMap.get(acStatus) == null ? "" : acstatusDescMap.get(acStatus));
                                }
                                dataList.add(pojo);
                            }
                        }
                    } else if (repType.equalsIgnoreCase("From")) {
                        long effNoOfDays = CbsUtil.dayDiff(ymd.parse(custUpdationDt), ymdFormat.parse(toDt));
                        long year = effNoOfDays / 365;
                        if (dateOfBierh == null || dateOfBierh.equalsIgnoreCase("") || dateOfBierh.equalsIgnoreCase("01/01/1900")) {
                            pojo.setDob("");
                        } else {
                            pojo.setDob(dateOfBierh);
                        }
                        String upDt = dmyy.format(ymd.parse(custUpdationDt));
                        pojo.setAdd(add.trim());
                        pojo.setCustId(custId);
                        pojo.setCustName(custName);
                        pojo.setUpDationDt(upDt);
                        pojo.setIdProof(getIdProofForKyc(custId));
                        pojo.setAddressProof(addIdProofMap.get(addProof) == null ? "" : addIdProofMap.get(addProof));
                        if (riskCategory.equals("01")) {
                            renewaldate = getRenewalDtForKyc(custUpdationDt, "2");
                        } else if (riskCategory.equals("02")) {
                            renewaldate = getRenewalDtForKyc(custUpdationDt, "8");
                        } else if ((riskCategory.equals("03") || riskCategory.equals("04"))) {
                            renewaldate = getRenewalDtForKyc(custUpdationDt, "10");
                        }
                        pojo.setRenewalDt(renewaldate);
                        pojo.setMobileNo(mobileNo);
                        pojo.setPanNo(panNo);
                        pojo.setRiskCategory(riskMap.get(riskCategory) == null ? "" : riskMap.get(riskCategory));
                        if (repOption.equalsIgnoreCase("Id")) {
                            pojo.setAcNo("");
                            pojo.setAccstatus("");
                        } else {
                            pojo.setAcNo(acno);
                            pojo.setAccstatus(acstatusDescMap.get(acStatus) == null ? "" : acstatusDescMap.get(acStatus));
                        }
                        dataList.add(pojo);
                    } else {

                        custUpdationDt = vtr.get(4).toString();
                        String upDt = dmyy.format(ymd.parse(custUpdationDt));
                        if (dateOfBierh == null || dateOfBierh.equalsIgnoreCase("") || dateOfBierh.equalsIgnoreCase("01/01/1900")) {
                            pojo.setDob("");
                        } else {
                            pojo.setDob(dateOfBierh);
                        }
                        pojo.setAdd(add.trim());
                        pojo.setCustId(custId);
                        pojo.setCustName(custName);
                        if (vtr.get(4) == null || vtr.get(4).toString().equalsIgnoreCase("19000101")) {
                            pojo.setUpDationDt("01/01/1900");
                        }
                        pojo.setUpDationDt(upDt);
                        pojo.setRenewalDt(renewaldate);
                        pojo.setIdProof(getIdProofForKyc(custId));
                        pojo.setAddressProof(addProof);
                        pojo.setMobileNo(mobileNo);
                        pojo.setPanNo(panNo);
                        pojo.setRiskCategory(riskMap.get(riskCategory) == null ? "" : riskMap.get(riskCategory));
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String getRenewalDtForKyc(String custUpdationDt, String yy) throws ApplicationException {
        String renewaldate = "";
        try {
            List dtList = em.createNativeQuery("select DATE_FORMAT(date_add(" + custUpdationDt + ", INTERVAL " + yy + " YEAR),'%d/%m/%Y')").getResultList();
            Vector ele = (Vector) dtList.get(0);
            renewaldate = ele.get(0).toString();
            return renewaldate;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getIdProofForKyc(String custId) throws ApplicationException {
        String idProof = "";
        try {
            List docNameList = em.createNativeQuery("select distinct REF_DESC from cbs_ref_rec_type where REF_REC_NO in('311','313') and REF_CODE = (select ifnull(legal_document,'') from cbs_customer_master_detail where customerid = '" + custId + "')").getResultList();
            if (!docNameList.isEmpty()) {
                Vector docnameVector = (Vector) docNameList.get(0);
                idProof = docnameVector.get(0).toString();
            }
            return idProof;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<AtmStatusPojo> getAtmStatusData(String brnCode, String reportType, String frDt, String toDt) throws ApplicationException {
        List<AtmStatusPojo> dataList = new ArrayList<AtmStatusPojo>();
        List result = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                if (reportType.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,verify,am.del_flag from atm_card_master am, accountmaster ac where ac.acno = am.acno "
                            + "and am.issue_dt between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                } else {
                    result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,verify,am.del_flag from atm_card_master am, accountmaster ac where ac.acno = am.acno "
                            + "and del_flag = '" + reportType + "' and am.issue_dt between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                }
            } else {
                if (reportType.equalsIgnoreCase("ALL")) {
                    result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,verify,am.del_flag from atm_card_master am, accountmaster ac where curbrcode = '" + brnCode + "' "
                            + "and ac.acno = am.acno and am.issue_dt between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                } else {
                    result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,verify,am.del_flag from atm_card_master am, accountmaster ac where curbrcode = '" + brnCode + "' "
                            + "and ac.acno = am.acno and del_flag = '" + reportType + "' and am.issue_dt between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    AtmStatusPojo pojo = new AtmStatusPojo();
                    String acNo = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();
                    String cardNo = vtr.get(2).toString();
                    String issueDt = vtr.get(3).toString();
                    String verStat = vtr.get(4).toString();
                    String stat = vtr.get(5).toString();
                    String mob = "";
                    String pAdd = "";
                    String mAdd = "";
                    List list = em.createNativeQuery("select mobilenumber,ifnull(perAddressline1,''),ifnull(perAddressline2,''),"
                            + "ifnull(mailAddressline1,''),ifnull(mailAddressline2,'') from cbs_customer_master_detail where "
                            + "customerid in(select CustId from customerid where acno = '" + acNo + "')").getResultList();
                    if (!list.isEmpty()) {
                        Vector ele = (Vector) list.get(0);
                        mob = ele.get(0).toString();
                        pAdd = ele.get(1).toString() + " " + ele.get(2).toString();
                        mAdd = ele.get(3).toString() + " " + ele.get(4).toString();
                    }

                    String brName;
                    if (brnCode.equalsIgnoreCase("0A")) {
                        brName = common.getBranchNameByBrncode("90");
                    } else {
                        brName = common.getBranchNameByBrncode(brnCode);
                    }
                    pojo.setAcNo(acNo);
                    pojo.setCustName(custName);
                    pojo.setAtmCardNo(cardNo);
                    pojo.setIssueDt(issueDt.substring(8, 10) + "/" + issueDt.substring(5, 7) + "/" + issueDt.substring(0, 4));
                    pojo.setMobileNo(mob);
                    pojo.setBranchNAme(brName);
                    pojo.setActive(stat);
//                    pojo.setAdd1("JSR URBAN COOP BANK LTD");
//                    pojo.setAdd2("SAKCHI  KASIDIH");
                    pojo.setAdd1(pAdd);
                    pojo.setAdd2(mAdd);
                    //pojo.setAdd3("NEAR MANSAROVAR HOTEL");
                    //pojo.setDist("JAMSHEDPUR");
                    //pojo.setState("JHARKHAND");
                    //pojo.setType("002");
                    //pojo.setVerify(verStat);
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<AtmStatusPojo> getAtmDetailData(String brnCode, String fileType, String status, String frDt, String toDt) throws ApplicationException {
        List<AtmStatusPojo> dataList = new ArrayList<AtmStatusPojo>();
        List result = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                if (fileType.equalsIgnoreCase("L")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag In('A','M','D','U') "
                                + "and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno  "
                                + "and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' order by am.del_flag <>'G'").getResultList();
                    }
                } else {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag In('A','M','D','U') "
                                + "and file_type='" + fileType + "' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and file_type='" + fileType + "' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno "
                                + "and file_type='" + fileType + "' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' order by am.del_flag <>'G'").getResultList();
                    }
                }
            } else {
                if (fileType.equalsIgnoreCase("N")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag In('A','M','D') "
                                + "and file_type='N' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();

                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag='G'  "
                                + "and file_type='N' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' and "
                                + "substring(am.acno,1,2)='" + brnCode + "'").getResultList();

                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno "
                                + "and file_type='N' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' and "
                                + "substring(am.acno,1,2)='" + brnCode + "' order by am.del_flag <>'G'").getResultList();
                    }
                } else if (fileType.equalsIgnoreCase("A")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='U' "
                                + "and file_type='A' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and file_type='A' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno  "
                                + "and file_type='A' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "' order by am.del_flag <>'G'").getResultList();
                    }
                } else if (fileType.equalsIgnoreCase("C")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='U' "
                                + "and file_type='C' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and file_type='C' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno  "
                                + "and file_type='C' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "' order by am.del_flag <>'G'").getResultList();
                    }
                } else if (fileType.equalsIgnoreCase("D")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='U' "
                                + "and file_type='D' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and file_type='D' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno  "
                                + "and file_type='D' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "' order by am.del_flag <>'G'").getResultList();
                    }
                } else if (fileType.equalsIgnoreCase("S")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='U' "
                                + "and file_type='S' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and file_type='S' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno  "
                                + "and file_type='S' and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "' order by am.del_flag <>'G'").getResultList();
                    }
                } else if (fileType.equalsIgnoreCase("L")) {
                    if (status.equalsIgnoreCase("E")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag In('A','M','D','U') "
                                + "and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("G")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno and am.del_flag ='G' "
                                + "and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "'").getResultList();
                    } else if (status.equalsIgnoreCase("ALL")) {
                        result = em.createNativeQuery("select ac.acno,ac.custname,am.card_no,am.issue_dt,am.withdrawal_limit_amount,"
                                + "am.withdrawal_limit_count,am.purchase_limit_amount,am.purchase_limit_count,am.min_limit,am.file_type,am.del_flag,kit_no "
                                + "from atm_card_master am, accountmaster ac where ac.acno = am.acno  "
                                + "and cast(am.lastUpdateDate as date)between '" + frDt + "' and '" + toDt + "' and verify ='Y' "
                                + "and substring(am.acno,1,2)='" + brnCode + "' order by am.del_flag <>'G'").getResultList();
                    }
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    AtmStatusPojo pojo = new AtmStatusPojo();
                    String acNo = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();
                    String cardNo = vtr.get(2).toString();
                    String issueDt = dmyy.format(yymmdd.parse(vtr.get(3).toString()));
                    String wdrlLimitAmount = vtr.get(4).toString();
                    String wdrlLimitCount = vtr.get(5).toString();
                    String purLimitAmount = vtr.get(6).toString();
                    String purLimitCount = vtr.get(7).toString();
                    String minLimit = vtr.get(8).toString();
                    fileType = vtr.get(9).toString();
                    status = vtr.get(10).toString();
                    String kitNo = vtr.get(11).toString();
                    String status1 = "";
                    if (status.equalsIgnoreCase("A") || status.equalsIgnoreCase("M")
                            || status.equalsIgnoreCase("D") || status.equalsIgnoreCase("U")) {
                        status1 = "ENTERED";
                    } else {
                        status1 = "GENERATED";
                    }

                    pojo.setAcNo(acNo);
                    pojo.setCustName(custName);
                    pojo.setAtmCardNo(cardNo);
                    pojo.setIssueDt(issueDt);
                    pojo.setWithdrawalLimitAmount(new BigDecimal(wdrlLimitAmount));
                    pojo.setWithdrawalLimitCount(Integer.parseInt(wdrlLimitCount));
                    pojo.setPurchaseLimitAmount(new BigDecimal(purLimitAmount));
                    pojo.setPurchaseLimitCount(Integer.parseInt(purLimitCount));
                    pojo.setMinLmt(minLimit);
                    pojo.setFileType(fileType);
                    pojo.setStatus(status1);
                    pojo.setKitNo(kitNo);

                    dataList.add(pojo);

                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<UserDepuTrfDetailPojo> getUserDetailData(String frBranch, String toBranch, String frDt, String toDt) throws ApplicationException {
        List<UserDepuTrfDetailPojo> dataList = new ArrayList<UserDepuTrfDetailPojo>();
        try {
            List result = em.createNativeQuery("select userid,username,address,brncode as Currentbranch,orgbrncode,fromdate,todate,deputeorxfer from securityinfohistory where brncode = '" + frBranch + "' "
                    + "and orgbrncode = '" + toBranch + "'and fromdate >= '" + frDt + "' and todate < '" + toDt + "' union all "
                    + "select userid,username,address,brncode as Currentbranch,orgbrncode,fromdate,todate,deputeorxfer from securityinfo where brncode = '" + toBranch + "' "
                    + "and orgbrncode = '" + frBranch + "'and fromdate >= '" + frDt + "' and todate <= '" + toDt + "' and deputeorxfer != 'Transfer' union all "
                    + " select userid,username,address,brncode as Currentbranch,orgbrncode,fromdate,todate,deputeorxfer from securityinfo where brncode = '" + toBranch + "' "
                    + " and orgbrncode = '" + frBranch + "' and fromdate >= '" + frDt + "' and deputeorxfer = 'Transfer' order by userid,username,fromdate,todate").getResultList();

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    UserDepuTrfDetailPojo pojo = new UserDepuTrfDetailPojo();
                    pojo.setUserId(vtr.get(0).toString());
                    pojo.setUserName(vtr.get(1).toString());
                    pojo.setAddress(vtr.get(2).toString());
                    pojo.setCurBranch(common.getBranchNameByBrncode(vtr.get(3).toString()));
                    pojo.setOriginBranch(common.getBranchNameByBrncode(vtr.get(4).toString()));
                    pojo.setFrDate(vtr.get(5).toString().substring(8, 10) + "/" + vtr.get(5).toString().substring(5, 7) + "/" + vtr.get(5).toString().substring(0, 4));
                    if (vtr.get(7).toString().equalsIgnoreCase("Transfer")) {
                        pojo.setToDate("");
                    } else {
                        pojo.setToDate(vtr.get(6).toString().substring(8, 10) + "/" + vtr.get(6).toString().substring(5, 7) + "/" + vtr.get(6).toString().substring(0, 4));
                    }
                    pojo.setDeputeXfr(vtr.get(7).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String markUnClaimed(String reportBranchCode, String acNature, List<UnclaimedAccountStatementPojo> unClaimedList,
            String today, String userName, String flag, String acType, String savingRoi) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = ftsPosting.markUnClaimed(reportBranchCode, acNature, unClaimedList,
                    today, userName, flag, acType, savingRoi);
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public List<AccountEditHistoryPojo> getSignatureReport(String brnCode, String acNat, String acType, String Opt) throws ApplicationException {
        List<AccountEditHistoryPojo> scanList = new ArrayList<AccountEditHistoryPojo>();
        try {
            List resultList = new ArrayList();
            if (Opt.equalsIgnoreCase("S")) {
                if ((acNat.equalsIgnoreCase("FD")) || (acNat.equalsIgnoreCase("MS"))) {
                    // resultList = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%Y%m%d') from td_accountmaster where acno in (select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "') and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by acno").getResultList();
                    resultList = em.createNativeQuery("select a.acno,b.custfullname,date_format(OpeningDt,'%Y%m%d'),ifnull(b.fathername,''),ifnull(b.mailaddressline1,''), ifnull(b.mailaddressline2,''), ifnull(b.mobilenumber,'')   \n"
                            + "from td_accountmaster a,cbs_customer_master_detail b,customerid c where a.acno in \n"
                            + "(select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "') \n"
                            + "and cast(customerid as unsigned) = CustId and a.acno = c.acno \n"
                            + "and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by a.acno").getResultList();
                } else {
                    //resultList = em.createNativeQuery("select acno,custname,OpeningDt from accountmaster where acno in (select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "') and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by acno").getResultList();
                    resultList = em.createNativeQuery("select a.acno,b.custfullname,OpeningDt,ifnull(b.fathername,''),ifnull(b.mailaddressline1,''), ifnull(b.mailaddressline2,''), ifnull(b.mobilenumber,'')   \n"
                            + "from accountmaster a,cbs_customer_master_detail b,customerid c where a.acno in \n"
                            + "(select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "') \n"
                            + "and cast(customerid as unsigned) = CustId and a.acno = c.acno \n"
                            + "and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by a.acno").getResultList();
                }
            } else if (Opt.equalsIgnoreCase("P")) {
                if ((acNat.equalsIgnoreCase("FD")) || (acNat.equalsIgnoreCase("MS"))) {
                    //resultList = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%Y%m%d') from td_accountmaster where acno not in (select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "') and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by acno").getResultList();
                    resultList = em.createNativeQuery("select a.acno,b.custfullname,date_format(OpeningDt,'%Y%m%d'),ifnull(b.fathername,''),ifnull(b.mailaddressline1,''), ifnull(b.mailaddressline2,''), ifnull(b.mobilenumber,'')  \n"
                            + "from td_accountmaster a,cbs_customer_master_detail b,customerid c \n"
                            + "where a.acno not in \n"
                            + "(select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "')\n"
                            + "and cast(customerid as unsigned) = CustId and a.acno = c.acno\n"
                            + "and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by a.acno").getResultList();
                } else {
                    //resultList = em.createNativeQuery("select acno,custname,OpeningDt from accountmaster where acno not in (select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "') and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by acno").getResultList();
                    resultList = em.createNativeQuery("select a.acno,b.custfullname,OpeningDt,ifnull(b.fathername,''),ifnull(b.mailaddressline1,''), ifnull(b.mailaddressline2,''), ifnull(b.mobilenumber,'')  \n"
                            + "from accountmaster a,cbs_customer_master_detail b,customerid c \n"
                            + "where a.acno not in \n"
                            + "(select newacno from cbs_cust_image_detail where substring(newacno,3,2) = '" + acType + "' and substring(newacno,1,2) = '" + brnCode + "')\n"
                            + "and cast(customerid as unsigned) = CustId and a.acno = c.acno\n"
                            + "and AccStatus <>9 and Accttype='" + acType + "' and CurBrCode = '" + brnCode + "' order by a.acno").getResultList();
                }
            }

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    AccountEditHistoryPojo pojo = new AccountEditHistoryPojo();
                    String acNo = vec.get(0).toString();
                    String cuName = vec.get(1).toString();
                    String opDt = vec.get(2).toString();

                    String fName = vec.get(3).toString();
                    String mAdd = vec.get(4).toString() + vec.get(5).toString();
                    String mNo = vec.get(6).toString();

                    pojo.setAcno(acNo);
                    pojo.setCustName(cuName);
                    pojo.setOpeningDt(opDt.substring(6) + "/" + opDt.substring(4, 6) + "/" + opDt.substring(0, 4));
                    pojo.setFatherName(fName);
                    pojo.setMailAdd(mAdd);
                    pojo.setPhoneNo(mNo);
                    scanList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return scanList;
    }

    public List<InterestFdReportPojo> getTdsFormDetails(String brCode, String acTp, String repTp, String fYear) throws ApplicationException {
        List<InterestFdReportPojo> dataList = new ArrayList<InterestFdReportPojo>();
        List result1 = new ArrayList();
        List fyearList = new ArrayList();
        try {
            String minDate = "";
            String maxDate = "";

            if (brCode.equalsIgnoreCase("0A")) {
                fyearList = em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='90'").getResultList();
            } else {
                fyearList = em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='" + brCode + "'").getResultList();
            }
            Vector fVector = (Vector) fyearList.get(0);
            String currentFyear = fVector.get(0).toString();

            if (repTp.equalsIgnoreCase("0")) {
                if (currentFyear.equalsIgnoreCase(fYear)) {
                    if (brCode.equalsIgnoreCase("0A")) {
                        List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='N' and brncode='90'").getResultList();
                        Vector yearEnd = (Vector) yearEndList.get(0);
                        minDate = yearEnd.get(0).toString();
                        maxDate = yearEnd.get(1).toString();
                    } else {
                        List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='N' and brncode='" + brCode + "'").getResultList();
                        Vector yearEnd = (Vector) yearEndList.get(0);
                        minDate = yearEnd.get(0).toString();
                        maxDate = yearEnd.get(1).toString();
                    }
                } else {
                    if (brCode.equalsIgnoreCase("0A")) {
                        List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='Y' and brncode='90' and F_YEAR = '" + fYear + "' ").getResultList();
                        Vector yearEnd = (Vector) yearEndList.get(0);
                        minDate = yearEnd.get(0).toString();
                        maxDate = yearEnd.get(1).toString();
                    } else {
                        List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='Y' and brncode='" + brCode + "' and F_YEAR = '" + fYear + "'").getResultList();
                        Vector yearEnd = (Vector) yearEndList.get(0);
                        minDate = yearEnd.get(0).toString();
                        maxDate = yearEnd.get(1).toString();
                    }
                }
            } else {
                if (brCode.equalsIgnoreCase("0A")) {
                    List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='N' and brncode='90'").getResultList();
                    Vector yearEnd = (Vector) yearEndList.get(0);
                    minDate = yearEnd.get(0).toString();
                    maxDate = yearEnd.get(1).toString();
                } else {
                    List yearEndList = em.createNativeQuery("select mindate,maxdate from yearend where yearendflag='N' and brncode='" + brCode + "'").getResultList();
                    Vector yearEnd = (Vector) yearEndList.get(0);
                    minDate = yearEnd.get(0).toString();
                    maxDate = yearEnd.get(1).toString();
                }
            }

            String table = "", acStatus = "";
            if (repTp.equalsIgnoreCase("0")) {
                if (currentFyear.equalsIgnoreCase(fYear)) {
                    table = "tds_docdetail";
                    acStatus = "and a.accstatus <>9";
                } else {
                    table = "tds_docdetail_his";
                    acStatus = "";
                }
            } else {
                table = "tds_docdetail";
                acStatus = "";
            }

            if (brCode.equalsIgnoreCase("0A")) {
                if (acTp.equalsIgnoreCase("A")) {
                    if (repTp.equalsIgnoreCase("0")) {

//                        result1 = em.createNativeQuery("select cast(c.custid as unsigned),b.acno,a.custname,date_format(b.submission_date,'%d/%m/%Y'),d.pan_girnumber,date_format(d.dateofbirth,'%d/%m/%Y'),d.peraddressline1,ifnull(d.perphonenumber,''),ifnull(d.mobilenumber,''),b.doc_details "
//                                + "from td_accountmaster a,tds_docdetail b,customerid c,cbs_customer_master_detail d where b.submission_date between '" + minDate + "' and '" + maxDate + "' "
//                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and b.customerid = d.customerid and a.accstatus <>9  "
//                                + "group by a.acno order by cast(c.custid as unsigned),b.acno").getResultList();
                        result1 = em.createNativeQuery("select e.Custid,acNo,Name,submiDt,panNo,dob,addr,phone,mob,doc from("
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,"
                                + "d.pan_girnumber as panNo,date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from td_accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and b.customerid = d.customerid " + acStatus + " group by a.acno "
                                + "union "
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,d.pan_girnumber panNo,"
                                + "date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and b.customerid = d.customerid " + acStatus + "  group by a.acno"
                                + ")e order by e.doc,e.Custid,e.acNo").getResultList();

                    } else if (repTp.equalsIgnoreCase("1")) {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a,customerid b,"
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.accstatus <>9 order by acno,custid").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.accstatus <>9 order by acno,custid").getResultList();
                    }
                } else {
                    if (repTp.equalsIgnoreCase("0")) {
//                        result1 = em.createNativeQuery("select c.custid,a.acno,a.custname,date_format(b.formdate,'%d/%m/%Y'),'' from td_accountmaster a,td_form15h b,"
//                                + " customerid c where b.formdate between '" + minDate + "' and '" + maxDate + "' "
//                                + " and a.acno = b.acno and a.acno = c.acno and a.accstatus <>9 and a.Accttype = '" + acTp + "' order by acno,custid ").getResultList();
//                        result1 = em.createNativeQuery("select cast(c.custid as unsigned),b.acno,a.custname,date_format(b.submission_date,'%d/%m/%Y'),d.pan_girnumber,date_format(d.dateofbirth,'%d/%m/%Y'),d.peraddressline1,ifnull(d.perphonenumber,''),ifnull(d.mobilenumber,''),b.doc_details "
//                                + "from td_accountmaster a,tds_docdetail b,customerid c,cbs_customer_master_detail d where b.submission_date between '" + minDate + "' and '" + maxDate + "' "
//                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and b.customerid = d.customerid and a.accstatus <>9 and a.Accttype = '" + acTp + "' "
//                                + "group by a.acno order by cast(c.custid as unsigned),b.acno").getResultList();

                        result1 = em.createNativeQuery("select e.Custid,acNo,Name,submiDt,panNo,dob,addr,phone,mob,doc from("
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,"
                                + "d.pan_girnumber as panNo,date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from td_accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and b.customerid = d.customerid " + acStatus + " and a.Accttype = '" + acTp + "' group by a.acno "
                                + "union "
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,d.pan_girnumber panNo,"
                                + "date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and b.customerid = d.customerid " + acStatus + " and a.Accttype = '" + acTp + "' group by a.acno"
                                + ")e order by e.doc,e.Custid,e.acNo").getResultList();

                    } else if (repTp.equalsIgnoreCase("1")) {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.Accttype = '" + acTp + "' and c.accstatus <>9 order by acno,custid").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.Accttype = '" + acTp + "' and c.accstatus <>9 order by acno,custid").getResultList();
                    }
                }
            } else {
                if (acTp.equalsIgnoreCase("A")) {
                    if (repTp.equalsIgnoreCase("0")) {
//                        result1 = em.createNativeQuery("select c.custid,a.acno,a.custname,date_format(b.formdate,'%d/%m/%Y'),'' from td_accountmaster a,td_form15h b,"
//                                + " customerid c where b.formdate between '" + minDate + "' and '" + maxDate + "' "
//                                + " and a.acno = b.acno and a.acno = c.acno and a.CurBrCode = '" + brCode + "' and a.accstatus <>9 order by formdate,acno,custid ").getResultList();
//                        result1 = em.createNativeQuery("select cast(c.custid as unsigned),b.acno,a.custname,date_format(b.submission_date,'%d/%m/%Y'),d.pan_girnumber,date_format(d.dateofbirth,'%d/%m/%Y'),d.peraddressline1,ifnull(d.perphonenumber,''),ifnull(d.mobilenumber,''),b.doc_details "
//                                + "from td_accountmaster a,tds_docdetail b,customerid c,cbs_customer_master_detail d where b.submission_date between '" + minDate + "' and '" + maxDate + "' "
//                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno  and a.CurBrCode = '" + brCode + "' and b.customerid = d.customerid and a.accstatus <>9 "
//                                + "group by a.acno order by cast(c.custid as unsigned),b.acno").getResultList();

                        result1 = em.createNativeQuery("select e.Custid,acNo,Name,submiDt,panNo,dob,addr,phone,mob,doc from("
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,"
                                + "d.pan_girnumber as panNo,date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from td_accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and a.CurBrCode = '" + brCode + "' and b.customerid = d.customerid " + acStatus + " group by a.acno "
                                + "union "
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,d.pan_girnumber panNo,"
                                + "date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and a.CurBrCode = '" + brCode + "' and b.customerid = d.customerid " + acStatus + " group by a.acno"
                                + ")e order by e.doc,e.Custid,e.acNo").getResultList();

                    } else if (repTp.equalsIgnoreCase("1")) {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.CurBrCode = '" + brCode + "' and c.accstatus <>9 order by acno,custid").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.CurBrCode = '" + brCode + "' and c.accstatus <>9 order by acno,custid").getResultList();
                    }
                } else {
                    if (repTp.equalsIgnoreCase("0")) {
//                        result1 = em.createNativeQuery("select c.custid,a.acno,a.custname,date_format(b.formdate,'%d/%m/%Y'),'' from td_accountmaster a,td_form15h b,"
//                                + " customerid c where b.formdate between '" + minDate + "' and '" + maxDate + "' "
//                                + " and a.acno = b.acno and a.acno = c.acno and a.CurBrCode = '" + brCode + "' and a.Accttype = '" + acTp + "' and a.accstatus <>9 order by formdate,acno,custid ").getResultList();
//                        result1 = em.createNativeQuery("select cast(c.custid as unsigned),b.acno,a.custname,date_format(b.submission_date,'%d/%m/%Y'),d.pan_girnumber,date_format(d.dateofbirth,'%d/%m/%Y'),d.peraddressline1,ifnull(d.perphonenumber,''),ifnull(d.mobilenumber,''),b.doc_details "
//                                + "from td_accountmaster a,tds_docdetail b,customerid c,cbs_customer_master_detail d where b.submission_date between '" + minDate + "' and '" + maxDate + "' "
//                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno  and a.CurBrCode = '" + brCode + "' and b.customerid = d.customerid and a.accstatus <>9 and a.Accttype = '" + acTp + "' "
//                                + "group by a.acno order by cast(c.custid as unsigned),b.acno").getResultList();

                        result1 = em.createNativeQuery("select e.Custid,acNo,Name,submiDt,panNo,dob,addr,phone,mob,doc from("
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,"
                                + "d.pan_girnumber as panNo,date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from td_accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and a.CurBrCode = '" + brCode + "' and b.customerid = d.customerid " + acStatus + " and a.Accttype = '" + acTp + "' group by a.acno "
                                + "union "
                                + "select cast(c.custid as unsigned) as Custid,b.acno as acNo,a.custname as Name,date_format(b.submission_date,'%d/%m/%Y') as submiDt,d.pan_girnumber panNo,"
                                + "date_format(d.dateofbirth,'%d/%m/%Y') as dob,d.peraddressline1 as addr,ifnull(d.perphonenumber,'') as phone,ifnull(d.mobilenumber,'') as mob,b.doc_details as doc "
                                + "from accountmaster a," + table + " b,customerid c,cbs_customer_master_detail d where date_format(b.submission_date,'%Y%m%d') between '" + minDate + "' and '" + maxDate + "' "
                                + "and a.acno = b.acno and a.acno = c.acno and b.acno = c.acno and a.CurBrCode = '" + brCode + "' and b.customerid = d.customerid " + acStatus + " and a.Accttype = '" + acTp + "' group by a.acno"
                                + ")e order by e.doc,e.Custid,e.acNo").getResultList();

                    } else if (repTp.equalsIgnoreCase("1")) {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.CurBrCode = '" + brCode + "' and c.Accttype = '" + acTp + "' and c.accstatus <>9 order by acno,custid").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select b.custid,c.acno,c.custname,'',a.PAN_GIRNumber from cbs_customer_master_detail a, customerid b, "
                                + " td_accountmaster c where a.customerid = b.custid "
                                + " and b.acno = c.acno and c.CurBrCode = '" + brCode + "' and c.Accttype = '" + acTp + "' and c.accstatus <>9 order by acno,custid").getResultList();
                    }
                }
            }

            if (!result1.isEmpty()) {
                for (int i = 0; i < result1.size(); i++) {
                    Vector ele = (Vector) result1.get(i);
                    InterestFdReportPojo pojo = new InterestFdReportPojo();
                    String submissionDt = ele.get(3).toString();
                    String pan = ele.get(4) == null ? " " : ele.get(4).toString();
                    if (repTp.equalsIgnoreCase("0")) {
                        String acNo = ele.get(1).toString();
                        String acNature = ftsPosting.getAcNatureByCode(ele.get(1).toString().substring(2, 4));
                        List intAmtList = new ArrayList();
                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0),ifnull(date_format(max(dt),'%d/%m/%Y'),''),0 '','' from td_interesthistory where acno = '" + acNo + "' and dt between '" + minDate + "' and '" + maxDate + "' ").getResultList();
                        } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0),ifnull(date_format(max(dt),'%d/%m/%Y'),''),0 '','' from rd_interesthistory where acno = '" + acNo + "' and dt between '" + minDate + "' and '" + maxDate + "' ").getResultList();
                        } else if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0),ifnull(date_format(max(dt),'%d/%m/%Y'),''),0 '','' from dds_interesthistory where acno = '" + acNo + "' and dt between '" + minDate + "' and '" + maxDate + "' ").getResultList();
                        }
                        List tdsDocDetailList = em.createNativeQuery("select acno,voucherno,sum(tds) as tds from tds_reserve_history where dt "
                                + "between '" + minDate + "' and '" + maxDate + "' and acno ='" + acNo + "' group by acno,voucherno order by 1,2 ").getResultList();
                        BigDecimal intAmttdReceipt = new BigDecimal(0);
                        BigDecimal tdsAmttdReceipt = new BigDecimal(0);
                        if (!tdsDocDetailList.isEmpty()) {
                            for (int j = 0; j < tdsDocDetailList.size(); j++) {
                                Vector vtr = (Vector) tdsDocDetailList.get(j);
                                String acno = vtr.get(0).toString();
                                float voucher = Float.parseFloat(vtr.get(1).toString());
                                tdsAmttdReceipt = tdsAmttdReceipt.add(new BigDecimal(vtr.get(2).toString()));
                                List submitDtList = em.createNativeQuery("select ifnull(date_format(max(submission_date),'%Y%m%d'),'') from " + table + " where submission_date between '" + minDate + "' and '" + maxDate + "' and acno='" + acNo + "'").getResultList();
                                Vector dtVector = (Vector) submitDtList.get(0);
                                String submitDt = dtVector.get(0).toString();
                                String table1 = common.getIntTableName(acNature);
                                String tDt = "";
                                if (!submitDt.equalsIgnoreCase("")) {
                                    tDt = submitDt;
                                } else {
                                    tDt = maxDate;
                                }
                                List intDetailList = null;
                                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    intDetailList = em.createNativeQuery("select sum(interest) from td_interesthistory where acno = '" + acno + "' and voucherno = "
                                            + voucher + "  and dt between '" + minDate + "' and '" + tDt + "' ").getResultList();
                                } else {
                                    intDetailList = em.createNativeQuery("select sum(interest) from " + table1 + " where acno = '" + acno + "' "
                                            + "  and dt between '" + minDate + "' and '" + tDt + "' ").getResultList();
                                }
                                if (!intDetailList.isEmpty()) {
                                    Vector intVtr = (Vector) intDetailList.get(0);
                                    intAmttdReceipt = intAmttdReceipt.add(new BigDecimal(intVtr.get(0) != null ? intVtr.get(0).toString() : "0"));
                                }
                            }
                        }
                        //End
                        double vouchNo = 0d, intAmt = 0d, exempAmt = 0d;
                        String dt = "";
                        if (!intAmtList.isEmpty()) {
                            for (int j = 0; j < intAmtList.size(); j++) {
                                Vector intAmtVector = (Vector) intAmtList.get(j);
                                InterestFdReportPojo pojo1 = new InterestFdReportPojo();
                                intAmt = Double.parseDouble(intAmtVector.get(0).toString());
                                if (!tdsDocDetailList.isEmpty()) {
                                    dt = intAmtVector.get(1).toString();
                                } else {
                                    dt = intAmtVector.get(1).toString();
                                }
                                exempAmt = Double.parseDouble(intAmtVector.get(2).toString());
                                if (!tdsDocDetailList.isEmpty()) {
                                    pojo1.setIntAmt(intAmttdReceipt);
                                    pojo1.setExemptionAmt(new BigDecimal(intAmt).subtract(intAmttdReceipt));
                                } else {
                                    pojo1.setIntAmt(new BigDecimal(0));
                                    pojo1.setExemptionAmt(new BigDecimal(intAmt));
                                }
                                pojo1.setIntPayDate(dt);
                                pojo1.setAcNo(acNo);
                                pojo1.setCustId(ele.get(0).toString());
                                pojo1.setCustName(ele.get(2).toString());
                                pojo1.setDepDate(ele.get(3).toString());
                                pojo1.setPanNo(pan);
                                pojo1.setDob(ele.get(5).toString());
                                pojo1.setPerAddr(ele.get(6).toString());
                                pojo1.setMobile(ele.get(8).toString());
                                pojo1.setDocType(ele.get(9).toString());

                                dataList.add(pojo1);
                            }
                        } else {
                            InterestFdReportPojo pojo1 = new InterestFdReportPojo();
                            pojo1.setAcNo(acNo);
                            String exemptDt = "";
                            double exemptionAmt = 0d;
                            if (!intAmtList.isEmpty()) {
                                Vector exemptionVector = (Vector) intAmtList.get(0);
                                exemptionAmt = Double.parseDouble(exemptionVector.get(0).toString());
                                exemptDt = exemptionVector.get(1).toString();
                            }
                            pojo1.setCustId(ele.get(0).toString());
                            pojo1.setCustName(ele.get(2).toString());
                            pojo1.setDepDate(ele.get(3).toString());
                            pojo1.setIntPayDate(exemptDt);
                            pojo1.setPanNo(pan);
                            pojo1.setDob(ele.get(5).toString());
                            pojo1.setPerAddr(ele.get(6).toString());
                            pojo1.setMobile(ele.get(8).toString());
                            pojo1.setDocType(ele.get(9).toString());
                            pojo1.setExemptionAmt(new BigDecimal(exemptionAmt));
                            pojo1.setIntAmt(new BigDecimal(0));
                            dataList.add(pojo1);
                        }
                    } else if (repTp.equalsIgnoreCase("1")) {
                        if (pan.equals("") || pan.length() != 10) {
                            pojo.setCustId(ele.get(0).toString());
                            pojo.setAcNo(ele.get(1).toString());
                            pojo.setCustName(ele.get(2).toString());
                            pojo.setDepDate(ele.get(3).toString());
                            pojo.setPanNo(pan);
                            dataList.add(pojo);
                        } else {
                            if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))
                                    || !ParseFileUtil.isNumeric(pan.substring(5, 9))
                                    || !ParseFileUtil.isAlphabet(pan.substring(9))) {
                                pojo.setCustId(ele.get(0).toString());
                                pojo.setAcNo(ele.get(1).toString());
                                pojo.setCustName(ele.get(2).toString());
                                pojo.setDepDate(ele.get(3).toString());
                                pojo.setPanNo(pan);
                                dataList.add(pojo);
                            }
                        }
                    } else {
                        if (pan.equals("") || pan.length() != 10) {
                        } else {
                            if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))
                                    || !ParseFileUtil.isNumeric(pan.substring(5, 9))
                                    || !ParseFileUtil.isAlphabet(pan.substring(9))) {
                            } else {
                                pojo.setCustId(ele.get(0).toString());
                                pojo.setAcNo(ele.get(1).toString());
                                pojo.setCustName(ele.get(2).toString());
                                pojo.setDepDate(ele.get(3).toString());
                                pojo.setPanNo(pan);
                                dataList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<SmsAcnoRegistrationPojo> smsAcnoRegister(String BrnCode, String actType, String fromDt, String toDt, String RepType) throws ApplicationException {
        List<SmsAcnoRegistrationPojo> dataList = new ArrayList<SmsAcnoRegistrationPojo>();
        List result = new ArrayList();
        try {
            String table = "";
            if (!actType.equalsIgnoreCase("ALL")) {
                String acNat = ftsBean.getAcNatureByCode(actType);
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    table = "td_accountmaster";
                } else {
                    table = "accountmaster";
                }
                if (RepType.equalsIgnoreCase("R")) {
                    result = em.createNativeQuery("select acno,mobile_no,date_format(CREATED_DATE,'%d/%m/%Y') from mb_subscriber_tab where substring(acno,1,2)= '" + BrnCode + "' "
                            + "and substring(acno,3,2)= '" + actType + "' and Status = 1 and AUTH_STATUS = 'V' "
                            + "and date_format(CREATED_DATE,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "' order by CREATED_DATE").getResultList();
                } else {
                    result = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from " + table + " where curbrcode = '" + BrnCode + "' and accttype = '" + actType + "' "
                            + "and (ifnull(closingdate,'')='' or closingdate > '" + toDt + "' OR closingdate='')"
                            + "and acno not in(select acno from mb_subscriber_tab where substring(acno,1,2)= '" + BrnCode + "' "
                            + "and substring(acno,3,2)= '" + actType + "' and Status = 1 and AUTH_STATUS = 'V') order by OpeningDt ").getResultList();
                }
            } else {
                if (RepType.equalsIgnoreCase("R")) {
                    result = em.createNativeQuery("select acno,mobile_no,date_format(CREATED_DATE,'%d/%m/%Y') from mb_subscriber_tab where substring(acno,1,2)= '" + BrnCode + "' "
                            + "and Status = 1 and AUTH_STATUS = 'V' "
                            + "and date_format(CREATED_DATE,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "' order by CREATED_DATE").getResultList();
                } else {
                    result = em.createNativeQuery("select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from accountmaster where curbrcode = '" + BrnCode + "' "
                            + "and (ifnull(closingdate,'')='' or closingdate > '" + toDt + "' OR closingdate='') "
                            + "and acno not in(select acno from mb_subscriber_tab where substring(acno,1,2)= '" + BrnCode + "' "
                            + "and Status = 1 and AUTH_STATUS = 'V') "
                            + "union "
                            + "select acno,custname,date_format(OpeningDt,'%d/%m/%Y') from td_accountmaster where curbrcode = '" + BrnCode + "'  "
                            + "and (ifnull(closingdate,'')='' or closingdate > '" + toDt + "' OR closingdate='') "
                            + "and acno not in(select acno from mb_subscriber_tab where substring(acno,1,2)= '" + BrnCode + "' "
                            + "and Status = 1 and AUTH_STATUS = 'V') "
                            + "order by 3 ").getResultList();
                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    SmsAcnoRegistrationPojo pojo = new SmsAcnoRegistrationPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    if (RepType.equalsIgnoreCase("R")) {
                        pojo.setMobileNo(vtr.get(1).toString().substring(3));
                        pojo.setCustName(ftsBean.getCustName(vtr.get(0).toString()));
                    } else {
                        pojo.setCustName(vtr.get(1).toString());
                    }
                    pojo.setRegistDt(vtr.get(2).toString());
                    dataList.add(pojo);
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }

    public List<ThresoldTransactionInfoPojo> getThresoldData(String brCode, String frDt, String toDt, String optionDt) throws ApplicationException {
        List<ThresoldTransactionInfoPojo> datList = new ArrayList<ThresoldTransactionInfoPojo>();
        List result = new ArrayList();
        try {

            if (optionDt.equalsIgnoreCase("B")) {
                if (brCode.equalsIgnoreCase("0A")) {
//                    result = em.createNativeQuery("select cast(customerid as unsigned),concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y') "
//                            + "from cbs_customer_master_detail_his where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' order by cast(customerid as unsigned)").getResultList();
                    result = em.createNativeQuery("select cast(customerid as unsigned),concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y')\n"
                            + "from cbs_customer_master_detail cm,(select cId, max(ThresoldDate) thDate from(\n"
                            + "select customerid cId,max(ThresoldLimitUpdationDate) ThresoldDate from cbs_customer_master_detail_his where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid \n"
                            + "union\n"
                            + "select customerid cId,date_format(ThresoldLimitUpdationDate,'%Y%m%d') ThresoldDate from cbs_customer_master_detail where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid\n"
                            + ")a group by cId)mx where cm.ThresoldLimitUpdationDate = mx.thDate and cm.customerid = mx.cId\n"
                            + "union\n"
                            + "select cast(customerid as unsigned),concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y')\n"
                            + "from cbs_customer_master_detail_his cm,(select cId, max(ThresoldDate) thDate,max(tnId) TId from(\n"
                            + "select customerid cId,max(ThresoldLimitUpdationDate) ThresoldDate,max(TXNID) tnId from cbs_customer_master_detail_his where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid \n"
                            + "union\n"
                            + "select customerid cId,date_format(ThresoldLimitUpdationDate,'%Y%m%d') ThresoldDate,'0'tnId from cbs_customer_master_detail where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid\n"
                            + ")a group by cId)mx where cm.ThresoldLimitUpdationDate = mx.thDate and cm.customerid = mx.cId and TXNID = mx.TId\n"
                            + "order by 1").getResultList();
                } else {
//                    result = em.createNativeQuery("select cast(customerid as unsigned),concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y') "
//                            + "from cbs_customer_master_detail_his where PrimaryBrCode ='" + brCode + "' and ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' order by cast(customerid as unsigned)").getResultList();
                    result = em.createNativeQuery("select cast(customerid as unsigned),concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y')\n"
                            + "from cbs_customer_master_detail cm,(select cId, max(ThresoldDate) thDate from(\n"
                            + "select customerid cId,max(ThresoldLimitUpdationDate) ThresoldDate from cbs_customer_master_detail_his where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid \n"
                            + "union\n"
                            + "select customerid cId,date_format(ThresoldLimitUpdationDate,'%Y%m%d') ThresoldDate from cbs_customer_master_detail where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid\n"
                            + ")a group by cId)mx where PrimaryBrCode ='" + brCode + "' and cm.ThresoldLimitUpdationDate = mx.thDate and cm.customerid = mx.cId\n"
                            + "union\n"
                            + "select cast(customerid as unsigned),concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y')\n"
                            + "from cbs_customer_master_detail_his cm,(select cId, max(ThresoldDate) thDate,max(tnId) TId from(\n"
                            + "select customerid cId,max(ThresoldLimitUpdationDate) ThresoldDate,max(TXNID) tnId from cbs_customer_master_detail_his where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid \n"
                            + "union\n"
                            + "select customerid cId,date_format(ThresoldLimitUpdationDate,'%Y%m%d') ThresoldDate,'0'tnId from cbs_customer_master_detail where ThresoldLimitUpdationDate between '" + frDt + "' and '" + toDt + "' group by customerid\n"
                            + ")a group by cId)mx where PrimaryBrCode ='" + brCode + "' and cm.ThresoldLimitUpdationDate = mx.thDate and cm.customerid = mx.cId and TXNID = mx.TId\n"
                            + "order by 1").getResultList();
                }
            } else {
                if (brCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select cast(customerid as unsigned) custId,c.acno,concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y') thresDt,d.REF_DESC "
                            + "from cbs_customer_master_detail a,customerid b,accountmaster c,cbs_ref_rec_type d  where cast(a.customerid as unsigned)=b.custid  "
                            + "and d.ref_rec_no='024' and  a.operationalRiskrating = d.ref_code "
                            + "and b.acno = c.acno and (c.closingdate is null or c.closingdate = '' or c.closingdate > '" + toDt + "') and ThresoldLimitUpdationDate <='" + toDt + "' "
                            + "union "
                            + "select cast(customerid as unsigned) custId,c.acno,concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y') thresDt,d.REF_DESC "
                            + "from cbs_customer_master_detail a,customerid b,td_accountmaster c,cbs_ref_rec_type d  where cast(a.customerid as unsigned)=b.custid "
                            + "and d.ref_rec_no='024' and  a.operationalRiskrating = d.ref_code "
                            + "and b.acno = c.acno and (c.closingdate is null or c.closingdate = '' or c.closingdate > '" + toDt + "') "
                            + "and ThresoldLimitUpdationDate <='" + toDt + "' order by 1").getResultList();
                } else {
                    result = em.createNativeQuery("select cast(customerid as unsigned) custId,c.acno,concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y') thresDt,d.REF_DESC "
                            + "from cbs_customer_master_detail a,customerid b,accountmaster c,cbs_ref_rec_type d  where substring(c.acno,1,2) ='" + brCode + "' and cast(a.customerid as unsigned)=b.custid "
                            + "and d.ref_rec_no='024' and  a.operationalRiskrating = d.ref_code "
                            + "and b.acno = c.acno and (c.closingdate is null or c.closingdate = '' or c.closingdate > '" + toDt + "') and ThresoldLimitUpdationDate <='" + toDt + "' "
                            + "union "
                            + "select cast(customerid as unsigned) custId,c.acno,concat(title,' ',custfullname),concat(fathername,' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')),ThresoldTransactionLimit,date_format(ThresoldLimitUpdationDate,'%d/%m/%Y') thresDt,d.REF_DESC "
                            + "from cbs_customer_master_detail a,customerid b,td_accountmaster c,cbs_ref_rec_type d  where substring(c.acno,1,2) ='" + brCode + "' and cast(a.customerid as unsigned)=b.custid "
                            + "and d.ref_rec_no='024' and  a.operationalRiskrating = d.ref_code "
                            + "and b.acno = c.acno and (c.closingdate is null or c.closingdate = '' or c.closingdate > '" + toDt + "') "
                            + "and ThresoldLimitUpdationDate <='" + toDt + "' order by 1").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    ThresoldTransactionInfoPojo pojo = new ThresoldTransactionInfoPojo();
                    Vector vtr = (Vector) result.get(i);
                    if (optionDt.equalsIgnoreCase("B")) {
                        pojo.setCustId(vtr.get(0).toString());
                        pojo.setCustName(vtr.get(1).toString());
                        pojo.setFatherName(vtr.get(2).toString());
                        pojo.setThresoldLimit(Double.parseDouble(vtr.get(3).toString()));
                        pojo.setThresoldUpdateDt(vtr.get(4).toString());
                    } else {
                        pojo.setCustId(vtr.get(0).toString());
                        pojo.setAcNo(vtr.get(1).toString());
                        pojo.setCustName(vtr.get(2).toString());
                        pojo.setFatherName(vtr.get(3).toString());
                        pojo.setThresoldLimit(Double.parseDouble(vtr.get(4).toString()));
                        pojo.setThresoldUpdateDt(vtr.get(5).toString());
                        pojo.setRiskCategory(vtr.get(6).toString());
                    }
                    datList.add(pojo);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return datList;
    }

    public List<CustAccountInfoPojo> getCustAccountInfo(String brCode, String acNature, String acType, String frDt, String toDt) throws ApplicationException {
        List<CustAccountInfoPojo> datList = new ArrayList<CustAccountInfoPojo>();
        List result = new ArrayList();
        String table = "";
        try {
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                table = "td_accountmaster";
            } else {
                table = "accountmaster";
            }
            if (brCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("All")) {

                    result = em.createNativeQuery("select t.custid,t.tacno,t.tcust,t.tnet,t.tthres,t.tref,r.csum,r.ccr,r.cdr,t.actype,t.acdesc from "
                            + "(select b.customerid as custid, a.acno as tacno,a.custname as tcust,ifnull(b.Networth,0) as tnet,ifnull(b.ThresoldTransactionLimit,0) as "
                            + "tthres,d.ref_desc as tref,a.accttype as actype,atm.acctdesc as acdesc from " + table + " a,cbs_customer_master_detail "
                            + "b,customerid c,cbs_ref_rec_type d,accounttypemaster atm where a.accttype = atm.acctcode and atm.acctnature = '"
                            + acNature + "' and a.acno = c.acno and b.customerid = c.CustId and d.ref_rec_no='024'and b.operationalRiskrating = "
                            + "d.ref_code and OpeningDt <= '" + toDt + "' and a.AccStatus <> 9 order by a.acno) t,"
                            + "(select acno as ano,cast(ifnull(sum(cramt),0) as decimal(25,2)) as csum,cast(ifnull(max(cramt),0)as decimal(25,2)) "
                            + "as ccr,cast(ifnull(max(dramt),0) as decimal(25,2)) as cdr from " + common.getTableName(acNature) + " where dt between "
                            + "'" + frDt + "' and '" + toDt + "' group by acno) r where t.tacno = r.ano").getResultList();
                } else {

                    result = em.createNativeQuery("select t.custid,t.tacno,t.tcust,t.tnet,t.tthres,t.tref,r.csum,r.ccr,r.cdr,t.actype,t.acdesc from "
                            + "(select b.customerid as custid, a.acno as tacno,a.custname as tcust,ifnull(b.Networth,0) as tnet,ifnull(b.ThresoldTransactionLimit,0) as "
                            + "tthres,d.ref_desc as tref,a.accttype as actype,atm.acctdesc as acdesc from " + table + " a,cbs_customer_master_detail b,"
                            + "customerid c,cbs_ref_rec_type d ,accounttypemaster atm where a.accttype = atm.acctcode and a.accttype ='" + acType
                            + "' and a.acno = c.acno and b.customerid = c.CustId and d.ref_rec_no='024' and b.operationalRiskrating = d.ref_code and "
                            + "OpeningDt <= '" + toDt + "' and a.AccStatus <> 9 order by a.acno) t,"
                            + "(select acno as ano,cast(ifnull(sum(cramt),0) as decimal(25,2)) as csum,cast(ifnull(max(cramt),0)as decimal(25,2)) "
                            + "as ccr,cast(ifnull(max(dramt),0) as decimal(25,2)) as cdr from " + common.getTableName(acNature) + " where dt between "
                            + "'" + frDt + "' and '" + toDt + "' group by acno) r where t.tacno = r.ano").getResultList();
                }
            } else {
                if (acType.equalsIgnoreCase("All")) {

                    result = em.createNativeQuery("select t.custid,t.tacno,t.tcust,t.tnet,t.tthres,t.tref,r.csum,r.ccr,r.cdr,t.actype,t.acdesc from "
                            + "(select b.customerid as custid, a.acno as tacno,a.custname as tcust,ifnull(b.Networth,0) as tnet,ifnull(b.ThresoldTransactionLimit,0) as "
                            + "tthres,d.ref_desc as tref,a.accttype as actype,atm.acctdesc as acdesc from " + table + " a,cbs_customer_master_detail b,"
                            + "customerid c,cbs_ref_rec_type d,accounttypemaster atm where a.accttype = atm.acctcode and atm.acctnature = '" + acNature
                            + "' and a.acno = c.acno and b.customerid = c.CustId and a.curbrcode = '" + brCode + "' and d.ref_rec_no='024' and "
                            + "b.operationalRiskrating = d.ref_code and OpeningDt <= '" + toDt + "' and a.AccStatus <> 9 order by a.acno) t,"
                            + "(select acno as ano,cast(ifnull(sum(cramt),0) as decimal(25,2)) as csum,cast(ifnull(max(cramt),0)as decimal(25,2)) "
                            + "as ccr,cast(ifnull(max(dramt),0) as decimal(25,2)) as cdr from " + common.getTableName(acNature) + " where dt between "
                            + "'" + frDt + "' and '" + toDt + "' group by acno) r where t.tacno = r.ano").getResultList();
                } else {

                    result = em.createNativeQuery("select t.custid,t.tacno,t.tcust,t.tnet,t.tthres,t.tref,r.csum,r.ccr,r.cdr,t.actype,t.acdesc from "
                            + "(select b.customerid as custid, a.acno as tacno,a.custname as tcust,ifnull(b.Networth,0) as tnet,ifnull(b.ThresoldTransactionLimit,0) as "
                            + "tthres,d.ref_desc as tref,a.accttype as actype,atm.acctdesc as acdesc from " + table + " a,cbs_customer_master_detail b,"
                            + "customerid c,cbs_ref_rec_type d ,accounttypemaster atm where a.accttype = atm.acctcode and a.accttype ='" + acType + "' and "
                            + "a.acno = c.acno and b.customerid = c.CustId and a.curbrcode = '" + brCode + "' and d.ref_rec_no='024' and "
                            + "b.operationalRiskrating = d.ref_code and OpeningDt <= '" + toDt + "' and a.AccStatus <> 9 order by a.acno) t,"
                            + "(select acno as ano,cast(ifnull(sum(cramt),0) as decimal(25,2)) as csum,cast(ifnull(max(cramt),0)as decimal(25,2)) "
                            + "as ccr,cast(ifnull(max(dramt),0) as decimal(25,2)) as cdr from " + common.getTableName(acNature) + " where dt between "
                            + "'" + frDt + "' and '" + toDt + "' group by acno) r where t.tacno = r.ano").getResultList();
                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    CustAccountInfoPojo pojo = new CustAccountInfoPojo();
                    pojo.setCustId(vtr.get(0).toString());
                    pojo.setAcno(vtr.get(1).toString());
                    pojo.setCustname(vtr.get(2).toString());
                    pojo.setAnnualTurnover(new BigDecimal(vtr.get(3).toString()));
                    pojo.setThresholdLimit(new BigDecimal(vtr.get(4).toString()));
                    pojo.setRiskCategory(vtr.get(5).toString().replace("RISK CATEGORY", ""));
                    pojo.setTotalCrAmt(new BigDecimal(vtr.get(6).toString()));
                    pojo.setMaxCrAmt(new BigDecimal(vtr.get(7).toString()));
                    pojo.setMaxDrAmt(new BigDecimal(vtr.get(8).toString()));
                    pojo.setActCode(vtr.get(9).toString());
                    pojo.setActDesc(vtr.get(10).toString());
                    datList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return datList;
    }

    public List<FormNo15gPart1Pojo> getForm15gPart1Data(String brCode, String custId, String finYear, String reportType, String optionType, String estimatedTotalIncomed, String totalNoForm, String aggregateAmt, boolean taxChecking, String assYear) throws ApplicationException {
        List<FormNo15gPart1Pojo> dataList = new ArrayList<FormNo15gPart1Pojo>();
        try {

            List finList = em.createNativeQuery("select distinct mindate,maxdate,F_YEAR from yearend where yearendflag = 'N' ").getResultList();
            Vector finVector = (Vector) finList.get(0);
            String finFirstDt = finVector.get(0).toString();
            String finLastDt = finVector.get(1).toString();
            String currentYear = finVector.get(2).toString();

            String tdsHeaderTable = "tds_docdetail_header", tdsTable = "tds_docdetail";
            if (!currentYear.equalsIgnoreCase(finYear)) {
                tdsHeaderTable = "tds_docdetail_header_his";
                tdsTable = "tds_docdetail_his";
                List yearList1 = em.createNativeQuery("select distinct MINDATE,MAXDATE,F_YEAR from yearend where F_YEAR = '" + finYear + "'").getResultList(); // for temparay basic
                Vector pVector = (Vector) yearList1.get(0);
                finFirstDt = pVector.get(0).toString();
                finLastDt = pVector.get(1).toString();
                currentYear = pVector.get(2).toString();
            }

            //List declarationDtList = em.createNativeQuery("select  date_format(submission_date,'%Y%m%d'),assessmentYear,uniqueIdentificationNo from tds_docdetail_header where custid = '" + custId + "'").getResultList();
            List declarationDtList = em.createNativeQuery("select  b.maxDecDt,assessmentYear,uniqueIdentificationNo from " + tdsHeaderTable + " a,(select max(date_format(submission_date,'%Y%m%d'))as maxDecDt,customerid from " + tdsTable + " where customerid = '" + custId + "' and fyear = '" + currentYear + "')b where custid = '" + custId + "' and a.custid=b.customerid and date_format(a.submission_date,'%Y%m%d') between '" + finFirstDt + "' and '" + finLastDt + "'").getResultList();
            if (declarationDtList.isEmpty()) {
                throw new ApplicationException("Data does not exist in tds_docdetail_header table !");
            }

            Vector decDtVector = (Vector) declarationDtList.get(0);
            String declareDt = decDtVector.get(0).toString();
            String assessmentYear = decDtVector.get(1).toString();
            String uniNo = decDtVector.get(2).toString();
            if (optionType.equalsIgnoreCase("PART I")) {
//                List result = em.createNativeQuery("select distinct CustFullName,pan_girnumber,rf3.ref_desc Residential_Status,ifnull(perblock,''),ifnull(pervillage,''),peraddressline1,"
//                        + "rf1.ref_desc CityTwon,rf2.ref_desc State,ifnull(perpostalcode,''),ifnull(per_email,''),ifnull(perphonenumber,''),ifnull(mobilenumber,''),date_format(dateofbirth,'%d/%m/%Y'),PerDistrict "
//                        + "from cbs_customer_master_detail a,cbs_ref_rec_type rf1,cbs_ref_rec_type rf2,"
//                        + "cbs_ref_rec_type rf3,tds_docdetail b where a.customerid = '" + custId + "'and a.PrimaryBrCode = '" + brCode + "' and a.customerid = b.customerid and doc_details = '" + reportType + "' and a.percitycode = rf1.ref_code and rf1.ref_rec_no = '001' "
//                        + "and a.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'"
//                        + "and a.nriflag = rf3.ref_code and rf3.ref_rec_no = '303'").getResultList();

                List result = em.createNativeQuery("select distinct CustFullName,pan_girnumber,rf3.ref_desc Residential_Status,ifnull(perblock,''),ifnull(pervillage,''),"
                        + "peraddressline1,PerDistrict,rf2.ref_desc State,ifnull(perpostalcode,''),ifnull(per_email,''),ifnull(perphonenumber,''),ifnull(mobilenumber,''),"
                        + "date_format(dateofbirth,'%d/%m/%Y'),ifnull(peraddressline2,'') from cbs_customer_master_detail a,cbs_ref_rec_type rf2,cbs_ref_rec_type rf3," + tdsTable + " b "
                        + "where a.customerid = '" + custId + "'and a.customerid = b.customerid and doc_details = '" + reportType + "' and b.fyear = '" + currentYear + "'"
                        + "and a.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'and a.nriflag = rf3.ref_code and rf3.ref_rec_no = '303'").getResultList();

                if (result.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                Vector vtr = (Vector) result.get(0);

                List headerList = em.createNativeQuery("SELECT formNo15G_15H,aggregateAmt,otherIncome,deductionAmt,estimatedIncome,orgBrnid FROM " + tdsHeaderTable + " where custId = '" + custId + "' and date_format(submission_date,'%Y%m%d') between '" + finFirstDt + "' and '" + finLastDt + "'").getResultList();
                Vector headerVector = (Vector) headerList.get(0);
                String formNo = headerVector.get(0).toString();
                double aggregateAmount = Double.parseDouble(headerVector.get(1).toString());
                double otherIncome = Double.parseDouble(headerVector.get(2).toString());
                double deductionAmt = Double.parseDouble(headerVector.get(3).toString());
                double estimateIncome1 = Double.parseDouble(headerVector.get(4).toString());

                List intList = new ArrayList();
//                intList = em.createNativeQuery("select concat(acno,' / ',cast(VoucherNo as unsigned )) IdentificationNo,'Income from Interest'NatureOfincome,"
//                        + "'194A'SectionUnder,sum(Interest) from td_interesthistory where acno in(select distinct acno from " + tdsTable + " "
//                        + "where customerid = '" + custId + "' and doc_details = '" + reportType + "' and fyear = '" + currentYear + "')and dt between '" + finFirstDt + "' and '" + finLastDt + "' "
//                        + "group by acno,VoucherNo "
//                        + "union "
//                        + "select concat(acno,'')IdentificationNo,'Income from Interest'NatureOfincome,'194A'SectionUnder,ifnull(sum(Interest),0) "
//                        + "from rd_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custId + "'"
//                        + "and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') and dt between '" + finFirstDt + "' and '" + finLastDt + "' "
//                        + "having(ifnull(sum(Interest),0))>0 "
//                        + "union "
//                        + "select concat(acno,'')IdentificationNo,'Income from Interest'NatureOfincome,'194A'SectionUnder,ifnull(sum(Interest),0) "
//                        + "from dds_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custId + "' "
//                        + "and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') and dt between '" + finFirstDt + "' and '" + finLastDt + "' "
//                        + "having(ifnull(sum(Interest),0))>0").getResultList();

                intList = em.createNativeQuery("select a.IdentificationNo,a.NatureOfincome,a.SectionUnder,ifnull(b.interest,0) from "
                        + "(select concat(acno,' / ',cast(receiptNo as unsigned )) IdentificationNo,'Income from Interest'NatureOfincome,'194A'SectionUnder,0 interest,acno from " + tdsTable + " "
                        + "where customerid = '" + custId + "' and doc_details = '" + reportType + "'and fyear = '" + currentYear + "' group by acno,receiptNo)a "
                        + "left join "
                        + "(select concat(acno,' / ',cast(VoucherNo as unsigned )) IdentificationNo,'Income from Interest'NatureOfincome, "
                        + "'194A'SectionUnder,sum(Interest) interest,acno from td_interesthistory where acno in(select distinct acno from " + tdsTable + " "
                        + "where customerid = '" + custId + "' and doc_details = '" + reportType + "' and fyear = '" + currentYear + "')and dt between '" + finFirstDt + "' and '" + finLastDt + "' "
                        + "group by acno,VoucherNo "
                        + "union "
                        + "select concat(acno,'')IdentificationNo,'Income from Interest'NatureOfincome,'194A'SectionUnder,ifnull(sum(Interest),0) interest,acno "
                        + "from rd_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custId + "' "
                        + "and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') and dt between '" + finFirstDt + "' and '" + finLastDt + "'  "
                        + "having(ifnull(sum(Interest),0))>0  "
                        + "union  "
                        + "select concat(acno,'')IdentificationNo,'Income from Interest'NatureOfincome,'194A'SectionUnder,ifnull(sum(Interest),0) interest,acno "
                        + "from dds_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custId + "' "
                        + "and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') and dt between '" + finFirstDt + "' and '" + finLastDt + "' "
                        + "having(ifnull(sum(Interest),0))>0)b on a.IdentificationNo = b.IdentificationNo order by 1 ").getResultList();

                if (intList.isEmpty()) {
                    intList = em.createNativeQuery("select distinct acno,'Income from Interest'NatureOfincome,'194A'SectionUnder,0 interest from " + tdsTable + " where customerid = '" + custId + "' and doc_details = '" + reportType + "'and fyear = '" + currentYear + "' ").getResultList();
                }

                if (!intList.isEmpty()) {
                    for (int i = 0; i < intList.size(); i++) {
                        Vector intVector = (Vector) intList.get(i);
                        FormNo15gPart1Pojo pojo = new FormNo15gPart1Pojo();
                        String IdNo = intVector.get(0).toString();
                        String nature = intVector.get(1).toString();
                        String secUnder = intVector.get(2).toString();
                        double incomeAmt = Double.parseDouble(intVector.get(3).toString());
                        pojo.setIdNoOfRelevant(IdNo);
                        pojo.setNatureOfIncome(nature);
                        pojo.setSectionUnder(secUnder);
                        pojo.setIncomeAmt(incomeAmt);

                        pojo.setNameAssessee(vtr.get(0).toString());
                        pojo.setPanNo(vtr.get(1).toString());
                        if (vtr.get(1).toString().trim().substring(3, 4).equalsIgnoreCase("P")) {
                            pojo.setStatus("Individual");
                        } else {
                            pojo.setStatus("HUF");
                        }
                        pojo.setPreviousYear(finFirstDt.substring(0, 4) + "-" + finLastDt.substring(0, 4));
                        if (reportType.equalsIgnoreCase("Form 15G")) {
                            pojo.setrStatus(vtr.get(2).toString());
                        } else {
                            pojo.setDob(vtr.get(12).toString());
                        }
                        pojo.setFlatDoorBlock(vtr.get(3).toString());
                        pojo.setPremises(vtr.get(4).toString());
                        pojo.setRoadStreetLane(vtr.get(5).toString());
                        pojo.setAreaLocality(vtr.get(13).toString());
                        pojo.setTwonCityDist(vtr.get(6).toString());
                        pojo.setState(vtr.get(7).toString());
                        pojo.setPinNo(vtr.get(8).toString());
                        pojo.setEmail(vtr.get(9).toString());
                        pojo.setTelePhoneNo(vtr.get(11).toString());
                        if (!assessmentYear.equalsIgnoreCase("")) {
                            pojo.setA15Yes("Y");
                            pojo.setB15LatestAssYear(assessmentYear + " - " + (Integer.parseInt(assessmentYear.substring(2, 4)) + 1));
                        } else {
                            pojo.setA15No("N");
                            pojo.setB15LatestAssYear("");
                        }
                        pojo.setEstimatedIncome(estimateIncome1);
                        pojo.setEstimatedTotalIncome(estimateIncome1 + otherIncome + aggregateAmount);
                        pojo.setTotalNoOf15g(formNo);
                        pojo.setAggregateAmt(aggregateAmount);
                        dataList.add(pojo);
                    }
                } else {
                    FormNo15gPart1Pojo pojo = new FormNo15gPart1Pojo();
                    pojo.setNameAssessee(vtr.get(0).toString());
                    pojo.setPanNo(vtr.get(1).toString());
                    if (vtr.get(1).toString().trim().substring(3, 4).equalsIgnoreCase("P")) {
                        pojo.setStatus("Individual");
                    } else {
                        pojo.setStatus("HUF");
                    }
                    pojo.setPreviousYear(finFirstDt.substring(0, 4) + "-" + finLastDt.substring(0, 4));
                    if (reportType.equalsIgnoreCase("Form 15G")) {
                        pojo.setrStatus(vtr.get(2).toString());
                    } else {
                        pojo.setDob(vtr.get(12).toString());
                    }
                    pojo.setFlatDoorBlock(vtr.get(3).toString());
                    pojo.setPremises(vtr.get(4).toString());
                    pojo.setRoadStreetLane(vtr.get(5).toString()); // khattri change
                    pojo.setAreaLocality(vtr.get(13).toString());  // khattri change
                    pojo.setTwonCityDist(vtr.get(6).toString());
                    pojo.setState(vtr.get(7).toString());
                    pojo.setPinNo(vtr.get(8).toString());
                    pojo.setEmail(vtr.get(9).toString());
                    pojo.setTelePhoneNo(vtr.get(11).toString());
                    if (!assessmentYear.equalsIgnoreCase("")) {
                        pojo.setA15Yes("Y");
                        pojo.setB15LatestAssYear(assessmentYear + " - " + (Integer.parseInt(assessmentYear.substring(2, 4)) + 1));
                    } else {
                        pojo.setA15No("N");
                        pojo.setB15LatestAssYear("");
                    }

                    pojo.setEstimatedIncome(estimateIncome1);
                    pojo.setEstimatedTotalIncome(estimateIncome1 + otherIncome + aggregateAmount);
                    pojo.setTotalNoOf15g(formNo);
                    pojo.setAggregateAmt(aggregateAmount);
                    dataList.add(pojo);
                }

            } else {

                List partIIList = em.createNativeQuery("select a.bankname,a.bankaddress,ifnull(b.panno,''),ifnull(b.taxacno,''),ifnull(c.email,''),b.brphone from bnkadd a,parameterinfo b,branchmaster c  where c.brncode = b.brncode and b.brncode = '" + brCode + "' and a.alphacode = c.alphacode").getResultList();
                Vector vtr = (Vector) partIIList.get(0);
                String bnkName = vtr.get(0).toString();
                String bnkAdd = vtr.get(1).toString();
                String pan = vtr.get(2).toString();
                String taxNo = vtr.get(3).toString();
                String email = vtr.get(4).toString();
                String phoneNo = vtr.get(5).toString();
                List acnoList = em.createNativeQuery("select distinct acno from " + tdsTable + " where customerid = '" + custId + "' and doc_details = '" + reportType + "'and fyear = '" + currentYear + "' ").getResultList();
                double estimateIncome1 = 0d;
                if (!acnoList.isEmpty()) {
                    for (int i = 0; i < acnoList.size(); i++) {
                        Vector vtr1 = (Vector) acnoList.get(i);
                        String acno = vtr1.get(0).toString();
                        List incomeList = em.createNativeQuery("select sum(intAmt) from("
                                + "select ifnull(sum(Interest),0) as intAmt from td_interesthistory where acno = '" + acno + "' and dt between '" + finFirstDt + "' and '" + finLastDt + "'"
                                + "union "
                                + "select ifnull(sum(Interest),0) as intAmt from dds_interesthistory where acno = '" + acno + "' and dt between '" + finFirstDt + "' and '" + finLastDt + "'"
                                + "union "
                                + "select ifnull(sum(Interest),0) as intAmt from rd_interesthistory where acno = '" + acno + "' and dt between '" + finFirstDt + "' and '" + finLastDt + "')a").getResultList();
                        Vector incomeVec = (Vector) incomeList.get(0);
                        double estimateIncome = Double.parseDouble(incomeVec.get(0).toString());
                        estimateIncome1 = estimateIncome1 + estimateIncome;
                    }
                }
//                List dtList1 = em.createNativeQuery("select date_format(submission_date,'%d/%m/%Y'),date_format(dt,'%d/%m/%Y')from td_interesthistory a,"
//                        + "tds_docdetail b where b.customerid = '" + custId + "' and a.acno = b.acno and a.acno in(select distinct acno from tds_docdetail "
//                        + "where customerid = '" + custId + "' and orgBrnid = '" + brCode + "' and doc_details = '" + reportType + "') and a.dt between '" + finFirstDt + "'and '" + declareDt + "' "
//                        + "union "
//                        + "select date_format(submission_date,'%d/%m/%Y'),date_format(dt,'%d/%m/%Y')from rd_interesthistory a,tds_docdetail b where  b.customerid = '" + custId + "' "
//                        + "and a.acno = b.acno and a.acno in(select distinct acno from tds_docdetail where customerid = '" + custId + "' "
//                        + "and orgBrnid = '" + brCode + "' and doc_details = '" + reportType + "') and dt between '" + finFirstDt + "' and '" + declareDt + "' "
//                        + "union "
//                        + "select date_format(submission_date,'%d/%m/%Y'),date_format(dt,'%d/%m/%Y')from dds_interesthistory a,tds_docdetail b where  b.customerid = '" + custId + "' "
//                        + "and a.acno = b.acno and a.acno in(select distinct acno from tds_docdetail where customerid = '" + custId + "' "
//                        + "and orgBrnid = '" + brCode + "' and doc_details = '" + reportType + "') and dt between '" + finFirstDt + "' and '" + declareDt + "'").getResultList();
//                

                List dtList = em.createNativeQuery("select distinct date_format(dt,'%d/%m/%Y')from td_interesthistory where acno in"
                        + "(select distinct acno from " + tdsTable + " where customerid = '" + custId + "' and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') "
                        + "and dt between '" + finFirstDt + "'and '" + finLastDt + "'"
                        + "union "
                        + "select distinct date_format(dt,'%d/%m/%Y')from rd_interesthistory where acno in"
                        + "(select distinct acno from " + tdsTable + " where customerid = '" + custId + "' and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') "
                        + "and dt between '" + finFirstDt + "' and '" + finLastDt + "'"
                        + "union "
                        + "select distinct date_format(dt,'%d/%m/%Y')from dds_interesthistory where acno in"
                        + "(select distinct acno from " + tdsTable + " where customerid = '" + custId + "' and doc_details = '" + reportType + "' and fyear = '" + currentYear + "') "
                        + "and dt between '" + finFirstDt + "' and '" + finLastDt + "'").getResultList();

                if (!dtList.isEmpty()) {
                    for (int j = 0; j < dtList.size(); j++) {
                        FormNo15gPart1Pojo pojo = new FormNo15gPart1Pojo();
                        Vector dtVector = (Vector) dtList.get(j);
                        String intPaidDt = dtVector.get(0).toString();
                        pojo.setDeclarationDt(declareDt.substring(6, 8) + "/" + declareDt.substring(4, 6) + "/" + declareDt.substring(0, 4));
                        pojo.setPaidDt(intPaidDt);
                        pojo.setNameAssessee(bnkName);
                        pojo.setAreaLocality(bnkAdd);
                        pojo.setPanNo(pan);
                        pojo.setTanNo(taxNo);
                        pojo.setEmail(email);
                        pojo.setTelePhoneNo(phoneNo);
                        pojo.setEstimatedIncome(estimateIncome1);
                        pojo.setUniNo(uniNo);
                        dataList.add(pojo);
                    }
                } else {
                    FormNo15gPart1Pojo pojo = new FormNo15gPart1Pojo();
                    pojo.setNameAssessee(bnkName);
                    pojo.setAreaLocality(bnkAdd);
                    pojo.setPanNo(pan);
                    pojo.setTanNo(taxNo);
                    pojo.setEmail(email);
                    pojo.setTelePhoneNo(phoneNo);
                    pojo.setEstimatedIncome(estimateIncome1);
                    pojo.setUniNo(uniNo);
                    pojo.setDeclarationDt(declareDt.substring(6, 8) + "/" + declareDt.substring(4, 6) + "/" + declareDt.substring(0, 4));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }

        return dataList;
    }

    public List<FormNo15gPart1Pojo> getForm15g15hData(String brCode, String frDt, String toDt, String repType, String finYear) throws ApplicationException {
        List<FormNo15gPart1Pojo> dataList = new ArrayList<FormNo15gPart1Pojo>();
        List result = new ArrayList();
        try {

            if (repType.contains("Form 15G")) {
                repType = "Form 15G";
            } else if (repType.contains("Form 15H")) {
                repType = "Form 15H";
            }
            if (brCode.equalsIgnoreCase("0A")) {
                brCode = "90";
            }

            Map<String, String> map = new HashMap<String, String>();
            List stateList = em.createNativeQuery("select S_GNO,SS_GNO from cbs_ref_rec_mapping where GNO = '002'").getResultList();
            if (stateList.isEmpty()) {
                throw new ApplicationException("Please fill mapping table value");
            }
            for (int i = 0; i < stateList.size(); i++) {
                Vector stVector = (Vector) stateList.get(i);
                map.put(stVector.get(1).toString(), stVector.get(0).toString());
            }

            // List finList = em.createNativeQuery("select mindate,maxdate from yearend where f_year = '" + frDt.substring(0, 4) + "' and brncode = '" + brCode + "'").getResultList();          
            List finList = em.createNativeQuery("select mindate,maxdate,F_YEAR from yearend where f_year = (select  min(F_Year) from yearend where yearendflag='N' and brncode='" + brCode + "') and brncode = '" + brCode + "'").getResultList();
            if (finList.isEmpty()) {
                throw new ApplicationException("Data does not exist in  yearend table !");
            }
            Vector finVector = (Vector) finList.get(0);
            String finFirstDt = finVector.get(0).toString();
            String finLastDt = finVector.get(1).toString();
            String currentYear = finVector.get(2).toString();

            String tdsHeaderTable = "tds_docdetail_header", tdsTable = "tds_docdetail";
            if (!currentYear.equalsIgnoreCase(finYear)) {
                tdsHeaderTable = "tds_docdetail_header_his";
                tdsTable = "tds_docdetail_his";
                List yearList1 = em.createNativeQuery("select distinct MINDATE,MAXDATE,F_YEAR from yearend where F_YEAR = '" + finYear + "'").getResultList(); // for temparay basic
                Vector pVector = (Vector) yearList1.get(0);
                finFirstDt = pVector.get(0).toString();
                finLastDt = pVector.get(1).toString();
                currentYear = pVector.get(2).toString();
            }

            if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                if (repType.contains("Society & Exemption")) {
                    result = em.createNativeQuery("select CustFullName,pan_girnumber,rf3.ref_desc Residential_Status,perblock,pervillage,peraddressline1,"
                            + "PerDistrict CityTwon,rf2.ref_desc State,perpostalcode,ifnull(per_email,''),ifnull(perphonenumber,''),ifnull(mobilenumber,''),"
                            + "date_format(dateofbirth,'%d/%m/%Y'),date_format(b.submission_date,'%d/%m/%Y'),custId, formNo15G_15H, aggregateAmt, otherIncome,"
                            + " deductionAmt, estimatedIncome,b.uniqueIdentificationNo,b.orgBrnid,b.assessmentYear,date_format(DateOfBirth,'%d/%m/%Y'),peraddressline2,rf2.REF_CODE from cbs_customer_master_detail a,"
                            + "cbs_ref_rec_type rf2,cbs_ref_rec_type rf3," + tdsHeaderTable + " b where a.customerid = b.custid "
                            + "and date_format(b.submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'and b.uniqueIdentificationNo = ''"
                            + "and a.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'"
                            + "and a.nriflag = rf3.ref_code and rf3.ref_rec_no = '303' order by uniqueIdentificationNo").getResultList();
                } else {
                    result = em.createNativeQuery("select CustFullName,pan_girnumber,rf3.ref_desc Residential_Status,perblock,pervillage,peraddressline1,"
                            + "PerDistrict CityTwon,rf2.ref_desc State,perpostalcode,ifnull(per_email,''),ifnull(perphonenumber,''),ifnull(mobilenumber,''),"
                            + "date_format(dateofbirth,'%d/%m/%Y'),date_format(b.submission_date,'%d/%m/%Y'),custId, formNo15G_15H, aggregateAmt, otherIncome,"
                            + " deductionAmt, estimatedIncome,b.uniqueIdentificationNo,b.orgBrnid,b.assessmentYear,date_format(DateOfBirth,'%d/%m/%Y'),peraddressline2,rf2.REF_CODE from cbs_customer_master_detail a,"
                            + "cbs_ref_rec_type rf2,cbs_ref_rec_type rf3," + tdsHeaderTable + " b where a.customerid = b.custid "
                            + "and date_format(b.submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'and substring(b.uniqueIdentificationNo,1,1) = '" + repType.substring(7) + "'"
                            + "and a.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'"
                            + "and a.nriflag = rf3.ref_code and rf3.ref_rec_no = '303' order by uniqueIdentificationNo").getResultList();
                }
            } else {
                if (repType.contains("Society & Exemption")) {
                    result = em.createNativeQuery("select CustFullName,pan_girnumber,rf3.ref_desc Residential_Status,perblock,pervillage,peraddressline1,"
                            + "PerDistrict CityTwon,rf2.ref_desc State,perpostalcode,ifnull(per_email,''),ifnull(perphonenumber,''),ifnull(mobilenumber,''),"
                            + "date_format(dateofbirth,'%d/%m/%Y'),date_format(b.submission_date,'%d/%m/%Y'),custId, formNo15G_15H, aggregateAmt, "
                            + "otherIncome,deductionAmt, estimatedIncome,b.uniqueIdentificationNo,b.orgBrnid,b.assessmentYear,date_format(DateOfBirth,'%d/%m/%Y'),peraddressline2,rf2.REF_CODE from cbs_customer_master_detail a,"
                            + "cbs_ref_rec_type rf2,cbs_ref_rec_type rf3," + tdsHeaderTable + " b where b.orgbrnid = '" + brCode + "' "
                            + "and a.customerid = b.custid and date_format(b.submission_date,'%Y%m%d') between "
                            + "'" + frDt + "' and '" + toDt + "'and b.uniqueIdentificationNo = ''"
                            + "and a.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002' and a.nriflag = rf3.ref_code "
                            + "and rf3.ref_rec_no = '303' order by uniqueIdentificationNo").getResultList();
                } else {
                    result = em.createNativeQuery("select CustFullName,pan_girnumber,rf3.ref_desc Residential_Status,perblock,pervillage,peraddressline1,"
                            + "PerDistrict CityTwon,rf2.ref_desc State,perpostalcode,ifnull(per_email,''),ifnull(perphonenumber,''),ifnull(mobilenumber,''),"
                            + "date_format(dateofbirth,'%d/%m/%Y'),date_format(b.submission_date,'%d/%m/%Y'),custId, formNo15G_15H, aggregateAmt, "
                            + "otherIncome,deductionAmt, estimatedIncome,b.uniqueIdentificationNo,b.orgBrnid,b.assessmentYear,date_format(DateOfBirth,'%d/%m/%Y'),peraddressline2,rf2.REF_CODE from cbs_customer_master_detail a,"
                            + "cbs_ref_rec_type rf2,cbs_ref_rec_type rf3," + tdsHeaderTable + " b where b.orgbrnid = '" + brCode + "' "
                            + "and a.customerid = b.custid and date_format(b.submission_date,'%Y%m%d') between "
                            + "'" + frDt + "' and '" + toDt + "'and substring(b.uniqueIdentificationNo,1,1) = '" + repType.substring(7) + "'"
                            + "and a.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002' and a.nriflag = rf3.ref_code "
                            + "and rf3.ref_rec_no = '303' order by uniqueIdentificationNo").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    String assessName = ele.get(0).toString();
                    String panNo = ele.get(1).toString();
                    String residentailStatus = ele.get(2).toString();
                    String block = ele.get(3).toString();
                    String premises = ele.get(4).toString();
                    String addressLine1 = ele.get(5).toString(); // Flat/Door/Block for  khatttri
                    String cityTown = ele.get(6).toString();  // Area/ Locality or Town/City/ District
                    String state = ele.get(7).toString();
                    String postalCode = ele.get(8).toString();
                    String email = ele.get(9).toString();
                    String phoneNo = ele.get(10).toString();
                    String mobileNo = ele.get(11).toString();
                    String dob = ele.get(12).toString();
                    String declarationDt = ele.get(13).toString();
                    String custid = ele.get(14).toString();
                    String formNo = ele.get(15).toString();
                    String aggregateAmt = ele.get(16).toString();
                    String otherIncome = ele.get(17).toString();
                    String deductionAmt = ele.get(18).toString();
                    String estimatedIncome = ele.get(19).toString();
                    String uniqueIdNo = ele.get(20).toString();
                    String assessYear = ele.get(22).toString();
                    String dateOfbirth = ele.get(23).toString();
                    String addressLine2 = ele.get(24).toString(); // Flat/Door/Block No. for  khatttri
                    String stateCode = ele.get(25).toString();

//                    List result1 = em.createNativeQuery("select max(date_format(dt,'%d/%m/%Y')),sum(Interest),acno from td_interesthistory where acno in(select distinct acno from tds_docdetail where customerid = '" + custid + "') "
//                            + "and dt between '" + finFirstDt + "' and '" + ymd.format(dmyy.parse(declarationDt)) + "' "
//                            + "union "
//                            + "select max(date_format(dt,'%d/%m/%Y')),sum(Interest),acno from rd_interesthistory where acno in(select distinct acno from tds_docdetail where customerid = '" + custid + "') "
//                            + "and dt between '" + finFirstDt + "' and '" + ymd.format(dmyy.parse(declarationDt)) + "' "
//                            + "union "
//                            + "select max(date_format(dt,'%d/%m/%Y')),sum(Interest),acno from dds_interesthistory where acno in(select distinct acno from tds_docdetail where customerid = '" + custid + "') "
//                            + "and dt between '" + finFirstDt + "' and '" + ymd.format(dmyy.parse(declarationDt)) + "' ").getResultList();
                    List result1 = em.createNativeQuery("select max(pdt),sum(IntAmt),max(acNo) from("
                            + "select ifnull(max(dt),'') as pdt,ifnull(sum(Interest),0) as IntAmt,ifnull(acno,'')as acNo "
                            + "from td_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custid + "' and fyear = " + currentYear + ") "
                            + "and dt between '" + finFirstDt + "' and '" + finLastDt + "'"
                            + "union "
                            + "select ifnull(max(dt),'') as pdt,ifnull(sum(Interest),0) as IntAmt,ifnull(acno,'')as acNo "
                            + "from rd_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custid + "' and fyear = " + currentYear + ")"
                            + "and dt between '" + finFirstDt + "' and '" + finLastDt + "' "
                            + "union "
                            + "select ifnull(max(dt),'') as pdt,ifnull(sum(Interest),0) as IntAmt,ifnull(acno,'')as acNo "
                            + "from dds_interesthistory where acno in(select distinct acno from " + tdsTable + " where customerid = '" + custid + "' and fyear = " + currentYear + ") "
                            + "and dt between '" + finFirstDt + "' and '" + finLastDt + "')a").getResultList();

                    if (!result1.isEmpty()) {
                        for (int j = 0; j < result1.size(); j++) {
                            FormNo15gPart1Pojo pojo = new FormNo15gPart1Pojo();
                            Vector vtr = (Vector) result1.get(j);
                            String paidDt = vtr.get(0).toString();
                            String intAmt = vtr.get(1).toString();
                            String idNo = vtr.get(2).toString();
                            pojo.setUniNo(uniqueIdNo.trim());
                            pojo.setNameAssessee(assessName.trim());
                            pojo.setPanNo(panNo.trim());
                            if (panNo.trim().substring(3, 4).equalsIgnoreCase("P")) {
                                pojo.setStatus("1");
                            } else {
                                pojo.setStatus("2");
                            }
                            if (residentailStatus.toUpperCase().contains("RESIDENT INDIVIDUAL")) {
                                pojo.setrStatus("RES");
                            } else if (residentailStatus.toUpperCase().contains("NON RESIDENT")) {
                                pojo.setrStatus("NRI");
                            } else {
                                pojo.setrStatus(residentailStatus.trim());
                            }
                            pojo.setPreviousYear(finFirstDt.substring(0, 4));
                            pojo.setFlatDoorBlock(addressLine1.trim());
                            pojo.setRoadStreetLane(addressLine2.trim());
                            pojo.setPremises(premises.trim());
                            if (cityTown.trim().equalsIgnoreCase("")) {
                                pojo.setAreaLocality(".");
                                pojo.setTwonCityDist(".");
                            } else {
                                pojo.setAreaLocality(cityTown.trim());
                                pojo.setTwonCityDist(cityTown.trim());
                            }
//                            if (state.toUpperCase().contains("DELHI")) {
//                                pojo.setState("09");
//                            } else if (state.toUpperCase().contains("UTTAR PRADESH")) {
//                                pojo.setState("31");
//                            } else {
//                                pojo.setState(state);
//                            }
                            pojo.setState(map.get(stateCode).trim());
                            pojo.setPinNo(postalCode.trim());
                            pojo.setEmail(email.trim());
                            pojo.setStdCode("");
                            pojo.setTelePhoneNo("");
                            pojo.setMobileNo(mobileNo.trim());
                            if (assessYear.equalsIgnoreCase("")) {
                                pojo.setA15Yes("N");
                                pojo.setB15LatestAssYear("");
                            } else {
                                pojo.setA15Yes("Y");
                                pojo.setB15LatestAssYear(assessYear.trim());
                            }
                            pojo.setEstimatedIncome1(formatter.format(Double.parseDouble(estimatedIncome)));
                            double estimatedTotalIncome = Double.parseDouble(estimatedIncome) + Double.parseDouble(otherIncome) + Double.parseDouble(aggregateAmt);
                            pojo.setEstimatedTotalIncome1(formatter.format(estimatedTotalIncome));
                            pojo.setTotalNoOf15g(formNo.trim());
                            pojo.setAggregateAmt1(formatter.format(Double.parseDouble(aggregateAmt)));
                            pojo.setDeclarationDt(declarationDt);
                            pojo.setPaidAmt1(formatter.format(Double.parseDouble(intAmt)));
                            if (paidDt.equalsIgnoreCase("")) {
                                pojo.setPaidDt(paidDt);
                            } else {
                                pojo.setPaidDt(dmyy.format(yymmdd.parse(paidDt)));
                            }
                            pojo.setIdNoOfRelevant(idNo.trim());
                            pojo.setNatureOfIncome("Income from Interest");
                            pojo.setSectionUnder("194A");
                            pojo.setIncomeAmt1(formatter.format(Double.parseDouble(intAmt)));
                            pojo.setRecordType("");
                            pojo.setDateOfbirth(dateOfbirth.trim());
                            dataList.add(pojo);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public BigDecimal getTdsDeductedInterest(String brCode, String frDt, String toDt) throws ApplicationException {
        try {
            String brQuery = "";
            if (!brCode.equals("0A")) {
                brQuery = " and substring(acno,1,2) = '" + brCode + "'";
            }
            List tdsList = em.createNativeQuery("select cast(sum(m.intamt) as decimal(14,2)) from "
                    + " (select a.acno, a.voucherno,a.intamt, b.tds from "
                    + " (select acno,voucherno,sum(interest) as intamt from td_interesthistory where dt between '" + frDt + "' and '" + toDt + "' "
                    + " and acno not in (select acno from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "') "
                    + brQuery + " group by acno,voucherno"
                    + " union all"
                    + " select acno,0 as voucherno,sum(interest)as intamt from rd_interesthistory where dt between '" + frDt + "' and '" + toDt + "' "
                    + " and acno not in (select acno from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "') "
                    + brQuery + " group by acno"
                    + " union all"
                    + " select acno,0 as voucherno,sum(interest)as intamt from dds_interesthistory where dt between '" + frDt + "' and '" + toDt + "' "
                    + " and acno not in (select acno from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "') "
                    + brQuery + "group by acno"
                    + " ) a,"
                    + " (select acno,voucherno,sum(tds) as tds from tds_reserve_history where dt between '" + frDt + "' and '" + toDt + "' "
                    + " and acno not in (select acno from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "')" + brQuery
                    + " group by acno,voucherno) b where a.acno=b.acno and a.voucherno=b.voucherno) m").getResultList();

            BigDecimal intAmt = new BigDecimal(0);
            if (!tdsList.isEmpty()) {
                Vector vtr = (Vector) tdsList.get(0);
                intAmt = new BigDecimal(vtr.get(0).toString());
            }

            //intAmt = intAmt.add(getVoucherWiseInterest(frDt, toDt, brQuery));
            return intAmt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getVoucherWiseInterest(String frDt, String toDt, String brCode) throws ApplicationException {
        try {
            BigDecimal tdsAmt = new BigDecimal(0);

            String brQuery = "";
            if (!brCode.equals("0A")) {
                brQuery = " and substring(acno,1,2) = '" + brCode + "'";
            }

            Map<String, String> acNatureMap = new HashMap<>();
            List acList = em.createNativeQuery("select AcctCode,acctNature from accounttypemaster a,(select distinct substring(a.acno,3,2) as acCode from tds_reserve_history a,"
                    + "(select distinct acno from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "')b "
                    + "where a.dt between '" + frDt + "' and '" + toDt + "' and a.acno=b.acno group by a.acno)c where a.acctcode = c.acCode").getResultList();
            for (int j = 0; j < acList.size(); j++) {
                Vector ele = (Vector) acList.get(j);
                acNatureMap.put(ele.get(0).toString(), ele.get(1).toString());
            }
//            List tdsDocDetailList = em.createNativeQuery("select acno,voucherno,sum(tds) as tds from tds_reserve_history where dt "
//                    + "between '" + frDt + "' and '" + toDt + "' and acno in (select acno from tds_docdetail_his where submission_date between "
//                    + "'" + frDt + "' and '" + toDt + "') " + brQuery + " group by acno,voucherno order by 1,2 ").getResultList();
            List tdsDocDetailList = em.createNativeQuery("select a.acno,voucherno,sum(tds) as tds from tds_reserve_history a, "
                    + "(select distinct acno from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "')b "
                    + "where a.dt between '" + frDt + "' and '" + toDt + "' and a.acno = b.acno " + brQuery + " group by a.acno,voucherno order by 1,2").getResultList();

            List docDetailList = em.createNativeQuery("select acno,receiptno,max(date_format(date(submission_date),'%Y%m%d')) from tds_docdetail_his "
                    + "where submission_date between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by acno,receiptno order by 1,2 ").getResultList();

            BigDecimal intAmt = new BigDecimal(0);

            for (int i = 0; i < tdsDocDetailList.size(); i++) {
                Vector vtr = (Vector) tdsDocDetailList.get(i);
                String acno = vtr.get(0).toString();

                float voucher = Float.parseFloat(vtr.get(1).toString());
                tdsAmt = tdsAmt.add(new BigDecimal(vtr.get(2).toString()));

                String submissionDt = getSubmissionDate(docDetailList, acno, voucher);
                // String acNature = ftsPosting.getAccountNature(acno);
                String acNature = acNatureMap.get(acno.substring(2, 4));
                String table = common.getIntTableName(acNature);

                String tDt = toDt;
                if (submissionDt != null) {
                    tDt = submissionDt;
                }
                List intDetailList = null;
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    intDetailList = em.createNativeQuery("select sum(interest) from td_interesthistory where acno = '" + acno + "' and voucherno = "
                            + voucher + "  and dt between '" + frDt + "' and '" + tDt + "' ").getResultList();
                } else {
                    intDetailList = em.createNativeQuery("select sum(interest) from " + table + " where acno = '" + acno + "' "
                            + "  and dt between '" + frDt + "' and '" + tDt + "' ").getResultList();
                }
                if (!intDetailList.isEmpty()) {
                    Vector intVtr = (Vector) intDetailList.get(0);
                    intAmt = intAmt.add(new BigDecimal(intVtr.get(0) != null ? intVtr.get(0).toString() : "0"));
                }
            }
            return intAmt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getExemptedInterest(String brCode, String frDt, String toDt) throws ApplicationException {
        try {
            String brQuery = "";
            if (!brCode.equals("0A")) {
                brQuery = " and substring(acno,1,2) = '" + brCode + "'";
            }
//            List intList = em.createNativeQuery("select ifnull(cast(sum(m.intamt) as decimal(14,2)),0) from "
//                    + " (select acno,voucherno,sum(interest) as intamt from td_interesthistory where dt between '" + frDt + "' and '" + toDt + "' "
//                    + " and acno in (select acno from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "') "
//                    + brQuery + " group by acno,voucherno"
//                    + " union all"
//                    + " select acno,0 as voucherno,sum(interest)as intamt from rd_interesthistory where dt between '" + frDt + "' and '" + toDt + "' "
//                    + " and acno in (select acno from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "') "
//                    + brQuery + " group by acno"
//                    + " union all"
//                    + " select acno,0 as voucherno,sum(interest)as intamt from dds_interesthistory where dt between '" + frDt + "' and '" + toDt + "' "
//                    + " and acno in (select acno from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "') "
//                    + brQuery + "group by acno ) m").getResultList();

            List intList = em.createNativeQuery("select ifnull(cast(sum(m.intamt) as decimal(14,2)),0) from "
                    + "( "
                    + "select a.acno,voucherno,sum(interest) as intamt from td_interesthistory a, "
                    + "(select distinct acno from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "')b "
                    + " where a.dt between '" + frDt + "' and '" + toDt + "' and a.acno = b.acno " + brQuery + " group by a.acno,voucherno "
                    + "union all "
                    + "select a.acno,0 as voucherno,sum(interest) as intamt from rd_interesthistory a, "
                    + "(select distinct acno from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "')b "
                    + " where a.dt between '" + frDt + "' and '" + toDt + "' and a.acno = b.acno " + brQuery + " group by a.acno,voucherno "
                    + "union all "
                    + "select a.acno,0 as voucherno,sum(interest) as intamt from dds_interesthistory a, "
                    + "(select distinct acno from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "')b "
                    + " where a.dt between '" + frDt + "' and '" + toDt + "' and a.acno = b.acno " + brQuery + " group by a.acno,voucherno "
                    + ") m").getResultList();

            BigDecimal intAmt = new BigDecimal(0);
            if (!intList.isEmpty()) {
                Vector vtr = (Vector) intList.get(0);
                intAmt = new BigDecimal(vtr.get(0).toString());
            }
            //intAmt = intAmt.subtract(getVoucherWiseInterest(frDt, toDt, brQuery));
            return intAmt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private String getSubmissionDate(List docDetailList, String acno, float voucherNo) throws ApplicationException {
        try {
            for (int i = 0; i < docDetailList.size(); i++) {
                Vector vtr = (Vector) docDetailList.get(i);
                float vchNo = 0;
                if (!vtr.get(1).toString().equals("")) {
                    vchNo = Float.parseFloat(vtr.get(1).toString());
                }
                if (vtr.get(0).toString().equals(acno) && vchNo == voucherNo) {
                    return vtr.get(2).toString();
                }
            }
            return null;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public BigDecimal getInterestOfMergeIdAfterToDate(String brCode, String frDt, String toDt) throws ApplicationException {
        try {
            String brQuery = "";
            if (!brCode.equals("0A")) {
                brQuery = " and substring(z.acno,1,2) = '" + brCode + "'";
            }
            BigDecimal intAmt = new BigDecimal(0);
//            List intList = em.createNativeQuery("select ifnull(sum(z.intamt),0) from "
//                    + "(select c.CustId as cid, cast(sum(a.interest) as decimal(25,2)) as intAmt,c.acno as acno from td_interesthistory a, customerid c "
//                    + "where a.Acno = c.Acno  and a.dt between  '" + frDt + "' and '" + toDt + "' group by c.CustId,c.acno "
//                    + "union all "
//                    + "select c.CustId as cid, cast(sum(a.interest) as decimal(25,2)) as intAmt,c.acno as acno from rd_interesthistory a, customerid c "
//                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId,c.acno "
//                    + "union all "
//                    + "select c.CustId as cid, cast(sum(a.interest) as decimal(25,2)) as intAmt,c.acno as acno from dds_interesthistory a, customerid c "
//                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId,c.acno) z, "
//                    + "(select custid from ( select c.CustId, cast(sum(a.interest) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
//                    + "where a.Acno = c.Acno  and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
//                    + "union all "
//                    + "select c.CustId, cast(sum(a.interest) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
//                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
//                    + "union all "
//                    + "select c.CustId, cast(sum(a.interest) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c "
//                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId) j group by custid having sum(intAmt) >= 10000) m "
//                    + "where z.cid = m.custid and z.cid not in (select c.custid from tds_reserve_history a, customerid c where a.Acno = c.Acno and "
//                    + "dt between '" + frDt + "' and '" + toDt + "' group by c.CustId) and z.cid not in (select customerid from tds_docdetail_his where "
//                    + "submission_date between '" + frDt + "' and '" + toDt + "') and z.cid not in (select orgid from cbs_id_merge_auth where mergeddate > "
//                    + "'" + toDt + "') and z.cid not in (select mergid from cbs_id_merge_auth where mergeddate > '" + toDt + "') " + brQuery + " ").getResultList();

            List intList = em.createNativeQuery("select ifnull(sum(z.intamt),0) from "
                    + "(select c.CustId as cid, cast(sum(a.interest) as decimal(25,2)) as intAmt,c.acno as acno from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between  '" + frDt + "' and '" + toDt + "' group by c.CustId,c.acno "
                    + "union all "
                    + "select c.CustId as cid, cast(sum(a.interest) as decimal(25,2)) as intAmt,c.acno as acno from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId,c.acno "
                    + "union all "
                    + "select c.CustId as cid, cast(sum(a.interest) as decimal(25,2)) as intAmt,c.acno as acno from dds_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId,c.acno) z, "
                    + "(select custid from ( select c.CustId, cast(sum(a.interest) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId, cast(sum(a.interest) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId, cast(sum(a.interest) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId) j group by custid having sum(intAmt) >= 10000) m  "
                    + "where z.cid = m.custid "
                    + "and z.cid not in (select cId from "
                    + "(select c.custid as cId from tds_reserve_history a, customerid c where a.Acno = c.Acno and dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
                    + "union "
                    + "select customerid as cId from tds_docdetail_his where submission_date between '" + frDt + "' and '" + toDt + "' "
                    + "union "
                    + "select orgid as cId from cbs_id_merge_auth where mergeddate > '" + toDt + "' "
                    + "union "
                    + "select mergid as cId from cbs_id_merge_auth where mergeddate > '" + toDt + "')aa) " + brQuery + " ").getResultList();

            if (!intList.isEmpty()) {
                Vector vtr1 = (Vector) intList.get(0);
                intAmt = new BigDecimal(vtr1.get(0).toString());
            }
            return intAmt;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<PfmsRegistrationDetailPojo> getPfmsRegstrationData(String brCode, String frDt, String toDt) throws ApplicationException {
        List<PfmsRegistrationDetailPojo> dataList = new ArrayList<PfmsRegistrationDetailPojo>();
        List result = new ArrayList();
        try {

            if (brCode.equalsIgnoreCase("0A")) {
//                result = em.createNativeQuery("select cb.customerid,pf.acno,cb.custname,cb.fathername,cb.PerAddressLine1,cb.pan_girnumber,pf.error_code "
//                        + "from pfms_req_details pf,accountmaster ac,customerid cu,cbs_customer_master_detail cb where pf.acno = ac.acno "
//                        + "and pf.acno = cu.acno and cu.custid = cast(cb.customerid as unsigned) "
//                        + "and date_format(STR_TO_DATE(substring(pf.req_id,11,8),'%d%m%Y'),'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
                result = em.createNativeQuery("select a.customerid,a.acno,a.custname,a.fathername,a.PerAddressLine1,a.pan_girnumber,a.error_code,"
                        + "a.txn_id from (select cb.customerid,pf.acno,cb.custname,cb.fathername,cb.PerAddressLine1,cb.pan_girnumber,pf.error_code,"
                        + "pf.txn_id  from pfms_req_details pf,accountmaster ac,customerid cu,cbs_customer_master_detail cb "
                        + "where pf.acno = ac.acno and pf.acno = cu.acno and cu.custid = cast(cb.customerid as unsigned) "
                        + "and date_format(STR_TO_DATE(substring(pf.req_id,11,8),'%d%m%Y'),'%Y%m%d') between '" + frDt + "' and '" + toDt + "')a,"
                        + "(select max(txn_id) as txnId from pfms_req_details where date_format(STR_TO_DATE(substring(req_id,11,8),'%d%m%Y'),'%Y%m%d') "
                        + "between '" + frDt + "' and '" + toDt + "' group by acno)b where a.txn_id = b.txnId").getResultList();
            } else {
//                result = em.createNativeQuery("select cb.customerid,pf.acno,cb.custname,cb.fathername,cb.PerAddressLine1,cb.pan_girnumber,pf.error_code "
//                        + "from pfms_req_details pf,accountmaster ac,customerid cu,cbs_customer_master_detail cb where substring(pf.acno,1,2)= '" + brCode + "' "
//                        + "and pf.acno = ac.acno and pf.acno = cu.acno and cu.custid = cast(cb.customerid as unsigned) "
//                        + "and date_format(STR_TO_DATE(substring(pf.req_id,11,8),'%d%m%Y'),'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
                result = em.createNativeQuery("select a.customerid,a.acno,a.custname,a.fathername,a.PerAddressLine1,a.pan_girnumber,a.error_code,a.txn_id "
                        + "from(select cb.customerid,pf.acno,cb.custname,cb.fathername,cb.PerAddressLine1,cb.pan_girnumber,pf.error_code,pf.txn_id "
                        + "from pfms_req_details pf,accountmaster ac,customerid cu,cbs_customer_master_detail cb where substring(pf.acno,1,2)= '" + brCode + "' "
                        + "and pf.acno = ac.acno and pf.acno = cu.acno and cu.custid = cast(cb.customerid as unsigned) "
                        + "and date_format(STR_TO_DATE(substring(pf.req_id,11,8),'%d%m%Y'),'%Y%m%d') between '" + frDt + "' and '" + toDt + "') a,"
                        + "(select max(txn_id) as txnId from pfms_req_details where substring(acno,1,2)= '" + brCode + "' "
                        + "and date_format(STR_TO_DATE(substring(req_id,11,8),'%d%m%Y'),'%Y%m%d') between '" + frDt + "' and '" + toDt + "' group by acno)b "
                        + "where a.txn_id = b.txnId").getResultList();

            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    PfmsRegistrationDetailPojo pojo = new PfmsRegistrationDetailPojo();
                    Vector element = (Vector) result.get(i);
                    pojo.setCustId(element.get(0).toString());
                    pojo.setAcNo(element.get(1).toString());
                    pojo.setCustName(element.get(2).toString());
                    pojo.setFatherName(element.get(3).toString());
                    pojo.setAdd(element.get(4).toString());
                    pojo.setPan(element.get(5).toString());
                    String errorCode = element.get(6).toString();
                    if (errorCode.equalsIgnoreCase("")) {
                        pojo.setStatus("Success");
                        pojo.setReason("");
                    } else {
                        pojo.setStatus("Unsuccess");
                        pojo.setReason(getErrorCodeDescription(errorCode));
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public String getErrorCodeDescription(String errorCode) throws ApplicationException {
        String errorDesc = "";
        try {
            List list = em.createNativeQuery("select error_description from pfms_error_code where error_code = '" + errorCode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                errorDesc = vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return errorDesc;
    }

    public List<CurrencyExchangePojo> getCurrencyExchangrData(String brCode, String toDt, String typeOfReport) throws ApplicationException {
        List<CurrencyExchangePojo> dataList = new ArrayList<CurrencyExchangePojo>();
        try {
            List list;
            if (typeOfReport.equalsIgnoreCase("Y")) {
                /*IN SUMMARY*/
                if (brCode.equalsIgnoreCase("A")) {
                    list = em.createNativeQuery("select ifnull(b.bankname,''), ifnull(b.bankaddress,''),ifnull(a.IFSC_CODE,''), ifnull(c.brcode,''), date_format(c.entrydate,'%d/%m/%Y'), sum(ifnull(den500,0)) as den500, sum(ifnull(den1000,0)) as den1000  from "
                            + " branchmaster a, bnkadd b, money_exchange_details c where a.AlphaCode = b.alphacode  "
                            + " and  a.BrnCode = cast(c.brcode as unsigned) and date_format(c.entrydate,'%Y%m%d') = '" + toDt + "' and c.auth = 'Y' group by c.brcode, c.entrydate order by c.brcode, c.entrydate ").getResultList();
                } else {
                    list = em.createNativeQuery("select ifnull(b.bankname,''), ifnull(b.bankaddress,''),ifnull(a.IFSC_CODE,''), ifnull(c.brcode,''), date_format(c.entrydate,'%d/%m/%Y'), sum(ifnull(den500,0)) as den500, sum(ifnull(den1000,0)) as den1000  from "
                            + " branchmaster a, bnkadd b, money_exchange_details c where a.AlphaCode = b.alphacode and a.BrnCode = " + brCode
                            + " and a.BrnCode = cast(c.brcode as unsigned)  and date_format(c.entrydate,'%Y%m%d') = '" + toDt + "' and c.auth = 'Y' group by c.brcode, c.entrydate  order by c.brcode, c.entrydate").getResultList();
                }
                CurrencyExchangePojo pojo = new CurrencyExchangePojo();
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vtr = (Vector) list.get(i);
                        pojo = new CurrencyExchangePojo();
                        /*500*/
                        pojo.setBnkName(vtr.get(0).toString());
                        pojo.setBnkBranch(vtr.get(1).toString());
                        pojo.setBnkIfsc(vtr.get(2).toString());
                        pojo.setBnkExchangeAtBranch(vtr.get(4).toString());
                        pojo.setDenomination("Rs 500");
                        pojo.setNoOfPieces(Integer.parseInt(vtr.get(5).toString()));
                        pojo.setTotalValue(new BigDecimal(vtr.get(5).toString()).multiply(new BigDecimal("500")));
                        dataList.add(pojo);

                        /*1000*/
                        pojo = new CurrencyExchangePojo();
                        pojo.setBnkName(vtr.get(0).toString());
                        pojo.setBnkBranch(vtr.get(1).toString());
                        pojo.setBnkIfsc(vtr.get(2).toString());
                        pojo.setBnkExchangeAtBranch(vtr.get(4).toString());
                        pojo.setDenomination("Rs 1000");
                        pojo.setNoOfPieces(Integer.parseInt(vtr.get(6).toString()));
                        pojo.setTotalValue(new BigDecimal(vtr.get(6).toString()).multiply(new BigDecimal("1000")));
                        dataList.add(pojo);
                    }
                }
            } else {
                /*IN DETAIL*/
                if (brCode.equalsIgnoreCase("A")) {
                    list = em.createNativeQuery(" select ifnull(b.bankname,''), ifnull(b.bankaddress,''),ifnull(a.IFSC_CODE,''), ifnull(c.brcode,''), date_format(c.entrydate,'%d/%m/%Y'), "
                            + " ifnull(c.den500,0) as rs500, ifnull(c.den1000,0) as rs1000,c.total,  c.tenderer_name, c.idproof, c.idno, c.enterby, c.authBy "
                            + " from  branchmaster a, bnkadd b, money_exchange_details c "
                            + " where a.AlphaCode = b.alphacode  "
                            + " and  a.BrnCode = cast(c.brcode as unsigned) and  c.auth = 'Y'  and date_format(c.entrydate,'%Y%m%d') = '" + toDt + "' "
                            + " order by  cast(c.brcode as unsigned),  c.tenderer_name, c.idproof, c.idno ").getResultList();
                } else {
                    list = em.createNativeQuery(" select ifnull(b.bankname,''), ifnull(b.bankaddress,''),ifnull(a.IFSC_CODE,''), ifnull(c.brcode,''), date_format(c.entrydate,'%d/%m/%Y'), "
                            + " ifnull(c.den500,0) as rs500, ifnull(c.den1000,0) as rs1000,c.total,  c.tenderer_name, c.idproof, c.idno, c.enterby, c.authBy "
                            + " from  branchmaster a, bnkadd b, money_exchange_details c "
                            + " where a.AlphaCode = b.alphacode  "
                            + " and  a.BrnCode = cast(c.brcode as unsigned) and  c.auth = 'Y'  and date_format(c.entrydate,'%Y%m%d') = '" + toDt + "' and a.BrnCode = " + brCode + " "
                            + " order by  cast(c.brcode as unsigned),  c.tenderer_name, c.idproof, c.idno ").getResultList();
                }
                CurrencyExchangePojo pojo = new CurrencyExchangePojo();
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vtr = (Vector) list.get(i);
                        pojo = new CurrencyExchangePojo();
                        pojo.setBnkName(vtr.get(0).toString());
                        pojo.setBnkBranch(vtr.get(1).toString());
                        pojo.setBnkIfsc(vtr.get(2).toString());
                        pojo.setBnkExchangeAtBranch(vtr.get(4).toString());
                        pojo.setDenomination("Rs 500");
                        pojo.setRs500(Integer.parseInt(vtr.get(5).toString()));
                        pojo.setRs1000(Integer.parseInt(vtr.get(6).toString()));
                        pojo.setTotalValue(new BigDecimal(vtr.get(7).toString()));
                        pojo.setNameOfTenderer(vtr.get(8).toString());
                        pojo.setTypeOfId(vtr.get(9).toString());
                        pojo.setIdNo(vtr.get(10).toString());
                        pojo.setEnterBy(vtr.get(11).toString());
                        pojo.setAuthBy(vtr.get(12).toString());

                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<ctrMoreThan1Crore> ctrMoreThan1CroreReport(List acNatureList, String acType, String trnCrType, String trnDrType, BigDecimal repTypeAmt, String fromDt, String toDt, String brCode, String repType, String userName, BigDecimal repToAmt, String amtType, String denominationFlag) throws ApplicationException {
        List<ctrMoreThan1Crore> dataList = new ArrayList<ctrMoreThan1Crore>();
        try {
            Integer arfAccLine = 0, inpLineNo = 0, trnLineNo = 0, lpeLineNo = 0;
            Integer year;
            String ctrParameter, acTypeNot = "''", amtQuery = "", fiuId = "", denominationQuery = "";
            Double ctrLimit = 0d;
            /**
             * *Limit is implemented for CCBL, Because they reported more than
             * 50000 *
             */
            List result = em.createNativeQuery("select ifnull(fiureid,'BANUCXXXXX') from branchmaster b,parameterinfo p, "
                    + " bnkadd c where b.BrnCode = p.BrnCode and b.BrnCode in ( " + brCode
                    + " ) and b.AlphaCode = c.alphacode").getResultList();
            if (!result.isEmpty()) {
                Vector vtr = (Vector) result.get(0);
                fiuId = vtr.get(0).toString();
            }

            if (amtType.equalsIgnoreCase("Between")) {
                amtQuery = " between " + repTypeAmt + " and " + repToAmt;
            } else if (amtType.equalsIgnoreCase("till")) {
                amtQuery = " >= " + repTypeAmt;
            }
//            List ctrAppDtList = em.createNativeQuery("select ifnull(code,'20161108') from cbs_parameterinfo where name = 'CTR_1CRORE_APP_DT'").getResultList();
//            if (!ctrAppDtList.isEmpty()) {
//                Vector ctrAppDtVect = (Vector) ctrAppDtList.get(0);
//                fromDt = ctrAppDtVect.get(0).toString();
//            }
            List acTypeNotList = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name = 'CTR_ACCTCODE_NOT'").getResultList();
            if (!acTypeNotList.isEmpty()) {
                Vector acTypeNotVect = (Vector) acTypeNotList.get(0);
                acTypeNot = acTypeNotVect.get(0).toString();
            }

            if (repType.equalsIgnoreCase("9")) {
//                denominationQuery = " and a.acno in ( "
//                        + " select distinct acno  from denomination_detail where acno in ( "
//                        + " select distinct acno   from denomination_detail where dt  between '" + fromDt + "' and '" + toDt + "' and ty in (0) and new_old_flag = 'O' and denomination in (500,1000) group by acno having sum(denomination*denomination_value) " + amtQuery
//                        + " ) and  dt  between '" + fromDt + "' and '" + toDt + "' and ty in (0) and new_old_flag = 'O' and denomination in (500,1000) group by acno having count(distinct recno)>1 ) ";
                denominationQuery = " and a.acno in ( "
                        + " select distinct d.acno from "
                        + " (select a.acno, sum(a.recno) as recno from "
                        + " (select acno, count(distinct recno) as recno from denomination_detail where "
                        + " dt between '" + fromDt + "' and '" + toDt + "' and ty = 0 and new_old_flag = 'O' "
                        + " and denomination in (500,1000) "
                        + " group by acno ,dt) a group by a.acno) d, "
                        + " (select acno, sum(denomination*denomination_value) as amount from denomination_detail where acno in ( "
                        + " select distinct acno from denomination_detail where dt between '" + fromDt + "' and '" + toDt + "' "
                        + " and ty = 0 and new_old_flag = 'O' and denomination in (500,1000) "
                        + " group by acno  having sum(denomination*denomination_value) " + amtQuery + ") "
                        + " and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and new_old_flag = 'O' "
                        + " and denomination in (500,1000) group by acno) b "
                        + " where d.acno = b.acno group by d.acno having sum(d.recno)  >1 )";
            }
            for (int i = 0; i < acNatureList.size(); i++) {
                String acType1 = "";
                Vector acnatureVect = (Vector) acNatureList.get(i);
                String acNature = acnatureVect.get(0).toString();
                List acTypeList = common.getAccType(acnatureVect.get(0).toString());
                if (acTypeList.size() > 0) {
                    for (int z = 0; z < acTypeList.size(); z++) {
                        Vector actypevect = (Vector) acTypeList.get(z);
                        if (z == 0) {
                            acType1 = "'" + actypevect.get(0).toString() + "'";
                        } else {
                            acType1 = acType1.concat(",'" + actypevect.get(0).toString() + "'");
                        }
                    }
                }
                acType = acType1;
                List acTransactionDetailsList = null;
                if (repType.equalsIgnoreCase("9") && denominationFlag.equalsIgnoreCase("N")) {
//                    denominationQuery = " and a.acno in ( "
//                            + " select distinct acno  from " + CbsUtil.getReconTableName(acNature) + " where acno in ( "
//                            + " select distinct acno   from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and ty = 0  and trantype in (" + trnCrType + ")  group by acno having sum(cramt)  " + amtQuery
//                            + " ) and  dt between '" + fromDt + "' and '" + toDt + "' and ty = 0  and trantype in (" + trnCrType + ")  group by acno having count(distinct recno)>1) ";
                    denominationQuery = " and a.acno in ( select distinct d.acno from "
                            + " (select a.acno, sum(a.recno) as recno from "
                            + " (select acno, count(distinct recno) as recno from " + CbsUtil.getReconTableName(acNature) + " where "
                            + " dt between '" + fromDt + "' and '" + toDt + "' and ty = 0 and trantype in (" + trnCrType + ") "
                            + " group by acno ,dt) a group by a.acno ) d, "
                            + " (select acno, sum(cramt) as amount from " + CbsUtil.getReconTableName(acNature) + " where acno in ( "
                            + " select distinct acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' "
                            + " and ty = 0 and trantype in (0) group by acno  having sum(cramt) " + amtQuery + " ) "
                            + " and dt between '" + fromDt + "' and '" + toDt + "'  and ty = 0 and trantype in (" + trnCrType + ") group by acno) b "
                            + " where d.acno = b.acno group by d.acno having sum(d.recno)>1) ";
                }
                String query = "select mst.custname, mst.openingdt, mst.opermode, mst.OperationalRiskRating, mst.custid, mst.PAN_GIRNumber, "
                        + " mst.PerAddressLine1, mst.PerAddressLine2, mst.PerVillage, mst.PerBlock, mst.PerCityCode, mst.PerStateCode, "
                        + " mst.PerPostalCode, mst.PerCountryCode, mst.PerPhoneNumber, mst.PerTelexNumber, mst.PerFaxNumber, "
                        + " mst.MailAddressLine1, mst.MailAddressLine2, mst.MailVillage, mst.MailBlock, mst.MailCityCode,"
                        + " mst.MailStateCode, mst.MailPostalCode, mst.MailCountryCode, mst.MailPhoneNumber, mst.MailTelexNumber,"
                        + " mst.MailFaxNumber, mst.mobilenumber, mst.EmailID, mst.accstatus, mst.AADHAAR_NO, mst.VoterIDNo, "
                        + " mst.DrivingLicenseNo, mst.DateOfBirth, mst.PassportNo, mst.acno, cr.cramt, dr.dramt, cr.crdt, dr.drdt  from "
                        + " (select a.acno, a.custname, a.openingdt, a.opermode, ifnull(c.OperationalRiskRating,'') as OperationalRiskRating, b.custid, IFNULL(c.PAN_GIRNumber,'') as PAN_GIRNumber, "
                        + " IFNULL(c.PerAddressLine1,'') as PerAddressLine1, IFNULL(c.PerAddressLine2,'') as PerAddressLine2, IFNULL(c.PerVillage,'') as PerVillage, IFNULL(c.PerBlock,'') as  PerBlock, IFNULL(c.PerCityCode,'0') as PerCityCode, IFNULL(c.PerStateCode,'') as PerStateCode, "
                        + " IFNULL(c.PerPostalCode,'') as PerPostalCode, IFNULL(c.PerCountryCode,'') as PerCountryCode, IFNULL(c.PerPhoneNumber,'') as PerPhoneNumber, IFNULL(c.PerTelexNumber,'') as PerTelexNumber, IFNULL(c.PerFaxNumber,'') as PerFaxNumber, "
                        + " IFNULL(c.MailAddressLine1,'') as MailAddressLine1, IFNULL(c.MailAddressLine2,'') as MailAddressLine2, IFNULL(c.MailVillage,'') as MailVillage, IFNULL(c.MailBlock, '') as MailBlock, IFNULL(c.MailCityCode, '0') as MailCityCode,"
                        + " IFNULL(c.MailStateCode, '') as MailStateCode, IFNULL(c.MailPostalCode, '') as MailPostalCode, IFNULL(c.MailCountryCode, '') as MailCountryCode, IFNULL(c.MailPhoneNumber, '') as MailPhoneNumber, IFNULL(c.MailTelexNumber, '') as MailTelexNumber, "
                        + " IFNULL(c.MailFaxNumber, '') as MailFaxNumber, IFNULL(c.mobilenumber,'') as mobilenumber, IFNULL(c.EmailID,'') as EmailID, a.accstatus, IFNULL(c.AADHAAR_NO, '') as AADHAAR_NO, ifnull(c.VoterIDNo,'') as VoterIDNo, "
                        + " IFNULL(c.DrivingLicenseNo,'') as DrivingLicenseNo, date_format(ifnull(c.DateOfBirth,'1900-01-01'),'%Y%m%d') as DateOfBirth, IFNULL(c.PassportNo,'') as PassportNo    from " + CbsUtil.getAccMasterTableName(acNature) + "  a, customerid b, cbs_customer_master_detail c "
                        + " where a.acno = b.acno and b.custid = c.customerid and a.acno in ("
                        + " select Distinct aa.acno from (select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                        + " substring(acno,1,2) in (" + brCode + ")   and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype in (" + trnCrType + ") group by acno having(sum(cramt) " + amtQuery + " ) "
                        + " union all select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                        + " substring(acno,1,2) in (" + brCode + ")   and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype in (" + trnDrType + ") group by acno having(sum(dramt) " + amtQuery + " ))aa "
                        + ") " + denominationQuery + " ) mst,"
                        + " (select acno, cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)) as cramt,date_format(ifnull(max(dt),now()),'%d/%m/%Y') as crDt from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and trantype in (" + trnCrType + ") group by acno ) cr, "
                        + " (select acno, cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(18,2)) as dramt,date_format(ifnull(max(dt),now()),'%d/%m/%Y') as drDt from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and trantype in (" + trnDrType + ") group by acno ) dr "
                        + " where mst.acno = cr.acno and mst.acno = dr.acno "
                        + " group by mst.acno  order by mst.acno";
                acTransactionDetailsList = em.createNativeQuery(query).getResultList();

//                System.out.println(query);
                for (int j = 0; j < acTransactionDetailsList.size(); j++) {
                    Vector acTranVect = (Vector) acTransactionDetailsList.get(j);
                    String acNo = acTranVect.get(36).toString();
                    BigDecimal crAmt = new BigDecimal(acTranVect.get(37).toString());
                    BigDecimal drAmt = new BigDecimal(acTranVect.get(38).toString());
                    String crDt = acTranVect.get(39).toString();
                    String drDt = acTranVect.get(40).toString();
                    String trnDt = dmyy.format(ymd.parse(toDt));
                    if (dmyy.parse(crDt).after(dmyy.parse(drDt))) {
                        trnDt = crDt;
                    } else if (dmyy.parse(drDt).after(dmyy.parse(crDt))) {
                        trnDt = drDt;
                    } else {
                        trnDt = drDt;
                    }
                    arfAccLine = arfAccLine + 1;
                    ctrMoreThan1Crore arfAcc = new ctrMoreThan1Crore();
                    /**
                     * ** Customer Information ***
                     */
                    String custName = acTranVect.get(0).toString().toUpperCase();
                    String openingDt = acTranVect.get(1).toString();
                    String operMode = acTranVect.get(2).toString();
                    String riskCategory = acTranVect.get(3).toString();
                    String custId = acTranVect.get(4).toString();
                    String pan = acTranVect.get(5).toString().toUpperCase();
                    String perAddressLine1 = acTranVect.get(6).toString();
                    String perAddressLine2 = acTranVect.get(7).toString();
                    String perVillage = acTranVect.get(8).toString();
                    String perBlock = acTranVect.get(9).toString();
                    String perCityCode = acTranVect.get(10).toString();
                    String perStateCode = acTranVect.get(11).toString();
                    String perPostalCode = acTranVect.get(12).toString();
                    String perCountryCode = acTranVect.get(13).toString();
                    String perPhoneNumber = acTranVect.get(14).toString();
                    String perTelexNumber = acTranVect.get(15).toString();
                    String perFaxNumber = acTranVect.get(16).toString();
                    String mailAddressLine1 = acTranVect.get(17).toString();
                    String mailAddressLine2 = acTranVect.get(18).toString();
                    String mailVillage = acTranVect.get(19).toString();
                    String mailBlock = acTranVect.get(20).toString();
                    String mailCityCode = acTranVect.get(21).toString();
                    String mailStateCode = acTranVect.get(22).toString();
                    String mailPostalCode = acTranVect.get(23).toString();
                    String mailCountryCode = acTranVect.get(24).toString();
                    String mailPhoneNumber = acTranVect.get(25).toString();
                    String mailTelexNumber = acTranVect.get(26).toString();
                    String mailFaxNumber = acTranVect.get(27).toString();
                    String mobileNo = acTranVect.get(28).toString();
                    String eMailId = acTranVect.get(29).toString();
                    String accStatus = acTranVect.get(30).toString();
                    String uinAadharNo = acTranVect.get(31).toString();
                    String voterId = acTranVect.get(32).toString();
                    String drivLicence = acTranVect.get(33).toString();
                    String dob = acTranVect.get(34).toString();
                    String passPortNo = acTranVect.get(35).toString();

                    String acNature1 = common.getAcctNature(acNo.substring(2, 4));

                    /**
                     * ** Cash Transaction Information ***
                     */
                    BigDecimal cashCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(acTranVect.get(37).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(0).toString());
                    BigDecimal cashDrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(acTranVect.get(38).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(1).toString());
                    arfAcc.setsNo(arfAccLine);
                    arfAcc.setReportingEntityName(toDt);
                    arfAcc.setFiureid(fiuId);
                    arfAcc.setAccountNumber(acNo);
                    arfAcc.setAccountHoldersDateofBirth(dob.equalsIgnoreCase("") ? "" : dmyy.format(ymd.parse(dob)));
                    /*  Account Type
                     BS - Savings Account
                     BC - Current Account
                     BR - Cash Credit/Overdraft Account
                     BD - Credit Card Account
                     BP - Prepaid Card Account
                     BL - Loan Account
                     BT - Term Deposit Account
                     BG – Letter of Credit/Bank Guarantee
                     ZZ - Others
                     XX - Not Categorised
                     */
                    if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        arfAcc.setAccountType("BC");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        arfAcc.setAccountType("BR");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        arfAcc.setAccountType("BS");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        arfAcc.setAccountType("BL");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                        arfAcc.setAccountType("BT");
                    } else {
                        arfAcc.setAccountType("ZZ");
                    }

                    List<ArfBrcPojo> arfBrcPojos = ctrStrRemote.getArfBrcData(acNo.substring(0, 2), "STR");
                    arfAcc.setBrachName(arfBrcPojos.get(0).getBranchName());
                    arfAcc.setBranchCity(arfBrcPojos.get(0).getCity() + " - " + arfBrcPojos.get(0).getPinCode());

                    arfAcc.setAccountHolderName(custName);

                    String flag = "Y";
                    if (pan.length() == 10) {
                        if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                            flag = "N";
                        }
                        if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                            flag = "N";
                        }
                        if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                            flag = "N";
                        }
                        if (!flag.equalsIgnoreCase("Y")) {
                            pan = "";
                        }
                    } else {
                        pan = "";
                    }

                    arfAcc.setAccountHoldersPAN(pan);
                    /*Permissible values are:
                     * A - Passport
                     * B - Election ID Card
                     * C - Pan Card
                     * D - ID Card
                     * E - Driving License
                     * F - Account Introducer
                     * G - UIDAI Letter
                     * H - NREGA job card
                     * Z – Others*/
                    if (!voterId.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("B");
                        arfAcc.setIdentificationDocumentNumber(voterId);
                    } else if (!drivLicence.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("E");
                        arfAcc.setIdentificationDocumentNumber(drivLicence);
                    } else if (!passPortNo.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("A");
                        arfAcc.setIdentificationDocumentNumber(passPortNo);
                    } else if (!uinAadharNo.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("G");
                        arfAcc.setIdentificationDocumentNumber(uinAadharNo);
                    } else if (!pan.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("C");
                        arfAcc.setIdentificationDocumentNumber(pan);
                    }

                    arfAcc.setAccountHoldersAddress((mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailPostalCode).trim().length() > 10 ? (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailPostalCode) : (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailPostalCode).concat(" ").concat(mailCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", mailCityCode)));    //No, building//Street, Road//Locality
                    arfAcc.setAccountHoldersCity(mailCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", mailCityCode));                                                              //City/Town, District
                    arfAcc.setAccountHoldersMobile(mobileNo.equalsIgnoreCase("") ? "" : (mobileNo.length() == 10 ? mobileNo : ""));
                    arfAcc.setCumulativeAmountOfCashWithdrawn(cashDrAmt);
                    arfAcc.setDateOfTransaction(trnDt);
                    if (repType.equalsIgnoreCase("9")) {
                        arfAcc.setBalanceInAcno(new BigDecimal(common.getBalanceOnDate(acNo, "20161107")));
                        if (denominationFlag.equalsIgnoreCase("Y")) {
                            List crBalList = em.createNativeQuery("select sum(denomination*denomination_value) from denomination_detail where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and ty = 0 and new_old_flag = 'O' and denomination in (500,1000)").getResultList();
                            if (!crBalList.isEmpty()) {
                                Vector crBalVect = (Vector) crBalList.get(0);
                                arfAcc.setCumulativeAmountOfCashDeposited(new BigDecimal(new DecimalFormat("#").format(new BigDecimal(crBalVect.get(0).toString()).doubleValue())));
                            }
                        } else {
                            arfAcc.setCumulativeAmountOfCashDeposited(cashCrAmt);
                        }

                        String chKyc = txnRemote.kycExpCheck(acNo, toDt);
                        if (chKyc.equalsIgnoreCase("true")) {
                            arfAcc.setAcOpenDuringDt("Y");
                        } else {
                            arfAcc.setAcOpenDuringDt("N");
                        }
                    } else {
                        arfAcc.setBalanceInAcno(new BigDecimal(common.getBalanceOnDate(acNo, CbsUtil.dateAdd(fromDt, -1))));
                        arfAcc.setCumulativeAmountOfCashDeposited(cashCrAmt);
                        arfAcc.setAcOpenDuringDt(ymd.parse(openingDt).before(ymd.parse(fromDt)) ? "N" : "Y");
                    }

                    dataList.add(arfAcc);
                }
            }
            if (dataList.isEmpty()) {
                ctrMoreThan1Crore arfAcc = new ctrMoreThan1Crore();
                arfAcc.setsNo(1);
                arfAcc.setReportingEntityName(toDt);
                arfAcc.setFiureid("");
                arfAcc.setAccountNumber("");
                arfAcc.setAccountHoldersDateofBirth("");
                arfAcc.setAccountType("");
                arfAcc.setBrachName("");
                arfAcc.setBranchCity("");
                arfAcc.setAccountHolderName("");
                arfAcc.setAccountHoldersPAN("");
                arfAcc.setIdentificationDocumentType("");
                arfAcc.setIdentificationDocumentNumber("");
                arfAcc.setAccountHoldersAddress("");    //No, building//Street, Road//Locality
                arfAcc.setAccountHoldersCity("");                                                              //City/Town, District
                arfAcc.setAccountHoldersMobile("");
                arfAcc.setCumulativeAmountOfCashDeposited(new BigDecimal("0"));
                arfAcc.setCumulativeAmountOfCashWithdrawn(new BigDecimal("0"));
                arfAcc.setDateOfTransaction("");
                arfAcc.setAcOpenDuringDt("");
                dataList.add(arfAcc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<ctrMoreThan1Crore> loanDeposit25LakhsOrMoreReport(List acNatureList, String acType, String trnCrType, String trnDrType, BigDecimal repTypeAmt, String fromDt, String toDt, String brCode, String repType, String userName, BigDecimal repToAmt, String amtType, String denominationFlag) throws ApplicationException {
        List<ctrMoreThan1Crore> dataList = new ArrayList<ctrMoreThan1Crore>();
        try {
            Integer arfAccLine = 0, inpLineNo = 0, trnLineNo = 0, lpeLineNo = 0;
            Integer year;
            String ctrParameter, acTypeNot = "''", amtQuery = "", fiuId = "", denominationQuery = "";
            Double ctrLimit = 0d;
            /**
             * *Limit is implemented for CCBL, Because they reported more than
             * 50000 *
             */
            List result = em.createNativeQuery("select ifnull(fiureid,'BANUCXXXXX') from branchmaster b,parameterinfo p, "
                    + " bnkadd c where b.BrnCode = p.BrnCode and b.BrnCode in ( " + brCode
                    + " ) and b.AlphaCode = c.alphacode").getResultList();
            if (!result.isEmpty()) {
                Vector vtr = (Vector) result.get(0);
                fiuId = vtr.get(0).toString();
            }

            if (amtType.equalsIgnoreCase("Between")) {
                amtQuery = " between " + repTypeAmt + " and " + repToAmt;
            } else if (amtType.equalsIgnoreCase("till")) {
                amtQuery = " > " + repTypeAmt;
            }
//            List ctrAppDtList = em.createNativeQuery("select ifnull(code,'20161108') from cbs_parameterinfo where name = 'CTR_1CRORE_APP_DT'").getResultList();
//            if (!ctrAppDtList.isEmpty()) {
//                Vector ctrAppDtVect = (Vector) ctrAppDtList.get(0);
//                fromDt = ctrAppDtVect.get(0).toString();
//            }
            List acTypeNotList = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name = 'CTR_ACCTCODE_NOT'").getResultList();
            if (!acTypeNotList.isEmpty()) {
                Vector acTypeNotVect = (Vector) acTypeNotList.get(0);
                acTypeNot = acTypeNotVect.get(0).toString();
            }

            for (int i = 0; i < acNatureList.size(); i++) {
                String acType1 = "";
                Vector acnatureVect = (Vector) acNatureList.get(i);
                String acNature = acnatureVect.get(0).toString();
                List acTypeList;
                if (repType.equalsIgnoreCase("11")) {
                    acTypeList = common.getAccTypeOnlyCrDbFlag(acnatureVect.get(0).toString());
                } else {
                    acTypeList = common.getAccType(acnatureVect.get(0).toString());
                }
                if (acTypeList.size() > 0) {
                    for (int z = 0; z < acTypeList.size(); z++) {
                        Vector actypevect = (Vector) acTypeList.get(z);
                        if (z == 0) {
                            acType1 = "'" + actypevect.get(0).toString() + "'";
                        } else {
                            acType1 = acType1.concat(",'" + actypevect.get(0).toString() + "'");
                        }
                    }
                }
                acType = acType1;
                List acTransactionDetailsList = null;

                String query = "select mst.custname, mst.openingdt, mst.opermode, mst.OperationalRiskRating, mst.custid, mst.PAN_GIRNumber, "
                        + " mst.PerAddressLine1, mst.PerAddressLine2, mst.PerVillage, mst.PerBlock, mst.PerCityCode, mst.PerStateCode, "
                        + " mst.PerPostalCode, mst.PerCountryCode, mst.PerPhoneNumber, mst.PerTelexNumber, mst.PerFaxNumber, "
                        + " mst.MailAddressLine1, mst.MailAddressLine2, mst.MailVillage, mst.MailBlock, mst.MailCityCode,"
                        + " mst.MailStateCode, mst.MailPostalCode, mst.MailCountryCode, mst.MailPhoneNumber, mst.MailTelexNumber,"
                        + " mst.MailFaxNumber, mst.mobilenumber, mst.EmailID, mst.accstatus, mst.AADHAAR_NO, mst.VoterIDNo, "
                        + " mst.DrivingLicenseNo, mst.DateOfBirth, mst.PassportNo, mst.acno, mst.odlimit from "
                        + " (select a.acno, a.custname, a.openingdt, a.opermode, ifnull(c.OperationalRiskRating,'') as OperationalRiskRating, b.custid, IFNULL(c.PAN_GIRNumber,'') as PAN_GIRNumber, "
                        + " IFNULL(c.PerAddressLine1,'') as PerAddressLine1, IFNULL(c.PerAddressLine2,'') as PerAddressLine2, IFNULL(c.PerVillage,'') as PerVillage, IFNULL(c.PerBlock,'') as  PerBlock, IFNULL(c.PerCityCode,'0') as PerCityCode, IFNULL(c.PerStateCode,'') as PerStateCode, "
                        + " IFNULL(c.PerPostalCode,'') as PerPostalCode, IFNULL(c.PerCountryCode,'') as PerCountryCode, IFNULL(c.PerPhoneNumber,'') as PerPhoneNumber, IFNULL(c.PerTelexNumber,'') as PerTelexNumber, IFNULL(c.PerFaxNumber,'') as PerFaxNumber, "
                        + " IFNULL(c.MailAddressLine1,'') as MailAddressLine1, IFNULL(c.MailAddressLine2,'') as MailAddressLine2, IFNULL(c.MailVillage,'') as MailVillage, IFNULL(c.MailBlock, '') as MailBlock, IFNULL(c.MailCityCode, '0') as MailCityCode,"
                        + " IFNULL(c.MailStateCode, '') as MailStateCode, IFNULL(c.MailPostalCode, '') as MailPostalCode, IFNULL(c.MailCountryCode, '') as MailCountryCode, IFNULL(c.MailPhoneNumber, '') as MailPhoneNumber, IFNULL(c.MailTelexNumber, '') as MailTelexNumber, "
                        + " IFNULL(c.MailFaxNumber, '') as MailFaxNumber, IFNULL(c.mobilenumber,'') as mobilenumber, IFNULL(c.EmailID,'') as EmailID, a.accstatus, IFNULL(c.AADHAAR_NO, '') as AADHAAR_NO, ifnull(c.VoterIDNo,'') as VoterIDNo, "
                        + " IFNULL(c.DrivingLicenseNo,'') as DrivingLicenseNo, date_format(ifnull(c.DateOfBirth,'1900-01-01'),'%Y%m%d') as DateOfBirth, IFNULL(c.PassportNo,'') as PassportNo, ifnull(a.ODLimit,0) as odlimit    from " + CbsUtil.getAccMasterTableName(acNature) + "  a, customerid b, cbs_customer_master_detail c "
                        + " where a.acno = b.acno and b.custid = c.customerid and a.acno in ("
                        + " select Distinct aa.acno from (select acno from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and "
                        + " substring(acno,1,2) in (" + brCode + ")   and substring(acno,3,2) in (" + acType + ")  and substring(acno,3,2) not in (" + acTypeNot + ") and trantype in (" + trnCrType + ") group by acno having(sum(cramt) " + amtQuery + " ) )aa "
                        + ") ) mst "
                        //                        + " (select acno, cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)) as cramt,date_format(ifnull(max(dt),now()),'%d/%m/%Y') as crDt from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' and trantype in (" + trnCrType + ") group by acno ) cr, "
                        //                        + " (select acno, cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(18,2)) as totalCrAmt,date_format(ifnull(max(dt),now()),'%d/%m/%Y') as totalCrDt from " + CbsUtil.getReconTableName(acNature) + " where dt between '" + fromDt + "' and '" + toDt + "' group by acno ) dr "
                        //                        + " where mst.acno = dr.acno "
                        + " group by mst.acno  order by mst.acno";
                acTransactionDetailsList = em.createNativeQuery(query).getResultList();

//                System.out.println(query);
                for (int j = 0; j < acTransactionDetailsList.size(); j++) {
                    Vector acTranVect = (Vector) acTransactionDetailsList.get(j);
                    String acNo = acTranVect.get(36).toString();
                    double odLimit = Double.parseDouble(acTranVect.get(37).toString());
                    String additionFlag = "Y";
                    ctrMoreThan1Crore arfAcc = new ctrMoreThan1Crore();
                    /**
                     * ** Customer Information ***
                     */
                    String custName = acTranVect.get(0).toString().toUpperCase();
                    String openingDt = acTranVect.get(1).toString();
                    String operMode = acTranVect.get(2).toString();
                    String riskCategory = acTranVect.get(3).toString();
                    String custId = acTranVect.get(4).toString();
                    String pan = acTranVect.get(5).toString().toUpperCase();
                    String perAddressLine1 = acTranVect.get(6).toString();
                    String perAddressLine2 = acTranVect.get(7).toString();
                    String perVillage = acTranVect.get(8).toString();
                    String perBlock = acTranVect.get(9).toString();
                    String perCityCode = acTranVect.get(10).toString();
                    String perStateCode = acTranVect.get(11).toString();
                    String perPostalCode = acTranVect.get(12).toString();
                    String perCountryCode = acTranVect.get(13).toString();
                    String perPhoneNumber = acTranVect.get(14).toString();
                    String perTelexNumber = acTranVect.get(15).toString();
                    String perFaxNumber = acTranVect.get(16).toString();
                    String mailAddressLine1 = acTranVect.get(17).toString();
                    String mailAddressLine2 = acTranVect.get(18).toString();
                    String mailVillage = acTranVect.get(19).toString();
                    String mailBlock = acTranVect.get(20).toString();
                    String mailCityCode = acTranVect.get(21).toString();
                    String mailStateCode = acTranVect.get(22).toString();
                    String mailPostalCode = acTranVect.get(23).toString();
                    String mailCountryCode = acTranVect.get(24).toString();
                    String mailPhoneNumber = acTranVect.get(25).toString();
                    String mailTelexNumber = acTranVect.get(26).toString();
                    String mailFaxNumber = acTranVect.get(27).toString();
                    String mobileNo = acTranVect.get(28).toString();
                    String eMailId = acTranVect.get(29).toString();
                    String accStatus = acTranVect.get(30).toString();
                    String uinAadharNo = acTranVect.get(31).toString();
                    String voterId = acTranVect.get(32).toString();
                    String drivLicence = acTranVect.get(33).toString();
                    String dob = acTranVect.get(34).toString();
                    String passPortNo = acTranVect.get(35).toString();

                    String acNature1 = common.getAcctNature(acNo.substring(2, 4));

                    /**
                     * ** Cash Transaction Information ***
                     */
//                    BigDecimal cashCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(acTranVect.get(37).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(0).toString());
//                    BigDecimal totalCrAmt = new BigDecimal(new DecimalFormat("#").format(new BigDecimal(acTranVect.get(37).toString()).doubleValue()));//new BigDecimal(cumCashTranVect.get(1).toString());
                    BigDecimal cashCrAmt = new BigDecimal("0");
                    BigDecimal totalCrAmt = new BigDecimal("0");

                    List acNoCashCrList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype in (0) ").getResultList();
                    if (!acNoCashCrList.isEmpty()) {
                        Vector acNoCashCrVect = (Vector) acNoCashCrList.get(0);
                        cashCrAmt = new BigDecimal(acNoCashCrVect.get(0).toString());
                    } else {
                        cashCrAmt = new BigDecimal("0");
                    }

                    List acNoTotalCrList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from " + CbsUtil.getReconTableName(acNature) + " where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' ").getResultList();
                    if (!acNoTotalCrList.isEmpty()) {
                        Vector acNoTotalCashCrVect = (Vector) acNoTotalCrList.get(0);
                        totalCrAmt = new BigDecimal(acNoTotalCashCrVect.get(0).toString());
                    } else {
                        totalCrAmt = new BigDecimal("0");
                    }

                    if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        List sanctionLimitDtList = em.createNativeQuery("select SanctionLimitDt, acLimit from loan_oldinterest "
                                + "where acno =  '" + acNo + "' and txnid = "
                                + "(select min(TXNID) from loan_oldinterest where acno =  '" + acNo + "' and enterdate>'" + toDt + "' )").getResultList();
                        if (!sanctionLimitDtList.isEmpty()) {
                            Vector vist = (Vector) sanctionLimitDtList.get(0);
                            if (Double.parseDouble(vist.get(1).toString()) == 0) {
                                additionFlag = "N";
                            }
                        } else {
                            if (odLimit == 0) {
                                additionFlag = "N";
                            }
                        }
                    }

                    arfAcc.setReportingEntityName(toDt);
                    arfAcc.setFiureid(fiuId);
                    arfAcc.setAccountNumber(acNo);
                    arfAcc.setAccountHoldersDateofBirth(dob.equalsIgnoreCase("") ? "" : dmyy.format(ymd.parse(dob)));
                    /*  Account Type
                     BS - Savings Account
                     BC - Current Account
                     BR - Cash Credit/Overdraft Account
                     BD - Credit Card Account
                     BP - Prepaid Card Account
                     BL - Loan Account
                     BT - Term Deposit Account
                     BG – Letter of Credit/Bank Guarantee
                     ZZ - Others
                     XX - Not Categorised
                     */
                    if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        arfAcc.setAccountType("BC");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        arfAcc.setAccountType("BR");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        arfAcc.setAccountType("BS");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        arfAcc.setAccountType("BL");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                        arfAcc.setAccountType("BT");
                    } else {
                        arfAcc.setAccountType("ZZ");
                    }

                    List<ArfBrcPojo> arfBrcPojos = ctrStrRemote.getArfBrcData(acNo.substring(0, 2), "STR");
                    arfAcc.setBrachName(arfBrcPojos.get(0).getBranchName());
                    arfAcc.setBranchCity(arfBrcPojos.get(0).getCity());
                    arfAcc.setBrachDistrict(arfBrcPojos.get(0).getCity());
                    arfAcc.setBrachPinCode(arfBrcPojos.get(0).getPinCode());
                    arfAcc.setBrachState(arfBrcPojos.get(0).getStateCode());

                    arfAcc.setAccountHolderName(custName);

                    String flag = "Y";
                    if (pan.length() == 10) {
                        if (!ParseFileUtil.isAlphabet(pan.substring(0, 5))) {
                            flag = "N";
                        }
                        if (!ParseFileUtil.isNumeric(pan.substring(5, 9))) {
                            flag = "N";
                        }
                        if (!ParseFileUtil.isAlphabet(pan.substring(9))) {
                            flag = "N";
                        }
                        if (!flag.equalsIgnoreCase("Y")) {
                            pan = "";
                        }
                    } else {
                        pan = "";
                    }

                    arfAcc.setAccountHoldersPAN(pan);
                    /*Permissible values are:
                     * A - Passport
                     * B - Election ID Card
                     * C - Pan Card
                     * D - ID Card
                     * E - Driving License
                     * F - Account Introducer
                     * G - UIDAI Letter
                     * H - NREGA job card
                     * Z – Others*/
                    if (!voterId.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("B");
                        arfAcc.setIdentificationDocumentNumber(voterId);
                    } else if (!drivLicence.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("E");
                        arfAcc.setIdentificationDocumentNumber(drivLicence);
                    } else if (!passPortNo.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("A");
                        arfAcc.setIdentificationDocumentNumber(passPortNo);
                    } else if (!uinAadharNo.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("G");
                        arfAcc.setIdentificationDocumentNumber(uinAadharNo);
                    } else if (!pan.equalsIgnoreCase("")) {
                        arfAcc.setIdentificationDocumentType("C");
                        arfAcc.setIdentificationDocumentNumber(pan);
                    }

                    arfAcc.setAccountHoldersAddress((mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailPostalCode).trim().length() > 10 ? (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailPostalCode) : (mailAddressLine1.concat(" ").concat(mailAddressLine2).concat(" ").concat(mailBlock)).concat(" ").concat(mailPostalCode).concat(" ").concat(mailCityCode.equalsIgnoreCase("0") ? " XXXXXX" : common.getRefRecDesc("001", mailCityCode)));    //No, building//Street, Road//Locality
                    arfAcc.setAccountHoldersCity(mailCityCode.equalsIgnoreCase("0") ? "" : common.getRefRecDesc("001", mailCityCode));                                                              //City/Town, District
                    arfAcc.setAccountHoldersMobile(mobileNo.equalsIgnoreCase("") ? "" : (mobileNo.length() == 10 ? mobileNo : ""));
                    arfAcc.setCumulativeAmountOfCashDeposited(cashCrAmt);
                    arfAcc.setCumulativeAmountOfCashWithdrawn(totalCrAmt.subtract(cashCrAmt));
                    arfAcc.setBalanceInAcno(totalCrAmt);
                    arfAcc.setDateOfTransaction("");
                    arfAcc.setAcOpenDuringDt(ymd.parse(openingDt).before(ymd.parse(fromDt)) ? "N" : "Y");
                    if (additionFlag.equalsIgnoreCase("Y")) {
                        arfAccLine = arfAccLine + 1;
                        arfAcc.setsNo(arfAccLine);
                        dataList.add(arfAcc);
                    }
                }
            }
            if (dataList.isEmpty()) {
                ctrMoreThan1Crore arfAcc = new ctrMoreThan1Crore();
                arfAcc.setsNo(1);
                arfAcc.setReportingEntityName(toDt);
                arfAcc.setFiureid("");
                arfAcc.setAccountNumber("");
                arfAcc.setAccountHoldersDateofBirth("");
                arfAcc.setAccountType("");
                arfAcc.setBrachName("");
                arfAcc.setBranchCity("");
                arfAcc.setBrachDistrict("");
                arfAcc.setBrachPinCode("");
                arfAcc.setBrachState("");
                arfAcc.setAccountHolderName("");
                arfAcc.setAccountHoldersPAN("");
                arfAcc.setIdentificationDocumentType("");
                arfAcc.setIdentificationDocumentNumber("");
                arfAcc.setAccountHoldersAddress("");    //No, building//Street, Road//Locality
                arfAcc.setAccountHoldersCity("");                                                              //City/Town, District
                arfAcc.setAccountHoldersMobile("");
                arfAcc.setCumulativeAmountOfCashDeposited(new BigDecimal("0"));
                arfAcc.setCumulativeAmountOfCashWithdrawn(new BigDecimal("0"));
                arfAcc.setBalanceInAcno(new BigDecimal("0"));
                arfAcc.setDateOfTransaction("");
                arfAcc.setAcOpenDuringDt("");
                dataList.add(arfAcc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<JanDhanAcnoInfoPojo> getJanDhanAcnoInfoData(String RepType, String BrnCode, String actType, String fromDt, String toDt) throws ApplicationException {
        List<JanDhanAcnoInfoPojo> dataList = new ArrayList<JanDhanAcnoInfoPojo>();
        try {
            String prevTodt = CbsUtil.dateAdd(toDt, -1);
            String acType = ftsPosting.getCodeFromCbsParameterInfo("JAN_DHAN_ACTYPE");
            String brcode = "";
            if (BrnCode.equalsIgnoreCase("0A")) {
                brcode = "";
            } else {
                brcode = " curbrcode = '" + BrnCode + "' and ";
            }
            List result = em.createNativeQuery("select a.acno4,b.crAmt5,c.crAmt6,d.crAmt7,e.crAmt8,f.kycAcno9,g.kycAcno10 from("
                    + "(select count(acno) acno4 from accountmaster where " + brcode + "  accttype in(" + acType + ") and OpeningDt < '" + toDt + "' and accStatus <> 9)a,"
                    + "(select ifnull(sum(CrAmt),0) crAmt5 from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt < '" + toDt + "' and accStatus <> 9) and ty = 0 and trantype = 0 and dt <= '" + prevTodt + "')b, "
                    + "(select ifnull(sum(CrAmt),0) crAmt6 from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt = '" + toDt + "' and accStatus <> 9)  and ty = 0 and trantype = 0 and dt = '" + toDt + "')c, "
                    + "(select ifnull(sum(CrAmt),0) crAmt7 from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt < '" + toDt + "' and accStatus <> 9)  and ty = 0 and trantype = 0 and dt = '" + toDt + "')d,"
                    + "(select ifnull(sum(CrAmt),0) crAmt8 from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + toDt + "' and accStatus <> 9) and ty = 0 and trantype = 0 and dt <= '" + toDt + "')e,"
                    + "(select count(CustUpdationDate) kycAcno9 from cbs_customer_master_detail where customerid in(select custid from customerid where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + toDt + "' and accStatus <> 9))and CustUpdationDate = '" + toDt + "')f,"
                    + "(select count(CustUpdationDate) kycAcno10 from cbs_customer_master_detail where customerid in(select custid from customerid where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + toDt + "' and accStatus <> 9))and CustUpdationDate <= '" + toDt + "')g )").getResultList();

            if (!result.isEmpty()) {
                JanDhanAcnoInfoPojo pojo = new JanDhanAcnoInfoPojo();
                Vector janVector = (Vector) result.get(0);
                pojo.setsNo(1);
                pojo.setReportingEntityName(toDt);
                pojo.setfIuReid("");
                pojo.setNoOfAcnoOnPrevDay(janVector.get(0).toString());//4
                pojo.setTotalCashOnPrevDays(new BigDecimal(janVector.get(1).toString()));//5
                pojo.setDepositDuringdDay(new BigDecimal(janVector.get(2).toString()));//6
                pojo.setTotalAmountDuringdDay(new BigDecimal(janVector.get(3).toString()));//7
                pojo.setTotalCashOnEndOfDay(new BigDecimal(janVector.get(4).toString()));//8
                pojo.setKycUpdateDuringday(janVector.get(5).toString());//9
                pojo.setKycUpdateTillday(janVector.get(6).toString());//10
                dataList.add(pojo);
            }

            if (dataList.isEmpty()) {
                JanDhanAcnoInfoPojo pojo = new JanDhanAcnoInfoPojo();
                pojo.setsNo(1);
                pojo.setReportingEntityName(toDt);
                pojo.setfIuReid("");
                pojo.setNoOfAcnoOnPrevDay("");
                pojo.setTotalCashOnPrevDays(new BigDecimal("0"));
                pojo.setDepositDuringdDay(new BigDecimal("0"));
                pojo.setTotalAmountDuringdDay(new BigDecimal("0"));
                pojo.setTotalAmountDuringdDay(new BigDecimal("0"));
                pojo.setKycUpdateDuringday("0");
                pojo.setKycUpdateTillday("0");
                dataList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<jdHavingDepositPojo> getJdHavingData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount) throws ApplicationException {
        List<jdHavingDepositPojo> dataList = new ArrayList<jdHavingDepositPojo>();
        try {

            String acType = ftsPosting.getCodeFromCbsParameterInfo("JAN_DHAN_ACTYPE");

            String brcode = "";
            if (BrnCode.equalsIgnoreCase("0A")) {
                brcode = "";
            } else {
                brcode = "curbrcode = '" + BrnCode + "' and";
            }

            List result = em.createNativeQuery("select a.acno4,b.crAmt5,c.drAmt6,d.crAmt7,e.drAmt8 from ("
                    + "(select count(*) acno4 from accountmaster a,(select distinct acno from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + fromDt + "' and accStatus <> 9)and dt <= '" + fromDt + "' group by acno having cast(ifnull(sum(cramt),0) as decimal(25,2)) < 1000 ) b where a.acno = b.acno)a,"
                    + "(select cast(ifnull(sum(r.cramt),0) as decimal(25,2)) crAmt5 from recon r ,(select distinct acno from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + fromDt + "' and accStatus <> 9)and dt <= '" + fromDt + "' group by acno having cast(ifnull(sum(cramt),0) as decimal(25,2)) < " + amount + " ) a where r.acno=a.acno and r.dt = '" + toDt + "' and TranType = 0 and ty = 0)b,"
                    + "(select cast(ifnull(sum(r.dramt),0) as decimal(25,2)) drAmt6 from recon r ,(select distinct acno from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + fromDt + "' and accStatus <> 9)and dt <= '" + fromDt + "' group by acno having cast(ifnull(sum(cramt),0) as decimal(25,2)) < " + amount + " ) a where r.acno=a.acno and r.dt = '" + toDt + "' and TranType = 0 and ty = 1)c,"
                    + "(select cast(ifnull(sum(r.cramt),0) as decimal(25,2)) crAmt7 from recon r ,(select distinct acno from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + fromDt + "' and accStatus <> 9)and dt <= '" + fromDt + "' group by acno having cast(ifnull(sum(cramt),0) as decimal(25,2)) < " + amount + " ) a where r.acno=a.acno and r.dt between '" + fromDt + "' and '" + toDt + "' and TranType = 0 and ty = 0)d,"
                    + "(select cast(ifnull(sum(r.dramt),0) as decimal(25,2)) drAmt8 from recon r ,(select distinct acno from recon where acno in(select acno from accountmaster where " + brcode + " accttype in(" + acType + ") and OpeningDt <= '" + fromDt + "' and accStatus <> 9)and dt <= '" + fromDt + "' group by acno having cast(ifnull(sum(cramt),0) as decimal(25,2)) < " + amount + " ) a where r.acno=a.acno and r.dt between '" + fromDt + "' and '" + toDt + "' and TranType = 0 and ty = 1)e )").getResultList();

            if (!result.isEmpty()) {
                jdHavingDepositPojo pojo = new jdHavingDepositPojo();
                Vector jdVector = (Vector) result.get(0);
                pojo.setsNo(1);
                pojo.setReportingEntityName(toDt);
                pojo.setfIuReid("");
                pojo.setNoOfAcnoLessThan1000(jdVector.get(0).toString());
                pojo.setTotalAmtCashDuringDays(new BigDecimal(jdVector.get(1).toString()));
                pojo.setWithdrawDuringDays(new BigDecimal(jdVector.get(2).toString()));
                pojo.setCumulativeAmtDeposit(new BigDecimal(jdVector.get(3).toString()));
                pojo.setCumulativeWithdraw(new BigDecimal(jdVector.get(4).toString()));

                dataList.add(pojo);
            }
            if (dataList.isEmpty()) {
                jdHavingDepositPojo pojo = new jdHavingDepositPojo();
                pojo.setsNo(1);
                pojo.setReportingEntityName(toDt);
                pojo.setfIuReid("");
                pojo.setNoOfAcnoLessThan1000("");
                pojo.setTotalAmtCashDuringDays(new BigDecimal("0"));
                pojo.setWithdrawDuringDays(new BigDecimal("0"));
                pojo.setCumulativeAmtDeposit(new BigDecimal("0"));
                pojo.setCumulativeWithdraw(new BigDecimal("0"));

                dataList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<InoperativeToOperativePoJo> dormantToOperativeReportDetails(String brCode, String repType, String acNature, String acType, String dateType, String fromDt, String toDt, String txnFromDt, String txnToDt) throws ApplicationException {
        List<InoperativeToOperativePoJo> dataList = new ArrayList<InoperativeToOperativePoJo>();
        try {
            Integer noOfDorAcPreDate = 0, noOfDorAcBetDate = 0;
            BigDecimal noOfDorAcBalPreDate = new BigDecimal("0"), crBetDate = new BigDecimal("0"), drBetDate = new BigDecimal("0");
            String date = "";
            if (dateType.equalsIgnoreCase("1")) {
                date = "<='" + toDt + "'";
            } else {
                date = "between '" + fromDt + "' and '" + toDt + "'";
            }
            String brCodeQuery = "";
            if (brCode.equalsIgnoreCase("0A")) {
                brCodeQuery = "";
            } else {
                brCodeQuery = "and  substring(ast.acno,1,2) in ('" + brCode + "')";
            }
            String acTypeQuery = "";
            if (acNature.equalsIgnoreCase("ALL")) {
                acTypeQuery = " and substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) ";
            } else {
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeQuery = " and substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('" + acNature + "')) ";
                } else {
                    acTypeQuery = " and substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctCode in ('" + acType + "')) ";
                }
            }
            String query = "";
            if (repType.equalsIgnoreCase("1")) {
                query = "select a.acno as acno, ac.custname, c.sno, atm.acctnature, (cb.description), npa.npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y'), recon.bal as bal "
                        + " from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt " + date + " and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c , "
                        + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from recon where dt <='" + toDt + "' group by acno) recon, "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and  ac.acno = recon.acno  "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + toDt + "' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')  " + acTypeQuery + "  "
                        + " union all "
                        + "select a.acno as acno, ac.custname, c.sno, atm.acctnature, (cb.description), npa.npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y'), recon.bal as bal "
                        + " from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  " + date + " and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='" + toDt + "' group by acno) c , "
                        + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from ca_recon where dt <='" + toDt + "' group by acno) recon, "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and  ac.acno = recon.acno  "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + toDt + "' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + toDt + "')  " + acTypeQuery + " ";
            } else if (repType.equalsIgnoreCase("2")) {
                if (dateType.equalsIgnoreCase("1")) {
                    fromDt = CbsUtil.dateAdd(toDt, 1);
                    toDt = txnToDt;
                }
                query = "select bet.acno, recon.bal from "
                        + " (select acno from accountstatus  where acno in ("
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  " + acTypeQuery + "  "
                        + " ) and spflag =1 and dt between '" + fromDt + "' and '" + toDt + "'  order by acno ) bet, "
                        + " (select acno,cast(ifnull(sum(ifnull(cramt,0)- ifnull(dramt,0)),0) as decimal(25,2)) as bal from recon where dt<='" + toDt + "' group by acno) recon "
                        + " where bet.acno = recon.acno  "
                        + " union all "
                        + " select bet.acno, recon.bal from "
                        + " (select acno from accountstatus  where acno in ( "
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  " + acTypeQuery + "  "
                        + " ) and spflag =1 and dt between '" + fromDt + "' and '" + toDt + "'  order by acno ) bet, "
                        + " (select acno,cast(ifnull(sum(ifnull(cramt,0)- ifnull(dramt,0)),0) as decimal(25,2)) as bal from ca_recon where dt<='" + toDt + "' group by acno) recon "
                        + " where bet.acno = recon.acno ";
            }
            List acNoList = em.createNativeQuery(query).getResultList();
            if (repType.equalsIgnoreCase("1")) {
                for (int k = 0; k < acNoList.size(); k++) {
                    Vector vect = (Vector) acNoList.get(k);
                    InoperativeToOperativePoJo pojo = new InoperativeToOperativePoJo();
                    pojo.setCustName(vect.get(1).toString());
                    pojo.setAcNo(vect.get(0).toString());
                    pojo.setAcctnature(vect.get(3).toString());
                    pojo.setDescription(vect.get(4).toString());
                    pojo.setBalance(new BigDecimal(vect.get(7).toString()));
                    pojo.setDate(vect.get(6).toString());
                    dataList.add(pojo);
                }
            } else if (repType.equalsIgnoreCase("2")) {
                String txnFromDate = "";
                String txnToDate = "";
                if (!acNoList.isEmpty()) {
                    if (dateType.equalsIgnoreCase("1")) {
                        txnFromDate = txnFromDt;
                        txnToDate = txnToDt;
                    } else if (dateType.equalsIgnoreCase("2")) {
                        txnFromDate = fromDt;
                        txnToDate = toDt;
                    }
                    String queryCrDr = "select bet.acno, reconCr.balCr from "
                            + " (select acno from accountstatus  where acno in ("
                            + " select a.acno from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                            + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                            + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  " + acTypeQuery + " ) "
                            + " and spflag =1 and dt between '" + fromDt + "' and '" + toDt + "'  order by acno ) bet, "
                            + " (select acno,cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)) as balCr from recon where dt between '" + txnFromDate + "' and '" + txnToDate + "' group by acno) reconCr "
                            + " where bet.acno = reconCr.acno  "
                            + " union all "
                            + " select bet.acno, reconCr.balCr from "
                            + " (select acno from accountstatus  where acno in ( "
                            + " select a.acno from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno " + brCodeQuery + "  ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                            + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                            + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "') " + acTypeQuery + " ) "
                            + " and spflag =1 and dt between '" + fromDt + "' and '" + toDt + "'  order by acno ) bet, "
                            + " (select acno,cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)) as balCr from ca_recon where dt between  '" + txnFromDate + "' and '" + txnToDate + "'  group by acno) reconCr "
                            + " where bet.acno = reconCr.acno ";
                    List acNoCrList = em.createNativeQuery(queryCrDr).getResultList();
                    queryCrDr = "select bet.acno, reconDr.balDr from "
                            + " (select acno from accountstatus  where acno in ("
                            + " select a.acno from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                            + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                            + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  " + acTypeQuery + " ) "
                            + " and spflag =1 and dt between '" + fromDt + "' and '" + toDt + "'  order by acno ) bet, "
                            + " (select acno,cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as balDr from recon where dt between  '" + txnFromDate + "' and '" + txnToDate + "'  group by acno) reconDr "
                            + " where bet.acno = reconDr.acno "
                            + " union all "
                            + " select bet.acno, reconDr.balDr from "
                            + " (select acno from accountstatus  where acno in ( "
                            + " select a.acno from accountstatus a, "
                            + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                            + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                            + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                            + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                            + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  " + brCodeQuery + " ) npa, "
                            + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                            + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                            + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                            + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                            + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  " + acTypeQuery + " ) "
                            + " and spflag =1 and dt between '" + fromDt + "' and '" + toDt + "'  order by acno ) bet, "
                            + " (select acno,cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as balDr from ca_recon where dt between  '" + txnFromDate + "' and '" + txnToDate + "'  group by acno) reconDr "
                            + " where bet.acno = reconDr.acno ";
                    List acNoDrList = em.createNativeQuery(queryCrDr).getResultList();
                    for (int k = 0; k < acNoList.size(); k++) {
                        Vector vect = (Vector) acNoList.get(k);
                        String acno = vect.get(0).toString();
                        InoperativeToOperativePoJo pojo = new InoperativeToOperativePoJo();
                        pojo.setAcNo(acno);
                        pojo.setCustName(ftsBean.getCustName(acno));
                        pojo.setAcctnature(ftsBean.getAccountNature(acno));
                        List<String> acStatus = ftsBean.getAccountPresentStatus(acno);
                        if (!acStatus.isEmpty()) {
                            String acVect = acStatus.get(1);
                            pojo.setDescription(acVect);
                        }
                        BigDecimal crAmt = new BigDecimal("0");
                        BigDecimal drAmt = new BigDecimal("0");
                        if (!acNoCrList.isEmpty()) {
                            for (int l = 0; l < acNoCrList.size(); l++) {
                                Vector crVect = (Vector) acNoCrList.get(l);
                                if (acno.equalsIgnoreCase(crVect.get(0).toString())) {
                                    if (!crVect.get(1).toString().equalsIgnoreCase(null)) {
                                        crAmt = new BigDecimal(crVect.get(1).toString());
                                    }
                                }
                            }
                        }
                        pojo.setCrAmt(crAmt);
                        if (!acNoDrList.isEmpty()) {
                            for (int l = 0; l < acNoDrList.size(); l++) {
                                Vector drVect = (Vector) acNoDrList.get(l);
                                if (acno.equalsIgnoreCase(drVect.get(0).toString())) {
                                    if (!drVect.get(1).toString().equalsIgnoreCase(null)) {
                                        drAmt = new BigDecimal(drVect.get(1).toString());
                                    }
                                }
                            }
                        }
                        pojo.setDrAmt(drAmt);
                        pojo.setBalance(new BigDecimal(vect.get(1).toString()));
                        dataList.add(pojo);
                    }
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<FiuDormantToOperative> dormantToOperativeReport(String toDt, String brCode) throws ApplicationException {
        List<FiuDormantToOperative> dataList = new ArrayList<FiuDormantToOperative>();
        try {
            Integer noOfDorAcPreDate = 0, noOfDorAcBetDate = 0;
            BigDecimal noOfDorAcBalPreDate = new BigDecimal("0"), crBetDate = new BigDecimal("0"), drBetDate = new BigDecimal("0");

            String query = "select count(total.acno), sum(total.bal) from "
                    + "(select a.acno as acno, ac.custname, c.sno, atm.acctnature, (cb.description), npa.npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y'), recon.bal as bal "
                    + " from accountstatus a, "
                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                    + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from recon where dt <='20161107' group by acno) recon, "
                    + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                    + " and  ac.acno = recon.acno  "
                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                    + " union all "
                    + "select a.acno as acno, ac.custname, c.sno, atm.acctnature, (cb.description), npa.npaSpFlag, date_format(npa.npaEffDt,'%d/%m/%Y'), recon.bal as bal "
                    + " from accountstatus a, "
                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                    + " (select acno,cast(sum(cramt-dramt) as decimal(25,2)) as bal from ca_recon where dt <='20161107' group by acno) recon, "
                    + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                    + " and  ac.acno = recon.acno  "
                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                    + "   ) total ";

            List acNoList = em.createNativeQuery(query).getResultList();
            if (!acNoList.isEmpty()) {
                for (int i = 0; i < acNoList.size(); i++) {
                    Vector acNoVect = (Vector) acNoList.get(i);
                    noOfDorAcPreDate = noOfDorAcPreDate + Integer.parseInt(acNoVect.get(0).toString());
                    noOfDorAcBalPreDate = noOfDorAcBalPreDate.add(new BigDecimal(acNoVect.get(1).toString()));
                }

                String queryCrDr = "select count(bet.acno) from "
                        + " (select acno from accountstatus  where acno in ("
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                        + " ) and spflag =1 and dt between '20161108' and '" + toDt + "'  order by acno ) bet ";
                List acNoDrCrList = em.createNativeQuery(queryCrDr).getResultList();

                if (!acNoDrCrList.isEmpty()) {
                    for (int j = 0; j < acNoDrCrList.size(); j++) {
                        Vector acNoDrCrVect = (Vector) acNoDrCrList.get(j);
                        noOfDorAcBetDate = noOfDorAcBetDate + Integer.parseInt(acNoDrCrVect.get(0).toString());
                    }
                }

                queryCrDr = "select count(acno), ifnull(sum(balCr),0) from "
                        + "(select bet.acno, reconCr.balCr from "
                        + " (select acno from accountstatus  where acno in ("
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                        + " ) and spflag =1 and dt between '20161108' and '" + toDt + "'  order by acno ) bet, "
                        + " (select acno,cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)) as balCr from recon where dt between '20161108' and '" + toDt + "' group by acno) reconCr "
                        + " where bet.acno = reconCr.acno  "
                        + " union all "
                        + " select bet.acno, reconCr.balCr from "
                        + " (select acno from accountstatus  where acno in ( "
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                        + " ) and spflag =1 and dt between '20161108' and '" + toDt + "'  order by acno ) bet, "
                        + " (select acno,cast(ifnull(sum(ifnull(cramt,0)),0) as decimal(25,2)) as balCr from ca_recon where dt between '20161108' and '" + toDt + "' group by acno) reconCr "
                        + " where bet.acno = reconCr.acno  ) total ";
                acNoDrCrList = em.createNativeQuery(queryCrDr).getResultList();

                if (!acNoDrCrList.isEmpty()) {
                    for (int j = 0; j < acNoDrCrList.size(); j++) {
                        Vector acNoDrCrVect = (Vector) acNoDrCrList.get(j);
//                        noOfDorAcBetDate = noOfDorAcBetDate + Integer.parseInt(acNoDrCrVect.get(0).toString());
                        crBetDate = crBetDate.add(new BigDecimal(acNoDrCrVect.get(1).toString()));
                    }
                }

                queryCrDr = "select count(acno), ifnull(sum(balDr),0) from "
                        + " (select bet.acno, reconDr.balDr from "
                        + " (select acno from accountstatus  where acno in ("
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                        + " ) and spflag =1 and dt between '20161108' and '" + toDt + "'  order by acno ) bet, "
                        + " (select acno,cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as balDr from recon where dt between '20161109' and '" + toDt + "' group by acno) reconDr "
                        + " where bet.acno = reconDr.acno "
                        + " union all "
                        + " select bet.acno, reconDr.balDr from "
                        + " (select acno from accountstatus  where acno in ( "
                        + " select a.acno from accountstatus a, "
                        + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                        + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                        + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '20161107' and SPFLAG IN (2)  group by acno) b "
                        + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                        + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + brCode + ")) npa, "
                        + " (select acno,max(spno) as sno from accountstatus where effdt <='20161107' group by acno) c , "
                        + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                        + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                        + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='20161107' and "
                        + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '20161107')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                        + " ) and spflag =1 and dt between '20161108' and '" + toDt + "'  order by acno ) bet, "
                        + " (select acno,cast(ifnull(sum(ifnull(dramt,0)),0) as decimal(25,2)) as balDr from ca_recon where dt between '20161109' and '" + toDt + "' group by acno) reconDr "
                        + " where bet.acno = reconDr.acno) total ";
                acNoDrCrList = em.createNativeQuery(queryCrDr).getResultList();

                if (!acNoDrCrList.isEmpty()) {
                    for (int j = 0; j < acNoDrCrList.size(); j++) {
                        Vector acNoDrCrVect = (Vector) acNoDrCrList.get(j);
//                        noOfDorAcBetDate = noOfDorAcBetDate + Integer.parseInt(acNoDrCrVect.get(0).toString());
                        drBetDate = drBetDate.add(new BigDecimal(acNoDrCrVect.get(1).toString()));
                    }
                }
            }

            FiuDormantToOperative pojo = new FiuDormantToOperative();
            pojo.setDate(yymmdd.format(ymd.parse(toDt)));
            pojo.setNameOfReportingEntity(toDt);
            pojo.setFiuReId("");
            pojo.setNoOfDormantAccountPrevDay(noOfDorAcPreDate);
            pojo.setBalanceOnPrevDay(noOfDorAcBalPreDate);
            pojo.setNoOfDormantAcReactivatedBetDate(noOfDorAcBetDate);
            pojo.setAmountDepositedBetDate(crBetDate);
            pojo.setAmountWithdrawnBetDate(drBetDate);
            dataList.add(pojo);

            if (dataList.isEmpty()) {
                pojo = new FiuDormantToOperative();
                pojo.setDate(yymmdd.format(ymd.parse(toDt)));
                pojo.setNameOfReportingEntity(toDt);
                pojo.setFiuReId("");
                pojo.setNoOfDormantAccountPrevDay(noOfDorAcPreDate);
                pojo.setBalanceOnPrevDay(noOfDorAcBalPreDate);
                pojo.setNoOfDormantAcReactivatedBetDate(noOfDorAcBetDate);
                pojo.setAmountDepositedBetDate(crBetDate);
                pojo.setAmountWithdrawnBetDate(drBetDate);
                dataList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<DenominationDetailsPojo> denominationDetailsReport(String BrnCode, String toDt, String userId) throws ApplicationException {
        List<DenominationDetailsPojo> dataList = new ArrayList<DenominationDetailsPojo>();
        try {
            String brcode = "", groupByBranch = "", typeOfReportQuery = "", userIdWise = "f";
            if (BrnCode.equalsIgnoreCase("0A")) {
                brcode = " ";
                groupByBranch = " ";
            } else {
                brcode = " and brncode = '" + BrnCode + "' ";
                groupByBranch = " brncode, ";
            }
            if (userId.equalsIgnoreCase("A")) {
                userIdWise = "";
            } else {
                userIdWise = "and enterby = '" + userId + "'";
            }
            Integer noOfClosingNew = 0, noOfClosingOld = 0, noOfOpeningNew = 0, noOfOpeningOld = 0,
                    noOfExchangeCrOld = 0, noOfExchangeCrNew = 0, noOfExchangeDrOld = 0, noOfExchangeDrNew = 0;
            BigDecimal closingBalNew = new BigDecimal("0"), closingBalOld = new BigDecimal("0"), openingBalNew = new BigDecimal("0"), openingBalOld = new BigDecimal("0"),
                    exchangeCrBalNew = new BigDecimal("0"), exchangeCrBalOld = new BigDecimal("0"), exchangeDrBalNew = new BigDecimal("0"), exchangeDrBalOld = new BigDecimal("0");

            List cashOpeningOldList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_opening where dt = '" + CbsUtil.dateAdd(toDt, -1) + "' and new_old_flag = 'O' " + brcode + " group by " + groupByBranch + " denomination").getResultList();
            List cashOpeningNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_opening where dt = '" + CbsUtil.dateAdd(toDt, -1) + "' and new_old_flag = 'N' " + brcode + " group by " + groupByBranch + " denomination").getResultList();
            List cashCrOldList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 0 and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashCrNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 0 and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashDrOldList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashDrNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashCrExchangeList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno = '99' and dt = '" + toDt + "' and ty = 0  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashCrExchangeNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno = '99' and dt = '" + toDt + "' and ty = 0  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashDrExchangeList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno = '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashDrExchangeNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno = '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();

            List cashCrExchangeAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 4  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashCrExchangeNewAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 4  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashDrExchangeAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 3  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();
            List cashDrExchangeNewAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where acno <> '99' and dt = '" + toDt + "' and ty = 3  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + " denomination").getResultList();

            List result = em.createNativeQuery("select REF_DESC, cast(REF_CODE as decimal(6,2))from cbs_ref_rec_type  where REF_REC_NO = '353' order by order_by").getResultList();
            DenominationDetailsPojo pojo = new DenominationDetailsPojo();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    pojo = new DenominationDetailsPojo();
                    noOfClosingNew = 0;
                    noOfClosingOld = 0;
                    noOfOpeningNew = 0;
                    noOfOpeningOld = 0;

                    noOfExchangeCrOld = 0;
                    noOfExchangeCrNew = 0;
                    noOfExchangeDrOld = 0;
                    noOfExchangeDrNew = 0;

                    exchangeCrBalOld = new BigDecimal("0");
                    exchangeCrBalNew = new BigDecimal("0");
                    exchangeDrBalOld = new BigDecimal("0");
                    exchangeDrBalNew = new BigDecimal("0");

                    closingBalNew = new BigDecimal("0");
                    closingBalOld = new BigDecimal("0");
                    openingBalNew = new BigDecimal("0");
                    openingBalOld = new BigDecimal("0");

                    Vector jdVector = (Vector) result.get(i);
                    String curDenomination = jdVector.get(0).toString();
                    String curValue = jdVector.get(1).toString();
                    pojo.setDenominationType("CurCrDrExDetails");
                    pojo.setBrCode(BrnCode);
                    pojo.setCurrencyDenomination(curDenomination);
//                    pojo.set("");

                    /*Cash Opening Bal Old*/
                    if (!cashOpeningOldList.isEmpty()) {
                        for (int j = 0; j < cashOpeningOldList.size(); j++) {
                            Vector cashOpeningOldVect = (Vector) cashOpeningOldList.get(j);
                            String denomination = cashOpeningOldVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                if (userId.equalsIgnoreCase("A")) {
                                    pojo.setNoOfOpeingOld(Integer.parseInt(cashOpeningOldVect.get(1).toString()));
                                    pojo.setOpeingBalOld(new BigDecimal(cashOpeningOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                } else {
                                    pojo.setNoOfOpeingOld(Integer.parseInt("0"));
                                    pojo.setOpeingBalOld(new BigDecimal("0"));
                                }
                                noOfOpeningOld = noOfOpeningOld + Integer.parseInt(cashOpeningOldVect.get(1).toString());
                                openingBalOld = openingBalOld.add(new BigDecimal(cashOpeningOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                noOfClosingOld = noOfClosingOld + Integer.parseInt(cashOpeningOldVect.get(1).toString());
                                closingBalOld = closingBalOld.add(new BigDecimal(cashOpeningOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            } else {
                                pojo.setNoOfOpeingOld(Integer.parseInt("0"));
                                pojo.setOpeingBalOld(new BigDecimal("0"));
                            }
                        }
                    } else {
                        pojo.setNoOfOpeingOld(Integer.parseInt("0"));
                        pojo.setOpeingBalOld(new BigDecimal("0"));
                    }

                    /*Cash Opening Bal New*/
                    if (!cashOpeningNewList.isEmpty()) {
                        for (int j = 0; j < cashOpeningNewList.size(); j++) {
                            Vector cashOpeningNewVect = (Vector) cashOpeningNewList.get(j);
                            String denomination = cashOpeningNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                if (userId.equalsIgnoreCase("A")) {
                                    pojo.setNoOfOpeingNew(Integer.parseInt(cashOpeningNewVect.get(1).toString()));
                                    pojo.setOpeingBalNew(new BigDecimal(cashOpeningNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                } else {
                                    pojo.setNoOfOpeingNew(Integer.parseInt("0"));
                                    pojo.setOpeingBalNew(new BigDecimal("0"));
                                }
                                noOfOpeningNew = noOfOpeningNew + Integer.parseInt(cashOpeningNewVect.get(1).toString());
                                openingBalNew = openingBalNew.add(new BigDecimal(cashOpeningNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                noOfClosingNew = noOfClosingNew + Integer.parseInt(cashOpeningNewVect.get(1).toString());
                                closingBalNew = closingBalNew.add(new BigDecimal(cashOpeningNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            } else {
                                pojo.setNoOfOpeingNew(Integer.parseInt("0"));
                                pojo.setOpeingBalNew(new BigDecimal("0"));
                            }
                        }
                    } else {
                        pojo.setNoOfOpeingNew(Integer.parseInt("0"));
                        pojo.setOpeingBalNew(new BigDecimal("0"));
                    }

                    /*Cash Credit For OLD Notes*/
                    if (!cashCrOldList.isEmpty()) {
                        for (int j = 0; j < cashCrOldList.size(); j++) {
                            Vector cashCrOldVect = (Vector) cashCrOldList.get(j);
                            String denomination = cashCrOldVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                pojo.setNoOfCashDepositOld(Integer.parseInt(cashCrOldVect.get(1).toString()));
                                pojo.setCashDepositOld(new BigDecimal(cashCrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                noOfClosingOld = noOfClosingOld + Integer.parseInt(cashCrOldVect.get(1).toString());
                                closingBalOld = closingBalOld.add(new BigDecimal(cashCrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            } else {
                                pojo.setNoOfCashDepositOld(Integer.parseInt("0"));
                                pojo.setCashDepositOld(new BigDecimal("0"));
                            }
                        }
                    } else {
                        pojo.setNoOfCashDepositOld(Integer.parseInt("0"));
                        pojo.setCashDepositOld(new BigDecimal("0"));
                    }

                    /*Cash Credit For NEW Notes*/
                    if (!cashCrNewList.isEmpty()) {
                        for (int j = 0; j < cashCrNewList.size(); j++) {
                            Vector cashCrNewVect = (Vector) cashCrNewList.get(j);
                            String denomination = cashCrNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                pojo.setNoOfCashDepositNew(Integer.parseInt(cashCrNewVect.get(1).toString()));
                                pojo.setCashDepositNew(new BigDecimal(cashCrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                noOfClosingNew = noOfClosingNew + Integer.parseInt(cashCrNewVect.get(1).toString());
                                closingBalNew = closingBalNew.add(new BigDecimal(cashCrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            } else {
                                pojo.setNoOfCashDepositNew(Integer.parseInt("0"));
                                pojo.setCashDepositNew(new BigDecimal("0"));
                            }
                        }
                    } else {
                        pojo.setNoOfCashDepositNew(Integer.parseInt("0"));
                        pojo.setCashDepositNew(new BigDecimal("0"));
                    }

                    /*Cash Withdraw Old*/
                    if (!cashDrOldList.isEmpty()) {
                        for (int j = 0; j < cashDrOldList.size(); j++) {
                            Vector cashDrOldVect = (Vector) cashDrOldList.get(j);
                            String denomination = cashDrOldVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                pojo.setNoOfCashWithdrawOld(Integer.parseInt(cashDrOldVect.get(1).toString()));
                                pojo.setCashWithdrawOld(new BigDecimal(cashDrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                noOfClosingOld = noOfClosingOld - Integer.parseInt(cashDrOldVect.get(1).toString());
                                closingBalOld = closingBalOld.subtract(new BigDecimal(cashDrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            } else {
                                pojo.setNoOfCashWithdrawOld(Integer.parseInt("0"));
                                pojo.setCashWithdrawOld(new BigDecimal("0"));
                            }
                        }
                    } else {
                        pojo.setNoOfCashWithdrawOld(Integer.parseInt("0"));
                        pojo.setCashWithdrawOld(new BigDecimal("0"));
                    }

                    /*Cash Withdraw New*/
                    if (!cashDrNewList.isEmpty()) {
                        for (int j = 0; j < cashDrNewList.size(); j++) {
                            Vector cashDrNewVect = (Vector) cashDrNewList.get(j);
                            String denomination = cashDrNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                pojo.setNoOfCashWithdrawNew(Integer.parseInt(cashDrNewVect.get(1).toString()));
                                pojo.setCashWithdrawNew(new BigDecimal(cashDrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                noOfClosingNew = noOfClosingNew - Integer.parseInt(cashDrNewVect.get(1).toString());
                                closingBalNew = closingBalNew.subtract(new BigDecimal(cashDrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            } else {
                                pojo.setNoOfCashWithdrawNew(Integer.parseInt("0"));
                                pojo.setCashWithdrawNew(new BigDecimal("0"));
                            }
                        }
                    } else {
                        pojo.setNoOfCashWithdrawNew(Integer.parseInt("0"));
                        pojo.setCashWithdrawNew(new BigDecimal("0"));
                    }

                    /*Cash Credit Exchange OLD*/
                    if (!cashCrExchangeList.isEmpty()) {
                        for (int j = 0; j < cashCrExchangeList.size(); j++) {
                            Vector cashCrExchangeVect = (Vector) cashCrExchangeList.get(j);
                            String denomination = cashCrExchangeVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeCrOld = noOfExchangeCrOld + Integer.parseInt(cashCrExchangeVect.get(1).toString());
                                exchangeCrBalOld = exchangeCrBalOld.add(new BigDecimal(cashCrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingOld = noOfClosingOld + Integer.parseInt(cashCrExchangeVect.get(1).toString());
                                closingBalOld = closingBalOld.add(new BigDecimal(cashCrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Cash Credit Exchange NEW*/
                    if (!cashCrExchangeNewList.isEmpty()) {
                        for (int j = 0; j < cashCrExchangeNewList.size(); j++) {
                            Vector cashCrExchangeNewVect = (Vector) cashCrExchangeNewList.get(j);
                            String denomination = cashCrExchangeNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeCrNew = noOfExchangeCrNew + Integer.parseInt(cashCrExchangeNewVect.get(1).toString());
                                exchangeCrBalNew = exchangeCrBalNew.add(new BigDecimal(cashCrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingNew = noOfClosingNew + Integer.parseInt(cashCrExchangeNewVect.get(1).toString());
                                closingBalNew = closingBalNew.add(new BigDecimal(cashCrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Cash Debit Exchange OLD*/
                    if (!cashDrExchangeList.isEmpty()) {
                        for (int j = 0; j < cashDrExchangeList.size(); j++) {
                            Vector cashDrExchangeVect = (Vector) cashDrExchangeList.get(j);
                            String denomination = cashDrExchangeVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeDrOld = noOfExchangeDrOld + Integer.parseInt(cashDrExchangeVect.get(1).toString());
                                exchangeDrBalOld = exchangeDrBalOld.add(new BigDecimal(cashDrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingOld = noOfClosingOld - Integer.parseInt(cashDrExchangeVect.get(1).toString());
                                closingBalOld = closingBalOld.subtract(new BigDecimal(cashDrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Cash Debit Exchange NEW*/
                    if (!cashDrExchangeNewList.isEmpty()) {
                        for (int j = 0; j < cashDrExchangeNewList.size(); j++) {
                            Vector cashDrExchangeNewVect = (Vector) cashDrExchangeNewList.get(j);
                            String denomination = cashDrExchangeNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeDrNew = noOfExchangeDrNew + Integer.parseInt(cashDrExchangeNewVect.get(1).toString());
                                exchangeDrBalNew = exchangeDrBalNew.add(new BigDecimal(cashDrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingNew = noOfClosingNew - Integer.parseInt(cashDrExchangeNewVect.get(1).toString());
                                closingBalNew = closingBalNew.subtract(new BigDecimal(cashDrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }
//                    
                    /*Cash Credit As Per Account but not in Exchange OLD*/
                    if (!cashCrExchangeAcNoList.isEmpty()) {
                        for (int j = 0; j < cashCrExchangeAcNoList.size(); j++) {
                            Vector cashCrExchangeAcNoVect = (Vector) cashCrExchangeAcNoList.get(j);
                            String denomination = cashCrExchangeAcNoVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeCrOld = noOfExchangeCrOld + Integer.parseInt(cashCrExchangeAcNoVect.get(1).toString());
                                exchangeCrBalOld = exchangeCrBalOld.add(new BigDecimal(cashCrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingOld = noOfClosingOld + Integer.parseInt(cashCrExchangeAcNoVect.get(1).toString());
                                closingBalOld = closingBalOld.add(new BigDecimal(cashCrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Cash Credit As Per Account but not in Exchange NEW*/
                    if (!cashCrExchangeNewAcNoList.isEmpty()) {
                        for (int j = 0; j < cashCrExchangeNewAcNoList.size(); j++) {
                            Vector cashCrExchangeAcNoNewVect = (Vector) cashCrExchangeNewAcNoList.get(j);
                            String denomination = cashCrExchangeAcNoNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeCrNew = noOfExchangeCrNew + Integer.parseInt(cashCrExchangeAcNoNewVect.get(1).toString());
                                exchangeCrBalNew = exchangeCrBalNew.add(new BigDecimal(cashCrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingNew = noOfClosingNew + Integer.parseInt(cashCrExchangeAcNoNewVect.get(1).toString());
                                closingBalNew = closingBalNew.add(new BigDecimal(cashCrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Cash Debit As Per Account but not in Exchange OLD*/
                    if (!cashDrExchangeAcNoList.isEmpty()) {
                        for (int j = 0; j < cashDrExchangeAcNoList.size(); j++) {
                            Vector cashDrExchangeAcNoVect = (Vector) cashDrExchangeAcNoList.get(j);
                            String denomination = cashDrExchangeAcNoVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeDrOld = noOfExchangeDrOld + Integer.parseInt(cashDrExchangeAcNoVect.get(1).toString());
                                exchangeDrBalOld = exchangeDrBalOld.add(new BigDecimal(cashDrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingOld = noOfClosingOld - Integer.parseInt(cashDrExchangeAcNoVect.get(1).toString());
                                closingBalOld = closingBalOld.subtract(new BigDecimal(cashDrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Cash Debit As Per Account but not in Exchange NEW*/
                    if (!cashDrExchangeNewAcNoList.isEmpty()) {
                        for (int j = 0; j < cashDrExchangeNewAcNoList.size(); j++) {
                            Vector cashDrExchangeAcNoNewVect = (Vector) cashDrExchangeNewAcNoList.get(j);
                            String denomination = cashDrExchangeAcNoNewVect.get(0).toString();
                            if (denomination.equalsIgnoreCase(curValue)) {
                                noOfExchangeDrNew = noOfExchangeDrNew + Integer.parseInt(cashDrExchangeAcNoNewVect.get(1).toString());
                                exchangeDrBalNew = exchangeDrBalNew.add(new BigDecimal(cashDrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                noOfClosingNew = noOfClosingNew - Integer.parseInt(cashDrExchangeAcNoNewVect.get(1).toString());
                                closingBalNew = closingBalNew.subtract(new BigDecimal(cashDrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                break;
                            }
                        }
                    }

                    /*Exchange Denomination*/
                    pojo.setNoOfCashExchangeDeposit(noOfExchangeCrOld);
                    pojo.setCashExchangeDeposit(exchangeCrBalOld);
                    pojo.setNoOfCashExchangeDepositNew(noOfExchangeCrNew);
                    pojo.setCashExchangeDepositNew(exchangeCrBalNew);
                    pojo.setNoOfCashExchangeWithdraw(noOfExchangeDrOld);
                    pojo.setCashExchangeWithdraw(exchangeDrBalOld);
                    pojo.setNoOfCashExchangeWithdrawNew(noOfExchangeDrNew);
                    pojo.setCashExchangeWithdrawNew(exchangeDrBalNew);


                    /* Closing Balance */
                    if (userId.equalsIgnoreCase("A")) {
                        pojo.setNoOfClosingNew(noOfClosingNew);
                        pojo.setNoOfClosingOld(noOfClosingOld);
                        pojo.setClosingBalNew(closingBalNew);
                        pojo.setClosingBalOld(closingBalOld);
                    } else {
                        pojo.setNoOfClosingNew(0);
                        pojo.setNoOfClosingOld(0);
                        pojo.setClosingBalNew(new BigDecimal("0"));
                        pojo.setClosingBalOld(new BigDecimal("0"));
                    }


                    dataList.add(pojo);
                }
            }
            if (dataList.isEmpty()) {
                pojo.setDenominationType("CurCrDrExDetails");
                pojo.setBrCode(BrnCode);
                pojo.setCurrencyDenomination("");
                pojo.setNoOfCashDepositOld(Integer.parseInt("0"));
                pojo.setCashDepositOld(new BigDecimal("0"));
                pojo.setNoOfCashDepositNew(Integer.parseInt("0"));
                pojo.setCashDepositNew(new BigDecimal("0"));
                pojo.setNoOfCashWithdrawNew(Integer.parseInt("0"));
                pojo.setCashWithdrawNew(new BigDecimal("0"));
                pojo.setNoOfCashWithdrawOld(Integer.parseInt("0"));
                pojo.setCashWithdrawOld(new BigDecimal("0"));
                pojo.setNoOfCashExchangeDeposit(Integer.parseInt("0"));
                pojo.setCashExchangeDeposit(new BigDecimal("0"));
                pojo.setNoOfCashExchangeWithdraw(Integer.parseInt("0"));
                pojo.setCashExchangeWithdraw(new BigDecimal("0"));
                pojo.setNoOfCashExchangeDepositNew(Integer.parseInt("0"));
                pojo.setCashExchangeDepositNew(new BigDecimal("0"));
                pojo.setNoOfCashExchangeWithdrawNew(Integer.parseInt("0"));
                pojo.setCashExchangeWithdrawNew(new BigDecimal("0"));
                dataList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<DenominationDetailsPojo> denominationDetailsAcWiseReport(String BrnCode, String toDt, String typeOfReport, String userId) throws ApplicationException {
        List<DenominationDetailsPojo> dataList = new ArrayList<DenominationDetailsPojo>();
        try {
            DenominationDetailsPojo pojo = new DenominationDetailsPojo();
            String brcode = "", groupByBranch = "", typeOfReportQuery = "", acNo = "", acTypeQuery = "", groupByAcType = "", acTypeDesc = "", userIdWise = "";
            if (BrnCode.equalsIgnoreCase("0A")) {
                brcode = " ";
                groupByBranch = " ";
            } else {
                brcode = " and brncode = '" + BrnCode + "' ";
                groupByBranch = " brncode, ";
            }
            if (typeOfReport.equalsIgnoreCase("Y")) {
                acTypeQuery = " acno ";
                groupByAcType = " acno, ";
            } else if (typeOfReport.equalsIgnoreCase("N")) {
                acTypeQuery = " substring(acno,3,2) ";
                groupByAcType = " substring(acno,3,2), ";
            }

            if (userId.equalsIgnoreCase("A")) {
                userIdWise = "";
            } else {
                userIdWise = "and enterby = '" + userId + "'";
            }
            Integer noOfClosingNew = 0, noOfClosingOld = 0, noOfOpeningNew = 0, noOfOpeningOld = 0,
                    noOfExchangeCrOld = 0, noOfExchangeCrNew = 0, noOfExchangeDrOld = 0, noOfExchangeDrNew = 0;
            BigDecimal closingBalNew = new BigDecimal("0"), closingBalOld = new BigDecimal("0"), openingBalNew = new BigDecimal("0"), openingBalOld = new BigDecimal("0"),
                    exchangeCrBalNew = new BigDecimal("0"), exchangeCrBalOld = new BigDecimal("0"), exchangeDrBalNew = new BigDecimal("0"), exchangeDrBalOld = new BigDecimal("0");

            List acnoList = em.createNativeQuery("select distinct " + acTypeQuery + " from denomination_detail where dt = '" + toDt + "' order by " + acTypeQuery + "").getResultList();
            if (!acnoList.isEmpty()) {
                for (int z = 0; z < acnoList.size(); z++) {
                    Vector acNoVector = (Vector) acnoList.get(z);
                    acNo = acNoVector.get(0).toString();

                    noOfClosingNew = 0;
                    noOfClosingOld = 0;
                    noOfOpeningNew = 0;
                    noOfOpeningOld = 0;

                    noOfExchangeCrOld = 0;
                    noOfExchangeCrNew = 0;
                    noOfExchangeDrOld = 0;
                    noOfExchangeDrNew = 0;

                    exchangeCrBalOld = new BigDecimal("0");
                    exchangeCrBalNew = new BigDecimal("0");
                    exchangeDrBalOld = new BigDecimal("0");
                    exchangeDrBalNew = new BigDecimal("0");

                    closingBalNew = new BigDecimal("0");
                    closingBalOld = new BigDecimal("0");
                    openingBalNew = new BigDecimal("0");
                    openingBalOld = new BigDecimal("0");
                    List cashOpeningOldList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_opening where dt = '" + CbsUtil.dateAdd(toDt, -1) + "' and new_old_flag = 'O' " + brcode + " group by " + groupByBranch + " denomination").getResultList();
                    List cashOpeningNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_opening where dt = '" + CbsUtil.dateAdd(toDt, -1) + "' and new_old_flag = 'N' " + brcode + " group by " + groupByBranch + " denomination").getResultList();
                    List cashCrOldList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 0 and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashCrNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 0 and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashDrOldList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashDrNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashCrExchangeList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno = '99' and dt = '" + toDt + "' and ty = 0  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashCrExchangeNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno = '99' and dt = '" + toDt + "' and ty = 0  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashDrExchangeList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno = '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashDrExchangeNewList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno = '99' and dt = '" + toDt + "' and ty = 1  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();

                    List cashCrExchangeAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 4  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashCrExchangeNewAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 4  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashDrExchangeAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 3  and new_old_flag = 'O' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();
                    List cashDrExchangeNewAcNoList = em.createNativeQuery("select denomination, sum(denomination_value) from denomination_detail where " + acTypeQuery + " = '" + acNo + "' and acno <> '99' and dt = '" + toDt + "' and ty = 3  and new_old_flag = 'N' " + brcode + " " + userIdWise + " group by " + groupByBranch + groupByAcType + " denomination").getResultList();

                    List result = em.createNativeQuery("select REF_DESC, cast(REF_CODE as decimal(6,2))from cbs_ref_rec_type  where REF_REC_NO = '353' order by order_by").getResultList();
                    acTypeDesc = "";
                    if (typeOfReport.equalsIgnoreCase("N")) {
                        List acTypeDescList = ftsBean.getAcctCodeDetails(acNo);
                        if (!acTypeDescList.isEmpty()) {
                            Vector acTypeDescVect = (Vector) acTypeDescList.get(0);
                            acTypeDesc = " [".concat(acTypeDescVect.get(0).toString()).concat("] AcNature:{").concat(acTypeDescVect.get(2).toString()).concat("} ");
                        }
                    }
                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            int denominationflagAdd = 0;
                            noOfExchangeCrOld = 0;
                            noOfExchangeCrNew = 0;
                            noOfExchangeDrOld = 0;
                            noOfExchangeDrNew = 0;

                            exchangeCrBalOld = new BigDecimal("0");
                            exchangeCrBalNew = new BigDecimal("0");
                            exchangeDrBalOld = new BigDecimal("0");
                            exchangeDrBalNew = new BigDecimal("0");

                            pojo = new DenominationDetailsPojo();
                            Vector jdVector = (Vector) result.get(i);
                            String curDenomination = jdVector.get(0).toString();
                            String curValue = jdVector.get(1).toString();
                            pojo.setDenominationType(acNo.equalsIgnoreCase("99") ? "EXCHANGE VALUE" : (typeOfReport.equalsIgnoreCase("Y") ? "A/c:" : "A/c Type:").concat(acNo).concat(acTypeDesc));
                            pojo.setBrCode(BrnCode);
                            pojo.setCurrencyDenomination(curDenomination);
                            //                    pojo.set("");

                            /*Cash Opening Bal Old*/
                            pojo.setNoOfOpeingOld(Integer.parseInt("0"));
                            pojo.setOpeingBalOld(new BigDecimal("0"));

                            /*Cash Opening Bal New*/
                            pojo.setNoOfOpeingNew(Integer.parseInt("0"));
                            pojo.setOpeingBalNew(new BigDecimal("0"));

                            /*Cash Credit For OLD Notes*/
                            if (!cashCrOldList.isEmpty()) {
                                for (int j = 0; j < cashCrOldList.size(); j++) {
                                    Vector cashCrOldVect = (Vector) cashCrOldList.get(j);
                                    String denomination = cashCrOldVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        pojo.setNoOfCashDepositOld(Integer.parseInt(cashCrOldVect.get(1).toString()));
                                        pojo.setCashDepositOld(new BigDecimal(cashCrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        noOfClosingOld = noOfClosingOld + Integer.parseInt(cashCrOldVect.get(1).toString());
                                        closingBalOld = closingBalOld.add(new BigDecimal(cashCrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    } else {
                                        pojo.setNoOfCashDepositOld(Integer.parseInt("0"));
                                        pojo.setCashDepositOld(new BigDecimal("0"));
                                    }
                                }
                            } else {
                                pojo.setNoOfCashDepositOld(Integer.parseInt("0"));
                                pojo.setCashDepositOld(new BigDecimal("0"));
                            }

                            /*Cash Credit For NEW Notes*/
                            if (!cashCrNewList.isEmpty()) {
                                for (int j = 0; j < cashCrNewList.size(); j++) {
                                    Vector cashCrNewVect = (Vector) cashCrNewList.get(j);
                                    String denomination = cashCrNewVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        pojo.setNoOfCashDepositNew(Integer.parseInt(cashCrNewVect.get(1).toString()));
                                        pojo.setCashDepositNew(new BigDecimal(cashCrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        noOfClosingNew = noOfClosingNew + Integer.parseInt(cashCrNewVect.get(1).toString());
                                        closingBalNew = closingBalNew.add(new BigDecimal(cashCrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    } else {
                                        pojo.setNoOfCashDepositNew(Integer.parseInt("0"));
                                        pojo.setCashDepositNew(new BigDecimal("0"));
                                    }
                                }
                            } else {
                                pojo.setNoOfCashDepositNew(Integer.parseInt("0"));
                                pojo.setCashDepositNew(new BigDecimal("0"));
                            }

                            /*Cash Withdraw Old*/
                            if (!cashDrOldList.isEmpty()) {
                                for (int j = 0; j < cashDrOldList.size(); j++) {
                                    Vector cashDrOldVect = (Vector) cashDrOldList.get(j);
                                    String denomination = cashDrOldVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        pojo.setNoOfCashWithdrawOld(Integer.parseInt(cashDrOldVect.get(1).toString()));
                                        pojo.setCashWithdrawOld(new BigDecimal(cashDrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        noOfClosingOld = noOfClosingOld - Integer.parseInt(cashDrOldVect.get(1).toString());
                                        closingBalOld = closingBalOld.subtract(new BigDecimal(cashDrOldVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    } else {
                                        pojo.setNoOfCashWithdrawOld(Integer.parseInt("0"));
                                        pojo.setCashWithdrawOld(new BigDecimal("0"));
                                    }
                                }
                            } else {
                                pojo.setNoOfCashWithdrawOld(Integer.parseInt("0"));
                                pojo.setCashWithdrawOld(new BigDecimal("0"));
                            }

                            /*Cash Withdraw New*/
                            if (!cashDrNewList.isEmpty()) {
                                for (int j = 0; j < cashDrNewList.size(); j++) {
                                    Vector cashDrNewVect = (Vector) cashDrNewList.get(j);
                                    String denomination = cashDrNewVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        pojo.setNoOfCashWithdrawNew(Integer.parseInt(cashDrNewVect.get(1).toString()));
                                        pojo.setCashWithdrawNew(new BigDecimal(cashDrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        noOfClosingNew = noOfClosingNew - Integer.parseInt(cashDrNewVect.get(1).toString());
                                        closingBalNew = closingBalNew.subtract(new BigDecimal(cashDrNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    } else {
                                        pojo.setNoOfCashWithdrawNew(Integer.parseInt("0"));
                                        pojo.setCashWithdrawNew(new BigDecimal("0"));
                                    }
                                }
                            } else {
                                pojo.setNoOfCashWithdrawNew(Integer.parseInt("0"));
                                pojo.setCashWithdrawNew(new BigDecimal("0"));
                            }

                            /*Cash Credit Exchange OLD*/
                            if (!cashCrExchangeList.isEmpty()) {
                                for (int j = 0; j < cashCrExchangeList.size(); j++) {
                                    Vector cashCrExchangeVect = (Vector) cashCrExchangeList.get(j);
                                    String denomination = cashCrExchangeVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeCrOld = noOfExchangeCrOld + Integer.parseInt(cashCrExchangeVect.get(1).toString());
                                        exchangeCrBalOld = exchangeCrBalOld.add(new BigDecimal(cashCrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingOld = noOfClosingOld + Integer.parseInt(cashCrExchangeVect.get(1).toString());
                                        closingBalOld = closingBalOld.add(new BigDecimal(cashCrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Credit Exchange NEW*/
                            if (!cashCrExchangeNewList.isEmpty()) {
                                for (int j = 0; j < cashCrExchangeNewList.size(); j++) {
                                    Vector cashCrExchangeNewVect = (Vector) cashCrExchangeNewList.get(j);
                                    String denomination = cashCrExchangeNewVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeCrNew = noOfExchangeCrNew + Integer.parseInt(cashCrExchangeNewVect.get(1).toString());
                                        exchangeCrBalNew = exchangeCrBalNew.add(new BigDecimal(cashCrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingNew = noOfClosingNew + Integer.parseInt(cashCrExchangeNewVect.get(1).toString());
                                        closingBalNew = closingBalNew.add(new BigDecimal(cashCrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Debit Exchange OLD*/
                            if (!cashDrExchangeList.isEmpty()) {
                                for (int j = 0; j < cashDrExchangeList.size(); j++) {
                                    Vector cashDrExchangeVect = (Vector) cashDrExchangeList.get(j);
                                    String denomination = cashDrExchangeVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeDrOld = noOfExchangeDrOld + Integer.parseInt(cashDrExchangeVect.get(1).toString());
                                        exchangeDrBalOld = exchangeDrBalOld.add(new BigDecimal(cashDrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingOld = noOfClosingOld - Integer.parseInt(cashDrExchangeVect.get(1).toString());
                                        closingBalOld = closingBalOld.subtract(new BigDecimal(cashDrExchangeVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Debit Exchange NEW*/
                            if (!cashDrExchangeNewList.isEmpty()) {
                                for (int j = 0; j < cashDrExchangeNewList.size(); j++) {
                                    Vector cashDrExchangeNewVect = (Vector) cashDrExchangeNewList.get(j);
                                    String denomination = cashDrExchangeNewVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeDrNew = noOfExchangeDrNew + Integer.parseInt(cashDrExchangeNewVect.get(1).toString());
                                        exchangeDrBalNew = exchangeDrBalNew.add(new BigDecimal(cashDrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingNew = noOfClosingNew - Integer.parseInt(cashDrExchangeNewVect.get(1).toString());
                                        closingBalNew = closingBalNew.subtract(new BigDecimal(cashDrExchangeNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Credit As Per Account but not in Exchange OLD*/
                            if (!cashCrExchangeAcNoList.isEmpty()) {
                                for (int j = 0; j < cashCrExchangeAcNoList.size(); j++) {
                                    Vector cashCrExchangeAcNoVect = (Vector) cashCrExchangeAcNoList.get(j);
                                    String denomination = cashCrExchangeAcNoVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeCrOld = noOfExchangeCrOld + Integer.parseInt(cashCrExchangeAcNoVect.get(1).toString());
                                        exchangeCrBalOld = exchangeCrBalOld.add(new BigDecimal(cashCrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingOld = noOfClosingOld + Integer.parseInt(cashCrExchangeAcNoVect.get(1).toString());
                                        closingBalOld = closingBalOld.add(new BigDecimal(cashCrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Credit As Per Account but not in Exchange NEW*/
                            if (!cashCrExchangeNewAcNoList.isEmpty()) {
                                for (int j = 0; j < cashCrExchangeNewAcNoList.size(); j++) {
                                    Vector cashCrExchangeAcNoNewVect = (Vector) cashCrExchangeNewAcNoList.get(j);
                                    String denomination = cashCrExchangeAcNoNewVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeCrNew = noOfExchangeCrNew + Integer.parseInt(cashCrExchangeAcNoNewVect.get(1).toString());
                                        exchangeCrBalNew = exchangeCrBalNew.add(new BigDecimal(cashCrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingNew = noOfClosingNew + Integer.parseInt(cashCrExchangeAcNoNewVect.get(1).toString());
                                        closingBalNew = closingBalNew.add(new BigDecimal(cashCrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Debit As Per Account but not in Exchange OLD*/
                            if (!cashDrExchangeAcNoList.isEmpty()) {
                                for (int j = 0; j < cashDrExchangeAcNoList.size(); j++) {
                                    Vector cashDrExchangeAcNoVect = (Vector) cashDrExchangeAcNoList.get(j);
                                    String denomination = cashDrExchangeAcNoVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeDrOld = noOfExchangeDrOld + Integer.parseInt(cashDrExchangeAcNoVect.get(1).toString());
                                        exchangeDrBalOld = exchangeDrBalOld.add(new BigDecimal(cashDrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingOld = noOfClosingOld - Integer.parseInt(cashDrExchangeAcNoVect.get(1).toString());
                                        closingBalOld = closingBalOld.subtract(new BigDecimal(cashDrExchangeAcNoVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Cash Debit As Per Account but not in Exchange NEW*/
                            if (!cashDrExchangeNewAcNoList.isEmpty()) {
                                for (int j = 0; j < cashDrExchangeNewAcNoList.size(); j++) {
                                    Vector cashDrExchangeAcNoNewVect = (Vector) cashDrExchangeNewAcNoList.get(j);
                                    String denomination = cashDrExchangeAcNoNewVect.get(0).toString();
                                    if (denomination.equalsIgnoreCase(curValue)) {
                                        noOfExchangeDrNew = noOfExchangeDrNew + Integer.parseInt(cashDrExchangeAcNoNewVect.get(1).toString());
                                        exchangeDrBalNew = exchangeDrBalNew.add(new BigDecimal(cashDrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));

                                        noOfClosingNew = noOfClosingNew - Integer.parseInt(cashDrExchangeAcNoNewVect.get(1).toString());
                                        closingBalNew = closingBalNew.subtract(new BigDecimal(cashDrExchangeAcNoNewVect.get(1).toString()).multiply(new BigDecimal(denomination)));
                                        denominationflagAdd = 1;
                                        break;
                                    }
                                }
                            }

                            /*Exchange Denomination */
                            pojo.setNoOfCashExchangeDeposit(noOfExchangeCrOld);
                            pojo.setCashExchangeDeposit(exchangeCrBalOld);
                            pojo.setNoOfCashExchangeDepositNew(noOfExchangeCrNew);
                            pojo.setCashExchangeDepositNew(exchangeCrBalNew);
                            pojo.setNoOfCashExchangeWithdraw(noOfExchangeDrOld);
                            pojo.setCashExchangeWithdraw(exchangeDrBalOld);
                            pojo.setNoOfCashExchangeWithdrawNew(noOfExchangeDrNew);
                            pojo.setCashExchangeWithdrawNew(exchangeDrBalNew);

                            /* Closing Balance */
                            pojo.setNoOfClosingNew(noOfClosingNew);
                            pojo.setNoOfClosingOld(noOfClosingOld);
                            pojo.setClosingBalNew(closingBalNew);
                            pojo.setClosingBalOld(closingBalOld);

                            if (denominationflagAdd == 1) {
                                dataList.add(pojo);
                            }
                        }
                    }
                }
            }
            if (dataList.isEmpty()) {
                pojo.setDenominationType((typeOfReport.equalsIgnoreCase("Y") ? "A/c:" : "A/c Type:").concat(acNo).concat(acTypeDesc));
                pojo.setBrCode(BrnCode);
                pojo.setCurrencyDenomination("");
                pojo.setNoOfCashDepositOld(Integer.parseInt("0"));
                pojo.setCashDepositOld(new BigDecimal("0"));
                pojo.setNoOfCashDepositNew(Integer.parseInt("0"));
                pojo.setCashDepositNew(new BigDecimal("0"));
                pojo.setNoOfCashWithdrawNew(Integer.parseInt("0"));
                pojo.setCashWithdrawNew(new BigDecimal("0"));
                pojo.setNoOfCashWithdrawOld(Integer.parseInt("0"));
                pojo.setCashWithdrawOld(new BigDecimal("0"));
                pojo.setNoOfCashExchangeDeposit(Integer.parseInt("0"));
                pojo.setCashExchangeDeposit(new BigDecimal("0"));
                pojo.setNoOfCashExchangeWithdraw(Integer.parseInt("0"));
                pojo.setCashExchangeWithdraw(new BigDecimal("0"));
                pojo.setNoOfCashExchangeDepositNew(Integer.parseInt("0"));
                pojo.setCashExchangeDepositNew(new BigDecimal("0"));
                pojo.setNoOfCashExchangeWithdrawNew(Integer.parseInt("0"));
                pojo.setCashExchangeWithdrawNew(new BigDecimal("0"));
                dataList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<DeletedTransactionPojo> getDeletedTransactionData(String brCode, String frDt, String toDt, String acNo, String reportType) throws ApplicationException {
        List<DeletedTransactionPojo> dataList = new ArrayList<DeletedTransactionPojo>();
        List result = new ArrayList();

        try {

//            if (brCode.equalsIgnoreCase("0A")) {
//                result = em.createNativeQuery("select Acno,Custname,Enterby,Deletedby,date_format(Deletedt,'%d/%m/%Y'),trantype,ifnull(TrsNo,0),Cramt,"
//                        + "Dramt,ifnull(Details,'') from deletetrans a,"
//                        + "(select UserId,date_format(lastLoginDate,'%Y%m%d') sDt from securityinfohistory  "
//                        + "union "
//                        + "select UserId,date_format(lastLoginDate,'%Y%m%d') sDt from securityinfo )b where a.Deletedby = b.UserId  "
//                        + "and a.Deletedt = b.sDt and Deletedt between '" + frDt + "' and '" + toDt + "' order by date_format(Deletedt,'%d/%m/%Y')").getResultList();
//            } else {
//                result = em.createNativeQuery("select Acno,Custname,Enterby,Deletedby,date_format(Deletedt,'%d/%m/%Y'),trantype,ifnull(TrsNo,0),Cramt,"
//                        + "Dramt,ifnull(Details,'') from deletetrans a,"
//                        + "(select UserId,date_format(lastLoginDate,'%Y%m%d') sDt from securityinfohistory where brncode = '" + brCode + "' "
//                        + "union "
//                        + "select UserId,date_format(lastLoginDate,'%Y%m%d') sDt from securityinfo where brncode = '" + brCode + "')b where a.Deletedby = b.UserId  "
//                        + "and a.Deletedt = b.sDt and Deletedt between '" + frDt + "' and '" + toDt + "' order by date_format(Deletedt,'%d/%m/%Y')").getResultList();
//            }
            if (reportType.equalsIgnoreCase("IU")) {
                result = em.createNativeQuery("select Acno,Custname,Enterby,Deletedby,date_format(Deletedt,'%d/%m/%Y'),trantype,ifnull(TrsNo,0),Cramt,Dramt,"
                        + "ifnull(Details,'') from deletetrans where Deletedby = '" + acNo + "' and Deletedt between '" + frDt + "' and '" + toDt + "' ").getResultList();
            } else if (reportType.equalsIgnoreCase("AU")) {
                result = em.createNativeQuery("select Acno,Custname,Enterby,Deletedby,date_format(Deletedt,'%d/%m/%Y'),trantype,ifnull(TrsNo,0),Cramt,Dramt,"
                        + "ifnull(Details,'') from deletetrans where Deletedt between '" + frDt + "' and '" + toDt + "' ").getResultList();
            } else {
                result = em.createNativeQuery("select Acno,Custname,Enterby,Deletedby,date_format(Deletedt,'%d/%m/%Y'),trantype,ifnull(TrsNo,0),Cramt,Dramt,"
                        + "ifnull(Details,'') from deletetrans where Acno = '" + acNo + "' and Deletedt between '" + frDt + "' and '" + toDt + "' ").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    DeletedTransactionPojo pojo = new DeletedTransactionPojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setEnterBy(vtr.get(2).toString());
                    pojo.setDeletedBy(vtr.get(3).toString());
                    pojo.setDeleteDt(vtr.get(4).toString());
                    pojo.setTranType(Integer.parseInt(vtr.get(5).toString()));
                    pojo.setTrsNo(Float.parseFloat(vtr.get(6).toString()));
                    pojo.setCreditAmt(Double.parseDouble(vtr.get(7).toString()));
                    pojo.setDebitAmt(Double.parseDouble(vtr.get(8).toString()));
                    if (vtr.get(1).toString().contains("PAY")) {
                        String dtl = vtr.get(9).toString().replace("#", "");
                        pojo.setDetail(dtl);
                    } else {
                        pojo.setDetail("");
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return dataList;
    }

    public List<ctrMoreThan1Crore> getDemandBankersChecqueData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount) throws ApplicationException {
        List<ctrMoreThan1Crore> dataList = new ArrayList<ctrMoreThan1Crore>();
        List result = new ArrayList();
        List result1 = new ArrayList();
        try {
            String branchCode = "";
            if (BrnCode.length() > 4) {
                branchCode = "90";
            } else {
                branchCode = BrnCode;
            }

            List list = em.createNativeQuery("select ifnull(fiureid,'BANUCXXXXX') from branchmaster where brnCode = " + branchCode + " ").getResultList();
            Vector fiuVector = (Vector) list.get(0);

            result = em.createNativeQuery("select count(*) from ("
                    + "select acno from bill_po where status = 'cancelled' and trantype = 0 and substring(acno,1,2) in(" + BrnCode + ") and dt between '" + fromDt + "' and '" + toDt + "' group by acno having sum(amount)>= " + amount + " "
                    + "union all "
                    + "select acno from bill_dd where status = 'cancelled' and trantype = 0 and substring(acno,1,2) in(" + BrnCode + ") and dt between '" + fromDt + "' and '" + toDt + "' group by acno having sum(amount)>= " + amount + " "
                    + ")a").getResultList();

            result1 = em.createNativeQuery("select ifnull(sum(Amt),0) from ("
                    + "select ifnull(sum(amount),0) as Amt from bill_po where status = 'cancelled' and trantype = 0 and substring(acno,1,2) in(" + BrnCode + ") and dt between '" + fromDt + "' and '" + toDt + "' group by acno having sum(amount)>= " + amount + " "
                    + "union all "
                    + "select ifnull(sum(amount),0) as Amt from bill_dd where status = 'cancelled' and trantype = 0 and substring(acno,1,2) in(" + BrnCode + ") and dt between '" + fromDt + "' and '" + toDt + "' group by acno having sum(amount)>= " + amount + " "
                    + ")b").getResultList();
//            }
            String acNo = "";
            double amt = 0d;
            if (!result.isEmpty()) {
                Vector vtr = (Vector) result.get(0);
                acNo = vtr.get(0).toString();
            }
            if (!result1.isEmpty()) {
                Vector vtr = (Vector) result1.get(0);
                amt = new Double(vtr.get(0).toString());
            }
            ctrMoreThan1Crore arfAcc = new ctrMoreThan1Crore();
            List<ArfBrcPojo> arfBrcPojos = ctrStrRemote.getArfBrcData(branchCode, "STR");
            arfAcc.setReportingEntityName("BANUC");
            arfAcc.setFiureid(fiuVector.get(0).toString());
            arfAcc.setAccountNumber("");
            arfAcc.setBrachName(arfBrcPojos.get(0).getBranchName());
            arfAcc.setBrachAdd(arfBrcPojos.get(0).getAddress());
            arfAcc.setBranchCity(arfBrcPojos.get(0).getCity());
            arfAcc.setBrachPinCode(arfBrcPojos.get(0).getPinCode());
            arfAcc.setAccountHolderName("");
            arfAcc.setIdentificationDocumentType("");
            arfAcc.setIdentificationDocumentNumber("");
            arfAcc.setAccountHoldersPAN("");
            arfAcc.setAccountHoldersAddress("");
            arfAcc.setAccountHoldersCity("");
            arfAcc.setNoOfCancelPoDo(acNo);
            arfAcc.setValueOfCancelPoDo(new BigDecimal(amt));
            dataList.add(arfAcc);

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;

    }

    public List<DepositLoanPojo> getDepositLoanData(String RepType, String BrnCode, String fromDt, String toDt, String acType, String denoType) throws ApplicationException {
        List<DepositLoanPojo> dataList = new ArrayList<DepositLoanPojo>();
        String branch = "", curBranch = "", table = "", actNature = "";
        List result = new ArrayList();
        try {
//            result = em.createNativeQuery("select  d.acNo,d.withdrwal,d.deposit,d.depWithdDt,custname,ifnull(pan_girnumber,''),ifnull(aadhaar_no,'') from customerid b,cbs_customer_master_detail c,"
//                    + "(select acNo,withdrwal,deposit,depWithdDt from ("
//                    + "select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from ddstransaction where " + branch + " TranType = 0 and substring(acno,3,2)='" + denoType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno "
//                    + "union all "
//                    + "select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from recon where " + branch + " TranType = 0 and substring(acno,3,2)='" + denoType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno "
//                    + "union all "
//                    + "select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from rdrecon where " + branch + " TranType = 0 and substring(acno,3,2)='" + denoType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno "
//                    + "union all "
//                    + "select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from td_recon where " + branch + " TranType = 0 and substring(acno,3,2)='" + denoType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno "
//                    + "union all "
//                    + "select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from loan_recon where " + branch + " TranType = 0 and substring(acno,3,2)='" + denoType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno "
//                    + "union all "
//                    + "select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from ca_recon where " + branch + " TranType = 0 and substring(acno,3,2)='" + denoType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno"
//                    + ")a)d where d.acno=b.acno and b.custid = cast(c.customerid as unsigned)").getResultList();
            if (RepType.equalsIgnoreCase("1") && denoType.equalsIgnoreCase("N")) {
                actNature = ftsBean.getAcNatureByCode(acType);
                if (BrnCode.equalsIgnoreCase("0A")) {
                    branch = "";
                    curBranch = "";
                } else {
                    branch = "substring(acno,1,2)='" + BrnCode + "' and";
                    curBranch = "curbrcode  = '" + BrnCode + "' and";
                }
                if (actNature.equalsIgnoreCase("FD") || actNature.equalsIgnoreCase("MS") || actNature.equalsIgnoreCase("TD")) {
                    table = "td_accountmaster";
                } else {
                    table = "accountmaster";
                }
            }
            if (RepType.equalsIgnoreCase("1") && denoType.equalsIgnoreCase("N")) {
                result = em.createNativeQuery("select fi.acNo,fi.withdrwal,fi.deposit,fi.depWithdDt,custname,ifnull(pan_girnumber,''),ifnull(aadhaar_no,''),fi.operationDt,fi.opDt,fi.clDt,ifnull(PerPhoneNumber,'') "
                        + "from customerid bb,cbs_customer_master_detail cc,(select acNo,withdrwal,deposit,depWithdDt,operationDt,opDt,clDt from "
                        + "((select acno acNo,sum(dramt) withdrwal,sum(cramt) deposit,date_format(dt,'%d/%m/%Y') depWithdDt from " + common.getTableName(actNature) + " where " + branch + " TranType = 0  and substring(acno,3,2)='" + acType + "' and dt between '" + fromDt + "' and '" + toDt + "' group by dt,acno)a,"
                        + "(select acno acn, date_format(max(dt),'%d/%m/%Y') operationDt from " + common.getTableName(actNature) + " where dt <= '" + toDt + "' group by acno)b,"
                        + "(select acno account,date_format(OpeningDt,'%d/%m/%Y') opDt,ifnull(closingdate,'') clDt from " + table + " where " + curBranch + " accttype = '" + acType + "')c)"
                        + "where a.acno = b.acn and a.acno = c.account)fi  where fi.acno=bb.acno and bb.custid = cast(cc.customerid as unsigned)").getResultList();
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector ele1 = (Vector) result.get(i);
                        DepositLoanPojo pojo = new DepositLoanPojo();
                        pojo.setsNo(i + 1);
                        String acno = ele1.get(0).toString();
                        pojo.setAcNo(acno);
                        pojo.setAmtWithdrwaInCash(new BigDecimal(ele1.get(1).toString()));
                        pojo.setDepositedAmt(new BigDecimal(ele1.get(2).toString()));
                        String tranDt = ele1.get(3).toString();
                        if (Double.parseDouble(ele1.get(2).toString()) != 0) {
                            pojo.setDepositDt(ele1.get(3).toString()); // both dt deposit dt or withdrwal dt 
                        } else {
                            pojo.setWithdrwalDt(ele1.get(3).toString());
                        }
                        pojo.setCustName(ele1.get(4).toString());
                        pojo.setPanNo(ele1.get(5).toString());
                        pojo.setUcicUid(ele1.get(6).toString());
                        double note500 = 0, note1000 = 0;

                        List list1 = em.createNativeQuery("select denomination*denomination_value 500Note from denomination_detail  where acno = '" + acno + "' and dt ='" + ymd.format(dmyy.parse(tranDt)) + "'and denomination in (500)").getResultList();
                        if (!list1.isEmpty()) {
                            Vector vtr1 = (Vector) list1.get(0);
                            note500 = Double.parseDouble(vtr1.get(0).toString());
                            pojo.setAmtInsbn500(new BigDecimal(note500));
                        } else {
                            pojo.setAmtInsbn500(new BigDecimal("0"));

                        }
                        List list2 = em.createNativeQuery("select denomination*denomination_value 500Note from denomination_detail  where acno = '" + acno + "'and dt= '" + ymd.format(dmyy.parse(tranDt)) + "'and denomination in (1000)").getResultList();
                        if (!list2.isEmpty()) {
                            Vector vtr1 = (Vector) list2.get(0);
                            note1000 = Double.parseDouble(vtr1.get(0).toString());
                            pojo.setAmtInsbn1000(new BigDecimal(note1000));
                        } else {
                            pojo.setAmtInsbn1000(new BigDecimal("0"));
                        }
                        List lastTxnList = em.createNativeQuery("select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from " + common.getTableName(actNature) + " where acno = '" + acno + "' and dt < '" + fromDt + "' ").getResultList();
                        Vector lopVector = (Vector) lastTxnList.get(0);
                        String lastOpDt = lopVector.get(0).toString();
                        pojo.setLastOperationDt(lastOpDt);

//                        if(ymdFormat.parse(fromDt).after(dmyy.parse(ele1.get(7).toString()))){
//                             pojo.setLastOperationDt(ele1.get(7).toString());
//                        }else{
//                             pojo.setLastOperationDt("");
//                        }
                        //pojo.setLastOperationDt(ele1.get(7).toString());
                        pojo.setOpeningDt(ele1.get(8).toString());
                        String closingDt = ele1.get(9).toString();
                        if (!closingDt.trim().equalsIgnoreCase("")) {
                            closingDt = closingDt.substring(6, 8) + "/" + closingDt.substring(4, 6) + "/" + closingDt.substring(0, 4);
                        }
                        pojo.setClosingDt(closingDt);
                        pojo.setAcType(acno.substring(2, 4));
                        pojo.setPhoneNo(ele1.get(10).toString());

                        dataList.add(pojo);
                    }
                }
            } else if (RepType.equalsIgnoreCase("1") && denoType.equalsIgnoreCase("Y")) {
                if (BrnCode.equalsIgnoreCase("0A")) {
                    branch = "";
                } else {
                    branch = " and substring(acno,1,2) = '" + BrnCode + "' ";
                }
                result = em.createNativeQuery("select acno,date_format(dt,'%Y%m%d'),recno from denomination_detail  where dt between '" + fromDt + "'and '" + toDt + "' and denomination in(500,1000) " + branch + " and new_old_flag = 'o' and ty = 0 and substring(acno,3,2)<> '06' group by dt,acno,recno ").getResultList();
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        DepositLoanPojo pojo = new DepositLoanPojo();
                        Vector ele1 = (Vector) result.get(i);
                        String acno = ele1.get(0).toString();
                        String tranDt = ele1.get(1).toString();
                        double recno = Double.parseDouble(ele1.get(2).toString());
                        String accType = acno.substring(2, 4);
                        BigDecimal withdAmt = new BigDecimal("0");
                        String lastOperationDt = "", opDt = "", clDt = "", name = "", panNo = "", aadharNo = "", phoneNo = "";
                        double note500 = 0, note1000 = 0, depsitAmt = 0;
//                        actNature = ftsBean.getAcNatureByCode(accType);
//                        if (actNature.equalsIgnoreCase("FD") || actNature.equalsIgnoreCase("MS") || actNature.equalsIgnoreCase("TD")) {
//                            table = "td_accountmaster";
//                        } else {
//                            table = "accountmaster";
//                        }

//                        List list1 = em.createNativeQuery("select withdrwal,operationDt,opDt,clDt,Name,PanNo,AadharNo from ("
//                                + "(select custname Name,ifnull(pan_girnumber,'')PanNo ,ifnull(aadhaar_no,'') AadharNo from customerid bb,cbs_customer_master_detail cc where bb.acno = '" + acno + "'  and bb.custid = cast(cc.customerid as unsigned))a,"
//                                + "(select ifnull(sum(dramt),0) withdrwal from " + common.getTableName(actNature) + "  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "')b,"
//                                + "(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from " + common.getTableName(actNature) + " where acno ='" + acno + "' and dt < '" + fromDt + "')c, "
//                                + "(select date_format(OpeningDt,'%d/%m/%Y') opDt,ifnull(closingdate,'') clDt from " + table + " where acno ='" + acno + "')"
//                                + "d)").getResultList();
                        List list1 = em.createNativeQuery("select withdAmt,opDt,clDt,Name,PanNo,CustId,PhoneNo from ("
                                + "(select custname Name,ifnull(pan_girnumber,'')PanNo ,customerid CustId,ifnull(PerPhoneNumber,'') PhoneNo from customerid bb,cbs_customer_master_detail cc where bb.acno = '" + acno + "'  and bb.custid = cast(cc.customerid as unsigned))a,"
                                + "(select sum(withdrwal) withdAmt from ("
                                + "select ifnull(sum(dramt),0) withdrwal from ddstransaction  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "'"
                                + " union all "
                                + "select ifnull(sum(dramt),0) withdrwal from rdrecon  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "'"
                                + " union all "
                                + "select ifnull(sum(dramt),0) withdrwal from td_recon  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "'"
                                + " union all "
                                + "select ifnull(sum(dramt),0) withdrwal from recon  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "'"
                                + " union all "
                                + "select ifnull(sum(dramt),0) withdrwal from ca_recon  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "'"
                                + " union all "
                                + "select ifnull(sum(dramt),0) withdrwal from loan_recon  where acno ='" + acno + "' and TranType = 0 and dt = '" + tranDt + "'"
                                + ")s)b,"
                                + "/*(select operationDt from ("
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from ddstransaction where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + " union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from rdrecon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + " union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from td_recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + " union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from ca_recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + " union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + " union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from loan_recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + ")dt where operationDt <> '')c, */"
                                + "(select date_format(OpeningDt,'%d/%m/%Y') opDt,ifnull(closingdate,'') clDt from accountmaster where acno ='" + acno + "' "
                                + " union "
                                + "select date_format(OpeningDt,'%d/%m/%Y') opDt,ifnull(closingdate,'') clDt from td_accountmaster where acno ='" + acno + "')d)").getResultList();

                        if (!list1.isEmpty()) {
                            Vector ele2 = (Vector) list1.get(0);
                            withdAmt = new BigDecimal(ele2.get(0).toString());
                            // lastOperationDt = ele2.get(1).toString();
                            opDt = ele2.get(1).toString();
                            clDt = ele2.get(2).toString();
                            name = ele2.get(3).toString();
                            panNo = ele2.get(4).toString();
                            aadharNo = ele2.get(5).toString();
                            phoneNo = ele2.get(6).toString();
                        }

                        List lastTxnList = em.createNativeQuery("select operationDt from ("
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from ddstransaction where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + "union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from rdrecon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + "union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from td_recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + "union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from ca_recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + "union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + "union "
                                + "select ifnull(date_format(max(dt),'%d/%m/%Y'),'') operationDt from loan_recon where acno ='" + acno + "' and dt < '" + fromDt + "'"
                                + ")dt where operationDt <> '' ").getResultList();
                        String lastOpDt = "";
                        if (!lastTxnList.isEmpty()) {
                            Vector lopVector = (Vector) lastTxnList.get(0);
                            lastOpDt = lopVector.get(0).toString();
                        }

                        pojo.setLastOperationDt(lastOpDt);
                        pojo.setsNo(i + 1);
                        pojo.setAcNo(acno);
                        pojo.setAcType(accType);
                        pojo.setCustName(name);
                        pojo.setUcicUid(aadharNo);
                        pojo.setPanNo(panNo);
                        if (clDt.equalsIgnoreCase("")) {
                            pojo.setClosingDt(clDt);
                        } else {
                            pojo.setClosingDt(clDt.substring(6, 8) + "/" + clDt.substring(4, 6) + "/" + clDt.substring(0, 4));
                        }
                        pojo.setOpeningDt(opDt);
                        //pojo.setLastOperationDt(lastOperationDt);
                        List list3 = em.createNativeQuery("select denomination*denomination_value 500Note from denomination_detail  where acno = '" + acno + "' "
                                + "and dt ='" + tranDt + "'and denomination in (500) and new_old_flag = 'o' and ty = 0 and recno = " + recno + " ").getResultList();
                        if (!list3.isEmpty()) {
                            Vector vtr1 = (Vector) list3.get(0);
                            note500 = Double.parseDouble(vtr1.get(0).toString());
                            pojo.setAmtInsbn500(new BigDecimal(note500));
                        } else {
                            pojo.setAmtInsbn500(new BigDecimal("0"));

                        }
                        List list4 = em.createNativeQuery("select denomination*denomination_value 500Note from denomination_detail  where acno = '" + acno + "'"
                                + "and dt= '" + tranDt + "'and denomination in (1000) and new_old_flag = 'o' and ty = 0 and recno = " + recno + " ").getResultList();
                        if (!list4.isEmpty()) {
                            Vector vtr1 = (Vector) list4.get(0);
                            note1000 = Double.parseDouble(vtr1.get(0).toString());
                            pojo.setAmtInsbn1000(new BigDecimal(note1000));
                        } else {
                            pojo.setAmtInsbn1000(new BigDecimal("0"));
                        }
                        depsitAmt = note500 + note1000;
                        if (depsitAmt != 0) {
                            pojo.setDepositDt(tranDt.substring(6, 8) + "/" + tranDt.substring(4, 6) + "/" + tranDt.substring(0, 4));
                        }
                        if (withdAmt.doubleValue() != 0) {
                            pojo.setWithdrwalDt(tranDt.substring(6, 8) + "/" + tranDt.substring(4, 6) + "/" + tranDt.substring(0, 4));
                        }
                        pojo.setDepositedAmt(new BigDecimal(depsitAmt));
                        pojo.setAmtWithdrwaInCash(withdAmt);
                        pojo.setPhoneNo(phoneNo);
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return dataList;
    }

    public List<exchangeDetailPojo> getExchangeData(String RepType, String BrnCode, String fromDt, String toDt, String acType, String denoType) throws ApplicationException {
        List<exchangeDetailPojo> dataList = new ArrayList<exchangeDetailPojo>();
        try {
            List result = new ArrayList();

            if (BrnCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select tenderer_name,idno,den500,den1000,total,date_format(entrydate,'%d/%m/%Y'),recno from money_exchange_details "
                        + "where recno !=0 and date_format(entrydate,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "'").getResultList();
            } else {
                result = em.createNativeQuery("select tenderer_name,idno,den500,den1000,total,date_format(entrydate,'%d/%m/%Y'),recno from money_exchange_details "
                        + "where recno !=0 and brcode = '" + BrnCode + "' and date_format(entrydate,'%Y%m%d') between '" + fromDt + "' and '" + toDt + "'").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    exchangeDetailPojo pojo = new exchangeDetailPojo();
                    String name = ele.get(0).toString();
                    String idNo = ele.get(1).toString();
                    int deno500 = Integer.parseInt(ele.get(2).toString());
                    int deno1000 = Integer.parseInt(ele.get(3).toString());
                    double totalAmt = Double.parseDouble(ele.get(4).toString());
                    String exDt = ele.get(5).toString();
                    double recno = Double.parseDouble(ele.get(6).toString());
                    double amt500Deno = deno500 * 500;
                    double amt1000Deno = deno1000 * 1000;

                    pojo.setsNo(i + 1);
                    pojo.setCustName(name);
                    pojo.setAnnexV("");
                    pojo.setExchangeDt(exDt);
                    pojo.setAmt500(new BigDecimal(amt500Deno));
                    pojo.setAmt1000(new BigDecimal(amt1000Deno));
                    if (idNo.length() == 10) {
                        pojo.setPanNo(idNo);
                    } else {
                        pojo.setPanNo("");
                    }

                    pojo.setTotalAmt(new BigDecimal(totalAmt));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<ctrMoreThan1Crore> getDemandBankersChecqueAllStatusData(String RepType, String BrnCode, String status, String fromDt, String toDt, BigDecimal amount, String trnCrType, String trnDrType) throws ApplicationException {
        List<ctrMoreThan1Crore> dataList = new ArrayList<ctrMoreThan1Crore>();
        List result = new ArrayList();

        try {
            String branch = "";
            if (BrnCode.equalsIgnoreCase("0A")) {
                branch = "90";
            } else {
                branch = BrnCode;
            }

            List list = em.createNativeQuery("select ifnull(fiureid,'BANUCXXXXX') from branchmaster where brnCode = " + branch + " ").getResultList();
            Vector fiuVector = (Vector) list.get(0);

            if (BrnCode.equalsIgnoreCase("0A")) {
                if (status.equalsIgnoreCase("A")) {
                    // result = em.createNativeQuery("select acno,CUSTNAME,sum(AMOUNT),INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where  trantype in(" + trnCrType + ") and ORIGINDT between '" + fromDt + "' and '" + toDt + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + " ").getResultList();
                    result = em.createNativeQuery("select acno,trim(CUSTNAME),AMOUNT,INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where ORIGINDT between '" + fromDt + "' and '" + toDt + "' and CUSTNAME in(select distinct CUSTNAME from bill_po where  trantype in(" + trnCrType + ") and ORIGINDT between '" + fromDt + "' and '" + toDt + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + ") order by CUSTNAME,INFAVOUROF,ORIGINDT ").getResultList();
                } else {
                    // result = em.createNativeQuery("select acno,CUSTNAME,sum(AMOUNT),INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where  trantype in(" + trnCrType + ") and status = '" + status + "' and ORIGINDT between '" + fromDt + "' and '" + toDt + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + " ").getResultList();
                    result = em.createNativeQuery("select acno,trim(CUSTNAME),AMOUNT,INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where ORIGINDT between '" + fromDt + "' and '" + toDt + "' and CUSTNAME in(select distinct CUSTNAME from bill_po where  trantype in(" + trnCrType + ") and ORIGINDT between '" + fromDt + "' and '" + toDt + "' and status = '" + status + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + ")  and status = '" + status + "' order by CUSTNAME,INFAVOUROF,ORIGINDT").getResultList();
                }
            } else {
                if (status.equalsIgnoreCase("A")) {
                    //result = em.createNativeQuery("select acno,CUSTNAME,sum(AMOUNT),INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where  trantype in(" + trnCrType + ") and substring(acno,1,2) = '" + BrnCode + "' and ORIGINDT between '" + fromDt + "' and '" + toDt + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + " ").getResultList();
                    result = em.createNativeQuery("select acno,trim(CUSTNAME),AMOUNT,INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where ORIGINDT between '" + fromDt + "' and '" + toDt + "' and CUSTNAME in(select distinct CUSTNAME from bill_po where  trantype in(0,1,2) and ORIGINDT between '" + fromDt + "' and '" + toDt + "' and substring(acno,1,2) = '" + BrnCode + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + ") and substring(acno,1,2) = '" + BrnCode + "' order by CUSTNAME,INFAVOUROF,ORIGINDT").getResultList();
                } else {
                    //result = em.createNativeQuery("select acno,CUSTNAME,sum(AMOUNT),INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where  trantype in(" + trnCrType + ") and substring(acno,1,2) = '" + BrnCode + "' and status = '" + status + "' and ORIGINDT between '" + fromDt + "' and '" + toDt + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + " ").getResultList();
                    result = em.createNativeQuery("select acno,trim(CUSTNAME),AMOUNT,INFAVOUROF,ifnull(PLACE,''),date_format(ORIGINDT,'%d/%m/%Y') issueDate from bill_po where ORIGINDT between '" + fromDt + "' and '" + toDt + "' and CUSTNAME in(select distinct CUSTNAME from bill_po where  trantype in(0,1,2) and ORIGINDT between '" + fromDt + "' and '" + toDt + "' and substring(acno,1,2) = '" + BrnCode + "' and status = '" + status + "' group by CUSTNAME having(sum(AMOUNT))>= " + amount + ") and status = '" + status + "' and substring(acno,1,2) = '" + BrnCode + "' order by CUSTNAME,INFAVOUROF,ORIGINDT").getResultList();
                }
            }
            String prevAcno = "";
            int cnt = 0;
            double toAmt = 0;

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    ctrMoreThan1Crore pojo = new ctrMoreThan1Crore();
                    String glAcno = vtr.get(0).toString();
                    String nameAcno = vtr.get(1).toString();
                    double amt = Double.parseDouble(vtr.get(2).toString());
                    String payeeName = vtr.get(3).toString();
                    String payeeCity = vtr.get(4).toString();
                    String issueDt = vtr.get(5).toString();
                    List<ArfBrcPojo> arfBrcPojos = ctrStrRemote.getArfBrcData(glAcno.substring(0, 2), "STR");  // For Branch information
                    pojo.setBrachName(arfBrcPojos.get(0).getBranchName());
                    pojo.setBrachAdd(arfBrcPojos.get(0).getAddress());
                    pojo.setBrachPinCode(arfBrcPojos.get(0).getPinCode());
                    pojo.setReportingEntityName("BANUC");
                    pojo.setFiureid(fiuVector.get(0).toString());
                    if (nameAcno.length() == 12) {
                        if (nameAcno.substring(10).equalsIgnoreCase("01")) {
                            pojo.setAccountNumber(nameAcno);
                            List aplList = em.createNativeQuery("select custname,PerAddressLine1,ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail a,(select custid from customerid where acno = '" + nameAcno + "')b where cast(a.customerid as unsigned) = b.custid").getResultList();
                            Vector listVector = (Vector) aplList.get(0);
                            pojo.setAccountHolderName(listVector.get(0).toString());
                            pojo.setAccountHoldersAddress(listVector.get(1).toString());
                            pojo.setAccountHoldersPAN(listVector.get(2).toString());
                        } else {
                            pojo.setAccountHolderName(nameAcno);
                            pojo.setAccountNumber("");
                        }
                    } else {
                        pojo.setAccountHolderName(nameAcno);
                        pojo.setAccountNumber("");
                    }
                    pojo.setPayeeName(payeeName);
                    pojo.setPayeeCity(payeeCity);
                    pojo.setPayeeAdd("");
//                    List ddList = em.createNativeQuery("select count(*) from bill_po where CUSTNAME = '" + nameAcno + "' and ORIGINDT between '" + fromDt + "' and '" + toDt + "'").getResultList();
//                    Vector ddVector = (Vector) ddList.get(0);
//                    pojo.setNoOfCancelPoDo(ddVector.get(0).toString());
//                    List ddList1 = em.createNativeQuery("select date_format(max(ORIGINDT),'%d/%m/%Y') ,sum(amount) from bill_po where CUSTNAME = '" + nameAcno + "' and ORIGINDT in(select date_format(max(ORIGINDT),'%Y%m%d') from bill_po where CUSTNAME = '" + nameAcno + "' and ORIGINDT between '" + fromDt + "' and '" + toDt + "')").getResultList();
//                    Vector ddVector1 = (Vector) ddList1.get(0);
//                    pojo.setAcOpenDuringDt(ddVector1.get(0).toString());
//                    pojo.setValueOfCancelPoDo(new BigDecimal(ddVector1.get(1).toString()));
                    pojo.setNoOfCancelPoDo("1");
                    pojo.setAcOpenDuringDt(issueDt);
                    pojo.setValueOfCancelPoDo(new BigDecimal(amt));

                    if (prevAcno.trim().equalsIgnoreCase(nameAcno.trim())) {
                        toAmt = toAmt + amt;
                    } else {
                        toAmt = amt;
                    }
                    prevAcno = nameAcno;
                    pojo.setTotalValueddPo(new BigDecimal(toAmt));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;

    }

    public List<ctrMoreThan1Crore> getReactivatedDormantAccountData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount, String trnCrType, String trnDrType) throws ApplicationException {
        List<ctrMoreThan1Crore> dataList = new ArrayList<ctrMoreThan1Crore>();
        String branch = "";

        try {
            if (BrnCode.length() > 4) {
                branch = "90";
            } else {
                branch = BrnCode;
            }
            List list = em.createNativeQuery("select ifnull(fiureid,'BANUCXXXXX') from branchmaster where brnCode = " + branch + " ").getResultList();
            Vector fiuVector = (Vector) list.get(0);

            List result = em.createNativeQuery("select a.acno as acno, ac.custname,atm.acctnature,reconCr.depositAmt as depositAmout "
                    + "  from accountstatus a, "
                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + BrnCode + ")) npa, "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                    + " (select acno,cast(sum(cramt) as decimal(25,2)) as depositAmt from recon where dt between '" + fromDt + "' and '" + toDt + "' and tranType in(" + trnCrType + ") group by acno having(sum(cramt)) > " + amount + " order by depositAmt) reconCr, "
                    + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno "
                    + " and  ac.acno = reconCr.acno "
                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and "
                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and "
                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB')) "
                    + " union all  "
                    + " select a.acno as acno, ac.custname,atm.acctnature,reconCr.depositAmt as depositAmout "
                    + " from accountstatus a, "
                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + CbsUtil.dateAdd(fromDt, -1) + "' and SPFLAG IN (2)  group by acno) b "
                    + " where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (2) "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno  and  substring(ast.acno,1,2) in (" + BrnCode + ")) npa, "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' group by acno) c , "
                    + " (select acno,cast(sum(cramt) as decimal(25,2)) as depositAmt from ca_recon where dt between '" + fromDt + "' and '" + toDt + "' and tranType in(" + trnCrType + ") group by acno having(sum(cramt)) > " + amount + " order by depositAmt) reconCr, "
                    + " accountmaster ac, accounttypemaster atm, codebook cb  where  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                    + " and  ac.acno = reconCr.acno  "
                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (2) and  "
                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + CbsUtil.dateAdd(fromDt, -1) + "' and  "
                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + CbsUtil.dateAdd(fromDt, -1) + "')  AND substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB'))").getResultList();

            double nonCash = 0;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector element = (Vector) result.get(i);
                    ctrMoreThan1Crore pojo = new ctrMoreThan1Crore();
                    String acNo = element.get(0).toString();
                    String custName = element.get(1).toString();
                    String acctNature = element.get(2).toString();
                    double totalDepositAmt = Double.parseDouble(element.get(3).toString());
                    double withdrawalAmt = 0, cashAmt = 0;
//                    double totalDepositAmt = Double.parseDouble(element.get(5).toString());
//                    nonCash = totalDepositAmt - cashAmt;
                    String panNo = "", address = "", city = "", voterId = "", drivLicence = "", passPortNo = "", uinAadharNo = "";
//                    System.out.println(acNo);

                    List custList = em.createNativeQuery("select PAN_GIRNumber,PerAddressLine1,REF_Desc,ifnull(AADHAAR_NO,''), ifnull(VoterIDNo,''), "
                            + " ifnull(DrivingLicenseNo,''), ifnull(PassportNo,'') from cbs_customer_master_detail cmd,customerid cui,cbs_ref_rec_type rft where cast(cmd.customerid as unsigned) = cui.custid and cui.acno = '" + acNo + "' and rft.REF_REC_NO = '001' and rft.REF_CODE = cmd.PerCityCode").getResultList();
                    if (!custList.isEmpty()) {
                        Vector cuVector = (Vector) custList.get(0);
                        panNo = cuVector.get(0).toString();
                        address = cuVector.get(1).toString();
                        city = cuVector.get(2).toString();
                        uinAadharNo = cuVector.get(3).toString();
                        voterId = cuVector.get(4).toString();
                        drivLicence = cuVector.get(5).toString();
                        passPortNo = cuVector.get(6).toString();

                        /*Permissible values are:
                         * A - Passport
                         * B - Election ID Card
                         * C - Pan Card
                         * D - ID Card
                         * E - Driving License
                         * F - Account Introducer
                         * G - UIDAI Letter
                         * H - NREGA job card
                         * Z – Others*/
                        if (!voterId.equalsIgnoreCase("")) {
                            pojo.setIdentificationDocumentType("B");
                            pojo.setIdentificationDocumentNumber(voterId);
                        } else if (!drivLicence.equalsIgnoreCase("")) {
                            pojo.setIdentificationDocumentType("E");
                            pojo.setIdentificationDocumentNumber(drivLicence);
                        } else if (!passPortNo.equalsIgnoreCase("")) {
                            pojo.setIdentificationDocumentType("A");
                            pojo.setIdentificationDocumentNumber(passPortNo);
                        } else if (!uinAadharNo.equalsIgnoreCase("")) {
                            pojo.setIdentificationDocumentType("G");
                            pojo.setIdentificationDocumentNumber(uinAadharNo);
                        } else if (!panNo.equalsIgnoreCase("")) {
                            pojo.setIdentificationDocumentType("C");
                            pojo.setIdentificationDocumentNumber(panNo);
                        }
                    }

                    /*  Account Type
                     BS - Savings Account
                     BC - Current Account
                     BR - Cash Credit/Overdraft Account
                     BD - Credit Card Account
                     BP - Prepaid Card Account
                     BL - Loan Account
                     BT - Term Deposit Account
                     BG – Letter of Credit/Bank Guarantee
                     ZZ - Others
                     XX - Not Categorised
                     */
                    if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        pojo.setAccountType("BC");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        pojo.setAccountType("BR");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        pojo.setAccountType("BS");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.TERM_LOAN) || common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        pojo.setAccountType("BL");
                    } else if (common.getAcctNature(acNo.substring(2, 4)).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                        pojo.setAccountType("BT");
                    } else {
                        pojo.setAccountType("ZZ");
                    }

                    List<ArfBrcPojo> arfBrcPojos = ctrStrRemote.getArfBrcData(acNo.substring(0, 2), "STR");  // For Branch information
                    pojo.setBrachName(arfBrcPojos.get(0).getBranchName());
                    pojo.setBrachAdd(arfBrcPojos.get(0).getAddress());
                    pojo.setBrachPinCode(arfBrcPojos.get(0).getPinCode());
                    pojo.setBranchCity(arfBrcPojos.get(0).getCity());
                    pojo.setBrachDistrict(arfBrcPojos.get(0).getCity());
                    pojo.setBrachState(arfBrcPojos.get(0).getStateCode());
                    pojo.setReportingEntityName("BANUC");
                    pojo.setFiureid(fiuVector.get(0).toString());
                    pojo.setAccountNumber(acNo);
                    pojo.setAccountHolderName(custName);
                    pojo.setAccountHoldersPAN(panNo);
                    pojo.setAccountHoldersAddress(address);
                    pojo.setAccountHoldersCity(city);

                    List acNoCashCrList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0)),0) as decimal) as cramt from " + CbsUtil.getReconTableName(acctNature) + " where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype in (0) ").getResultList();
                    if (!acNoCashCrList.isEmpty()) {
                        Vector acNoCashCrVect = (Vector) acNoCashCrList.get(0);
                        cashAmt = Double.parseDouble(acNoCashCrVect.get(0).toString());
                    }

                    List acNoTotalDrList = em.createNativeQuery("select cast(ifnull(sum(ifnull(dramt,0)),0) as decimal) as cramt from " + CbsUtil.getReconTableName(acctNature) + " where acno = '" + acNo + "' and dt between '" + fromDt + "' and '" + toDt + "' ").getResultList();
                    if (!acNoTotalDrList.isEmpty()) {
                        Vector acNoTotalCashDrVect = (Vector) acNoTotalDrList.get(0);
                        withdrawalAmt = Double.parseDouble(acNoTotalCashDrVect.get(0).toString());
                    }

                    pojo.setBalanceInAcno(new BigDecimal(cashAmt));  //  Cash deposited from 8th Nov to 30th Dec 2016 
                    pojo.setCumulativeAmountOfCashDeposited(new BigDecimal(totalDepositAmt - cashAmt)); //  Non-cash  deposited by other means (i.e Transfer, Clearing, RTGS, NEFT, IMPS etc) from 8th Nov to 30th Dec 2016
                    pojo.setTotalValueddPo(new BigDecimal(totalDepositAmt));                  //  Cumulative Amount of ALL Deposits fom 8th Nov to 30th Dec 2016 (Column 16 + 17)
                    pojo.setCumulativeAmountOfCashWithdrawn(new BigDecimal(withdrawalAmt)); //  Cumulative Amount of withdrawals fom 8th Nov to 30th Dec 2016

                    dataList.add(pojo);

                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<Form60ReportPojo> getFormSixtyDetail(String brnCode, String frmDate, String toDate, String opt) throws ApplicationException {
        List<Form60ReportPojo> frmSixDtl = new ArrayList<Form60ReportPojo>();
        try {
            int cnt = 0;
            List acDtlLst = new ArrayList();
            //Account With Form 60 Open Between The Given Period SL No - 2 Rule 114B
            if (brnCode.equalsIgnoreCase("0A")) {
                acDtlLst = em.createNativeQuery("select cd.customerid,cd.custname,ifnull(cd.middle_name,''),ifnull(cd.last_name,''),"
                        + " date_format(cd.dateofbirth,'%d/%m/%Y'),ifnull(cd.fathername,''),"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cd.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from "
                        + " cbs_customer_master_detail where customerid = cd.customerid and legal_document = 'E' and CustEntityType = '01'),"
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa, cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = cd.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ), "
                        + " ifnull(cd.AADHAAR_NO,'')) as AadhaarNo,ifnull(cd.PerAddressLine1,''),"
                        + " ifnull(cd.peraddressline2,''),ifnull(cd.PerBlock,''),ifnull(cd.PerVillage,''),ifnull(cd.PerCountryCode,''),"
                        + " ifnull(cd.PerPostalCode,''),ifnull(cd.PerStateCode,''),ifnull(cd.mobilenumber,''),c.acno,"
                        + " case cd.legal_document when 'DL' then '05' when 'NRE' then '08' when 'PAS' then '06' when 'VIC' then '03' "
                        + " else '' end,ifnull(cd.drivingLicenseNo,''),ifnull(cd.nrega_job_card,''),ifnull(cd.passportNo,''),"
                        + " ifnull(cd.VoterIdNo,'') from customerid c,"
                        + " cbs_customer_master_detail cd where c.acno in (select acno from accountmaster where openingdt "
                        + " between '" + frmDate + "' and '" + toDate + "' and (closingdate is null or closingdate ='' or "
                        + " closingdate > '" + toDate + "') and accttype not in (select acctcode from accounttypemaster where "
                        + " acctnature in ('RD'))) and c.custid = cast(cd.customerid as unsigned) "
                        + " and pan_girnumber = 'Form 60'").getResultList();
            } else {
                acDtlLst = em.createNativeQuery("select cd.customerid,cd.custname,ifnull(cd.middle_name,''),ifnull(cd.last_name,''),"
                        + " date_format(cd.dateofbirth,'%d/%m/%Y'),ifnull(cd.fathername,''),"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cd.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from "
                        + " cbs_customer_master_detail where customerid = cd.customerid and legal_document = 'E' and CustEntityType = '01'),"
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa, cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = cd.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ), "
                        + " ifnull(cd.AADHAAR_NO,'')) as AadhaarNo, ifnull(cd.PerAddressLine1,''),"
                        + " ifnull(cd.peraddressline2,''),ifnull(cd.PerBlock,''),ifnull(cd.PerVillage,''),ifnull(cd.PerCountryCode,''),"
                        + " ifnull(cd.PerPostalCode,''),ifnull(cd.PerStateCode,''),ifnull(cd.mobilenumber,''),c.acno,"
                        + " case cd.legal_document when 'DL' then '05' when 'NRE' then '08' when 'PAS' then '06' when 'VIC' then '03' "
                        + " else '' end,ifnull(cd.drivingLicenseNo,''),ifnull(cd.nrega_job_card,''),ifnull(cd.passportNo,''),"
                        + " ifnull(cd.VoterIdNo,'') from customerid c, "
                        + " cbs_customer_master_detail cd where c.acno in (select acno from accountmaster where openingdt "
                        + " between '" + frmDate + "' and '" + toDate + "' and (closingdate is null or closingdate ='' or "
                        + " closingdate > '" + toDate + "') and curbrcode = '" + brnCode + "' and accttype not in (select acctcode "
                        + " from accounttypemaster where acctnature in ('RD'))) and c.custid = cast(cd.customerid as unsigned) "
                        + " and pan_girnumber = 'Form 60'").getResultList();
            }

            if (!acDtlLst.isEmpty()) {
                for (int a = 0; a < acDtlLst.size(); a++) {
                    Vector acVec = (Vector) acDtlLst.get(a);
                    String acNo = acVec.get(15).toString();

                    cnt = cnt + 1;

                    String crAmt = "", txnType = "", txnDt = "", txnRec = "";
                    String acNat = ftsPosting.getAccountNature(acNo);
                    String tableName = common.getTableName(acNat);

                    List acTrnLst;
                    if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        acTrnLst = em.createNativeQuery("select ifnull(dramt,0),case trantype when 0 then '01' when 1 then '02' else '06' end"
                                + " ,date_format(min(dt),'%d/%m/%Y'),ifnull(recno,0) from " + tableName + " where acno = '" + acNo + "'").getResultList();
                    } else {
                        acTrnLst = em.createNativeQuery("select ifnull(cramt,0),case trantype when 0 then '01' when 1 then '02' else '06' end"
                                + " ,date_format(min(dt),'%d/%m/%Y'),ifnull(recno,0) from " + tableName + " where acno = '" + acNo + "'").getResultList();
                    }

                    if (!acTrnLst.isEmpty()) {
                        for (int b = 0; b < acTrnLst.size(); b++) {
                            Vector acTrnVec = (Vector) acTrnLst.get(b);
                            crAmt = acTrnVec.get(0).toString();
                            txnType = acTrnVec.get(1).toString();
                            txnRec = acTrnVec.get(3).toString();
                            if (acTrnVec.get(2) == null) {
                                txnDt = "";
                            } else {
                                txnDt = acTrnVec.get(2).toString();
                            }

                        }
                    }

                    Form60ReportPojo frmPojo = new Form60ReportPojo();

                    frmPojo.setRepSrNo(cnt);
                    frmPojo.setOrgRepSrNo(cnt);
                    frmPojo.setCustId(acVec.get(0).toString());
                    frmPojo.setCustName(acVec.get(1).toString() + acVec.get(2).toString() + acVec.get(3).toString());
                    frmPojo.setDob(acVec.get(4).toString());
                    frmPojo.setFatherName(acVec.get(5).toString());
                    frmPojo.setPanAck("");
                    frmPojo.setAadharNo(acVec.get(6).toString());

                    String idnTp = acVec.get(16).toString();
                    String idnNo = "";
                    if (acVec.get(16).toString().equalsIgnoreCase("05")) {
                        idnNo = acVec.get(17).toString();
                    } else if (acVec.get(16).toString().equalsIgnoreCase("08")) {
                        idnNo = acVec.get(18).toString();
                    } else if (acVec.get(16).toString().equalsIgnoreCase("06")) {
                        idnNo = acVec.get(19).toString();
                    } else if (acVec.get(16).toString().equalsIgnoreCase("03")) {
                        idnNo = acVec.get(20).toString();
                    } else if (!acVec.get(6).toString().equalsIgnoreCase("")) {
                        idnNo = acVec.get(6).toString();
                        idnTp = "01";
                    }

                    frmPojo.setIdenType(idnTp);
                    frmPojo.setIdenNo(idnNo);
                    frmPojo.setFlatNo(acVec.get(7).toString());
                    frmPojo.setNameOfPremises("");
                    frmPojo.setStreetName("");
                    frmPojo.setArea(acVec.get(8).toString());
                    frmPojo.setCity(acVec.get(10).toString());
                    frmPojo.setPostalCode(acVec.get(12).toString().equalsIgnoreCase("") ? "XXXXXX" : acVec.get(12).toString());
                    frmPojo.setStateCode(acVec.get(13).toString());
                    frmPojo.setCountryCode(acVec.get(11).toString().equalsIgnoreCase("IND") ? "IN" : acVec.get(11).toString());
                    frmPojo.setMobile(acVec.get(14).toString());
                    frmPojo.setEstAgrIncom("");
                    frmPojo.setEstNonAgrIncom("");
                    frmPojo.setRemarks("");
                    frmPojo.setForm60Ack("");
                    frmPojo.setTxnDate(txnDt);
                    frmPojo.setTxnId(txnRec);
                    frmPojo.setTxnType("09");
                    frmPojo.setTxnAmount(crAmt);
                    frmPojo.setTxnMode(txnType);

                    frmSixDtl.add(frmPojo);
                }
            }

            //SL No - 10 Rule 114B Deposits
            List depLst = new ArrayList();
            depLst = em.createNativeQuery("select b.custid,c.custname,ifnull(c.middle_name,''),ifnull(c.last_name,''),"
                    + " date_format(c.dateofbirth,'%d/%m/%Y'),ifnull(c.fathername,''),"
                    + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from "
                    + " cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'),"
                    + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa, cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ), "
                    + " ifnull(c.AADHAAR_NO,'')) as AadhaarNo,"
                    + " ifnull(c.PerAddressLine1,''),ifnull(c.peraddressline2,''),ifnull(c.PerBlock,''),ifnull(c.PerVillage,''),"
                    + " ifnull(c.PerCountryCode,''),ifnull(c.PerPostalCode,''),ifnull(c.PerStateCode,''),"
                    + " ifnull(c.mobilenumber,''),date_format(b.dt,'%d/%m/%Y'),sum(b.cramt),case c.legal_document when 'DL' then '05' "
                    + " when 'NRE' then '08' when 'PAS' then '06' when 'VIC' then '03' else '' end,"
                    + " ifnull(c.drivingLicenseNo,''),ifnull(c.nrega_job_card,''),ifnull(c.passportNo,''),ifnull(c.VoterIdNo,'') from (select r.acno,r.cramt,r.dt,c.custid from "
                    + " recon r,customerid c where r.trantype = 0 and r.dt between '" + frmDate + "' and '" + toDate + "' "
                    + " and r.cramt <>0 and r.acno = c.acno union select r.acno,r.cramt,r.dt,c.custid from ca_recon r,"
                    + " customerid c where r.trantype = 0 and r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 "
                    + " and r.acno = c.acno union select r.acno,r.cramt,r.dt,c.custid from loan_recon r, customerid c "
                    + " where r.trantype = 0 and r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 "
                    + " and r.acno = c.acno union select r.acno,r.cramt,r.dt,c.custid from rdrecon r,customerid c where "
                    + " r.trantype = 0 and r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 and r.acno = c.acno "
                    + " union select r.acno,r.cramt,r.dt,c.custid from ddstransaction r,customerid c where r.trantype = 0 "
                    + " and r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 and r.acno = c.acno union "
                    + " select r.acno,r.cramt,r.dt,c.custid from td_recon r,customerid c where r.trantype = 0 and r.dt "
                    + " between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 and r.trantype <> 27 and r.acno = c.acno) b,"
                    + " cbs_customer_master_detail c where c.customerid = b.custid and c.pan_girnumber = 'Form 60' "
                    + " group by c.custname,ifnull(c.middle_name,''),ifnull(c.last_name,''),"
                    + " date_format(c.dateofbirth,'%d/%m/%Y'),ifnull(c.fathername,''),ifnull(c.AADHAAR_NO,''),"
                    + " ifnull(c.PerAddressLine1,''),ifnull(c.peraddressline2,''),ifnull(c.PerBlock,''),ifnull(c.PerCityCode,''),"
                    + " ifnull(c.PerCountryCode,''),ifnull(c.PerPostalCode,''),ifnull(c.PerStateCode,''),"
                    + " ifnull(c.PerPhoneNumber,''),b.custid,date_format(b.dt,'%d/%m/%Y') having sum(b.cramt)> '50000'").getResultList();

            if (!depLst.isEmpty()) {
                for (int c = 0; c < depLst.size(); c++) {
                    cnt = cnt + 1;
                    Vector depVec = (Vector) depLst.get(c);
                    String custId = depVec.get(0).toString();
                    String tDt = ymdFormat.format(dmyy.parse(depVec.get(15).toString()));
                    List txnLst = em.createNativeQuery("select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno from recon r,customerid c "
                            + " where r.trantype = 0 and r.dt = '" + tDt + "' and r.cramt <>0 and r.acno = c.acno and "
                            + " c.custid = '" + custId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno from ca_recon r,"
                            + " customerid c where r.trantype = 0 and r.dt = '" + tDt + "' and r.cramt <>0 and r.acno = c.acno "
                            + " and c.custid = '" + custId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno from "
                            + " loan_recon r, customerid c where r.trantype = 0 and r.dt = '" + tDt + "' and r.cramt <>0 "
                            + " and r.acno = c.acno and c.custid = '" + custId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,"
                            + " r.trantime,r.org_brnid,r.recno from rdrecon r,customerid c where r.trantype = 0 and r.dt = '" + tDt + "' and r.cramt <>0 "
                            + " and r.acno = c.acno and c.custid = '" + custId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno "
                            + " from ddstransaction r,customerid c where r.trantype = 0 and r.dt = '" + tDt + "' and r.cramt <>0 "
                            + " and r.acno = c.acno and c.custid = '" + custId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno "
                            + " from td_recon r,customerid c where r.trantype = 0 and r.dt = '" + tDt + "' and r.cramt <>0 "
                            + " and r.trantype <> 27 and r.acno = c.acno and c.custid = '" + custId + "' order by 5").getResultList();

                    double totCr = 0;
                    String txnDt = "", txnAmt = "", orgCd = "", depTxRec = "";
                    for (int d = 0; d < txnLst.size(); d++) {
                        Vector txVec = (Vector) txnLst.get(d);
                        totCr = totCr + Double.parseDouble(txVec.get(1).toString());
                        if (totCr > 50000) {
                            txnDt = txVec.get(2).toString();
                            txnAmt = txVec.get(1).toString();
                            orgCd = txVec.get(5).toString();
                            depTxRec = txVec.get(6).toString();
                            break;
                        }
                    }

                    if (brnCode.equals("0A") || brnCode.equalsIgnoreCase(orgCd)) {
                        Form60ReportPojo frmPojo = new Form60ReportPojo();

                        frmPojo.setRepSrNo(cnt);
                        frmPojo.setOrgRepSrNo(cnt);
                        frmPojo.setCustId(depVec.get(0).toString());
                        frmPojo.setCustName(depVec.get(1).toString() + depVec.get(2).toString() + depVec.get(3).toString());
                        frmPojo.setDob(depVec.get(4).toString());
                        frmPojo.setFatherName(depVec.get(5).toString());
                        frmPojo.setPanAck("");
                        frmPojo.setAadharNo(depVec.get(6).toString());

                        String idnTp = depVec.get(17).toString();
                        String idnNo = "";
                        if (depVec.get(17).toString().equalsIgnoreCase("05")) {
                            idnNo = depVec.get(18).toString();
                        } else if (depVec.get(17).toString().equalsIgnoreCase("08")) {
                            idnNo = depVec.get(19).toString();
                        } else if (depVec.get(17).toString().equalsIgnoreCase("06")) {
                            idnNo = depVec.get(20).toString();
                        } else if (depVec.get(17).toString().equalsIgnoreCase("03")) {
                            idnNo = depVec.get(21).toString();
                        } else if (!depVec.get(6).toString().equalsIgnoreCase("")) {
                            idnNo = depVec.get(6).toString();
                            idnTp = "01";
                        }

                        frmPojo.setIdenType(idnTp);
                        frmPojo.setIdenNo(idnNo);
                        frmPojo.setFlatNo(depVec.get(7).toString());
                        frmPojo.setNameOfPremises("");
                        frmPojo.setStreetName("");
                        frmPojo.setArea(depVec.get(8).toString());
                        frmPojo.setCity(depVec.get(10).toString());
                        frmPojo.setPostalCode(depVec.get(12).toString().equalsIgnoreCase("") ? "XXXXXX" : depVec.get(12).toString());
                        frmPojo.setStateCode(depVec.get(13).toString());
                        frmPojo.setCountryCode(depVec.get(11).toString().equalsIgnoreCase("IND") ? "IN" : depVec.get(11).toString());
                        frmPojo.setMobile(depVec.get(14).toString());
                        frmPojo.setEstAgrIncom("");
                        frmPojo.setEstNonAgrIncom("");
                        frmPojo.setRemarks("");
                        frmPojo.setForm60Ack("");
                        frmPojo.setTxnDate(txnDt);
                        frmPojo.setTxnId(depTxRec);
                        frmPojo.setTxnType("06");
                        frmPojo.setTxnAmount(txnAmt);
                        frmPojo.setTxnMode("01");

                        frmSixDtl.add(frmPojo);
                    }
                }
            }

            if (opt.equalsIgnoreCase("Y")) {
                //SL No - 10 Rule 114B Deposits FOR  250000 During Period

                String prdFrDt = "20161109";
                String prdToDt = "20161230";

                List prdDepLst = new ArrayList();
                prdDepLst = em.createNativeQuery("select b.custid,c.custname,ifnull(c.middle_name,''),ifnull(c.last_name,''),"
                        + " date_format(c.dateofbirth,'%d/%m/%Y'),ifnull(c.fathername,''),"
                        + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from "
                        + " cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'),"
                        + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa, cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ), "
                        + " ifnull(c.AADHAAR_NO,'')) as AadhaarNo,"
                        + " ifnull(c.PerAddressLine1,''),ifnull(c.peraddressline2,''),ifnull(c.PerBlock,''),ifnull(c.PerVillage,''),"
                        + " ifnull(c.PerCountryCode,''),ifnull(c.PerPostalCode,''),ifnull(c.PerStateCode,''),"
                        + " ifnull(c.mobilenumber,''),sum(b.cramt),case c.legal_document when 'DL' then '05' when 'NRE' then '08' "
                        + " when 'PAN' then '15' when 'PAS' then '06' when 'VIC' then '03' else '' end, "
                        + " ifnull(c.drivingLicenseNo,''),ifnull(c.nrega_job_card,''),ifnull(c.passportNo,''),ifnull(c.VoterIdNo,'') from (select r.acno,r.cramt,c.custid from "
                        + " recon r,customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' "
                        + " and r.cramt <>0 and r.acno = c.acno union select r.acno,r.cramt,c.custid from ca_recon r,"
                        + " customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 "
                        + " and r.acno = c.acno union select r.acno,r.cramt,c.custid from loan_recon r, customerid c "
                        + " where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 "
                        + " and r.acno = c.acno union select r.acno,r.cramt,c.custid from rdrecon r,customerid c where "
                        + " r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 and r.acno = c.acno "
                        + " union select r.acno,r.cramt,c.custid from ddstransaction r,customerid c where r.trantype = 0 "
                        + " and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 and r.acno = c.acno union "
                        + " select r.acno,r.cramt,c.custid from td_recon r,customerid c where r.trantype = 0 and r.dt "
                        + " between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 and r.trantype <> 27 and r.acno = c.acno) b,"
                        + " cbs_customer_master_detail c where c.customerid = b.custid and c.pan_girnumber = 'Form 60' "
                        + " group by c.custname,ifnull(c.middle_name,''),ifnull(c.last_name,''),"
                        + " date_format(c.dateofbirth,'%d/%m/%Y'),ifnull(c.fathername,''),ifnull(c.AADHAAR_NO,''),"
                        + " ifnull(c.PerAddressLine1,''),ifnull(c.peraddressline2,''),ifnull(c.PerBlock,''),ifnull(c.PerCityCode,''),"
                        + " ifnull(c.PerCountryCode,''),ifnull(c.PerPostalCode,''),ifnull(c.PerStateCode,''),"
                        + " ifnull(c.PerPhoneNumber,''),b.custid having sum(b.cramt)> '250000'").getResultList();

                if (!prdDepLst.isEmpty()) {
                    for (int e = 0; e < prdDepLst.size(); e++) {
                        cnt = cnt + 1;
                        Vector prdDepVec = (Vector) prdDepLst.get(e);
                        String prdCustId = prdDepVec.get(0).toString();

                        List prdTxnLst = em.createNativeQuery("select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno from recon r,customerid c "
                                + " where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 and r.acno = c.acno and "
                                + " c.custid = '" + prdCustId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno from ca_recon r,"
                                + " customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 and r.acno = c.acno "
                                + " and c.custid = '" + prdCustId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno from "
                                + " loan_recon r, customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 "
                                + " and r.acno = c.acno and c.custid = '" + prdCustId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,"
                                + " r.trantime,r.org_brnid,r.recno from rdrecon r,customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 "
                                + " and r.acno = c.acno and c.custid = '" + prdCustId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno "
                                + " from ddstransaction r,customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 "
                                + " and r.acno = c.acno and c.custid = '" + prdCustId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,r.recno "
                                + " from td_recon r,customerid c where r.trantype = 0 and r.dt between '" + prdFrDt + "' and '" + prdToDt + "' and r.cramt <>0 "
                                + " and r.trantype <> 27 and r.acno = c.acno and c.custid = '" + prdCustId + "' order by 5").getResultList();

                        double totPrdCr = 0;
                        String prdTxnDt = "", prdTxnAmt = "", prdOrgCd = "", prdRec = "";
                        for (int f = 0; f < prdTxnLst.size(); f++) {
                            Vector prdTxVec = (Vector) prdTxnLst.get(f);
                            totPrdCr = totPrdCr + Double.parseDouble(prdTxVec.get(1).toString());
                            if (totPrdCr > 250000) {
                                prdTxnDt = prdTxVec.get(2).toString();
                                prdTxnAmt = prdTxVec.get(1).toString();
                                prdOrgCd = prdTxVec.get(5).toString();
                                prdRec = prdTxVec.get(6).toString();
                                break;
                            }
                        }

                        if (brnCode.equals("0A") || brnCode.equalsIgnoreCase(prdOrgCd)) {
                            Form60ReportPojo frmPojo = new Form60ReportPojo();

                            frmPojo.setRepSrNo(cnt);
                            frmPojo.setOrgRepSrNo(cnt);
                            frmPojo.setCustId(prdDepVec.get(0).toString());
                            frmPojo.setCustName(prdDepVec.get(1).toString() + prdDepVec.get(2).toString() + prdDepVec.get(3).toString());
                            frmPojo.setDob(prdDepVec.get(4).toString());
                            frmPojo.setFatherName(prdDepVec.get(5).toString());
                            frmPojo.setPanAck("");
                            frmPojo.setAadharNo(prdDepVec.get(6).toString());

                            String idnTp = prdDepVec.get(16).toString();
                            String idnNo = "";
                            if (prdDepVec.get(16).toString().equalsIgnoreCase("05")) {
                                idnNo = prdDepVec.get(17).toString();
                            } else if (prdDepVec.get(16).toString().equalsIgnoreCase("08")) {
                                idnNo = prdDepVec.get(18).toString();
                            } else if (prdDepVec.get(16).toString().equalsIgnoreCase("06")) {
                                idnNo = prdDepVec.get(19).toString();
                            } else if (prdDepVec.get(16).toString().equalsIgnoreCase("03")) {
                                idnNo = prdDepVec.get(20).toString();
                            } else if (!prdDepVec.get(6).toString().equalsIgnoreCase("")) {
                                idnNo = prdDepVec.get(6).toString();
                                idnTp = "01";
                            }

                            frmPojo.setIdenType(idnTp);
                            frmPojo.setIdenNo(idnNo);
                            frmPojo.setFlatNo(prdDepVec.get(7).toString());
                            frmPojo.setNameOfPremises("");
                            frmPojo.setStreetName("");
                            frmPojo.setArea(prdDepVec.get(8).toString());
                            frmPojo.setCity(prdDepVec.get(10).toString());
                            frmPojo.setPostalCode(prdDepVec.get(12).toString().equalsIgnoreCase("") ? "XXXXXX" : prdDepVec.get(12).toString());
                            frmPojo.setStateCode(prdDepVec.get(13).toString());
                            frmPojo.setCountryCode(prdDepVec.get(11).toString().equalsIgnoreCase("IND") ? "IN" : prdDepVec.get(11).toString());
                            frmPojo.setMobile(prdDepVec.get(14).toString());
                            frmPojo.setEstAgrIncom("");
                            frmPojo.setEstNonAgrIncom("");
                            frmPojo.setRemarks("");
                            frmPojo.setForm60Ack("");
                            frmPojo.setTxnDate(prdTxnDt);
                            frmPojo.setTxnId(prdRec);
                            frmPojo.setTxnType("06");
                            frmPojo.setTxnAmount(prdTxnAmt);
                            frmPojo.setTxnMode("01");

                            frmSixDtl.add(frmPojo);
                        }
                    }
                }
            }

            //SL No - 12 Rule 114B Time Deposits   
            List timeDepLst = new ArrayList();
            timeDepLst = em.createNativeQuery("select b.custid,c.custname,ifnull(c.middle_name,''),ifnull(c.last_name,''),"
                    + "date_format(c.dateofbirth,'%d/%m/%Y'),ifnull(c.fathername,''),"
                    + " ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from "
                    + " cbs_customer_master_detail where customerid = c.customerid and legal_document = 'E' and CustEntityType = '01'),"
                    + " (select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa, cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = c.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ), "
                    + " ifnull(c.AADHAAR_NO,'')) as AadhaarNo,"
                    + "ifnull(c.PerAddressLine1,''),"
                    + "ifnull(c.peraddressline2,''),ifnull(c.PerBlock,''),ifnull(c.PerVillage,''),ifnull(c.PerCountryCode,''),"
                    + "ifnull(c.PerPostalCode,''),ifnull(c.PerStateCode,''),ifnull(c.mobilenumber,''),date_format(b.dt,'%d/%m/%Y'),"
                    + "sum(b.cramt), case c.legal_document when 'DL' then '05' when 'NRE' then '08' when 'PAN' then '15' when 'PAS' then '06' when 'VIC' then '03' "
                    + " else '' end,ifnull(c.drivingLicenseNo,''),ifnull(c.nrega_job_card,''),ifnull(c.passportNo,''),ifnull(c.VoterIdNo,'') from (select r.acno,r.cramt,r.dt,c.custid from rdrecon r,customerid c where r.dt between '" + frmDate + "' "
                    + "and '" + toDate + "' and r.cramt <>0 and r.acno = c.acno union select r.acno,r.cramt,r.dt,c.custid from td_recon r,"
                    + "customerid c where r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 and r.trantype <> 27 "
                    + "and r.acno = c.acno) b,cbs_customer_master_detail c where c.customerid = b.custid and c.pan_girnumber = 'Form 60' "
                    + "group by c.custname,ifnull(c.middle_name,''),ifnull(c.last_name,''),date_format(c.dateofbirth,'%d/%m/%Y'),"
                    + "ifnull(c.fathername,''),ifnull(c.AADHAAR_NO,''),ifnull(c.PerAddressLine1,''),ifnull(c.peraddressline2,''),"
                    + "ifnull(c.PerBlock,''),ifnull(c.PerCityCode,''),ifnull(c.PerCountryCode,''),ifnull(c.PerPostalCode,''),"
                    + "ifnull(c.PerStateCode,''),ifnull(c.PerPhoneNumber,''),b.custid,date_format(b.dt,'%d/%m/%Y') "
                    + "having sum(b.cramt)> '500000'").getResultList();

            if (!timeDepLst.isEmpty()) {
                for (int g = 0; g < timeDepLst.size(); g++) {
                    cnt = cnt + 1;
                    Vector timeDepVec = (Vector) timeDepLst.get(g);
                    String timeCustId = timeDepVec.get(0).toString();
                    List timeTxnLst = em.createNativeQuery("select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,"
                            + " r.trantime,r.org_brnid,case r.trantype when 0 then '01' when 1 then '02' when 2 then '05' when 8 then '05' end,r.recno  from rdrecon r,customerid c where r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 "
                            + " and r.acno = c.acno and c.custid = '" + timeCustId + "' union select r.acno,r.cramt,date_format(r.dt,'%d/%m/%Y'),c.custid,r.trantime,r.org_brnid,case r.trantype when 0 then '01' when 1 then '02' when 2 then '05' when 8 then '05' end,r.recno "
                            + " from td_recon r,customerid c where r.dt between '" + frmDate + "' and '" + toDate + "' and r.cramt <>0 "
                            + " and r.trantype <> 27 and r.acno = c.acno and c.custid = '" + timeCustId + "' order by 5").getResultList();

                    double timeTotCr = 0;
                    String timeTxnDt = "", timeTxnAmt = "", timeOrgCd = "", timeTxnCd = "", timeTxnRec = "";
                    for (int h = 0; h < timeTxnLst.size(); h++) {
                        Vector timeTxVec = (Vector) timeTxnLst.get(h);
                        timeTotCr = timeTotCr + Double.parseDouble(timeTxVec.get(1).toString());
                        if (timeTotCr > 500000) {
                            timeTxnDt = timeTxVec.get(2).toString();
                            timeTxnAmt = timeTxVec.get(1).toString();
                            timeOrgCd = timeTxVec.get(5).toString();
                            timeTxnCd = timeTxVec.get(6).toString();
                            timeTxnRec = timeTxVec.get(7).toString();
                            break;
                        }
                    }

                    if (brnCode.equals("0A") || brnCode.equalsIgnoreCase(timeOrgCd)) {
                        Form60ReportPojo frmPojo = new Form60ReportPojo();

                        frmPojo.setRepSrNo(cnt);
                        frmPojo.setOrgRepSrNo(cnt);
                        frmPojo.setCustId(timeDepVec.get(0).toString());
                        frmPojo.setCustName(timeDepVec.get(1).toString() + timeDepVec.get(2).toString() + timeDepVec.get(3).toString());
                        frmPojo.setDob(timeDepVec.get(4).toString());
                        frmPojo.setFatherName(timeDepVec.get(5).toString());
                        frmPojo.setPanAck("");
                        frmPojo.setAadharNo(timeDepVec.get(6).toString());

                        String idnTp = timeDepVec.get(17).toString();
                        String idnNo = "";
                        if (timeDepVec.get(17).toString().equalsIgnoreCase("05")) {
                            idnNo = timeDepVec.get(18).toString();
                        } else if (timeDepVec.get(17).toString().equalsIgnoreCase("08")) {
                            idnNo = timeDepVec.get(19).toString();
                        } else if (timeDepVec.get(17).toString().equalsIgnoreCase("06")) {
                            idnNo = timeDepVec.get(20).toString();
                        } else if (timeDepVec.get(17).toString().equalsIgnoreCase("03")) {
                            idnNo = timeDepVec.get(21).toString();
                        } else if (!timeDepVec.get(6).toString().equalsIgnoreCase("")) {
                            idnNo = timeDepVec.get(6).toString();
                            idnTp = "01";
                        }

                        frmPojo.setIdenType(idnTp);
                        frmPojo.setIdenNo(idnNo);
                        frmPojo.setFlatNo(timeDepVec.get(7).toString());
                        frmPojo.setNameOfPremises("");
                        frmPojo.setStreetName("");
                        frmPojo.setArea(timeDepVec.get(8).toString());
                        frmPojo.setCity(timeDepVec.get(10).toString());
                        frmPojo.setPostalCode(timeDepVec.get(12).toString().equalsIgnoreCase("") ? "XXXXXX" : timeDepVec.get(12).toString());
                        frmPojo.setStateCode(timeDepVec.get(13).toString());
                        frmPojo.setCountryCode(timeDepVec.get(11).toString().equalsIgnoreCase("IND") ? "IN" : timeDepVec.get(11).toString());
                        frmPojo.setMobile(timeDepVec.get(14).toString());
                        frmPojo.setEstAgrIncom("");
                        frmPojo.setEstNonAgrIncom("");
                        frmPojo.setRemarks("");
                        frmPojo.setForm60Ack("");
                        frmPojo.setTxnDate(timeTxnDt);
                        frmPojo.setTxnId(timeTxnRec);
                        frmPojo.setTxnType(timeTxnCd.equalsIgnoreCase("01") ? "06" : "22");
                        frmPojo.setTxnAmount(timeTxnAmt);
                        frmPojo.setTxnMode(timeTxnCd);
                        frmSixDtl.add(frmPojo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return frmSixDtl;
    }

    public List<AccountOpenRegisterPojo> getAccountCategoryData(String brnCode, String acNature, String actType, String actCategory, String toDate) throws ApplicationException {
        List<AccountOpenRegisterPojo> dataList = new ArrayList<AccountOpenRegisterPojo>();
        List result = new ArrayList();
        String tableName = "";
        try {

            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                tableName = "td_customermaster";
            } else {
                tableName = "customermaster";
            }

            if (brnCode.equalsIgnoreCase("0A")) {
                if (actType.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select ACNo,a.custname,ifnull(cm.fathername,''),OpeningDt,ifnull(JtName1,''),ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,''),b.ref_desc,a.OperMode,c.Description "
                            + "from " + CbsUtil.getAccMasterTableName(acNature) + " a,cbs_ref_rec_type b,codebook c," + tableName + " cm where a.AccStatus <> 9 and a.accttype in(select distinct(AcctCode) from accounttypemaster where acctnature = '" + acNature + "') and a.acctCategory = '" + actCategory + "' and OpeningDt <= '" + toDate + "' and ref_rec_no = '325' "
                            + "and a.acctCategory = b.ref_code and c.GroupCode = '4' and c.Code = a.OperMode "
                            + "and a.accttype=cm.actype and substring(a.acno,5,6)=cm.custno and substring(a.acno,11,2)=cm.agcode  and a.curbrcode = cm.brncode").getResultList();
                } else {
                    result = em.createNativeQuery("select ACNo,a.custname,ifnull(cm.fathername,''),OpeningDt,ifnull(JtName1,''),ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,''),b.ref_desc,a.OperMode,c.Description "
                            + "from " + CbsUtil.getAccMasterTableName(acNature) + " a,cbs_ref_rec_type b,codebook c," + tableName + " cm where a.accttype = '" + actType + "' "
                            + "and a.AccStatus <> 9 and a.acctCategory = '" + actCategory + "' and OpeningDt <= '" + toDate + "' and ref_rec_no = '325' "
                            + "and a.acctCategory = b.ref_code and c.GroupCode = '4' and c.Code = a.OperMode  "
                            + "and a.accttype=cm.actype and substring(a.acno,5,6)=cm.custno and substring(a.acno,11,2)=cm.agcode  and a.curbrcode = cm.brncode").getResultList();
                }
            } else {
                if (actType.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select ACNo,a.custname,ifnull(cm.fathername,''),OpeningDt,ifnull(JtName1,''),ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,''),b.ref_desc,a.OperMode,c.Description "
                            + "from " + CbsUtil.getAccMasterTableName(acNature) + " a,cbs_ref_rec_type b,codebook c," + tableName + " cm where a.curbrcode = '" + brnCode + "' and a.accttype in(select distinct(AcctCode) from accounttypemaster where acctnature = '" + acNature + "')"
                            + "and a.AccStatus <> 9 and a.acctCategory = '" + actCategory + "' and OpeningDt <= '" + toDate + "' and ref_rec_no = '325' "
                            + "and a.acctCategory = b.ref_code and c.GroupCode = '4' and c.Code = a.OperMode "
                            + "and a.accttype=cm.actype and substring(a.acno,5,6)=cm.custno and substring(a.acno,11,2)=cm.agcode  and a.curbrcode = cm.brncode").getResultList();
                } else {
                    result = em.createNativeQuery("select ACNo,a.custname,ifnull(cm.fathername,''),OpeningDt,ifnull(JtName1,''),ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,''),b.ref_desc,a.OperMode,c.Description "
                            + "from " + CbsUtil.getAccMasterTableName(acNature) + " a,cbs_ref_rec_type b,codebook c," + tableName + " cm where a.curbrcode = '" + brnCode + "' and a.accttype = '" + actType + "' "
                            + "and a.AccStatus <> 9 and a.acctCategory = '" + actCategory + "' and OpeningDt <= '" + toDate + "' and ref_rec_no = '325' "
                            + "and a.acctCategory = b.ref_code and c.GroupCode = '4' and c.Code = a.OperMode "
                            + "and a.accttype=cm.actype and substring(a.acno,5,6)=cm.custno and substring(a.acno,11,2)=cm.agcode  and a.curbrcode = cm.brncode").getResultList();
                }
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    AccountOpenRegisterPojo pojo = new AccountOpenRegisterPojo();
                    pojo.setACNO(vtr.get(0).toString());
                    pojo.setCUSTNAME(vtr.get(1).toString());
                    pojo.setFHNAME(vtr.get(2).toString());
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.TD_AC)) {
                        pojo.setOpDt(vtr.get(3).toString().substring(8, 10) + "/" + vtr.get(3).toString().substring(5, 7) + "/" + vtr.get(3).toString().substring(0, 4));
                    } else {
                        pojo.setOpDt(vtr.get(3).toString().substring(6, 8) + "/" + vtr.get(3).toString().substring(4, 6) + "/" + vtr.get(3).toString().substring(0, 4));
                    }
                    pojo.setJTNAME(vtr.get(4).toString());
                    pojo.setJtName2(vtr.get(5).toString());
                    pojo.setJtName3(vtr.get(6).toString());
                    pojo.setJtName4(vtr.get(7).toString());
                    pojo.setActCategoryDesc(vtr.get(8).toString());
                    pojo.setOpModeDesc(vtr.get(10).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<SalarySheetPojo> getAccountSalarySheetDataold(String repType, String brnCode, String month, String year) throws ApplicationException {
        List<SalarySheetPojo> dataList = new ArrayList<SalarySheetPojo>();
        List result = new ArrayList();
        try {

            List brList = em.createNativeQuery("select a.bankname,a.bankaddress,b.BrPhone from bnkadd a,parameterinfo b,branchmaster c where c.BrnCode = '90'and b.BrnCode = c.BrnCode and a.alphacode = c.alphacode").getResultList();
            Vector brVector = (Vector) brList.get(0);
            if (repType.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select base_branch, emp_code,emp_name,desg_code,description,pf_account,bank_account_code from hr_personnel_details a,hr_mst_struct b where base_branch = '" + brnCode + "' and a.desg_code = b.struct_code").getResultList();
            } else {
                result = em.createNativeQuery("select base_branch, emp_code,emp_name,desg_code,description,pf_account,bank_account_code from hr_personnel_details a,hr_mst_struct b where base_branch = '" + brnCode + "' and a.desg_code = b.struct_code").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    SalarySheetPojo pojo = new SalarySheetPojo();
                    String branch = vtr.get(0).toString();
                    String empNo = vtr.get(1).toString();
                    String empName = vtr.get(2).toString();
                    String desgCode = vtr.get(3).toString();
                    String desgDesc = vtr.get(4).toString();
                    String pfAcno = vtr.get(5).toString();
                    String acno = vtr.get(6).toString();
                    BigDecimal totalAmt = new BigDecimal("0");
                    BigDecimal totaldeduAmt = new BigDecimal("0");
                    BigDecimal netPay = new BigDecimal("0");
                    List salryList = em.createNativeQuery("SELECT COMPONENT_TYPE,AMOUNT FROM hr_salary_processing_dtl where EMP_CODE = '" + empNo + "' and PURPOSE_CODE = 'PUR001' and MONTH = '" + month + "' and  EXTRACT(YEAR FROM ENTERED_DATE) = '" + year + "'").getResultList();
                    if (!salryList.isEmpty()) {
                        for (int j = 0; j < salryList.size(); j++) {
                            Vector ele = (Vector) salryList.get(j);
                            String compType = ele.get(0).toString();
                            String amount = ele.get(1).toString();
                            totalAmt = totalAmt.add(new BigDecimal(amount));
                            // SalarySheetPojo pojo = new SalarySheetPojo();
                            if (repType.equalsIgnoreCase("SA")) {
                                if (compType.equalsIgnoreCase("Grade")) {
                                    pojo.setGrade(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("basic pay")) {
                                    pojo.setBasicPay(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("DA")) {
                                    pojo.setDa(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("HRA")) {
                                    pojo.setHra(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("KEY ALLOWANCE")) {
                                    pojo.setKey(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("CCA")) {
                                    pojo.setCca(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("OFFICE ALLOWANCE")) {
                                    pojo.setOffice(amount);
                                } else if (compType.equalsIgnoreCase("CONVEYENCE")) {
                                    pojo.setConvyn(amount);
                                } else if (compType.equalsIgnoreCase("PF")) {
                                    pojo.setPfBank(Double.parseDouble(amount));
                                }
                            }
                        }
                    }

                    if (repType.equalsIgnoreCase("SD")) {
                        List deductionList = em.createNativeQuery("SELECT COMPONENT_TYPE,AMOUNT FROM hr_salary_processing_dtl where EMP_CODE = '" + empNo + "' and PURPOSE_CODE = 'PUR002' and MONTH = '" + month + "' and  EXTRACT(YEAR FROM ENTERED_DATE) = '" + year + "'").getResultList();
                        if (!deductionList.isEmpty()) {
                            for (int k = 0; k < deductionList.size(); k++) {
                                Vector deduVector = (Vector) deductionList.get(k);
                                String compType = deduVector.get(0).toString();
                                String amount = deduVector.get(1).toString();
                                totaldeduAmt = totaldeduAmt.add(new BigDecimal(amount));
                                if (compType.equalsIgnoreCase("PF")) {
                                    pojo.setPfEmp(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("LIC")) {
                                    pojo.setLic(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("LOP")) {
                                    pojo.setLop(Double.parseDouble(amount));
                                } else if (compType.equalsIgnoreCase("SA")) {
                                    pojo.setSa(Double.parseDouble(amount));
                                }
                                pojo.setTotaldeduction(totaldeduAmt);
                            }
                        }
                        netPay = totalAmt.subtract(totaldeduAmt);
                        pojo.setAcNo(acno);
                        pojo.setNetPayble(netPay);
                    }

                    pojo.setsNo(i + 1);
                    pojo.setEmpName(empName);
                    pojo.setEmpNo(empNo);
                    pojo.setDesignation(desgDesc);
                    pojo.setPfAcNo(pfAcno);
                    pojo.setTotal(totalAmt);
                    pojo.setBnkName(brVector.get(0).toString());
                    pojo.setBnkAdd(brVector.get(1).toString());
                    pojo.setPhone("ph. " + brVector.get(2).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<SalarySheetPojo> getAccountSalarySheetData(String repType, String brnCode, String month, String year) throws ApplicationException {
        List<SalarySheetPojo> dataList = new ArrayList<SalarySheetPojo>();
        List result = new ArrayList();
        List compList = new ArrayList();
        compList.add("Basic");
        try {
            List brList = em.createNativeQuery("select a.bankname,a.bankaddress,b.BrPhone from bnkadd a,parameterinfo b,branchmaster c where c.BrnCode = '90'and b.BrnCode = c.BrnCode and a.alphacode = c.alphacode").getResultList();
            Vector brVector = (Vector) brList.get(0);

//            if (repType.equalsIgnoreCase("BRN")) {
//               result = em.createNativeQuery("select base_branch, emp_code,emp_name,desg_code,description,pf_account,bank_account_code from hr_personnel_details a,hr_mst_struct b where base_branch = '" + brnCode + "' and a.desg_code = b.struct_code and a.WORK_STATUS != 'STA004'").getResultList();
//            } else {
//               result = em.createNativeQuery("select base_branch, emp_code,emp_name,desg_code,description,pf_account,bank_account_code from hr_personnel_details a,hr_mst_struct b where emp_id = '" + brnCode + "' and a.desg_code = b.struct_code ").getResultList();
//            }
            if (repType.equalsIgnoreCase("BRN")) {
                result = em.createNativeQuery("select  ifnull(hpd.EMP_NAME,' '), ifnull(hpd.EMP_CODE,' '),ifnull(hms.DESCRIPTION,' '),ifnull(cmd.PAN_GIRNumber,' '),ifnull(Date_format(hpd.JOIN_DATE,\"%d/%m/%Y\"),' ')\n"
                        + ",ifnull(hpd.BANK_ACCOUNT_CODE,' '),ifnull(hpd.SEX,' '),ifnull(bm.AlphaCode,' '),ifnull(bm.City,' '),ifnull(hpd.PF_ACCOUNT,' ')\n"
                        + "from hr_personnel_details hpd ,hr_mst_struct hms,cbs_customer_master_detail cmd,branchmaster bm \n"
                        + "where hms.STRUCT_CODE= hpd.DESG_CODE and cmd.customerid = hpd.CUSTOMER_ID and bm.BrnCode = hpd.BASE_BRANCH and hpd.BASE_BRANCH='" + brnCode + "'and hpd.WORK_STATUS != 'STA004'").getResultList();
            } else {
                result = em.createNativeQuery("select  ifnull(hpd.EMP_NAME,''), ifnull(hpd.EMP_CODE,' '),ifnull(hms.DESCRIPTION,' '),ifnull(cmd.PAN_GIRNumber,' '),ifnull(Date_format(hpd.JOIN_DATE,\"%d/%m/%Y\"),' '),\n"
                        + "ifnull(hpd.BANK_ACCOUNT_CODE,''),ifnull(hpd.SEX,''),ifnull(bm.AlphaCode,''),ifnull(bm.City,''),ifnull(hpd.PF_ACCOUNT,'')\n"
                        + "from hr_personnel_details hpd ,hr_mst_struct hms,cbs_customer_master_detail cmd,branchmaster bm \n"
                        + " where hms.STRUCT_CODE= hpd.DESG_CODE and cmd.customerid = hpd.CUSTOMER_ID and bm.BrnCode = hpd.BASE_BRANCH and  hpd.EMP_ID='" + brnCode + "'").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    String empName = vtr.get(0).toString();
                    String empNo = vtr.get(1).toString();
                    String designation = vtr.get(2).toString();
                    String panNo = vtr.get(3).toString();
                    String joiningDate = vtr.get(4).toString();
                    String bankAcCode = vtr.get(5).toString();
                    String gender = vtr.get(6).toString();
                    String location = vtr.get(7).toString();
                    String unit = vtr.get(8).toString();
                    String pfAcno = vtr.get(9).toString();
                    String Department = "Bank";

                    BigDecimal totalAmt = new BigDecimal("0");
                    BigDecimal totaldeduAmt = new BigDecimal("0");
                    BigDecimal netPay = new BigDecimal("0");

                    List workstatusList = em.createNativeQuery("select WORK_STATUS from hr_personnel_details where EMP_CODE='" + empNo + "'").getResultList();
                    Vector vt = (Vector) workstatusList.get(0);
                    String workStatus = vt.get(0).toString();

                    if (!workStatus.equalsIgnoreCase("STA004")) {
                        List salryList = em.createNativeQuery("SELECT a.COMPONENT_TYPE,a.AMOUNT,a.purpose_code,b.DEDUCT_DAYS,b.ABSENT_DAYS  FROM hr_salary_processing_dtl a,hr_attendance_details b, hr_personnel_details c\n"
                                + "where a.EMP_CODE = '" + empNo + "' and a.MONTH = '" + month + "' and  EXTRACT(YEAR FROM a.ENTERED_DATE) = '" + year + "'and a.EMP_CODE = b.EMP_CODE and \n"
                                + "a.MONTH = b.ATTEN_MONTH  and EXTRACT(YEAR FROM a.ENTERED_DATE) = b.ATTEN_YEAR and c.EMP_CODE = a.EMP_CODE and c.EMP_CODE=b.EMP_CODE \n"
                                + "and c.WORK_STATUS != 'STA004' order by a.purpose_code,a.component_type").getResultList();
                        if (!salryList.isEmpty()) {
                            for (int j = 0; j < salryList.size(); j++) {
                                Vector ele = (Vector) salryList.get(j);
                                String compType = ele.get(0).toString();
                                String amount = ele.get(1).toString();
                                String purposeType = ele.get(2).toString();

                                Double noOfDays = Double.valueOf(ele.get(3).toString());
                                // added absent days
                                int absentDays = Integer.parseInt(ele.get(4).toString());
                                totalAmt = totalAmt.add(new BigDecimal(amount));
                                SalarySheetPojo pojo = new SalarySheetPojo();
                                pojo.setsNo(j + 1);
                                pojo.setEmpName(empName);
                                pojo.setEmpNo(empNo);
                                pojo.setPurposeType(purposeType);
                                pojo.setComponentType(compType);
                                pojo.setComponentAmt(new BigDecimal(amount));
                                pojo.setDesignation(designation);
                                pojo.setPanNo(panNo);
                                pojo.setJoiningDate(joiningDate);
                                pojo.setGender(gender);
                                pojo.setLocation(location);
                                pojo.setUnit(unit);
                                pojo.setDepartment(Department);
                                pojo.setDays(noOfDays);
                                pojo.setAbsentDays(absentDays);
                                //    if (compType.equalsIgnoreCase("PF")) {
                                pojo.setPfAcNo(pfAcno);
                                //  } else {
                                //       pojo.setPfAcNo("");
                                //   }
                                //pojo.setPfAcNo(pfAcno);
                                if (compType.contains("BASIC")) {
                                    pojo.setAcNo(bankAcCode);
                                } else {
                                    pojo.setAcNo("");
                                }
                                //pojo.setAcNo(acno);
                                pojo.setBnkName(brVector.get(0).toString());
                                pojo.setBnkAdd(brVector.get(1).toString());
                                pojo.setPhone("ph. " + brVector.get(2).toString());
                                dataList.add(pojo);
                            }
                        } else {
                            List maxMonth = em.createNativeQuery("select distinct ifnull(max(month),'nomonth') from hr_salary_processing_dtl where EXTRACT(YEAR FROM ENTERED_DATE) = '" + year + "' and EMP_CODE='" + empNo + "' ").getResultList();
                            Vector vec = (Vector) maxMonth.get(0);
                            String maxMonthName = vec.get(0).toString();
                            if (!maxMonthName.equalsIgnoreCase("nomonth")) {
                                List salryList1 = em.createNativeQuery(" SELECT a.COMPONENT_TYPE,a.AMOUNT,a.purpose_code from hr_salary_processing_dtl a, hr_personnel_details b\n"
                                        + "  where a.EMP_CODE = '" + empNo + "' and a.MONTH = '" + maxMonthName + "' and  EXTRACT(YEAR FROM a.ENTERED_DATE) = '" + year + "' and b.WORK_STATUS != 'STA004' and a.EMP_CODE= b.EMP_CODE\n"
                                        + "  order by a.purpose_code,a.component_type").getResultList();
                                for (int j = 0; j < salryList1.size(); j++) {
                                    Vector ele = (Vector) salryList1.get(j);
                                    String compType = ele.get(0).toString();
                                    String amount = "0.00";
                                    String purposeType = ele.get(2).toString();
                                    String noOfDays = "30";
                                    totalAmt = totalAmt.add(new BigDecimal(amount));
                                    SalarySheetPojo pojo = new SalarySheetPojo();
                                    pojo.setsNo(j + 1);
                                    pojo.setEmpName(empName);
                                    pojo.setEmpNo(empNo);
                                    pojo.setPurposeType(purposeType);
                                    pojo.setComponentType(compType);
                                    pojo.setDesignation(designation);
                                    pojo.setPanNo(panNo);
                                    pojo.setJoiningDate(joiningDate);
                                    pojo.setGender(gender);
                                    pojo.setLocation(location);
                                    pojo.setUnit(unit);
                                    pojo.setDepartment(Department);
                                    pojo.setComponentAmt(new BigDecimal(amount));
                                    pojo.setDesignation(designation);
                                    pojo.setDays(0.0);
                                    pojo.setAbsentDays(0);
                                    pojo.setPfAcNo(pfAcno);

                                    if (compType.contains("BASIC")) {
                                        pojo.setAcNo(bankAcCode);
                                    } else {
                                        pojo.setAcNo("");
                                    }
                                    pojo.setBnkName(brVector.get(0).toString());
                                    pojo.setBnkAdd(brVector.get(1).toString());
                                    pojo.setPhone("ph. " + brVector.get(2).toString());
                                    dataList.add(pojo);
                                }
                            } else {
                                SalarySheetPojo pojo1 = new SalarySheetPojo();
                                pojo1.setsNo(1);
                                pojo1.setEmpName(empName);
                                pojo1.setEmpNo(empNo);
                                pojo1.setPurposeType("PUR001");
                                pojo1.setComponentType("BASIC");
                                pojo1.setComponentAmt(new BigDecimal(0.00));
                                pojo1.setDesignation(designation);
                                pojo1.setPanNo(panNo);
                                pojo1.setJoiningDate(joiningDate);
                                pojo1.setGender(gender);
                                pojo1.setLocation(location);
                                pojo1.setUnit(unit);
                                pojo1.setDepartment(Department);
                                pojo1.setDesignation(designation);
                                pojo1.setDays(0.00);
                                pojo1.setAbsentDays(0);
                                pojo1.setAcNo(bankAcCode);
                                pojo1.setBnkName(brVector.get(0).toString());
                                pojo1.setBnkAdd(brVector.get(1).toString());
                                pojo1.setPhone("ph. " + brVector.get(2).toString());
                                dataList.add(pojo1);

                                List salaryList2 = em.createNativeQuery("select hsa.slab_code ,hsa.basic_salary ,hsm.DEPEND_COMPONENT,hsm.purpose_code \n"
                                        + "from hr_salary_allocation hsa,hr_slab_master hsm ,hr_personnel_details hp where hsa.EMP_CODE='" + empNo + "'and hsa.SLAB_CODE=hsm.SLAB_CODE  \n"
                                        + "and hp.WORK_STATUS != 'STA004' and hp.EMP_CODE= hsa.EMP_CODE ").getResultList();
                                for (int m = 0; m < salaryList2.size(); m++) {
                                    SalarySheetPojo pojo = new SalarySheetPojo();
                                    Vector vc = (Vector) salaryList2.get(m);
                                    if (!compList.contains(vc.get(2).toString())) {
                                        compList.add(vc.get(2).toString());
                                        pojo.setsNo(m + 2);
                                        pojo.setEmpName(empName);
                                        pojo.setEmpNo(empNo);
                                        pojo.setPurposeType(vc.get(3).toString());
                                        pojo.setComponentType(vc.get(2).toString());
                                        pojo.setComponentAmt(new BigDecimal(0.00));
                                        pojo.setDesignation(designation);
                                        pojo.setPanNo(panNo);
                                        pojo.setJoiningDate(joiningDate);
                                        pojo.setGender(gender);
                                        pojo.setLocation(location);
                                        pojo.setUnit(unit);
                                        pojo.setDepartment(Department);
                                        pojo.setDesignation(designation);
                                        pojo.setDays(0.00);
                                        pojo.setAbsentDays(0);
                                        pojo.setPfAcNo(pfAcno);
                                        pojo.setAcNo(bankAcCode);
                                        pojo.setBnkName(brVector.get(0).toString());
                                        pojo.setBnkAdd(brVector.get(1).toString());
                                        pojo.setPhone("ph. " + brVector.get(2).toString());
                                        dataList.add(pojo);
                                    }
                                }
                            }
                        }
                    } else {
                        List salryList = em.createNativeQuery("select a.COMPONENT_TYPE,a.AMOUNT,a.purpose_code,b.DEDUCT_DAYS ,b.ABSENT_DAYS FROM hr_salary_processing_dtl a,hr_attendance_details b, hr_personnel_details c\n"
                                + "where a.EMP_CODE = '" + empNo + "' and a.MONTH = '" + month + "' and  EXTRACT(YEAR FROM a.ENTERED_DATE) = '" + year + "'and a.EMP_CODE = b.EMP_CODE and \n"
                                + "a.MONTH = b.ATTEN_MONTH  and EXTRACT(YEAR FROM a.ENTERED_DATE) = b.ATTEN_YEAR and c.EMP_CODE = a.EMP_CODE and c.EMP_CODE=b.EMP_CODE \n"
                                + "and a.ENTERED_DATE<= c.RETIREMENT_DATE order by a.purpose_code,a.component_type").getResultList();
                        if (!salryList.isEmpty()) {
                            for (int j = 0; j < salryList.size(); j++) {
                                Vector ele = (Vector) salryList.get(j);
                                String compType = ele.get(0).toString();
                                String amount = ele.get(1).toString();
                                String purposeType = ele.get(2).toString();

                                totalAmt = totalAmt.add(new BigDecimal(amount));
                                SalarySheetPojo pojo = new SalarySheetPojo();
                                pojo.setsNo(j + 1);
                                pojo.setEmpName(empName);
                                pojo.setEmpNo(empNo);
                                pojo.setPurposeType(purposeType);
                                pojo.setComponentType(compType);
                                pojo.setComponentAmt(new BigDecimal(amount));
                                pojo.setDesignation(designation);
                                pojo.setPanNo(panNo);
                                pojo.setJoiningDate(joiningDate);
                                pojo.setGender(gender);
                                pojo.setLocation(location);
                                pojo.setUnit(unit);
                                pojo.setDepartment(Department);
                                pojo.setDesignation(designation);
                                pojo.setDays(Double.valueOf(ele.get(3).toString()));
                                pojo.setAbsentDays(Integer.parseInt(ele.get(4).toString()));
                                //      if (compType.equalsIgnoreCase("PF")) {
                                pojo.setPfAcNo(pfAcno);
                                //      } else {
                                //          pojo.setPfAcNo("");
                                //      }
                                //pojo.setPfAcNo(pfAcno);
                                if (compType.contains("BASIC")) {
                                    pojo.setAcNo(bankAcCode);
                                } else {
                                    pojo.setAcNo("");
                                }
                                //pojo.setAcNo(acno);
                                pojo.setBnkName(brVector.get(0).toString());
                                pojo.setBnkAdd(brVector.get(1).toString());
                                pojo.setPhone("ph. " + brVector.get(2).toString());
                                dataList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<AccountEditHistoryPojo> getSignatureAuthDetail(String acNat, String acType, String opt, String frmAcno,
            String toAcno, String frmDate, String toDate, String brnCode) throws ApplicationException {
        List<AccountEditHistoryPojo> scanList = new ArrayList<AccountEditHistoryPojo>();
        try {
            List resultList = new ArrayList();
            if (opt.equalsIgnoreCase("A")) {
                resultList = em.createNativeQuery("select d.NewAcno,d.Enterby,d.AuthBy,ifnull(c.CustFullName,''),ifnull(c.fathername,''),"
                        + " ifnull(c.mailaddressline1,''), ifnull(c.mailaddressline2,''), ifnull(c.mobilenumber,''),d.scanned_date from customerid b, "
                        + " cbs_customer_master_detail c , cbs_cust_image_detail d where b.custid = c.customerid and b.acno = d.newacno "
                        + " and d.newacno between '" + frmAcno + "' and '" + toAcno + "' order by 1").getResultList();
            } else if (opt.equalsIgnoreCase("D")) {
                String tDt = toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6) + " 23:59:00";
                resultList = em.createNativeQuery("select d.NewAcno,d.Enterby,d.AuthBy,ifnull(c.CustFullName,''),ifnull(c.fathername,''),"
                        + " ifnull(c.mailaddressline1,''), ifnull(c.mailaddressline2,''),ifnull(c.mobilenumber,''),d.scanned_date from customerid b, "
                        + " cbs_customer_master_detail c ,cbs_cust_image_detail d where d.scanned_date "
                        + " between '" + frmDate + "' and '" + tDt + "' and substring(d.newacno,1,2) = '" + brnCode + "' "
                        + " and substring(d.newacno,3,2)='" + acType + "' and b.acno =  d.newacno and b.custid = c.customerid "
                        + " union "
                        + " select d.NewAcno,d.Enterby,d.AuthBy,ifnull(c.CustFullName,''),ifnull(c.fathername,''),"
                        + " ifnull(c.mailaddressline1,''), ifnull(c.mailaddressline2,''),ifnull(c.mobilenumber,''),d.scanned_date from customerid b, "
                        + " cbs_customer_master_detail c ,cbs_cust_image_detail_his d where d.scanned_date "
                        + " between '" + frmDate + "' and '" + tDt + "' and substring(d.newacno,1,2) = '" + brnCode + "' "
                        + " and substring(d.newacno,3,2)='" + acType + "' and b.acno =  d.newacno and b.custid = c.customerid order by 9").getResultList();
            }

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    AccountEditHistoryPojo pojo = new AccountEditHistoryPojo();
                    String acNo = vec.get(0).toString();
                    String enterBy = vec.get(1).toString();
                    String authBy = vec.get(2).toString();
                    String cuName = vec.get(3).toString();
                    String fName = vec.get(4).toString();
                    String mAdd = vec.get(5).toString() + vec.get(6).toString();
                    String mNo = vec.get(7).toString();
                    pojo.setAcno(acNo);
                    pojo.setCustName(cuName);
                    pojo.setFatherName(fName);
                    pojo.setMailAdd(mAdd);
                    pojo.setPhoneNo(mNo);
                    pojo.setEnterBy(enterBy);
                    pojo.setAuthBy(authBy);
                    scanList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return scanList;
    }

    public List<PendingChargesReportPojo> getPendingChargesData(String branch, String tranType, String fromDate, String toDate) throws ApplicationException {
        List<PendingChargesReportPojo> dataList = new ArrayList<PendingChargesReportPojo>();
        List resultList = new ArrayList();
        try {
            String subcondition = "", date = "";
            if (tranType.equalsIgnoreCase("All")) {
                subcondition = "";
            } else {
                subcondition = "and recover='" + tranType + "'";
            }

            if (branch.equalsIgnoreCase("0A")) {
                resultList = em.createNativeQuery("select a.acno,a.custname,p.amount,p.details,ifnull(date_format(p.updatedt,'%d/%m/%Y'),''),TRANDESC,recover,date_format(dt,'%d/%m/%Y'),REF_DESC  "
                        + "from pendingcharges p,accountmaster a,cbs_ref_rec_type rf where p.ACNo=a.ACNo and REF_REC_NO = '454' and TRANDESC = REF_CODE  \n"
                        + " and (dt BETWEEN '" + fromDate + "' and '" + toDate + "' OR date_format(Updatedt,'%Y%m%d') BETWEEN '" + fromDate + "' and '" + toDate + "') "
                        + "and (a.ClosingDate is null or a.ClosingDate = '' or a.ClosingDate>'" + toDate + "')"
                        + subcondition + " and TRANDESC <> 113 order by acno,dt").getResultList();
            } else {
                resultList = em.createNativeQuery("select a.acno,a.custname,p.amount,p.details,ifnull(date_format(p.updatedt,'%d/%m/%Y'),''),TRANDESC,recover,date_format(dt,'%d/%m/%Y'),REF_DESC "
                        + "from pendingcharges p,accountmaster a,cbs_ref_rec_type rf where p.ACNo=a.ACNo and REF_REC_NO = '454' and TRANDESC = REF_CODE \n"
                        + " and substring(p.Acno,1,2) = '" + branch + "' and (dt BETWEEN '" + fromDate + "' and '" + toDate + "' OR date_format(Updatedt,'%Y%m%d') BETWEEN '" + fromDate + "' and '" + toDate + "') "
                        + "and (a.ClosingDate is null or a.ClosingDate = '' or a.ClosingDate>'" + toDate + "')"
                        + subcondition + " and TRANDESC <> 113 order by acno,dt").getResultList();
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    PendingChargesReportPojo pojo = new PendingChargesReportPojo();
                    pojo.setAccountno(vec.get(0).toString());
                    pojo.setCustomername(vec.get(1).toString());
                    pojo.setAmount(Double.parseDouble(vec.get(2).toString()));
                    pojo.setDetails(vec.get(3).toString());
                    pojo.setUpdatedt(vec.get(4).toString());
                    pojo.setRecover(vec.get(6).toString());
                    pojo.setPostingDt(vec.get(7).toString());
                    pojo.setTRANDESC(vec.get(8).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }

        return dataList;
    }

    public List<AccountStatementReportPojo> getAccountStatementFdData(String acNo, String fromDate, String toDate) throws ApplicationException {
        List result = new ArrayList();
        List<AccountStatementReportPojo> fdDataList = new ArrayList<AccountStatementReportPojo>();
        try {

            result = em.createNativeQuery("select 'Interest Amt'INTA,acno,cast(VoucherNo as unsigned),Interest IntTdsAmt,dt from td_interesthistory  where acno = '" + acNo + "' and dt between  '" + fromDate + "' and '" + toDate + "' \n"
                    + "union all\n"
                    + "select 'Tds Amt' TDS,acno,cast(voucherNo as unsigned),tds IntTdsAmt,dt from tdshistory  where acno = '" + acNo + "' and dt between  '" + fromDate + "' and '" + toDate + "' \n"
                    + "order by 1,3,5").getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    AccountStatementReportPojo pojo = new AccountStatementReportPojo();
                    pojo.setAcNo(vtr.get(1).toString());
                    pojo.setParticulars(vtr.get(0).toString());
                    pojo.setChequeNo(vtr.get(2).toString());
                    if (vtr.get(0).toString().equalsIgnoreCase("Interest Amt")) {
                        pojo.setDeposit(Double.parseDouble(vtr.get(3).toString()));
                    } else {
                        pojo.setWithdrawl(Double.parseDouble(vtr.get(3).toString()));
                    }
                    pojo.setDate(dmyy.format(yymmdd.parse(vtr.get(4).toString())));
                    fdDataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return fdDataList;
    }

    public List<SalarySheetPojo> getEarningRecordsData(String empId, String frmDate, String toDate) throws ApplicationException {
        List<SalarySheetPojo> dataList = new ArrayList<SalarySheetPojo>();
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select emp_code,emp_name,desg_code,description from "
                    + " hr_personnel_details a,hr_mst_struct b where emp_id = '" + empId + "' and a.desg_code = b.struct_code").getResultList();
            if (!result.isEmpty()) {
                Vector vtr = (Vector) result.get(0);
                String empNo = vtr.get(0).toString();
                String empName = vtr.get(1).toString();
                String desgCode = vtr.get(2).toString();
                String desgDesc = vtr.get(3).toString();

                List monthDtl = em.createNativeQuery("select distinct month,year(month_start_date) from hr_salary_processing_dtl "
                        + " where emp_code = '" + empNo + "' and month_start_date >='" + frmDate + "' and month_last_date <='" + toDate + "' order by month_start_date").getResultList();
                for (int i = 0; i < monthDtl.size(); i++) {
                    Vector vtrMon = (Vector) monthDtl.get(i);
                    String month = vtrMon.get(0).toString();
                    String year = vtrMon.get(1).toString();

                    //GROSS INCOME OF EMPLOYEE FOR THAT PARTICULAR MONTH
                    List grIncome = em.createNativeQuery("select ifnull(sum(amount),0) from hr_salary_processing_dtl where "
                            + "emp_code = '" + empNo + "' and month ='" + month + "' and DATE_FORMAT(MONTH_START_DATE,'%Y')='" + year + "' and PURPOSE_CODE IN ('PUR001')").getResultList();
                    Vector vtrGrIncome = (Vector) grIncome.get(0);

                    //EMPLOYEE PF DEDUCTION FOR THAT PARTICULAR MONTH
                    List empPf = em.createNativeQuery("select ifnull(sum(amount),0) from hr_salary_processing_dtl where "
                            + " emp_code = '" + empNo + "' and month ='" + month + "' and DATE_FORMAT(MONTH_START_DATE,'%Y')='" + year + "' and purpose_code = 'PUR002' and desc_short_code = 1").getResultList();
                    Vector vtrEmpPf = (Vector) empPf.get(0);

                    //PF EMPLOYER CONTRIBUTION  0 : NO , 1: YES
                    int pfEmpCont = ftsBean.getCodeForReportName("PF_EMPLOYER_CONT");
                    String empPfCont = "0";

                    if (pfEmpCont == 1) {
                        //EMPLOYER PF CONTRIBUTION FOR THAT PARTICULAR MONTH
                        List empContPf = em.createNativeQuery("select ifnull(sum(amount),0) from hr_salary_processing_dtl where "
                                + " emp_code = '" + empNo + "' and month = '" + month + "' and DATE_FORMAT(MONTH_START_DATE,'%Y')='" + year + "' and purpose_code ='PUR004'").getResultList();
                        Vector vtrEmpContPf = (Vector) empContPf.get(0);
                        empPfCont = vtrEmpContPf.get(0).toString();
                    }

                    SalarySheetPojo pojo = new SalarySheetPojo();
                    pojo.setEmpName(empName);
                    pojo.setDesignation(desgDesc);
                    pojo.setEmpNo(empNo);
                    pojo.setsNo(i + 1);
                    pojo.setPurposeType(month + " - " + year);
                    pojo.setBasicPay(Double.parseDouble(vtrGrIncome.get(0).toString()));
                    pojo.setCca(Double.parseDouble("0"));
                    pojo.setNetPayble(BigDecimal.valueOf((Double.parseDouble(vtrGrIncome.get(0).toString())) + Double.parseDouble("0")));
                    pojo.setPfEmp(Double.parseDouble(vtrEmpPf.get(0).toString()));
                    pojo.setLop(Double.parseDouble(empPfCont));
                    pojo.setTotaldeduction(BigDecimal.valueOf(Double.parseDouble(vtrEmpPf.get(0).toString()) + Double.parseDouble(empPfCont)));

                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List getDistinctChargeName(String chgType) throws ApplicationException {
        try {
            //return em.createNativeQuery("select distinct charge_name from cbs_charge_detail where charge_type = '" + chgType + "'").getResultList();
            return em.createNativeQuery("select distinct ref_desc from cbs_ref_rec_type where ref_rec_no = 108").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getChargeGlAndAmt(String acType, String chgName) throws ApplicationException {
        try {
            return em.createNativeQuery("select amt,cr_gl_head from cbs_charge_detail where charge_name = '" + chgName + "' and ac_type ='" + acType + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String individualAccountCharges(String acno, String chargeAmt, String orgBrnCode, String username, String crHead, String chgType, String gstType) throws ApplicationException {
        String dt = ymdFormat.format(new Date());
        String acNature = "";
        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            String mainDetail = chgType + " For A/c No " + acno;
            ut.begin();
            /*
             * For Service Tax
             */
            List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname like 'STAXMODULE_ACTIVE'").getResultList();
            Vector element4 = (Vector) listCode.get(0);
            Integer sCode = Integer.parseInt(element4.get(0).toString());

            float trsno = ftsBean.getTrsNo();
            float recno = ftsBean.getRecNo();

            acNature = ftsBean.getAccountNature(acno);
            double limitAmount = 0;
            List accTypeFrom = null;

            if ((acNature.equals(CbsConstant.PAY_ORDER))) {
                accTypeFrom = em.createNativeQuery("select acname,'1'accStatus,balance,'0' odlimit,''optstatus from gltable a,reconbalan r where  "
                        + "a.acno='" + acno + "' and a.acno=r.acno").getResultList();
            } else if (acNature.equals(CbsConstant.FIXED_AC) || (acNature.equals(CbsConstant.MS_AC))) {
                accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r "
                        + "where  a.acno='" + acno + "' and a.acno=r.acno").getResultList();
            } else if (acNature.equals(CbsConstant.CURRENT_AC)) {

                accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,ca_reconbalan r "
                        + "where  a.acno='" + acno + "' and a.acno=r.acno").getResultList();
                List limiamtListQuery = em.createNativeQuery("select ifnull(odlimit,0) from loan_appparameter where acno='" + acno + "'").getResultList();

                if (limiamtListQuery.size() > 0) {
                    Vector amt = (Vector) limiamtListQuery.get(0);
                    limitAmount = Double.parseDouble(amt.get(0).toString());
                }
                List adHocLimitListQuery = em.createNativeQuery("select ifnull(adhoclimit,0) from loan_appparameter where acno='" + acno
                        + "' and '" + dt + "' between Adhocapplicabledt and adhocexpiry").getResultList();
                double adhocLimit = 0;
                if (adHocLimitListQuery.size() > 0) {
                    Vector amt = (Vector) adHocLimitListQuery.get(0);
                    adhocLimit = Double.parseDouble(amt.get(0).toString());
                }
                limitAmount = limitAmount + adhocLimit;
            } else {
                accTypeFrom = em.createNativeQuery("select custName,accStatus,balance,coalesce(odlimit,'0'),optstatus from accountmaster a,reconbalan r "
                        + "where  a.acno='" + acno + "' and a.acno=r.acno").getResultList();
            }

            int fromAccSts = 0;
            double fromBalance = 0d;
            double fromOdlimit = 0d;
            if (!accTypeFrom.isEmpty()) {
                Vector accAllValues = (Vector) accTypeFrom.get(0);
                String statusValue = accAllValues.get(1).toString();

                String balanceValue = accAllValues.get(2).toString();
                //String limitValue = accAllValues.get(3).toString();
                fromAccSts = Integer.parseInt(statusValue);
                if (fromAccSts == 9) {
                    ut.rollback();
                    return "Account Is Closed";
                }

                fromBalance = Double.parseDouble(balanceValue);
                fromOdlimit = limitAmount;

                double sTax = 0d;
                if (sCode == 1) {
                    List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + orgBrnCode + "'").getResultList();
                    Vector tempCurrent = (Vector) tempBd.get(0);
                    String Tempbd = tempCurrent.get(0).toString();

                    List listBrState = em.createNativeQuery("Select ifnull(State,'') as brState from branchmaster where brncode = '" + orgBrnCode + "'").getResultList();
                    Vector branchVec = (Vector) listBrState.get(0);
                    String branchState = branchVec.get(0).toString();
                    String custState = branchVec.get(0).toString();

                    if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        List listCustState = em.createNativeQuery("select ifnull(cm.mailStateCode,'') as stateCode from cbs_customer_master_detail cm, "
                                + " customerid c where cast(cm.customerid as unsigned) = c.custid and c.acno = '" + acno + "'").getResultList();
                        Vector custVec = (Vector) listCustState.get(0);
                        custState = custVec.get(0).toString();

                        if (custState.equalsIgnoreCase(branchState)) {
                            map = sxcxBean.getTaxComponent(Double.parseDouble(chargeAmt), Tempbd);
                        } else {
                            map = sxcxBean.getIgstTaxComponent(Double.parseDouble(chargeAmt), Tempbd);
                        }
                    } else {
                        if (gstType.equalsIgnoreCase("IGST")) {
                            map = sxcxBean.getIgstTaxComponent(Double.parseDouble(chargeAmt), Tempbd);
                        } else {
                            map = sxcxBean.getTaxComponent(Double.parseDouble(chargeAmt), Tempbd);
                        }
                    }
                    Set<Map.Entry<String, Double>> set = map.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
                    }
                }
                if (!(acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                    if ((fromBalance + fromOdlimit) < (Double.parseDouble(chargeAmt) + sTax)) {
                        ut.rollback();
                        return "Balance does not sufficient to deduct charge from " + acno;
                    }
                }
                Query Q = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                        + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                        + "dest_brnid,tran_id,adviceno,advicebrncode)"
                        + "values ('" + acno + "','" + ftsBean.getCustName(acno) + "','" + dt + "','" + dt + "',0,'" + Double.parseDouble(chargeAmt) + "',1,2," + recno + ",'',null,3,0,'N',"
                        + "'" + username + "',123,0,'A','" + mainDetail + "',''," + trsno + ",now(),'','" + orgBrnCode + "','" + acno.substring(0, 2) + "',0,'','')");
                int rowAffected = Q.executeUpdate();
                if (rowAffected <= 0) {
                    ut.rollback();
                    return "Problem In Insertion Of Charge Amount " + chargeAmt + " In Recon_trf_d";
                }

                String result = ftsBean.updateBalance(acNature, acno, 0.0d, Double.parseDouble(chargeAmt), null, null);
                if (!result.equalsIgnoreCase("TRUE")) {
                    ut.rollback();
                    return "Problem In Balance Update of Account " + acno;
                }

                recno = ftsBean.getRecNo();
                Query Q1 = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                        + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                        + "dest_brnid,tran_id,adviceno,advicebrncode)"
                        + "values ('" + crHead + "','" + ftsBean.getCustName(crHead) + "','" + dt + "','" + dt + "','" + Double.parseDouble(chargeAmt) + "',0,0,2," + recno + ",'',null,3,0,'N',"
                        + "'" + username + "',71,0,'A','" + mainDetail + "',''," + trsno + ",now(),'','" + orgBrnCode + "','" + crHead.substring(0, 2) + "',0,'','')");
                int rowAffected1 = Q1.executeUpdate();
                if (rowAffected1 <= 0) {
                    ut.rollback();
                    return "Problem In Insertion Of Charge Amount " + chargeAmt + " In Recon_trf_d";
                }

                if (sCode == 1) {
//                List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + orgBrnCode + "'").getResultList();
//                Vector tempCurrent = (Vector) tempBd.get(0);
//                String Tempbd = tempCurrent.get(0).toString();
//
//                List listBrState = em.createNativeQuery("Select ifnull(State,'') as brState from branchmaster where brncode = '" + orgBrnCode + "'").getResultList();
//                Vector branchVec = (Vector) listBrState.get(0);
//                String branchState = branchVec.get(0).toString();
//                String custState = branchVec.get(0).toString();
//
//                if (!acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
//                    List listCustState = em.createNativeQuery("select ifnull(cm.mailStateCode,'') as stateCode from cbs_customer_master_detail cm, "
//                            + " customerid c where cast(cm.customerid as unsigned) = c.custid and c.acno = '" + acno + "'").getResultList();
//                    Vector custVec = (Vector) listCustState.get(0);
//                    custState = custVec.get(0).toString();
//                }
//
//                double sTax = 0d;
//                if (custState.equalsIgnoreCase(branchState)) {
//                    map = sxcxBean.getTaxComponent(Double.parseDouble(chargeAmt), Tempbd);
//                    Set<Map.Entry<String, Double>> set = map.entrySet();
//                    Iterator<Map.Entry<String, Double>> it = set.iterator();
//                    while (it.hasNext()) {
//                        Map.Entry entry = it.next();
//                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
//                    }
//                } else {
//                    map = sxcxBean.getIgstTaxComponent(Double.parseDouble(chargeAmt), Tempbd);
//                    Set<Map.Entry<String, Double>> set = map.entrySet();
//                    Iterator<Map.Entry<String, Double>> it = set.iterator();
//                    while (it.hasNext()) {
//                        Map.Entry entry = it.next();
//                        sTax = sTax + Double.parseDouble(entry.getValue().toString());
//                    }
//                }

                    String mDetail = "GST For " + chgType;
                    recno = ftsBean.getRecNo();
                    Query taxQ = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                            + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                            + "dest_brnid,tran_id,adviceno,advicebrncode)"
                            + "values ('" + acno + "','" + ftsBean.getCustName(acno) + "','" + dt + "','" + dt + "',0,'" + sTax + "',1,2," + recno + ",'',null,3,0,'N',"
                            + "'" + username + "',71,0,'A','" + mDetail + "',''," + trsno + ",now(),'','" + orgBrnCode + "','" + acno.substring(0, 2) + "',0,'','')");
                    int rowTaxAffected = taxQ.executeUpdate();
                    if (rowTaxAffected <= 0) {
                        ut.rollback();
                        return "Problem In Insertion Of Tax Amount " + sTax + " In Recon_trf_d";
                    }

                    result = ftsBean.updateBalance(acNature, acno, 0.0d, sTax, null, null);
                    if (!result.equalsIgnoreCase("TRUE")) {
                        ut.rollback();
                        return "Problem In Balance Update of Account " + acno;
                    }

                    Set<Entry<String, Double>> set = map.entrySet();
                    Iterator<Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Entry entry = it.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        String description = keyArray[0];
                        String taxHead = orgBrnCode + keyArray[1];
                        String mainDetails = description.toUpperCase() + " for " + chgType + " For " + acno;
                        double taxAmount = Double.parseDouble(entry.getValue().toString());
                        recno = ftsBean.getRecNo();
                        Query taxGlQ = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,recno,"
                                + "instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,"
                                + "dest_brnid,tran_id,adviceno,advicebrncode)"
                                + "values ('" + taxHead + "','" + ftsBean.getCustName(taxHead) + "','" + dt + "','" + dt + "','" + taxAmount + "',0,0,2," + recno + ",'',null,3,0,'N',"
                                + "'" + username + "',71,0,'A','" + mainDetails + "',''," + trsno + ",now(),'','" + orgBrnCode + "','" + orgBrnCode + "',0,'','')");
                        int rowTaxGlQ = taxGlQ.executeUpdate();
                        if (rowTaxGlQ <= 0) {
                            ut.rollback();
                            return "Problem In Insertion Of " + taxHead + " For Tax Amount " + taxAmount + " In Recon_trf_d";
                        }
                    }
                }
            } else {
                ut.rollback();
                return "Balance Not Identified For Account " + acno;
            }
            ut.commit();
            return "true" + trsno;
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

    @Override
    public List<IMPSTxnParameterPojo> getSarvatraImpsTxnParameter(String process, String mode, String frDt, String toDt, String brcode) throws ApplicationException {
        List<IMPSTxnParameterPojo> dataList = new ArrayList<>();
        try {
            String subCondition = "";
            String brncodeQuery = "";
            if (brcode.equalsIgnoreCase("0A")) {
                brncodeQuery = "";
            } else {
                brncodeQuery = "and substring(FROM_ACCOUNT_NUMBER,1,2)='" + brcode + "'";
            }
//            1)Balance Inquery			31	31
//            2)Mini Statement			36	36
//            3)Cheque Book			95	95
//            4)Statement Request		97	97
//            5)NEFT				88	88
//            6)Intra-transfer			41	40
//            7)Intra-IMPS P2P Transfer		41	45
//            8)Intra-IMPS P2A Transfer		41	48
//            9)Outward IMPS P2P Transfer	42	45
//            10)Inward IMPS P2P Transfer	43	45
//            11)Outward IMPS P2A Transfer	42	48
//            12)Inward IMPS P2A Transfer	43	48
            if (process.equalsIgnoreCase("1")) {
                subCondition = "and PROCESSING_CODE = 31 and TXN_TYPE = 31";
            } else if (process.equalsIgnoreCase("2")) {
                subCondition = "and PROCESSING_CODE = 36 and TXN_TYPE = 36";
            } else if (process.equalsIgnoreCase("3")) {
                subCondition = "and PROCESSING_CODE = 95 and TXN_TYPE = 95";
            } else if (process.equalsIgnoreCase("4")) {
                subCondition = "and PROCESSING_CODE = 97 and TXN_TYPE = 97";
            } else if (process.equalsIgnoreCase("5")) {
                subCondition = "and PROCESSING_CODE = 88 and TXN_TYPE = 88";
            } else if (process.equalsIgnoreCase("6")) {
                subCondition = "and PROCESSING_CODE = 41 and TXN_TYPE = 40";
            } else if (process.equalsIgnoreCase("7")) {
                subCondition = "and PROCESSING_CODE = 41 and TXN_TYPE = 45";
            } else if (process.equalsIgnoreCase("8")) {
                subCondition = "and PROCESSING_CODE = 41 and TXN_TYPE = 48";
            } else if (process.equalsIgnoreCase("9")) {
                subCondition = "and PROCESSING_CODE = 42 and TXN_TYPE = 45";
            } else if (process.equalsIgnoreCase("10")) {
                subCondition = "and PROCESSING_CODE = 43 and TXN_TYPE = 45";
            } else if (process.equalsIgnoreCase("11")) {
                subCondition = "and PROCESSING_CODE = 42 and TXN_TYPE = 48";
            } else if (process.equalsIgnoreCase("12")) {
                subCondition = "and PROCESSING_CODE = 43 and TXN_TYPE = 48";
            }
            List repDataList = em.createNativeQuery("select cast(TRSNO as unsigned) batchNo,FROM_ACCOUNT_NUMBER,TO_ACCOUNT_NUMBER,RRN,"
                    + "REMITTER_NBIN,date_format(TRAN_DATE,'%d/%m/%Y'),AMOUNT,"
                    + "BEN_IFSC_CODE,BEN_NAME,TRAN_TIME,DATE_FORMAT(TRAN_TIME,'%T'),IN_PROCESS_FLAG,IN_PROCESS_STATUS,RESERVED_2,REVERSAL_INDICATOR  "
                    + "from imps_txn_parameter where TRAN_DATE between '" + frDt + "' and '" + toDt + "' "
                    + "" + subCondition + " "
                    + "and REVERSAL_INDICATOR = " + mode + " " + brncodeQuery + "").getResultList();
            if (!repDataList.isEmpty()) {
                for (int i = 0; i < repDataList.size(); i++) {
                    Vector vtr = (Vector) repDataList.get(i);
                    IMPSTxnParameterPojo pojo = new IMPSTxnParameterPojo();
                    pojo.setSrNo(i + 1);
                    //REVERSAL_INDICATOR 0 for Normal and 1 for Reversal
                    String reversalIndicator = vtr.get(14).toString();
                    pojo.setTrsBatch(vtr.get(0).toString());
                    pojo.setFromAcNo(vtr.get(1).toString());
                    try {
                        String custName = ftsBean.getCustName(vtr.get(1).toString());
                        //System.out.println("Cust Name = " + custName);
                        pojo.setCustName(custName);
                    } catch (Exception e) {
                        pojo.setCustName("");
                    }
                    double reversalAmt = 0;
                    if (reversalIndicator.equalsIgnoreCase("1")) {
                        List reversalList = em.createNativeQuery("select ifnull(amount,0) from imps_txn_parameter where RRN = " + vtr.get(3).toString() + " "
                                + "and date_format(TRAN_DATE,'%Y%m%d') = '" + ymd.format(dmyy.parse(vtr.get(5).toString())) + "' and reversal_indicator = 0").getResultList();
                        if (!reversalList.isEmpty()) {
                            Vector ele = (Vector) reversalList.get(0);
                            reversalAmt = Double.parseDouble(ele.get(0).toString()) - Double.parseDouble(vtr.get(6).toString());
                        }
                    }

                    pojo.setToAcno(vtr.get(2).toString());
                    String beneficiaryName = vtr.get(8).toString();
                    String status = vtr.get(11).toString();
                    if (status.equalsIgnoreCase("S") && beneficiaryName.equalsIgnoreCase("")) {
                        try {
                            beneficiaryName = ftsBean.getCustName(vtr.get(2).toString());
                        } catch (Exception e) {
                            beneficiaryName = "";
                        }
                    }
                    pojo.setBeneficiary_name(beneficiaryName);
                    pojo.setBeneficiary_ifsc(vtr.get(7).toString());
                    pojo.setRrn(vtr.get(3).toString());
                    pojo.setRemitterNbin(vtr.get(4).toString());
                    pojo.setTranDate(vtr.get(5).toString());
                    if (reversalIndicator.equalsIgnoreCase("1")) {
                        pojo.setAmount(new BigDecimal(reversalAmt));
                    } else {
                        pojo.setAmount(new BigDecimal(vtr.get(6).toString()));
                    }
                    pojo.setTranTime(vtr.get(10).toString());
                    pojo.setStatus(status.equalsIgnoreCase("S") ? "Success" : "Failed");


                    // Transaction Narration
                    //Based on transaction details split-up of input parameter:-
                    //P2A Outward - 42	48 (11)
                    //  IMPS/RRN/Channel Payee Name/Payee Acct No/Payee IFSC/Remark/Payee Bank
                    //P2P Outward  - 42	45 (9)
                    //  IMPS/RRN/Channel Payee Name/Payee Mobile No/Payee MMID/Remark/Payee Bank
                    //P2A Inward - 43	48 (12)
                    //  IMPS/RRN/Payer Name/Payer Mobile No/Payer Acct No/Remark/Payer Bank
                    //P2P Inward 43	45 (10)
                    //  IMPS/RRN/Payer Name/Payer Mobile No/Payer Acct No/Remark/Payer Bank
                    //Within Transfer 41   40 (6)
                    //  IMPS/RRN/Payer Name to Payee Name/Remark
                    //VAS
                    //  BIL/RCHG/RRN/Operator Name/Consumer No
                    //Note :- Default value for bank would be “Unknown bank” and name would be “Unregistered”
                    String naration = vtr.get(13).toString();
                    if (process.equals("11") && !naration.equals("")) {
                        String[] tmpArr = naration.split("/");
                        pojo.setToAcno(tmpArr[3]);
                        pojo.setBeneficiary_name(tmpArr[2]);
                        naration = tmpArr[0] + "/" + tmpArr[5] + "/" + tmpArr[6];
                    }
                    if (process.equals("12") && !naration.equals("")) {
                        String[] tmpArr = naration.split("/");
                        pojo.setFromAcNo(tmpArr[4]);
                        pojo.setCustName(tmpArr[2]);
                        naration = tmpArr[0] + "/" + tmpArr[3] + "/" + tmpArr[5] + "/" + tmpArr[6];
                    }
                    pojo.setReserved_2(naration);
                    dataList.add(pojo);
                }
            }
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List<AtmDisputePojo> getAtmDisputeReportData(String brnCode, String frDt, String toDt) throws ApplicationException {
        List<AtmDisputePojo> dataList = new ArrayList<>();
        try {
            String branchCondition = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                branchCondition = "";
            } else {
                branchCondition = "and substring(a.FROM_ACCOUNT_NUMBER,1,2)='" + brnCode + "'";
            }

            List list = em.createNativeQuery("select a.FROM_ACCOUNT_NUMBER,b.CaseNumber,b.PrimaryAccountNumber,b.TransactionDate,b.TransactionAmount,b.SettlementAmount, "
                    + "b.ControlNumber,b.DisputeOriginatorPID,b.DisputeDestinationPID,b.AcquirerReferenceDataRRN,ifnull(a.RRN,''),cast(b.trsno as unsigned),b.reason,b.ReportDate,b.DisputeAmount,c.custname from atm_normal_transaction_parameter a,atm_dispute_details b,accountmaster c where a.RRN = trim(substring(AcquirerReferenceDataRRN,26,13)) "
                    + "and a.from_account_number = c.acno and date_format(ProcessTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' "
                    + "and a.IN_PROCESS_FLAG = 'S' " + branchCondition + " "
                    + "/*union all "
                    + "select ''FROM_ACCOUNT_NUMBER,b.CaseNumber,b.PrimaryAccountNumber,b.TransactionDate,b.TransactionAmount,b.SettlementAmount, "
                    + "b.ControlNumber,b.DisputeOriginatorPID,b.DisputeDestinationPID,b.AcquirerReferenceDataRRN,trim(substring(b.AcquirerReferenceDataRRN,26,13)) as rrn, cast(b.trsno as unsigned) as batchNo, "
                    + "b.reason,b.ReportDate,b.DisputeAmount,'' custname from  atm_dispute_details b where b.trsno=0 and date_format(ProcessTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'*/ order by 12,1").getResultList();

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    AtmDisputePojo pojo = new AtmDisputePojo();
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(15).toString());
                    pojo.setCaseNumber(vtr.get(1).toString());
                    pojo.setPrimaryAccountNumber(vtr.get(2).toString());
                    pojo.setTransactionDate(vtr.get(3).toString());
                    pojo.setTransactionAmount(new BigDecimal(vtr.get(4).toString()));

                    pojo.setControlNumber(vtr.get(6).toString());
                    pojo.setDisputeOriginatorPID(vtr.get(7).toString());
                    pojo.setDisputeDestinationPID(vtr.get(8).toString());
                    pojo.setAcquirerReferenceDataRRN(vtr.get(10).toString());
                    pojo.setTrsno(Integer.parseInt(vtr.get(11).toString()));
                    pojo.setReason(vtr.get(12).toString());
                    pojo.setReportDate(vtr.get(13).toString());
                    pojo.setSettlementAmount(new BigDecimal(vtr.get(14).toString()));
                    dataList.add(pojo);
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }

    public List<TdsOnCashWithdrawalPojo> getTdsOnWithdrawalDetails(String brncode, String fromdate, String toDate, String financialFromDate, String financialToDate) throws ApplicationException {
        List<TdsOnCashWithdrawalPojo> dataList = new ArrayList();
        String branchCodeQuery = "";
        if (brncode.equalsIgnoreCase("0A")) {
            branchCodeQuery = "";
        } else {
            branchCodeQuery = "and c.curbrcode = '" + brncode + "'";
        }
        Map<String, BigDecimal> map = new HashMap();
        try {
            List lis = em.createNativeQuery("select a.PAN_GIRNumber as PanNo,sum(cashWithdrawal) "
                    + "from cbs_customer_master_detail a,customerid b, "
                    + "(select g.cid,g.acno, cast(sum(g.amt) as decimal(14,2)) as cashWithdrawal,g.curbrcode from "
                    + "(select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.accstatus = 1 or a.closingdate between '" + fromdate + "' and '" + financialToDate + "') and am.acctnature in ('SB') and t.acno = a.acno and dt between '" + fromdate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR t.details like 'NFS ATM WITHDRAWAL%' OR t.details like "
                    + "'ATM NORMAL TRANSACTION%' OR t.details like 'NFS ATM REVERSAL%' OR t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR t.details like 'NORMAL REVERSAL TRANSACTION' OR t.details like "
                    + "'NFS REVERSAL TRANSACTION%'))) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.accstatus = 1 or a.closingdate between '" + fromdate + "' and '" + financialToDate + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between '" + fromdate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR t.details like 'NFS ATM WITHDRAWAL%' OR t.details like "
                    + "'ATM NORMAL TRANSACTION%' OR t.details like 'NFS ATM REVERSAL%' OR t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR t.details like 'NORMAL REVERSAL TRANSACTION' OR t.details like "
                    + "'NFS REVERSAL TRANSACTION%'))) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.accstatus = 1 or a.closingdate between '" + fromdate + "' and '" + financialToDate + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between '" + fromdate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.accstatus = 1 or a.closingdate between '" + fromdate + "' and '" + financialToDate + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between '" + fromdate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.accstatus = 1 or a.closingdate between '" + fromdate + "' and '" + financialToDate + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between '" + fromdate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.accstatus = 1 or a.closingdate between '" + fromdate + "' and '" + financialToDate + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between '" + fromdate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno) g group by g.cid,g.acno) c "
                    + "where b.acno = c.acno and b.custid = cast(a.customerid as unsigned) "
                    + "group by a.PAN_GIRNumber having(sum(cashWithdrawal)>10000000)").getResultList();

            if (!lis.isEmpty()) {
                for (int j = 0; j < lis.size(); j++) {
                    Vector vector = (Vector) lis.get(j);
                    map.put(vector.get(0).toString(), new BigDecimal(vector.get(1).toString()));
                }
            }
            List list = em.createNativeQuery("select aa.CustId,aa.acno,aa.custFullName,aa.PanNo,aa.cashWithdrawal,ifnull(bb.SubsequnceCashWithdrawal,0),ifnull(cc.tds,0),aa.curbrcode from "
                    + "(select cast(a.customerid as unsigned) as CustId,c.acno,a.custFullName,a.PAN_GIRNumber as PanNo,cashWithdrawal,c.curbrcode "
                    + "from cbs_customer_master_detail a,customerid b, "
                    + "(select g.cid,g.acno, cast(sum(g.amt) as decimal(14,2)) as cashWithdrawal,g.curbrcode from "
                    + "(select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('SB') and t.acno = a.acno and dt between '" + fromdate + "' and '" + toDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9) OR "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR t.details like 'NFS ATM WITHDRAWAL%' OR t.details like "
                    + "'ATM NORMAL TRANSACTION%' OR t.details like 'NFS ATM REVERSAL%' OR t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR t.details like 'NORMAL REVERSAL TRANSACTION' OR t.details like "
                    + "'NFS REVERSAL TRANSACTION%'))) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between '" + fromdate + "' and '" + toDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9) OR  "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR t.details like 'NFS ATM WITHDRAWAL%' OR t.details like "
                    + "'ATM NORMAL TRANSACTION%' OR t.details like 'NFS ATM REVERSAL%' OR t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR t.details like 'NORMAL REVERSAL TRANSACTION' OR t.details like "
                    + "'NFS REVERSAL TRANSACTION%'))) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between '" + fromdate + "' and '" + toDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between '" + fromdate + "' and '" + toDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between '" + fromdate + "' and '" + toDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt,a.curbrcode from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between '" + fromdate + "' and '" + toDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno) g group by g.cid,g.acno) c "
                    + "where b.acno = c.acno and b.custid = cast(a.customerid as unsigned) " + branchCodeQuery
                    + "order by a.PAN_GIRNumber,cast(a.customerid as unsigned))aa "
                    + "left join "
                    + "(select g.cid,g.acno, cast(sum(g.amt) as decimal(14,2)) as SubsequnceCashWithdrawal from "
                    + "(select c.custid as cid, a.acno, sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('SB') and t.acno = a.acno and dt between '" + financialFromDate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9) OR  "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR t.details like 'NFS ATM WITHDRAWAL%' OR t.details like "
                    + "'ATM NORMAL TRANSACTION%' OR t.details like 'NFS ATM REVERSAL%' OR t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR t.details like 'NORMAL REVERSAL TRANSACTION' OR t.details like "
                    + "'NFS REVERSAL TRANSACTION%'))) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('CA') and t.acno = a.acno and dt between '" + financialFromDate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9) OR  "
                    + "(t.trandesc = 70 and (t.details like 'ATM CASH WITHDRAWAL AT%' OR t.details like 'NFS ATM WITHDRAWAL%' OR t.details like "
                    + "'ATM NORMAL TRANSACTION%' OR t.details like 'NFS ATM REVERSAL%' OR t.details like 'ATM CASH WITHDRAWAL REVERSAL AT%' OR "
                    + "t.details like 'NFS ISSUER WITHDRAWAL REVERSAL AT%' OR t.details like 'NORMAL REVERSAL TRANSACTION' OR t.details like "
                    + "'NFS REVERSAL TRANSACTION%'))) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('RD') and t.acno = a.acno and dt between '" + financialFromDate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt between '" + financialFromDate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno\n"
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('DS') and t.acno = a.acno and dt between '" + financialFromDate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno, sum(dramt) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + toDate + "') and am.acctnature in ('FD','MS') and t.acno = a.acno and dt between '" + financialFromDate + "' and '" + financialToDate + "' "
                    + "and (t.trantype = 0 and t.ty = 1 OR (t.ty=0 AND t.trandesc = 9)) group by c.custid, a.acno) g group by g.cid,g.acno)bb "
                    + "on aa.acno = bb.acno "
                    + "left join "
                    + "(select acno, sum(dramt) as tds from recon where trandesc = 136 and dt between '" + financialFromDate + "' and '" + financialToDate + "' group by acno "
                    + "union "
                    + "select acno, sum(dramt) as tds from ca_recon where trandesc = 136 and dt between '" + financialFromDate + "' and '" + financialToDate + "' group by acno "
                    + "union "
                    + "select acno, sum(dramt) as tds from rdrecon where trandesc = 136 and dt between '" + financialFromDate + "' and '" + financialToDate + "' group by acno "
                    + "union "
                    + "select acno, sum(dramt) as tds from loan_recon where trandesc = 136 and dt between '" + financialFromDate + "' and '" + financialToDate + "' group by acno "
                    + "union "
                    + "select acno, sum(dramt) as tds from td_recon where trandesc = 136 and dt between '" + financialFromDate + "' and '" + financialToDate + "' group by acno "
                    + "union "
                    + "select acno, sum(dramt) as tds from ddstransaction where trandesc = 136 and dt between '" + financialFromDate + "' and '" + financialToDate + "' group by acno) cc "
                    + "on aa.acno = cc.acno order by 4,1").getResultList();

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    String pan = vec.get(3).toString();

                    if (map.containsKey(pan)) {
                        TdsOnCashWithdrawalPojo pojo = new TdsOnCashWithdrawalPojo();
                        pojo.setCustomerid(vec.get(0).toString());
                        pojo.setAcno(vec.get(1).toString());
                        pojo.setName(vec.get(2).toString());
                        pojo.setPanno(vec.get(3).toString());
                        pojo.setCashwithdrawal(BigDecimal.valueOf(Double.valueOf(vec.get(4).toString())));
                        pojo.setSubsequenceCashWithDrawal(BigDecimal.valueOf(Double.valueOf(vec.get(5).toString())));
                        BigDecimal sum = pojo.getCashwithdrawal().add(pojo.getSubsequenceCashWithDrawal());
                        BigDecimal value = sum.multiply(BigDecimal.valueOf(Double.valueOf("0.02")));
                        pojo.setSumofcashnsubamt(sum);
                        pojo.setTdstobededucted(value);
                        pojo.setTdsdeducted(BigDecimal.valueOf(Double.valueOf(vec.get(6).toString())));
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<CreditBalancePojo> getCreditBalanceDetails(String brncode, String fromDate, String toDate, String reportoption) throws ApplicationException {
        List<CreditBalancePojo> list = new ArrayList();
        List data = new ArrayList();

        String branchcode = "";
        if (brncode.equalsIgnoreCase("0A")) {
            branchcode = "";
        } else {
            branchcode = " and a.CurBrCode= '" + brncode + "'";
        }

        if (reportoption.equalsIgnoreCase("2")) {

            data = em.createNativeQuery("select g.cid,g.custname, cast(sum(g.amt) as decimal(14,2)) as depositBal from "
                    + "(select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, recon t  "
                    + "where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('SB') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('CA') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno having cast(sum(cramt-dramt) as decimal(25,2)) >0 "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('RD') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno having cast(sum(cramt-dramt) as decimal(25,2)) >0 "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('DS') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('FD','MS') and t.acno = a.acno  and t.Trantype<>27 and t.closeflag is Null "
                    + "and dt <='" + toDate + "' " + branchcode + " group by c.custid, a.acno) "
                    + "g group by g.cid order by g.cid ").getResultList();
        } else if (reportoption.equalsIgnoreCase("1")) {

            data = em.createNativeQuery("select g.cid,g.acno,g.custname, cast(sum(g.amt) as decimal(14,2)) as depositBal from "
                    + "(select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, recon t  "
                    + "where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('SB') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno  "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, ca_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('CA') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno having cast(sum(cramt-dramt) as decimal(25,2)) >0 "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, rdrecon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('RD') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, loan_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('TL','DL') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno having cast(sum(cramt-dramt) as decimal(25,2)) >0 "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from accountmaster a, customerid c, accounttypemaster am, ddstransaction t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('DS') and t.acno = a.acno and dt <='" + toDate + "' " + branchcode
                    + "group by c.custid, a.acno "
                    + "union "
                    + "select c.custid as cid, a.acno,a.custname, cast(sum(cramt-dramt) as decimal(25,2)) as amt from td_accountmaster a, customerid c, accounttypemaster am, td_recon t where am.acctcode = a.accttype and a.acno = c.acno "
                    + "and (ifnull(a.closingdate,'')='' or a.closingdate>'" + toDate + "') and am.acctnature in ('FD','MS') and t.acno = a.acno  and t.Trantype<>27 and t.closeflag is Null "
                    + "and dt <='" + toDate + "'" + branchcode + " group by c.custid, a.acno) "
                    + "g group by g.cid,g.acno order by g.cid,g.acno").getResultList();
        }

        if (!data.isEmpty()) {
            if (reportoption.equalsIgnoreCase("2")) {
                for (int i = 0; i < data.size(); i++) {
                    CreditBalancePojo pojo = new CreditBalancePojo();
                    Vector vector = (Vector) data.get(i);
                    pojo.setCustomerid(vector.get(0).toString());
                    pojo.setCustomerName(vector.get(1).toString());
                    pojo.setDepositbal(BigDecimal.valueOf(Double.valueOf(vector.get(2).toString())));
                    list.add(pojo);
                }
            } else if (reportoption.equalsIgnoreCase("1")) {
                for (int i = 0; i < data.size(); i++) {
                    CreditBalancePojo pojo = new CreditBalancePojo();
                    Vector vector = (Vector) data.get(i);
                    pojo.setCustomerid(vector.get(0).toString());
                    pojo.setAcNo(vector.get(1).toString());
                    pojo.setCustomerName(vector.get(2).toString());
                    pojo.setDepositbal(BigDecimal.valueOf(Double.valueOf(vector.get(3).toString())));
                    list.add(pojo);
                }
            }
        }

        return list;
    }
}
