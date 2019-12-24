/**
 *
 * @author Navneet Goyal
 */
package com.cbs.dto.cdci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MiniStatementPojo", propOrder = {
    "date1", "particulars", "chequeNo", "withdrawl", "deposit", "balance"})
public class MiniStatementPojo {

    private String date1;
    private String particulars;
    private String chequeNo;
    private double withdrawl;
    private double deposit;
    private double balance;

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

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public double getWithdrawl() {
        return withdrawl;
    }

    public void setWithdrawl(double withdrawl) {
        this.withdrawl = withdrawl;
    }
}

