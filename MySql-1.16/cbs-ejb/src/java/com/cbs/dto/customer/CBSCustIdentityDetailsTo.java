/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

/**
 *
 * @author root
 */
public class CBSCustIdentityDetailsTo {

    private int sNo;
    private String customerId;
    private String identificationType;
    private String idTypeDesc;
    private String otherIdentificationType;
    private String identityNo;
    private String identityExpiryDate;
    private String tinIssuingCountry;
    private String tinIssuingCountryDesc;
    private String enterDate;
    private String enterBy;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdentityExpiryDate() {
        return identityExpiryDate;
    }

    public void setIdentityExpiryDate(String identityExpiryDate) {
        this.identityExpiryDate = identityExpiryDate;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getOtherIdentificationType() {
        return otherIdentificationType;
    }

    public void setOtherIdentificationType(String otherIdentificationType) {
        this.otherIdentificationType = otherIdentificationType;
    }

    public String getIdTypeDesc() {
        return idTypeDesc;
    }

    public void setIdTypeDesc(String idTypeDesc) {
        this.idTypeDesc = idTypeDesc;
    }

    public String getTinIssuingCountry() {
        return tinIssuingCountry;
    }

    public void setTinIssuingCountry(String tinIssuingCountry) {
        this.tinIssuingCountry = tinIssuingCountry;
    }

    public String getTinIssuingCountryDesc() {
        return tinIssuingCountryDesc;
    }

    public void setTinIssuingCountryDesc(String tinIssuingCountryDesc) {
        this.tinIssuingCountryDesc = tinIssuingCountryDesc;
    }
}
