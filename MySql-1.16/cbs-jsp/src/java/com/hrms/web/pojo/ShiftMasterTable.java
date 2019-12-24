package com.hrms.web.pojo;

public class ShiftMasterTable {

    private int sno;
    private String shiftCode;
    private String description;
    private String timeIn;
    private String timeOut;
    private String breakFrom;
    private String breakTo;
    private String graceTimeIn;
    private String graceTimeOut;
    private String graceBreakTime;
    private String odTimeFirstHalf;
    private String odTimeSecondHalf;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getBreakFrom() {
        return breakFrom;
    }

    public void setBreakFrom(String breakFrom) {
        this.breakFrom = breakFrom;
    }

    public String getBreakTo() {
        return breakTo;
    }

    public void setBreakTo(String breakTo) {
        this.breakTo = breakTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGraceBreakTime() {
        return graceBreakTime;
    }

    public void setGraceBreakTime(String graceBreakTime) {
        this.graceBreakTime = graceBreakTime;
    }

    public String getGraceTimeIn() {
        return graceTimeIn;
    }

    public void setGraceTimeIn(String graceTimeIn) {
        this.graceTimeIn = graceTimeIn;
    }

    public String getGraceTimeOut() {
        return graceTimeOut;
    }

    public void setGraceTimeOut(String graceTimeOut) {
        this.graceTimeOut = graceTimeOut;
    }

    public String getOdTimeFirstHalf() {
        return odTimeFirstHalf;
    }

    public void setOdTimeFirstHalf(String odTimeFirstHalf) {
        this.odTimeFirstHalf = odTimeFirstHalf;
    }

    public String getOdTimeSecondHalf() {
        return odTimeSecondHalf;
    }

    public void setOdTimeSecondHalf(String odTimeSecondHalf) {
        this.odTimeSecondHalf = odTimeSecondHalf;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
