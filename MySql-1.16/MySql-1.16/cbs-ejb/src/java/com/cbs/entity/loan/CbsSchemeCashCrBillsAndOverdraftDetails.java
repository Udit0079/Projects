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
@Table(name = "cbs_scheme_cash_cr_bills_and_overdraft_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findAll", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByCurrencyCode", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByMaxSanctionLimit", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.maxSanctionLimit = :maxSanctionLimit"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDebitBalanceLimit", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.debitBalanceLimit = :debitBalanceLimit"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByLedgerFolioChargeOrFolio", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.ledgerFolioChargeOrFolio = :ledgerFolioChargeOrFolio"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByInactiveAccountAbrnmlTranLimit", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.inactiveAccountAbrnmlTranLimit = :inactiveAccountAbrnmlTranLimit"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDormantAccountAbrnmlTranLimit", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.dormantAccountAbrnmlTranLimit = :dormantAccountAbrnmlTranLimit"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByMaximumPenalInterest", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.maximumPenalInterest = :maximumPenalInterest"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDurationtoMarkAccountAsInactive", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.durationtoMarkAccountAsInactive = :durationtoMarkAccountAsInactive"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDurationFromInactiveToDormant", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.durationFromInactiveToDormant = :durationFromInactiveToDormant"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDormantAccountChargeEvent", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.dormantAccountChargeEvent = :dormantAccountChargeEvent"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByInactiveAccountChargeEvent", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.inactiveAccountChargeEvent = :inactiveAccountChargeEvent"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByAllowSweeps", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.allowSweeps = :allowSweeps"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDebitAgainstUnclearBalance", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.debitAgainstUnclearBalance = :debitAgainstUnclearBalance"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByDebitBalanceLimitExec", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.debitBalanceLimitExec = :debitBalanceLimitExec"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByBpPtranOutsideBills", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.bpPtranOutsideBills = :bpPtranOutsideBills"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findBySancOrDisbExceedExpOrdOrDc", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.sancOrDisbExceedExpOrdOrDc = :sancOrDisbExceedExpOrdOrDc"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findBySancOrDisbWithoutExpOrdOrDc", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.sancOrDisbWithoutExpOrdOrDc = :sancOrDisbWithoutExpOrdOrDc"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByCiTranToInactiveAcct", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.ciTranToInactiveAcct = :ciTranToInactiveAcct"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByCiDrTranToDormantAcct", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.ciDrTranToDormantAcct = :ciDrTranToDormantAcct"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByBiDrTranToDormantAcct", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.biDrTranToDormantAcct = :biDrTranToDormantAcct"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByChequeIssuedToInactiveAcct", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.chequeIssuedToInactiveAcct = :chequeIssuedToInactiveAcct"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByChequeIssuedToDormantAcct", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.chequeIssuedToDormantAcct = :chequeIssuedToDormantAcct"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findBySanctionLimitCompletelyUtilised", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.sanctionLimitCompletelyUtilised = :sanctionLimitCompletelyUtilised"),
    @NamedQuery(name = "CbsSchemeCashCrBillsAndOverdraftDetails.findByAcctMiniBalBelowSchemeMinBal", query = "SELECT c FROM CbsSchemeCashCrBillsAndOverdraftDetails c WHERE c.acctMiniBalBelowSchemeMinBal = :acctMiniBalBelowSchemeMinBal")})
public class CbsSchemeCashCrBillsAndOverdraftDetails extends BaseEntity implements Serializable {
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
    @Column(name = "MAX_SANCTION_LIMIT")
    private BigDecimal maxSanctionLimit;
    @Column(name = "DEBIT_BALANCE_LIMIT")
    private BigDecimal debitBalanceLimit;
    @Column(name = "LEDGER_FOLIO_CHARGE_OR_FOLIO")
    private Long ledgerFolioChargeOrFolio;
    @Column(name = "INACTIVE_ACCOUNT_ABRNML_TRAN_LIMIT")
    private BigDecimal inactiveAccountAbrnmlTranLimit;
    @Column(name = "DORMANT_ACCOUNT_ABRNML_TRAN_LIMIT")
    private BigDecimal dormantAccountAbrnmlTranLimit;
    @Column(name = "MAXIMUM_PENAL_INTEREST")
    private BigDecimal maximumPenalInterest;
    @Size(max = 3)
    @Column(name = "DURATIONTO_MARK_ACCOUNT_AS_INACTIVE")
    private String durationtoMarkAccountAsInactive;
    @Size(max = 3)
    @Column(name = "DURATION_FROM_INACTIVE_TO_DORMANT")
    private String durationFromInactiveToDormant;
    @Size(max = 5)
    @Column(name = "DORMANT_ACCOUNT_CHARGE_EVENT")
    private String dormantAccountChargeEvent;
    @Size(max = 5)
    @Column(name = "INACTIVE_ACCOUNT_CHARGE_EVENT")
    private String inactiveAccountChargeEvent;
    @Size(max = 1)
    @Column(name = "ALLOW_SWEEPS")
    private String allowSweeps;
    @Size(max = 1)
    @Column(name = "DEBIT_AGAINST_UNCLEAR_BALANCE")
    private String debitAgainstUnclearBalance;
    @Size(max = 3)
    @Column(name = "DEBIT_BALANCE_LIMIT_EXEC")
    private String debitBalanceLimitExec;
    @Size(max = 3)
    @Column(name = "BP_PTRAN_OUTSIDE_BILLS")
    private String bpPtranOutsideBills;
    @Size(max = 3)
    @Column(name = "SANC_OR_DISB_EXCEED_EXP_ORD_OR_DC")
    private String sancOrDisbExceedExpOrdOrDc;
    @Size(max = 3)
    @Column(name = "SANC_OR_DISB_WITHOUT_EXP_ORD_OR_DC")
    private String sancOrDisbWithoutExpOrdOrDc;
    @Size(max = 3)
    @Column(name = "CI_TRAN_TO_INACTIVE_ACCT")
    private String ciTranToInactiveAcct;
    @Size(max = 3)
    @Column(name = "CI_DR_TRAN_TO_DORMANT_ACCT")
    private String ciDrTranToDormantAcct;
    @Size(max = 3)
    @Column(name = "BI_DR_TRAN_TO_DORMANT_ACCT")
    private String biDrTranToDormantAcct;
    @Size(max = 3)
    @Column(name = "CHEQUE_ISSUED_TO_INACTIVE_ACCT")
    private String chequeIssuedToInactiveAcct;
    @Size(max = 3)
    @Column(name = "CHEQUE_ISSUED_TO_DORMANT_ACCT")
    private String chequeIssuedToDormantAcct;
    @Size(max = 3)
    @Column(name = "SANCTION_LIMIT_COMPLETELY_UTILISED")
    private String sanctionLimitCompletelyUtilised;
    @Size(max = 3)
    @Column(name = "ACCT_MINI_BAL_BELOW_SCHEME_MIN_BAL")
    private String acctMiniBalBelowSchemeMinBal;

    public CbsSchemeCashCrBillsAndOverdraftDetails() {
    }

    public CbsSchemeCashCrBillsAndOverdraftDetails(String schemeCode) {
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

    public BigDecimal getMaxSanctionLimit() {
        return maxSanctionLimit;
    }

    public void setMaxSanctionLimit(BigDecimal maxSanctionLimit) {
        this.maxSanctionLimit = maxSanctionLimit;
    }

    public BigDecimal getDebitBalanceLimit() {
        return debitBalanceLimit;
    }

    public void setDebitBalanceLimit(BigDecimal debitBalanceLimit) {
        this.debitBalanceLimit = debitBalanceLimit;
    }

    public Long getLedgerFolioChargeOrFolio() {
        return ledgerFolioChargeOrFolio;
    }

    public void setLedgerFolioChargeOrFolio(Long ledgerFolioChargeOrFolio) {
        this.ledgerFolioChargeOrFolio = ledgerFolioChargeOrFolio;
    }

    public BigDecimal getInactiveAccountAbrnmlTranLimit() {
        return inactiveAccountAbrnmlTranLimit;
    }

    public void setInactiveAccountAbrnmlTranLimit(BigDecimal inactiveAccountAbrnmlTranLimit) {
        this.inactiveAccountAbrnmlTranLimit = inactiveAccountAbrnmlTranLimit;
    }

    public BigDecimal getDormantAccountAbrnmlTranLimit() {
        return dormantAccountAbrnmlTranLimit;
    }

    public void setDormantAccountAbrnmlTranLimit(BigDecimal dormantAccountAbrnmlTranLimit) {
        this.dormantAccountAbrnmlTranLimit = dormantAccountAbrnmlTranLimit;
    }

    public BigDecimal getMaximumPenalInterest() {
        return maximumPenalInterest;
    }

    public void setMaximumPenalInterest(BigDecimal maximumPenalInterest) {
        this.maximumPenalInterest = maximumPenalInterest;
    }

    public String getDurationtoMarkAccountAsInactive() {
        return durationtoMarkAccountAsInactive;
    }

    public void setDurationtoMarkAccountAsInactive(String durationtoMarkAccountAsInactive) {
        this.durationtoMarkAccountAsInactive = durationtoMarkAccountAsInactive;
    }

    public String getDurationFromInactiveToDormant() {
        return durationFromInactiveToDormant;
    }

    public void setDurationFromInactiveToDormant(String durationFromInactiveToDormant) {
        this.durationFromInactiveToDormant = durationFromInactiveToDormant;
    }

    public String getDormantAccountChargeEvent() {
        return dormantAccountChargeEvent;
    }

    public void setDormantAccountChargeEvent(String dormantAccountChargeEvent) {
        this.dormantAccountChargeEvent = dormantAccountChargeEvent;
    }

    public String getInactiveAccountChargeEvent() {
        return inactiveAccountChargeEvent;
    }

    public void setInactiveAccountChargeEvent(String inactiveAccountChargeEvent) {
        this.inactiveAccountChargeEvent = inactiveAccountChargeEvent;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getDebitAgainstUnclearBalance() {
        return debitAgainstUnclearBalance;
    }

    public void setDebitAgainstUnclearBalance(String debitAgainstUnclearBalance) {
        this.debitAgainstUnclearBalance = debitAgainstUnclearBalance;
    }

    public String getDebitBalanceLimitExec() {
        return debitBalanceLimitExec;
    }

    public void setDebitBalanceLimitExec(String debitBalanceLimitExec) {
        this.debitBalanceLimitExec = debitBalanceLimitExec;
    }

    public String getBpPtranOutsideBills() {
        return bpPtranOutsideBills;
    }

    public void setBpPtranOutsideBills(String bpPtranOutsideBills) {
        this.bpPtranOutsideBills = bpPtranOutsideBills;
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

    public String getCiTranToInactiveAcct() {
        return ciTranToInactiveAcct;
    }

    public void setCiTranToInactiveAcct(String ciTranToInactiveAcct) {
        this.ciTranToInactiveAcct = ciTranToInactiveAcct;
    }

    public String getCiDrTranToDormantAcct() {
        return ciDrTranToDormantAcct;
    }

    public void setCiDrTranToDormantAcct(String ciDrTranToDormantAcct) {
        this.ciDrTranToDormantAcct = ciDrTranToDormantAcct;
    }

    public String getBiDrTranToDormantAcct() {
        return biDrTranToDormantAcct;
    }

    public void setBiDrTranToDormantAcct(String biDrTranToDormantAcct) {
        this.biDrTranToDormantAcct = biDrTranToDormantAcct;
    }

    public String getChequeIssuedToInactiveAcct() {
        return chequeIssuedToInactiveAcct;
    }

    public void setChequeIssuedToInactiveAcct(String chequeIssuedToInactiveAcct) {
        this.chequeIssuedToInactiveAcct = chequeIssuedToInactiveAcct;
    }

    public String getChequeIssuedToDormantAcct() {
        return chequeIssuedToDormantAcct;
    }

    public void setChequeIssuedToDormantAcct(String chequeIssuedToDormantAcct) {
        this.chequeIssuedToDormantAcct = chequeIssuedToDormantAcct;
    }

    public String getSanctionLimitCompletelyUtilised() {
        return sanctionLimitCompletelyUtilised;
    }

    public void setSanctionLimitCompletelyUtilised(String sanctionLimitCompletelyUtilised) {
        this.sanctionLimitCompletelyUtilised = sanctionLimitCompletelyUtilised;
    }

    public String getAcctMiniBalBelowSchemeMinBal() {
        return acctMiniBalBelowSchemeMinBal;
    }

    public void setAcctMiniBalBelowSchemeMinBal(String acctMiniBalBelowSchemeMinBal) {
        this.acctMiniBalBelowSchemeMinBal = acctMiniBalBelowSchemeMinBal;
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
        if (!(object instanceof CbsSchemeCashCrBillsAndOverdraftDetails)) {
            return false;
        }
        CbsSchemeCashCrBillsAndOverdraftDetails other = (CbsSchemeCashCrBillsAndOverdraftDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeCashCrBillsAndOverdraftDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
