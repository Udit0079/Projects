/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.investment;

import java.util.Date;

/**
 *
 * @author root
 */
public class CallMoneyPojo {

    private int sno;
    private int ctrlNo;
    private String dealDt;
    private String compDt;
    private double roi;
    //private double amount;
    private String amount;
    private int noOfDays;
    private String auth;
    private String authBy;
    private String enterBy;
    private String lasUpdateBy;
    private Date lastUpdateDt;
    private String details;
    private Date tranTime;
    private String status;
    private double intAmount;
    private String glheadAcno;

    public String getGlheadAcno() {
        return glheadAcno;
    }

    public void setGlheadAcno(String glheadAcno) {
        this.glheadAcno = glheadAcno;
    }
    
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getLasUpdateBy() {
        return lasUpdateBy;
    }

    public void setLasUpdateBy(String lasUpdateBy) {
        this.lasUpdateBy = lasUpdateBy;
    }

    public Date getLastUpdateDt() {
        return lastUpdateDt;
    }

    public void setLastUpdateDt(Date lastUpdateDt) {
        this.lastUpdateDt = lastUpdateDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public double getIntAmount() {
        return intAmount;
    }

    public void setIntAmount(double intAmount) {
        this.intAmount = intAmount;
    }

//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getCompDt() {
        return compDt;
    }

    public void setCompDt(String compDt) {
        this.compDt = compDt;
    }

    public int getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(int ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getDealDt() {
        return dealDt;
    }

    public void setDealDt(String dealDt) {
        this.dealDt = dealDt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
}
