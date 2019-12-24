/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.pojo;

/**
 *
 * @author Ankit Verma
 */
public class TaxSlabMasterPojo {
    private String rangeFrom;
    private String rangeTo;
    private String applicableTax;
    private String taxFor;
    private String enterBy;
    private String taxSlabCode;
    private int compCode;
    
    public String getApplicableTax() {
        return applicableTax;
    }

    public void setApplicableTax(String applicableTax) {
        this.applicableTax = applicableTax;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(String rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public String getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(String rangeTo) {
        this.rangeTo = rangeTo;
    }

    public String getTaxFor() {
        return taxFor;
    }

    public void setTaxFor(String taxFor) {
        this.taxFor = taxFor;
    }

    public String getTaxSlabCode() {
        return taxSlabCode;
    }

    public void setTaxSlabCode(String taxSlabCode) {
        this.taxSlabCode = taxSlabCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }
    
    
    
}
