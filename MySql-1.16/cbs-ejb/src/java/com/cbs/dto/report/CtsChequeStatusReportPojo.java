/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 *
 * @author root
 */
public class CtsChequeStatusReportPojo {

    private String bnkName;
    private String bnkAdd;
    private int brncode;
    private String acType;
    private String destBranch;
    private BigInteger batchNo;
    private String acno;
    private String custName;
    private String chequeNo;
    private BigDecimal amount;
    private String favourOf;
    private String details;
    private String instDt;
    private int status;
    private String userDetails;
    private java.sql.Timestamp tranTime;
    private String prbankCode;
    private String branchName;
    private String instNo;
    private String instAmt;
    private String txnId;
    private String itemSAN;
    private String EmFlag;
    private String dt;
    private String scheduleNo;
    private String enterBy;
    private String seqNo;
    private String operMode;
    private String itemTransCode;
    private String itemSeqNo;
    private String modifyFlag;
    private String itemPayorBnkRoutNo;
    private String imageCode;

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigInteger getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(BigInteger batchNo) {
        this.batchNo = batchNo;
    }

    public String getBnkAdd() {
        return bnkAdd;
    }

    public void setBnkAdd(String bnkAdd) {
        this.bnkAdd = bnkAdd;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBrncode() {
        return brncode;
    }

    public void setBrncode(int brncode) {
        this.brncode = brncode;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDestBranch() {
        return destBranch;
    }

    public void setDestBranch(String destBranch) {
        this.destBranch = destBranch;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFavourOf() {
        return favourOf;
    }

    public void setFavourOf(String favourOf) {
        this.favourOf = favourOf;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getPrbankCode() {
        return prbankCode;
    }

    public void setPrbankCode(String prbankCode) {
        this.prbankCode = prbankCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getTranTime() {
        return tranTime;
    }

    public void setTranTime(Timestamp tranTime) {
        this.tranTime = tranTime;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
        this.instAmt = instAmt;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getItemSAN() {
        return itemSAN;
    }

    public void setItemSAN(String itemSAN) {
        this.itemSAN = itemSAN;
    }

    public String getEmFlag() {
        return EmFlag;
    }

    public void setEmFlag(String EmFlag) {
        this.EmFlag = EmFlag;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public String getItemTransCode() {
        return itemTransCode;
    }

    public void setItemTransCode(String ItemTransCode) {
        this.itemTransCode = ItemTransCode;
    }

    public String getItemSeqNo() {
        return itemSeqNo;
    }

    public void setItemSeqNo(String ItemSeqNo) {
        this.itemSeqNo = ItemSeqNo;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public String getItemPayorBnkRoutNo() {
        return itemPayorBnkRoutNo;
    }

    public void setItemPayorBnkRoutNo(String itemPayorBnkRoutNo) {
        this.itemPayorBnkRoutNo = itemPayorBnkRoutNo;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
