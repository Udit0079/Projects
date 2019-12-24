/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_loan_scheme_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findAll", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanPeriodMiniMonths", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanPeriodMiniMonths = :loanPeriodMiniMonths"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanPeriodMiniDays", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanPeriodMiniDays = :loanPeriodMiniDays"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanPeriodMaxMonths", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanPeriodMaxMonths = :loanPeriodMaxMonths"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanPeriodMaxDays", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanPeriodMaxDays = :loanPeriodMaxDays"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanAmountMin", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanAmountMin = :loanAmountMin"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanAmountMax", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanAmountMax = :loanAmountMax"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanRepaymentMethod", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanRepaymentMethod = :loanRepaymentMethod"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByHoldInOpenAccountForAmountDue", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.holdInOpenAccountForAmountDue = :holdInOpenAccountForAmountDue"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByUpfrontInstallmentCollection", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.upfrontInstallmentCollection = :upfrontInstallmentCollection"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByIntBaseMethod", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.intBaseMethod = :intBaseMethod"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByIntProductMethod", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.intProductMethod = :intProductMethod"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByIntRouteFlag", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.intRouteFlag = :intRouteFlag"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByChrargeRouteFlag", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.chrargeRouteFlag = :chrargeRouteFlag"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByLoanIntOrChrgAccountPlaceholder", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.loanIntOrChrgAccountPlaceholder = :loanIntOrChrgAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByEquatedInstallments", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.equatedInstallments = :equatedInstallments"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByEiInAdvance", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.eiInAdvance = :eiInAdvance"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByEiFormulaFlag", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.eiFormulaFlag = :eiFormulaFlag"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByEiRoundingOffAmount", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.eiRoundingOffAmount = :eiRoundingOffAmount"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByEiRoundingOffInd", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.eiRoundingOffInd = :eiRoundingOffInd"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByCompoundingFreq", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.compoundingFreq = :compoundingFreq"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByEiPaymentFreq", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.eiPaymentFreq = :eiPaymentFreq"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByInterestRestFreq", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.interestRestFreq = :interestRestFreq"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByInterestRestBasis", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.interestRestBasis = :interestRestBasis"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByUpfrontInterestCollection", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.upfrontInterestCollection = :upfrontInterestCollection"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByDiscountedInterest", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.discountedInterest = :discountedInterest"),
    @NamedQuery(name = "CbsSchemeLoanSchemeDetails.findByIntAmortizationByRule78", query = "SELECT c FROM CbsSchemeLoanSchemeDetails c WHERE c.intAmortizationByRule78 = :intAmortizationByRule78")})
public class CbsSchemeLoanSchemeDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "LOAN_PERIOD_MINI_MONTHS")
    private String loanPeriodMiniMonths;
    @Size(max = 3)
    @Column(name = "LOAN_PERIOD_MINI_DAYS")
    private String loanPeriodMiniDays;
    @Size(max = 3)
    @Column(name = "LOAN_PERIOD_MAX_MONTHS")
    private String loanPeriodMaxMonths;
    @Size(max = 3)
    @Column(name = "LOAN_PERIOD_MAX_DAYS")
    private String loanPeriodMaxDays;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LOAN_AMOUNT_MIN")
    private BigDecimal loanAmountMin;
    @Column(name = "LOAN_AMOUNT_MAX")
    private BigDecimal loanAmountMax;
    @Size(max = 1)
    @Column(name = "LOAN_REPAYMENT_METHOD")
    private String loanRepaymentMethod;
    @Size(max = 1)
    @Column(name = "HOLD_IN_OPEN_ACCOUNT_FOR_AMOUNT_DUE")
    private String holdInOpenAccountForAmountDue;
    @Size(max = 1)
    @Column(name = "UPFRONT_INSTALLMENT_COLLECTION")
    private String upfrontInstallmentCollection;
    @Size(max = 1)
    @Column(name = "INT_BASE_METHOD")
    private String intBaseMethod;
    @Size(max = 1)
    @Column(name = "INT_PRODUCT_METHOD")
    private String intProductMethod;
    @Size(max = 1)
    @Column(name = "INT_ROUTE_FLAG")
    private String intRouteFlag;
    @Size(max = 1)
    @Column(name = "CHRARGE_ROUTE_FLAG")
    private String chrargeRouteFlag;
    @Size(max = 12)
    @Column(name = "LOAN_INT_OR_CHRG_ACCOUNT_PLACEHOLDER")
    private String loanIntOrChrgAccountPlaceholder;
    @Size(max = 1)
    @Column(name = "EQUATED_INSTALLMENTS")
    private String equatedInstallments;
    @Size(max = 1)
    @Column(name = "EI_IN_ADVANCE")
    private String eiInAdvance;
    @Size(max = 1)
    @Column(name = "EI_FORMULA_FLAG")
    private String eiFormulaFlag;
    @Size(max = 1)
    @Column(name = "EI_ROUNDING_OFF_AMOUNT")
    private String eiRoundingOffAmount;
    @Size(max = 1)
    @Column(name = "EI_ROUNDING_OFF_IND")
    private String eiRoundingOffInd;
    @Size(max = 1)
    @Column(name = "COMPOUNDING_FREQ")
    private String compoundingFreq;
    @Size(max = 1)
    @Column(name = "EI_PAYMENT_FREQ")
    private String eiPaymentFreq;
    @Size(max = 1)
    @Column(name = "INTEREST_REST_FREQ")
    private String interestRestFreq;
    @Size(max = 1)
    @Column(name = "INTEREST_REST_BASIS")
    private String interestRestBasis;
    @Size(max = 1)
    @Column(name = "UPFRONT_INTEREST_COLLECTION")
    private String upfrontInterestCollection;
    @Size(max = 1)
    @Column(name = "DISCOUNTED_INTEREST")
    private String discountedInterest;
    @Size(max = 1)
    @Column(name = "INT_AMORTIZATION_BY_RULE78")
    private String intAmortizationByRule78;

    public CbsSchemeLoanSchemeDetails() {
    }

    public CbsSchemeLoanSchemeDetails(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getLoanPeriodMiniMonths() {
        return loanPeriodMiniMonths;
    }

    public void setLoanPeriodMiniMonths(String loanPeriodMiniMonths) {
        this.loanPeriodMiniMonths = loanPeriodMiniMonths;
    }

    public String getLoanPeriodMiniDays() {
        return loanPeriodMiniDays;
    }

    public void setLoanPeriodMiniDays(String loanPeriodMiniDays) {
        this.loanPeriodMiniDays = loanPeriodMiniDays;
    }

    public String getLoanPeriodMaxMonths() {
        return loanPeriodMaxMonths;
    }

    public void setLoanPeriodMaxMonths(String loanPeriodMaxMonths) {
        this.loanPeriodMaxMonths = loanPeriodMaxMonths;
    }

    public String getLoanPeriodMaxDays() {
        return loanPeriodMaxDays;
    }

    public void setLoanPeriodMaxDays(String loanPeriodMaxDays) {
        this.loanPeriodMaxDays = loanPeriodMaxDays;
    }

    public BigDecimal getLoanAmountMin() {
        return loanAmountMin;
    }

    public void setLoanAmountMin(BigDecimal loanAmountMin) {
        this.loanAmountMin = loanAmountMin;
    }

    public BigDecimal getLoanAmountMax() {
        return loanAmountMax;
    }

    public void setLoanAmountMax(BigDecimal loanAmountMax) {
        this.loanAmountMax = loanAmountMax;
    }

    public String getLoanRepaymentMethod() {
        return loanRepaymentMethod;
    }

    public void setLoanRepaymentMethod(String loanRepaymentMethod) {
        this.loanRepaymentMethod = loanRepaymentMethod;
    }

    public String getHoldInOpenAccountForAmountDue() {
        return holdInOpenAccountForAmountDue;
    }

    public void setHoldInOpenAccountForAmountDue(String holdInOpenAccountForAmountDue) {
        this.holdInOpenAccountForAmountDue = holdInOpenAccountForAmountDue;
    }

    public String getUpfrontInstallmentCollection() {
        return upfrontInstallmentCollection;
    }

    public void setUpfrontInstallmentCollection(String upfrontInstallmentCollection) {
        this.upfrontInstallmentCollection = upfrontInstallmentCollection;
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

    public String getChrargeRouteFlag() {
        return chrargeRouteFlag;
    }

    public void setChrargeRouteFlag(String chrargeRouteFlag) {
        this.chrargeRouteFlag = chrargeRouteFlag;
    }

    public String getLoanIntOrChrgAccountPlaceholder() {
        return loanIntOrChrgAccountPlaceholder;
    }

    public void setLoanIntOrChrgAccountPlaceholder(String loanIntOrChrgAccountPlaceholder) {
        this.loanIntOrChrgAccountPlaceholder = loanIntOrChrgAccountPlaceholder;
    }

    public String getEquatedInstallments() {
        return equatedInstallments;
    }

    public void setEquatedInstallments(String equatedInstallments) {
        this.equatedInstallments = equatedInstallments;
    }

    public String getEiInAdvance() {
        return eiInAdvance;
    }

    public void setEiInAdvance(String eiInAdvance) {
        this.eiInAdvance = eiInAdvance;
    }

    public String getEiFormulaFlag() {
        return eiFormulaFlag;
    }

    public void setEiFormulaFlag(String eiFormulaFlag) {
        this.eiFormulaFlag = eiFormulaFlag;
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

    public String getCompoundingFreq() {
        return compoundingFreq;
    }

    public void setCompoundingFreq(String compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public String getEiPaymentFreq() {
        return eiPaymentFreq;
    }

    public void setEiPaymentFreq(String eiPaymentFreq) {
        this.eiPaymentFreq = eiPaymentFreq;
    }

    public String getInterestRestFreq() {
        return interestRestFreq;
    }

    public void setInterestRestFreq(String interestRestFreq) {
        this.interestRestFreq = interestRestFreq;
    }

    public String getInterestRestBasis() {
        return interestRestBasis;
    }

    public void setInterestRestBasis(String interestRestBasis) {
        this.interestRestBasis = interestRestBasis;
    }

    public String getUpfrontInterestCollection() {
        return upfrontInterestCollection;
    }

    public void setUpfrontInterestCollection(String upfrontInterestCollection) {
        this.upfrontInterestCollection = upfrontInterestCollection;
    }

    public String getDiscountedInterest() {
        return discountedInterest;
    }

    public void setDiscountedInterest(String discountedInterest) {
        this.discountedInterest = discountedInterest;
    }

    public String getIntAmortizationByRule78() {
        return intAmortizationByRule78;
    }

    public void setIntAmortizationByRule78(String intAmortizationByRule78) {
        this.intAmortizationByRule78 = intAmortizationByRule78;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schemeCode != null ? schemeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeLoanSchemeDetails)) {
            return false;
        }
        CbsSchemeLoanSchemeDetails other = (CbsSchemeLoanSchemeDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanSchemeDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
