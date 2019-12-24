/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class ManualInwardPojo {

    private Integer sno;
    private String accountNo;
    private String custName;
    private String instNo;
    private String instDt;
    private BigDecimal amount;
    private String inFavourof;
    private String bankName;
    private String branchName;
    private String prBankCode;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
    
    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getPrBankCode() {
        return prBankCode;
    }

    public void setPrBankCode(String prBankCode) {
        this.prBankCode = prBankCode;
    }

    public String getInFavourof() {
        return inFavourof;
    }

    public void setInFavourof(String inFavourof) {
        this.inFavourof = inFavourof;
    }
}
