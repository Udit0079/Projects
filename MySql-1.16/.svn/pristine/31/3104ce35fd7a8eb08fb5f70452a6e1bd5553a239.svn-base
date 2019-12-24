/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Embeddable
public class HrSalaryAllocationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_CODE")
    private Long empCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "SLAB_CODE")
    private String slabCode;
    @Basic(optional = false)
    @Column(name = "COMP_CODE")
    private int compCode;

    public HrSalaryAllocationPK() {
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public HrSalaryAllocationPK(Long empCode, String slabCode) {
        this.empCode = empCode;
        this.slabCode = slabCode;
    }

    public Long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(Long empCode) {
        this.empCode = empCode;
    }

    public String getSlabCode() {
        return slabCode;
    }

    public void setSlabCode(String slabCode) {
        this.slabCode = slabCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empCode != null ? empCode.hashCode() : 0);
        hash += (slabCode != null ? slabCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryAllocationPK)) {
            return false;
        }
        HrSalaryAllocationPK other = (HrSalaryAllocationPK) object;
        if ((this.empCode == null && other.empCode != null) || (this.empCode != null && !this.empCode.equals(other.empCode))) {
            return false;
        }
        if ((this.slabCode == null && other.slabCode != null) || (this.slabCode != null && !this.slabCode.equals(other.slabCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrSalaryAllocationPK[ empCode=" + empCode + ", slabCode=" + slabCode + " ]";
    }
}
