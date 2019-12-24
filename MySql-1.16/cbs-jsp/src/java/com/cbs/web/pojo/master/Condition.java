/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.master;

/**
 *
 * @author root
 */
public class Condition {

    private String status;
    private String applicableDate;
    private int tdAmount;
    private int tdDayMonthly;
    private int tdDayCumulat;
    private String enterBy;
    private String lastUpDateDt;
    private String sNumber;

    public String getsNumber() {
        return sNumber;
    }

    public void setsNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getLastUpDateDt() {
        return lastUpDateDt;
    }

    public void setLastUpDateDt(String lastUpDateDt) {
        this.lastUpDateDt = lastUpDateDt;
    }
   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTdAmount() {
        return tdAmount;
    }

    public void setTdAmount(int tdAmount) {
        this.tdAmount = tdAmount;
    }

    public int getTdDayCumulat() {
        return tdDayCumulat;
    }

    public void setTdDayCumulat(int tdDayCumulat) {
        this.tdDayCumulat = tdDayCumulat;
    }

    public int getTdDayMonthly() {
        return tdDayMonthly;
    }

    public void setTdDayMonthly(int tdDayMonthly) {
        this.tdDayMonthly = tdDayMonthly;
    }

    

   
}
