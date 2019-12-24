/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class GuarantorDetailPojo implements Serializable{
    private String acNo;
    private String custName;
    private String memberName;
    private String address;
    private String fatherName;
    private String guarAcno;
    private String guarCustId;
    private String guarantor;
    private BigDecimal netWorth;
    private BigDecimal outStanding;
    private BigDecimal disbursementAmt;
    private String disbursementDt;
    private String area;
    private BigDecimal salary;
    private BigDecimal guaPotential;
    private Integer srlNo;
    private String guarDate;

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

    public String getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public BigDecimal getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }

    public BigDecimal getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(BigDecimal outStanding) {
        this.outStanding = outStanding;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGuarAcno() {
        return guarAcno;
    }

    public void setGuarAcno(String guarAcno) {
        this.guarAcno = guarAcno;
    }

    public String getGuarCustId() {
        return guarCustId;
    }

    public void setGuarCustId(String guarCustId) {
        this.guarCustId = guarCustId;
    }

    public BigDecimal getDisbursementAmt() {
        return disbursementAmt;
    }

    public void setDisbursementAmt(BigDecimal disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public String getDisbursementDt() {
        return disbursementDt;
    }

    public void setDisbursementDt(String disbursementDt) {
        this.disbursementDt = disbursementDt;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getGuaPotential() {
        return guaPotential;
    }

    public void setGuaPotential(BigDecimal guaPotential) {
        this.guaPotential = guaPotential;
    }

    public Integer getSrlNo() {
        return srlNo;
    }

    public void setSrlNo(Integer srlNo) {
        this.srlNo = srlNo;
    }

    public String getGuarDate() {
        return guarDate;
    }

    public void setGuarDate(String guarDate) {
        this.guarDate = guarDate;
    }
    
}
