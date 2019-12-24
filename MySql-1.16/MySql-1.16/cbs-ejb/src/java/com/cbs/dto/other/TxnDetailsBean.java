/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.other;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class TxnDetailsBean implements Serializable {

    int srNo;
    int emflag;
    int scheduleNo;
    BigDecimal amount;
    String instDate;
    String chqNo;
    String image;
    String rbiReferenceNo;
    String txnId;
    String prBankCode;
    String prBankName;
    String prBankAddress;
    String inFavourOf ;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChqNo() {
        return chqNo;
    }

    public void setChqNo(String chqNo) {
        this.chqNo = chqNo;
    }

    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getPrBankCode() {
        return prBankCode;
    }

    public void setPrBankCode(String prBankCode) {
        this.prBankCode = prBankCode;
    }

    public String getPrBankName() {
        return prBankName;
    }

    public void setPrBankName(String prBankName) {
        this.prBankName = prBankName;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRbiReferenceNo() {
        return rbiReferenceNo;
    }

    public void setRbiReferenceNo(String rbiReferenceNo) {
        this.rbiReferenceNo = rbiReferenceNo;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public int getEmflag() {
        return emflag;
    }

    public void setEmflag(int emflag) {
        this.emflag = emflag;
    }

    public int getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getPrBankAddress() {
        return prBankAddress;
    }

    public void setPrBankAddress(String prBankAddress) {
        this.prBankAddress = prBankAddress;
    }

    public String getInFavourOf() {
        return inFavourOf;
    }

    public void setInFavourOf(String inFavourOf) {
        this.inFavourOf = inFavourOf;
    }
  }
