/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.util.Date;

/**
 *
 * @author Sudhir Bisht
 */
public class SalaryAllocationTOExtra {

    private int compCode;
    private Long empCode;
    private Date calDateFrom;
    private Date caldateTo;
    private int month;
    private String purposeCode;
    
    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }
    public Date getCalDateFrom() {
        return calDateFrom;
    }

    public void setCalDateFrom(Date calDateFrom) {
        this.calDateFrom = calDateFrom;
    }

    public Date getCaldateTo() {
        return caldateTo;
    }

    public void setCaldateTo(Date caldateTo) {
        this.caldateTo = caldateTo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }
    public Long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(Long empCode) {
        this.empCode = empCode;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

  }
