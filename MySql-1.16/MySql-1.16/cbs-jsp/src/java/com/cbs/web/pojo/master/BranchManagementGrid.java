/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.master;

/**
 *
 * @author sipl
 */
public class BranchManagementGrid {

    int serialNo;
    String dayEndFlag;
    String monthEndFlag;
    String dayBeginFlag;
    String beginUser;
    String branchName;
    String endUser;
    String sodTime;
    String eodTime;
    String sodUserName;
    String eodUserName;

    public String getSodUserName() {
        return sodUserName;
    }

    public void setSodUserName(String sodUserName) {
        this.sodUserName = sodUserName;
    }

    public String getEodUserName() {
        return eodUserName;
    }

    public void setEodUserName(String eodUserName) {
        this.eodUserName = eodUserName;
    }

   
    public String getEodTime() {
        return eodTime;
    }

    public void setEodTime(String eodTime) {
        this.eodTime = eodTime;
    }

    public String getSodTime() {
        return sodTime;
    }

    public void setSodTime(String sodTime) {
        this.sodTime = sodTime;
    }
    
    public String getBeginUser() {
        return beginUser;
    }

    public void setBeginUser(String beginUser) {
        this.beginUser = beginUser;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDayBeginFlag() {
        return dayBeginFlag;
    }

    public void setDayBeginFlag(String dayBeginFlag) {
        this.dayBeginFlag = dayBeginFlag;
    }

    public String getDayEndFlag() {
        return dayEndFlag;
    }

    public void setDayEndFlag(String dayEndFlag) {
        this.dayEndFlag = dayEndFlag;
    }

    public String getMonthEndFlag() {
        return monthEndFlag;
    }

    public void setMonthEndFlag(String monthEndFlag) {
        this.monthEndFlag = monthEndFlag;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    

}
