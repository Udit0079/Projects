/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs.sbi;

import java.io.Serializable;

public class R41 implements Serializable {

//    private String beginIdentifier;
    private String tranRefNo;
    private String valueDtCurrencyAndAmount;
    private String orderingCustomer;
    private String customerMobileOrEmail;
    private String senderIfsc;
    private String receiverIfsc;
    private String senderAccount;
    private String beneficiaryCustomer;
    private String paymentDetail;
    private String senderToReceiverInfo;
    private String endIdentifier;

    public R41() {
    }

    @Override
    public String toString() {
        return (tranRefNo + "\n" + valueDtCurrencyAndAmount + "\n" + orderingCustomer + "\n"
                + customerMobileOrEmail + "\n" + senderIfsc + "\n" + receiverIfsc + "\n"
                + senderAccount + "\n" + beneficiaryCustomer + "\n" + paymentDetail + "\n"
                + senderToReceiverInfo + "\n" + endIdentifier);
    }
    //Getter And Setter

    public String getTranRefNo() {
        return tranRefNo;
    }

    public void setTranRefNo(String tranRefNo) {
        this.tranRefNo = tranRefNo;
    }

    public String getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(String orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public String getBeneficiaryCustomer() {
        return beneficiaryCustomer;
    }

    public void setBeneficiaryCustomer(String beneficiaryCustomer) {
        this.beneficiaryCustomer = beneficiaryCustomer;
    }

    public String getEndIdentifier() {
        return endIdentifier;
    }

    public void setEndIdentifier(String endIdentifier) {
        this.endIdentifier = endIdentifier;
    }

    public String getValueDtCurrencyAndAmount() {
        return valueDtCurrencyAndAmount;
    }

    public void setValueDtCurrencyAndAmount(String valueDtCurrencyAndAmount) {
        this.valueDtCurrencyAndAmount = valueDtCurrencyAndAmount;
    }

    public String getCustomerMobileOrEmail() {
        return customerMobileOrEmail;
    }

    public void setCustomerMobileOrEmail(String customerMobileOrEmail) {
        this.customerMobileOrEmail = customerMobileOrEmail;
    }

    public String getSenderIfsc() {
        return senderIfsc;
    }

    public void setSenderIfsc(String senderIfsc) {
        this.senderIfsc = senderIfsc;
    }

    public String getReceiverIfsc() {
        return receiverIfsc;
    }

    public void setReceiverIfsc(String receiverIfsc) {
        this.receiverIfsc = receiverIfsc;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public String getSenderToReceiverInfo() {
        return senderToReceiverInfo;
    }

    public void setSenderToReceiverInfo(String senderToReceiverInfo) {
        this.senderToReceiverInfo = senderToReceiverInfo;
    }
}
