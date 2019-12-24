/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.master;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class TableDetails {

    private String seqNo;
    private String applicableDate;
    private String intRate;
    private String fromPeriod;
    private String toPeriod;
    private BigDecimal fromAmt;
    private BigDecimal toAmt;
    private String addIntSr;
    private String addIntSt;
    private String addIntOt;
    private String acNature;
    private String addIntMg;

    public BigDecimal getFromAmt() {
        return fromAmt;
    }

    public void setFromAmt(BigDecimal fromAmt) {
        this.fromAmt = fromAmt;
    }

    public BigDecimal getToAmt() {
        return toAmt;
    }

    public void setToAmt(BigDecimal toAmt) {
        this.toAmt = toAmt;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public String getAddIntOt() {
        return addIntOt;
    }

    public void setAddIntOt(String addIntOt) {
        this.addIntOt = addIntOt;
    }

    public String getAddIntSr() {
        return addIntSr;
    }

    public void setAddIntSr(String addIntSr) {
        this.addIntSr = addIntSr;
    }

    public String getAddIntSt() {
        return addIntSt;
    }

    public void setAddIntSt(String addIntSt) {
        this.addIntSt = addIntSt;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAddIntMg() {
        return addIntMg;
    }

    public void setAddIntMg(String addIntMg) {
        this.addIntMg = addIntMg;
    }
    
}
