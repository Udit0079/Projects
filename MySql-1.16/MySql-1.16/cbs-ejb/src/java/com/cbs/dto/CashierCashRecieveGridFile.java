/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author ROHIT KRISHNA
 */
public class CashierCashRecieveGridFile {

    public int sNo;
    public String voucherNo;
    public String acno;
    public String custName;
    public String jointName;
    public String transaction;
    public String amount;
    public String enterBy;
    public String cashRecieved;
    public String tokenNo;
    public String tokenPaid;
    public String tokenPaidBy;
    public String recNo;
    private String details;
    private String orgnCode;
    private String destCode;
    private String balBeforeInst;
    
    private String viewDetails;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
    
    public String getBalBeforeInst() {
        return balBeforeInst;
    }

    public void setBalBeforeInst(String balBeforeInst) {
        this.balBeforeInst = balBeforeInst;
    }

    public String getOrgnCode() {
        return orgnCode;
    }

    public void setOrgnCode(String orgnCode) {
        this.orgnCode = orgnCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getViewDetails() {
        return viewDetails;
    }

    public void setViewDetails(String viewDetails) {
        this.viewDetails = viewDetails;
    }
    
    public String getRecNo() {
        return recNo;
    }

    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCashRecieved() {
        return cashRecieved;
    }

    public void setCashRecieved(String cashRecieved) {
        this.cashRecieved = cashRecieved;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getTokenPaid() {
        return tokenPaid;
    }

    public void setTokenPaid(String tokenPaid) {
        this.tokenPaid = tokenPaid;
    }

    public String getTokenPaidBy() {
        return tokenPaidBy;
    }

    public void setTokenPaidBy(String tokenPaidBy) {
        this.tokenPaidBy = tokenPaidBy;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getJointName() {
        return jointName;
    }

    public void setJointName(String jointName) {
        this.jointName = jointName;
    }
    
}
