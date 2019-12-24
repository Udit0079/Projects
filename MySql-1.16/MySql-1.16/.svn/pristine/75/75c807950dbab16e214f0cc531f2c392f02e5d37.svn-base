package com.cbs.email.service;

import com.cbs.sms.service.MimeType;
import java.io.Serializable;

public class MailMessage implements Serializable {

    private static final long serialVersionUID = 5945301926931354110L;
    private String serviceType;
    private String custId;
    private String fromAddress;
    private String fromName;
    private String toAddress;
    private String subject;
    private String body;
    private MimeType mimeType;
    private String periodFromDt;
    private String periodToDt;
    private String ccAddress;
    private String stmtDueDt;

    public MailMessage(String serviceType, String custId, String fromAddress, String fromName, String toAddress,
            String subject, String body, MimeType mimeType, String periodFromDt, String periodToDt) {
        this.serviceType = serviceType;
        this.custId = custId;
        this.fromAddress = fromAddress;
        this.fromName = fromName;
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;
        this.mimeType = mimeType;
        this.periodFromDt = periodFromDt;
        this.periodToDt = periodToDt;
    }

    public MailMessage(String serviceType, String custId, String fromAddress, String fromName, String toAddress,
            String subject, String body, MimeType mimeType, String periodFromDt, String periodToDt, String ccAddress,
            String stmtDueDt) {
        this(serviceType, custId, fromAddress, fromName, toAddress, subject, body, mimeType, periodFromDt, periodToDt);
        this.ccAddress = ccAddress;
        this.stmtDueDt = stmtDueDt;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public String getSubject() {
        return subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    public boolean isValid() {
        return (fromAddress != null) && (toAddress != null) && (subject != null) && (body != null)
                && (mimeType != null);
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromName() {
        return fromName;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPeriodFromDt() {
        return periodFromDt;
    }

    public void setPeriodFromDt(String periodFromDt) {
        this.periodFromDt = periodFromDt;
    }

    public String getPeriodToDt() {
        return periodToDt;
    }

    public void setPeriodToDt(String periodToDt) {
        this.periodToDt = periodToDt;
    }

    public String getStmtDueDt() {
        return stmtDueDt;
    }

    public void setStmtDueDt(String stmtDueDt) {
        this.stmtDueDt = stmtDueDt;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MailMessage[");
        builder.append("serviceType: ").append(serviceType).append("\n");
        builder.append("custId: ").append(custId).append("\n");
        builder.append("fromAddress: ").append(fromAddress).append("\n");
        builder.append("fromName: ").append(fromName).append("\n");
        builder.append("toAddress: ").append(toAddress).append("\n");
        builder.append("subject: ").append(subject).append("\n");
        builder.append("body: ").append(body).append("\n");
        builder.append("mimeType: ").append(mimeType).append("\n");
        builder.append("periodFromDt: ").append(periodFromDt).append("\n");
        builder.append("periodToDt: ").append(periodToDt).append("\n");
        builder.append("ccAddress: ").append(ccAddress).append("\n");
        builder.append("stmtDueDt: ").append(stmtDueDt).append("]");

        return builder.toString();
    }
}
