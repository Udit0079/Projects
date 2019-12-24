/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;
/**
 *
 * @author Admin
 */
public class IBCOBCAuthGrid implements Serializable{

    int sNo;
    String billType;
    String acNo;
    String partyName;
    String instNo;
    String instAmt;
    String instDt;
    String enterBy;
    String auth;
    String colBankname;
    String colBankAdd;
    String bankName;
    String bankAdd;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getColBankAdd() {
        return colBankAdd;
    }

    public void setColBankAdd(String colBankAdd) {
        this.colBankAdd = colBankAdd;
    }

    public String getColBankname() {
        return colBankname;
    }

    public void setColBankname(String colBankname) {
        this.colBankname = colBankname;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

  

}
