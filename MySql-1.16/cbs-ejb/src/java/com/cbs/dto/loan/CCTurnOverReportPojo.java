/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CCTurnOverReportPojo {

    private String acno;
    private String acholdername;
    private String renewalDate;
    private BigDecimal sanctionLimit;
    private BigDecimal outstandingBalance;
    private BigDecimal debitTurnOver;
    private BigDecimal creditTurnOver;
    private String lastCreditDate;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcholdername() {
        return acholdername;
    }

    public void setAcholdername(String acholdername) {
        this.acholdername = acholdername;
    }

    public String getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(String renewalDate) {
        this.renewalDate = renewalDate;
    }

    public BigDecimal getSanctionLimit() {
        return sanctionLimit;
    }

    public void setSanctionLimit(BigDecimal sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public BigDecimal getDebitTurnOver() {
        return debitTurnOver;
    }

    public void setDebitTurnOver(BigDecimal debitTurnOver) {
        this.debitTurnOver = debitTurnOver;
    }

    public BigDecimal getCreditTurnOver() {
        return creditTurnOver;
    }

    public void setCreditTurnOver(BigDecimal creditTurnOver) {
        this.creditTurnOver = creditTurnOver;
    }

    public String getLastCreditDate() {
        return lastCreditDate;
    }

    public void setLastCreditDate(String lastCreditDate) {
        this.lastCreditDate = lastCreditDate;
    }
}
