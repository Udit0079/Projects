package com.cbs.dto.report;

public class LockerSurrenderReportPojo implements java.io.Serializable{

    private String cabNo,
            lockerType,
            lockerNo,
            acNo,
            rentDueDt,
            surrenderDt;
    private double rent;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public String getRentDueDt() {
        return rentDueDt;
    }

    public void setRentDueDt(String rentDueDt) {
        this.rentDueDt = rentDueDt;
    }

    public String getSurrenderDt() {
        return surrenderDt;
    }

    public void setSurrenderDt(String surrenderDt) {
        this.surrenderDt = surrenderDt;
    }
}
