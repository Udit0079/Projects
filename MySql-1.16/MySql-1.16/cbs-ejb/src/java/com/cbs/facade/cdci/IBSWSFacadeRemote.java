package com.cbs.facade.cdci;

import com.cbs.dto.cdci.AccountMasterPojo;
import com.cbs.dto.cdci.AccountStatementPojo;
import com.cbs.dto.cdci.AccountTypeMasterPojo;
import com.cbs.dto.cdci.CommissionPojo;
import com.cbs.dto.cdci.CustomerDetailsPojo;
import com.cbs.dto.cdci.CustomerIdPojo;
import com.cbs.dto.cdci.CustomerMasterDetailsPojo;
import com.cbs.dto.cdci.DocumentDetailsPojo;
import com.cbs.dto.cdci.MiniStatementPojo;
import com.cbs.dto.cdci.NomineeDetailsPojo;
import com.cbs.dto.cdci.RdAcountDetailPojo;
import com.cbs.dto.cdci.RequestStatusObj;
import com.cbs.dto.cdci.TransactionsPojo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface IBSWSFacadeRemote {

    public String getHOAddress() throws ApplicationException;

    public List getBranchNameandAddress(String orgnbrcode) throws ApplicationException;

    public List<AccountTypeMasterPojo> getAccountTypeMasterPojoList(String acType) throws ApplicationException;

    public List<AccountStatementPojo> getAccountStatement(String acNo, String fromDt, String toDt) throws ApplicationException;

    public List getCurrentTDSlab(String acctNature) throws ApplicationException;

    public String tdInterestCalculation(String InterestOption, float RoInt, String FDDate, String MatDate, double amt, String prd) throws ApplicationException;

    public float getTDRoi(String custCat, Float tOTAMT, String matDt, String wefDt, String presentDt, String acNat) throws ApplicationException;

    public float getRDRoi(double amount, long days) throws ApplicationException;

    public double rdInterestCalculation(float instAmt, int periodInMonth, float rdRoi) throws ApplicationException;

    public List createNewFixedDeposit(String userId, String CustId, String drAc, String orgBrn, float amount, float roi, String interestRateType, String maturityDate, String period, int year, int month, int days);

    public String accountOpenAll(String acttype, String acno, int rdperiod, float rdinstall, float rdroi, String custid, String brnCode, String userid);

    public BigDecimal getBalanceByAccountNumber(String acNo, String asOnDt) throws ApplicationException;

    public boolean isAccountOperative(String acNo) throws ApplicationException;

    public boolean updateEmailIdMobileNumberByCusromerId(String custId, String eMailId, String mobileNumber) throws ApplicationException;

    public List<CustomerIdPojo> customerAccountNumbersByCustomerId(String custId) throws ApplicationException;

    public CustomerDetailsPojo customerInformationByCustomerId(String customerId) throws ApplicationException;

    public List<CustomerDetailsPojo> getAccountNoVerficationDetail(String acNo) throws ApplicationException;

    public AccountMasterPojo getcustomerInformationByAccountNo(String acNo) throws ApplicationException;

    public NomineeDetailsPojo getNomineeDetailsByAccountNo(String acNo) throws ApplicationException;

    public DocumentDetailsPojo getDocumentDetailsByAccountNo(String acNo) throws ApplicationException;

    public CustomerMasterDetailsPojo getCustomerDetailsByCustNo(String custNo, String brnCode) throws ApplicationException;

    public List<RdAcountDetailPojo> getRdAccountListbyCustomerid(String custid) throws ApplicationException;

    public List<RdAcountDetailPojo> getRdAccountsDetailsInstallment(String acno, String status) throws ApplicationException;

    public String transferAmount(String drAc, String crAc, double amount) throws ApplicationException;

    public String ddTransfer(String drAc, String destination, String favour, double amount, double commision, double tax, String billType) throws ApplicationException;

    public List getChequeRecord(String acNature, String acNo, Float chqNo) throws ApplicationException;

//    public String[] getAccountTypeBranchCodeByAccountNumber(String accountNumber) throws ApplicationException;
    //public List findServiceTax1() throws ApplicationException;
    //public List fnTaxApplicableROT() throws ApplicationException;
    public String taxAmountProcedure(double amt, String type, int rUpto) throws ApplicationException;

    public List<CommissionPojo> getCommission(String bill, int payby) throws ApplicationException;

    public String getAccountNatureByAccountType(String acctType) throws ApplicationException;

    public String saveStopPaymentRequest(String drAc, String chqStart, String chqEnd, String option) throws ApplicationException;

    public List<TransactionsPojo> unclearedTransactionsList(String accountNo) throws ApplicationException;

    public List<MiniStatementPojo> getMiniStatement(String acNo) throws ApplicationException;

    public List<CustomerIdPojo> getAllActiveAccountByCustomerId(String custId) throws ApplicationException;

    public CustomerIdPojo getDetailOfAccountNumber(String acno) throws ApplicationException;

    public String processChqBookRequest(String acno, BigInteger requestNo, String requestType, Integer noOfBooks,
            String noOfLeaves, String ibRequestDt, String deliveryAddress, String deliveryMode,
            String deliveryStatus) throws ApplicationException;

    public String processStopPaymentRequest(String acno, BigInteger requestNo, String requestType,
            String ibRequestDt, long chqStart, long chqEnd, String option) throws ApplicationException;

    public String getBranchBusinessDt(String brCode) throws ApplicationException;

    public List<RequestStatusObj> getUsersRequestStatus(List<Long> reqNoList) throws ApplicationException;

    public String updateRequestStatus(Long requestNo, String status) throws ApplicationException;

    public CustomerDetailsPojo verifyRegisteringUser(String idType, String idNo) throws ApplicationException;

    public List<CustomerIdPojo> customerTransactionalAccountNumbersByCustomerId(String custId) throws ApplicationException;

    public String ibsIntraTrfTxnPosting(String debitAccountNo, String creditAccounNo, BigDecimal amount) throws ApplicationException;

    public String processImpsTxn(Long id, String username, String debitAccount, String creditAccount, BigDecimal amount,
            String requestType, String beneficaryIfsc, String beneficaryName, String beneficaryMobileNo, String remarks,
            String orgnCode, String userName, String todayDt) throws Exception;
}