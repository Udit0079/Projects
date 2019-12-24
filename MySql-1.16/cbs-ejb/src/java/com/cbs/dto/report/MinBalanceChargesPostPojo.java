/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Zeeshan Waris
 */
public class MinBalanceChargesPostPojo implements Serializable {

    private String bnkName;
    private String bnkAddress;
    private String acno;
    private double dramt;
    private String details;
    private String custName;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBnkAddress() {
        return bnkAddress;
    }

    public void setBnkAddress(String bnkAddress) {
        this.bnkAddress = bnkAddress;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getDramt() {
        return dramt;
    }

    public void setDramt(double dramt) {
        this.dramt = dramt;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }   
}
