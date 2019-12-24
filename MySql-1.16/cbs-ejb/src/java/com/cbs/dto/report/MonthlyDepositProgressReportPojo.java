package com.cbs.dto.report;

import java.math.BigDecimal;

public class MonthlyDepositProgressReportPojo implements java.io.Serializable {

    private String acType;
    private String acDesc;
    private int noAcAtBeginOfYear;
    private BigDecimal beginOfYearBalance;
    private int noAcTargetOfYear;
    private BigDecimal targetBalOfYear;
    private int targetAcOfMonth;
    private BigDecimal targetBalOfMonth;
    private int noAcAtpreMonth;
    private BigDecimal preMonthBalance;
    private int noAcAtCurrentMonth;
    private BigDecimal currentMonthBal;

    public String getAcDesc() {
        return acDesc;
    }

    public void setAcDesc(String acDesc) {
        this.acDesc = acDesc;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

   
    public int getNoAcAtBeginOfYear() {
        return noAcAtBeginOfYear;
    }

    public void setNoAcAtBeginOfYear(int noAcAtBeginOfYear) {
        this.noAcAtBeginOfYear = noAcAtBeginOfYear;
    }

    public int getNoAcAtCurrentMonth() {
        return noAcAtCurrentMonth;
    }

    public void setNoAcAtCurrentMonth(int noAcAtCurrentMonth) {
        this.noAcAtCurrentMonth = noAcAtCurrentMonth;
    }

    public int getNoAcAtpreMonth() {
        return noAcAtpreMonth;
    }

    public void setNoAcAtpreMonth(int noAcAtpreMonth) {
        this.noAcAtpreMonth = noAcAtpreMonth;
    }

    public int getNoAcTargetOfYear() {
        return noAcTargetOfYear;
    }

    public void setNoAcTargetOfYear(int noAcTargetOfYear) {
        this.noAcTargetOfYear = noAcTargetOfYear;
    }

   

    public int getTargetAcOfMonth() {
        return targetAcOfMonth;
    }

    public void setTargetAcOfMonth(int targetAcOfMonth) {
        this.targetAcOfMonth = targetAcOfMonth;
    }

    public BigDecimal getBeginOfYearBalance() {
        return beginOfYearBalance;
    }

    public void setBeginOfYearBalance(BigDecimal beginOfYearBalance) {
        this.beginOfYearBalance = beginOfYearBalance;
    }

    public BigDecimal getCurrentMonthBal() {
        return currentMonthBal;
    }

    public void setCurrentMonthBal(BigDecimal currentMonthBal) {
        this.currentMonthBal = currentMonthBal;
    }

    public BigDecimal getPreMonthBalance() {
        return preMonthBalance;
    }

    public void setPreMonthBalance(BigDecimal preMonthBalance) {
        this.preMonthBalance = preMonthBalance;
    }

    public BigDecimal getTargetBalOfMonth() {
        return targetBalOfMonth;
    }

    public void setTargetBalOfMonth(BigDecimal targetBalOfMonth) {
        this.targetBalOfMonth = targetBalOfMonth;
    }

    public BigDecimal getTargetBalOfYear() {
        return targetBalOfYear;
    }

    public void setTargetBalOfYear(BigDecimal targetBalOfYear) {
        this.targetBalOfYear = targetBalOfYear;
    }

   
}
