/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.other;

/**
 *
 * @author root
 */
public class ReportDetail {

    String sNo;
    String custId;
    String accNo;
    String name;
    String voucherNo;
    String option;
    String status;
    String panNo;
    public double tdsDeducted;
    public double tdsCalculated;
    public double intPaid;
    public double intCalculated;
    public double tdsToBeDed;

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    
    public double getTdsToBeDed() {
        return tdsToBeDed;
    }

    public void setTdsToBeDed(double tdsToBeDed) {
        this.tdsToBeDed = tdsToBeDed;
    }
    
    public double getIntCalculated() {
        return intCalculated;
    }

    public void setIntCalculated(double intCalculated) {
        this.intCalculated = intCalculated;
    }

    public double getIntPaid() {
        return intPaid;
    }

    public void setIntPaid(double intPaid) {
        this.intPaid = intPaid;
    }

    public double getTdsCalculated() {
        return tdsCalculated;
    }

    public void setTdsCalculated(double tdsCalculated) {
        this.tdsCalculated = tdsCalculated;
    }

    public double getTdsDeducted() {
        return tdsDeducted;
    }

    public void setTdsDeducted(double tdsDeducted) {
        this.tdsDeducted = tdsDeducted;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
    
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }
    

}
