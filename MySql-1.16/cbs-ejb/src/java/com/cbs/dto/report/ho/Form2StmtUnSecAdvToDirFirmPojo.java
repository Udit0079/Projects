/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

/**
 *
 * @author root
 */
public class Form2StmtUnSecAdvToDirFirmPojo {
    private String groupNo;
    private Integer srNo;
    private String nameOfDir;
    private String nameOfCompany;
    private String relOfDirWithBank;
    private double limitSanctioned, amtOutStandOnLastDayOfMonth, lowestOutStandDuringMonth, roi;
    private String dateOfAdv;
    private String purOfAdv;
    private String dateOfRepayment;
    private String acNature;
    private String remark;

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }    

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getNameOfDir() {
        return nameOfDir;
    }

    public void setNameOfDir(String nameOfDir) {
        this.nameOfDir = nameOfDir;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getRelOfDirWithBank() {
        return relOfDirWithBank;
    }

    public void setRelOfDirWithBank(String relOfDirWithBank) {
        this.relOfDirWithBank = relOfDirWithBank;
    }

    public double getLimitSanctioned() {
        return limitSanctioned;
    }

    public void setLimitSanctioned(double limitSanctioned) {
        this.limitSanctioned = limitSanctioned;
    }    

    public double getAmtOutStandOnLastDayOfMonth() {
        return amtOutStandOnLastDayOfMonth;
    }

    public void setAmtOutStandOnLastDayOfMonth(double amtOutStandOnLastDayOfMonth) {
        this.amtOutStandOnLastDayOfMonth = amtOutStandOnLastDayOfMonth;
    }

    public double getLowestOutStandDuringMonth() {
        return lowestOutStandDuringMonth;
    }

    public void setLowestOutStandDuringMonth(double lowestOutStandDuringMonth) {
        this.lowestOutStandDuringMonth = lowestOutStandDuringMonth;
    }   

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getDateOfAdv() {
        return dateOfAdv;
    }

    public void setDateOfAdv(String dateOfAdv) {
        this.dateOfAdv = dateOfAdv;
    }

    public String getPurOfAdv() {
        return purOfAdv;
    }

    public void setPurOfAdv(String purOfAdv) {
        this.purOfAdv = purOfAdv;
    }

    public String getDateOfRepayment() {
        return dateOfRepayment;
    }

    public void setDateOfRepayment(String dateOfRepayment) {
        this.dateOfRepayment = dateOfRepayment;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
