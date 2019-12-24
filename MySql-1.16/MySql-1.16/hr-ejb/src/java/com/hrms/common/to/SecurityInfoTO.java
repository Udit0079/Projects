/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sipl
 */
public class SecurityInfoTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Short levelId;
    
    private String userId;
    
    private byte[] password;
    
    private String lastUpdateDt;
    
    private String userName;
    
    private Double tocashlimit;
    
    private Date lastLoginDate;
    
    private String address;
    
    private String status;
    
    private String enterby;
    
    private Date pwDate;
    
    private String login;
    
    private String cashierst;
    
    private Integer sid;
    
    private Double clgDebit;
    
    private Double tranDebit;
    
    private String brncode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    public String getCashierst() {
        return cashierst;
    }

    public void setCashierst(String cashierst) {
        this.cashierst = cashierst;
    }

    public Double getClgDebit() {
        return clgDebit;
    }

    public void setClgDebit(Double clgDebit) {
        this.clgDebit = clgDebit;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(String lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public Short getLevelId() {
        return levelId;
    }

    public void setLevelId(Short levelId) {
        this.levelId = levelId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Date getPwDate() {
        return pwDate;
    }

    public void setPwDate(Date pwDate) {
        this.pwDate = pwDate;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTocashlimit() {
        return tocashlimit;
    }

    public void setTocashlimit(Double tocashlimit) {
        this.tocashlimit = tocashlimit;
    }

    public Double getTranDebit() {
        return tranDebit;
    }

    public void setTranDebit(Double tranDebit) {
        this.tranDebit = tranDebit;
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
    
    
}
