/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class InterestCalculationPojo implements Serializable {
    private String acNo;
    private String custName;
    private String fromDate;
    private String toDate;
    private String intTableCode;
    private BigDecimal roi;
    private BigDecimal closingBal;
    private BigDecimal product;
    private BigDecimal interestAmt;
    private BigDecimal emiAmt;
    private String drCrflag;
    private String groupBy;
    private String address;
    private String branch;
    private String acType;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public BigDecimal getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(BigDecimal closingBal) {
        this.closingBal = closingBal;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getIntTableCode() {
        return intTableCode;
    }

    public void setIntTableCode(String intTableCode) {
        this.intTableCode = intTableCode;
    }

    public BigDecimal getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(BigDecimal interestAmt) {
        this.interestAmt = interestAmt;
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

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDrCrflag() {
        return drCrflag;
    }

    public void setDrCrflag(String drCrflag) {
        this.drCrflag = drCrflag;
    }

    public BigDecimal getEmiAmt() {
        return emiAmt;
    }

    public void setEmiAmt(BigDecimal emiAmt) {
        this.emiAmt = emiAmt;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }
    
    
}
