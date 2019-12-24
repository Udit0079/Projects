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
public class NeftDataReconsilationPojo implements Serializable {

    private String tranDate;
    private String valueDate;
    private String chqNo;
    private String txnDetail;
    private BigDecimal amount;
    private String drCr;
    private BigDecimal balance;
    private String branch;
    private String remitterName;
    private String remitterAcNo;
    private String beneficaryName;
    private String beneficaryAcNo;
    private String status;
    private String transcationDate;
    private String neftRtgsData;
    private float trsNo;

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getChqNo() {
        return chqNo;
    }

    public void setChqNo(String chqNo) {
        this.chqNo = chqNo;
    }

    public String getTxnDetail() {
        return txnDetail;
    }

    public void setTxnDetail(String txnDetail) {
        this.txnDetail = txnDetail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    public String getRemitterAcNo() {
        return remitterAcNo;
    }

    public void setRemitterAcNo(String remitterAcNo) {
        this.remitterAcNo = remitterAcNo;
    }

    public String getBeneficaryName() {
        return beneficaryName;
    }

    public void setBeneficaryName(String beneficaryName) {
        this.beneficaryName = beneficaryName;
    }

    public String getBeneficaryAcNo() {
        return beneficaryAcNo;
    }

    public void setBeneficaryAcNo(String beneficaryAcNo) {
        this.beneficaryAcNo = beneficaryAcNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTranscationDate() {
        return transcationDate;
    }

    public void setTranscationDate(String transcationDate) {
        this.transcationDate = transcationDate;
    }

    public float getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(float trsNo) {
        this.trsNo = trsNo;
    }

    public String getNeftRtgsData() {
        return neftRtgsData;
    }

    public void setNeftRtgsData(String neftRtgsData) {
        this.neftRtgsData = neftRtgsData;
    }
}
