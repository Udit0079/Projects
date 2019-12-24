/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.bill;

/**
 *
 * @author Admin
 */
public class RealOutwardBillGrid {

    int sNo;
    String billNo;
    String billType;
    String fYear;
    String acNo;
    String instNo;
    double instAmt;
    String instDate;
    String colBankName;
    String colBankAdd;
    String enterBy;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getColBankAdd() {
        return colBankAdd;
    }

    public void setColBankAdd(String colBankAdd) {
        this.colBankAdd = colBankAdd;
    }

    public String getColBankName() {
        return colBankName;
    }

    public void setColBankName(String colBankName) {
        this.colBankName = colBankName;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(String fYear) {
        this.fYear = fYear;
    }

    public double getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(double instAmt) {
        this.instAmt = instAmt;
    }

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

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
}
