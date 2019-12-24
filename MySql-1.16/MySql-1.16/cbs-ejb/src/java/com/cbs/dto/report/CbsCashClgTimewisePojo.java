/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class CbsCashClgTimewisePojo implements Serializable{
    String accNo;
    String custName;
    BigDecimal crAmt;
    BigDecimal drAmt;
    BigDecimal opAmt;
    String bankAddress;
    String bankName;
    String enterBy;
    String authBy;
    double recno;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public BigDecimal getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(BigDecimal crAmt) {
        this.crAmt = crAmt;
    }

    public BigDecimal getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(BigDecimal drAmt) {
        this.drAmt = drAmt;
    }

    public BigDecimal getOpAmt() {
        return opAmt;
    }

    public void setOpAmt(BigDecimal opAmt) {
        this.opAmt = opAmt;
    }
    public double getRecno() {
        return recno;
    }

    public void setRecno(double recno) {
        this.recno = recno;
    }
    
}
