/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CbsSchemeLoanPrepaymentDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String currencyCode;
    private String schemeType;
    private String prepaymentIntReductionMethod;
    private String applyPrepaymentCharges;
    private BigDecimal minAmountForPrepayment;
    private String noPrepaymentChargesAfterMonths;
    private String noPrepaymentChargesAfterDays;
    private BigDecimal limitForPrepaymentInAYear;
    private String limitIndicatorForPrepayment;
    private String yearIndicatorForPrepaymentLimit;
    private String prepaymentNotAcceptedBeforeMonths;
    private String prepaymentNotAcceptedBeforeDays;
    private String payOffIntToBeCollectedTill;
    private String noticeReqdForPrepayment;
    private String minNoticePeriodMonths;
    private String minNoticePeriodDay;
    private String validityOfTheNoticePeriodMonths;
    private String validityofthenoticeperioddays;
    private String eiFlowId;
    private String principalFlowId;
    private String disbursementFlowId;
    private String collectionFlowId;
    private String intDemandFlowId;
    private String penalIntDemandFlowId;
    private String overdueIntDemandFlowId;
    private String pastDueCollectionFlowId;
    private String chargeDemandFlowId;

    public String getApplyPrepaymentCharges() {
        return applyPrepaymentCharges;
    }

    public void setApplyPrepaymentCharges(String applyPrepaymentCharges) {
        this.applyPrepaymentCharges = applyPrepaymentCharges;
    }

    public String getChargeDemandFlowId() {
        return chargeDemandFlowId;
    }

    public void setChargeDemandFlowId(String chargeDemandFlowId) {
        this.chargeDemandFlowId = chargeDemandFlowId;
    }

    public String getCollectionFlowId() {
        return collectionFlowId;
    }

    public void setCollectionFlowId(String collectionFlowId) {
        this.collectionFlowId = collectionFlowId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDisbursementFlowId() {
        return disbursementFlowId;
    }

    public void setDisbursementFlowId(String disbursementFlowId) {
        this.disbursementFlowId = disbursementFlowId;
    }

    public String getEiFlowId() {
        return eiFlowId;
    }

    public void setEiFlowId(String eiFlowId) {
        this.eiFlowId = eiFlowId;
    }

    public String getIntDemandFlowId() {
        return intDemandFlowId;
    }

    public void setIntDemandFlowId(String intDemandFlowId) {
        this.intDemandFlowId = intDemandFlowId;
    }

    public BigDecimal getLimitForPrepaymentInAYear() {
        return limitForPrepaymentInAYear;
    }

    public void setLimitForPrepaymentInAYear(BigDecimal limitForPrepaymentInAYear) {
        this.limitForPrepaymentInAYear = limitForPrepaymentInAYear;
    }

    public String getLimitIndicatorForPrepayment() {
        return limitIndicatorForPrepayment;
    }

    public void setLimitIndicatorForPrepayment(String limitIndicatorForPrepayment) {
        this.limitIndicatorForPrepayment = limitIndicatorForPrepayment;
    }

    public BigDecimal getMinAmountForPrepayment() {
        return minAmountForPrepayment;
    }

    public void setMinAmountForPrepayment(BigDecimal minAmountForPrepayment) {
        this.minAmountForPrepayment = minAmountForPrepayment;
    }

    public String getMinNoticePeriodDay() {
        return minNoticePeriodDay;
    }

    public void setMinNoticePeriodDay(String minNoticePeriodDay) {
        this.minNoticePeriodDay = minNoticePeriodDay;
    }

    public String getMinNoticePeriodMonths() {
        return minNoticePeriodMonths;
    }

    public void setMinNoticePeriodMonths(String minNoticePeriodMonths) {
        this.minNoticePeriodMonths = minNoticePeriodMonths;
    }

    public String getNoPrepaymentChargesAfterDays() {
        return noPrepaymentChargesAfterDays;
    }

    public void setNoPrepaymentChargesAfterDays(String noPrepaymentChargesAfterDays) {
        this.noPrepaymentChargesAfterDays = noPrepaymentChargesAfterDays;
    }

    public String getNoPrepaymentChargesAfterMonths() {
        return noPrepaymentChargesAfterMonths;
    }

    public void setNoPrepaymentChargesAfterMonths(String noPrepaymentChargesAfterMonths) {
        this.noPrepaymentChargesAfterMonths = noPrepaymentChargesAfterMonths;
    }

    public String getNoticeReqdForPrepayment() {
        return noticeReqdForPrepayment;
    }

    public void setNoticeReqdForPrepayment(String noticeReqdForPrepayment) {
        this.noticeReqdForPrepayment = noticeReqdForPrepayment;
    }

    public String getOverdueIntDemandFlowId() {
        return overdueIntDemandFlowId;
    }

    public void setOverdueIntDemandFlowId(String overdueIntDemandFlowId) {
        this.overdueIntDemandFlowId = overdueIntDemandFlowId;
    }

    public String getPastDueCollectionFlowId() {
        return pastDueCollectionFlowId;
    }

    public void setPastDueCollectionFlowId(String pastDueCollectionFlowId) {
        this.pastDueCollectionFlowId = pastDueCollectionFlowId;
    }

    public String getPayOffIntToBeCollectedTill() {
        return payOffIntToBeCollectedTill;
    }

    public void setPayOffIntToBeCollectedTill(String payOffIntToBeCollectedTill) {
        this.payOffIntToBeCollectedTill = payOffIntToBeCollectedTill;
    }

    public String getPenalIntDemandFlowId() {
        return penalIntDemandFlowId;
    }

    public void setPenalIntDemandFlowId(String penalIntDemandFlowId) {
        this.penalIntDemandFlowId = penalIntDemandFlowId;
    }

    public String getPrepaymentIntReductionMethod() {
        return prepaymentIntReductionMethod;
    }

    public void setPrepaymentIntReductionMethod(String prepaymentIntReductionMethod) {
        this.prepaymentIntReductionMethod = prepaymentIntReductionMethod;
    }

    public String getPrepaymentNotAcceptedBeforeDays() {
        return prepaymentNotAcceptedBeforeDays;
    }

    public void setPrepaymentNotAcceptedBeforeDays(String prepaymentNotAcceptedBeforeDays) {
        this.prepaymentNotAcceptedBeforeDays = prepaymentNotAcceptedBeforeDays;
    }

    public String getPrepaymentNotAcceptedBeforeMonths() {
        return prepaymentNotAcceptedBeforeMonths;
    }

    public void setPrepaymentNotAcceptedBeforeMonths(String prepaymentNotAcceptedBeforeMonths) {
        this.prepaymentNotAcceptedBeforeMonths = prepaymentNotAcceptedBeforeMonths;
    }

    public String getPrincipalFlowId() {
        return principalFlowId;
    }

    public void setPrincipalFlowId(String principalFlowId) {
        this.principalFlowId = principalFlowId;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getValidityOfTheNoticePeriodMonths() {
        return validityOfTheNoticePeriodMonths;
    }

    public void setValidityOfTheNoticePeriodMonths(String validityOfTheNoticePeriodMonths) {
        this.validityOfTheNoticePeriodMonths = validityOfTheNoticePeriodMonths;
    }

    public String getValidityofthenoticeperioddays() {
        return validityofthenoticeperioddays;
    }

    public void setValidityofthenoticeperioddays(String validityofthenoticeperioddays) {
        this.validityofthenoticeperioddays = validityofthenoticeperioddays;
    }

    public String getYearIndicatorForPrepaymentLimit() {
        return yearIndicatorForPrepaymentLimit;
    }

    public void setYearIndicatorForPrepaymentLimit(String yearIndicatorForPrepaymentLimit) {
        this.yearIndicatorForPrepaymentLimit = yearIndicatorForPrepaymentLimit;
    }
}
