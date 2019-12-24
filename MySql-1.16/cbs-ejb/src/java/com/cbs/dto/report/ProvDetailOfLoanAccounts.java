/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author saurabhsipl
 */
public class ProvDetailOfLoanAccounts {
    
    String acno,sno,custName;
    String category;
    String subcategory;
    String npadt;
    BigDecimal outstanding;
    BigDecimal provper;
    BigDecimal provamt;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNpadt() {
        return npadt;
    }

    public void setNpadt(String npadt) {
        this.npadt = npadt;
    }

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(BigDecimal outstanding) {
        this.outstanding = outstanding;
    }

    public BigDecimal getProvamt() {
        return provamt;
    }

    public void setProvamt(BigDecimal provamt) {
        this.provamt = provamt;
    }

    public BigDecimal getProvper() {
        return provper;
    }

    public void setProvper(BigDecimal provper) {
        this.provper = provper;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
