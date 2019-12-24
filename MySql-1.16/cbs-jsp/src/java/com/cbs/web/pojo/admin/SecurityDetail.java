/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class SecurityDetail implements Serializable{

public Integer sno;
public Float matValue;
public String matDate;
public String issueDate;
public Float lienValue;
public String type;
public String LtSTMDate;
public String DPLimit;
public String STMDate;
public String NxSTMDate;
public String STMFrequency;
public String remark;
public String status;
public String enteredBy;
public String enteredDate;
public String particular;
public String intTable;
public String depositRoi;
public String margineRoi;
public String addRoi;
public String applicableRoi;
public String authBy;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getDPLimit() {
        return DPLimit;
    }

    public void setDPLimit(String DPLimit) {
        this.DPLimit = DPLimit;
    }

    public String getLtSTMDate() {
        return LtSTMDate;
    }

    public void setLtSTMDate(String LtSTMDate) {
        this.LtSTMDate = LtSTMDate;
    }

    public String getNxSTMDate() {
        return NxSTMDate;
    }

    public void setNxSTMDate(String NxSTMDate) {
        this.NxSTMDate = NxSTMDate;
    }

    public String getSTMDate() {
        return STMDate;
    }

    public void setSTMDate(String STMDate) {
        this.STMDate = STMDate;
    }

    public String getSTMFrequency() {
        return STMFrequency;
    }

    public void setSTMFrequency(String STMFrequency) {
        this.STMFrequency = STMFrequency;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Float getLienValue() {
        return lienValue;
    }

    public void setLienValue(Float lienValue) {
        this.lienValue = lienValue;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public Float getMatValue() {
        return matValue;
    }

    public void setMatValue(Float matValue) {
        this.matValue = matValue;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepositRoi() {
        return depositRoi;
    }

    public void setDepositRoi(String depositRoi) {
        this.depositRoi = depositRoi;
    }

    public String getMargineRoi() {
        return margineRoi;
    }

    public void setMargineRoi(String margineRoi) {
        this.margineRoi = margineRoi;
    }

    public String getApplicableRoi() {
        return applicableRoi;
    }

    public void setApplicableRoi(String applicableRoi) {
        this.applicableRoi = applicableRoi;
    }    

    public String getIntTable() {
        return intTable;
    }

    public void setIntTable(String intTable) {
        this.intTable = intTable;
    }

    public String getAddRoi() {
        return addRoi;
    }

    public void setAddRoi(String addRoi) {
        this.addRoi = addRoi;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }
    
}
