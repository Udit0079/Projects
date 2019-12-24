/*
    Document   : MinBalanceCharges
    Created on : 25 Aug, 2010, 5:28:22 PM
    Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]

 */

package com.cbs.web.pojo.misc;

/**
 *
 * @author root
 */

public class MinBalnceChargeTable {
    int sNo;
    String accountNo;
    String july;
    double charges;
    String minBalnceCharge;
    String transaction;
    String limit;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

  
    

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public String getJuly() {
        return july;
    }

    public void setJuly(String july) {
        this.july = july;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

  

   



   public String getMinBalnceCharge() {
        return minBalnceCharge;
    }

    public void setMinBalnceCharge(String minBalnceCharge) {
        this.minBalnceCharge = minBalnceCharge;
    }

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }
    
}
