/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class SuspenseGeneralPojo implements Serializable{
    private String suspenseId;
    private String susCreationDt;
    private String particularOfSus;
    private double susDrAmt;
    private double susCrAmt;
    private double unadjDrAmt;
    private double unadjCrAmt;
    private String lastAdjDt;

    public String getSuspenseId() {
        return suspenseId;
    }

    public void setSuspenseId(String suspenseId) {
        this.suspenseId = suspenseId;
    }

    public String getSusCreationDt() {
        return susCreationDt;
    }

    public void setSusCreationDt(String susCreationDt) {
        this.susCreationDt = susCreationDt;
    }

    public String getParticularOfSus() {
        return particularOfSus;
    }

    public void setParticularOfSus(String particularOfSus) {
        this.particularOfSus = particularOfSus;
    }

    public double getSusDrAmt() {
        return susDrAmt;
    }

    public void setSusDrAmt(double susDrAmt) {
        this.susDrAmt = susDrAmt;
    }

    public double getSusCrAmt() {
        return susCrAmt;
    }

    public void setSusCrAmt(double susCrAmt) {
        this.susCrAmt = susCrAmt;
    }

    public double getUnadjDrAmt() {
        return unadjDrAmt;
    }

    public void setUnadjDrAmt(double unadjDrAmt) {
        this.unadjDrAmt = unadjDrAmt;
    }

    public double getUnadjCrAmt() {
        return unadjCrAmt;
    }

    public void setUnadjCrAmt(double unadjCrAmt) {
        this.unadjCrAmt = unadjCrAmt;
    }

    public String getLastAdjDt() {
        return lastAdjDt;
    }

    public void setLastAdjDt(String lastAdjDt) {
        this.lastAdjDt = lastAdjDt;
    }
    
    
    
    
    
    
}
