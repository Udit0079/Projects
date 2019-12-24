/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author root
 */
public class NeftRtgsReportPojo {

    private String beneAccount;
    private String beneName;
    private String receiverIfsc;
    private String aadharNo;
    private BigDecimal amount;
    private String narration;
    private String senderAccount;
    private String senderName;
    private String senderIfsc;
    private String details;
    private String branchName;
    private String branchAdd;
    //private String tranDt;
    private Date tranDt;
    private String utrNo;
    private String trSno;
    private String settleDt;
    //Addition of ECS DR
    private String acctcode;
    private String micr;
    private String achItemSeqNo;
    private String fileSeqNo;
    private String tranRef;
    private String acValFlag;
    private String returnReason;
    private String fileName;
    private String umrn;

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getSettleDt() {
        return settleDt;
    }

    public void setSettleDt(String settleDt) {
        this.settleDt = settleDt;
    }

    public String getTrSno() {
        return trSno;
    }

    public void setTrSno(String trSno) {
        this.trSno = trSno;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public Date getTranDt() {
        return tranDt;
    }

    public void setTranDt(Date tranDt) {
        this.tranDt = tranDt;
    }

//    public String getTranDt() {
//        return tranDt;
//    }
//
//    public void setTranDt(String tranDt) {
//        this.tranDt = tranDt;
//    }
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

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public String getSenderIfsc() {
        return senderIfsc;
    }

    public void setSenderIfsc(String senderIfsc) {
        this.senderIfsc = senderIfsc;
    }

    public String getBranchAdd() {
        return branchAdd;
    }

    public void setBranchAdd(String branchAdd) {
        this.branchAdd = branchAdd;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getAcctcode() {
        return acctcode;
    }

    public void setAcctcode(String acctcode) {
        this.acctcode = acctcode;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getAchItemSeqNo() {
        return achItemSeqNo;
    }

    public void setAchItemSeqNo(String achItemSeqNo) {
        this.achItemSeqNo = achItemSeqNo;
    }

    public String getFileSeqNo() {
        return fileSeqNo;
    }

    public void setFileSeqNo(String fileSeqNo) {
        this.fileSeqNo = fileSeqNo;
    }

    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(String tranRef) {
        this.tranRef = tranRef;
    }

    public String getAcValFlag() {
        return acValFlag;
    }

    public void setAcValFlag(String acValFlag) {
        this.acValFlag = acValFlag;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }
    
}
