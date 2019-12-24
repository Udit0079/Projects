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
public class HrLeaveMasterPK extends BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "LEAVE_CODE")
    private String leaveCode;
    @Basic(optional = false)
    @Column(name = "DATE_FROM")
    private String dateFrom;
    @Basic(optional = false)
    @Column(name = "DATE_TO")
    private String dateTo;

    public HrLeaveMasterPK() {
    }

    public HrLeaveMasterPK(int compCode, String leaveCode, String dateFrom, String dateTo) {
        this.compCode = compCode;
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
        hash += (leaveCode != null ? leaveCode.hashCode() : 0);
        hash += (dateFrom != null ? dateFrom.hashCode() : 0);
        hash += (dateTo != null ? dateTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrLeaveMasterPK)) {
            return false;
        }
        HrLeaveMasterPK other = (HrLeaveMasterPK) object;
        if (this.compCode != other.compCode) {
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
        return "entity.payroll.HrLeaveMasterPK[compCode=" + compCode + ", leaveCode=" + leaveCode + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
    }

}
