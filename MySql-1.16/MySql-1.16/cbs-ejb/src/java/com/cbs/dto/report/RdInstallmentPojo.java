/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author sipl
 */
public class RdInstallmentPojo {
    
    private String accountNumber;
    private String custName;
    private String rdMatDate;
    private String rdMadeDate;
    private String rdPymtDate;
    private String rdDueDate;
    private Integer totInstallment;
    private String status;
    private double rdInstalAmt;
    private double roi;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getRdDueDate() {
        return rdDueDate;
    }

    public void setRdDueDate(String rdDueDate) {
        this.rdDueDate = rdDueDate;
    }

    public double getRdInstalAmt() {
        return rdInstalAmt;
    }

    public void setRdInstalAmt(double rdInstalAmt) {
        this.rdInstalAmt = rdInstalAmt;
    }

    public String getRdMadeDate() {
        return rdMadeDate;
    }

    public void setRdMadeDate(String rdMadeDate) {
        this.rdMadeDate = rdMadeDate;
    }

    public String getRdMatDate() {
        return rdMatDate;
    }

    public void setRdMatDate(String rdMatDate) {
        this.rdMatDate = rdMatDate;
    }

    public String getRdPymtDate() {
        return rdPymtDate;
    }

    public void setRdPymtDate(String rdPymtDate) {
        this.rdPymtDate = rdPymtDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotInstallment() {
        return totInstallment;
    }

    public void setTotInstallment(int totInstallment) {
        this.totInstallment = totInstallment;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }        
}