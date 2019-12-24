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
public class CbsSchemeLoanSchemeDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String currencyCode;
    private String schemeType;
    private String loanPeriodMiniMonths;
    private String loanPeriodMiniDays;
    private String loanPeriodMaxMonths;
    private String loanPeriodMaxDays;
    private BigDecimal loanAmountMin;
    private BigDecimal loanAmountMax;
    private String loanRepaymentMethod;
    private String holdInOpenAccountForAmountDue;
    private String upfrontInstallmentCollection;
    private String intBaseMethod;
    private String intProductMethod;
    private String intRouteFlag;
    private String chrargeRouteFlag;
    private String loanIntOrChrgAccountPlaceholder;
    private String equatedInstallments;
    private String eiInAdvance;
    private String eiFormulaFlag;
    private String eiRoundingOffAmount;
    private String eiRoundingOffInd;
    private String compoundingFreq;
    private String eiPaymentFreq;
    private String interestRestFreq;
    private String interestRestBasis;
    private String upfrontInterestCollection;
    private String discountedInterest;
    private String intAmortizationByRule78;

    public String getChrargeRouteFlag() {
        return chrargeRouteFlag;
    }

    public void setChrargeRouteFlag(String chrargeRouteFlag) {
        this.chrargeRouteFlag = chrargeRouteFlag;
    }

    public String getCompoundingFreq() {
        return compoundingFreq;
    }

    public void setCompoundingFreq(String compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDiscountedInterest() {
        return discountedInterest;
    }

    public void setDiscountedInterest(String discountedInterest) {
        this.discountedInterest = discountedInterest;
    }

    public String getEiFormulaFlag() {
        return eiFormulaFlag;
    }

    public void setEiFormulaFlag(String eiFormulaFlag) {
        this.eiFormulaFlag = eiFormulaFlag;
    }

    public String getEiInAdvance() {
        return eiInAdvance;
    }

    public void setEiInAdvance(String eiInAdvance) {
        this.eiInAdvance = eiInAdvance;
    }

    public String getEiPaymentFreq() {
        return eiPaymentFreq;
    }

    public void setEiPaymentFreq(String eiPaymentFreq) {
        this.eiPaymentFreq = eiPaymentFreq;
    }

    public String getEiRoundingOffAmount() {
        return eiRoundingOffAmount;
    }

    public void setEiRoundingOffAmount(String eiRoundingOffAmount) {
        this.eiRoundingOffAmount = eiRoundingOffAmount;
    }

    public String getEiRoundingOffInd() {
        return eiRoundingOffInd;
    }

    public void setEiRoundingOffInd(String eiRoundingOffInd) {
        this.eiRoundingOffInd = eiRoundingOffInd;
    }

    public String getEquatedInstallments() {
        return equatedInstallments;
    }

    public void setEquatedInstallments(String equatedInstallments) {
        this.equatedInstallments = equatedInstallments;
    }

    public String getHoldInOpenAccountForAmountDue() {
        return holdInOpenAccountForAmountDue;
    }

    public void setHoldInOpenAccountForAmountDue(String holdInOpenAccountForAmountDue) {
        this.holdInOpenAccountForAmountDue = holdInOpenAccountForAmountDue;
    }

    public String getIntAmortizationByRule78() {
        return intAmortizationByRule78;
    }

    public void setIntAmortizationByRule78(String intAmortizationByRule78) {
        this.intAmortizationByRule78 = intAmortizationByRule78;
    }

    public String getIntBaseMethod() {
        return intBaseMethod;
    }

    public void setIntBaseMethod(String intBaseMethod) {
        this.intBaseMethod = intBaseMethod;
    }

    public String getIntProductMethod() {
        return intProductMethod;
    }

    public void setIntProductMethod(String intProductMethod) {
        this.intProductMethod = intProductMethod;
    }

    public String getIntRouteFlag() {
        return intRouteFlag;
    }

    public void setIntRouteFlag(String intRouteFlag) {
        this.intRouteFlag = intRouteFlag;
    }

    public String getInterestRestBasis() {
        return interestRestBasis;
    }

    public void setInterestRestBasis(String interestRestBasis) {
        this.interestRestBasis = interestRestBasis;
    }

    public String getInterestRestFreq() {
        return interestRestFreq;
    }

    public void setInterestRestFreq(String interestRestFreq) {
        this.interestRestFreq = interestRestFreq;
    }

    public BigDecimal getLoanAmountMax() {
        return loanAmountMax;
    }

    public void setLoanAmountMax(BigDecimal loanAmountMax) {
        this.loanAmountMax = loanAmountMax;
    }

    public BigDecimal getLoanAmountMin() {
        return loanAmountMin;
    }

    public void setLoanAmountMin(BigDecimal loanAmountMin) {
        this.loanAmountMin = loanAmountMin;
    }

    public String getLoanIntOrChrgAccountPlaceholder() {
        return loanIntOrChrgAccountPlaceholder;
    }

    public void setLoanIntOrChrgAccountPlaceholder(String loanIntOrChrgAccountPlaceholder) {
        this.loanIntOrChrgAccountPlaceholder = loanIntOrChrgAccountPlaceholder;
    }

    public String getLoanPeriodMaxDays() {
        return loanPeriodMaxDays;
    }

    public void setLoanPeriodMaxDays(String loanPeriodMaxDays) {
        this.loanPeriodMaxDays = loanPeriodMaxDays;
    }

    public String getLoanPeriodMaxMonths() {
        return loanPeriodMaxMonths;
    }

    public void setLoanPeriodMaxMonths(String loanPeriodMaxMonths) {
        this.loanPeriodMaxMonths = loanPeriodMaxMonths;
    }

    public String getLoanPeriodMiniDays() {
        return loanPeriodMiniDays;
    }

    public void setLoanPeriodMiniDays(String loanPeriodMiniDays) {
        this.loanPeriodMiniDays = loanPeriodMiniDays;
    }

    public String getLoanPeriodMiniMonths() {
        return loanPeriodMiniMonths;
    }

    public void setLoanPeriodMiniMonths(String loanPeriodMiniMonths) {
        this.loanPeriodMiniMonths = loanPeriodMiniMonths;
    }

    public String getLoanRepaymentMethod() {
        return loanRepaymentMethod;
    }

    public void setLoanRepaymentMethod(String loanRepaymentMethod) {
        this.loanRepaymentMethod = loanRepaymentMethod;
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

    public String getUpfrontInstallmentCollection() {
        return upfrontInstallmentCollection;
    }

    public void setUpfrontInstallmentCollection(String upfrontInstallmentCollection) {
        this.upfrontInstallmentCollection = upfrontInstallmentCollection;
    }

    public String getUpfrontInterestCollection() {
        return upfrontInterestCollection;
    }

    public void setUpfrontInterestCollection(String upfrontInterestCollection) {
        this.upfrontInterestCollection = upfrontInterestCollection;
    }
}
