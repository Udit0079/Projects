/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author root
 */
public class LoanPrequistitsList {
    
    Integer custId;
    String acNo;
    String custName;
    double intApplied;
    double intProjected;
    double intTotal;
    double newIntApplied;
    double newIntProjected;
    double newIntTotal;
    double diffInt;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getIntApplied() {
        return intApplied;
    }

    public void setIntApplied(double intApplied) {
        this.intApplied = intApplied;
    }

    public double getIntProjected() {
        return intProjected;
    }

    public void setIntProjected(double intProjected) {
        this.intProjected = intProjected;
    }

    public double getIntTotal() {
        return intTotal;
    }

    public void setIntTotal(double intTotal) {
        this.intTotal = intTotal;
    }

    public double getNewIntApplied() {
        return newIntApplied;
    }

    public void setNewIntApplied(double newIntApplied) {
        this.newIntApplied = newIntApplied;
    }

    public double getNewIntProjected() {
        return newIntProjected;
    }

    public void setNewIntProjected(double newIntProjected) {
        this.newIntProjected = newIntProjected;
    }

    public double getNewIntTotal() {
        return newIntTotal;
    }

    public void setNewIntTotal(double newIntTotal) {
        this.newIntTotal = newIntTotal;
    }

    public double getDiffInt() {
        return diffInt;
    }

    public void setDiffInt(double diffInt) {
        this.diffInt = diffInt;
    }
    
}
