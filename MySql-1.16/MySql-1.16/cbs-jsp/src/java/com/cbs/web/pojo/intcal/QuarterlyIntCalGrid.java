/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.intcal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class QuarterlyIntCalGrid implements Serializable {
    int sNo;
    String acno;
    String custName;
    String stDate;
    BigDecimal instalment;
    BigDecimal roi;
    BigDecimal balance;
    BigDecimal interest;

    public int getsNo() {
        return sNo;
    }

    public void setsNo(int sNo) {
        this.sNo = sNo;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }
    
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInstalment() {
        return instalment;
    }

    public void setInstalment(BigDecimal instalment) {
        this.instalment = instalment;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getRoi() {
        return roi;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi;
    }

    

    public String getStDate() {
        return stDate;
    }

    public void setStDate(String stDate) {
        this.stDate = stDate;
    }
    
}
