package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 * @author Nishant Srivastava
 */
public class AccountReportPojo implements java.io.Serializable{
    private String acname;
    private String acno;
    private BigDecimal crBal ;
    private BigDecimal drBal;
    private String details;
    private String adviceno;
    private String dt; 
    private String dest_brnid;
    private BigDecimal closeBal;
    private BigDecimal balance;
    private BigDecimal opBal;

    public BigDecimal getOpBal() {
        return opBal;
    }

    public void setOpBal(BigDecimal opBal) {
        this.opBal = opBal;
    }

    public String getAdviceno() {
        return adviceno;
    }

    public void setAdviceno(String adviceno) {
        this.adviceno = adviceno;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCloseBal() {
        return closeBal;
    }

    public void setCloseBal(BigDecimal closeBal) {
        this.closeBal = closeBal;
    }

    public BigDecimal getCrBal() {
        return crBal;
    }

    public void setCrBal(BigDecimal crBal) {
        this.crBal = crBal;
    }

    public BigDecimal getDrBal() {
        return drBal;
    }

    public void setDrBal(BigDecimal drBal) {
        this.drBal = drBal;
    }
   
    public String getDt() {
        return dt;
    }
    public void setDt(String dt) {
        this.dt = dt;
    }
    public String getDest_brnid() {
        return dest_brnid;
    }
    public void setDest_brnid(String dest_brnid) {
        this.dest_brnid = dest_brnid;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getAcname() {
        return acname;
    }
    public void setAcname(String acname) {
        this.acname = acname;
    }
    public String getAcno() {
        return acno;
    }
    public void setAcno(String acno) {
        this.acno = acno;
    }
}