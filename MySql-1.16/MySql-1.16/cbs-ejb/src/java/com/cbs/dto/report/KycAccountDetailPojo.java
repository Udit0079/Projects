/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class KycAccountDetailPojo implements Serializable {

    private String custId;
    private String custName;
    private String add;
    private String dob;
    private String document;
    private String upDationDt;
    private String renewalDt;
    private String nature;
    private String idProof;
    private String addressProof;
    private String mobileNo;
    private String panNo;
    private String riskCategory;
    private String acNo;
    private String accstatus;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getRenewalDt() {
        return renewalDt;
    }

    public void setRenewalDt(String renewalDt) {
        this.renewalDt = renewalDt;
    }

    public String getUpDationDt() {
        return upDationDt;
    }

    public void setUpDationDt(String upDationDt) {
        this.upDationDt = upDationDt;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAccstatus() {
        return accstatus;
    }

    public void setAccstatus(String accstatus) {
        this.accstatus = accstatus;
    }
    
    
}
