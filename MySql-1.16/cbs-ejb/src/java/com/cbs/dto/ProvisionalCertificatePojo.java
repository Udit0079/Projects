/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.math.BigDecimal;

/**
 *
 * @author saurabhsipl
 */
public class ProvisionalCertificatePojo {

    private String sNo,firstDt,custName,acNo,acNat;
    private String lastDt,drCrFlag,flag,sanctionDt;
    private BigDecimal product,totalInt,closingBal;
    private BigDecimal totalNoOfDays,totalProduct,roi;
    private String fromDate,toDate,bnkName,bnkAdd,details,intTableCode;
    private BigDecimal fdRoi,fdProduct,fdInt,sbInt,sbFdTotalInt,sanctionAmt,interestAmt;
    private String curAdd, jointName1, jointName2, jointName3, jointName4;

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

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(BigDecimal interestAmt) {
        this.interestAmt = interestAmt;
    }

    public String getAcNat() {
        return acNat;
    }

    public void setAcNat(String acNat) {
        this.acNat = acNat;
    }

    public BigDecimal getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(BigDecimal sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public String getSanctionDt() {
        return sanctionDt;
    }

    public void setSanctionDt(String sanctionDt) {
        this.sanctionDt = sanctionDt;
    }

    public String getCurAdd() {
        return curAdd;
    }

    public void setCurAdd(String curAdd) {
        this.curAdd = curAdd;
    }

    public String getJointName1() {
        return jointName1;
    }

    public void setJointName1(String jointName1) {
        this.jointName1 = jointName1;
    }

    public String getJointName2() {
        return jointName2;
    }

    public void setJointName2(String jointName2) {
        this.jointName2 = jointName2;
    }

    public String getJointName3() {
        return jointName3;
    }

    public void setJointName3(String jointName3) {
        this.jointName3 = jointName3;
    }

    public String getJointName4() {
        return jointName4;
    }

    public void setJointName4(String jointName4) {
        this.jointName4 = jointName4;
    }

    public BigDecimal getTotalNoOfDays() {
        return totalNoOfDays;
    }

    public void setTotalNoOfDays(BigDecimal totalNoOfDays) {
        this.totalNoOfDays = totalNoOfDays;
    }

    public BigDecimal getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(BigDecimal totalProduct) {
        this.totalProduct = totalProduct;
    }    
    
}
