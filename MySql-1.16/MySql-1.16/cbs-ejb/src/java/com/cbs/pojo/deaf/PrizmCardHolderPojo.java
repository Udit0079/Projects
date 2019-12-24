/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.deaf;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrizmCardHolderPojo implements Serializable {

    private String cbsStatus;
    private String acno;
    private String cardNo;
    private String cardExpDt;
    private String cbsRegDt;
    private BigDecimal minLimit;
    private String updateDt;
    private String enterBy;
    private String authBy;
    private String cardFileStatus;
    private String acStatus;

    public String getCbsStatus() {
        return cbsStatus;
    }

    public void setCbsStatus(String cbsStatus) {
        this.cbsStatus = cbsStatus;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardExpDt() {
        return cardExpDt;
    }

    public void setCardExpDt(String cardExpDt) {
        this.cardExpDt = cardExpDt;
    }

    public String getCbsRegDt() {
        return cbsRegDt;
    }

    public void setCbsRegDt(String cbsRegDt) {
        this.cbsRegDt = cbsRegDt;
    }

    public BigDecimal getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(BigDecimal minLimit) {
        this.minLimit = minLimit;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getCardFileStatus() {
        return cardFileStatus;
    }

    public void setCardFileStatus(String cardFileStatus) {
        this.cardFileStatus = cardFileStatus;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }
}
