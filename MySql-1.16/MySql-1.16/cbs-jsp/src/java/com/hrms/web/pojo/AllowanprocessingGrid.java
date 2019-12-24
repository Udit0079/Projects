/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.pojo;

/**
 *
 * @author Sudhir Kr. Bisht
 */
public class AllowanprocessingGrid {
    private String empName;
    private String empId;
    private String calDateFrom;
    private String calDateTo;
    private String months;
    private String totalAmt;
    private char postFlag;

    public String getCalDateFrom() {
        return calDateFrom;
    }

    public void setCalDateFrom(String calDateFrom) {
        this.calDateFrom = calDateFrom;
    }

    public String getCalDateTo() {
        return calDateTo;
    }

    public void setCalDateTo(String calDateTo) {
        this.calDateTo = calDateTo;
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

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public char getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(char postFlag) {
        this.postFlag = postFlag;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

}
