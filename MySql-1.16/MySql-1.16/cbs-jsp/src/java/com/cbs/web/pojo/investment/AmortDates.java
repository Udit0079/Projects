/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.investment;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class AmortDates implements Serializable, Comparable<AmortDates> {

    private Integer slno;
    private Integer ctrlno;
    private String dateS;
    private String dateE;
    private double days;
    private double amount;
    private String mode;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getCtrlno() {
        return ctrlno;
    }

    public void setCtrlno(Integer ctrlno) {
        this.ctrlno = ctrlno;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) {
        this.dateE = dateE;
    }

    public String getDateS() {
        return dateS;
    }

    public void setDateS(String dateS) {
        this.dateS = dateS;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public int compareTo(AmortDates obj) {
        return (this.getSlno() - obj.getSlno());
    }
}
