package com.cbs.dto.report;

import java.util.Date;

public class ExceptionReportPojo implements java.io.Serializable {

    private String header;
    private String acNo;
    private String custName;
    private double odLimit;
    private double debitAmt;
    private double balance;
    private String enterBy;
    private String authBy;
    private String billType;
    private String details;
    private String instNo;
    private double instAmount;
    private Date instEntryDt;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(double debitAmt) {
        this.debitAmt = debitAmt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public double getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(double instAmount) {
        this.instAmount = instAmount;
    }

    public Date getInstEntryDt() {
        return instEntryDt;
    }

    public void setInstEntryDt(Date instEntryDt) {
        this.instEntryDt = instEntryDt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public double getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(double odLimit) {
        this.odLimit = odLimit;
    }
}
