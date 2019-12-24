/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.math.BigDecimal;

public class MmsReportPojo {

    private String umrn;
    private String acType;
    private String acNo;
    private String acName;
    private String frqncy;
    private String firstCollDt;
    private String finalCollDt;
    private BigDecimal collectionAmt;
    private BigDecimal maxAmt;
    private String debtorIFSC;
    private String debtorBnkName;
    private String status;
    private String rejectRsn;
    private String uploadDate;
    private String zipFileName;
    private String cbsAcNo;

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getFrqncy() {
        return frqncy;
    }

    public void setFrqncy(String frqncy) {
        this.frqncy = frqncy;
    }

    public String getFirstCollDt() {
        return firstCollDt;
    }

    public void setFirstCollDt(String firstCollDt) {
        this.firstCollDt = firstCollDt;
    }

    public String getFinalCollDt() {
        return finalCollDt;
    }

    public void setFinalCollDt(String finalCollDt) {
        this.finalCollDt = finalCollDt;
    }

    public BigDecimal getCollectionAmt() {
        return collectionAmt;
    }

    public void setCollectionAmt(BigDecimal collectionAmt) {
        this.collectionAmt = collectionAmt;
    }

    public BigDecimal getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getDebtorIFSC() {
        return debtorIFSC;
    }

    public void setDebtorIFSC(String debtorIFSC) {
        this.debtorIFSC = debtorIFSC;
    }

    public String getDebtorBnkName() {
        return debtorBnkName;
    }

    public void setDebtorBnkName(String debtorBnkName) {
        this.debtorBnkName = debtorBnkName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectRsn() {
        return rejectRsn;
    }

    public void setRejectRsn(String rejectRsn) {
        this.rejectRsn = rejectRsn;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }

    public String getCbsAcNo() {
        return cbsAcNo;
    }

    public void setCbsAcNo(String cbsAcNo) {
        this.cbsAcNo = cbsAcNo;
    }
    
}
