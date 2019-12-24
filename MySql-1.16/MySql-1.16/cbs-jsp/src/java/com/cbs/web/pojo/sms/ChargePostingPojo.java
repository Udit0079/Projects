/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.sms;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChargePostingPojo implements Serializable {

    private String acno;
    private String messageType;
    private int noOfMsg;
    private String fromDate;
    private String toDate;
    private BigDecimal charges;
    private BigDecimal serCharges;
    private int sno;
    private String crGlHead;
    private String billingAcno;
    private String billingAcnoNature;
    private String custState;
    private String brState;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getNoOfMsg() {
        return noOfMsg;
    }

    public void setNoOfMsg(int noOfMsg) {
        this.noOfMsg = noOfMsg;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getCrGlHead() {
        return crGlHead;
    }

    public void setCrGlHead(String crGlHead) {
        this.crGlHead = crGlHead;
    }

    public BigDecimal getSerCharges() {
        return serCharges;
    }

    public void setSerCharges(BigDecimal serCharges) {
        this.serCharges = serCharges;
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
