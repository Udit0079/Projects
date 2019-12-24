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
public class CbsSchemeLoanInterestDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String currencyCode;
    private String schemeType;
    private String intOnPrincipal;
    private String penalIntOnPrincipalDemandOverdue;
    private String principalDemandOverdueAtEndOfMonths;
    private String principalOverduePeriodMonths;
    private String principalOverduePeriodDays;
    private String intOnIntDemand;
    private String penalIntOnIntDemandOverdue;
    private String intDemandOverdueAtEndOfMonth;
    private String intOverduePeriodMonths;
    private String intOverduePeriodDays;
    private String overdueIntOnPrincipal;
    private String applyPreferentialForOverdueInt;
    private String intRateBasedOnLoanAmount;
    private String applyLateFeeForDelayedPayment;
    private String gracePeriodForLateFeeMonths;
    private String gracePeriodForLateFeeDays;
    private BigDecimal toleranceLimitForDpdCycleAmount;
    private String toleranceLimitForDpdCycleType;
    private String considerToleranceForLateFee;
    private String createIntDemandFromRepSchedule;
    private String rephasementCarryOverdueDemands;
    private String priorityLoan;
    private String agriLoan;
    private BigDecimal intLimit;
    private String coveredByDicge;
    private String dicgcFeeFlowId;
    private String dicgcFeeAccountPlaceholer;
    private String hirerDetails;
    private String aodOrAosType;
    private String subsidyAvailable;
    private String refinanceScheme;

    public String getAgriLoan() {
        return agriLoan;
    }

    public void setAgriLoan(String agriLoan) {
        this.agriLoan = agriLoan;
    }

    public String getAodOrAosType() {
        return aodOrAosType;
    }

    public void setAodOrAosType(String aodOrAosType) {
        this.aodOrAosType = aodOrAosType;
    }

    public String getApplyLateFeeForDelayedPayment() {
        return applyLateFeeForDelayedPayment;
    }

    public void setApplyLateFeeForDelayedPayment(String applyLateFeeForDelayedPayment) {
        this.applyLateFeeForDelayedPayment = applyLateFeeForDelayedPayment;
    }

    public String getApplyPreferentialForOverdueInt() {
        return applyPreferentialForOverdueInt;
    }

    public void setApplyPreferentialForOverdueInt(String applyPreferentialForOverdueInt) {
        this.applyPreferentialForOverdueInt = applyPreferentialForOverdueInt;
    }

    public String getConsiderToleranceForLateFee() {
        return considerToleranceForLateFee;
    }

    public void setConsiderToleranceForLateFee(String considerToleranceForLateFee) {
        this.considerToleranceForLateFee = considerToleranceForLateFee;
    }

    public String getCoveredByDicge() {
        return coveredByDicge;
    }

    public void setCoveredByDicge(String coveredByDicge) {
        this.coveredByDicge = coveredByDicge;
    }

    public String getCreateIntDemandFromRepSchedule() {
        return createIntDemandFromRepSchedule;
    }

    public void setCreateIntDemandFromRepSchedule(String createIntDemandFromRepSchedule) {
        this.createIntDemandFromRepSchedule = createIntDemandFromRepSchedule;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDicgcFeeAccountPlaceholer() {
        return dicgcFeeAccountPlaceholer;
    }

    public void setDicgcFeeAccountPlaceholer(String dicgcFeeAccountPlaceholer) {
        this.dicgcFeeAccountPlaceholer = dicgcFeeAccountPlaceholer;
    }

    public String getDicgcFeeFlowId() {
        return dicgcFeeFlowId;
    }

    public void setDicgcFeeFlowId(String dicgcFeeFlowId) {
        this.dicgcFeeFlowId = dicgcFeeFlowId;
    }

    public String getGracePeriodForLateFeeDays() {
        return gracePeriodForLateFeeDays;
    }

    public void setGracePeriodForLateFeeDays(String gracePeriodForLateFeeDays) {
        this.gracePeriodForLateFeeDays = gracePeriodForLateFeeDays;
    }

    public String getGracePeriodForLateFeeMonths() {
        return gracePeriodForLateFeeMonths;
    }

    public void setGracePeriodForLateFeeMonths(String gracePeriodForLateFeeMonths) {
        this.gracePeriodForLateFeeMonths = gracePeriodForLateFeeMonths;
    }

    public String getHirerDetails() {
        return hirerDetails;
    }

    public void setHirerDetails(String hirerDetails) {
        this.hirerDetails = hirerDetails;
    }

    public String getIntDemandOverdueAtEndOfMonth() {
        return intDemandOverdueAtEndOfMonth;
    }

    public void setIntDemandOverdueAtEndOfMonth(String intDemandOverdueAtEndOfMonth) {
        this.intDemandOverdueAtEndOfMonth = intDemandOverdueAtEndOfMonth;
    }

    public BigDecimal getIntLimit() {
        return intLimit;
    }

    public void setIntLimit(BigDecimal intLimit) {
        this.intLimit = intLimit;
    }

    public String getIntOnIntDemand() {
        return intOnIntDemand;
    }

    public void setIntOnIntDemand(String intOnIntDemand) {
        this.intOnIntDemand = intOnIntDemand;
    }

    public String getIntOnPrincipal() {
        return intOnPrincipal;
    }

    public void setIntOnPrincipal(String intOnPrincipal) {
        this.intOnPrincipal = intOnPrincipal;
    }

    public String getIntOverduePeriodDays() {
        return intOverduePeriodDays;
    }

    public void setIntOverduePeriodDays(String intOverduePeriodDays) {
        this.intOverduePeriodDays = intOverduePeriodDays;
    }

    public String getIntOverduePeriodMonths() {
        return intOverduePeriodMonths;
    }

    public void setIntOverduePeriodMonths(String intOverduePeriodMonths) {
        this.intOverduePeriodMonths = intOverduePeriodMonths;
    }

    public String getIntRateBasedOnLoanAmount() {
        return intRateBasedOnLoanAmount;
    }

    public void setIntRateBasedOnLoanAmount(String intRateBasedOnLoanAmount) {
        this.intRateBasedOnLoanAmount = intRateBasedOnLoanAmount;
    }

    public String getOverdueIntOnPrincipal() {
        return overdueIntOnPrincipal;
    }

    public void setOverdueIntOnPrincipal(String overdueIntOnPrincipal) {
        this.overdueIntOnPrincipal = overdueIntOnPrincipal;
    }

    public String getPenalIntOnIntDemandOverdue() {
        return penalIntOnIntDemandOverdue;
    }

    public void setPenalIntOnIntDemandOverdue(String penalIntOnIntDemandOverdue) {
        this.penalIntOnIntDemandOverdue = penalIntOnIntDemandOverdue;
    }

    public String getPenalIntOnPrincipalDemandOverdue() {
        return penalIntOnPrincipalDemandOverdue;
    }

    public void setPenalIntOnPrincipalDemandOverdue(String penalIntOnPrincipalDemandOverdue) {
        this.penalIntOnPrincipalDemandOverdue = penalIntOnPrincipalDemandOverdue;
    }

    public String getPrincipalDemandOverdueAtEndOfMonths() {
        return principalDemandOverdueAtEndOfMonths;
    }

    public void setPrincipalDemandOverdueAtEndOfMonths(String principalDemandOverdueAtEndOfMonths) {
        this.principalDemandOverdueAtEndOfMonths = principalDemandOverdueAtEndOfMonths;
    }

    public String getPrincipalOverduePeriodDays() {
        return principalOverduePeriodDays;
    }

    public void setPrincipalOverduePeriodDays(String principalOverduePeriodDays) {
        this.principalOverduePeriodDays = principalOverduePeriodDays;
    }

    public String getPrincipalOverduePeriodMonths() {
        return principalOverduePeriodMonths;
    }

    public void setPrincipalOverduePeriodMonths(String principalOverduePeriodMonths) {
        this.principalOverduePeriodMonths = principalOverduePeriodMonths;
    }

    public String getPriorityLoan() {
        return priorityLoan;
    }

    public void setPriorityLoan(String priorityLoan) {
        this.priorityLoan = priorityLoan;
    }

    public String getRefinanceScheme() {
        return refinanceScheme;
    }

    public void setRefinanceScheme(String refinanceScheme) {
        this.refinanceScheme = refinanceScheme;
    }

    public String getRephasementCarryOverdueDemands() {
        return rephasementCarryOverdueDemands;
    }

    public void setRephasementCarryOverdueDemands(String rephasementCarryOverdueDemands) {
        this.rephasementCarryOverdueDemands = rephasementCarryOverdueDemands;
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

    public String getSubsidyAvailable() {
        return subsidyAvailable;
    }

    public void setSubsidyAvailable(String subsidyAvailable) {
        this.subsidyAvailable = subsidyAvailable;
    }

    public BigDecimal getToleranceLimitForDpdCycleAmount() {
        return toleranceLimitForDpdCycleAmount;
    }

    public void setToleranceLimitForDpdCycleAmount(BigDecimal toleranceLimitForDpdCycleAmount) {
        this.toleranceLimitForDpdCycleAmount = toleranceLimitForDpdCycleAmount;
    }

    public String getToleranceLimitForDpdCycleType() {
        return toleranceLimitForDpdCycleType;
    }

    public void setToleranceLimitForDpdCycleType(String toleranceLimitForDpdCycleType) {
        this.toleranceLimitForDpdCycleType = toleranceLimitForDpdCycleType;
    }
}
