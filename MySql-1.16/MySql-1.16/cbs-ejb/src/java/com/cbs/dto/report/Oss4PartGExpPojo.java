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
public class Oss4PartGExpPojo {

    private String name;
    private String tpExp;
    private BigDecimal expAmt;
    private BigDecimal expPerc;

    public BigDecimal getExpAmt() {
        return expAmt;
    }

    public void setExpAmt(BigDecimal expAmt) {
        this.expAmt = expAmt;
    }

    public BigDecimal getExpPerc() {
        return expPerc;
    }

    public void setExpPerc(BigDecimal expPerc) {
        this.expPerc = expPerc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTpExp() {
        return tpExp;
    }

    public void setTpExp(String tpExp) {
        this.tpExp = tpExp;
    }
}