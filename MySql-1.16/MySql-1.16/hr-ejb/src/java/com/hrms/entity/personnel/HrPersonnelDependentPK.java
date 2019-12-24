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
public class HrPersonnelDependentPK extends  BaseEntity implements Serializable {
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @Column(name = "EMP_CODE")
    private long empCode;
    @Basic(optional = false)
    @Column(name = "DEPENDENT_CODE")
    private long dependentCode;

    public HrPersonnelDependentPK() {
    }

    public HrPersonnelDependentPK(int compCode, long empCode, long dependentCode) {
        this.compCode = compCode;
        this.empCode = empCode;
        this.dependentCode = dependentCode;
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

    public long getDependentCode() {
        return dependentCode;
    }

    public void setDependentCode(long dependentCode) {
        this.dependentCode = dependentCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (int) empCode;
        hash += (int) dependentCode;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelDependentPK)) {
            return false;
        }
        HrPersonnelDependentPK other = (HrPersonnelDependentPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if (this.empCode != other.empCode) {
            return false;
        }
        if (this.dependentCode != other.dependentCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.payroll.HrPersonnelDependentPK[compCode=" + compCode + ", empCode=" + empCode + ", dependentCode=" + dependentCode + "]";
    }

}
