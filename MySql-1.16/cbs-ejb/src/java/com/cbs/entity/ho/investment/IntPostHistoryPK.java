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
public class IntPostHistoryPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "actype")
    private String actype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fromdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "brncode")
    private String brncode;

    public IntPostHistoryPK() {
    }

    public IntPostHistoryPK(String actype, Date fromdate, String brncode) {
        this.actype = actype;
        this.fromdate = fromdate;
        this.brncode = brncode;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public String getBrncode() {
        return brncode;
    }

    public void setBrncode(String brncode) {
        this.brncode = brncode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actype != null ? actype.hashCode() : 0);
        hash += (fromdate != null ? fromdate.hashCode() : 0);
        hash += (brncode != null ? brncode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntPostHistoryPK)) {
            return false;
        }
        IntPostHistoryPK other = (IntPostHistoryPK) object;
        if ((this.actype == null && other.actype != null) || (this.actype != null && !this.actype.equals(other.actype))) {
            return false;
        }
        if ((this.fromdate == null && other.fromdate != null) || (this.fromdate != null && !this.fromdate.equals(other.fromdate))) {
            return false;
        }
        if ((this.brncode == null && other.brncode != null) || (this.brncode != null && !this.brncode.equals(other.brncode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.IntPostHistoryPK[ actype=" + actype + ", fromdate=" + fromdate + ", brncode=" + brncode + " ]";
    }
    
}
