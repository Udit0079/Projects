package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

public class HrEmpAdvanceDtPKTO implements Serializable {

    private long empAdvNo;
    private int compCode;
    private long empCode;
    private String advance;
    private Date dueDate;

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public long getEmpAdvNo() {
        return empAdvNo;
    }

    public void setEmpAdvNo(long empAdvNo) {
        this.empAdvNo = empAdvNo;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }
}
