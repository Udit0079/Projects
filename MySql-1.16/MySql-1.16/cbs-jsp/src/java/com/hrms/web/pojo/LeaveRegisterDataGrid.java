package com.hrms.web.pojo;

public class LeaveRegisterDataGrid {

    private int sno;
    private String empId;
    private String empName;
    private String paid;
    private String sanctionAuthority;
    private String leaveCode;
    private int daysTaken;
    private String deptRecommendations;
    private String reasonOfLeave;
    private String leaveFromDate;
    private String leaveToDate;
    private String leaveRegCode;
    private String remarks;

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    public String getDeptRecommendations() {
        return deptRecommendations;
    }

    public void setDeptRecommendations(String deptRecommendations) {
        this.deptRecommendations = deptRecommendations;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getReasonOfLeave() {
        return reasonOfLeave;
    }

    public void setReasonOfLeave(String reasonOfLeave) {
        this.reasonOfLeave = reasonOfLeave;
    }

    public String getSanctionAuthority() {
        return sanctionAuthority;
    }

    public void setSanctionAuthority(String sanctionAuthority) {
        this.sanctionAuthority = sanctionAuthority;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getLeaveFromDate() {
        return leaveFromDate;
    }

    public void setLeaveFromDate(String leaveFromDate) {
        this.leaveFromDate = leaveFromDate;
    }

    public String getLeaveRegCode() {
        return leaveRegCode;
    }

    public void setLeaveRegCode(String leaveRegCode) {
        this.leaveRegCode = leaveRegCode;
    }

    public String getLeaveToDate() {
        return leaveToDate;
    }

    public void setLeaveToDate(String leaveToDate) {
        this.leaveToDate = leaveToDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
