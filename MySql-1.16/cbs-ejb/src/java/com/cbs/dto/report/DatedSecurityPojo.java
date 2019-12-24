package com.cbs.dto.report;

public class DatedSecurityPojo implements java.io.Serializable {

    private String bankName;
    private String bankAddress;
    private String acNo;
    private String custName;
    private String remarks;
    private double matValue;
    private String matDate;
    private String securityChg;
    private String status;
    private String enteredBy;
    private String entryDate;
    private double roi;
    private double lienValue;
    private double outstandingBalance;
    private String securityOptions;

    public String getSecurityOptions() {
        return securityOptions;
    }

    public void setSecurityOptions(String securityOptions) {
        this.securityOptions = securityOptions;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public double getMatValue() {
        return matValue;
    }

    public void setMatValue(double matValue) {
        this.matValue = matValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSecurityChg() {
        return securityChg;
    }

    public void setSecurityChg(String securityChg) {
        this.securityChg = securityChg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getLienValue() {
        return lienValue;
    }

    public void setLienValue(double lienValue) {
        this.lienValue = lienValue;
    }
    
      
}