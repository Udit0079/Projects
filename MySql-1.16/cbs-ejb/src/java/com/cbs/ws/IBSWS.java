/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.ws;

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
import com.cbs.facade.cdci.IBSWSFacadeRemote;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author root
 */
@WebService(serviceName = "IBSWS")
@Stateless()
public class IBSWS {
    @EJB
    private IBSWSFacadeRemote ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "getHOAddress")
    public String getHOAddress() throws ApplicationException {
        return ejbRef.getHOAddress();
    }

    @WebMethod(operationName = "getBranchNameandAddress")
    public List getBranchNameandAddress(@WebParam(name = "orgnbrcode") String orgnbrcode) throws ApplicationException {
        return ejbRef.getBranchNameandAddress(orgnbrcode);
    }

    @WebMethod(operationName = "getAccountTypeMasterPojoList")
    public List<AccountTypeMasterPojo> getAccountTypeMasterPojoList(@WebParam(name = "acType") String acType) throws ApplicationException {
        return ejbRef.getAccountTypeMasterPojoList(acType);
    }

    @WebMethod(operationName = "getAccountStatement")
    public List<AccountStatementPojo> getAccountStatement(@WebParam(name = "acNo") String acNo, @WebParam(name = "fromDt") String fromDt, @WebParam(name = "toDt") String toDt) throws ApplicationException {
        return ejbRef.getAccountStatement(acNo, fromDt, toDt);
    }

    @WebMethod(operationName = "getCurrentTDSlab")
    public List getCurrentTDSlab(@WebParam(name = "acctNature") String acctNature) throws ApplicationException {
        return ejbRef.getCurrentTDSlab(acctNature);
    }

    @WebMethod(operationName = "tdInterestCalculation")
    public String tdInterestCalculation(@WebParam(name = "InterestOption") String InterestOption, @WebParam(name = "RoInt") float RoInt, @WebParam(name = "FDDate") String FDDate, @WebParam(name = "MatDate") String MatDate, @WebParam(name = "amt") double amt, @WebParam(name = "prd") String prd) throws ApplicationException {
        return ejbRef.tdInterestCalculation(InterestOption, RoInt, FDDate, MatDate, amt, prd);
    }

    @WebMethod(operationName = "getTDRoi")
    public float getTDRoi(@WebParam(name = "custCat") String custCat, @WebParam(name = "tOTAMT") Float tOTAMT, @WebParam(name = "matDt") String matDt, @WebParam(name = "wefDt") String wefDt, @WebParam(name = "presentDt") String presentDt, @WebParam(name = "acNat") String acNat) throws ApplicationException {
        return ejbRef.getTDRoi(custCat, tOTAMT, matDt, wefDt, presentDt, acNat);
    }

    @WebMethod(operationName = "getRDRoi")
    public float getRDRoi(@WebParam(name = "amount") double amount, @WebParam(name = "days") long days) throws ApplicationException {
        return ejbRef.getRDRoi(amount, days);
    }

    @WebMethod(operationName = "rdInterestCalculation")
    public double rdInterestCalculation(@WebParam(name = "instAmt") float instAmt, @WebParam(name = "periodInMonth") int periodInMonth, @WebParam(name = "rdRoi") float rdRoi) throws ApplicationException {
        return ejbRef.rdInterestCalculation(instAmt, periodInMonth, rdRoi);
    }

    @WebMethod(operationName = "createNewFixedDeposit")
    public List createNewFixedDeposit(@WebParam(name = "userId") String userId, @WebParam(name = "CustId") String CustId, @WebParam(name = "drAc") String drAc, @WebParam(name = "orgBrn") String orgBrn, @WebParam(name = "amount") float amount, @WebParam(name = "roi") float roi, @WebParam(name = "interestRateType") String interestRateType, @WebParam(name = "maturityDate") String maturityDate, @WebParam(name = "period") String period, @WebParam(name = "year") int year, @WebParam(name = "month") int month, @WebParam(name = "days") int days) {
        return ejbRef.createNewFixedDeposit(userId, CustId, drAc, orgBrn, amount, roi, interestRateType, maturityDate, period, year, month, days);
    }

    @WebMethod(operationName = "accountOpenAll")
    public String accountOpenAll(@WebParam(name = "acttype") String acttype, @WebParam(name = "acno") String acno, @WebParam(name = "rdperiod") int rdperiod, @WebParam(name = "rdinstall") float rdinstall, @WebParam(name = "rdroi") float rdroi, @WebParam(name = "custid") String custid, @WebParam(name = "brnCode") String brnCode, @WebParam(name = "userid") String userid) {
        return ejbRef.accountOpenAll(acttype, acno, rdperiod, rdinstall, rdroi, custid, brnCode, userid);
    }

    @WebMethod(operationName = "getBalanceByAccountNumber")
    public BigDecimal getBalanceByAccountNumber(@WebParam(name = "acNo") String acNo, @WebParam(name = "asOnDt") String asOnDt) throws ApplicationException {
        return ejbRef.getBalanceByAccountNumber(acNo, asOnDt);
    }

    @WebMethod(operationName = "isAccountOperative")
    public boolean isAccountOperative(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.isAccountOperative(acNo);
    }

    @WebMethod(operationName = "updateEmailIdMobileNumberByCusromerId")
    public boolean updateEmailIdMobileNumberByCusromerId(@WebParam(name = "custId") String custId, @WebParam(name = "eMailId") String eMailId, @WebParam(name = "mobileNumber") String mobileNumber) throws ApplicationException {
        return ejbRef.updateEmailIdMobileNumberByCusromerId(custId, eMailId, mobileNumber);
    }

    @WebMethod(operationName = "customerAccountNumbersByCustomerId")
    public List<CustomerIdPojo> customerAccountNumbersByCustomerId(@WebParam(name = "custId") String custId) throws ApplicationException {
        return ejbRef.customerAccountNumbersByCustomerId(custId);
    }

    @WebMethod(operationName = "customerInformationByCustomerId")
    public CustomerDetailsPojo customerInformationByCustomerId(@WebParam(name = "customerId") String customerId) throws ApplicationException {
        return ejbRef.customerInformationByCustomerId(customerId);
    }

    @WebMethod(operationName = "getAccountNoVerficationDetail")
    public List<CustomerDetailsPojo> getAccountNoVerficationDetail(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.getAccountNoVerficationDetail(acNo);
    }

    @WebMethod(operationName = "getcustomerInformationByAccountNo")
    public AccountMasterPojo getcustomerInformationByAccountNo(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.getcustomerInformationByAccountNo(acNo);
    }

    @WebMethod(operationName = "getNomineeDetailsByAccountNo")
    public NomineeDetailsPojo getNomineeDetailsByAccountNo(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.getNomineeDetailsByAccountNo(acNo);
    }

    @WebMethod(operationName = "getDocumentDetailsByAccountNo")
    public DocumentDetailsPojo getDocumentDetailsByAccountNo(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.getDocumentDetailsByAccountNo(acNo);
    }

    @WebMethod(operationName = "getCustomerDetailsByCustNo")
    public CustomerMasterDetailsPojo getCustomerDetailsByCustNo(@WebParam(name = "custNo") String custNo, @WebParam(name = "brnCode") String brnCode) throws ApplicationException {
        return ejbRef.getCustomerDetailsByCustNo(custNo, brnCode);
    }

    @WebMethod(operationName = "getRdAccountListbyCustomerid")
    public List<RdAcountDetailPojo> getRdAccountListbyCustomerid(@WebParam(name = "custid") String custid) throws ApplicationException {
        return ejbRef.getRdAccountListbyCustomerid(custid);
    }

    @WebMethod(operationName = "getRdAccountsDetailsInstallment")
    public List<RdAcountDetailPojo> getRdAccountsDetailsInstallment(@WebParam(name = "acno") String acno, @WebParam(name = "status") String status) throws ApplicationException {
        return ejbRef.getRdAccountsDetailsInstallment(acno, status);
    }

    @WebMethod(operationName = "transferAmount")
    public String transferAmount(@WebParam(name = "drAc") String drAc, @WebParam(name = "crAc") String crAc, @WebParam(name = "amount") double amount) throws ApplicationException {
        return ejbRef.transferAmount(drAc, crAc, amount);
    }

    @WebMethod(operationName = "ddTransfer")
    public String ddTransfer(@WebParam(name = "drAc") String drAc, @WebParam(name = "destination") String destination, @WebParam(name = "favour") String favour, @WebParam(name = "amount") double amount, @WebParam(name = "commision") double commision, @WebParam(name = "tax") double tax, @WebParam(name = "billType") String billType) throws ApplicationException {
        return ejbRef.ddTransfer(drAc, destination, favour, amount, commision, tax, billType);
    }

    @WebMethod(operationName = "getChequeRecord")
    public List getChequeRecord(@WebParam(name = "acNature") String acNature, @WebParam(name = "acNo") String acNo, @WebParam(name = "chqNo") Float chqNo) throws ApplicationException {
        return ejbRef.getChequeRecord(acNature, acNo, chqNo);
    }

    @WebMethod(operationName = "taxAmountProcedure")
    public String taxAmountProcedure(@WebParam(name = "amt") double amt, @WebParam(name = "type") String type, @WebParam(name = "rUpto") int rUpto) throws ApplicationException {
        return ejbRef.taxAmountProcedure(amt, type, rUpto);
    }

    @WebMethod(operationName = "getCommission")
    public List<CommissionPojo> getCommission(@WebParam(name = "bill") String bill, @WebParam(name = "payby") int payby) throws ApplicationException {
        return ejbRef.getCommission(bill, payby);
    }

    @WebMethod(operationName = "getAccountNatureByAccountType")
    public String getAccountNatureByAccountType(@WebParam(name = "acctType") String acctType) throws ApplicationException {
        return ejbRef.getAccountNatureByAccountType(acctType);
    }

    @WebMethod(operationName = "saveStopPaymentRequest")
    public String saveStopPaymentRequest(@WebParam(name = "drAc") String drAc, @WebParam(name = "chqStart") String chqStart, @WebParam(name = "chqEnd") String chqEnd, @WebParam(name = "option") String option) throws ApplicationException {
        return ejbRef.saveStopPaymentRequest(drAc, chqStart, chqEnd, option);
    }

    @WebMethod(operationName = "unclearedTransactionsList")
    public List<TransactionsPojo> unclearedTransactionsList(@WebParam(name = "accountNo") String accountNo) throws ApplicationException {
        return ejbRef.unclearedTransactionsList(accountNo);
    }

    @WebMethod(operationName = "getMiniStatement")
    public List<MiniStatementPojo> getMiniStatement(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.getMiniStatement(acNo);
    }

    @WebMethod(operationName = "getAllActiveAccountByCustomerId")
    public List<CustomerIdPojo> getAllActiveAccountByCustomerId(@WebParam(name = "custId") String custId) throws ApplicationException {
        return ejbRef.getAllActiveAccountByCustomerId(custId);
    }

    @WebMethod(operationName = "getDetailOfAccountNumber")
    public CustomerIdPojo getDetailOfAccountNumber(@WebParam(name = "acno") String acno) throws ApplicationException {
        return ejbRef.getDetailOfAccountNumber(acno);
    }

    @WebMethod(operationName = "processChqBookRequest")
    public String processChqBookRequest(@WebParam(name = "acno") String acno, @WebParam(name = "requestNo") BigInteger requestNo, @WebParam(name = "requestType") String requestType, @WebParam(name = "noOfBooks") Integer noOfBooks, @WebParam(name = "noOfLeaves") String noOfLeaves, @WebParam(name = "ibRequestDt") String ibRequestDt, @WebParam(name = "deliveryAddress") String deliveryAddress, @WebParam(name = "deliveryMode") String deliveryMode, @WebParam(name = "deliveryStatus") String deliveryStatus) throws ApplicationException {
        return ejbRef.processChqBookRequest(acno, requestNo, requestType, noOfBooks, noOfLeaves, ibRequestDt, deliveryAddress, deliveryMode, deliveryStatus);
    }

    @WebMethod(operationName = "processStopPaymentRequest")
    public String processStopPaymentRequest(@WebParam(name = "acno") String acno, @WebParam(name = "requestNo") BigInteger requestNo, @WebParam(name = "requestType") String requestType, @WebParam(name = "ibRequestDt") String ibRequestDt, @WebParam(name = "chqStart") long chqStart, @WebParam(name = "chqEnd") long chqEnd, @WebParam(name = "option") String option) throws ApplicationException {
        return ejbRef.processStopPaymentRequest(acno, requestNo, requestType, ibRequestDt, chqStart, chqEnd, option);
    }

    @WebMethod(operationName = "getBranchBusinessDt")
    public String getBranchBusinessDt(@WebParam(name = "brCode") String brCode) throws ApplicationException {
        return ejbRef.getBranchBusinessDt(brCode);
    }

    @WebMethod(operationName = "getUsersRequestStatus")
    public List<RequestStatusObj> getUsersRequestStatus(@WebParam(name = "reqNoList") List<Long> reqNoList) throws ApplicationException {
        return ejbRef.getUsersRequestStatus(reqNoList);
    }

    @WebMethod(operationName = "updateRequestStatus")
    public String updateRequestStatus(@WebParam(name = "requestNo") Long requestNo, @WebParam(name = "status") String status) throws ApplicationException {
        return ejbRef.updateRequestStatus(requestNo, status);
    }

    @WebMethod(operationName = "verifyRegisteringUser")
    public CustomerDetailsPojo verifyRegisteringUser(@WebParam(name = "idType") String idType, @WebParam(name = "idNo") String idNo) throws ApplicationException {
        return ejbRef.verifyRegisteringUser(idType, idNo);
    }

    @WebMethod(operationName = "customerTransactionalAccountNumbersByCustomerId")
    public List<CustomerIdPojo> customerTransactionalAccountNumbersByCustomerId(@WebParam(name = "custId") String custId) throws ApplicationException {
        return ejbRef.customerTransactionalAccountNumbersByCustomerId(custId);
    }

    @WebMethod(operationName = "ibsIntraTrfTxnPosting")
    public String ibsIntraTrfTxnPosting(@WebParam(name = "debitAccountNo") String debitAccountNo, @WebParam(name = "creditAccounNo") String creditAccounNo, @WebParam(name = "amount") BigDecimal amount) throws ApplicationException {
        return ejbRef.ibsIntraTrfTxnPosting(debitAccountNo, creditAccounNo, amount);
    }

    @WebMethod(operationName = "processImpsTxn")
    public String processImpsTxn(@WebParam(name = "id") Long id, @WebParam(name = "username") String username, @WebParam(name = "debitAccount") String debitAccount, @WebParam(name = "creditAccount") String creditAccount, @WebParam(name = "amount") BigDecimal amount, @WebParam(name = "requestType") String requestType, @WebParam(name = "beneficaryIfsc") String beneficaryIfsc, @WebParam(name = "beneficaryName") String beneficaryName, @WebParam(name = "beneficaryMobileNo") String beneficaryMobileNo, @WebParam(name = "remarks") String remarks, @WebParam(name = "orgnCode") String orgnCode, @WebParam(name = "userName") String userName, @WebParam(name = "todayDt") String todayDt) throws Exception {
        return ejbRef.processImpsTxn(id, username, debitAccount, creditAccount, amount, requestType, beneficaryIfsc, beneficaryName, beneficaryMobileNo, remarks, orgnCode, userName, todayDt);
    }
    
}
