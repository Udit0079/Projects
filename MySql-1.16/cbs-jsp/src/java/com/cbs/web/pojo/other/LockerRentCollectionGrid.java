/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.other;

/**
 *
 * @author Admin
 */
public class LockerRentCollectionGrid {
    String cabNo;
    String lockerTy;
    String lockerNo;
    String actNo;
    String custName;
    double rent;
    String rentDueDt;
    String status;
    String apply;
    String brCode;
    String custState;
    String brState;
    String advPayYr;

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }
    
    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getLockerTy() {
        return lockerTy;
    }

    public void setLockerTy(String lockerTy) {
        this.lockerTy = lockerTy;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    

    public String getRentDueDt() {
        return rentDueDt;
    }

    public void setRentDueDt(String rentDueDt) {
        this.rentDueDt = rentDueDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getBrState() {
        return brState;
    }

    public void setBrState(String brState) {
        this.brState = brState;
    }

    public String getAdvPayYr() {
        return advPayYr;
    }

    public void setAdvPayYr(String advPayYr) {
        this.advPayYr = advPayYr;
    }
    
}
