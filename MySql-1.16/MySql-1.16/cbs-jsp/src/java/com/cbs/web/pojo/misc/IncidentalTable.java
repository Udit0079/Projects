/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.misc;

/**
 *
 * @author root
 */
public class IncidentalTable {
    private String accno;
    private String name;
    private double penlty;
    private float lim;
    private String optst;
    private String status1;
    private String tran;

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public float getLim() {
        return lim;
    }

    public void setLim(float lim) {
        this.lim = lim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptst() {
        return optst;
    }

    public void setOptst(String optst) {
        this.optst = optst;
    }

    public double getPenlty() {
        return penlty;
    }

    public void setPenlty(double penlty) {
        this.penlty = penlty;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }


}
