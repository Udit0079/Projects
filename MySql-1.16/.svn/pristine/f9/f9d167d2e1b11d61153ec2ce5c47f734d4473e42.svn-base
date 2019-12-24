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
@Table(name = "cbs_scheme_loan_prepayment_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findAll", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPrepaymentIntReductionMethod", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.prepaymentIntReductionMethod = :prepaymentIntReductionMethod"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByApplyPrepaymentCharges", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.applyPrepaymentCharges = :applyPrepaymentCharges"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByMinAmountForPrepayment", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.minAmountForPrepayment = :minAmountForPrepayment"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByNoPrepaymentChargesAfterMonths", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.noPrepaymentChargesAfterMonths = :noPrepaymentChargesAfterMonths"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByNoPrepaymentChargesAfterDays", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.noPrepaymentChargesAfterDays = :noPrepaymentChargesAfterDays"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByLimitForPrepaymentInAYear", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.limitForPrepaymentInAYear = :limitForPrepaymentInAYear"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByLimitIndicatorForPrepayment", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.limitIndicatorForPrepayment = :limitIndicatorForPrepayment"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByYearIndicatorForPrepaymentLimit", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.yearIndicatorForPrepaymentLimit = :yearIndicatorForPrepaymentLimit"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPrepaymentNotAcceptedBeforeMonths", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.prepaymentNotAcceptedBeforeMonths = :prepaymentNotAcceptedBeforeMonths"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPrepaymentNotAcceptedBeforeDays", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.prepaymentNotAcceptedBeforeDays = :prepaymentNotAcceptedBeforeDays"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPayOffIntToBeCollectedTill", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.payOffIntToBeCollectedTill = :payOffIntToBeCollectedTill"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByNoticeReqdForPrepayment", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.noticeReqdForPrepayment = :noticeReqdForPrepayment"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByMinNoticePeriodMonths", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.minNoticePeriodMonths = :minNoticePeriodMonths"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByMinNoticePeriodDay", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.minNoticePeriodDay = :minNoticePeriodDay"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByValidityOfTheNoticePeriodMonths", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.validityOfTheNoticePeriodMonths = :validityOfTheNoticePeriodMonths"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByValidityofthenoticeperioddays", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.validityofthenoticeperioddays = :validityofthenoticeperioddays"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByEiFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.eiFlowId = :eiFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPrincipalFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.principalFlowId = :principalFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByDisbursementFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.disbursementFlowId = :disbursementFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByCollectionFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.collectionFlowId = :collectionFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByIntDemandFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.intDemandFlowId = :intDemandFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPenalIntDemandFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.penalIntDemandFlowId = :penalIntDemandFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByOverdueIntDemandFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.overdueIntDemandFlowId = :overdueIntDemandFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByPastDueCollectionFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.pastDueCollectionFlowId = :pastDueCollectionFlowId"),
    @NamedQuery(name = "CbsSchemeLoanPrepaymentDetails.findByChargeDemandFlowId", query = "SELECT c FROM CbsSchemeLoanPrepaymentDetails c WHERE c.chargeDemandFlowId = :chargeDemandFlowId")})
public class CbsSchemeLoanPrepaymentDetails extends BaseEntity implements Serializable {
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
    @Size(max = 1)
    @Column(name = "PREPAYMENT_INT_REDUCTION_METHOD")
    private String prepaymentIntReductionMethod;
    @Size(max = 1)
    @Column(name = "APPLY_PREPAYMENT_CHARGES")
    private String applyPrepaymentCharges;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MIN_AMOUNT_FOR_PREPAYMENT")
    private BigDecimal minAmountForPrepayment;
    @Size(max = 3)
    @Column(name = "NO_PREPAYMENT_CHARGES_AFTER_MONTHS")
    private String noPrepaymentChargesAfterMonths;
    @Size(max = 3)
    @Column(name = "NO_PREPAYMENT_CHARGES_AFTER_DAYS")
    private String noPrepaymentChargesAfterDays;
    @Column(name = "LIMIT_FOR_PREPAYMENT_IN_A_YEAR")
    private BigDecimal limitForPrepaymentInAYear;
    @Size(max = 1)
    @Column(name = "LIMIT_INDICATOR_FOR_PREPAYMENT")
    private String limitIndicatorForPrepayment;
    @Size(max = 1)
    @Column(name = "YEAR_INDICATOR_FOR_PREPAYMENT_LIMIT")
    private String yearIndicatorForPrepaymentLimit;
    @Size(max = 3)
    @Column(name = "PREPAYMENT_NOT_ACCEPTED_BEFORE_MONTHS")
    private String prepaymentNotAcceptedBeforeMonths;
    @Size(max = 3)
    @Column(name = "PREPAYMENT_NOT_ACCEPTED_BEFORE_DAYS")
    private String prepaymentNotAcceptedBeforeDays;
    @Size(max = 1)
    @Column(name = "PAY_OFF_INT_TO_BE_COLLECTED_TILL")
    private String payOffIntToBeCollectedTill;
    @Size(max = 1)
    @Column(name = "NOTICE_REQD_FOR_PREPAYMENT")
    private String noticeReqdForPrepayment;
    @Size(max = 3)
    @Column(name = "MIN_NOTICE_PERIOD_MONTHS")
    private String minNoticePeriodMonths;
    @Size(max = 3)
    @Column(name = "MIN_NOTICE_PERIOD_DAY")
    private String minNoticePeriodDay;
    @Size(max = 3)
    @Column(name = "VALIDITY_OF_THE_NOTICE_PERIOD_MONTHS")
    private String validityOfTheNoticePeriodMonths;
    @Size(max = 3)
    @Column(name = "VALIDITYOFTHENOTICEPERIODDAYS")
    private String validityofthenoticeperioddays;
    @Size(max = 5)
    @Column(name = "EI_FLOW_ID")
    private String eiFlowId;
    @Size(max = 5)
    @Column(name = "PRINCIPAL_FLOW_ID")
    private String principalFlowId;
    @Size(max = 5)
    @Column(name = "DISBURSEMENT_FLOW_ID")
    private String disbursementFlowId;
    @Size(max = 5)
    @Column(name = "COLLECTION_FLOW_ID")
    private String collectionFlowId;
    @Size(max = 5)
    @Column(name = "INT_DEMAND_FLOW_ID")
    private String intDemandFlowId;
    @Size(max = 5)
    @Column(name = "PENAL_INT_DEMAND_FLOW_ID")
    private String penalIntDemandFlowId;
    @Size(max = 5)
    @Column(name = "OVERDUE_INT_DEMAND_FLOW_ID")
    private String overdueIntDemandFlowId;
    @Size(max = 5)
    @Column(name = "PAST_DUE_COLLECTION_FLOW_ID")
    private String pastDueCollectionFlowId;
    @Size(max = 5)
    @Column(name = "CHARGE_DEMAND_FLOW_ID")
    private String chargeDemandFlowId;

    public CbsSchemeLoanPrepaymentDetails() {
    }

    public CbsSchemeLoanPrepaymentDetails(String schemeCode) {
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

    public String getPrepaymentIntReductionMethod() {
        return prepaymentIntReductionMethod;
    }

    public void setPrepaymentIntReductionMethod(String prepaymentIntReductionMethod) {
        this.prepaymentIntReductionMethod = prepaymentIntReductionMethod;
    }

    public String getApplyPrepaymentCharges() {
        return applyPrepaymentCharges;
    }

    public void setApplyPrepaymentCharges(String applyPrepaymentCharges) {
        this.applyPrepaymentCharges = applyPrepaymentCharges;
    }

    public BigDecimal getMinAmountForPrepayment() {
        return minAmountForPrepayment;
    }

    public void setMinAmountForPrepayment(BigDecimal minAmountForPrepayment) {
        this.minAmountForPrepayment = minAmountForPrepayment;
    }

    public String getNoPrepaymentChargesAfterMonths() {
        return noPrepaymentChargesAfterMonths;
    }

    public void setNoPrepaymentChargesAfterMonths(String noPrepaymentChargesAfterMonths) {
        this.noPrepaymentChargesAfterMonths = noPrepaymentChargesAfterMonths;
    }

    public String getNoPrepaymentChargesAfterDays() {
        return noPrepaymentChargesAfterDays;
    }

    public void setNoPrepaymentChargesAfterDays(String noPrepaymentChargesAfterDays) {
        this.noPrepaymentChargesAfterDays = noPrepaymentChargesAfterDays;
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

    public String getYearIndicatorForPrepaymentLimit() {
        return yearIndicatorForPrepaymentLimit;
    }

    public void setYearIndicatorForPrepaymentLimit(String yearIndicatorForPrepaymentLimit) {
        this.yearIndicatorForPrepaymentLimit = yearIndicatorForPrepaymentLimit;
    }

    public String getPrepaymentNotAcceptedBeforeMonths() {
        return prepaymentNotAcceptedBeforeMonths;
    }

    public void setPrepaymentNotAcceptedBeforeMonths(String prepaymentNotAcceptedBeforeMonths) {
        this.prepaymentNotAcceptedBeforeMonths = prepaymentNotAcceptedBeforeMonths;
    }

    public String getPrepaymentNotAcceptedBeforeDays() {
        return prepaymentNotAcceptedBeforeDays;
    }

    public void setPrepaymentNotAcceptedBeforeDays(String prepaymentNotAcceptedBeforeDays) {
        this.prepaymentNotAcceptedBeforeDays = prepaymentNotAcceptedBeforeDays;
    }

    public String getPayOffIntToBeCollectedTill() {
        return payOffIntToBeCollectedTill;
    }

    public void setPayOffIntToBeCollectedTill(String payOffIntToBeCollectedTill) {
        this.payOffIntToBeCollectedTill = payOffIntToBeCollectedTill;
    }

    public String getNoticeReqdForPrepayment() {
        return noticeReqdForPrepayment;
    }

    public void setNoticeReqdForPrepayment(String noticeReqdForPrepayment) {
        this.noticeReqdForPrepayment = noticeReqdForPrepayment;
    }

    public String getMinNoticePeriodMonths() {
        return minNoticePeriodMonths;
    }

    public void setMinNoticePeriodMonths(String minNoticePeriodMonths) {
        this.minNoticePeriodMonths = minNoticePeriodMonths;
    }

    public String getMinNoticePeriodDay() {
        return minNoticePeriodDay;
    }

    public void setMinNoticePeriodDay(String minNoticePeriodDay) {
        this.minNoticePeriodDay = minNoticePeriodDay;
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

    public String getEiFlowId() {
        return eiFlowId;
    }

    public void setEiFlowId(String eiFlowId) {
        this.eiFlowId = eiFlowId;
    }

    public String getPrincipalFlowId() {
        return principalFlowId;
    }

    public void setPrincipalFlowId(String principalFlowId) {
        this.principalFlowId = principalFlowId;
    }

    public String getDisbursementFlowId() {
        return disbursementFlowId;
    }

    public void setDisbursementFlowId(String disbursementFlowId) {
        this.disbursementFlowId = disbursementFlowId;
    }

    public String getCollectionFlowId() {
        return collectionFlowId;
    }

    public void setCollectionFlowId(String collectionFlowId) {
        this.collectionFlowId = collectionFlowId;
    }

    public String getIntDemandFlowId() {
        return intDemandFlowId;
    }

    public void setIntDemandFlowId(String intDemandFlowId) {
        this.intDemandFlowId = intDemandFlowId;
    }

    public String getPenalIntDemandFlowId() {
        return penalIntDemandFlowId;
    }

    public void setPenalIntDemandFlowId(String penalIntDemandFlowId) {
        this.penalIntDemandFlowId = penalIntDemandFlowId;
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

    public String getChargeDemandFlowId() {
        return chargeDemandFlowId;
    }

    public void setChargeDemandFlowId(String chargeDemandFlowId) {
        this.chargeDemandFlowId = chargeDemandFlowId;
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
        if (!(object instanceof CbsSchemeLoanPrepaymentDetails)) {
            return false;
        }
        CbsSchemeLoanPrepaymentDetails other = (CbsSchemeLoanPrepaymentDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanPrepaymentDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
