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
public class HoReconsilePojo {

    private String baseBranch;
    private String respondingBranch;
    private String tranType;
    private BigDecimal amount;
    private String tranDt;

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

    public String getRespondingBranch() {
        return respondingBranch;
    }

    public void setRespondingBranch(String respondingBranch) {
        this.respondingBranch = respondingBranch;
    }

    public String getTranDt() {
        return tranDt;
    }

    public void setTranDt(String tranDt) {
        this.tranDt = tranDt;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }
}
