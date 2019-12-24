/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AirPayRecord {

    private String customerId;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String businessCatId;
    private String businessCatDescription;
    private String businessTypeId;
    private String businessTypeDescription;
    private String bankName;
    private String bankIfsc;
    private String bankAccountNo;
    private String bankPanNo;
    private String businessGSt;
    private String flag;
    private String username;
    private Date date;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public AirPayRecord() {
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBusinessCatDescription() {
        return businessCatDescription;
    }

    public void setBusinessCatDescription(String businessCatDescription) {
        this.businessCatDescription = businessCatDescription;
    }

    public String getBusinessTypeDescription() {
        return businessTypeDescription;
    }

    public void setBusinessTypeDescription(String businessTypeDescription) {
        this.businessTypeDescription = businessTypeDescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String FirstName) {
        this.firstName = FirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusinessCatId() {
        return businessCatId;
    }

    public void setBusinessCatId(String businessCatId) {
        this.businessCatId = businessCatId;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIfsc() {
        return bankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.bankIfsc = bankIfsc;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankPanNo() {
        return bankPanNo;
    }

    public void setBankPanNo(String bankPanNo) {
        this.bankPanNo = bankPanNo;
    }

    public String getBusinessGSt() {
        return businessGSt;
    }

    public void setBusinessGSt(String businessGSt) {
        this.businessGSt = businessGSt;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getDateUserFormat() {
        return sdf.format(getDate());
    }
}