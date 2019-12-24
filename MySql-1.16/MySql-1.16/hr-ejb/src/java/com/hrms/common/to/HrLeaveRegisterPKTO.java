/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author stellar
 */
public class HrLeaveRegisterPKTO implements Serializable {

    private int compCode;
    private long empCode;
    private String leavRegCode;

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getLeavRegCode() {
        return leavRegCode;
    }

    public void setLeavRegCode(String leavRegCode) {
        this.leavRegCode = leavRegCode;
    }
}
