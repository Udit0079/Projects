package com.cbs.pojo.mis;

import java.io.Serializable;

public class TaskUpdationPojo implements Serializable {

    private int srNo;
    private String dataSource;
    private String marketingSource;
    private String detailId;
    private String leadId;
    private String accountNo;
    private String customerName;
    private String fatherName;
    private String contactNo;
    private String emailId;
    private String address;
    private String city;
    private String alternateContactNo;
    private String status;
    private String purpose;
    private String purposeType;
    private String enquiryType;
    private String enterBy;//as assignedUser in report
    private String followUpDatetime;
    private String followedDateTime;
    private String followedBy;
    private String entryDate;
    private String followUpRemarks;
    private String refferedAcNo;
    private String branch;
    boolean selected;

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAlternateContactNo() {
        return alternateContactNo;
    }

    public void setAlternateContactNo(String alternateContactNo) {
        this.alternateContactNo = alternateContactNo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getFollowUpDatetime() {
        return followUpDatetime;
    }

    public void setFollowUpDatetime(String followUpDatetime) {
        this.followUpDatetime = followUpDatetime;
    }

    public String getFollowedDateTime() {
        return followedDateTime;
    }

    public void setFollowedDateTime(String followedDateTime) {
        this.followedDateTime = followedDateTime;
    }

    public String getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(String followedBy) {
        this.followedBy = followedBy;
    }

    public String getFollowUpRemarks() {
        return followUpRemarks;
    }

    public void setFollowUpRemarks(String followUpRemarks) {
        this.followUpRemarks = followUpRemarks;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getMarketingSource() {
        return marketingSource;
    }

    public void setMarketingSource(String marketingSource) {
        this.marketingSource = marketingSource;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getRefferedAcNo() {
        return refferedAcNo;
    }

    public void setRefferedAcNo(String refferedAcNo) {
        this.refferedAcNo = refferedAcNo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
