/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

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
@Table(name = "mb_charge_posting_indivisual_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findAll", query = "SELECT m FROM MbChargePostingIndivisualHistory m"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByTxnId", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByTrsNo", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.trsNo = :trsNo"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByAcno", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.acno = :acno"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByFromDate", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.fromDate = :fromDate"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByToDate", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.toDate = :toDate"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByDrAmount", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.drAmount = :drAmount"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByNoOfMessage", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.noOfMessage = :noOfMessage"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByMessageType", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.messageType = :messageType"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByEnterBy", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbChargePostingIndivisualHistory.findByTranDt", query = "SELECT m FROM MbChargePostingIndivisualHistory m WHERE m.tranDt = :tranDt")})
public class MbChargePostingIndivisualHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRS_NO")
    private double trsNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "ACNO")
    private String acno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DR_AMOUNT")
    private double drAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_OF_MESSAGE")
    private long noOfMessage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "MESSAGE_TYPE")
    private String messageType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ENTER_BY")
    private String enterBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRAN_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDt;

    public MbChargePostingIndivisualHistory() {
    }

    public MbChargePostingIndivisualHistory(Long txnId) {
        this.txnId = txnId;
    }

    public MbChargePostingIndivisualHistory(Long txnId, double trsNo, String acno, Date fromDate, Date toDate, double drAmount, long noOfMessage, String messageType, String enterBy, Date tranDt) {
        this.txnId = txnId;
        this.trsNo = trsNo;
        this.acno = acno;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.drAmount = drAmount;
        this.noOfMessage = noOfMessage;
        this.messageType = messageType;
        this.enterBy = enterBy;
        this.tranDt = tranDt;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public double getTrsNo() {
        return trsNo;
    }

    public void setTrsNo(double trsNo) {
        this.trsNo = trsNo;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public double getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(double drAmount) {
        this.drAmount = drAmount;
    }

    public long getNoOfMessage() {
        return noOfMessage;
    }

    public void setNoOfMessage(long noOfMessage) {
        this.noOfMessage = noOfMessage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public Date getTranDt() {
        return tranDt;
    }

    public void setTranDt(Date tranDt) {
        this.tranDt = tranDt;
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
        if (!(object instanceof MbChargePostingIndivisualHistory)) {
            return false;
        }
        MbChargePostingIndivisualHistory other = (MbChargePostingIndivisualHistory) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbChargePostingIndivisualHistory[ txnId=" + txnId + " ]";
    }
}
