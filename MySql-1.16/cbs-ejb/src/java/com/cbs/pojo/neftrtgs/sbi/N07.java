/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs.sbi;

import java.io.Serializable;

public class N07 implements Serializable {

    private String headerTranRefNo;
    private String totalNo;
    private String totalAmount;
    private String detailTranRefNo;
    private String relatedRefNo;
    private String amount;
    private String valueDt;
    private String sendingIfsc;
    private String sendingAccountNo;
    private String senderName;
    private String originatorOfRemittance;
    private String beneficiaryIfsc;
    private String beneficiaryAccountNo;
    private String beneficiaryName;
    private String reasonCode;
    private String reasonDesc;
    private String endIdentifier;

    public N07() {
    }

    @Override
    public String toString() {
        return (headerTranRefNo + "\n" + totalNo + "\n" + totalAmount + "\n" + detailTranRefNo + "\n" + relatedRefNo
                + "\n" + amount + "\n" + valueDt + "\n" + sendingIfsc + "\n" + sendingAccountNo + "\n" + senderName + "\n" + originatorOfRemittance
                + "\n" + beneficiaryIfsc + "\n" + beneficiaryAccountNo + "\n" + beneficiaryName + "\n" + reasonCode + "\n" + reasonDesc + "\n" + endIdentifier);
    }

    //Getter And Setter
    public String getHeaderTranRefNo() {
        return headerTranRefNo;
    }

    public void setHeaderTranRefNo(String headerTranRefNo) {
        this.headerTranRefNo = headerTranRefNo;
    }

    public String getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(String totalNo) {
        this.totalNo = totalNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDetailTranRefNo() {
        return detailTranRefNo;
    }

    public void setDetailTranRefNo(String detailTranRefNo) {
        this.detailTranRefNo = detailTranRefNo;
    }

    public String getRelatedRefNo() {
        return relatedRefNo;
    }

    public void setRelatedRefNo(String relatedRefNo) {
        this.relatedRefNo = relatedRefNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getValueDt() {
        return valueDt;
    }

    public void setValueDt(String valueDt) {
        this.valueDt = valueDt;
    }

    public String getSendingIfsc() {
        return sendingIfsc;
    }

    public void setSendingIfsc(String sendingIfsc) {
        this.sendingIfsc = sendingIfsc;
    }

    public String getSendingAccountNo() {
        return sendingAccountNo;
    }

    public void setSendingAccountNo(String sendingAccountNo) {
        this.sendingAccountNo = sendingAccountNo;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getOriginatorOfRemittance() {
        return originatorOfRemittance;
    }

    public void setOriginatorOfRemittance(String originatorOfRemittance) {
        this.originatorOfRemittance = originatorOfRemittance;
    }

    public String getBeneficiaryIfsc() {
        return beneficiaryIfsc;
    }

    public void setBeneficiaryIfsc(String beneficiaryIfsc) {
        this.beneficiaryIfsc = beneficiaryIfsc;
    }

    public String getBeneficiaryAccountNo() {
        return beneficiaryAccountNo;
    }

    public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
        this.beneficiaryAccountNo = beneficiaryAccountNo;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public String getEndIdentifier() {
        return endIdentifier;
    }

    public void setEndIdentifier(String endIdentifier) {
        this.endIdentifier = endIdentifier;
    }
}
