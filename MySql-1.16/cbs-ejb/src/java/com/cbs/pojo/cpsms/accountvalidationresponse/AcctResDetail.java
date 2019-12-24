package com.cbs.pojo.cpsms.accountvalidationresponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author root
 */
public class AcctResDetail {

    private String reqMsgId;
    private String accountNo;
    private String acctValidity;
    private String acctStatus;
    private String accountType;
    private String bsrCode;
    private String ifscCode;
    private String entityCode;
    private String micrCode;
    private AHDetails ahDetails;
    private String error;
    
    private String accountOpenDate;
    private String accountCloseDate;
    private String accountCategory;
    private String masterAccNo;
    private String majorHead;
    private String minorHead;
    private String treasuryCode;
    private String pao_ddoCode;
    private String smsEnabled;
    private String activeChannels;
    private String fiAccount;
    private String zeroBalanceAccount;
    private String dailyCL;
    private String periodicCL;
    private String periodicCLUnit;
    private String regSanctionedCL;
    private String regSanctionedCLExpiryDate;
    private String activeSweep;
    private String connectedSweepAccount;
    
    public String getReqMsgId() {
        return reqMsgId;
    }

    @XmlElement(namespace = "",name = "ReqMsgId")
    public void setReqMsgId(String reqMsgId) {
        this.reqMsgId = reqMsgId;
    }

    public String getAcctValidity() {
        return acctValidity;
    }

    @XmlElement(namespace = "",name = "AccountValidity")
    public void setAcctValidity(String acctValidity) {
        this.acctValidity = acctValidity;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    @XmlElement(namespace = "",name = "AccountStatus")
    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    @XmlElement(namespace = "",name = "IFSCCode")
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    @XmlElement(namespace = "",name = "AccountNumber")
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBsrCode() {
        return bsrCode;
    }

    @XmlElement(namespace = "",name = "BSRCode")
    public void setBsrCode(String bsrCode) {
        this.bsrCode = bsrCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    @XmlElement(namespace = "",name = "EntityCode")
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getAccountType() {
        return accountType;
    }

    @XmlElement(namespace = "",name = "AccountType")
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public AHDetails getAhDetails() {
        return ahDetails;
    }

    @XmlElement(namespace = "",name = "AHDetails")
    public void setAhDetails(AHDetails ahDetails) {
        this.ahDetails = ahDetails;
    }

    public String getError() {
        return error;
    }

    @XmlElement(namespace = "",name = "Error")
    public void setError(String error) {
        this.error = error;
    }

    public String getMicrCode() {
        return micrCode;
    }

    @XmlElement(namespace = "",name = "MicrCode")
    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    
    
    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    @XmlElement(namespace = "",name = "AccountOpenDate")
    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public String getAccountCloseDate() {
        return accountCloseDate;
    }

    @XmlElement(namespace = "",name = "AccountCloseDate")
    public void setAccountCloseDate(String accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    @XmlElement(namespace = "",name = "AccountCategory")
    public void setAccountCategory(String accountCategory) {
        this.accountCategory = accountCategory;
    }

    public String getMasterAccNo() {
        return masterAccNo;
    }

    @XmlElement(namespace = "",name = "MasterAccountNumber")
    public void setMasterAccNo(String masterAccNo) {
        this.masterAccNo = masterAccNo;
    }

    public String getMajorHead() {
        return majorHead;
    }

    @XmlElement(namespace = "",name = "MajorHead")
    public void setMajorHead(String majorHead) {
        this.majorHead = majorHead;
    }

    public String getMinorHead() {
        return minorHead;
    }

    @XmlElement(namespace = "",name = "MinorHead")
    public void setMinorHead(String minorHead) {
        this.minorHead = minorHead;
    }

    public String getTreasuryCode() {
        return treasuryCode;
    }

    @XmlElement(namespace = "",name = "TreasuryCode")
    public void setTreasuryCode(String treasuryCode) {
        this.treasuryCode = treasuryCode;
    }

    public String getPao_ddoCode() {
        return pao_ddoCode;
    }

    @XmlElement(namespace = "",name = "PAO_DDOCode")
    public void setPao_ddoCode(String pao_ddoCode) {
        this.pao_ddoCode = pao_ddoCode;
    }

    public String getSmsEnabled() {
        return smsEnabled;
    }

    @XmlElement(namespace = "",name = "SMSEnabled")
    public void setSmsEnabled(String smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public String getActiveChannels() {
        return activeChannels;
    }

    @XmlElement(namespace = "",name = "ActiveChannels")
    public void setActiveChannels(String activeChannels) {
        this.activeChannels = activeChannels;
    }

    public String getFiAccount() {
        return fiAccount;
    }

    @XmlElement(namespace = "",name = "FIAccount")
    public void setFiAccount(String fiAccount) {
        this.fiAccount = fiAccount;
    }

    public String getZeroBalanceAccount() {
        return zeroBalanceAccount;
    }

    @XmlElement(namespace = "",name = "ZeroBalanceAccount")
    public void setZeroBalanceAccount(String zeroBalanceAccount) {
        this.zeroBalanceAccount = zeroBalanceAccount;
    }

    public String getDailyCL() {
        return dailyCL;
    }

    @XmlElement(namespace = "",name = "DailyCL")
    public void setDailyCL(String dailyCL) {
        this.dailyCL = dailyCL;
    }

    public String getPeriodicCL() {
        return periodicCL;
    }

    @XmlElement(namespace = "",name = "PeriodicCL")
    public void setPeriodicCL(String periodicCL) {
        this.periodicCL = periodicCL;
    }

    public String getPeriodicCLUnit() {
        return periodicCLUnit;
    }

    @XmlElement(namespace = "",name = "PeriodicCLUnit")
    public void setPeriodicCLUnit(String periodicCLUnit) {
        this.periodicCLUnit = periodicCLUnit;
    }

    public String getRegSanctionedCL() {
        return regSanctionedCL;
    }

    @XmlElement(namespace = "",name = "RegSanctionedCL")
    public void setRegSanctionedCL(String regSanctionedCL) {
        this.regSanctionedCL = regSanctionedCL;
    }

    public String getRegSanctionedCLExpiryDate() {
        return regSanctionedCLExpiryDate;
    }

    @XmlElement(namespace = "",name = "RegSanctionedCLExpiryDate")
    public void setRegSanctionedCLExpiryDate(String regSanctionedCLExpiryDate) {
        this.regSanctionedCLExpiryDate = regSanctionedCLExpiryDate;
    }

    public String getActiveSweep() {
        return activeSweep;
    }

    @XmlElement(namespace = "",name = "ActiveSweep")
    public void setActiveSweep(String activeSweep) {
        this.activeSweep = activeSweep;
    }

    public String getConnectedSweepAccount() {
        return connectedSweepAccount;
    }
    
    @XmlElement(namespace = "",name = "ConnectedSweepAccount")
    public void setConnectedSweepAccount(String connectedSweepAccount) {
        this.connectedSweepAccount = connectedSweepAccount;
    }
    
    
    
    
    
    
    
    
    
}
