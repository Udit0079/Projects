/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.io.Serializable;

public class NpciInwardDto implements Serializable {

    //Do't delete any comments.
    private String apbsTranCode;    //Non-Aadhaar A/c Ver.--Record_Identifier,  //ACH Cr--ACH Transaction Code(Detail)
    private String destBankIIN;     //Non-Aadhaar A/c Ver.--Dest_Bank_Ifsc,     //ACH Cr--Destination Bank IFSC / MICR / IIN      
    private String destAcType;                                                  //ACH-CR--Destination Account Type
    private String ledgerNo;                                                    //ACH-CR--Ledger Folio Number
    private String beneAadhaarNo;   //Non-Aadhaar A/c Ver.--Aadhaar_No,         //ACH-CR--Beneficiary Aadhaar Number
    private String beneName;        //Non-Aadhaar A/c Ver.--Ben_Name_Orgn,      //ACH-CR--Beneficiary Account Holders Name
    private String sponsorBankIIN;                                              //ACH-CR--Sponsor Bank IFSC / MICR / IIN
    private String userNumber;                                                  //ACH-CR--User Number
    private String userName;                                                    //ACH-CR--User Name / Narration
    private String userCreditRef;   //Non-Aadhaar A/c Ver.--RRN,                //ACH-CR--Transaction Reference
    private String amount;                                                      //ACH-CR--Amount
    private String reservedOne;     //Non-Aadhaar A/c Ver.--Mobile
    private String reservedTwo;     //Non-Aadhaar--Email
    private String reservedThree;
    private String destBankAcno;    //Non-Aadhaar A/c Ver.--Dest_Bank_Acno,     //ACH-CR--Beneficiarys Bank Account number
    private String retReasonCode;
    private String settlementDt;    //Non-Aadhaar A/c Ver.--File_Coming_Dt,     //ACH-CR--Settlement Date(Header)    
    //New Non-Aadhaar A/c Ver Inward Fields.
    private String originatorCode;
    private String responderCode;
    private String fileSeqNo;
    private String lpgId;
    private String acValidFlag;
    private String benNameMatchFlag;
    private String benNameResponder;
    private String fileRefNo;
    private String filler;
    //New ACH Inward Fields.
    private String settlementCycle;
    private String controll2nd;
    private String controll5th;
    private String controll7th;
    private String controll8th;
    private String controll10th;
    private String itemSeqNo;
    private String checkSum;
    private String achFiller;
    private String productType;
    private String umrn;
    private String reservedFlag;
    private String reservedReason;
    private String headerDestIIN;
    //New Addition for OAC
    private String headerId;
    private String recordId;
    private String oldAcno;
    private String oldAcName;
    private String tranRef;
    private String cbsAcno;
    private String cbsAcName;
    //New Modification in ECS DR inward file - 11/03/2016
    private String fileName;
    private String fileNameDt;
    //New Addition for aadhar upload response -23/03/2018
    private String reasonCode;
    
    public String getApbsTranCode() {
        return apbsTranCode;
    }

    public void setApbsTranCode(String apbsTranCode) {
        this.apbsTranCode = apbsTranCode;
    }

    public String getDestBankIIN() {
        return destBankIIN;
    }

    public void setDestBankIIN(String destBankIIN) {
        this.destBankIIN = destBankIIN;
    }

    public String getDestAcType() {
        return destAcType;
    }

    public void setDestAcType(String destAcType) {
        this.destAcType = destAcType;
    }

    public String getLedgerNo() {
        return ledgerNo;
    }

    public void setLedgerNo(String ledgerNo) {
        this.ledgerNo = ledgerNo;
    }

    public String getBeneAadhaarNo() {
        return beneAadhaarNo;
    }

    public void setBeneAadhaarNo(String beneAadhaarNo) {
        this.beneAadhaarNo = beneAadhaarNo;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getSponsorBankIIN() {
        return sponsorBankIIN;
    }

    public void setSponsorBankIIN(String sponsorBankIIN) {
        this.sponsorBankIIN = sponsorBankIIN;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCreditRef() {
        return userCreditRef;
    }

    public void setUserCreditRef(String userCreditRef) {
        this.userCreditRef = userCreditRef;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReservedOne() {
        return reservedOne;
    }

    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    public String getReservedTwo() {
        return reservedTwo;
    }

    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    public String getReservedThree() {
        return reservedThree;
    }

    public void setReservedThree(String reservedThree) {
        this.reservedThree = reservedThree;
    }

    public String getDestBankAcno() {
        return destBankAcno;
    }

    public void setDestBankAcno(String destBankAcno) {
        this.destBankAcno = destBankAcno;
    }

    public String getRetReasonCode() {
        return retReasonCode;
    }

    public void setRetReasonCode(String retReasonCode) {
        this.retReasonCode = retReasonCode;
    }

    public String getSettlementDt() {
        return settlementDt;
    }

    public void setSettlementDt(String settlementDt) {
        this.settlementDt = settlementDt;
    }

    public String getOriginatorCode() {
        return originatorCode;
    }

    public void setOriginatorCode(String originatorCode) {
        this.originatorCode = originatorCode;
    }

    public String getResponderCode() {
        return responderCode;
    }

    public void setResponderCode(String responderCode) {
        this.responderCode = responderCode;
    }

    public String getFileSeqNo() {
        return fileSeqNo;
    }

    public void setFileSeqNo(String fileSeqNo) {
        this.fileSeqNo = fileSeqNo;
    }

    public String getLpgId() {
        return lpgId;
    }

    public void setLpgId(String lpgId) {
        this.lpgId = lpgId;
    }

    public String getAcValidFlag() {
        return acValidFlag;
    }

    public void setAcValidFlag(String acValidFlag) {
        this.acValidFlag = acValidFlag;
    }

    public String getBenNameMatchFlag() {
        return benNameMatchFlag;
    }

    public void setBenNameMatchFlag(String benNameMatchFlag) {
        this.benNameMatchFlag = benNameMatchFlag;
    }

    public String getBenNameResponder() {
        return benNameResponder;
    }

    public void setBenNameResponder(String benNameResponder) {
        this.benNameResponder = benNameResponder;
    }

    public String getFileRefNo() {
        return fileRefNo;
    }

    public void setFileRefNo(String fileRefNo) {
        this.fileRefNo = fileRefNo;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getSettlementCycle() {
        return settlementCycle;
    }

    public void setSettlementCycle(String settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public String getControll2nd() {
        return controll2nd;
    }

    public void setControll2nd(String controll2nd) {
        this.controll2nd = controll2nd;
    }

    public String getControll5th() {
        return controll5th;
    }

    public void setControll5th(String controll5th) {
        this.controll5th = controll5th;
    }

    public String getControll7th() {
        return controll7th;
    }

    public void setControll7th(String controll7th) {
        this.controll7th = controll7th;
    }

    public String getControll8th() {
        return controll8th;
    }

    public void setControll8th(String controll8th) {
        this.controll8th = controll8th;
    }

    public String getControll10th() {
        return controll10th;
    }

    public void setControll10th(String controll10th) {
        this.controll10th = controll10th;
    }

    public String getItemSeqNo() {
        return itemSeqNo;
    }

    public void setItemSeqNo(String itemSeqNo) {
        this.itemSeqNo = itemSeqNo;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getAchFiller() {
        return achFiller;
    }

    public void setAchFiller(String achFiller) {
        this.achFiller = achFiller;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getReservedFlag() {
        return reservedFlag;
    }

    public void setReservedFlag(String reservedFlag) {
        this.reservedFlag = reservedFlag;
    }

    public String getReservedReason() {
        return reservedReason;
    }

    public void setReservedReason(String reservedReason) {
        this.reservedReason = reservedReason;
    }

    public String getHeaderDestIIN() {
        return headerDestIIN;
    }

    public void setHeaderDestIIN(String headerDestIIN) {
        this.headerDestIIN = headerDestIIN;
    }

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getOldAcName() {
        return oldAcName;
    }

    public void setOldAcName(String oldAcName) {
        this.oldAcName = oldAcName;
    }

    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(String tranRef) {
        this.tranRef = tranRef;
    }

    public String getCbsAcno() {
        return cbsAcno;
    }

    public void setCbsAcno(String cbsAcno) {
        this.cbsAcno = cbsAcno;
    }

    public String getCbsAcName() {
        return cbsAcName;
    }

    public void setCbsAcName(String cbsAcName) {
        this.cbsAcName = cbsAcName;
    }

    public String getFileNameDt() {
        return fileNameDt;
    }

    public void setFileNameDt(String fileNameDt) {
        this.fileNameDt = fileNameDt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
