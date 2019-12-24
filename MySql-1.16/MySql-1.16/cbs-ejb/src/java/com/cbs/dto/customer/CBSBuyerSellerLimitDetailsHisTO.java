/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ANKIT VERMA
 */
public class CBSBuyerSellerLimitDetailsHisTO implements Serializable{
     private String customerId;
   
    private String draweeCode;
   
    private String billType;
   
    private String staus;
   
    private String impCurrencyCodeCCY;
   
    private Double importLimit;
   
    private Double utilisedImportLimit;
   
    private Double buyLimit;
   
    private Double utilisedBuyLimit;
   
   
    private String expCurrencyCodeCCY;
   
    private Double exportLimit;
   
    private Double utilisedExportLimit;
   
    private Double sellLimit;
   
    private Double utilisedSellLimit;
   
    private String lastChangeUserID;
   
    private Date lastChangeTime;
   
    private String recordCreaterID;
   
    private Long txnid;

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public Double getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Double buyLimit) {
        this.buyLimit = buyLimit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDraweeCode() {
        return draweeCode;
    }

    public void setDraweeCode(String draweeCode) {
        this.draweeCode = draweeCode;
    }

    public String getExpCurrencyCodeCCY() {
        return expCurrencyCodeCCY;
    }

    public void setExpCurrencyCodeCCY(String expCurrencyCodeCCY) {
        this.expCurrencyCodeCCY = expCurrencyCodeCCY;
    }

    public Double getExportLimit() {
        return exportLimit;
    }

    public void setExportLimit(Double exportLimit) {
        this.exportLimit = exportLimit;
    }

    public String getImpCurrencyCodeCCY() {
        return impCurrencyCodeCCY;
    }

    public void setImpCurrencyCodeCCY(String impCurrencyCodeCCY) {
        this.impCurrencyCodeCCY = impCurrencyCodeCCY;
    }

    public Double getImportLimit() {
        return importLimit;
    }

    public void setImportLimit(Double importLimit) {
        this.importLimit = importLimit;
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

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public Double getSellLimit() {
        return sellLimit;
    }

    public void setSellLimit(Double sellLimit) {
        this.sellLimit = sellLimit;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    public Double getUtilisedBuyLimit() {
        return utilisedBuyLimit;
    }

    public void setUtilisedBuyLimit(Double utilisedBuyLimit) {
        this.utilisedBuyLimit = utilisedBuyLimit;
    }

    public Double getUtilisedExportLimit() {
        return utilisedExportLimit;
    }

    public void setUtilisedExportLimit(Double utilisedExportLimit) {
        this.utilisedExportLimit = utilisedExportLimit;
    }

    public Double getUtilisedImportLimit() {
        return utilisedImportLimit;
    }

    public void setUtilisedImportLimit(Double utilisedImportLimit) {
        this.utilisedImportLimit = utilisedImportLimit;
    }

    public Double getUtilisedSellLimit() {
        return utilisedSellLimit;
    }

    public void setUtilisedSellLimit(Double utilisedSellLimit) {
        this.utilisedSellLimit = utilisedSellLimit;
    }

    
}
