/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.math.BigDecimal;

/**
 *
 * @author Sudhir
 */
public class LockerRentCalTable {

    String acno,
            custName;
    BigDecimal rent;
    float cabno, lockerNum;
    String lockerType;
    String rentDueDT;
    String custState;
    String branchState;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public float getCabno() {
        return cabno;
    }

    public void setCabno(float cabno) {
        this.cabno = cabno;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public float getLockerNum() {
        return lockerNum;
    }

    public void setLockerNum(float lockerNum) {
        this.lockerNum = lockerNum;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public String getRentDueDT() {
        return rentDueDT;
    }

    public void setRentDueDT(String rentDueDT) {
        this.rentDueDT = rentDueDT;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getBranchState() {
        return branchState;
    }

    public void setBranchState(String branchState) {
        this.branchState = branchState;
    }
  
}
