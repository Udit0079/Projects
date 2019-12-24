/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeAccountOpenMatrixTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String schemeType;
    private String matrixDescription;
    private String validInvalidFlag;
    private String custStatus;
    private String custSector;
    private String custSubSector;
    private String custTypeCode;
    private String custConstitution;
    private String cusTempId;
    private String custOtherBank;
    private String modeOfOperation;
    private String guaranteeCover;
    private String natureOfAdvance;
    private String typeOfAdvance;
    private String modeOfAdvance;
    private String purposeOfAdvance;
    private String custMinorFlag;
    private String chequeAllowedFlag;
    private String accountTurnDetail;
    private String accountOwnership;
    private String delFlag;

    public String getAccountOwnership() {
        return accountOwnership;
    }

    public void setAccountOwnership(String accountOwnership) {
        this.accountOwnership = accountOwnership;
    }

    public String getAccountTurnDetail() {
        return accountTurnDetail;
    }

    public void setAccountTurnDetail(String accountTurnDetail) {
        this.accountTurnDetail = accountTurnDetail;
    }

    public String getChequeAllowedFlag() {
        return chequeAllowedFlag;
    }

    public void setChequeAllowedFlag(String chequeAllowedFlag) {
        this.chequeAllowedFlag = chequeAllowedFlag;
    }

    public String getCusTempId() {
        return cusTempId;
    }

    public void setCusTempId(String cusTempId) {
        this.cusTempId = cusTempId;
    }

    public String getCustConstitution() {
        return custConstitution;
    }

    public void setCustConstitution(String custConstitution) {
        this.custConstitution = custConstitution;
    }

    public String getCustMinorFlag() {
        return custMinorFlag;
    }

    public void setCustMinorFlag(String custMinorFlag) {
        this.custMinorFlag = custMinorFlag;
    }

    public String getCustOtherBank() {
        return custOtherBank;
    }

    public void setCustOtherBank(String custOtherBank) {
        this.custOtherBank = custOtherBank;
    }

    public String getCustSector() {
        return custSector;
    }

    public void setCustSector(String custSector) {
        this.custSector = custSector;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public String getCustSubSector() {
        return custSubSector;
    }

    public void setCustSubSector(String custSubSector) {
        this.custSubSector = custSubSector;
    }

    public String getCustTypeCode() {
        return custTypeCode;
    }

    public void setCustTypeCode(String custTypeCode) {
        this.custTypeCode = custTypeCode;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getGuaranteeCover() {
        return guaranteeCover;
    }

    public void setGuaranteeCover(String guaranteeCover) {
        this.guaranteeCover = guaranteeCover;
    }

    public String getMatrixDescription() {
        return matrixDescription;
    }

    public void setMatrixDescription(String matrixDescription) {
        this.matrixDescription = matrixDescription;
    }

    public String getModeOfAdvance() {
        return modeOfAdvance;
    }

    public void setModeOfAdvance(String modeOfAdvance) {
        this.modeOfAdvance = modeOfAdvance;
    }

    public String getModeOfOperation() {
        return modeOfOperation;
    }

    public void setModeOfOperation(String modeOfOperation) {
        this.modeOfOperation = modeOfOperation;
    }

    public String getNatureOfAdvance() {
        return natureOfAdvance;
    }

    public void setNatureOfAdvance(String natureOfAdvance) {
        this.natureOfAdvance = natureOfAdvance;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getTypeOfAdvance() {
        return typeOfAdvance;
    }

    public void setTypeOfAdvance(String typeOfAdvance) {
        this.typeOfAdvance = typeOfAdvance;
    }

    public String getValidInvalidFlag() {
        return validInvalidFlag;
    }

    public void setValidInvalidFlag(String validInvalidFlag) {
        this.validInvalidFlag = validInvalidFlag;
    }
}
