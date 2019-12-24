/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class HDFCInwardNEFTReturnPojo {
    private String transactionRefNo;
    private BigDecimal amount;
    private String valueDate;
    private String branchCode;
    private String senderAccountType;
    private String remitterAccountNo;
    private String remitterName;
    private String iFSCCode;
    private String debitAccount;
    private String beneficiaryAccounttype;
    private String bankAccountNumber;
    private String beneficiaryName;
    private String remittanceDetails;
    private String debitAccountSystem;
    private String originatorOfRemmittance;
    private String emailIDMobnumber;        

    public String getTransactionRefNo() {
        return transactionRefNo;
    }

    public void setTransactionRefNo(String transactionRefNo) {
        this.transactionRefNo = transactionRefNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getSenderAccountType() {
        return senderAccountType;
    }

    public void setSenderAccountType(String senderAccountType) {
        this.senderAccountType = senderAccountType;
    }

    public String getRemitterAccountNo() {
        return remitterAccountNo;
    }

    public void setRemitterAccountNo(String remitterAccountNo) {
        this.remitterAccountNo = remitterAccountNo;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    public String getiFSCCode() {
        return iFSCCode;
    }

    public void setiFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getBeneficiaryAccounttype() {
        return beneficiaryAccounttype;
    }

    public void setBeneficiaryAccounttype(String beneficiaryAccounttype) {
        this.beneficiaryAccounttype = beneficiaryAccounttype;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getRemittanceDetails() {
        return remittanceDetails;
    }

    public void setRemittanceDetails(String remittanceDetails) {
        this.remittanceDetails = remittanceDetails;
    }

    public String getDebitAccountSystem() {
        return debitAccountSystem;
    }

    public void setDebitAccountSystem(String debitAccountSystem) {
        this.debitAccountSystem = debitAccountSystem;
    }

    public String getOriginatorOfRemmittance() {
        return originatorOfRemmittance;
    }

    public void setOriginatorOfRemmittance(String originatorOfRemmittance) {
        this.originatorOfRemmittance = originatorOfRemmittance;
    }

    public String getEmailIDMobnumber() {
        return emailIDMobnumber;
    }

    public void setEmailIDMobnumber(String emailIDMobnumber) {
        this.emailIDMobnumber = emailIDMobnumber;
    }
}
