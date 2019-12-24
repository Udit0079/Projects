/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.common.to;

import java.io.Serializable;

/**
 *
 * @author Ankit Verma
 */
public class HrTaxSlabMasterPKTO implements Serializable {
     
    private int compCode;
    private String taxSlabCode;
    private String taxFor;

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
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
    
    
}
