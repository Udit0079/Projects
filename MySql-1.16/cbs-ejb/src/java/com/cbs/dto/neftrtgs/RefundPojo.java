/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.neftrtgs;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class RefundPojo implements Serializable{
    
    @CsvBindByName(column = "Report Date", required = true)
    String reportDate;
    @CsvBindByName(column = "Dispute Raise Date", required = true)
    String disputeRaiseDate;
    @CsvBindByName(column = "Dispute Raised Settlement Date", required = true)
    String disputeRaisedSettlementDate;
    @CsvBindByName(column = "Case Number", required = true)
    String caseNumber;
    @CsvBindByName(column = "Function Code", required = true)
    String functionCode;
    @CsvBindByName(column = "Function Code and Description", required = true)
    String functionCodeAndDescription;
    @CsvBindByName(column = "Primary Account Number", required = true)
    String primaryAccountNumber;
    @CsvBindByName(column = "Processing Code", required = true)
    String processingCode;
    @CsvBindByName(column = "Transaction Date", required = true)
    String transactionDate;
    @CsvBindByName(column = "Transaction Amount", required = true)
    String transactionAmount;
    @CsvBindByName(column = "Transaction Currency Code", required = true)
    String transactionCurrencyCode;
    @CsvBindByName(column = "Settlement Amount", required = true)
    String settlementAmount;
    @CsvBindByName(column = "Settlement Currency Code", required = true)
    String settlementCurrencyCode;
    @CsvBindByName(column = "Transaction Settlement Date", required = true)
    String transactionSettlementDate;
    @CsvBindByName(column = "Amounts, Additional", required = true)
    String amountsAdditional;
    @CsvBindByName(column = "Control Number", required = true)
    String controlNumber;
    @CsvBindByName(column = "Dispute Originator PID", required = true)
    String disputeOriginatorPID;
    @CsvBindByName(column = "Dispute Destination PID", required = true)
    String disputeDestinationPID;
    @CsvBindByName(column = "Acquirer Reference Data - RRN", required = true)
    String acquirerReferenceData;
    @CsvBindByName(column = "Approval Code", required = true)
    String approvalCode;
    @CsvBindByName(column = "Originator Point", required = true)
    String originatorPoint;
    @CsvBindByName(column = "POS Entry Mode", required = true)
    String posEntryMode;
    @CsvBindByName(column = "POS Condition Code", required = true)
    String posConditionCode;
    @CsvBindByName(column = "Acquirer Institution ID code", required = true)
    String acquirerInstitutionIDCode;
    @CsvBindByName(column = "Acquirer Name and Country", required = true)
    String acquirerNameAndCountry;
    @CsvBindByName(column = "Issuer Institution ID code", required = true)
    String issuerInstitutionIDCode;
    @CsvBindByName(column = "Issuer Name and Country", required = true)
    String issuerNameAndCountry;
    @CsvBindByName(column = "Card Type", required = true)
    String cardType;
    @CsvBindByName(column = "Card Brand", required = true)
    String cardBrand;
    @CsvBindByName(column = "Card Acceptor Terminal ID", required = true)
    String cardAcceptorTerminalID;
    @CsvBindByName(column = "Card Acceptor Name", required = true)
    String cardAcceptorName;
    @CsvBindByName(column = "Card Acceptor Location/address", required = true)
    String cardAcceptorLocation;
    @CsvBindByName(column = "Card Acceptor Country Code", required = true)
    String cardAcceptorCountryCode;
    @CsvBindByName(column = "Card Acceptor Business Code", required = true)
    String cardAcceptorBusinessCode;
    @CsvBindByName(column = "Dispute Reason code", required = false)
    String disputeReasonCode;
    @CsvBindByName(column = "Dispute Reason code description", required = false)
    String disputeReasonCodeDescription;
    @CsvBindByName(column = "Dispute Amount", required = true)
    String disputeAmount;
    @CsvBindByName(column = "Full / Partial Indicator", required = true)
    String fullOrPartialIndicator;
    @CsvBindByName(column = "Dispute Member Message text", required = false)
    String disputeMemberMessageText;
    @CsvBindByName(column = "Dispute Document Indicator", required = false)
    String disputeDocumentIndicator;
    @CsvBindByName(column = "Document Attached Date", required = false)
    String documentAttachedDate;
    @CsvBindByName(column = "MTI", required = true)
    String mti;
    @CsvBindByName(column = "Incentive amount", required = false)
    String incentiveAmount;
    @CsvBindByName(column = "Tier Code of non-fulfillment", required = false)
    String tierCodeOfNonFulfillment;
    @CsvBindByName(column = "Tier Code of Fulfillment", required = false)
    String tierCodeOfFulfillment;
    @CsvBindByName(column = "Deadline date", required = true)
    String deadlineDate;
    @CsvBindByName(column = "Days To act", required = true)
    String daysToAct;
    @CsvBindByName(column = "Direction of Dispute-Inward/Outward", required = true)
    String directionOfDispute;
    

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getDisputeRaiseDate() {
        return disputeRaiseDate;
    }

    public void setDisputeRaiseDate(String disputeRaiseDate) {
        this.disputeRaiseDate = disputeRaiseDate;
    }

    public String getDisputeRaisedSettlementDate() {
        return disputeRaisedSettlementDate;
    }

    public void setDisputeRaisedSettlementDate(String disputeRaisedSettlementDate) {
        this.disputeRaisedSettlementDate = disputeRaisedSettlementDate;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionCodeAndDescription() {
        return functionCodeAndDescription;
    }

    public void setFunctionCodeAndDescription(String functionCodeAndDescription) {
        this.functionCodeAndDescription = functionCodeAndDescription;
    }

    public String getPrimaryAccountNumber() {
        return primaryAccountNumber;
    }

    public void setPrimaryAccountNumber(String primaryAccountNumber) {
        this.primaryAccountNumber = primaryAccountNumber;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrencyCode() {
        return transactionCurrencyCode;
    }

    public void setTransactionCurrencyCode(String transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getSettlementCurrencyCode() {
        return settlementCurrencyCode;
    }

    public void setSettlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
    }

    public String getTransactionSettlementDate() {
        return transactionSettlementDate;
    }

    public void setTransactionSettlementDate(String transactionSettlementDate) {
        this.transactionSettlementDate = transactionSettlementDate;
    }

    public String getAmountsAdditional() {
        return amountsAdditional;
    }

    public void setAmountsAdditional(String amountsAdditional) {
        this.amountsAdditional = amountsAdditional;
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

    public String getAcquirerReferenceData() {
        return acquirerReferenceData;
    }

    public void setAcquirerReferenceData(String acquirerReferenceData) {
        this.acquirerReferenceData = acquirerReferenceData;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getOriginatorPoint() {
        return originatorPoint;
    }

    public void setOriginatorPoint(String originatorPoint) {
        this.originatorPoint = originatorPoint;
    }

    public String getPosEntryMode() {
        return posEntryMode;
    }

    public void setPosEntryMode(String posEntryMode) {
        this.posEntryMode = posEntryMode;
    }

    public String getPosConditionCode() {
        return posConditionCode;
    }

    public void setPosConditionCode(String posConditionCode) {
        this.posConditionCode = posConditionCode;
    }

    public String getAcquirerInstitutionIDCode() {
        return acquirerInstitutionIDCode;
    }

    public void setAcquirerInstitutionIDCode(String acquirerInstitutionIDCode) {
        this.acquirerInstitutionIDCode = acquirerInstitutionIDCode;
    }

    public String getAcquirerNameAndCountry() {
        return acquirerNameAndCountry;
    }

    public void setAcquirerNameAndCountry(String acquirerNameAndCountry) {
        this.acquirerNameAndCountry = acquirerNameAndCountry;
    }

    public String getIssuerInstitutionIDCode() {
        return issuerInstitutionIDCode;
    }

    public void setIssuerInstitutionIDCode(String issuerInstitutionIDCode) {
        this.issuerInstitutionIDCode = issuerInstitutionIDCode;
    }

    public String getIssuerNameAndCountry() {
        return issuerNameAndCountry;
    }

    public void setIssuerNameAndCountry(String issuerNameAndCountry) {
        this.issuerNameAndCountry = issuerNameAndCountry;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCardAcceptorTerminalID() {
        return cardAcceptorTerminalID;
    }

    public void setCardAcceptorTerminalID(String cardAcceptorTerminalID) {
        this.cardAcceptorTerminalID = cardAcceptorTerminalID;
    }

    public String getCardAcceptorName() {
        return cardAcceptorName;
    }

    public void setCardAcceptorName(String cardAcceptorName) {
        this.cardAcceptorName = cardAcceptorName;
    }

    public String getCardAcceptorLocation() {
        return cardAcceptorLocation;
    }

    public void setCardAcceptorLocation(String cardAcceptorLocation) {
        this.cardAcceptorLocation = cardAcceptorLocation;
    }

    public String getCardAcceptorCountryCode() {
        return cardAcceptorCountryCode;
    }

    public void setCardAcceptorCountryCode(String cardAcceptorCountryCode) {
        this.cardAcceptorCountryCode = cardAcceptorCountryCode;
    }

    public String getCardAcceptorBusinessCode() {
        return cardAcceptorBusinessCode;
    }

    public void setCardAcceptorBusinessCode(String cardAcceptorBusinessCode) {
        this.cardAcceptorBusinessCode = cardAcceptorBusinessCode;
    }

    public String getDisputeReasonCode() {
        return disputeReasonCode;
    }

    public void setDisputeReasonCode(String disputeReasonCode) {
        this.disputeReasonCode = disputeReasonCode;
    }

    public String getDisputeReasonCodeDescription() {
        return disputeReasonCodeDescription;
    }

    public void setDisputeReasonCodeDescription(String disputeReasonCodeDescription) {
        this.disputeReasonCodeDescription = disputeReasonCodeDescription;
    }

    public String getDisputeAmount() {
        return disputeAmount;
    }

    public void setDisputeAmount(String disputeAmount) {
        this.disputeAmount = disputeAmount;
    }

    public String getFullOrPartialIndicator() {
        return fullOrPartialIndicator;
    }

    public void setFullOrPartialIndicator(String fullOrPartialIndicator) {
        this.fullOrPartialIndicator = fullOrPartialIndicator;
    }

    public String getDisputeMemberMessageText() {
        return disputeMemberMessageText;
    }

    public void setDisputeMemberMessageText(String disputeMemberMessageText) {
        this.disputeMemberMessageText = disputeMemberMessageText;
    }

    public String getDisputeDocumentIndicator() {
        return disputeDocumentIndicator;
    }

    public void setDisputeDocumentIndicator(String disputeDocumentIndicator) {
        this.disputeDocumentIndicator = disputeDocumentIndicator;
    }

    public String getDocumentAttachedDate() {
        return documentAttachedDate;
    }

    public void setDocumentAttachedDate(String documentAttachedDate) {
        this.documentAttachedDate = documentAttachedDate;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getIncentiveAmount() {
        return incentiveAmount;
    }

    public void setIncentiveAmount(String incentiveAmount) {
        this.incentiveAmount = incentiveAmount;
    }

    public String getTierCodeOfNonFulfillment() {
        return tierCodeOfNonFulfillment;
    }

    public void setTierCodeOfNonFulfillment(String tierCodeOfNonFulfillment) {
        this.tierCodeOfNonFulfillment = tierCodeOfNonFulfillment;
    }

    public String getTierCodeOfFulfillment() {
        return tierCodeOfFulfillment;
    }

    public void setTierCodeOfFulfillment(String tierCodeOfFulfillment) {
        this.tierCodeOfFulfillment = tierCodeOfFulfillment;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getDaysToAct() {
        return daysToAct;
    }

    public void setDaysToAct(String daysToAct) {
        this.daysToAct = daysToAct;
    }

    public String getDirectionOfDispute() {
        return directionOfDispute;
    }

    public void setDirectionOfDispute(String directionOfDispute) {
        this.directionOfDispute = directionOfDispute;
    }

    @Override
    public String toString() {
        return "RefundPojo ["+reportDate + "," + disputeRaiseDate + "," + disputeRaisedSettlementDate + "," + caseNumber+ "," + functionCode + "," + functionCodeAndDescription + "," 
    + primaryAccountNumber+ "," + processingCode+ "," + transactionDate + "," + transactionAmount+ "," + transactionCurrencyCode + "," + settlementAmount
    + "," + settlementCurrencyCode + "," + transactionSettlementDate+ "," + amountsAdditional+ "," + controlNumber+ "," + disputeOriginatorPID
    + "," + disputeDestinationPID+ "," + acquirerReferenceData+ "," + approvalCode+ "," + originatorPoint+ "," + posEntryMode+ "," + posConditionCode
    + "," + acquirerInstitutionIDCode+ "," + acquirerNameAndCountry+ "," + issuerInstitutionIDCode+ "," + issuerNameAndCountry+ "," + cardType
    + "," + cardBrand+ "," + cardAcceptorTerminalID+ "," + cardAcceptorName+ "," + cardAcceptorLocation+ "," + cardAcceptorCountryCode+ "," + cardAcceptorBusinessCode
    + "," + disputeReasonCode+ "," + disputeReasonCodeDescription+ "," + disputeAmount+ "," + fullOrPartialIndicator+ "," + disputeMemberMessageText
    + "," + disputeDocumentIndicator+ "," + documentAttachedDate+ "," + mti+ "," + incentiveAmount+ "," + tierCodeOfNonFulfillment+ "," + tierCodeOfFulfillment
    + "," + deadlineDate+ "," + daysToAct+ "," + directionOfDispute+ "]"; 
    }
    
    
}
