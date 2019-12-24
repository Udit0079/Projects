/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

/**
 *
 * @author root
 */
public class GoiIntReportPojo {

    private int ctrlNo;
    private String particulars;
    private String rtno;
    private String settleDt;
    private String ipDate;
    private String faceVal;
    private String bookVal;
    private double roi;
    private long period;
    private String intAmt;
    private double ytm;

    public int getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(int ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(String intAmt) {
        this.intAmt = intAmt;
    }   

    public String getIpDate() {
        return ipDate;
    }

    public void setIpDate(String ipDate) {
        this.ipDate = ipDate;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public String getFaceVal() {
        return faceVal;
    }

    public void setFaceVal(String faceVal) {
        this.faceVal = faceVal;
    }

    public String getBookVal() {
        return bookVal;
    }

    public void setBookVal(String bookVal) {
        this.bookVal = bookVal;
    }   

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getRtno() {
        return rtno;
    }

    public void setRtno(String rtno) {
        this.rtno = rtno;
    }

    public String getSettleDt() {
        return settleDt;
    }

    public void setSettleDt(String settleDt) {
        this.settleDt = settleDt;
    }

    public double getYtm() {
        return ytm;
    }

    public void setYtm(double ytm) {
        this.ytm = ytm;
    }   
}
