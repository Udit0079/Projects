/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class SharePaymentPojo implements Serializable {

    private String folioNo;
    private String shareHolderName, payorder, advice;
    private int certNo;
    private Date paymentDt;
    private int noOfShare;
    private double shareAmt;
    private int fromNo;
    private int toNO;
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getCertNo() {
        return certNo;
    }

    public void setCertNo(int certNo) {
        this.certNo = certNo;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public int getFromNo() {
        return fromNo;
    }

    public void setFromNo(int fromNo) {
        this.fromNo = fromNo;
    }

    public int getNoOfShare() {
        return noOfShare;
    }

    public void setNoOfShare(int noOfShare) {
        this.noOfShare = noOfShare;
    }

    public Date getPaymentDt() {
        return paymentDt;
    }

    public void setPaymentDt(Date paymentDt) {
        this.paymentDt = paymentDt;
    }

    public String getPayorder() {
        return payorder;
    }

    public void setPayorder(String payorder) {
        this.payorder = payorder;
    }

    public double getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(double shareAmt) {
        this.shareAmt = shareAmt;
    }

    public String getShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(String shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public int getToNO() {
        return toNO;
    }

    public void setToNO(int toNO) {
        this.toNO = toNO;
    }
}
