/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Sudhir
 */
public class InventoryTransferReceiptTable implements Serializable {

    private String invtClass,
            invntType,
            invtSrNo,
            invntLocClass,
            invntDesc,
            userID,
            authBy,
            tranDate,
            branch,
            bankName,
            bankAddress;

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

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getInvntDesc() {
        return invntDesc;
    }

    public void setInvntDesc(String invntDesc) {
        this.invntDesc = invntDesc;
    }

    public String getInvntLocClass() {
        return invntLocClass;
    }

    public void setInvntLocClass(String invntLocClass) {
        this.invntLocClass = invntLocClass;
    }

    public String getInvntType() {
        return invntType;
    }

    public void setInvntType(String invntType) {
        this.invntType = invntType;
    }

    public String getInvtClass() {
        return invtClass;
    }

    public void setInvtClass(String invtClass) {
        this.invtClass = invtClass;
    }

    public String getInvtSrNo() {
        return invtSrNo;
    }

    public void setInvtSrNo(String invtSrNo) {
        this.invtSrNo = invtSrNo;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
