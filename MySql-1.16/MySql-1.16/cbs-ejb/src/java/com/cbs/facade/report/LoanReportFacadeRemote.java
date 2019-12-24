package com.cbs.facade.report;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.loan.CCTurnOverReportPojo;
import com.cbs.dto.loan.StatementOfAlmPojo;
import com.cbs.dto.report.AcTypeWiseDisdreceiptPojo;
import com.cbs.dto.report.WorkingStmtBorrowingPojo;
import com.cbs.dto.report.LienReportPojo;
import com.cbs.dto.report.LoanAcDetailStatementPojo;
import com.cbs.dto.report.LoanAccStmPojo;
import com.cbs.dto.report.LoanBorrowerModPojo;
import com.cbs.dto.report.LoanIntCalculationPojo;
import com.cbs.dto.report.LoanIntCertPojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.LoanOutStandingBalancePojo;
import com.cbs.dto.report.LoanPeriodPojo;
import com.cbs.dto.report.LoanSanctionDetailPojo;
import com.cbs.dto.report.NpaAccStmPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.dto.report.ho.LoanEmiDetailPojo;
import com.cbs.dto.report.ho.LoanSchemeWiseDetailPojo;
import com.cbs.dto.report.ho.NoticeToBorrowerPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.DateSlabPojo;
import com.cbs.pojo.FolioLedgerPojo;
import com.cbs.pojo.GuarantorDetailPojo;
import com.cbs.pojo.LoanPrincipalInterestPojo;
import com.cbs.pojo.LoanRenewalSecurityPojo;
import com.cbs.pojo.RecoveryDetailPojo;
import com.cbs.dto.report.LoanIntCertPoJoAll;
import com.cbs.dto.report.LoanRecoveryTable;
import com.cbs.dto.report.ho.StockStatementRepPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LoanReportFacadeRemote {

    public List getSchemeType(String accType) throws ApplicationException;
    
    public List<LoanMisCellaniousPojo> cbsLoanMisReport(String branchCode, String acNature, String accType, String dt, String reportBasedOn, double from, double to, String reportBase, String sector, String subSector, String modeOfAdvance, String secured, String typeOfAdvance, String purposeOfAdvance, String guarnteeCover, String purOfAdv, String exposure, String exposureCategory, String exposureCategoryPurpose, String schemeCode, String intType, String applicantCategory, String categoryOpt, String minorCommunity, String relation, String relOwner, String drawingPwrInd, String popuGroup, String sanctionLevel, String sanctionAuth, String courts, String restructuring, String loanPeriod, String gender, String reportIn, String parameter,String industry,String overDue,String npaClass,String withInsurance, String withSecurity  ,String securityNature,
   String securityType,String securityDesc1,String securityDesc2) throws ApplicationException;

    public List<LoanIntCertPojo> cbsrepLoanIntCert(String acno, String fromdt, String todt, String brncode) throws ApplicationException;

    public List<LoanIntCalculationPojo> loanInterestCalculation(String brncode) throws ApplicationException;

    public List<LoanIntCertPoJoAll> loanIntDetailsAll(String acType, String fromdt, String todt, String brncode,String schemeType) throws ApplicationException;

    public List getDistinctAcCodeByAcNature() throws ApplicationException;

    public java.util.List<com.cbs.dto.report.LoanAccStmPojo> loanAccountStatement(java.lang.String acno, java.lang.String frmdt, java.lang.String todt) throws ApplicationException;

    public List<LoanAccStmPojo> noDuesCertificate(String acno, String frmdt) throws ApplicationException;

    public java.util.List getAccList() throws com.cbs.exception.ApplicationException;

    public List getLoanAccList() throws ApplicationException;

    public List getLoanTypeList() throws ApplicationException;

    public java.util.List getGridDataList(java.lang.String refrecNo) throws ApplicationException;

    public java.util.List<com.cbs.dto.report.LoanRecoveryTable> loanRecoveryReport(java.lang.String acctCode, java.lang.String frmdt, java.lang.String todt, String branchCode,String reportType,String acNature) throws ApplicationException;

    public java.util.List<com.cbs.dto.report.LoanAcDetailsTable> loanAcDetailsReport(java.lang.String acctCode, java.lang.String tilldt, String branchCode) throws ApplicationException;

    public java.util.List<com.cbs.dto.report.LoanRepaymentPojo> loanRepaymentSchedule(java.lang.String acno, java.lang.String brnCode) throws ApplicationException;

    //  public List getOverDueSectorDetails(String brnCode) throws ApplicationException;
    public List getTLCode() throws ApplicationException;

    public List getNpaStatus(String acno) throws ApplicationException;

    public List<NpaAccStmPojo> npaAccountStatement(String acno, String frmdt, String todt) throws ApplicationException;

    public List<LoanPeriodPojo> npaInterestData(String repType, String AcctCode, String FromDt, String ToDt, String brncode) throws ApplicationException;

    public double fnBalosForAccountAsOn(String acno, String dt) throws ApplicationException;

    public List getBorrowAccList() throws com.cbs.exception.ApplicationException;

    public List<WorkingStmtBorrowingPojo> workingStmtBorrowData(String AcctCode, String FromDt, String brncode, String finDt, String PrevDt) throws ApplicationException;

    public String getFindate(String FromDt, String brncode) throws ApplicationException;

    public List getNatureTypeList() throws ApplicationException;

    public List getDisburseList(String acType,String acNature, double disFrAmount, double disToAmount, String disFromDate, String disToDate, String orderBy, String brCode) throws ApplicationException;

    public List getAdvancesAccList() throws com.cbs.exception.ApplicationException;

    public List workingStmtAdvancesData(String SelectAcType, String Frdt, String orgnBrCode, String yearstart) throws ApplicationException;

    public List workingStmtAbstractAdvancesData(String Frdt, String orgnBrCode, String yearstart) throws ApplicationException;

    public List<AcTypeWiseDisdreceiptPojo> getAcTypeWiseDisdreceipt(String tillDate, String acctCode, String branchCode, String reportType) throws ApplicationException;

    public java.util.List getAccListType() throws com.cbs.exception.ApplicationException;

    public double openingBalance(String acno, String date) throws com.cbs.exception.ApplicationException;

    public List getChargeName() throws ApplicationException;

    public List depositAcType() throws ApplicationException;

    public List<LienReportPojo> getDepositLienReport(String acctType, String asOnDate, String brCode) throws ApplicationException;

    public List<LienReportPojo> getLienReport(String acctType, String asOnDate, String brCode) throws ApplicationException;

    public List<LienReportPojo> getLienReportNew(String acctType, String asOnDate, String brCode) throws ApplicationException;

    public List<LienReportPojo> getLienReportNewOptimized(String acctType, String asOnDate, String brCode) throws ApplicationException;

    public List getAcctTypecadlList() throws ApplicationException;

    public List getFDMSRDnatureList() throws ApplicationException;

    public List<LoanAcDetailStatementPojo> getLoanAcDetailStmt(String brCode, String acctType, String acNo, String frDt, String todt) throws ApplicationException;

    public List<VillageWiseEMIDetailPojo> getVillWeseEmiDetail(String repType, String brCode, String acctType, String acNo, String frDt, String toDt, String accountManager, String groupId) throws ApplicationException;

    List<LoanOutStandingBalancePojo> getLoanOutStandingReport(String brCode, String acType, String toDate, double frAmt, double toAmt, String acNature, String balType) throws ApplicationException;

    public List<StatemenrtOfRecoveriesPojo> getStatementRecoveriesData(String area, String tillDate, String brCode,String reportOption) throws ApplicationException;

    public String getSchemeCodeAcNoWise(String acNo) throws ApplicationException;

    public List<LoanSanctionDetailPojo> getLoanSanctionDetail(String brCode, String acttype, String fromDt, String toDt) throws ApplicationException;

    public BigDecimal getCheckLimit(String acno) throws ApplicationException;

    public List<DateSlabPojo> getLimitOnRangeOfDate(String fromDt, String toDt, String acno) throws ApplicationException;

    public List<NoticeToBorrowerPojo> getNoticeToBorrowerData(String brCode, String repType, String acNo, String acType, String asOnDt, String day) throws ApplicationException;

    public List getStdAssect() throws ApplicationException;

    public List<RecoveryDetailPojo> getLoanRecoveryDetail(String custId, String date, String area, String reportType, String brCode) throws ApplicationException;

    public List schemeCodeCombo(String acctype) throws ApplicationException;

    public String schemeCodeDesc(String schemeCode) throws ApplicationException;

    public List<LoanSchemeWiseDetailPojo> getLoanSchemeDetail(String brCode, String asOnDt, String acttype, String schemeCode) throws ApplicationException;

    public List<LoanEmiDetailPojo> getLoanEmiDetail(String brCode, String acNature, String acType, String asOnDt) throws ApplicationException;

    public List<LoanPrincipalInterestPojo> getLoanPrincipalInterest(String brCode, String acType, String frDt, String asOnDt) throws ApplicationException;

    public List<FolioLedgerPojo> getFolioLedgerData(String folioNo, String frDt, String toDt) throws ApplicationException;

    public double getAcRestBalAsPerDmdFlowId(String acNo, String dt, String dmdFlowId) throws ApplicationException;

    public double getAcRecAsOnDateAsPerDmdFlowId(String acNo, String dt, String dmdFlowId) throws ApplicationException;

    public double getCrEntryAsOnDt(String acNo, String dt) throws ApplicationException;

    public double getPostingDmdAmtAsPerDmdFlowId(String acNo, String dt, String dmdFlowId) throws ApplicationException;

    //public List<FolioLedgerPojo> getFolioLedgerData1(String folioNo, String frDt, String toDt) throws ApplicationException;
    public List getFolioLedgerHeaderData(String folioNo) throws ApplicationException;

    public List<GuarantorDetailPojo> getguarantorData(String brCode, String reportType, String guarantor, String acNo, String asOnDt) throws ApplicationException;

    public List<LoanRenewalSecurityPojo> getLoanRenwalData(String brCode, String acType, String repType, String asOnDt, int days) throws ApplicationException;

    public List<StatemenrtOfRecoveriesPojo> getAreaWiseRecoveryData(String area, String brCode, String repType, String frDt, String toDt, Integer srNo, String isOverDue) throws ApplicationException;

    public double getCustIdWiseBal(String brCode, String custid, String dt) throws ApplicationException;

    public NpaAccountDetailPojo npaAccountAddition(String accStatus, String tillDate, String acno, String custname, BigDecimal outStandBal, String acctNature) throws ApplicationException;

    public String getRefRecDesc(String refCode) throws ApplicationException;

    public BigDecimal getOverDueAmount(String acno, String asOnDt) throws ApplicationException;

    public List<OverdueEmiReportPojo> getGuarantorOverdueData(String brCode, String asOnDt) throws ApplicationException;

    public double getshareBal(String folioNo, String dt) throws ApplicationException;

    public List<LoanRecoveryTable> getLoanRecoveryWithPercentage(String acType, String acNature, String frDt, String toDt, String Percent, String brnCode, String mode) throws ApplicationException;
    

    public List<LoanAccStmPojo> loanAccountStatementBifurficationPrinInt(String acno, String frmdt, String todt,String brnCode) throws ApplicationException;

    public List<LoanBorrowerModPojo> getLoanBorrowerDtl(String branchCode, String acNature, String accType, String fromDate, String toDate,String acno) throws ApplicationException; 

   // public List<LoanAccStmPojo> loanAccountStatementBifurficationPrinInt(String acno, String frmdt, String todt,String brnCode) throws ApplicationException;
     
    public List getLoanStmaHeader(String acNo) throws ApplicationException;
    
    public List <LoanIntCalcList> getMinMaxAvgBalance(String brcode, String acNature,String acType, String toDate, String frDate) throws ApplicationException;

    public List<StockStatementRepPojo> getStockStatementDetails(String brcode, String acnochoice,String acno, String frdt,String todt) throws  ApplicationException;
    
    public List<CCTurnOverReportPojo> getccTurnOverDetails(String brncode,String actype,String fromdate,String toDate) throws ApplicationException ;

    public List<StatementOfAlmPojo> getStatementOfAlmDetails(String  brncode,String acType,String fromDate,String toDate) throws ApplicationException;
}
