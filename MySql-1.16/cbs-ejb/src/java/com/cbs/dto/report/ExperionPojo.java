/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;
import java.math.BigDecimal;
/**
 *
 * @author root
 */
public class ExperionPojo implements java.io.Serializable{
 
    private String memberCode;
    private String memberName;
    private String cycleIdent;
    private String reportDate;
    private String reportPassword;
    private String futureUse;
    private String memberData;
    private String custNameF1;
    private String custNameF2;
    private String custNameF3;
    private String custNameF4;
    private String custNameF5;
    private String dob;
    private String gender;
    private String incomeTaxId;
    private String passportNo;
    /*CIBIL Start*/    
    private String passportIssueDt;
    private String passportExpDt;
    /*CIBIL End*/
    
    private String voterIdNo;
    /*CIBIL Start*/
    private String driLicenseNo;
    private String driLicenseIssueDt;
    private String driLicenseExpDt;
    private String rationCardNo;
    private String universalIdNo;
    private String addId1;
    private String addId2;
    /*CIBIL End*/
    
    private String telephone;
    /*CIBIL Start*/
    private String telNoResidence;
    private String telNoOffice;
    private String extOffice;
    private String telNoOther;
    private String extOther;
    private String emailId1;
    private String emailId2;
    /*CIBIL End*/
    
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String stateCode;
    private String pinCode;
    /*CIBIL Start*/
    private String addressCategory1;
    private String residenceCode1;
    private String address2;
    private String stateCode2;
    private String pinCode2;
    private String addressCategory2;
    private String residenceCode2;
    /*CIBIL End*/
    
    private String reportingMemCode;
    private String memberShortName;
    private String currentAcno;
    private String accountType;
    private String accountHolderTypeCode;
    private String disbursDt;
    private String lastPaymentdate;
    private String dateClose;
    private String dateReported;
    private String sanctAmt;
    private String currentBalance;
    private String amountOverDue;
    private String noOfDaysPast;
    private String oldReportingMemCode;
    private String oldMemShortName;
    private String oldAccountNo;
    /*CIBIL Start*/
    private String oldAccType;
    private String oldOwnershipIndicator;
    /*CIBIL End*/
    
    private String writtenOffStatus;
    /*CIBIL Start*/
    private String writtenSettledStatus;
    /*CIBIL End*/
    
    private String assetClassif;
    /*CIBIL Start*/
    private String valueOfCollateral;
    private String typeOfCollateral;
    private String creditLimit;
    private String cashLimit;
    private String rateOfInterest;
    private String repaymentTenure;
    private String emiAmt;
    private String writtenOffAmountTotal;
    private String writtenOffPrincipalAmount;
    private String settlementAmt;
    private String paymentFrequency;
    private String actualPaymentAmt;
    private String occupationCode;
    private String income;
    private String netGrossIncomeIndicator;
    private String monthlyAnnualIncomeIndicator;
    private String borcity2;
    private String borDistrict2;
    /*CIBIL End*/

    public String getBorDistrict2() {
        return borDistrict2;
    }
    public void setBorDistrict2(String borDistrict2) {
        this.borDistrict2 = borDistrict2;
    }
    public String getBorcity2() {
        return borcity2;
    }
    public void setBorcity2(String borcity2) {
        this.borcity2 = borcity2;
    }
    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getCustNameF1() {
        return custNameF1;
    }

    public void setCustNameF1(String custNameF1) {
        this.custNameF1 = custNameF1;
    }

    public String getCustNameF2() {
        return custNameF2;
    }

    public void setCustNameF2(String custNameF2) {
        this.custNameF2 = custNameF2;
    }

    public String getCustNameF3() {
        return custNameF3;
    }

    public void setCustNameF3(String custNameF3) {
        this.custNameF3 = custNameF3;
    }

    public String getCustNameF4() {
        return custNameF4;
    }

    public void setCustNameF4(String custNameF4) {
        this.custNameF4 = custNameF4;
    }

    public String getCustNameF5() {
        return custNameF5;
    }

    public void setCustNameF5(String custNameF5) {
        this.custNameF5 = custNameF5;
    }

   
    public String getAccountHolderTypeCode() {
        return accountHolderTypeCode;
    }

    public void setAccountHolderTypeCode(String accountHolderTypeCode) {
        this.accountHolderTypeCode = accountHolderTypeCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    

    public String getAssetClassif() {
        return assetClassif;
    }

    public void setAssetClassif(String assetClassif) {
        this.assetClassif = assetClassif;
    }


    public String getCycleIdent() {
        return cycleIdent;
    }

    public void setCycleIdent(String cycleIdent) {
        this.cycleIdent = cycleIdent;
    }

    public String getDateClose() {
        return dateClose;
    }

    public void setDateClose(String dateClose) {
        this.dateClose = dateClose;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getDisbursDt() {
        return disbursDt;
    }

    public void setDisbursDt(String disbursDt) {
        this.disbursDt = disbursDt;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFutureUse() {
        return futureUse;
    }

    public void setFutureUse(String futureUse) {
        this.futureUse = futureUse;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIncomeTaxId() {
        return incomeTaxId;
    }

    public void setIncomeTaxId(String incomeTaxId) {
        this.incomeTaxId = incomeTaxId;
    }

    public String getLastPaymentdate() {
        return lastPaymentdate;
    }

    public void setLastPaymentdate(String lastPaymentdate) {
        this.lastPaymentdate = lastPaymentdate;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberData() {
        return memberData;
    }

    public void setMemberData(String memberData) {
        this.memberData = memberData;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberShortName() {
        return memberShortName;
    }

    public void setMemberShortName(String memberShortName) {
        this.memberShortName = memberShortName;
    }

    public String getNoOfDaysPast() {
        return noOfDaysPast;
    }

    public void setNoOfDaysPast(String noOfDaysPast) {
        this.noOfDaysPast = noOfDaysPast;
    }

    public String getOldAccountNo() {
        return oldAccountNo;
    }

    public void setOldAccountNo(String oldAccountNo) {
        this.oldAccountNo = oldAccountNo;
    }

    


    public String getOldMemShortName() {
        return oldMemShortName;
    }

    public void setOldMemShortName(String oldMemShortName) {
        this.oldMemShortName = oldMemShortName;
    }

    public String getOldReportingMemCode() {
        return oldReportingMemCode;
    }

    public void setOldReportingMemCode(String oldReportingMemCode) {
        this.oldReportingMemCode = oldReportingMemCode;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportPassword() {
        return reportPassword;
    }

    public void setReportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
    }

    public String getReportingMemCode() {
        return reportingMemCode;
    }

    public void setReportingMemCode(String reportingMemCode) {
        this.reportingMemCode = reportingMemCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVoterIdNo() {
        return voterIdNo;
    }

    public void setVoterIdNo(String voterIdNo) {
        this.voterIdNo = voterIdNo;
    }

    public String getWrittenOffStatus() {
        return writtenOffStatus;
    }

    public void setWrittenOffStatus(String writtenOffStatus) {
        this.writtenOffStatus = writtenOffStatus;
    }

    public String getCurrentAcno() {
        return currentAcno;
    }

    public void setCurrentAcno(String currentAcno) {
        this.currentAcno = currentAcno;
    }

    public String getAmountOverDue() {
        return amountOverDue;
    }

    public void setAmountOverDue(String amountOverDue) {
        this.amountOverDue = amountOverDue;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getSanctAmt() {
        return sanctAmt;
    }

    public void setSanctAmt(String sanctAmt) {
        this.sanctAmt = sanctAmt;
    }

    public String getActualPaymentAmt() {
        return actualPaymentAmt;
    }

    public void setActualPaymentAmt(String actualPaymentAmt) {
        this.actualPaymentAmt = actualPaymentAmt;
    }

    public String getAddId1() {
        return addId1;
    }

    public void setAddId1(String addId1) {
        this.addId1 = addId1;
    }

    public String getAddId2() {
        return addId2;
    }

    public void setAddId2(String addId2) {
        this.addId2 = addId2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddressCategory1() {
        return addressCategory1;
    }

    public void setAddressCategory1(String addressCategory1) {
        this.addressCategory1 = addressCategory1;
    }

    public String getAddressCategory2() {
        return addressCategory2;
    }

    public void setAddressCategory2(String addressCategory2) {
        this.addressCategory2 = addressCategory2;
    }

    public String getCashLimit() {
        return cashLimit;
    }

    public void setCashLimit(String cashLimit) {
        this.cashLimit = cashLimit;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getDriLicenseExpDt() {
        return driLicenseExpDt;
    }

    public void setDriLicenseExpDt(String driLicenseExpDt) {
        this.driLicenseExpDt = driLicenseExpDt;
    }

    public String getDriLicenseIssueDt() {
        return driLicenseIssueDt;
    }

    public void setDriLicenseIssueDt(String driLicenseIssueDt) {
        this.driLicenseIssueDt = driLicenseIssueDt;
    }

    public String getDriLicenseNo() {
        return driLicenseNo;
    }

    public void setDriLicenseNo(String driLicenseNo) {
        this.driLicenseNo = driLicenseNo;
    }

    public String getEmailId1() {
        return emailId1;
    }

    public void setEmailId1(String emailId1) {
        this.emailId1 = emailId1;
    }

    public String getEmailId2() {
        return emailId2;
    }

    public void setEmailId2(String emailId2) {
        this.emailId2 = emailId2;
    }

    public String getEmiAmt() {
        return emiAmt;
    }

    public void setEmiAmt(String emiAmt) {
        this.emiAmt = emiAmt;
    }

    public String getExtOffice() {
        return extOffice;
    }

    public void setExtOffice(String extOffice) {
        this.extOffice = extOffice;
    }

    public String getExtOther() {
        return extOther;
    }

    public void setExtOther(String extOther) {
        this.extOther = extOther;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getMonthlyAnnualIncomeIndicator() {
        return monthlyAnnualIncomeIndicator;
    }

    public void setMonthlyAnnualIncomeIndicator(String monthlyAnnualIncomeIndicator) {
        this.monthlyAnnualIncomeIndicator = monthlyAnnualIncomeIndicator;
    }

    public String getNetGrossIncomeIndicator() {
        return netGrossIncomeIndicator;
    }

    public void setNetGrossIncomeIndicator(String netGrossIncomeIndicator) {
        this.netGrossIncomeIndicator = netGrossIncomeIndicator;
    }

    public String getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }

    public String getOldAccType() {
        return oldAccType;
    }

    public void setOldAccType(String oldAccType) {
        this.oldAccType = oldAccType;
    }

    public String getOldOwnershipIndicator() {
        return oldOwnershipIndicator;
    }

    public void setOldOwnershipIndicator(String oldOwnershipIndicator) {
        this.oldOwnershipIndicator = oldOwnershipIndicator;
    }

    public String getPassportExpDt() {
        return passportExpDt;
    }

    public void setPassportExpDt(String passportExpDt) {
        this.passportExpDt = passportExpDt;
    }

    public String getPassportIssueDt() {
        return passportIssueDt;
    }

    public void setPassportIssueDt(String passportIssueDt) {
        this.passportIssueDt = passportIssueDt;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getPinCode2() {
        return pinCode2;
    }

    public void setPinCode2(String pinCode2) {
        this.pinCode2 = pinCode2;
    }

    public String getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(String rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public String getRationCardNo() {
        return rationCardNo;
    }

    public void setRationCardNo(String rationCardNo) {
        this.rationCardNo = rationCardNo;
    }

    public String getRepaymentTenure() {
        return repaymentTenure;
    }

    public void setRepaymentTenure(String repaymentTenure) {
        this.repaymentTenure = repaymentTenure;
    }

    public String getResidenceCode1() {
        return residenceCode1;
    }

    public void setResidenceCode1(String residenceCode1) {
        this.residenceCode1 = residenceCode1;
    }

    public String getResidenceCode2() {
        return residenceCode2;
    }

    public void setResidenceCode2(String residenceCode2) {
        this.residenceCode2 = residenceCode2;
    }

    public String getSettlementAmt() {
        return settlementAmt;
    }

    public void setSettlementAmt(String settlementAmt) {
        this.settlementAmt = settlementAmt;
    }

    public String getStateCode2() {
        return stateCode2;
    }

    public void setStateCode2(String stateCode2) {
        this.stateCode2 = stateCode2;
    }

    public String getTelNoOffice() {
        return telNoOffice;
    }

    public void setTelNoOffice(String telNoOffice) {
        this.telNoOffice = telNoOffice;
    }

    public String getTelNoOther() {
        return telNoOther;
    }

    public void setTelNoOther(String telNoOther) {
        this.telNoOther = telNoOther;
    }

    public String getTelNoResidence() {
        return telNoResidence;
    }

    public void setTelNoResidence(String telNoResidence) {
        this.telNoResidence = telNoResidence;
    }

    public String getTypeOfCollateral() {
        return typeOfCollateral;
    }

    public void setTypeOfCollateral(String typeOfCollateral) {
        this.typeOfCollateral = typeOfCollateral;
    }

    public String getUniversalIdNo() {
        return universalIdNo;
    }

    public void setUniversalIdNo(String universalIdNo) {
        this.universalIdNo = universalIdNo;
    }

    public String getValueOfCollateral() {
        return valueOfCollateral;
    }

    public void setValueOfCollateral(String valueOfCollateral) {
        this.valueOfCollateral = valueOfCollateral;
    }

    public String getWrittenOffAmountTotal() {
        return writtenOffAmountTotal;
    }

    public void setWrittenOffAmountTotal(String writtenOffAmountTotal) {
        this.writtenOffAmountTotal = writtenOffAmountTotal;
    }

    public String getWrittenOffPrincipalAmount() {
        return writtenOffPrincipalAmount;
    }

    public void setWrittenOffPrincipalAmount(String writtenOffPrincipalAmount) {
        this.writtenOffPrincipalAmount = writtenOffPrincipalAmount;
    }

    public String getWrittenSettledStatus() {
        return writtenSettledStatus;
    }

    public void setWrittenSettledStatus(String writtenSettledStatus) {
        this.writtenSettledStatus = writtenSettledStatus;
    }

   
    
    
    
}
