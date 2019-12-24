/*
Document   : AcctEnquery
Created on : 1 Nov, 2010, 2:58:09 PM
Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.pojo.misc;

/**
 *
 * @author root
 */
public class AccountEnq {

    String txninstNo;
    Float txninStmt;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getTxninStmt() {
        return txninStmt;
    }

    public void setTxninStmt(Float txninStmt) {
        this.txninStmt = txninStmt;
    }

    public String getTxninstNo() {
        return txninstNo;
    }

    public void setTxninstNo(String txninstNo) {
        this.txninstNo = txninstNo;
    }
}
