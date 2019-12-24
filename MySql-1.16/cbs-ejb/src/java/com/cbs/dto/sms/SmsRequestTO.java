/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import com.cbs.sms.service.SmsType;
import java.io.Serializable;
import java.math.BigDecimal;

public class SmsRequestTO implements Serializable {

    private static final long serialVersionUID = 54692750311563142L;
    private SmsType smsTemplate;
    private String acNo;
    private BigDecimal amount;
    private Integer tranType;
    private Integer ty;
    private String mobile;
    private String bankName;
    private String txnDt;
    private String balance;
    private String messageType;
    private String actualAcNo;
    private String promoMsg;
    private String pin;
    private String pullCode;
    private String services;
    private String miniStatement;
    private String firstCheque;
    private String lastCheque;
    //Added for minimum balance sms
    private String moduleName;
    //Added For ATM Sms
    private String userMsgId;
    //For UserName in promotional
    private String userName;
    private String details;
    private String pendingBalance;

    public SmsType getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(SmsType smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTranType() {
        return tranType;
    }

    public void setTranType(Integer tranType) {
        this.tranType = tranType;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(String txnDt) {
        this.txnDt = txnDt;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getActualAcNo() {
        return actualAcNo;
    }

    public void setActualAcNo(String actualAcNo) {
        this.actualAcNo = actualAcNo;
    }

    public String getPromoMsg() {
        return promoMsg;
    }

    public void setPromoMsg(String promoMsg) {
        this.promoMsg = promoMsg;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPullCode() {
        return pullCode;
    }

    public void setPullCode(String pullCode) {
        this.pullCode = pullCode;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getMiniStatement() {
        return miniStatement;
    }

    public void setMiniStatement(String miniStatement) {
        this.miniStatement = miniStatement;
    }

    public String getFirstCheque() {
        return firstCheque;
    }

    public void setFirstCheque(String firstCheque) {
        this.firstCheque = firstCheque;
    }

    public String getLastCheque() {
        return lastCheque;
    }

    public void setLastCheque(String lastCheque) {
        this.lastCheque = lastCheque;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUserMsgId() {
        return userMsgId;
    }

    public void setUserMsgId(String userMsgId) {
        this.userMsgId = userMsgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(String pendingBalance) {
        this.pendingBalance = pendingBalance;
    }

    @Override
    public String toString() {
        return "SmsRequestTO [acNo=" + acNo + "amount, =" + amount + ", tranType="
                + tranType + ", ty=" + ty + ", mobile=" + mobile + ", bankName="
                + bankName + "]";
    }
}
