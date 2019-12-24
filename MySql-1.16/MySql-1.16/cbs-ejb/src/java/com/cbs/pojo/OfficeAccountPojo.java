/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class OfficeAccountPojo {

    private String baseBranch;
    private String tranType;
    private String tranDt;
    private BigDecimal amount;
    private String particulars;
    private String glhead;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBaseBranch() {
        return baseBranch;
    }

    public void setBaseBranch(String baseBranch) {
        this.baseBranch = baseBranch;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getGlhead() {
        return glhead;
    }

    public void setGlhead(String glhead) {
        this.glhead = glhead;
    }

    public String getTranDt() {
        return tranDt;
    }

    public void setTranDt(String tranDt) {
        this.tranDt = tranDt;
    }
}
