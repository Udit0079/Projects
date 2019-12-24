/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author root
 */
public class NpaAccStmPojo implements Serializable, Comparable<NpaAccStmPojo> {

    private String npaAcno, npaParticulars, npaChequeno;
    private Date npaDate, npaValueDt;
    private BigDecimal npaWithDrawl, npaDeposit, npaBalance, npaOpenBal;

    public String getNpaAcno() {
        return npaAcno;
    }

    public void setNpaAcno(String npaAcno) {
        this.npaAcno = npaAcno;
    }

    public String getNpaChequeno() {
        return npaChequeno;
    }

    public void setNpaChequeno(String npaChequeno) {
        this.npaChequeno = npaChequeno;
    }

    public Date getNpaDate() {
        return npaDate;
    }

    public void setNpaDate(Date npaDate) {
        this.npaDate = npaDate;
    }

    public String getNpaParticulars() {
        return npaParticulars;
    }

    public void setNpaParticulars(String npaParticulars) {
        this.npaParticulars = npaParticulars;
    }

    public BigDecimal getNpaBalance() {
        return npaBalance;
    }

    public void setNpaBalance(BigDecimal npaBalance) {
        this.npaBalance = npaBalance;
    }

    public BigDecimal getNpaDeposit() {
        return npaDeposit;
    }

    public void setNpaDeposit(BigDecimal npaDeposit) {
        this.npaDeposit = npaDeposit;
    }

    public BigDecimal getNpaOpenBal() {
        return npaOpenBal;
    }

    public void setNpaOpenBal(BigDecimal npaOpenBal) {
        this.npaOpenBal = npaOpenBal;
    }

    public BigDecimal getNpaWithDrawl() {
        return npaWithDrawl;
    }

    public void setNpaWithDrawl(BigDecimal npaWithDrawl) {
        this.npaWithDrawl = npaWithDrawl;
    }

    public Date getNpaValueDt() {
        return npaValueDt;
    }

    public void setNpaValueDt(Date npaValueDt) {
        this.npaValueDt = npaValueDt;
    }

    public int compareTo(NpaAccStmPojo pojo) {
        return this.npaDate.compareTo(pojo.npaDate);
    }
}
