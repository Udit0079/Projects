/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author sipl
 */
public class Oss4PartGUnSecAdvPojo {

    private String name;
    private String relName;
    private String natInterest;
    private String relation;
    private String tpExp;
    private String expDate;
    private BigDecimal expAmt, secValue, amtOs, prov, secRoi;
    private String secNature;
    private String classification;

    public BigDecimal getAmtOs() {
        return amtOs;
    }

    public void setAmtOs(BigDecimal amtOs) {
        this.amtOs = amtOs;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public BigDecimal getExpAmt() {
        return expAmt;
    }

    public void setExpAmt(BigDecimal expAmt) {
        this.expAmt = expAmt;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNatInterest() {
        return natInterest;
    }

    public void setNatInterest(String natInterest) {
        this.natInterest = natInterest;
    }

    public BigDecimal getProv() {
        return prov;
    }

    public void setProv(BigDecimal prov) {
        this.prov = prov;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSecNature() {
        return secNature;
    }

    public void setSecNature(String secNature) {
        this.secNature = secNature;
    }

    public BigDecimal getSecRoi() {
        return secRoi;
    }

    public void setSecRoi(BigDecimal secRoi) {
        this.secRoi = secRoi;
    }

    public BigDecimal getSecValue() {
        return secValue;
    }

    public void setSecValue(BigDecimal secValue) {
        this.secValue = secValue;
    }

    public String getTpExp() {
        return tpExp;
    }

    public void setTpExp(String tpExp) {
        this.tpExp = tpExp;
    }
}