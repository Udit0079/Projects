/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class SalaryProcessingGrid {

    private String employeeId;
    private String employeeName;
    private String month;
    private double grossSalary;
    private double netSalary;
    private double incomeTax;
    private BigDecimal compAmt;
    private String compType;
    private String varDesc;
    private String salAcc;
    private int sNo;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public BigDecimal getCompAmt() {
        return compAmt;
    }

    public void setCompAmt(BigDecimal compAmt) {
        this.compAmt = compAmt;
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public String getVarDesc() {
        return varDesc;
    }

    public void setVarDesc(String varDesc) {
        this.varDesc = varDesc;
    }

    public String getSalAcc() {
        return salAcc;
    }

    public void setSalAcc(String salAcc) {
        this.salAcc = salAcc;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
}
