/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class SSSRegistrationPojo implements Serializable {

    private String acNo;
    private String nomName;
    private String scheme;
    private String vendor;
    private String enrollFlag;
    private String enrollDt;
    private String custName;
    private String fatherName;
    private String dob;
    private String addr;
    private Integer record;
    private String custId;
    private String branch;
    private String reason;
    private String actualFile;
    private String gender;
    private String renewDt;

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }
       
    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getNomName() {
        return nomName;
    }

    public void setNomName(String nomName) {
        this.nomName = nomName;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getEnrollFlag() {
        return enrollFlag;
    }

    public void setEnrollFlag(String enrollFlag) {
        this.enrollFlag = enrollFlag;
    }

    public String getEnrollDt() {
        return enrollDt;
    }

    public void setEnrollDt(String enrollDt) {
        this.enrollDt = enrollDt;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getActualFile() {
        return actualFile;
    }

    public void setActualFile(String actualFile) {
        this.actualFile = actualFile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRenewDt() {
        return renewDt;
    }

    public void setRenewDt(String renewDt) {
        this.renewDt = renewDt;
    }
    
 
}
