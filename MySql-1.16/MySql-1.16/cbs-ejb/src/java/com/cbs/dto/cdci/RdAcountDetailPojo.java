/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.cdci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Navneet
 */@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RdAcountDetailPojo", propOrder = {
    "acNo", "installAmt", "unpaidInstallment","sno"
            ,"duedt","status","fromacno"
 })
public class RdAcountDetailPojo {
private String acNo;
private double installAmt;
private int unpaidInstallment;
private int sno;
private String duedt;
private String status;
private String fromacno;


    public String getDuedt() {
        return duedt;
    }

    public void setDuedt(String duedt) {
        this.duedt = duedt;
    }

    public String getFromacno() {
        return fromacno;
    }

    public void setFromacno(String fromacno) {
        this.fromacno = fromacno;
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

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getInstallAmt() {
        return installAmt;
    }

    public void setInstallAmt(double installAmt) {
        this.installAmt = installAmt;
    }

    public int getUnpaidInstallment() {
        return unpaidInstallment;
    }

    public void setUnpaidInstallment(int unpaidInstallment) {
        this.unpaidInstallment = unpaidInstallment;
    }


}
