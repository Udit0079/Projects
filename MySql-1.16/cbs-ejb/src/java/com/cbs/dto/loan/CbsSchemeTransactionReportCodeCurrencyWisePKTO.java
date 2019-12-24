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
public class CbsSchemeTransactionReportCodeCurrencyWisePKTO implements Serializable {

    private String schemeCode;
    private String transactionReportCode;

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getTransactionReportCode() {
        return transactionReportCode;
    }

    public void setTransactionReportCode(String transactionReportCode) {
        this.transactionReportCode = transactionReportCode;
    }
}
