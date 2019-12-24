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
 * @author Administrator
 */
@Embeddable
public class HrEmpAdvanceDtPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "EMP_ADV_NO", nullable = false)
    private long empAdvNo;
    @Basic(optional = false)
    @Column(name = "COMP_CODE", nullable = false)
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE", nullable = false)
    private long empCode;
    @Basic(optional = false)
    @Column(name = "ADVANCE", nullable = false, length = 6)
    private String advance;
    @Basic(optional = false)
    @Column(name = "DUE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    public HrEmpAdvanceDtPK() {
    }

    public HrEmpAdvanceDtPK(long empAdvNo, int compCode, long empCode, String advance, Date dueDate) {
        this.empAdvNo = empAdvNo;
        this.compCode = compCode;
        this.empCode = empCode;
        this.advance = advance;
        this.dueDate = dueDate;
    }

    public long getEmpAdvNo() {
        return empAdvNo;
    }

    public void setEmpAdvNo(long empAdvNo) {
        this.empAdvNo = empAdvNo;
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

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empAdvNo;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (advance != null ? advance.hashCode() : 0);
        hash += (dueDate != null ? dueDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpAdvanceDtPK)) {
            return false;
        }
        HrEmpAdvanceDtPK other = (HrEmpAdvanceDtPK) object;
        if (this.empAdvNo != other.empAdvNo) {
            return false;
        }
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if ((this.advance == null && other.advance != null) || (this.advance != null && !this.advance.equals(other.advance))) {
            return false;
        }
        if ((this.dueDate == null && other.dueDate != null) || (this.dueDate != null && !this.dueDate.equals(other.dueDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.personnel.HrEmpAdvanceDtPK[empAdvNo=" + empAdvNo + ", compCode=" + compCode + ", empCode=" + empCode + ", advance=" + advance + ", dueDate=" + dueDate + "]";
    }
}
