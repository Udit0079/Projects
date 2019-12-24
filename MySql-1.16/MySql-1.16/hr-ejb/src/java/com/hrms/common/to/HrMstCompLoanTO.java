/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class HrMstCompLoanTO implements Serializable {
  private static final long serialVersionUID = 1L;
   
    protected HrMstCompLoanPKTO hrMstCompLoanPKTO;
  
    private double loanBudget;
   
    private double loanAvailable;
   
    private double principleCollection;
   
    private String statFlag;
   
    private String statUpFlag;
   
    private Date modDate;
   
    private int defaultComp;
   
    private String authBy;
   
    private String enteredBy;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrMstCompLoanPKTO getHrMstCompLoanPKTO() {
        return hrMstCompLoanPKTO;
    }

    public void setHrMstCompLoanPKTO(HrMstCompLoanPKTO hrMstCompLoanPKTO) {
        this.hrMstCompLoanPKTO = hrMstCompLoanPKTO;
    }

    public double getLoanAvailable() {
        return loanAvailable;
    }

    public void setLoanAvailable(double loanAvailable) {
        this.loanAvailable = loanAvailable;
    }

    public double getLoanBudget() {
        return loanBudget;
    }

    public void setLoanBudget(double loanBudget) {
        this.loanBudget = loanBudget;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public double getPrincipleCollection() {
        return principleCollection;
    }

    public void setPrincipleCollection(double principleCollection) {
        this.principleCollection = principleCollection;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    

}
