/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsLoanInterestCodeMasterTO;
import com.cbs.dto.loan.CbsSchemeCurrencyDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables For cd.jsp file
    private String acctReportCode;
    private String defaultNationalRate;
    private String revaluationAcctPlaceholder;
    private String crIntRateCode;
    private String drIntRateCode;
    private String defaultCustPreferentialToAcct;
    private String acctOpenEvent;
    private String acctMatchEvent;
    private String acctClosureEvent;
    private String ledgerFolioCalcEvent;
    private String adhocPassSheetPrintEvent;
    private String regPassSheetPrintEvent;
    private String commitmentEvent;
    private String crCashLimit;
    private String drCashLimit;
    private String crClgLimit;
    private String drClgLimit;
    private String crXrefLimit;
    private String drXrefLimit;
    private String drCashAbnLimit;
    private String drClgAbnLimit;
    private String drXrefAbnLimit;
    private String newAccountAbnTranAmt;
    private String interestTableCode1;
    private String crMin;
    private String crMax;
    private String drMin;
    private String drMax;
    private String crDaysInaYear;
    private String drDaysinaYear;
    private String crLeapYearAdjustment;
    private String drLeapYearAdjustment;
    private String minIntPaidAmt;
    private String minIntCollAmt;
    private String bookAdvanceInt;
    private String mICRChrgEvent;
    private String residualIntAdjustmentAmountforBooking;
    private String withoutTaxAmountRndSt;
    private String withoutTaxAmountRndAmount;
    private String intPrdRndStCr;
    private String intPrdRndAmountCr;
    private String intPrdRoundingStartDr;
    private String intPrdRoundingAmtDr;
    private String intAmRoundingStartCr;
    private String intAmtRoundingAmtCr;
    private String intAmtRoundingStartDr;
    private String intAmtRoundingAmtDr;
    private String intPaidRptCode;
    private String intCollRptCode;
    private String intDrRptCode;
    private String intCrRptCode;
    private String taxCollRptCode;
    private String taxCollAccountPlaceholder;
    private String taxFlag;
    private String includeFloorLimitForTax;
    private String proportionateFloorLimit;
    private String withoutTaxFlrLim;
    private String withHoldingTax;
    private String wTAXMaturityAdjReqd;
    private String endofDayBalCheck;
    private String endOfDayMinBalance;
    private String drCrInd;
    private String endOfDayMaxBalance;
    private String drCrInd1;
    private String eODMinBalanceExceptionCode;
    private String eODMaxBalanceExceptionCode;
    private String cashLimitDr;
    private String cashLimitCr;
    private String clgLimitDr;
    private String clgLimitCr;
    private String transferLimitDr;
    private String transferLimitCr;
    private String lateCashTran;
    private String tranAmtLimit;
    private String drTranAmtNotAllowed;
    private String crTranAmtNotAllowed;
    private String minBalanceEvent;
    private String acctMinBalance;
    private String sweepMinBalance;
    private String minChargePeriod;
    private String penalChargePeriod;
    private String minBalanceServiceChrg;
    private String regularIntCertificatePrintingEvent;
    private String adhocIntCertificatePrintingEvent;
    private String stCashLimitDr;
    private String stCashLimitCr;
    private String stClgLimitDr;
    private String stClgLimitCr;
    private String stTransferLimitDr;
    private String stTransferLimitCr;
    private String stLateCashTran;
    private String stTranAmtLimit;
    private String stDrTranAmtNotAllowed;
    private String stCrTranAmtNotAllowed;
    private boolean cdFlag;
    private List<SelectItem> interestTableCodeList;
    private List<SelectItem> ddDefaultNationalRate;
    private List<SelectItem> ddInterestTableCode;
    private List<SelectItem> ddWithoutTaxAmount;
    private List<SelectItem> ddTaxglag;
    private List<SelectItem> ddCDTrnRefNo;

    //Getter-Setter for cd.jsp file
    public String getAcctClosureEvent() {
        return acctClosureEvent;
    }

    public void setAcctClosureEvent(String acctClosureEvent) {
        this.acctClosureEvent = acctClosureEvent;
    }

    public String getAcctMatchEvent() {
        return acctMatchEvent;
    }

    public void setAcctMatchEvent(String acctMatchEvent) {
        this.acctMatchEvent = acctMatchEvent;
    }

    public String getAcctMinBalance() {
        return acctMinBalance;
    }

    public void setAcctMinBalance(String acctMinBalance) {
        this.acctMinBalance = acctMinBalance;
    }

    public String getAcctOpenEvent() {
        return acctOpenEvent;
    }

    public void setAcctOpenEvent(String acctOpenEvent) {
        this.acctOpenEvent = acctOpenEvent;
    }

    public String getAcctReportCode() {
        return acctReportCode;
    }

    public void setAcctReportCode(String acctReportCode) {
        this.acctReportCode = acctReportCode;
    }

    public String getAdhocIntCertificatePrintingEvent() {
        return adhocIntCertificatePrintingEvent;
    }

    public void setAdhocIntCertificatePrintingEvent(String adhocIntCertificatePrintingEvent) {
        this.adhocIntCertificatePrintingEvent = adhocIntCertificatePrintingEvent;
    }

    public String getAdhocPassSheetPrintEvent() {
        return adhocPassSheetPrintEvent;
    }

    public void setAdhocPassSheetPrintEvent(String adhocPassSheetPrintEvent) {
        this.adhocPassSheetPrintEvent = adhocPassSheetPrintEvent;
    }

    public String getBookAdvanceInt() {
        return bookAdvanceInt;
    }

    public void setBookAdvanceInt(String bookAdvanceInt) {
        this.bookAdvanceInt = bookAdvanceInt;
    }

    public String getCashLimitCr() {
        return cashLimitCr;
    }

    public void setCashLimitCr(String cashLimitCr) {
        this.cashLimitCr = cashLimitCr;
    }

    public String getCashLimitDr() {
        return cashLimitDr;
    }

    public void setCashLimitDr(String cashLimitDr) {
        this.cashLimitDr = cashLimitDr;
    }

    public String getClgLimitCr() {
        return clgLimitCr;
    }

    public void setClgLimitCr(String clgLimitCr) {
        this.clgLimitCr = clgLimitCr;
    }

    public String getClgLimitDr() {
        return clgLimitDr;
    }

    public void setClgLimitDr(String clgLimitDr) {
        this.clgLimitDr = clgLimitDr;
    }

    public String getCommitmentEvent() {
        return commitmentEvent;
    }

    public void setCommitmentEvent(String commitmentEvent) {
        this.commitmentEvent = commitmentEvent;
    }

    public String getCrCashLimit() {
        return crCashLimit;
    }

    public void setCrCashLimit(String crCashLimit) {
        this.crCashLimit = crCashLimit;
    }

    public String getCrClgLimit() {
        return crClgLimit;
    }

    public void setCrClgLimit(String crClgLimit) {
        this.crClgLimit = crClgLimit;
    }

    public String getCrDaysInaYear() {
        return crDaysInaYear;
    }

    public void setCrDaysInaYear(String crDaysInaYear) {
        this.crDaysInaYear = crDaysInaYear;
    }

    public String getCrIntRateCode() {
        return crIntRateCode;
    }

    public void setCrIntRateCode(String crIntRateCode) {
        this.crIntRateCode = crIntRateCode;
    }

    public String getCrLeapYearAdjustment() {
        return crLeapYearAdjustment;
    }

    public void setCrLeapYearAdjustment(String crLeapYearAdjustment) {
        this.crLeapYearAdjustment = crLeapYearAdjustment;
    }

    public String getCrMax() {
        return crMax;
    }

    public void setCrMax(String crMax) {
        this.crMax = crMax;
    }

    public String getCrMin() {
        return crMin;
    }

    public void setCrMin(String crMin) {
        this.crMin = crMin;
    }

    public String getCrTranAmtNotAllowed() {
        return crTranAmtNotAllowed;
    }

    public void setCrTranAmtNotAllowed(String crTranAmtNotAllowed) {
        this.crTranAmtNotAllowed = crTranAmtNotAllowed;
    }

    public String getCrXrefLimit() {
        return crXrefLimit;
    }

    public void setCrXrefLimit(String crXrefLimit) {
        this.crXrefLimit = crXrefLimit;
    }

    public List<SelectItem> getDdDefaultNationalRate() {
        return ddDefaultNationalRate;
    }

    public void setDdDefaultNationalRate(List<SelectItem> ddDefaultNationalRate) {
        this.ddDefaultNationalRate = ddDefaultNationalRate;
    }

    public List<SelectItem> getDdInterestTableCode() {
        return ddInterestTableCode;
    }

    public void setDdInterestTableCode(List<SelectItem> ddInterestTableCode) {
        this.ddInterestTableCode = ddInterestTableCode;
    }

    public List<SelectItem> getDdTaxglag() {
        return ddTaxglag;
    }

    public void setDdTaxglag(List<SelectItem> ddTaxglag) {
        this.ddTaxglag = ddTaxglag;
    }

    public List<SelectItem> getDdWithoutTaxAmount() {
        return ddWithoutTaxAmount;
    }

    public void setDdWithoutTaxAmount(List<SelectItem> ddWithoutTaxAmount) {
        this.ddWithoutTaxAmount = ddWithoutTaxAmount;
    }

    public String getDefaultCustPreferentialToAcct() {
        return defaultCustPreferentialToAcct;
    }

    public void setDefaultCustPreferentialToAcct(String defaultCustPreferentialToAcct) {
        this.defaultCustPreferentialToAcct = defaultCustPreferentialToAcct;
    }

    public String getDefaultNationalRate() {
        return defaultNationalRate;
    }

    public void setDefaultNationalRate(String defaultNationalRate) {
        this.defaultNationalRate = defaultNationalRate;
    }

    public String getDrCashAbnLimit() {
        return drCashAbnLimit;
    }

    public void setDrCashAbnLimit(String drCashAbnLimit) {
        this.drCashAbnLimit = drCashAbnLimit;
    }

    public String getDrCashLimit() {
        return drCashLimit;
    }

    public void setDrCashLimit(String drCashLimit) {
        this.drCashLimit = drCashLimit;
    }

    public String getDrClgAbnLimit() {
        return drClgAbnLimit;
    }

    public void setDrClgAbnLimit(String drClgAbnLimit) {
        this.drClgAbnLimit = drClgAbnLimit;
    }

    public String getDrClgLimit() {
        return drClgLimit;
    }

    public void setDrClgLimit(String drClgLimit) {
        this.drClgLimit = drClgLimit;
    }

    public String getDrCrInd() {
        return drCrInd;
    }

    public void setDrCrInd(String drCrInd) {
        this.drCrInd = drCrInd;
    }

    public String getDrCrInd1() {
        return drCrInd1;
    }

    public void setDrCrInd1(String drCrInd1) {
        this.drCrInd1 = drCrInd1;
    }

    public String getDrDaysinaYear() {
        return drDaysinaYear;
    }

    public void setDrDaysinaYear(String drDaysinaYear) {
        this.drDaysinaYear = drDaysinaYear;
    }

    public String getDrIntRateCode() {
        return drIntRateCode;
    }

    public void setDrIntRateCode(String drIntRateCode) {
        this.drIntRateCode = drIntRateCode;
    }

    public String getDrLeapYearAdjustment() {
        return drLeapYearAdjustment;
    }

    public void setDrLeapYearAdjustment(String drLeapYearAdjustment) {
        this.drLeapYearAdjustment = drLeapYearAdjustment;
    }

    public String getDrMax() {
        return drMax;
    }

    public void setDrMax(String drMax) {
        this.drMax = drMax;
    }

    public String getDrMin() {
        return drMin;
    }

    public void setDrMin(String drMin) {
        this.drMin = drMin;
    }

    public String getDrTranAmtNotAllowed() {
        return drTranAmtNotAllowed;
    }

    public void setDrTranAmtNotAllowed(String drTranAmtNotAllowed) {
        this.drTranAmtNotAllowed = drTranAmtNotAllowed;
    }

    public String getDrXrefAbnLimit() {
        return drXrefAbnLimit;
    }

    public void setDrXrefAbnLimit(String drXrefAbnLimit) {
        this.drXrefAbnLimit = drXrefAbnLimit;
    }

    public String getDrXrefLimit() {
        return drXrefLimit;
    }

    public void setDrXrefLimit(String drXrefLimit) {
        this.drXrefLimit = drXrefLimit;
    }

    public String geteODMaxBalanceExceptionCode() {
        return eODMaxBalanceExceptionCode;
    }

    public void seteODMaxBalanceExceptionCode(String eODMaxBalanceExceptionCode) {
        this.eODMaxBalanceExceptionCode = eODMaxBalanceExceptionCode;
    }

    public String geteODMinBalanceExceptionCode() {
        return eODMinBalanceExceptionCode;
    }

    public void seteODMinBalanceExceptionCode(String eODMinBalanceExceptionCode) {
        this.eODMinBalanceExceptionCode = eODMinBalanceExceptionCode;
    }

    public String getEndOfDayMaxBalance() {
        return endOfDayMaxBalance;
    }

    public void setEndOfDayMaxBalance(String endOfDayMaxBalance) {
        this.endOfDayMaxBalance = endOfDayMaxBalance;
    }

    public String getEndOfDayMinBalance() {
        return endOfDayMinBalance;
    }

    public void setEndOfDayMinBalance(String endOfDayMinBalance) {
        this.endOfDayMinBalance = endOfDayMinBalance;
    }

    public String getEndofDayBalCheck() {
        return endofDayBalCheck;
    }

    public void setEndofDayBalCheck(String endofDayBalCheck) {
        this.endofDayBalCheck = endofDayBalCheck;
    }

    public String getIncludeFloorLimitForTax() {
        return includeFloorLimitForTax;
    }

    public void setIncludeFloorLimitForTax(String includeFloorLimitForTax) {
        this.includeFloorLimitForTax = includeFloorLimitForTax;
    }

    public String getIntAmRoundingStartCr() {
        return intAmRoundingStartCr;
    }

    public void setIntAmRoundingStartCr(String intAmRoundingStartCr) {
        this.intAmRoundingStartCr = intAmRoundingStartCr;
    }

    public String getIntAmtRoundingAmtCr() {
        return intAmtRoundingAmtCr;
    }

    public void setIntAmtRoundingAmtCr(String intAmtRoundingAmtCr) {
        this.intAmtRoundingAmtCr = intAmtRoundingAmtCr;
    }

    public String getIntAmtRoundingAmtDr() {
        return intAmtRoundingAmtDr;
    }

    public void setIntAmtRoundingAmtDr(String intAmtRoundingAmtDr) {
        this.intAmtRoundingAmtDr = intAmtRoundingAmtDr;
    }

    public String getIntAmtRoundingStartDr() {
        return intAmtRoundingStartDr;
    }

    public void setIntAmtRoundingStartDr(String intAmtRoundingStartDr) {
        this.intAmtRoundingStartDr = intAmtRoundingStartDr;
    }

    public String getIntCollRptCode() {
        return intCollRptCode;
    }

    public void setIntCollRptCode(String intCollRptCode) {
        this.intCollRptCode = intCollRptCode;
    }

    public String getIntCrRptCode() {
        return intCrRptCode;
    }

    public void setIntCrRptCode(String intCrRptCode) {
        this.intCrRptCode = intCrRptCode;
    }

    public String getIntDrRptCode() {
        return intDrRptCode;
    }

    public void setIntDrRptCode(String intDrRptCode) {
        this.intDrRptCode = intDrRptCode;
    }

    public String getIntPaidRptCode() {
        return intPaidRptCode;
    }

    public void setIntPaidRptCode(String intPaidRptCode) {
        this.intPaidRptCode = intPaidRptCode;
    }

    public String getIntPrdRndAmountCr() {
        return intPrdRndAmountCr;
    }

    public void setIntPrdRndAmountCr(String intPrdRndAmountCr) {
        this.intPrdRndAmountCr = intPrdRndAmountCr;
    }

    public String getIntPrdRndStCr() {
        return intPrdRndStCr;
    }

    public void setIntPrdRndStCr(String intPrdRndStCr) {
        this.intPrdRndStCr = intPrdRndStCr;
    }

    public String getIntPrdRoundingAmtDr() {
        return intPrdRoundingAmtDr;
    }

    public void setIntPrdRoundingAmtDr(String intPrdRoundingAmtDr) {
        this.intPrdRoundingAmtDr = intPrdRoundingAmtDr;
    }

    public String getIntPrdRoundingStartDr() {
        return intPrdRoundingStartDr;
    }

    public void setIntPrdRoundingStartDr(String intPrdRoundingStartDr) {
        this.intPrdRoundingStartDr = intPrdRoundingStartDr;
    }

    public String getInterestTableCode1() {
        return interestTableCode1;
    }

    public void setInterestTableCode1(String interestTableCode1) {
        this.interestTableCode1 = interestTableCode1;
    }

    public String getLateCashTran() {
        return lateCashTran;
    }

    public void setLateCashTran(String lateCashTran) {
        this.lateCashTran = lateCashTran;
    }

    public String getLedgerFolioCalcEvent() {
        return ledgerFolioCalcEvent;
    }

    public void setLedgerFolioCalcEvent(String ledgerFolioCalcEvent) {
        this.ledgerFolioCalcEvent = ledgerFolioCalcEvent;
    }

    public String getmICRChrgEvent() {
        return mICRChrgEvent;
    }

    public void setmICRChrgEvent(String mICRChrgEvent) {
        this.mICRChrgEvent = mICRChrgEvent;
    }

    public String getMinBalanceEvent() {
        return minBalanceEvent;
    }

    public void setMinBalanceEvent(String minBalanceEvent) {
        this.minBalanceEvent = minBalanceEvent;
    }

    public String getMinBalanceServiceChrg() {
        return minBalanceServiceChrg;
    }

    public void setMinBalanceServiceChrg(String minBalanceServiceChrg) {
        this.minBalanceServiceChrg = minBalanceServiceChrg;
    }

    public String getMinChargePeriod() {
        return minChargePeriod;
    }

    public void setMinChargePeriod(String minChargePeriod) {
        this.minChargePeriod = minChargePeriod;
    }

    public String getMinIntCollAmt() {
        return minIntCollAmt;
    }

    public void setMinIntCollAmt(String minIntCollAmt) {
        this.minIntCollAmt = minIntCollAmt;
    }

    public String getMinIntPaidAmt() {
        return minIntPaidAmt;
    }

    public void setMinIntPaidAmt(String minIntPaidAmt) {
        this.minIntPaidAmt = minIntPaidAmt;
    }

    public String getNewAccountAbnTranAmt() {
        return newAccountAbnTranAmt;
    }

    public void setNewAccountAbnTranAmt(String newAccountAbnTranAmt) {
        this.newAccountAbnTranAmt = newAccountAbnTranAmt;
    }

    public String getPenalChargePeriod() {
        return penalChargePeriod;
    }

    public void setPenalChargePeriod(String penalChargePeriod) {
        this.penalChargePeriod = penalChargePeriod;
    }

    public String getProportionateFloorLimit() {
        return proportionateFloorLimit;
    }

    public void setProportionateFloorLimit(String proportionateFloorLimit) {
        this.proportionateFloorLimit = proportionateFloorLimit;
    }

    public String getRegPassSheetPrintEvent() {
        return regPassSheetPrintEvent;
    }

    public void setRegPassSheetPrintEvent(String regPassSheetPrintEvent) {
        this.regPassSheetPrintEvent = regPassSheetPrintEvent;
    }

    public String getRegularIntCertificatePrintingEvent() {
        return regularIntCertificatePrintingEvent;
    }

    public void setRegularIntCertificatePrintingEvent(String regularIntCertificatePrintingEvent) {
        this.regularIntCertificatePrintingEvent = regularIntCertificatePrintingEvent;
    }

    public String getResidualIntAdjustmentAmountforBooking() {
        return residualIntAdjustmentAmountforBooking;
    }

    public void setResidualIntAdjustmentAmountforBooking(String residualIntAdjustmentAmountforBooking) {
        this.residualIntAdjustmentAmountforBooking = residualIntAdjustmentAmountforBooking;
    }

    public String getRevaluationAcctPlaceholder() {
        return revaluationAcctPlaceholder;
    }

    public void setRevaluationAcctPlaceholder(String revaluationAcctPlaceholder) {
        this.revaluationAcctPlaceholder = revaluationAcctPlaceholder;
    }

    public String getStCashLimitCr() {
        return stCashLimitCr;
    }

    public void setStCashLimitCr(String stCashLimitCr) {
        this.stCashLimitCr = stCashLimitCr;
    }

    public String getStCashLimitDr() {
        return stCashLimitDr;
    }

    public void setStCashLimitDr(String stCashLimitDr) {
        this.stCashLimitDr = stCashLimitDr;
    }

    public String getStClgLimitCr() {
        return stClgLimitCr;
    }

    public void setStClgLimitCr(String stClgLimitCr) {
        this.stClgLimitCr = stClgLimitCr;
    }

    public String getStClgLimitDr() {
        return stClgLimitDr;
    }

    public void setStClgLimitDr(String stClgLimitDr) {
        this.stClgLimitDr = stClgLimitDr;
    }

    public String getStCrTranAmtNotAllowed() {
        return stCrTranAmtNotAllowed;
    }

    public void setStCrTranAmtNotAllowed(String stCrTranAmtNotAllowed) {
        this.stCrTranAmtNotAllowed = stCrTranAmtNotAllowed;
    }

    public String getStDrTranAmtNotAllowed() {
        return stDrTranAmtNotAllowed;
    }

    public void setStDrTranAmtNotAllowed(String stDrTranAmtNotAllowed) {
        this.stDrTranAmtNotAllowed = stDrTranAmtNotAllowed;
    }

    public String getStLateCashTran() {
        return stLateCashTran;
    }

    public void setStLateCashTran(String stLateCashTran) {
        this.stLateCashTran = stLateCashTran;
    }

    public String getStTranAmtLimit() {
        return stTranAmtLimit;
    }

    public void setStTranAmtLimit(String stTranAmtLimit) {
        this.stTranAmtLimit = stTranAmtLimit;
    }

    public String getStTransferLimitCr() {
        return stTransferLimitCr;
    }

    public void setStTransferLimitCr(String stTransferLimitCr) {
        this.stTransferLimitCr = stTransferLimitCr;
    }

    public String getStTransferLimitDr() {
        return stTransferLimitDr;
    }

    public void setStTransferLimitDr(String stTransferLimitDr) {
        this.stTransferLimitDr = stTransferLimitDr;
    }

    public String getSweepMinBalance() {
        return sweepMinBalance;
    }

    public void setSweepMinBalance(String sweepMinBalance) {
        this.sweepMinBalance = sweepMinBalance;
    }

    public String getTaxCollAccountPlaceholder() {
        return taxCollAccountPlaceholder;
    }

    public void setTaxCollAccountPlaceholder(String taxCollAccountPlaceholder) {
        this.taxCollAccountPlaceholder = taxCollAccountPlaceholder;
    }

    public String getTaxCollRptCode() {
        return taxCollRptCode;
    }

    public void setTaxCollRptCode(String taxCollRptCode) {
        this.taxCollRptCode = taxCollRptCode;
    }

    public String getTaxFlag() {
        return taxFlag;
    }

    public void setTaxFlag(String taxFlag) {
        this.taxFlag = taxFlag;
    }

    public String getTranAmtLimit() {
        return tranAmtLimit;
    }

    public void setTranAmtLimit(String tranAmtLimit) {
        this.tranAmtLimit = tranAmtLimit;
    }

    public String getTransferLimitCr() {
        return transferLimitCr;
    }

    public void setTransferLimitCr(String transferLimitCr) {
        this.transferLimitCr = transferLimitCr;
    }

    public String getTransferLimitDr() {
        return transferLimitDr;
    }

    public void setTransferLimitDr(String transferLimitDr) {
        this.transferLimitDr = transferLimitDr;
    }

    public String getwTAXMaturityAdjReqd() {
        return wTAXMaturityAdjReqd;
    }

    public void setwTAXMaturityAdjReqd(String wTAXMaturityAdjReqd) {
        this.wTAXMaturityAdjReqd = wTAXMaturityAdjReqd;
    }

    public String getWithHoldingTax() {
        return withHoldingTax;
    }

    public void setWithHoldingTax(String withHoldingTax) {
        this.withHoldingTax = withHoldingTax;
    }

    public String getWithoutTaxAmountRndAmount() {
        return withoutTaxAmountRndAmount;
    }

    public void setWithoutTaxAmountRndAmount(String withoutTaxAmountRndAmount) {
        this.withoutTaxAmountRndAmount = withoutTaxAmountRndAmount;
    }

    public String getWithoutTaxAmountRndSt() {
        return withoutTaxAmountRndSt;
    }

    public void setWithoutTaxAmountRndSt(String withoutTaxAmountRndSt) {
        this.withoutTaxAmountRndSt = withoutTaxAmountRndSt;
    }

    public String getWithoutTaxFlrLim() {
        return withoutTaxFlrLim;
    }

    public void setWithoutTaxFlrLim(String withoutTaxFlrLim) {
        this.withoutTaxFlrLim = withoutTaxFlrLim;
    }

    public List<SelectItem> getInterestTableCodeList() {
        return interestTableCodeList;
    }

    public void setInterestTableCodeList(List<SelectItem> interestTableCodeList) {
        this.interestTableCodeList = interestTableCodeList;
    }

    public boolean isCdFlag() {
        return cdFlag;
    }

    public void setCdFlag(boolean cdFlag) {
        this.cdFlag = cdFlag;
    }

    public List<SelectItem> getDdCDTrnRefNo() {
        return ddCDTrnRefNo;
    }

    public void setDdCDTrnRefNo(List<SelectItem> ddCDTrnRefNo) {
        this.ddCDTrnRefNo = ddCDTrnRefNo;
    }

    /** Creates a new instance of CD */
    public CD() {

        try {
            ddCDTrnRefNo = new ArrayList<SelectItem>();
            ddCDTrnRefNo.add(new SelectItem("0", ""));
            ddCDTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddCDTrnRefNo.add(new SelectItem("N", "No"));

            ddWithoutTaxAmount = new ArrayList<SelectItem>();
            ddWithoutTaxAmount.add(new SelectItem("0", ""));
            ddWithoutTaxAmount.add(new SelectItem("N", "Nearest"));
            ddWithoutTaxAmount.add(new SelectItem("H", "Highest"));
            ddWithoutTaxAmount.add(new SelectItem("L", "Lowest"));
            ddWithoutTaxAmount.add(new SelectItem("NR", "No Rounding"));

            ddTaxglag = new ArrayList<SelectItem>();
            ddTaxglag.add(new SelectItem("0", ""));
            ddTaxglag.add(new SelectItem("W", "Account Level with holding Tax"));
            ddTaxglag.add(new SelectItem("T", "Customer TDS"));
            ddTaxglag.add(new SelectItem("E", "Customer TDS eligibility"));
            ddTaxglag.add(new SelectItem("N", "NONE"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs = schemeMasterManagementDelegate.getCurrencyCode("067");
            if (CbsRefRecTypeTOs.size() > 0) {
                ddDefaultNationalRate = new ArrayList<SelectItem>();
                ddDefaultNationalRate.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddDefaultNationalRate.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            } else {
                ddDefaultNationalRate = new ArrayList<SelectItem>();
                ddDefaultNationalRate.add(new SelectItem("0", ""));
            }

            List<CbsLoanInterestCodeMasterTO> cbsLoanInterestCodeMasterTOs = schemeMasterManagementDelegate.getDataTodRef();
            interestTableCodeList = new ArrayList<SelectItem>();
            if (cbsLoanInterestCodeMasterTOs.size() > 0) {

                for (CbsLoanInterestCodeMasterTO tosList : cbsLoanInterestCodeMasterTOs) {
                    interestTableCodeList.add(new SelectItem(tosList.getInterestCode(), tosList.getInterestCodeDescription()));
                }
            } else {
                interestTableCodeList.add(new SelectItem("0", ""));
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    //Functionality for cd.jsp file
    public void descCashLimitDr() {
        schemeMaster.setMessage("");
        try {
            if (this.cashLimitDr.equals("")) {
                this.setStCashLimitDr("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cashLimitDr);
                this.setStCashLimitDr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStCashLimitDr("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCashLimitCr() {
        schemeMaster.setMessage("");
        try {
            if (this.cashLimitCr.equals("")) {
                this.setStCashLimitCr("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cashLimitCr);
                this.setStCashLimitCr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStCashLimitCr("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClgLimitDr() {
        schemeMaster.setMessage("");
        try {
            if (this.clgLimitDr.equals("")) {
                this.setStClgLimitDr("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clgLimitDr);
                this.setStClgLimitDr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStClgLimitDr("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClgLimitCr() {
        schemeMaster.setMessage("");
        try {
            if (this.clgLimitCr.equals("")) {
                this.setStClgLimitCr("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clgLimitCr);
                this.setStClgLimitCr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStClgLimitCr("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descTransferLimitDr() {
        schemeMaster.setMessage("");
        try {
            if (this.transferLimitDr.equals("")) {
                this.setStTransferLimitDr("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.transferLimitDr);
                this.setStTransferLimitDr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStTransferLimitDr("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descTransferLimitCr() {
        schemeMaster.setMessage("");
        try {
            if (this.transferLimitCr.equals("")) {
                this.setStTransferLimitCr("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.transferLimitCr);
                this.setStTransferLimitCr(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStTransferLimitCr("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descLateCashTran() {
        schemeMaster.setMessage("");
        try {
            if (this.lateCashTran.equals("")) {
                this.setStLateCashTran("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.lateCashTran);
                this.setStLateCashTran(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStLateCashTran("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descTranAmtLimit() {
        schemeMaster.setMessage("");
        try {
            if (this.tranAmtLimit.equals("")) {
                this.setStTranAmtLimit("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.tranAmtLimit);
                this.setStTranAmtLimit(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStTranAmtLimit("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDrTranAmtNotAllowed() {
        schemeMaster.setMessage("");
        try {
            if (this.drTranAmtNotAllowed.equals("")) {
                this.setStDrTranAmtNotAllowed("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.drTranAmtNotAllowed);
                this.setStDrTranAmtNotAllowed(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStDrTranAmtNotAllowed("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descCrTranAmtNotAllowed() {
        schemeMaster.setMessage("");
        try {
            if (this.crTranAmtNotAllowed.equals("")) {
                this.setStCrTranAmtNotAllowed("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.crTranAmtNotAllowed);
                this.setStCrTranAmtNotAllowed(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                schemeMaster.setMessage("");
                this.setStCrTranAmtNotAllowed("Please fill The right code.");
                schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectCurrencyDetails() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeCurrencyDetailsTO cbsSchemeCurrencyDetailsTO = schemeMasterManagementDelegate.getSelectCurrencyDetail(schemeMaster.getSchemeCode());
            if (cbsSchemeCurrencyDetailsTO != null) {

                this.setCrCashLimit(cbsSchemeCurrencyDetailsTO.getCrCashLimit().toString());
                this.setDrCashLimit(cbsSchemeCurrencyDetailsTO.getDrCashLimit().toString());
                this.setCrClgLimit(cbsSchemeCurrencyDetailsTO.getCrClgLimit().toString());
                this.setDrClgLimit(cbsSchemeCurrencyDetailsTO.getDrClgLimit().toString());

                this.setCrXrefLimit(cbsSchemeCurrencyDetailsTO.getCrXrefLimit().toString());
                this.setDrXrefLimit(cbsSchemeCurrencyDetailsTO.getDrXrefLimit().toString());
                this.setDrCashAbnLimit(cbsSchemeCurrencyDetailsTO.getDrCashAbnLimit().toString());
                this.setDrClgAbnLimit(cbsSchemeCurrencyDetailsTO.getDrClgAbnLimit().toString());

                this.setDrXrefAbnLimit(cbsSchemeCurrencyDetailsTO.getDrxRefAbnLimit().toString());
                this.setNewAccountAbnTranAmt(cbsSchemeCurrencyDetailsTO.getNewAccountAbnTranAmt().toString());
                this.setCrMin(cbsSchemeCurrencyDetailsTO.getCrMin().toString());
                this.setCrMax(cbsSchemeCurrencyDetailsTO.getCrMax().toString());

                this.setDrMin(cbsSchemeCurrencyDetailsTO.getDrMin().toString());
                this.setDrMax(cbsSchemeCurrencyDetailsTO.getDrMax().toString());
                this.setMinIntPaidAmt(cbsSchemeCurrencyDetailsTO.getMinIntPaidAmt().toString());
                this.setMinIntCollAmt(cbsSchemeCurrencyDetailsTO.getMinIntCollAmt().toString());

                this.setResidualIntAdjustmentAmountforBooking(cbsSchemeCurrencyDetailsTO.getResidualIntAdjustmentAmountForBooking().toString());
                this.setWithoutTaxAmountRndAmount(cbsSchemeCurrencyDetailsTO.getWithoutTaxAmountRndAmount().toString());
                this.setIntPrdRndAmountCr(cbsSchemeCurrencyDetailsTO.getIntPrdRndAmountCr().toString());
                this.setIntPrdRoundingAmtDr(cbsSchemeCurrencyDetailsTO.getIntPrdRoundingAmtDr().toString());

                this.setIntAmtRoundingAmtCr(cbsSchemeCurrencyDetailsTO.getIntAmtRoundingAmtCr().toString());
                this.setIntAmtRoundingAmtDr(cbsSchemeCurrencyDetailsTO.getIntAmtRoundingAmtDr().toString());
                this.setWithoutTaxFlrLim(cbsSchemeCurrencyDetailsTO.getWithoutTaxFlrLim().toString());
                this.setWithHoldingTax(cbsSchemeCurrencyDetailsTO.getWithholdingTax().toString());

                this.setIncludeFloorLimitForTax(cbsSchemeCurrencyDetailsTO.getIncludeFloorLimitForTax().toString());
                this.setAcctMinBalance(cbsSchemeCurrencyDetailsTO.getAcctMinBalance().toString());
                this.setSweepMinBalance(cbsSchemeCurrencyDetailsTO.getSweepMinBalance().toString());
                this.setMinChargePeriod(cbsSchemeCurrencyDetailsTO.getMinChargePeriod().toString());

                this.setPenalChargePeriod(cbsSchemeCurrencyDetailsTO.getPenalChargePeriod().toString());
                this.setMinBalanceServiceChrg(cbsSchemeCurrencyDetailsTO.getMinBalanceServiceChrg().toString());
                this.setEndOfDayMinBalance(cbsSchemeCurrencyDetailsTO.getEndOfDayMinBalance().toString());
                this.setEndOfDayMaxBalance(cbsSchemeCurrencyDetailsTO.getEndOfDayMaxBalance().toString());

                if (cbsSchemeCurrencyDetailsTO.getAcctReportCode() == null || cbsSchemeCurrencyDetailsTO.getAcctReportCode().equalsIgnoreCase("")) {
                    this.setAcctReportCode("");
                } else {
                    this.setAcctReportCode(cbsSchemeCurrencyDetailsTO.getAcctReportCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getDefaultNationalRate() == null || cbsSchemeCurrencyDetailsTO.getDefaultNationalRate().equalsIgnoreCase("")) {
                    this.setDefaultNationalRate("0");
                } else {
                    this.setDefaultNationalRate(cbsSchemeCurrencyDetailsTO.getDefaultNationalRate());
                }

                if (cbsSchemeCurrencyDetailsTO.getRevaluationAcctPlaceholder() == null || cbsSchemeCurrencyDetailsTO.getRevaluationAcctPlaceholder().equalsIgnoreCase("")) {
                    this.setRevaluationAcctPlaceholder("");
                } else {
                    this.setRevaluationAcctPlaceholder(cbsSchemeCurrencyDetailsTO.getRevaluationAcctPlaceholder());
                }

                if (cbsSchemeCurrencyDetailsTO.getCrIntRateCode() == null || cbsSchemeCurrencyDetailsTO.getCrIntRateCode().equalsIgnoreCase("")) {
                    this.setCrIntRateCode("");
                } else {
                    this.setCrIntRateCode(cbsSchemeCurrencyDetailsTO.getCrIntRateCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getDrIntRateCode() == null || cbsSchemeCurrencyDetailsTO.getDrIntRateCode().equalsIgnoreCase("")) {
                    this.setDrIntRateCode("");
                } else {
                    this.setDrIntRateCode(cbsSchemeCurrencyDetailsTO.getDrIntRateCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getDefaultCustPreferentialToAcct() == null || cbsSchemeCurrencyDetailsTO.getDefaultCustPreferentialToAcct().equalsIgnoreCase("")) {
                    this.setDefaultCustPreferentialToAcct("0");
                } else {
                    this.setDefaultCustPreferentialToAcct(cbsSchemeCurrencyDetailsTO.getDefaultCustPreferentialToAcct());
                }

                if (cbsSchemeCurrencyDetailsTO.getAcctOpenEvent() == null || cbsSchemeCurrencyDetailsTO.getAcctOpenEvent().equalsIgnoreCase("")) {
                    this.setAcctOpenEvent("");
                } else {
                    this.setAcctOpenEvent(cbsSchemeCurrencyDetailsTO.getAcctOpenEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getAcctMatchEvent() == null || cbsSchemeCurrencyDetailsTO.getAcctMatchEvent().equalsIgnoreCase("")) {
                    this.setAcctMatchEvent("");
                } else {
                    this.setAcctMatchEvent(cbsSchemeCurrencyDetailsTO.getAcctMatchEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getAcctClosureEvent() == null || cbsSchemeCurrencyDetailsTO.getAcctClosureEvent().equalsIgnoreCase("")) {
                    this.setAcctClosureEvent("");
                } else {
                    this.setAcctClosureEvent(cbsSchemeCurrencyDetailsTO.getAcctClosureEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getLedgerFolioCalcEvent() == null || cbsSchemeCurrencyDetailsTO.getLedgerFolioCalcEvent().equalsIgnoreCase("")) {
                    this.setLedgerFolioCalcEvent("");
                } else {
                    this.setLedgerFolioCalcEvent(cbsSchemeCurrencyDetailsTO.getLedgerFolioCalcEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getAdhocPassSheetPrintEvent() == null || cbsSchemeCurrencyDetailsTO.getAdhocPassSheetPrintEvent().equalsIgnoreCase("")) {
                    this.setAdhocPassSheetPrintEvent("");
                } else {
                    this.setAdhocPassSheetPrintEvent(cbsSchemeCurrencyDetailsTO.getAdhocPassSheetPrintEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getRegPassSheetPrintEvent() == null || cbsSchemeCurrencyDetailsTO.getRegPassSheetPrintEvent().equalsIgnoreCase("")) {
                    this.setRegPassSheetPrintEvent("");
                } else {
                    this.setRegPassSheetPrintEvent(cbsSchemeCurrencyDetailsTO.getRegPassSheetPrintEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getCommitmentEvent() == null || cbsSchemeCurrencyDetailsTO.getCommitmentEvent().equalsIgnoreCase("")) {
                    this.setCommitmentEvent("");
                } else {
                    this.setCommitmentEvent(cbsSchemeCurrencyDetailsTO.getCommitmentEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getInterestTableCode() == null || cbsSchemeCurrencyDetailsTO.getInterestTableCode().equalsIgnoreCase("")) {
                    this.setInterestTableCode1("0");
                } else {
                    this.setInterestTableCode1(cbsSchemeCurrencyDetailsTO.getInterestTableCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getCrDaysInAYear() == null || cbsSchemeCurrencyDetailsTO.getCrDaysInAYear().equalsIgnoreCase("")) {
                    this.setCrDaysInaYear("");
                } else {
                    this.setCrDaysInaYear(cbsSchemeCurrencyDetailsTO.getCrDaysInAYear());
                }

                if (cbsSchemeCurrencyDetailsTO.getDrDaysinaYear() == null || cbsSchemeCurrencyDetailsTO.getDrDaysinaYear().equalsIgnoreCase("")) {
                    this.setDrDaysinaYear("");
                } else {
                    this.setDrDaysinaYear(cbsSchemeCurrencyDetailsTO.getDrDaysinaYear());
                }

                if (cbsSchemeCurrencyDetailsTO.getCrLeapYearAdjustment() == null || cbsSchemeCurrencyDetailsTO.getCrLeapYearAdjustment().equalsIgnoreCase("")) {
                    this.setCrLeapYearAdjustment("");
                } else {
                    this.setCrLeapYearAdjustment(cbsSchemeCurrencyDetailsTO.getCrLeapYearAdjustment());
                }

                if (cbsSchemeCurrencyDetailsTO.getDrLeapYearAdjustment() == null || cbsSchemeCurrencyDetailsTO.getDrLeapYearAdjustment().equalsIgnoreCase("")) {
                    this.setDrLeapYearAdjustment("");
                } else {
                    this.setDrLeapYearAdjustment(cbsSchemeCurrencyDetailsTO.getDrLeapYearAdjustment());
                }

                if (cbsSchemeCurrencyDetailsTO.getBookAdvanceInt() == null || cbsSchemeCurrencyDetailsTO.getBookAdvanceInt().equalsIgnoreCase("")) {
                    this.setBookAdvanceInt("0");
                } else {
                    this.setBookAdvanceInt(cbsSchemeCurrencyDetailsTO.getBookAdvanceInt());
                }

                if (cbsSchemeCurrencyDetailsTO.getMicrChrgEvent() == null || cbsSchemeCurrencyDetailsTO.getMicrChrgEvent().equalsIgnoreCase("")) {
                    this.setmICRChrgEvent("");
                } else {
                    this.setmICRChrgEvent(cbsSchemeCurrencyDetailsTO.getMicrChrgEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getWithoutTaxAmountRndSt() == null || cbsSchemeCurrencyDetailsTO.getWithoutTaxAmountRndSt().equalsIgnoreCase("")) {
                    this.setWithoutTaxAmountRndSt("0");
                } else {
                    this.setWithoutTaxAmountRndSt(cbsSchemeCurrencyDetailsTO.getWithoutTaxAmountRndSt());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntPrdRndStCr() == null || cbsSchemeCurrencyDetailsTO.getIntPrdRndStCr().equalsIgnoreCase("")) {
                    this.setIntPrdRndStCr("0");
                } else {
                    this.setIntPrdRndStCr(cbsSchemeCurrencyDetailsTO.getIntPrdRndStCr());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntPrdRoundingStartDr() == null || cbsSchemeCurrencyDetailsTO.getIntPrdRoundingStartDr().equalsIgnoreCase("")) {
                    this.setIntPrdRoundingStartDr("0");
                } else {
                    this.setIntPrdRoundingStartDr(cbsSchemeCurrencyDetailsTO.getIntPrdRoundingStartDr());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntAmtRoundingStartCr() == null || cbsSchemeCurrencyDetailsTO.getIntAmtRoundingStartCr().equalsIgnoreCase("")) {
                    this.setIntAmRoundingStartCr("0");
                } else {
                    this.setIntAmRoundingStartCr(cbsSchemeCurrencyDetailsTO.getIntAmtRoundingStartCr());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntAmtRoundingStartDr() == null || cbsSchemeCurrencyDetailsTO.getIntAmtRoundingStartDr().equalsIgnoreCase("")) {
                    this.setIntAmtRoundingStartDr("0");
                } else {
                    this.setIntAmtRoundingStartDr(cbsSchemeCurrencyDetailsTO.getIntAmtRoundingStartDr());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntPaidRptCode() == null || cbsSchemeCurrencyDetailsTO.getIntPaidRptCode().equalsIgnoreCase("")) {
                    this.setIntPaidRptCode("");
                } else {
                    this.setIntPaidRptCode(cbsSchemeCurrencyDetailsTO.getIntPaidRptCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntCollRptCode() == null || cbsSchemeCurrencyDetailsTO.getIntCollRptCode().equalsIgnoreCase("")) {
                    this.setIntCollRptCode("");
                } else {
                    this.setIntCollRptCode(cbsSchemeCurrencyDetailsTO.getIntCollRptCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntDrRptCode() == null || cbsSchemeCurrencyDetailsTO.getIntDrRptCode().equalsIgnoreCase("")) {
                    this.setIntDrRptCode("");
                } else {
                    this.setIntDrRptCode(cbsSchemeCurrencyDetailsTO.getIntDrRptCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getIntCrRptCode() == null || cbsSchemeCurrencyDetailsTO.getIntCrRptCode().equalsIgnoreCase("")) {
                    this.setIntCrRptCode("");
                } else {
                    this.setIntCrRptCode(cbsSchemeCurrencyDetailsTO.getIntCrRptCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getTaxCollRptCode() == null || cbsSchemeCurrencyDetailsTO.getTaxCollRptCode().equalsIgnoreCase("")) {
                    this.setTaxCollRptCode("");
                } else {
                    this.setTaxCollRptCode(cbsSchemeCurrencyDetailsTO.getTaxCollRptCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getTaxCollAccountPlaceholder() == null || cbsSchemeCurrencyDetailsTO.getTaxCollAccountPlaceholder().equalsIgnoreCase("")) {
                    this.setTaxCollAccountPlaceholder("");
                } else {
                    this.setTaxCollAccountPlaceholder(cbsSchemeCurrencyDetailsTO.getTaxCollAccountPlaceholder());
                }

                if (cbsSchemeCurrencyDetailsTO.getTaxFlag() == null || cbsSchemeCurrencyDetailsTO.getTaxFlag().equalsIgnoreCase("")) {
                    this.setTaxFlag("0");
                } else {
                    this.setTaxFlag(cbsSchemeCurrencyDetailsTO.getTaxFlag());
                }

                if (cbsSchemeCurrencyDetailsTO.getProportionateFloorLimit() == null || cbsSchemeCurrencyDetailsTO.getProportionateFloorLimit().equalsIgnoreCase("")) {
                    this.setProportionateFloorLimit("0");
                } else {
                    this.setProportionateFloorLimit(cbsSchemeCurrencyDetailsTO.getProportionateFloorLimit());
                }

                if (cbsSchemeCurrencyDetailsTO.getWtaxMaturityAdjReqd() == null || cbsSchemeCurrencyDetailsTO.getWtaxMaturityAdjReqd().equalsIgnoreCase("")) {
                    this.setwTAXMaturityAdjReqd("0");
                } else {
                    this.setwTAXMaturityAdjReqd(cbsSchemeCurrencyDetailsTO.getWtaxMaturityAdjReqd());
                }

                if (cbsSchemeCurrencyDetailsTO.getEndOfDayBalCheck() == null || cbsSchemeCurrencyDetailsTO.getEndOfDayBalCheck().equalsIgnoreCase("")) {
                    this.setEndofDayBalCheck("0");
                } else {
                    this.setEndofDayBalCheck(cbsSchemeCurrencyDetailsTO.getEndOfDayBalCheck());
                }

                if (cbsSchemeCurrencyDetailsTO.getDrcrInd() == null || cbsSchemeCurrencyDetailsTO.getDrcrInd().equalsIgnoreCase("")) {
                    this.setDrCrInd("0");
                } else {
                    this.setDrCrInd(cbsSchemeCurrencyDetailsTO.getDrcrInd());
                }

                if (cbsSchemeCurrencyDetailsTO.getDrcrInd1() == null || cbsSchemeCurrencyDetailsTO.getDrcrInd1().equalsIgnoreCase("")) {
                    this.setDrCrInd1("0");
                } else {
                    this.setDrCrInd1(cbsSchemeCurrencyDetailsTO.getDrcrInd1());
                }

                if (cbsSchemeCurrencyDetailsTO.getEodMinBalanceExceptionCode() == null || cbsSchemeCurrencyDetailsTO.getEodMinBalanceExceptionCode().equalsIgnoreCase("")) {
                    this.seteODMinBalanceExceptionCode("");
                } else {
                    this.seteODMinBalanceExceptionCode(cbsSchemeCurrencyDetailsTO.getEodMinBalanceExceptionCode());
                }

                if (cbsSchemeCurrencyDetailsTO.getEodMaxBalanceExceptionCode() == null || cbsSchemeCurrencyDetailsTO.getEodMaxBalanceExceptionCode().equalsIgnoreCase("")) {
                    this.seteODMaxBalanceExceptionCode("");
                } else {
                    this.seteODMaxBalanceExceptionCode(cbsSchemeCurrencyDetailsTO.getEodMaxBalanceExceptionCode());
                }

                /*Exception code*/
                if (cbsSchemeCurrencyDetailsTO.getCashLimitDr() == null || cbsSchemeCurrencyDetailsTO.getCashLimitDr().equalsIgnoreCase("")) {
                    this.setCashLimitDr("");
                } else {
                    this.setCashLimitDr(cbsSchemeCurrencyDetailsTO.getCashLimitDr());
                    descCashLimitDr();
                }

                if (cbsSchemeCurrencyDetailsTO.getClgLimitDr() == null || cbsSchemeCurrencyDetailsTO.getClgLimitDr().equalsIgnoreCase("")) {
                    this.setClgLimitDr("");
                } else {
                    this.setClgLimitDr(cbsSchemeCurrencyDetailsTO.getClgLimitDr());
                    descClgLimitDr();
                }

                if (cbsSchemeCurrencyDetailsTO.getTransferLimitDr() == null || cbsSchemeCurrencyDetailsTO.getTransferLimitDr().equalsIgnoreCase("")) {
                    this.setTransferLimitDr("");
                } else {
                    this.setTransferLimitDr(cbsSchemeCurrencyDetailsTO.getTransferLimitDr());
                    descTransferLimitDr();
                }

                if (cbsSchemeCurrencyDetailsTO.getCashLimitCr() == null || cbsSchemeCurrencyDetailsTO.getCashLimitCr().equalsIgnoreCase("")) {
                    this.setCashLimitCr("");
                } else {
                    this.setCashLimitCr(cbsSchemeCurrencyDetailsTO.getCashLimitCr());
                    descCashLimitCr();
                }

                if (cbsSchemeCurrencyDetailsTO.getClgLimitCr() == null || cbsSchemeCurrencyDetailsTO.getClgLimitCr().equalsIgnoreCase("")) {
                    this.setClgLimitCr("");
                } else {
                    this.setClgLimitCr(cbsSchemeCurrencyDetailsTO.getClgLimitCr());
                    descClgLimitCr();
                }

                if (cbsSchemeCurrencyDetailsTO.getTransferLimitCr() == null || cbsSchemeCurrencyDetailsTO.getTransferLimitCr().equalsIgnoreCase("")) {
                    this.setTransferLimitCr("");
                } else {
                    this.setTransferLimitCr(cbsSchemeCurrencyDetailsTO.getTransferLimitCr());
                    descTransferLimitCr();
                }

                if (cbsSchemeCurrencyDetailsTO.getLateCashTran() == null || cbsSchemeCurrencyDetailsTO.getLateCashTran().equalsIgnoreCase("")) {
                    this.setLateCashTran("");
                } else {
                    this.setLateCashTran(cbsSchemeCurrencyDetailsTO.getLateCashTran());
                    descLateCashTran();
                }

                if (cbsSchemeCurrencyDetailsTO.getTranAmtLimit() == null || cbsSchemeCurrencyDetailsTO.getTranAmtLimit().equalsIgnoreCase("")) {
                    this.setTranAmtLimit("");
                } else {
                    this.setTranAmtLimit(cbsSchemeCurrencyDetailsTO.getTranAmtLimit());
                    descTranAmtLimit();
                }

                if (cbsSchemeCurrencyDetailsTO.getDrTranAmtNotAllowed() == null || cbsSchemeCurrencyDetailsTO.getDrTranAmtNotAllowed().equalsIgnoreCase("")) {
                    this.setDrTranAmtNotAllowed("");
                } else {
                    this.setDrTranAmtNotAllowed(cbsSchemeCurrencyDetailsTO.getDrTranAmtNotAllowed());
                    descDrTranAmtNotAllowed();
                }

                if (cbsSchemeCurrencyDetailsTO.getCrTranAmtNotAllowed() == null || cbsSchemeCurrencyDetailsTO.getCrTranAmtNotAllowed().equalsIgnoreCase("")) {
                    this.setCrTranAmtNotAllowed("");
                } else {
                    this.setCrTranAmtNotAllowed(cbsSchemeCurrencyDetailsTO.getCrTranAmtNotAllowed());
                    descCrTranAmtNotAllowed();
                }

                /**Min Balance Details***/
                if (cbsSchemeCurrencyDetailsTO.getMinBalanceEvent() == null || cbsSchemeCurrencyDetailsTO.getMinBalanceEvent().equalsIgnoreCase("")) {
                    this.setMinBalanceEvent("");
                } else {
                    this.setMinBalanceEvent(cbsSchemeCurrencyDetailsTO.getMinBalanceEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getRegularIntCertificatePrintingEvent() == null || cbsSchemeCurrencyDetailsTO.getRegularIntCertificatePrintingEvent().equalsIgnoreCase("")) {
                    this.setRegularIntCertificatePrintingEvent("");
                } else {
                    this.setRegularIntCertificatePrintingEvent(cbsSchemeCurrencyDetailsTO.getRegularIntCertificatePrintingEvent());
                }

                if (cbsSchemeCurrencyDetailsTO.getAdhocIntCertificatePrintingEvent() == null || cbsSchemeCurrencyDetailsTO.getAdhocIntCertificatePrintingEvent().equalsIgnoreCase("")) {
                    this.setAdhocIntCertificatePrintingEvent("");
                } else {
                    this.setAdhocIntCertificatePrintingEvent(cbsSchemeCurrencyDetailsTO.getAdhocIntCertificatePrintingEvent());
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshCDForm() {
        this.setDefaultNationalRate("0");
        this.setDefaultCustPreferentialToAcct("0");
        this.setInterestTableCode1("0");
        this.setBookAdvanceInt("0");
        this.setWithoutTaxAmountRndSt("0");
        this.setIntPrdRndStCr("0");
        this.setIntPrdRoundingStartDr("0");
        this.setIntAmRoundingStartCr("0");
        this.setIntAmtRoundingStartDr("0");
        this.setTaxFlag("0");
        this.setEndofDayBalCheck("0");
        this.setProportionateFloorLimit("0");
        this.setwTAXMaturityAdjReqd("0");
        this.setDrCrInd("0");
        this.setDrCrInd1("0");
        this.setAcctReportCode("");
        this.setRevaluationAcctPlaceholder("");
        this.setCrIntRateCode("");
        this.setDrIntRateCode("");
        this.setAcctOpenEvent("");
        this.setAcctMatchEvent("");
        this.setAcctClosureEvent("");
        this.setLedgerFolioCalcEvent("");
        this.setAdhocPassSheetPrintEvent("");
        this.setRegPassSheetPrintEvent("");
        this.setCommitmentEvent("");
        this.setCrCashLimit("0.00");
        this.setDrCashLimit("0.00");
        this.setCrClgLimit("0.00");
        this.setDrClgLimit("0.00");
        this.setCrXrefLimit("0.00");
        this.setDrXrefLimit("0.00");
        this.setDrCashAbnLimit("0.00");
        this.setDrClgAbnLimit("0.00");
        this.setDrXrefAbnLimit("0.00");
        this.setNewAccountAbnTranAmt("0.00");
        this.setCrMin("0.00");
        this.setCrMax("0.00");
        this.setDrMin("0.00");
        this.setDrMax("0.00");
        this.setCrDaysInaYear("");
        this.setDrDaysinaYear("");
        this.setCrLeapYearAdjustment("");
        this.setDrLeapYearAdjustment("");
        this.setMinIntPaidAmt("0.00");
        this.setMinIntCollAmt("0.00");
        this.setmICRChrgEvent("");
        this.setResidualIntAdjustmentAmountforBooking("0.00");
        this.setWithoutTaxAmountRndAmount("0.00");
        this.setIntPrdRndAmountCr("0.00");
        this.setIntPrdRoundingAmtDr("0.00");
        this.setIntAmtRoundingAmtCr("0.00");
        this.setIntAmtRoundingAmtDr("0.00");
        this.setIntPaidRptCode("");
        this.setIntCollRptCode("");
        this.setIntDrRptCode("");
        this.setIntCrRptCode("");
        this.setTaxCollRptCode("");
        this.setTaxCollAccountPlaceholder("");
        this.setIncludeFloorLimitForTax("0.00");
        this.setWithoutTaxFlrLim("0.00");
        this.setWithHoldingTax("0.00");
        this.setEndOfDayMinBalance("0.00");
        this.setEndOfDayMaxBalance("0.00");
        this.seteODMinBalanceExceptionCode("");
        this.seteODMaxBalanceExceptionCode("");
        this.setCashLimitDr("");
        this.setCashLimitCr("");
        this.setClgLimitDr("");
        this.setClgLimitCr("");
        this.setTransferLimitDr("");
        this.setTransferLimitCr("");
        this.setLateCashTran("");
        this.setTranAmtLimit("");
        this.setDrTranAmtNotAllowed("");
        this.setCrTranAmtNotAllowed("");
        this.setMinBalanceEvent("");
        this.setAcctMinBalance("0.00");
        this.setSweepMinBalance("0.00");
        this.setMinChargePeriod("0.00");
        this.setPenalChargePeriod("0.00");
        this.setMinBalanceServiceChrg("0.00");
        this.setRegularIntCertificatePrintingEvent("");
        this.setAdhocIntCertificatePrintingEvent("");
        this.setStCashLimitDr("");
        this.setStCashLimitCr("");
        this.setStClgLimitDr("");
        this.setStClgLimitCr("");
        this.setStTransferLimitDr("");
        this.setStTransferLimitCr("");
        this.setStLateCashTran("");
        this.setStLateCashTran("");
        this.setStDrTranAmtNotAllowed("");
        this.setCrTranAmtNotAllowed("");
    }

    public void resetAllLimit() {
        this.setCrCashLimit("0.00");
        this.setDrCashLimit("0.00");
        this.setCrClgLimit("0.00");
        this.setDrClgLimit("0.00");
        this.setCrXrefLimit("0.00");
        this.setDrXrefLimit("0.00");
        this.setDrCashAbnLimit("0.00");
        this.setDrClgAbnLimit("0.00");
        this.setDrXrefAbnLimit("0.00");
        this.setNewAccountAbnTranAmt("0.00");
        this.setCrMin("0.00");
        this.setCrMax("0.00");
        this.setDrMin("0.00");
        this.setDrMax("0.00");
        this.setMinIntPaidAmt("0.00");
        this.setMinIntCollAmt("0.00");
        this.setResidualIntAdjustmentAmountforBooking("0.00");
        this.setWithoutTaxAmountRndAmount("0.00");
        this.setIntPrdRndAmountCr("0.00");
        this.setIntPrdRoundingAmtDr("0.00");
        this.setIntAmtRoundingAmtCr("0.00");
        this.setIntAmtRoundingAmtDr("0.00");
        this.setIncludeFloorLimitForTax("0.00");
        this.setWithoutTaxFlrLim("0.00");
        this.setWithHoldingTax("0.00");
        this.setEndOfDayMinBalance("0.00");
        this.setEndOfDayMaxBalance("0.00");
        this.setAcctMinBalance("0.00");
        this.setSweepMinBalance("0.00");
        this.setMinChargePeriod("0.00");
        this.setPenalChargePeriod("0.00");
        this.setMinBalanceServiceChrg("0.00");
    }

    public void enableCDForm() {
        this.cdFlag = false;
    }

    public void disableCDForm() {
        this.cdFlag = true;
    }
}
