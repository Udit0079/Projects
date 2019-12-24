/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class AtmReversalPostingPojo implements Serializable {

    private String reversalIndicater;
    private double amount;
    private String stNo;
    private String ostNo;
    private String cardNo;
    private String tranDate;
    private String incomingDate;
    private String processFlag;
    private String processStatus;

    public String getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(String processFlag) {
        this.processFlag = processFlag;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(String incomingDate) {
        this.incomingDate = incomingDate;
    }

    public String getOstNo() {
        return ostNo;
    }

    public void setOstNo(String ostNo) {
        this.ostNo = ostNo;
    }

    public String getReversalIndicater() {
        return reversalIndicater;
    }

    public void setReversalIndicater(String reversalIndicater) {
        this.reversalIndicater = reversalIndicater;
    }

    public String getStNo() {
        return stNo;
    }

    public void setStNo(String stNo) {
        this.stNo = stNo;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }
}
