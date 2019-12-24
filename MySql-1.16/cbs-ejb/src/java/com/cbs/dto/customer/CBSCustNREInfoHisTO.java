/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class CBSCustNREInfoHisTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String customerId;
    private String nationality;
    private Date nonResidentDate;
    private String countryCode;
    private String countryType;
    private String localContPersonCode;
    private String localPersonTitle;
    private String localContPersonName;
    private String localAddress1;
    private String localAddress2;
    private String village;
    private String block;
    private String cityCode;
    private String stateCode;
    private String postalCode;
    private String phoneNumber;
    private String mobileNumber;
    private String nreEmail;
    private Date nonResidentEndDate;
    private String lastChangeUserID;
    private Date lastChangeTime;
    private String recordCreaterID;
    private Long txnid;

    public String getNreEmail() {
        return nreEmail;
    }

    public void setNreEmail(String nreEmail) {
        this.nreEmail = nreEmail;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getLastChangeUserID() {
        return lastChangeUserID;
    }

    public void setLastChangeUserID(String lastChangeUserID) {
        this.lastChangeUserID = lastChangeUserID;
    }

    public String getLocalAddress1() {
        return localAddress1;
    }

    public void setLocalAddress1(String localAddress1) {
        this.localAddress1 = localAddress1;
    }

    public String getLocalAddress2() {
        return localAddress2;
    }

    public void setLocalAddress2(String localAddress2) {
        this.localAddress2 = localAddress2;
    }

    public String getLocalContPersonCode() {
        return localContPersonCode;
    }

    public void setLocalContPersonCode(String localContPersonCode) {
        this.localContPersonCode = localContPersonCode;
    }

    public String getLocalContPersonName() {
        return localContPersonName;
    }

    public void setLocalContPersonName(String localContPersonName) {
        this.localContPersonName = localContPersonName;
    }

    public String getLocalPersonTitle() {
        return localPersonTitle;
    }

    public void setLocalPersonTitle(String localPersonTitle) {
        this.localPersonTitle = localPersonTitle;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getNonResidentDate() {
        return nonResidentDate;
    }

    public void setNonResidentDate(Date nonResidentDate) {
        this.nonResidentDate = nonResidentDate;
    }

    public Date getNonResidentEndDate() {
        return nonResidentEndDate;
    }

    public void setNonResidentEndDate(Date nonResidentEndDate) {
        this.nonResidentEndDate = nonResidentEndDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRecordCreaterID() {
        return recordCreaterID;
    }

    public void setRecordCreaterID(String recordCreaterID) {
        this.recordCreaterID = recordCreaterID;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
