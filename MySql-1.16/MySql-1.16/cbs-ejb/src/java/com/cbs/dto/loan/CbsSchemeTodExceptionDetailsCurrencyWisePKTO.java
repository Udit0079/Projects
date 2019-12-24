/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeTodExceptionDetailsCurrencyWisePKTO implements Serializable {

    private String schemeCode;
    private String todSrlNo;
    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getTodSrlNo() {
        return todSrlNo;
    }

    public void setTodSrlNo(String todSrlNo) {
        this.todSrlNo = todSrlNo;
    }
}
