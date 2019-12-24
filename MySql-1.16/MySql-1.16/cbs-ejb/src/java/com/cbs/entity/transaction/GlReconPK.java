/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.transaction;

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
public class GlReconPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "ACNo")
    private String aCNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recno")
    private double recno;

    public GlReconPK() {
    }

    public GlReconPK(String aCNo, Date dt, double recno) {
        this.aCNo = aCNo;
        this.dt = dt;
        this.recno = recno;
    }

    public String getACNo() {
        return aCNo;
    }

    public void setACNo(String aCNo) {
        this.aCNo = aCNo;
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
        hash += (aCNo != null ? aCNo.hashCode() : 0);
        hash += (dt != null ? dt.hashCode() : 0);
        hash += (int) recno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GlReconPK)) {
            return false;
        }
        GlReconPK other = (GlReconPK) object;
        if ((this.aCNo == null && other.aCNo != null) || (this.aCNo != null && !this.aCNo.equals(other.aCNo))) {
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
        return "com.cbs.entity.transaction.GlReconPK[ aCNo=" + aCNo + ", dt=" + dt + ", recno=" + recno + " ]";
    }
    
}
