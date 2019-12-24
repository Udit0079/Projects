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

/**
 *
 * @author sipl
 */
@Embeddable
public class InvestmentFdrIntHisPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CTRL_NO")
    private long ctrlNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;

    public InvestmentFdrIntHisPK() {
    }

    public InvestmentFdrIntHisPK(long ctrlNo, Date dt) {
        this.ctrlNo = ctrlNo;
        this.dt = dt;
    }

    public long getCtrlNo() {
        return ctrlNo;
    }

    public void setCtrlNo(long ctrlNo) {
        this.ctrlNo = ctrlNo;
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
        hash += (int) ctrlNo;
        hash += (dt != null ? dt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvestmentFdrIntHisPK)) {
            return false;
        }
        InvestmentFdrIntHisPK other = (InvestmentFdrIntHisPK) object;
        if (this.ctrlNo != other.ctrlNo) {
            return false;
        }
        if ((this.dt == null && other.dt != null) || (this.dt != null && !this.dt.equals(other.dt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.InvestmentFdrIntHisPK[ ctrlNo=" + ctrlNo + ", dt=" + dt + " ]";
    }
    
}
