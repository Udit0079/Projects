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
public class CbsSchemeInterestOrServiceChargesDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String schemeType;
    private String interestPaidFlag;
    private String interestPayableAccountPlaceholder;
    private String intCollectionFlag;
    private String intRecbleAccountPlaceholder;
    private String serviceChargesFlag;
    private String serviceCollAccountPlaceholder;
    private String parkingAccountTdsPlaceholder;
    private String incomeExpenseAccountInHomeCurrency;
    private String normalProfitAndLossAccountPlaceholderCr;
    private String normalProfitAndLossAccountPlaceholderDr;
    private String penalProfitAndLossAccountPlaceholderDr;
    private String parkingAccountPlaceholder;
    private String chequeAllowedFlag;
    private String micrChequeChrgAccountPlaceholder;
    private String overDueIntPaidAcPlaceholder;
    private String overDueNormalProfitAndLossAccountPlaceholder;
    private String mergeIntPtranFlag;
    private String operativeAccountWithoutEnoughFundsToBeDebited;
    private String advanceInterestAccount;
    private String bookTransScript;
    private String interestCalculationFreqCrType;
    private String interestCalculationFreqCrWeekNo;
    private String interestCalculationFreqCrWeekDay;
    private String interestCalculationFreqCrStartDate;
    private String interestCalculationFreqCrNp;
    private String interestCalculationFreqDrType;
    private String interestCalculationFreqDrWeekNo;
    private String interestCalculationFreqDrWeekDate;
    private String interestCalculationFreqDrStartDate;
    private String interestCalculationFreqDrNp;
    private String limitLevelIntFlag;
    private String interestOnQis;
    private String lookBackPeriod;
    private String interestOnStock;
    private String compoundRestDayFlag;
    private String debitIntCompoundingFreq;
    private Double applyDiscountedIntRate;
    private String principalLossLinePlaceholder;
    private String recoveryLossLinePlaceholer;
    private String chargeOffAccountPlaceholder;
    private String dealerContributionPlaceholder;
    private String interestWaiverDebitPlaceholder;

    public String getAdvanceInterestAccount() {
        return advanceInterestAccount;
    }

    public void setAdvanceInterestAccount(String advanceInterestAccount) {
        this.advanceInterestAccount = advanceInterestAccount;
    }

    public Double getApplyDiscountedIntRate() {
        return applyDiscountedIntRate;
    }

    public void setApplyDiscountedIntRate(Double applyDiscountedIntRate) {
        this.applyDiscountedIntRate = applyDiscountedIntRate;
    }

    public String getBookTransScript() {
        return bookTransScript;
    }

    public void setBookTransScript(String bookTransScript) {
        this.bookTransScript = bookTransScript;
    }

    public String getChargeOffAccountPlaceholder() {
        return chargeOffAccountPlaceholder;
    }

    public void setChargeOffAccountPlaceholder(String chargeOffAccountPlaceholder) {
        this.chargeOffAccountPlaceholder = chargeOffAccountPlaceholder;
    }

    public String getChequeAllowedFlag() {
        return chequeAllowedFlag;
    }

    public void setChequeAllowedFlag(String chequeAllowedFlag) {
        this.chequeAllowedFlag = chequeAllowedFlag;
    }

    public String getCompoundRestDayFlag() {
        return compoundRestDayFlag;
    }

    public void setCompoundRestDayFlag(String compoundRestDayFlag) {
        this.compoundRestDayFlag = compoundRestDayFlag;
    }

    public String getDealerContributionPlaceholder() {
        return dealerContributionPlaceholder;
    }

    public void setDealerContributionPlaceholder(String dealerContributionPlaceholder) {
        this.dealerContributionPlaceholder = dealerContributionPlaceholder;
    }

    public String getDebitIntCompoundingFreq() {
        return debitIntCompoundingFreq;
    }

    public void setDebitIntCompoundingFreq(String debitIntCompoundingFreq) {
        this.debitIntCompoundingFreq = debitIntCompoundingFreq;
    }

    public String getIncomeExpenseAccountInHomeCurrency() {
        return incomeExpenseAccountInHomeCurrency;
    }

    public void setIncomeExpenseAccountInHomeCurrency(String incomeExpenseAccountInHomeCurrency) {
        this.incomeExpenseAccountInHomeCurrency = incomeExpenseAccountInHomeCurrency;
    }

    public String getIntCollectionFlag() {
        return intCollectionFlag;
    }

    public void setIntCollectionFlag(String intCollectionFlag) {
        this.intCollectionFlag = intCollectionFlag;
    }

    public String getIntRecbleAccountPlaceholder() {
        return intRecbleAccountPlaceholder;
    }

    public void setIntRecbleAccountPlaceholder(String intRecbleAccountPlaceholder) {
        this.intRecbleAccountPlaceholder = intRecbleAccountPlaceholder;
    }

    public String getInterestCalculationFreqCrNp() {
        return interestCalculationFreqCrNp;
    }

    public void setInterestCalculationFreqCrNp(String interestCalculationFreqCrNp) {
        this.interestCalculationFreqCrNp = interestCalculationFreqCrNp;
    }

    public String getInterestCalculationFreqCrStartDate() {
        return interestCalculationFreqCrStartDate;
    }

    public void setInterestCalculationFreqCrStartDate(String interestCalculationFreqCrStartDate) {
        this.interestCalculationFreqCrStartDate = interestCalculationFreqCrStartDate;
    }

    public String getInterestCalculationFreqCrType() {
        return interestCalculationFreqCrType;
    }

    public void setInterestCalculationFreqCrType(String interestCalculationFreqCrType) {
        this.interestCalculationFreqCrType = interestCalculationFreqCrType;
    }

    public String getInterestCalculationFreqCrWeekDay() {
        return interestCalculationFreqCrWeekDay;
    }

    public void setInterestCalculationFreqCrWeekDay(String interestCalculationFreqCrWeekDay) {
        this.interestCalculationFreqCrWeekDay = interestCalculationFreqCrWeekDay;
    }

    public String getInterestCalculationFreqCrWeekNo() {
        return interestCalculationFreqCrWeekNo;
    }

    public void setInterestCalculationFreqCrWeekNo(String interestCalculationFreqCrWeekNo) {
        this.interestCalculationFreqCrWeekNo = interestCalculationFreqCrWeekNo;
    }

    public String getInterestCalculationFreqDrNp() {
        return interestCalculationFreqDrNp;
    }

    public void setInterestCalculationFreqDrNp(String interestCalculationFreqDrNp) {
        this.interestCalculationFreqDrNp = interestCalculationFreqDrNp;
    }

    public String getInterestCalculationFreqDrStartDate() {
        return interestCalculationFreqDrStartDate;
    }

    public void setInterestCalculationFreqDrStartDate(String interestCalculationFreqDrStartDate) {
        this.interestCalculationFreqDrStartDate = interestCalculationFreqDrStartDate;
    }

    public String getInterestCalculationFreqDrType() {
        return interestCalculationFreqDrType;
    }

    public void setInterestCalculationFreqDrType(String interestCalculationFreqDrType) {
        this.interestCalculationFreqDrType = interestCalculationFreqDrType;
    }

    public String getInterestCalculationFreqDrWeekDate() {
        return interestCalculationFreqDrWeekDate;
    }

    public void setInterestCalculationFreqDrWeekDate(String interestCalculationFreqDrWeekDate) {
        this.interestCalculationFreqDrWeekDate = interestCalculationFreqDrWeekDate;
    }

    public String getInterestCalculationFreqDrWeekNo() {
        return interestCalculationFreqDrWeekNo;
    }

    public void setInterestCalculationFreqDrWeekNo(String interestCalculationFreqDrWeekNo) {
        this.interestCalculationFreqDrWeekNo = interestCalculationFreqDrWeekNo;
    }

    public String getInterestOnQis() {
        return interestOnQis;
    }

    public void setInterestOnQis(String interestOnQis) {
        this.interestOnQis = interestOnQis;
    }

    public String getInterestOnStock() {
        return interestOnStock;
    }

    public void setInterestOnStock(String interestOnStock) {
        this.interestOnStock = interestOnStock;
    }

    public String getInterestPaidFlag() {
        return interestPaidFlag;
    }

    public void setInterestPaidFlag(String interestPaidFlag) {
        this.interestPaidFlag = interestPaidFlag;
    }

    public String getInterestPayableAccountPlaceholder() {
        return interestPayableAccountPlaceholder;
    }

    public void setInterestPayableAccountPlaceholder(String interestPayableAccountPlaceholder) {
        this.interestPayableAccountPlaceholder = interestPayableAccountPlaceholder;
    }

    public String getInterestWaiverDebitPlaceholder() {
        return interestWaiverDebitPlaceholder;
    }

    public void setInterestWaiverDebitPlaceholder(String interestWaiverDebitPlaceholder) {
        this.interestWaiverDebitPlaceholder = interestWaiverDebitPlaceholder;
    }

    public String getLimitLevelIntFlag() {
        return limitLevelIntFlag;
    }

    public void setLimitLevelIntFlag(String limitLevelIntFlag) {
        this.limitLevelIntFlag = limitLevelIntFlag;
    }

    public String getLookBackPeriod() {
        return lookBackPeriod;
    }

    public void setLookBackPeriod(String lookBackPeriod) {
        this.lookBackPeriod = lookBackPeriod;
    }

    public String getMergeIntPtranFlag() {
        return mergeIntPtranFlag;
    }

    public void setMergeIntPtranFlag(String mergeIntPtranFlag) {
        this.mergeIntPtranFlag = mergeIntPtranFlag;
    }

    public String getMicrChequeChrgAccountPlaceholder() {
        return micrChequeChrgAccountPlaceholder;
    }

    public void setMicrChequeChrgAccountPlaceholder(String micrChequeChrgAccountPlaceholder) {
        this.micrChequeChrgAccountPlaceholder = micrChequeChrgAccountPlaceholder;
    }

    public String getNormalProfitAndLossAccountPlaceholderCr() {
        return normalProfitAndLossAccountPlaceholderCr;
    }

    public void setNormalProfitAndLossAccountPlaceholderCr(String normalProfitAndLossAccountPlaceholderCr) {
        this.normalProfitAndLossAccountPlaceholderCr = normalProfitAndLossAccountPlaceholderCr;
    }

    public String getNormalProfitAndLossAccountPlaceholderDr() {
        return normalProfitAndLossAccountPlaceholderDr;
    }

    public void setNormalProfitAndLossAccountPlaceholderDr(String normalProfitAndLossAccountPlaceholderDr) {
        this.normalProfitAndLossAccountPlaceholderDr = normalProfitAndLossAccountPlaceholderDr;
    }

    public String getOperativeAccountWithoutEnoughFundsToBeDebited() {
        return operativeAccountWithoutEnoughFundsToBeDebited;
    }

    public void setOperativeAccountWithoutEnoughFundsToBeDebited(String operativeAccountWithoutEnoughFundsToBeDebited) {
        this.operativeAccountWithoutEnoughFundsToBeDebited = operativeAccountWithoutEnoughFundsToBeDebited;
    }

    public String getOverDueIntPaidAcPlaceholder() {
        return overDueIntPaidAcPlaceholder;
    }

    public void setOverDueIntPaidAcPlaceholder(String overDueIntPaidAcPlaceholder) {
        this.overDueIntPaidAcPlaceholder = overDueIntPaidAcPlaceholder;
    }

    public String getOverDueNormalProfitAndLossAccountPlaceholder() {
        return overDueNormalProfitAndLossAccountPlaceholder;
    }

    public void setOverDueNormalProfitAndLossAccountPlaceholder(String overDueNormalProfitAndLossAccountPlaceholder) {
        this.overDueNormalProfitAndLossAccountPlaceholder = overDueNormalProfitAndLossAccountPlaceholder;
    }

    public String getParkingAccountPlaceholder() {
        return parkingAccountPlaceholder;
    }

    public void setParkingAccountPlaceholder(String parkingAccountPlaceholder) {
        this.parkingAccountPlaceholder = parkingAccountPlaceholder;
    }

    public String getParkingAccountTdsPlaceholder() {
        return parkingAccountTdsPlaceholder;
    }

    public void setParkingAccountTdsPlaceholder(String parkingAccountTdsPlaceholder) {
        this.parkingAccountTdsPlaceholder = parkingAccountTdsPlaceholder;
    }

    public String getPenalProfitAndLossAccountPlaceholderDr() {
        return penalProfitAndLossAccountPlaceholderDr;
    }

    public void setPenalProfitAndLossAccountPlaceholderDr(String penalProfitAndLossAccountPlaceholderDr) {
        this.penalProfitAndLossAccountPlaceholderDr = penalProfitAndLossAccountPlaceholderDr;
    }

    public String getPrincipalLossLinePlaceholder() {
        return principalLossLinePlaceholder;
    }

    public void setPrincipalLossLinePlaceholder(String principalLossLinePlaceholder) {
        this.principalLossLinePlaceholder = principalLossLinePlaceholder;
    }

    public String getRecoveryLossLinePlaceholer() {
        return recoveryLossLinePlaceholer;
    }

    public void setRecoveryLossLinePlaceholer(String recoveryLossLinePlaceholer) {
        this.recoveryLossLinePlaceholer = recoveryLossLinePlaceholer;
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

    public String getServiceChargesFlag() {
        return serviceChargesFlag;
    }

    public void setServiceChargesFlag(String serviceChargesFlag) {
        this.serviceChargesFlag = serviceChargesFlag;
    }

    public String getServiceCollAccountPlaceholder() {
        return serviceCollAccountPlaceholder;
    }

    public void setServiceCollAccountPlaceholder(String serviceCollAccountPlaceholder) {
        this.serviceCollAccountPlaceholder = serviceCollAccountPlaceholder;
    }
}
