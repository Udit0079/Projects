/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.other;

import java.io.Serializable;
import java.math.BigDecimal;

public class CashHandlingDTO implements Serializable {

    private BigDecimal breakedAmount;
    private String fixedOrPercFlag;
    private BigDecimal fixedAmtOrPerc;
    private String chargeGlHead;

    public BigDecimal getBreakedAmount() {
        return breakedAmount;
    }

    public void setBreakedAmount(BigDecimal breakedAmount) {
        this.breakedAmount = breakedAmount;
    }

    public String getFixedOrPercFlag() {
        return fixedOrPercFlag;
    }

    public void setFixedOrPercFlag(String fixedOrPercFlag) {
        this.fixedOrPercFlag = fixedOrPercFlag;
    }

    public BigDecimal getFixedAmtOrPerc() {
        return fixedAmtOrPerc;
    }

    public void setFixedAmtOrPerc(BigDecimal fixedAmtOrPerc) {
        this.fixedAmtOrPerc = fixedAmtOrPerc;
    }

    public String getChargeGlHead() {
        return chargeGlHead;
    }

    public void setChargeGlHead(String chargeGlHead) {
        this.chargeGlHead = chargeGlHead;
    }
}
