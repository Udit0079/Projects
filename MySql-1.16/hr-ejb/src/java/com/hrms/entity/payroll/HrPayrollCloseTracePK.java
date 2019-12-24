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
 * @author Administrator
 */
@Embeddable
public class HrPayrollCloseTracePK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "CAL_DATE_FROM", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date calDateFrom;
    @Basic(optional = false)
    @Column(name = "CAL_DATE_TO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date calDateTo;
    @Basic(optional = false)
    @Column(name = "COMP_CODE", nullable = false)
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE", nullable = false)
    private long empCode;
    @Basic(optional = false)
    @Column(name = "TRACED_PROBLEM_CODE", nullable = false)
    private long tracedProblemCode;

    public HrPayrollCloseTracePK() {
    }

    public HrPayrollCloseTracePK(Date calDateFrom, Date calDateTo, int compCode, long empCode, long tracedProblemCode) {
        this.calDateFrom = calDateFrom;
        this.calDateTo = calDateTo;
        this.compCode = compCode;
        this.empCode = empCode;
        this.tracedProblemCode = tracedProblemCode;
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

    public long getTracedProblemCode() {
        return tracedProblemCode;
    }

    public void setTracedProblemCode(long tracedProblemCode) {
        this.tracedProblemCode = tracedProblemCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calDateFrom != null ? calDateFrom.hashCode() : 0);
        hash += (calDateTo != null ? calDateTo.hashCode() : 0);
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (int) tracedProblemCode;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollCloseTracePK)) {
            return false;
        }
        HrPayrollCloseTracePK other = (HrPayrollCloseTracePK) object;
        if ((this.calDateFrom == null && other.calDateFrom != null) || (this.calDateFrom != null && !this.calDateFrom.equals(other.calDateFrom))) {
            return false;
        }
        if ((this.calDateTo == null && other.calDateTo != null) || (this.calDateTo != null && !this.calDateTo.equals(other.calDateTo))) {
            return false;
        }
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if (this.tracedProblemCode != other.tracedProblemCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrPayrollCloseTracePK[calDateFrom=" + calDateFrom + ", calDateTo=" + calDateTo + ", compCode=" + compCode + ", empCode=" + empCode + ", tracedProblemCode=" + tracedProblemCode + "]";
    }
}
