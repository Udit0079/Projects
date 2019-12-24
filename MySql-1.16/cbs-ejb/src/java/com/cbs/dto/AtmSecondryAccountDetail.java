/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AtmSecondryAccountDetail implements Serializable {

    private String primaryAccount;
    private String cardNo;
    private String secondaryAccount;
    private String txnLimitType;
    private BigDecimal withdrawalLimitAmount;
    private Integer withdrawalLimitCount;
    private BigDecimal purchaseLimitAmount;
    private Integer purchaseLimitCount;
    private BigDecimal trfLimitAmount;
    private Integer trfLimitCount;
    private BigDecimal commonLimitAmount;
    private Integer commonLimitCount;

    public String getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(String primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSecondaryAccount() {
        return secondaryAccount;
    }

    public void setSecondaryAccount(String secondaryAccount) {
        this.secondaryAccount = secondaryAccount;
    }

    public String getTxnLimitType() {
        return txnLimitType;
    }

    public void setTxnLimitType(String txnLimitType) {
        this.txnLimitType = txnLimitType;
    }

    public BigDecimal getWithdrawalLimitAmount() {
        return withdrawalLimitAmount;
    }

    public void setWithdrawalLimitAmount(BigDecimal withdrawalLimitAmount) {
        this.withdrawalLimitAmount = withdrawalLimitAmount;
    }

    public Integer getWithdrawalLimitCount() {
        return withdrawalLimitCount;
    }

    public void setWithdrawalLimitCount(Integer withdrawalLimitCount) {
        this.withdrawalLimitCount = withdrawalLimitCount;
    }

    public BigDecimal getPurchaseLimitAmount() {
        return purchaseLimitAmount;
    }

    public void setPurchaseLimitAmount(BigDecimal purchaseLimitAmount) {
        this.purchaseLimitAmount = purchaseLimitAmount;
    }

    public Integer getPurchaseLimitCount() {
        return purchaseLimitCount;
    }

    public void setPurchaseLimitCount(Integer purchaseLimitCount) {
        this.purchaseLimitCount = purchaseLimitCount;
    }

    public BigDecimal getTrfLimitAmount() {
        return trfLimitAmount;
    }

    public void setTrfLimitAmount(BigDecimal trfLimitAmount) {
        this.trfLimitAmount = trfLimitAmount;
    }

    public Integer getTrfLimitCount() {
        return trfLimitCount;
    }

    public void setTrfLimitCount(Integer trfLimitCount) {
        this.trfLimitCount = trfLimitCount;
    }

    public BigDecimal getCommonLimitAmount() {
        return commonLimitAmount;
    }

    public void setCommonLimitAmount(BigDecimal commonLimitAmount) {
        this.commonLimitAmount = commonLimitAmount;
    }

    public Integer getCommonLimitCount() {
        return commonLimitCount;
    }

    public void setCommonLimitCount(Integer commonLimitCount) {
        this.commonLimitCount = commonLimitCount;
    }
}
