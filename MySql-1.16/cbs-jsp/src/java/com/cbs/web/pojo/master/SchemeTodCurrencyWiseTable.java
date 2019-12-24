/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.master;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class SchemeTodCurrencyWiseTable implements Serializable {

    private String bgAmt;
    private String endAmt;
    private String todException;
    private String deleteTod;
    private String counterSaveUpdate;
    private String todExceptionDesc;
    private String currencyCode;
    private String serialNo;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTodExceptionDesc() {
        return todExceptionDesc;
    }

    public void setTodExceptionDesc(String todExceptionDesc) {
        this.todExceptionDesc = todExceptionDesc;
    }

    public String getCounterSaveUpdate() {
        return counterSaveUpdate;
    }

    public void setCounterSaveUpdate(String counterSaveUpdate) {
        this.counterSaveUpdate = counterSaveUpdate;
    }

    public String getBgAmt() {
        return bgAmt;
    }

    public void setBgAmt(String bgAmt) {
        this.bgAmt = bgAmt;
    }

    public String getDeleteTod() {
        return deleteTod;
    }

    public void setDeleteTod(String deleteTod) {
        this.deleteTod = deleteTod;
    }

    public String getEndAmt() {
        return endAmt;
    }

    public void setEndAmt(String endAmt) {
        this.endAmt = endAmt;
    }

    public String getTodException() {
        return todException;
    }

    public void setTodException(String todException) {
        this.todException = todException;
    }
}
