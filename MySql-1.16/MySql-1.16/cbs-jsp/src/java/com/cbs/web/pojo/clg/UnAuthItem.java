/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.clg;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class UnAuthItem implements Serializable {
    //EncryptText has been left from function

    private Integer sNo;
    private Integer batchNo;
    private Integer vchNo;
    private String acNo;
    private String custName;
    private double instAmt;
    private String instNo;
    private String instDt;
    private String prBankCode;
    private Integer status;
    private String operMode;
    private String enterBy;
    private String details;
    private String payee;
    private String bankName;
    private String bankAddress;
    private String txnId;
    private String orgnCode;
    private String destCode;
    private Float seqNo;
    private String authBy;
    private Integer payBy;
    private Integer ty;
    private String screenFlag;
    private String secondAuthBy;
    private String circleType;
    private Integer reasonForCancel;
    private String tranTime;
    private String dt;
    private Integer tranType;
    private String subStatus;
    private Integer requestType;
    private String imageCode;
    private String varStatus;
    private String exceedFlag;
    private String custState;
    private String branchState;
    private String docType;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVarStatus() {
        return varStatus;
    }

    public void setVarStatus(String varStatus) {
        this.varStatus = varStatus;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    public String getCircleType() {
        return circleType;
    }

    public void setCircleType(String circleType) {
        this.circleType = circleType;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getOrgnCode() {
        return orgnCode;
    }

    public void setOrgnCode(String orgnCode) {
        this.orgnCode = orgnCode;
    }

    public Integer getPayBy() {
        return payBy;
    }

    public void setPayBy(Integer payBy) {
        this.payBy = payBy;
    }

    public Integer getReasonForCancel() {
        return reasonForCancel;
    }

    public void setReasonForCancel(Integer reasonForCancel) {
        this.reasonForCancel = reasonForCancel;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getScreenFlag() {
        return screenFlag;
    }

    public void setScreenFlag(String screenFlag) {
        this.screenFlag = screenFlag;
    }

    public String getSecondAuthBy() {
        return secondAuthBy;
    }

    public void setSecondAuthBy(String secondAuthBy) {
        this.secondAuthBy = secondAuthBy;
    }

    public Float getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Float seqNo) {
        this.seqNo = seqNo;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public Integer getTranType() {
        return tranType;
    }

    public void setTranType(Integer tranType) {
        this.tranType = tranType;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public Integer getVchNo() {
        return vchNo;
    }

    public void setVchNo(Integer vchNo) {
        this.vchNo = vchNo;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public double getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(double instAmt) {
        this.instAmt = instAmt;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPrBankCode() {
        return prBankCode;
    }

    public void setPrBankCode(String prBankCode) {
        this.prBankCode = prBankCode;
    }

    public String getExceedFlag() {
        return exceedFlag;
    }

    public void setExceedFlag(String exceedFlag) {
        this.exceedFlag = exceedFlag;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getBranchState() {
        return branchState;
    }

    public void setBranchState(String branchState) {
        this.branchState = branchState;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
