/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.entity.hr;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author root
 */
@Embeddable
public class HrPersonnelDetailsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_CODE")
    private int compCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_CODE")
    private BigInteger empCode;

    public HrPersonnelDetailsPK() {
    }

    public HrPersonnelDetailsPK(int compCode, BigInteger empCode) {
        this.compCode = compCode;
        this.empCode = empCode;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public BigInteger getEmpCode() {
        return empCode;
    }

    public void setEmpCode(BigInteger empCode) {
        this.empCode = empCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compCode;
        hash += (empCode != null ? empCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrPersonnelDetailsPK)) {
            return false;
        }
        HrPersonnelDetailsPK other = (HrPersonnelDetailsPK) object;
        if (this.compCode != other.compCode) {
            return false;
        }
        if ((this.empCode == null && other.empCode != null) || (this.empCode != null && !this.empCode.equals(other.empCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hrms.entity.hr.HrPersonnelDetailsPK[ compCode=" + compCode + ", empCode=" + empCode + " ]";
    }
    
}
