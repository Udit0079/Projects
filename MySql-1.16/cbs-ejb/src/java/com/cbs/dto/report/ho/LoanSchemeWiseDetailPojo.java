/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Athar Raza
 */
public class LoanSchemeWiseDetailPojo implements Serializable{
    private String acNo;
    private String acType;
    private String actDesc;
    private String custName;
    private String scmCodeIntTableCode;
    private String fixedFloating;
    private String paggingFreq;
    private String fixedTillDt;
    private String openingDt;
    private double schemeRoi;
    private double acRoi;
    private double applicableRoi;
    private double acPrfCr;
    private double acPrfDr;   
    private String loanDuration;
    private BigDecimal outBal;
    private String moratoriumPd;

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }
    
    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }
    
    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }
    
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getSchemeRoi() {
        return schemeRoi;
    }

    public void setSchemeRoi(double schemeRoi) {
        this.schemeRoi = schemeRoi;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getAcPrfCr() {
        return acPrfCr;
    }

    public void setAcPrfCr(double acPrfCr) {
        this.acPrfCr = acPrfCr;
    }

    public double getAcPrfDr() {
        return acPrfDr;
    }

    public void setAcPrfDr(double acPrfDr) {
        this.acPrfDr = acPrfDr;
    }

    public double getAcRoi() {
        return acRoi;
    }

    public void setAcRoi(double acRoi) {
        this.acRoi = acRoi;
    }

    public double getApplicableRoi() {
        return applicableRoi;
    }

    public void setApplicableRoi(double applicableRoi) {
        this.applicableRoi = applicableRoi;
    }

    public String getFixedFloating() {
        return fixedFloating;
    }

    public void setFixedFloating(String fixedFloating) {
        this.fixedFloating = fixedFloating;
    }

    public String getFixedTillDt() {
        return fixedTillDt;
    }

    public void setFixedTillDt(String fixedTillDt) {
        this.fixedTillDt = fixedTillDt;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public BigDecimal getOutBal() {
        return outBal;
    }

    public void setOutBal(BigDecimal outBal) {
        this.outBal = outBal;
    }

    public String getPaggingFreq() {
        return paggingFreq;
    }

    public void setPaggingFreq(String paggingFreq) {
        this.paggingFreq = paggingFreq;
    }

    public String getScmCodeIntTableCode() {
        return scmCodeIntTableCode;
    }

    public void setScmCodeIntTableCode(String scmCodeIntTableCode) {
        this.scmCodeIntTableCode = scmCodeIntTableCode;
    }
    public String getMoratoriumPd() {
        return moratoriumPd;
    }
    public void setMoratoriumPd(String moratoriumPd) {
        this.moratoriumPd = moratoriumPd;
    }
    
}
