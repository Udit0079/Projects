/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class TdsOnCashWithdrawalPojo {

    private String customerid;
    private String acno;
    private String name;
    private String panno;
    private BigDecimal cashwithdrawal;
    private BigDecimal subsequenceCashWithDrawal;
    private BigDecimal tdstobededucted;
    private BigDecimal tdsdeducted;
    private BigDecimal sumofcashnsubamt;

    public BigDecimal getSumofcashnsubamt() {
        return sumofcashnsubamt;
    }

    public void setSumofcashnsubamt(BigDecimal sumofcashnsubamt) {
        this.sumofcashnsubamt = sumofcashnsubamt;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public BigDecimal getCashwithdrawal() {
        return cashwithdrawal;
    }

    public void setCashwithdrawal(BigDecimal cashwithdrawal) {
        this.cashwithdrawal = cashwithdrawal;
    }

    public BigDecimal getSubsequenceCashWithDrawal() {
        return subsequenceCashWithDrawal;
    }

    public void setSubsequenceCashWithDrawal(BigDecimal subsequenceCashWithDrawal) {
        this.subsequenceCashWithDrawal = subsequenceCashWithDrawal;
    }

    public BigDecimal getTdstobededucted() {
        return tdstobededucted;
    }

    public void setTdstobededucted(BigDecimal tdstobededucted) {
        this.tdstobededucted = tdstobededucted;
    }

    public BigDecimal getTdsdeducted() {
        return tdsdeducted;
    }

    public void setTdsdeducted(BigDecimal tdsdeducted) {
        this.tdsdeducted = tdsdeducted;
    }
}
