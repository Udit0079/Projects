/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CreditBalancePojo {
    
    private String customerid;
    private String acNo;
    private String customerName;
    private BigDecimal depositbal;

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getDepositbal() {
        return depositbal;
    }

    public void setDepositbal(BigDecimal depositbal) {
        this.depositbal = depositbal;
    }
    
    
    
}
