/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
* @author Zeeshan Waris
 */
public class HrContractorDetailsTO implements Serializable {
private static final long serialVersionUID = 1L;

    protected HrContractorDetailsPKTO hrContractorDetailsPKTO;

    private String contFirName;

    private String contMidName;

    private String contLastName;

    private String contAddress;

    private String contCity;

    private String contPin;

    private String contState;

    private String faxNumber;

    private String mobileNumber;

    private String teleNumber;

    private String resiNumber;

    private String emailNumber;

    private Integer defaultComp;

    private String statFlag;

    private String statUpFlag;

    private Date modDate;

    private String authBy;

    private String enteredBy;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getContAddress() {
        return contAddress;
    }

    public void setContAddress(String contAddress) {
        this.contAddress = contAddress;
    }

    public String getContCity() {
        return contCity;
    }

    public void setContCity(String contCity) {
        this.contCity = contCity;
    }

    public String getContFirName() {
        return contFirName;
    }

    public void setContFirName(String contFirName) {
        this.contFirName = contFirName;
    }

    public String getContLastName() {
        return contLastName;
    }

    public void setContLastName(String contLastName) {
        this.contLastName = contLastName;
    }

    public String getContMidName() {
        return contMidName;
    }

    public void setContMidName(String contMidName) {
        this.contMidName = contMidName;
    }

    public String getContPin() {
        return contPin;
    }

    public void setContPin(String contPin) {
        this.contPin = contPin;
    }

    public String getContState() {
        return contState;
    }

    public void setContState(String contState) {
        this.contState = contState;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEmailNumber() {
        return emailNumber;
    }

    public void setEmailNumber(String emailNumber) {
        this.emailNumber = emailNumber;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public HrContractorDetailsPKTO getHrContractorDetailsPKTO() {
        return hrContractorDetailsPKTO;
    }

    public void setHrContractorDetailsPKTO(HrContractorDetailsPKTO hrContractorDetailsPKTO) {
        this.hrContractorDetailsPKTO = hrContractorDetailsPKTO;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getResiNumber() {
        return resiNumber;
    }

    public void setResiNumber(String resiNumber) {
        this.resiNumber = resiNumber;
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

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    
}
