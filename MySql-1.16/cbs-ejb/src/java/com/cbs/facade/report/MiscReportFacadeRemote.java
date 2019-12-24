package com.cbs.facade.report;

import com.cbs.dto.RdReschedulePojo;
import com.cbs.dto.report.AccountEditHistoryPojo;
import com.cbs.dto.report.AccountOpenRegisterPojo;
import com.cbs.dto.report.AccountStatementReportPojo;
import com.cbs.dto.report.AccountStatusReportPojo;
import com.cbs.dto.report.AccountTransactionPojo;
import com.cbs.dto.report.BillSundryDetailsPojo;
import com.cbs.dto.report.CADebitBalancePojo;
import com.cbs.dto.report.CrDrBalancePojo;
import com.cbs.dto.report.CreditBalancePojo;
import com.cbs.dto.report.CustIdWiseAcStmt;
import com.cbs.dto.report.FdAccountDetailPojo;
import com.cbs.dto.report.Form60ReportPojo;
import com.cbs.dto.report.FormNo15gPart1Pojo;
import com.cbs.dto.report.IMPSTxnParameterPojo;
import com.cbs.dto.report.InoperativeMarkingPojo;
import com.cbs.dto.report.InterestFdReportPojo;
import com.cbs.dto.report.KYCcategorisationPojo;
import com.cbs.dto.report.KycAccountDetailPojo;
import com.cbs.dto.report.PendingChargesReportPojo;
import com.cbs.dto.report.SlabDetailPojo;
import com.cbs.dto.report.SmsPostingReportPojo;
import com.cbs.dto.report.TdsOnCashWithdrawalPojo;
import com.cbs.dto.report.TopDepositorBorrowerPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.dto.report.ZeroBalReportPojo;
import com.cbs.dto.report.ho.AtmDisputePojo;
import com.cbs.dto.report.ho.AtmStatusPojo;
import com.cbs.dto.report.ho.UserDepuTrfDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.CurrencyExchangePojo;
import com.cbs.pojo.CustAccountInfoPojo;
import com.cbs.pojo.DeletedTransactionPojo;
import com.cbs.pojo.DenominationDetailsPojo;
import com.cbs.pojo.DepositLoanPojo;
import com.cbs.pojo.JanDhanAcnoInfoPojo;
import com.cbs.pojo.PfmsRegistrationDetailPojo;
import com.cbs.pojo.FiuDormantToOperative;
import com.cbs.pojo.InoperativeToOperativePoJo;
import com.cbs.pojo.SalarySheetPojo;
import com.cbs.pojo.SmsAcnoRegistrationPojo;
import com.cbs.pojo.ThresoldTransactionInfoPojo;
import com.cbs.pojo.ctrMoreThan1Crore;
import com.cbs.pojo.exchangeDetailPojo;
import com.cbs.pojo.jdHavingDepositPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface MiscReportFacadeRemote {

    public List<AccountOpenRegisterPojo> accountOpenRegister(String acType, String fromDt, String toDt, String orgnBrId,String acNature) throws ApplicationException;

    public List<CADebitBalancePojo> CADebitBalance(String dt, String accountType, String brnCode) throws ApplicationException;

    public List<InoperativeMarkingPojo> inoperativeMarking(String acType, String date, int days, String brCode) throws ApplicationException;

    public List<AccountStatementReportPojo> getAccountStatementReport(String acNo, String fromDate, String toDate) throws ApplicationException;

    public List<CustIdWiseAcStmt> getCustIdAccountStatementReport(String acNo, String fromDate, String toDate) throws ApplicationException;

    public List<ZeroBalReportPojo> getZeroBalReportDetails(String DATE, String ACTYPE, String BRCODE, String acNature ,String txnType) throws ApplicationException;

    public List<AccountStatusReportPojo> getAccountStatusReport(String date, String brnCode) throws ApplicationException;

    public List getAccountDetails(String acno) throws ApplicationException;

    public String checkAcStatementChargeOpt(String checkOrigin) throws ApplicationException;

    public String originBranchAccountCharges(String acno, int rows, String orgBrnCode, String username, String fromdt, String toDt, int pages) throws ApplicationException;

    public String destinationBranchAccountCharges(String acno, int rows, String orgBrnCode, String username, String fromdt, String toDt, int pages) throws ApplicationException;

    public List getParameters(String acno, int rows, String orgBrnCode, String fromdt, String todt, int pages) throws ApplicationException;

    public String insertReasons(String acno, String dt, String remarks, String enterBy, String statementFromDt, String statementToDt, String orgBrnId) throws ApplicationException;

    public String saveAccountDetails(String acno, String fromDt, String toDt, String user) throws ApplicationException;

    public java.util.List getAllAccounType() throws com.cbs.exception.ApplicationException;

    public java.lang.String getAccDescriptionByAcctCode(java.lang.String code) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.report.HoIntCalPojo> getHoInterst(java.lang.String acno, java.lang.String fromDate, java.lang.String toDate, double roi) throws com.cbs.exception.ApplicationException;

    public java.lang.String checkAcno(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.report.AccountEditHistoryPojo> getAccountEditHistoryReport(java.lang.String acno, java.lang.String frmDt, java.lang.String toDt) throws com.cbs.exception.ApplicationException;

    public List<AccountStatementReportPojo> getAccountStatementReportWise(String acNo, String fromDate, String toDate) throws ApplicationException;

    public List<RdReschedulePojo> getAccountDetailsStatement(String acno) throws ApplicationException;

    public List<RdReschedulePojo> gridDetailAccountDetails(String acno) throws ApplicationException;

    public String SaveRecordRD(String acno, String openingDate, String installamt, int period, String EnterBy) throws ApplicationException;

    public List gettransDetailsReport(String frDt, String toDt, String brnCode, String fromTime, String tottime, int timeAllow, String reportType, String statusType,String bnkName) throws ApplicationException;

    public List<AccountTransactionPojo> getAccountTransactionDetails(String acno, String repType, double frAmt, double toAmt, String frDt, String toDt) throws ApplicationException;

    public List<KYCcategorisationPojo> getKYCcategDetails(String brnCode, String acctNature, String acctType, String toDt) throws ApplicationException;

    public List<FdAccountDetailPojo> getFdAccountDetail(String acCode, String fAmt, String tAmt, String fDt, String tDt, String brCode) throws ApplicationException;

    public List<CrDrBalancePojo> getMonthWiseCrDrBal(String brnCode, String acctType, String balType, String frDt, String toDt, String orderBy) throws ApplicationException;

     public List<TopDepositorBorrowerPojo> getTopDepositorBorrower(String brnCode, String opt, String frdate, String todate, int nos, String repType, String bnkBranch,String borrowerType, double frmAmt, double toAmt) throws ApplicationException;

    public List<UnclaimedAccountStatementPojo> getUnclaimedAccountDetails(String brnCode, String acType, String asOnDt, String frYear, String toYear, String acNature) throws ApplicationException;

    public List<UnclaimedAccountStatementPojo> getUnclaimedAccountStatement(String brnCode, String acType, String asOnDt, String frYear, String toYear, String acNature) throws ApplicationException;

    public List<BillSundryDetailsPojo> getBillSundryDetail(String brnCode, String tillDt) throws ApplicationException;

    public List<SlabDetailPojo> getSlabDetal(String acctNature, String frDt, String toDt) throws ApplicationException;

    public List getFdrdtltdAcctType() throws ApplicationException;

    public List<SmsPostingReportPojo> getSmsPostingDetail(String brnCode, String acType, String frDt, String toDt) throws ApplicationException;

    public List<KycAccountDetailPojo> getKycData(String brnCode, String asOnDt, String toDt, String repType,String repOption,String nature,String acType) throws ApplicationException;

    public List<AtmStatusPojo> getAtmStatusData(String brnCode, String reportType, String frDt, String toDt) throws ApplicationException;

    public List<UserDepuTrfDetailPojo> getUserDetailData(String frBranch, String toBranch, String frDt, String toDt) throws ApplicationException;

    public String markUnClaimed(String reportBranchCode, String acNature, List<UnclaimedAccountStatementPojo> unClaimedList,
            String today, String userName, String flag, String acType, String savingRoi) throws ApplicationException;

    public List<com.cbs.dto.report.AccountEditHistoryPojo> getSignatureReport(String brnCode, String acNat, String acType, String Opt) throws ApplicationException;

    public List<InterestFdReportPojo> getTdsFormDetails(String brCode, String acTp, String repTp, String fYear) throws ApplicationException;

    public List<SmsAcnoRegistrationPojo> smsAcnoRegister(String BrnCode, String actType, String fromDt, String toDt, String RepType) throws ApplicationException;

    public List<ThresoldTransactionInfoPojo> getThresoldData(String brCode, String frDt, String toDt, String optionDt) throws ApplicationException;

    public List<CustAccountInfoPojo> getCustAccountInfo(String brCode, String acNature, String acType, String frDt, String toDt) throws ApplicationException;

    public List<FormNo15gPart1Pojo> getForm15gPart1Data(String brCode, String custId, String finYear, String reportType, String optionType, String estimatedTotalIncomed, String totalNoForm, String aggregateAmt, boolean taxChecking, String assYear) throws ApplicationException;

    public List<FormNo15gPart1Pojo> getForm15g15hData(String brCode, String frDt, String toDt, String repType, String finYear) throws ApplicationException;

    public BigDecimal getTdsDeductedInterest(String brCode, String frDt, String toDt) throws ApplicationException;

    public BigDecimal getExemptedInterest(String brCode, String frDt, String toDt) throws ApplicationException;

    public BigDecimal getVoucherWiseInterest(String frDt, String toDt, String brQuery) throws ApplicationException;

    public BigDecimal getInterestOfMergeIdAfterToDate(String brCode, String frDt, String toDt) throws ApplicationException;

    public List<PfmsRegistrationDetailPojo> getPfmsRegstrationData(String brCode, String frDt, String toDt) throws ApplicationException;

    public List<CurrencyExchangePojo> getCurrencyExchangrData(String brCode, String toDt, String typeOfReport) throws ApplicationException;

    public List<ctrMoreThan1Crore> ctrMoreThan1CroreReport(List acNatureList, String acType, String trnCrType, String trnDrType, BigDecimal repTypeAmt, String fromDt, String toDt, String brCode, String repType, String userName, BigDecimal repToAmt, String amtType, String denominationFlag) throws ApplicationException;

    public List<ctrMoreThan1Crore> loanDeposit25LakhsOrMoreReport(List acNatureList, String acType, String trnCrType, String trnDrType, BigDecimal repTypeAmt, String fromDt, String toDt, String brCode, String repType, String userName, BigDecimal repToAmt, String amtType, String denominationFlag) throws ApplicationException;

    public List<JanDhanAcnoInfoPojo> getJanDhanAcnoInfoData(String RepType, String BrnCode, String actType, String fromDt, String toDt) throws ApplicationException;

    public List<jdHavingDepositPojo> getJdHavingData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount) throws ApplicationException;

    public List<FiuDormantToOperative> dormantToOperativeReport(String toDt, String brCode) throws ApplicationException;

    public List<InoperativeToOperativePoJo> dormantToOperativeReportDetails(String brCode, String repType, String acNature, String acType, String dateType, String fromDt, String toDt, String txnFromDt, String txnToDt) throws ApplicationException;

    public List<DenominationDetailsPojo> denominationDetailsReport(String BrnCode, String toDt,String userId) throws ApplicationException;

    public List<DenominationDetailsPojo> denominationDetailsAcWiseReport(String BrnCode, String toDt, String typeOfReport,String userId) throws ApplicationException;

    public List<DeletedTransactionPojo> getDeletedTransactionData(String brCode, String frDt, String toDt, String acNo, String reportType) throws ApplicationException;

    public List<ctrMoreThan1Crore> getDemandBankersChecqueData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount) throws ApplicationException;

    public List<DepositLoanPojo> getDepositLoanData(String RepType, String BrnCode, String fromDt, String toDt, String acType, String denoType) throws ApplicationException;

    public List<exchangeDetailPojo> getExchangeData(String RepType, String BrnCode, String fromDt, String toDt, String acType, String denoType) throws ApplicationException;

    public List<ctrMoreThan1Crore> getDemandBankersChecqueAllStatusData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount, String trnCrType, String trnDrType) throws ApplicationException;

    public List<ctrMoreThan1Crore> getReactivatedDormantAccountData(String RepType, String BrnCode, String actType, String fromDt, String toDt, BigDecimal amount, String trnCrType, String trnDrType) throws ApplicationException;

    public List<Form60ReportPojo> getFormSixtyDetail(String brnCode, String frmDate, String toDate, String opt) throws ApplicationException;

    public List<AccountOpenRegisterPojo> getAccountCategoryData(String brnCode, String acNature, String actCode, String actCategory, String toDate) throws ApplicationException;

    public List<SalarySheetPojo> getAccountSalarySheetData(String repType, String brnCode, String month, String year) throws ApplicationException;
    
    public List<AccountEditHistoryPojo> getSignatureAuthDetail(String acNat, String acType, String opt, String frmAcno, String toAcno, String frmDate, String toDate, String brnCode) throws ApplicationException;
    
    public List<PendingChargesReportPojo> getPendingChargesData(String branch,String tranType,String fromDate,String toDate)  throws ApplicationException;
    
    public List<AccountStatementReportPojo> getAccountStatementFdData(String acNo, String fromDate, String toDate) throws ApplicationException;
    
    public List<SalarySheetPojo> getEarningRecordsData(String empId, String frmDate, String toDate) throws ApplicationException;
    
    public List getDistinctChargeName(String chgType) throws ApplicationException;
    
    public List getChargeGlAndAmt(String acType, String chgName) throws ApplicationException;
    
    public String individualAccountCharges(String acno, String chargeAmt, String orgBrnCode, String username, String crHead, String chgType,String gstType) throws ApplicationException;

    public List<AtmStatusPojo> getAtmDetailData(String brnCode, String fileType, String status, String frDt, String toDt) throws ApplicationException;
    
    public List<IMPSTxnParameterPojo> getSarvatraImpsTxnParameter(String process, String mode, String frDt, String toDt, String brcode) throws ApplicationException;
    
    public List<AtmDisputePojo> getAtmDisputeReportData(String brnCode, String frDt, String toDt) throws ApplicationException;
   
    public List<TdsOnCashWithdrawalPojo> getTdsOnWithdrawalDetails(String  brncode, String fromdate ,String toDate ,String financialFromDate ,String financialToDate )throws ApplicationException;
    
    public List<CreditBalancePojo> getCreditBalanceDetails(String brncode,String fromDate,String toDate,String reportoption) throws ApplicationException;
}
