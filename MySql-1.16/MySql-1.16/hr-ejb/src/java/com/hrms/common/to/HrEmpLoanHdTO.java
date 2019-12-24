/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.common.to;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class HrEmpLoanHdTO implements Serializable {
     private static final long serialVersionUID = 1L;

    protected HrEmpLoanHdPKTO hrEmpLoanHdPKTO;

    private double sanctionAmt;

    private Date sanctionDate;

    private double roi;

    private int noInst;

    private String instPlan;

    private char periodicity;

    private char repayFlag;

    private Date startDate;

    private String statFlag;

    private String statUpFlag;

    private Date modDate;

    private int defaultComp;

    private String authBy;

    private String enteredBy;

    private HrMstStructTO hrMstStructTO;

    private HrPersonnelDetailsTO hrPersonnelDetailsTO;

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public int getDefaultComp() {
        return defaultComp;
    }

    public void setDefaultComp(int defaultComp) {
        this.defaultComp = defaultComp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public HrEmpLoanHdPKTO getHrEmpLoanHdPKTO() {
        return hrEmpLoanHdPKTO;
    }

    public void setHrEmpLoanHdPKTO(HrEmpLoanHdPKTO hrEmpLoanHdPKTO) {
        this.hrEmpLoanHdPKTO = hrEmpLoanHdPKTO;
    }

    public HrMstStructTO getHrMstStructTO() {
        return hrMstStructTO;
    }

    public void setHrMstStructTO(HrMstStructTO hrMstStructTO) {
        this.hrMstStructTO = hrMstStructTO;
    }

    public HrPersonnelDetailsTO getHrPersonnelDetailsTO() {
        return hrPersonnelDetailsTO;
    }

    public void setHrPersonnelDetailsTO(HrPersonnelDetailsTO hrPersonnelDetailsTO) {
        this.hrPersonnelDetailsTO = hrPersonnelDetailsTO;
    }

    public String getInstPlan() {
        return instPlan;
    }

    public void setInstPlan(String instPlan) {
        this.instPlan = instPlan;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public int getNoInst() {
        return noInst;
    }

    public void setNoInst(int noInst) {
        this.noInst = noInst;
    }

    public char getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(char periodicity) {
        this.periodicity = periodicity;
    }

    public char getRepayFlag() {
        return repayFlag;
    }

    public void setRepayFlag(char repayFlag) {
        this.repayFlag = repayFlag;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Date getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Date sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatFlag() {
        return statFlag;
    }

    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
    }

    public String getStatUpFlag() {
        return statUpFlag;
    }

    public void setStatUpFlag(String statUpFlag) {
        this.statUpFlag = statUpFlag;
    }

    


}
