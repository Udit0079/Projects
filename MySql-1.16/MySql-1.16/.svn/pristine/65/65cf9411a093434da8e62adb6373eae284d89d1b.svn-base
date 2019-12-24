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
@Table(name = "mb_charge_posting_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MbChargePostingHistory.findAll", query = "SELECT m FROM MbChargePostingHistory m"),
    @NamedQuery(name = "MbChargePostingHistory.findByTxnId", query = "SELECT m FROM MbChargePostingHistory m WHERE m.txnId = :txnId"),
    @NamedQuery(name = "MbChargePostingHistory.findByTrsNo", query = "SELECT m FROM MbChargePostingHistory m WHERE m.trsNo = :trsNo"),
    @NamedQuery(name = "MbChargePostingHistory.findByGlCrAmount", query = "SELECT m FROM MbChargePostingHistory m WHERE m.glCrAmount = :glCrAmount"),
    @NamedQuery(name = "MbChargePostingHistory.findByMessageType", query = "SELECT m FROM MbChargePostingHistory m WHERE m.messageType = :messageType"),
    @NamedQuery(name = "MbChargePostingHistory.findByEnterBy", query = "SELECT m FROM MbChargePostingHistory m WHERE m.enterBy = :enterBy"),
    @NamedQuery(name = "MbChargePostingHistory.findByTranDt", query = "SELECT m FROM MbChargePostingHistory m WHERE m.tranDt = :tranDt"),
    @NamedQuery(name = "MbChargePostingHistory.findByCrAcno", query = "SELECT m FROM MbChargePostingHistory m WHERE m.crAcno = :crAcno"),
    @NamedQuery(name = "MbChargePostingHistory.findByFromDate", query = "SELECT m FROM MbChargePostingHistory m WHERE m.fromDate = :fromDate"),
    @NamedQuery(name = "MbChargePostingHistory.findByToDate", query = "SELECT m FROM MbChargePostingHistory m WHERE m.toDate = :toDate"),
    @NamedQuery(name = "MbChargePostingHistory.findByAcType", query = "SELECT m FROM MbChargePostingHistory m WHERE m.acType = :acType")})
public class MbChargePostingHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXN_ID")
    private Long txnId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRS_NO")
    private double trsNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GL_CR_AMOUNT")
    private double glCrAmount;
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
    @Size(max = 20)
    @Column(name = "CR_ACNO")
    private String crAcno;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @Size(max = 2)
    @Column(name = "AC_TYPE")
    private String acType;

    public MbChargePostingHistory() {
    }

    public MbChargePostingHistory(Long txnId) {
        this.txnId = txnId;
    }

    public MbChargePostingHistory(Long txnId, double trsNo, double glCrAmount, String messageType, String enterBy, Date tranDt) {
        this.txnId = txnId;
        this.trsNo = trsNo;
        this.glCrAmount = glCrAmount;
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

    public double getGlCrAmount() {
        return glCrAmount;
    }

    public void setGlCrAmount(double glCrAmount) {
        this.glCrAmount = glCrAmount;
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

    public String getCrAcno() {
        return crAcno;
    }

    public void setCrAcno(String crAcno) {
        this.crAcno = crAcno;
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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
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
        if (!(object instanceof MbChargePostingHistory)) {
            return false;
        }
        MbChargePostingHistory other = (MbChargePostingHistory) object;
        if ((this.txnId == null && other.txnId != null) || (this.txnId != null && !this.txnId.equals(other.txnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbChargePostingHistory[ txnId=" + txnId + " ]";
    }
}
