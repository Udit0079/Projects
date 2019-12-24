/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author SAMY
 */
public class CibilSSPoJo {
    public String acno;
    public String flag;
    public String segId;
    public BigDecimal valOfSec;
    public String currencyType;
    public String typeOfSec;
    public String secClassification;
    public String dateOfValue;
    public String filler;

    public String getAcno() {
        return acno;
    }
    public void setAcno(String acno) {
        this.acno = acno;
    }
    public String getCurrencyType() {
        return currencyType;
    }
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
    public String getDateOfValue() {
        return dateOfValue;
    }
    public void setDateOfValue(String dateOfValue) {
        this.dateOfValue = dateOfValue;
    }
    public String getFiller() {
        return filler;
    }
    public void setFiller(String filler) {
        this.filler = filler;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getSecClassification() {
        return secClassification;
    }
    public void setSecClassification(String secClassification) {
        this.secClassification = secClassification;
    }
    public String getSegId() {
        return segId;
    }
    public void setSegId(String segId) {
        this.segId = segId;
    }
    public String getTypeOfSec() {
        return typeOfSec;
    }
    public void setTypeOfSec(String typeOfSec) {
        this.typeOfSec = typeOfSec;
    }
    public BigDecimal getValOfSec() {
        return valOfSec;
    }
    public void setValOfSec(BigDecimal valOfSec) {
        this.valOfSec = valOfSec;
    }
}