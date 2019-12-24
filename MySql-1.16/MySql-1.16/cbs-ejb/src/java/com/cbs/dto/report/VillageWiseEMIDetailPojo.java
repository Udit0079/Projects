/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Athar Reza
 */
public class VillageWiseEMIDetailPojo implements Serializable {

    private String acTypeDesc;
    private String custId;
    private String acNo;
    private String date;
    private String name;
    private String village;
    private double roi;
    private double disbAmt;
    private double installment;
    private double outStandBal;
    private String accMang;
    private String accMangDesc;
    private String groupId;
    private String groupIdDesc;
    private double prinAmt;
    private double intAmt;
    private String acType;
    private String schdlNo;
    private String demEffDt;
    private String sNo;
    private String demandAmt;
    private double lipAmt;

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }
    
    public double getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public double getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(double intAmt) {
        this.intAmt = intAmt;
    }
    
    public String getAccMangDesc() {
        return accMangDesc;
    }

    public void setAccMangDesc(String accMangDesc) {
        this.accMangDesc = accMangDesc;
    }

    public String getGroupIdDesc() {
        return groupIdDesc;
    }

    public void setGroupIdDesc(String groupIdDesc) {
        this.groupIdDesc = groupIdDesc;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAccMang() {
        return accMang;
    }

    public void setAccMang(String accMang) {
        this.accMang = accMang;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcTypeDesc() {
        return acTypeDesc;
    }

    public void setAcTypeDesc(String acTypeDesc) {
        this.acTypeDesc = acTypeDesc;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDisbAmt() {
        return disbAmt;
    }

    public void setDisbAmt(double disbAmt) {
        this.disbAmt = disbAmt;
    }

    public double getInstallment() {
        return installment;
    }

    public void setInstallment(double installment) {
        this.installment = installment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOutStandBal() {
        return outStandBal;
    }

    public void setOutStandBal(double outStandBal) {
        this.outStandBal = outStandBal;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSchdlNo() {
        return schdlNo;
    }

    public void setSchdlNo(String schdlNo) {
        this.schdlNo = schdlNo;
    }

    public String getDemEffDt() {
        return demEffDt;
    }

    public void setDemEffDt(String demEffDt) {
        this.demEffDt = demEffDt;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getDemandAmt() {
        return demandAmt;
    }

    public void setDemandAmt(String demandAmt) {
        this.demandAmt = demandAmt;
    }

    public double getLipAmt() {
        return lipAmt;
    }

    public void setLipAmt(double lipAmt) {
        this.lipAmt = lipAmt;
    }    
}
