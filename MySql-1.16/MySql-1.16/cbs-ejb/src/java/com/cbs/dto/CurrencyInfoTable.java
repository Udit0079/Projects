/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

/**
 *
 * @author root
 */
public class CurrencyInfoTable {

    String currencyCode;
    String withHoldingTaxLevel;
    String withHoldTaxPer;
    String withHoldTaxLim;
    String preferInterestTillDate;
    String tdsOperativeAccIdEdit;
    String saveUpdateFlag;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCustIntRateCredit() {
        return custIntRateCredit;
    }

    public void setCustIntRateCredit(String custIntRateCredit) {
        this.custIntRateCredit = custIntRateCredit;
    }

    public String getCustIntRateDebit() {
        return custIntRateDebit;
    }

    public void setCustIntRateDebit(String custIntRateDebit) {
        this.custIntRateDebit = custIntRateDebit;
    }

    public String getPreferInterestTillDate() {
        return preferInterestTillDate;
    }

    public void setPreferInterestTillDate(String preferInterestTillDate) {
        this.preferInterestTillDate = preferInterestTillDate;
    }

    public String getTdsOperativeAccIdEdit() {
        return tdsOperativeAccIdEdit;
    }

    public void setTdsOperativeAccIdEdit(String tdsOperativeAccIdEdit) {
        this.tdsOperativeAccIdEdit = tdsOperativeAccIdEdit;
    }

    public String getWithHoldTaxLim() {
        return withHoldTaxLim;
    }

    public void setWithHoldTaxLim(String withHoldTaxLim) {
        this.withHoldTaxLim = withHoldTaxLim;
    }

    public String getWithHoldTaxPer() {
        return withHoldTaxPer;
    }

    public void setWithHoldTaxPer(String withHoldTaxPer) {
        this.withHoldTaxPer = withHoldTaxPer;
    }

    public String getWithHoldingTaxLevel() {
        return withHoldingTaxLevel;
    }

    public void setWithHoldingTaxLevel(String withHoldingTaxLevel) {
        this.withHoldingTaxLevel = withHoldingTaxLevel;
    }

    public String getSaveUpdateFlag() {
        return saveUpdateFlag;
    }

    public void setSaveUpdateFlag(String saveUpdateFlag) {
        this.saveUpdateFlag = saveUpdateFlag;
    }
    
    String custIntRateCredit;
    String custIntRateDebit;
}
