/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package com.cbs.web.pojo.intcal;

/**
 *
 * @author root
 */
public class AmortizationSchedule {
    private String sno;
    private String dueDate ;
    private String principleAmt ;
    private String interestAmt ;
    private String installment;
    private String periodicity;
    private String openingPrinciple;
    private String closingPrinciple;

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }     

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(String interestAmt) {
        this.interestAmt = interestAmt;
    }

    public String getPrincipleAmt() {
        return principleAmt;
    }

    public void setPrincipleAmt(String principleAmt) {
        this.principleAmt = principleAmt;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getOpeningPrinciple() {
        return openingPrinciple;
    }

    public void setOpeningPrinciple(String openingPrinciple) {
        this.openingPrinciple = openingPrinciple;
    }

    public String getClosingPrinciple() {
        return closingPrinciple;
    }

    public void setClosingPrinciple(String closingPrinciple) {
        this.closingPrinciple = closingPrinciple;
    }

    

}
