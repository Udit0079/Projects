/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.loan;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_scheme_currency_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findAll", query = "SELECT c FROM CbsSchemeCurrencyDetails c"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.cbsSchemeCurrencyDetailsPK.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.cbsSchemeCurrencyDetailsPK.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAcctReportCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.acctReportCode = :acctReportCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDefaultNationalRate", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.defaultNationalRate = :defaultNationalRate"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByRevaluationAcctPlaceholder", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.revaluationAcctPlaceholder = :revaluationAcctPlaceholder"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrIntRateCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crIntRateCode = :crIntRateCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrIntRateCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drIntRateCode = :drIntRateCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDefaultCustPreferentialToAcct", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.defaultCustPreferentialToAcct = :defaultCustPreferentialToAcct"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAcctOpenEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.acctOpenEvent = :acctOpenEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAcctMatchEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.acctMatchEvent = :acctMatchEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAcctClosureEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.acctClosureEvent = :acctClosureEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByLedgerFolioCalcEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.ledgerFolioCalcEvent = :ledgerFolioCalcEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAdhocPassSheetPrintEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.adhocPassSheetPrintEvent = :adhocPassSheetPrintEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByRegPassSheetPrintEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.regPassSheetPrintEvent = :regPassSheetPrintEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCommitmentEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.commitmentEvent = :commitmentEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrCashLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crCashLimit = :crCashLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrCashLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drCashLimit = :drCashLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrClgLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crClgLimit = :crClgLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrClgLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drClgLimit = :drClgLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrXrefLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crXrefLimit = :crXrefLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrXrefLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drXrefLimit = :drXrefLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrCashAbnLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drCashAbnLimit = :drCashAbnLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrClgAbnLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drClgAbnLimit = :drClgAbnLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrxRefAbnLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drxRefAbnLimit = :drxRefAbnLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByNewAccountAbnTranAmt", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.newAccountAbnTranAmt = :newAccountAbnTranAmt"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByInterestTableCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.interestTableCode = :interestTableCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrMin", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crMin = :crMin"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrMax", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crMax = :crMax"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrMin", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drMin = :drMin"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrMax", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drMax = :drMax"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrDaysInAYear", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crDaysInAYear = :crDaysInAYear"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrLeapYearAdjustment", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crLeapYearAdjustment = :crLeapYearAdjustment"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrDaysinaYear", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drDaysinaYear = :drDaysinaYear"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrLeapYearAdjustment", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drLeapYearAdjustment = :drLeapYearAdjustment"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByMinIntPaidAmt", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.minIntPaidAmt = :minIntPaidAmt"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByMinIntCollAmt", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.minIntCollAmt = :minIntCollAmt"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByBookAdvanceInt", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.bookAdvanceInt = :bookAdvanceInt"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByMicrChrgEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.micrChrgEvent = :micrChrgEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByResidualIntAdjustmentAmountForBooking", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.residualIntAdjustmentAmountForBooking = :residualIntAdjustmentAmountForBooking"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByWithoutTaxAmountRndSt", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.withoutTaxAmountRndSt = :withoutTaxAmountRndSt"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByWithoutTaxAmountRndAmount", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.withoutTaxAmountRndAmount = :withoutTaxAmountRndAmount"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntPrdRndStCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intPrdRndStCr = :intPrdRndStCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntPrdRndAmountCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intPrdRndAmountCr = :intPrdRndAmountCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntPrdRoundingStartDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intPrdRoundingStartDr = :intPrdRoundingStartDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntPrdRoundingAmtDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intPrdRoundingAmtDr = :intPrdRoundingAmtDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntAmtRoundingStartCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intAmtRoundingStartCr = :intAmtRoundingStartCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntAmtRoundingAmtCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intAmtRoundingAmtCr = :intAmtRoundingAmtCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntAmtRoundingStartDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intAmtRoundingStartDr = :intAmtRoundingStartDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntAmtRoundingAmtDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intAmtRoundingAmtDr = :intAmtRoundingAmtDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntPaidRptCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intPaidRptCode = :intPaidRptCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntCollRptCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intCollRptCode = :intCollRptCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntDrRptCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intDrRptCode = :intDrRptCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIntCrRptCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.intCrRptCode = :intCrRptCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByTaxCollRptCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.taxCollRptCode = :taxCollRptCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByTaxCollAccountPlaceholder", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.taxCollAccountPlaceholder = :taxCollAccountPlaceholder"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByTaxFlag", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.taxFlag = :taxFlag"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByIncludeFloorLimitForTax", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.includeFloorLimitForTax = :includeFloorLimitForTax"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByProportionateFloorLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.proportionateFloorLimit = :proportionateFloorLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByWithoutTaxFlrLim", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.withoutTaxFlrLim = :withoutTaxFlrLim"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByWithholdingTax", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.withholdingTax = :withholdingTax"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByWtaxMaturityAdjReqd", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.wtaxMaturityAdjReqd = :wtaxMaturityAdjReqd"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByEndOfDayBalCheck", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.endOfDayBalCheck = :endOfDayBalCheck"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByEndOfDayMinBalance", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.endOfDayMinBalance = :endOfDayMinBalance"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrcrInd", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drcrInd = :drcrInd"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByEndOfDayMaxBalance", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.endOfDayMaxBalance = :endOfDayMaxBalance"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrcrInd1", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drcrInd1 = :drcrInd1"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByEodMinBalanceExceptionCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.eodMinBalanceExceptionCode = :eodMinBalanceExceptionCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByEodMaxBalanceExceptionCode", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.eodMaxBalanceExceptionCode = :eodMaxBalanceExceptionCode"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCashLimitDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.cashLimitDr = :cashLimitDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByClgLimitDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.clgLimitDr = :clgLimitDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByTransferLimitDr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.transferLimitDr = :transferLimitDr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCashLimitCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.cashLimitCr = :cashLimitCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByClgLimitCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.clgLimitCr = :clgLimitCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByTransferLimitCr", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.transferLimitCr = :transferLimitCr"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByLateCashTran", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.lateCashTran = :lateCashTran"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByTranAmtLimit", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.tranAmtLimit = :tranAmtLimit"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByDrTranAmtNotAllowed", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.drTranAmtNotAllowed = :drTranAmtNotAllowed"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByCrTranAmtNotAllowed", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.crTranAmtNotAllowed = :crTranAmtNotAllowed"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByMinBalanceEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.minBalanceEvent = :minBalanceEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAcctMinBalance", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.acctMinBalance = :acctMinBalance"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findBySweepMinBalance", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.sweepMinBalance = :sweepMinBalance"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByMinChargePeriod", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.minChargePeriod = :minChargePeriod"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByPenalChargePeriod", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.penalChargePeriod = :penalChargePeriod"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByMinBalanceServiceChrg", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.minBalanceServiceChrg = :minBalanceServiceChrg"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByRegularIntCertificatePrintingEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.regularIntCertificatePrintingEvent = :regularIntCertificatePrintingEvent"),
    @NamedQuery(name = "CbsSchemeCurrencyDetails.findByAdhocIntCertificatePrintingEvent", query = "SELECT c FROM CbsSchemeCurrencyDetails c WHERE c.adhocIntCertificatePrintingEvent = :adhocIntCertificatePrintingEvent")})
public class CbsSchemeCurrencyDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CbsSchemeCurrencyDetailsPK cbsSchemeCurrencyDetailsPK;
    @Size(max = 6)
    @Column(name = "SCHEME_TYPE")
    private String schemeType;
    @Size(max = 12)
    @Column(name = "ACCT_REPORT_CODE")
    private String acctReportCode;
    @Size(max = 3)
    @Column(name = "DEFAULT_NATIONAL_RATE")
    private String defaultNationalRate;
    @Size(max = 10)
    @Column(name = "REVALUATION_ACCT_PLACEHOLDER")
    private String revaluationAcctPlaceholder;
    @Size(max = 6)
    @Column(name = "CR_INT_RATE_CODE")
    private String crIntRateCode;
    @Size(max = 6)
    @Column(name = "DR_INT_RATE_CODE")
    private String drIntRateCode;
    @Size(max = 1)
    @Column(name = "DEFAULT_CUST_PREFERENTIAL_TO_ACCT")
    private String defaultCustPreferentialToAcct;
    @Size(max = 20)
    @Column(name = "ACCT_OPEN_EVENT")
    private String acctOpenEvent;
    @Size(max = 20)
    @Column(name = "ACCT_MATCH_EVENT")
    private String acctMatchEvent;
    @Size(max = 20)
    @Column(name = "ACCT_CLOSURE_EVENT")
    private String acctClosureEvent;
    @Size(max = 20)
    @Column(name = "LEDGER_FOLIO_CALC_EVENT")
    private String ledgerFolioCalcEvent;
    @Size(max = 20)
    @Column(name = "ADHOC_PASS_SHEET_PRINT_EVENT")
    private String adhocPassSheetPrintEvent;
    @Size(max = 20)
    @Column(name = "REG_PASS_SHEET_PRINT_EVENT")
    private String regPassSheetPrintEvent;
    @Size(max = 20)
    @Column(name = "COMMITMENT_EVENT")
    private String commitmentEvent;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CR_CASH_LIMIT")
    private BigDecimal crCashLimit;
    @Column(name = "DR_CASH_LIMIT")
    private BigDecimal drCashLimit;
    @Column(name = "CR_CLG_LIMIT")
    private BigDecimal crClgLimit;
    @Column(name = "DR_CLG_LIMIT")
    private BigDecimal drClgLimit;
    @Column(name = "CR_XREF_LIMIT")
    private BigDecimal crXrefLimit;
    @Column(name = "DR_XREF_LIMIT")
    private BigDecimal drXrefLimit;
    @Column(name = "DR_CASH_ABN_LIMIT")
    private BigDecimal drCashAbnLimit;
    @Column(name = "DR_CLG_ABN_LIMIT")
    private BigDecimal drClgAbnLimit;
    @Column(name = "DRX_REF_ABN_LIMIT")
    private BigDecimal drxRefAbnLimit;
    @Column(name = "NEW_ACCOUNT_ABN_TRAN_AMT")
    private BigDecimal newAccountAbnTranAmt;
    @Size(max = 5)
    @Column(name = "INTEREST_TABLE_CODE")
    private String interestTableCode;
    @Column(name = "CR_MIN")
    private BigDecimal crMin;
    @Column(name = "CR_MAX")
    private BigDecimal crMax;
    @Column(name = "DR_MIN")
    private BigDecimal drMin;
    @Column(name = "DR_MAX")
    private BigDecimal drMax;
    @Size(max = 3)
    @Column(name = "CR_DAYS_IN_A_YEAR")
    private String crDaysInAYear;
    @Size(max = 3)
    @Column(name = "CR_LEAP_YEAR_ADJUSTMENT")
    private String crLeapYearAdjustment;
    @Size(max = 3)
    @Column(name = "DR_DAYSINA_YEAR")
    private String drDaysinaYear;
    @Size(max = 3)
    @Column(name = "DR_LEAP_YEAR_ADJUSTMENT")
    private String drLeapYearAdjustment;
    @Column(name = "MIN_INT_PAID_AMT")
    private BigDecimal minIntPaidAmt;
    @Column(name = "MIN_INT_COLL_AMT")
    private BigDecimal minIntCollAmt;
    @Size(max = 1)
    @Column(name = "BOOK_ADVANCE_INT")
    private String bookAdvanceInt;
    @Size(max = 20)
    @Column(name = "MICR_CHRG_EVENT")
    private String micrChrgEvent;
    @Column(name = "RESIDUAL_INT_ADJUSTMENT_AMOUNT_FOR_BOOKING")
    private BigDecimal residualIntAdjustmentAmountForBooking;
    @Size(max = 2)
    @Column(name = "WITHOUT_TAX_AMOUNT_RND_ST")
    private String withoutTaxAmountRndSt;
    @Column(name = "WITHOUT_TAX_AMOUNT_RND_AMOUNT")
    private BigDecimal withoutTaxAmountRndAmount;
    @Size(max = 2)
    @Column(name = "INT_PRD_RND_ST_CR")
    private String intPrdRndStCr;
    @Column(name = "INT_PRD_RND_AMOUNT_CR")
    private BigDecimal intPrdRndAmountCr;
    @Size(max = 2)
    @Column(name = "INT_PRD_ROUNDING_START_DR")
    private String intPrdRoundingStartDr;
    @Column(name = "INT_PRD_ROUNDING_AMT_DR")
    private BigDecimal intPrdRoundingAmtDr;
    @Size(max = 2)
    @Column(name = "INT_AMT_ROUNDING_START_CR")
    private String intAmtRoundingStartCr;
    @Column(name = "INT_AMT_ROUNDING_AMT_CR")
    private BigDecimal intAmtRoundingAmtCr;
    @Size(max = 2)
    @Column(name = "INT_AMT_ROUNDING_START_DR")
    private String intAmtRoundingStartDr;
    @Column(name = "INT_AMT_ROUNDING_AMT_DR")
    private BigDecimal intAmtRoundingAmtDr;
    @Size(max = 5)
    @Column(name = "INT_PAID_RPT_CODE")
    private String intPaidRptCode;
    @Size(max = 5)
    @Column(name = "INT_COLL_RPT_CODE")
    private String intCollRptCode;
    @Size(max = 5)
    @Column(name = "INT_DR_RPT_CODE")
    private String intDrRptCode;
    @Size(max = 5)
    @Column(name = "INT_CR_RPT_CODE")
    private String intCrRptCode;
    @Size(max = 5)
    @Column(name = "TAX_COLL_RPT_CODE")
    private String taxCollRptCode;
    @Size(max = 12)
    @Column(name = "TAX_COLL_ACCOUNT_PLACEHOLDER")
    private String taxCollAccountPlaceholder;
    @Size(max = 1)
    @Column(name = "TAX_FLAG")
    private String taxFlag;
    @Column(name = "INCLUDE_FLOOR_LIMIT_FOR_TAX")
    private BigDecimal includeFloorLimitForTax;
    @Size(max = 1)
    @Column(name = "PROPORTIONATE_FLOOR_LIMIT")
    private String proportionateFloorLimit;
    @Column(name = "WITHOUT_TAX_FLR_LIM")
    private BigDecimal withoutTaxFlrLim;
    @Column(name = "WITHHOLDING_TAX")
    private BigDecimal withholdingTax;
    @Size(max = 1)
    @Column(name = "WTAX_MATURITY_ADJ_REQD")
    private String wtaxMaturityAdjReqd;
    @Size(max = 1)
    @Column(name = "END_OF_DAY_BAL_CHECK")
    private String endOfDayBalCheck;
    @Column(name = "END_OF_DAY_MIN_BALANCE")
    private BigDecimal endOfDayMinBalance;
    @Size(max = 1)
    @Column(name = "DRCR_IND")
    private String drcrInd;
    @Column(name = "END_OF_DAY_MAX_BALANCE")
    private BigDecimal endOfDayMaxBalance;
    @Size(max = 1)
    @Column(name = "DRCR_IND1")
    private String drcrInd1;
    @Size(max = 3)
    @Column(name = "EOD_MIN_BALANCE_EXCEPTION_CODE")
    private String eodMinBalanceExceptionCode;
    @Size(max = 3)
    @Column(name = "EOD_MAX_BALANCE_EXCEPTION_CODE")
    private String eodMaxBalanceExceptionCode;
    @Size(max = 3)
    @Column(name = "CASH_LIMIT_DR")
    private String cashLimitDr;
    @Size(max = 3)
    @Column(name = "CLG_LIMIT_DR")
    private String clgLimitDr;
    @Size(max = 3)
    @Column(name = "TRANSFER_LIMIT_DR")
    private String transferLimitDr;
    @Size(max = 3)
    @Column(name = "CASH_LIMIT_CR")
    private String cashLimitCr;
    @Size(max = 3)
    @Column(name = "CLG_LIMIT_CR")
    private String clgLimitCr;
    @Size(max = 3)
    @Column(name = "TRANSFER_LIMIT_CR")
    private String transferLimitCr;
    @Size(max = 3)
    @Column(name = "LATE_CASH_TRAN")
    private String lateCashTran;
    @Size(max = 3)
    @Column(name = "TRAN_AMT_LIMIT")
    private String tranAmtLimit;
    @Size(max = 3)
    @Column(name = "DR_TRAN_AMT_NOT_ALLOWED")
    private String drTranAmtNotAllowed;
    @Size(max = 3)
    @Column(name = "CR_TRAN_AMT_NOT_ALLOWED")
    private String crTranAmtNotAllowed;
    @Size(max = 20)
    @Column(name = "MIN_BALANCE_EVENT")
    private String minBalanceEvent;
    @Column(name = "ACCT_MIN_BALANCE")
    private BigDecimal acctMinBalance;
    @Column(name = "SWEEP_MIN_BALANCE")
    private BigDecimal sweepMinBalance;
    @Column(name = "MIN_CHARGE_PERIOD")
    private BigDecimal minChargePeriod;
    @Column(name = "PENAL_CHARGE_PERIOD")
    private BigDecimal penalChargePeriod;
    @Column(name = "MIN_BALANCE_SERVICE_CHRG")
    private BigDecimal minBalanceServiceChrg;
    @Size(max = 20)
    @Column(name = "REGULAR_INT_CERTIFICATE_PRINTING_EVENT")
    private String regularIntCertificatePrintingEvent;
    @Size(max = 20)
    @Column(name = "ADHOC_INT_CERTIFICATE_PRINTING_EVENT")
    private String adhocIntCertificatePrintingEvent;

    public CbsSchemeCurrencyDetails() {
    }

    public CbsSchemeCurrencyDetails(CbsSchemeCurrencyDetailsPK cbsSchemeCurrencyDetailsPK) {
        this.cbsSchemeCurrencyDetailsPK = cbsSchemeCurrencyDetailsPK;
    }

    public CbsSchemeCurrencyDetails(String schemeCode, String currencyCode) {
        this.cbsSchemeCurrencyDetailsPK = new CbsSchemeCurrencyDetailsPK(schemeCode, currencyCode);
    }

    public CbsSchemeCurrencyDetailsPK getCbsSchemeCurrencyDetailsPK() {
        return cbsSchemeCurrencyDetailsPK;
    }

    public void setCbsSchemeCurrencyDetailsPK(CbsSchemeCurrencyDetailsPK cbsSchemeCurrencyDetailsPK) {
        this.cbsSchemeCurrencyDetailsPK = cbsSchemeCurrencyDetailsPK;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getAcctReportCode() {
        return acctReportCode;
    }

    public void setAcctReportCode(String acctReportCode) {
        this.acctReportCode = acctReportCode;
    }

    public String getDefaultNationalRate() {
        return defaultNationalRate;
    }

    public void setDefaultNationalRate(String defaultNationalRate) {
        this.defaultNationalRate = defaultNationalRate;
    }

    public String getRevaluationAcctPlaceholder() {
        return revaluationAcctPlaceholder;
    }

    public void setRevaluationAcctPlaceholder(String revaluationAcctPlaceholder) {
        this.revaluationAcctPlaceholder = revaluationAcctPlaceholder;
    }

    public String getCrIntRateCode() {
        return crIntRateCode;
    }

    public void setCrIntRateCode(String crIntRateCode) {
        this.crIntRateCode = crIntRateCode;
    }

    public String getDrIntRateCode() {
        return drIntRateCode;
    }

    public void setDrIntRateCode(String drIntRateCode) {
        this.drIntRateCode = drIntRateCode;
    }

    public String getDefaultCustPreferentialToAcct() {
        return defaultCustPreferentialToAcct;
    }

    public void setDefaultCustPreferentialToAcct(String defaultCustPreferentialToAcct) {
        this.defaultCustPreferentialToAcct = defaultCustPreferentialToAcct;
    }

    public String getAcctOpenEvent() {
        return acctOpenEvent;
    }

    public void setAcctOpenEvent(String acctOpenEvent) {
        this.acctOpenEvent = acctOpenEvent;
    }

    public String getAcctMatchEvent() {
        return acctMatchEvent;
    }

    public void setAcctMatchEvent(String acctMatchEvent) {
        this.acctMatchEvent = acctMatchEvent;
    }

    public String getAcctClosureEvent() {
        return acctClosureEvent;
    }

    public void setAcctClosureEvent(String acctClosureEvent) {
        this.acctClosureEvent = acctClosureEvent;
    }

    public String getLedgerFolioCalcEvent() {
        return ledgerFolioCalcEvent;
    }

    public void setLedgerFolioCalcEvent(String ledgerFolioCalcEvent) {
        this.ledgerFolioCalcEvent = ledgerFolioCalcEvent;
    }

    public String getAdhocPassSheetPrintEvent() {
        return adhocPassSheetPrintEvent;
    }

    public void setAdhocPassSheetPrintEvent(String adhocPassSheetPrintEvent) {
        this.adhocPassSheetPrintEvent = adhocPassSheetPrintEvent;
    }

    public String getRegPassSheetPrintEvent() {
        return regPassSheetPrintEvent;
    }

    public void setRegPassSheetPrintEvent(String regPassSheetPrintEvent) {
        this.regPassSheetPrintEvent = regPassSheetPrintEvent;
    }

    public String getCommitmentEvent() {
        return commitmentEvent;
    }

    public void setCommitmentEvent(String commitmentEvent) {
        this.commitmentEvent = commitmentEvent;
    }

    public BigDecimal getCrCashLimit() {
        return crCashLimit;
    }

    public void setCrCashLimit(BigDecimal crCashLimit) {
        this.crCashLimit = crCashLimit;
    }

    public BigDecimal getDrCashLimit() {
        return drCashLimit;
    }

    public void setDrCashLimit(BigDecimal drCashLimit) {
        this.drCashLimit = drCashLimit;
    }

    public BigDecimal getCrClgLimit() {
        return crClgLimit;
    }

    public void setCrClgLimit(BigDecimal crClgLimit) {
        this.crClgLimit = crClgLimit;
    }

    public BigDecimal getDrClgLimit() {
        return drClgLimit;
    }

    public void setDrClgLimit(BigDecimal drClgLimit) {
        this.drClgLimit = drClgLimit;
    }

    public BigDecimal getCrXrefLimit() {
        return crXrefLimit;
    }

    public void setCrXrefLimit(BigDecimal crXrefLimit) {
        this.crXrefLimit = crXrefLimit;
    }

    public BigDecimal getDrXrefLimit() {
        return drXrefLimit;
    }

    public void setDrXrefLimit(BigDecimal drXrefLimit) {
        this.drXrefLimit = drXrefLimit;
    }

    public BigDecimal getDrCashAbnLimit() {
        return drCashAbnLimit;
    }

    public void setDrCashAbnLimit(BigDecimal drCashAbnLimit) {
        this.drCashAbnLimit = drCashAbnLimit;
    }

    public BigDecimal getDrClgAbnLimit() {
        return drClgAbnLimit;
    }

    public void setDrClgAbnLimit(BigDecimal drClgAbnLimit) {
        this.drClgAbnLimit = drClgAbnLimit;
    }

    public BigDecimal getDrxRefAbnLimit() {
        return drxRefAbnLimit;
    }

    public void setDrxRefAbnLimit(BigDecimal drxRefAbnLimit) {
        this.drxRefAbnLimit = drxRefAbnLimit;
    }

    public BigDecimal getNewAccountAbnTranAmt() {
        return newAccountAbnTranAmt;
    }

    public void setNewAccountAbnTranAmt(BigDecimal newAccountAbnTranAmt) {
        this.newAccountAbnTranAmt = newAccountAbnTranAmt;
    }

    public String getInterestTableCode() {
        return interestTableCode;
    }

    public void setInterestTableCode(String interestTableCode) {
        this.interestTableCode = interestTableCode;
    }

    public BigDecimal getCrMin() {
        return crMin;
    }

    public void setCrMin(BigDecimal crMin) {
        this.crMin = crMin;
    }

    public BigDecimal getCrMax() {
        return crMax;
    }

    public void setCrMax(BigDecimal crMax) {
        this.crMax = crMax;
    }

    public BigDecimal getDrMin() {
        return drMin;
    }

    public void setDrMin(BigDecimal drMin) {
        this.drMin = drMin;
    }

    public BigDecimal getDrMax() {
        return drMax;
    }

    public void setDrMax(BigDecimal drMax) {
        this.drMax = drMax;
    }

    public String getCrDaysInAYear() {
        return crDaysInAYear;
    }

    public void setCrDaysInAYear(String crDaysInAYear) {
        this.crDaysInAYear = crDaysInAYear;
    }

    public String getCrLeapYearAdjustment() {
        return crLeapYearAdjustment;
    }

    public void setCrLeapYearAdjustment(String crLeapYearAdjustment) {
        this.crLeapYearAdjustment = crLeapYearAdjustment;
    }

    public String getDrDaysinaYear() {
        return drDaysinaYear;
    }

    public void setDrDaysinaYear(String drDaysinaYear) {
        this.drDaysinaYear = drDaysinaYear;
    }

    public String getDrLeapYearAdjustment() {
        return drLeapYearAdjustment;
    }

    public void setDrLeapYearAdjustment(String drLeapYearAdjustment) {
        this.drLeapYearAdjustment = drLeapYearAdjustment;
    }

    public BigDecimal getMinIntPaidAmt() {
        return minIntPaidAmt;
    }

    public void setMinIntPaidAmt(BigDecimal minIntPaidAmt) {
        this.minIntPaidAmt = minIntPaidAmt;
    }

    public BigDecimal getMinIntCollAmt() {
        return minIntCollAmt;
    }

    public void setMinIntCollAmt(BigDecimal minIntCollAmt) {
        this.minIntCollAmt = minIntCollAmt;
    }

    public String getBookAdvanceInt() {
        return bookAdvanceInt;
    }

    public void setBookAdvanceInt(String bookAdvanceInt) {
        this.bookAdvanceInt = bookAdvanceInt;
    }

    public String getMicrChrgEvent() {
        return micrChrgEvent;
    }

    public void setMicrChrgEvent(String micrChrgEvent) {
        this.micrChrgEvent = micrChrgEvent;
    }

    public BigDecimal getResidualIntAdjustmentAmountForBooking() {
        return residualIntAdjustmentAmountForBooking;
    }

    public void setResidualIntAdjustmentAmountForBooking(BigDecimal residualIntAdjustmentAmountForBooking) {
        this.residualIntAdjustmentAmountForBooking = residualIntAdjustmentAmountForBooking;
    }

    public String getWithoutTaxAmountRndSt() {
        return withoutTaxAmountRndSt;
    }

    public void setWithoutTaxAmountRndSt(String withoutTaxAmountRndSt) {
        this.withoutTaxAmountRndSt = withoutTaxAmountRndSt;
    }

    public BigDecimal getWithoutTaxAmountRndAmount() {
        return withoutTaxAmountRndAmount;
    }

    public void setWithoutTaxAmountRndAmount(BigDecimal withoutTaxAmountRndAmount) {
        this.withoutTaxAmountRndAmount = withoutTaxAmountRndAmount;
    }

    public String getIntPrdRndStCr() {
        return intPrdRndStCr;
    }

    public void setIntPrdRndStCr(String intPrdRndStCr) {
        this.intPrdRndStCr = intPrdRndStCr;
    }

    public BigDecimal getIntPrdRndAmountCr() {
        return intPrdRndAmountCr;
    }

    public void setIntPrdRndAmountCr(BigDecimal intPrdRndAmountCr) {
        this.intPrdRndAmountCr = intPrdRndAmountCr;
    }

    public String getIntPrdRoundingStartDr() {
        return intPrdRoundingStartDr;
    }

    public void setIntPrdRoundingStartDr(String intPrdRoundingStartDr) {
        this.intPrdRoundingStartDr = intPrdRoundingStartDr;
    }

    public BigDecimal getIntPrdRoundingAmtDr() {
        return intPrdRoundingAmtDr;
    }

    public void setIntPrdRoundingAmtDr(BigDecimal intPrdRoundingAmtDr) {
        this.intPrdRoundingAmtDr = intPrdRoundingAmtDr;
    }

    public String getIntAmtRoundingStartCr() {
        return intAmtRoundingStartCr;
    }

    public void setIntAmtRoundingStartCr(String intAmtRoundingStartCr) {
        this.intAmtRoundingStartCr = intAmtRoundingStartCr;
    }

    public BigDecimal getIntAmtRoundingAmtCr() {
        return intAmtRoundingAmtCr;
    }

    public void setIntAmtRoundingAmtCr(BigDecimal intAmtRoundingAmtCr) {
        this.intAmtRoundingAmtCr = intAmtRoundingAmtCr;
    }

    public String getIntAmtRoundingStartDr() {
        return intAmtRoundingStartDr;
    }

    public void setIntAmtRoundingStartDr(String intAmtRoundingStartDr) {
        this.intAmtRoundingStartDr = intAmtRoundingStartDr;
    }

    public BigDecimal getIntAmtRoundingAmtDr() {
        return intAmtRoundingAmtDr;
    }

    public void setIntAmtRoundingAmtDr(BigDecimal intAmtRoundingAmtDr) {
        this.intAmtRoundingAmtDr = intAmtRoundingAmtDr;
    }

    public String getIntPaidRptCode() {
        return intPaidRptCode;
    }

    public void setIntPaidRptCode(String intPaidRptCode) {
        this.intPaidRptCode = intPaidRptCode;
    }

    public String getIntCollRptCode() {
        return intCollRptCode;
    }

    public void setIntCollRptCode(String intCollRptCode) {
        this.intCollRptCode = intCollRptCode;
    }

    public String getIntDrRptCode() {
        return intDrRptCode;
    }

    public void setIntDrRptCode(String intDrRptCode) {
        this.intDrRptCode = intDrRptCode;
    }

    public String getIntCrRptCode() {
        return intCrRptCode;
    }

    public void setIntCrRptCode(String intCrRptCode) {
        this.intCrRptCode = intCrRptCode;
    }

    public String getTaxCollRptCode() {
        return taxCollRptCode;
    }

    public void setTaxCollRptCode(String taxCollRptCode) {
        this.taxCollRptCode = taxCollRptCode;
    }

    public String getTaxCollAccountPlaceholder() {
        return taxCollAccountPlaceholder;
    }

    public void setTaxCollAccountPlaceholder(String taxCollAccountPlaceholder) {
        this.taxCollAccountPlaceholder = taxCollAccountPlaceholder;
    }

    public String getTaxFlag() {
        return taxFlag;
    }

    public void setTaxFlag(String taxFlag) {
        this.taxFlag = taxFlag;
    }

    public BigDecimal getIncludeFloorLimitForTax() {
        return includeFloorLimitForTax;
    }

    public void setIncludeFloorLimitForTax(BigDecimal includeFloorLimitForTax) {
        this.includeFloorLimitForTax = includeFloorLimitForTax;
    }

    public String getProportionateFloorLimit() {
        return proportionateFloorLimit;
    }

    public void setProportionateFloorLimit(String proportionateFloorLimit) {
        this.proportionateFloorLimit = proportionateFloorLimit;
    }

    public BigDecimal getWithoutTaxFlrLim() {
        return withoutTaxFlrLim;
    }

    public void setWithoutTaxFlrLim(BigDecimal withoutTaxFlrLim) {
        this.withoutTaxFlrLim = withoutTaxFlrLim;
    }

    public BigDecimal getWithholdingTax() {
        return withholdingTax;
    }

    public void setWithholdingTax(BigDecimal withholdingTax) {
        this.withholdingTax = withholdingTax;
    }

    public String getWtaxMaturityAdjReqd() {
        return wtaxMaturityAdjReqd;
    }

    public void setWtaxMaturityAdjReqd(String wtaxMaturityAdjReqd) {
        this.wtaxMaturityAdjReqd = wtaxMaturityAdjReqd;
    }

    public String getEndOfDayBalCheck() {
        return endOfDayBalCheck;
    }

    public void setEndOfDayBalCheck(String endOfDayBalCheck) {
        this.endOfDayBalCheck = endOfDayBalCheck;
    }

    public BigDecimal getEndOfDayMinBalance() {
        return endOfDayMinBalance;
    }

    public void setEndOfDayMinBalance(BigDecimal endOfDayMinBalance) {
        this.endOfDayMinBalance = endOfDayMinBalance;
    }

    public String getDrcrInd() {
        return drcrInd;
    }

    public void setDrcrInd(String drcrInd) {
        this.drcrInd = drcrInd;
    }

    public BigDecimal getEndOfDayMaxBalance() {
        return endOfDayMaxBalance;
    }

    public void setEndOfDayMaxBalance(BigDecimal endOfDayMaxBalance) {
        this.endOfDayMaxBalance = endOfDayMaxBalance;
    }

    public String getDrcrInd1() {
        return drcrInd1;
    }

    public void setDrcrInd1(String drcrInd1) {
        this.drcrInd1 = drcrInd1;
    }

    public String getEodMinBalanceExceptionCode() {
        return eodMinBalanceExceptionCode;
    }

    public void setEodMinBalanceExceptionCode(String eodMinBalanceExceptionCode) {
        this.eodMinBalanceExceptionCode = eodMinBalanceExceptionCode;
    }

    public String getEodMaxBalanceExceptionCode() {
        return eodMaxBalanceExceptionCode;
    }

    public void setEodMaxBalanceExceptionCode(String eodMaxBalanceExceptionCode) {
        this.eodMaxBalanceExceptionCode = eodMaxBalanceExceptionCode;
    }

    public String getCashLimitDr() {
        return cashLimitDr;
    }

    public void setCashLimitDr(String cashLimitDr) {
        this.cashLimitDr = cashLimitDr;
    }

    public String getClgLimitDr() {
        return clgLimitDr;
    }

    public void setClgLimitDr(String clgLimitDr) {
        this.clgLimitDr = clgLimitDr;
    }

    public String getTransferLimitDr() {
        return transferLimitDr;
    }

    public void setTransferLimitDr(String transferLimitDr) {
        this.transferLimitDr = transferLimitDr;
    }

    public String getCashLimitCr() {
        return cashLimitCr;
    }

    public void setCashLimitCr(String cashLimitCr) {
        this.cashLimitCr = cashLimitCr;
    }

    public String getClgLimitCr() {
        return clgLimitCr;
    }

    public void setClgLimitCr(String clgLimitCr) {
        this.clgLimitCr = clgLimitCr;
    }

    public String getTransferLimitCr() {
        return transferLimitCr;
    }

    public void setTransferLimitCr(String transferLimitCr) {
        this.transferLimitCr = transferLimitCr;
    }

    public String getLateCashTran() {
        return lateCashTran;
    }

    public void setLateCashTran(String lateCashTran) {
        this.lateCashTran = lateCashTran;
    }

    public String getTranAmtLimit() {
        return tranAmtLimit;
    }

    public void setTranAmtLimit(String tranAmtLimit) {
        this.tranAmtLimit = tranAmtLimit;
    }

    public String getDrTranAmtNotAllowed() {
        return drTranAmtNotAllowed;
    }

    public void setDrTranAmtNotAllowed(String drTranAmtNotAllowed) {
        this.drTranAmtNotAllowed = drTranAmtNotAllowed;
    }

    public String getCrTranAmtNotAllowed() {
        return crTranAmtNotAllowed;
    }

    public void setCrTranAmtNotAllowed(String crTranAmtNotAllowed) {
        this.crTranAmtNotAllowed = crTranAmtNotAllowed;
    }

    public String getMinBalanceEvent() {
        return minBalanceEvent;
    }

    public void setMinBalanceEvent(String minBalanceEvent) {
        this.minBalanceEvent = minBalanceEvent;
    }

    public BigDecimal getAcctMinBalance() {
        return acctMinBalance;
    }

    public void setAcctMinBalance(BigDecimal acctMinBalance) {
        this.acctMinBalance = acctMinBalance;
    }

    public BigDecimal getSweepMinBalance() {
        return sweepMinBalance;
    }

    public void setSweepMinBalance(BigDecimal sweepMinBalance) {
        this.sweepMinBalance = sweepMinBalance;
    }

    public BigDecimal getMinChargePeriod() {
        return minChargePeriod;
    }

    public void setMinChargePeriod(BigDecimal minChargePeriod) {
        this.minChargePeriod = minChargePeriod;
    }

    public BigDecimal getPenalChargePeriod() {
        return penalChargePeriod;
    }

    public void setPenalChargePeriod(BigDecimal penalChargePeriod) {
        this.penalChargePeriod = penalChargePeriod;
    }

    public BigDecimal getMinBalanceServiceChrg() {
        return minBalanceServiceChrg;
    }

    public void setMinBalanceServiceChrg(BigDecimal minBalanceServiceChrg) {
        this.minBalanceServiceChrg = minBalanceServiceChrg;
    }

    public String getRegularIntCertificatePrintingEvent() {
        return regularIntCertificatePrintingEvent;
    }

    public void setRegularIntCertificatePrintingEvent(String regularIntCertificatePrintingEvent) {
        this.regularIntCertificatePrintingEvent = regularIntCertificatePrintingEvent;
    }

    public String getAdhocIntCertificatePrintingEvent() {
        return adhocIntCertificatePrintingEvent;
    }

    public void setAdhocIntCertificatePrintingEvent(String adhocIntCertificatePrintingEvent) {
        this.adhocIntCertificatePrintingEvent = adhocIntCertificatePrintingEvent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbsSchemeCurrencyDetailsPK != null ? cbsSchemeCurrencyDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsSchemeCurrencyDetails)) {
            return false;
        }
        CbsSchemeCurrencyDetails other = (CbsSchemeCurrencyDetails) object;
        if ((this.cbsSchemeCurrencyDetailsPK == null && other.cbsSchemeCurrencyDetailsPK != null) || (this.cbsSchemeCurrencyDetailsPK != null && !this.cbsSchemeCurrencyDetailsPK.equals(other.cbsSchemeCurrencyDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeCurrencyDetails[ cbsSchemeCurrencyDetailsPK=" + cbsSchemeCurrencyDetailsPK + " ]";
    }
    
}
