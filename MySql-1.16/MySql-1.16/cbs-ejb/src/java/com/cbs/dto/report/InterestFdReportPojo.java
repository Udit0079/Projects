/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author sipl
 */
public class InterestFdReportPojo {
    
    private String custId;
    private String custName;
    private String acNo;
    private String fatherName;
    private String dob;
    private String panNo;
    private String perAddr;
    private BigDecimal prnAmt;
    private String depDate;
    private BigDecimal intAmt;
    private BigDecimal tdsAmt;
    private BigDecimal exemptionAmt;
    private String tdsDate;
    private String intPayDate;
    private String recptNo;
    private String mobile;
    private String docType;
    private String voucherNo;
    
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal IntAmt) {
        this.intAmt = IntAmt;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPerAddr() {
        return perAddr;
    }

    public void setPerAddr(String perAddr) {
        this.perAddr = perAddr;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getIntPayDate() {
        return intPayDate;
    }

    public void setIntPayDate(String intPayDate) {
        this.intPayDate = intPayDate;
    }

    public BigDecimal getPrnAmt() {
        return prnAmt;
    }

    public void setPrnAmt(BigDecimal prnAmt) {
        this.prnAmt = prnAmt;
    }

    public String getRecptNo() {
        return recptNo;
    }

    public void setRecptNo(String recptNo) {
        this.recptNo = recptNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getTdsAmt() {
        return tdsAmt;
    }

    public void setTdsAmt(BigDecimal tdsAmt) {
        this.tdsAmt = tdsAmt;
    }

    public String getTdsDate() {
        return tdsDate;
    }

    public void setTdsDate(String tdsDate) {
        this.tdsDate = tdsDate;
    }

    public BigDecimal getExemptionAmt() {
        return exemptionAmt;
    }

    public void setExemptionAmt(BigDecimal exemptionAmt) {
        this.exemptionAmt = exemptionAmt;
    }
 
}