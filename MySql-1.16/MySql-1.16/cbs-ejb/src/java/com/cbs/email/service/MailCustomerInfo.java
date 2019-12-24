/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.email.service;

import com.cbs.dto.report.AccontDetailList;
import java.io.Serializable;
import java.util.List;

public class MailCustomerInfo implements Serializable {

    private static final long serialVersionUID = -673796629771899997L;
    private String serviceType;
    private String customerId;
    private String acno;
    private String email;
    private String customerDob;
    private String frequency;
    private String startDate;
    private String startFlag;
    private String statementPeriod;
    private String accType;
    private String custName;
    private String periodFrDt;
    private String periodToDt;
    private String custEntityType;
    private String stmtDueDt;
    private List<AccontDetailList> accList;

    public String getCustEntityType() {
        return custEntityType;
    }

    public void setCustEntityType(String custEntityType) {
        this.custEntityType = custEntityType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public String getStatementPeriod() {
        return statementPeriod;
    }

    public void setStatementPeriod(String statementPeriod) {
        this.statementPeriod = statementPeriod;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public List<AccontDetailList> getAccList() {
        return accList;
    }

    public void setAccList(List<AccontDetailList> accList) {
        this.accList = accList;
    }

    public String getPeriodFrDt() {
        return periodFrDt;
    }

    public void setPeriodFrDt(String periodFrDt) {
        this.periodFrDt = periodFrDt;
    }

    public String getPeriodToDt() {
        return periodToDt;
    }

    public void setPeriodToDt(String periodToDt) {
        this.periodToDt = periodToDt;
    }

    public String getCustomerDob() {
        return customerDob;
    }

    public void setCustomerDob(String customerDob) {
        this.customerDob = customerDob;
    }

    public String getStmtDueDt() {
        return stmtDueDt;
    }

    public void setStmtDueDt(String stmtDueDt) {
        this.stmtDueDt = stmtDueDt;
    }
}
