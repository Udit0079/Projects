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
public class ArfTrnPojo {
    
    Integer lineNumber;
    String reportSerialNum;
    String branchRefNum;
    String accountNumber;
    String dateOfTransaction;
    String transactionId;
    String transactionMode;
    String debitCredit;
    BigDecimal amount;
    String currency;
    String productType;
    String identifier;
    String transactionType;
    BigDecimal units;
    BigDecimal rate;
    String dispositionOfFunds;
    String relatedAccountNum;
    String relatedInstitutionName;
    String relatedInstitutionRefNum;
    String remarks;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBranchRefNum() {
        return branchRefNum;
    }

    public void setBranchRefNum(String branchRefNum) {
        this.branchRefNum = branchRefNum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(String debitCredit) {
        this.debitCredit = debitCredit;
    }

    public String getDispositionOfFunds() {
        return dispositionOfFunds;
    }

    public void setDispositionOfFunds(String dispositionOfFunds) {
        this.dispositionOfFunds = dispositionOfFunds;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRelatedAccountNum() {
        return relatedAccountNum;
    }

    public void setRelatedAccountNum(String relatedAccountNum) {
        this.relatedAccountNum = relatedAccountNum;
    }

    public String getRelatedInstitutionName() {
        return relatedInstitutionName;
    }

    public void setRelatedInstitutionName(String relatedInstitutionName) {
        this.relatedInstitutionName = relatedInstitutionName;
    }

    public String getRelatedInstitutionRefNum() {
        return relatedInstitutionRefNum;
    }

    public void setRelatedInstitutionRefNum(String relatedInstitutionRefNum) {
        this.relatedInstitutionRefNum = relatedInstitutionRefNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReportSerialNum() {
        return reportSerialNum;
    }

    public void setReportSerialNum(String reportSerialNum) {
        this.reportSerialNum = reportSerialNum;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }
    
    

}
