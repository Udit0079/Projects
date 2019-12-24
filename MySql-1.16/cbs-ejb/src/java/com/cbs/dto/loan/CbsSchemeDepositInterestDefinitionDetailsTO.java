/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeDepositInterestDefinitionDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeDepositInterestDefinitionDetailsPKTO cbsSchemeDepositInterestDefinitionDetailsPKTO;
    private String schemeType;
    private String maxDepositPeriodMonths;
    private String maxDepositPeriodDays;
    private String baseAmountInd;
    private String compoundingPeriod;
    private String compoundingBase;
    private String minCompoundingPeriod;
    private String minCompoundingBase;
    private String brokenPeriodMthd;
    private String brokenPeriodBase;
    private String delFlag;

    public String getBaseAmountInd() {
        return baseAmountInd;
    }

    public void setBaseAmountInd(String baseAmountInd) {
        this.baseAmountInd = baseAmountInd;
    }

    public String getBrokenPeriodBase() {
        return brokenPeriodBase;
    }

    public void setBrokenPeriodBase(String brokenPeriodBase) {
        this.brokenPeriodBase = brokenPeriodBase;
    }

    public String getBrokenPeriodMthd() {
        return brokenPeriodMthd;
    }

    public void setBrokenPeriodMthd(String brokenPeriodMthd) {
        this.brokenPeriodMthd = brokenPeriodMthd;
    }

    public CbsSchemeDepositInterestDefinitionDetailsPKTO getCbsSchemeDepositInterestDefinitionDetailsPKTO() {
        return cbsSchemeDepositInterestDefinitionDetailsPKTO;
    }

    public void setCbsSchemeDepositInterestDefinitionDetailsPKTO(CbsSchemeDepositInterestDefinitionDetailsPKTO cbsSchemeDepositInterestDefinitionDetailsPKTO) {
        this.cbsSchemeDepositInterestDefinitionDetailsPKTO = cbsSchemeDepositInterestDefinitionDetailsPKTO;
    }

    public String getCompoundingBase() {
        return compoundingBase;
    }

    public void setCompoundingBase(String compoundingBase) {
        this.compoundingBase = compoundingBase;
    }

    public String getCompoundingPeriod() {
        return compoundingPeriod;
    }

    public void setCompoundingPeriod(String compoundingPeriod) {
        this.compoundingPeriod = compoundingPeriod;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMaxDepositPeriodDays() {
        return maxDepositPeriodDays;
    }

    public void setMaxDepositPeriodDays(String maxDepositPeriodDays) {
        this.maxDepositPeriodDays = maxDepositPeriodDays;
    }

    public String getMaxDepositPeriodMonths() {
        return maxDepositPeriodMonths;
    }

    public void setMaxDepositPeriodMonths(String maxDepositPeriodMonths) {
        this.maxDepositPeriodMonths = maxDepositPeriodMonths;
    }

    public String getMinCompoundingBase() {
        return minCompoundingBase;
    }

    public void setMinCompoundingBase(String minCompoundingBase) {
        this.minCompoundingBase = minCompoundingBase;
    }

    public String getMinCompoundingPeriod() {
        return minCompoundingPeriod;
    }

    public void setMinCompoundingPeriod(String minCompoundingPeriod) {
        this.minCompoundingPeriod = minCompoundingPeriod;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
