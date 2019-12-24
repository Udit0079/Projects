/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class MbSubscriberTabTO implements Serializable {

    private static final long serialVersionUID = 9632514278852412L;
    private String acno;
    private String acnoType;
    private String mobileNo;
    private String status;
    private String billDeductionAcno;
    private double cashCrLimit;
    private double cashDrLimit;
    private double trfCrLimit;
    private double trfDrLimit;
    private double clgCrLimit;
    private double clgDrLimit;
    private String pinNo;
    private Date createdDate;
    private String enterBy;
    private String auth;
    private String authBy;
    private String authStatus;
    private double interestLimit;
    private double chargeLimit;
    private String updateBy;
    private Date updateDt;
    private Collection<MbSubscriberServicesTO> mbSubscriberServicesCollectionTO;

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAcnoType() {
        return acnoType;
    }

    public void setAcnoType(String acnoType) {
        this.acnoType = acnoType;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getBillDeductionAcno() {
        return billDeductionAcno;
    }

    public void setBillDeductionAcno(String billDeductionAcno) {
        this.billDeductionAcno = billDeductionAcno;
    }

    public double getCashCrLimit() {
        return cashCrLimit;
    }

    public void setCashCrLimit(double cashCrLimit) {
        this.cashCrLimit = cashCrLimit;
    }

    public double getCashDrLimit() {
        return cashDrLimit;
    }

    public void setCashDrLimit(double cashDrLimit) {
        this.cashDrLimit = cashDrLimit;
    }

    public double getClgCrLimit() {
        return clgCrLimit;
    }

    public void setClgCrLimit(double clgCrLimit) {
        this.clgCrLimit = clgCrLimit;
    }

    public double getClgDrLimit() {
        return clgDrLimit;
    }

    public void setClgDrLimit(double clgDrLimit) {
        this.clgDrLimit = clgDrLimit;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Collection<MbSubscriberServicesTO> getMbSubscriberServicesCollectionTO() {
        return mbSubscriberServicesCollectionTO;
    }

    public void setMbSubscriberServicesCollectionTO(Collection<MbSubscriberServicesTO> mbSubscriberServicesCollectionTO) {
        this.mbSubscriberServicesCollectionTO = mbSubscriberServicesCollectionTO;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTrfCrLimit() {
        return trfCrLimit;
    }

    public void setTrfCrLimit(double trfCrLimit) {
        this.trfCrLimit = trfCrLimit;
    }

    public double getTrfDrLimit() {
        return trfDrLimit;
    }

    public void setTrfDrLimit(double trfDrLimit) {
        this.trfDrLimit = trfDrLimit;
    }

    public double getInterestLimit() {
        return interestLimit;
    }

    public void setInterestLimit(double interestLimit) {
        this.interestLimit = interestLimit;
    }

    public double getChargeLimit() {
        return chargeLimit;
    }

    public void setChargeLimit(double chargeLimit) {
        this.chargeLimit = chargeLimit;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }
}
