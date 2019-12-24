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
public class CBSCustDeliveryChannelDetailsTO implements Serializable{
      private String customerId;
    
    private String aTMDebitCardHolder;
   
    private String creditCardEnabled;
   
    private String teleBankingEnabled;
   
    private String smsBankingEnabled;
   
    private String iNetBankingEnabled;
    
    private String mobileBankingEnabled;
    
    private String selfServiceEnabled;
    
    private String eCSEnabled;
    
    private String lastChangeUserID;
    
    private Date lastChangeTime;
    
    private String recordCreaterID;
    
    private Date creationTime;
    
    private String tsCnt;

    public String getaTMDebitCardHolder() {
        return aTMDebitCardHolder;
    }

    public void setaTMDebitCardHolder(String aTMDebitCardHolder) {
        this.aTMDebitCardHolder = aTMDebitCardHolder;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreditCardEnabled() {
        return creditCardEnabled;
    }

    public void setCreditCardEnabled(String creditCardEnabled) {
        this.creditCardEnabled = creditCardEnabled;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String geteCSEnabled() {
        return eCSEnabled;
    }

    public void seteCSEnabled(String eCSEnabled) {
        this.eCSEnabled = eCSEnabled;
    }

    public String getiNetBankingEnabled() {
        return iNetBankingEnabled;
    }

    public void setiNetBankingEnabled(String iNetBankingEnabled) {
        this.iNetBankingEnabled = iNetBankingEnabled;
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

    public String getMobileBankingEnabled() {
        return mobileBankingEnabled;
    }

    public void setMobileBankingEnabled(String mobileBankingEnabled) {
        this.mobileBankingEnabled = mobileBankingEnabled;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public String getSelfServiceEnabled() {
        return selfServiceEnabled;
    }

    public void setSelfServiceEnabled(String selfServiceEnabled) {
        this.selfServiceEnabled = selfServiceEnabled;
    }

    public String getSmsBankingEnabled() {
        return smsBankingEnabled;
    }

    public void setSmsBankingEnabled(String smsBankingEnabled) {
        this.smsBankingEnabled = smsBankingEnabled;
    }

    public String getTeleBankingEnabled() {
        return teleBankingEnabled;
    }

    public void setTeleBankingEnabled(String teleBankingEnabled) {
        this.teleBankingEnabled = teleBankingEnabled;
    }

    public String getTsCnt() {
        return tsCnt;
    }

    public void setTsCnt(String tsCnt) {
        this.tsCnt = tsCnt;
    }
    
    

}
