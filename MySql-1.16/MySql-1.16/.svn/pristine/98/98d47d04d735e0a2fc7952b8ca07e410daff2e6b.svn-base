/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.td;

import com.cbs.dto.AccountDetail;
import com.cbs.dto.RdInterestDTO;
import com.cbs.dto.report.TdPeriodMaturityPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface TdReceiptManagementFacadeRemote {

    public List getData() throws ApplicationException;

    public String getProvApplyFlag(String acType) throws ApplicationException;

    public List tableData(String accountNo) throws ApplicationException;

    public List accountStatus(String accountNo) throws ApplicationException;

    public String tdDupReceiptIssueClickGrid(String acno) throws ApplicationException;

    public String delete(String accountNo, float receiptNo, float rtNo) throws ApplicationException;

    public String receiptIssue(String accountNo, float receiptNo, float rtNo, String authBy,
            String accType) throws ApplicationException;

    public String orgFDInterest(String InterestOption, float RoInt, String FDDate, String MatDate,
            double amt, String prd, String brCode) throws ApplicationException;
    
     public String orgFDInterestSimple15gh(String InterestOption, float roInt, String fdDate, String matDate,
            double amt, String prd, String brCode) throws ApplicationException;

    public String getRtNumberInformation(String acno, String rtNumbers) throws ApplicationException;

    public List getCustFdInfo(String acno) throws ApplicationException;

    public List getAcctType() throws ApplicationException;

    public List getAcctTypeToBeCredit() throws ApplicationException;

    public List cbsIntroInfo(String acNo1, String acType) throws ApplicationException;

    public float tdApplicableROI(String acno, String custCat, double tOTAMT, String matDt, String wefDt,
            String presentDt, String acNat) throws ApplicationException;

    public List getAutoRenew() throws ApplicationException;

    public List getTdRecieptSeq() throws ApplicationException;

    public List fnAcnat(String acType) throws ApplicationException;

    public List getBookSeries(String acctNature, String receipt, String brCode) throws ApplicationException;

    public List getGlobalFdCondition() throws ApplicationException;

    public String newTdReciptCreation(String acctNature, String acNo, String vDate, String vDayBegin, String vDepositeDate,
            String vMatDate, double amount, String bookSeries, String enteredBy, String optInterest, String gLHeadProv,
            String acnoToCredit, float roi, int years, int months, int days, String autoRenew, String brCode,
            String autoPay, String paidAcno) throws ApplicationException;

    public String newTdReciptAuthorization(String acctNature, String acNo, String vDate, String vDayBegin, String vDepositeDate,
            String vMatDate, double amount, String tdReceipt, String bookSeries, String brCode, String acctType,
            String enteredBy, String optInterest, String gLHeadProv, String acnoToCredit, float roi, String strPeriod,
            int years, int months, int days, String autoRenew, String authBy, long txnId,
            String autoPay, String paidAcno) throws ApplicationException;

    public List tableData(Float receiptNo, String acType) throws ApplicationException;

    public String currentDate(String selectDuration, String duration) throws ApplicationException;

    public String tdEnquiryCalculation(String reviewByAmount, String enterAmount, String selectDuration,
            String duration1, String interestOption, String rateOfInterest, String brncode)
            throws ApplicationException;

    public String getAcctDetail(String accNo) throws ApplicationException;

    public List getTableDetails(String actNo) throws ApplicationException;

    public List<AccountDetail> getDetailOnClick(String actNo, String accType) throws ApplicationException;

    public List selectFromTdRecon(float voucherNo, String accNo);

    public List selectFromTdVouchMast(float voucherNo, String accNo);

    public List selectFromSecurityInfo(String userName);

    public List selectFromParameterInforeport();

    public String updateTdVouchMaster(float roi, String matDate, String period, String years,
            String months, String days, String accNo, float voucherNo) throws ApplicationException;

    public List getTdReceiptSearchAcType() throws ApplicationException;

    public List getBranchCodeList(String orgnCode) throws ApplicationException;

    public List<TdPeriodMaturityPojo> gettdPeriodMaturityList(String tdDate, String orgnCode,String acctNature) throws ApplicationException;

    public String getProductCode(String acno) throws ApplicationException;

    public List getAllBranchCodeList(String orgnCode) throws ApplicationException;

    public List getUnAuthAcNo(String orgBrCode, String curDt) throws ApplicationException;

    public List getUnAuthTxnId(String acno, String curDt) throws ApplicationException;

    public List getRtDetails(String acNo, float rtNo) throws ApplicationException;

    public String modifyReceiptDetails(String acctNature, String function, String acno, String voucherNo, String intOpt, String inttoAcno, String autoRenew,
            int day, int month, int year, float roi, String matDt, String strPd, String userId, String brCode,
            String autoPay, String paidAcno) throws ApplicationException;

    public String getTotalIntPaid(String acNo, float vchNo) throws ApplicationException;
    
    public String getValidateBookSeries(String acNat, String acType, String recpt, String brCode, String bookSr) throws ApplicationException;
    
    public boolean isStaffSeniorCitizen(String acno, int dblBenifitAge, String wefDt) throws ApplicationException;
    
    public String orgFDInterestGoi(String InterestOption, float RoInt, String FDDate, String MatDate,
            double amt, String prd, String brCode) throws ApplicationException;

    public List<RdInterestDTO> getRdperiodmaturityList(String matDtAsOn, String orgnCode) throws ApplicationException;
    
    public String orgFDInterestNew(String InterestOption, float RoInt, String FDDate, String MatDate,
            double amt, String prd, String brCode, String fdDateCrDate) throws ApplicationException;
}
