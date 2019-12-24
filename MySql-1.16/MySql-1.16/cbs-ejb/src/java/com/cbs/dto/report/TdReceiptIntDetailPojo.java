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
public class TdReceiptIntDetailPojo implements Serializable {    
    private String custId;
    private String acNo;
    private String receiptNo;
    private String date;
    private double interest;
    private double tds;
    private double tdsAmount;
    private String tdsPostingDt;
    private String recoveredFlag;
    private double recoveredTdsAmt;
    private String recoveredDt;
    private String recoveredVchNo;    
    private String name;
    private String panNo;
    private String custAdd;
    private String majorCustId;
    private String minorFlag;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }    
    
    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public double getTds() {
        return tds;
    }

    public void setTds(double tds) {
        this.tds = tds;
    }

    public String getTdsPostingDt() {
        return tdsPostingDt;
    }

    public void setTdsPostingDt(String tdsPostingDt) {
        this.tdsPostingDt = tdsPostingDt;
    }

    public String getRecoveredFlag() {
        return recoveredFlag;
    }

    public void setRecoveredFlag(String recoveredFlag) {
        this.recoveredFlag = recoveredFlag;
    }

    public double getRecoveredTdsAmt() {
        return recoveredTdsAmt;
    }

    public void setRecoveredTdsAmt(double recoveredTdsAmt) {
        this.recoveredTdsAmt = recoveredTdsAmt;
    }

    public String getRecoveredDt() {
        return recoveredDt;
    }

    public void setRecoveredDt(String recoveredDt) {
        this.recoveredDt = recoveredDt;
    }

    public String getRecoveredVchNo() {
        return recoveredVchNo;
    }

    public void setRecoveredVchNo(String recoveredVchNo) {
        this.recoveredVchNo = recoveredVchNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }    

    public double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public String getCustAdd() {
        return custAdd;
    }

    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    public String getMajorCustId() {
        return majorCustId;
    }

    public void setMajorCustId(String majorCustId) {
        this.majorCustId = majorCustId;
    }
    
    public String getMinorFlag() {
        return minorFlag;
    }

    public void setMinorFlag(String minorFlag) {
        this.minorFlag = minorFlag;
    }
    
}
