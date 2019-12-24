package com.hrms.web.pojo;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sudhir
 */
public class LeaveMasterGrid {

    private String fromDate;
    private String toDate;
    private String leaveCode;
    private String leaveDesc;
    private String leaveNature;
    private String applicableDate;
    private String description;
    private int noOfDays;
    private char enCash;

    public String getLeaveDesc() {
        return leaveDesc;
    }

    public void setLeaveDesc(String leaveDesc) {
        this.leaveDesc = leaveDesc;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public char getEnCash() {
        return enCash;
    }

    public void setEnCash(char enCash) {
        this.enCash = enCash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getLeaveNature() {
        return leaveNature;
    }

    public void setLeaveNature(String leaveNature) {
        this.leaveNature = leaveNature;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
