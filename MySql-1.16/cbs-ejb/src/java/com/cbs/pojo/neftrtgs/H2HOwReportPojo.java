/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class H2HOwReportPojo implements Serializable {

    private String paymentType;
    private String uCustRefNo;
    private String beneName;
    private String beneCode;
    private BigDecimal amount;
    private String paymentDt;
    private String crAccountNo;
    private String ifscCode;
    private String drAccountNo;
    private String cmsRefNo;
    private String utrNo;
    private String status;
    private String rejectReason;
    private String reversalReason;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBeneCode() {
        return beneCode;
    }

    public void setBeneCode(String beneCode) {
        this.beneCode = beneCode;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getCmsRefNo() {
        return cmsRefNo;
    }

    public void setCmsRefNo(String cmsRefNo) {
        this.cmsRefNo = cmsRefNo;
    }

    public String getCrAccountNo() {
        return crAccountNo;
    }

    public void setCrAccountNo(String crAccountNo) {
        this.crAccountNo = crAccountNo;
    }

    public String getDrAccountNo() {
        return drAccountNo;
    }

    public void setDrAccountNo(String drAccountNo) {
        this.drAccountNo = drAccountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPaymentDt() {
        return paymentDt;
    }

    public void setPaymentDt(String paymentDt) {
        this.paymentDt = paymentDt;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getuCustRefNo() {
        return uCustRefNo;
    }

    public void setuCustRefNo(String uCustRefNo) {
        this.uCustRefNo = uCustRefNo;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public String getReversalReason() {
        return reversalReason;
    }

    public void setReversalReason(String reversalReason) {
        this.reversalReason = reversalReason;
    }
}
