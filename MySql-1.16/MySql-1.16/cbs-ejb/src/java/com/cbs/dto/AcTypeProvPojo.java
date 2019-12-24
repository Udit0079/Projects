/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

/**
 *
 * @author sipl
 */
public class AcTypeProvPojo {
    
    String provHead;
    
    String acType;
    
    double amt;

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getProvHead() {
        return provHead;
    }

    public void setProvHead(String provHead) {
        this.provHead = provHead;
    }   
}
