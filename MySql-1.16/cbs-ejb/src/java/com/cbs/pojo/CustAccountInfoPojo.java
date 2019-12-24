/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class CustAccountInfoPojo implements Serializable {

    private String custId;
    private String acno;
    private String custname;
    private BigDecimal totalCrAmt;
    private BigDecimal maxCrAmt;
    private BigDecimal maxDrAmt;
    private BigDecimal annualTurnover;
    private String riskCategory;
    private BigDecimal thresholdLimit;
    private String actCode;
    private String actDesc;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public BigDecimal getTotalCrAmt() {
        return totalCrAmt;
    }

    public void setTotalCrAmt(BigDecimal totalCrAmt) {
        this.totalCrAmt = totalCrAmt;
    }

    public BigDecimal getMaxCrAmt() {
        return maxCrAmt;
    }

    public void setMaxCrAmt(BigDecimal maxCrAmt) {
        this.maxCrAmt = maxCrAmt;
    }

    public BigDecimal getMaxDrAmt() {
        return maxDrAmt;
    }

    public void setMaxDrAmt(BigDecimal maxDrAmt) {
        this.maxDrAmt = maxDrAmt;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(BigDecimal annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public BigDecimal getThresholdLimit() {
        return thresholdLimit;
    }

    public void setThresholdLimit(BigDecimal thresholdLimit) {
        this.thresholdLimit = thresholdLimit;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }
    
    
}
