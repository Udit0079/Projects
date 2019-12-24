/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ankit Verma
 */
public class MbChargePostingIndividualHistoryTO implements Serializable {

    private String acno;
    private String billingAcno;
    private Date fromDate;
    private Date toDate;
    private double drAmount;
    private long noOfMessage;
    private String messageType;
    private String enterBy;
    private Date tranDt;
    private long mbPushTxnId;
    private double trsNo;
    private double serCharge;
    private String billingAcnoNature;
    private String custState;
    private String brState;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public double getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(double drAmount) {
        this.drAmount = drAmount;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public long getMbPushTxnId() {
        return mbPushTxnId;
    }

    public void setMbPushTxnId(long mbPushTxnId) {
        this.mbPushTxnId = mbPushTxnId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public long getNoOfMessage() {
        return noOfMessage;
    }

    public void setNoOfMessage(long noOfMessage) {
        this.noOfMessage = noOfMessage;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getTranDt() {
        return tranDt;
    }

    public void setTranDt(Date tranDt) {
        this.tranDt = tranDt;
    }

    public double getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(double trsNo) {
        this.trsNo = trsNo;
    }

    public double getSerCharge() {
        return serCharge;
    }

    public void setSerCharge(double serCharge) {
        this.serCharge = serCharge;
    }

    public String getBillingAcno() {
        return billingAcno;
    }

    public void setBillingAcno(String billingAcno) {
        this.billingAcno = billingAcno;
    }

    public String getBillingAcnoNature() {
        return billingAcnoNature;
    }

    public void setBillingAcnoNature(String billingAcnoNature) {
        this.billingAcnoNature = billingAcnoNature;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getBrState() {
        return brState;
    }

    public void setBrState(String brState) {
        this.brState = brState;
    }
    
}
