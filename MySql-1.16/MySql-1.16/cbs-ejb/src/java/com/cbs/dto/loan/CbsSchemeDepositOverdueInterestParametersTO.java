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
public class CbsSchemeDepositOverdueInterestParametersTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String schemeCode;
    private String schemeType;
    private String currencyCode;
    private String overdueGlSubHeadCode;
    private String overdueInterestCode;
    private String overdueInterestTblCodeType;
    private String overdueInterestCalcMethod;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOverdueGlSubHeadCode() {
        return overdueGlSubHeadCode;
    }

    public void setOverdueGlSubHeadCode(String overdueGlSubHeadCode) {
        this.overdueGlSubHeadCode = overdueGlSubHeadCode;
    }

    public String getOverdueInterestCalcMethod() {
        return overdueInterestCalcMethod;
    }

    public void setOverdueInterestCalcMethod(String overdueInterestCalcMethod) {
        this.overdueInterestCalcMethod = overdueInterestCalcMethod;
    }

    public String getOverdueInterestCode() {
        return overdueInterestCode;
    }

    public void setOverdueInterestCode(String overdueInterestCode) {
        this.overdueInterestCode = overdueInterestCode;
    }

    public String getOverdueInterestTblCodeType() {
        return overdueInterestTblCodeType;
    }

    public void setOverdueInterestTblCodeType(String overdueInterestTblCodeType) {
        this.overdueInterestTblCodeType = overdueInterestTblCodeType;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
