/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author Sudhir Kr bisht
 */
public class AlmAnnexture1Table {

    private String acName;
    private double headofAcc,
            buk1,
            buk2,
            buk3,
            buk4,
            buk5,
            buk6,
            buk7,
            buk8,
            total;
    private String actype;
    private String bankName;
    private String bankAddress;

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    

    public double getBuk1() {
        return buk1;
    }

    public void setBuk1(double buk1) {
        this.buk1 = buk1;
    }

    public double getBuk2() {
        return buk2;
    }

    public void setBuk2(double buk2) {
        this.buk2 = buk2;
    }

    public double getBuk3() {
        return buk3;
    }

    public void setBuk3(double buk3) {
        this.buk3 = buk3;
    }

    public double getBuk4() {
        return buk4;
    }

    public void setBuk4(double buk4) {
        this.buk4 = buk4;
    }

    public double getBuk5() {
        return buk5;
    }

    public void setBuk5(double buk5) {
        this.buk5 = buk5;
    }

    public double getBuk6() {
        return buk6;
    }

    public void setBuk6(double buk6) {
        this.buk6 = buk6;
    }

    public double getBuk7() {
        return buk7;
    }

    public void setBuk7(double buk7) {
        this.buk7 = buk7;
    }

    public double getBuk8() {
        return buk8;
    }

    public void setBuk8(double buk8) {
        this.buk8 = buk8;
    }

    public double getHeadofAcc() {
        return headofAcc;
    }

    public void setHeadofAcc(double headofAcc) {
        this.headofAcc = headofAcc;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }
}
