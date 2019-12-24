/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.loan;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsSchemeLoanRepaymentCycleDefinitionTO implements Serializable {

    private static final long serialVersionUID = 1L;
    protected CbsSchemeLoanRepaymentCycleDefinitionPKTO cbsSchemeLoanRepaymentCycleDefinitionPKTO;
    private String accountOpenToDate;
    private String repaymentStartDate;
    private String currentOrNextMonthIndicator;
    private String delFlag;

    public String getAccountOpenToDate() {
        return accountOpenToDate;
    }

    public void setAccountOpenToDate(String accountOpenToDate) {
        this.accountOpenToDate = accountOpenToDate;
    }

    public CbsSchemeLoanRepaymentCycleDefinitionPKTO getCbsSchemeLoanRepaymentCycleDefinitionPKTO() {
        return cbsSchemeLoanRepaymentCycleDefinitionPKTO;
    }

    public void setCbsSchemeLoanRepaymentCycleDefinitionPKTO(CbsSchemeLoanRepaymentCycleDefinitionPKTO cbsSchemeLoanRepaymentCycleDefinitionPKTO) {
        this.cbsSchemeLoanRepaymentCycleDefinitionPKTO = cbsSchemeLoanRepaymentCycleDefinitionPKTO;
    }

    public String getCurrentOrNextMonthIndicator() {
        return currentOrNextMonthIndicator;
    }

    public void setCurrentOrNextMonthIndicator(String currentOrNextMonthIndicator) {
        this.currentOrNextMonthIndicator = currentOrNextMonthIndicator;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRepaymentStartDate() {
        return repaymentStartDate;
    }

    public void setRepaymentStartDate(String repaymentStartDate) {
        this.repaymentStartDate = repaymentStartDate;
    }
}
