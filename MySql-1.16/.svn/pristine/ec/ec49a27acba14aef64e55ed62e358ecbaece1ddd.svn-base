package com.cbs.dto.report;

public class AlmTdAccountWiseReportPojo implements java.io.Serializable, Comparable {

    private int sNo;
    private String acNo;
    private double vchNo;
    private double roi;
    private String maturityDate;
    private double outstandingAmount;
    private int daysLeft;
    private String rangePeriod;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getRangePeriod() {
        return rangePeriod;
    }

    public void setRangePeriod(String rangePeriod) {
        this.rangePeriod = rangePeriod;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public double getVchNo() {
        return vchNo;
    }

    public void setVchNo(double vchNo) {
        this.vchNo = vchNo;
    }

    public int compareTo(Object o) {
        Double roi1 = new Double(this.roi);
        Double roi2 = new Double(((AlmTdAccountWiseReportPojo) o).roi);
        return roi1.compareTo(roi2);
    }
}
