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
@Table(name = "cbs_scheme_ca_sb_sch_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findAll", query = "SELECT c FROM CbsSchemeCaSbSchDetails c"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findBySchemeCode", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.schemeCode = :schemeCode"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findBySchemeType", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.schemeType = :schemeType"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByCurrencyCodeType", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.currencyCodeType = :currencyCodeType"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByAlwdNumberOfWthdrawals", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.alwdNumberOfWthdrawals = :alwdNumberOfWthdrawals"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByNoIntIfWithdrawalsExceed", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.noIntIfWithdrawalsExceed = :noIntIfWithdrawalsExceed"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByMinBalanceForCheque", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.minBalanceForCheque = :minBalanceForCheque"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByDrBalanceLimit", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.drBalanceLimit = :drBalanceLimit"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByLedgerFolioChargeFolio", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.ledgerFolioChargeFolio = :ledgerFolioChargeFolio"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByServiceChargeExtraWithdrawal", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.serviceChargeExtraWithdrawal = :serviceChargeExtraWithdrawal"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByInactiveAccountAbmmlTranLimit", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.inactiveAccountAbmmlTranLimit = :inactiveAccountAbmmlTranLimit"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByDormantAccAbmmlTranLimit", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.dormantAccAbmmlTranLimit = :dormantAccAbmmlTranLimit"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByDurationToMarkAccasInactive", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.durationToMarkAccasInactive = :durationToMarkAccasInactive"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByDurationToFromInactiveToDormat", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.durationToFromInactiveToDormat = :durationToFromInactiveToDormat"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByDormantChargeEvent", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.dormantChargeEvent = :dormantChargeEvent"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByInactiveChargeEvent", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.inactiveChargeEvent = :inactiveChargeEvent"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByAllowSweeps", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.allowSweeps = :allowSweeps"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByAllowDrAgainstUnclearBalance", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.allowDrAgainstUnclearBalance = :allowDrAgainstUnclearBalance"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByIntCalculationOnLocalCalender", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.intCalculationOnLocalCalender = :intCalculationOnLocalCalender"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByIntOnAvgOrMinBal", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.intOnAvgOrMinBal = :intOnAvgOrMinBal"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByBalanceBetween", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.balanceBetween = :balanceBetween"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByDrBalLimit", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.drBalLimit = :drBalLimit"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByCITranToInactiveAccount", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.cITranToInactiveAccount = :cITranToInactiveAccount"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByCIDrTranToDormantAccount", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.cIDrTranToDormantAccount = :cIDrTranToDormantAccount"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByBIDrTranToDormantAccount", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.bIDrTranToDormantAccount = :bIDrTranToDormantAccount"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByChequeIssuedOnInactiveAccount", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.chequeIssuedOnInactiveAccount = :chequeIssuedOnInactiveAccount"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByChequeIssuedOnDormantAccount", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.chequeIssuedOnDormantAccount = :chequeIssuedOnDormantAccount"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByChequeBookWithoutMinBalance", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.chequeBookWithoutMinBalance = :chequeBookWithoutMinBalance"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByAccClosedWithinAnYear", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.accClosedWithinAnYear = :accClosedWithinAnYear"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByNoOfWithdrawalsExceeded", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.noOfWithdrawalsExceeded = :noOfWithdrawalsExceeded"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByTODOnMinorAccount", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.tODOnMinorAccount = :tODOnMinorAccount"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByIntroducerNotACAHolder", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.introducerNotACAHolder = :introducerNotACAHolder"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByAccMinBalanceBelowSchemeMinBal", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.accMinBalanceBelowSchemeMinBal = :accMinBalanceBelowSchemeMinBal"),
    @NamedQuery(name = "CbsSchemeCaSbSchDetails.findByBalanceAnd", query = "SELECT c FROM CbsSchemeCaSbSchDetails c WHERE c.balanceAnd = :balanceAnd")})
public class CbsSchemeCaSbSchDetails extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "Scheme_Code")
    private String schemeCode;
    @Size(max = 6)
    @Column(name = "Scheme_Type")
    private String schemeType;
    @Size(max = 3)
    @Column(name = "Currency_Code_Type")
    private String currencyCodeType;
    @Size(max = 2)
    @Column(name = "Alwd_Number_Of_Wthdrawals")
    private String alwdNumberOfWthdrawals;
    @Size(max = 1)
    @Column(name = "No_Int_If_Withdrawals_Exceed")
    private String noIntIfWithdrawalsExceed;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Min_Balance_For_Cheque")
    private BigDecimal minBalanceForCheque;
    @Column(name = "Dr_Balance_Limit")
    private BigDecimal drBalanceLimit;
    @Column(name = "Ledger_Folio_Charge_Folio")
    private BigDecimal ledgerFolioChargeFolio;
    @Column(name = "Service_Charge_Extra_Withdrawal")
    private BigDecimal serviceChargeExtraWithdrawal;
    @Column(name = "Inactive_Account_Abmml_Tran_Limit")
    private BigDecimal inactiveAccountAbmmlTranLimit;
    @Column(name = "Dormant_Acc_Abmml_Tran_Limit")
    private BigDecimal dormantAccAbmmlTranLimit;
    @Size(max = 2)
    @Column(name = "Duration_To_Mark_Acc_as_Inactive")
    private String durationToMarkAccasInactive;
    @Size(max = 2)
    @Column(name = "Duration_To_From_Inactive_To_Dormat")
    private String durationToFromInactiveToDormat;
    @Size(max = 25)
    @Column(name = "Dormant_Charge_Event")
    private String dormantChargeEvent;
    @Size(max = 25)
    @Column(name = "Inactive_Charge_Event")
    private String inactiveChargeEvent;
    @Size(max = 1)
    @Column(name = "Allow_Sweeps")
    private String allowSweeps;
    @Size(max = 1)
    @Column(name = "Allow_Dr_Against_Unclear_Balance")
    private String allowDrAgainstUnclearBalance;
    @Size(max = 1)
    @Column(name = "Int_Calculation_On_Local_Calender")
    private String intCalculationOnLocalCalender;
    @Size(max = 1)
    @Column(name = "Int_On_Avg_Or_Min_Bal")
    private String intOnAvgOrMinBal;
    @Column(name = "Balance_Between")
    private BigDecimal balanceBetween;
    @Size(max = 3)
    @Column(name = "Dr_Bal_Limit")
    private String drBalLimit;
    @Size(max = 3)
    @Column(name = "CI_Tran_To_Inactive_Account")
    private String cITranToInactiveAccount;
    @Size(max = 3)
    @Column(name = "CI_Dr_Tran_To_Dormant_Account")
    private String cIDrTranToDormantAccount;
    @Size(max = 3)
    @Column(name = "BI_Dr_Tran_To_Dormant_Account")
    private String bIDrTranToDormantAccount;
    @Size(max = 3)
    @Column(name = "Cheque_Issued_On_Inactive_Account")
    private String chequeIssuedOnInactiveAccount;
    @Size(max = 3)
    @Column(name = "Cheque_Issued_On_Dormant_Account")
    private String chequeIssuedOnDormantAccount;
    @Size(max = 3)
    @Column(name = "Cheque_Book_Without_Min_Balance")
    private String chequeBookWithoutMinBalance;
    @Size(max = 3)
    @Column(name = "Acc_Closed_Within_An_Year")
    private String accClosedWithinAnYear;
    @Size(max = 3)
    @Column(name = "No_Of_Withdrawals_Exceeded")
    private String noOfWithdrawalsExceeded;
    @Size(max = 3)
    @Column(name = "TOD_On_Minor_Account")
    private String tODOnMinorAccount;
    @Size(max = 3)
    @Column(name = "Introducer_Not_A_CA_Holder")
    private String introducerNotACAHolder;
    @Size(max = 3)
    @Column(name = "Acc_Min_Balance_Below_Scheme_Min_Bal")
    private String accMinBalanceBelowSchemeMinBal;
    @Column(name = "BALANCE_AND")
    private BigDecimal balanceAnd;

    public CbsSchemeCaSbSchDetails() {
    }

    public CbsSchemeCaSbSchDetails(String schemeCode) {
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

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
    }

    public String getAlwdNumberOfWthdrawals() {
        return alwdNumberOfWthdrawals;
    }

    public void setAlwdNumberOfWthdrawals(String alwdNumberOfWthdrawals) {
        this.alwdNumberOfWthdrawals = alwdNumberOfWthdrawals;
    }

    public String getNoIntIfWithdrawalsExceed() {
        return noIntIfWithdrawalsExceed;
    }

    public void setNoIntIfWithdrawalsExceed(String noIntIfWithdrawalsExceed) {
        this.noIntIfWithdrawalsExceed = noIntIfWithdrawalsExceed;
    }

    public BigDecimal getMinBalanceForCheque() {
        return minBalanceForCheque;
    }

    public void setMinBalanceForCheque(BigDecimal minBalanceForCheque) {
        this.minBalanceForCheque = minBalanceForCheque;
    }

    public BigDecimal getDrBalanceLimit() {
        return drBalanceLimit;
    }

    public void setDrBalanceLimit(BigDecimal drBalanceLimit) {
        this.drBalanceLimit = drBalanceLimit;
    }

    public BigDecimal getLedgerFolioChargeFolio() {
        return ledgerFolioChargeFolio;
    }

    public void setLedgerFolioChargeFolio(BigDecimal ledgerFolioChargeFolio) {
        this.ledgerFolioChargeFolio = ledgerFolioChargeFolio;
    }

    public BigDecimal getServiceChargeExtraWithdrawal() {
        return serviceChargeExtraWithdrawal;
    }

    public void setServiceChargeExtraWithdrawal(BigDecimal serviceChargeExtraWithdrawal) {
        this.serviceChargeExtraWithdrawal = serviceChargeExtraWithdrawal;
    }

    public BigDecimal getInactiveAccountAbmmlTranLimit() {
        return inactiveAccountAbmmlTranLimit;
    }

    public void setInactiveAccountAbmmlTranLimit(BigDecimal inactiveAccountAbmmlTranLimit) {
        this.inactiveAccountAbmmlTranLimit = inactiveAccountAbmmlTranLimit;
    }

    public BigDecimal getDormantAccAbmmlTranLimit() {
        return dormantAccAbmmlTranLimit;
    }

    public void setDormantAccAbmmlTranLimit(BigDecimal dormantAccAbmmlTranLimit) {
        this.dormantAccAbmmlTranLimit = dormantAccAbmmlTranLimit;
    }

    public String getDurationToMarkAccasInactive() {
        return durationToMarkAccasInactive;
    }

    public void setDurationToMarkAccasInactive(String durationToMarkAccasInactive) {
        this.durationToMarkAccasInactive = durationToMarkAccasInactive;
    }

    public String getDurationToFromInactiveToDormat() {
        return durationToFromInactiveToDormat;
    }

    public void setDurationToFromInactiveToDormat(String durationToFromInactiveToDormat) {
        this.durationToFromInactiveToDormat = durationToFromInactiveToDormat;
    }

    public String getDormantChargeEvent() {
        return dormantChargeEvent;
    }

    public void setDormantChargeEvent(String dormantChargeEvent) {
        this.dormantChargeEvent = dormantChargeEvent;
    }

    public String getInactiveChargeEvent() {
        return inactiveChargeEvent;
    }

    public void setInactiveChargeEvent(String inactiveChargeEvent) {
        this.inactiveChargeEvent = inactiveChargeEvent;
    }

    public String getAllowSweeps() {
        return allowSweeps;
    }

    public void setAllowSweeps(String allowSweeps) {
        this.allowSweeps = allowSweeps;
    }

    public String getAllowDrAgainstUnclearBalance() {
        return allowDrAgainstUnclearBalance;
    }

    public void setAllowDrAgainstUnclearBalance(String allowDrAgainstUnclearBalance) {
        this.allowDrAgainstUnclearBalance = allowDrAgainstUnclearBalance;
    }

    public String getIntCalculationOnLocalCalender() {
        return intCalculationOnLocalCalender;
    }

    public void setIntCalculationOnLocalCalender(String intCalculationOnLocalCalender) {
        this.intCalculationOnLocalCalender = intCalculationOnLocalCalender;
    }

    public String getIntOnAvgOrMinBal() {
        return intOnAvgOrMinBal;
    }

    public void setIntOnAvgOrMinBal(String intOnAvgOrMinBal) {
        this.intOnAvgOrMinBal = intOnAvgOrMinBal;
    }

    public BigDecimal getBalanceBetween() {
        return balanceBetween;
    }

    public void setBalanceBetween(BigDecimal balanceBetween) {
        this.balanceBetween = balanceBetween;
    }

    public String getDrBalLimit() {
        return drBalLimit;
    }

    public void setDrBalLimit(String drBalLimit) {
        this.drBalLimit = drBalLimit;
    }

    public String getCITranToInactiveAccount() {
        return cITranToInactiveAccount;
    }

    public void setCITranToInactiveAccount(String cITranToInactiveAccount) {
        this.cITranToInactiveAccount = cITranToInactiveAccount;
    }

    public String getCIDrTranToDormantAccount() {
        return cIDrTranToDormantAccount;
    }

    public void setCIDrTranToDormantAccount(String cIDrTranToDormantAccount) {
        this.cIDrTranToDormantAccount = cIDrTranToDormantAccount;
    }

    public String getBIDrTranToDormantAccount() {
        return bIDrTranToDormantAccount;
    }

    public void setBIDrTranToDormantAccount(String bIDrTranToDormantAccount) {
        this.bIDrTranToDormantAccount = bIDrTranToDormantAccount;
    }

    public String getChequeIssuedOnInactiveAccount() {
        return chequeIssuedOnInactiveAccount;
    }

    public void setChequeIssuedOnInactiveAccount(String chequeIssuedOnInactiveAccount) {
        this.chequeIssuedOnInactiveAccount = chequeIssuedOnInactiveAccount;
    }

    public String getChequeIssuedOnDormantAccount() {
        return chequeIssuedOnDormantAccount;
    }

    public void setChequeIssuedOnDormantAccount(String chequeIssuedOnDormantAccount) {
        this.chequeIssuedOnDormantAccount = chequeIssuedOnDormantAccount;
    }

    public String getChequeBookWithoutMinBalance() {
        return chequeBookWithoutMinBalance;
    }

    public void setChequeBookWithoutMinBalance(String chequeBookWithoutMinBalance) {
        this.chequeBookWithoutMinBalance = chequeBookWithoutMinBalance;
    }

    public String getAccClosedWithinAnYear() {
        return accClosedWithinAnYear;
    }

    public void setAccClosedWithinAnYear(String accClosedWithinAnYear) {
        this.accClosedWithinAnYear = accClosedWithinAnYear;
    }

    public String getNoOfWithdrawalsExceeded() {
        return noOfWithdrawalsExceeded;
    }

    public void setNoOfWithdrawalsExceeded(String noOfWithdrawalsExceeded) {
        this.noOfWithdrawalsExceeded = noOfWithdrawalsExceeded;
    }

    public String getTODOnMinorAccount() {
        return tODOnMinorAccount;
    }

    public void setTODOnMinorAccount(String tODOnMinorAccount) {
        this.tODOnMinorAccount = tODOnMinorAccount;
    }

    public String getIntroducerNotACAHolder() {
        return introducerNotACAHolder;
    }

    public void setIntroducerNotACAHolder(String introducerNotACAHolder) {
        this.introducerNotACAHolder = introducerNotACAHolder;
    }

    public String getAccMinBalanceBelowSchemeMinBal() {
        return accMinBalanceBelowSchemeMinBal;
    }

    public void setAccMinBalanceBelowSchemeMinBal(String accMinBalanceBelowSchemeMinBal) {
        this.accMinBalanceBelowSchemeMinBal = accMinBalanceBelowSchemeMinBal;
    }

    public BigDecimal getBalanceAnd() {
        return balanceAnd;
    }

    public void setBalanceAnd(BigDecimal balanceAnd) {
        this.balanceAnd = balanceAnd;
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
        if (!(object instanceof CbsSchemeCaSbSchDetails)) {
            return false;
        }
        CbsSchemeCaSbSchDetails other = (CbsSchemeCaSbSchDetails) object;
        if ((this.schemeCode == null && other.schemeCode != null) || (this.schemeCode != null && !this.schemeCode.equals(other.schemeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.CbsSchemeCaSbSchDetails[ schemeCode=" + schemeCode + " ]";
    }
    
}
