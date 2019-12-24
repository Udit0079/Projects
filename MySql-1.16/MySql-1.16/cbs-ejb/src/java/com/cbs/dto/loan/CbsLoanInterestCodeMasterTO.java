/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class CbsLoanInterestCodeMasterTO implements Serializable {
     private static final long serialVersionUID = 1L;
   
    private String interestCode;
    
    private String interestCodeDescription;
   
    private int interestVersionNo;
   
    private String interestVersionDescription;
   
    private String currencyCode;
  
    private String recordStatus;
    
    private Date startDate;
   
    private Date endDate;
   
    private String indicatorFlag;
    
    private String interestVersionRemarks;
    
    private String interestMasterTableCode;
   
    private int recordModificationCount;
   
    private double basePercentageDebit;
   
    private double basePercentageCredit;
    
    private String createdByUserId;
   
    private Date creationDate;
   
    private String lastUpdatedByUserId;
   
    private Date lastUpdatedDate;

    public double getBasePercentageCredit() {
        return basePercentageCredit;
    }

    public void setBasePercentageCredit(double basePercentageCredit) {
        this.basePercentageCredit = basePercentageCredit;
    }

    public double getBasePercentageDebit() {
        return basePercentageDebit;
    }

    public void setBasePercentageDebit(double basePercentageDebit) {
        this.basePercentageDebit = basePercentageDebit;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIndicatorFlag() {
        return indicatorFlag;
    }

    public void setIndicatorFlag(String indicatorFlag) {
        this.indicatorFlag = indicatorFlag;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getInterestCodeDescription() {
        return interestCodeDescription;
    }

    public void setInterestCodeDescription(String interestCodeDescription) {
        this.interestCodeDescription = interestCodeDescription;
    }

    public String getInterestMasterTableCode() {
        return interestMasterTableCode;
    }

    public void setInterestMasterTableCode(String interestMasterTableCode) {
        this.interestMasterTableCode = interestMasterTableCode;
    }

    public String getInterestVersionDescription() {
        return interestVersionDescription;
    }

    public void setInterestVersionDescription(String interestVersionDescription) {
        this.interestVersionDescription = interestVersionDescription;
    }

    public int getInterestVersionNo() {
        return interestVersionNo;
    }

    public void setInterestVersionNo(int interestVersionNo) {
        this.interestVersionNo = interestVersionNo;
    }

    public String getInterestVersionRemarks() {
        return interestVersionRemarks;
    }

    public void setInterestVersionRemarks(String interestVersionRemarks) {
        this.interestVersionRemarks = interestVersionRemarks;
    }

    public String getLastUpdatedByUserId() {
        return lastUpdatedByUserId;
    }

    public void setLastUpdatedByUserId(String lastUpdatedByUserId) {
        this.lastUpdatedByUserId = lastUpdatedByUserId;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getRecordModificationCount() {
        return recordModificationCount;
    }

    public void setRecordModificationCount(int recordModificationCount) {
        this.recordModificationCount = recordModificationCount;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    
    
}
