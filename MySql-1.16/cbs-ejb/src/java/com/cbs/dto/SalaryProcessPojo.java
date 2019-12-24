/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

/**
 *
 * @author root
 */
public class SalaryProcessPojo implements Comparator<SalaryProcessPojo> {

    private Integer sno;
    private BigInteger recno;
    private String salaryAccount;
    private String salaryAccountName;
    private BigDecimal salaryAmount;
    private String salaryTranType;
    private String salaryInstNo;
    private String salaryInstDt;
    private String salaryDetails;

    public String getSalaryAccount() {
        return salaryAccount;
    }

    public void setSalaryAccount(String salaryAccount) {
        this.salaryAccount = salaryAccount;
    }

    public String getSalaryAccountName() {
        return salaryAccountName;
    }

    public void setSalaryAccountName(String salaryAccountName) {
        this.salaryAccountName = salaryAccountName;
    }

    public BigDecimal getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public String getSalaryTranType() {
        return salaryTranType;
    }

    public void setSalaryTranType(String salaryTranType) {
        this.salaryTranType = salaryTranType;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public BigInteger getRecno() {
        return recno;
    }

    public void setRecno(BigInteger recno) {
        this.recno = recno;
    }

    public String getSalaryDetails() {
        return salaryDetails;
    }

    public void setSalaryDetails(String salaryDetails) {
        this.salaryDetails = salaryDetails;
    }

    public String getSalaryInstDt() {
        return salaryInstDt;
    }

    public void setSalaryInstDt(String salaryInstDt) {
        this.salaryInstDt = salaryInstDt;
    }

    public String getSalaryInstNo() {
        return salaryInstNo;
    }

    public void setSalaryInstNo(String salaryInstNo) {
        this.salaryInstNo = salaryInstNo;
    }

    public int compare(SalaryProcessPojo obj1, SalaryProcessPojo obj2) {
        return obj1.getSalaryAccount().compareTo(obj2.getSalaryAccount());
    }
}
