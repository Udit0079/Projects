/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface AccountOpeningFacadeRemote {

    public String cbsIntroInfo(String introCustId, String introAcno, String accCode) throws ApplicationException;

    public java.lang.String custIdInformation(java.lang.String custId) throws ApplicationException;

    public java.lang.String getJointHolderDetails(java.lang.String custId) throws ApplicationException;

    public java.lang.String saveAccountOpenSbRd(java.lang.String cust_type, java.lang.String cust_id, java.lang.String actype, java.lang.String title, java.lang.String custname, java.lang.String craddress, java.lang.String praddress, java.lang.String phoneno, java.lang.String dob, int occupation, int operatingMode, java.lang.String panno, java.lang.String grdname, java.lang.String grd_relation, java.lang.String agcode, java.lang.String DateText, java.lang.String UserText, java.lang.String fathername, java.lang.String acnoIntro, java.lang.String JtName1, java.lang.String JtName2, java.lang.String orgncode, java.lang.String nominee, java.lang.String nominee_relatioship, java.lang.String JtName3, java.lang.String JtName4, int rdperiod, float rdinstall, float rdroi, int docuno, java.lang.String docudetails, java.lang.String nomineeAdd, java.lang.String nomineeDate, java.lang.String custid1, java.lang.String custid2, java.lang.String custid3, java.lang.String custid4, java.lang.String schemeCode, java.lang.String intCode, java.lang.String actCateg, String hufFamily, int chqOpt) throws ApplicationException;

    // public java.lang.String cbscustId(java.lang.String accType, java.lang.String brcode) throws ApplicationException;
    public java.util.List dateDiffStatementFreqDate(java.lang.String statementFreqDate) throws ApplicationException;

    public java.util.List getRDRoi(double amount, long days) throws ApplicationException;

    public List interestCodeAdditionalRoi(String intCode, double amt) throws ApplicationException;

    public java.lang.String cbsRdCalculation(float instAmt, int periodInMonth, float rdRoi) throws com.cbs.exception.ApplicationException;

    List SetValueAcctToScheme(String schemeCode) throws ApplicationException;

    public java.util.List schemeCodeCombo(java.lang.String acctype) throws com.cbs.exception.ApplicationException;

    public java.util.List interestCodeCombo() throws com.cbs.exception.ApplicationException;

    public List interestCodeData(String intCode) throws ApplicationException;

    public java.lang.String getROI(java.lang.String intTableCode, double amt, java.lang.String date) throws com.cbs.exception.ApplicationException;

    public java.util.List acctTypeCombo() throws com.cbs.exception.ApplicationException;

    public java.util.List IntroducerAcctTypeCombo() throws com.cbs.exception.ApplicationException;

    public java.util.List parameterCode() throws com.cbs.exception.ApplicationException;

    public java.util.List appSeqNoCombo(java.lang.String acctType) throws com.cbs.exception.ApplicationException;

    public java.util.List acctTypeComboLostFocus(java.lang.String acType) throws com.cbs.exception.ApplicationException;

    public java.util.List appSeqNoComboLostFocus(java.lang.String acctType, java.lang.String appNo, java.lang.String fYear) throws com.cbs.exception.ApplicationException;

    public java.util.List customerDetail(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public java.lang.String introducerAcDetail(java.lang.String custid, java.lang.String introCustId, java.lang.String introAcno) throws com.cbs.exception.ApplicationException;

    public java.util.List operationModeComboLostFocus(java.lang.String code) throws com.cbs.exception.ApplicationException;

    String saveTLAcOpenDetail(String cust_id, String tmpANat, String appTp, String appSeq,
            String FYear, String actype, String orgncode, String agcode, String acOpDt, String title,
            String custname, String fathername, String praddress, String craddress, String phoneno,
            String panno, String dob, Integer occupation, String grdname, String grd_relation,
            Integer operatingMode, String JtName1, String JtName2, String JtName3, String JtName4,
            String nominee, String nominee_relatioship, String nomineeAdd, String nomineeDob,
            String acnoIntro, Float Odlimit, Float roi, String sancDtV, String IntOpt, String subSidyAmt,
            Integer docuno, String docudetails, String spInst, String UserText, String schemeCode,
            Integer moritoriumPeriod, Float acnoPreDr, Float acnoPreCr, String rateCode, String calMethod, String calOn,
            String intAppFrequency, String calLevel, String compoundFrequency, String disbursmentType, String intCode,
            String paggingFrequency, Integer LoanPeriod1, Integer LoanPeriod2, String jtCustId1, String jtCustId2, String jtCustId3, String jtCustId4, String actCateg, String hufFamily, int chqOpt) throws ApplicationException;

    public java.util.List getOrganizationType() throws com.cbs.exception.ApplicationException;

    public java.util.List getExistingCustDetail(java.lang.String custId) throws com.cbs.exception.ApplicationException;

    public java.lang.String introducerAcDetailForDlFDAc(java.lang.String introCustId, java.lang.String introAcno) throws com.cbs.exception.ApplicationException;

    public java.util.List getJtName(java.lang.Integer custId) throws com.cbs.exception.ApplicationException;

    public java.lang.String GetROIForLoanDLAcOpen(java.lang.String intTableCode, float amt, java.lang.String date) throws com.cbs.exception.ApplicationException;

    public String saveDlAcctOpenRegister(String actype, String Occupation, String OpMode, String orgncode, String agcode,
            Float Sanclimit, Float ROI, String Title, String custname, String CrAddress, String PrAddress, String phone,
            String enterby, String pan, String FrName, String Age, String JtName1, String JtName2, String IntAcct, String catvalue,
            String CustIDExist, String schemeCode, String intTableCode, Integer moratoriunPd, Float accPrefDr,
            Float accPrefCr, String rateCode, String disbType, String calcMethod, String calcOnInt, String calLevel,
            String compFreq, Integer peggFreq, String intAppFreq, Integer loanPdMonth, Integer loanPdDay, List table, String dob, String custId1, String custId2, String actCateg, String acOpenFromFlag, String hufFamily) throws ApplicationException;

    public java.util.List getStatus() throws com.cbs.exception.ApplicationException;

    public java.util.List getSecurityDesc1(java.lang.String securityType) throws com.cbs.exception.ApplicationException;

    public java.util.List getSecurityDesc2(java.lang.String securityType, java.lang.String securityDesc1) throws com.cbs.exception.ApplicationException;

    public String getSecurityDesc2Code(String securityType, String securityDesc1, String securityDesc2) throws ApplicationException;

    public java.util.List getSecurityDesc3(java.lang.String securityType, java.lang.String securityDesc1, java.lang.String securityDesc2) throws com.cbs.exception.ApplicationException;

    public java.lang.String cbsCustId(java.lang.String acctType, java.lang.String brcode) throws com.cbs.exception.ApplicationException;

    public List getAcTypeDescription(String acctCode) throws ApplicationException;

    public int getRdEnableCode() throws ApplicationException;

    public String getMemDetails() throws ApplicationException;

    public int chkMonDetails() throws ApplicationException;

    public List chkSchDetails(String schCode) throws ApplicationException;

    public String shareCompare(String custId, Float Odlimit, String schCode, String acNo) throws ApplicationException;

    public String loanAmtCompare(String custId, Float Odlimit, String schCode, String returnParameter, String acNo) throws ApplicationException;

    public String saveFidilityAccountOpen(String mode, String custId, String acNo, String openDt, String businessDesig, String custName, String actype, String userName, String orgnBrCode, String schemeCode, double bAmt, double prAmt) throws ApplicationException;

    public List getDetail(String acNo) throws ApplicationException;

    public List getUnAuthAccountList(String brncode) throws ApplicationException;

    public List memInfo(String folioNo) throws ApplicationException;

    public List loanForMemInfo(String acNo) throws ApplicationException;

    public List memMirDue(String acNo) throws ApplicationException;

    public java.util.List getRDRate(double amount, int days, String IntCode) throws ApplicationException;

    public List getRDDaysLst(String IntCode) throws ApplicationException;

    public String custMergedFlag(String custId) throws ApplicationException;

    public String GetROIAcOpen(String intTableCode, double amt, int period, String date) throws ApplicationException;

    public String getCustAcTdsDocDtl(String customerId, String acno, String flag) throws ApplicationException;

    public List fnAcnat(String acType) throws ApplicationException;

    //---- Added by Manish Kumar
    public List getOccupation(String custId) throws ApplicationException;

    public String customerId(String acountNumber) throws ApplicationException;

    public String removeSomeSpecialChar(String str) throws ApplicationException;

    public String getRecRefDiscription(String RecRefNo, String RecRefCode) throws ApplicationException;
    //---

    //FOR Chq Book Facility At Account Opening 
    public String getChqFacilityTrue(String acctType) throws ApplicationException;

    public List getChbookStatusList(String acno, String acnature) throws ApplicationException;
    
    public List getCustIdForRenewal(String acNo,String receoptNo,String voucherNo) throws ApplicationException;
     
    public List isCustIdForRenewalAuth(String custId,String acNo) throws ApplicationException;
    
    public List getSecurityDescAll() throws ApplicationException;
}
