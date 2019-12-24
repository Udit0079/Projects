/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.complex;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class TodExceptionDetailsTO implements Serializable {

    private String schemeCode;
    private String todSrlNo;
    private String currencyCode;
    private String schemeType;
    private BigDecimal beginAmount;
    private BigDecimal endAmount;
    private String todException;
    private String delFlag;
    private String exceptionCode;
    private String exceptionDesc;
    private String exceptionType;
    private String counterSaveUpdate;

    public String getCounterSaveUpdate() {
        return counterSaveUpdate;
    }

    public void setCounterSaveUpdate(String counterSaveUpdate) {
        this.counterSaveUpdate = counterSaveUpdate;
    }

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
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

    public String getTodException() {
        return todException;
    }

    public void setTodException(String todException) {
        this.todException = todException;
    }

    public String getTodSrlNo() {
        return todSrlNo;
    }

    public void setTodSrlNo(String todSrlNo) {
        this.todSrlNo = todSrlNo;
    }
}
