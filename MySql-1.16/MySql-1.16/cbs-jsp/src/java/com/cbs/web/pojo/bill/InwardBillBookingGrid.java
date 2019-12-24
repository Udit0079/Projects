/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.bill;

/**
 *
 * @author Admin
 */
public class InwardBillBookingGrid {

    int sNo;
    String acNo;
    String instNo;
    double instAmt;
    String instDt;
    String payableAt;
    String inFavOf;
    String bankName;
    String bankAddress;
    String billType;
    String remType;
    String enterBy;

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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getInFavOf() {
        return inFavOf;
    }

    public void setInFavOf(String inFavOf) {
        this.inFavOf = inFavOf;
    }

    public double getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(double instAmt) {
        this.instAmt = instAmt;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getPayableAt() {
        return payableAt;
    }

    public void setPayableAt(String payableAt) {
        this.payableAt = payableAt;
    }

    public String getRemType() {
        return remType;
    }

    public void setRemType(String remType) {
        this.remType = remType;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
}
