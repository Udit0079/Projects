/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class AtmCardIssueDetailPojo implements Serializable{
    private String custId;
    private String acno;
    private String custName;
    private String date;
    private double limitAmt;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(double limitAmt) {
        this.limitAmt = limitAmt;
    }
      
    
}
