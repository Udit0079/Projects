/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.intcal;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class IntCalTable {

    private String sNo;
    private String flag;
    private String acNo;
    private String custName;
    private String firstDt;
    private String lastDt;
    private String drCrFlag;
    private BigDecimal closingBal;
    private BigDecimal product;
    private BigDecimal totalInt;
    private BigDecimal roi;
    private String intTableCode;
    private String details;
    private String fromDate;
    private String bnkName;
    private String bnkAdd;
    private BigDecimal fdRoi;
    private BigDecimal fdProduct;
    private BigDecimal fdInt;
    private BigDecimal sbInt;
    private BigDecimal sbFdTotalInt;
    private String relatedAc;
    private String fatherName;
    private String accStatus;

    public BigDecimal getSbInt() {
        return sbInt;
    }

    public void setSbInt(BigDecimal sbInt) {
        this.sbInt = sbInt;
    }
    
    

    public BigDecimal getSbFdTotalInt() {
        return sbFdTotalInt;
    }

    public void setSbFdTotalInt(BigDecimal sbFdTotalInt) {
        this.sbFdTotalInt = sbFdTotalInt;
    }
    
    public BigDecimal getFdRoi() {
        return fdRoi;
    }

    public void setFdRoi(BigDecimal fdRoi) {
        this.fdRoi = fdRoi;
    }

    public BigDecimal getFdProduct() {
        return fdProduct;
    }

    public void setFdProduct(BigDecimal fdProduct) {
        this.fdProduct = fdProduct;
    }

    public BigDecimal getFdInt() {
        return fdInt;
    }

    public void setFdInt(BigDecimal fdInt) {
        this.fdInt = fdInt;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFirstDt() {
        return firstDt;
    }

    public void setFirstDt(String firstDt) {
        this.firstDt = firstDt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIntTableCode() {
        return intTableCode;
    }

    public void setIntTableCode(String intTableCode) {
        this.intTableCode = intTableCode;
    }

    public String getLastDt() {
        return lastDt;
    }

    public void setLastDt(String lastDt) {
        this.lastDt = lastDt;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

   
    public BigDecimal getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(BigDecimal closingBal) {
        this.closingBal = closingBal;
    }

    public BigDecimal getProduct() {
        return product;
    }

    public void setProduct(BigDecimal product) {
        this.product = product;
    }

    public BigDecimal getRoi() {
        return roi;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi;
    }

    public BigDecimal getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(BigDecimal totalInt) {
        this.totalInt = totalInt;
    }

    public String getBnkAdd() {
        return bnkAdd;
    }

    public void setBnkAdd(String bnkAdd) {
        this.bnkAdd = bnkAdd;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getDrCrFlag() {
        return drCrFlag;
    }

    public void setDrCrFlag(String drCrFlag) {
        this.drCrFlag = drCrFlag;
    }

    public String getRelatedAc() {
        return relatedAc;
    }

    public void setRelatedAc(String relatedAc) {
        this.relatedAc = relatedAc;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }
    
}