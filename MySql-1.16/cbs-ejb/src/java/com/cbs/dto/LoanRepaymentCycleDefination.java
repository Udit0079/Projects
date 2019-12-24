/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

/**
 *
 * @author admin
 */
public class LoanRepaymentCycleDefination {

    String schemeCode;
    String acOpenFromDate;

    String acOpenToDate;
    String repaymentStartDate;
    String monthIndicator;
    String delFlag;
    String counterSaveUpdate;

    public String getAcOpenFromDate() {
        return acOpenFromDate;
    }

    public void setAcOpenFromDate(String acOpenFromDate) {
        this.acOpenFromDate = acOpenFromDate;
    }

    public String getAcOpenToDate() {
        return acOpenToDate;
    }

    public void setAcOpenToDate(String acOpenToDate) {
        this.acOpenToDate = acOpenToDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMonthIndicator() {
        return monthIndicator;
    }

    public void setMonthIndicator(String monthIndicator) {
        this.monthIndicator = monthIndicator;
    }

    public String getRepaymentStartDate() {
        return repaymentStartDate;
    }

    public void setRepaymentStartDate(String repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
    }

    public String getCounterSaveUpdate() {
        return counterSaveUpdate;
    }

    public void setCounterSaveUpdate(String counterSaveUpdate) {
        this.counterSaveUpdate = counterSaveUpdate;
    }

   

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

}
