/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.dds;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DdsTable;
import com.cbs.dto.InterestProvisionTable;
import com.cbs.dto.InterestSlabMasterTable;
import com.cbs.dto.ReceiptMasterTable;
import com.cbs.dto.TdIntDetail;
import com.cbs.dto.master.MultiAcCodeTo;
import com.cbs.dto.master.MultiGLTo;
import com.cbs.dto.report.DdsTransactionGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.OtherUnclaimedDepositPojo;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface DDSManagementFacadeRemote {

    public String getCashMode(String brncode) throws ApplicationException;

    public List getAcctType() throws ApplicationException;

    public List getAgents(String brCode) throws ApplicationException;

    public String getAgentName(String agentCode, String brCode) throws ApplicationException;

    public List getAgentTransactions(String acTy, String agent, String orgnBrCode) throws ApplicationException;

//    public String updateAgentTransactions(String agentCode, String brCode, String authBy, com.cbs.dto.DenominitionTable denominitionObj) throws ApplicationException;
    
    public String updateAgentTransactions(String agentCode, String brCode, String authBy, List<DDSDenominationGrid> denominationTable, String gCashMode, String enterDt) throws ApplicationException;

    public List getPendingAgents(String orgnBrCode, Boolean mode) throws ApplicationException;

    public String saveData(String agentCode, String name, String address, String phone, String opdate,
            String status, String remarks, String brncode, String enteredBy, String password, String acno) throws ApplicationException;

    public String getMaxCode(String orgnBrCode) throws ApplicationException;

    public List<DdsTable> viewData(String brncode) throws ApplicationException;

    public String updateData(String agentCode, String name, String address, String phone, String remarks,
            String status, String modBy, String brncode, String password, String acno) throws ApplicationException;

    public List getCustomerDetails(String acno, String brCode) throws ApplicationException;

//    public List getacopendate(String acno) throws ApplicationException;
    public String getBalance(String acNo) throws ApplicationException;

    public List<Double> interestCalcultaion(String acno, Date to, Date from, double panelty) throws ApplicationException;

    // public int datediff(Calendar from, Calendar to, int f) throws ApplicationException;
    //public String postInterest(String acno, String custname, String user, double amt) throws ApplicationException;
    public String postInterest(String acno, String custname, String user, double amt, double tTds, double cTds, String brnCode, String fromDt, String toDt) throws ApplicationException;

    public List getAgentsForAuth(String brCode) throws ApplicationException;

    public List getPendingAgentsForAuth(String brCode) throws ApplicationException;

    public List getAgentTransactionsBy(String agentCode, String brCode, String ordBy) throws ApplicationException;

    public String authorizeAgentTransactions(String agentName, double amount, String agentCode, String brCode, String authBy, String date) throws ApplicationException;

    public boolean checkData(float period, Date AppDate) throws ApplicationException;

//    public String insertData(Date date, double intrt, String fromprd, String toprd, String enterdby, Date update) throws ApplicationException;
    public String insertData(Date date, double intrt, String fromprd, String toprd, String enterdby, String frAmt, String toAmt, String comm, String sChg, String agTax, String agSDep, String dBal) throws ApplicationException;

    public List<InterestSlabMasterTable> datalist() throws ApplicationException;

    public String insertSeries(float receiptFrom, float receiptTo) throws ApplicationException;

    public String issueSeries(String agCode, String issueBy, float receiptFrom, float receiptTo) throws ApplicationException;

    public List<ReceiptMasterTable> getAvailableReceipts() throws ApplicationException;

    public String deleteSeries(float receiptFrom, float receiptTo) throws ApplicationException;

    public List getBranchList() throws ApplicationException;

    public List getAgentList(String brCode) throws ApplicationException;

    public List<ReceiptMasterTable> getAllocatedReceipts() throws ApplicationException;

    public String revertSeries(float receiptFrom, float receiptTo) throws ApplicationException;

    public String saveDdsTransactions(String acNo, String dt, double cramt, String receiptNo, String enterBy, float recno, String tokenPaid) throws ApplicationException;

    public List getagentCodeNameAndAccountNo(String acType, String agCode, String brnCode) throws ApplicationException;

    public List getCustNamebasedOnAcNo(String acno) throws ApplicationException;

    public String getReceiptNo(String agentCode, String orgBrCode) throws ApplicationException;

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException;

    public String post(List<InterestProvisionTable> list, String type, Date fromDate, Date toDate, String brncode, String userName) throws ApplicationException;

    public List<InterestProvisionTable> calculate(Date fromDate, Date toDate, String type, String brncode) throws ApplicationException;

    public String deleteAllDdsEntries(String agCode, String tokenPaid, int tokenNo, String dt, float crAmt, String brCode) throws ApplicationException;

    public String deleteDDSEntries(String acno, float recno, String receiptno, String tokenPaid, float newAmount, int tokenNo, float cramt, String dt) throws ApplicationException;

    public List getUnauthorizedDDSEntries(String agCode, String brnCode) throws ApplicationException;

    public List getCodeFromParameterInfoReportTable() throws ApplicationException;

    public List getAgCode(String brncode) throws ApplicationException;

    public List getActiveAgentName(String agentCode, String brnCode) throws ApplicationException;

    public String getAccountCode(String acNature) throws ApplicationException;

    public String pushPcrxData(String acNo, String dt, double cramt, String receiptNo, String enterBy, float recno, String tokenPaid) throws ApplicationException;

    public List<String> generateDDSOutwardFile(String accode, String branchCode, String agentCode, String days, 
            String bankCode, String password) throws ApplicationException;
    
    public List<String> pratiNidhiNewDDSOutwardFile(String accode, String branchCode, String agentCode, String days, 
            String bankCode, String password) throws ApplicationException;

    public List<DdsTransactionGrid> getAgentUnAuthorizedTransaction(String orgBrCode, String agentCode, String acType, String agentName) throws ApplicationException;

    public String deleteDDSAccountTransaction(String acno, String date, String receiptNo) throws ApplicationException;

    public String updateDDSAccountTransaction(String acno, String date, String receiptNo, double amount) throws ApplicationException;

    public double getProduct(String fromDt, String toDt, String acno, int code) throws ApplicationException;

    public String claimDeafAccount(String deafAcno, String userName, String curDt,
            String orgnBrCFode, double deafAmount, String deafDt, double vchNo, double savingRoi, String fyear, String instCode) throws ApplicationException;

    public List getDeafAccountDetail(String acno, String year, String seqNo) throws ApplicationException;

    public List getOtherUnclaimedAccountDetail(String orginBrCode, String acctNature, String acCode, String glAcno, String finalDeafDt, String todayDt) throws ApplicationException;

    public String otherMarkUnClaimed(String reportBranchCode, String acctNature, String acCode, List<OtherUnclaimedDepositPojo> unClaimedList,
            String finalDeafEffDt, String intCalcDt, String today, String userName) throws ApplicationException;

    public List<String> generateMultiGlDDSOwFile(List<MultiAcCodeTo> glList, String fileType, String accode, String agentCode,
            String agentName, String days, Integer password, String orgnBrCode, String todayDt,
            String generatedBy) throws ApplicationException;

    public List getMultiGlDdsAcCode() throws ApplicationException;

    public String pushMultiGLData(List<MultiGLTo> dataList, String dt, String enterBy,
            String tokenPaid, String orgnBrCode) throws ApplicationException;

    public List<TdIntDetail> intCalcForTds(String fromDate, String toDate, String type, String brncode) throws ApplicationException;

    public String singleAcnoDeafAmtPost(String function, String txnId, String acno, String deafAmt, String voucherNo, String effectdate, String userName) throws ApplicationException;

    public String getLastTxnDt(String acno, String voucherNo) throws ApplicationException;

    public String getAccStatus(String acno) throws ApplicationException;

    public List getGlAccounNature() throws ApplicationException;

    public List getGlCodeByGlNature(String nature) throws ApplicationException;

    public String getGlAcnoByGlCodeBGlNature(String nature, String code) throws ApplicationException;

    public List getSingleAcnoData(String brCode, String frDt, String toDt) throws ApplicationException;

    public String badPersonDataPost(String buttonVal, String sections, String personId, String title, String name, String dob, String pob, String designation, String address, String goodQuality, String lowQuality, String nationality, String passportNo, String nationalIdNo, String listedNo, String otherInfo, String enterBy, String enterDate, String updateBy, String updateDate) throws ApplicationException;

    public List getBadPersonData(String section, String id) throws ApplicationException;

    public List getBadPersonSecWiseData(String section) throws ApplicationException;

    public String getnatioality(String nationalCode) throws ApplicationException;

    public String getAgentPassword(String agentCode, String brnCode) throws ApplicationException;

    public List<String> pratiNidhiNewDDSOutwardFileForMode2(String accode, String branchCode, String agentCode, String days, String bankCode, String password) throws ApplicationException;
}