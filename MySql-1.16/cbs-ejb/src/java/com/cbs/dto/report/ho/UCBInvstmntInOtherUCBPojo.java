/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author saurabhsipl
 */
public class UCBInvstmntInOtherUCBPojo implements Serializable{
    private String bankName;
    private String category;
    private String nameOfDeposit;
    private String dateOfDeposit;
    private BigDecimal amount;
    private double roi;
    private String dateOfMaturity;
    private String rcsNo;
    private String rcsDate;
    private String remarks;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateOfDeposit() {
        return dateOfDeposit;
    }

    public void setDateOfDeposit(String dateOfDeposit) {
        this.dateOfDeposit = dateOfDeposit;
    }

    public String getDateOfMaturity() {
        return dateOfMaturity;
    }

    public void setDateOfMaturity(String dateOfMaturity) {
        this.dateOfMaturity = dateOfMaturity;
    }

    public String getNameOfDeposit() {
        return nameOfDeposit;
    }

    public void setNameOfDeposit(String nameOfDeposit) {
        this.nameOfDeposit = nameOfDeposit;
    }

    public String getRcsDate() {
        return rcsDate;
    }

    public void setRcsDate(String rcsDate) {
        this.rcsDate = rcsDate;
    }

    public String getRcsNo() {
        return rcsNo;
    }

    public void setRcsNo(String rcsNo) {
        this.rcsNo = rcsNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }
    
}
