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
public class HrSalaryAllocationTOPK implements Serializable {

    private int compCode;
    private long empCode;
    private String calDateFrom;
    private String calDateTo;
    private String months;
    private String componentType;

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

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }
}
