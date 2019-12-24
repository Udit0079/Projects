/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CbsSchemeTodExceptionDetailsCurrencyWiseTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeTodExceptionDetailsCurrencyWisePKTO cbsSchemeTodExceptionDetailsCurrencyWisePKTO;
    private String schemeType;
    private BigDecimal beginAmount;
    private BigDecimal endAmount;
    private String todException;
    private String delFlag;

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public CbsSchemeTodExceptionDetailsCurrencyWisePKTO getCbsSchemeTodExceptionDetailsCurrencyWisePKTO() {
        return cbsSchemeTodExceptionDetailsCurrencyWisePKTO;
    }

    public void setCbsSchemeTodExceptionDetailsCurrencyWisePKTO(CbsSchemeTodExceptionDetailsCurrencyWisePKTO cbsSchemeTodExceptionDetailsCurrencyWisePKTO) {
        this.cbsSchemeTodExceptionDetailsCurrencyWisePKTO = cbsSchemeTodExceptionDetailsCurrencyWisePKTO;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getTodException() {
        return todException;
    }

    public void setTodException(String todException) {
        this.todException = todException;
    }
}
