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
public class DemandOefPojo implements Serializable {

    private String personalNo;
    private String custId;
    private String acNo;
    private String custName;
    private double ltAmt;
    private double ltInt;
    private double stAmt;
    private double stInt;
    private double loanEmi;
    private double rdAmt;
    private double ltdmsAmt;
    private double surityAmt;
    private double totalDemand;
    private double balance;
    private int sNo;

    public String getPersonalNo() {
        return personalNo;
    }

    public void setPersonalNo(String personalNo) {
        this.personalNo = personalNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

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

    public double getLtAmt() {
        return ltAmt;
    }

    public void setLtAmt(double ltAmt) {
        this.ltAmt = ltAmt;
    }

    public double getLtInt() {
        return ltInt;
    }

    public void setLtInt(double ltInt) {
        this.ltInt = ltInt;
    }

    public double getStAmt() {
        return stAmt;
    }

    public void setStAmt(double stAmt) {
        this.stAmt = stAmt;
    }

    public double getStInt() {
        return stInt;
    }

    public void setStInt(double stInt) {
        this.stInt = stInt;
    }

    public double getRdAmt() {
        return rdAmt;
    }

    public void setRdAmt(double rdAmt) {
        this.rdAmt = rdAmt;
    }

    public double getLtdmsAmt() {
        return ltdmsAmt;
    }

    public void setLtdmsAmt(double ltdmsAmt) {
        this.ltdmsAmt = ltdmsAmt;
    }

    public double getSurityAmt() {
        return surityAmt;
    }

    public void setSurityAmt(double surityAmt) {
        this.surityAmt = surityAmt;
    }

    public double getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(double totalDemand) {
        this.totalDemand = totalDemand;
    }

    public double getLoanEmi() {
        return loanEmi;
    }

    public void setLoanEmi(double loanEmi) {
        this.loanEmi = loanEmi;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
     
}
