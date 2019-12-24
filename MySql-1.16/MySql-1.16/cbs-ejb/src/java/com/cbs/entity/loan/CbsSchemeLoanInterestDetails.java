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
@Table(name = "cbs_scheme_loan_interest_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findAll", query = "SELECT c FROM CbsSchemeLoanInterestDetails c"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntOnPrincipal", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intOnPrincipal = :intOnPrincipal"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByPenalIntOnPrincipalDemandOverdue", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.penalIntOnPrincipalDemandOverdue = :penalIntOnPrincipalDemandOverdue"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByPrincipalDemandOverdueAtEndOfMonths", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.principalDemandOverdueAtEndOfMonths = :principalDemandOverdueAtEndOfMonths"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByPrincipalOverduePeriodMonths", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.principalOverduePeriodMonths = :principalOverduePeriodMonths"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByPrincipalOverduePeriodDays", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.principalOverduePeriodDays = :principalOverduePeriodDays"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntOnIntDemand", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intOnIntDemand = :intOnIntDemand"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByPenalIntOnIntDemandOverdue", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.penalIntOnIntDemandOverdue = :penalIntOnIntDemandOverdue"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntDemandOverdueAtEndOfMonth", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intDemandOverdueAtEndOfMonth = :intDemandOverdueAtEndOfMonth"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntOverduePeriodMonths", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intOverduePeriodMonths = :intOverduePeriodMonths"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntOverduePeriodDays", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intOverduePeriodDays = :intOverduePeriodDays"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByOverdueIntOnPrincipal", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.overdueIntOnPrincipal = :overdueIntOnPrincipal"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByApplyPreferentialForOverdueInt", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.applyPreferentialForOverdueInt = :applyPreferentialForOverdueInt"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntRateBasedOnLoanAmount", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intRateBasedOnLoanAmount = :intRateBasedOnLoanAmount"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByApplyLateFeeForDelayedPayment", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.applyLateFeeForDelayedPayment = :applyLateFeeForDelayedPayment"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByGracePeriodForLateFeeMonths", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.gracePeriodForLateFeeMonths = :gracePeriodForLateFeeMonths"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByGracePeriodForLateFeeDays", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.gracePeriodForLateFeeDays = :gracePeriodForLateFeeDays"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByToleranceLimitForDpdCycleAmount", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.toleranceLimitForDpdCycleAmount = :toleranceLimitForDpdCycleAmount"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByToleranceLimitForDpdCycleType", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.toleranceLimitForDpdCycleType = :toleranceLimitForDpdCycleType"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByConsiderToleranceForLateFee", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.considerToleranceForLateFee = :considerToleranceForLateFee"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByCreateIntDemandFromRepSchedule", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.createIntDemandFromRepSchedule = :createIntDemandFromRepSchedule"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByRephasementCarryOverdueDemands", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.rephasementCarryOverdueDemands = :rephasementCarryOverdueDemands"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByPriorityLoan", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.priorityLoan = :priorityLoan"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByAgriLoan", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.agriLoan = :agriLoan"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByIntLimit", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.intLimit = :intLimit"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByCoveredByDicge", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.coveredByDicge = :coveredByDicge"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByDicgcFeeFlowId", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.dicgcFeeFlowId = :dicgcFeeFlowId"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByDicgcFeeAccountPlaceholer", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.dicgcFeeAccountPlaceholer = :dicgcFeeAccountPlaceholer"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByHirerDetails", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.hirerDetails = :hirerDetails"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByAodOrAosType", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.aodOrAosType = :aodOrAosType"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findBySubsidyAvailable", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.subsidyAvailable = :subsidyAvailable"),
    @NamedQuery(name = "CbsSchemeLoanInterestDetails.findByRefinanceScheme", query = "SELECT c FROM CbsSchemeLoanInterestDetails c WHERE c.refinanceScheme = :refinanceScheme")})
public class CbsSchemeLoanInterestDetails extends BaseEntity implements Serializable {
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
    @Column(name = "INT_ON_PRINCIPAL")
    private String intOnPrincipal;
    @Size(max = 1)
    @Column(name = "PENAL_INT_ON_PRINCIPAL_DEMAND_OVERDUE")
    private String penalIntOnPrincipalDemandOverdue;
    @Size(max = 1)
    @Column(name = "PRINCIPAL_DEMAND_OVERDUE_AT_END_OF_MONTHS")
    private String principalDemandOverdueAtEndOfMonths;
    @Size(max = 3)
    @Column(name = "PRINCIPAL_OVERDUE_PERIOD_MONTHS")
    private String principalOverduePeriodMonths;
    @Size(max = 3)
    @Column(name = "PRINCIPAL_OVERDUE_PERIOD_DAYS")
    private String principalOverduePeriodDays;
    @Size(max = 1)
    @Column(name = "INT_ON_INT_DEMAND")
    private String intOnIntDemand;
    @Size(max = 1)
    @Column(name = "PENAL_INT_ON_INT_DEMAND_OVERDUE")
    private String penalIntOnIntDemandOverdue;
    @Size(max = 1)
    @Column(name = "INT_DEMAND_OVERDUE_AT_END_OF_MONTH")
    private String intDemandOverdueAtEndOfMonth;
    @Size(max = 3)
    @Column(name = "INT_OVERDUE_PERIOD_MONTHS")
    private String intOverduePeriodMonths;
    @Size(max = 3)
    @Column(name = "INT_OVERDUE_PERIOD_DAYS")
    private String intOverduePeriodDays;
    @Size(max = 1)
    @Column(name = "OVERDUE_INT_ON_PRINCIPAL")
    private String overdueIntOnPrincipal;
    @Size(max = 1)
    @Column(name = "APPLY_PREFERENTIAL_FOR_OVERDUE_INT")
    private String applyPreferentialForOverdueInt;
    @Size(max = 1)
    @Column(name = "INT_RATE_BASED_ON_LOAN_AMOUNT")
    private String intRateBasedOnLoanAmount;
    @Size(max = 1)
    @Column(name = "APPLY_LATE_FEE_FOR_DELAYED_PAYMENT")
    private String applyLateFeeForDelayedPayment;
    @Size(max = 3)
    @Column(name = "GRACE_PERIOD_FOR_LATE_FEE_MONTHS")
    private String gracePeriodForLateFeeMonths;
    @Size(max = 3)
    @Column(name = "GRACE_PERIOD_FOR_LATE_FEE_DAYS")
    private String gracePeriodForLateFeeDays;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOLERANCE_LIMIT_FOR_DPD_CYCLE_AMOUNT")
    private BigDecimal toleranceLimitForDpdCycleAmount;
    @Size(max = 1)
    @Column(name = "TOLERANCE_LIMIT_FOR_DPD_CYCLE_TYPE")
    private String toleranceLimitForDpdCycleType;
    @Size(max = 1)
    @Column(name = "CONSIDER_TOLERANCE_FOR_LATE_FEE")
    private String considerToleranceForLateFee;
    @Size(max = 1)
    @Column(name = "CREATE_INT_DEMAND_FROM_REP_SCHEDULE")
    private String createIntDemandFromRepSchedule;
    @Size(max = 1)
    @Column(name = "REPHASEMENT_CARRY_OVERDUE_DEMANDS")
    private String rephasementCarryOverdueDemands;
    @Size(max = 1)
    @Column(name = "PRIORITY_LOAN")
    private String priorityLoan;
    @Size(max = 1)
    @Column(name = "AGRI_LOAN")
    private String agriLoan;
    @Column(name = "INT_LIMIT")
    private BigDecimal intLimit;
    @Size(max = 1)
    @Column(name = "COVERED_BY_DICGE")
    private String coveredByDicge;
    @Size(max = 1)
    @Column(name = "DICGC_FEE_FLOW_ID")
    private String dicgcFeeFlowId;
    @Size(max = 12)
    @Column(name = "DICGC_FEE_ACCOUNT_PLACEHOLER")
    private String dicgcFeeAccountPlaceholer;
    @Size(max = 1)
    @Column(name = "HIRER_DETAILS")
    private String hirerDetails;
    @Size(max = 1)
    @Column(name = "AOD_OR_AOS_TYPE")
    private String aodOrAosType;
    @Size(max = 1)
    @Column(name = "SUBSIDY_AVAILABLE")
    private String subsidyAvailable;
    @Size(max = 1)
    @Column(name = "REFINANCE_SCHEME")
    private String refinanceScheme;

    public CbsSchemeLoanInterestDetails() {
    }

    public CbsSchemeLoanInterestDetails(String schemeCode) {
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

    public String getIntOnPrincipal() {
        return intOnPrincipal;
    }

    public void setIntOnPrincipal(String intOnPrincipal) {
        this.intOnPrincipal = intOnPrincipal;
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

    public String getPrincipalOverduePeriodMonths() {
        return principalOverduePeriodMonths;
    }

    public void setPrincipalOverduePeriodMonths(String principalOverduePeriodMonths) {
        this.principalOverduePeriodMonths = principalOverduePeriodMonths;
    }

    public String getPrincipalOverduePeriodDays() {
        return principalOverduePeriodDays;
    }

    public void setPrincipalOverduePeriodDays(String principalOverduePeriodDays) {
        this.principalOverduePeriodDays = principalOverduePeriodDays;
    }

    public String getIntOnIntDemand() {
        return intOnIntDemand;
    }

    public void setIntOnIntDemand(String intOnIntDemand) {
        this.intOnIntDemand = intOnIntDemand;
    }

    public String getPenalIntOnIntDemandOverdue() {
        return penalIntOnIntDemandOverdue;
    }

    public void setPenalIntOnIntDemandOverdue(String penalIntOnIntDemandOverdue) {
        this.penalIntOnIntDemandOverdue = penalIntOnIntDemandOverdue;
    }

    public String getIntDemandOverdueAtEndOfMonth() {
        return intDemandOverdueAtEndOfMonth;
    }

    public void setIntDemandOverdueAtEndOfMonth(String intDemandOverdueAtEndOfMonth) {
        this.intDemandOverdueAtEndOfMonth = intDemandOverdueAtEndOfMonth;
    }

    public String getIntOverduePeriodMonths() {
        return intOverduePeriodMonths;
    }

    public void setIntOverduePeriodMonths(String intOverduePeriodMonths) {
        this.intOverduePeriodMonths = intOverduePeriodMonths;
    }

    public String getIntOverduePeriodDays() {
        return intOverduePeriodDays;
    }

    public void setIntOverduePeriodDays(String intOverduePeriodDays) {
        this.intOverduePeriodDays = intOverduePeriodDays;
    }

    public String getOverdueIntOnPrincipal() {
        return overdueIntOnPrincipal;
    }

    public void setOverdueIntOnPrincipal(String overdueIntOnPrincipal) {
        this.overdueIntOnPrincipal = overdueIntOnPrincipal;
    }

    public String getApplyPreferentialForOverdueInt() {
        return applyPreferentialForOverdueInt;
    }

    public void setApplyPreferentialForOverdueInt(String applyPreferentialForOverdueInt) {
        this.applyPreferentialForOverdueInt = applyPreferentialForOverdueInt;
    }

    public String getIntRateBasedOnLoanAmount() {
        return intRateBasedOnLoanAmount;
    }

    public void setIntRateBasedOnLoanAmount(String intRateBasedOnLoanAmount) {
        this.intRateBasedOnLoanAmount = intRateBasedOnLoanAmount;
    }

    public String getApplyLateFeeForDelayedPayment() {
        return applyLateFeeForDelayedPayment;
    }

    public void setApplyLateFeeForDelayedPayment(String applyLateFeeForDelayedPayment) {
        this.applyLateFeeForDelayedPayment = applyLateFeeForDelayedPayment;
    }

    public String getGracePeriodForLateFeeMonths() {
        return gracePeriodForLateFeeMonths;
    }

    public void setGracePeriodForLateFeeMonths(String gracePeriodForLateFeeMonths) {
        this.gracePeriodForLateFeeMonths = gracePeriodForLateFeeMonths;
    }

    public String getGracePeriodForLateFeeDays() {
        return gracePeriodForLateFeeDays;
    }

    public void setGracePeriodForLateFeeDays(String gracePeriodForLateFeeDays) {
        this.gracePeriodForLateFeeDays = gracePeriodForLateFeeDays;
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

    public String getConsiderToleranceForLateFee() {
        return considerToleranceForLateFee;
    }

    public void setConsiderToleranceForLateFee(String considerToleranceForLateFee) {
        this.considerToleranceForLateFee = considerToleranceForLateFee;
    }

    public String getCreateIntDemandFromRepSchedule() {
        return createIntDemandFromRepSchedule;
    }

    public void setCreateIntDemandFromRepSchedule(String createIntDemandFromRepSchedule) {
        this.createIntDemandFromRepSchedule = createIntDemandFromRepSchedule;
    }

    public String getRephasementCarryOverdueDemands() {
        return rephasementCarryOverdueDemands;
    }

    public void setRephasementCarryOverdueDemands(String rephasementCarryOverdueDemands) {
        this.rephasementCarryOverdueDemands = rephasementCarryOverdueDemands;
    }

    public String getPriorityLoan() {
        return priorityLoan;
    }

    public void setPriorityLoan(String priorityLoan) {
        this.priorityLoan = priorityLoan;
    }

    public String getAgriLoan() {
        return agriLoan;
    }

    public void setAgriLoan(String agriLoan) {
        this.agriLoan = agriLoan;
    }

    public BigDecimal getIntLimit() {
        return intLimit;
    }

    public void setIntLimit(BigDecimal intLimit) {
        this.intLimit = intLimit;
    }

    public String getCoveredByDicge() {
        return coveredByDicge;
    }

    public void setCoveredByDicge(String coveredByDicge) {
        this.coveredByDicge = coveredByDicge;
    }

    public String getDicgcFeeFlowId() {
        return dicgcFeeFlowId;
    }

    public void setDicgcFeeFlowId(String dicgcFeeFlowId) {
        this.dicgcFeeFlowId = dicgcFeeFlowId;
    }

    public String getDicgcFeeAccountPlaceholer() {
        return dicgcFeeAccountPlaceholer;
    }

    public void setDicgcFeeAccountPlaceholer(String dicgcFeeAccountPlaceholer) {
        this.dicgcFeeAccountPlaceholer = dicgcFeeAccountPlaceholer;
    }

    public String getHirerDetails() {
        return hirerDetails;
    }

    public void setHirerDetails(String hirerDetails) {
        this.hirerDetails = hirerDetails;
    }

    public String getAodOrAosType() {
        return aodOrAosType;
    }

    public void setAodOrAosType(String aodOrAosType) {
        this.aodOrAosType = aodOrAosType;
    }

    public String getSubsidyAvailable() {
        return subsidyAvailable;
    }

    public void setSubsidyAvailable(String subsidyAvailable) {
        this.subsidyAvailable = subsidyAvailable;
    }

    public String getRefinanceScheme() {
        return refinanceScheme;
    }

    public void setRefinanceScheme(String refinanceScheme) {
        this.refinanceScheme = refinanceScheme;
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
        if (!(object instanceof CbsSchemeLoanInterestDetails)) {
            return false;
        }
        CbsSchemeLoanInterestDetails other = (CbsSchemeLoanInterestDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanInterestDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
