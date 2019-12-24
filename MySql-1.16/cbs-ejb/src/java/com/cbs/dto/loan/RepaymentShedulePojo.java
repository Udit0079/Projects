/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ankit Verma
 */
public class RepaymentShedulePojo implements Serializable{
    private String acno;
    private String selectPeriodicity;
    private double amount;
    private String period;
    private Date emiDate;
    private String status;
    private int sno;
    private String dueDate;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSelectPeriodicity() {
        return selectPeriodicity;
    }

    public void setSelectPeriodicity(String selectPeriodicity) {
        this.selectPeriodicity = selectPeriodicity;
    }

    public Date getEmiDate() {
        return emiDate;
    }

    public void setEmiDate(Date emiDate) {
        this.emiDate = emiDate;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    
    
}
