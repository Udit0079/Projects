/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.customer;

import java.io.Serializable;

/**
 *
 * @author Ankit Verma
 */
public class CBSCustCurrencyInfoPKTO implements Serializable{
    private String customerId;
   
    private String currencyCodeType;

    public String getCurrencyCodeType() {
        return currencyCodeType;
    }

    public void setCurrencyCodeType(String currencyCodeType) {
        this.currencyCodeType = currencyCodeType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
}
