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
@Table(name = "neft_rtgs_logging")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NeftRtgsLogging.findAll", query = "SELECT n FROM NeftRtgsLogging n"),
    @NamedQuery(name = "NeftRtgsLogging.findByTxnId", query = "SELECT n FROM NeftRtgsLogging n WHERE n.neftRtgsLoggingPK.txnId = :txnId"),
    @NamedQuery(name = "NeftRtgsLogging.findByCoopBankAccountNo", query = "SELECT n FROM NeftRtgsLogging n WHERE n.coopBankAccountNo = :coopBankAccountNo"),
    @NamedQuery(name = "NeftRtgsLogging.findByIfscCode", query = "SELECT n FROM NeftRtgsLogging n WHERE n.ifscCode = :ifscCode"),
    @NamedQuery(name = "NeftRtgsLogging.findByUtr", query = "SELECT n FROM NeftRtgsLogging n WHERE n.utr = :utr"),
    @NamedQuery(name = "NeftRtgsLogging.findByBeneAccount", query = "SELECT n FROM NeftRtgsLogging n WHERE n.beneAccount = :beneAccount"),
    @NamedQuery(name = "NeftRtgsLogging.findByBeneName", query = "SELECT n FROM NeftRtgsLogging n WHERE n.beneName = :beneName"),
    @NamedQuery(name = "NeftRtgsLogging.findByBeneAddress", query = "SELECT n FROM NeftRtgsLogging n WHERE n.beneAddress = :beneAddress"),
    @NamedQuery(name = "NeftRtgsLogging.findByReceiverIfsc", query = "SELECT n FROM NeftRtgsLogging n WHERE n.receiverIfsc = :receiverIfsc"),
    @NamedQuery(name = "NeftRtgsLogging.findByAmount", query = "SELECT n FROM NeftRtgsLogging n WHERE n.amount = :amount"),
    @NamedQuery(name = "NeftRtgsLogging.findByTimestamp", query = "SELECT n FROM NeftRtgsLogging n WHERE n.timestamp = :timestamp"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderAccount", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderAccount = :senderAccount"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderName", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderName = :senderName"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderIfsc", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderIfsc = :senderIfsc"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderAddress1", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderAddress1 = :senderAddress1"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderAddress2", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderAddress2 = :senderAddress2"),
    @NamedQuery(name = "NeftRtgsLogging.findByTxnType", query = "SELECT n FROM NeftRtgsLogging n WHERE n.txnType = :txnType"),
    @NamedQuery(name = "NeftRtgsLogging.findByDt", query = "SELECT n FROM NeftRtgsLogging n WHERE n.neftRtgsLoggingPK.dt = :dt"),
    @NamedQuery(name = "NeftRtgsLogging.findByTranTime", query = "SELECT n FROM NeftRtgsLogging n WHERE n.tranTime = :tranTime"),
    @NamedQuery(name = "NeftRtgsLogging.findByEnterBy", query = "SELECT n FROM NeftRtgsLogging n WHERE n.enterBy = :enterBy"),
    @NamedQuery(name = "NeftRtgsLogging.findByAuthBy", query = "SELECT n FROM NeftRtgsLogging n WHERE n.authBy = :authBy"),
    @NamedQuery(name = "NeftRtgsLogging.findByValueDt", query = "SELECT n FROM NeftRtgsLogging n WHERE n.valueDt = :valueDt"),
    @NamedQuery(name = "NeftRtgsLogging.findByReceiverBankName", query = "SELECT n FROM NeftRtgsLogging n WHERE n.receiverBankName = :receiverBankName"),
    @NamedQuery(name = "NeftRtgsLogging.findByReceiverBankCode", query = "SELECT n FROM NeftRtgsLogging n WHERE n.receiverBankCode = :receiverBankCode"),
    @NamedQuery(name = "NeftRtgsLogging.findByReceiverBankAddress", query = "SELECT n FROM NeftRtgsLogging n WHERE n.receiverBankAddress = :receiverBankAddress"),
    @NamedQuery(name = "NeftRtgsLogging.findBySponsorBankName", query = "SELECT n FROM NeftRtgsLogging n WHERE n.sponsorBankName = :sponsorBankName"),
    @NamedQuery(name = "NeftRtgsLogging.findBySponsorBankCode", query = "SELECT n FROM NeftRtgsLogging n WHERE n.sponsorBankCode = :sponsorBankCode"),
    @NamedQuery(name = "NeftRtgsLogging.findBySponsorIfsc", query = "SELECT n FROM NeftRtgsLogging n WHERE n.sponsorIfsc = :sponsorIfsc"),
    @NamedQuery(name = "NeftRtgsLogging.findBySponsorRefNo", query = "SELECT n FROM NeftRtgsLogging n WHERE n.sponsorRefNo = :sponsorRefNo"),
    @NamedQuery(name = "NeftRtgsLogging.findBySponsorAddress", query = "SELECT n FROM NeftRtgsLogging n WHERE n.sponsorAddress = :sponsorAddress"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderBankName", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderBankName = :senderBankName"),
    @NamedQuery(name = "NeftRtgsLogging.findBySenderBankCode", query = "SELECT n FROM NeftRtgsLogging n WHERE n.senderBankCode = :senderBankCode")})
public class NeftRtgsLogging extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NeftRtgsLoggingPK neftRtgsLoggingPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "COOP_BANK_ACCOUNT_NO")
    private String coopBankAccountNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "IFSC_CODE")
    private String ifscCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
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
    @Size(max = 255)
    @Column(name = "BENE_ADDRESS")
    private String beneAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "RECEIVER_IFSC")
    private String receiverIfsc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
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
    @Size(max = 255)
    @Column(name = "SENDER_ADDRESS1")
    private String senderAddress1;
    @Size(max = 255)
    @Column(name = "SENDER_ADDRESS2")
    private String senderAddress2;
    @Size(max = 50)
    @Column(name = "TXN_TYPE")
    private String txnType;
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

    public NeftRtgsLogging() {
    }

    public NeftRtgsLogging(NeftRtgsLoggingPK neftRtgsLoggingPK) {
        this.neftRtgsLoggingPK = neftRtgsLoggingPK;
    }

    public NeftRtgsLogging(NeftRtgsLoggingPK neftRtgsLoggingPK, String coopBankAccountNo, String ifscCode, String utr, String beneAccount, String beneName, String receiverIfsc, BigDecimal amount, String senderAccount, String senderIfsc, Date tranTime, String enterBy, String authBy) {
        this.neftRtgsLoggingPK = neftRtgsLoggingPK;
        this.coopBankAccountNo = coopBankAccountNo;
        this.ifscCode = ifscCode;
        this.utr = utr;
        this.beneAccount = beneAccount;
        this.beneName = beneName;
        this.receiverIfsc = receiverIfsc;
        this.amount = amount;
        this.senderAccount = senderAccount;
        this.senderIfsc = senderIfsc;
        this.tranTime = tranTime;
        this.enterBy = enterBy;
        this.authBy = authBy;
    }

    public NeftRtgsLogging(long txnId, Date dt) {
        this.neftRtgsLoggingPK = new NeftRtgsLoggingPK(txnId, dt);
    }

    public NeftRtgsLoggingPK getNeftRtgsLoggingPK() {
        return neftRtgsLoggingPK;
    }

    public void setNeftRtgsLoggingPK(NeftRtgsLoggingPK neftRtgsLoggingPK) {
        this.neftRtgsLoggingPK = neftRtgsLoggingPK;
    }

    public String getCoopBankAccountNo() {
        return coopBankAccountNo;
    }

    public void setCoopBankAccountNo(String coopBankAccountNo) {
        this.coopBankAccountNo = coopBankAccountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
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

    public String getBeneAddress() {
        return beneAddress;
    }

    public void setBeneAddress(String beneAddress) {
        this.beneAddress = beneAddress;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public String getSenderAddress1() {
        return senderAddress1;
    }

    public void setSenderAddress1(String senderAddress1) {
        this.senderAddress1 = senderAddress1;
    }

    public String getSenderAddress2() {
        return senderAddress2;
    }

    public void setSenderAddress2(String senderAddress2) {
        this.senderAddress2 = senderAddress2;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (neftRtgsLoggingPK != null ? neftRtgsLoggingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftRtgsLogging)) {
            return false;
        }
        NeftRtgsLogging other = (NeftRtgsLogging) object;
        if ((this.neftRtgsLoggingPK == null && other.neftRtgsLoggingPK != null) || (this.neftRtgsLoggingPK != null && !this.neftRtgsLoggingPK.equals(other.neftRtgsLoggingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftRtgsLogging[ neftRtgsLoggingPK=" + neftRtgsLoggingPK + " ]";
    }
}
