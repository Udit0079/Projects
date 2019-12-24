package com.cbs.dto.report;

import java.math.BigDecimal;

public class OutwardClearingBankwiseReportPojo implements java.io.Serializable{

    private String bnkName;
    private String bnkAdd;
    private int brncode;
    private String bankName;
    private String bankAddress;
    private int instNo;
    private BigDecimal instAmount;
    private String status;

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

//    public double getInstAmount() {
//        return instAmount;
//    }
//
//    public void setInstAmount(double instAmount) {
//        this.instAmount = instAmount;
//    }

//    public String getInstNo() {
//        return instNo;
//    }
//
//    public void setInstNo(String instNo) {
//        this.instNo = instNo;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getBrncode() {
        return brncode;
    }

    public void setBrncode(int brncode) {
        this.brncode = brncode;
    }

    public BigDecimal getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(BigDecimal instAmount) {
        this.instAmount = instAmount;
    }

    public int getInstNo() {
        return instNo;
    }

    public void setInstNo(int instNo) {
        this.instNo = instNo;
    }
    
}
