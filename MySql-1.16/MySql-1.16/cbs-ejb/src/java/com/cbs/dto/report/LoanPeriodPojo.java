package com.cbs.dto.report;

import java.util.Date;

public class LoanPeriodPojo implements java.io.Serializable {

    private String accountNumber;
    private String custName;
    private float rate;
    private double sanctionedamt;
    private Date sanctionedDate;
    private double disbAmount;
    private double intAmount;
    private double balance;
    private double overdueAmt;
    private double odLimit;
    private String accountType;
    private int duration;
    private String schemeCode;
    private String schemeDesc;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getDisbAmount() {
        return disbAmount;
    }

    public void setDisbAmount(double disbAmount) {
        this.disbAmount = disbAmount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(double overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Date getSanctionedDate() {
        return sanctionedDate;
    }

    public void setSanctionedDate(Date sanctionedDate) {
        this.sanctionedDate = sanctionedDate;
    }

    public double getSanctionedamt() {
        return sanctionedamt;
    }

    public void setSanctionedamt(double sanctionedamt) {
        this.sanctionedamt = sanctionedamt;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeDesc() {
        return schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }

    public double getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(double odLimit) {
        this.odLimit = odLimit;
    }

    public double getIntAmount() {
        return intAmount;
    }

    public void setIntAmount(double intAmount) {
        this.intAmount = intAmount;
    }
       
}
