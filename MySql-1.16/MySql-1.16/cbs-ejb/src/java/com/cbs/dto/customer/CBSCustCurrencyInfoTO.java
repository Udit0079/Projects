/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ankit Verma
 */
public class CBSCustCurrencyInfoTO implements Serializable{
    private CBSCustCurrencyInfoPKTO cBSCustCurrencyInfoPKTO;
    
    private String withHoldingTaxLevel;
    
    private Double withHoldingTax;
    
    private Double withHoldingTaxLimit;
    
    private String tDSOperativeAccountID;
    
    private Double custinterestRateCredit;
    
    private Double custInterestRateDebit;
    
    private Date preferentialInterestTillDate;
    
    private String lastChangeUserID;
    
    private Date lastChangeTime;
    
    private String recordCreaterID;
    
    private Date creationTime;
    
    private String tsCnt;

    public CBSCustCurrencyInfoPKTO getcBSCustCurrencyInfoPKTO() {
        return cBSCustCurrencyInfoPKTO;
    }

    public void setcBSCustCurrencyInfoPKTO(CBSCustCurrencyInfoPKTO cBSCustCurrencyInfoPKTO) {
        this.cBSCustCurrencyInfoPKTO = cBSCustCurrencyInfoPKTO;
    }

 
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Double getCustInterestRateDebit() {
        return custInterestRateDebit;
    }

    public void setCustInterestRateDebit(Double custInterestRateDebit) {
        this.custInterestRateDebit = custInterestRateDebit;
    }

    public Double getCustinterestRateCredit() {
        return custinterestRateCredit;
    }

    public void setCustinterestRateCredit(Double custinterestRateCredit) {
        this.custinterestRateCredit = custinterestRateCredit;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public Date getPreferentialInterestTillDate() {
        return preferentialInterestTillDate;
    }

    public void setPreferentialInterestTillDate(Date preferentialInterestTillDate) {
        this.preferentialInterestTillDate = preferentialInterestTillDate;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public String gettDSOperativeAccountID() {
        return tDSOperativeAccountID;
    }

    public void settDSOperativeAccountID(String tDSOperativeAccountID) {
        this.tDSOperativeAccountID = tDSOperativeAccountID;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }

    public Double getWithHoldingTax() {
        return withHoldingTax;
    }

    public void setWithHoldingTax(Double withHoldingTax) {
        this.withHoldingTax = withHoldingTax;
    }

    public String getWithHoldingTaxLevel() {
        return withHoldingTaxLevel;
    }

    public void setWithHoldingTaxLevel(String withHoldingTaxLevel) {
        this.withHoldingTaxLevel = withHoldingTaxLevel;
    }

    public Double getWithHoldingTaxLimit() {
        return withHoldingTaxLimit;
    }

    public void setWithHoldingTaxLimit(Double withHoldingTaxLimit) {
        this.withHoldingTaxLimit = withHoldingTaxLimit;
    }

    
}
