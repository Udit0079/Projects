package com.cbs.dto;

public class InterestSlabMasterTable implements Comparable {

    private String applicableDate;
    private String interestrate;
    private String fromPeriod;
    private String toPeriod;
    private String enteredby;
    private String lastupdatedt;
    private String frmAmt;
    private String toAmt;
    private String commPerc;
    private String surChg;
    private String agiTax;
    private String agDecDep;
    private String dayBal;   

    public String getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(String interestrate) {
        this.interestrate = interestrate;
    }

    public String getEnteredby() {
        return enteredby;
    }

    public void setEnteredby(String enteredby) {
        this.enteredby = enteredby;
    }

    public String getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public String getLastupdatedt() {
        return lastupdatedt;
    }

    public void setLastupdatedt(String lastupdatedt) {
        this.lastupdatedt = lastupdatedt;
    }

    public String getFrmAmt() {
        return frmAmt;
    }

    public void setFrmAmt(String frmAmt) {
        this.frmAmt = frmAmt;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public String getCommPerc() {
        return commPerc;
    }

    public void setCommPerc(String commPerc) {
        this.commPerc = commPerc;
    }

    public String getSurChg() {
        return surChg;
    }

    public void setSurChg(String surChg) {
        this.surChg = surChg;
    }

    public String getAgiTax() {
        return agiTax;
    }

    public void setAgiTax(String agiTax) {
        this.agiTax = agiTax;
    }

    public String getAgDecDep() {
        return agDecDep;
    }

    public void setAgDecDep(String agDecDep) {
        this.agDecDep = agDecDep;
    }

    public String getDayBal() {
        return dayBal;
    }

    public void setDayBal(String dayBal) {
        this.dayBal = dayBal;
    }   

    public int compareTo(Object o) {
        Double p1 = new Double(this.fromPeriod);
        Double p2 = new Double(((InterestSlabMasterTable) o).fromPeriod);
        return p1.compareTo(p2);
    }
}
