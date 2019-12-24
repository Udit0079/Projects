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
public class LoanRenewalSecurityPojo implements Serializable{
    private String acNo;
    private String custName;
    private String security;
    private double roi;
    private double sansAmt;
    private double balance;
    private String sansDt;
    private String renewalDt;
    private String securityDt;
    private String renewal;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getSansAmt() {
        return sansAmt;
    }

    public void setSansAmt(double sansAmt) {
        this.sansAmt = sansAmt;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSansDt() {
        return sansDt;
    }

    public void setSansDt(String sansDt) {
        this.sansDt = sansDt;
    }

    public String getRenewalDt() {
        return renewalDt;
    }

    public void setRenewalDt(String renewalDt) {
        this.renewalDt = renewalDt;
    }

    public String getSecurityDt() {
        return securityDt;
    }

    public void setSecurityDt(String securityDt) {
        this.securityDt = securityDt;
    }

    public String getRenewal() {
        return renewal;
    }

    public void setRenewal(String renewal) {
        this.renewal = renewal;
    }
    
    
}
