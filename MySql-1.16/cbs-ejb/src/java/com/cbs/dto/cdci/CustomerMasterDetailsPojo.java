/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.cdci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerMasterDetailsPojo", propOrder = {
    "custNo",
    "title",
    "custName",
    "crAddress",
    "prAddress",
    "phoneNo",
    "panNo",
    "status",
    "grdName",
    "relation",
    "dob",
    "occupation",
    "enteredBy",
    "lastUpdatedDate",
    "remarks",
    "lines",
    "acType",
    "agCode1",
    "agCode", 
    "fatherName",
    "brnCode"})
public class CustomerMasterDetailsPojo {

    private String custNo;
    private String title;
    private String custName;
    private String crAddress;
    private String prAddress;
    private String phoneNo;
    private String panNo;
    private String status;
    private String grdName;
    private String relation;
    private String dob;
    private Integer occupation;
    private String enteredBy;
    private String lastUpdatedDate;
    private String remarks;
    private Integer lines;
    private String acType;
    private Integer agCode1;
    private String agCode;
    private String fatherName;
    private String brnCode;

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAgCode() {
        return agCode;
    }

    public void setAgCode(String agCode) {
        this.agCode = agCode;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getCrAddress() {
        return crAddress;
    }

    public void setCrAddress(String crAddress) {
        this.crAddress = crAddress;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getAgCode1() {
        return agCode1;
    }

    public void setAgCode1(Integer agCode1) {
        this.agCode1 = agCode1;
    }

    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPrAddress() {
        return prAddress;
    }

    public void setPrAddress(String prAddress) {
        this.prAddress = prAddress;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
