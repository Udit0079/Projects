/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.loan;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class EmploymentDetailsGrid {
    String sNo;
    String firmName;
    String firmAdd;
    String firmPhoneNo;
    String fromDt;
    String toDt;
    String designation;
    BigDecimal monthlyIncome;
    String reasonToQuit;
    String empId;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFirmAdd() {
        return firmAdd;
    }

    public void setFirmAdd(String firmAdd) {
        this.firmAdd = firmAdd;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getFirmPhoneNo() {
        return firmPhoneNo;
    }

    public void setFirmPhoneNo(String firmPhoneNo) {
        this.firmPhoneNo = firmPhoneNo;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

  
    public String getReasonToQuit() {
        return reasonToQuit;
    }

    public void setReasonToQuit(String reasonToQuit) {
        this.reasonToQuit = reasonToQuit;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

        

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }
    
}
