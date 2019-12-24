/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.txn;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class AccountStatus implements Serializable {

    String acno;
    String remark;
    String date;
    String spflag;
    String auth;
    String authBy;
    String enterBy;
    String baseAcno;
    String effDate;
    int sno;
    String spFlagCode;
    long spNo;
    String oldStatus;
    String custName;
    String oldStatusCode;

    public String getSpFlagCode() {
        return spFlagCode;
    }

    public void setSpFlagCode(String spFlagCode) {
        this.spFlagCode = spFlagCode;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBaseAcno() {
        return baseAcno;
    }

    public void setBaseAcno(String baseAcno) {
        this.baseAcno = baseAcno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpflag() {
        return spflag;
    }

    public void setSpflag(String spflag) {
        this.spflag = spflag;
    }

    public long getSpNo() {
        return spNo;
    }

    public void setSpNo(long spNo) {
        this.spNo = spNo;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOldStatusCode() {
        return oldStatusCode;
    }

    public void setOldStatusCode(String oldStatusCode) {
        this.oldStatusCode = oldStatusCode;
    }
}
