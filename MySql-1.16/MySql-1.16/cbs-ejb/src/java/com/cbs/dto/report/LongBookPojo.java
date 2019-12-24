/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class LongBookPojo implements Serializable, Comparable<LongBookPojo> {
    private String acNo;
    private String custName;
    private double crCash; 
    private double crTrans; 
    private double crClg; 
    private double drCash; 
    private double drTrans; 
    private double drClg;
    private double recno;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getCrCash() {
        return crCash;
    }

    public void setCrCash(double crCash) {
        this.crCash = crCash;
    }

    public double getCrClg() {
        return crClg;
    }

    public void setCrClg(double crClg) {
        this.crClg = crClg;
    }

    public double getCrTrans() {
        return crTrans;
    }

    public void setCrTrans(double crTrans) {
        this.crTrans = crTrans;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getDrCash() {
        return drCash;
    }

    public void setDrCash(double drCash) {
        this.drCash = drCash;
    }

    public double getDrClg() {
        return drClg;
    }

    public void setDrClg(double drClg) {
        this.drClg = drClg;
    }

    public double getDrTrans() {
        return drTrans;
    }

    public void setDrTrans(double drTrans) {
        this.drTrans = drTrans;
    }
    public int compareTo(LongBookPojo pojo){
        return this.acNo.compareTo(pojo.acNo);
    }
    public double getRecno() {
        return recno;
    }
    public void setRecno(double recno) {
        this.recno = recno;
    }

    
}
