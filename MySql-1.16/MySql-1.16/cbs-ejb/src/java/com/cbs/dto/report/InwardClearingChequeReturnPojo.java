/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author Athar Reza
 */
public class InwardClearingChequeReturnPojo implements java.io.Serializable {

    private String acNo;
    private String custName;
    private String fatherName;
    private String address;
    private String address2;
    private BigDecimal amt;
    private String checque;
    private String instDt;
    private String details;
    private String bankName;
    private String branchName;
    private String ownBankName;
    private String ownBranchName;
    private String perVillage;
    private String perPostalCode;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getChecque() {
        return checque;
    }

    public void setChecque(String checque) {
        this.checque = checque;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getInstDt() {
        return instDt;
    }

    public void setInstDt(String instDt) {
        this.instDt = instDt;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOwnBankName() {
        return ownBankName;
    }

    public void setOwnBankName(String ownBankName) {
        this.ownBankName = ownBankName;
    }

    public String getOwnBranchName() {
        return ownBranchName;
    }

    public void setOwnBranchName(String ownBranchName) {
        this.ownBranchName = ownBranchName;
    }

   

    public String getPerVillage() {
        return perVillage;
    }

    public void setPerVillage(String perVillage) {
        this.perVillage = perVillage;
    }

    public String getPerPostalCode() {
        return perPostalCode;
    }

    public void setPerPostalCode(String perPostalCode) {
        this.perPostalCode = perPostalCode;
    }
}
