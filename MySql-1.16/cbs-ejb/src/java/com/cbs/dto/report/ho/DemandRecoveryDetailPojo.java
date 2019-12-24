/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class DemandRecoveryDetailPojo {

    private String custId;
    private String acno;
    private String name;
    private BigDecimal emiAmt;
    private BigDecimal overdueAmt;
    private BigDecimal outstandingBalance;
    private BigDecimal totaldueAmt;
    private BigDecimal recoveryAmt;
    private BigDecimal remainingBal;
    private BigDecimal stdRecovery;
    private BigDecimal npaRecovery;
    private BigDecimal totalDeposit;
    private BigDecimal npaBal;
    private String creditDate;
    private String status;
    private String statusflag;

    public String getStatusflag() {
        return statusflag;
    }

    public void setStatusflag(String statusflag) {
        this.statusflag = statusflag;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public BigDecimal getTotaldueAmt() {
        return totaldueAmt;
    }

    public void setTotaldueAmt(BigDecimal totaldueAmt) {
        this.totaldueAmt = totaldueAmt;
    }
 
    public BigDecimal getNpaBal() {
        return npaBal;
    }

    public void setNpaBal(BigDecimal npaBal) {
        this.npaBal = npaBal;
    }

    public String getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(String creditDate) {
        this.creditDate = creditDate;
    }
   
    
    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
    
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getEmiAmt() {
        return emiAmt;
    }

    public void setEmiAmt(BigDecimal emiAmt) {
        this.emiAmt = emiAmt;
    }

    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

 

    public BigDecimal getRecoveryAmt() {
        return recoveryAmt;
    }

    public void setRecoveryAmt(BigDecimal recoveryAmt) {
        this.recoveryAmt = recoveryAmt;
    }

    public BigDecimal getRemainingBal() {
        return remainingBal;
    }

    public void setRemainingBal(BigDecimal remainingBal) {
        this.remainingBal = remainingBal;
    }

    public BigDecimal getStdRecovery() {
        return stdRecovery;
    }

    public void setStdRecovery(BigDecimal stdRecovery) {
        this.stdRecovery = stdRecovery;
    }

    public BigDecimal getNpaRecovery() {
        return npaRecovery;
    }

    public void setNpaRecovery(BigDecimal npaRecovery) {
        this.npaRecovery = npaRecovery;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }
}
