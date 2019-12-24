/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.io.Serializable;

/**
 *
 * @author Ankit Verma
 */
public class ItemStatusReportTable implements Serializable {

    private int sno;
    private String groupCode;
    private String groupName;
    private int itemCode;
    private String itemName;
    private String purchaseDt;
    private String originBranch;
    private String forBranch;
    private double originCost;
    private int dsno;
    private double depreciationPercent;
    private double depreciationAmt;
    private double bookValue;
    private String delFlag;
    private String noOfMovement;
    private String status;
    private int srNo;
    private String depDate;
    
    

    public double getBookValue() {
        return bookValue;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public int getDsno() {
        return dsno;
    }

    public void setDsno(int dsno) {
        this.dsno = dsno;
    }

    public String getForBranch() {
        return forBranch;
    }

    public void setForBranch(String forBranch) {
        this.forBranch = forBranch;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getOriginCost() {
        return originCost;
    }

    public void setOriginCost(double originCost) {
        this.originCost = originCost;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPurchaseDt() {
        return purchaseDt;
    }

    public void setPurchaseDt(String purchaseDt) {
        this.purchaseDt = purchaseDt;
    }

    public double getDepreciationPercent() {
        return depreciationPercent;
    }

    public void setDepreciationPercent(double depreciationPercent) {
        this.depreciationPercent = depreciationPercent;
    }

    public double getDepreciationAmt() {
        return depreciationAmt;
    }

    public void setDepreciationAmt(double depreciationAmt) {
        this.depreciationAmt = depreciationAmt;
    }

    public String getNoOfMovement() {
        return noOfMovement;
    }

    public void setNoOfMovement(String noOfMovement) {
        this.noOfMovement = noOfMovement;
    }

    public String getOriginBranch() {
        return originBranch;
    }

    public void setOriginBranch(String originBranch) {
        this.originBranch = originBranch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }
    
}
