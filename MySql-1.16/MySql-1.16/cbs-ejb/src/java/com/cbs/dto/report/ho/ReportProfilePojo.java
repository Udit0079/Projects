/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class ReportProfilePojo implements Serializable {

    private String acno;
    private String name;
    private double roi;
    private String period;
    private BigDecimal amount;
    private Integer noOfAc;
    private BigDecimal outstanding;
    private String nature;
    private String acType;
    private String acCodeDesc;
    private String slabDescription;
    private String column;
    private String slabType;

    public String getSlabType() {
        return slabType;
    }

    public void setSlabType(String slabType) {
        this.slabType = slabType;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcCodeDesc() {
        return acCodeDesc;
    }

    public void setAcCodeDesc(String acCodeDesc) {
        this.acCodeDesc = acCodeDesc;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public Integer getNoOfAc() {
        return noOfAc;
    }

    public void setNoOfAc(Integer noOfAc) {
        this.noOfAc = noOfAc;
    }

    public String getSlabDescription() {
        return slabDescription;
    }

    public void setSlabDescription(String slabDescription) {
        this.slabDescription = slabDescription;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(BigDecimal outstanding) {
        this.outstanding = outstanding;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
