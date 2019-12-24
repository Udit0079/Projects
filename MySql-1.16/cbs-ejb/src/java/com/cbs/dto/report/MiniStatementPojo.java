/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan  Waris
 */
public class MiniStatementPojo implements Serializable  {

    private String date1;
    private Date dt;
    private String particulars;
    private String chequeNo;
    private double withdrawl;
    private double deposit;
    private double balance;
    private int tranType;
    private int ty;
    private Date tranTime;
    private double recNo;
    private String valueDate;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public double getRecNo() {
        return recNo;
    }

    public void setRecNo(double recNo) {
        this.recNo = recNo;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public double getWithdrawl() {
        return withdrawl;
    }

    public void setWithdrawl(double withdrawl) {
        this.withdrawl = withdrawl;
    }
}
