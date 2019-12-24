/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Athar Reza
 */
public class TdNewAndReNewPojo implements Serializable{
    private String acNo;
    private String custName;
    private String rtNo;
    private String receiptNo;
    private String controlNo; 
    private double prinAmt;
    private String intOpt;
    private String tdDtWef;
    private double roi;
    private String prevRtNo;
    private double prevPrinAmt;
    
    private String year;
    private String month;
    private String day;
    
    private String period;
    private String matDt;
    private String paymentDt;
    private String printLabel;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }
   
    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getPrevPrinAmt() {
        return prevPrinAmt;
    }

    public void setPrevPrinAmt(double prevPrinAmt) {
        this.prevPrinAmt = prevPrinAmt;
    }

    public String getPrevRtNo() {
        return prevRtNo;
    }

    public void setPrevRtNo(String prevRtNo) {
        this.prevRtNo = prevRtNo;
    }

    public double getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public String getTdDtWef() {
        return tdDtWef;
    }

    public void setTdDtWef(String tdDtWef) {
        this.tdDtWef = tdDtWef;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPaymentDt() {
        return paymentDt;
    }

    public void setPaymentDt(String paymentDt) {
        this.paymentDt = paymentDt;
    }

    public String getPrintLabel() {
        return printLabel;
    }

    public void setPrintLabel(String printLabel) {
        this.printLabel = printLabel;
    }
          
}
