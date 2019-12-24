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
@Table(name = "cbs_scheme_deposits_scheme_parameters_maintanance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findAll", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findBySchemeCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findBySchemeType", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByCurrencyCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDepositAmountMinimum", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.depositAmountMinimum = :depositAmountMinimum"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDepositAmountMaximum", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.depositAmountMaximum = :depositAmountMaximum"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDepositAmountSteps", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.depositAmountSteps = :depositAmountSteps"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPeriodMiniMonths", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.periodMiniMonths = :periodMiniMonths"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPeriodMiniDays", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.periodMiniDays = :periodMiniDays"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPeriodMaxMonths", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.periodMaxMonths = :periodMaxMonths"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPeriodMaxDays", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.periodMaxDays = :periodMaxDays"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPeriodStepsMonths", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.periodStepsMonths = :periodStepsMonths"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPeriodStepsDays", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.periodStepsDays = :periodStepsDays"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDepositReportTemplate", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.depositReportTemplate = :depositReportTemplate"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDepositType", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.depositType = :depositType"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByAutoRenewal", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.autoRenewal = :autoRenewal"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByMaxRenewalAllowed", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.maxRenewalAllowed = :maxRenewalAllowed"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByRenewalPeriodMonths", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.renewalPeriodMonths = :renewalPeriodMonths"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByRenewalPeriodDays", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.renewalPeriodDays = :renewalPeriodDays"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByRenewalAllowedPeriod", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.renewalAllowedPeriod = :renewalAllowedPeriod"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByAutoRenewalGracePeriod", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.autoRenewalGracePeriod = :autoRenewalGracePeriod"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findBySundryDepositPlaceholder", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.sundryDepositPlaceholder = :sundryDepositPlaceholder"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByRepaymentReportCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.repaymentReportCode = :repaymentReportCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByValueDatedClosure", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.valueDatedClosure = :valueDatedClosure"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByCallDepositNoticePeriodMonths", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.callDepositNoticePeriodMonths = :callDepositNoticePeriodMonths"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByCallDepositNoticePeriodDays", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.callDepositNoticePeriodDays = :callDepositNoticePeriodDays"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDelayInstallmentTblCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.delayInstallmentTblCode = :delayInstallmentTblCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDelayWithinMonth", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.delayWithinMonth = :delayWithinMonth"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDelayAllowedPeriodMonths", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.delayAllowedPeriodMonths = :delayAllowedPeriodMonths"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByDelayAllowedPeriodDays", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.delayAllowedPeriodDays = :delayAllowedPeriodDays"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPenalFeePlaceholder", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.penalFeePlaceholder = :penalFeePlaceholder"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPenalFeeReportCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.penalFeeReportCode = :penalFeeReportCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPrintPbDr", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.printPbDr = :printPbDr"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByMatAmtToleranceLimit", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.matAmtToleranceLimit = :matAmtToleranceLimit"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByUseInventory", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.useInventory = :useInventory"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByInventoryClass", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.inventoryClass = :inventoryClass"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByInventoryType", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.inventoryType = :inventoryType"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByInventoryLoanClass", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.inventoryLoanClass = :inventoryLoanClass"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByInventoryLoanCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.inventoryLoanCode = :inventoryLoanCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByCommissionPlaceholder", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.commissionPlaceholder = :commissionPlaceholder"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByServiceChargeTblCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.serviceChargeTblCode = :serviceChargeTblCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByCommissionTblCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.commissionTblCode = :commissionTblCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByPreOrPartClosurePenaltyCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.preOrPartClosurePenaltyCode = :preOrPartClosurePenaltyCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByCommissionReportCode", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.commissionReportCode = :commissionReportCode"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByAllowSweeps", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.allowSweeps = :allowSweeps"),
    @NamedQuery(name = "CbsSchemeDepositsSchemeParametersMaintanance.findByAllowPartClosure", query = "SELECT c FROM CbsSchemeDepositsSchemeParametersMaintanance c WHERE c.allowPartClosure = :allowPartClosure")})
public class CbsSchemeDepositsSchemeParametersMaintanance extends BaseEntity implements Serializable {
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
    @Size(max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DEPOSIT_AMOUNT_MINIMUM")
    private BigDecimal depositAmountMinimum;
    @Column(name = "DEPOSIT_AMOUNT_MAXIMUM")
    private BigDecimal depositAmountMaximum;
    @Column(name = "DEPOSIT_AMOUNT_STEPS")
    private BigDecimal depositAmountSteps;
    @Size(max = 3)
    @Column(name = "PERIOD_MINI_MONTHS")
    private String periodMiniMonths;
    @Size(max = 3)
    @Column(name = "PERIOD_MINI_DAYS")
    private String periodMiniDays;
    @Size(max = 3)
    @Column(name = "PERIOD_MAX_MONTHS")
    private String periodMaxMonths;
    @Size(max = 3)
    @Column(name = "PERIOD_MAX_DAYS")
    private String periodMaxDays;
    @Size(max = 3)
    @Column(name = "PERIOD_STEPS_MONTHS")
    private String periodStepsMonths;
    @Size(max = 3)
    @Column(name = "PERIOD_STEPS_DAYS")
    private String periodStepsDays;
    @Size(max = 25)
    @Column(name = "DEPOSIT_REPORT_TEMPLATE")
    private String depositReportTemplate;
    @Size(max = 1)
    @Column(name = "DEPOSIT_TYPE")
    private String depositType;
    @Size(max = 1)
    @Column(name = "AUTO_RENEWAL")
    private String autoRenewal;
    @Size(max = 1)
    @Column(name = "MAX_RENEWAL_ALLOWED")
    private String maxRenewalAllowed;
    @Size(max = 3)
    @Column(name = "RENEWAL_PERIOD_MONTHS")
    private String renewalPeriodMonths;
    @Size(max = 3)
    @Column(name = "RENEWAL_PERIOD_DAYS")
    private String renewalPeriodDays;
    @Size(max = 3)
    @Column(name = "RENEWAL_ALLOWED_PERIOD")
    private String renewalAllowedPeriod;
    @Size(max = 3)
    @Column(name = "AUTO_RENEWAL_GRACE_PERIOD")
    private String autoRenewalGracePeriod;
    @Size(max = 12)
    @Column(name = "SUNDRY_DEPOSIT_PLACEHOLDER")
    private String sundryDepositPlaceholder;
    @Size(max = 5)
    @Column(name = "REPAYMENT_REPORT_CODE")
    private String repaymentReportCode;
    @Size(max = 1)
    @Column(name = "VALUE_DATED_CLOSURE")
    private String valueDatedClosure;
    @Size(max = 3)
    @Column(name = "CALL_DEPOSIT_NOTICE_PERIOD_MONTHS")
    private String callDepositNoticePeriodMonths;
    @Size(max = 3)
    @Column(name = "CALL_DEPOSIT_NOTICE_PERIOD_DAYS")
    private String callDepositNoticePeriodDays;
    @Size(max = 5)
    @Column(name = "DELAY_INSTALLMENT_TBL_CODE")
    private String delayInstallmentTblCode;
    @Size(max = 3)
    @Column(name = "DELAY_WITHIN_MONTH")
    private String delayWithinMonth;
    @Size(max = 3)
    @Column(name = "DELAY_ALLOWED_PERIOD_MONTHS")
    private String delayAllowedPeriodMonths;
    @Size(max = 3)
    @Column(name = "DELAY_ALLOWED_PERIOD_DAYS")
    private String delayAllowedPeriodDays;
    @Size(max = 10)
    @Column(name = "PENAL_FEE_PLACEHOLDER")
    private String penalFeePlaceholder;
    @Size(max = 5)
    @Column(name = "PENAL_FEE_REPORT_CODE")
    private String penalFeeReportCode;
    @Size(max = 1)
    @Column(name = "PRINT_PB_DR")
    private String printPbDr;
    @Size(max = 1)
    @Column(name = "MAT_AMT_TOLERANCE_LIMIT")
    private String matAmtToleranceLimit;
    @Size(max = 1)
    @Column(name = "USE_INVENTORY")
    private String useInventory;
    @Size(max = 3)
    @Column(name = "INVENTORY_CLASS")
    private String inventoryClass;
    @Size(max = 6)
    @Column(name = "INVENTORY_TYPE")
    private String inventoryType;
    @Size(max = 2)
    @Column(name = "INVENTORY_LOAN_CLASS")
    private String inventoryLoanClass;
    @Size(max = 12)
    @Column(name = "INVENTORY_LOAN_CODE")
    private String inventoryLoanCode;
    @Size(max = 12)
    @Column(name = "COMMISSION_PLACEHOLDER")
    private String commissionPlaceholder;
    @Size(max = 5)
    @Column(name = "SERVICE_CHARGE_TBL_CODE")
    private String serviceChargeTblCode;
    @Size(max = 5)
    @Column(name = "COMMISSION_TBL_CODE")
    private String commissionTblCode;
    @Size(max = 5)
    @Column(name = "PRE_OR_PART_CLOSURE_PENALTY_CODE")
    private String preOrPartClosurePenaltyCode;
    @Size(max = 5)
    @Column(name = "COMMISSION_REPORT_CODE")
    private String commissionReportCode;
    @Size(max = 1)
    @Column(name = "ALLOW_SWEEPS")
    private String allowSweeps;
    @Size(max = 1)
    @Column(name = "ALLOW_PART_CLOSURE")
    private String allowPartClosure;

    public CbsSchemeDepositsSchemeParametersMaintanance() {
    }

    public CbsSchemeDepositsSchemeParametersMaintanance(String schemeCode) {
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDepositAmountMinimum() {
        return depositAmountMinimum;
    }

    public void setDepositAmountMinimum(BigDecimal depositAmountMinimum) {
        this.depositAmountMinimum = depositAmountMinimum;
    }

    public BigDecimal getDepositAmountMaximum() {
        return depositAmountMaximum;
    }

    public void setDepositAmountMaximum(BigDecimal depositAmountMaximum) {
        this.depositAmountMaximum = depositAmountMaximum;
    }

    public BigDecimal getDepositAmountSteps() {
        return depositAmountSteps;
    }

    public void setDepositAmountSteps(BigDecimal depositAmountSteps) {
        this.depositAmountSteps = depositAmountSteps;
    }

    public String getPeriodMiniMonths() {
        return periodMiniMonths;
    }

    public void setPeriodMiniMonths(String periodMiniMonths) {
        this.periodMiniMonths = periodMiniMonths;
    }

    public String getPeriodMiniDays() {
        return periodMiniDays;
    }

    public void setPeriodMiniDays(String periodMiniDays) {
        this.periodMiniDays = periodMiniDays;
    }

    public String getPeriodMaxMonths() {
        return periodMaxMonths;
    }

    public void setPeriodMaxMonths(String periodMaxMonths) {
        this.periodMaxMonths = periodMaxMonths;
    }

    public String getPeriodMaxDays() {
        return periodMaxDays;
    }

    public void setPeriodMaxDays(String periodMaxDays) {
        this.periodMaxDays = periodMaxDays;
    }

    public String getPeriodStepsMonths() {
        return periodStepsMonths;
    }

    public void setPeriodStepsMonths(String periodStepsMonths) {
        this.periodStepsMonths = periodStepsMonths;
    }

    public String getPeriodStepsDays() {
        return periodStepsDays;
    }

    public void setPeriodStepsDays(String periodStepsDays) {
        this.periodStepsDays = periodStepsDays;
    }

    public String getDepositReportTemplate() {
        return depositReportTemplate;
    }

    public void setDepositReportTemplate(String depositReportTemplate) {
        this.depositReportTemplate = depositReportTemplate;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getAutoRenewal() {
        return autoRenewal;
    }

    public void setAutoRenewal(String autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    public String getMaxRenewalAllowed() {
        return maxRenewalAllowed;
    }

    public void setMaxRenewalAllowed(String maxRenewalAllowed) {
        this.maxRenewalAllowed = maxRenewalAllowed;
    }

    public String getRenewalPeriodMonths() {
        return renewalPeriodMonths;
    }

    public void setRenewalPeriodMonths(String renewalPeriodMonths) {
        this.renewalPeriodMonths = renewalPeriodMonths;
    }

    public String getRenewalPeriodDays() {
        return renewalPeriodDays;
    }

    public void setRenewalPeriodDays(String renewalPeriodDays) {
        this.renewalPeriodDays = renewalPeriodDays;
    }

    public String getRenewalAllowedPeriod() {
        return renewalAllowedPeriod;
    }

    public void setRenewalAllowedPeriod(String renewalAllowedPeriod) {
        this.renewalAllowedPeriod = renewalAllowedPeriod;
    }

    public String getAutoRenewalGracePeriod() {
        return autoRenewalGracePeriod;
    }

    public void setAutoRenewalGracePeriod(String autoRenewalGracePeriod) {
        this.autoRenewalGracePeriod = autoRenewalGracePeriod;
    }

    public String getSundryDepositPlaceholder() {
        return sundryDepositPlaceholder;
    }

    public void setSundryDepositPlaceholder(String sundryDepositPlaceholder) {
        this.sundryDepositPlaceholder = sundryDepositPlaceholder;
    }

    public String getRepaymentReportCode() {
        return repaymentReportCode;
    }

    public void setRepaymentReportCode(String repaymentReportCode) {
        this.repaymentReportCode = repaymentReportCode;
    }

    public String getValueDatedClosure() {
        return valueDatedClosure;
    }

    public void setValueDatedClosure(String valueDatedClosure) {
        this.valueDatedClosure = valueDatedClosure;
    }

    public String getCallDepositNoticePeriodMonths() {
        return callDepositNoticePeriodMonths;
    }

    public void setCallDepositNoticePeriodMonths(String callDepositNoticePeriodMonths) {
        this.callDepositNoticePeriodMonths = callDepositNoticePeriodMonths;
    }

    public String getCallDepositNoticePeriodDays() {
        return callDepositNoticePeriodDays;
    }

    public void setCallDepositNoticePeriodDays(String callDepositNoticePeriodDays) {
        this.callDepositNoticePeriodDays = callDepositNoticePeriodDays;
    }

    public String getDelayInstallmentTblCode() {
        return delayInstallmentTblCode;
    }

    public void setDelayInstallmentTblCode(String delayInstallmentTblCode) {
        this.delayInstallmentTblCode = delayInstallmentTblCode;
    }

    public String getDelayWithinMonth() {
        return delayWithinMonth;
    }

    public void setDelayWithinMonth(String delayWithinMonth) {
        this.delayWithinMonth = delayWithinMonth;
    }

    public String getDelayAllowedPeriodMonths() {
        return delayAllowedPeriodMonths;
    }

    public void setDelayAllowedPeriodMonths(String delayAllowedPeriodMonths) {
        this.delayAllowedPeriodMonths = delayAllowedPeriodMonths;
    }

    public String getDelayAllowedPeriodDays() {
        return delayAllowedPeriodDays;
    }

    public void setDelayAllowedPeriodDays(String delayAllowedPeriodDays) {
        this.delayAllowedPeriodDays = delayAllowedPeriodDays;
    }

    public String getPenalFeePlaceholder() {
        return penalFeePlaceholder;
    }

    public void setPenalFeePlaceholder(String penalFeePlaceholder) {
        this.penalFeePlaceholder = penalFeePlaceholder;
    }

    public String getPenalFeeReportCode() {
        return penalFeeReportCode;
    }

    public void setPenalFeeReportCode(String penalFeeReportCode) {
        this.penalFeeReportCode = penalFeeReportCode;
    }

    public String getPrintPbDr() {
        return printPbDr;
    }

    public void setPrintPbDr(String printPbDr) {
        this.printPbDr = printPbDr;
    }

    public String getMatAmtToleranceLimit() {
        return matAmtToleranceLimit;
    }

    public void setMatAmtToleranceLimit(String matAmtToleranceLimit) {
        this.matAmtToleranceLimit = matAmtToleranceLimit;
    }

    public String getUseInventory() {
        return useInventory;
    }

    public void setUseInventory(String useInventory) {
        this.useInventory = useInventory;
    }

    public String getInventoryClass() {
        return inventoryClass;
    }

    public void setInventoryClass(String inventoryClass) {
        this.inventoryClass = inventoryClass;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getInventoryLoanClass() {
        return inventoryLoanClass;
    }

    public void setInventoryLoanClass(String inventoryLoanClass) {
        this.inventoryLoanClass = inventoryLoanClass;
    }

    public String getInventoryLoanCode() {
        return inventoryLoanCode;
    }

    public void setInventoryLoanCode(String inventoryLoanCode) {
        this.inventoryLoanCode = inventoryLoanCode;
    }

    public String getCommissionPlaceholder() {
        return commissionPlaceholder;
    }

    public void setCommissionPlaceholder(String commissionPlaceholder) {
        this.commissionPlaceholder = commissionPlaceholder;
    }

    public String getServiceChargeTblCode() {
        return serviceChargeTblCode;
    }

    public void setServiceChargeTblCode(String serviceChargeTblCode) {
        this.serviceChargeTblCode = serviceChargeTblCode;
    }

    public String getCommissionTblCode() {
        return commissionTblCode;
    }

    public void setCommissionTblCode(String commissionTblCode) {
        this.commissionTblCode = commissionTblCode;
    }

    public String getPreOrPartClosurePenaltyCode() {
        return preOrPartClosurePenaltyCode;
    }

    public void setPreOrPartClosurePenaltyCode(String preOrPartClosurePenaltyCode) {
        this.preOrPartClosurePenaltyCode = preOrPartClosurePenaltyCode;
    }

    public String getCommissionReportCode() {
        return commissionReportCode;
    }

    public void setCommissionReportCode(String commissionReportCode) {
        this.commissionReportCode = commissionReportCode;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getAllowPartClosure() {
        return allowPartClosure;
    }

    public void setAllowPartClosure(String allowPartClosure) {
        this.allowPartClosure = allowPartClosure;
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
        if (!(object instanceof CbsSchemeDepositsSchemeParametersMaintanance)) {
            return false;
        }
        CbsSchemeDepositsSchemeParametersMaintanance other = (CbsSchemeDepositsSchemeParametersMaintanance) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeDepositsSchemeParametersMaintanance[ schemeCode=" + schemeCode + " ]";
    }
    
}
