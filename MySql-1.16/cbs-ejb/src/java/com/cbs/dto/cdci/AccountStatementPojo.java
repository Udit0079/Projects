/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.cdci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountStatementPojo", propOrder = {
    "acNo", "custName", "acType", "jtName",
    "nomination", "crAdd", "prAdd", "pendingBal", "openingBal", "date", "particulars",
    "chequeNo", "withdrawl", "deposit", "balance", "valueDate", "acNature", "jtName2",
    "jtName3", "jtName4", "perStatus", "acStatus", "acStatusDesc", "gstrn", "type"})
public class AccountStatementPojo {

    private String acNo;
    private String custName;
    private String acType;
    private String jtName;
    private String nomination;
    private String crAdd;
    private String prAdd;
    private double pendingBal;
    private double openingBal;
    private String date;
    private String particulars;
    private String chequeNo;
    private double withdrawl;
    private double deposit;
    private double balance;
    private String valueDate;
    private String acNature;
    private String jtName2;
    private String jtName3;
    private String jtName4;
    private String perStatus;
    private String acStatus;
    private String acStatusDesc;
    private String gstrn;

    private String type;

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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getCrAdd() {
        return crAdd;
    }

    public void setCrAdd(String crAdd) {
        this.crAdd = crAdd;
    }

    public String getPrAdd() {
        return prAdd;
    }

    public void setPrAdd(String prAdd) {
        this.prAdd = prAdd;
    }

    public double getPendingBal() {
        return pendingBal;
    }

    public void setPendingBal(double pendingBal) {
        this.pendingBal = pendingBal;
    }

    public double getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(double openingBal) {
        this.openingBal = openingBal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public double getWithdrawl() {
        return withdrawl;
    }

    public void setWithdrawl(double withdrawl) {
        this.withdrawl = withdrawl;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getPerStatus() {
        return perStatus;
    }

    public void setPerStatus(String perStatus) {
        this.perStatus = perStatus;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public String getAcStatusDesc() {
        return acStatusDesc;
    }

    public void setAcStatusDesc(String acStatusDesc) {
        this.acStatusDesc = acStatusDesc;
    }

    public String getGstrn() {
        return gstrn;
    }

    public void setGstrn(String gstrn) {
        this.gstrn = gstrn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
