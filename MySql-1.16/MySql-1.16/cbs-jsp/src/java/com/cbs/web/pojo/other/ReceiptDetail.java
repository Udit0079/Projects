/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.other;

/**
 *
 * @author root
 */
public class ReceiptDetail {
    private float seqNo;
    private float voucherNo;
    private String acno;
    private double prinAmt;
    private double roi;
    private String tdMadeDt;
    private String fdDt;
    private String matDt;
    private String intOpt;
    private String intToAcno;
    private String period;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
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

    public float getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(float seqNo) {
        this.seqNo = seqNo;
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
