/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.neftrtgs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirPayErrorResponse {

    @JsonProperty("appId")
    private String appId;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("referenceId")
    private String referenceId;
    @JsonProperty("fname")
    private String fname;
    @JsonProperty("lname")
    private String lname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("Checksum")
    private String checksum;
    @JsonProperty("businessCatId")
    private String businessCatId;
    @JsonProperty("businessTypeId")
    private String businessTypeId;
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("bankifsc")
    private String bankifsc;
    @JsonProperty("bankaccountNumber")
    private String bankAccountNumber;
    @JsonProperty("bankaccountHolderName")
    private String bankAccountHolderName;
    @JsonProperty("businesspanno")
    private String businessPanNo;
    @JsonProperty("businessgstin")
    private String businessGstin;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
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

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
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

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankifsc() {
        return bankifsc;
    }

    public void setBankifsc(String bankifsc) {
        this.bankifsc = bankifsc;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }

    public void setBankAccountHolderName(String bankAccountHolderName) {
        this.bankAccountHolderName = bankAccountHolderName;
    }

    public String getBusinessPanNo() {
        return businessPanNo;
    }

    public void setBusinessPanNo(String businessPanNo) {
        this.businessPanNo = businessPanNo;
    }

    public String getBusinessGstin() {
        return businessGstin;
    }

    public void setBusinessGstin(String businessGstin) {
        this.businessGstin = businessGstin;
    }

    @Override
    public String toString() {
        return (getAppId() == null ? "" : getAppId()).trim()
                + (getCompanyName() == null ? "" : getCompanyName()).trim()
                + (getReferenceId() == null ? "" : getReferenceId()).trim()
                + (getFname() == null ? "" : getFname()).trim()
                + (getLname() == null ? "" : getLname()).trim()
                + (getEmail() == null ? "" : getEmail()).trim()
                + (getMobile() == null ? "" : getMobile()).trim()
                + (getChecksum() == null ? "" : getChecksum()).trim()
                + (getBusinessCatId() == null ? "" : getBusinessCatId()).trim()
                + (getBusinessTypeId() == null ? "" : getBusinessTypeId()).trim()
                + (getBankName() == null ? "" : getBankName()).trim()
                + (getBankifsc() == null ? "" : getBankifsc()).trim()
                + (getBankAccountNumber() == null ? "" : getBankAccountNumber()).trim()
                + (getBankAccountHolderName() == null ? "" : getBankAccountHolderName()).trim()
                + (getBusinessPanNo() == null ? "" : getBusinessPanNo()).trim()
                + (getBusinessGstin() == null ? "" : getBusinessGstin()).trim();
    }
}
