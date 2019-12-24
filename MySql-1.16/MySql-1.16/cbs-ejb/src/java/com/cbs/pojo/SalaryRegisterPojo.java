/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

/**
 *
 * @author root
 */
public class SalaryRegisterPojo {
    private String empId;
    private String empName;
    private String empCode;
    private double totalEarningAmt;
    private double totalDeductionAmt;
    private double netpayAmt;
    private String purposeType;
    private String purposeCode;
    private String componentName;
    private double componentAmount;
    private int compCount;

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

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public double getTotalEarningAmt() {
        return totalEarningAmt;
    }

    public void setTotalEarningAmt(double totalEarningAmt) {
        this.totalEarningAmt = totalEarningAmt;
    }

    public double getTotalDeductionAmt() {
        return totalDeductionAmt;
    }

    public void setTotalDeductionAmt(double totalDeductionAmt) {
        this.totalDeductionAmt = totalDeductionAmt;
    }

    public double getNetpayAmt() {
        return netpayAmt;
    }

    public void setNetpayAmt(double netpayAmt) {
        this.netpayAmt = netpayAmt;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public double getComponentAmount() {
        return componentAmount;
    }

    public void setComponentAmount(double componentAmount) {
        this.componentAmount = componentAmount;
    }

    public int getCompCount() {
        return compCount;
    }

    public void setCompCount(int compCount) {
        this.compCount = compCount;
    }


}
