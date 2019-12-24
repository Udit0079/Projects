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
public class FiuDormantToOperative {
    
    private String date;
    private String nameOfReportingEntity;
    private String fiuReId;
    private Integer noOfDormantAccountPrevDay;
    private BigDecimal balanceOnPrevDay;
    private Integer noOfDormantAcReactivatedBetDate;
    private BigDecimal amountDepositedBetDate;
    private BigDecimal amountWithdrawnBetDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameOfReportingEntity() {
        return nameOfReportingEntity;
    }

    public void setNameOfReportingEntity(String nameOfReportingEntity) {
        this.nameOfReportingEntity = nameOfReportingEntity;
    }

    public String getFiuReId() {
        return fiuReId;
    }

    public void setFiuReId(String fiuReId) {
        this.fiuReId = fiuReId;
    }

    public Integer getNoOfDormantAccountPrevDay() {
        return noOfDormantAccountPrevDay;
    }

    public void setNoOfDormantAccountPrevDay(Integer noOfDormantAccountPrevDay) {
        this.noOfDormantAccountPrevDay = noOfDormantAccountPrevDay;
    }

    public BigDecimal getBalanceOnPrevDay() {
        return balanceOnPrevDay;
    }

    public void setBalanceOnPrevDay(BigDecimal balanceOnPrevDay) {
        this.balanceOnPrevDay = balanceOnPrevDay;
    }

    public Integer getNoOfDormantAcReactivatedBetDate() {
        return noOfDormantAcReactivatedBetDate;
    }

    public void setNoOfDormantAcReactivatedBetDate(Integer noOfDormantAcReactivatedBetDate) {
        this.noOfDormantAcReactivatedBetDate = noOfDormantAcReactivatedBetDate;
    }

    public BigDecimal getAmountDepositedBetDate() {
        return amountDepositedBetDate;
    }

    public void setAmountDepositedBetDate(BigDecimal amountDepositedBetDate) {
        this.amountDepositedBetDate = amountDepositedBetDate;
    }

    public BigDecimal getAmountWithdrawnBetDate() {
        return amountWithdrawnBetDate;
    }

    public void setAmountWithdrawnBetDate(BigDecimal amountWithdrawnBetDate) {
        this.amountWithdrawnBetDate = amountWithdrawnBetDate;
    }
}
