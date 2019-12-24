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
public class CbsSchemeLoanExceptionDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String currencyCode;
    private String schemeType;
    private String nonConformingLoanPeriod;
    private String nonConformingLoanAmount;
    private String disbGreaterThanLoanAmount;
    private String disbNotConformingToSchedule;
    private String disbDateSanctExpiryDate;
    private String intCalculationNotUpToDate;
    private String transferAmountIsGreaterThanCrBalance;
    private String custIdDiffForLoanAndOpAccount;
    private String interestCollectedExceedsLimit;
    private String waiverOfChargesOrInterest;
    private String overrideSystemCalcEiAmount;
    private String pendingSchedulePayments;
    private String repaymentPerdNotEqualToLoanPerd;
    private String rephasementInterestCalcNotUpToDate;
    private String maxHolidayPeriodExceeded;
    private String prepaymentNotAsPerNotice;
    private String valueDatedNotice;
    private String backValueDatedAccountOpening;

    public String getBackValueDatedAccountOpening() {
        return backValueDatedAccountOpening;
    }

    public void setBackValueDatedAccountOpening(String backValueDatedAccountOpening) {
        this.backValueDatedAccountOpening = backValueDatedAccountOpening;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCustIdDiffForLoanAndOpAccount() {
        return custIdDiffForLoanAndOpAccount;
    }

    public void setCustIdDiffForLoanAndOpAccount(String custIdDiffForLoanAndOpAccount) {
        this.custIdDiffForLoanAndOpAccount = custIdDiffForLoanAndOpAccount;
    }

    public String getDisbDateSanctExpiryDate() {
        return disbDateSanctExpiryDate;
    }

    public void setDisbDateSanctExpiryDate(String disbDateSanctExpiryDate) {
        this.disbDateSanctExpiryDate = disbDateSanctExpiryDate;
    }

    public String getDisbGreaterThanLoanAmount() {
        return disbGreaterThanLoanAmount;
    }

    public void setDisbGreaterThanLoanAmount(String disbGreaterThanLoanAmount) {
        this.disbGreaterThanLoanAmount = disbGreaterThanLoanAmount;
    }

    public String getDisbNotConformingToSchedule() {
        return disbNotConformingToSchedule;
    }

    public void setDisbNotConformingToSchedule(String disbNotConformingToSchedule) {
        this.disbNotConformingToSchedule = disbNotConformingToSchedule;
    }

    public String getIntCalculationNotUpToDate() {
        return intCalculationNotUpToDate;
    }

    public void setIntCalculationNotUpToDate(String intCalculationNotUpToDate) {
        this.intCalculationNotUpToDate = intCalculationNotUpToDate;
    }

    public String getInterestCollectedExceedsLimit() {
        return interestCollectedExceedsLimit;
    }

    public void setInterestCollectedExceedsLimit(String interestCollectedExceedsLimit) {
        this.interestCollectedExceedsLimit = interestCollectedExceedsLimit;
    }

    public String getMaxHolidayPeriodExceeded() {
        return maxHolidayPeriodExceeded;
    }

    public void setMaxHolidayPeriodExceeded(String maxHolidayPeriodExceeded) {
        this.maxHolidayPeriodExceeded = maxHolidayPeriodExceeded;
    }

    public String getNonConformingLoanAmount() {
        return nonConformingLoanAmount;
    }

    public void setNonConformingLoanAmount(String nonConformingLoanAmount) {
        this.nonConformingLoanAmount = nonConformingLoanAmount;
    }

    public String getNonConformingLoanPeriod() {
        return nonConformingLoanPeriod;
    }

    public void setNonConformingLoanPeriod(String nonConformingLoanPeriod) {
        this.nonConformingLoanPeriod = nonConformingLoanPeriod;
    }

    public String getOverrideSystemCalcEiAmount() {
        return overrideSystemCalcEiAmount;
    }

    public void setOverrideSystemCalcEiAmount(String overrideSystemCalcEiAmount) {
        this.overrideSystemCalcEiAmount = overrideSystemCalcEiAmount;
    }

    public String getPendingSchedulePayments() {
        return pendingSchedulePayments;
    }

    public void setPendingSchedulePayments(String pendingSchedulePayments) {
        this.pendingSchedulePayments = pendingSchedulePayments;
    }

    public String getPrepaymentNotAsPerNotice() {
        return prepaymentNotAsPerNotice;
    }

    public void setPrepaymentNotAsPerNotice(String prepaymentNotAsPerNotice) {
        this.prepaymentNotAsPerNotice = prepaymentNotAsPerNotice;
    }

    public String getRepaymentPerdNotEqualToLoanPerd() {
        return repaymentPerdNotEqualToLoanPerd;
    }

    public void setRepaymentPerdNotEqualToLoanPerd(String repaymentPerdNotEqualToLoanPerd) {
        this.repaymentPerdNotEqualToLoanPerd = repaymentPerdNotEqualToLoanPerd;
    }

    public String getRephasementInterestCalcNotUpToDate() {
        return rephasementInterestCalcNotUpToDate;
    }

    public void setRephasementInterestCalcNotUpToDate(String rephasementInterestCalcNotUpToDate) {
        this.rephasementInterestCalcNotUpToDate = rephasementInterestCalcNotUpToDate;
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

    public String getTransferAmountIsGreaterThanCrBalance() {
        return transferAmountIsGreaterThanCrBalance;
    }

    public void setTransferAmountIsGreaterThanCrBalance(String transferAmountIsGreaterThanCrBalance) {
        this.transferAmountIsGreaterThanCrBalance = transferAmountIsGreaterThanCrBalance;
    }

    public String getValueDatedNotice() {
        return valueDatedNotice;
    }

    public void setValueDatedNotice(String valueDatedNotice) {
        this.valueDatedNotice = valueDatedNotice;
    }

    public String getWaiverOfChargesOrInterest() {
        return waiverOfChargesOrInterest;
    }

    public void setWaiverOfChargesOrInterest(String waiverOfChargesOrInterest) {
        this.waiverOfChargesOrInterest = waiverOfChargesOrInterest;
    }
}
