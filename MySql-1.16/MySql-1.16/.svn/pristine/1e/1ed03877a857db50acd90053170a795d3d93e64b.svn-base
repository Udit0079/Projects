/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
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
@Table(name = "cbs_scheme_loan_exception_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findAll", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByNonConformingLoanPeriod", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.nonConformingLoanPeriod = :nonConformingLoanPeriod"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByNonConformingLoanAmount", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.nonConformingLoanAmount = :nonConformingLoanAmount"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByDisbGreaterThanLoanAmount", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.disbGreaterThanLoanAmount = :disbGreaterThanLoanAmount"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByDisbNotConformingToSchedule", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.disbNotConformingToSchedule = :disbNotConformingToSchedule"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByDisbDateSanctExpiryDate", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.disbDateSanctExpiryDate = :disbDateSanctExpiryDate"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByIntCalculationNotUpToDate", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.intCalculationNotUpToDate = :intCalculationNotUpToDate"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByTransferAmountIsGreaterThanCrBalance", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.transferAmountIsGreaterThanCrBalance = :transferAmountIsGreaterThanCrBalance"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByCustIdDiffForLoanAndOpAccount", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.custIdDiffForLoanAndOpAccount = :custIdDiffForLoanAndOpAccount"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByInterestCollectedExceedsLimit", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.interestCollectedExceedsLimit = :interestCollectedExceedsLimit"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByWaiverOfChargesOrInterest", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.waiverOfChargesOrInterest = :waiverOfChargesOrInterest"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByOverrideSystemCalcEiAmount", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.overrideSystemCalcEiAmount = :overrideSystemCalcEiAmount"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByPendingSchedulePayments", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.pendingSchedulePayments = :pendingSchedulePayments"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByRepaymentPerdNotEqualToLoanPerd", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.repaymentPerdNotEqualToLoanPerd = :repaymentPerdNotEqualToLoanPerd"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByRephasementInterestCalcNotUpToDate", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.rephasementInterestCalcNotUpToDate = :rephasementInterestCalcNotUpToDate"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByMaxHolidayPeriodExceeded", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.maxHolidayPeriodExceeded = :maxHolidayPeriodExceeded"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByPrepaymentNotAsPerNotice", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.prepaymentNotAsPerNotice = :prepaymentNotAsPerNotice"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByValueDatedNotice", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.valueDatedNotice = :valueDatedNotice"),
    @NamedQuery(name = "CbsSchemeLoanExceptionDetails.findByBackValueDatedAccountOpening", query = "SELECT c FROM CbsSchemeLoanExceptionDetails c WHERE c.backValueDatedAccountOpening = :backValueDatedAccountOpening")})
public class CbsSchemeLoanExceptionDetails extends BaseEntity implements Serializable {
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
    @Column(name = "NON_CONFORMING_LOAN_PERIOD")
    private String nonConformingLoanPeriod;
    @Size(max = 3)
    @Column(name = "NON_CONFORMING_LOAN_AMOUNT")
    private String nonConformingLoanAmount;
    @Size(max = 3)
    @Column(name = "DISB_GREATER_THAN_LOAN_AMOUNT")
    private String disbGreaterThanLoanAmount;
    @Size(max = 3)
    @Column(name = "DISB_NOT_CONFORMING_TO_SCHEDULE")
    private String disbNotConformingToSchedule;
    @Size(max = 3)
    @Column(name = "DISB_DATE_SANCT_EXPIRY_DATE")
    private String disbDateSanctExpiryDate;
    @Size(max = 3)
    @Column(name = "INT_CALCULATION_NOT_UP_TO_DATE")
    private String intCalculationNotUpToDate;
    @Size(max = 3)
    @Column(name = "TRANSFER_AMOUNT_IS_GREATER_THAN_CR_BALANCE")
    private String transferAmountIsGreaterThanCrBalance;
    @Size(max = 3)
    @Column(name = "CUST_ID_DIFF_FOR_LOAN_AND_OP_ACCOUNT")
    private String custIdDiffForLoanAndOpAccount;
    @Size(max = 3)
    @Column(name = "INTEREST_COLLECTED_EXCEEDS_LIMIT")
    private String interestCollectedExceedsLimit;
    @Size(max = 3)
    @Column(name = "WAIVER_OF_CHARGES_OR_INTEREST")
    private String waiverOfChargesOrInterest;
    @Size(max = 3)
    @Column(name = "OVERRIDE_SYSTEM_CALC_EI_AMOUNT")
    private String overrideSystemCalcEiAmount;
    @Size(max = 3)
    @Column(name = "PENDING_SCHEDULE_PAYMENTS")
    private String pendingSchedulePayments;
    @Size(max = 3)
    @Column(name = "REPAYMENT_PERD_NOT_EQUAL_TO_LOAN_PERD")
    private String repaymentPerdNotEqualToLoanPerd;
    @Size(max = 3)
    @Column(name = "REPHASEMENT_INTEREST_CALC_NOT_UP_TO_DATE")
    private String rephasementInterestCalcNotUpToDate;
    @Size(max = 3)
    @Column(name = "MAX_HOLIDAY_PERIOD_EXCEEDED")
    private String maxHolidayPeriodExceeded;
    @Size(max = 3)
    @Column(name = "PREPAYMENT_NOT_AS_PER_NOTICE")
    private String prepaymentNotAsPerNotice;
    @Size(max = 3)
    @Column(name = "VALUE_DATED_NOTICE")
    private String valueDatedNotice;
    @Size(max = 3)
    @Column(name = "BACK_VALUE_DATED_ACCOUNT_OPENING")
    private String backValueDatedAccountOpening;

    public CbsSchemeLoanExceptionDetails() {
    }

    public CbsSchemeLoanExceptionDetails(String schemeCode) {
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

    public String getNonConformingLoanPeriod() {
        return nonConformingLoanPeriod;
    }

    public void setNonConformingLoanPeriod(String nonConformingLoanPeriod) {
        this.nonConformingLoanPeriod = nonConformingLoanPeriod;
    }

    public String getNonConformingLoanAmount() {
        return nonConformingLoanAmount;
    }

    public void setNonConformingLoanAmount(String nonConformingLoanAmount) {
        this.nonConformingLoanAmount = nonConformingLoanAmount;
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

    public String getDisbDateSanctExpiryDate() {
        return disbDateSanctExpiryDate;
    }

    public void setDisbDateSanctExpiryDate(String disbDateSanctExpiryDate) {
        this.disbDateSanctExpiryDate = disbDateSanctExpiryDate;
    }

    public String getIntCalculationNotUpToDate() {
        return intCalculationNotUpToDate;
    }

    public void setIntCalculationNotUpToDate(String intCalculationNotUpToDate) {
        this.intCalculationNotUpToDate = intCalculationNotUpToDate;
    }

    public String getTransferAmountIsGreaterThanCrBalance() {
        return transferAmountIsGreaterThanCrBalance;
    }

    public void setTransferAmountIsGreaterThanCrBalance(String transferAmountIsGreaterThanCrBalance) {
        this.transferAmountIsGreaterThanCrBalance = transferAmountIsGreaterThanCrBalance;
    }

    public String getCustIdDiffForLoanAndOpAccount() {
        return custIdDiffForLoanAndOpAccount;
    }

    public void setCustIdDiffForLoanAndOpAccount(String custIdDiffForLoanAndOpAccount) {
        this.custIdDiffForLoanAndOpAccount = custIdDiffForLoanAndOpAccount;
    }

    public String getInterestCollectedExceedsLimit() {
        return interestCollectedExceedsLimit;
    }

    public void setInterestCollectedExceedsLimit(String interestCollectedExceedsLimit) {
        this.interestCollectedExceedsLimit = interestCollectedExceedsLimit;
    }

    public String getWaiverOfChargesOrInterest() {
        return waiverOfChargesOrInterest;
    }

    public void setWaiverOfChargesOrInterest(String waiverOfChargesOrInterest) {
        this.waiverOfChargesOrInterest = waiverOfChargesOrInterest;
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

    public String getMaxHolidayPeriodExceeded() {
        return maxHolidayPeriodExceeded;
    }

    public void setMaxHolidayPeriodExceeded(String maxHolidayPeriodExceeded) {
        this.maxHolidayPeriodExceeded = maxHolidayPeriodExceeded;
    }

    public String getPrepaymentNotAsPerNotice() {
        return prepaymentNotAsPerNotice;
    }

    public void setPrepaymentNotAsPerNotice(String prepaymentNotAsPerNotice) {
        this.prepaymentNotAsPerNotice = prepaymentNotAsPerNotice;
    }

    public String getValueDatedNotice() {
        return valueDatedNotice;
    }

    public void setValueDatedNotice(String valueDatedNotice) {
        this.valueDatedNotice = valueDatedNotice;
    }

    public String getBackValueDatedAccountOpening() {
        return backValueDatedAccountOpening;
    }

    public void setBackValueDatedAccountOpening(String backValueDatedAccountOpening) {
        this.backValueDatedAccountOpening = backValueDatedAccountOpening;
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
        if (!(object instanceof CbsSchemeLoanExceptionDetails)) {
            return false;
        }
        CbsSchemeLoanExceptionDetails other = (CbsSchemeLoanExceptionDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeLoanExceptionDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
