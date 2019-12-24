/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Embeddable
public class MbPullMsgTabPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXN_ID")
    private long txnId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVED_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDt;

    public MbPullMsgTabPK() {
    }

    public MbPullMsgTabPK(long txnId, Date receivedDt) {
        this.txnId = txnId;
        this.receivedDt = receivedDt;
    }

    public long getTxnId() {
        return txnId;
    }

    public void setTxnId(long txnId) {
        this.txnId = txnId;
    }

    public Date getReceivedDt() {
        return receivedDt;
    }

    public void setReceivedDt(Date receivedDt) {
        this.receivedDt = receivedDt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) txnId;
        hash += (receivedDt != null ? receivedDt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbPullMsgTabPK)) {
            return false;
        }
        MbPullMsgTabPK other = (MbPullMsgTabPK) object;
        if (this.txnId != other.txnId) {
            return false;
        }
        if ((this.receivedDt == null && other.receivedDt != null) || (this.receivedDt != null && !this.receivedDt.equals(other.receivedDt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbPullMsgTabPK[ txnId=" + txnId + ", receivedDt=" + receivedDt + " ]";
    }
    
}
