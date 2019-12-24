/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "hr_payroll_close_trace")
@NamedQueries({
    @NamedQuery(name = "HrPayrollCloseTrace.findAll", query = "SELECT h FROM HrPayrollCloseTrace h"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByCalDateFrom", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.hrPayrollCloseTracePK.calDateFrom = :calDateFrom"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByCalDateTo", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.hrPayrollCloseTracePK.calDateTo = :calDateTo"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByCompCode", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.hrPayrollCloseTracePK.compCode = :compCode"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByEmpCode", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.hrPayrollCloseTracePK.empCode = :empCode"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByTracedProblemCode", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.hrPayrollCloseTracePK.tracedProblemCode = :tracedProblemCode"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByTracedProblem", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.tracedProblem = :tracedProblem"),
    @NamedQuery(name = "HrPayrollCloseTrace.findTraceProblemCode", query = "SELECT max(h.hrPayrollCloseTracePK.tracedProblemCode) FROM HrPayrollCloseTrace h"),
    @NamedQuery(name = "HrPayrollCloseTrace.findByFromDateAndToDate", query = "SELECT h FROM HrPayrollCloseTrace h WHERE h.hrPayrollCloseTracePK.calDateFrom = :calDateFrom and h.hrPayrollCloseTracePK.calDateTo = :calDateTo")
})
public class HrPayrollCloseTrace extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrPayrollCloseTracePK hrPayrollCloseTracePK;
    @Basic(optional = false)
    @Column(name = "TRACED_PROBLEM", nullable = false, length = 500)
    private String tracedProblem;
    @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMPANY_CODE", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompanyMaster companyMaster;
    @JoinColumns({
        @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "CAL_DATE_FROM", referencedColumnName = "DATE_FROM", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "CAL_DATE_TO", referencedColumnName = "DATE_TO", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPayrollCalendar hrPayrollCalendar;
    @JoinColumns({
        @JoinColumn(name = "COMP_CODE", referencedColumnName = "COMP_CODE", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "EMP_CODE", referencedColumnName = "EMP_CODE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrPersonnelDetails hrPersonnelDetails;

    public HrPayrollCloseTrace() {
    }

    public HrPayrollCloseTrace(HrPayrollCloseTracePK hrPayrollCloseTracePK) {
        this.hrPayrollCloseTracePK = hrPayrollCloseTracePK;
    }

    public HrPayrollCloseTrace(HrPayrollCloseTracePK hrPayrollCloseTracePK, String tracedProblem) {
        this.hrPayrollCloseTracePK = hrPayrollCloseTracePK;
        this.tracedProblem = tracedProblem;
    }

    public HrPayrollCloseTrace(Date calDateFrom, Date calDateTo, int compCode, long empCode, long tracedProblemCode) {
        this.hrPayrollCloseTracePK = new HrPayrollCloseTracePK(calDateFrom, calDateTo, compCode, empCode, tracedProblemCode);
    }

    public HrPayrollCloseTracePK getHrPayrollCloseTracePK() {
        return hrPayrollCloseTracePK;
    }

    public void setHrPayrollCloseTracePK(HrPayrollCloseTracePK hrPayrollCloseTracePK) {
        this.hrPayrollCloseTracePK = hrPayrollCloseTracePK;
    }

    public String getTracedProblem() {
        return tracedProblem;
    }

    public void setTracedProblem(String tracedProblem) {
        this.tracedProblem = tracedProblem;
    }

    public CompanyMaster getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
    }

    public HrPayrollCalendar getHrPayrollCalendar() {
        return hrPayrollCalendar;
    }

    public void setHrPayrollCalendar(HrPayrollCalendar hrPayrollCalendar) {
        this.hrPayrollCalendar = hrPayrollCalendar;
    }

    public HrPersonnelDetails getHrPersonnelDetails() {
        return hrPersonnelDetails;
    }

    public void setHrPersonnelDetails(HrPersonnelDetails hrPersonnelDetails) {
        this.hrPersonnelDetails = hrPersonnelDetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrPayrollCloseTracePK != null ? hrPayrollCloseTracePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollCloseTrace)) {
            return false;
        }
        HrPayrollCloseTrace other = (HrPayrollCloseTrace) object;
        if ((this.hrPayrollCloseTracePK == null && other.hrPayrollCloseTracePK != null) || (this.hrPayrollCloseTracePK != null && !this.hrPayrollCloseTracePK.equals(other.hrPayrollCloseTracePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrPayrollCloseTrace[hrPayrollCloseTracePK=" + hrPayrollCloseTracePK + "]";
    }
}
