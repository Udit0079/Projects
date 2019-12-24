package com.cbs.facade.ho.bankGuarantee;

import com.cbs.dto.BankGuaranteePojo;
import com.cbs.dto.TdLienMarkingGrid;
import com.cbs.exception.ApplicationException;
import java.util.List;

public interface BankGuaranteeFacadeRemote {

    public double getTaxCharges(double totalCommissionAmt, String dt) throws ApplicationException;

    public String getAccountNumber(String userAccountNumber) throws ApplicationException;

    public String getAccountValidation(String userAccountNumber) throws ApplicationException;

    public List getAccountDetail(String acno) throws ApplicationException;

    public String updateRenewEntry(int GuaranteeNo, double guaranteeAmt, String updateDt, String entryDt,
            String enterBy, String action, List<TdLienMarkingGrid> currentItem, String authBy, String orgnBrCode,
            String guaranteeIssuedBy,double comissionAmt, String acNo) throws ApplicationException;

    public String updateLienEntry(int GuaranteeNo, double guaranteeAmt, double comissionAmt, String acNo,
            String entryDt, String enterBy, String action, String authBy, List<TdLienMarkingGrid> currentItem,
            String orgnBrCode, String updateDt, String guaranteeIssuedBy) throws ApplicationException;

    public String deleteEntry(int GuaranteeNo, Double guaranteeAmt, String action, List<TdLienMarkingGrid> currentItem,
            String orgnBrCode, String entryDt, String enterBy) throws ApplicationException;

    public String saveIssueEntry(int GuaranteeNo, String acno, String action, String benfiName, String benfiAddress, String city, String state,
            String pinCode, String classification, String guaranteeIssuedBy, String purpose, String validityIn, int period,
            Double guaranteeAmt, String mode, Double comissionAmt, Double totalcomissionAmt, Double taxCharges,
            String guaranteeExpiryDate, String guaranteeInvokingDueDt, String enterBy, String authBy, String entryDt,
            String updateDt, int txnId, String orgnBrCode, List<TdLienMarkingGrid> currentItem) throws ApplicationException;

    public String saveLienEntry(int GuaranteeNo, String acno, String action, String benfiName, String benfiAddress, String city, String state,
            String pinCode, String classification, String guaranteeIssuedBy, String purpose, String validityIn, int period,
            Double guaranteeAmt, String mode, Double comissionAmt, Double totalcomissionAmt, Double taxCharges,
            String guaranteeExpiryDate, String guaranteeInvokingDueDt, String enterBy, String authBy, String entryDt,
            String updateDt, int txnId, String orgnBrCode, List<TdLienMarkingGrid> currentItem) throws ApplicationException;

//    public String getCityCode(String city) throws ApplicationException;
    public List verifyGNo(String function, String action, String authValue, String brnCode) throws ApplicationException;

    public List verifyCase(String function, int GuaranteeNo, String action) throws ApplicationException;

    public List verifyGridCase(String Acno) throws ApplicationException;

    public List getCityList(String state) throws ApplicationException;
    
    public List verifyIssueCase(String action, int GuaranteeNo) throws ApplicationException;

    public String updateSecuritySecStatus(String ExpiryDate, String acno, Integer sno, String brnCode) throws ApplicationException;

    public String DeleteSecurityTableEntry(String expiredBy, String ExpiryDate, String acno, Double guarAmt, Integer sno, String brnCode) throws ApplicationException;

    public String UpdateSecurityTable(String expiredBy, String ExpiryDate, String acno, Double guarAmt, Integer sno, String brnCode) throws ApplicationException;

    public String verifyCheckAmt(String guarNo, Double guarAmt) throws ApplicationException;

    public List<BankGuaranteePojo> getReportData(String action, String fromDate, String toDate, String orgbrn, String reportType, String issueType, String tillDate) throws ApplicationException;
}
