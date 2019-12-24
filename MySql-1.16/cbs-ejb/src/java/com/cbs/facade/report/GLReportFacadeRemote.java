package com.cbs.facade.report;

import com.cbs.dto.GLDetailsTable;
import com.cbs.dto.report.AgentCollectionPojo;
import com.cbs.dto.report.AggregateDepositTable;
import com.cbs.dto.report.BalanceBookReportPojo;
import com.cbs.dto.report.CashClosingReportPojo;
import com.cbs.dto.report.CashDepositAggTable;
import com.cbs.dto.report.CashInBankPojo;
import com.cbs.dto.report.CashReserveReportPojo;
import com.cbs.dto.report.CbsGeneralLedgerBookPojo;
import com.cbs.dto.report.ConsolidateProfitLossPojo;
import com.cbs.dto.report.FinalBalanceReportPojo;
import com.cbs.dto.report.GenReportLedgerTable;
import com.cbs.dto.report.GlbComperativePojo;
import com.cbs.dto.report.GlbTempActypeEntryPojo;
import com.cbs.dto.report.SundrySuspenseDetailPojo;
import com.cbs.dto.report.SuspenseGeneralPojo;
import com.cbs.dto.report.Td15HEntryCertificationPojo;
import com.cbs.dto.report.TrialBalancePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.ExpenditureBalDayWisePojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface GLReportFacadeRemote {

    public List<Td15HEntryCertificationPojo> getTd15HEntryCertification(String acNo, int fYear, String brCode) throws ApplicationException;

    public List<CashReserveReportPojo> getCashReserveReport(String date, String brCode) throws ApplicationException;

    public List<BalanceBookReportPojo> getBalanceBookReport(String fromDate, String toDate, String brCode,String exCounterInclude) throws ApplicationException;

    public List<CashClosingReportPojo> getCashClosingReport(String fromDate, String toDate, String brCode) throws ApplicationException;

    /**
     * CONVERSION OF CBS_REP_AGGRIGATE_DEPOSIT_REPORT function GET THE AGGREGATE
     * DEPOSIT REPORT FOR DIFFERENT ACCOUNT NATURE
     *
     * @param tillDate
     * @param brncode
     * @return
     */
    public List<AggregateDepositTable> getAggregateDepositReport(String tillDate, String brncode) throws ApplicationException;

    /**
     * ******************************************************************
     * FOLLOWING THREE FUNCTIONS ARE OF ALL LEDGER AND SUBSIDIARY REPORTS
     * /*******************************************************************
     */
    /**
     * CONVERSION OF FUNCTION CBS_REP_ALL_LEDGER getAbb_bill_ddRowSet24
     *
     * @param glType
     * @param acc
     * @param accAll
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<GenReportLedgerTable> genRepAllLedger(String glType, String acc, String accAll, String fromDate, String toDate, String brcode, String txnMode) throws ApplicationException;

    /**
     * conversion of CBS_REP_ALLLED_ACC .
     *
     * @param glType
     * @param brnCode
     * @return
     */
    public List<GLDetailsTable> getGLtypeHeads(String glType, String brnCode) throws ApplicationException;

    /**
     * Conversion of CBS_REP_SUB_BOOK_STM function getAbb_bill_ddRowSet25
     *
     * @param glType
     * @param acc
     * @param accAll
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<GenReportLedgerTable> subsidiaryBookStatement(String glType, String acc, String accAll, String fromDate, String toDate, String brcode, String txnMode) throws ApplicationException;

    public List<CashDepositAggTable> cashDepositAggregateReport(String repTypeOption, String acNature, String acCode, String tranType, String amountType, String fromDate, String toDate, String brCode, String optType, double fromAmt, double toAmt) throws ApplicationException;

    /**
     * ******************************************************************
     * END OF FUNCTIONS ALL LEDGER AND SUBSIDIARY REPORTS
     * /*******************************************************************
     */
    public List<CbsGeneralLedgerBookPojo> getGeneralLedgerBookData(String userDate, String orgBrnCode) throws ApplicationException;

    public List<FinalBalanceReportPojo> getFinalBalanceReport(String status, String tmpAcNat, String fromAccNo, String toAccNo, String userDate, String orgBrnCode, String agCode, String type,String exCounterInclude) throws ApplicationException;

    public List<TrialBalancePojo> getNewTrialBalanceReport(String userDate, String orgBrnCode,String exCounterFlag) throws ApplicationException; 
    public List<TrialBalancePojo> getTrialBalanceReportData(String userDate, String orgBrnCode) throws ApplicationException;

    public java.util.List<com.cbs.dto.report.ProfitAndLossPojo> getProfitAndLossDetails(java.lang.String userDate, java.lang.String orgBrnCode, java.lang.String selectOption) throws com.cbs.exception.ApplicationException;

    public List<GlbTempActypeEntryPojo> getconsolidateGeneralLedgerBookData(String userDate, String orgBrnCode, String nBrCode) throws ApplicationException;

    public List<GlbTempActypeEntryPojo> getNewConsolidateGLBData(String userDate, String orgBrnCode, String nBrCode, String exCounterInclude) throws ApplicationException;

    public List<ConsolidateProfitLossPojo> getConsolidateProfitLoss(String userDate, String orgBrnCode, String selectOption, String flag,String exCounterInclude) throws ApplicationException;

    //public List<CbsGeneralLedgerBookPojo> getGeneralLedgerBookDataOne(String userDate, String orgBrnCode) throws ApplicationException;
    public double IncomeExp(String userDate, String orgBrnCode, String orgBrCode) throws ApplicationException;

    public String getHalfYearEndFlag() throws ApplicationException;

    public List getBranchCodeListExt(String orgnCode) throws ApplicationException;

    public List<CashInBankPojo> getCashInBankData(String userDate, String orgBrnCode) throws ApplicationException;

    public double brWiseIncomeExp(String userDate, String orgBrnCode) throws ApplicationException;

    public List<GlbComperativePojo> getComperativeGlbData(String userDate1, String userDate2, String orgBrnCode, String nBrCode) throws ApplicationException;

    public List<Td15HEntryCertificationPojo> getTdsCertificateData(String repType, String acNo, String fYear) throws ApplicationException;

    public List<SuspenseGeneralPojo> getSuspenseGeneralData(String userDate, String orgBrnCode, String acNo) throws ApplicationException;

    public List<AgentCollectionPojo> getAgentCollectionReport(String status, String tmpAcNat, String fromAccNo, String toAccNo, String fromDate, String toDate, String orgBrnCode, String agCode) throws ApplicationException;

    public List<ExpenditureBalDayWisePojo> getExpenditureData(String orgBrnCode, String frDt, String toDt, String amt,String amtReqOption,String reportType) throws ApplicationException;

    public List<GenReportLedgerTable> expenditureReportAmtWise(String brcode, String glType, String acc, String fromDate, String toDate, String reportAmt, String txnMode) throws ApplicationException;

     public List glHeadList(String reportType) throws ApplicationException;
             
    public List<SundrySuspenseDetailPojo> sundrySuspenseReportDetail(String brcode, String fromDate, String toDate, String type, String glHead,String reportOption,String partialReconcilType) throws ApplicationException;

 }
