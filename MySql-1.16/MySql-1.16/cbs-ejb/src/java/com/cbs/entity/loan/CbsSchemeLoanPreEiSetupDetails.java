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
@Table(name = "cbs_scheme_loan_pre_ei_setup_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findAll", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByNormalHolidayPeriodMonths", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.normalHolidayPeriodMonths = :normalHolidayPeriodMonths"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByMaxHolidayPeriodAllowedMonths", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.maxHolidayPeriodAllowedMonths = :maxHolidayPeriodAllowedMonths"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByIntDuringHolidayPeriod", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.intDuringHolidayPeriod = :intDuringHolidayPeriod"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByIntFreqDuringHolidayPeriodType", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.intFreqDuringHolidayPeriodType = :intFreqDuringHolidayPeriodType"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByIntFreqDuringHolidayPeriodWeekNo", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.intFreqDuringHolidayPeriodWeekNo = :intFreqDuringHolidayPeriodWeekNo"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByIntFreqDuringHolidayPeriodWeekDay", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.intFreqDuringHolidayPeriodWeekDay = :intFreqDuringHolidayPeriodWeekDay"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByIntFreqDuringHolidayPeriodStartDate", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.intFreqDuringHolidayPeriodStartDate = :intFreqDuringHolidayPeriodStartDate"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByIntFreqDuringHolidayPeriodNp", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.intFreqDuringHolidayPeriodNp = :intFreqDuringHolidayPeriodNp"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByMultipleDisbursementsAllowed", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.multipleDisbursementsAllowed = :multipleDisbursementsAllowed"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByAutoProcessAfterHolidayPeriod", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.autoProcessAfterHolidayPeriod = :autoProcessAfterHolidayPeriod"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByOddDaysInt", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.oddDaysInt = :oddDaysInt"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByResidualBalanceWaiverLimit", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.residualBalanceWaiverLimit = :residualBalanceWaiverLimit"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByResidualBalanceAbsorbLimit", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.residualBalanceAbsorbLimit = :residualBalanceAbsorbLimit"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByPlaceholderForResidualBalanceWaiver", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.placeholderForResidualBalanceWaiver = :placeholderForResidualBalanceWaiver"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByPlaceholderForResidualBalanceAbsorb", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.placeholderForResidualBalanceAbsorb = :placeholderForResidualBalanceAbsorb"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByMaxCycleForPromptPaymentDiscount", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.maxCycleForPromptPaymentDiscount = :maxCycleForPromptPaymentDiscount"),
    @NamedQuery(name = "CbsSchemeLoanPreEiSetupDetails.findByEventIdForPromptPaymentDiscount", query = "SELECT c FROM CbsSchemeLoanPreEiSetupDetails c WHERE c.eventIdForPromptPaymentDiscount = :eventIdForPromptPaymentDiscount")})
public class CbsSchemeLoanPreEiSetupDetails extends BaseEntity implements Serializable {
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
    @Column(name = "NORMAL_HOLIDAY_PERIOD_MONTHS")
    private String normalHolidayPeriodMonths;
    @Size(max = 3)
    @Column(name = "MAX_HOLIDAY_PERIOD_ALLOWED_MONTHS")
    private String maxHolidayPeriodAllowedMonths;
    @Size(max = 1)
    @Column(name = "INT_DURING_HOLIDAY_PERIOD")
    private String intDuringHolidayPeriod;
    @Size(max = 1)
    @Column(name = "INT_FREQ_DURING_HOLIDAY_PERIOD_TYPE")
    private String intFreqDuringHolidayPeriodType;
    @Size(max = 1)
    @Column(name = "INT_FREQ_DURING_HOLIDAY_PERIOD_WEEK_NO")
    private String intFreqDuringHolidayPeriodWeekNo;
    @Size(max = 1)
    @Column(name = "INT_FREQ_DURING_HOLIDAY_PERIOD_WEEK_DAY")
    private String intFreqDuringHolidayPeriodWeekDay;
    @Size(max = 2)
    @Column(name = "INT_FREQ_DURING_HOLIDAY_PERIOD_START_DATE")
    private String intFreqDuringHolidayPeriodStartDate;
    @Size(max = 1)
    @Column(name = "INT_FREQ_DURING_HOLIDAY_PERIOD_NP")
    private String intFreqDuringHolidayPeriodNp;
    @Size(max = 1)
    @Column(name = "MULTIPLE_DISBURSEMENTS_ALLOWED")
    private String multipleDisbursementsAllowed;
    @Size(max = 1)
    @Column(name = "AUTO_PROCESS_AFTER_HOLIDAY_PERIOD")
    private String autoProcessAfterHolidayPeriod;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ODD_DAYS_INT")
    private BigDecimal oddDaysInt;
    @Column(name = "RESIDUAL_BALANCE_WAIVER_LIMIT")
    private BigDecimal residualBalanceWaiverLimit;
    @Column(name = "RESIDUAL_BALANCE_ABSORB_LIMIT")
    private BigDecimal residualBalanceAbsorbLimit;
    @Size(max = 12)
    @Column(name = "PLACEHOLDER_FOR_RESIDUAL_BALANCE_WAIVER")
    private String placeholderForResidualBalanceWaiver;
    @Size(max = 12)
    @Column(name = "PLACEHOLDER_FOR_RESIDUAL_BALANCE_ABSORB")
    private String placeholderForResidualBalanceAbsorb;
    @Column(name = "MAX_CYCLE_FOR_PROMPT_PAYMENT_DISCOUNT")
    private BigDecimal maxCycleForPromptPaymentDiscount;
    @Size(max = 25)
    @Column(name = "EVENT_ID_FOR_PROMPT_PAYMENT_DISCOUNT")
    private String eventIdForPromptPaymentDiscount;

    public CbsSchemeLoanPreEiSetupDetails() {
    }

    public CbsSchemeLoanPreEiSetupDetails(String schemeCode) {
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

    public String getNormalHolidayPeriodMonths() {
        return normalHolidayPeriodMonths;
    }

    public void setNormalHolidayPeriodMonths(String normalHolidayPeriodMonths) {
        this.normalHolidayPeriodMonths = normalHolidayPeriodMonths;
    }

    public String getMaxHolidayPeriodAllowedMonths() {
        return maxHolidayPeriodAllowedMonths;
    }

    public void setMaxHolidayPeriodAllowedMonths(String maxHolidayPeriodAllowedMonths) {
        this.maxHolidayPeriodAllowedMonths = maxHolidayPeriodAllowedMonths;
    }

    public String getIntDuringHolidayPeriod() {
        return intDuringHolidayPeriod;
    }

    public void setIntDuringHolidayPeriod(String intDuringHolidayPeriod) {
        this.intDuringHolidayPeriod = intDuringHolidayPeriod;
    }

    public String getIntFreqDuringHolidayPeriodType() {
        return intFreqDuringHolidayPeriodType;
    }

    public void setIntFreqDuringHolidayPeriodType(String intFreqDuringHolidayPeriodType) {
        this.intFreqDuringHolidayPeriodType = intFreqDuringHolidayPeriodType;
    }

    public String getIntFreqDuringHolidayPeriodWeekNo() {
        return intFreqDuringHolidayPeriodWeekNo;
    }

    public void setIntFreqDuringHolidayPeriodWeekNo(String intFreqDuringHolidayPeriodWeekNo) {
        this.intFreqDuringHolidayPeriodWeekNo = intFreqDuringHolidayPeriodWeekNo;
    }

    public String getIntFreqDuringHolidayPeriodWeekDay() {
        return intFreqDuringHolidayPeriodWeekDay;
    }

    public void setIntFreqDuringHolidayPeriodWeekDay(String intFreqDuringHolidayPeriodWeekDay) {
        this.intFreqDuringHolidayPeriodWeekDay = intFreqDuringHolidayPeriodWeekDay;
    }

    public String getIntFreqDuringHolidayPeriodStartDate() {
        return intFreqDuringHolidayPeriodStartDate;
    }

    public void setIntFreqDuringHolidayPeriodStartDate(String intFreqDuringHolidayPeriodStartDate) {
        this.intFreqDuringHolidayPeriodStartDate = intFreqDuringHolidayPeriodStartDate;
    }

    public String getIntFreqDuringHolidayPeriodNp() {
        return intFreqDuringHolidayPeriodNp;
    }

    public void setIntFreqDuringHolidayPeriodNp(String intFreqDuringHolidayPeriodNp) {
        this.intFreqDuringHolidayPeriodNp = intFreqDuringHolidayPeriodNp;
    }

    public String getMultipleDisbursementsAllowed() {
        return multipleDisbursementsAllowed;
    }

    public void setMultipleDisbursementsAllowed(String multipleDisbursementsAllowed) {
        this.multipleDisbursementsAllowed = multipleDisbursementsAllowed;
    }

    public String getAutoProcessAfterHolidayPeriod() {
        return autoProcessAfterHolidayPeriod;
    }

    public void setAutoProcessAfterHolidayPeriod(String autoProcessAfterHolidayPeriod) {
        this.autoProcessAfterHolidayPeriod = autoProcessAfterHolidayPeriod;
    }

    public BigDecimal getOddDaysInt() {
        return oddDaysInt;
    }

    public void setOddDaysInt(BigDecimal oddDaysInt) {
        this.oddDaysInt = oddDaysInt;
    }

    public BigDecimal getResidualBalanceWaiverLimit() {
        return residualBalanceWaiverLimit;
    }

    public void setResidualBalanceWaiverLimit(BigDecimal residualBalanceWaiverLimit) {
        this.residualBalanceWaiverLimit = residualBalanceWaiverLimit;
    }

    public BigDecimal getResidualBalanceAbsorbLimit() {
        return residualBalanceAbsorbLimit;
    }

    public void setResidualBalanceAbsorbLimit(BigDecimal residualBalanceAbsorbLimit) {
        this.residualBalanceAbsorbLimit = residualBalanceAbsorbLimit;
    }

    public String getPlaceholderForResidualBalanceWaiver() {
        return placeholderForResidualBalanceWaiver;
    }

    public void setPlaceholderForResidualBalanceWaiver(String placeholderForResidualBalanceWaiver) {
        this.placeholderForResidualBalanceWaiver = placeholderForResidualBalanceWaiver;
    }

    public String getPlaceholderForResidualBalanceAbsorb() {
        return placeholderForResidualBalanceAbsorb;
    }

    public void setPlaceholderForResidualBalanceAbsorb(String placeholderForResidualBalanceAbsorb) {
        this.placeholderForResidualBalanceAbsorb = placeholderForResidualBalanceAbsorb;
    }

    public BigDecimal getMaxCycleForPromptPaymentDiscount() {
        return maxCycleForPromptPaymentDiscount;
    }

    public void setMaxCycleForPromptPaymentDiscount(BigDecimal maxCycleForPromptPaymentDiscount) {
        this.maxCycleForPromptPaymentDiscount = maxCycleForPromptPaymentDiscount;
    }

    public String getEventIdForPromptPaymentDiscount() {
        return eventIdForPromptPaymentDiscount;
    }

    public void setEventIdForPromptPaymentDiscount(String eventIdForPromptPaymentDiscount) {
        this.eventIdForPromptPaymentDiscount = eventIdForPromptPaymentDiscount;
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
        if (!(object instanceof CbsSchemeLoanPreEiSetupDetails)) {
            return false;
        }
        CbsSchemeLoanPreEiSetupDetails other = (CbsSchemeLoanPreEiSetupDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanPreEiSetupDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
