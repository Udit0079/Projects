/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

/**
 *
 * @author root
 */
public class LoanTable {
//    int sNo;
    String loanType;
    String loanValue;
    String saveUpdateCounter;
    String sNo2;

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(String loanValue) {
        this.loanValue = loanValue;
    }

    public String getSaveUpdateCounter() {
        return saveUpdateCounter;
    }

    public void setSaveUpdateCounter(String saveUpdateCounter) {
        this.saveUpdateCounter = saveUpdateCounter;
    }

    public String getsNo2() {
        return sNo2;
    }

    public void setsNo2(String sNo2) {
        this.sNo2 = sNo2;
    }

//    public int getsNo() {
//        return sNo;
//    }
//
//    public void setsNo(int sNo) {
//        this.sNo = sNo;
//    }
    
    
}
