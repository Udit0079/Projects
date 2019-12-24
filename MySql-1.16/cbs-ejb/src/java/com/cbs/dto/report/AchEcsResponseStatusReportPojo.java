/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class AchEcsResponseStatusReportPojo {

    private String cbsUmrn;
    private String creditorAccount;
    private String creditorName;
    private BigDecimal amount;
    private String debitorAccount;
    private String debitorName;
    private String entryDt;
    private String successFlag;
    private String status;
    private String returnCode;
    private String reasonDesc;
    private String responseDt;

    public String getCbsUmrn() {
        return cbsUmrn;
    }

    public void setCbsUmrn(String cbsUmrn) {
        this.cbsUmrn = cbsUmrn;
    }

    public String getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(String creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDebitorAccount() {
        return debitorAccount;
    }

    public void setDebitorAccount(String debitorAccount) {
        this.debitorAccount = debitorAccount;
    }

    public String getDebitorName() {
        return debitorName;
    }

    public void setDebitorName(String debitorName) {
        this.debitorName = debitorName;
    }

    public String getEntryDt() {
        return entryDt;
    }

    public void setEntryDt(String entryDt) {
        this.entryDt = entryDt;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResponseDt() {
        return responseDt;
    }

    public void setResponseDt(String responseDt) {
        this.responseDt = responseDt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }
}
