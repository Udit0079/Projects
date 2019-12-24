/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeCaSbSchDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

public class SPM {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;
    String allowedNoWithdrawls;
    String noWithdrawlsExceed;
    List<SelectItem> noWithdrawlsExceedoption;
    String minBalChq;
    String drBalLim;
    String ledgerFolio1;
    String servChrgWithdrawl;
    String inactiveAccAbnrmlTranLimit;
    String dormantAccAbnrmlTranLimit;
    String durationMarkAccAct;
    String durationFromactDorm;
    String dormEvent;
    String inacEvent;
    String allowSweep;
    List<SelectItem> allowSweepOption;
    String allowDrUnclearBalance;
    List<SelectItem> allowDrUnclearBalanceOption;
    String intCalcLocalCalender;
    List<SelectItem> intCalcLocalCalenderOption;
    String intAvgBalance;
    List<SelectItem> intAvgBalanceOption;
    String balBet;
    String andBalBet;
    String drBalLimitExc;
    String cITranInacAcc;
    String cIDrTranDormantAcct;
    String bIDrTranDormantAcct;
    String chqIssuedOnInactiveAcct;
    String chqIssuedOnDormantAcct;
    String chqBkWithoutMinBal;
    String accClosedWithinYr;
    String noWithdrawlsExceeded;
    String tODMinorAcc;
    String introducerNotCAHold;
    String accMinBalBlowSchMinBal;
    String drBalLimitExcValue;
    String cITranInacAccValue;
    String cIDrTranDormantAcctValue;
    String bIDrTranDormantAcctValue;
    String chqIssuedOnInactiveAcctValue;
    String chqIssuedOnDormantAcctValue;
    String chqBkWithoutMinBalValue;
    String accClosedWithinYrValue;
    String noWithdrawlsExceededValue;
    String tODMinorAccValue;
    String introducerNotCAHoldValue;
    String accMinBalBlowSchMinBalValue;
    private boolean disableFlag;

    /** Creates a new instance of SPM */
    public SPM() {
       
        noWithdrawlsExceedoption = new ArrayList<SelectItem>();
        noWithdrawlsExceedoption.add(new SelectItem("0", ""));
        noWithdrawlsExceedoption.add(new SelectItem("Y", "Yes"));
        noWithdrawlsExceedoption.add(new SelectItem("N", "No"));
        allowSweepOption = new ArrayList<SelectItem>();
        allowSweepOption.add(new SelectItem("0", ""));
        allowSweepOption.add(new SelectItem("Y", "Yes"));
        allowSweepOption.add(new SelectItem("N", "No"));
        allowDrUnclearBalanceOption = new ArrayList<SelectItem>();
        allowDrUnclearBalanceOption.add(new SelectItem("0", ""));
        allowDrUnclearBalanceOption.add(new SelectItem("Y", "Yes"));
        allowDrUnclearBalanceOption.add(new SelectItem("N", "No"));
        intCalcLocalCalenderOption = new ArrayList<SelectItem>();
        intCalcLocalCalenderOption.add(new SelectItem("0", ""));
        intCalcLocalCalenderOption.add(new SelectItem("Y", "Yes"));
        intCalcLocalCalenderOption.add(new SelectItem("N", "No"));
        intAvgBalanceOption = new ArrayList<SelectItem>();
        intAvgBalanceOption.add(new SelectItem("0", ""));
        intAvgBalanceOption.add(new SelectItem("Y", "Yes"));
        intAvgBalanceOption.add(new SelectItem("N", "No"));

    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }
    

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }

    public String getAccClosedWithinYr() {
        return accClosedWithinYr;
    }

    public void setAccClosedWithinYr(String accClosedWithinYr) {
        this.accClosedWithinYr = accClosedWithinYr;
    }

    public String getAccClosedWithinYrValue() {
        return accClosedWithinYrValue;
    }

    public void setAccClosedWithinYrValue(String accClosedWithinYrValue) {
        this.accClosedWithinYrValue = accClosedWithinYrValue;
    }

    public String getAccMinBalBlowSchMinBal() {
        return accMinBalBlowSchMinBal;
    }

    public void setAccMinBalBlowSchMinBal(String accMinBalBlowSchMinBal) {
        this.accMinBalBlowSchMinBal = accMinBalBlowSchMinBal;
    }

    public String getAccMinBalBlowSchMinBalValue() {
        return accMinBalBlowSchMinBalValue;
    }

    public void setAccMinBalBlowSchMinBalValue(String accMinBalBlowSchMinBalValue) {
        this.accMinBalBlowSchMinBalValue = accMinBalBlowSchMinBalValue;
    }

    public String getAllowDrUnclearBalance() {
        return allowDrUnclearBalance;
    }

    public void setAllowDrUnclearBalance(String allowDrUnclearBalance) {
        this.allowDrUnclearBalance = allowDrUnclearBalance;
    }

    public List<SelectItem> getAllowDrUnclearBalanceOption() {
        return allowDrUnclearBalanceOption;
    }

    public void setAllowDrUnclearBalanceOption(List<SelectItem> allowDrUnclearBalanceOption) {
        this.allowDrUnclearBalanceOption = allowDrUnclearBalanceOption;
    }

    public String getAllowSweep() {
        return allowSweep;
    }

    public void setAllowSweep(String allowSweep) {
        this.allowSweep = allowSweep;
    }

    public List<SelectItem> getAllowSweepOption() {
        return allowSweepOption;
    }

    public void setAllowSweepOption(List<SelectItem> allowSweepOption) {
        this.allowSweepOption = allowSweepOption;
    }

    public String getAllowedNoWithdrawls() {
        return allowedNoWithdrawls;
    }

    public void setAllowedNoWithdrawls(String allowedNoWithdrawls) {
        this.allowedNoWithdrawls = allowedNoWithdrawls;
    }

    public String getAndBalBet() {
        return andBalBet;
    }

    public void setAndBalBet(String andBalBet) {
        this.andBalBet = andBalBet;
    }

    public String getbIDrTranDormantAcct() {
        return bIDrTranDormantAcct;
    }

    public void setbIDrTranDormantAcct(String bIDrTranDormantAcct) {
        this.bIDrTranDormantAcct = bIDrTranDormantAcct;
    }

    public String getbIDrTranDormantAcctValue() {
        return bIDrTranDormantAcctValue;
    }

    public void setbIDrTranDormantAcctValue(String bIDrTranDormantAcctValue) {
        this.bIDrTranDormantAcctValue = bIDrTranDormantAcctValue;
    }

    public String getBalBet() {
        return balBet;
    }

    public void setBalBet(String balBet) {
        this.balBet = balBet;
    }

    public String getcIDrTranDormantAcct() {
        return cIDrTranDormantAcct;
    }

    public void setcIDrTranDormantAcct(String cIDrTranDormantAcct) {
        this.cIDrTranDormantAcct = cIDrTranDormantAcct;
    }

    public String getcIDrTranDormantAcctValue() {
        return cIDrTranDormantAcctValue;
    }

    public void setcIDrTranDormantAcctValue(String cIDrTranDormantAcctValue) {
        this.cIDrTranDormantAcctValue = cIDrTranDormantAcctValue;
    }

    public String getcITranInacAcc() {
        return cITranInacAcc;
    }

    public void setcITranInacAcc(String cITranInacAcc) {
        this.cITranInacAcc = cITranInacAcc;
    }

    public String getcITranInacAccValue() {
        return cITranInacAccValue;
    }

    public void setcITranInacAccValue(String cITranInacAccValue) {
        this.cITranInacAccValue = cITranInacAccValue;
    }

    public String getChqBkWithoutMinBal() {
        return chqBkWithoutMinBal;
    }

    public void setChqBkWithoutMinBal(String chqBkWithoutMinBal) {
        this.chqBkWithoutMinBal = chqBkWithoutMinBal;
    }

    public String getChqBkWithoutMinBalValue() {
        return chqBkWithoutMinBalValue;
    }

    public void setChqBkWithoutMinBalValue(String chqBkWithoutMinBalValue) {
        this.chqBkWithoutMinBalValue = chqBkWithoutMinBalValue;
    }

    public String getChqIssuedOnDormantAcct() {
        return chqIssuedOnDormantAcct;
    }

    public void setChqIssuedOnDormantAcct(String chqIssuedOnDormantAcct) {
        this.chqIssuedOnDormantAcct = chqIssuedOnDormantAcct;
    }

    public String getChqIssuedOnDormantAcctValue() {
        return chqIssuedOnDormantAcctValue;
    }

    public void setChqIssuedOnDormantAcctValue(String chqIssuedOnDormantAcctValue) {
        this.chqIssuedOnDormantAcctValue = chqIssuedOnDormantAcctValue;
    }

    public String getChqIssuedOnInactiveAcct() {
        return chqIssuedOnInactiveAcct;
    }

    public void setChqIssuedOnInactiveAcct(String chqIssuedOnInactiveAcct) {
        this.chqIssuedOnInactiveAcct = chqIssuedOnInactiveAcct;
    }

    public String getChqIssuedOnInactiveAcctValue() {
        return chqIssuedOnInactiveAcctValue;
    }

    public void setChqIssuedOnInactiveAcctValue(String chqIssuedOnInactiveAcctValue) {
        this.chqIssuedOnInactiveAcctValue = chqIssuedOnInactiveAcctValue;
    }

    public String getDormEvent() {
        return dormEvent;
    }

    public void setDormEvent(String dormEvent) {
        this.dormEvent = dormEvent;
    }

    public String getDormantAccAbnrmlTranLimit() {
        return dormantAccAbnrmlTranLimit;
    }

    public void setDormantAccAbnrmlTranLimit(String dormantAccAbnrmlTranLimit) {
        this.dormantAccAbnrmlTranLimit = dormantAccAbnrmlTranLimit;
    }

    public String getDrBalLim() {
        return drBalLim;
    }

    public void setDrBalLim(String drBalLim) {
        this.drBalLim = drBalLim;
    }

    public String getDrBalLimitExc() {
        return drBalLimitExc;
    }

    public void setDrBalLimitExc(String drBalLimitExc) {
        this.drBalLimitExc = drBalLimitExc;
    }

    public String getDrBalLimitExcValue() {
        return drBalLimitExcValue;
    }

    public void setDrBalLimitExcValue(String drBalLimitExcValue) {
        this.drBalLimitExcValue = drBalLimitExcValue;
    }

    public String getDurationFromactDorm() {
        return durationFromactDorm;
    }

    public void setDurationFromactDorm(String durationFromactDorm) {
        this.durationFromactDorm = durationFromactDorm;
    }

    public String getDurationMarkAccAct() {
        return durationMarkAccAct;
    }

    public void setDurationMarkAccAct(String durationMarkAccAct) {
        this.durationMarkAccAct = durationMarkAccAct;
    }

    public String getInacEvent() {
        return inacEvent;
    }

    public void setInacEvent(String inacEvent) {
        this.inacEvent = inacEvent;
    }

    public String getInactiveAccAbnrmlTranLimit() {
        return inactiveAccAbnrmlTranLimit;
    }

    public void setInactiveAccAbnrmlTranLimit(String inactiveAccAbnrmlTranLimit) {
        this.inactiveAccAbnrmlTranLimit = inactiveAccAbnrmlTranLimit;
    }

    public String getIntAvgBalance() {
        return intAvgBalance;
    }

    public void setIntAvgBalance(String intAvgBalance) {
        this.intAvgBalance = intAvgBalance;
    }

    public List<SelectItem> getIntAvgBalanceOption() {
        return intAvgBalanceOption;
    }

    public void setIntAvgBalanceOption(List<SelectItem> intAvgBalanceOption) {
        this.intAvgBalanceOption = intAvgBalanceOption;
    }

    public String getIntCalcLocalCalender() {
        return intCalcLocalCalender;
    }

    public void setIntCalcLocalCalender(String intCalcLocalCalender) {
        this.intCalcLocalCalender = intCalcLocalCalender;
    }

    public List<SelectItem> getIntCalcLocalCalenderOption() {
        return intCalcLocalCalenderOption;
    }

    public void setIntCalcLocalCalenderOption(List<SelectItem> intCalcLocalCalenderOption) {
        this.intCalcLocalCalenderOption = intCalcLocalCalenderOption;
    }

    public String getIntroducerNotCAHold() {
        return introducerNotCAHold;
    }

    public void setIntroducerNotCAHold(String introducerNotCAHold) {
        this.introducerNotCAHold = introducerNotCAHold;
    }

    public String getIntroducerNotCAHoldValue() {
        return introducerNotCAHoldValue;
    }

    public void setIntroducerNotCAHoldValue(String introducerNotCAHoldValue) {
        this.introducerNotCAHoldValue = introducerNotCAHoldValue;
    }

    public String getLedgerFolio1() {
        return ledgerFolio1;
    }

    public void setLedgerFolio1(String ledgerFolio1) {
        this.ledgerFolio1 = ledgerFolio1;
    }

    public String getMinBalChq() {
        return minBalChq;
    }

    public void setMinBalChq(String minBalChq) {
        this.minBalChq = minBalChq;
    }

    public String getNoWithdrawlsExceed() {
        return noWithdrawlsExceed;
    }

    public void setNoWithdrawlsExceed(String noWithdrawlsExceed) {
        this.noWithdrawlsExceed = noWithdrawlsExceed;
    }

    public String getNoWithdrawlsExceeded() {
        return noWithdrawlsExceeded;
    }

    public void setNoWithdrawlsExceeded(String noWithdrawlsExceeded) {
        this.noWithdrawlsExceeded = noWithdrawlsExceeded;
    }

    public String getNoWithdrawlsExceededValue() {
        return noWithdrawlsExceededValue;
    }

    public void setNoWithdrawlsExceededValue(String noWithdrawlsExceededValue) {
        this.noWithdrawlsExceededValue = noWithdrawlsExceededValue;
    }

    public List<SelectItem> getNoWithdrawlsExceedoption() {
        return noWithdrawlsExceedoption;
    }

    public void setNoWithdrawlsExceedoption(List<SelectItem> noWithdrawlsExceedoption) {
        this.noWithdrawlsExceedoption = noWithdrawlsExceedoption;
    }

    public String getServChrgWithdrawl() {
        return servChrgWithdrawl;
    }

    public void setServChrgWithdrawl(String servChrgWithdrawl) {
        this.servChrgWithdrawl = servChrgWithdrawl;
    }

    public String gettODMinorAcc() {
        return tODMinorAcc;
    }

    public void settODMinorAcc(String tODMinorAcc) {
        this.tODMinorAcc = tODMinorAcc;
    }

    public String gettODMinorAccValue() {
        return tODMinorAccValue;
    }

    public void settODMinorAccValue(String tODMinorAccValue) {
        this.tODMinorAccValue = tODMinorAccValue;
    }

    public void selectSPMDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeCaSbSchDetailsTO cbsSchemeCaSbSchDetailsTO = schemeMasterManagementDelegate.getDataIntoSBSchemeParamMain(schemeMaster.schemeCode);
             if (cbsSchemeCaSbSchDetailsTO.getCurrencyCodeType() != null || (!cbsSchemeCaSbSchDetailsTO.getCurrencyCodeType().equalsIgnoreCase(""))) {
               schemeMaster.setCurrencyType(cbsSchemeCaSbSchDetailsTO.getCurrencyCodeType());
            } else {
                this.schemeMaster.setCurrencyType("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getSchemeType()!= null || (!cbsSchemeCaSbSchDetailsTO.getSchemeType().equalsIgnoreCase(""))) {
               schemeMaster.setSchemeType(cbsSchemeCaSbSchDetailsTO.getSchemeType());
            } else {
                this.schemeMaster.setSchemeType("");
            }
           
            if (cbsSchemeCaSbSchDetailsTO.getAccClosedWithinAnYear() != null || (!cbsSchemeCaSbSchDetailsTO.getAccClosedWithinAnYear().equalsIgnoreCase(""))) {
                setAccClosedWithinYr(cbsSchemeCaSbSchDetailsTO.getAccClosedWithinAnYear());
            } else {
                this.setAccClosedWithinYr("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getAlwdNumberOfWthdrawals() != null || (!cbsSchemeCaSbSchDetailsTO.getAlwdNumberOfWthdrawals().equalsIgnoreCase(""))) {
                setAllowedNoWithdrawls(cbsSchemeCaSbSchDetailsTO.getAlwdNumberOfWthdrawals());
            } else {
                this.setAllowedNoWithdrawls("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getNoIntIfWithdrawalsExceed() != null || (!cbsSchemeCaSbSchDetailsTO.getNoIntIfWithdrawalsExceed().equalsIgnoreCase(""))) {
                setNoWithdrawlsExceed(cbsSchemeCaSbSchDetailsTO.getNoIntIfWithdrawalsExceed());
            } else {
                this.setNoWithdrawlsExceed("0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getMinBalanceForCheque() != null || (!cbsSchemeCaSbSchDetailsTO.getMinBalanceForCheque().toString().equalsIgnoreCase(""))) {
                setMinBalChq(cbsSchemeCaSbSchDetailsTO.getMinBalanceForCheque().toString());
            } else {
                this.setMinBalChq("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getDrBalanceLimit() != null || (!cbsSchemeCaSbSchDetailsTO.getDrBalanceLimit().toString().equalsIgnoreCase(""))) {
                setDrBalLim(cbsSchemeCaSbSchDetailsTO.getDrBalanceLimit().toString());
            } else {
                this.setDrBalLim("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getLedgerFolioChargeFolio() != null || (!cbsSchemeCaSbSchDetailsTO.getLedgerFolioChargeFolio().toString().equalsIgnoreCase(""))) {
                setLedgerFolio1(cbsSchemeCaSbSchDetailsTO.getLedgerFolioChargeFolio().toString());
            } else {
                this.setLedgerFolio1("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getServiceChargeExtraWithdrawal() != null || (!cbsSchemeCaSbSchDetailsTO.getServiceChargeExtraWithdrawal().toString().equalsIgnoreCase(""))) {
                setServChrgWithdrawl(cbsSchemeCaSbSchDetailsTO.getServiceChargeExtraWithdrawal().toString());
            } else {
                this.setServChrgWithdrawl("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getInactiveAccountAbmmlTranLimit() != null || (!cbsSchemeCaSbSchDetailsTO.getInactiveAccountAbmmlTranLimit().toString().equalsIgnoreCase(""))) {
                setInactiveAccAbnrmlTranLimit(cbsSchemeCaSbSchDetailsTO.getInactiveAccountAbmmlTranLimit().toString());
            } else {
                this.setInactiveAccAbnrmlTranLimit("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getDormantAccAbmmlTranLimit() != null || (!cbsSchemeCaSbSchDetailsTO.getDormantAccAbmmlTranLimit().toString().equalsIgnoreCase(""))) {
                setDormantAccAbnrmlTranLimit(cbsSchemeCaSbSchDetailsTO.getDormantAccAbmmlTranLimit().toString());
            } else {
                this.setDormantAccAbnrmlTranLimit("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getBalanceBetween() != null || (!cbsSchemeCaSbSchDetailsTO.getBalanceBetween().toString().equalsIgnoreCase(""))) {
                setBalBet(cbsSchemeCaSbSchDetailsTO.getBalanceBetween().toString());
            } else {
                this.setBalBet("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getBalanceAnd() != null || (!cbsSchemeCaSbSchDetailsTO.getBalanceAnd().toString().equalsIgnoreCase(""))) {
                setAndBalBet(cbsSchemeCaSbSchDetailsTO.getBalanceAnd().toString());
            } else {
                this.setAndBalBet("0.0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getDurationToMarkAccasInactive() != null || (!cbsSchemeCaSbSchDetailsTO.getDurationToMarkAccasInactive().equalsIgnoreCase(""))) {
                setDurationMarkAccAct(cbsSchemeCaSbSchDetailsTO.getDurationToMarkAccasInactive());
            } else {
                this.setDurationMarkAccAct("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getDurationToFromInactiveToDormat() != null || (!cbsSchemeCaSbSchDetailsTO.getDurationToFromInactiveToDormat().equalsIgnoreCase(""))) {
                setDurationFromactDorm(cbsSchemeCaSbSchDetailsTO.getDurationToFromInactiveToDormat());
            } else {
                this.setDurationFromactDorm("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getDormantChargeEvent() != null || (!cbsSchemeCaSbSchDetailsTO.getDormantChargeEvent().equalsIgnoreCase(""))) {
                setDormEvent(cbsSchemeCaSbSchDetailsTO.getDormantChargeEvent());
            } else {
                this.setDormEvent("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getInactiveChargeEvent() != null || (!cbsSchemeCaSbSchDetailsTO.getInactiveChargeEvent().equalsIgnoreCase(""))) {
                setInacEvent(cbsSchemeCaSbSchDetailsTO.getInactiveChargeEvent());
            } else {
                this.setInacEvent("");
            }
            if (cbsSchemeCaSbSchDetailsTO.getAllowSweeps() != null || (!cbsSchemeCaSbSchDetailsTO.getAllowSweeps().equalsIgnoreCase(""))) {
                setAllowSweep(cbsSchemeCaSbSchDetailsTO.getAllowSweeps());
            } else {
                this.setAllowSweep("0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getAllowDrAgainstUnclearBalance() != null || (!cbsSchemeCaSbSchDetailsTO.getAllowDrAgainstUnclearBalance().equalsIgnoreCase(""))) {
                setAllowDrUnclearBalance(cbsSchemeCaSbSchDetailsTO.getAllowDrAgainstUnclearBalance());
            } else {
                this.setAllowDrUnclearBalance("0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getIntCalculationOnLocalCalender() != null || (!cbsSchemeCaSbSchDetailsTO.getIntCalculationOnLocalCalender().equalsIgnoreCase(""))) {
                setIntCalcLocalCalender(cbsSchemeCaSbSchDetailsTO.getIntCalculationOnLocalCalender());
            } else {
                this.setIntCalcLocalCalender("0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getIntOnAvgOrMinBal() != null || (!cbsSchemeCaSbSchDetailsTO.getIntOnAvgOrMinBal().equalsIgnoreCase(""))) {
                setIntAvgBalance(cbsSchemeCaSbSchDetailsTO.getIntOnAvgOrMinBal());
            } else {
                this.setIntAvgBalance("0");
            }
            if (cbsSchemeCaSbSchDetailsTO.getDrBalLimit() != null || (!cbsSchemeCaSbSchDetailsTO.getDrBalLimit().toString().equalsIgnoreCase(""))) {
                setDrBalLimitExc(cbsSchemeCaSbSchDetailsTO.getDrBalLimit());
            } else {
                this.setDrBalLimitExc("");
            }
            getExcepDescDrBalLimit();
            if (cbsSchemeCaSbSchDetailsTO.getcITranToInactiveAccount() != null || (!cbsSchemeCaSbSchDetailsTO.getcITranToInactiveAccount().equalsIgnoreCase(""))) {
                setcITranInacAcc(cbsSchemeCaSbSchDetailsTO.getcITranToInactiveAccount());
            } else {
                this.setcITranInacAcc("");
            }
            getExcepDescCITranInacAcc();
            if (cbsSchemeCaSbSchDetailsTO.getcIDrTranToDormantAccount() != null || (!cbsSchemeCaSbSchDetailsTO.getcIDrTranToDormantAccount().equalsIgnoreCase(""))) {
                setcIDrTranDormantAcct(cbsSchemeCaSbSchDetailsTO.getcIDrTranToDormantAccount());
            } else {
                this.setcIDrTranDormantAcct("");
            }
            getExcepDescCITranInacAcc();
            if (cbsSchemeCaSbSchDetailsTO.getcIDrTranToDormantAccount() != null || (!cbsSchemeCaSbSchDetailsTO.getcIDrTranToDormantAccount().equalsIgnoreCase(""))) {
                setcIDrTranDormantAcct(cbsSchemeCaSbSchDetailsTO.getcIDrTranToDormantAccount());
            } else {
                this.setcIDrTranDormantAcct("");
            }
            getExcepDescCIDrTranDormantAcc();
            if (cbsSchemeCaSbSchDetailsTO.getbIDrTranToDormantAccount() != null || (!cbsSchemeCaSbSchDetailsTO.getbIDrTranToDormantAccount().equalsIgnoreCase(""))) {
                setbIDrTranDormantAcct(cbsSchemeCaSbSchDetailsTO.getbIDrTranToDormantAccount());
            } else {
                this.setbIDrTranDormantAcct("");
            }
            getExcepDescBIDrTranDormantAcc();
            if (cbsSchemeCaSbSchDetailsTO.getChequeIssuedOnInactiveAccount() != null || (!cbsSchemeCaSbSchDetailsTO.getChequeIssuedOnInactiveAccount().equalsIgnoreCase(""))) {
                setChqIssuedOnInactiveAcct(cbsSchemeCaSbSchDetailsTO.getChequeIssuedOnInactiveAccount());
            } else {
                this.setChqIssuedOnInactiveAcct("");
            }
            getExcepDescChqIssueInacAcc();
            if (cbsSchemeCaSbSchDetailsTO.getChequeIssuedOnDormantAccount() != null || (!cbsSchemeCaSbSchDetailsTO.getChequeIssuedOnDormantAccount().equalsIgnoreCase(""))) {
                setChqIssuedOnDormantAcct(cbsSchemeCaSbSchDetailsTO.getChequeIssuedOnDormantAccount());
            } else {
                this.setChqIssuedOnDormantAcct("");
            }
            getExcepDescChqIssueDorAcc();
            if (cbsSchemeCaSbSchDetailsTO.getChequeBookWithoutMinBalance() != null || (!cbsSchemeCaSbSchDetailsTO.getChequeBookWithoutMinBalance().equalsIgnoreCase(""))) {
                setChqBkWithoutMinBal(cbsSchemeCaSbSchDetailsTO.getChequeBookWithoutMinBalance());
            } else {
                this.setChqBkWithoutMinBal("");
            }
            getExcepDescChqBookMinBal();
            getExcepDescAccClosedWithinYr();
            if (cbsSchemeCaSbSchDetailsTO.getNoOfWithdrawalsExceeded() != null || (!cbsSchemeCaSbSchDetailsTO.getNoOfWithdrawalsExceeded().equalsIgnoreCase(""))) {
                setNoWithdrawlsExceeded(cbsSchemeCaSbSchDetailsTO.getNoOfWithdrawalsExceeded());
            } else {
                this.setNoWithdrawlsExceeded("");
            }
            getExcepDescNoWithdrawlsExceeded();
            if (cbsSchemeCaSbSchDetailsTO.gettODOnMinorAccount() != null || (!cbsSchemeCaSbSchDetailsTO.gettODOnMinorAccount().equalsIgnoreCase(""))) {
                settODMinorAcc(cbsSchemeCaSbSchDetailsTO.gettODOnMinorAccount());
            } else {
                this.settODMinorAcc("");
            }
            getExcepDescTodMinor();
            if (cbsSchemeCaSbSchDetailsTO.getIntroducerNotACAHolder() != null || (!cbsSchemeCaSbSchDetailsTO.getIntroducerNotACAHolder().equalsIgnoreCase(""))) {
                setIntroducerNotCAHold(cbsSchemeCaSbSchDetailsTO.getIntroducerNotACAHolder());
            } else {
                this.setIntroducerNotCAHold("");
            }
            getExcepDescIntroNotCAHold();
            if (cbsSchemeCaSbSchDetailsTO.getAccMinBalanceBelowSchemeMinBal() != null || (!cbsSchemeCaSbSchDetailsTO.getAccMinBalanceBelowSchemeMinBal().equalsIgnoreCase(""))) {
                setAccMinBalBlowSchMinBal(cbsSchemeCaSbSchDetailsTO.getAccMinBalanceBelowSchemeMinBal());
            } else {
                this.setAccMinBalBlowSchMinBal("");
            }
            getExcepDescAccMinBalBelowSchMinBal();
            
            
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescDrBalLimit() {
        this.schemeMaster.setMessage("");
        try {
            if (this.drBalLimitExc.equals("")) {
                this.setDrBalLimitExcValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.drBalLimitExc);
                this.setDrBalLimitExcValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setDrBalLimitExcValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescCIDrTranDormantAcc() {
        this.schemeMaster.setMessage("");
        try {
            if (this.cIDrTranDormantAcct.equals("")) {
                this.setcIDrTranDormantAcctValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cIDrTranDormantAcct);
                this.setcIDrTranDormantAcctValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setcIDrTranDormantAcctValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescBIDrTranDormantAcc() {
        this.schemeMaster.setMessage("");
        try {
            if (this.bIDrTranDormantAcct.equals("")) {
                this.setbIDrTranDormantAcctValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.bIDrTranDormantAcct);
                this.setbIDrTranDormantAcctValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setbIDrTranDormantAcctValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescCITranInacAcc() {
        this.schemeMaster.setMessage("");
        try {
            if (this.cITranInacAcc.equals("")) {
                this.setcITranInacAccValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.cITranInacAcc);
                this.setcITranInacAccValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setcITranInacAccValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescChqIssueInacAcc() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chqIssuedOnInactiveAcct.equals("")) {
                this.setChqIssuedOnInactiveAcctValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chqIssuedOnInactiveAcct);
                this.setChqIssuedOnInactiveAcctValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setChqIssuedOnInactiveAcctValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void getExcepDescChqIssueDorAcc() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chqIssuedOnDormantAcct.equals("")) {
                this.setChqIssuedOnDormantAcctValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chqIssuedOnDormantAcct);
                this.setChqIssuedOnDormantAcctValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setChqIssuedOnDormantAcctValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescChqBookMinBal() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chqBkWithoutMinBal.equals("")) {
                this.setChqBkWithoutMinBalValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chqBkWithoutMinBal);
                this.setChqBkWithoutMinBalValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setChqBkWithoutMinBalValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescAccClosedWithinYr() {
        this.schemeMaster.setMessage("");
        try {
            if (this.accClosedWithinYr.equals("")) {
                this.setAccClosedWithinYrValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.accClosedWithinYr);
                this.setAccClosedWithinYrValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setAccClosedWithinYrValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescNoWithdrawlsExceeded() {
        this.schemeMaster.setMessage("");
        try {
            if (this.noWithdrawlsExceeded.equals("")) {
                this.setNoWithdrawlsExceededValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.noWithdrawlsExceeded);
                this.setNoWithdrawlsExceededValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setNoWithdrawlsExceededValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescTodMinor() {
        this.schemeMaster.setMessage("");
        try {
            if (this.tODMinorAcc.equals("")) {
                this.settODMinorAccValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.tODMinorAcc);
                this.settODMinorAccValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.settODMinorAccValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescIntroNotCAHold() {
        this.schemeMaster.setMessage("");
        try {
            if (this.introducerNotCAHold.equals("")) {
                this.setIntroducerNotCAHoldValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.introducerNotCAHold);
                this.setIntroducerNotCAHoldValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setIntroducerNotCAHoldValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void getExcepDescAccMinBalBelowSchMinBal() {
        this.schemeMaster.setMessage("");
        try {
            if (this.accMinBalBlowSchMinBal.equals("")) {
                this.setAccMinBalBlowSchMinBalValue("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.accMinBalBlowSchMinBal);
                this.setAccMinBalBlowSchMinBalValue(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setAccMinBalBlowSchMinBalValue("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void refreshSPMForm() {
        schemeMaster.setCurrencyType(""); //correct
        setAllowedNoWithdrawls("");
        setNoWithdrawlsExceed("0");
        setMinBalChq("0.0");
        setDrBalLim("0.0");
        setLedgerFolio1("0.0");
        setServChrgWithdrawl("0.0");
        setInactiveAccAbnrmlTranLimit("0.0");
        setDormantAccAbnrmlTranLimit("0.0");
        setBalBet("0.0");
        setAndBalBet("0.0");
        setDurationMarkAccAct("");
        setDurationFromactDorm("");
        setDormEvent("");
        setInacEvent("");
        setAllowSweep("0");
        setAllowDrUnclearBalance("0");
        setIntCalcLocalCalender("0");
        setIntAvgBalance("0");
        setDrBalLimitExc("");
        setcITranInacAcc("");
        setcIDrTranDormantAcct("");
        setbIDrTranDormantAcct("");
        setChqIssuedOnInactiveAcct("");
        setChqIssuedOnDormantAcct("");
        setChqBkWithoutMinBal("");
        setAccClosedWithinYr("");
        setNoWithdrawlsExceeded("");
        settODMinorAcc("");
        setIntroducerNotCAHold("");
        setAccMinBalBlowSchMinBal("");
        setDrBalLimitExcValue("");
        setcITranInacAccValue("");
        setcIDrTranDormantAcctValue("");
        setbIDrTranDormantAcctValue("");
        setChqIssuedOnInactiveAcctValue("");
        setChqIssuedOnDormantAcctValue("");
        setChqBkWithoutMinBalValue("");
        setAccClosedWithinYrValue("");
        setNoWithdrawlsExceededValue("");
        settODMinorAccValue("");
        setIntroducerNotCAHoldValue("");
        setAccMinBalBlowSchMinBalValue("");
    }
    

    public void enableSPMForm() {
        disableFlag=false;
    }

    public void disableSPMForm() {
        disableFlag=true;
    }
}

