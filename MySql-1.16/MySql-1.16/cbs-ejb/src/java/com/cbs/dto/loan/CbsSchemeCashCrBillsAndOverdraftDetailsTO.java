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
public class CbsSchemeCashCrBillsAndOverdraftDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String schemeType;
    private String currencyCode;
    private BigDecimal maxSanctionLimit;
    private BigDecimal debitBalanceLimit;
    private Long ledgerFolioChargeOrFolio;
    private BigDecimal inactiveAccountAbrnmlTranLimit;
    private BigDecimal dormantAccountAbrnmlTranLimit;
    private BigDecimal maximumPenalInterest;
    private String durationtoMarkAccountAsInactive;
    private String durationFromInactiveToDormant;
    private String dormantAccountChargeEvent;
    private String inactiveAccountChargeEvent;
    private String allowSweeps;
    private String debitAgainstUnclearBalance;
    private String debitBalanceLimitExec;
    private String bpPtranOutsideBills;
    private String sancOrDisbExceedExpOrdOrDc;
    private String sancOrDisbWithoutExpOrdOrDc;
    private String ciTranToInactiveAcct;
    private String ciDrTranToDormantAcct;
    private String biDrTranToDormantAcct;
    private String chequeIssuedToInactiveAcct;
    private String chequeIssuedToDormantAcct;
    private String sanctionLimitCompletelyUtilised;
    private String acctMiniBalBelowSchemeMinBal;

    public String getAcctMiniBalBelowSchemeMinBal() {
        return acctMiniBalBelowSchemeMinBal;
    }

    public void setAcctMiniBalBelowSchemeMinBal(String acctMiniBalBelowSchemeMinBal) {
        this.acctMiniBalBelowSchemeMinBal = acctMiniBalBelowSchemeMinBal;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getBiDrTranToDormantAcct() {
        return biDrTranToDormantAcct;
    }

    public void setBiDrTranToDormantAcct(String biDrTranToDormantAcct) {
        this.biDrTranToDormantAcct = biDrTranToDormantAcct;
    }

    public String getBpPtranOutsideBills() {
        return bpPtranOutsideBills;
    }

    public void setBpPtranOutsideBills(String bpPtranOutsideBills) {
        this.bpPtranOutsideBills = bpPtranOutsideBills;
    }

    public String getChequeIssuedToDormantAcct() {
        return chequeIssuedToDormantAcct;
    }

    public void setChequeIssuedToDormantAcct(String chequeIssuedToDormantAcct) {
        this.chequeIssuedToDormantAcct = chequeIssuedToDormantAcct;
    }

    public String getChequeIssuedToInactiveAcct() {
        return chequeIssuedToInactiveAcct;
    }

    public void setChequeIssuedToInactiveAcct(String chequeIssuedToInactiveAcct) {
        this.chequeIssuedToInactiveAcct = chequeIssuedToInactiveAcct;
    }

    public String getCiDrTranToDormantAcct() {
        return ciDrTranToDormantAcct;
    }

    public void setCiDrTranToDormantAcct(String ciDrTranToDormantAcct) {
        this.ciDrTranToDormantAcct = ciDrTranToDormantAcct;
    }

    public String getCiTranToInactiveAcct() {
        return ciTranToInactiveAcct;
    }

    public void setCiTranToInactiveAcct(String ciTranToInactiveAcct) {
        this.ciTranToInactiveAcct = ciTranToInactiveAcct;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDebitAgainstUnclearBalance() {
        return debitAgainstUnclearBalance;
    }

    public void setDebitAgainstUnclearBalance(String debitAgainstUnclearBalance) {
        this.debitAgainstUnclearBalance = debitAgainstUnclearBalance;
    }

    public BigDecimal getDebitBalanceLimit() {
        return debitBalanceLimit;
    }

    public void setDebitBalanceLimit(BigDecimal debitBalanceLimit) {
        this.debitBalanceLimit = debitBalanceLimit;
    }

    public String getDebitBalanceLimitExec() {
        return debitBalanceLimitExec;
    }

    public void setDebitBalanceLimitExec(String debitBalanceLimitExec) {
        this.debitBalanceLimitExec = debitBalanceLimitExec;
    }

    public BigDecimal getDormantAccountAbrnmlTranLimit() {
        return dormantAccountAbrnmlTranLimit;
    }

    public void setDormantAccountAbrnmlTranLimit(BigDecimal dormantAccountAbrnmlTranLimit) {
        this.dormantAccountAbrnmlTranLimit = dormantAccountAbrnmlTranLimit;
    }

    public String getDormantAccountChargeEvent() {
        return dormantAccountChargeEvent;
    }

    public void setDormantAccountChargeEvent(String dormantAccountChargeEvent) {
        this.dormantAccountChargeEvent = dormantAccountChargeEvent;
    }

    public String getDurationFromInactiveToDormant() {
        return durationFromInactiveToDormant;
    }

    public void setDurationFromInactiveToDormant(String durationFromInactiveToDormant) {
        this.durationFromInactiveToDormant = durationFromInactiveToDormant;
    }

    public String getDurationtoMarkAccountAsInactive() {
        return durationtoMarkAccountAsInactive;
    }

    public void setDurationtoMarkAccountAsInactive(String durationtoMarkAccountAsInactive) {
        this.durationtoMarkAccountAsInactive = durationtoMarkAccountAsInactive;
    }

    public BigDecimal getInactiveAccountAbrnmlTranLimit() {
        return inactiveAccountAbrnmlTranLimit;
    }

    public void setInactiveAccountAbrnmlTranLimit(BigDecimal inactiveAccountAbrnmlTranLimit) {
        this.inactiveAccountAbrnmlTranLimit = inactiveAccountAbrnmlTranLimit;
    }

    public String getInactiveAccountChargeEvent() {
        return inactiveAccountChargeEvent;
    }

    public void setInactiveAccountChargeEvent(String inactiveAccountChargeEvent) {
        this.inactiveAccountChargeEvent = inactiveAccountChargeEvent;
    }

    public Long getLedgerFolioChargeOrFolio() {
        return ledgerFolioChargeOrFolio;
    }

    public void setLedgerFolioChargeOrFolio(Long ledgerFolioChargeOrFolio) {
        this.ledgerFolioChargeOrFolio = ledgerFolioChargeOrFolio;
    }

    public BigDecimal getMaxSanctionLimit() {
        return maxSanctionLimit;
    }

    public void setMaxSanctionLimit(BigDecimal maxSanctionLimit) {
        this.maxSanctionLimit = maxSanctionLimit;
    }

    public BigDecimal getMaximumPenalInterest() {
        return maximumPenalInterest;
    }

    public void setMaximumPenalInterest(BigDecimal maximumPenalInterest) {
        this.maximumPenalInterest = maximumPenalInterest;
    }

    public String getSancOrDisbExceedExpOrdOrDc() {
        return sancOrDisbExceedExpOrdOrDc;
    }

    public void setSancOrDisbExceedExpOrdOrDc(String sancOrDisbExceedExpOrdOrDc) {
        this.sancOrDisbExceedExpOrdOrDc = sancOrDisbExceedExpOrdOrDc;
    }

    public String getSancOrDisbWithoutExpOrdOrDc() {
        return sancOrDisbWithoutExpOrdOrDc;
    }

    public void setSancOrDisbWithoutExpOrdOrDc(String sancOrDisbWithoutExpOrdOrDc) {
        this.sancOrDisbWithoutExpOrdOrDc = sancOrDisbWithoutExpOrdOrDc;
    }

    public String getSanctionLimitCompletelyUtilised() {
        return sanctionLimitCompletelyUtilised;
    }

    public void setSanctionLimitCompletelyUtilised(String sanctionLimitCompletelyUtilised) {
        this.sanctionLimitCompletelyUtilised = sanctionLimitCompletelyUtilised;
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
}
