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
public class HrConsultantTO implements Serializable {
 private static final long serialVersionUID = 1L;

    protected HrConsultantPKTO hrConsultantPKTO;

    private String consFirName;

    private String consMidName;

    private String consLastName;

    private String consAdd;

    private String consCity;

    private String consPin;

    private String consState;

    private String faxNumber;

    private String mobileNumber;

    private String teleNumber;

    private String contPerson;

    private String email;

    private String contDesg;

    private Double consFee;

    private Integer defaultComp;

    private String statFlag;

    private String statUpFlag;

    private Date dtModDate;

    private String authBy;

    private String enteredBy;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getConsAdd() {
        return consAdd;
    }

    public void setConsAdd(String consAdd) {
        this.consAdd = consAdd;
    }

    public String getConsCity() {
        return consCity;
    }

    public void setConsCity(String consCity) {
        this.consCity = consCity;
    }

    public Double getConsFee() {
        return consFee;
    }

    public void setConsFee(Double consFee) {
        this.consFee = consFee;
    }

    public String getConsFirName() {
        return consFirName;
    }

    public void setConsFirName(String consFirName) {
        this.consFirName = consFirName;
    }

    public String getConsLastName() {
        return consLastName;
    }

    public void setConsLastName(String consLastName) {
        this.consLastName = consLastName;
    }

    public String getConsMidName() {
        return consMidName;
    }

    public void setConsMidName(String consMidName) {
        this.consMidName = consMidName;
    }

    public String getConsPin() {
        return consPin;
    }

    public void setConsPin(String consPin) {
        this.consPin = consPin;
    }

    public String getConsState() {
        return consState;
    }

    public void setConsState(String consState) {
        this.consState = consState;
    }

    public String getContDesg() {
        return contDesg;
    }

    public void setContDesg(String contDesg) {
        this.contDesg = contDesg;
    }

    public String getContPerson() {
        return contPerson;
    }

    public void setContPerson(String contPerson) {
        this.contPerson = contPerson;
    }

    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public Date getDtModDate() {
        return dtModDate;
    }

    public void setDtModDate(Date dtModDate) {
        this.dtModDate = dtModDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public HrConsultantPKTO getHrConsultantPKTO() {
        return hrConsultantPKTO;
    }

    public void setHrConsultantPKTO(HrConsultantPKTO hrConsultantPKTO) {
        this.hrConsultantPKTO = hrConsultantPKTO;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
