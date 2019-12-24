/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class RbiSoss1AnnexI2 {

    String bankName;
    BigDecimal depositAmt;
    BigDecimal roi;
    String dateOfReceipt;
    String pdInDays;
    String pdInMonth;
    String pdInYears;
    String matDt;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(String dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public BigDecimal getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(BigDecimal depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public String getPdInDays() {
        return pdInDays;
    }

    public void setPdInDays(String pdInDays) {
        this.pdInDays = pdInDays;
    }

    public String getPdInMonth() {
        return pdInMonth;
    }

    public void setPdInMonth(String pdInMonth) {
        this.pdInMonth = pdInMonth;
    }

    public String getPdInYears() {
        return pdInYears;
    }

    public void setPdInYears(String pdInYears) {
        this.pdInYears = pdInYears;
    }

    

    public BigDecimal getRoi() {
        return roi;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi;
    }
}
