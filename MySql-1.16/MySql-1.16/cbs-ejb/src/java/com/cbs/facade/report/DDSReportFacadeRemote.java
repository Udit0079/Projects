/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.AgentCommissionPojo;
import com.cbs.dto.report.DDSIntReportPojo;
import com.cbs.dto.report.DdsAccountExpiryPojo;
import com.cbs.dto.report.DdsTransactionGrid;
import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.dto.report.TdPeriodMaturityPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.dto.report.ho.DepositProvisionCalPojo;
import com.cbs.dto.report.ho.CashInAtmPojo;
import com.cbs.dto.report.ho.DepositDrLoanCrBalPojo;
import com.cbs.dto.report.ho.ReportProfilePojo;
import com.cbs.dto.report.ho.UserHistoryDto;
import javax.ejb.Remote;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AadharDetailSummaryPojo;
import com.cbs.pojo.FolioStatement;
import com.cbs.pojo.GuaranteeOnLoanPojo;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.pojo.PostDatedChequePojo;
import com.cbs.pojo.VoucherPrintingPojo;
import java.util.List;

/**
 *
 * @author sipl
 */
@Remote
public interface DDSReportFacadeRemote {

    public List getAccountType() throws ApplicationException;

    public List getAgentCode(String orgBrCode) throws ApplicationException;

    public List<DdsTransactionGrid> getDailyCollData(String userDate, String orgBrnCode, String agCode, String acType, String Ord) throws ApplicationException;

    public String getAgentName(String agCode, String orgBrCode) throws ApplicationException;

    public List<AgentCommissionPojo> getAgentCommission(String frmdt, String todt, String branchCode) throws ApplicationException;

    public List<DdsAccountExpiryPojo> getDdsAccountExpiryDate(String branchCode, String agCode, String tillDate) throws ApplicationException;

    public List getAgentCodeHo() throws ApplicationException;

    public List getAcodeRdds() throws ApplicationException;

    public List<DDSIntReportPojo> getDDSIntData(String acType, String frmdt, String todt, String branchCode) throws ApplicationException;

    public List getLoanAndDepositNatures() throws ApplicationException;

    public List getAccountCodeByNature(String nature) throws ApplicationException;

    public List<ReportProfilePojo> getSummaryReportProfile(String brCode, String classification, String actNature, String acctcode,
            String reportBasedOn, double from, double to, double noOfSlab, String asOnDt, String reportBase) throws ApplicationException;

    public List<ReportProfilePojo> getDetailReportProfile(String brCode, String classification, String actNature, String acctcode,
            String reportBasedOn, double from, double to, double noOfSlab, String asOnDt, String reportBase) throws ApplicationException;

    public List getDepositNatures() throws ApplicationException;

    public List<DepositProvisionCalPojo> getSummaryProvisionCalculation(String brCode, String acNature,
            String acType, String asOnDt) throws ApplicationException;

    public List<DepositProvisionCalPojo> getDetailProvisionCalculation(String brCode, String acNature,
            String acType, String asOnDt) throws ApplicationException;

    public List<CashInAtmPojo> getGlHeadData(String atmBrcode, String glhead, String frDt, String toDt) throws ApplicationException;

    public List<DepositDrLoanCrBalPojo> getDepositDrLoanCrData(String brCode, String acNature, String acType, String asOnDt) throws ApplicationException;

    public List getAccountNatureClassification(String classification) throws ApplicationException;

    public List getAccountNatureClassificationWithGl(String classification) throws ApplicationException;

    public List getAccountCodeByClassificationAndNature(String classification, String nature) throws ApplicationException;

    public List<UserHistoryDto> printUserHistory(String brnCode, String frDt, String toDt,
            String enterBy) throws ApplicationException;

    public List getAcctCodeByNature(String nature) throws ApplicationException;

    public List getAcctCodeByNatureFlag(String nature, String flag) throws ApplicationException;

    public String getDeafEfectiveDate() throws ApplicationException;

    public List<UnclaimedAccountStatementPojo> unClaimedMarking(String brnCode, String acNature, String acType,
            String finalDeafDt, String intTillDate, String curDt,double savingRoi) throws ApplicationException;

    public List<UnclaimedAccountStatementPojo> unClaimedMarkingSingle(String brnCode, String acNo,
            String finalDeafDt, String intTillDate, String curDt,double savingRoi) throws ApplicationException;

    public String insertDeafTransaction(String reportBranchCode, String acNature, List<UnclaimedAccountStatementPojo> unClaimedList,
            String tillIntDt, String userName, String flag, String acType, String savingRoi, String finalDeafDt, String curDt) throws ApplicationException;

    public List<TdActiveReportPojo> principalWiseFdDetailData(String brnCode, String frDt, String toDt, String frAmt, String toAmt, String repType,String dateType,String actCategory) throws ApplicationException;
    
    public List<GuaranteeOnLoanPojo> getGauranteeOnLoanData(String brCode,String tillDate,String reportType,String acno) throws ApplicationException;
    
    public List<FolioStatement> getFolioStatementData(String folio,String frDt,String toDt) throws ApplicationException;
    
    public List<VoucherPrintingPojo> getVoucherPrintingData(String branch,String txnMode,String date,String voucherNo) throws ApplicationException;
    
    public List<ChallanDeducteePojo> getChallanDeducteeData(String reportType,String frDt,String otDt) throws ApplicationException;
    
    public List<PostDatedChequePojo> getPostDatedChequieData(String brncode, String frDt,String toDt,String reportType) throws ApplicationException;
    
    public List<OverDueListPojo> getOverDueListData(String branch,String acType,String toDt,String acNo) throws ApplicationException;
          
    public List<TdPeriodMaturityPojo> getCustWiseRecieptDetail(String custId,String toDt) throws ApplicationException;
    
    public String getCustNameByCustId(String custId) throws ApplicationException;
    
    public List<AadharDetailSummaryPojo> getAadharDetailSummaryData(String branch,String frDt,String toDt,String reportOpion) throws ApplicationException;

    public String postAgentCommisionTransaction(List<AgentCommissionPojo> itemList, String txndate, String user, String orgnBrCode,String frDt,String toDt,String branchCode) throws ApplicationException;

    public String getAllBranchCodefromddsAgentListforCheckPostHistory(String frDt, String toDt) throws ApplicationException;
}
