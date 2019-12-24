/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class AtmStatusPojo implements Serializable {

    private String acNo;
    private String acNo1;
    private String acNo2;
    private String custName;
    private String mobileNo;
    private String atmCardNo;
    private String issueDt;
    private String branchNAme;
    private String add1;
    private String add2;
    private String add3;
    private String dist;
    private String state;
    private String active;
    private String type;
    private String verify;
    private BigDecimal withdrawalLimitAmount;
    private Integer withdrawalLimitCount;
    private BigDecimal purchaseLimitAmount;
    private Integer purchaseLimitCount;
    private String minLmt;
    private String fileType;
    private String status;
    private String kitNo;
    
    public String getAcNo1() {
        return acNo1;
    }

    public void setAcNo1(String acNo1) {
        this.acNo1 = acNo1;
    }

    public String getAcNo2() {
        return acNo2;
    }

    public void setAcNo2(String acNo2) {
        this.acNo2 = acNo2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAtmCardNo() {
        return atmCardNo;
    }

    public void setAtmCardNo(String atmCardNo) {
        this.atmCardNo = atmCardNo;
    }

    public String getBranchNAme() {
        return branchNAme;
    }

    public void setBranchNAme(String branchNAme) {
        this.branchNAme = branchNAme;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public BigDecimal getWithdrawalLimitAmount() {
        return withdrawalLimitAmount;
    }

    public void setWithdrawalLimitAmount(BigDecimal withdrawalLimitAmount) {
        this.withdrawalLimitAmount = withdrawalLimitAmount;
    }

    public Integer getWithdrawalLimitCount() {
        return withdrawalLimitCount;
    }

    public void setWithdrawalLimitCount(Integer withdrawalLimitCount) {
        this.withdrawalLimitCount = withdrawalLimitCount;
    }

    public BigDecimal getPurchaseLimitAmount() {
        return purchaseLimitAmount;
    }

    public void setPurchaseLimitAmount(BigDecimal purchaseLimitAmount) {
        this.purchaseLimitAmount = purchaseLimitAmount;
    }

    public Integer getPurchaseLimitCount() {
        return purchaseLimitCount;
    }

    public void setPurchaseLimitCount(Integer purchaseLimitCount) {
        this.purchaseLimitCount = purchaseLimitCount;
    }

    public String getMinLmt() {
        return minLmt;
    }

    public void setMinLmt(String minLmt) {
        this.minLmt = minLmt;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKitNo() {
        return kitNo;
    }

    public void setKitNo(String kitNo) {
        this.kitNo = kitNo;
    }
    
    
}
