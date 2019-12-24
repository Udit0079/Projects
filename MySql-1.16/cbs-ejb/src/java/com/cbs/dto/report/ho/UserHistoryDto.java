/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserHistoryDto implements Serializable {

    private String userid;
    private String role;
    private String username;
    private String status;
    private BigDecimal cashDrLimit;
    private BigDecimal trfDrLimit;
    private BigDecimal clgDrLimit;
    private String address;
    private String updateDt;
    private String operatingBranch;
    private String flag;
    private String pwdReSet;
    private String enterBy;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getCashDrLimit() {
        return cashDrLimit;
    }

    public void setCashDrLimit(BigDecimal cashDrLimit) {
        this.cashDrLimit = cashDrLimit;
    }

    public BigDecimal getTrfDrLimit() {
        return trfDrLimit;
    }

    public void setTrfDrLimit(BigDecimal trfDrLimit) {
        this.trfDrLimit = trfDrLimit;
    }

    public BigDecimal getClgDrLimit() {
        return clgDrLimit;
    }

    public void setClgDrLimit(BigDecimal clgDrLimit) {
        this.clgDrLimit = clgDrLimit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getOperatingBranch() {
        return operatingBranch;
    }

    public void setOperatingBranch(String operatingBranch) {
        this.operatingBranch = operatingBranch;
    }

    public String getPwdReSet() {
        return pwdReSet;
    }

    public void setPwdReSet(String pwdReSet) {
        this.pwdReSet = pwdReSet;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }
     
}
