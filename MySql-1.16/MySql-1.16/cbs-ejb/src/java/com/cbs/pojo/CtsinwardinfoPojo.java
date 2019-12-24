/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

public class CtsinwardinfoPojo {
    private int srNo;
    private String presentingBankCode;
    private String draweeBankCode;
    private String clearingDate;
    private String chqNo;
    private double amount;
    private String sequenceNo;
    private String tC;

    public int getsrNo() {
        return srNo;
    }

    public void setsrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getpresentingBankCode() {
        return presentingBankCode;
    }

    public void setpresentingBankCode(String presentingBankCode) {
        this.presentingBankCode = presentingBankCode;
    }

    public String getdraweeBankCode() {
        return draweeBankCode;
    }

    public void setdraweeBankCode(String draweeBankCode) {
        this.draweeBankCode = draweeBankCode;
    }

    public String getclearingDate() {
        return clearingDate;
    }

    public void setclearingDate(String clearingDate) {
        this.clearingDate = clearingDate;
    }
 public String getchqNo() {
        return chqNo;
    }

    public void setchqNo(String chqNo) {
        this.chqNo = chqNo;
    }
    
    
    public double getamount() {
        return amount;
    }

    public void setamount(double amount) {
        this.amount = amount;
    }

    public String getsequenceNo() {
        return sequenceNo;
    }

    public void setsequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String gettC() {
        return tC;
    }

    public void settC(String tC) {
        this.tC = tC;
    }

   
    
    
   
}