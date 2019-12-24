/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.investment;

/**
 *
 * @author sipl
 */
public class TdDueDatePojo {
    
    private String bankName;
    private Integer ctrlNo;
    private String fdrNo;
    
    private double faceValue;
    private String madeDt;
    private double roi;
    private String dueDt;
    private String prd;
    private String intOpt;
    private double matValue;
    private String flag;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getDueDt() {
        return dueDt;
    }

    public void setDueDt(String dueDt) {
        this.dueDt = dueDt;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public String getFdrNo() {
        return fdrNo;
    }

    public void setFdrNo(String fdrNo) {
        this.fdrNo = fdrNo;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getMadeDt() {
        return madeDt;
    }

    public void setMadeDt(String madeDt) {
        this.madeDt = madeDt;
    }

    public double getMatValue() {
        return matValue;
    }

    public void setMatValue(double matValue) {
        this.matValue = matValue;
    }

    public String getPrd() {
        return prd;
    }

    public void setPrd(String prd) {
        this.prd = prd;
    }

  

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }      
}