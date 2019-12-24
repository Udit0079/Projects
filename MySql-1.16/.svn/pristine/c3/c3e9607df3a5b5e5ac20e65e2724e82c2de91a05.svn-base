/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.payroll;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Embeddable
public class HrSalaryProcessingDtlPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "REF_NO")
    private int refNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "COMPONENT_TYPE")
    private String componentType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_CODE")
    private BigInteger empCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;

    public HrSalaryProcessingDtlPK() {
    }

    public HrSalaryProcessingDtlPK(int refNo, String componentType, BigInteger empCode, int compCode) {
        this.refNo = refNo;
        this.componentType = componentType;
        this.empCode = empCode;
        this.compCode = compCode;
    }

    public int getRefNo() {
        return refNo;
    }

    public void setRefNo(int refNo) {
        this.refNo = refNo;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public BigInteger getEmpCode() {
        return empCode;
    }

    public void setEmpCode(BigInteger empCode) {
        this.empCode = empCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) refNo;
        hash += (componentType != null ? componentType.hashCode() : 0);
        hash += (empCode != null ? empCode.hashCode() : 0);
        hash += (int) compCode;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrSalaryProcessingDtlPK)) {
            return false;
        }
        HrSalaryProcessingDtlPK other = (HrSalaryProcessingDtlPK) object;
        if (this.refNo != other.refNo) {
            return false;
        }
        if ((this.componentType == null && other.componentType != null) || (this.componentType != null && !this.componentType.equals(other.componentType))) {
            return false;
        }
        if ((this.empCode == null && other.empCode != null) || (this.empCode != null && !this.empCode.equals(other.empCode))) {
            return false;
        }
        if (this.compCode != other.compCode) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.payroll.HrSalaryProcessingDtlPK[ refNo=" + refNo + ", componentType=" + componentType + ", empCode=" + empCode + ", compCode=" + compCode + " ]";
    }
    
}
