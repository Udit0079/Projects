/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class AgentWiseMonthlyCollectionPojo {
    private String agcode;
    private String name;
    private BigDecimal cashCr;
    private BigDecimal transfercash;
    public String getAgcode() {
     return agcode;
    }
    public void setAgcode(String agcode) {
        this.agcode = agcode;
    }
    public BigDecimal getCashCr() {
        return cashCr;
    }
    public void setCashCr(BigDecimal cashCr) {
        this.cashCr = cashCr;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getTransfercash() {
        return transfercash;
    }
    public void setTransfercash(BigDecimal transfercash) {
        this.transfercash = transfercash;
    }   
}

