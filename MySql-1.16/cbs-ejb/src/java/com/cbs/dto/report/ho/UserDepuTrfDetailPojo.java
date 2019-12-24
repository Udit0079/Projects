/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class UserDepuTrfDetailPojo implements Serializable{
    private String userId;
    private String userName;
    private String address;
    private String curBranch;
    private String originBranch;
    private String frDate;
    private String toDate;
    private String deputeXfr;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurBranch() {
        return curBranch;
    }

    public void setCurBranch(String curBranch) {
        this.curBranch = curBranch;
    }

    public String getFrDate() {
        return frDate;
    }

    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getOriginBranch() {
        return originBranch;
    }

    public void setOriginBranch(String originBranch) {
        this.originBranch = originBranch;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeputeXfr() {
        return deputeXfr;
    }

    public void setDeputeXfr(String deputeXfr) {
        this.deputeXfr = deputeXfr;
    }
         
}
