/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

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
public class NeftRtgsLoggingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "TXN_ID")
    private long txnId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;

    public NeftRtgsLoggingPK() {
    }

    public NeftRtgsLoggingPK(long txnId, Date dt) {
        this.txnId = txnId;
        this.dt = dt;
    }

    public long getTxnId() {
        return txnId;
    }

    public void setTxnId(long txnId) {
        this.txnId = txnId;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) txnId;
        hash += (dt != null ? dt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeftRtgsLoggingPK)) {
            return false;
        }
        NeftRtgsLoggingPK other = (NeftRtgsLoggingPK) object;
        if (this.txnId != other.txnId) {
            return false;
        }
        if ((this.dt == null && other.dt != null) || (this.dt != null && !this.dt.equals(other.dt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.NeftRtgsLoggingPK[ txnId=" + txnId + ", dt=" + dt + " ]";
    }
    
}
