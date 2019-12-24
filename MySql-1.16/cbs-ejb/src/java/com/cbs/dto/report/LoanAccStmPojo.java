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
 * @author admin
 */
public class LoanAccStmPojo implements Serializable, Comparable<LoanAccStmPojo> {

    private String acNo, custName, acType, jtName, nomination, crAdd, prAdd, particulars, chequeno, rdMatDate, acNature, accStatus, openingDt,closingDate;
    private Date date, valueDt, opDate, opValueDt;
    private float roi;
    private BigDecimal withDrawl, deposit, balance, pendBal, openBal, amtSanc, amtInst, odLimit, rdMatAmt, totalBalance;
    private BigDecimal pCredit, pDebit, pBalance, intCredit, intDebit, intBalance, prinOpBal, intOpBal;
    private int period;

    public BigDecimal getpCredit() {
        return pCredit;
    }

    public void setpCredit(BigDecimal pCredit) {
        this.pCredit = pCredit;
    }

    public BigDecimal getpDebit() {
        return pDebit;
    }

    public void setpDebit(BigDecimal pDebit) {
        this.pDebit = pDebit;
    }

    public BigDecimal getpBalance() {
        return pBalance;
    }

    public void setpBalance(BigDecimal pBalance) {
        this.pBalance = pBalance;
    }

    public BigDecimal getIntCredit() {
        return intCredit;
    }

    public void setIntCredit(BigDecimal intCredit) {
        this.intCredit = intCredit;
    }

    public BigDecimal getIntDebit() {
        return intDebit;
    }

    public void setIntDebit(BigDecimal intDebit) {
        this.intDebit = intDebit;
    }

    public BigDecimal getIntBalance() {
        return intBalance;
    }

    public void setIntBalance(BigDecimal intBalance) {
        this.intBalance = intBalance;
    }

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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public BigDecimal getAmtInst() {
        return amtInst;
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

    public BigDecimal getWithDrawl() {
        return withDrawl;
    }

    public void setWithDrawl(BigDecimal withDrawl) {
        this.withDrawl = withDrawl;
    }

    public int compareTo(LoanAccStmPojo pojo) {
        return this.date.compareTo(pojo.date);
    }

    public Date getValueDt() {
        return valueDt;
    }

    public void setValueDt(Date valueDt) {
        this.valueDt = valueDt;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getPrinOpBal() {
        return prinOpBal;
    }

    public void setPrinOpBal(BigDecimal prinOpBal) {
        this.prinOpBal = prinOpBal;
    }

    public BigDecimal getIntOpBal() {
        return intOpBal;
    }

    public void setIntOpBal(BigDecimal intOpBal) {
        this.intOpBal = intOpBal;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public Date getOpValueDt() {
        return opValueDt;
    }

    public void setOpValueDt(Date opValueDt) {
        this.opValueDt = opValueDt;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }
       
}
