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
@Table(name = "cbs_npci_inward_non_aadhaar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findAll", query = "SELECT c FROM CbsNpciInwardNonAadhaar c"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByTxnId", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.txnId = :txnId"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByOriginatorCode", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.originatorCode = :originatorCode"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByResponderCode", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.responderCode = :responderCode"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByFileComingDt", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.fileComingDt = :fileComingDt"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByFileSeqNo", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.fileSeqNo = :fileSeqNo"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByFileRefNo", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.fileRefNo = :fileRefNo"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByRecordIdentifier", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.recordIdentifier = :recordIdentifier"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByRrn", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.rrn = :rrn"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByDestBankIfsc", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.destBankIfsc = :destBankIfsc"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByDestBankAcno", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.destBankAcno = :destBankAcno"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByBenNameOrgn", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.benNameOrgn = :benNameOrgn"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByLpgId", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.lpgId = :lpgId"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByAcValidFlag", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.acValidFlag = :acValidFlag"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByBenNameMatchFlag", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.benNameMatchFlag = :benNameMatchFlag"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByBenNameResponder", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.benNameResponder = :benNameResponder"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByAadhaarNo", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.aadhaarNo = :aadhaarNo"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByMobileNo", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.mobileNo = :mobileNo"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByEmailId", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.emailId = :emailId"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByReturnCode", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.returnCode = :returnCode"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByStatus", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.status = :status"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByReason", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.reason = :reason"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByTranDate", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.tranDate = :tranDate"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByTranTime", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.tranTime = :tranTime"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByEnterBy", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.enterBy = :enterBy"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByAuthBy", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.authBy = :authBy"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByFiller", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.filler = :filler"),
    @NamedQuery(name = "CbsNpciInwardNonAadhaar.findByFileGenFlag", query = "SELECT c FROM CbsNpciInwardNonAadhaar c WHERE c.fileGenFlag = :fileGenFlag")})
public class CbsNpciInwardNonAadhaar extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "ORIGINATOR_CODE")
    private String originatorCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "RESPONDER_CODE")
    private String responderCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FILE_COMING_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fileComingDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "FILE_SEQ_NO")
    private String fileSeqNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FILE_REF_NO")
    private String fileRefNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RECORD_IDENTIFIER")
    private String recordIdentifier;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "RRN")
    private String rrn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "DEST_BANK_IFSC")
    private String destBankIfsc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "DEST_BANK_ACNO")
    private String destBankAcno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BEN_NAME_ORGN")
    private String benNameOrgn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "LPG_ID")
    private String lpgId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "AC_VALID_FLAG")
    private String acValidFlag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "BEN_NAME_MATCH_FLAG")
    private String benNameMatchFlag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "BEN_NAME_RESPONDER")
    private String benNameResponder;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "AADHAAR_NO")
    private String aadhaarNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "EMAIL_ID")
    private String emailId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RETURN_CODE")
    private String returnCode;
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
    @Size(min = 1, max = 50)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AUTH_BY")
    private String authBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 452)
    @Column(name = "FILLER")
    private String filler;
    @Size(max = 1)
    @Column(name = "FILE_GEN_FLAG")
    private String fileGenFlag;

    public CbsNpciInwardNonAadhaar() {
    }

    public CbsNpciInwardNonAadhaar(Long txnId) {
        this.txnId = txnId;
    }

    public CbsNpciInwardNonAadhaar(Long txnId, String originatorCode, String responderCode, Date fileComingDt, String fileSeqNo, String fileRefNo, String recordIdentifier, String rrn, String destBankIfsc, String destBankAcno, String benNameOrgn, String lpgId, String acValidFlag, String benNameMatchFlag, String benNameResponder, String aadhaarNo, String mobileNo, String emailId, String returnCode, String status, String reason, Date tranDate, Date tranTime, String enterBy, String authBy, String filler,String fileGenFlag) {
        this.txnId = txnId;
        this.originatorCode = originatorCode;
        this.responderCode = responderCode;
        this.fileComingDt = fileComingDt;
        this.fileSeqNo = fileSeqNo;
        this.fileRefNo = fileRefNo;
        this.recordIdentifier = recordIdentifier;
        this.rrn = rrn;
        this.destBankIfsc = destBankIfsc;
        this.destBankAcno = destBankAcno;
        this.benNameOrgn = benNameOrgn;
        this.lpgId = lpgId;
        this.acValidFlag = acValidFlag;
        this.benNameMatchFlag = benNameMatchFlag;
        this.benNameResponder = benNameResponder;
        this.aadhaarNo = aadhaarNo;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.returnCode = returnCode;
        this.status = status;
        this.reason = reason;
        this.tranDate = tranDate;
        this.tranTime = tranTime;
        this.enterBy = enterBy;
        this.authBy = authBy;
        this.filler = filler;
        this.fileGenFlag = fileGenFlag ;
     }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
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

    public Date getFileComingDt() {
        return fileComingDt;
    }

    public void setFileComingDt(Date fileComingDt) {
        this.fileComingDt = fileComingDt;
    }

    public String getFileSeqNo() {
        return fileSeqNo;
    }

    public void setFileSeqNo(String fileSeqNo) {
        this.fileSeqNo = fileSeqNo;
    }

    public String getFileRefNo() {
        return fileRefNo;
    }

    public void setFileRefNo(String fileRefNo) {
        this.fileRefNo = fileRefNo;
    }

    public String getRecordIdentifier() {
        return recordIdentifier;
    }

    public void setRecordIdentifier(String recordIdentifier) {
        this.recordIdentifier = recordIdentifier;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getDestBankIfsc() {
        return destBankIfsc;
    }

    public void setDestBankIfsc(String destBankIfsc) {
        this.destBankIfsc = destBankIfsc;
    }

    public String getDestBankAcno() {
        return destBankAcno;
    }

    public void setDestBankAcno(String destBankAcno) {
        this.destBankAcno = destBankAcno;
    }

    public String getBenNameOrgn() {
        return benNameOrgn;
    }

    public void setBenNameOrgn(String benNameOrgn) {
        this.benNameOrgn = benNameOrgn;
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

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
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

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
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
        if (!(object instanceof CbsNpciInwardNonAadhaar)) {
            return false;
        }
        CbsNpciInwardNonAadhaar other = (CbsNpciInwardNonAadhaar) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.CbsNpciInwardNonAadhaar[ txnId=" + txnId + " ]";
    }
}
