/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Sudhir
 */
public class OnUsReportTable implements Serializable  {

    private String acNo,
            custName,
            auth,
            details,
            originBran,
            destBran,
            branchAddress,
            branch;
    private double crAmt, drAmt,batchNo;

    public double getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(double batchNo) {
        this.batchNo = batchNo;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }
   
    public String getDestBran() {
        return destBran;
    }

    public void setDestBran(String destBran) {
        this.destBran = destBran;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    public String getOriginBran() {
        return originBran;
    }

    public void setOriginBran(String originBran) {
        this.originBran = originBran;
    }
}
