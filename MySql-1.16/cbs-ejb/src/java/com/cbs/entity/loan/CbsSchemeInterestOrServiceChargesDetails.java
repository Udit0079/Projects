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
@Table(name = "cbs_scheme_interest_or_service_charges_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findAll", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestPaidFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestPaidFlag = :interestPaidFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestPayableAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestPayableAccountPlaceholder = :interestPayableAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByIntCollectionFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.intCollectionFlag = :intCollectionFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByIntRecbleAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.intRecbleAccountPlaceholder = :intRecbleAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByServiceChargesFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.serviceChargesFlag = :serviceChargesFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByServiceCollAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.serviceCollAccountPlaceholder = :serviceCollAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByParkingAccountTdsPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.parkingAccountTdsPlaceholder = :parkingAccountTdsPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByIncomeExpenseAccountInHomeCurrency", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.incomeExpenseAccountInHomeCurrency = :incomeExpenseAccountInHomeCurrency"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByNormalProfitAndLossAccountPlaceholderCr", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.normalProfitAndLossAccountPlaceholderCr = :normalProfitAndLossAccountPlaceholderCr"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByNormalProfitAndLossAccountPlaceholderDr", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.normalProfitAndLossAccountPlaceholderDr = :normalProfitAndLossAccountPlaceholderDr"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByPenalProfitAndLossAccountPlaceholderDr", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.penalProfitAndLossAccountPlaceholderDr = :penalProfitAndLossAccountPlaceholderDr"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByParkingAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.parkingAccountPlaceholder = :parkingAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByChequeAllowedFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.chequeAllowedFlag = :chequeAllowedFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByMicrChequeChrgAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.micrChequeChrgAccountPlaceholder = :micrChequeChrgAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByOverDueIntPaidAcPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.overDueIntPaidAcPlaceholder = :overDueIntPaidAcPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByOverDueNormalProfitAndLossAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.overDueNormalProfitAndLossAccountPlaceholder = :overDueNormalProfitAndLossAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByMergeIntPtranFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.mergeIntPtranFlag = :mergeIntPtranFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByOperativeAccountWithoutEnoughFundsToBeDebited", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.operativeAccountWithoutEnoughFundsToBeDebited = :operativeAccountWithoutEnoughFundsToBeDebited"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByAdvanceInterestAccount", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.advanceInterestAccount = :advanceInterestAccount"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByBookTransScript", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.bookTransScript = :bookTransScript"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqCrType", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqCrType = :interestCalculationFreqCrType"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqCrWeekNo", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqCrWeekNo = :interestCalculationFreqCrWeekNo"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqCrWeekDay", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqCrWeekDay = :interestCalculationFreqCrWeekDay"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqCrStartDate", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqCrStartDate = :interestCalculationFreqCrStartDate"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqCrNp", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqCrNp = :interestCalculationFreqCrNp"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqDrType", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqDrType = :interestCalculationFreqDrType"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqDrWeekNo", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqDrWeekNo = :interestCalculationFreqDrWeekNo"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqDrWeekDate", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqDrWeekDate = :interestCalculationFreqDrWeekDate"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqDrStartDate", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqDrStartDate = :interestCalculationFreqDrStartDate"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestCalculationFreqDrNp", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestCalculationFreqDrNp = :interestCalculationFreqDrNp"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByLimitLevelIntFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.limitLevelIntFlag = :limitLevelIntFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestOnQis", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestOnQis = :interestOnQis"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByLookBackPeriod", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.lookBackPeriod = :lookBackPeriod"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestOnStock", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestOnStock = :interestOnStock"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByCompoundRestDayFlag", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.compoundRestDayFlag = :compoundRestDayFlag"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByDebitIntCompoundingFreq", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.debitIntCompoundingFreq = :debitIntCompoundingFreq"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByApplyDiscountedIntRate", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.applyDiscountedIntRate = :applyDiscountedIntRate"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByPrincipalLossLinePlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.principalLossLinePlaceholder = :principalLossLinePlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByRecoveryLossLinePlaceholer", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.recoveryLossLinePlaceholer = :recoveryLossLinePlaceholer"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByChargeOffAccountPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.chargeOffAccountPlaceholder = :chargeOffAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByDealerContributionPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.dealerContributionPlaceholder = :dealerContributionPlaceholder"),
    @NamedQuery(name = "CbsSchemeInterestOrServiceChargesDetails.findByInterestWaiverDebitPlaceholder", query = "SELECT c FROM CbsSchemeInterestOrServiceChargesDetails c WHERE c.interestWaiverDebitPlaceholder = :interestWaiverDebitPlaceholder")})
public class CbsSchemeInterestOrServiceChargesDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "SCHEME_CODE")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 1)
    @Column(name = "INTEREST_PAID_FLAG")
    private String interestPaidFlag;
    @Size(max = 12)
    @Column(name = "INTEREST_PAYABLE_ACCOUNT_PLACEHOLDER")
    private String interestPayableAccountPlaceholder;
    @Size(max = 1)
    @Column(name = "INT_COLLECTION_FLAG")
    private String intCollectionFlag;
    @Size(max = 12)
    @Column(name = "INT_RECBLE_ACCOUNT_PLACEHOLDER")
    private String intRecbleAccountPlaceholder;
    @Size(max = 1)
    @Column(name = "SERVICE_CHARGES_FLAG")
    private String serviceChargesFlag;
    @Size(max = 12)
    @Column(name = "SERVICE_COLL_ACCOUNT_PLACEHOLDER")
    private String serviceCollAccountPlaceholder;
    @Size(max = 12)
    @Column(name = "PARKING_ACCOUNT_TDS_PLACEHOLDER")
    private String parkingAccountTdsPlaceholder;
    @Size(max = 12)
    @Column(name = "INCOME_EXPENSE_ACCOUNT_IN_HOME_CURRENCY")
    private String incomeExpenseAccountInHomeCurrency;
    @Size(max = 12)
    @Column(name = "NORMAL_PROFIT_AND_LOSS_ACCOUNT_PLACEHOLDER_CR")
    private String normalProfitAndLossAccountPlaceholderCr;
    @Size(max = 12)
    @Column(name = "NORMAL_PROFIT_AND_LOSS_ACCOUNT_PLACEHOLDER_DR")
    private String normalProfitAndLossAccountPlaceholderDr;
    @Size(max = 12)
    @Column(name = "PENAL_PROFIT_AND_LOSS_ACCOUNT_PLACEHOLDER_DR")
    private String penalProfitAndLossAccountPlaceholderDr;
    @Size(max = 12)
    @Column(name = "PARKING_ACCOUNT_PLACEHOLDER")
    private String parkingAccountPlaceholder;
    @Size(max = 1)
    @Column(name = "CHEQUE_ALLOWED_FLAG")
    private String chequeAllowedFlag;
    @Size(max = 12)
    @Column(name = "MICR_CHEQUE_CHRG_ACCOUNT_PLACEHOLDER")
    private String micrChequeChrgAccountPlaceholder;
    @Size(max = 12)
    @Column(name = "OVER_DUE_INT_PAID_AC_PLACEHOLDER")
    private String overDueIntPaidAcPlaceholder;
    @Size(max = 12)
    @Column(name = "OVER_DUE_NORMAL_PROFIT_AND_LOSS_ACCOUNT_PLACEHOLDER")
    private String overDueNormalProfitAndLossAccountPlaceholder;
    @Size(max = 1)
    @Column(name = "MERGE_INT_PTRAN_FLAG")
    private String mergeIntPtranFlag;
    @Size(max = 1)
    @Column(name = "OPERATIVE_ACCOUNT_WITHOUT_ENOUGH_FUNDS_TO_BE_DEBITED")
    private String operativeAccountWithoutEnoughFundsToBeDebited;
    @Size(max = 12)
    @Column(name = "ADVANCE_INTEREST_ACCOUNT")
    private String advanceInterestAccount;
    @Size(max = 100)
    @Column(name = "BOOK_TRANS_SCRIPT")
    private String bookTransScript;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_CR_TYPE")
    private String interestCalculationFreqCrType;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_CR_WEEK_NO")
    private String interestCalculationFreqCrWeekNo;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_CR_WEEK_DAY")
    private String interestCalculationFreqCrWeekDay;
    @Size(max = 2)
    @Column(name = "INTEREST_CALCULATION_FREQ_CR_START_DATE")
    private String interestCalculationFreqCrStartDate;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_CR_NP")
    private String interestCalculationFreqCrNp;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_DR_TYPE")
    private String interestCalculationFreqDrType;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_DR_WEEK_NO")
    private String interestCalculationFreqDrWeekNo;
    @Size(max = 2)
    @Column(name = "INTEREST_CALCULATION_FREQ_DR_WEEK_DATE")
    private String interestCalculationFreqDrWeekDate;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_DR_START_DATE")
    private String interestCalculationFreqDrStartDate;
    @Size(max = 1)
    @Column(name = "INTEREST_CALCULATION_FREQ_DR_NP")
    private String interestCalculationFreqDrNp;
    @Size(max = 1)
    @Column(name = "LIMIT_LEVEL_INT_FLAG")
    private String limitLevelIntFlag;
    @Size(max = 1)
    @Column(name = "INTEREST_ON_QIS")
    private String interestOnQis;
    @Size(max = 3)
    @Column(name = "LOOK_BACK_PERIOD")
    private String lookBackPeriod;
    @Size(max = 1)
    @Column(name = "INTEREST_ON_STOCK")
    private String interestOnStock;
    @Size(max = 1)
    @Column(name = "COMPOUND_REST_DAY_FLAG")
    private String compoundRestDayFlag;
    @Size(max = 1)
    @Column(name = "DEBIT_INT_COMPOUNDING_FREQ")
    private String debitIntCompoundingFreq;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "APPLY_DISCOUNTED_INT_RATE")
    private Double applyDiscountedIntRate;
    @Size(max = 12)
    @Column(name = "PRINCIPAL_LOSS_LINE_PLACEHOLDER")
    private String principalLossLinePlaceholder;
    @Size(max = 12)
    @Column(name = "RECOVERY_LOSS_LINE_PLACEHOLER")
    private String recoveryLossLinePlaceholer;
    @Size(max = 12)
    @Column(name = "CHARGE_OFF_ACCOUNT_PLACEHOLDER")
    private String chargeOffAccountPlaceholder;
    @Size(max = 12)
    @Column(name = "DEALER_CONTRIBUTION_PLACEHOLDER")
    private String dealerContributionPlaceholder;
    @Size(max = 12)
    @Column(name = "INTEREST_WAIVER_DEBIT_PLACEHOLDER")
    private String interestWaiverDebitPlaceholder;

    public CbsSchemeInterestOrServiceChargesDetails() {
    }

    public CbsSchemeInterestOrServiceChargesDetails(String schemeCode) {
        this.schemeCode = schemeCode;
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

    public String getParkingAccountTdsPlaceholder() {
        return parkingAccountTdsPlaceholder;
    }

    public void setParkingAccountTdsPlaceholder(String parkingAccountTdsPlaceholder) {
        this.parkingAccountTdsPlaceholder = parkingAccountTdsPlaceholder;
    }

    public String getIncomeExpenseAccountInHomeCurrency() {
        return incomeExpenseAccountInHomeCurrency;
    }

    public void setIncomeExpenseAccountInHomeCurrency(String incomeExpenseAccountInHomeCurrency) {
        this.incomeExpenseAccountInHomeCurrency = incomeExpenseAccountInHomeCurrency;
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

    public String getPenalProfitAndLossAccountPlaceholderDr() {
        return penalProfitAndLossAccountPlaceholderDr;
    }

    public void setPenalProfitAndLossAccountPlaceholderDr(String penalProfitAndLossAccountPlaceholderDr) {
        this.penalProfitAndLossAccountPlaceholderDr = penalProfitAndLossAccountPlaceholderDr;
    }

    public String getParkingAccountPlaceholder() {
        return parkingAccountPlaceholder;
    }

    public void setParkingAccountPlaceholder(String parkingAccountPlaceholder) {
        this.parkingAccountPlaceholder = parkingAccountPlaceholder;
    }

    public String getChequeAllowedFlag() {
        return chequeAllowedFlag;
    }

    public void setChequeAllowedFlag(String chequeAllowedFlag) {
        this.chequeAllowedFlag = chequeAllowedFlag;
    }

    public String getMicrChequeChrgAccountPlaceholder() {
        return micrChequeChrgAccountPlaceholder;
    }

    public void setMicrChequeChrgAccountPlaceholder(String micrChequeChrgAccountPlaceholder) {
        this.micrChequeChrgAccountPlaceholder = micrChequeChrgAccountPlaceholder;
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

    public String getMergeIntPtranFlag() {
        return mergeIntPtranFlag;
    }

    public void setMergeIntPtranFlag(String mergeIntPtranFlag) {
        this.mergeIntPtranFlag = mergeIntPtranFlag;
    }

    public String getOperativeAccountWithoutEnoughFundsToBeDebited() {
        return operativeAccountWithoutEnoughFundsToBeDebited;
    }

    public void setOperativeAccountWithoutEnoughFundsToBeDebited(String operativeAccountWithoutEnoughFundsToBeDebited) {
        this.operativeAccountWithoutEnoughFundsToBeDebited = operativeAccountWithoutEnoughFundsToBeDebited;
    }

    public String getAdvanceInterestAccount() {
        return advanceInterestAccount;
    }

    public void setAdvanceInterestAccount(String advanceInterestAccount) {
        this.advanceInterestAccount = advanceInterestAccount;
    }

    public String getBookTransScript() {
        return bookTransScript;
    }

    public void setBookTransScript(String bookTransScript) {
        this.bookTransScript = bookTransScript;
    }

    public String getInterestCalculationFreqCrType() {
        return interestCalculationFreqCrType;
    }

    public void setInterestCalculationFreqCrType(String interestCalculationFreqCrType) {
        this.interestCalculationFreqCrType = interestCalculationFreqCrType;
    }

    public String getInterestCalculationFreqCrWeekNo() {
        return interestCalculationFreqCrWeekNo;
    }

    public void setInterestCalculationFreqCrWeekNo(String interestCalculationFreqCrWeekNo) {
        this.interestCalculationFreqCrWeekNo = interestCalculationFreqCrWeekNo;
    }

    public String getInterestCalculationFreqCrWeekDay() {
        return interestCalculationFreqCrWeekDay;
    }

    public void setInterestCalculationFreqCrWeekDay(String interestCalculationFreqCrWeekDay) {
        this.interestCalculationFreqCrWeekDay = interestCalculationFreqCrWeekDay;
    }

    public String getInterestCalculationFreqCrStartDate() {
        return interestCalculationFreqCrStartDate;
    }

    public void setInterestCalculationFreqCrStartDate(String interestCalculationFreqCrStartDate) {
        this.interestCalculationFreqCrStartDate = interestCalculationFreqCrStartDate;
    }

    public String getInterestCalculationFreqCrNp() {
        return interestCalculationFreqCrNp;
    }

    public void setInterestCalculationFreqCrNp(String interestCalculationFreqCrNp) {
        this.interestCalculationFreqCrNp = interestCalculationFreqCrNp;
    }

    public String getInterestCalculationFreqDrType() {
        return interestCalculationFreqDrType;
    }

    public void setInterestCalculationFreqDrType(String interestCalculationFreqDrType) {
        this.interestCalculationFreqDrType = interestCalculationFreqDrType;
    }

    public String getInterestCalculationFreqDrWeekNo() {
        return interestCalculationFreqDrWeekNo;
    }

    public void setInterestCalculationFreqDrWeekNo(String interestCalculationFreqDrWeekNo) {
        this.interestCalculationFreqDrWeekNo = interestCalculationFreqDrWeekNo;
    }

    public String getInterestCalculationFreqDrWeekDate() {
        return interestCalculationFreqDrWeekDate;
    }

    public void setInterestCalculationFreqDrWeekDate(String interestCalculationFreqDrWeekDate) {
        this.interestCalculationFreqDrWeekDate = interestCalculationFreqDrWeekDate;
    }

    public String getInterestCalculationFreqDrStartDate() {
        return interestCalculationFreqDrStartDate;
    }

    public void setInterestCalculationFreqDrStartDate(String interestCalculationFreqDrStartDate) {
        this.interestCalculationFreqDrStartDate = interestCalculationFreqDrStartDate;
    }

    public String getInterestCalculationFreqDrNp() {
        return interestCalculationFreqDrNp;
    }

    public void setInterestCalculationFreqDrNp(String interestCalculationFreqDrNp) {
        this.interestCalculationFreqDrNp = interestCalculationFreqDrNp;
    }

    public String getLimitLevelIntFlag() {
        return limitLevelIntFlag;
    }

    public void setLimitLevelIntFlag(String limitLevelIntFlag) {
        this.limitLevelIntFlag = limitLevelIntFlag;
    }

    public String getInterestOnQis() {
        return interestOnQis;
    }

    public void setInterestOnQis(String interestOnQis) {
        this.interestOnQis = interestOnQis;
    }

    public String getLookBackPeriod() {
        return lookBackPeriod;
    }

    public void setLookBackPeriod(String lookBackPeriod) {
        this.lookBackPeriod = lookBackPeriod;
    }

    public String getInterestOnStock() {
        return interestOnStock;
    }

    public void setInterestOnStock(String interestOnStock) {
        this.interestOnStock = interestOnStock;
    }

    public String getCompoundRestDayFlag() {
        return compoundRestDayFlag;
    }

    public void setCompoundRestDayFlag(String compoundRestDayFlag) {
        this.compoundRestDayFlag = compoundRestDayFlag;
    }

    public String getDebitIntCompoundingFreq() {
        return debitIntCompoundingFreq;
    }

    public void setDebitIntCompoundingFreq(String debitIntCompoundingFreq) {
        this.debitIntCompoundingFreq = debitIntCompoundingFreq;
    }

    public Double getApplyDiscountedIntRate() {
        return applyDiscountedIntRate;
    }

    public void setApplyDiscountedIntRate(Double applyDiscountedIntRate) {
        this.applyDiscountedIntRate = applyDiscountedIntRate;
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

    public String getChargeOffAccountPlaceholder() {
        return chargeOffAccountPlaceholder;
    }

    public void setChargeOffAccountPlaceholder(String chargeOffAccountPlaceholder) {
        this.chargeOffAccountPlaceholder = chargeOffAccountPlaceholder;
    }

    public String getDealerContributionPlaceholder() {
        return dealerContributionPlaceholder;
    }

    public void setDealerContributionPlaceholder(String dealerContributionPlaceholder) {
        this.dealerContributionPlaceholder = dealerContributionPlaceholder;
    }

    public String getInterestWaiverDebitPlaceholder() {
        return interestWaiverDebitPlaceholder;
    }

    public void setInterestWaiverDebitPlaceholder(String interestWaiverDebitPlaceholder) {
        this.interestWaiverDebitPlaceholder = interestWaiverDebitPlaceholder;
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
        if (!(object instanceof CbsSchemeInterestOrServiceChargesDetails)) {
            return false;
        }
        CbsSchemeInterestOrServiceChargesDetails other = (CbsSchemeInterestOrServiceChargesDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeInterestOrServiceChargesDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
