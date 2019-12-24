package com.cbs.sms.service;

import java.io.Serializable;

/**
 * @author ahada
 *
 */
public class SmsMessage implements Serializable {

    private static final long serialVersionUID = 5945301926931354110L;
    private String messageType;
    private String actualAcNo;
    private String mobileNo;
    private String message;
    private MimeType mimeType;
    //Addition for minimum balance sms
    private String moduleName;
    //Addition for ATM SMS
    private String userMsgId;
    //Addition of User Name
    private String userName;
    //For outside request message
    private String idNo;

    public SmsMessage() {
    }

    public SmsMessage(String messageType, String actualAcNo, String mobileNo,
            String message, MimeType mimeType, String moduleName, String userMsgId, String userName) {
        this.messageType = messageType;
        this.actualAcNo = actualAcNo;
        this.mobileNo = mobileNo;
        this.message = message;
        this.mimeType = mimeType;
        this.moduleName = moduleName;
        this.userMsgId = userMsgId;
        this.userName = userName;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isValid() {
        return (mobileNo != null) && (message != null) && (mimeType != null);
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

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("SmsMessage[");
        builder.append("messageType: ").append(messageType).append("\n");
        builder.append("actualAcNo: ").append(actualAcNo).append("\n");
        builder.append("mobileNo: ").append(mobileNo).append("\n");
        builder.append("message: ").append(message).append("\n");
        builder.append("mimeType: ").append(mimeType).append("\n");
        builder.append("moduleName: ").append(moduleName).append("\n");
        builder.append("userMsgId: ").append(userMsgId).append("\n");
        builder.append("userName: ").append(userName).append("]");
        return builder.toString();
    }
}
