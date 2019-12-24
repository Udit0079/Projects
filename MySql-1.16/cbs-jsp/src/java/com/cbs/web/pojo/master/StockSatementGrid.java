/*
 * CREATED BY :  ROHIT KRISHNA GUPTA
 */

package com.cbs.web.pojo.master;




public class StockSatementGrid {
    Integer sNo;
    String stmNo;
    String secNo;
    String security;
    String secDesc;
    double value;
    double margin;
//    String dpPartition;
//    String netDp;
    double netDp;
    double dpPartition;
    String renewDt;
    String statusToday;
    String stmDt;
    String validDt;

    
    public String getRenewDt() {
        return renewDt;
    }

    public void setRenewDt(String renewDt) {
        this.renewDt = renewDt;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getSecDesc() {
        return secDesc;
    }

    public void setSecDesc(String secDesc) {
        this.secDesc = secDesc;
    }

    public String getSecNo() {
        return secNo;
    }

    public void setSecNo(String secNo) {
        this.secNo = secNo;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatusToday() {
        return statusToday;
    }

    public void setStatusToday(String statusToday) {
        this.statusToday = statusToday;
    }

    public String getStmDt() {
        return stmDt;
    }

    public void setStmDt(String stmDt) {
        this.stmDt = stmDt;
    }

    public String getStmNo() {
        return stmNo;
    }

    public void setStmNo(String stmNo) {
        this.stmNo = stmNo;
    }

    public String getValidDt() {
        return validDt;
    }

    public void setValidDt(String validDt) {
        this.validDt = validDt;
    }

    public double getDpPartition() {
        return dpPartition;
    }

    public void setDpPartition(double dpPartition) {
        this.dpPartition = dpPartition;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public double getNetDp() {
        return netDp;
    }

    public void setNetDp(double netDp) {
        this.netDp = netDp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
