/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "neft_rtgs_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NeftRtgsStatus.findAll", query = "SELECT n FROM NeftRtgsStatus n"),
    @NamedQuery(name = "NeftRtgsStatus.findByTxnId", query = "SELECT n FROM NeftRtgsStatus n WHERE n.neftRtgsStatusPK.txnId = :txnId"),
    @NamedQuery(name = "NeftRtgsStatus.findByUtr", query = "SELECT n FROM NeftRtgsStatus n WHERE n.utr = :utr"),
    @NamedQuery(name = "NeftRtgsStatus.findByBeneAccount", query = "SELECT n FROM NeftRtgsStatus n WHERE n.beneAccount = :beneAccount"),
    @NamedQuery(name = "NeftRtgsStatus.findByBeneName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.beneName = :beneName"),
    @NamedQuery(name = "NeftRtgsStatus.findByReceiverIfsc", query = "SELECT n FROM NeftRtgsStatus n WHERE n.receiverIfsc = :receiverIfsc"),
    @NamedQuery(name = "NeftRtgsStatus.findByAmount", query = "SELECT n FROM NeftRtgsStatus n WHERE n.amount = :amount"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderAccount", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderAccount = :senderAccount"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderName = :senderName"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderIfsc", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderIfsc = :senderIfsc"),
    @NamedQuery(name = "NeftRtgsStatus.findByTxnType", query = "SELECT n FROM NeftRtgsStatus n WHERE n.txnType = :txnType"),
    @NamedQuery(name = "NeftRtgsStatus.findByNarration", query = "SELECT n FROM NeftRtgsStatus n WHERE n.narration = :narration"),
    @NamedQuery(name = "NeftRtgsStatus.findByStatus", query = "SELECT n FROM NeftRtgsStatus n WHERE n.status = :status"),
    @NamedQuery(name = "NeftRtgsStatus.findByReason", query = "SELECT n FROM NeftRtgsStatus n WHERE n.reason = :reason"),
    @NamedQuery(name = "NeftRtgsStatus.findByDetails", query = "SELECT n FROM NeftRtgsStatus n WHERE n.details = :details"),
    @NamedQuery(name = "NeftRtgsStatus.findByDt", query = "SELECT n FROM NeftRtgsStatus n WHERE n.neftRtgsStatusPK.dt = :dt"),
    @NamedQuery(name = "NeftRtgsStatus.findByTranTime", query = "SELECT n FROM NeftRtgsStatus n WHERE n.tranTime = :tranTime"),
    @NamedQuery(name = "NeftRtgsStatus.findByEnterBy", query = "SELECT n FROM NeftRtgsStatus n WHERE n.enterBy = :enterBy"),
    @NamedQuery(name = "NeftRtgsStatus.findByAuthBy", query = "SELECT n FROM NeftRtgsStatus n WHERE n.authBy = :authBy"),
    @NamedQuery(name = "NeftRtgsStatus.findByValueDt", query = "SELECT n FROM NeftRtgsStatus n WHERE n.valueDt = :valueDt"),
    @NamedQuery(name = "NeftRtgsStatus.findByReceiverBankName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.receiverBankName = :receiverBankName"),
    @NamedQuery(name = "NeftRtgsStatus.findByReceiverBankCode", query = "SELECT n FROM NeftRtgsStatus n WHERE n.receiverBankCode = :receiverBankCode"),
    @NamedQuery(name = "NeftRtgsStatus.findByReceiverBankAddress", query = "SELECT n FROM NeftRtgsStatus n WHERE n.receiverBankAddress = :receiverBankAddress"),
    @NamedQuery(name = "NeftRtgsStatus.findBySponsorBankName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.sponsorBankName = :sponsorBankName"),
    @NamedQuery(name = "NeftRtgsStatus.findBySponsorBankCode", query = "SELECT n FROM NeftRtgsStatus n WHERE n.sponsorBankCode = :sponsorBankCode"),
    @NamedQuery(name = "NeftRtgsStatus.findBySponsorIfsc", query = "SELECT n FROM NeftRtgsStatus n WHERE n.sponsorIfsc = :sponsorIfsc"),
    @NamedQuery(name = "NeftRtgsStatus.findBySponsorRefNo", query = "SELECT n FROM NeftRtgsStatus n WHERE n.sponsorRefNo = :sponsorRefNo"),
    @NamedQuery(name = "NeftRtgsStatus.findBySponsorAddress", query = "SELECT n FROM NeftRtgsStatus n WHERE n.sponsorAddress = :sponsorAddress"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderBankName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderBankName = :senderBankName"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderBankCode", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderBankCode = :senderBankCode"),
    @NamedQuery(name = "NeftRtgsStatus.findByReasonCode", query = "SELECT n FROM NeftRtgsStatus n WHERE n.reasonCode = :reasonCode"),
    @NamedQuery(name = "NeftRtgsStatus.findByBeneAdd", query = "SELECT n FROM NeftRtgsStatus n WHERE n.beneAdd = :beneAdd"),
    @NamedQuery(name = "NeftRtgsStatus.findByRemittanceInfo", query = "SELECT n FROM NeftRtgsStatus n WHERE n.remittanceInfo = :remittanceInfo"),
    @NamedQuery(name = "NeftRtgsStatus.findByRemittanceOriginator", query = "SELECT n FROM NeftRtgsStatus n WHERE n.remittanceOriginator = :remittanceOriginator"),
    @NamedQuery(name = "NeftRtgsStatus.findByProcess", query = "SELECT n FROM NeftRtgsStatus n WHERE n.process = :process"),
    @NamedQuery(name = "NeftRtgsStatus.findByNeftBankName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.neftBankName = :neftBankName"),
    @NamedQuery(name = "NeftRtgsStatus.findByTrsNo", query = "SELECT n FROM NeftRtgsStatus n WHERE n.trsNo = :trsNo"),
    @NamedQuery(name = "NeftRtgsStatus.findByHeaderUtr", query = "SELECT n FROM NeftRtgsStatus n WHERE n.headerUtr = :headerUtr"),
    @NamedQuery(name = "NeftRtgsStatus.findByBatchTime", query = "SELECT n FROM NeftRtgsStatus n WHERE n.batchTime = :batchTime"),
    @NamedQuery(name = "NeftRtgsStatus.findByRelatedRefNo", query = "SELECT n FROM NeftRtgsStatus n WHERE n.relatedRefNo = :relatedRefNo"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderActype", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderActype = :senderActype"),
    @NamedQuery(name = "NeftRtgsStatus.findByBeneActype", query = "SELECT n FROM NeftRtgsStatus n WHERE n.beneActype = :beneActype"),
    @NamedQuery(name = "NeftRtgsStatus.findByRejectReason", query = "SELECT n FROM NeftRtgsStatus n WHERE n.rejectReason = :rejectReason"),
    @NamedQuery(name = "NeftRtgsStatus.findByRemittanceDate", query = "SELECT n FROM NeftRtgsStatus n WHERE n.remittanceDate = :remittanceDate"),
    @NamedQuery(name = "NeftRtgsStatus.findBySenderAddress", query = "SELECT n FROM NeftRtgsStatus n WHERE n.senderAddress = :senderAddress"),
    @NamedQuery(name = "NeftRtgsStatus.findByReturnTranRefNo", query = "SELECT n FROM NeftRtgsStatus n WHERE n.returnTranRefNo = :returnTranRefNo"),
    @NamedQuery(name = "NeftRtgsStatus.findByIwFileName", query = "SELECT n FROM NeftRtgsStatus n WHERE n.iwFileName = :iwFileName")})
public class NeftRtgsStatus extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NeftRtgsStatusPK neftRtgsStatusPK;
    @Size(max = 30)
    @Column(name = "UTR")
    private String utr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "BENE_ACCOUNT")
    private String beneAccount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "BENE_NAME")
    private String beneName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RECEIVER_IFSC")
    private String receiverIfsc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SENDER_ACCOUNT")
    private String senderAccount;
    @Size(max = 255)
    @Column(name = "SENDER_NAME")
    private String senderName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SENDER_IFSC")
    private String senderIfsc;
    @Size(max = 50)
    @Column(name = "TXN_TYPE")
    private String txnType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NARRATION")
    private String narration;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 255)
    @Column(name = "REASON")
    private String reason;
    @Size(max = 255)
    @Column(name = "DETAILS")
    private String details;
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
    @Column(name = "VALUE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDt;
    @Size(max = 255)
    @Column(name = "RECEIVER_BANK_NAME")
    private String receiverBankName;
    @Size(max = 50)
    @Column(name = "RECEIVER_BANK_CODE")
    private String receiverBankCode;
    @Size(max = 255)
    @Column(name = "RECEIVER_BANK_ADDRESS")
    private String receiverBankAddress;
    @Size(max = 255)
    @Column(name = "SPONSOR_BANK_NAME")
    private String sponsorBankName;
    @Size(max = 50)
    @Column(name = "SPONSOR_BANK_CODE")
    private String sponsorBankCode;
    @Size(max = 20)
    @Column(name = "SPONSOR_IFSC")
    private String sponsorIfsc;
    @Size(max = 50)
    @Column(name = "SPONSOR_REF_NO")
    private String sponsorRefNo;
    @Size(max = 255)
    @Column(name = "SPONSOR_ADDRESS")
    private String sponsorAddress;
    @Size(max = 255)
    @Column(name = "SENDER_BANK_NAME")
    private String senderBankName;
    @Size(max = 50)
    @Column(name = "SENDER_BANK_CODE")
    private String senderBankCode;
    @Size(max = 50)
    @Column(name = "reason_code")
    private String reasonCode;
    @Size(max = 255)
    @Column(name = "BENE_ADD")
    private String beneAdd;
    @Size(max = 255)
    @Column(name = "REMITTANCE_INFO")
    private String remittanceInfo;
    @Size(max = 255)
    @Column(name = "REMITTANCE_ORIGINATOR")
    private String remittanceOriginator;
    @Size(max = 10)
    @Column(name = "PROCESS")
    private String process;
    @Size(max = 10)
    @Column(name = "NEFT_BANK_NAME")
    private String neftBankName;
    @Column(name = "TRS_NO")
    private Float trsNo;
    @Size(max = 30)
    @Column(name = "HEADER_UTR")
    private String headerUtr;
    @Size(max = 4)
    @Column(name = "BATCH_TIME")
    private String batchTime;
    @Size(max = 30)
    @Column(name = "RELATED_REF_NO")
    private String relatedRefNo;
    @Size(max = 2)
    @Column(name = "SENDER_ACTYPE")
    private String senderActype;
    @Size(max = 2)
    @Column(name = "BENE_ACTYPE")
    private String beneActype;
    @Size(max = 50)
    @Column(name = "REJECT_REASON")
    private String rejectReason;
    @Column(name = "REMITTANCE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date remittanceDate;
    @Size(max = 255)
    @Column(name = "SENDER_ADDRESS")
    private String senderAddress;
    @Size(max = 22)
    @Column(name = "RETURN_TRAN_REF_NO")
    private String returnTranRefNo;
    @Size(max = 100)
    @Column(name = "IW_FILE_NAME")
    private String iwFileName;

    public NeftRtgsStatus() {
    }

    public NeftRtgsStatus(NeftRtgsStatusPK neftRtgsStatusPK) {
        this.neftRtgsStatusPK = neftRtgsStatusPK;
    }

    public NeftRtgsStatus(NeftRtgsStatusPK neftRtgsStatusPK, String beneAccount, String beneName, String receiverIfsc, BigDecimal amount, String senderAccount, String senderIfsc, String narration, String status, Date tranTime, String enterBy, String authBy) {
        this.neftRtgsStatusPK = neftRtgsStatusPK;
        this.beneAccount = beneAccount;
        this.beneName = beneName;
        this.receiverIfsc = receiverIfsc;
        this.amount = amount;
        this.senderAccount = senderAccount;
        this.senderIfsc = senderIfsc;
        this.narration = narration;
        this.status = status;
        this.tranTime = tranTime;
        this.enterBy = enterBy;
        this.authBy = authBy;
    }

    public NeftRtgsStatus(long txnId, Date dt) {
        this.neftRtgsStatusPK = new NeftRtgsStatusPK(txnId, dt);
    }

    public NeftRtgsStatusPK getNeftRtgsStatusPK() {
        return neftRtgsStatusPK;
    }

    public void setNeftRtgsStatusPK(NeftRtgsStatusPK neftRtgsStatusPK) {
        this.neftRtgsStatusPK = neftRtgsStatusPK;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getBeneAccount() {
        return beneAccount;
    }

    public void setBeneAccount(String beneAccount) {
        this.beneAccount = beneAccount;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getReceiverIfsc() {
        return receiverIfsc;
    }

    public void setReceiverIfsc(String receiverIfsc) {
        this.receiverIfsc = receiverIfsc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderIfsc() {
        return senderIfsc;
    }

    public void setSenderIfsc(String senderIfsc) {
        this.senderIfsc = senderIfsc;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public Date getValueDt() {
        return valueDt;
    }

    public void setValueDt(Date valueDt) {
        this.valueDt = valueDt;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }

    public String getReceiverBankAddress() {
        return receiverBankAddress;
    }

    public void setReceiverBankAddress(String receiverBankAddress) {
        this.receiverBankAddress = receiverBankAddress;
    }

    public String getSponsorBankName() {
        return sponsorBankName;
    }

    public void setSponsorBankName(String sponsorBankName) {
        this.sponsorBankName = sponsorBankName;
    }

    public String getSponsorBankCode() {
        return sponsorBankCode;
    }

    public void setSponsorBankCode(String sponsorBankCode) {
        this.sponsorBankCode = sponsorBankCode;
    }

    public String getSponsorIfsc() {
        return sponsorIfsc;
    }

    public void setSponsorIfsc(String sponsorIfsc) {
        this.sponsorIfsc = sponsorIfsc;
    }

    public String getSponsorRefNo() {
        return sponsorRefNo;
    }

    public void setSponsorRefNo(String sponsorRefNo) {
        this.sponsorRefNo = sponsorRefNo;
    }

    public String getSponsorAddress() {
        return sponsorAddress;
    }

    public void setSponsorAddress(String sponsorAddress) {
        this.sponsorAddress = sponsorAddress;
    }

    public String getSenderBankName() {
        return senderBankName;
    }

    public void setSenderBankName(String senderBankName) {
        this.senderBankName = senderBankName;
    }

    public String getSenderBankCode() {
        return senderBankCode;
    }

    public void setSenderBankCode(String senderBankCode) {
        this.senderBankCode = senderBankCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getBeneAdd() {
        return beneAdd;
    }

    public void setBeneAdd(String beneAdd) {
        this.beneAdd = beneAdd;
    }

    public String getRemittanceInfo() {
        return remittanceInfo;
    }

    public void setRemittanceInfo(String remittanceInfo) {
        this.remittanceInfo = remittanceInfo;
    }

    public String getRemittanceOriginator() {
        return remittanceOriginator;
    }

    public void setRemittanceOriginator(String remittanceOriginator) {
        this.remittanceOriginator = remittanceOriginator;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getNeftBankName() {
        return neftBankName;
    }

    public void setNeftBankName(String neftBankName) {
        this.neftBankName = neftBankName;
    }

    public Float getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(Float trsNo) {
        this.trsNo = trsNo;
    }

    public String getHeaderUtr() {
        return headerUtr;
    }

    public void setHeaderUtr(String headerUtr) {
        this.headerUtr = headerUtr;
    }

    public String getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }

    public String getRelatedRefNo() {
        return relatedRefNo;
    }

    public void setRelatedRefNo(String relatedRefNo) {
        this.relatedRefNo = relatedRefNo;
    }

    public String getSenderActype() {
        return senderActype;
    }

    public void setSenderActype(String senderActype) {
        this.senderActype = senderActype;
    }

    public String getBeneActype() {
        return beneActype;
    }

    public void setBeneActype(String beneActype) {
        this.beneActype = beneActype;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getRemittanceDate() {
        return remittanceDate;
    }

    public void setRemittanceDate(Date remittanceDate) {
        this.remittanceDate = remittanceDate;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReturnTranRefNo() {
        return returnTranRefNo;
    }

    public void setReturnTranRefNo(String returnTranRefNo) {
        this.returnTranRefNo = returnTranRefNo;
    }

    public String getIwFileName() {
        return iwFileName;
    }

    public void setIwFileName(String iwFileName) {
        this.iwFileName = iwFileName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (neftRtgsStatusPK != null ? neftRtgsStatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftRtgsStatus)) {
            return false;
        }
        NeftRtgsStatus other = (NeftRtgsStatus) object;
        if ((this.neftRtgsStatusPK == null && other.neftRtgsStatusPK != null) || (this.neftRtgsStatusPK != null && !this.neftRtgsStatusPK.equals(other.neftRtgsStatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftRtgsStatus[ neftRtgsStatusPK=" + neftRtgsStatusPK + " ]";
    }
}
