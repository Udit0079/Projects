/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.other;

/**
 *
 * @author root
 */
public class TdDuplicateReceipt {

    private String accountNumber;
    private float seqNo;
    private float voucherNo;
    private String receiptNo;
    private float roi;
    private String tdMadeDt;
    private String fdDt;
    private String matDt;
    private double prinAmt;
    private String intOpt;
    private String intToAcno;
    private String period;
    private String status;
    private double intAtMat;
    private double totTdAmt;

    public double getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public double getIntAtMat() {
        return intAtMat;
    }

    public void setIntAtMat(double intAtMat) {
        this.intAtMat = intAtMat;
    }

    public double getTotTdAmt() {
        return totTdAmt;
    }

    public void setTotTdAmt(double totTdAmt) {
        this.totTdAmt = totTdAmt;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }
      
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    
    public String getFdDt() {
        return fdDt;
    }

    public void setFdDt(String fdDt) {
        this.fdDt = fdDt;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getIntToAcno() {
        return intToAcno;
    }

    public void setIntToAcno(String intToAcno) {
        this.intToAcno = intToAcno;
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
    
    public float getRoi() {
        return roi;
    }

    public void setRoi(float roi) {
        this.roi = roi;
    }

    public float getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(float seqNo) {
        this.seqNo = seqNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTdMadeDt() {
        return tdMadeDt;
    }

    public void setTdMadeDt(String tdMadeDt) {
        this.tdMadeDt = tdMadeDt;
    }

    public float getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(float voucherNo) {
        this.voucherNo = voucherNo;
    }
  
}
