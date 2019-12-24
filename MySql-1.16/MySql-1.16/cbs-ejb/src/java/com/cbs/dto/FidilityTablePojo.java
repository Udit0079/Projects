/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author sipl
 */
public class FidilityTablePojo {
   private String dsgCode;
    private String dsgDesc;
    private String bondAmount;
    private String prAmount;
    private String period;
    private String effDt;
    private String crGl;
    private String drGl;

    public String getBondAmount() {
        return bondAmount;
    }

    public void setBondAmount(String bondAmount) {
        this.bondAmount = bondAmount;
    }

    public String getCrGl() {
        return crGl;
    }

    public void setCrGl(String crGl) {
        this.crGl = crGl;
    }

    public String getDrGl() {
        return drGl;
    }

    public void setDrGl(String drGl) {
        this.drGl = drGl;
    }

    public String getDsgCode() {
        return dsgCode;
    }

    public void setDsgCode(String dsgCode) {
        this.dsgCode = dsgCode;
    }

    public String getDsgDesc() {
        return dsgDesc;
    }

    public void setDsgDesc(String dsgDesc) {
        this.dsgDesc = dsgDesc;
    }

    public String getEffDt() {
        return effDt;
    }

    public void setEffDt(String effDt) {
        this.effDt = effDt;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrAmount() {
        return prAmount;
    }

    public void setPrAmount(String prAmount) {
        this.prAmount = prAmount;
    } 
}
