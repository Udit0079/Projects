/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Athar Reza
 */
public class AlmFddetailPojo implements Serializable{
    private String acNo;
    private String custname;
    private String matDate;
    private double amount;
    private String bktDesc;
    private String bktNo;
    private String vchNo;

    public String getBktDesc() {
        return bktDesc;
    }

    public void setBktDesc(String bktDesc) {
        this.bktDesc = bktDesc;
    }

    public String getBktNo() {
        return bktNo;
    }

    public void setBktNo(String bktNo) {
        this.bktNo = bktNo;
    }
    
    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getVchNo() {
        return vchNo;
    }

    public void setVchNo(String vchNo) {
        this.vchNo = vchNo;
    }
    
}
