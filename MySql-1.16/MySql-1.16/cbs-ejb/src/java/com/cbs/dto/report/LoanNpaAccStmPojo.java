/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author root
 */
public class LoanNpaAccStmPojo implements Serializable {

    private String acNo, custName, acType, jtName, nomination, crAdd, prAdd, particulars, chequeno, type, rdMatDate, acNature,openingDt,closingDate;
    private Date date, valueDt;
    private float roi;
    private BigDecimal withDrawl, deposit, balance, pendBal, openBal, amtSanc, amtInst, odLimit, rdMatAmt;
    private int period;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public BigDecimal getAmtInst() {
        return amtInst;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public void setAmtInst(BigDecimal amtInst) {
        this.amtInst = amtInst;
    }

    public BigDecimal getAmtSanc() {
        return amtSanc;
    }

    public void setAmtSanc(BigDecimal amtSanc) {
        this.amtSanc = amtSanc;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getChequeno() {
        return chequeno;
    }

    public void setChequeno(String chequeno) {
        this.chequeno = chequeno;
    }

    public String getCrAdd() {
        return crAdd;
    }

    public void setCrAdd(String crAdd) {
        this.crAdd = crAdd;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public BigDecimal getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(BigDecimal odLimit) {
        this.odLimit = odLimit;
    }

    public BigDecimal getOpenBal() {
        return openBal;
    }

    public void setOpenBal(BigDecimal openBal) {
        this.openBal = openBal;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public BigDecimal getPendBal() {
        return pendBal;
    }

    public void setPendBal(BigDecimal pendBal) {
        this.pendBal = pendBal;
    }

    public String getPrAdd() {
        return prAdd;
    }

    public void setPrAdd(String prAdd) {
        this.prAdd = prAdd;
    }

    public BigDecimal getRdMatAmt() {
        return rdMatAmt;
    }

    public void setRdMatAmt(BigDecimal rdMatAmt) {
        this.rdMatAmt = rdMatAmt;
    }

    public String getRdMatDate() {
        return rdMatDate;
    }

    public void setRdMatDate(String rdMatDate) {
        this.rdMatDate = rdMatDate;
    }

    public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

    public Date getValueDt() {
        return valueDt;
    }

    public void setValueDt(Date valueDt) {
        this.valueDt = valueDt;
    }

    public BigDecimal getWithDrawl() {
        return withDrawl;
    }

    public void setWithDrawl(BigDecimal withDrawl) {
        this.withDrawl = withDrawl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    } 

    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }
    
       
}
