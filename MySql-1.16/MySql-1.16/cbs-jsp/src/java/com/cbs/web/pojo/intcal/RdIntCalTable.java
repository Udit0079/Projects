/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.intcal;

import java.io.Serializable;

/**
 *
 * @author Dhirendra Singh
 */
public class RdIntCalTable implements Serializable {

    private int srNo;

    private String dueDate;
    
    private double installAmt;
    
    private String status;

    private String paymentDate;

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getInstallAmt() {
        return installAmt;
    }

    public void setInstallAmt(double installAmt) {
        this.installAmt = installAmt;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
