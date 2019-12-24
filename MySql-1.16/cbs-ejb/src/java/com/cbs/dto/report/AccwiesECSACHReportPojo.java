/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

public class AccwiesECSACHReportPojo {

    private String umrn;
    private String destBankName;
    private String destAccNo;
    private BigDecimal amount;
    private String frequency;
    private String txnDate;
    private String txnFileGenDate;
    private String status;
    private String reason;

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getDestBankName() {
        return destBankName;
    }

    public void setDestBankName(String destBankName) {
        this.destBankName = destBankName;
    }

    public String getDestAccNo() {
        return destAccNo;
    }

    public void setDestAccNo(String destAccNo) {
        this.destAccNo = destAccNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnFileGenDate() {
        return txnFileGenDate;
    }

    public void setTxnFileGenDate(String txnFileGenDate) {
        this.txnFileGenDate = txnFileGenDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
