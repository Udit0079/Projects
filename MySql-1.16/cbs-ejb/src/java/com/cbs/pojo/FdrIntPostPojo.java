/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

/**
 *
 * @author root
 */
public class FdrIntPostPojo {
    private int controlNo;
    private String particulars;
    private double faceValue;
    private double maturityValue;
    private String lastIntPaidDate;
    private double roi;
    private String period;
    private double interestAmt;
    private String interestoption;
    private double clearingdiff;

    public String getInterestoption() {
        return interestoption;
    }

    public void setInterestoption(String interestoption) {
        this.interestoption = interestoption;
    }

    public int getControlNo() {
        return controlNo;
    }

    public void setControlNo(int controlNo) {
        this.controlNo = controlNo;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getMaturityValue() {
        return maturityValue;
    }

    public void setMaturityValue(double maturityValue) {
        this.maturityValue = maturityValue;
    }

    public String getLastIntPaidDate() {
        return lastIntPaidDate;
    }

    public void setLastIntPaidDate(String lastIntPaidDate) {
        this.lastIntPaidDate = lastIntPaidDate;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(double interestAmt) {
        this.interestAmt = interestAmt;
    }

    public double getClearingdiff() {
        return clearingdiff;
    }

    public void setClearingdiff(double clearingdiff) {
        this.clearingdiff = clearingdiff;
    }
    
    
}
