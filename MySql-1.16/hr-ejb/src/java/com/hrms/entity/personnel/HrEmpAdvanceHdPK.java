/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.personnel;

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
public class HrEmpAdvanceHdPK extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "EMP_ADV_NO")
    private long empAdvNo;
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "ADVANCE")
    private String advance;

    public HrEmpAdvanceHdPK() {
    }

    public HrEmpAdvanceHdPK(long empAdvNo, int compCode, long empCode, String advance) {
        this.empAdvNo = empAdvNo;
        this.compCode = compCode;
        this.empCode = empCode;
        this.advance = advance;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empAdvNo;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (advance != null ? advance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrEmpAdvanceHdPK)) {
            return false;
        }
        HrEmpAdvanceHdPK other = (HrEmpAdvanceHdPK) object;
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
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrEmpAdvanceHdPK[empAdvNo=" + empAdvNo + ", compCode=" + compCode + ", empCode=" + empCode + ", advance=" + advance + "]";
    }
}
