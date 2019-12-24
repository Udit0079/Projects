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
public class HrSalaryProcessingPK extends BaseEntity  implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "CAL_DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date calDateFrom;
    @Basic(optional = false)
    @Column(name = "CAL_DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date calDateTo;
    @Basic(optional = false)
    @Column(name = "MONTHS")
    private String months;

    public HrSalaryProcessingPK() {
    }

    public HrSalaryProcessingPK(int compCode, long empCode, Date calDateFrom, Date calDateTo, String months) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.calDateFrom = calDateFrom;
        this.calDateTo = calDateTo;
        this.months = months;
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

    public Date getCalDateFrom() {
        return calDateFrom;
    }

    public void setCalDateFrom(Date calDateFrom) {
        this.calDateFrom = calDateFrom;
    }

    public Date getCalDateTo() {
        return calDateTo;
    }

    public void setCalDateTo(Date calDateTo) {
        this.calDateTo = calDateTo;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (calDateFrom != null ? calDateFrom.hashCode() : 0);
        hash += (calDateTo != null ? calDateTo.hashCode() : 0);
        hash += (months != null ? months.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryProcessingPK)) {
            return false;
        }
        HrSalaryProcessingPK other = (HrSalaryProcessingPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.calDateFrom == null && other.calDateFrom != null) || (this.calDateFrom != null && !this.calDateFrom.equals(other.calDateFrom))) {
            return false;
        }
        if ((this.calDateTo == null && other.calDateTo != null) || (this.calDateTo != null && !this.calDateTo.equals(other.calDateTo))) {
            return false;
        }
        if ((this.months == null && other.months != null) || (this.months != null && !this.months.equals(other.months))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrSalaryProcessingPK[compCode=" + compCode + ", empCode=" + empCode + ", calDateFrom=" + calDateFrom + ", calDateTo=" + calDateTo + ", months=" + months + "]";
    }

}
