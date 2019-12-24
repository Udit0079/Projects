/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;


/**
 *
 * @author root
 */
public class RbiAlmPojo {
    
    Integer sNo;
    String reportName;                     //Like:Statement1 or Statement2
    String description;                    //Value of the report which will be shown in Description
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
    String rangeFrom;
    String rangeTo;
    String formula;
    String fGNo;
    String fSGNo;
    String fSsGNo;
    String fSssGNo;
    String fSsssGNo;
    Double amt;
    Double secondAmount;
    Double thirdAmount;
    Double fourthAmount;
    Double fifthAmount;
    Double sixthAmount;
    Double seventhAmount;
    Double eighthAmount;
    Double nineAmount;
    Double tenAmount;
    Double elevenAmount;
    Double twelveAmount;

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

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
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

    public Double getEighthAmount() {
        return eighthAmount;
    }

    public void setEighthAmount(Double eighthAmount) {
        this.eighthAmount = eighthAmount;
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

    public Double getFifthAmount() {
        return fifthAmount;
    }

    public void setFifthAmount(Double fifthAmount) {
        this.fifthAmount = fifthAmount;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getFourthAmount() {
        return fourthAmount;
    }

    public void setFourthAmount(Double fourthAmount) {
        this.fourthAmount = fourthAmount;
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

    public String getNpaClassification() {
        return npaClassification;
    }

    public void setNpaClassification(String npaClassification) {
        this.npaClassification = npaClassification;
    }

    public String getRangeBaseOn() {
        return rangeBaseOn;
    }

    public void setRangeBaseOn(String rangeBaseOn) {
        this.rangeBaseOn = rangeBaseOn;
    }

    public String getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(String rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public String getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(String rangeTo) {
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

    public Double getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(Double secondAmount) {
        this.secondAmount = secondAmount;
    }

    public Double getSeventhAmount() {
        return seventhAmount;
    }

    public void setSeventhAmount(Double seventhAmount) {
        this.seventhAmount = seventhAmount;
    }

    public Double getSixthAmount() {
        return sixthAmount;
    }

    public void setSixthAmount(Double sixthAmount) {
        this.sixthAmount = sixthAmount;
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

    public Double getThirdAmount() {
        return thirdAmount;
    }

    public void setThirdAmount(Double thirdAmount) {
        this.thirdAmount = thirdAmount;
    }
    public Double getElevenAmount() {
        return elevenAmount;
    }
    public void setElevenAmount(Double elevenAmount) {
        this.elevenAmount = elevenAmount;
    }
    public Double getNineAmount() {
        return nineAmount;
    }
    public void setNineAmount(Double nineAmount) {
        this.nineAmount = nineAmount;
    }
    public Double getTenAmount() {
        return tenAmount;
    }
    public void setTenAmount(Double tenAmount) {
        this.tenAmount = tenAmount;
    }
    public Double getTwelveAmount() {
        return twelveAmount;
    }
    public void setTwelveAmount(Double twelveAmount) {
        this.twelveAmount = twelveAmount;
    }
    
}
