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
public class FeeOrChargesDetailsTO implements Serializable {

    private String schemeCode;
    private String currencyCode;
    private String chargesType;
    private String amortize;
    private String chargesDesc;
    private String amortMethod;
    private String chargesEventId;
    private String drPlaceHolder;
    private String crPlaceHolder;
    private String assertOrDmd;
    private String deductible;
    private String considerForIrr;
    private String multipleFlag;
    private String delFlagFeeCharges;
    private String prePaymentFee;
    private String counterSaveUpdate;

    public String getAmortMethod() {
        return amortMethod;
    }

    public void setAmortMethod(String amortMethod) {
        this.amortMethod = amortMethod;
    }

    public String getAmortize() {
        return amortize;
    }

    public void setAmortize(String amortize) {
        this.amortize = amortize;
    }

    public String getAssertOrDmd() {
        return assertOrDmd;
    }

    public void setAssertOrDmd(String assertOrDmd) {
        this.assertOrDmd = assertOrDmd;
    }

    public String getChargesDesc() {
        return chargesDesc;
    }

    public void setChargesDesc(String chargesDesc) {
        this.chargesDesc = chargesDesc;
    }

    public String getChargesEventId() {
        return chargesEventId;
    }

    public void setChargesEventId(String chargesEventId) {
        this.chargesEventId = chargesEventId;
    }

    public String getChargesType() {
        return chargesType;
    }

    public void setChargesType(String chargesType) {
        this.chargesType = chargesType;
    }

    public String getConsiderForIrr() {
        return considerForIrr;
    }

    public void setConsiderForIrr(String considerForIrr) {
        this.considerForIrr = considerForIrr;
    }

    public String getCounterSaveUpdate() {
        return counterSaveUpdate;
    }

    public void setCounterSaveUpdate(String counterSaveUpdate) {
        this.counterSaveUpdate = counterSaveUpdate;
    }

    public String getCrPlaceHolder() {
        return crPlaceHolder;
    }

    public void setCrPlaceHolder(String crPlaceHolder) {
        this.crPlaceHolder = crPlaceHolder;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDeductible() {
        return deductible;
    }

    public void setDeductible(String deductible) {
        this.deductible = deductible;
    }

    public String getDelFlagFeeCharges() {
        return delFlagFeeCharges;
    }

    public void setDelFlagFeeCharges(String delFlagFeeCharges) {
        this.delFlagFeeCharges = delFlagFeeCharges;
    }

    public String getDrPlaceHolder() {
        return drPlaceHolder;
    }

    public void setDrPlaceHolder(String drPlaceHolder) {
        this.drPlaceHolder = drPlaceHolder;
    }

    public String getMultipleFlag() {
        return multipleFlag;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public String getPrePaymentFee() {
        return prePaymentFee;
    }

    public void setPrePaymentFee(String prePaymentFee) {
        this.prePaymentFee = prePaymentFee;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }
}
