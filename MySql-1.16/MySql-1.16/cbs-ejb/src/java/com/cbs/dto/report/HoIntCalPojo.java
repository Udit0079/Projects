/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author ANKIT VERMA
 */
public class HoIntCalPojo {
    String acno;
    String productType;
    double product,
            interest,
            intpaid,
            closingBal,
            roi;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public double getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(double closingBal) {
        this.closingBal = closingBal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getIntpaid() {
        return intpaid;
    }

    public void setIntpaid(double intpaid) {
        this.intpaid = intpaid;
    }

    public double getProduct() {
        return product;
    }

    public void setProduct(double product) {
        this.product = product;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }
        
}
