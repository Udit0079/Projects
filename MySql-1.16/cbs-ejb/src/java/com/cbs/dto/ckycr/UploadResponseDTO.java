/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.ckycr;

import java.io.Serializable;

public class UploadResponseDTO implements Serializable {

    private String fileName;
    private String recordType;
    private String fiCode;
    private String branchRegionCode;
    private String batchNo;
    private Integer totalDetailRecord;
    private String createDate;
    private Integer lineNo;
    private String requestType;
    private String recordStatus;
    private String referenceNo;
    private String ckycNo;
    private String remarks;
    private String responseType;
    private String idVerificationStatus;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getFiCode() {
        return fiCode;
    }

    public void setFiCode(String fiCode) {
        this.fiCode = fiCode;
    }

    public String getBranchRegionCode() {
        return branchRegionCode;
    }

    public void setBranchRegionCode(String branchRegionCode) {
        this.branchRegionCode = branchRegionCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getTotalDetailRecord() {
        return totalDetailRecord;
    }

    public void setTotalDetailRecord(Integer totalDetailRecord) {
        this.totalDetailRecord = totalDetailRecord;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getCkycNo() {
        return ckycNo;
    }

    public void setCkycNo(String ckycNo) {
        this.ckycNo = ckycNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getIdVerificationStatus() {
        return idVerificationStatus;
    }

    public void setIdVerificationStatus(String idVerificationStatus) {
        this.idVerificationStatus = idVerificationStatus;
    }
}
