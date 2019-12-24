/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.personnel;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Embeddable
public class HrPersonnelPreviousCompanyPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "PREV_COMP_CODE")
    private long prevCompCode;
    @Basic(optional = false)
    @Column(name = "DUR_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date durFrom;
    @Basic(optional = false)
    @Column(name = "DUR_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date durTo;

    public HrPersonnelPreviousCompanyPK() {
    }

    public HrPersonnelPreviousCompanyPK(int compCode, long empCode, long prevCompCode, Date durFrom, Date durTo) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.prevCompCode = prevCompCode;
        this.durFrom = durFrom;
        this.durTo = durTo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(long empCode) {
        this.empCode = empCode;
    }

    public long getPrevCompCode() {
        return prevCompCode;
    }

    public void setPrevCompCode(long prevCompCode) {
        this.prevCompCode = prevCompCode;
    }

    public Date getDurFrom() {
        return durFrom;
    }

    public void setDurFrom(Date durFrom) {
        this.durFrom = durFrom;
    }

    public Date getDurTo() {
        return durTo;
    }

    public void setDurTo(Date durTo) {
        this.durTo = durTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (int) prevCompCode;
        hash += (durFrom != null ? durFrom.hashCode() : 0);
        hash += (durTo != null ? durTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelPreviousCompanyPK)) {
            return false;
        }
        HrPersonnelPreviousCompanyPK other = (HrPersonnelPreviousCompanyPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if (this.prevCompCode != other.prevCompCode) {
            return false;
        }
        if ((this.durFrom == null && other.durFrom != null) || (this.durFrom != null && !this.durFrom.equals(other.durFrom))) {
            return false;
        }
        if ((this.durTo == null && other.durTo != null) || (this.durTo != null && !this.durTo.equals(other.durTo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelPreviousCompanyPK[compCode=" + compCode + ", empCode=" + empCode + ", prevCompCode=" + prevCompCode + ", durFrom=" + durFrom + ", durTo=" + durTo + "]";
    }

}
