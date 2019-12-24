/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author sipl
 */
public class WorkingStmtBorrowingPojo {
    private int sno;
    private String acno;
    private String name;
    private BigDecimal openingBal;
    private BigDecimal credit;
    private BigDecimal debit;
    private BigDecimal closingBal;
    private BigDecimal intpaid;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public BigDecimal getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(BigDecimal closingBal) {
        this.closingBal = closingBal;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getIntpaid() {
        return intpaid;
    }

    public void setIntpaid(BigDecimal intpaid) {
        this.intpaid = intpaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(BigDecimal openingBal) {
        this.openingBal = openingBal;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }    
}