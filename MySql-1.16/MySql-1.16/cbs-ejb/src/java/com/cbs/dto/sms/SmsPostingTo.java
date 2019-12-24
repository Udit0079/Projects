/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import java.io.Serializable;
import java.math.BigInteger;

public class SmsPostingTo implements Serializable {

    private String acno;
    private String billingAcno;
    private BigInteger noOfMessage;
    private String fromDt;
    private String billingAcnoNature;
    private String custState;
    private String brState;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBillingAcno() {
        return billingAcno;
    }

    public void setBillingAcno(String billingAcno) {
        this.billingAcno = billingAcno;
    }

    public BigInteger getNoOfMessage() {
        return noOfMessage;
    }

    public void setNoOfMessage(BigInteger noOfMessage) {
        this.noOfMessage = noOfMessage;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
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
