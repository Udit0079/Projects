/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class FolioStatement {

    private String folioNo;
    private String acNo;
    private String custName;
    private String txnDt;
    private String area;
    private String lastDisburseDt;
    private String particular;
    private BigDecimal lastDisburseAmt;
    private BigDecimal creditAmt;
    private BigDecimal debitAmt;
    private BigDecimal openingBalance;
    private BigDecimal balance;
    private BigDecimal demandAmt;
    private BigDecimal opningOverdueAmt;
    private BigDecimal overdueAmt;
    private String acNature;
    private BigDecimal totalDemand;
    private double memorandumBal;
    private String typeFlag="";
    private BigDecimal npaOpeningBalance;

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
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

    public String getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(String txnDt) {
        this.txnDt = txnDt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLastDisburseDt() {
        return lastDisburseDt;
    }

    public void setLastDisburseDt(String lastDisburseDt) {
        this.lastDisburseDt = lastDisburseDt;
    }

    public BigDecimal getLastDisburseAmt() {
        return lastDisburseAmt;
    }

    public void setLastDisburseAmt(BigDecimal lastDisburseAmt) {
        this.lastDisburseAmt = lastDisburseAmt;
    }

    public BigDecimal getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(BigDecimal creditAmt) {
        this.creditAmt = creditAmt;
    }

    public BigDecimal getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(BigDecimal debitAmt) {
        this.debitAmt = debitAmt;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getDemandAmt() {
        return demandAmt;
    }

    public void setDemandAmt(BigDecimal demandAmt) {
        this.demandAmt = demandAmt;
    }

    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public BigDecimal getOpningOverdueAmt() {
        return opningOverdueAmt;
    }

    public void setOpningOverdueAmt(BigDecimal opningOverdueAmt) {
        this.opningOverdueAmt = opningOverdueAmt;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public BigDecimal getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(BigDecimal totalDemand) {
        this.totalDemand = totalDemand;
    }

    public double getMemorandumBal() {
        return memorandumBal;
    }

    public void setMemorandumBal(double memorandumBal) {
        this.memorandumBal = memorandumBal;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public BigDecimal getNpaOpeningBalance() {
        return npaOpeningBalance;
    }

    public void setNpaOpeningBalance(BigDecimal npaOpeningBalance) {
        this.npaOpeningBalance = npaOpeningBalance;
    }
         
}
