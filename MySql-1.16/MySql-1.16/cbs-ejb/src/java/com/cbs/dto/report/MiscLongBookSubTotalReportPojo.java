package com.cbs.dto.report;

public class MiscLongBookSubTotalReportPojo implements java.io.Serializable {

    private String acNo, acName, details;
    private double crAmt, drAmt;
    private int tranType, iy;

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public int getIy() {
        return iy;
    }

    public void setIy(int iy) {
        this.iy = iy;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }
}
