/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class HrEmpAdvanceHdPKTO implements Serializable{

    private long empAdvNo;

    private int compCode;

    private long empCode;

    private String advance;

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
