/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ClearSlipDetTO implements Serializable{
protected ClearSlipDetPKTO clearSlipDetPK;

    private Double dueAmt;
    private Double recoverableAmt;
    private Double netAmt;
    private String remarks;
    private Integer defaultComp;
    private String statFlag;
    private String statUpFlag;
    private Date modDate;
    private String authBy;
    private String enterdBy;

    public String getEnterdBy() {
        return enterdBy;
    }

    public void setEnterdBy(String enterdBy) {
        this.enterdBy = enterdBy;
    }
    

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public ClearSlipDetPKTO getClearSlipDetPK() {
        return clearSlipDetPK;
    }

    public void setClearSlipDetPK(ClearSlipDetPKTO clearSlipDetPK) {
        this.clearSlipDetPK = clearSlipDetPK;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public Double getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(Double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Double getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(Double netAmt) {
        this.netAmt = netAmt;
    }

    public Double getRecoverableAmt() {
        return recoverableAmt;
    }

    public void setRecoverableAmt(Double recoverableAmt) {
        this.recoverableAmt = recoverableAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

}
