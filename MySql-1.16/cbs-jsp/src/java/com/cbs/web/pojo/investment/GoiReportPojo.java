/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.investment;

/**
 *
 * @author sipl
 */
public class GoiReportPojo {
    private Integer ctrlNo;
    private String secDtl;
    private String sellarName;
    private String settleDt;
    private String matDt;
    private double faceValue;
    private double bookvalue;
    private double ytm;
    private double bookValueOld;
    private double premiumAmort;
    private double rate;
    private double costPrice;
    private String matSaleDt;
    private String marking;
    private double recInt;
    private String recievableDt;

    public Integer getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }   

    public String getMatDt() {
        return matDt;
    }

    public void setMatDt(String matDt) {
        this.matDt = matDt;
    }    

    public String getSecDtl() {
        return secDtl;
    }

    public void setSecDtl(String secDtl) {
        this.secDtl = secDtl;
    }

    public String getSellarName() {
        return sellarName;
    }

    public void setSellarName(String sellarName) {
        this.sellarName = sellarName;
    }

    public String getSettleDt() {
        return settleDt;
    }

    public void setSettleDt(String settleDt) {
        this.settleDt = settleDt;
    }

    public double getBookvalue() {
        return bookvalue;
    }

    public void setBookvalue(double bookvalue) {
        this.bookvalue = bookvalue;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getYtm() {
        return ytm;
    }

    public void setYtm(double ytm) {
        this.ytm = ytm;
    }

    public double getBookValueOld() {
        return bookValueOld;
    }
    public void setBookValueOld(double bookValueOld) {
        this.bookValueOld = bookValueOld;
    }
    public double getCostPrice() {
        return costPrice;
    }
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
    public String getMatSaleDt() {
        return matSaleDt;
    }
    public void setMatSaleDt(String matSaleDt) {
        this.matSaleDt = matSaleDt;
    }
    public double getPremiumAmort() {
        return premiumAmort;
    }
    public void setPremiumAmort(double premiumAmort) {
        this.premiumAmort = premiumAmort;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }

    public double getRecInt() {
        return recInt;
    }

    public void setRecInt(double recInt) {
        this.recInt = recInt;
    }

    public String getRecievableDt() {
        return recievableDt;
    }

    public void setRecievableDt(String recievableDt) {
        this.recievableDt = recievableDt;
    }
}