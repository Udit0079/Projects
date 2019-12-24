/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Athar Reza
 */
public class DemandDraftPayOrderPojo implements Serializable {

    private String date;
    private String ddNoPoNo;
    private String chequeFavo;
    private BigDecimal amount;
    private String payable;
    private String custName;
    private String address;
    private String mode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getChequeFavo() {
        return chequeFavo;
    }

    public void setChequeFavo(String chequeFavo) {
        this.chequeFavo = chequeFavo;
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

    public String getDdNoPoNo() {
        return ddNoPoNo;
    }

    public void setDdNoPoNo(String ddNoPoNo) {
        this.ddNoPoNo = ddNoPoNo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPayable() {
        return payable;
    }

    public void setPayable(String payable) {
        this.payable = payable;
    }
  
}
