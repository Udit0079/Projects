/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.math.BigDecimal;

/**
 *
 * @author saurabhsipl
 */
public class AnnualNPAStmtPojo {
      
    private String reportName;
    private String description;
    private Integer gNo;
    private Integer sGNo;
    private Integer ssGNo;
    private Integer sssGNo;
    private Integer ssssGNo;
    private String classification;
    private String countApplicable;
    private String acNature;
    private String acType;
    private String rangeBaseOn;
    private String rangeFrom;
    private String rangeTo;
    private String formula;
    private String fGNo;
    private String fSGNo;
    private String fSsGNo;
    private String fSssGNo;
    private String fSsssGNo;
    private String glheadFrom;
    private String glheadTo;
    private long numberOfAc;
    private BigDecimal principalAmt;
    private BigDecimal interestAmt;
    private BigDecimal percOfLoanAndAdv;
    private String provPerc;
    private BigDecimal provAmt;
    private BigDecimal startProv;
    private BigDecimal actualProv;
    private BigDecimal totalProv;
    private BigDecimal excessBDDRProv;
    private BigDecimal totalOutStandingCurr;
    private BigDecimal totalOutStandingPrev;    
    private BigDecimal npaAtStart;
    private BigDecimal npaAsOnDate;
    private String remarks;
    private String asOnDate;
    private String beginDate;
    private BigDecimal percNPAToLoanGrossCurr;
    private BigDecimal percNPAToLoanGrossPrev;    
    private BigDecimal netAdvncCurr;
    private BigDecimal netAdvncPrev;
    private BigDecimal netNPACurr;
    private BigDecimal netNPAPrev;
    private BigDecimal netNPAasPercOfNetAdvncCurr;
    private BigDecimal netNPAasPercOfNetAdvncPrev;
    private BigDecimal glBalanceCurrA;
    private BigDecimal glBalancePrevA;    
    private BigDecimal glBalanceCurrB;
    private BigDecimal glBalancePrevB;
    private BigDecimal glBalanceCurrC;
    private BigDecimal glBalancePrevC;
    private BigDecimal glBalanceCurrT;
    private BigDecimal glBalancePrevT;
    private BigDecimal totProvAfterBDDRCurr;
    private BigDecimal totProvAfterBDDRPrev;

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

    public BigDecimal getActualProv() {
        return actualProv;
    }

    public void setActualProv(BigDecimal actualProv) {
        this.actualProv = actualProv;
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

    public BigDecimal getExcessBDDRProv() {
        return excessBDDRProv;
    }

    public void setExcessBDDRProv(BigDecimal excessBDDRProv) {
        this.excessBDDRProv = excessBDDRProv;
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

    public String getGlheadFrom() {
        return glheadFrom;
    }

    public void setGlheadFrom(String glheadFrom) {
        this.glheadFrom = glheadFrom;
    }

    public String getGlheadTo() {
        return glheadTo;
    }

    public void setGlheadTo(String glheadTo) {
        this.glheadTo = glheadTo;
    }

    public BigDecimal getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(BigDecimal interestAmt) {
        this.interestAmt = interestAmt;
    }

    public BigDecimal getNpaAsOnDate() {
        return npaAsOnDate;
    }

    public void setNpaAsOnDate(BigDecimal npaAsOnDate) {
        this.npaAsOnDate = npaAsOnDate;
    }

    public BigDecimal getNpaAtStart() {
        return npaAtStart;
    }

    public void setNpaAtStart(BigDecimal npaAtStart) {
        this.npaAtStart = npaAtStart;
    }

    public long getNumberOfAc() {
        return numberOfAc;
    }

    public void setNumberOfAc(long numberOfAc) {
        this.numberOfAc = numberOfAc;
    }

    public BigDecimal getPercOfLoanAndAdv() {
        return percOfLoanAndAdv;
    }

    public void setPercOfLoanAndAdv(BigDecimal percOfLoanAndAdv) {
        this.percOfLoanAndAdv = percOfLoanAndAdv;
    }

    public BigDecimal getPrincipalAmt() {
        return principalAmt;
    }

    public void setPrincipalAmt(BigDecimal principalAmt) {
        this.principalAmt = principalAmt;
    }

    public BigDecimal getProvAmt() {
        return provAmt;
    }

    public void setProvAmt(BigDecimal provAmt) {
        this.provAmt = provAmt;
    }

    public String getProvPerc() {
        return provPerc;
    }

    public void setProvPerc(String provPerc) {
        this.provPerc = provPerc;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public BigDecimal getStartProv() {
        return startProv;
    }

    public void setStartProv(BigDecimal startProv) {
        this.startProv = startProv;
    }

    public BigDecimal getTotalOutStandingCurr() {
        return totalOutStandingCurr;
    }

    public void setTotalOutStandingCurr(BigDecimal totalOutStandingCurr) {
        this.totalOutStandingCurr = totalOutStandingCurr;
    }

    public BigDecimal getTotalOutStandingPrev() {
        return totalOutStandingPrev;
    }

    public void setTotalOutStandingPrev(BigDecimal totalOutStandingPrev) {
        this.totalOutStandingPrev = totalOutStandingPrev;
    }

    public BigDecimal getTotalProv() {
        return totalProv;
    }

    public void setTotalProv(BigDecimal totalProv) {
        this.totalProv = totalProv;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public BigDecimal getNetAdvncCurr() {
        return netAdvncCurr;
    }

    public void setNetAdvncCurr(BigDecimal netAdvncCurr) {
        this.netAdvncCurr = netAdvncCurr;
    }

    public BigDecimal getNetAdvncPrev() {
        return netAdvncPrev;
    }

    public void setNetAdvncPrev(BigDecimal netAdvncPrev) {
        this.netAdvncPrev = netAdvncPrev;
    }

    public BigDecimal getNetNPACurr() {
        return netNPACurr;
    }

    public void setNetNPACurr(BigDecimal netNPACurr) {
        this.netNPACurr = netNPACurr;
    }

    public BigDecimal getNetNPAPrev() {
        return netNPAPrev;
    }

    public void setNetNPAPrev(BigDecimal netNPAPrev) {
        this.netNPAPrev = netNPAPrev;
    }

    public BigDecimal getNetNPAasPercOfNetAdvncCurr() {
        return netNPAasPercOfNetAdvncCurr;
    }

    public void setNetNPAasPercOfNetAdvncCurr(BigDecimal netNPAasPercOfNetAdvncCurr) {
        this.netNPAasPercOfNetAdvncCurr = netNPAasPercOfNetAdvncCurr;
    }

    public BigDecimal getNetNPAasPercOfNetAdvncPrev() {
        return netNPAasPercOfNetAdvncPrev;
    }

    public void setNetNPAasPercOfNetAdvncPrev(BigDecimal netNPAasPercOfNetAdvncPrev) {
        this.netNPAasPercOfNetAdvncPrev = netNPAasPercOfNetAdvncPrev;
    }

    public BigDecimal getPercNPAToLoanGrossCurr() {
        return percNPAToLoanGrossCurr;
    }

    public void setPercNPAToLoanGrossCurr(BigDecimal percNPAToLoanGrossCurr) {
        this.percNPAToLoanGrossCurr = percNPAToLoanGrossCurr;
    }

    public BigDecimal getPercNPAToLoanGrossPrev() {
        return percNPAToLoanGrossPrev;
    }

    public void setPercNPAToLoanGrossPrev(BigDecimal percNPAToLoanGrossPrev) {
        this.percNPAToLoanGrossPrev = percNPAToLoanGrossPrev;
    }

    public BigDecimal getGlBalanceCurrA() {
        return glBalanceCurrA;
    }

    public void setGlBalanceCurrA(BigDecimal glBalanceCurrA) {
        this.glBalanceCurrA = glBalanceCurrA;
    }

    public BigDecimal getGlBalanceCurrB() {
        return glBalanceCurrB;
    }

    public void setGlBalanceCurrB(BigDecimal glBalanceCurrB) {
        this.glBalanceCurrB = glBalanceCurrB;
    }

    public BigDecimal getGlBalanceCurrC() {
        return glBalanceCurrC;
    }

    public void setGlBalanceCurrC(BigDecimal glBalanceCurrC) {
        this.glBalanceCurrC = glBalanceCurrC;
    }

    public BigDecimal getGlBalanceCurrT() {
        return glBalanceCurrT;
    }

    public void setGlBalanceCurrT(BigDecimal glBalanceCurrT) {
        this.glBalanceCurrT = glBalanceCurrT;
    }

    public BigDecimal getGlBalancePrevA() {
        return glBalancePrevA;
    }

    public void setGlBalancePrevA(BigDecimal glBalancePrevA) {
        this.glBalancePrevA = glBalancePrevA;
    }

    public BigDecimal getGlBalancePrevB() {
        return glBalancePrevB;
    }

    public void setGlBalancePrevB(BigDecimal glBalancePrevB) {
        this.glBalancePrevB = glBalancePrevB;
    }

    public BigDecimal getGlBalancePrevC() {
        return glBalancePrevC;
    }

    public void setGlBalancePrevC(BigDecimal glBalancePrevC) {
        this.glBalancePrevC = glBalancePrevC;
    }

    public BigDecimal getGlBalancePrevT() {
        return glBalancePrevT;
    }

    public void setGlBalancePrevT(BigDecimal glBalancePrevT) {
        this.glBalancePrevT = glBalancePrevT;
    }

    public BigDecimal getTotProvAfterBDDRCurr() {
        return totProvAfterBDDRCurr;
    }

    public void setTotProvAfterBDDRCurr(BigDecimal totProvAfterBDDRCurr) {
        this.totProvAfterBDDRCurr = totProvAfterBDDRCurr;
    }

    public BigDecimal getTotProvAfterBDDRPrev() {
        return totProvAfterBDDRPrev;
    }

    public void setTotProvAfterBDDRPrev(BigDecimal totProvAfterBDDRPrev) {
        this.totProvAfterBDDRPrev = totProvAfterBDDRPrev;
    }
}
