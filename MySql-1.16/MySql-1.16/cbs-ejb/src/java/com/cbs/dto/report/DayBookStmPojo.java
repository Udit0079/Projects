/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author root
 */
public class DayBookStmPojo {
    double crBal;
    double drBal;
    int tranType;
    int optStatus;

    public double getCrBal() {
        return crBal;
    }

    public void setCrBal(double crBal) {
        this.crBal = crBal;
    }

    public double getDrBal() {
        return drBal;
    }

    public void setDrBal(double drBal) {
        this.drBal = drBal;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }

    public int getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(int optStatus) {
        this.optStatus = optStatus;
    }
    
}
