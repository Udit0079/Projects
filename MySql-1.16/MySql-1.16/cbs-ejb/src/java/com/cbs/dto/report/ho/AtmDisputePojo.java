/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report.ho;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class AtmDisputePojo {
    
    private String acNo;
    private String custName;
    private String caseNumber;
    private String PrimaryAccountNumber;
    private String transactionDate;
    private BigDecimal transactionAmount;
    private BigDecimal settlementAmount;
    private String controlNumber;
    private String disputeOriginatorPID;
    private String disputeDestinationPID;
    private String acquirerReferenceDataRRN;
    private int trsno;
    private String reason;
    private String reportDate;

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

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getPrimaryAccountNumber() {
        return PrimaryAccountNumber;
    }

    public void setPrimaryAccountNumber(String PrimaryAccountNumber) {
        this.PrimaryAccountNumber = PrimaryAccountNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getDisputeOriginatorPID() {
        return disputeOriginatorPID;
    }

    public void setDisputeOriginatorPID(String disputeOriginatorPID) {
        this.disputeOriginatorPID = disputeOriginatorPID;
    }

    public String getDisputeDestinationPID() {
        return disputeDestinationPID;
    }

    public void setDisputeDestinationPID(String disputeDestinationPID) {
        this.disputeDestinationPID = disputeDestinationPID;
    }

    public String getAcquirerReferenceDataRRN() {
        return acquirerReferenceDataRRN;
    }

    public void setAcquirerReferenceDataRRN(String acquirerReferenceDataRRN) {
        this.acquirerReferenceDataRRN = acquirerReferenceDataRRN;
    }

    public int getTrsno() {
        return trsno;
    }

    public void setTrsno(int trsno) {
        this.trsno = trsno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
    
  
}
