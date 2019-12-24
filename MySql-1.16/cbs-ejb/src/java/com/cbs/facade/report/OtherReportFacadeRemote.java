package com.cbs.facade.report;

import com.cbs.dto.MprPojo;
import com.cbs.dto.UserReportTable;
import com.cbs.dto.cdci.NomineeDetailsPojo;
import com.cbs.dto.loanInsurancePojo;
import com.cbs.dto.other.BranchPerformancePojo;
import com.cbs.dto.report.AccountOpenIntroducerPojo;
import com.cbs.dto.report.AccountReportPojo;
import com.cbs.dto.report.AcntCloseDetailsTable;
import com.cbs.dto.report.ActiveRdReportPojo;
import com.cbs.dto.report.AgentLedgerReportPojo;
import com.cbs.dto.report.AmortizationReportPojo;
import com.cbs.dto.report.ArfBrcPojo;
import com.cbs.dto.report.AtmTransactionStatusPojo;
import com.cbs.dto.report.BalanceCertificatePojo;
import com.cbs.dto.report.CashDepositAggregateAnyOneDayPojo;
import com.cbs.dto.report.CashTranRepPojo;
import com.cbs.dto.report.CheqHonouredCertificate;
import com.cbs.dto.report.ChqBookStopTable;
import com.cbs.dto.report.Closing_CashPojo;
import com.cbs.dto.report.CtrArfPojo;
import com.cbs.dto.report.CtrPojo;
import com.cbs.dto.report.CustomerEnquiryPojo;
import com.cbs.dto.report.DICGCDetailReportPojo;
import com.cbs.dto.report.DepositWithdrawlPojo;
import com.cbs.dto.report.DepositesReportPojo;
import com.cbs.dto.report.ExceptionReportPojo;
import com.cbs.dto.report.ExcessTransactionPojo;
import com.cbs.dto.report.IMPSTxnParameterPojo;
import com.cbs.dto.report.InterestCertificatePojo;
import com.cbs.dto.report.InterestFdReportPojo;
import com.cbs.dto.report.InterestReportPojo;
import com.cbs.dto.report.IssueChequeBookRegisterPojo;
import com.cbs.dto.report.LoanIntCertPojo;
import com.cbs.dto.report.MinBalanceChargesPostPojo;
import com.cbs.dto.report.MinorAccountPojo;
import com.cbs.dto.report.NominationRegisterPojo;
import com.cbs.dto.report.OnUsReportTable;
import com.cbs.dto.report.PayOrderPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.RdInstallmentPojo;
import com.cbs.dto.report.SignatureReportPojo;
import com.cbs.dto.report.StandingErrorPojo;
import com.cbs.dto.report.StandingInstructionTodayExecutedReportPojo;
import com.cbs.dto.report.SundrySuspencePojo;
import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.dto.report.TdNewAndReNewPojo;
import com.cbs.dto.report.TdPeriodReportPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.dto.report.TransactionAggregatePojo;
import com.cbs.dto.report.TransactionAggregateTotalPojo;
import com.cbs.dto.report.TransactionDetailsPojo;
import com.cbs.dto.report.TransferBatchPojo;
import com.cbs.dto.report.UltilityReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AtmCardIssueDetailPojo;
import com.cbs.pojo.FolioLedgerPojo;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface OtherReportFacadeRemote {

    public List<AcntCloseDetailsTable> getAcctCloseDetails(String accountNature, String acctNature, String fromDate, String toDate, String brnCode) throws ApplicationException;

    public List<ChqBookStopTable> chequeStopDetails(String reportType, String accountWise, String fromDate, String toDate, String brCode) throws ApplicationException;

    public List<OnUsReportTable> getOnUsReport(String date, String scrollType, String brCode, String fromTime, String toTime, String timeAllowed) throws ApplicationException;

    public List<OnUsReportTable> dataAddition(List resultSet, String table, List<OnUsReportTable> appendedList, String trsno, String chqTrsno) throws ApplicationException;

    public List<OnUsReportTable> getOffUsReport(String date, String scrollType, String brCode, String fromTime, String toTime, String timeAllowed) throws ApplicationException;

    public List<BalanceCertificatePojo> balanceCertificate(String accountNo, String dt, String brnCode) throws ApplicationException;

    public List<IssueChequeBookRegisterPojo> issueChequeBookRegister(int reportType, String accountWise, String fromDate, String ToDate, String brnCode) throws ApplicationException;

    public List<ExcessTransactionPojo> excessTransaction(double amount, String trDate, String brCode) throws ApplicationException;

    public List<InterestCertificatePojo> interestCertificate(String acType, String acno, String fromDt, String toDt, String brCode, String agCode) throws ApplicationException;

    public List<AccountOpenIntroducerPojo> accountOpenIntroducer(String checkBox, String acountOwner, String acType, String acnoText, String fromDt, String toDt, String brCode, String agCode) throws ApplicationException;

    public List<TransferBatchPojo> transferBatch(String voucherNo, String date, String brnCode) throws ApplicationException;

    public List<StandingInstructionTodayExecutedReportPojo> StandingInstructionTodayExecutedReport(String date, String tranType, String brnCode) throws ApplicationException;

    public List billTypeLoad() throws ApplicationException;

    public List<PayOrderPojo> getPoDdData(String billType, String mode, String dateValue, String fromdttmp, String todttmp, String bcode) throws ApplicationException;

    public List<DICGCDetailReportPojo> getDICGCReport(String date, String acType, String instCode, String brCode, String type, String accBalType, String deafAcType) throws ApplicationException;

    public List<DepositWithdrawlPojo> depositWithdrawlReport(int reporttype, String actype1, String frmDate, String toDate, String brcode, double amt) throws ApplicationException;

    public List<CashTranRepPojo> cashTransferReport(int reporttype, String actype1, double amt, String frmdt, String todt, String brCode, String ty) throws ApplicationException;

    public List<TdActiveReportPojo> getTdActiveReport(int option, String actype, int month, int yyyy, String brncode, String frmdt, String todt, int tempDays, float frmroi, float toroi, String orderby) throws ApplicationException;

    public List<TransactionAggregatePojo> transactionAggregateReport(int reportType, String acType, double amount, String frmDt, String toDt, String brnCode) throws ApplicationException;

    public List<TransactionAggregateTotalPojo> transactionAggregateReportTotal(int reportType, String acType, double amount, String frmDt, String toDt, String brnCode) throws ApplicationException;

    public List<StandingErrorPojo> stanIntReport(String frmdt, String todt, String brncode) throws ApplicationException;

    public List<ExceptionReportPojo> exceptionTrnasctionReport(String todt, String brncode) throws ApplicationException;

    public List getAcctTypeList() throws ApplicationException;

    public List getAcctTypeListCashTrf() throws ApplicationException;

    public List getDicgcAcTypeList() throws ApplicationException;

    public List getLongBook(String accType, int status, String date, String brnCode, String fromTime, String tottime, int timeAllow, String orderby) throws ApplicationException;

    public List<SundrySuspencePojo> getSundryData(java.lang.String brncode, int reporttype, String todate, String fromdt);

    public List getAcctType() throws ApplicationException;

    public List getRefcodeDesc(String refcode) throws ApplicationException;

    public List<CustomerEnquiryPojo> getCustomerEnquiry(String query, String sb1, boolean balanceOption) throws ApplicationException;

    public List<UserReportTable> getUserInfo(String brncode, String selectCriteria) throws ApplicationException;

    public List<CheqHonouredCertificate> chequeHonouredCertificate(String accountNo, int chequeNo) throws ApplicationException;

    public List getRdAccountCode() throws com.cbs.exception.ApplicationException;

    public List<ActiveRdReportPojo> getRdActiveReportDetails(String acCode, String date, String brncode) throws ApplicationException;

    public List<MprPojo> getMprDetails(String fromDt, String toDt, String brncode) throws ApplicationException;

    public List getReportWriter(String frmDt, String toDt, String acctype, int orderBy, boolean flag, String branch,
            String status, String custStatus, boolean avgBalFlag, String annualIncomeFrom, String annualIncomeTo,
            String areaSector, String occupation, String riskCategorization, String fromAge,
            String toAge, String fromAcno, String toAcno,String reportOption) throws ApplicationException;

    public List getCustomersData(String branch, int orderBy, String custStatus, String annualIncomeFrom, String annualIncomeTo,
            String areaSector, String occupation, String riskCategorization, String fromAge,
            String toAge, boolean shareQuery) throws ApplicationException;

    public List<CustomerEnquiryPojo> getCustomerEnquiry(String sb, String sb1, double balance, int fromAge, int toAge, Date parse, double avgBalance) throws com.cbs.exception.ApplicationException;

    public List getBranchCode(int parseInt) throws com.cbs.exception.ApplicationException;

    public List<LoanIntCertPojo> getBranchWiseBal(String brCode, String dt) throws com.cbs.exception.ApplicationException;

    public List<AccountReportPojo> getPrintAction(String codeBr, String calFromDate, String caltoDate, String orderBy, String brCode, String tranType) throws ApplicationException;

    public List<AgentLedgerReportPojo> getAgentLedgerAction(String agentCode, String acctType, String calFromDate, String caltoDate, String OrgnBrCode) throws ApplicationException;

    public List getActTypeList() throws ApplicationException;

    public List getagentWiseMonthlyReport(String orgnBrCode, String acType, String Frdt, String Todt);

    public List<RdInstallmentPojo> getRdInstallmentDetails(String opt, String acno, String date, String brncode) throws ApplicationException;

    public List getAcctTypeList1() throws ApplicationException;

    public List<loanInsurancePojo> getInsuranceReport(String insurancePolicy, String acctType, String caltoDate, int day, String branchCode) throws ApplicationException;

    public List<InterestReportPojo> interestReport(String acCode, String fAmt, String tAmt, String fDt, String tDt, String brCode, String tdsApp, String repType) throws ApplicationException;

    public List<MinorAccountPojo> getMinorAccount(String tillDate, String acctCode, String branchCode) throws ApplicationException;

    public List<InterestFdReportPojo> interestFdReport(String acNature, String acCode, String fAmt, String tAmt, String fDt, String tDt, String brCode, String repType) throws ApplicationException;

    public List<AmortizationReportPojo> getAmortizationReport(String asOnDate) throws ApplicationException;

    // public List<AtmTransactionStatusPojo> getAtmTransactionStatus(String type, String transaction, String brnCode, String frDate, String toDate, String atmId, String status) throws ApplicationException;
    public List<AtmTransactionStatusPojo> getAtmTransactionStatus(String type, String transaction, String brnCode, String frDate, String toDate, String atmId, String status, String mode, String AccountNo) throws ApplicationException;

    public List<SignatureReportPojo> getSignatureList(String sign, String acctCode, String branchCode) throws ApplicationException;

    public List<DepositesReportPojo> getDepositesReport(String report, String actype, String frmDate, String toDate, String brcode, double amt, String reportFormatIn) throws ApplicationException;

    public List getAcctsbcaTypeList() throws ApplicationException;

    public List getInoperativeReport(String orgnBrCode, String Frdt, String Todt) throws ApplicationException;

    public String getCodeFromCbsParameterInfo(String name) throws ApplicationException;

    public List<InterestFdDepositesRepPojo> interestFdDepositesReport(String acCode, String amt, String fDt, String tDt, String brCode) throws ApplicationException;

    public List<TdNewAndReNewPojo> getTdNewAndRenewData(String brCode, String status, String frDt, String toDt) throws ApplicationException;

    public List<TdPeriodReportPojo> getTdPeriodReportData(String brCode, String date) throws ApplicationException;

    public List<CashDepositAggregateAnyOneDayPojo> getCashDepositAnyOneDay(int reportType, String acType, double amount, String frmDt, String toDt, String brnCode, String option) throws ApplicationException;

    public List<TdReceiptIntDetailPojo> getTdReceiptIntData(String allAcNo, String acNo, String frDt, String toDt, String receiptNo, String brCode, String acType, String tdsOption, String summaryOpt, String recFrDt, String recToDt, String actCatgory, double fromAmt, double toAmt) throws ApplicationException;

    public List getKycReport(String branch) throws ApplicationException;

//    public List <ReportProfilePojo>getReportProfilesData(String brCode,String acctcode,String actNature)throws ApplicationException;
//    
//    public List <ReportProfilePojo>getReportProfilesDataDetail(String brCode,String acctcode,String actNature, double frRoi,double toRoi,String frPeriod,String toPeriod,double frAmt,double toAmt,boolean roiBoolean,boolean periodBoolean,boolean amountBoolean)throws ApplicationException;
//    
    public List getAcctTypeExceptGL() throws ApplicationException;

    public List<MinBalanceChargesPostPojo> lockerExeErrReport(String repType, String repDt, String orgBrCode) throws ApplicationException;

    public List<AtmCardIssueDetailPojo> getAtmCardIssueDetail(String branch, String statusType, String frDt, String toDt) throws ApplicationException;

    public List<RbiSossPojo> getTdsIntSummary(String brnCode, String acType, String fromDt, String toDt) throws ApplicationException;

    public List<TdReceiptIntDetailPojo> getInterestCertificateDate(String custId, List<TdReceiptIntDetailPojo> reportList, String ftDate, String toDate) throws ApplicationException;

    public List<FolioLedgerPojo> lockerOperationDetail(String frmdt, String todt, String brncode, String reportOption, String lockerNumber, String reportType) throws ApplicationException;

    public List<BranchPerformancePojo> getDetailBranchPerformance(String fromDt, String toDt, String branchCode) throws ApplicationException;

    public List<UltilityReportPojo> getUtilityReportDetails(String brnCode, String remarks, String frDate, String toDate, String mode, String acno,String searchBy,String serchByOption) throws ApplicationException;

    public List<Closing_CashPojo> get_ClosingCashDetails(String branch, String frmDt, String toDt, String limit) throws ApplicationException;

    public List<IMPSTxnParameterPojo> getDetailImpsTxnParameter(String process, String mode, String frDt, String toDt, String user, String brcode) throws ApplicationException;

    public List<TransactionDetailsPojo> getDetailImpsOutward(String process, String frDt, String toDt, String user, String brnCode, String mode) throws ApplicationException;

    public List<TdActiveReportPojo> getDuplicateReceiptDetails(String brnCode, String frmDt, String toDt) throws ApplicationException;

    //   public List<BranchPerformancePojo> getDetailBranchConversion(String toDt, String branchCode) throws ApplicationException;
    public List<IMPSTxnParameterPojo> getImpsTxnData(String frDt, String toDt, String brcode) throws ApplicationException;

    public List<NominationRegisterPojo> getNomineeDetails(String brnCode, String fromDate , String toDate ,String reportType,String nomStatus) throws ApplicationException;
}