/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.TxnDetailBean;
import com.cbs.dto.other.BackDateEntryPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.SuspiciousVerifyPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface TransactionManagementFacadeRemote {

    public List getDataForF6(String orgBrnCode)throws ApplicationException;

    public List getDropdownDataOnLoad(String groupCode)throws ApplicationException;

    public List getDropdownDataOnLoadTranType(String groupCode)throws ApplicationException;

   // public List getChargesDetails()throws ApplicationException;

    public List getBy(String groupCode)throws ApplicationException;

    public List selectFromBankDays(String orgBrnCode)throws ApplicationException;

   // public List selectAcctNature(String accNo)throws ApplicationException;

    public List selectFromLoanDisbursement(String accNo)throws ApplicationException;

    public List selectFromGLTable(String accNo)throws ApplicationException;

    public List selectForDS(String accNo)throws ApplicationException;

    public List selectForFDMS(String accNo)throws ApplicationException;

    public List selectForOF(String accNo)throws ApplicationException;

    public List selectFromAcctMaster(String accNo) throws ApplicationException;

    public List selectFromAccountStatus(String accNo);

    public List selectFromLoanMisDetails(String accNo, String Tempbd);

    public List selectFromLoanAppParameter(String accNo);

    public List selectFromReconTdReconCaRecon(String accNo, String TmpAcctNature);

    public List selectPendingBalance(String accNo);

    public List selectForStopBalance(String accNo, String TmpAcctNature);

    public List addToChqDate(String chqDate);

    public List msgModuleFlag(String msgModuleName);

    //public List selectMsgFlag(String acno);

    public List selectForTDOFDRAct(String acno);

    public List selectGrdName(String accNo, String acctType);

    public List selectForOperationMode(String opmode);

    public String selectProductCode(String socAcct) throws ApplicationException;

    public List selectFromToknCrReconCash(String accNo, String Tempbd);

    public List selectPanNo(String tableName, String accNo) throws ApplicationException;

    public List selectFromBillLost(String chqno, String orgBrnCode);

    public List selectRDInstall(String accNo);

    public void checkStockStatementExpiry(String accNo, String dt)throws ApplicationException;

    public List selectFromTDRecon(String accNo, String tempBd);

    public List selectFromLoanDisb(String accNo);

    public List selectForGlobalVariables(String orgBrnCode);

    public void checkCashClose(String orgBrnCode, String tempBd)throws ApplicationException;

    public String selectFromTDParameterInfo();

    public List selectBulkReportsFromParameterInfoReport();

    public List selectForCtrlIKey(String orgBrnCode);

    public List<TxnDetailBean> accViewAuth(String acNo, String date, String brCode) throws ApplicationException;

    public List selectForCtrlS(String accNo, String orgBrnCode);

    public List selectBillType(String accNo) throws ApplicationException;

    public List selectAlphaCode(String brCode);

    public List checkCommFlag(String billType) throws ApplicationException;

    public List strBillNature(String billType);

    public List getParentACode(String billType, String orgBrnCode);

    public List getPoDetails(String billTable, String seqNo, String validateDate, String orgBrnCode) throws ApplicationException;

    public List getDataFromBillPO(String billTable, String seqNo, String validateDate, String orgBrnCode) throws ApplicationException;

    public Double getCommission(String Tempbd, String bill, int payby, double amt) throws ApplicationException;

    public List getDetailsPOCustName(String accNo,String orgBrnCode)throws ApplicationException;

    public String saveTransferDetails(String orgBrnCode, List<TxnDetailBean> cashPOTxnBeanList, String tempBd, String authBy) throws ApplicationException;

    public String updateSundryAccount(String accNo, String authBy, String seqNo, String year, String orgBrnCode, String sundryTable, String date, float recno) throws ApplicationException;

    public List checkForNPA(String tempAcctNature, String accNo);

    public String checkForAccountNo(String accNo) throws ApplicationException;

    public String getOpeningBalF4(String accNo, String currentDt) throws ApplicationException;

    public int ccodStockExp(String accNo)throws ApplicationException;

    public List checkLoanAuthorization(String accNo);

    /*                Start Of Repayment Schedule Code         */
    public List getRepaymentScheduleInfo(String accNo) throws ApplicationException;
    /*                End Of Repayment Schedule Code         */

    public List checkForSundryTableAmount(String accNo, String seqNo, String year, String sundryTable);

    public List getSumOfAmountFromIntermediateTable(String interTableName, String seqNo, String year);

    public List checkForUnpaidRdInstallment(String accNo);

    public List checkForUnpaidRdInstallmentAmt(String accNo);

    public List getDetailsOfUnAuthAccounts(String accNo) throws ApplicationException;

    public List getDropdownList(String brCode) throws ApplicationException;

    public List extensionCounterDropDown() throws ApplicationException;

    public List accountDetail(String acctNo) throws ApplicationException;

    public List getBalance(String acctNo) throws ApplicationException;

    public String saveExtTxn(List list, String dt, String enterBy, String details, String brcode)
            throws ApplicationException;

    public List<TxnDetailBean> accViewUnauth(String acNo, String date, String brCode) throws ApplicationException;

    public List getLastTransAmtForAccount(String accNo);

    public int getRemoteCashLimitFlag() throws ApplicationException;

    public List getRemoteTranLimits(String accode) throws ApplicationException;

    public BigDecimal getTotalCashTranAmtOnCurrentDt(String acno, String orgBrCode, String option) throws ApplicationException;

    public List getUnAuthPayOrderPayment(String acno) throws ApplicationException;

    public List<String> getCustIdByAccount(String accountNo) throws ApplicationException;
    
    public BigDecimal getFinYearDepositAsWellAsMonthWithdrawalTillDate(String accountNo, String finYearMinDate) throws ApplicationException;
    
    public List getCustomerDetailByAcno(String acno) throws ApplicationException;
    
    public List getNpaAcnoChechByAcno(String acno) throws ApplicationException;
    
    public List getAcnoCheckByAcno(String acno) throws ApplicationException;
    
    public Float getNpaRecNo() throws ApplicationException;
    
    public List getNpaUnauthData(String brcode, String txnDt) throws ApplicationException;
    
    public String saveNpaTransactionData(String acNo, String transType, String type, String by, String relatedTo, String date, double amt, String detail, String enterBy, String brcode, String status) throws ApplicationException;
    
    public String deleteNpaDetail(String acno) throws ApplicationException;
    
    public String UpdateNpaAuthStatus(String acno, String authby) throws ApplicationException;
    
    public List selectFidiltyDtl(String accNo)throws ApplicationException;
    
    public String loanExpCheck(String acno, String Tempbd, String acctOpenDate)throws ApplicationException;
    
    public String kycExpCheck(String acno, String Tempbd);
    
    public long getPollInterval() throws ApplicationException;
    
    public String markDemand(List<VillageWiseEMIDetailPojo> reportList, String user, String date, String orgnCode, String frDate, String toDate, String offId, String deptId) throws ApplicationException;
    
    public String recoverDemand(List<VillageWiseEMIDetailPojo> reportList, String recType, String accNo, String chqNo, String chqDt, String offId, String orgnCode, String user, String date) throws ApplicationException;
    
    public String saveDdPaymentCancelDetails(int fyear, double seqNo, String instNo, String cuName, String date, String payableAt, 
           String fvrOf, double amt, String instBrnId, String brName, String status, String drAcNo, String crAcNo, String user, String flag, String orgBrnid) throws ApplicationException;
    
    public String deleteDdPaymentCancelDetails(int fyear, double seqNo, String instNo, String user) throws ApplicationException;
    
    public String verifyDdPaymentCancelDetails(String instNo, double seqNo, int fyear, String trantype, String orgnBrnCode, String date, String user) throws ApplicationException;
    
    public List<TdReceiptIntDetailPojo> getOtherActDetails(String acNo) throws ApplicationException;
    
    public double getNpaBalCheck(String acNo, String date) throws ApplicationException;
    
    public String saveTxnDetails(List<TxnDetailBean> txnList,String sundryTable, List<DDSDenominationGrid> denominationTable, String acNature, String custId, 
            String panNo, String occupation)throws ApplicationException;
    
    public int getPostFlag(String accNo) throws ApplicationException;
    
    public int getMsgFlag(String accNo) throws ApplicationException;
    
    public List getCashTxnDetals(String acno, String orgnBrCode)throws ApplicationException;
    
    public List getDenominationDetails(String acno,float recno, String dt, String orgnBrCode, int ty)throws ApplicationException;
    
    public String updateDenominationDetails(List<DDSDenominationGrid> denoList, String acno,float recno, String dt, String orgnBrCode, int ty, String userName)throws ApplicationException;
    
    public boolean isWSAllow(String acno)throws ApplicationException;
    
    public List getAccountDetails(String accNo, String acNature) throws ApplicationException;
    
    public List getUnauthDetails(String orgBrCode)throws ApplicationException;
    
    public String processSBNCashDepostDecl(String func, String acno, String details, String userName, double amt, String orgbrCode, String authBy, long txnId) throws ApplicationException;
    
    public List<SuspiciousVerifyPojo> getStrVerifyDetails(String dt, String brnCode) throws ApplicationException;
    
    public String markSuspiciousTran(List<SuspiciousVerifyPojo> gridList, String user) throws ApplicationException;
    
    public List getAccDetailForChange(String accNo)throws ApplicationException;
    
    public String changeCustomerIdData(String accNo, String jtId, String remark, String orgBrCode, String userName) throws ApplicationException;

    public String saveBackDateEntry(List<BackDateEntryPojo> itemList, String brncode, String username, String mode,String remarks, String valueDate) throws ApplicationException;

    public List<BackDateEntryPojo> getUnauthorizedData() throws ApplicationException;

    public List<BackDateEntryPojo> getBatchList(String function, int batchno) throws ApplicationException;

    public String authorizeBackDateEntry(List<BackDateEntryPojo> itemList, String username, String Date,String brncode, float trsNo) throws ApplicationException;

    public String deleteOperationBackDateEnry(List<BackDateEntryPojo> item, String username, String Date, String brnCode,float trsNo) throws ApplicationException;

    public List selectFromGLTablecrDRFlag(String accNo) throws ApplicationException;

    public List getDocumentExpiryDate(String acno) throws ApplicationException;
    
    public long getDateWiseTrsNo(String dt)throws ApplicationException;
}