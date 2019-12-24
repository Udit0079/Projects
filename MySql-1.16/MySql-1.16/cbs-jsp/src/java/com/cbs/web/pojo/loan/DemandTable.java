/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.loan;

/**
 *
 * @author Administrator
 */
public class DemandTable {

    private String acno;
    private String scheduleNo;
    private String flowId;
    private String demandDate;
    private String freqtype;
    private String dmdEffDt;
    private String overDueDt;
    private String dmdAmt;
    private String equatedAmt;
    private String latePayFee;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(String demandDate) {
        this.demandDate = demandDate;
    }

    public String getDmdAmt() {
        return dmdAmt;
    }

    public void setDmdAmt(String dmdAmt) {
        this.dmdAmt = dmdAmt;
    }

    public String getDmdEffDt() {
        return dmdEffDt;
    }

    public void setDmdEffDt(String dmdEffDt) {
        this.dmdEffDt = dmdEffDt;
    }

    public String getEquatedAmt() {
        return equatedAmt;
    }

    public void setEquatedAmt(String equatedAmt) {
        this.equatedAmt = equatedAmt;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFreqtype() {
        return freqtype;
    }

    public void setFreqtype(String freqtype) {
        this.freqtype = freqtype;
    }

    public String getLatePayFee() {
        return latePayFee;
    }

    public void setLatePayFee(String latePayFee) {
        this.latePayFee = latePayFee;
    }

    public String getOverDueDt() {
        return overDueDt;
    }

    public void setOverDueDt(String overDueDt) {
        this.overDueDt = overDueDt;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    
}
