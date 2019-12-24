/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.common;

import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface FtsPostingMgmtFacadeRemote {

    public String ftsPosting43CBS(String acNo, Integer tranType, Integer ty, Double amt, String dt, String valueDt,
            String enterBy, String orgBrCode, String destBrCode, Integer tranDesc, String details, Float trsNo,
            Float recNo, Integer tranId, String termId, String auth, String authBy, String subTokenNo, Integer payBy,
            String instNo, String instDt, String tdacNo, Float vchNo, String intFlag, String closeFlag,
            String screenFlag, String txnStatus, Float tokenNoDr, String cxSxFlag, String adviceNo,
            String AdviceBrnCode, String entryType) throws ApplicationException;

    public String ftsDateValidate(String dtTransaction, String orgnBrCode) throws ApplicationException;

    public int dateDiff(String frmDate, String toDate) throws ApplicationException;

    public String ftsAcnoValidate(String acctNo, int tyFlag, String entryType) throws ApplicationException;

    public String ftsUserValidate(String user, String orgnBrCode) throws ApplicationException;

    public String checkBalance(String acctNo, double amount, String Userid) throws ApplicationException;

    public String checkBalForOdLimit(String acctNo, double amount, String Userid) throws ApplicationException;

    public String ftsGetBal(String acctNo) throws ApplicationException;

    public String ftsGetCustName(String acno) throws ApplicationException;

    public Float getRecNo() throws ApplicationException;

    public void updateRecNo(float recNo) throws ApplicationException;

    public Float getTrsNo() throws ApplicationException;

    public Float ftsTokenTable(Integer ty, String subTokenNo, String version) throws ApplicationException;

    public String insertRecons(String actNature, String acno, Integer ty, double amt, String dt, String valueDt, Integer tranType, String details,
            String enterBy, Float trsno, String tranTime, Float recno, String auth, String authBy, Integer tranDesc, Integer payBy,
            String instno, String instDt, Float tokenNo, String tokenPaidBy, String subTokenNo, Integer iy, String tdAcno, Float voucherNo,
            String intFlag, String closeFlag, String orgBrnId, String destBrnId, Integer tranId, String termId, String adviceNo, String adviceBrnCode) throws ApplicationException;

    public String updateBalance(String acctNature, String acno, double cramt, double dramt, String balanceflag, String clearedbalanceflag) throws ApplicationException;

    public String updateCheque(String acno, Integer payBy, Integer ty, Float chqNo, String authBy) throws ApplicationException;

    public String processBill(String acno, String instNo, String instDt, double amount, Integer ty, Integer tranType, String authBy) throws ApplicationException;

    public String loanDisbursementInstallment(String accNo, double amount, int ty, String authBy, String dt, float recNo, int transDesc, String remarks) throws ApplicationException;

    public String cbsAuthCashCrMakeEntry(int msgNo, int msgBillStart, String status, int msgBillPo, int msgBillEnd, String authBy, String date,
            Float seqNo, String instNo, String billType, String acno, String custName, String payableAt, double amount, String dt1,
            String originDt, int timeLimit, double comm, int tranType, int ty, String inFavourOf, String enterBy,
            String lastUpdateBy, Float recNo, String orgnBrCode) throws ApplicationException;

    public String cbsAuthCashCrSrfentry(float recNo, String authBy, String originDt, String orgnBrCode) throws ApplicationException;

    public String lPading(String padVar, int padLength, String padChar) throws ApplicationException;

    public String daybeginDate(String orgnBrCode) throws ApplicationException;

    public String ftsInstDateValidate(String instDate) throws ApplicationException;

    public String chkBal(String ACNO, Integer TY, Integer TRANDESC, String ACCTNATURE) throws ApplicationException;

    public String getIntTrfAcNo(String userAccountNumber) throws ApplicationException;

    public String getNewAccountNumber(String userAccountNumber) throws ApplicationException;

    public String getNewAccountNumberForHo(String userAccountNumber) throws ApplicationException;

    public String getAccountNature(String accNo) throws ApplicationException;

    public String getActNatureFor8DigitGLCode(String accNo) throws ApplicationException;

    public String getCurrentBrnCode(String accNo) throws ApplicationException;

    public String getAccountCode(String accNo) throws ApplicationException;

    public List getAcctTypeDesc() throws ApplicationException;

    public List getUnAuthorizedTranList(String acctcode, String brCode) throws ApplicationException;

    public List getUnAuthorizedTranListForAcno(String acno) throws ApplicationException;

    public String getOriginBranch() throws ApplicationException;

    public String getMiniStatement(String acno, String bankName) throws ApplicationException;

    public String checkDuplicateToken(String tokenNo, String subTokenNo, String brCode, String dt) throws ApplicationException;

    public String getCashMode(String brCode) throws ApplicationException;

    public String getAcNatureByCode(String acCode) throws ApplicationException;

    public String getBankCode() throws ApplicationException;

    public String getOfficeAccountNo(String acno) throws ApplicationException;

    public List getOfficeHeadDetails(String acno) throws ApplicationException;

    public List checkOfficeAccount() throws ApplicationException;

    public String getAcnoByPurpose(String purpose) throws ApplicationException;

    public List getAdviceList(int groupcode) throws ApplicationException;

    public String getAccountStatusMessage(int accStatus) throws ApplicationException;

    public boolean isUserAuthorized(String userId, String brCode) throws ApplicationException;

    public boolean existInParameterInfoReport(String reportName) throws ApplicationException;

    boolean isInstrumentLost(float seqNo, String instNo, String issueDt, String issueBrCode, String billType) throws ApplicationException;

    public List getBaseParameter(String accode) throws ApplicationException;

    public List isModuleActiveBasedOnAcCode(String accode) throws ApplicationException;

    public List getCurrentFinYear(String brCode) throws ApplicationException;

    public boolean isPoDdGlhead(String glHead) throws ApplicationException;

    public String getPoDdGlhead(String billNature) throws ApplicationException;

    public String getGlHeadFromParam(String brCode, String headName) throws ApplicationException;

    public List getSmsDetails() throws ApplicationException;

    public String getAcctTypeByCode(String acCode) throws ApplicationException;

    public String getCustName(String acno) throws ApplicationException;

    public String getThriftAccountNumber(String userAccountNumber) throws ApplicationException;

    public String getCodeFromCbsParameterInfo(String name) throws ApplicationException;

    public String getglheadDD(String acno) throws ApplicationException;

//    public String insertSms(String acno, String message) throws ApplicationException;
    public boolean isBranchExists(String brncode) throws ApplicationException;

    public boolean isActiveAgent(String agentCode, String brnCode) throws ApplicationException;

    public boolean isValidDddAccount(String acno) throws ApplicationException;

    public String getAccountTable(String nature) throws ApplicationException;

    public String markUnClaimed(String reportBranchCode, String acNature, List<UnclaimedAccountStatementPojo> unClaimedList,
            String today, String userName, String flag, String acType, String savingRoi) throws ApplicationException;

    public String getCodeByReportName(String reportName) throws ApplicationException;

    public List<String> getAccountPresentStatus(String acno) throws ApplicationException;

    public String isPrimaryAc(String custId, String acno) throws ApplicationException;

    public String isAadharExists(String custId, String aadharNo) throws ApplicationException;

    public String isLpgIdExists(String custId, String lpgId) throws ApplicationException;

    public boolean isAccountNPA(String acno) throws ApplicationException;

    public String lastTxnDateUpdation(String acno) throws ApplicationException;

    public String fdPaymentRenewalTxn(String accNature, String acNo, Integer tranType, Integer ty, Double amt, String dt, String valueDt,
            String enterBy, String orgnBrCode, String destBrCode, Integer tranDesc, String details, Float trsNo, Float recNo, Integer tranId,
            String termId, String auth, String authBy, String subTokenNo, Integer payBy, String instNo, String instDt, String tdacNo, Float vchNo,
            String intFlag, String closeFlag, String screenFlag, String txnStatus, Float tokenNoDr) throws ApplicationException;

    public Integer getCodeForReportName(String reportName) throws ApplicationException;

    public List getThreshLmtParam() throws ApplicationException;

    public String isThreshLmtExceed(String acNo, double amt, String dt) throws ApplicationException;

    public String saveTLAcOpenDetailWithOutTranMgmt(String cust_id, String tmpANat, String appTp, String appSeq,
            String FYear, String actype, String orgncode, String agcode, String acOpDt, String title,
            String custname, String fathername, String praddress, String craddress, String phoneno,
            String panno, String dob, Integer occupation, String grdname, String grd_relation,
            Integer operatingMode, String JtName1, String JtName2, String JtName3, String JtName4,
            String nominee, String nominee_relatioship, String nomineeAdd, String nomineeDob,
            String acnoIntro, Float Odlimit, Float roi, String sancDtV, String IntOpt, String subSidyAmt,
            Integer docuno, String docudetails, String spInst, String UserText, String schemeCode,
            Integer moritoriumPeriod, Float acnoPreDr, Float acnoPreCr, String rateCode, String calMethod, String calOn,
            String intAppFrequency, String calLevel, String compoundFrequency, String disbursmentType, String intCode,
            String paggingFrequency, Integer LoanPeriod1, Integer LoanPeriod2, String jtCustId1, String jtCustId2,
            String jtCustId3, String jtCustId4, String actCateg, String folioNo, String hufFamily, int chqOpt) throws ApplicationException;

    public String saveDlAcctOpenRegisterWithOutTranMgmt(String actype, String Occupation, String OpMode, String orgncode, String agcode,
            Float Sanclimit, Float ROI, String Title, String custname, String CrAddress, String PrAddress, String phone,
            String enterby, String pan, String FrName, String Age, String JtName1, String JtName2, String IntAcct, String catvalue,
            String CustIDExist, String schemeCode, String intTableCode, Integer moratoriunPd, Float accPrefDr,
            Float accPrefCr, String rateCode, String disbType, String calcMethod, String calcOnInt, String calLevel,
            String compFreq, Integer peggFreq, String intAppFreq, Integer loanPdMonth,
            Integer loanPdDay, List table, String dob, String custId1, String custId2, String actCateg, String acOpenFromFlag, String folioNo, String hufFamily) throws ApplicationException;

    public String saveDlLoanSecurity(List table, String Tempacno, String orgncode, String agcode) throws ApplicationException;

    public String saveAccountOpenSbRdWithOutTranMgmt(String cust_type, String cust_id, String actype, String title, String custname, String craddress, String praddress,
            String phoneno, String dob, int occupation, int operatingMode, String panno, String grdname, String grd_relation, String agcode, String DateText,
            String UserText, String fathername, String acnoIntro, String JtName1, String JtName2, String orgncode, String nominee, String nominee_relatioship,
            String JtName3, String JtName4, int rdperiod, float rdinstall, float rdroi, int docuno, String docudetails, String nomineeAdd, String nomineeDate,
            String custid1, String custid2, String custid3, String custid4, String schemeCode, String intCode, String actCateg, String folioNo, String hufFamily, int chqOpt) throws ApplicationException;

    public List getAcctCodeDetails(String acctcode) throws ApplicationException;

//    public List<String> getTaxDetail(String applicableDt) throws ApplicationException;
    public String getDdAccountNo(String acno) throws ApplicationException;

    public List getIssuedDdList(String opt) throws ApplicationException;

    public List getDdInstNoDetails(String opt, String instNo) throws ApplicationException;

    public List getAllDdGlhead(String billNature) throws ApplicationException;

    public String getGlTranInfo(String acno, Integer ty, double amt) throws ApplicationException;

    public String ftsAcnoValidateAuto(String acctNo, int tyFlag) throws ApplicationException;

    public List chkDdflag() throws ApplicationException;

    public List chkBadPerson(String title, String name, String bob, String pob,
            String designation, String address, String good, String low,
            String passportNo, String nationality) throws ApplicationException;

    public List autoPayLienStatus(String acNo, double recptNo) throws ApplicationException;

    public String npaPosting(List<NpaAccountDetailPojo> npaList, String dt, String enterBy, String orgnCode) throws ApplicationException;

    public String insertionAsPerPrincipalInt(String acNo, String toDt, double intAmt) throws ApplicationException;

    public String npaRecoveryUpdation(float trsNumber, String nature, String acNo, String curDt, double amount, String brCode, String toBrCode, String authBy) throws ApplicationException;

    public String getKeyPwd() throws ApplicationException;

    public String isReceiptExist(String acno) throws ApplicationException;

    public String receiptNotCreated(String tempBd, String brnCode) throws ApplicationException;

    public String strDetailInsertion(String alertSubCode, String acNo, Float trSno, Float recNo, String orginBrCode, String destBrCode, String enterBy) throws ApplicationException;

    public String strDetailDeletion(String acNo, Float trSno, Float recNo, String dt, String enterBy) throws ApplicationException;

    public double getDebitAmtByAcno(String acNo, String frdt, String toDt) throws ApplicationException;

    public List getTotDebitAndCreditTran(String acno, String dt) throws ApplicationException;

    public boolean xchangeDenoModule(String brnCode) throws ApplicationException;

    public Boolean[] ddsCashReceivedFlag(String brnCode) throws ApplicationException;

    public List getTotDebitAndCreditAfterCir(String acno, String dt) throws ApplicationException;

    public String getCircularPassInfo(String acno, String dt, double amt) throws ApplicationException;

    public List getTotCreditAfterCir(String acno, String dt) throws ApplicationException;

    public String getNpciBankCode() throws ApplicationException;

    public List getNameForcts(String acName, String acNo) throws ApplicationException;

    public List getAccountDetails(String branchCode, String accountType, String accountNo) throws ApplicationException;

    public List getReceiptNo(String branchCode, String acType, String accountNo, String receiptNo) throws ApplicationException;

    public List getCheqRetChgAndHead(String purpose, String acType) throws ApplicationException;

    public String chequeStatus(String acno, String chqNo) throws ApplicationException;

    public String getBusinessDate() throws ApplicationException;

    public Map<Integer, String> clearingReturnReasonInMap() throws Exception;

    public String getOldAccountNumber(String userAccountNumber) throws ApplicationException;

    public String ftsgetPanNo(String destAcNo) throws ApplicationException;

    public String ftsgetjointDetail(String destAcNo) throws ApplicationException;

    public String ftsgetJointname(String destAcNo) throws ApplicationException;

    public String isATMHead(String acno, String orgBrCode) throws ApplicationException;

    public List getAccountDetailFromAccountMaster(String acno) throws ApplicationException;

    public String isIdentityNoExists(String identityNo) throws ApplicationException;

    public String isCustomerIdentityNoExists(String identityNo) throws ApplicationException;

    public List getBranchNameEmail(String brcode) throws ApplicationException;

    public List getGlHeadbalance(String acno) throws ApplicationException;

    public List getMaxyearEndDate(String year) throws ApplicationException;

    public List isVerifyPending(String acNo) throws ApplicationException;

    public List getChequeReturnCharge(String chargeType, String chargeName, double amount, String accountType) throws ApplicationException;

    public String loanInstallmentReversal(String acNo, String date, String recNo, String updateBy) throws ApplicationException;

    public String isMinorCustomerId(String customerId) throws ApplicationException;

    public String ftsStockStatementExipryValidate(String acno, String todayDate) throws ApplicationException;

    public String getValueFromCbsparameterInfo(String name) throws ApplicationException;

    public String autoInterestSaveLoanInterestParameter(String financialYear, String acctType, String interestType, String intOption, String userName, String todayDate, String brnCode, String frDt, String chgParameter, String acctNature) throws ApplicationException;
}