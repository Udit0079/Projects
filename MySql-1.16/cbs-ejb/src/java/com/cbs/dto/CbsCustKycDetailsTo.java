/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.util.Date;

public class CbsCustKycDetailsTo {

    private String customerId;
    private String riskCategory;
    private String typeOfDocSubmitted;
    private String brnCode;
    private String errorCode;
    private String ckycrUpdateFlag;
    private String kycCreatedBy;
    private Date kycCreationDate;
    private String kycVerifiedBy;
    private Date kycVerifiedDate;
    private String kycVerifiedUserName;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getTypeOfDocSubmitted() {
        return typeOfDocSubmitted;
    }

    public void setTypeOfDocSubmitted(String typeOfDocSubmitted) {
        this.typeOfDocSubmitted = typeOfDocSubmitted;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCkycrUpdateFlag() {
        return ckycrUpdateFlag;
    }

    public void setCkycrUpdateFlag(String ckycrUpdateFlag) {
        this.ckycrUpdateFlag = ckycrUpdateFlag;
    }

    public String getKycCreatedBy() {
        return kycCreatedBy;
    }

    public void setKycCreatedBy(String kycCreatedBy) {
        this.kycCreatedBy = kycCreatedBy;
    }

    public Date getKycCreationDate() {
        return kycCreationDate;
    }

    public void setKycCreationDate(Date kycCreationDate) {
        this.kycCreationDate = kycCreationDate;
    }

    public String getKycVerifiedBy() {
        return kycVerifiedBy;
    }

    public void setKycVerifiedBy(String kycVerifiedBy) {
        this.kycVerifiedBy = kycVerifiedBy;
    }

    public Date getKycVerifiedDate() {
        return kycVerifiedDate;
    }

    public void setKycVerifiedDate(Date kycVerifiedDate) {
        this.kycVerifiedDate = kycVerifiedDate;
    }

    public String getKycVerifiedUserName() {
        return kycVerifiedUserName;
    }

    public void setKycVerifiedUserName(String kycVerifiedUserName) {
        this.kycVerifiedUserName = kycVerifiedUserName;
    }
}
