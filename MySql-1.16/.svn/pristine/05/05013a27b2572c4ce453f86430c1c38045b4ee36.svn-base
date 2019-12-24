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
public class HrLeavePostingPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "LEAVE_CODE")
    private String leaveCode;
    @Basic(optional = false)
    @Column(name = "DATE_FROM")
    private String dateFrom;
    @Basic(optional = false)
    @Column(name = "DATE_TO")
    private String dateTo;

    public HrLeavePostingPK() {
    }

    public HrLeavePostingPK(int compCode, long empCode, String leaveCode, String dateFrom, String dateTo) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.leaveCode = leaveCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (leaveCode != null ? leaveCode.hashCode() : 0);
        hash += (dateFrom != null ? dateFrom.hashCode() : 0);
        hash += (dateTo != null ? dateTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeavePostingPK)) {
            return false;
        }
        HrLeavePostingPK other = (HrLeavePostingPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.leaveCode == null && other.leaveCode != null) || (this.leaveCode != null && !this.leaveCode.equals(other.leaveCode))) {
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
        return "entity.payroll.HrLeavePostingPK[compCode=" + compCode + ", empCode=" + empCode + ", leaveCode=" + leaveCode + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
    }

}
