/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs;

import java.math.BigDecimal;

public class ExcelReaderPojo {

    public String copBankAccNo;
    public String ifsccode;
    public String utr;
    public String beneAccount;
    public String receiverIfsc;
    public BigDecimal amount;
    public String timestamp;
    public String senderAcc;
    public String senderIfsc;
    public String senderName;
    public String senderAddOne;
    public String senderAddTwo;
    public String txnType;
    public String beneName;
    public String beneAdd;
    public String receiverBankName;
    public String receiverBankCode;
    public String receiverBankAddress;
    public String sponsorBankName;
    public String sponsorBankCode;
    public String sponsorIfsc;
    public String sponsorRefNo;
    public String sponsorAddress;
    public String senderBankName;
    public String senderBankCode;
    public String reasonCode;
    public String reason;
    public String remitInfo;
    public String remittanceOriginator;
    //New addition for SBI
    public String txnAmount;
    public String headerUtr;
    public String batchTime;
    public String relatedRefNo;
    public String senderAcype;
    public String beneficiaryAcType;
    public String rejectReason;
    public String remittanceDate;
    public String returnTranRefNo;
    //Addition of file name.
    public String iwFileName;
    public String txnId;
    public String valueDt;
    private boolean selected;
    private String tranDate;
    public String narration ;
    //Addition of Axis bulk neft file upload
    public String instNo;
    public String instDt;
    private String batchMode;
    
    public String getRemitInfo() {
        return remitInfo;
    }

    public void setRemitInfo(String remitInfo) {
        this.remitInfo = remitInfo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBeneAccount() {
        return beneAccount;
    }

    public void setBeneAccount(String beneAccount) {
        this.beneAccount = beneAccount;
    }

    public String getBeneAdd() {
        return beneAdd;
    }

    public void setBeneAdd(String beneAdd) {
        this.beneAdd = beneAdd;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getCopBankAccNo() {
        return copBankAccNo;
    }

    public void setCopBankAccNo(String copBankAccNo) {
        this.copBankAccNo = copBankAccNo;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getReceiverIfsc() {
        return receiverIfsc;
    }

    public void setReceiverIfsc(String receiverIfsc) {
        this.receiverIfsc = receiverIfsc;
    }

    public String getSenderAcc() {
        return senderAcc;
    }

    public void setSenderAcc(String senderAcc) {
        this.senderAcc = senderAcc;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddOne() {
        return senderAddOne;
    }

    public void setSenderAddOne(String senderAddOne) {
        this.senderAddOne = senderAddOne;
    }

    public String getSenderAddTwo() {
        return senderAddTwo;
    }

    public void setSenderAddTwo(String senderAddTwo) {
        this.senderAddTwo = senderAddTwo;
    }

    public String getSenderIfsc() {
        return senderIfsc;
    }

    public void setSenderIfsc(String senderIfsc) {
        this.senderIfsc = senderIfsc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getReceiverBankAddress() {
        return receiverBankAddress;
    }

    public void setReceiverBankAddress(String receiverBankAddress) {
        this.receiverBankAddress = receiverBankAddress;
    }

    public String getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getSenderBankCode() {
        return senderBankCode;
    }

    public void setSenderBankCode(String senderBankCode) {
        this.senderBankCode = senderBankCode;
    }

    public String getSenderBankName() {
        return senderBankName;
    }

    public void setSenderBankName(String senderBankName) {
        this.senderBankName = senderBankName;
    }

    public String getSponsorAddress() {
        return sponsorAddress;
    }

    public void setSponsorAddress(String sponsorAddress) {
        this.sponsorAddress = sponsorAddress;
    }

    public String getSponsorBankCode() {
        return sponsorBankCode;
    }

    public void setSponsorBankCode(String sponsorBankCode) {
        this.sponsorBankCode = sponsorBankCode;
    }

    public String getSponsorBankName() {
        return sponsorBankName;
    }

    public void setSponsorBankName(String sponsorBankName) {
        this.sponsorBankName = sponsorBankName;
    }

    public String getSponsorIfsc() {
        return sponsorIfsc;
    }

    public void setSponsorIfsc(String sponsorIfsc) {
        this.sponsorIfsc = sponsorIfsc;
    }

    public String getSponsorRefNo() {
        return sponsorRefNo;
    }

    public void setSponsorRefNo(String sponsorRefNo) {
        this.sponsorRefNo = sponsorRefNo;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemittanceOriginator() {
        return remittanceOriginator;
    }

    public void setRemittanceOriginator(String remittanceOriginator) {
        this.remittanceOriginator = remittanceOriginator;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getHeaderUtr() {
        return headerUtr;
    }

    public void setHeaderUtr(String headerUtr) {
        this.headerUtr = headerUtr;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getRelatedRefNo() {
        return relatedRefNo;
    }

    public void setRelatedRefNo(String relatedRefNo) {
        this.relatedRefNo = relatedRefNo;
    }

    public String getSenderAcype() {
        return senderAcype;
    }

    public void setSenderAcype(String senderAcype) {
        this.senderAcype = senderAcype;
    }

    public String getBeneficiaryAcType() {
        return beneficiaryAcType;
    }

    public void setBeneficiaryAcType(String beneficiaryAcType) {
        this.beneficiaryAcType = beneficiaryAcType;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRemittanceDate() {
        return remittanceDate;
    }

    public void setRemittanceDate(String remittanceDate) {
        this.remittanceDate = remittanceDate;
    }

    public String getReturnTranRefNo() {
        return returnTranRefNo;
    }

    public void setReturnTranRefNo(String returnTranRefNo) {
        this.returnTranRefNo = returnTranRefNo;
    }

    public String getIwFileName() {
        return iwFileName;
    }

    public void setIwFileName(String iwFileName) {
        this.iwFileName = iwFileName;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getValueDt() {
        return valueDt;
    }

    public void setValueDt(String valueDt) {
        this.valueDt = valueDt;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getBatchMode() {
        return batchMode;
    }

    public void setBatchMode(String batchMode) {
        this.batchMode = batchMode;
    }
    
    
}
