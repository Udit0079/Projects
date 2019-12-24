/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.sms;

/**
 *
 * @author root
 */
public class MessageDetailPojo {

    private String messageType;
    private Integer totalNoOfMessages;
    private double chargePerMessage;
    private double totalCharge;

    public double getChargePerMessage() {
        return chargePerMessage;
    }

    public void setChargePerMessage(double chargePerMessage) {
        this.chargePerMessage = chargePerMessage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Integer getTotalNoOfMessages() {
        return totalNoOfMessages;
    }

    public void setTotalNoOfMessages(Integer totalNoOfMessages) {
        this.totalNoOfMessages = totalNoOfMessages;
    }
}
