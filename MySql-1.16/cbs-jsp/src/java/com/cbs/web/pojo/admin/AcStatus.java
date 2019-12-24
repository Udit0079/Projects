/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class AcStatus implements Serializable{
    private String acctNo;
    private String remark;
    private String spFlag;
    private String date;
    private String amount;
    private String auth;
    private String enterBy;
    private String efftDate;

    public String getSpFlag() {
        return spFlag;
    }

    public void setSpFlag(String spFlag) {
        this.spFlag = spFlag;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEfftDate() {
        return efftDate;
    }

    public void setEfftDate(String efftDate) {
        this.efftDate = efftDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
