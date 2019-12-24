/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author SAMY
 */
public class AadharNonAadharPoJo implements Serializable{
    private String custId;
    private String aadharNo;
    private String acNo;
    private String custName;
    private Date dob;
    private String fatherName;
    private String motherName;
    private String mobileNo;
    private String panNo;
    private String preAdd;
    private String mailAdd;
    private String primaryAcNo;

    public String getAadharNo() {
        return aadharNo;
    }
    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }
    public String getAcNo() {
        return acNo;
    }
    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }
    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getFatherName() {
        return fatherName;
    }
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    public String getMailAdd() {
        return mailAdd;
    }
    public void setMailAdd(String mailAdd) {
        this.mailAdd = mailAdd;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getMotherName() {
        return motherName;
    }
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
    public String getPanNo() {
        return panNo;
    }
    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    public String getPreAdd() {
        return preAdd;
    }
    public void setPreAdd(String preAdd) {
        this.preAdd = preAdd;
    }
    public String getPrimaryAcNo() {
        return primaryAcNo;
    }
    public void setPrimaryAcNo(String primaryAcNo) {
        this.primaryAcNo = primaryAcNo;
    }    
}
