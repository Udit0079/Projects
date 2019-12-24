/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class PostalDemandPojo implements Serializable {

    private String acno;
    private String dmdType;
    private BigDecimal overDueAmt;
    private String dueDt;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getDmdType() {
        return dmdType;
    }

    public void setDmdType(String dmdType) {
        this.dmdType = dmdType;
    }

    public BigDecimal getOverDueAmt() {
        return overDueAmt;
    }

    public void setOverDueAmt(BigDecimal overDueAmt) {
        this.overDueAmt = overDueAmt;
    }

    public String getDueDt() {
        return dueDt;
    }

    public void setDueDt(String dueDt) {
        this.dueDt = dueDt;
    }
}
