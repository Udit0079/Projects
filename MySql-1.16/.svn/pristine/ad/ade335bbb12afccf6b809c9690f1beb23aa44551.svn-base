/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;

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
public class HrAttendanceMaintenancePK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "ATTEN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attenDate;

    public HrAttendanceMaintenancePK() {
    }

    public HrAttendanceMaintenancePK(int compCode, long empCode, Date attenDate) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.attenDate = attenDate;
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

    public Date getAttenDate() {
        return attenDate;
    }

    public void setAttenDate(Date attenDate) {
        this.attenDate = attenDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (attenDate != null ? attenDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAttendanceMaintenancePK)) {
            return false;
        }
        HrAttendanceMaintenancePK other = (HrAttendanceMaintenancePK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.attenDate == null && other.attenDate != null) || (this.attenDate != null && !this.attenDate.equals(other.attenDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrAttendanceMaintenancePK[compCode=" + compCode + ", empCode=" + empCode + ", attenDate=" + attenDate + "]";
    }

}
