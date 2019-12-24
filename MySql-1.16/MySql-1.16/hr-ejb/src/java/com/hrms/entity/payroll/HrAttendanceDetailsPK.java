/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author root
 */
@Embeddable
public class HrAttendanceDetailsPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "ATTEN_MONTH")
    private String attenMonth;
    @Basic(optional = false)
    @Column(name = "ATTEN_YEAR")
    private int attenYear;

    public HrAttendanceDetailsPK() {
    }

    public HrAttendanceDetailsPK(int compCode, long empCode, String attenMonth, int attenYear) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.attenMonth = attenMonth;
        this.attenYear = attenYear;
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

    public String getAttenMonth() {
        return attenMonth;
    }

    public void setAttenMonth(String attenMonth) {
        this.attenMonth = attenMonth;
    }

    public int getAttenYear() {
        return attenYear;
    }

    public void setAttenYear(int attenYear) {
        this.attenYear = attenYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (attenMonth != null ? attenMonth.hashCode() : 0);
        hash += (int) attenYear;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrAttendanceDetailsPK)) {
            return false;
        }
        HrAttendanceDetailsPK other = (HrAttendanceDetailsPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.attenMonth == null && other.attenMonth != null) || (this.attenMonth != null && !this.attenMonth.equals(other.attenMonth))) {
            return false;
        }
        if (this.attenYear != other.attenYear) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrAttendanceDetailsPK[compCode=" + compCode + ", empCode=" + empCode + ", attenMonth=" + attenMonth + ", attenYear=" + attenYear + "]";
    }

}
