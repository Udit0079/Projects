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
public class TransactionDetailsPojo {

    private String ref_desc;
    private String beneficiaryName;
    private BigDecimal txnAmt;
    private String paymentDate;
    private String debitAccountNo;
    private String creditAccountNo;
    private String status;
    private String details;
    private String cmsBankRefNo;
    private String utrNo;
    private String reason;
    private String dt;
    private String ifscCode;
    private String uniqueCustRefNo;
    private String rrno;

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRef_desc() {
        return ref_desc;
    }

    public void setRef_desc(String ref_desc) {
        this.ref_desc = ref_desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getCmsBankRefNo() {
        return cmsBankRefNo;
    }

    public void setCmsBankRefNo(String cmsBankRefNo) {
        this.cmsBankRefNo = cmsBankRefNo;
    }

    public String getCreditAccountNo() {
        return creditAccountNo;
    }

    public void setCreditAccountNo(String creditAccountNo) {
        this.creditAccountNo = creditAccountNo;
    }

    public String getDebitAccountNo() {
        return debitAccountNo;
    }

    public void setDebitAccountNo(String debitAccountNo) {
        this.debitAccountNo = debitAccountNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getUniqueCustRefNo() {
        return uniqueCustRefNo;
    }

    public void setUniqueCustRefNo(String uniqueCustRefNo) {
        this.uniqueCustRefNo = uniqueCustRefNo;
    }

    public String getRrno() {
        return rrno;
    }

    public void setRrno(String rrno) {
        this.rrno = rrno;
    }
        
}
