/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.other;

/**
 *
 * @author root
 */
public class BranchPerformancePojo {
    private Long noOfACOpen ; 
    private Long noOfACClosed ;
    private String  branchName;
    private String  branchCode;
    private Long  noOfLoanAcOpen;
    private Long  noOfLockerIssue;
    private Long  noOfLockerSurrender;
    private int srNo;
    private Long acOpenNetOff;
    private Long lockerIssueNetOff;
    private double depositAmt;
    private String dayBegin;
    private String dayEnd;
    private String atmIssue;

    public Long getNoOfACOpen() {
        return noOfACOpen;
    }

    public void setNoOfACOpen(Long noOfACOpen) {
        this.noOfACOpen = noOfACOpen;
    }

    public Long getNoOfACClosed() {
        return noOfACClosed;
    }

    public void setNoOfACClosed(Long noOfACClosed) {
        this.noOfACClosed = noOfACClosed;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Long getNoOfLoanAcOpen() {
        return noOfLoanAcOpen;
    }

    public void setNoOfLoanAcOpen(Long noOfLoanAcOpen) {
        this.noOfLoanAcOpen = noOfLoanAcOpen;
    }

    public Long getNoOfLockerIssue() {
        return noOfLockerIssue;
    }

    public void setNoOfLockerIssue(Long noOfLockerIssue) {
        this.noOfLockerIssue = noOfLockerIssue;
    }

    public Long getNoOfLockerSurrender() {
        return noOfLockerSurrender;
    }

    public void setNoOfLockerSurrender(Long noOfLockerSurrender) {
        this.noOfLockerSurrender = noOfLockerSurrender;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public Long getAcOpenNetOff() {
        return acOpenNetOff;
    }

    public void setAcOpenNetOff(Long acOpenNetOff) {
        this.acOpenNetOff = acOpenNetOff;
    }

    public Long getLockerIssueNetOff() {
        return lockerIssueNetOff;
    }

    public void setLockerIssueNetOff(Long lockerIssueNetOff) {
        this.lockerIssueNetOff = lockerIssueNetOff;
    }

    public double getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(double depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getDayBegin() {
        return dayBegin;
    }

    public void setDayBegin(String dayBegin) {
        this.dayBegin = dayBegin;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getAtmIssue() {
        return atmIssue;
    }

    public void setAtmIssue(String atmIssue) {
        this.atmIssue = atmIssue;
    }
     
 }
