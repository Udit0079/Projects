/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.sms;

import java.math.BigDecimal;

public class TdInterestSmsTo {

    private String tdAcno;
    private String primaryAcno;
    private BigDecimal interest;

    public String getTdAcno() {
        return tdAcno;
    }

    public void setTdAcno(String tdAcno) {
        this.tdAcno = tdAcno;
    }

    public String getPrimaryAcno() {
        return primaryAcno;
    }

    public void setPrimaryAcno(String primaryAcno) {
        this.primaryAcno = primaryAcno;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
