/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_npci_inward")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsNpciInward.findAll", query = "SELECT c FROM CbsNpciInward c"),
    @NamedQuery(name = "CbsNpciInward.findByTxnId", query = "SELECT c FROM CbsNpciInward c WHERE c.txnId = :txnId"),
    @NamedQuery(name = "CbsNpciInward.findByApbsTranCode", query = "SELECT c FROM CbsNpciInward c WHERE c.apbsTranCode = :apbsTranCode"),
    @NamedQuery(name = "CbsNpciInward.findByDestBankIin", query = "SELECT c FROM CbsNpciInward c WHERE c.destBankIin = :destBankIin"),
    @NamedQuery(name = "CbsNpciInward.findByDestActype", query = "SELECT c FROM CbsNpciInward c WHERE c.destActype = :destActype"),
    @NamedQuery(name = "CbsNpciInward.findByLedgerNo", query = "SELECT c FROM CbsNpciInward c WHERE c.ledgerNo = :ledgerNo"),
    @NamedQuery(name = "CbsNpciInward.findByBeneAadhaarNo", query = "SELECT c FROM CbsNpciInward c WHERE c.beneAadhaarNo = :beneAadhaarNo"),
    @NamedQuery(name = "CbsNpciInward.findByBeneName", query = "SELECT c FROM CbsNpciInward c WHERE c.beneName = :beneName"),
    @NamedQuery(name = "CbsNpciInward.findBySponsorBankIin", query = "SELECT c FROM CbsNpciInward c WHERE c.sponsorBankIin = :sponsorBankIin"),
    @NamedQuery(name = "CbsNpciInward.findByUserNo", query = "SELECT c FROM CbsNpciInward c WHERE c.userNo = :userNo"),
    @NamedQuery(name = "CbsNpciInward.findByUserNameNarration", query = "SELECT c FROM CbsNpciInward c WHERE c.userNameNarration = :userNameNarration"),
    @NamedQuery(name = "CbsNpciInward.findByUserCreditReference", query = "SELECT c FROM CbsNpciInward c WHERE c.userCreditReference = :userCreditReference"),
    @NamedQuery(name = "CbsNpciInward.findByAmount", query = "SELECT c FROM CbsNpciInward c WHERE c.amount = :amount"),
    @NamedQuery(name = "CbsNpciInward.findByReservedOne", query = "SELECT c FROM CbsNpciInward c WHERE c.reservedOne = :reservedOne"),
    @NamedQuery(name = "CbsNpciInward.findByReservedTwo", query = "SELECT c FROM CbsNpciInward c WHERE c.reservedTwo = :reservedTwo"),
    @NamedQuery(name = "CbsNpciInward.findByReservedThree", query = "SELECT c FROM CbsNpciInward c WHERE c.reservedThree = :reservedThree"),
    @NamedQuery(name = "CbsNpciInward.findByDestBankAcno", query = "SELECT c FROM CbsNpciInward c WHERE c.destBankAcno = :destBankAcno"),
    @NamedQuery(name = "CbsNpciInward.findByReturnReasonCode", query = "SELECT c FROM CbsNpciInward c WHERE c.returnReasonCode = :returnReasonCode"),
    @NamedQuery(name = "CbsNpciInward.findByStatus", query = "SELECT c FROM CbsNpciInward c WHERE c.status = :status"),
    @NamedQuery(name = "CbsNpciInward.findByReason", query = "SELECT c FROM CbsNpciInward c WHERE c.reason = :reason"),
    @NamedQuery(name = "CbsNpciInward.findByTranDate", query = "SELECT c FROM CbsNpciInward c WHERE c.tranDate = :tranDate"),
    @NamedQuery(name = "CbsNpciInward.findByTranTime", query = "SELECT c FROM CbsNpciInward c WHERE c.tranTime = :tranTime"),
    @NamedQuery(name = "CbsNpciInward.findByValueDate", query = "SELECT c FROM CbsNpciInward c WHERE c.valueDate = :valueDate"),
    @NamedQuery(name = "CbsNpciInward.findByEnterBy", query = "SELECT c FROM CbsNpciInward c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "CbsNpciInward.findByAuthBy", query = "SELECT c FROM CbsNpciInward c WHERE c.authBy = :authBy"),
    @NamedQuery(name = "CbsNpciInward.findByTrsNo", query = "SELECT c FROM CbsNpciInward c WHERE c.trsNo = :trsNo"),
    @NamedQuery(name = "CbsNpciInward.findBySettlementDate", query = "SELECT c FROM CbsNpciInward c WHERE c.settlementDate = :settlementDate"),
    @NamedQuery(name = "CbsNpciInward.findByIwType", query = "SELECT c FROM CbsNpciInward c WHERE c.iwType = :iwType"),
    @NamedQuery(name = "CbsNpciInward.findByAchSettlementCycle", query = "SELECT c FROM CbsNpciInward c WHERE c.achSettlementCycle = :achSettlementCycle"),
    @NamedQuery(name = "CbsNpciInward.findByAchControl2nd", query = "SELECT c FROM CbsNpciInward c WHERE c.achControl2nd = :achControl2nd"),
    @NamedQuery(name = "CbsNpciInward.findByAchControl5th", query = "SELECT c FROM CbsNpciInward c WHERE c.achControl5th = :achControl5th"),
    @NamedQuery(name = "CbsNpciInward.findByAchControl7th", query = "SELECT c FROM CbsNpciInward c WHERE c.achControl7th = :achControl7th"),
    @NamedQuery(name = "CbsNpciInward.findByAchControl8th", query = "SELECT c FROM CbsNpciInward c WHERE c.achControl8th = :achControl8th"),
    @NamedQuery(name = "CbsNpciInward.findByAchControl10th", query = "SELECT c FROM CbsNpciInward c WHERE c.achControl10th = :achControl10th"),
    @NamedQuery(name = "CbsNpciInward.findByAchItemSeqNo", query = "SELECT c FROM CbsNpciInward c WHERE c.achItemSeqNo = :achItemSeqNo"),
    @NamedQuery(name = "CbsNpciInward.findByAchChecksum", query = "SELECT c FROM CbsNpciInward c WHERE c.achChecksum = :achChecksum"),
    @NamedQuery(name = "CbsNpciInward.findByAchFiller", query = "SELECT c FROM CbsNpciInward c WHERE c.achFiller = :achFiller"),
    @NamedQuery(name = "CbsNpciInward.findByAchProductType", query = "SELECT c FROM CbsNpciInward c WHERE c.achProductType = :achProductType"),
    @NamedQuery(name = "CbsNpciInward.findByAchUmrn", query = "SELECT c FROM CbsNpciInward c WHERE c.achUmrn = :achUmrn"),
    @NamedQuery(name = "CbsNpciInward.findByAchReservedFlag", query = "SELECT c FROM CbsNpciInward c WHERE c.achReservedFlag = :achReservedFlag"),
    @NamedQuery(name = "CbsNpciInward.findByAchReservedReason", query = "SELECT c FROM CbsNpciInward c WHERE c.achReservedReason = :achReservedReason"),
    @NamedQuery(name = "CbsNpciInward.findByAchHeaderDestIin", query = "SELECT c FROM CbsNpciInward c WHERE c.achHeaderDestIin = :achHeaderDestIin"),
    @NamedQuery(name = "CbsNpciInward.findByCbsAcno", query = "SELECT c FROM CbsNpciInward c WHERE c.cbsAcno = :cbsAcno"),
    @NamedQuery(name = "CbsNpciInward.findByCbsAcname", query = "SELECT c FROM CbsNpciInward c WHERE c.cbsAcname = :cbsAcname"),
    @NamedQuery(name = "CbsNpciInward.findByFileName", query = "SELECT c FROM CbsNpciInward c WHERE c.fileName = :fileName")})
public class CbsNpciInward extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "APBS_TRAN_CODE")
    private String apbsTranCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "DEST_BANK_IIN")
    private String destBankIin;
    @Size(max = 2)
    @Column(name = "DEST_ACTYPE")
    private String destActype;
    @Size(max = 3)
    @Column(name = "LEDGER_NO")
    private String ledgerNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "BENE_AADHAAR_NO")
    private String beneAadhaarNo;
    @Size(max = 40)
    @Column(name = "BENE_NAME")
    private String beneName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "SPONSOR_BANK_IIN")
    private String sponsorBankIin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "USER_NO")
    private String userNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USER_NAME_NARRATION")
    private String userNameNarration;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USER_CREDIT_REFERENCE")
    private String userCreditReference;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "AMOUNT")
    private String amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "RESERVED_ONE")
    private String reservedOne;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "RESERVED_TWO")
    private String reservedTwo;
    @Size(max = 7)
    @Column(name = "RESERVED_THREE")
    private String reservedThree;
    @Size(max = 35)
    @Column(name = "DEST_BANK_ACNO")
    private String destBankAcno;
    @Size(max = 2)
    @Column(name = "RETURN_REASON_CODE")
    private String returnReasonCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "REASON")
    private String reason;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AUTH_BY")
    private String authBy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TRS_NO")
    private Double trsNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SETTLEMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date settlementDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "IW_TYPE")
    private String iwType;
    @Size(max = 2)
    @Column(name = "ACH_SETTLEMENT_CYCLE")
    private String achSettlementCycle;
    @Size(max = 9)
    @Column(name = "ACH_CONTROL_2ND")
    private String achControl2nd;
    @Size(max = 15)
    @Column(name = "ACH_CONTROL_5TH")
    private String achControl5th;
    @Size(max = 9)
    @Column(name = "ACH_CONTROL_7TH")
    private String achControl7th;
    @Size(max = 7)
    @Column(name = "ACH_CONTROL_8TH")
    private String achControl8th;
    @Size(max = 13)
    @Column(name = "ACH_CONTROL_10TH")
    private String achControl10th;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ACH_ITEM_SEQ_NO")
    private String achItemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ACH_CHECKSUM")
    private String achChecksum;
    @Size(max = 7)
    @Column(name = "ACH_FILLER")
    private String achFiller;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "ACH_PRODUCT_TYPE")
    private String achProductType;
    @Size(max = 20)
    @Column(name = "ACH_UMRN")
    private String achUmrn;
    @Size(max = 1)
    @Column(name = "ACH_RESERVED_FLAG")
    private String achReservedFlag;
    @Size(max = 2)
    @Column(name = "ACH_RESERVED_REASON")
    private String achReservedReason;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "ACH_HEADER_DEST_IIN")
    private String achHeaderDestIin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "CBS_ACNO")
    private String cbsAcno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CBS_ACNAME")
    private String cbsAcname;
    @Size(max = 100)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 1)
    @Column(name = "FILE_GEN_FLAG")
    private String fileGenFlag;

    public CbsNpciInward() {
    }

    public CbsNpciInward(Long txnId) {
        this.txnId = txnId;
    }

    public CbsNpciInward(Long txnId, String apbsTranCode, String destBankIin, String beneAadhaarNo, String sponsorBankIin, String userNo, String userNameNarration, String userCreditReference, String amount, String reservedOne, String reservedTwo, String status, String reason, Date tranDate, Date tranTime, Date valueDate, String enterBy, String authBy, Date settlementDate, String iwType, String achItemSeqNo, String achChecksum, String achProductType, String achHeaderDestIin, String cbsAcno, String cbsAcname) {
        this.txnId = txnId;
        this.apbsTranCode = apbsTranCode;
        this.destBankIin = destBankIin;
        this.beneAadhaarNo = beneAadhaarNo;
        this.sponsorBankIin = sponsorBankIin;
        this.userNo = userNo;
        this.userNameNarration = userNameNarration;
        this.userCreditReference = userCreditReference;
        this.amount = amount;
        this.reservedOne = reservedOne;
        this.reservedTwo = reservedTwo;
        this.status = status;
        this.reason = reason;
        this.tranDate = tranDate;
        this.tranTime = tranTime;
        this.valueDate = valueDate;
        this.enterBy = enterBy;
        this.authBy = authBy;
        this.settlementDate = settlementDate;
        this.iwType = iwType;
        this.achItemSeqNo = achItemSeqNo;
        this.achChecksum = achChecksum;
        this.achProductType = achProductType;
        this.achHeaderDestIin = achHeaderDestIin;
        this.cbsAcno = cbsAcno;
        this.cbsAcname = cbsAcname;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public String getApbsTranCode() {
        return apbsTranCode;
    }

    public void setApbsTranCode(String apbsTranCode) {
        this.apbsTranCode = apbsTranCode;
    }

    public String getDestBankIin() {
        return destBankIin;
    }

    public void setDestBankIin(String destBankIin) {
        this.destBankIin = destBankIin;
    }

    public String getDestActype() {
        return destActype;
    }

    public void setDestActype(String destActype) {
        this.destActype = destActype;
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

    public String getSponsorBankIin() {
        return sponsorBankIin;
    }

    public void setSponsorBankIin(String sponsorBankIin) {
        this.sponsorBankIin = sponsorBankIin;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserNameNarration() {
        return userNameNarration;
    }

    public void setUserNameNarration(String userNameNarration) {
        this.userNameNarration = userNameNarration;
    }

    public String getUserCreditReference() {
        return userCreditReference;
    }

    public void setUserCreditReference(String userCreditReference) {
        this.userCreditReference = userCreditReference;
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

    public String getReturnReasonCode() {
        return returnReasonCode;
    }

    public void setReturnReasonCode(String returnReasonCode) {
        this.returnReasonCode = returnReasonCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public Double getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(Double trsNo) {
        this.trsNo = trsNo;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getIwType() {
        return iwType;
    }

    public void setIwType(String iwType) {
        this.iwType = iwType;
    }

    public String getAchSettlementCycle() {
        return achSettlementCycle;
    }

    public void setAchSettlementCycle(String achSettlementCycle) {
        this.achSettlementCycle = achSettlementCycle;
    }

    public String getAchControl2nd() {
        return achControl2nd;
    }

    public void setAchControl2nd(String achControl2nd) {
        this.achControl2nd = achControl2nd;
    }

    public String getAchControl5th() {
        return achControl5th;
    }

    public void setAchControl5th(String achControl5th) {
        this.achControl5th = achControl5th;
    }

    public String getAchControl7th() {
        return achControl7th;
    }

    public void setAchControl7th(String achControl7th) {
        this.achControl7th = achControl7th;
    }

    public String getAchControl8th() {
        return achControl8th;
    }

    public void setAchControl8th(String achControl8th) {
        this.achControl8th = achControl8th;
    }

    public String getAchControl10th() {
        return achControl10th;
    }

    public void setAchControl10th(String achControl10th) {
        this.achControl10th = achControl10th;
    }

    public String getAchItemSeqNo() {
        return achItemSeqNo;
    }

    public void setAchItemSeqNo(String achItemSeqNo) {
        this.achItemSeqNo = achItemSeqNo;
    }

    public String getAchChecksum() {
        return achChecksum;
    }

    public void setAchChecksum(String achChecksum) {
        this.achChecksum = achChecksum;
    }

    public String getAchFiller() {
        return achFiller;
    }

    public void setAchFiller(String achFiller) {
        this.achFiller = achFiller;
    }

    public String getAchProductType() {
        return achProductType;
    }

    public void setAchProductType(String achProductType) {
        this.achProductType = achProductType;
    }

    public String getAchUmrn() {
        return achUmrn;
    }

    public void setAchUmrn(String achUmrn) {
        this.achUmrn = achUmrn;
    }

    public String getAchReservedFlag() {
        return achReservedFlag;
    }

    public void setAchReservedFlag(String achReservedFlag) {
        this.achReservedFlag = achReservedFlag;
    }

    public String getAchReservedReason() {
        return achReservedReason;
    }

    public void setAchReservedReason(String achReservedReason) {
        this.achReservedReason = achReservedReason;
    }

    public String getAchHeaderDestIin() {
        return achHeaderDestIin;
    }

    public void setAchHeaderDestIin(String achHeaderDestIin) {
        this.achHeaderDestIin = achHeaderDestIin;
    }

    public String getCbsAcno() {
        return cbsAcno;
    }

    public void setCbsAcno(String cbsAcno) {
        this.cbsAcno = cbsAcno;
    }

    public String getCbsAcname() {
        return cbsAcname;
    }

    public void setCbsAcname(String cbsAcname) {
        this.cbsAcname = cbsAcname;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileGenFlag() {
        return fileGenFlag;
    }

    public void setFileGenFlag(String fileGenFlag) {
        this.fileGenFlag = fileGenFlag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnId != null ? txnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsNpciInward)) {
            return false;
        }
        CbsNpciInward other = (CbsNpciInward) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.CbsNpciInward[ txnId=" + txnId + " ]";
    }
}
