/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ankit Verma
 */
public class HrTaxInvestmentCategoryTO implements Serializable {
    private HrTaxInvestmentCategoryPKTO hrTaxInvestmentCategoryPKTO;
     
    private double categoryAmt;
   
    private Double categoryMaxLimit;
   
    private String enteredBy;
   
    private String authBy;
   
    private Integer defaultComp;
   
    private Date modDate;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public double getCategoryAmt() {
        return categoryAmt;
    }

    public void setCategoryAmt(double categoryAmt) {
        this.categoryAmt = categoryAmt;
    }

    public Double getCategoryMaxLimit() {
        return categoryMaxLimit;
    }

    public void setCategoryMaxLimit(Double categoryMaxLimit) {
        this.categoryMaxLimit = categoryMaxLimit;
    }
  
    public Integer getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(Integer defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrTaxInvestmentCategoryPKTO getHrTaxInvestmentCategoryPKTO() {
        return hrTaxInvestmentCategoryPKTO;
    }

    public void setHrTaxInvestmentCategoryPKTO(HrTaxInvestmentCategoryPKTO hrTaxInvestmentCategoryPKTO) {
        this.hrTaxInvestmentCategoryPKTO = hrTaxInvestmentCategoryPKTO;
    }

   

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
    
    
}
