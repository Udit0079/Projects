/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class jdHavingDepositPojo {
    
    private Integer sNo;                        // column 1
    private String reportingEntityName;         // 2
    private String fIuReid;                     // 3
    private String noOfAcnoLessThan1000;        // 4
    private BigDecimal totalAmtCashDuringDays;  // 5
    private BigDecimal withdrawDuringDays;      // 6
    private BigDecimal cumulativeAmtDeposit;    // 7
    private BigDecimal cumulativeWithdraw;      // 8

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getReportingEntityName() {
        return reportingEntityName;
    }

    public void setReportingEntityName(String reportingEntityName) {
        this.reportingEntityName = reportingEntityName;
    }

    public String getfIuReid() {
        return fIuReid;
    }

    public void setfIuReid(String fIuReid) {
        this.fIuReid = fIuReid;
    }

    public String getNoOfAcnoLessThan1000() {
        return noOfAcnoLessThan1000;
    }

    public void setNoOfAcnoLessThan1000(String noOfAcnoLessThan1000) {
        this.noOfAcnoLessThan1000 = noOfAcnoLessThan1000;
    }

    public BigDecimal getTotalAmtCashDuringDays() {
        return totalAmtCashDuringDays;
    }

    public void setTotalAmtCashDuringDays(BigDecimal totalAmtCashDuringDays) {
        this.totalAmtCashDuringDays = totalAmtCashDuringDays;
    }

    public BigDecimal getWithdrawDuringDays() {
        return withdrawDuringDays;
    }

    public void setWithdrawDuringDays(BigDecimal withdrawDuringDays) {
        this.withdrawDuringDays = withdrawDuringDays;
    }

    public BigDecimal getCumulativeAmtDeposit() {
        return cumulativeAmtDeposit;
    }

    public void setCumulativeAmtDeposit(BigDecimal cumulativeAmtDeposit) {
        this.cumulativeAmtDeposit = cumulativeAmtDeposit;
    }

    public BigDecimal getCumulativeWithdraw() {
        return cumulativeWithdraw;
    }

    public void setCumulativeWithdraw(BigDecimal cumulativeWithdraw) {
        this.cumulativeWithdraw = cumulativeWithdraw;
    }
    
    
   
    
}
