/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.util.Date;

/**
 *
 * @author ANKIT VERMA
 */
public class ActiveRdReportPojo {
    private String acno;
    private String custName;
    private double rdInstal;
    private float intDeposit;
    private Date openingDt;
    private int period;
    private String rdMatDate;
    private short accStatus;
    private String closingDate;
    private String custId;
    private double balance;
    private double intAmt;
    

    public short getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(short accStatus) {
        this.accStatus = accStatus;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public float getIntDeposit() {
        return intDeposit;
    }

    public void setIntDeposit(float intDeposit) {
        this.intDeposit = intDeposit;
    }

    public Date getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(Date openingDt) {
        this.openingDt = openingDt;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getRdInstal() {
        return rdInstal;
    }

    public void setRdInstal(double rdInstal) {
        this.rdInstal = rdInstal;
    }

    public String getRdMatDate() {
        return rdMatDate;
    }

    public void setRdMatDate(String rdMatDate) {
        this.rdMatDate = rdMatDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(double intAmt) {
        this.intAmt = intAmt;
    }
      
}
