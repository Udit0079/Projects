package com.cbs.dto.report;

import java.math.BigDecimal;

public class OverdueEmiReportPojo implements java.io.Serializable {

    private String bankName;
    private String branchAddress;
    private String accountNumber;
    private String custName;
    private String sanctionDate;
    private BigDecimal sanctionedamt;
    private BigDecimal installmentamt;
    private BigDecimal balance;
    private String lastRecDt;
    private BigDecimal repaymentAmt;
    private int noOfEmiOverdue;
    private double noOfActualEmiOverdue;
    private BigDecimal amountOverdue;
    private String sector;
    private String reportDate;
    private long currentStatusNoOfDays;
    private String accStatus;
    private String custId;
    private String folioName;
    private String dueDate;
    //Added by Manish kumar
    private String sNo;
    private boolean checkBoxFlag;
    private BigDecimal amount;
    private String glHead;
    private String flag;
    private String custState;
    private String branchState;
    private String moratoriumFlag;
    private String moratoriumOnOf;
    private String surety1Id;
    private String surety1Name;
    private String surety1Add;
    private String surety2Id;
    private String surety2Name;
    private String surety2Add;
    private String surety3Id;
    private String surety3Name;
    private String surety3Add;
    private String custAdd;
    private String customerId;
    private BigDecimal npaBalance;
    private BigDecimal penalIntCharged;
    private BigDecimal penalReceivable;
    private String joinName;
    private BigDecimal outstandingBalance;
    private String expiryDate;
    private String loanPeriod;

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }
    
    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public long getCurrentStatusNoOfDays() {
        return currentStatusNoOfDays;
    }

    public void setCurrentStatusNoOfDays(long currentStatusNoOfDays) {
        this.currentStatusNoOfDays = currentStatusNoOfDays;
    }

    public BigDecimal getAmountOverdue() {
        return amountOverdue;
    }

    public void setAmountOverdue(BigDecimal amountOverdue) {
        this.amountOverdue = amountOverdue;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInstallmentamt() {
        return installmentamt;
    }

    public void setInstallmentamt(BigDecimal installmentamt) {
        this.installmentamt = installmentamt;
    }

    public BigDecimal getRepaymentAmt() {
        return repaymentAmt;
    }

    public void setRepaymentAmt(BigDecimal repaymentAmt) {
        this.repaymentAmt = repaymentAmt;
    }

    public BigDecimal getSanctionedamt() {
        return sanctionedamt;
    }

    public void setSanctionedamt(BigDecimal sanctionedamt) {
        this.sanctionedamt = sanctionedamt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getLastRecDt() {
        return lastRecDt;
    }

    public void setLastRecDt(String lastRecDt) {
        this.lastRecDt = lastRecDt;
    }

    public int getNoOfEmiOverdue() {
        return noOfEmiOverdue;
    }

    public void setNoOfEmiOverdue(int noOfEmiOverdue) {
        this.noOfEmiOverdue = noOfEmiOverdue;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getFolioName() {
        return folioName;
    }

    public void setFolioName(String folioName) {
        this.folioName = folioName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public boolean isCheckBoxFlag() {
        return checkBoxFlag;
    }

    public void setCheckBoxFlag(boolean checkBoxFlag) {
        this.checkBoxFlag = checkBoxFlag;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public double getNoOfActualEmiOverdue() {
        return noOfActualEmiOverdue;
    }

    public void setNoOfActualEmiOverdue(double noOfActualEmiOverdue) {
        this.noOfActualEmiOverdue = noOfActualEmiOverdue;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getBranchState() {
        return branchState;
    }

    public void setBranchState(String branchState) {
        this.branchState = branchState;
    }

    public String getMoratoriumFlag() {
        return moratoriumFlag;
    }

    public void setMoratoriumFlag(String moratoriumFlag) {
        this.moratoriumFlag = moratoriumFlag;
    }

    public String getMoratoriumOnOf() {
        return moratoriumOnOf;
    }

    public void setMoratoriumOnOf(String moratoriumOnOf) {
        this.moratoriumOnOf = moratoriumOnOf;
    }

    public String getSurety1Id() {
        return surety1Id;
    }

    public void setSurety1Id(String surety1Id) {
        this.surety1Id = surety1Id;
    }

    public String getSurety1Name() {
        return surety1Name;
    }

    public void setSurety1Name(String surety1Name) {
        this.surety1Name = surety1Name;
    }

    public String getSurety1Add() {
        return surety1Add;
    }

    public void setSurety1Add(String surety1Add) {
        this.surety1Add = surety1Add;
    }

    public String getSurety2Id() {
        return surety2Id;
    }

    public void setSurety2Id(String surety2Id) {
        this.surety2Id = surety2Id;
    }

    public String getSurety2Name() {
        return surety2Name;
    }

    public void setSurety2Name(String surety2Name) {
        this.surety2Name = surety2Name;
    }

    public String getSurety2Add() {
        return surety2Add;
    }

    public void setSurety2Add(String surety2Add) {
        this.surety2Add = surety2Add;
    }

    public String getSurety3Id() {
        return surety3Id;
    }

    public void setSurety3Id(String surety3Id) {
        this.surety3Id = surety3Id;
    }

    public String getSurety3Name() {
        return surety3Name;
    }

    public void setSurety3Name(String surety3Name) {
        this.surety3Name = surety3Name;
    }

    public String getSurety3Add() {
        return surety3Add;
    }

    public void setSurety3Add(String surety3Add) {
        this.surety3Add = surety3Add;
    }

    public String getCustAdd() {
        return custAdd;
    }

    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getNpaBalance() {
        return npaBalance;
    }

    public void setNpaBalance(BigDecimal npaBalance) {
        this.npaBalance = npaBalance;
    }

    public BigDecimal getPenalIntCharged() {
        return penalIntCharged;
    }

    public void setPenalIntCharged(BigDecimal penalIntCharged) {
        this.penalIntCharged = penalIntCharged;
    }

    public BigDecimal getPenalReceivable() {
        return penalReceivable;
    }

    public void setPenalReceivable(BigDecimal penalReceivable) {
        this.penalReceivable = penalReceivable;
    }

    public String getJoinName() {
        return joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
    
    
       
}
