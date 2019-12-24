/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class GoiReconPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "TXNID")
    private long txnid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRLNO")
    private long ctrlno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "ACNO")
    private String acno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECNO")
    private double recno;

    public GoiReconPK() {
    }

    public GoiReconPK(long txnid, long ctrlno, String acno, Date dt, double recno) {
        this.txnid = txnid;
        this.ctrlno = ctrlno;
        this.acno = acno;
        this.dt = dt;
        this.recno = recno;
    }

    public GoiReconPK(long ctrlno, String acno, Date dt, double recno) {
        this.ctrlno = ctrlno;
        this.acno = acno;
        this.dt = dt;
        this.recno = recno;
    }

    public long getTxnid() {
        return txnid;
    }

    public void setTxnid(long txnid) {
        this.txnid = txnid;
    }

    public long getCtrlno() {
        return ctrlno;
    }

    public void setCtrlno(long ctrlno) {
        this.ctrlno = ctrlno;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public double getRecno() {
        return recno;
    }

    public void setRecno(double recno) {
        this.recno = recno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) txnid;
        hash += (int) ctrlno;
        hash += (acno != null ? acno.hashCode() : 0);
        hash += (dt != null ? dt.hashCode() : 0);
        hash += (int) recno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoiReconPK)) {
            return false;
        }
        GoiReconPK other = (GoiReconPK) object;
        if (this.txnid != other.txnid) {
            return false;
        }
        if (this.ctrlno != other.ctrlno) {
            return false;
        }
        if ((this.acno == null && other.acno != null) || (this.acno != null && !this.acno.equals(other.acno))) {
            return false;
        }
        if ((this.dt == null && other.dt != null) || (this.dt != null && !this.dt.equals(other.dt))) {
            return false;
        }
        if (this.recno != other.recno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.GoiReconPK[ txnid=" + txnid + ", ctrlno=" + ctrlno + ", acno=" + acno + ", dt=" + dt + ", recno=" + recno + " ]";
    }
}
