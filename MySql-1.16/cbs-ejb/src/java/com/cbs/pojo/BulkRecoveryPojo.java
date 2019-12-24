/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class BulkRecoveryPojo implements Serializable {

    private String acno;
    private String loanEmi;
    private String insPremium;
    private String wellFare;
    private String amount;
    private String chqNo;
    private String chqDt;
    private String finYear;
    private String sequnceNo;
    

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getLoanEmi() {
        return loanEmi;
    }

    public void setLoanEmi(String loanEmi) {
        this.loanEmi = loanEmi;
    }

    public String getInsPremium() {
        return insPremium;
    }

    public void setInsPremium(String insPremium) {
        this.insPremium = insPremium;
    }

    public String getWellFare() {
        return wellFare;
    }

    public void setWellFare(String wellFare) {
        this.wellFare = wellFare;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChqNo() {
        return chqNo;
    }

    public void setChqNo(String chqNo) {
        this.chqNo = chqNo;
    }

    public String getChqDt() {
        return chqDt;
    }

    public void setChqDt(String chqDt) {
        this.chqDt = chqDt;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getSequnceNo() {
        return sequnceNo;
    }

    public void setSequnceNo(String sequnceNo) {
        this.sequnceNo = sequnceNo;
    }
    
}
