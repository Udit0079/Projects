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
public class FdrReconPK implements Serializable {
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

    public FdrReconPK() {
    }

    public FdrReconPK(String acno, Date dt, double recno) {
        this.acno = acno;
        this.dt = dt;
        this.recno = recno;
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
        hash += (acno != null ? acno.hashCode() : 0);
        hash += (dt != null ? dt.hashCode() : 0);
        hash += (int) recno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FdrReconPK)) {
            return false;
        }
        FdrReconPK other = (FdrReconPK) object;
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
        return "com.cbs.entity.ho.investment.FdrReconPK[ acno=" + acno + ", dt=" + dt + ", recno=" + recno + " ]";
    }
    
}
