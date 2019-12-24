package com.cbs.facade.master;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.BucketParameterGrid;
import com.cbs.pojo.GstReportPojo;
import com.cbs.pojo.MinorToMajorPojo;
import com.cbs.pojo.NpciInwardNonaadhaarPojo;
import com.cbs.pojo.OnlinePigmeInfoPojo;
import com.cbs.pojo.ThresoldTransactionInfoPojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface OtherMasterFacadeRemote {

    //AlmAccountsClassificationBean Methods
    List headOfAccountsOutFlow() throws ApplicationException;

    List headOfAccountsInFlow() throws ApplicationException;

    List bucketDescCombo() throws ApplicationException;

    String headAcDescForIF(String code) throws ApplicationException;

    String headAcDescForOF(String code) throws ApplicationException;

    String bucketDescriptionGrid(String bucketNo) throws ApplicationException;

    String saveALMActClassification(List list, String enterBy, String dt) throws ApplicationException;

    List almActClassGridLoad() throws ApplicationException;

    String bucketDescriptionFromGrid(String bucketdesc) throws ApplicationException;

    String hoDescriptionFromGrid(String flow, String description) throws ApplicationException;

    String updateALMActClassification(String modBy, String modDt, String flowOld, String hoActOld, String bucketNoOld, Float amtOld, String remarksOld, String flowNew, String hoActNew, String bucketNoNew, Float amtNew, String remarksNew) throws ApplicationException;

    //BucketParameterBean Methods
    List bucketGridLoad() throws ApplicationException;

    String saveBucketParameter(List<BucketParameterGrid> list, String dt, String enterBy) throws ApplicationException;

    String updateBucketParameter(String modDt, String updateBy, Integer oldBucketNo, Integer bucketNo, String bucketDesc, Integer startDay, Integer endDay, String parameter) throws ApplicationException;

    //EntryFrTxMastBean Methods
    public List loadTable() throws ApplicationException;

    public List loadValue() throws ApplicationException;

    public List taxNature(String acNo) throws ApplicationException;

    public List loadApply(String st1) throws ApplicationException;

    public String update(String apf) throws ApplicationException;

    public List save(String labeltax) throws ApplicationException;

    public List save2() throws ApplicationException;

    public List save3(String cboAppOn) throws ApplicationException;

    public String update2(String labelTax, String taxType, String date, double rot, String rotAppOn, String glHead, double minTax, double maxTax, String sudhir, String system, String nature, String listItem, String auth) throws ApplicationException;

    //ReceiptIssueByStockBean Methods
    List getData() throws ApplicationException;

    List allReceiptTableData(String scheme, String brCode) throws ApplicationException;

    List backTableData(String scheme, String brCode) throws ApplicationException;

    String deleteData(float seqNo, String brCode) throws ApplicationException;

    String saveData(List issueGrid, String scheme, String txtBookNo, String txtRFrom, String txtRTo, String showTableData, String tmpFlag, String txtNo, String txtSeries, String issueDt, String user, Float seqNumber, String brCode) throws ApplicationException;

    List dateDiffStatementFreqDate(String statementFreqDate) throws ApplicationException;

    //StockStatementBean Methods
    List actTypeCombo() throws ApplicationException;

    List addMonth() throws ApplicationException;

    List acDetail(String acno) throws ApplicationException;

    List gridLoad(String acno) throws ApplicationException;

    List chkGrid(String nDt) throws ApplicationException;

    List getNetProposedDp(String acno) throws ApplicationException;

    String saveStockStatement(String acno, String enterBy, List list, String gracePeriod, String stockStatDueDt, String stockStatSubDt, String acStatSubDt, String brCode) throws ApplicationException;

    String setDpLimit(String ACNO, Double lblNewDP) throws ApplicationException;

    public java.lang.String getCode(java.lang.String scheme) throws com.cbs.exception.ApplicationException;

    public List loadTdPeriod() throws ApplicationException;

    public String saveTdPeriodData(String fromDays, String toDays, String enterBy) throws ApplicationException;
    
    public List<MinorToMajorPojo> getMinorToMajorData(String BranchCode, String frDt, String toDt) throws ApplicationException;

    public List<GstReportPojo> getGstrData(String repType, String optionType, String frDt, String toDt, String brnCode,String acNo,String gstNo) throws ApplicationException;

    public List<ThresoldTransactionInfoPojo> getOlineAadharRegistrationData(String frDt, String toDt, String brnCode,String repOption) throws ApplicationException;

    public List<OnlinePigmeInfoPojo> getOlinePigmeInfoData(String frDt, String toDt, String status, String brnCode,String agentCode) throws ApplicationException;

    public List<NpciInwardNonaadhaarPojo> getNpciInwardNonAadharDetails(String frdt, String todt, String status) throws ApplicationException;
    
    public List getBranchInfoByBrCode(String branch) throws ApplicationException;
    
    public List getCustomerInfoByCustNo(String customerNo) throws ApplicationException;
    
    public String savedGenerateInvoiceData(String acNo,String finYear,String branch,String userName,String option,String frDt,String toDt) throws ApplicationException;
    
    public List isVoucherGen(String branch,String frDt,String toDt) throws ApplicationException;

    public List getddAgent(String brnCode) throws ApplicationException;
    
    public String getCodeCbsparameterInfo(String name) throws ApplicationException;
}
