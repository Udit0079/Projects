/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class PostDatedChequePojo implements Serializable {

    private String acnoCredit;
    private String custName;
    private String acnoDebit;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String chequeNo;
    private String chequeDt;
    private double amount;
    private String chqStatus;

    public String getAcnoCredit() {
        return acnoCredit;
    }

    public void setAcnoCredit(String acnoCredit) {
        this.acnoCredit = acnoCredit;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAcnoDebit() {
        return acnoDebit;
    }

    public void setAcnoDebit(String acnoDebit) {
        this.acnoDebit = acnoDebit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeDt() {
        return chequeDt;
    }

    public void setChequeDt(String chequeDt) {
        this.chequeDt = chequeDt;
    }

 

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getChqStatus() {
        return chqStatus;
    }

    public void setChqStatus(String chqStatus) {
        this.chqStatus = chqStatus;
    }
    
    
}
