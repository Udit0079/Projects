package com.cbs.dto.report;

public class AlmTlRoiWiseReportPojo implements java.io.Serializable, Comparable {

    private double roi;
    private double buk1;
    private double buk2;
    private double buk3;
    private double buk4;
    private double buk5;
    private double buk6;
    private double buk7;
    private double buk8;
    private String bankname;
    private String branchname;
    private String bankaddress;

    public String getBankaddress() {
        return bankaddress;
    }

    public void setBankaddress(String bankaddress) {
        this.bankaddress = bankaddress;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
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

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    @Override
    public int compareTo(Object o) {

        return new Double(this.roi).compareTo(new Double(((AlmTlRoiWiseReportPojo) o).roi));
    }
}
