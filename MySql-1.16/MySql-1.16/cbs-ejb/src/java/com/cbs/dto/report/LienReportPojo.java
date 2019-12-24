/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Athar Reza
 */
public class LienReportPojo implements Serializable {

    private String acNo;
    private Integer srNo;
    private String custName;
    private double disbAmt;
    private String disbDt;
    private String lienAcNo;
    private double outStandBal;
    private double lienValue;
    private double actualValue;
    private String matDt;
    private double roi;
    private String remark;
    private BigDecimal voucherNo;
    private BigDecimal lienValues;
   

    public double getLienValue() {
        return lienValue;
    }

    public void setLienValue(double lienValue) {
        this.lienValue = lienValue;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getDisbAmt() {
        return disbAmt;
    }

    public void setDisbAmt(double disbAmt) {
        this.disbAmt = disbAmt;
    }

    public String getDisbDt() {
        return disbDt;
    }

    public void setDisbDt(String disbDt) {
        this.disbDt = disbDt;
    }

    public String getLienAcNo() {
        return lienAcNo;
    }

    public void setLienAcNo(String lienAcNo) {
        this.lienAcNo = lienAcNo;
    }

    public double getOutStandBal() {
        return outStandBal;
    }

    public void setOutStandBal(double outStandBal) {
        this.outStandBal = outStandBal;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(BigDecimal voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getLienValues() {
        return lienValues;
    }

    public void setLienValues(BigDecimal lienValues) {
        this.lienValues = lienValues;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

}
