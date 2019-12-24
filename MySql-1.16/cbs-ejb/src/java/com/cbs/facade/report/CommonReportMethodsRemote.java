package com.cbs.facade.report;

import com.cbs.dto.report.AbbStatementPojo;
import com.cbs.dto.report.LoanDemandRecoveryPojo;
import com.cbs.dto.report.MiniStatementPojo;
import com.cbs.dto.report.PostingReportPojo;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.dto.report.ho.BranchAdjPojo;
import com.cbs.dto.report.ho.HoReconPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.Remote;

@Remote
public interface CommonReportMethodsRemote {

    public List getBranchNameandAddress(String orgnbrcode) throws ApplicationException;

    public List getCode(String reportName) throws ApplicationException;

    public long dayDifference(String dt1, String dt2) throws ApplicationException;

    public int getTotalWorkingDays(int quarter, int year, String brCode) throws ApplicationException;

    public String getTableName(String acctNature) throws ApplicationException;

    public List getDoubleAndIntegerElementsFromList(List vecList) throws ApplicationException;

    public String getMonthName(int month) throws ApplicationException;

    public List getGlNames(String acno) throws ApplicationException;

    public Double getDoubleValueFromVector(Vector ele) throws ApplicationException;

    public float getLoanRoi(double amt, String date, String acno) throws ApplicationException;

    public float getCbsRoi(double amt, String intTableCode, String date) throws ApplicationException;

    public String getAcTypeByAcNo(String acNo) throws ApplicationException;

    public Double getBalanceOnDate(String acNo, String date) throws ApplicationException;

    public String getAcNatureByAcNo(String acNo) throws ApplicationException;

    public List<AbbStatementPojo> getAbbStatement(String acNo, String fromDate, String toDate, String option) throws ApplicationException;

    public List getAllAcTypeList() throws ApplicationException;

    public List getAcNatureByAcNat(String acNat) throws ApplicationException;

    public String getAcNatureByAcType(String acType) throws ApplicationException;

    public List getCAAcTypeList() throws ApplicationException;

    public List getCASBAcTypeList() throws ApplicationException;

    public List getCASBAcNatureList() throws ApplicationException;

    public List getCibilCompanyList() throws ApplicationException;

    public String getCibilVerNo() throws ApplicationException;

    public String getBranchNameandAddressString(String orgnbrcode) throws ApplicationException;

    public List getSBRDAcTypeList() throws ApplicationException;

    public String currentDateByBrnCode(java.lang.String BRCODE) throws ApplicationException;

    public List getOperatingModeDetails() throws ApplicationException;

    public List getOcupationDetails() throws ApplicationException;

    public List getActCategoryDetails() throws ApplicationException;

    public List getDocumentDetails() throws ApplicationException;

    public List getAcctTypebyDLNature() throws ApplicationException;

    public List getDSRDAcTypeList() throws ApplicationException;

    public List getFDMSAcTypeList() throws ApplicationException;

    public List getAcctTypeForDs() throws ApplicationException;

    public List getAccTypeExcludePO() throws ApplicationException;

    public List<MiniStatementPojo> getminiStatement(String acNo, String option) throws ApplicationException;

    public List getAllAcctcode() throws ApplicationException;

    public String getAcctNature(String acctCode) throws ApplicationException;

    public String getAcctDecription(String acctCode) throws ApplicationException;

    public List getAgentCode(String brCode) throws ApplicationException;

    public String getAgentName(String agCode, String brCode) throws ApplicationException;

    public String getBranchNameByBrncode(String brcode) throws ApplicationException;

    public String getTransactionType(String tranCode) throws ApplicationException;

    public List<PostingReportPojo> getPostingReport(float trsno, java.lang.String acType, java.lang.String enterBy, java.lang.String enteryDt, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public List getAlphacodeExcludingHo() throws ApplicationException;

    public String getAlphacodeByBrncode(String brncode) throws ApplicationException;

    public String getBrnRefNoByBrncode(String brncode) throws ApplicationException;

    public String getBrncodeByAlphacode(String alphacode) throws ApplicationException;

    public String getAcNameByAcno(String acno) throws ApplicationException;

    public Double getBalanceOnDateWise(String acNo, String date) throws ApplicationException;

    public List<AbbStatementPojo> getAbbStatementWies(String acNo, String fromDate, String toDate, String option) throws ApplicationException;

    public java.util.List dividendParameterCode() throws com.cbs.exception.ApplicationException;

    public String getBrncodeByAcno(String acno) throws ApplicationException;

    public List getAccTypeIncludeSBRDFD() throws ApplicationException;

    public List getAcNatureIncludeRdFdMsDs() throws ApplicationException;

    public List getAccType(String acNature) throws ApplicationException;

    public List getAccTypeOnlyCrDbFlag(String acNature) throws ApplicationException;

    public String getRefRecDesc(String refRecNo, String refCode) throws ApplicationException;

    public List getAccountManager() throws ApplicationException;

    public List getGroupId(String type) throws ApplicationException;

    public List getALLGroupId() throws ApplicationException;

    public List getAccTypeIncludeRDFDMS() throws ApplicationException;

    public List getUserId(String brncode, String dt, String ty) throws ApplicationException;

    public List getTokenPaidBy(String brncode, String dt, String ty) throws ApplicationException;

    public List getReceiptNoByAcNo(String acNo) throws ApplicationException;

    public String getAccStatusdesc(String accStatusCode) throws ApplicationException;

    public List getAcctTypeNpaList() throws ApplicationException;

    public List getRefRecList(String refRecNo) throws ApplicationException;

    public List getBranchCodeList(String orgnCode) throws ApplicationException;

    public List<PostingReportPojo> sbIntPostingReport(String acType, String status, String postingDt, String brCode) throws ApplicationException;

    public String getBillTable(String bNature);

    public List getAcctfdRdMsNatList() throws ApplicationException;

    public List getBucketList(String almBase) throws ApplicationException;

    public Integer getCodeByReportName(String reportName) throws ApplicationException;

    public List getAllNatureList() throws ApplicationException;

    public List getTranDtList(String finFrDt, String finToDt) throws ApplicationException;

    public List getTrsNoList(String trsnDt) throws ApplicationException;

    public List getAlphacodeBasedOnBranch(String orgnBrCode) throws ApplicationException;

    public List getAlphacodeIncludingHo() throws ApplicationException;

    public List getAcNatureListExcludingTl() throws ApplicationException;

    public List getAtmGlhead(String atmBranch) throws ApplicationException;

    public List getAcNaturetldlcaList() throws ApplicationException;

    public List getAccTypeCcod(String acNature) throws ApplicationException;

    public String getNoticeToBorrowerCode() throws ApplicationException;

    public List getAlphacodeAllAndBranch(String orgnBrCode) throws ApplicationException;

    public String getSuspenceBalance(String acNo, String fYear, String segNo) throws ApplicationException;

    public String getPoddType(String glAcno) throws ApplicationException;

    public List getUnclaimedDate(String fYear, String tYear, String asOnDt) throws ApplicationException;

    public List getAtmId(String atmBranch) throws ApplicationException;

    public List getAtmBranchList(String orgnCode) throws ApplicationException;

    public List getacNatureTLDL() throws ApplicationException;

    public List getAccountNature() throws ApplicationException;

    public String getAtmhead(String atmId) throws ApplicationException;

    public String getNfsAtmHead() throws ApplicationException;

    public List getCustIdCheck(String aadharNo) throws ApplicationException;

    public List getCustId(String aadharNo) throws ApplicationException;

    public List getCustIdData(String functionType, String custId, String aadharNo) throws ApplicationException;

    public String getAAdharAcno(String custId) throws ApplicationException;

    public Double getcrrPercent(String dt) throws ApplicationException;

    public List getAcctTypeScheme() throws ApplicationException;

    public List getAcctNatureOnlyDB() throws ApplicationException;

    public List getAcctTypeAsAcNatureOnlyDB(String acNature) throws ApplicationException;

    public List getAcTypeDescByClassificationAndNature(String classification, String nature) throws ApplicationException;

    public String getAccseq() throws ApplicationException;

    public List getAcctCodeDetails(String acctcode) throws ApplicationException;

    public String getTdsOfficeAdd() throws ApplicationException;

    public String getTheftAcno(String custId) throws ApplicationException;

    public String getArea(String folioNo) throws ApplicationException;

    public List getSundryCrAcno() throws ApplicationException;

    public List getOccupationByfolioNo(String folioNo) throws ApplicationException;

    public String getNomDobByfolioNo(String folioNo) throws ApplicationException;

    public String getOfficeDecByType(String Type) throws ApplicationException;

    public List getIntroducerAcNo(String folioNo) throws ApplicationException;

    public String getDeptDescByGroupId(String groupId) throws ApplicationException;

    public String getEmiReportName() throws ApplicationException;

    public double getshareMoneyBal(String folioNo, String dt) throws ApplicationException;

    public double gettotalLoanBal(String folioNo, String dt) throws ApplicationException;

    public String getOrderByActCode(String actCode) throws ApplicationException;

    public double getDisbursementAmt(String acNo, String dt) throws ApplicationException;

    public List getUnRecoveredDemand(String type, String brnCode) throws ApplicationException;

    public List<VillageWiseEMIDetailPojo> getDemandDetails(String demNo) throws ApplicationException;

    public List getPhoneNo(String brCode) throws ApplicationException;

    public List getOfficeDeptHead(String officeId, String deptId) throws ApplicationException;

    public String dmdAmtFlag() throws ApplicationException;

    public List getGroupIdByTypeAndCustId(String custId, String type) throws ApplicationException;

    public List<LoanDemandRecoveryPojo> loanDmdRecovery(String acNature, String acCode, String fromDt, String toDt, String orgnBrId) throws ApplicationException;

    public List<BranchAdjPojo> getBranchAdjustment(String orgnId, String asonDate, String reptop) throws ApplicationException;

//    public List<MmsReportPojo> getMmsDetails(String reportType, String manDateType, String uploadDate, String zipFileName) throws ApplicationException;
//    
//    public List<MmsReportPojo> getZipFileNameList(String manDateType, String uploadDate) throws ApplicationException;
    public List getFidiltyTypeList() throws ApplicationException;

    public List getLipAcctCodeList(String purpCode) throws ApplicationException;

    public List getAreaWiseDmdsrno(String area, String frDt, String toDt) throws ApplicationException;

    public String getIntTableName(String acNature) throws ApplicationException;

    public List getDocDetail(String custId, String fYear, String tableName) throws ApplicationException;

    public Double getBalanceInBetweenDate(String acNo, String frDt, String toDt) throws ApplicationException;

    public String getBankName() throws ApplicationException;

    public List getAllAcNature() throws ApplicationException;

    public List getActCodeByAcNature(String acNature) throws ApplicationException;

    public List getAcnoByFolio(String folio) throws ApplicationException;

    public List getAccountStatus() throws ApplicationException;

    public List getBatchVoucherNo(String txnMode, String orgBrCode, String dt) throws ApplicationException;

    public List getBnkNameAdd(String alphaCode) throws ApplicationException;

    public List getGOIParameter() throws ApplicationException;

    public String getCodeBookDescription(int groupcode, int code) throws ApplicationException;

    public List getTdsDoctype(String refRecNo) throws ApplicationException;

    public String validateAccountType(String accNature, String accCode) throws ApplicationException;

    public List getAlphacodeBasedOnOrgnBranch(String orgnBrCode) throws ApplicationException;

    public String getBranchCode(String empCode) throws ApplicationException;

    public String getcbsFormatAcno(String noncbsAcno, String micr, String accType) throws ApplicationException;

    public Map getConvertedAccTypeByMapping() throws ApplicationException;

    public List<HoReconPojo> getHeadOfficeDetails(String orgnId, String asonDate, String reptop) throws ApplicationException;

    public int getMoratoriumpd(String acNo) throws ApplicationException;

    public int isExceessEmi(String dt) throws ApplicationException;

    public String getCodeFromCbsParameterInfo(String reportName) throws ApplicationException;

    public List getAcopMode(String acno) throws ApplicationException;

    public String selectForOperationMode(String opMode) throws ApplicationException;

    public List getBankDetails(String iinNo) throws ApplicationException;

    public List getAcctTypeAsAcNatureOnlyDB1(String acNature) throws ApplicationException;

    public List getActypeOnlyForDLProbableNPA() throws ApplicationException;

    public List getAcctTypeSchemeWise(String acType) throws ApplicationException;

    public String getSchemeDescAcTypeAndSchemeWise(String acType, String schemeCode) throws ApplicationException;

    public List getChargeCode(String chargeType) throws ApplicationException;

    public List getChargeName(String code) throws ApplicationException;

    public List getRefCardStatuslist(String refrecNo, String cardStatus) throws ApplicationException;

    public List getfrequencydescription(String refrecNo, String code) throws ApplicationException;

    public List checkCustId(String Custid) throws ApplicationException;

    public List getMonthEndList(String date) throws ApplicationException;

    public List getCustIdfromacNo(String acNo) throws ApplicationException;

    public List getAcctypeNatureconstant() throws ApplicationException;

    public List getGlHeadName(String acno) throws ApplicationException;

    public Double getBalanceOnDateWithoutInt(String acNo, String date) throws ApplicationException;

    public List getCaAdvanceAccount(String acctNature, String acType) throws ApplicationException;

    public List getBranchInfoByAlphaCode(String alphaCode) throws ApplicationException;

    public List getAmountLimitList() throws ApplicationException;

    public boolean isExCounterExit(String brCode) throws ApplicationException;

    public List getAtmChgDuration() throws ApplicationException;

    public String getStateCodeFromBranchMaster(String brnCode) throws ApplicationException;

    public String getRefRecCode(String refRecNo, String refdesc) throws ApplicationException;
    //for ibs Mobile Api(user in   Mobile Intra Xfer Trf posting)     

    public String getBusinessDate() throws ApplicationException;

    public List getUsernameandUserId(String brncode) throws ApplicationException;

    public String getIntAppFreq(String acNo) throws ApplicationException;

    public String getAccountOpeningDate(String AccountNo) throws Exception;

    public String getRecoverType(String schemeCode) throws Exception;

    public List getAllUsernameandUserId() throws Exception;
    
    public List getNatureSecurityValueByAcno(String acNo,String asOnDt) throws Exception;
    
    public List getCustEntityType(String acno) throws ApplicationException;
    
    public String getUserNameByUserId(String userId) throws Exception;
    
}
