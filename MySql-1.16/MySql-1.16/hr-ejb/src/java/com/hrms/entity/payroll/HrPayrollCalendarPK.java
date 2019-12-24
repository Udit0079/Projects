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
public class HrPayrollCalendarPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Basic(optional = false)
    @Column(name = "DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;

    public HrPayrollCalendarPK() {
    }

    public HrPayrollCalendarPK(int compCode, Date dateFrom, Date dateTo) {
        this.compCode = compCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (dateFrom != null ? dateFrom.hashCode() : 0);
        hash += (dateTo != null ? dateTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPayrollCalendarPK)) {
            return false;
        }
        HrPayrollCalendarPK other = (HrPayrollCalendarPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.dateFrom == null && other.dateFrom != null) || (this.dateFrom != null && !this.dateFrom.equals(other.dateFrom))) {
            return false;
        }
        if ((this.dateTo == null && other.dateTo != null) || (this.dateTo != null && !this.dateTo.equals(other.dateTo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPayrollCalendarPK[compCode=" + compCode + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
    }

}
