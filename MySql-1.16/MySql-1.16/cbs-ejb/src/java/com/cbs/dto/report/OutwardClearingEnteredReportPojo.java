package com.cbs.dto.report;

import java.math.BigDecimal;

public class OutwardClearingEnteredReportPojo implements java.io.Serializable {

    private String bnkName;
    private String bnkAdd;
    private int brncode;
    private String actype;
    private int sNo;
    private String acNo;
    private String custName;
    private int voucherNo;
    private String bankName;
    private String bankAddress;
    private String instNo;
    private String instDate;
    private BigDecimal instAmount;
    private BigDecimal vchAmount;
    private String status;
    private String micrCode;
    private String reasons;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

//    public double getVchAmount() {
//        return vchAmount;
//    }
//
//    public void setVchAmount(double vchAmount) {
//        this.vchAmount = vchAmount;
//    }
    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

//    public double getInstAmount() {
//        return instAmount;
//    }
//
//    public void setInstAmount(double instAmount) {
//        this.instAmount = instAmount;
//    }
    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(int voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getBnkAdd() {
        return bnkAdd;
    }

    public void setBnkAdd(String bnkAdd) {
        this.bnkAdd = bnkAdd;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public BigDecimal getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(BigDecimal instAmount) {
        this.instAmount = instAmount;
    }

    public BigDecimal getVchAmount() {
        return vchAmount;
    }

    public void setVchAmount(BigDecimal vchAmount) {
        this.vchAmount = vchAmount;
    }

    public int getBrncode() {
        return brncode;
    }

    public void setBrncode(int brncode) {
        this.brncode = brncode;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
