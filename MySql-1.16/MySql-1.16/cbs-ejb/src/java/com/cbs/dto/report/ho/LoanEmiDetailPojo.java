/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class LoanEmiDetailPojo implements Serializable{
    private String acNo;
    private String name;
    private String duedtOfemi;
    private String noOfemiDue;
    private double emiAmt;
    private double outstandBal;
    private String accountType;
    private String acTypeDesc;
    private String loanPd;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getLoanPd() {
        return loanPd;
    }

    public void setLoanPd(String loanPd) {
        this.loanPd = loanPd;
    }
    
    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }
    
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getDuedtOfemi() {
        return duedtOfemi;
    }

    public void setDuedtOfemi(String duedtOfemi) {
        this.duedtOfemi = duedtOfemi;
    }

    public double getEmiAmt() {
        return emiAmt;
    }

    public void setEmiAmt(double emiAmt) {
        this.emiAmt = emiAmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoOfemiDue() {
        return noOfemiDue;
    }

    public void setNoOfemiDue(String noOfemiDue) {
        this.noOfemiDue = noOfemiDue;
    }

    public double getOutstandBal() {
        return outstandBal;
    }

    public void setOutstandBal(double outstandBal) {
        this.outstandBal = outstandBal;
    }
    
}
