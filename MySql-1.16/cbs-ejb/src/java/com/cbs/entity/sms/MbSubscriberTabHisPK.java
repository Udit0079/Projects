/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.sms;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class MbSubscriberTabHisPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXN_ID")
    private long txnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "ACNO")
    private String acno;

    public MbSubscriberTabHisPK() {
    }

    public MbSubscriberTabHisPK(long txnId, String acno) {
        this.txnId = txnId;
        this.acno = acno;
    }

    public long getTxnId() {
        return txnId;
    }

    public void setTxnId(long txnId) {
        this.txnId = txnId;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) txnId;
        hash += (acno != null ? acno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MbSubscriberTabHisPK)) {
            return false;
        }
        MbSubscriberTabHisPK other = (MbSubscriberTabHisPK) object;
        if (this.txnId != other.txnId) {
            return false;
        }
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.sms.MbSubscriberTabHisPK[ txnId=" + txnId + ", acno=" + acno + " ]";
    }
    
}
