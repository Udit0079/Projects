/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author root
 */
public class RevenueReportDrCrPojo {
    
    private double opBal;
    private double drAmt;
    private double crAmt;
    private double closingBal;

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public double getOpBal() {
        return opBal;
    }

    public void setOpBal(double opBal) {
        this.opBal = opBal;
    }

    public double getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(double closingBal) {
        this.closingBal = closingBal;
    }
    
}
