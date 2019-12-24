/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class TopLoanPojo implements Serializable {

    private Integer sno;
    private String accountNo;
    private String borrowerName;
    private BigDecimal outStanding;
    private BigDecimal sanctionLimit;
    private String nature;
    private String sector;
    private String subSector;
    private BigDecimal funded;
    private BigDecimal nonFunded;
    private BigDecimal percToAdvance;
    private BigDecimal capitalFund;
    private BigDecimal percToCapital;
    private String riskClassification;
    private String dataClass;
    private String rowCount;
    private Integer custId;
    private Integer groupCustId;
    private String groupName;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public BigDecimal getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(BigDecimal outStanding) {
        this.outStanding = outStanding;
    }

    public BigDecimal getSanctionLimit() {
        return sanctionLimit;
    }

    public void setSanctionLimit(BigDecimal sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSubSector() {
        return subSector;
    }

    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public BigDecimal getFunded() {
        return funded;
    }

    public void setFunded(BigDecimal funded) {
        this.funded = funded;
    }

    public BigDecimal getNonFunded() {
        return nonFunded;
    }

    public void setNonFunded(BigDecimal nonFunded) {
        this.nonFunded = nonFunded;
    }

    public BigDecimal getPercToAdvance() {
        return percToAdvance;
    }

    public void setPercToAdvance(BigDecimal percToAdvance) {
        this.percToAdvance = percToAdvance;
    }

    public BigDecimal getCapitalFund() {
        return capitalFund;
    }

    public void setCapitalFund(BigDecimal capitalFund) {
        this.capitalFund = capitalFund;
    }

    public BigDecimal getPercToCapital() {
        return percToCapital;
    }

    public void setPercToCapital(BigDecimal percToCapital) {
        this.percToCapital = percToCapital;
    }

    public String getRiskClassification() {
        return riskClassification;
    }

    public void setRiskClassification(String riskClassification) {
        this.riskClassification = riskClassification;
    }

    public String getDataClass() {
        return dataClass;
    }

    public void setDataClass(String dataClass) {
        this.dataClass = dataClass;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }
    public Integer getGroupCustId() {
        return groupCustId;
    }
    public void setGroupCustId(Integer groupCustId) {
        this.groupCustId = groupCustId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }    
    
}
