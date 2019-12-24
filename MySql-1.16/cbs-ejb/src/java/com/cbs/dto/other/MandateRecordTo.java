/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.other;

import java.io.Serializable;

public class MandateRecordTo implements Serializable {

    private static final long serialVersionUID = 56L;
    private String umrn;
    private String schemeName;
    private String instruction;
    private String dateOfEffect;
    private String periodicity;
    private String amtFlag;
    private String amt;
    private String debitAccount;
    private String mandateReceivedBranch;
    private String mandateReceivedIfsc;
    private String mandateReceivedMicr;
    private String mandateAmtPeriodicityFlag;
    private String installmentNo;
    private String fromDt;
    private String toDt;
    private String mandateUserNo;
    private String mandateUserName;
    private String mandateUserAdd;
    private String mandateForIfsc;
    private String mandateForMicr;
    private String creditAccount;
    private String enterBy;
    private String enterDt;
    /* New Format */
    private String sponserBankCode;
    private String utilityCode;
    private String creditorName;
    private String drAcType;
    private String drAcNo;
    private String drBankName;
    private String drBrnIFSC;
    private String drBrnMICR;
    private String drAmtType; 
    private String drAmt;
    private String frequency;
    private String period;
    private String ref1;
    private String ref2;
    private String mobile;
    private String email;

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDateOfEffect() {
        return dateOfEffect;
    }

    public void setDateOfEffect(String dateOfEffect) {
        this.dateOfEffect = dateOfEffect;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getAmtFlag() {
        return amtFlag;
    }

    public void setAmtFlag(String amtFlag) {
        this.amtFlag = amtFlag;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getMandateReceivedBranch() {
        return mandateReceivedBranch;
    }

    public void setMandateReceivedBranch(String mandateReceivedBranch) {
        this.mandateReceivedBranch = mandateReceivedBranch;
    }

    public String getMandateReceivedIfsc() {
        return mandateReceivedIfsc;
    }

    public void setMandateReceivedIfsc(String mandateReceivedIfsc) {
        this.mandateReceivedIfsc = mandateReceivedIfsc;
    }

    public String getMandateReceivedMicr() {
        return mandateReceivedMicr;
    }

    public void setMandateReceivedMicr(String mandateReceivedMicr) {
        this.mandateReceivedMicr = mandateReceivedMicr;
    }

    public String getMandateAmtPeriodicityFlag() {
        return mandateAmtPeriodicityFlag;
    }

    public void setMandateAmtPeriodicityFlag(String mandateAmtPeriodicityFlag) {
        this.mandateAmtPeriodicityFlag = mandateAmtPeriodicityFlag;
    }

    public String getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(String installmentNo) {
        this.installmentNo = installmentNo;
    }

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getMandateUserNo() {
        return mandateUserNo;
    }

    public void setMandateUserNo(String mandateUserNo) {
        this.mandateUserNo = mandateUserNo;
    }

    public String getMandateUserName() {
        return mandateUserName;
    }

    public void setMandateUserName(String mandateUserName) {
        this.mandateUserName = mandateUserName;
    }

    public String getMandateUserAdd() {
        return mandateUserAdd;
    }

    public void setMandateUserAdd(String mandateUserAdd) {
        this.mandateUserAdd = mandateUserAdd;
    }

    public String getMandateForIfsc() {
        return mandateForIfsc;
    }

    public void setMandateForIfsc(String mandateForIfsc) {
        this.mandateForIfsc = mandateForIfsc;
    }

    public String getMandateForMicr() {
        return mandateForMicr;
    }

    public void setMandateForMicr(String mandateForMicr) {
        this.mandateForMicr = mandateForMicr;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getEnterDt() {
        return enterDt;
    }

    public void setEnterDt(String enterDt) {
        this.enterDt = enterDt;
    }
    public String getCreditorName() {
        return creditorName;
    }
    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }
    public String getDrAcNo() {
        return drAcNo;
    }
    public void setDrAcNo(String drAcNo) {
        this.drAcNo = drAcNo;
    }
    public String getDrAcType() {
        return drAcType;
    }
    public void setDrAcType(String drAcType) {
        this.drAcType = drAcType;
    }
    public String getDrAmt() {
        return drAmt;
    }
    public void setDrAmt(String drAmt) {
        this.drAmt = drAmt;
    }
    public String getDrAmtType() {
        return drAmtType;
    }
    public void setDrAmtType(String drAmtType) {
        this.drAmtType = drAmtType;
    }
    public String getDrBankName() {
        return drBankName;
    }
    public void setDrBankName(String drBankName) {
        this.drBankName = drBankName;
    }
    public String getDrBrnIFSC() {
        return drBrnIFSC;
    }
    public void setDrBrnIFSC(String drBrnIFSC) {
        this.drBrnIFSC = drBrnIFSC;
    }
    public String getDrBrnMICR() {
        return drBrnMICR;
    }
    public void setDrBrnMICR(String drBrnMICR) {
        this.drBrnMICR = drBrnMICR;
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
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getRef1() {
        return ref1;
    }
    public void setRef1(String ref1) {
        this.ref1 = ref1;
    }
    public String getRef2() {
        return ref2;
    }
    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }
    public String getSponserBankCode() {
        return sponserBankCode;
    }
    public void setSponserBankCode(String sponserBankCode) {
        this.sponserBankCode = sponserBankCode;
    }
    public String getUtilityCode() {
        return utilityCode;
    }
    public void setUtilityCode(String utilityCode) {
        this.utilityCode = utilityCode;
    }
}
