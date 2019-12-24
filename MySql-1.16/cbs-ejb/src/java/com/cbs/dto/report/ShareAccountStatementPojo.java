/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ShareAccountStatementPojo implements Serializable {

    private String acno;
    private String branch;
    private String name;
    private String fatherName;
    private String presentAdd;
    private String permanentAdd;
    private String nomneeName;
    private String relation;
    private double openBal;
    private String certNo;
    private int noOfShare;
    private String fromNo;
    private String toNo;
    private Date issueDate;
    private double shareAmt;
    private double pendingDiv;
    private String type;
    private double crAmt;
    private double drAmt;
    private double balance;
    private String details;
    private Date disDate;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDisDate() {
        return disDate;
    }

    public void setDisDate(Date disDate) {
        this.disDate = disDate;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfShare() {
        return noOfShare;
    }

    public void setNoOfShare(int noOfShare) {
        this.noOfShare = noOfShare;
    }

    public String getNomneeName() {
        return nomneeName;
    }

    public void setNomneeName(String nomneeName) {
        this.nomneeName = nomneeName;
    }

    public double getOpenBal() {
        return openBal;
    }

    public void setOpenBal(double openBal) {
        this.openBal = openBal;
    }

    public double getPendingDiv() {
        return pendingDiv;
    }

    public void setPendingDiv(double pendingDiv) {
        this.pendingDiv = pendingDiv;
    }

    public String getPermanentAdd() {
        return permanentAdd;
    }

    public void setPermanentAdd(String permanentAdd) {
        this.permanentAdd = permanentAdd;
    }

    public String getPresentAdd() {
        return presentAdd;
    }

    public void setPresentAdd(String presentAdd) {
        this.presentAdd = presentAdd;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public double getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(double shareAmt) {
        this.shareAmt = shareAmt;
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo;
    }
}
