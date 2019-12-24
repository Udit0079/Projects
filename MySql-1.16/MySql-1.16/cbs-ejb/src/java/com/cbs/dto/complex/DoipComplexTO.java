/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.complex;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class DoipComplexTO implements Serializable {

    private String schemeCode;
    private String schemeType;
    private String currencyCode;
    private String overDueGlSubHeadCode;
    private String overDueInterestCode;
    private String overDueIntTblCodeType;
    private String overDueintCalcMethod;
    private String renewalPerdExcd;
    private String maxPerd;
    private String maxAmt;
    private String minorDepPreclosure;
    private String extension;
    private String splCatgClosure;
    private String matAmtTolerance;
    private String nilPenalty;
    private String disContinuedInst;
    private String transferIn;
    private String acctVerBalCheck;
    private String systemDrTransAllowed;
    private String dupReprntRcpt;
    private String preMatureClosure;
    private String noticePerdMinNoticePerd;
    private String defaultValueForPreIntChgd;
    private String backValueDateAccOpen;
    private String futureValueDateAccOpen;

    public String getAcctVerBalCheck() {
        return acctVerBalCheck;
    }

    public void setAcctVerBalCheck(String acctVerBalCheck) {
        this.acctVerBalCheck = acctVerBalCheck;
    }

    public String getBackValueDateAccOpen() {
        return backValueDateAccOpen;
    }

    public void setBackValueDateAccOpen(String backValueDateAccOpen) {
        this.backValueDateAccOpen = backValueDateAccOpen;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDefaultValueForPreIntChgd() {
        return defaultValueForPreIntChgd;
    }

    public void setDefaultValueForPreIntChgd(String defaultValueForPreIntChgd) {
        this.defaultValueForPreIntChgd = defaultValueForPreIntChgd;
    }

    public String getDisContinuedInst() {
        return disContinuedInst;
    }

    public void setDisContinuedInst(String disContinuedInst) {
        this.disContinuedInst = disContinuedInst;
    }

    public String getDupReprntRcpt() {
        return dupReprntRcpt;
    }

    public void setDupReprntRcpt(String dupReprntRcpt) {
        this.dupReprntRcpt = dupReprntRcpt;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFutureValueDateAccOpen() {
        return futureValueDateAccOpen;
    }

    public void setFutureValueDateAccOpen(String futureValueDateAccOpen) {
        this.futureValueDateAccOpen = futureValueDateAccOpen;
    }

    public String getMatAmtTolerance() {
        return matAmtTolerance;
    }

    public void setMatAmtTolerance(String matAmtTolerance) {
        this.matAmtTolerance = matAmtTolerance;
    }

    public String getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(String maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getMaxPerd() {
        return maxPerd;
    }

    public void setMaxPerd(String maxPerd) {
        this.maxPerd = maxPerd;
    }

    public String getMinorDepPreclosure() {
        return minorDepPreclosure;
    }

    public void setMinorDepPreclosure(String minorDepPreclosure) {
        this.minorDepPreclosure = minorDepPreclosure;
    }

    public String getNilPenalty() {
        return nilPenalty;
    }

    public void setNilPenalty(String nilPenalty) {
        this.nilPenalty = nilPenalty;
    }

    public String getNoticePerdMinNoticePerd() {
        return noticePerdMinNoticePerd;
    }

    public void setNoticePerdMinNoticePerd(String noticePerdMinNoticePerd) {
        this.noticePerdMinNoticePerd = noticePerdMinNoticePerd;
    }

    public String getOverDueGlSubHeadCode() {
        return overDueGlSubHeadCode;
    }

    public void setOverDueGlSubHeadCode(String overDueGlSubHeadCode) {
        this.overDueGlSubHeadCode = overDueGlSubHeadCode;
    }

    public String getOverDueIntTblCodeType() {
        return overDueIntTblCodeType;
    }

    public void setOverDueIntTblCodeType(String overDueIntTblCodeType) {
        this.overDueIntTblCodeType = overDueIntTblCodeType;
    }

    public String getOverDueInterestCode() {
        return overDueInterestCode;
    }

    public void setOverDueInterestCode(String overDueInterestCode) {
        this.overDueInterestCode = overDueInterestCode;
    }

    public String getOverDueintCalcMethod() {
        return overDueintCalcMethod;
    }

    public void setOverDueintCalcMethod(String overDueintCalcMethod) {
        this.overDueintCalcMethod = overDueintCalcMethod;
    }

    public String getPreMatureClosure() {
        return preMatureClosure;
    }

    public void setPreMatureClosure(String preMatureClosure) {
        this.preMatureClosure = preMatureClosure;
    }

    public String getRenewalPerdExcd() {
        return renewalPerdExcd;
    }

    public void setRenewalPerdExcd(String renewalPerdExcd) {
        this.renewalPerdExcd = renewalPerdExcd;
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

    public String getSplCatgClosure() {
        return splCatgClosure;
    }

    public void setSplCatgClosure(String splCatgClosure) {
        this.splCatgClosure = splCatgClosure;
    }

    public String getSystemDrTransAllowed() {
        return systemDrTransAllowed;
    }

    public void setSystemDrTransAllowed(String systemDrTransAllowed) {
        this.systemDrTransAllowed = systemDrTransAllowed;
    }

    public String getTransferIn() {
        return transferIn;
    }

    public void setTransferIn(String transferIn) {
        this.transferIn = transferIn;
    }
}
