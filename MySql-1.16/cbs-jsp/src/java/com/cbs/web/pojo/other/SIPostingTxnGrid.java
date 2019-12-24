/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.other;

/**
 *
 * @author root
 */
public class SIPostingTxnGrid {
    private String fromAcno;
    private String toAcno;
    private int sNo;
    private float instrNo;
    private float amount;
    private String effDate;
    private String status;
    private String remarks;
    private String enterBy;
    private String entryDate;
    private String chargeFlag;
    private String expiryDt;
    private String fromCustName;
    private String toCustName;

    public String getFromCustName() {
        return fromCustName;
    }

    public void setFromCustName(String fromCustName) {
        this.fromCustName = fromCustName;
    }

    public String getToCustName() {
        return toCustName;
    }

    public void setToCustName(String toCustName) {
        this.toCustName = toCustName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getChargeFlag() {
        return chargeFlag;
    }

    public void setChargeFlag(String chargeFlag) {
        this.chargeFlag = chargeFlag;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(String expiryDt) {
        this.expiryDt = expiryDt;
    }

    public String getFromAcno() {
        return fromAcno;
    }

    public void setFromAcno(String fromAcno) {
        this.fromAcno = fromAcno;
    }

    public float getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(float instrNo) {
        this.instrNo = instrNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToAcno() {
        return toAcno;
    }

    public void setToAcno(String toAcno) {
        this.toAcno = toAcno;
    }
    

}
