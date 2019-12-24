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
public class InvestmentMutualFundPojo {

    private String controlNo;
    private String drAcNo;
    private String crAcNo;
    private String redeemDrAcNo;
    private String redeemCrAcNo;
    private String days;
    private BigDecimal amount;
    private BigDecimal profitAmount;
    private String generalDt;
    private String redeemDt;
    private String bnkName;
    private String redeemAmt;
    private String balanceAmt;
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
   
    public String getDrAcNo() {
        return drAcNo;
    }

    public void setDrAcNo(String drAcNo) {
        this.drAcNo = drAcNo;
    }

    public String getCrAcNo() {
        return crAcNo;
    }

    public void setCrAcNo(String crAcNo) {
        this.crAcNo = crAcNo;
    }

    public String getRedeemDrAcNo() {
        return redeemDrAcNo;
    }

    public void setRedeemDrAcNo(String redeemDrAcNo) {
        this.redeemDrAcNo = redeemDrAcNo;
    }

    public String getRedeemCrAcNo() {
        return redeemCrAcNo;
    }

    public void setRedeemCrAcNo(String redeemCrAcNo) {
        this.redeemCrAcNo = redeemCrAcNo;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public String getGeneralDt() {
        return generalDt;
    }

    public void setGeneralDt(String generalDt) {
        this.generalDt = generalDt;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getRedeemDt() {
        return redeemDt;
    }

    public void setRedeemDt(String redeemDt) {
        this.redeemDt = redeemDt;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public String getRedeemAmt() {
        return redeemAmt;
    }

    public void setRedeemAmt(String redeemAmt) {
        this.redeemAmt = redeemAmt;
    }

    public String getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(String balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

   
    
}