/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class RevenueStatementPojo implements Serializable{
    private Integer sNo;
    private String  reportName;                     //Like:Statement1 or Statement2
    private String  description;                    //Value of the report which will be shown in Description
    private Integer gNo;                            
    private Integer sGNo;
    private Integer ssGNo;
    private Integer sssGNo;
    private Integer ssssGNo;
    private String classification;
    private String acNature;                        //AcNature
    private String acType;   
    private String glHeadFrom;                      //if Value is present then Gl head Series
    private String glHeadTo;                        //if Value is present then Gl head Series
    private String formula;
    private String npaClassification;
    private String countApplicable;
    private BigDecimal  opBal,
            drAmt,
            crAmt,
            closingBal;

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getgNo() {
        return gNo;
    }

    public void setgNo(Integer gNo) {
        this.gNo = gNo;
    }

    public Integer getsGNo() {
        return sGNo;
    }

    public void setsGNo(Integer sGNo) {
        this.sGNo = sGNo;
    }

    public Integer getSsGNo() {
        return ssGNo;
    }

    public void setSsGNo(Integer ssGNo) {
        this.ssGNo = ssGNo;
    }

    public Integer getSssGNo() {
        return sssGNo;
    }

    public void setSssGNo(Integer sssGNo) {
        this.sssGNo = sssGNo;
    }

    public Integer getSsssGNo() {
        return ssssGNo;
    }

    public void setSsssGNo(Integer ssssGNo) {
        this.ssssGNo = ssssGNo;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getGlHeadFrom() {
        return glHeadFrom;
    }

    public void setGlHeadFrom(String glHeadFrom) {
        this.glHeadFrom = glHeadFrom;
    }

    public String getGlHeadTo() {
        return glHeadTo;
    }

    public void setGlHeadTo(String glHeadTo) {
        this.glHeadTo = glHeadTo;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public BigDecimal getOpBal() {
        return opBal;
    }

    public void setOpBal(BigDecimal opBal) {
        this.opBal = opBal;
    }

    public BigDecimal getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(BigDecimal drAmt) {
        this.drAmt = drAmt;
    }

    public BigDecimal getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(BigDecimal crAmt) {
        this.crAmt = crAmt;
    }

    public BigDecimal getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(BigDecimal closingBal) {
        this.closingBal = closingBal;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCountApplicable() {
        return countApplicable;
    }

    public void setCountApplicable(String countApplicable) {
        this.countApplicable = countApplicable;
    }

    public String getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(String npaClassification) {
        this.npaClassification = npaClassification;
    }
    
}
