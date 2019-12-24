package com.cbs.dto.report;

public class LoanRecoveryPojo implements java.io.Serializable {

    private String accountNumber;
    private double amountSanc;
    private double amtDis;
    private double amtCash;
    private double amtClearing;
    private double amtClearingdr;
    private double amtTransfer;
    private double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmountSanc() {
        return amountSanc;
    }

    public void setAmountSanc(double amountSanc) {
        this.amountSanc = amountSanc;
    }

    public double getAmtCash() {
        return amtCash;
    }

    public void setAmtCash(double amtCash) {
        this.amtCash = amtCash;
    }

    public double getAmtClearing() {
        return amtClearing;
    }

    public void setAmtClearing(double amtClearing) {
        this.amtClearing = amtClearing;
    }

    public double getAmtClearingdr() {
        return amtClearingdr;
    }

    public void setAmtClearingdr(double amtClearingdr) {
        this.amtClearingdr = amtClearingdr;
    }

    public double getAmtDis() {
        return amtDis;
    }

    public void setAmtDis(double amtDis) {
        this.amtDis = amtDis;
    }

    public double getAmtTransfer() {
        return amtTransfer;
    }

    public void setAmtTransfer(double amtTransfer) {
        this.amtTransfer = amtTransfer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
