/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ShareDividendDetailPojo implements Serializable{
    private String folioNo;
    private String custName;
    private double dividendAmt;
    private double balAmt;

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public double getDividendAmt() {
        return dividendAmt;
    }

    public void setDividendAmt(double dividendAmt) {
        this.dividendAmt = dividendAmt;
    }

    public double getBalAmt() {
        return balAmt;
    }

    public void setBalAmt(double balAmt) {
        this.balAmt = balAmt;
    }

}
