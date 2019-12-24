package com.cbs.facade.cdci;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.cdci.AccountMasterPojo;
import com.cbs.dto.cdci.AccountStatementPojo;
import com.cbs.dto.cdci.AccountTypeMasterPojo;
import com.cbs.dto.cdci.CommissionPojo;
import com.cbs.dto.cdci.CustomerDetailsPojo;
import com.cbs.dto.cdci.CustomerIdPojo;
import com.cbs.dto.cdci.CustomerMasterDetailsPojo;
import com.cbs.dto.cdci.DocumentDetailsPojo;
import com.cbs.dto.cdci.MiniStatementPojo;
import com.cbs.dto.cdci.NomineeDetailsPojo;
import com.cbs.dto.cdci.RdAcountDetailPojo;
import com.cbs.dto.cdci.TransactionsPojo;
import com.cbs.dto.TxnDetailBean;
import com.cbs.dto.cdci.RequestStatusObj;
import com.cbs.dto.neftrtgs.ChannelReplyDto;
import com.cbs.dto.neftrtgs.ImpsOwResponseDto;
import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.admin.FdDdsAccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.inventory.ChequeMaintinanceRegisterFacadeRemote;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.ibl.util.IblUtil;
import com.cbs.utils.CbsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class IBSWSFacade implements IBSWSFacadeRemote {

    @PersistenceContext
    protected EntityManager entityManager;
    @Resource
    EJBContext context;
    @Resource
    SessionContext ctx;
    @EJB
    private AccountOpeningFacadeRemote accountOpeningFacadeRemote;
    @EJB
    private FdDdsAccountOpeningFacadeRemote fdDdsAccountOpeningFacade;
    @EJB
    private TdReceiptManagementFacadeRemote tdReceiptMgmtFacade;
    @EJB
    TermDepositeCalculationManagementFacadeRemote tdCalcMgmtFacade;
    @EJB
    private AccountAuthorizationManagementFacadeRemote actAuthMgmtFacade;
    @EJB
    private TransactionManagementFacadeRemote txnMgmtFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPostingMgmtFacade;
    @EJB
    private TxnAuthorizationManagementFacadeRemote txnAuthMgmtFacade;
    @EJB
    private ChequeMaintinanceRegisterFacadeRemote cmrFacade;
    @EJB
    private AcctEnqueryFacadeRemote acctEnqueryFacadeRemote;
    @EJB
    private MiscReportFacadeRemote miscFacade;
    @EJB
    private CommonReportMethodsRemote commonReport;
//    @EJB
//    private InventorySplitAndMergeFacadeRemote invtSplitRemote;
//    @EJB
//    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private NeftRtgsMgmtFacadeRemote neftRemote;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat ymd_Format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private Date date = new Date();
    private String dateString = ymdFormat.format(date);
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getHOAddress() {
        try {
            Query selectQuery = entityManager.createNativeQuery("select address from branchmaster where branchname='HO'");
            List list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                if (ele.get(0) != null) {
                    return ele.get(0).toString();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List getBranchNameandAddress(String orgnbrcode) {
        List returnList = null;
        try {
            Query selectQuery = entityManager.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br "
                    + "WHERE b.alphacode=br.alphacode and br.brncode=" + Integer.parseInt(orgnbrcode));
            List list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                returnList = new ArrayList();
                returnList.add(ele.get(0));
                returnList.add(ele.get(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

    public List<MiniStatementPojo> getMiniStatement(String acNo) throws ApplicationException {
        List<MiniStatementPojo> miniStatement = new ArrayList<MiniStatementPojo>();
        try {
            double bal = 0.0;
            int trantype = 0, ty = 0;
            String particulars = "";
            double cramt = 0, dramt = 0;

            SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String accountNature = ftsPostingMgmtFacade.getAccountNature(acNo);

            //String reconTable = getTableName(accountNature);
            String reconTable = commonReport.getTableName(accountNature);

            List resultList = entityManager.createNativeQuery("select date_format(min(tmp.dt),'%Y%m%d') from (select acno,dt from " + reconTable
                    + " where acno='" + acNo + "' order by dt desc limit 10) tmp").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
            Vector vect = (Vector) resultList.get(0);
            String minDt = vect.get(0).toString() == null ? ymmd.format(date) : vect.get(0).toString();

            String preDt = CbsUtil.dateAdd(minDt, -1);
//            double balance = fnBalosForAccountAsOn(acNo, preDt);

            BigDecimal balance = getBalanceByAccountNumber(acNo, preDt);
            List dataList = entityManager.createNativeQuery("select date_format(dt,'%d/%m/%Y') ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,"
                    + "recno from " + reconTable + " where acno='" + acNo + "' and auth='y' order by trantime DESC limit 10").getResultList();

            if (dataList.isEmpty()) {
                throw new ApplicationException("Transaction does not exist.");
            }
            //int l = 1;
            for (int i = dataList.size() - 1; i >= 0; i--) {
                String p1 = "", p2 = "";
                Vector vec = (Vector) dataList.get(i);
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
                // l = l + 1;
                balance = (balance.add(new BigDecimal(cramt))).subtract(new BigDecimal(dramt));
                particulars = p1 + p2 + (vec.get(1) == null ? "" : vec.get(1).toString());
                MiniStatementPojo pojo = new MiniStatementPojo();
                pojo.setDate1(vec.get(0) == null ? sdf.format(date) : vec.get(0).toString());
                pojo.setParticulars(particulars);
                pojo.setChequeNo((vec.get(2) == null || vec.get(2).equals("0000000000")) ? "" : vec.get(2).toString());
                pojo.setWithdrawl(dramt);
                pojo.setDeposit(cramt);
                pojo.setBalance(bal);
                miniStatement.add(pojo);
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return miniStatement;
    }

    public List<TransactionsPojo> unclearedTransactionsList(String accountNo) throws ApplicationException {
        List<TransactionsPojo> finalList = new ArrayList<TransactionsPojo>();
        try {
            List result = acctEnqueryFacadeRemote.tableAccountWise(accountNo);
            for (int i = 0; i < result.size(); i++) {
                TransactionsPojo idPojo = new TransactionsPojo();
                Vector vtr = (Vector) result.get(i);
                if (vtr.get(0) != null) {
                    idPojo.setTxnInstNo(vtr.get(0).toString());
                }
                if (vtr.get(1) != null) {
                    idPojo.setTxnInstAmt(vtr.get(1).toString());
                }
                if (vtr.get(2) != null) {
                    idPojo.setTxnDate(vtr.get(2).toString());
                }
                finalList.add(idPojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalList;
    }

    public String getAccountNatureByAccountType(String acctType) throws ApplicationException {
        try {
            List acctNatureList = new ArrayList();
            acctNatureList = entityManager.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acctType + "'").getResultList();
            if (!acctNatureList.isEmpty()) {
                return ((Vector) acctNatureList.get(0)).get(0).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    public List findServiceTax1() throws ApplicationException {
//        List returnList = null;
//        try {
//            List list1 = entityManager.createNativeQuery("select TOP 1 CommFlag,RoundUpto from taxmaster where ROTApplyOn='C'and applicableFlag='Y' "
//                    + "and auth='Y' and applicableDt <= '" + dateString + "'").getResultList();
//            if (!list1.isEmpty()) {
//                returnList = new ArrayList();
//                Vector ele = (Vector) list1.get(0);
//                returnList.add(ele.get(0));
//                returnList.add(ele.get(1));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnList;
//    }
//    public List fnTaxApplicableROT() throws ApplicationException {
//        List returnList = null;
//        try {
//            List list = entityManager.createNativeQuery("select ifnull(TYPE,'N/A'),ifnull(ROT,0),ifnull(ROTApplyOn,'N/A'),ifnull(glhead,'') from "
//                    + "taxmaster where ApplicableDt<='" + dateString + "' and applicableFlag='Y' and Auth='Y'").getResultList();
//            if (!list.isEmpty()) {
//                returnList = new ArrayList();
//                Vector ele = (Vector) list.get(0);
//                returnList.add(ele.get(0));
//                returnList.add(ele.get(1));
//                returnList.add(ele.get(2));
//                returnList.add(ele.get(3));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnList;
//    }
    //TODO later take carew of this 
    public String taxAmountProcedure(double amt, String type, int rUpto) throws ApplicationException {
        return "";// txnMgmtFacade.taxAmountProcedure(amt, type, rUpto);
    }

    public List<CommissionPojo> getCommission(String bill, int payby) throws ApplicationException {
        List<CommissionPojo> commissionPojoList = new ArrayList<CommissionPojo>();
        try {
            List list = entityManager.createNativeQuery("select ifnull(amountfrom,0),ifnull(amountto,0),commcharge,ifnull(surcharge,0),mincharge,"
                    + "commflag from bill_commission where slabdate = (select max(slabdate) from bill_commission where slabdate <= '" + dateString
                    + "' and collecttype='" + bill + "') and collecttype='" + bill + "' and payby=" + payby + " order by amountfrom").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                CommissionPojo pojo = new CommissionPojo();
                if (ele.get(0) != null) {
                    pojo.setAmountFrom(new Double(ele.get(0).toString()));
                }
                if (ele.get(1) != null) {
                    pojo.setAmountTo(new Double(ele.get(1).toString()));
                }
                if (ele.get(2) != null) {
                    pojo.setCommCharge(new Double(ele.get(2).toString()));
                }
                if (ele.get(3) != null) {
                    pojo.setSurCharge(new Double(ele.get(3).toString()));
                }
                if (ele.get(4) != null) {
                    pojo.setMinCharge(new Double(ele.get(4).toString()));
                }
                if (ele.get(5) != null) {
                    String com = ele.get(5).toString();
                    if (com.length() > 0) {
                        pojo.setCommFlag(com.charAt(0));
                    }
                }
                commissionPojoList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commissionPojoList;
    }

    public List<AccountTypeMasterPojo> getAccountTypeMasterPojoList(String acType) throws ApplicationException {
        List<AccountTypeMasterPojo> accountTypeMasterPojoList = new ArrayList<AccountTypeMasterPojo>();
        try {
            List resultList = entityManager.createNativeQuery("select AcctCode,AcctDesc,MaxPay,MinBal,MinBal_chq,"
                    + "MinInt,staffint,maxLendIntPeriod,ChequeBounceAmt,MinIntPeriod,CrDbFlag,penalty,chqsrno,acctNature,"
                    + "GLHead,GLHeadInt,ofAcctnature,GLHeadProv,ProductCode,LoanAuth from accounttypemaster where AcctCode='" + acType + "'").getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                AccountTypeMasterPojo ibsas = new AccountTypeMasterPojo();
                Vector ele = (Vector) resultList.get(i);
                if (ele.get(0) != null) {
                    ibsas.setAcctCode(ele.get(0).toString());
                }
                if (ele.get(1) != null) {
                    ibsas.setAcctDesc(ele.get(1).toString());
                }
                if (ele.get(2) != null) {
                    ibsas.setMaxPay(Integer.parseInt(ele.get(2).toString()));
                }
                if (ele.get(3) != null) {
                    ibsas.setMinBal(Integer.parseInt(ele.get(3).toString()));
                }
                if (ele.get(4) != null) {
                    ibsas.setMinBalChq(Float.parseFloat(ele.get(4).toString()));
                }
                if (ele.get(5) != null) {
                    ibsas.setMinInt(Float.parseFloat(ele.get(5).toString()));
                }
                if (ele.get(6) != null) {
                    ibsas.setStaffInt(Float.parseFloat(ele.get(6).toString()));
                }
                if (ele.get(7) != null) {
                    ibsas.setMaxLendIntPeriod(Integer.parseInt(ele.get(7).toString()));
                }
                if (ele.get(8) != null) {
                    ibsas.setChequeBounceAmt(Float.parseFloat(ele.get(8).toString()));
                }
                if (ele.get(9) != null) {
                    ibsas.setMinIntPeriod(Integer.parseInt(ele.get(9).toString()));
                }
                if (ele.get(10) != null) {
                    ibsas.setCrDbFlag(ele.get(10).toString());
                }
                if (ele.get(11) != null) {
                    ibsas.setPenalty(Float.parseFloat(ele.get(11).toString()));
                }
                if (ele.get(12) != null) {
                    ibsas.setChqSrNo(ele.get(12).toString());
                }
                if (ele.get(13) != null) {
                    ibsas.setAcctNature(ele.get(13).toString());
                }
                if (ele.get(14) != null) {
                    ibsas.setgLHead(ele.get(14).toString());
                }
                if (ele.get(15) != null) {
                    ibsas.setgLHeadInt(ele.get(15).toString());
                }
                if (ele.get(16) != null) {
                    ibsas.setOfAcctNature(ele.get(16).toString());
                }
                if (ele.get(17) != null) {
                    ibsas.setgLHeadProv(ele.get(17).toString());
                }
                if (ele.get(18) != null) {
                    ibsas.setProductCode(ele.get(18).toString());
                }
                if (ele.get(19) != null) {
                    ibsas.setLoanAuth(ele.get(19).toString());
                }
                accountTypeMasterPojoList.add(ibsas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountTypeMasterPojoList;
    }

    public List<AccountStatementPojo> getAccountStatement(String acNo, String fromDt, String toDt) throws ApplicationException {
        List<AccountStatementPojo> iBSAccountStatement = new ArrayList<AccountStatementPojo>();
        try {
            List<AccountStatementReportPojo> resultList = miscFacade.getAccountStatementReport(acNo, fromDt, toDt);
            for (AccountStatementReportPojo pojo : resultList) {
                AccountStatementPojo ibsas = new AccountStatementPojo();
                ibsas.setAcNo(pojo.getAcNo());
                ibsas.setCustName(pojo.getCustName());
                ibsas.setAcType(pojo.getAcType());

                ibsas.setJtName(pojo.getJtName());
                ibsas.setNomination(pojo.getNomination());
                ibsas.setCrAdd(pojo.getCrAdd());

                ibsas.setPrAdd(pojo.getPrAdd());
                ibsas.setPendingBal(pojo.getPendingBal());
                ibsas.setOpeningBal(pojo.getOpeningBal());

                ibsas.setDate(pojo.getDate());
                ibsas.setParticulars(pojo.getParticulars());
                ibsas.setChequeNo(pojo.getChequeNo());

                ibsas.setWithdrawl(pojo.getWithdrawl());
                ibsas.setDeposit(pojo.getDeposit());
                ibsas.setBalance(pojo.getBalance());

                ibsas.setValueDate(pojo.getValueDate());

                ibsas.setPerStatus(pojo.getPerStatus());
                ibsas.setAcStatusDesc(pojo.getAcStatusDesc());
                ibsas.setAcStatus(pojo.getAcStatus());

                ibsas.setAcNature(pojo.getAcNature());
                ibsas.setJtName2(pojo.getJtName2());
                ibsas.setJtName3(pojo.getJtName3());
                ibsas.setJtName4(pojo.getJtName4());
                ibsas.setGstrn(pojo.getGstrn());
                iBSAccountStatement.add(ibsas);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return iBSAccountStatement;
    }

    public List getCurrentTDSlab(String acctNature) throws ApplicationException {
        List returnList = new ArrayList();
        try {
            List list = entityManager.createNativeQuery("select min(fromdays),max(todays),min(fromamount),max(toamount) from td_slab where "
                    + "acctNature ='" + acctNature + "' and applicable_date = (select max(applicable_date) from td_slab where acctNature='" + acctNature + "')").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                for (int i = 0; i < ele.size(); i++) {
                    if (ele.get(i) != null) {
                        returnList.add(ele.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public String tdInterestCalculation(String InterestOption, float RoInt, String FDDate, String MatDate, double amt, String prd) throws ApplicationException {
        return tdReceiptMgmtFacade.orgFDInterest(InterestOption, RoInt, FDDate, MatDate, amt, prd, "");
    }

    public float getTDRoi(String custCat, Float tOTAMT, String matDt, String wefDt, String presentDt, String acNat) throws ApplicationException {
        return tdReceiptMgmtFacade.tdApplicableROI("", custCat, tOTAMT, matDt, wefDt, presentDt, acNat);
    }

    public float getRDRoi(double amount, long days) throws ApplicationException {
        List result = accountOpeningFacadeRemote.getRDRoi(amount, days);
        float roi = 0;
        if (result.isEmpty()) {
            return 0;
        }
        Vector vtr = (Vector) result.get(0);
        roi = Float.parseFloat(vtr.get(0).toString());
        return roi;
    }

    public double rdInterestCalculation(float instAmt, int periodInMonth, float rdRoi) throws ApplicationException {
        return Double.parseDouble(accountOpeningFacadeRemote.cbsRdCalculation(instAmt, periodInMonth, rdRoi));
    }

    public List createNewFixedDeposit(String userId, String CustId, String drAc,
            String orgBrn, float amount, float roi, String interestRateType,
            String maturityDate, String period, int year, int month, int days) {
        List returnList = new ArrayList();
        try {
            String generatedAcNo = null;
            String newAccountResult = openNewFDAccount(CustId, orgBrn, userId, drAc);
            if (newAccountResult != null) {
                generatedAcNo = newAccountResult.substring(4, 16);
                String authorize = actAuthMgmtFacade.authorizeAcOpen(generatedAcNo, userId, "SYSTEM");
                if (authorize != null) {
                    String transfer = transferAmount(drAc, generatedAcNo, amount);
                    if (transfer != null) {
                        List createReceipt = createNewFDReceipt(generatedAcNo, orgBrn, maturityDate, amount, userId, interestRateType, drAc, roi, period, year, month, days);
                        System.out.println("createReceipt--" + createReceipt);
                        if (createReceipt != null) {
                            String receiptNo = createReceipt.get(0).toString();
                            String rtNo = createReceipt.get(1).toString();
                            String ctrlNo = createReceipt.get(2).toString();
                            returnList.add(generatedAcNo);
                            returnList.add(receiptNo);
                            returnList.add(rtNo);
                            returnList.add(ctrlNo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public String accountOpenAll(String acttype, String acno, int rdperiod, float rdinstall, float rdroi, String custid, String brnCode, String userid) {
        String msg = null,
                generatedAcNo = null;
        try {
            if (acttype.equalsIgnoreCase("SB") || acttype.equalsIgnoreCase("RD")) {
                msg = accountOpenSBRD(acttype, acno, rdperiod, acttype.equalsIgnoreCase("SB") ? 0 : rdinstall, rdroi, custid, brnCode, userid);
                if (msg.contains("Account Open Successfully")) {
                    generatedAcNo = msg.substring(44, 56);
                }
            }
            if (acttype.equalsIgnoreCase("CA")) {
                msg = openCurrentAccount(custid, acno, brnCode);
                if (msg.contains("FOR CUST ID")) {
                    generatedAcNo = msg.substring(0, 12);
                }
            }
            if (generatedAcNo != null) {
                actAuthMgmtFacade.authorizeAcOpen(generatedAcNo, userid, "SYSTEM1");
                transferAmount(acno, generatedAcNo, rdinstall);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public BigDecimal getBalanceByAccountNumber(String acNo, String asOnDt) throws ApplicationException {
        BigDecimal balance = BigDecimal.ZERO;
        try {
            //Pending Balance.
            List pendingList = txnMgmtFacade.selectPendingBalance(acNo);
            Vector element = (Vector) pendingList.get(0);
            Double pendingBal = Double.parseDouble(element.get(0).toString());
            //Actual Ledger Balance.
            Double ledgerBal = commonReport.getBalanceOnDate(acNo, asOnDt);
            balance = new BigDecimal((formatter.format(ledgerBal - pendingBal)));
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return balance;
    }

    public boolean isAccountOperative(String acNo) throws ApplicationException {
        try {
            List acctStatusList = new ArrayList();
            acctStatusList = entityManager.createNativeQuery("select accstatus,authBy from accountmaster where acNo='" + acNo + "'").getResultList();
            if (acctStatusList.isEmpty()) {
                return false;
            } else {
                Vector ele = (Vector) acctStatusList.get(0);
                if (ele.get(0) == null || ele.get(1) == null) {
                    return false;
                }
                int status = Integer.parseInt(ele.get(0).toString());
                String authBy = ele.get(1).toString();
                if (authBy != null && status == 1 && !authBy.equalsIgnoreCase("")
                        && commonReport.getBalanceOnDate(acNo, ymdFormat.format(new Date())) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmailIdMobileNumberByCusromerId(String custId, String eMailId, String mobileNumber) throws ApplicationException {
        UserTransaction userTransaction = context.getUserTransaction();
        try {
            userTransaction.begin();
            int value = entityManager.createNativeQuery("UPDATE cbs_customer_master_detail SET MOBILENUMBER='" + mobileNumber + "', EMAILID='"
                    + eMailId + "' WHERE CUSTOMERID='" + custId + "'").executeUpdate();
            if (value == 1) {
                userTransaction.commit();
                return true;
            } else {
                userTransaction.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CustomerIdPojo> customerAccountNumbersByCustomerId(String custId) throws ApplicationException {
        List<CustomerIdPojo> finalList = new ArrayList<>();
        try {
            Query query = entityManager.createNativeQuery("select custId,acNo,tranTime,txnBrn from customerid where custid='" + custId + "'");
            List result = query.getResultList();
            for (int i = 0; i < result.size(); i++) {
                CustomerIdPojo idPojo = new CustomerIdPojo();
                Vector vtr = (Vector) result.get(i);
                if (vtr.get(0) != null) {
                    idPojo.setCustId(vtr.get(0).toString());
                }
                if (vtr.get(1) != null) {
                    idPojo.setAcNo(vtr.get(1).toString());
                }
                if (vtr.get(2) != null) {
                    idPojo.setTranTime((Date) vtr.get(2));
                }
                if (vtr.get(3) != null) {
                    idPojo.setTxnBrn(vtr.get(3).toString());
                }
                if (isAccountOperative(idPojo.getAcNo())) {
//                    String[] A = getAccountTypeBranchCodeByAccountNumber(idPojo.getAcNo());

                    CustomerIdPojo pojo = getDetailOfAccountNumber(idPojo.getAcNo());
                    if (pojo == null) {
                        throw new ApplicationException("There is no detail for A/c No:" + idPojo.getAcNo());
                    }
                    idPojo.setAcType(pojo.getAcType());
                    idPojo.setBrCode(pojo.getBrCode());
                    finalList.add(idPojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalList;
    }

    public CustomerDetailsPojo customerInformationByCustomerId(String customerId) throws ApplicationException {
        CustomerDetailsPojo customerDetailsPojo = new CustomerDetailsPojo();
        Date curDt = new Date();
        try {
            List resultList = entityManager.createNativeQuery("select lastChangeUserID,customerid,custname,lastChangeTime,title,shortName,gender,"
                    + "maritalstatus,fathername,mothername,mobilenumber,emailId,pan_GirNumber, tinNumber,perAddressLine1,perAddressLine2,"
                    + "perVillage,perBlock,perCityCode,perStateCode,perPostalCode, perCountryCode,perPhoneNumber,perTelexNumber,perFaxNumber,"
                    + "mailAddressLine1,mailAddressLine2, mailVillage,mailBlock,mailCityCode,mailStateCode,mailPostalCode,mailCountryCode,"
                    + "mailPhoneNumber, mailTelexNumber,mailFaxNumber,primaryBrCode,minorflag,dateOfBirth,passportNo,issueDate,expirydate,"
                    + "placeOfIssue,introCustomerId, combinedstmtFlag,stmtFreqType,stmtFreqWeekNo,stmtFreqStartDate,tdsExemptionEndDate,taxSlab, "
                    + "tdsCode,tdsCustomerId,tdsFormReceiveDate,middle_name,last_name from cbs_customer_master_detail where customerid='" + customerId + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector val = (Vector) resultList.get(0);
                if (null != val.get(0)) {
                    customerDetailsPojo.setLastChangeUserID(val.get(0).toString());
                } else {
                    customerDetailsPojo.setLastChangeUserID("");
                }
                if (null != val.get(1)) {
                    customerDetailsPojo.setCustomerid(val.get(1).toString());
                } else {
                    customerDetailsPojo.setCustomerid("");
                }
                if (null != val.get(2)) {
                    customerDetailsPojo.setCustname(val.get(2).toString());
                } else {
                    customerDetailsPojo.setCustname("");
                }
                if (null != val.get(3)) {
                    customerDetailsPojo.setLastChangeTime(ymdFormat.parse(val.get(3).toString()));
                }
                if (null != val.get(4)) {
                    customerDetailsPojo.setTitle(val.get(4).toString());
                } else {
                    customerDetailsPojo.setTitle("");
                }
                if (null != val.get(5)) {
                    customerDetailsPojo.setShortName(val.get(5).toString());
                } else {
                    customerDetailsPojo.setShortName("");
                }
                if (null != val.get(6)) {
                    customerDetailsPojo.setGender(val.get(6).toString());
                } else {
                    customerDetailsPojo.setGender("");
                }
                if (null != val.get(7)) {
                    customerDetailsPojo.setMaritalstatus(val.get(7).toString());
                } else {
                    customerDetailsPojo.setMaritalstatus("");
                }
                if (null != val.get(8)) {
                    customerDetailsPojo.setFathername(val.get(8).toString());
                } else {
                    customerDetailsPojo.setFathername("");
                }
                if (null != val.get(9)) {
                    customerDetailsPojo.setMothername(val.get(9).toString());
                } else {
                    customerDetailsPojo.setMothername("");
                }
                if (null != val.get(10)) {
                    customerDetailsPojo.setMobilenumber(val.get(10).toString());
                } else {
                    customerDetailsPojo.setMobilenumber("");
                }
                if (null != val.get(11)) {
                    customerDetailsPojo.setEmailId(val.get(11).toString());
                } else {
                    customerDetailsPojo.setEmailId("");
                }
                if (null != val.get(12)) {
                    customerDetailsPojo.setPan_GirNumber(val.get(12).toString());
                } else {
                    customerDetailsPojo.setPan_GirNumber("");
                }
                if (null != val.get(13)) {
                    customerDetailsPojo.setTinNumber(val.get(13).toString());
                } else {
                    customerDetailsPojo.setTinNumber("");
                }
                if (null != val.get(14)) {
                    customerDetailsPojo.setPerAddressLine1(val.get(14).toString());
                } else {
                    customerDetailsPojo.setPerAddressLine1("");
                }
                if (null != val.get(15)) {
                    customerDetailsPojo.setPerAddressLine2(val.get(15).toString());
                } else {
                    customerDetailsPojo.setPerAddressLine2("");
                }
                if (null != val.get(16)) {
                    customerDetailsPojo.setPerVillage(val.get(16).toString());
                } else {
                    customerDetailsPojo.setPerVillage("");
                }
                if (null != val.get(17)) {
                    customerDetailsPojo.setPerBlock(val.get(17).toString());
                } else {
                    customerDetailsPojo.setPerBlock("");
                }
                if (null != val.get(18)) {
                    customerDetailsPojo.setPerCityCode(val.get(18).toString());
                } else {
                    customerDetailsPojo.setPerCityCode("");
                }
                if (null != val.get(19)) {
                    customerDetailsPojo.setPerStateCode(val.get(19).toString());
                } else {
                    customerDetailsPojo.setPerStateCode("");
                }
                if (null != val.get(20)) {
                    customerDetailsPojo.setPerPostalCode(val.get(20).toString());
                } else {
                    customerDetailsPojo.setPerPostalCode("");
                }
                if (null != val.get(21)) {
                    customerDetailsPojo.setPerCountryCode(val.get(21).toString());
                } else {
                    customerDetailsPojo.setPerCountryCode("");
                }
                if (null != val.get(22)) {
                    customerDetailsPojo.setPerPhoneNumber(val.get(22).toString());
                } else {
                    customerDetailsPojo.setPerPhoneNumber("");
                }
                if (null != val.get(23)) {
                    customerDetailsPojo.setPerTelexNumber(val.get(23).toString());
                } else {
                    customerDetailsPojo.setPerTelexNumber("");
                }
                if (null != val.get(24)) {
                    customerDetailsPojo.setPerFaxNumber(val.get(24).toString());
                } else {
                    customerDetailsPojo.setPerFaxNumber("");
                }
                if (null != val.get(25)) {
                    customerDetailsPojo.setMailAddressLine1(val.get(25).toString());
                } else {
                    customerDetailsPojo.setMailAddressLine1("");
                }
                if (null != val.get(26)) {
                    customerDetailsPojo.setMailAddressLine2(val.get(26).toString());
                } else {
                    customerDetailsPojo.setMailAddressLine2("");
                }
                if (null != val.get(27)) {
                    customerDetailsPojo.setMailVillage(val.get(27).toString());
                } else {
                    customerDetailsPojo.setMailVillage("");
                }
                if (null != val.get(28)) {
                    customerDetailsPojo.setMailBlock(val.get(28).toString());
                } else {
                    customerDetailsPojo.setMailBlock("");
                }
                if (null != val.get(29)) {
                    customerDetailsPojo.setMailCityCode(val.get(29).toString());
                } else {
                    customerDetailsPojo.setMailCityCode("");
                }
                if (null != val.get(30)) {
                    customerDetailsPojo.setMailStateCode(val.get(30).toString());
                } else {
                    customerDetailsPojo.setMailStateCode("");
                }
                if (null != val.get(31)) {
                    customerDetailsPojo.setMailPostalCode(val.get(31).toString());
                } else {
                    customerDetailsPojo.setMailPostalCode("");
                }
                if (null != val.get(32)) {
                    customerDetailsPojo.setMailCountryCode(val.get(32).toString());
                } else {
                    customerDetailsPojo.setMailCountryCode("");
                }
                if (null != val.get(33)) {
                    customerDetailsPojo.setMailPhoneNumber(val.get(33).toString());
                } else {
                    customerDetailsPojo.setMailPhoneNumber("");
                }
                if (null != val.get(34)) {
                    customerDetailsPojo.setMailTelexNumber(val.get(34).toString());
                } else {
                    customerDetailsPojo.setMailTelexNumber("");
                }
                if (null != val.get(35)) {
                    customerDetailsPojo.setMailFaxNumber(val.get(35).toString());
                } else {
                    customerDetailsPojo.setMailFaxNumber("");
                }
                if (null != val.get(36)) {
                    customerDetailsPojo.setPrimaryBrCode(val.get(36).toString());
                } else {
                    customerDetailsPojo.setPrimaryBrCode("");
                }
                if (null != val.get(37)) {
                    customerDetailsPojo.setMinorflag(val.get(37).toString());
                } else {
                    customerDetailsPojo.setMinorflag("");
                }
                if (null != val.get(38)) {
                    customerDetailsPojo.setDateOfBirth(ymd_Format.parse(val.get(38).toString()));
                }
                if (null != val.get(39)) {
                    customerDetailsPojo.setPassportNo(val.get(39).toString());
                } else {
                    customerDetailsPojo.setPassportNo("");
                }
                if (null != val.get(40) && !val.get(40).equals("")) {
                    customerDetailsPojo.setIssueDate(ymd_Format.parse(val.get(40).toString()));
                } else {
                    customerDetailsPojo.setIssueDate(curDt);
                }
                if (null != val.get(41) && !val.get(41).equals("")) {
                    customerDetailsPojo.setExpirydate(ymd_Format.parse(val.get(41).toString()));
                } else {
                    customerDetailsPojo.setExpirydate(curDt);
                }
                if (null != val.get(42)) {
                    customerDetailsPojo.setPlaceOfIssue(val.get(42).toString());
                } else {
                    customerDetailsPojo.setPlaceOfIssue("");
                }
                if (null != val.get(43)) {
                    customerDetailsPojo.setIntroCustomerId(val.get(43).toString());
                } else {
                    customerDetailsPojo.setIntroCustomerId("");
                }
                if (null != val.get(44)) {
                    customerDetailsPojo.setCombinedstmtFlag(val.get(44).toString());
                } else {
                    customerDetailsPojo.setCombinedstmtFlag("");
                }
                if (null != val.get(45)) {
                    customerDetailsPojo.setStmtFreqType(val.get(45).toString());
                } else {
                    customerDetailsPojo.setStmtFreqType("");
                }
                if (null != val.get(46)) {
                    customerDetailsPojo.setStmtFreqWeekNo(val.get(46).toString());
                } else {
                    customerDetailsPojo.setStmtFreqWeekNo("");
                }
                if (null != val.get(47) && !val.get(47).equals("")) {
                    customerDetailsPojo.setStmtFreqStartDate(ymdFormat.parse(val.get(47).toString()));
                } else {
                    customerDetailsPojo.setStmtFreqStartDate(curDt);
                }
                if (null != val.get(48) && !val.get(48).equals("")) {
                    customerDetailsPojo.setTdsExemptionEndDate(ymd_Format.parse(val.get(48).toString()));
                } else {
                    customerDetailsPojo.setTdsExemptionEndDate(curDt);
                }
                if (null != val.get(49)) {
                    customerDetailsPojo.setTaxSlab(val.get(49).toString());
                } else {
                    customerDetailsPojo.setTaxSlab("");
                }
                if (null != val.get(50)) {
                    customerDetailsPojo.setTdsCode(val.get(50).toString());
                } else {
                    customerDetailsPojo.setTdsCode("");
                }
                if (null != val.get(51)) {
                    customerDetailsPojo.setTdsCustomerId(val.get(51).toString());
                } else {
                    customerDetailsPojo.setTdsCustomerId("");
                }
                if (null != val.get(52) && !val.get(52).equals("")) {
                    customerDetailsPojo.setTdsFormReceiveDate(ymd_Format.parse(val.get(38).toString()));
                } else {
                    customerDetailsPojo.setTdsFormReceiveDate(curDt);
                }
                if (null != val.get(53)) {
                    customerDetailsPojo.setMiddleName(val.get(53).toString().trim());
                } else {
                    customerDetailsPojo.setMiddleName("");
                }
                if (null != val.get(54)) {
                    customerDetailsPojo.setLastName(val.get(54).toString());
                } else {
                    customerDetailsPojo.setLastName("");
                }
                return customerDetailsPojo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerDetailsPojo;
    }

    public List<CustomerDetailsPojo> getAccountNoVerficationDetail(String acNo) throws ApplicationException {
        Query query = entityManager.createNativeQuery("select c.custid,a.accttype,cd.custname,cd.mobilenumber,cd.emailId,cd.primaryBrCode,a.acNo "
                + "from customerid c,accountmaster a,cbs_customer_master_detail cd,branchmaster br where c.custid in (select custid from "
                + "customerid where acNo='" + acNo + "' ) and c.acNo=a.acNo and a.accStatus=1 and c.custid=cd.customerid and br.brncode=cd.primaryBrCode");
        List result = query.getResultList();
        List<CustomerDetailsPojo> finalList = new ArrayList<CustomerDetailsPojo>();
        for (int i = 0; i < result.size(); i++) {
            CustomerDetailsPojo row = new CustomerDetailsPojo();
            Vector vtr = (Vector) result.get(i);
            if (vtr.get(0) != null) {
                row.setCustomerid(vtr.get(0).toString());
            }
            if (vtr.get(1) != null) {
                row.setPerPostalCode(vtr.get(1).toString());
            }
            if (vtr.get(2) != null) {
                row.setCustname(vtr.get(2).toString());
            }
            if (vtr.get(3) != null) {
                row.setMobilenumber(vtr.get(3).toString());
            }
            if (vtr.get(4) != null) {
                row.setEmailId(vtr.get(4).toString());
            }
            if (vtr.get(5) != null) {
                String brncode = vtr.get(5).toString();
                while (brncode.length() < 6) {
                    brncode = "0" + brncode;
                }
                row.setPrimaryBrCode(brncode);
            }
            if (vtr.get(6) != null) {
                row.setAcno(vtr.get(6).toString());
            }
            finalList.add(row);
        }
        return finalList;
    }

    public AccountMasterPojo getcustomerInformationByAccountNo(String acNo) throws ApplicationException {
        AccountMasterPojo ibsas = new AccountMasterPojo();
        try {
            List resultList = entityManager.createNativeQuery("select * from accountmaster where acNo='" + acNo + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(0) != null) {
                    ibsas.setAcNo(ele.get(0).toString());
                } else {
                    ibsas.setAcNo("");
                }
                if (ele.get(1) != null) {
                    ibsas.setCustname(ele.get(1).toString());
                } else {
                    ibsas.setCustname("");
                }
                if (ele.get(2) != null) {
                    ibsas.setOpeningDt(ele.get(2).toString());
                } else {
                    ibsas.setOpeningDt("");
                }
                if (ele.get(3) != null) {
                    ibsas.setIntroAccno(ele.get(3).toString());
                } else {
                    ibsas.setIntroAccno("");
                }
                if (ele.get(4) != null) {
                    ibsas.setIntDeposit(Double.parseDouble(ele.get(4).toString()));
                }
                if (ele.get(5) != null) {
                    ibsas.setClosingDate(ele.get(5).toString());
                } else {
                    ibsas.setClosingDate("");
                }
                if (ele.get(6) != null) {
                    ibsas.setClosingBal(Double.parseDouble(ele.get(6).toString()));
                }
                if (ele.get(7) != null) {
                    ibsas.setOperMode(Integer.parseInt(ele.get(7).toString()));
                }
                if (ele.get(8) != null) {
                    ibsas.setJtName1(ele.get(8).toString());
                } else {
                    ibsas.setJtName1("");
                }
                if (ele.get(9) != null) {
                    ibsas.setJtName2(ele.get(9).toString());
                } else {
                    ibsas.setJtName2("");
                }
                if (ele.get(10) != null) {
                    ibsas.setJtName3(ele.get(10).toString());
                } else {
                    ibsas.setJtName3("");
                }
                if (ele.get(11) != null) {
                    ibsas.setJtName4(ele.get(11).toString());
                } else {
                    ibsas.setJtName4("");
                }
                if (ele.get(12) != null) {
                    ibsas.setLastOpDate(ele.get(12).toString());
                } else {
                    ibsas.setLastOpDate("");
                }
                if (ele.get(13) != null) {
                    ibsas.setAccStatus(Integer.parseInt(ele.get(13).toString()));
                }
                if (ele.get(14) != null) {
                    ibsas.setOptstatus(Integer.parseInt(ele.get(14).toString()));
                }
                if (ele.get(15) != null) {
                    ibsas.setOrgnCode(Integer.parseInt(ele.get(15).toString()));
                }
                if (ele.get(16) != null) {
                    ibsas.setNomination(ele.get(16).toString());
                } else {
                    ibsas.setNomination("");
                }
                if (ele.get(17) != null) {
                    ibsas.setoDLimit(Double.parseDouble(ele.get(17).toString()));
                }
                if (ele.get(18) != null) {
                    ibsas.setEnteredBy(ele.get(18).toString());
                } else {
                    ibsas.setEnteredBy("");
                }
                if (ele.get(19) != null) {
                    ibsas.setAuthBy(ele.get(19).toString());
                } else {
                    ibsas.setAuthBy("");
                }
                if (ele.get(20) != null) {
                    ibsas.setLastUpdateDt(ele.get(20).toString());
                } else {
                    ibsas.setLastUpdateDt("");
                }
                if (ele.get(21) != null) {
                    ibsas.setAccttype(ele.get(21).toString());
                } else {
                    ibsas.setAccttype("");
                }
                if (ele.get(22) != null) {
                    ibsas.setRelatioship(ele.get(22).toString());
                } else {
                    ibsas.setRelatioship("");
                }
                if (ele.get(23) != null) {
                    ibsas.setLedgerFolioNo(Integer.parseInt(ele.get(23).toString()));
                }
                if (ele.get(24) != null) {
                    ibsas.setInstruction(ele.get(24).toString());
                } else {
                    ibsas.setInstruction("");
                }
                if (ele.get(25) != null) {
                    ibsas.setPenalty(Double.parseDouble(ele.get(25).toString()));
                }
                if (ele.get(26) != null) {
                    ibsas.setMinbal(Integer.parseInt(ele.get(26).toString()));
                }
                if (ele.get(27) != null) {
                    ibsas.setRdmatdate(ymd_Format.parse(ele.get(27).toString()));
                }
                if (ele.get(28) != null) {
                    ibsas.setRdInstal(Double.parseDouble(ele.get(28).toString()));
                }
                if (ele.get(29) != null) {
                    ibsas.setChequebook(Integer.parseInt(ele.get(29).toString()));
                }
                if (ele.get(30) != null) {
                    ibsas.setAdhoclimit(Double.parseDouble(ele.get(30).toString()));
                }
                if (ele.get(31) != null) {
                    ibsas.setAdhoctilldt(ymd_Format.parse(ele.get(31).toString()));
                }
                if (ele.get(32) != null) {
                    ibsas.setAdhocInterest(Double.parseDouble(ele.get(32).toString()));
                }
                if (ele.get(33) != null) {
                    ibsas.setCreationDt(ele.get(33).toString());
                } else {
                    ibsas.setCreationDt("");
                }
                if (ele.get(34) != null) {
                    ibsas.setName(ele.get(34).toString());
                } else {
                    ibsas.setName("");
                }
                if (ele.get(35) != null) {
                    ibsas.setCustType(ele.get(35).toString());
                } else {
                    ibsas.setCustType("");
                }
                if (ele.get(36) != null) {
                    ibsas.setPenalty1(Integer.parseInt(ele.get(36).toString()));
                }
                if (ele.get(37) != null) {
                    ibsas.settDSFLAG(ele.get(37).toString());
                } else {
                    ibsas.settDSFLAG("");
                }
                if (ele.get(38) != null) {
                    ibsas.setCustid1(ele.get(38).toString());
                } else {
                    ibsas.setCustid1("");
                }
                if (ele.get(39) != null) {
                    ibsas.setCustid2(ele.get(39).toString());
                } else {
                    ibsas.setCustid2("");
                }
                if (ele.get(40) != null) {
                    ibsas.setCustid3(ele.get(40).toString());
                } else {
                    ibsas.setCustid3("");
                }
                if (ele.get(41) != null) {
                    ibsas.setCustid4(ele.get(41).toString());
                } else {
                    ibsas.setCustid4("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ibsas;
    }

    public NomineeDetailsPojo getNomineeDetailsByAccountNo(String acNo) throws ApplicationException {
        try {
            List resultList = entityManager.createNativeQuery("select * from  nom_details where acNo='" + acNo + "'").getResultList();
            if (!resultList.isEmpty()) {
                NomineeDetailsPojo ibsas = new NomineeDetailsPojo();
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(1) != null) {
                    ibsas.setNomname(ele.get(1).toString());
                } else {
                    ibsas.setNomname("");
                }
                if (ele.get(2) != null) {
                    ibsas.setNomadd(ele.get(2).toString());
                } else {
                    ibsas.setNomadd("");
                }
                if (ele.get(3) != null) {
                    ibsas.setRelation(ele.get(3).toString());
                } else {
                    ibsas.setRelation("");
                }
                if (ele.get(5) != null) {
                    ibsas.setDob(ymd_Format.parse(ele.get(5).toString()));
                }
                return ibsas;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DocumentDetailsPojo getDocumentDetailsByAccountNo(String acNo) throws ApplicationException {
        DocumentDetailsPojo ibsas = new DocumentDetailsPojo();
        try {
            List resultList = entityManager.createNativeQuery("select * from documentsreceived where acNo='" + acNo + "'").getResultList();
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(2) != null) {
                    ibsas.setDocuno(Integer.parseInt(ele.get(2).toString()));
                }
                if (ele.get(3) != null) {
                    ibsas.setDocudetails(ele.get(3).toString());
                } else {
                    ibsas.setDocudetails("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ibsas;
    }

    public CustomerMasterDetailsPojo getCustomerDetailsByCustNo(String custNo, String brnCode) throws ApplicationException {
        try {
            List resultList = entityManager.createNativeQuery("select * from customermaster WHERE custNo='" + custNo + "' and brncode='" + brnCode + "'").getResultList();
            if (!resultList.isEmpty()) {
                CustomerMasterDetailsPojo ibsas = new CustomerMasterDetailsPojo();
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(0) != null) {
                    ibsas.setCustNo(ele.get(0).toString());
                } else {
                    ibsas.setCustNo("");
                }
                if (ele.get(1) != null) {
                    ibsas.setTitle(ele.get(1).toString());
                } else {
                    ibsas.setTitle("");
                }
                if (ele.get(2) != null) {
                    ibsas.setCustName(ele.get(2).toString());
                } else {
                    ibsas.setCustName("");
                }
                if (ele.get(3) != null) {
                    ibsas.setCrAddress(ele.get(3).toString());
                } else {
                    ibsas.setCrAddress("");
                }
                if (ele.get(4) != null) {
                    ibsas.setPrAddress(ele.get(4).toString());
                } else {
                    ibsas.setPrAddress("");
                }
                if (ele.get(5) != null) {
                    ibsas.setPhoneNo(ele.get(5).toString());
                } else {
                    ibsas.setPhoneNo("");
                }
                if (ele.get(6) != null) {
                    ibsas.setPanNo(ele.get(6).toString());
                } else {
                    ibsas.setPanNo("");
                }
                if (ele.get(7) != null) {
                    ibsas.setStatus(ele.get(7).toString());
                } else {
                    ibsas.setStatus("");
                }
                if (ele.get(8) != null) {
                    ibsas.setGrdName(ele.get(8).toString());
                } else {
                    ibsas.setGrdName("");
                }
                if (ele.get(9) != null) {
                    ibsas.setRelation(ele.get(9).toString());
                } else {
                    ibsas.setRelation("");
                }
                if (ele.get(10) != null) {
                    ibsas.setDob(ele.get(10).toString());
                } else {
                    ibsas.setDob("");
                }
                if (ele.get(11) != null) {
                    ibsas.setOccupation(Integer.parseInt(ele.get(11).toString()));
                }
                if (ele.get(12) != null) {
                    ibsas.setEnteredBy(ele.get(12).toString());
                } else {
                    ibsas.setEnteredBy("");
                }
                if (ele.get(13) != null) {
                    ibsas.setLastUpdatedDate(ele.get(13).toString());
                } else {
                    ibsas.setLastUpdatedDate("");
                }
                if (ele.get(14) != null) {
                    ibsas.setRemarks(ele.get(14).toString());
                } else {
                    ibsas.setRemarks("");
                }
                if (ele.get(15) != null) {
                    ibsas.setLines(Integer.parseInt(ele.get(15).toString()));
                } else {
                    ibsas.setLines(0);
                }
                if (ele.get(16) != null) {
                    ibsas.setAcType(ele.get(16).toString());
                } else {
                    ibsas.setAcType("");
                }
                if (ele.get(17) != null) {
                    ibsas.setAgCode1(Integer.parseInt(ele.get(17).toString()));
                } else {
                    ibsas.setAgCode1(0);
                }
                if (ele.get(18) != null) {
                    ibsas.setAgCode(ele.get(18).toString());
                } else {
                    ibsas.setAgCode("");
                }
                if (ele.get(19) != null) {
                    ibsas.setFatherName(ele.get(19).toString());
                } else {
                    ibsas.setFatherName("");
                }
                if (ele.get(20) != null) {
                    ibsas.setBrnCode(ele.get(20).toString());
                } else {
                    ibsas.setBrnCode("");
                }
                return ibsas;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RdAcountDetailPojo> getRdAccountListbyCustomerid(String custid) throws ApplicationException {
        List<RdAcountDetailPojo> listpojo = new ArrayList<RdAcountDetailPojo>();
        try {
            List resultList = entityManager.createNativeQuery("select r.acno,r.installamt,count(*) as unpaid from accountmaster a,rd_installment r,"
                    + "customerid c where c.custid='" + custid + "'and  a.acno=c.acno and a.accstatus<>9 and a.accttype='RD' and a.acno=r.acno "
                    + "and r.status='Unpaid' group by r.acno,r.installamt").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vtr = (Vector) resultList.get(i);
                    RdAcountDetailPojo rdAcountDetailPojo = new RdAcountDetailPojo();
                    rdAcountDetailPojo.setAcNo(vtr.get(0).toString());
                    rdAcountDetailPojo.setInstallAmt(Double.parseDouble(vtr.get(1).toString()));
                    rdAcountDetailPojo.setUnpaidInstallment(Integer.parseInt(vtr.get(2).toString()));
                    listpojo.add(rdAcountDetailPojo);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listpojo;
    }

    public List<RdAcountDetailPojo> getRdAccountsDetailsInstallment(String acno, String status) throws ApplicationException {
        List<RdAcountDetailPojo> listpojo = new ArrayList<RdAcountDetailPojo>();
        try {
            List resultList = entityManager.createNativeQuery("select acno,sno,date_format(duedt,'%d/%m/%Y'),installamt,status from rd_installment "
                    + "where acno='" + acno + "' order by sno").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vtr = (Vector) resultList.get(i);
                    RdAcountDetailPojo rdAcountDetailPojo = new RdAcountDetailPojo();
                    rdAcountDetailPojo.setAcNo(vtr.get(0) == null ? "" : vtr.get(0).toString());
                    rdAcountDetailPojo.setSno(vtr.get(1) == null ? 0 : Integer.parseInt(vtr.get(1).toString()));
                    rdAcountDetailPojo.setDuedt(vtr.get(2) == null ? "" : vtr.get(2).toString());
                    rdAcountDetailPojo.setInstallAmt(vtr.get(3) == null ? 0.0 : Double.parseDouble(vtr.get(3).toString()));
                    rdAcountDetailPojo.setStatus(vtr.get(4) == null ? "" : vtr.get(4).toString());
                    listpojo.add(rdAcountDetailPojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listpojo;
    }

    public String transferAmount(String drAc, String crAc, double amount) {
        try {
//            String orgBrn = null;
//            String destBrn = null;
//            String[] A = getAccountTypeBranchCodeByAccountNumber(drAc);
//            if (A.length == 2) {
//                orgBrn = A[0];
//            } else {
//                return "Origin Branch not found";
//            }

            CustomerIdPojo pojo = getDetailOfAccountNumber(drAc);
            if (pojo == null) {
                throw new ApplicationException("There is no detail for A/c No:" + drAc);
            }
            String orgBrn = pojo.getBrCode();

//            String[] B = getAccountTypeBranchCodeByAccountNumber(crAc);
//            if (B.length == 2) {
//                destBrn = B[0];
//            } else {
//                return "Destination Branch not found";
//            }

            pojo = getDetailOfAccountNumber(crAc);
            if (pojo == null) {
                throw new ApplicationException("There is no detail for A/c No:" + crAc);
            }
            String destBrn = pojo.getBrCode();


            List<TxnDetailBean> cashPOTxnBeanList = new ArrayList<TxnDetailBean>();
            cashPOTxnBeanList.add(getTransferBeanObj(ftsPostingMgmtFacade.getRecNo(), drAc, "DEBIT", 0, amount, amount, orgBrn, crAc, "1"));
            cashPOTxnBeanList.add(getTransferBeanObj(ftsPostingMgmtFacade.getRecNo(), crAc, "CREDIT", amount, 0, amount, destBrn, drAc, "0"));
            String saveTransferDetails = txnMgmtFacade.saveTransferDetails(orgBrn, cashPOTxnBeanList, ymd_Format.format(date), "");
            if (saveTransferDetails.contains("True")) {
                int index = saveTransferDetails.indexOf('!');
                String batchNo = saveTransferDetails.substring(0, index);
                String saveResult = saveTransferDetails.substring(index + 1, index + 5);
                if (saveResult.equalsIgnoreCase("True")) {
                    String transferAuthorization = transferAuthorization(batchNo, orgBrn);
                    return transferAuthorization;
                }
            } else {
                return saveTransferDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String transferAuthorization(String batchNo, String orgBrn) {
        try {
            List unAuthXferDetails = txnAuthMgmtFacade.getUnAuthXferDetails(orgBrn);
            if (!unAuthXferDetails.isEmpty()) {
                String authorized = txnAuthMgmtFacade.cbsAuthTransfer(dateString, new Float(batchNo), "SYSTEM1", orgBrn);
                if (authorized.equalsIgnoreCase("TRUETRUE")) {
                    return "TRUE-" + batchNo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ddTransfer(String drAc, String destination, String favour, double amount, double commision, double tax, String billType) {
        try {
//            String orgBrn = null;
//            String[] A = getAccountTypeBranchCodeByAccountNumber(drAc);
//            if (A.length == 2) {
//                orgBrn = A[0];
//            } else {
//                return "Branch not found";
//            }

            CustomerIdPojo pojo = getDetailOfAccountNumber(drAc);
            if (pojo == null) {
                throw new ApplicationException("There is no detail for A/c No:" + drAc);
            }
            String orgBrn = pojo.getBrCode();

            List accountList = getGlHeadForDD(orgBrn);
            String ddGlHead = accountList.get(0).toString();
            String taxGlHead = accountList.get(1).toString();
            String commGlHead = accountList.get(2).toString();
            double drAmt = amount + commision + tax;
            List<TxnDetailBean> cashPOTxnBeanList = new ArrayList<TxnDetailBean>();
            cashPOTxnBeanList.add(getTransferBeanObj(ftsPostingMgmtFacade.getRecNo(), drAc, "DEBIT", 0, drAmt, drAmt, orgBrn, "For issuing " + billType + " favouring " + favour, "1"));
            cashPOTxnBeanList.add(getTransferBeanObj(ftsPostingMgmtFacade.getRecNo(), ddGlHead, "CREDIT", amount, 0, amount, orgBrn, "For issuing " + billType + " favouring " + favour, "0"));
            cashPOTxnBeanList.add(getTransferBeanObj(ftsPostingMgmtFacade.getRecNo(), commGlHead, "CREDIT", commision, 0, commision, orgBrn, "COMM. FOR " + billType + " DRAWN ON " + destination + " FAVOURING " + favour, "0"));
            cashPOTxnBeanList.add(getTransferBeanObj(ftsPostingMgmtFacade.getRecNo(), taxGlHead, "CREDIT", tax, 0, tax, orgBrn, "SERVICE TAX AND EDUCATION CESS", "0"));
            String saveTransferDetails = txnMgmtFacade.saveTransferDetails(orgBrn, cashPOTxnBeanList, ymd_Format.format(date), "");
            if (saveTransferDetails.contains("True")) {
                int index = saveTransferDetails.indexOf('!');
                String batchNo = saveTransferDetails.substring(0, index);
                String saveResult = saveTransferDetails.substring(index + 1, index + 5);
                if (saveResult.equalsIgnoreCase("True")) {
                    return transferAuthorization(batchNo, orgBrn);
                }
            } else {
                return saveTransferDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //It is used for STOP CHEQUE PAYMENT in old case so do not delete it untill you test on new.
    public String saveStopPaymentRequest(String drAc, String chqStart, String chqEnd,
            String option) throws ApplicationException {
        try {
            CustomerIdPojo pojo = getDetailOfAccountNumber(drAc);
            if (pojo == null) {
                throw new ApplicationException("There is no detail for A/c No:" + drAc);
            }
            List list = cmrFacade.chkGLHead(pojo.getAcType());
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                Float tmpCharges = Float.parseFloat(ele.get(0).toString());
                String glHead = ele.get(1).toString();
                return cmrFacade.saveChqMaintinanceDetail(drAc, Long.parseLong(chqStart),
                        option, Long.parseLong(chqEnd), "SP", "SYSTEM", "", dateString, 0.0F, "YES",
                        getBalanceByAccountNumber(drAc, ymdFormat.format(new Date())).floatValue(),
                        tmpCharges, glHead, pojo.getBrCode(), pojo.getBrCode(), "S");

//                if (result.trim().toLowerCase().contains("data has been saved successfully")) {
//                    int index = result.lastIndexOf(':');
//                    String batchNo = result.substring(index + 1).trim();
//                    return transferAuthorization(batchNo, pojo.getBrCode());
//                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return null;
    }

    public List getChequeRecord(String acNature, String acNo, Float chqNo) {
        List returnList = new ArrayList();
        try {
            List detail = null;
            if (acNature.equalsIgnoreCase("SB")) {
                detail = entityManager.createNativeQuery("select * from chbook_sb where acno='" + acNo + "' and chqno=" + chqNo + "").getResultList();
            } else if (acNature.equalsIgnoreCase("CA")) {
                detail = entityManager.createNativeQuery("select * from chbook_ca where acno='" + acNo + "' and chqno=" + chqNo + "").getResultList();
            } else if (acNature.equalsIgnoreCase("PO")) {
                detail = entityManager.createNativeQuery("select * from chbook_po where acno='" + acNo + "' and chqno=" + chqNo + "").getResultList();
            }
            if (!detail.isEmpty()) {
                Vector ele = (Vector) detail.get(0);
                if (null != ele.get(0)) {
                    returnList.add(ele.get(0));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(1)) {
                    returnList.add(ele.get(1));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(2)) {
                    returnList.add(ele.get(2));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(3)) {
                    returnList.add(ele.get(3));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(4)) {
                    returnList.add(ele.get(4));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(5)) {
                    returnList.add(ele.get(5));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(6)) {
                    returnList.add(ele.get(6));
                } else {
                    returnList.add("NA");
                }
                if (null != ele.get(7)) {
                    returnList.add(ele.get(7));
                } else {
                    returnList.add("NA");
                }
                return returnList;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnList;
    }

//    public String[] getAccountTypeBranchCodeByAccountNumber(String accountNumber) throws ApplicationException {
//        String[] returnArray = new String[2];
//        try {
//            if (accountNumber.length() == 12) {
//                returnArray[0] = accountNumber.substring(0, 2);
//                returnArray[1] = accountNumber.substring(2, 4);
//            }
//            try {
//                Vector brCodeVec = (Vector) entityManager.createNativeQuery("select primaryBrCode from cbs_customer_master_detail where "
//                        + "customerid=(select custid from customerid where acNo='" + accountNumber + "')").getSingleResult();
//                if (null != brCodeVec || !brCodeVec.isEmpty()) {
//                    if (null != brCodeVec.get(0)) {
//                        returnArray[0] = brCodeVec.get(0).toString();
//                    }
//                }
//                Vector acTypeVec = (Vector) entityManager.createNativeQuery("select accttype from accountmaster where acNo='" + accountNumber + "'").getSingleResult();
//                if (null != acTypeVec || !acTypeVec.isEmpty()) {
//                    if (null != acTypeVec.get(0)) {
//                        returnArray[1] = acTypeVec.get(0).toString();
//                    }
//                }
//            } catch (NoResultException e) {
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnArray;
//    }
    private String openNewFDAccount(String custId, String brCode, String userId, String drAc) {
        try {
            Date defaultDate = ymdFormat.parse("19000401");
            String title;
            String custname;
            String craddress;
            String praddress;
            String phoneno;
            String dob;
            Integer occupation;
            Integer operatingMode;
            String status;
            String panno;
            String grdname;
            String grdRelation;
            String agcode;
            String dateText;
            String userText;
            String fathername;
            String acnoIntro;
            String jtName1;
            String jtName2;
            String jtName3;
            String jtName4;
            Integer docuno;
            String docudetails;
            String remark = userId;
            String minor = "N";
            int nomineeAge = 0;
            String nomineeName;
            Date nomineeDob = null;
            String nomineeAddress = "";
            String nomineeRelation = "";
            String custNo = drAc.substring(4, 10);
            String customerType;
            NomineeDetailsPojo nomineeDetails = getNomineeDetailsByAccountNo(drAc);
            AccountMasterPojo accountDetails = getcustomerInformationByAccountNo(drAc);
            CustomerMasterDetailsPojo customerMasterDetails = getCustomerDetailsByCustNo(custNo, brCode);
            DocumentDetailsPojo documentDetails = getDocumentDetailsByAccountNo(drAc);
            if (nomineeDetails == null) {
                nomineeName = "";
            } else {
                nomineeName = nomineeDetails.getNomname();
                nomineeDob = nomineeDetails.getDob();
                nomineeAge = CbsUtil.yearDiff(nomineeDob, date);
                nomineeAddress = nomineeDetails.getNomadd();
                nomineeRelation = nomineeDetails.getRelation();
            }
            Date accountHolderDob = ymdFormat.parse(customerMasterDetails.getDob());
            if (accountHolderDob.equals(defaultDate)) {
                customerType = "OT";
            } else {
                int accountHolderAge = CbsUtil.yearDiff(accountHolderDob, date);
                if (accountHolderAge > 60) {
                    customerType = "SC";
                } else {
                    customerType = "OT";
                }
            }
            if (accountDetails != null && customerMasterDetails != null) {
                title = customerMasterDetails.getTitle();
                custname = customerMasterDetails.getCustName();
                craddress = customerMasterDetails.getCrAddress();
                praddress = customerMasterDetails.getPrAddress();
                phoneno = customerMasterDetails.getPhoneNo();
                dob = customerMasterDetails.getDob();
                occupation = customerMasterDetails.getOccupation();
                operatingMode = accountDetails.getOperMode();
                status = customerMasterDetails.getStatus();
                panno = customerMasterDetails.getPanNo();
                grdname = customerMasterDetails.getGrdName();
                grdRelation = customerMasterDetails.getRelation();
                agcode = customerMasterDetails.getAgCode();
                dateText = dateString;
                userText = userId;
                fathername = customerMasterDetails.getFatherName();
                acnoIntro = accountDetails.getIntroAccno();
                jtName1 = accountDetails.getJtName1();
                jtName2 = accountDetails.getJtName2();
                jtName3 = accountDetails.getJtName3();
                jtName4 = accountDetails.getJtName4();
                docuno = documentDetails.getDocuno();
                docudetails = documentDetails.getDocudetails();
                remark = userId;
                if (nomineeAge < 18) {
                    minor = "Y";
                }
                String custId1 = accountDetails.getCustid1();
                String custId2 = accountDetails.getCustid2();
                String custId3 = accountDetails.getCustid3();
                String custId4 = accountDetails.getCustid4();
                return fdDdsAccountOpeningFacade.saveAccountOpenFd("", custId, "FD", title, custname, craddress, praddress, phoneno, dob,
                        occupation, operatingMode, status, panno, grdname, grdRelation, agcode, dateText, userText, fathername, acnoIntro,
                        jtName1, jtName2, jtName3, jtName4, brCode, nomineeName, nomineeRelation, docuno, docudetails, remark, customerType,
                        "Y", "", nomineeName, nomineeAddress, nomineeRelation, minor, nomineeDob == null ? null : ymdFormat.format(nomineeDob),
                        nomineeAge, custId1, custId2, custId3, custId4, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List createNewFDReceipt(
            String generatedAcNo,
            String brCode,
            String maturityDate,
            Float amount,
            String userId,
            String interestRateType,
            String drAc,
            Float roi,
            String period,
            int tenureYear,
            int tenureMonth,
            int tenureDays) {
        List list = new ArrayList();
        try {
            String tdReceipt = getTdRecieptSeq(), bookSeries = getBookSeries("FD", tdReceipt, brCode);

//            List resultList = tdReceiptMgmtFacade.newTdReciptCreation("FD", generatedAcNo, dateString, dateString, dateString, maturityDate, 
//                    amount, tdReceipt, bookSeries, brCode, "FD", userId, interestRateType, getAcctType(), drAc, roi, period, tenureYear, 
//                    tenureMonth, tenureDays);
            List resultList = new ArrayList();
            System.out.println(resultList);
            if (resultList.size() == 3) {
                list.add(resultList.get(0).toString().substring(resultList.get(0).toString().indexOf("--> ") + 4));
                list.add(resultList.get(1).toString().substring(resultList.get(1).toString().indexOf("--> ") + 4));
                list.add(resultList.get(2).toString().substring(resultList.get(2).toString().indexOf("-->") + 3));
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getBookSeries(String acctType, String tdReciept, String orgnBrCode) throws ApplicationException {
        try {
            List bookSeriesList = tdReceiptMgmtFacade.getBookSeries(acctType, tdReciept, orgnBrCode);
            if (!bookSeriesList.isEmpty()) {
                Vector ele = (Vector) bookSeriesList.get(0);
                if (ele.get(0) != null) {
                    return ele.get(0).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTdRecieptSeq() throws ApplicationException {
        try {
            List tdRecieptSeqList = tdReceiptMgmtFacade.getTdRecieptSeq();
            if (!tdRecieptSeqList.isEmpty()) {
                Vector ele = (Vector) tdRecieptSeqList.get(0);
                if (ele.get(0) != null) {
                    return ele.get(0).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAcctType() throws ApplicationException {
        try {
            List accountList = tdReceiptMgmtFacade.getAcctType();
            if (!accountList.isEmpty()) {
                Vector ele = (Vector) accountList.get(0);
                if (ele.get(2) != null) {
                    return ele.get(2).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String accountOpenSBRD(String acttype, String acno, int rdperiod, float rdinstall, float rdroi, String custid, String brnCode, String userid) {
        String msg = "FALSE";
        try {
            CustomerDetailsPojo customerDetailsPojo = customerInformationByCustomerId(custid);
            if (customerDetailsPojo == null) {
                return "Customer with ID " + custid + " not found";
            }
            AccountMasterPojo customerInformationByAccountNo = getcustomerInformationByAccountNo(acno);
            if (customerInformationByAccountNo == null) {
                return "Account Number " + acno + " not found";
            }
            NomineeDetailsPojo nomineeDetails = getNomineeDetailsByAccountNo(acno);
            DocumentDetailsPojo documentDetailsByAccountNo = getDocumentDetailsByAccountNo(acno);
            CustomerMasterDetailsPojo customerDetailsByCustNo = getCustomerDetailsByCustNo(acno.substring(4, 10), acno.substring(0, 2));
            if (customerDetailsByCustNo == null) {
                return "Customer Details not found";
            }
            msg = accountOpeningFacadeRemote.saveAccountOpenSbRd("", custid, acttype, customerDetailsPojo.getTitle(), customerDetailsPojo.getCustname(), customerDetailsPojo.getMailAddressLine1(), customerDetailsPojo.getPerAddressLine1(),
                    customerDetailsPojo.getMobilenumber(), ymdFormat.format(customerDetailsPojo.getDateOfBirth()), customerDetailsByCustNo.getOccupation(),
                    customerInformationByAccountNo.getOperMode(), customerDetailsPojo.getPan_GirNumber(), customerDetailsByCustNo.getGrdName(), customerDetailsByCustNo.getRelation(), acno.substring(10), dateString, userid, customerDetailsPojo.getFathername(), acno, customerInformationByAccountNo.getJtName1(), customerInformationByAccountNo.getJtName2(), brnCode,
                    nomineeDetails == null ? "" : nomineeDetails.getNomname(), nomineeDetails == null ? "" : nomineeDetails.getRelation(), customerInformationByAccountNo.getJtName3(), customerInformationByAccountNo.getJtName4(), rdperiod, rdinstall, rdroi, documentDetailsByAccountNo == null ? 0 : documentDetailsByAccountNo.getDocuno(), documentDetailsByAccountNo == null ? "" : documentDetailsByAccountNo.getDocudetails(), nomineeDetails == null ? "" : nomineeDetails.getNomadd(), nomineeDetails == null ? "" : ymdFormat.format(nomineeDetails.getDob() == null ? new java.util.Date() : nomineeDetails.getDob()),
                    customerInformationByAccountNo.getCustid1(), customerInformationByAccountNo.getCustid2(), customerInformationByAccountNo.getCustid3(), customerInformationByAccountNo.getCustid4(), "", "", "", "", customerInformationByAccountNo.getChequebook());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    private String openCurrentAccount(String custid, String drAc, String brncode) {
        try {
            String title;
            String custname;
            String craddress;
            String praddress;
            String phoneno;
            String dob;
            Integer occupation;
            Integer operatingMode;
            String panno;
            String grdname;
            String grdRelation;
            String agcode;
            String userText;
            String fathername;
            String acnoIntro;
            String jtName1;
            String jtName2;
            String jtName3;
            String jtName4;
            Integer docuno;
            String docudetails;
            String nomineeName = "";
            String nomineeDob = null;
            String nomineeAddress = "";
            String nomineeRelation = "";
            String custNo = drAc.substring(4, 10);
            NomineeDetailsPojo nomineeDetails = getNomineeDetailsByAccountNo(drAc);
            AccountMasterPojo accountDetails = getcustomerInformationByAccountNo(drAc);
            CustomerMasterDetailsPojo customerMasterDetails = getCustomerDetailsByCustNo(custNo, brncode);
            DocumentDetailsPojo documentDetails = getDocumentDetailsByAccountNo(drAc);
            if (nomineeDetails != null) {
                nomineeName = nomineeDetails.getNomname();
                nomineeDob = ymdFormat.format(nomineeDetails.getDob());
                nomineeAddress = nomineeDetails.getNomadd();
                nomineeRelation = nomineeDetails.getRelation();
            }
            if (accountDetails != null && customerMasterDetails != null) {
                title = customerMasterDetails.getTitle();
                custname = customerMasterDetails.getCustName();
                craddress = customerMasterDetails.getCrAddress();
                praddress = customerMasterDetails.getPrAddress();
                phoneno = customerMasterDetails.getPhoneNo();
                dob = customerMasterDetails.getDob();
                occupation = customerMasterDetails.getOccupation();
                operatingMode = accountDetails.getOperMode();
                panno = customerMasterDetails.getPanNo();
                grdname = customerMasterDetails.getGrdName();
                grdRelation = customerMasterDetails.getRelation();
                agcode = customerMasterDetails.getAgCode();
                userText = "SYSTEM";
                fathername = customerMasterDetails.getFatherName();
                acnoIntro = accountDetails.getIntroAccno();
                jtName1 = accountDetails.getJtName1();
                jtName2 = accountDetails.getJtName2();
                jtName3 = accountDetails.getJtName3();
                jtName4 = accountDetails.getJtName4();
                docuno = documentDetails.getDocuno();
                docudetails = documentDetails.getDocudetails();
                String custId1 = accountDetails.getCustid1();
                String custId2 = accountDetails.getCustid2();
                String custId3 = accountDetails.getCustid3();
                String custId4 = accountDetails.getCustid4();
                String tmpANat = "CA";
                String appTp = "FALSE";
                String appSeq = "";
                String FYear = "0";
                float limit = 0;
                float roi = 0;
                String intopt = "abc";
                String subsidyAmt = "0";
                String spInst = "";
                int moritoriumPeriod = 0;
                float acnoPreDr = 0f;
                float acnoPreCr = 0f;
                String rateCode = "";
                String calMethod = "";
                String calOn = "";
                String intAppFrequency = "";
                String calLevel = "";
                String compoundFrequency = "";
                String disbursmentType = "";
                String paggingFrequency = "";
                int LoanPeriod1 = 0;
                int LoanPeriod2 = 0;
                String schemeCode = "";
                String intCode = "";
                String saveTLAcOpenDetail = accountOpeningFacadeRemote.saveTLAcOpenDetail(custid, tmpANat, appTp, appSeq, FYear, "CA", brncode, agcode,
                        dateString, title, custname, fathername, praddress, craddress, phoneno, panno, dob, occupation,
                        grdname, grdRelation, operatingMode, jtName1, jtName2, jtName3, jtName4, nomineeName, nomineeRelation,
                        nomineeAddress, nomineeDob, acnoIntro, limit, roi, dateString, intopt, subsidyAmt, docuno, docudetails,
                        spInst, userText, schemeCode, moritoriumPeriod, acnoPreDr, acnoPreCr, rateCode, calMethod,
                        calOn, intAppFrequency, calLevel, compoundFrequency, disbursmentType, intCode, paggingFrequency,
                        LoanPeriod1, LoanPeriod2, custId1, custId2, custId3, custId4, "", "", accountDetails.getChequebook());
                return saveTLAcOpenDetail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private TxnDetailBean getTransferBeanObj(Float recNo, String accNo, String type, double crAmt, double drAmt, double amount, String brCode, String details, String ty) {
        TxnDetailBean txnBean = new TxnDetailBean();
        txnBean.setAuth("N");
        txnBean.setAuthBy("");
        txnBean.setBillType("");
        txnBean.setCloseFlag("");
        txnBean.setCxsxFlag("");
        txnBean.setFinYear(0);
        txnBean.setInstNo("0");
        txnBean.setIntFlag("");
        txnBean.setPayBy("3");
        txnBean.setScreenFlag("");
        txnBean.setSqNo(0);
        txnBean.setSubTokenNo("");
        txnBean.setTermId("");
        txnBean.setTokenNoDr(0);
        txnBean.setTrsNo(0);
        txnBean.setTxnStatus("");
        txnBean.setValueDate("");
        txnBean.setVoucherNo(0);
        txnBean.setAcNo(accNo);
        txnBean.setAmount(amount);
        txnBean.setTyDesc(type);
        txnBean.setCrAmt(crAmt);
        txnBean.setDrAmt(drAmt);
        txnBean.setDestBrId(brCode);
        txnBean.setDetails(details);
        txnBean.setDt(dateString);
        txnBean.setEnterBy("SYSTEM");
        txnBean.setInstDt(dateString);
        txnBean.setOrgnBrId(brCode);
        txnBean.setTranType("2");
        txnBean.setTranDesc("0");
        txnBean.setTranTypeDesc("TRANSFER");
        txnBean.setTy(ty);
        txnBean.setTdAcNo("");
        txnBean.setRecNo(recNo);
        txnBean.setValueDate(dateString);
        return txnBean;
    }

    private List getGlHeadForDD(String brnCode) {
        List returnList = new ArrayList();
        try {
            List list = entityManager.createNativeQuery("select distinct acno from gltable where acname in ('SERVICE TAX AND EDUCATION CESS',"
                    + "'COMM ON D.D','PAY ORDER') and substring(acno,1,2)='" + brnCode + "'").getResultList();
            if (!list.isEmpty()) {
                for (int j = 0; j < list.size(); j++) {
                    Vector ele = (Vector) list.get(j);
                    if (ele.get(0) != null) {
                        returnList.add(ele.get(0));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    private List getChargesInGLHeadBYAccountType(String acctType) {
        List returnList = new ArrayList();
        try {
            List chkGLHeadList = cmrFacade.chkGLHead(acctType);
            if (!chkGLHeadList.isEmpty()) {
                Vector ele = (Vector) chkGLHeadList.get(0);
                returnList.add(ele.get(0));
                returnList.add(ele.get(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    public List<CustomerIdPojo> getAllActiveAccountByCustomerId(String custId) throws ApplicationException {
        List<CustomerIdPojo> returnList = new ArrayList<CustomerIdPojo>();
        try {
            List dataList = entityManager.createNativeQuery("select c.custid,c.acno,ifnull(c.txnbrn,'') as brnName,"
                    + "a.accttype,a.curbrcode,ifnull(ac.acctdesc,'') as acTypeDesc,ifnull(ac.acctnature,'') as "
                    + "acNature,ac.accttype from customerid c,accountmaster a,accounttypemaster ac where "
                    + "c.custid='" + custId + "' and c.acno=a.acno and a.accstatus=1 and "
                    + "ifnull(a.authby,'')<>'' and a.accttype=ac.acctcode "
                    + "union "
                    + "select c.custid,c.acno,ifnull(c.txnbrn,'') as brnName,a.accttype,a.curbrcode,"
                    + "ifnull(ac.acctdesc,'') as acTypeDesc,ifnull(ac.acctnature,'') as acNature,ac.accttype from "
                    + "customerid c,td_accountmaster a,accounttypemaster ac where "
                    + "c.custid='" + custId + "' and c.acno=a.acno and a.accstatus=1 and "
                    + "ifnull(a.authby,'')<>'' and a.accttype=ac.acctcode").getResultList();
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                String acno = ele.get(1).toString().trim();
                String brnName = ele.get(2).toString().trim();
                String acCode = ele.get(3).toString().trim();
                String brnCode = ele.get(4).toString().trim();
                String acCodeDesc = ele.get(5).toString().trim();
                String acNature = ele.get(6).toString().trim();
                String acctType = ele.get(7).toString().trim();
                if (!(acCodeDesc.equals("") || acNature.equals(""))) {
                    CustomerIdPojo pojo = new CustomerIdPojo();
                    pojo.setCustId(custId);
                    pojo.setAcNo(acno);
                    pojo.setAcType(acCode);
                    pojo.setAcTypeDesc(acCodeDesc);
                    pojo.setBrCode(brnCode);
                    pojo.setTxnBrn(brnName);
                    pojo.setActNature(acNature);
                    pojo.setAccountType(acctType);
                    pojo.setBalance(getBalanceByAccountNumber(acno, ymdFormat.format(new Date())).toString());

                    returnList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    @Override
    public CustomerIdPojo getDetailOfAccountNumber(String acno) throws ApplicationException {
        CustomerIdPojo acDetailPojo = new CustomerIdPojo();
        try {
            List dataList = entityManager.createNativeQuery("select c.custid,c.acno,a.custname,a.accstatus,"
                    + "ifnull(ac.acctnature,'') as acNature,a.accttype,ac.accttype,ifnull(ac.acctdesc,'') as "
                    + "acTypeDesc,a.curbrcode,ifnull(c.txnbrn,'') as brnName from customerid c,accountmaster a,"
                    + "accounttypemaster ac where c.acno=a.acno and ifnull(a.authby,'')<>'' and "
                    + "a.accttype=ac.acctcode and a.acno='" + acno + "' "
                    + "union "
                    + "select c.custid,c.acno,a.custname,a.accstatus,ifnull(ac.acctnature,'') as acNature,"
                    + "a.accttype,ac.accttype,ifnull(ac.acctdesc,'') as acTypeDesc,a.curbrcode,"
                    + "ifnull(c.txnbrn,'') as brnName from customerid c,td_accountmaster a,"
                    + "accounttypemaster ac where c.acno=a.acno and ifnull(a.authby,'')<>'' and "
                    + "a.accttype=ac.acctcode and a.acno='" + acno + "'").getResultList();
            if (dataList.isEmpty() || dataList.size() > 1) {
                throw new ApplicationException("There is no proper detail for A/c:" + acno);
            }

            Vector ele = (Vector) dataList.get(0);
            String acNo = ele.get(1).toString().trim();
            String acNature = ele.get(4).toString().trim();
            String acCodeDesc = ele.get(7).toString().trim();
            if (!(acCodeDesc.equals("") || acNature.equals(""))) {
                acDetailPojo.setCustId(ele.get(0).toString().trim());
                acDetailPojo.setAcNo(acNo);
                acDetailPojo.setAcName(ele.get(2).toString().trim());
                acDetailPojo.setAccStatus(ele.get(3).toString().trim());
                acDetailPojo.setActNature(acNature);
                acDetailPojo.setAcType(ele.get(5).toString().trim());
                acDetailPojo.setAccountType(ele.get(6).toString().trim());
                acDetailPojo.setAcTypeDesc(acCodeDesc);
                acDetailPojo.setBrCode(ele.get(8).toString().trim());
                acDetailPojo.setTxnBrn(ele.get(9).toString().trim());
                acDetailPojo.setBalance(getBalanceByAccountNumber(acNo, ymdFormat.format(new Date())).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acDetailPojo;
    }

    @Override
    public String processChqBookRequest(String acno, BigInteger requestNo, String requestType, Integer noOfBooks,
            String noOfLeaves, String ibRequestDt, String deliveryAddress, String deliveryMode,
            String deliveryStatus) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            CustomerIdPojo pojo = getDetailOfAccountNumber(acno);
//            String invtType = pojo.getActNature().trim() + noOfLeaves.trim();
            String invtType = noOfLeaves.trim();
            if (!pojo.getAccountType().trim().toUpperCase().equals(invtType.trim().toUpperCase().substring(0, 2))) {
                throw new ApplicationException("Please select proper number of leaves for your account number.");
            }



            String branchBusinessDt = getBranchBusinessDt(pojo.getBrCode());
            if (branchBusinessDt.equals("")) {
                throw new ApplicationException("Cheque Book Request Error-001");
            }
            for (int i = 0; i < noOfBooks; i++) {
                Integer requestBrNo = i + 1;

                int n = entityManager.createNativeQuery("INSERT INTO ib_request(acno,request_no,request_type,"
                        + "ib_request_dt,cbs_request_dt,request_status,request_break_no,invt_class,invt_type,"
                        + "reschedule_date,reschedule_time,reschedule_reason,reschedule_by,charge_status,process_by,process_dt) "
                        + "VALUES('" + acno + "'," + requestNo + ",'" + requestType + "','" + ibRequestDt + "',"
                        + "'" + branchBusinessDt + "','NEW'," + requestBrNo + ",'CHQ','" + invtType + "',null,"
                        + "null,'','','N','',null)").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Cheque Book Request Error-002");
                }
            }
            //Delivery details
            int n = entityManager.createNativeQuery("INSERT INTO ib_delivery_details(request_no,delevery_agency,"
                    + "delivery_address,delivery_mode,delivery_status,delivery_date,update_by,update_time) "
                    + "VALUES(" + requestNo + ",'','" + deliveryAddress + "','" + deliveryMode + "',"
                    + "'" + deliveryStatus + "',null,'',null)").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Cheque Book Request Error-003");
            }

            /**
             * **********OLD CODE TO PROCESS IN ONE GO***************
             */
//            CustomerIdPojo pojo = getDetailOfAccountNumber(acno);
//            String invtType = pojo.getActNature().trim() + noOfLeaves.trim();
//            List list = entityManager.createNativeQuery("select chqsrno,chnofrom,chnoto,date_format(stockdt,'%d/%m/%Y') as "
//                    + "stockdt from chbookmaster_stock where brncode ='" + pojo.getBrCode() + "' and ent_flag ='SPLIT' and "
//                    + "invt_class = 'CHQ' and invt_type = '" + invtType + "' and status='F'").getResultList();
//            if (list.isEmpty()) {
//                System.out.println("Data not found in chbookmaster_stock table.");
//                throw new ApplicationException("Currently request can not be processed.");
//            }
//            Vector ele = (Vector) list.get(0);
//            String chqSrNo = ele.get(0).toString().trim();
//            Integer chqFrom = Integer.parseInt(ele.get(1).toString().trim());
//            Integer chqTo = Integer.parseInt(ele.get(2).toString().trim());
//
//            String msg = invtSplitRemote.ChqBookIssueSaveUpdation("save", chqFrom, chqTo, chqSrNo,
//                    Integer.parseInt(noOfLeaves), acno, pojo.getAcType().trim(), "",
//                    "System", ymdFormat.format(new Date()), "N", "CHQ", invtType);
//
//            if (!msg.equalsIgnoreCase("Record Saved Successfully")) {
//                throw new ApplicationException(msg);
//            }
            /**
             * **********OLD CODE TO PROCESS IN ONE GO END HERE***************
             */
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    @Override
    public String processStopPaymentRequest(String acno, BigInteger requestNo, String requestType,
            String ibRequestDt, long chqStart, long chqEnd, String option) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String chequeBookTable = "", statusflag = "";
        try {
            ut.begin();
            CustomerIdPojo pojo = getDetailOfAccountNumber(acno);
            //Cheques validation
            if (chqStart <= 0) {
                throw new ApplicationException("Please provide cheque no. Error-001");
            }
            if (option.equalsIgnoreCase("series")) {
                if (chqEnd <= 0) {
                    throw new ApplicationException("Please provide cheque no. Error-002");
                }
                if (chqEnd < chqStart) {
                    throw new ApplicationException("Please provide cheque no. Error-003");
                }
            } else {
                chqEnd = chqStart;
            }

            String branchBusinessDt = getBranchBusinessDt(pojo.getBrCode());
            if (branchBusinessDt.equals("")) {
                throw new ApplicationException("Cheque Book Request Error-004");
            }

            long tmpchqFrom = chqStart;
            chequeBookTable = cmrFacade.getChequeBookTable(ftsPostingMgmtFacade.getAccountNature(acno));
            while (tmpchqFrom <= chqEnd) {
                List list = entityManager.createNativeQuery("select statusflag from " + chequeBookTable + " where "
                        + "acno = '" + acno + "' and chqno = " + tmpchqFrom).getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("Instrument number does not exist. Cheque Number is " + tmpchqFrom);
                }
                Vector ele = (Vector) list.get(0);
                statusflag = ele.get(0).toString();
                if (statusflag.equalsIgnoreCase("U")) {
                    throw new ApplicationException("Cheque Number " + tmpchqFrom + " is already used. Error-005");
                } else if (statusflag.equalsIgnoreCase("S")) {
                    throw new ApplicationException("Cheque Number " + tmpchqFrom + " is already stopped. Error-006");
                }

                int n = entityManager.createNativeQuery("update " + chequeBookTable + " set statusflag = 'S',"
                        + "lastupdateby = 'IBS',lastupdatedt = '" + branchBusinessDt + "' where "
                        + "acno='" + acno + "' and chqno=" + tmpchqFrom).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Stop Payment Request Error-007");
                }
                tmpchqFrom = tmpchqFrom + 1;
            }

            int n = entityManager.createNativeQuery("insert into chbookdetail(acno,chbookno,chequeno,status,"
                    + "amount,favoring,chequedt,enterydate,enteredby,authby) values ('" + acno + "'," + chqStart + ","
                    + "" + chqEnd + ",9,0,'','" + branchBusinessDt + "','" + branchBusinessDt + "','IBS','IBS')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Stop Payment Request Error-008");
            }

            n = entityManager.createNativeQuery("INSERT INTO ib_request(acno,request_no,request_type,"
                    + "ib_request_dt,cbs_request_dt,request_status,request_break_no,invt_class,invt_type,"
                    + "reschedule_date,reschedule_time,reschedule_reason,reschedule_by,charge_status,process_by,process_dt) "
                    + "VALUES('" + acno + "'," + requestNo + ",'" + requestType + "','" + ibRequestDt + "',"
                    + "'" + branchBusinessDt + "','APPROVED',1,'" + String.valueOf(chqStart) + "',"
                    + "'" + String.valueOf(chqStart) + "',null,null,'','','N','',null)").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Stop Payment Request Error-009");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    @Override
    public String getBranchBusinessDt(String brCode) throws ApplicationException {
        String branchBusinessDt = "";
        try {
            List list = entityManager.createNativeQuery("select date from bankdays where "
                    + "brncode='" + brCode + "' and dayendflag='N' and dayendflag1='N' "
                    + "and daybeginflag='Y'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                branchBusinessDt = ele.get(0).toString().trim();
            } else {
                list = entityManager.createNativeQuery("select min(date) from bankdays where "
                        + "brncode='" + brCode + "' and dayendflag='Y' and dayendflag1='N' "
                        + "and daybeginflag='N'").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    branchBusinessDt = ele.get(0).toString().trim();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return branchBusinessDt;
    }

    @Override
    public List<RequestStatusObj> getUsersRequestStatus(List<Long> reqNoList) throws ApplicationException {
        List<RequestStatusObj> statusList = new ArrayList<RequestStatusObj>();
        try {
            for (int i = 0; i < reqNoList.size(); i++) {
                Long requestNo = reqNoList.get(i);
                List list = entityManager.createNativeQuery("select request_status from ib_request "
                        + "where request_no=" + requestNo + "").getResultList();
                if (!list.isEmpty()) {
                    String requestStatus = "APPROVED";

                    RequestStatusObj obj = new RequestStatusObj();
                    obj.setRequestNo(requestNo);
                    obj.setRequestStatus(requestStatus);
                    for (int z = 0; z < list.size(); z++) {
                        Vector ele = (Vector) list.get(z);
                        requestStatus = ele.get(0).toString().trim();
                        if (requestStatus.equalsIgnoreCase("NEW")) {
                            obj.setRequestStatus(requestStatus);
                            break;
                        }
                    }
                    statusList.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return statusList;
    }

    @Override
    public String updateRequestStatus(Long requestNo, String status) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = entityManager.createNativeQuery("select request_status from ib_request "
                    + "where request_no=" + requestNo + "").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("This process can not approve-Error-001");
            }

            for (int z = 0; z < list.size(); z++) {
                Vector ele = (Vector) list.get(z);
                String requestStatus = ele.get(0).toString().trim();
                if (!requestStatus.equalsIgnoreCase("NEW") && status.equalsIgnoreCase("CANCEL")) {
                    throw new ApplicationException("This request is in process-Error-002");
                }
            }
            int n = entityManager.createNativeQuery("update ib_request set "
                    + "request_status='" + status + "' where request_no=" + requestNo + "").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("This process can not approve-Error-003");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    @Override
    public CustomerDetailsPojo verifyRegisteringUser(String idType, String idNo) throws ApplicationException {
        CustomerDetailsPojo obj = new CustomerDetailsPojo();
        try {
            String query = "select c.customerid,a.acno,ifnull(c.mobilenumber,''),ifnull(per_email,'') from "
                    + "cbs_customer_master_detail c,customerid i,accountmaster a where c.customerid=i.custid and "
                    + "i.acno=a.acno and a.accstatus=1 and a.accttype in(select acctcode from accounttypemaster "
                    + "where (acctnature='" + CbsConstant.SAVING_AC + "' or acctnature='" + CbsConstant.CURRENT_AC + "'))";
            if (idType.equalsIgnoreCase("C")) { //Customer Id
                query = query + " and c.customerid='" + idNo + "' group by c.customerid";
            } else if (idType.equalsIgnoreCase("A")) { //Account Base
                query = query + " and a.acno='" + idNo + "'";
            }
            List dataList = entityManager.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Detail not found.");
            }

            Vector ele = (Vector) dataList.get(0);
            String mobileNo = ele.get(2).toString().trim();
            if (mobileNo.trim().length() == 10) {
                obj.setCustomerid(ele.get(0).toString().trim());
                obj.setAcno(ele.get(1).toString().trim());
                obj.setMobilenumber(mobileNo);
                obj.setEmailId(ele.get(3).toString().trim());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return obj;
    }

    @Override
    public List<CustomerIdPojo> customerTransactionalAccountNumbersByCustomerId(String custId) throws ApplicationException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<CustomerIdPojo> dataList = new ArrayList<>();
        try {
            List list = entityManager.createNativeQuery("select c.custId,c.acNo,ifnull(c.tranTime,''),ifnull(c.txnBrn,''),a.accttype,a.curbrcode from "
                    + "customerid c,accountmaster a where c.acno=a.acno and a.accstatus=1 and a.accttype in(select "
                    + "acctcode from accounttypemaster where (acctnature='" + CbsConstant.SAVING_AC + "' or "
                    + "acctnature='" + CbsConstant.CURRENT_AC + "')) and c.custid='" + custId + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("No transactional accounts for this customerid.");
            }
            for (int i = 0; i < list.size(); i++) {
                CustomerIdPojo idPojo = new CustomerIdPojo();
                Vector vtr = (Vector) list.get(i);
                idPojo.setCustId(vtr.get(0).toString());
                idPojo.setAcNo(vtr.get(1).toString());
                try {
                    idPojo.setTranTime(sdf.parse(vtr.get(2).toString()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                idPojo.setTxnBrn(vtr.get(3).toString());
                idPojo.setAcType(vtr.get(4).toString());
                idPojo.setBrCode(vtr.get(5).toString());
                dataList.add(idPojo);
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public String ibsIntraTrfTxnPosting(String debitAccountNo, String creditAccounNo, BigDecimal amount) throws ApplicationException {
        Float trsnumber;
        String businessDate;
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            float reconNo, reconNo1;
            Date dt = new Date();
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            trsnumber = ftsPostingMgmtFacade.getTrsNo();
            businessDate = commonReport.getBusinessDate();
            String intersoleDrAccount = debitAccountNo.substring(0, 2) + ftsPostingMgmtFacade.getAcnoByPurpose("INTERSOLE ACCOUNT");
            String intersoleCrAccount = creditAccounNo.substring(0, 2) + ftsPostingMgmtFacade.getAcnoByPurpose("INTERSOLE ACCOUNT");
            System.out.println(intersoleDrAccount);
            System.out.println(intersoleCrAccount);

            if (debitAccountNo.substring(0, 2).equalsIgnoreCase(creditAccounNo.substring(0, 2))) {
                reconNo = ftsPostingMgmtFacade.getRecNo();
                String insertRecons = ftsPostingMgmtFacade.insertRecons(commonReport.getAcNatureByAcNo(debitAccountNo), debitAccountNo, 1, amount.doubleValue(), ymdhms.format(ymd.parse(businessDate)), ymdhms.format(dt), 2,
                        "Mobile Intra Xfer Txn", "System", trsnumber, ymdhms.format(dt), reconNo, "Y", "", 133, 3, "0", "", 0f, "System", "A", 1, "", 0f, "", "", debitAccountNo.substring(0, 2), debitAccountNo.substring(0, 2), 0, "", "", "");
                if (insertRecons.substring(0, 4).equalsIgnoreCase("true")) {
                    int m = entityManager.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                            + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                            + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)values('" + debitAccountNo + "','" + ftsPostingMgmtFacade.ftsGetCustName(debitAccountNo) + "','" + ymdhms.format(ymd.parse(businessDate)) + "',"
                            + "'" + ymdhms.format(dt) + "', 0," + amount.doubleValue() + ",1,2,'" + reconNo + "','',null,3.0,1,'Y',"
                            + "'System', 133,0.0,'A','Mobile Intra Xfer Txn','System','" + trsnumber + "','" + ymdhms.format(dt) + "','',"
                            + "'" + debitAccountNo.substring(0, 2) + "','" + debitAccountNo.substring(0, 2) + "',0,'','')").executeUpdate();
                    if (m <= 0) {
                        throw new Exception("Insertion Problem in recon_trf_d for A/c No :- " + debitAccountNo);
                    }
                    String result = ftsPostingMgmtFacade.updateBalance(commonReport.getAcNatureByAcNo(debitAccountNo), debitAccountNo, 0, amount.doubleValue(), "Y", "Y");
                    if (!(result.equalsIgnoreCase("TRUE"))) {
                        ut.rollback();
                        throw new Exception(result);
                    }
                } else {
                    ut.rollback();
                    throw new Exception(insertRecons);
                }
                //for cr
                reconNo1 = ftsPostingMgmtFacade.getRecNo();
                String insertReconsC = ftsPostingMgmtFacade.insertRecons(commonReport.getAcNatureByAcNo(creditAccounNo), creditAccounNo, 0, amount.doubleValue(), ymdhms.format(ymd.parse(businessDate)), ymdhms.format(dt), 2,
                        "Mobile Intra Xfer Txn", "System", trsnumber, "", reconNo1, "Y", "", 133, 3, "0", "", 0f, "System", "A", 1, "", 0f, "", "", debitAccountNo.substring(0, 2), debitAccountNo.substring(0, 2), 0, "", "", "");
                if (insertReconsC.substring(0, 4).equalsIgnoreCase("true")) {
                    int m = entityManager.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                            + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                            + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)values('" + creditAccounNo + "','" + ftsPostingMgmtFacade.ftsGetCustName(creditAccounNo) + "','" + ymdhms.format(ymd.parse(businessDate)) + "',"
                            + "'" + ymdhms.format(dt) + "'," + amount.doubleValue() + ",0,0,2,'" + reconNo1 + "','',null,3.0,1,'Y',"
                            + "'System', 133,0.0,'A','Mobile Intra Xfer Txn','System','" + trsnumber + "','" + ymdhms.format(dt) + "','',"
                            + "'" + debitAccountNo.substring(0, 2) + "','" + debitAccountNo.substring(0, 2) + "',0,'','')").executeUpdate();
                    if (m <= 0) {
                        throw new Exception("Insertion Problem in recon_trf_d for A/c No :- " + creditAccounNo);
                    }
                    String balresult = ftsPostingMgmtFacade.updateBalance(commonReport.getAcNatureByAcNo(creditAccounNo), creditAccounNo, amount.doubleValue(), 0, "Y", "Y");
                    if (!(balresult.equalsIgnoreCase("TRUE"))) {
                        ut.rollback();
                        throw new Exception(balresult);
                    }
                } else {
                    ut.rollback();
                    throw new Exception(insertReconsC);
                }

            } else {
                // for different branch// for dr
                reconNo = ftsPostingMgmtFacade.getRecNo();
                reconNo1 = ftsPostingMgmtFacade.getRecNo();
                String insertrecon1 = ftsPostingMgmtFacade.insertRecons(commonReport.getAcNatureByAcNo(debitAccountNo), debitAccountNo, 1, amount.doubleValue(), ymdhms.format(ymd.parse(businessDate)), ymdhms.format(dt), 2,
                        "Mobile Intra Xfer Txn", "System", trsnumber, "", reconNo, "Y", "System", 133, 3, "0", "", 0f, "System", "A", 1, "", 0f, "", "", debitAccountNo.substring(0, 2), debitAccountNo.substring(0, 2), 0, "", "", "");
                if (insertrecon1.substring(0, 4).equalsIgnoreCase("true")) {
                    String insertrecon2 = ftsPostingMgmtFacade.insertRecons(commonReport.getAcNatureByAcNo(intersoleCrAccount), intersoleCrAccount, 1, amount.doubleValue(), ymdhms.format(ymd.parse(businessDate)), ymdhms.format(dt), 2,
                            "Mobile Intra Xfer Txn", "System", trsnumber, "", reconNo1, "Y", "System", 133, 3, "0", "", 0f, "", "A", 1, "", 0f, "", "", debitAccountNo.substring(0, 2), creditAccounNo.substring(0, 2), 0, "", "", "");
                    if (insertrecon2.substring(0, 4).equalsIgnoreCase("true")) {
                        int updatereconTrfd1 = entityManager.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                                + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                                + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)values('" + debitAccountNo + "','" + ftsPostingMgmtFacade.ftsGetCustName(debitAccountNo) + "','" + ymdhms.format(ymd.parse(businessDate)) + "',"
                                + "'" + ymdhms.format(dt) + "',0," + amount.doubleValue() + ",1,2,'" + reconNo + "','',null,3.0,1,'Y',"
                                + "'System', 133,0.0,'A','Mobile Intra Xfer Txn','System','" + trsnumber + "','" + ymdhms.format(dt) + "','',"
                                + "'" + debitAccountNo.substring(0, 2) + "','" + debitAccountNo.substring(0, 2) + "',0,'','')").executeUpdate();
                        if (updatereconTrfd1 <= 0) {
                            throw new Exception("Insertion Problem in recon_trf_d for A/c No :- " + debitAccountNo);
                        }
                        int updatereconTrfd2 = entityManager.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                                + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                                + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)values('" + intersoleCrAccount + "','" + ftsPostingMgmtFacade.ftsGetCustName(intersoleCrAccount) + "','" + ymdhms.format(ymd.parse(businessDate)) + "',"
                                + "'" + ymdhms.format(dt) + "',0," + amount.doubleValue() + ",1,2,'" + reconNo1 + "','',null,3.0,1,'Y',"
                                + "'System', 133,0.0,'A','Mobile Intra Xfer Txn','System','" + trsnumber + "','" + ymdhms.format(dt) + "','',"
                                + "'" + debitAccountNo.substring(0, 2) + "','" + creditAccounNo.substring(0, 2) + "',0,'','')").executeUpdate();
                        if (updatereconTrfd2 <= 0) {
                            throw new Exception("Insertion Problem in recon_trf_d for A/c No :- " + debitAccountNo);
                        }

                        String balupdateresult = ftsPostingMgmtFacade.updateBalance(commonReport.getAcNatureByAcNo(debitAccountNo), debitAccountNo, 0, amount.doubleValue(), "Y", "Y");
                        if (!(balupdateresult.equalsIgnoreCase("TRUE"))) {
                            ut.rollback();
                            throw new Exception(balupdateresult);
                        }
                    } else {
                        ut.rollback();
                        throw new Exception(insertrecon2);
                    }
                } else {
                    ut.rollback();
                    throw new Exception(insertrecon1);
                }

                //for cr

                reconNo = ftsPostingMgmtFacade.getRecNo();
                reconNo1 = ftsPostingMgmtFacade.getRecNo();
                String insertreconCR1 = ftsPostingMgmtFacade.insertRecons(commonReport.getAcNatureByAcNo(creditAccounNo), creditAccounNo, 0, amount.doubleValue(), ymdhms.format(ymd.parse(businessDate)), ymdhms.format(dt), 2,
                        "Mobile Intra Xfer Txn", "System", trsnumber, "", reconNo, "Y", "System", 133, 3, "0", "", 0f, "System", "A", 1, "", 0f, "", "", debitAccountNo.substring(0, 2), creditAccounNo.substring(0, 2), 0, "", "", "");
                if (insertreconCR1.substring(0, 4).equalsIgnoreCase("true")) {
                    String insertreconCR2 = ftsPostingMgmtFacade.insertRecons(commonReport.getAcNatureByAcNo(intersoleDrAccount), intersoleDrAccount, 0, amount.doubleValue(), ymdhms.format(ymd.parse(businessDate)), ymdhms.format(dt), 2,
                            "Mobile Intra Xfer Txn", "System", trsnumber, "", reconNo1, "Y", "System", 133, 3, "0", "", 0f, "", "A", 1, "", 0f, "", "", debitAccountNo.substring(0, 2), creditAccounNo.substring(0, 2), 0, "", "", "");
                    if (insertreconCR2.substring(0, 4).equalsIgnoreCase("true")) {
                        int m = entityManager.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                                + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                                + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)values('" + creditAccounNo + "','" + ftsPostingMgmtFacade.ftsGetCustName(creditAccounNo) + "','" + ymdhms.format(ymd.parse(businessDate)) + "',"
                                + "'" + ymdhms.format(dt) + "'," + amount.doubleValue() + ",0,0,2,'" + reconNo + "','',null,3.0,1,'Y',"
                                + "'System', 133,0.0,'A','Mobile Intra Xfer Txn','System','" + trsnumber + "','" + ymdhms.format(dt) + "','',"
                                + "'" + debitAccountNo.substring(0, 2) + "','" + creditAccounNo.substring(0, 2) + "',0,'','')").executeUpdate();
                        if (m <= 0) {
                            return "Insertion Problem in recon_trf_d for A/c No :- " + creditAccounNo;
                        }
                        int recontrfd = entityManager.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                                + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                                + "DEST_BRNID,TRAN_ID,adviceNo,adviceBrnCode)values('" + intersoleDrAccount + "','" + ftsPostingMgmtFacade.ftsGetCustName(intersoleDrAccount) + "','" + ymdhms.format(ymd.parse(businessDate)) + "',"
                                + "'" + ymdhms.format(dt) + "'," + amount.doubleValue() + ",0,0,2,'" + reconNo1 + "','',null,3.0,1,'Y',"
                                + "'System', 133,0.0,'A','Mobile Intra Xfer Txn','System','" + trsnumber + "','" + ymdhms.format(dt) + "','',"
                                + "'" + debitAccountNo.substring(0, 2) + "','" + creditAccounNo.substring(0, 2) + "',0,'','')").executeUpdate();
                        if (recontrfd <= 0) {
                            return "Insertion Problem in recon_trf_d for A/c No :- " + creditAccounNo;
                        }
                        String balupdateresult1 = ftsPostingMgmtFacade.updateBalance(commonReport.getAcNatureByAcNo(creditAccounNo), creditAccounNo, amount.doubleValue(), 0, "Y", "Y");
                        if (!(balupdateresult1.equalsIgnoreCase("TRUE"))) {
                            return balupdateresult1;
                        }
                    } else {
                        ut.rollback();
                        throw new Exception(insertreconCR2);
                    }
                } else {
                    ut.rollback();
                    throw new Exception(insertreconCR1);
                }
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
        return "TRUE" + ":" + trsnumber + ":" + businessDate;
    }

    @Override
    public String processImpsTxn(Long id, String userId, String debitAccount, String creditAccount, BigDecimal amount,
            String requestType, String beneficaryIfsc, String beneficaryName, String beneficaryMobileNo, String remarks,
            String orgnCode, String userName, String todayDt) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        Float trsNo;
        String businessDate = "", msg = "Failed", reqRefNo = "";
        try {
            ut.begin();
            trsNo = ftsPostingMgmtFacade.getTrsNo();
            businessDate = commonReport.getBusinessDate();

            ChannelReplyDto resultObj;
            String debitAccountName = ftsPostingMgmtFacade.ftsGetCustName(debitAccount);
            String partyBrnCode = ftsPostingMgmtFacade.getCurrentBrnCode(debitAccount);
            String intersoleAccount = ftsPostingMgmtFacade.getAcnoByPurpose("INTERSOLE ACCOUNT");
            String intersoleDrAccount = partyBrnCode + intersoleAccount;
            String intersoleCrAccount = orgnCode + intersoleAccount;
            String loginAlphaCode = commonReport.getAlphacodeByBrncode(orgnCode);
            String glAccount = ftsPostingMgmtFacade.getCodeFromCbsParameterInfo("IMPS-OW-HEAD");

            if (loginAlphaCode.equalsIgnoreCase("HO")) {
                Integer payBy = 3, trandesc = 134;
                String individualEntryDetail = "IMPS Txn Through Mobile ";

                String drAccountNature = ftsPostingMgmtFacade.getAccountNature(debitAccount);
                Float recNo = ftsPostingMgmtFacade.getRecNo();
                msg = ftsPostingMgmtFacade.insertRecons(drAccountNature, debitAccount, 1, amount.doubleValue(), businessDate,
                        todayDt, 2, individualEntryDetail, userName, trsNo, "", recNo, "Y",
                        userName, trandesc, payBy, null, null, 0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }

                int n = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                        + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                        + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                        + "values('" + debitAccount + "','" + debitAccountName + "','" + businessDate + "','" + todayDt + "',"
                        + "0," + amount.doubleValue() + ",1,2,'" + recNo + "','',null,'" + payBy + "',"
                        + "1,'Y','" + userName + "', " + trandesc + ",0.0,'A','" + individualEntryDetail + "','" + userName + "',"
                        + "'" + trsNo + "','" + ymdhms.format(date) + "','','" + orgnCode + "','" + partyBrnCode + "',0,'','')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Insertion problem in Recon_Trf_D for A/c No :- " + debitAccount);
                }

                recNo = ftsPostingMgmtFacade.getRecNo();
                msg = ftsPostingMgmtFacade.insertRecons("PO", intersoleDrAccount, 0, amount.doubleValue(), businessDate, todayDt,
                        2, individualEntryDetail, userName, trsNo, "", recNo, "Y", userName, trandesc,
                        payBy, "", "", 0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }

                n = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                        + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                        + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                        + "values('" + intersoleDrAccount + "','INTERSOLE ACCOUNT','" + businessDate + "','" + todayDt + "',"
                        + "" + amount.doubleValue() + ",0,0,2,'" + recNo + "','',null,'" + payBy + "',"
                        + "1,'Y','" + userName + "', " + trandesc + ",0.0,'A','" + individualEntryDetail + "','" + userName + "',"
                        + "'" + trsNo + "','" + ymdhms.format(date) + "','','" + orgnCode + "','" + partyBrnCode + "',0,'','')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Insertion problem in Recon_Trf_D for A/c No :- " + debitAccount);
                }

                recNo = ftsPostingMgmtFacade.getRecNo();
                msg = ftsPostingMgmtFacade.insertRecons("PO", glAccount, 0, amount.doubleValue(), businessDate, todayDt, 2,
                        individualEntryDetail + debitAccount, userName, trsNo, "", recNo, "Y",
                        userName, trandesc, payBy, null, null, 0f, "", "", 0, "", 0f, "", "", orgnCode, orgnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }

                n = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,trantype,"
                        + "recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,trsno,trantime,"
                        + "term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                        + "values('" + glAccount + "','IMPS-OW-HEAD','" + businessDate + "','" + todayDt + "',"
                        + "" + amount.doubleValue() + ",0,0,2,'" + recNo + "','',null,'" + payBy + "',"
                        + "1,'Y','" + userName + "', " + trandesc + ",0.0,'A','" + (individualEntryDetail + debitAccount) + "','" + userName + "',"
                        + "'" + trsNo + "','" + ymdhms.format(date) + "','','" + orgnCode + "','" + orgnCode + "',0,'','')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Insertion problem in Recon_Trf_D for A/c No :- " + debitAccount);
                }

                recNo = ftsPostingMgmtFacade.getRecNo();
                msg = ftsPostingMgmtFacade.insertRecons("PO", intersoleCrAccount, 1, amount.doubleValue(), businessDate, todayDt,
                        2, individualEntryDetail, userName, trsNo, "", recNo, "Y", userName, trandesc,
                        payBy, "", "", 0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }

                n = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                        + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                        + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                        + "values('" + intersoleCrAccount + "','INTERSOLE ACCOUNT','" + businessDate + "','" + todayDt + "',"
                        + "0," + amount.doubleValue() + ",1,2,'" + recNo + "','',null,'" + payBy + "',"
                        + "1,'Y','" + userName + "', " + trandesc + ",0.0,'A','" + individualEntryDetail + "','" + userName + "',"
                        + "'" + trsNo + "','" + ymdhms.format(date) + "','','" + orgnCode + "','" + partyBrnCode + "',0,'','')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Insertion problem in Recon_Trf_D for A/c No :- " + debitAccount);
                }

                msg = ftsPostingMgmtFacade.updateBalance(drAccountNature, debitAccount, 0, amount.doubleValue(), "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }

                msg = ftsPostingMgmtFacade.updateBalance("PO", glAccount, amount.doubleValue(), 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }
            }
            ftsPostingMgmtFacade.lastTxnDateUpdation(debitAccount);
            //IMPS Processing
            String logUrl = "", txnUrl = "", impsBin = "";
            if (requestType.trim().equalsIgnoreCase("IMPS")) {

                List impsDetailList = entityManager.createNativeQuery("select ifnull(a.code,'') as imps_vendor,ifnull(b.code,'') "
                        + "as imps_log_url,ifnull(c.code,'') as imps_txn_url,ifnull(d.code,'') as imps_bank_bin from "
                        + "(select ifnull((select code from cbs_parameterinfo  where name='IMPS-VENDOR'),'') as code) a,"
                        + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-LOG-URL'),'') as code) b,"
                        + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-TXN-URL'),'') as code) c,"
                        + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-BANK-BIN'),'') as code) d").getResultList();
                if (impsDetailList.isEmpty()) {
                    throw new Exception("Please define IMPS detail.");
                }
                Vector elem = (Vector) impsDetailList.get(0);
                String impsVendor = elem.get(0).toString().trim();
                if (impsVendor.equals("") || impsVendor.trim().length() == 0) {
                    throw new Exception("Please define IMPS Vendor.");
                }

                logUrl = elem.get(1).toString().trim();
                txnUrl = elem.get(2).toString().trim();
                impsBin = elem.get(3).toString().trim();
                if (logUrl.equals("") || txnUrl.equals("") || impsBin.trim().length() != 4) {
                    throw new Exception("Please define proper IMPS detail like Log Url,Txn Url and IMPS BIN");
                }
                System.out.println("Logged Request-->" + new Date());
                
                resultObj = sendImpsRequestByGetMethod(id, userId, debitAccount, creditAccount, amount,
                        requestType, beneficaryIfsc, beneficaryName, beneficaryMobileNo, remarks, impsBin, userName,
                        "logged", logUrl, txnUrl, partyBrnCode, debitAccountName); //For GET
                
                if (resultObj == null || resultObj.toString().equals("")) {
                    throw new Exception("No result found in logging the Mobile IMPS request.");
                }
                
                if (!(resultObj.getRespCode().trim().equalsIgnoreCase("00")
                        || resultObj.getRespCode().trim().equalsIgnoreCase("91"))) {
                    reqRefNo = resultObj.getUniqueBankRefNo();
                    throw new Exception("No proper result found in logging the Mobile IMPS request.");
                }
                
                String statusResult = getImpsTxnStatus(id, userId, debitAccount, creditAccount, amount, requestType, beneficaryIfsc,
                        beneficaryName, beneficaryMobileNo, remarks, todayDt, impsBin, userName,
                        logUrl, txnUrl, partyBrnCode, debitAccountName);

                String[] statusResponse = statusResult.split(":");
                reqRefNo = statusResponse[1].toString();

                if (statusResponse[0].equalsIgnoreCase("success")) {
                    int n = entityManager.createNativeQuery("INSERT INTO ib_txn_request (ib_request_id,  user_id,  debit_account_no,"
                            + " credit_account_no, amount,purpose, purpose_desc,request_type,beneficiary_ifsc, beneficiary_name,"
                            + " beneficiary_mobile, remarks, request_date, request_time, business_date, value_date, batch_no ,rrn)"
                            + " VALUES (" + id + ", '" + userId + "', '" + debitAccount + "', '" + creditAccount + "', " + amount + ","
                            + " '', '', '" + requestType + "', '" + beneficaryIfsc + "',  '" + beneficaryName + "', '" + beneficaryMobileNo + "',"
                            + " '" + remarks + "','" + todayDt + "', now(), '" + businessDate + "' ,'" + todayDt + "'  ,  " + trsNo + ",'" + statusResponse[1] + "');").executeUpdate();
                    if (n < 0) {
                        throw new Exception("Issue in response updation in ib txn request.");
                    }
                }
            }
            ut.commit();
            return msg + ":" + trsNo + ":" + businessDate; //On success msg will be true
        } catch (Exception e) {
            ut.rollback();
            String errorMessage = "Failed";
            if (e instanceof java.lang.NullPointerException) {
                errorMessage = errorMessage + "";
            } else {
                errorMessage = errorMessage + " " + e.getMessage();
            }
            try {
                ut.begin();
                int n = entityManager.createNativeQuery("INSERT INTO ib_txn_request (ib_request_id,  user_id,  debit_account_no,"
                        + " credit_account_no, amount,purpose, purpose_desc,request_type,beneficiary_ifsc, beneficiary_name,"
                        + " beneficiary_mobile, remarks, request_date, request_time, business_date, value_date, batch_no,rrn )"
                        + " VALUES (" + id + ", '" + userId + "', '" + debitAccount + "', '" + creditAccount + "', " + amount + ","
                        + " '', '', '" + requestType + "', '" + beneficaryIfsc + "',  '" + beneficaryName + "', "
                        + "'" + beneficaryMobileNo + "','" + remarks + "', '" + todayDt + "', now(), '" + businessDate + "' ,"
                        + "'" + todayDt + "'  , 0,'" + reqRefNo + "')").executeUpdate();
                if (n < 0) {
                    throw new Exception("Insertion problem in Ib_Txn_Staus");
                }
                ut.commit();
            } catch (Exception ex) {
                ut.rollback();
                errorMessage = errorMessage.trim() + " " + ex.getMessage();
            }
            return errorMessage.trim() + "::";
        }
    }

    public ChannelReplyDto sendImpsRequestByGetMethod(Long id, String userId, String debitAccount, String creditAccount,
            BigDecimal amount, String requestType, String beneficaryIfsc, String beneficaryName, String beneficaryMobileNo,
            String remarks, String impsBin, String userName, String txnMode, String logUrl, String txnUrl,
            String debitAccoutBrCode, String debitAccountName) throws Exception {
        ChannelReplyDto resultObj;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode internalNode = mapper.createObjectNode();

            internalNode.put("ChannelId", "00001"); //Finacus will tell us - Rohan
            internalNode.put("ChannelTerminalId", "00000"); //Finacus will tell us - Rohan
            internalNode.put("UniqueChnlRefNo", String.valueOf(id));
            internalNode.put("CustType", "M");
            internalNode.put("CustLBrCode", Integer.parseInt(debitAccoutBrCode));

            String debitAccountMobileNo = neftRemote.getAccountMobileNo(debitAccount);
            if (debitAccountMobileNo.equals("")) {
                throw new Exception("Debit mobile number not found !");
            }
            internalNode.put("CustMobNo", Long.parseLong(debitAccountMobileNo.substring(3))); //Sender Mobile No

            String custLongName = debitAccountName;
            custLongName = custLongName.replaceAll("[\\W_]", " ");
            custLongName = custLongName.length() > 50 ? custLongName.substring(0, 50) : custLongName;
            internalNode.put("CustLongName", custLongName);
            internalNode.put("CustAcctNo", debitAccount.trim());
            internalNode.put("CustMMID", impsBin + "001");
            internalNode.put("ModeOfTrn", "P2A");

            internalNode.put("BenefLongName", beneficaryName.trim()); //Beneficiary Name
            internalNode.put("BenefAadhaarNo", "");
            internalNode.put("BenefMobNo", beneficaryMobileNo.trim()); //Beneficiary Mobile
            internalNode.put("BenefAccNo", creditAccount.trim());
            internalNode.put("BenefIFSC", beneficaryIfsc.trim());
            internalNode.put("MakerUserId", "IMPSU");

            internalNode.put("CheckerUserId", "IMPSU");
            internalNode.put("OTP", "0");
            internalNode.put("MPIN", "0");
            internalNode.put("TrnAmount", amount); //Confirm from Finacus for point
            internalNode.put("UniqueBankRefNo", String.valueOf(id));
            internalNode.put("TrnRemarks", remarks.trim());
            internalNode.put("ChargeAmt", BigDecimal.ZERO);

            internalNode.put("InstNo", "000000000000");
            internalNode.put("InstType", "0");
            internalNode.put("SetNo", 10);
            internalNode.put("ScrollNo", 10);
            internalNode.put("BatchCode", "");
            internalNode.put("LbrCode", Integer.parseInt(debitAccoutBrCode));
            internalNode.put("EntryDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));

            mapper = new ObjectMapper();
            ObjectNode requestNode = mapper.createObjectNode();
            ((ObjectNode) requestNode).set("ChannelReqObj", internalNode);
            String JSON_STRING = requestNode.toString();
            System.out.println("Json Request Is-->" + JSON_STRING);

            DefaultHttpClient httpClient = new DefaultHttpClient();

            int timeout = 30; // seconds
            HttpParams httpParams = httpClient.getParams();
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            String url = "";
            if (txnMode.equalsIgnoreCase("logged")) {
//                url = "http://172.17.2.83/NagarBranchBankingService/RestWebService.svc/ProcessRequest/ProcChannelBranchBank?";
                url = logUrl;
            } else if (txnMode.equalsIgnoreCase("txn")) {
//                url = "http://172.17.2.83/NagarBranchBankingService/RestWebService.svc/ProcessRequest/BranchBnnkTxnStatus?";
                url = txnUrl;
            }

            List<NameValuePair> params = new LinkedList<>();

            params.add(new BasicNameValuePair("request", JSON_STRING));
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url = url + paramString;
            System.out.println("url :" + url);
            HttpGet getRequest = new HttpGet(url);
            getRequest.addHeader("Content-type", "application/json");

            System.out.println("Request Line-->" + getRequest.getRequestLine());
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            StringBuilder resultBuffer = new StringBuilder();
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                resultBuffer.append(output);
            }

            Document resultDoc = IblUtil.convertStringToDocument(resultBuffer.toString());
            System.out.println(resultDoc.getDocumentElement().getNodeName());
            Element ele = resultDoc.getDocumentElement();

            ImpsOwResponseDto owResponse;
            owResponse = mapper.readValue(ele.getFirstChild().getNodeValue(), ImpsOwResponseDto.class); //Might be change here
            resultObj = owResponse.getChannelReplyObj();
            System.out.println("Result Object-->" + resultObj);

            httpClient.getConnectionManager().shutdown();
            return resultObj;
            //End Here
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getImpsTxnStatus(Long id, String userId, String debitAccount, String creditAccount, BigDecimal amount,
            String requestType, String beneficaryIfsc, String beneficaryName, String beneficaryMobileNo, String remarks,
            String requestDate, String impsBin, String userName, String logUrl, String txnUrl, String partyBrnCode,
            String debitAccountName) throws Exception {
        ChannelReplyDto resultObj;
        try {
            System.out.println("IMPS Txn Request-->" + new Date());
            resultObj = sendImpsRequestByGetMethod(id, userId, debitAccount, creditAccount, amount, requestType,
                    beneficaryIfsc, beneficaryName, beneficaryMobileNo, remarks, impsBin, userName, "txn", logUrl, txnUrl,
                    partyBrnCode, debitAccountName); //For GET
            if (resultObj == null || resultObj.toString().equals("")) {
                throw new Exception("No result found in txn status of Mobile IMPS request.");
            }
            int smsCode = ftsPostingMgmtFacade.getCodeForReportName("IMPS-SMS");
            if (!(resultObj.getRespCode().trim().equalsIgnoreCase("00")
                    || resultObj.getRespCode().trim().equalsIgnoreCase("91"))) {
                if (smsCode == 1) {
                    List<TransferSmsRequestTo> smsList = new ArrayList<>();
                    String mobileNo = neftRemote.getAccountMobileNo(debitAccount.trim()).trim();
                    if (mobileNo.length() == 13) {
                        TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                        trfSmsTo.setAcno(debitAccount);
                        trfSmsTo.setPromoMobile(mobileNo);
                        trfSmsTo.setAmount(amount.doubleValue());
                        trfSmsTo.setDate(dmy.format(ymdFormat.parse(requestDate)));
                        trfSmsTo.setUserMsgId(debitAccount.substring(0, 10) + new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
                        trfSmsTo.setModuleName("IMPS");
                        trfSmsTo.setTxnType("W"); //Withdrawal
                        trfSmsTo.setFirstCheque("42"); //IMPS outward processing code
                        trfSmsTo.setLastCheque(creditAccount.trim() + "/" + resultObj.getUniqueBankRefNo().trim() + "/0");
                        smsList.add(trfSmsTo);
                    }
                    smsFacade.sendAtmSms(smsList);
                }
            } else if (!(resultObj.getRespCode().trim().equalsIgnoreCase("03")
                    || resultObj.getRespCode().trim().equalsIgnoreCase("3"))) {
                getImpsTxnStatus(id, userId, debitAccount, creditAccount, amount, requestType, beneficaryIfsc,
                        beneficaryName, beneficaryMobileNo, remarks, requestDate, impsBin, userName, logUrl,
                        txnUrl, partyBrnCode, debitAccountName);
            } else if (neftRemote.isTxnReversed(resultObj.getRespCode().trim())) {
                throw new Exception("Request not honored !");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "success" + ":" + (resultObj.getUniqueBankRefNo() == null ? "" : resultObj.getUniqueBankRefNo());
    }
}
