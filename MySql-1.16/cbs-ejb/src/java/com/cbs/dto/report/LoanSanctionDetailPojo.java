/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author Athar Reza
 */
public class LoanSanctionDetailPojo implements Serializable{
    private String acNo;
    private String custName;
    private String opningDt;
    private double sanctionAmt;
    private double outStandBal;
    private String actDesc;
    private String actType;

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOpningDt() {
        return opningDt;
    }

    public void setOpningDt(String opningDt) {
        this.opningDt = opningDt;
    }

    public double getOutStandBal() {
        return outStandBal;
    }

    public void setOutStandBal(double outStandBal) {
        this.outStandBal = outStandBal;
    }

    public double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }
    
    
}
