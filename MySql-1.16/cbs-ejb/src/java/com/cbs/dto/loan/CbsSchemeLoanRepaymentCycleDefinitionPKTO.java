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
public class CbsSchemeLoanRepaymentCycleDefinitionPKTO implements Serializable {

    private String schemeCode;
    private String accountOpenFromDate;

    public String getAccountOpenFromDate() {
        return accountOpenFromDate;
    }

    public void setAccountOpenFromDate(String accountOpenFromDate) {
        this.accountOpenFromDate = accountOpenFromDate;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }
}
