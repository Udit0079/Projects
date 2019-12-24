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
public class HrAttendanceDetailsPKTO implements Serializable {

    private int compCode;
    private long empCode;
    private String attenMonth;
    private int attenYear;

    public String getAttenMonth() {
        return attenMonth;
    }

    public void setAttenMonth(String attenMonth) {
        this.attenMonth = attenMonth;
    }

    public int getAttenYear() {
        return attenYear;
    }

    public void setAttenYear(int attenYear) {
        this.attenYear = attenYear;
    }

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
}
