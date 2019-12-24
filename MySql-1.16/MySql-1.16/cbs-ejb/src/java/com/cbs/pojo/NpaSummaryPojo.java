/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author root
 */
public class NpaSummaryPojo {

    private String acno;
    private BigDecimal balance;
    private long npaDays;
    private Date npaDate;
    private String npaType;         //D1, D2, D3  (In case of DOUBTFUL)
    private String npaTypeSecurity; //D1(Secured/Unsecured)
    private String npa;             //STANDARD, DOUBTFUL, LOSS
    private String remark;
    private String productCode;
    private String accountDesc;

    public String getAccountDesc() {
        return accountDesc;
    }

    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }
    
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getNpaDays() {
        return npaDays;
    }

    public void setNpaDays(long npaDays) {
        this.npaDays = npaDays;
    }

    public String getNpa() {
        return npa;
    }

    public void setNpa(String npa) {
        this.npa = npa;
    }

    public Date getNpaDate() {
        return npaDate;
    }

    public void setNpaDate(Date npaDate) {
        this.npaDate = npaDate;
    }

    public String getNpaType() {
        return npaType;
    }

    public void setNpaType(String npaType) {
        this.npaType = npaType;
    }

    public String getNpaTypeSecurity() {
        return npaTypeSecurity;
    }

    public void setNpaTypeSecurity(String npaTypeSecurity) {
        this.npaTypeSecurity = npaTypeSecurity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
