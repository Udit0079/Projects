/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.other;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class BackDateEntryPojo {
    private String acno;
    private String glname;
    private String txnType;
    private BigDecimal amount;
    private String date;
    private String trsno;
    private BigDecimal dramount;
    private BigDecimal cramount;
    private int batchno ;
    private double recno;
    private String detail;
    private String mode;

   
    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getGlname() {
        return glname;
    }

    public void setGlname(String glname) {
        this.glname = glname;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrsno() {
        return trsno;
    }

    public void setTrsno(String trsno) {
        this.trsno = trsno;
    }
    
    public BigDecimal getDramount() {
        return dramount;
    }

    public void setDramount(BigDecimal dramount) {
        this.dramount = dramount;
    }

    public BigDecimal getCramount() {
        return cramount;
    }

    public void setCramount(BigDecimal cramount) {
        this.cramount = cramount;
    }

    public int getBatchno() {
        return batchno;
    }

    public void setBatchno(int batchno) {
        this.batchno = batchno;
    }

    public double getRecno() {
        return recno;
    }

    public void setRecno(double recno) {
        this.recno = recno;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
 }
