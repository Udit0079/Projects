/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.other;

/**
 *
 * @author Admin
 */
public class LockerRentMasterGrid {
    String lockerType;
    String custCat;
    double rent;
    String effDate;
    String enterBy;

    public String getCustCat() {
        return custCat;
    }

    public void setCustCat(String custCat) {
        this.custCat = custCat;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

        
}
