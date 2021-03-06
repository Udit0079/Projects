/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class PortFolioClassificationPojo {
    
    Integer sNo;
    String  reportName;                     //Like:Statement1 or Statement2
    String  description;                    //Value of the report which will be shown in Description
    Integer gNo;                            
    Integer sGNo;
    Integer ssGNo;
    Integer sssGNo;
    Integer ssssGNo;
    String classification;                  //A:Asset or L:Liability
    String countApplicable;                 //Y:If No. of accounts required 
    String acNature;                        //AcNature
    String acType;                          //AccountType Code
    String npaClassification;               //If Npa Classification is Applicable
    String glheadFrom;                      //if Value is present then Gl head Series
    String glHeadTo;                        //if Value is present then Gl head Series
    String rangeBaseOn;                     //Rs.:Range based on Rupees; Y:Years; M: Months; D:No. of days; 
    BigDecimal rangeFrom;
    BigDecimal rangeTo;
    String formula;
    String fGNo;
    String fSGNo;
    String fSsGNo;
    String fSssGNo;
    String fSsssGNo;
    public BigDecimal grossOutstd, provDedu;

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCountApplicable() {
        return countApplicable;
    }

    public void setCountApplicable(String countApplicable) {
        this.countApplicable = countApplicable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getfGNo() {
        return fGNo;
    }

    public void setfGNo(String fGNo) {
        this.fGNo = fGNo;
    }

    public String getfSGNo() {
        return fSGNo;
    }

    public void setfSGNo(String fSGNo) {
        this.fSGNo = fSGNo;
    }

    public String getfSsGNo() {
        return fSsGNo;
    }

    public void setfSsGNo(String fSsGNo) {
        this.fSsGNo = fSsGNo;
    }

    public String getfSssGNo() {
        return fSssGNo;
    }

    public void setfSssGNo(String fSssGNo) {
        this.fSssGNo = fSssGNo;
    }

    public String getfSsssGNo() {
        return fSsssGNo;
    }

    public void setfSsssGNo(String fSsssGNo) {
        this.fSsssGNo = fSsssGNo;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getgNo() {
        return gNo;
    }

    public void setgNo(Integer gNo) {
        this.gNo = gNo;
    }

    public String getGlHeadTo() {
        return glHeadTo;
    }

    public void setGlHeadTo(String glHeadTo) {
        this.glHeadTo = glHeadTo;
    }

    public String getGlheadFrom() {
        return glheadFrom;
    }

    public void setGlheadFrom(String glheadFrom) {
        this.glheadFrom = glheadFrom;
    }

    public BigDecimal getGrossOutstd() {
        return grossOutstd;
    }

    public void setGrossOutstd(BigDecimal grossOutstd) {
        this.grossOutstd = grossOutstd;
    }

    public String getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(String npaClassification) {
        this.npaClassification = npaClassification;
    }

    public BigDecimal getProvDedu() {
        return provDedu;
    }

    public void setProvDedu(BigDecimal provDedu) {
        this.provDedu = provDedu;
    }

    public String getRangeBaseOn() {
        return rangeBaseOn;
    }

    public void setRangeBaseOn(String rangeBaseOn) {
        this.rangeBaseOn = rangeBaseOn;
    }

    public BigDecimal getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(BigDecimal rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public BigDecimal getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(BigDecimal rangeTo) {
        this.rangeTo = rangeTo;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Integer getsGNo() {
        return sGNo;
    }

    public void setsGNo(Integer sGNo) {
        this.sGNo = sGNo;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
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
    
}
